//! FACE-002 ordering ported from the pinned `RustOSRS` priority preparation oracle.
//! Depth buckets are supplied by camera preparation; priority is never a depth bias.
#[derive(Clone, Copy)]
pub(crate) struct FaceDepth {
    pub index: usize,
    pub priority: u8,
    pub depth: i32,
}
/// Reuses the caller's depth list and output storage on every camera update.
pub(crate) fn order(faces: &mut [FaceDepth], output: &mut Vec<usize>) {
    faces.sort_unstable_by(|a, b| b.depth.cmp(&a.depth).then(a.index.cmp(&b.index)));
    output.clear();
    let mut sums = [0_i64; 12];
    let mut counts = [0_u32; 12];
    for face in faces.iter() {
        sums[usize::from(face.priority)] += i64::from(face.depth);
        counts[usize::from(face.priority)] += 1;
    }
    let average = |a: usize, b: usize| {
        let count = counts[a] + counts[b];
        if count == 0 {
            0
        } else {
            (sums[a] + sums[b]) / i64::from(count)
        }
    };
    let thresholds = [average(1, 2), average(3, 4), average(6, 8)];
    // Priority 11 starts only after priority 10 is exhausted, even when it is farther away.
    let mut special = faces
        .iter()
        .filter(|f| f.priority == 10)
        .chain(faces.iter().filter(|f| f.priority == 11))
        .peekable();
    for priority in 0..10 {
        let threshold = match priority {
            0 => Some(thresholds[0]),
            3 => Some(thresholds[1]),
            5 => Some(thresholds[2]),
            _ => None,
        };
        if let Some(threshold) = threshold {
            while special
                .peek()
                .is_some_and(|face| i64::from(face.depth) > threshold)
            {
                if let Some(face) = special.next() {
                    output.push(face.index);
                }
            }
        }
        output.extend(
            faces
                .iter()
                .filter(|f| f.priority == priority)
                .map(|f| f.index),
        );
    }
    output.extend(special.map(|f| f.index));
}
#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn all_priority_bands_match_the_pinned_threshold_crossing_oracle() {
        // RustOSRS reference-fixtures/priority/all_0_11_threshold_crossing.{input,expected}.json
        let priorities = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11, 11, 11, 11];
        let depths = [
            65, 90, 70, 60, 40, 35, 30, 15, 10, 0, 100, 55, 25, 5, 95, 52, 22, 2,
        ];
        let mut faces: Vec<_> = priorities
            .into_iter()
            .zip(depths)
            .enumerate()
            .map(|(index, (priority, depth))| FaceDepth {
                index,
                priority,
                depth,
            })
            .collect();
        let mut output = vec![];
        order(&mut faces, &mut output);
        assert_eq!(
            output,
            [10, 0, 1, 2, 11, 3, 4, 12, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17]
        );
    }
    #[test]
    fn equal_depths_keep_original_face_order_and_empty_models_clear_the_plan() {
        let mut faces = [
            FaceDepth {
                index: 2,
                priority: 0,
                depth: 10,
            },
            FaceDepth {
                index: 0,
                priority: 0,
                depth: 10,
            },
        ];
        let mut output = vec![];
        order(&mut faces, &mut output);
        assert_eq!(output, [0, 2]);
        order(&mut [], &mut output);
        assert!(output.is_empty());
    }
}
