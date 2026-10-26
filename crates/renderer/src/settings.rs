//! One owner of the GPU plugin's rendering preferences and how this adapter honors them.
//!
//! `GpuSettings` is the typed projection of accepted `dev.rustclient.gpu` configuration; the composition
//! root converts schema values into it. `GpuSettings::resolve` reports, per option, what was requested and
//! what the adapter will actually do, so the shell can show a difference instead of pretending.
//! Semantics follow `RuneLite`'s `GpuPlugin` (`setupSyncMode`, `AntiAliasingMode`, `ColorBlindMode`).

use crate::MaterialOptions;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum AntiAliasing {
    Disabled,
    Msaa2,
    Msaa4,
    Msaa8,
    Msaa16,
}
impl AntiAliasing {
    #[must_use]
    pub const fn samples(self) -> u32 {
        match self {
            Self::Disabled => 1,
            Self::Msaa2 => 2,
            Self::Msaa4 => 4,
            Self::Msaa8 => 8,
            Self::Msaa16 => 16,
        }
    }
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ColorBlind {
    None,
    Protanopia,
    Deuteranopia,
    Tritanopia,
}
impl ColorBlind {
    /// Shader selector: `RuneLite`'s `PROTAN`/`DEUTERAN`/`TRITAN` defines, 0 meaning disabled.
    pub(crate) const fn shader_mode(self) -> u32 {
        match self {
            Self::None => 0,
            Self::Protanopia => 1,
            Self::Deuteranopia => 2,
            Self::Tritanopia => 3,
        }
    }
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum VsyncMode {
    Off,
    On,
    Adaptive,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct GpuSettings {
    /// Tiles; also the fog boundary. Geometry beyond it is not culled yet (see the ledger).
    pub draw_distance: u32,
    pub material: MaterialOptions,
    pub anti_aliasing: AntiAliasing,
    /// 0 disables fog; otherwise fade width in tiles.
    pub fog_depth: u32,
    /// 0 and 1 both mean no anisotropic filtering.
    pub anisotropy: u32,
    pub color_blind: ColorBlind,
    pub color_blind_intensity: u32,
    pub unlock_fps: bool,
    pub vsync: VsyncMode,
    pub fps_target: u32,
}
impl Default for GpuSettings {
    /// Source defaults from `GpuPluginConfig`.
    fn default() -> Self {
        Self {
            draw_distance: 50,
            material: MaterialOptions::default(),
            anti_aliasing: AntiAliasing::Msaa2,
            fog_depth: 0,
            anisotropy: 1,
            color_blind: ColorBlind::None,
            color_blind_intensity: 100,
            unlock_fps: true,
            vsync: VsyncMode::Off,
            fps_target: 60,
        }
    }
}

/// What the adapter and surface can do, gathered once at initialization.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct GpuCapabilities {
    /// Ascending MSAA sample counts the world color and depth formats both support (always contains 1).
    pub sample_counts: Vec<u32>,
    pub present_modes: Vec<wgpu::PresentMode>,
}
impl Default for GpuCapabilities {
    fn default() -> Self {
        Self {
            sample_counts: vec![1, 2, 4],
            present_modes: vec![wgpu::PresentMode::Fifo],
        }
    }
}

/// WebGPU guarantees only 1 and 4 samples; other counts need this adapter-specific feature.
const ADAPTER_FORMAT_FEATURES: wgpu::Features =
    wgpu::Features::TEXTURE_ADAPTER_SPECIFIC_FORMAT_FEATURES;

/// Device request that enables the optional features the GPU settings can use, when the adapter has them.
pub(crate) fn device_descriptor(adapter: &wgpu::Adapter) -> wgpu::DeviceDescriptor<'static> {
    wgpu::DeviceDescriptor {
        required_features: adapter.features() & ADAPTER_FORMAT_FEATURES,
        ..wgpu::DeviceDescriptor::default()
    }
}

/// Capabilities of a device created through [`device_descriptor`].
pub(crate) fn capabilities(
    adapter: &wgpu::Adapter,
    device: &wgpu::Device,
    present_modes: Vec<wgpu::PresentMode>,
) -> GpuCapabilities {
    let adapter_specific = device.features().contains(ADAPTER_FORMAT_FEATURES);
    let sample_counts = [1_u32, 2, 4, 8, 16]
        .into_iter()
        .filter(|count| {
            [
                crate::world_target::FORMAT,
                wgpu::TextureFormat::Depth32Float,
            ]
            .into_iter()
            .all(|format| {
                // Without the feature only the WebGPU-guaranteed counts are usable.
                matches!(count, 1 | 4)
                    || (adapter_specific
                        && adapter
                            .get_texture_format_features(format)
                            .flags
                            .sample_count_supported(*count))
            })
        })
        .collect();
    GpuCapabilities {
        sample_counts,
        present_modes,
    }
}

/// A requested option whose effective behavior differs.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct Deviation {
    pub key: &'static str,
    pub reason: String,
}

/// The resolved behavior that the renderer and frame pacing apply.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct EffectiveGpu {
    pub samples: u32,
    pub present_mode: wgpu::PresentMode,
    /// Frames per second the application should not exceed; 0 means uncapped.
    pub frame_cap: u32,
    pub anisotropy: u16,
    pub deviations: Vec<Deviation>,
}

/// The client's own timer drives drawing when FPS is locked: one frame per 20 ms game cycle.
const LOCKED_FPS: u32 = 50;

impl GpuSettings {
    #[must_use]
    pub fn resolve(&self, capabilities: &GpuCapabilities) -> EffectiveGpu {
        let mut deviations = Vec::new();

        // Prefer the nearest supported count that does not lose anti-aliasing the user asked for.
        let wanted = self.anti_aliasing.samples();
        let samples = capabilities
            .sample_counts
            .iter()
            .copied()
            .filter(|count| *count >= wanted)
            .min()
            .or_else(|| capabilities.sample_counts.iter().copied().max())
            .unwrap_or(1);
        if samples != wanted {
            deviations.push(Deviation {
                key: "antiAliasingMode",
                reason: format!(
                    "{wanted}x multisampling is unavailable here; the world renders with {samples}x."
                ),
            });
        }

        // Without unlocked FPS the client paces itself, as in RuneLite's setupSyncMode.
        let wanted_mode = if self.unlock_fps {
            self.vsync
        } else {
            VsyncMode::Off
        };
        let supports = |mode| capabilities.present_modes.contains(&mode);
        let present_mode = match wanted_mode {
            VsyncMode::On => wgpu::PresentMode::Fifo,
            VsyncMode::Adaptive if supports(wgpu::PresentMode::FifoRelaxed) => {
                wgpu::PresentMode::FifoRelaxed
            }
            VsyncMode::Adaptive => {
                deviations.push(Deviation {
                    key: "vsyncMode",
                    reason: "Adaptive vsync is unavailable on this surface; regular vsync is used."
                        .into(),
                });
                wgpu::PresentMode::Fifo
            }
            VsyncMode::Off if supports(wgpu::PresentMode::Immediate) => {
                wgpu::PresentMode::Immediate
            }
            VsyncMode::Off if supports(wgpu::PresentMode::Mailbox) => wgpu::PresentMode::Mailbox,
            VsyncMode::Off => {
                deviations.push(Deviation {
                    key: "vsyncMode",
                    reason: "This surface cannot present without vsync; regular vsync is used."
                        .into(),
                });
                wgpu::PresentMode::Fifo
            }
        };
        // RuneLite applies the FPS target only when the effective swap interval is 0.
        let frame_cap = match (self.unlock_fps, present_mode) {
            (false, _) => LOCKED_FPS,
            (true, wgpu::PresentMode::Immediate | wgpu::PresentMode::Mailbox) => self.fps_target,
            (true, _) => 0,
        };

        // wgpu clamps anisotropy to 1..=16; the schema already limits the request to 0..=16.
        let anisotropy = u16::try_from(self.anisotropy.clamp(1, 16)).unwrap_or(1);
        if anisotropy > 1 {
            deviations.push(Deviation {
                key: "anisotropicFilteringLevel",
                reason: "wgpu applies anisotropy only with linear filtering, so textures are also smoothed when magnified."
                    .into(),
            });
        }
        EffectiveGpu {
            samples,
            present_mode,
            frame_cap,
            anisotropy,
            deviations,
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    fn has(effective: &EffectiveGpu, key: &str) -> bool {
        effective
            .deviations
            .iter()
            .any(|deviation| deviation.key == key)
    }

    fn caps(samples: &[u32], modes: &[wgpu::PresentMode]) -> GpuCapabilities {
        GpuCapabilities {
            sample_counts: samples.to_vec(),
            present_modes: modes.to_vec(),
        }
    }

    #[test]
    fn source_defaults_resolve_to_two_samples_and_uncapped_target_sixty() {
        let effective = GpuSettings::default().resolve(&caps(
            &[1, 2, 4, 8],
            &[wgpu::PresentMode::Fifo, wgpu::PresentMode::Immediate],
        ));
        assert_eq!(effective.samples, 2);
        assert_eq!(effective.present_mode, wgpu::PresentMode::Immediate);
        assert_eq!(effective.frame_cap, 60);
        assert_eq!(effective.anisotropy, 1);
        assert!(effective.deviations.is_empty());
    }

    #[test]
    fn unsupported_sample_count_steps_down_and_says_why() {
        let settings = GpuSettings {
            anti_aliasing: AntiAliasing::Msaa16,
            ..GpuSettings::default()
        };
        let effective = settings.resolve(&caps(&[1, 2, 4], &[wgpu::PresentMode::Fifo]));
        assert_eq!(effective.samples, 4);
        // Browsers expose only 1 and 4: MSAA_2 keeps anti-aliasing at 4x instead of dropping it.
        let web = GpuSettings::default().resolve(&caps(&[1, 4], &[wgpu::PresentMode::Fifo]));
        assert_eq!(web.samples, 4);
        assert!(has(&effective, "antiAliasingMode"));
    }

    #[test]
    fn locked_fps_ignores_vsync_and_paces_on_the_game_cycle() {
        let settings = GpuSettings {
            unlock_fps: false,
            vsync: VsyncMode::On,
            ..GpuSettings::default()
        };
        let effective = settings.resolve(&caps(
            &[1],
            &[wgpu::PresentMode::Fifo, wgpu::PresentMode::Immediate],
        ));
        assert_eq!(effective.present_mode, wgpu::PresentMode::Immediate);
        assert_eq!(effective.frame_cap, 50);
    }

    #[test]
    fn vsync_on_disables_the_fps_target() {
        let settings = GpuSettings {
            vsync: VsyncMode::On,
            ..GpuSettings::default()
        };
        let effective = settings.resolve(&caps(&[1], &[wgpu::PresentMode::Fifo]));
        assert_eq!(effective.present_mode, wgpu::PresentMode::Fifo);
        assert_eq!(effective.frame_cap, 0);
    }

    #[test]
    fn missing_adaptive_and_immediate_modes_fall_back_with_a_reason() {
        let only_fifo = caps(&[1], &[wgpu::PresentMode::Fifo]);
        let adaptive = GpuSettings {
            vsync: VsyncMode::Adaptive,
            ..GpuSettings::default()
        };
        let effective = adaptive.resolve(&only_fifo);
        assert_eq!(effective.present_mode, wgpu::PresentMode::Fifo);
        assert!(has(&effective, "vsyncMode"));
        // Vsync off falls back too, and then the FPS target no longer applies.
        let off = GpuSettings::default().resolve(&only_fifo);
        assert_eq!(off.present_mode, wgpu::PresentMode::Fifo);
        assert_eq!(off.frame_cap, 0);
        assert!(has(&off, "vsyncMode"));
    }

    #[test]
    fn anisotropy_zero_and_one_both_mean_off() {
        for level in [0, 1] {
            let settings = GpuSettings {
                anisotropy: level,
                ..GpuSettings::default()
            };
            assert_eq!(settings.resolve(&GpuCapabilities::default()).anisotropy, 1);
        }
        let high = GpuSettings {
            anisotropy: 16,
            ..GpuSettings::default()
        }
        .resolve(&GpuCapabilities::default());
        assert_eq!(high.anisotropy, 16);
        assert!(has(&high, "anisotropicFilteringLevel"));
    }
}
