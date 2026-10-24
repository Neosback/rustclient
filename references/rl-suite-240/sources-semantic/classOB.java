import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ob")
public final class classOB {
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   static final int field4811 = 30;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field4808 = 7;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   public static final int field4809 = 12;
   @ObfuscatedName("az")
   static final char[] field4806 = new char[]{
      '_',
      'a',
      'b',
      'c',
      'd',
      'e',
      'f',
      'g',
      'h',
      'i',
      'j',
      'k',
      'l',
      'm',
      'n',
      'o',
      'p',
      'q',
      'r',
      's',
      't',
      'u',
      'v',
      'w',
      'x',
      'y',
      'z',
      '0',
      '1',
      '2',
      '3',
      '4',
      '5',
      '6',
      '7',
      '8',
      '9'
   };
   @ToRemove(unused = "true")
   @ObfuscatedName("be")
   public static final int field4810 = 67;
   @ObfuscatedName("af")
   static long[] field4807 = new long[12];

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("as")
   public static String method8325(long var0) {
      if (var0 > 0L && var0 < 6582952005840035281L) {
         if (var0 % 37L == 0L) {
            return null;
         } else {
            int var2 = 0;

            for (long var3 = var0; 0L != var3; var3 /= 37L) {
               var2++;
            }

            StringBuilder var5 = new StringBuilder(var2);

            while (0L != var0) {
               long var6 = var0;
               var0 /= 37L;
               int var8 = field4806[(int)(var6 - 37L * var0)];
               if (312251424 == var8) {
                  int var9 = var5.length() - 1;
                  var5.setCharAt(var9, Character.toUpperCase(var5.charAt(var9)));
                  var8 = 746348644;
               }

               var5.append((char)var8);
            }

            var5.reverse();
            var5.setCharAt(0, Character.toUpperCase(var5.charAt(0)));
            return var5.toString();
         }
      } else {
         return null;
      }
   }

   static {
      for (int var0 = 0; var0 < field4807.length; var0++) {
         field4807[var0] = (long)Math.pow(37.0, var0);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)V")
   @ObfuscatedName("ac")
   static void method8331(Widget var0, int var1, int var2) {
      try {
         if (0 == var0.field4373 * -1510882375) {
            if (var2 != 427421377) {
               throw new IllegalStateException();
            }

            if (63131743 * var0.field4258 > -1) {
               if (var2 != 427421377) {
                  return;
               }

               if (var1 > 379541057 * var0.field4264) {
                  if (var2 != 427421377) {
                     throw new IllegalStateException();
                  }

                  var0.field4264 = var1 * -1479820863;
               }

               if (var1 < var0.field4263 * -1210177335) {
                  if (var2 != 427421377) {
                     throw new IllegalStateException();
                  }

                  var0.field4263 = -452038279 * var1;
               }
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ob.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("ab")
   public static String method8323(long var0) {
      if (var0 > 0L && var0 < 6582952005840035281L) {
         if (var0 % 37L == 0L) {
            return null;
         } else {
            int var2 = 0;

            for (long var3 = var0; var3 != 0L; var3 /= 37L) {
               var2++;
            }

            StringBuilder var5 = new StringBuilder(var2);

            while (var0 != 0L) {
               long var6 = var0;
               var0 /= 37L;
               var5.append(field4806[(int)(var6 - var0 * 37L)]);
            }

            return var5.reverse().toString();
         }
      } else {
         return null;
      }
   }

   classOB() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("ag")
   public static String method8326(long var0) {
      if (var0 > 0L && var0 < 6582952005840035281L) {
         if (var0 % 37L == 0L) {
            return null;
         } else {
            int var2 = 0;

            for (long var3 = var0; 0L != var3; var3 /= 37L) {
               var2++;
            }

            StringBuilder var5 = new StringBuilder(var2);

            while (0L != var0) {
               long var6 = var0;
               var0 /= 37L;
               char var8 = field4806[(int)(var6 - 37L * var0)];
               if ('_' == var8) {
                  int var9 = var5.length() - 1;
                  var5.setCharAt(var9, Character.toUpperCase(var5.charAt(var9)));
                  var8 = 160;
               }

               var5.append(var8);
            }

            var5.reverse();
            var5.setCharAt(0, Character.toUpperCase(var5.charAt(0)));
            return var5.toString();
         }
      } else {
         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("ax")
   public static String method8327(long var0) {
      if (var0 > 0L && var0 < 6582952005840035281L) {
         if (var0 % 37L == 0L) {
            return null;
         } else {
            int var2 = 0;

            for (long var3 = var0; 0L != var3; var3 /= 37L) {
               var2++;
            }

            StringBuilder var5 = new StringBuilder(var2);

            while (0L != var0) {
               long var6 = var0;
               var0 /= 37L;
               char var8 = field4806[(int)(var6 - 37L * var0)];
               if ('_' == var8) {
                  int var9 = var5.length() - 1;
                  var5.setCharAt(var9, Character.toUpperCase(var5.charAt(var9)));
                  var8 = 160;
               }

               var5.append(var8);
            }

            var5.reverse();
            var5.setCharAt(0, Character.toUpperCase(var5.charAt(0)));
            return var5.toString();
         }
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Lop;")
   @ObfuscatedName("ae")
   public static ItemComposition method8329(int var0, byte var1) {
      try {
         ItemComposition var2 = (ItemComposition)NodeCache.method6460(ItemComposition.field5004, var0);
         if (null != var2) {
            if (var1 == 64) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classOT.field5115.method11942(10, var0, -1651052842);
            if (null == var3) {
               if (var1 == 64) {
                  throw new IllegalStateException();
               }

               var2 = new ItemComposition(null, var0, null, null, true);
            } else {
               classXY var4;
               int[] var6;
               label61: {
                  var4 = new classXY(var3);
                  var6 = new int[2];
                  int var7 = var4.method13095(-346779531);
                  if (var7 != 97) {
                     if (var1 == 64) {
                        throw new IllegalStateException();
                     }

                     if (139 != var7) {
                        if (var1 == 64) {
                           throw new IllegalStateException();
                        }

                        if (148 != var7) {
                           Arrays.fill(var6, -1);
                           break label61;
                        }
                     }
                  }

                  var6[0] = var4.method13099(945959292);
                  var4.method13095(-346779531);
                  var6[1] = var4.method13099(130405734);
               }

               var4.field6953 = 0;
               ItemComposition var11 = var6[0] == -1 ? null : method8329(var6[0], (byte)13);
               ItemComposition var8 = var6[1] == -1 ? null : method8329(var6[1], (byte)-52);
               var2 = new ItemComposition(var4, var0, var11, var8, true);
            }

            ItemComposition.field5004.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ob.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lse;")
   @ObfuscatedName("as")
   static classSE method8328(int var0, int var1) {
      try {
         classSE var2 = (classSE)classOF.method8436(classQD.method9546(-379692846), var0, -1950997571);
         if (var2 == null) {
            if (var1 != -47642443) {
               throw new IllegalStateException();
            }

            var2 = classSE.field5909;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ob.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("ae")
   public static String method8324(long var0) {
      if (var0 > 0L && var0 < 6582952005840035281L) {
         if (var0 % 37L == 0L) {
            return null;
         } else {
            int var2 = 0;

            for (long var3 = var0; var3 != 0L; var3 /= 37L) {
               var2++;
            }

            StringBuilder var5 = new StringBuilder(var2);

            while (var0 != 0L) {
               long var6 = var0;
               var0 /= 37L;
               var5.append(field4806[(int)(var6 - var0 * 37L)]);
            }

            return var5.reverse().toString();
         }
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;ILjava/lang/Object;I)V")
   @ObfuscatedName("ar")
   public static void method8330(classTE var0, int var1, Object var2, int var3) {
      try {
         classOL.method8544(var0, 1327640383);
         if (var1 >= 0) {
            if (var3 <= 1252777968) {
               throw new IllegalStateException();
            }

            if (var1 <= -1943548931 * var0.field6129) {
               var0.method10817(1 + -1943548931 * var0.field6129, 1304866981);
               if (var1 < -1943548931 * var0.field6129) {
                  classMX.method7786(var0, var1, var0, var1 + 1, var0.field6129 * -1943548931 - var1, -2128084676);
               }

               var0.method10807(var1, var2, 1976963692);
               var0.field6129 += -1063196331;
               var0.field6131 = true;
               return;
            }

            if (var3 <= 1252777968) {
               return;
            }
         }

         throw new RuntimeException();
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ob.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Lgy;III)V")
   @ObfuscatedName("az")
   public static void method8321(classXY var0, classGY var1, int var2, int var3, int var4) {
      try {
         var1.method5957(classKY.method6649(var2, 1577372180), classKY.method6649(var3, 861181486), (byte)5);
         var1.method5961(0, (short)255);
         var1.method5950(0, 841791791);
         byte var5 = var0.method13097((byte)17);
         if (var5 != 0) {
            int var6 = classQK.method9651(var0, var5, 0, (byte)69);
            int var7 = classQK.method9651(var0, var5, 2, (byte)76);
            int var8 = classQK.method9651(var0, var5, 4, (byte)77);
            int var9 = classQK.method9651(var0, var5, 6, (byte)114);
            var1.method5967(var6, var7, var8, var9, -390000412);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "ob.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[Lkr;")
   @ObfuscatedName("ag")
   public static classKR[] method8322(byte var0) {
      try {
         return new classKR[]{classKR.field4004, classKR.field4006, classKR.field4005};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ob.ag(" + ')');
      }
   }
}
