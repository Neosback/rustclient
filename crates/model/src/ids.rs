//! Typed semantic identifiers.
//!
//! Definition/resource identifiers and runtime entity indexes are deliberately
//! different types. Revision-specific sentinel values are handled by adapters;
//! absence in semantic state is represented with `Option<T>`.

use core::fmt;

macro_rules! semantic_id {
    ($name:ident, $doc:literal) => {
        #[doc = $doc]
        #[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
        pub struct $name(u32);

        impl $name {
            /// Construct from the canonical non-negative numeric identity.
            #[must_use]
            pub const fn new(value: u32) -> Self {
                Self(value)
            }

            /// Return the canonical numeric identity.
            #[must_use]
            pub const fn get(self) -> u32 {
                self.0
            }
        }

        impl From<u32> for $name {
            fn from(value: u32) -> Self {
                Self::new(value)
            }
        }

        impl From<$name> for u32 {
            fn from(value: $name) -> Self {
                value.get()
            }
        }

        impl fmt::Display for $name {
            fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
                self.0.fmt(formatter)
            }
        }
    };
}

semantic_id!(ObjectDefinitionId, "Object/location definition identity.");
semantic_id!(NpcDefinitionId, "NPC definition identity.");
semantic_id!(ItemDefinitionId, "Item definition identity.");
semantic_id!(ModelId, "Model resource identity.");
semantic_id!(SpriteId, "Sprite resource identity.");
semantic_id!(TextureId, "Texture definition identity.");
semantic_id!(SequenceId, "Animation sequence identity.");
semantic_id!(SpotAnimationId, "Spot-animation/graphics identity.");
semantic_id!(VarpId, "Varp identity.");
semantic_id!(VarbitId, "Varbit identity.");
semantic_id!(WidgetId, "Widget/component identity.");
semantic_id!(InterfaceId, "Top-level interface identity.");
semantic_id!(ScriptId, "Client-script identity.");
semantic_id!(RegionId, "Cache/map-region identity.");

semantic_id!(PlayerIndex, "Runtime player slot/index, not a definition ID.");
semantic_id!(NpcIndex, "Runtime NPC slot/index, not an NPC definition ID.");

#[cfg(test)]
mod tests {
    use super::*;
    use std::collections::BTreeSet;

    #[test]
    fn ids_preserve_full_u32_range() {
        assert_eq!(ModelId::new(u32::MAX).get(), u32::MAX);
        assert_eq!(NpcDefinitionId::new(u32::MAX).get(), u32::MAX);
        assert_eq!(PlayerIndex::new(u32::MAX).get(), u32::MAX);
    }

    #[test]
    fn equal_numbers_do_not_imply_equal_semantic_types() {
        let definition = NpcDefinitionId::new(42);
        let runtime = NpcIndex::new(42);

        assert_eq!(definition.get(), runtime.get());
        assert_eq!(definition.to_string(), "42");
        assert_eq!(runtime.to_string(), "42");
    }

    #[test]
    fn identifiers_are_orderable_value_types() {
        let mut ids = BTreeSet::new();
        ids.insert(TextureId::new(9));
        ids.insert(TextureId::new(2));
        ids.insert(TextureId::new(9));

        assert_eq!(
            ids.into_iter().collect::<Vec<_>>(),
            vec![TextureId::new(2), TextureId::new(9)]
        );
    }
}
