import java.io.DataInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mb")
public class classMB implements Runnable {
   @ObfuscatedName("iv")
   static int field4476;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4472 = 4;
   @ObfuscatedName("aa")
   boolean field4470;
   @ObfuscatedSignature(descriptor = "Lmj;")
   @ObfuscatedName("as")
   classMJ field4468 = null;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4474 = 1;
   @ObfuscatedName("ac")
   Thread field4471;
   @ObfuscatedSignature(descriptor = "Lmj;")
   @ObfuscatedName("ax")
   classMJ field4469 = null;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field4473 = 12;
   @ToRemove(unused = "true")
   @ObfuscatedName("cu")
   static final int field4475 = 148;
   @ObfuscatedName("ag")
   public static String field4477;

   @ObfuscatedSignature(descriptor = "(S)V")
   @ObfuscatedName("ab")
   public final void method7543(short var1) {
      try {
         synchronized (this) {
            this.field4470 = true;
            this.notifyAll();
         }

         try {
            this.field4471.join();
         } catch (InterruptedException var4) {
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "mb.ab(" + ')');
      }
   }

   @Override
   public final void run() {
      try {
         while (true) {
            classMJ var1;
            synchronized (this) {
               while (true) {
                  if (this.field4470) {
                     return;
                  }

                  if (this.field4468 != null) {
                     var1 = this.field4468;
                     this.field4468 = this.field4468.field4517;
                     if (null == this.field4468) {
                        this.field4469 = null;
                     }
                     break;
                  }

                  try {
                     this.wait();
                  } catch (InterruptedException var7) {
                  }
               }
            }

            try {
               int var2 = var1.field4513 * 1949692181;
               if (var2 == 1) {
                  var1.field4516 = new Socket(InetAddress.getByName((String)var1.field4515), var1.field4511);
               } else if (2 == var2) {
                  Thread var3 = new Thread((Runnable)var1.field4515);
                  var3.setDaemon(true);
                  var3.start();
                  var3.setPriority(var1.field4511);
                  var1.field4516 = var3;
               } else if (4 == var2) {
                  var1.field4516 = new DataInputStream(((URL)var1.field4515).openStream());
               }

               var1.field4510 = 1;
            } catch (ThreadDeath var5) {
               throw var5;
            } catch (Throwable var6) {
               var1.field4510 = 2;
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "mb.run(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILjava/lang/Object;I)Lmj;")
   @ObfuscatedName("ag")
   final classMJ method7551(int var1, int var2, int var3, Object var4, int var5) {
      try {
         classMJ var6 = new classMJ();
         var6.field4513 = var1 * 1420219965;
         var6.field4511 = var2;
         var6.field4515 = var4;
         synchronized (this) {
            if (null != this.field4469) {
               if (var5 >= 1375698635) {
                  throw new IllegalStateException();
               }

               this.field4469.field4517 = var6;
               this.field4469 = var6;
            } else {
               this.field4469 = this.field4468 = var6;
            }

            this.notify();
            return var6;
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "mb.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;II)Lmj;")
   @ObfuscatedName("as")
   public final classMJ method7554(String var1, int var2, int var3) {
      try {
         return this.method7551(1, var2, 0, var1, 143965232);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mb.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILjava/lang/Object;)Lmj;")
   @ObfuscatedName("al")
   final classMJ method7552(int var1, int var2, int var3, Object var4) {
      classMJ var5 = new classMJ();
      var5.field4513 = var1 * 1420219965;
      var5.field4511 = var2;
      var5.field4515 = var4;
      synchronized (this) {
         if (null != this.field4469) {
            this.field4469.field4517 = var5;
            this.field4469 = var5;
         } else {
            this.field4469 = this.field4468 = var5;
         }

         this.notify();
         return var5;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;I)I")
   @ObfuscatedName("as")
   public static int method7563(classTE var0, int var1) {
      try {
         classCQ.method2564(var0, -668471179);
         byte var2 = 0;
         int var3 = var0.method10797(-2063914818);
         int var4 = -1;
         if (classYY.field7109 == var0.field6126) {
            if (var1 == -1456496566) {
               throw new IllegalStateException();
            }

            int[] var5 = var0.method10791(1293299844);

            for (int var6 = var2; var6 < var3; var6++) {
               if (var1 == -1456496566) {
                  throw new IllegalStateException();
               }

               if (var4 != -1) {
                  if (var1 == -1456496566) {
                     throw new IllegalStateException();
                  }

                  if (var5[var6] <= var5[var4]) {
                     continue;
                  }

                  if (var1 == -1456496566) {
                     throw new IllegalStateException();
                  }
               }

               var4 = var6;
            }
         } else if (classYY.field7113 == var0.field6126) {
            if (var1 == -1456496566) {
               throw new IllegalStateException();
            }

            long[] var10 = var0.method10793(-262575641);

            for (int var12 = var2; var12 < var3; var12++) {
               if (var1 == -1456496566) {
                  throw new IllegalStateException();
               }

               if (-1 != var4) {
                  if (var1 == -1456496566) {
                     throw new IllegalStateException();
                  }

                  if (var10[var12] <= var10[var4]) {
                     continue;
                  }

                  if (var1 == -1456496566) {
                     throw new IllegalStateException();
                  }
               }

               var4 = var12;
            }
         } else if (classYY.field7111 == var0.field6126) {
            String var11 = null;
            Object[] var13 = var0.method10796(648693478);

            for (int var7 = var2; var7 < var3; var7++) {
               if (var1 == -1456496566) {
                  throw new IllegalStateException();
               }

               String var8 = (String)var13[var7];
               if (var4 != -1) {
                  if (var1 == -1456496566) {
                     throw new IllegalStateException();
                  }

                  if (var8 == null) {
                     continue;
                  }

                  if (var1 == -1456496566) {
                     throw new IllegalStateException();
                  }

                  if (var8.compareTo(var11) <= 0) {
                     continue;
                  }
               }

               var4 = var7;
               var11 = var8;
            }
         }

         return var4;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "mb.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("af")
   public static int method7542(int var0, byte var1) {
      try {
         return var0 + -512;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mb.af(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmb;)V")
   @ObfuscatedName("ge")
   public static void method7544(classMB var0) {
      if (var0 == null) {
         var0.method7546();
      } else {
         synchronized (var0) {
            var0.field4470 = true;
            var0.notifyAll();
         }

         try {
            var0.field4471.join();
         } catch (InterruptedException var3) {
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmb;)V")
   @ObfuscatedName("zb")
   public static void method7547(classMB var0) {
      if (var0 == null) {
         var0.run();
      }

      while (true) {
         classMJ var1;
         synchronized (var0) {
            while (true) {
               if (var0.field4470) {
                  return;
               }

               if (var0.field4468 != null) {
                  var1 = var0.field4468;
                  var0.field4468 = var0.field4468.field4517;
                  if (null == var0.field4468) {
                     var0.field4469 = null;
                  }
                  break;
               }

               try {
                  var0.wait();
               } catch (InterruptedException var5) {
               }
            }
         }

         try {
            int var2 = var1.field4513 * 1949692181;
            if (var2 == 1) {
               var1.field4516 = new Socket(InetAddress.getByName((String)var1.field4515), var1.field4511);
            } else if (2 == var2) {
               Thread var3 = new Thread((Runnable)var1.field4515);
               var3.setDaemon(true);
               var3.start();
               var3.setPriority(var1.field4511);
               var1.field4516 = var3;
            } else if (4 == var2) {
               var1.field4516 = new DataInputStream(((URL)var1.field4515).openStream());
            }

            var1.field4510 = 1;
         } catch (ThreadDeath var6) {
            throw var6;
         } catch (Throwable var7) {
            var1.field4510 = 2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   public final void method7545() {
      synchronized (this) {
         this.field4470 = true;
         this.notifyAll();
      }

      try {
         this.field4471.join();
      } catch (InterruptedException var3) {
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILjava/lang/Object;)Lmj;")
   @ObfuscatedName("ao")
   final classMJ method7553(int var1, int var2, int var3, Object var4) {
      classMJ var5 = new classMJ();
      var5.field4513 = var1 * -1399864910;
      var5.field4511 = var2;
      var5.field4515 = var4;
      synchronized (this) {
         if (null != this.field4469) {
            this.field4469.field4517 = var5;
            this.field4469 = var5;
         } else {
            this.field4469 = this.field4468 = var5;
         }

         this.notify();
         return var5;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   public final void method7548() {
      while (true) {
         classMJ var1;
         synchronized (this) {
            while (true) {
               if (this.field4470) {
                  return;
               }

               if (this.field4468 != null) {
                  var1 = this.field4468;
                  this.field4468 = this.field4468.field4517;
                  if (null == this.field4468) {
                     this.field4469 = null;
                  }
                  break;
               }

               try {
                  this.wait();
               } catch (InterruptedException var7) {
               }
            }
         }

         try {
            int var2 = var1.field4513 * 794181862;
            if (var2 == 1) {
               var1.field4516 = new Socket(InetAddress.getByName((String)var1.field4515), var1.field4511);
            } else if (2 == var2) {
               Thread var3 = new Thread((Runnable)var1.field4515);
               var3.setDaemon(true);
               var3.start();
               var3.setPriority(var1.field4511);
               var1.field4516 = var3;
            } else if (4 == var2) {
               var1.field4516 = new DataInputStream(((URL)var1.field4515).openStream());
            }

            var1.field4510 = 1;
         } catch (ThreadDeath var5) {
            throw var5;
         } catch (Throwable var6) {
            var1.field4510 = 2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Lmj;")
   @ObfuscatedName("aj")
   public final classMJ method7555(String var1, int var2) {
      return this.method7551(1, var2, 0, var1, -391069800);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Lmj;")
   @ObfuscatedName("ay")
   public final classMJ method7556(String var1, int var2) {
      return this.method7551(1, var2, 0, var1, -585293095);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmb;)V")
   @ObfuscatedName("si")
   public static void method7549(classMB var0) {
      if (var0 == null) {
         var0.method7545();
      } else {
         while (true) {
            classMJ var1;
            synchronized (var0) {
               while (true) {
                  if (var0.field4470) {
                     return;
                  }

                  if (var0.field4468 != null) {
                     var1 = var0.field4468;
                     var0.field4468 = var0.field4468.field4517;
                     if (null == var0.field4468) {
                        var0.field4469 = null;
                     }
                     break;
                  }

                  try {
                     var0.wait();
                  } catch (InterruptedException var5) {
                  }
               }
            }

            try {
               int var2 = var1.field4513 * 1949692181;
               if (var2 == 1) {
                  var1.field4516 = new Socket(InetAddress.getByName((String)var1.field4515), var1.field4511);
               } else if (2 == var2) {
                  Thread var3 = new Thread((Runnable)var1.field4515);
                  var3.setDaemon(true);
                  var3.start();
                  var3.setPriority(var1.field4511);
                  var1.field4516 = var3;
               } else if (4 == var2) {
                  var1.field4516 = new DataInputStream(((URL)var1.field4515).openStream());
               }

               var1.field4510 = 1;
            } catch (ThreadDeath var6) {
               throw var6;
            } catch (Throwable var7) {
               var1.field4510 = 2;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmb;Ljava/lang/Runnable;I)Lmj;")
   @ObfuscatedName("jy")
   public static classMJ method7557(classMB var0, Runnable var1, int var2) {
      if (var0 == null) {
         var0.method7546();
      }

      return var0.method7551(2, var2, 0, var1, 1177595316);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Runnable;I)Lmj;")
   @ObfuscatedName("aq")
   public final classMJ method7558(Runnable var1, int var2) {
      return this.method7551(2, var2, 0, var1, 1264907340);
   }

   @ObfuscatedSignature(descriptor = "(Lmb;Ljava/lang/Runnable;II)Lmj;")
   @ObfuscatedName("zc")
   public static classMJ method7559(classMB var0, Runnable var1, int var2, int var3) {
      if (var0 == null) {
         return var0.method7562(var1, var2, var2);
      } else {
         try {
            return var0.method7551(2, var2, 0, var1, -1883812875);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "mb.ax(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Runnable;I)Lmj;")
   @ObfuscatedName("ad")
   public final classMJ method7560(Runnable var1, int var2) {
      return this.method7551(2, var2, 0, var1, -1133155428);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Runnable;I)Lmj;")
   @ObfuscatedName("au")
   public final classMJ method7561(Runnable var1, int var2) {
      return this.method7551(2, var2, 0, var1, -182770655);
   }

   @ObfuscatedSignature(descriptor = "(Lva;Lva;II)Lsn;")
   @ObfuscatedName("az")
   public static classSN method7550(IndexDataBase var0, IndexDataBase var1, int var2, int var3) {
      try {
         byte[] var5 = var0.method11937(var2 >> 16 & 65535, var2 & 65535, 2044601435);
         boolean var4;
         if (null == var5) {
            if (var3 != -1929466463) {
               throw new IllegalStateException();
            }

            var4 = false;
         } else {
            int var6 = (var5[1] & 255) << 8 | var5[2] & 255;
            byte[] var7 = var1.method11937(var6, 0, 350903642);
            var4 = null != var7;
         }

         if (!var4) {
            if (var3 != -1929466463) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            if (classXN.field6889 == null) {
               if (var3 != -1929466463) {
                  throw new IllegalStateException();
               }

               classSN.field5991 = Runtime.getRuntime().availableProcessors() * 1058142237;
               classXN.field6889 = new ThreadPoolExecutor(
                  0, -517436875 * classSN.field5991, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100 + -204079948 * classSN.field5991), new classSF()
               );
            }

            try {
               return new classSN(var0, var1, var2);
            } catch (Exception var8) {
               return null;
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "mb.az(" + ')');
      }
   }

   public classMB() {
      this.field4470 = false;
      classDQ.field1602 = "Unknown";
      field4477 = "1.6";

      try {
         classDQ.field1602 = System.getProperty("java.vendor");
         field4477 = System.getProperty("java.version");
      } catch (Exception var2) {
      }

      this.field4470 = false;
      this.field4471 = new Thread(this);
      this.field4471.setPriority(10);
      this.field4471.setDaemon(true);
      this.field4471.start();
   }

   @ObfuscatedSignature(descriptor = "(I)Lct;")
   @ObfuscatedName("ev")
   static Player method7564(int var0) {
      try {
         WorldView var1 = client.field815.method1584(client.field847 * -2034209657, (short)13357);
         Player var10000;
         if (var1 != null) {
            if (var0 == 120041229) {
               throw new IllegalStateException();
            }

            var10000 = var1.method3752(client.field846 * -2130951373, (byte)1);
         } else {
            var10000 = null;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mb.ev(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public final void method7546() {
      synchronized (this) {
         this.field4470 = true;
         this.notifyAll();
      }

      try {
         this.field4471.join();
      } catch (InterruptedException var3) {
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Runnable;II)Lmj;")
   @ObfuscatedName("ax")
   public final classMJ method7562(Runnable var1, int var2, int var3) {
      try {
         return this.method7551(2, var2, 0, var1, -1883812875);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mb.ax(" + ')');
      }
   }
}
