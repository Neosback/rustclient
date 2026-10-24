import java.util.Iterator;
import java.util.LinkedList;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gx")
public class WorldMapData implements net.runelite.api.WorldMapData {
   @ObfuscatedSignature(descriptor = "Lky;")
   @ObfuscatedName("ax")
   classKY field2732;
   @ObfuscatedName("aa")
   boolean field2730;
   @ObfuscatedName("af")
   String field2737;
   @ObfuscatedName("az")
   int field2731 = -752140647;
   @ObfuscatedName("ag")
   int field2733;
   @ObfuscatedName("as")
   int field2729;
   @ObfuscatedName("ae")
   String field2736;
   @ObfuscatedSignature(descriptor = "Lhg;")
   @ObfuscatedName("ac")
   classHG field2728;
   @ToRemove(unused = "true")
   @ObfuscatedName("bs")
   static final int field2735 = 123;
   @ObfuscatedName("ab")
   int field2734 = 496714561;
   @ObfuscatedName("ao")
   LinkedList field2738;

   @ObfuscatedSignature(descriptor = "(Luz;B)Luz;")
   @ObfuscatedName("ae")
   public static classUZ method5868(classUZ var0, byte var1) {
      try {
         synchronized (classUZ.field6428) {
            if (0 == -1124722655 * classUZ.field6433) {
               return new classUZ(var0);
            } else {
               classUZ.field6428[(classUZ.field6433 -= 801074145) * -1124722655].method11804(var0, -1600171278);
               return classUZ.field6428[-1124722655 * classUZ.field6433];
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gx.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)V")
   @ObfuscatedName("az")
   public void method5849(classXY var1, int var2, int var3) {
      try {
         this.field2731 = 752140647 * var2;
         this.field2736 = var1.method13127(1112097678);
         this.field2737 = var1.method13127(-52847926);
         this.field2732 = new classKY(classXY.method13113(var1, (byte)1));
         this.field2734 = classXY.method13113(var1, (byte)1) * -496714561;
         this.field2733 = classXY.method13113(var1, (byte)1) * 1284192623;
         var1.method13095(-346779531);
         boolean var10001;
         if (var1.method13095(-346779531) == 1) {
            if (var3 <= 643105531) {
               return;
            }

            var10001 = true;
         } else {
            var10001 = false;
         }

         this.field2730 = var10001;
         this.field2729 = var1.method13095(-346779531) * -2079247407;
         int var4 = var1.method13095(-346779531);
         this.field2738 = new LinkedList();

         for (int var5 = 0; var5 < var4; var5++) {
            if (var3 <= 643105531) {
               throw new IllegalStateException();
            }

            this.field2738.add(this.method5857(var1, (byte)1));
         }

         this.method5874(2062050477);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gx.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   void method5874(int var1) {
      try {
         Iterator var2 = this.field2738.iterator();

         while (var2.hasNext()) {
            if (var1 <= 1781988354) {
               throw new IllegalStateException();
            }

            classHM var3 = (classHM)var2.next();
            var3.vmethod469(this.field2728, 342193347);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gx.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ac")
   public int method5876(int var1) {
      try {
         return 1389480535 * this.field2731;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.ac(" + 41);
      }
   }

   public boolean surfaceContainsPosition(int var1, int var2) {
      return method5867(this, var1, var2, 1404367312);
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("al")
   public String method5885(int var1) {
      try {
         return this.field2736;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ao")
   public String method5888(int var1) {
      try {
         return this.field2737;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aj")
   int method5890(int var1) {
      try {
         return 1774498623 * this.field2734;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;IIIB)Z")
   @ObfuscatedName("hq")
   public static boolean method5858(WorldMapData var0, int var1, int var2, int var3, byte var4) {
      if (var0 == null) {
         return var0.method5862(var1, var1, var1, var4);
      } else {
         try {
            for (classHM var6 : var0.field2738) {
               if (var6.vmethod472(var1, var2, var3, -1212958818)) {
                  return true;
               }
            }

            return false;
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "gx.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ay")
   int method5893(byte var1) {
      try {
         return this.field2733 * 1681933711;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.ay(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;Lxy;I)V")
   @ObfuscatedName("gf")
   public static void method5850(WorldMapData var0, classXY var1, int var2) {
      if (var0 == null) {
         var0.method5886();
      }

      var0.field2731 = 752140647 * var2;
      var0.field2736 = var1.method13127(944579687);
      var0.field2737 = var1.method13127(1548044585);
      var0.field2732 = new classKY(classXY.method13113(var1, (byte)1));
      var0.field2734 = classXY.method13113(var1, (byte)1) * -496714561;
      var0.field2733 = classXY.method13113(var1, (byte)1) * 1284192623;
      var1.method13095(-346779531);
      var0.field2730 = var1.method13095(-346779531) == 1;
      var0.field2729 = var1.method13095(-346779531) * -2079247407;
      int var3 = var1.method13095(-346779531);
      var0.field2738 = new LinkedList();

      for (int var4 = 0; var4 < var3; var4++) {
         var0.field2738.add(var0.method5857(var1, (byte)1));
      }

      var0.method5874(1927889252);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bd")
   int method5891() {
      return 2083962968 * this.field2734;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ap")
   public int method5900(int var1) {
      try {
         return this.field2728.field2790 * 1597382741;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.ap(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cz")
   public int method5919() {
      return this.field2732.field4055 * -210579119;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Lhm;")
   @ObfuscatedName("am")
   classHM method5852(classXY var1) {
      int var2 = var1.method13095(-346779531);
      classHR var3 = (classHR)classOF.method8436(classHR.method6131(1499581853), var2, 936069132);
      Object var4 = null;
      switch (var3.field2873 * 408829253) {
         case 0:
            var4 = new classHK();
            break;
         case 1:
            var4 = new classHJ();
            break;
         case 2:
            var4 = new classGJ();
            break;
         case 3:
            var4 = new classGH();
            break;
         default:
            throw new IllegalStateException("");
      }

      ((classHM)var4).vmethod484(var1, 2097581916);
      return (classHM)var4;
   }

   @ObfuscatedSignature(descriptor = "()Lky;")
   @ObfuscatedName("cj")
   public classKY method5927() {
      return new classKY(this.field2732);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   public int method5911(int var1) {
      try {
         return this.field2728.field2789 * 252768043;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.ai(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("bt")
   public boolean method5865(int var1, int var2) {
      int var3 = var1 / 64;
      int var4 = var2 / 64;
      if (var3 >= 1212455025 * this.field2728.field2787 && var3 <= -1918389617 * this.field2728.field2790) {
         if (var4 >= 1546846349 * this.field2728.field2788 && var4 <= this.field2728.field2789 * -394131752) {
            for (classHM var6 : this.field2738) {
               if (var6.vmethod476(var1, var2, (byte)60)) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;)I")
   @ObfuscatedName("ie")
   public static int method5912(WorldMapData var0) {
      return var0 == null ? var0.method5917() : var0.field2728.field2789 * 252768043;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("bg")
   public boolean method5866(int var1, int var2) {
      int var3 = var1 / 64;
      int var4 = var2 / 64;
      if (var3 >= -1293823145 * this.field2728.field2787 && var3 <= 1597382741 * this.field2728.field2790) {
         if (var4 >= 1546846349 * this.field2728.field2788 && var4 <= this.field2728.field2789 * 252768043) {
            for (classHM var6 : this.field2738) {
               if (var6.vmethod476(var1, var2, (byte)60)) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("at")
   public void method5851(classXY var1, int var2) {
      this.field2731 = 752140647 * var2;
      this.field2736 = var1.method13127(381544322);
      this.field2737 = var1.method13127(-815995053);
      this.field2732 = new classKY(classXY.method13113(var1, (byte)1));
      this.field2734 = classXY.method13113(var1, (byte)1) * -496714561;
      this.field2733 = classXY.method13113(var1, (byte)1) * 1284192623;
      var1.method13095(-346779531);
      this.field2730 = var1.method13095(-346779531) == 1;
      this.field2729 = var1.method13095(-346779531) * -2079247407;
      int var3 = var1.method13095(-346779531);
      this.field2738 = new LinkedList();

      for (int var4 = 0; var4 < var3; var4++) {
         this.field2738.add(this.method5857(var1, (byte)1));
      }

      this.method5874(2141978621);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Lhm;")
   @ObfuscatedName("ah")
   classHM method5853(classXY var1) {
      int var2 = var1.method13095(-346779531);
      classHR var3 = (classHR)classOF.method8436(classHR.method6131(1499581853), var2, -407708683);
      Object var4 = null;
      switch (var3.field2873 * 408829253) {
         case 0:
            var4 = new classHK();
            break;
         case 1:
            var4 = new classHJ();
            break;
         case 2:
            var4 = new classGJ();
            break;
         case 3:
            var4 = new classGH();
            break;
         default:
            throw new IllegalStateException("");
      }

      ((classHM)var4).vmethod484(var1, 2092164313);
      return (classHM)var4;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Lhm;")
   @ObfuscatedName("bn")
   classHM method5854(classXY var1) {
      int var2 = var1.method13095(-346779531);
      classHR var3 = (classHR)classOF.method8436(classHR.method6131(1499581853), var2, -640915207);
      Object var4 = null;
      switch (var3.field2873 * 408829253) {
         case 0:
            var4 = new classHK();
            break;
         case 1:
            var4 = new classHJ();
            break;
         case 2:
            var4 = new classGJ();
            break;
         case 3:
            var4 = new classGH();
            break;
         default:
            throw new IllegalStateException("");
      }

      ((classHM)var4).vmethod484(var1, 2145587687);
      return (classHM)var4;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cp")
   public int method5901() {
      return this.field2728.field2790 * 979916645;
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("bp")
   public boolean method5859(int var1, int var2, int var3) {
      for (classHM var5 : this.field2738) {
         if (var5.vmethod472(var1, var2, var3, -819144253)) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("br")
   public boolean method5860(int var1, int var2, int var3) {
      for (classHM var5 : this.field2738) {
         if (var5.vmethod472(var1, var2, var3, -1076031181)) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(Lgx;IIIS)[I")
   @ObfuscatedName("xb")
   public static int[] method5869(WorldMapData var0, int var1, int var2, int var3, short var4) {
      if (var0 == null) {
         var0.method5870(var1, var1, var1, var4);
      }

      try {
         Iterator var5 = var0.field2738.iterator();

         while (var5.hasNext()) {
            if (var4 == 351) {
               throw new IllegalStateException();
            }

            classHM var6 = (classHM)var5.next();
            if (var6.vmethod472(var1, var2, var3, -1079098217)) {
               if (var4 == 351) {
                  throw new IllegalStateException();
               }

               return var6.vmethod478(var1, var2, var3, (byte)44);
            }
         }

         return null;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gx.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lky;")
   @ObfuscatedName("ci")
   public classKY method5928() {
      return new classKY(this.field2732);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   void method5875() {
      for (classHM var2 : this.field2738) {
         var2.vmethod469(this.field2728, 398888398);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;B)Z")
   @ObfuscatedName("qz")
   public static boolean method5880(WorldMapData var0, byte var1) {
      if (var0 == null) {
         return var0.method5884(var1);
      } else {
         try {
            return var0.field2730;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "gx.aa(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bu")
   public int method5877() {
      return 1389480535 * this.field2731;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ba")
   public int method5878() {
      return 1389480535 * this.field2731;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Lhm;")
   @ObfuscatedName("bx")
   classHM method5855(classXY var1) {
      int var2 = var1.method13095(-346779531);
      classHR var3 = (classHR)classOF.method8436(classHR.method6131(1499581853), var2, -1217935631);
      Object var4 = null;
      switch (var3.field2873 * 408829253) {
         case 0:
            var4 = new classHK();
            break;
         case 1:
            var4 = new classHJ();
            break;
         case 2:
            var4 = new classGJ();
            break;
         case 3:
            var4 = new classGH();
            break;
         default:
            throw new IllegalStateException("");
      }

      ((classHM)var4).vmethod484(var1, 2052230242);
      return (classHM)var4;
   }

   @ObfuscatedSignature(descriptor = "(IIB)Lky;")
   @ObfuscatedName("as")
   public classKY method5871(int var1, int var2, byte var3) {
      try {
         for (classHM var5 : this.field2738) {
            if (var5.vmethod476(var1, var2, (byte)60)) {
               if (var3 <= 1) {
                  throw new IllegalStateException();
               }

               return var5.vmethod480(var1, var2, 2141092142);
            }
         }

         return null;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gx.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("be")
   public boolean method5881() {
      return this.field2730;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("by")
   public boolean method5882() {
      return this.field2730;
   }

   @ObfuscatedSignature(descriptor = "(Lgx;I)I")
   @ObfuscatedName("wg")
   public static int method5924(WorldMapData var0, int var1) {
      if (var0 == null) {
         return var0.method5926(var1);
      } else {
         try {
            return var0.field2732.field4054 * 819227381;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "gx.ak(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bq")
   public String method5886() {
      return this.field2736;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cw")
   public int method5925() {
      return this.field2732.field4054 * 819227381;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cr")
   public int method5898() {
      return this.field2728.field2787 * 1924713133;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cu")
   public int method5902() {
      return this.field2728.field2790 * 1597382741;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bl")
   int method5892() {
      return 1774498623 * this.field2734;
   }

   @ObfuscatedSignature(descriptor = "(Lgx;III)Z")
   @ObfuscatedName("zr")
   public static boolean method5867(WorldMapData var0, int var1, int var2, int var3) {
      if (var0 == null) {
         return var0.method5864(var1, var1, var1);
      } else {
         try {
            int var4 = var1 / 64;
            int var5 = var2 / 64;
            if (var4 >= -1293823145 * var0.field2728.field2787) {
               if (var3 >= 1872027761) {
                  throw new IllegalStateException();
               }

               if (var4 <= 1597382741 * var0.field2728.field2790) {
                  if (var5 >= 1546846349 * var0.field2728.field2788) {
                     if (var3 >= 1872027761) {
                        throw new IllegalStateException();
                     }

                     if (var5 <= var0.field2728.field2789 * 252768043) {
                        Iterator var6 = var0.field2738.iterator();

                        while (var6.hasNext()) {
                           if (var3 >= 1872027761) {
                              throw new IllegalStateException();
                           }

                           classHM var7 = (classHM)var6.next();
                           if (var7.vmethod476(var1, var2, (byte)60)) {
                              if (var3 >= 1872027761) {
                                 throw new IllegalStateException();
                              }

                              return true;
                           }
                        }

                        return false;
                     }

                     if (var3 >= 1872027761) {
                        throw new IllegalStateException();
                     }
                  }

                  return false;
               }

               if (var3 >= 1872027761) {
                  throw new IllegalStateException();
               }
            }

            return false;
         } catch (RuntimeException var8) {
            throw classEG.method3899(var8, "gx.ab(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bv")
   int method5894() {
      return this.field2733 * 1681933711;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bh")
   public int method5895() {
      return 1578239281 * this.field2729;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;)Z")
   @ObfuscatedName("zx")
   public static boolean method5883(WorldMapData var0) {
      return var0.field2730;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bz")
   public int method5896() {
      return 364261001 * this.field2729;
   }

   @ObfuscatedSignature(descriptor = "(Lgx;S)I")
   @ObfuscatedName("bj")
   public static int method5907(WorldMapData var0, short var1) {
      if (var0 == null) {
         return var0.method5910(var1);
      } else {
         try {
            return 1546846349 * var0.field2728.field2788;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "gx.au(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lky;")
   @ObfuscatedName("av")
   public classKY method5929(int var1) {
      try {
         return new classKY(this.field2732);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.av(" + ')');
      }
   }

   public WorldMapData() {
      this.field2733 = -1862270976;
      this.field2729 = 2079247407;
      this.field2732 = null;
      this.field2728 = new classHG();
      this.field2730 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;II)Lky;")
   @ObfuscatedName("ly")
   public static classKY method5872(WorldMapData var0, int var1, int var2) {
      for (classHM var4 : var0.field2738) {
         if (var4.vmethod476(var1, var2, (byte)60)) {
            return var4.vmethod480(var1, var2, 2141092142);
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;Lxy;)Lhm;")
   @ObfuscatedName("xe")
   public static classHM method5856(WorldMapData var0, classXY var1) {
      if (var0 == null) {
         var0.method5877();
      }

      int var2 = var1.method13095(-346779531);
      classHR var3 = (classHR)classOF.method8436(classHR.method6131(1499581853), var2, -345759270);
      Object var4 = null;
      switch (var3.field2873 * 408829253) {
         case 0:
            var4 = new classHK();
            break;
         case 1:
            var4 = new classHJ();
            break;
         case 2:
            var4 = new classGJ();
            break;
         case 3:
            var4 = new classGH();
            break;
         default:
            throw new IllegalStateException("");
      }

      ((classHM)var4).vmethod484(var1, 2035974292);
      return (classHM)var4;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cb")
   public int method5903() {
      return this.field2728.field2790 * 1597382741;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ct")
   public int method5904() {
      return this.field2728.field2790 * 1597382741;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;II)Lky;")
   @ObfuscatedName("rb")
   public static classKY method5873(WorldMapData var0, int var1, int var2) {
      for (classHM var4 : var0.field2738) {
         if (var4.vmethod476(var1, var2, (byte)60)) {
            return var4.vmethod480(var1, var2, 2141092142);
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;)I")
   @ObfuscatedName("du")
   public static int method5913(WorldMapData var0) {
      if (var0 == null) {
         var0.method5902();
      }

      return var0.field2728.field2789 * -1134238487;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cy")
   public int method5914() {
      return this.field2728.field2789 * 252768043;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cq")
   public int method5905() {
      return this.field2728.field2790 * 1597382741;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cl")
   public int method5908() {
      return -1864268570 * this.field2728.field2788;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cd")
   public int method5909() {
      return 1546846349 * this.field2728.field2788;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cs")
   public int method5915() {
      return this.field2728.field2789 * 148347620;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aw")
   public int method5920(byte var1) {
      try {
         return this.field2732.field4055 * -210579119;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ca")
   public int method5916() {
      return 1870919123 * this.field2732.field4053;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cc")
   public int method5917() {
      return 1870919123 * this.field2732.field4053;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cn")
   public int method5921() {
      return this.field2732.field4055 * -210579119;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ad")
   public int method5899(int var1) {
      try {
         return this.field2728.field2787 * -1293823145;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.ad(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bs")
   public String method5889() {
      return this.field2737;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bf")
   public String method5887() {
      return this.field2736;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)Lhm;")
   @ObfuscatedName("af")
   classHM method5857(classXY var1, byte var2) {
      try {
         int var3 = var1.method13095(-346779531);
         classHR var4 = (classHR)classOF.method8436(classHR.method6131(1499581853), var3, 2029338115);
         Object var5 = null;
         switch (var4.field2873 * 408829253) {
            case 0:
               var5 = new classHK();
               break;
            case 1:
               var5 = new classHJ();
               break;
            case 2:
               var5 = new classGJ();
               break;
            case 3:
               var5 = new classGH();
               break;
            default:
               throw new IllegalStateException("");
         }

         ((classHM)var5).vmethod484(var1, 2146944303);
         return (classHM)var5;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gx.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;III)Z")
   @ObfuscatedName("rt")
   public static boolean method5861(WorldMapData var0, int var1, int var2, int var3) {
      if (var0 == null) {
         return var0.method5863(var1, var1, var1);
      } else {
         for (classHM var5 : var0.field2738) {
            if (var5.vmethod472(var1, var2, var3, -986597694)) {
               return true;
            }
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   public int method5906(int var1) {
      try {
         return this.field2728.field2788 * 1597382741;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.ap(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)Z")
   @ObfuscatedName("ae")
   public boolean method5862(int var1, int var2, int var3, byte var4) {
      try {
         for (classHM var6 : this.field2738) {
            if (var6.vmethod472(var1, var2, var3, -1212958818)) {
               return true;
            }
         }

         return false;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gx.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aq")
   public int method5922(byte var1) {
      try {
         return this.field2732.field4053 * -210579119;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;B)I")
   @ObfuscatedName("je")
   public static int method5897(WorldMapData var0, byte var1) {
      if (var0 == null) {
         return var0.method5922(var1);
      } else {
         try {
            return 1578239281 * var0.field2729;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "gx.aq(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIS)[I")
   @ObfuscatedName("ag")
   public int[] method5870(int var1, int var2, int var3, short var4) {
      try {
         Iterator var5 = this.field2738.iterator();

         while (var5.hasNext()) {
            if (var4 == 351) {
               throw new IllegalStateException();
            }

            classHM var6 = (classHM)var5.next();
            if (var6.vmethod472(var1, var2, var3, -1079098217)) {
               if (var4 == 351) {
                  throw new IllegalStateException();
               }

               return var6.vmethod478(var1, var2, var3, (byte)44);
            }
         }

         return null;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gx.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("aa")
   public boolean method5884(byte var1) {
      try {
         return this.field2730;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ak")
   public int method5926(int var1) {
      try {
         return this.field2732.field4054 * 819227381;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.ak(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("bm")
   public boolean method5863(int var1, int var2, int var3) {
      for (classHM var5 : this.field2738) {
         if (var5.vmethod472(var1, var2, var3, -986597694)) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("ab")
   public boolean method5864(int var1, int var2, int var3) {
      for (classHM var5 : this.field2738) {
         if (var5.vmethod472(var1, var2, var3, -1076031181)) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("au")
   public int method5910(short var1) {
      try {
         return 1546846349 * this.field2728.field2788;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gx.au(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;)I")
   @ObfuscatedName("lg")
   public static int method5879(WorldMapData var0) {
      return 1389480535 * var0.field2731;
   }

   @ObfuscatedSignature(descriptor = "(Lgx;I)I")
   @ObfuscatedName("qx")
   public static int method5918(WorldMapData var0, int var1) {
      if (var0 == null) {
         return var0.method5906(var1);
      } else {
         try {
            return 1870919123 * var0.field2732.field4053;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "gx.ar(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cf")
   public int method5923() {
      return this.field2732.field4053 * -210579119;
   }
}
