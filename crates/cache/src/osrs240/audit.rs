//! Read-only declared-file audit. Decoding is deliberately separate from execution parity.
mod stacks;
use crate::{Cache, CacheError, CacheProfile, IndexId, Source};
use rustclient_model::{
    InterfaceId, ItemDefinitionId, KitId, NpcDefinitionId, ObjectDefinitionId, ScriptId, SequenceId,
};
use rustclient_platform::ReadOnlyBlobs;
pub use stacks::{StackAudit, audit_stacks};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum AuditStatus {
    Decoded,
    Unsupported,
    Failed,
    Unchecked,
}
#[derive(Debug)]
pub struct AuditEntry {
    pub source: Source,
    pub status: AuditStatus,
    pub detail: String,
}
#[derive(Debug, Default)]
pub struct CacheAudit {
    pub entries: Vec<AuditEntry>,
    pub warnings: Vec<String>,
}
impl CacheProfile {
    /// Inventory every declared file in config, interface and script indexes, without executing it.
    /// Unknown config families remain unchecked. Missing/corrupt groups produce a row for every
    /// affected declared file. This is decoder evidence, not a gameplay acceptance test.
    #[must_use]
    pub fn audit_cache<S: ReadOnlyBlobs>(self, cache: &Cache<S>) -> CacheAudit {
        let mut report = CacheAudit::default();
        for index in [IndexId(2), IndexId(3), IndexId(12)] {
            let Some(table) = cache.tables().get(&index) else {
                report.warnings.push(format!("index {} absent", index.0));
                continue;
            };
            for group in table.groups.values() {
                let source = Source::group(index, group.id);
                if index == IndexId(2)
                    && !matches!(
                        group.id.0,
                        1 | 3 | 4 | 5 | 6 | 8 | 9 | 10 | 11 | 12 | 14 | 34
                    )
                {
                    for file in &group.files {
                        report.entries.push(entry(
                            Source {
                                file: Some(*file),
                                ..source
                            },
                            Ok(None),
                        ));
                    }
                    continue;
                }
                let loaded = if index == IndexId(2) {
                    // OpenRune publishes some advisory size hints; retain all such warnings.
                    let advisory = self == Self::OpenRune240
                        && matches!(group.id.0, 1 | 4 | 5 | 6 | 9 | 10 | 11 | 12 | 14);
                    cache.config_group_with(group.id.0, advisory, &mut report.warnings, |files| {
                        Ok(files.clone())
                    })
                } else {
                    cache
                        .group_files(index, group.id)
                        .map(|v| v.into_iter().collect())
                };
                match loaded {
                    Err(error) => {
                        for file in &group.files {
                            report.entries.push(entry(
                                Source {
                                    file: Some(*file),
                                    ..source
                                },
                                Err(error.clone()),
                            ));
                        }
                    }
                    Ok(files) => {
                        for file in &group.files {
                            let address = Source {
                                file: Some(*file),
                                ..source
                            };
                            let Some(bytes) = files.get(file) else {
                                report.entries.push(entry(
                                    address,
                                    Err(address.error("audit", "declared file absent")),
                                ));
                                continue;
                            };
                            let result = self.audit_file(cache, bytes, address);
                            report.entries.push(entry(address, result));
                        }
                        if index == IndexId(3) {
                            // A component can decode while its complete parent graph is invalid.
                            if let Err(error) =
                                self.load_interface(cache, InterfaceId::new(group.id.0))
                            {
                                report.warnings.push(format!("interface graph: {error}"));
                            }
                        }
                    }
                }
            }
        }
        report
    }
    fn audit_file<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        bytes: &[u8],
        source: Source,
    ) -> Result<Option<String>, CacheError> {
        let group = source.group.map_or(0, |v| v.0);
        let file = source.file.map_or(0, |v| v.0);
        match source.index.0 {
            2 => self.audit_config(cache, bytes, group, file, source),
            3 => super::widgets::decode(
                bytes,
                rustclient_model::WidgetId::new(InterfaceId::new(group), file),
                source,
            )
            .map(|_| {
                Some(
                    if bytes.first() == Some(&255) {
                        "IF3"
                    } else {
                        "IF1"
                    }
                    .into(),
                )
            })
            .map_err(|mut error| {
                error.detail = format!(
                    "{}; format={} kind={}",
                    error.detail,
                    if bytes.first() == Some(&255) {
                        "IF3"
                    } else {
                        "IF1"
                    },
                    bytes
                        .get(usize::from(bytes.first() == Some(&255)))
                        .copied()
                        .unwrap_or(255)
                );
                error
            }),
            12 => super::scripts::decode(bytes, ScriptId::new(group), source).and_then(|s| {
                let unknown = s
                    .instructions
                    .iter()
                    .filter(|i| matches!(i.opcode, crate::script::Opcode::Unsupported(_)))
                    .count();
                let detail = format!("stack: {:?}", audit_stacks(&s));
                if unknown == 0 {
                    Ok(Some(detail))
                } else {
                    Err(source.error(
                        "script coverage",
                        format!("unsupported instructions={unknown}; {detail}"),
                    ))
                }
            }),
            _ => unreachable!(),
        }
    }
    fn audit_config<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        bytes: &[u8],
        group: u32,
        id: u32,
        source: Source,
    ) -> Result<Option<String>, CacheError> {
        match group {
            1 | 4 => super::definitions::floor(bytes, group == 4, source).map(|_| ()),
            3 => super::definitions::kit(bytes, KitId::new(id), source).map(|_| ()),
            5 => self.inventory_capacity(cache, id).map(|_| ()),
            6 => super::definitions::object(bytes, ObjectDefinitionId::new(id), source).map(|_| ()),
            8 => self.load_script_enum(cache, id).map(|_| ()),
            9 => super::definitions::npc(bytes, NpcDefinitionId::new(id), source).map(|_| ()),
            10 => super::definitions::item(bytes, ItemDefinitionId::new(id), source).map(|_| ()),
            11 => self.parameter_default(cache, id).map(|_| ()),
            12 => super::animation::sequence(bytes, SequenceId::new(id), source).map(|_| ()),
            14 => super::animation::varbit(bytes, source).map(|_| ()),
            34 => self.script_struct(cache, id).map(|_| ()),
            _ => return Ok(None),
        }
        .map(|()| Some(String::new()))
    }
}
fn entry(source: Source, result: Result<Option<String>, CacheError>) -> AuditEntry {
    match result {
        Ok(Some(detail)) => AuditEntry {
            source,
            status: AuditStatus::Decoded,
            detail,
        },
        Ok(None) => AuditEntry {
            source,
            status: AuditStatus::Unchecked,
            detail: "config family outside decoder audit scope".into(),
        },
        Err(error) => AuditEntry {
            source,
            status: if error.detail.contains("unsupported") {
                AuditStatus::Unsupported
            } else {
                AuditStatus::Failed
            },
            detail: error.to_string(),
        },
    }
}
