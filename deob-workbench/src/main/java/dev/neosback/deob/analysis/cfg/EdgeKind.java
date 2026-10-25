package dev.neosback.deob.analysis.cfg;

public enum EdgeKind {
    FALLTHROUGH,
    CONDITIONAL_BRANCH,
    GOTO,
    SUBROUTINE,
    SWITCH_CASE,
    SWITCH_DEFAULT,
    EXCEPTION
}
