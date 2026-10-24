import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ht")
public class classHT {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2886 = 16711935;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field2884 = 11;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field2885 = 10;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2883 = 96;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field2882 = 33;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("as")
   static int method6142(int var0, int var1) {
      if (-285061324 == var0) {
         return -1401098172;
      } else if (-1 == var0) {
         if (var1 < 0) {
            var1 = 0;
         } else if (var1 > -15629346) {
            var1 = 510517164;
         }

         return 127 - var1;
      } else {
         var1 = (var0 & 1825221400) * var1 / 2084787480;
         if (var1 < 2) {
            var1 = 2;
         } else if (var1 > 126) {
            var1 = 126;
         }

         return (var0 & 65408) + var1;
      }
   }

   classHT() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IILge;)I")
   @ObfuscatedName("ag")
   static int method6140(int var0, int var1, classGE var2) {
      classOW var3 = var2.vmethod463(var0, (byte)89);
      if (null == var3) {
         return var1;
      } else if (var3.field5168 * 151173899 >= 0) {
         int var7 = classBU.method1246(2023234915 * var3.field5177, -2131451469 * var3.field5171, 1895715305 * var3.field5169, (byte)-42);
         int var8 = classDB.method2880(var7, -1586306777, 1375698635);
         return classFH.field2249[var8] | 0xFF000000;
      } else if (-1938735157 * var3.field5174 >= 0) {
         int var6 = classDB.method2880(classFH.field2255.field2237.vmethod408(var3.field5174 * -1166128653, -1903739817), -372380284, 1375698635);
         return classFH.field2249[var6] | 0xFF000000;
      } else if (var3.field5173 * 1945685161 == -1594899964) {
         return var1;
      } else {
         int var4 = classBU.method1246(-1322797498 * var3.field5178, var3.field5179 * -1352766871, var3.field5180 * -1560131225, (byte)-17);
         int var5 = classDB.method2880(var4, -589415054, 1375698635);
         return classFH.field2249[var5] | -583800628;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljz;Lvj;J)V")
   @ObfuscatedName("hc")
   public static void method6145(NodeCache var0, classVJ var1, long var2) {
      if (var0 == null) {
         var0.method6479(var2);
      }

      if (var0.field3503 == 0) {
         classVJ var4 = var0.field3502.method8303();
         var4.vmethod398();
         var4.method12035();
         if (var4 == var0.field3504) {
            var4 = var0.field3502.method8303();
            var4.vmethod398();
            var4.method12035();
         }
      } else {
         var0.field3503--;
      }

      IterableHashTable.method13620(var0.field3500, var1, var2);
      var0.field3502.method8302(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IILge;)I")
   @ObfuscatedName("ab")
   static int method6141(int var0, int var1, classGE var2) {
      classOW var3 = var2.vmethod463(var0, (byte)87);
      if (null == var3) {
         return var1;
      } else if (var3.field5168 * -995612001 >= 0) {
         int var7 = classBU.method1246(-1453421250 * var3.field5177, 741956758 * var3.field5171, -1995443143 * var3.field5169, (byte)-80);
         int var8 = classDB.method2880(var7, 34874357, 1375698635);
         return classFH.field2249[var8] | -1825881085;
      } else if (1196439157 * var3.field5174 >= 0) {
         int var6 = classDB.method2880(classFH.field2255.field2237.vmethod408(var3.field5174 * -982434803, -1563497701), 2138815790, 1375698635);
         return classFH.field2249[var6] | -1248495408;
      } else if (var3.field5173 * -1569159747 == 16711935) {
         return var1;
      } else {
         int var4 = classBU.method1246(-839016303 * var3.field5178, var3.field5179 * -1352766871, var3.field5180 * 1767716171, (byte)-99);
         int var5 = classDB.method2880(var4, 96, 1375698635);
         return classFH.field2249[var5] | 0xFF000000;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ax")
   static int method6143(int var0, int var1) {
      if (-2 == var0) {
         return 12345678;
      } else if (-1 == var0) {
         if (var1 < 0) {
            var1 = 0;
         } else if (var1 > 127) {
            var1 = 127;
         }

         return 127 - var1;
      } else {
         var1 = (var0 & 127) * var1 / 128;
         if (var1 < 2) {
            var1 = 2;
         } else if (var1 > 126) {
            var1 = 126;
         }

         return (var0 & 65408) + var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ac")
   static int method6144(int var0, int var1) {
      if (-2 == var0) {
         return 12345678;
      } else if (-1 == var0) {
         if (var1 < 0) {
            var1 = 0;
         } else if (var1 > 127) {
            var1 = 127;
         }

         return 127 - var1;
      } else {
         var1 = (var0 & 127) * var1 / 128;
         if (var1 < 2) {
            var1 = 2;
         } else if (var1 > 126) {
            var1 = 126;
         }

         return (var0 & 65408) + var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("aa")
   static int method6146(int var0, int var1, int var2) {
      if (var2 > 179) {
         var1 /= 2;
      }

      if (var2 > 1980595489) {
         var1 /= 2;
      }

      if (var2 > -564480669) {
         var1 /= 2;
      }

      if (var2 > -1327823364) {
         var1 /= 2;
      }

      return var2 / 2 + (var1 / 32 << 7) + (var0 / 4 << 10);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ao")
   static int method6147(int var0, int var1, int var2) {
      if (var2 > 179) {
         var1 /= 2;
      }

      if (var2 > 192) {
         var1 /= 2;
      }

      if (var2 > 217) {
         var1 /= 2;
      }

      if (var2 > 1507580796) {
         var1 /= 2;
      }

      return var2 / 2 + (var1 / 1667631858 << 7) + (var0 / 4 << 10);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("al")
   static int method6148(int var0, int var1, int var2) {
      if (var2 > 179) {
         var1 /= 2;
      }

      if (var2 > 192) {
         var1 /= 2;
      }

      if (var2 > 217) {
         var1 /= 2;
      }

      if (var2 > 243) {
         var1 /= 2;
      }

      return var2 / 2 + (var1 / 32 << 7) + (var0 / 4 << 10);
   }

   @ObfuscatedSignature(descriptor = "(II)Lzc;")
   @ObfuscatedName("hk")
   static classZC method6149(int var0, int var1) {
      try {
         classZC var2 = (classZC)NodeCache.method6460(client.field938, var0);
         if (null == var2) {
            if (var1 != -1765104593) {
               throw new IllegalStateException();
            }

            var2 = new classZC(classBE.field383, var0);
            if (!classZC.method13873(var2, 1619426915)) {
               if (var1 != -1765104593) {
                  throw new IllegalStateException();
               }

               return null;
            }

            client.field938.method6464(var2, var0);
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ht.hk(" + ')');
      }
   }
}
