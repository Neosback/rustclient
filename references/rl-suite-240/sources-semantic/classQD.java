import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qd")
public class classQD {
   @ObfuscatedName("az")
   final String field5517;
   @ObfuscatedName("af")
   final ThreadFactory field5514;
   @ObfuscatedName("ae")
   final ThreadPoolExecutor field5515;
   @ObfuscatedName("ab")
   final int field5516;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field5518 = 7;

   @ObfuscatedSignature(descriptor = "(Lqa;)Lqm;")
   @ObfuscatedName("ac")
   public classQM method9534(classQA var1) {
      if (this.field5515.getQueue().remainingCapacity() <= 0) {
         System.err
            .println(
               "REST thread pool queue is empty\r\nThread pool size " + this.field5515.getCorePoolSize() + " Queue capacity " + 1603104713 * this.field5516
            );
         return new classQM("Queue full");
      } else {
         return new classQM(this.field5515.submit(new classQK(this, var1)));
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqd;Lqa;)Lqm;")
   @ObfuscatedName("my")
   public static classQM method9535(classQD var0, classQA var1) {
      if (var0.field5515.getQueue().remainingCapacity() <= 0) {
         System.err
            .println(
               "REST thread pool queue is empty\r\nThread pool size " + var0.field5515.getCorePoolSize() + " Queue capacity " + 1603104713 * var0.field5516
            );
         return new classQM("Queue full");
      } else {
         return new classQM(var0.field5515.submit(new classQK(var0, var1)));
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/util/concurrent/ThreadPoolExecutor;")
   @ObfuscatedName("ab")
   final ThreadPoolExecutor method9531(int var1) {
      return new ThreadPoolExecutor(var1, var1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(this.field5516 * 1603104713), this.field5514);
   }

   @ObfuscatedSignature(descriptor = "(Lqd;B)V")
   @ObfuscatedName("ey")
   public static void method9538(classQD var0, byte var1) {
      if (var0 == null) {
         var0.method9543(var1);
      }

      try {
         try {
            var0.field5515.shutdown();
         } catch (Exception var3) {
            System.err.println("Error shutting down RestRequestService\r\n" + var3);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qd.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqd;)V")
   @ObfuscatedName("dw")
   public static void method9539(classQD var0) {
      try {
         var0.field5515.shutdown();
      } catch (Exception var2) {
         System.err.println("Error shutting down RestRequestService\r\n" + var2);
      }
   }

   public classQD(String var1, int var2, int var3) {
      this.field5517 = var1;
      this.field5516 = var2 * 1506663033;
      this.field5514 = new classQE(this);
      this.field5515 = this.method9547(var3, (byte)-51);
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/util/concurrent/ThreadPoolExecutor;")
   @ObfuscatedName("ag")
   final ThreadPoolExecutor method9532(int var1) {
      return new ThreadPoolExecutor(var1, var1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(this.field5516 * -300290001), this.field5514);
   }

   @ObfuscatedSignature(descriptor = "(IB)Ljava/util/concurrent/ThreadPoolExecutor;")
   @ObfuscatedName("az")
   final ThreadPoolExecutor method9547(int var1, byte var2) {
      return new ThreadPoolExecutor(0, var1, 2L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(this.field5516 * 1603104713), this.field5514);
   }

   @ObfuscatedSignature(descriptor = "(Lqa;)Lqm;")
   @ObfuscatedName("ax")
   public classQM method9536(classQA var1) {
      if (this.field5515.getQueue().remainingCapacity() <= 0) {
         System.err
            .println(
               "REST thread pool queue is empty\r\nThread pool size " + this.field5515.getCorePoolSize() + " Queue capacity " + 1603104713 * this.field5516
            );
         return new classQM("Queue full");
      } else {
         return new classQM(this.field5515.submit(new classQK(this, var1)));
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/util/concurrent/ThreadPoolExecutor;")
   @ObfuscatedName("as")
   final ThreadPoolExecutor method9533(int var1) {
      return new ThreadPoolExecutor(var1, var1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(this.field5516 * 1603104713), this.field5514);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqd;)V")
   @ObfuscatedName("em")
   public static void method9540(classQD var0) {
      try {
         var0.field5515.shutdown();
      } catch (Exception var2) {
         System.err.println("Error shutting down RestRequestService\r\n" + var2);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvw;Llw;)V")
   @ObfuscatedName("eg")
   public static void method9545(classVW var0, Widget var1) {
      if (var0 == null) {
         var0.method12296();
      }

      var0.field6623 = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   public final void method9541() {
      try {
         this.field5515.shutdown();
      } catch (Exception var2) {
         System.err.println("Error shutting down RestRequestService\r\n" + var2);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnn;)Lvq;")
   @ObfuscatedName("gq")
   public static Node method9544(Deque2 var0) {
      if (var0 == null) {
         return var0.method7979();
      } else {
         Node var1 = var0.field4689.field6551;
         if (var1 == var0.field4689) {
            return null;
         } else {
            var1.vmethod398();
            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqa;I)Lqm;")
   @ObfuscatedName("af")
   public classQM method9537(classQA var1, int var2) {
      try {
         if (this.field5515.getQueue().remainingCapacity() <= 0) {
            if (var2 == 675603180) {
               throw new IllegalStateException();
            } else {
               System.err
                  .println(
                     "REST thread pool queue is empty\r\nThread pool size "
                        + this.field5515.getCorePoolSize()
                        + " Queue capacity "
                        + 1603104713 * this.field5516
                  );
               return new classQM("Queue full");
            }
         } else {
            return new classQM(this.field5515.submit(new classQK(this, var1)));
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qd.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public final void method9542() {
      try {
         this.field5515.shutdown();
      } catch (Exception var2) {
         System.err.println("Error shutting down RestRequestService\r\n" + var2);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Lse;")
   @ObfuscatedName("ag")
   static classSE[] method9546(int var0) {
      try {
         return new classSE[]{classSE.field5909, classSE.field5911, classSE.field5913, classSE.field5912, classSE.field5910};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qd.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ae")
   public final void method9543(byte var1) {
      try {
         try {
            this.field5515.shutdown();
         } catch (Exception var3) {
            System.err.println("Error shutting down RestRequestService\r\n" + var3);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qd.ae(" + ')');
      }
   }
}
