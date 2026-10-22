//! Bounded target-neutral interpreter. Live variables/widgets belong to its host.
#![forbid(unsafe_code)]
mod arithmetic;
use Opcode::{
    ArrayLength, DefineArray, GetArrayInt, Iconst, IfIcmpeq, IfIcmpge, IfIcmpgt, IfIcmple,
    IfIcmplt, IfIcmpne, Iload, Invoke, Istore, JoinString, Jump, Lconst, Lload, Lstore, Oload,
    Ostore, PopInt, PopLong, PopObject, PushNull, Return, Sconst, SetArrayInt, Switch,
};
use rustclient_cache::script::{Opcode, Script};
use rustclient_model::{ScriptId, ScriptRequest, ScriptValue, WidgetAddress};
use std::sync::Arc;
#[derive(Debug, Clone, Copy)]
pub struct Limits {
    pub instructions: usize,
    pub stack: usize,
    pub depth: usize,
    pub text_bytes: usize,
}
impl Default for Limits {
    fn default() -> Self {
        Self {
            instructions: 1_000_000,
            stack: 4096,
            depth: 64,
            text_bytes: 1_048_576,
        }
    }
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ScriptError {
    pub script: ScriptId,
    pub instruction: usize,
    pub detail: String,
}
impl std::fmt::Display for ScriptError {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        write!(
            f,
            "script {} instruction {}: {}",
            self.script, self.instruction, self.detail
        )
    }
}
impl std::error::Error for ScriptError {}
pub type Result<T> = std::result::Result<T, String>;
#[derive(Debug, Clone)]
pub enum Object {
    Text(String),
    Array(usize),
    Null,
}
#[derive(Debug)]
pub enum Array {
    Integers(Vec<i32>),
    Strings(Vec<String>),
}
#[derive(Default)]
pub struct Stacks {
    pub integers: Vec<i32>,
    pub objects: Vec<Object>,
    pub longs: Vec<i64>,
    arrays: Vec<Array>,
}
impl Stacks {
    pub fn push_object(&mut self, value: ScriptValue) {
        let object = match value {
            ScriptValue::Text(s) => Object::Text(s),
            ScriptValue::Integers(a) => {
                let id = self.arrays.len();
                self.arrays.push(Array::Integers(a));
                Object::Array(id)
            }
            ScriptValue::Strings(a) => {
                let id = self.arrays.len();
                self.arrays.push(Array::Strings(a));
                Object::Array(id)
            }
            _ => Object::Null,
        };
        self.objects.push(object);
    }

    /// # Errors
    /// Returns an error when the integer stack is empty.
    pub fn int(&mut self) -> Result<i32> {
        self.integers
            .pop()
            .ok_or_else(|| "integer stack underflow".into())
    }
    /// # Errors
    /// Returns an error when the object stack is empty.
    pub fn object(&mut self) -> Result<Object> {
        self.objects
            .pop()
            .ok_or_else(|| "object stack underflow".into())
    }
    /// # Errors
    /// Returns an error for an empty object stack or a non-string value.
    pub fn text(&mut self) -> Result<String> {
        match self.object()? {
            Object::Text(s) => Ok(s),
            Object::Null => Ok("null".into()),
            Object::Array(_) => Err("expected string".into()),
        }
    }
    /// # Errors
    /// Returns an error when the long stack is empty.
    pub fn long(&mut self) -> Result<i64> {
        self.longs
            .pop()
            .ok_or_else(|| "long stack underflow".into())
    }
    /// # Errors
    /// Returns an error when fewer than N integer operands are available.
    pub fn ints<const N: usize>(&mut self) -> Result<[i32; N]> {
        let start = self
            .integers
            .len()
            .checked_sub(N)
            .ok_or("integer stack underflow")?;
        let out = self.integers[start..]
            .try_into()
            .map_err(|_| "integer stack shape")?;
        self.integers.truncate(start);
        Ok(out)
    }
}
#[derive(Default)]
pub struct Context {
    pub active: [Option<WidgetAddress>; 2],
}
pub trait Host {
    /// # Errors
    /// Returns an error when the script asset cannot be loaded.
    fn script(&mut self, id: ScriptId) -> Result<Arc<Script>>;
    /// # Errors
    /// Returns an error for unsupported behavior or an invalid semantic transition.
    fn instruction(
        &mut self,
        opcode: Opcode,
        operand: i32,
        stacks: &mut Stacks,
        context: &mut Context,
    ) -> Result<()>;
}
struct Frame {
    script: Arc<Script>,
    pc: usize,
    ints: Vec<i32>,
    objects: Vec<Object>,
    longs: Vec<i64>,
}
impl Frame {
    fn new(script: Arc<Script>, stacks: &mut Stacks) -> Result<Self> {
        let [i, o, l] = script.arguments;
        let is = stacks
            .integers
            .len()
            .checked_sub(i)
            .ok_or("missing integer arguments")?;
        let os = stacks
            .objects
            .len()
            .checked_sub(o)
            .ok_or("missing object arguments")?;
        let ls = stacks
            .longs
            .len()
            .checked_sub(l)
            .ok_or("missing long arguments")?;
        let mut ints = vec![0; script.locals[0]];
        let mut objects = vec![Object::Null; script.locals[1]];
        let mut longs = vec![0; script.locals[2]];
        ints[..i].copy_from_slice(&stacks.integers[is..]);
        objects[..o].clone_from_slice(&stacks.objects[os..]);
        longs[..l].copy_from_slice(&stacks.longs[ls..]);
        stacks.integers.truncate(is);
        stacks.objects.truncate(os);
        stacks.longs.truncate(ls);
        Ok(Self {
            script,
            pc: 0,
            ints,
            objects,
            longs,
        })
    }
}
fn index(n: i32) -> Result<usize> {
    usize::try_from(n).map_err(|_| "negative index".into())
}
fn jump(frame: &mut Frame, offset: i32) -> Result<()> {
    frame.pc = frame
        .pc
        .checked_add_signed(isize::try_from(offset).map_err(|_| "branch overflow")?)
        .filter(|pc| *pc < frame.script.instructions.len())
        .ok_or("branch outside program")?;
    Ok(())
}
/// Execute one invocation, sharing stacks between bounded calls. Prefix host commits survive errors.
/// # Errors
/// Reports exact script/instruction for limits, malformed stack use and unsupported host behavior.
#[expect(
    clippy::too_many_lines,
    reason = "bounded VM instruction dispatch with a single error-context boundary"
)]
#[expect(
    clippy::many_single_char_names,
    reason = "integer/object/long operand counts and local arithmetic in the VM grammar"
)]
pub fn execute(
    host: &mut impl Host,
    request: ScriptRequest,
    mut context: Context,
    limits: Limits,
) -> std::result::Result<(), ScriptError> {
    let mut stacks = Stacks::default();
    for value in request.arguments {
        match value {
            ScriptValue::Int(n) => stacks.integers.push(n),
            ScriptValue::Long(n) => stacks.longs.push(n),
            other => stacks.push_object(other),
        }
    }
    let fail = |detail: String| ScriptError {
        script: request.id,
        instruction: 0,
        detail,
    };
    let program = host.script(request.id).map_err(fail)?;
    let [i, o, l] = program.locals;
    if stacks.integers.len() > i || stacks.objects.len() > o || stacks.longs.len() > l {
        return Err(fail("event arguments exceed locals".into()));
    }
    let mut frame = Frame {
        script: program,
        pc: 0,
        ints: vec![0; i],
        objects: vec![Object::Null; o],
        longs: vec![0; l],
    };
    frame.ints[..stacks.integers.len()].copy_from_slice(&stacks.integers);
    frame.objects[..stacks.objects.len()].clone_from_slice(&stacks.objects);
    frame.longs[..stacks.longs.len()].copy_from_slice(&stacks.longs);
    stacks.integers.clear();
    stacks.objects.clear();
    stacks.longs.clear();
    let mut calls = Vec::new();
    let mut remaining = limits.instructions;
    loop {
        let current_pc = frame.pc;
        let result = (|| -> Result<bool> {
            if remaining == 0 {
                return Err("instruction budget exhausted".into());
            }
            remaining -= 1;
            if stacks.integers.len() + stacks.objects.len() + stacks.longs.len() > limits.stack {
                return Err("stack limit".into());
            }
            let bytes = stacks
                .objects
                .iter()
                .chain(frame.objects.iter())
                .chain(calls.iter().flat_map(|f: &Frame| f.objects.iter()))
                .map(|v| match v {
                    Object::Text(s) => s.len(),
                    _ => 8,
                })
                .sum::<usize>()
                + stacks
                    .arrays
                    .iter()
                    .map(|a| match a {
                        Array::Integers(a) => a.len() * 4,
                        Array::Strings(a) => {
                            a.iter().map(String::len).sum::<usize>() + a.len() * 24
                        }
                    })
                    .sum::<usize>();
            if bytes > limits.text_bytes {
                return Err("allocation limit".into());
            }
            let ins = frame
                .script
                .instructions
                .get(frame.pc)
                .ok_or("program ended without return")?
                .clone();
            frame.pc += 1;
            let n = ins.integer;
            match ins.opcode {
                Iconst => stacks.integers.push(n),
                Sconst => stacks
                    .objects
                    .push(Object::Text(ins.text.unwrap_or_default())),
                Lconst => stacks.longs.push(ins.long),
                Iload => stacks
                    .integers
                    .push(*frame.ints.get(index(n)?).ok_or("integer local index")?),
                Istore => {
                    let v = stacks.int()?;
                    *frame.ints.get_mut(index(n)?).ok_or("integer local index")? = v;
                }
                Oload => stacks.objects.push(
                    frame
                        .objects
                        .get(index(n)?)
                        .ok_or("object local index")?
                        .clone(),
                ),
                Ostore => {
                    let v = stacks.object()?;
                    *frame
                        .objects
                        .get_mut(index(n)?)
                        .ok_or("object local index")? = v;
                }
                Lload => stacks
                    .longs
                    .push(*frame.longs.get(index(n)?).ok_or("long local index")?),
                Lstore => {
                    let v = stacks.long()?;
                    *frame.longs.get_mut(index(n)?).ok_or("long local index")? = v;
                }
                ArrayLength => {
                    let value = stacks.object()?;
                    let length = if let Object::Array(id) = value {
                        match stacks.arrays.get(id).ok_or("array handle")? {
                            Array::Integers(a) => a.len(),
                            Array::Strings(a) => a.len(),
                        }
                    } else {
                        0
                    };
                    stacks
                        .integers
                        .push(i32::try_from(length).map_err(|_| "array length")?);
                }
                PopInt => {
                    stacks.int()?;
                }
                PopObject => {
                    stacks.object()?;
                }
                PopLong => {
                    stacks.long()?;
                }
                PushNull => stacks.objects.push(Object::Null),
                Jump => jump(&mut frame, n)?,
                IfIcmpeq | IfIcmpne | IfIcmplt | IfIcmpgt | IfIcmple | IfIcmpge => {
                    let [a, b] = stacks.ints()?;
                    let yes = match ins.opcode {
                        IfIcmpeq => a == b,
                        IfIcmpne => a != b,
                        IfIcmplt => a < b,
                        IfIcmpgt => a > b,
                        IfIcmple => a <= b,
                        _ => a >= b,
                    };
                    if yes {
                        jump(&mut frame, n)?;
                    }
                }
                Switch => {
                    let key = stacks.int()?;
                    let offset = frame
                        .script
                        .switches
                        .get(index(n)?)
                        .ok_or("switch index")?
                        .get(&key)
                        .copied();
                    if let Some(offset) = offset {
                        jump(&mut frame, offset)?;
                    }
                }
                Invoke => {
                    if calls.len() >= limits.depth {
                        return Err("call depth limit".into());
                    }
                    let script = host.script(ScriptId::new(
                        u32::try_from(n).map_err(|_| "negative script ID")?,
                    ))?;
                    let next = Frame::new(script, &mut stacks)?;
                    calls.push(std::mem::replace(&mut frame, next));
                }
                Return => {
                    if let Some(previous) = calls.pop() {
                        frame = previous;
                    } else {
                        return Ok(true);
                    }
                }
                JoinString => {
                    let start = stacks
                        .objects
                        .len()
                        .checked_sub(index(n)?)
                        .ok_or("join underflow")?;
                    let mut out = String::new();
                    for value in &stacks.objects[start..] {
                        if let Object::Text(s) = value {
                            if out.len() + s.len() > limits.text_bytes {
                                return Err("text allocation limit".into());
                            }
                            out.push_str(s);
                        } else {
                            return Err("join expects strings".into());
                        }
                    }
                    stacks.objects.truncate(start);
                    stacks.objects.push(Object::Text(out));
                }
                DefineArray => {
                    let size = index(stacks.int()?)?;
                    if size > 5000 || stacks.arrays.len() >= 4096 {
                        return Err("array allocation limit".into());
                    }
                    let slot = index(n >> 16)?;
                    let kind = n & 65535;
                    let target = frame.objects.get_mut(slot).ok_or("array local index")?;
                    let id = stacks.arrays.len();
                    stacks.arrays.push(if kind == 115 {
                        Array::Strings(vec![String::new(); size])
                    } else {
                        Array::Integers(vec![if kind == 105 || kind == 49 { 0 } else { -1 }; size])
                    });
                    *target = Object::Array(id);
                }
                GetArrayInt => {
                    let pos = index(stacks.int()?)?;
                    let Object::Array(id) =
                        frame.objects.get(index(n)?).ok_or("array local index")?
                    else {
                        return Err("local is not an array".into());
                    };
                    match stacks.arrays.get(*id).ok_or("array handle")? {
                        Array::Integers(a) => {
                            stacks.integers.push(*a.get(pos).ok_or("array index")?);
                        }
                        Array::Strings(a) => stacks
                            .objects
                            .push(Object::Text(a.get(pos).ok_or("array index")?.clone())),
                    }
                }
                SetArrayInt => {
                    let Object::Array(id) =
                        frame.objects.get(index(n)?).ok_or("array local index")?
                    else {
                        return Err("local is not an array".into());
                    };
                    let strings = matches!(stacks.arrays.get(*id), Some(Array::Strings(_)));
                    if strings {
                        let pos = index(stacks.int()?)?;
                        let v = stacks.text()?;
                        if let Some(Array::Strings(a)) = stacks.arrays.get_mut(*id) {
                            *a.get_mut(pos).ok_or("array index")? = v;
                        }
                    } else {
                        let [pos, v] = stacks.ints()?;
                        if let Some(Array::Integers(a)) = stacks.arrays.get_mut(*id) {
                            *a.get_mut(index(pos)?).ok_or("array index")? = v;
                        } else {
                            return Err("array handle".into());
                        }
                    }
                }
                opcode => {
                    if !arithmetic::instruction(opcode, &mut stacks)? {
                        host.instruction(opcode, n, &mut stacks, &mut context)?;
                    }
                }
            }
            Ok(false)
        })();
        match result {
            Ok(true) => return Ok(()),
            Ok(false) => {}
            Err(detail) => {
                return Err(ScriptError {
                    script: frame.script.id,
                    instruction: current_pc,
                    detail,
                });
            }
        }
    }
}
