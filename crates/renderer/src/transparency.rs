//! Merge resident geometry streams into one alpha draw order without copying vertices.
use core::ops::Range;

#[derive(Debug, PartialEq, Eq)]
pub(crate) struct TransparentRun {
    pub source: usize,
    pub indices: Range<u32>,
}

/// Each source is already ordered by descending camera depth. Adjacent selections
/// from the same source coalesce into one indexed draw. Equal depths retain source order.
pub(crate) fn merge(depth: impl Fn(usize, u32) -> Option<f32>, runs: &mut Vec<TransparentRun>) {
    runs.clear();
    let mut cursor = [0_u32; 3];
    loop {
        let mut next: Option<(usize, f32)> = None;
        for (source, &index) in cursor.iter().enumerate() {
            if let Some(value) = depth(source, index)
                && next.is_none_or(|(_, current)| value.total_cmp(&current).is_gt())
            {
                next = Some((source, value));
            }
        }
        let Some((source, _)) = next else {
            break;
        };
        // Resident index counts were checked to fit u32 before metadata was installed.
        let first = cursor[source] * 3;
        cursor[source] += 1;
        if let Some(last) = runs.last_mut().filter(|last| last.source == source) {
            last.indices.end += 3;
        } else {
            runs.push(TransparentRun {
                source,
                indices: first..first + 3,
            });
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn alpha_streams_interleave_by_depth_with_stable_ties_and_coalesced_draws() {
        let depths: [&[f32]; 3] = [&[90., 50., 20.], &[80., 70., 20.], &[60., 20.]];
        let mut runs = vec![];
        merge(
            |source, index| depths[source].get(index as usize).copied(),
            &mut runs,
        );
        let order: Vec<_> = runs
            .iter()
            .flat_map(|run| {
                run.indices
                    .clone()
                    .step_by(3)
                    .map(move |i| (run.source, i / 3))
            })
            .collect();
        assert_eq!(
            order,
            [
                (0, 0),
                (1, 0),
                (1, 1),
                (2, 0),
                (0, 1),
                (0, 2),
                (1, 2),
                (2, 1)
            ]
        );
        assert_eq!(runs[1].indices, 0..6);
        merge(|_, _| None, &mut runs);
        assert!(runs.is_empty(), "scene shutdown drops the old draw plan");
    }
}
