import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ac")
public class classAC {
   @ObfuscatedName("ae")
   ExecutorService field127 = Executors.newSingleThreadExecutor();
   @ObfuscatedSignature(descriptor = "Lae;")
   @ObfuscatedName("af")
   final classAE field128;
   @ObfuscatedSignature(descriptor = "Lxy;")
   @ObfuscatedName("az")
   final classXY field129;
   @ObfuscatedName("ab")
   Future field133;
   @ObfuscatedSignature(descriptor = "Lhv;")
   @ObfuscatedName("ah")
   static classHV field132;
   @ObfuscatedName("cx")
   static boolean field135;
   @ObfuscatedName("lk")
   static byte[][] field136;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field130 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field131 = 10;
   @ObfuscatedSignature(descriptor = "Lmf;")
   @ObfuscatedName("kf")
   static classMF field134;

   @ObfuscatedSignature(descriptor = "(S)V")
   @ObfuscatedName("ab")
   void method314(short var1) {
      try {
         this.field133 = this.field127.submit(new classAF(this, this.field129, this.field128));
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ac.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   public boolean method305() {
      return this.field133.isDone();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public void method307() {
      this.field127.shutdown();
      this.field127 = null;
   }

   public classAC(classXY var1, classAE var2) {
      this.field129 = var1;
      this.field128 = var2;
      this.method314((short)-6012);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public void method308(int var1) {
      try {
         this.field127.shutdown();
         this.field127 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ac.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lxy;")
   @ObfuscatedName("ae")
   public classXY method312(byte var1) {
      try {
         try {
            return (classXY)this.field133.get();
         } catch (Exception var3) {
            return null;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ac.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public void method309() {
      this.field127.shutdown();
      this.field127 = null;
   }

   @ObfuscatedSignature(descriptor = "()Lxy;")
   @ObfuscatedName("aa")
   public classXY method313() {
      try {
         return (classXY)this.field133.get();
      } catch (Exception var2) {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   void method315() {
      this.field133 = this.field127.submit(new classAF(this, this.field129, this.field128));
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("az")
   public boolean method306(int var1) {
      try {
         return this.field133.isDone();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ac.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("am")
   static void method321(int var0, int var1) {
      try {
         if (-47366135 * classBF.field384 == var0) {
            if (var1 <= 1997865712) {
               throw new IllegalStateException();
            }
         } else {
            classBF.field384 = 1117408825 * var0;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ac.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("as")
   static void method319(byte var0) {
      try {
         if (null != classSX.field6094) {
            if (var0 != 11) {
               throw new IllegalStateException();
            }

            if (classDS.method3603(classSX.field6094, (byte)-24)) {
               if (var0 != 11) {
                  throw new IllegalStateException();
               }

               classSX.field6094.method3590(738542393);
            }
         }

         classGN.method5578(-765496050);
         classBD.method995((byte)127);
         if (null != NpcOverrides.field5378) {
            if (var0 != 11) {
               throw new IllegalStateException();
            }

            NpcOverrides.field5378.field1537 = false;
         }

         NpcOverrides.field5378 = null;
         client.field796.method3381((short)19926);
         if (classWW.field6784 != null) {
            if (var0 != 11) {
               throw new IllegalStateException();
            }

            try {
               classWW.field6784.method7543((short)28124);
            } catch (Exception var4) {
            }
         }

         classWW.field6784 = null;
         classPM.method9063((short)6916);
         GrandExchangeOffer.method7662(1434517176);
         client.field815.method1615(1822507524);
         Deque2.method7942(client.field818);
         classYE.field6975.method13623();
         client.field1124 = null;
         classBK.method1122(0, 0, (byte)119);
         Actor.method3134(-1612757241);
         client.field990 = false;
         Iterator var1 = client.field815.iterator();

         while (var1.hasNext()) {
            if (var0 != 11) {
               client.method2228();
               return;
            }

            WorldView var2 = (WorldView)var1.next();
            WorldView.method3759(var2, (byte)-98);
         }

         client.field858.method10346(false, 1845258725);
         if (classBJ.field481 != null) {
            if (var0 != 11) {
               throw new IllegalStateException();
            }

            classBJ.field481.method408((byte)-1);
         }

         classVB.method11987(classEB.field1724, 2131645249);
         classTB.method10775(-1927903443);
         if (null != classAV.field294) {
            if (var0 != 11) {
               client.method2228();
               return;
            }

            classAV.field294.method2875((byte)44);
         }

         Widget.method7205(2000203024);
         classME.field4486 = null;
         classME.field4487 = null;
         GraphicsObject.field477 = null;
         classHG.method6040((byte)16);
         classQX.field5621 = null;
         client.field1003.clear();
         client.field1005 = 0;
         classEB.field1724 = new classVB();
         classAV.field294 = new classDP(classOE.field4841.field792, 240);

         try {
            classCL.method1579("oldschool", classGV.field2714, classRO.field5823.field3554, 0, 25, (byte)1);
         } catch (IOException var3) {
            throw new RuntimeException(var3);
         }

         classQX.field5621 = new classUS(255, classME.field4486, classME.field4487, 750000);
         classWW.field6784 = new classMB();
         classWK.field6689 = new classVV(classCC.field671, classSX.field6095, classHC.field2765, classYO.field7021, classLZ.field4450);
         classOE.field4841.method11079((byte)1);
         classXQ.method12933(classCP.field1195, 200380884);
         classJB.method6386(0, -1192966987);
         client.method2228();
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ac.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lac;)V")
   @ObfuscatedName("ru")
   public static void method316(classAC var0) {
      if (var0 == null) {
         var0.method318();
      }

      var0.field133 = var0.field127.submit(new classAF(var0, var0.field129, var0.field128));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lac;)V")
   @ObfuscatedName("qu")
   public static void method317(classAC var0) {
      var0.field133 = var0.field127.submit(new classAF(var0, var0.field129, var0.field128));
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("az")
   public static void method311(int var0, int var1, int var2) {
      try {
         Iterator var3 = classNP.field4693.iterator();

         while (var3.hasNext()) {
            if (var2 <= 227155452) {
               throw new IllegalStateException();
            }

            classNQ var4 = (classNQ)var3.next();
            var4.vmethod246(var0, var1, 927076618);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ac.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;B)I")
   @ObfuscatedName("az")
   public static int method304(CharSequence var0, byte var1) {
      try {
         int var2 = var0.length();
         int var3 = 0;

         for (int var4 = 0; var4 < var2; var4++) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            char var5 = var0.charAt(var4);
            if (var5 <= 127) {
               if (var1 <= 1) {
                  throw new IllegalStateException();
               }

               var3++;
            } else if (var5 <= 2047) {
               if (var1 <= 1) {
                  throw new IllegalStateException();
               }

               var3 += 2;
            } else {
               var3 += 3;
            }
         }

         return var3;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ac.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;)Z")
   @ObfuscatedName("ud")
   public static boolean method320(ItemComposition var0) {
      return null != var0.field5039;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   public void method310() {
      this.field127.shutdown();
      this.field127 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   void method318() {
      this.field133 = this.field127.submit(new classAF(this, this.field129, this.field128));
   }
}
