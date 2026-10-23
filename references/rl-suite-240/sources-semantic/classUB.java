import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ub")
public class classUB implements Runnable {
   @ObfuscatedName("as")
   int field6279;
   @ObfuscatedName("af")
   InputStream field6277;
   @ObfuscatedName("az")
   Thread field6276;
   @ObfuscatedName("ab")
   byte[] field6275;
   @ObfuscatedName("ag")
   int field6278 = 0;
   @ObfuscatedName("ae")
   int field6274;
   @ObfuscatedName("ax")
   IOException field6281;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   public static final int field6280 = 2048;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   void method11318() {
      synchronized (this) {
         if (null == this.field6281) {
            this.field6281 = new IOException("");
         }

         this.notifyAll();
      }

      try {
         this.field6276.join();
      } catch (InterruptedException var3) {
      }
   }

   @Override
   public void run() {
      try {
         while (true) {
            int var1;
            synchronized (this) {
               while (true) {
                  if (null != this.field6281) {
                     return;
                  }

                  if (0 == 1764480657 * this.field6278) {
                     var1 = this.field6274 * -593120253 - 1284385685 * this.field6279 - 1;
                  } else if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
                     var1 = this.field6274 * -593120253 - 1284385685 * this.field6279;
                  } else {
                     var1 = this.field6278 * 1764480657 - this.field6279 * 1284385685 - 1;
                  }

                  if (var1 > 0) {
                     break;
                  }

                  try {
                     this.wait();
                  } catch (InterruptedException var9) {
                  }
               }
            }

            int var2;
            try {
               var2 = this.field6277.read(this.field6275, this.field6279 * 1284385685, var1);
               if (var2 == -1) {
                  throw new EOFException();
               }
            } catch (IOException var10) {
               IOException var3 = var10;
               synchronized (this) {
                  this.field6281 = var3;
                  return;
               }
            }

            synchronized (this) {
               this.field6279 = (1284385685 * this.field6279 + var2) % (this.field6274 * -593120253) * 1480968125;
            }
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "ub.run(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ab")
   boolean method11304(int var1, int var2) throws IOException {
      try {
         if (var1 == 0) {
            if (var2 >= 446505856) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            if (var1 > 0) {
               if (var2 >= 446505856) {
                  throw new IllegalStateException();
               }

               if (var1 < -593120253 * this.field6274) {
                  synchronized (this) {
                     int var4;
                     if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
                        if (var2 >= 446505856) {
                           throw new IllegalStateException();
                        }

                        var4 = 1284385685 * this.field6279 - 1764480657 * this.field6278;
                     } else {
                        var4 = -593120253 * this.field6274 - 1764480657 * this.field6278 + this.field6279 * 1284385685;
                     }

                     if (var4 < var1) {
                        if (var2 >= 446505856) {
                           throw new IllegalStateException();
                        }

                        if (this.field6281 != null) {
                           if (var2 >= 446505856) {
                              throw new IllegalStateException();
                           }

                           throw new IOException(this.field6281.toString());
                        }

                        this.notifyAll();
                        return false;
                     }

                     return true;
                  }
               }

               if (var2 >= 446505856) {
                  throw new IllegalStateException();
               }
            }

            throw new IOException();
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ub.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("ag")
   int method11307(short var1) throws IOException {
      try {
         synchronized (this) {
            int var3;
            if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
               if (var1 >= 153) {
                  throw new IllegalStateException();
               }

               var3 = 1284385685 * this.field6279 - this.field6278 * 1764480657;
            } else {
               var3 = this.field6274 * -593120253 - 1764480657 * this.field6278 + this.field6279 * 1284385685;
            }

            if (var3 <= 0) {
               if (var1 >= 153) {
                  throw new IllegalStateException();
               }

               if (null != this.field6281) {
                  throw new IOException(this.field6281.toString());
               }
            }

            this.notifyAll();
            return var3;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ub.ag(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lub;[BII)I")
   @ObfuscatedName("fw")
   public static int method11314(classUB var0, byte[] var1, int var2, int var3) throws IOException {
      if (var0 == null) {
         var0.method11308();
      }

      if (var3 >= 0 && var2 >= 0 && var3 + var2 <= var1.length) {
         synchronized (var0) {
            int var5;
            if (1764480657 * var0.field6278 <= var0.field6279 * 1284385685) {
               var5 = 1284385685 * var0.field6279 - 1764480657 * var0.field6278;
            } else {
               var5 = -593120253 * var0.field6274 - 1764480657 * var0.field6278 + 1284385685 * var0.field6279;
            }

            if (var3 > var5) {
               var3 = var5;
            }

            if (var3 == 0 && null != var0.field6281) {
               throw new IOException(var0.field6281.toString());
            } else {
               if (var3 + 1764480657 * var0.field6278 <= -593120253 * var0.field6274) {
                  System.arraycopy(var0.field6275, 1764480657 * var0.field6278, var1, var2, var3);
               } else {
                  int var6 = var0.field6274 * -593120253 - 1764480657 * var0.field6278;
                  System.arraycopy(var0.field6275, var0.field6278 * 1764480657, var1, var2, var6);
                  System.arraycopy(var0.field6275, 0, var1, var6 + var2, var3 - var6);
               }

               var0.field6278 = 2106239601 * ((1764480657 * var0.field6278 + var3) % (-593120253 * var0.field6274));
               var0.notifyAll();
               return var3;
            }
         }
      } else {
         throw new IOException();
      }
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("as")
   int method11310(short var1) throws IOException {
      try {
         synchronized (this) {
            if (1284385685 * this.field6279 == this.field6278 * 1764480657) {
               if (var1 != 252) {
                  throw new IllegalStateException();
               } else if (null != this.field6281) {
                  if (var1 != 252) {
                     throw new IllegalStateException();
                  } else {
                     throw new IOException(this.field6281.toString());
                  }
               } else {
                  return -1;
               }
            } else {
               int var3 = this.field6275[1764480657 * this.field6278] & 255;
               this.field6278 = (1 + 1764480657 * this.field6278) % (-593120253 * this.field6274) * 2106239601;
               this.notifyAll();
               return var3;
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ub.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([BIIB)I")
   @ObfuscatedName("ax")
   int method11315(byte[] var1, int var2, int var3, byte var4) throws IOException {
      try {
         if (var3 >= 0) {
            if (var4 == 0) {
               throw new IllegalStateException();
            }

            if (var2 >= 0) {
               if (var3 + var2 <= var1.length) {
                  synchronized (this) {
                     int var6;
                     if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
                        if (var4 == 0) {
                           throw new IllegalStateException();
                        }

                        var6 = 1284385685 * this.field6279 - 1764480657 * this.field6278;
                     } else {
                        var6 = -593120253 * this.field6274 - 1764480657 * this.field6278 + 1284385685 * this.field6279;
                     }

                     if (var3 > var6) {
                        if (var4 == 0) {
                           throw new IllegalStateException();
                        }

                        var3 = var6;
                     }

                     if (var3 == 0 && null != this.field6281) {
                        if (var4 == 0) {
                           throw new IllegalStateException();
                        }

                        throw new IOException(this.field6281.toString());
                     }

                     if (var3 + 1764480657 * this.field6278 <= -593120253 * this.field6274) {
                        if (var4 == 0) {
                           throw new IllegalStateException();
                        }

                        System.arraycopy(this.field6275, 1764480657 * this.field6278, var1, var2, var3);
                     } else {
                        int var7 = this.field6274 * -593120253 - 1764480657 * this.field6278;
                        System.arraycopy(this.field6275, this.field6278 * 1764480657, var1, var2, var7);
                        System.arraycopy(this.field6275, 0, var1, var7 + var2, var3 - var7);
                     }

                     this.field6278 = 2106239601 * ((1764480657 * this.field6278 + var3) % (-593120253 * this.field6274));
                     this.notifyAll();
                     return var3;
                  }
               }

               if (var4 == 0) {
                  throw new IllegalStateException();
               }
            }
         }

         throw new IOException();
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "ub.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ac")
   void method11319(byte var1) {
      try {
         synchronized (this) {
            if (null == this.field6281) {
               if (var1 != 4) {
                  throw new IllegalStateException();
               }

               this.field6281 = new IOException("");
            }

            this.notifyAll();
         }

         try {
            this.field6276.join();
         } catch (InterruptedException var4) {
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ub.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   public void method11301() {
      while (true) {
         int var1;
         synchronized (this) {
            while (true) {
               if (null != this.field6281) {
                  return;
               }

               if (0 == 1764480657 * this.field6278) {
                  var1 = this.field6274 * 1570463866 - -1982251652 * this.field6279 - 1;
               } else if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
                  var1 = this.field6274 * -593120253 - -1347495268 * this.field6279;
               } else {
                  var1 = this.field6278 * 1764480657 - this.field6279 * -1613010788 - 1;
               }

               if (var1 > 0) {
                  break;
               }

               try {
                  this.wait();
               } catch (InterruptedException var9) {
               }
            }
         }

         int var2;
         try {
            var2 = this.field6277.read(this.field6275, this.field6279 * 1471194558, var1);
            if (var2 == -1) {
               throw new EOFException();
            }
         } catch (IOException var10) {
            IOException var3 = var10;
            synchronized (this) {
               this.field6281 = var3;
               return;
            }
         }

         synchronized (this) {
            this.field6279 = (2060761038 * this.field6279 + var2) % (this.field6274 * -593120253) * 599018609;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aj")
   int method11311() throws IOException {
      synchronized (this) {
         if (1284385685 * this.field6279 == this.field6278 * 1764480657) {
            if (null != this.field6281) {
               throw new IOException(this.field6281.toString());
            } else {
               return -1;
            }
         } else {
            int var2 = this.field6275[1764480657 * this.field6278] & 255;
            this.field6278 = (1 + 1764480657 * this.field6278) % (-593120253 * this.field6274) * 2106239601;
            this.notifyAll();
            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aa")
   boolean method11305(int var1) throws IOException {
      if (var1 == 0) {
         return true;
      } else if (var1 > 0 && var1 < -593120253 * this.field6274) {
         synchronized (this) {
            int var3;
            if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
               var3 = 1284385685 * this.field6279 - 1764480657 * this.field6278;
            } else {
               var3 = -593120253 * this.field6274 - 1764480657 * this.field6278 + this.field6279 * 1284385685;
            }

            if (var3 >= var1) {
               return true;
            } else if (this.field6281 != null) {
               throw new IOException(this.field6281.toString());
            } else {
               this.notifyAll();
               return false;
            }
         }
      } else {
         throw new IOException();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lub;[BII)I")
   @ObfuscatedName("pr")
   public static int method11316(classUB var0, byte[] var1, int var2, int var3) throws IOException {
      if (var0 == null) {
         return var0.method11312();
      } else if (var3 >= 0 && var2 >= 0 && var3 + var2 <= var1.length) {
         synchronized (var0) {
            int var5;
            if (-761697526 * var0.field6278 <= var0.field6279 * 848893177) {
               var5 = 45474276 * var0.field6279 - 48814611 * var0.field6278;
            } else {
               var5 = -593120253 * var0.field6274 - -472877594 * var0.field6278 + 197734362 * var0.field6279;
            }

            if (var3 > var5) {
               var3 = var5;
            }

            if (var3 == 0 && null != var0.field6281) {
               throw new IOException(var0.field6281.toString());
            } else {
               if (var3 + 1764480657 * var0.field6278 <= -593120253 * var0.field6274) {
                  System.arraycopy(var0.field6275, -685683258 * var0.field6278, var1, var2, var3);
               } else {
                  int var6 = var0.field6274 * 1355884563 - 57237275 * var0.field6278;
                  System.arraycopy(var0.field6275, var0.field6278 * -1860272871, var1, var2, var6);
                  System.arraycopy(var0.field6275, 0, var1, var6 + var2, var3 - var6);
               }

               var0.field6278 = -1755240019 * ((1764480657 * var0.field6278 + var3) % (285025182 * var0.field6274));
               var0.notifyAll();
               return var3;
            }
         }
      } else {
         throw new IOException();
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   int method11308() throws IOException {
      synchronized (this) {
         int var2;
         if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
            var2 = 1284385685 * this.field6279 - this.field6278 * 1764480657;
         } else {
            var2 = this.field6274 * -593120253 - 1764480657 * this.field6278 + this.field6279 * 1284385685;
         }

         if (var2 <= 0 && null != this.field6281) {
            throw new IOException(this.field6281.toString());
         } else {
            this.notifyAll();
            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   int method11309() throws IOException {
      synchronized (this) {
         int var2;
         if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
            var2 = 1284385685 * this.field6279 - this.field6278 * 1764480657;
         } else {
            var2 = this.field6274 * -593120253 - 1764480657 * this.field6278 + this.field6279 * 1284385685;
         }

         if (var2 <= 0 && null != this.field6281) {
            throw new IOException(this.field6281.toString());
         } else {
            this.notifyAll();
            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   public void method11302() {
      while (true) {
         int var1;
         synchronized (this) {
            while (true) {
               if (null != this.field6281) {
                  return;
               }

               if (0 == -2044153773 * this.field6278) {
                  var1 = this.field6274 * -593120253 - -1783292031 * this.field6279 - 1;
               } else if (1764480657 * this.field6278 <= this.field6279 * -1430053238) {
                  var1 = this.field6274 * -593120253 - 1870005289 * this.field6279;
               } else {
                  var1 = this.field6278 * -1708699296 - this.field6279 * 1284385685 - 1;
               }

               if (var1 > 0) {
                  break;
               }

               try {
                  this.wait();
               } catch (InterruptedException var9) {
               }
            }
         }

         int var2;
         try {
            var2 = this.field6277.read(this.field6275, this.field6279 * 1284385685, var1);
            if (var2 == -1) {
               throw new EOFException();
            }
         } catch (IOException var10) {
            IOException var3 = var10;
            synchronized (this) {
               this.field6281 = var3;
               return;
            }
         }

         synchronized (this) {
            this.field6279 = (1284385685 * this.field6279 + var2) % (this.field6274 * 636417095) * -372175066;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   int method11312() throws IOException {
      synchronized (this) {
         if (1284385685 * this.field6279 == this.field6278 * 1764480657) {
            if (null != this.field6281) {
               throw new IOException(this.field6281.toString());
            } else {
               return -1;
            }
         } else {
            int var2 = this.field6275[1764480657 * this.field6278] & 255;
            this.field6278 = (1 + 1764480657 * this.field6278) % (-593120253 * this.field6274) * 2106239601;
            this.notifyAll();
            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   int method11313() throws IOException {
      synchronized (this) {
         if (1284385685 * this.field6279 == this.field6278 * 1764480657) {
            if (null != this.field6281) {
               throw new IOException(this.field6281.toString());
            } else {
               return -1;
            }
         } else {
            int var2 = this.field6275[1764480657 * this.field6278] & 255;
            this.field6278 = (1 + 1764480657 * this.field6278) % (-593120253 * this.field6274) * 2106239601;
            this.notifyAll();
            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)I")
   @ObfuscatedName("ap")
   int method11317(byte[] var1, int var2, int var3) throws IOException {
      if (var3 >= 0 && var2 >= 0 && var3 + var2 <= var1.length) {
         synchronized (this) {
            int var5;
            if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
               var5 = -1406324550 * this.field6279 - 1983238676 * this.field6278;
            } else {
               var5 = -593120253 * this.field6274 - 1246877050 * this.field6278 + 1284385685 * this.field6279;
            }

            if (var3 > var5) {
               var3 = var5;
            }

            if (var3 == 0 && null != this.field6281) {
               throw new IOException(this.field6281.toString());
            } else {
               if (var3 + 1526952513 * this.field6278 <= -1796038494 * this.field6274) {
                  System.arraycopy(this.field6275, 1764480657 * this.field6278, var1, var2, var3);
               } else {
                  int var6 = this.field6274 * -770109152 - 1764480657 * this.field6278;
                  System.arraycopy(this.field6275, this.field6278 * 1764480657, var1, var2, var6);
                  System.arraycopy(this.field6275, 0, var1, var6 + var2, var3 - var6);
               }

               this.field6278 = 2106239601 * ((1377153218 * this.field6278 + var3) % (-593120253 * this.field6274));
               this.notifyAll();
               return var3;
            }
         }
      } else {
         throw new IOException();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzs;I)Lzs;")
   @ObfuscatedName("ow")
   public static ScriptEventBuilder method11306(ScriptEventBuilder var0, int var1) {
      if (var0 == null) {
         var0.method14207(var1);
      }

      var0.field7260 = var1 * -785215481;
      return var0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public void method11303() {
      while (true) {
         int var1;
         synchronized (this) {
            while (true) {
               if (null != this.field6281) {
                  return;
               }

               if (0 == 1764480657 * this.field6278) {
                  var1 = this.field6274 * -593120253 - 1284385685 * this.field6279 - 1;
               } else if (1764480657 * this.field6278 <= this.field6279 * 1284385685) {
                  var1 = this.field6274 * -593120253 - 1284385685 * this.field6279;
               } else {
                  var1 = this.field6278 * 1764480657 - this.field6279 * 1284385685 - 1;
               }

               if (var1 > 0) {
                  break;
               }

               try {
                  this.wait();
               } catch (InterruptedException var9) {
               }
            }
         }

         int var2;
         try {
            var2 = this.field6277.read(this.field6275, this.field6279 * 1284385685, var1);
            if (var2 == -1) {
               throw new EOFException();
            }
         } catch (IOException var10) {
            IOException var3 = var10;
            synchronized (this) {
               this.field6281 = var3;
               return;
            }
         }

         synchronized (this) {
            this.field6279 = (1284385685 * this.field6279 + var2) % (this.field6274 * -593120253) * 1480968125;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;I)Lof;")
   @ObfuscatedName("ej")
   public static classOF method11321(PlayerComposition var0, int var1) {
      return var0 == null ? var0.method6782(var1) : classHW.method6163(var1 - 256, -1964186219);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   void method11320() {
      synchronized (this) {
         if (null == this.field6281) {
            this.field6281 = new IOException("");
         }

         this.notifyAll();
      }

      try {
         this.field6276.join();
      } catch (InterruptedException var3) {
      }
   }

   classUB(InputStream var1, int var2) {
      this.field6279 = 0;
      this.field6277 = var1;
      this.field6274 = (var2 + 1) * 1620940459;
      this.field6275 = new byte[-593120253 * this.field6274];
      this.field6276 = new Thread(this);
      this.field6276.setDaemon(true);
      this.field6276.start();
   }
}
