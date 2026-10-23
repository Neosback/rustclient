import java.io.File;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("db")
public class classDB {
   @ObfuscatedName("ag")
   int field1369;
   @ObfuscatedName("af")
   int field1368;
   @ObfuscatedName("au")
   static File field1377;
   @ObfuscatedName("ab")
   int field1366;
   @ToRemove(unused = "true")
   @ObfuscatedName("es")
   static final int field1376 = 1;
   @ObfuscatedName("as")
   int field1371;
   @ToRemove(unused = "true")
   @ObfuscatedName("bm")
   public static final int field1374 = 32;
   @ObfuscatedName("az")
   int field1372;
   @ObfuscatedName("ae")
   int field1367;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field1370 = 499;
   @ToRemove(unused = "true")
   @ObfuscatedName("cr")
   static final int field1375 = 1007;
   @ObfuscatedName("bb")
   static int field1373;

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("af")
   void method2882(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field1372 = var1 * -2025561193;
      this.field1368 = var2 * -378780443;
      this.field1367 = -1047173704 * var3;
      this.field1366 = var4 * 644877005;
      this.field1369 = var5 * -1839559751;
      this.field1371 = var6 * -1907644063;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldb;IIIIII)V")
   @ObfuscatedName("ux")
   public static void method2883(classDB var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var0 == null) {
         var0.method2884(var1, var1, var1, var1, var1, var1, var1);
      } else {
         var0.field1372 = var1 * -2025561193;
         var0.field1368 = var2 * -1895662654;
         var0.field1367 = 881833361 * var3;
         var0.field1366 = var4 * 644877005;
         var0.field1369 = var5 * -1165767657;
         var0.field1371 = var6 * -25884917;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("az")
   void method2884(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      try {
         this.field1372 = var1 * -2025561193;
         this.field1368 = var2 * -378780443;
         this.field1367 = 881833361 * var3;
         this.field1366 = var4 * 644877005;
         this.field1369 = var5 * -1839559751;
         this.field1371 = var6 * -1907644063;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "db.az(" + ')');
      }
   }

   classDB() {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwh;)Z")
   @ObfuscatedName("fl")
   public static boolean method2881(classWH var0) {
      return var0.field6676;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("ab")
   void method2885(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field1372 = var1 * -2025561193;
      this.field1368 = var2 * 104444716;
      this.field1367 = 881833361 * var3;
      this.field1366 = var4 * 596199647;
      this.field1369 = var5 * 1523743017;
      this.field1371 = var6 * -1907644063;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("ag")
   void method2886(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field1372 = var1 * -2025561193;
      this.field1368 = var2 * -378780443;
      this.field1367 = 881833361 * var3;
      this.field1366 = var4 * 644877005;
      this.field1369 = var5 * -1839559751;
      this.field1371 = var6 * -1907644063;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("af")
   static int method2880(int var0, int var1, int var2) {
      try {
         if (-2 == var0) {
            if (var2 != 1375698635) {
               throw new IllegalStateException();
            } else {
               return 12345678;
            }
         } else if (-1 == var0) {
            if (var1 < 0) {
               if (var2 != 1375698635) {
                  throw new IllegalStateException();
               }

               var1 = 0;
            } else if (var1 > 127) {
               if (var2 != 1375698635) {
                  throw new IllegalStateException();
               }

               var1 = 127;
            }

            return 127 - var1;
         } else {
            var1 = (var0 & 127) * var1 / 128;
            if (var1 < 2) {
               if (var2 != 1375698635) {
                  throw new IllegalStateException();
               }

               var1 = 2;
            } else if (var1 > 126) {
               if (var2 != 1375698635) {
                  throw new IllegalStateException();
               }

               var1 = 126;
            }

            return (var0 & 65408) + var1;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "db.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("cz")
   static boolean method2888(byte var0) {
      try {
         return (-1072700981 * client.field853 & 4) != 0;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "db.cz(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcm;I)V")
   @ObfuscatedName("av")
   static void method2887(World var0, int var1) {
      try {
         if (var0.method2473((byte)-49) != client.field933) {
            client.field933 = var0.method2473((byte)-105);
            classBD.method994(var0.method2473((byte)-33), 2047526172);
         }

         if (-2024190683 * client.field884 != var0.field1158 * -458552905) {
            classVP var2 = classHC.field2765;
            int var3 = var0.field1158 * -458552905;
            if ((var3 & classXE.field6838.vmethod8((byte)1)) != 0) {
               if (var1 != 1660308192) {
                  client.method1946();
                  return;
               }

               AABB.field2370 = classSU.method10729(var2, "logo_deadman_mode", "", -188356597);
            } else if ((var3 & classXE.field6849.vmethod8((byte)1)) != 0) {
               if (var1 != 1660308192) {
                  throw new IllegalStateException();
               }

               AABB.field2370 = classSU.method10729(var2, "logo_seasonal_mode", "", -1306596214);
            } else if ((var3 & classXE.field6834.vmethod8((byte)1)) != 0) {
               if (var1 != 1660308192) {
                  client.method1946();
                  return;
               }

               AABB.field2370 = classSU.method10729(var2, "logo_speedrunning", "", -117981147);
            } else if ((var3 & classXE.field6837.vmethod8((byte)1)) != 0) {
               if (var1 != 1660308192) {
                  throw new IllegalStateException();
               }

               AABB.field2370 = classSU.method10729(var2, "logo_ugc_world", "", -215054047);
            } else {
               AABB.field2370 = classSU.method10729(var2, "logo", "", 320308891);
            }
         }

         WidgetConfigNode.field4137 = var0.field1167;
         client.field828 = var0.field1159 * 894790041;
         client.field884 = var0.field1158 * -635970901;
         classGV.field2714 = var0.field1166;
         int var10001;
         if (0 == 2064080735 * client.field941) {
            if (var1 != 1660308192) {
               throw new IllegalStateException();
            }

            var10001 = 43594;
         } else {
            var10001 = var0.field1159 * 851246551 + 40000;
         }

         classRJ.field5679 = 721437403 * var10001;
         if (0 == client.field941 * 2064080735) {
            if (var1 != 1660308192) {
               throw new IllegalStateException();
            }

            var10001 = 443;
         } else {
            var10001 = 50000 + var0.field1159 * 851246551;
         }

         classMB.field4476 = 309769881 * var10001;
         classAD.field141 = 1527064229 * classRJ.field5679;
         client.method1946();
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "db.av(" + ')');
      }
   }
}
