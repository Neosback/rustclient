import java.util.ArrayList;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ij")
public class classIJ extends classHB {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field2975 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field2976 = 18;
   @ToRemove(unused = "true")
   @ObfuscatedName("bo")
   static final String field2980 = "dt";
   @ObfuscatedName("iz")
   static int field2979;
   @ObfuscatedName("az")
   int field2974;
   @ToRemove(unused = "true")
   @ObfuscatedName("bb")
   static final int field2977 = 61;
   @ToRemove(unused = "true")
   @ObfuscatedName("cc")
   static final int field2978 = 59;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("gt")
   static final void method6260(String var0, int var1) {
      try {
         if (var0.equals("")) {
            if (var1 != -1142457594) {
               throw new IllegalStateException();
            }
         } else {
            classJL var2 = classEF.method3863(classJS.FRIENDCHAT_JOIN_LEAVE, client.field796.field1533, (byte)-48);
            classXY.method13029(var2.field3341, classAU.method709(var0, (byte)59), (byte)-76);
            var2.field3341.method13054(var0, (short)19626);
            client.field796.method3374(var2, -1301097035);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ij.gt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      this.field2974 = var1.method13099(1811243603) * -595538065;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         this.field2974 = var1.method13099(-518976421) * -595538065;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ij.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      this.field2974 = var1.method13099(-1405582422) * -378118865;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)I")
   @ObfuscatedName("qk")
   public static int method6259(classMA var0) {
      return var0.field4455 * 2013644177;
   }

   classIJ(classIF var1) {
      this.this$0 = var1;
      this.field2974 = 595538065;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      var1.method6295(1779789711 * this.field2974, 702383331);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      var1.method6295(1779789711 * this.field2974, 434519228);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      var1.method6295(1779789711 * this.field2974, 55426774);
   }

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         var1.method6295(1779789711 * this.field2974, 1185036733);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ij.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/ArrayList;IIIIZI)V")
   @ObfuscatedName("af")
   public static void method6258(ArrayList var0, int var1, int var2, int var3, int var4, boolean var5, int var6) {
      try {
         if (var0.isEmpty()) {
            if (var6 >= 492858962) {
               throw new IllegalStateException();
            }
         } else {
            classNP.field4695.clear();
            classNP.field4698.clear();
            if (var5) {
               classGK.method5517(973918153);
            } else {
               for (int var7 = 0; var7 < classNP.field4696.size(); var7++) {
                  MidiRequest var8 = (MidiRequest)classNP.field4696.get(var7);
                  if (var8 == null) {
                     if (var6 >= 492858962) {
                        throw new IllegalStateException();
                     }

                     classNP.field4696.remove(var7);
                     var7--;
                  } else if (var8.field4631) {
                     if (var6 >= 492858962) {
                        return;
                     }

                     if (917328323 * var8.field4639.field4749 > 0) {
                        if (var6 >= 492858962) {
                           return;
                        }

                        var8.field4639.field4749 -= 16852715;
                     }

                     var8.field4639.method8079((byte)69);
                     var8.field4639.method8066((byte)0);
                     var8.field4639.method8048(0, -1159796592);
                     classNP.field4696.remove(var7);
                     var7--;
                  } else {
                     var8.field4631 = true;
                  }
               }
            }

            if (!var5) {
               if (var6 >= 492858962) {
                  throw new IllegalStateException();
               }

               classNP.field4697.clear();
            }

            for (MidiRequest var16 : var0) {
               if (-1 != 1942111947 * var16.field4630) {
                  if (450234221 * var16.field4628 == -1) {
                     if (var6 >= 492858962) {
                        throw new IllegalStateException();
                     }
                  } else {
                     if (!var5) {
                        if (var6 >= 492858962) {
                           throw new IllegalStateException();
                        }

                        classNP.field4697.add(var16);
                     }

                     classNP.field4695.add(var16);
                  }
               }
            }

            if (classNP.field4695.isEmpty()) {
               if (var6 < 492858962) {
                  ;
               }
            } else {
               classIF.method6247(var1, var2, var3, var4, (byte)-32);
               classNP.field4698.add(new classWN(null));
               classNP.field4698.add(new classWS(null, classIA.field2925, classRM.field5807, classNP.field4703));
               ArrayList var15 = new ArrayList();
               var15.add(new classWK(new classWP(null, 0, true, classNP.field4699 * 396217257)));
               if (!classNP.field4696.isEmpty()) {
                  ArrayList var17 = new ArrayList();
                  var17.add(new classWU(new classWQ(null, var15), -1350272915 * classNP.field4694));
                  ArrayList var10 = new ArrayList();
                  Iterator var11 = classNP.field4696.iterator();

                  while (var11.hasNext()) {
                     if (var6 >= 492858962) {
                        throw new IllegalStateException();
                     }

                     MidiRequest var12 = (MidiRequest)var11.next();
                     var10.add(var12);
                  }

                  var17.add(new classWU(new classWO(new classWL(null, var10), 0, false, 1784906769 * classNP.field4700), -1331669075 * classNP.field4701));
                  classNP.field4698.add(new classWQ(null, var17));
               } else {
                  classNP.field4698.add(new classWU(null, -1350272915 * classNP.field4694));
                  classNP.field4698.add(new classWQ(null, var15));
               }
            }
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "ij.af(" + ')');
      }
   }
}
