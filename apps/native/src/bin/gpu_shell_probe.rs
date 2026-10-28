//! Explicit GPU shell captures and CPU work/latency diagnostic; no server/account needed.
use rustclient_plugin_host::{configuration::Configuration, required::all_schemas};
use rustclient_renderer::{EguiShellLayer, SurfaceSize};
use rustclient_shell_ui::{EguiPanel, PluginPanel, ShellChannel, SidebarState};
fn click(panel: &mut EguiPanel, target: &str, time: f64) -> Result<(), Box<dyn std::error::Error>> {
    let [x, y, w, h] = panel.target_bounds(target).ok_or("missing UI target")?;
    panel.pointer(x + w / 2., y + h / 2., Some(true), true);
    panel.render(time)?;
    panel.pointer(x + w / 2., y + h / 2., Some(false), false);
    panel.render(time + 0.02)?;
    panel.render(time + 0.05)?;
    Ok(())
}
fn capture(
    panel: &EguiPanel,
    size: [u32; 2],
    scale: f32,
    name: &str,
) -> Result<(), Box<dyn std::error::Error>> {
    let frame = panel.frame();
    let layer = EguiShellLayer {
        jobs: &frame.jobs,
        textures: &frame.textures,
        size: SurfaceSize::new(size[0], size[1]),
        scale,
        generation: frame.generation,
        origin: [0, 0],
    };
    let run = || {
        pollster::block_on(rustclient_renderer::capture_gpu_shell(layer, |device| {
            let _ = device.poll(wgpu::PollType::wait_indefinitely());
        }))
    };
    let (pixels, stats) = run()?;
    assert_eq!(stats.paints, 1);
    assert_eq!(stats.geometry_uploads, 1);
    assert_eq!(pixels, run()?.0, "GPU reconstruction changed panel pixels");
    image::save_buffer(
        format!("target/gpu-shell-probe/{name}.png"),
        &pixels,
        size[0],
        size[1],
        image::ColorType::Rgba8,
    )?;
    println!("{name}: {stats:?}; fresh reconstruction identical");
    Ok(())
}
#[expect(
    clippy::cast_precision_loss,
    reason = "bounded 600-sample diagnostic and 1x/2x scales"
)]
fn main() -> Result<(), Box<dyn std::error::Error>> {
    std::fs::create_dir_all("target/gpu-shell-probe")?;
    for scale in [1_u32, 2] {
        let channel = ShellChannel::default();
        channel.model.borrow_mut().sidebar = SidebarState::Panel;
        for schema in all_schemas() {
            channel
                .model
                .borrow_mut()
                .plugins
                .push(PluginPanel::project(
                    &Configuration::new(schema,None)?,
                    |_| None,
                ));
        }
        let size = [278 * scale, 503 * scale];
        let mut panel = EguiPanel::new(channel, size, scale as f32)?;
        panel.set_visible(true);
        for index in 0..10 {
            panel.render(f64::from(index) * 0.2)?;
        }
        let initial = panel.stats();
        for index in 0..600 {
            panel.render(2. + f64::from(index) / 60.)?;
        }
        assert_eq!(
            panel.stats().passes,
            initial.passes,
            "unchanged world frames did UI work"
        );
        capture(&panel, size, scale as f32, &format!("plugins-{scale}x"))?;
        let first = panel
            .target_bounds("plugin:dev.rustclient.gpu")
            .ok_or("GPU row missing")?;
        let second = panel
            .target_bounds("plugin:dev.rustclient.runelite")
            .ok_or("RuneLite row missing")?;
        let mut samples = Vec::new();
        for index in 0..120 {
            let [x, y, w, h] = if index % 2 == 0 { first } else { second };
            let start = std::time::Instant::now();
            panel.pointer(x + w / 2., y + h / 2., None, false);
            panel.render(20. + f64::from(index) / 60.)?;
            samples.push(start.elapsed().as_secs_f64() * 1000.);
        }
        samples.sort_by(f64::total_cmp);
        println!(
            "{scale}x cross-row CPU: p50={:.4}ms p95={:.4}ms; {:?}",
            samples[60],
            samples[114],
            panel.stats()
        );
        click(&mut panel, "plugin:dev.rustclient.gpu", 30.)?;
        capture(&panel, size, scale as f32, &format!("gpu-{scale}x"))?;
        panel.set_visible(false);
        let before = panel.stats();
        for index in 0..600 {
            panel.render(40. + f64::from(index) / 60.)?;
        }
        assert_eq!(panel.stats().passes, before.passes, "hidden UI work");
    }
    Ok(())
}
