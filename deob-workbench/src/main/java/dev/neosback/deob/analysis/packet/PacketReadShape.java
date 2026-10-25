package dev.neosback.deob.analysis.packet;

/** Structural byte-consumption shape only. This is not a semantic read-method name. */
public enum PacketReadShape {
    FIXED_ONE_BYTE,
    FIXED_TWO_BYTE,
    FIXED_FOUR_BYTE,
    FIXED_EIGHT_BYTE,
    VARIABLE_OR_LOOPING,
    UNKNOWN
}
