//! Script queries over server-fed services this client does not receive yet.
//!
//! The reference client answers these from tables the server fills (stock market offers, the
//! ignore list, the clan channel) or from a world-list fetch. None of those packets is decoded
//! here, so every query reports the reference's own initial state rather than a neutral stub:
//! eight empty offers, an unloaded ignore list, no clan channel and a world list that is never
//! ready. Each arm names the reference method it mirrors; when a packet starts filling one of
//! these tables, the arm moves to the `GameState` owner and reads it.
use super::{Result, ScriptHost};
use rustclient_cache::script::Opcode;
use rustclient_client_script::Stacks;
use rustclient_model::ScriptValue;
use rustclient_platform::ReadOnlyBlobs;
/// `classBD` fills `client.field865` with eight `GrandExchangeOffer`s on every login.
const STOCK_MARKET_OFFERS: i32 = 8;
pub(super) fn instruction<S: ReadOnlyBlobs>(
    _host: &mut ScriptHost<'_, S>,
    op: Opcode,
    s: &mut Stacks,
) -> Result<bool> {
    use Opcode::{
        ClanGetchatownername, ClanprofileFind, IgnoreCount, StockmarketGetoffercompletedcount,
        StockmarketGetoffercompletedgold, StockmarketGetoffercount, StockmarketGetofferitem,
        StockmarketGetofferprice, StockmarketGetoffertype, StockmarketIsofferadding,
        StockmarketIsofferempty, StockmarketIsofferfinished, StockmarketIsofferstable,
        WorldlistFetch,
    };
    match op {
        // `classHU.method6157`: -1 until the ignore list has loaded.
        IgnoreCount => s.integers.push(-1),
        // `classHU.method6157`: the empty string without a clan channel.
        ClanGetchatownername => s.push_object(ScriptValue::Text(String::new())),
        // `classGL.method5528`: every getter reads the offer in the given slot, and a new offer
        // has type 0, no item, price or counts and state 0 (empty).
        StockmarketGetoffertype
        | StockmarketGetofferitem
        | StockmarketGetofferprice
        | StockmarketGetoffercount
        | StockmarketGetoffercompletedcount
        | StockmarketGetoffercompletedgold
        | StockmarketIsofferempty
        | StockmarketIsofferstable
        | StockmarketIsofferfinished
        | StockmarketIsofferadding => {
            let slot = s.int()?;
            if !(0..STOCK_MARKET_OFFERS).contains(&slot) {
                return Err("stock market offer slot".into());
            }
            s.integers.push(i32::from(op == StockmarketIsofferempty));
        }
        // `classBB.method871` and `classKB.method6484`: no clan profile is held and no fetched
        // world list is ready, and nothing receives or fetches either.
        ClanprofileFind | WorldlistFetch => s.integers.push(0),
        _ => return Ok(false),
    }
    Ok(true)
}
#[cfg(test)]
mod tests {
    use super::super::{ClientScripts, test_support::scripts};
    use super::*;
    use rustclient_game_state::GameState;
    use rustclient_platform::MemoryBlobs;

    fn run(
        scripts: &mut ClientScripts<MemoryBlobs>,
        state: &mut GameState,
        op: Opcode,
        ints: &[i32],
    ) -> Result<(Vec<i32>, Vec<String>)> {
        let mut stacks = Stacks::default();
        stacks.integers.extend_from_slice(ints);
        let mut host = ScriptHost {
            resources: scripts,
            state,
        };
        assert!(instruction(&mut host, op, &mut stacks)?);
        let text = stacks.objects.iter().map(|o| format!("{o:?}")).collect();
        Ok((stacks.integers, text))
    }

    #[test]
    fn unloaded_services_report_the_reference_initial_state() {
        let (mut scripts, mut state) = (scripts(), GameState::new());
        let mut ask = |op, ints: &[i32]| run(&mut scripts, &mut state, op, ints).unwrap().0;
        assert_eq!(ask(Opcode::IgnoreCount, &[]), [-1]);
        assert_eq!(ask(Opcode::WorldlistFetch, &[]), [0]);
        for slot in 0..STOCK_MARKET_OFFERS {
            assert_eq!(ask(Opcode::StockmarketIsofferempty, &[slot]), [1]);
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
                assert_eq!(ask(op, &[slot]), [0], "{op:?} slot {slot}");
            }
        }
    }

    #[test]
    fn stock_market_slots_are_bounded() {
        let (mut scripts, mut state) = (scripts(), GameState::new());
        for slot in [-1, STOCK_MARKET_OFFERS] {
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

    #[test]
    fn clan_channel_owner_is_empty_without_a_channel() {
        let (mut scripts, mut state) = (scripts(), GameState::new());
        let (integers, text) =
            run(&mut scripts, &mut state, Opcode::ClanGetchatownername, &[]).unwrap();
        assert!(integers.is_empty());
        assert_eq!(text.len(), 1);
        assert!(text[0].contains("\"\""));
    }
}
