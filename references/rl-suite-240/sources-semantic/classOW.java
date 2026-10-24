import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ow")
public class classOW extends classVJ {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field5176 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5170 = 1;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ax")
   public static IndexDataBase field5183;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field5182 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field5172 = 2;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ac")
   public static NodeCache field5181 = new NodeCache(64);
   @ObfuscatedName("aa")
   public final int field5173;
   @ObfuscatedName("au")
   public final int field5171;
   @ObfuscatedName("al")
   public final boolean field5175;
   @ObfuscatedName("aj")
   public final int field5168;
   @ObfuscatedName("ay")
   public final int field5178;
   @ObfuscatedName("aq")
   public final int field5179;
   @ObfuscatedName("ad")
   public final int field5180;
   @ObfuscatedName("ap")
   public final int field5177;
   @ObfuscatedName("ao")
   public final int field5174;
   @ObfuscatedName("ai")
   public final int field5169;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ag")
   public static void method8838() {
      field5181.method6468();
   }

   public classOW(classXY var1, int var2) {
      int var3 = 0;
      int var4 = -1;
      boolean var5 = true;
      int var6 = -1;
      if (null != var1 && var1.field6952 != null) {
         label51:
         while (true) {
            int var7 = var1.method13095(-346779531);
            switch (var7) {
               case 0:
                  break label51;
               case 1:
                  var3 = var1.method13105(-758448158);
                  continue;
               case 2:
                  var4 = var1.method13095(-346779531);
               case 3:
               case 4:
               case 6:
               case 8:
               default:
                  continue;
               case 5:
                  var5 = false;
                  continue;
               case 7:
                  var6 = var1.method13105(-758448158);
                  continue;
               case 9:
            }

            var1.method13095(-346779531);
         }
      }

      this.field5173 = var3 * 584710275;
      this.field5174 = 1423667909 * var4;
      this.field5175 = var5;
      this.field5168 = var6 * -1839613789;
      if (-1 != var6) {
         classOS var8 = this.method8844(var6, -1620115005);
         this.field5177 = 1910154801 * var8.field5105;
         this.field5171 = -2044794319 * var8.field5103;
         this.field5169 = 2036871205 * var8.field5104;
      } else {
         this.field5177 = 0;
         this.field5171 = 0;
         this.field5169 = 0;
      }

      classOS var9 = this.method8844(var3, 1943355153);
      this.field5178 = var9.field5105 * 2033473845;
      this.field5179 = var9.field5103 * -411782965;
      this.field5180 = 837220081 * var9.field5104;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("pm")
   public int method8845() {
      return this.field5168 * 151173899;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ab")
   public static void method8837(IndexDataBase var0) {
      field5183 = var0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("xf")
   public int method8846() {
      return this.field5180 * -1560131225;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("nk")
   public int method8847() {
      return this.field5173 * 2136479275;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Low;I)Los;")
   @ObfuscatedName("tj")
   public static classOS method8843(classOW var0, int var1) {
      if (var0 == null) {
         var0.method8848();
      }

      double var2 = (var1 >> 16 & 89120203) / 256.0;
      double var4 = (var1 >> 8 & 0xFF) / 256.0;
      double var6 = (var1 & 747201095) / 256.0;
      double var8 = var2;
      if (var4 < var2) {
         var8 = var4;
      }

      if (var6 < var8) {
         var8 = var6;
      }

      double var10 = var2;
      if (var4 > var2) {
         var10 = var4;
      }

      if (var6 > var10) {
         var10 = var6;
      }

      double var12 = 0.0;
      double var14 = 0.0;
      double var16 = (var8 + var10) / 2.0;
      if (var8 != var10) {
         if (var16 < 0.5) {
            var14 = (var10 - var8) / (var10 + var8);
         }

         if (var16 >= 0.5) {
            var14 = (var10 - var8) / (2.0 - var10 - var8);
         }

         if (var10 == var2) {
            var12 = (var4 - var6) / (var10 - var8);
         } else if (var10 == var4) {
            var12 = 2.0 + (var6 - var2) / (var10 - var8);
         } else if (var10 == var6) {
            var12 = (var2 - var4) / (var10 - var8) + 4.0;
         }
      }

      var12 /= 6.0;
      return new classOS(var0, (int)(var12 * 256.0), (int)(var14 * 256.0), (int)(256.0 * var16));
   }

   @ObfuscatedSignature(descriptor = "(II)J")
   @ObfuscatedName("af")
   public static long method8842(int var0, int var1) {
      try {
         return classZI.field7185[var0];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ow.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   public static void method8839() {
      field5181.method6468();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("gu")
   public int method8848() {
      return this.field5174 * -982434803;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public static void method8840() {
      field5181.method6468();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jq")
   public int method8849() {
      return this.field5169 * 1086307051;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("xr")
   public int method8850() {
      return this.field5179 * -1352766871;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public static void method8841() {
      field5181.method6468();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bj")
   public int method8851() {
      return this.field5171 * -2131451469;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("th")
   public int method8852() {
      return this.field5177 * 2023234915;
   }

   @ObfuscatedSignature(descriptor = "(II)Los;")
   @ObfuscatedName("ae")
   classOS method8844(int var1, int var2) {
      try {
         double var3 = (var1 >> 16 & 0xFF) / 256.0;
         double var5 = (var1 >> 8 & 0xFF) / 256.0;
         double var7 = (var1 & 0xFF) / 256.0;
         double var9 = var3;
         if (var5 < var3) {
            if (var2 == -389406596) {
               throw new IllegalStateException();
            }

            var9 = var5;
         }

         if (var7 < var9) {
            if (var2 == -389406596) {
               throw new IllegalStateException();
            }

            var9 = var7;
         }

         double var11 = var3;
         if (var5 > var3) {
            if (var2 == -389406596) {
               throw new IllegalStateException();
            }

            var11 = var5;
         }

         if (var7 > var11) {
            if (var2 == -389406596) {
               throw new IllegalStateException();
            }

            var11 = var7;
         }

         double var13 = 0.0;
         double var15 = 0.0;
         double var17 = (var9 + var11) / 2.0;
         if (var9 != var11) {
            if (var2 == -389406596) {
               throw new IllegalStateException();
            }

            if (var17 < 0.5) {
               if (var2 == -389406596) {
                  throw new IllegalStateException();
               }

               var15 = (var11 - var9) / (var11 + var9);
            }

            if (var17 >= 0.5) {
               if (var2 == -389406596) {
                  throw new IllegalStateException();
               }

               var15 = (var11 - var9) / (2.0 - var11 - var9);
            }

            if (var11 == var3) {
               if (var2 == -389406596) {
                  throw new IllegalStateException();
               }

               var13 = (var5 - var7) / (var11 - var9);
            } else if (var11 == var5) {
               if (var2 == -389406596) {
                  throw new IllegalStateException();
               }

               var13 = 2.0 + (var7 - var3) / (var11 - var9);
            } else if (var11 == var7) {
               var13 = (var3 - var5) / (var11 - var9) + 4.0;
            }
         }

         var13 /= 6.0;
         return new classOS(this, (int)(var13 * 256.0), (int)(var15 * 256.0), (int)(256.0 * var17));
      } catch (RuntimeException var20) {
         throw classEG.method3899(var20, "ow.ae(" + ')');
      }
   }
}
