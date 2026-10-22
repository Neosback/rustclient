use rustclient_cache::{GroupId, IndexId, Source, osrs240::scripts::decode, script::Opcode};
use rustclient_model::ScriptId;
#[test]
fn revision_240_script_footer_long_operands_switches_and_truncation_are_bounded() {
    // Synthetic 240 grammar: empty name, LCONST(17), RETURN; 3 local/argument types.
    let mut bytes = vec![0, 0, 61];
    bytes.extend(17_i64.to_be_bytes());
    bytes.extend([0, 21, 0]);
    bytes.extend(2_u32.to_be_bytes());
    for n in [0_u16, 0, 1, 0, 0, 1] {
        bytes.extend(n.to_be_bytes());
    }
    bytes.push(1);
    bytes.extend(1_u16.to_be_bytes());
    bytes.extend(7_i32.to_be_bytes());
    bytes.extend((-1_i32).to_be_bytes());
    bytes.extend(11_u16.to_be_bytes());
    let source = Source::group(IndexId(12), GroupId(23));
    let script = decode(&bytes, ScriptId::new(23), source).unwrap();
    assert_eq!(script.locals, [0, 0, 1]);
    assert_eq!(script.arguments, [0, 0, 1]);
    assert_eq!(script.instructions[0].opcode, Opcode::Lconst);
    assert_eq!(script.instructions[0].long, 17);
    assert_eq!(script.switches[0][&7], -1);
    for end in 0..bytes.len() {
        assert!(decode(&bytes[..end], ScriptId::new(23), source).is_err());
    }
    let last = bytes.len() - 2;
    bytes[last..].copy_from_slice(&u16::MAX.to_be_bytes());
    assert!(decode(&bytes, ScriptId::new(23), source).is_err());
}
