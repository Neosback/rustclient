import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wm")
public class RenderOverview implements net.runelite.api.RenderOverview {
   @ObfuscatedName("at")
   HashMap field6745;
   @ObfuscatedName("bc")
   int field6734;
   @ObfuscatedName("bl")
   long field6739;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final float field6721 = 30.0F;
   @ObfuscatedName("bu")
   int field6700;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field6723 = 50;
   @ObfuscatedSignature(descriptor = "Lxj;")
   @ObfuscatedName("ax")
   static final classXJ field6722 = classXJ.field6875;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6730 = -1;
   @ObfuscatedSignature(descriptor = "Lxj;")
   @ObfuscatedName("aa")
   static final classXJ field6724 = classXJ.field6873;
   @ObfuscatedName("cr")
   HashSet field6711;
   @ObfuscatedSignature(descriptor = "Lym;")
   @ObfuscatedName("cs")
   SpritePixels field6752;
   @ObfuscatedName("aq")
   final int[] field6718 = new int[]{1008, 1009, 1010, 1011, 1012};
   @ObfuscatedName("ad")
   public boolean field6699 = false;
   @ObfuscatedName("ap")
   HashSet field6706 = new HashSet();
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("au")
   IndexDataBase field6750;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ai")
   IndexDataBase field6748;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ar")
   IndexDataBase field6749;
   @ObfuscatedSignature(descriptor = "Lzv;")
   @ObfuscatedName("aw")
   classZV field6753;
   @ObfuscatedName("ak")
   HashMap field6744;
   @ObfuscatedName("bk")
   int field6698;
   @ObfuscatedSignature(descriptor = "Lgx;")
   @ObfuscatedName("ah")
   WorldMapData field6741;
   @ObfuscatedSignature(descriptor = "Lgx;")
   @ObfuscatedName("an")
   WorldMapData field6743;
   @ObfuscatedSignature(descriptor = "Lgx;")
   @ObfuscatedName("am")
   WorldMapData field6742;
   @ObfuscatedName("bm")
   int field6691;
   @ObfuscatedSignature(descriptor = "Lgu;")
   @ObfuscatedName("bn")
   WorldMapRenderer field6740;
   @ObfuscatedSignature(descriptor = "Lwh;")
   @ObfuscatedName("bx")
   classWH field6751;
   @ObfuscatedName("ca")
   int field6738;
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("av")
   IndexedSprite[] field6754;
   @ObfuscatedName("bv")
   int field6694 = 832458829;
   @ObfuscatedSignature(descriptor = "Lxj;")
   @ObfuscatedName("ac")
   static final classXJ field6726 = classXJ.field6877;
   @ObfuscatedName("bt")
   float field6728;
   @ObfuscatedName("bg")
   float field6727;
   @ObfuscatedName("co")
   int field6693;
   @ObfuscatedName("cl")
   List field6747;
   @ObfuscatedName("cq")
   int field6697;
   @ObfuscatedName("bj")
   int field6695;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field6725 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field6733 = -65536;
   @ObfuscatedName("bz")
   boolean field6710;
   @ObfuscatedName("bi")
   HashSet field6703;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field6732 = 500;
   @ObfuscatedName("bp")
   int field6705;
   @ObfuscatedName("by")
   int field6692;
   @ObfuscatedName("bh")
   int field6701;
   @ObfuscatedName("br")
   int field6704;
   @ObfuscatedName("ck")
   int field6709;
   @ObfuscatedName("bb")
   boolean field6696;
   @ObfuscatedName("bw")
   int field6737;
   @ObfuscatedName("bq")
   int field6736;
   @ObfuscatedName("bo")
   int field6713 = -1910538783;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field6731 = 100;
   @ObfuscatedName("cu")
   HashSet field6712;
   @ObfuscatedName("cb")
   HashSet field6707;
   @ObfuscatedName("ct")
   HashSet field6714;
   @ObfuscatedName("cp")
   boolean field6715;
   @ObfuscatedName("bs")
   int field6716;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6729 = 8;
   @ObfuscatedName("cd")
   Iterator field6746;
   @ObfuscatedSignature(descriptor = "Lky;")
   @ObfuscatedName("cv")
   classKY field6717;
   @ObfuscatedName("ba")
   int field6708;
   @ObfuscatedName("bd")
   int field6735;
   @ObfuscatedName("cy")
   int field6702;
   @ObfuscatedName("be")
   int field6719;
   @ObfuscatedName("bf")
   int field6720;

   @ObfuscatedSignature(descriptor = "(IIIIIS)V")
   @ObfuscatedName("am")
   void method12538(int var1, int var2, int var3, int var4, int var5, short var6) {
      try {
         byte var7 = 20;
         int var8 = var1 + var3 / 2;
         int var9 = var2 + var4 / 2 - 18 - var7;
         classYW.method13737(var1, var2, var3, var4, -16777216);
         classYW.method13746(var8 - 152, var9, 304, 34, -65536);
         classYW.method13737(var8 - 150, 2 + var9, var5 * 3, 30, -65536);
         this.field6753.method59(classKK.field3919, var8, var7 + var9, -1, -1);
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "wm.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ek")
   void method12515(int var1, int var2, int var3) {
      if (this.field6742 != null) {
         int[] var4 = WorldMapData.method5869(this.field6742, var1, var2, var3, (short)-3941);
         if (null == var4) {
            var4 = WorldMapData.method5869(
               this.field6742,
               this.field6742.method5920((byte)-67),
               WorldMapData.method5918(this.field6742, -915535260),
               WorldMapData.method5924(this.field6742, -1432227408),
               (short)-18645
            );
         }

         this.method12476(
            var4[0] - this.field6742.method5899(2086260676) * 64, var4[1] - WorldMapData.method5907(this.field6742, (short)-12362) * 64, true, -2126973978
         );
         this.field6694 = 832458829;
         this.field6713 = -1910538783;
         this.field6728 = this.method12543(WorldMapData.method5897(this.field6742, (byte)7), 120041229);
         this.field6727 = this.field6728;
         this.field6747 = null;
         this.field6746 = null;
         this.field6740.method5780((byte)-41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("fa")
   public int method12588() {
      return this.field6693 * -811287683;
   }

   @ObfuscatedSignature(descriptor = "(Lwm;Lgx;I)V")
   @ObfuscatedName("ai")
   public static void method12505(RenderOverview var0, WorldMapData var1, int var2) {
      if (var0 == null) {
         var0.method12511(var1, var2);
      }

      try {
         var0.field6742 = var1;
         var0.field6740 = new WorldMapRenderer(var0.field6754, var0.field6744, var0.field6748, var0.field6749);
         var0.field6751.method12433(var0.field6742, (byte)-74);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ji")
   public int method12660() {
      return this.field6734 * 1010414839;
   }

   @ObfuscatedSignature(descriptor = "(IIZZI)V")
   @ObfuscatedName("ab")
   public void method12458(int var1, int var2, boolean var3, boolean var4, int var5) {
      try {
         long var6;
         label56: {
            var6 = classDD.method2988(16711680);
            this.method12464(var1, var2, var4, var6);
            label55:
            if (!this.method12483(1754567301)) {
               if (!var4) {
                  if (var5 >= -1271930519) {
                     throw new IllegalStateException();
                  }

                  if (!var3) {
                     break label55;
                  }

                  if (var5 >= -1271930519) {
                     throw new IllegalStateException();
                  }
               }

               boolean var8 = classWK.field6689.method12230(-431955666);
               if (!var8) {
                  if (var4) {
                     if (var5 >= -1271930519) {
                        throw new IllegalStateException();
                     }

                     this.field6698 = -935653697 * var1;
                     this.field6709 = -1677524883 * var2;
                     this.field6708 = -223069005 * this.field6738;
                     this.field6701 = -943412585 * this.field6734;
                  }

                  if (this.field6708 * -156946503 != -1) {
                     int var9 = var1 - this.field6698 * 543121727;
                     int var10 = var2 - -656340635 * this.field6709;
                     this.method12476(
                        -156946503 * this.field6708 - (int)(var9 / this.field6727),
                        (int)(var10 / this.field6727) + this.field6701 * 1131574433,
                        false,
                        -836625698
                     );
                  }
                  break label56;
               }
            }

            this.method12482(1429288549);
         }

         if (var4) {
            if (var5 >= -1271930519) {
               throw new IllegalStateException();
            }

            this.field6739 = -2654852032954698167L * var6;
            this.field6737 = var1 * 1947564039;
            this.field6736 = -427845355 * var2;
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "wm.ab(" + ')');
      }
   }

   public void initializeWorldMap(net.runelite.api.worldmap.WorldMapData var1) {
      method12505(this, (WorldMapData)var1, -568352897);
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)Z")
   @ObfuscatedName("eh")
   boolean method12524(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (this.field6752 == null) {
         return true;
      } else if (this.field6752.field7005 == var1 && this.field6752.field7007 == var2) {
         if (-285636757 * this.field6735 != -314528699 * this.field6740.field2684) {
            return true;
         } else if (1373094587 * client.field882 != 829903289 * this.field6720) {
            return true;
         } else {
            return var3 <= 0 && var4 <= 0 ? var1 + var3 < var5 || var4 + var2 < var6 : true;
         }
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lhd;")
   @ObfuscatedName("hv")
   public WorldMapIcon method12652() {
      if (!this.field6751.method12442((short)255)) {
         return null;
      } else if (!this.field6740.method5801((short)-17081)) {
         return null;
      } else {
         HashMap var1 = this.field6740.method5811((byte)100);
         this.field6747 = new LinkedList();

         for (List var3 : var1.values()) {
            this.field6747.addAll(var3);
         }

         this.field6746 = this.field6747.iterator();
         return this.method12657((byte)-18);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("nj")
   public int method12661() {
      return this.field6694 * -19228805;
   }

   public RenderOverview() {
      this.field6693 = -344315349;
      this.field6692 = -573698517;
      this.field6697 = 77312519;
      this.field6695 = -1723689919;
      this.field6700 = -878772995;
      this.field6691 = 739603646;
      this.field6696 = false;
      this.field6703 = null;
      this.field6704 = 1148417837;
      this.field6705 = -958191021;
      this.field6708 = -983836297;
      this.field6701 = -655388513;
      this.field6698 = 935653697;
      this.field6709 = 1677524883;
      this.field6710 = true;
      this.field6711 = new HashSet();
      this.field6712 = new HashSet();
      this.field6707 = new HashSet();
      this.field6714 = new HashSet();
      this.field6715 = false;
      this.field6716 = 0;
      this.field6717 = null;
      this.field6702 = -1462606503;
      this.field6719 = -1135731861;
      this.field6720 = -629236873;
   }

   @ObfuscatedSignature(descriptor = "(IIZZ)V")
   @ObfuscatedName("cf")
   public void method12459(int var1, int var2, boolean var3, boolean var4) {
      long var5;
      label44: {
         var5 = classDD.method2988(16711680);
         this.method12464(var1, var2, var4, var5);
         if (!this.method12483(1977027471) && (var4 || var3)) {
            boolean var7 = classWK.field6689.method12230(-431955666);
            if (!var7) {
               if (var4) {
                  this.field6698 = -935653697 * var1;
                  this.field6709 = -1677524883 * var2;
                  this.field6708 = -223069005 * this.field6738;
                  this.field6701 = -943412585 * this.field6734;
               }

               if (this.field6708 * -156946503 != -1) {
                  int var8 = var1 - this.field6698 * 543121727;
                  int var9 = var2 - -656340635 * this.field6709;
                  this.method12476(
                     -156946503 * this.field6708 - (int)(var8 / this.field6727), (int)(var9 / this.field6727) + this.field6701 * 1131574433, false, -1960540117
                  );
               }
               break label44;
            }
         }

         this.method12482(1429288549);
      }

      if (var4) {
         this.field6739 = -2654852032954698167L * var5;
         this.field6737 = var1 * 1947564039;
         this.field6736 = -427845355 * var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)Lgx;")
   @ObfuscatedName("al")
   public WorldMapData method12486(int var1, int var2, int var3, byte var4) {
      try {
         Iterator var5 = this.field6745.values().iterator();

         while (var5.hasNext()) {
            if (var4 == 16) {
               throw new IllegalStateException();
            }

            WorldMapData var6 = (WorldMapData)var5.next();
            if (WorldMapData.method5858(var6, var1, var2, var3, (byte)58)) {
               if (var4 == 16) {
                  throw new IllegalStateException();
               }

               return var6;
            }
         }

         return null;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "wm.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIZS)V")
   @ObfuscatedName("aj")
   public void method12487(int var1, int var2, int var3, boolean var4, short var5) {
      try {
         WorldMapData var6 = this.method12486(var1, var2, var3, (byte)-9);
         if (null == var6) {
            if (!var4) {
               return;
            }

            var6 = this.field6743;
         }

         boolean var7;
         label51: {
            var7 = false;
            if (var6 == this.field6741) {
               if (var5 != 28249) {
                  throw new IllegalStateException();
               }

               if (!var4) {
                  break label51;
               }

               if (var5 != 28249) {
                  return;
               }
            }

            this.field6741 = var6;
            this.method12501(var6, -497706825);
            var7 = true;
         }

         if (!var7) {
            if (var5 != 28249) {
               throw new IllegalStateException();
            }

            if (!var4) {
               return;
            }

            if (var5 != 28249) {
               throw new IllegalStateException();
            }
         }

         this.method12516(var1, var2, var3, 1279937673);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "wm.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZI)V")
   @ObfuscatedName("ac")
   final void method12476(int var1, int var2, boolean var3, int var4) {
      try {
         this.field6738 = -871438893 * var1;
         this.field6734 = 841829575 * var2;
         classDD.method2988(16711680);
         if (var3) {
            if (var4 >= -787422434) {
               return;
            }

            this.method12482(1429288549);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "wm.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;Lgx;)V")
   @ObfuscatedName("gp")
   public static void method12500(RenderOverview var0, WorldMapData var1) {
      if (null == var0.field6742 || var1 != var0.field6742) {
         method12505(var0, var1, -531619150);
         var0.method12516(-1, -1, -1, 1438809329);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;I)V")
   @ObfuscatedName("tf")
   public static void method12613(RenderOverview var0, int var1) {
      if (var0 == null) {
         var0.method12473();
      }

      var0.field6703 = new HashSet();
      var0.field6704 = 0;
      var0.field6705 = 0;

      for (int var2 = 0; var2 < classPS.field5405 * -1676885712; var2++) {
         if (SceneTilePaint.method4817(var2, (byte)-102) != null && SceneTilePaint.method4817(var2, (byte)-78).field5402 * -1896737909 == var1) {
            var0.field6703.add(SceneTilePaint.method4817(var2, (byte)-94).field5403 * 959033126);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;I)V")
   @ObfuscatedName("ap")
   void method12501(WorldMapData var1, int var2) {
      try {
         if (null != this.field6742) {
            if (var2 >= 81862380) {
               return;
            }

            if (var1 == this.field6742) {
               if (var2 >= 81862380) {
                  throw new IllegalStateException();
               }

               return;
            }
         }

         method12505(this, var1, -568352897);
         this.method12516(-1, -1, -1, 1513972896);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ar")
   void method12516(int var1, int var2, int var3, int var4) {
      try {
         if (this.field6742 == null) {
            if (var4 > 1216253243) {
               ;
            }
         } else {
            int[] var5 = WorldMapData.method5869(this.field6742, var1, var2, var3, (short)-29644);
            if (null == var5) {
               if (var4 <= 1216253243) {
                  return;
               }

               var5 = WorldMapData.method5869(
                  this.field6742,
                  this.field6742.method5920((byte)-7),
                  WorldMapData.method5918(this.field6742, -189047916),
                  WorldMapData.method5924(this.field6742, -1204357463),
                  (short)-14549
               );
            }

            this.method12476(
               var5[0] - this.field6742.method5899(2005615177) * 64, var5[1] - WorldMapData.method5907(this.field6742, (short)-23223) * 64, true, -1392100145
            );
            this.field6694 = 832458829;
            this.field6713 = -1910538783;
            this.field6728 = this.method12543(WorldMapData.method5897(this.field6742, (byte)90), 120041229);
            this.field6727 = this.field6728;
            this.field6747 = null;
            this.field6746 = null;
            this.field6740.method5780((byte)51);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wm.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIID)V")
   @ObfuscatedName("aw")
   public void method12518(int var1, int var2, int var3, int var4, int var5, double var6) {
      try {
         int[] var8 = new int[4];
         classYW.method13714(var8);
         classYW.method13710(var1, var2, var3 + var1, var4 + var2);
         int var9 = this.field6751.method12443(-1540584211);
         if (var9 < 100) {
            this.method12538(var1, var2, var3, var4, var9, (short)13674);
         } else {
            if (!this.field6740.method5801((short)23258)) {
               this.field6740.method5778(this.field6750, this.field6742, client.field933, (byte)44);
               if (!this.field6740.method5801((short)-3532)) {
                  return;
               }
            }

            int var11 = this.field6740.method5804((short)4010);
            double var12 = (var11 >> 16 & 0xFF) / 256.0;
            double var14 = (var11 >> 8 & 0xFF) / 256.0;
            double var16 = (var11 & 0xFF) / 256.0;
            var12 = Math.pow(var12, var6);
            var14 = Math.pow(var14, var6);
            var16 = Math.pow(var16, var6);
            int var18 = (int)(256.0 * var12);
            int var19 = (int)(256.0 * var14);
            int var20 = (int)(var16 * 256.0);
            int var10 = var20 + (var19 << 8) + -16777216 + (var18 << 16);
            classYW.method13737(var1, var2, var3, var4, var10);
            if (this.field6703 != null) {
               this.field6705 += 958191021;
               if (this.field6705 * -1964943835 % (this.field6691 * 624117895) == 0) {
                  this.field6705 = 0;
                  this.field6704 += -1148417837;
               }

               if (1316818779 * this.field6704 >= -775290625 * this.field6700 && !this.field6696) {
                  this.field6703 = null;
               }
            }

            int var13 = (int)Math.ceil(var3 / this.field6728);
            int var24 = (int)Math.ceil(var4 / this.field6728);
            double var15 = classFH.method4722();
            this.field6740
               .method5781(
                  -1664894373 * this.field6738 - var13 / 2,
                  1010414839 * this.field6734 - var24 / 2,
                  var13 / 2 + -1664894373 * this.field6738,
                  var24 / 2 + 1010414839 * this.field6734,
                  var1,
                  var2,
                  var1 + var3,
                  var4 + var2,
                  (byte)94
               );
            if (!this.field6715) {
               boolean var17 = false;
               if (var5 - 1129128375 * this.field6716 > 100) {
                  this.field6716 = 1227656199 * var5;
                  var17 = true;
               }

               this.field6740
                  .method5784(
                     this.field6738 * -1664894373 - var13 / 2,
                     1010414839 * this.field6734 - var24 / 2,
                     var13 / 2 + this.field6738 * -1664894373,
                     var24 / 2 + this.field6734 * 1010414839,
                     var1,
                     var2,
                     var1 + var3,
                     var2 + var4,
                     this.field6714,
                     this.field6703,
                     -1964943835 * this.field6705,
                     624117895 * this.field6691,
                     var17,
                     1925771452
                  );
            }

            this.method12529(var1, var2, var3, var4, var13, var24, -1462212404);
            if (MessageNode.method1494((byte)-84) && this.field6699 && null != this.field6717) {
               this.field6753.method51("Coord: " + this.field6717, classYW.field7095 + 10, 20 + classYW.field7094, 16776960, -1);
            }

            this.field6693 = 344315349 * var13;
            this.field6692 = 573698517 * var24;
            this.field6697 = -77312519 * var1;
            this.field6695 = 1723689919 * var2;
            classYW.method13718(var8);
            if (var15 != classFH.method4722()) {
               classFH.method4714(var15);
            }
         }
      } catch (RuntimeException var21) {
         throw classEG.method3899(var21, "wm.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ak")
   public void method12520(int var1) {
      try {
         WorldMapRegion.field2661.method6208();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cy")
   public void method12451() {
      classPF.method8889((byte)71);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;Lva;Lva;Lva;Lzv;Ljava/util/HashMap;[Lyz;)V")
   @ObfuscatedName("kk")
   public static void method12448(
      RenderOverview var0, IndexDataBase var1, IndexDataBase var2, IndexDataBase var3, classZV var4, HashMap var5, IndexedSprite[] var6
   ) {
      if (var0 == null) {
         var0.method12594();
      }

      var0.field6754 = var6;
      var0.field6750 = var1;
      var0.field6748 = var2;
      var0.field6749 = var3;
      var0.field6753 = var4;
      var0.field6744 = new HashMap();
      var0.field6744.put(classHP.field2854, var5.get(field6722));
      var0.field6744.put(classHP.field2855, var5.get(field6726));
      var0.field6744.put(classHP.field2856, var5.get(field6724));
      var0.field6751 = new classWH(var1);
      int var7 = -1013526269 * classHE.field2772.field2771;
      int[] var8 = var0.field6750.method11898(var7, -1817826448);
      int var9 = var8 == null ? 0 : var8.length;
      var0.field6745 = new HashMap(var9);

      for (int var10 = 0; var10 < var9; var10++) {
         classXY var11 = new classXY(var0.field6750.method11942(var7, var8[var10], -1217061968));
         WorldMapData var12 = new WorldMapData();
         var12.method5849(var11, var8[var10], 1848080910);
         var0.field6745.put(var12.method5885(-1761269607), var12);
         if (WorldMapData.method5880(var12, (byte)48)) {
            var0.field6743 = var12;
         }
      }

      var0.method12501(var0.field6743, -173702782);
      var0.field6741 = null;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("at")
   void method12529(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      try {
         if (null != classMC.field4479) {
            if (var7 >= -1148417837) {
               return;
            }

            int var8 = 512 / (-629057398 * this.field6740.field2684);
            int var9 = var3 + 512;
            int var10 = var4 + 512;
            float var11 = 1.0F;
            var9 = (int)(var9 / var11);
            var10 = (int)(var10 / var11);
            int var12 = this.method12579(-836464468) - var5 / 2 - var8;
            int var13 = this.method12580(525287209) - var6 / 2 - var8;
            int var14 = var1 - (var8 + var12 - 927100183 * this.field6702) * -314528699 * this.field6740.field2684;
            int var15 = var2 - this.field6740.field2684 * -314528699 * (var8 - (var13 - this.field6719 * 1420019389));
            if (this.method12526(var9, var10, var14, var15, var3, var4, -89366347)) {
               if (var7 >= -1148417837) {
                  throw new IllegalStateException();
               }

               label61: {
                  if (null != this.field6752) {
                     if (var7 >= -1148417837) {
                        throw new IllegalStateException();
                     }

                     if (this.field6752.field7005 == var9) {
                        if (var7 >= -1148417837) {
                           return;
                        }

                        if (var10 == this.field6752.field7007) {
                           Arrays.fill(this.field6752.field7003, 0);
                           break label61;
                        }

                        if (var7 >= -1148417837) {
                           return;
                        }
                     }
                  }

                  this.field6752 = new SpritePixels(var9, var10);
               }

               this.field6702 = (this.method12579(-618903686) - var5 / 2 - var8) * 1462606503;
               this.field6719 = (this.method12580(-2105700704) - var6 / 2 - var8) * 1135731861;
               this.field6735 = this.field6740.field2684 * -87197937;
               classMC.field4479
                  .method14269(927100183 * this.field6702, 1420019389 * this.field6719, this.field6752, this.field6735 * -285636757 / var11, 102032434);
               this.field6720 = client.field882 * 1002652179;
               var14 = var1 - this.field6740.field2684 * -314528699 * (var8 + var12 - this.field6702 * 927100183);
               var15 = var2 - (var8 - (var13 - this.field6719 * 1420019389)) * this.field6740.field2684 * -314528699;
            }

            classYW.method13733(var1, var2, var3, var4, 0, 128);
            if (1.0F == var11) {
               if (var7 >= -1148417837) {
                  throw new IllegalStateException();
               }

               this.field6752.method13573(var14, var15, 192);
            } else {
               this.field6752.method13575(var14, var15, (int)(var11 * var9), (int)(var10 * var11), 192);
            }
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "wm.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)F")
   @ObfuscatedName("ah")
   float method12543(int var1, int var2) {
      try {
         if (25 == var1) {
            if (var2 != 120041229) {
               throw new IllegalStateException();
            } else {
               return 1.0F;
            }
         } else if (37 == var1) {
            if (var2 != 120041229) {
               throw new IllegalStateException();
            } else {
               return 1.5F;
            }
         } else if (50 == var1) {
            return 2.0F;
         } else if (75 == var1) {
            if (var2 != 120041229) {
               throw new IllegalStateException();
            } else {
               return 3.0F;
            }
         } else if (100 == var1) {
            if (var2 != 120041229) {
               throw new IllegalStateException();
            } else {
               return 4.0F;
            }
         } else {
            return 8.0F;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.ah(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;Lgx;)V")
   @ObfuscatedName("oz")
   public static void method12502(RenderOverview var0, WorldMapData var1) {
      if (null == var0.field6742 || var1 != var0.field6742) {
         method12505(var0, var1, -666270259);
         var0.method12516(-1, -1, -1, 1740808024);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("bc")
   public void method12554(byte var1) {
      try {
         classWH.method12439(this.field6751, 2016327012);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lgx;")
   @ObfuscatedName("br")
   public WorldMapData method12558(int var1, int var2) {
      try {
         Iterator var3 = this.field6745.values().iterator();

         while (var3.hasNext()) {
            if (var2 <= 1661497523) {
               throw new IllegalStateException();
            }

            WorldMapData var4 = (WorldMapData)var3.next();
            if (var4.method5876(-608589044) == var1) {
               return var4;
            }
         }

         return null;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "wm.br(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("bm")
   public void method12563(int var1, int var2, byte var3) {
      try {
         if (null != this.field6742) {
            if (var3 >= -1) {
               return;
            }

            if (WorldMapData.method5867(this.field6742, var1, var2, 1404367312)) {
               this.field6694 = (var1 - this.field6742.method5899(2140147375) * 64) * -832458829;
               this.field6713 = (var2 - WorldMapData.method5907(this.field6742, (short)-12407) * 64) * 1910538783;
               return;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wm.bm(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;IIIII)V")
   @ObfuscatedName("ns")
   public static void method12539(RenderOverview var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         var0.method12635(var1, var1);
      }

      byte var6 = 20;
      int var7 = var1 + var3 / 2;
      int var8 = var2 + var4 / 2 - 18 - var6;
      classYW.method13737(var1, var2, var3, var4, -16777216);
      classYW.method13746(var7 - 152, var8, 304, 34, -65536);
      classYW.method13737(var7 - 150, 2 + var8, var5 * 3, 30, -65536);
      var0.field6753.method59(classKK.field3919, var7, var6 + var8, -1, -1);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bt")
   public void method12568(int var1, int var2, int var3) {
      try {
         if (null == this.field6742) {
            if (var3 >= 982132619) {
               throw new IllegalStateException();
            }
         } else {
            this.method12476(
               var1 - this.field6742.method5899(2111888647) * 64, var2 - WorldMapData.method5907(this.field6742, (short)-4294) * 64, true, -1279830878
            );
            this.field6694 = 832458829;
            this.field6713 = -1910538783;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wm.bt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ao")
   boolean method12483(int var1) {
      try {
         if (-19228805 * this.field6694 != -1) {
            if (var1 <= 1547676657) {
               throw new IllegalStateException();
            }

            if (-1 != -1101891105 * this.field6713) {
               if (var1 <= 1547676657) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lgx;")
   @ObfuscatedName("dv")
   public WorldMapData method12496() {
      return this.field6742;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("bn")
   public int method12589(byte var1) {
      try {
         return this.field6693 * -811287683;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.ba(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bw")
   public int method12592(int var1) {
      try {
         return 166969213 * this.field6692;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bw(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;)V")
   @ObfuscatedName("wz")
   public static void method12465(RenderOverview var0) {
      if (var0 == null) {
         var0.method12470();
      } else if (classMC.field4479 != null) {
         var0.field6728 = var0.field6727;
      } else {
         if (var0.field6728 < var0.field6727) {
            var0.field6728 = Math.min(var0.field6727, var0.field6728 + var0.field6728 / 30.0F);
         }

         if (var0.field6728 > var0.field6727) {
            var0.field6728 = Math.max(var0.field6727, var0.field6728 - var0.field6728 / 30.0F);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IZI)V")
   @ObfuscatedName("bl")
   public void method12620(int var1, boolean var2, int var3) {
      try {
         if (!var2) {
            if (var3 >= -1244985006) {
               throw new IllegalStateException();
            }

            this.field6711.add(var1);
         } else {
            this.field6711.remove(var1);
         }

         this.method12638((byte)69);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wm.bl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("bi")
   public void method12600(byte var1) {
      try {
         this.field6700 = -878772995;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bi(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("be")
   public void method12602(int var1, int var2) {
      try {
         if (var1 >= 1) {
            if (var2 <= -751737320) {
               throw new IllegalStateException();
            }

            this.field6691 = var1 * -71107273;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.be(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("by")
   public void method12605(byte var1) {
      try {
         this.field6691 = 739603646;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.by(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("bq")
   public void method12608(boolean var1, int var2) {
      try {
         this.field6696 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.bq(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;I)F")
   @ObfuscatedName("lk")
   public static float method12544(RenderOverview var0, int var1) {
      if (var0 == null) {
         var0.method12654();
      }

      if (25 == var1) {
         return 1.0F;
      } else if (1330441392 == var1) {
         return 1.5F;
      } else if (50 == var1) {
         return 2.0F;
      } else if (75 == var1) {
         return 3.0F;
      } else {
         return 1964850342 == var1 ? 4.0F : 8.0F;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;IIIIII)V")
   @ObfuscatedName("ai")
   public static void method12530(RenderOverview var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var0 == null) {
         var0.method12618();
      } else {
         if (null != classMC.field4479) {
            int var7 = 512 / (-629057398 * var0.field6740.field2684);
            int var8 = var3 + 512;
            int var9 = var4 + 512;
            float var10 = 1.0F;
            var8 = (int)(var8 / var10);
            var9 = (int)(var9 / var10);
            int var11 = var0.method12579(-1374488797) - var5 / 2 - var7;
            int var12 = var0.method12580(1477708979) - var6 / 2 - var7;
            int var13 = var1 - (var7 + var11 - 927100183 * var0.field6702) * -314528699 * var0.field6740.field2684;
            int var14 = var2 - var0.field6740.field2684 * -314528699 * (var7 - (var12 - var0.field6719 * 1420019389));
            if (var0.method12526(var8, var9, var13, var14, var3, var4, -89366347)) {
               if (null != var0.field6752 && var0.field6752.field7005 == var8 && var9 == var0.field6752.field7007) {
                  Arrays.fill(var0.field6752.field7003, 0);
               } else {
                  var0.field6752 = new SpritePixels(var8, var9);
               }

               var0.field6702 = (var0.method12579(457110014) - var5 / 2 - var7) * 1462606503;
               var0.field6719 = (var0.method12580(524312259) - var6 / 2 - var7) * 1135731861;
               var0.field6735 = var0.field6740.field2684 * -87197937;
               classMC.field4479
                  .method14269(927100183 * var0.field6702, 1420019389 * var0.field6719, var0.field6752, var0.field6735 * -285636757 / var10, 885793990);
               var0.field6720 = client.field882 * 1002652179;
               var13 = var1 - var0.field6740.field2684 * -314528699 * (var7 + var11 - var0.field6702 * 927100183);
               var14 = var2 - (var7 - (var12 - var0.field6719 * 1420019389)) * var0.field6740.field2684 * -314528699;
            }

            classYW.method13733(var1, var2, var3, var4, 0, 128);
            if (1.0F == var10) {
               var0.field6752.method13573(var13, var14, 192);
            } else {
               var0.field6752.method13575(var13, var14, (int)(var10 * var8), (int)(var9 * var10), 192);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;IIIIII)Z")
   @ObfuscatedName("zo")
   public static boolean method12525(RenderOverview var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var0 == null) {
         var0.method12499(var1);
      }

      if (var0.field6752 == null) {
         return true;
      } else if (var0.field6752.field7005 == var1 && var0.field6752.field7007 == var2) {
         if (-285636757 * var0.field6735 != 1257880482 * var0.field6740.field2684) {
            return true;
         } else if (-819070451 * client.field882 != 1321367484 * var0.field6720) {
            return true;
         } else {
            return var3 <= 0 && var4 <= 0 ? var1 + var3 < var5 || var4 + var2 < var6 : true;
         }
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;IIIII)V")
   @ObfuscatedName("qa")
   public static void method12533(RenderOverview var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         var0.method12537(var1, var1, var1, var1, var1);
      }

      try {
         if (!var0.field6751.method12442((short)255)) {
            if (var5 == 1689794791) {
               throw new IllegalStateException();
            }
         } else {
            if (!var0.field6740.method5801((short)16106)) {
               if (var5 == 1689794791) {
                  throw new IllegalStateException();
               }

               var0.field6740.method5778(var0.field6750, var0.field6742, client.field933, (byte)2);
               if (!var0.field6740.method5801((short)-10268)) {
                  if (var5 == 1689794791) {
                     throw new IllegalStateException();
                  }

                  return;
               }
            }

            WorldMapRenderer.method5793(
               var0.field6740, var1, var2, var3, var4, var0.field6703, -1964943835 * var0.field6705, var0.field6691 * 624117895, (byte)-75
            );
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wm.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;I)V")
   @ObfuscatedName("ri")
   public static void method12466(RenderOverview var0, int var1) {
      if (var0 == null) {
         var0.method12471(var1);
      }

      try {
         if (classMC.field4479 != null) {
            if (var1 > 943875281) {
               var0.field6728 = var0.field6727;
            }
         } else {
            if (var0.field6728 < var0.field6727) {
               if (var1 <= 943875281) {
                  throw new IllegalStateException();
               }

               var0.field6728 = Math.min(var0.field6727, var0.field6728 + var0.field6728 / 30.0F);
            }

            if (var0.field6728 > var0.field6727) {
               if (var1 <= 943875281) {
                  throw new IllegalStateException();
               }

               var0.field6728 = Math.max(var0.field6727, var0.field6728 - var0.field6728 / 30.0F);
            }
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("hi")
   public void method12621(int var1, boolean var2) {
      if (!var2) {
         this.field6711.add(var1);
      } else {
         this.field6711.remove(var1);
      }

      this.method12638((byte)26);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bd")
   public void method12616(int var1) {
      try {
         this.field6703 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bd(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;III)V")
   @ObfuscatedName("xf")
   public static void method12575(RenderOverview var0, int var1, int var2, int var3) {
      if (var0.field6742 != null) {
         int[] var4 = WorldMapData.method5869(var0.field6742, var1, var2, var3, (short)-12959);
         if (null != var4) {
            var0.method12568(var4[0], var4[1], -989316156);
         }
      }
   }

   public Point getWorldMapPosition() {
      WorldMapRenderer var1 = this.method12670();
      int var2 = this.method12662() + var1.method5826();
      int var3 = this.method12660() + var1.method5824();
      return new Point(var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("hq")
   public void method12644(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (this.field6751.method12442((short)255)) {
         int var7 = (int)Math.ceil(var3 / this.field6728);
         int var8 = (int)Math.ceil(var4 / this.field6728);
         List var9 = this.field6740
            .method5796(
               this.field6738 * 682936646 - var7 / 2 - 1,
               this.field6734 * -881998445 - var8 / 2 - 1,
               1 + this.field6738 * -1664894373 + var7 / 2,
               var8 / 2 + this.field6734 * -21113754 + 1,
               var1,
               var2,
               var3,
               var4,
               var5,
               var6,
               -903075137
            );
         if (!var9.isEmpty()) {
            for (WorldMapIcon var11 : var9) {
               classPS var12 = SceneTilePaint.method4817(var11.vmethod486((byte)-14), (byte)-70);
               boolean var13 = false;

               for (int var14 = this.field6718.length - 1; var14 >= 0; var14--) {
                  if (null != var12.field5398[var14]) {
                     classTA.method10774(
                        var12.field5398[var14],
                        var12.field5410,
                        this.field6718[var14],
                        var11.vmethod486((byte)-92),
                        classKY.method6663(var11.field2768, -1477847462),
                        classKY.method6663(var11.field2767, -1721957829),
                        (byte)-23
                     );
                     var13 = true;
                  }
               }

               if (var13) {
                  return;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gz")
   public void method12595(int var1) {
      if (var1 >= 1) {
         this.field6700 = -1724580097 * var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;Lgx;)V")
   @ObfuscatedName("go")
   public static void method12506(RenderOverview var0, WorldMapData var1) {
      if (var0 == null) {
         var0.getWorldMapData();
      }

      var0.field6742 = var1;
      var0.field6740 = new WorldMapRenderer(var0.field6754, var0.field6744, var0.field6748, var0.field6749);
      var0.field6751.method12433(var0.field6742, (byte)-13);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bp")
   public boolean method12625(int var1) {
      try {
         boolean var10000;
         if (!this.field6715) {
            if (var1 == -2127982102) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bh(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;I)I")
   @ObfuscatedName("ia")
   public static int method12493(RenderOverview var0, int var1) {
      if (var0 == null) {
         return var0.method12495(var1);
      } else {
         try {
            return var0.field6742 == null ? -1 : var0.field6742.method5876(-608589044);
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "wm.aq(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gm")
   public void method12606() {
      this.field6691 = 739603646;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("cu")
   public boolean method12635(int var1, int var2) {
      try {
         boolean var10000;
         if (!this.field6712.contains(var1)) {
            if (var2 == -1229776556) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.cu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("cb")
   void method12638(byte var1) {
      try {
         this.field6714.clear();
         this.field6714.addAll(this.field6711);
         this.field6714.addAll(this.field6707);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.cb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("ct")
   public void method12645(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      try {
         if (!this.field6751.method12442((short)255)) {
            if (var7 == -47366135) {
               throw new IllegalStateException();
            }
         } else {
            int var8 = (int)Math.ceil(var3 / this.field6728);
            int var9 = (int)Math.ceil(var4 / this.field6728);
            List var10 = this.field6740
               .method5796(
                  this.field6738 * -1664894373 - var8 / 2 - 1,
                  this.field6734 * 1010414839 - var9 / 2 - 1,
                  1 + this.field6738 * -1664894373 + var8 / 2,
                  var9 / 2 + this.field6734 * 1010414839 + 1,
                  var1,
                  var2,
                  var3,
                  var4,
                  var5,
                  var6,
                  -903075137
               );
            if (var10.isEmpty()) {
               if (var7 == -47366135) {
                  throw new IllegalStateException();
               }
            } else {
               for (WorldMapIcon var12 : var10) {
                  classPS var13 = SceneTilePaint.method4817(var12.vmethod486((byte)-119), (byte)-116);
                  boolean var14 = false;

                  for (int var15 = this.field6718.length - 1; var15 >= 0; var15--) {
                     if (var7 == -47366135) {
                        throw new IllegalStateException();
                     }

                     if (null != var13.field5398[var15]) {
                        if (var7 == -47366135) {
                           throw new IllegalStateException();
                        }

                        classTA.method10774(
                           var13.field5398[var15],
                           var13.field5410,
                           this.field6718[var15],
                           var12.vmethod486((byte)-103),
                           classKY.method6663(var12.field2768, -1717495662),
                           classKY.method6663(var12.field2767, -1917946749),
                           (byte)-58
                        );
                        var14 = true;
                     }
                  }

                  if (var14) {
                     if (var7 == -47366135) {
                        throw new IllegalStateException();
                     }

                     return;
                  }
               }
            }
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "wm.ct(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILky;I)Lky;")
   @ObfuscatedName("cp")
   public classKY method12646(int var1, classKY var2, int var3) {
      try {
         if (!this.field6751.method12442((short)255)) {
            if (var3 != 241883862) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else if (!this.field6740.method5801((short)-3206)) {
            if (var3 != 241883862) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else if (!WorldMapData.method5867(this.field6742, 1870919123 * var2.field4053, var2.field4054 * 819227381, -157251391)) {
            if (var3 != 241883862) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            HashMap var4 = this.field6740.method5811((byte)91);
            List var5 = (List)var4.get(var1);
            if (var5 != null) {
               if (var3 != 241883862) {
                  throw new IllegalStateException();
               }

               if (!var5.isEmpty()) {
                  WorldMapIcon var6 = null;
                  int var7 = -1;
                  Iterator var8 = var5.iterator();

                  while (var8.hasNext()) {
                     if (var3 != 241883862) {
                        throw new IllegalStateException();
                     }

                     WorldMapIcon var9 = (WorldMapIcon)var8.next();
                     int var10 = var9.field2767.field4053 * 1870919123 - 1870919123 * var2.field4053;
                     int var11 = var9.field2767.field4054 * 819227381 - 819227381 * var2.field4054;
                     int var12 = var10 * var10 + var11 * var11;
                     if (0 == var12) {
                        return var9.field2767;
                     }

                     if (var12 >= var7) {
                        if (null != var6) {
                           continue;
                        }

                        if (var3 != 241883862) {
                           throw new IllegalStateException();
                        }
                     }

                     var6 = var9;
                     var7 = var12;
                  }

                  return var6.field2767;
               }
            }

            return null;
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "wm.cp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lgx;")
   @ObfuscatedName("fg")
   public WorldMapData method12559(int var1) {
      for (WorldMapData var3 : this.field6745.values()) {
         if (var3.method5876(-608589044) == var1) {
            return var3;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(B)Lhd;")
   @ObfuscatedName("cd")
   public WorldMapIcon method12657(byte var1) {
      try {
         if (null == this.field6746) {
            return null;
         } else {
            while (this.field6746.hasNext()) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               WorldMapIcon var2 = (WorldMapIcon)this.field6746.next();
               if (var2.vmethod486((byte)-109) != -1) {
                  return var2;
               }
            }

            return null;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.cd(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZIIII)V")
   @ObfuscatedName("ca")
   public void method12454(int var1, int var2, boolean var3, int var4, int var5, int var6, int var7) {
      if (this.field6751.method12442((short)255)) {
         method12466(this, 1576111790);
         method12472(this, (short)-219);
         if (var3) {
            int var8 = (int)Math.ceil(var6 / this.field6728);
            int var9 = (int)Math.ceil(var7 / this.field6728);
            List var10 = this.field6740
               .method5796(
                  1420704072 * this.field6738 - var8 / 2 - 1,
                  this.field6734 * 413511420 - var9 / 2 - 1,
                  1 + 1075245277 * this.field6738 + var8 / 2,
                  1 + var9 / 2 + this.field6734 * 1010414839,
                  var4,
                  var5,
                  var6,
                  var7,
                  var1,
                  var2,
                  -903075137
               );
            HashSet var11 = new HashSet();

            for (WorldMapIcon var13 : var10) {
               var11.add(var13);
               classHV var14 = new classHV(var13.vmethod486((byte)-70), var13.field2768, var13.field2767);
               Object[] var15 = new Object[]{var14, var1, var2};
               ScriptEvent var16;
               if (this.field6706.contains(var13)) {
                  var16 = classCA.method1393(17, -1451383213).method14195(var15, (byte)2).method14226((byte)105);
               } else {
                  var16 = classCA.method1393(15, -2004999147).method14195(var15, (byte)2).method14226((byte)98);
               }

               classSZ.method10771(var16, -205234782);
            }

            for (WorldMapIcon var18 : this.field6706) {
               if (!var11.contains(var18)) {
                  classHV var19 = new classHV(var18.vmethod486((byte)-1), var18.field2768, var18.field2767);
                  Object[] var20 = new Object[]{var19, var1, var2};
                  ScriptEvent var21 = classCA.method1393(16, -1648705530).method14195(var20, (byte)2).method14226((byte)2);
                  classSZ.method10771(var21, -1960893268);
               }
            }

            this.field6706 = var11;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZ)V")
   @ObfuscatedName("dc")
   final void method12477(int var1, int var2, boolean var3) {
      this.field6738 = 745269008 * var1;
      this.field6734 = 461109501 * var2;
      classDD.method2988(16711680);
      if (var3) {
         this.method12482(1429288549);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;I)V")
   @ObfuscatedName("qs")
   public static void method12490(RenderOverview var0, int var1) {
      if (var0 == null) {
         var0.method12543(var1, var1);
      }

      WorldMapData var2 = var0.method12558(var1, 2006499107);
      if (var2 != null) {
         var0.method12501(var2, -1537942060);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("co")
   public void method12452() {
      classPF.method8889((byte)71);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bk")
   public int method12579(int var1) {
      try {
         if (this.field6742 == null) {
            if (var1 <= -1920881857) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            return this.field6738 * -1664894373 + this.field6742.method5899(2024505853) * 64;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bk(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fv")
   public void method12573(int var1, int var2, int var3) {
      if (this.field6742 != null) {
         int[] var4 = WorldMapData.method5869(this.field6742, var1, var2, var3, (short)4811);
         if (null != var4) {
            this.method12563(var4[0], var4[1], (byte)-10);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;IB)Z")
   @ObfuscatedName("yl")
   public static boolean method12630(RenderOverview var0, int var1, byte var2) {
      if (var0 == null) {
         return var0.method12634(var1, var2);
      } else {
         try {
            boolean var10000;
            if (!var0.field6711.contains(var1)) {
               if (var2 == 16) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "wm.cr(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ev")
   public void method12521() {
      WorldMapRegion.field2661.method6208();
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bo")
   public int method12580(int var1) {
      try {
         if (this.field6742 == null) {
            if (var1 == -335086784) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            return this.field6734 * 1010414839 + WorldMapData.method5907(this.field6742, (short)-27995) * 64;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bo(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bh")
   public boolean method12631(int var1) {
      return !this.field6711.contains(var1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cj")
   void method12467() {
      if (classMC.field4479 != null) {
         this.field6728 = this.field6727;
      } else {
         if (this.field6728 < this.field6727) {
            this.field6728 = Math.min(this.field6727, this.field6728 + this.field6728 / 30.0F);
         }

         if (this.field6728 > this.field6727) {
            this.field6728 = Math.max(this.field6727, this.field6728 - this.field6728 / 30.0F);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;S)V")
   @ObfuscatedName("ms")
   public static void method12472(RenderOverview var0, short var1) {
      if (var0 == null) {
         var0.method12475(var1);
      } else {
         try {
            if (var0.method12483(1880693425)) {
               int var2 = var0.field6694 * -19228805 - var0.field6738 * -1664894373;
               int var3 = -1101891105 * var0.field6713 - 1010414839 * var0.field6734;
               if (0 != var2) {
                  if (var1 == 6513) {
                     return;
                  }

                  var2 /= Math.min(8, Math.abs(var2));
               }

               if (0 != var3) {
                  if (var1 == 6513) {
                     throw new IllegalStateException();
                  }

                  var3 /= Math.min(8, Math.abs(var3));
               }

               var0.method12476(var2 + -1664894373 * var0.field6738, var0.field6734 * 1010414839 + var3, true, -2007189481);
               if (-1664894373 * var0.field6738 == var0.field6694 * -19228805) {
                  if (var1 == 6513) {
                     return;
                  }

                  if (var0.field6734 * 1010414839 == var0.field6713 * -1101891105) {
                     if (var1 == 6513) {
                        throw new IllegalStateException();
                     }

                     var0.field6694 = 832458829;
                     var0.field6713 = -1910538783;
                  }
               }
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "wm.ax(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)V")
   @ObfuscatedName("bj")
   public void method12576(int var1, int var2, int var3, byte var4) {
      try {
         if (this.field6742 == null) {
            if (var4 != 0) {
               throw new IllegalStateException();
            }
         } else {
            int[] var5 = WorldMapData.method5869(this.field6742, var1, var2, var3, (short)-11691);
            if (null != var5) {
               if (var4 != 0) {
                  throw new IllegalStateException();
               }

               this.method12568(var5[0], var5[1], 584029035);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wm.bj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cx")
   void method12468() {
      if (classMC.field4479 != null) {
         this.field6728 = this.field6727;
      } else {
         if (this.field6728 < this.field6727) {
            this.field6728 = Math.min(this.field6727, this.field6728 + this.field6728 / 30.0F);
         }

         if (this.field6728 > this.field6727) {
            this.field6728 = Math.max(this.field6727, this.field6728 - this.field6728 / 30.0F);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("ee")
   float method12545(int var1) {
      if (25 == var1) {
         return 1.0F;
      } else if (37 == var1) {
         return 1.5F;
      } else if (50 == var1) {
         return 2.0F;
      } else if (-1980745443 == var1) {
         return 3.0F;
      } else {
         return 100 == var1 ? 4.0F : 8.0F;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;IIZZ)V")
   @ObfuscatedName("oc")
   public static void method12460(RenderOverview var0, int var1, int var2, boolean var3, boolean var4) {
      if (var0 == null) {
         var0.method12664(var1, var1);
      }

      long var5;
      label36: {
         var5 = classDD.method2988(16711680);
         var0.method12464(var1, var2, var4, var5);
         if (!var0.method12483(1717661734) && (var4 || var3)) {
            boolean var7 = classWK.field6689.method12230(-431955666);
            if (!var7) {
               if (var4) {
                  var0.field6698 = -935653697 * var1;
                  var0.field6709 = -1677524883 * var2;
                  var0.field6708 = -223069005 * var0.field6738;
                  var0.field6701 = -943412585 * var0.field6734;
               }

               if (var0.field6708 * -156946503 != -1) {
                  int var8 = var1 - var0.field6698 * 543121727;
                  int var9 = var2 - -656340635 * var0.field6709;
                  var0.method12476(
                     -156946503 * var0.field6708 - (int)(var8 / var0.field6727), (int)(var9 / var0.field6727) + var0.field6701 * 1131574433, false, -842205739
                  );
               }
               break label36;
            }
         }

         var0.method12482(1429288549);
      }

      if (var4) {
         var0.field6739 = -2654852032954698167L * var5;
         var0.field6737 = var1 * 1947564039;
         var0.field6736 = -427845355 * var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZ)V")
   @ObfuscatedName("dm")
   final void method12478(int var1, int var2, boolean var3) {
      this.field6738 = -871438893 * var1;
      this.field6734 = 841829575 * var2;
      classDD.method2988(16711680);
      if (var3) {
         this.method12482(1429288549);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dy")
   final void method12480() {
      this.field6709 = -250011315;
      this.field6698 = -591500047;
      this.field6701 = -655388513;
      this.field6708 = -983836297;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("di")
   final void method12481() {
      this.field6709 = 1677524883;
      this.field6698 = 935653697;
      this.field6701 = -655388513;
      this.field6708 = -983836297;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("du")
   boolean method12484() {
      return -19228805 * this.field6694 != -1 && -1 != -1101891105 * this.field6713;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dr")
   boolean method12485() {
      return -19228805 * this.field6694 != -1 && -1 != -1101891105 * this.field6713;
   }

   @ObfuscatedSignature(descriptor = "(Lwm;I)Lky;")
   @ObfuscatedName("yj")
   public static classKY method12585(RenderOverview var0, int var1) {
      if (var0 == null) {
         return var0.method12587(var1);
      } else {
         try {
            return var0.field6742 == null ? null : var0.field6742.method5871(var0.method12579(726285357), var0.method12580(-1418469369), (byte)110);
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "wm.bu(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIZ)V")
   @ObfuscatedName("db")
   public void method12488(int var1, int var2, int var3, boolean var4) {
      WorldMapData var5 = this.method12486(var1, var2, var3, (byte)-101);
      if (null == var5) {
         if (!var4) {
            return;
         }

         var5 = this.field6743;
      }

      boolean var6 = false;
      if (var5 != this.field6741 || var4) {
         this.field6741 = var5;
         this.method12501(var5, -577438891);
         var6 = true;
      }

      if (var6 || var4) {
         this.method12516(var1, var2, var3, 1281679465);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dh")
   public void method12491(int var1) {
      WorldMapData var2 = this.method12558(var1, 1709076461);
      if (var2 != null) {
         this.method12501(var2, -290387513);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIID)V")
   @ObfuscatedName("es")
   public void method12519(int var1, int var2, int var3, int var4, int var5, double var6) {
      int[] var8 = new int[4];
      classYW.method13714(var8);
      classYW.method13710(var1, var2, var3 + var1, var4 + var2);
      int var9 = this.field6751.method12443(-1540584211);
      if (var9 < 100) {
         this.method12538(var1, var2, var3, var4, var9, (short)3024);
      } else {
         if (!this.field6740.method5801((short)16936)) {
            this.field6740.method5778(this.field6750, this.field6742, client.field933, (byte)76);
            if (!this.field6740.method5801((short)-1810)) {
               return;
            }
         }

         int var11 = this.field6740.method5804((short)23377);
         double var12 = (var11 >> 16 & 0xFF) / 256.0;
         double var14 = (var11 >> 8 & 0xFF) / 256.0;
         double var16 = (var11 & 0xFF) / 256.0;
         var12 = Math.pow(var12, var6);
         var14 = Math.pow(var14, var6);
         var16 = Math.pow(var16, var6);
         int var18 = (int)(256.0 * var12);
         int var19 = (int)(256.0 * var14);
         int var20 = (int)(var16 * 256.0);
         int var10 = var20 + (var19 << 8) + -16777216 + (var18 << 16);
         classYW.method13737(var1, var2, var3, var4, var10);
         if (this.field6703 != null) {
            this.field6705 += 958191021;
            if (this.field6705 * -1964943835 % (this.field6691 * 624117895) == 0) {
               this.field6705 = 0;
               this.field6704 += -1148417837;
            }

            if (1316818779 * this.field6704 >= -775290625 * this.field6700 && !this.field6696) {
               this.field6703 = null;
            }
         }

         int var13 = (int)Math.ceil(var3 / this.field6728);
         int var23 = (int)Math.ceil(var4 / this.field6728);
         double var15 = classFH.method4722();
         this.field6740
            .method5781(
               -1664894373 * this.field6738 - var13 / 2,
               1010414839 * this.field6734 - var23 / 2,
               var13 / 2 + -1664894373 * this.field6738,
               var23 / 2 + 1010414839 * this.field6734,
               var1,
               var2,
               var1 + var3,
               var4 + var2,
               (byte)25
            );
         if (!this.field6715) {
            boolean var17 = false;
            if (var5 - 1129128375 * this.field6716 > 100) {
               this.field6716 = 1227656199 * var5;
               var17 = true;
            }

            this.field6740
               .method5784(
                  this.field6738 * -1664894373 - var13 / 2,
                  1010414839 * this.field6734 - var23 / 2,
                  var13 / 2 + this.field6738 * -1664894373,
                  var23 / 2 + this.field6734 * 1010414839,
                  var1,
                  var2,
                  var1 + var3,
                  var2 + var4,
                  this.field6714,
                  this.field6703,
                  -1964943835 * this.field6705,
                  624117895 * this.field6691,
                  var17,
                  2017467523
               );
         }

         this.method12529(var1, var2, var3, var4, var13, var23, -2004927156);
         if (MessageNode.method1494((byte)-112) && this.field6699 && null != this.field6717) {
            this.field6753.method51("Coord: " + this.field6717, classYW.field7095 + 10, 20 + classYW.field7094, 16776960, -1);
         }

         this.field6693 = 344315349 * var13;
         this.field6692 = 573698517 * var23;
         this.field6697 = -77312519 * var1;
         this.field6695 = 1723689919 * var2;
         classYW.method13718(var8);
         if (var15 != classFH.method4722()) {
            classFH.method4714(var15);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dz")
   public int method12494() {
      return this.field6742 == null ? -1 : this.field6742.method5876(-608589044);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("hx")
   public boolean method12556(int var1) {
      try {
         return this.field6751.method12442((short)255);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)V")
   @ObfuscatedName("bg")
   public void method12574(int var1, int var2, int var3, byte var4) {
      try {
         if (this.field6742 == null) {
            if (var4 != 1) {
               throw new IllegalStateException();
            }
         } else {
            int[] var5 = WorldMapData.method5869(this.field6742, var1, var2, var3, (short)21675);
            if (null != var5) {
               if (var4 != 1) {
                  throw new IllegalStateException();
               }

               this.method12563(var5[0], var5[1], (byte)-46);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wm.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cm")
   void method12473() {
      if (this.method12483(1837004952)) {
         int var1 = this.field6694 * -19228805 - this.field6738 * -1664894373;
         int var2 = -1101891105 * this.field6713 - 1010414839 * this.field6734;
         if (0 != var1) {
            var1 /= Math.min(8, Math.abs(var1));
         }

         if (0 != var2) {
            var2 /= Math.min(8, Math.abs(var2));
         }

         this.method12476(var1 + -1664894373 * this.field6738, this.field6734 * 1010414839 + var2, true, -1843570608);
         if (-1664894373 * this.field6738 == this.field6694 * -19228805 && this.field6734 * 1010414839 == this.field6713 * -1101891105) {
            this.field6694 = 832458829;
            this.field6713 = -1910538783;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;Lky;Lky;Z)V")
   @ObfuscatedName("ey")
   public void method12512(WorldMapData var1, classKY var2, classKY var3, boolean var4) {
      if (var1 != null) {
         if (this.field6742 == null || this.field6742 != var1) {
            method12505(this, var1, -1960673755);
         }

         if (!var4 && WorldMapData.method5858(this.field6742, -33612777 * var2.field4055, var2.field4053 * -1476854249, 591096687 * var2.field4054, (byte)52)) {
            this.method12516(-210579119 * var2.field4055, var2.field4053 * 671905568, var2.field4054 * 819227381, 1708373807);
         } else {
            this.method12516(568617954 * var3.field4055, var3.field4053 * 1066440694, 1764982547 * var3.field4054, 1759476596);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;II)V")
   @ObfuscatedName("tu")
   public static void method12564(RenderOverview var0, int var1, int var2) {
      if (null != var0.field6742 && WorldMapData.method5867(var0.field6742, var1, var2, 230188470)) {
         var0.field6694 = (var1 - var0.field6742.method5899(2127428772) * 1933136788) * -832458829;
         var0.field6713 = (var2 - WorldMapData.method5907(var0.field6742, (short)-9376) * 532582767) * -1899767149;
      }
   }

   public float getWorldMapZoom() {
      return this.field6728;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;I)V")
   @ObfuscatedName("sg")
   public static void method12551(RenderOverview var0, int var1) {
      if (var0 == null) {
         var0.method12567(var1, var1);
      }

      var0.field6727 = var0.method12543(var1, 120041229);
   }

   @ObfuscatedSignature(descriptor = "(Lgx;)V")
   @ObfuscatedName("dl")
   void method12503(WorldMapData var1) {
      if (null == this.field6742 || var1 != this.field6742) {
         method12505(this, var1, -987640966);
         this.method12516(-1, -1, -1, 1919193539);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("er")
   public int method12662() {
      return this.field6738 * -1664894373;
   }

   @ObfuscatedSignature(descriptor = "(Lgx;)V")
   @ObfuscatedName("dj")
   void method12507(WorldMapData var1) {
      this.field6742 = var1;
      this.field6740 = new WorldMapRenderer(this.field6754, this.field6744, this.field6748, this.field6749);
      this.field6751.method12433(this.field6742, (byte)81);
   }

   @ObfuscatedSignature(descriptor = "(Lva;Lva;Lva;Lzv;Ljava/util/HashMap;[Lyz;B)V")
   @ObfuscatedName("az")
   public void method12449(IndexDataBase var1, IndexDataBase var2, IndexDataBase var3, classZV var4, HashMap var5, IndexedSprite[] var6, byte var7) {
      try {
         this.field6754 = var6;
         this.field6750 = var1;
         this.field6748 = var2;
         this.field6749 = var3;
         this.field6753 = var4;
         this.field6744 = new HashMap();
         this.field6744.put(classHP.field2854, var5.get(field6722));
         this.field6744.put(classHP.field2855, var5.get(field6726));
         this.field6744.put(classHP.field2856, var5.get(field6724));
         this.field6751 = new classWH(var1);
         int var8 = -1013526269 * classHE.field2772.field2771;
         int[] var9 = this.field6750.method11898(var8, -1912062056);
         int var10000;
         if (var9 == null) {
            if (var7 >= 2) {
               throw new IllegalStateException();
            }

            var10000 = 0;
         } else {
            var10000 = var9.length;
         }

         int var10 = var10000;
         this.field6745 = new HashMap(var10);

         for (int var11 = 0; var11 < var10; var11++) {
            if (var7 >= 2) {
               throw new IllegalStateException();
            }

            classXY var12 = new classXY(this.field6750.method11942(var8, var9[var11], -973157393));
            WorldMapData var13 = new WorldMapData();
            var13.method5849(var12, var9[var11], 1792203160);
            this.field6745.put(var13.method5885(-1935285491), var13);
            if (WorldMapData.method5880(var13, (byte)-47)) {
               this.field6743 = var13;
            }
         }

         this.method12501(this.field6743, -187306842);
         this.field6741 = null;
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "wm.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lgx;")
   @ObfuscatedName("dn")
   public WorldMapData method12497() {
      return this.field6742;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;)V")
   @ObfuscatedName("qg")
   public static void method12474(RenderOverview var0) {
      if (var0 == null) {
         var0.method12586();
      }

      if (var0.method12483(2104218553)) {
         int var1 = var0.field6694 * -19228805 - var0.field6738 * -988263171;
         int var2 = -1049438298 * var0.field6713 - -1268509173 * var0.field6734;
         if (0 != var1) {
            var1 /= Math.min(8, Math.abs(var1));
         }

         if (0 != var2) {
            var2 /= Math.min(8, Math.abs(var2));
         }

         var0.method12476(var1 + -1268315106 * var0.field6738, var0.field6734 * 1010414839 + var2, true, -2063968209);
         if (1642911700 * var0.field6738 == var0.field6694 * 117909126 && var0.field6734 * 1204376503 == var0.field6713 * -1101891105) {
            var0.field6694 = -2029390199;
            var0.field6713 = -1910538783;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;)V")
   @ObfuscatedName("dg")
   void method12504(WorldMapData var1) {
      if (null == this.field6742 || var1 != this.field6742) {
         method12505(this, var1, -971572349);
         this.method12516(-1, -1, -1, 1320766739);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gu")
   public void method12617() {
      this.field6703 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lwm;B)I")
   @ObfuscatedName("ro")
   public static int method12547(RenderOverview var0, byte var1) {
      if (var0 == null) {
         var0.method12550(var1);
      }

      try {
         if (var0.field6727 == 1.0) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return 25;
            }
         } else if (var0.field6727 == 1.5) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return 37;
            }
         } else if (var0.field6727 == 2.0) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return 50;
            }
         } else if (3.0 == var0.field6727) {
            return 75;
         } else if (var0.field6727 == 4.0) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return 100;
            }
         } else {
            return 200;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bn(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ej")
   public void method12522() {
      WorldMapRegion.field2661.method6208();
   }

   @ObfuscatedSignature(descriptor = "(Lwm;S)Lhd;")
   @ObfuscatedName("pu")
   public static WorldMapIcon method12653(RenderOverview var0, short var1) {
      if (var0 == null) {
         return var0.method12656(var1);
      } else {
         try {
            if (!var0.field6751.method12442((short)255)) {
               return null;
            } else if (!var0.field6740.method5801((short)4599)) {
               if (var1 >= 229) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               HashMap var2 = var0.field6740.method5811((byte)87);
               var0.field6747 = new LinkedList();
               Iterator var3 = var2.values().iterator();

               while (var3.hasNext()) {
                  if (var1 >= 229) {
                     throw new IllegalStateException();
                  }

                  List var4 = (List)var3.next();
                  var0.field6747.addAll(var4);
               }

               var0.field6746 = var0.field6747.iterator();
               return var0.method12657((byte)-65);
            }
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "wm.cl(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;)V")
   @ObfuscatedName("vv")
   public static void method12639(RenderOverview var0) {
      var0.field6714.clear();
      var0.field6714.addAll(var0.field6711);
      var0.field6714.addAll(var0.field6707);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ed")
   public void method12523() {
      WorldMapRegion.field2661.method6208();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ht")
   public boolean method12626() {
      return !this.field6715;
   }

   @ObfuscatedSignature(descriptor = "(IIZIIII)V")
   @ObfuscatedName("cc")
   public void method12455(int var1, int var2, boolean var3, int var4, int var5, int var6, int var7) {
      if (this.field6751.method12442((short)255)) {
         method12466(this, 1149833048);
         method12472(this, (short)7595);
         if (var3) {
            int var8 = (int)Math.ceil(var6 / this.field6728);
            int var9 = (int)Math.ceil(var7 / this.field6728);
            List var10 = this.field6740
               .method5796(
                  2128544705 * this.field6738 - var8 / 2 - 1,
                  this.field6734 * -1010352863 - var9 / 2 - 1,
                  1 + -1664894373 * this.field6738 + var8 / 2,
                  1 + var9 / 2 + this.field6734 * 2125423134,
                  var4,
                  var5,
                  var6,
                  var7,
                  var1,
                  var2,
                  -903075137
               );
            HashSet var11 = new HashSet();

            for (WorldMapIcon var13 : var10) {
               var11.add(var13);
               classHV var14 = new classHV(var13.vmethod486((byte)-14), var13.field2768, var13.field2767);
               Object[] var15 = new Object[]{var14, var1, var2};
               ScriptEvent var16;
               if (this.field6706.contains(var13)) {
                  var16 = classCA.method1393(17, -1598863053).method14195(var15, (byte)2).method14226((byte)83);
               } else {
                  var16 = classCA.method1393(15, -1700958634).method14195(var15, (byte)2).method14226((byte)83);
               }

               classSZ.method10771(var16, -1974386929);
            }

            for (WorldMapIcon var18 : this.field6706) {
               if (!var11.contains(var18)) {
                  classHV var19 = new classHV(var18.vmethod486((byte)-111), var18.field2768, var18.field2767);
                  Object[] var20 = new Object[]{var19, var1, var2};
                  ScriptEvent var21 = classCA.method1393(16, -1662199949).method14195(var20, (byte)2).method14226((byte)103);
                  classSZ.method10771(var21, -1095613137);
               }
            }

            this.field6706 = var11;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;IIZ)V")
   @ObfuscatedName("yj")
   public static void method12479(RenderOverview var0, int var1, int var2, boolean var3) {
      if (var0 == null) {
         var0.method12552(var1);
      } else {
         var0.field6738 = -871438893 * var1;
         var0.field6734 = 841829575 * var2;
         classDD.method2988(16711680);
         if (var3) {
            var0.method12482(1429288549);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;ZB)V")
   @ObfuscatedName("jk")
   public static void method12627(RenderOverview var0, boolean var1, byte var2) {
      if (var0 == null) {
         var0.method12629(var1, var2);
      } else {
         try {
            var0.field6715 = !var1;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "wm.bz(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("bb")
   public void method12610(int var1, byte var2) {
      try {
         this.field6703 = new HashSet();
         this.field6703.add(var1);
         this.field6704 = 0;
         this.field6705 = 0;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.bf(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;IIZJ)V")
   @ObfuscatedName("ph")
   public static void method12463(RenderOverview var0, int var1, int var2, boolean var3, long var4) {
      if (var0.field6742 != null) {
         int var6 = (int)(
            var0.field6738 * -1664894373 + (var1 - -543890359 * var0.field6697 - var0.method12589((byte)-29) * var0.field6728 / 2.0F) / var0.field6728
         );
         int var7 = (int)(
            var0.field6734 * 1010414839 - (var2 - var0.field6695 * 959218751 - var0.method12592(-137371582) * var0.field6728 / 2.0F) / var0.field6728
         );
         var0.field6717 = var0.field6742
            .method5871(var6 + var0.field6742.method5899(1988907087) * 64, var7 + WorldMapData.method5907(var0.field6742, (short)-30528) * 64, (byte)59);
         if (var0.field6717 != null && var3) {
            classFA var8 = client.field932;
            if (MessageNode.method1494((byte)-88) && var8.method4563(82, 1555632321) && var8.method4563(81, 1457406092)) {
               classJS.method6447(
                  var0.field6717.field4053 * 1870919123, var0.field6717.field4054 * 819227381, var0.field6717.field4055 * -210579119, false, 240094172
               );
            } else {
               boolean var10 = true;
               if (var0.field6710) {
                  int var11 = var1 - -103516745 * var0.field6737;
                  int var12 = var2 - var0.field6736 * 2045505085;
                  if (var4 - 7559854042491313145L * var0.field6739 > 500L || var11 < -25 || var11 > 25 || var12 < -25 || var12 > 25) {
                     var10 = false;
                  }
               }

               if (var10) {
                  classJL var13 = classEF.method3863(classJS.CLICKWORLDMAP, client.field796.field1533, (byte)-49);
                  var13.field3341.method13284(classKY.method6663(var0.field6717, -1780424005), 730941790);
                  client.field796.method3374(var13, -1301097035);
                  var0.field6739 = 0L;
               }
            }
         }
      } else {
         var0.field6717 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;)V")
   @ObfuscatedName("df")
   void method12508(WorldMapData var1) {
      this.field6742 = var1;
      this.field6740 = new WorldMapRenderer(this.field6754, this.field6744, this.field6748, this.field6749);
      this.field6751.method12433(this.field6742, (byte)61);
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("eq")
   void method12531(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (null != classMC.field4479) {
         int var7 = 512 / (-629057398 * this.field6740.field2684);
         int var8 = var3 + 512;
         int var9 = var4 + 512;
         float var10 = 1.0F;
         var8 = (int)(var8 / var10);
         var9 = (int)(var9 / var10);
         int var11 = this.method12579(-1241060707) - var5 / 2 - var7;
         int var12 = this.method12580(1477386443) - var6 / 2 - var7;
         int var13 = var1 - (var7 + var11 - 927100183 * this.field6702) * -314528699 * this.field6740.field2684;
         int var14 = var2 - this.field6740.field2684 * -314528699 * (var7 - (var12 - this.field6719 * 1420019389));
         if (this.method12526(var8, var9, var13, var14, var3, var4, -89366347)) {
            if (null != this.field6752 && this.field6752.field7005 == var8 && var9 == this.field6752.field7007) {
               Arrays.fill(this.field6752.field7003, 0);
            } else {
               this.field6752 = new SpritePixels(var8, var9);
            }

            this.field6702 = (this.method12579(272460860) - var5 / 2 - var7) * 1462606503;
            this.field6719 = (this.method12580(-306351559) - var6 / 2 - var7) * 1135731861;
            this.field6735 = this.field6740.field2684 * -87197937;
            classMC.field4479
               .method14269(927100183 * this.field6702, 1420019389 * this.field6719, this.field6752, this.field6735 * -285636757 / var10, -334270256);
            this.field6720 = client.field882 * 1002652179;
            var13 = var1 - this.field6740.field2684 * -314528699 * (var7 + var11 - this.field6702 * 927100183);
            var14 = var2 - (var7 - (var12 - this.field6719 * 1420019389)) * this.field6740.field2684 * -314528699;
         }

         classYW.method13733(var1, var2, var3, var4, 0, 128);
         if (1.0F == var10) {
            this.field6752.method13573(var13, var14, 192);
         } else {
            this.field6752.method13575(var13, var14, (int)(var10 * var8), (int)(var9 * var10), 192);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;IB)V")
   @ObfuscatedName("mn")
   public static void method12596(RenderOverview var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method12599(var1, var2);
      }

      try {
         if (var1 >= 1) {
            if (var2 == 7) {
               throw new IllegalStateException();
            }

            var0.field6700 = -1724580097 * var1;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.bb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("vz")
   public int method12663() {
      return this.field6692 * 166969213;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("er")
   void method12532(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (null != classMC.field4479) {
         int var7 = 512 / (-629057398 * this.field6740.field2684);
         int var8 = var3 + 512;
         int var9 = var4 + 512;
         float var10 = 1.0F;
         var8 = (int)(var8 / var10);
         var9 = (int)(var9 / var10);
         int var11 = this.method12579(-940386358) - var5 / 2 - var7;
         int var12 = this.method12580(440684945) - var6 / 2 - var7;
         int var13 = var1 - (var7 + var11 - 927100183 * this.field6702) * -314528699 * this.field6740.field2684;
         int var14 = var2 - this.field6740.field2684 * -314528699 * (var7 - (var12 - this.field6719 * 1420019389));
         if (this.method12526(var8, var9, var13, var14, var3, var4, -89366347)) {
            if (null != this.field6752 && this.field6752.field7005 == var8 && var9 == this.field6752.field7007) {
               Arrays.fill(this.field6752.field7003, 0);
            } else {
               this.field6752 = new SpritePixels(var8, var9);
            }

            this.field6702 = (this.method12579(849909892) - var5 / 2 - var7) * 1462606503;
            this.field6719 = (this.method12580(1326449303) - var6 / 2 - var7) * 1135731861;
            this.field6735 = this.field6740.field2684 * -87197937;
            classMC.field4479
               .method14269(927100183 * this.field6702, 1420019389 * this.field6719, this.field6752, this.field6735 * -285636757 / var10, -1648676775);
            this.field6720 = client.field882 * 1002652179;
            var13 = var1 - this.field6740.field2684 * -314528699 * (var7 + var11 - this.field6702 * 927100183);
            var14 = var2 - (var7 - (var12 - this.field6719 * 1420019389)) * this.field6740.field2684 * -314528699;
         }

         classYW.method13733(var1, var2, var3, var4, 0, 128);
         if (1.0F == var10) {
            this.field6752.method13573(var13, var14, 192);
         } else {
            this.field6752.method13575(var13, var14, (int)(var10 * var8), (int)(var9 * var10), 192);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("et")
   public void method12534(int var1, int var2, int var3, int var4) {
      if (this.field6751.method12442((short)255)) {
         if (!this.field6740.method5801((short)8138)) {
            this.field6740.method5778(this.field6750, this.field6742, client.field933, (byte)10);
            if (!this.field6740.method5801((short)-1461)) {
               return;
            }
         }

         WorldMapRenderer.method5793(
            this.field6740, var1, var2, var3, var4, this.field6703, -1964943835 * this.field6705, this.field6691 * 624117895, (byte)-21
         );
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;ILky;)Lky;")
   @ObfuscatedName("cv")
   public static classKY method12647(RenderOverview var0, int var1, classKY var2) {
      if (!var0.field6751.method12442((short)255)) {
         return null;
      } else if (!var0.field6740.method5801((short)-19554)) {
         return null;
      } else if (!WorldMapData.method5867(var0.field6742, 1870919123 * var2.field4053, var2.field4054 * 819227381, -1630125944)) {
         return null;
      } else {
         HashMap var3 = var0.field6740.method5811((byte)100);
         List var4 = (List)var3.get(var1);
         if (var4 != null && !var4.isEmpty()) {
            WorldMapIcon var5 = null;
            int var6 = -1;

            for (WorldMapIcon var8 : var4) {
               int var9 = var8.field2767.field4053 * 1870919123 - 1870919123 * var2.field4053;
               int var10 = var8.field2767.field4054 * 819227381 - 819227381 * var2.field4054;
               int var11 = var9 * var9 + var10 * var10;
               if (0 == var11) {
                  return var8.field2767;
               }

               if (var11 < var6 || null == var5) {
                  var5 = var8;
                  var6 = var11;
               }
            }

            return var5.field2767;
         } else {
            return null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;)V")
   @ObfuscatedName("da")
   void method12509(WorldMapData var1) {
      this.field6742 = var1;
      this.field6740 = new WorldMapRenderer(this.field6754, this.field6744, this.field6748, this.field6749);
      this.field6751.method12433(this.field6742, (byte)-75);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("gh")
   public void method12664(int var1, int var2) {
      this.method12563(var1, var2, (byte)-81);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("eu")
   void method12540(int var1, int var2, int var3, int var4, int var5) {
      byte var6 = 20;
      int var7 = var1 + var3 / 2;
      int var8 = var2 + var4 / 2 - 18 - var6;
      classYW.method13737(var1, var2, var3, var4, -16777216);
      classYW.method13746(var7 - 152, var8, 304, 34, -65536);
      classYW.method13737(var7 - 150, 2 + var8, var5 * 3, 30, -65536);
      this.field6753.method59(classKK.field3919, var7, var6 + var8, -1, -1);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("ef")
   void method12541(int var1, int var2, int var3, int var4, int var5) {
      byte var6 = 20;
      int var7 = var1 + var3 / 2;
      int var8 = var2 + var4 / 2 - 18 - var6;
      classYW.method13737(var1, var2, var3, var4, -16777216);
      classYW.method13746(var7 - 811267074, var8, 304, 34, -65536);
      classYW.method13737(var7 - -1946988071, 2 + var8, var5 * 3, 30, -65536);
      this.field6753.method59(classKK.field3919, var7, var6 + var8, -1, -1);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("em")
   void method12542(int var1, int var2, int var3, int var4, int var5) {
      byte var6 = 20;
      int var7 = var1 + var3 / 2;
      int var8 = var2 + var4 / 2 - 18 - var6;
      classYW.method13737(var1, var2, var3, var4, -16777216);
      classYW.method13746(var7 - 152, var8, 304, 34, -65536);
      classYW.method13737(var7 - 150, 2 + var8, var5 * 3, 30, -65536);
      this.field6753.method59(classKK.field3919, var7, var6 + var8, -1, -1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   final void method12482(int var1) {
      try {
         this.field6709 = 1677524883;
         this.field6698 = 935653697;
         this.field6701 = -655388513;
         this.field6708 = -983836297;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("eb")
   float method12546(int var1) {
      if (25 == var1) {
         return 1.0F;
      } else if (-1397353618 == var1) {
         return 1.5F;
      } else if (1829824468 == var1) {
         return 2.0F;
      } else if (2120637410 == var1) {
         return 3.0F;
      } else {
         return 100 == var1 ? 4.0F : 8.0F;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ep")
   public int method12548() {
      if (this.field6727 == 1.0) {
         return 25;
      } else if (this.field6727 == 1.5) {
         return 37;
      } else if (this.field6727 == 2.0) {
         return 6184001;
      } else if (3.0 == this.field6727) {
         return 75;
      } else {
         return this.field6727 == 4.0 ? 100 : 200;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ay")
   public void method12492(int var1, int var2) {
      try {
         WorldMapData var3 = this.method12558(var1, 2137218399);
         if (var3 != null) {
            if (var2 <= -1541607628) {
               throw new IllegalStateException();
            }

            this.method12501(var3, -915721534);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wm.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fo")
   public void method12552(int var1) {
      this.field6727 = this.method12543(var1, 120041229);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("iv")
   public int method12665() {
      return this.field6713 * -1101891105;
   }

   @ObfuscatedSignature(descriptor = "(Lwm;II)V")
   @ObfuscatedName("ah")
   public static void method12614(RenderOverview var0, int var1, int var2) {
      if (var0 == null) {
         var0.method12615(var1, var1);
      } else {
         try {
            var0.field6703 = new HashSet();
            var0.field6704 = 0;
            var0.field6705 = 0;

            for (int var3 = 0; var3 < classPS.field5405 * 925594537; var3++) {
               if (var2 <= 1729729541) {
                  throw new IllegalStateException();
               }

               if (SceneTilePaint.method4817(var3, (byte)-71) == null) {
                  if (var2 <= 1729729541) {
                     throw new IllegalStateException();
                  }
               } else if (SceneTilePaint.method4817(var3, (byte)-126).field5402 * 1254355271 == var1) {
                  if (var2 <= 1729729541) {
                     return;
                  }

                  var0.field6703.add(SceneTilePaint.method4817(var3, (byte)-10).field5403 * 872689451);
               }
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "wm.bs(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)Z")
   @ObfuscatedName("av")
   boolean method12526(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      try {
         if (this.field6752 == null) {
            if (var7 != -89366347) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            if (this.field6752.field7005 == var1) {
               if (this.field6752.field7007 == var2) {
                  if (-285636757 * this.field6735 != -314528699 * this.field6740.field2684) {
                     if (var7 != -89366347) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }

                  if (1373094587 * client.field882 != 829903289 * this.field6720) {
                     return true;
                  }

                  if (var3 <= 0) {
                     if (var7 != -89366347) {
                        throw new IllegalStateException();
                     }

                     if (var4 <= 0) {
                        if (var1 + var3 >= var5) {
                           if (var7 != -89366347) {
                              throw new IllegalStateException();
                           }

                           if (var4 + var2 >= var6) {
                              return false;
                           }

                           if (var7 != -89366347) {
                              throw new IllegalStateException();
                           }
                        }

                        return true;
                     }

                     if (var7 != -89366347) {
                        throw new IllegalStateException();
                     }
                  }

                  return true;
               }

               if (var7 != -89366347) {
                  throw new IllegalStateException();
               }
            }

            return true;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "wm.av(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;I)V")
   @ObfuscatedName("fk")
   public static void method12597(RenderOverview var0, int var1) {
      if (var0 == null) {
         var0.method12644(var1, var1, var1, var1, var1, var1);
      }

      if (var1 >= 1) {
         var0.field6700 = -1552585640 * var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fj")
   public void method12555() {
      classWH.method12439(this.field6751, 1474497483);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("fd")
   public boolean method12557() {
      return this.field6751.method12442((short)255);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("fh")
   public void method12565(int var1, int var2) {
      if (null != this.field6742 && WorldMapData.method5867(this.field6742, var1, var2, 139677998)) {
         this.field6694 = (var1 - this.field6742.method5899(1994945837) * 64) * -832458829;
         this.field6713 = (var2 - WorldMapData.method5907(this.field6742, (short)-21362) * 64) * 1910538783;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lgx;")
   @ObfuscatedName("fi")
   public WorldMapData method12560(int var1) {
      for (WorldMapData var3 : this.field6745.values()) {
         if (var3.method5876(-608589044) == var1) {
            return var3;
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;Lva;Lva;Lva;Lzv;Ljava/util/HashMap;[Lyz;)V")
   @ObfuscatedName("wq")
   public static void method12450(
      RenderOverview var0, IndexDataBase var1, IndexDataBase var2, IndexDataBase var3, classZV var4, HashMap var5, IndexedSprite[] var6
   ) {
      if (var0 == null) {
         var0.method12618();
      } else {
         var0.field6754 = var6;
         var0.field6750 = var1;
         var0.field6748 = var2;
         var0.field6749 = var3;
         var0.field6753 = var4;
         var0.field6744 = new HashMap();
         var0.field6744.put(classHP.field2854, var5.get(field6722));
         var0.field6744.put(classHP.field2855, var5.get(field6726));
         var0.field6744.put(classHP.field2856, var5.get(field6724));
         var0.field6751 = new classWH(var1);
         int var7 = -1013526269 * classHE.field2772.field2771;
         int[] var8 = var0.field6750.method11898(var7, -2046822024);
         int var9 = var8 == null ? 0 : var8.length;
         var0.field6745 = new HashMap(var9);

         for (int var10 = 0; var10 < var9; var10++) {
            classXY var11 = new classXY(var0.field6750.method11942(var7, var8[var10], -2092757493));
            WorldMapData var12 = new WorldMapData();
            var12.method5849(var11, var8[var10], 748800632);
            var0.field6745.put(var12.method5885(-2145988097), var12);
            if (WorldMapData.method5880(var12, (byte)-14)) {
               var0.field6743 = var12;
            }
         }

         var0.method12501(var0.field6743, -833162955);
         var0.field6741 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;I)Lgx;")
   @ObfuscatedName("ra")
   public static WorldMapData method12498(RenderOverview var0, int var1) {
      if (var0 == null) {
         return var0.method12499(var1);
      } else {
         try {
            return var0.field6742;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "wm.ad(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ec")
   public int method12549() {
      if (this.field6727 == 1.0) {
         return 25;
      } else if (this.field6727 == 1.5) {
         return 37;
      } else if (this.field6727 == 2.0) {
         return 50;
      } else if (3.0 == this.field6727) {
         return 75;
      } else {
         return this.field6727 == 4.0 ? 100 : 200;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mb")
   public int method12666() {
      return this.field6693 * -811287683;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("fz")
   public void method12566(int var1, int var2) {
      if (null != this.field6742 && WorldMapData.method5867(this.field6742, var1, var2, 1150890400)) {
         this.field6694 = (var1 - this.field6742.method5899(2019125225) * 64) * -832458829;
         this.field6713 = (var2 - WorldMapData.method5907(this.field6742, (short)-30146) * 64) * 17617710;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ew")
   public void method12535(int var1, int var2, int var3, int var4) {
      if (this.field6751.method12442((short)255)) {
         if (!this.field6740.method5801((short)-285)) {
            this.field6740.method5778(this.field6750, this.field6742, client.field933, (byte)4);
            if (!this.field6740.method5801((short)-6084)) {
               return;
            }
         }

         WorldMapRenderer.method5793(
            this.field6740, var1, var2, var3, var4, this.field6703, -1964943835 * this.field6705, this.field6691 * 624117895, (byte)-119
         );
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("gk")
   public int method12593() {
      return -1523454929 * this.field6692;
   }

   @ObfuscatedSignature(descriptor = "(Lwm;Lgx;Lky;Lky;ZB)V")
   @ObfuscatedName("oo")
   public static void method12513(RenderOverview var0, WorldMapData var1, classKY var2, classKY var3, boolean var4, byte var5) {
      if (var0 == null) {
         var0.method12514(var1, var2, var2, var4, var5);
      }

      try {
         if (var1 != null) {
            if (var0.field6742 == null || var0.field6742 != var1) {
               method12505(var0, var1, -1904997508);
            }

            if (!var4) {
               if (var5 <= 0) {
                  throw new IllegalStateException();
               }

               if (WorldMapData.method5858(var0.field6742, -210579119 * var2.field4055, var2.field4053 * 1870919123, 819227381 * var2.field4054, (byte)111)) {
                  var0.method12516(-210579119 * var2.field4055, var2.field4053 * 1870919123, var2.field4054 * 819227381, 1338617160);
                  return;
               }

               if (var5 <= 0) {
                  throw new IllegalStateException();
               }
            }

            var0.method12516(-210579119 * var3.field4055, var3.field4053 * 1870919123, 819227381 * var3.field4054, 1849617190);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wm.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("fp")
   public void method12569(int var1, int var2) {
      if (null != this.field6742) {
         this.method12476(
            var1 - this.field6742.method5899(2002615988) * -392822479,
            var2 - WorldMapData.method5907(this.field6742, (short)-15678) * -799290653,
            true,
            -1875882451
         );
         this.field6694 = 1806408931;
         this.field6713 = -1910538783;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("fs")
   public void method12570(int var1, int var2) {
      if (null != this.field6742) {
         this.method12476(
            var1 - this.field6742.method5899(2038756018) * 64, var2 - WorldMapData.method5907(this.field6742, (short)-7187) * -2043327439, true, -1436741266
         );
         this.field6694 = 832458829;
         this.field6713 = 833337649;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fn")
   public void method12577(int var1, int var2, int var3) {
      if (this.field6742 != null) {
         int[] var4 = WorldMapData.method5869(this.field6742, var1, var2, var3, (short)15509);
         if (null != var4) {
            this.method12568(var4[0], var4[1], -175165264);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)Z")
   @ObfuscatedName("eg")
   boolean method12527(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (this.field6752 == null) {
         return true;
      } else if (this.field6752.field7005 == var1 && this.field6752.field7007 == var2) {
         if (-285636757 * this.field6735 != -314528699 * this.field6740.field2684) {
            return true;
         } else if (1373094587 * client.field882 != 829903289 * this.field6720) {
            return true;
         } else {
            return var3 <= 0 && var4 <= 0 ? var1 + var3 < var5 || var4 + var2 < var6 : true;
         }
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("eo")
   public void method12536(int var1, int var2, int var3, int var4) {
      if (this.field6751.method12442((short)255)) {
         if (!this.field6740.method5801((short)11113)) {
            this.field6740.method5778(this.field6750, this.field6742, client.field933, (byte)120);
            if (!this.field6740.method5801((short)-12535)) {
               return;
            }
         }

         WorldMapRenderer.method5793(this.field6740, var1, var2, var3, var4, this.field6703, -1964943835 * this.field6705, this.field6691 * -235260875, (byte)7);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ff")
   public int method12581() {
      return this.field6742 == null ? -1 : this.field6734 * 1010414839 + WorldMapData.method5907(this.field6742, (short)-20876) * 862006016;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;Lgx;)V")
   @ObfuscatedName("ai")
   public static void method12510(RenderOverview var0, WorldMapData var1) {
      if (var0 == null) {
         var0.method12557();
      }

      var0.field6742 = var1;
      var0.field6740 = new WorldMapRenderer(var0.field6754, var0.field6744, var0.field6748, var0.field6749);
      var0.field6751.method12433(var0.field6742, (byte)108);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("fe")
   public int method12582() {
      return this.field6742 == null ? -1 : this.field6734 * 1010414839 + WorldMapData.method5907(this.field6742, (short)-15363) * 64;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("fc")
   public int method12583() {
      return this.field6742 == null ? -1 : this.field6734 * 1010414839 + WorldMapData.method5907(this.field6742, (short)57) * 64;
   }

   @ObfuscatedSignature(descriptor = "()Lky;")
   @ObfuscatedName("fm")
   public classKY method12586() {
      return this.field6742 == null ? null : this.field6742.method5871(this.method12579(1557510630), this.method12580(1827058575), (byte)114);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fr")
   public void method12578(int var1, int var2, int var3) {
      if (this.field6742 != null) {
         int[] var4 = WorldMapData.method5869(this.field6742, var1, var2, var3, (short)6822);
         if (null != var4) {
            this.method12568(var4[0], var4[1], -785645989);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("fw")
   public int method12590() {
      return this.field6693 * -811287683;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("gq")
   public int method12591() {
      return this.field6693 * -811287683;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("gv")
   public int method12594() {
      return 1250862754 * this.field6692;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("fb")
   public void method12567(int var1, int var2) {
      if (null != this.field6742 && WorldMapData.method5867(this.field6742, var1, var2, 1216744707)) {
         this.field6694 = (var1 - this.field6742.method5899(1979114778) * 1702755890) * -87770755;
         this.field6713 = (var2 - WorldMapData.method5907(this.field6742, (short)-2801) * -1918236354) * 1910538783;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("go")
   public void method12598(int var1) {
      if (var1 >= 1) {
         this.field6700 = 759300585 * var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lhd;")
   @ObfuscatedName("hc")
   public WorldMapIcon method12654() {
      if (!this.field6751.method12442((short)255)) {
         return null;
      } else if (!this.field6740.method5801((short)8494)) {
         return null;
      } else {
         HashMap var1 = this.field6740.method5811((byte)103);
         this.field6747 = new LinkedList();

         for (List var3 : var1.values()) {
            this.field6747.addAll(var3);
         }

         this.field6746 = this.field6747.iterator();
         return this.method12657((byte)-56);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ga")
   public void method12601() {
      this.field6700 = -1305720828;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gc")
   public void method12603(int var1) {
      if (var1 >= 1) {
         this.field6691 = var1 * 249542509;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;I)Lgx;")
   @ObfuscatedName("jf")
   public static WorldMapData method12561(RenderOverview var0, int var1) {
      if (var0 == null) {
         return var0.method12562(var1);
      } else {
         for (WorldMapData var3 : var0.field6745.values()) {
            if (var3.method5876(-608589044) == var1) {
               return var3;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lgx;")
   @ObfuscatedName("nv")
   public WorldMapData method12667() {
      return this.field6742;
   }

   @ObfuscatedSignature(descriptor = "(Lwm;IIZIIIIB)V")
   @ObfuscatedName("sn")
   public static void method12456(RenderOverview var0, int var1, int var2, boolean var3, int var4, int var5, int var6, int var7, byte var8) {
      if (var0 == null) {
         var0.method12457(var1, var1, var3, var1, var1, var1, var1, var8);
      } else {
         try {
            if (!var0.field6751.method12442((short)255)) {
               if (var8 <= 1) {
                  throw new IllegalStateException();
               }
            } else {
               method12466(var0, 1944942167);
               method12472(var0, (short)3735);
               if (!var3) {
                  if (var8 <= 1) {
                     throw new IllegalStateException();
                  }
               } else {
                  int var9 = (int)Math.ceil(var6 / var0.field6728);
                  int var10 = (int)Math.ceil(var7 / var0.field6728);
                  List var11 = var0.field6740
                     .method5796(
                        -1664894373 * var0.field6738 - var9 / 2 - 1,
                        var0.field6734 * 1010414839 - var10 / 2 - 1,
                        1 + -1664894373 * var0.field6738 + var9 / 2,
                        1 + var10 / 2 + var0.field6734 * 1010414839,
                        var4,
                        var5,
                        var6,
                        var7,
                        var1,
                        var2,
                        -903075137
                     );
                  HashSet var12 = new HashSet();
                  Iterator var13 = var11.iterator();

                  while (var13.hasNext()) {
                     if (var8 <= 1) {
                        throw new IllegalStateException();
                     }

                     WorldMapIcon var14 = (WorldMapIcon)var13.next();
                     var12.add(var14);
                     classHV var15 = new classHV(var14.vmethod486((byte)-95), var14.field2768, var14.field2767);
                     Object[] var16 = new Object[]{var15, var1, var2};
                     ScriptEvent var17;
                     if (var0.field6706.contains(var14)) {
                        var17 = classCA.method1393(17, -1800682780).method14195(var16, (byte)2).method14226((byte)36);
                     } else {
                        var17 = classCA.method1393(15, -1850821134).method14195(var16, (byte)2).method14226((byte)97);
                     }

                     classSZ.method10771(var17, -1233666950);
                  }

                  var13 = var0.field6706.iterator();

                  while (var13.hasNext()) {
                     if (var8 <= 1) {
                        throw new IllegalStateException();
                     }

                     WorldMapIcon var20 = (WorldMapIcon)var13.next();
                     if (!var12.contains(var20)) {
                        if (var8 <= 1) {
                           throw new IllegalStateException();
                        }

                        classHV var21 = new classHV(var20.vmethod486((byte)-110), var20.field2768, var20.field2767);
                        Object[] var22 = new Object[]{var21, var1, var2};
                        ScriptEvent var23 = classCA.method1393(16, -1403020284).method14195(var22, (byte)2).method14226((byte)92);
                        classSZ.method10771(var23, -562177295);
                     }
                  }

                  var0.field6706 = var12;
               }
            }
         } catch (RuntimeException var18) {
            throw classEG.method3899(var18, "wm.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gw")
   public void method12604(int var1) {
      if (var1 >= 1) {
         this.field6691 = var1 * -71107273;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gg")
   public void method12607() {
      this.field6691 = 739603646;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("hd")
   public void method12609(boolean var1) {
      this.field6696 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ge")
   public void method12611(int var1) {
      this.field6703 = new HashSet();
      this.field6703.add(var1);
      this.field6704 = 0;
      this.field6705 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Lwm;IZB)V")
   @ObfuscatedName("bs")
   public static void method12622(RenderOverview var0, int var1, boolean var2, byte var3) {
      if (var0 == null) {
         var0.method12624(var1, var2, var3);
      } else {
         try {
            if (!var2) {
               if (var3 != 51) {
                  throw new IllegalStateException();
               }

               var0.field6712.add(var1);
            } else {
               var0.field6712.remove(var1);
            }

            for (int var4 = 0; var4 < 925594537 * classPS.field5405; var4++) {
               if (var3 != 51) {
                  return;
               }

               if (SceneTilePaint.method4817(var4, (byte)-102) == null) {
                  if (var3 != 51) {
                     throw new IllegalStateException();
                  }
               } else if (SceneTilePaint.method4817(var4, (byte)-49).field5402 * 1254355271 == var1) {
                  if (var3 != 51) {
                     throw new IllegalStateException();
                  }

                  int var5 = SceneTilePaint.method4817(var4, (byte)-31).field5403 * 872689451;
                  if (!var2) {
                     if (var3 != 51) {
                        return;
                     }

                     var0.field6707.add(var5);
                  } else {
                     var0.field6707.remove(var5);
                  }
               }
            }

            var0.method12638((byte)49);
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "wm.bv(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZZ)V")
   @ObfuscatedName("ch")
   public void method12461(int var1, int var2, boolean var3, boolean var4) {
      long var5;
      label40: {
         var5 = classDD.method2988(16711680);
         this.method12464(var1, var2, var4, var5);
         if (!this.method12483(2110820558) && (var4 || var3)) {
            boolean var7 = classWK.field6689.method12230(-431955666);
            if (!var7) {
               if (var4) {
                  this.field6698 = -935653697 * var1;
                  this.field6709 = -1677524883 * var2;
                  this.field6708 = -223069005 * this.field6738;
                  this.field6701 = -943412585 * this.field6734;
               }

               if (this.field6708 * -156946503 != -1) {
                  int var8 = var1 - this.field6698 * 543121727;
                  int var9 = var2 - -656340635 * this.field6709;
                  this.method12476(
                     -156946503 * this.field6708 - (int)(var8 / this.field6727), (int)(var9 / this.field6727) + this.field6701 * 1131574433, false, -988276017
                  );
               }
               break label40;
            }
         }

         this.method12482(1429288549);
      }

      if (var4) {
         this.field6739 = -2654852032954698167L * var5;
         this.field6737 = var1 * 1947564039;
         this.field6736 = -427845355 * var2;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gf")
   public void method12618() {
      this.field6703 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gh")
   public void method12619() {
      this.field6703 = null;
   }

   @ObfuscatedSignature(descriptor = "(IIZ)V")
   @ObfuscatedName("gf")
   public void method12668(int var1, int var2, boolean var3) {
      this.method12476(var1, var2, var3, -1392100145);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("hg")
   public boolean method12636(int var1) {
      return !this.field6712.contains(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;IIIZ)V")
   @ObfuscatedName("lr")
   public static void method12489(RenderOverview var0, int var1, int var2, int var3, boolean var4) {
      if (var0 == null) {
         var0.method12645(var1, var1, var1, var1, var1, var1, var1);
      } else {
         WorldMapData var5 = var0.method12486(var1, var2, var3, (byte)83);
         if (null == var5) {
            if (!var4) {
               return;
            }

            var5 = var0.field6743;
         }

         boolean var6 = false;
         if (var5 != var0.field6741 || var4) {
            var0.field6741 = var5;
            var0.method12501(var5, -989827837);
            var6 = true;
         }

         if (var6 || var4) {
            var0.method12516(var1, var2, var3, 1713609406);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("hz")
   public void method12623(int var1, boolean var2) {
      if (!var2) {
         this.field6712.add(var1);
      } else {
         this.field6712.remove(var1);
      }

      for (int var3 = 0; var3 < 925594537 * classPS.field5405; var3++) {
         if (SceneTilePaint.method4817(var3, (byte)11) != null && SceneTilePaint.method4817(var3, (byte)-98).field5402 * 1254355271 == var1) {
            int var4 = SceneTilePaint.method4817(var3, (byte)-8).field5403 * 872689451;
            if (!var2) {
               this.field6707.add(var4);
            } else {
               this.field6707.remove(var4);
            }
         }
      }

      this.method12638((byte)97);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gy")
   public void method12628(boolean var1) {
      this.field6715 = !var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("hk")
   public boolean method12632(int var1) {
      return !this.field6711.contains(var1);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("go")
   public int method12669() {
      return this.field6697 * -543890359;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("hl")
   public boolean method12633(int var1) {
      return !this.field6711.contains(var1);
   }

   @ObfuscatedSignature(descriptor = "(IIZJ)V")
   @ObfuscatedName("ag")
   void method12464(int var1, int var2, boolean var3, long var4) {
      try {
         if (this.field6742 != null) {
            int var6 = (int)(
               this.field6738 * -1664894373 + (var1 - -543890359 * this.field6697 - this.method12589((byte)-20) * this.field6728 / 2.0F) / this.field6728
            );
            int var7 = (int)(
               this.field6734 * 1010414839 - (var2 - this.field6695 * 959218751 - this.method12592(2000494820) * this.field6728 / 2.0F) / this.field6728
            );
            this.field6717 = this.field6742
               .method5871(var6 + this.field6742.method5899(1979976170) * 64, var7 + WorldMapData.method5907(this.field6742, (short)-7509) * 64, (byte)96);
            if (this.field6717 != null && var3) {
               classFA var8 = client.field932;
               if (MessageNode.method1494((byte)-65) && var8.method4563(82, 1135407994) && var8.method4563(81, 2134325806)) {
                  classJS.method6447(
                     this.field6717.field4053 * 1870919123, this.field6717.field4054 * 819227381, this.field6717.field4055 * -210579119, false, -1973590340
                  );
               } else {
                  boolean var10 = true;
                  if (this.field6710) {
                     int var11 = var1 - -103516745 * this.field6737;
                     int var12 = var2 - this.field6736 * 2045505085;
                     if (var4 - 7559854042491313145L * this.field6739 > 500L || var11 < -25 || var11 > 25 || var12 < -25 || var12 > 25) {
                        var10 = false;
                     }
                  }

                  if (var10) {
                     classJL var14 = classEF.method3863(classJS.CLICKWORLDMAP, client.field796.field1533, (byte)76);
                     var14.field3341.method13284(classKY.method6663(this.field6717, -1777655593), 730941790);
                     client.field796.method3374(var14, -1301097035);
                     this.field6739 = 0L;
                  }
               }
            }
         } else {
            this.field6717 = null;
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "wm.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("hp")
   public boolean method12637(int var1) {
      return !this.field6712.contains(var1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("hr")
   void method12640() {
      this.field6714.clear();
      this.field6714.addAll(this.field6711);
      this.field6714.addAll(this.field6707);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ho")
   void method12641() {
      this.field6714.clear();
      this.field6714.addAll(this.field6711);
      this.field6714.addAll(this.field6707);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("hu")
   void method12642() {
      this.field6714.clear();
      this.field6714.addAll(this.field6711);
      this.field6714.addAll(this.field6707);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("hj")
   void method12643() {
      this.field6714.clear();
      this.field6714.addAll(this.field6711);
      this.field6714.addAll(this.field6707);
   }

   @ObfuscatedSignature(descriptor = "()Lhd;")
   @ObfuscatedName("hn")
   public WorldMapIcon method12658() {
      if (null == this.field6746) {
         return null;
      } else {
         while (this.field6746.hasNext()) {
            WorldMapIcon var1 = (WorldMapIcon)this.field6746.next();
            if (var1.vmethod486((byte)-112) != -1) {
               return var1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;II)V")
   @ObfuscatedName("lu")
   public static void method12553(RenderOverview var0, int var1, int var2) {
      if (var0 == null) {
         var0.method12572(var1, var1);
      }

      try {
         var0.field6727 = var0.method12543(var1, 120041229);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.bx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)Z")
   @ObfuscatedName("en")
   boolean method12528(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (this.field6752 == null) {
         return true;
      } else if (this.field6752.field7005 == var1 && this.field6752.field7007 == var2) {
         if (-709750092 * this.field6735 != -314528699 * this.field6740.field2684) {
            return true;
         } else if (1373094587 * client.field882 != -796052709 * this.field6720) {
            return true;
         } else {
            return var3 <= 0 && var4 <= 0 ? var1 + var3 < var5 || var4 + var2 < var6 : true;
         }
      } else {
         return true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;III)V")
   @ObfuscatedName("cy")
   public static void method12517(RenderOverview var0, int var1, int var2, int var3) {
      if (var0.field6742 != null) {
         int[] var4 = WorldMapData.method5869(var0.field6742, var1, var2, var3, (short)22253);
         if (null == var4) {
            var4 = WorldMapData.method5869(
               var0.field6742,
               var0.field6742.method5920((byte)-15),
               WorldMapData.method5918(var0.field6742, -741799132),
               WorldMapData.method5924(var0.field6742, -2126289798),
               (short)4475
            );
         }

         var0.method12476(
            var4[0] - var0.field6742.method5899(2059441842) * 64, var4[1] - WorldMapData.method5907(var0.field6742, (short)-11099) * 64, true, -1697518721
         );
         var0.field6694 = 832458829;
         var0.field6713 = -1910538783;
         var0.field6728 = var0.method12543(WorldMapData.method5897(var0.field6742, (byte)39), 120041229);
         var0.field6727 = var0.field6728;
         var0.field6747 = null;
         var0.field6746 = null;
         var0.field6740.method5780((byte)-45);
      }
   }

   @ObfuscatedSignature(descriptor = "(IILky;Lky;)V")
   @ObfuscatedName("hy")
   public void method12648(int var1, int var2, classKY var3, classKY var4) {
      classHV var5 = new classHV(var2, var3, var4);
      Object[] var6 = new Object[]{var5};
      ScriptEvent var7 = null;
      switch (var1) {
         case 1008:
            var7 = classCA.method1393(10, -1962264714).method14195(var6, (byte)2).method14226((byte)21);
            break;
         case 1009:
            var7 = classCA.method1393(11, -1050774769).method14195(var6, (byte)2).method14226((byte)25);
            break;
         case 1010:
            var7 = classCA.method1393(12, -1055873954).method14195(var6, (byte)2).method14226((byte)118);
            break;
         case 1011:
            var7 = classCA.method1393(13, -1286332539).method14195(var6, (byte)2).method14226((byte)63);
            break;
         case 1012:
            var7 = classCA.method1393(14, -1109466741).method14195(var6, (byte)2).method14226((byte)82);
      }

      if (var7 != null) {
         classSZ.method10771(var7, -617721645);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("af")
   public void method12453(byte var1) {
      try {
         classPF.method8889((byte)71);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ci")
   void method12469() {
      if (classMC.field4479 != null) {
         this.field6728 = this.field6727;
      } else {
         if (this.field6728 < this.field6727) {
            this.field6728 = Math.min(this.field6727, this.field6728 + this.field6728 / 30.0F);
         }

         if (this.field6728 > this.field6727) {
            this.field6728 = Math.max(this.field6727, this.field6728 - this.field6728 / 30.0F);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZZ)V")
   @ObfuscatedName("cn")
   public void method12462(int var1, int var2, boolean var3, boolean var4) {
      long var5;
      label44: {
         var5 = classDD.method2988(16711680);
         this.method12464(var1, var2, var4, var5);
         if (!this.method12483(1978668659) && (var4 || var3)) {
            boolean var7 = classWK.field6689.method12230(-431955666);
            if (!var7) {
               if (var4) {
                  this.field6698 = -935653697 * var1;
                  this.field6709 = -1677524883 * var2;
                  this.field6708 = -223069005 * this.field6738;
                  this.field6701 = -943412585 * this.field6734;
               }

               if (this.field6708 * -156946503 != -1) {
                  int var8 = var1 - this.field6698 * 543121727;
                  int var9 = var2 - -656340635 * this.field6709;
                  this.method12476(
                     -156946503 * this.field6708 - (int)(var8 / this.field6727), (int)(var9 / this.field6727) + this.field6701 * 1131574433, false, -1795595335
                  );
               }
               break label44;
            }
         }

         this.method12482(1429288549);
      }

      if (var4) {
         this.field6739 = -2654852032954698167L * var5;
         this.field6737 = var1 * 1947564039;
         this.field6736 = -427845355 * var2;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lhd;")
   @ObfuscatedName("hb")
   public WorldMapIcon method12655() {
      if (!this.field6751.method12442((short)255)) {
         return null;
      } else if (!this.field6740.method5801((short)6620)) {
         return null;
      } else {
         HashMap var1 = this.field6740.method5811((byte)89);
         this.field6747 = new LinkedList();

         for (List var3 : var1.values()) {
            this.field6747.addAll(var3);
         }

         this.field6746 = this.field6747.iterator();
         return this.method12657((byte)-113);
      }
   }

   @ObfuscatedSignature(descriptor = "(IILky;Lky;)V")
   @ObfuscatedName("hh")
   public void method12649(int var1, int var2, classKY var3, classKY var4) {
      classHV var5 = new classHV(var2, var3, var4);
      Object[] var6 = new Object[]{var5};
      ScriptEvent var7 = null;
      switch (var1) {
         case 1008:
            var7 = classCA.method1393(10, -1158847301).method14195(var6, (byte)2).method14226((byte)89);
            break;
         case 1009:
            var7 = classCA.method1393(11, -1707388737).method14195(var6, (byte)2).method14226((byte)28);
            break;
         case 1010:
            var7 = classCA.method1393(12, -1204875656).method14195(var6, (byte)2).method14226((byte)48);
            break;
         case 1011:
            var7 = classCA.method1393(13, -2022456984).method14195(var6, (byte)2).method14226((byte)56);
            break;
         case 1012:
            var7 = classCA.method1393(14, -1052099835).method14195(var6, (byte)2).method14226((byte)2);
      }

      if (var7 != null) {
         classSZ.method10771(var7, -736800227);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lhd;")
   @ObfuscatedName("hs")
   public WorldMapIcon method12659() {
      if (null == this.field6746) {
         return null;
      } else {
         while (this.field6746.hasNext()) {
            WorldMapIcon var1 = (WorldMapIcon)this.field6746.next();
            if (var1.vmethod486((byte)1) != -1) {
               return var1;
            }
         }

         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;)I")
   @ObfuscatedName("cd")
   public static int method12584(RenderOverview var0) {
      return var0.field6742 == null ? -1 : var0.field6734 * 1010414839 + WorldMapData.method5907(var0.field6742, (short)-30182) * 64;
   }

   @ObfuscatedSignature(descriptor = "(IIZIIIIB)V")
   @ObfuscatedName("ae")
   public void method12457(int var1, int var2, boolean var3, int var4, int var5, int var6, int var7, byte var8) {
      try {
         if (!this.field6751.method12442((short)255)) {
            if (var8 <= 1) {
               throw new IllegalStateException();
            }
         } else {
            method12466(this, 1944942167);
            method12472(this, (short)3735);
            if (!var3) {
               if (var8 <= 1) {
                  throw new IllegalStateException();
               }
            } else {
               int var9 = (int)Math.ceil(var6 / this.field6728);
               int var10 = (int)Math.ceil(var7 / this.field6728);
               List var11 = this.field6740
                  .method5796(
                     -1664894373 * this.field6692 - var9 / 2 - 1,
                     this.field6736 * 1010414839 - var10 / 2 - 1,
                     1 + -1664894373 * this.field6700 + var9 / 2,
                     1 + var10 / 2 + this.field6694 * 1010414839,
                     var4,
                     var5,
                     var6,
                     var7,
                     var1,
                     var2,
                     -903075137
                  );
               HashSet var12 = new HashSet();
               Iterator var13 = var11.iterator();

               while (var13.hasNext()) {
                  if (var8 <= 1) {
                     throw new IllegalStateException();
                  }

                  WorldMapIcon var14 = (WorldMapIcon)var13.next();
                  var12.add(var14);
                  classHV var15 = new classHV(var14.vmethod486((byte)-95), var14.field2767, var14.field2768);
                  Object[] var16 = new Object[]{var15, var1, var2};
                  ScriptEvent var17;
                  if (this.field6714.contains(var14)) {
                     var17 = classCA.method1393(17, -1800682780).method14195(var16, (byte)2).method14226((byte)36);
                  } else {
                     var17 = classCA.method1393(15, -1850821134).method14195(var16, (byte)2).method14226((byte)97);
                  }

                  classSZ.method10771(var17, -1233666950);
               }

               var13 = this.field6706.iterator();

               while (var13.hasNext()) {
                  if (var8 <= 1) {
                     throw new IllegalStateException();
                  }

                  WorldMapIcon var20 = (WorldMapIcon)var13.next();
                  if (!var12.contains(var20)) {
                     if (var8 <= 1) {
                        throw new IllegalStateException();
                     }

                     classHV var21 = new classHV(var20.vmethod486((byte)-110), var20.field2767, var20.field2767);
                     Object[] var22 = new Object[]{var21, var1, var2};
                     ScriptEvent var23 = classCA.method1393(16, -1403020284).method14195(var22, (byte)2).method14226((byte)92);
                     classSZ.method10771(var23, -562177295);
                  }
               }

               this.field6714 = var12;
            }
         }
      } catch (RuntimeException var18) {
         throw classEG.method3899(var18, "wm.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)V")
   @ObfuscatedName("ax")
   void method12475(short var1) {
      try {
         if (this.method12483(1880693425)) {
            int var2 = this.field6693 * -19228805 - this.field6701 * -1664894373;
            int var3 = -1101891105 * this.field6713 - 1010414839 * this.field6698;
            if (0 != var2) {
               if (var1 == 6513) {
                  return;
               }

               var2 /= Math.min(8, Math.abs(var2));
            }

            if (0 != var3) {
               if (var1 == 6513) {
                  throw new IllegalStateException();
               }

               var3 /= Math.min(8, Math.abs(var3));
            }

            this.method12476(var2 + -1664894373 * this.field6693, this.field6720 * 1010414839 + var3, true, -2007189481);
            if (-1664894373 * this.field6692 == this.field6691 * -19228805) {
               if (var1 == 6513) {
                  return;
               }

               if (this.field6702 * 1010414839 == this.field6700 * -1101891105) {
                  if (var1 == 6513) {
                     throw new IllegalStateException();
                  }

                  this.field6692 = 832458829;
                  this.field6713 = -1910538783;
               }
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wm.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aq")
   public int method12495(int var1) {
      try {
         return this.field6742 == null ? -1 : this.field6741.method5900(-608589044);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.aq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;I)V")
   @ObfuscatedName("au")
   void method12511(WorldMapData var1, int var2) {
      try {
         this.field6743 = var1;
         this.field6740 = new WorldMapRenderer(this.field6754, this.field6744, this.field6750, this.field6748);
         this.field6751.method12433(this.field6742, (byte)-74);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;Lky;Lky;ZB)V")
   @ObfuscatedName("ai")
   public void method12514(WorldMapData var1, classKY var2, classKY var3, boolean var4, byte var5) {
      try {
         if (var1 != null) {
            if (this.field6743 == null || this.field6743 != var1) {
               method12505(this, var1, -1904997508);
            }

            if (!var4) {
               if (var5 <= 0) {
                  throw new IllegalStateException();
               }

               if (WorldMapData.method5858(this.field6743, -210579119 * var2.field4053, var2.field4054 * 1870919123, 819227381 * var2.field4053, (byte)111)) {
                  this.method12516(-210579119 * var2.field4053, var2.field4053 * 1870919123, var2.field4054 * 819227381, 1338617160);
                  return;
               }

               if (var5 <= 0) {
                  throw new IllegalStateException();
               }
            }

            this.method12516(-210579119 * var3.field4055, var3.field4054 * 1870919123, 819227381 * var3.field4054, 1849617190);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wm.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lgu;")
   @ObfuscatedName("wg")
   public WorldMapRenderer method12670() {
      return this.field6740;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;II)V")
   @ObfuscatedName("ad")
   public static void method12571(RenderOverview var0, int var1, int var2) {
      if (null != var0.field6742) {
         var0.method12476(
            var1 - var0.field6742.method5899(2143861292) * 64, var2 - WorldMapData.method5907(var0.field6742, (short)-16394) * -273327495, true, -2142058492
         );
         var0.field6694 = 832458829;
         var0.field6713 = -140798908;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("an")
   public void method12537(int var1, int var2, int var3, int var4, int var5) {
      try {
         if (!this.field6751.method12442((short)255)) {
            if (var5 == 1689794791) {
               throw new IllegalStateException();
            }
         } else {
            if (!this.field6740.method5801((short)16106)) {
               if (var5 == 1689794791) {
                  throw new IllegalStateException();
               }

               this.field6740.method5778(this.field6750, this.field6743, client.field1019, (byte)2);
               if (!this.field6740.method5801((short)-10268)) {
                  if (var5 == 1689794791) {
                     throw new IllegalStateException();
                  }

                  return;
               }
            }

            WorldMapRenderer.method5793(
               this.field6740, var1, var2, var3, var4, this.field6703, -1964943835 * this.field6693, this.field6705 * 624117895, (byte)-75
            );
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wm.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Lhd;")
   @ObfuscatedName("cl")
   public WorldMapIcon method12656(short var1) {
      try {
         if (!this.field6751.method12442((short)255)) {
            return null;
         } else if (!this.field6740.method5801((short)4599)) {
            if (var1 >= 229) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            HashMap var2 = this.field6740.method5811((byte)87);
            this.field6747 = new LinkedList();
            Iterator var3 = var2.values().iterator();

            while (var3.hasNext()) {
               if (var1 >= 229) {
                  throw new IllegalStateException();
               }

               List var4 = (List)var3.next();
               this.field6747.addAll(var4);
            }

            this.field6746 = this.field6747.iterator();
            return this.method12657((byte)-65);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "wm.cl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bx")
   public void method12572(int var1, int var2) {
      if (null != this.field6741) {
         this.method12476(
            var1 - this.field6743.method5911(2038756018) * 64, var2 - WorldMapData.method5907(this.field6743, (short)-7187) * -2043327439, true, -1436741266
         );
         this.field6708 = 832458829;
         this.field6713 = 833337649;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwm;IILky;Lky;I)V")
   @ObfuscatedName("sh")
   public static void method12650(RenderOverview var0, int var1, int var2, classKY var3, classKY var4, int var5) {
      if (var0 == null) {
         var0.method12651(var1, var1, var3, var3, var1);
      }

      try {
         classHV var6 = new classHV(var2, var3, var4);
         Object[] var7 = new Object[]{var6};
         ScriptEvent var8 = null;
         switch (var1) {
            case 1008:
               var8 = classCA.method1393(10, -1601227786).method14195(var7, (byte)2).method14226((byte)84);
               break;
            case 1009:
               var8 = classCA.method1393(11, -1847135103).method14195(var7, (byte)2).method14226((byte)109);
               break;
            case 1010:
               var8 = classCA.method1393(12, -1573681068).method14195(var7, (byte)2).method14226((byte)67);
               break;
            case 1011:
               var8 = classCA.method1393(13, -1146537211).method14195(var7, (byte)2).method14226((byte)4);
               break;
            case 1012:
               var8 = classCA.method1393(14, -1641551997).method14195(var7, (byte)2).method14226((byte)121);
         }

         if (var8 != null) {
            if (var5 == -1305955669) {
               return;
            }

            classSZ.method10771(var8, -187132422);
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "wm.cq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("cr")
   public boolean method12634(int var1, byte var2) {
      try {
         boolean var10000;
         if (!this.field6707.contains(var1)) {
            if (var2 == 16) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.cr(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lky;")
   @ObfuscatedName("bu")
   public classKY method12587(int var1) {
      try {
         return this.field6742 == null ? null : this.field6742.method5871(this.method12495(726285357), this.method12579(-1418469369), (byte)110);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bs")
   public void method12615(int var1, int var2) {
      try {
         this.field6714 = new HashSet();
         this.field6702 = 0;
         this.field6713 = 0;

         for (int var3 = 0; var3 < classPS.field5405 * 925594537; var3++) {
            if (var2 <= 1729729541) {
               throw new IllegalStateException();
            }

            if (SceneTilePaint.method4817(var3, (byte)-71) == null) {
               if (var2 <= 1729729541) {
                  throw new IllegalStateException();
               }
            } else if (SceneTilePaint.method4817(var3, (byte)-126).field5392 * 1254355271 == var1) {
               if (var2 <= 1729729541) {
                  return;
               }

               this.field6712.add(SceneTilePaint.method4817(var3, (byte)-10).field5403 * 872689451);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wm.bs(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("bz")
   public void method12629(boolean var1, byte var2) {
      try {
         this.field6710 = !var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.bz(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;I)V")
   @ObfuscatedName("kx")
   public static void method12612(RenderOverview var0, int var1) {
      if (var0 == null) {
         var0.method12666();
      }

      var0.field6703 = new HashSet();
      var0.field6703.add(var1);
      var0.field6704 = 0;
      var0.field6705 = 0;
   }

   public void setWorldMapPositionTarget(WorldPoint var1) {
      this.method12664(var1.getX(), var1.getY());
   }

   @ObfuscatedSignature(descriptor = "(IILky;Lky;I)V")
   @ObfuscatedName("cq")
   public void method12651(int var1, int var2, classKY var3, classKY var4, int var5) {
      try {
         classHV var6 = new classHV(var2, var3, var4);
         Object[] var7 = new Object[]{var6};
         ScriptEvent var8 = null;
         switch (var1) {
            case 1008:
               var8 = classCA.method1393(10, -1601227786).method14195(var7, (byte)2).method14226((byte)84);
               break;
            case 1009:
               var8 = classCA.method1393(11, -1847135103).method14195(var7, (byte)2).method14226((byte)109);
               break;
            case 1010:
               var8 = classCA.method1393(12, -1573681068).method14195(var7, (byte)2).method14226((byte)67);
               break;
            case 1011:
               var8 = classCA.method1393(13, -1146537211).method14195(var7, (byte)2).method14226((byte)4);
               break;
            case 1012:
               var8 = classCA.method1393(14, -1641551997).method14195(var7, (byte)2).method14226((byte)121);
         }

         if (var8 != null) {
            if (var5 == -1305955669) {
               return;
            }

            classSZ.method10771(var8, -187132422);
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "wm.cq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cw")
   void method12470() {
      if (classMC.field4479 != null) {
         this.field6727 = this.field6727;
      } else {
         if (this.field6728 < this.field6727) {
            this.field6728 = Math.min(this.field6728, this.field6728 + this.field6727 / 30.0F);
         }

         if (this.field6728 > this.field6727) {
            this.field6728 = Math.max(this.field6727, this.field6727 - this.field6728 / 30.0F);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IZB)V")
   @ObfuscatedName("bv")
   public void method12624(int var1, boolean var2, byte var3) {
      try {
         if (!var2) {
            if (var3 != 51) {
               throw new IllegalStateException();
            }

            this.field6703.add(var1);
         } else {
            this.field6706.remove(var1);
         }

         for (int var4 = 0; var4 < 925594537 * classPS.field5405; var4++) {
            if (var3 != 51) {
               return;
            }

            if (SceneTilePaint.method4817(var4, (byte)-102) == null) {
               if (var3 != 51) {
                  throw new IllegalStateException();
               }
            } else if (SceneTilePaint.method4817(var4, (byte)-49).field5392 * 1254355271 == var1) {
               if (var3 != 51) {
                  throw new IllegalStateException();
               }

               int var5 = SceneTilePaint.method4817(var4, (byte)-31).field5403 * 872689451;
               if (!var2) {
                  if (var3 != 51) {
                     return;
                  }

                  this.field6703.add(var5);
               } else {
                  this.field6706.remove(var5);
               }
            }
         }

         this.method12638((byte)49);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wm.bv(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lgx;")
   @ObfuscatedName("ad")
   public WorldMapData method12499(int var1) {
      try {
         return this.field6741;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("bf")
   public void method12599(int var1, byte var2) {
      try {
         if (var1 >= 1) {
            if (var2 == 7) {
               throw new IllegalStateException();
            }

            this.field6702 = -1724580097 * var1;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wm.bb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ha")
   public int method12671() {
      return this.field6695 * 959218751;
   }

   @ObfuscatedSignature(descriptor = "(I)Lgx;")
   @ObfuscatedName("fy")
   public WorldMapData method12562(int var1) {
      for (WorldMapData var3 : this.field6745.values()) {
         if (var3.method5900(-608589044) == var1) {
            return var3;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ba")
   public int method12550(byte var1) {
      try {
         if (this.field6727 == 1.0) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return 25;
            }
         } else if (this.field6728 == 1.5) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return 37;
            }
         } else if (this.field6728 == 2.0) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return 50;
            }
         } else if (3.0 == this.field6728) {
            return 75;
         } else if (this.field6728 == 4.0) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return 100;
            }
         } else {
            return 200;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.bn(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   void method12471(int var1) {
      try {
         if (classMC.field4479 != null) {
            if (var1 > 943875281) {
               this.field6728 = this.field6728;
            }
         } else {
            if (this.field6728 < this.field6727) {
               if (var1 <= 943875281) {
                  throw new IllegalStateException();
               }

               this.field6728 = Math.min(this.field6727, this.field6728 + this.field6728 / 30.0F);
            }

            if (this.field6728 > this.field6728) {
               if (var1 <= 943875281) {
                  throw new IllegalStateException();
               }

               this.field6728 = Math.max(this.field6727, this.field6727 - this.field6728 / 30.0F);
            }
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wm.as(" + ')');
      }
   }
}
