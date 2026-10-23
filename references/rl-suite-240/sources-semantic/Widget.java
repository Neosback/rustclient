import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import net.runelite.api.Point;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lw")
public class Widget extends Node implements net.runelite.api.widgets.Widget {
   @ObfuscatedName("eg")
   public boolean field4358;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4409 = 1338;
   @ObfuscatedName("hf")
   int field4328;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field4406 = 1400;
   @ObfuscatedName("fu")
   public Object[] field4379;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final int field4413 = 1402;
   @ObfuscatedName("gg")
   public int[] field4398;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   public static final int field4412 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   public static final int field4411 = 3;
   @ObfuscatedName("ek")
   public int field4303;
   @ObfuscatedName("bj")
   public boolean field4273;
   @ObfuscatedName("bk")
   public int field4327;
   @ObfuscatedName("bo")
   public int field4258;
   @ObfuscatedName("bu")
   String field4330;
   @ObfuscatedName("ba")
   public int field4373;
   @ObfuscatedName("bw")
   public int field4245;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   public static final int field4407 = 1;
   @ObfuscatedName("bi")
   public int field4262;
   @ObfuscatedName("dj")
   public int field4243;
   @ObfuscatedName("dh")
   public int field4289;
   @ObfuscatedName("bb")
   public int field4332;
   @ObfuscatedName("bf")
   public int field4246;
   @ObfuscatedName("da")
   public String field4299;
   @ObfuscatedName("bd")
   public int field4253;
   @ObfuscatedName("fo")
   public Object[] field4375;
   @ObfuscatedName("gb")
   public Object[] field4391;
   @ObfuscatedName("bh")
   public int field4255;
   @ToRemove(unused = "true")
   @ObfuscatedName("ww")
   public static int field4416;
   @ObfuscatedName("go")
   public Object[] field4393;
   @ObfuscatedName("cr")
   public int field4257;
   @ObfuscatedName("cu")
   public int field4272;
   @ObfuscatedName("cb")
   public int field4259;
   @ObfuscatedName("gm")
   public String field4315;
   @ObfuscatedName("cp")
   public int field4261;
   @ObfuscatedName("ub")
   public short field4241;
   @ObfuscatedName("be")
   public int field4248;
   @ObfuscatedName("cl")
   public int field4263;
   @ObfuscatedName("cd")
   public int field4264;
   @ObfuscatedName("cv")
   public int field4339;
   @ObfuscatedName("cs")
   public int field4329;
   @ObfuscatedName("cy")
   public int field4256;
   @ObfuscatedName("ew")
   public String[][] field4351;
   @ObfuscatedName("ck")
   public int field4268;
   @ObfuscatedName("ca")
   public int field4293;
   @ObfuscatedName("cc")
   public int field4270;
   @ObfuscatedName("et")
   public String[] field4335;
   @ObfuscatedName("cn")
   public boolean field4269;
   @ObfuscatedSignature(descriptor = "Lye;")
   @ObfuscatedName("ch")
   public classYE field4305;
   @ObfuscatedName("hp")
   HashMap field4266;
   @ObfuscatedName("cg")
   public int field4274;
   @ObfuscatedName("es")
   public boolean field4304;
   @ObfuscatedName("cj")
   public boolean field4276;
   @ObfuscatedName("ed")
   int[] field4251;
   @ObfuscatedName("xk")
   public int field4238 = -1;
   @ObfuscatedName("pb")
   public int field4239 = -1;
   @ObfuscatedName("op")
   public short field4242;
   @ObfuscatedName("cx")
   public int field4347;
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   public static final int field4415 = 9;
   @ObfuscatedSignature(descriptor = "[Llw;")
   @ObfuscatedName("ge")
   public Widget[] field4338;
   @ObfuscatedName("fg")
   public Object[] field4359;
   @ObfuscatedName("dd")
   public int field4281;
   @ObfuscatedName("di")
   public int field4331;
   @ObfuscatedName("gz")
   public Object[] field4244;
   @ObfuscatedName("dy")
   public boolean field4254;
   @ObfuscatedName("cw")
   public int field4275;
   @ObfuscatedName("du")
   public int field4372;
   @ObfuscatedName("ci")
   public int field4346;
   @ObfuscatedName("dr")
   int field4352;
   @ObfuscatedName("dk")
   public int field4286;
   @ObfuscatedName("db")
   public int field4291;
   @ObfuscatedName("dw")
   public int field4288;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4414 = 1337;
   @ObfuscatedName("ej")
   int[] field4360;
   @ObfuscatedName("dz")
   public int field4290;
   @ObfuscatedName("dn")
   public int field4292;
   @ObfuscatedName("bq")
   public int field4250;
   @ObfuscatedName("do")
   public int field4294;
   @ObfuscatedName("dg")
   public int field4260;
   @ObfuscatedSignature(descriptor = "Llc;")
   @ObfuscatedName("dl")
   public PlayerComposition field4356;
   @ObfuscatedName("dq")
   public boolean field4295;
   @ObfuscatedName("df")
   public boolean field4296;
   @ObfuscatedName("ds")
   public int field4297;
   @ObfuscatedName("gd")
   public int[] field4370;
   @ObfuscatedName("dt")
   int field4285;
   @ObfuscatedName("dp")
   public String field4300;
   @ObfuscatedName("ey")
   public int field4301;
   @ObfuscatedName("ea")
   public int field4302;
   @ObfuscatedName("fb")
   public Object[] field4378;
   @ObfuscatedName("fl")
   public Object[] field4355;
   @ObfuscatedName("ga")
   public Object[] field4395;
   @ObfuscatedName("cz")
   public int field4345;
   @ObfuscatedName("ev")
   int[] field4277;
   @ObfuscatedName("gy")
   public String field4316;
   @ObfuscatedName("eh")
   public int field4318;
   @ObfuscatedName("fi")
   public Object[] field4377;
   @ObfuscatedName("en")
   public byte[][] field4310;
   @ObfuscatedName("ce")
   public String field4337;
   @ObfuscatedName("eq")
   public int[] field4368;
   @ObfuscatedName("er")
   public int[] field4369;
   @ObfuscatedName("eo")
   public String field4307;
   @ObfuscatedName("de")
   public int field4354;
   @ObfuscatedName("bs")
   public int field4283;
   @ObfuscatedName("eu")
   public int field4308;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("ef")
   public Widget field4309;
   @ObfuscatedName("em")
   public int field4366;
   @ObfuscatedName("ie")
   public int field4240 = -1;
   @ObfuscatedName("ez")
   public int field4311;
   @ObfuscatedName("ei")
   public boolean field4312;
   @ObfuscatedName("ee")
   public String field4343;
   @ObfuscatedName("eb")
   public boolean field4313;
   @ObfuscatedName("ep")
   public Object[] field4374;
   @ObfuscatedName("dc")
   public boolean field4280;
   @ObfuscatedName("bv")
   public int field4279;
   @ObfuscatedName("fx")
   public Object[] field4384;
   @ObfuscatedName("hl")
   HashMap field4365;
   @ObfuscatedName("fd")
   public Object[] field4400;
   @ObfuscatedName("fy")
   public Object[] field4284;
   @ObfuscatedName("gl")
   public int field4317;
   @ObfuscatedName("cq")
   public boolean field4287;
   @ObfuscatedSignature(descriptor = "Llx;")
   @ObfuscatedName("hg")
   classLX field4367;
   @ObfuscatedName("cm")
   public int field4349;
   @ObfuscatedName("fz")
   public Object[] field4348;
   @ObfuscatedName("fh")
   public Object[] field4380;
   @ObfuscatedName("fq")
   public int[] field4278;
   @ObfuscatedName("fp")
   public Object[] field4353;
   @ObfuscatedName("fs")
   public int[] field4265;
   @ObfuscatedName("by")
   public int field4249;
   @ObfuscatedName("fn")
   public int[] field4383;
   @ObfuscatedName("gu")
   public boolean field4321;
   @ObfuscatedName("ht")
   public boolean field4401;
   @ObfuscatedName("fr")
   public Object[] field4252;
   @ObfuscatedName("ft")
   public Object[] field4385;
   @ObfuscatedName("ff")
   public Object[] field4363;
   @ObfuscatedName("fe")
   public Object[] field4386;
   @ObfuscatedName("fc")
   public Object[] field4387;
   @ObfuscatedName("fm")
   public Object[] field4361;
   @ObfuscatedName("fa")
   public Object[] field4381;
   @ObfuscatedName("fw")
   public Object[] field4389;
   @ObfuscatedName("gq")
   public Object[] field4390;
   @ObfuscatedName("dx")
   public boolean field4350;
   @ObfuscatedName("gv")
   public Object[] field4392;
   @ObfuscatedName("gk")
   public Object[] field4404;
   @ObfuscatedName("bz")
   public int field4247;
   @ObfuscatedName("bl")
   public int field4333;
   @ObfuscatedName("gp")
   public Object[] field4394;
   @ObfuscatedName("ec")
   public Object[] field4357;
   @ObfuscatedName("co")
   public int field4267;
   @ObfuscatedName("gw")
   public int[][] field4397;
   @ObfuscatedSignature(descriptor = "Lma;")
   @ObfuscatedName("hx")
   classMA field4364;
   @ObfuscatedName("gn")
   public int[] field4399;
   @ObfuscatedName("gt")
   public int field4314;
   @ObfuscatedName("gx")
   public int field4325;
   @ObfuscatedName("hr")
   Object[] field4334;
   @ObfuscatedName("gi")
   public int[] field4388;
   @ObfuscatedName("gc")
   public Object[] field4396;
   @ObfuscatedName("fv")
   public Object[] field4382;
   @ObfuscatedName("gs")
   public int field4342;
   @ObfuscatedName("gj")
   public int field4319;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field4410 = 1401;
   @ObfuscatedName("dv")
   public int field4336;
   @ObfuscatedName("gr")
   public boolean field4320;
   @ObfuscatedName("hm")
   public boolean field4371;
   @ObfuscatedName("gf")
   public int field4322;
   @ObfuscatedName("gh")
   public int field4323;
   @ObfuscatedName("hz")
   public int field4298;
   @ObfuscatedName("hw")
   public int field4324;
   @ObfuscatedName("hi")
   public int[] field4402;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field4408 = 1339;
   @ObfuscatedName("fj")
   public Object[] field4376;
   @ObfuscatedName("ct")
   public int field4340;
   @ObfuscatedName("ex")
   public byte[][] field4344;
   @ObfuscatedName("dm")
   public int field4282;
   @ObfuscatedName("cf")
   public int field4271;
   @ObfuscatedName("fk")
   public Object[] field4341;
   @ObfuscatedName("hd")
   public boolean field4326;
   @ObfuscatedName("hk")
   int[] field4403;
   @ObfuscatedName("el")
   String[] field4362;
   @ObfuscatedName("ha")
   int field4306;

   public int getTargetPriority() {
      return this.field4308 * 2085260293;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("dh")
   public int method7270(int var1, int var2) {
      for (int var3 = 0; var3 < -1461666972 * this.field4306; var3++) {
         if (var1 == this.field4403[var3]) {
            return (Integer)this.field4334[var3];
         }
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bm")
   public boolean method7259(int var1) {
      try {
         if (this.field4273) {
            if (var1 != -947902929) {
               throw new IllegalStateException();
            }

            if (0 != this.field4373 * -1510882375) {
               if (var1 != -947902929) {
                  throw new IllegalStateException();
               }

               if (11 != this.field4373 * -1510882375) {
                  if (var1 != -947902929) {
                     throw new IllegalStateException();
                  }

                  if (!this.field4313) {
                     if (var1 != -947902929) {
                        throw new IllegalStateException();
                     }

                     if (-90794217 * this.field4332 != 1338) {
                        if (var1 != -947902929) {
                           throw new IllegalStateException();
                        }

                        if (-1510882375 * this.field4373 != 12) {
                           return false;
                        }

                        if (var1 != -947902929) {
                           throw new IllegalStateException();
                        }
                     }
                  }
               }
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;I)[Ljava/lang/Object;")
   @ObfuscatedName("af")
   Object[] method7209(Object[] var1, int var2) {
      try {
         return var1 != null ? Arrays.copyOf(var1, var1.length) : null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.af(" + ')');
      }
   }

   public boolean isSelfHidden() {
      return this.field4287;
   }

   @ObfuscatedSignature(descriptor = "(Lly;Lda;B)Lym;")
   @ObfuscatedName("ao")
   SpritePixels method7235(classLY var1, classDA var2, byte var3) {
      try {
         if (!this.method7239(1183576002)) {
            if (var3 == 108) {
               throw new IllegalStateException();
            } else {
               return this.method7237(var1, var2, (byte)1);
            }
         } else {
            StringBuilder var10000 = new StringBuilder().append(this.field4337);
            byte var10001;
            if (this.field4350) {
               if (var3 == 108) {
                  throw new IllegalStateException();
               }

               var10001 = 1;
            } else {
               var10001 = 0;
            }

            var10000 = var10000.append((int)var10001);
            if (this.field4254) {
               if (var3 == 108) {
                  throw new IllegalStateException();
               }

               var10001 = 1;
            } else {
               var10001 = 0;
            }

            String var4 = var10000.append((int)var10001).append(this.field4281 * -2011859635).append(this.field4282 * 880555563).toString();
            SpritePixels var5 = (SpritePixels)var1.field4434.method10020(var4, (byte)-7);
            if (null == var5) {
               SpritePixels var6 = this.method7237(var1, var2, (byte)1);
               if (var6 != null) {
                  if (var3 == 108) {
                     throw new IllegalStateException();
                  }

                  var5 = var6.method13520();
                  this.method7243(var5, -2132881644);
                  var1.field4434.method10022(var4, var5, (byte)1);
               }
            }

            return var5;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lw.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("zj")
   public Widget method7371(int var1) {
      this.field4286 = var1 * 742198477;
      return this;
   }

   public void setOnMouseRepeatListener(Object[] var1) {
      this.field4284 = var1;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("so")
   public int[] method7372() {
      return this.field4370;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ed")
   public int method7304() {
      return null != this.field4266 && !this.field4266.isEmpty() ? 1 : 0;
   }

   @ObfuscatedSignature(descriptor = "(Llw;[IB)[I")
   @ObfuscatedName("js")
   public static int[] method7211(Widget var0, int[] var1, byte var2) {
      if (var0 == null) {
         var0.method7214(var1, var2);
      }

      try {
         int[] var10000;
         if (var1 != null) {
            if (var2 == 0) {
               throw new IllegalStateException();
            }

            var10000 = Arrays.copyOf(var1, var1.length);
         } else {
            var10000 = null;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   public static void method7205(int var0) {
      try {
         try {
            classAAC.method125(classME.field4486, -1255450835);

            for (int var1 = 0; var1 < -646722873 * classUP.field6361; var1++) {
               if (var0 != 2000203024) {
                  return;
               }

               classAAC.method125(GraphicsObject.field477[var1], 501841722);
            }

            classAAC.method125(classME.field4487, 1669685026);
            classAAC.method125(classME.field4488, -141358388);
         } catch (Exception var2) {
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;ZLda;I)Lym;")
   @ObfuscatedName("aa")
   public SpritePixels method7369(classLY var1, boolean var2, classDA var3, int var4) {
      if (this.getSpriteId() != -1) {
         net.runelite.api.SpritePixels var5 = (net.runelite.api.SpritePixels)client.field1043.get(this.getId());
         if (var5 != null) {
            return (SpritePixels)var5;
         }
      }

      classDA var9 = var3;
      boolean var8 = var2;
      classLY var7 = var1;
      Widget var6 = this;
      int var10 = 1522102538;

      try {
         if (null != var6.field4337) {
            if (var10 == 240082458) {
               throw new IllegalStateException();
            }

            SpritePixels var11 = var6.method7235(var7, var9, (byte)42);
            if (var11 != null) {
               if (var10 == 240082458) {
                  throw new IllegalStateException();
               }

               return var11;
            }
         }

         int var18;
         if (var8) {
            if (var10 == 240082458) {
               throw new IllegalStateException();
            }

            var18 = -669652445 * var6.field4347;
         } else {
            var18 = 208578545 * var6.field4346;
         }

         SpritePixels var10000;
         if (var18 == -1) {
            if (var10 == 240082458) {
               throw new IllegalStateException();
            }

            Object var16 = null;
            var10000 = (SpritePixels)var16;
         } else {
            long var20 = (long)(880555563 * var6.field4282) << 40;
            long var10001;
            if (var6.field4254) {
               if (var10 == 240082458) {
                  throw new IllegalStateException();
               }

               var10001 = 1L;
            } else {
               var10001 = 0L;
            }

            long var12 = var20 + (var10001 << 39) + ((var6.field4350 ? 1L : 0L) << 38) + ((long)(-2011859635 * var6.field4281) << 36) + var18;
            SpritePixels var14 = (SpritePixels)NodeCache.method6460(var7.field4428, var12);
            if (var14 != null) {
               var10000 = var14;
            } else {
               var14 = classPN.method9084(var7.field4435, var18, 0, -1619347588);
               if (null == var14) {
                  if (var10 == 240082458) {
                     throw new IllegalStateException();
                  }

                  Object var17 = null;
                  var10000 = (SpritePixels)var17;
               } else {
                  var6.method7243(var14, -1164212617);
                  var7.field4428.method6464(var14, var12);
                  var10000 = var14;
               }
            }
         }

         return var10000;
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "lw.aa(" + ')');
      }
   }

   public int getTextColor() {
      return this.field4268 * 1740831551;
   }

   public int getXTextAlignment() {
      return this.field4302 * 1409141973;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;B)V")
   @ObfuscatedName("an")
   public void method7276(int var1, Object var2, byte var3) {
      try {
         for (int var4 = 0; var4 < -2048004067 * this.field4306; var4++) {
            if (var3 >= 4) {
               throw new IllegalStateException();
            }

            if (var1 == this.field4403[var4]) {
               this.field4334[var4] = var2;
               return;
            }
         }

         if (this.field4334 == null) {
            this.field4403 = new int[4];
            this.field4334 = new Object[4];
         } else if (-2048004067 * this.field4306 == this.field4334.length) {
            this.field4403 = Arrays.copyOf(this.field4403, 2 * this.field4403.length);
            this.field4334 = Arrays.copyOf(this.field4334, 2 * this.field4334.length);
         }

         this.field4403[this.field4306 * -2048004067] = var1;
         this.field4334[this.field4306 * -2048004067] = var2;
         this.field4306 += -102477259;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lw.an(" + ')');
      }
   }

   public boolean hasListener() {
      return this.field4313;
   }

   @ObfuscatedSignature(descriptor = "(Lly;Lda;B)Lym;")
   @ObfuscatedName("al")
   SpritePixels method7237(classLY var1, classDA var2, byte var3) {
      try {
         if (this.field4337 != null) {
            if (var3 != 1) {
               throw new IllegalStateException();
            }

            if (null != var2) {
               classLN var4 = (classLN)var1.field4436.method10020(this.field4337, (byte)-26);
               if (null == var4) {
                  if (var3 != 1) {
                     throw new IllegalStateException();
                  }

                  var4 = new classLN(this.field4337, var2);
                  var1.field4436.method10022(this.field4337, var4, (byte)1);
               }

               return classLN.method7167(var4, 1214443818);
            }
         }

         return null;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lw.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aj")
   boolean method7239(int var1) {
      try {
         if (!this.field4350) {
            if (var1 <= 685877715) {
               throw new IllegalStateException();
            }

            if (!this.field4254) {
               if (var1 <= 685877715) {
                  throw new IllegalStateException();
               }

               if (0 == -2011859635 * this.field4281) {
                  if (var1 <= 685877715) {
                     throw new IllegalStateException();
                  }

                  if (880555563 * this.field4282 == 0) {
                     return false;
                  }
               }
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.aj(" + ')');
      }
   }

   public Rectangle getBounds() {
      return new Rectangle(this.field4239, this.field4240, this.getWidth(), this.getHeight());
   }

   @ObfuscatedSignature(descriptor = "(Lly;I)Z")
   @ObfuscatedName("bd")
   public boolean method7350(classLY var1, int var2) {
      try {
         classLH var3 = this.method7334(-1285434602);
         if (var3 == null) {
            if (var2 == -1110124474) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            boolean var4;
            label49: {
               var4 = false;
               if (!var3.method7043((byte)-81)) {
                  if (var2 == -1110124474) {
                     throw new IllegalStateException();
                  }

                  if (-1 != -1803847435 * this.field4243) {
                     if (var2 == -1110124474) {
                        throw new IllegalStateException();
                     }

                     int var5 = classLH.method7045(var3, 1953330546);
                     int var6 = var3.method7049(248323188);
                     int var7 = var3.method7039(652006032);
                     int var8 = var3.method7038((byte)-107);
                     classZV var9 = method7244(this, var1, (byte)-9);
                     if (null != var9) {
                        if (var2 == -1110124474) {
                           throw new IllegalStateException();
                        }

                        var4 |= var3.method6860(var9, -990616751);
                        var4 |= this.method7346(var3, -1853987285);
                        var4 |= var3.method6884(var5, var6, -774367201);
                        var4 |= var3.method6930(var7, var8, 1122842785);
                     }
                     break label49;
                  }
               }

               if (var3.method7043((byte)-23)) {
                  if (var2 == -1110124474) {
                     throw new IllegalStateException();
                  }

                  var4 |= this.method7346(var3, -1385179671);
               }
            }

            classLH.method6840(var3, 145599420);
            return var4;
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "lw.bd(" + ')');
      }
   }

   public int[] getVarTransmitTrigger() {
      return this.field4278;
   }

   public int getFontId() {
      return this.field4243 * -1803847435;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)V")
   @ObfuscatedName("ab")
   void method7215(classXY var1, byte var2) {
      try {
         this.field4273 = false;
         this.field4373 = var1.method13095(-346779531) * 685068937;
         this.field4245 = var1.method13095(-346779531) * -811438059;
         this.field4332 = var1.method13099(943248541) * -411168601;
         this.field4246 = var1.method13104((byte)16) * -1076753689;
         this.field4283 = var1.method13104((byte)16) * 1989424741;
         this.field4253 = var1.method13099(-772132885) * 1034606045;
         this.field4333 = var1.method13099(-1633239070) * -259501473;
         this.field4345 = var1.method13095(-346779531) * -2056841747;
         this.field4340 = var1.method13099(-1526083782) * -676768211;
         if (65535 == this.field4340 * -1502647899) {
            this.field4340 = 676768211;
         } else {
            this.field4340 = -676768211 * ((-1163656551 * this.field4327 & -65536) + -1502647899 * this.field4340);
         }

         this.field4314 = var1.method13099(-1245275110) * -1709618187;
         if (-2003253155 * this.field4314 == 65535) {
            if (var2 >= 0) {
               return;
            }

            this.field4314 = 1709618187;
         }

         int var3 = var1.method13095(-346779531);
         if (var3 > 0) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            this.field4398 = new int[var3];
            this.field4399 = new int[var3];

            for (int var4 = 0; var4 < var3; var4++) {
               this.field4398[var4] = var1.method13095(-346779531);
               this.field4399[var4] = var1.method13099(1091068094);
            }
         }

         int var9 = var1.method13095(-346779531);
         if (var9 > 0) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            this.field4397 = new int[var9][];

            for (int var5 = 0; var5 < var9; var5++) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               int var6 = var1.method13099(-123746907);
               this.field4397[var5] = new int[var6];

               for (int var7 = 0; var7 < var6; var7++) {
                  if (var2 >= 0) {
                     return;
                  }

                  this.field4397[var5][var7] = var1.method13099(-980959917);
                  if (this.field4397[var5][var7] == 65535) {
                     if (var2 >= 0) {
                        throw new IllegalStateException();
                     }

                     this.field4397[var5][var7] = -1;
                  }
               }
            }
         }

         if (0 == -1510882375 * this.field4373) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            this.field4267 = var1.method13099(1175672359) * -251301447;
            this.field4287 = var1.method13095(-346779531) == 1;
         }

         if (-1510882375 * this.field4373 == 1) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            var1.method13099(920361216);
            var1.method13095(-346779531);
         }

         if (3 == -1510882375 * this.field4373) {
            this.field4269 = var1.method13095(-346779531) == 1;
         }

         label358: {
            if (4 != -1510882375 * this.field4373) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               if (1 != this.field4373 * -1510882375) {
                  break label358;
               }

               if (var2 >= 0) {
                  throw new IllegalStateException();
               }
            }

            this.field4302 = var1.method13095(-346779531) * 571804797;
            this.field4303 = var1.method13095(-346779531) * -2106140265;
            this.field4301 = var1.method13095(-346779531) * -763080175;
            this.field4243 = var1.method13099(1522529353) * 590574429;
            if (65535 == this.field4243 * -1803847435) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               this.field4243 = -590574429;
            }

            boolean var10001;
            if (var1.method13095(-346779531) == 1) {
               if (var2 >= 0) {
                  return;
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            this.field4304 = var10001;
         }

         if (this.field4373 * -1510882375 == 4) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            this.field4299 = var1.method13127(1710850257);
            this.field4300 = var1.method13127(1471640970);
         }

         label316: {
            if (1 != -1510882375 * this.field4373) {
               if (var2 >= 0) {
                  return;
               }

               if (3 != this.field4373 * -1510882375) {
                  if (var2 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (this.field4373 * -1510882375 != 4) {
                     break label316;
                  }

                  if (var2 >= 0) {
                     throw new IllegalStateException();
                  }
               }
            }

            this.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
         }

         label307: {
            if (-1510882375 * this.field4373 != 3) {
               if (var2 >= 0) {
                  return;
               }

               if (4 != this.field4373 * -1510882375) {
                  break label307;
               }

               if (var2 >= 0) {
                  throw new IllegalStateException();
               }
            }

            this.field4293 = classXY.method13113(var1, (byte)1) * -1914223631;
            this.field4270 = classXY.method13113(var1, (byte)1) * -1882190803;
            this.field4271 = classXY.method13113(var1, (byte)1) * -1240050015;
         }

         if (5 == this.field4373 * -1510882375) {
            this.field4346 = classXY.method13113(var1, (byte)1) * 857041169;
            this.field4347 = classXY.method13113(var1, (byte)1) * 1515656587;
         }

         if (-1510882375 * this.field4373 == 6) {
            if (var2 >= 0) {
               return;
            }

            this.field4331 = 656328391;
            this.field4285 = -1108433005;
            this.field4372 = var1.method13173(classXM.field6886.field6888, 1593564491) * 434741949;
            this.field4352 = var1.method13173(classXM.field6886.field6888, 1741762813) * 607878109;
            this.field4286 = var1.method13099(-495192957) * 742198477;
            if (65535 == 2114568197 * this.field4286) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               this.field4286 = -742198477;
            }

            this.field4291 = var1.method13099(1533818567) * 668997325;
            if (821151237 * this.field4291 == 65535) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               this.field4291 = -668997325;
            }

            this.field4336 = var1.method13099(-1228383652) * 1556726191;
            this.field4354 = var1.method13099(1322170790) * 1703663435;
            this.field4290 = var1.method13099(1007031475) * 993003239;
         }

         if (8 == -1510882375 * this.field4373) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            this.field4299 = var1.method13127(-953868593);
         }

         if (this.field4245 * -851058883 == 2) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            this.field4343 = var1.method13127(1626437172);
            this.field4315 = var1.method13127(-1228529393);
            int var10 = var1.method13099(-909726546) & 63;
            this.field4318 = (this.field4318 * 1595830249 | var10 << 11) * 997939289;
         }

         label294: {
            if (this.field4245 * -851058883 != 1) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               if (4 != this.field4245 * -851058883) {
                  if (var2 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (5 != -851058883 * this.field4245) {
                     if (var2 >= 0) {
                        return;
                     }

                     if (6 != this.field4245 * -851058883) {
                        break label294;
                     }

                     if (var2 >= 0) {
                        throw new IllegalStateException();
                     }
                  }
               }
            }

            this.field4316 = var1.method13127(-160417512);
            if (this.field4316.isEmpty()) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               if (1 == this.field4245 * -851058883) {
                  this.field4316 = classKK.field3793;
               }

               if (4 == -851058883 * this.field4245) {
                  this.field4316 = classKK.field3610;
               }

               if (-851058883 * this.field4245 == 5) {
                  if (var2 >= 0) {
                     throw new IllegalStateException();
                  }

                  this.field4316 = classKK.field3610;
               }

               if (-851058883 * this.field4245 == 6) {
                  if (var2 >= 0) {
                     return;
                  }

                  this.field4316 = classKK.field3775;
               }
            }
         }

         label283: {
            if (-851058883 * this.field4245 != 1 && 4 != -851058883 * this.field4245) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               if (-851058883 * this.field4245 != 5) {
                  break label283;
               }

               if (var2 >= 0) {
                  throw new IllegalStateException();
               }
            }

            this.field4318 = 997939289 * (1595830249 * this.field4318 | 4194304);
         }

         if (-851058883 * this.field4245 == 6) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            this.field4318 = (this.field4318 * 1595830249 | 1) * 997939289;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lw.ab(" + ')');
      }
   }

   public void setAction(int var1, String var2) {
      this.method7255(var1, var2, (byte)-94);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;Lxy;)[I")
   @ObfuscatedName("cw")
   public static int[] method7227(Widget var0, classXY var1) {
      if (var0 == null) {
         var0.getDragDeadZone();
      }

      int var2 = var1.method13095(-346779531);
      if (0 == var2) {
         return null;
      } else {
         int[] var3 = new int[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            var3[var4] = classXY.method13113(var1, (byte)1);
         }

         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dy")
   public boolean method7260() {
      return !this.field4273
         || 0 == this.field4373 * -1510882375
         || 11 == this.field4373 * -1510882375
         || this.field4313
         || -90794217 * this.field4332 == 1338
         || -1510882375 * this.field4373 == 12;
   }

   public boolean getTextShadowed() {
      return this.field4304;
   }

   @ObfuscatedSignature(descriptor = "(IILjava/lang/String;I)V")
   @ObfuscatedName("ai")
   public void method7257(int var1, int var2, String var3, int var4) {
      try {
         label57: {
            if (this.field4351 != null) {
               if (var4 != -512976453) {
                  throw new IllegalStateException();
               }

               if (this.field4351.length > var1) {
                  break label57;
               }

               if (var4 != -512976453) {
                  return;
               }
            }

            String[][] var5 = new String[var1 + 1][];
            if (null != this.field4351) {
               if (var4 != -512976453) {
                  throw new IllegalStateException();
               }

               System.arraycopy(this.field4351, 0, var5, 0, this.field4351.length);
            }

            this.field4351 = var5;
         }

         label59: {
            if (null != this.field4351[var1]) {
               if (var4 != -512976453) {
                  throw new IllegalStateException();
               }

               if (this.field4351[var1].length > var2) {
                  break label59;
               }

               if (var4 != -512976453) {
                  return;
               }
            }

            String[] var7 = new String[var2 + 1];
            if (this.field4351[var1] != null) {
               System.arraycopy(this.field4351[var1], 0, var7, 0, this.field4351[var1].length);
            }

            this.field4351[var1] = var7;
         }

         this.field4351[var1][var2] = var3;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lw.ai(" + ')');
      }
   }

   public int getOriginalX() {
      return this.field4246 * 975005911;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("on")
   public void method7368(int var1) {
      this.field4239 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;ILjava/lang/Object;)V")
   @ObfuscatedName("yy")
   public static void method7277(Widget var0, int var1, Object var2) {
      if (var0 == null) {
         var0.setRotationY(var1);
      }

      for (int var3 = 0; var3 < -2048004067 * var0.field4306; var3++) {
         if (var1 == var0.field4403[var3]) {
            var0.field4334[var3] = var2;
            return;
         }
      }

      if (var0.field4334 == null) {
         var0.field4403 = new int[4];
         var0.field4334 = new Object[4];
      } else if (-2048004067 * var0.field4306 == var0.field4334.length) {
         var0.field4403 = Arrays.copyOf(var0.field4403, 2 * var0.field4403.length);
         var0.field4334 = Arrays.copyOf(var0.field4334, 2 * var0.field4334.length);
      }

      var0.field4403[var0.field4306 * 280402259] = var1;
      var0.field4334[var0.field4306 * 961827228] = var2;
      var0.field4306 += -102477259;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aw")
   public boolean method7296(int var1) {
      try {
         return this.field4328 * 2133229739 == 2;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.bm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lda;I)V")
   @ObfuscatedName("en")
   public static void method7280(Widget var0, String var1, String var2, String var3, String var4, String var5, classDA var6, int var7) {
      if (var0 == null) {
         var0.method7282(var1, var1, var1, var1, var1, var6, var7);
      }

      try {
         var0.field4364 = new classMA();
         if (!var0.field4364.method7474(var1, var3, var6, 1711128091)) {
            if (var7 >= -1580735539) {
               throw new IllegalStateException();
            } else {
               var0.field4364 = null;
            }
         } else {
            label33: {
               if (null != var0.field4365) {
                  if (var7 >= -1580735539) {
                     throw new IllegalStateException();
                  }

                  if (null != var0.field4266) {
                     break label33;
                  }

                  if (var7 >= -1580735539) {
                     throw new IllegalStateException();
                  }
               }

               var0.method7286(2055703410);
            }

            var0.field4364.method7476(var2, var4, var5, (short)-2712);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lw.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Lly;B)Lzv;")
   @ObfuscatedName("re")
   public static classZV method7244(Widget var0, classLY var1, byte var2) {
      if (var0 == null) {
         var0.method7247(var1, var2);
      }

      try {
         if (var0.field4243 * -1803847435 == -1) {
            return null;
         } else {
            classZV var3 = (classZV)NodeCache.method6460(var1.field4427, -1803847435 * var0.field4243);
            if (null != var3) {
               if (var2 == 1) {
                  throw new IllegalStateException();
               } else {
                  return var3;
               }
            } else {
               IndexDataBase var5 = var1.field4435;
               IndexDataBase var6 = var1.field4440;
               int var7 = var0.field4243 * -1803847435;
               classZV var4;
               if (!classHK.method6100(var5, var7, 0, (byte)-84)) {
                  if (var2 == 1) {
                     throw new IllegalStateException();
                  }

                  var4 = null;
               } else {
                  byte[] var9 = var6.method11942(var7, 0, -1598309711);
                  classZV var8;
                  if (null == var9) {
                     if (var2 == 1) {
                        throw new IllegalStateException();
                     }

                     var8 = null;
                  } else {
                     classZV var10 = new classZV(
                        var9, classYL.field7000, classYL.field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, classYL.field7002
                     );
                     classVZ.method12347((byte)68);
                     var8 = var10;
                  }

                  var4 = var8;
               }

               if (null != var4) {
                  if (var2 == 1) {
                     throw new IllegalStateException();
                  }

                  var1.field4427.method6464(var4, -1803847435 * var0.field4243);
               }

               return var4;
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "lw.aq(" + ')');
      }
   }

   public void setNoClickThrough(boolean var1) {
      this.field4401 = var1;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;I)Ljava/lang/Object;")
   @ObfuscatedName("ak")
   public Object method7267(int var1, Object var2, int var3) {
      try {
         for (int var4 = 0; var4 < -2048004067 * this.field4306; var4++) {
            if (var3 <= 614424977) {
               throw new IllegalStateException();
            }

            if (var1 == this.field4403[var4]) {
               return this.field4334[var4];
            }
         }

         return var2;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lw.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)[I")
   @ObfuscatedName("ca")
   int[] method7228(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (0 == var2) {
         return null;
      } else {
         int[] var3 = new int[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            var3[var4] = classXY.method13113(var1, (byte)1);
         }

         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("at")
   public long method7274(int var1, long var2) {
      try {
         for (int var4 = 0; var4 < this.field4306 * -2048004067; var4++) {
            if (var1 == this.field4403[var4]) {
               return (Long)this.field4334[var4];
            }
         }

         return var2;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lw.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("ov")
   public Widget method7373(int var1) {
      this.field4256 = var1 * -803344527;
      return this;
   }

   public void revalidateScroll() {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         this.revalidate();
         client.method2429(classWK.field6689.field4438[WidgetUtil.componentToInterface(this.getId())], this, false, classWK.field6689, client.field824);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("up")
   public Widget method7374(int var1) {
      this.field4268 = var1 * 1590850751;
      return this;
   }

   public net.runelite.api.widgets.Widget getDragParent() {
      return this.field4309;
   }

   public int getItemQuantityMode() {
      return this.field4297 * -278245589;
   }

   public int getScrollWidth() {
      return this.field4256 * -842862191;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("fw")
   public Widget method7375(int var1) {
      this.field4332 = var1 * -411168601;
      return this;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("en")
   public int[] method7376() {
      return this.field4388;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;II)V")
   @ObfuscatedName("aj")
   public static void method7288(Widget var0, int var1, int var2) {
      if (var0 == null) {
         var0.method7290(var1, var1);
      }

      if (var0.field4373 * -1510882375 == 11) {
         if (var0.field4365 == null) {
            var0.method7286(-1816394492);
         }

         var0.field4365.put(var1, var2);
      }
   }

   @ObfuscatedSignature(descriptor = "()Llx;")
   @ObfuscatedName("fy")
   public classLX method7342() {
      return this.field4367;
   }

   @ObfuscatedSignature(descriptor = "()Lli;")
   @ObfuscatedName("ee")
   public classLI method7336() {
      return this.field4367 != null ? this.field4367.field4419 : null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gc")
   public String method7377() {
      return this.field4299;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILlw;IIIII[F)Llw;")
   @ObfuscatedName("bz")
   static Widget method7206(int var0, Widget var1, int var2, int var3, int var4, int var5, int var6, float[] var7) {
      Widget var8 = new Widget();
      var8.field4373 = 685068937 * var0;
      var8.field4340 = 175404338 * var1.field4327;
      var8.field4258 = var2 * -372272152;
      var8.field4273 = true;
      var8.field4262 = var3 * 1632281619;
      var8.field4248 = var4 * 454876301;
      var8.field4249 = var5 * 1977876794;
      var8.field4250 = -565540095 * var6;
      var8.field4246 = (int)(1115597881 * var1.field4247 * var7[0]) * -1076753689;
      var8.field4283 = 1989424741 * (int)(var7[1] * (367196657 * var1.field4257));
      var8.field4253 = 1034606045 * (int)(var1.field4247 * 1115597881 * var7[2]);
      var8.field4333 = -845375102 * (int)(293773697 * var1.field4257 * var7[3]);
      return var8;
   }

   public void setRelativeX(int var1) {
      this.field4279 = var1 * 2084925661;
   }

   public int getModelId() {
      return this.field4372 * -759306091;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bx")
   void method7286(int var1) {
      try {
         this.field4365 = new HashMap();
         this.field4266 = new HashMap();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.bx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bc")
   public void method7289(int var1, int var2, int var3) {
      try {
         if (this.field4373 * -1510882375 == 11) {
            if (this.field4365 == null) {
               this.method7286(-9650769);
            }

            this.field4365.put(var1, var2);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lw.bc(" + ')');
      }
   }

   public void setDragDeadZone(int var1) {
      this.field4366 = var1 * 230129403;
   }

   @ObfuscatedSignature(descriptor = "(IIII)Llw;")
   @ObfuscatedName("ru")
   public Widget method7362(int var1, int var2, int var3, int var4) {
      return this.method7405(var1).method7386(var2).method7407(var3).method7388(var4);
   }

   public int getRotationZ() {
      return this.field4290 * -1008620329;
   }

   public void setOnDialogAbortListener(Object[] var1) {
      this.field4404 = var1;
   }

   public void setTargetPriority(int var1) {
      this.field4308 = var1 * 173826253;
   }

   public boolean isHidden() {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else if (this.isSelfHidden()) {
         return true;
      } else {
         net.runelite.api.widgets.Widget var1 = this.getParent();
         if (var1 == null) {
            if (WidgetUtil.componentToInterface(this.getId()) != classOE.field4841.getTopLevelInterfaceId()) {
               return true;
            }
         } else if (var1.isHidden()) {
            return true;
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Llw;")
   @ObfuscatedName("st")
   public Widget method7378(boolean var1) {
      this.field4304 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(IIIIB)Z")
   @ObfuscatedName("br")
   public boolean method7294(int var1, int var2, int var3, int var4, byte var5) {
      try {
         if (11 == -1510882375 * this.field4373) {
            if (var5 <= 2) {
               throw new IllegalStateException();
            }

            if (this.field4364 != null) {
               if (var5 <= 2) {
                  throw new IllegalStateException();
               }

               if (this.method7296(1355775728)) {
                  var1 -= var3;
                  var2 -= var4;
                  int var6 = (int)(this.field4364.method7512(-1568061385)[0] * (this.field4247 * 1115597881));
                  int var7 = (int)(this.field4364.method7512(-1201967217)[1] * (this.field4257 * 293773697));
                  int var8 = var6 + (int)(this.field4364.method7512(1325549162)[2] * (1115597881 * this.field4247));
                  int var9 = var7 + (int)(this.field4364.method7512(-2078599276)[3] * (this.field4257 * 293773697));
                  if (var1 >= var6) {
                     if (var5 <= 2) {
                        throw new IllegalStateException();
                     }

                     if (var2 >= var7) {
                        if (var5 <= 2) {
                           throw new IllegalStateException();
                        }

                        if (var1 < var8) {
                           if (var5 <= 2) {
                              throw new IllegalStateException();
                           }

                           if (var2 < var9) {
                              if (var5 <= 2) {
                                 throw new IllegalStateException();
                              }

                              return true;
                           }
                        }
                     }
                  }

                  return false;
               }

               if (var5 <= 2) {
                  throw new IllegalStateException();
               }
            }
         }

         return false;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "lw.br(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;B)V")
   @ObfuscatedName("au")
   public void method7255(int var1, String var2, byte var3) {
      try {
         label53: {
            if (this.field4335 != null) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               if (this.field4335.length > var1) {
                  break label53;
               }

               if (var3 >= 0) {
                  return;
               }
            }

            String[] var4 = new String[1 + var1];
            if (this.field4335 != null) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               for (int var5 = 0; var5 < this.field4335.length; var5++) {
                  if (var3 >= 0) {
                     return;
                  }

                  var4[var5] = this.field4335[var5];
               }
            }

            this.field4335 = var4;
         }

         this.field4335[var1] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lw.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Llw;")
   @ObfuscatedName("ll")
   public Widget method7360(int var1, int var2, int var3, int var4) {
      return this.method7389(var1).method7406(var2).method7392(var3).method7418(var4);
   }

   public void clearActions() {
      this.field4335 = null;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)I")
   @ObfuscatedName("bt")
   public int method7299(String var1, int var2) {
      try {
         if (11 == this.field4373 * -1510882375) {
            if (var2 == 1676325251) {
               throw new IllegalStateException();
            }

            if (this.field4364 != null) {
               if (var2 == 1676325251) {
                  throw new IllegalStateException();
               }

               if (this.method7296(1355775728)) {
                  return this.field4364.method7487(var1, (short)-13436);
               }

               if (var2 == 1676325251) {
                  throw new IllegalStateException();
               }
            }
         }

         return -1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.bt(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("xt")
   public int[] method7316() {
      if (11 == -1510882375 * this.field4373 && null != this.field4364) {
         int[] var1 = new int[3];
         int var2 = 0;

         for (classMD var4 : this.field4364.method7503((byte)12)) {
            if (!var4.method7578((byte)-108).equals("user_id")) {
               if (var4.vmethod525(-1924566210) != 0) {
                  return null;
               }

               var1[var2++] = var4.vmethod523(-2038217113);
               if (var2 > 3) {
                  return null;
               }
            }
         }

         return var1;
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("ap")
   public Widget method7379(int var1) {
      this.field4317 = var1 * 1321867761;
      return this;
   }

   public int getXPositionMode() {
      return this.field4262 * 404669979;
   }

   public int getRelativeY() {
      return this.field4255 * 1570844829;
   }

   @ObfuscatedSignature(descriptor = "(Llw;Lxy;I)[I")
   @ObfuscatedName("dh")
   public static int[] method7229(Widget var0, classXY var1, int var2) {
      if (var0 == null) {
         return var0.method7232(var1, var2);
      } else {
         try {
            int var3 = var1.method13095(-346779531);
            if (0 == var3) {
               if (var2 == 155860854) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               int[] var4 = new int[var3];

               for (int var5 = 0; var5 < var3; var5++) {
                  if (var2 == 155860854) {
                     throw new IllegalStateException();
                  }

                  var4[var5] = classXY.method13113(var1, (byte)1);
               }

               return var4;
            }
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "lw.ac(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("fk")
   public void method7380(String var1) {
      this.field4307 = var1;
   }

   public net.runelite.api.widgets.Widget[] getNestedChildren() {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else if (this.field4258 * 63131743 > -1) {
         return new net.runelite.api.widgets.Widget[0];
      } else {
         WidgetNode var1 = (WidgetNode)classWK.field6689.field6611.method13434(this.field4327 * -1163656551);
         if (var1 == null) {
            return new Widget[0];
         } else {
            int var2 = var1.getId();
            ArrayList var3 = new ArrayList();

            for (Widget var7 : classOE.field4841.method2265(var2)) {
               if (var7 != null && var7.field4340 * -1502647899 == -1) {
                  var3.add(var7);
               }
            }

            return var3.toArray(new Widget[0]);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("bo")
   public String method7313(int var1) {
      try {
         if (-1510882375 * this.field4373 == 11) {
            if (var1 <= -99418320) {
               throw new IllegalStateException();
            }

            if (this.field4364 != null) {
               String var2 = this.field4364.method7506(-1718468240);
               Iterator var3 = this.field4364.method7503((byte)119).iterator();

               while (var3.hasNext()) {
                  if (var1 <= -99418320) {
                     throw new IllegalStateException();
                  }

                  classMD var4 = (classMD)var3.next();
                  String var5 = String.format("%%%S%%", var4.method7578((byte)-51));
                  if (var4.vmethod525(-450426704) == 0) {
                     if (var1 <= -99418320) {
                        throw new IllegalStateException();
                     }

                     var2.replaceAll(var5, Integer.toString(var4.vmethod523(-1704902832)));
                  } else {
                     var2.replaceAll(var5, var4.vmethod519(1699599846));
                  }
               }

               return var2;
            }

            if (var1 <= -99418320) {
               throw new IllegalStateException();
            }
         }

         return null;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lw.bo(" + ')');
      }
   }

   public void setOnHoldListener(Object[] var1) {
      this.field4376 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("hm")
   public Widget method7381(int var1) {
      this.field4267 = var1 * -251301447;
      return this;
   }

   public void setTargetVerb(String var1) {
      this.field4343 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("uk")
   public Widget method7382(int var1) {
      this.field4297 = var1 * -349833341;
      return this;
   }

   public int getScrollY() {
      return this.field4329 * -41424189;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("uh")
   public Widget method7383(int var1) {
      this.field4336 = var1 * 1556726191;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lly;Lda;I)Z")
   @ObfuscatedName("bw")
   public boolean method7325(classLY var1, classDA var2, int var3) {
      try {
         if (11 == this.field4373 * -1510882375) {
            if (var3 <= -425882002) {
               throw new IllegalStateException();
            }

            if (null != this.field4364) {
               this.field4364.method7480(var2, -653470088);
               if (this.field4364.method7483(-139701566) != 2133229739 * this.field4328) {
                  this.field4328 = this.field4364.method7483(-139701566) * -1716422141;
                  if (2133229739 * this.field4328 >= 3) {
                     if (var3 <= -425882002) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }

                  if (2 == this.field4328 * 2133229739) {
                     if (var3 <= -425882002) {
                        throw new IllegalStateException();
                     }

                     this.method7328(var1, 511211855);
                     return true;
                  }
               }

               return false;
            }

            if (var3 <= -425882002) {
               throw new IllegalStateException();
            }
         }

         return false;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lw.bw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bi")
   public void method7332(int var1) {
      try {
         this.field4367 = new classLX();

         for (int var2 = 1; var2 <= 12; var2++) {
            classTT.method11189(this.field4367.field4418, var2, 0, 1709853166);
         }

         for (char var4 = 0; var4 < ' '; var4++) {
            this.field4367.field4418.method11191(var4, 0, (byte)64);
         }

         this.field4367.field4418.method11191('\u0080', 0, (byte)73);
         classTT.method11189(this.field4367.field4418, 82, 2, 2049801007);
         classTT.method11189(this.field4367.field4418, 81, 2, 1782508591);
         classTT.method11189(this.field4367.field4418, 86, 2, 1948751082);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.bi(" + ')');
      }
   }

   public boolean isFlippedVertically() {
      return this.field4350;
   }

   public int getSpriteId() {
      return this.field4346 * 208578545;
   }

   @ObfuscatedSignature(descriptor = "(Lnet/runelite/api/widgets/Widget;)Llw;")
   @ObfuscatedName("se")
   public Widget method7384(net.runelite.api.widgets.Widget var1) {
      this.field4309 = (Widget)var1;
      return this;
   }

   public void setHeight(int var1) {
      this.field4257 = var1 * -1358979455;
   }

   @ObfuscatedSignature(descriptor = "(I)Llh;")
   @ObfuscatedName("be")
   public classLH method7334(int var1) {
      try {
         classLH var10000;
         if (null != this.field4367) {
            if (var1 >= 32768) {
               throw new IllegalStateException();
            }

            var10000 = this.field4367.field4417;
         } else {
            var10000 = null;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.be(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("fa")
   public String method7385() {
      return this.field4307;
   }

   @ObfuscatedSignature(descriptor = "(I)Lli;")
   @ObfuscatedName("by")
   public classLI method7337(int var1) {
      try {
         classLI var10000;
         if (this.field4367 != null) {
            if (var1 <= -14785344) {
               throw new IllegalStateException();
            }

            var10000 = this.field4367.field4419;
         } else {
            var10000 = null;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.by(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("ba")
   public Widget method7386(int var1) {
      this.field4283 = var1 * 1989424741;
      return this;
   }

   public void setDragDeadTime(int var1) {
      this.field4311 = var1 * -2074329479;
   }

   @ObfuscatedSignature(descriptor = "(I)Ltt;")
   @ObfuscatedName("bq")
   public classTT method7338(int var1) {
      try {
         return this.field4367 != null ? this.field4367.field4418 : null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.bq(" + ')');
      }
   }

   public int getRotationX() {
      return this.field4354 * -1246332317;
   }

   public void setWidth(int var1) {
      this.field4247 = var1 * -1261469175;
   }

   @ObfuscatedSignature(descriptor = "(I)Llx;")
   @ObfuscatedName("bf")
   public classLX method7343(int var1) {
      try {
         return this.field4367;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.bf(" + ')');
      }
   }

   public int getParentId() {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         int var1 = this.field4340 * -1502647899;
         if (var1 != -1) {
            return var1;
         } else {
            int var2 = this.getId();
            if (WidgetUtil.componentToInterface(var2) == classOE.field4841.getTopLevelInterfaceId()) {
               return -1;
            } else {
               int var3 = this.field4238;
               if (var3 != -1) {
                  WidgetNode var4 = (WidgetNode)classWK.field6689.field6611.method13434(var3);
                  if (var4 != null && var4.getId() == WidgetUtil.componentToInterface(var2)) {
                     return var3;
                  }

                  this.field4238 = -1;
               }

               int var8 = WidgetUtil.componentToInterface(this.getId());

               for (WidgetNode var7 : classOE.field4841.method2248()) {
                  if (var8 == var7.getId()) {
                     return (int)var7.getHash();
                  }
               }

               return -1;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;I)Z")
   @ObfuscatedName("bs")
   boolean method7346(classLH var1, int var2) {
      try {
         boolean var3 = false;
         if (this.field4299 != null) {
            if (var2 >= -1338474268) {
               throw new IllegalStateException();
            }

            if (!this.field4299.isEmpty()) {
               if (var2 >= -1338474268) {
                  throw new IllegalStateException();
               }

               var3 |= classLH.method6855(var1, classLB.method6708(this.field4299, -904615318), -1480555760);
               this.field4299 = "";
            }
         }

         if (this.field4300 != null && !this.field4300.isEmpty()) {
            if (var2 >= -1338474268) {
               throw new IllegalStateException();
            }

            var3 |= var1.method6859(classLB.method6708(this.field4300, -1261950944), 1655923611);
            this.field4300 = "";
         }

         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lw.bs(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llc;ZB)V")
   @ObfuscatedName("bl")
   public void method7353(PlayerComposition var1, boolean var2, byte var3) {
      try {
         this.field4331 = 299331441;
         this.field4356 = new PlayerComposition(var1);
         if (!var2) {
            if (var3 <= -4) {
               throw new IllegalStateException();
            }

            this.field4356.field4093 = Arrays.copyOf(this.field4356.field4090, this.field4356.field4090.length);
            this.field4356.method6754((byte)-58);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lw.bl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("rt")
   public Widget method7387(int var1) {
      this.field4354 = var1 * 1703663435;
      return this;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILlw;IIIII[F)Llw;")
   @ObfuscatedName("bv")
   static Widget method7207(int var0, Widget var1, int var2, int var3, int var4, int var5, int var6, float[] var7) {
      Widget var8 = new Widget();
      var8.field4373 = 685068937 * var0;
      var8.field4340 = 498391781 * var1.field4327;
      var8.field4258 = var2 * -1612510305;
      var8.field4273 = true;
      var8.field4262 = var3 * -586967037;
      var8.field4248 = var4 * 454876301;
      var8.field4249 = var5 * -1430539503;
      var8.field4250 = -1106115532 * var6;
      var8.field4246 = (int)(1115597881 * var1.field4247 * var7[0]) * 368215045;
      var8.field4283 = 2124598895 * (int)(var7[1] * (293773697 * var1.field4257));
      var8.field4253 = 1034606045 * (int)(var1.field4247 * 1115597881 * var7[2]);
      var8.field4333 = -1953519449 * (int)(293773697 * var1.field4257 * var7[3]);
      return var8;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("lv")
   public static boolean method7262(Widget var0) {
      if (var0 == null) {
         var0.getScrollY();
      }

      return 11 == var0.field4373 * -1354330688 || var0.field4373 * 13322969 == 12;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("ft")
   public Widget method7388(int var1) {
      this.field4248 = var1 * 454876301;
      return this;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILlw;IIIII[F)Llw;")
   @ObfuscatedName("bh")
   static Widget method7208(int var0, Widget var1, int var2, int var3, int var4, int var5, int var6, float[] var7) {
      Widget var8 = new Widget();
      var8.field4373 = 1274993067 * var0;
      var8.field4340 = -953747743 * var1.field4327;
      var8.field4258 = var2 * -1612510305;
      var8.field4273 = true;
      var8.field4262 = var3 * 1632281619;
      var8.field4248 = var4 * 744640539;
      var8.field4249 = var5 * 1757502277;
      var8.field4250 = -565540095 * var6;
      var8.field4246 = (int)(1055563839 * var1.field4247 * var7[0]) * 924658261;
      var8.field4283 = -1966132731 * (int)(var7[1] * (-1640459841 * var1.field4257));
      var8.field4253 = 1330453301 * (int)(var1.field4247 * 2032338429 * var7[2]);
      var8.field4333 = 1791847397 * (int)(637353772 * var1.field4257 * var7[3]);
      return var8;
   }

   public void deleteAllChildren() {
      if (this.method7391() != null) {
         Arrays.fill(this.method7391(), null);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lym;)V")
   @ObfuscatedName("cw")
   void method7242(SpritePixels var1) {
      if (this.field4350) {
         var1.method13542();
      }

      if (this.field4254) {
         var1.method13535();
      }

      if (-2011859635 * this.field4281 > 0) {
         var1.method13532(this.field4281 * -2011859635);
      }

      if (this.field4281 * -2011859635 >= 1) {
         var1.method13543(1);
      }

      if (this.field4281 * -2011859635 >= 2) {
         var1.method13543(16777215);
      }

      if (880555563 * this.field4282 != 0) {
         SpritePixels.method13548(var1, this.field4282 * 880555563);
      }
   }

   public void revalidate() {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         client.method2424(this, classSA.field5895 * 773246731, IntProjection.field1936 * 8379747, classWK.field6689, client.field824);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("vs")
   public void method7361(int var1) {
      this.field4238 = var1;
   }

   @ObfuscatedSignature(descriptor = "([I)[I")
   @ObfuscatedName("cb")
   int[] method7212(int[] var1) {
      return var1 != null ? Arrays.copyOf(var1, var1.length) : null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bz")
   public void method7367(int var1) {
      if (this.field4242 > -1) {
         this.setRelativeY(this.field4242);
      }
   }

   public void setOnClickListener(Object[] var1) {
      this.field4357 = var1;
   }

   public int getIndex() {
      return this.field4258 * 63131743;
   }

   public void setOnMouseOverListener(Object[] var1) {
      this.field4400 = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ev")
   public int method7309() {
      if (11 == this.field4373 * -1510882375 && null != this.field4364 && this.field4266 != null && !this.field4266.isEmpty()) {
         String var1 = this.field4364.method7506(-1228083952);
         return var1 != null && this.field4266.containsKey(this.field4364.method7506(-98673453)) ? (Integer)this.field4266.get(var1) : -1;
      } else {
         return -1;
      }
   }

   public int getOriginalHeight() {
      return this.field4333 * 549489055;
   }

   @ObfuscatedSignature(descriptor = "(Llw;Lly;Lou;IZLlc;Lpl;Lpp;I)Lfx;")
   @ObfuscatedName("hg")
   public static Model method7248(
      Widget var0, classLY var1, Animation var2, int var3, boolean var4, PlayerComposition var5, NPCComposition var6, NpcOverrides var7, int var8
   ) {
      if (var0 == null) {
         var0.method7250(var1, var2, var3, var4, var5, var6, var7, var3);
      }

      try {
         int var9;
         int var10;
         if (var4) {
            var9 = -799043941 * var0.field4285;
            var10 = 697152629 * var0.field4352;
         } else {
            var9 = var0.field4331 * -910601;
            var10 = var0.field4372 * -759306091;
         }

         if (var9 == 0) {
            if (var8 >= -1946717396) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            if (1 == var9) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               if (-1 == var10) {
                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }

                  return null;
               }
            }

            if (var7 != null) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               if (var7.field5375) {
                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }

                  if (6 == var9) {
                     if (var8 >= -1946717396) {
                        throw new IllegalStateException();
                     }

                     var9 = 3;
                  }
               }
            }

            long var11;
            label143: {
               var11 = (var9 << 16) + var10;
               if (2 != var9) {
                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }

                  if (var9 != 6) {
                     break label143;
                  }

                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }
               }

               if (var6 == null) {
                  return null;
               }

               var11 = (var9 << 16) + 283969983 * var6.field5300;
            }

            if (null != var7) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               var11 |= -4980301671096129853L * var7.field5376 << 20;
            }

            Model var13 = (Model)NodeCache.method6460(var1.field4429, var11);
            if (null == var13) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               ModelData var14 = null;
               int var15 = 64;
               int var16 = 768;
               switch (var9) {
                  case 1:
                     var14 = ModelData.method4067(var1.field4432, var10, 0);
                     break;
                  case 2:
                     var14 = var6.method8987(null, (byte)37);
                     break;
                  case 3:
                     ModelData var10000;
                     if (null != var5) {
                        if (var8 >= -1946717396) {
                           throw new IllegalStateException();
                        }

                        var10000 = PlayerComposition.method6746(var5, (byte)0);
                     } else {
                        var10000 = null;
                     }

                     var14 = var10000;
                     break;
                  case 4:
                     ItemComposition var17 = classOB.method8329(var10, (byte)77);
                     var14 = var17.method8688(10, -1145537891);
                     var15 += 1845819663 * var17.field5058;
                     var16 += var17.field5028 * -1636300529;
                  case 5:
                  default:
                     break;
                  case 6:
                     var14 = var6.method8987(var7, (byte)115);
               }

               if (null == var14) {
                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }

                  return null;
               }

               var13 = var14.method4070(var15, var16, -50, -10, -50);
               var1.field4429.method6464(var13, var11);
            }

            if (null != var2) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               var13 = var2.method8801(var13, var3, -2146329348);
            }

            return var13;
         }
      } catch (RuntimeException var18) {
         throw classEG.method3899(var18, "lw.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llc;Z)V")
   @ObfuscatedName("fq")
   public void method7354(PlayerComposition var1, boolean var2) {
      this.field4331 = 299331441;
      this.field4356 = new PlayerComposition(var1);
      if (!var2) {
         this.field4356.field4093 = Arrays.copyOf(this.field4356.field4090, this.field4356.field4090.length);
         this.field4356.method6754((byte)-23);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("cl")
   void method7217(classXY var1) {
      var1.method13095(-346779531);
      this.field4273 = true;
      this.field4373 = var1.method13095(-346779531) * 685068937;
      this.field4332 = var1.method13099(1226095204) * -411168601;
      this.field4246 = var1.method13104((byte)16) * -1076753689;
      this.field4283 = var1.method13104((byte)16) * 1989424741;
      this.field4253 = var1.method13099(1706687071) * 1034606045;
      if (9 == this.field4373 * -1510882375) {
         this.field4333 = var1.method13104((byte)16) * -259501473;
      } else {
         this.field4333 = var1.method13099(-1796836835) * -259501473;
      }

      this.field4249 = var1.method13097((byte)17) * -1430539503;
      this.field4250 = var1.method13097((byte)17) * -565540095;
      this.field4262 = var1.method13097((byte)17) * 1632281619;
      this.field4248 = var1.method13097((byte)17) * 454876301;
      this.field4340 = var1.method13099(-1371666693) * -676768211;
      if (-1502647899 * this.field4340 == 65535) {
         this.field4340 = 676768211;
      } else {
         this.field4340 = -676768211 * (-1502647899 * this.field4340 + (-1163656551 * this.field4327 & -65536));
      }

      this.field4287 = var1.method13095(-346779531) == 1;
      if (0 == -1510882375 * this.field4373) {
         this.field4256 = var1.method13099(-800624117) * -803344527;
         this.field4267 = var1.method13099(-1235426038) * -251301447;
         this.field4401 = var1.method13095(-346779531) == 1;
      }

      if (5 == this.field4373 * -1510882375) {
         this.field4346 = classXY.method13113(var1, (byte)1) * 857041169;
         this.field4349 = var1.method13099(-1604054585) * -727683123;
         this.field4280 = var1.method13095(-346779531) == 1;
         this.field4345 = var1.method13095(-346779531) * -2056841747;
         this.field4281 = var1.method13095(-346779531) * -1993421947;
         this.field4282 = classXY.method13113(var1, (byte)1) * 37017731;
         this.field4350 = var1.method13095(-346779531) == 1;
         this.field4254 = var1.method13095(-346779531) == 1;
      }

      if (6 == this.field4373 * -1510882375) {
         this.field4331 = 656328391;
         this.field4372 = var1.method13173(classXM.field6886.field6888, 1885025106) * 434741949;
         this.field4288 = var1.method13104((byte)16) * 1981476425;
         this.field4289 = var1.method13104((byte)16) * 1954863581;
         this.field4354 = var1.method13099(-490902197) * 1703663435;
         this.field4290 = var1.method13099(30676335) * 993003239;
         this.field4292 = var1.method13099(144456973) * 863028557;
         this.field4336 = var1.method13099(-109871164) * 1556726191;
         this.field4286 = var1.method13099(33839186) * 742198477;
         if (65535 == this.field4286 * 2114568197) {
            this.field4286 = -742198477;
         }

         this.field4295 = var1.method13095(-346779531) == 1;
         var1.method13099(-1573447692);
         if (-1966367247 * this.field4249 != 0) {
            this.field4294 = var1.method13099(-181170079) * 1299951355;
         }

         if (this.field4250 * 372143361 != 0) {
            var1.method13099(-434676914);
         }
      }

      if (4 == -1510882375 * this.field4373) {
         this.field4243 = var1.method13099(259000072) * 590574429;
         if (this.field4243 * -1803847435 == 65535) {
            this.field4243 = -590574429;
         }

         this.field4299 = var1.method13127(1660926795);
         this.field4301 = var1.method13095(-346779531) * -763080175;
         this.field4302 = var1.method13095(-346779531) * 571804797;
         this.field4303 = var1.method13095(-346779531) * -2106140265;
         this.field4304 = var1.method13095(-346779531) == 1;
         this.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
      }

      if (-1510882375 * this.field4373 == 3) {
         this.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
         this.field4269 = var1.method13095(-346779531) == 1;
         this.field4345 = var1.method13095(-346779531) * -2056841747;
      }

      if (9 == this.field4373 * -1510882375) {
         this.field4275 = var1.method13095(-346779531) * 327091521;
         this.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
         this.field4276 = var1.method13095(-346779531) == 1;
      }

      this.field4318 = var1.method13105(-758448158) * 997939289;
      this.field4307 = var1.method13127(1682930918);
      int var2 = var1.method13095(-346779531);
      if (var2 > 0) {
         this.field4335 = new String[var2];

         for (int var3 = 0; var3 < var2; var3++) {
            this.field4335[var3] = var1.method13127(-583830759);
         }
      }

      this.field4366 = var1.method13095(-346779531) * 230129403;
      this.field4311 = var1.method13095(-346779531) * -2074329479;
      this.field4312 = var1.method13095(-346779531) == 1;
      this.field4343 = var1.method13127(994951657);
      this.field4374 = this.method7226(var1, 1274305683);
      this.field4400 = this.method7226(var1, 810947961);
      this.field4359 = this.method7226(var1, 1255855957);
      this.field4348 = this.method7226(var1, 392879593);
      this.field4379 = this.method7226(var1, 644062094);
      this.field4380 = this.method7226(var1, 1805554483);
      this.field4353 = this.method7226(var1, 507220260);
      this.field4382 = this.method7226(var1, 806431069);
      this.field4341 = this.method7226(var1, 2027846104);
      this.field4384 = this.method7226(var1, 1045371606);
      this.field4284 = this.method7226(var1, 1126525558);
      this.field4357 = this.method7226(var1, 1752793236);
      this.field4375 = this.method7226(var1, 783002508);
      this.field4355 = this.method7226(var1, 760327156);
      this.field4376 = this.method7226(var1, 743115669);
      this.field4377 = this.method7226(var1, 989426923);
      this.field4378 = this.method7226(var1, 1446497328);
      this.field4385 = this.method7226(var1, 1031532315);
      this.field4278 = method7229(this, var1, -676093146);
      this.field4265 = method7229(this, var1, -610265893);
      this.field4383 = method7229(this, var1, 1578417430);
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("yg")
   public Widget method7389(int var1) {
      this.field4253 = var1 * 1034606045;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("cd")
   void method7218(classXY var1) {
      var1.method13095(-346779531);
      this.field4273 = true;
      this.field4373 = var1.method13095(-346779531) * 685068937;
      this.field4332 = var1.method13099(-572661734) * -411168601;
      this.field4246 = var1.method13104((byte)16) * -1076753689;
      this.field4283 = var1.method13104((byte)16) * 1989424741;
      this.field4253 = var1.method13099(7580717) * 1034606045;
      if (9 == this.field4373 * -1510882375) {
         this.field4333 = var1.method13104((byte)16) * -259501473;
      } else {
         this.field4333 = var1.method13099(1807899564) * -259501473;
      }

      this.field4249 = var1.method13097((byte)17) * -1430539503;
      this.field4250 = var1.method13097((byte)17) * -565540095;
      this.field4262 = var1.method13097((byte)17) * 1632281619;
      this.field4248 = var1.method13097((byte)17) * 454876301;
      this.field4340 = var1.method13099(960579237) * -676768211;
      if (-1502647899 * this.field4340 == 65535) {
         this.field4340 = 676768211;
      } else {
         this.field4340 = -676768211 * (-1502647899 * this.field4340 + (-1163656551 * this.field4327 & -65536));
      }

      this.field4287 = var1.method13095(-346779531) == 1;
      if (0 == -1510882375 * this.field4373) {
         this.field4256 = var1.method13099(1648727926) * -803344527;
         this.field4267 = var1.method13099(-551555091) * -251301447;
         this.field4401 = var1.method13095(-346779531) == 1;
      }

      if (5 == this.field4373 * -1510882375) {
         this.field4346 = classXY.method13113(var1, (byte)1) * 857041169;
         this.field4349 = var1.method13099(807489590) * -727683123;
         this.field4280 = var1.method13095(-346779531) == 1;
         this.field4345 = var1.method13095(-346779531) * -2056841747;
         this.field4281 = var1.method13095(-346779531) * -1993421947;
         this.field4282 = classXY.method13113(var1, (byte)1) * 37017731;
         this.field4350 = var1.method13095(-346779531) == 1;
         this.field4254 = var1.method13095(-346779531) == 1;
      }

      if (6 == this.field4373 * -1510882375) {
         this.field4331 = 656328391;
         this.field4372 = var1.method13173(classXM.field6886.field6888, 1446318228) * 434741949;
         this.field4288 = var1.method13104((byte)16) * 1981476425;
         this.field4289 = var1.method13104((byte)16) * 1954863581;
         this.field4354 = var1.method13099(17865158) * 1703663435;
         this.field4290 = var1.method13099(163568153) * 993003239;
         this.field4292 = var1.method13099(-51558828) * 863028557;
         this.field4336 = var1.method13099(288905224) * 1556726191;
         this.field4286 = var1.method13099(924238525) * 742198477;
         if (65535 == this.field4286 * 2114568197) {
            this.field4286 = -742198477;
         }

         this.field4295 = var1.method13095(-346779531) == 1;
         var1.method13099(1026166147);
         if (-1966367247 * this.field4249 != 0) {
            this.field4294 = var1.method13099(745655976) * 1299951355;
         }

         if (this.field4250 * 372143361 != 0) {
            var1.method13099(-673847697);
         }
      }

      if (4 == -1510882375 * this.field4373) {
         this.field4243 = var1.method13099(-1164198420) * 590574429;
         if (this.field4243 * -1803847435 == 65535) {
            this.field4243 = -590574429;
         }

         this.field4299 = var1.method13127(366086190);
         this.field4301 = var1.method13095(-346779531) * -763080175;
         this.field4302 = var1.method13095(-346779531) * 571804797;
         this.field4303 = var1.method13095(-346779531) * -2106140265;
         this.field4304 = var1.method13095(-346779531) == 1;
         this.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
      }

      if (-1510882375 * this.field4373 == 3) {
         this.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
         this.field4269 = var1.method13095(-346779531) == 1;
         this.field4345 = var1.method13095(-346779531) * -2056841747;
      }

      if (9 == this.field4373 * -1510882375) {
         this.field4275 = var1.method13095(-346779531) * 327091521;
         this.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
         this.field4276 = var1.method13095(-346779531) == 1;
      }

      this.field4318 = var1.method13105(-758448158) * 997939289;
      this.field4307 = var1.method13127(1943259612);
      int var2 = var1.method13095(-346779531);
      if (var2 > 0) {
         this.field4335 = new String[var2];

         for (int var3 = 0; var3 < var2; var3++) {
            this.field4335[var3] = var1.method13127(829102579);
         }
      }

      this.field4366 = var1.method13095(-346779531) * 230129403;
      this.field4311 = var1.method13095(-346779531) * -2074329479;
      this.field4312 = var1.method13095(-346779531) == 1;
      this.field4343 = var1.method13127(-139400180);
      this.field4374 = this.method7226(var1, 1215530211);
      this.field4400 = this.method7226(var1, 975385127);
      this.field4359 = this.method7226(var1, 1659929587);
      this.field4348 = this.method7226(var1, 1423637524);
      this.field4379 = this.method7226(var1, 258283176);
      this.field4380 = this.method7226(var1, 245214415);
      this.field4353 = this.method7226(var1, 1950724952);
      this.field4382 = this.method7226(var1, 1358273455);
      this.field4341 = this.method7226(var1, 386757892);
      this.field4384 = this.method7226(var1, 460321647);
      this.field4284 = this.method7226(var1, 1013912744);
      this.field4357 = this.method7226(var1, 1093534699);
      this.field4375 = this.method7226(var1, 575681170);
      this.field4355 = this.method7226(var1, 1592924745);
      this.field4376 = this.method7226(var1, 1968989750);
      this.field4377 = this.method7226(var1, 802794107);
      this.field4378 = this.method7226(var1, 326377191);
      this.field4385 = this.method7226(var1, 1967657751);
      this.field4278 = method7229(this, var1, 629149806);
      this.field4265 = method7229(this, var1, -787508661);
      this.field4383 = method7229(this, var1, 664267371);
   }

   public int getClickMask() {
      return this.field4318 * 1595830249;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;Llh;)Z")
   @ObfuscatedName("ii")
   public static boolean method7347(Widget var0, classLH var1) {
      if (var0 == null) {
         var0.getYTextAlignment();
      }

      boolean var2 = false;
      if (var0.field4299 != null && !var0.field4299.isEmpty()) {
         var2 |= classLH.method6855(var1, classLB.method6708(var0.field4299, -1650575090), 1550228137);
         var0.field4299 = "";
      }

      if (var0.field4300 != null && !var0.field4300.isEmpty()) {
         var2 |= var1.method6859(classLB.method6708(var0.field4300, -809983775), -608172188);
         var0.field4300 = "";
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("cv")
   void method7221(classXY var1) {
      this.field4330 = var1.method13127(-532316096);
   }

   public int getId() {
      return this.field4327 * -1163656551;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("cs")
   void method7222(classXY var1) {
      this.field4330 = var1.method13127(-545224598);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)[Ljava/lang/Object;")
   @ObfuscatedName("cy")
   Object[] method7225(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (0 == var2) {
         return null;
      } else {
         Object[] var3 = new Object[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            int var5 = var1.method13095(-346779531);
            if (var5 == 0) {
               var3[var4] = new Integer(classXY.method13113(var1, (byte)1));
            } else if (1 == var5) {
               var3[var4] = var1.method13127(-98013612);
            }
         }

         this.field4313 = true;
         return var3;
      }
   }

   public void setOnOpListener(Object[] var1) {
      this.field4384 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)[I")
   @ObfuscatedName("co")
   int[] method7230(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (0 == var2) {
         return null;
      } else {
         int[] var3 = new int[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            var3[var4] = classXY.method13113(var1, (byte)1);
         }

         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "([I)[I")
   @ObfuscatedName("ct")
   int[] method7213(int[] var1) {
      return var1 != null ? Arrays.copyOf(var1, var1.length) : null;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("oa")
   public Widget method7390(int var1) {
      this.field4292 = var1 * 863028557;
      return this;
   }

   public boolean isFlippedHorizontally() {
      return this.field4254;
   }

   @ObfuscatedSignature(descriptor = "(Llc;Z)V")
   @ObfuscatedName("fs")
   public void method7355(PlayerComposition var1, boolean var2) {
      this.field4331 = -390889635;
      this.field4356 = new PlayerComposition(var1);
      if (!var2) {
         this.field4356.field4093 = Arrays.copyOf(this.field4356.field4090, this.field4356.field4090.length);
         this.field4356.method6754((byte)-42);
      }
   }

   public int getScrollX() {
      return this.field4339 * -34809441;
   }

   public int getRelativeX() {
      return this.field4279 * 744286069;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cl")
   public void method7357(int var1) {
      if (this.field4241 > -1) {
         this.setRelativeX(this.field4241);
      }
   }

   public void setForcedPosition(int var1, int var2) {
      this.field4241 = (short)var1;
      this.field4242 = (short)var2;
      this.setRelativeX(var1);
      this.setRelativeY(var2);
   }

   @ObfuscatedSignature(descriptor = "()[Llw;")
   @ObfuscatedName("qz")
   public Widget[] method7391() {
      return this.field4338;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("eh")
   public String method7314() {
      if (-1510882375 * this.field4373 == 11 && this.field4364 != null) {
         String var1 = this.field4364.method7506(-1360847022);

         for (classMD var3 : this.field4364.method7503((byte)88)) {
            String var4 = String.format("%%%S%%", var3.method7578((byte)-33));
            if (var3.vmethod525(588701019) == 0) {
               var1.replaceAll(var4, Integer.toString(var3.vmethod523(-2098433103)));
            } else {
               var1.replaceAll(var4, var3.vmethod519(-1416515802));
            }
         }

         return var1;
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;ZLda;)Lym;")
   @ObfuscatedName("cf")
   public SpritePixels method7233(classLY var1, boolean var2, classDA var3) {
      if (null != this.field4337) {
         SpritePixels var4 = this.method7235(var1, var3, (byte)58);
         if (var4 != null) {
            return var4;
         }
      }

      int var8;
      if (var2) {
         var8 = -669652445 * this.field4347;
      } else {
         var8 = 208578545 * this.field4346;
      }

      if (var8 == -1) {
         return null;
      } else {
         long var5 = ((long)(880555563 * this.field4282) << 40)
            + ((this.field4254 ? 1L : 0L) << 39)
            + ((this.field4350 ? 1L : 0L) << 38)
            + ((long)(-2011859635 * this.field4281) << 36)
            + var8;
         SpritePixels var7 = (SpritePixels)NodeCache.method6460(var1.field4428, var5);
         if (var7 != null) {
            return var7;
         } else {
            var7 = classPN.method9084(var1.field4435, var8, 0, -1750895898);
            if (null == var7) {
               return null;
            } else {
               this.method7243(var7, -2107773143);
               var1.field4428.method6464(var7, var5);
               return var7;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)I")
   @ObfuscatedName("sd")
   public static int method7310(Widget var0, byte var1) {
      if (var0 == null) {
         return var0.method7312(var1);
      } else {
         try {
            if (11 == var0.field4373 * -1510882375) {
               if (var1 >= 2) {
                  throw new IllegalStateException();
               }

               if (null != var0.field4364) {
                  if (var1 >= 2) {
                     throw new IllegalStateException();
                  }

                  if (var0.field4266 != null) {
                     if (var1 >= 2) {
                        throw new IllegalStateException();
                     }

                     if (!var0.field4266.isEmpty()) {
                        String var2 = var0.field4364.method7506(-391312536);
                        if (var2 != null && var0.field4266.containsKey(var0.field4364.method7506(-56824379))) {
                           return (Integer)var0.field4266.get(var2);
                        }

                        return -1;
                     }

                     if (var1 >= 2) {
                        throw new IllegalStateException();
                     }
                  }
               }
            }

            return -1;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "lw.bk(" + 41);
         }
      }
   }

   public void setChildren(net.runelite.api.widgets.Widget[] var1) {
      if (this.getType() != 0) {
         throw new UnsupportedOperationException("children can only be set on type layer widgets");
      } else if (this.field4258 * 63131743 != -1) {
         throw new UnsupportedOperationException("only static widgets may have children");
      } else {
         this.field4338 = (Widget[])var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;ZLda;)Lym;")
   @ObfuscatedName("cn")
   public SpritePixels method7234(classLY var1, boolean var2, classDA var3) {
      if (null != this.field4337) {
         SpritePixels var4 = this.method7235(var1, var3, (byte)-102);
         if (var4 != null) {
            return var4;
         }
      }

      int var8;
      if (var2) {
         var8 = -669652445 * this.field4347;
      } else {
         var8 = 208578545 * this.field4346;
      }

      if (var8 == -1) {
         return null;
      } else {
         long var5 = ((long)(880555563 * this.field4282) << 40)
            + ((this.field4254 ? 1L : 0L) << 39)
            + ((this.field4350 ? 1L : 0L) << 38)
            + ((long)(-2011859635 * this.field4281) << 36)
            + var8;
         SpritePixels var7 = (SpritePixels)NodeCache.method6460(var1.field4428, var5);
         if (var7 != null) {
            return var7;
         } else {
            var7 = classPN.method9084(var1.field4435, var8, 0, -1732651649);
            if (null == var7) {
               return null;
            } else {
               this.method7243(var7, -1791269977);
               var1.field4428.method6464(var7, var5);
               return var7;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;Lda;)Lym;")
   @ObfuscatedName("ch")
   SpritePixels method7236(classLY var1, classDA var2) {
      if (!this.method7239(1894829560)) {
         return this.method7237(var1, var2, (byte)1);
      } else {
         String var3 = this.field4337 + (this.field4350 ? 1 : 0) + (this.field4254 ? 1 : 0) + this.field4281 * -2011859635 + this.field4282 * 880555563;
         SpritePixels var4 = (SpritePixels)var1.field4434.method10020(var3, (byte)61);
         if (null == var4) {
            SpritePixels var5 = this.method7237(var1, var2, (byte)1);
            if (var5 != null) {
               var4 = var5.method13520();
               this.method7243(var4, -933143798);
               var1.field4434.method10022(var3, var4, (byte)1);
            }
         }

         return var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Lxy;I)V")
   @ObfuscatedName("uc")
   public static void method7223(Widget var0, classXY var1, int var2) {
      if (var0 == null) {
         var0.method7224(var1, var2);
      } else {
         try {
            var0.field4330 = var1.method13127(552941395);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "lw.as(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("av")
   public int method7271(int var1, int var2, byte var3) {
      try {
         for (int var4 = 0; var4 < -2048004067 * this.field4306; var4++) {
            if (var3 == 1) {
               throw new IllegalStateException();
            }

            if (var1 == this.field4403[var4]) {
               if (var3 == 1) {
                  throw new IllegalStateException();
               }

               return (Integer)this.field4334[var4];
            }
         }

         return var2;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lw.av(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("qb")
   public Widget method7392(int var1) {
      this.field4249 = var1 * -1430539503;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lym;I)V")
   @ObfuscatedName("ay")
   void method7243(SpritePixels var1, int var2) {
      try {
         if (this.field4350) {
            if (var2 >= -563524676) {
               throw new IllegalStateException();
            }

            var1.method13542();
         }

         if (this.field4254) {
            if (var2 >= -563524676) {
               throw new IllegalStateException();
            }

            var1.method13535();
         }

         if (-2011859635 * this.field4281 > 0) {
            if (var2 >= -563524676) {
               throw new IllegalStateException();
            }

            var1.method13532(this.field4281 * -2011859635);
         }

         if (this.field4281 * -2011859635 >= 1) {
            if (var2 >= -563524676) {
               throw new IllegalStateException();
            }

            var1.method13543(1);
         }

         if (this.field4281 * -2011859635 >= 2) {
            if (var2 >= -563524676) {
               throw new IllegalStateException();
            }

            var1.method13543(16777215);
         }

         if (880555563 * this.field4282 != 0) {
            if (var2 >= -563524676) {
               throw new IllegalStateException();
            }

            SpritePixels.method13548(var1, this.field4282 * 880555563);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;)Lzv;")
   @ObfuscatedName("cj")
   public classZV method7245(classLY var1) {
      if (this.field4243 * -1873556420 == -1) {
         return null;
      } else {
         classZV var2 = (classZV)NodeCache.method6460(var1.field4427, -1803847435 * this.field4243);
         if (null != var2) {
            return var2;
         } else {
            IndexDataBase var4 = var1.field4435;
            IndexDataBase var5 = var1.field4440;
            int var6 = this.field4243 * 818095370;
            classZV var3;
            if (!classHK.method6100(var4, var6, 0, (byte)-7)) {
               var3 = null;
            } else {
               byte[] var8 = var5.method11942(var6, 0, -2009463730);
               classZV var7;
               if (null == var8) {
                  var7 = null;
               } else {
                  classZV var9 = new classZV(
                     var8, classYL.field7000, classYL.field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, classYL.field7002
                  );
                  classVZ.method12347((byte)-25);
                  var7 = var9;
               }

               var3 = var7;
            }

            if (null != var3) {
               var1.field4427.method6464(var3, -1535916298 * this.field4243);
            }

            return var3;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("da")
   public boolean method7295(int var1, int var2, int var3, int var4) {
      if (11 == -1510882375 * this.field4373 && this.field4364 != null && this.method7296(1355775728)) {
         var1 -= var3;
         var2 -= var4;
         int var5 = (int)(this.field4364.method7512(325924250)[0] * (this.field4247 * 1028880974));
         int var6 = (int)(this.field4364.method7512(-1250896511)[1] * (this.field4257 * 293773697));
         int var7 = var5 + (int)(this.field4364.method7512(-593915569)[2] * (340233946 * this.field4247));
         int var8 = var6 + (int)(this.field4364.method7512(988786477)[3] * (this.field4257 * 293773697));
         return var1 >= var5 && var2 >= var6 && var1 < var7 && var2 < var8;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("pm")
   public Widget method7393(int var1) {
      this.field4331 = var1 * 656328391;
      return this;
   }

   @ObfuscatedSignature(descriptor = "()Llx;")
   @ObfuscatedName("fj")
   public classLX method7344() {
      return this.field4367;
   }

   @ObfuscatedSignature(descriptor = "(Lly;Lou;IZLlc;Lpl;Lpp;)Lfx;")
   @ObfuscatedName("cx")
   public Model method7249(classLY var1, Animation var2, int var3, boolean var4, PlayerComposition var5, NPCComposition var6, NpcOverrides var7) {
      int var8;
      int var9;
      if (var4) {
         var8 = -557671144 * this.field4285;
         var9 = 697152629 * this.field4352;
      } else {
         var8 = this.field4331 * -620665047;
         var9 = this.field4372 * 1325600190;
      }

      if (var8 == 0) {
         return null;
      } else if (1 == var8 && -1 == var9) {
         return null;
      } else {
         if (var7 != null && var7.field5375 && 6 == var8) {
            var8 = 3;
         }

         long var10 = (var8 << 16) + var9;
         if (2 == var8 || var8 == 6) {
            if (var6 == null) {
               return null;
            }

            var10 = (var8 << 16) + -1428076529 * var6.field5300;
         }

         if (null != var7) {
            var10 |= -4980301671096129853L * var7.field5376 << 20;
         }

         Model var12 = (Model)NodeCache.method6460(var1.field4429, var10);
         if (null == var12) {
            ModelData var13 = null;
            int var14 = 1000833940;
            int var15 = 768;
            switch (var8) {
               case 1:
                  var13 = ModelData.method4067(var1.field4432, var9, 0);
                  break;
               case 2:
                  var13 = var6.method8987(null, (byte)36);
                  break;
               case 3:
                  var13 = null != var5 ? PlayerComposition.method6746(var5, (byte)0) : null;
                  break;
               case 4:
                  ItemComposition var16 = classOB.method8329(var9, (byte)-53);
                  var13 = var16.method8688(10, 849174015);
                  var14 += 1845819663 * var16.field5058;
                  var15 += var16.field5028 * -1636300529;
               case 5:
               default:
                  break;
               case 6:
                  var13 = var6.method8987(var7, (byte)93);
            }

            if (null == var13) {
               return null;
            }

            var12 = var13.method4070(var14, var15, -50, 2097628435, -50);
            var1.field4429.method6464(var12, var10);
         }

         if (null != var2) {
            var12 = var2.method8801(var12, var3, -2090339941);
         }

         return var12;
      }
   }

   public int getWidthMode() {
      return this.field4249 * -1966367247;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Llw;")
   @ObfuscatedName("nd")
   public Widget method7394(String var1) {
      this.field4299 = var1;
      return this;
   }

   public int getRotationY() {
      return this.field4292 * 827587461;
   }

   @ObfuscatedSignature(descriptor = "(Lly;Z)Lkh;")
   @ObfuscatedName("ce")
   public classKH method7251(classLY var1, boolean var2) {
      if (-1 == this.field4347 * -669652445) {
         var2 = false;
      }

      int var3 = var2 ? -669652445 * this.field4347 : 208578545 * this.field4346;
      if (var3 == -1) {
         return null;
      } else {
         long var4 = ((long)(this.field4282 * 880555563) << 40)
            + var3
            + ((long)(-2011859635 * this.field4281) << 36)
            + ((this.field4350 ? 1L : 0L) << 38)
            + ((this.field4254 ? 1L : 0L) << 39);
         classKH var6 = (classKH)NodeCache.method6460(var1.field4430, var4);
         if (null != var6) {
            return var6;
         } else {
            SpritePixels var7 = this.method7369(var1, var2, null, -548149013);
            if (var7 == null) {
               return null;
            } else {
               SpritePixels var8 = var7.method13523();
               int[] var9 = new int[var8.field7007];
               int[] var10 = new int[var8.field7007];

               for (int var11 = 0; var11 < var8.field7007; var11++) {
                  int var12 = 0;
                  int var13 = var8.field7005;

                  for (int var14 = 0; var14 < var8.field7005; var14++) {
                     if (var8.field7003[var11 * var8.field7005 + var14] == 0) {
                        var12 = var14;
                        break;
                     }
                  }

                  for (int var16 = var8.field7005 - 1; var16 >= var12; var16--) {
                     if (var8.field7003[var16 + var8.field7005 * var11] == 0) {
                        var13 = 1 + var16;
                        break;
                     }
                  }

                  var9[var11] = var12;
                  var10[var11] = var13 - var12;
               }

               var6 = new classKH(var8.field7005, var8.field7007, var10, var9, var3);
               var1.field4430.method6464(var6, var4);
               return var6;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fp")
   public void method7395(boolean var1) {
      this.field4273 = var1;
   }

   public Point getCanvasLocation() {
      return new Point(this.field4239, this.field4240);
   }

   @ObfuscatedSignature(descriptor = "(Lly;Z)Lkh;")
   @ObfuscatedName("cm")
   public classKH method7252(classLY var1, boolean var2) {
      if (-1 == this.field4347 * 1775105243) {
         var2 = false;
      }

      int var3 = var2 ? -579466528 * this.field4347 : 208578545 * this.field4346;
      if (var3 == -1) {
         return null;
      } else {
         long var4 = ((long)(this.field4282 * 880555563) << 40)
            + var3
            + ((long)(2137755444 * this.field4281) << 36)
            + ((this.field4350 ? 1L : 0L) << -1992025586)
            + ((this.field4254 ? 1L : 0L) << 39);
         classKH var6 = (classKH)NodeCache.method6460(var1.field4430, var4);
         if (null != var6) {
            return var6;
         } else {
            SpritePixels var7 = this.method7369(var1, var2, null, 1393465226);
            if (var7 == null) {
               return null;
            } else {
               SpritePixels var8 = var7.method13523();
               int[] var9 = new int[var8.field7007];
               int[] var10 = new int[var8.field7007];

               for (int var11 = 0; var11 < var8.field7007; var11++) {
                  int var12 = 0;
                  int var13 = var8.field7005;

                  for (int var14 = 0; var14 < var8.field7005; var14++) {
                     if (var8.field7003[var11 * var8.field7005 + var14] == 0) {
                        var12 = var14;
                        break;
                     }
                  }

                  for (int var16 = var8.field7005 - 1; var16 >= var12; var16--) {
                     if (var8.field7003[var16 + var8.field7005 * var11] == 0) {
                        var13 = 1 + var16;
                        break;
                     }
                  }

                  var9[var11] = var12;
                  var10[var11] = var13 - var12;
               }

               var6 = new classKH(var8.field7005, var8.field7007, var10, var9, var3);
               var1.field4430.method6464(var6, var4);
               return var6;
            }
         }
      }
   }

   public net.runelite.api.widgets.Widget[] getStaticChildren() {
      if (this.field4258 * 63131743 > -1) {
         return new net.runelite.api.widgets.Widget[0];
      } else {
         ArrayList var1 = new ArrayList();

         for (Widget var5 : classOE.field4841.method2265(WidgetUtil.componentToInterface(this.field4327 * -1163656551))) {
            if (var5 != null && var5.field4340 * -1502647899 == this.field4327 * -1163656551) {
               var1.add(var5);
            }
         }

         return var1.toArray(new Widget[0]);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;Z)Lkh;")
   @ObfuscatedName("dc")
   public classKH method7253(classLY var1, boolean var2) {
      if (-1 == this.field4347 * -669652445) {
         var2 = false;
      }

      int var3 = var2 ? -669652445 * this.field4347 : 208578545 * this.field4346;
      if (var3 == -1) {
         return null;
      } else {
         long var4 = ((long)(this.field4282 * 880555563) << 40)
            + var3
            + ((long)(-2011859635 * this.field4281) << 36)
            + ((this.field4350 ? 1L : 0L) << 38)
            + ((this.field4254 ? 1L : 0L) << 39);
         classKH var6 = (classKH)NodeCache.method6460(var1.field4430, var4);
         if (null != var6) {
            return var6;
         } else {
            SpritePixels var7 = this.method7369(var1, var2, null, 2118704501);
            if (var7 == null) {
               return null;
            } else {
               SpritePixels var8 = var7.method13523();
               int[] var9 = new int[var8.field7007];
               int[] var10 = new int[var8.field7007];

               for (int var11 = 0; var11 < var8.field7007; var11++) {
                  int var12 = 0;
                  int var13 = var8.field7005;

                  for (int var14 = 0; var14 < var8.field7005; var14++) {
                     if (var8.field7003[var11 * var8.field7005 + var14] == 0) {
                        var12 = var14;
                        break;
                     }
                  }

                  for (int var16 = var8.field7005 - 1; var16 >= var12; var16--) {
                     if (var8.field7003[var16 + var8.field7005 * var11] == 0) {
                        var13 = 1 + var16;
                        break;
                     }
                  }

                  var9[var11] = var12;
                  var10[var11] = var13 - var12;
               }

               var6 = new classKH(var8.field7005, var8.field7007, var10, var9, var3);
               var1.field4430.method6464(var6, var4);
               return var6;
            }
         }
      }
   }

   public void setOnVarTransmitListener(Object[] var1) {
      this.field4380 = var1;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)V")
   @ObfuscatedName("dd")
   public void method7256(int var1, String var2) {
      if (this.field4335 == null || this.field4335.length <= var1) {
         String[] var3 = new String[1 + var1];
         if (this.field4335 != null) {
            for (int var4 = 0; var4 < this.field4335.length; var4++) {
               var3[var4] = this.field4335[var4];
            }
         }

         this.field4335 = var3;
      }

      this.field4335[var1] = var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("bg")
   public String method7300(String var1, int var2) {
      try {
         if (11 == this.field4373 * -1510882375) {
            if (var2 <= -1798356091) {
               throw new IllegalStateException();
            }

            if (this.field4364 != null) {
               if (var2 <= -1798356091) {
                  throw new IllegalStateException();
               }

               if (this.method7296(1355775728)) {
                  return this.field4364.method7493(var1, (byte)46);
               }

               if (var2 <= -1798356091) {
                  throw new IllegalStateException();
               }
            }
         }

         return null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;)Lzv;")
   @ObfuscatedName("ci")
   public classZV method7246(classLY var1) {
      if (this.field4243 * -1803847435 == -1) {
         return null;
      } else {
         classZV var2 = (classZV)NodeCache.method6460(var1.field4427, -1803847435 * this.field4243);
         if (null != var2) {
            return var2;
         } else {
            IndexDataBase var4 = var1.field4435;
            IndexDataBase var5 = var1.field4440;
            int var6 = this.field4243 * -1803847435;
            classZV var3;
            if (!classHK.method6100(var4, var6, 0, (byte)-100)) {
               var3 = null;
            } else {
               byte[] var8 = var5.method11942(var6, 0, -1425096123);
               classZV var7;
               if (null == var8) {
                  var7 = null;
               } else {
                  classZV var9 = new classZV(
                     var8, classYL.field7000, classYL.field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, classYL.field7002
                  );
                  classVZ.method12347((byte)70);
                  var7 = var9;
               }

               var3 = var7;
            }

            if (null != var3) {
               var1.field4427.method6464(var3, -1803847435 * this.field4243);
            }

            return var3;
         }
      }
   }

   public int getDragDeadTime() {
      return this.field4311 * 135384009;
   }

   public int getModelZoom() {
      return this.field4336 * 245118287;
   }

   @ObfuscatedSignature(descriptor = "(Llw;Lxy;I)V")
   @ObfuscatedName("em")
   public static void method7219(Widget var0, classXY var1, int var2) {
      if (var0 == null) {
         var0.method7220(var1, var2);
      } else {
         try {
            var1.method13095(-346779531);
            var0.field4273 = true;
            var0.field4373 = var1.method13095(-346779531) * 685068937;
            var0.field4332 = var1.method13099(-241947726) * -411168601;
            var0.field4246 = var1.method13104((byte)16) * -1076753689;
            var0.field4283 = var1.method13104((byte)16) * 1989424741;
            var0.field4253 = var1.method13099(106831180) * 1034606045;
            if (9 == var0.field4373 * -1510882375) {
               var0.field4333 = var1.method13104((byte)16) * -259501473;
            } else {
               var0.field4333 = var1.method13099(998811198) * -259501473;
            }

            var0.field4249 = var1.method13097((byte)17) * -1430539503;
            var0.field4250 = var1.method13097((byte)17) * -565540095;
            var0.field4262 = var1.method13097((byte)17) * 1632281619;
            var0.field4248 = var1.method13097((byte)17) * 454876301;
            var0.field4340 = var1.method13099(-1842555802) * -676768211;
            if (-1502647899 * var0.field4340 == 65535) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var0.field4340 = 676768211;
            } else {
               var0.field4340 = -676768211 * (-1502647899 * var0.field4340 + (-1163656551 * var0.field4327 & -65536));
            }

            boolean var10001;
            if (var1.method13095(-346779531) == 1) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            var0.field4287 = var10001;
            if (0 == -1510882375 * var0.field4373) {
               var0.field4256 = var1.method13099(-126044868) * -803344527;
               var0.field4267 = var1.method13099(1051631118) * -251301447;
               if (var1.method13095(-346779531) == 1) {
                  if (var2 != 1218236279) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var0.field4401 = var10001;
            }

            if (5 == var0.field4373 * -1510882375) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var0.field4346 = classXY.method13113(var1, (byte)1) * 857041169;
               var0.field4349 = var1.method13099(-359716027) * -727683123;
               if (var1.method13095(-346779531) == 1) {
                  if (var2 != 1218236279) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var0.field4280 = var10001;
               var0.field4345 = var1.method13095(-346779531) * -2056841747;
               var0.field4281 = var1.method13095(-346779531) * -1993421947;
               var0.field4282 = classXY.method13113(var1, (byte)1) * 37017731;
               if (var1.method13095(-346779531) == 1) {
                  if (var2 != 1218236279) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var0.field4350 = var10001;
               if (var1.method13095(-346779531) == 1) {
                  if (var2 != 1218236279) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var0.field4254 = var10001;
            }

            if (6 == var0.field4373 * -1510882375) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var0.field4331 = 656328391;
               var0.field4372 = var1.method13173(classXM.field6886.field6888, 2125942098) * 434741949;
               var0.field4288 = var1.method13104((byte)16) * 1981476425;
               var0.field4289 = var1.method13104((byte)16) * 1954863581;
               var0.field4354 = var1.method13099(1370270829) * 1703663435;
               var0.field4290 = var1.method13099(994802512) * 993003239;
               var0.field4292 = var1.method13099(450674507) * 863028557;
               var0.field4336 = var1.method13099(-1508902731) * 1556726191;
               var0.field4286 = var1.method13099(-1221385387) * 742198477;
               if (65535 == var0.field4286 * 2114568197) {
                  if (var2 != 1218236279) {
                     throw new IllegalStateException();
                  }

                  var0.field4286 = -742198477;
               }

               if (var1.method13095(-346779531) == 1) {
                  if (var2 != 1218236279) {
                     return;
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var0.field4295 = var10001;
               var1.method13099(-450046437);
               if (-1966367247 * var0.field4249 != 0) {
                  var0.field4294 = var1.method13099(-520213711) * 1299951355;
               }

               if (var0.field4250 * 372143361 != 0) {
                  var1.method13099(-1192075905);
               }
            }

            if (4 == -1510882375 * var0.field4373) {
               if (var2 != 1218236279) {
                  return;
               }

               var0.field4243 = var1.method13099(-1135544449) * 590574429;
               if (var0.field4243 * -1803847435 == 65535) {
                  if (var2 != 1218236279) {
                     throw new IllegalStateException();
                  }

                  var0.field4243 = -590574429;
               }

               var0.field4299 = var1.method13127(-945185794);
               var0.field4301 = var1.method13095(-346779531) * -763080175;
               var0.field4302 = var1.method13095(-346779531) * 571804797;
               var0.field4303 = var1.method13095(-346779531) * -2106140265;
               if (var1.method13095(-346779531) == 1) {
                  if (var2 != 1218236279) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var0.field4304 = var10001;
               var0.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
            }

            if (-1510882375 * var0.field4373 == 3) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var0.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
               var0.field4269 = var1.method13095(-346779531) == 1;
               var0.field4345 = var1.method13095(-346779531) * -2056841747;
            }

            if (9 == var0.field4373 * -1510882375) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var0.field4275 = var1.method13095(-346779531) * 327091521;
               var0.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
               if (var1.method13095(-346779531) == 1) {
                  if (var2 != 1218236279) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var0.field4276 = var10001;
            }

            var0.field4318 = var1.method13105(-758448158) * 997939289;
            var0.field4307 = var1.method13127(-205058935);
            int var3 = var1.method13095(-346779531);
            if (var3 > 0) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var0.field4335 = new String[var3];

               for (int var4 = 0; var4 < var3; var4++) {
                  if (var2 != 1218236279) {
                     return;
                  }

                  var0.field4335[var4] = var1.method13127(-520288158);
               }
            }

            var0.field4366 = var1.method13095(-346779531) * 230129403;
            var0.field4311 = var1.method13095(-346779531) * -2074329479;
            if (var1.method13095(-346779531) == 1) {
               if (var2 != 1218236279) {
                  return;
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            var0.field4312 = var10001;
            var0.field4343 = var1.method13127(-140187877);
            var0.field4374 = var0.method7226(var1, 1062416733);
            var0.field4400 = var0.method7226(var1, 2099116228);
            var0.field4359 = var0.method7226(var1, 1842509635);
            var0.field4348 = var0.method7226(var1, 1263183737);
            var0.field4379 = var0.method7226(var1, 2071129357);
            var0.field4380 = var0.method7226(var1, 1394211475);
            var0.field4353 = var0.method7226(var1, 938205581);
            var0.field4382 = var0.method7226(var1, 1050161569);
            var0.field4341 = var0.method7226(var1, 540048844);
            var0.field4384 = var0.method7226(var1, 1635652811);
            var0.field4284 = var0.method7226(var1, 1062349515);
            var0.field4357 = var0.method7226(var1, 766182010);
            var0.field4375 = var0.method7226(var1, 1391640694);
            var0.field4355 = var0.method7226(var1, 1455687462);
            var0.field4376 = var0.method7226(var1, 1358422772);
            var0.field4377 = var0.method7226(var1, 2019082877);
            var0.field4378 = var0.method7226(var1, 1756166187);
            var0.field4385 = var0.method7226(var1, 1010164295);
            var0.field4278 = method7229(var0, var1, -1089851676);
            var0.field4265 = method7229(var0, var1, 1812795025);
            var0.field4383 = method7229(var0, var1, 209304390);
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "lw.ag(" + ')');
         }
      }
   }

   public int getModelType() {
      return this.field4331 * -910601;
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)Z")
   @ObfuscatedName("ej")
   public static boolean method7263(Widget var0, int var1) {
      if (var0 == null) {
         return var0.method7298(var1);
      } else {
         try {
            if (11 != var0.field4373 * -1510882375) {
               if (var0.field4373 * -1510882375 != 12) {
                  return false;
               }

               if (var1 <= 403819328) {
                  throw new IllegalStateException();
               }
            }

            return true;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "lw.aw(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("di")
   public boolean method7261() {
      return !this.field4273
         || 0 == this.field4373 * -1844190441
         || 11 == this.field4373 * -1510882375
         || this.field4313
         || -90794217 * this.field4332 == 1338
         || -1510882375 * this.field4373 == 12;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dt")
   public boolean method7264() {
      return 11 == this.field4373 * -1510882375 || this.field4373 * -1510882375 == 12;
   }

   public int getDragDeadZone() {
      return this.field4366 * -782836685;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("uj")
   public Widget method7396(int var1) {
      this.field4329 = var1 * -1148676629;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lly;I)V")
   @ObfuscatedName("bb")
   void method7328(classLY var1, int var2) {
      try {
         this.field4401 = true;
         ArrayList var3 = this.field4364.method7496((short)-7103);
         ArrayList var4 = classMA.method7501(this.field4364, 1107323496);
         int var5 = var3.size() + var4.size();
         this.field4338 = new Widget[var5];
         int var6 = 0;

         for (classMN var8 : var3) {
            Widget var9 = classMN.method7664(5, this, var6, 0, 0, 0, 0, var8.field4539, (byte)-7);
            var9.field4337 = var8.field4542.method4148((byte)6);
            classLN var10 = new classLN(var8.field4542);
            var1.field4436.method10022(var9.field4337, var10, (byte)1);
            this.field4338[var6++] = var9;
         }

         Iterator var12 = var4.iterator();

         while (var12.hasNext()) {
            if (var2 == -1556285699) {
               throw new IllegalStateException();
            }

            classML var13 = (classML)var12.next();
            Widget var14 = classMN.method7664(4, this, var6, 0, 0, 0, 0, var13.field4523, (byte)-25);
            var14.field4299 = var13.field4530;
            var14.field4243 = (Integer)this.field4365.get(463835541 * var13.field4522) * 590574429;
            var14.field4302 = 329236247 * var13.field4521;
            var14.field4303 = var13.field4524 * 616376749;
            this.field4338[var6++] = var14;
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "lw.bb(" + ')');
      }
   }

   public net.runelite.api.widgets.Widget getParent() {
      int var1 = this.getParentId();
      return var1 == -1 ? null : classOE.field4841.method2281(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("bk")
   public static boolean method7240(Widget var0) {
      return var0 == null ? var0.method7241() : var0.field4350 || var0.field4254 || 0 != -2011859635 * var0.field4281 || -1872633104 * var0.field4282 != 0;
   }

   public Object[] getOnVarTransmitListener() {
      return this.field4380;
   }

   public int getOriginalWidth() {
      return this.field4253 * 1523460725;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("ys")
   public Widget method7397(int var1) {
      this.field4346 = var1 * 857041169;
      return this;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dk")
   public boolean method7265() {
      return 11 == this.field4373 * -1510882375 || this.field4373 * -1510882375 == 12;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;)Lma;")
   @ObfuscatedName("os")
   public static classMA method7321(Widget var0) {
      return var0.field4364;
   }

   @ObfuscatedSignature(descriptor = "()Laaa;")
   @ObfuscatedName("ck")
   public AbstractFont method7366() {
      return this.method7404(classWK.field6689);
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("po")
   public Widget method7398(int var1) {
      this.field4303 = var1 * -2106140265;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;)Ljava/lang/Object;")
   @ObfuscatedName("db")
   public Object method7268(int var1, Object var2) {
      for (int var3 = 0; var3 < -2048004067 * this.field4306; var3++) {
         if (var1 == this.field4403[var3]) {
            return this.field4334[var3];
         }
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;)Ljava/lang/Object;")
   @ObfuscatedName("dw")
   public Object method7269(int var1, Object var2) {
      for (int var3 = 0; var3 < -2048004067 * this.field4306; var3++) {
         if (var1 == this.field4403[var3]) {
            return this.field4334[var3];
         }
      }

      return var2;
   }

   public void setOnTargetEnterListener(Object[] var1) {
      this.field4379 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("bs")
   public Widget method7399(int var1) {
      this.field4342 = var1 * 911947905;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("de")
   public int method7272(int var1, int var2) {
      for (int var3 = 0; var3 < -2048004067 * this.field4306; var3++) {
         if (var1 == this.field4403[var3]) {
            return (Integer)this.field4334[var3];
         }
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(II)Llw;")
   @ObfuscatedName("af")
   public Widget method7359(int var1, int var2) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else if (this.getType() != 0) {
         throw new UnsupportedOperationException("children can only be created on type layer widgets");
      } else {
         Widget var3 = new Widget();
         var3.setType(var2);
         int var10001 = this.field4327 * -1163656551;
         var3.field4327 = this.field4327 * -1163656551 * 519254441;
         var3.field4340 = var10001 * -676768211;
         var3.field4261 = this.field4258 * 63131743 * 1000754647;
         var3.method7395(true);
         Widget var4 = classOE.field4841.method2281(this.field4327 * -1163656551);
         if (var4 == null) {
            throw new IllegalStateException("createChild called on unloaded component");
         } else {
            assert var4 == this || this.field4258 * 63131743 != -1;

            Widget[] var5 = var4.field4338;
            if (var1 < 0) {
               if (var5 == null) {
                  var1 = 0;
               } else {
                  var1 = 0;

                  for (int var6 = var5.length - 1; var6 >= 0; var6--) {
                     if (var5[var6] != null) {
                        var1 = var6 + 1;
                        break;
                     }
                  }
               }
            }

            if (var5 == null) {
               var5 = new Widget[var1 + 1];
               var4.field4338 = var5;
            } else if (var5.length <= var1) {
               Widget[] var7 = new Widget[var1 + 1];
               System.arraycopy(var5, 0, var7, 0, var5.length);
               var5 = var7;
               var4.field4338 = var7;
            }

            var5[var1] = var3;
            var3.field4258 = var1 * -1612510305;
            if (this.field4258 * 63131743 > -1) {
               assert this.getType() == 0;

               if (var1 > this.field4264 * 379541057) {
                  this.field4264 = var1 * -1479820863;
               }

               if (var1 < this.field4263 * -1210177335) {
                  this.field4263 = var1 * -452038279;
               }
            }

            return var3;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lye;")
   @ObfuscatedName("jl")
   public classYE method7400() {
      return this.field4305;
   }

   public int getOriginalY() {
      return this.field4283 * -265516179;
   }

   @ObfuscatedSignature(descriptor = "(Z)Llw;")
   @ObfuscatedName("xr")
   public Widget method7401(boolean var1) {
      this.field4313 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)[I")
   @ObfuscatedName("ck")
   int[] method7231(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (0 == var2) {
         return null;
      } else {
         int[] var3 = new int[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            var3[var4] = classXY.method13113(var1, (byte)1);
         }

         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;Z)Lkh;")
   @ObfuscatedName("js")
   public classKH method7402(classLY var1, boolean var2) {
      return this.method7254(var1, var2, 555383795);
   }

   public Object[] getOnKeyListener() {
      return this.field4386;
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("dn")
   public long method7275(int var1, long var2) {
      for (int var4 = 0; var4 < this.field4306 * -2048004067; var4++) {
         if (var1 == this.field4403[var4]) {
            return (Long)this.field4334[var4];
         }
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("du")
   public Widget method7403(int var1) {
      this.field4345 = var1 * -2056841747;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lly;ZI)Lkh;")
   @ObfuscatedName("ap")
   public classKH method7254(classLY var1, boolean var2, int var3) {
      try {
         if (-1 == this.field4347 * -669652445) {
            if (var3 == 1669356044) {
               throw new IllegalStateException();
            }

            var2 = false;
         }

         int var10000;
         if (var2) {
            if (var3 == 1669356044) {
               throw new IllegalStateException();
            }

            var10000 = -669652445 * this.field4347;
         } else {
            var10000 = 208578545 * this.field4346;
         }

         int var4 = var10000;
         if (var4 == -1) {
            return null;
         } else {
            long var19 = (long)(this.field4282 * 880555563) << 40;
            long var10001 = var4 + ((long)(-2011859635 * this.field4281) << 36);
            long var10002;
            if (this.field4350) {
               if (var3 == 1669356044) {
                  throw new IllegalStateException();
               }

               var10002 = 1L;
            } else {
               var10002 = 0L;
            }

            var10001 += var10002 << 38;
            if (this.field4254) {
               if (var3 == 1669356044) {
                  throw new IllegalStateException();
               }

               var10002 = 1L;
            } else {
               var10002 = 0L;
            }

            long var5 = var19 + var10001 + (var10002 << 39);
            classKH var7 = (classKH)NodeCache.method6460(var1.field4430, var5);
            if (null != var7) {
               if (var3 == 1669356044) {
                  throw new IllegalStateException();
               } else {
                  return var7;
               }
            } else {
               SpritePixels var8 = this.method7369(var1, var2, null, 1817675597);
               if (var8 == null) {
                  return null;
               } else {
                  SpritePixels var9 = var8.method13523();
                  int[] var10 = new int[var9.field7007];
                  int[] var11 = new int[var9.field7007];

                  for (int var12 = 0; var12 < var9.field7007; var12++) {
                     if (var3 == 1669356044) {
                        throw new IllegalStateException();
                     }

                     int var13 = 0;
                     int var14 = var9.field7005;

                     for (int var15 = 0; var15 < var9.field7005; var15++) {
                        if (var3 == 1669356044) {
                           throw new IllegalStateException();
                        }

                        if (var9.field7003[var12 * var9.field7005 + var15] == 0) {
                           if (var3 == 1669356044) {
                              throw new IllegalStateException();
                           }

                           var13 = var15;
                           break;
                        }
                     }

                     for (int var18 = var9.field7005 - 1; var18 >= var13; var18--) {
                        if (var3 == 1669356044) {
                           throw new IllegalStateException();
                        }

                        if (var9.field7003[var18 + var9.field7005 * var12] == 0) {
                           if (var3 == 1669356044) {
                              throw new IllegalStateException();
                           }

                           var14 = 1 + var18;
                           break;
                        }
                     }

                     var10[var12] = var13;
                     var11[var12] = var14 - var13;
                  }

                  var7 = new classKH(var9.field7005, var9.field7007, var11, var10, var4);
                  var1.field4430.method6464(var7, var5);
                  return var7;
               }
            }
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "lw.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lda;)V")
   @ObfuscatedName("do")
   public void method7281(String var1, String var2, String var3, String var4, String var5, classDA var6) {
      this.field4364 = new classMA();
      if (!this.field4364.method7474(var1, var3, var6, 571906965)) {
         this.field4364 = null;
      } else {
         if (null == this.field4365 || null == this.field4266) {
            this.method7286(19588919);
         }

         this.field4364.method7476(var2, var4, var5, (short)-31519);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lda;I)V")
   @ObfuscatedName("bn")
   public void method7283(String var1, classDA var2, int var3) {
      try {
         if (11 == this.field4373 * -1510882375 && var1 != null) {
            this.field4364 = new classMA();
            this.field4364.method7537(var1, var2, 2081829054);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lw.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lda;)V")
   @ObfuscatedName("dl")
   public void method7284(String var1, classDA var2) {
      if (11 == this.field4373 * -8267802 && var1 != null) {
         this.field4364 = new classMA();
         this.field4364.method7537(var1, var2, 1719852866);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dq")
   void method7287() {
      this.field4365 = new HashMap();
      this.field4266 = new HashMap();
   }

   public void setSubOp(int var1, int var2, String var3) {
      this.method7257(var1, var2, var3, -512976453);
   }

   public int getYPositionMode() {
      return this.field4248 * 923944517;
   }

   public void setOnDragListener(Object[] var1) {
      this.field4377 = var1;
   }

   public String[][] getSubOps() {
      return this.field4351;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dp")
   public boolean method7297() {
      return this.field4328 * 2133229739 == 2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;Lly;Lda;)Lym;")
   @ObfuscatedName("bf")
   public static SpritePixels method7238(Widget var0, classLY var1, classDA var2) {
      if (var0 == null) {
         var0.getItemId();
      }

      if (var0.field4337 != null && null != var2) {
         classLN var3 = (classLN)var1.field4436.method10020(var0.field4337, (byte)-3);
         if (null == var3) {
            var3 = new classLN(var0.field4337, var2);
            var1.field4436.method10022(var0.field4337, var3, (byte)1);
         }

         return classLN.method7167(var3, 1627105142);
      } else {
         return null;
      }
   }

   public void setOnScrollWheelListener(Object[] var1) {
      this.field4385 = var1;
   }

   public void setVarTransmitTrigger(int[] var1) {
      this.field4278 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Ltt;")
   @ObfuscatedName("ec")
   public classTT method7339() {
      return this.field4367 != null ? this.field4367.field4418 : null;
   }

   public int getScrollHeight() {
      return this.field4267 * -714521463;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)[Ljava/lang/Object;")
   @ObfuscatedName("ax")
   Object[] method7226(classXY var1, int var2) {
      try {
         int var3 = var1.method13095(-346779531);
         if (0 == var3) {
            if (var2 <= 227024831) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            Object[] var4 = new Object[var3];

            for (int var5 = 0; var5 < var3; var5++) {
               if (var2 <= 227024831) {
                  throw new IllegalStateException();
               }

               int var6 = var1.method13095(-346779531);
               if (var6 == 0) {
                  if (var2 <= 227024831) {
                     throw new IllegalStateException();
                  }

                  var4[var5] = new Integer(classXY.method13113(var1, (byte)1));
               } else if (1 == var6) {
                  if (var2 <= 227024831) {
                     throw new IllegalStateException();
                  }

                  var4[var5] = var1.method13127(-871315181);
               }
            }

            this.field4313 = true;
            return var4;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lw.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("ey")
   public String method7301(String var1) {
      return 11 == this.field4373 * -1510882375 && this.field4364 != null && this.method7296(1355775728) ? this.field4364.method7493(var1, (byte)27) : null;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("dz")
   public int method7273(int var1, int var2) {
      for (int var3 = 0; var3 < -2048004067 * this.field4306; var3++) {
         if (var1 == this.field4403[var3]) {
            return (Integer)this.field4334[var3];
         }
      }

      return var2;
   }

   public void setOnTimerListener(Object[] var1) {
      this.field4341 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)I")
   @ObfuscatedName("zt")
   public static int method7305(Widget var0, int var1) {
      if (var0 == null) {
         return var0.method7308(var1);
      } else {
         try {
            if (null != var0.field4266) {
               if (var1 == -1415490927) {
                  throw new IllegalStateException();
               }

               if (!var0.field4266.isEmpty()) {
                  return 1;
               }
            }

            return 0;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "lw.bj(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("ek")
   public String method7302(String var1) {
      return 11 == this.field4373 * -1510882375 && this.field4364 != null && this.method7296(1355775728) ? this.field4364.method7493(var1, (byte)101) : null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;)[I")
   @ObfuscatedName("lf")
   public static int[] method7317(Widget var0) {
      if (11 == -1510882375 * var0.field4373 && null != var0.field4364) {
         int[] var1 = new int[3];
         int var2 = 0;

         for (classMD var4 : var0.field4364.method7503((byte)20)) {
            if (!var4.method7578((byte)-40).equals("user_id")) {
               if (var4.vmethod525(-645725653) != 0) {
                  return null;
               }

               var1[var2++] = var4.vmethod523(-1250028197);
               if (var2 > 3) {
                  return null;
               }
            }
         }

         return var1;
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("es")
   public int method7306() {
      return null != this.field4266 && !this.field4266.isEmpty() ? 1 : 0;
   }

   public void setOnMouseLeaveListener(Object[] var1) {
      this.field4359 = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ej")
   public int method7307() {
      return null != this.field4266 && !this.field4266.isEmpty() ? 1 : 0;
   }

   public void setOnDragCompleteListener(Object[] var1) {
      this.field4378 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Ltt;")
   @ObfuscatedName("eb")
   public classTT method7340() {
      return this.field4367 != null ? this.field4367.field4418 : null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;Ljava/lang/String;I)V")
   @ObfuscatedName("rd")
   public static void method7291(Widget var0, String var1, int var2) {
      if (var0.field4373 * -1510882375 == 11) {
         if (null == var0.field4266) {
            var0.method7286(908132681);
         }

         var0.field4266.put(var1, var2);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("cp")
   void method7216(classXY var1) {
      this.field4273 = false;
      this.field4373 = var1.method13095(-346779531) * 685068937;
      this.field4245 = var1.method13095(-346779531) * -811438059;
      this.field4332 = var1.method13099(-1011630446) * -411168601;
      this.field4246 = var1.method13104((byte)16) * -1076753689;
      this.field4283 = var1.method13104((byte)16) * 1989424741;
      this.field4253 = var1.method13099(-545755246) * 1034606045;
      this.field4333 = var1.method13099(1061592634) * -259501473;
      this.field4345 = var1.method13095(-346779531) * -2056841747;
      this.field4340 = var1.method13099(-1379916457) * -676768211;
      if (65535 == this.field4340 * -1502647899) {
         this.field4340 = 676768211;
      } else {
         this.field4340 = -676768211 * ((-1163656551 * this.field4327 & -65536) + -1502647899 * this.field4340);
      }

      this.field4314 = var1.method13099(1153615263) * -1709618187;
      if (-2003253155 * this.field4314 == 65535) {
         this.field4314 = 1709618187;
      }

      int var2 = var1.method13095(-346779531);
      if (var2 > 0) {
         this.field4398 = new int[var2];
         this.field4399 = new int[var2];

         for (int var3 = 0; var3 < var2; var3++) {
            this.field4398[var3] = var1.method13095(-346779531);
            this.field4399[var3] = var1.method13099(-1701590333);
         }
      }

      int var7 = var1.method13095(-346779531);
      if (var7 > 0) {
         this.field4397 = new int[var7][];

         for (int var4 = 0; var4 < var7; var4++) {
            int var5 = var1.method13099(1393564595);
            this.field4397[var4] = new int[var5];

            for (int var6 = 0; var6 < var5; var6++) {
               this.field4397[var4][var6] = var1.method13099(697094154);
               if (this.field4397[var4][var6] == 65535) {
                  this.field4397[var4][var6] = -1;
               }
            }
         }
      }

      if (0 == -1510882375 * this.field4373) {
         this.field4267 = var1.method13099(-623648271) * -251301447;
         this.field4287 = var1.method13095(-346779531) == 1;
      }

      if (-1510882375 * this.field4373 == 1) {
         var1.method13099(1932385003);
         var1.method13095(-346779531);
      }

      if (3 == -1510882375 * this.field4373) {
         this.field4269 = var1.method13095(-346779531) == 1;
      }

      if (4 == -1510882375 * this.field4373 || 1 == this.field4373 * -1510882375) {
         this.field4302 = var1.method13095(-346779531) * 571804797;
         this.field4303 = var1.method13095(-346779531) * -2106140265;
         this.field4301 = var1.method13095(-346779531) * -763080175;
         this.field4243 = var1.method13099(-798261243) * 590574429;
         if (65535 == this.field4243 * -1803847435) {
            this.field4243 = -590574429;
         }

         this.field4304 = var1.method13095(-346779531) == 1;
      }

      if (this.field4373 * -1510882375 == 4) {
         this.field4299 = var1.method13127(-873623348);
         this.field4300 = var1.method13127(1814550955);
      }

      if (1 == -1510882375 * this.field4373 || 3 == this.field4373 * -1510882375 || this.field4373 * -1510882375 == 4) {
         this.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
      }

      if (-1510882375 * this.field4373 == 3 || 4 == this.field4373 * -1510882375) {
         this.field4293 = classXY.method13113(var1, (byte)1) * -1914223631;
         this.field4270 = classXY.method13113(var1, (byte)1) * -1882190803;
         this.field4271 = classXY.method13113(var1, (byte)1) * -1240050015;
      }

      if (5 == this.field4373 * -1510882375) {
         this.field4346 = classXY.method13113(var1, (byte)1) * 857041169;
         this.field4347 = classXY.method13113(var1, (byte)1) * 1515656587;
      }

      if (-1510882375 * this.field4373 == 6) {
         this.field4331 = 656328391;
         this.field4285 = -1108433005;
         this.field4372 = var1.method13173(classXM.field6886.field6888, 1941535278) * 434741949;
         this.field4352 = var1.method13173(classXM.field6886.field6888, 2143341074) * 607878109;
         this.field4286 = var1.method13099(800533050) * 742198477;
         if (65535 == 2114568197 * this.field4286) {
            this.field4286 = -742198477;
         }

         this.field4291 = var1.method13099(-597253696) * 668997325;
         if (821151237 * this.field4291 == 65535) {
            this.field4291 = -668997325;
         }

         this.field4336 = var1.method13099(1048196937) * 1556726191;
         this.field4354 = var1.method13099(-1415541469) * 1703663435;
         this.field4290 = var1.method13099(-1907413821) * 993003239;
      }

      if (8 == -1510882375 * this.field4373) {
         this.field4299 = var1.method13127(1748993238);
      }

      if (this.field4245 * -851058883 == 2) {
         this.field4343 = var1.method13127(1731049422);
         this.field4315 = var1.method13127(2001274770);
         int var8 = var1.method13099(176156968) & 63;
         this.field4318 = (this.field4318 * 1595830249 | var8 << 11) * 997939289;
      }

      if (this.field4245 * -851058883 == 1 || 4 == this.field4245 * -851058883 || 5 == -851058883 * this.field4245 || 6 == this.field4245 * -851058883) {
         this.field4316 = var1.method13127(247557025);
         if (this.field4316.isEmpty()) {
            if (1 == this.field4245 * -851058883) {
               this.field4316 = classKK.field3793;
            }

            if (4 == -851058883 * this.field4245) {
               this.field4316 = classKK.field3610;
            }

            if (-851058883 * this.field4245 == 5) {
               this.field4316 = classKK.field3610;
            }

            if (-851058883 * this.field4245 == 6) {
               this.field4316 = classKK.field3775;
            }
         }
      }

      if (-851058883 * this.field4245 == 1 || 4 == -851058883 * this.field4245 || -851058883 * this.field4245 == 5) {
         this.field4318 = 997939289 * (1595830249 * this.field4318 | 4194304);
      }

      if (-851058883 * this.field4245 == 6) {
         this.field4318 = (this.field4318 * 1595830249 | 1) * 997939289;
      }
   }

   public void setFlippedHorizontally(boolean var1) {
      this.field4254 = var1;
   }

   public int getItemQuantity() {
      return this.field4342 * 1190987649;
   }

   public String getTargetVerb() {
      return this.field4343;
   }

   @ObfuscatedSignature(descriptor = "(Lly;)Laaa;")
   @ObfuscatedName("hh")
   public AbstractFont method7404(classLY var1) {
      return method7244(this, var1, (byte)20);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("el")
   public int method7311() {
      if (11 == this.field4373 * -1510882375 && null != this.field4364 && this.field4266 != null && !this.field4266.isEmpty()) {
         String var1 = this.field4364.method7506(-1393227033);
         return var1 != null && this.field4266.containsKey(this.field4364.method7506(-250534846)) ? (Integer)this.field4266.get(var1) : -1;
      } else {
         return -1;
      }
   }

   public void setBorderType(int var1) {
      this.field4281 = var1 * -1993421947;
   }

   public Widget(Widget var1) {
      this.field4241 = -1;
      this.field4242 = -1;
      this.field4273 = false;
      this.field4327 = -519254441;
      this.field4258 = 1612510305;
      this.field4245 = 0;
      this.field4332 = 0;
      this.field4262 = 0;
      this.field4248 = 0;
      this.field4249 = 0;
      this.field4250 = 0;
      this.field4246 = 0;
      this.field4283 = 0;
      this.field4253 = 0;
      this.field4333 = 0;
      this.field4279 = 0;
      this.method7357(-1);
      this.field4255 = 0;
      this.method7367(-1);
      this.field4247 = 0;
      this.field4257 = 0;
      this.field4272 = 62747509;
      this.field4259 = -1988594555;
      this.field4340 = 676768211;
      this.field4261 = -1000754647;
      this.field4287 = false;
      this.field4263 = -1939173753;
      this.field4264 = 0;
      this.field4339 = 0;
      this.field4329 = 0;
      this.field4256 = 0;
      this.field4267 = 0;
      this.field4268 = 0;
      this.field4293 = 0;
      this.field4270 = 0;
      this.field4271 = 0;
      this.field4269 = false;
      this.field4305 = classYE.field6970;
      this.field4345 = 0;
      this.field4274 = 0;
      this.field4275 = 327091521;
      this.field4276 = false;
      this.field4346 = -857041169;
      this.field4347 = -1515656587;
      this.field4349 = 0;
      this.field4280 = false;
      this.field4281 = 0;
      this.field4282 = 0;
      this.field4331 = 656328391;
      this.field4372 = -434741949;
      this.field4285 = -1108433005;
      this.field4352 = -607878109;
      this.field4286 = -742198477;
      this.field4291 = -668997325;
      this.field4288 = 0;
      this.field4289 = 0;
      this.field4354 = 0;
      this.field4290 = 0;
      this.field4292 = 0;
      this.field4336 = 1053796444;
      this.field4294 = 0;
      this.field4260 = 0;
      this.field4295 = false;
      this.field4296 = false;
      this.field4297 = -699666682;
      this.field4243 = -590574429;
      this.field4299 = "";
      this.field4300 = "";
      this.field4301 = 0;
      this.field4302 = 0;
      this.field4303 = 0;
      this.field4304 = false;
      this.field4318 = 0;
      this.field4358 = false;
      this.field4307 = "";
      this.field4308 = 695305012;
      this.field4309 = null;
      this.field4366 = 0;
      this.field4311 = 0;
      this.field4312 = false;
      this.field4343 = "";
      this.field4313 = false;
      this.field4314 = 1709618187;
      this.field4315 = "";
      this.field4316 = classKK.field3793;
      this.field4317 = -1321867761;
      this.field4342 = 0;
      this.field4319 = 0;
      this.field4325 = 0;
      this.field4320 = false;
      this.field4321 = false;
      this.field4322 = -1478436671;
      this.field4323 = 0;
      this.field4298 = 0;
      this.field4324 = 0;
      this.field4401 = false;
      this.field4326 = false;
      this.field4371 = false;
      this.field4328 = 1716422141;
      this.field4273 = var1.field4273;
      this.field4327 = 1 * var1.field4327;
      this.field4258 = 1 * var1.field4258;
      this.field4330 = var1.field4330;
      this.field4373 = 1 * var1.field4373;
      this.field4245 = var1.field4245 * 1;
      this.field4332 = 1 * var1.field4332;
      this.field4262 = var1.field4262 * 1;
      this.field4248 = var1.field4248 * 1;
      this.field4249 = 1 * var1.field4249;
      this.field4250 = var1.field4250 * 1;
      this.field4246 = var1.field4246 * 1;
      this.field4283 = 1 * var1.field4283;
      this.field4253 = 1 * var1.field4253;
      this.field4333 = var1.field4333 * 1;
      this.field4279 = var1.field4279 * 1;
      this.method7357(-1);
      this.field4255 = var1.field4255 * 1;
      this.method7367(-1);
      this.field4247 = var1.field4247 * 1;
      this.field4257 = 1 * var1.field4257;
      this.field4272 = 1 * var1.field4272;
      this.field4259 = 1 * var1.field4259;
      this.field4340 = var1.field4340 * 1;
      this.field4261 = 1 * var1.field4261;
      this.field4263 = var1.field4263 * 1;
      this.field4264 = var1.field4264 * 1;
      this.field4287 = var1.field4287;
      this.field4339 = 1 * var1.field4339;
      this.field4329 = var1.field4329 * 1;
      this.field4256 = 1 * var1.field4256;
      this.field4267 = 1 * var1.field4267;
      this.field4268 = 1 * var1.field4268;
      this.field4293 = 1 * var1.field4293;
      this.field4270 = var1.field4270 * 1;
      this.field4271 = var1.field4271 * 1;
      this.field4269 = var1.field4269;
      this.field4305 = var1.field4305;
      this.field4345 = 1 * var1.field4345;
      this.field4274 = var1.field4274 * 1;
      this.field4275 = 1 * var1.field4275;
      this.field4276 = var1.field4276;
      this.field4346 = var1.field4346 * 1;
      this.field4347 = 1 * var1.field4347;
      this.field4337 = var1.field4337;
      this.field4349 = 1 * var1.field4349;
      this.field4280 = var1.field4280;
      this.field4281 = 1 * var1.field4281;
      this.field4282 = 1 * var1.field4282;
      this.field4350 = var1.field4350;
      this.field4254 = var1.field4254;
      this.field4331 = 1 * var1.field4331;
      this.field4372 = 1 * var1.field4372;
      this.field4285 = 1 * var1.field4285;
      this.field4352 = var1.field4352 * 1;
      this.field4286 = var1.field4286 * 1;
      this.field4291 = 1 * var1.field4291;
      this.field4288 = 1 * var1.field4288;
      this.field4289 = var1.field4289 * 1;
      this.field4354 = 1 * var1.field4354;
      this.field4290 = var1.field4290 * 1;
      this.field4292 = var1.field4292 * 1;
      this.field4336 = var1.field4336 * 1;
      this.field4294 = var1.field4294 * 1;
      this.field4260 = var1.field4260 * 1;
      if (null != var1.field4356) {
         this.field4356 = new PlayerComposition(var1.field4356);
      }

      this.field4295 = var1.field4295;
      this.field4296 = var1.field4296;
      this.field4297 = var1.field4297 * 1;
      this.field4243 = 1 * var1.field4243;
      this.field4299 = var1.field4299;
      this.field4300 = var1.field4300;
      this.field4301 = var1.field4301 * 1;
      this.field4302 = 1 * var1.field4302;
      this.field4303 = 1 * var1.field4303;
      this.field4304 = var1.field4304;
      this.field4360 = method7211(this, var1.field4360, (byte)-100);
      this.field4251 = method7211(this, var1.field4251, (byte)50);
      this.field4277 = method7211(this, var1.field4277, (byte)34);
      this.field4362 = (String[])this.method7209(var1.field4362, -1524024607);
      if (var1.field4364 != null) {
         this.field4364 = new classMA(var1.field4364);
      }

      this.field4328 = var1.field4328 * 1;
      this.field4365 = var1.field4365;
      if (null != var1.field4266) {
         this.field4266 = new HashMap();
         this.field4266.putAll(var1.field4266);
      }

      this.field4367 = null;
      this.field4318 = 1 * var1.field4318;
      this.field4358 = var1.field4358;
      if (var1.field4310 != null) {
         this.field4310 = new byte[var1.field4310.length][];

         for (int var2 = 0; var2 < var1.field4310.length; var2++) {
            this.field4310[var2] = new byte[var1.field4310[var2].length];
            System.arraycopy(var1.field4310[var2], 0, this.field4310[var2], 0, var1.field4310[var2].length);
         }
      }

      if (var1.field4344 != null) {
         this.field4344 = new byte[var1.field4344.length][];

         for (int var4 = 0; var4 < var1.field4344.length; var4++) {
            this.field4344[var4] = new byte[var1.field4344[var4].length];
            System.arraycopy(var1.field4344[var4], 0, this.field4344[var4], 0, var1.field4344[var4].length);
         }
      }

      this.field4368 = method7211(this, var1.field4368, (byte)16);
      this.field4369 = method7211(this, var1.field4369, (byte)90);
      this.field4307 = var1.field4307;
      this.field4335 = (String[])this.method7209(var1.field4335, -1524024607);
      if (null != var1.field4351) {
         this.field4351 = new String[var1.field4351.length][];

         for (int var5 = 0; var5 < var1.field4351.length; var5++) {
            this.field4351[var5] = new String[var1.field4351[var5].length];
            System.arraycopy(var1.field4351[var5], 0, this.field4351[var5], 0, var1.field4351[var5].length);
         }
      }

      this.field4308 = var1.field4308 * 1;
      this.field4309 = var1.field4309;
      this.field4366 = var1.field4366 * 1;
      this.field4311 = var1.field4311 * 1;
      this.field4312 = var1.field4312;
      this.field4343 = var1.field4343;
      this.field4313 = var1.field4313;
      this.field4374 = var1.field4374;
      this.field4357 = var1.field4357;
      this.field4375 = var1.field4375;
      this.field4355 = var1.field4355;
      this.field4376 = var1.field4376;
      this.field4400 = var1.field4400;
      this.field4284 = var1.field4284;
      this.field4359 = var1.field4359;
      this.field4377 = var1.field4377;
      this.field4378 = var1.field4378;
      this.field4379 = var1.field4379;
      this.field4348 = var1.field4348;
      this.field4380 = var1.field4380;
      this.field4278 = var1.field4278;
      this.field4353 = var1.field4353;
      this.field4265 = var1.field4265;
      this.field4382 = var1.field4382;
      this.field4383 = var1.field4383;
      this.field4341 = var1.field4341;
      this.field4384 = var1.field4384;
      this.field4252 = var1.field4252;
      this.field4385 = var1.field4385;
      this.field4363 = var1.field4363;
      this.field4386 = var1.field4386;
      this.field4387 = var1.field4387;
      this.field4361 = var1.field4361;
      this.field4381 = var1.field4381;
      this.field4389 = var1.field4389;
      this.field4390 = var1.field4390;
      this.field4391 = var1.field4391;
      this.field4392 = var1.field4392;
      this.field4404 = var1.field4404;
      this.field4393 = var1.field4393;
      this.field4244 = var1.field4244;
      this.field4394 = var1.field4394;
      this.field4395 = var1.field4395;
      this.field4396 = var1.field4396;
      this.field4397 = var1.field4397;
      this.field4398 = method7211(this, var1.field4398, (byte)-93);
      this.field4399 = method7211(this, var1.field4399, (byte)3);
      this.field4314 = var1.field4314 * 1;
      this.field4315 = var1.field4315;
      this.field4316 = var1.field4316;
      this.field4388 = method7211(this, var1.field4388, (byte)53);
      this.field4370 = method7211(this, var1.field4370, (byte)-23);
      this.field4317 = 1 * var1.field4317;
      this.field4342 = var1.field4342 * 1;
      this.field4319 = 1 * var1.field4319;
      this.field4325 = 1 * var1.field4325;
      if (var1.field4338 != null) {
         this.field4338 = new Widget[var1.field4338.length];

         for (int var6 = 0; var6 < var1.field4338.length; var6++) {
            this.field4338[var6] = new Widget(var1.field4338[var6]);
         }
      }

      this.field4320 = var1.field4320;
      this.field4321 = var1.field4321;
      this.field4322 = var1.field4322 * 1;
      this.field4323 = 1 * var1.field4323;
      this.field4298 = var1.field4298 * 1;
      this.field4324 = 1 * var1.field4324;
      this.field4402 = method7211(this, var1.field4402, (byte)77);
      this.field4401 = var1.field4401;
      this.field4326 = var1.field4326;
      this.field4371 = var1.field4371;
      if (-2048004067 * var1.field4306 > 0) {
         this.field4403 = method7211(this, var1.field4403, (byte)24);
         this.field4334 = this.method7209(var1.field4334, -1524024607);
         this.field4306 = 1 * var1.field4306;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("nh")
   public Widget method7405(int var1) {
      this.field4246 = var1 * -1076753689;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)Lma;")
   @ObfuscatedName("fz")
   public static classMA method7322(Widget var0, int var1) {
      if (var0 == null) {
         return var0.method7324(var1);
      } else {
         try {
            return var0.field4364;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "lw.ba(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("ex")
   public int[] method7318() {
      if (11 == -1510882375 * this.field4373 && null != this.field4364) {
         int[] var1 = new int[3];
         int var2 = 0;

         for (classMD var4 : this.field4364.method7503((byte)46)) {
            if (!var4.method7578((byte)-98).equals("user_id")) {
               if (var4.vmethod525(-1173770292) != 0) {
                  return null;
               }

               var1[var2++] = var4.vmethod523(-1377673403);
               if (var2 > 3) {
                  return null;
               }
            }
         }

         return var1;
      } else {
         return null;
      }
   }

   public int getType() {
      return this.field4373 * -1510882375;
   }

   public int getOpacity() {
      return this.field4345 * 1517457381;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("jn")
   public Widget method7406(int var1) {
      this.field4333 = var1 * -259501473;
      return this;
   }

   public boolean isFilled() {
      return this.field4269;
   }

   @ObfuscatedSignature(descriptor = "()Lma;")
   @ObfuscatedName("eo")
   public classMA method7323() {
      return this.field4364;
   }

   public boolean isIf3() {
      return this.field4273;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("bd")
   public Widget method7407(int var1) {
      this.field4262 = var1 * 1632281619;
      return this;
   }

   @ObfuscatedSignature(descriptor = "()Llx;")
   @ObfuscatedName("fl")
   public classLX method7345() {
      return this.field4367;
   }

   @ObfuscatedSignature(descriptor = "(Llc;Z)V")
   @ObfuscatedName("fp")
   public void method7356(PlayerComposition var1, boolean var2) {
      this.field4331 = 299331441;
      this.field4356 = new PlayerComposition(var1);
      if (!var2) {
         this.field4356.field4093 = Arrays.copyOf(this.field4356.field4090, this.field4356.field4090.length);
         this.field4356.method6754((byte)-127);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;)V")
   @ObfuscatedName("ef")
   void method7329(classLY var1) {
      this.field4401 = true;
      ArrayList var2 = this.field4364.method7496((short)-8203);
      ArrayList var3 = classMA.method7501(this.field4364, 1036447206);
      int var4 = var2.size() + var3.size();
      this.field4338 = new Widget[var4];
      int var5 = 0;

      for (classMN var7 : var2) {
         Widget var8 = classMN.method7664(5, this, var5, 0, 0, 0, 0, var7.field4539, (byte)-5);
         var8.field4337 = var7.field4542.method4148((byte)-24);
         classLN var9 = new classLN(var7.field4542);
         var1.field4436.method10022(var8.field4337, var9, (byte)1);
         this.field4338[var5++] = var8;
      }

      for (classML var11 : var3) {
         Widget var12 = classMN.method7664(4, this, var5, 0, 0, 0, 0, var11.field4523, (byte)-92);
         var12.field4299 = var11.field4530;
         var12.field4243 = (Integer)this.field4365.get(463835541 * var11.field4522) * 590574429;
         var12.field4302 = 329236247 * var11.field4521;
         var12.field4303 = var11.field4524 * 616376749;
         this.field4338[var5++] = var12;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;)V")
   @ObfuscatedName("em")
   void method7330(classLY var1) {
      this.field4401 = true;
      ArrayList var2 = this.field4364.method7496((short)-4545);
      ArrayList var3 = classMA.method7501(this.field4364, 1884139200);
      int var4 = var2.size() + var3.size();
      this.field4338 = new Widget[var4];
      int var5 = 0;

      for (classMN var7 : var2) {
         Widget var8 = classMN.method7664(5, this, var5, 0, 0, 0, 0, var7.field4539, (byte)-66);
         var8.field4337 = var7.field4542.method4148((byte)34);
         classLN var9 = new classLN(var7.field4542);
         var1.field4436.method10022(var8.field4337, var9, (byte)1);
         this.field4338[var5++] = var8;
      }

      for (classML var11 : var3) {
         Widget var12 = classMN.method7664(4, this, var5, 0, 0, 0, 0, var11.field4523, (byte)0);
         var12.field4299 = var11.field4530;
         var12.field4243 = (Integer)this.field4365.get(463835541 * var11.field4522) * 590574429;
         var12.field4302 = 329236247 * var11.field4521;
         var12.field4303 = var11.field4524 * 616376749;
         this.field4338[var5++] = var12;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ez")
   public void method7333() {
      this.field4367 = new classLX();

      for (int var1 = 1; var1 <= 12; var1++) {
         classTT.method11189(this.field4367.field4418, var1, 0, 1873082935);
      }

      for (char var2 = 0; var2 < ' '; var2++) {
         this.field4367.field4418.method11191(var2, 0, (byte)9);
      }

      this.field4367.field4418.method11191((char)688577459, 0, (byte)91);
      classTT.method11189(this.field4367.field4418, 82, 2, 1796657499);
      classTT.method11189(this.field4367.field4418, -6837354, 2, 1539048331);
      classTT.method11189(this.field4367.field4418, -887462927, 2, 1909242214);
   }

   @ObfuscatedSignature(descriptor = "()Llh;")
   @ObfuscatedName("ei")
   public classLH method7335() {
      return null != this.field4367 ? this.field4367.field4417 : null;
   }

   @ObfuscatedSignature(descriptor = "()Ltt;")
   @ObfuscatedName("ep")
   public classTT method7341() {
      return this.field4367 != null ? this.field4367.field4418 : null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("eg")
   public String method7315() {
      if (-1510882375 * this.field4373 == 11 && this.field4364 != null) {
         String var1 = this.field4364.method7506(-143597963);

         for (classMD var3 : this.field4364.method7503((byte)85)) {
            String var4 = String.format("%%%S%%", var3.method7578((byte)-108));
            if (var3.vmethod525(-1173380297) == 0) {
               var1.replaceAll(var4, Integer.toString(var3.vmethod523(-1283537517)));
            } else {
               var1.replaceAll(var4, var3.vmethod519(-1458487053));
            }
         }

         return var1;
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;)[Ljava/lang/Object;")
   @ObfuscatedName("cr")
   Object[] method7210(Object[] var1) {
      return var1 != null ? Arrays.copyOf(var1, var1.length) : null;
   }

   public void setOnTargetLeaveListener(Object[] var1) {
      this.field4348 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Z)Llw;")
   @ObfuscatedName("wj")
   public Widget method7408(boolean var1) {
      this.field4287 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(IILjava/lang/String;)V")
   @ObfuscatedName("dx")
   public void method7258(int var1, int var2, String var3) {
      if (this.field4351 == null || this.field4351.length <= var1) {
         String[][] var4 = new String[var1 + 1][];
         if (null != this.field4351) {
            System.arraycopy(this.field4351, 0, var4, 0, this.field4351.length);
         }

         this.field4351 = var4;
      }

      if (null == this.field4351[var1] || this.field4351[var1].length <= var2) {
         String[] var5 = new String[var2 + 1];
         if (this.field4351[var1] != null) {
            System.arraycopy(this.field4351[var1], 0, var5, 0, this.field4351[var1].length);
         }

         this.field4351[var1] = var5;
      }

      this.field4351[var1][var2] = var3;
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)[I")
   @ObfuscatedName("yc")
   public static int[] method7319(Widget var0, byte var1) {
      if (var0 == null) {
         var0.method7320(var1);
      }

      try {
         if (11 == -1510882375 * var0.field4373) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            if (null != var0.field4364) {
               int[] var2 = new int[3];
               int var3 = 0;
               Iterator var4 = var0.field4364.method7503((byte)106).iterator();

               while (var4.hasNext()) {
                  if (var1 <= 1) {
                     throw new IllegalStateException();
                  }

                  classMD var5 = (classMD)var4.next();
                  if (!var5.method7578((byte)-54).equals("user_id")) {
                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }

                     if (var5.vmethod525(1565430181) != 0) {
                        return null;
                     }

                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }

                     var2[var3++] = var5.vmethod523(-1525521641);
                     if (var3 > 3) {
                        if (var1 <= 1) {
                           throw new IllegalStateException();
                        }

                        return null;
                     }
                  }
               }

               return var2;
            }
         }

         return null;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lw.bu(" + ')');
      }
   }

   public Object[] getOnLoadListener() {
      return this.field4374;
   }

   @ObfuscatedSignature(descriptor = "(Lly;)V")
   @ObfuscatedName("eu")
   void method7331(classLY var1) {
      this.field4401 = true;
      ArrayList var2 = this.field4364.method7496((short)-244);
      ArrayList var3 = classMA.method7501(this.field4364, 599647165);
      int var4 = var2.size() + var3.size();
      this.field4338 = new Widget[var4];
      int var5 = 0;

      for (classMN var7 : var2) {
         Widget var8 = classMN.method7664(5, this, var5, 0, 0, 0, 0, var7.field4539, (byte)-1);
         var8.field4337 = var7.field4542.method4148((byte)-1);
         classLN var9 = new classLN(var7.field4542);
         var1.field4436.method10022(var8.field4337, var9, (byte)1);
         this.field4338[var5++] = var8;
      }

      for (classML var11 : var3) {
         Widget var12 = classMN.method7664(4, this, var5, 0, 0, 0, 0, var11.field4523, (byte)-66);
         var12.field4299 = var11.field4530;
         var12.field4243 = (Integer)this.field4365.get(463835541 * var11.field4522) * 590574429;
         var12.field4302 = 329236247 * var11.field4521;
         var12.field4303 = var11.field4524 * 616376749;
         this.field4338[var5++] = var12;
      }
   }

   public void setOnReleaseListener(Object[] var1) {
      this.field4355 = var1;
   }

   public Widget() {
      this.field4241 = -1;
      this.field4242 = -1;
      this.field4273 = false;
      this.field4327 = -519254441;
      this.field4258 = 1612510305;
      this.field4245 = 0;
      this.field4332 = 0;
      this.field4262 = 0;
      this.field4248 = 0;
      this.field4249 = 0;
      this.field4250 = 0;
      this.field4246 = 0;
      this.field4283 = 0;
      this.field4253 = 0;
      this.field4333 = 0;
      this.field4279 = 0;
      this.method7357(-1);
      this.field4255 = 0;
      this.method7367(-1);
      this.field4247 = 0;
      this.field4257 = 0;
      this.field4272 = 62747509;
      this.field4259 = -1988594555;
      this.field4340 = 676768211;
      this.field4261 = -1000754647;
      this.field4287 = false;
      this.field4263 = -1939173753;
      this.field4264 = 0;
      this.field4339 = 0;
      this.field4329 = 0;
      this.field4256 = 0;
      this.field4267 = 0;
      this.field4268 = 0;
      this.field4293 = 0;
      this.field4270 = 0;
      this.field4271 = 0;
      this.field4269 = false;
      this.field4305 = classYE.field6970;
      this.field4345 = 0;
      this.field4274 = 0;
      this.field4275 = 327091521;
      this.field4276 = false;
      this.field4346 = -857041169;
      this.field4347 = -1515656587;
      this.field4349 = 0;
      this.field4280 = false;
      this.field4281 = 0;
      this.field4282 = 0;
      this.field4331 = 656328391;
      this.field4372 = -434741949;
      this.field4285 = -1108433005;
      this.field4352 = -607878109;
      this.field4286 = -742198477;
      this.field4291 = -668997325;
      this.field4288 = 0;
      this.field4289 = 0;
      this.field4354 = 0;
      this.field4290 = 0;
      this.field4292 = 0;
      this.field4336 = 1053796444;
      this.field4294 = 0;
      this.field4260 = 0;
      this.field4295 = false;
      this.field4296 = false;
      this.field4297 = -699666682;
      this.field4243 = -590574429;
      this.field4299 = "";
      this.field4300 = "";
      this.field4301 = 0;
      this.field4302 = 0;
      this.field4303 = 0;
      this.field4304 = false;
      this.field4318 = 0;
      this.field4358 = false;
      this.field4307 = "";
      this.field4308 = 695305012;
      this.field4309 = null;
      this.field4366 = 0;
      this.field4311 = 0;
      this.field4312 = false;
      this.field4343 = "";
      this.field4313 = false;
      this.field4314 = 1709618187;
      this.field4315 = "";
      this.field4316 = classKK.field3793;
      this.field4317 = -1321867761;
      this.field4342 = 0;
      this.field4319 = 0;
      this.field4325 = 0;
      this.field4320 = false;
      this.field4321 = false;
      this.field4322 = -1478436671;
      this.field4323 = 0;
      this.field4298 = 0;
      this.field4324 = 0;
      this.field4401 = false;
      this.field4326 = false;
      this.field4371 = false;
      this.field4328 = 1716422141;
   }

   public Object[] getOnInvTransmitListener() {
      return this.field4353;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("ea")
   public String method7303(String var1) {
      return 11 == this.field4373 * -1510882375 && this.field4364 != null && this.method7296(1355775728) ? this.field4364.method7493(var1, (byte)-29) : null;
   }

   @ObfuscatedSignature(descriptor = "(Llh;)Z")
   @ObfuscatedName("fi")
   boolean method7348(classLH var1) {
      boolean var2 = false;
      if (this.field4299 != null && !this.field4299.isEmpty()) {
         var2 |= classLH.method6855(var1, classLB.method6708(this.field4299, -1749188599), 1589594921);
         this.field4299 = "";
      }

      if (this.field4300 != null && !this.field4300.isEmpty()) {
         var2 |= var1.method6859(classLB.method6708(this.field4300, 1431120141), -797032978);
         this.field4300 = "";
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("dn")
   public Widget method7409(int var1) {
      this.field4318 = var1 * 997939289;
      return this;
   }

   public void setRelativeY(int var1) {
      this.field4255 = var1 * -217131595;
   }

   @ObfuscatedSignature(descriptor = "(Llh;)Z")
   @ObfuscatedName("fb")
   boolean method7349(classLH var1) {
      boolean var2 = false;
      if (this.field4299 != null && !this.field4299.isEmpty()) {
         var2 |= classLH.method6855(var1, classLB.method6708(this.field4299, 2048179364), -278339340);
         this.field4299 = "";
      }

      if (this.field4300 != null && !this.field4300.isEmpty()) {
         var2 |= var1.method6859(classLB.method6708(this.field4300, -1454598254), -1098056040);
         this.field4300 = "";
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lly;)Z")
   @ObfuscatedName("fh")
   public boolean method7351(classLY var1) {
      classLH var2 = this.method7334(-1384507301);
      if (var2 == null) {
         return false;
      } else {
         boolean var3 = false;
         if (!var2.method7043((byte)-40) && -1 != -1803847435 * this.field4243) {
            int var4 = classLH.method7045(var2, 1936117163);
            int var5 = var2.method7049(891551425);
            int var6 = var2.method7039(-729758454);
            int var7 = var2.method7038((byte)-88);
            classZV var8 = method7244(this, var1, (byte)28);
            if (null != var8) {
               var3 |= var2.method6860(var8, -333112261);
               var3 |= this.method7346(var2, -1429429791);
               var3 |= var2.method6884(var4, var5, -882131617);
               var3 |= var2.method6930(var6, var7, 1122842785);
            }
         } else if (var2.method7043((byte)-91)) {
            var3 |= this.method7346(var2, -1991414598);
         }

         classLH.method6840(var2, 1403534382);
         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lly;)Z")
   @ObfuscatedName("fz")
   public boolean method7352(classLY var1) {
      classLH var2 = this.method7334(-603792379);
      if (var2 == null) {
         return false;
      } else {
         boolean var3 = false;
         if (!var2.method7043((byte)-44) && -1 != -1803847435 * this.field4243) {
            int var4 = classLH.method7045(var2, 1396991191);
            int var5 = var2.method7049(824062724);
            int var6 = var2.method7039(1326989786);
            int var7 = var2.method7038((byte)-7);
            classZV var8 = method7244(this, var1, (byte)-36);
            if (null != var8) {
               var3 |= var2.method6860(var8, -1732545020);
               var3 |= this.method7346(var2, -1938765451);
               var3 |= var2.method6884(var4, var5, -56253071);
               var3 |= var2.method6930(var6, var7, 1122842785);
            }
         } else if (var2.method7043((byte)-98)) {
            var3 |= this.method7346(var2, -1349843627);
         }

         classLH.method6840(var2, -1363350530);
         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ck")
   public void method7364(int var1) {
      this.field4240 = var1;
   }

   public int getItemId() {
      return this.field4317 * 2073671441;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lda;)V")
   @ObfuscatedName("dg")
   public void method7285(String var1, classDA var2) {
      if (11 == this.field4373 * -1510882375 && var1 != null) {
         this.field4364 = new classMA();
         this.field4364.method7537(var1, var2, 1472116592);
      }
   }

   public String getText() {
      return this.method7377().replace(' ', ' ');
   }

   public void setNoScrollThrough(boolean var1) {
      this.field4326 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lly;Lda;)Z")
   @ObfuscatedName("ew")
   public boolean method7326(classLY var1, classDA var2) {
      if (11 == this.field4373 * -1510882375 && null != this.field4364) {
         this.field4364.method7480(var2, 838164688);
         if (this.field4364.method7483(-139701566) != 2133229739 * this.field4328) {
            this.field4328 = this.field4364.method7483(-139701566) * -1716422141;
            if (2133229739 * this.field4328 >= 3) {
               return true;
            }

            if (2 == this.field4328 * 2133229739) {
               this.method7328(var1, -1152753755);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;Lly;Lda;)Z")
   @ObfuscatedName("qr")
   public static boolean method7327(Widget var0, classLY var1, classDA var2) {
      if (11 == var0.field4373 * -1510882375 && null != var0.field4364) {
         var0.field4364.method7480(var2, 572195416);
         if (var0.field4364.method7483(-139701566) != 2133229739 * var0.field4328) {
            var0.field4328 = var0.field4364.method7483(-139701566) * -1716422141;
            if (-2015835098 * var0.field4328 >= 3) {
               return true;
            }

            if (2 == var0.field4328 * 2133229739) {
               var0.method7328(var1, -1243617095);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "([BI)Lbl;")
   @ObfuscatedName("ab")
   static Script method7204(byte[] var0, int var1) {
      try {
         Script var2 = new Script();
         classXY var3 = new classXY(var0);
         var3.field6953 = (var3.field6952.length - 2) * -1095856699;
         int var4 = var3.method13099(-2136955376);
         int var5 = var3.field6952.length - 2 - var4 - 16;
         var3.field6953 = var5 * -1095856699;
         int var6 = classXY.method13113(var3, (byte)1);
         var2.field493 = var3.method13099(-966173398) * -420896219;
         var2.field496 = var3.method13099(1836786951) * -431593185;
         var2.field492 = var3.method13099(1297999619) * 2139455799;
         var2.field495 = var3.method13099(395083812) * 1424730171;
         var2.field494 = var3.method13099(-2057306067) * 712179175;
         var2.field497 = var3.method13099(760736946) * 1116857197;
         int var7 = var3.method13095(-346779531);
         if (var7 > 0) {
            if (var1 >= 642137221) {
               throw new IllegalStateException();
            }

            var2.field505 = Script.method1148(var2, var7, (byte)127);

            for (int var8 = 0; var8 < var7; var8++) {
               int var9 = var3.method13099(909554200);
               IterableHashTable var10000 = new IterableHashTable;
               int var10002;
               if (var9 > 0) {
                  if (var1 >= 642137221) {
                     throw new IllegalStateException();
                  }

                  var10002 = classBZ.method1387(var9, -81972016);
               } else {
                  var10002 = 1;
               }

               var10000./* $VF: Unable to resugar constructor */<init>(var10002);
               IterableHashTable var10 = var10000;
               var2.field505[var8] = var10;

               while (var9-- > 0) {
                  if (var1 >= 642137221) {
                     throw new IllegalStateException();
                  }

                  int var11 = classXY.method13113(var3, (byte)1);
                  int var12 = classXY.method13113(var3, (byte)1);
                  IterableHashTable.method13620(var10, new IntegerNode(var12), var11);
               }
            }
         }

         var3.field6953 = 0;
         var2.field500 = var3.method13125((byte)-43);
         var2.field501 = new int[var6];
         var2.field502 = new int[var6];
         var2.field504 = new String[var6];
         var2.field503 = new long[var6];
         int var14 = 0;

         while (var3.field6953 * 702114061 < var5) {
            int var15 = var3.method13099(1509230209);
            switch (var15) {
               case 3:
                  var2.field504[var14] = var3.method13127(59567316);
                  break;
               case 21:
               case 38:
               case 39:
               case 62:
               case 63:
                  var2.field502[var14] = var3.method13095(-346779531);
                  break;
               case 61:
                  var2.field503[var14] = classXY.method13115(var3, 1648463983);
                  break;
               default:
                  if (var15 <= 99) {
                     if (var1 >= 642137221) {
                        throw new IllegalStateException();
                     }

                     var2.field502[var14] = classXY.method13113(var3, (byte)1);
                  } else {
                     var2.field502[var14] = var3.method13095(-346779531);
                  }
            }

            var2.field501[var14++] = var15;
         }

         return var2;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "lw.ab(" + ')');
      }
   }

   public int getAnimationId() {
      return this.field4286 * 2114568197;
   }

   @ObfuscatedSignature(descriptor = "(Z)Llw;")
   @ObfuscatedName("su")
   public Widget method7410(boolean var1) {
      this.field4280 = var1;
      return this;
   }

   public void setFlippedVertically(boolean var1) {
      this.field4350 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("jo")
   public Widget method7411(int var1) {
      this.field4302 = var1 * 571804797;
      return this;
   }

   public int getHeight() {
      return this.field4257 * 293773697;
   }

   @ObfuscatedSignature(descriptor = "(Z)Llw;")
   @ObfuscatedName("qd")
   public Widget method7412(boolean var1) {
      this.field4269 = var1;
      return this;
   }

   public int getHeightMode() {
      return this.field4250 * 372143361;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Llw;")
   @ObfuscatedName("vn")
   public Widget method7370(String var1) {
      this.method7380(var1.replace(' ', ' '));
      return this;
   }

   public String[] getActions() {
      return this.field4335;
   }

   public boolean getNoClickThrough() {
      return this.field4401;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("mf")
   public Widget method7413(int var1) {
      this.field4301 = var1 * -763080175;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("kq")
   public Widget method7414(int var1) {
      this.field4243 = var1 * 590574429;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("as")
   void method7224(classXY var1, int var2) {
      try {
         this.field4330 = var1.method13127(552941395);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)[I")
   @ObfuscatedName("ac")
   int[] method7232(classXY var1, int var2) {
      try {
         int var3 = var1.method13225(-346779531);
         if (0 == var3) {
            if (var2 == 155860854) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            int[] var4 = new int[var3];

            for (int var5 = 0; var5 < var3; var5++) {
               if (var2 == 155860854) {
                  throw new IllegalStateException();
               }

               var4[var5] = classXY.method13113(var1, (byte)1);
            }

            return var4;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lw.ac(" + ')');
      }
   }

   public String getName() {
      return this.method7385().replace(' ', ' ');
   }

   @ObfuscatedSignature(descriptor = "(Lly;Lou;IZLlc;Lpl;Lpp;I)Lfx;")
   @ObfuscatedName("ad")
   public Model method7250(classLY var1, Animation var2, int var3, boolean var4, PlayerComposition var5, NPCComposition var6, NpcOverrides var7, int var8) {
      try {
         int var9;
         int var10;
         if (var4) {
            var9 = -799043941 * this.field4285;
            var10 = 697152629 * this.field4285;
         } else {
            var9 = this.field4247 * -910601;
            var10 = this.field4339 * -759306091;
         }

         if (var9 == 0) {
            if (var8 >= -1946717396) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            if (1 == var9) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               if (-1 == var10) {
                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }

                  return null;
               }
            }

            if (var7 != null) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               if (var7.field5371) {
                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }

                  if (6 == var9) {
                     if (var8 >= -1946717396) {
                        throw new IllegalStateException();
                     }

                     var9 = 3;
                  }
               }
            }

            long var11;
            label139: {
               var11 = (var9 << 16) + var10;
               if (2 != var9) {
                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }

                  if (var9 != 6) {
                     break label139;
                  }

                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }
               }

               if (var6 == null) {
                  return null;
               }

               var11 = (var9 << 16) + 283969983 * var6.field5310;
            }

            if (null != var7) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               var11 |= -4980301671096129853L * var7.field5376 << 20;
            }

            Model var13 = (Model)NodeCache.method6460(var1.field4429, var11);
            if (null == var13) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               ModelData var14 = null;
               int var15 = 64;
               int var16 = 768;
               switch (var9) {
                  case 1:
                     var14 = ModelData.method3985(var1.field4435, var10, 0);
                     break;
                  case 2:
                     var14 = var6.method8987(null, (byte)37);
                     break;
                  case 3:
                     ModelData var10000;
                     if (null != var5) {
                        if (var8 >= -1946717396) {
                           throw new IllegalStateException();
                        }

                        var10000 = PlayerComposition.method6746(var5, (byte)0);
                     } else {
                        var10000 = null;
                     }

                     var14 = var10000;
                     break;
                  case 4:
                     ItemComposition var17 = classOB.method8329(var10, (byte)77);
                     var14 = var17.method8688(10, -1145537891);
                     var15 += 1845819663 * var17.field5028;
                     var16 += var17.field5063 * -1636300529;
                  case 5:
                  default:
                     break;
                  case 6:
                     var14 = var6.method8987(var7, (byte)115);
               }

               if (null == var14) {
                  if (var8 >= -1946717396) {
                     throw new IllegalStateException();
                  }

                  return null;
               }

               var13 = var14.method4058(var15, var16, -50, -10, -50);
               var1.field4427.method6466(var13, var11);
            }

            if (null != var2) {
               if (var8 >= -1946717396) {
                  throw new IllegalStateException();
               }

               var13 = var2.method8801(var13, var3, -2146329348);
            }

            return var13;
         }
      } catch (RuntimeException var18) {
         throw classEG.method3899(var18, "lw.ad(" + ')');
      }
   }

   public int getContentType() {
      return this.field4332 * -90794217;
   }

   public int getYTextAlignment() {
      return this.field4303 * -338086361;
   }

   public net.runelite.api.widgets.Widget getChild(int var1) {
      Widget[] var2 = this.method7391();
      return var2 != null && var1 >= 0 && var1 < var2.length ? var2[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "(Llw;Ljava/lang/String;Ljava/lang/String;Lda;J)V")
   @ObfuscatedName("ij")
   public static void method7278(Widget var0, String var1, String var2, classDA var3, long var4) {
      if (var0 == null) {
         var0.method7279(var1, var1, var3, var4);
      } else {
         try {
            if (-1510882375 * var0.field4373 == 11 && var4 != -1L) {
               var1 = var1.replaceAll("%userid%", Long.toString(var4));
               var0.field4364 = new classMA();
               if (!var0.field4364.method7474(var1, var2, var3, 1881063693)) {
                  var0.field4364 = null;
               } else {
                  if (var0.field4365 == null || null == var0.field4266) {
                     var0.method7286(542097596);
                  }
               }
            }
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "lw.am(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Ljava/lang/String;IB)V")
   @ObfuscatedName("ik")
   public static void method7292(Widget var0, String var1, int var2, byte var3) {
      if (var0 == null) {
         var0.method7293(var1, var2, var3);
      } else {
         try {
            if (var0.field4373 * -1510882375 != 11) {
               if (var3 != 32) {
                  ;
               }
            } else {
               if (null == var0.field4266) {
                  if (var3 == 32) {
                     throw new IllegalStateException();
                  }

                  var0.method7286(-1248678116);
               }

               var0.field4266.put(var1, var2);
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "lw.bp(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cg")
   boolean method7241() {
      return this.field4321 || this.field4312 || 0 != -2011859635 * this.field4271 || -1872633104 * this.field4275 != 0;
   }

   public void setOnKeyListener(Object[] var1) {
      this.field4386 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("tb")
   public Widget method7415(int var1) {
      this.field4290 = var1 * 993003239;
      return this;
   }

   public boolean getSpriteTiling() {
      return this.field4280;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("df")
   public Widget method7365(int var1) {
      return this.method7359(-1, var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lda;I)V")
   @ObfuscatedName("ah")
   public void method7282(String var1, String var2, String var3, String var4, String var5, classDA var6, int var7) {
      try {
         this.field4364 = new classMA();
         if (!this.field4364.method7474(var1, var3, var6, 1711128091)) {
            if (var7 >= -1580735539) {
               throw new IllegalStateException();
            } else {
               this.field4364 = null;
            }
         } else {
            label31: {
               if (null != this.field4266) {
                  if (var7 >= -1580735539) {
                     throw new IllegalStateException();
                  }

                  if (null != this.field4365) {
                     break label31;
                  }

                  if (var7 >= -1580735539) {
                     throw new IllegalStateException();
                  }
               }

               this.method7286(2055703410);
            }

            this.field4364.method7476(var2, var4, var5, (short)-2712);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lw.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;IB)V")
   @ObfuscatedName("bp")
   public void method7293(String var1, int var2, byte var3) {
      try {
         if (this.field4373 * -1510882375 != 11) {
            if (var3 != 32) {
               ;
            }
         } else {
            if (null == this.field4266) {
               if (var3 == 32) {
                  throw new IllegalStateException();
               }

               this.method7286(-1248678116);
            }

            this.field4365.put(var1, var2);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lw.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Llw;")
   @ObfuscatedName("al")
   public Widget method7363(int var1, int var2) {
      return this.method7405(var1).method7386(var2);
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("mz")
   public Widget method7416(int var1) {
      this.field4339 = var1 * 731382367;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("ad")
   public Widget method7417(int var1) {
      this.field4372 = var1 * 434741949;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("ox")
   public Widget method7418(int var1) {
      this.field4250 = var1 * -565540095;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bj")
   public int method7308(int var1) {
      try {
         if (null != this.field4266) {
            if (var1 == -1415490927) {
               throw new IllegalStateException();
            }

            if (!this.field4266.isEmpty()) {
               return 1;
            }
         }

         return 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.bj(" + 41);
      }
   }

   public void setType(int var1) {
      this.field4373 = var1 * 685068937;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("bk")
   public int method7312(byte var1) {
      try {
         if (11 == this.field4250 * -1510882375) {
            if (var1 >= 2) {
               throw new IllegalStateException();
            }

            if (null != this.field4364) {
               if (var1 >= 2) {
                  throw new IllegalStateException();
               }

               if (this.field4266 != null) {
                  if (var1 >= 2) {
                     throw new IllegalStateException();
                  }

                  if (!this.field4266.isEmpty()) {
                     String var2 = this.field4364.method7506(-391312536);
                     if (var2 != null && this.field4266.containsKey(this.field4364.method7521(-56824379))) {
                        return (Integer)this.field4266.get(var2);
                     }

                     return -1;
                  }

                  if (var1 >= 2) {
                     throw new IllegalStateException();
                  }
               }
            }
         }

         return -1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.bk(" + 41);
      }
   }

   public boolean getNoScrollThrough() {
      return this.field4326;
   }

   public boolean contains(Point var1) {
      Rectangle var2 = this.getBounds();
      return var2 != null && var2.contains(new java.awt.Point(var1.getX(), var1.getY()));
   }

   @ObfuscatedSignature(descriptor = "(B)[I")
   @ObfuscatedName("bu")
   public int[] method7320(byte var1) {
      try {
         if (11 == -1510882375 * this.field4249) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            if (null != this.field4364) {
               int[] var2 = new int[3];
               int var3 = 0;
               Iterator var4 = this.field4364.method7503((byte)106).iterator();

               while (var4.hasNext()) {
                  if (var1 <= 1) {
                     throw new IllegalStateException();
                  }

                  classMD var5 = (classMD)var4.next();
                  if (!var5.method7578((byte)-54).equals("user_id")) {
                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }

                     if (var5.vmethod525(1565430181) != 0) {
                        return null;
                     }

                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }

                     var2[var3++] = var5.vmethod523(-1525521641);
                     if (var3 > 3) {
                        if (var1 <= 1) {
                           throw new IllegalStateException();
                        }

                        return null;
                     }
                  }
               }

               return var2;
            }
         }

         return null;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lw.bu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lma;")
   @ObfuscatedName("ba")
   public classMA method7324(int var1) {
      try {
         return this.field4364;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.ba(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("bd")
   public static boolean method7266(Widget var0) {
      return 11 == var0.field4373 * -1510882375 || var0.field4373 * -1510882375 == 12;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ag")
   void method7220(classXY var1, int var2) {
      try {
         var1.method13224(-346779531);
         this.field4276 = true;
         this.field4336 = var1.method13201(-346779531) * 685068937;
         this.field4311 = var1.method13172(-241947726) * -411168601;
         this.field4366 = var1.method13156((byte)16) * -1076753689;
         this.field4327 = var1.method13158((byte)16) * 1989424741;
         this.field4286 = var1.method13225(106831180) * 1034606045;
         if (9 == this.field4250 * -1510882375) {
            this.field4289 = var1.method13156((byte)16) * -259501473;
         } else {
            this.field4274 = var1.method13099(998811198) * -259501473;
         }

         this.field4238 = var1.method13235((byte)17) * -1430539503;
         this.field4346 = var1.method13097((byte)17) * -565540095;
         this.field4243 = var1.method13097((byte)17) * 1632281619;
         this.field4240 = var1.method13235((byte)17) * 454876301;
         this.field4329 = var1.method13225(-1842555802) * -676768211;
         if (-1502647899 * this.field4318 == 65535) {
            if (var2 != 1218236279) {
               throw new IllegalStateException();
            }

            this.field4291 = 676768211;
         } else {
            this.field4239 = -676768211 * (-1502647899 * this.field4267 + (-1163656551 * this.field4342 & -65536));
         }

         boolean var10001;
         if (var1.method13105(-346779531) == 1) {
            if (var2 != 1218236279) {
               throw new IllegalStateException();
            }

            var10001 = true;
         } else {
            var10001 = false;
         }

         this.field4269 = var10001;
         if (0 == -1510882375 * this.field4311) {
            this.field4347 = var1.method13250(-126044868) * -803344527;
            this.field4346 = var1.method13254(1051631118) * -251301447;
            if (var1.method13254(-346779531) == 1) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            this.field4304 = var10001;
         }

         if (5 == this.field4259 * -1510882375) {
            if (var2 != 1218236279) {
               throw new IllegalStateException();
            }

            this.field4336 = classXY.method13113(var1, (byte)1) * 857041169;
            this.field4262 = var1.method13278(-359716027) * -727683123;
            if (var1.method13200(-346779531) == 1) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            this.field4401 = var10001;
            this.field4239 = var1.method13275(-346779531) * -2056841747;
            this.field4347 = var1.method13099(-346779531) * -1993421947;
            this.field4373 = classXY.method13113(var1, (byte)1) * 37017731;
            if (var1.method13105(-346779531) == 1) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            this.field4273 = var10001;
            if (var1.method13152(-346779531) == 1) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            this.field4371 = var10001;
         }

         if (6 == this.field4319 * -1510882375) {
            if (var2 != 1218236279) {
               throw new IllegalStateException();
            }

            this.field4282 = 656328391;
            this.field4331 = var1.method13173(classXM.field6886.field6888, 2125942098) * 434741949;
            this.field4342 = var1.method13158((byte)16) * 1981476425;
            this.field4327 = var1.method13258((byte)16) * 1954863581;
            this.field4302 = var1.method13099(1370270829) * 1703663435;
            this.field4260 = var1.method13201(994802512) * 993003239;
            this.field4293 = var1.method13201(450674507) * 863028557;
            this.field4333 = var1.method13295(-1508902731) * 1556726191;
            this.field4346 = var1.method13266(-1221385387) * 742198477;
            if (65535 == this.field4282 * 2114568197) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               this.field4259 = -742198477;
            }

            if (var1.method13295(-346779531) == 1) {
               if (var2 != 1218236279) {
                  return;
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            this.field4312 = var10001;
            var1.method13200(-450046437);
            if (-1966367247 * this.field4271 != 0) {
               this.field4331 = var1.method13152(-520213711) * 1299951355;
            }

            if (this.field4366 * 372143361 != 0) {
               var1.method13107(-1192075905);
            }
         }

         if (4 == -1510882375 * this.field4268) {
            if (var2 != 1218236279) {
               return;
            }

            this.field4329 = var1.method13266(-1135544449) * 590574429;
            if (this.field4275 * -1803847435 == 65535) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               this.field4274 = -590574429;
            }

            this.field4307 = var1.method13127(-945185794);
            this.field4279 = var1.method13266(-346779531) * -763080175;
            this.field4332 = var1.method13266(-346779531) * 571804797;
            this.field4331 = var1.method13176(-346779531) * -2106140265;
            if (var1.method13254(-346779531) == 1) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            this.field4276 = var10001;
            this.field4373 = classXY.method13113(var1, (byte)1) * 1590850751;
         }

         if (-1510882375 * this.field4342 == 3) {
            if (var2 != 1218236279) {
               throw new IllegalStateException();
            }

            this.field4289 = classXY.method13113(var1, (byte)1) * 1590850751;
            this.field4295 = var1.method13172(-346779531) == 1;
            this.field4271 = var1.method13172(-346779531) * -2056841747;
         }

         if (9 == this.field4319 * -1510882375) {
            if (var2 != 1218236279) {
               throw new IllegalStateException();
            }

            this.field4243 = var1.method13152(-346779531) * 327091521;
            this.field4275 = classXY.method13113(var1, (byte)1) * 1590850751;
            if (var1.method13099(-346779531) == 1) {
               if (var2 != 1218236279) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            this.field4280 = var10001;
         }

         this.field4288 = var1.method13146(-758448158) * 997939289;
         this.field4307 = var1.method13134(-205058935);
         int var3 = var1.method13146(-346779531);
         if (var3 > 0) {
            if (var2 != 1218236279) {
               throw new IllegalStateException();
            }

            this.field4335 = new String[var3];

            for (int var4 = 0; var4 < var3; var4++) {
               if (var2 != 1218236279) {
                  return;
               }

               this.field4335[var4] = var1.method13127(-520288158);
            }
         }

         this.field4347 = var1.method13099(-346779531) * 230129403;
         this.field4345 = var1.method13250(-346779531) * -2074329479;
         if (var1.method13300(-346779531) == 1) {
            if (var2 != 1218236279) {
               return;
            }

            var10001 = true;
         } else {
            var10001 = false;
         }

         this.field4295 = var10001;
         this.field4315 = var1.method13134(-140187877);
         this.field4389 = this.method7226(var1, 1062416733);
         this.field4252 = this.method7226(var1, 2099116228);
         this.field4375 = this.method7226(var1, 1842509635);
         this.field4396 = this.method7226(var1, 1263183737);
         this.field4387 = this.method7226(var1, 2071129357);
         this.field4376 = this.method7226(var1, 1394211475);
         this.field4394 = this.method7226(var1, 938205581);
         this.field4376 = this.method7226(var1, 1050161569);
         this.field4389 = this.method7226(var1, 540048844);
         this.field4395 = this.method7226(var1, 1635652811);
         this.field4392 = this.method7226(var1, 1062349515);
         this.field4384 = this.method7226(var1, 766182010);
         this.field4389 = this.method7226(var1, 1391640694);
         this.field4375 = this.method7226(var1, 1455687462);
         this.field4374 = this.method7226(var1, 1358422772);
         this.field4394 = this.method7226(var1, 2019082877);
         this.field4392 = this.method7226(var1, 1756166187);
         this.field4395 = this.method7226(var1, 1010164295);
         this.field4265 = method7229(this, var1, -1089851676);
         this.field4399 = method7229(this, var1, 1812795025);
         this.field4398 = method7229(this, var1, 209304390);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lw.ag(" + ')');
      }
   }

   public int getBorderType() {
      return this.field4281 * -2011859635;
   }

   public net.runelite.api.widgets.Widget[] getDynamicChildren() {
      Widget var1 = classOE.field4841.method2281(this.field4327 * -1163656551);
      if (var1 == null) {
         return new net.runelite.api.widgets.Widget[0];
      } else {
         Widget[] var2 = var1.field4338;
         if (var2 == null) {
            return new net.runelite.api.widgets.Widget[0];
         } else {
            ArrayList var3 = new ArrayList();

            for (Widget var7 : var2) {
               if (var7 != null) {
                  assert var7.field4340 * -1502647899 == this.field4327 * -1163656551;

                  if (var7.field4261 * 653784551 == this.field4258 * 63131743) {
                     var3.add(var7);
                  }
               }
            }

            return var3.toArray(new Widget[0]);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Llw;")
   @ObfuscatedName("pl")
   public Widget method7358(int var1, int var2) {
      return this.method7389(var1).method7406(var2);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ar")
   public boolean method7298(int var1) {
      try {
         return this.field4306 * 2133229739 == 2;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lw.bm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([IB)[I")
   @ObfuscatedName("ae")
   int[] method7214(int[] var1, byte var2) {
      try {
         int[] var10000;
         if (var1 != null) {
            if (var2 == 0) {
               throw new IllegalStateException();
            }

            var10000 = Arrays.copyOf(var1, var1.length);
         } else {
            var10000 = null;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lw.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("df")
   public void method7290(int var1, int var2) {
      if (this.field4349 * -1510882375 == 11) {
         if (this.field4365 == null) {
            this.method7286(-1816394492);
         }

         this.field4365.put(var1, var2);
      }
   }

   public Object[] getOnOpListener() {
      return this.field4384;
   }

   public int getWidth() {
      return this.field4247 * 1115597881;
   }

   public int getLineHeight() {
      return this.field4301 * 704962289;
   }

   @ObfuscatedSignature(descriptor = "(Lly;B)Lzv;")
   @ObfuscatedName("aq")
   public classZV method7247(classLY var1, byte var2) {
      try {
         if (this.field4256 * -1803847435 == -1) {
            return null;
         } else {
            classZV var3 = (classZV)NodeCache.method6460(var1.field4429, -1803847435 * this.field4333);
            if (null != var3) {
               if (var2 == 1) {
                  throw new IllegalStateException();
               } else {
                  return var3;
               }
            } else {
               IndexDataBase var5 = var1.field4432;
               IndexDataBase var6 = var1.field4440;
               int var7 = this.field4253 * -1803847435;
               classZV var4;
               if (!classHK.method6100(var5, var7, 0, (byte)-84)) {
                  if (var2 == 1) {
                     throw new IllegalStateException();
                  }

                  var4 = null;
               } else {
                  byte[] var9 = var6.method11937(var7, 0, -1598309711);
                  classZV var8;
                  if (null == var9) {
                     if (var2 == 1) {
                        throw new IllegalStateException();
                     }

                     var8 = null;
                  } else {
                     classZV var10 = new classZV(
                        var9, classYL.field7000, classYL.field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, classYL.field7002
                     );
                     classVZ.method12347((byte)68);
                     var8 = var10;
                  }

                  var4 = var8;
               }

               if (null != var4) {
                  if (var2 == 1) {
                     throw new IllegalStateException();
                  }

                  var1.field4427.method6466(var4, -1803847435 * this.field4291);
               }

               return var4;
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "lw.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Lda;J)V")
   @ObfuscatedName("am")
   public void method7279(String var1, String var2, classDA var3, long var4) {
      try {
         if (-1510882375 * this.field4268 == 11 && var4 != -1L) {
            var1 = var1.replaceAll("%userid%", Long.toString(var4));
            this.field4364 = new classMA();
            if (!this.field4364.method7474(var1, var2, var3, 1881063693)) {
               this.field4364 = null;
            } else {
               if (this.field4266 == null || null == this.field4365) {
                  this.method7286(542097596);
               }
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lw.am(" + ')');
      }
   }
}
