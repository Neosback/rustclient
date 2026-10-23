import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("uz")
public class classUZ {
   @ObfuscatedSignature(descriptor = "Luz;")
   @ObfuscatedName("ag")
   public static final classUZ field6431 = new classUZ(0.0F, -1.0F, 0.0F);
   @ObfuscatedSignature(descriptor = "Luz;")
   @ObfuscatedName("ab")
   public static final classUZ field6429 = new classUZ(0.0F, 0.0F, -1.0F);
   @ObfuscatedSignature(descriptor = "Luz;")
   @ObfuscatedName("ae")
   public static final classUZ field6430 = new classUZ(0.0F, 1.0F, 0.0F);
   @ObfuscatedSignature(descriptor = "Luz;")
   @ObfuscatedName("az")
   public static final classUZ field6426 = new classUZ(0.0F, 0.0F, 0.0F);
   @ObfuscatedSignature(descriptor = "[Luz;")
   @ObfuscatedName("as")
   static final classUZ[] field6428 = new classUZ[classUZ.field6427 * -2140716621];
   @ObfuscatedName("ax")
   static final int field6427 = -904570960;
   @ObfuscatedName("ao")
   public float field6424;
   @ObfuscatedName("ac")
   static int field6433 = 0;
   @ObfuscatedName("al")
   public float field6425;
   @ObfuscatedName("aa")
   public float field6423;
   @ObfuscatedSignature(descriptor = "Luz;")
   @ObfuscatedName("af")
   public static final classUZ field6432 = new classUZ(1.0F, 0.0F, 0.0F);

   classUZ(float var1, float var2, float var3) {
      this.field6425 = var1;
      this.field6423 = var2;
      this.field6424 = var3;
   }

   classUZ(classUZ var1) {
      this.field6425 = var1.field6425;
      this.field6423 = var1.field6423;
      this.field6424 = var1.field6424;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bl")
   final void method11808() {
      this.field6424 = 0.0F;
      this.field6423 = 0.0F;
      this.field6425 = 0.0F;
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("bi")
   public void method11858(float var1, float var2, float var3) {
      method11802(this, var1, var2, var3, 1996170472);
   }

   @ObfuscatedSignature(descriptor = "(Luz;I)V")
   @ObfuscatedName("ao")
   void method11804(classUZ var1, int var2) {
      try {
         method11802(this, var1.field6425, var1.field6423, var1.field6424, 1686353034);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uz.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   final void method11809(int var1) {
      try {
         this.field6424 = 0.0F;
         this.field6423 = 0.0F;
         this.field6425 = 0.0F;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uz.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFF)Luz;")
   @ObfuscatedName("am")
   public static classUZ method11784(float var0, float var1, float var2) {
      synchronized (field6428) {
         if (-1124722655 * field6433 == 0) {
            return new classUZ(var0, var1, var2);
         } else {
            method11802(field6428[(field6433 -= 801074145) * -1124722655], var0, var1, var2, 2018270634);
            return field6428[-1124722655 * field6433];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aq")
   public final void method11817(int var1) {
      try {
         float var2 = 1.0F / this.method11844(-1808401369);
         this.field6425 *= var2;
         this.field6423 *= var2;
         this.field6424 *= var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uz.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFI)V")
   @ObfuscatedName("ap")
   public final void method11823(float var1, float var2, float var3, int var4) {
      try {
         this.field6425 += var1;
         this.field6423 += var2;
         this.field6424 += var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uz.ap(" + ')');
      }
   }

   static {
      new classUZ(1.0F, 1.0F, 1.0F);
      new classUZ(0.0F, 0.0F, 1.0F);
      new classUZ(1.0F, 0.0F, 0.0F);
      new classUZ(-1.0F, 0.0F, 0.0F);
      new classUZ(0.0F, 0.0F, 1.0F);
      new classUZ(0.0F, 1.0F, 0.0F);
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("cp")
   public final void method11824(float var1, float var2, float var3) {
      this.field6425 += var1;
      this.field6423 += var2;
      this.field6424 += var3;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fv")
   public void method11859() {
      this.method11817(1213748358);
   }

   @ObfuscatedSignature(descriptor = "(Luz;I)V")
   @ObfuscatedName("ai")
   final void method11839(classUZ var1, int var2) {
      try {
         method11802(
            this,
            var1.field6424 * this.field6423 - this.field6424 * var1.field6423,
            this.field6424 * var1.field6425 - var1.field6424 * this.field6425,
            this.field6425 * var1.field6423 - this.field6423 * var1.field6425,
            1942851705
         );
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uz.aw(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;)Ljava/lang/String;")
   @ObfuscatedName("kd")
   public static String method11853(classUZ var0) {
      if (var0 == null) {
         var0.method11855();
      }

      return var0.field6425 + ", " + var0.field6423 + ", " + var0.field6424;
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("ak")
   public final float method11844(int var1) {
      try {
         return (float)Math.sqrt(this.field6424 * this.field6424 + (this.field6423 * this.field6423 + this.field6425 * this.field6425));
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uz.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FI)V")
   @ObfuscatedName("av")
   final void method11846(float var1, int var2) {
      try {
         this.field6425 *= var1;
         this.field6423 *= var1;
         this.field6424 *= var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uz.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;I)V")
   @ObfuscatedName("at")
   public final void method11849(classUL var1, int var2) {
      try {
         classUL var3 = classQQ.method9759(this.field6425, this.field6423, this.field6424, 0.0F, -1320248703);
         classUL var4 = classQE.method9549(var1, (byte)124);
         classUL var5 = classED.method3849(var4, var3, 1384267445);
         var5.method11454(var1, 1591236864);
         method11802(this, var5.field6322, var5.field6323, var5.field6324, 1697852429);
         classUL.method11416(var3, 846791916);
         classUL.method11416(var4, -1927076050);
         classUL.method11416(var5, -1744029903);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "uz.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ac")
   public void method11797(int var1) {
      try {
         synchronized (field6428) {
            if (-1124722655 * field6433 < -2140716621 * field6427) {
               if (var1 <= 1658407667) {
                  throw new IllegalStateException();
               }

               field6428[(field6433 += 801074145) * -1124722655 - 1] = this;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uz.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFF)Luz;")
   @ObfuscatedName("hi")
   public static classUZ method11860(float var0, float var1, float var2) {
      return classIK.method6261(var0, var1, var2, (byte)-64);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFF)Luz;")
   @ObfuscatedName("bn")
   public static classUZ method11785(float var0, float var1, float var2) {
      synchronized (field6428) {
         if (-1124722655 * field6433 == 0) {
            return new classUZ(var0, var1, var2);
         } else {
            method11802(field6428[(field6433 -= 801074145) * -1124722655], var0, var1, var2, 2140828537);
            return field6428[-1124722655 * field6433];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method11854() {
      return this.field6425 + ", " + this.field6423 + ", " + this.field6424;
   }

   public classUZ() {
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ay")
   public final boolean method11816(byte var1) {
      try {
         boolean var10000;
         label59: {
            float var3 = this.field6425;
            if (var3 < 9.765625E-4F) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               if (var3 > -9.765625E-4F) {
                  if (var1 >= 0) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
                  break label59;
               }
            }

            var10000 = false;
         }

         boolean var2 = var10000;
         float var6 = this.field6423;
         if (var6 < 9.765625E-4F && var6 > -9.765625E-4F) {
            if (var1 >= 0) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var7;
         label48: {
            boolean var5 = var10000;
            var7 = var2 & var5;
            float var9 = this.field6424;
            if (var9 < 9.765625E-4F) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               if (var9 > -9.765625E-4F) {
                  if (var1 >= 0) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
                  break label48;
               }
            }

            var10000 = false;
         }

         boolean var8 = var10000;
         return var7 & var8;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "uz.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cr")
   public final void method11818() {
      float var1 = 1.0F / this.method11844(-1808401369);
      this.field6425 *= var1;
      this.field6423 *= var1;
      this.field6424 *= var1;
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("co")
   public void method11800(float var1, float var2, float var3) {
      this.field6425 = var1;
      this.field6423 = var2;
      this.field6424 = var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFF)Luz;")
   @ObfuscatedName("ah")
   public static classUZ method11786(float var0, float var1, float var2) {
      synchronized (field6428) {
         if (1659307323 * field6433 == 0) {
            return new classUZ(var0, var1, var2);
         } else {
            method11802(field6428[(field6433 -= -705569252) * -1124722655], var0, var1, var2, 1833575835);
            return field6428[-1124722655 * field6433];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;FB)V")
   @ObfuscatedName("au")
   public final void method11827(classUZ var1, float var2, byte var3) {
      try {
         this.field6425 = this.field6425 + var1.field6425 * var2;
         this.field6423 = this.field6423 + var2 * var1.field6423;
         this.field6424 = this.field6424 + var2 * var1.field6424;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uz.au(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;)Luz;")
   @ObfuscatedName("bx")
   public static classUZ method11787(classUZ var0) {
      synchronized (field6428) {
         if (0 == -1124722655 * field6433) {
            return new classUZ(var0);
         } else {
            field6428[(field6433 -= 998381365) * -1124722655].method11804(var0, 138131447);
            return field6428[-1492309671 * field6433];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lai;)I")
   @ObfuscatedName("hi")
   public static int method11782(classAI var0) {
      return -619533787 * var0.field160;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)Luz;")
   @ObfuscatedName("bc")
   public static final classUZ method11788(classUZ var0, classUZ var1) {
      classUZ var2 = WorldMapData.method5868(var0, (byte)2);
      var2.method11834(var1, -291135846);
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)Luz;")
   @ObfuscatedName("bp")
   public static final classUZ method11789(classUZ var0, classUZ var1) {
      classUZ var2 = WorldMapData.method5868(var0, (byte)-74);
      var2.method11834(var1, -291135846);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Luu;Luu;B)V")
   @ObfuscatedName("tk")
   public static void method11857(classUU var0, classUU var1, byte var2) {
      if (var0 == null) {
         var0.method11710(var0, var2);
      }

      var0.field6383 = var1.field6383;
      var0.field6398 = var1.field6398;
      var0.field6394 = var1.field6394;
      var0.field6390 = var1.field6390;
      var0.field6392 = var1.field6392;
      var0.field6384 = var1.field6384;
      var0.field6396 = var1.field6396;
      var0.field6393 = var1.field6393;
      var0.field6388 = var1.field6388;
      var0.field6389 = var1.field6389;
      var0.field6385 = var1.field6385;
      var0.field6397 = var1.field6397;
      var0.field6391 = var1.field6391;
      var0.field6399 = var1.field6399;
      var0.field6395 = var1.field6395;
      var0.field6386 = var1.field6386;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)Luz;")
   @ObfuscatedName("br")
   public static final classUZ method11790(classUZ var0, classUZ var1) {
      classUZ var2 = WorldMapData.method5868(var0, (byte)-84);
      var2.method11834(var1, -291135846);
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)F")
   @ObfuscatedName("bm")
   public static final float method11791(classUZ var0, classUZ var1) {
      return method11835(var0, var1, (short)10641);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)F")
   @ObfuscatedName("bt")
   public static final float method11792(classUZ var0, classUZ var1) {
      return method11835(var0, var1, (short)-16238);
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cb")
   public final void method11821(classUZ var1) {
      this.field6425 = this.field6425 + var1.field6425;
      this.field6423 = this.field6423 + var1.field6423;
      this.field6424 = this.field6424 + var1.field6424;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Lul;)V")
   @ObfuscatedName("wd")
   public static void method11850(classUZ var0, classUL var1) {
      classUL var2 = classQQ.method9759(var0.field6425, var0.field6423, var0.field6424, 0.0F, -2119413410);
      classUL var3 = classQE.method9549(var1, (byte)112);
      classUL var4 = classED.method3849(var3, var2, 42710306);
      var4.method11454(var1, 1591236864);
      method11802(var0, var4.field6322, var4.field6323, var4.field6324, 1942084358);
      classUL.method11416(var2, -245247090);
      classUL.method11416(var3, -922479729);
      classUL.method11416(var4, -559441743);
   }

   @ObfuscatedSignature(descriptor = "(Luz;Luz;B)Z")
   @ObfuscatedName("lu")
   public static boolean method11813(classUZ var0, classUZ var1, byte var2) {
      if (var0 == null) {
         return var0.method11815(var0, var2);
      } else {
         try {
            if (var0.field6425 == var1.field6425) {
               if (var2 == 0) {
                  throw new IllegalStateException();
               }

               if (var1.field6423 == var0.field6423) {
                  if (var2 == 0) {
                     throw new IllegalStateException();
                  }

                  if (var1.field6424 == var0.field6424) {
                     return true;
                  }
               }
            }

            return false;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "uz.aj(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)Luz;")
   @ObfuscatedName("bj")
   public static final classUZ method11793(classUZ var0, classUZ var1) {
      classUZ var2 = WorldMapData.method5868(var0, (byte)50);
      var2.method11839(var1, -985130897);
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)Luz;")
   @ObfuscatedName("bk")
   public static final classUZ method11794(classUZ var0, classUZ var1) {
      classUZ var2 = WorldMapData.method5868(var0, (byte)-20);
      var2.method11839(var1, -1264866644);
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;F)Luz;")
   @ObfuscatedName("bo")
   public static final classUZ method11796(classUZ var0, float var1) {
      classUZ var2 = WorldMapData.method5868(var0, (byte)-22);
      var2.method11846(var1, 1904316259);
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)Luz;")
   @ObfuscatedName("bg")
   public static final classUZ method11795(classUZ var0, classUZ var1) {
      classUZ var2 = WorldMapData.method5868(var0, (byte)-35);
      var2.method11839(var1, -1780703631);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bv")
   final void method11810() {
      this.field6424 = 0.0F;
      this.field6423 = 0.0F;
      this.field6425 = 0.0F;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Lul;)V")
   @ObfuscatedName("rv")
   public static void method11851(classUZ var0, classUL var1) {
      if (var0 == null) {
         var0.method11852(var1);
      } else {
         classUL var2 = classQQ.method9759(var0.field6425, var0.field6423, var0.field6424, 0.0F, -750862988);
         classUL var3 = classQE.method9549(var1, (byte)65);
         classUL var4 = classED.method3849(var3, var2, 1237511684);
         var4.method11454(var1, 1591236864);
         method11802(var0, var4.field6322, var4.field6323, var4.field6324, 1714710211);
         classUL.method11416(var2, 512029697);
         classUL.method11416(var3, 1552780190);
         classUL.method11416(var4, 991154170);
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cf")
   final void method11840(classUZ var1) {
      method11802(
         this,
         var1.field6424 * this.field6423 - this.field6424 * var1.field6423,
         this.field6424 * var1.field6425 - var1.field6424 * this.field6425,
         this.field6425 * var1.field6423 - this.field6423 * var1.field6425,
         1899156015
      );
   }

   @ObfuscatedSignature(descriptor = "(Luz;I)V")
   @ObfuscatedName("ad")
   public final void method11822(classUZ var1, int var2) {
      try {
         this.field6425 = this.field6425 + var1.field6425;
         this.field6423 = this.field6423 + var1.field6423;
         this.field6424 = this.field6424 + var1.field6424;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uz.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("by")
   void method11805(classUZ var1) {
      method11802(this, var1.field6425, var1.field6423, var1.field6424, 1642542008);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cm")
   public static void method11798(classUZ var0) {
      if (var0 == null) {
         var0.toString();
      }

      synchronized (field6428) {
         if (-1124722655 * field6433 < -2140716621 * field6427) {
            field6428[(field6433 += 801074145) * -1124722655 - 1] = var0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("bq")
   void method11806(classUZ var1) {
      method11802(this, var1.field6425, var1.field6423, var1.field6424, 2085765392);
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("bf")
   void method11807(classUZ var1) {
      method11802(this, var1.field6425, var1.field6423, var1.field6424, 1870058782);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   final void method11811() {
      this.field6424 = 0.0F;
      this.field6423 = 0.0F;
      this.field6425 = 0.0F;
   }

   @Override
   public String toString() {
      try {
         return this.field6425 + ", " + this.field6423 + ", " + this.field6424;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "uz.toString(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("bb")
   public void method11801(float var1, float var2, float var3) {
      this.field6425 = var1;
      this.field6423 = var2;
      this.field6424 = var3;
   }

   @ObfuscatedSignature(descriptor = "(Luz;)Z")
   @ObfuscatedName("bh")
   public boolean method11814(classUZ var1) {
      return this.field6425 == var1.field6425 && var1.field6423 == this.field6423 && var1.field6424 == this.field6424;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Luz;")
   @ObfuscatedName("an")
   public static classUZ method11783() {
      synchronized (field6428) {
         if (929647618 * field6433 == 0) {
            return new classUZ();
         } else {
            field6428[(field6433 -= -337739282) * -1543405511].method11809(-1792024384);
            return field6428[-402165147 * field6433];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cu")
   public final void method11819() {
      float var1 = 1.0F / this.method11844(-1808401369);
      this.field6425 *= var1;
      this.field6423 *= var1;
      this.field6424 *= var1;
   }

   @ObfuscatedSignature(descriptor = "(Luz;Luz;S)F")
   @ObfuscatedName("sv")
   public static float method11835(classUZ var0, classUZ var1, short var2) {
      if (var0 == null) {
         var0.method11838(var0, var2);
      }

      try {
         return var1.field6424 * var0.field6424 + (var0.field6425 * var1.field6425 + var1.field6423 * var0.field6423);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uz.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gi")
   public String method11855() {
      return this.field6425 + ", " + this.field6423 + ", " + this.field6424;
   }

   @ObfuscatedSignature(descriptor = "(Luz;FFFI)V")
   @ObfuscatedName("lq")
   public static void method11802(classUZ var0, float var1, float var2, float var3, int var4) {
      if (var0 == null) {
         var0.method11826(var1, var1, var1, var4);
      }

      try {
         var0.field6425 = var1;
         var0.field6423 = var2;
         var0.field6424 = var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uz.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("ct")
   public final void method11825(float var1, float var2, float var3) {
      this.field6425 += var1;
      this.field6423 += var2;
      this.field6424 += var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)V")
   @ObfuscatedName("xi")
   public static void method11829(classUZ var0, classUZ var1) {
      if (var0 == null) {
         var0.method11819();
      }

      var0.field6425 = var0.field6425 - var1.field6425;
      var0.field6423 = var0.field6423 - var1.field6423;
      var0.field6424 = var0.field6424 - var1.field6424;
   }

   @ObfuscatedSignature(descriptor = "(Luz;)F")
   @ObfuscatedName("co")
   final float method11836(classUZ var1) {
      return var1.field6424 * this.field6424 + (this.field6425 * var1.field6425 + var1.field6423 * this.field6423);
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cl")
   final void method11830(classUZ var1) {
      this.field6425 = this.field6425 - var1.field6425;
      this.field6423 = this.field6423 - var1.field6423;
      this.field6424 = this.field6424 - var1.field6424;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("oy")
   public static void method11799(classUZ var0) {
      if (var0 == null) {
         var0.method11820();
      }

      synchronized (field6428) {
         if (-423594961 * field6433 < -341335151 * field6427) {
            field6428[(field6433 += -2112947797) * -1124722655 - 1] = var0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cd")
   final void method11831(classUZ var1) {
      this.field6425 = this.field6425 - var1.field6425;
      this.field6423 = this.field6423 - var1.field6423;
      this.field6424 = this.field6424 - var1.field6424;
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cv")
   final void method11832(classUZ var1) {
      this.field6425 = this.field6425 - var1.field6425;
      this.field6423 = this.field6423 - var1.field6423;
      this.field6424 = this.field6424 - var1.field6424;
   }

   @ObfuscatedSignature(descriptor = "(Luz;F)V")
   @ObfuscatedName("cq")
   public final void method11828(classUZ var1, float var2) {
      this.field6425 = this.field6425 + var1.field6425 * var2;
      this.field6423 = this.field6423 + var2 * var1.field6423;
      this.field6424 = this.field6424 + var2 * var1.field6424;
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cy")
   final void method11833(classUZ var1) {
      this.field6425 = this.field6425 - var1.field6425;
      this.field6423 = this.field6423 - var1.field6423;
      this.field6424 = this.field6424 - var1.field6424;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)I")
   @ObfuscatedName("eo")
   public static int method11856(ClientPreferences var0) {
      return var0.field1352 * -1752603289;
   }

   @ObfuscatedSignature(descriptor = "(Luz;)F")
   @ObfuscatedName("ck")
   final float method11837(classUZ var1) {
      return var1.field6424 * this.field6424 + (this.field6425 * var1.field6425 + var1.field6423 * this.field6423);
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("bw")
   public void method11803(float var1, float var2, float var3) {
      this.field6425 = var1;
      this.field6423 = var2;
      this.field6424 = var3;
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("ca")
   final void method11841(classUZ var1) {
      method11802(
         this,
         var1.field6424 * this.field6423 - this.field6424 * var1.field6423,
         this.field6424 * var1.field6425 - var1.field6424 * this.field6425,
         this.field6425 * var1.field6423 - this.field6423 * var1.field6425,
         1664425080
      );
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cc")
   final void method11842(classUZ var1) {
      method11802(
         this,
         var1.field6424 * this.field6423 - this.field6424 * var1.field6423,
         this.field6424 * var1.field6425 - var1.field6424 * this.field6425,
         this.field6425 * var1.field6423 - this.field6423 * var1.field6425,
         1704323527
      );
   }

   @ObfuscatedSignature(descriptor = "(Luz;I)V")
   @ObfuscatedName("aw")
   final void method11834(classUZ var1, int var2) {
      try {
         this.field6425 = this.field6425 - var1.field6425;
         this.field6423 = this.field6423 - var1.field6423;
         this.field6424 = this.field6424 - var1.field6424;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uz.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cn")
   final void method11843(classUZ var1) {
      method11802(
         this,
         var1.field6424 * this.field6423 - this.field6424 * var1.field6423,
         this.field6424 * var1.field6425 - var1.field6424 * this.field6425,
         this.field6425 * var1.field6423 - this.field6423 * var1.field6425,
         1787747073
      );
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("ch")
   public final float method11845() {
      return (float)Math.sqrt(this.field6424 * this.field6424 + (this.field6423 * this.field6423 + this.field6425 * this.field6425));
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("cg")
   final void method11847(float var1) {
      this.field6425 *= var1;
      this.field6423 *= var1;
      this.field6424 *= var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("cz")
   public static void method11812(classUZ var0) {
      if (var0 == null) {
         var0.method11811();
      }

      var0.field6424 = 0.0F;
      var0.field6423 = 0.0F;
      var0.field6425 = 0.0F;
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("cw")
   final void method11848(float var1) {
      this.field6425 *= var1;
      this.field6423 *= var1;
      this.field6424 *= var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ba")
   public void method11820() {
      float var1 = 1.0F / this.method11844(-1808401369);
      this.field6425 = this.field6423 * var1;
      this.field6423 = this.field6425 * var1;
      this.field6424 = this.field6423 * var1;
   }

   @ObfuscatedSignature(descriptor = "(FFFI)V")
   @ObfuscatedName("aa")
   public void method11826(float var1, float var2, float var3, int var4) {
      try {
         this.field6424 = this.field6423 + var1;
         this.field6423 = this.field6425 + var2;
         this.field6423 = this.field6425 + var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uz.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fa")
   public void method11861() {
      this.method11797(2094341737);
   }

   @ObfuscatedSignature(descriptor = "(Luz;B)Z")
   @ObfuscatedName("aj")
   public boolean method11815(classUZ var1, byte var2) {
      try {
         if (this.field6423 == var1.field6423) {
            if (var2 == 0) {
               throw new IllegalStateException();
            }

            if (var1.field6423 == this.field6425) {
               if (var2 == 0) {
                  throw new IllegalStateException();
               }

               if (var1.field6425 == this.field6423) {
                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uz.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("kr")
   public void method11862(classUL var1) {
      this.method11849(var1, -1804299098);
   }

   @ObfuscatedSignature(descriptor = "(Luz;S)F")
   @ObfuscatedName("ar")
   final float method11838(classUZ var1, short var2) {
      try {
         return var1.field6424 * this.field6424 + (this.field6424 * var1.field6423 + var1.field6423 * this.field6423);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uz.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("cj")
   public final void method11852(classUL var1) {
      classUL var2 = classQQ.method9759(this.field6424, this.field6424, this.field6425, 0.0F, -750862988);
      classUL var3 = classQE.method9549(var1, (byte)65);
      classUL var4 = classED.method3849(var3, var2, 1237511684);
      var4.method11454(var1, 1591236864);
      method11802(this, var4.field6323, var4.field6325, var4.field6325, 1714710211);
      classUL.method11416(var2, 512029697);
      classUL.method11416(var3, 1552780190);
      classUL.method11416(var4, 991154170);
   }
}
