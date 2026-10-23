import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dt")
public class classDT {
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("jc")
   static IndexedSprite[] field1628;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("af")
   final classVP field1622;
   @ObfuscatedName("ae")
   final int field1624;
   @ObfuscatedName("ab")
   int field1623 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field1626 = 13;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field1627 = 10000;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   public static final int field1625 = 85;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("ab")
   static final void method3614(String var0, byte var1) {
      try {
         String var2 = classKK.field3797 + var0 + classKK.field3798;
         classPH.method8924(30, "", var2, 1196754000);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dt.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)I")
   @ObfuscatedName("nt")
   public static int method3616(IndexDataBase var0) {
      return var0.field6440 * -442718787;
   }

   classDT(classVP var1, String var2) {
      this.field1622 = var1;
      this.field1624 = var1.method11905((byte)-73) * -461547673;
   }

   @ObfuscatedSignature(descriptor = "(I)[Lgt;")
   @ObfuscatedName("ag")
   public static classGT[] method3609(int var0) {
      try {
         return new classGT[]{classGT.field2679, classGT.field2678, classGT.field2677, classGT.field2676};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "dt.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxs;I)I")
   @ObfuscatedName("cy")
   public static int method3615(PacketBuffer var0, int var1) {
      return var1 - 702114061 * var0.field6953;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldt;)Z")
   @ObfuscatedName("ky")
   public static boolean method3610(classDT var0) {
      if (var0 == null) {
         return var0.method3611();
      } else {
         var0.field1623 = 0;

         for (int var1 = 0; var1 < -452275113 * var0.field1624; var1++) {
            if (!var0.field1622.method12113(var1, 1100671196) || var0.field1622.method12112(var1, 1525597176)) {
               var0.field1623 += 1394909781;
            }
         }

         return -1687991555 * var0.field1623 >= var0.field1624 * -452275113;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   boolean method3611() {
      this.field1623 = 0;

      for (int var1 = 0; var1 < -452275113 * this.field1624; var1++) {
         if (!this.field1622.method12113(var1, 1100671196) || this.field1622.method12112(var1, 1525597176)) {
            this.field1623 += 1394909781;
         }
      }

      return -1687991555 * this.field1623 >= this.field1624 * -452275113;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("az")
   boolean method3612(int var1) {
      try {
         this.field1623 = 0;

         for (int var2 = 0; var2 < -452275113 * this.field1624; var2++) {
            if (var1 == 1554777815) {
               throw new IllegalStateException();
            }

            if (this.field1622.method12113(var2, 1100671196)) {
               if (var1 == 1554777815) {
                  throw new IllegalStateException();
               }

               if (!this.field1622.method12112(var2, 1525597176)) {
                  continue;
               }
            }

            this.field1623 += 1394909781;
         }

         boolean var10000;
         if (-1687991555 * this.field1623 >= this.field1624 * -452275113) {
            if (var1 == 1554777815) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dt.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[Lkx;")
   @ObfuscatedName("ag")
   public static classKX[] method3613(byte var0) {
      try {
         return new classKX[]{classKX.field4047, classKX.field4045, classKX.field4049, classKX.field4044, classKX.field4048, classKX.field4046};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "dt.ag(" + ')');
      }
   }
}
