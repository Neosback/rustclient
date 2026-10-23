import java.util.ArrayList;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("im")
public class classIM extends classIS {
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   public static final int field2991 = 19;
   @ObfuscatedName("az")
   int field2990;
   @ToRemove(unused = "true")
   @ObfuscatedName("bv")
   static final int field2996 = 130;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   public static final int field2994 = 21;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field2993 = 6;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2992 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   static final int field2995 = 44;

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod506(classXY var1) {
      this.field2990 = var1.method13099(1363314717) * 1242480657;
      var1.method13095(-346779531);
      if (var1.method13095(-346779531) != 1874736274) {
         var1.field6953 -= -82698895;
         classXY.method13115(var1, 488882632);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod511(ClanChannel var1) {
      var1.method6220(this.field2990 * -876529621, -167076243);
   }

   @ObfuscatedSignature(descriptor = "(Lie;I)V")
   @ObfuscatedName("af")
   @Override
   void vmethod509(ClanChannel var1, int var2) {
      try {
         var1.method6220(this.field2990 * 1506658405, -1172431142);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "im.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod510(ClanChannel var1) {
      var1.method6220(this.field2990 * 1506658405, -1438515093);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod505(classXY var1) {
      this.field2990 = var1.method13099(1902951631) * -953626631;
      var1.method13095(-346779531);
      if (var1.method13095(-346779531) != 341801299) {
         var1.field6953 -= 1347578625;
         classXY.method13115(var1, 1817284262);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod508(ClanChannel var1) {
      var1.method6220(this.field2990 * 1506658405, -1658141160);
   }

   classIM(classIX var1) {
      this.this$0 = var1;
      this.field2990 = 624187027;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod507(classXY var1, int var2) {
      try {
         this.field2990 = var1.method13099(1663062833) * -624187027;
         var1.method13095(-346779531);
         if (var1.method13095(-346779531) != 255) {
            if (var2 >= 1072982787) {
               return;
            }

            var1.field6953 -= -1095856699;
            classXY.method13115(var1, -926205580);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "im.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwy;)Ljava/lang/String;")
   @ObfuscatedName("dl")
   public static String method6265(classWY var0) {
      return var0.field6788;
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("as")
   public static void method6266(int var0, int var1, int var2, int var3, int var4) {
      try {
         if (classNP.field4696.size() > 1 && classNP.field4696.get(0) != null) {
            if (var4 >= -545605385) {
               return;
            }

            if (classNU.method8085(((MidiRequest)classNP.field4696.get(0)).field4639, (byte)10)) {
               if (var4 >= -545605385) {
                  return;
               }

               if (classNP.field4696.get(1) != null) {
                  if (var4 >= -545605385) {
                     throw new IllegalStateException();
                  }

                  if (classNU.method8085(((MidiRequest)classNP.field4696.get(1)).field4639, (byte)10)) {
                     if (var4 >= -545605385) {
                        throw new IllegalStateException();
                     }

                     classIF.method6247(var0, var1, var2, var3, (byte)8);
                     classNP.field4698.add(new classWA(null));
                     ArrayList var5 = new ArrayList();
                     var5.add(new classWU(new classWP(null, 1, false, classNP.field4699 * 396217257), classNP.field4694 * -1350272915));
                     var5.add(new classWU(new classWO(null, 0, false, classNP.field4700 * 1784906769), classNP.field4701 * -1331669075));
                     classNP.field4698.add(new classWQ(null, var5));
                     if (classNP.field4697.get(0) != null && classNP.field4697.get(1) != null) {
                        MidiRequest var6 = (MidiRequest)classNP.field4697.get(0);
                        classNP.field4697.set(0, classNP.field4696.get(1));
                        classNP.field4697.set(1, var6);
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "im.as(" + ')');
      }
   }
}
