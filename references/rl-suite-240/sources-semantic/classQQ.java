import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qq")
public class classQQ implements classXZ {
   @ObfuscatedSignature(descriptor = "Lqq;")
   @ObfuscatedName("ab")
   static final classQQ field5588 = new classQQ(3, "PATCH", false, true);
   @ObfuscatedSignature(descriptor = "Lqq;")
   @ObfuscatedName("af")
   public static final classQQ field5586 = new classQQ(1, "GET", true, false);
   @ObfuscatedSignature(descriptor = "Lqq;")
   @ObfuscatedName("ae")
   static final classQQ field5587 = new classQQ(2, "PUT", false, true);
   @ObfuscatedName("aa")
   boolean field5593;
   @ObfuscatedSignature(descriptor = "Lqq;")
   @ObfuscatedName("ag")
   static final classQQ field5589 = new classQQ(4, "DELETE", false, true);
   @ObfuscatedName("as")
   int field5590;
   @ObfuscatedName("ax")
   String field5591;
   @ObfuscatedName("ac")
   boolean field5592;
   @ObfuscatedSignature(descriptor = "Lqq;")
   @ObfuscatedName("az")
   public static final classQQ field5585 = new classQQ(0, "POST", true, true);
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final int field5594 = 4;
   @ObfuscatedSignature(descriptor = "Lbw;")
   @ObfuscatedName("fx")
   public static classBW field5595;

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field5590 * 2142977363;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qq.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("al")
   public String method9753() {
      return this.field5591;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("as")
   public String method9754(int var1) {
      try {
         return this.field5591;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qq.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ax")
   boolean method9758(int var1) {
      try {
         return this.field5593;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qq.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ac")
   boolean method9748() {
      return this.field5592;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field5590 * 2142977363;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field5590 * -409231248;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aa")
   boolean method9749() {
      return this.field5592;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ao")
   boolean method9750() {
      return this.field5592;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aj")
   public String method9755() {
      return this.field5591;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field5590 * 837162110;
   }

   @ObfuscatedSignature(descriptor = "(Lqq;I)Z")
   @ObfuscatedName("nw")
   public static boolean method9751(classQQ var0, int var1) {
      if (var0 == null) {
         return var0.method9752(var1);
      } else {
         try {
            return var0.field5592;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "qq.ag(" + ')');
         }
      }
   }

   classQQ(int var1, String var2, boolean var3, boolean var4) {
      this.field5590 = var1 * -441970981;
      this.field5591 = var2;
      this.field5592 = var3;
      this.field5593 = var4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqq;)Ljava/lang/String;")
   @ObfuscatedName("ab")
   public static String method9756(classQQ var0) {
      if (var0 == null) {
         var0.method9757();
      }

      return var0.field5591;
   }

   @ObfuscatedSignature(descriptor = "(FFFFI)Lul;")
   @ObfuscatedName("af")
   static classUL method9759(float var0, float var1, float var2, float var3, int var4) {
      try {
         synchronized (classUL.field6321) {
            if (classUL.field6319 * 1171203923 == 0) {
               if (var4 >= 120041229) {
                  throw new IllegalStateException();
               } else {
                  return new classUL(var0, var1, var2, var3);
               }
            } else {
               classUL.method11424(classUL.field6321[(classUL.field6319 -= -1712212773) * 1171203923], var0, var1, var2, var3, (byte)-20);
               return classUL.field6321[1171203923 * classUL.field6319];
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "qq.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyj;III)V")
   @ObfuscatedName("qk")
   public static void method9747(classYJ var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.vmethod368(var1);
      }

      var0.field6989 = classKY.method6656(var2, 559645505) * -1254167701;
      var0.field6991 = classKY.method6656(var3, 127772384) * -1343438351;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aq")
   public String method9757() {
      return this.field5591;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ag")
   boolean method9752(int var1) {
      try {
         return this.field5592;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qq.ag(" + ')');
      }
   }
}
