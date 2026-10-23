import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("iv")
public class classIV extends classHB {
   @ObfuscatedName("kh")
   static boolean field3069;
   @ObfuscatedName("af")
   String field3067;
   @ObfuscatedName("az")
   int field3066;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final String field3068 = "scale_";
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field3065 = 10;

   classIV(classIF var1) {
      this.this$0 = var1;
   }

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("ap")
   static void method6363(boolean var0, byte var1) {
      try {
         byte var2 = 0;
         if (!IndexDataBase.method11935(-2053271753)) {
            if (var1 == 1) {
               throw new IllegalStateException();
            }

            var2 = 12;
         } else {
            label91: {
               if (!classOE.field4841.method2138(1985617997)) {
                  if (var1 == 1) {
                     return;
                  }

                  if (!client.method2142(classOE.field4841, (byte)80)) {
                     if (var1 == 1) {
                        throw new IllegalStateException();
                     }

                     if (!classOE.field4841.method2140(-956599779)) {
                        break label91;
                     }

                     if (var1 == 1) {
                        return;
                     }
                  }
               }

               var2 = 10;
            }
         }

         classAC.method321(var2, 2129783208);
         if (var0) {
            if (var1 == 1) {
               throw new IllegalStateException();
            }

            classBF.field389 = "";
            client.method1779(-1);
            classBF.method1067("");
            classEV.field1942 = 0;
            classQP.field5584 = "";
         }

         label81: {
            if (null != classBF.field389) {
               if (var1 == 1) {
                  return;
               }

               if (!classBF.field389.isEmpty()) {
                  if (var1 == 1) {
                     throw new IllegalStateException();
                  }
                  break label81;
               }
            }

            if (classAB.field122.method2782((byte)8) != null) {
               if (var1 == 1) {
                  throw new IllegalStateException();
               }

               classBF.field389 = classAB.field122.method2782((byte)8);
               client.method1779(-1);
               client.field833 = true;
            } else {
               client.field833 = false;
            }
         }

         if (client.field833) {
            if (var1 == 1) {
               throw new IllegalStateException();
            }

            if (null != classBF.field389) {
               if (var1 == 1) {
                  throw new IllegalStateException();
               }

               if (!classBF.field389.isEmpty()) {
                  if (var1 == 1) {
                     throw new IllegalStateException();
                  }

                  classBF.field402 = -878854915;
                  return;
               }
            }
         }

         classBF.field402 = 0;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iv.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         var1.method6334(102865497 * this.field3066, this.field3067, (byte)-85);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iv.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      this.field3066 = classXY.method13113(var1, (byte)1) * -1330377239;
      this.field3067 = var1.method13127(254767341);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      this.field3066 = classXY.method13113(var1, (byte)1) * -1330377239;
      this.field3067 = var1.method13127(1085918569);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      var1.method6334(102865497 * this.field3066, this.field3067, (byte)-121);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      var1.method6334(102865497 * this.field3066, this.field3067, (byte)-124);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         this.field3066 = classXY.method13113(var1, (byte)1) * -1330377239;
         this.field3067 = var1.method13127(-597738653);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iv.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lle;I)V")
   @ObfuscatedName("yf")
   public static void method6361(classLE var0, int var1) {
      if (var0 == null) {
         var0.method6813(var1);
      } else {
         var0.field4113 = classDD.method2988(16711680) * -7767324811451944647L;
         var0.field4116 = var1 * 94990176;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;I)V")
   @ObfuscatedName("cm")
   static final void method6364(PacketBuffer var0, int var1) {
      client.method2283(var0);

      try {
         int var2 = var0.method13254(-83822973);
         var0.method13266(1891829759);
         int var3 = var0.method13254(221345550);
         client.field1045.method12885(var3, var2, -1379620371);
         classKK.method6533(client.field1045, var3, var2, false, (byte)0);
         client.method1927(var0);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "iv.cm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      var1.method6334(102865497 * this.field3066, this.field3067, (byte)-121);
   }

   @ObfuscatedSignature(descriptor = "(I)Lcm;")
   @ObfuscatedName("ax")
   static World method6362(int var0) {
      try {
         World.field1154 = 0;
         return classSX.method10755(-1953402072);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "iv.ax(" + ')');
      }
   }
}
