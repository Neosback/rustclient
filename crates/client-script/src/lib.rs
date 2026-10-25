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
/// Which result an array search produces.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ArraySearch {
    First,
    Last,
    Count,
}
/// Per-array bookkeeping the reference keeps beside the elements: the fill value used when an
/// array grows, and whether the array is a shared read-only view (enum key/value arrays).
#[derive(Debug, Clone, Copy)]
struct ArrayMeta {
    init: i32,
    frozen: bool,
}
/// Largest array a script may create or grow to (reference limit).
const ARRAY_LIMIT: usize = 5000;
#[derive(Default)]
pub struct Stacks {
    pub integers: Vec<i32>,
    pub objects: Vec<Object>,
    pub longs: Vec<i64>,
    arrays: Vec<Array>,
    meta: Vec<ArrayMeta>,
}
impl Stacks {
    pub fn push_object(&mut self, value: ScriptValue) {
        let object = match value {
            ScriptValue::Text(s) => Object::Text(s),
            ScriptValue::Integers(a) => Object::Array(self.add_array(Array::Integers(a), 0, false)),
            ScriptValue::Strings(a) => Object::Array(self.add_array(Array::Strings(a), 0, false)),
            _ => Object::Null,
        };
        self.objects.push(object);
    }

    fn add_array(&mut self, array: Array, init: i32, frozen: bool) -> usize {
        self.arrays.push(array);
        self.meta.push(ArrayMeta { init, frozen });
        self.arrays.len() - 1
    }
    /// Creates a script array (reference opcode 8022). `text` arrays hold empty strings; integer
    /// arrays are filled with `init`, which is also what growing the array later fills with.
    ///
    /// # Errors
    /// Returns an error beyond 5000 elements or when too many arrays exist.
    pub fn new_array(&mut self, text: bool, init: i32, length: usize) -> Result<Object> {
        if length > ARRAY_LIMIT || self.arrays.len() >= 4096 {
            return Err("array allocation limit".into());
        }
        let array = if text {
            Array::Strings(vec![String::new(); length])
        } else {
            Array::Integers(vec![init; length])
        };
        Ok(Object::Array(self.add_array(array, init, false)))
    }
    /// Marks `object` as a read-only view: every mutating array operation then fails, as for the
    /// reference's shared enum key and value arrays.
    pub fn freeze_array(&mut self, object: &Object) {
        if let Object::Array(id) = object
            && let Some(meta) = self.meta.get_mut(*id)
        {
            meta.frozen = true;
        }
    }
    /// Element handle of a mutable array, or an error for null, non-array or read-only operands.
    fn writable(&self, object: &Object) -> Result<usize> {
        match object {
            Object::Array(id) => match self.meta.get(*id) {
                Some(meta) if meta.frozen => Err("array is read-only".into()),
                Some(_) => Ok(*id),
                None => Err("array handle".into()),
            },
            Object::Null => Err("null array".into()),
            Object::Text(_) => Err("expected array".into()),
        }
    }
    /// Number of elements, 0 for a null array.
    ///
    /// # Errors
    /// Returns an error for a non-array operand or invalid handle.
    pub fn array_len(&self, object: &Object) -> Result<usize> {
        Ok(match self.array(object)? {
            Some(Array::Integers(a)) => a.len(),
            Some(Array::Strings(a)) => a.len(),
            None => 0,
        })
    }
    /// Sets the length (reference opcode 8023), filling new elements with the array's grow value.
    ///
    /// # Errors
    /// Returns an error for null, read-only or non-array operands or a length above 5000.
    pub fn resize_array(&mut self, object: &Object, length: usize) -> Result<()> {
        let id = self.writable(object)?;
        if length > ARRAY_LIMIT {
            return Err("array length limit".into());
        }
        let init = self.meta[id].init;
        match &mut self.arrays[id] {
            Array::Integers(a) => a.resize(length, init),
            Array::Strings(a) => a.resize(length, String::new()),
        }
        Ok(())
    }
    /// Inserts `value` at `index`, which may equal the length (reference opcodes 8024 and 8025).
    ///
    /// # Errors
    /// Returns an error for a bad index, a value of the wrong element type, a read-only array or
    /// growth above 5000 elements.
    pub fn insert_array(
        &mut self,
        object: &Object,
        index: usize,
        value: ScriptValue,
    ) -> Result<()> {
        let id = self.writable(object)?;
        let length = self.array_len(object)?;
        if index > length || length >= ARRAY_LIMIT {
            return Err("array insert index".into());
        }
        match (&mut self.arrays[id], value) {
            (Array::Integers(a), ScriptValue::Int(v)) => a.insert(index, v),
            (Array::Strings(a), ScriptValue::Text(v)) => a.insert(index, v),
            _ => return Err("array value type".into()),
        }
        Ok(())
    }
    /// Removes and returns the element at `index` (reference opcode 8026).
    ///
    /// # Errors
    /// Returns an error for a bad index or a null, read-only or non-array operand.
    pub fn remove_array(&mut self, object: &Object, index: usize) -> Result<ScriptValue> {
        let id = self.writable(object)?;
        if index >= self.array_len(object)? {
            return Err("array remove index".into());
        }
        Ok(match &mut self.arrays[id] {
            Array::Integers(a) => ScriptValue::Int(a.remove(index)),
            Array::Strings(a) => ScriptValue::Text(a.remove(index)),
        })
    }
    /// Inserts every element of `source` at `index` of `target` (reference opcodes 8027 and
    /// 8028). An empty source changes nothing.
    ///
    /// # Errors
    /// Returns an error for a bad index, mismatched element types, null operands, a read-only
    /// target or growth above 5000 elements.
    pub fn insert_all_array(
        &mut self,
        target: &Object,
        source: &Object,
        index: usize,
    ) -> Result<()> {
        let id = self.writable(target)?;
        let incoming = match self.array(source)? {
            Some(Array::Integers(a)) => Array::Integers(a.clone()),
            Some(Array::Strings(a)) => Array::Strings(a.clone()),
            None => return Err("null array".into()),
        };
        if index > self.array_len(target)? {
            return Err("array insert index".into());
        }
        match (&mut self.arrays[id], incoming) {
            (Array::Integers(a), Array::Integers(b)) => {
                if a.len() + b.len() > ARRAY_LIMIT && !b.is_empty() {
                    return Err("array length limit".into());
                }
                a.splice(index..index, b);
            }
            (Array::Strings(a), Array::Strings(b)) => {
                if a.len() + b.len() > ARRAY_LIMIT && !b.is_empty() {
                    return Err("array length limit".into());
                }
                a.splice(index..index, b);
            }
            _ => return Err("array element type".into()),
        }
        Ok(())
    }
    /// Removes `count` elements starting at `index` (reference opcode 8029). A zero count is a
    /// no-op once the array is known to be mutable.
    ///
    /// # Errors
    /// Returns an error when the range leaves the array or the operand is null or read-only.
    pub fn remove_array_range(&mut self, object: &Object, index: i32, count: i32) -> Result<()> {
        let id = self.writable(object)?;
        if count == 0 {
            return Ok(());
        }
        let length = self.array_len(object)?;
        let (Ok(index), Ok(count)) = (usize::try_from(index), usize::try_from(count)) else {
            return Err("array range".into());
        };
        if index >= length || index + count > length {
            return Err("array range".into());
        }
        match &mut self.arrays[id] {
            Array::Integers(a) => {
                a.drain(index..index + count);
            }
            Array::Strings(a) => {
                a.drain(index..index + count);
            }
        }
        Ok(())
    }
    fn array(&self, object: &Object) -> Result<Option<&Array>> {
        match object {
            Object::Null => Ok(None),
            Object::Array(id) => self
                .arrays
                .get(*id)
                .map(Some)
                .ok_or_else(|| "array handle".into()),
            Object::Text(_) => Err("expected array".into()),
        }
    }
    /// Sorts `keys` ascending over its whole length, applying the same permutation to the
    /// matching prefix of `companion` (reference opcode 8000). Equal keys keep their order,
    /// where the reference quicksort leaves ties unspecified.
    ///
    /// # Errors
    /// Returns an error for a null or non-array `keys`, a companion shorter than `keys`, or
    /// an invalid array handle.
    pub fn sort_array(&mut self, keys: &Object, companion: &Object) -> Result<()> {
        let Object::Array(key_id) = keys else {
            return Err("sort requires an array".into());
        };
        let key_id = *key_id;
        let length = match self.array(keys)?.ok_or("sort requires an array")? {
            Array::Integers(a) => a.len(),
            Array::Strings(a) => a.len(),
        };
        if length <= 1 {
            return Ok(());
        }
        let order = self.sort_order(key_id)?;
        if let Some(Array::Integers(a)) = self.arrays.get_mut(key_id) {
            let sorted: Vec<i32> = order.iter().map(|&i| a[i]).collect();
            *a = sorted;
        } else if let Some(Array::Strings(a)) = self.arrays.get_mut(key_id) {
            let sorted: Vec<String> = order.iter().map(|&i| std::mem::take(&mut a[i])).collect();
            *a = sorted;
        }
        let Object::Array(companion_id) = companion else {
            return Ok(());
        };
        let companion_length = match self.arrays.get(*companion_id).ok_or("array handle")? {
            Array::Integers(a) => a.len(),
            Array::Strings(a) => a.len(),
        };
        if companion_length < length {
            return Err("sort companion shorter than keys".into());
        }
        match self.arrays.get_mut(*companion_id) {
            Some(Array::Integers(a)) => {
                let head: Vec<i32> = order.iter().map(|&i| a[i]).collect();
                a[..length].copy_from_slice(&head);
            }
            Some(Array::Strings(a)) => {
                let head: Vec<String> = order.iter().map(|&i| a[i].clone()).collect();
                a[..length].clone_from_slice(&head);
            }
            None => return Err("array handle".into()),
        }
        Ok(())
    }
    fn sort_order(&self, id: usize) -> Result<Vec<usize>> {
        let mut order: Vec<usize>;
        match self.arrays.get(id).ok_or("array handle")? {
            Array::Integers(a) => {
                order = (0..a.len()).collect();
                order.sort_by_key(|&i| a[i]);
            }
            // Java `String.compareTo` orders by UTF-16 code unit.
            Array::Strings(a) => {
                order = (0..a.len()).collect();
                order.sort_by(|&x, &y| a[x].encode_utf16().cmp(a[y].encode_utf16()));
            }
        }
        Ok(order)
    }
    /// Searches `from..to` of `array` for `value` (reference opcodes 8005–8007). A negative
    /// `from` starts at zero and an out-of-range or negative `to` runs to the end. A null array
    /// finds nothing: `-1` for the index searches and `0` for the count.
    ///
    /// # Errors
    /// Returns an error when the value type does not match the array element type.
    pub fn search_array(
        &self,
        array: &Object,
        value: &ScriptValue,
        range: [i32; 2],
        mode: ArraySearch,
    ) -> Result<i32> {
        let Some(array) = self.array(array)? else {
            return Ok(if mode == ArraySearch::Count { 0 } else { -1 });
        };
        let (length, hit): (usize, Box<dyn Fn(usize) -> bool>) = match (array, value) {
            (Array::Integers(a), ScriptValue::Int(v)) => (a.len(), Box::new(|i| a[i] == *v)),
            (Array::Strings(a), ScriptValue::Text(v)) => (a.len(), Box::new(|i| a[i] == *v)),
            _ => return Err("array search value type".into()),
        };
        let from = usize::try_from(range[0]).unwrap_or(0);
        let to = usize::try_from(range[1])
            .ok()
            .filter(|to| *to <= length)
            .unwrap_or(length);
        let mut hits = (from..to).filter(|&i| hit(i));
        let found = match mode {
            ArraySearch::First => hits.next(),
            ArraySearch::Last => hits.next_back(),
            ArraySearch::Count => {
                return i32::try_from(hits.count()).map_err(|_| "array count".into());
            }
        };
        found.map_or(Ok(-1), |i| {
            i32::try_from(i).map_err(|_| "array index".into())
        })
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
                    let slot = index(n)?;
                    let target = frame.objects.get_mut(slot).ok_or("array local index")?;
                    let (array, init) =
                        match ins.array_default.ok_or("missing semantic array default")? {
                            rustclient_cache::script::ArrayInitialValue::Text => {
                                (Array::Strings(vec![String::new(); size]), 0)
                            }
                            rustclient_cache::script::ArrayInitialValue::Integer(value) => {
                                (Array::Integers(vec![value; size]), value)
                            }
                        };
                    let id = stacks.add_array(array, init, false);
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
