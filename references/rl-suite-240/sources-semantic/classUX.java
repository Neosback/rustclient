import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ux")
public class classUX {
   @ObfuscatedName("ac")
   float field6411;
   @ObfuscatedName("az")
   float field6420;
   @ObfuscatedSignature(descriptor = "Lux;")
   @ObfuscatedName("ke")
   public static classUX field6408 = new classUX();
   @ObfuscatedName("ae")
   float field6412;
   @ObfuscatedName("as")
   float field6418;
   @ObfuscatedName("ag")
   float field6414;
   @ObfuscatedName("ab")
   float field6410;
   @ObfuscatedName("af")
   float field6413;
   @ObfuscatedName("ao")
   float field6417;
   @ObfuscatedName("aa")
   float field6409;
   @ObfuscatedName("ax")
   float field6419;
   @ObfuscatedName("al")
   float field6416;
   @ObfuscatedName("aj")
   float field6415;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   public static final int field6421 = 45;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;)I")
   @ObfuscatedName("tw")
   public static int method11766(WorldEntity var0) {
      return var0.field6010.field1690 * 377427776 + var0.field6024.method8511((byte)-17);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   void method11744(int var1) {
      try {
         this.field6415 = 0.0F;
         this.field6416 = 0.0F;
         this.field6417 = 0.0F;
         this.field6411 = 0.0F;
         this.field6419 = 0.0F;
         this.field6418 = 0.0F;
         this.field6410 = 0.0F;
         this.field6412 = 0.0F;
         this.field6413 = 0.0F;
         this.field6409 = 1.0F;
         this.field6414 = 1.0F;
         this.field6420 = 1.0F;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ux.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcl;)I")
   @ObfuscatedName("yt")
   public static int method11759(classCL var0) {
      return var0.field779 * 165613146;
   }

   @ObfuscatedSignature(descriptor = "(Lux;FI)V")
   @ObfuscatedName("yl")
   public static void method11754(classUX var0, float var1, int var2) {
      if (var0 == null) {
         var0.method11756(var1, var2);
      } else {
         try {
            float var3 = (float)Math.cos(var1);
            float var4 = (float)Math.sin(var1);
            float var5 = var0.field6420;
            float var6 = var0.field6410;
            float var7 = var0.field6419;
            float var8 = var0.field6417;
            var0.field6420 = var3 * var5 - var4 * var0.field6413;
            var0.field6413 = var0.field6413 * var3 + var4 * var5;
            var0.field6410 = var3 * var6 - var4 * var0.field6414;
            var0.field6414 = var4 * var6 + var0.field6414 * var3;
            var0.field6419 = var3 * var7 - var0.field6411 * var4;
            var0.field6411 = var0.field6411 * var3 + var7 * var4;
            var0.field6417 = var8 * var3 - var4 * var0.field6416;
            var0.field6416 = var3 * var0.field6416 + var4 * var8;
         } catch (RuntimeException var9) {
            throw classEG.method3899(var9, "ux.ab(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("ao")
   public void method11751(float var1) {
      float var2 = (float)Math.cos(var1);
      float var3 = (float)Math.sin(var1);
      float var4 = this.field6420;
      float var5 = this.field6410;
      float var6 = this.field6419;
      float var7 = this.field6417;
      this.field6420 = var3 * this.field6412 + var4 * var2;
      this.field6412 = this.field6412 * var2 - var3 * var4;
      this.field6410 = var2 * var5 + this.field6418 * var3;
      this.field6418 = var2 * this.field6418 - var5 * var3;
      this.field6419 = var2 * var6 + var3 * this.field6409;
      this.field6409 = this.field6409 * var2 - var3 * var6;
      this.field6417 = this.field6415 * var3 + var7 * var2;
      this.field6415 = var2 * this.field6415 - var7 * var3;
   }

   @ObfuscatedSignature(descriptor = "(FI)V")
   @ObfuscatedName("ae")
   public void method11748(float var1, int var2) {
      try {
         float var3 = (float)Math.cos(var1);
         float var4 = (float)Math.sin(var1);
         float var5 = this.field6413;
         float var6 = this.field6414;
         float var7 = this.field6411;
         float var8 = this.field6416;
         this.field6413 = var3 * var5 - this.field6412 * var4;
         this.field6412 = var4 * var5 + var3 * this.field6412;
         this.field6414 = var6 * var3 - var4 * this.field6418;
         this.field6418 = var6 * var4 + this.field6418 * var3;
         this.field6411 = var3 * var7 - var4 * this.field6409;
         this.field6409 = var4 * var7 + this.field6409 * var3;
         this.field6416 = var3 * var8 - this.field6415 * var4;
         this.field6415 = var3 * this.field6415 + var4 * var8;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ux.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gi")
   public String method11763() {
      return this.field6420
         + ","
         + this.field6410
         + ","
         + this.field6419
         + ","
         + this.field6417
         + "\n"
         + this.field6413
         + ","
         + this.field6414
         + ","
         + this.field6411
         + ","
         + this.field6416
         + "\n"
         + this.field6412
         + ","
         + this.field6418
         + ","
         + this.field6409
         + ","
         + this.field6415;
   }

   public classUX() {
      this.method11744(2000712555);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method11764() {
      return this.field6420
         + ","
         + this.field6410
         + ","
         + this.field6419
         + ","
         + this.field6417
         + "\n"
         + this.field6413
         + ","
         + this.field6414
         + ","
         + this.field6411
         + ","
         + this.field6416
         + "\n"
         + this.field6412
         + ","
         + this.field6418
         + ","
         + this.field6409
         + ","
         + this.field6415;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   void method11745() {
      this.field6415 = 0.0F;
      this.field6416 = 0.0F;
      this.field6417 = 0.0F;
      this.field6411 = 0.0F;
      this.field6419 = 0.0F;
      this.field6418 = 0.0F;
      this.field6410 = 0.0F;
      this.field6412 = 0.0F;
      this.field6413 = 0.0F;
      this.field6409 = 1.0F;
      this.field6414 = 1.0F;
      this.field6420 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("vk")
   public void method11768(float var1, float var2, float var3) {
      method11760(this, var1, var2, var3, (byte)105);
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ae")
   static final boolean method11767(int var0, byte var1) {
      try {
         if (var0 != 7) {
            if (var1 != 3) {
               throw new IllegalStateException();
            }

            if (8 != var0) {
               if (var0 < 9) {
                  return false;
               }

               if (var1 != 3) {
                  throw new IllegalStateException();
               }

               if (var0 > 13) {
                  return false;
               }

               if (var1 != 3) {
                  throw new IllegalStateException();
               }
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ux.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("aa")
   public void method11749(float var1) {
      float var2 = (float)Math.cos(var1);
      float var3 = (float)Math.sin(var1);
      float var4 = this.field6413;
      float var5 = this.field6414;
      float var6 = this.field6411;
      float var7 = this.field6416;
      this.field6413 = var2 * var4 - this.field6412 * var3;
      this.field6412 = var3 * var4 + var2 * this.field6412;
      this.field6414 = var5 * var2 - var3 * this.field6418;
      this.field6418 = var5 * var3 + this.field6418 * var2;
      this.field6411 = var2 * var6 - var3 * this.field6409;
      this.field6409 = var3 * var6 + this.field6409 * var2;
      this.field6416 = var2 * var7 - this.field6415 * var3;
      this.field6415 = var2 * this.field6415 + var3 * var7;
   }

   @ObfuscatedSignature(descriptor = "(Lux;FFFB)V")
   @ObfuscatedName("tl")
   public static void method11760(classUX var0, float var1, float var2, float var3, byte var4) {
      if (var0 == null) {
         var0.method11762(var1, var1, var1, var4);
      }

      try {
         var0.field6417 += var1;
         var0.field6416 += var2;
         var0.field6415 += var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ux.ag(" + ')');
      }
   }

   static {
      new classUX();
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("al")
   public void method11752(float var1) {
      float var2 = (float)Math.cos(var1);
      float var3 = (float)Math.sin(var1);
      float var4 = this.field6420;
      float var5 = this.field6410;
      float var6 = this.field6419;
      float var7 = this.field6417;
      this.field6420 = var3 * this.field6412 + var4 * var2;
      this.field6412 = this.field6412 * var2 - var3 * var4;
      this.field6410 = var2 * var5 + this.field6418 * var3;
      this.field6418 = var2 * this.field6418 - var5 * var3;
      this.field6419 = var2 * var6 + var3 * this.field6409;
      this.field6409 = this.field6409 * var2 - var3 * var6;
      this.field6417 = this.field6415 * var3 + var7 * var2;
      this.field6415 = var2 * this.field6415 - var7 * var3;
   }

   @Override
   public String toString() {
      try {
         return this.field6420
            + ","
            + this.field6410
            + ","
            + this.field6419
            + ","
            + this.field6417
            + "\n"
            + this.field6413
            + ","
            + this.field6414
            + ","
            + this.field6411
            + ","
            + this.field6416
            + "\n"
            + this.field6412
            + ","
            + this.field6418
            + ","
            + this.field6409
            + ","
            + this.field6415;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ux.toString(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("zh")
   public void method11769(float var1) {
      this.method11748(var1, 173632492);
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("ac")
   public void method11750(float var1) {
      float var2 = (float)Math.cos(var1);
      float var3 = (float)Math.sin(var1);
      float var4 = this.field6413;
      float var5 = this.field6414;
      float var6 = this.field6411;
      float var7 = this.field6416;
      this.field6413 = var2 * var4 - this.field6412 * var3;
      this.field6412 = var3 * var4 + var2 * this.field6412;
      this.field6414 = var5 * var2 - var3 * this.field6418;
      this.field6418 = var5 * var3 + this.field6418 * var2;
      this.field6411 = var2 * var6 - var3 * this.field6409;
      this.field6409 = var3 * var6 + this.field6409 * var2;
      this.field6416 = var2 * var7 - this.field6415 * var3;
      this.field6415 = var2 * this.field6415 + var3 * var7;
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("aq")
   void method11755(float var1) {
      float var2 = (float)Math.cos(var1);
      float var3 = (float)Math.sin(var1);
      float var4 = this.field6420;
      float var5 = this.field6410;
      float var6 = this.field6419;
      float var7 = this.field6417;
      this.field6420 = var2 * var4 - var3 * this.field6413;
      this.field6413 = this.field6413 * var2 + var3 * var4;
      this.field6410 = var2 * var5 - var3 * this.field6414;
      this.field6414 = var3 * var5 + this.field6414 * var2;
      this.field6419 = var2 * var6 - this.field6411 * var3;
      this.field6411 = this.field6411 * var2 + var6 * var3;
      this.field6417 = var7 * var2 - var3 * this.field6416;
      this.field6416 = var2 * this.field6416 + var3 * var7;
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("nd")
   public void method11770(float var1) {
      method11754(this, var1, -2028165068);
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("ad")
   public void method11761(float var1, float var2, float var3) {
      this.field6417 += var1;
      this.field6416 += var2;
      this.field6415 += var3;
   }

   @ObfuscatedSignature(descriptor = "(FI)V")
   @ObfuscatedName("af")
   public void method11753(float var1, int var2) {
      try {
         float var3 = (float)Math.cos(var1);
         float var4 = (float)Math.sin(var1);
         float var5 = this.field6420;
         float var6 = this.field6410;
         float var7 = this.field6419;
         float var8 = this.field6417;
         this.field6420 = var4 * this.field6412 + var5 * var3;
         this.field6412 = this.field6412 * var3 - var4 * var5;
         this.field6410 = var3 * var6 + this.field6418 * var4;
         this.field6418 = var3 * this.field6418 - var6 * var4;
         this.field6419 = var3 * var7 + var4 * this.field6409;
         this.field6409 = this.field6409 * var3 - var4 * var7;
         this.field6417 = this.field6415 * var4 + var8 * var3;
         this.field6415 = var3 * this.field6415 - var8 * var4;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ux.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lux;)Ljava/lang/String;")
   @ObfuscatedName("wh")
   public static String method11765(classUX var0) {
      if (var0 == null) {
         var0.toString();
      }

      return var0.field6420
         + ","
         + var0.field6410
         + ","
         + var0.field6419
         + ","
         + var0.field6417
         + "\n"
         + var0.field6413
         + ","
         + var0.field6414
         + ","
         + var0.field6411
         + ","
         + var0.field6416
         + "\n"
         + var0.field6412
         + ","
         + var0.field6418
         + ","
         + var0.field6409
         + ","
         + var0.field6415;
   }

   @ObfuscatedSignature(descriptor = "(FI)V")
   @ObfuscatedName("ab")
   void method11756(float var1, int var2) {
      try {
         float var3 = (float)Math.cos(var1);
         float var4 = (float)Math.sin(var1);
         float var5 = this.field6411;
         float var6 = this.field6409;
         float var7 = this.field6417;
         float var8 = this.field6418;
         this.field6411 = var3 * var5 - var4 * this.field6419;
         this.field6419 = this.field6419 * var3 + var4 * var5;
         this.field6411 = var3 * var6 - var4 * this.field6411;
         this.field6410 = var4 * var6 + this.field6414 * var3;
         this.field6410 = var3 * var7 - this.field6417 * var4;
         this.field6414 = this.field6415 * var3 + var7 * var4;
         this.field6410 = var8 * var3 - var4 * this.field6414;
         this.field6409 = var3 * this.field6412 + var4 * var8;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ux.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("rg")
   public void method11771() {
      this.method11744(2000712555);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lux;F)V")
   @ObfuscatedName("mb")
   public static void method11757(classUX var0, float var1) {
      if (var0 == null) {
         var0.method11761(var1, var1, var1);
      }

      float var2 = (float)Math.cos(var1);
      float var3 = (float)Math.sin(var1);
      float var4 = var0.field6420;
      float var5 = var0.field6410;
      float var6 = var0.field6419;
      float var7 = var0.field6417;
      var0.field6420 = var2 * var4 - var3 * var0.field6413;
      var0.field6413 = var0.field6413 * var2 + var3 * var4;
      var0.field6410 = var2 * var5 - var3 * var0.field6414;
      var0.field6414 = var3 * var5 + var0.field6414 * var2;
      var0.field6419 = var2 * var6 - var0.field6411 * var3;
      var0.field6411 = var0.field6411 * var2 + var6 * var3;
      var0.field6417 = var7 * var2 - var3 * var0.field6416;
      var0.field6416 = var2 * var0.field6416 + var3 * var7;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lux;F)V")
   @ObfuscatedName("dq")
   public static void method11758(classUX var0, float var1) {
      if (var0 == null) {
         var0.method11772(var1);
      }

      float var2 = (float)Math.cos(var1);
      float var3 = (float)Math.sin(var1);
      float var4 = var0.field6420;
      float var5 = var0.field6410;
      float var6 = var0.field6419;
      float var7 = var0.field6417;
      var0.field6420 = var2 * var4 - var3 * var0.field6413;
      var0.field6413 = var0.field6413 * var2 + var3 * var4;
      var0.field6410 = var2 * var5 - var3 * var0.field6414;
      var0.field6414 = var3 * var5 + var0.field6414 * var2;
      var0.field6419 = var2 * var6 - var0.field6411 * var3;
      var0.field6411 = var0.field6411 * var2 + var6 * var3;
      var0.field6417 = var7 * var2 - var3 * var0.field6416;
      var0.field6416 = var2 * var0.field6416 + var3 * var7;
   }

   @ObfuscatedSignature(descriptor = "(FFFB)V")
   @ObfuscatedName("ag")
   public void method11762(float var1, float var2, float var3, byte var4) {
      try {
         this.field6413 = this.field6414 + var1;
         this.field6410 = this.field6415 + var2;
         this.field6414 = this.field6417 + var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ux.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("ou")
   public void method11772(float var1) {
      this.method11753(var1, 982132619);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lux;)V")
   @ObfuscatedName("ix")
   public static void method11746(classUX var0) {
      if (var0 == null) {
         var0.method11747();
      }

      var0.field6415 = 0.0F;
      var0.field6416 = 0.0F;
      var0.field6417 = 0.0F;
      var0.field6411 = 0.0F;
      var0.field6419 = 0.0F;
      var0.field6418 = 0.0F;
      var0.field6410 = 0.0F;
      var0.field6412 = 0.0F;
      var0.field6413 = 0.0F;
      var0.field6409 = 1.0F;
      var0.field6414 = 1.0F;
      var0.field6420 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   void method11747() {
      this.field6413 = 0.0F;
      this.field6416 = 0.0F;
      this.field6416 = 0.0F;
      this.field6420 = 0.0F;
      this.field6414 = 0.0F;
      this.field6409 = 0.0F;
      this.field6416 = 0.0F;
      this.field6414 = 0.0F;
      this.field6420 = 0.0F;
      this.field6409 = 1.0F;
      this.field6412 = 1.0F;
      this.field6414 = 1.0F;
   }
}
