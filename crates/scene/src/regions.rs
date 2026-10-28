//! `RuneLite` GPU chunk groups. Classification is disposable scenery policy, never collision/state.
//! Source: Regions/RegionManager and resources/gpu-regions.txt; see NOTICE.
use crate::{Result, SceneError};

#[derive(Debug, Clone, Copy)]
struct Rectangle {
    group: u32,
    min: [i32; 2],
    max: [i32; 2],
}
#[derive(Debug)]
struct Groups(Vec<Rectangle>);
impl Groups {
    fn parse(source: &str) -> Result<Self> {
        if source.len() > 128 * 1024 {
            return Err(SceneError("GPU region resource exceeds budget".into()));
        }
        let mut rectangles = Vec::new();
        let mut cursor_min = [0_i32; 2];
        let mut cursor_max = cursor_min;
        let mut group = 0_u32;
        for (line, text) in source.lines().enumerate() {
            let text = text.split_once("//").map_or(text, |(before, _)| before);
            let mut input = text.as_bytes();
            let error = || {
                SceneError(format!(
                    "GPU region resource line {}: invalid expression",
                    line + 1
                ))
            };
            while !input.is_empty() {
                skip_space(&mut input);
                if input.is_empty() {
                    break;
                }
                let operation = input[0];
                input = &input[1..];
                let mut number = |signed| read_number(&mut input, signed).ok_or_else(error);
                match operation {
                    b'n' => group = group.checked_add(1).ok_or_else(error)?,
                    b'm' => {
                        cursor_min = [number(false)?, number(false)?];
                        cursor_max = cursor_min;
                    }
                    b'r' | b'R' | b'c' | b'C' => {
                        if operation == b'r' {
                            cursor_min = [number(false)?, number(false)?];
                            cursor_max = cursor_min;
                        } else if operation == b'R' {
                            cursor_min = [number(false)?, number(false)?];
                            cursor_max = [number(false)?, number(false)?];
                        }
                        let mut min = [0; 2];
                        let mut max = [0; 2];
                        for axis in 0..2 {
                            min[axis] = cursor_min[axis].checked_mul(8).ok_or_else(error)?;
                            max[axis] = cursor_max[axis]
                                .checked_mul(8)
                                .and_then(|v| v.checked_add(7))
                                .ok_or_else(error)?;
                        }
                        if matches!(operation, b'c' | b'C') {
                            let from = [number(true)?, number(true)?];
                            let to = if operation == b'C' {
                                [number(true)?, number(true)?]
                            } else {
                                from
                            };
                            for axis in 0..2 {
                                max[axis] = min[axis].checked_add(to[axis]).ok_or_else(error)?;
                                min[axis] = min[axis].checked_add(from[axis]).ok_or_else(error)?;
                            }
                        }
                        if min[0] > max[0] || min[1] > max[1] || rectangles.len() == 8192 {
                            return Err(error());
                        }
                        rectangles.push(Rectangle { group, min, max });
                    }
                    _ => return Err(error()),
                }
            }
        }
        Ok(Self(rectangles))
    }
    fn group(&self, chunk: [i32; 2]) -> u32 {
        self.0
            .iter()
            .find(|r| (0..2).all(|a| (r.min[a]..=r.max[a]).contains(&chunk[a])))
            .map_or(0, |r| r.group)
    }
}
fn skip_space(input: &mut &[u8]) {
    while input.first().is_some_and(u8::is_ascii_whitespace) {
        *input = &input[1..];
    }
}
fn read_number(input: &mut &[u8], signed: bool) -> Option<i32> {
    skip_space(input);
    let negative = signed && input.first() == Some(&b'-');
    if negative {
        *input = &input[1..];
    }
    let mut digits = 0;
    let mut value = 0_i32;
    while let Some(&byte) = input.first().filter(|byte| byte.is_ascii_digit()) {
        value = value.checked_mul(10)?.checked_add(i32::from(byte - b'0'))?;
        *input = &input[1..];
        digits += 1;
    }
    (digits > 0).then_some(if negative { -value } else { value })
}

/// Immutable chunk admission for one map asset. Instances bypass global world-map grouping.
#[derive(Debug)]
pub struct RelatedMaps {
    origin: [i32; 2],
    size: [usize; 2],
    visible: Vec<bool>,
}
impl RelatedMaps {
    /// World-map base coordinates are the core scene base, before an expanded loading ring.
    /// # Errors
    /// Rejects invalid dimensions, arithmetic overflow or a malformed bundled group resource.
    pub fn for_world_map(base: [i32; 2], extent: [u32; 2]) -> Result<Self> {
        if extent.contains(&0) || extent.iter().any(|&side| side > 192) {
            return Err(SceneError("invalid related-map extent".into()));
        }
        let bounds_error = || SceneError("related-map coordinate overflow".into());
        let groups = Groups::parse(include_str!("../resources/gpu-regions.txt"))?;
        let center = base.map(|v| v / 8 + 6);
        let center_group = groups.group(center);
        let origin = base.map(|v| v.div_euclid(8));
        let mut size = [0; 2];
        for axis in 0..2 {
            let last = base[axis]
                .checked_add(i32::try_from(extent[axis] - 1).map_err(|_| bounds_error())?)
                .ok_or_else(|| SceneError("related-map coordinate overflow".into()))?;
            size[axis] = usize::try_from(last.div_euclid(8) - origin[axis] + 1)
                .map_err(|_| bounds_error())?;
        }
        let mut visible = Vec::with_capacity(size[0] * size[1]);
        for x in 0..size[0] {
            for y in 0..size[1] {
                let chunk = [
                    origin[0] + i32::try_from(x).map_err(|_| bounds_error())?,
                    origin[1] + i32::try_from(y).map_err(|_| bounds_error())?,
                ];
                visible.push(groups.group(chunk) == center_group);
            }
        }
        Ok(Self {
            origin,
            size,
            visible,
        })
    }
    /// Instance coordinates have no global region-group meaning. Instance asset assembly must use
    /// this bypass; normal-map loading does not implement instance-template reconstruction.
    #[must_use]
    pub fn for_instance() -> Self {
        Self {
            origin: [0; 2],
            size: [0; 2],
            visible: Vec::new(),
        }
    }
    #[must_use]
    pub fn contains(&self, world_tile: [i32; 2]) -> bool {
        if self.visible.is_empty() {
            return true;
        }
        let chunk = world_tile.map(|v| v.div_euclid(8));
        let [Some(x), Some(y)] = std::array::from_fn(|a| {
            chunk[a]
                .checked_sub(self.origin[a])
                .and_then(|v| usize::try_from(v).ok())
        }) else {
            return false;
        };
        x < self.size[0] && y < self.size[1] && self.visible[x * self.size[1] + y]
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn source_regions_case_and_bounded_parser_contract() {
        let groups = Groups::parse(include_str!("../resources/gpu-regions.txt")).unwrap();
        for (i, a) in groups.0.iter().enumerate() {
            for b in &groups.0[i + 1..] {
                assert!(
                    !(0..2).all(|axis| a.min[axis] <= b.max[axis] && a.max[axis] >= b.min[axis]),
                    "overlapping regions"
                );
            }
        }
        assert_eq!(groups.group([50 * 8, 50 * 8]), 0);
        assert_ne!(groups.group([2416 / 8, 4448 / 8]), 0);
        let groups = Groups::parse("n m 10 20 C -1 -2 1 2 // relative\nnr11 21").unwrap();
        assert_eq!(groups.group([79, 158]), 1);
        assert_eq!(groups.group([81, 162]), 1);
        assert_eq!(groups.group([88, 168]), 2);
        assert_eq!(groups.group([82, 162]), 0);
        for source in [
            "r 2",
            "C 3 0 2 0",
            "r -1 2",
            "r 2147483647 0",
            "x",
            "n m 1 2 c - 0",
        ] {
            assert!(Groups::parse(source).is_err(), "{source}");
        }
        assert!(RelatedMaps::for_instance().contains([2416, 4448]));
        let mask = RelatedMaps::for_world_map([2256, 4400], [104, 104]).unwrap();
        assert!(mask.contains([2320, 4448]));
        assert!(!mask.contains([2256, 4400]));
    }
}
