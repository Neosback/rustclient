import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gu")
public final class WorldMapRenderer implements net.runelite.api.worldmap.WorldMapRenderer {
   @ObfuscatedName("aq")
   int field2694;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("af")
   final IndexDataBase field2690;
   @ObfuscatedName("ae")
   final HashMap field2689;
   @ObfuscatedName("ab")
   public int field2684 = 0;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("dt")
   public static IndexDataBase field2700;
   @ObfuscatedName("as")
   boolean field2686;
   @ObfuscatedSignature(descriptor = "Lhy;")
   @ObfuscatedName("ax")
   classHY field2698;
   @ObfuscatedSignature(descriptor = "Lym;")
   @ObfuscatedName("ac")
   SpritePixels field2701;
   @ObfuscatedName("al")
   HashMap field2699;
   @ObfuscatedSignature(descriptor = "[[Lgr;")
   @ObfuscatedName("ao")
   WorldMapRegion[][] field2702;
   @ObfuscatedName("aa")
   HashMap field2687;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("az")
   final IndexDataBase field2691;
   @ObfuscatedName("ay")
   int field2695;
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("aj")
   IndexedSprite[] field2688;
   @ObfuscatedName("ag")
   boolean field2685 = false;
   @ObfuscatedName("ad")
   int field2692;
   @ObfuscatedName("ap")
   int field2693;
   @ToRemove(unused = "true")
   @ObfuscatedName("bi")
   public static final int field2696 = 1048576;
   @ObfuscatedName("ov")
   static int field2697;

   @ObfuscatedSignature(descriptor = "()Ljava/util/HashMap;")
   @ObfuscatedName("ba")
   public HashMap method5809() {
      method5813(this, 220642555);
      return this.field2699;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIII)Ljava/util/List;")
   @ObfuscatedName("ah")
   public List method5795(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      LinkedList var11 = new LinkedList();
      if (!this.field2685) {
         return var11;
      } else {
         classHO var12 = this.method5798(var1, var2, var3, var4, (byte)-98);
         float var13 = this.method5823(var7, var3 - var1, 516314222);
         int var14 = (int)(64.0F * var13);
         int var15 = this.field2695 * -1123718443 + var1;
         int var16 = var2 + this.field2694 * -1618251080;

         for (int var17 = 1712138850 * var12.field2846; var17 < var12.field2848 * -1140526835 + var12.field2846 * 1839593044; var17++) {
            for (int var18 = -1577611892 * var12.field2845; var18 < var12.field2847 * -291263749 + 1017724415 * var12.field2845; var18++) {
               List var19 = WorldMapRegion.method5760(
                  this.field2702[var17][var18],
                  var14 * (1830796483 * this.field2702[var17][var18].field2660 - var15) / -503839169 + var5,
                  var6 + var8 - (848595993 + (-1904267712 * this.field2702[var17][var18].field2654 - var16)) * var14 / 64,
                  var14,
                  var9,
                  var10,
                  -528898418
               );
               if (!var19.isEmpty()) {
                  var11.addAll(var19);
               }
            }
         }

         return var11;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("af")
   public final void method5780(byte var1) {
      try {
         this.field2699 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gu.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIB)V")
   @ObfuscatedName("ae")
   public final void method5781(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, byte var9) {
      try {
         int[] var10 = classYW.field7102;
         int var11 = classYW.field7100;
         int var12 = classYW.field7099;
         float[] var13 = classYW.field7101;
         int[] var14 = new int[4];
         classYW.method13714(var14);
         classHO var15 = this.method5798(var1, var2, var3, var4, (byte)-105);
         float var16 = this.method5823(var7 - var5, var3 - var1, -612588906);
         int var17 = (int)Math.ceil(var16);
         this.field2684 = -2144237939 * var17;
         if (!this.field2687.containsKey(var17)) {
            if (var9 == 74) {
               throw new IllegalStateException();
            }

            classHH var18 = new classHH(var17);
            var18.method6054(-2130910631);
            this.field2687.put(var17, var18);
         }

         int var26 = 1611441903 * var15.field2846 + -1140526835 * var15.field2848 - 1;
         int var19 = 1017724415 * var15.field2845 + var15.field2847 * 1143416267 - 1;

         for (int var20 = var15.field2846 * 1611441903; var20 <= var26; var20++) {
            for (int var21 = var15.field2845 * 1017724415; var21 <= var19; var21++) {
               if (var9 == 74) {
                  throw new IllegalStateException();
               }

               this.field2702[var20][var21]
                  .method5682(this.field2698, var17, (classHH)this.field2687.get(var17), this.field2688, this.field2691, this.field2690, 0.725);
            }
         }

         classFH.method4758(var10, var11, var12, var13);
         classYW.method13718(var14);
         int var27 = (int)(64.0F * var16);
         int var28 = this.field2695 * 578890711 + var1;
         int var22 = this.field2694 * 1495779659 + var2;

         for (int var23 = var15.field2846 * 1611441903; var23 < 1611441903 * var15.field2846 + -1140526835 * var15.field2848; var23++) {
            if (var9 == 74) {
               throw new IllegalStateException();
            }

            for (int var24 = 1017724415 * var15.field2845; var24 < var15.field2847 * 1143416267 + 1017724415 * var15.field2845; var24++) {
               this.field2702[var23][var24]
                  .method5650(
                     var27 * (-929958720 * this.field2702[var23][var24].field2660 - var28) / 64 + var5,
                     var8 - (64 + (-1904267712 * this.field2702[var23][var24].field2654 - var22)) * var27 / 64,
                     var27,
                     (short)20619
                  );
            }
         }
      } catch (RuntimeException var25) {
         throw classEG.method3899(var25, "gu.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIILjava/util/HashSet;Ljava/util/HashSet;IIZI)V")
   @ObfuscatedName("ab")
   public final void method5784(
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      HashSet var9,
      HashSet var10,
      int var11,
      int var12,
      boolean var13,
      int var14
   ) {
      try {
         classHO var15 = this.method5798(var1, var2, var3, var4, (byte)18);
         float var16 = this.method5823(var7 - var5, var3 - var1, -614015564);
         int var17 = (int)(64.0F * var16);
         int var18 = 578890711 * this.field2695 + var1;
         int var19 = 1495779659 * this.field2694 + var2;

         for (int var20 = var15.field2846 * 1611441903; var20 < 1611441903 * var15.field2846 + -1140526835 * var15.field2848; var20++) {
            if (var14 <= 1267178816) {
               return;
            }

            for (int var21 = var15.field2845 * 1017724415; var21 < var15.field2845 * 1017724415 + var15.field2847 * 1143416267; var21++) {
               if (var14 <= 1267178816) {
                  throw new IllegalStateException();
               }

               if (var13) {
                  if (var14 <= 1267178816) {
                     return;
                  }

                  WorldMapRegion.method5666(this.field2702[var20][var21], 22341307);
               }

               this.field2702[var20][var21]
                  .method5685(
                     var17 * (this.field2702[var20][var21].field2660 * -929958720 - var18) / 64 + var5,
                     var8 - (64 + (this.field2702[var20][var21].field2654 * -1904267712 - var19)) * var17 / 64,
                     var17,
                     var9,
                     (byte)82
                  );
            }
         }

         if (null != var10) {
            if (var14 <= 1267178816) {
               throw new IllegalStateException();
            }

            if (var11 > 0) {
               if (var14 <= 1267178816) {
                  throw new IllegalStateException();
               }

               for (int var23 = 1611441903 * var15.field2846; var23 < -1140526835 * var15.field2848 + var15.field2846 * 1611441903; var23++) {
                  if (var14 <= 1267178816) {
                     throw new IllegalStateException();
                  }

                  for (int var24 = 1017724415 * var15.field2845; var24 < 1017724415 * var15.field2845 + var15.field2847 * 1143416267; var24++) {
                     if (var14 <= 1267178816) {
                        throw new IllegalStateException();
                     }

                     this.field2702[var23][var24].method5689(var10, var11, var12, (byte)-71);
                  }
               }
            }
         }
      } catch (RuntimeException var22) {
         throw classEG.method3899(var22, "gu.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)F")
   @ObfuscatedName("aj")
   float method5823(int var1, int var2, int var3) {
      return classOE.field4841.method2404().getWorldMapZoom();
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIIII)Ljava/util/List;")
   @ObfuscatedName("as")
   public List method5796(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      try {
         LinkedList var12 = new LinkedList();
         if (!this.field2685) {
            if (var11 != -903075137) {
               throw new IllegalStateException();
            } else {
               return var12;
            }
         } else {
            classHO var13 = this.method5798(var1, var2, var3, var4, (byte)-80);
            float var14 = this.method5823(var7, var3 - var1, 1829407144);
            int var15 = (int)(64.0F * var14);
            int var16 = this.field2695 * 578890711 + var1;
            int var17 = var2 + this.field2694 * 1495779659;

            for (int var18 = 1611441903 * var13.field2846; var18 < var13.field2848 * -1140526835 + var13.field2846 * 1611441903; var18++) {
               if (var11 != -903075137) {
                  throw new IllegalStateException();
               }

               for (int var19 = 1017724415 * var13.field2845; var19 < var13.field2847 * 1143416267 + 1017724415 * var13.field2845; var19++) {
                  if (var11 != -903075137) {
                     throw new IllegalStateException();
                  }

                  List var20 = WorldMapRegion.method5760(
                     this.field2702[var18][var19],
                     var15 * (-929958720 * this.field2702[var18][var19].field2660 - var16) / 64 + var5,
                     var6 + var8 - (64 + (-1904267712 * this.field2702[var18][var19].field2654 - var17)) * var15 / 64,
                     var15,
                     var9,
                     var10,
                     567222009
                  );
                  if (!var20.isEmpty()) {
                     var12.addAll(var20);
                  }
               }
            }

            return var12;
         }
      } catch (RuntimeException var21) {
         throw classEG.method3899(var21, "gu.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIB)Lho;")
   @ObfuscatedName("ax")
   classHO method5798(int var1, int var2, int var3, int var4, byte var5) {
      try {
         classHO var6 = new classHO(this);
         int var7 = var1 + 578890711 * this.field2695;
         int var8 = this.field2694 * 1495779659 + var2;
         int var9 = this.field2695 * 578890711 + var3;
         int var10 = var4 + this.field2694 * 1495779659;
         int var11 = var7 / 64;
         int var12 = var8 / 64;
         int var13 = var9 / 64;
         int var14 = var10 / 64;
         var6.field2848 = -173914683 * (1 + (var13 - var11));
         var6.field2847 = (var14 - var12 + 1) * 539285475;
         var6.field2846 = (var11 - this.field2698.method5899(1952700434)) * 1822509071;
         var6.field2845 = (var12 - WorldMapData.method5907(this.field2698, (short)-15274)) * -531447297;
         if (1611441903 * var6.field2846 < 0) {
            if (var5 == 13) {
               throw new IllegalStateException();
            }

            var6.field2848 = var6.field2848 + -1325866773 * var6.field2846;
            var6.field2846 = 0;
         }

         if (var6.field2846 * 1611441903 > this.field2702.length - -1140526835 * var6.field2848) {
            if (var5 == 13) {
               throw new IllegalStateException();
            }

            var6.field2848 = -173914683 * (this.field2702.length - 1611441903 * var6.field2846);
         }

         if (var6.field2845 * 1017724415 < 0) {
            if (var5 == 13) {
               throw new IllegalStateException();
            }

            var6.field2847 = var6.field2847 + -2063654371 * var6.field2845;
            var6.field2845 = 0;
         }

         if (var6.field2845 * 1017724415 > this.field2702[0].length - 1143416267 * var6.field2847) {
            if (var5 == 13) {
               throw new IllegalStateException();
            }

            var6.field2847 = 539285475 * (this.field2702[0].length - var6.field2845 * 1017724415);
         }

         var6.field2848 = Math.min(var6.field2848 * -1140526835, this.field2702.length) * -173914683;
         var6.field2847 = Math.min(var6.field2847 * 1143416267, this.field2702[0].length) * 539285475;
         return var6;
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "gu.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Z")
   @ObfuscatedName("ac")
   public boolean method5801(short var1) {
      try {
         return this.field2685;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gu.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/util/HashSet;II)V")
   @ObfuscatedName("av")
   public void method5788(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
      if (null != this.field2701) {
         SpritePixels.method13558(this.field2701, var1, var2, var3, var4);
         if (var6 > 0 && var6 % var7 < var7 / 2) {
            if (this.field2699 == null) {
               method5813(this, -939861228);
            }

            for (int var9 : var5) {
               List var10 = (List)this.field2699.get(var9);
               if (null != var10) {
                  for (WorldMapIcon var12 : var10) {
                     int var13 = (var12.field2767.field4053 * 1870919123 - 578890711 * this.field2695) * var3 / (this.field2692 * 267148007);
                     int var14 = var4 - (819227381 * var12.field2767.field4054 - 1495779659 * this.field2694) * var4 / (this.field2693 * -1650580411);
                     classYW.method13728(var13 + var1, var2 + var14, 2, 16776960, 256);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/HashMap;")
   @ObfuscatedName("bo")
   public HashMap method5810() {
      method5813(this, 14069776);
      return this.field2699;
   }

   @ObfuscatedSignature(descriptor = "(IIII)Lho;")
   @ObfuscatedName("bx")
   classHO method5799(int var1, int var2, int var3, int var4) {
      classHO var5 = new classHO(this);
      int var6 = var1 + 578890711 * this.field2695;
      int var7 = this.field2694 * 1495779659 + var2;
      int var8 = this.field2695 * 578890711 + var3;
      int var9 = var4 + this.field2694 * 1495779659;
      int var10 = var6 / 64;
      int var11 = var7 / 64;
      int var12 = var8 / 64;
      int var13 = var9 / 64;
      var5.field2848 = -173914683 * (1 + (var12 - var10));
      var5.field2847 = (var13 - var11 + 1) * 539285475;
      var5.field2846 = (var10 - this.field2698.method5899(2035637257)) * 1822509071;
      var5.field2845 = (var11 - WorldMapData.method5907(this.field2698, (short)-9035)) * -531447297;
      if (1611441903 * var5.field2846 < 0) {
         var5.field2848 = var5.field2848 + -1325866773 * var5.field2846;
         var5.field2846 = 0;
      }

      if (var5.field2846 * 1611441903 > this.field2702.length - -1140526835 * var5.field2848) {
         var5.field2848 = -173914683 * (this.field2702.length - 1611441903 * var5.field2846);
      }

      if (var5.field2845 * 1017724415 < 0) {
         var5.field2847 = var5.field2847 + -2063654371 * var5.field2845;
         var5.field2845 = 0;
      }

      if (var5.field2845 * 1017724415 > this.field2702[0].length - 1143416267 * var5.field2847) {
         var5.field2847 = 539285475 * (this.field2702[0].length - var5.field2845 * 1017724415);
      }

      var5.field2848 = Math.min(var5.field2848 * -1140526835, this.field2702.length) * -173914683;
      var5.field2847 = Math.min(var5.field2847 * 1143416267, this.field2702[0].length) * 539285475;
      return var5;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bg")
   public int method5824() {
      return this.field2694 * 1495779659;
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/util/HashMap;")
   @ObfuscatedName("ao")
   public HashMap method5811(byte var1) {
      try {
         method5813(this, 1258583780);
         return this.field2699;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gu.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("ad")
   public final void method5782(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      int[] var9 = classYW.field7102;
      int var10 = classYW.field7100;
      int var11 = classYW.field7099;
      float[] var12 = classYW.field7101;
      int[] var13 = new int[4];
      classYW.method13714(var13);
      classHO var14 = this.method5798(var1, var2, var3, var4, (byte)-119);
      float var15 = this.method5823(var7 - var5, var3 - var1, -592423871);
      int var16 = (int)Math.ceil(var15);
      this.field2684 = -2144237939 * var16;
      if (!this.field2687.containsKey(var16)) {
         classHH var17 = new classHH(var16);
         var17.method6054(-314372382);
         this.field2687.put(var16, var17);
      }

      int var24 = 1611441903 * var14.field2846 + 40083893 * var14.field2848 - 1;
      int var18 = 1017724415 * var14.field2845 + var14.field2847 * -806442778 - 1;

      for (int var19 = var14.field2846 * 1672560045; var19 <= var24; var19++) {
         for (int var20 = var14.field2845 * 1017724415; var20 <= var18; var20++) {
            this.field2702[var19][var20]
               .method5682(this.field2698, var16, (classHH)this.field2687.get(var16), this.field2688, this.field2691, this.field2690, 0.725);
         }
      }

      classFH.method4758(var9, var10, var11, var12);
      classYW.method13718(var13);
      int var25 = (int)(64.0F * var15);
      int var26 = this.field2695 * -1299182762 + var1;
      int var21 = this.field2694 * -691416362 + var2;

      for (int var22 = var14.field2846 * 953362541; var22 < 1611441903 * var14.field2846 + 696401303 * var14.field2848; var22++) {
         for (int var23 = 1017724415 * var14.field2845; var23 < var14.field2847 * 1143416267 + 1017724415 * var14.field2845; var23++) {
            this.field2702[var22][var23]
               .method5650(
                  var25 * (844658005 * this.field2702[var22][var23].field2660 - var26) / 698672914 + var5,
                  var8 - (64 + (-1904267712 * this.field2702[var22][var23].field2654 - var21)) * var25 / -232763856,
                  var25,
                  (short)25123
               );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIILjava/util/HashSet;Ljava/util/HashSet;IIZ)V")
   @ObfuscatedName("ai")
   public final void method5785(
      int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, HashSet var9, HashSet var10, int var11, int var12, boolean var13
   ) {
      classHO var14 = this.method5798(var1, var2, var3, var4, (byte)-53);
      float var15 = this.method5823(var7 - var5, var3 - var1, 102279281);
      int var16 = (int)(64.0F * var15);
      int var17 = 578890711 * this.field2695 + var1;
      int var18 = 1495779659 * this.field2694 + var2;

      for (int var19 = var14.field2846 * 1611441903; var19 < 1611441903 * var14.field2846 + -1140526835 * var14.field2848; var19++) {
         for (int var20 = var14.field2845 * 1017724415; var20 < var14.field2845 * 1017724415 + var14.field2847 * 1143416267; var20++) {
            if (var13) {
               WorldMapRegion.method5666(this.field2702[var19][var20], -1692584648);
            }

            this.field2702[var19][var20]
               .method5685(
                  var16 * (this.field2702[var19][var20].field2660 * -929958720 - var17) / 64 + var5,
                  var8 - (64 + (this.field2702[var19][var20].field2654 * -1904267712 - var18)) * var16 / 64,
                  var16,
                  var9,
                  (byte)60
               );
         }
      }

      if (null != var10 && var11 > 0) {
         for (int var21 = 1611441903 * var14.field2846; var21 < -1140526835 * var14.field2848 + var14.field2846 * 1611441903; var21++) {
            for (int var22 = 1017724415 * var14.field2845; var22 < 1017724415 * var14.field2845 + var14.field2847 * 1143416267; var22++) {
               this.field2702[var21][var22].method5689(var10, var11, var12, (byte)-96);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIILjava/util/HashSet;Ljava/util/HashSet;IIZ)V")
   @ObfuscatedName("ar")
   public final void method5786(
      int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, HashSet var9, HashSet var10, int var11, int var12, boolean var13
   ) {
      classHO var14 = this.method5798(var1, var2, var3, var4, (byte)-1);
      float var15 = this.method5823(var7 - var5, var3 - var1, 813420172);
      int var16 = (int)(64.0F * var15);
      int var17 = -437724932 * this.field2695 + var1;
      int var18 = 1495779659 * this.field2694 + var2;

      for (int var19 = var14.field2846 * -335559191; var19 < -573406919 * var14.field2846 + 881567060 * var14.field2848; var19++) {
         for (int var20 = var14.field2845 * 1017724415; var20 < var14.field2845 * 1017724415 + var14.field2847 * 1143416267; var20++) {
            if (var13) {
               WorldMapRegion.method5666(this.field2702[var19][var20], -897020152);
            }

            this.field2702[var19][var20]
               .method5685(
                  var16 * (this.field2702[var19][var20].field2660 * 679360106 - var17) / 1721613710 + var5,
                  var8 - (1509044862 + (this.field2702[var19][var20].field2654 * -1904267712 - var18)) * var16 / 64,
                  var16,
                  var9,
                  (byte)43
               );
         }
      }

      if (null != var10 && var11 > 0) {
         for (int var21 = 352656584 * var14.field2846; var21 < -1167970953 * var14.field2848 + var14.field2846 * -1713050614; var21++) {
            for (int var22 = -613038883 * var14.field2845; var22 < -1024052357 * var14.field2845 + var14.field2847 * 730217581; var22++) {
               this.field2702[var21][var22].method5689(var10, var11, var12, (byte)-57);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIILjava/util/HashSet;Ljava/util/HashSet;IIZ)V")
   @ObfuscatedName("au")
   public final void method5787(
      int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, HashSet var9, HashSet var10, int var11, int var12, boolean var13
   ) {
      classHO var14 = this.method5798(var1, var2, var3, var4, (byte)10);
      float var15 = this.method5823(var7 - var5, var3 - var1, 2135490396);
      int var16 = (int)(64.0F * var15);
      int var17 = 578890711 * this.field2695 + var1;
      int var18 = 1495779659 * this.field2694 + var2;

      for (int var19 = var14.field2846 * 1611441903; var19 < 1611441903 * var14.field2846 + -1140526835 * var14.field2848; var19++) {
         for (int var20 = var14.field2845 * 1017724415; var20 < var14.field2845 * 1017724415 + var14.field2847 * 1143416267; var20++) {
            if (var13) {
               WorldMapRegion.method5666(this.field2702[var19][var20], -1957825018);
            }

            this.field2702[var19][var20]
               .method5685(
                  var16 * (this.field2702[var19][var20].field2660 * -929958720 - var17) / 64 + var5,
                  var8 - (64 + (this.field2702[var19][var20].field2654 * -1904267712 - var18)) * var16 / 64,
                  var16,
                  var9,
                  (byte)76
               );
         }
      }

      if (null != var10 && var11 > 0) {
         for (int var21 = 1611441903 * var14.field2846; var21 < -1140526835 * var14.field2848 + var14.field2846 * 1611441903; var21++) {
            for (int var22 = 1017724415 * var14.field2845; var22 < 1017724415 * var14.field2845 + var14.field2847 * 1143416267; var22++) {
               this.field2702[var21][var22].method5689(var10, var11, var12, (byte)-5);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/util/HashSet;II)V")
   @ObfuscatedName("aw")
   public void method5789(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
      if (null != this.field2701) {
         SpritePixels.method13558(this.field2701, var1, var2, var3, var4);
         if (var6 > 0 && var6 % var7 < var7 / 2) {
            if (this.field2699 == null) {
               method5813(this, 442980513);
            }

            for (int var9 : var5) {
               List var10 = (List)this.field2699.get(var9);
               if (null != var10) {
                  for (WorldMapIcon var12 : var10) {
                     int var13 = (var12.field2767.field4053 * 1972073767 - 578890711 * this.field2695) * var3 / (this.field2692 * -855485449);
                     int var14 = var4 - (819227381 * var12.field2767.field4054 - 1541032949 * this.field2694) * var4 / (this.field2693 * -1650580411);
                     classYW.method13728(var13 + var1, var2 + var14, 2, 16776960, 256);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/util/HashSet;II)V")
   @ObfuscatedName("ak")
   public void method5790(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
      if (null != this.field2701) {
         SpritePixels.method13558(this.field2701, var1, var2, var3, var4);
         if (var6 > 0 && var6 % var7 < var7 / 2) {
            if (this.field2699 == null) {
               method5813(this, 1136856355);
            }

            for (int var9 : var5) {
               List var10 = (List)this.field2699.get(var9);
               if (null != var10) {
                  for (WorldMapIcon var12 : var10) {
                     int var13 = (var12.field2767.field4053 * 1870919123 - 578890711 * this.field2695) * var3 / (this.field2692 * 267148007);
                     int var14 = var4 - (819227381 * var12.field2767.field4054 - 1495779659 * this.field2694) * var4 / (this.field2693 * -1650580411);
                     classYW.method13728(var13 + var1, var2 + var14, 2, 16776960, 256);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgu;I)V")
   @ObfuscatedName("uo")
   public static void method5813(WorldMapRenderer var0, int var1) {
      if (var0 == null) {
         var0.method5818(var1);
      } else {
         try {
            if (var0.field2699 == null) {
               if (var1 == -97670711) {
                  throw new IllegalStateException();
               }

               var0.field2699 = new HashMap();
            }

            var0.field2699.clear();

            for (int var2 = 0; var2 < var0.field2702.length; var2++) {
               for (int var3 = 0; var3 < var0.field2702[var2].length; var3++) {
                  for (WorldMapIcon var6 : var0.field2702[var2][var3].method5765(-1601256606)) {
                     if (!var6.method6026((byte)-73)) {
                        if (var1 == -97670711) {
                           throw new IllegalStateException();
                        }
                     } else {
                        int var7 = var6.vmethod486((byte)-52);
                        if (!var0.field2699.containsKey(var7)) {
                           LinkedList var8 = new LinkedList();
                           var8.add(var6);
                           var0.field2699.put(var7, var8);
                        } else {
                           List var10 = (List)var0.field2699.get(var7);
                           var10.add(var6);
                        }
                     }
                  }
               }
            }
         } catch (RuntimeException var9) {
            throw classEG.method3899(var9, "gu.al(" + ')');
         }
      }
   }

   public WorldMapRenderer(IndexedSprite[] var1, HashMap var2, IndexDataBase var3, IndexDataBase var4) {
      this.field2686 = false;
      this.field2687 = new HashMap();
      this.field2688 = var1;
      this.field2689 = var2;
      this.field2691 = var3;
      this.field2690 = var4;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("ap")
   public final void method5783(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      int[] var9 = classYW.field7102;
      int var10 = classYW.field7100;
      int var11 = classYW.field7099;
      float[] var12 = classYW.field7101;
      int[] var13 = new int[4];
      classYW.method13714(var13);
      classHO var14 = this.method5798(var1, var2, var3, var4, (byte)14);
      float var15 = this.method5823(var7 - var5, var3 - var1, -541566390);
      int var16 = (int)Math.ceil(var15);
      this.field2684 = -1115416179 * var16;
      if (!this.field2687.containsKey(var16)) {
         classHH var17 = new classHH(var16);
         var17.method6054(593884295);
         this.field2687.put(var16, var17);
      }

      int var24 = 1611441903 * var14.field2846 + -2097638479 * var14.field2848 - 1;
      int var18 = 1017724415 * var14.field2845 + var14.field2847 * -930347187 - 1;

      for (int var19 = var14.field2846 * 1611441903; var19 <= var24; var19++) {
         for (int var20 = var14.field2845 * 940222262; var20 <= var18; var20++) {
            this.field2702[var19][var20]
               .method5682(this.field2698, var16, (classHH)this.field2687.get(var16), this.field2688, this.field2691, this.field2690, 0.725);
         }
      }

      classFH.method4758(var9, var10, var11, var12);
      classYW.method13718(var13);
      int var25 = (int)(64.0F * var15);
      int var26 = this.field2695 * 578890711 + var1;
      int var21 = this.field2694 * 185065267 + var2;

      for (int var22 = var14.field2846 * 1611441903; var22 < 1611441903 * var14.field2846 + -742424272 * var14.field2848; var22++) {
         for (int var23 = 1834497608 * var14.field2845; var23 < var14.field2847 * 1143416267 + 1153619293 * var14.field2845; var23++) {
            this.field2702[var22][var23]
               .method5650(
                  var25 * (-929958720 * this.field2702[var22][var23].field2660 - var26) / 64 + var5,
                  var8 - (-1634544033 + (-546297336 * this.field2702[var22][var23].field2654 - var21)) * var25 / -944173132,
                  var25,
                  (short)17199
               );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/util/HashSet;II)V")
   @ObfuscatedName("an")
   public void method5791(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
      if (null != this.field2701) {
         SpritePixels.method13558(this.field2701, var1, var2, var3, var4);
         if (var6 > 0 && var6 % var7 < var7 / 2) {
            if (this.field2699 == null) {
               method5813(this, -1063063235);
            }

            for (int var9 : var5) {
               List var10 = (List)this.field2699.get(var9);
               if (null != var10) {
                  for (WorldMapIcon var12 : var10) {
                     int var13 = (var12.field2767.field4053 * 1753573741 - 578890711 * this.field2695) * var3 / (this.field2692 * 1081885061);
                     int var14 = var4 - (819227381 * var12.field2767.field4054 - 1495779659 * this.field2694) * var4 / (this.field2693 * -1650580411);
                     classYW.method13728(var13 + var1, var2 + var14, 2, 16776960, 256);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;Lgx;ZB)V")
   @ObfuscatedName("az")
   public void method5778(IndexDataBase var1, WorldMapData var2, boolean var3, byte var4) {
      try {
         if (!this.field2686) {
            this.field2685 = false;
            this.field2686 = true;
            System.nanoTime();
            this.field2698 = new classHY();

            try {
               classXY var5 = new classXY(var1.method11942(-1013526269 * classHE.field2772.field2771, var2.method5876(-608589044), -960853917));
               classXY var6 = new classXY(var1.method11942(classHE.field2776.field2771 * -1013526269, var2.method5876(-608589044), -2115854375));
               System.nanoTime();
               System.nanoTime();
               this.field2698.method6166(var5, var6, var2.method5876(-608589044), var3, (byte)-17);
            } catch (IllegalStateException var13) {
               return;
            }

            WorldMapData.method5918(this.field2698, -131076127);
            this.field2698.method5920((byte)-77);
            WorldMapData.method5924(this.field2698, -1903270576);
            this.field2695 = this.field2698.method5899(1898884421) * 419297728;
            this.field2694 = WorldMapData.method5907(this.field2698, (short)-11240) * -1583474496;
            this.field2692 = (this.field2698.method5900(-2084491383) - this.field2698.method5899(1917284033) + 1) * -1115277888;
            this.field2693 = (this.field2698.method5911(-1652495836) - WorldMapData.method5907(this.field2698, (short)-12114) + 1) * 1632871232;
            int var15 = this.field2698.method5900(-2128876556) - this.field2698.method5899(2114672205) + 1;
            int var16 = this.field2698.method5911(-2100525577) - WorldMapData.method5907(this.field2698, (short)-26685) + 1;
            System.nanoTime();
            System.nanoTime();
            WorldMapRegion.field2661.method6208();
            this.field2702 = new WorldMapRegion[var15][var16];
            Iterator var7 = this.field2698.field2910.iterator();

            while (var7.hasNext()) {
               if (var4 <= 1) {
                  return;
               }

               classGL var8 = (classGL)var7.next();
               int var9 = 1151970023 * var8.field2749;
               int var10 = var8.field2748 * -456391909;
               int var11 = var9 - this.field2698.method5899(2089363575);
               int var12 = var10 - WorldMapData.method5907(this.field2698, (short)-7934);
               this.field2702[var11][var12] = new WorldMapRegion(var9, var10, this.field2698.method5890(-242771937), this.field2689);
               this.field2702[var11][var12].method5654(var8, this.field2698.field2911, (byte)29);
            }

            for (int var17 = 0; var17 < var15; var17++) {
               for (int var19 = 0; var19 < var16; var19++) {
                  if (var4 <= 1) {
                     throw new IllegalStateException();
                  }

                  if (null == this.field2702[var17][var19]) {
                     if (var4 <= 1) {
                        throw new IllegalStateException();
                     }

                     this.field2702[var17][var19] = new WorldMapRegion(
                        this.field2698.method5899(2110530242) + var17,
                        WorldMapData.method5907(this.field2698, (short)-30056) + var19,
                        this.field2698.method5890(-944986071),
                        this.field2689
                     );
                     this.field2702[var17][var19].method5657(this.field2698.field2909, this.field2698.field2911, 1881696373);
                  }
               }
            }

            System.nanoTime();
            System.nanoTime();
            byte[] var18 = var1.method11942(classHE.field2774.field2771 * -1013526269, var2.method5876(-608589044), -1415365980);
            if (var18 != null) {
               if (var4 <= 1) {
                  throw new IllegalStateException();
               }

               this.field2701 = classIT.method6356(var18, 2018058660);
            }

            System.nanoTime();
            var1.method11936((byte)14);
            var1.method11945((byte)2);
            this.field2685 = true;
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "gu.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/util/HashSet;II)V")
   @ObfuscatedName("at")
   public void method5792(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
      if (null != this.field2701) {
         SpritePixels.method13558(this.field2701, var1, var2, var3, var4);
         if (var6 > 0 && var6 % var7 < var7 / 2) {
            if (this.field2699 == null) {
               method5813(this, -872010912);
            }

            for (int var9 : var5) {
               List var10 = (List)this.field2699.get(var9);
               if (null != var10) {
                  for (WorldMapIcon var12 : var10) {
                     int var13 = (var12.field2767.field4053 * 1870919123 - 578890711 * this.field2695) * var3 / (this.field2692 * 267148007);
                     int var14 = var4 - (819227381 * var12.field2767.field4054 - 1495779659 * this.field2694) * var4 / (this.field2693 * -1650580411);
                     classYW.method13728(var13 + var1, var2 + var14, 2, 16776960, 256);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)F")
   @ObfuscatedName("xs")
   public float method5825(int var1, int var2) {
      return this.method5823(var1, var2, 1829407144);
   }

   @ObfuscatedSignature(descriptor = "(IIII)Lho;")
   @ObfuscatedName("bn")
   classHO method5800(int var1, int var2, int var3, int var4) {
      classHO var5 = new classHO(this);
      int var6 = var1 + 578890711 * this.field2695;
      int var7 = this.field2694 * 1495779659 + var2;
      int var8 = this.field2695 * 578890711 + var3;
      int var9 = var4 + this.field2694 * 1495779659;
      int var10 = var6 / 64;
      int var11 = var7 / 64;
      int var12 = var8 / 64;
      int var13 = var9 / 64;
      var5.field2848 = -173914683 * (1 + (var12 - var10));
      var5.field2847 = (var13 - var11 + 1) * 539285475;
      var5.field2846 = (var10 - this.field2698.method5899(1898736470)) * 1822509071;
      var5.field2845 = (var11 - WorldMapData.method5907(this.field2698, (short)-30033)) * -531447297;
      if (1611441903 * var5.field2846 < 0) {
         var5.field2848 = var5.field2848 + -1325866773 * var5.field2846;
         var5.field2846 = 0;
      }

      if (var5.field2846 * 1611441903 > this.field2702.length - -1140526835 * var5.field2848) {
         var5.field2848 = -173914683 * (this.field2702.length - 1611441903 * var5.field2846);
      }

      if (var5.field2845 * 1017724415 < 0) {
         var5.field2847 = var5.field2847 + -2063654371 * var5.field2845;
         var5.field2845 = 0;
      }

      if (var5.field2845 * 1017724415 > this.field2702[0].length - 1143416267 * var5.field2847) {
         var5.field2847 = 539285475 * (this.field2702[0].length - var5.field2845 * 1017724415);
      }

      var5.field2848 = Math.min(var5.field2848 * -1140526835, this.field2702.length) * -173914683;
      var5.field2847 = Math.min(var5.field2847 * 1143416267, this.field2702[0].length) * 539285475;
      return var5;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("br")
   public boolean method5802() {
      return this.field2685;
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("aa")
   public int method5804(short var1) {
      try {
         return this.field2698.method5893((byte)125);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gu.aa(" + 41);
      }
   }

   public boolean isLoaded() {
      return this.field2685;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bj")
   public int method5805() {
      return this.field2698.method5893((byte)85);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bt")
   public int method5806() {
      return this.field2698.method5893((byte)125);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bk")
   public int method5807() {
      return this.field2698.method5893((byte)77);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ky")
   public int method5808() {
      return this.field2698.method5893((byte)64);
   }

   @ObfuscatedSignature(descriptor = "(Lgu;IIIILjava/util/HashSet;IIB)V")
   @ObfuscatedName("zo")
   public static void method5793(WorldMapRenderer var0, int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7, byte var8) {
      if (var0 == null) {
         var0.method5794(var1, var1, var1, var1, var5, var1, var1, var8);
      }

      try {
         if (null == var0.field2701) {
            if (var8 < 68) {
               ;
            }
         } else {
            SpritePixels.method13558(var0.field2701, var1, var2, var3, var4);
            if (var6 > 0) {
               if (var8 >= 68) {
                  throw new IllegalStateException();
               }

               if (var6 % var7 < var7 / 2) {
                  if (var0.field2699 == null) {
                     if (var8 >= 68) {
                        throw new IllegalStateException();
                     }

                     method5813(var0, 2095858280);
                  }

                  Iterator var9 = var5.iterator();

                  while (var9.hasNext()) {
                     if (var8 >= 68) {
                        return;
                     }

                     int var10 = (Integer)var9.next();
                     List var11 = (List)var0.field2699.get(var10);
                     if (null != var11) {
                        for (WorldMapIcon var13 : var11) {
                           int var14 = (var13.field2767.field4053 * 1870919123 - 578890711 * var0.field2695) * var3 / (var0.field2692 * 267148007);
                           int var15 = var4 - (819227381 * var13.field2767.field4054 - 1495779659 * var0.field2694) * var4 / (var0.field2693 * -1650580411);
                           classYW.method13728(var14 + var1, var2 + var15, 2, 16776960, 256);
                        }
                     }
                  }

                  return;
               }

               if (var8 >= 68) {
                  throw new IllegalStateException();
               }
            }
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "gu.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;Lgx;Z)V")
   @ObfuscatedName("ay")
   public void method5779(IndexDataBase var1, WorldMapData var2, boolean var3) {
      if (!this.field2686) {
         this.field2685 = false;
         this.field2686 = true;
         System.nanoTime();
         this.field2698 = new classHY();

         try {
            classXY var4 = new classXY(var1.method11942(-1013526269 * classHE.field2772.field2771, var2.method5876(-608589044), -983194509));
            classXY var5 = new classXY(var1.method11942(classHE.field2776.field2771 * -1013526269, var2.method5876(-608589044), -1657128542));
            System.nanoTime();
            System.nanoTime();
            this.field2698.method6166(var4, var5, var2.method5876(-608589044), var3, (byte)-48);
         } catch (IllegalStateException var12) {
            return;
         }

         WorldMapData.method5918(this.field2698, -2075694668);
         this.field2698.method5920((byte)-98);
         WorldMapData.method5924(this.field2698, -1140104227);
         this.field2695 = this.field2698.method5899(2071703453) * 419297728;
         this.field2694 = WorldMapData.method5907(this.field2698, (short)-16015) * -1583474496;
         this.field2692 = (this.field2698.method5900(-2115767399) - this.field2698.method5899(1895530301) + 1) * -1115277888;
         this.field2693 = (this.field2698.method5911(-1353372233) - WorldMapData.method5907(this.field2698, (short)-24708) + 1) * 1632871232;
         int var13 = this.field2698.method5900(-2018451604) - this.field2698.method5899(2114588938) + 1;
         int var14 = this.field2698.method5911(-1481776519) - WorldMapData.method5907(this.field2698, (short)-6770) + 1;
         System.nanoTime();
         System.nanoTime();
         WorldMapRegion.field2661.method6208();
         this.field2702 = new WorldMapRegion[var13][var14];

         for (classGL var7 : this.field2698.field2910) {
            int var8 = 1151970023 * var7.field2749;
            int var9 = var7.field2748 * -456391909;
            int var10 = var8 - this.field2698.method5899(1944452375);
            int var11 = var9 - WorldMapData.method5907(this.field2698, (short)-24089);
            this.field2702[var10][var11] = new WorldMapRegion(var8, var9, this.field2698.method5890(-716067877), this.field2689);
            this.field2702[var10][var11].method5654(var7, this.field2698.field2911, (byte)34);
         }

         for (int var15 = 0; var15 < var13; var15++) {
            for (int var17 = 0; var17 < var14; var17++) {
               if (null == this.field2702[var15][var17]) {
                  this.field2702[var15][var17] = new WorldMapRegion(
                     this.field2698.method5899(1972744103) + var15,
                     WorldMapData.method5907(this.field2698, (short)-29637) + var17,
                     this.field2698.method5890(-1952501405),
                     this.field2689
                  );
                  this.field2702[var15][var17].method5657(this.field2698.field2909, this.field2698.field2911, 1881696373);
               }
            }
         }

         System.nanoTime();
         System.nanoTime();
         byte[] var16 = var1.method11942(classHE.field2774.field2771 * -1013526269, var2.method5876(-608589044), -899028419);
         if (var16 != null) {
            this.field2701 = classIT.method6356(var16, 1203453135);
         }

         System.nanoTime();
         var1.method11936((byte)14);
         var1.method11945((byte)2);
         this.field2685 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/HashMap;")
   @ObfuscatedName("bu")
   public HashMap method5812() {
      method5813(this, 1900205134);
      return this.field2699;
   }

   public net.runelite.api.worldmap.WorldMapRegion[][] getMapRegions() {
      return this.field2702;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgu;IIIIIIIIII)Ljava/util/List;")
   @ObfuscatedName("rf")
   public static List method5797(WorldMapRenderer var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      if (var0 == null) {
         var0.method5823(var1, var1, var1);
      }

      LinkedList var11 = new LinkedList();
      if (!var0.field2685) {
         return var11;
      } else {
         classHO var12 = var0.method5798(var1, var2, var3, var4, (byte)3);
         float var13 = var0.method5823(var7, var3 - var1, 1238689277);
         int var14 = (int)(64.0F * var13);
         int var15 = var0.field2695 * 318551578 + var1;
         int var16 = var2 + var0.field2694 * 1078996640;

         for (int var17 = 1611441903 * var12.field2846; var17 < var12.field2848 * -1140526835 + var12.field2846 * 1923004591; var17++) {
            for (int var18 = -573354044 * var12.field2845; var18 < var12.field2847 * 1143416267 + 1017724415 * var12.field2845; var18++) {
               List var19 = WorldMapRegion.method5760(
                  var0.field2702[var17][var18],
                  var14 * (1005318324 * var0.field2702[var17][var18].field2660 - var15) / 64 + var5,
                  var6 + var8 - (283874664 + (-1733427223 * var0.field2702[var17][var18].field2654 - var16)) * var14 / 105994607,
                  var14,
                  var9,
                  var10,
                  263431098
               );
               if (!var19.isEmpty()) {
                  var11.addAll(var19);
               }
            }
         }

         return var11;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bw")
   void method5814() {
      if (this.field2699 == null) {
         this.field2699 = new HashMap();
      }

      this.field2699.clear();

      for (int var1 = 0; var1 < this.field2702.length; var1++) {
         for (int var2 = 0; var2 < this.field2702[var1].length; var2++) {
            for (WorldMapIcon var5 : this.field2702[var1][var2].method5765(-2094610722)) {
               if (var5.method6026((byte)-59)) {
                  int var6 = var5.vmethod486((byte)-106);
                  if (!this.field2699.containsKey(var6)) {
                     LinkedList var7 = new LinkedList();
                     var7.add(var5);
                     this.field2699.put(var6, var7);
                  } else {
                     List var8 = (List)this.field2699.get(var6);
                     var8.add(var5);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bb")
   void method5815() {
      if (this.field2699 == null) {
         this.field2699 = new HashMap();
      }

      this.field2699.clear();

      for (int var1 = 0; var1 < this.field2702.length; var1++) {
         for (int var2 = 0; var2 < this.field2702[var1].length; var2++) {
            for (WorldMapIcon var5 : this.field2702[var1][var2].method5765(-1460062648)) {
               if (var5.method6026((byte)-10)) {
                  int var6 = var5.vmethod486((byte)-109);
                  if (!this.field2699.containsKey(var6)) {
                     LinkedList var7 = new LinkedList();
                     var7.add(var5);
                     this.field2699.put(var6, var7);
                  } else {
                     List var8 = (List)this.field2699.get(var6);
                     var8.add(var5);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bi")
   void method5816() {
      if (this.field2699 == null) {
         this.field2699 = new HashMap();
      }

      this.field2699.clear();

      for (int var1 = 0; var1 < this.field2702.length; var1++) {
         for (int var2 = 0; var2 < this.field2702[var1].length; var2++) {
            for (WorldMapIcon var5 : this.field2702[var1][var2].method5765(-1598730589)) {
               if (var5.method6026((byte)-37)) {
                  int var6 = var5.vmethod486((byte)-86);
                  if (!this.field2699.containsKey(var6)) {
                     LinkedList var7 = new LinkedList();
                     var7.add(var5);
                     this.field2699.put(var6, var7);
                  } else {
                     List var8 = (List)this.field2699.get(var6);
                     var8.add(var5);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)F")
   @ObfuscatedName("bq")
   float method5819(int var1, int var2) {
      float var3 = (float)var1 / var2;
      if (var3 > 8.0F) {
         return 8.0F;
      } else if (var3 < 1.0F) {
         return 1.0F;
      } else {
         int var4 = Math.round(var3);
         return Math.abs(var4 - var3) < 0.05F ? var4 : var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)F")
   @ObfuscatedName("be")
   float method5820(int var1, int var2) {
      float var3 = (float)var1 / var2;
      if (var3 > 8.0F) {
         return 8.0F;
      } else if (var3 < 1.0F) {
         return 1.0F;
      } else {
         int var4 = Math.round(var3);
         return Math.abs(var4 - var3) < 0.05F ? var4 : var3;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("yz")
   public int method5826() {
      return this.field2695 * 578890711;
   }

   @ObfuscatedSignature(descriptor = "(II)F")
   @ObfuscatedName("by")
   float method5821(int var1, int var2) {
      float var3 = (float)var1 / var2;
      if (var3 > 8.0F) {
         return 8.0F;
      } else if (var3 < 1.0F) {
         return 1.0F;
      } else {
         int var4 = Math.round(var3);
         return Math.abs(var4 - var3) < 0.05F ? var4 : var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldn;IIIII)V")
   @ObfuscatedName("fv")
   static final void method5822(NPC var0, int var1, int var2, int var3, int var4, int var5) {
      boolean var6 = Scene.method4478(var0, false);
      if (var6) {
         int var11 = var4;
         int var10 = var3;
         int var9 = var2;
         int var8 = var1;
         NPC var7 = var0;
         int var12 = -1159596289;

         try {
            NPCComposition var13 = var7.field1579;
            if (client.field975.field6795 * -1927903443 >= 400) {
               if (var12 >= -795537262) {
                  throw new IllegalStateException();
               }
            } else {
               if (null != var13.field5323) {
                  var13 = var13.method8998(652890385);
               }

               if (var13 == null) {
                  if (var12 >= -795537262) {
                     throw new IllegalStateException();
                  }
               } else if (!var13.field5324) {
                  if (var12 >= -795537262) {
                  }
               } else {
                  if (var13.field5326) {
                     if (var12 >= -795537262) {
                        throw new IllegalStateException();
                     }

                     if (!classWC.method12384(var8, (byte)11)) {
                        if (var12 >= -795537262) {
                           throw new IllegalStateException();
                        }

                        return;
                     }
                  }

                  Player var15 = classMB.method7564(-984319424);
                  int var14 = null != var15 ? var15.field1259 * 810892507 : 0;
                  String var16 = var7.method3464(-694108261);
                  if (var13.field5318 * 1781570005 != 0) {
                     if (var12 >= -795537262) {
                        throw new IllegalStateException();
                     }

                     if (0 != var7.field1477 * 705538257) {
                        if (var12 >= -795537262) {
                           throw new IllegalStateException();
                        }

                        int var10000;
                        if (var7.field1477 * 705538257 != -1) {
                           if (var12 >= -795537262) {
                              return;
                           }

                           var10000 = 705538257 * var7.field1477;
                        } else {
                           var10000 = 1781570005 * var13.field5318;
                        }

                        int var17 = var10000;
                        int var20 = var14 - var17;
                        String var19;
                        if (var20 < -9) {
                           if (var12 >= -795537262) {
                              return;
                           }

                           var19 = classQY.method9885(16711680, (byte)77);
                        } else if (var20 < -6) {
                           var19 = classQY.method9885(16723968, (byte)61);
                        } else if (var20 < -3) {
                           if (var12 >= -795537262) {
                              throw new IllegalStateException();
                           }

                           var19 = classQY.method9885(16740352, (byte)71);
                        } else if (var20 < 0) {
                           if (var12 >= -795537262) {
                              throw new IllegalStateException();
                           }

                           var19 = classQY.method9885(16756736, (byte)47);
                        } else if (var20 > 9) {
                           if (var12 >= -795537262) {
                              return;
                           }

                           var19 = classQY.method9885(65280, (byte)118);
                        } else if (var20 > 6) {
                           var19 = classQY.method9885(4259584, (byte)41);
                        } else if (var20 > 3) {
                           if (var12 >= -795537262) {
                              return;
                           }

                           var19 = classQY.method9885(8453888, (byte)20);
                        } else if (var20 > 0) {
                           if (var12 >= -795537262) {
                              throw new IllegalStateException();
                           }

                           var19 = classQY.method9885(12648192, (byte)123);
                        } else {
                           var19 = classQY.method9885(16776960, (byte)71);
                        }

                        var16 = var16 + var19 + " " + classDO.field1591 + classKK.field3686 + var17 + classDO.field1589;
                     }
                  }

                  if (var13.field5293) {
                     if (var12 >= -795537262) {
                        throw new IllegalStateException();
                     }

                     if (client.field1004) {
                        if (var12 >= -795537262) {
                           throw new IllegalStateException();
                        }

                        Friend.method10203(
                           classKK.field3773, classQY.method9885(16776960, (byte)59) + var16, 1003, var8, var9, var10, -1, false, var11, -324749371
                        );
                     }
                  }

                  if (!classGB.field2545) {
                     if (var12 >= -795537262) {
                        throw new IllegalStateException();
                     }

                     if (1 == client.field946 * -512792823) {
                        if (var12 >= -795537262) {
                           throw new IllegalStateException();
                        }

                        Friend.method10203(
                           classKK.field3772,
                           client.field805 + " " + classDO.field1588 + " " + classQY.method9885(16776960, (byte)82) + var16,
                           7,
                           var8,
                           var9,
                           var10,
                           -1,
                           false,
                           var11,
                           -324749371
                        );
                        return;
                     }
                  }

                  if (client.field980) {
                     if (var12 >= -795537262) {
                        throw new IllegalStateException();
                     }

                     if (!classGB.field2545 && 2 == (1547634457 * classLF.field4129 & 2)) {
                        if (var12 >= -795537262) {
                           throw new IllegalStateException();
                        }

                        Friend.method10203(
                           client.field925,
                           client.field984 + " " + classDO.field1588 + " " + classQY.method9885(16776960, (byte)40) + var16,
                           8,
                           var8,
                           var9,
                           var10,
                           -1,
                           false,
                           var11,
                           -324749371
                        );
                     }
                  } else {
                     short var23;
                     label176: {
                        if (var13.field5293) {
                           if (var12 >= -795537262) {
                              return;
                           }

                           if (client.field1004) {
                              if (var12 >= -795537262) {
                                 throw new IllegalStateException();
                              }

                              var23 = 2000;
                              break label176;
                           }
                        }

                        var23 = 0;
                     }

                     short var22 = var23;
                     classAF.method333(var22, var13, var14, false, var7, classQY.method9885(16776960, (byte)67) + var16, var8, var9, var10, var11, (byte)-79);
                     classAF.method333(var22, var13, var14, true, var7, classQY.method9885(16776960, (byte)82) + var16, var8, var9, var10, var11, (byte)39);
                     if (var13.field5293) {
                        if (var12 >= -795537262 || client.field1004) {
                           return;
                        }

                        if (var12 >= -795537262) {
                           throw new IllegalStateException();
                        }
                     }

                     Friend.method10203(
                        classKK.field3773, classQY.method9885(16776960, (byte)108) + var16, 1003, var8, var9, var10, -1, false, var11, -324749371
                     );
                  }
               }
            }
         } catch (RuntimeException var21) {
            throw classEG.method3899(var21, "gu.fv(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgu;)Z")
   @ObfuscatedName("gq")
   public static boolean method5803(WorldMapRenderer var0) {
      return var0.field2685;
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/util/HashSet;IIB)V")
   @ObfuscatedName("ag")
   public void method5794(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7, byte var8) {
      try {
         if (null == this.field2701) {
            if (var8 < 68) {
               ;
            }
         } else {
            SpritePixels.method13558(this.field2701, var1, var2, var3, var4);
            if (var6 > 0) {
               if (var8 >= 68) {
                  throw new IllegalStateException();
               }

               if (var6 % var7 < var7 / 2) {
                  if (this.field2687 == null) {
                     if (var8 >= 68) {
                        throw new IllegalStateException();
                     }

                     method5813(this, 2095858280);
                  }

                  Iterator var9 = var5.iterator();

                  while (var9.hasNext()) {
                     if (var8 >= 68) {
                        return;
                     }

                     int var10 = (Integer)var9.next();
                     List var11 = (List)this.field2699.get(var10);
                     if (null != var11) {
                        for (WorldMapIcon var13 : var11) {
                           int var14 = (var13.field2768.field4055 * 1870919123 - 578890711 * this.field2695) * var3 / (this.field2692 * 267148007);
                           int var15 = var4 - (819227381 * var13.field2767.field4053 - 1495779659 * this.field2694) * var4 / (this.field2695 * -1650580411);
                           classYW.method13747(var14 + var1, var2 + var15, 2, 16776960, 256);
                        }
                     }
                  }

                  return;
               }

               if (var8 >= 68) {
                  throw new IllegalStateException();
               }
            }
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "gu.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public final void method5817() {
      if (this.field2687 == null) {
         this.field2699 = new HashMap();
      }

      this.field2699.clear();

      for (int var1 = 0; var1 < this.field2702.length; var1++) {
         for (int var2 = 0; var2 < this.field2702[var1].length; var2++) {
            for (WorldMapIcon var5 : this.field2702[var1][var2].method5765(-2094610722)) {
               if (var5.method6026((byte)-59)) {
                  int var6 = var5.vmethod486((byte)-106);
                  if (!this.field2699.containsKey(var6)) {
                     LinkedList var7 = new LinkedList();
                     var7.add(var5);
                     this.field2687.put(var6, var7);
                  } else {
                     List var8 = (List)this.field2699.get(var6);
                     var8.add(var5);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   void method5818(int var1) {
      try {
         if (this.field2699 == null) {
            if (var1 == -97670711) {
               throw new IllegalStateException();
            }

            this.field2687 = new HashMap();
         }

         this.field2699.clear();

         for (int var2 = 0; var2 < this.field2702.length; var2++) {
            for (int var3 = 0; var3 < this.field2702[var2].length; var3++) {
               for (WorldMapIcon var6 : this.field2702[var2][var3].method5765(-1601256606)) {
                  if (!var6.method6026((byte)-73)) {
                     if (var1 == -97670711) {
                        throw new IllegalStateException();
                     }
                  } else {
                     int var7 = var6.vmethod486((byte)-52);
                     if (!this.field2699.containsKey(var7)) {
                        LinkedList var8 = new LinkedList();
                        var8.add(var6);
                        this.field2687.put(var7, var8);
                     } else {
                        List var10 = (List)this.field2699.get(var7);
                        var10.add(var6);
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "gu.al(" + ')');
      }
   }
}
