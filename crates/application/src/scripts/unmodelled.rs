//! Answers for server systems this client does not model yet: the friend system, friends chat,
//! clan profiles, the world list, the Grand Exchange and the oculus orb.
//!
//! Each answer is what the reference client reports while that system is empty or unloaded,
//! so interfaces that query it mount instead of aborting. When a system becomes authoritative
//! state, its answers move to the owning query module.
use super::{Result, ScriptHost};
use rustclient_cache::script::Opcode;
use rustclient_client_script::Stacks;
use rustclient_model::ScriptValue;
use rustclient_platform::ReadOnlyBlobs;

pub(super) fn instruction<S: ReadOnlyBlobs>(
    _h: &mut ScriptHost<'_, S>,
    op: Opcode,
    s: &mut Stacks,
) -> Result<bool> {
    let number = match op {
        // IGNORE_COUNT pushes -1 until the friend system has loaded (235 `Buddy`).
        Opcode::IgnoreCount => Some(-1),
        // CLANPROFILE_FIND finds no profile (235 `FillMode`); WORLDLIST_FETCH answers
        // `loadWorlds()`, false without a fetched list (235 `HealthBarUpdate`).
        Opcode::ClanprofileFind | Opcode::WorldlistFetch => Some(0),
        // MOBILE_WIFIAVAILABLE is 1 unconditionally (235 `HealthBarUpdate`).
        Opcode::MobileWifiavailable => Some(1),
        // No friends chat is joined: it has no owner name (235 `Buddy` `CLAN_GETCHATOWNERNAME`)
        // and no member comparator for 3644 to drop.
        Opcode::ClanGetchatownername => {
            s.push_object(ScriptValue::Text(String::new()));
            None
        }
        Opcode::FriendsChatClearSort => None,
        // 3129 sets the oculus orb's speeds; the orb camera is not implemented, so the operands
        // are consumed exactly as the reference does (deviation `OCULUS-ORB-SPEED`).
        Opcode::SetOculusOrbSpeed => {
            s.ints::<2>()?;
            None
        }
        Opcode::StockmarketGetoffertype
        | Opcode::StockmarketGetofferitem
        | Opcode::StockmarketGetofferprice
        | Opcode::StockmarketGetoffercount
        | Opcode::StockmarketGetoffercompletedcount
        | Opcode::StockmarketGetoffercompletedgold
        | Opcode::StockmarketIsofferempty
        | Opcode::StockmarketIsofferstable
        | Opcode::StockmarketIsofferfinished
        | Opcode::StockmarketIsofferadding => {
            // `Client.grandExchangeOffers` holds eight slots that start as empty offers (state
            // 0) and change only through UPDATE_STOCKMARKET_SLOT, which is not decoded yet. An
            // empty offer reports type, item, price and counts 0 and status 0, which is "empty"
            // (deviation `STOCKMARKET-EMPTY-OFFERS`).
            let slot = s.int()?;
            if !(0..8).contains(&slot) {
                return Err(format!("stock market slot {slot} out of range"));
            }
            Some(i32::from(op == Opcode::StockmarketIsofferempty))
        }
        _ => return Ok(false),
    };
    if let Some(number) = number {
        s.integers.push(number);
    }
    Ok(true)
}

#[cfg(test)]
mod tests {
    use super::{super::ClientScripts, *};
    use rustclient_game_state::{GameState, SemanticOperation};
    use rustclient_platform::MemoryBlobs;

    use super::super::test_support::scripts;
    fn state() -> GameState {
        let mut state = GameState::new();
        state
            .apply_operation(SemanticOperation::BeginSession {
                revision: rustclient_model::Revision::with_subrevision(240, 2),
            })
            .unwrap();
        state
    }
    fn run_with_text(
        scripts: &mut ClientScripts<MemoryBlobs>,
        state: &mut GameState,
        op: Opcode,
        ints: &[i32],
        _texts: &[&str],
    ) -> Result<Stacks> {
        let mut stacks = Stacks::default();
        stacks.integers.extend_from_slice(ints);
        let mut host = ScriptHost {
            resources: scripts,
            state,
        };
        assert!(
            instruction(&mut host, op, &mut stacks)?,
            "{op:?} is not handled here"
        );
        Ok(stacks)
    }
    fn run(
        scripts: &mut ClientScripts<MemoryBlobs>,
        state: &mut GameState,
        op: Opcode,
        ints: &[i32],
    ) -> Result<Vec<i32>> {
        run_with_text(scripts, state, op, ints, &[]).map(|s| s.integers)
    }

    #[test]
    fn unloaded_social_world_and_device_queries_answer_like_the_reference() {
        let (mut scripts, mut state) = (scripts(), state());
        let mut ask = |op, ints: &[i32]| run(&mut scripts, &mut state, op, ints).unwrap();
        assert_eq!(ask(Opcode::IgnoreCount, &[]), [-1]);
        assert_eq!(ask(Opcode::ClanprofileFind, &[]), [0]);
        assert_eq!(ask(Opcode::WorldlistFetch, &[]), [0]);
        assert_eq!(ask(Opcode::MobileWifiavailable, &[]), [1]);
        assert!(ask(Opcode::FriendsChatClearSort, &[]).is_empty());
        assert!(ask(Opcode::SetOculusOrbSpeed, &[12, 4]).is_empty());
        let mut owner = run_with_text(
            &mut scripts,
            &mut state,
            Opcode::ClanGetchatownername,
            &[],
            &[],
        )
        .unwrap();
        assert_eq!(owner.text().unwrap(), "");
    }

    #[test]
    fn stock_market_slots_are_empty_offers_and_bounded() {
        let (mut scripts, mut state) = (scripts(), state());
        for op in [
            Opcode::StockmarketGetoffertype,
            Opcode::StockmarketGetofferitem,
            Opcode::StockmarketGetofferprice,
            Opcode::StockmarketGetoffercount,
            Opcode::StockmarketGetoffercompletedcount,
            Opcode::StockmarketGetoffercompletedgold,
            Opcode::StockmarketIsofferstable,
            Opcode::StockmarketIsofferfinished,
            Opcode::StockmarketIsofferadding,
        ] {
            assert_eq!(
                run(&mut scripts, &mut state, op, &[7]).unwrap(),
                [0],
                "{op:?}"
            );
        }
        assert_eq!(
            run(
                &mut scripts,
                &mut state,
                Opcode::StockmarketIsofferempty,
                &[0]
            )
            .unwrap(),
            [1]
        );
        for slot in [-1, 8] {
            assert!(
                run(
                    &mut scripts,
                    &mut state,
                    Opcode::StockmarketIsofferempty,
                    &[slot]
                )
                .is_err()
            );
        }
    }
}
