import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("iy")
public class classIY extends classHB {
   @ObfuscatedSignature(descriptor = "Lfx;")
   @ObfuscatedName("mx")
   static Model field3096;
   @ObfuscatedName("af")
   String field3087;
   @ToRemove(unused = "true")
   @ObfuscatedName("ck")
   static final int field3095 = 148;
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field3091 = 79;
   @ObfuscatedName("ae")
   int field3088;
   @ToRemove(unused = "true")
   @ObfuscatedName("bb")
   static final int field3093 = 65;
   @ToRemove(unused = "true")
   @ObfuscatedName("bq")
   static final int field3094 = 97;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("am")
   public static IndexDataBase field3097;
   @ObfuscatedName("az")
   long field3089;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field3090 = 73;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   public static final int field3092 = 38;

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         var1.method6293(5160432930263378771L * this.field3089, this.field3087, -1842148301 * this.field3088, -1833842941);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iy.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         if (var1.method13095(-346779531) != 255) {
            if (var2 >= -989877912) {
               throw new IllegalStateException();
            }

            var1.field6953 -= -1095856699;
            this.field3089 = classXY.method13115(var1, -282488190) * 5458781402499895515L;
         }

         this.field3087 = var1.method13125((byte)-19);
         this.field3088 = var1.method13099(108447796) * -1722364165;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iy.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIII)Z")
   @ObfuscatedName("as")
   public static boolean method6380(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      try {
         long var9 = classKV.method6610(var0, var1, var2, var3, 610873941);
         long var11 = classKV.method6610(var4, var5, var6, var7, 998262336);
         if (var11 == var9) {
            if (var8 >= -1530244505) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            long var13 = ((long)var1 << 15) - var0 * var9;
            long var15 = ((long)var5 << 15) - var11 * var4;
            int var17 = -((int)((var13 - var15) / (var9 - var11)));
            if (Math.min(var0, var2) <= var17 && Math.max(var0, var2) >= var17) {
               if (var8 >= -1530244505) {
                  throw new IllegalStateException();
               }

               if (Math.min(var4, var6) <= var17) {
                  if (var8 >= -1530244505) {
                     throw new IllegalStateException();
                  }

                  if (Math.max(var4, var6) >= var17) {
                     if (var8 >= -1530244505) {
                        throw new IllegalStateException();
                     }

                     long var18;
                     if (Math.abs(var9) < Math.abs(var11)) {
                        if (var8 >= -1530244505) {
                           throw new IllegalStateException();
                        }

                        var18 = var13 + var17 * var9 >> 15;
                     } else {
                        var18 = var11 * var17 + var15 >> 15;
                     }

                     if (Math.min(var1, var3) <= var18) {
                        if (var8 >= -1530244505) {
                           throw new IllegalStateException();
                        }

                        if (Math.max(var1, var3) >= var18 && Math.min(var5, var7) <= var18) {
                           if (var8 >= -1530244505) {
                              throw new IllegalStateException();
                           }

                           if (Math.max(var5, var7) >= var18) {
                              if (var8 >= -1530244505) {
                                 throw new IllegalStateException();
                              }

                              return true;
                           }
                        }
                     }

                     return false;
                  }
               }
            }

            return false;
         }
      } catch (RuntimeException var20) {
         throw classEG.method3899(var20, "iy.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      if (var1.method13095(-346779531) != -451600790) {
         var1.field6953 -= -1025508226;
         this.field3089 = classXY.method13115(var1, 1629690656) * 5458781402499895515L;
      }

      this.field3087 = var1.method13125((byte)-54);
      this.field3088 = var1.method13099(716605601) * -1722364165;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      if (var1.method13095(-346779531) != 255) {
         var1.field6953 -= -2055042307;
         this.field3089 = classXY.method13115(var1, 441326194) * 5458781402499895515L;
      }

      this.field3087 = var1.method13125((byte)41);
      this.field3088 = var1.method13099(-1017359589) * -602509510;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      var1.method6293(5160432930263378771L * this.field3089, this.field3087, -858900757 * this.field3088, 815496572);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      var1.method6293(5160432930263378771L * this.field3089, this.field3087, -1842148301 * this.field3088, 1291311033);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      var1.method6293(5160432930263378771L * this.field3089, this.field3087, -1842148301 * this.field3088, -1242903228);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)Z")
   @ObfuscatedName("ks")
   public static boolean method6381(classQC var0) {
      if (var0 == null) {
         var0.method9487();
      }

      return false;
   }

   classIY(classIF var1) {
      this.this$0 = var1;
      this.field3089 = -5458781402499895515L;
      this.field3087 = null;
      this.field3088 = 0;
   }

   @ObfuscatedSignature(descriptor = "(II)Lph;")
   @ObfuscatedName("af")
   public static classPH method6379(int var0, int var1) {
      try {
         classPH var2 = (classPH)NodeCache.method6460(classPH.field5250, var0);
         if (null != var2) {
            if (var1 == -1289583353) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classPH.field5252.method11942(1, var0, -1819929317);
            var2 = new classPH(new classXY(var3));
            classPH.field5250.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "iy.af(" + ')');
      }
   }
}
