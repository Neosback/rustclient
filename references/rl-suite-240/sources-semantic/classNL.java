import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nl")
public class classNL implements Comparator {
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field4680 = 9;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field4681 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   public static final int field4679 = 2;
   @ObfuscatedName("jr")
   static int field4682;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method7930(Object var1, Object var2) {
      return this.method7927((classNS)var1, (classNS)var2, -951245792);
   }

   @ObfuscatedSignature(descriptor = "(Lns;Lns;I)I")
   @ObfuscatedName("az")
   int method7927(classNS var1, classNS var2, int var3) {
      try {
         return var1.field4715 * -1799225543 - var2.field4715 * -1799225543;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nl.az(" + 41);
      }
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method7927((classNS)var1, (classNS)var2, -1297027899);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "nl.compare(" + 41);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nl.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method7931(Object var1, Object var2) {
      return this.method7927((classNS)var1, (classNS)var2, 415080863);
   }

   @ObfuscatedSignature(descriptor = "(Lns;Lns;)I")
   @ObfuscatedName("af")
   int method7928(classNS var1, classNS var2) {
      return var1.field4715 * -1799225543 - var2.field4715 * -1799225543;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method7932(Object var1) {
      return super.equals(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnl;Ljava/lang/Object;)Z")
   @ObfuscatedName("le")
   public static boolean method7933(classNL var0, Object var1) {
      if (var0 == null) {
         var0.method7930(var1, var1);
      }

      return var0.equals(var1);
   }

   classNL() {
   }

   @ObfuscatedSignature(descriptor = "(Lns;Lns;)I")
   @ObfuscatedName("ae")
   int method7929(classNS var1, classNS var2) {
      return var1.field4715 * -1799225543 - var2.field4715 * -1799225543;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIIII)Z")
   @ObfuscatedName("ax")
   static boolean method7926(
      int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12
   ) {
      try {
         var8++;
         var9++;
         var10--;
         var11--;
         if (var0 >= var8) {
            if (var12 == -483624883) {
               throw new IllegalStateException();
            }

            if (var0 <= var10) {
               if (var12 == -483624883) {
                  throw new IllegalStateException();
               }

               if (var1 >= var9) {
                  if (var12 == -483624883) {
                     throw new IllegalStateException();
                  }

                  if (var1 <= var11) {
                     if (var12 == -483624883) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }
            }
         }

         if (var2 >= var8) {
            if (var12 == -483624883) {
               throw new IllegalStateException();
            }

            if (var2 <= var10 && var3 >= var9) {
               if (var12 == -483624883) {
                  throw new IllegalStateException();
               }

               if (var3 <= var11) {
                  if (var12 == -483624883) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         if (var4 >= var8) {
            if (var12 == -483624883) {
               throw new IllegalStateException();
            }

            if (var4 <= var10) {
               if (var12 == -483624883) {
                  throw new IllegalStateException();
               }

               if (var5 >= var9) {
                  if (var12 == -483624883) {
                     throw new IllegalStateException();
                  }

                  if (var5 <= var11) {
                     if (var12 == -483624883) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }
            }
         }

         if (var6 >= var8 && var6 <= var10) {
            if (var12 == -483624883) {
               throw new IllegalStateException();
            }

            if (var7 >= var9) {
               if (var12 == -483624883) {
                  throw new IllegalStateException();
               }

               if (var7 <= var11) {
                  return true;
               }
            }
         }

         if (!classPF.method8888(var8, var9, var0, var1, var2, var3, var4, var5, (byte)-1)) {
            if (var12 == -483624883) {
               throw new IllegalStateException();
            }

            if (!classPF.method8888(var8, var11, var0, var1, var2, var3, var4, var5, (byte)-1)) {
               if (var12 == -483624883) {
                  throw new IllegalStateException();
               }

               if (!classPF.method8888(var10, var9, var0, var1, var2, var3, var4, var5, (byte)-1)) {
                  if (var12 == -483624883) {
                     throw new IllegalStateException();
                  }

                  if (!classPF.method8888(var10, var11, var0, var1, var2, var3, var4, var5, (byte)-1)) {
                     if (!classHL.method6103(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var9, -1453501658)) {
                        if (var12 == -483624883) {
                           throw new IllegalStateException();
                        }

                        if (!classHL.method6103(var0, var1, var2, var3, var4, var5, var6, var7, var10, var9, var10, var11, -1453501658)) {
                           if (var12 == -483624883) {
                              throw new IllegalStateException();
                           }

                           if (!classHL.method6103(var0, var1, var2, var3, var4, var5, var6, var7, var10, var11, var8, var11, -1453501658)
                              && !classHL.method6103(var0, var1, var2, var3, var4, var5, var6, var7, var8, var11, var8, var9, -1453501658)) {
                              return false;
                           }
                        }
                     }

                     return true;
                  }

                  if (var12 == -483624883) {
                     throw new IllegalStateException();
                  }
               }
            }
         }

         return true;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "nl.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("af")
   public static int method7925(int var0, int var1) {
      try {
         return var0 >> 8;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nl.af(" + 41);
      }
   }
}
