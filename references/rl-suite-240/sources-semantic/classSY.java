import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sy")
public class classSY {
   @ObfuscatedName("ae")
   float field6099 = Float.MAX_VALUE;
   @ObfuscatedName("af")
   float field6100;
   @ObfuscatedName("az")
   int field6101;
   @ObfuscatedName("ab")
   float field6097 = Float.MAX_VALUE;
   @ObfuscatedName("ag")
   float field6096 = Float.MAX_VALUE;
   @ObfuscatedName("as")
   float field6098 = Float.MAX_VALUE;
   @ObfuscatedSignature(descriptor = "Lsy;")
   @ObfuscatedName("ax")
   classSY field6102;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Loi;)Lkw;")
   @ObfuscatedName("co")
   public static classKW method10764(WorldEntityConfig var0) {
      return var0.field4884;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsy;Lxy;I)V")
   @ObfuscatedName("bb")
   public static void method10758(classSY var0, classXY var1, int var2) {
      if (var0 == null) {
         var0.method10762(var1, var2, var2);
      }

      var0.field6101 = var1.method13104((byte)16) * -1704955975;
      var0.field6100 = var1.method13119(954238399);
      var0.field6099 = var1.method13119(1140390718);
      var0.field6097 = var1.method13119(787704173);
      var0.field6096 = var1.method13119(1876739545);
      var0.field6098 = var1.method13119(22579728);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzh;)V")
   @ObfuscatedName("pl")
   public static void method10763(classZH var0) {
      if (var0 == null) {
         var0.method14051();
      }

      var0.field7183 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsy;Lxy;I)V")
   @ObfuscatedName("hj")
   public static void method10759(classSY var0, classXY var1, int var2) {
      if (var0 == null) {
         var0.method10760(var1, var2);
      }

      var0.field6101 = var1.method13104((byte)16) * -1704955975;
      var0.field6100 = var1.method13119(-83963739);
      var0.field6099 = var1.method13119(291689664);
      var0.field6097 = var1.method13119(1269653316);
      var0.field6096 = var1.method13119(-250677433);
      var0.field6098 = var1.method13119(1854011696);
   }

   classSY() {
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ae")
   void method10760(classXY var1, int var2) {
      this.field6101 = var1.method13104((byte)16) * -1704955975;
      this.field6100 = var1.method13119(-1028566497);
      this.field6099 = var1.method13119(480949948);
      this.field6097 = var1.method13119(1840430139);
      this.field6096 = var1.method13119(-834142439);
      this.field6098 = var1.method13119(993234334);
   }

   @ObfuscatedSignature(descriptor = "(I)[Ljava/lang/Object;")
   @ObfuscatedName("bn")
   static Object[] method10765(int var0) {
      try {
         String var1 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
         Object[] var2 = new Object[var1.length()];

         for (int var3 = var1.length() - 1; var3 >= 0; var3--) {
            if (var0 <= 818745819) {
               throw new IllegalStateException();
            }

            if (var1.charAt(var3) == 'i') {
               if (var0 <= 818745819) {
                  throw new IllegalStateException();
               }

               var2[var3] = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            } else {
               var2[var3] = classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
            }
         }

         return var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "sy.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;Lte;I)I")
   @ObfuscatedName("az")
   public static int method10757(classTE var0, classTE var1, int var2) {
      try {
         if (var0 == var1) {
            if (var2 != -325353510) {
               throw new IllegalStateException();
            } else {
               return 0;
            }
         } else if (null == var0) {
            if (var2 != -325353510) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else if (var1 == null) {
            return 1;
         } else {
            int var3 = var0.method10797(-356529791);
            int var4 = var1.method10797(-480017276);
            int var5 = Math.min(var3, var4);
            if (classYY.field7109 == var0.field6126 && classYY.field7109 == var1.field6126) {
               if (var2 != -325353510) {
                  throw new IllegalStateException();
               }

               int[] var12 = var0.method10791(1466976318);
               int[] var14 = var1.method10791(1580187264);

               for (int var16 = 0; var16 < var5; var16++) {
                  if (var2 != -325353510) {
                     throw new IllegalStateException();
                  }

                  if (var12[var16] < var14[var16]) {
                     if (var2 != -325353510) {
                        throw new IllegalStateException();
                     }

                     return -1;
                  }

                  if (var12[var16] > var14[var16]) {
                     if (var2 != -325353510) {
                        throw new IllegalStateException();
                     }

                     return 1;
                  }
               }
            } else {
               if (var0.field6126 == classYY.field7113) {
                  if (var2 != -325353510) {
                     throw new IllegalStateException();
                  }

                  if (var1.field6126 == classYY.field7113) {
                     if (var2 != -325353510) {
                        throw new IllegalStateException();
                     }

                     long[] var11 = var0.method10793(-1985316054);
                     long[] var13 = var1.method10793(-261201653);

                     for (int var15 = 0; var15 < var5; var15++) {
                        if (var2 != -325353510) {
                           throw new IllegalStateException();
                        }

                        if (var11[var15] < var13[var15]) {
                           if (var2 != -325353510) {
                              throw new IllegalStateException();
                           }

                           return -1;
                        }

                        if (var11[var15] > var13[var15]) {
                           if (var2 != -325353510) {
                              throw new IllegalStateException();
                           }

                           return 1;
                        }
                     }

                     return var3 < var4 ? -1 : (var4 == var3 ? 0 : 1);
                  }
               }

               if (classYY.field7111 != var0.field6126) {
                  throw new RuntimeException();
               }

               if (var2 != -325353510) {
                  throw new IllegalStateException();
               }

               if (classYY.field7111 != var1.field6126) {
                  throw new RuntimeException();
               }

               if (var2 != -325353510) {
                  throw new IllegalStateException();
               }

               Object[] var6 = var0.method10796(1968563847);
               Object[] var7 = var1.method10796(-1462959061);

               for (int var8 = 0; var8 < var5; var8++) {
                  int var9 = ((String)var6[var8]).compareTo((String)var7[var8]);
                  if (var9 < 0) {
                     if (var2 != -325353510) {
                        throw new IllegalStateException();
                     }

                     return -1;
                  }

                  if (var9 > 0) {
                     if (var2 != -325353510) {
                        throw new IllegalStateException();
                     }

                     return 1;
                  }
               }
            }

            return var3 < var4 ? -1 : (var4 == var3 ? 0 : 1);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "sy.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsy;Lxy;II)V")
   @ObfuscatedName("bb")
   public static void method10761(classSY var0, classXY var1, int var2, int var3) {
      if (var0 == null) {
         var0.method10762(var1, var2, var2);
      }

      try {
         var0.field6101 = var1.method13104((byte)16) * -1704955975;
         var0.field6100 = var1.method13119(1386282862);
         var0.field6099 = var1.method13119(-422565930);
         var0.field6097 = var1.method13119(2120343611);
         var0.field6096 = var1.method13119(713711615);
         var0.field6098 = var1.method13119(-227074139);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "sy.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)V")
   @ObfuscatedName("az")
   void method10762(classXY var1, int var2, int var3) {
      try {
         this.field6101 = var1.method13218((byte)16) * -1704955975;
         this.field6099 = var1.method13119(1386282862);
         this.field6098 = var1.method13119(-422565930);
         this.field6098 = var1.method13119(2120343611);
         this.field6099 = var1.method13119(713711615);
         this.field6099 = var1.method13119(-227074139);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "sy.az(" + ')');
      }
   }
}
