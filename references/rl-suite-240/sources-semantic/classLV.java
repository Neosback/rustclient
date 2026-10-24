import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lv")
public class classLV implements classXZ {
   @ObfuscatedSignature(descriptor = "Llv;")
   @ObfuscatedName("af")
   public static final classLV field4233 = new classLV(1);
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   static final int field4237 = 66;
   @ObfuscatedName("ae")
   int field4231 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   static final int field4236 = 26;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field4235 = 16;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field4234 = 0;
   @ObfuscatedSignature(descriptor = "Llv;")
   @ObfuscatedName("az")
   public static final classLV field4232 = new classLV(0);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Llv;")
   @ObfuscatedName("as")
   public static classLV[] method7200() {
      return new classLV[]{field4233, field4232};
   }

   classLV(int var1) {
      this.field4231 = var1 * -1761326117;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field4231 * -1608337325;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lv.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field4231 * -1608337325;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field4231 * -1608337325;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field4231 * -1608337325;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public static void method7201(int var0) {
      try {
         classPG.field5236.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "lv.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;Luz;B)F")
   @ObfuscatedName("ag")
   public static final float method7202(classUZ var0, classUZ var1, byte var2) {
      try {
         return classUZ.method11835(var0, var1, (short)5176);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lv.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIZIZB)V")
   @ObfuscatedName("ag")
   static void method7203(int var0, int var1, int var2, boolean var3, int var4, boolean var5, byte var6) {
      try {
         if (var0 < var1) {
            int var7 = (var1 + var0) / 2;
            int var8 = var0;
            World var9 = World.field1168[var7];
            World.field1168[var7] = World.field1168[var1];
            World.field1168[var1] = var9;

            for (int var10 = var0; var10 < var1; var10++) {
               if (var6 != 1) {
                  throw new IllegalStateException();
               }

               World var12 = World.field1168[var10];
               int var13 = classLI.method7147(var12, var9, var2, var3, 104496674);
               int var11;
               if (0 != var13) {
                  if (var3) {
                     if (var6 != 1) {
                        throw new IllegalStateException();
                     }

                     var11 = -var13;
                  } else {
                     var11 = var13;
                  }
               } else if (var4 == -1) {
                  if (var6 != 1) {
                     throw new IllegalStateException();
                  }

                  var11 = 0;
               } else {
                  int var14 = classLI.method7147(var12, var9, var4, var5, -795038588);
                  if (var5) {
                     if (var6 != 1) {
                        throw new IllegalStateException();
                     }

                     var11 = -var14;
                  } else {
                     var11 = var14;
                  }
               }

               if (var11 <= 0) {
                  if (var6 != 1) {
                     throw new IllegalStateException();
                  }

                  World var16 = World.field1168[var10];
                  World.field1168[var10] = World.field1168[var8];
                  World.field1168[var8++] = var16;
               }
            }

            World.field1168[var1] = World.field1168[var8];
            World.field1168[var8] = var9;
            method7203(var0, var8 - 1, var2, var3, var4, var5, (byte)1);
            method7203(var8 + 1, var1, var2, var3, var4, var5, (byte)1);
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "lv.ag(" + ')');
      }
   }
}
