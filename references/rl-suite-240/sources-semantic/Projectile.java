import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bs")
public final class Projectile extends Renderable implements net.runelite.api.Projectile {
   @ObfuscatedName("gy")
   static int field570;
   @ObfuscatedName("af")
   int field547;
   @ObfuscatedName("ae")
   int field559;
   @ToRemove(unused = "true")
   @ObfuscatedName("cm")
   public static final int field569 = 99;
   @ObfuscatedName("aj")
   int field558;
   @ObfuscatedName("as")
   int field551;
   @ToRemove(unused = "true")
   @ObfuscatedName("qz")
   static final int field571 = 32;
   @ObfuscatedName("ax")
   int field552;
   @ObfuscatedName("aa")
   int field557;
   @ObfuscatedName("ao")
   int field561;
   @ObfuscatedName("al")
   int field544;
   @ObfuscatedName("ak")
   double field563;
   @ObfuscatedName("ay")
   int field555;
   @ObfuscatedName("ag")
   int field550;
   @ObfuscatedName("ap")
   double field564;
   @ObfuscatedName("aq")
   int field554;
   @ObfuscatedName("ad")
   double field562;
   @ObfuscatedName("ab")
   int field549;
   @ObfuscatedName("ar")
   int field566;
   @ObfuscatedName("aw")
   boolean field545 = false;
   @ObfuscatedName("au")
   double field560;
   @ObfuscatedName("av")
   int field556;
   @ObfuscatedSignature(descriptor = "Lqr;")
   @ObfuscatedName("at")
   final classQR field553 = new classQR();
   @ToRemove(unused = "true")
   @ObfuscatedName("bs")
   static final int field568 = 25;
   @ToRemove(unused = "true")
   @ObfuscatedName("bo")
   public static final int field567 = 57;
   @ObfuscatedName("ai")
   int field565;
   @ObfuscatedName("az")
   int field546;
   @ObfuscatedName("ac")
   int field548;

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bc")
   void method1216(int var1, int var2) {
      this.field547 += -1758672327 * var1;
      this.field559 += var2 * 1468183829;
      this.field552 += -1147372671 * var1;
      this.field548 += var2 * -2135583901;
      this.field564 = this.field564 + classKY.method6649(var1, -509334942);
      this.field563 = this.field563 + classKY.method6649(var2, 1650293400);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("lq")
   public void method1231(int var1, int var2) {
      this.method1217(var1, var2, -1049759616);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqw;J)Ljava/lang/String;")
   @ObfuscatedName("ek")
   public static String method1222(classQW var0, long var1) {
      if (var0 == null) {
         return var0.method9870(var1);
      } else {
         return var1 == 1L ? var0.field5615 : var0.field5616;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ax")
   void method1217(int var1, int var2, int var3) {
      try {
         this.field547 += -1758672327 * var1;
         this.field559 += var2 * 1468183829;
         this.field552 += -1147372671 * var1;
         this.field548 += var2 * -2135583901;
         this.field564 = this.field564 + classKY.method6649(var1, -535895662);
         this.field563 = this.field563 + classKY.method6649(var2, -55391551);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bs.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("fe")
   static final int method1230(int var0) {
      try {
         float var1 = 200.0F * ((float)ClientPreferences.method2755(classAB.field122, -1674478907) - 0.5F);
         return 100 - Math.round(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bs.fe(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("ae")
   @Override
   protected final Model vmethod104(int var1) {
      try {
         if (this.field553.method9805(30, -1564010216)) {
            if (var1 <= -1767431455) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            classOR var2 = classAI.method367(this.field556 * -2127538519, -1276171479);
            Model var3 = var2.method8757(this.field553.method9780(1823720123), 1621840478);
            if (null == var3) {
               return null;
            } else {
               var3.method5172(-947616749 * this.field566);
               return var3;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bs.ae(" + ')');
      }
   }

   public int getAnimationFrame() {
      return this.field553.field5600 * 292569817;
   }

   public int getFloor() {
      return this.getSourceLevel();
   }

   @ObfuscatedSignature(descriptor = "(Lcl;IIB)V")
   @ObfuscatedName("ac")
   void method1228(classCL var1, int var2, int var3, byte var4) {
      if (var2 >= this.field544 * -988090901) {
         ProjectileMoved var23 = new ProjectileMoved();
         var23.setProjectile(this);
         var23.setPosition(this.getTarget());
         var23.setZ(this.field557 * -1558582945);
         classOE.field4841.getCallbacks().post(var23);
      }

      try {
         if (var2 < this.field544 * -988090901) {
            if (var4 != 1) {
               throw new IllegalStateException();
            }
         } else {
            classUZ var5 = classNG.method7883(
               this.field561 * -711329097, this.field552 * 253215873, -1774680501 * this.field548, -476329201 * this.field551, var1, -1853322114
            );
            int var6 = (int)var5.field6425;
            int var7 = (int)var5.field6424;
            int var8 = (int)var5.field6423 - -1558582945 * this.field557;
            var5.method11797(2082085210);
            if (!this.field545) {
               if (var4 != 1) {
                  throw new IllegalStateException();
               }

               classUZ var9 = classNG.method7883(
                  -431034519 * this.field550, -295435255 * this.field547, 1899622973 * this.field559, this.field546 * 728954045, var1, -921903166
               );
               int var10 = (int)var9.field6425;
               int var11 = (int)var9.field6424;
               int var12 = (int)var9.field6423 - this.field549 * -1976479015;
               var9.method11797(2136541289);
               double var13 = var6 - var10;
               double var15 = var7 - var11;
               double var17 = Math.sqrt(var13 * var13 + var15 * var15);
               double var10001;
               if (Math.abs(var17) < 0.01F) {
                  if (var4 != 1) {
                     throw new IllegalStateException();
                  }

                  var10001 = var10;
               } else {
                  var10001 = var10 + var13 * (980231641 * this.field554) / var17;
               }

               this.field564 = var10001;
               if (Math.abs(var17) < 0.01F) {
                  if (var4 != 1) {
                     throw new IllegalStateException();
                  }

                  var10001 = var11;
               } else {
                  var10001 = this.field554 * 980231641 * var15 / var17 + var11;
               }

               this.field563 = var10001;
               this.field562 = var12;
            }

            double var25 = this.field558 * 384835215 + 1 - var2;
            double var26 = (var6 - this.field564) / var25;
            double var27 = (var7 - this.field563) / var25;
            double var28 = Math.sqrt(var26 * var26 + var27 * var27);
            if (!this.field545) {
               if (var4 != 1) {
                  throw new IllegalStateException();
               }

               this.field560 = -var28 * Math.tan(0.02454369 * (2109317713 * this.field555));
            }

            double var29 = 2.0 * (var8 - this.field562 - var25 * this.field560) / (var25 * var25);
            this.field545 = true;
            this.field564 += var3 * var26;
            this.field563 += var27 * var3;
            this.field562 = this.field562 + (var3 * this.field560 + var3 * (var29 * 0.5) * var3);
            this.field560 += var3 * var29;
            this.field565 = ((int)(Math.atan2(var26, var27) * 325.949) + 1024 & 2047) * 2120328949;
            this.field566 = ((int)(Math.atan2(this.field560, var28) * 325.949) & 2047) * -640686565;
            classAT.field277.method280(classIS.field3051, (int)this.field564, (int)this.field563, false, 1868243250);
            classRD.method9980(this.field553, var3, classAT.field277, -1847056053);
            classAT.field277.method284(-845090641);
         }
      } catch (RuntimeException var24) {
         throw classEG.method3899(var24, "bs.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ay")
   static int method1210(int var0) {
      return var0 - 1;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ad")
   @Override
   protected final Model vmethod92() {
      if (this.field553.method9805(30, -236546980)) {
         return null;
      } else {
         classOR var1 = classAI.method367(this.field556 * -2127538519, -1757031176);
         Model var2 = var1.method8757(this.field553.method9780(2053825511), 1329699974);
         if (null == var2) {
            return null;
         } else {
            var2.method5172(-947616749 * this.field566);
            return var2;
         }
      }
   }

   public int getOrientation() {
      return this.field565 * 1834702173;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   @Override
   protected int vmethod98(int var1) {
      try {
         int var10000;
         if (this.field553.method9772(374233424)) {
            if (var1 <= -1498749565) {
               throw new IllegalStateException();
            }

            var10000 = this.field553.method9775((byte)85).field5136 * -687960895;
         } else {
            var10000 = 0;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bs.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   @Override
   protected boolean vmethod96() {
      if (this.field553.method9772(374233424) && this.field553.method9775((byte)115).method8786(this.field553.method9780(2094498840), 1284412146)) {
         return true;
      } else {
         classOR var1 = classAI.method367(-2127538519 * this.field556, -1611413342);
         Model var2 = classOR.method8760(var1, 1555762411);
         return var2 != null && null != var2.field2442;
      }
   }

   public int getStartPos() {
      return this.field554 * 980231641;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ai")
   @Override
   protected boolean vmethod94() {
      if (this.field553.method9772(374233424) && this.field553.method9775((byte)37).method8786(this.field553.method9780(1858921218), 1284412146)) {
         return true;
      } else {
         classOR var1 = classAI.method367(-544039895 * this.field556, -1394048523);
         Model var2 = classOR.method8760(var1, 400892565);
         return var2 != null && null != var2.field2442;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ar")
   @Override
   protected boolean vmethod97() {
      if (this.field553.method9772(374233424) && this.field553.method9775((byte)115).method8786(this.field553.method9780(1735652441), 1284412146)) {
         return true;
      } else {
         classOR var1 = classAI.method367(1294212229 * this.field556, -2051638579);
         Model var2 = classOR.method8760(var1, 1864499634);
         return var2 != null && null != var2.field2442;
      }
   }

   public int getTargetLevel() {
      return this.field551 * -476329201;
   }

   public int getHeight() {
      return this.field549 * -1976479015;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aq")
   static int method1211(int var0) {
      return var0 - 1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   @Override
   protected int vmethod100() {
      return this.field553.method9772(374233424) ? this.field553.method9775((byte)18).field5136 * -687960895 : 0;
   }

   public double getX() {
      return this.field564;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   @Override
   protected boolean vmethod95(int var1) {
      try {
         if (this.field553.method9772(374233424)) {
            if (var1 == -702405562) {
               throw new IllegalStateException();
            }

            if (this.field553.method9775((byte)82).method8786(this.field553.method9780(2063741440), 1284412146)) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         classOR var2 = classAI.method367(-2127538519 * this.field556, -2108158718);
         Model var3 = classOR.method8760(var2, -306048504);
         return var3 != null && null != var3.field2442;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bs.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcl;II)V")
   @ObfuscatedName("bm")
   void method1219(classCL var1, int var2, int var3) {
      if (var2 >= this.field544 * -988090901) {
         classUZ var4 = classNG.method7883(
            this.field561 * -2143695651, this.field552 * 1573881485, -1774680501 * this.field548, 1275886853 * this.field551, var1, -1149150687
         );
         int var5 = (int)var4.field6425;
         int var6 = (int)var4.field6424;
         int var7 = (int)var4.field6423 - 848487824 * this.field557;
         var4.method11797(1834510005);
         if (!this.field545) {
            classUZ var8 = classNG.method7883(
               843396195 * this.field550, 1323236034 * this.field547, 1899622973 * this.field559, this.field546 * 789642591, var1, -1353905931
            );
            int var9 = (int)var8.field6425;
            int var10 = (int)var8.field6424;
            int var11 = (int)var8.field6423 - this.field549 * -1976479015;
            var8.method11797(2013847212);
            double var12 = var5 - var9;
            double var14 = var6 - var10;
            double var16 = Math.sqrt(var12 * var12 + var14 * var14);
            this.field564 = Math.abs(var16) < 0.01F ? var9 : var9 + var12 * (980231641 * this.field554) / var16;
            this.field563 = Math.abs(var16) < 0.01F ? var10 : this.field554 * 2012582295 * var14 / var16 + var10;
            this.field562 = var11;
         }

         double var18 = this.field558 * 384835215 + 1 - var2;
         double var19 = (var5 - this.field564) / var18;
         double var20 = (var6 - this.field563) / var18;
         double var21 = Math.sqrt(var19 * var19 + var20 * var20);
         if (!this.field545) {
            this.field560 = -var21 * Math.tan(0.02454369 * (2109317713 * this.field555));
         }

         double var22 = 2.0 * (var7 - this.field562 - var18 * this.field560) / (var18 * var18);
         this.field545 = true;
         this.field564 += var3 * var19;
         this.field563 += var20 * var3;
         this.field562 = this.field562 + (var3 * this.field560 + var3 * (var22 * 0.5) * var3);
         this.field560 += var3 * var22;
         this.field565 = ((int)(Math.atan2(var19, var20) * 325.949) + 642228427 & 1540065440) * 2120328949;
         this.field566 = ((int)(Math.atan2(this.field560, var21) * 325.949) & -1176386747) * -640686565;
         classAT.field277.method280(classIS.field3051, (int)this.field564, (int)this.field563, false, 1579253242);
         classRD.method9980(this.field553, var3, classAT.field277, -235825684);
         classAT.field277.method284(-2073208404);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   @Override
   protected int vmethod103() {
      return this.field553.method9772(374233424) ? this.field553.method9775((byte)29).field5136 * -687960895 : 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ao")
   static boolean method1208(int var0) {
      return var0 != 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzd;)I")
   @ObfuscatedName("cy")
   public static int method1226(classZD var0) {
      return 1115114717 * var0.field7151;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("al")
   static boolean method1209(int var0) {
      return var0 != 0;
   }

   public int getY1() {
      return LocalPoint.fromWorld(classIS.field3051, this.field547 * -295435255, this.field559 * 1899622973).getY();
   }

   public net.runelite.api.Actor getSourceActor() {
      int var1 = this.field550 * -431034519;
      WorldView var2 = classIS.field3051;
      if (var1 > 0) {
         int var4 = var1 - 1;
         return (net.runelite.api.Actor)var2.field1696.method13451(var4);
      } else if (var1 < 0) {
         int var3 = -var1 - 1;
         return (net.runelite.api.Actor)var2.field1692.method13451(var3);
      } else {
         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aj")
   static int method1212(int var0) {
      return var0 - 1;
   }

   public int getSourceLevel() {
      return this.field546 * 728954045;
   }

   public void setEndCycle(int var1) {
      this.field558 = var1 * 1968259183;
   }

   @ObfuscatedSignature(descriptor = "(Lcl;II)V")
   @ObfuscatedName("bp")
   void method1220(classCL var1, int var2, int var3) {
      if (var2 >= this.field544 * -988090901) {
         classUZ var4 = classNG.method7883(
            this.field561 * -434424920, this.field552 * 846124369, 188397345 * this.field548, -476329201 * this.field551, var1, -1612222692
         );
         int var5 = (int)var4.field6425;
         int var6 = (int)var4.field6424;
         int var7 = (int)var4.field6423 - -1421604176 * this.field557;
         var4.method11797(1792281252);
         if (!this.field545) {
            classUZ var8 = classNG.method7883(
               1899055320 * this.field550, -295435255 * this.field547, -545315742 * this.field559, this.field546 * 1795886189, var1, -399459984
            );
            int var9 = (int)var8.field6425;
            int var10 = (int)var8.field6424;
            int var11 = (int)var8.field6423 - this.field549 * -1976479015;
            var8.method11797(1904882167);
            double var12 = var5 - var9;
            double var14 = var6 - var10;
            double var16 = Math.sqrt(var12 * var12 + var14 * var14);
            this.field564 = Math.abs(var16) < 0.01F ? var9 : var9 + var12 * (980231641 * this.field554) / var16;
            this.field563 = Math.abs(var16) < 0.01F ? var10 : this.field554 * -1178169900 * var14 / var16 + var10;
            this.field562 = var11;
         }

         double var18 = this.field558 * 384835215 + 1 - var2;
         double var19 = (var5 - this.field564) / var18;
         double var20 = (var6 - this.field563) / var18;
         double var21 = Math.sqrt(var19 * var19 + var20 * var20);
         if (!this.field545) {
            this.field560 = -var21 * Math.tan(0.02454369 * (2109317713 * this.field555));
         }

         double var22 = 2.0 * (var7 - this.field562 - var18 * this.field560) / (var18 * var18);
         this.field545 = true;
         this.field564 += var3 * var19;
         this.field563 += var20 * var3;
         this.field562 = this.field562 + (var3 * this.field560 + var3 * (var22 * 0.5) * var3);
         this.field560 += var3 * var22;
         this.field565 = ((int)(Math.atan2(var19, var20) * 325.949) + 1024 & 117321838) * 2120328949;
         this.field566 = ((int)(Math.atan2(this.field560, var21) * 325.949) & 1160360149) * 668790418;
         classAT.field277.method280(classIS.field3051, (int)this.field564, (int)this.field563, false, -1352873552);
         classRD.method9980(this.field553, var3, classAT.field277, -1735870657);
         classAT.field277.method284(-1170825749);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   @Override
   protected int vmethod99() {
      return this.field553.method9772(374233424) ? this.field553.method9775((byte)36).field5136 * -687960895 : 0;
   }

   public int getRemainingCycles() {
      int var1 = classOE.field4841.getGameCycle();
      return this.getEndCycle() - var1;
   }

   public double getY() {
      return this.field563;
   }

   public int getStartHeight() {
      return this.field549 * -1976479015;
   }

   public WorldPoint getSourcePoint() {
      return new WorldPoint(this.field547 * -295435255, this.field559 * 1899622973, this.field546 * 728954045);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("am")
   static int method1214(int var0) {
      return -var0 - 1;
   }

   public net.runelite.api.Animation getAnimation() {
      return this.field553.field5597;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ah")
   static int method1215(int var0) {
      return -var0 - 1;
   }

   public int getId() {
      return this.field556 * -2127538519;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ap")
   @Override
   protected final Model vmethod93() {
      if (this.field553.method9805(30, -226365416)) {
         return null;
      } else {
         classOR var1 = classAI.method367(this.field556 * 1275730851, -1391534362);
         Model var2 = var1.method8757(this.field553.method9780(2042238521), 1722059945);
         if (null == var2) {
            return null;
         } else {
            var2.method5172(-947616749 * this.field566);
            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   @Override
   protected int vmethod101() {
      return this.field553.method9772(374233424) ? this.field553.method9775((byte)85).field5136 * -421269324 : 0;
   }

   public LocalPoint getTarget() {
      return LocalPoint.fromWorld(classIS.field3051, this.field552 * 253215873, this.field548 * -1774680501);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   @Override
   protected int vmethod102() {
      return this.field553.method9772(374233424) ? this.field553.method9775((byte)73).field5136 * -687960895 : 0;
   }

   @ObfuscatedSignature(descriptor = "(Lcl;II)V")
   @ObfuscatedName("br")
   void method1221(classCL var1, int var2, int var3) {
      if (var2 >= this.field544 * -988090901) {
         classUZ var4 = classNG.method7883(
            this.field561 * 2026180945, this.field552 * 253215873, -1774680501 * this.field548, -476329201 * this.field551, var1, -850146277
         );
         int var5 = (int)var4.field6425;
         int var6 = (int)var4.field6424;
         int var7 = (int)var4.field6423 - -1558582945 * this.field557;
         var4.method11797(1766979584);
         if (!this.field545) {
            classUZ var8 = classNG.method7883(
               -2010404175 * this.field550, 1873381453 * this.field547, 1899622973 * this.field559, this.field546 * 728954045, var1, -737431092
            );
            int var9 = (int)var8.field6425;
            int var10 = (int)var8.field6424;
            int var11 = (int)var8.field6423 - this.field549 * -1976479015;
            var8.method11797(2022063781);
            double var12 = var5 - var9;
            double var14 = var6 - var10;
            double var16 = Math.sqrt(var12 * var12 + var14 * var14);
            this.field564 = Math.abs(var16) < 0.01F ? var9 : var9 + var12 * (-1606861187 * this.field554) / var16;
            this.field563 = Math.abs(var16) < 0.01F ? var10 : this.field554 * -595269690 * var14 / var16 + var10;
            this.field562 = var11;
         }

         double var18 = this.field558 * 286174958 + 1 - var2;
         double var19 = (var5 - this.field564) / var18;
         double var20 = (var6 - this.field563) / var18;
         double var21 = Math.sqrt(var19 * var19 + var20 * var20);
         if (!this.field545) {
            this.field560 = -var21 * Math.tan(0.02454369 * (2109317713 * this.field555));
         }

         double var22 = 2.0 * (var7 - this.field562 - var18 * this.field560) / (var18 * var18);
         this.field545 = true;
         this.field564 += var3 * var19;
         this.field563 += var20 * var3;
         this.field562 = this.field562 + (var3 * this.field560 + var3 * (var22 * 0.5) * var3);
         this.field560 += var3 * var22;
         this.field565 = ((int)(Math.atan2(var19, var20) * 325.949) + -170235679 & -103239752) * 2120328949;
         this.field566 = ((int)(Math.atan2(this.field560, var21) * 325.949) & 1058287986) * 630007551;
         classAT.field277.method280(classIS.field3051, (int)this.field564, (int)this.field563, false, 371644747);
         classRD.method9980(this.field553, var3, classAT.field277, -1153460167);
         classAT.field277.method284(-846143920);
      }
   }

   Projectile(
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15
   ) {
      this.field546 = -1077303659 * var1;
      this.field547 = -1758672327 * var2;
      this.field559 = var3 * 1468183829;
      this.field549 = -619704983 * var4;
      this.field550 = var5 * 2072809689;
      this.field551 = var6 * 269468655;
      this.field552 = var7 * -1147372671;
      this.field548 = var8 * -2135583901;
      this.field557 = 2145170079 * var9;
      this.field561 = 167117575 * var10;
      this.field556 = var11 * 1991028121;
      this.field544 = -1854483773 * var12;
      this.field558 = 1968259183 * var13;
      this.field555 = 1731389105 * var14;
      this.field554 = -1507259799 * var15;
      classQR.method9770(this.field553, classAI.method367(-2127538519 * this.field556, -1725206219).field5081 * 696329207, -2116386216);
   }

   public int getEndCycle() {
      return this.field558 * 384835215;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIILcl;)Luz;")
   @ObfuscatedName("bt")
   static classUZ method1223(int var0, int var1, int var2, int var3, classCL var4) {
      classUZ var5 = null;
      WorldView var6 = null;
      if (classBZ.method1383(var0, (byte)-74)) {
         boolean var8 = var0 > 0;
         Object var7;
         if (var8) {
            int var9 = classHI.method6090(var0, -1508058413);
            var6 = classHB.method6020(var9, var4, 66607526);
            var7 = (Actor)IndexedObjectSet.method13449(var6.field1696, var9);
         } else {
            int var15 = classGA.method5323(var0, -1282125261);
            var6 = classQB.method9481(var15, var4, 1916673709);
            var7 = var6.method3752(var15, (byte)1);
         }

         if (var7 != null) {
            classKY var16 = Actor.method3175((Actor)var7, var6, (short)7751);
            var5 = classHZ.method6171(16777216);
            var5.field6425 = 1870919123 * var16.field4053;
            var5.field6424 = var16.field4054 * -449868112;
         }
      }

      if (var5 == null) {
         var6 = var4.method1590(var1, var2, -1357603660);
         int var13 = var1 - 1996520823 * var6.field1706;
         int var14 = var2 - -351145363 * var6.field1707;
         WorldEntity var17 = (WorldEntity)var4.method1588(-1404261670).field1698.method13637(2140889407 * var6.field1697);
         if (var17 != null) {
            var5 = var17.method10666(classKY.method6656(var13, -1865755514), classKY.method6656(var14, 1376016537), (byte)60);
         } else {
            var5 = classIK.method6261(classKY.method6656(var13, -220471336), 0.0F, classKY.method6656(var14, 1511916235), (byte)3);
         }
      }

      int var18 = (int)var5.field6425;
      int var10 = (int)var5.field6424;
      int var11 = classKV.method6613(var6, var18, var10, var3, (byte)122);
      WorldEntity var12 = (WorldEntity)classIS.field3051.field1698.method13637(var6.field1697 * 2140889407);
      if (null != var12) {
         var11 += classKV.method6613(classIS.field3051, var12.vmethod368(311606126), var12.vmethod371(376334859), var12.vmethod258((byte)-91), (byte)122);
      }

      var5.field6423 = var11;
      return var5;
   }

   public int getEndHeight() {
      return this.field557 * -1558582945;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bn")
   void method1218(int var1, int var2) {
      this.field547 += -409179964 * var1;
      this.field559 += var2 * 1468183829;
      this.field552 += -1147372671 * var1;
      this.field548 += var2 * -2135583901;
      this.field564 = this.field564 + classKY.method6649(var1, 2117323185);
      this.field563 = this.field563 + classKY.method6649(var2, 1820808361);
   }

   public int getSlope() {
      return this.field555 * 2109317713;
   }

   public net.runelite.api.Actor getTargetActor() {
      int var1 = this.field561 * -711329097;
      WorldView var2 = classIS.field3051;
      if (var1 > 0) {
         int var4 = var1 - 1;
         return (net.runelite.api.Actor)var2.field1696.method13451(var4);
      } else if (var1 < 0) {
         int var3 = -var1 - 1;
         return (net.runelite.api.Actor)var2.field1692.method13451(var3);
      } else {
         return null;
      }
   }

   public int getX1() {
      return LocalPoint.fromWorld(classIS.field3051, this.field547 * -295435255, this.field559 * 1899622973).getX();
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("cp")
   static int method1229(int var0, Script var1, boolean var2, int var3) {
      try {
         if (7108 == var0) {
            if (var3 >= -1565688993) {
               throw new IllegalStateException();
            } else {
               int[] var10000 = classBB.field323;
               int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var10002;
               if (classTB.method10779(585092889)) {
                  if (var3 >= -1565688993) {
                     throw new IllegalStateException();
                  }

                  var10002 = 1;
               } else {
                  var10002 = 0;
               }

               var10000[var10001] = var10002;
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bs.cp(" + 41);
      }
   }

   public WorldPoint getTargetPoint() {
      return new WorldPoint(this.field552 * 253215873, this.field548 * -1774680501, this.field551 * -476329201);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Lra;")
   @ObfuscatedName("af")
   static classRA method1213(String var0, int var1) {
      try {
         boolean var2 = true;
         if (var0.endsWith("_z")) {
            if (var1 <= -1581618260) {
               throw new IllegalStateException();
            }

            var2 = false;
            var0 = var0.substring(0, var0.length() - "_z".length());
         }

         if (var0.isEmpty()) {
            if (var1 <= -1581618260) {
               throw new IllegalStateException();
            } else {
               return new classRA(0, var2);
            }
         } else {
            return new classRA(Integer.parseInt(var0), var2);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "bs.af(" + ')');
      }
   }

   public double getZ() {
      return this.field562;
   }

   @ObfuscatedSignature(descriptor = "(Lsu;FFFFFFFFB)V")
   @ObfuscatedName("ae")
   static void method1225(classSU var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, byte var9) {
      try {
         if (null == var0) {
            if (var9 >= -1) {
               throw new IllegalStateException();
            }
         } else {
            float var10 = var4 - var1;
            if (var10 == 0.0F) {
               if (var9 >= -1) {
                  throw new IllegalStateException();
               }
            } else {
               float[] var13;
               boolean var10001;
               label103: {
                  float var11 = var2 - var1;
                  float var12 = var3 - var1;
                  var13 = new float[]{var11 / var10, var12 / var10};
                  if (var13[0] == 0.33333334F) {
                     if (var9 >= -1) {
                        throw new IllegalStateException();
                     }

                     if (0.6666667F == var13[1]) {
                        if (var9 >= -1) {
                           throw new IllegalStateException();
                        }

                        var10001 = true;
                        break label103;
                     }
                  }

                  var10001 = false;
               }

               var0.field6063 = var10001;
               float var14 = var13[0];
               float var15 = var13[1];
               if (var13[0] < 0.0F) {
                  if (var9 >= -1) {
                     throw new IllegalStateException();
                  }

                  var13[0] = 0.0F;
               }

               if (var13[1] > 1.0F) {
                  if (var9 >= -1) {
                     throw new IllegalStateException();
                  }

                  var13[1] = 1.0F;
               }

               label94: {
                  if (!(var13[0] > 1.0F)) {
                     if (var9 >= -1) {
                        throw new IllegalStateException();
                     }

                     if (!(var13[1] < -1.0F)) {
                        break label94;
                     }

                     if (var9 >= -1) {
                        throw new IllegalStateException();
                     }
                  }

                  classBU.method1248(var13, 290889158);
               }

               if (var14 != var13[0]) {
                  if (var9 >= -1) {
                     throw new IllegalStateException();
                  }

                  var2 = var13[0] * var10 + var1;
                  if (0.0F != var14) {
                     if (var9 >= -1) {
                        throw new IllegalStateException();
                     }

                     var6 = var5 + (var6 - var5) * var13[0] / var14;
                  }
               }

               if (var13[1] != var15) {
                  if (var9 >= -1) {
                     throw new IllegalStateException();
                  }

                  var3 = var1 + var13[1] * var10;
                  if (1.0F != var15) {
                     var7 = var8 - (1.0F - var13[1]) * (var8 - var7) / (1.0F - var15);
                  }
               }

               var0.field6055 = var1;
               var0.field6046 = var4;
               Script.method1153(0.0F, var13[0], var13[1], 1.0F, var0, 2000914988);
               float var16 = var6 - var5;
               float var17 = var7 - var6;
               float var18 = var8 - var7;
               float var19 = var17 - var16;
               var0.field6051 = var18 - var17 - var19;
               var0.field6047 = var19 + (var19 + var19);
               var0.field6052 = var16 + var16 + var16;
               var0.field6056 = var5;
            }
         }
      } catch (RuntimeException var20) {
         throw classEG.method3899(var20, "bs.ae(" + ')');
      }
   }

   public int getStartCycle() {
      return this.field544 * -988090901;
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("bo")
   static final int method1227(int var0, byte var1) {
      try {
         int var2 = Math.abs(var0 - client.field797.method11500((short)-7322));
         short var3 = 8192;
         return var2 > var3 ? var0 + classKG.method6508(-1176778633) * (var0 < client.field797.method11500((short)-15215) ? 1 : -1) : var0;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bs.bo(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfx;IIIIFFFFIIII)Z")
   @ObfuscatedName("ai")
   static final boolean method1224(
      Model var0, int var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11, int var12
   ) {
      try {
         if (!classAS.method657(2084378253)) {
            if (var12 == -492863254) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            classEV.method4118(var5, var6, var7, var8, var9, var10, var11, (byte)-78);
            return GrandExchangeOffer.method7660(var0, var1, var2, var3, var4, (byte)76);
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "bs.ai(" + ')');
      }
   }
}
