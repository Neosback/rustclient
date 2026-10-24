import com.google.common.primitives.Doubles;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ns")
public class classNS {
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field4716 = 2048;
   @ObfuscatedSignature(descriptor = "Lau;")
   @ObfuscatedName("af")
   classAU field4714;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   public static final int field4717 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   static final int field4718 = 36;
   @ObfuscatedName("az")
   int field4715;
   @ObfuscatedName("hk")
   static long field4719;

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ag")
   static int method8037(int var0, int var1) {
      try {
         ChatLineBuffer var2 = (ChatLineBuffer)classCN.field1169.get(var0);
         if (var2 == null) {
            if (var1 >= 839963150) {
               throw new IllegalStateException();
            } else {
               return 0;
            }
         } else {
            return ChatLineBuffer.method1255(var2, (byte)-51);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ns.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIB)V")
   @ObfuscatedName("al")
   public static final void method8041(Widget var0, int var1, int var2, byte var3) {
      if (classBA.field305 * -782895767 == 0 || classBA.field305 * -782895767 == 3) {
         if (!classOE.field4841.isMenuOpen()) {
            int var4 = classTZ.field6259 * 1229235359;
            if (var4 == 0) {
               int var5 = client.field927 * 1762426687;
               if (client.field1135 && var5 != 0) {
                  boolean var6 = classTZ.field6253 * -38255113 >= var1
                     && classTZ.field6262 * -2144333897 >= var2
                     && classTZ.field6253 * -38255113 < var1 + var0.getWidth()
                     && classTZ.field6262 * -2144333897 < var2 + var0.getHeight();
                  if (var6) {
                     double var7 = client.field1026 + -var5 * 0.25;
                     client.field1026 = Doubles.constrainToRange(var7, 2.0, 8.0);
                  }
               }
            } else {
               classKH var19 = var0.method7402(classWK.field6689, true);
               if (var19 == null) {
                  return;
               }

               int var20 = classTZ.field6264 * 1422993977 - var1;
               int var22 = classTZ.field6255 * 1243590197 - var2;
               if (!var19.method6518(var20, var22)) {
                  return;
               }

               if (var4 == 1 || !classON.field4993 && var4 == 4) {
                  classTN var8 = client.field815.method1626(-1);
                  if (var8 == classTN.field6170) {
                     var20 -= var19.method6519() / 2;
                     var22 -= var19.method6521() / 2;
                     int var9 = client.field995.field6360 * 1379695375 & 16383;
                     int var10 = classGB.field2535[var9];
                     int var11 = classGB.field2538[var9];
                     int var12 = var11 * var20 + var10 * var22 >> 16;
                     int var13 = var11 * var22 - var10 * var20 >> 16;
                     float var14 = (float)client.field1026 / 128.0F;
                     int var15 = (int)(var12 / var14);
                     int var16 = (int)(var13 / var14);
                     int var17 = client.field963 * 2106329293 + var15 >> 7;
                     int var18 = client.field987 * -2126074583 - var16 >> 7;
                     client.method2418(var17, var18, var20, var22);
                  } else if (var8 == classTN.field6171) {
                     int var24 = var1 + var19.method6519() / 2;
                     int var25 = var2 + var19.method6521() / 2;
                     int var26 = classTZ.field6253 * -38255113 - var24;
                     int var27 = classTZ.field6262 * -2144333897 - var25;
                     int var28 = client.method2326((float)Math.atan2(var27, var26)) - 512;
                     var28 -= client.field995.field6360 * 1379695375 >> 3;
                     int var30 = (var28 + 64 & 2047) / 128;
                     client.field989 = var30 * -2024696235;
                     client.field839 = 30 * -1543296915;
                     client.method2446(var30);
                  }
               } else if (var4 == 2) {
                  client.field1026 = 4.0;
                  classTZ.field6259 = 0 * 811562847;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("afy")
   protected static int method8039(int var0) {
      try {
         int var1;
         label58: {
            var1 = 0;
            if (null != classAT.field279) {
               if (var0 >= 120041229) {
                  throw new IllegalStateException();
               }

               if (classAT.field279.isValid()) {
                  break label58;
               }
            }

            try {
               for (GarbageCollectorMXBean var3 : ManagementFactory.getGarbageCollectorMXBeans()) {
                  if (var3.isValid()) {
                     if (var0 >= 120041229) {
                        throw new IllegalStateException();
                     }

                     classAT.field279 = var3;
                     GameEngine.field6186 = 1622412589189406289L;
                     GameEngine.field6197 = -6728113685686042049L;
                  }
               }
            } catch (Throwable var10) {
            }
         }

         if (classAT.field279 != null) {
            if (var0 >= 120041229) {
               throw new IllegalStateException();
            }

            long var12 = classDD.method2988(16711680);
            long var4 = classAT.field279.getCollectionTime();
            if (-1L != 3566866902695694913L * GameEngine.field6197) {
               if (var0 >= 120041229) {
                  throw new IllegalStateException();
               }

               long var6 = var4 - GameEngine.field6197 * 3566866902695694913L;
               long var8 = var12 - GameEngine.field6186 * -5967588509917407921L;
               if (var8 != 0L) {
                  if (var0 >= 120041229) {
                     throw new IllegalStateException();
                  }

                  var1 = (int)(var6 * 100L / var8);
               }
            }

            GameEngine.field6197 = var4 * 6728113685686042049L;
            GameEngine.field6186 = -1622412589189406289L * var12;
         }

         return var1;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "ns.afy(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;[BIII)Z")
   @ObfuscatedName("aa")
   static boolean method8040(WorldView var0, byte[] var1, int var2, int var3, int var4) {
      try {
         return AABB.method4936(var1, var2, var3, var0.field1694 * 1296729483 - 1, -1269171107 * var0.field1690 - 1, (short)-9080);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ns.aa(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvl;I)I")
   @ObfuscatedName("kk")
   public static int method8038(classVL var0, int var1) {
      return var0 == null ? var0.method12055(var1) : var0.field6517[var1];
   }

   classNS(int var1, classAU var2) {
      this.field4715 = var1 * -1554563319;
      this.field4714 = var2;
   }
}
