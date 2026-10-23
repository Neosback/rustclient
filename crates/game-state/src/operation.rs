use crate::{
    AuthenticatedPlayer, ConnectionStateError, InitialPlacement, TitleScreen, TitleTransitionError,
};
use core::fmt;

use rustclient_model::{Revision, SceneDescriptor, TickSpan};

use crate::commit::{OperationCommit, SemanticEvent, StateDelta};
use crate::state::{GameState, LifecycleError, SessionLifecycle, TickAdvanceError};

/// Source-neutral semantic operation applied to the authoritative [`GameState`].
///
/// Operations describe requested domain changes after transport/UI-specific
/// details have already been translated away. They are not committed events.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum SemanticOperation {
    GameMessage(rustclient_model::GameMessage),
    ChatFilterText(String),
    ChatFilters([Option<u8>; 3]),
    Scenery(crate::SceneryOperation),
    PlayerStatus(rustclient_model::PlayerStatusUpdate),
    Inventory(rustclient_model::InventoryUpdate),
    Interface(rustclient_model::InterfaceUpdate),
    /// Authoritative player info tick update.
    UpdatePlayers(rustclient_model::PlayerInfoUpdate),
    /// Authoritative NPC info tick update.
    UpdateNpcs(rustclient_model::NpcInfoUpdate),
    /// Clear server-controlled action animations while retaining locomotion and appearance.
    ResetActorAnimations,
    /// Select or unselect active gameframe tab.
    SelectGameFrameTab(Option<usize>),
    /// Change non-secret pre-session title navigation/feedback.
    SetTitleScreen(TitleScreen),
    /// Begin a new semantic game session for an explicit compatibility revision.
    BeginSession {
        /// Protocol/cache compatibility revision selected by the caller.
        revision: Revision,
    },
    /// Establish an authenticated server session with explicit profile identity.
    AuthenticateSession {
        revision: Revision,
        player: AuthenticatedPlayer,
    },
    /// Establish the initial map/player placement after authentication.
    EstablishInitialPlacement(InitialPlacement),
    /// Select a new ordinary map window, retaining confirmed world entities.
    RebuildMap(rustclient_model::MapAnchor),
    /// Select a validated immutable scene after initial placement.
    InstallScene(SceneDescriptor),
    /// End the active semantic game session.
    EndSession,
    /// Advance deterministic semantic game time by the supplied span.
    AdvanceTick(TickSpan),
}

/// Failure while validating or applying one semantic operation.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum OperationError {
    Chat(crate::ChatError),
    /// Entity transition validation failed.
    EntityUpdate(crate::EntityUpdateError),
    Scenery(crate::SceneryError),
    Inventory(crate::InventoryError),
    Interface(crate::InterfaceError),
    /// Authenticated bootstrap transition failed.
    Connection(ConnectionStateError),
    /// Title navigation requires an inactive session.
    Title(TitleTransitionError),
    /// Session lifecycle validation failed.
    Lifecycle(LifecycleError),
    /// Deterministic game-time validation failed.
    TickAdvance(TickAdvanceError),
}

impl fmt::Display for OperationError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self {
            Self::Chat(error) => error.fmt(formatter),
            Self::EntityUpdate(error) => error.fmt(formatter),
            Self::Scenery(error) => error.fmt(formatter),
            Self::Inventory(error) => error.fmt(formatter),
            Self::Interface(error) => error.fmt(formatter),
            Self::Connection(error) => error.fmt(formatter),
            Self::Title(error) => error.fmt(formatter),
            Self::Lifecycle(error) => error.fmt(formatter),
            Self::TickAdvance(error) => error.fmt(formatter),
        }
    }
}

impl std::error::Error for OperationError {
    fn source(&self) -> Option<&(dyn std::error::Error + 'static)> {
        match self {
            Self::Chat(error) => Some(error),
            Self::EntityUpdate(error) => Some(error),
            Self::Scenery(error) => Some(error),
            Self::Inventory(error) => Some(error),
            Self::Interface(error) => Some(error),
            Self::Connection(error) => Some(error),
            Self::Title(error) => Some(error),
            Self::Lifecycle(error) => Some(error),
            Self::TickAdvance(error) => Some(error),
        }
    }
}

/// Failure while applying an ordered semantic-operation batch.
///
/// Every operation before `failed_index` has already committed successfully.
/// The failing operation does not mutate authoritative state, and later
/// operations in the slice are not attempted.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct OperationBatchError {
    failed_index: usize,
    error: OperationError,
}

impl OperationBatchError {
    /// Return the zero-based index of the first operation that failed.
    #[must_use]
    pub const fn failed_index(self) -> usize {
        self.failed_index
    }

    /// Return the typed error produced by the failing operation.
    #[must_use]
    pub const fn operation_error(self) -> OperationError {
        self.error
    }
}

impl fmt::Display for OperationBatchError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(
            formatter,
            "semantic operation batch failed at index {}: {}",
            self.failed_index, self.error
        )
    }
}

impl std::error::Error for OperationBatchError {
    fn source(&self) -> Option<&(dyn std::error::Error + 'static)> {
        Some(&self.error)
    }
}

impl GameState {
    /// Validate and apply exactly one source-neutral semantic operation.
    ///
    /// This is the public mutation gateway for semantic inputs. The dispatcher
    /// deliberately delegates to the crate's validated transition primitives so
    /// lifecycle/time rules have one implementation rather than being repeated.
    /// A successful return describes only facts and state domains that actually
    /// changed as a consequence of the committed mutation.
    ///
    /// # Errors
    ///
    /// Returns [`OperationError`] when the requested operation is invalid for
    /// the current state. Failed operations leave authoritative state unchanged
    /// and therefore produce no [`OperationCommit`].
    #[expect(
        clippy::too_many_lines,
        reason = "explicit semantic transition dispatch"
    )]
    pub fn apply_operation(
        &mut self,
        operation: SemanticOperation,
    ) -> Result<OperationCommit, OperationError> {
        match operation {
            SemanticOperation::ChatFilterText(text) => {
                self.require_active()?;
                if text.len() > 4096 {
                    return Err(OperationError::Chat(crate::ChatError));
                }
                let changed = self.chat.filter_text != text;
                self.chat.filter_text = text;
                Ok(if changed {
                    OperationCommit::new(Some(SemanticEvent::ChatSettingsChanged), StateDelta::CHAT)
                } else {
                    OperationCommit::new(None, StateDelta::default())
                })
            }
            SemanticOperation::ChatFilters(values) => {
                self.require_active()?;
                if values
                    .into_iter()
                    .zip([4, 2, 2])
                    .any(|(value, max)| value.is_some_and(|v| v > max))
                {
                    return Err(OperationError::Chat(crate::ChatError));
                }
                let next = std::array::from_fn(|i| values[i].unwrap_or(self.chat.filters[i]));
                let changed = self.chat.filters != next;
                self.chat.filters = next;
                Ok(if changed {
                    OperationCommit::new(Some(SemanticEvent::ChatSettingsChanged), StateDelta::CHAT)
                } else {
                    OperationCommit::new(None, StateDelta::default())
                })
            }
            SemanticOperation::GameMessage(message) => {
                self.require_active()?;
                let id = self
                    .chat
                    .push(message, self.scenery.cycle())
                    .map_err(OperationError::Chat)?;
                Ok(OperationCommit::new(
                    Some(SemanticEvent::ChatMessageAdded(id)),
                    StateDelta::CHAT,
                ))
            }
            SemanticOperation::PlayerStatus(update) => {
                self.require_active()?;
                let changed = self
                    .entities
                    .status(update)
                    .map_err(OperationError::EntityUpdate)?;
                Ok(if changed {
                    OperationCommit::new(Some(SemanticEvent::EntitiesUpdated), StateDelta::ENTITIES)
                } else {
                    OperationCommit::new(None, StateDelta::default())
                })
            }
            SemanticOperation::Scenery(op) => self.apply_scenery(op),
            SemanticOperation::Inventory(update) => self.apply_inventory(update),
            SemanticOperation::Interface(update) => self.apply_interface(update),
            SemanticOperation::UpdatePlayers(update) => self.apply_update_players(&update),
            SemanticOperation::UpdateNpcs(update) => self.apply_update_npcs(&update),
            SemanticOperation::ResetActorAnimations => {
                self.require_active()?;
                Ok(if self.entities.reset_animations() {
                    OperationCommit::new(Some(SemanticEvent::EntitiesUpdated), StateDelta::ENTITIES)
                } else {
                    OperationCommit::new(None, StateDelta::default())
                })
            }
            SemanticOperation::SelectGameFrameTab(tab) => self.apply_select_tab(tab),
            SemanticOperation::RebuildMap(anchor) => {
                let placement = self
                    .rebuild_map(anchor)
                    .map_err(OperationError::Connection)?;
                Ok(OperationCommit::new(
                    Some(SemanticEvent::MapRebuilt(placement)),
                    StateDelta::MAP_REBUILD,
                ))
            }
            SemanticOperation::InstallScene(scene) => {
                self.install_scene(scene)
                    .map_err(OperationError::Connection)?;
                Ok(OperationCommit::new(
                    Some(SemanticEvent::SceneInstalled(scene)),
                    StateDelta::SCENE,
                ))
            }
            SemanticOperation::AuthenticateSession { revision, player } => {
                let title_changed = self.title_screen() != TitleScreen::Welcome;
                self.authenticate_session(revision, player)
                    .map_err(OperationError::Lifecycle)?;
                Ok(OperationCommit::new(
                    Some(SemanticEvent::SessionAuthenticated { revision, player }),
                    StateDelta::session_boundary(title_changed),
                ))
            }
            SemanticOperation::EstablishInitialPlacement(placement) => {
                self.establish_placement(placement)
                    .map_err(OperationError::Connection)?;
                Ok(OperationCommit::new(
                    Some(SemanticEvent::InitialPlacementEstablished(placement)),
                    StateDelta::CONNECTION,
                ))
            }
            SemanticOperation::SetTitleScreen(screen) => {
                let previous = self
                    .set_title_screen(screen)
                    .map_err(OperationError::Title)?;
                if previous == screen {
                    Ok(OperationCommit::new(None, StateDelta::default()))
                } else {
                    Ok(OperationCommit::new(
                        Some(SemanticEvent::TitleScreenChanged {
                            previous,
                            current: screen,
                        }),
                        StateDelta::TITLE,
                    ))
                }
            }
            SemanticOperation::BeginSession { revision } => {
                let title_changed = self.title_screen() != TitleScreen::Welcome;
                self.begin_session(revision)
                    .map_err(OperationError::Lifecycle)?;
                Ok(OperationCommit::new(
                    Some(SemanticEvent::SessionBegan { revision }),
                    StateDelta::session_boundary(title_changed),
                ))
            }
            SemanticOperation::EndSession => {
                self.end_session().map_err(OperationError::Lifecycle)?;
                Ok(OperationCommit::new(
                    Some(SemanticEvent::SessionEnded),
                    StateDelta::session_boundary(false),
                ))
            }
            SemanticOperation::AdvanceTick(span) => {
                let (previous, current) = self
                    .advance_tick(span)
                    .map_err(OperationError::TickAdvance)?;

                if previous == current {
                    Ok(OperationCommit::new(None, StateDelta::default()))
                } else {
                    Ok(OperationCommit::new(
                        Some(SemanticEvent::GameTickAdvanced { previous, current }),
                        StateDelta::GAME_TICK,
                    ))
                }
            }
        }
    }

    fn apply_interface(
        &mut self,
        update: rustclient_model::InterfaceUpdate,
    ) -> Result<OperationCommit, OperationError> {
        self.require_active()?;
        let topology = matches!(
            &update,
            rustclient_model::InterfaceUpdate::SetRoot(_)
                | rustclient_model::InterfaceUpdate::Open(_)
                | rustclient_model::InterfaceUpdate::Close(_)
                | rustclient_model::InterfaceUpdate::Move { .. }
                | rustclient_model::InterfaceUpdate::Resync { .. }
                | rustclient_model::InterfaceUpdate::CloneChild { .. }
                | rustclient_model::InterfaceUpdate::CreateChild { .. }
                | rustclient_model::InterfaceUpdate::DeleteChild(_)
                | rustclient_model::InterfaceUpdate::DeleteChildren(_)
        );
        if self
            .interfaces
            .apply(update)
            .map_err(OperationError::Interface)?
        {
            if topology {
                self.interfaces.invalidate();
            }
            Ok(OperationCommit::new(
                Some(SemanticEvent::InterfacesChanged),
                StateDelta::INTERFACE,
            ))
        } else {
            Ok(OperationCommit::new(None, StateDelta::default()))
        }
    }

    fn apply_inventory(
        &mut self,
        update: rustclient_model::InventoryUpdate,
    ) -> Result<OperationCommit, OperationError> {
        self.require_active()?;
        let id = update.id();
        if self
            .inventories
            .apply(update)
            .map_err(OperationError::Inventory)?
        {
            Ok(OperationCommit::new(
                Some(SemanticEvent::InventoryChanged(id)),
                StateDelta::INVENTORY,
            ))
        } else {
            Ok(OperationCommit::new(None, StateDelta::default()))
        }
    }

    fn require_active(&self) -> Result<(), OperationError> {
        if self.lifecycle() == SessionLifecycle::Active {
            Ok(())
        } else {
            Err(OperationError::Lifecycle(LifecycleError::NotActive))
        }
    }

    fn apply_update_players(
        &mut self,
        update: &rustclient_model::PlayerInfoUpdate,
    ) -> Result<OperationCommit, OperationError> {
        self.require_active()?;
        self.entities
            .validate_player_info(update)
            .map_err(OperationError::EntityUpdate)?;
        let previous_plane = self.entities.local_player().map(|p| p.tile.plane);
        if !self.entities.apply_player_info(update) {
            return Ok(OperationCommit::new(None, StateDelta::default()));
        }
        if let Some(player) = self.entities.local_player()
            && Some(player.tile.plane) != previous_plane
        {
            self.change_map_plane(player.tile);
            return Ok(OperationCommit::new(
                Some(SemanticEvent::EntitiesUpdated),
                StateDelta::PLAYER_PLANE,
            ));
        }
        Ok(OperationCommit::new(
            Some(SemanticEvent::EntitiesUpdated),
            StateDelta::ENTITIES,
        ))
    }

    fn apply_update_npcs(
        &mut self,
        update: &rustclient_model::NpcInfoUpdate,
    ) -> Result<OperationCommit, OperationError> {
        self.require_active()?;
        let local_tile =
            self.entities
                .local_player()
                .map(|p| p.tile)
                .ok_or(OperationError::EntityUpdate(
                    crate::EntityUpdateError::MissingLocalPlayer,
                ))?;
        let origin = if let Some(offset) = update.origin {
            let anchor = self
                .initial_placement()
                .ok_or(OperationError::Connection(
                    ConnectionStateError::SceneRequiresPlacement,
                ))?
                .map_anchor;
            rustclient_model::WorldTile::new(
                anchor
                    .x
                    .checked_add(offset.x)
                    .ok_or(OperationError::EntityUpdate(
                        crate::EntityUpdateError::PositionOverflow,
                    ))?,
                anchor
                    .y
                    .checked_add(offset.y)
                    .ok_or(OperationError::EntityUpdate(
                        crate::EntityUpdateError::PositionOverflow,
                    ))?,
                local_tile.plane,
            )
        } else {
            local_tile
        };
        self.entities
            .validate_npc_info(update, origin)
            .map_err(OperationError::EntityUpdate)?;
        if !self.entities.apply_npc_info(update, origin) {
            return Ok(OperationCommit::new(None, StateDelta::default()));
        }
        Ok(OperationCommit::new(
            Some(SemanticEvent::EntitiesUpdated),
            StateDelta::ENTITIES,
        ))
    }

    fn apply_select_tab(&mut self, tab: Option<usize>) -> Result<OperationCommit, OperationError> {
        self.require_active()?;
        if self.selected_tab == tab {
            Ok(OperationCommit::new(None, StateDelta::default()))
        } else {
            self.selected_tab = tab;
            Ok(OperationCommit::new(
                Some(SemanticEvent::GameFrameTabSelected(tab)),
                StateDelta::GAMEFRAME,
            ))
        }
    }

    /// Apply an ordered batch of semantic operations from first to last.
    ///
    /// This convenience path preserves CP3D batch semantics while intentionally
    /// discarding commit metadata. Consumers that need committed events/deltas
    /// should call [`Self::apply_operation_batch_with_commits`].
    ///
    /// # Errors
    ///
    /// Returns [`OperationBatchError`] for the first failing operation. Earlier
    /// successful prefix mutations remain committed and later operations are not
    /// attempted.
    pub fn apply_operation_batch(
        &mut self,
        operations: &[SemanticOperation],
    ) -> Result<(), OperationBatchError> {
        self.apply_operation_batch_with_commits(operations, |_, _| {})
    }

    /// Apply an ordered batch and stream each successful commit in input order.
    ///
    /// `on_commit` runs immediately after each operation commits and receives
    /// that operation's zero-based input index plus its [`OperationCommit`]. No
    /// callback occurs for a failing operation or for any skipped suffix.
    /// This keeps batch application allocation-free while exposing exact commit
    /// order to future projection/replay consumers.
    ///
    /// # Errors
    ///
    /// Returns [`OperationBatchError`] containing the first failing operation's
    /// zero-based index and typed [`OperationError`]. Successful prefix commits
    /// have already been delivered to `on_commit` and remain authoritative.
    pub fn apply_operation_batch_with_commits<F>(
        &mut self,
        operations: &[SemanticOperation],
        mut on_commit: F,
    ) -> Result<(), OperationBatchError>
    where
        F: FnMut(usize, OperationCommit),
    {
        for (index, operation) in operations.iter().cloned().enumerate() {
            match self.apply_operation(operation) {
                Ok(commit) => on_commit(index, commit),
                Err(error) => {
                    return Err(OperationBatchError {
                        failed_index: index,
                        error,
                    });
                }
            }
        }

        Ok(())
    }
}

#[cfg(test)]
mod tests {
    use rustclient_model::{GameTick, REVISION_240, TickSpan};

    use super::*;
    use crate::SessionLifecycle;

    const BEGIN_240: SemanticOperation = SemanticOperation::BeginSession {
        revision: REVISION_240,
    };

    #[test]
    fn lifecycle_and_tick_changes_apply_through_one_public_gateway() {
        let mut state = GameState::new();

        state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))
            .expect("small tick advance cannot overflow");

        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::new(5)));

        state
            .apply_operation(SemanticOperation::EndSession)
            .expect("active state can end a session");
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
        assert_eq!(state.session_revision(), None);
        assert_eq!(state.current_tick(), None);
    }

    #[test]
    fn operation_commit_describes_only_committed_changes() {
        let mut state = GameState::new();

        let begin = state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        assert_eq!(
            begin.event(),
            Some(SemanticEvent::SessionBegan {
                revision: REVISION_240,
            })
        );
        assert!(begin.delta().lifecycle_changed());
        assert!(begin.delta().game_tick_changed());

        let advance = state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(3)))
            .expect("small tick advance cannot overflow");
        assert_eq!(
            advance.event(),
            Some(SemanticEvent::GameTickAdvanced {
                previous: GameTick::ZERO,
                current: GameTick::new(3),
            })
        );
        assert!(!advance.delta().lifecycle_changed());
        assert!(advance.delta().game_tick_changed());

        let end = state
            .apply_operation(SemanticOperation::EndSession)
            .expect("active state can end a session");
        assert_eq!(end.event(), Some(SemanticEvent::SessionEnded));
        assert!(end.delta().lifecycle_changed());
        assert!(end.delta().game_tick_changed());
    }

    #[test]
    fn zero_tick_span_commits_without_claiming_an_observable_change() {
        let mut state = GameState::new();
        state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");

        let commit = state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::ZERO))
            .expect("zero tick span is valid");

        assert_eq!(commit.event(), None);
        assert!(commit.delta().is_empty());
        assert_eq!(state.current_tick(), Some(GameTick::ZERO));
    }

    #[test]
    fn invalid_operation_preserves_authoritative_state_and_typed_error() {
        let mut state = GameState::new();
        let before_lifecycle = state.lifecycle();
        let before_revision = state.session_revision();
        let before_tick = state.current_tick();

        assert_eq!(
            state.apply_operation(SemanticOperation::AdvanceTick(TickSpan::ONE)),
            Err(OperationError::TickAdvance(
                TickAdvanceError::InactiveSession
            ))
        );
        assert_eq!(state.lifecycle(), before_lifecycle);
        assert_eq!(state.session_revision(), before_revision);
        assert_eq!(state.current_tick(), before_tick);
    }

    #[test]
    fn operation_gateway_preserves_checked_tick_overflow() {
        let mut state = GameState::new();
        state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(u64::MAX)))
            .expect("zero plus max is representable");

        let before_tick = state.current_tick();
        assert_eq!(
            state.apply_operation(SemanticOperation::AdvanceTick(TickSpan::ONE)),
            Err(OperationError::TickAdvance(TickAdvanceError::Overflow))
        );
        assert_eq!(state.current_tick(), before_tick);
    }

    #[test]
    fn identical_operation_sequences_produce_identical_observable_state() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::AdvanceTick(TickSpan::new(3)),
            SemanticOperation::EndSession,
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::ONE),
        ];

        let mut left = GameState::new();
        let mut right = GameState::new();

        for operation in operations {
            assert_eq!(
                left.apply_operation(operation.clone()),
                right.apply_operation(operation)
            );
        }

        assert_eq!(left.lifecycle(), right.lifecycle());
        assert_eq!(left.session_revision(), right.session_revision());
        assert_eq!(left.current_tick(), right.current_tick());
        assert_eq!(left.session_revision(), Some(REVISION_240));
        assert_eq!(left.current_tick(), Some(GameTick::new(1)));
    }

    #[test]
    fn operation_batch_applies_in_exact_slice_order() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::EndSession,
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(3)),
        ];
        let mut state = GameState::new();

        state
            .apply_operation_batch(&operations)
            .expect("ordered reference batch is valid");

        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::new(3)));
    }

    #[test]
    fn operation_batch_stops_at_first_failure_and_preserves_committed_prefix() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(5)),
        ];
        let mut state = GameState::new();

        let error = state
            .apply_operation_batch(&operations)
            .expect_err("second begin-session must fail");

        assert_eq!(error.failed_index(), 2);
        assert_eq!(
            error.operation_error(),
            OperationError::Lifecycle(LifecycleError::AlreadyActive)
        );
        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::new(2)));
    }

    #[test]
    fn batch_commit_stream_matches_successful_prefix_only() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            BEGIN_240,
            SemanticOperation::EndSession,
        ];
        let mut state = GameState::new();
        let mut commits = Vec::new();

        let error = state
            .apply_operation_batch_with_commits(&operations, |index, commit| {
                commits.push((index, commit));
            })
            .expect_err("second begin-session must fail");

        assert_eq!(error.failed_index(), 2);
        assert_eq!(commits.len(), 2);
        assert_eq!(commits[0].0, 0);
        assert_eq!(
            commits[0].1.event(),
            Some(SemanticEvent::SessionBegan {
                revision: REVISION_240,
            })
        );
        assert_eq!(commits[1].0, 1);
        assert_eq!(
            commits[1].1.event(),
            Some(SemanticEvent::GameTickAdvanced {
                previous: GameTick::ZERO,
                current: GameTick::new(2),
            })
        );
        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::new(2)));
    }

    #[test]
    fn empty_operation_batch_is_a_no_op() {
        let mut state = GameState::new();
        let before_lifecycle = state.lifecycle();
        let before_revision = state.session_revision();
        let before_tick = state.current_tick();

        state
            .apply_operation_batch(&[])
            .expect("empty batch is valid");

        assert_eq!(state.lifecycle(), before_lifecycle);
        assert_eq!(state.session_revision(), before_revision);
        assert_eq!(state.current_tick(), before_tick);
    }
}
