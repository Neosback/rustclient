//! Render profiles and capability negotiation (SPEC-0030, ADR-0009).
//!
//! A profile names what it needs from the device; [`negotiate`] compares that with the capabilities of
//! the device actually created. Profile ids are data, so a new HD profile needs no change here.
use crate::GpuCapabilities;

/// Stable id of a render profile.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct ProfileId(&'static str);
impl ProfileId {
    /// The classic path owned by the GPU plugin.
    pub const CLASSIC: Self = Self("classic");
    /// 117HD. Requirements are declared when its audit lands.
    pub const HD117: Self = Self("hd117");
    /// Reserved for the planned 2008HD profile.
    pub const HD2008: Self = Self("hd2008");
    #[must_use]
    pub const fn new(id: &'static str) -> Self {
        Self(id)
    }
    #[must_use]
    pub const fn as_str(self) -> &'static str {
        self.0
    }
}

/// What a profile needs from the device. `Default` is the WebGPU core baseline: nothing beyond it.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct ProfileRequirements {
    pub min_texture_dimension_2d: u32,
    pub min_texture_array_layers: u32,
    pub min_storage_buffers_per_stage: u32,
    pub min_storage_buffer_binding_bytes: u64,
    /// Compute workgroup storage and invocations are not queried: compute use is a feature gate.
    pub needs_compute: bool,
    pub required_features: wgpu::Features,
    /// Features that improve quality but have a fallback.
    pub optional_features: wgpu::Features,
}

/// The outcome of checking a profile against a device.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum Negotiation {
    /// Everything the profile asked for is available.
    Supported,
    /// The profile runs, but each listed optional capability is missing.
    Degraded(Vec<String>),
    /// The profile cannot run; the reason names the first unmet requirement.
    Unsupported(String),
}
impl Negotiation {
    #[must_use]
    pub const fn runs(&self) -> bool {
        !matches!(self, Self::Unsupported(_))
    }
}

/// Check `requirements` against the capabilities of the created device.
#[must_use]
pub fn negotiate(requirements: &ProfileRequirements, caps: &GpuCapabilities) -> Negotiation {
    let limits = &caps.device_limits;
    let at_least = |name: &str, have: u64, need: u64| {
        (have < need).then(|| format!("{name}: the device grants {have}, the profile needs {need}"))
    };
    let unmet = [
        at_least(
            "texture dimension",
            u64::from(limits.max_texture_dimension_2d),
            u64::from(requirements.min_texture_dimension_2d),
        ),
        at_least(
            "texture array layers",
            u64::from(limits.max_texture_array_layers),
            u64::from(requirements.min_texture_array_layers),
        ),
        at_least(
            "storage buffers per stage",
            u64::from(limits.max_storage_buffers_per_shader_stage),
            u64::from(requirements.min_storage_buffers_per_stage),
        ),
        at_least(
            "storage buffer binding bytes",
            limits.max_storage_buffer_binding_size,
            requirements.min_storage_buffer_binding_bytes,
        ),
    ]
    .into_iter()
    .flatten()
    .next();
    if let Some(reason) = unmet {
        return Negotiation::Unsupported(reason);
    }
    let missing_required = requirements.required_features - caps.enabled_features;
    if !missing_required.is_empty() {
        return Negotiation::Unsupported(format!(
            "required features are not enabled: {missing_required:?}"
        ));
    }
    let mut degraded = Vec::new();
    for (feature, _) in (requirements.optional_features - caps.enabled_features).iter_names() {
        degraded.push(format!("optional feature {feature} is not enabled"));
    }
    if requirements.needs_compute && limits.max_compute_invocations_per_workgroup == 0 {
        degraded.push("compute shaders are unavailable".to_owned());
    }
    if degraded.is_empty() {
        Negotiation::Supported
    } else {
        Negotiation::Degraded(degraded)
    }
}

/// The one active profile. Selecting replaces it and reports what was replaced.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ProfileSelection {
    active: ProfileId,
}
impl ProfileSelection {
    #[must_use]
    pub const fn new(active: ProfileId) -> Self {
        Self { active }
    }
    #[must_use]
    pub const fn active(&self) -> ProfileId {
        self.active
    }
    /// Make `next` active. `Some(previous)` when it replaced a different profile.
    pub fn select(&mut self, next: ProfileId) -> Option<ProfileId> {
        (next != self.active).then(|| std::mem::replace(&mut self.active, next))
    }
}
impl Default for ProfileSelection {
    fn default() -> Self {
        Self::new(ProfileId::CLASSIC)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    /// The WebGPU core guarantees: 8192 px textures, 256 array layers, 4 bind groups.
    fn webgpu_baseline() -> GpuCapabilities {
        GpuCapabilities {
            device_limits: wgpu::Limits {
                max_texture_dimension_2d: 8192,
                max_texture_array_layers: 256,
                max_storage_buffers_per_shader_stage: 8,
                max_storage_buffer_binding_size: 128 << 20,
                ..wgpu::Limits::downlevel_webgl2_defaults()
            },
            ..GpuCapabilities::default()
        }
    }

    #[test]
    fn the_classic_baseline_runs_on_the_webgpu_core() {
        assert_eq!(
            negotiate(&ProfileRequirements::default(), &webgpu_baseline()),
            Negotiation::Supported
        );
    }

    #[test]
    fn a_profile_needing_more_than_the_device_grants_is_unsupported_and_says_which_limit() {
        let needs = ProfileRequirements {
            min_texture_array_layers: 2048,
            ..ProfileRequirements::default()
        };
        let result = negotiate(&needs, &webgpu_baseline());
        let Negotiation::Unsupported(reason) = &result else {
            panic!("expected unsupported, got {result:?}");
        };
        assert!(
            reason.contains("256") && reason.contains("2048"),
            "{reason}"
        );
        assert!(!result.runs());
        // Feature requirements are checked against what the device enabled, not what exists.
        let needs = ProfileRequirements {
            required_features: wgpu::Features::TIMESTAMP_QUERY,
            ..ProfileRequirements::default()
        };
        assert!(matches!(
            negotiate(&needs, &webgpu_baseline()),
            Negotiation::Unsupported(reason) if reason.contains("TIMESTAMP_QUERY")
        ));
    }

    #[test]
    fn missing_optional_capabilities_degrade_the_profile_and_each_is_named() {
        let wants = ProfileRequirements {
            optional_features: wgpu::Features::SHADER_F16 | wgpu::Features::TIMESTAMP_QUERY,
            ..ProfileRequirements::default()
        };
        let mut caps = webgpu_baseline();
        let Negotiation::Degraded(reasons) = negotiate(&wants, &caps) else {
            panic!("expected degraded");
        };
        assert_eq!(reasons.len(), 2);
        assert!(reasons.iter().any(|r| r.contains("SHADER_F16")));
        // Granting one of them leaves one reason; granting both is fully supported.
        caps.enabled_features = wgpu::Features::SHADER_F16;
        let Negotiation::Degraded(reasons) = negotiate(&wants, &caps) else {
            panic!("expected degraded");
        };
        assert_eq!(reasons.len(), 1);
        caps.enabled_features |= wgpu::Features::TIMESTAMP_QUERY;
        assert_eq!(negotiate(&wants, &caps), Negotiation::Supported);
        assert!(Negotiation::Degraded(vec![]).runs());
    }

    #[test]
    fn selecting_a_profile_reports_the_one_it_replaced() {
        let mut selection = ProfileSelection::default();
        assert_eq!(selection.active(), ProfileId::CLASSIC);
        assert_eq!(selection.select(ProfileId::CLASSIC), None);
        assert_eq!(selection.select(ProfileId::HD117), Some(ProfileId::CLASSIC));
        assert_eq!(selection.active(), ProfileId::HD117);
        assert_eq!(selection.select(ProfileId::HD2008), Some(ProfileId::HD117));
        assert_eq!(ProfileId::HD117.as_str(), "hd117");
    }
}
