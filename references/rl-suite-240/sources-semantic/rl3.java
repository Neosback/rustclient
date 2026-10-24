import net.runelite.api.MenuAction;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

// $VF: synthetic class
@ObfuscatedName("rl3")
class rl3 {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltt;I)I")
   @ObfuscatedName("th")
   public static int method10117(classTT var0, int var1) {
      if (var0 == null) {
         var0.method11219(var1);
      }

      return classTT.method11210(var0, var1, 120041229) ? var0.field6228[var1] : 0;
   }

   static {
      try {
         field5753[MenuAction.WIDGET_TARGET_ON_GAME_OBJECT.ordinal()] = 1;
      } catch (NoSuchFieldError var14) {
      }

      try {
         field5753[MenuAction.GAME_OBJECT_FIRST_OPTION.ordinal()] = 2;
      } catch (NoSuchFieldError var13) {
      }

      try {
         field5753[MenuAction.GAME_OBJECT_SECOND_OPTION.ordinal()] = 3;
      } catch (NoSuchFieldError var12) {
      }

      try {
         field5753[MenuAction.GAME_OBJECT_THIRD_OPTION.ordinal()] = 4;
      } catch (NoSuchFieldError var11) {
      }

      try {
         field5753[MenuAction.GAME_OBJECT_FOURTH_OPTION.ordinal()] = 5;
      } catch (NoSuchFieldError var10) {
      }

      try {
         field5753[MenuAction.GAME_OBJECT_FIFTH_OPTION.ordinal()] = 6;
      } catch (NoSuchFieldError var9) {
      }

      try {
         field5753[MenuAction.EXAMINE_OBJECT.ordinal()] = 7;
      } catch (NoSuchFieldError var8) {
      }

      try {
         field5753[MenuAction.WIDGET_TARGET_ON_GROUND_ITEM.ordinal()] = 8;
      } catch (NoSuchFieldError var7) {
      }

      try {
         field5753[MenuAction.GROUND_ITEM_FIRST_OPTION.ordinal()] = 9;
      } catch (NoSuchFieldError var6) {
      }

      try {
         field5753[MenuAction.GROUND_ITEM_SECOND_OPTION.ordinal()] = 10;
      } catch (NoSuchFieldError var5) {
      }

      try {
         field5753[MenuAction.GROUND_ITEM_THIRD_OPTION.ordinal()] = 11;
      } catch (NoSuchFieldError var4) {
      }

      try {
         field5753[MenuAction.GROUND_ITEM_FOURTH_OPTION.ordinal()] = 12;
      } catch (NoSuchFieldError var3) {
      }

      try {
         field5753[MenuAction.GROUND_ITEM_FIFTH_OPTION.ordinal()] = 13;
      } catch (NoSuchFieldError var2) {
      }

      try {
         field5753[MenuAction.EXAMINE_ITEM_GROUND.ordinal()] = 14;
      } catch (NoSuchFieldError var1) {
      }
   }
}
