// TSPS custom binary framing; source pin and attribution: tests/fixtures/tsps-241/PROVENANCE.md.
pub(super) const SERVER_LENGTHS: [i32; 256] = [
    8,  // 0: WELCOME
    8,  // 1: TICK
    -1, // 2: HANDSHAKE
    -1, // 3: LOGIN_RESPONSE
    -1, // 4: LOGOUT_RESPONSE
    -1, // 5: PATH_RESPONSE
    -2, // 6: PLUGIN_MESSAGE
    -3, // 7: unassigned
    -3, // 8: unassigned
    -3, // 9: unassigned
    -3, // 10: unassigned
    -3, // 11: unassigned
    -3, // 12: unassigned
    -3, // 13: unassigned
    -3, // 14: unassigned
    -3, // 15: unassigned
    -3, // 16: unassigned
    -3, // 17: unassigned
    -3, // 18: unassigned
    -3, // 19: unassigned
    -2, // 20: PLAYER_SYNC
    -2, // 21: NPC_INFO
    22, // 22: ANIM
    18, // 23: MOVEMENT_POSITION
    5,  // 24: NPC_ANIM_SPECIFIC
    9,  // 25: NPC_SPOTANIM_SPECIFIC
    7,  // 26: NPC_HEADICON_SPECIFIC
    -3, // 27: unassigned
    -3, // 28: unassigned
    -3, // 29: unassigned
    -3, // 30: unassigned
    -3, // 31: unassigned
    -3, // 32: unassigned
    -3, // 33: unassigned
    -3, // 34: unassigned
    -3, // 35: unassigned
    -3, // 36: unassigned
    -3, // 37: unassigned
    -3, // 38: unassigned
    -3, // 39: unassigned
    3,  // 40: VARP_SMALL
    6,  // 41: VARP_LARGE
    6,  // 42: VARBIT
    -1, // 43: VARP_BATCH
    10, // 44: VARP_LONG
    -3, // 45: unassigned
    -3, // 46: unassigned
    -3, // 47: unassigned
    -3, // 48: unassigned
    -3, // 49: unassigned
    -2, // 50: INVENTORY_SNAPSHOT
    -1, // 51: INVENTORY_SLOT
    -2, // 52: BANK_SNAPSHOT
    -1, // 53: BANK_SLOT
    -2, // 54: GROUND_ITEMS
    -2, // 55: GROUND_ITEMS_DELTA
    2,  // 56: INVENTORY_STOP_TRANSMIT
    -3, // 57: unassigned
    -3, // 58: unassigned
    -3, // 59: unassigned
    -3, // 60: unassigned
    -3, // 61: unassigned
    -3, // 62: unassigned
    -3, // 63: unassigned
    -3, // 64: unassigned
    -3, // 65: unassigned
    -3, // 66: unassigned
    -3, // 67: unassigned
    -3, // 68: unassigned
    -3, // 69: unassigned
    -1, // 70: SKILLS_SNAPSHOT
    -1, // 71: SKILLS_DELTA
    -3, // 72: unassigned
    -3, // 73: unassigned
    -3, // 74: unassigned
    -3, // 75: unassigned
    -3, // 76: unassigned
    -3, // 77: unassigned
    -3, // 78: unassigned
    -3, // 79: unassigned
    -1, // 80: COMBAT_STATE
    10, // 81: RUN_ENERGY
    -1, // 82: HITSPLAT
    -1, // 83: SPOT_ANIM
    -2, // 84: PROJECTILES
    -2, // 85: SPELL_RESULT
    -2, // 86: DEBUG_PACKET
    4,  // 87: DESTINATION
    -1, // 88: PLAYER_OPTION
    7,  // 89: HINT_ARROW
    1,  // 90: ATTACK_TIMER
    -3, // 91: unassigned
    -3, // 92: unassigned
    -3, // 93: unassigned
    -3, // 94: unassigned
    -3, // 95: unassigned
    -3, // 96: unassigned
    -3, // 97: unassigned
    -3, // 98: unassigned
    -3, // 99: unassigned
    3,  // 100: WIDGET_OPEN
    2,  // 101: WIDGET_CLOSE
    2,  // 102: WIDGET_SET_ROOT
    -2, // 103: WIDGET_OPEN_SUB
    4,  // 104: WIDGET_CLOSE_SUB
    -2, // 105: WIDGET_SET_TEXT
    5,  // 106: WIDGET_SET_HIDDEN
    10, // 107: WIDGET_SET_ITEM
    6,  // 108: WIDGET_SET_NPC_HEAD
    12, // 109: WIDGET_SET_FLAGS_RANGE
    -2, // 110: WIDGET_RUN_SCRIPT
    8,  // 111: WIDGET_SET_FLAGS
    16, // 112: WIDGET_SET_FLAGS_RANGE_V2
    10, // 113: WIDGET_SET_ANGLE
    6,  // 114: WIDGET_SET_ANIMATION
    4,  // 115: WIDGET_SET_PLAYER_HEAD
    8,  // 116: WIDGET_SET_ROTATE_SPEED
    8,  // 117: WIDGET_SET_MODEL
    8,  // 118: WIDGET_SET_POSITION
    6,  // 119: WIDGET_SET_COLOUR
    -2, // 120: CHAT_MESSAGE
    -2, // 121: FRIENDS_CHAT_UPDATE
    3,  // 122: CHAT_FILTER_SETTINGS
    0,  // 123: TRIGGER_ONDIALOGABORT
    -2, // 124: CLAN_SETTINGS_FULL
    -2, // 125: CLAN_CHANNEL_FULL
    -2, // 126: CLAN_CHANNEL_DELTA
    -2, // 127: CLAN_SETTINGS_DELTA
    6,  // 128: CLAN_VAR
    -3, // 129: unassigned
    -1, // 130: LOC_CHANGE
    -1, // 131: SOUND
    5,  // 132: PLAY_JINGLE
    10, // 133: PLAY_SONG
    -1, // 134: LOC_ADD_CHANGE
    -1, // 135: LOC_DEL
    10, // 136: LOC_ANIM
    -1, // 137: CAMERA_CONTROL
    18, // 138: LOC_MERGE
    -3, // 139: unassigned
    -2, // 140: REBUILD_REGION
    -2, // 141: REBUILD_NORMAL
    -2, // 142: REBUILD_WORLDENTITY
    -1, // 143: WORLDENTITY_INFO
    -2, // 144: REGION_REPLACEMENT
    4,  // 145: SET_INTERACTION_MODE
    2,  // 146: RESET_INTERACTION_MODE
    -2, // 147: REGION_REPLACEMENT_COMPRESSED
    1,  // 148: HIDE_NPC_OPS
    1,  // 149: HIDE_OBJ_OPS
    -2, // 150: SHOP_OPEN
    -1, // 151: SHOP_SLOT
    0,  // 152: SHOP_CLOSE
    -1, // 153: SHOP_MODE
    -1, // 154: TRADE_REQUEST
    -2, // 155: TRADE_OPEN
    -2, // 156: TRADE_UPDATE
    -1, // 157: TRADE_CLOSE
    -3, // 158: unassigned
    -3, // 159: unassigned
    -3, // 160: unassigned
    -3, // 161: unassigned
    -3, // 162: unassigned
    -3, // 163: unassigned
    -3, // 164: unassigned
    -3, // 165: unassigned
    -3, // 166: unassigned
    -3, // 167: unassigned
    -3, // 168: unassigned
    -3, // 169: unassigned
    -2, // 170: RUN_CLIENT_SCRIPT
    -3, // 171: unassigned
    -3, // 172: unassigned
    -3, // 173: unassigned
    -3, // 174: unassigned
    -3, // 175: unassigned
    -3, // 176: unassigned
    -3, // 177: unassigned
    -3, // 178: unassigned
    -3, // 179: unassigned
    -2, // 180: SMITHING_OPEN
    5,  // 181: SMITHING_MODE
    0,  // 182: SMITHING_CLOSE
    -3, // 183: unassigned
    -3, // 184: unassigned
    -3, // 185: unassigned
    -3, // 186: unassigned
    -3, // 187: unassigned
    -3, // 188: unassigned
    -3, // 189: unassigned
    -2, // 190: COLLECTION_LOG_SNAPSHOT
    -3, // 191: unassigned
    -3, // 192: unassigned
    -3, // 193: unassigned
    -3, // 194: unassigned
    -3, // 195: unassigned
    -3, // 196: unassigned
    -3, // 197: unassigned
    -3, // 198: unassigned
    -3, // 199: unassigned
    -1, // 200: NOTIFICATION
    -3, // 201: unassigned
    -3, // 202: unassigned
    -3, // 203: unassigned
    -3, // 204: unassigned
    -3, // 205: unassigned
    -3, // 206: unassigned
    -3, // 207: unassigned
    -3, // 208: unassigned
    -3, // 209: unassigned
    -2, // 210: GAMEMODE_DATA
    -3, // 211: unassigned
    -3, // 212: unassigned
    -3, // 213: unassigned
    -3, // 214: unassigned
    -3, // 215: unassigned
    -3, // 216: unassigned
    -3, // 217: unassigned
    -3, // 218: unassigned
    -3, // 219: unassigned
    4,  // 220: SYSTEM_UPDATE
    -3, // 221: unassigned
    -3, // 222: unassigned
    -3, // 223: unassigned
    -3, // 224: unassigned
    -3, // 225: unassigned
    -3, // 226: unassigned
    -3, // 227: unassigned
    -3, // 228: unassigned
    -3, // 229: unassigned
    -3, // 230: unassigned
    -3, // 231: unassigned
    -3, // 232: unassigned
    -3, // 233: unassigned
    -3, // 234: unassigned
    -3, // 235: unassigned
    -3, // 236: unassigned
    -3, // 237: unassigned
    -3, // 238: unassigned
    -3, // 239: unassigned
    -3, // 240: unassigned
    -3, // 241: unassigned
    -3, // 242: unassigned
    -3, // 243: unassigned
    -3, // 244: unassigned
    -3, // 245: unassigned
    -3, // 246: unassigned
    -3, // 247: unassigned
    -3, // 248: unassigned
    -3, // 249: unassigned
    -2, // 250: DEBUG
    -3, // 251: unassigned
    -3, // 252: unassigned
    -3, // 253: unassigned
    -3, // 254: unassigned
    -3, // 255: unassigned
];
pub(super) const CLIENT_LENGTHS: [i32; 256] = [
    -3, // 0
    16, // 1
    15, // 2
    -3, // 3
    -3, // 4
    -3, // 5
    3,  // 6
    -3, // 7
    -3, // 8
    2,  // 9
    3,  // 10
    8,  // 11
    3,  // 12
    4,  // 13
    8,  // 14
    -3, // 15
    7,  // 16
    -3, // 17
    -3, // 18
    8,  // 19
    8,  // 20
    3,  // 21
    -3, // 22
    8,  // 23
    -3, // 24
    8,  // 25
    -3, // 26
    -3, // 27
    7,  // 28
    -3, // 29
    -2, // 30
    8,  // 31
    11, // 32
    -3, // 33
    3,  // 34
    -3, // 35
    11, // 36
    20, // 37
    7,  // 38
    -3, // 39
    -3, // 40
    -3, // 41
    7,  // 42
    7,  // 43
    3,  // 44
    3,  // 45
    3,  // 46
    -3, // 47
    3,  // 48
    -3, // 49
    3,  // 50
    7,  // 51
    -3, // 52
    -3, // 53
    -3, // 54
    0,  // 55
    7,  // 56
    3,  // 57
    -3, // 58
    -3, // 59
    -3, // 60
    -3, // 61
    6,  // 62
    8,  // 63
    -3, // 64
    11, // 65
    -3, // 66
    -3, // 67
    -3, // 68
    8,  // 69
    3,  // 70
    -3, // 71
    -3, // 72
    3,  // 73
    -3, // 74
    11, // 75
    3,  // 76
    -3, // 77
    -3, // 78
    15, // 79
    -3, // 80
    -3, // 81
    7,  // 82
    -3, // 83
    8,  // 84
    2,  // 85
    15, // 86
    -3, // 87
    -3, // 88
    10, // 89
    16, // 90
    9,  // 91
    -3, // 92
    -3, // 93
    15, // 94
    -3, // 95
    7,  // 96
    -3, // 97
    -3, // 98
    -3, // 99
    -3, // 100
    -3, // 101
    7,  // 102
    7,  // 103
    6,  // 104
    4,  // 105
    -3, // 106
    -3, // 107
    -3, // 108
    -3, // 109
    -3, // 110
    -3, // 111
    -3, // 112
    -3, // 113
    -3, // 114
    -3, // 115
    -3, // 116
    -3, // 117
    -3, // 118
    -3, // 119
    -3, // 120
    -3, // 121
    -3, // 122
    -3, // 123
    -3, // 124
    -3, // 125
    -3, // 126
    -3, // 127
    -3, // 128
    -3, // 129
    -3, // 130
    -3, // 131
    -3, // 132
    -3, // 133
    -3, // 134
    -3, // 135
    -3, // 136
    -3, // 137
    -3, // 138
    -3, // 139
    -3, // 140
    -3, // 141
    -3, // 142
    -3, // 143
    -3, // 144
    -3, // 145
    -3, // 146
    -3, // 147
    -3, // 148
    -3, // 149
    -3, // 150
    -3, // 151
    -3, // 152
    -3, // 153
    -3, // 154
    -3, // 155
    -3, // 156
    -3, // 157
    -3, // 158
    -3, // 159
    -3, // 160
    -3, // 161
    -3, // 162
    -3, // 163
    -3, // 164
    -3, // 165
    -3, // 166
    -3, // 167
    -3, // 168
    -3, // 169
    -3, // 170
    -3, // 171
    -3, // 172
    -3, // 173
    -3, // 174
    -3, // 175
    -3, // 176
    -3, // 177
    -3, // 178
    -3, // 179
    -1, // 180
    -1, // 181
    -3, // 182
    -3, // 183
    -3, // 184
    -3, // 185
    -3, // 186
    -3, // 187
    -3, // 188
    -3, // 189
    -1, // 190
    6,  // 191
    4,  // 192
    -1, // 193
    -1, // 194
    -1, // 195
    -1, // 196
    -1, // 197
    3,  // 198
    -1, // 199
    -1, // 200
    4,  // 201
    -1, // 202
    0,  // 203
    -2, // 204
    -2, // 205
    -3, // 206
    -1, // 207
    -3, // 208
    -3, // 209
    5,  // 210
    -1, // 211
    5,  // 212
    -1, // 213
    1,  // 214
    12, // 215
    -3, // 216
    -3, // 217
    -3, // 218
    -3, // 219
    3,  // 220
    3,  // 221
    3,  // 222
    3,  // 223
    3,  // 224
    2,  // 225
    -3, // 226
    -3, // 227
    -3, // 228
    -3, // 229
    -3, // 230
    -1, // 231
    -1, // 232
    -1, // 233
    0,  // 234
    -3, // 235
    -3, // 236
    -3, // 237
    -3, // 238
    -3, // 239
    -1, // 240
    -1, // 241
    4,  // 242
    0,  // 243
    0,  // 244
    -1, // 245
    -1, // 246
    -3, // 247
    -3, // 248
    -3, // 249
    -1, // 250
    -1, // 251
    6,  // 252
    16, // 253
    3,  // 254
    -2, // 255
];
