import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qe")
public class classQE implements ThreadFactory {
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final String field5522 = "cond";
   @ObfuscatedName("af")
   final AtomicInteger field5519;
   @ObfuscatedName("az")
   final ThreadGroup field5520;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   static final int field5521 = 22;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("ho")
   static classVP field5523;

   @Override
   public Thread newThread(Runnable var1) {
      try {
         Thread var2 = new Thread(this.field5520, var1, this.this$0.field5517 + "-rest-request-" + this.field5519.getAndIncrement(), 0L);
         var2.setDaemon(true);
         var2.setPriority(5);
         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qe.newThread(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxs;[BII)V")
   @ObfuscatedName("ll")
   public static void method9548(PacketBuffer var0, byte[] var1, int var2, int var3) {
      if (var0 == null) {
         var0.method12991(var2, var2);
      }

      for (int var4 = 0; var4 < var3; var4++) {
         var1[var4 + var2] = (byte)(var0.field6952[(var0.field6953 += 597900486) * 702114061 - 1] - var0.field6927.method13667((byte)1));
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;B)Z")
   @ObfuscatedName("ab")
   public static boolean method9552(CharSequence var0, byte var1) {
      try {
         boolean var3 = false;
         boolean var4 = false;
         long var5 = 0L;
         int var7 = var0.length();
         int var8 = 0;

         boolean var2;
         while (true) {
            if (var8 >= var7) {
               var2 = var4;
               break;
            }

            label118: {
               int var9 = var0.charAt(var8);
               if (0 == var8) {
                  if (var1 <= 43) {
                     throw new IllegalStateException();
                  }

                  if (var9 == 45) {
                     var3 = true;
                     break label118;
                  }

                  if (var9 == 43) {
                     if (var1 <= 43) {
                        throw new IllegalStateException();
                     }
                     break label118;
                  }
               }

               if (var9 >= 48 && var9 <= 57) {
                  if (var1 <= 43) {
                     throw new IllegalStateException();
                  }

                  var9 -= 48;
               } else {
                  label117: {
                     if (var9 >= 65) {
                        if (var1 <= 43) {
                           throw new IllegalStateException();
                        }

                        if (var9 <= 90) {
                           if (var1 <= 43) {
                              throw new IllegalStateException();
                           }

                           var9 -= 55;
                           break label117;
                        }
                     }

                     if (var9 < 97 || var9 > 122) {
                        var2 = false;
                        break;
                     }

                     if (var1 <= 43) {
                        throw new IllegalStateException();
                     }

                     var9 -= 87;
                  }
               }

               if (var9 >= 10) {
                  if (var1 <= 43) {
                     throw new IllegalStateException();
                  }

                  var2 = false;
                  break;
               }

               if (var3) {
                  var9 = -var9;
               }

               long var10 = 10L * var5 + var9;
               if (var5 != var10 / 10L) {
                  if (var1 <= 43) {
                     throw new IllegalStateException();
                  }

                  var2 = false;
                  break;
               }

               var5 = var10;
               var4 = true;
            }

            var8++;
         }

         return var2;
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "qe.ab(" + ')');
      }
   }

   classQE(classQD var1) {
      this.this$0 = var1;
      this.field5519 = new AtomicInteger(1);
      SecurityManager var2 = System.getSecurityManager();
      this.field5520 = null != var2 ? var2.getThreadGroup() : Thread.currentThread().getThreadGroup();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Runnable;)Ljava/lang/Thread;")
   @ObfuscatedName("az")
   public Thread method9550(Runnable var1) {
      Thread var2 = new Thread(this.field5520, var1, this.this$0.field5517 + "-rest-request-" + this.field5519.getAndIncrement(), 0L);
      var2.setDaemon(true);
      var2.setPriority(5);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Runnable;)Ljava/lang/Thread;")
   @ObfuscatedName("af")
   public Thread method9551(Runnable var1) {
      Thread var2 = new Thread(this.field5520, var1, this.this$0.field5517 + "-rest-request-" + this.field5519.getAndIncrement(), 0L);
      var2.setDaemon(true);
      var2.setPriority(5);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lul;B)Lul;")
   @ObfuscatedName("ab")
   static final classUL method9549(classUL var0, byte var1) {
      try {
         classUL var2 = classSQ.method10683(var0, -506347951);
         classUL.method11446(var2, (byte)100);
         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qe.ab(" + ')');
      }
   }
}
