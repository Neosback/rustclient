import java.io.IOException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jl")
public class classJL extends Node {
   @ObfuscatedSignature(descriptor = "Lxs;")
   @ObfuscatedName("ac")
   public PacketBuffer field3341;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field3334 = 10000;
   @ObfuscatedSignature(descriptor = "[Ljl;")
   @ObfuscatedName("ab")
   static classJL[] field3331 = new classJL[300];
   @ObfuscatedName("ag")
   static int field3332 = 0;
   @ObfuscatedSignature(descriptor = "Ljs;")
   @ObfuscatedName("as")
   classJS field3340;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field3338 = 512;
   @ObfuscatedName("ax")
   int field3337;
   @ObfuscatedName("aa")
   public int field3333;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   public static final int field3335 = 39;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field3339 = 260;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field3336 = 24;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;I)V")
   @ObfuscatedName("wd")
   public static void method6423(NPC var0, int var1) {
      if (var0 == null) {
         var0.method3492();
      }

      var0.field1568 = 937220001 * var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public void method6427() {
      if (1275112637 * field3332 < field3331.length) {
         field3331[(field3332 += 766488213) * 1275112637 - 1] = this;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   public void method6428(int var1) {
      try {
         if (1275112637 * field3332 < field3331.length) {
            field3331[(field3332 += 766488213) * 1275112637 - 1] = this;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "jl.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljl;")
   @ObfuscatedName("ay")
   public static classJL method6424() {
      classJL var0 = SceneTilePaint.method4814(1410588756);
      var0.field3340 = null;
      var0.field3337 = 0;
      var0.field3341 = new PacketBuffer(5000);
      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljl;")
   @ObfuscatedName("as")
   static classJL method6417() {
      return 1275112637 * field3332 == 0 ? new classJL() : field3331[(field3332 -= 766488213) * 1275112637];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljs;Lyt;)Ljl;")
   @ObfuscatedName("ao")
   public static classJL method6421(classJS var0, classYT var1) {
      classJL var2 = SceneTilePaint.method4814(2109786031);
      var2.field3340 = var0;
      var2.field3337 = -1950310809 * var0.field3369;
      if (-1669214905 * var2.field3337 == -1) {
         var2.field3341 = new PacketBuffer(260);
      } else if (-1669214905 * var2.field3337 == -2) {
         var2.field3341 = new PacketBuffer(10000);
      } else if (var2.field3337 * -1669214905 <= 18) {
         var2.field3341 = new PacketBuffer(20);
      } else if (var2.field3337 * -1669214905 <= 98) {
         var2.field3341 = new PacketBuffer(100);
      } else {
         var2.field3341 = new PacketBuffer(260);
      }

      var2.field3341.method12959(var1, -867900324);
      var2.field3341.method12964(var2.field3340.field3370 * 484572505, (byte)3);
      var2.field3333 = 0;
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljl;")
   @ObfuscatedName("ac")
   static classJL method6418() {
      return 1275112637 * field3332 == 0 ? new classJL() : field3331[(field3332 -= 766488213) * 1275112637];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljs;Lyt;)Ljl;")
   @ObfuscatedName("aa")
   public static classJL method6422(classJS var0, classYT var1) {
      classJL var2 = SceneTilePaint.method4814(2128648648);
      var2.field3340 = var0;
      var2.field3337 = -1950310809 * var0.field3369;
      if (-1669214905 * var2.field3337 == -1) {
         var2.field3341 = new PacketBuffer(260);
      } else if (-1669214905 * var2.field3337 == -2) {
         var2.field3341 = new PacketBuffer(10000);
      } else if (var2.field3337 * -1669214905 <= 18) {
         var2.field3341 = new PacketBuffer(20);
      } else if (var2.field3337 * -1669214905 <= 98) {
         var2.field3341 = new PacketBuffer(100);
      } else {
         var2.field3341 = new PacketBuffer(260);
      }

      var2.field3341.method12959(var1, -1341633633);
      var2.field3341.method12964(var2.field3340.field3370 * 484572505, (byte)75);
      var2.field3333 = 0;
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljl;")
   @ObfuscatedName("aj")
   public static classJL method6425() {
      classJL var0 = SceneTilePaint.method4814(1619799152);
      var0.field3340 = null;
      var0.field3337 = 0;
      var0.field3341 = new PacketBuffer(5000);
      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljl;")
   @ObfuscatedName("al")
   public static classJL method6426() {
      classJL var0 = SceneTilePaint.method4814(1568205369);
      var0.field3340 = null;
      var0.field3337 = 0;
      var0.field3341 = new PacketBuffer(5000);
      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljl;")
   @ObfuscatedName("ag")
   static classJL method6419() {
      return 1275112637 * field3332 == 0 ? new classJL() : field3331[(field3332 -= 766488213) * 1275112637];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljl;")
   @ObfuscatedName("ax")
   static classJL method6420() {
      return 1275112637 * field3332 == 0 ? new classJL() : field3331[(field3332 -= 766488213) * 1479425096];
   }

   classJL() {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   public void method6429() {
      if (1275112637 * field3332 < field3331.length) {
         field3331[(field3332 += 766488213) * 1275112637 - 1] = this;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   public void method6430() {
      if (1275112637 * field3332 < field3331.length) {
         field3331[(field3332 += 766488213) * 1275112637 - 1] = this;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[B")
   @ObfuscatedName("ab")
   public static byte[] method6431(byte var0) {
      try {
         byte[] var1 = new byte[24];

         try {
            classME.field4488.method131(0L);
            classAAC.method140(classME.field4488, var1, 1861877066);

            int var2;
            for (var2 = 0; var2 < 24; var2++) {
               if (0 != var1[var2]) {
                  if (var0 != 0) {
                     throw new IllegalStateException();
                  }
                  break;
               }
            }

            if (var2 >= 24) {
               if (var0 != 0) {
                  throw new IllegalStateException();
               }

               throw new IOException();
            }
         } catch (Exception var4) {
            for (int var3 = 0; var3 < 24; var3++) {
               if (var0 != 0) {
                  throw new IllegalStateException();
               }

               var1[var3] = -1;
            }
         }

         return var1;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "jl.ab(" + ')');
      }
   }
}
