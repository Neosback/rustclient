//! Array-object semantics ported from the 240 client's `8000`-family handler (`fw.cs`).
use rustclient_client_script::{Object, Stacks};
use rustclient_model::ScriptValue;

fn ints(stacks: &mut Stacks, values: &[i32]) -> Object {
    stacks.push_object(ScriptValue::Integers(values.to_vec()));
    stacks.objects.pop().unwrap()
}

/// Reads every element through the public mutation API: remove each element, then put it back.
fn contents(stacks: &mut Stacks, array: &Object) -> Vec<i32> {
    let length = stacks.array_len(array).unwrap();
    (0..length)
        .map(|i| {
            let value = stacks.remove_array(array, i).unwrap();
            let ScriptValue::Int(v) = value.clone() else {
                panic!("integer array")
            };
            stacks.insert_array(array, i, value).unwrap();
            v
        })
        .collect()
}

#[test]
fn new_arrays_start_filled_and_growth_uses_the_same_fill() {
    let mut s = Stacks::default();
    let zero = s.new_array(false, 0, 2).unwrap();
    let minus = s.new_array(false, -1, 1).unwrap();
    s.resize_array(&zero, 4).unwrap();
    s.resize_array(&minus, 3).unwrap();
    assert_eq!(contents(&mut s, &zero), [0, 0, 0, 0]);
    assert_eq!(contents(&mut s, &minus), [-1, -1, -1]);
    s.resize_array(&minus, 1).unwrap();
    assert_eq!(s.array_len(&minus).unwrap(), 1);
}

#[test]
fn insert_remove_and_range_keep_order() {
    let mut s = Stacks::default();
    let a = ints(&mut s, &[10, 20, 30]);
    s.insert_array(&a, 3, ScriptValue::Int(40)).unwrap(); // push
    s.insert_array(&a, 1, ScriptValue::Int(15)).unwrap();
    assert_eq!(contents(&mut s, &a), [10, 15, 20, 30, 40]);
    assert_eq!(s.remove_array(&a, 2).unwrap(), ScriptValue::Int(20));
    s.remove_array_range(&a, 1, 2).unwrap(); // drops 15 and 30
    assert_eq!(contents(&mut s, &a), [10, 40]);
    s.remove_array_range(&a, 99, 0).unwrap(); // zero count is a no-op even out of range
    assert!(s.remove_array_range(&a, 1, 2).is_err());
    assert!(s.remove_array_range(&a, 0, -1).is_err());
    assert!(s.insert_array(&a, 3, ScriptValue::Int(1)).is_err());
    assert!(s.remove_array(&a, 2).is_err());
}

#[test]
fn append_and_insert_all_copy_the_source_in_place() {
    let mut s = Stacks::default();
    let a = ints(&mut s, &[1, 2]);
    let b = ints(&mut s, &[8, 9]);
    s.insert_all_array(&a, &b, 2).unwrap();
    s.insert_all_array(&a, &b, 1).unwrap();
    assert_eq!(contents(&mut s, &a), [1, 8, 9, 2, 8, 9]);
    assert_eq!(contents(&mut s, &b), [8, 9]);
    let empty = ints(&mut s, &[]);
    s.insert_all_array(&a, &empty, 0).unwrap();
    assert!(s.insert_all_array(&a, &b, 7).is_err());
    s.push_object(ScriptValue::Strings(vec!["x".into()]));
    let text = s.objects.pop().unwrap();
    assert!(
        s.insert_all_array(&a, &text, 0).is_err(),
        "element types must match"
    );
}

#[test]
fn read_only_and_null_arrays_reject_every_mutation() {
    let mut s = Stacks::default();
    let a = ints(&mut s, &[1, 2, 3]);
    s.freeze_array(&a);
    assert!(s.resize_array(&a, 3).is_err());
    assert!(s.insert_array(&a, 0, ScriptValue::Int(0)).is_err());
    assert!(s.remove_array(&a, 0).is_err());
    assert!(s.remove_array_range(&a, 0, 0).is_err());
    assert!(s.insert_all_array(&a, &a, 0).is_err());
    assert_eq!(s.array_len(&a).unwrap(), 3);
    assert!(s.resize_array(&Object::Null, 1).is_err());
    assert_eq!(s.array_len(&Object::Null).unwrap(), 0);
}

#[test]
fn arrays_are_bounded_and_typed() {
    let mut s = Stacks::default();
    assert!(s.new_array(false, 0, 5001).is_err());
    let a = s.new_array(true, 0, 1).unwrap();
    assert!(s.insert_array(&a, 0, ScriptValue::Int(1)).is_err());
    s.insert_array(&a, 1, ScriptValue::Text("b".into()))
        .unwrap();
    assert_eq!(
        s.remove_array(&a, 0).unwrap(),
        ScriptValue::Text(String::new())
    );
    let big = s.new_array(false, 0, 5000).unwrap();
    assert!(s.insert_array(&big, 0, ScriptValue::Int(1)).is_err());
    assert!(s.resize_array(&big, 5001).is_err());
}
