//! Requested destination lifecycle, separate from confirmed actor movement.
use crate::{GameState, OperationCommit, OperationError, SemanticEvent, StateDelta};
use rustclient_model::MapDestination;

impl GameState {
    #[must_use]
    pub const fn destination(&self) -> Option<MapDestination> {
        self.destination
    }

    pub(crate) fn apply_destination(
        &mut self,
        value: Option<MapDestination>,
    ) -> Result<OperationCommit, OperationError> {
        self.require_active()?;
        if self.destination == value {
            return Ok(OperationCommit::new(None, StateDelta::default()));
        }
        self.destination = value;
        Ok(OperationCommit::new(
            Some(SemanticEvent::DestinationChanged(value)),
            StateDelta::GAMEFRAME,
        ))
    }

    pub(crate) fn clear_reached_destination(&mut self) -> bool {
        let Some(destination) = self.destination else {
            return false;
        };
        let reached = self.entities.local_player().is_none_or(|player| {
            let position = player.path_queue.fine_position(player.tile);
            // Source actors use footprint-centred coordinates; path state keeps
            // the south-west corner. Normal players have a one-tile footprint.
            (position.x + 64).div_euclid(128) == i64::from(destination.x)
                && (position.y + 64).div_euclid(128) == i64::from(destination.y)
        });
        if reached {
            self.destination = None;
        }
        reached
    }
}
