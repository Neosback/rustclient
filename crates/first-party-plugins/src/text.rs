//! The `net.runelite.client.util` text helpers the ports share (BSD-2, see `NOTICE`).

/// `Text.fromCSV`: split on commas, trim each piece and drop the empty ones.
pub(crate) fn from_csv(input: &str) -> Vec<&str> {
    input
        .split(',')
        .map(str::trim)
        .filter(|piece| !piece.is_empty())
        .collect()
}

/// `Text.removeTags`: drop every `<...>` run, tag contents included.
pub(crate) fn remove_tags(input: &str) -> String {
    let mut out = String::with_capacity(input.len());
    let mut rest = input;
    while let Some(open) = rest.find('<') {
        let Some(close) = rest[open..].find('>') else {
            break;
        };
        out.push_str(&rest[..open]);
        rest = &rest[open + close + 1..];
    }
    out.push_str(rest);
    out
}

/// `Text.sanitize`: keep what follows the last tag when the name carries an icon, and turn
/// non-breaking spaces into spaces.
pub(crate) fn sanitize(name: &str) -> String {
    let cleaned = if name.contains("<img") {
        name.rfind('>').map_or("", |at| &name[at + 1..])
    } else {
        name
    };
    cleaned.replace('\u{a0}', " ")
}

/// `WildcardMatcher.matches`: the whole of `text` must match `pattern`, where `*` stands for any run
/// of characters and everything else compares case-insensitively.
pub(crate) fn wildcard_matches(pattern: &str, text: &str) -> bool {
    let pattern = pattern.to_lowercase();
    let text = text.to_lowercase();
    let mut pieces = pattern.split('*');
    let first = pieces.next().unwrap_or_default();
    let Some(mut rest) = text.strip_prefix(first) else {
        return false;
    };
    let pieces: Vec<&str> = pieces.collect();
    let Some((last, middle)) = pieces.split_last() else {
        // No wildcard at all: the prefix check above consumed a candidate, it must be everything.
        return rest.is_empty();
    };
    for piece in middle {
        match rest.find(piece) {
            Some(at) => rest = &rest[at + piece.len()..],
            None => return false,
        }
    }
    rest.ends_with(last)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn csv_trims_and_drops_empty_pieces() {
        assert_eq!(from_csv(" a , b,, ,c "), ["a", "b", "c"]);
        assert!(from_csv("").is_empty());
    }

    #[test]
    fn sanitize_drops_an_icon_prefix_and_non_breaking_spaces() {
        assert_eq!(sanitize("<img=2>Zezima"), "Zezima");
        assert_eq!(sanitize("Two\u{a0}Words"), "Two Words");
        assert_eq!(sanitize("Plain"), "Plain");
    }

    #[test]
    fn tags_are_removed_with_their_contents() {
        assert_eq!(remove_tags("<col=ff0000>Man</col>"), "Man");
        assert_eq!(remove_tags("a < b"), "a < b");
        assert_eq!(remove_tags("<img=1>Name"), "Name");
    }

    #[test]
    fn wildcards_match_the_whole_string_ignoring_case() {
        assert!(wildcard_matches("goblin", "Goblin"));
        assert!(!wildcard_matches("goblin", "Goblin guard"));
        assert!(wildcard_matches("goblin*", "Goblin guard"));
        assert!(wildcard_matches("*guard", "Goblin guard"));
        assert!(wildcard_matches("g*n*d", "Goblin guard"));
        assert!(!wildcard_matches("g*x*d", "Goblin guard"));
        assert!(wildcard_matches("*", ""));
        assert!(wildcard_matches("**", "anything"));
        // The suffix cannot reuse characters the prefix consumed.
        assert!(!wildcard_matches("ab*ba", "aba"));
    }
}
