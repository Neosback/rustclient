import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gr")
public class WorldMapRegion implements net.runelite.api.worldmap.WorldMapRegion {
   @ObfuscatedName("ao")
   LinkedList field2655;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2664 = -3407872;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2666 = -3355444;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field2662 = 256;
   @ObfuscatedName("ay")
   List field2656;
   @ObfuscatedName("aj")
   int field2665;
   @ObfuscatedName("ax")
   int field2660;
   @ObfuscatedName("ac")
   int field2654;
   @ObfuscatedSignature(descriptor = "Lgl;")
   @ObfuscatedName("aa")
   classGL field2667;
   @ObfuscatedSignature(descriptor = "Lid;")
   @ObfuscatedName("ag")
   public static classID field2661 = new classID(37748736, 256);
   @ObfuscatedName("al")
   int field2658;
   @ObfuscatedName("as")
   final HashMap field2659;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field2663 = 37748736;
   @ObfuscatedName("aq")
   HashMap field2657;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;)V")
   @ObfuscatedName("vg")
   public static void method5674(WorldMapRegion var0) {
      if (null != var0.field2667) {
         var0.field2667.method5997((byte)-35);
      } else {
         for (classGF var2 : var0.field2655) {
            var2.method5997((byte)-54);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIS)V")
   @ObfuscatedName("ab")
   void method5650(int var1, int var2, int var3, short var4) {
      try {
         int var6 = -551401517 * this.field2660;
         int var7 = this.field2654 * 1312423097;
         int var8 = this.field2665 * 680806733;
         SpritePixels var5 = (SpritePixels)classID.method6185(field2661, classCX.method2699(var6, var7, var8, (byte)1));
         if (null == var5) {
            if (var4 > 128) {
               ;
            }
         } else {
            if (621957952 * this.field2665 == var3) {
               if (var4 <= 128) {
                  return;
               }

               var5.method13552(var1, var2);
            } else {
               var5.method13604(var1, var2, var3, var3);
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "gr.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;)V")
   @ObfuscatedName("pc")
   public static void method5675(WorldMapRegion var0) {
      if (null != var0.field2667) {
         var0.field2667.method5997((byte)-37);
      } else {
         for (classGF var2 : var0.field2655) {
            var2.method5997((byte)-22);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgl;Ljava/util/List;B)V")
   @ObfuscatedName("ag")
   void method5654(classGL var1, List var2, byte var3) {
      try {
         this.field2657.clear();
         this.field2667 = var1;
         this.method5670(var2, (byte)0);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gr.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/ArrayList;Ljava/util/List;I)V")
   @ObfuscatedName("as")
   void method5657(ArrayList var1, List var2, int var3) {
      try {
         this.field2657.clear();
         Iterator var4 = var1.iterator();

         while (var4.hasNext()) {
            if (var3 != 1881696373) {
               throw new IllegalStateException();
            }

            classGF var5 = (classGF)var4.next();
            if (var5.method6014((byte)15) == this.field2660 * -551401517) {
               if (var3 != 1881696373) {
                  throw new IllegalStateException();
               }

               if (var5.method6016((byte)-60) == this.field2654 * 1312423097) {
                  if (var3 != 1881696373) {
                     throw new IllegalStateException();
                  }

                  this.field2655.add(var5);
               }
            }
         }

         this.method5670(var2, (byte)16);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gr.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;IIILjava/util/HashSet;)V")
   @ObfuscatedName("eh")
   public static void method5684(WorldMapRegion var0, int var1, int var2, int var3, HashSet var4) {
      if (var0 == null) {
         var0.method5686(var1, var1, var1, var4);
      }

      if (null == var4) {
         var4 = new HashSet();
      }

      var0.method5721(var1, var2, var4, var3, (byte)3);
      method5745(var0, var1, var2, var4, var3, 543832187);
   }

   @ObfuscatedSignature(descriptor = "(IILjava/util/HashSet;I)V")
   @ObfuscatedName("eg")
   void method5744(int var1, int var2, HashSet var3, int var4) {
      float var5 = var4 / 64.0F;

      for (WorldMapIcon var7 : this.field2656) {
         if (var7.method6026((byte)-17)) {
            int var8 = 1870919123 * var7.field2767.field4053 % 64;
            int var9 = 819227381 * var7.field2767.field4054 % 64;
            var7.field2769 = (int)(var1 + var8 * var5) * 1179114429;
            var7.field2770 = 1689733203 * (int)(var2 + var5 * (63 - var9));
            if (!var3.contains(var7.vmethod486((byte)-83))) {
               this.method5730(var7, 797816725 * var7.field2769, var7.field2770 * 655244251, var5, (byte)32);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;IILjava/util/HashSet;I)V")
   @ObfuscatedName("tw")
   public static void method5720(WorldMapRegion var0, int var1, int var2, HashSet var3, int var4) {
      if (var0 == null) {
         var0.method5762(var1, var1, var1, var1, var1);
      }

      float var5 = var4 / 64.0F;
      float var6 = var5 / 2.0F;

      for (Entry var8 : var0.field2657.entrySet()) {
         classKY var9 = (classKY)var8.getKey();
         int var10 = (int)(var5 * (var9.field4053 * 1870919123) + var1 - var6);
         int var11 = (int)(var2 + var4 - var5 * (819227381 * var9.field4054) - var6);
         WorldMapIcon var12 = (WorldMapIcon)var8.getValue();
         if (var12 != null && var12.method6026((byte)-86)) {
            var12.field2769 = 1179114429 * var10;
            var12.field2770 = var11 * 1689733203;
            classPS var13 = SceneTilePaint.method4817(var12.vmethod486((byte)-37), (byte)-104);
            if (!var3.contains(classPS.method9241(var13, (byte)0))) {
               var0.method5730(var12, var10, var11, var5, (byte)32);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lpe;)I")
   @ObfuscatedName("er")
   int method5747(SpritePixels var1, classPE var2) {
      switch (-44671146 * var2.field5229) {
         case 0:
            return 0;
         case 1:
            return -var1.field7005 / 2;
         default:
            return -var1.field7005;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/List;B)V")
   @ObfuscatedName("ao")
   void method5670(List var1, byte var2) {
      try {
         Iterator var3 = var1.iterator();

         while (var3.hasNext()) {
            if (var2 == 32) {
               return;
            }

            classHF var4 = (classHF)var3.next();
            if (!SceneTilePaint.method4817(var4.field2783 * -1047464521, (byte)-63).field5390) {
               if (var2 == 32) {
                  throw new IllegalStateException();
               }
            } else if (1870919123 * var4.field2767.field4053 >> 6 == this.field2660 * -551401517
               && this.field2654 * 1312423097 == 819227381 * var4.field2767.field4054 >> 6) {
               if (var2 == 32) {
                  throw new IllegalStateException();
               }

               classHF var5 = new classHF(
                  var4.field2767, var4.field2767, var4.field2783 * -1047464521, this.method5753(-1047464521 * var4.field2783, 2063850095)
               );
               this.field2656.add(var5);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gr.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhh;[Lyz;Lhu;Lge;)V")
   @ObfuscatedName("dc")
   void method5691(classHH var1, IndexedSprite[] var2, classHU var3, classGE var4) {
      for (int var5 = 0; var5 < 64; var5++) {
         for (int var6 = 0; var6 < 64; var6++) {
            this.method5702(var5, var6, this.field2667, var1, var3, var4, (byte)104);
            this.method5705(var5, var6, this.field2667, var1, var4, -1017290293);
         }
      }

      for (int var7 = 0; var7 < 64; var7++) {
         for (int var8 = 0; var8 < 64; var8++) {
            this.method5699(var7, var8, this.field2667, var1, var2, (byte)73);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILva;I)Z")
   @ObfuscatedName("aj")
   boolean method5678(int var1, IndexDataBase var2, int var3) {
      try {
         this.field2657.clear();
         int var4 = classDG.method3067(this.field2660 * -551401517, 1312423097 * this.field2654, (byte)-88);
         byte[] var5 = var2.method11942(var4, var1, -1232070465);
         classXY var6 = null;
         if (var5 != null) {
            if (var3 == -172229614) {
               throw new IllegalStateException();
            }

            var6 = new classXY(var5);
         }

         if (null != this.field2667) {
            if (var3 == -172229614) {
               throw new IllegalStateException();
            } else {
               classHA.method5992(this.field2667, var6, (byte)54);
               if (this.field2667.method5988((byte)-86)) {
                  if (var3 == -172229614) {
                     throw new IllegalStateException();
                  } else {
                     this.method5658(0, 0, 64, 64, this.field2667, -1709921586);
                     return true;
                  }
               } else {
                  return false;
               }
            }
         } else {
            boolean var7 = true;
            Iterator var8 = this.field2655.iterator();

            while (var8.hasNext()) {
               if (var3 == -172229614) {
                  throw new IllegalStateException();
               }

               classGF var9 = (classGF)var8.next();
               classHA.method5992(var9, var6, (byte)39);
               var7 &= var9.method5988((byte)-5);
            }

            if (var7) {
               if (var3 == -172229614) {
                  throw new IllegalStateException();
               }

               var8 = this.field2655.iterator();

               while (var8.hasNext()) {
                  if (var3 == -172229614) {
                     throw new IllegalStateException();
                  }

                  classGF var12 = (classGF)var8.next();
                  this.method5658(var12.method5448(-2055683981) * 8, classGF.method5453(var12, -2032103155) * 8, 8, 8, var12, -1841437185);
               }
            }

            return var7;
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "gr.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;[Lyz;B)V")
   @ObfuscatedName("ai")
   void method5699(int var1, int var2, classHA var3, classHH var4, IndexedSprite[] var5, byte var6) {
      try {
         method5717(this, var1, var2, var3, -344925418);
         method5713(this, var1, var2, var3, var5, 801221335);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gr.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILjava/util/HashSet;B)V")
   @ObfuscatedName("aq")
   void method5685(int var1, int var2, int var3, HashSet var4, byte var5) {
      try {
         if (null == var4) {
            if (var5 >= 101) {
               throw new IllegalStateException();
            }

            var4 = new HashSet();
         }

         this.method5721(var1, var2, var4, var3, (byte)3);
         method5745(this, var1, var2, var4, var3, 543832187);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gr.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhh;[Lyz;Lhu;Lge;I)V")
   @ObfuscatedName("au")
   void method5692(classHH var1, IndexedSprite[] var2, classHU var3, classGE var4, int var5) {
      try {
         for (int var6 = 0; var6 < 64; var6++) {
            if (var5 >= -711998756) {
               return;
            }

            for (int var7 = 0; var7 < 64; var7++) {
               if (var5 >= -711998756) {
                  throw new IllegalStateException();
               }

               this.method5702(var6, var7, this.field2667, var1, var3, var4, (byte)77);
               this.method5705(var6, var7, this.field2667, var1, var4, -1017290293);
            }
         }

         for (int var9 = 0; var9 < 64; var9++) {
            if (var5 >= -711998756) {
               throw new IllegalStateException();
            }

            for (int var10 = 0; var10 < 64; var10++) {
               if (var5 >= -711998756) {
                  return;
               }

               this.method5699(var9, var10, this.field2667, var1, var2, (byte)127);
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "gr.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhh;[Lyz;Lhu;Lge;I)V")
   @ObfuscatedName("ap")
   void method5695(classHH var1, IndexedSprite[] var2, classHU var3, classGE var4, int var5) {
      try {
         Iterator var6 = this.field2655.iterator();

         while (var6.hasNext()) {
            if (var5 >= -1426728943) {
               throw new IllegalStateException();
            }

            classGF var7 = (classGF)var6.next();

            for (int var8 = var7.method5448(-1979286425) * 8; var8 < var7.method5448(-1824743324) * 8 + 8; var8++) {
               if (var5 >= -1426728943) {
                  throw new IllegalStateException();
               }

               for (int var9 = classGF.method5453(var7, -2041482250) * 8; var9 < classGF.method5453(var7, -2126916529) * 8 + 8; var9++) {
                  if (var5 >= -1426728943) {
                     throw new IllegalStateException();
                  }

                  this.method5702(var8, var9, var7, var1, var3, var4, (byte)116);
                  this.method5705(var8, var9, var7, var1, var4, -1017290293);
               }
            }
         }

         var6 = this.field2655.iterator();

         while (var6.hasNext()) {
            if (var5 >= -1426728943) {
               return;
            }

            classGF var12 = (classGF)var6.next();

            for (int var13 = var12.method5448(-1901635223) * 8; var13 < var12.method5448(-2137283529) * 8 + 8; var13++) {
               if (var5 >= -1426728943) {
                  return;
               }

               for (int var14 = classGF.method5453(var12, -2077628961) * 8; var14 < classGF.method5453(var12, -2054737183) * 8 + 8; var14++) {
                  if (var5 >= -1426728943) {
                     throw new IllegalStateException();
                  }

                  this.method5699(var13, var14, var12, var1, var2, (byte)79);
               }
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "gr.au(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;)Z")
   @ObfuscatedName("bd")
   static boolean method5647(ObjectComposition var0) {
      if (var0.field4928 != null) {
         int[] var1 = var0.field4928;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            ObjectComposition var4 = classMU.method7771(var3, 1418227343);
            if (var4.field4951 * 1412472849 != -1) {
               return true;
            }
         }
      } else if (1412472849 * var0.field4951 != -1) {
         return true;
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;Lhu;Lge;B)V")
   @ObfuscatedName("ar")
   void method5702(int var1, int var2, classHA var3, classHH var4, classHU var5, classGE var6, byte var7) {
      try {
         int var8 = var3.field2756[0][var1][var2] - 1;
         int var9 = var3.field2757[0][var1][var2] - 1;
         if (-1 == var8) {
            if (var7 <= 0) {
               throw new IllegalStateException();
            }

            if (-1 == var9) {
               if (var7 <= 0) {
                  throw new IllegalStateException();
               }

               classYW.method13737(
                  680806733 * this.field2665 * var1,
                  680806733 * this.field2665 * (63 - var2),
                  680806733 * this.field2665,
                  this.field2665 * 680806733,
                  288074485 * this.field2658
               );
            }
         }

         int var10 = 16711935;
         if (var9 != -1) {
            var10 = classPS.method9221(var9, 288074485 * this.field2658, var6, -2092820255);
         }

         if (var9 > -1) {
            if (var7 <= 0) {
               throw new IllegalStateException();
            }

            if (0 == var3.field2755[0][var1][var2]) {
               if (var7 <= 0) {
                  throw new IllegalStateException();
               }

               classYW.method13737(
                  680806733 * this.field2665 * var1, (63 - var2) * this.field2665 * 680806733, this.field2665 * 680806733, this.field2665 * 680806733, var10
               );
               return;
            }
         }

         int var11 = this.method5709(var1, var2, var3, var5, -1255554545);
         if (var9 == -1) {
            if (var7 <= 0) {
               throw new IllegalStateException();
            } else {
               classYW.method13737(
                  this.field2665 * 680806733 * var1, (63 - var2) * this.field2665 * 680806733, 680806733 * this.field2665, this.field2665 * 680806733, var11
               );
            }
         } else {
            classHH.method6048(
               var4,
               var1 * this.field2665 * 680806733,
               (63 - var2) * this.field2665 * 680806733,
               var11,
               var10,
               this.field2665 * 680806733,
               this.field2665 * 680806733,
               var3.field2755[0][var1][var2],
               var3.field2754[0][var1][var2],
               980481800
            );
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "gr.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;Lge;I)V")
   @ObfuscatedName("aw")
   void method5705(int var1, int var2, classHA var3, classHH var4, classGE var5, int var6) {
      try {
         for (int var7 = 1; var7 < var3.field2750 * -1667236931; var7++) {
            if (var6 != -1017290293) {
               throw new IllegalStateException();
            }

            int var8 = var3.field2757[var7][var1][var2] - 1;
            if (var8 > -1) {
               if (var6 != -1017290293) {
                  throw new IllegalStateException();
               }

               int var9 = classPS.method9221(var8, this.field2658 * 288074485, var5, -1517689602);
               if (0 == var3.field2755[var7][var1][var2]) {
                  if (var6 != -1017290293) {
                     return;
                  }

                  classYW.method13737(
                     this.field2665 * 680806733 * var1, (63 - var2) * 680806733 * this.field2665, this.field2665 * 680806733, this.field2665 * 680806733, var9
                  );
               } else {
                  classHH.method6048(
                     var4,
                     var1 * this.field2665 * 680806733,
                     (63 - var2) * this.field2665 * 680806733,
                     0,
                     var9,
                     680806733 * this.field2665,
                     680806733 * this.field2665,
                     var3.field2755[var7][var1][var2],
                     var3.field2754[var7][var1][var2],
                     980481800
                  );
               }
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "gr.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhu;I)I")
   @ObfuscatedName("ak")
   int method5709(int var1, int var2, classHA var3, classHU var4, int var5) {
      try {
         if (var3.field2756[0][var1][var2] == 0) {
            if (var5 != -1255554545) {
               throw new IllegalStateException();
            } else {
               return 288074485 * this.field2658;
            }
         } else {
            return var4.method6152(var1, var2, (byte)6);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gr.ak(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IILjava/util/HashSet;IB)V")
   @ObfuscatedName("an")
   void method5721(int var1, int var2, HashSet var3, int var4, byte var5) {
      try {
         float var6 = var4 / 64.0F;
         float var7 = var6 / 2.0F;
         Iterator var8 = this.field2657.entrySet().iterator();

         while (var8.hasNext()) {
            if (var5 != 3) {
               return;
            }

            Entry var9 = (Entry)var8.next();
            classKY var10 = (classKY)var9.getKey();
            int var11 = (int)(var6 * (var10.field4053 * 1870919123) + var1 - var7);
            int var12 = (int)(var2 + var4 - var6 * (819227381 * var10.field4054) - var7);
            WorldMapIcon var13 = (WorldMapIcon)var9.getValue();
            if (var13 != null) {
               if (var5 != 3) {
                  throw new IllegalStateException();
               }

               if (var13.method6026((byte)-52)) {
                  if (var5 != 3) {
                     return;
                  }

                  var13.field2769 = 1179114429 * var11;
                  var13.field2770 = var12 * 1689733203;
                  classPS var14 = SceneTilePaint.method4817(var13.vmethod486((byte)-115), (byte)-66);
                  if (!var3.contains(classPS.method9241(var14, (byte)0))) {
                     if (var5 != 3) {
                        throw new IllegalStateException();
                     }

                     this.method5730(var13, var11, var12, var6, (byte)32);
                  }
               }
            }
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "gr.an(" + ')');
      }
   }

   WorldMapRegion(int var1, int var2, int var3, HashMap var4) {
      this.field2660 = var1 * 584755291;
      this.field2654 = var2 * 1000294793;
      this.field2655 = new LinkedList();
      this.field2656 = new LinkedList();
      this.field2657 = new HashMap();
      this.field2658 = -612224675 * (var3 | 0xFF000000);
      this.field2659 = var4;
   }

   @ObfuscatedSignature(descriptor = "(Lhd;Lps;IIF)V")
   @ObfuscatedName("ed")
   void method5739(WorldMapIcon var1, classPS var2, int var3, int var4, float var5) {
      classHW var6 = var1.vmethod491((byte)-36);
      if (null != var6) {
         if (var6.field2899.method6121(var5, 1247117025)) {
            classZV var7 = (classZV)this.field2659.get(var6.field2899);
            classAAO.method225(
               var7,
               var6.field2898,
               var3 - -1499119738 * var6.field2900 / 2,
               var4,
               var6.field2900 * 2116737063,
               2072150010 * var6.field2897,
               -1519185711 | 257956491 * var2.field5406,
               0,
               1,
               0,
               var7.field8 / 2
            );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lps;II)V")
   @ObfuscatedName("ej")
   void method5735(classPS var1, int var2, int var3) {
      SpritePixels var4 = var1.method9236(false, 317703748);
      if (null != var4) {
         int var5 = this.method5748(var4, var1.field5400, 1738483204);
         int var6 = this.method5752(var4, var1.field5395, -431077059);
         var4.method13555(var2 + var5, var3 + var6);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lps;III)V")
   @ObfuscatedName("bx")
   void method5736(classPS var1, int var2, int var3, int var4) {
      try {
         SpritePixels var5 = var1.method9236(false, -1683811326);
         if (null != var5) {
            if (var4 >= 586322231) {
               throw new IllegalStateException();
            }

            int var6 = this.method5748(var5, var1.field5400, 1767348466);
            int var7 = this.method5752(var5, var1.field5395, -129613938);
            var5.method13555(var2 + var6, var3 + var7);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "gr.bx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lol;I)I")
   @ObfuscatedName("bm")
   int method5752(SpritePixels var1, classOL var2, int var3) {
      try {
         switch (-1205275063 * var2.field4915) {
            case 0:
               return -var1.field7007 / 2;
            case 1:
               return 0;
            default:
               return -var1.field7007;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gr.bm(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILha;I)V")
   @ObfuscatedName("ax")
   void method5658(int var1, int var2, int var3, int var4, classHA var5, int var6) {
      try {
         for (int var7 = var1; var7 < var1 + var3; var7++) {
            label74:
            for (int var8 = var2; var8 < var4 + var2; var8++) {
               if (var6 >= -1375923225) {
                  throw new IllegalStateException();
               }

               for (int var9 = 0; var9 < var5.field2750 * -1667236931; var9++) {
                  if (var6 >= -1375923225) {
                     throw new IllegalStateException();
                  }

                  classHZ[] var10 = var5.field2758[var9][var7][var8];
                  if (var10 != null) {
                     if (var10.length == 0) {
                        if (var6 >= -1375923225) {
                           return;
                        }
                     } else {
                        classHZ[] var11 = var10;

                        for (int var12 = 0; var12 < var11.length; var12++) {
                           if (var6 >= -1375923225) {
                              throw new IllegalStateException();
                           }

                           classHZ var13 = var11[var12];
                           ObjectComposition var14 = classMU.method7771(-1831941567 * var13.field2914, 2018249124);
                           if (classUU.method11614(var14, 1217057510)) {
                              method5663(this, var14, var9, var7, var8, var5, -297503953);
                              continue label74;
                           }

                           if (var6 >= -1375923225) {
                              throw new IllegalStateException();
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "gr.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lps;I)Lhw;")
   @ObfuscatedName("bg")
   classHW method5756(classPS var1, int var2) {
      try {
         if (null != var1.field5409) {
            if (var2 <= 1412510579) {
               throw new IllegalStateException();
            }

            if (null != this.field2659) {
               if (var2 <= 1412510579) {
                  throw new IllegalStateException();
               }

               if (this.field2659.get(classHP.field2854) != null) {
                  classHP var3 = classHP.method6119(var1.field5392 * 1698532127, (byte)47);
                  if (var3 == null) {
                     if (var2 <= 1412510579) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }

                  classZV var4 = (classZV)this.field2659.get(var3);
                  if (null == var4) {
                     if (var2 <= 1412510579) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }

                  int var5 = var4.method41(var1.field5409, 1000000);
                  String[] var6 = new String[var5];
                  AbstractFont.method38(var4, var1.field5409, null, var6);
                  int var7 = var4.field8 * var6.length / 2;
                  int var8 = 0;
                  String[] var9 = var6;

                  for (int var10 = 0; var10 < var9.length; var10++) {
                     if (var2 <= 1412510579) {
                        throw new IllegalStateException();
                     }

                     String var11 = var9[var10];
                     int var12 = var4.method33(var11);
                     if (var12 > var8) {
                        if (var2 <= 1412510579) {
                           throw new IllegalStateException();
                        }

                        var8 = var12;
                     }
                  }

                  return new classHW(var1.field5409, var8, var7, var3);
               }
            }
         }

         return null;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "gr.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/util/List;")
   @ObfuscatedName("bk")
   List method5765(int var1) {
      try {
         LinkedList var2 = new LinkedList();
         var2.addAll(this.field2656);
         var2.addAll(this.field2657.values());
         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gr.bk(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("bo")
   void method5768(int var1, int var2, int var3, int var4, int var5) {
      try {
         var3 %= 4;
         if (var3 == 0) {
            if (var5 == 163963853) {
               throw new IllegalStateException();
            }

            classYW.method13762(var1 * this.field2665 * 680806733, this.field2665 * 680806733 * (63 - var2), 680806733 * this.field2665, var4);
         }

         if (var3 == 1) {
            if (var5 == 163963853) {
               return;
            }

            classYW.method13756(var1 * this.field2665 * 680806733, (63 - var2) * this.field2665 * 680806733, this.field2665 * 680806733, var4);
         }

         if (var3 == 2) {
            if (var5 == 163963853) {
               return;
            }

            classYW.method13762(
               680806733 * this.field2665 + this.field2665 * 680806733 * var1 - 1, 680806733 * this.field2665 * (63 - var2), this.field2665 * 680806733, var4
            );
         }

         if (3 == var3) {
            if (var5 == 163963853) {
               return;
            }

            classYW.method13756(
               this.field2665 * 680806733 * var1, 680806733 * this.field2665 + (63 - var2) * this.field2665 * 680806733 - 1, 680806733 * this.field2665, var4
            );
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gr.bo(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;III)V")
   @ObfuscatedName("ba")
   public static void method5651(WorldMapRegion var0, int var1, int var2, int var3) {
      int var5 = -1838604949 * var0.field2660;
      int var6 = var0.field2654 * 1312423097;
      int var7 = var0.field2665 * 1744663631;
      SpritePixels var4 = (SpritePixels)classID.method6185(field2661, classCX.method2699(var5, var6, var7, (byte)1));
      if (null != var4) {
         if (621957952 * var0.field2665 == var3) {
            var4.method13552(var1, var2);
         } else {
            var4.method13604(var1, var2, var3, var3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgr;I)V")
   @ObfuscatedName("oo")
   public static void method5666(WorldMapRegion var0, int var1) {
      if (var0 == null) {
         var0.method5669(var1);
      } else {
         try {
            for (WorldMapIcon var3 : var0.field2657.values()) {
               if (var3 instanceof classGS) {
                  if (var1 == 757968036) {
                     throw new IllegalStateException();
                  }

                  ((classGS)var3).method5771(-1132427880);
               }
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "gr.aa(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Lps;II)V")
   @ObfuscatedName("es")
   public static void method5737(WorldMapRegion var0, classPS var1, int var2, int var3) {
      if (var0 == null) {
         var0.method5735(var1, var2, var2);
      } else {
         SpritePixels var4 = var1.method9236(false, -1534229956);
         if (null != var4) {
            int var5 = var0.method5748(var4, var1.field5400, 1654920068);
            int var6 = var0.method5752(var4, var1.field5395, 696529173);
            var4.method13555(var2 + var5, var3 + var6);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("bu")
   static long method5638(int var0, int var1, int var2) {
      return var2 << 16 | var0 << 8 | var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("ba")
   static long method5639(int var0, int var1, int var2) {
      return var2 << 16 | var0 << 8 | var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("bw")
   static long method5640(int var0, int var1, int var2) {
      return var2 << 16 | var0 << 8 | var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)I")
   @ObfuscatedName("ub")
   public static int method5734(classLH var0) {
      return var0.field4157.method13974(-1321894998);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;IILha;[Lyz;)V")
   @ObfuscatedName("zr")
   public static void method5712(WorldMapRegion var0, int var1, int var2, classHA var3, IndexedSprite[] var4) {
      if (var0 == null) {
         var0.method5769();
      }

      for (int var5 = 0; var5 < -1667236931 * var3.field2750; var5++) {
         classHZ[] var6 = var3.field2758[var5][var1][var2];
         if (null != var6 && var6.length != 0) {
            classHZ[] var7 = var6;

            for (int var8 = 0; var8 < var7.length; var8++) {
               classHZ var9 = var7[var8];
               int var11 = var9.field2912 * 1253547473;
               boolean var10 = var11 >= classKB.field3519.field3536 * 78062377 && var11 <= 78062377 * classKB.field3524.field3536;
               if (!var10) {
                  int var13 = var9.field2912 * 1253547473;
                  boolean var12 = var13 == 78062377 * classKB.field3535.field3536;
                  if (!var12) {
                     continue;
                  }
               }

               ObjectComposition var14 = classMU.method7771(var9.field2914 * -1831941567, 1924964978);
               if (-1 != var14.field4952 * 1680279607) {
                  int var15 = var9.field2913 * 374409179 != 1 && 3 != var9.field2913 * 374409179 ? 402876423 * var14.field4922 : -582306509 * var14.field4942;
                  var4[1680279607 * var14.field4952]
                     .method13856(
                        var1 * 680806733 * var0.field2665,
                        (64 - var15 - var2) * var0.field2665 * 680806733,
                        var0.field2665 * 1361613466,
                        var0.field2665 * 1361613466
                     );
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhd;IIFB)V")
   @ObfuscatedName("bn")
   void method5730(WorldMapIcon var1, int var2, int var3, float var4, byte var5) {
      try {
         classPS var6 = SceneTilePaint.method4817(var1.vmethod486((byte)-68), (byte)-77);
         this.method5736(var6, var2, var3, 148705509);
         this.method5741(var1, var6, var2, var3, var4, 364928686);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gr.bn(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("be")
   public static void method5642() {
      field2661.method6200(5);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyc;Lrr;Lrr;)I")
   @ObfuscatedName("is")
   public static int method5637(classYC var0, Nameable var1, Nameable var2) {
      if (var0 == null) {
         return var0.method13354(var1, var1);
      } else {
         return var0.field6963 ? var1.vmethod562(var2, -30374874) : var2.vmethod562(var1, -30374874);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("by")
   public static void method5643() {
      field2661.method6200(5);
   }

   public Collection getMapIcons() {
      return Collections.unmodifiableCollection(this.method5769().values());
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   public static void method5644() {
      field2661.method6200(5);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   public static void method5645() {
      field2661.method6200(5);
   }

   @ObfuscatedSignature(descriptor = "(Lgr;IILha;I)V")
   @ObfuscatedName("oy")
   public static void method5717(WorldMapRegion var0, int var1, int var2, classHA var3, int var4) {
      if (var0 == null) {
         var0.method5719(var1, var1, var3, var1);
      } else {
         try {
            for (int var5 = 0; var5 < -1667236931 * var3.field2750; var5++) {
               if (var4 >= 1860831095) {
                  throw new IllegalStateException();
               }

               classHZ[] var6 = var3.field2758[var5][var1][var2];
               if (var6 != null) {
                  if (var4 >= 1860831095) {
                     throw new IllegalStateException();
                  }

                  if (0 == var6.length) {
                     if (var4 >= 1860831095) {
                        return;
                     }
                  } else {
                     classHZ[] var7 = var6;

                     for (int var8 = 0; var8 < var7.length; var8++) {
                        if (var4 >= 1860831095) {
                           throw new IllegalStateException();
                        }

                        classHZ var9 = var7[var8];
                        if (classJW.method6453(var9.field2912 * 1253547473, 1644747895)) {
                           if (var4 >= 1860831095) {
                              throw new IllegalStateException();
                           }

                           ObjectComposition var10 = classMU.method7771(var9.field2914 * -1831941567, 1881626262);
                           int var10000;
                           if (var10.field4921 * 1663954033 != 0) {
                              if (var4 >= 1860831095) {
                                 throw new IllegalStateException();
                              }

                              var10000 = -3407872;
                           } else {
                              var10000 = -3355444;
                           }

                           int var11 = var10000;
                           if (classKB.field3513.field3536 * 78062377 == var9.field2912 * 1253547473) {
                              var0.method5768(var1, var2, var9.field2913 * 374409179, var11, 1423521969);
                           }

                           if (classKB.field3515.field3536 * 78062377 == var9.field2912 * 1253547473) {
                              if (var4 >= 1860831095) {
                                 throw new IllegalStateException();
                              }

                              var0.method5768(var1, var2, 374409179 * var9.field2913, -3355444, -1893756618);
                              var0.method5768(var1, var2, 374409179 * var9.field2913 + 1, var11, 1240727397);
                           }

                           if (var9.field2912 * 1253547473 == 78062377 * classKB.field3516.field3536) {
                              if (var4 >= 1860831095) {
                                 throw new IllegalStateException();
                              }

                              if (var9.field2913 * 374409179 == 0) {
                                 if (var4 >= 1860831095) {
                                    throw new IllegalStateException();
                                 }

                                 classYW.method13756(var1 * var0.field2665 * 680806733, 680806733 * var0.field2665 * (63 - var2), 1, var11);
                              }

                              if (1 == var9.field2913 * 374409179) {
                                 if (var4 >= 1860831095) {
                                    throw new IllegalStateException();
                                 }

                                 classYW.method13756(
                                    680806733 * var0.field2665 + var1 * var0.field2665 * 680806733 - 1, var0.field2665 * 680806733 * (63 - var2), 1, var11
                                 );
                              }

                              if (374409179 * var9.field2913 == 2) {
                                 if (var4 >= 1860831095) {
                                    throw new IllegalStateException();
                                 }

                                 classYW.method13756(
                                    680806733 * var0.field2665 + var0.field2665 * 680806733 * var1 - 1,
                                    (63 - var2) * 680806733 * var0.field2665 + var0.field2665 * 680806733 - 1,
                                    1,
                                    var11
                                 );
                              }

                              if (var9.field2913 * 374409179 == 3) {
                                 if (var4 >= 1860831095) {
                                    return;
                                 }

                                 classYW.method13756(
                                    var1 * var0.field2665 * 680806733, var0.field2665 * 680806733 + var0.field2665 * 680806733 * (63 - var2) - 1, 1, var11
                                 );
                              }
                           }

                           if (1253547473 * var9.field2912 == 78062377 * classKB.field3517.field3536) {
                              int var12 = var9.field2913 * 374409179 % 2;
                              if (0 == var12) {
                                 if (var4 >= 1860831095) {
                                    throw new IllegalStateException();
                                 }

                                 for (int var15 = 0; var15 < 680806733 * var0.field2665; var15++) {
                                    if (var4 >= 1860831095) {
                                       return;
                                    }

                                    classYW.method13756(
                                       var1 * var0.field2665 * 680806733 + var15, var0.field2665 * 680806733 * (64 - var2) - 1 - var15, 1, var11
                                    );
                                 }
                              } else {
                                 for (int var13 = 0; var13 < 680806733 * var0.field2665; var13++) {
                                    if (var4 >= 1860831095) {
                                       throw new IllegalStateException();
                                    }

                                    classYW.method13756(var13 + var1 * 680806733 * var0.field2665, var13 + var0.field2665 * 680806733 * (63 - var2), 1, var11);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         } catch (RuntimeException var14) {
            throw classEG.method3899(var14, "gr.at(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;)Z")
   @ObfuscatedName("bs")
   static boolean method5648(ObjectComposition var0) {
      if (var0.field4928 != null) {
         int[] var1 = var0.field4928;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            ObjectComposition var4 = classMU.method7771(var3, 2014570797);
            if (var4.field4951 * -990302710 != -1) {
               return true;
            }
         }
      } else if (-1944388660 * var0.field4951 != -1) {
         return true;
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(Lgr;IILha;[Lyz;I)V")
   @ObfuscatedName("sz")
   public static void method5713(WorldMapRegion var0, int var1, int var2, classHA var3, IndexedSprite[] var4, int var5) {
      if (var0 == null) {
         var0.method5716(var1, var1, var3, var4, var1);
      } else {
         try {
            for (int var6 = 0; var6 < -1667236931 * var3.field2750; var6++) {
               if (var5 != 801221335) {
                  return;
               }

               classHZ[] var7 = var3.field2758[var6][var1][var2];
               if (null != var7) {
                  if (var5 != 801221335) {
                     throw new IllegalStateException();
                  }

                  if (var7.length != 0) {
                     classHZ[] var8 = var7;

                     for (int var9 = 0; var9 < var8.length; var9++) {
                        classHZ var10;
                        boolean var10000;
                        label109: {
                           var10 = var8[var9];
                           int var12 = var10.field2912 * 1253547473;
                           if (var12 >= classKB.field3519.field3536 * 78062377) {
                              if (var5 != 801221335) {
                                 throw new IllegalStateException();
                              }

                              if (var12 <= 78062377 * classKB.field3524.field3536) {
                                 if (var5 != 801221335) {
                                    throw new IllegalStateException();
                                 }

                                 var10000 = 1;
                                 break label109;
                              }
                           }

                           var10000 = 0;
                        }

                        boolean var11 = (boolean)var10000;
                        if (!var11) {
                           if (var5 != 801221335) {
                              throw new IllegalStateException();
                           }

                           int var14 = var10.field2912 * 1253547473;
                           if (var14 == 78062377 * classKB.field3535.field3536) {
                              if (var5 != 801221335) {
                                 throw new IllegalStateException();
                              }

                              var10000 = (boolean)1;
                           } else {
                              var10000 = (boolean)0;
                           }

                           boolean var13 = var10000;
                           if (!var13) {
                              if (var5 != 801221335) {
                                 throw new IllegalStateException();
                              }
                              continue;
                           }
                        }

                        ObjectComposition var16 = classMU.method7771(var10.field2914 * -1831941567, 1639553332);
                        if (-1 != var16.field4952 * 1680279607) {
                           if (var5 != 801221335) {
                              throw new IllegalStateException();
                           }

                           label95: {
                              if (var10.field2913 * 374409179 != 1) {
                                 if (var5 != 801221335) {
                                    throw new IllegalStateException();
                                 }

                                 if (3 != var10.field2913 * 374409179) {
                                    var10000 = 402876423 * var16.field4922;
                                    break label95;
                                 }

                                 if (var5 != 801221335) {
                                    throw new IllegalStateException();
                                 }
                              }

                              var10000 = -582306509 * var16.field4942;
                           }

                           int var17 = var10000;
                           var4[1680279607 * var16.field4952]
                              .method13856(
                                 var1 * 680806733 * var0.field2665,
                                 (64 - var17 - var2) * var0.field2665 * 680806733,
                                 var0.field2665 * 1361613466,
                                 var0.field2665 * 1361613466
                              );
                        }
                     }
                  }
               }
            }
         } catch (RuntimeException var15) {
            throw classEG.method3899(var15, "gr.av(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILha;)V")
   @ObfuscatedName("cq")
   void method5659(int var1, int var2, int var3, int var4, classHA var5) {
      for (int var6 = var1; var6 < var1 + var3; var6++) {
         label53:
         for (int var7 = var2; var7 < var4 + var2; var7++) {
            for (int var8 = 0; var8 < var5.field2750 * 1618039302; var8++) {
               classHZ[] var9 = var5.field2758[var8][var6][var7];
               if (var9 != null && var9.length != 0) {
                  classHZ[] var10 = var9;

                  for (int var11 = 0; var11 < var10.length; var11++) {
                     classHZ var12 = var10[var11];
                     ObjectComposition var13 = classMU.method7771(-1759172876 * var12.field2914, 1439231117);
                     if (classUU.method11614(var13, 1217057510)) {
                        method5663(this, var13, var8, var6, var7, var5, 602222571);
                        continue label53;
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("al")
   void method5676(byte var1) {
      try {
         if (null != this.field2667) {
            if (var1 <= 31) {
               throw new IllegalStateException();
            }

            this.field2667.method5997((byte)-79);
         } else {
            Iterator var2 = this.field2655.iterator();

            while (var2.hasNext()) {
               if (var1 <= 31) {
                  throw new IllegalStateException();
               }

               classGF var3 = (classGF)var2.next();
               var3.method5997((byte)-23);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gr.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bv")
   void method5652(int var1, int var2, int var3) {
      int var5 = -551401517 * this.field2660;
      int var6 = this.field2654 * 1312423097;
      int var7 = this.field2665 * 680806733;
      SpritePixels var4 = (SpritePixels)classID.method6185(field2661, classCX.method2699(var5, var6, var7, (byte)1));
      if (null != var4) {
         if (621957952 * this.field2665 == var3) {
            var4.method13552(var1, var2);
         } else {
            var4.method13604(var1, var2, var3, var3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lhw;")
   @ObfuscatedName("bt")
   classHW method5753(int var1, int var2) {
      try {
         classPS var3 = SceneTilePaint.method4817(var1, (byte)-98);
         return this.method5756(var3, 1467959558);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gr.bt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgr;IILjava/util/HashSet;II)V")
   @ObfuscatedName("fg")
   public static void method5745(WorldMapRegion var0, int var1, int var2, HashSet var3, int var4, int var5) {
      if (var0 == null) {
         var0.method5746(var1, var1, var3, var1, var1);
      }

      try {
         float var6 = var4 / 64.0F;
         Iterator var7 = var0.field2656.iterator();

         while (var7.hasNext()) {
            if (var5 != 543832187) {
               return;
            }

            WorldMapIcon var8 = (WorldMapIcon)var7.next();
            if (!var8.method6026((byte)-115)) {
               if (var5 != 543832187) {
                  throw new IllegalStateException();
               }
            } else {
               int var9 = 1870919123 * var8.field2767.field4053 % 64;
               int var10 = 819227381 * var8.field2767.field4054 % 64;
               var8.field2769 = (int)(var1 + var9 * var6) * 1179114429;
               var8.field2770 = 1689733203 * (int)(var2 + var6 * (63 - var10));
               if (var3.contains(var8.vmethod486((byte)-86))) {
                  if (var5 != 543832187) {
                     return;
                  }
               } else {
                  var0.method5730(var8, 797816725 * var8.field2769, var8.field2770 * 655244251, var6, (byte)32);
               }
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "gr.bp(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;IIIII)Ljava/util/List;")
   @ObfuscatedName("pd")
   public static List method5759(WorldMapRegion var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         return var0.method5764(var1, var1, var1, var1, var1);
      } else {
         LinkedList var6 = new LinkedList();
         if (var4 >= var1 && var5 >= var2) {
            if (var4 < var1 + var3 && var5 < var2 + var3) {
               for (WorldMapIcon var8 : var0.field2657.values()) {
                  if (var8.method6026((byte)-51) && var8.method6024(var4, var5, (byte)-96)) {
                     var6.add(var8);
                  }
               }

               for (WorldMapIcon var10 : var0.field2656) {
                  if (var10.method6026((byte)-111) && var10.method6024(var4, var5, (byte)-23)) {
                     var6.add(var10);
                  }
               }

               return var6;
            } else {
               return var6;
            }
         } else {
            return var6;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgr;IIIIII)Ljava/util/List;")
   @ObfuscatedName("xu")
   public static List method5760(WorldMapRegion var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var0 == null) {
         var0.method5763(var1, var1, var1, var1, var1, var1);
      }

      try {
         LinkedList var7 = new LinkedList();
         if (var4 >= var1) {
            if (var6 <= -673752481) {
               throw new IllegalStateException();
            }

            if (var5 >= var2) {
               if (var4 < var1 + var3) {
                  if (var6 <= -673752481) {
                     throw new IllegalStateException();
                  }

                  if (var5 < var2 + var3) {
                     Iterator var8 = var0.field2657.values().iterator();

                     while (var8.hasNext()) {
                        if (var6 <= -673752481) {
                           throw new IllegalStateException();
                        }

                        WorldMapIcon var9 = (WorldMapIcon)var8.next();
                        if (var9.method6026((byte)-4)) {
                           if (var6 <= -673752481) {
                              throw new IllegalStateException();
                           }

                           if (var9.method6024(var4, var5, (byte)-65)) {
                              var7.add(var9);
                           }
                        }
                     }

                     var8 = var0.field2656.iterator();

                     while (var8.hasNext()) {
                        if (var6 <= -673752481) {
                           throw new IllegalStateException();
                        }

                        WorldMapIcon var12 = (WorldMapIcon)var8.next();
                        if (var12.method6026((byte)-54)) {
                           if (var6 <= -673752481) {
                              throw new IllegalStateException();
                           }

                           if (var12.method6024(var4, var5, (byte)-44)) {
                              var7.add(var12);
                           }
                        }
                     }

                     return var7;
                  }
               }

               return var7;
            }

            if (var6 <= -673752481) {
               throw new IllegalStateException();
            }
         }

         return var7;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "gr.bj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cn")
   void method5677() {
      if (null != this.field2667) {
         this.field2667.method5997((byte)40);
      } else {
         for (classGF var2 : this.field2655) {
            var2.method5997((byte)-91);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgl;Ljava/util/List;)V")
   @ObfuscatedName("cu")
   void method5655(classGL var1, List var2) {
      this.field2657.clear();
      this.field2667 = var1;
      this.method5670(var2, (byte)24);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Ljava/util/List;)V")
   @ObfuscatedName("ay")
   public static void method5671(WorldMapRegion var0, List var1) {
      if (var0 == null) {
         var0.getMapIcons();
      }

      for (classHF var3 : var1) {
         if (SceneTilePaint.method4817(var3.field2783 * -1047464521, (byte)-92).field5390
            && 1870919123 * var3.field2767.field4053 >> 6 == var0.field2660 * -551401517
            && var0.field2654 * 1312423097 == 819227381 * var3.field2767.field4054 >> 6) {
            classHF var4 = new classHF(var3.field2767, var3.field2767, var3.field2783 * -1047464521, var0.method5753(-1047464521 * var3.field2783, 2130223302));
            var0.field2656.add(var4);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILha;)V")
   @ObfuscatedName("cl")
   void method5660(int var1, int var2, int var3, int var4, classHA var5) {
      for (int var6 = var1; var6 < var1 + var3; var6++) {
         label56:
         for (int var7 = var2; var7 < var4 + var2; var7++) {
            for (int var8 = 0; var8 < var5.field2750 * -1667236931; var8++) {
               classHZ[] var9 = var5.field2758[var8][var6][var7];
               if (var9 != null && var9.length != 0) {
                  classHZ[] var10 = var9;

                  for (int var11 = 0; var11 < var10.length; var11++) {
                     classHZ var12 = var10[var11];
                     ObjectComposition var13 = classMU.method7771(-2066145620 * var12.field2914, 1893475433);
                     if (classUU.method11614(var13, 1217057510)) {
                        method5663(this, var13, var8, var6, var7, var5, 1424067734);
                        continue label56;
                     }
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;IIIILha;)V")
   @ObfuscatedName("lw")
   public static void method5661(WorldMapRegion var0, int var1, int var2, int var3, int var4, classHA var5) {
      if (var0 == null) {
         var0.method5768(var1, var1, var1, var1, var1);
      }

      for (int var6 = var1; var6 < var1 + var3; var6++) {
         label60:
         for (int var7 = var2; var7 < var4 + var2; var7++) {
            for (int var8 = 0; var8 < var5.field2750 * -1667236931; var8++) {
               classHZ[] var9 = var5.field2758[var8][var6][var7];
               if (var9 != null && var9.length != 0) {
                  classHZ[] var10 = var9;

                  for (int var11 = 0; var11 < var10.length; var11++) {
                     classHZ var12 = var10[var11];
                     ObjectComposition var13 = classMU.method7771(-1831941567 * var12.field2914, 1775677517);
                     if (classUU.method11614(var13, 1217057510)) {
                        method5663(var0, var13, var8, var6, var7, var5, 82368089);
                        continue label60;
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lpe;I)I")
   @ObfuscatedName("br")
   int method5748(SpritePixels var1, classPE var2, int var3) {
      try {
         switch (1700578181 * var2.field5229) {
            case 0:
               return 0;
            case 1:
               return -var1.field7005 / 2;
            default:
               return -var1.field7005;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gr.br(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cy")
   void method5667() {
      for (WorldMapIcon var2 : this.field2657.values()) {
         if (var2 instanceof classGS) {
            ((classGS)var2).method5771(-2077744068);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/List;)V")
   @ObfuscatedName("co")
   void method5672(List var1) {
      for (classHF var3 : var1) {
         if (SceneTilePaint.method4817(var3.field2783 * -1047464521, (byte)-83).field5390
            && 1870919123 * var3.field2767.field4053 >> 6 == this.field2660 * -551401517
            && this.field2654 * 1312423097 == 819227381 * var3.field2767.field4054 >> 6) {
            classHF var4 = new classHF(var3.field2767, var3.field2767, var3.field2783 * -1047464521, this.method5753(-1047464521 * var3.field2783, 1965611735));
            this.field2656.add(var4);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhh;[Lyz;Lhu;Lge;)V")
   @ObfuscatedName("dm")
   void method5696(classHH var1, IndexedSprite[] var2, classHU var3, classGE var4) {
      for (classGF var6 : this.field2655) {
         for (int var7 = var6.method5448(-2085738907) * 8; var7 < var6.method5448(-1855988952) * 8 + 8; var7++) {
            for (int var8 = classGF.method5453(var6, -2092448097) * 8; var8 < classGF.method5453(var6, -2094425218) * 8 + 8; var8++) {
               this.method5702(var7, var8, var6, var1, var3, var4, (byte)6);
               this.method5705(var7, var8, var6, var1, var4, -1017290293);
            }
         }
      }

      for (classGF var10 : this.field2655) {
         for (int var11 = var10.method5448(-1984218074) * 8; var11 < var10.method5448(-1704589692) * 8 + 8; var11++) {
            for (int var12 = classGF.method5453(var10, -2036439783) * 8; var12 < classGF.method5453(var10, -2122505821) * 8 + 8; var12++) {
               this.method5699(var11, var12, var10, var1, var2, (byte)86);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Lgl;Ljava/util/List;)V")
   @ObfuscatedName("js")
   public static void method5656(WorldMapRegion var0, classGL var1, List var2) {
      var0.field2657.clear();
      var0.field2667 = var1;
      var0.method5670(var2, (byte)-27);
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;Lhu;Lge;)V")
   @ObfuscatedName("dt")
   void method5703(int var1, int var2, classHA var3, classHH var4, classHU var5, classGE var6) {
      int var7 = var3.field2756[0][var1][var2] - 1;
      int var8 = var3.field2757[0][var1][var2] - 1;
      if (-1 == var7 && -1 == var8) {
         classYW.method13737(
            680806733 * this.field2665 * var1,
            680806733 * this.field2665 * (63 - var2),
            680806733 * this.field2665,
            this.field2665 * 680806733,
            288074485 * this.field2658
         );
      }

      int var9 = 16711935;
      if (var8 != -1) {
         var9 = classPS.method9221(var8, 288074485 * this.field2658, var6, -1378665219);
      }

      if (var8 > -1 && 0 == var3.field2755[0][var1][var2]) {
         classYW.method13737(
            680806733 * this.field2665 * var1, (63 - var2) * this.field2665 * 680806733, this.field2665 * 680806733, this.field2665 * 680806733, var9
         );
      } else {
         int var10 = this.method5709(var1, var2, var3, var5, -1255554545);
         if (var8 == -1) {
            classYW.method13737(
               this.field2665 * 680806733 * var1, (63 - var2) * this.field2665 * 680806733, 680806733 * this.field2665, this.field2665 * 680806733, var10
            );
         } else {
            classHH.method6048(
               var4,
               var1 * this.field2665 * 680806733,
               (63 - var2) * this.field2665 * 680806733,
               var10,
               var9,
               this.field2665 * 680806733,
               this.field2665 * 680806733,
               var3.field2755[0][var1][var2],
               var3.field2754[0][var1][var2],
               980481800
            );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;Lge;)V")
   @ObfuscatedName("dw")
   void method5706(int var1, int var2, classHA var3, classHH var4, classGE var5) {
      for (int var6 = 1; var6 < var3.field2750 * -1667236931; var6++) {
         int var7 = var3.field2757[var6][var1][var2] - 1;
         if (var7 > -1) {
            int var8 = classPS.method9221(var7, this.field2658 * 288074485, var5, -1313828822);
            if (0 == var3.field2755[var6][var1][var2]) {
               classYW.method13737(
                  this.field2665 * 28285155 * var1,
                  (-349447874 - var2) * 680806733 * this.field2665,
                  this.field2665 * 937426308,
                  this.field2665 * 680806733,
                  var8
               );
            } else {
               classHH.method6048(
                  var4,
                  var1 * this.field2665 * 1730859881,
                  (63 - var2) * this.field2665 * 680806733,
                  0,
                  var8,
                  680806733 * this.field2665,
                  680806733 * this.field2665,
                  var3.field2755[var6][var1][var2],
                  var3.field2754[var6][var1][var2],
                  980481800
               );
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;ILva;)Z")
   @ObfuscatedName("tv")
   public static boolean method5679(WorldMapRegion var0, int var1, IndexDataBase var2) {
      var0.field2657.clear();
      int var3 = classDG.method3067(var0.field2660 * -551401517, 1312423097 * var0.field2654, (byte)-43);
      byte[] var4 = var2.method11942(var3, var1, -1441875101);
      classXY var5 = null;
      if (var4 != null) {
         var5 = new classXY(var4);
      }

      if (null != var0.field2667) {
         classHA.method5992(var0.field2667, var5, (byte)61);
         if (var0.field2667.method5988((byte)-6)) {
            var0.method5658(0, 0, 64, 64, var0.field2667, -1645911389);
            return true;
         } else {
            return false;
         }
      } else {
         boolean var6 = true;

         for (classGF var8 : var0.field2655) {
            classHA.method5992(var8, var5, (byte)88);
            var6 &= var8.method5988((byte)17);
         }

         if (var6) {
            for (classGF var10 : var0.field2655) {
               var0.method5658(var10.method5448(-1996254876) * 8, classGF.method5453(var10, -2029911932) * 8, 8, 8, var10, -1478433216);
            }
         }

         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lps;IIIII)V")
   @ObfuscatedName("ah")
   void method5727(classPS var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         SpritePixels var7 = var1.method9236(false, -1113911178);
         if (null == var7) {
            if (var6 >= 1564893887) {
               throw new IllegalStateException();
            }
         } else {
            var7.method13555(var2 - var7.field7005 / 2, var3 - var7.field7007 / 2);
            if (var4 % var5 < var5 / 2) {
               if (var6 >= 1564893887) {
                  return;
               }

               classYW.method13728(var2, var3, 15, 16776960, 128);
               classYW.method13728(var2, var3, 7, 16777215, 256);
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "gr.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;ILhh;[Lyz;Lva;Lva;D)V")
   @ObfuscatedName("ay")
   void method5682(WorldMapData var1, int var2, classHH var3, IndexedSprite[] var4, IndexDataBase var5, IndexDataBase var6, double var7) {
      try {
         this.field2665 = -908060795 * var2;
         if (null != this.field2667 || !this.field2655.isEmpty()) {
            int var10 = -551401517 * this.field2660;
            int var11 = this.field2654 * 1312423097;
            SpritePixels var9 = (SpritePixels)classID.method6185(field2661, classCX.method2699(var10, var11, var2, (byte)1));
            if (null == var9) {
               if (classFH.method4722() != var7) {
                  classFH.method4714(var7);
               }

               boolean var12 = true;
               var12 &= this.method5678(var1.method5876(-608589044), var5, 1285314194);
               int var14 = classDG.method3067(this.field2660 * -551401517, this.field2654 * 1312423097, (byte)59);
               var12 &= var6.method11940(var14, -906797091);
               if (var12) {
                  byte[] var13 = var6.method11942(var14, var1.method5876(-608589044), -1555239139);
                  classHU var15;
                  if (var13 == null) {
                     var15 = new classHU();
                  } else {
                     SpritePixels var16 = classIT.method6356(var13, 1820082454);
                     if (null == var16) {
                        var15 = new classHU();
                     } else {
                        var15 = new classHU(var16.field7003);
                     }
                  }

                  SpritePixels var17 = new SpritePixels(621957952 * this.field2665, 621957952 * this.field2665);
                  SpritePixels.method13526(var17);
                  if (null != this.field2667) {
                     this.method5692(var3, var4, var15, new classHQ(), -717963337);
                  } else {
                     this.method5695(var3, var4, var15, new classHQ(), -2049758853);
                  }

                  int var18 = -551401517 * this.field2660;
                  int var19 = this.field2654 * 1312423097;
                  int var20 = 680806733 * this.field2665;
                  field2661.method6197(var17, classCX.method2699(var18, var19, var20, (byte)1), 4 * var17.field7003.length);
                  this.method5676((byte)61);
               }
            }
         }
      } catch (RuntimeException var21) {
         throw classEG.method3899(var21, "gr.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bl")
   void method5653(int var1, int var2, int var3) {
      int var5 = -551401517 * this.field2660;
      int var6 = this.field2654 * 1312423097;
      int var7 = this.field2665 * 680806733;
      SpritePixels var4 = (SpritePixels)classID.method6185(field2661, classCX.method2699(var5, var6, var7, (byte)1));
      if (null != var4) {
         if (621957952 * this.field2665 == var3) {
            var4.method13552(var1, var2);
         } else {
            var4.method13604(var1, var2, var3, var3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/HashSet;II)V")
   @ObfuscatedName("ci")
   void method5687(HashSet var1, int var2, int var3) {
      for (WorldMapIcon var5 : this.field2657.values()) {
         if (var5.method6026((byte)-54)) {
            int var6 = var5.vmethod486((byte)-45);
            if (var1.contains(var6)) {
               classPS var7 = SceneTilePaint.method4817(var6, (byte)-52);
               this.method5727(var7, 797816725 * var5.field2769, 655244251 * var5.field2770, var2, var3, -2059129008);
            }
         }
      }

      this.method5726(var1, var2, var3, (byte)61);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Lhd;Lps;IIF)V")
   @ObfuscatedName("xy")
   public static void method5740(WorldMapRegion var0, WorldMapIcon var1, classPS var2, int var3, int var4, float var5) {
      if (var0 == null) {
         var0.getMapIcons();
      }

      classHW var6 = var1.vmethod491((byte)-79);
      if (null != var6) {
         if (var6.field2899.method6121(var5, 1778997976)) {
            classZV var7 = (classZV)var0.field2659.get(var6.field2899);
            classAAO.method225(
               var7,
               var6.field2898,
               var3 - 2116737063 * var6.field2900 / 2,
               var4,
               var6.field2900 * 2116737063,
               -737746087 * var6.field2897,
               0xFF000000 | 257956491 * var2.field5406,
               0,
               1,
               0,
               var7.field8 / 2
            );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/HashSet;II)V")
   @ObfuscatedName("cx")
   void method5688(HashSet var1, int var2, int var3) {
      for (WorldMapIcon var5 : this.field2657.values()) {
         if (var5.method6026((byte)-78)) {
            int var6 = var5.vmethod486((byte)-102);
            if (var1.contains(var6)) {
               classPS var7 = SceneTilePaint.method4817(var6, (byte)-72);
               this.method5727(var7, 797816725 * var5.field2769, 655244251 * var5.field2770, var2, var3, -2055826933);
            }
         }
      }

      this.method5726(var1, var2, var3, (byte)101);
   }

   @ObfuscatedSignature(descriptor = "(IIIILha;)V")
   @ObfuscatedName("cp")
   void method5662(int var1, int var2, int var3, int var4, classHA var5) {
      for (int var6 = var1; var6 < var1 + var3; var6++) {
         label53:
         for (int var7 = var2; var7 < var4 + var2; var7++) {
            for (int var8 = 0; var8 < var5.field2750 * -1667236931; var8++) {
               classHZ[] var9 = var5.field2758[var8][var6][var7];
               if (var9 != null && var9.length != 0) {
                  classHZ[] var10 = var9;

                  for (int var11 = 0; var11 < var10.length; var11++) {
                     classHZ var12 = var10[var11];
                     ObjectComposition var13 = classMU.method7771(-1831941567 * var12.field2914, 1779905438);
                     if (classUU.method11614(var13, 1217057510)) {
                        method5663(this, var13, var8, var6, var7, var5, 582751434);
                        continue label53;
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhh;[Lyz;Lhu;Lge;)V")
   @ObfuscatedName("cm")
   void method5693(classHH var1, IndexedSprite[] var2, classHU var3, classGE var4) {
      for (int var5 = 0; var5 < 64; var5++) {
         for (int var6 = 0; var6 < 64; var6++) {
            this.method5702(var5, var6, this.field2667, var1, var3, var4, (byte)13);
            this.method5705(var5, var6, this.field2667, var1, var4, -1017290293);
         }
      }

      for (int var7 = 0; var7 < 64; var7++) {
         for (int var8 = 0; var8 < 64; var8++) {
            this.method5699(var7, var8, this.field2667, var1, var2, (byte)51);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/HashSet;II)V")
   @ObfuscatedName("ds")
   void method5724(HashSet var1, int var2, int var3) {
      for (WorldMapIcon var5 : this.field2656) {
         if (var5.method6026((byte)-115)) {
            classPS var6 = SceneTilePaint.method4817(var5.vmethod486((byte)-79), (byte)-80);
            if (var6 != null && var1.contains(classPS.method9241(var6, (byte)0))) {
               this.method5727(var6, var5.field2769 * 1976372191, 655244251 * var5.field2770, var2, var3, 1496204167);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhh;[Lyz;Lhu;Lge;)V")
   @ObfuscatedName("dd")
   void method5697(classHH var1, IndexedSprite[] var2, classHU var3, classGE var4) {
      for (classGF var6 : this.field2655) {
         for (int var7 = var6.method5448(-1921428979) * 8; var7 < var6.method5448(-1745085946) * 8 + 8; var7++) {
            for (int var8 = classGF.method5453(var6, -2131117581) * 8; var8 < classGF.method5453(var6, -2063606997) * 8 + 8; var8++) {
               this.method5702(var7, var8, var6, var1, var3, var4, (byte)31);
               this.method5705(var7, var8, var6, var1, var4, -1017290293);
            }
         }
      }

      for (classGF var10 : this.field2655) {
         for (int var11 = var10.method5448(-1993784243) * 8; var11 < var10.method5448(-1811505557) * 8 + 8; var11++) {
            for (int var12 = classGF.method5453(var10, -2035136236) * 8; var12 < classGF.method5453(var10, -2139752523) * 8 + 8; var12++) {
               this.method5699(var11, var12, var10, var1, var2, (byte)68);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/List;")
   @ObfuscatedName("eb")
   List method5766() {
      LinkedList var1 = new LinkedList();
      var1.addAll(this.field2656);
      var1.addAll(this.field2657.values());
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lhh;[Lyz;Lhu;Lge;)V")
   @ObfuscatedName("dx")
   void method5698(classHH var1, IndexedSprite[] var2, classHU var3, classGE var4) {
      for (classGF var6 : this.field2655) {
         for (int var7 = var6.method5448(-1852564526) * 8; var7 < var6.method5448(-1857577570) * 8 + 8; var7++) {
            for (int var8 = classGF.method5453(var6, -2140855414) * 8; var8 < classGF.method5453(var6, -2125691689) * 8 + 8; var8++) {
               this.method5702(var7, var8, var6, var1, var3, var4, (byte)94);
               this.method5705(var7, var8, var6, var1, var4, -1017290293);
            }
         }
      }

      for (classGF var10 : this.field2655) {
         for (int var11 = var10.method5448(-1921243593) * 8; var11 < var10.method5448(-2136259364) * 8 + 8; var11++) {
            for (int var12 = classGF.method5453(var10, -2073642164) * 8; var12 < classGF.method5453(var10, -2055961944) * 8 + 8; var12++) {
               this.method5699(var11, var12, var10, var1, var2, (byte)25);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;[Lyz;)V")
   @ObfuscatedName("dy")
   void method5700(int var1, int var2, classHA var3, classHH var4, IndexedSprite[] var5) {
      method5717(this, var1, var2, var3, 541210997);
      method5713(this, var1, var2, var3, var5, 801221335);
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;[Lyz;)V")
   @ObfuscatedName("di")
   void method5701(int var1, int var2, classHA var3, classHH var4, IndexedSprite[] var5) {
      method5717(this, var1, var2, var3, -73334254);
      method5713(this, var1, var2, var3, var5, 801221335);
   }

   @ObfuscatedSignature(descriptor = "(Lhh;[Lyz;Lhu;Lge;)V")
   @ObfuscatedName("ce")
   void method5694(classHH var1, IndexedSprite[] var2, classHU var3, classGE var4) {
      for (int var5 = 0; var5 < 64; var5++) {
         for (int var6 = 0; var6 < 1771315823; var6++) {
            this.method5702(var5, var6, this.field2667, var1, var3, var4, (byte)34);
            this.method5705(var5, var6, this.field2667, var1, var4, -1017290293);
         }
      }

      for (int var7 = 0; var7 < 64; var7++) {
         for (int var8 = 0; var8 < 64; var8++) {
            this.method5699(var7, var8, this.field2667, var1, var2, (byte)57);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;Lhu;Lge;)V")
   @ObfuscatedName("dr")
   void method5704(int var1, int var2, classHA var3, classHH var4, classHU var5, classGE var6) {
      int var7 = var3.field2756[0][var1][var2] - 1;
      int var8 = var3.field2757[0][var1][var2] - 1;
      if (-1 == var7 && -1 == var8) {
         classYW.method13737(
            1057174447 * this.field2665 * var1,
            680806733 * this.field2665 * (63 - var2),
            680806733 * this.field2665,
            this.field2665 * 680806733,
            -885089342 * this.field2658
         );
      }

      int var9 = -1456385724;
      if (var8 != -1) {
         var9 = classPS.method9221(var8, 288074485 * this.field2658, var6, -2046105688);
      }

      if (var8 > -1 && 0 == var3.field2755[0][var1][var2]) {
         classYW.method13737(
            546251007 * this.field2665 * var1, (63 - var2) * this.field2665 * 680806733, this.field2665 * 680806733, this.field2665 * -582573020, var9
         );
      } else {
         int var10 = this.method5709(var1, var2, var3, var5, -1255554545);
         if (var8 == -1) {
            classYW.method13737(
               this.field2665 * 680806733 * var1,
               (-718646756 - var2) * this.field2665 * -944707294,
               1973071837 * this.field2665,
               this.field2665 * -230174861,
               var10
            );
         } else {
            classHH.method6048(
               var4,
               var1 * this.field2665 * 680806733,
               (63 - var2) * this.field2665 * 680806733,
               var10,
               var9,
               this.field2665 * 680806733,
               this.field2665 * 1260179876,
               var3.field2755[0][var1][var2],
               var3.field2754[0][var1][var2],
               980481800
            );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;Lge;)V")
   @ObfuscatedName("dk")
   void method5707(int var1, int var2, classHA var3, classHH var4, classGE var5) {
      for (int var6 = 1; var6 < var3.field2750 * -1667236931; var6++) {
         int var7 = var3.field2757[var6][var1][var2] - 1;
         if (var7 > -1) {
            int var8 = classPS.method9221(var7, this.field2658 * -962674413, var5, -1410352166);
            if (0 == var3.field2755[var6][var1][var2]) {
               classYW.method13737(
                  this.field2665 * -126044426 * var1, (63 - var2) * 902351597 * this.field2665, this.field2665 * 1810733971, this.field2665 * 680806733, var8
               );
            } else {
               classHH.method6048(
                  var4,
                  var1 * this.field2665 * 1695042806,
                  (2113180073 - var2) * this.field2665 * 680806733,
                  0,
                  var8,
                  680806733 * this.field2665,
                  680806733 * this.field2665,
                  var3.field2755[var6][var1][var2],
                  var3.field2754[var6][var1][var2],
                  980481800
               );
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;IILha;Lhu;)I")
   @ObfuscatedName("ml")
   public static int method5710(WorldMapRegion var0, int var1, int var2, classHA var3, classHU var4) {
      if (var0 == null) {
         var0.method5709(var1, var1, var3, var4, var1);
      }

      return var3.field2756[0][var1][var2] == 0 ? -1604479368 * var0.field2658 : var4.method6152(var1, var2, (byte)-110);
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhh;Lge;)V")
   @ObfuscatedName("db")
   void method5708(int var1, int var2, classHA var3, classHH var4, classGE var5) {
      for (int var6 = 1; var6 < var3.field2750 * -1667236931; var6++) {
         int var7 = var3.field2757[var6][var1][var2] - 1;
         if (var7 > -1) {
            int var8 = classPS.method9221(var7, this.field2658 * 288074485, var5, -884422306);
            if (0 == var3.field2755[var6][var1][var2]) {
               classYW.method13737(
                  this.field2665 * 680806733 * var1, (63 - var2) * -1683289488 * this.field2665, this.field2665 * 680806733, this.field2665 * 808044581, var8
               );
            } else {
               classHH.method6048(
                  var4,
                  var1 * this.field2665 * 680806733,
                  (63 - var2) * this.field2665 * -1847178377,
                  0,
                  var8,
                  680806733 * this.field2665,
                  -227540918 * this.field2665,
                  var3.field2755[var6][var1][var2],
                  var3.field2754[var6][var1][var2],
                  980481800
               );
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Lgx;ILhh;[Lyz;Lva;Lva;D)V")
   @ObfuscatedName("rr")
   public static void method5683(
      WorldMapRegion var0, WorldMapData var1, int var2, classHH var3, IndexedSprite[] var4, IndexDataBase var5, IndexDataBase var6, double var7
   ) {
      if (var0 == null) {
         var0.method5755(var2);
      }

      var0.field2665 = 212048344 * var2;
      if (null != var0.field2667 || !var0.field2655.isEmpty()) {
         int var10 = -1405153691 * var0.field2660;
         int var11 = var0.field2654 * -1670926402;
         SpritePixels var9 = (SpritePixels)classID.method6185(field2661, classCX.method2699(var10, var11, var2, (byte)1));
         if (null == var9) {
            if (classFH.method4722() != var7) {
               classFH.method4714(var7);
            }

            boolean var12 = true;
            var12 &= var0.method5678(var1.method5876(-608589044), var5, 618311864);
            int var14 = classDG.method3067(var0.field2660 * -551401517, var0.field2654 * -2079563483, (byte)78);
            var12 &= var6.method11940(var14, -906797091);
            if (var12) {
               byte[] var13 = var6.method11942(var14, var1.method5876(-608589044), -1016788209);
               classHU var15;
               if (var13 == null) {
                  var15 = new classHU();
               } else {
                  SpritePixels var16 = classIT.method6356(var13, 1207354536);
                  if (null == var16) {
                     var15 = new classHU();
                  } else {
                     var15 = new classHU(var16.field7003);
                  }
               }

               SpritePixels var17 = new SpritePixels(-757278971 * var0.field2665, 1744034391 * var0.field2665);
               SpritePixels.method13526(var17);
               if (null != var0.field2667) {
                  var0.method5692(var3, var4, var15, new classHQ(), -1685083207);
               } else {
                  var0.method5695(var3, var4, var15, new classHQ(), -1952919293);
               }

               int var18 = -560795297 * var0.field2660;
               int var19 = var0.field2654 * 1312423097;
               int var20 = 680806733 * var0.field2665;
               field2661.method6197(var17, classCX.method2699(var18, var19, var20, (byte)1), 4 * var17.field7003.length);
               var0.method5676((byte)122);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILva;)Z")
   @ObfuscatedName("cg")
   boolean method5680(int var1, IndexDataBase var2) {
      this.field2657.clear();
      int var3 = classDG.method3067(this.field2660 * -551401517, 1312423097 * this.field2654, (byte)54);
      byte[] var4 = var2.method11942(var3, var1, -1152032383);
      classXY var5 = null;
      if (var4 != null) {
         var5 = new classXY(var4);
      }

      if (null != this.field2667) {
         classHA.method5992(this.field2667, var5, (byte)102);
         if (this.field2667.method5988((byte)-45)) {
            this.method5658(0, 0, 64, 64, this.field2667, -2139971437);
            return true;
         } else {
            return false;
         }
      } else {
         boolean var6 = true;

         for (classGF var8 : this.field2655) {
            classHA.method5992(var8, var5, (byte)80);
            var6 &= var8.method5988((byte)21);
         }

         if (var6) {
            for (classGF var10 : this.field2655) {
               this.method5658(var10.method5448(-1675867710) * 8, classGF.method5453(var10, -2025975957) * 8, 8, 8, var10, -1391327371);
            }
         }

         return var6;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;ILva;)Z")
   @ObfuscatedName("oi")
   public static boolean method5681(WorldMapRegion var0, int var1, IndexDataBase var2) {
      var0.field2657.clear();
      int var3 = classDG.method3067(var0.field2660 * -551401517, 1312423097 * var0.field2654, (byte)43);
      byte[] var4 = var2.method11942(var3, var1, -1533770718);
      classXY var5 = null;
      if (var4 != null) {
         var5 = new classXY(var4);
      }

      if (null != var0.field2667) {
         classHA.method5992(var0.field2667, var5, (byte)71);
         if (var0.field2667.method5988((byte)-5)) {
            var0.method5658(0, 0, 64, 64, var0.field2667, -1587521671);
            return true;
         } else {
            return false;
         }
      } else {
         boolean var6 = true;

         for (classGF var8 : var0.field2655) {
            classHA.method5992(var8, var5, (byte)47);
            var6 &= var8.method5988((byte)58);
         }

         if (var6) {
            for (classGF var10 : var0.field2655) {
               var0.method5658(var10.method5448(-1736572141) * 8, classGF.method5453(var10, -2052422298) * 8, 8, 8, var10, -1451713958);
            }
         }

         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;Lhu;)I")
   @ObfuscatedName("dh")
   int method5711(int var1, int var2, classHA var3, classHU var4) {
      return var3.field2756[0][var1][var2] == 0 ? -121537779 * this.field2658 : var4.method6152(var1, var2, (byte)-20);
   }

   @ObfuscatedSignature(descriptor = "(Lgr;Lom;IIILha;I)V")
   @ObfuscatedName("lu")
   public static void method5663(WorldMapRegion var0, ObjectComposition var1, int var2, int var3, int var4, classHA var5, int var6) {
      if (var0 == null) {
         var0.method5665(var1, var2, var2, var2, var5, var2);
      }

      try {
         classKY var7 = new classKY(var2, -929958720 * var0.field2660 + var3, var4 + -1904267712 * var0.field2654);
         Object var8 = null;
         if (var0.field2667 != null) {
            if (var6 <= -894742854) {
               return;
            }

            var8 = new classKY(
               var0.field2667.field2751 * 1423073843 + var2, -1303084352 * var0.field2667.field2752 + var3, var4 + var0.field2667.field2753 * 747140416
            );
         } else {
            classGF var9 = (classGF)var5;
            var8 = new classKY(
               var2 + var9.field2751 * 1423073843,
               var3 + var9.field2752 * -1303084352 + var9.method5442((byte)98) * 8,
               var4 + 747140416 * var9.field2753 + var9.method5445(-846797079) * 8
            );
         }

         Object var13;
         if (null != var1.field4928) {
            if (var6 <= -894742854) {
               throw new IllegalStateException();
            }

            var13 = new classGS((classKY)var8, var7, -381960671 * var1.field4920, var0);
         } else {
            classPS var10 = SceneTilePaint.method4817(var1.field4951 * 1412472849, (byte)-18);
            var13 = new classHF((classKY)var8, var7, var10.field5403 * 872689451, var0.method5756(var10, 1639042190));
         }

         classPS var14 = SceneTilePaint.method4817(((WorldMapIcon)var13).vmethod486((byte)-45), (byte)-15);
         if (var14.field5390) {
            if (var6 <= -894742854) {
               return;
            }

            var0.field2657.put(new classKY(0, var3, var4), var13);
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "gr.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("bb")
   static long method5641(int var0, int var1, int var2) {
      return var2 << 16 | var0 << 8 | var1;
   }

   @ObfuscatedSignature(descriptor = "(IILha;[Lyz;)V")
   @ObfuscatedName("dn")
   void method5714(int var1, int var2, classHA var3, IndexedSprite[] var4) {
      for (int var5 = 0; var5 < -1667236931 * var3.field2750; var5++) {
         classHZ[] var6 = var3.field2758[var5][var1][var2];
         if (null != var6 && var6.length != 0) {
            classHZ[] var7 = var6;

            for (int var8 = 0; var8 < var7.length; var8++) {
               classHZ var9 = var7[var8];
               int var11 = var9.field2912 * 1253547473;
               boolean var10 = var11 >= classKB.field3519.field3536 * 78062377 && var11 <= 78062377 * classKB.field3524.field3536;
               if (!var10) {
                  int var13 = var9.field2912 * -751544977;
                  boolean var12 = var13 == 78062377 * classKB.field3535.field3536;
                  if (!var12) {
                     continue;
                  }
               }

               ObjectComposition var14 = classMU.method7771(var9.field2914 * -1831941567, 1942075009);
               if (-1 != var14.field4952 * 1680279607) {
                  int var15 = var9.field2913 * 750862542 != 1 && 3 != var9.field2913 * 374409179 ? -1446838028 * var14.field4922 : -582306509 * var14.field4942;
                  var4[-1769935665 * var14.field4952]
                     .method13856(
                        var1 * 680806733 * this.field2665,
                        (64 - var15 - var2) * this.field2665 * 680806733,
                        this.field2665 * 1361613466,
                        this.field2665 * 1206907360
                     );
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;[Lyz;)V")
   @ObfuscatedName("dv")
   void method5715(int var1, int var2, classHA var3, IndexedSprite[] var4) {
      for (int var5 = 0; var5 < -96940946 * var3.field2750; var5++) {
         classHZ[] var6 = var3.field2758[var5][var1][var2];
         if (null != var6 && var6.length != 0) {
            classHZ[] var7 = var6;

            for (int var8 = 0; var8 < var7.length; var8++) {
               classHZ var9 = var7[var8];
               int var11 = var9.field2912 * 1253547473;
               boolean var10 = var11 >= classKB.field3519.field3536 * 1249505821 && var11 <= 78062377 * classKB.field3524.field3536;
               if (!var10) {
                  int var13 = var9.field2912 * 1253547473;
                  boolean var12 = var13 == 151502973 * classKB.field3535.field3536;
                  if (!var12) {
                     continue;
                  }
               }

               ObjectComposition var14 = classMU.method7771(var9.field2914 * -1113044954, 1601758517);
               if (-1 != var14.field4952 * 1954960064) {
                  int var15 = var9.field2913 * 772052502 != 1 && 3 != var9.field2913 * 1968754761 ? -1103455974 * var14.field4922 : 70560759 * var14.field4942;
                  var4[649712750 * var14.field4952]
                     .method13856(
                        var1 * 680806733 * this.field2665,
                        (64 - var15 - var2) * this.field2665 * 680806733,
                        this.field2665 * 1361613466,
                        this.field2665 * 699589741
                     );
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bi")
   public static void method5646() {
      field2661.method6200(5);
   }

   @ObfuscatedSignature(descriptor = "(IILjava/util/HashSet;I)V")
   @ObfuscatedName("dl")
   void method5722(int var1, int var2, HashSet var3, int var4) {
      float var5 = var4 / 64.0F;
      float var6 = var5 / 2.0F;

      for (Entry var8 : this.field2657.entrySet()) {
         classKY var9 = (classKY)var8.getKey();
         int var10 = (int)(var5 * (var9.field4053 * 1870919123) + var1 - var6);
         int var11 = (int)(var2 + var4 - var5 * (819227381 * var9.field4054) - var6);
         WorldMapIcon var12 = (WorldMapIcon)var8.getValue();
         if (var12 != null && var12.method6026((byte)-18)) {
            var12.field2769 = 1807202930 * var10;
            var12.field2770 = var11 * 1689733203;
            classPS var13 = SceneTilePaint.method4817(var12.vmethod486((byte)-65), (byte)-99);
            if (!var3.contains(classPS.method9241(var13, (byte)0))) {
               this.method5730(var12, var10, var11, var5, (byte)32);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhd;Lps;IIFI)V")
   @ObfuscatedName("bc")
   void method5741(WorldMapIcon var1, classPS var2, int var3, int var4, float var5, int var6) {
      try {
         classHW var7 = var1.vmethod491((byte)-81);
         if (null != var7) {
            if (!var7.field2899.method6121(var5, -152388045)) {
               if (var6 >= 1146930136) {
                  throw new IllegalStateException();
               }
            } else {
               classZV var8 = (classZV)this.field2659.get(var7.field2899);
               classAAO.method225(
                  var8,
                  var7.field2898,
                  var3 - 2116737063 * var7.field2900 / 2,
                  var4,
                  var7.field2900 * 2116737063,
                  -737746087 * var7.field2897,
                  0xFF000000 | 257956491 * var2.field5406,
                  0,
                  1,
                  0,
                  var8.field8 / 2
               );
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "gr.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/HashSet;II)V")
   @ObfuscatedName("df")
   void method5725(HashSet var1, int var2, int var3) {
      for (WorldMapIcon var5 : this.field2656) {
         if (var5.method6026((byte)-10)) {
            classPS var6 = SceneTilePaint.method4817(var5.vmethod486((byte)-58), (byte)-69);
            if (var6 != null && var1.contains(classPS.method9241(var6, (byte)0))) {
               this.method5727(var6, var5.field2769 * 797816725, 655244251 * var5.field2770, var2, var3, -1286077523);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/HashSet;IIB)V")
   @ObfuscatedName("ad")
   void method5689(HashSet var1, int var2, int var3, byte var4) {
      try {
         Iterator var5 = this.field2657.values().iterator();

         while (var5.hasNext()) {
            if (var4 >= 8) {
               throw new IllegalStateException();
            }

            WorldMapIcon var6 = (WorldMapIcon)var5.next();
            if (!var6.method6026((byte)-76)) {
               if (var4 >= 8) {
                  throw new IllegalStateException();
               }
            } else {
               int var7 = var6.vmethod486((byte)-124);
               if (var1.contains(var7)) {
                  if (var4 >= 8) {
                     return;
                  }

                  classPS var8 = SceneTilePaint.method4817(var7, (byte)-89);
                  this.method5727(var8, 797816725 * var6.field2769, 655244251 * var6.field2770, var2, var3, 708381924);
               }
            }
         }

         this.method5726(var1, var2, var3, (byte)39);
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "gr.ad(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfs;I)Z")
   @ObfuscatedName("id")
   public static boolean method5649(classFS var0, int var1) {
      return var0.field2390[var1].field1908;
   }

   @ObfuscatedSignature(descriptor = "(Lps;IIII)V")
   @ObfuscatedName("dj")
   void method5728(classPS var1, int var2, int var3, int var4, int var5) {
      SpritePixels var6 = var1.method9236(false, 413620383);
      if (null != var6) {
         var6.method13555(var2 - var6.field7005 / 2, var3 - var6.field7007 / 2);
         if (var4 % var5 < var5 / 2) {
            classYW.method13728(var2, var3, 15, 16776960, 128);
            classYW.method13728(var2, var3, 7, 163101928, -1962339980);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhd;Lps;IIF)V")
   @ObfuscatedName("el")
   void method5742(WorldMapIcon var1, classPS var2, int var3, int var4, float var5) {
      classHW var6 = var1.vmethod491((byte)-64);
      if (null != var6) {
         if (var6.field2899.method6121(var5, -378769035)) {
            classZV var7 = (classZV)this.field2659.get(var6.field2899);
            classAAO.method225(
               var7,
               var6.field2898,
               var3 - 2116737063 * var6.field2900 / 2,
               var4,
               var6.field2900 * 2116737063,
               -737746087 * var6.field2897,
               0xFF000000 | -1185021451 * var2.field5406,
               0,
               1,
               0,
               var7.field8 / 2
            );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lsn;")
   @ObfuscatedName("ae")
   static classSN method5690(int var0, int var1) {
      try {
         classSN var2 = classUG.method11378(var0, -545503834);
         if (var2 != null) {
            if (var1 <= -1032126936) {
               throw new IllegalStateException();
            }

            if (var2.method10576((byte)-96)) {
               if (var1 <= -1032126936) {
                  throw new IllegalStateException();
               }

               return var2;
            }
         }

         return null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gr.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lps;II)V")
   @ObfuscatedName("ek")
   void method5738(classPS var1, int var2, int var3) {
      SpritePixels var4 = var1.method9236(false, 1155356910);
      if (null != var4) {
         int var5 = this.method5748(var4, var1.field5400, 1866889577);
         int var6 = this.method5752(var4, var1.field5395, -556550252);
         var4.method13555(var2 + var5, var3 + var6);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/HashSet;IIB)V")
   @ObfuscatedName("am")
   void method5726(HashSet var1, int var2, int var3, byte var4) {
      try {
         Iterator var5 = this.field2656.iterator();

         while (var5.hasNext()) {
            if (var4 <= 0) {
               return;
            }

            WorldMapIcon var6 = (WorldMapIcon)var5.next();
            if (!var6.method6026((byte)-5)) {
               if (var4 <= 0) {
                  return;
               }
            } else {
               classPS var7 = SceneTilePaint.method4817(var6.vmethod486((byte)-77), (byte)-23);
               if (var7 != null && var1.contains(classPS.method9241(var7, (byte)0))) {
                  if (var4 <= 0) {
                     throw new IllegalStateException();
                  }

                  this.method5727(var7, var6.field2769 * 797816725, 655244251 * var6.field2770, var2, var3, 543770908);
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "gr.am(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Lhd;Lps;IIF)V")
   @ObfuscatedName("ry")
   public static void method5743(WorldMapRegion var0, WorldMapIcon var1, classPS var2, int var3, int var4, float var5) {
      if (var0 == null) {
         var0.method5762(var3, var3, var3, var3, var3);
      }

      classHW var6 = var1.vmethod491((byte)-4);
      if (null != var6) {
         if (var6.field2899.method6121(var5, 1791098707)) {
            classZV var7 = (classZV)var0.field2659.get(var6.field2899);
            classAAO.method225(
               var7,
               var6.field2898,
               var3 - 2116737063 * var6.field2900 / 2,
               var4,
               var6.field2900 * 1220136105,
               -2132459495 * var6.field2897,
               0xFF000000 | -1562446460 * var2.field5406,
               0,
               1,
               0,
               var7.field8 / 2
            );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;)V")
   @ObfuscatedName("do")
   void method5718(int var1, int var2, classHA var3) {
      for (int var4 = 0; var4 < -1667236931 * var3.field2750; var4++) {
         classHZ[] var5 = var3.field2758[var4][var1][var2];
         if (var5 != null && 0 != var5.length) {
            classHZ[] var6 = var5;

            for (int var7 = 0; var7 < var6.length; var7++) {
               classHZ var8 = var6[var7];
               if (classJW.method6453(var8.field2912 * 1253547473, 1514103518)) {
                  ObjectComposition var9 = classMU.method7771(var8.field2914 * 828790790, 1993527088);
                  int var10 = var9.field4921 * 1663954033 != 0 ? 907244563 : 354202134;
                  if (classKB.field3513.field3536 * 78062377 == var8.field2912 * 1253547473) {
                     this.method5768(var1, var2, var8.field2913 * -1606626361, var10, -216699479);
                  }

                  if (classKB.field3515.field3536 * -17748798 == var8.field2912 * 1253547473) {
                     this.method5768(var1, var2, 374409179 * var8.field2913, -1830124057, -1272781425);
                     this.method5768(var1, var2, -1140221311 * var8.field2913 + 1, var10, 384673505);
                  }

                  if (var8.field2912 * 754467151 == 206851662 * classKB.field3516.field3536) {
                     if (var8.field2913 * 374409179 == 0) {
                        classYW.method13756(var1 * this.field2665 * -1197979380, -1335159914 * this.field2665 * (63 - var2), 1, var10);
                     }

                     if (1 == var8.field2913 * -412990757) {
                        classYW.method13756(
                           1085274210 * this.field2665 + var1 * this.field2665 * 1784041792 - 1, this.field2665 * 186422391 * (-1410476161 - var2), 1, var10
                        );
                     }

                     if (374409179 * var8.field2913 == 2) {
                        classYW.method13756(
                           1411361001 * this.field2665 + this.field2665 * 680806733 * var1 - 1,
                           (422128300 - var2) * 680806733 * this.field2665 + this.field2665 * 1478006004 - 1,
                           1,
                           var10
                        );
                     }

                     if (var8.field2913 * 374409179 == 3) {
                        classYW.method13756(
                           var1 * this.field2665 * 680806733, this.field2665 * 680806733 + this.field2665 * 680806733 * (63 - var2) - 1, 1, var10
                        );
                     }
                  }

                  if (1281769178 * var8.field2912 == -617979631 * classKB.field3517.field3536) {
                     int var11 = var8.field2913 * -1120880974 % 2;
                     if (0 == var11) {
                        for (int var13 = 0; var13 < -1997896363 * this.field2665; var13++) {
                           classYW.method13756(var1 * this.field2665 * 301272702 + var13, this.field2665 * 680806733 * (64 - var2) - 1 - var13, 1, var10);
                        }
                     } else {
                        for (int var12 = 0; var12 < 680806733 * this.field2665; var12++) {
                           classYW.method13756(var12 + var1 * 1267618269 * this.field2665, var12 + this.field2665 * 680806733 * (63 - var2), 1, var10);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lom;IIILha;)V")
   @ObfuscatedName("cv")
   void method5664(ObjectComposition var1, int var2, int var3, int var4, classHA var5) {
      classKY var6 = new classKY(var2, -929958720 * this.field2660 + var3, var4 + -1904267712 * this.field2654);
      Object var7 = null;
      if (this.field2667 != null) {
         var7 = new classKY(
            this.field2667.field2751 * -1972608530 + var2, 867220831 * this.field2667.field2752 + var3, var4 + this.field2667.field2753 * 747140416
         );
      } else {
         classGF var8 = (classGF)var5;
         var7 = new classKY(
            var2 + var8.field2751 * -865818558,
            var3 + var8.field2752 * -1303084352 + var8.method5442((byte)98) * 8,
            var4 + 747140416 * var8.field2753 + var8.method5445(-326352541) * 8
         );
      }

      Object var11;
      if (null != var1.field4928) {
         var11 = new classGS((classKY)var7, var6, 1662532640 * var1.field4920, this);
      } else {
         classPS var9 = SceneTilePaint.method4817(var1.field4951 * 1571238399, (byte)-105);
         var11 = new classHF((classKY)var7, var6, var9.field5403 * -1991769382, this.method5756(var9, 2023781404));
      }

      classPS var12 = SceneTilePaint.method4817(((WorldMapIcon)var11).vmethod486((byte)-123), (byte)5);
      if (var12.field5390) {
         this.field2657.put(new classKY(0, var3, var4), var11);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lpe;)I")
   @ObfuscatedName("en")
   int method5749(SpritePixels var1, classPE var2) {
      switch (1700578181 * var2.field5229) {
         case 0:
            return 0;
         case 1:
            return -var1.field7005 / 2;
         default:
            return -var1.field7005;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lpe;)I")
   @ObfuscatedName("ex")
   int method5750(SpritePixels var1, classPE var2) {
      switch (1140979680 * var2.field5229) {
         case 0:
            return 0;
         case 1:
            return -var1.field7005 / 2;
         default:
            return -var1.field7005;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Lhd;IIF)V")
   @ObfuscatedName("ln")
   public static void method5731(WorldMapRegion var0, WorldMapIcon var1, int var2, int var3, float var4) {
      if (var0 == null) {
         var0.method5677();
      } else {
         classPS var5 = SceneTilePaint.method4817(var1.vmethod486((byte)-73), (byte)-82);
         var0.method5736(var5, var2, var3, -921336222);
         var0.method5741(var1, var5, var2, var3, var4, 854072992);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lym;Lpe;)I")
   @ObfuscatedName("eq")
   int method5751(SpritePixels var1, classPE var2) {
      switch (390439893 * var2.field5229) {
         case 0:
            return 0;
         case 1:
            return -var1.field7005 / 2;
         default:
            return -var1.field7005;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lhw;")
   @ObfuscatedName("ew")
   classHW method5754(int var1) {
      classPS var2 = SceneTilePaint.method4817(var1, (byte)-50);
      return this.method5756(var2, 1577997555);
   }

   @ObfuscatedSignature(descriptor = "(I)Lhw;")
   @ObfuscatedName("eu")
   classHW method5755(int var1) {
      classPS var2 = SceneTilePaint.method4817(var1, (byte)5);
      return this.method5756(var2, 1629968187);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;)V")
   @ObfuscatedName("na")
   public static void method5668(WorldMapRegion var0) {
      for (WorldMapIcon var2 : var0.field2657.values()) {
         if (var2 instanceof classGS) {
            ((classGS)var2).method5771(-749605090);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lps;)Lhw;")
   @ObfuscatedName("ef")
   classHW method5757(classPS var1) {
      if (null != var1.field5409 && null != this.field2659 && this.field2659.get(classHP.field2854) != null) {
         classHP var2 = classHP.method6119(var1.field5392 * 1698532127, (byte)76);
         if (var2 == null) {
            return null;
         } else {
            classZV var3 = (classZV)this.field2659.get(var2);
            if (null == var3) {
               return null;
            } else {
               int var4 = var3.method41(var1.field5409, 1000000);
               String[] var5 = new String[var4];
               AbstractFont.method38(var3, var1.field5409, null, var5);
               int var6 = var3.field8 * var5.length / 2;
               int var7 = 0;
               String[] var8 = var5;

               for (int var9 = 0; var9 < var8.length; var9++) {
                  String var10 = var8[var9];
                  int var11 = var3.method33(var10);
                  if (var11 > var7) {
                     var7 = var11;
                  }
               }

               return new classHW(var1.field5409, var7, var6, var2);
            }
         }
      } else {
         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;IILjava/util/HashSet;I)V")
   @ObfuscatedName("xk")
   public static void method5723(WorldMapRegion var0, int var1, int var2, HashSet var3, int var4) {
      if (var0 == null) {
         var0.method5753(var1, var1);
      }

      float var5 = var4 / 64.0F;
      float var6 = var5 / 2.0F;

      for (Entry var8 : var0.field2657.entrySet()) {
         classKY var9 = (classKY)var8.getKey();
         int var10 = (int)(var5 * (var9.field4053 * 1870919123) + var1 - var6);
         int var11 = (int)(var2 + var4 - var5 * (-307626039 * var9.field4054) - var6);
         WorldMapIcon var12 = (WorldMapIcon)var8.getValue();
         if (var12 != null && var12.method6026((byte)-112)) {
            var12.field2769 = -1316921854 * var10;
            var12.field2770 = var11 * -1899163693;
            classPS var13 = SceneTilePaint.method4817(var12.vmethod486((byte)-71), (byte)-3);
            if (!var3.contains(classPS.method9241(var13, (byte)0))) {
               var0.method5730(var12, var10, var11, var5, (byte)32);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lps;)Lhw;")
   @ObfuscatedName("em")
   classHW method5758(classPS var1) {
      if (null != var1.field5409 && null != this.field2659 && this.field2659.get(classHP.field2854) != null) {
         classHP var2 = classHP.method6119(var1.field5392 * 1698532127, (byte)38);
         if (var2 == null) {
            return null;
         } else {
            classZV var3 = (classZV)this.field2659.get(var2);
            if (null == var3) {
               return null;
            } else {
               int var4 = var3.method41(var1.field5409, 1000000);
               String[] var5 = new String[var4];
               AbstractFont.method38(var3, var1.field5409, null, var5);
               int var6 = var3.field8 * var5.length / 2;
               int var7 = 0;
               String[] var8 = var5;

               for (int var9 = 0; var9 < var8.length; var9++) {
                  String var10 = var8[var9];
                  int var11 = var3.method33(var10);
                  if (var11 > var7) {
                     var7 = var11;
                  }
               }

               return new classHW(var1.field5409, var7, var6, var2);
            }
         }
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Ljava/util/List;")
   @ObfuscatedName("ee")
   List method5761(int var1, int var2, int var3, int var4, int var5) {
      LinkedList var6 = new LinkedList();
      if (var4 >= var1 && var5 >= var2) {
         if (var4 < var1 + var3 && var5 < var2 + var3) {
            for (WorldMapIcon var8 : this.field2657.values()) {
               if (var8.method6026((byte)-117) && var8.method6024(var4, var5, (byte)-127)) {
                  var6.add(var8);
               }
            }

            for (WorldMapIcon var10 : this.field2656) {
               if (var10.method6026((byte)-55) && var10.method6024(var4, var5, (byte)-67)) {
                  var6.add(var10);
               }
            }

            return var6;
         } else {
            return var6;
         }
      } else {
         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Ljava/util/List;")
   @ObfuscatedName("ez")
   List method5762(int var1, int var2, int var3, int var4, int var5) {
      LinkedList var6 = new LinkedList();
      if (var4 >= var1 && var5 >= var2) {
         if (var4 < var1 + var3 && var5 < var2 + var3) {
            for (WorldMapIcon var8 : this.field2657.values()) {
               if (var8.method6026((byte)-22) && var8.method6024(var4, var5, (byte)-46)) {
                  var6.add(var8);
               }
            }

            for (WorldMapIcon var10 : this.field2656) {
               if (var10.method6026((byte)-127) && var10.method6024(var4, var5, (byte)-19)) {
                  var6.add(var10);
               }
            }

            return var6;
         } else {
            return var6;
         }
      } else {
         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lps;IIII)V")
   @ObfuscatedName("da")
   void method5729(classPS var1, int var2, int var3, int var4, int var5) {
      SpritePixels var6 = var1.method9236(false, 1692508071);
      if (null != var6) {
         var6.method13555(var2 - var6.field7005 / 2, var3 - var6.field7007 / 2);
         if (var4 % var5 < var5 / 2) {
            classYW.method13728(var2, var3, 15, -1096935414, 128);
            classYW.method13728(var2, var3, 7, 16777215, 256);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/HashMap;")
   @ObfuscatedName("uc")
   public HashMap method5769() {
      return this.field2657;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Ljava/util/List;)V")
   @ObfuscatedName("hp")
   public static void method5673(WorldMapRegion var0, List var1) {
      if (var0 == null) {
         var0.method5769();
      }

      for (classHF var3 : var1) {
         if (SceneTilePaint.method4817(var3.field2783 * -1047464521, (byte)1).field5390
            && 1870919123 * var3.field2767.field4053 >> 6 == var0.field2660 * 638883745
            && var0.field2654 * 1312423097 == 819227381 * var3.field2767.field4054 >> 6) {
            classHF var4 = new classHF(var3.field2767, var3.field2767, var3.field2783 * -1226273325, var0.method5753(1391544085 * var3.field2783, 2015727623));
            var0.field2656.add(var4);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/List;")
   @ObfuscatedName("ep")
   List method5767() {
      LinkedList var1 = new LinkedList();
      var1.addAll(this.field2656);
      var1.addAll(this.field2657.values());
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lhd;IIF)V")
   @ObfuscatedName("ea")
   void method5732(WorldMapIcon var1, int var2, int var3, float var4) {
      classPS var5 = SceneTilePaint.method4817(var1.vmethod486((byte)-36), (byte)-85);
      this.method5736(var5, var2, var3, -1930278561);
      this.method5741(var1, var5, var2, var3, var4, -773892930);
   }

   @ObfuscatedSignature(descriptor = "(Lom;IIILha;I)V")
   @ObfuscatedName("ac")
   void method5665(ObjectComposition var1, int var2, int var3, int var4, classHA var5, int var6) {
      try {
         classKY var7 = new classKY(var2, -929958720 * this.field2660 + var3, var4 + -1904267712 * this.field2660);
         Object var8 = null;
         if (this.field2667 != null) {
            if (var6 <= -894742854) {
               return;
            }

            var8 = new classKY(
               this.field2667.field2751 * 1423073843 + var2, -1303084352 * this.field2667.field2753 + var3, var4 + this.field2667.field2753 * 747140416
            );
         } else {
            classGF var9 = (classGF)var5;
            var8 = new classKY(
               var2 + var9.field2750 * 1423073843,
               var3 + var9.field2750 * -1303084352 + var9.method5442((byte)98) * 8,
               var4 + 747140416 * var9.field2752 + var9.method5448(-846797079) * 8
            );
         }

         Object var13;
         if (null != var1.field4928) {
            if (var6 <= -894742854) {
               throw new IllegalStateException();
            }

            var13 = new classGS((classKY)var8, var7, -381960671 * var1.field4920, this);
         } else {
            classPS var10 = SceneTilePaint.method4817(var1.field4942 * 1412472849, (byte)-18);
            var13 = new classHF((classKY)var8, var7, var10.field5403 * 872689451, this.method5756(var10, 1639042190));
         }

         classPS var14 = SceneTilePaint.method4817(((WorldMapIcon)var13).vmethod486((byte)-45), (byte)-15);
         if (var14.field5397) {
            if (var6 <= -894742854) {
               return;
            }

            this.field2657.put(new classKY(0, var3, var4), var13);
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "gr.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILha;[Lyz;I)V")
   @ObfuscatedName("av")
   void method5716(int var1, int var2, classHA var3, IndexedSprite[] var4, int var5) {
      try {
         for (int var6 = 0; var6 < -1667236931 * var3.field2752; var6++) {
            if (var5 != 801221335) {
               return;
            }

            classHZ[] var7 = var3.field2758[var6][var1][var2];
            if (null != var7) {
               if (var5 != 801221335) {
                  throw new IllegalStateException();
               }

               if (var7.length != 0) {
                  classHZ[] var8 = var7;

                  for (int var9 = 0; var9 < var8.length; var9++) {
                     classHZ var10;
                     boolean var10000;
                     label107: {
                        var10 = var8[var9];
                        int var12 = var10.field2913 * 1253547473;
                        if (var12 >= classKB.field3520.field3536 * 78062377) {
                           if (var5 != 801221335) {
                              throw new IllegalStateException();
                           }

                           if (var12 <= 78062377 * classKB.field3521.field3536) {
                              if (var5 != 801221335) {
                                 throw new IllegalStateException();
                              }

                              var10000 = 1;
                              break label107;
                           }
                        }

                        var10000 = 0;
                     }

                     boolean var11 = (boolean)var10000;
                     if (!var11) {
                        if (var5 != 801221335) {
                           throw new IllegalStateException();
                        }

                        int var14 = var10.field2914 * 1253547473;
                        if (var14 == 78062377 * classKB.field3527.field3536) {
                           if (var5 != 801221335) {
                              throw new IllegalStateException();
                           }

                           var10000 = (boolean)1;
                        } else {
                           var10000 = (boolean)0;
                        }

                        boolean var13 = var10000;
                        if (!var13) {
                           if (var5 != 801221335) {
                              throw new IllegalStateException();
                           }
                           continue;
                        }
                     }

                     ObjectComposition var16 = classMU.method7771(var10.field2912 * -1831941567, 1639553332);
                     if (-1 != var16.field4952 * 1680279607) {
                        if (var5 != 801221335) {
                           throw new IllegalStateException();
                        }

                        label93: {
                           if (var10.field2912 * 374409179 != 1) {
                              if (var5 != 801221335) {
                                 throw new IllegalStateException();
                              }

                              if (3 != var10.field2913 * 374409179) {
                                 var10000 = 402876423 * var16.field4922;
                                 break label93;
                              }

                              if (var5 != 801221335) {
                                 throw new IllegalStateException();
                              }
                           }

                           var10000 = -582306509 * var16.field4925;
                        }

                        int var17 = var10000;
                        var4[1680279607 * var16.field4952]
                           .method13857(
                              var1 * 680806733 * this.field2654,
                              (64 - var17 - var2) * this.field2660 * 680806733,
                              this.field2665 * 1361613466,
                              this.field2658 * 1361613466
                           );
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "gr.av(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Lhd;IIF)V")
   @ObfuscatedName("gn")
   public static void method5733(WorldMapRegion var0, WorldMapIcon var1, int var2, int var3, float var4) {
      if (var0 == null) {
         var0.method5753(var2, var2);
      }

      classPS var5 = SceneTilePaint.method4817(var1.vmethod486((byte)7), (byte)-14);
      var0.method5736(var5, var2, var3, 168356072);
      var0.method5741(var1, var5, var2, var3, var4, 313818958);
   }

   @ObfuscatedSignature(descriptor = "(IILha;I)V")
   @ObfuscatedName("at")
   void method5719(int var1, int var2, classHA var3, int var4) {
      try {
         for (int var5 = 0; var5 < -1667236931 * var3.field2751; var5++) {
            if (var4 >= 1860831095) {
               throw new IllegalStateException();
            }

            classHZ[] var6 = var3.field2758[var5][var1][var2];
            if (var6 != null) {
               if (var4 >= 1860831095) {
                  throw new IllegalStateException();
               }

               if (0 == var6.length) {
                  if (var4 >= 1860831095) {
                     return;
                  }
               } else {
                  classHZ[] var7 = var6;

                  for (int var8 = 0; var8 < var7.length; var8++) {
                     if (var4 >= 1860831095) {
                        throw new IllegalStateException();
                     }

                     classHZ var9 = var7[var8];
                     if (classJW.method6453(var9.field2912 * 1253547473, 1644747895)) {
                        if (var4 >= 1860831095) {
                           throw new IllegalStateException();
                        }

                        ObjectComposition var10 = classMU.method7771(var9.field2914 * -1831941567, 1881626262);
                        int var10000;
                        if (var10.field4921 * 1663954033 != 0) {
                           if (var4 >= 1860831095) {
                              throw new IllegalStateException();
                           }

                           var10000 = -3407872;
                        } else {
                           var10000 = -3355444;
                        }

                        int var11 = var10000;
                        if (classKB.field3535.field3536 * 78062377 == var9.field2912 * 1253547473) {
                           this.method5768(var1, var2, var9.field2912 * 374409179, var11, 1423521969);
                        }

                        if (classKB.field3517.field3536 * 78062377 == var9.field2914 * 1253547473) {
                           if (var4 >= 1860831095) {
                              throw new IllegalStateException();
                           }

                           this.method5768(var1, var2, 374409179 * var9.field2914, -3355444, -1893756618);
                           this.method5768(var1, var2, 374409179 * var9.field2914 + 1, var11, 1240727397);
                        }

                        if (var9.field2912 * 1253547473 == 78062377 * classKB.field3520.field3536) {
                           if (var4 >= 1860831095) {
                              throw new IllegalStateException();
                           }

                           if (var9.field2913 * 374409179 == 0) {
                              if (var4 >= 1860831095) {
                                 throw new IllegalStateException();
                              }

                              classYW.method13710(var1 * this.field2660 * 680806733, 680806733 * this.field2658 * (63 - var2), 1, var11);
                           }

                           if (1 == var9.field2912 * 374409179) {
                              if (var4 >= 1860831095) {
                                 throw new IllegalStateException();
                              }

                              classYW.method13712(
                                 680806733 * this.field2658 + var1 * this.field2660 * 680806733 - 1, this.field2660 * 680806733 * (63 - var2), 1, var11
                              );
                           }

                           if (374409179 * var9.field2914 == 2) {
                              if (var4 >= 1860831095) {
                                 throw new IllegalStateException();
                              }

                              classYW.method13711(
                                 680806733 * this.field2658 + this.field2654 * 680806733 * var1 - 1,
                                 (63 - var2) * 680806733 * this.field2665 + this.field2654 * 680806733 - 1,
                                 1,
                                 var11
                              );
                           }

                           if (var9.field2914 * 374409179 == 3) {
                              if (var4 >= 1860831095) {
                                 return;
                              }

                              classYW.method13711(
                                 var1 * this.field2665 * 680806733, this.field2658 * 680806733 + this.field2665 * 680806733 * (63 - var2) - 1, 1, var11
                              );
                           }
                        }

                        if (1253547473 * var9.field2912 == 78062377 * classKB.field3520.field3536) {
                           int var12 = var9.field2914 * 374409179 % 2;
                           if (0 == var12) {
                              if (var4 >= 1860831095) {
                                 throw new IllegalStateException();
                              }

                              for (int var15 = 0; var15 < 680806733 * this.field2665; var15++) {
                                 if (var4 >= 1860831095) {
                                    return;
                                 }

                                 classYW.method13711(var1 * this.field2660 * 680806733 + var15, this.field2658 * 680806733 * (64 - var2) - 1 - var15, 1, var11);
                              }
                           } else {
                              for (int var13 = 0; var13 < 680806733 * this.field2660; var13++) {
                                 if (var4 >= 1860831095) {
                                    throw new IllegalStateException();
                                 }

                                 classYW.method13763(var13 + var1 * 680806733 * this.field2654, var13 + this.field2660 * 680806733 * (63 - var2), 1, var11);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "gr.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILjava/util/HashSet;II)V")
   @ObfuscatedName("bp")
   void method5746(int var1, int var2, HashSet var3, int var4, int var5) {
      try {
         float var6 = var4 / 64.0F;
         Iterator var7 = this.field2656.iterator();

         while (var7.hasNext()) {
            if (var5 != 543832187) {
               return;
            }

            WorldMapIcon var8 = (WorldMapIcon)var7.next();
            if (!var8.method6026((byte)-115)) {
               if (var5 != 543832187) {
                  throw new IllegalStateException();
               }
            } else {
               int var9 = 1870919123 * var8.field2767.field4055 % 64;
               int var10 = 819227381 * var8.field2768.field4054 % 64;
               var8.field2769 = (int)(var1 + var9 * var6) * 1179114429;
               var8.field2769 = 1689733203 * (int)(var2 + var6 * (63 - var10));
               if (var3.contains(var8.vmethod486((byte)-86))) {
                  if (var5 != 543832187) {
                     return;
                  }
               } else {
                  this.method5730(var8, 797816725 * var8.field2769, var8.field2770 * 655244251, var6, (byte)32);
               }
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "gr.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)Ljava/util/List;")
   @ObfuscatedName("bj")
   List method5763(int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         LinkedList var7 = new LinkedList();
         if (var4 >= var1) {
            if (var6 <= -673752481) {
               throw new IllegalStateException();
            }

            if (var5 >= var2) {
               if (var4 < var1 + var3) {
                  if (var6 <= -673752481) {
                     throw new IllegalStateException();
                  }

                  if (var5 < var2 + var3) {
                     Iterator var8 = this.field2657.values().iterator();

                     while (var8.hasNext()) {
                        if (var6 <= -673752481) {
                           throw new IllegalStateException();
                        }

                        WorldMapIcon var9 = (WorldMapIcon)var8.next();
                        if (var9.method6026((byte)-4)) {
                           if (var6 <= -673752481) {
                              throw new IllegalStateException();
                           }

                           if (var9.method6024(var4, var5, (byte)-65)) {
                              var7.add(var9);
                           }
                        }
                     }

                     var8 = this.field2656.iterator();

                     while (var8.hasNext()) {
                        if (var6 <= -673752481) {
                           throw new IllegalStateException();
                        }

                        WorldMapIcon var12 = (WorldMapIcon)var8.next();
                        if (var12.method6026((byte)-54)) {
                           if (var6 <= -673752481) {
                              throw new IllegalStateException();
                           }

                           if (var12.method6024(var4, var5, (byte)-44)) {
                              var7.add(var12);
                           }
                        }
                     }

                     return var7;
                  }
               }

               return var7;
            }

            if (var6 <= -673752481) {
               throw new IllegalStateException();
            }
         }

         return var7;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "gr.bj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   void method5669(int var1) {
      try {
         for (WorldMapIcon var3 : this.field2657.values()) {
            if (var3 instanceof classGS) {
               if (var1 == 757968036) {
                  throw new IllegalStateException();
               }

               ((classGS)var3).method5771(-1132427880);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gr.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILjava/util/HashSet;)V")
   @ObfuscatedName("cj")
   void method5686(int var1, int var2, int var3, HashSet var4) {
      if (null == var4) {
         var4 = new HashSet();
      }

      this.method5721(var1, var2, var4, var3, (byte)3);
      method5745(this, var1, var2, var4, var3, 543832187);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Ljava/util/List;")
   @ObfuscatedName("ei")
   List method5764(int var1, int var2, int var3, int var4, int var5) {
      LinkedList var6 = new LinkedList();
      if (var4 >= var1 && var5 >= var2) {
         if (var4 < var1 + var3 && var5 < var2 + var3) {
            for (WorldMapIcon var8 : this.field2657.values()) {
               if (var8.method6026((byte)-117) && var8.method6024(var4, var5, (byte)-127)) {
                  var6.add(var8);
               }
            }

            for (WorldMapIcon var10 : this.field2656) {
               if (var10.method6026((byte)-55) && var10.method6024(var4, var5, (byte)-67)) {
                  var6.add(var10);
               }
            }

            return var6;
         } else {
            return var6;
         }
      } else {
         return var6;
      }
   }
}
