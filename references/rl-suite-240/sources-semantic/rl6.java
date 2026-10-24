import net.runelite.api.MenuAction;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

// $VF: synthetic class
@ObfuscatedName("rl6")
class rl6 {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lup;)I")
   @ObfuscatedName("gf")
   public static int method10160(classUP var0) {
      int var2 = var0.field6360 * 931288241;
      return var2 >> 3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;Lrr;)V")
   @ObfuscatedName("rp")
   public static void method10161(NameableContainer var0, Nameable var1) {
      var0.field5841[(var0.field5840 += 2063367951) * 1744080879 - 1] = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;)I")
   @ObfuscatedName("ei")
   public static int method10162(WorldMapData var0) {
      return 1546846349 * var0.field2728.field2788;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzs;I)Lzs;")
   @ObfuscatedName("ey")
   public static ScriptEventBuilder method10163(ScriptEventBuilder var0, int var1) {
      if (var0 == null) {
         var0.method14229();
      }

      var0.field7255 = -1978668005 * var1;
      return var0;
   }

   static {
      try {
         field5802[MenuAction.WIDGET_TARGET.ordinal()] = 1;
      } catch (NoSuchFieldError var26) {
      }

      try {
         field5802[MenuAction.WIDGET_TARGET_ON_WIDGET.ordinal()] = 2;
      } catch (NoSuchFieldError var25) {
      }

      try {
         field5802[MenuAction.WIDGET_CONTINUE.ordinal()] = 3;
      } catch (NoSuchFieldError var24) {
      }

      try {
         field5802[MenuAction.CC_OP.ordinal()] = 4;
      } catch (NoSuchFieldError var23) {
      }

      try {
         field5802[MenuAction.CC_OP_LOW_PRIORITY.ordinal()] = 5;
      } catch (NoSuchFieldError var22) {
      }

      try {
         field5802[MenuAction.RUNELITE_WIDGET.ordinal()] = 6;
      } catch (NoSuchFieldError var21) {
      }

      try {
         field5802[MenuAction.ITEM_USE_ON_NPC.ordinal()] = 7;
      } catch (NoSuchFieldError var20) {
      }

      try {
         field5802[MenuAction.WIDGET_TARGET_ON_NPC.ordinal()] = 8;
      } catch (NoSuchFieldError var19) {
      }

      try {
         field5802[MenuAction.NPC_FIRST_OPTION.ordinal()] = 9;
      } catch (NoSuchFieldError var18) {
      }

      try {
         field5802[MenuAction.NPC_SECOND_OPTION.ordinal()] = 10;
      } catch (NoSuchFieldError var17) {
      }

      try {
         field5802[MenuAction.NPC_THIRD_OPTION.ordinal()] = 11;
      } catch (NoSuchFieldError var16) {
      }

      try {
         field5802[MenuAction.NPC_FOURTH_OPTION.ordinal()] = 12;
      } catch (NoSuchFieldError var15) {
      }

      try {
         field5802[MenuAction.NPC_FIFTH_OPTION.ordinal()] = 13;
      } catch (NoSuchFieldError var14) {
      }

      try {
         field5802[MenuAction.EXAMINE_NPC.ordinal()] = 14;
      } catch (NoSuchFieldError var13) {
      }

      try {
         field5802[MenuAction.WALK.ordinal()] = 15;
      } catch (NoSuchFieldError var12) {
      }

      try {
         field5802[MenuAction.ITEM_USE_ON_PLAYER.ordinal()] = 16;
      } catch (NoSuchFieldError var11) {
      }

      try {
         field5802[MenuAction.WIDGET_TARGET_ON_PLAYER.ordinal()] = 17;
      } catch (NoSuchFieldError var10) {
      }

      try {
         field5802[MenuAction.PLAYER_FIRST_OPTION.ordinal()] = 18;
      } catch (NoSuchFieldError var9) {
      }

      try {
         field5802[MenuAction.PLAYER_SECOND_OPTION.ordinal()] = 19;
      } catch (NoSuchFieldError var8) {
      }

      try {
         field5802[MenuAction.PLAYER_THIRD_OPTION.ordinal()] = 20;
      } catch (NoSuchFieldError var7) {
      }

      try {
         field5802[MenuAction.PLAYER_FOURTH_OPTION.ordinal()] = 21;
      } catch (NoSuchFieldError var6) {
      }

      try {
         field5802[MenuAction.PLAYER_FIFTH_OPTION.ordinal()] = 22;
      } catch (NoSuchFieldError var5) {
      }

      try {
         field5802[MenuAction.PLAYER_SIXTH_OPTION.ordinal()] = 23;
      } catch (NoSuchFieldError var4) {
      }

      try {
         field5802[MenuAction.PLAYER_SEVENTH_OPTION.ordinal()] = 24;
      } catch (NoSuchFieldError var3) {
      }

      try {
         field5802[MenuAction.PLAYER_EIGHTH_OPTION.ordinal()] = 25;
      } catch (NoSuchFieldError var2) {
      }

      try {
         field5802[MenuAction.RUNELITE_PLAYER.ordinal()] = 26;
      } catch (NoSuchFieldError var1) {
      }
   }
}
