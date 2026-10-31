//! Explicit CPU preparation workload, separate from the manual visual parity gate.
use super::{
    Image, RenderError, TitleAssets, TitleViewport, UiFrame, UiImageId, Vertex, atlas,
    commands::{ImageLayout, PreparedUi},
};
use std::{collections::BTreeMap, hint::black_box, time::Instant};

/// CPU preparation samples only. Byte counts describe requested vertex uploads;
/// no GPU execution, submission or presentation time is measured here.
#[derive(Debug)]
pub struct UiPreparationMeasurement {
    pub retained: bool,
    pub iterations_per_sample: u32,
    pub samples_ns: Vec<u128>,
    pub prepared_quads: u64,
    pub retained_quads: u64,
    pub vertex_upload_bytes: u64,
    /// Command preparation frames eligible for raster reuse before texture/canvas checks.
    pub unchanged_geometry_frames: u64,
}

/// Measure fresh versus retained preparation of the same sealed frame, including
/// transient commands. Two warm-up batches and nine raw batches are returned.
/// The explicit diagnostic also checks painter order and invalidation/recovery.
/// It allocates comparison bytes outside the measured region and never mutates semantics.
/// # Errors
/// Reports invalid assets/commands, missing seals, or a retention contract failure.
pub fn measure_ui_preparation(
    assets: &TitleAssets,
    images: &BTreeMap<UiImageId, Image>,
    frame: &mut UiFrame,
    viewport: TitleViewport,
    iterations: u32,
) -> Result<Vec<UiPreparationMeasurement>, RenderError> {
    if iterations == 0 || iterations > 100_000 || frame.retained.is_none() {
        return Err(RenderError::Ui(
            "UI measurement needs a sealed frame and 1..100000 iterations",
        ));
    }
    let packed = atlas(assets, images, u32::MAX)?;
    let layout = ImageLayout {
        regions: &packed.regions,
        atlas: [packed.side; 2],
        dynamic: [[1, 1]; 4],
    };
    validate_retention(frame, layout, viewport)?;
    let mut measurements = Vec::new();
    for retained in [false, true] {
        let identity = if retained {
            None
        } else {
            frame.retained.take()
        };
        let result = sample(frame, layout, viewport, iterations, retained);
        if !retained {
            frame.retained = identity;
        }
        measurements.push(result?);
    }
    Ok(measurements)
}

fn sample(
    frame: &UiFrame,
    layout: ImageLayout<'_>,
    viewport: TitleViewport,
    iterations: u32,
    retained: bool,
) -> Result<UiPreparationMeasurement, RenderError> {
    let mut prepared = PreparedUi::default();
    let mut result = UiPreparationMeasurement {
        retained,
        iterations_per_sample: iterations,
        samples_ns: Vec::with_capacity(9),
        prepared_quads: 0,
        retained_quads: 0,
        vertex_upload_bytes: 0,
        unchanged_geometry_frames: 0,
    };
    for batch in 0..11 {
        let started = Instant::now();
        for _ in 0..iterations {
            let upload = prepared.prepare(black_box(frame), layout, viewport)?;
            if batch >= 2 {
                result.prepared_quads += u64::from(prepared.prepared_quads());
                result.retained_quads += u64::from(prepared.retained_quads());
                result.vertex_upload_bytes += (upload.len() * size_of::<Vertex>()) as u64;
                result.unchanged_geometry_frames += u64::from(prepared.unchanged());
            }
            black_box(&prepared.vertices[upload]);
        }
        if batch >= 2 {
            result.samples_ns.push(started.elapsed().as_nanos());
        }
    }
    Ok(result)
}

fn ordered_vertices(prepared: &PreparedUi) -> Vec<u8> {
    let mut bytes = Vec::new();
    for draw in prepared.draws() {
        let vertices = &prepared.vertices[draw.first as usize..(draw.first + draw.count) as usize];
        for vertex in vertices {
            bytes.push(draw.source as u8);
            bytes.extend_from_slice(bytemuck::bytes_of(vertex));
        }
    }
    bytes
}

fn compare_fresh(
    prepared: &mut PreparedUi,
    frame: &mut UiFrame,
    layout: ImageLayout<'_>,
    viewport: TitleViewport,
) -> Result<(), RenderError> {
    prepared.prepare(frame, layout, viewport)?;
    let identity = frame.retained.take();
    let mut fresh = PreparedUi::default();
    let result = fresh.prepare(frame, layout, viewport);
    frame.retained = identity;
    result?;
    if ordered_vertices(prepared) != ordered_vertices(&fresh) {
        return Err(RenderError::Ui(
            "retained UI painter order/vertices differ from fresh preparation",
        ));
    }
    Ok(())
}

fn validate_retention(
    frame: &mut UiFrame,
    mut layout: ImageLayout<'_>,
    viewport: TitleViewport,
) -> Result<(), RenderError> {
    let mut prepared = PreparedUi::default();
    for scale in [1., 1.25, 1.5, 1.75, 2.] {
        let view = TitleViewport { scale, ..viewport };
        compare_fresh(&mut prepared, frame, layout, view)?;
        compare_fresh(&mut prepared, frame, layout, view)?;
        let transient = frame.world_quads.len() + frame.quads.len()
            - frame
                .retained
                .as_ref()
                .map_or(0, rustclient_game_ui::RetainedUi::quad_count);
        if prepared.prepared_quads() as usize != transient {
            return Err(RenderError::Ui("unchanged widgets were prepared again"));
        }
    }
    let stretched = TitleViewport {
        stretch: Some(rustclient_game_ui::StretchedCanvas {
            canvas_height: 503,
            x: 15.,
            width: 1000.,
            height: 700.,
        }),
        ..viewport
    };
    compare_fresh(&mut prepared, frame, layout, stretched)?;
    compare_fresh(
        &mut prepared,
        frame,
        layout,
        TitleViewport {
            physical_width: 1280,
            physical_height: 720,
            ..viewport
        },
    )?;
    layout.dynamic[0] = [64, 64];
    compare_fresh(&mut prepared, frame, layout, viewport)?;
    let identity = frame.retained.take();
    frame.seal_widgets();
    let resealed = compare_fresh(&mut prepared, frame, layout, viewport);
    frame.retained = identity;
    resealed?;
    prepared.invalidate();
    compare_fresh(&mut prepared, frame, layout, viewport)?;
    // A transient lookup failure must not make a later retry reuse partial geometry.
    frame.image(
        UiImageId::Sprite(u32::MAX),
        rustclient_game_ui::Rect::new(0., 0., 1., 1.),
        rustclient_game_ui::Rect::new(0., 0., 1., 1.),
    );
    let failed = prepared.prepare(frame, layout, viewport).is_err();
    frame.quads.pop();
    if !failed {
        return Err(RenderError::Ui(
            "UI measurement expected an unknown asset failure",
        ));
    }
    compare_fresh(&mut prepared, frame, layout, viewport)?;
    validate_raster_reuse(frame, layout, viewport)?;
    Ok(())
}

fn validate_raster_reuse(
    frame: &mut UiFrame,
    layout: ImageLayout<'_>,
    viewport: TitleViewport,
) -> Result<(), RenderError> {
    let base = frame
        .retained
        .as_ref()
        .ok_or(RenderError::Ui("missing widget seal"))?
        .quad_count();
    let tail = frame.quads.split_off(base);
    let world = std::mem::take(&mut frame.world_quads);
    let result = (|| {
        let mut prepared = PreparedUi::default();
        prepared.prepare(frame, layout, viewport)?;
        if prepared.unchanged() {
            return Err(RenderError::Ui(
                "first UI preparation must require rasterization",
            ));
        }
        prepared.prepare(frame, layout, viewport)?;
        if !prepared.unchanged() {
            return Err(RenderError::Ui("unchanged static UI is not reusable"));
        }
        let rect = rustclient_game_ui::Rect::new(0., 0., 1., 1.);
        frame.image(UiImageId::White, rect, rect);
        let overlay = prepared.prepare(frame, layout, viewport);
        frame.quads.pop();
        overlay?;
        if prepared.unchanged() {
            return Err(RenderError::Ui("transient UI must require rasterization"));
        }
        prepared.prepare(frame, layout, viewport)?;
        if prepared.unchanged() {
            return Err(RenderError::Ui(
                "removed overlay must be erased by rasterization",
            ));
        }
        prepared.prepare(frame, layout, viewport)?;
        if !prepared.unchanged() {
            return Err(RenderError::Ui(
                "static UI did not recover raster eligibility",
            ));
        }
        Ok(())
    })();
    frame.quads.extend(tail);
    frame.world_quads = world;
    result
}
