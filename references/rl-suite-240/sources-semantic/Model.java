import java.awt.Shape;
import java.util.Arrays;
import net.runelite.api.Perspective;
import net.runelite.api.model.Jarvis;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fx")
public class Model extends Renderable implements net.runelite.api.Model {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field2459 = 2500;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2482 = 50;
   @ObfuscatedName("ck")
   int[][] field2506;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field2481 = 6500;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field2466 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2462 = 20;
   @ObfuscatedName("ap")
   static int[] field2474 = new int[6500];
   @ObfuscatedName("ac")
   static boolean[] field2461 = new boolean[6500];
   @ObfuscatedName("be")
   static int field2485;
   @ObfuscatedName("bo")
   static int[] field2476 = new int[10];
   @ObfuscatedName("al")
   static float[] field2450 = new float[6500];
   @ObfuscatedName("aj")
   static float[] field2448 = new float[6500];
   @ObfuscatedName("ay")
   static int[] field2449 = new int[6500];
   @ObfuscatedName("aq")
   static int[] field2468 = new int[6500];
   @ObfuscatedName("ad")
   static int[] field2451 = new int[6500];
   @ObfuscatedName("ak")
   static int[] field2457 = new int[2500];
   @ObfuscatedName("au")
   static char[] field2453 = new char[6000];
   @ObfuscatedName("aa")
   static boolean[] field2447 = new boolean[6500];
   @ObfuscatedName("ar")
   static int[] field2455 = new int[12];
   @ObfuscatedName("aw")
   static int[][] field2456 = new int[12][2500];
   @ObfuscatedName("bn")
   static int[] field2460 = classFH.field2249;
   @ObfuscatedName("cm")
   short field2497;
   @ObfuscatedName("at")
   static int[] field2478 = new int[12];
   @ObfuscatedName("ok")
   public int[] field2503;
   @ObfuscatedName("an")
   static boolean field2446 = true;
   @ObfuscatedName("am")
   static int[] field2463 = classFH.field2251;
   @ObfuscatedName("ah")
   static int[] field2471 = classFH.field2252;
   @ObfuscatedName("ax")
   static boolean[] field2443 = new boolean[6500];
   @ObfuscatedName("fe")
   public int[] field2501;
   @ObfuscatedName("dj")
   public float[] field2428;
   @ObfuscatedName("bx")
   static int[] field2467 = classFH.field2250;
   @ObfuscatedName("bc")
   static float[] field2444 = new float[3];
   @ObfuscatedName("cn")
   int field2416;
   @ObfuscatedName("br")
   static byte[] field2470 = new byte[1];
   @ObfuscatedName("cq")
   short[] field2435;
   @ObfuscatedSignature(descriptor = "Lfx;")
   @ObfuscatedName("bp")
   static Model field2445 = new Model();
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("bg")
   static classUU field2473 = new classUU();
   @ObfuscatedName("cy")
   int[] field2438;
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("bk")
   static classUU field2475 = new classUU();
   @ObfuscatedName("cp")
   byte[] field2434;
   @ObfuscatedName("bu")
   static int[] field2477 = new int[10];
   @ObfuscatedName("ba")
   static int[] field2480 = new int[10];
   @ObfuscatedSignature(descriptor = "Lfx;")
   @ObfuscatedName("bm")
   static Model field2469 = new Model();
   @ObfuscatedName("bb")
   static int field2484;
   @ObfuscatedName("ju")
   public int[] field2502;
   @ObfuscatedName("bi")
   static int field2486;
   @ObfuscatedName("bt")
   static byte[] field2472 = new byte[1];
   @ObfuscatedName("ai")
   static char[][] field2454 = new char[6000][512];
   @ObfuscatedName("wx")
   public float[] field2424;
   @ObfuscatedName("bq")
   int[] field2499;
   @ObfuscatedName("cd")
   byte field2418;
   @ObfuscatedName("bs")
   int[] field2500;
   @ObfuscatedName("bd")
   public int field2417;
   @ObfuscatedName("bl")
   int[] field2427;
   @ObfuscatedName("bv")
   int[] field2430;
   @ObfuscatedName("bh")
   int[] field2431;
   @ObfuscatedName("bz")
   int[] field2432;
   @ObfuscatedName("yz")
   public short[] field2504;
   @ObfuscatedName("cr")
   int[] field2426;
   @ObfuscatedName("av")
   static int[] field2465 = new int[2500];
   @ObfuscatedSignature(descriptor = "Lfx;")
   @ObfuscatedName("lq")
   public Model field2496;
   @ObfuscatedName("cb")
   byte[] field2429;
   @ObfuscatedName("bw")
   static float[] field2452 = new float[10];
   @ObfuscatedName("cu")
   int[] field2433;
   @ObfuscatedName("cs")
   int[] field2437;
   @ObfuscatedName("cl")
   byte[] field2436;
   @ObfuscatedName("bf")
   int[] field2498;
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("bj")
   static classUU field2458 = new classUU();
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2483 = 6000;
   @ObfuscatedName("ct")
   public byte[] field2442;
   @ObfuscatedName("co")
   int[] field2439;
   @ObfuscatedName("ch")
   int field2490;
   @ObfuscatedName("lg")
   public int field2493;
   @ObfuscatedName("ca")
   int[][] field2505;
   @ObfuscatedName("cc")
   public boolean field2419;
   @ObfuscatedName("cz")
   int field2491;
   @ObfuscatedName("cf")
   int field2488;
   @ObfuscatedName("ao")
   static float[] field2464 = new float[6500];
   @ObfuscatedName("cg")
   int field2489;
   @ObfuscatedName("by")
   int field2487;
   @ObfuscatedName("pa")
   public int field2494;
   @ObfuscatedName("cw")
   int[][] field2440;
   @ObfuscatedName("hg")
   public float[] field2425;
   @ObfuscatedName("cj")
   int[][] field2441;
   @ObfuscatedName("ei")
   public int field2492;
   @ObfuscatedSignature(descriptor = "Lfp;")
   @ObfuscatedName("ci")
   AABB field2495;
   @ObfuscatedSignature(descriptor = "Lfl;")
   @ObfuscatedName("cx")
   classFL field2420;
   @ObfuscatedSignature(descriptor = "Lfz;")
   @ObfuscatedName("ce")
   classFZ field2421;
   @ObfuscatedName("cv")
   int field2422 = 0;
   @ObfuscatedName("dc")
   byte field2423;

   public void calculateExtreme(int var1) {
      this.method5117(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lem;Lsn;I[ZZZ)V")
   @ObfuscatedName("bm")
   public void method5149(classEM var1, classSN var2, int var3, boolean[] var4, boolean var5, boolean var6) {
      classEF var7 = var1.method3948((byte)25);
      if (var7 != null) {
         var7.method3886(var2, var3, var4, var5, (byte)11);
         if (var6) {
            this.method5143(var7, var2.method10559(1186942905));
         }
      }

      if (!var5 && var2.method10562(-155425021)) {
         this.method5267(var2, var3);
      }
   }

   public Model(Model[] var1, int var2) {
      this.field2417 = 0;
      this.field2418 = 0;
      this.field2416 = 0;
      this.field2419 = false;
      this.field2420 = null;
      this.field2421 = classFZ.field2513;
      this.field2423 = 0;
      this.field2422 = 0;
      this.field2417 = 0;
      this.field2416 = 0;
      this.field2418 = -1;

      for (int var3 = 0; var3 < var2; var3++) {
         Model var4 = var1[var3];
         if (var4 != null) {
            this.field2422 = this.field2422 + var4.field2422;
            this.field2417 = this.field2417 + var4.field2417;
            this.field2416 = this.field2416 + var4.field2416;
            if (this.field2418 == -1) {
               this.field2418 = var4.field2418;
            }
         }
      }

      this.method5093(this.field2422, this.field2417, this.field2416);
      this.field2422 = 0;
      this.field2417 = 0;
      this.field2416 = 0;

      for (int var5 = 0; var5 < var2; var5++) {
         this.method5101(var1[var5]);
      }

      this.method5271(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIJ)V")
   @ObfuscatedName("fz")
   void method5191(int var1, classUP var2, classUP var3, int var4, int var5, int var6, long var7) {
      if (classFH.field2255.field2215 != 1 || this.field2442 != null || this.field2423 != 0) {
         int var9 = classUP.method11506(var2, 1765668592);
         int var10 = var2.method11507((byte)-16);
         int var11 = classUP.method11506(var3, 1710077081);
         int var12 = var3.method11507((byte)-28);
         float var13 = var2.method11510(2146235953);
         float var14 = var2.method11512((byte)114);
         float var15 = var3.method11510(2111648931);
         float var16 = var3.method11512((byte)110);
         if (this.field2491 != 1) {
            method5254(this);
         }

         this.method5117(var1);
         int var17 = var6 * var12 - var4 * var11 >> 16;
         int var18 = var5 * var9 + var17 * var10 >> 16;
         int var19 = this.field2488 * var10 >> 16;
         int var20 = var18 + var19;
         if (var20 > 742446085 && var18 < classFQ.method4948()) {
            int var21 = var6 * var11 + var4 * var12 >> 16;
            int var22 = (var21 - this.field2488) * classFH.method4747();
            if (var22 / var20 < classFH.method4736()) {
               int var23 = (var21 + this.field2488) * classFH.method4747();
               if (var23 / var20 > classFH.method4732()) {
                  int var24 = var5 * var10 - var17 * var9 >> 16;
                  int var25 = this.field2488 * var9 >> 16;
                  int var26 = var25 + (this.field2490 * var10 >> 16);
                  int var27 = (var24 + var26) * classFH.method4747();
                  if (var27 / var20 > classFH.method4744()) {
                     int var28 = var25 + (this.field1741 * 699042514 * var10 >> 16);
                     int var29 = (var24 - var28) * classFH.method4747();
                     if (var29 / var20 < classFH.method4740()) {
                        int var30 = var19 + (this.field1741 * 1410741989 * var9 >> 16);
                        boolean var31 = false;
                        boolean var32 = false;
                        if (var18 - var30 <= -1395221944) {
                           var32 = true;
                        }

                        boolean var33 = var32 || this.field2435 != null;
                        int var34 = classCS.method2587(-2101960750);
                        int var35 = -1724683376 * classFO.field2346;
                        boolean var37 = classAS.method657(2102810142);
                        boolean var39 = 0L != var7;
                        if (var39) {
                           boolean var40 = 1 == (int)(var7 >>> 19 & 1L);
                           var39 = !var40;
                        }

                        boolean var55 = false;
                        if (var39 && var37) {
                           boolean var41 = false;
                           if (field2446) {
                              var41 = Projectile.method1224(
                                 this,
                                 var1,
                                 var4,
                                 var5,
                                 var6,
                                 var13,
                                 var14,
                                 var15,
                                 var16,
                                 classFH.method4725(),
                                 classFH.method4730(),
                                 classFH.method4747(),
                                 -392431248
                              );
                           } else {
                              int var42 = var18 - var19;
                              if (var42 <= 50) {
                                 var42 = 50;
                              }

                              if (var21 > 0) {
                                 var22 /= var20;
                                 var23 /= var42;
                              } else {
                                 var23 /= var20;
                                 var22 /= var42;
                              }

                              if (var24 > 0) {
                                 var29 /= var20;
                                 var27 /= var42;
                              } else {
                                 var27 /= var20;
                                 var29 /= var42;
                              }

                              int var43 = var34 - classFH.method4725();
                              int var44 = var35 - classFH.method4730();
                              if (var43 > var22 && var43 < var23 && var44 > var29 && var44 < var27) {
                                 var41 = true;
                              }
                           }

                           if (var41) {
                              if (this.field2419) {
                                 int var60 = classFH.method4725();
                                 int var45 = classFH.method4730();
                                 int var46 = classFH.method4747();
                                 classEV.method4118(var13, var14, var15, var16, var60, var45, var46, (byte)-14);
                                 int var57 = classSZ.method10772(
                                    var4,
                                    var5,
                                    var6,
                                    client.field1069 * -1163968665,
                                    1969534689 * classDX.field1660,
                                    1345215331 * classDU.field1631,
                                    1788691143 * classDB.field1373,
                                    1810494134 * classYJ.field6992,
                                    1711517837 * World.field1163,
                                    this.method5118(var1),
                                    934389493
                                 );
                                 int var47 = var57 + 32;
                                 classGI.method5479(var7, var47, -23559056);
                              } else {
                                 var55 = true;
                              }
                           }
                        }

                        int var56 = classFH.method4725();
                        int var58 = classFH.method4730();
                        float var59 = 0.0F;
                        float var61 = 0.0F;
                        if (var1 != 0) {
                           var59 = classFH.field2253[var1];
                           var61 = classFH.field2257[var1];
                        }

                        for (int var62 = 0; var62 < this.field2422; var62++) {
                           float var63 = (int)this.field2425[var62];
                           float var66 = (int)this.field2428[var62];
                           float var48 = (int)this.field2424[var62];
                           if (var1 != 0) {
                              float var49 = var48 * var59 + var63 * var61;
                              var48 = var48 * var61 - var63 * var59;
                              var63 = var49;
                           }

                           var63 += var4;
                           var66 += var5;
                           var48 += var6;
                           float var71 = var48 * var15 + var63 * var16;
                           var48 = var48 * var16 - var63 * var15;
                           float var72 = var66 * var14 - var48 * var13;
                           var48 = var66 * var13 + var48 * var14;
                           var31 |= method5198(this, var62, var71, var72, var48, var18, var56, var58, var33);
                        }

                        try {
                           this.method5202(var31, var55, this.field2419, var7);
                        } catch (Exception var50) {
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIJ)V")
   @ObfuscatedName("bq")
   void method5192(int var1, classUP var2, classUP var3, int var4, int var5, int var6, long var7) {
      if (classFH.field2255.field2215 != 1 || this.field2442 != null || this.field2423 != 0) {
         int var9 = classUP.method11506(var2, -188309584);
         int var10 = var2.method11507((byte)56);
         int var11 = classUP.method11506(var3, 1044302003);
         int var12 = var3.method11507((byte)-29);
         float var13 = var2.method11510(2086932342);
         float var14 = var2.method11512((byte)108);
         float var15 = var3.method11510(2110171503);
         float var16 = var3.method11512((byte)114);
         if (this.field2491 != 1) {
            method5254(this);
         }

         this.method5117(var1);
         int var17 = var6 * var12 - var4 * var11 >> 16;
         int var18 = var5 * var9 + var17 * var10 >> 16;
         int var19 = this.field2488 * var10 >> 16;
         int var20 = var18 + var19;
         if (var20 > 50 && var18 < classFQ.method4948()) {
            int var21 = var6 * var11 + var4 * var12 >> 16;
            int var22 = (var21 - this.field2488) * classFH.method4747();
            if (var22 / var20 < classFH.method4736()) {
               int var23 = (var21 + this.field2488) * classFH.method4747();
               if (var23 / var20 > classFH.method4732()) {
                  int var24 = var5 * var10 - var17 * var9 >> 16;
                  int var25 = this.field2488 * var9 >> 16;
                  int var26 = var25 + (this.field2490 * var10 >> 16);
                  int var27 = (var24 + var26) * classFH.method4747();
                  if (var27 / var20 > classFH.method4744()) {
                     int var28 = var25 + (this.field1741 * -1256242689 * var10 >> 16);
                     int var29 = (var24 - var28) * classFH.method4747();
                     if (var29 / var20 < classFH.method4740()) {
                        int var30 = var19 + (this.field1741 * -1256242689 * var9 >> 16);
                        boolean var31 = false;
                        boolean var32 = false;
                        if (var18 - var30 <= 50) {
                           var32 = true;
                        }

                        boolean var33 = var32 || this.field2435 != null;
                        int var34 = classCS.method2587(-820146731);
                        int var35 = 1170217135 * classFO.field2346;
                        boolean var37 = classAS.method657(2088058067);
                        boolean var39 = 0L != var7;
                        if (var39) {
                           boolean var40 = 1 == (int)(var7 >>> 19 & 1L);
                           var39 = !var40;
                        }

                        boolean var55 = false;
                        if (var39 && var37) {
                           boolean var41 = false;
                           if (field2446) {
                              var41 = Projectile.method1224(
                                 this,
                                 var1,
                                 var4,
                                 var5,
                                 var6,
                                 var13,
                                 var14,
                                 var15,
                                 var16,
                                 classFH.method4725(),
                                 classFH.method4730(),
                                 classFH.method4747(),
                                 1949910178
                              );
                           } else {
                              int var42 = var18 - var19;
                              if (var42 <= 50) {
                                 var42 = 50;
                              }

                              if (var21 > 0) {
                                 var22 /= var20;
                                 var23 /= var42;
                              } else {
                                 var23 /= var20;
                                 var22 /= var42;
                              }

                              if (var24 > 0) {
                                 var29 /= var20;
                                 var27 /= var42;
                              } else {
                                 var27 /= var20;
                                 var29 /= var42;
                              }

                              int var43 = var34 - classFH.method4725();
                              int var44 = var35 - classFH.method4730();
                              if (var43 > var22 && var43 < var23 && var44 > var29 && var44 < var27) {
                                 var41 = true;
                              }
                           }

                           if (var41) {
                              if (this.field2419) {
                                 int var60 = classFH.method4725();
                                 int var45 = classFH.method4730();
                                 int var46 = classFH.method4747();
                                 classEV.method4118(var13, var14, var15, var16, var60, var45, var46, (byte)-58);
                                 int var57 = classSZ.method10772(
                                    var4,
                                    var5,
                                    var6,
                                    client.field1069 * -1163968665,
                                    -639123865 * classDX.field1660,
                                    -1674330987 * classDU.field1631,
                                    1788691143 * classDB.field1373,
                                    1768347607 * classYJ.field6992,
                                    -1569185711 * World.field1163,
                                    this.method5118(var1),
                                    1140024710
                                 );
                                 int var47 = var57 + 32;
                                 classGI.method5479(var7, var47, -1724546756);
                              } else {
                                 var55 = true;
                              }
                           }
                        }

                        int var56 = classFH.method4725();
                        int var58 = classFH.method4730();
                        float var59 = 0.0F;
                        float var61 = 0.0F;
                        if (var1 != 0) {
                           var59 = classFH.field2253[var1];
                           var61 = classFH.field2257[var1];
                        }

                        for (int var62 = 0; var62 < this.field2422; var62++) {
                           float var63 = (int)this.field2425[var62];
                           float var66 = (int)this.field2428[var62];
                           float var48 = (int)this.field2424[var62];
                           if (var1 != 0) {
                              float var49 = var48 * var59 + var63 * var61;
                              var48 = var48 * var61 - var63 * var59;
                              var63 = var49;
                           }

                           var63 += var4;
                           var66 += var5;
                           var48 += var6;
                           float var71 = var48 * var15 + var63 * var16;
                           var48 = var48 * var16 - var63 * var15;
                           float var72 = var66 * var14 - var48 * var13;
                           var48 = var66 * var13 + var48 * var14;
                           var31 |= method5198(this, var62, var71, var72, var48, var18, var56, var58, var33);
                        }

                        try {
                           this.method5202(var31, var55, this.field2419, var7);
                        } catch (Exception var50) {
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public int getBottomY() {
      return this.field2490;
   }

   public byte[] getFaceTransparencies() {
      return this.field2442;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("as")
   void method5101(Model var1) {
      this.method5266(var1);
      if (var1 != null) {
         this.method5099(var1);

         for (int var2 = 0; var2 < var1.field2417; var2++) {
            this.field2427[this.field2417] = var1.field2427[var2] + this.field2422;
            this.field2430[this.field2417] = var1.field2430[var2] + this.field2422;
            this.field2431[this.field2417] = var1.field2431[var2] + this.field2422;
            this.field2432[this.field2417] = var1.field2432[var2];
            this.field2426[this.field2417] = var1.field2426[var2];
            this.field2433[this.field2417] = var1.field2433[var2];
            if (this.field2429 != null) {
               this.field2429[this.field2417] = var1.field2429 != null ? var1.field2429[var2] : var1.field2418;
            }

            if (this.field2442 != null && var1.field2442 != null) {
               this.field2442[this.field2417] = var1.field2442[var2];
            }

            if (this.field2435 != null) {
               this.field2435[this.field2417] = var1.field2435 != null ? var1.field2435[var2] : -1;
            }

            if (this.field2434 != null) {
               if (var1.field2434 != null && var1.field2434[var2] != -1) {
                  this.field2434[this.field2417] = (byte)(var1.field2434[var2] + this.field2416);
               } else {
                  this.field2434[this.field2417] = -1;
               }
            }

            if (this.field2436 != null && var1.field2436 != null) {
               this.field2436[this.field2417] = var1.field2436[var2];
            }

            this.field2417++;
         }

         for (int var3 = 0; var3 < var1.field2416; var3++) {
            this.field2437[this.field2416] = var1.field2437[var3] + this.field2422;
            this.field2438[this.field2416] = var1.field2438[var3] + this.field2422;
            this.field2439[this.field2416] = var1.field2439[var3] + this.field2422;
            this.field2416++;
         }

         for (int var4 = 0; var4 < var1.field2422; var4++) {
            this.field2425[this.field2422] = var1.field2425[var4];
            this.field2428[this.field2422] = var1.field2428[var4];
            this.field2424[this.field2422] = var1.field2424[var4];
            this.field2422++;
         }
      }
   }

   public int[] getFaceColors3() {
      return this.field2433;
   }

   @ObfuscatedSignature(descriptor = "([[IIIIZI)Lfx;")
   @ObfuscatedName("ax")
   public Model method5258(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      int var14 = var6;
      int var12 = var4;
      int var11 = var3;
      int var10 = var2;
      int[][] var9 = var1;
      Model var8 = this;
      method5254(this);
      int var15 = var2 - this.field2488;
      int var16 = var2 + this.field2488;
      int var17 = var4 - this.field2488;
      int var18 = var4 + this.field2488;
      Model var10000;
      if (var15 >= 0 && var16 + 128 >> 7 < var1.length && var17 >= 0 && var18 + 128 >> 7 < var1[0].length) {
         var15 >>= 7;
         var16 = var16 + 127 >> 7;
         var17 >>= 7;
         var18 = var18 + 127 >> 7;
         if (var1[var15][var17] == var3 && var1[var16][var17] == var3 && var1[var15][var18] == var3 && var1[var16][var18] == var3) {
            var10000 = this;
         } else {
            Model var19;
            if (var5) {
               var19 = new Model(this);
               var19.field2428 = new float[var19.field2422];
            } else {
               var19 = this;
            }

            if (var6 == 0) {
               for (int var35 = 0; var35 < var19.field2422; var35++) {
                  int var36 = (int)var8.field2425[var35] + var10;
                  int var37 = (int)var8.field2424[var35] + var12;
                  int var38 = var36 & 127;
                  int var39 = var37 & 127;
                  int var40 = var36 >> 7;
                  int var41 = var37 >> 7;
                  int var42 = var9[var40][var41] * (128 - var38) + var9[var40 + 1][var41] * var38 >> 7;
                  int var43 = var9[var40][var41 + 1] * (128 - var38) + var9[var40 + 1][var41 + 1] * var38 >> 7;
                  int var44 = var42 * (128 - var39) + var43 * var39 >> 7;
                  var19.field2428[var35] = (int)var8.field2428[var35] + var44 - var11;
               }
            } else {
               for (int var20 = 0; var20 < var19.field2422; var20++) {
                  int var21 = (-((int)var8.field2428[var20]) << 16) / (var8.field1741 * -1256242689);
                  if (var21 < var14) {
                     int var22 = (int)var8.field2425[var20] + var10;
                     int var23 = (int)var8.field2424[var20] + var12;
                     int var24 = var22 & 127;
                     int var25 = var23 & 127;
                     int var26 = var22 >> 7;
                     int var27 = var23 >> 7;
                     int var28 = var9[var26][var27] * (128 - var24) + var9[var26 + 1][var27] * var24 >> 7;
                     int var29 = var9[var26][var27 + 1] * (128 - var24) + var9[var26 + 1][var27 + 1] * var24 >> 7;
                     int var30 = var28 * (128 - var25) + var29 * var25 >> 7;
                     var19.field2428[var20] = (int)var8.field2428[var20] + (var30 - var11) * (var14 - var21) / var14;
                  } else {
                     var19.field2428[var20] = var8.field2428[var20];
                  }
               }
            }

            var19.method5129();
            var10000 = var19;
         }
      } else {
         var10000 = this;
      }

      Model var7 = var10000;
      if (var7 != this && (client.field1021 & 2) == 2 && var6 == 0) {
         var7.field2496 = this;
      }

      return var7;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;I)V")
   @ObfuscatedName("jl")
   public static void method5170(Model var0, int var1) {
      if (var0 == null) {
         var0.method5097(var1, var1, var1);
      }

      int var2 = field2463[var1];
      int var3 = field2471[var1];

      for (int var4 = 0; var4 < var0.field2422; var4++) {
         int var5 = (int)var0.field2428[var4] * var3 - (int)var0.field2424[var4] * var2 >> 16;
         var0.field2424[var4] = (int)var0.field2428[var4] * var2 + (int)var0.field2424[var4] * var3 >> 16;
         var0.field2428[var4] = var5;
      }

      var0.method5129();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;IIIIIII)V")
   @ObfuscatedName("gb")
   public static void method5184(Model var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var0.field2491 != 2) {
         var0.method5263();
      }

      int var8 = classFH.method4725();
      int var9 = classFH.method4730();
      int var10 = field2463[var1];
      int var11 = field2471[var1];
      int var12 = field2463[var2];
      int var13 = field2471[var2];
      int var14 = field2463[var3];
      int var15 = field2471[var3];
      int var16 = field2463[var4];
      int var17 = field2471[var4];
      int var18 = var6 * var16 + var7 * var17 >> 16;

      for (int var19 = 0; var19 < var0.field2422; var19++) {
         int var20 = (int)var0.field2425[var19];
         int var21 = (int)var0.field2428[var19];
         int var22 = (int)var0.field2424[var19];
         if (var3 != 0) {
            int var23 = var21 * var14 + var20 * var15 >> 16;
            var21 = var21 * var15 - var20 * var14 >> 16;
            var20 = var23;
         }

         if (var1 != 0) {
            int var29 = var21 * var11 - var22 * var10 >> 16;
            var22 = var21 * var10 + var22 * var11 >> 16;
            var21 = var29;
         }

         if (var2 != 0) {
            int var30 = var22 * var12 + var20 * var13 >> 16;
            var22 = var22 * var13 - var20 * var12 >> 16;
            var20 = var30;
         }

         var20 += var5;
         var21 += var6;
         var22 += var7;
         int var31 = var21 * var17 - var22 * var16 >> 16;
         var22 = var21 * var16 + var22 * var17 >> 16;
         field2449[var19] = var22 - var18;
         field2464[var19] = var8 + var20 * classFH.method4747() / var22;
         field2450[var19] = var9 + var31 * classFH.method4747() / var22;
         field2448[var19] = var22;
         if (var0.field2435 != null) {
            field2468[var19] = var20;
            field2451[var19] = var31;
            field2474[var19] = var22;
         }
      }

      try {
         var0.method5202(false, false, false, 0L);
      } catch (Exception var24) {
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;Lfs;I)V")
   @ObfuscatedName("rc")
   public void method5262(rl21 var1, classFS var2, int var3) {
      if (this.field2440 != null && var3 != -1) {
         classET var4 = var2.field2390[var3];
         classEM var5 = var4.field1909;
         var1.field5730 = 0;
         var1.field5731 = 0;
         var1.field5732 = 0;

         for (int var6 = 0; var6 < var4.field1911; var6++) {
            int var7 = var4.field1910[var6];
            this.method5265(var1, var5.field1783[var7], var5.field1785[var7], var4.field1907[var6], var4.field1913[var6], var4.field1912[var6]);
         }

         this.method5294();
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Lfx;")
   @ObfuscatedName("ac")
   public Model method5106(boolean var1) {
      if (!var1 && field2470.length < this.field2417) {
         field2470 = new byte[this.field2417 + 100];
      }

      return this.method5112(var1, field2445, field2470);
   }

   @ObfuscatedSignature(descriptor = "(Z)Lfx;")
   @ObfuscatedName("aa")
   public Model method5109(boolean var1) {
      if (!var1 && field2472.length < this.field2417) {
         field2472 = new byte[this.field2417 + 100];
      }

      return this.method5112(var1, field2469, field2472);
   }

   @ObfuscatedSignature(descriptor = "(ZLfx;[B)Lfx;")
   @ObfuscatedName("ao")
   Model method5112(boolean var1, Model var2, byte[] var3) {
      this.method5255(var1, var2, var3);
      var2.field2422 = this.field2422;
      var2.field2417 = this.field2417;
      var2.field2416 = this.field2416;
      if (var2.field2425 == null || var2.field2425.length < this.field2422) {
         var2.field2425 = new float[this.field2422 + 100];
         var2.field2428 = new float[this.field2422 + 100];
         var2.field2424 = new float[this.field2422 + 100];
      }

      for (int var4 = 0; var4 < this.field2422; var4++) {
         var2.field2425[var4] = this.field2425[var4];
         var2.field2428[var4] = this.field2428[var4];
         var2.field2424[var4] = this.field2424[var4];
      }

      if (var1) {
         var2.field2442 = this.field2442;
      } else {
         var2.field2442 = var3;
         if (this.field2442 == null) {
            for (int var5 = 0; var5 < this.field2417; var5++) {
               var2.field2442[var5] = 0;
            }
         } else {
            for (int var6 = 0; var6 < this.field2417; var6++) {
               var2.field2442[var6] = this.field2442[var6];
            }
         }
      }

      var2.field2427 = this.field2427;
      var2.field2430 = this.field2430;
      var2.field2431 = this.field2431;
      var2.field2432 = this.field2432;
      var2.field2426 = this.field2426;
      var2.field2433 = this.field2433;
      var2.field2429 = this.field2429;
      var2.field2434 = this.field2434;
      var2.field2435 = this.field2435;
      var2.field2436 = this.field2436;
      var2.field2418 = this.field2418;
      var2.field2437 = this.field2437;
      var2.field2438 = this.field2438;
      var2.field2439 = this.field2439;
      var2.field2440 = this.field2440;
      var2.field2441 = this.field2441;
      var2.field2506 = this.field2506;
      var2.field2505 = this.field2505;
      var2.field2419 = this.field2419;
      var2.field2420 = this.field2420;
      var2.field2497 = this.field2497;
      var2.field2423 = this.field2423;
      var2.method5129();
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;I)V")
   @ObfuscatedName("wu")
   public static void method5171(Model var0, int var1) {
      if (var0 == null) {
         var0.method5242(var1);
      } else {
         int var2 = field2463[var1];
         int var3 = field2471[var1];

         for (int var4 = 0; var4 < var0.field2422; var4++) {
            int var5 = (int)var0.field2428[var4] * var3 - (int)var0.field2424[var4] * var2 >> 16;
            var0.field2424[var4] = (int)var0.field2428[var4] * var2 + (int)var0.field2424[var4] * var3 >> 16;
            var0.field2428[var4] = var5;
         }

         var0.method5129();
      }
   }

   @ObfuscatedSignature(descriptor = "([Lfx;I)V")
   @ObfuscatedName("qv")
   public void method5271(Model[] var1, int var2) {
      this.method5272();
   }

   Model(Model var1) {
      this.field2417 = 0;
      this.field2418 = 0;
      this.field2416 = 0;
      this.field2419 = false;
      this.field2420 = null;
      this.field2421 = classFZ.field2513;
      this.field2423 = 0;
      this.field2422 = var1.field2422;
      this.field2417 = var1.field2417;
      this.field2416 = var1.field2416;
      this.field2425 = var1.field2425;
      this.field2428 = var1.field2428;
      this.field2424 = var1.field2424;
      this.field2427 = var1.field2427;
      this.field2430 = var1.field2430;
      this.field2431 = var1.field2431;
      this.field2432 = var1.field2432;
      this.field2426 = var1.field2426;
      this.field2433 = var1.field2433;
      this.field2429 = var1.field2429;
      this.field2442 = var1.field2442;
      this.field2434 = var1.field2434;
      this.field2435 = var1.field2435;
      this.field2436 = var1.field2436;
      this.field2418 = var1.field2418;
      this.field2437 = var1.field2437;
      this.field2438 = var1.field2438;
      this.field2439 = var1.field2439;
      this.field2440 = var1.field2440;
      this.field2441 = var1.field2441;
      this.field2419 = var1.field2419;
      this.method5249(var1);
   }

   @ObfuscatedSignature(descriptor = "(ZLfx;[B)V")
   @ObfuscatedName("km")
   public void method5255(boolean var1, Model var2, byte[] var3) {
      var2.field2501 = this.field2501;
      var2.field2503 = this.field2503;
      var2.field2502 = this.field2502;
      var2.field2504 = this.field2504;
   }

   @ObfuscatedSignature(descriptor = "(I)Lfp;")
   @ObfuscatedName("aj")
   AABB method5118(int var1) {
      for (AABB var2 = this.field2495; var2 != null; var2 = var2.field2369) {
         if (var2.field2362 * 1384390485 == var1) {
            return var2;
         }
      }

      return null;
   }

   public byte[] getTextureFaces() {
      return this.field2434;
   }

   public int[] getVertexNormalsZ() {
      return this.field2502;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dw")
   public void method5129() {
      this.field2491 = 0;
      this.field2495 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gy")
   public void method5236(int var1) {
      if (var1 != 0) {
         for (int var2 = 0; var2 < this.field2422; var2++) {
            this.field2428[var2] = (int)this.field2428[var2] + var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("sj")
   public void method5281(int var1, int var2, int var3) {
      if ((client.field1021 & 64) != 0) {
         this.field2504 = new short[var2];
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("bc")
   void method5267(classSN var1, int var2) {
      classEM var3 = var1.field5981;
      byte[] var4 = this.getFaceTransparencies();

      for (int var5 = 0; var5 < var3.field1784 * 1690200285; var5++) {
         int var6 = var3.field1783[var5];
         if (var6 == 5 && var1.field5998 != null && var1.field5998[var5 * 1 + 0] != 0 && this.field2441 != null && var4 != null) {
            int var7 = var1.field5998[var5 * 1 + 0];

            for (int var11 : var3.field1785[var5]) {
               if (var11 < this.field2441.length) {
                  int[] var12 = this.field2441[var11];

                  for (int var13 = 0; var13 < var12.length; var13++) {
                     int var14 = var12[var13];
                     int var15 = (int)((var4[var14] & 255) + var1.method10585(var7, var2) * 255.0F);
                     if (var15 < 0) {
                        var15 = 0;
                     } else if (var15 > 255) {
                        var15 = 255;
                     }

                     var4[var14] = (byte)var15;
                  }
               }
            }
         }
      }
   }

   public int[] getTexIndices3() {
      return this.field2439;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("no")
   public Model method5282() {
      this.method5186();
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lef;I)V")
   @ObfuscatedName("bp")
   void method5143(classEF var1, int var2) {
      this.method5277(var1, var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;ZZZJ)V")
   @ObfuscatedName("wz")
   public static void method5200(Model var0, boolean var1, boolean var2, boolean var3, long var4) {
      if (var0.field2489 < 6000) {
         for (int var6 = 0; var6 < var0.field2489; var6++) {
            field2453[var6] = 0;
         }

         int var30 = var3 ? 20 : 5;
         int var7 = classFH.method4751();

         for (int var8 = 0; var8 < var0.field2417; var8++) {
            if (var0.field2433[var8] == 1428405977) {
               field2447[var8] = true;
            } else {
               if (var0.field2421 != classFZ.field2515) {
                  if (classFH.field2255.field2215 == 1 && (var0.field2442 == null || var0.field2442[var8] == 0) && var0.field2423 == 0) {
                     field2447[var8] = true;
                     continue;
                  }

                  if (classFH.field2255.field2215 == 0 && (var0.field2442 != null && var0.field2442[var8] != 0 || var0.field2423 != 0)) {
                     field2447[var8] = true;
                     continue;
                  }
               }

               int var9 = var0.field2427[var8];
               int var10 = var0.field2430[var8];
               int var11 = var0.field2431[var8];
               float var12 = field2464[var9];
               float var13 = field2464[var10];
               float var14 = field2464[var11];
               field2461[var8] = var1 && (var12 == -5000.0F || var13 == -5000.0F || var14 == -5000.0F);
               if (field2461[var8]) {
                  int var15 = field2468[var9];
                  int var16 = field2468[var10];
                  int var17 = field2468[var11];
                  int var18 = field2451[var9];
                  int var19 = field2451[var10];
                  int var20 = field2451[var11];
                  int var21 = field2474[var9];
                  int var22 = field2474[var10];
                  int var23 = field2474[var11];
                  var15 -= var16;
                  var17 -= var16;
                  var18 -= var19;
                  var20 -= var19;
                  var21 -= var22;
                  var23 -= var22;
                  int var24 = var18 * var23 - var21 * var20;
                  int var25 = var21 * var17 - var15 * var23;
                  int var26 = var15 * var20 - var18 * var17;
                  field2447[var8] = var16 * var24 + var19 * var25 + var22 * var26 <= 0.0F;
               } else {
                  field2447[var8] = (var12 - var13) * (field2450[var11] - field2450[var10]) - (field2450[var9] - field2450[var10]) * (var14 - var13) <= 0.0F;
                  field2443[var8] = var12 < 0.0F || var13 < 0.0F || var14 < 0.0F || var12 > var7 || var13 > var7 || var14 > var7;
                  if (var2
                     && classDG.method3086(
                        (int)field2450[var9], (int)field2450[var10], (int)field2450[var11], (int)var12, (int)var13, (int)var14, var30, -1778910972
                     )) {
                     int var35 = (int)(field2448[var9] + field2448[var10] + field2448[var11]) / 3;
                     classGI.method5479(var4, var35, -1931336340);
                  }
               }
            }
         }

         short var31 = var0.field2497;
         boolean var32 = false;
         if (classFH.field2255.field2213.method4857()) {
            var0.field2497 = (short)var0.field2417;
         } else if (var0.field2420 != null && var0.field2420.method4857()) {
            classFH.field2255.field2213.method4843(var0.field2420);
            var32 = true;
         }

         try {
            if (!classFH.method4755()) {
               classMO.method7679(var0, true);
            } else {
               classFZ var33 = var0.field2421;
               if (var33 == classFZ.field2513) {
                  if (classFH.field2255.field2215 != 0 && var0.field2442 != null) {
                     var33 = classFZ.field2514;
                  } else {
                     var33 = classFZ.field2516;
                  }
               }

               switch (var33.field2512) {
                  case 1:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     classMO.method7679(var0, false);
                     break;
                  case 2:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = 0;
                     classMO.method7679(var0, true);
                     classFH.field2255.field2208 = 0;
                     classFH.field2255.field2214 = -1;
                     classMO.method7679(var0, true);
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     break;
                  case 3:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     method5207(var0);
                     break;
                  case 4:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = 0;
                     method5207(var0);
               }
            }
         } finally {
            var0.field2497 = var31;
            if (var32) {
               classFL.method4856(classFH.field2255.field2213);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("xw")
   public static void method5130(Model var0) {
      if (var0 == null) {
         var0.method5299();
      }

      var0.field2491 = 0;
      var0.field2495 = null;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("az")
   void method5093(int var1, int var2, int var3) {
      this.method5281(var1, var2, var3);
      this.field2425 = new float[var1];
      this.field2428 = new float[var1];
      this.field2424 = new float[var1];
      this.field2427 = new int[var2];
      this.field2430 = new int[var2];
      this.field2431 = new int[var2];
      this.field2432 = new int[var2];
      this.field2426 = new int[var2];
      this.field2433 = new int[var2];
      if (var3 > 0) {
         this.field2437 = new int[var3];
         this.field2438 = new int[var3];
         this.field2439 = new int[var3];
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZZJ)V")
   @ObfuscatedName("bs")
   final void method5201(boolean var1, boolean var2, boolean var3, long var4) {
      if (this.field2489 < 6000) {
         for (int var6 = 0; var6 < this.field2489; var6++) {
            field2453[var6] = 0;
         }

         int var30 = var3 ? 20 : 5;
         int var7 = classFH.method4751();

         for (int var8 = 0; var8 < this.field2417; var8++) {
            if (this.field2433[var8] == -2) {
               field2447[var8] = true;
            } else {
               if (this.field2421 != classFZ.field2515) {
                  if (classFH.field2255.field2215 == 1 && (this.field2442 == null || this.field2442[var8] == 0) && this.field2423 == 0) {
                     field2447[var8] = true;
                     continue;
                  }

                  if (classFH.field2255.field2215 == 0 && (this.field2442 != null && this.field2442[var8] != 0 || this.field2423 != 0)) {
                     field2447[var8] = true;
                     continue;
                  }
               }

               int var9 = this.field2427[var8];
               int var10 = this.field2430[var8];
               int var11 = this.field2431[var8];
               float var12 = field2464[var9];
               float var13 = field2464[var10];
               float var14 = field2464[var11];
               field2461[var8] = var1 && (var12 == -5000.0F || var13 == -5000.0F || var14 == -5000.0F);
               if (field2461[var8]) {
                  int var15 = field2468[var9];
                  int var16 = field2468[var10];
                  int var17 = field2468[var11];
                  int var18 = field2451[var9];
                  int var19 = field2451[var10];
                  int var20 = field2451[var11];
                  int var21 = field2474[var9];
                  int var22 = field2474[var10];
                  int var23 = field2474[var11];
                  var15 -= var16;
                  var17 -= var16;
                  var18 -= var19;
                  var20 -= var19;
                  var21 -= var22;
                  var23 -= var22;
                  int var24 = var18 * var23 - var21 * var20;
                  int var25 = var21 * var17 - var15 * var23;
                  int var26 = var15 * var20 - var18 * var17;
                  field2447[var8] = var16 * var24 + var19 * var25 + var22 * var26 <= 0.0F;
               } else {
                  field2447[var8] = (var12 - var13) * (field2450[var11] - field2450[var10]) - (field2450[var9] - field2450[var10]) * (var14 - var13) <= 0.0F;
                  field2443[var8] = var12 < 0.0F || var13 < 0.0F || var14 < 0.0F || var12 > var7 || var13 > var7 || var14 > var7;
                  if (var2
                     && classDG.method3086(
                        (int)field2450[var9], (int)field2450[var10], (int)field2450[var11], (int)var12, (int)var13, (int)var14, var30, -2043306261
                     )) {
                     int var35 = (int)(field2448[var9] + field2448[var10] + field2448[var11]) / 3;
                     classGI.method5479(var4, var35, -1325273292);
                  }
               }
            }
         }

         short var31 = this.field2497;
         boolean var32 = false;
         if (classFH.field2255.field2213.method4857()) {
            this.field2497 = (short)this.field2417;
         } else if (this.field2420 != null && this.field2420.method4857()) {
            classFH.field2255.field2213.method4843(this.field2420);
            var32 = true;
         }

         try {
            if (!classFH.method4755()) {
               classMO.method7679(this, true);
            } else {
               classFZ var33 = this.field2421;
               if (var33 == classFZ.field2513) {
                  if (classFH.field2255.field2215 != 0 && this.field2442 != null) {
                     var33 = classFZ.field2514;
                  } else {
                     var33 = classFZ.field2516;
                  }
               }

               switch (var33.field2512) {
                  case 1:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     classMO.method7679(this, false);
                     break;
                  case 2:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = 0;
                     classMO.method7679(this, true);
                     classFH.field2255.field2208 = 0;
                     classFH.field2255.field2214 = -1;
                     classMO.method7679(this, true);
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     break;
                  case 3:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     method5207(this);
                     break;
                  case 4:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = 0;
                     method5207(this);
               }
            }
         } finally {
            this.field2497 = var31;
            if (var32) {
               classFL.method4856(classFH.field2255.field2213);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ig")
   public static void method5246(int var0) {
      assert classOE.field4841.isClientThread();

      rl21 var1 = rl21.field5726;
      var1.field5730 = var1.field5731 = var1.field5732 = 0;
      var1.field5727 = var1.field5729 = var1.field5728 = 0.0F;
   }

   @ObfuscatedSignature(descriptor = "(ZLfx;[B)Lfx;")
   @ObfuscatedName("cm")
   Model method5113(boolean var1, Model var2, byte[] var3) {
      var2.field2422 = this.field2422;
      var2.field2417 = this.field2417;
      var2.field2416 = this.field2416;
      if (var2.field2425 == null || var2.field2425.length < this.field2422) {
         var2.field2425 = new float[this.field2422 + 1497599298];
         var2.field2428 = new float[this.field2422 + 100];
         var2.field2424 = new float[this.field2422 + 100];
      }

      for (int var4 = 0; var4 < this.field2422; var4++) {
         var2.field2425[var4] = this.field2425[var4];
         var2.field2428[var4] = this.field2428[var4];
         var2.field2424[var4] = this.field2424[var4];
      }

      if (var1) {
         var2.field2442 = this.field2442;
      } else {
         var2.field2442 = var3;
         if (this.field2442 == null) {
            for (int var5 = 0; var5 < this.field2417; var5++) {
               var2.field2442[var5] = 0;
            }
         } else {
            for (int var6 = 0; var6 < this.field2417; var6++) {
               var2.field2442[var6] = this.field2442[var6];
            }
         }
      }

      var2.field2427 = this.field2427;
      var2.field2430 = this.field2430;
      var2.field2431 = this.field2431;
      var2.field2432 = this.field2432;
      var2.field2426 = this.field2426;
      var2.field2433 = this.field2433;
      var2.field2429 = this.field2429;
      var2.field2434 = this.field2434;
      var2.field2435 = this.field2435;
      var2.field2436 = this.field2436;
      var2.field2418 = this.field2418;
      var2.field2437 = this.field2437;
      var2.field2438 = this.field2438;
      var2.field2439 = this.field2439;
      var2.field2440 = this.field2440;
      var2.field2441 = this.field2441;
      var2.field2506 = this.field2506;
      var2.field2505 = this.field2505;
      var2.field2419 = this.field2419;
      var2.field2420 = this.field2420;
      var2.field2497 = this.field2497;
      var2.field2423 = this.field2423;
      var2.method5129();
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lem;Lsn;I[ZZZ)V")
   @ObfuscatedName("ek")
   public void method5150(classEM var1, classSN var2, int var3, boolean[] var4, boolean var5, boolean var6) {
      classEF var7 = var1.method3948((byte)-21);
      if (var7 != null) {
         var7.method3886(var2, var3, var4, var5, (byte)123);
         if (var6) {
            this.method5143(var7, var2.method10559(912562354));
         }
      }

      if (!var5 && var2.method10562(286256297)) {
         this.method5267(var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   public void method5186() {
      for (int var1 = 0; var1 < this.field2422; var1++) {
         this.field2425[var1] = -this.field2425[var1];
         this.field2424[var1] = -this.field2424[var1];
      }

      this.method5294();
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ba")
   public void method5172(int var1) {
      int var2 = field2463[var1];
      int var3 = field2471[var1];

      for (int var4 = 0; var4 < this.field2422; var4++) {
         int var5 = (int)this.field2428[var4] * var3 - (int)this.field2424[var4] * var2 >> 16;
         this.field2424[var4] = (int)this.field2428[var4] * var2 + (int)this.field2424[var4] * var3 >> 16;
         this.field2428[var4] = var5;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(Lfz;)V")
   @ObfuscatedName("yx")
   public void method5283(classFZ var1) {
      this.method5243(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;Lfs;I)V")
   @ObfuscatedName("xl")
   public static void method5134(Model var0, classFS var1, int var2) {
      if (var0 == null) {
         var0.getFaceIndices3();
      }

      if (var0.field2440 != null) {
         if (var2 != -1) {
            classET var3 = var1.field2390[var2];
            classEM var4 = var3.field1909;
            field2484 = 0;
            field2486 = 0;
            field2485 = 0;

            for (int var5 = 0; var5 < var3.field1911; var5++) {
               int var6 = var3.field1910[var5];
               method5260(var0, var4.field1783[var6], var4.field1785[var6], var3.field1907[var5], var3.field1913[var5], var3.field1912[var5]);
            }

            var0.method5129();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("bw")
   public void method5248(classUL var1) {
      classUZ var2 = classUZ.method11860(0.0F, 0.0F, 0.0F);

      for (int var3 = 0; var3 < this.field2422; var3++) {
         var2.method11858(this.field2425[var3], this.field2428[var3], this.field2424[var3]);
         var2.method11862(var1);
         this.field2425[var3] = var2.field6425;
         this.field2428[var3] = var2.field6423;
         this.field2424[var3] = var2.field6424;
      }

      var2.method11861();
      this.method5294();
   }

   @ObfuscatedSignature(descriptor = "(Lfx;ILuu;)V")
   @ObfuscatedName("hk")
   public static void method5274(Model var0, int var1, classUU var2) {
      if (var0 == null) {
         var0.method5275(var1, var2);
      } else {
         float var3 = var0.field2425[var1];
         float var4 = -var0.field2428[var1];
         float var5 = -var0.field2424[var1];
         float var6 = 1.0F;
         var0.field2425[var1] = var2.field6383 * var3 + var2.field6392 * var4 + var2.field6388 * var5 + var2.field6391 * var6;
         var0.field2428[var1] = -(var2.field6398 * var3 + var2.field6384 * var4 + var2.field6389 * var5 + var2.field6399 * var6);
         var0.field2424[var1] = -(var2.field6394 * var3 + var2.field6396 * var4 + var2.field6385 * var5 + var2.field6395 * var6);
      }
   }

   public int[] getFaceColors1() {
      return this.field2432;
   }

   public float[] getVerticesZ() {
      return this.field2424;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bb")
   public void method5250(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2422; var4++) {
         this.field2425[var4] = this.field2425[var4] + var1;
         this.field2428[var4] = this.field2428[var4] + var2;
         this.field2424[var4] = this.field2424[var4] + var3;
      }

      this.method5294();
   }

   public void setUvBufferOffset(int var1) {
      this.field2492 = var1;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bi")
   public void method5251(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2422; var4++) {
         this.field2425[var4] = var1 * this.field2425[var4] / 128.0F;
         this.field2428[var4] = var2 * this.field2428[var4] / 128.0F;
         this.field2424[var4] = var3 * this.field2424[var4] / 128.0F;
      }

      this.method5294();
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("be")
   public final void method5269(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      try {
         int var16 = var7;
         int var15 = var6;
         int var14 = var5;
         int var12 = var3;
         int var11 = var2;
         int var10 = var1;
         Model var9 = this;
         if (this.field2491 != 2) {
            this.method5263();
         }

         int var17 = classFH.method4725();
         int var18 = classFH.method4730();
         int var19 = field2463[var1];
         int var20 = field2471[var1];
         int var21 = field2463[var2];
         int var22 = field2471[var2];
         int var23 = field2463[var3];
         int var24 = field2471[var3];
         int var25 = field2463[var4];
         int var26 = field2471[var4];
         int var27 = var6 * var25 + var7 * var26 >> 16;

         for (int var28 = 0; var28 < var9.field2422; var28++) {
            int var29 = (int)var9.field2425[var28];
            int var30 = (int)var9.field2428[var28];
            int var31 = (int)var9.field2424[var28];
            if (var12 != 0) {
               int var32 = var30 * var23 + var29 * var24 >> 16;
               var30 = var30 * var24 - var29 * var23 >> 16;
               var29 = var32;
            }

            if (var10 != 0) {
               int var39 = var30 * var20 - var31 * var19 >> 16;
               var31 = var30 * var19 + var31 * var20 >> 16;
               var30 = var39;
            }

            if (var11 != 0) {
               int var40 = var31 * var21 + var29 * var22 >> 16;
               var31 = var31 * var22 - var29 * var21 >> 16;
               var29 = var40;
            }

            var29 += var14;
            var30 += var15;
            var31 += var16;
            int var41 = var30 * var26 - var31 * var25 >> 16;
            var31 = var30 * var25 + var31 * var26 >> 16;
            field2449[var28] = var31 - var27;
            field2464[var28] = var17 + var29 * classFH.method4747() / var31;
            field2450[var28] = var18 + var41 * classFH.method4747() / var31;
            field2448[var28] = var31;
            if (var9.field2435 != null) {
               field2468[var28] = var29;
               field2451[var28] = var41;
               field2474[var28] = var31;
            }
         }

         try {
            var9.method5202(false, false, false, 0L);
         } catch (Exception var33) {
         }
      } catch (Exception var34) {
         client.field1028.debug("failed drawing model", var34);
      }
   }

   @ObfuscatedSignature(descriptor = "(I[IIII)V")
   @ObfuscatedName("ed")
   void method5154(int var1, int[] var2, int var3, int var4, int var5) {
      int var6 = var2.length;
      if (var1 == 0) {
         int var21 = 0;
         field2484 = 0;
         field2486 = 0;
         field2485 = 0;

         for (int var25 = 0; var25 < var6; var25++) {
            int var29 = var2[var25];
            if (var29 < this.field2440.length) {
               int[] var33 = this.field2440[var29];

               for (int var37 = 0; var37 < var33.length; var37++) {
                  int var39 = var33[var37];
                  field2484 = field2484 + (int)this.field2425[var39];
                  field2486 = field2486 + (int)this.field2428[var39];
                  field2485 = field2485 + (int)this.field2424[var39];
                  var21++;
               }
            }
         }

         if (var21 > 0) {
            field2484 = field2484 / var21 + var3;
            field2486 = field2486 / var21 + var4;
            field2485 = field2485 / var21 + var5;
         } else {
            field2484 = var3;
            field2486 = var4;
            field2485 = var5;
         }
      } else if (var1 == 1) {
         for (int var20 = 0; var20 < var6; var20++) {
            int var24 = var2[var20];
            if (var24 < this.field2440.length) {
               int[] var28 = this.field2440[var24];

               for (int var32 = 0; var32 < var28.length; var32++) {
                  int var36 = var28[var32];
                  this.field2425[var36] = (int)this.field2425[var36] + var3;
                  this.field2428[var36] = (int)this.field2428[var36] + var4;
                  this.field2424[var36] = (int)this.field2424[var36] + var5;
               }
            }
         }
      } else if (var1 == 2) {
         for (int var19 = 0; var19 < var6; var19++) {
            int var23 = var2[var19];
            if (var23 < this.field2440.length) {
               int[] var27 = this.field2440[var23];

               for (int var31 = 0; var31 < var27.length; var31++) {
                  int var35 = var27[var31];
                  this.field2425[var35] = (int)this.field2425[var35] - field2484;
                  this.field2428[var35] = (int)this.field2428[var35] - field2486;
                  this.field2424[var35] = (int)this.field2424[var35] - field2485;
                  int var38 = (var3 & 1124927192) * 8;
                  int var13 = (var4 & 0xFF) * 8;
                  int var14 = (var5 & 0xFF) * 8;
                  if (var14 != 0) {
                     int var15 = field2463[var14];
                     int var16 = field2471[var14];
                     int var17 = (int)this.field2428[var35] * var15 + (int)this.field2425[var35] * var16 >> 16;
                     this.field2428[var35] = (int)this.field2428[var35] * var16 - (int)this.field2425[var35] * var15 >> 16;
                     this.field2425[var35] = var17;
                  }

                  if (var38 != 0) {
                     int var40 = field2463[var38];
                     int var42 = field2471[var38];
                     int var44 = (int)this.field2428[var35] * var42 - (int)this.field2424[var35] * var40 >> 16;
                     this.field2424[var35] = (int)this.field2428[var35] * var40 + (int)this.field2424[var35] * var42 >> 16;
                     this.field2428[var35] = var44;
                  }

                  if (var13 != 0) {
                     int var41 = field2463[var13];
                     int var43 = field2471[var13];
                     int var45 = (int)this.field2424[var35] * var41 + (int)this.field2425[var35] * var43 >> 16;
                     this.field2424[var35] = (int)this.field2424[var35] * var43 - (int)this.field2425[var35] * var41 >> 16;
                     this.field2425[var35] = var45;
                  }

                  this.field2425[var35] = (int)this.field2425[var35] + field2484;
                  this.field2428[var35] = (int)this.field2428[var35] + field2486;
                  this.field2424[var35] = (int)this.field2424[var35] + field2485;
               }
            }
         }
      } else if (var1 == 3) {
         for (int var18 = 0; var18 < var6; var18++) {
            int var22 = var2[var18];
            if (var22 < this.field2440.length) {
               int[] var26 = this.field2440[var22];

               for (int var30 = 0; var30 < var26.length; var30++) {
                  int var34 = var26[var30];
                  this.field2425[var34] = (int)this.field2425[var34] - field2484;
                  this.field2428[var34] = (int)this.field2428[var34] - field2486;
                  this.field2424[var34] = (int)this.field2424[var34] - field2485;
                  this.field2425[var34] = (int)this.field2425[var34] * var3 / -1716346759;
                  this.field2428[var34] = (int)this.field2428[var34] * var4 / 1756641002;
                  this.field2424[var34] = (int)this.field2424[var34] * var5 / 128;
                  this.field2425[var34] = (int)this.field2425[var34] + field2484;
                  this.field2428[var34] = (int)this.field2428[var34] + field2486;
                  this.field2424[var34] = (int)this.field2424[var34] + field2485;
               }
            }
         }
      } else if (var1 == 5) {
         if (this.field2441 != null && this.field2442 != null) {
            for (int var7 = 0; var7 < var6; var7++) {
               int var8 = var2[var7];
               if (var8 < this.field2441.length) {
                  int[] var9 = this.field2441[var8];

                  for (int var10 = 0; var10 < var9.length; var10++) {
                     int var11 = var9[var10];
                     int var12 = (this.field2442[var11] & 255) + var3 * 8;
                     if (var12 < 0) {
                        var12 = 0;
                     } else if (var12 > 255) {
                        var12 = -2034739518;
                     }

                     this.field2442[var11] = (byte)var12;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("lx")
   @Override
   void vmethod415(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      this.method5192(var1, var2, var3, var4 - var7, var5 - var8, var6 - var9, var10);
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("ei")
   public void method5174(classUL var1) {
      classUZ var2 = classHZ.method6171(16777216);

      for (int var3 = 0; var3 < this.field2422; var3++) {
         classUZ.method11802(var2, (int)this.field2425[var3], (int)this.field2428[var3], (int)this.field2424[var3], 2115498524);
         var2.method11849(var1, -1949319718);
         this.field2425[var3] = var2.field6425;
         this.field2428[var3] = var2.field6423;
         this.field2424[var3] = var2.field6424;
      }

      var2.method11797(1774614733);
      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(I)Lfp;")
   @ObfuscatedName("jg")
   public AABB method5261(int var1) {
      this.calculateExtreme(var1);
      AABB var2 = this.method5285(var1);

      assert var2 != null;

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(ILuu;IIIJ)V")
   @ObfuscatedName("mu")
   @Override
   void vmethod419(int var1, classUU var2, int var3, int var4, int var5, long var6) {
      if (classFH.field2255.field2215 != 1 || this.field2442 != null) {
         if (this.field2491 != 1) {
            method5254(this);
         }

         this.method5117(var1);
         classUG var8 = classMQ.method7682(-1562594477);
         var8.field6303.method11437(var1 * 0.0030679614F, 0.0F, 0.0F, 996505715);
         classUZ.method11802(var8.field6304, 1.0F, 1.0F, 1.0F, 1881271103);
         classUZ.method11802(var8.field6305, var3, var4, var5, 2067038293);
         classUU var9 = classRM.method10172(1778071523);
         var9.method11706(var8, 1963133204);
         var9.method11656(var2, 802951699);
         int var10 = (int)var9.method11689(0.0F, 0.0F, 0.0F, -1839417560);
         boolean var11 = false;
         int var12 = var10 - this.field2487;
         boolean var13 = var12 <= 50 || this.field2435 != null;
         boolean var15 = var6 != 0L;
         if (var15) {
            boolean var16 = (int)(var6 >>> 19 & 1L) == 1;
            var15 = !var16;
         }

         boolean var30 = false;
         if (var15 && classAS.method657(2120718781)) {
            int var19 = classFH.method4725();
            int var20 = classFH.method4730();
            int var21 = classFH.method4747();
            boolean var22 = classFO.field2350;
            boolean var17;
            if (!var22) {
               var17 = false;
            } else {
               classVL.method12067(var2, var19, var20, var21, 2082928416);
               var17 = GrandExchangeOffer.method7660(
                  this, var1, var3 - -1365423377 * classFM.field2313, var4 - classCP.field1200 * 1448483213, var5 - -417087201 * classVR.field6558, (byte)32
               );
            }

            if (var17) {
               if (this.field2419) {
                  int var25 = classFH.method4725();
                  int var26 = classFH.method4730();
                  int var27 = classFH.method4747();
                  classVL.method12067(var2, var25, var26, var27, 678266870);
                  int var23 = classSZ.method10772(
                     var3 - classFM.field2313 * -1365423377,
                     var4 - 1448483213 * classCP.field1200,
                     var5 - -417087201 * classVR.field6558,
                     -1163968665 * client.field1069,
                     classDX.field1660 * -639123865,
                     classDU.field1631 * -1674330987,
                     classDB.field1373 * 1788691143,
                     1768347607 * classYJ.field6992,
                     World.field1163 * -1569185711,
                     this.method5118(var1),
                     -474044496
                  );
                  int var28 = var23 + 32;
                  classGI.method5479(var6, var28, -1753576533);
               } else {
                  var30 = true;
               }
            }
         }

         int var31 = classFH.method4725();
         int var18 = classFH.method4730();

         for (int var32 = 0; var32 < this.field2422; var32++) {
            float var33 = (int)this.field2425[var32];
            float var35 = (int)this.field2428[var32];
            float var37 = (int)this.field2424[var32];
            var9.method11677(var33, var35, var37, field2444, 1634822735);
            var33 = field2444[0];
            var35 = field2444[1];
            var37 = field2444[2];
            var11 |= method5198(this, var32, var33, var35, var37, var10, var31, var18, var13);
         }

         var8.method11364(182636645);
         var9.method11615(1862795037);

         try {
            this.method5202(var11, var30, this.field2419, var6);
         } catch (Exception var29) {
         }
      }
   }

   public byte[] getFaceBias() {
      return this.field2436;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("qx")
   public void method5249(Model var1) {
      this.field2501 = var1.field2501;
      this.field2503 = var1.field2503;
      this.field2502 = var1.field2502;
      this.field2504 = var1.field2504;
   }

   public short[] getUnlitFaceColors() {
      return this.field2504;
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("mk")
   @Override
   void vmethod416(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      this.method5192(var1, var2, var3, var4 - var7, var5 - var8, var6 - var9, var10);
   }

   public byte getOverrideLuminance() {
      return this.field2420 != null ? this.field2420.field2300 : 0;
   }

   @ObfuscatedSignature(descriptor = "(I[IIII)V")
   @ObfuscatedName("ej")
   void method5155(int var1, int[] var2, int var3, int var4, int var5) {
      int var6 = var2.length;
      if (var1 == 0) {
         int var21 = 0;
         field2484 = 0;
         field2486 = 0;
         field2485 = 0;

         for (int var25 = 0; var25 < var6; var25++) {
            int var29 = var2[var25];
            if (var29 < this.field2440.length) {
               int[] var33 = this.field2440[var29];

               for (int var37 = 0; var37 < var33.length; var37++) {
                  int var39 = var33[var37];
                  field2484 = field2484 + (int)this.field2425[var39];
                  field2486 = field2486 + (int)this.field2428[var39];
                  field2485 = field2485 + (int)this.field2424[var39];
                  var21++;
               }
            }
         }

         if (var21 > 0) {
            field2484 = field2484 / var21 + var3;
            field2486 = field2486 / var21 + var4;
            field2485 = field2485 / var21 + var5;
         } else {
            field2484 = var3;
            field2486 = var4;
            field2485 = var5;
         }
      } else if (var1 == 1) {
         for (int var20 = 0; var20 < var6; var20++) {
            int var24 = var2[var20];
            if (var24 < this.field2440.length) {
               int[] var28 = this.field2440[var24];

               for (int var32 = 0; var32 < var28.length; var32++) {
                  int var36 = var28[var32];
                  this.field2425[var36] = (int)this.field2425[var36] + var3;
                  this.field2428[var36] = (int)this.field2428[var36] + var4;
                  this.field2424[var36] = (int)this.field2424[var36] + var5;
               }
            }
         }
      } else if (var1 == 2) {
         for (int var19 = 0; var19 < var6; var19++) {
            int var23 = var2[var19];
            if (var23 < this.field2440.length) {
               int[] var27 = this.field2440[var23];

               for (int var31 = 0; var31 < var27.length; var31++) {
                  int var35 = var27[var31];
                  this.field2425[var35] = (int)this.field2425[var35] - field2484;
                  this.field2428[var35] = (int)this.field2428[var35] - field2486;
                  this.field2424[var35] = (int)this.field2424[var35] - field2485;
                  int var38 = (var3 & 0xFF) * 8;
                  int var13 = (var4 & 0xFF) * 8;
                  int var14 = (var5 & 593523257) * 8;
                  if (var14 != 0) {
                     int var15 = field2463[var14];
                     int var16 = field2471[var14];
                     int var17 = (int)this.field2428[var35] * var15 + (int)this.field2425[var35] * var16 >> 16;
                     this.field2428[var35] = (int)this.field2428[var35] * var16 - (int)this.field2425[var35] * var15 >> 16;
                     this.field2425[var35] = var17;
                  }

                  if (var38 != 0) {
                     int var40 = field2463[var38];
                     int var42 = field2471[var38];
                     int var44 = (int)this.field2428[var35] * var42 - (int)this.field2424[var35] * var40 >> 16;
                     this.field2424[var35] = (int)this.field2428[var35] * var40 + (int)this.field2424[var35] * var42 >> 16;
                     this.field2428[var35] = var44;
                  }

                  if (var13 != 0) {
                     int var41 = field2463[var13];
                     int var43 = field2471[var13];
                     int var45 = (int)this.field2424[var35] * var41 + (int)this.field2425[var35] * var43 >> 16;
                     this.field2424[var35] = (int)this.field2424[var35] * var43 - (int)this.field2425[var35] * var41 >> 16;
                     this.field2425[var35] = var45;
                  }

                  this.field2425[var35] = (int)this.field2425[var35] + field2484;
                  this.field2428[var35] = (int)this.field2428[var35] + field2486;
                  this.field2424[var35] = (int)this.field2424[var35] + field2485;
               }
            }
         }
      } else if (var1 == 3) {
         for (int var18 = 0; var18 < var6; var18++) {
            int var22 = var2[var18];
            if (var22 < this.field2440.length) {
               int[] var26 = this.field2440[var22];

               for (int var30 = 0; var30 < var26.length; var30++) {
                  int var34 = var26[var30];
                  this.field2425[var34] = (int)this.field2425[var34] - field2484;
                  this.field2428[var34] = (int)this.field2428[var34] - field2486;
                  this.field2424[var34] = (int)this.field2424[var34] - field2485;
                  this.field2425[var34] = (int)this.field2425[var34] * var3 / 128;
                  this.field2428[var34] = (int)this.field2428[var34] * var4 / 128;
                  this.field2424[var34] = (int)this.field2424[var34] * var5 / 128;
                  this.field2425[var34] = (int)this.field2425[var34] + field2484;
                  this.field2428[var34] = (int)this.field2428[var34] + field2486;
                  this.field2424[var34] = (int)this.field2424[var34] + field2485;
               }
            }
         }
      } else if (var1 == 5) {
         if (this.field2441 != null && this.field2442 != null) {
            for (int var7 = 0; var7 < var6; var7++) {
               int var8 = var2[var7];
               if (var8 < this.field2441.length) {
                  int[] var9 = this.field2441[var8];

                  for (int var10 = 0; var10 < var9.length; var10++) {
                     int var11 = var9[var10];
                     int var12 = (this.field2442[var11] & -671553839) + var3 * 8;
                     if (var12 < 0) {
                        var12 = 0;
                     } else if (var12 > 255) {
                        var12 = 255;
                     }

                     this.field2442[var11] = (byte)var12;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bv")
   void method5210() {
      for (int var1 = 0; var1 < 12; var1++) {
         field2455[var1] = 0;
         field2478[var1] = 0;
      }

      for (int var13 = this.field2489 - 1; var13 >= 0; var13--) {
         char var2 = field2453[var13];
         if (var2 > 0) {
            char[] var3 = field2454[var13];

            for (int var4 = 0; var4 < var2; var4++) {
               char var5 = var3[var4];
               byte var6 = this.field2429[var5];
               int var7 = field2455[var6]++;
               field2456[var6][var7] = var5;
               if (var6 < 10) {
                  field2478[var6] = field2478[var6] + var13;
               } else if (var6 == 10) {
                  field2457[var7] = var13;
               } else {
                  field2465[var7] = var13;
               }
            }
         }
      }

      int var14 = 0;
      if (field2455[1] > 0 || field2455[2] > 0) {
         var14 = (field2478[1] + field2478[2]) / (field2455[1] + field2455[2]);
      }

      int var15 = 0;
      if (field2455[3] > 0 || field2455[4] > 0) {
         var15 = (field2478[3] + field2478[4]) / (field2455[3] + field2455[4]);
      }

      int var16 = 0;
      if (field2455[6] > 0 || field2455[8] > 0) {
         var16 = (field2478[6] + field2478[8]) / (field2455[6] + field2455[8]);
      }

      int var18 = 0;
      int var19 = field2455[10];
      int[] var20 = field2456[10];
      int[] var8 = field2457;
      if (var18 == var19) {
         var18 = 0;
         var19 = field2455[11];
         var20 = field2456[11];
         var8 = field2465;
      }

      int var17;
      if (var18 < var19) {
         var17 = var8[var18];
      } else {
         var17 = -1000;
      }

      for (int var9 = 0; var9 < 10; var9++) {
         while (var9 == 0 && var17 > var14) {
            this.method5217(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2455[11];
               var20 = field2456[11];
               var8 = field2465;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         while (var9 == 3 && var17 > var15) {
            this.method5217(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2455[11];
               var20 = field2456[11];
               var8 = field2465;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         while (var9 == 5 && var17 > var16) {
            this.method5217(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2455[11];
               var20 = field2456[11];
               var8 = field2465;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         int var10 = field2455[var9];
         int[] var11 = field2456[var9];

         for (int var12 = 0; var12 < var10; var12++) {
            this.method5217(var11[var12]);
         }
      }

      while (var17 != -1000) {
         this.method5217(var20[var18++]);
         if (var18 == var19 && var20 != field2456[11]) {
            var18 = 0;
            var20 = field2456[11];
            var19 = field2455[11];
            var8 = field2465;
         }

         if (var18 < var19) {
            var17 = var8[var18];
         } else {
            var17 = -1000;
         }
      }
   }

   public float[] getVerticesX() {
      return this.field2425;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("fa")
   int method5214(int var1) {
      if (this.field2423 == -1) {
         return 253;
      } else {
         int var2 = this.field2423 & 255;
         if (var2 > 0 && var1 < 253) {
            int var3 = 253 - var1;
            int var4 = var3 * var2 >> 8;
            return var1 + var4;
         } else {
            return var1;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;III)V")
   @ObfuscatedName("hp")
   public static void method5094(Model var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method5097(var1, var1, var1);
      }

      var0.field2425 = new float[var1];
      var0.field2428 = new float[var1];
      var0.field2424 = new float[var1];
      var0.field2427 = new int[var2];
      var0.field2430 = new int[var2];
      var0.field2431 = new int[var2];
      var0.field2432 = new int[var2];
      var0.field2426 = new int[var2];
      var0.field2433 = new int[var2];
      if (var3 > 0) {
         var0.field2437 = new int[var3];
         var0.field2438 = new int[var3];
         var0.field2439 = new int[var3];
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   void method5263() {
      if (this.field2491 != 2) {
         this.field2491 = 2;
         float var1 = 0.0F;

         for (int var2 = 0; var2 < this.field2422; var2++) {
            float var3 = this.field2425[var2];
            float var4 = this.field2428[var2];
            float var5 = this.field2424[var2];
            float var6 = var3 * var3 + var5 * var5 + var4 * var4;
            if (var6 > var1) {
               var1 = var6;
            }
         }

         this.field2488 = (int)Math.ceil(Math.sqrt(var1));
         this.field2487 = this.field2488;
         this.field2489 = this.field2488 + this.field2488;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("pl")
   public static void method5122(Model var0) {
      if (var0.field2491 != 1) {
         var0.field2491 = 1;
         var0.field1741 = 0;
         var0.field2490 = 0;
         var0.field2488 = 0;

         for (int var1 = 0; var1 < var0.field2422; var1++) {
            int var2 = (int)var0.field2425[var1];
            int var3 = (int)var0.field2428[var1];
            int var4 = (int)var0.field2424[var1];
            if (-var3 > var0.field1741 * -1256242689) {
               var0.field1741 = -var3 * 903659007;
            }

            if (var3 > var0.field2490) {
               var0.field2490 = var3;
            }

            int var5 = var2 * var2 + var4 * var4;
            if (var5 > var0.field2488) {
               var0.field2488 = var5;
            }
         }

         var0.field2488 = (int)(Math.sqrt(var0.field2488) + 0.99);
         var0.field2487 = (int)(Math.sqrt(var0.field2488 * var0.field2488 + var0.field1741 * -1256242689 * var0.field1741 * -1256242689) + 0.99);
         var0.field2489 = var0.field2487 + (int)(Math.sqrt(var0.field2488 * var0.field2488 + var0.field2490 * var0.field2490) + 0.99);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("cr")
   boolean method5219(int var1) {
      return var1 < this.field2497;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cb")
   final void method5224(int var1) {
      int var2 = classFH.method4725();
      int var3 = classFH.method4730();
      int var4 = 0;
      int var5 = this.field2427[var1];
      int var6 = this.field2430[var1];
      int var7 = this.field2431[var1];
      int var8 = field2474[var5];
      int var9 = field2474[var6];
      int var10 = field2474[var7];
      if (var8 >= 50) {
         field2476[var4] = (int)field2464[var5];
         field2477[var4] = (int)field2450[var5];
         field2452[var4] = field2448[var5];
         field2480[var4++] = this.field2432[var1];
      } else {
         int var11 = field2468[var5];
         int var12 = field2451[var5];
         int var13 = this.field2432[var1];
         if (var10 >= 50) {
            int var14 = (50 - var8) * field2467[var10 - var8];
            field2476[var4] = var2 + (var11 + ((field2468[var7] - var11) * var14 >> 16)) * classFH.method4747() / 50;
            field2477[var4] = var3 + (var12 + ((field2451[var7] - var12) * var14 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var13 + ((this.field2433[var1] - var13) * var14 >> 16);
         }

         if (var9 >= 50) {
            int var35 = (50 - var8) * field2467[var9 - var8];
            field2476[var4] = var2 + (var11 + ((field2468[var6] - var11) * var35 >> 16)) * classFH.method4747() / 50;
            field2477[var4] = var3 + (var12 + ((field2451[var6] - var12) * var35 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var13 + ((this.field2426[var1] - var13) * var35 >> 16);
         }
      }

      if (var9 >= 50) {
         field2476[var4] = (int)field2464[var6];
         field2477[var4] = (int)field2450[var6];
         field2452[var4] = field2448[var6];
         field2480[var4++] = this.field2426[var1];
      } else {
         int var26 = field2468[var6];
         int var29 = field2451[var6];
         int var32 = this.field2426[var1];
         if (var8 >= 50) {
            int var36 = (50 - var9) * field2467[var8 - var9];
            field2476[var4] = var2 + (var26 + ((field2468[var5] - var26) * var36 >> 16)) * classFH.method4747() / 50;
            field2477[var4] = var3 + (var29 + ((field2451[var5] - var29) * var36 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var32 + ((this.field2432[var1] - var32) * var36 >> 16);
         }

         if (var10 >= 50) {
            int var37 = (50 - var9) * field2467[var10 - var9];
            field2476[var4] = var2 + (var26 + ((field2468[var7] - var26) * var37 >> 16)) * classFH.method4747() / 50;
            field2477[var4] = var3 + (var29 + ((field2451[var7] - var29) * var37 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var32 + ((this.field2433[var1] - var32) * var37 >> 16);
         }
      }

      if (var10 >= 50) {
         field2476[var4] = (int)field2464[var7];
         field2477[var4] = (int)field2450[var7];
         field2452[var4] = field2448[var7];
         field2480[var4++] = this.field2433[var1];
      } else {
         int var27 = field2468[var7];
         int var30 = field2451[var7];
         int var33 = this.field2433[var1];
         if (var9 >= 50) {
            int var38 = (50 - var10) * field2467[var9 - var10];
            field2476[var4] = var2 + (var27 + ((field2468[var6] - var27) * var38 >> 16)) * classFH.method4747() / 50;
            field2477[var4] = var3 + (var30 + ((field2451[var6] - var30) * var38 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var33 + ((this.field2426[var1] - var33) * var38 >> 16);
         }

         if (var8 >= 50) {
            int var39 = (50 - var10) * field2467[var8 - var10];
            field2476[var4] = var2 + (var27 + ((field2468[var5] - var27) * var39 >> 16)) * classFH.method4747() / 50;
            field2477[var4] = var3 + (var30 + ((field2451[var5] - var30) * var39 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var33 + ((this.field2432[var1] - var33) * var39 >> 16);
         }
      }

      int var28 = field2476[0];
      int var31 = field2476[1];
      int var34 = field2476[2];
      int var40 = field2477[0];
      int var15 = field2477[1];
      int var16 = field2477[2];
      int var17 = this.field2436 == null ? 0 : this.field2436[var1] * 2;
      float var18 = classJE.method6398(field2452[0] - var17, 2136660051);
      float var19 = classJE.method6398(field2452[1] - var17, 2127606892);
      float var20 = classJE.method6398(field2452[2] - var17, 2086644271);
      classFH.field2255.field2209 = false;
      int var21 = classFH.method4751();
      if (var4 == 3) {
         if (var28 < 0 || var31 < 0 || var34 < 0 || var28 > var21 || var31 > var21 || var34 > var21) {
            classFH.field2255.field2209 = true;
         }

         method5221(this, var1, var40, var15, var16, var28, var31, var34, var18, var19, var20, field2480[0], field2480[1], field2480[2]);
      }

      if (var4 == 4) {
         if (var28 < 0 || var31 < 0 || var34 < 0 || var28 > var21 || var31 > var21 || var34 > var21 || field2476[3] < 0 || field2476[3] > var21) {
            classFH.field2255.field2209 = true;
         }

         if (this.field2435 != null && this.field2435[var1] != -1) {
            int var24;
            int var41;
            int var43;
            if (this.field2434 != null && this.field2434[var1] != -1) {
               int var25 = this.field2434[var1] & 255;
               var41 = this.field2437[var25];
               var43 = this.field2438[var25];
               var24 = this.field2439[var25];
            } else {
               var41 = var5;
               var43 = var6;
               var24 = var7;
            }

            short var44 = this.field2435[var1];
            if (this.field2433[var1] == -1) {
               classFH.method4787(
                  var40,
                  var15,
                  var16,
                  var28,
                  var31,
                  var34,
                  var18,
                  var19,
                  var20,
                  this.field2432[var1],
                  this.field2432[var1],
                  this.field2432[var1],
                  field2468[var41],
                  field2468[var43],
                  field2468[var24],
                  field2451[var41],
                  field2451[var43],
                  field2451[var24],
                  field2474[var41],
                  field2474[var43],
                  field2474[var24],
                  var44
               );
               classFH.method4787(
                  var40,
                  var16,
                  field2477[3],
                  var28,
                  var34,
                  field2476[3],
                  var18,
                  var20,
                  field2452[3],
                  this.field2432[var1],
                  this.field2432[var1],
                  this.field2432[var1],
                  field2468[var41],
                  field2468[var43],
                  field2468[var24],
                  field2451[var41],
                  field2451[var43],
                  field2451[var24],
                  field2474[var41],
                  field2474[var43],
                  field2474[var24],
                  var44
               );
            } else {
               classFH.method4787(
                  var40,
                  var15,
                  var16,
                  var28,
                  var31,
                  var34,
                  var18,
                  var19,
                  var20,
                  field2480[0],
                  field2480[1],
                  field2480[2],
                  field2468[var41],
                  field2468[var43],
                  field2468[var24],
                  field2451[var41],
                  field2451[var43],
                  field2451[var24],
                  field2474[var41],
                  field2474[var43],
                  field2474[var24],
                  var44
               );
               classFH.method4787(
                  var40,
                  var16,
                  field2477[3],
                  var28,
                  var34,
                  field2476[3],
                  var18,
                  var20,
                  field2452[3],
                  field2480[0],
                  field2480[2],
                  field2480[3],
                  field2468[var41],
                  field2468[var43],
                  field2468[var24],
                  field2451[var41],
                  field2451[var43],
                  field2451[var24],
                  field2474[var41],
                  field2474[var43],
                  field2474[var24],
                  var44
               );
            }
         } else {
            boolean var22 = this.method5219(var1);
            if (this.field2433[var1] == -1 && var22) {
               int var42 = field2460[this.field2432[var1]];
               classFH.method4793(var40, var15, var16, var28, var31, var34, var18, var19, var20, var42);
               classFH.method4793(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], var42);
            } else if (this.field2433[var1] == -1) {
               int var23 = field2460[this.field2432[var1]];
               classFH.method4782(var40, var15, var16, var28, var31, var34, var18, var19, var20, var23);
               classFH.method4782(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], var23);
            } else if (var22) {
               classFH.method4792(var40, var15, var16, var28, var31, var34, var18, var19, var20, field2480[0], field2480[1], field2480[2]);
               classFH.method4792(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], field2480[0], field2480[2], field2480[3]);
            } else {
               classFH.method4778(var40, var15, var16, var28, var31, var34, var18, var19, var20, field2480[0], field2480[1], field2480[2]);
               classFH.method4778(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], field2480[0], field2480[2], field2480[3]);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Lfx;")
   @ObfuscatedName("dj")
   public Model method5284(int var1, int var2, int var3) {
      this.method5250(var1, var2, var3);
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;Lef;I)V")
   @ObfuscatedName("gj")
   public void method5264(rl21 var1, classEF var2, int var3) {
      if (this.field2506 != null) {
         for (int var4 = 0; var4 < this.field2422; var4++) {
            int[] var5 = this.field2506[var4];
            if (var5 != null && var5.length != 0) {
               int[] var6 = this.field2505[var4];
               var1.field5723.method11719();

               for (int var7 = 0; var7 < var5.length; var7++) {
                  int var8 = var5[var7];
                  classSG var9 = var2.method3890(var8);
                  if (var9 != null) {
                     float var10 = var6[var7] / 255.0F;
                     var1.field5724.method11723(var10, var10, var10);
                     var1.field5725.method11720(var9.method10469(var1, var3));
                     var1.field5725.method11728(var1.field5724);
                     var1.field5723.method11725(var1.field5725);
                  }
               }

               this.method5286(var4, var1.field5723);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfs;I)V")
   @ObfuscatedName("bn")
   public void method5135(classFS var1, int var2) {
      if (this.field2440 != null) {
         if (var2 != -1) {
            classET var3 = var1.field2390[var2];
            classEM var4 = var3.field1909;
            field2484 = 0;
            field2486 = 0;
            field2485 = 0;
            method5246(-1);

            for (int var5 = 0; var5 < var3.field1911; var5++) {
               int var6 = var3.field1910[var5];
               method5260(this, var4.field1783[var6], var4.field1785[var6], var3.field1907[var5], var3.field1913[var5], var3.field1912[var5]);
            }

            this.method5129();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lef;I)V")
   @ObfuscatedName("cq")
   void method5277(classEF var1, int var2) {
      this.method5264(rl21.field5726, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;Lem;Let;Let;FI)V")
   @ObfuscatedName("ys")
   public void method5257(rl21 var1, classEM var2, classET var3, classET var4, float var5, int var6) {
      if (var4 != null && var5 != 0.0F) {
         int var26 = 0;
         int var27 = 0;

         for (int var9 = 0; var9 < var2.field1784 * 1690200285; var9++) {
            boolean var10 = false;
            if (var26 < var3.field1911 && var3.field1910[var26] == var9) {
               var10 = true;
            }

            boolean var11 = false;
            if (var27 < var4.field1911 && var4.field1910[var27] == var9) {
               var11 = true;
            }

            if (var10 || var11) {
               short var12 = 0;
               int var13 = var2.field1783[var9];
               if (var13 == 3) {
                  var12 = 128;
               }

               int var14 = var12;
               int var15 = var12;
               int var16 = var12;
               if (var10) {
                  var14 = var3.field1907[var26];
                  var15 = var3.field1913[var26];
                  var16 = var3.field1912[var26];
                  var26++;
               }

               int var17 = var12;
               int var18 = var12;
               int var19 = var12;
               if (var11) {
                  var17 = var4.field1907[var27];
                  var18 = var4.field1913[var27];
                  var19 = var4.field1912[var27];
                  var27++;
               }

               float var20;
               float var21;
               float var22;
               if (var13 == 2) {
                  int var23 = var17 - var14 & 0xFF;
                  int var24 = var18 - var15 & 0xFF;
                  int var25 = var19 - var16 & 0xFF;
                  if (var23 >= 128) {
                     var23 -= 256;
                  }

                  if (var24 >= 128) {
                     var24 -= 256;
                  }

                  if (var25 >= 128) {
                     var25 -= 256;
                  }

                  var20 = var14 + var23 * var5 / var6;
                  var21 = var15 + var24 * var5 / var6;
                  var22 = var16 + var25 * var5 / var6;
               } else if (var13 == 5) {
                  var20 = var14;
                  var21 = 0.0F;
                  var22 = 0.0F;
               } else {
                  var20 = var14 + (var17 - var14) * var5 / var6;
                  var21 = var15 + (var18 - var15) * var5 / var6;
                  var22 = var16 + (var19 - var16) * var5 / var6;
               }

               this.method5256(var1, var13, var2.field1785[var9], var20, var21, var22);
            }
         }
      } else {
         for (int var7 = 0; var7 < var3.field1911; var7++) {
            int var8 = var3.field1910[var7];
            this.method5256(var1, var2.field1783[var8], var2.field1785[var8], var3.field1907[var7], var3.field1913[var7], var3.field1912[var7]);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;Lsn;I)V")
   @ObfuscatedName("gs")
   public void method5252(rl21 var1, classSN var2, int var3) {
      classEM var4 = var2.field5981;
      classEF var5 = var4.field1786;
      if (var5 != null) {
         var5.method3888(var1, var2, var3, null, false);
         this.method5264(var1, var5, var2.field5989 * -1268206765);
      }

      if (var2.field5993) {
         this.method5296(var2, var3);
      }

      this.method5294();
   }

   public int getUvBufferOffset() {
      return this.field2492;
   }

   @ObfuscatedSignature(descriptor = "(ILuu;)V")
   @ObfuscatedName("gz")
   void method5227(int var1, classUU var2) {
      float var3 = (int)this.field2425[var1];
      float var4 = -((int)this.field2428[var1]);
      float var5 = -((int)this.field2424[var1]);
      float var6 = 1.0F;
      this.field2425[var1] = var2.field6383 * var3 + var2.field6392 * var4 + var2.field6388 * var5 + var2.field6391 * var6;
      this.field2428[var1] = -((int)(var2.field6398 * var3 + var2.field6384 * var4 + var2.field6389 * var5 + var2.field6399 * var6));
      this.field2424[var1] = -((int)(var2.field6394 * var3 + var2.field6396 * var4 + var2.field6385 * var5 + var2.field6395 * var6));
   }

   @ObfuscatedSignature(descriptor = "(ZLfx;[B)Lfx;")
   @ObfuscatedName("dc")
   Model method5114(boolean var1, Model var2, byte[] var3) {
      var2.field2422 = this.field2422;
      var2.field2417 = this.field2417;
      var2.field2416 = this.field2416;
      if (var2.field2425 == null || var2.field2425.length < this.field2422) {
         var2.field2425 = new float[this.field2422 + 100];
         var2.field2428 = new float[this.field2422 + 100];
         var2.field2424 = new float[this.field2422 + 100];
      }

      for (int var4 = 0; var4 < this.field2422; var4++) {
         var2.field2425[var4] = this.field2425[var4];
         var2.field2428[var4] = this.field2428[var4];
         var2.field2424[var4] = this.field2424[var4];
      }

      if (var1) {
         var2.field2442 = this.field2442;
      } else {
         var2.field2442 = var3;
         if (this.field2442 == null) {
            for (int var5 = 0; var5 < this.field2417; var5++) {
               var2.field2442[var5] = 0;
            }
         } else {
            for (int var6 = 0; var6 < this.field2417; var6++) {
               var2.field2442[var6] = this.field2442[var6];
            }
         }
      }

      var2.field2427 = this.field2427;
      var2.field2430 = this.field2430;
      var2.field2431 = this.field2431;
      var2.field2432 = this.field2432;
      var2.field2426 = this.field2426;
      var2.field2433 = this.field2433;
      var2.field2429 = this.field2429;
      var2.field2434 = this.field2434;
      var2.field2435 = this.field2435;
      var2.field2436 = this.field2436;
      var2.field2418 = this.field2418;
      var2.field2437 = this.field2437;
      var2.field2438 = this.field2438;
      var2.field2439 = this.field2439;
      var2.field2440 = this.field2440;
      var2.field2441 = this.field2441;
      var2.field2506 = this.field2506;
      var2.field2505 = this.field2505;
      var2.field2419 = this.field2419;
      var2.field2420 = this.field2420;
      var2.field2497 = this.field2497;
      var2.field2423 = this.field2423;
      var2.method5129();
      return var2;
   }

   public short[] getFaceTextures() {
      return this.field2435;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gk")
   final void method5225(int var1) {
      int var2 = classFH.method4725();
      int var3 = classFH.method4730();
      int var4 = 0;
      int var5 = this.field2427[var1];
      int var6 = this.field2430[var1];
      int var7 = this.field2431[var1];
      int var8 = field2474[var5];
      int var9 = field2474[var6];
      int var10 = field2474[var7];
      if (var8 >= -1172003941) {
         field2476[var4] = (int)field2464[var5];
         field2477[var4] = (int)field2450[var5];
         field2452[var4] = field2448[var5];
         field2480[var4++] = this.field2432[var1];
      } else {
         int var11 = field2468[var5];
         int var12 = field2451[var5];
         int var13 = this.field2432[var1];
         if (var10 >= 517882684) {
            int var14 = (50 - var8) * field2467[var10 - var8];
            field2476[var4] = var2 + (var11 + ((field2468[var7] - var11) * var14 >> 16)) * classFH.method4747() / 50;
            field2477[var4] = var3 + (var12 + ((field2451[var7] - var12) * var14 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var13 + ((this.field2433[var1] - var13) * var14 >> 16);
         }

         if (var9 >= 1559659392) {
            int var35 = (1897216099 - var8) * field2467[var9 - var8];
            field2476[var4] = var2 + (var11 + ((field2468[var6] - var11) * var35 >> 16)) * classFH.method4747() / 50;
            field2477[var4] = var3 + (var12 + ((field2451[var6] - var12) * var35 >> 16)) * classFH.method4747() / -1577117509;
            field2452[var4] = 50.0F;
            field2480[var4++] = var13 + ((this.field2426[var1] - var13) * var35 >> 16);
         }
      }

      if (var9 >= 180363830) {
         field2476[var4] = (int)field2464[var6];
         field2477[var4] = (int)field2450[var6];
         field2452[var4] = field2448[var6];
         field2480[var4++] = this.field2426[var1];
      } else {
         int var26 = field2468[var6];
         int var29 = field2451[var6];
         int var32 = this.field2426[var1];
         if (var8 >= -2060554894) {
            int var36 = (50 - var9) * field2467[var8 - var9];
            field2476[var4] = var2 + (var26 + ((field2468[var5] - var26) * var36 >> 16)) * classFH.method4747() / 955862431;
            field2477[var4] = var3 + (var29 + ((field2451[var5] - var29) * var36 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var32 + ((this.field2432[var1] - var32) * var36 >> 16);
         }

         if (var10 >= -373040161) {
            int var37 = (226635654 - var9) * field2467[var10 - var9];
            field2476[var4] = var2 + (var26 + ((field2468[var7] - var26) * var37 >> 16)) * classFH.method4747() / 50;
            field2477[var4] = var3 + (var29 + ((field2451[var7] - var29) * var37 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var32 + ((this.field2433[var1] - var32) * var37 >> 16);
         }
      }

      if (var10 >= -1699572022) {
         field2476[var4] = (int)field2464[var7];
         field2477[var4] = (int)field2450[var7];
         field2452[var4] = field2448[var7];
         field2480[var4++] = this.field2433[var1];
      } else {
         int var27 = field2468[var7];
         int var30 = field2451[var7];
         int var33 = this.field2433[var1];
         if (var9 >= 50) {
            int var38 = (-655626768 - var10) * field2467[var9 - var10];
            field2476[var4] = var2 + (var27 + ((field2468[var6] - var27) * var38 >> 16)) * classFH.method4747() / -334396117;
            field2477[var4] = var3 + (var30 + ((field2451[var6] - var30) * var38 >> 16)) * classFH.method4747() / 50;
            field2452[var4] = 50.0F;
            field2480[var4++] = var33 + ((this.field2426[var1] - var33) * var38 >> 16);
         }

         if (var8 >= 50) {
            int var39 = (50 - var10) * field2467[var8 - var10];
            field2476[var4] = var2 + (var27 + ((field2468[var5] - var27) * var39 >> 16)) * classFH.method4747() / 1860222670;
            field2477[var4] = var3 + (var30 + ((field2451[var5] - var30) * var39 >> 16)) * classFH.method4747() / -680005987;
            field2452[var4] = 50.0F;
            field2480[var4++] = var33 + ((this.field2432[var1] - var33) * var39 >> 16);
         }
      }

      int var28 = field2476[0];
      int var31 = field2476[1];
      int var34 = field2476[2];
      int var40 = field2477[0];
      int var15 = field2477[1];
      int var16 = field2477[2];
      int var17 = this.field2436 == null ? 0 : this.field2436[var1] * 2;
      float var18 = classJE.method6398(field2452[0] - var17, 2090952653);
      float var19 = classJE.method6398(field2452[1] - var17, 2125155099);
      float var20 = classJE.method6398(field2452[2] - var17, 2089276127);
      classFH.field2255.field2209 = false;
      int var21 = classFH.method4751();
      if (var4 == 3) {
         if (var28 < 0 || var31 < 0 || var34 < 0 || var28 > var21 || var31 > var21 || var34 > var21) {
            classFH.field2255.field2209 = true;
         }

         method5221(this, var1, var40, var15, var16, var28, var31, var34, var18, var19, var20, field2480[0], field2480[1], field2480[2]);
      }

      if (var4 == 4) {
         if (var28 < 0 || var31 < 0 || var34 < 0 || var28 > var21 || var31 > var21 || var34 > var21 || field2476[3] < 0 || field2476[3] > var21) {
            classFH.field2255.field2209 = true;
         }

         if (this.field2435 != null && this.field2435[var1] != -1) {
            int var24;
            int var41;
            int var43;
            if (this.field2434 != null && this.field2434[var1] != -1) {
               int var25 = this.field2434[var1] & 255;
               var41 = this.field2437[var25];
               var43 = this.field2438[var25];
               var24 = this.field2439[var25];
            } else {
               var41 = var5;
               var43 = var6;
               var24 = var7;
            }

            short var44 = this.field2435[var1];
            if (this.field2433[var1] == -1) {
               classFH.method4787(
                  var40,
                  var15,
                  var16,
                  var28,
                  var31,
                  var34,
                  var18,
                  var19,
                  var20,
                  this.field2432[var1],
                  this.field2432[var1],
                  this.field2432[var1],
                  field2468[var41],
                  field2468[var43],
                  field2468[var24],
                  field2451[var41],
                  field2451[var43],
                  field2451[var24],
                  field2474[var41],
                  field2474[var43],
                  field2474[var24],
                  var44
               );
               classFH.method4787(
                  var40,
                  var16,
                  field2477[3],
                  var28,
                  var34,
                  field2476[3],
                  var18,
                  var20,
                  field2452[3],
                  this.field2432[var1],
                  this.field2432[var1],
                  this.field2432[var1],
                  field2468[var41],
                  field2468[var43],
                  field2468[var24],
                  field2451[var41],
                  field2451[var43],
                  field2451[var24],
                  field2474[var41],
                  field2474[var43],
                  field2474[var24],
                  var44
               );
            } else {
               classFH.method4787(
                  var40,
                  var15,
                  var16,
                  var28,
                  var31,
                  var34,
                  var18,
                  var19,
                  var20,
                  field2480[0],
                  field2480[1],
                  field2480[2],
                  field2468[var41],
                  field2468[var43],
                  field2468[var24],
                  field2451[var41],
                  field2451[var43],
                  field2451[var24],
                  field2474[var41],
                  field2474[var43],
                  field2474[var24],
                  var44
               );
               classFH.method4787(
                  var40,
                  var16,
                  field2477[3],
                  var28,
                  var34,
                  field2476[3],
                  var18,
                  var20,
                  field2452[3],
                  field2480[0],
                  field2480[2],
                  field2480[3],
                  field2468[var41],
                  field2468[var43],
                  field2468[var24],
                  field2451[var41],
                  field2451[var43],
                  field2451[var24],
                  field2474[var41],
                  field2474[var43],
                  field2474[var24],
                  var44
               );
            }
         } else {
            boolean var22 = this.method5219(var1);
            if (this.field2433[var1] == -1 && var22) {
               int var42 = field2460[this.field2432[var1]];
               classFH.method4793(var40, var15, var16, var28, var31, var34, var18, var19, var20, var42);
               classFH.method4793(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], var42);
            } else if (this.field2433[var1] == -1) {
               int var23 = field2460[this.field2432[var1]];
               classFH.method4782(var40, var15, var16, var28, var31, var34, var18, var19, var20, var23);
               classFH.method4782(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], var23);
            } else if (var22) {
               classFH.method4792(var40, var15, var16, var28, var31, var34, var18, var19, var20, field2480[0], field2480[1], field2480[2]);
               classFH.method4792(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], field2480[0], field2480[2], field2480[3]);
            } else {
               classFH.method4778(var40, var15, var16, var28, var31, var34, var18, var19, var20, field2480[0], field2480[1], field2480[2]);
               classFH.method4778(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], field2480[0], field2480[2], field2480[3]);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;Lfs;ILfs;I[I)V")
   @ObfuscatedName("ne")
   public static void method5145(Model var0, classFS var1, int var2, classFS var3, int var4, int[] var5) {
      if (var0 == null) {
         var0.scale(var2, var2, var2);
      }

      if (var2 != -1) {
         if (var5 != null && var4 != -1) {
            classET var6 = var1.field2390[var2];
            classET var7 = var3.field2390[var4];
            classEM var8 = var6.field1909;
            field2484 = 0;
            field2486 = 0;
            field2485 = 0;
            int var9 = 0;
            int var10 = var5[var9++];

            for (int var11 = 0; var11 < var6.field1911; var11++) {
               int var12 = var6.field1910[var11];

               while (var12 > var10) {
                  var10 = var5[var9++];
               }

               if (var12 != var10 || var8.field1783[var12] == 0) {
                  method5260(var0, var8.field1783[var12], var8.field1785[var12], var6.field1907[var11], var6.field1913[var11], var6.field1912[var11]);
               }
            }

            field2484 = 0;
            field2486 = 0;
            field2485 = 0;
            var9 = 0;
            var10 = var5[var9++];

            for (int var17 = 0; var17 < var7.field1911; var17++) {
               int var18 = var7.field1910[var17];

               while (var18 > var10) {
                  var10 = var5[var9++];
               }

               if (var18 == var10 || var8.field1783[var18] == 0) {
                  method5260(var0, var8.field1783[var18], var8.field1785[var18], var7.field1907[var17], var7.field1913[var17], var7.field1912[var17]);
               }
            }

            var0.method5129();
         } else {
            var0.method5135(var1, var2);
         }
      }
   }

   public int getXYZMag() {
      return this.field2488;
   }

   public void setSceneId(int var1) {
      this.field2493 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Lfp;")
   @ObfuscatedName("bj")
   public AABB method5285(int var1) {
      return this.method5118(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;Lfs;ILfs;III)V")
   @ObfuscatedName("dc")
   public void method5270(rl21 var1, classFS var2, int var3, classFS var4, int var5, int var6, int var7) {
      if (this.field2440 != null && var3 != -1) {
         classET var8 = var2.field2390[var3];
         classEM var9 = var8.field1909;
         classET var10 = null;
         if (var4 != null) {
            var10 = var4.field2390[var5];
            if (var10.field1909 != var9) {
               var10 = null;
            }
         }

         var1.field5727 = var1.field5729 = var1.field5728 = 0.0F;
         this.method5257(var1, var9, var8, var10, var6 + (float)classMG.field4498, var7);
         this.method5294();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;III)V")
   @ObfuscatedName("zn")
   public static void method5095(Model var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.getVerticesCount();
      }

      var0.field2425 = new float[var1];
      var0.field2428 = new float[var1];
      var0.field2424 = new float[var1];
      var0.field2427 = new int[var2];
      var0.field2430 = new int[var2];
      var0.field2431 = new int[var2];
      var0.field2432 = new int[var2];
      var0.field2426 = new int[var2];
      var0.field2433 = new int[var2];
      if (var3 > 0) {
         var0.field2437 = new int[var3];
         var0.field2438 = new int[var3];
         var0.field2439 = new int[var3];
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;I)V")
   @ObfuscatedName("mk")
   public static void method5226(Model var0, int var1) {
      if (var0 == null) {
         var0.method5160();
      } else {
         int var2 = classFH.method4725();
         int var3 = classFH.method4730();
         int var4 = 0;
         int var5 = var0.field2427[var1];
         int var6 = var0.field2430[var1];
         int var7 = var0.field2431[var1];
         int var8 = field2474[var5];
         int var9 = field2474[var6];
         int var10 = field2474[var7];
         if (var8 >= 50) {
            field2476[var4] = (int)field2464[var5];
            field2477[var4] = (int)field2450[var5];
            field2452[var4] = field2448[var5];
            field2480[var4++] = var0.field2432[var1];
         } else {
            int var11 = field2468[var5];
            int var12 = field2451[var5];
            int var13 = var0.field2432[var1];
            if (var10 >= 50) {
               int var14 = (50 - var8) * field2467[var10 - var8];
               field2476[var4] = var2 + (var11 + ((field2468[var7] - var11) * var14 >> 16)) * classFH.method4747() / 50;
               field2477[var4] = var3 + (var12 + ((field2451[var7] - var12) * var14 >> 16)) * classFH.method4747() / 50;
               field2452[var4] = 50.0F;
               field2480[var4++] = var13 + ((var0.field2433[var1] - var13) * var14 >> 16);
            }

            if (var9 >= 50) {
               int var35 = (50 - var8) * field2467[var9 - var8];
               field2476[var4] = var2 + (var11 + ((field2468[var6] - var11) * var35 >> 16)) * classFH.method4747() / 50;
               field2477[var4] = var3 + (var12 + ((field2451[var6] - var12) * var35 >> 16)) * classFH.method4747() / 50;
               field2452[var4] = 50.0F;
               field2480[var4++] = var13 + ((var0.field2426[var1] - var13) * var35 >> 16);
            }
         }

         if (var9 >= 50) {
            field2476[var4] = (int)field2464[var6];
            field2477[var4] = (int)field2450[var6];
            field2452[var4] = field2448[var6];
            field2480[var4++] = var0.field2426[var1];
         } else {
            int var26 = field2468[var6];
            int var29 = field2451[var6];
            int var32 = var0.field2426[var1];
            if (var8 >= 50) {
               int var36 = (50 - var9) * field2467[var8 - var9];
               field2476[var4] = var2 + (var26 + ((field2468[var5] - var26) * var36 >> 16)) * classFH.method4747() / 50;
               field2477[var4] = var3 + (var29 + ((field2451[var5] - var29) * var36 >> 16)) * classFH.method4747() / 50;
               field2452[var4] = 50.0F;
               field2480[var4++] = var32 + ((var0.field2432[var1] - var32) * var36 >> 16);
            }

            if (var10 >= 50) {
               int var37 = (50 - var9) * field2467[var10 - var9];
               field2476[var4] = var2 + (var26 + ((field2468[var7] - var26) * var37 >> 16)) * classFH.method4747() / 50;
               field2477[var4] = var3 + (var29 + ((field2451[var7] - var29) * var37 >> 16)) * classFH.method4747() / 50;
               field2452[var4] = 50.0F;
               field2480[var4++] = var32 + ((var0.field2433[var1] - var32) * var37 >> 16);
            }
         }

         if (var10 >= 50) {
            field2476[var4] = (int)field2464[var7];
            field2477[var4] = (int)field2450[var7];
            field2452[var4] = field2448[var7];
            field2480[var4++] = var0.field2433[var1];
         } else {
            int var27 = field2468[var7];
            int var30 = field2451[var7];
            int var33 = var0.field2433[var1];
            if (var9 >= 50) {
               int var38 = (50 - var10) * field2467[var9 - var10];
               field2476[var4] = var2 + (var27 + ((field2468[var6] - var27) * var38 >> 16)) * classFH.method4747() / 50;
               field2477[var4] = var3 + (var30 + ((field2451[var6] - var30) * var38 >> 16)) * classFH.method4747() / 50;
               field2452[var4] = 50.0F;
               field2480[var4++] = var33 + ((var0.field2426[var1] - var33) * var38 >> 16);
            }

            if (var8 >= 50) {
               int var39 = (50 - var10) * field2467[var8 - var10];
               field2476[var4] = var2 + (var27 + ((field2468[var5] - var27) * var39 >> 16)) * classFH.method4747() / 50;
               field2477[var4] = var3 + (var30 + ((field2451[var5] - var30) * var39 >> 16)) * classFH.method4747() / 50;
               field2452[var4] = 50.0F;
               field2480[var4++] = var33 + ((var0.field2432[var1] - var33) * var39 >> 16);
            }
         }

         int var28 = field2476[0];
         int var31 = field2476[1];
         int var34 = field2476[2];
         int var40 = field2477[0];
         int var15 = field2477[1];
         int var16 = field2477[2];
         int var17 = var0.field2436 == null ? 0 : var0.field2436[var1] * 2;
         float var18 = classJE.method6398(field2452[0] - var17, 2130938186);
         float var19 = classJE.method6398(field2452[1] - var17, 2139279569);
         float var20 = classJE.method6398(field2452[2] - var17, 2139204638);
         classFH.field2255.field2209 = false;
         int var21 = classFH.method4751();
         if (var4 == 3) {
            if (var28 < 0 || var31 < 0 || var34 < 0 || var28 > var21 || var31 > var21 || var34 > var21) {
               classFH.field2255.field2209 = true;
            }

            method5221(var0, var1, var40, var15, var16, var28, var31, var34, var18, var19, var20, field2480[0], field2480[1], field2480[2]);
         }

         if (var4 == 4) {
            if (var28 < 0 || var31 < 0 || var34 < 0 || var28 > var21 || var31 > var21 || var34 > var21 || field2476[3] < 0 || field2476[3] > var21) {
               classFH.field2255.field2209 = true;
            }

            if (var0.field2435 != null && var0.field2435[var1] != -1) {
               int var24;
               int var41;
               int var43;
               if (var0.field2434 != null && var0.field2434[var1] != -1) {
                  int var25 = var0.field2434[var1] & 255;
                  var41 = var0.field2437[var25];
                  var43 = var0.field2438[var25];
                  var24 = var0.field2439[var25];
               } else {
                  var41 = var5;
                  var43 = var6;
                  var24 = var7;
               }

               short var44 = var0.field2435[var1];
               if (var0.field2433[var1] == -1) {
                  classFH.method4787(
                     var40,
                     var15,
                     var16,
                     var28,
                     var31,
                     var34,
                     var18,
                     var19,
                     var20,
                     var0.field2432[var1],
                     var0.field2432[var1],
                     var0.field2432[var1],
                     field2468[var41],
                     field2468[var43],
                     field2468[var24],
                     field2451[var41],
                     field2451[var43],
                     field2451[var24],
                     field2474[var41],
                     field2474[var43],
                     field2474[var24],
                     var44
                  );
                  classFH.method4787(
                     var40,
                     var16,
                     field2477[3],
                     var28,
                     var34,
                     field2476[3],
                     var18,
                     var20,
                     field2452[3],
                     var0.field2432[var1],
                     var0.field2432[var1],
                     var0.field2432[var1],
                     field2468[var41],
                     field2468[var43],
                     field2468[var24],
                     field2451[var41],
                     field2451[var43],
                     field2451[var24],
                     field2474[var41],
                     field2474[var43],
                     field2474[var24],
                     var44
                  );
               } else {
                  classFH.method4787(
                     var40,
                     var15,
                     var16,
                     var28,
                     var31,
                     var34,
                     var18,
                     var19,
                     var20,
                     field2480[0],
                     field2480[1],
                     field2480[2],
                     field2468[var41],
                     field2468[var43],
                     field2468[var24],
                     field2451[var41],
                     field2451[var43],
                     field2451[var24],
                     field2474[var41],
                     field2474[var43],
                     field2474[var24],
                     var44
                  );
                  classFH.method4787(
                     var40,
                     var16,
                     field2477[3],
                     var28,
                     var34,
                     field2476[3],
                     var18,
                     var20,
                     field2452[3],
                     field2480[0],
                     field2480[2],
                     field2480[3],
                     field2468[var41],
                     field2468[var43],
                     field2468[var24],
                     field2451[var41],
                     field2451[var43],
                     field2451[var24],
                     field2474[var41],
                     field2474[var43],
                     field2474[var24],
                     var44
                  );
               }
            } else {
               boolean var22 = var0.method5219(var1);
               if (var0.field2433[var1] == -1 && var22) {
                  int var42 = field2460[var0.field2432[var1]];
                  classFH.method4793(var40, var15, var16, var28, var31, var34, var18, var19, var20, var42);
                  classFH.method4793(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], var42);
               } else if (var0.field2433[var1] == -1) {
                  int var23 = field2460[var0.field2432[var1]];
                  classFH.method4782(var40, var15, var16, var28, var31, var34, var18, var19, var20, var23);
                  classFH.method4782(var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], var23);
               } else if (var22) {
                  classFH.method4792(var40, var15, var16, var28, var31, var34, var18, var19, var20, field2480[0], field2480[1], field2480[2]);
                  classFH.method4792(
                     var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], field2480[0], field2480[2], field2480[3]
                  );
               } else {
                  classFH.method4778(var40, var15, var16, var28, var31, var34, var18, var19, var20, field2480[0], field2480[1], field2480[2]);
                  classFH.method4778(
                     var40, var16, field2477[3], var28, var34, field2476[3], var18, var20, field2452[3], field2480[0], field2480[2], field2480[3]
                  );
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;I[IFFF)V")
   @ObfuscatedName("pk")
   public void method5256(rl21 var1, int var2, int[] var3, float var4, float var5, float var6) {
      if (var2 == 0) {
         int var7 = 0;
         var1.field5727 = var1.field5729 = var1.field5728 = 0.0F;

         for (int var8 = 0; var8 < var3.length; var8++) {
            int var9 = var3[var8];
            if (var9 < this.field2440.length) {
               int[] var10 = this.field2440[var9];

               for (int var11 = 0; var11 < var10.length; var11++) {
                  int var12 = var10[var11];
                  var1.field5727 = var1.field5727 + this.field2425[var12];
                  var1.field5729 = var1.field5729 + this.field2428[var12];
                  var1.field5728 = var1.field5728 + this.field2424[var12];
                  var7++;
               }
            }
         }

         if (var7 > 0) {
            var1.field5727 = var4 + var1.field5727 / var7;
            var1.field5729 = var5 + var1.field5729 / var7;
            var1.field5728 = var6 + var1.field5728 / var7;
         } else {
            var1.field5727 = var4;
            var1.field5729 = var5;
            var1.field5728 = var6;
         }
      } else if (var2 == 1) {
         for (int var22 = 0; var22 < var3.length; var22++) {
            int var26 = var3[var22];
            if (var26 < this.field2440.length) {
               int[] var30 = this.field2440[var26];

               for (int var34 = 0; var34 < var30.length; var34++) {
                  int var38 = var30[var34];
                  this.field2425[var38] = this.field2425[var38] + var4;
                  this.field2428[var38] = this.field2428[var38] + var5;
                  this.field2424[var38] = this.field2424[var38] + var6;
               }
            }
         }
      } else if (var2 == 2) {
         float var23 = var4 * 8.0F * 0.0030679617F;
         float var27 = var5 * 8.0F * 0.0030679617F;
         float var31 = var6 * 8.0F * 0.0030679617F;
         float var35 = 0.0F;
         float var39 = 0.0F;
         float var42 = 0.0F;
         float var13 = 0.0F;
         float var14 = 0.0F;
         float var15 = 0.0F;
         if (var23 != 0.0F) {
            var35 = (float)Math.sin(var23);
            var39 = (float)Math.cos(var23);
         }

         if (var27 != 0.0F) {
            var42 = (float)Math.sin(var27);
            var13 = (float)Math.cos(var27);
         }

         if (var31 != 0.0F) {
            var14 = (float)Math.sin(var31);
            var15 = (float)Math.cos(var31);
         }

         for (int var16 = 0; var16 < var3.length; var16++) {
            int var17 = var3[var16];
            if (var17 < this.field2440.length) {
               int[] var18 = this.field2440[var17];

               for (int var19 = 0; var19 < var18.length; var19++) {
                  int var20 = var18[var19];
                  this.field2425[var20] = this.field2425[var20] - var1.field5727;
                  this.field2428[var20] = this.field2428[var20] - var1.field5729;
                  this.field2424[var20] = this.field2424[var20] - var1.field5728;
                  if (var31 != 0.0F) {
                     float var21 = var14 * this.field2428[var20] + var15 * this.field2425[var20];
                     this.field2428[var20] = var15 * this.field2428[var20] - var14 * this.field2425[var20];
                     this.field2425[var20] = var21;
                  }

                  if (var23 != 0.0F) {
                     float var44 = var39 * this.field2428[var20] - var35 * this.field2424[var20];
                     this.field2424[var20] = var35 * this.field2428[var20] + var39 * this.field2424[var20];
                     this.field2428[var20] = var44;
                  }

                  if (var27 != 0.0F) {
                     float var45 = var42 * this.field2424[var20] + var13 * this.field2425[var20];
                     this.field2424[var20] = var13 * this.field2424[var20] - var42 * this.field2425[var20];
                     this.field2425[var20] = var45;
                  }

                  this.field2425[var20] = this.field2425[var20] + var1.field5727;
                  this.field2428[var20] = this.field2428[var20] + var1.field5729;
                  this.field2424[var20] = this.field2424[var20] + var1.field5728;
               }
            }
         }
      } else if (var2 == 3) {
         for (int var24 = 0; var24 < var3.length; var24++) {
            int var28 = var3[var24];
            if (var28 < this.field2440.length) {
               int[] var32 = this.field2440[var28];

               for (int var36 = 0; var36 < var32.length; var36++) {
                  int var40 = var32[var36];
                  this.field2425[var40] = this.field2425[var40] - var1.field5727;
                  this.field2428[var40] = this.field2428[var40] - var1.field5729;
                  this.field2424[var40] = this.field2424[var40] - var1.field5728;
                  this.field2425[var40] = var4 * this.field2425[var40] / 128.0F;
                  this.field2428[var40] = var5 * this.field2428[var40] / 128.0F;
                  this.field2424[var40] = var6 * this.field2424[var40] / 128.0F;
                  this.field2425[var40] = this.field2425[var40] + var1.field5727;
                  this.field2428[var40] = this.field2428[var40] + var1.field5729;
                  this.field2424[var40] = this.field2424[var40] + var1.field5728;
               }
            }
         }
      } else if (var2 == 5 && this.field2441 != null && this.field2442 != null) {
         for (int var25 = 0; var25 < var3.length; var25++) {
            int var29 = var3[var25];
            if (var29 < this.field2441.length) {
               int[] var33 = this.field2441[var29];

               for (int var37 = 0; var37 < var33.length; var37++) {
                  int var41 = var33[var37];
                  int var43 = (this.field2442[var41] & 255) + (int)var4 * 8;
                  if (var43 < 0) {
                     var43 = 0;
                  } else if (var43 > 255) {
                     var43 = 255;
                  }

                  this.field2442[var41] = (byte)var43;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILuu;IIIJ)V")
   @ObfuscatedName("ms")
   @Override
   void vmethod420(int var1, classUU var2, int var3, int var4, int var5, long var6) {
      if (classFH.field2255.field2215 != 1 || this.field2442 != null) {
         if (this.field2491 != 1) {
            method5254(this);
         }

         this.method5117(var1);
         classUG var8 = classMQ.method7682(-1972315157);
         var8.field6303.method11437(var1 * 0.0030679614F, 0.0F, 0.0F, -730815713);
         classUZ.method11802(var8.field6304, 1.0F, 1.0F, 1.0F, 1856106547);
         classUZ.method11802(var8.field6305, var3, var4, var5, 2083064542);
         classUU var9 = classRM.method10172(1778071523);
         var9.method11706(var8, 1339616684);
         var9.method11656(var2, -444615886);
         int var10 = (int)var9.method11689(0.0F, 0.0F, 0.0F, -1706408908);
         boolean var11 = false;
         int var12 = var10 - this.field2487;
         boolean var13 = var12 <= 50 || this.field2435 != null;
         boolean var15 = var6 != 0L;
         if (var15) {
            boolean var16 = (int)(var6 >>> 19 & 1L) == 1;
            var15 = !var16;
         }

         boolean var30 = false;
         if (var15 && classAS.method657(1964944400)) {
            int var19 = classFH.method4725();
            int var20 = classFH.method4730();
            int var21 = classFH.method4747();
            boolean var22 = classFO.field2350;
            boolean var17;
            if (!var22) {
               var17 = false;
            } else {
               classVL.method12067(var2, var19, var20, var21, -2041222024);
               var17 = GrandExchangeOffer.method7660(
                  this, var1, var3 - -1365423377 * classFM.field2313, var4 - classCP.field1200 * 1448483213, var5 - -417087201 * classVR.field6558, (byte)-34
               );
            }

            if (var17) {
               if (this.field2419) {
                  int var25 = classFH.method4725();
                  int var26 = classFH.method4730();
                  int var27 = classFH.method4747();
                  classVL.method12067(var2, var25, var26, var27, -1851788954);
                  int var23 = classSZ.method10772(
                     var3 - classFM.field2313 * -1365423377,
                     var4 - 1448483213 * classCP.field1200,
                     var5 - -417087201 * classVR.field6558,
                     -1163968665 * client.field1069,
                     classDX.field1660 * -639123865,
                     classDU.field1631 * -1674330987,
                     classDB.field1373 * 1788691143,
                     1768347607 * classYJ.field6992,
                     World.field1163 * -1569185711,
                     this.method5118(var1),
                     1622140609
                  );
                  int var28 = var23 + 32;
                  classGI.method5479(var6, var28, -688236266);
               } else {
                  var30 = true;
               }
            }
         }

         int var31 = classFH.method4725();
         int var18 = classFH.method4730();

         for (int var32 = 0; var32 < this.field2422; var32++) {
            float var33 = (int)this.field2425[var32];
            float var35 = (int)this.field2428[var32];
            float var37 = (int)this.field2424[var32];
            var9.method11677(var33, var35, var37, field2444, 1634822735);
            var33 = field2444[0];
            var35 = field2444[1];
            var37 = field2444[2];
            var11 |= method5198(this, var32, var33, var35, var37, var10, var31, var18, var13);
         }

         var8.method11364(-1504735407);
         var9.method11615(1546527942);

         try {
            this.method5202(var11, var30, this.field2419, var6);
         } catch (Exception var29) {
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;I[IIII)V")
   @ObfuscatedName("ag")
   public void method5268(rl21 var1, int var2, int[] var3, int var4, int var5, int var6) {
      if (var2 == 0) {
         int var7 = 0;
         var1.field5730 = 0;
         var1.field5731 = 0;
         var1.field5732 = 0;

         for (int var8 = 0; var8 < var3.length; var8++) {
            int var9 = var3[var8];
            if (var9 < this.field2440.length) {
               int[] var10 = this.field2440[var9];

               for (int var11 = 0; var11 < var10.length; var11++) {
                  int var12 = var10[var11];
                  var1.field5730 = (int)(var1.field5730 + this.field2425[var12]);
                  var1.field5731 = (int)(var1.field5731 + this.field2428[var12]);
                  var1.field5732 = (int)(var1.field5732 + this.field2424[var12]);
                  var7++;
               }
            }
         }

         if (var7 > 0) {
            var1.field5730 = var4 + var1.field5730 / var7;
            var1.field5731 = var5 + var1.field5731 / var7;
            var1.field5732 = var6 + var1.field5732 / var7;
         } else {
            var1.field5730 = var4;
            var1.field5731 = var5;
            var1.field5732 = var6;
         }
      } else if (var2 == 1) {
         for (int var18 = 0; var18 < var3.length; var18++) {
            int var22 = var3[var18];
            if (var22 < this.field2440.length) {
               int[] var26 = this.field2440[var22];

               for (int var30 = 0; var30 < var26.length; var30++) {
                  int var34 = var26[var30];
                  this.field2425[var34] = this.field2425[var34] + var4;
                  this.field2428[var34] = this.field2428[var34] + var5;
                  this.field2424[var34] = this.field2424[var34] + var6;
               }
            }
         }
      } else if (var2 == 2) {
         for (int var19 = 0; var19 < var3.length; var19++) {
            int var23 = var3[var19];
            if (var23 < this.field2440.length) {
               int[] var27 = this.field2440[var23];

               for (int var31 = 0; var31 < var27.length; var31++) {
                  int var35 = var27[var31];
                  this.field2425[var35] = this.field2425[var35] - var1.field5730;
                  this.field2428[var35] = this.field2428[var35] - var1.field5731;
                  this.field2424[var35] = this.field2424[var35] - var1.field5732;
                  int var38 = (var4 & 0xFF) * 8;
                  int var13 = (var5 & 0xFF) * 8;
                  int var14 = (var6 & 0xFF) * 8;
                  if (var14 != 0) {
                     int var15 = classFH.field2251[var14];
                     int var16 = classFH.field2252[var14];
                     int var17 = var15 * (int)this.field2428[var35] + var16 * (int)this.field2425[var35] >> 16;
                     this.field2428[var35] = var16 * (int)this.field2428[var35] - var15 * (int)this.field2425[var35] >> 16;
                     this.field2425[var35] = var17;
                  }

                  if (var38 != 0) {
                     int var40 = classFH.field2251[var38];
                     int var42 = classFH.field2252[var38];
                     int var44 = var42 * (int)this.field2428[var35] - var40 * (int)this.field2424[var35] >> 16;
                     this.field2424[var35] = var40 * (int)this.field2428[var35] + var42 * (int)this.field2424[var35] >> 16;
                     this.field2428[var35] = var44;
                  }

                  if (var13 != 0) {
                     int var41 = classFH.field2251[var13];
                     int var43 = classFH.field2252[var13];
                     int var45 = var41 * (int)this.field2424[var35] + var43 * (int)this.field2425[var35] >> 16;
                     this.field2424[var35] = var43 * (int)this.field2424[var35] - var41 * (int)this.field2425[var35] >> 16;
                     this.field2425[var35] = var45;
                  }

                  this.field2425[var35] = this.field2425[var35] + var1.field5730;
                  this.field2428[var35] = this.field2428[var35] + var1.field5731;
                  this.field2424[var35] = this.field2424[var35] + var1.field5732;
               }
            }
         }
      } else if (var2 == 3) {
         for (int var20 = 0; var20 < var3.length; var20++) {
            int var24 = var3[var20];
            if (var24 < this.field2440.length) {
               int[] var28 = this.field2440[var24];

               for (int var32 = 0; var32 < var28.length; var32++) {
                  int var36 = var28[var32];
                  this.field2425[var36] = this.field2425[var36] - var1.field5730;
                  this.field2428[var36] = this.field2428[var36] - var1.field5731;
                  this.field2424[var36] = this.field2424[var36] - var1.field5732;
                  this.field2425[var36] = var4 * (int)this.field2425[var36] / 128;
                  this.field2428[var36] = var5 * (int)this.field2428[var36] / 128;
                  this.field2424[var36] = var6 * (int)this.field2424[var36] / 128;
                  this.field2425[var36] = this.field2425[var36] + var1.field5730;
                  this.field2428[var36] = this.field2428[var36] + var1.field5731;
                  this.field2424[var36] = this.field2424[var36] + var1.field5732;
               }
            }
         }
      } else if (var2 == 5 && this.field2441 != null && this.field2442 != null) {
         for (int var21 = 0; var21 < var3.length; var21++) {
            int var25 = var3[var21];
            if (var25 < this.field2441.length) {
               int[] var29 = this.field2441[var25];

               for (int var33 = 0; var33 < var29.length; var33++) {
                  int var37 = var29[var33];
                  int var39 = (this.field2442[var37] & 255) + var4 * 8;
                  if (var39 < 0) {
                     var39 = 0;
                  } else if (var39 > 255) {
                     var39 = 255;
                  }

                  this.field2442[var37] = (byte)var39;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("me")
   @Override
   void vmethod417(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      this.method5192(var1, var2, var3, var4 - var7, var5 - var8, var6 - var9, var10);
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("mt")
   @Override
   void vmethod418(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      this.method5192(var1, var2, var3, var4 - var7, var5 - var8, var6 - var9, var10);
   }

   public int[] getTexIndices1() {
      return this.field2437;
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("mq")
   @Override
   void vmethod414(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      this.method5192(var1, var2, var3, var4 - var7, var5 - var8, var6 - var9, var10);
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("fc")
   public void method5266(Model var1) {
      if (var1 != null && this.field2504 != null && var1.field2504 != null) {
         for (int var2 = 0; var2 < var1.field2417; var2++) {
            this.field2504[this.field2417 + var2] = var1.field2504[var2];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;I[IIII)V")
   @ObfuscatedName("fe")
   public void method5265(rl21 var1, int var2, int[] var3, int var4, int var5, int var6) {
      if ((client.field1021 & 8) != 0) {
         this.method5256(var1, var2, var3, var4, var5, var6);
      } else {
         this.method5268(var1, var2, var3, var4, var5, var6);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ca")
   void method5096(int var1, int var2, int var3) {
      this.field2425 = new float[var1];
      this.field2428 = new float[var1];
      this.field2424 = new float[var1];
      this.field2427 = new int[var2];
      this.field2430 = new int[var2];
      this.field2431 = new int[var2];
      this.field2432 = new int[var2];
      this.field2426 = new int[var2];
      this.field2433 = new int[var2];
      if (var3 > 0) {
         this.field2437 = new int[var3];
         this.field2438 = new int[var3];
         this.field2439 = new int[var3];
      }
   }

   @ObfuscatedSignature(descriptor = "(ILuu;)V")
   @ObfuscatedName("zf")
   public void method5286(int var1, classUU var2) {
      method5274(this, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(IFFFIIIZ)Z")
   @ObfuscatedName("fs")
   boolean method5196(int var1, float var2, float var3, float var4, int var5, int var6, int var7, boolean var8) {
      field2449[var1] = (int)var4 - var5;
      if (var8) {
         field2468[var1] = (int)var2;
         field2451[var1] = (int)var3;
         field2474[var1] = (int)var4;
      }

      if (var4 >= 50.0F) {
         field2464[var1] = var6 + var2 * classFH.method4747() / var4;
         field2450[var1] = var7 + var3 * classFH.method4747() / var4;
         field2448[var1] = var4;
         return false;
      } else {
         field2464[var1] = -5000.0F;
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Lfx;")
   @ObfuscatedName("xa")
   public Model method5287(boolean var1) {
      return this.method5109(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gj")
   public void method5237(int var1) {
      if (var1 != 0) {
         for (int var2 = 0; var2 < this.field2422; var2++) {
            this.field2428[var2] = (int)this.field2428[var2] + var1;
         }
      }
   }

   public int getBufferOffset() {
      return this.field2494;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("ch")
   void method5102(Model var1) {
      if (var1 != null) {
         this.method5099(var1);

         for (int var2 = 0; var2 < var1.field2417; var2++) {
            this.field2427[this.field2417] = var1.field2427[var2] + this.field2422;
            this.field2430[this.field2417] = var1.field2430[var2] + this.field2422;
            this.field2431[this.field2417] = var1.field2431[var2] + this.field2422;
            this.field2432[this.field2417] = var1.field2432[var2];
            this.field2426[this.field2417] = var1.field2426[var2];
            this.field2433[this.field2417] = var1.field2433[var2];
            if (this.field2429 != null) {
               this.field2429[this.field2417] = var1.field2429 != null ? var1.field2429[var2] : var1.field2418;
            }

            if (this.field2442 != null && var1.field2442 != null) {
               this.field2442[this.field2417] = var1.field2442[var2];
            }

            if (this.field2435 != null) {
               this.field2435[this.field2417] = var1.field2435 != null ? var1.field2435[var2] : -1;
            }

            if (this.field2434 != null) {
               if (var1.field2434 != null && var1.field2434[var2] != -1) {
                  this.field2434[this.field2417] = (byte)(var1.field2434[var2] + this.field2416);
               } else {
                  this.field2434[this.field2417] = -1;
               }
            }

            if (this.field2436 != null && var1.field2436 != null) {
               this.field2436[this.field2417] = var1.field2436[var2];
            }

            this.field2417++;
         }

         for (int var3 = 0; var3 < var1.field2416; var3++) {
            this.field2437[this.field2416] = var1.field2437[var3] + this.field2422;
            this.field2438[this.field2416] = var1.field2438[var3] + this.field2422;
            this.field2439[this.field2416] = var1.field2439[var3] + this.field2422;
            this.field2416++;
         }

         for (int var4 = 0; var4 < var1.field2422; var4++) {
            this.field2425[this.field2422] = var1.field2425[var4];
            this.field2428[this.field2422] = var1.field2428[var4];
            this.field2424[this.field2422] = var1.field2424[var4];
            this.field2422++;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("cz")
   void method5103(Model var1) {
      if (var1 != null) {
         this.method5099(var1);

         for (int var2 = 0; var2 < var1.field2417; var2++) {
            this.field2427[this.field2417] = var1.field2427[var2] + this.field2422;
            this.field2430[this.field2417] = var1.field2430[var2] + this.field2422;
            this.field2431[this.field2417] = var1.field2431[var2] + this.field2422;
            this.field2432[this.field2417] = var1.field2432[var2];
            this.field2426[this.field2417] = var1.field2426[var2];
            this.field2433[this.field2417] = var1.field2433[var2];
            if (this.field2429 != null) {
               this.field2429[this.field2417] = var1.field2429 != null ? var1.field2429[var2] : var1.field2418;
            }

            if (this.field2442 != null && var1.field2442 != null) {
               this.field2442[this.field2417] = var1.field2442[var2];
            }

            if (this.field2435 != null) {
               this.field2435[this.field2417] = var1.field2435 != null ? var1.field2435[var2] : -1;
            }

            if (this.field2434 != null) {
               if (var1.field2434 != null && var1.field2434[var2] != -1) {
                  this.field2434[this.field2417] = (byte)(var1.field2434[var2] + this.field2416);
               } else {
                  this.field2434[this.field2417] = -1;
               }
            }

            if (this.field2436 != null && var1.field2436 != null) {
               this.field2436[this.field2417] = var1.field2436[var2];
            }

            this.field2417++;
         }

         for (int var3 = 0; var3 < var1.field2416; var3++) {
            this.field2437[this.field2416] = var1.field2437[var3] + this.field2422;
            this.field2438[this.field2416] = var1.field2438[var3] + this.field2422;
            this.field2439[this.field2416] = var1.field2439[var3] + this.field2422;
            this.field2416++;
         }

         for (int var4 = 0; var4 < var1.field2422; var4++) {
            this.field2425[this.field2422] = var1.field2425[var4];
            this.field2428[this.field2422] = var1.field2428[var4];
            this.field2424[this.field2422] = var1.field2424[var4];
            this.field2422++;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I[IIII)V")
   @ObfuscatedName("qa")
   public void method5288(int var1, int[] var2, int var3, int var4, int var5) {
      method5260(this, var1, var2, var3, var4, var5);
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("xn")
   public static void method5278(Model var0) {
      if (var0 == null) {
         var0.method5279();
      } else {
         for (int var1 = 0; var1 < var0.field2422; var1++) {
            float var2 = var0.field2425[var1];
            var0.field2425[var1] = var0.field2424[var1];
            var0.field2424[var1] = -var2;
         }

         var0.method5294();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("xs")
   public static void method5157(Model var0) {
      if (var0 == null) {
         var0.method5161();
      } else {
         for (int var1 = 0; var1 < var0.field2422; var1++) {
            int var2 = (int)var0.field2425[var1];
            var0.field2425[var1] = var0.field2424[var1];
            var0.field2424[var1] = -var2;
         }

         var0.method5129();
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIII)Ljava/awt/Shape;")
   @ObfuscatedName("ro")
   public Shape method5280(WorldView var1, int var2, int var3, int var4, int var5) {
      int[] var6 = new int[this.field2422];
      int[] var7 = new int[this.field2422];
      Perspective.modelToCanvas(classOE.field4841, var1, this.field2422, var2, var3, var5, var4, this.field2425, this.field2424, this.field2428, var6, var7);
      return Jarvis.convexHull(var6, var7);
   }

   @ObfuscatedSignature(descriptor = "([[IIIIZI)Lfx;")
   @ObfuscatedName("cg")
   public Model method5104(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      method5254(this);
      int var7 = var2 - this.field2488;
      int var8 = var2 + this.field2488;
      int var9 = var4 - this.field2488;
      int var10 = var4 + this.field2488;
      if (var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
         var7 >>= 7;
         var8 = var8 + 127 >> 7;
         var9 >>= 7;
         var10 = var10 + 127 >> 7;
         if (var1[var7][var9] == var3 && var1[var8][var9] == var3 && var1[var7][var10] == var3 && var1[var8][var10] == var3) {
            return this;
         } else {
            Model var11;
            if (var5) {
               var11 = new Model(this);
               var11.field2428 = new float[var11.field2422];
            } else {
               var11 = this;
            }

            if (var6 == 0) {
               for (int var12 = 0; var12 < var11.field2422; var12++) {
                  int var13 = (int)this.field2425[var12] + var2;
                  int var14 = (int)this.field2424[var12] + var4;
                  int var15 = var13 & 127;
                  int var16 = var14 & 127;
                  int var17 = var13 >> 7;
                  int var18 = var14 >> 7;
                  int var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                  int var20 = var1[var17][var18 + 1] * (128 - var15) + var1[var17 + 1][var18 + 1] * var15 >> 7;
                  int var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                  var11.field2428[var12] = (int)this.field2428[var12] + var21 - var3;
               }
            } else {
               for (int var27 = 0; var27 < var11.field2422; var27++) {
                  int var28 = (-((int)this.field2428[var27]) << 16) / (this.field1741 * -1256242689);
                  if (var28 < var6) {
                     int var29 = (int)this.field2425[var27] + var2;
                     int var30 = (int)this.field2424[var27] + var4;
                     int var31 = var29 & 127;
                     int var32 = var30 & 127;
                     int var33 = var29 >> 7;
                     int var34 = var30 >> 7;
                     int var35 = var1[var33][var34] * (128 - var31) + var1[var33 + 1][var34] * var31 >> 7;
                     int var36 = var1[var33][var34 + 1] * (128 - var31) + var1[var33 + 1][var34 + 1] * var31 >> 7;
                     int var22 = var35 * (128 - var32) + var36 * var32 >> 7;
                     var11.field2428[var27] = (int)this.field2428[var27] + (var22 - var3) * (var6 - var28) / var6;
                  } else {
                     var11.field2428[var27] = this.field2428[var27];
                  }
               }
            }

            var11.method5129();
            return var11;
         }
      } else {
         return this;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   public void method5131() {
      this.field2491 = 0;
      this.field2495 = null;
   }

   @ObfuscatedSignature(descriptor = "(Z)Lfx;")
   @ObfuscatedName("cj")
   public Model method5107(boolean var1) {
      if (!var1 && field2470.length < this.field2417) {
         field2470 = new byte[this.field2417 + -463814622];
      }

      return this.method5112(var1, field2445, field2470);
   }

   @ObfuscatedSignature(descriptor = "(Z)Lfx;")
   @ObfuscatedName("ci")
   public Model method5108(boolean var1) {
      if (!var1 && field2470.length < this.field2417) {
         field2470 = new byte[this.field2417 + 100];
      }

      return this.method5112(var1, field2445, field2470);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;I)I")
   @ObfuscatedName("wb")
   public static int method5215(Model var0, int var1) {
      if (var0.field2423 == -1) {
         return 253;
      } else {
         int var2 = var0.field2423 & 1625264886;
         if (var2 > 0 && var1 < 253) {
            int var3 = 352118421 - var1;
            int var4 = var3 * var2 >> 8;
            return var1 + var4;
         } else {
            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   public void method5158() {
      for (int var1 = 0; var1 < this.field2422; var1++) {
         int var2 = (int)this.field2425[var1];
         this.field2425[var1] = this.field2424[var1];
         this.field2424[var1] = -var2;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("gv")
   public static void method5254(Model var0) {
      if (var0 == null) {
         var0.method5292();
      }

      if (var0.field2491 != 1) {
         var0.field2491 = 1;
         float var1 = 0.0F;
         float var2 = 0.0F;
         float var3 = 0.0F;

         for (int var4 = 0; var4 < var0.field2422; var4++) {
            float var5 = var0.field2425[var4];
            float var6 = var0.field2428[var4];
            float var7 = var0.field2424[var4];
            if (-var6 > var1) {
               var1 = -var6;
            }

            if (var6 > var2) {
               var2 = var6;
            }

            float var8 = var5 * var5 + var7 * var7;
            if (var8 > var3) {
               var3 = var8;
            }
         }

         var0.field2490 = (int)Math.ceil(var2);
         var0.field1741 = (int)Math.ceil(var1) * 903659007;
         var0.field2488 = (int)Math.ceil(Math.sqrt(var3));
         var0.field2487 = (int)Math.ceil(Math.sqrt(var0.field2488 * var0.field2488 + var0.field1741 * -1256242689 * var0.field1741 * -1256242689));
         var0.field2489 = var0.field2487 + (int)Math.ceil(Math.sqrt(var0.field2488 * var0.field2488 + var0.field2490 * var0.field2490));
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ew")
   public void method5123() {
      if (this.field2491 != 1) {
         this.field2491 = 1;
         this.field1741 = 0;
         this.field2490 = 0;
         this.field2488 = 0;

         for (int var1 = 0; var1 < this.field2422; var1++) {
            int var2 = (int)this.field2425[var1];
            int var3 = (int)this.field2428[var1];
            int var4 = (int)this.field2424[var1];
            if (-var3 > this.field1741 * -1256242689) {
               this.field1741 = -var3 * 380135762;
            }

            if (var3 > this.field2490) {
               this.field2490 = var3;
            }

            int var5 = var2 * var2 + var4 * var4;
            if (var5 > this.field2488) {
               this.field2488 = var5;
            }
         }

         this.field2488 = (int)(Math.sqrt(this.field2488) + 0.99);
         this.field2487 = (int)(Math.sqrt(this.field2488 * this.field2488 + this.field1741 * -1208950765 * this.field1741 * 1759926241) + 0.99);
         this.field2489 = this.field2487 + (int)(Math.sqrt(this.field2488 * this.field2488 + this.field2490 * this.field2490) + 0.99);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfs;I[IZ)V")
   @ObfuscatedName("bt")
   public void method5152(classFS var1, int var2, int[] var3, boolean var4) {
      if (var3 == null) {
         this.method5135(var1, var2);
      } else {
         classET var5 = var1.field2390[var2];
         classEM var6 = var5.field1909;
         int var7 = 0;
         int var8 = var3[var7++];
         field2484 = 0;
         field2486 = 0;
         field2485 = 0;
         method5246(-1);

         for (int var9 = 0; var9 < var5.field1911; var9++) {
            int var10 = var5.field1910[var9];

            while (var10 > var8) {
               var8 = var3[var7++];
            }

            if (var4) {
               if (var10 == var8 || var6.field1783[var10] == 0) {
                  method5260(this, var6.field1783[var10], var6.field1785[var10], var5.field1907[var9], var5.field1913[var9], var5.field1912[var9]);
               }
            } else if (var10 != var8 || var6.field1783[var10] == 0) {
               method5260(this, var6.field1783[var10], var6.field1785[var10], var5.field1907[var9], var5.field1913[var9], var5.field1912[var9]);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([[IIIIZI)Lfx;")
   @ObfuscatedName("cw")
   public Model method5105(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      method5254(this);
      int var7 = var2 - this.field2488;
      int var8 = var2 + this.field2488;
      int var9 = var4 - this.field2488;
      int var10 = var4 + this.field2488;
      if (var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
         var7 >>= 7;
         var8 = var8 + 127 >> 7;
         var9 >>= 7;
         var10 = var10 + 127 >> 7;
         if (var1[var7][var9] == var3 && var1[var8][var9] == var3 && var1[var7][var10] == var3 && var1[var8][var10] == var3) {
            return this;
         } else {
            Model var11;
            if (var5) {
               var11 = new Model(this);
               var11.field2428 = new float[var11.field2422];
            } else {
               var11 = this;
            }

            if (var6 == 0) {
               for (int var12 = 0; var12 < var11.field2422; var12++) {
                  int var13 = (int)this.field2425[var12] + var2;
                  int var14 = (int)this.field2424[var12] + var4;
                  int var15 = var13 & 127;
                  int var16 = var14 & 127;
                  int var17 = var13 >> 7;
                  int var18 = var14 >> 7;
                  int var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                  int var20 = var1[var17][var18 + 1] * (128 - var15) + var1[var17 + 1][var18 + 1] * var15 >> 7;
                  int var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                  var11.field2428[var12] = (int)this.field2428[var12] + var21 - var3;
               }
            } else {
               for (int var27 = 0; var27 < var11.field2422; var27++) {
                  int var28 = (-((int)this.field2428[var27]) << 16) / (this.field1741 * -1256242689);
                  if (var28 < var6) {
                     int var29 = (int)this.field2425[var27] + var2;
                     int var30 = (int)this.field2424[var27] + var4;
                     int var31 = var29 & 127;
                     int var32 = var30 & 127;
                     int var33 = var29 >> 7;
                     int var34 = var30 >> 7;
                     int var35 = var1[var33][var34] * (128 - var31) + var1[var33 + 1][var34] * var31 >> 7;
                     int var36 = var1[var33][var34 + 1] * (128 - var31) + var1[var33 + 1][var34 + 1] * var31 >> 7;
                     int var22 = var35 * (128 - var32) + var36 * var32 >> 7;
                     var11.field2428[var27] = (int)this.field2428[var27] + (var22 - var3) * (var6 - var28) / var6;
                  } else {
                     var11.field2428[var27] = this.field2428[var27];
                  }
               }
            }

            var11.method5129();
            return var11;
         }
      } else {
         return this;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Lfx;")
   @ObfuscatedName("ya")
   public Model method5289(boolean var1) {
      return this.method5106(var1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dt")
   void method5124() {
      if (this.field2491 != 2) {
         this.field2491 = 2;
         this.field2488 = 0;

         for (int var1 = 0; var1 < this.field2422; var1++) {
            int var2 = (int)this.field2425[var1];
            int var3 = (int)this.field2428[var1];
            int var4 = (int)this.field2424[var1];
            int var5 = var2 * var2 + var4 * var4 + var3 * var3;
            if (var5 > this.field2488) {
               this.field2488 = var5;
            }
         }

         this.field2488 = (int)(Math.sqrt(this.field2488) + 0.99);
         this.field2487 = this.field2488;
         this.field2489 = this.field2488 + this.field2488;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIJ)V")
   @ObfuscatedName("br")
   public void method5290(int var1, classUP var2, classUP var3, int var4, int var5, int var6, long var7) {
      this.method5192(var1, var2, var3, var4, var5, var6, var7);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dr")
   void method5125() {
      if (this.field2491 != 2) {
         this.field2491 = 2;
         this.field2488 = 0;

         for (int var1 = 0; var1 < this.field2422; var1++) {
            int var2 = (int)this.field2425[var1];
            int var3 = (int)this.field2428[var1];
            int var4 = (int)this.field2424[var1];
            int var5 = var2 * var2 + var4 * var4 + var3 * var3;
            if (var5 > this.field2488) {
               this.field2488 = var5;
            }
         }

         this.field2488 = (int)(Math.sqrt(this.field2488) + 0.99);
         this.field2487 = this.field2488;
         this.field2489 = this.field2488 + this.field2488;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;I)Lfp;")
   @ObfuscatedName("tf")
   public static AABB method5119(Model var0, int var1) {
      if (var0 == null) {
         var0.method5121(var1);
      }

      for (AABB var2 = var0.field2495; var2 != null; var2 = var2.field2369) {
         if (var2.field2362 * 1384390485 == var1) {
            return var2;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(I[IIII)V")
   @ObfuscatedName("ev")
   void method5156(int var1, int[] var2, int var3, int var4, int var5) {
      int var6 = var2.length;
      if (var1 == 0) {
         int var21 = 0;
         field2484 = 0;
         field2486 = 0;
         field2485 = 0;

         for (int var25 = 0; var25 < var6; var25++) {
            int var29 = var2[var25];
            if (var29 < this.field2440.length) {
               int[] var33 = this.field2440[var29];

               for (int var37 = 0; var37 < var33.length; var37++) {
                  int var39 = var33[var37];
                  field2484 = field2484 + (int)this.field2425[var39];
                  field2486 = field2486 + (int)this.field2428[var39];
                  field2485 = field2485 + (int)this.field2424[var39];
                  var21++;
               }
            }
         }

         if (var21 > 0) {
            field2484 = field2484 / var21 + var3;
            field2486 = field2486 / var21 + var4;
            field2485 = field2485 / var21 + var5;
         } else {
            field2484 = var3;
            field2486 = var4;
            field2485 = var5;
         }
      } else if (var1 == 1) {
         for (int var20 = 0; var20 < var6; var20++) {
            int var24 = var2[var20];
            if (var24 < this.field2440.length) {
               int[] var28 = this.field2440[var24];

               for (int var32 = 0; var32 < var28.length; var32++) {
                  int var36 = var28[var32];
                  this.field2425[var36] = (int)this.field2425[var36] + var3;
                  this.field2428[var36] = (int)this.field2428[var36] + var4;
                  this.field2424[var36] = (int)this.field2424[var36] + var5;
               }
            }
         }
      } else if (var1 == 2) {
         for (int var19 = 0; var19 < var6; var19++) {
            int var23 = var2[var19];
            if (var23 < this.field2440.length) {
               int[] var27 = this.field2440[var23];

               for (int var31 = 0; var31 < var27.length; var31++) {
                  int var35 = var27[var31];
                  this.field2425[var35] = (int)this.field2425[var35] - field2484;
                  this.field2428[var35] = (int)this.field2428[var35] - field2486;
                  this.field2424[var35] = (int)this.field2424[var35] - field2485;
                  int var38 = (var3 & 0xFF) * 8;
                  int var13 = (var4 & 0xFF) * 8;
                  int var14 = (var5 & 0xFF) * 8;
                  if (var14 != 0) {
                     int var15 = field2463[var14];
                     int var16 = field2471[var14];
                     int var17 = (int)this.field2428[var35] * var15 + (int)this.field2425[var35] * var16 >> 16;
                     this.field2428[var35] = (int)this.field2428[var35] * var16 - (int)this.field2425[var35] * var15 >> 16;
                     this.field2425[var35] = var17;
                  }

                  if (var38 != 0) {
                     int var40 = field2463[var38];
                     int var42 = field2471[var38];
                     int var44 = (int)this.field2428[var35] * var42 - (int)this.field2424[var35] * var40 >> 16;
                     this.field2424[var35] = (int)this.field2428[var35] * var40 + (int)this.field2424[var35] * var42 >> 16;
                     this.field2428[var35] = var44;
                  }

                  if (var13 != 0) {
                     int var41 = field2463[var13];
                     int var43 = field2471[var13];
                     int var45 = (int)this.field2424[var35] * var41 + (int)this.field2425[var35] * var43 >> 16;
                     this.field2424[var35] = (int)this.field2424[var35] * var43 - (int)this.field2425[var35] * var41 >> 16;
                     this.field2425[var35] = var45;
                  }

                  this.field2425[var35] = (int)this.field2425[var35] + field2484;
                  this.field2428[var35] = (int)this.field2428[var35] + field2486;
                  this.field2424[var35] = (int)this.field2424[var35] + field2485;
               }
            }
         }
      } else if (var1 == 3) {
         for (int var18 = 0; var18 < var6; var18++) {
            int var22 = var2[var18];
            if (var22 < this.field2440.length) {
               int[] var26 = this.field2440[var22];

               for (int var30 = 0; var30 < var26.length; var30++) {
                  int var34 = var26[var30];
                  this.field2425[var34] = (int)this.field2425[var34] - field2484;
                  this.field2428[var34] = (int)this.field2428[var34] - field2486;
                  this.field2424[var34] = (int)this.field2424[var34] - field2485;
                  this.field2425[var34] = (int)this.field2425[var34] * var3 / 128;
                  this.field2428[var34] = (int)this.field2428[var34] * var4 / 128;
                  this.field2424[var34] = (int)this.field2424[var34] * var5 / 128;
                  this.field2425[var34] = (int)this.field2425[var34] + field2484;
                  this.field2428[var34] = (int)this.field2428[var34] + field2486;
                  this.field2424[var34] = (int)this.field2424[var34] + field2485;
               }
            }
         }
      } else if (var1 == 5) {
         if (this.field2441 != null && this.field2442 != null) {
            for (int var7 = 0; var7 < var6; var7++) {
               int var8 = var2[var7];
               if (var8 < this.field2441.length) {
                  int[] var9 = this.field2441[var8];

                  for (int var10 = 0; var10 < var9.length; var10++) {
                     int var11 = var9[var10];
                     int var12 = (this.field2442[var11] & 255) + var3 * 8;
                     if (var12 < 0) {
                        var12 = 0;
                     } else if (var12 > 255) {
                        var12 = 255;
                     }

                     this.field2442[var11] = (byte)var12;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("db")
   void method5126() {
      if (this.field2491 != 2) {
         this.field2491 = 2;
         this.field2488 = 0;

         for (int var1 = 0; var1 < this.field2422; var1++) {
            int var2 = (int)this.field2425[var1];
            int var3 = (int)this.field2428[var1];
            int var4 = (int)this.field2424[var1];
            int var5 = var2 * var2 + var4 * var4 + var3 * var3;
            if (var5 > this.field2488) {
               this.field2488 = var5;
            }
         }

         this.field2488 = (int)(Math.sqrt(this.field2488) + 0.99);
         this.field2487 = this.field2488;
         this.field2489 = this.field2488 + this.field2488;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("en")
   public void method5132() {
      this.field2491 = 0;
      this.field2495 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("cf")
   void method5098(Model var1) {
      int var2 = this.field2427.length;
      if (this.field2429 == null && (var1.field2429 != null || this.field2418 != var1.field2418)) {
         this.field2429 = new byte[var2];
         Arrays.fill(this.field2429, this.field2418);
      }

      if (this.field2442 == null && var1.field2442 != null) {
         this.field2442 = new byte[var2];
         Arrays.fill(this.field2442, (byte)0);
      }

      if (this.field2435 == null && var1.field2435 != null) {
         this.field2435 = new short[var2];
         Arrays.fill(this.field2435, (short)-1);
      }

      if (this.field2434 == null && var1.field2434 != null) {
         this.field2434 = new byte[var2];
         Arrays.fill(this.field2434, (byte)-1);
      }

      if (this.field2436 == null && var1.field2436 != null) {
         this.field2436 = new byte[var2];
         Arrays.fill(this.field2436, (byte)0);
      }
   }

   public int[] getFaceIndices3() {
      return this.field2431;
   }

   public byte[] getFaceRenderPriorities() {
      return this.field2429;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("nw")
   public static void method5204(Model var0) {
      if (var0 == null) {
         var0.getVertexNormalsZ();
      }

      for (int var1 = 0; var1 < var0.field2417; var1++) {
         if (!field2447[var1]) {
            var0.method5217(var1);
         }
      }
   }

   public int getSceneId() {
      return this.field2493;
   }

   @ObfuscatedSignature(descriptor = "(Lfz;)V")
   @ObfuscatedName("co")
   void method5243(classFZ var1) {
      this.field2421 = var1;
   }

   public byte getOverrideHue() {
      return this.field2420 != null ? this.field2420.field2298 : 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dk")
   void method5127() {
      if (this.field2491 != 2) {
         this.field2491 = 2;
         this.field2488 = 0;

         for (int var1 = 0; var1 < this.field2422; var1++) {
            int var2 = (int)this.field2425[var1];
            int var3 = (int)this.field2428[var1];
            int var4 = (int)this.field2424[var1];
            int var5 = var2 * var2 + var4 * var4 + var3 * var3;
            if (var5 > this.field2488) {
               this.field2488 = var5;
            }
         }

         this.field2488 = (int)(Math.sqrt(this.field2488) + 0.99);
         this.field2487 = this.field2488;
         this.field2489 = this.field2488 + this.field2488;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public void method5272() {
      if (this.field2501 == null && (client.field1021 & 4) == 4) {
         int var1 = this.getVerticesCount();
         this.field2501 = new int[var1];
         this.field2503 = new int[var1];
         this.field2502 = new int[var1];
         int[] var2 = this.getFaceIndices1();
         int[] var3 = this.getFaceIndices2();
         int[] var4 = this.getFaceIndices3();
         float[] var5 = this.getVerticesX();
         float[] var6 = this.getVerticesY();
         float[] var7 = this.getVerticesZ();

         for (int var8 = 0; var8 < this.getFaceCount(); var8++) {
            int var9 = var2[var8];
            int var10 = var3[var8];
            int var11 = var4[var8];
            int var12 = (int)(var5[var10] - var5[var9]);
            int var13 = (int)(var6[var10] - var6[var9]);
            int var14 = (int)(var7[var10] - var7[var9]);
            int var15 = (int)(var5[var11] - var5[var9]);
            int var16 = (int)(var6[var11] - var6[var9]);
            int var17 = (int)(var7[var11] - var7[var9]);
            int var18 = var13 * var17 - var16 * var14;
            int var19 = var14 * var15 - var17 * var12;

            int var20;
            for (var20 = var12 * var16 - var15 * var13;
               var18 > 8192 || var19 > 8192 || var20 > 8192 || var18 < -8192 || var19 < -8192 || var20 < -8192;
               var20 >>= 1
            ) {
               var18 >>= 1;
               var19 >>= 1;
            }

            int var21 = (int)Math.sqrt(var18 * var18 + var19 * var19 + var20 * var20);
            if (var21 <= 0) {
               var21 = 1;
            }

            var18 = var18 * 256 / var21;
            var19 = var19 * 256 / var21;
            var20 = var20 * 256 / var21;
            this.field2501[var9] = this.field2501[var9] + var18;
            this.field2503[var9] = this.field2503[var9] + var19;
            this.field2502[var9] = this.field2502[var9] + var20;
            this.field2501[var10] = this.field2501[var10] + var18;
            this.field2503[var10] = this.field2503[var10] + var19;
            this.field2502[var10] = this.field2502[var10] + var20;
            this.field2501[var11] = this.field2501[var11] + var18;
            this.field2503[var11] = this.field2503[var11] + var19;
            this.field2502[var11] = this.field2502[var11] + var20;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;Lfs;I)V")
   @ObfuscatedName("dv")
   public static void method5136(Model var0, classFS var1, int var2) {
      if (var0.field2440 != null) {
         if (var2 != -1) {
            classET var3 = var1.field2390[var2];
            classEM var4 = var3.field1909;
            field2484 = 0;
            field2486 = 0;
            field2485 = 0;

            for (int var5 = 0; var5 < var3.field1911; var5++) {
               int var6 = var3.field1910[var5];
               method5260(var0, var4.field1783[var6], var4.field1785[var6], var3.field1907[var5], var3.field1913[var5], var3.field1912[var5]);
            }

            var0.method5129();
         }
      }
   }

   public int[] getTexIndices2() {
      return this.field2438;
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("bx")
   public void method5137(classSN var1, int var2) {
      classEM var3 = var1.field5981;
      classEF var4 = var3.method3948((byte)16);
      if (var4 != null) {
         var4.method3879(var1, var2, -1468766705);
         this.method5143(var4, var1.method10559(2107780364));
      }

      if (var1.method10562(-397143478)) {
         this.method5267(var1, var2);
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("dl")
   void method5141(classSN var1, int var2) {
      classEM var3 = var1.field5981;

      for (int var4 = 0; var4 < var3.field1784 * 1690200285; var4++) {
         int var5 = var3.field1783[var4];
         if (var5 == 5
            && var1.field6004 != null
            && var1.field6004[var4] != null
            && var1.field6004[var4][0] != null
            && this.field2441 != null
            && this.field2442 != null) {
            classSU var6 = var1.field6004[var4][0];

            for (int var10 : var3.field1785[var4]) {
               if (var10 < this.field2441.length) {
                  int[] var11 = this.field2441[var10];

                  for (int var12 = 0; var12 < var11.length; var12++) {
                     int var13 = var11[var12];
                     int var14 = (int)((this.field2442[var13] & 255) + var6.method10709(var2, -792518208) * 255.0F);
                     if (var14 < 0) {
                        var14 = 0;
                     } else if (var14 > 255) {
                        var14 = 1294318459;
                     }

                     this.field2442[var13] = (byte)var14;
                  }
               }
            }
         }
      }
   }

   public int getDiameter() {
      return this.field2489;
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("dq")
   void method5142(classSN var1, int var2) {
      classEM var3 = var1.field5981;

      for (int var4 = 0; var4 < var3.field1784 * 1690200285; var4++) {
         int var5 = var3.field1783[var4];
         if (var5 == 5
            && var1.field6004 != null
            && var1.field6004[var4] != null
            && var1.field6004[var4][0] != null
            && this.field2441 != null
            && this.field2442 != null) {
            classSU var6 = var1.field6004[var4][0];

            for (int var10 : var3.field1785[var4]) {
               if (var10 < this.field2441.length) {
                  int[] var11 = this.field2441[var10];

                  for (int var12 = 0; var12 < var11.length; var12++) {
                     int var13 = var11[var12];
                     int var14 = (int)((this.field2442[var13] & 255) + var6.method10709(var2, -2135412554) * 255.0F);
                     if (var14 < 0) {
                        var14 = 0;
                     } else if (var14 > 255) {
                        var14 = 255;
                     }

                     this.field2442[var13] = (byte)var14;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("cs")
   public void method5247(float var1) {
      if (var1 != 1.0F) {
         for (int var2 = 0; var2 < this.field2422; var2++) {
            this.field2428[var2] = this.field2428[var2] * var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("sn")
   public Model method5273() {
      return this.field2496;
   }

   @ObfuscatedSignature(descriptor = "(Lfs;ILfs;I[I)V")
   @ObfuscatedName("dp")
   public void method5146(classFS var1, int var2, classFS var3, int var4, int[] var5) {
      if (var2 != -1) {
         if (var5 != null && var4 != -1) {
            classET var6 = var1.field2390[var2];
            classET var7 = var3.field2390[var4];
            classEM var8 = var6.field1909;
            field2484 = 0;
            field2486 = 0;
            field2485 = 0;
            int var9 = 0;
            int var10 = var5[var9++];

            for (int var11 = 0; var11 < var6.field1911; var11++) {
               int var12 = var6.field1910[var11];

               while (var12 > var10) {
                  var10 = var5[var9++];
               }

               if (var12 != var10 || var8.field1783[var12] == 0) {
                  method5260(this, var8.field1783[var12], var8.field1785[var12], var6.field1907[var11], var6.field1913[var11], var6.field1912[var11]);
               }
            }

            field2484 = 0;
            field2486 = 0;
            field2485 = 0;
            var9 = 0;
            var10 = var5[var9++];

            for (int var17 = 0; var17 < var7.field1911; var17++) {
               int var18 = var7.field1910[var17];

               while (var18 > var10) {
                  var10 = var5[var9++];
               }

               if (var18 == var10 || var8.field1783[var18] == 0) {
                  method5260(this, var8.field1783[var18], var8.field1785[var18], var7.field1907[var17], var7.field1913[var17], var7.field1912[var17]);
               }
            }

            this.method5129();
         } else {
            this.method5135(var1, var2);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfs;ILfs;I[I)V")
   @ObfuscatedName("ey")
   public void method5147(classFS var1, int var2, classFS var3, int var4, int[] var5) {
      if (var2 != -1) {
         if (var5 != null && var4 != -1) {
            classET var6 = var1.field2390[var2];
            classET var7 = var3.field2390[var4];
            classEM var8 = var6.field1909;
            field2484 = 0;
            field2486 = 0;
            field2485 = 0;
            int var9 = 0;
            int var10 = var5[var9++];

            for (int var11 = 0; var11 < var6.field1911; var11++) {
               int var12 = var6.field1910[var11];

               while (var12 > var10) {
                  var10 = var5[var9++];
               }

               if (var12 != var10 || var8.field1783[var12] == 0) {
                  method5260(this, var8.field1783[var12], var8.field1785[var12], var6.field1907[var11], var6.field1913[var11], var6.field1912[var11]);
               }
            }

            field2484 = 0;
            field2486 = 0;
            field2485 = 0;
            var9 = 0;
            var10 = var5[var9++];

            for (int var17 = 0; var17 < var7.field1911; var17++) {
               int var18 = var7.field1910[var17];

               while (var18 > var10) {
                  var10 = var5[var9++];
               }

               if (var18 == var10 || var8.field1783[var18] == 0) {
                  method5260(this, var8.field1783[var18], var8.field1785[var18], var7.field1907[var17], var7.field1913[var17], var7.field1912[var17]);
               }
            }

            this.method5129();
         } else {
            this.method5135(var1, var2);
         }
      }
   }

   Model() {
      this.field2417 = 0;
      this.field2418 = 0;
      this.field2416 = 0;
      this.field2419 = false;
      this.field2420 = null;
      this.field2421 = classFZ.field2513;
      this.field2423 = 0;
   }

   public byte getOverrideAmount() {
      return this.field2420 != null ? this.field2420.field2301 : 0;
   }

   @ObfuscatedSignature(descriptor = "(Lfl;S)V")
   @ObfuscatedName("gn")
   public void method5232(classFL var1, short var2) {
      if (var1 == null) {
         classKU.method6608(this);
      } else {
         if (this.field2420 == null) {
            this.field2420 = new classFL();
         }

         this.field2420.method4843(var1);
         this.field2497 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfs;I[IZ)V")
   @ObfuscatedName("es")
   public void method5153(classFS var1, int var2, int[] var3, boolean var4) {
      if (var3 == null) {
         this.method5135(var1, var2);
      } else {
         classET var5 = var1.field2390[var2];
         classEM var6 = var5.field1909;
         int var7 = 0;
         int var8 = var3[var7++];
         field2484 = 0;
         field2486 = 0;
         field2485 = 0;

         for (int var9 = 0; var9 < var5.field1911; var9++) {
            int var10 = var5.field1910[var9];

            while (var10 > var8) {
               var8 = var3[var7++];
            }

            if (var4) {
               if (var10 == var8 || var6.field1783[var10] == 0) {
                  method5260(this, var6.field1783[var10], var6.field1785[var10], var5.field1907[var9], var5.field1913[var9], var5.field1912[var9]);
               }
            } else if (var10 != var8 || var6.field1783[var10] == 0) {
               method5260(this, var6.field1783[var10], var6.field1785[var10], var5.field1907[var9], var5.field1913[var9], var5.field1912[var9]);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ep")
   public void method5176(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2422; var4++) {
         this.field2425[var4] = (int)this.field2425[var4] + var1;
         this.field2428[var4] = (int)this.field2428[var4] + var2;
         this.field2424[var4] = (int)this.field2424[var4] + var3;
      }

      this.method5129();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;IIIIIII)V")
   @ObfuscatedName("ps")
   public static void method5185(Model var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var0.field2491 != 2) {
         var0.method5263();
      }

      int var8 = classFH.method4725();
      int var9 = classFH.method4730();
      int var10 = field2463[var1];
      int var11 = field2471[var1];
      int var12 = field2463[var2];
      int var13 = field2471[var2];
      int var14 = field2463[var3];
      int var15 = field2471[var3];
      int var16 = field2463[var4];
      int var17 = field2471[var4];
      int var18 = var6 * var16 + var7 * var17 >> 16;

      for (int var19 = 0; var19 < var0.field2422; var19++) {
         int var20 = (int)var0.field2425[var19];
         int var21 = (int)var0.field2428[var19];
         int var22 = (int)var0.field2424[var19];
         if (var3 != 0) {
            int var23 = var21 * var14 + var20 * var15 >> 16;
            var21 = var21 * var15 - var20 * var14 >> 16;
            var20 = var23;
         }

         if (var1 != 0) {
            int var29 = var21 * var11 - var22 * var10 >> 16;
            var22 = var21 * var10 + var22 * var11 >> 16;
            var21 = var29;
         }

         if (var2 != 0) {
            int var30 = var22 * var12 + var20 * var13 >> 16;
            var22 = var22 * var13 - var20 * var12 >> 16;
            var20 = var30;
         }

         var20 += var5;
         var21 += var6;
         var22 += var7;
         int var31 = var21 * var17 - var22 * var16 >> 16;
         var22 = var21 * var16 + var22 * var17 >> 16;
         field2449[var19] = var22 - var18;
         field2464[var19] = var8 + var20 * classFH.method4747() / var22;
         field2450[var19] = var9 + var31 * classFH.method4747() / var22;
         field2448[var19] = var22;
         if (var0.field2435 != null) {
            field2468[var19] = var20;
            field2451[var19] = var31;
            field2474[var19] = var22;
         }
      }

      try {
         var0.method5202(false, false, false, 0L);
      } catch (Exception var24) {
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gm")
   public void method5238(int var1) {
      if (var1 != 0) {
         for (int var2 = 0; var2 < this.field2422; var2++) {
            this.field2428[var2] = (int)this.field2428[var2] + var1;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("ep")
   public static void method5165(Model var0) {
      if (var0 == null) {
         var0.method5124();
      }

      for (int var1 = 0; var1 < var0.field2422; var1++) {
         int var2 = (int)var0.field2424[var1];
         var0.field2424[var1] = var0.field2425[var1];
         var0.field2425[var1] = -var2;
      }

      var0.method5129();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dz")
   public void method5159() {
      for (int var1 = 0; var1 < this.field2422; var1++) {
         int var2 = (int)this.field2425[var1];
         this.field2425[var1] = this.field2424[var1];
         this.field2424[var1] = -var2;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   void method5117(int var1) {
      AABB var2 = this.method5118(var1);
      if (var2 == null) {
         int var3 = 0;
         int var4 = 0;
         int var5 = 0;
         int var6 = 0;
         int var7 = 0;
         int var8 = 0;
         int var9 = field2471[var1];
         int var10 = field2463[var1];

         for (int var11 = 0; var11 < this.field2422; var11++) {
            int var12 = classAU.method692((int)this.field2425[var11], (int)this.field2424[var11], var9, var10, -1418703602);
            int var13 = (int)this.field2428[var11];
            int var14 = classUE.method11340((int)this.field2425[var11], (int)this.field2424[var11], var9, var10, 1397515973);
            if (var12 < var3) {
               var3 = var12;
            }

            if (var12 > var6) {
               var6 = var12;
            }

            if (var13 < var4) {
               var4 = var13;
            }

            if (var13 > var7) {
               var7 = var13;
            }

            if (var14 < var5) {
               var5 = var14;
            }

            if (var14 > var8) {
               var8 = var14;
            }
         }

         var2 = new AABB(var1, (var6 + var3) / 2, (var7 + var4) / 2, (var8 + var5) / 2, (var6 - var3 + 1) / 2, (var7 - var4 + 1) / 2, (var8 - var5 + 1) / 2);
         byte var16 = 32;
         if (var2.field2366 * 1301855055 < 32) {
            var2.field2366 = 854554080;
         }

         if (var2.field2368 * 2105673973 < 32) {
            var2.field2368 = -1282643040;
         }

         if (this.field2419) {
            byte var17 = 8;
            var2.field2366 += -860103304;
            var2.field2368 += 753081064;
         }

         var2.field2369 = this.field2495;
         this.field2495 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gt")
   public void method5160() {
      for (int var1 = 0; var1 < this.field2422; var1++) {
         int var2 = (int)this.field2425[var1];
         this.field2425[var1] = this.field2424[var1];
         this.field2424[var1] = -var2;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("eh")
   public void method5162() {
      for (int var1 = 0; var1 < this.field2422; var1++) {
         this.field2425[var1] = -((int)this.field2425[var1]);
         this.field2424[var1] = -((int)this.field2424[var1]);
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("el")
   public void method5163() {
      for (int var1 = 0; var1 < this.field2422; var1++) {
         this.field2425[var1] = -((int)this.field2425[var1]);
         this.field2424[var1] = -((int)this.field2424[var1]);
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(Lfx;IFFFFFFFFFIII)V")
   @ObfuscatedName("oc")
   public static void method5221(
      Model var0,
      int var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      int var11,
      int var12,
      int var13
   ) {
      if (var0 == null) {
         var0.method5223(var1, var2, var2, var2, var2, var2, var2, var2, var2, var2, var1, var1, var1);
      }

      if (var0.field2435 != null && var0.field2435[var1] != -1) {
         int var15;
         int var16;
         int var18;
         if (var0.field2434 != null && var0.field2434[var1] != -1) {
            int var17 = var0.field2434[var1] & 255;
            var18 = var0.field2437[var17];
            var15 = var0.field2438[var17];
            var16 = var0.field2439[var17];
         } else {
            var18 = var0.field2427[var1];
            var15 = var0.field2430[var1];
            var16 = var0.field2431[var1];
         }

         if (var0.field2433[var1] == -1) {
            classFH.method4787(
               var2,
               var3,
               var4,
               var5,
               var6,
               var7,
               var8,
               var9,
               var10,
               var11,
               var11,
               var11,
               field2468[var18],
               field2468[var15],
               field2468[var16],
               field2451[var18],
               field2451[var15],
               field2451[var16],
               field2474[var18],
               field2474[var15],
               field2474[var16],
               var0.field2435[var1]
            );
         } else {
            classFH.method4787(
               var2,
               var3,
               var4,
               var5,
               var6,
               var7,
               var8,
               var9,
               var10,
               var11,
               var12,
               var13,
               field2468[var18],
               field2468[var15],
               field2468[var16],
               field2451[var18],
               field2451[var15],
               field2451[var16],
               field2474[var18],
               field2474[var15],
               field2474[var16],
               var0.field2435[var1]
            );
         }
      } else {
         boolean var14 = var0.method5219(var1);
         if (var0.field2433[var1] == -1 && var14) {
            classFH.method4793(var2, var3, var4, var5, var6, var7, var8, var9, var10, field2460[var0.field2432[var1]]);
         } else if (var0.field2433[var1] == -1) {
            classFH.method4782(var2, var3, var4, var5, var6, var7, var8, var9, var10, field2460[var0.field2432[var1]]);
         } else if (var14) {
            classFH.method4792(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
         } else {
            classFH.method4778(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
         }
      }
   }

   public boolean useBoundingBox() {
      return this.field2419;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("de")
   public void method5164() {
      for (int var1 = 0; var1 < this.field2422; var1++) {
         this.field2425[var1] = -((int)this.field2425[var1]);
         this.field2424[var1] = -((int)this.field2424[var1]);
      }

      this.method5129();
   }

   public int[] getFaceIndices2() {
      return this.field2430;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ft")
   public void method5166() {
      for (int var1 = 0; var1 < this.field2422; var1++) {
         int var2 = (int)this.field2424[var1];
         this.field2424[var1] = this.field2425[var1];
         this.field2425[var1] = -var2;
      }

      this.method5129();
   }

   public byte getOverrideSaturation() {
      return this.field2420 != null ? this.field2420.field2299 : 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ex")
   public void method5167() {
      for (int var1 = 0; var1 < this.field2422; var1++) {
         int var2 = (int)this.field2424[var1];
         this.field2424[var1] = this.field2425[var1];
         this.field2425[var1] = -var2;
      }

      this.method5129();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("dx")
   public static void method5205(Model var0) {
      if (var0 == null) {
         var0.getFaceIndices3();
      }

      for (int var1 = 0; var1 < var0.field2417; var1++) {
         if (!field2447[var1]) {
            var0.method5217(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ef")
   public void method5173(int var1) {
      int var2 = field2463[var1];
      int var3 = field2471[var1];

      for (int var4 = 0; var4 < this.field2422; var4++) {
         int var5 = (int)this.field2428[var4] * var3 - (int)this.field2424[var4] * var2 >> 16;
         this.field2424[var4] = (int)this.field2428[var4] * var2 + (int)this.field2424[var4] * var3 >> 16;
         this.field2428[var4] = var5;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(Lfx;Lsn;I)V")
   @ObfuscatedName("fw")
   public static void method5138(Model var0, classSN var1, int var2) {
      if (var0 == null) {
         var0.method5140(var1, var2);
      } else {
         classEM var3 = var1.field5981;
         classEF var4 = var3.method3948((byte)90);
         if (var4 != null) {
            var4.method3879(var1, var2, -1468766705);
            var0.method5143(var4, var1.method10559(-1499030123));
         }

         if (var1.method10562(1772783837)) {
            var0.method5267(var1, var2);
         }

         var0.method5129();
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("di")
   public void method5133() {
      this.field2491 = 0;
      this.field2495 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bz")
   final void method5217(int var1) {
      if (this.field2423 != -1) {
         if (this.field2442 == null) {
            classFH.field2255.field2211 = 0;
         } else {
            classFH.field2255.field2211 = (this.field2442[var1] == -1 ? 253 : this.field2442[var1]) & 255;
         }

         if (this.field2423 != 0) {
            classFH.field2255.field2211 = this.method5214(classFH.field2255.field2211);
         }

         if (classFH.field2255.field2215 != 1 || classFH.field2255.field2211 != 0 || this.field2423 != 0) {
            if (classFH.field2255.field2215 != 0 || classFH.field2255.field2211 == 0) {
               if (field2461[var1]) {
                  this.method5224(var1);
               } else {
                  int var2 = this.field2427[var1];
                  int var3 = this.field2430[var1];
                  int var4 = this.field2431[var1];
                  classFH.field2255.field2209 = field2443[var1];
                  int var5 = this.field2436 == null ? 0 : this.field2436[var1] * 2;
                  float var6 = classJE.method6398(field2448[var2] - var5, 2098849775);
                  float var7 = classJE.method6398(field2448[var3] - var5, 2113670730);
                  float var8 = classJE.method6398(field2448[var4] - var5, 2103903816);
                  method5221(
                     this,
                     var1,
                     field2450[var2],
                     field2450[var3],
                     field2450[var4],
                     field2464[var2],
                     field2464[var3],
                     field2464[var4],
                     var6,
                     var7,
                     var8,
                     this.field2432[var1],
                     this.field2426[var1],
                     this.field2433[var1]
                  );
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ZLfx;[B)Lfx;")
   @ObfuscatedName("mq")
   public Model method5291(boolean var1, Model var2, byte[] var3) {
      return this.method5112(var1, var2, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;III)V")
   @ObfuscatedName("no")
   public static void method5181(Model var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method5281(var1, var1, var1);
      } else {
         for (int var4 = 0; var4 < var0.field2422; var4++) {
            var0.field2425[var4] = (int)var0.field2425[var4] * var1 / 128;
            var0.field2428[var4] = (int)var0.field2428[var4] * var2 / 128;
            var0.field2424[var4] = (int)var0.field2424[var4] * var3 / 128;
         }

         var0.method5129();
      }
   }

   public int getVerticesCount() {
      return this.field2422;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;III)V")
   @ObfuscatedName("rm")
   public static void method5177(Model var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.setSceneId(var1);
      }

      for (int var4 = 0; var4 < var0.field2422; var4++) {
         var0.field2425[var4] = (int)var0.field2425[var4] + var1;
         var0.field2428[var4] = (int)var0.field2428[var4] + var2;
         var0.field2424[var4] = (int)var0.field2424[var4] + var3;
      }

      var0.method5129();
   }

   public int[] getFaceColors2() {
      return this.field2426;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;I)Lfp;")
   @ObfuscatedName("bt")
   public static AABB method5120(Model var0, int var1) {
      for (AABB var2 = var0.field2495; var2 != null; var2 = var2.field2369) {
         if (var2.field2362 * 1384390485 == var1) {
            return var2;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("ee")
   public void method5175(classUL var1) {
      classUZ var2 = classHZ.method6171(16777216);

      for (int var3 = 0; var3 < this.field2422; var3++) {
         classUZ.method11802(var2, (int)this.field2425[var3], (int)this.field2428[var3], (int)this.field2424[var3], 1706466493);
         var2.method11849(var1, 893551407);
         this.field2425[var3] = var2.field6425;
         this.field2428[var3] = var2.field6423;
         this.field2424[var3] = var2.field6424;
      }

      var2.method11797(1758606309);
      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("cl")
   public void method5230(byte var1) {
      this.field2423 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;Lef;I)V")
   @ObfuscatedName("pn")
   public static void method5229(Model var0, classEF var1, int var2) {
      if (var0.field2506 != null) {
         for (int var3 = 0; var3 < var0.field2422; var3++) {
            int[] var4 = var0.field2506[var3];
            if (var4 != null && var4.length != 0) {
               int[] var5 = var0.field2505[var3];
               field2473.method11640(-1382204550);

               for (int var6 = 0; var6 < var4.length; var6++) {
                  int var7 = var4[var6];
                  classSG var8 = var1.method3866(var7, (byte)32);
                  if (var8 != null) {
                     classUU.method11649(field2458, var5[var6] / 255.0F, -1290979281);
                     classUZ.method11857(field2475, var8.method10451(var2, 131168747), (byte)7);
                     field2475.method11656(field2458, -289464942);
                     classUU.method11707(field2473, field2475, (byte)-85);
                  }
               }

               method5274(var0, var3, field2473);
            }
         }
      }
   }

   public void drawOrtho(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      method5188(this, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("af")
   void method5099(Model var1) {
      int var2 = this.field2427.length;
      if (this.field2429 == null && (var1.field2429 != null || this.field2418 != var1.field2418)) {
         this.field2429 = new byte[var2];
         Arrays.fill(this.field2429, this.field2418);
      }

      if (this.field2442 == null && var1.field2442 != null) {
         this.field2442 = new byte[var2];
         Arrays.fill(this.field2442, (byte)0);
      }

      if (this.field2435 == null && var1.field2435 != null) {
         this.field2435 = new short[var2];
         Arrays.fill(this.field2435, (short)-1);
      }

      if (this.field2434 == null && var1.field2434 != null) {
         this.field2434 = new byte[var2];
         Arrays.fill(this.field2434, (byte)-1);
      }

      if (this.field2436 == null && var1.field2436 != null) {
         this.field2436 = new byte[var2];
         Arrays.fill(this.field2436, (byte)0);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ec")
   public void method5178(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2422; var4++) {
         this.field2425[var4] = (int)this.field2425[var4] + var1;
         this.field2428[var4] = (int)this.field2428[var4] + var2;
         this.field2424[var4] = (int)this.field2424[var4] + var3;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(Lem;Lsn;I[ZZZ)V")
   @ObfuscatedName("ea")
   public void method5151(classEM var1, classSN var2, int var3, boolean[] var4, boolean var5, boolean var6) {
      classEF var7 = var1.method3948((byte)-24);
      if (var7 != null) {
         var7.method3886(var2, var3, var4, var5, (byte)69);
         if (var6) {
            this.method5143(var7, var2.method10559(1853881563));
         }
      }

      if (!var5 && var2.method10562(1617374318)) {
         this.method5267(var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fl")
   public void method5179(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2422; var4++) {
         this.field2425[var4] = (int)this.field2425[var4] + var1;
         this.field2428[var4] = (int)this.field2428[var4] + var2;
         this.field2424[var4] = (int)this.field2424[var4] + var3;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(ZLfx;[B)Lfx;")
   @ObfuscatedName("dm")
   Model method5115(boolean var1, Model var2, byte[] var3) {
      var2.field2422 = this.field2422;
      var2.field2417 = this.field2417;
      var2.field2416 = this.field2416;
      if (var2.field2425 == null || var2.field2425.length < this.field2422) {
         var2.field2425 = new float[this.field2422 + 100];
         var2.field2428 = new float[this.field2422 + 100];
         var2.field2424 = new float[this.field2422 + 100];
      }

      for (int var4 = 0; var4 < this.field2422; var4++) {
         var2.field2425[var4] = this.field2425[var4];
         var2.field2428[var4] = this.field2428[var4];
         var2.field2424[var4] = this.field2424[var4];
      }

      if (var1) {
         var2.field2442 = this.field2442;
      } else {
         var2.field2442 = var3;
         if (this.field2442 == null) {
            for (int var5 = 0; var5 < this.field2417; var5++) {
               var2.field2442[var5] = 0;
            }
         } else {
            for (int var6 = 0; var6 < this.field2417; var6++) {
               var2.field2442[var6] = this.field2442[var6];
            }
         }
      }

      var2.field2427 = this.field2427;
      var2.field2430 = this.field2430;
      var2.field2431 = this.field2431;
      var2.field2432 = this.field2432;
      var2.field2426 = this.field2426;
      var2.field2433 = this.field2433;
      var2.field2429 = this.field2429;
      var2.field2434 = this.field2434;
      var2.field2435 = this.field2435;
      var2.field2436 = this.field2436;
      var2.field2418 = this.field2418;
      var2.field2437 = this.field2437;
      var2.field2438 = this.field2438;
      var2.field2439 = this.field2439;
      var2.field2440 = this.field2440;
      var2.field2441 = this.field2441;
      var2.field2506 = this.field2506;
      var2.field2505 = this.field2505;
      var2.field2419 = this.field2419;
      var2.field2420 = this.field2420;
      var2.field2497 = this.field2497;
      var2.field2423 = this.field2423;
      var2.method5129();
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fy")
   public void method5182(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2422; var4++) {
         this.field2425[var4] = (int)this.field2425[var4] * var1 / 128;
         this.field2428[var4] = (int)this.field2428[var4] * var2 / 128;
         this.field2424[var4] = (int)this.field2424[var4] * var3 / 128;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("do")
   public void method5139(classSN var1, int var2) {
      classEM var3 = var1.field5981;
      classEF var4 = var3.method3948((byte)27);
      if (var4 != null) {
         var4.method3879(var1, var2, -1468766705);
         this.method5143(var4, var1.method10559(1641569409));
      }

      if (var1.method10562(1651933478)) {
         this.method5267(var1, var2);
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;Z)Lfx;")
   @ObfuscatedName("uj")
   public Model method5253(rl21 var1, boolean var2) {
      if (!var2 && var1.field5722.length < this.field2417) {
         var1.field5722 = new byte[this.field2417 + 100];
      }

      return this.method5291(var2, var1.field5721, var1.field5722);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fo")
   public void method5180(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2422; var4++) {
         this.field2425[var4] = (int)this.field2425[var4] + var1;
         this.field2428[var4] = (int)this.field2428[var4] + var2;
         this.field2424[var4] = (int)this.field2424[var4] + var3;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(ZZZJ)V")
   @ObfuscatedName("fv")
   final void method5202(boolean var1, boolean var2, boolean var3, long var4) {
      if (this.field2489 < 6000) {
         for (int var6 = 0; var6 < this.field2489; var6++) {
            field2453[var6] = 0;
         }

         int var30 = var3 ? 20 : 5;
         int var7 = classFH.method4751();

         for (int var8 = 0; var8 < this.field2417; var8++) {
            if (this.field2433[var8] == -2) {
               field2447[var8] = true;
            } else {
               if (this.field2421 != classFZ.field2515) {
                  if (classFH.field2255.field2215 == 1 && (this.field2442 == null || this.field2442[var8] == 0) && this.field2423 == 0) {
                     field2447[var8] = true;
                     continue;
                  }

                  if (classFH.field2255.field2215 == 0 && (this.field2442 != null && this.field2442[var8] != 0 || this.field2423 != 0)) {
                     field2447[var8] = true;
                     continue;
                  }
               }

               int var9 = this.field2427[var8];
               int var10 = this.field2430[var8];
               int var11 = this.field2431[var8];
               float var12 = field2464[var9];
               float var13 = field2464[var10];
               float var14 = field2464[var11];
               field2461[var8] = var1 && (var12 == -5000.0F || var13 == -5000.0F || var14 == -5000.0F);
               if (field2461[var8]) {
                  int var15 = field2468[var9];
                  int var16 = field2468[var10];
                  int var17 = field2468[var11];
                  int var18 = field2451[var9];
                  int var19 = field2451[var10];
                  int var20 = field2451[var11];
                  int var21 = field2474[var9];
                  int var22 = field2474[var10];
                  int var23 = field2474[var11];
                  var15 -= var16;
                  var17 -= var16;
                  var18 -= var19;
                  var20 -= var19;
                  var21 -= var22;
                  var23 -= var22;
                  int var24 = var18 * var23 - var21 * var20;
                  int var25 = var21 * var17 - var15 * var23;
                  int var26 = var15 * var20 - var18 * var17;
                  field2447[var8] = var16 * var24 + var19 * var25 + var22 * var26 <= 0.0F;
               } else {
                  field2447[var8] = (var12 - var13) * (field2450[var11] - field2450[var10]) - (field2450[var9] - field2450[var10]) * (var14 - var13) <= 0.0F;
                  field2443[var8] = var12 < 0.0F || var13 < 0.0F || var14 < 0.0F || var12 > var7 || var13 > var7 || var14 > var7;
                  if (var2
                     && classDG.method3086(
                        (int)field2450[var9], (int)field2450[var10], (int)field2450[var11], (int)var12, (int)var13, (int)var14, var30, -2043627994
                     )) {
                     int var35 = (int)(field2448[var9] + field2448[var10] + field2448[var11]) / 3;
                     classGI.method5479(var4, var35, -340158988);
                  }
               }
            }
         }

         short var31 = this.field2497;
         boolean var32 = false;
         if (classFH.field2255.field2213.method4857()) {
            this.field2497 = (short)this.field2417;
         } else if (this.field2420 != null && this.field2420.method4857()) {
            classFH.field2255.field2213.method4843(this.field2420);
            var32 = true;
         }

         try {
            if (!classFH.method4755()) {
               classMO.method7679(this, true);
            } else {
               classFZ var33 = this.field2421;
               if (var33 == classFZ.field2513) {
                  if (classFH.field2255.field2215 != 0 && this.field2442 != null) {
                     var33 = classFZ.field2514;
                  } else {
                     var33 = classFZ.field2516;
                  }
               }

               switch (var33.field2512) {
                  case 1:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     classMO.method7679(this, false);
                     break;
                  case 2:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = 0;
                     classMO.method7679(this, true);
                     classFH.field2255.field2208 = 0;
                     classFH.field2255.field2214 = -1;
                     classMO.method7679(this, true);
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     break;
                  case 3:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     method5207(this);
                     break;
                  case 4:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = 0;
                     method5207(this);
               }
            }
         } finally {
            this.field2497 = var31;
            if (var32) {
               classFL.method4856(classFH.field2255.field2213);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIJ)V")
   @ObfuscatedName("fh")
   void method5193(int var1, classUP var2, classUP var3, int var4, int var5, int var6, long var7) {
      if (classFH.field2255.field2215 != 1 || this.field2442 != null || this.field2423 != 0) {
         int var9 = classUP.method11506(var2, 2030397284);
         int var10 = var2.method11507((byte)-34);
         int var11 = classUP.method11506(var3, 843730827);
         int var12 = var3.method11507((byte)16);
         float var13 = var2.method11510(2122430262);
         float var14 = var2.method11512((byte)115);
         float var15 = var3.method11510(2100578267);
         float var16 = var3.method11512((byte)118);
         if (this.field2491 != 1) {
            method5254(this);
         }

         this.method5117(var1);
         int var17 = var6 * var12 - var4 * var11 >> 16;
         int var18 = var5 * var9 + var17 * var10 >> 16;
         int var19 = this.field2488 * var10 >> 16;
         int var20 = var18 + var19;
         if (var20 > 50 && var18 < classFQ.method4948()) {
            int var21 = var6 * var11 + var4 * var12 >> 16;
            int var22 = (var21 - this.field2488) * classFH.method4747();
            if (var22 / var20 < classFH.method4736()) {
               int var23 = (var21 + this.field2488) * classFH.method4747();
               if (var23 / var20 > classFH.method4732()) {
                  int var24 = var5 * var10 - var17 * var9 >> 16;
                  int var25 = this.field2488 * var9 >> 16;
                  int var26 = var25 + (this.field2490 * var10 >> 16);
                  int var27 = (var24 + var26) * classFH.method4747();
                  if (var27 / var20 > classFH.method4744()) {
                     int var28 = var25 + (this.field1741 * -1256242689 * var10 >> 16);
                     int var29 = (var24 - var28) * classFH.method4747();
                     if (var29 / var20 < classFH.method4740()) {
                        int var30 = var19 + (this.field1741 * -1256242689 * var9 >> 16);
                        boolean var31 = false;
                        boolean var32 = false;
                        if (var18 - var30 <= 50) {
                           var32 = true;
                        }

                        boolean var33 = var32 || this.field2435 != null;
                        int var34 = classCS.method2587(-290355318);
                        int var35 = 1170217135 * classFO.field2346;
                        boolean var37 = classAS.method657(2087470704);
                        boolean var39 = 0L != var7;
                        if (var39) {
                           boolean var40 = 1 == (int)(var7 >>> 19 & 1L);
                           var39 = !var40;
                        }

                        boolean var55 = false;
                        if (var39 && var37) {
                           boolean var41 = false;
                           if (field2446) {
                              var41 = Projectile.method1224(
                                 this,
                                 var1,
                                 var4,
                                 var5,
                                 var6,
                                 var13,
                                 var14,
                                 var15,
                                 var16,
                                 classFH.method4725(),
                                 classFH.method4730(),
                                 classFH.method4747(),
                                 -1756217321
                              );
                           } else {
                              int var42 = var18 - var19;
                              if (var42 <= 50) {
                                 var42 = 50;
                              }

                              if (var21 > 0) {
                                 var22 /= var20;
                                 var23 /= var42;
                              } else {
                                 var23 /= var20;
                                 var22 /= var42;
                              }

                              if (var24 > 0) {
                                 var29 /= var20;
                                 var27 /= var42;
                              } else {
                                 var27 /= var20;
                                 var29 /= var42;
                              }

                              int var43 = var34 - classFH.method4725();
                              int var44 = var35 - classFH.method4730();
                              if (var43 > var22 && var43 < var23 && var44 > var29 && var44 < var27) {
                                 var41 = true;
                              }
                           }

                           if (var41) {
                              if (this.field2419) {
                                 int var60 = classFH.method4725();
                                 int var45 = classFH.method4730();
                                 int var46 = classFH.method4747();
                                 classEV.method4118(var13, var14, var15, var16, var60, var45, var46, (byte)-5);
                                 int var57 = classSZ.method10772(
                                    var4,
                                    var5,
                                    var6,
                                    client.field1069 * -1163968665,
                                    -639123865 * classDX.field1660,
                                    -1674330987 * classDU.field1631,
                                    1788691143 * classDB.field1373,
                                    1768347607 * classYJ.field6992,
                                    -1569185711 * World.field1163,
                                    this.method5118(var1),
                                    1366188944
                                 );
                                 int var47 = var57 + 32;
                                 classGI.method5479(var7, var47, -55367300);
                              } else {
                                 var55 = true;
                              }
                           }
                        }

                        int var56 = classFH.method4725();
                        int var58 = classFH.method4730();
                        float var59 = 0.0F;
                        float var61 = 0.0F;
                        if (var1 != 0) {
                           var59 = classFH.field2253[var1];
                           var61 = classFH.field2257[var1];
                        }

                        for (int var62 = 0; var62 < this.field2422; var62++) {
                           float var63 = (int)this.field2425[var62];
                           float var66 = (int)this.field2428[var62];
                           float var48 = (int)this.field2424[var62];
                           if (var1 != 0) {
                              float var49 = var48 * var59 + var63 * var61;
                              var48 = var48 * var61 - var63 * var59;
                              var63 = var49;
                           }

                           var63 += var4;
                           var66 += var5;
                           var48 += var6;
                           float var71 = var48 * var15 + var63 * var16;
                           var48 = var48 * var16 - var63 * var15;
                           float var72 = var66 * var14 - var48 * var13;
                           var48 = var66 * var13 + var48 * var14;
                           var31 |= method5198(this, var62, var71, var72, var48, var18, var56, var58, var33);
                        }

                        try {
                           this.method5202(var31, var55, this.field2419, var7);
                        } catch (Exception var50) {
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;I)V")
   @ObfuscatedName("yo")
   public static void method5239(Model var0, int var1) {
      if (var0 == null) {
         var0.method5216(var1);
      }

      if (var1 != 0) {
         for (int var2 = 0; var2 < var0.field2422; var2++) {
            var0.field2428[var2] = (int)var0.field2428[var2] + var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIJ)V")
   @ObfuscatedName("fq")
   void method5194(int var1, classUP var2, classUP var3, int var4, int var5, int var6, long var7) {
      if (classFH.field2255.field2215 != 1 || this.field2442 != null || this.field2423 != 0) {
         int var9 = classUP.method11506(var2, 1594011388);
         int var10 = var2.method11507((byte)-82);
         int var11 = classUP.method11506(var3, 1201365912);
         int var12 = var3.method11507((byte)-7);
         float var13 = var2.method11510(2080224168);
         float var14 = var2.method11512((byte)108);
         float var15 = var3.method11510(2096610087);
         float var16 = var3.method11512((byte)116);
         if (this.field2491 != 1) {
            method5254(this);
         }

         this.method5117(var1);
         int var17 = var6 * var12 - var4 * var11 >> 16;
         int var18 = var5 * var9 + var17 * var10 >> 16;
         int var19 = this.field2488 * var10 >> 16;
         int var20 = var18 + var19;
         if (var20 > 50 && var18 < classFQ.method4948()) {
            int var21 = var6 * var11 + var4 * var12 >> 16;
            int var22 = (var21 - this.field2488) * classFH.method4747();
            if (var22 / var20 < classFH.method4736()) {
               int var23 = (var21 + this.field2488) * classFH.method4747();
               if (var23 / var20 > classFH.method4732()) {
                  int var24 = var5 * var10 - var17 * var9 >> 16;
                  int var25 = this.field2488 * var9 >> 16;
                  int var26 = var25 + (this.field2490 * var10 >> 16);
                  int var27 = (var24 + var26) * classFH.method4747();
                  if (var27 / var20 > classFH.method4744()) {
                     int var28 = var25 + (this.field1741 * -1256242689 * var10 >> 16);
                     int var29 = (var24 - var28) * classFH.method4747();
                     if (var29 / var20 < classFH.method4740()) {
                        int var30 = var19 + (this.field1741 * -1256242689 * var9 >> 16);
                        boolean var31 = false;
                        boolean var32 = false;
                        if (var18 - var30 <= 50) {
                           var32 = true;
                        }

                        boolean var33 = var32 || this.field2435 != null;
                        int var34 = classCS.method2587(-1053495833);
                        int var35 = 1170217135 * classFO.field2346;
                        boolean var37 = classAS.method657(1941153625);
                        boolean var39 = 0L != var7;
                        if (var39) {
                           boolean var40 = 1 == (int)(var7 >>> 19 & 1L);
                           var39 = !var40;
                        }

                        boolean var55 = false;
                        if (var39 && var37) {
                           boolean var41 = false;
                           if (field2446) {
                              var41 = Projectile.method1224(
                                 this,
                                 var1,
                                 var4,
                                 var5,
                                 var6,
                                 var13,
                                 var14,
                                 var15,
                                 var16,
                                 classFH.method4725(),
                                 classFH.method4730(),
                                 classFH.method4747(),
                                 1122533453
                              );
                           } else {
                              int var42 = var18 - var19;
                              if (var42 <= 1123749286) {
                                 var42 = 50;
                              }

                              if (var21 > 0) {
                                 var22 /= var20;
                                 var23 /= var42;
                              } else {
                                 var23 /= var20;
                                 var22 /= var42;
                              }

                              if (var24 > 0) {
                                 var29 /= var20;
                                 var27 /= var42;
                              } else {
                                 var27 /= var20;
                                 var29 /= var42;
                              }

                              int var43 = var34 - classFH.method4725();
                              int var44 = var35 - classFH.method4730();
                              if (var43 > var22 && var43 < var23 && var44 > var29 && var44 < var27) {
                                 var41 = true;
                              }
                           }

                           if (var41) {
                              if (this.field2419) {
                                 int var60 = classFH.method4725();
                                 int var45 = classFH.method4730();
                                 int var46 = classFH.method4747();
                                 classEV.method4118(var13, var14, var15, var16, var60, var45, var46, (byte)-97);
                                 int var57 = classSZ.method10772(
                                    var4,
                                    var5,
                                    var6,
                                    client.field1069 * -1163968665,
                                    455131281 * classDX.field1660,
                                    -914460155 * classDU.field1631,
                                    1788691143 * classDB.field1373,
                                    1768347607 * classYJ.field6992,
                                    -1569185711 * World.field1163,
                                    this.method5118(var1),
                                    1360440870
                                 );
                                 int var47 = var57 + 32;
                                 classGI.method5479(var7, var47, -1866449209);
                              } else {
                                 var55 = true;
                              }
                           }
                        }

                        int var56 = classFH.method4725();
                        int var58 = classFH.method4730();
                        float var59 = 0.0F;
                        float var61 = 0.0F;
                        if (var1 != 0) {
                           var59 = classFH.field2253[var1];
                           var61 = classFH.field2257[var1];
                        }

                        for (int var62 = 0; var62 < this.field2422; var62++) {
                           float var63 = (int)this.field2425[var62];
                           float var66 = (int)this.field2428[var62];
                           float var48 = (int)this.field2424[var62];
                           if (var1 != 0) {
                              float var49 = var48 * var59 + var63 * var61;
                              var48 = var48 * var61 - var63 * var59;
                              var63 = var49;
                           }

                           var63 += var4;
                           var66 += var5;
                           var48 += var6;
                           float var71 = var48 * var15 + var63 * var16;
                           var48 = var48 * var16 - var63 * var15;
                           float var72 = var66 * var14 - var48 * var13;
                           var48 = var66 * var13 + var48 * var14;
                           var31 |= method5198(this, var62, var71, var72, var48, var18, var56, var58, var33);
                        }

                        try {
                           this.method5202(var31, var55, this.field2419, var7);
                        } catch (Exception var50) {
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;I)V")
   @ObfuscatedName("hs")
   public static void method5240(Model var0, int var1) {
      if (var0 == null) {
         var0.method5210();
      } else {
         if (var1 != 0) {
            for (int var2 = 0; var2 < var0.field2422; var2++) {
               var0.field2428[var2] = (int)var0.field2428[var2] + var1;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IFFFIIIZ)Z")
   @ObfuscatedName("fp")
   boolean method5197(int var1, float var2, float var3, float var4, int var5, int var6, int var7, boolean var8) {
      field2449[var1] = (int)var4 - var5;
      if (var8) {
         field2468[var1] = (int)var2;
         field2451[var1] = (int)var3;
         field2474[var1] = (int)var4;
      }

      if (var4 >= 50.0F) {
         field2464[var1] = var6 + var2 * classFH.method4747() / var4;
         field2450[var1] = var7 + var3 * classFH.method4747() / var4;
         field2448[var1] = var4;
         return false;
      } else {
         field2464[var1] = -5000.0F;
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILuu;IIIJ)V")
   @ObfuscatedName("ml")
   @Override
   void vmethod421(int var1, classUU var2, int var3, int var4, int var5, long var6) {
      if (classFH.field2255.field2215 != 1 || this.field2442 != null) {
         if (this.field2491 != 1) {
            method5254(this);
         }

         this.method5117(var1);
         classUG var8 = classMQ.method7682(654899424);
         var8.field6303.method11437(var1 * 0.0030679614F, 0.0F, 0.0F, -284857820);
         classUZ.method11802(var8.field6304, 1.0F, 1.0F, 1.0F, 1784367608);
         classUZ.method11802(var8.field6305, var3, var4, var5, 1892025795);
         classUU var9 = classRM.method10172(1778071523);
         var9.method11706(var8, 2098028491);
         var9.method11656(var2, 433257666);
         int var10 = (int)var9.method11689(0.0F, 0.0F, 0.0F, -1972903940);
         boolean var11 = false;
         int var12 = var10 - this.field2487;
         boolean var13 = var12 <= -1603509246 || this.field2435 != null;
         boolean var15 = var6 != 0L;
         if (var15) {
            boolean var16 = (int)(var6 >>> 19 & 1L) == 1;
            var15 = !var16;
         }

         boolean var30 = false;
         if (var15 && classAS.method657(1962492365)) {
            int var19 = classFH.method4725();
            int var20 = classFH.method4730();
            int var21 = classFH.method4747();
            boolean var22 = classFO.field2350;
            boolean var17;
            if (!var22) {
               var17 = false;
            } else {
               classVL.method12067(var2, var19, var20, var21, -1777153680);
               var17 = GrandExchangeOffer.method7660(
                  this, var1, var3 - -1650464014 * classFM.field2313, var4 - classCP.field1200 * 1933993628, var5 - -1592493719 * classVR.field6558, (byte)-52
               );
            }

            if (var17) {
               if (this.field2419) {
                  int var25 = classFH.method4725();
                  int var26 = classFH.method4730();
                  int var27 = classFH.method4747();
                  classVL.method12067(var2, var25, var26, var27, 1506999251);
                  int var23 = classSZ.method10772(
                     var3 - classFM.field2313 * -1039715711,
                     var4 - 1448483213 * classCP.field1200,
                     var5 - -417087201 * classVR.field6558,
                     -1163968665 * client.field1069,
                     classDX.field1660 * -888802465,
                     classDU.field1631 * -1708438567,
                     classDB.field1373 * 1788691143,
                     -318412586 * classYJ.field6992,
                     World.field1163 * -1569185711,
                     this.method5118(var1),
                     -471932658
                  );
                  int var28 = var23 + 32;
                  classGI.method5479(var6, var28, -86511550);
               } else {
                  var30 = true;
               }
            }
         }

         int var31 = classFH.method4725();
         int var18 = classFH.method4730();

         for (int var32 = 0; var32 < this.field2422; var32++) {
            float var33 = (int)this.field2425[var32];
            float var35 = (int)this.field2428[var32];
            float var37 = (int)this.field2424[var32];
            var9.method11677(var33, var35, var37, field2444, 1634822735);
            var33 = field2444[0];
            var35 = field2444[1];
            var37 = field2444[2];
            var11 |= method5198(this, var32, var33, var35, var37, var10, var31, var18, var13);
         }

         var8.method11364(-531063913);
         var9.method11615(1957724618);

         try {
            this.method5202(var11, var30, this.field2419, var6);
         } catch (Exception var29) {
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZZJ)V")
   @ObfuscatedName("fn")
   final void method5203(boolean var1, boolean var2, boolean var3, long var4) {
      if (this.field2489 < 6000) {
         for (int var6 = 0; var6 < this.field2489; var6++) {
            field2453[var6] = 0;
         }

         int var30 = var3 ? 20 : 5;
         int var7 = classFH.method4751();

         for (int var8 = 0; var8 < this.field2417; var8++) {
            if (this.field2433[var8] == -2) {
               field2447[var8] = true;
            } else {
               if (this.field2421 != classFZ.field2515) {
                  if (classFH.field2255.field2215 == 1 && (this.field2442 == null || this.field2442[var8] == 0) && this.field2423 == 0) {
                     field2447[var8] = true;
                     continue;
                  }

                  if (classFH.field2255.field2215 == 0 && (this.field2442 != null && this.field2442[var8] != 0 || this.field2423 != 0)) {
                     field2447[var8] = true;
                     continue;
                  }
               }

               int var9 = this.field2427[var8];
               int var10 = this.field2430[var8];
               int var11 = this.field2431[var8];
               float var12 = field2464[var9];
               float var13 = field2464[var10];
               float var14 = field2464[var11];
               field2461[var8] = var1 && (var12 == -5000.0F || var13 == -5000.0F || var14 == -5000.0F);
               if (field2461[var8]) {
                  int var15 = field2468[var9];
                  int var16 = field2468[var10];
                  int var17 = field2468[var11];
                  int var18 = field2451[var9];
                  int var19 = field2451[var10];
                  int var20 = field2451[var11];
                  int var21 = field2474[var9];
                  int var22 = field2474[var10];
                  int var23 = field2474[var11];
                  var15 -= var16;
                  var17 -= var16;
                  var18 -= var19;
                  var20 -= var19;
                  var21 -= var22;
                  var23 -= var22;
                  int var24 = var18 * var23 - var21 * var20;
                  int var25 = var21 * var17 - var15 * var23;
                  int var26 = var15 * var20 - var18 * var17;
                  field2447[var8] = var16 * var24 + var19 * var25 + var22 * var26 <= 0.0F;
               } else {
                  field2447[var8] = (var12 - var13) * (field2450[var11] - field2450[var10]) - (field2450[var9] - field2450[var10]) * (var14 - var13) <= 0.0F;
                  field2443[var8] = var12 < 0.0F || var13 < 0.0F || var14 < 0.0F || var12 > var7 || var13 > var7 || var14 > var7;
                  if (var2
                     && classDG.method3086(
                        (int)field2450[var9], (int)field2450[var10], (int)field2450[var11], (int)var12, (int)var13, (int)var14, var30, -1933558936
                     )) {
                     int var35 = (int)(field2448[var9] + field2448[var10] + field2448[var11]) / 3;
                     classGI.method5479(var4, var35, -339143960);
                  }
               }
            }
         }

         short var31 = this.field2497;
         boolean var32 = false;
         if (classFH.field2255.field2213.method4857()) {
            this.field2497 = (short)this.field2417;
         } else if (this.field2420 != null && this.field2420.method4857()) {
            classFH.field2255.field2213.method4843(this.field2420);
            var32 = true;
         }

         try {
            if (!classFH.method4755()) {
               classMO.method7679(this, true);
            } else {
               classFZ var33 = this.field2421;
               if (var33 == classFZ.field2513) {
                  if (classFH.field2255.field2215 != 0 && this.field2442 != null) {
                     var33 = classFZ.field2514;
                  } else {
                     var33 = classFZ.field2516;
                  }
               }

               switch (var33.field2512) {
                  case 1:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     classMO.method7679(this, false);
                     break;
                  case 2:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = 0;
                     classMO.method7679(this, true);
                     classFH.field2255.field2208 = 0;
                     classFH.field2255.field2214 = -1;
                     classMO.method7679(this, true);
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     break;
                  case 3:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = -1;
                     method5207(this);
                     break;
                  case 4:
                     classFH.field2255.field2208 = -1;
                     classFH.field2255.field2214 = 0;
                     method5207(this);
               }
            }
         } finally {
            this.field2497 = var31;
            if (var32) {
               classFL.method4856(classFH.field2255.field2213);
            }
         }
      }
   }

   public int getFaceCount() {
      return this.field2417;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fx")
   void method5206() {
      for (int var1 = 0; var1 < this.field2417; var1++) {
         if (!field2447[var1]) {
            this.method5217(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfl;S)V")
   @ObfuscatedName("cd")
   public void method5233(classFL var1, short var2) {
      if (var1 == null) {
         classKU.method6608(this);
      } else {
         if (this.field2420 == null) {
            this.field2420 = new classFL();
         }

         this.field2420.method4843(var1);
         this.field2497 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fe")
   void method5211() {
      for (int var1 = 0; var1 < 12; var1++) {
         field2455[var1] = 0;
         field2478[var1] = 0;
      }

      for (int var13 = this.field2489 - 1; var13 >= 0; var13--) {
         char var2 = field2453[var13];
         if (var2 > 0) {
            char[] var3 = field2454[var13];

            for (int var4 = 0; var4 < var2; var4++) {
               char var5 = var3[var4];
               byte var6 = this.field2429[var5];
               int var7 = field2455[var6]++;
               field2456[var6][var7] = var5;
               if (var6 < 10) {
                  field2478[var6] = field2478[var6] + var13;
               } else if (var6 == 10) {
                  field2457[var7] = var13;
               } else {
                  field2465[var7] = var13;
               }
            }
         }
      }

      int var14 = 0;
      if (field2455[1] > 0 || field2455[2] > 0) {
         var14 = (field2478[1] + field2478[2]) / (field2455[1] + field2455[2]);
      }

      int var15 = 0;
      if (field2455[3] > 0 || field2455[4] > 0) {
         var15 = (field2478[3] + field2478[4]) / (field2455[3] + field2455[4]);
      }

      int var16 = 0;
      if (field2455[6] > 0 || field2455[8] > 0) {
         var16 = (field2478[6] + field2478[8]) / (field2455[6] + field2455[8]);
      }

      int var18 = 0;
      int var19 = field2455[10];
      int[] var20 = field2456[10];
      int[] var8 = field2457;
      if (var18 == var19) {
         var18 = 0;
         var19 = field2455[11];
         var20 = field2456[11];
         var8 = field2465;
      }

      int var17;
      if (var18 < var19) {
         var17 = var8[var18];
      } else {
         var17 = -1000;
      }

      for (int var9 = 0; var9 < 10; var9++) {
         while (var9 == 0 && var17 > var14) {
            this.method5217(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2455[11];
               var20 = field2456[11];
               var8 = field2465;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         while (var9 == 3 && var17 > var15) {
            this.method5217(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2455[11];
               var20 = field2456[11];
               var8 = field2465;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         while (var9 == 5 && var17 > var16) {
            this.method5217(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2455[11];
               var20 = field2456[11];
               var8 = field2465;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         int var10 = field2455[var9];
         int[] var11 = field2456[var9];

         for (int var12 = 0; var12 < var10; var12++) {
            this.method5217(var11[var12]);
         }
      }

      while (var17 != -1000) {
         this.method5217(var20[var18++]);
         if (var18 == var19 && var20 != field2456[11]) {
            var18 = 0;
            var20 = field2456[11];
            var19 = field2455[11];
            var8 = field2465;
         }

         if (var18 < var19) {
            var17 = var8[var18];
         } else {
            var17 = -1000;
         }
      }
   }

   public int[] getVertexNormalsX() {
      return this.field2501;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fc")
   void method5212() {
      for (int var1 = 0; var1 < 12; var1++) {
         field2455[var1] = 0;
         field2478[var1] = 0;
      }

      for (int var13 = this.field2489 - 1; var13 >= 0; var13--) {
         char var2 = field2453[var13];
         if (var2 > 0) {
            char[] var3 = field2454[var13];

            for (int var4 = 0; var4 < var2; var4++) {
               char var5 = var3[var4];
               byte var6 = this.field2429[var5];
               int var7 = field2455[var6]++;
               field2456[var6][var7] = var5;
               if (var6 < 10) {
                  field2478[var6] = field2478[var6] + var13;
               } else if (var6 == 10) {
                  field2457[var7] = var13;
               } else {
                  field2465[var7] = var13;
               }
            }
         }
      }

      int var14 = 0;
      if (field2455[1] > 0 || field2455[2] > 0) {
         var14 = (field2478[1] + field2478[2]) / (field2455[1] + field2455[2]);
      }

      int var15 = 0;
      if (field2455[3] > 0 || field2455[4] > 0) {
         var15 = (field2478[3] + field2478[4]) / (field2455[3] + field2455[4]);
      }

      int var16 = 0;
      if (field2455[6] > 0 || field2455[8] > 0) {
         var16 = (field2478[6] + field2478[8]) / (field2455[6] + field2455[8]);
      }

      int var18 = 0;
      int var19 = field2455[10];
      int[] var20 = field2456[10];
      int[] var8 = field2457;
      if (var18 == var19) {
         var18 = 0;
         var19 = field2455[11];
         var20 = field2456[11];
         var8 = field2465;
      }

      int var17;
      if (var18 < var19) {
         var17 = var8[var18];
      } else {
         var17 = -1000;
      }

      for (int var9 = 0; var9 < 10; var9++) {
         while (var9 == 0 && var17 > var14) {
            this.method5217(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2455[11];
               var20 = field2456[11];
               var8 = field2465;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         while (var9 == 3 && var17 > var15) {
            this.method5217(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2455[11];
               var20 = field2456[11];
               var8 = field2465;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         while (var9 == 5 && var17 > var16) {
            this.method5217(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2455[11];
               var20 = field2456[11];
               var8 = field2465;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         int var10 = field2455[var9];
         int[] var11 = field2456[var9];

         for (int var12 = 0; var12 < var10; var12++) {
            this.method5217(var11[var12]);
         }
      }

      while (var17 != -1000) {
         this.method5217(var20[var18++]);
         if (var18 == var19 && var20 != field2456[11]) {
            var18 = 0;
            var20 = field2456[11];
            var19 = field2455[11];
            var8 = field2465;
         }

         if (var18 < var19) {
            var17 = var8[var18];
         } else {
            var17 = -1000;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bh")
   int method5216(int var1) {
      if (this.field2423 == -1) {
         return 253;
      } else {
         int var2 = this.field2423 & 255;
         if (var2 > 0 && var1 < 253) {
            int var3 = 161191213 - var1;
            int var4 = var3 * var2 >> 8;
            return var1 + var4;
         } else {
            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fw")
   final void method5218(int var1) {
      if (this.field2423 != -1) {
         if (this.field2442 == null) {
            classFH.field2255.field2211 = 0;
         } else {
            classFH.field2255.field2211 = (this.field2442[var1] == -1 ? 253 : this.field2442[var1]) & 255;
         }

         if (this.field2423 != 0) {
            classFH.field2255.field2211 = this.method5214(classFH.field2255.field2211);
         }

         if (classFH.field2255.field2215 != 1 || classFH.field2255.field2211 != 0 || this.field2423 != 0) {
            if (classFH.field2255.field2215 != 0 || classFH.field2255.field2211 == 0) {
               if (field2461[var1]) {
                  this.method5224(var1);
               } else {
                  int var2 = this.field2427[var1];
                  int var3 = this.field2430[var1];
                  int var4 = this.field2431[var1];
                  classFH.field2255.field2209 = field2443[var1];
                  int var5 = this.field2436 == null ? 0 : this.field2436[var1] * 2;
                  float var6 = classJE.method6398(field2448[var2] - var5, 2099878350);
                  float var7 = classJE.method6398(field2448[var3] - var5, 2090890276);
                  float var8 = classJE.method6398(field2448[var4] - var5, 2115871006);
                  method5221(
                     this,
                     var1,
                     field2450[var2],
                     field2450[var3],
                     field2450[var4],
                     field2464[var2],
                     field2464[var3],
                     field2464[var4],
                     var6,
                     var7,
                     var8,
                     this.field2432[var1],
                     this.field2426[var1],
                     this.field2433[var1]
                  );
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("gq")
   boolean method5220(int var1) {
      return var1 < this.field2497;
   }

   public int getRadius() {
      return this.field2487;
   }

   @ObfuscatedSignature(descriptor = "(IFFFFFFFFFIII)V")
   @ObfuscatedName("cu")
   final void method5222(
      int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, int var11, int var12, int var13
   ) {
      if (this.field2435 != null && this.field2435[var1] != -1) {
         int var15;
         int var16;
         int var18;
         if (this.field2434 != null && this.field2434[var1] != -1) {
            int var17 = this.field2434[var1] & -1449798525;
            var18 = this.field2437[var17];
            var15 = this.field2438[var17];
            var16 = this.field2439[var17];
         } else {
            var18 = this.field2427[var1];
            var15 = this.field2430[var1];
            var16 = this.field2431[var1];
         }

         if (this.field2433[var1] == -1) {
            classFH.method4787(
               var2,
               var3,
               var4,
               var5,
               var6,
               var7,
               var8,
               var9,
               var10,
               var11,
               var11,
               var11,
               field2468[var18],
               field2468[var15],
               field2468[var16],
               field2451[var18],
               field2451[var15],
               field2451[var16],
               field2474[var18],
               field2474[var15],
               field2474[var16],
               this.field2435[var1]
            );
         } else {
            classFH.method4787(
               var2,
               var3,
               var4,
               var5,
               var6,
               var7,
               var8,
               var9,
               var10,
               var11,
               var12,
               var13,
               field2468[var18],
               field2468[var15],
               field2468[var16],
               field2451[var18],
               field2451[var15],
               field2451[var16],
               field2474[var18],
               field2474[var15],
               field2474[var16],
               this.field2435[var1]
            );
         }
      } else {
         boolean var14 = this.method5219(var1);
         if (this.field2433[var1] == -1 && var14) {
            classFH.method4793(var2, var3, var4, var5, var6, var7, var8, var9, var10, field2460[this.field2432[var1]]);
         } else if (this.field2433[var1] == -1) {
            classFH.method4782(var2, var3, var4, var5, var6, var7, var8, var9, var10, field2460[this.field2432[var1]]);
         } else if (var14) {
            classFH.method4792(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
         } else {
            classFH.method4778(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("cn")
   void method5100(Model var1) {
      int var2 = this.field2427.length;
      if (this.field2429 == null && (var1.field2429 != null || this.field2418 != var1.field2418)) {
         this.field2429 = new byte[var2];
         Arrays.fill(this.field2429, this.field2418);
      }

      if (this.field2442 == null && var1.field2442 != null) {
         this.field2442 = new byte[var2];
         Arrays.fill(this.field2442, (byte)0);
      }

      if (this.field2435 == null && var1.field2435 != null) {
         this.field2435 = new short[var2];
         Arrays.fill(this.field2435, (short)-1);
      }

      if (this.field2434 == null && var1.field2434 != null) {
         this.field2434 = new byte[var2];
         Arrays.fill(this.field2434, (byte)-1);
      }

      if (this.field2436 == null && var1.field2436 != null) {
         this.field2436 = new byte[var2];
         Arrays.fill(this.field2436, (byte)0);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lef;I)V")
   @ObfuscatedName("ds")
   void method5144(classEF var1, int var2) {
      this.method5277(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Lfx;IFFFIIIZ)Z")
   @ObfuscatedName("rv")
   public static boolean method5198(Model var0, int var1, float var2, float var3, float var4, int var5, int var6, int var7, boolean var8) {
      if (var0 == null) {
         return var0.method5199(var1, var2, var2, var2, var1, var1, var1, var8);
      } else {
         field2449[var1] = (int)var4 - var5;
         if (var8) {
            field2468[var1] = (int)var2;
            field2451[var1] = (int)var3;
            field2474[var1] = (int)var4;
         }

         if (var4 >= 50.0F) {
            field2464[var1] = var6 + var2 * classFH.method4747() / var4;
            field2450[var1] = var7 + var3 * classFH.method4747() / var4;
            field2448[var1] = var4;
            return false;
         } else {
            field2464[var1] = -5000.0F;
            return true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILuu;)V")
   @ObfuscatedName("gp")
   void method5228(int var1, classUU var2) {
      float var3 = (int)this.field2425[var1];
      float var4 = -((int)this.field2428[var1]);
      float var5 = -((int)this.field2424[var1]);
      float var6 = 1.0F;
      this.field2425[var1] = var2.field6383 * var3 + var2.field6392 * var4 + var2.field6388 * var5 + var2.field6391 * var6;
      this.field2428[var1] = -((int)(var2.field6398 * var3 + var2.field6384 * var4 + var2.field6389 * var5 + var2.field6399 * var6));
      this.field2424[var1] = -((int)(var2.field6394 * var3 + var2.field6396 * var4 + var2.field6385 * var5 + var2.field6395 * var6));
   }

   @ObfuscatedSignature(descriptor = "(Z)Lfx;")
   @ObfuscatedName("cx")
   public Model method5110(boolean var1) {
      if (!var1 && field2472.length < this.field2417) {
         field2472 = new byte[this.field2417 + 100];
      }

      return this.method5112(var1, field2469, field2472);
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("gg")
   public void method5231(byte var1) {
      this.field2423 = var1;
   }

   public void setBufferOffset(int var1) {
      this.field2494 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;IIIIIIII)V")
   @ObfuscatedName("dk")
   public static void method5187(Model var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (var0 == null) {
         var0.method5189(var1, var1, var1, var1, var1, var1, var1, var1);
      }

      if (var0.field2491 != 2) {
         var0.method5263();
      }

      int var9 = classFH.method4725();
      int var10 = classFH.method4730();
      int var11 = field2463[var1];
      int var12 = field2471[var1];
      int var13 = field2463[var2];
      int var14 = field2471[var2];
      int var15 = field2463[var3];
      int var16 = field2471[var3];
      int var17 = field2463[var4];
      int var18 = field2471[var4];
      int var19 = var6 * var17 + var7 * var18 >> 16;

      for (int var20 = 0; var20 < var0.field2422; var20++) {
         int var21 = (int)var0.field2425[var20];
         int var22 = (int)var0.field2428[var20];
         int var23 = (int)var0.field2424[var20];
         if (var3 != 0) {
            int var24 = var22 * var15 + var21 * var16 >> 16;
            var22 = var22 * var16 - var21 * var15 >> 16;
            var21 = var24;
         }

         if (var1 != 0) {
            int var30 = var22 * var12 - var23 * var11 >> 16;
            var23 = var22 * var11 + var23 * var12 >> 16;
            var22 = var30;
         }

         if (var2 != 0) {
            int var31 = var23 * var13 + var21 * var14 >> 16;
            var23 = var23 * var14 - var21 * var13 >> 16;
            var21 = var31;
         }

         var21 += var5;
         var22 += var6;
         var23 += var7;
         int var32 = var22 * var18 - var23 * var17 >> 16;
         var23 = var22 * var17 + var23 * var18 >> 16;
         field2449[var20] = var23 - var19;
         field2464[var20] = var9 + var21 * classFH.method4747() / var8;
         field2450[var20] = var10 + var32 * classFH.method4747() / var8;
         field2448[var20] = var8;
         if (var0.field2435 != null) {
            field2468[var20] = var21;
            field2451[var20] = var32;
            field2474[var20] = var23;
         }
      }

      try {
         var0.method5202(false, false, false, 0L);
      } catch (Exception var25) {
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("er")
   public void method5234() {
      this.field2420 = null;
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIJ)V")
   @ObfuscatedName("fu")
   void method5195(int var1, classUP var2, classUP var3, int var4, int var5, int var6, long var7) {
      if (classFH.field2255.field2215 != 1 || this.field2442 != null || this.field2423 != 0) {
         int var9 = classUP.method11506(var2, 1237894015);
         int var10 = var2.method11507((byte)-69);
         int var11 = classUP.method11506(var3, 1606683675);
         int var12 = var3.method11507((byte)-3);
         float var13 = var2.method11510(2111974549);
         float var14 = var2.method11512((byte)125);
         float var15 = var3.method11510(2132282507);
         float var16 = var3.method11512((byte)127);
         if (this.field2491 != 1) {
            method5254(this);
         }

         this.method5117(var1);
         int var17 = var6 * var12 - var4 * var11 >> 16;
         int var18 = var5 * var9 + var17 * var10 >> 16;
         int var19 = this.field2488 * var10 >> 16;
         int var20 = var18 + var19;
         if (var20 > -1061492015 && var18 < classFQ.method4948()) {
            int var21 = var6 * var11 + var4 * var12 >> 16;
            int var22 = (var21 - this.field2488) * classFH.method4747();
            if (var22 / var20 < classFH.method4736()) {
               int var23 = (var21 + this.field2488) * classFH.method4747();
               if (var23 / var20 > classFH.method4732()) {
                  int var24 = var5 * var10 - var17 * var9 >> 16;
                  int var25 = this.field2488 * var9 >> 16;
                  int var26 = var25 + (this.field2490 * var10 >> 16);
                  int var27 = (var24 + var26) * classFH.method4747();
                  if (var27 / var20 > classFH.method4744()) {
                     int var28 = var25 + (this.field1741 * -1256242689 * var10 >> 16);
                     int var29 = (var24 - var28) * classFH.method4747();
                     if (var29 / var20 < classFH.method4740()) {
                        int var30 = var19 + (this.field1741 * -432844799 * var9 >> 16);
                        boolean var31 = false;
                        boolean var32 = false;
                        if (var18 - var30 <= 865534903) {
                           var32 = true;
                        }

                        boolean var33 = var32 || this.field2435 != null;
                        int var34 = classCS.method2587(-502055544);
                        int var35 = -129880211 * classFO.field2346;
                        boolean var37 = classAS.method657(1907553954);
                        boolean var39 = 0L != var7;
                        if (var39) {
                           boolean var40 = 1 == (int)(var7 >>> 19 & 1L);
                           var39 = !var40;
                        }

                        boolean var55 = false;
                        if (var39 && var37) {
                           boolean var41 = false;
                           if (field2446) {
                              var41 = Projectile.method1224(
                                 this,
                                 var1,
                                 var4,
                                 var5,
                                 var6,
                                 var13,
                                 var14,
                                 var15,
                                 var16,
                                 classFH.method4725(),
                                 classFH.method4730(),
                                 classFH.method4747(),
                                 1407604053
                              );
                           } else {
                              int var42 = var18 - var19;
                              if (var42 <= 471943653) {
                                 var42 = -1319565614;
                              }

                              if (var21 > 0) {
                                 var22 /= var20;
                                 var23 /= var42;
                              } else {
                                 var23 /= var20;
                                 var22 /= var42;
                              }

                              if (var24 > 0) {
                                 var29 /= var20;
                                 var27 /= var42;
                              } else {
                                 var27 /= var20;
                                 var29 /= var42;
                              }

                              int var43 = var34 - classFH.method4725();
                              int var44 = var35 - classFH.method4730();
                              if (var43 > var22 && var43 < var23 && var44 > var29 && var44 < var27) {
                                 var41 = true;
                              }
                           }

                           if (var41) {
                              if (this.field2419) {
                                 int var60 = classFH.method4725();
                                 int var45 = classFH.method4730();
                                 int var46 = classFH.method4747();
                                 classEV.method4118(var13, var14, var15, var16, var60, var45, var46, (byte)-24);
                                 int var57 = classSZ.method10772(
                                    var4,
                                    var5,
                                    var6,
                                    client.field1069 * -436230143,
                                    -1993288235 * classDX.field1660,
                                    -1674330987 * classDU.field1631,
                                    1788691143 * classDB.field1373,
                                    -938609029 * classYJ.field6992,
                                    632410639 * World.field1163,
                                    this.method5118(var1),
                                    -563823246
                                 );
                                 int var47 = var57 + 32;
                                 classGI.method5479(var7, var47, -74243497);
                              } else {
                                 var55 = true;
                              }
                           }
                        }

                        int var56 = classFH.method4725();
                        int var58 = classFH.method4730();
                        float var59 = 0.0F;
                        float var61 = 0.0F;
                        if (var1 != 0) {
                           var59 = classFH.field2253[var1];
                           var61 = classFH.field2257[var1];
                        }

                        for (int var62 = 0; var62 < this.field2422; var62++) {
                           float var63 = (int)this.field2425[var62];
                           float var66 = (int)this.field2428[var62];
                           float var48 = (int)this.field2424[var62];
                           if (var1 != 0) {
                              float var49 = var48 * var59 + var63 * var61;
                              var48 = var48 * var61 - var63 * var59;
                              var63 = var49;
                           }

                           var63 += var4;
                           var66 += var5;
                           var48 += var6;
                           float var71 = var48 * var15 + var63 * var16;
                           var48 = var48 * var16 - var63 * var15;
                           float var72 = var66 * var14 - var48 * var13;
                           var48 = var66 * var13 + var48 * var14;
                           var31 |= method5198(this, var62, var71, var72, var48, var18, var56, var58, var33);
                        }

                        try {
                           this.method5202(var31, var55, this.field2419, var7);
                        } catch (Exception var50) {
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fj")
   public void method5183(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2422; var4++) {
         this.field2425[var4] = (int)this.field2425[var4] * var1 / -1277400391;
         this.field2428[var4] = (int)this.field2428[var4] * var2 / -381887117;
         this.field2424[var4] = (int)this.field2424[var4] * var3 / 128;
      }

      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "(ZLfx;[B)Lfx;")
   @ObfuscatedName("dd")
   Model method5116(boolean var1, Model var2, byte[] var3) {
      var2.field2422 = this.field2422;
      var2.field2417 = this.field2417;
      var2.field2416 = this.field2416;
      if (var2.field2425 == null || var2.field2425.length < this.field2422) {
         var2.field2425 = new float[this.field2422 + 2116082260];
         var2.field2428 = new float[this.field2422 + -458330916];
         var2.field2424 = new float[this.field2422 + -577404880];
      }

      for (int var4 = 0; var4 < this.field2422; var4++) {
         var2.field2425[var4] = this.field2425[var4];
         var2.field2428[var4] = this.field2428[var4];
         var2.field2424[var4] = this.field2424[var4];
      }

      if (var1) {
         var2.field2442 = this.field2442;
      } else {
         var2.field2442 = var3;
         if (this.field2442 == null) {
            for (int var5 = 0; var5 < this.field2417; var5++) {
               var2.field2442[var5] = 0;
            }
         } else {
            for (int var6 = 0; var6 < this.field2417; var6++) {
               var2.field2442[var6] = this.field2442[var6];
            }
         }
      }

      var2.field2427 = this.field2427;
      var2.field2430 = this.field2430;
      var2.field2431 = this.field2431;
      var2.field2432 = this.field2432;
      var2.field2426 = this.field2426;
      var2.field2433 = this.field2433;
      var2.field2429 = this.field2429;
      var2.field2434 = this.field2434;
      var2.field2435 = this.field2435;
      var2.field2436 = this.field2436;
      var2.field2418 = this.field2418;
      var2.field2437 = this.field2437;
      var2.field2438 = this.field2438;
      var2.field2439 = this.field2439;
      var2.field2440 = this.field2440;
      var2.field2441 = this.field2441;
      var2.field2506 = this.field2506;
      var2.field2505 = this.field2505;
      var2.field2419 = this.field2419;
      var2.field2420 = this.field2420;
      var2.field2497 = this.field2497;
      var2.field2423 = this.field2423;
      var2.method5129();
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lfz;)V")
   @ObfuscatedName("ge")
   void method5244(classFZ var1) {
      this.field2421 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("mf")
   public static void method5207(Model var0) {
      if (var0 == null) {
         var0.method5169();
      } else {
         for (int var1 = 0; var1 < var0.field2417; var1++) {
            if (!field2447[var1]) {
               var0.method5217(var1);
            }
         }
      }
   }

   public int[] getVertexNormalsY() {
      return this.field2503;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("am")
   public static void method5168(Model var0) {
      if (var0 == null) {
         var0.calculateBoundsCylinder();
      } else {
         for (int var1 = 0; var1 < var0.field2422; var1++) {
            int var2 = (int)var0.field2424[var1];
            var0.field2424[var1] = var0.field2425[var1];
            var0.field2425[var1] = -var2;
         }

         var0.method5129();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfz;)V")
   @ObfuscatedName("hz")
   void method5245(classFZ var1) {
      this.field2421 = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("eq")
   public void method5292() {
      method5254(this);
   }

   @ObfuscatedSignature(descriptor = "(Lfx;IIIIIIII)V")
   @ObfuscatedName("xk")
   public static void method5188(Model var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (var0 == null) {
         var0.method5190(var1, var1, var1, var1, var1, var1, var1, var1);
      }

      if (var0.field2491 != 2) {
         var0.method5263();
      }

      int var9 = classFH.method4725();
      int var10 = classFH.method4730();
      int var11 = field2463[var1];
      int var12 = field2471[var1];
      int var13 = field2463[var2];
      int var14 = field2471[var2];
      int var15 = field2463[var3];
      int var16 = field2471[var3];
      int var17 = field2463[var4];
      int var18 = field2471[var4];
      int var19 = var6 * var17 + var7 * var18 >> 16;

      for (int var20 = 0; var20 < var0.field2422; var20++) {
         int var21 = (int)var0.field2425[var20];
         int var22 = (int)var0.field2428[var20];
         int var23 = (int)var0.field2424[var20];
         if (var3 != 0) {
            int var24 = var22 * var15 + var21 * var16 >> 16;
            var22 = var22 * var16 - var21 * var15 >> 16;
            var21 = var24;
         }

         if (var1 != 0) {
            int var30 = var22 * var12 - var23 * var11 >> 16;
            var23 = var22 * var11 + var23 * var12 >> 16;
            var22 = var30;
         }

         if (var2 != 0) {
            int var31 = var23 * var13 + var21 * var14 >> 16;
            var23 = var23 * var14 - var21 * var13 >> 16;
            var21 = var31;
         }

         var21 += var5;
         var22 += var6;
         var23 += var7;
         int var32 = var22 * var18 - var23 * var17 >> 16;
         var23 = var22 * var17 + var23 * var18 >> 16;
         field2449[var20] = var23 - var19;
         field2464[var20] = var9 + var21 * classFH.method4747() / var8;
         field2450[var20] = var10 + var32 * classFH.method4747() / var8;
         field2448[var20] = var8;
         if (var0.field2435 != null) {
            field2468[var20] = var21;
            field2451[var20] = var32;
            field2474[var20] = var23;
         }
      }

      try {
         var0.method5202(false, false, false, 0L);
      } catch (Exception var25) {
      }
   }

   @ObfuscatedSignature(descriptor = "([[IIIIZI)Lfx;")
   @ObfuscatedName("sh")
   public Model method5293(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      return this.method5258(var1, var2, var3, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(Lfs;ILfs;I[I)V")
   @ObfuscatedName("br")
   public void method5148(classFS var1, int var2, classFS var3, int var4, int[] var5) {
      if (var2 != -1) {
         if (var5 != null && var4 != -1) {
            classET var6 = var1.field2390[var2];
            classET var7 = var3.field2390[var4];
            classEM var8 = var6.field1909;
            field2486 = 0;
            field2486 = 0;
            field2486 = 0;
            method5246(-1);
            int var9 = 0;
            int var10 = var5[var9++];

            for (int var11 = 0; var11 < var6.field1911; var11++) {
               int var12 = var6.field1910[var11];

               while (var12 > var10) {
                  var10 = var5[var9++];
               }

               if (var12 != var10 || var8.field1783[var12] == 0) {
                  method5260(this, var8.field1783[var12], var8.field1785[var12], var6.field1910[var11], var6.field1907[var11], var6.field1907[var11]);
               }
            }

            field2486 = 0;
            field2484 = 0;
            field2485 = 0;
            method5246(-1);
            var9 = 0;
            var10 = var5[var9++];

            for (int var17 = 0; var17 < var7.field1911; var17++) {
               int var18 = var7.field1913[var17];

               while (var18 > var10) {
                  var10 = var5[var9++];
               }

               if (var18 == var10 || var8.field1783[var18] == 0) {
                  method5260(this, var8.field1783[var18], var8.field1785[var18], var7.field1913[var17], var7.field1912[var17], var7.field1907[var17]);
               }
            }

            this.method5297();
         } else {
            this.method5135(var1, var2);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;Z)V")
   @ObfuscatedName("jz")
   public static void method5208(Model var0, boolean var1) {
      for (char var2 = 0; var2 < var0.field2417; var2++) {
         if (!field2447[var2]) {
            int var3 = (field2449[var0.field2427[var2]] + field2449[var0.field2430[var2]] + field2449[var0.field2431[var2]]) / 3 + var0.field2487;
            char[] var10000 = field2454[var3];
            field2453[var3]++;
            var10000[field2453[var3]] = var2;
         }
      }

      if (var0.field2429 != null && var1) {
         var0.method5210();
      } else {
         for (int var6 = var0.field2489 - 1; var6 >= 0; var6--) {
            char var7 = field2453[var6];
            if (var7 > 0) {
               char[] var4 = field2454[var6];

               for (int var5 = 0; var5 < var7; var5++) {
                  var0.method5217(var4[var5]);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("eg")
   public void method5294() {
      this.method5129();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public int method5128() {
      method5254(this);
      return this.field2416;
   }

   public void calculateBoundsCylinder() {
      method5254(this);
   }

   public void drawFrustum(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.method5269(var1, var2, var3, var4, var5, var6, var7);
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("sf")
   public void method5140(classSN var1, int var2) {
      classEM var3 = var1.field5981;
      classEF var4 = var3.method3948((byte)90);
      if (var4 != null) {
         var4.method3879(var1, var2, -1468766705);
         this.method5143(var4, var1.method10559(-1499030123));
      }

      if (var1.method10562(1772783837)) {
         this.method5141(var1, var2);
      }

      this.method5292();
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ue")
   public Model method5295() {
      method5276(this);
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I[IIII)V")
   @ObfuscatedName("bj")
   void method5259(int var1, int[] var2, int var3, int var4, int var5) {
      assert classOE.field4841.method11148();

      this.method5268(rl21.field5726, var1, var2, var3, var4, var5);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   public void method5213() {
      for (int var1 = 0; var1 < 12; var1++) {
         field2460[var1] = 0;
         field2457[var1] = 0;
      }

      for (int var13 = this.field2416 - 1; var13 >= 0; var13--) {
         char var2 = field2453[var13];
         if (var2 > 0) {
            char[] var3 = field2454[var13];

            for (int var4 = 0; var4 < var2; var4++) {
               char var5 = var3[var4];
               byte var6 = this.field2429[var5];
               int var7 = field2465[var6]++;
               field2456[var6][var7] = var5;
               if (var6 < 10) {
                  field2474[var6] = field2474[var6] + var13;
               } else if (var6 == 10) {
                  field2460[var7] = var13;
               } else {
                  field2476[var7] = var13;
               }
            }
         }
      }

      int var14 = 0;
      if (field2468[1] > 0 || field2457[2] > 0) {
         var14 = (field2471[1] + field2468[2]) / (field2460[1] + field2476[2]);
      }

      int var15 = 0;
      if (field2474[3] > 0 || field2474[4] > 0) {
         var15 = (field2455[3] + field2463[4]) / (field2460[3] + field2468[4]);
      }

      int var16 = 0;
      if (field2463[6] > 0 || field2451[8] > 0) {
         var16 = (field2477[6] + field2474[8]) / (field2468[6] + field2460[8]);
      }

      int var18 = 0;
      int var19 = field2471[10];
      int[] var20 = field2456[10];
      int[] var8 = field2480;
      if (var18 == var19) {
         var18 = 0;
         var19 = field2477[11];
         var20 = field2456[11];
         var8 = field2471;
      }

      int var17;
      if (var18 < var19) {
         var17 = var8[var18];
      } else {
         var17 = -1000;
      }

      for (int var9 = 0; var9 < 10; var9++) {
         while (var9 == 0 && var17 > var14) {
            this.method5224(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2467[11];
               var20 = field2456[11];
               var8 = field2467;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         while (var9 == 3 && var17 > var15) {
            this.method5224(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2468[11];
               var20 = field2456[11];
               var8 = field2480;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         while (var9 == 5 && var17 > var16) {
            this.method5218(var20[var18++]);
            if (var18 == var19 && var20 != field2456[11]) {
               var18 = 0;
               var19 = field2451[11];
               var20 = field2456[11];
               var8 = field2474;
            }

            if (var18 < var19) {
               var17 = var8[var18];
            } else {
               var17 = -1000;
            }
         }

         int var10 = field2455[var9];
         int[] var11 = field2456[var9];

         for (int var12 = 0; var12 < var10; var12++) {
            this.method5217(var11[var12]);
         }
      }

      while (var17 != -1000) {
         this.method5218(var20[var18++]);
         if (var18 == var19 && var20 != field2456[11]) {
            var18 = 0;
            var20 = field2456[11];
            var19 = field2478[11];
            var8 = field2471;
         }

         if (var18 < var19) {
            var17 = var8[var18];
         } else {
            var17 = -1000;
         }
      }
   }

   public byte getTransparency() {
      return this.field2423;
   }

   @ObfuscatedSignature(descriptor = "(IFFFIIIZ)Z")
   @ObfuscatedName("bf")
   boolean method5199(int var1, float var2, float var3, float var4, int var5, int var6, int var7, boolean var8) {
      field2471[var1] = (int)var4 - var5;
      if (var8) {
         field2476[var1] = (int)var2;
         field2478[var1] = (int)var3;
         field2457[var1] = (int)var4;
      }

      if (var4 >= 50.0F) {
         field2450[var1] = var6 + var2 * classFH.method4747() / var4;
         field2450[var1] = var7 + var3 * classFH.method4750() / var4;
         field2464[var1] = var4;
         return false;
      } else {
         field2464[var1] = -5000.0F;
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bl")
   void method5209(boolean var1) {
      for (char var2 = 0; var2 < this.field2493; var2++) {
         if (!field2443[var2]) {
            int var3 = (field2468[this.field2439[var2]] + field2449[this.field2439[var2]] + field2468[this.field2427[var2]]) / 3 + this.field2416;
            char[] var10000 = field2454[var3];
            field2453[var3]++;
            var10000[field2453[var3]] = var2;
         }
      }

      if (this.field2436 != null && var1) {
         this.method5125();
      } else {
         for (int var6 = this.field2491 - 1; var6 >= 0; var6--) {
            char var7 = field2453[var6];
            if (var7 > 0) {
               char[] var4 = field2454[var6];

               for (int var5 = 0; var5 < var7; var5++) {
                  this.method5225(var4[var5]);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("dg")
   public void method5296(classSN var1, int var2) {
      this.method5267(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("eu")
   public void method5297() {
      this.method5263();
   }

   @ObfuscatedSignature(descriptor = "(IFFFFFFFFFIII)V")
   @ObfuscatedName("gb")
   final void method5223(
      int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, int var11, int var12, int var13
   ) {
      if (this.field2435 != null && this.field2435[var1] != -1) {
         int var15;
         int var16;
         int var18;
         if (this.field2436 != null && this.field2436[var1] != -1) {
            int var17 = this.field2429[var1] & 255;
            var18 = this.field2499[var17];
            var15 = this.field2498[var17];
            var16 = this.field2432[var17];
         } else {
            var18 = this.field2498[var1];
            var15 = this.field2498[var1];
            var16 = this.field2430[var1];
         }

         if (this.field2431[var1] == -1) {
            classFH.method4788(
               var2,
               var3,
               var4,
               var5,
               var6,
               var7,
               var8,
               var9,
               var10,
               var11,
               var11,
               var11,
               field2471[var18],
               field2451[var15],
               field2449[var16],
               field2449[var18],
               field2463[var15],
               field2477[var16],
               field2471[var18],
               field2463[var15],
               field2451[var16],
               this.field2435[var1]
            );
         } else {
            classFH.method4790(
               var2,
               var3,
               var4,
               var5,
               var6,
               var7,
               var8,
               var9,
               var10,
               var11,
               var12,
               var13,
               field2476[var18],
               field2476[var15],
               field2477[var16],
               field2477[var18],
               field2457[var15],
               field2478[var16],
               field2474[var18],
               field2477[var15],
               field2449[var16],
               this.field2435[var1]
            );
         }
      } else {
         boolean var14 = this.method5219(var1);
         if (this.field2498[var1] == -1 && var14) {
            classFH.method4783(var2, var3, var4, var5, var6, var7, var8, var9, var10, field2467[this.field2500[var1]]);
         } else if (this.field2499[var1] == -1) {
            classFH.method4785(var2, var3, var4, var5, var6, var7, var8, var9, var10, field2455[this.field2432[var1]]);
         } else if (var14) {
            classFH.method4781(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
         } else {
            classFH.method4779(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Lfx;")
   @ObfuscatedName("dv")
   public Model method5298(int var1, int var2, int var3) {
      this.method5251(var1, var2, var3);
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;I[IIII)V")
   @ObfuscatedName("ku")
   public static void method5260(Model var0, int var1, int[] var2, int var3, int var4, int var5) {
      if (var0 == null) {
         var0.method5259(var1, var2, var1, var1, var1);
      } else {
         assert classOE.field4841.isClientThread();

         var0.method5265(rl21.field5726, var1, var2, var3, var4, var5);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   void method5169() {
      for (int var1 = 0; var1 < this.field2487; var1++) {
         int var2 = (int)this.field2424[var1];
         this.field2425[var1] = this.field2428[var1];
         this.field2425[var1] = -var2;
      }

      this.method5234();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gi")
   public void method5235() {
      this.field2420 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)V")
   @ObfuscatedName("ay")
   public static void method5276(Model var0) {
      if (var0 == null) {
         var0.method5213();
      } else {
         for (int var1 = 0; var1 < var0.field2422; var1++) {
            float var2 = var0.field2424[var1];
            var0.field2424[var1] = var0.field2425[var1];
            var0.field2425[var1] = -var2;
         }

         var0.method5294();
      }
   }

   public int[] getFaceIndices1() {
      return this.field2427;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cy")
   public void method5241(int var1) {
      if (var1 != 0) {
         for (int var2 = 0; var2 < this.field2489; var2++) {
            this.field2424[var2] = (int)this.field2425[var2] + var1;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzs;Z)Lzs;")
   @ObfuscatedName("cn")
   public static ScriptEventBuilder method5092(ScriptEventBuilder var0, boolean var1) {
      if (var0 == null) {
         var0.getSource();
      }

      var0.field7266 = var1;
      return var0;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("em")
   public void method5242(int var1) {
      if (var1 != 0) {
         for (int var2 = 0; var2 < this.field2490; var2++) {
            this.field2424[var2] = (int)this.field2428[var2] + var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("bu")
   public Model method5299() {
      method5278(this);
      return this;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("cc")
   void method5097(int var1, int var2, int var3) {
      this.field2428 = new float[var1];
      this.field2424 = new float[var1];
      this.field2428 = new float[var1];
      this.field2500 = new int[var2];
      this.field2500 = new int[var2];
      this.field2437 = new int[var2];
      this.field2432 = new int[var2];
      this.field2498 = new int[var2];
      this.field2433 = new int[var2];
      if (var3 > 0) {
         this.field2500 = new int[var3];
         this.field2438 = new int[var3];
         this.field2427 = new int[var3];
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;Z)Lfx;")
   @ObfuscatedName("jl")
   public static Model method5111(Model var0, boolean var1) {
      if (var0 == null) {
         var0.getOverrideHue();
      }

      if (!var1 && field2472.length < var0.field2417) {
         field2472 = new byte[var0.field2417 + 100];
      }

      return var0.method5112(var1, field2469, field2472);
   }

   @ObfuscatedSignature(descriptor = "(I)Lfp;")
   @ObfuscatedName("dx")
   AABB method5121(int var1) {
      for (AABB var2 = this.field2495; var2 != null; var2 = var2.field2369) {
         if (var2.field2362 * 1384390485 == var1) {
            return var2;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cv")
   public void method5279() {
      for (int var1 = 0; var1 < this.field2489; var1++) {
         float var2 = this.field2428[var1];
         this.field2425[var1] = this.field2424[var1];
         this.field2425[var1] = -var2;
      }

      this.method5272();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("do")
   public void method5161() {
      for (int var1 = 0; var1 < this.field2489; var1++) {
         int var2 = (int)this.field2425[var1];
         this.field2428[var1] = this.field2425[var1];
         this.field2428[var1] = -var2;
      }

      this.calculateBoundsCylinder();
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("fb")
   public final void method5189(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (this.field2491 != 2) {
         this.method5126();
      }

      int var9 = classFH.method4727();
      int var10 = classFH.method4752();
      int var11 = field2467[var1];
      int var12 = field2467[var1];
      int var13 = field2465[var2];
      int var14 = field2477[var2];
      int var15 = field2471[var3];
      int var16 = field2460[var3];
      int var17 = field2467[var4];
      int var18 = field2451[var4];
      int var19 = var6 * var17 + var7 * var18 >> 16;

      for (int var20 = 0; var20 < this.field2491; var20++) {
         int var21 = (int)this.field2425[var20];
         int var22 = (int)this.field2428[var20];
         int var23 = (int)this.field2424[var20];
         if (var3 != 0) {
            int var24 = var22 * var15 + var21 * var16 >> 16;
            var22 = var22 * var16 - var21 * var15 >> 16;
            var21 = var24;
         }

         if (var1 != 0) {
            int var30 = var22 * var12 - var23 * var11 >> 16;
            var23 = var22 * var11 + var23 * var12 >> 16;
            var22 = var30;
         }

         if (var2 != 0) {
            int var31 = var23 * var13 + var21 * var14 >> 16;
            var23 = var23 * var14 - var21 * var13 >> 16;
            var21 = var31;
         }

         var21 += var5;
         var22 += var6;
         var23 += var7;
         int var32 = var22 * var18 - var23 * var17 >> 16;
         var23 = var22 * var17 + var23 * var18 >> 16;
         field2477[var20] = var23 - var19;
         field2448[var20] = var9 + var21 * classFH.method4750() / var8;
         field2450[var20] = var10 + var32 * classFH.method4749() / var8;
         field2464[var20] = var8;
         if (this.field2435 != null) {
            field2451[var20] = var21;
            field2480[var20] = var32;
            field2480[var20] = var23;
         }
      }

      try {
         this.method5203(false, false, false, 0L);
      } catch (Exception var25) {
      }
   }

   public float[] getVerticesY() {
      return this.field2428;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("by")
   public final void method5190(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (this.field2422 != 2) {
         this.method5210();
      }

      int var9 = classFH.method4738();
      int var10 = classFH.method4752();
      int var11 = field2467[var1];
      int var12 = field2474[var1];
      int var13 = field2457[var2];
      int var14 = field2478[var2];
      int var15 = field2476[var3];
      int var16 = field2455[var3];
      int var17 = field2480[var4];
      int var18 = field2474[var4];
      int var19 = var6 * var17 + var7 * var18 >> 16;

      for (int var20 = 0; var20 < this.field2491; var20++) {
         int var21 = (int)this.field2428[var20];
         int var22 = (int)this.field2425[var20];
         int var23 = (int)this.field2424[var20];
         if (var3 != 0) {
            int var24 = var22 * var15 + var21 * var16 >> 16;
            var22 = var22 * var16 - var21 * var15 >> 16;
            var21 = var24;
         }

         if (var1 != 0) {
            int var30 = var22 * var12 - var23 * var11 >> 16;
            var23 = var22 * var11 + var23 * var12 >> 16;
            var22 = var30;
         }

         if (var2 != 0) {
            int var31 = var23 * var13 + var21 * var14 >> 16;
            var23 = var23 * var14 - var21 * var13 >> 16;
            var21 = var31;
         }

         var21 += var5;
         var22 += var6;
         var23 += var7;
         int var32 = var22 * var18 - var23 * var17 >> 16;
         var23 = var22 * var17 + var23 * var18 >> 16;
         field2468[var20] = var23 - var19;
         field2448[var20] = var9 + var21 * classFH.method4748() / var8;
         field2464[var20] = var10 + var32 * classFH.method4749() / var8;
         field2452[var20] = var8;
         if (this.field2435 != null) {
            field2455[var20] = var21;
            field2451[var20] = var32;
            field2474[var20] = var23;
         }
      }

      try {
         this.method5201(false, false, false, 0L);
      } catch (Exception var25) {
      }
   }

   @ObfuscatedSignature(descriptor = "(ILuu;)V")
   @ObfuscatedName("ct")
   void method5275(int var1, classUU var2) {
      float var3 = this.field2424[var1];
      float var4 = -this.field2428[var1];
      float var5 = -this.field2428[var1];
      float var6 = 1.0F;
      this.field2425[var1] = var2.field6383 * var3 + var2.field6397 * var4 + var2.field6391 * var5 + var2.field6397 * var6;
      this.field2428[var1] = -(var2.field6393 * var3 + var2.field6390 * var4 + var2.field6386 * var5 + var2.field6386 * var6);
      this.field2428[var1] = -(var2.field6385 * var3 + var2.field6392 * var4 + var2.field6388 * var5 + var2.field6398 * var6);
   }
}
