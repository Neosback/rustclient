import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nu")
public class classNU extends classAH {
   @ObfuscatedName("at")
   int[] field4769;
   @ObfuscatedName("aq")
   int[] field4766;
   @ObfuscatedName("aw")
   int[] field4763;
   @ObfuscatedName("ag")
   static ThreadPoolExecutor field4747 = null;
   @ObfuscatedName("ad")
   int[] field4760;
   @ObfuscatedName("ax")
   public int field4749;
   @ObfuscatedName("am")
   int[] field4756;
   @ObfuscatedName("ak")
   int[] field4750;
   @ObfuscatedName("bx")
   int[] field4751;
   @ObfuscatedSignature(descriptor = "Lyi;")
   @ObfuscatedName("al")
   HashTable field4770;
   @ObfuscatedName("aj")
   int field4752;
   @ObfuscatedName("ay")
   int field4761;
   @ObfuscatedName("ao")
   int[] field4748;
   @ObfuscatedName("av")
   int[] field4771;
   @ObfuscatedSignature(descriptor = "Lak;")
   @ObfuscatedName("ba")
   classAK field4758;
   @ObfuscatedName("bk")
   long field4777;
   @ObfuscatedName("bc")
   int[] field4754;
   @ObfuscatedName("ah")
   int[] field4757;
   @ObfuscatedName("aa")
   int[] field4753;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4773 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4774 = 2;
   @ObfuscatedName("ac")
   int[] field4762;
   @ObfuscatedName("as")
   PriorityQueue field4755 = new PriorityQueue(5, new classNL());
   @ObfuscatedName("an")
   int[] field4764;
   @ObfuscatedSignature(descriptor = "[[Lnt;")
   @ObfuscatedName("bp")
   classNT[][] field4765;
   @ObfuscatedSignature(descriptor = "[[Lnt;")
   @ObfuscatedName("br")
   classNT[][] field4772;
   @ObfuscatedSignature(descriptor = "Lnc;")
   @ObfuscatedName("bm")
   classNC field4767;
   @ObfuscatedName("bt")
   boolean field4779;
   @ObfuscatedName("ab")
   static AtomicBoolean field4746 = null;
   @ObfuscatedName("bj")
   int field4776;
   @ObfuscatedName("bg")
   int field4775;
   @ObfuscatedName("bo")
   long field4778;
   @ObfuscatedSignature(descriptor = "Lnd;")
   @ObfuscatedName("bu")
   classND field4768;
   @ObfuscatedName("bn")
   int[] field4759;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cm")
   public boolean method8086() {
      synchronized (this.field4758) {
         return this.field4770.method13417() > 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("ah")
   @Override
   protected classAH vmethod34() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   public void method8060(int var1) {
      try {
         if (this.field4755 == null) {
            if (var1 != 953065235) {
               ;
            }
         } else {
            if (field4746 != null) {
               if (var1 == 953065235) {
                  throw new IllegalStateException();
               }

               field4746.set(true);
            }

            field4746 = new AtomicBoolean(false);
            AtomicBoolean var2 = field4746;
            if (field4747 == null) {
               if (var1 == 953065235) {
                  throw new IllegalStateException();
               }

               int var3 = Runtime.getRuntime().availableProcessors();
               field4747 = new ThreadPoolExecutor(0, var3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new classNF(this));
            }

            field4747.submit(new classNG(this, var2));
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ag")
   public void method8063(byte var1) {
      try {
         synchronized (this.field4758) {
            for (classNR var3 = (classNR)this.field4770.method13423(); var3 != null; var3 = (classNR)this.field4770.method13427()) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               classNR.method8033(var3, (byte)60);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "nu.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ao")
   public void method8066(byte var1) {
      try {
         synchronized (this.field4758) {
            for (classNR var3 = (classNR)this.field4770.method13423(); var3 != null; var3 = (classNR)this.field4770.method13427()) {
               if (var1 != 0) {
                  throw new IllegalStateException();
               }

               var3.vmethod398();
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "nu.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("co")
   public void method8067() {
      synchronized (this.field4758) {
         for (classNR var2 = (classNR)this.field4770.method13423(); var2 != null; var2 = (classNR)this.field4770.method13427()) {
            var2.vmethod398();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;II)V")
   @ObfuscatedName("hv")
   public static void method8097(classNU var0, int var1, int var2) {
      if (var0 == null) {
         var0.method8134(var1, var1);
      } else {
         var0.field4756[var1] = var2;
         var0.field4757[var1] = var2 & -128;
         var0.method8102(var1, var2, 1964276760);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("du")
   public void method8091(int var1, int var2) {
      synchronized (this.field4758) {
         this.method8098(var1, var2, -1973392059);
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Z")
   @ObfuscatedName("ad")
   public boolean method8087(short var1) {
      try {
         synchronized (this.field4758) {
            boolean var10000;
            if (this.field4770.method13417() > 0) {
               if (var1 <= 1122) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "nu.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;)I")
   @ObfuscatedName("fj")
   int method8167(classNT var1) {
      classNY var2 = var1.field4745;
      int var3 = 4096 + this.field4753[-1057694491 * var1.field4732] * this.field4769[-1057694491 * var1.field4732] >> 13;
      var3 = var3 * var3 + 16384 >> 15;
      var3 = var1.field4735 * -983353503 * var3 + 16384 >> 15;
      var3 = 1238197527 * this.field4752 * var3 + 128 >> 8;
      if (-939533713 * var2.field4791 > 0) {
         var3 = (int)(var3 * Math.pow(0.5, -939533713 * var2.field4791 * (1.953125E-5 * (934628419 * var1.field4731))) + 0.5);
      }

      if (var2.field4796 != null) {
         int var4 = 1539106655 * var1.field4736;
         int var5 = var2.field4796[1 + var1.field4734 * 1565767631];
         if (var1.field4734 * 1565767631 < var2.field4796.length - 2) {
            int var6 = (var2.field4796[1565767631 * var1.field4734] & 255) << 8;
            int var7 = (var2.field4796[var1.field4734 * 1565767631 + 2] & 255) << 8;
            var5 += (var2.field4796[1565767631 * var1.field4734 + 3] - var5) * (var4 - var6) / (var7 - var6);
         }

         var3 = var5 * var3 + 32 >> 6;
      }

      if (67131625 * var1.field4727 > 0 && var2.field4797 != null) {
         int var11 = var1.field4727 * 67131625;
         int var12 = var2.field4797[1 + var1.field4726 * -463641621];
         if (-463641621 * var1.field4726 < var2.field4797.length - 2) {
            int var13 = (var2.field4797[var1.field4726 * -463641621] & 255) << 8;
            int var14 = (var2.field4797[-463641621 * var1.field4726 + 2] & 255) << 8;
            var12 += (var11 - var13) * (var2.field4797[3 + var1.field4726 * -463641621] - var12) / (var14 - var13);
         }

         var3 = var3 * var12 + 32 >> 6;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("au")
   void method8098(int var1, int var2, int var3) {
      try {
         this.field4756[var1] = var2;
         this.field4757[var1] = var2 & -128;
         this.method8102(var1, var2, 1682469127);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("db")
   void method8101(int var1, int var2) {
      if (var2 != this.field4754[var1]) {
         this.field4754[var1] = var2;

         for (int var3 = 0; var3 < 128; var3++) {
            this.field4772[var1][var3] = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ar")
   void method8103(int var1, int var2, int var3, int var4) {
      try {
         this.method8112(var1, var2, 64, -1053552803);
         if ((this.field4748[var1] & 2) != 0) {
            for (classNT var5 = (classNT)Deque2.method7962(this.field4768.field4659); var5 != null; var5 = (classNT)Deque2.method7968(this.field4768.field4659)) {
               if (-1057694491 * var5.field4732 == var1) {
                  if (var4 == 799916168) {
                     throw new IllegalStateException();
                  }

                  if (var5.field4727 * 67131625 < 0) {
                     if (var4 == 799916168) {
                        throw new IllegalStateException();
                     }

                     this.field4765[var1][-107170957 * var5.field4729] = null;
                     this.field4765[var1][var2] = var5;
                     int var6 = (var5.field4725 * -359606667 * -403336933 * var5.field4733 >> 12) + var5.field4721 * -1276571151;
                     var5.field4721 = var5.field4721 + -665600239 * (var2 - -107170957 * var5.field4729 << 8);
                     var5.field4733 = -300850925 * (var6 - var5.field4721 * -1276571151);
                     var5.field4725 = 480104448;
                     var5.field4729 = -685332037 * var2;
                     return;
                  }
               }
            }
         }

         classNR var10 = (classNR)HashTable.method13432(this.field4770, this.field4754[var1]);
         if (null == var10) {
            if (var4 == 799916168) {
               throw new IllegalStateException();
            }
         } else if (null == var10.field4704[var2]) {
            if (var4 == 799916168) {
               throw new IllegalStateException();
            }
         } else {
            classAJ var11 = var10.field4704[var2].method700(1016727737);
            if (var11 == null) {
               if (var4 != 799916168) {
                  ;
               }
            } else {
               classNT var7 = new classNT();
               var7.field4732 = -1566998291 * var1;
               var7.field4744 = var10;
               var7.field4741 = var11;
               var7.field4745 = var10.field4711[var2];
               var7.field4720 = var10.field4709[var2] * 1130932567;
               var7.field4729 = var2 * -685332037;
               var7.field4735 = 780512417 * (1024 + var10.field4708 * -127646999 * var3 * var3 * var10.field4706[var2] >> 11);
               var7.field4723 = -225319163 * (var10.field4707[var2] & 255);
               var7.field4721 = ((var2 << 8) - (var10.field4705[var2] & 32767)) * -665600239;
               var7.field4731 = 0;
               var7.field4736 = 0;
               var7.field4734 = 0;
               var7.field4727 = -371851097;
               var7.field4726 = 0;
               if (0 == this.field4750[var1]) {
                  if (var4 == 799916168) {
                     throw new IllegalStateException();
                  }

                  var7.field4742 = classAM.method472(
                     var11, method8162(this, var7, -851559498), this.method8168(var7, -1876968885), this.method8171(var7, 1746496860)
                  );
               } else {
                  var7.field4742 = classAM.method472(var11, method8162(this, var7, -1168586760), 0, this.method8171(var7, 1725890535));
                  boolean var10002;
                  if (var10.field4705[var2] < 0) {
                     if (var4 == 799916168) {
                        throw new IllegalStateException();
                     }

                     var10002 = true;
                  } else {
                     var10002 = false;
                  }

                  this.method8107(var7, var10002, 881975798);
               }

               if (var10.field4705[var2] < 0) {
                  var7.field4742.method541(-1);
               }

               if (-1530244505 * var7.field4720 >= 0) {
                  if (var4 == 799916168) {
                     throw new IllegalStateException();
                  }

                  classNT var8 = this.field4772[var1][-1530244505 * var7.field4720];
                  if (var8 != null) {
                     if (var4 == 799916168) {
                        throw new IllegalStateException();
                     }

                     if (67131625 * var8.field4727 < 0) {
                        if (var4 == 799916168) {
                           throw new IllegalStateException();
                        }

                        this.field4765[var1][var8.field4729 * -107170957] = null;
                        var8.field4727 = 0;
                     }
                  }

                  this.field4772[var1][var7.field4720 * -1530244505] = var7;
               }

               this.field4768.field4659.method7946(var7);
               this.field4765[var1][var2] = var7;
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "nu.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;ZI)V")
   @ObfuscatedName("aw")
   void method8107(classNT var1, boolean var2, int var3) {
      try {
         int var5;
         label34: {
            int var4 = var1.field4741.field189.length;
            if (var2) {
               if (var3 != 881975798) {
                  throw new IllegalStateException();
               }

               if (var1.field4741.field190) {
                  if (var3 != 881975798) {
                     throw new IllegalStateException();
                  }

                  int var6 = var4 + var4 - var1.field4741.field192;
                  var5 = (int)((long)var6 * this.field4750[var1.field4732 * -1057694491] >> 6);
                  var4 <<= 8;
                  if (var5 >= var4) {
                     if (var3 != 881975798) {
                        throw new IllegalStateException();
                     }

                     var5 = var4 + var4 - 1 - var5;
                     var1.field4742.method569(true);
                  }
                  break label34;
               }
            }

            var5 = (int)((long)var4 * this.field4750[var1.field4732 * -1057694491] >> 6);
         }

         var1.field4742.method564(var5);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "nu.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("bk")
   void method8136(int var1, byte var2) {
      try {
         if (var1 < 0) {
            if (var2 != 2) {
               for (int var4 = 0; var4 < 16; var4++) {
                  if (var2 == 2) {
                     throw new IllegalStateException();
                  }

                  this.method8136(var4, (byte)14);
               }
            }
         } else {
            this.field4753[var1] = 12800;
            this.field4771[var1] = 8192;
            this.field4769[var1] = 16383;
            this.field4759[var1] = 8192;
            this.field4760[var1] = 0;
            this.field4766[var1] = 8192;
            this.method8148(var1, -2033466329);
            this.method8151(var1, -1140320208);
            this.field4748[var1] = 0;
            this.field4762[var1] = 32767;
            this.field4763[var1] = 256;
            this.field4750[var1] = 0;
            method8158(this, var1, 8192, (byte)71);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "nu.bk(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ct")
   public int method8050() {
      return 1238197527 * this.field4752;
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("bg")
   void method8124(int var1, int var2, byte var3) {
      try {
         this.field4759[var1] = var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnu;IIII)V")
   @ObfuscatedName("qc")
   public static void method8117(classNU var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method8121(var1, var1, var1, var1);
      }

      try {
         ;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "nu.bm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bj")
   void method8130(int var1, int var2) {
      try {
         for (classNT var3 = (classNT)this.field4768.field4659.method7957(); var3 != null; var3 = (classNT)this.field4768.field4659.method7965()) {
            if (var2 <= 2044929277) {
               throw new IllegalStateException();
            }

            if (var1 >= 0) {
               if (var2 <= 2044929277) {
                  throw new IllegalStateException();
               }

               if (var1 != -1057694491 * var3.field4732) {
                  continue;
               }

               if (var2 <= 2044929277) {
                  throw new IllegalStateException();
               }
            }

            if (null != var3.field4742) {
               if (var2 <= 2044929277) {
                  return;
               }

               var3.field4742.method583(WidgetConfigNode.field4136 * 675076789 / 100);
               if (var3.field4742.method595()) {
                  if (var2 <= 2044929277) {
                     throw new IllegalStateException();
                  }

                  this.field4768.field4660.method618(var3.field4742);
               }

               classNT.method8044(var3, 1946466263);
            }

            if (var3.field4727 * 67131625 < 0) {
               if (var2 <= 2044929277) {
                  return;
               }

               this.field4765[-1057694491 * var3.field4732][-107170957 * var3.field4729] = null;
            }

            var3.vmethod398();
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.bj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dt")
   void method8099(int var1, int var2) {
      this.field4756[var1] = var2;
      this.field4757[var1] = var2 & 906513920;
      this.method8102(var1, var2, 1958391157);
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("bw")
   void method8140(int var1, byte var2) {
      try {
         for (classNT var3 = (classNT)this.field4768.field4659.method7957(); var3 != null; var3 = (classNT)this.field4768.field4659.method7965()) {
            if (var1 >= 0) {
               if (var2 != 0) {
                  return;
               }

               if (var1 != var3.field4732 * -1057694491) {
                  continue;
               }
            }

            if (var3.field4727 * 67131625 < 0) {
               if (var2 != 0) {
                  throw new IllegalStateException();
               }

               this.field4765[var3.field4732 * -1057694491][var3.field4729 * -107170957] = null;
               var3.field4727 = 0;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.bw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("be")
   void method8148(int var1, int var2) {
      try {
         if ((this.field4748[var1] & 2) != 0) {
            if (var2 >= -918718938) {
               throw new IllegalStateException();
            }

            for (classNT var3 = (classNT)this.field4768.field4659.method7957(); var3 != null; var3 = (classNT)this.field4768.field4659.method7965()) {
               if (var2 >= -918718938) {
                  throw new IllegalStateException();
               }

               if (-1057694491 * var3.field4732 == var1 && this.field4765[var1][var3.field4729 * -107170957] == null && 67131625 * var3.field4727 < 0) {
                  var3.field4727 = 0;
               }
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.be(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("by")
   void method8151(int var1, int var2) {
      try {
         if ((this.field4748[var1] & 4) != 0) {
            for (classNT var3 = (classNT)this.field4768.field4659.method7957(); null != var3; var3 = (classNT)this.field4768.field4659.method7965()) {
               if (var2 != -1140320208) {
                  throw new IllegalStateException();
               }

               if (-1057694491 * var3.field4732 == var1) {
                  if (var2 != -1140320208) {
                     return;
                  }

                  var3.field4730 = 0;
               }
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.by(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fv")
   void method8175() {
      int var1 = -1076146517 * this.field4775;
      int var2 = 2122727052 * this.field4776;

      long var3;
      for (var3 = 5174971452106306489L * this.field4778; this.field4776 * 852899933 == var2; var3 = this.field4767.method7846(var2)) {
         while (true) {
            if (var2 == this.field4767.field4654[var1]) {
               this.field4767.method7824(var1);
               int var5 = this.field4767.method7835(var1);
               if (1 != var5) {
                  if (0 != (var5 & 128)) {
                     this.method8153(var5, -724523093);
                  }

                  classTO.method10989(this.field4767, var1);
                  this.field4767.method7825(var1);
                  continue;
               }

               this.field4767.method7832();
               this.field4767.method7825(var1);
               if (this.field4767.method7857()) {
                  if (!this.field4779 || var2 == 0) {
                     method8144(this, -1768093644);
                     this.field4767.method7813();
                     return;
                  }

                  classNC.method7859(this.field4767, var3);
               }
            }

            var1 = classNC.method7851(this.field4767);
            var2 = this.field4767.field4654[var1];
            break;
         }
      }

      this.field4775 = -1260362814 * var1;
      this.field4776 = var2 * -1258786400;
      this.field4778 = var3 * -7106189560981577079L;
   }

   @ObfuscatedSignature(descriptor = "(Lno;ZB)V")
   @ObfuscatedName("aj")
   public void method8071(classNO var1, boolean var2, byte var3) {
      try {
         synchronized (this.field4758) {
            this.method8079((byte)-90);
            this.field4767.method7810(var1.field4691);
            this.field4779 = var2;
            this.field4777 = 0L;
            int var5 = this.field4767.method7822();

            for (int var6 = 0; var6 < var5; var6++) {
               if (var3 <= 1) {
                  throw new IllegalStateException();
               }

               this.field4767.method7824(var6);
               classTO.method10989(this.field4767, var6);
               this.field4767.method7825(var6);
            }

            this.field4775 = classNC.method7851(this.field4767) * -1778762749;
            this.field4776 = this.field4767.field4654[-1076146517 * this.field4775] * 596597749;
            this.field4778 = this.field4767.method7846(852899933 * this.field4776) * -7106189560981577079L;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "nu.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cb")
   public int method8051() {
      return 1238197527 * this.field4752;
   }

   @ObfuscatedSignature(descriptor = "(Lnt;I)I")
   @ObfuscatedName("bd")
   int method8168(classNT var1, int var2) {
      try {
         classNY var3 = var1.field4745;
         int var4 = 4096 + this.field4753[-1057694491 * var1.field4732] * this.field4769[-1057694491 * var1.field4732] >> 13;
         var4 = var4 * var4 + 16384 >> 15;
         var4 = var1.field4735 * -983353503 * var4 + 16384 >> 15;
         var4 = 1238197527 * this.field4752 * var4 + 128 >> 8;
         if (-939533713 * var3.field4791 > 0) {
            if (var2 == -1095595519) {
               throw new IllegalStateException();
            }

            var4 = (int)(var4 * Math.pow(0.5, -939533713 * var3.field4791 * (1.953125E-5 * (934628419 * var1.field4731))) + 0.5);
         }

         if (var3.field4796 != null) {
            int var5 = 1539106655 * var1.field4736;
            int var6 = var3.field4796[1 + var1.field4734 * 1565767631];
            if (var1.field4734 * 1565767631 < var3.field4796.length - 2) {
               if (var2 == -1095595519) {
                  throw new IllegalStateException();
               }

               int var7 = (var3.field4796[1565767631 * var1.field4734] & 255) << 8;
               int var8 = (var3.field4796[var1.field4734 * 1565767631 + 2] & 255) << 8;
               var6 += (var3.field4796[1565767631 * var1.field4734 + 3] - var6) * (var5 - var7) / (var8 - var7);
            }

            var4 = var6 * var4 + 32 >> 6;
         }

         if (67131625 * var1.field4727 > 0) {
            if (var2 == -1095595519) {
               throw new IllegalStateException();
            }

            if (var3.field4797 != null) {
               if (var2 == -1095595519) {
                  throw new IllegalStateException();
               }

               int var13 = var1.field4727 * 67131625;
               int var14 = var3.field4797[1 + var1.field4726 * -463641621];
               if (-463641621 * var1.field4726 < var3.field4797.length - 2) {
                  if (var2 == -1095595519) {
                     throw new IllegalStateException();
                  }

                  int var15 = (var3.field4797[var1.field4726 * -463641621] & 255) << 8;
                  int var16 = (var3.field4797[-463641621 * var1.field4726 + 2] & 255) << 8;
                  var14 += (var13 - var15) * (var3.field4797[3 + var1.field4726 * -463641621] - var14) / (var16 - var15);
               }

               var4 = var4 * var14 + 32 >> 6;
            }
         }

         return var4;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "nu.bd(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("eh")
   void method8137(int var1) {
      if (var1 >= 0) {
         this.field4753[var1] = 12800;
         this.field4771[var1] = 8192;
         this.field4769[var1] = 16383;
         this.field4759[var1] = 8192;
         this.field4760[var1] = 0;
         this.field4766[var1] = 8192;
         this.method8148(var1, -1564988536);
         this.method8151(var1, -1140320208);
         this.field4748[var1] = 0;
         this.field4762[var1] = 32767;
         this.field4763[var1] = 462154629;
         this.field4750[var1] = 0;
         method8158(this, var1, 173345212, (byte)93);
      } else {
         for (int var2 = 0; var2 < 16; var2++) {
            this.method8136(var2, (byte)37);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;)V")
   @ObfuscatedName("jk")
   public static void method8061(classNU var0) {
      if (var0.field4755 != null) {
         if (field4746 != null) {
            field4746.set(true);
         }

         field4746 = new AtomicBoolean(false);
         AtomicBoolean var1 = field4746;
         if (field4747 == null) {
            int var2 = Runtime.getRuntime().availableProcessors();
            field4747 = new ThreadPoolExecutor(0, var2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new classNF(var0));
         }

         field4747.submit(new classNG(var0, var1));
      }
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("as")
   @Override
   protected classAH vmethod29() {
      try {
         return this.field4768;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "nu.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("ax")
   @Override
   protected classAH vmethod35() {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "nu.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("aa")
   @Override
   protected void vmethod30(int[] var1, int var2, int var3) {
      try {
         if (this.field4767.method7817()) {
            int var4 = this.field4761 * -1423260313 * this.field4767.field4647 / (675076789 * WidgetConfigNode.field4136);

            do {
               long var5 = (long)var3 * var4 + -8970596226586884259L * this.field4777;
               if (this.field4778 * 5174971452106306489L - var5 >= 0L) {
                  this.field4777 = 1512448154803324149L * var5;
                  break;
               }

               int var7 = (int)((var4 + (this.field4778 * 5174971452106306489L - -8970596226586884259L * this.field4777) - 1L) / var4);
               this.field4777 += (long)var7 * var4 * 1512448154803324149L;
               this.field4768.vmethod30(var1, var2, var7);
               var2 += var7;
               var3 -= var7;
               method8176(this, -1205376008);
            } while (this.field4767.method7817());
         }

         this.field4768.vmethod30(var1, var2, var3);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "nu.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   @Override
   protected void vmethod31(int var1) {
      try {
         if (this.field4767.method7817()) {
            int var2 = -1423260313 * this.field4761 * this.field4767.field4647 / (WidgetConfigNode.field4136 * 675076789);

            do {
               long var3 = (long)var1 * var2 + -8970596226586884259L * this.field4777;
               if (this.field4778 * 5174971452106306489L - var3 >= 0L) {
                  this.field4777 = 1512448154803324149L * var3;
                  break;
               }

               int var5 = (int)((var2 + (this.field4778 * 5174971452106306489L - -8970596226586884259L * this.field4777) - 1L) / var2);
               this.field4777 += (long)var5 * var2 * 1512448154803324149L;
               this.field4768.vmethod31(var5);
               var1 -= var5;
               method8176(this, 307618493);
            } while (this.field4767.method7817());
         }

         this.field4768.vmethod31(var1);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "nu.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;)I")
   @ObfuscatedName("fy")
   int method8161(classNT var1) {
      int var2 = (-403336933 * var1.field4733 * -359606667 * var1.field4725 >> 12) + var1.field4721 * -1276571151;
      var2 += (this.field4759[var1.field4732 * -1057694491] - 8192) * this.field4763[-1057694491 * var1.field4732] >> 12;
      classNY var3 = var1.field4745;
      if (-771094009 * var3.field4790 > 0 && (var3.field4794 * -1472637671 > 0 || this.field4760[var1.field4732 * -1057694491] > 0)) {
         int var4 = -1472637671 * var3.field4794 << 2;
         int var5 = var3.field4788 * -1122448781 << 1;
         if (var1.field4728 * -1955754893 < var5) {
            var4 = -1955754893 * var1.field4728 * var4 / var5;
         }

         var4 += this.field4760[-1057694491 * var1.field4732] >> 7;
         double var6 = Math.sin((1237307259 * var1.field4737 & 511) * 0.01227184630308513);
         var2 += (int)(var4 * var6);
      }

      int var10 = (int)(var1.field4741.field187 * 256 * Math.pow(2.0, 3.255208333333333E-4 * var2) / (675076789 * WidgetConfigNode.field4136) + 0.5);
      return var10 < 1 ? 1 : var10;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ef")
   void method8149(int var1) {
      if ((this.field4748[var1] & 2) != 0) {
         for (classNT var2 = (classNT)this.field4768.field4659.method7957(); var2 != null; var2 = (classNT)this.field4768.field4659.method7965()) {
            if (-1057694491 * var2.field4732 == var1 && this.field4765[var1][var2.field4729 * -107170957] == null && 67131625 * var2.field4727 < 0) {
               var2.field4727 = 0;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bq")
   void method8153(int var1, int var2) {
      try {
         int var3 = var1 & 240;
         if (var3 == 128) {
            if (var2 != -724523093) {
               throw new IllegalStateException();
            } else {
               int var15 = var1 & 15;
               int var21 = var1 >> 8 & 127;
               int var24 = var1 >> 16 & 127;
               this.method8112(var15, var21, var24, -1007796470);
            }
         } else if (var3 == 144) {
            if (var2 != -724523093) {
               throw new IllegalStateException();
            } else {
               int var14 = var1 & 15;
               int var20 = var1 >> 8 & 127;
               int var23 = var1 >> 16 & 127;
               if (var23 > 0) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.method8103(var14, var20, var23, -29666088);
               } else {
                  this.method8112(var14, var20, 64, -1354132743);
               }
            }
         } else if (160 == var3) {
            if (var2 != -724523093) {
               throw new IllegalStateException();
            } else {
               int var13 = var1 & 15;
               int var19 = var1 >> 8 & 127;
               int var22 = var1 >> 16 & 127;
               method8117(this, var13, var19, var22, -2123728586);
            }
         } else if (176 == var3) {
            if (var2 == -724523093) {
               int var12 = var1 & 15;
               int var18 = var1 >> 8 & 127;
               int var6 = var1 >> 16 & 127;
               if (var18 == 0) {
                  if (var2 != -724523093) {
                     return;
                  }

                  this.field4757[var12] = (var6 << 14) + (this.field4757[var12] & -2080769);
               }

               if (var18 == 32) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4757[var12] = (this.field4757[var12] & -16257) + (var6 << 7);
               }

               if (var18 == 1) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4760[var12] = (this.field4760[var12] & -16257) + (var6 << 7);
               }

               if (var18 == 33) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4760[var12] = (this.field4760[var12] & -128) + var6;
               }

               if (var18 == 5) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4766[var12] = (this.field4766[var12] & -16257) + (var6 << 7);
               }

               if (var18 == 37) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4766[var12] = var6 + (this.field4766[var12] & -128);
               }

               if (7 == var18) {
                  if (var2 != -724523093) {
                     return;
                  }

                  this.field4753[var12] = (var6 << 7) + (this.field4753[var12] & -16257);
               }

               if (39 == var18) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4753[var12] = (this.field4753[var12] & -128) + var6;
               }

               if (var18 == 10) {
                  if (var2 != -724523093) {
                     return;
                  }

                  this.field4771[var12] = (var6 << 7) + (this.field4771[var12] & -16257);
               }

               if (var18 == 42) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4771[var12] = var6 + (this.field4771[var12] & -128);
               }

               if (11 == var18) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4769[var12] = (this.field4769[var12] & -16257) + (var6 << 7);
               }

               if (var18 == 43) {
                  this.field4769[var12] = (this.field4769[var12] & -128) + var6;
               }

               if (64 == var18) {
                  if (var2 != -724523093) {
                     return;
                  }

                  if (var6 >= 64) {
                     this.field4748[var12] = this.field4748[var12] | 1;
                  } else {
                     this.field4748[var12] = this.field4748[var12] & -2;
                  }
               }

               if (65 == var18) {
                  if (var6 >= 64) {
                     if (var2 != -724523093) {
                        throw new IllegalStateException();
                     }

                     this.field4748[var12] = this.field4748[var12] | 2;
                  } else {
                     this.method8148(var12, -1000649374);
                     this.field4748[var12] = this.field4748[var12] & -3;
                  }
               }

               if (99 == var18) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4762[var12] = (this.field4762[var12] & 127) + (var6 << 7);
               }

               if (var18 == 98) {
                  this.field4762[var12] = var6 + (this.field4762[var12] & 16256);
               }

               if (101 == var18) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4762[var12] = 16384 + (this.field4762[var12] & 127) + (var6 << 7);
               }

               if (var18 == 100) {
                  if (var2 != -724523093) {
                     return;
                  }

                  this.field4762[var12] = 16384 + (this.field4762[var12] & 16256) + var6;
               }

               if (120 == var18) {
                  this.method8130(var12, 2110408396);
               }

               if (var18 == 121) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.method8136(var12, (byte)54);
               }

               if (var18 == 123) {
                  if (var2 != -724523093) {
                     return;
                  }

                  this.method8140(var12, (byte)0);
               }

               if (6 == var18) {
                  if (var2 != -724523093) {
                     return;
                  }

                  int var7 = this.field4762[var12];
                  if (var7 == 16384) {
                     if (var2 != -724523093) {
                        return;
                     }

                     this.field4763[var12] = (var6 << 7) + (this.field4763[var12] & -16257);
                  }
               }

               if (38 == var18) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  int var25 = this.field4762[var12];
                  if (var25 == 16384) {
                     this.field4763[var12] = (this.field4763[var12] & -128) + var6;
                  }
               }

               if (var18 == 16) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4750[var12] = (var6 << 7) + (this.field4750[var12] & -16257);
               }

               if (var18 == 48) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  this.field4750[var12] = var6 + (this.field4750[var12] & -128);
               }

               if (var18 == 81) {
                  if (var2 != -724523093) {
                     throw new IllegalStateException();
                  }

                  if (var6 >= 64) {
                     this.field4748[var12] = this.field4748[var12] | 4;
                  } else {
                     this.method8151(var12, -1140320208);
                     this.field4748[var12] = this.field4748[var12] & -5;
                  }
               }

               if (var18 == 17) {
                  method8158(this, var12, (this.field4764[var12] & -16257) + (var6 << 7), (byte)88);
               }

               if (var18 == 49) {
                  if (var2 != -724523093) {
                     return;
                  }

                  method8158(this, var12, (this.field4764[var12] & -128) + var6, (byte)89);
               }
            }
         } else if (var3 == 192) {
            if (var2 != -724523093) {
               throw new IllegalStateException();
            } else {
               int var11 = var1 & 15;
               int var17 = var1 >> 8 & 127;
               this.method8102(var11, this.field4757[var11] + var17, 1927507411);
            }
         } else if (var3 == 208) {
            if (var2 != -724523093) {
               throw new IllegalStateException();
            } else {
               int var10 = var1 & 15;
               int var16 = var1 >> 8 & 127;
               this.method8122(var10, var16, -1044212998);
            }
         } else if (224 == var3) {
            if (var2 != -724523093) {
               throw new IllegalStateException();
            } else {
               int var4 = var1 & 15;
               int var5 = (var1 >> 8 & 127) + (var1 >> 9 & 16256);
               this.method8124(var4, var5, (byte)-27);
            }
         } else {
            var3 = var1 & 0xFF;
            if (var3 == 255) {
               if (var2 != -724523093) {
                  throw new IllegalStateException();
               } else {
                  method8144(this, -1435920584);
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "nu.bq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dw")
   void method8104(int var1, int var2, int var3) {
      this.method8112(var1, var2, -487945971, -1078748678);
      if ((this.field4748[var1] & 2) != 0) {
         for (classNT var4 = (classNT)Deque2.method7962(this.field4768.field4659); var4 != null; var4 = (classNT)Deque2.method7968(this.field4768.field4659)) {
            if (-1057694491 * var4.field4732 == var1 && var4.field4727 * 67131625 < 0) {
               this.field4765[var1][-370277124 * var4.field4729] = null;
               this.field4765[var1][var2] = var4;
               int var5 = (var4.field4725 * -359606667 * -403336933 * var4.field4733 >> 12) + var4.field4721 * 464921419;
               var4.field4721 = var4.field4721 + -1901450660 * (var2 - 1701614193 * var4.field4729 << 8);
               var4.field4733 = -300850925 * (var5 - var4.field4721 * 1732100978);
               var4.field4725 = 480104448;
               var4.field4729 = -685332037 * var2;
               return;
            }
         }
      }

      classNR var8 = (classNR)HashTable.method13432(this.field4770, this.field4754[var1]);
      if (null != var8) {
         if (null != var8.field4704[var2]) {
            classAJ var9 = var8.field4704[var2].method700(2019781816);
            if (var9 != null) {
               classNT var6 = new classNT();
               var6.field4732 = -1566998291 * var1;
               var6.field4744 = var8;
               var6.field4741 = var9;
               var6.field4745 = var8.field4711[var2];
               var6.field4720 = var8.field4709[var2] * 15946373;
               var6.field4729 = var2 * -685332037;
               var6.field4735 = 780512417 * (1039261828 + var8.field4708 * -127646999 * var3 * var3 * var8.field4706[var2] >> 11);
               var6.field4723 = -225319163 * (var8.field4707[var2] & 2132532882);
               var6.field4721 = ((var2 << 8) - (var8.field4705[var2] & 1608534758)) * 1567246768;
               var6.field4731 = 0;
               var6.field4736 = 0;
               var6.field4734 = 0;
               var6.field4727 = 1108414625;
               var6.field4726 = 0;
               if (0 == this.field4750[var1]) {
                  var6.field4742 = classAM.method472(
                     var9, method8162(this, var6, -816538955), this.method8168(var6, 1966662171), this.method8171(var6, 1667012717)
                  );
               } else {
                  var6.field4742 = classAM.method472(var9, method8162(this, var6, -420856506), 0, this.method8171(var6, 1870930798));
                  this.method8107(var6, var8.field4705[var2] < 0, 881975798);
               }

               if (var8.field4705[var2] < 0) {
                  var6.field4742.method541(-1);
               }

               if (-1530244505 * var6.field4720 >= 0) {
                  classNT var7 = this.field4772[var1][-662556096 * var6.field4720];
                  if (var7 != null && 67131625 * var7.field4727 < 0) {
                     this.field4765[var1][var7.field4729 * -549347805] = null;
                     var7.field4727 = 0;
                  }

                  this.field4772[var1][var6.field4720 * -1530244505] = var6;
               }

               this.field4768.field4659.method7946(var6);
               this.field4765[var1][var2] = var6;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;II)V")
   @ObfuscatedName("vt")
   public static void method8125(classNU var0, int var1, int var2) {
      var0.field4759[var1] = var2;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ev")
   void method8131(int var1) {
      for (classNT var2 = (classNT)this.field4768.field4659.method7957(); var2 != null; var2 = (classNT)this.field4768.field4659.method7965()) {
         if (var1 < 0 || var1 == -1057694491 * var2.field4732) {
            if (null != var2.field4742) {
               var2.field4742.method583(WidgetConfigNode.field4136 * 675076789 / 100);
               if (var2.field4742.method595()) {
                  this.field4768.field4660.method618(var2.field4742);
               }

               classNT.method8044(var2, 2139097596);
            }

            if (var2.field4727 * 67131625 < 0) {
               this.field4765[-1057694491 * var2.field4732][-107170957 * var2.field4729] = null;
            }

            var2.vmethod398();
         }
      }
   }

   public classNU(classAK var1) {
      this.field4749 = 0;
      this.field4748 = new int[16];
      this.field4750 = new int[16];
      this.field4751 = new int[16];
      this.field4752 = -635263232;
      this.field4761 = 1159529408;
      this.field4753 = new int[16];
      this.field4771 = new int[16];
      this.field4769 = new int[16];
      this.field4756 = new int[16];
      this.field4754 = new int[16];
      this.field4757 = new int[16];
      this.field4759 = new int[16];
      this.field4760 = new int[16];
      this.field4766 = new int[16];
      this.field4762 = new int[16];
      this.field4763 = new int[16];
      this.field4764 = new int[16];
      this.field4765 = new classNT[16][128];
      this.field4772 = new classNT[16][128];
      this.field4767 = new classNC();
      this.field4768 = new classND(this);
      this.field4758 = null;
      this.field4758 = var1;
      this.field4770 = new HashTable(128);
      method8144(this, -932703825);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ds")
   void method8118(int var1, int var2, int var3) {
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   @Override
   protected int vmethod28() {
      return 0;
   }

   @ObfuscatedSignature(descriptor = "(Lnu;B)I")
   @ObfuscatedName("pf")
   public static int method8052(classNU var0, byte var1) {
      if (var0 == null) {
         return var0.method8055(var1);
      } else {
         try {
            return 1238197527 * var0.field4752;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "nu.af(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bx")
   @Override
   protected void vmethod36(int[] var1, int var2, int var3) {
      if (this.field4767.method7817()) {
         int var4 = this.field4761 * -167767442 * this.field4767.field4647 / (1966081859 * WidgetConfigNode.field4136);

         do {
            long var5 = (long)var3 * var4 + -8970596226586884259L * this.field4777;
            if (this.field4778 * 5174971452106306489L - var5 >= 0L) {
               this.field4777 = 1512448154803324149L * var5;
               break;
            }

            int var7 = (int)((var4 + (this.field4778 * 5174971452106306489L - -8970596226586884259L * this.field4777) - 1L) / var4);
            this.field4777 += (long)var7 * var4 * 1512448154803324149L;
            this.field4768.vmethod30(var1, var2, var7);
            var2 += var7;
            var3 -= var7;
            method8176(this, 996302914);
         } while (this.field4767.method7817());
      }

      this.field4768.vmethod30(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bc")
   @Override
   protected void vmethod37(int[] var1, int var2, int var3) {
      if (this.field4767.method7817()) {
         int var4 = this.field4761 * -1423260313 * this.field4767.field4647 / (675076789 * WidgetConfigNode.field4136);

         do {
            long var5 = (long)var3 * var4 + -8970596226586884259L * this.field4777;
            if (this.field4778 * 5174971452106306489L - var5 >= 0L) {
               this.field4777 = 1512448154803324149L * var5;
               break;
            }

            int var7 = (int)((var4 + (this.field4778 * 5174971452106306489L - -8970596226586884259L * this.field4777) - 1L) / var4);
            this.field4777 += (long)var7 * var4 * 1512448154803324149L;
            this.field4768.vmethod30(var1, var2, var7);
            var2 += var7;
            var3 -= var7;
            method8176(this, 725777762);
         } while (this.field4767.method7817());
      }

      this.field4768.vmethod30(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bp")
   @Override
   protected void vmethod38(int[] var1, int var2, int var3) {
      if (this.field4767.method7817()) {
         int var4 = this.field4761 * -1423260313 * this.field4767.field4647 / (675076789 * WidgetConfigNode.field4136);

         do {
            long var5 = (long)var3 * var4 + -8970596226586884259L * this.field4777;
            if (this.field4778 * 5174971452106306489L - var5 >= 0L) {
               this.field4777 = 1512448154803324149L * var5;
               break;
            }

            int var7 = (int)((var4 + (this.field4778 * 5174971452106306489L - -8970596226586884259L * this.field4777) - 1L) / var4);
            this.field4777 += (long)var7 * var4 * 1512448154803324149L;
            this.field4768.vmethod30(var1, var2, var7);
            var2 += var7;
            var3 -= var7;
            method8176(this, -1026147751);
         } while (this.field4767.method7817());
      }

      this.field4768.vmethod30(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("br")
   @Override
   protected void vmethod39(int[] var1, int var2, int var3) {
      if (this.field4767.method7817()) {
         int var4 = this.field4761 * 493773987 * this.field4767.field4647 / (675076789 * WidgetConfigNode.field4136);

         do {
            long var5 = (long)var3 * var4 + -8970596226586884259L * this.field4777;
            if (this.field4778 * 5174971452106306489L - var5 >= 0L) {
               this.field4777 = 1512448154803324149L * var5;
               break;
            }

            int var7 = (int)((var4 + (this.field4778 * 5174971452106306489L - -8970596226586884259L * this.field4777) - 1L) / var4);
            this.field4777 += (long)var7 * var4 * 1512448154803324149L;
            this.field4768.vmethod30(var1, var2, var7);
            var2 += var7;
            var3 -= var7;
            method8176(this, -495862168);
         } while (this.field4767.method7817());
      }

      this.field4768.vmethod30(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bo")
   @Override
   protected void vmethod40(int var1) {
      if (this.field4767.method7817()) {
         int var2 = 1968952280 * this.field4761 * this.field4767.field4647 / (WidgetConfigNode.field4136 * 19588851);

         do {
            long var3 = (long)var1 * var2 + -8970596226586884259L * this.field4777;
            if (this.field4778 * 5174971452106306489L - var3 >= 0L) {
               this.field4777 = 1512448154803324149L * var3;
               break;
            }

            int var5 = (int)((var2 + (this.field4778 * 5174971452106306489L - -8970596226586884259L * this.field4777) - 1L) / var2);
            this.field4777 += (long)var5 * var2 * 1512448154803324149L;
            this.field4768.vmethod31(var5);
            var1 -= var5;
            method8176(this, -95849439);
         } while (this.field4767.method7817());
      }

      this.field4768.vmethod31(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;I)I")
   @ObfuscatedName("ps")
   public static int method8162(classNU var0, classNT var1, int var2) {
      if (var0 == null) {
         return var0.method8166(var1, var2);
      } else {
         try {
            int var10;
            var10 = (-403336933 * var1.field4733 * -359606667 * var1.field4725 >> 12) + var1.field4721 * -1276571151;
            var10 += (var0.field4759[var1.field4732 * -1057694491] - 8192) * var0.field4763[-1057694491 * var1.field4732] >> 12;
            classNY var4 = var1.field4745;
            label56:
            if (-771094009 * var4.field4790 > 0) {
               if (var2 >= 1204216895) {
                  throw new IllegalStateException();
               }

               if (var4.field4794 * -1472637671 <= 0) {
                  if (var2 >= 1204216895) {
                     throw new IllegalStateException();
                  }

                  if (var0.field4760[var1.field4732 * -1057694491] <= 0) {
                     break label56;
                  }

                  if (var2 >= 1204216895) {
                     throw new IllegalStateException();
                  }
               }

               int var5 = -1472637671 * var4.field4794 << 2;
               int var6 = var4.field4788 * -1122448781 << 1;
               if (var1.field4728 * -1955754893 < var6) {
                  if (var2 >= 1204216895) {
                     throw new IllegalStateException();
                  }

                  var5 = -1955754893 * var1.field4728 * var5 / var6;
               }

               var5 += var0.field4760[-1057694491 * var1.field4732] >> 7;
               double var7 = Math.sin((1237307259 * var1.field4737 & 511) * 0.01227184630308513);
               var10 += (int)(var5 * var7);
            }

            int var12 = (int)(var1.field4741.field187 * 256 * Math.pow(2.0, 3.255208333333333E-4 * var10) / (675076789 * WidgetConfigNode.field4136) + 0.5);
            int var10000;
            if (var12 < 1) {
               if (var2 >= 1204216895) {
                  throw new IllegalStateException();
               }

               var10000 = 1;
            } else {
               var10000 = var12;
            }

            return var10000;
         } catch (RuntimeException var9) {
            throw classEG.method3899(var9, "nu.bs(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bu")
   @Override
   protected void vmethod41(int var1) {
      if (this.field4767.method7817()) {
         int var2 = 1556367119 * this.field4761 * this.field4767.field4647 / (WidgetConfigNode.field4136 * -179221373);

         do {
            long var3 = (long)var1 * var2 + -8970596226586884259L * this.field4777;
            if (this.field4778 * 5174971452106306489L - var3 >= 0L) {
               this.field4777 = 1512448154803324149L * var3;
               break;
            }

            int var5 = (int)((var2 + (this.field4778 * 5174971452106306489L - -8970596226586884259L * this.field4777) - 1L) / var2);
            this.field4777 += (long)var5 * var2 * 1512448154803324149L;
            this.field4768.vmethod31(var5);
            var1 -= var5;
            method8176(this, 2111081720);
         } while (this.field4767.method7817());
      }

      this.field4768.vmethod31(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ba")
   @Override
   protected void vmethod42(int var1) {
      if (this.field4767.method7817()) {
         int var2 = -1423260313 * this.field4761 * this.field4767.field4647 / (WidgetConfigNode.field4136 * 675076789);

         do {
            long var3 = (long)var1 * var2 + -8970596226586884259L * this.field4777;
            if (this.field4778 * 5174971452106306489L - var3 >= 0L) {
               this.field4777 = 1512448154803324149L * var3;
               break;
            }

            int var5 = (int)((var2 + (this.field4778 * 5174971452106306489L - -8970596226586884259L * this.field4777) - 1L) / var2);
            this.field4777 += (long)var5 * var2 * 1512448154803324149L;
            this.field4768.vmethod31(var5);
            var1 -= var5;
            method8176(this, -1748465743);
         } while (this.field4767.method7817());
      }

      this.field4768.vmethod31(var1);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("da")
   void method8119(int var1, int var2, int var3) {
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cu")
   public int method8053() {
      return 1238197527 * this.field4752;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;II)V")
   @ObfuscatedName("di")
   public static void method8157(classNU var0, int var1, int var2) {
      var0.field4764[var1] = var2;
      var0.field4751[var1] = (int)(2097152.0 * Math.pow(2.0, 5.493164E-4F * var2) + 0.5);
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("an")
   @Override
   protected classAH vmethod33() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(Lnu;IIB)V")
   @ObfuscatedName("zh")
   public static void method8158(classNU var0, int var1, int var2, byte var3) {
      if (var0 == null) {
         var0.method8160(var1, var1, var3);
      }

      try {
         var0.field4764[var1] = var2;
         var0.field4751[var1] = (int)(2097152.0 * Math.pow(2.0, 5.493164E-4F * var2) + 0.5);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.bf(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ak")
   void method8112(int var1, int var2, int var3, int var4) {
      try {
         classNT var5 = this.field4765[var1][var2];
         if (var5 != null) {
            this.field4765[var1][var2] = null;
            if (0 != (this.field4748[var1] & 2)) {
               if (var4 >= -549981141) {
                  throw new IllegalStateException();
               }

               for (classNT var6 = (classNT)this.field4768.field4659.method7957(); var6 != null; var6 = (classNT)this.field4768.field4659.method7965()) {
                  if (-1057694491 * var6.field4732 == -1057694491 * var5.field4732) {
                     if (var4 >= -549981141) {
                        throw new IllegalStateException();
                     }

                     if (var6.field4727 * 67131625 < 0) {
                        if (var4 >= -549981141) {
                           throw new IllegalStateException();
                        }

                        if (var5 != var6) {
                           if (var4 >= -549981141) {
                              throw new IllegalStateException();
                           }

                           var5.field4727 = 0;
                           break;
                        }
                     }
                  }
               }
            } else {
               var5.field4727 = 0;
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "nu.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lno;Lva;Lat;)Z")
   @ObfuscatedName("cl")
   public boolean method8056(classNO var1, IndexDataBase var2, classAT var3) {
      synchronized (this.field4758) {
         boolean var5 = true;
         synchronized (this.field4755) {
            this.field4755.clear();
         }

         var1.method7980();

         for (classNJ var17 = (classNJ)var1.field4692.method13423(); var17 != null; var17 = (classNJ)var1.field4692.method13427()) {
            int var7 = (int)var17.field6550;
            classNR var8 = (classNR)HashTable.method13432(this.field4770, var7);
            if (null == var8) {
               var8 = classJI.method6411(var2, var7, -885874591);
               if (var8 == null) {
                  var5 = false;
                  continue;
               }

               this.field4770.method13418(var8, var7);
            }

            if (!var8.method8030(var3, var17.field4672, 1570631702)) {
               var5 = false;
            } else if (this.field4755 != null) {
               synchronized (this.field4755) {
                  for (classAU var11 : var8.field4713) {
                     this.field4755.add(new classNS(var17.field4673 * 950357568, var11));
                  }
               }
            }
         }

         if (var5) {
            var1.method7983();
         }

         return var5;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;)I")
   @ObfuscatedName("yz")
   public static int method8170(classNU var0, classNT var1) {
      int var2 = var0.field4771[var1.field4732 * -1057694491];
      return var2 < 236052648
         ? 1654630509 + var2 * var1.field4723 * -258945897 >> 6
         : 840421272 - ((209533444 - -502360857 * var1.field4723) * (1946791120 - var2) + 1449524227 >> 6);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("eq")
   void method8141(int var1) {
      for (classNT var2 = (classNT)this.field4768.field4659.method7957(); var2 != null; var2 = (classNT)this.field4768.field4659.method7965()) {
         if ((var1 < 0 || var1 == var2.field4732 * 186984351) && var2.field4727 * 1503486442 < 0) {
            this.field4765[var2.field4732 * 1974828459][var2.field4729 * -2038165215] = null;
            var2.field4727 = 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cs")
   public void method8064() {
      synchronized (this.field4758) {
         for (classNR var2 = (classNR)this.field4770.method13423(); var2 != null; var2 = (classNR)this.field4770.method13427()) {
            classNR.method8033(var2, (byte)80);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;I)I")
   @ObfuscatedName("bl")
   int method8171(classNT var1, int var2) {
      try {
         int var3 = this.field4771[var1.field4732 * -1057694491];
         if (var3 < 8192) {
            if (var2 <= 1660644709) {
               throw new IllegalStateException();
            } else {
               return 32 + var3 * var1.field4723 * -1066414643 >> 6;
            }
         } else {
            return 16384 - ((128 - -1066414643 * var1.field4723) * (16384 - var3) + 32 >> 6);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.bl(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ck")
   public void method8068() {
      synchronized (this.field4758) {
         for (classNR var2 = (classNR)this.field4770.method13423(); var2 != null; var2 = (classNR)this.field4770.method13427()) {
            var2.vmethod398();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;Z)V")
   @ObfuscatedName("ft")
   public static void method8108(classNU var0, classNT var1, boolean var2) {
      if (var0 == null) {
         var0.vmethod27();
      }

      int var3 = var1.field4741.field189.length;
      int var4;
      if (var2 && var1.field4741.field190) {
         int var5 = var3 + var3 - var1.field4741.field192;
         var4 = (int)((long)var5 * var0.field4750[var1.field4732 * -1057694491] >> 6);
         var3 <<= 8;
         if (var4 >= var3) {
            var4 = var3 + var3 - 1 - var4;
            var1.field4742.method569(true);
         }
      } else {
         var4 = (int)((long)var3 * var0.field4750[var1.field4732 * -916402755] >> 6);
      }

      var1.field4742.method564(var4);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ca")
   public void method8069() {
      synchronized (this.field4758) {
         for (classNR var2 = (classNR)this.field4770.method13423(); var2 != null; var2 = (classNR)this.field4770.method13427()) {
            var2.vmethod398();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lno;Z)V")
   @ObfuscatedName("cf")
   public void method8072(classNO var1, boolean var2) {
      synchronized (this.field4758) {
         this.method8079((byte)38);
         this.field4767.method7810(var1.field4691);
         this.field4779 = var2;
         this.field4777 = 0L;
         int var4 = this.field4767.method7822();

         for (int var5 = 0; var5 < var4; var5++) {
            this.field4767.method7824(var5);
            classTO.method10989(this.field4767, var5);
            this.field4767.method7825(var5);
         }

         this.field4775 = classNC.method7851(this.field4767) * -1778762749;
         this.field4776 = this.field4767.field4654[-1076146517 * this.field4775] * 596597749;
         this.field4778 = this.field4767.method7846(852899933 * this.field4776) * -7106189560981577079L;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lno;Z)V")
   @ObfuscatedName("cn")
   public void method8073(classNO var1, boolean var2) {
      synchronized (this.field4758) {
         this.method8079((byte)-2);
         this.field4767.method7810(var1.field4691);
         this.field4779 = var2;
         this.field4777 = 0L;
         int var4 = this.field4767.method7822();

         for (int var5 = 0; var5 < var4; var5++) {
            this.field4767.method7824(var5);
            classTO.method10989(this.field4767, var5);
            this.field4767.method7825(var5);
         }

         this.field4775 = classNC.method7851(this.field4767) * -1778762749;
         this.field4776 = this.field4767.field4654[-1076146517 * this.field4775] * 596597749;
         this.field4778 = this.field4767.method7846(852899933 * this.field4776) * -7106189560981577079L;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cz")
   public void method8075() {
      synchronized (this.field4758) {
         this.field4767.method7813();
         method8144(this, -2104063806);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cw")
   public void method8076() {
      synchronized (this.field4758) {
         this.field4767.method7813();
         method8144(this, -1953164524);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cj")
   public void method8077() {
      synchronized (this.field4758) {
         this.field4767.method7813();
         method8144(this, -1137408671);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cx")
   public boolean method8081() {
      synchronized (this.field4758) {
         return this.field4767.method7817();
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("do")
   void method8113(int var1, int var2, int var3) {
      classNT var4 = this.field4765[var1][var2];
      if (var4 != null) {
         this.field4765[var1][var2] = null;
         if (0 != (this.field4748[var1] & 2)) {
            for (classNT var5 = (classNT)this.field4768.field4659.method7957(); var5 != null; var5 = (classNT)this.field4768.field4659.method7965()) {
               if (-1057694491 * var5.field4732 == -1057694491 * var4.field4732 && var5.field4727 * 67131625 < 0 && var4 != var5) {
                  var4.field4727 = 0;
                  break;
               }
            }
         } else {
            var4.field4727 = 0;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;)V")
   @ObfuscatedName("du")
   public static void method8070(classNU var0) {
      if (var0 == null) {
         var0.method8080();
      }

      synchronized (var0.field4758) {
         for (classNR var2 = (classNR)var0.field4770.method13423(); var2 != null; var2 = (classNR)var0.field4770.method13427()) {
            var2.vmethod398();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dc")
   public boolean method8088() {
      synchronized (this.field4758) {
         return this.field4770.method13417() > 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIS)V")
   @ObfuscatedName("ap")
   public void method8092(int var1, int var2, short var3) {
      try {
         synchronized (this.field4758) {
            this.method8098(var1, var2, 110238855);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "nu.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ce")
   public boolean method8089() {
      synchronized (this.field4758) {
         return this.field4770.method13417() > 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;II)V")
   @ObfuscatedName("ar")
   public static void method8126(classNU var0, int var1, int var2) {
      var0.field4759[var1] = var2;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dm")
   public void method8093(int var1, int var2) {
      synchronized (this.field4758) {
         this.method8098(var1, var2, 1220472108);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dx")
   public void method8094(int var1, int var2) {
      synchronized (this.field4758) {
         this.method8098(var1, var2, -644509436);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dy")
   public void method8095(int var1, int var2) {
      synchronized (this.field4758) {
         this.method8098(var1, var2, -2114096052);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;)V")
   @ObfuscatedName("xq")
   public static void method8078(classNU var0) {
      synchronized (var0.field4758) {
         var0.field4767.method7813();
         method8144(var0, -711855675);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("di")
   public void method8096(int var1, int var2) {
      synchronized (this.field4758) {
         this.method8098(var1, var2, -2126562125);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ec")
   void method8159(int var1, int var2) {
      this.field4764[var1] = var2;
      this.field4751[var1] = (int)(2097152.0 * Math.pow(2.0, 5.493164E-4F * var2) + 0.5);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ai")
   void method8102(int var1, int var2, int var3) {
      try {
         if (var2 != this.field4754[var1]) {
            this.field4754[var1] = var2;

            for (int var4 = 0; var4 < 128; var4++) {
               if (var3 <= 1591084815) {
                  throw new IllegalStateException();
               }

               this.field4772[var1][var4] = null;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "nu.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dk")
   void method8100(int var1, int var2) {
      this.field4756[var1] = var2;
      this.field4757[var1] = var2 & -128;
      this.method8102(var1, var2, 2098248730);
   }

   @ObfuscatedSignature(descriptor = "(Lnu;I)V")
   @ObfuscatedName("kx")
   public static void method8144(classNU var0, int var1) {
      if (var0 == null) {
         var0.method8147(var1);
      } else {
         try {
            var0.method8130(-1, 2100279562);
            var0.method8136(-1, (byte)19);

            for (int var2 = 0; var2 < 16; var2++) {
               var0.field4754[var2] = var0.field4756[var2];
            }

            for (int var4 = 0; var4 < 16; var4++) {
               if (var1 >= -676520024) {
                  throw new IllegalStateException();
               }

               var0.field4757[var4] = var0.field4756[var4] & -128;
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "nu.bb(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnu;I)V")
   @ObfuscatedName("aj")
   public static void method8176(classNU var0, int var1) {
      if (var0 == null) {
         var0.method8143(var1);
      } else {
         try {
            int var2 = -1076146517 * var0.field4775;
            int var3 = 852899933 * var0.field4776;
            long var4 = 5174971452106306489L * var0.field4778;

            label69:
            while (true) {
               if (var0.field4776 * 852899933 != var3) {
                  var0.field4775 = -1778762749 * var2;
                  var0.field4776 = var3 * 596597749;
                  var0.field4778 = var4 * -7106189560981577079L;
                  return;
               }

               while (true) {
                  if (var3 == var0.field4767.field4654[var2]) {
                     var0.field4767.method7824(var2);
                     int var6 = var0.field4767.method7835(var2);
                     if (1 != var6) {
                        if (0 != (var6 & 128)) {
                           if (var1 == -324749371) {
                              throw new IllegalStateException();
                           }

                           var0.method8153(var6, -724523093);
                        }

                        classTO.method10989(var0.field4767, var2);
                        var0.field4767.method7825(var2);
                        continue;
                     }

                     if (var1 == -324749371) {
                        return;
                     }

                     var0.field4767.method7832();
                     var0.field4767.method7825(var2);
                     if (var0.field4767.method7857()) {
                        if (!var0.field4779) {
                           break label69;
                        }

                        if (var1 == -324749371) {
                           throw new IllegalStateException();
                        }

                        if (var3 == 0) {
                           break label69;
                        }

                        if (var1 == -324749371) {
                           throw new IllegalStateException();
                        }

                        classNC.method7859(var0.field4767, var4);
                     }
                  }

                  var2 = classNC.method7851(var0.field4767);
                  var3 = var0.field4767.field4654[var2];
                  var4 = var0.field4767.method7846(var3);
                  break;
               }
            }

            method8144(var0, -1300338349);
            var0.field4767.method7813();
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "nu.bv(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;)V")
   @ObfuscatedName("kb")
   public static void method8065(classNU var0) {
      synchronized (var0.field4758) {
         for (classNR var2 = (classNR)var0.field4770.method13423(); var2 != null; var2 = (classNR)var0.field4770.method13427()) {
            classNR.method8033(var2, (byte)110);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("at")
   @Override
   protected classAH vmethod27() {
      return this.field4768;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bt")
   void method8122(int var1, int var2, int var3) {
      try {
         ;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.bt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dh")
   void method8105(int var1, int var2, int var3) {
      this.method8112(var1, var2, 64, -975565602);
      if ((this.field4748[var1] & 2) != 0) {
         for (classNT var4 = (classNT)Deque2.method7962(this.field4768.field4659); var4 != null; var4 = (classNT)Deque2.method7968(this.field4768.field4659)) {
            if (-1057694491 * var4.field4732 == var1 && var4.field4727 * 67131625 < 0) {
               this.field4765[var1][-107170957 * var4.field4729] = null;
               this.field4765[var1][var2] = var4;
               int var5 = (var4.field4725 * -359606667 * -403336933 * var4.field4733 >> 12) + var4.field4721 * -1276571151;
               var4.field4721 = var4.field4721 + -665600239 * (var2 - -107170957 * var4.field4729 << 8);
               var4.field4733 = -300850925 * (var5 - var4.field4721 * -1276571151);
               var4.field4725 = 480104448;
               var4.field4729 = -685332037 * var2;
               return;
            }
         }
      }

      classNR var8 = (classNR)HashTable.method13432(this.field4770, this.field4754[var1]);
      if (null != var8) {
         if (null != var8.field4704[var2]) {
            classAJ var9 = var8.field4704[var2].method700(1835537839);
            if (var9 != null) {
               classNT var6 = new classNT();
               var6.field4732 = -1566998291 * var1;
               var6.field4744 = var8;
               var6.field4741 = var9;
               var6.field4745 = var8.field4711[var2];
               var6.field4720 = var8.field4709[var2] * 1130932567;
               var6.field4729 = var2 * -685332037;
               var6.field4735 = 780512417 * (1024 + var8.field4708 * -127646999 * var3 * var3 * var8.field4706[var2] >> 11);
               var6.field4723 = -225319163 * (var8.field4707[var2] & 255);
               var6.field4721 = ((var2 << 8) - (var8.field4705[var2] & 32767)) * -665600239;
               var6.field4731 = 0;
               var6.field4736 = 0;
               var6.field4734 = 0;
               var6.field4727 = -371851097;
               var6.field4726 = 0;
               if (0 == this.field4750[var1]) {
                  var6.field4742 = classAM.method472(
                     var9, method8162(this, var6, 397490094), this.method8168(var6, 1285039116), this.method8171(var6, 1760746250)
                  );
               } else {
                  var6.field4742 = classAM.method472(var9, method8162(this, var6, 215717452), 0, this.method8171(var6, 1690094063));
                  this.method8107(var6, var8.field4705[var2] < 0, 881975798);
               }

               if (var8.field4705[var2] < 0) {
                  var6.field4742.method541(-1);
               }

               if (-1530244505 * var6.field4720 >= 0) {
                  classNT var7 = this.field4772[var1][-1530244505 * var6.field4720];
                  if (var7 != null && 67131625 * var7.field4727 < 0) {
                     this.field4765[var1][var7.field4729 * -107170957] = null;
                     var7.field4727 = 0;
                  }

                  this.field4772[var1][var6.field4720 * -1530244505] = var6;
               }

               this.field4768.field4659.method7946(var6);
               this.field4765[var1][var2] = var6;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("az")
   public void method8048(int var1, int var2) {
      try {
         synchronized (this.field4758) {
            this.field4752 = 853156519 * var1;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "nu.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cv")
   public void method8062() {
      if (this.field4755 != null) {
         if (field4746 != null) {
            field4746.set(true);
         }

         field4746 = new AtomicBoolean(false);
         AtomicBoolean var1 = field4746;
         if (field4747 == null) {
            int var2 = Runtime.getRuntime().availableProcessors();
            field4747 = new ThreadPoolExecutor(0, var2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new classNF(this));
         }

         field4747.submit(new classNG(this, var1));
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;Z)V")
   @ObfuscatedName("dn")
   void method8109(classNT var1, boolean var2) {
      int var3 = var1.field4741.field189.length;
      int var4;
      if (var2 && var1.field4741.field190) {
         int var5 = var3 + var3 - var1.field4741.field192;
         var4 = (int)((long)var5 * this.field4750[var1.field4732 * -1057694491] >> 6);
         var3 <<= 8;
         if (var4 >= var3) {
            var4 = var3 + var3 - 1 - var4;
            var1.field4742.method569(true);
         }
      } else {
         var4 = (int)((long)var3 * this.field4750[var1.field4732 * 1187247753] >> 6);
      }

      var1.field4742.method564(var4);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   @Override
   protected int vmethod32() {
      try {
         return 0;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "nu.ac(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dl")
   void method8114(int var1, int var2, int var3) {
      classNT var4 = this.field4765[var1][var2];
      if (var4 != null) {
         this.field4765[var1][var2] = null;
         if (0 != (this.field4748[var1] & 2)) {
            for (classNT var5 = (classNT)this.field4768.field4659.method7957(); var5 != null; var5 = (classNT)this.field4768.field4659.method7965()) {
               if (-1410583715 * var5.field4732 == -50448318 * var4.field4732 && var5.field4727 * 67131625 < 0 && var4 != var5) {
                  var4.field4727 = 0;
                  break;
               }
            }
         } else {
            var4.field4727 = 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dq")
   void method8115(int var1, int var2, int var3) {
      classNT var4 = this.field4765[var1][var2];
      if (var4 != null) {
         this.field4765[var1][var2] = null;
         if (0 != (this.field4748[var1] & 2)) {
            for (classNT var5 = (classNT)this.field4768.field4659.method7957(); var5 != null; var5 = (classNT)this.field4768.field4659.method7965()) {
               if (-1057694491 * var5.field4732 == -1057694491 * var4.field4732 && var5.field4727 * 67131625 < 0 && var4 != var5) {
                  var4.field4727 = 0;
                  break;
               }
            }
         } else {
            var4.field4727 = 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ci")
   public boolean method8090() {
      synchronized (this.field4758) {
         return this.field4770.method13417() > 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dj")
   void method8120(int var1, int var2, int var3) {
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("am")
   @Override
   protected classAH vmethod44() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dp")
   void method8123(int var1, int var2) {
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ea")
   void method8127(int var1, int var2) {
      this.field4759[var1] = var2;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ek")
   void method8128(int var1, int var2) {
      this.field4759[var1] = var2;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("es")
   void method8129(int var1, int var2) {
      this.field4759[var1] = var2;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ed")
   void method8132(int var1) {
      for (classNT var2 = (classNT)this.field4768.field4659.method7957(); var2 != null; var2 = (classNT)this.field4768.field4659.method7965()) {
         if (var1 < 0 || var1 == -1311139638 * var2.field4732) {
            if (null != var2.field4742) {
               var2.field4742.method583(WidgetConfigNode.field4136 * -1572993699 / -2096733984);
               if (var2.field4742.method595()) {
                  this.field4768.field4660.method618(var2.field4742);
               }

               classNT.method8044(var2, 2005278781);
            }

            if (var2.field4727 * -1690390608 < 0) {
               this.field4765[-1057694491 * var2.field4732][-107170957 * var2.field4729] = null;
            }

            var2.vmethod398();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lno;Lva;Lat;)Z")
   @ObfuscatedName("cq")
   public boolean method8057(classNO var1, IndexDataBase var2, classAT var3) {
      synchronized (this.field4758) {
         boolean var5 = true;
         synchronized (this.field4755) {
            this.field4755.clear();
         }

         var1.method7980();

         for (classNJ var17 = (classNJ)var1.field4692.method13423(); var17 != null; var17 = (classNJ)var1.field4692.method13427()) {
            int var7 = (int)var17.field6550;
            classNR var8 = (classNR)HashTable.method13432(this.field4770, var7);
            if (null == var8) {
               var8 = classJI.method6411(var2, var7, -885874591);
               if (var8 == null) {
                  var5 = false;
                  continue;
               }

               this.field4770.method13418(var8, var7);
            }

            if (!var8.method8030(var3, var17.field4672, 2003953031)) {
               var5 = false;
            } else if (this.field4755 != null) {
               synchronized (this.field4755) {
                  for (classAU var11 : var8.field4713) {
                     this.field4755.add(new classNS(var17.field4673 * 1220483629, var11));
                  }
               }
            }
         }

         if (var5) {
            var1.method7983();
         }

         return var5;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("eg")
   void method8138(int var1) {
      if (var1 >= 0) {
         this.field4753[var1] = 12800;
         this.field4771[var1] = 1539832493;
         this.field4769[var1] = 1792727278;
         this.field4759[var1] = 1009023550;
         this.field4760[var1] = 0;
         this.field4766[var1] = 8192;
         this.method8148(var1, -1622798110);
         this.method8151(var1, -1140320208);
         this.field4748[var1] = 0;
         this.field4762[var1] = 2093241976;
         this.field4763[var1] = 256;
         this.field4750[var1] = 0;
         method8158(this, var1, 1908540761, (byte)61);
      } else {
         for (int var2 = 0; var2 < 16; var2++) {
            this.method8136(var2, (byte)-72);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("en")
   void method8139(int var1) {
      if (var1 >= 0) {
         this.field4753[var1] = 12800;
         this.field4771[var1] = 8192;
         this.field4769[var1] = 16383;
         this.field4759[var1] = 8192;
         this.field4760[var1] = 0;
         this.field4766[var1] = 8192;
         this.method8148(var1, -1305041572);
         this.method8151(var1, -1140320208);
         this.field4748[var1] = 0;
         this.field4762[var1] = 32767;
         this.field4763[var1] = 256;
         this.field4750[var1] = 0;
         method8158(this, var1, 8192, (byte)74);
      } else {
         for (int var2 = 0; var2 < 16; var2++) {
            this.method8136(var2, (byte)-17);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;)Z")
   @ObfuscatedName("zg")
   public static boolean method8082(classNU var0) {
      if (var0 == null) {
         return var0.method8084();
      } else {
         synchronized (var0.field4758) {
            return var0.field4767.method7817();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ex")
   void method8142(int var1) {
      for (classNT var2 = (classNT)this.field4768.field4659.method7957(); var2 != null; var2 = (classNT)this.field4768.field4659.method7965()) {
         if ((var1 < 0 || var1 == var2.field4732 * -1057694491) && var2.field4727 * 67131625 < 0) {
            this.field4765[var2.field4732 * -1057694491][var2.field4729 * -107170957] = null;
            var2.field4727 = 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cr")
   public void method8049(int var1) {
      synchronized (this.field4758) {
         this.field4752 = 853156519 * var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ew")
   void method8145() {
      this.method8130(-1, 2132120614);
      this.method8136(-1, (byte)-18);

      for (int var1 = 0; var1 < 16; var1++) {
         this.field4754[var1] = this.field4756[var1];
      }

      for (int var2 = 0; var2 < 16; var2++) {
         this.field4757[var2] = this.field4756[var2] & 1137512112;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("eu")
   void method8146() {
      this.method8130(-1, 2093435522);
      this.method8136(-1, (byte)-17);

      for (int var1 = 0; var1 < 16; var1++) {
         this.field4754[var1] = this.field4756[var1];
      }

      for (int var2 = 0; var2 < 16; var2++) {
         this.field4757[var2] = this.field4756[var2] & -128;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("el")
   void method8133(int var1) {
      for (classNT var2 = (classNT)this.field4768.field4659.method7957(); var2 != null; var2 = (classNT)this.field4768.field4659.method7965()) {
         if (var1 < 0 || var1 == -451172262 * var2.field4732) {
            if (null != var2.field4742) {
               var2.field4742.method583(WidgetConfigNode.field4136 * -187966319 / 100);
               if (var2.field4742.method595()) {
                  this.field4768.field4660.method618(var2.field4742);
               }

               classNT.method8044(var2, 2100880756);
            }

            if (var2.field4727 * -710379488 < 0) {
               this.field4765[1821790950 * var2.field4732][-107170957 * var2.field4729] = null;
            }

            var2.vmethod398();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("em")
   void method8150(int var1) {
      if ((this.field4748[var1] & 2) != 0) {
         for (classNT var2 = (classNT)this.field4768.field4659.method7957(); var2 != null; var2 = (classNT)this.field4768.field4659.method7965()) {
            if (-1057694491 * var2.field4732 == var1 && this.field4765[var1][var2.field4729 * -107170957] == null && 67131625 * var2.field4727 < 0) {
               var2.field4727 = 0;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ez")
   void method8152(int var1) {
      if ((this.field4748[var1] & 4) != 0) {
         for (classNT var2 = (classNT)this.field4768.field4659.method7957(); null != var2; var2 = (classNT)this.field4768.field4659.method7965()) {
            if (-1057694491 * var2.field4732 == var1) {
               var2.field4730 = 0;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ei")
   void method8154(int var1) {
      int var2 = var1 & 240;
      if (var2 == 128) {
         int var13 = var1 & 15;
         int var19 = var1 >> 8 & 127;
         int var22 = var1 >> 16 & 127;
         this.method8112(var13, var19, var22, -1605775374);
      } else if (var2 == 144) {
         int var12 = var1 & 15;
         int var18 = var1 >> 8 & 127;
         int var21 = var1 >> 16 & 127;
         if (var21 > 0) {
            this.method8103(var12, var18, var21, 1026262110);
         } else {
            this.method8112(var12, var18, 64, -1183423236);
         }
      } else if (160 == var2) {
         int var11 = var1 & 15;
         int var17 = var1 >> 8 & 127;
         int var20 = var1 >> 16 & 127;
         method8117(this, var11, var17, var20, -1955668349);
      } else if (176 == var2) {
         int var10 = var1 & 15;
         int var16 = var1 >> 8 & 127;
         int var5 = var1 >> 16 & 127;
         if (var16 == 0) {
            this.field4757[var10] = (var5 << 14) + (this.field4757[var10] & -2080769);
         }

         if (var16 == 32) {
            this.field4757[var10] = (this.field4757[var10] & -16257) + (var5 << 7);
         }

         if (var16 == 1) {
            this.field4760[var10] = (this.field4760[var10] & -16257) + (var5 << 7);
         }

         if (var16 == 33) {
            this.field4760[var10] = (this.field4760[var10] & -128) + var5;
         }

         if (var16 == 5) {
            this.field4766[var10] = (this.field4766[var10] & -16257) + (var5 << 7);
         }

         if (var16 == 37) {
            this.field4766[var10] = var5 + (this.field4766[var10] & -128);
         }

         if (7 == var16) {
            this.field4753[var10] = (var5 << 7) + (this.field4753[var10] & -16257);
         }

         if (39 == var16) {
            this.field4753[var10] = (this.field4753[var10] & -128) + var5;
         }

         if (var16 == 10) {
            this.field4771[var10] = (var5 << 7) + (this.field4771[var10] & -16257);
         }

         if (var16 == 42) {
            this.field4771[var10] = var5 + (this.field4771[var10] & -128);
         }

         if (11 == var16) {
            this.field4769[var10] = (this.field4769[var10] & -16257) + (var5 << 7);
         }

         if (var16 == 43) {
            this.field4769[var10] = (this.field4769[var10] & -128) + var5;
         }

         if (64 == var16) {
            if (var5 >= 64) {
               this.field4748[var10] = this.field4748[var10] | 1;
            } else {
               this.field4748[var10] = this.field4748[var10] & -2;
            }
         }

         if (65 == var16) {
            if (var5 >= 64) {
               this.field4748[var10] = this.field4748[var10] | 2;
            } else {
               this.method8148(var10, -1165871656);
               this.field4748[var10] = this.field4748[var10] & -3;
            }
         }

         if (99 == var16) {
            this.field4762[var10] = (this.field4762[var10] & 127) + (var5 << 7);
         }

         if (var16 == 98) {
            this.field4762[var10] = var5 + (this.field4762[var10] & 16256);
         }

         if (101 == var16) {
            this.field4762[var10] = 16384 + (this.field4762[var10] & 127) + (var5 << 7);
         }

         if (var16 == 100) {
            this.field4762[var10] = 16384 + (this.field4762[var10] & 16256) + var5;
         }

         if (120 == var16) {
            this.method8130(var10, 2068263234);
         }

         if (var16 == 121) {
            this.method8136(var10, (byte)9);
         }

         if (var16 == 123) {
            this.method8140(var10, (byte)0);
         }

         if (6 == var16) {
            int var6 = this.field4762[var10];
            if (var6 == 16384) {
               this.field4763[var10] = (var5 << 7) + (this.field4763[var10] & -16257);
            }
         }

         if (38 == var16) {
            int var23 = this.field4762[var10];
            if (var23 == 16384) {
               this.field4763[var10] = (this.field4763[var10] & -128) + var5;
            }
         }

         if (var16 == 16) {
            this.field4750[var10] = (var5 << 7) + (this.field4750[var10] & -16257);
         }

         if (var16 == 48) {
            this.field4750[var10] = var5 + (this.field4750[var10] & -128);
         }

         if (var16 == 81) {
            if (var5 >= 64) {
               this.field4748[var10] = this.field4748[var10] | 4;
            } else {
               this.method8151(var10, -1140320208);
               this.field4748[var10] = this.field4748[var10] & -5;
            }
         }

         if (var16 == 17) {
            method8158(this, var10, (this.field4764[var10] & -16257) + (var5 << 7), (byte)54);
         }

         if (var16 == 49) {
            method8158(this, var10, (this.field4764[var10] & -128) + var5, (byte)104);
         }
      } else if (var2 == 192) {
         int var9 = var1 & 15;
         int var15 = var1 >> 8 & 127;
         this.method8102(var9, this.field4757[var9] + var15, 1795320947);
      } else if (var2 == 208) {
         int var8 = var1 & 15;
         int var14 = var1 >> 8 & 127;
         this.method8122(var8, var14, -1044212998);
      } else if (224 == var2) {
         int var3 = var1 & 15;
         int var4 = (var1 >> 8 & 127) + (var1 >> 9 & 16256);
         this.method8124(var3, var4, (byte)-23);
      } else {
         var2 = var1 & 0xFF;
         if (var2 == 255) {
            method8144(this, -962327122);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("eb")
   void method8155(int var1) {
      int var2 = var1 & -1002544930;
      if (var2 == 128) {
         int var13 = var1 & 15;
         int var19 = var1 >> 8 & -915804367;
         int var22 = var1 >> 16 & 127;
         this.method8112(var13, var19, var22, -1379119463);
      } else if (var2 == 144) {
         int var12 = var1 & 15;
         int var18 = var1 >> 8 & 127;
         int var21 = var1 >> 16 & 1499816585;
         if (var21 > 0) {
            this.method8103(var12, var18, var21, -1143996487);
         } else {
            this.method8112(var12, var18, 64, -1837809472);
         }
      } else if (884887178 == var2) {
         int var11 = var1 & 15;
         int var17 = var1 >> 8 & -939298867;
         int var20 = var1 >> 16 & 127;
         method8117(this, var11, var17, var20, -2067350196);
      } else if (176 == var2) {
         int var10 = var1 & 15;
         int var16 = var1 >> 8 & 127;
         int var5 = var1 >> 16 & 127;
         if (var16 == 0) {
            this.field4757[var10] = (var5 << 14) + (this.field4757[var10] & 1599909350);
         }

         if (var16 == 32) {
            this.field4757[var10] = (this.field4757[var10] & -16257) + (var5 << 7);
         }

         if (var16 == 1) {
            this.field4760[var10] = (this.field4760[var10] & -1202588431) + (var5 << 7);
         }

         if (var16 == -1628789590) {
            this.field4760[var10] = (this.field4760[var10] & -1761887459) + var5;
         }

         if (var16 == 5) {
            this.field4766[var10] = (this.field4766[var10] & -16257) + (var5 << 7);
         }

         if (var16 == 1885235473) {
            this.field4766[var10] = var5 + (this.field4766[var10] & -904670624);
         }

         if (7 == var16) {
            this.field4753[var10] = (var5 << 7) + (this.field4753[var10] & 78976967);
         }

         if (39 == var16) {
            this.field4753[var10] = (this.field4753[var10] & 1688331512) + var5;
         }

         if (var16 == 10) {
            this.field4771[var10] = (var5 << 7) + (this.field4771[var10] & -1251896340);
         }

         if (var16 == 42) {
            this.field4771[var10] = var5 + (this.field4771[var10] & -128);
         }

         if (11 == var16) {
            this.field4769[var10] = (this.field4769[var10] & 938117171) + (var5 << 7);
         }

         if (var16 == -46382888) {
            this.field4769[var10] = (this.field4769[var10] & 883912059) + var5;
         }

         if (120180095 == var16) {
            if (var5 >= 64) {
               this.field4748[var10] = this.field4748[var10] | 1;
            } else {
               this.field4748[var10] = this.field4748[var10] & -2;
            }
         }

         if (65 == var16) {
            if (var5 >= 64) {
               this.field4748[var10] = this.field4748[var10] | 2;
            } else {
               this.method8148(var10, -1413736404);
               this.field4748[var10] = this.field4748[var10] & -3;
            }
         }

         if (99 == var16) {
            this.field4762[var10] = (this.field4762[var10] & 1853143538) + (var5 << 7);
         }

         if (var16 == 98) {
            this.field4762[var10] = var5 + (this.field4762[var10] & 1669657282);
         }

         if (101 == var16) {
            this.field4762[var10] = 494424661 + (this.field4762[var10] & 1358702076) + (var5 << 7);
         }

         if (var16 == -886590375) {
            this.field4762[var10] = 1383242171 + (this.field4762[var10] & 16256) + var5;
         }

         if (1262213440 == var16) {
            this.method8130(var10, 2133498202);
         }

         if (var16 == 121) {
            this.method8136(var10, (byte)6);
         }

         if (var16 == -2075473059) {
            this.method8140(var10, (byte)0);
         }

         if (6 == var16) {
            int var6 = this.field4762[var10];
            if (var6 == -1448066131) {
               this.field4763[var10] = (var5 << 7) + (this.field4763[var10] & 32684547);
            }
         }

         if (959819395 == var16) {
            int var23 = this.field4762[var10];
            if (var23 == 16384) {
               this.field4763[var10] = (this.field4763[var10] & -128) + var5;
            }
         }

         if (var16 == 16) {
            this.field4750[var10] = (var5 << 7) + (this.field4750[var10] & 1944706000);
         }

         if (var16 == 48) {
            this.field4750[var10] = var5 + (this.field4750[var10] & -128);
         }

         if (var16 == 81) {
            if (var5 >= 1525120026) {
               this.field4748[var10] = this.field4748[var10] | 4;
            } else {
               this.method8151(var10, -1140320208);
               this.field4748[var10] = this.field4748[var10] & 899584701;
            }
         }

         if (var16 == 17) {
            method8158(this, var10, (this.field4764[var10] & -16257) + (var5 << 7), (byte)54);
         }

         if (var16 == 49) {
            method8158(this, var10, (this.field4764[var10] & -128) + var5, (byte)44);
         }
      } else if (var2 == -1859493282) {
         int var9 = var1 & 15;
         int var15 = var1 >> 8 & -1377920511;
         this.method8102(var9, this.field4757[var9] + var15, 1631225658);
      } else if (var2 == 208) {
         int var8 = var1 & 15;
         int var14 = var1 >> 8 & -961517115;
         this.method8122(var8, var14, -1044212998);
      } else if (224 == var2) {
         int var3 = var1 & 15;
         int var4 = (var1 >> 8 & 310631209) + (var1 >> 9 & 735618052);
         this.method8124(var3, var4, (byte)61);
      } else {
         var2 = var1 & 0xFF;
         if (var2 == 627240793) {
            method8144(this, -855641013);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnu;Lno;Lva;Lat;I)Z")
   @ObfuscatedName("hc")
   public static boolean method8058(classNU var0, classNO var1, IndexDataBase var2, classAT var3, int var4) {
      if (var0 == null) {
         return var0.method8059(var1, var2, var3, var4);
      } else {
         try {
            synchronized (var0.field4758) {
               boolean var6 = true;
               synchronized (var0.field4755) {
                  var0.field4755.clear();
               }

               var1.method7980();

               for (classNJ var19 = (classNJ)var1.field4692.method13423(); var19 != null; var19 = (classNJ)var1.field4692.method13427()) {
                  int var8 = (int)var19.field6550;
                  classNR var9 = (classNR)HashTable.method13432(var0.field4770, var8);
                  if (null == var9) {
                     var9 = classJI.method6411(var2, var8, -885874591);
                     if (var9 == null) {
                        if (var4 <= 1571663513) {
                           throw new IllegalStateException();
                        }

                        var6 = false;
                        continue;
                     }

                     var0.field4770.method13418(var9, var8);
                  }

                  if (!var9.method8030(var3, var19.field4672, 1643218864)) {
                     if (var4 <= 1571663513) {
                        throw new IllegalStateException();
                     }

                     var6 = false;
                  } else if (var0.field4755 != null) {
                     synchronized (var0.field4755) {
                        Iterator var11 = var9.field4713.iterator();

                        while (var11.hasNext()) {
                           if (var4 <= 1571663513) {
                              throw new IllegalStateException();
                           }

                           classAU var12 = (classAU)var11.next();
                           var0.field4755.add(new classNS(var19.field4673 * 1220483629, var12));
                        }
                     }
                  }
               }

               if (var6) {
                  if (var4 <= 1571663513) {
                     throw new IllegalStateException();
                  }

                  var1.method7983();
               }

               return var6;
            }
         } catch (RuntimeException var18) {
            throw classEG.method3899(var18, "nu.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;)I")
   @ObfuscatedName("fu")
   int method8172(classNT var1) {
      int var2 = this.field4771[var1.field4732 * -1057694491];
      return var2 < 8192 ? 32 + var2 * var1.field4723 * -1066414643 >> 6 : 16384 - ((128 - -1066414643 * var1.field4723) * (16384 - var2) + 32 >> 6);
   }

   @ObfuscatedSignature(descriptor = "(Lnt;)I")
   @ObfuscatedName("fz")
   int method8163(classNT var1) {
      int var2 = (-403336933 * var1.field4733 * -359606667 * var1.field4725 >> 12) + var1.field4721 * -1276571151;
      var2 += (this.field4759[var1.field4732 * -1057694491] - 8192) * this.field4763[-1057694491 * var1.field4732] >> 12;
      classNY var3 = var1.field4745;
      if (-771094009 * var3.field4790 > 0 && (var3.field4794 * -1472637671 > 0 || this.field4760[var1.field4732 * -1057694491] > 0)) {
         int var4 = -1472637671 * var3.field4794 << 2;
         int var5 = var3.field4788 * -1122448781 << 1;
         if (var1.field4728 * -1955754893 < var5) {
            var4 = -1955754893 * var1.field4728 * var4 / var5;
         }

         var4 += this.field4760[-1057694491 * var1.field4732] >> 7;
         double var6 = Math.sin((1237307259 * var1.field4737 & 511) * 0.01227184630308513);
         var2 += (int)(var4 * var6);
      }

      int var10 = (int)(var1.field4741.field187 * 256 * Math.pow(2.0, 3.255208333333333E-4 * var2) / (675076789 * WidgetConfigNode.field4136) + 0.5);
      return var10 < 1 ? 1 : var10;
   }

   @ObfuscatedSignature(descriptor = "(Lnt;)I")
   @ObfuscatedName("fb")
   int method8164(classNT var1) {
      int var2 = (-403336933 * var1.field4733 * -359606667 * var1.field4725 >> 12) + var1.field4721 * -1276571151;
      var2 += (this.field4759[var1.field4732 * -1057694491] - 8192) * this.field4763[-1057694491 * var1.field4732] >> 12;
      classNY var3 = var1.field4745;
      if (-771094009 * var3.field4790 > 0 && (var3.field4794 * -1472637671 > 0 || this.field4760[var1.field4732 * -1057694491] > 0)) {
         int var4 = -1472637671 * var3.field4794 << 2;
         int var5 = var3.field4788 * -1122448781 << 1;
         if (var1.field4728 * -1955754893 < var5) {
            var4 = -1955754893 * var1.field4728 * var4 / var5;
         }

         var4 += this.field4760[-1057694491 * var1.field4732] >> 7;
         double var6 = Math.sin((1237307259 * var1.field4737 & 511) * 0.01227184630308513);
         var2 += (int)(var4 * var6);
      }

      int var10 = (int)(var1.field4741.field187 * 256 * Math.pow(2.0, 3.255208333333333E-4 * var2) / (675076789 * WidgetConfigNode.field4136) + 0.5);
      return var10 < 1 ? 1 : var10;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cp")
   public int method8054() {
      return 1238197527 * this.field4752;
   }

   @ObfuscatedSignature(descriptor = "(Lnt;)I")
   @ObfuscatedName("fh")
   int method8169(classNT var1) {
      classNY var2 = var1.field4745;
      int var3 = -1554358408 + this.field4753[-1569917068 * var1.field4732] * this.field4769[619349541 * var1.field4732] >> 13;
      var3 = var3 * var3 + 16384 >> 15;
      var3 = var1.field4735 * 753829437 * var3 + -1848643850 >> 15;
      var3 = 1238197527 * this.field4752 * var3 + 128 >> 8;
      if (-939533713 * var2.field4791 > 0) {
         var3 = (int)(var3 * Math.pow(0.5, -939533713 * var2.field4791 * (1.953125E-5 * (934628419 * var1.field4731))) + 0.5);
      }

      if (var2.field4796 != null) {
         int var4 = -840369632 * var1.field4736;
         int var5 = var2.field4796[1 + var1.field4734 * -1948393691];
         if (var1.field4734 * 730729186 < var2.field4796.length - 2) {
            int var6 = (var2.field4796[1565767631 * var1.field4734] & -1060350370) << 8;
            int var7 = (var2.field4796[var1.field4734 * 819359530 + 2] & -1925709992) << 8;
            var5 += (var2.field4796[380631447 * var1.field4734 + 3] - var5) * (var4 - var6) / (var7 - var6);
         }

         var3 = var5 * var3 + -870944654 >> 6;
      }

      if (860965171 * var1.field4727 > 0 && var2.field4797 != null) {
         int var11 = var1.field4727 * 257053581;
         int var12 = var2.field4797[1 + var1.field4726 * -463641621];
         if (-463641621 * var1.field4726 < var2.field4797.length - 2) {
            int var13 = (var2.field4797[var1.field4726 * -463641621] & 591189722) << 8;
            int var14 = (var2.field4797[-463641621 * var1.field4726 + 2] & 1787406606) << 8;
            var12 += (var11 - var13) * (var2.field4797[3 + var1.field4726 * -463641621] - var12) / (var14 - var13);
         }

         var3 = var3 * var12 + -1304315389 >> 6;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(Lno;Z)V")
   @ObfuscatedName("ch")
   public void method8074(classNO var1, boolean var2) {
      synchronized (this.field4758) {
         this.method8079((byte)97);
         this.field4767.method7810(var1.field4691);
         this.field4779 = var2;
         this.field4777 = 0L;
         int var4 = this.field4767.method7822();

         for (int var5 = 0; var5 < var4; var5++) {
            this.field4767.method7824(var5);
            classTO.method10989(this.field4767, var5);
            this.field4767.method7825(var5);
         }

         this.field4775 = classNC.method7851(this.field4767) * -1778762749;
         this.field4776 = this.field4767.field4654[-1076146517 * this.field4775] * 596597749;
         this.field4778 = this.field4767.method7846(852899933 * this.field4776) * -7106189560981577079L;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ay")
   public void method8079(byte var1) {
      try {
         synchronized (this.field4758) {
            this.field4767.method7813();
            method8144(this, -734603996);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "nu.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;Z)V")
   @ObfuscatedName("de")
   void method8110(classNT var1, boolean var2) {
      int var3 = var1.field4741.field189.length;
      int var4;
      if (var2 && var1.field4741.field190) {
         int var5 = var3 + var3 - var1.field4741.field192;
         var4 = (int)((long)var5 * this.field4750[var1.field4732 * -1703295848] >> 6);
         var3 <<= 8;
         if (var4 >= var3) {
            var4 = var3 + var3 - 1 - var4;
            var1.field4742.method569(true);
         }
      } else {
         var4 = (int)((long)var3 * this.field4750[var1.field4732 * 277206074] >> 6);
      }

      var1.field4742.method564(var4);
   }

   @ObfuscatedSignature(descriptor = "(Lnt;)I")
   @ObfuscatedName("fl")
   int method8173(classNT var1) {
      int var2 = this.field4771[var1.field4732 * -1057694491];
      return var2 < 1635886375
         ? 32 + var2 * var1.field4723 * 535835540 >> 6
         : -145382279 - ((75884796 - 1157103378 * var1.field4723) * (16384 - var2) + 32 >> 6);
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("av")
   @Override
   protected classAH vmethod43() {
      return this.field4768;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dg")
   void method8116(int var1, int var2, int var3) {
      classNT var4 = this.field4765[var1][var2];
      if (var4 != null) {
         this.field4765[var1][var2] = null;
         if (0 != (this.field4748[var1] & 2)) {
            for (classNT var5 = (classNT)this.field4768.field4659.method7957(); var5 != null; var5 = (classNT)this.field4768.field4659.method7965()) {
               if (1180492173 * var5.field4732 == 951430840 * var4.field4732 && var5.field4727 * 67131625 < 0 && var4 != var5) {
                  var4.field4727 = 0;
                  break;
               }
            }
         } else {
            var4.field4727 = 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fs")
   void method8177() {
      int var1 = -1076146517 * this.field4775;
      int var2 = 852899933 * this.field4776;

      long var3;
      for (var3 = 5174971452106306489L * this.field4778; this.field4776 * 852899933 == var2; var3 = this.field4767.method7846(var2)) {
         while (true) {
            if (var2 == this.field4767.field4654[var1]) {
               this.field4767.method7824(var1);
               int var5 = this.field4767.method7835(var1);
               if (1 != var5) {
                  if (0 != (var5 & 128)) {
                     this.method8153(var5, -724523093);
                  }

                  classTO.method10989(this.field4767, var1);
                  this.field4767.method7825(var1);
                  continue;
               }

               this.field4767.method7832();
               this.field4767.method7825(var1);
               if (this.field4767.method7857()) {
                  if (!this.field4779 || var2 == 0) {
                     method8144(this, -1863753370);
                     this.field4767.method7813();
                     return;
                  }

                  classNC.method7859(this.field4767, var3);
               }
            }

            var1 = classNC.method7851(this.field4767);
            var2 = this.field4767.field4654[var1];
            break;
         }
      }

      this.field4775 = -1778762749 * var1;
      this.field4776 = var2 * 596597749;
      this.field4778 = var3 * -7106189560981577079L;
   }

   @ObfuscatedSignature(descriptor = "(Lnt;)I")
   @ObfuscatedName("fo")
   int method8174(classNT var1) {
      int var2 = this.field4771[var1.field4732 * -1057694491];
      return var2 < 8192 ? 32 + var2 * var1.field4723 * -1066414643 >> 6 : -456015476 - ((128 - -353135489 * var1.field4723) * (1258023958 - var2) + 32 >> 6);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;)Z")
   @ObfuscatedName("iq")
   public static boolean method8178(classNU var0, classNT var1) {
      if (var0 == null) {
         var0.method8053();
      }

      if (null == var1.field4742) {
         if (67131625 * var1.field4727 >= 0) {
            var1.vmethod398();
            if (-675471651 * var1.field4720 > 0 && var0.field4772[-1057694491 * var1.field4732][var1.field4720 * 276298627] == var1) {
               var0.field4772[-1057694491 * var1.field4732][-1530244505 * var1.field4720] = null;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;)Z")
   @ObfuscatedName("fx")
   boolean method8179(classNT var1) {
      if (null == var1.field4742) {
         if (67131625 * var1.field4727 >= 0) {
            var1.vmethod398();
            if (1851399286 * var1.field4720 > 0 && this.field4772[-1057694491 * var1.field4732][var1.field4720 * -1530244505] == var1) {
               this.field4772[-1057694491 * var1.field4732][-1530244505 * var1.field4720] = null;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;[IIIB)Z")
   @ObfuscatedName("ek")
   public static boolean method8182(classNU var0, classNT var1, int[] var2, int var3, int var4, byte var5) {
      if (var0 == null) {
         var0.method8184(var1, var2, var3, var3, var5);
      }

      try {
         label225: {
            var1.field4722 = WidgetConfigNode.field4136 * 675076789 / 100 * 1686279333;
            if (67131625 * var1.field4727 >= 0) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               if (var1.field4742 == null) {
                  break label225;
               }

               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               if (var1.field4742.method592()) {
                  break label225;
               }
            }

            int var6 = var1.field4725 * -359606667;
            if (var6 > 0) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               var6 -= (int)(16.0 * Math.pow(2.0, var0.field4766[-1057694491 * var1.field4732] * 4.921259842519685E-4) + 0.5);
               if (var6 < 0) {
                  var6 = 0;
               }

               var1.field4725 = var6 * 631359965;
            }

            var1.field4742.method589(method8162(var0, var1, -1500906549));
            classNY var7 = var1.field4745;
            boolean var8 = false;
            var1.field4728 += -1247151941;
            var1.field4737 = var1.field4737 + -1276836891 * var7.field4790;
            double var9 = ((var1.field4729 * -107170957 - 60 << 8) + (-403336933 * var1.field4733 * var1.field4725 * -359606667 >> 12)) * 5.086263020833333E-6;
            if (-939533713 * var7.field4791 > 0) {
               if (-819751845 * var7.field4793 > 0) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.field4731 = var1.field4731 + (int)(128.0 * Math.pow(2.0, var9 * (-819751845 * var7.field4793)) + 0.5) * 14704747;
               } else {
                  var1.field4731 += 1882207616;
               }
            }

            if (null != var7.field4796) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               if (var7.field4789 * 1071615623 > 0) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.field4736 = var1.field4736 + (int)(128.0 * Math.pow(2.0, var9 * (1071615623 * var7.field4789)) + 0.5) * -1452067681;
               } else {
                  var1.field4736 += -1181069440;
               }

               while (
                  var1.field4734 * 1565767631 < var7.field4796.length - 2
                     && var1.field4736 * 1539106655 > (var7.field4796[var1.field4734 * 1565767631 + 2] & 255) << 8
               ) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.field4734 += -1970020770;
               }

               if (var1.field4734 * 1565767631 == var7.field4796.length - 2) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  if (var7.field4796[1 + 1565767631 * var1.field4734] == 0) {
                     var8 = true;
                  }
               }
            }

            if (67131625 * var1.field4727 >= 0) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               if (var7.field4797 != null) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  label193:
                  if (0 == (var0.field4748[var1.field4732 * -1057694491] & 1)) {
                     if (var5 != -1) {
                        throw new IllegalStateException();
                     }

                     if (-1530244505 * var1.field4720 >= 0) {
                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }

                        if (var1 == var0.field4772[var1.field4732 * -1057694491][-1530244505 * var1.field4720]) {
                           break label193;
                        }

                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }
                     }

                     if (-1022877567 * var7.field4792 > 0) {
                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }

                        var1.field4727 = var1.field4727 + (int)(128.0 * Math.pow(2.0, var7.field4792 * -1022877567 * var9) + 0.5) * 371851097;
                     } else {
                        var1.field4727 += 352300160;
                     }

                     while (
                        -463641621 * var1.field4726 < var7.field4797.length - 2
                           && 67131625 * var1.field4727 > (var7.field4797[2 + var1.field4726 * -463641621] & 255) << 8
                     ) {
                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }

                        var1.field4726 += -1293723258;
                     }

                     if (var7.field4797.length - 2 == -463641621 * var1.field4726) {
                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }

                        var8 = true;
                     }
                  }
               }
            }

            if (var8) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               var1.field4742.method583(1837314861 * var1.field4722);
               if (var2 != null) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.field4742.vmethod30(var2, var3, var4);
               } else {
                  var1.field4742.vmethod31(var4);
               }

               if (var1.field4742.method595()) {
                  var0.field4768.field4660.method618(var1.field4742);
               }

               classNT.method8044(var1, 1774779114);
               if (67131625 * var1.field4727 >= 0) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.vmethod398();
                  if (-1530244505 * var1.field4720 > 0 && var0.field4772[-1057694491 * var1.field4732][-1530244505 * var1.field4720] == var1) {
                     if (var5 != -1) {
                        throw new IllegalStateException();
                     }

                     var0.field4772[var1.field4732 * -1057694491][var1.field4720 * -1530244505] = null;
                  }
               }

               return true;
            }

            var1.field4742.method578(1837314861 * var1.field4722, var0.method8168(var1, 121229128), var0.method8171(var1, 1932888135));
            return false;
         }

         classNT.method8044(var1, 1814200938);
         var1.vmethod398();
         if (var1.field4720 * -1530244505 > 0) {
            if (var5 != -1) {
               throw new IllegalStateException();
            }

            if (var0.field4772[var1.field4732 * -1057694491][var1.field4720 * -1530244505] == var1) {
               var0.field4772[-1057694491 * var1.field4732][-1530244505 * var1.field4720] = null;
            }
         }

         return true;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "nu.bz(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;[III)Z")
   @ObfuscatedName("fr")
   boolean method8183(classNT var1, int[] var2, int var3, int var4) {
      var1.field4722 = WidgetConfigNode.field4136 * 675076789 / 100 * 1686279333;
      if (67131625 * var1.field4727 < 0 || var1.field4742 != null && !var1.field4742.method592()) {
         int var5 = var1.field4725 * -359606667;
         if (var5 > 0) {
            var5 -= (int)(16.0 * Math.pow(2.0, this.field4766[-1057694491 * var1.field4732] * 4.921259842519685E-4) + 0.5);
            if (var5 < 0) {
               var5 = 0;
            }

            var1.field4725 = var5 * 631359965;
         }

         var1.field4742.method589(method8162(this, var1, 434175831));
         classNY var6 = var1.field4745;
         boolean var7 = false;
         var1.field4728 += -1247151941;
         var1.field4737 = var1.field4737 + -1276836891 * var6.field4790;
         double var8 = ((var1.field4729 * -107170957 - 60 << 8) + (-403336933 * var1.field4733 * var1.field4725 * -359606667 >> 12)) * 5.086263020833333E-6;
         if (-939533713 * var6.field4791 > 0) {
            if (-819751845 * var6.field4793 > 0) {
               var1.field4731 = var1.field4731 + (int)(128.0 * Math.pow(2.0, var8 * (-819751845 * var6.field4793)) + 0.5) * 14704747;
            } else {
               var1.field4731 += 1882207616;
            }
         }

         if (null != var6.field4796) {
            if (var6.field4789 * 1071615623 > 0) {
               var1.field4736 = var1.field4736 + (int)(128.0 * Math.pow(2.0, var8 * (1071615623 * var6.field4789)) + 0.5) * -1452067681;
            } else {
               var1.field4736 += -1181069440;
            }

            while (
               var1.field4734 * 1565767631 < var6.field4796.length - 2
                  && var1.field4736 * 1539106655 > (var6.field4796[var1.field4734 * 1565767631 + 2] & 255) << 8
            ) {
               var1.field4734 += -1970020770;
            }

            if (var1.field4734 * 1565767631 == var6.field4796.length - 2 && var6.field4796[1 + 1565767631 * var1.field4734] == 0) {
               var7 = true;
            }
         }

         if (67131625 * var1.field4727 >= 0
            && var6.field4797 != null
            && 0 == (this.field4748[var1.field4732 * -1057694491] & 1)
            && (-1530244505 * var1.field4720 < 0 || var1 != this.field4772[var1.field4732 * -1057694491][-1530244505 * var1.field4720])) {
            if (-1022877567 * var6.field4792 > 0) {
               var1.field4727 = var1.field4727 + (int)(128.0 * Math.pow(2.0, var6.field4792 * -1022877567 * var8) + 0.5) * 371851097;
            } else {
               var1.field4727 += 352300160;
            }

            while (
               -463641621 * var1.field4726 < var6.field4797.length - 2
                  && 67131625 * var1.field4727 > (var6.field4797[2 + var1.field4726 * -463641621] & 255) << 8
            ) {
               var1.field4726 += -1293723258;
            }

            if (var6.field4797.length - 2 == -463641621 * var1.field4726) {
               var7 = true;
            }
         }

         if (var7) {
            var1.field4742.method583(1837314861 * var1.field4722);
            if (var2 != null) {
               var1.field4742.vmethod30(var2, var3, var4);
            } else {
               var1.field4742.vmethod31(var4);
            }

            if (var1.field4742.method595()) {
               this.field4768.field4660.method618(var1.field4742);
            }

            classNT.method8044(var1, 2038001096);
            if (67131625 * var1.field4727 >= 0) {
               var1.vmethod398();
               if (-1530244505 * var1.field4720 > 0 && this.field4772[-1057694491 * var1.field4732][-1530244505 * var1.field4720] == var1) {
                  this.field4772[var1.field4732 * -1057694491][var1.field4720 * -1530244505] = null;
               }
            }

            return true;
         } else {
            var1.field4742.method578(1837314861 * var1.field4722, this.method8168(var1, -1561437423), this.method8171(var1, 1703656030));
            return false;
         }
      } else {
         classNT.method8044(var1, 2014029027);
         var1.vmethod398();
         if (var1.field4720 * -1530244505 > 0 && this.field4772[var1.field4732 * -1057694491][var1.field4720 * -1530244505] == var1) {
            this.field4772[-1057694491 * var1.field4732][-1530244505 * var1.field4720] = null;
         }

         return true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;)I")
   @ObfuscatedName("la")
   public static int method8165(classNU var0, classNT var1) {
      if (var0 == null) {
         var0.vmethod34();
      }

      int var2 = (-403336933 * var1.field4733 * -359606667 * var1.field4725 >> 12) + var1.field4721 * -1276571151;
      var2 += (var0.field4759[var1.field4732 * -1057694491] - 8192) * var0.field4763[-1057694491 * var1.field4732] >> 12;
      classNY var3 = var1.field4745;
      if (-771094009 * var3.field4790 > 0 && (var3.field4794 * -1472637671 > 0 || var0.field4760[var1.field4732 * -1057694491] > 0)) {
         int var4 = -1472637671 * var3.field4794 << 2;
         int var5 = var3.field4788 * -1122448781 << 1;
         if (var1.field4728 * -1955754893 < var5) {
            var4 = -1955754893 * var1.field4728 * var4 / var5;
         }

         var4 += var0.field4760[-1057694491 * var1.field4732] >> 7;
         double var6 = Math.sin((1237307259 * var1.field4737 & 511) * 0.01227184630308513);
         var2 += (int)(var4 * var6);
      }

      int var10 = (int)(var1.field4741.field187 * 256 * Math.pow(2.0, 3.255208333333333E-4 * var2) / (675076789 * WidgetConfigNode.field4136) + 0.5);
      return var10 < 1 ? 1 : var10;
   }

   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;B)Z")
   @ObfuscatedName("df")
   public static boolean method8180(classNU var0, classNT var1, byte var2) {
      if (var0 == null) {
         return var0.method8181(var1, var2);
      } else {
         try {
            if (null == var1.field4742) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               } else {
                  if (67131625 * var1.field4727 >= 0) {
                     if (var2 >= 0) {
                        throw new IllegalStateException();
                     }

                     var1.vmethod398();
                     if (-1530244505 * var1.field4720 > 0) {
                        if (var2 >= 0) {
                           throw new IllegalStateException();
                        }

                        if (var0.field4772[-1057694491 * var1.field4732][var1.field4720 * -1530244505] == var1) {
                           var0.field4772[-1057694491 * var1.field4732][-1530244505 * var1.field4720] = null;
                        }
                     }
                  }

                  return true;
               }
            } else {
               return false;
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "nu.bh(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ax")
   static boolean method8185(int var0) {
      try {
         if ((client.field884 * -2024190683 & classXE.field6831.vmethod8((byte)1)) != 0) {
            if (var0 >= -1108606041) {
               throw new IllegalStateException();
            } else {
               classBF.field395 = "";
               classBF.field396 = classKK.field3814;
               classBF.field397 = classKK.field3739;
               classBF.field398 = classKK.field3834;
               classAC.method321(1, 2000903999);
               return true;
            }
         } else if ((-2024190683 * client.field884 & classXE.field6828.vmethod8((byte)1)) != 0) {
            if (var0 >= -1108606041) {
               throw new IllegalStateException();
            } else {
               if ((-2024190683 * client.field884 & classXE.field6836.vmethod8((byte)1)) != 0) {
                  if (var0 >= -1108606041) {
                     throw new IllegalStateException();
                  }

                  classBF.field396 = classKK.field3829;
                  classBF.field397 = classKK.field3830;
                  classBF.field398 = classKK.field3832;
               } else {
                  classBF.field396 = classKK.field3823;
                  classBF.field397 = classKK.field3824;
                  classBF.field398 = classKK.field3825;
               }

               classBF.field395 = classKK.field3822;
               classAC.method321(1, 2082594261);
               return true;
            }
         } else if ((client.field884 * -2024190683 & classXE.field6836.vmethod8((byte)1)) != 0) {
            classBF.field396 = classKK.field3640;
            classBF.field397 = classKK.field3653;
            classBF.field398 = classKK.field3828;
            classBF.field395 = classKK.field3822;
            classAC.method321(1, 2097366523);
            return true;
         } else {
            return false;
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "nu.ax(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;Z)V")
   @ObfuscatedName("mo")
   public static void method8111(classNU var0, classNT var1, boolean var2) {
      if (var0 == null) {
         var0.method8172(var1);
      }

      int var3 = var1.field4741.field189.length;
      int var4;
      if (var2 && var1.field4741.field190) {
         int var5 = var3 + var3 - var1.field4741.field192;
         var4 = (int)((long)var5 * var0.field4750[var1.field4732 * -1057694491] >> 6);
         var3 <<= 8;
         if (var4 >= var3) {
            var4 = var3 + var3 - 1 - var4;
            var1.field4742.method569(true);
         }
      } else {
         var4 = (int)((long)var3 * var0.field4750[var1.field4732 * -1057694491] >> 6);
      }

      var1.field4742.method564(var4);
   }

   @ObfuscatedSignature(descriptor = "(Lno;Lva;Lat;I)Z")
   @ObfuscatedName("ae")
   public boolean method8059(classNO var1, IndexDataBase var2, classAT var3, int var4) {
      try {
         synchronized (this.field4758) {
            boolean var6 = true;
            synchronized (this.field4755) {
               this.field4755.clear();
            }

            var1.method7982();

            for (classNJ var19 = (classNJ)var1.field4692.method13427(); var19 != null; var19 = (classNJ)var1.field4692.method13431()) {
               int var8 = (int)var19.field6550;
               classNR var9 = (classNR)HashTable.method13432(this.field4770, var8);
               if (null == var9) {
                  var9 = classJI.method6411(var2, var8, -885874591);
                  if (var9 == null) {
                     if (var4 <= 1571663513) {
                        throw new IllegalStateException();
                     }

                     var6 = false;
                     continue;
                  }

                  this.field4770.method13421(var9, var8);
               }

               if (!var9.method8030(var3, var19.field4672, 1643218864)) {
                  if (var4 <= 1571663513) {
                     throw new IllegalStateException();
                  }

                  var6 = false;
               } else if (this.field4755 != null) {
                  synchronized (this.field4755) {
                     Iterator var11 = var9.field4713.iterator();

                     while (var11.hasNext()) {
                        if (var4 <= 1571663513) {
                           throw new IllegalStateException();
                        }

                        classAU var12 = (classAU)var11.next();
                        this.field4755.add(new classNS(var19.field4673 * 1220483629, var12));
                     }
                  }
               }
            }

            if (var6) {
               if (var4 <= 1571663513) {
                  throw new IllegalStateException();
               }

               var1.method7981();
            }

            return var6;
         }
      } catch (RuntimeException var18) {
         throw classEG.method3899(var18, "nu.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("aq")
   public boolean method8083(byte var1) {
      try {
         synchronized (this.field4758) {
            return this.field4767.method7817();
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "nu.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bm")
   void method8121(int var1, int var2, int var3, int var4) {
      try {
         ;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "nu.bm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bb")
   void method8147(int var1) {
      try {
         this.method8129(-1, 2100279562);
         this.method8140(-1, (byte)19);

         for (int var2 = 0; var2 < 16; var2++) {
            this.field4766[var2] = this.field4754[var2];
         }

         for (int var4 = 0; var4 < 16; var4++) {
            if (var1 >= -676520024) {
               throw new IllegalStateException();
            }

            this.field4748[var4] = this.field4753[var4] & -128;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "nu.bb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;[IIIB)Z")
   @ObfuscatedName("bz")
   boolean method8184(classNT var1, int[] var2, int var3, int var4, byte var5) {
      try {
         label221: {
            var1.field4735 = WidgetConfigNode.field4136 * 675076789 / 100 * 1686279333;
            if (67131625 * var1.field4725 >= 0) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               if (var1.field4742 == null) {
                  break label221;
               }

               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               if (var1.field4742.method596()) {
                  break label221;
               }
            }

            int var6 = var1.field4722 * -359606667;
            if (var6 > 0) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               var6 -= (int)(16.0 * Math.pow(2.0, this.field4762[-1057694491 * var1.field4722] * 4.921259842519685E-4) + 0.5);
               if (var6 < 0) {
                  var6 = 0;
               }

               var1.field4736 = var6 * 631359965;
            }

            var1.field4742.method584(method8162(this, var1, -1500906549));
            classNY var7 = var1.field4745;
            boolean var8 = false;
            var1.field4728 = var1.field4731 + -1247151941;
            var1.field4730 = var1.field4725 + -1276836891 * var7.field4793;
            double var9 = ((var1.field4727 * -107170957 - 60 << 8) + (-403336933 * var1.field4728 * var1.field4736 * -359606667 >> 12)) * 5.086263020833333E-6;
            if (-939533713 * var7.field4794 > 0) {
               if (-819751845 * var7.field4793 > 0) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.field4731 = var1.field4720 + (int)(128.0 * Math.pow(2.0, var9 * (-819751845 * var7.field4790)) + 0.5) * 14704747;
               } else {
                  var1.field4725 += 1882207616;
               }
            }

            if (null != var7.field4797) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               if (var7.field4789 * 1071615623 > 0) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.field4727 = var1.field4734 + (int)(128.0 * Math.pow(2.0, var9 * (1071615623 * var7.field4794)) + 0.5) * -1452067681;
               } else {
                  var1.field4730 = var1.field4721 + -1181069440;
               }

               while (
                  var1.field4732 * 1565767631 < var7.field4797.length - 2
                     && var1.field4730 * 1539106655 > (var7.field4796[var1.field4722 * 1565767631 + 2] & 255) << 8
               ) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.field4729 = var1.field4736 + -1970020770;
               }

               if (var1.field4730 * 1565767631 == var7.field4796.length - 2) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  if (var7.field4796[1 + 1565767631 * var1.field4732] == 0) {
                     var8 = true;
                  }
               }
            }

            if (67131625 * var1.field4734 >= 0) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               if (var7.field4796 != null) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  label190:
                  if (0 == (this.field4762[var1.field4729 * -1057694491] & 1)) {
                     if (var5 != -1) {
                        throw new IllegalStateException();
                     }

                     if (-1530244505 * var1.field4731 >= 0) {
                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }

                        if (var1 == this.field4765[var1.field4723 * -1057694491][-1530244505 * var1.field4731]) {
                           break label190;
                        }

                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }
                     }

                     if (-1022877567 * var7.field4791 > 0) {
                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }

                        var1.field4726 = var1.field4720 + (int)(128.0 * Math.pow(2.0, var7.field4794 * -1022877567 * var9) + 0.5) * 371851097;
                     } else {
                        var1.field4725 = var1.field4737 + 352300160;
                     }

                     while (
                        -463641621 * var1.field4736 < var7.field4796.length - 2
                           && 67131625 * var1.field4723 > (var7.field4797[2 + var1.field4728 * -463641621] & 255) << 8
                     ) {
                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }

                        var1.field4735 = var1.field4730 + -1293723258;
                     }

                     if (var7.field4797.length - 2 == -463641621 * var1.field4730) {
                        if (var5 != -1) {
                           throw new IllegalStateException();
                        }

                        var8 = true;
                     }
                  }
               }
            }

            if (var8) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               var1.field4742.method550(1837314861 * var1.field4726);
               if (var2 != null) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.field4742.vmethod38(var2, var3, var4);
               } else {
                  var1.field4742.vmethod42(var4);
               }

               if (var1.field4742.method596()) {
                  this.field4768.field4660.method620(var1.field4742);
               }

               classNT.method8044(var1, 1774779114);
               if (67131625 * var1.field4720 >= 0) {
                  if (var5 != -1) {
                     throw new IllegalStateException();
                  }

                  var1.vmethod399();
                  if (-1530244505 * var1.field4729 > 0 && this.field4765[-1057694491 * var1.field4730][-1530244505 * var1.field4723] == var1) {
                     if (var5 != -1) {
                        throw new IllegalStateException();
                     }

                     this.field4772[var1.field4732 * -1057694491][var1.field4725 * -1530244505] = null;
                  }
               }

               return true;
            }

            var1.field4742.method580(1837314861 * var1.field4721, this.method8171(var1, 121229128), this.method8171(var1, 1932888135));
            return false;
         }

         classNT.method8044(var1, 1814200938);
         var1.method12133();
         if (var1.field4720 * -1530244505 > 0) {
            if (var5 != -1) {
               throw new IllegalStateException();
            }

            if (this.field4772[var1.field4730 * -1057694491][var1.field4732 * -1530244505] == var1) {
               this.field4772[-1057694491 * var1.field4726][-1530244505 * var1.field4721] = null;
            }
         }

         return true;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "nu.bz(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("af")
   public int method8055(byte var1) {
      try {
         return 1238197527 * this.field4761;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nu.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cc")
   public void method8080() {
      synchronized (this.field4758) {
         this.field4767.method7830();
         method8144(this, -1953164524);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dd")
   public boolean method8084() {
      synchronized (this.field4758) {
         return this.field4767.method7857();
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dr")
   void method8134(int var1, int var2) {
      try {
         for (classNT var3 = (classNT)this.field4768.field4659.method7959(); var3 != null; var3 = (classNT)this.field4768.field4659.method7959()) {
            if (var2 <= 2044929277) {
               throw new IllegalStateException();
            }

            if (var1 >= 0) {
               if (var2 <= 2044929277) {
                  throw new IllegalStateException();
               }

               if (var1 != -1057694491 * var3.field4722) {
                  continue;
               }

               if (var2 <= 2044929277) {
                  throw new IllegalStateException();
               }
            }

            if (null != var3.field4742) {
               if (var2 <= 2044929277) {
                  return;
               }

               var3.field4742.method584(WidgetConfigNode.field4136 * 675076789 / 100);
               if (var3.field4742.method593()) {
                  if (var2 <= 2044929277) {
                     throw new IllegalStateException();
                  }

                  this.field4768.field4660.method618(var3.field4742);
               }

               classNT.method8044(var3, 1946466263);
            }

            if (var3.field4723 * 67131625 < 0) {
               if (var2 <= 2044929277) {
                  return;
               }

               this.field4772[-1057694491 * var3.field4736][-107170957 * var3.field4734] = null;
            }

            var3.vmethod398();
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.bj(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;I)V")
   @ObfuscatedName("nj")
   public static void method8156(classNU var0, int var1) {
      if (var0 == null) {
         var0.method8135(var1);
      } else {
         int var2 = var1 & -373423707;
         if (var2 == -1952773022) {
            int var13 = var1 & 15;
            int var19 = var1 >> 8 & 127;
            int var22 = var1 >> 16 & -239626205;
            var0.method8112(var13, var19, var22, -1945193169);
         } else if (var2 == 144) {
            int var12 = var1 & 15;
            int var18 = var1 >> 8 & -1492256028;
            int var21 = var1 >> 16 & 127;
            if (var21 > 0) {
               var0.method8103(var12, var18, var21, 367820714);
            } else {
               var0.method8112(var12, var18, 64, -1290020752);
            }
         } else if (2030296144 == var2) {
            int var11 = var1 & 15;
            int var17 = var1 >> 8 & -2115925350;
            int var20 = var1 >> 16 & 1357118883;
            method8117(var0, var11, var17, var20, -2078091137);
         } else if (916383054 == var2) {
            int var10 = var1 & 15;
            int var16 = var1 >> 8 & 127;
            int var5 = var1 >> 16 & 1814771152;
            if (var16 == 0) {
               var0.field4757[var10] = (var5 << 14) + (var0.field4757[var10] & -2080769);
            }

            if (var16 == -846041544) {
               var0.field4757[var10] = (var0.field4757[var10] & -16257) + (var5 << 7);
            }

            if (var16 == 1) {
               var0.field4760[var10] = (var0.field4760[var10] & -1129142244) + (var5 << 7);
            }

            if (var16 == 33) {
               var0.field4760[var10] = (var0.field4760[var10] & -1198904599) + var5;
            }

            if (var16 == 5) {
               var0.field4766[var10] = (var0.field4766[var10] & -16257) + (var5 << 7);
            }

            if (var16 == 37) {
               var0.field4766[var10] = var5 + (var0.field4766[var10] & -128);
            }

            if (7 == var16) {
               var0.field4753[var10] = (var5 << 7) + (var0.field4753[var10] & -1264009661);
            }

            if (39 == var16) {
               var0.field4753[var10] = (var0.field4753[var10] & 673578595) + var5;
            }

            if (var16 == 10) {
               var0.field4771[var10] = (var5 << 7) + (var0.field4771[var10] & 1062196448);
            }

            if (var16 == 42) {
               var0.field4771[var10] = var5 + (var0.field4771[var10] & 359328614);
            }

            if (11 == var16) {
               var0.field4769[var10] = (var0.field4769[var10] & -1363026354) + (var5 << 7);
            }

            if (var16 == 1102432625) {
               var0.field4769[var10] = (var0.field4769[var10] & -1349262136) + var5;
            }

            if (64 == var16) {
               if (var5 >= 1331934289) {
                  var0.field4748[var10] = var0.field4748[var10] | 1;
               } else {
                  var0.field4748[var10] = var0.field4748[var10] & -2;
               }
            }

            if (-1420631876 == var16) {
               if (var5 >= 49894709) {
                  var0.field4748[var10] = var0.field4748[var10] | 2;
               } else {
                  var0.method8148(var10, -2105039660);
                  var0.field4748[var10] = var0.field4748[var10] & 912188463;
               }
            }

            if (-342433575 == var16) {
               var0.field4762[var10] = (var0.field4762[var10] & 127) + (var5 << 7);
            }

            if (var16 == -1193686232) {
               var0.field4762[var10] = var5 + (var0.field4762[var10] & 16256);
            }

            if (1756417828 == var16) {
               var0.field4762[var10] = -1346433097 + (var0.field4762[var10] & 127) + (var5 << 7);
            }

            if (var16 == -1815740340) {
               var0.field4762[var10] = 16384 + (var0.field4762[var10] & 1885280934) + var5;
            }

            if (304611318 == var16) {
               var0.method8130(var10, 2093478091);
            }

            if (var16 == 121) {
               var0.method8136(var10, (byte)-23);
            }

            if (var16 == 123) {
               var0.method8140(var10, (byte)0);
            }

            if (6 == var16) {
               int var6 = var0.field4762[var10];
               if (var6 == 2020197647) {
                  var0.field4763[var10] = (var5 << 7) + (var0.field4763[var10] & -16257);
               }
            }

            if (-1323688826 == var16) {
               int var23 = var0.field4762[var10];
               if (var23 == 16384) {
                  var0.field4763[var10] = (var0.field4763[var10] & -1487614348) + var5;
               }
            }

            if (var16 == 16) {
               var0.field4750[var10] = (var5 << 7) + (var0.field4750[var10] & -16257);
            }

            if (var16 == 267955526) {
               var0.field4750[var10] = var5 + (var0.field4750[var10] & 1899896760);
            }

            if (var16 == -1431088180) {
               if (var5 >= -1355883746) {
                  var0.field4748[var10] = var0.field4748[var10] | 4;
               } else {
                  var0.method8151(var10, -1140320208);
                  var0.field4748[var10] = var0.field4748[var10] & -1098375473;
               }
            }

            if (var16 == 17) {
               method8158(var0, var10, (var0.field4764[var10] & -16257) + (var5 << 7), (byte)62);
            }

            if (var16 == 49) {
               method8158(var0, var10, (var0.field4764[var10] & -128) + var5, (byte)69);
            }
         } else if (var2 == -2075727965) {
            int var9 = var1 & 15;
            int var15 = var1 >> 8 & -561640679;
            var0.method8102(var9, var0.field4757[var9] + var15, 2107245484);
         } else if (var2 == 208) {
            int var8 = var1 & 15;
            int var14 = var1 >> 8 & 2089232794;
            var0.method8122(var8, var14, -1044212998);
         } else if (224 == var2) {
            int var3 = var1 & 15;
            int var4 = (var1 >> 8 & -1522497842) + (var1 >> 9 & 1233852998);
            var0.method8124(var3, var4, (byte)-31);
         } else {
            var2 = var1 & -476661782;
            if (var2 == 255) {
               method8144(var0, -1505245603);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("bf")
   void method8160(int var1, int var2, byte var3) {
      try {
         this.field4762[var1] = var2;
         this.field4753[var1] = (int)(2097152.0 * Math.pow(2.0, 5.493164E-4F * var2) + 0.5);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nu.bf(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnu;B)Z")
   @ObfuscatedName("ap")
   public static boolean method8085(classNU var0, byte var1) {
      if (var0 == null) {
         var0.method8083(var1);
      }

      try {
         synchronized (var0.field4758) {
            return var0.field4767.method7817();
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "nu.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("df")
   void method8106(int var1, int var2, int var3) {
      this.method8121(var1, var2, -487945971, -1078748678);
      if ((this.field4760[var1] & 2) != 0) {
         for (classNT var4 = (classNT)Deque2.method7962(this.field4768.field4659); var4 != null; var4 = (classNT)Deque2.method7968(this.field4768.field4659)) {
            if (-1057694491 * var4.field4720 == var1 && var4.field4733 * 67131625 < 0) {
               this.field4772[var1][-370277124 * var4.field4729] = null;
               this.field4772[var1][var2] = var4;
               int var5 = (var4.field4722 * -359606667 * -403336933 * var4.field4725 >> 12) + var4.field4720 * 464921419;
               var4.field4729 = var4.field4730 + -1901450660 * (var2 - 1701614193 * var4.field4731 << 8);
               var4.field4731 = -300850925 * (var5 - var4.field4727 * 1732100978);
               var4.field4733 = 480104448;
               var4.field4733 = -685332037 * var2;
               return;
            }
         }
      }

      classNR var8 = (classNR)HashTable.method13432(this.field4770, this.field4751[var1]);
      if (null != var8) {
         if (null != var8.field4704[var2]) {
            classAJ var9 = var8.field4704[var2].method700(2019781816);
            if (var9 != null) {
               classNT var6 = new classNT();
               var6.field4727 = -1566998291 * var1;
               var6.field4744 = var8;
               var6.field4741 = var9;
               var6.field4745 = var8.field4711[var2];
               var6.field4733 = var8.field4709[var2] * 15946373;
               var6.field4735 = var2 * -685332037;
               var6.field4736 = 780512417 * (1039261828 + var8.field4708 * -127646999 * var3 * var3 * var8.field4709[var2] >> 11);
               var6.field4730 = -225319163 * (var8.field4709[var2] & 2132532882);
               var6.field4721 = ((var2 << 8) - (var8.field4705[var2] & 1608534758)) * 1567246768;
               var6.field4729 = 0;
               var6.field4735 = 0;
               var6.field4737 = 0;
               var6.field4737 = 1108414625;
               var6.field4733 = 0;
               if (0 == this.field4769[var1]) {
                  var6.field4742 = classAM.method472(
                     var9, method8162(this, var6, -816538955), this.method8171(var6, 1966662171), this.method8168(var6, 1667012717)
                  );
               } else {
                  var6.field4742 = classAM.method474(var9, method8162(this, var6, -420856506), 0, this.method8168(var6, 1870930798));
                  this.method8107(var6, var8.field4705[var2] < 0, 881975798);
               }

               if (var8.field4705[var2] < 0) {
                  var6.field4742.method583(-1);
               }

               if (-1530244505 * var6.field4725 >= 0) {
                  classNT var7 = this.field4765[var1][-662556096 * var6.field4726];
                  if (var7 != null && 67131625 * var7.field4727 < 0) {
                     this.field4772[var1][var7.field4726 * -549347805] = null;
                     var7.field4721 = 0;
                  }

                  this.field4772[var1][var6.field4734 * -1530244505] = var6;
               }

               this.field4768.field4659.method7946(var6);
               this.field4765[var1][var2] = var6;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;B)Z")
   @ObfuscatedName("bh")
   boolean method8181(classNT var1, byte var2) {
      try {
         if (null == var1.field4742) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            } else {
               if (67131625 * var1.field4735 >= 0) {
                  if (var2 >= 0) {
                     throw new IllegalStateException();
                  }

                  var1.vmethod398();
                  if (-1530244505 * var1.field4722 > 0) {
                     if (var2 >= 0) {
                        throw new IllegalStateException();
                     }

                     if (this.field4765[-1057694491 * var1.field4726][var1.field4727 * -1530244505] == var1) {
                        this.field4765[-1057694491 * var1.field4730][-1530244505 * var1.field4731] = null;
                     }
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "nu.bh(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;I)I")
   @ObfuscatedName("bs")
   int method8166(classNT var1, int var2) {
      try {
         int var10;
         var10 = (-403336933 * var1.field4736 * -359606667 * var1.field4737 >> 12) + var1.field4721 * -1276571151;
         var10 += (this.field4764[var1.field4737 * -1057694491] - 8192) * this.field4760[-1057694491 * var1.field4730] >> 12;
         classNY var4 = var1.field4745;
         label53:
         if (-771094009 * var4.field4793 > 0) {
            if (var2 >= 1204216895) {
               throw new IllegalStateException();
            }

            if (var4.field4792 * -1472637671 <= 0) {
               if (var2 >= 1204216895) {
                  throw new IllegalStateException();
               }

               if (this.field4751[var1.field4729 * -1057694491] <= 0) {
                  break label53;
               }

               if (var2 >= 1204216895) {
                  throw new IllegalStateException();
               }
            }

            int var5 = -1472637671 * var4.field4792 << 2;
            int var6 = var4.field4791 * -1122448781 << 1;
            if (var1.field4726 * -1955754893 < var6) {
               if (var2 >= 1204216895) {
                  throw new IllegalStateException();
               }

               var5 = -1955754893 * var1.field4722 * var5 / var6;
            }

            var5 += this.field4751[-1057694491 * var1.field4731] >> 7;
            double var7 = Math.sin((1237307259 * var1.field4726 & 511) * 0.01227184630308513);
            var10 += (int)(var5 * var7);
         }

         int var12 = (int)(var1.field4741.field192 * 256 * Math.pow(2.0, 3.255208333333333E-4 * var10) / (675076789 * WidgetConfigNode.field4136) + 0.5);
         int var10000;
         if (var12 < 1) {
            if (var2 >= 1204216895) {
               throw new IllegalStateException();
            }

            var10000 = 1;
         } else {
            var10000 = var12;
         }

         return var10000;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "nu.bs(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ee")
   void method8135(int var1) {
      for (classNT var2 = (classNT)this.field4768.field4659.method7959(); var2 != null; var2 = (classNT)this.field4768.field4659.method7975()) {
         if (var1 < 0 || var1 == -1311139638 * var2.field4733) {
            if (null != var2.field4742) {
               var2.field4742.method542(WidgetConfigNode.field4136 * -1572993699 / -2096733984);
               if (var2.field4742.method593()) {
                  this.field4768.field4660.method620(var2.field4742);
               }

               classNT.method8044(var2, 2005278781);
            }

            if (var2.field4734 * -1690390608 < 0) {
               this.field4765[-1057694491 * var2.field4723][-107170957 * var2.field4725] = null;
            }

            var2.vmethod387();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bv")
   void method8143(int var1) {
      for (classNT var2 = (classNT)this.field4768.field4659.method7965(); var2 != null; var2 = (classNT)this.field4768.field4659.method7966()) {
         if ((var1 < 0 || var1 == var2.field4720 * -1057694491) && var2.field4737 * 67131625 < 0) {
            this.field4772[var2.field4728 * -1057694491][var2.field4725 * -107170957] = null;
            var2.field4721 = 0;
         }
      }
   }
}
