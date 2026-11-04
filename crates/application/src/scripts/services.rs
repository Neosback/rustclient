//! Script queries over server-fed services this client does not receive yet.
//!
//! The reference client answers these from tables the server fills (stock market offers, the
//! ignore list, the friends chat channel) or from a world-list fetch. None of those packets is decoded
//! here, so every query reports the reference's own initial state rather than a neutral stub:
//! eight empty offers, an unloaded ignore list, no friends chat channel and a world list that is never
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
        ClanGetchatcount, ClanGetchatdisplayname, ClanGetchatminkick, ClanGetchatownername,
        ClanGetchatrank, ClanGetchatusername, ClanGetchatuserrank, ClanGetchatuserworld,
        ClanIsfriend, ClanIsself, ClanprofileFind, FriendschatApplysort, FriendschatClearsort,
        IgnoreCount, StockmarketGetoffercompletedcount, StockmarketGetoffercompletedgold,
        StockmarketGetoffercount, StockmarketGetofferitem, StockmarketGetofferprice,
        StockmarketGetoffertype, StockmarketIsofferadding, StockmarketIsofferempty,
        StockmarketIsofferfinished, StockmarketIsofferstable, WorldlistFetch,
    };
    match op {
        // `classHU.method6157`: -1 until the ignore list has loaded.
        IgnoreCount => s.integers.push(-1),
        // `classHU.method6157` 3644 clears the friends chat member sort comparator and 3655 sorts
        // the members by it, each only `if (classIB.field2933 != null)`. That field is the channel
        // `ClanGetchatownername` reads; none is ever joined, so there is nothing to clear or sort.
        FriendschatClearsort | FriendschatApplysort => {}
        // `classHU.method6157` 3611, 3625: the channel's display name and owner, the empty string
        // without a channel; 3613 reads a member's name by index and answers the same.
        ClanGetchatdisplayname | ClanGetchatownername => {
            s.push_object(ScriptValue::Text(String::new()));
        }
        ClanGetchatusername => {
            s.int()?;
            s.push_object(ScriptValue::Text(String::new()));
        }
        // `classHU.method6157` 3612, 3616, 3618: member count, minimum kick rank and own rank are
        // all 0 without a channel; 3614, 3615, 3624, 3626 take a member index and answer 0 too.
        // `classBB.method871` and `classKB.method6484`: no clan profile is held and no fetched
        // world list is ready, and nothing receives or fetches either.
        ClanGetchatcount | ClanGetchatminkick | ClanGetchatrank | ClanprofileFind
        | WorldlistFetch => s.integers.push(0),
        ClanGetchatuserworld | ClanGetchatuserrank | ClanIsself | ClanIsfriend => {
            s.int()?;
            s.integers.push(0);
        }
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
    fn friends_chat_sort_clear_is_a_no_op_without_a_channel() {
        let (mut scripts, mut state) = (scripts(), GameState::new());
        let (integers, text) =
            run(&mut scripts, &mut state, Opcode::FriendschatClearsort, &[]).unwrap();
        assert!(integers.is_empty() && text.is_empty());
        let (integers, text) =
            run(&mut scripts, &mut state, Opcode::FriendschatApplysort, &[]).unwrap();
        assert!(integers.is_empty() && text.is_empty());
    }

    #[test]
    fn channel_queries_answer_the_no_channel_state() {
        let (mut scripts, mut state) = (scripts(), GameState::new());
        let mut ints = |op, input: &[i32]| run(&mut scripts, &mut state, op, input).unwrap().0;
        for op in [
            Opcode::ClanGetchatcount,
            Opcode::ClanGetchatminkick,
            Opcode::ClanGetchatrank,
        ] {
            assert_eq!(ints(op, &[]), [0], "{op:?}");
        }
        for op in [
            Opcode::ClanGetchatuserworld,
            Opcode::ClanGetchatuserrank,
            Opcode::ClanIsself,
            Opcode::ClanIsfriend,
        ] {
            assert_eq!(ints(op, &[4]), [0], "{op:?}");
        }
        let (integers, text) =
            run(&mut scripts, &mut state, Opcode::ClanGetchatusername, &[2]).unwrap();
        assert!(integers.is_empty() && text.len() == 1);
        assert!(run(&mut scripts, &mut state, Opcode::ClanIsself, &[]).is_err());
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
