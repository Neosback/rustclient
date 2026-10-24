import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xg")
public class classXG {
   @ObfuscatedName("az")
   public final int field6860;
   @ObfuscatedName("ab")
   public final Object[] field6862;
   @ToRemove(unused = "true")
   @ObfuscatedName("bo")
   public static final int field6866 = 65536;
   @ObfuscatedName("as")
   public final Object[] field6859;
   @ObfuscatedName("ae")
   public final long field6863;
   @ObfuscatedName("ag")
   public final Object[] field6864;
   @ToRemove(unused = "true")
   @ObfuscatedName("be")
   static final int field6865 = 94;
   @ObfuscatedName("af")
   public final int field6861;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([I)[Ljava/lang/Object;")
   @ObfuscatedName("ae")
   static Object[] method12818(int[] var0) {
      Object[] var1 = new Object[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         switch (FriendContainer.method10175(var0[var2], 1604958077).field7105 * -900901303) {
            case 0:
            case 1:
               var1[var2] = null;
               break;
            case 2:
               var1[var2] = classTM.method10983(var0[var2], -973324355);
               break;
            case 3:
               var1[var2] = (long)classTM.method10983(var0[var2], -973324355);
               break;
            case 4:
               var1[var2] = "";
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcg;)Z")
   @ObfuscatedName("eo")
   public static boolean method12822(MessageNode var0) {
      if (var0.field723 == classRC.field5638) {
         var0.method1473((byte)-106);
      }

      return classRC.field5640 == var0.field723;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([I)[Ljava/lang/Object;")
   @ObfuscatedName("af")
   static Object[] method12819(int[] var0) {
      Object[] var1 = new Object[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         switch (FriendContainer.method10175(var0[var2], 309706448).field7105 * -900901303) {
            case 0:
            case 1:
               var1[var2] = null;
               break;
            case 2:
               var1[var2] = classTM.method10983(var0[var2], -973324355);
               break;
            case 3:
               var1[var2] = (long)classTM.method10983(var0[var2], -973324355);
               break;
            case 4:
               var1[var2] = "";
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([I)[Ljava/lang/Object;")
   @ObfuscatedName("ag")
   static Object[] method12820(int[] var0) {
      Object[] var1 = new Object[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         switch (FriendContainer.method10175(var0[var2], 439512772).field7105 * -900901303) {
            case 0:
            case 1:
               var1[var2] = null;
               break;
            case 2:
               var1[var2] = classTM.method10983(var0[var2], -973324355);
               break;
            case 3:
               var1[var2] = (long)classTM.method10983(var0[var2], -973324355);
               break;
            case 4:
               var1[var2] = "";
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([I)[Ljava/lang/Object;")
   @ObfuscatedName("ab")
   static Object[] method12821(int[] var0) {
      Object[] var1 = new Object[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         switch (FriendContainer.method10175(var0[var2], -423916870).field7105 * 1560680972) {
            case 0:
            case 1:
               var1[var2] = null;
               break;
            case 2:
               var1[var2] = classTM.method10983(var0[var2], -973324355);
               break;
            case 3:
               var1[var2] = (long)classTM.method10983(var0[var2], -973324355);
               break;
            case 4:
               var1[var2] = "";
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgc;III)Z")
   @ObfuscatedName("ho")
   public static boolean method12823(CollisionData var0, int var1, int var2, int var3) {
      return 0 != (var0.field2548[var1][var2] & var3);
   }

   classXG(int var1, int var2, long var3) {
      classPC var5 = WorldView.method3733(var2, -1866817942);
      this.field6860 = -18007553 * var1;
      this.field6861 = var2 * -1747020391;
      this.field6863 = -3755125832428087081L * var3;
      this.field6859 = classLJ.method7153(var5.field5213, (byte)-71);
      this.field6862 = classLJ.method7153(var5.field5212, (byte)48);
      this.field6864 = classLJ.method7153(var5.field5211, (byte)-15);
   }
}
