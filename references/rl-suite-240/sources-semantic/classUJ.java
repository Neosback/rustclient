import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("uj")
public class classUJ implements Runnable {
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("ag")
   public static Deque2 field6314 = new Deque2();
   @ObfuscatedName("af")
   static final Object field6313 = new Object();
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6317 = 600;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("az")
   public static final Deque2 field6311 = new Deque2();
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field6316 = 100;
   @ObfuscatedName("as")
   static int field6312 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   public static final int field6315 = 3;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public void method11390() {
      try {
         while (true) {
            classVU var1;
            synchronized (field6311) {
               var1 = (classVU)field6311.method7957();
            }

            if (var1 != null) {
               if (0 == var1.field6605 * -1312025255) {
                  classUS.method11594(var1.field6606, (int)var1.field6550, var1.field6608, var1.field6608.length, 665812228);
                  synchronized (field6311) {
                     var1.vmethod398();
                  }
               } else if (var1.field6605 * -452974526 == 1) {
                  var1.field6608 = var1.field6606.method11587((int)var1.field6550, -1142684788);
                  synchronized (field6311) {
                     field6314.method7946(var1);
                  }
               }

               synchronized (field6313) {
                  if (field6312 * 508619279 <= 1) {
                     field6312 = 0;
                     field6313.notifyAll();
                     return;
                  }

                  field6312 = 2125268663;
               }
            } else {
               Friend.method10197(100L);
               synchronized (field6313) {
                  if (508619279 * field6312 <= 1) {
                     field6312 = 0;
                     field6313.notifyAll();
                     return;
                  }

                  field6312 -= 379503855;
               }
            }
         }
      } catch (Exception var13) {
         classAAU.method270(null, var13, (byte)53);
      }
   }

   @Override
   public void run() {
      try {
         try {
            while (true) {
               classVU var1;
               synchronized (field6311) {
                  var1 = (classVU)field6311.method7957();
               }

               if (var1 != null) {
                  if (0 == var1.field6605 * -1312025255) {
                     classUS.method11594(var1.field6606, (int)var1.field6550, var1.field6608, var1.field6608.length, -1758944047);
                     synchronized (field6311) {
                        var1.vmethod398();
                     }
                  } else if (var1.field6605 * -1312025255 == 1) {
                     var1.field6608 = var1.field6606.method11587((int)var1.field6550, -1142684788);
                     synchronized (field6311) {
                        field6314.method7946(var1);
                     }
                  }

                  synchronized (field6313) {
                     if (field6312 * 508619279 <= 1) {
                        field6312 = 0;
                        field6313.notifyAll();
                        return;
                     }

                     field6312 = 69046312;
                  }
               } else {
                  Friend.method10197(100L);
                  synchronized (field6313) {
                     if (508619279 * field6312 <= 1) {
                        field6312 = 0;
                        field6313.notifyAll();
                        return;
                     }

                     field6312 -= 379503855;
                  }
               }
            }
         } catch (Exception var13) {
            classAAU.method270(null, var13, (byte)52);
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "uj.run(" + ')');
      }
   }

   classUJ() {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   public void method11391() {
      try {
         while (true) {
            classVU var1;
            synchronized (field6311) {
               var1 = (classVU)field6311.method7957();
            }

            if (var1 != null) {
               if (0 == var1.field6605 * -1312025255) {
                  classUS.method11594(var1.field6606, (int)var1.field6550, var1.field6608, var1.field6608.length, 410444381);
                  synchronized (field6311) {
                     var1.vmethod398();
                  }
               } else if (var1.field6605 * -1312025255 == 1) {
                  var1.field6608 = var1.field6606.method11587((int)var1.field6550, -1142684788);
                  synchronized (field6311) {
                     field6314.method7946(var1);
                  }
               }

               synchronized (field6313) {
                  if (field6312 * 508619279 <= 1) {
                     field6312 = 0;
                     field6313.notifyAll();
                     return;
                  }

                  field6312 = 69046312;
               }
            } else {
               Friend.method10197(100L);
               synchronized (field6313) {
                  if (508619279 * field6312 <= 1) {
                     field6312 = 0;
                     field6313.notifyAll();
                     return;
                  }

                  field6312 -= 379503855;
               }
            }
         }
      } catch (Exception var13) {
         classAAU.method270(null, var13, (byte)52);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   public void method11392() {
      try {
         while (true) {
            classVU var1;
            synchronized (field6311) {
               var1 = (classVU)field6311.method7957();
            }

            if (var1 != null) {
               if (0 == var1.field6605 * -1312025255) {
                  classUS.method11594(var1.field6606, (int)var1.field6550, var1.field6608, var1.field6608.length, -538211867);
                  synchronized (field6311) {
                     var1.vmethod398();
                  }
               } else if (var1.field6605 * -1312025255 == 1) {
                  var1.field6608 = var1.field6606.method11587((int)var1.field6550, -1142684788);
                  synchronized (field6311) {
                     field6314.method7946(var1);
                  }
               }

               synchronized (field6313) {
                  if (field6312 * 508619279 <= 1) {
                     field6312 = 0;
                     field6313.notifyAll();
                     return;
                  }

                  field6312 = 69046312;
               }
            } else {
               Friend.method10197(100L);
               synchronized (field6313) {
                  if (-979027664 * field6312 <= 1) {
                     field6312 = 0;
                     field6313.notifyAll();
                     return;
                  }

                  field6312 -= 379503855;
               }
            }
         }
      } catch (Exception var13) {
         classAAU.method270(null, var13, (byte)85);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public static void method11387() {
      synchronized (field6313) {
         if (field6312 * 508619279 != 0) {
            field6312 = 379503855;

            try {
               field6313.wait();
            } catch (InterruptedException var5) {
            }
         }
      }

      synchronized (field6311) {
         Deque2.method7942(field6311);
         Deque2.method7942(field6314);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ag")
   public static void method11388() {
      synchronized (field6313) {
         if (field6312 * 508619279 != 0) {
            field6312 = 371289217;

            try {
               field6313.wait();
            } catch (InterruptedException var5) {
            }
         }
      }

      synchronized (field6311) {
         Deque2.method7942(field6311);
         Deque2.method7942(field6314);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   public static void method11389() {
      synchronized (field6313) {
         if (field6312 * 508619279 != 0) {
            field6312 = 1691652418;

            try {
               field6313.wait();
            } catch (InterruptedException var5) {
            }
         }
      }

      synchronized (field6311) {
         Deque2.method7942(field6311);
         Deque2.method7942(field6314);
      }
   }
}
