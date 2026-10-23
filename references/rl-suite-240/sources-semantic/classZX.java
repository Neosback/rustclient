import java.util.HashMap;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zx")
public class classZX {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field7292 = 256;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final float field7290 = 3.0F;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field7291 = 6;
   @ObfuscatedName("ag")
   final HashMap field7286 = new HashMap();
   @ObfuscatedSignature(descriptor = "Luq;")
   @ObfuscatedName("as")
   classUQ field7285 = new classUQ(0, 0);
   @ObfuscatedName("ax")
   int[] field7287 = new int[2048];
   @ToRemove(unused = "true")
   @ObfuscatedName("cs")
   static final int field7293 = 83;
   @ObfuscatedName("aa")
   int field7288;
   @ObfuscatedName("ac")
   int[] field7289 = new int[2048];

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public final void method14265() {
      this.field7288 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzx;IILym;F)V")
   @ObfuscatedName("lb")
   public static void method14268(classZX var0, int var1, int var2, SpritePixels var3, float var4) {
      if (var0 == null) {
         var0.method14267();
      } else {
         int var5 = (int)(18.0F * var4);
         SpritePixels var6 = method14260(var0, var5, -339174694);
         int var7 = 1 + 2 * var5;
         classUQ var8 = new classUQ(0, 0, var3.field7005, var3.field7007);
         classUQ var9 = new classUQ(0, 0);
         var0.field7285.method11534(var7, var7, -1647031174);
         System.nanoTime();

         for (int var10 = 0; var10 < var0.field7288 * -1366011121; var10++) {
            int var11 = var0.field7287[var10];
            int var12 = var0.field7289[var10];
            int var13 = (int)((var11 - var1) * var4) - var5;
            int var14 = (int)(var3.field7007 - (var12 - var2) * var4) - var5;
            var0.field7285.method11533(var13, var14, (byte)18);
            classUQ.method11547(var0.field7285, var8, var9, 1312925182);
            var0.method14272(var6, var3, var9, (byte)103);
         }

         System.nanoTime();
         System.nanoTime();

         for (int var15 = 0; var15 < var3.field7003.length; var15++) {
            if (var3.field7003[var15] == 0) {
               var3.field7003[var15] = -16777216;
            } else {
               int var16 = (var3.field7003[var15] + 64 - 1) / 256;
               if (var16 <= 0) {
                  var3.field7003[var15] = -16777216;
               } else {
                  if (var16 > classDR.field1611.length) {
                     var16 = classDR.field1611.length;
                  }

                  int var17 = classDR.field1611[var16 - 1];
                  var3.field7003[var15] = 0xFF000000 | var17;
               }
            }
         }

         System.nanoTime();
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("ab")
   public final void method14262(int var1, int var2, byte var3) {
      try {
         if (-1366011121 * this.field7288 >= this.field7287.length) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }
         } else {
            this.field7287[this.field7288 * -1366011121] = var1;
            this.field7289[-1366011121 * this.field7288] = var2;
            this.field7288 += -578929681;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "zx.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   public final void method14266(int var1) {
      try {
         this.field7288 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "zx.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("al")
   public final void method14263(int var1, int var2) {
      if (-1333235261 * this.field7288 < this.field7287.length) {
         this.field7287[this.field7288 * -1366011121] = var1;
         this.field7289[2081724457 * this.field7288] = var2;
         this.field7288 += -578929681;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lym;Luq;B)V")
   @ObfuscatedName("ax")
   void method14272(SpritePixels var1, SpritePixels var2, classUQ var3, byte var4) {
      try {
         if (0 != var3.field6368 * 518523273) {
            if (var3.field6369 * -874893249 != 0) {
               int var5 = 0;
               int var6 = 0;
               if (0 == var3.field6366 * -1357616065) {
                  if (var4 == 5) {
                     throw new IllegalStateException();
                  }

                  var5 = var1.field7005 - 518523273 * var3.field6368;
               }

               if (926655743 * var3.field6367 == 0) {
                  if (var4 == 5) {
                     return;
                  }

                  var6 = var1.field7007 - -874893249 * var3.field6369;
               }

               int var7 = var5 + var1.field7005 * var6;
               int var8 = 926655743 * var3.field6367 * var2.field7005 + var3.field6366 * -1357616065;

               for (int var9 = 0; var9 < var3.field6369 * -874893249; var9++) {
                  for (int var10 = 0; var10 < var3.field6368 * 518523273; var10++) {
                     if (var4 == 5) {
                        return;
                     }

                     int var10001 = var8++;
                     var2.field7003[var10001] = var2.field7003[var10001] + var1.field7003[var7++];
                  }

                  var7 += var1.field7005 - 518523273 * var3.field6368;
                  var8 += var2.field7005 - 518523273 * var3.field6368;
               }

               return;
            }

            if (var4 == 5) {
               return;
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "zx.ax(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzx;II)V")
   @ObfuscatedName("sb")
   public static void method14264(classZX var0, int var1, int var2) {
      if (1706344020 * var0.field7288 < var0.field7287.length) {
         var0.field7287[var0.field7288 * -1366011121] = var1;
         var0.field7289[-1366011121 * var0.field7288] = var2;
         var0.field7288 += -296242497;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzx;II)V")
   @ObfuscatedName("ej")
   public static void method14256(classZX var0, int var1, int var2) {
      if (var0 == null) {
         var0.method14257(var1, var1);
      }

      try {
         int var3 = 1 + 2 * var1;
         double[] var4 = classFW.method5068(0.0, var1 / 3.0F, var1, 224234046);
         double var5 = var4[var1] * var4[var1];
         int[] var7 = new int[var3 * var3];
         boolean var8 = false;

         for (int var9 = 0; var9 < var3; var9++) {
            if (var2 <= 1894137396) {
               throw new IllegalStateException();
            }

            for (int var10 = 0; var10 < var3; var10++) {
               int var11 = var7[var3 * var9 + var10] = (int)(var4[var10] * var4[var9] / var5 * 256.0);
               if (!var8 && var11 > 0) {
                  if (var2 <= 1894137396) {
                     throw new IllegalStateException();
                  }

                  var8 = true;
               }
            }
         }

         SpritePixels var13 = new SpritePixels(var7, var3, var3);
         var0.field7286.put(var1, var13);
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "zx.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   static void method14255() {
      classDR.field1611 = new int[2000];
      int var0 = 0;
      int var1 = -378936223;

      for (byte var2 = 12; var0 < 16; var1 -= var2) {
         int var3 = classHS.method6135(var1 / 360.0F, 0.9999F, 0.075F + var0 * 0.425F / 16.0F);
         classDR.field1611[var0] = var3;
         var0++;
      }

      var1 = -2002516797;

      for (int var6 = var1 / 6; var0 < classDR.field1611.length; var1 -= var6) {
         int var7 = var0 * 2;

         for (int var4 = classHS.method6135(var1 / 360.0F, 0.9999F, 0.5); var0 < var7 && var0 < classDR.field1611.length; var0++) {
            classDR.field1611[var0] = var4;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("ao")
   SpritePixels method14258(int var1) {
      if (!this.field7286.containsKey(var1)) {
         method14256(this, var1, 1897744987);
      }

      return (SpritePixels)this.field7286.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(IILym;FI)V")
   @ObfuscatedName("as")
   public final void method14269(int var1, int var2, SpritePixels var3, float var4, int var5) {
      try {
         int var6 = (int)(18.0F * var4);
         SpritePixels var7 = method14260(this, var6, -1270092059);
         int var8 = 1 + 2 * var6;
         classUQ var9 = new classUQ(0, 0, var3.field7005, var3.field7007);
         classUQ var10 = new classUQ(0, 0);
         this.field7285.method11534(var8, var8, -1722492525);
         System.nanoTime();

         for (int var11 = 0; var11 < this.field7288 * -1366011121; var11++) {
            if (var5 >= 1016952715) {
               throw new IllegalStateException();
            }

            int var12 = this.field7287[var11];
            int var13 = this.field7289[var11];
            int var14 = (int)((var12 - var1) * var4) - var6;
            int var15 = (int)(var3.field7007 - (var13 - var2) * var4) - var6;
            this.field7285.method11533(var14, var15, (byte)61);
            classUQ.method11547(this.field7285, var9, var10, 732616358);
            this.method14272(var7, var3, var10, (byte)-29);
         }

         System.nanoTime();
         System.nanoTime();

         for (int var17 = 0; var17 < var3.field7003.length; var17++) {
            if (var5 >= 1016952715) {
               return;
            }

            if (var3.field7003[var17] == 0) {
               if (var5 >= 1016952715) {
                  return;
               }

               var3.field7003[var17] = -16777216;
            } else {
               int var18 = (var3.field7003[var17] + 64 - 1) / 256;
               if (var18 <= 0) {
                  if (var5 >= 1016952715) {
                     throw new IllegalStateException();
                  }

                  var3.field7003[var17] = -16777216;
               } else {
                  if (var18 > classDR.field1611.length) {
                     if (var5 >= 1016952715) {
                        return;
                     }

                     var18 = classDR.field1611.length;
                  }

                  int var19 = classDR.field1611[var18 - 1];
                  var3.field7003[var17] = 0xFF000000 | var19;
               }
            }
         }

         System.nanoTime();
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "zx.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzx;I)Lym;")
   @ObfuscatedName("mx")
   public static SpritePixels method14259(classZX var0, int var1) {
      if (!var0.field7286.containsKey(var1)) {
         method14256(var0, var1, 2069481781);
      }

      return (SpritePixels)var0.field7286.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lym;Luq;)V")
   @ObfuscatedName("aw")
   void method14273(SpritePixels var1, SpritePixels var2, classUQ var3) {
      if (0 != var3.field6368 * 518523273 && var3.field6369 * -874893249 != 0) {
         int var4 = 0;
         int var5 = 0;
         if (0 == var3.field6366 * -1357616065) {
            var4 = var1.field7005 - 518523273 * var3.field6368;
         }

         if (926655743 * var3.field6367 == 0) {
            var5 = var1.field7007 - -874893249 * var3.field6369;
         }

         int var6 = var4 + var1.field7005 * var5;
         int var7 = 926655743 * var3.field6367 * var2.field7005 + var3.field6366 * -1357616065;

         for (int var8 = 0; var8 < var3.field6369 * -874893249; var8++) {
            for (int var9 = 0; var9 < var3.field6368 * 518523273; var9++) {
               int var10001 = var7++;
               var2.field7003[var10001] = var2.field7003[var10001] + var1.field7003[var6++];
            }

            var6 += var1.field7005 - 518523273 * var3.field6368;
            var7 += var2.field7005 - 518523273 * var3.field6368;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzx;II)Lym;")
   @ObfuscatedName("yr")
   public static SpritePixels method14260(classZX var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method14261(var1, var1);
      } else {
         try {
            if (!var0.field7286.containsKey(var1)) {
               if (var2 <= -2122380289) {
                  throw new IllegalStateException();
               }

               method14256(var0, var1, 2107515615);
            }

            return (SpritePixels)var0.field7286.get(var1);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "zx.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public final void method14267() {
      this.field7288 = 0;
   }

   @ObfuscatedSignature(descriptor = "(IILym;F)V")
   @ObfuscatedName("ad")
   public final void method14270(int var1, int var2, SpritePixels var3, float var4) {
      int var5 = (int)(18.0F * var4);
      SpritePixels var6 = method14260(this, var5, 473429035);
      int var7 = 1 + 2 * var5;
      classUQ var8 = new classUQ(0, 0, var3.field7005, var3.field7007);
      classUQ var9 = new classUQ(0, 0);
      this.field7285.method11534(var7, var7, -1017271496);
      System.nanoTime();

      for (int var10 = 0; var10 < this.field7288 * -1366011121; var10++) {
         int var11 = this.field7287[var10];
         int var12 = this.field7289[var10];
         int var13 = (int)((var11 - var1) * var4) - var5;
         int var14 = (int)(var3.field7007 - (var12 - var2) * var4) - var5;
         this.field7285.method11533(var13, var14, (byte)88);
         classUQ.method11547(this.field7285, var8, var9, -1548234324);
         this.method14272(var6, var3, var9, (byte)37);
      }

      System.nanoTime();
      System.nanoTime();

      for (int var15 = 0; var15 < var3.field7003.length; var15++) {
         if (var3.field7003[var15] == 0) {
            var3.field7003[var15] = -16777216;
         } else {
            int var16 = (var3.field7003[var15] + 64 - 1) / 256;
            if (var16 <= 0) {
               var3.field7003[var15] = -16777216;
            } else {
               if (var16 > classDR.field1611.length) {
                  var16 = classDR.field1611.length;
               }

               int var17 = classDR.field1611[var16 - 1];
               var3.field7003[var15] = 0xFF000000 | var17;
            }
         }
      }

      System.nanoTime();
   }

   @ObfuscatedSignature(descriptor = "(IILym;F)V")
   @ObfuscatedName("ap")
   public final void method14271(int var1, int var2, SpritePixels var3, float var4) {
      int var5 = (int)(18.0F * var4);
      SpritePixels var6 = method14260(this, var5, 981780693);
      int var7 = 1 + 2 * var5;
      classUQ var8 = new classUQ(0, 0, var3.field7005, var3.field7007);
      classUQ var9 = new classUQ(0, 0);
      this.field7285.method11534(var7, var7, -1490701363);
      System.nanoTime();

      for (int var10 = 0; var10 < this.field7288 * -1366011121; var10++) {
         int var11 = this.field7287[var10];
         int var12 = this.field7289[var10];
         int var13 = (int)((var11 - var1) * var4) - var5;
         int var14 = (int)(var3.field7007 - (var12 - var2) * var4) - var5;
         this.field7285.method11533(var13, var14, (byte)67);
         classUQ.method11547(this.field7285, var8, var9, 2079935131);
         this.method14272(var6, var3, var9, (byte)32);
      }

      System.nanoTime();
      System.nanoTime();

      for (int var15 = 0; var15 < var3.field7003.length; var15++) {
         if (var3.field7003[var15] == 0) {
            var3.field7003[var15] = -16777216;
         } else {
            int var16 = (var3.field7003[var15] + 64 - 1) / 256;
            if (var16 <= 0) {
               var3.field7003[var15] = 101263684;
            } else {
               if (var16 > classDR.field1611.length) {
                  var16 = classDR.field1611.length;
               }

               int var17 = classDR.field1611[var16 - 1];
               var3.field7003[var15] = 0xFF000000 | var17;
            }
         }
      }

      System.nanoTime();
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lym;Luq;)V")
   @ObfuscatedName("ar")
   void method14274(SpritePixels var1, SpritePixels var2, classUQ var3) {
      if (0 != var3.field6368 * 518523273 && var3.field6369 * -874893249 != 0) {
         int var4 = 0;
         int var5 = 0;
         if (0 == var3.field6366 * -1357616065) {
            var4 = var1.field7005 - 518523273 * var3.field6368;
         }

         if (926655743 * var3.field6367 == 0) {
            var5 = var1.field7007 - -874893249 * var3.field6369;
         }

         int var6 = var4 + var1.field7005 * var5;
         int var7 = 926655743 * var3.field6367 * var2.field7005 + var3.field6366 * -1357616065;

         for (int var8 = 0; var8 < var3.field6369 * -874893249; var8++) {
            for (int var9 = 0; var9 < var3.field6368 * 518523273; var9++) {
               int var10001 = var7++;
               var2.field7003[var10001] = var2.field7003[var10001] + var1.field7003[var6++];
            }

            var6 += var1.field7005 - 518523273 * var3.field6368;
            var7 += var2.field7005 - 518523273 * var3.field6368;
         }
      }
   }

   public classZX() {
      this.field7288 = 0;
      classRE.method9988((byte)-76);
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lym;Luq;)V")
   @ObfuscatedName("ai")
   void method14275(SpritePixels var1, SpritePixels var2, classUQ var3) {
      if (0 != var3.field6368 * 518523273 && var3.field6369 * -874893249 != 0) {
         int var4 = 0;
         int var5 = 0;
         if (0 == var3.field6366 * -1247468175) {
            var4 = var1.field7005 - 518523273 * var3.field6368;
         }

         if (2126533639 * var3.field6367 == 0) {
            var5 = var1.field7007 - -1904036842 * var3.field6369;
         }

         int var6 = var4 + var1.field7005 * var5;
         int var7 = 926655743 * var3.field6367 * var2.field7005 + var3.field6366 * -1357616065;

         for (int var8 = 0; var8 < var3.field6369 * 1375341397; var8++) {
            for (int var9 = 0; var9 < var3.field6368 * 1039637675; var9++) {
               int var10001 = var7++;
               var2.field7003[var10001] = var2.field7003[var10001] + var1.field7003[var6++];
            }

            var6 += var1.field7005 - -1059343231 * var3.field6368;
            var7 += var2.field7005 - 518523273 * var3.field6368;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("af")
   void method14257(int var1, int var2) {
      try {
         int var3 = 1 + 2 * var1;
         double[] var4 = classFW.method5068(0.0, var1 / 3.0F, var1, 224234046);
         double var5 = var4[var1] * var4[var1];
         int[] var7 = new int[var3 * var3];
         boolean var8 = false;

         for (int var9 = 0; var9 < var3; var9++) {
            if (var2 <= 1894137396) {
               throw new IllegalStateException();
            }

            for (int var10 = 0; var10 < var3; var10++) {
               int var11 = var7[var3 * var9 + var10] = (int)(var4[var10] * var4[var9] / var5 * 256.0);
               if (!var8 && var11 > 0) {
                  if (var2 <= 1894137396) {
                     throw new IllegalStateException();
                  }

                  var8 = true;
               }
            }
         }

         SpritePixels var13 = new SpritePixels(var7, var3, var3);
         this.field7286.put(var1, var13);
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "zx.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lym;")
   @ObfuscatedName("ae")
   SpritePixels method14261(int var1, int var2) {
      try {
         if (!this.field7286.containsKey(var1)) {
            if (var2 <= -2122380289) {
               throw new IllegalStateException();
            }

            method14256(this, var1, 2107515615);
         }

         return (SpritePixels)this.field7286.get(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zx.ae(" + ')');
      }
   }
}
