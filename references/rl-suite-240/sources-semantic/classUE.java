import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ue")
public class classUE implements Runnable {
   @ObfuscatedName("ab")
   byte[] field6294;
   @ObfuscatedName("af")
   OutputStream field6289;
   @ObfuscatedName("ag")
   int field6291;
   @ObfuscatedName("ax")
   IOException field6296;
   @ObfuscatedName("as")
   int field6292 = 0;
   @ObfuscatedName("ae")
   int field6293 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field6295 = 6;
   @ObfuscatedName("ac")
   boolean field6297;
   @ObfuscatedName("az")
   Thread field6290;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   public void method11346() {
      do {
         int var1;
         synchronized (this) {
            while (true) {
               if (null != this.field6296) {
                  return;
               }

               if (this.field6292 * -1880294139 <= -909433061 * this.field6293) {
                  var1 = this.field6293 * -909433061 - -1880294139 * this.field6292;
               } else {
                  var1 = this.field6291 * -545605385 - this.field6292 * -1880294139 + this.field6293 * -909433061;
               }

               if (var1 > 0) {
                  break;
               }

               try {
                  this.field6289.flush();
               } catch (IOException var10) {
                  this.field6296 = var10;
                  return;
               }

               if (method11343(this, 2063367951)) {
                  return;
               }

               try {
                  this.wait();
               } catch (InterruptedException var11) {
               }
            }
         }

         try {
            if (var1 + this.field6292 * -1880294139 <= -545605385 * this.field6291) {
               this.field6289.write(this.field6294, -1880294139 * this.field6292, var1);
            } else {
               int var13 = -545605385 * this.field6291 - -1880294139 * this.field6292;
               this.field6289.write(this.field6294, -1880294139 * this.field6292, var13);
               this.field6289.write(this.field6294, 0, var1 - var13);
            }
         } catch (IOException var9) {
            IOException var2 = var9;
            synchronized (this) {
               this.field6296 = var2;
               return;
            }
         }

         synchronized (this) {
            this.field6292 = (this.field6292 * -1880294139 + var1) % (-545605385 * this.field6291) * -1503049779;
         }
      } while (!method11343(this, 2063367951));
   }

   @Override
   public void run() {
      try {
         do {
            int var1;
            synchronized (this) {
               while (true) {
                  if (null != this.field6296) {
                     return;
                  }

                  if (this.field6292 * -1880294139 <= -909433061 * this.field6293) {
                     var1 = this.field6293 * -909433061 - -1880294139 * this.field6292;
                  } else {
                     var1 = this.field6291 * -545605385 - this.field6292 * -1880294139 + this.field6293 * -909433061;
                  }

                  if (var1 > 0) {
                     break;
                  }

                  try {
                     this.field6289.flush();
                  } catch (IOException var10) {
                     this.field6296 = var10;
                     return;
                  }

                  if (method11343(this, 2063367951)) {
                     return;
                  }

                  try {
                     this.wait();
                  } catch (InterruptedException var11) {
                  }
               }
            }

            try {
               if (var1 + this.field6292 * -1880294139 <= -545605385 * this.field6291) {
                  this.field6289.write(this.field6294, -1880294139 * this.field6292, var1);
               } else {
                  int var14 = -545605385 * this.field6291 - -1880294139 * this.field6292;
                  this.field6289.write(this.field6294, -1880294139 * this.field6292, var14);
                  this.field6289.write(this.field6294, 0, var1 - var14);
               }
            } catch (IOException var9) {
               IOException var2 = var9;
               synchronized (this) {
                  this.field6296 = var2;
                  return;
               }
            }

            synchronized (this) {
               this.field6292 = (this.field6292 * -1880294139 + var1) % (-545605385 * this.field6291) * -1503049779;
            }
         } while (!method11343(this, 2063367951));
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "ue.run(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BIII)V")
   @ObfuscatedName("ag")
   void method11349(byte[] var1, int var2, int var3, int var4) throws IOException {
      try {
         if (var3 >= 0) {
            if (var4 != 1079426491) {
               return;
            }

            if (var2 >= 0) {
               if (var4 != 1079426491) {
                  throw new IllegalStateException();
               }

               if (var2 + var3 <= var1.length) {
                  synchronized (this) {
                     if (this.field6296 != null) {
                        throw new IOException(this.field6296.toString());
                     }

                     int var6;
                     if (this.field6292 * -1880294139 <= -909433061 * this.field6293) {
                        var6 = -545605385 * this.field6291 - this.field6293 * -909433061 + -1880294139 * this.field6292 - 1;
                     } else {
                        var6 = this.field6292 * -1880294139 - this.field6293 * -909433061 - 1;
                     }

                     if (var6 < var3) {
                        if (var4 != 1079426491) {
                           throw new IllegalStateException();
                        }

                        throw new IOException("");
                     }

                     if (this.field6293 * -909433061 + var3 <= this.field6291 * -545605385) {
                        System.arraycopy(var1, var2, this.field6294, -909433061 * this.field6293, var3);
                     } else {
                        int var7 = -545605385 * this.field6291 - this.field6293 * -909433061;
                        System.arraycopy(var1, var2, this.field6294, -909433061 * this.field6293, var7);
                        System.arraycopy(var1, var2 + var7, this.field6294, 0, var3 - var7);
                     }

                     this.field6293 = (var3 + this.field6293 * -909433061) % (this.field6291 * -545605385) * 300473107;
                     this.notifyAll();
                     return;
                  }
               }

               if (var4 != 1079426491) {
                  throw new IllegalStateException();
               }
            }
         }

         throw new IOException();
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "ue.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("al")
   void method11350(byte[] var1, int var2, int var3) throws IOException {
      if (var3 >= 0 && var2 >= 0 && var2 + var3 <= var1.length) {
         synchronized (this) {
            if (this.field6296 != null) {
               throw new IOException(this.field6296.toString());
            } else {
               int var5;
               if (this.field6292 * -1880294139 <= -909433061 * this.field6293) {
                  var5 = -545605385 * this.field6291 - this.field6293 * -909433061 + -1880294139 * this.field6292 - 1;
               } else {
                  var5 = this.field6292 * -1880294139 - this.field6293 * -909433061 - 1;
               }

               if (var5 < var3) {
                  throw new IOException("");
               } else {
                  if (this.field6293 * -909433061 + var3 <= this.field6291 * -545605385) {
                     System.arraycopy(var1, var2, this.field6294, -909433061 * this.field6293, var3);
                  } else {
                     int var6 = -545605385 * this.field6291 - this.field6293 * -909433061;
                     System.arraycopy(var1, var2, this.field6294, -909433061 * this.field6293, var6);
                     System.arraycopy(var1, var2 + var6, this.field6294, 0, var3 - var6);
                  }

                  this.field6293 = (var3 + this.field6293 * -909433061) % (this.field6291 * -545605385) * 300473107;
                  this.notifyAll();
               }
            }
         }
      } else {
         throw new IOException();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   void method11354(int var1) {
      try {
         synchronized (this) {
            this.field6297 = true;
            this.notifyAll();
         }

         try {
            this.field6290.join();
         } catch (InterruptedException var4) {
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ue.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   public void method11347() {
      do {
         int var1;
         synchronized (this) {
            while (true) {
               if (null != this.field6296) {
                  return;
               }

               if (this.field6292 * -1880294139 <= -1899747393 * this.field6293) {
                  var1 = this.field6293 * -909433061 - -803453971 * this.field6292;
               } else {
                  var1 = this.field6291 * -545605385 - this.field6292 * -1880294139 + this.field6293 * -909433061;
               }

               if (var1 > 0) {
                  break;
               }

               try {
                  this.field6289.flush();
               } catch (IOException var10) {
                  this.field6296 = var10;
                  return;
               }

               if (method11343(this, 2063367951)) {
                  return;
               }

               try {
                  this.wait();
               } catch (InterruptedException var11) {
               }
            }
         }

         try {
            if (var1 + this.field6292 * -1101924665 <= -181188660 * this.field6291) {
               this.field6289.write(this.field6294, -1880294139 * this.field6292, var1);
            } else {
               int var13 = -1519739478 * this.field6291 - -1880294139 * this.field6292;
               this.field6289.write(this.field6294, -1880294139 * this.field6292, var13);
               this.field6289.write(this.field6294, 0, var1 - var13);
            }
         } catch (IOException var9) {
            IOException var2 = var9;
            synchronized (this) {
               this.field6296 = var2;
               return;
            }
         }

         synchronized (this) {
            this.field6292 = (this.field6292 * -1880294139 + var1) % (-545605385 * this.field6291) * -1503049779;
         }
      } while (!method11343(this, 2063367951));
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public void method11348() {
      do {
         int var1;
         synchronized (this) {
            while (true) {
               if (null != this.field6296) {
                  return;
               }

               if (this.field6292 * -1880294139 <= -909433061 * this.field6293) {
                  var1 = this.field6293 * -909433061 - -1880294139 * this.field6292;
               } else {
                  var1 = this.field6291 * -545605385 - this.field6292 * -1880294139 + this.field6293 * -909433061;
               }

               if (var1 > 0) {
                  break;
               }

               try {
                  this.field6289.flush();
               } catch (IOException var10) {
                  this.field6296 = var10;
                  return;
               }

               if (method11343(this, 2063367951)) {
                  return;
               }

               try {
                  this.wait();
               } catch (InterruptedException var11) {
               }
            }
         }

         try {
            if (var1 + this.field6292 * -1880294139 <= -545605385 * this.field6291) {
               this.field6289.write(this.field6294, -1880294139 * this.field6292, var1);
            } else {
               int var13 = -545605385 * this.field6291 - -1880294139 * this.field6292;
               this.field6289.write(this.field6294, -1880294139 * this.field6292, var13);
               this.field6289.write(this.field6294, 0, var1 - var13);
            }
         } catch (IOException var9) {
            IOException var2 = var9;
            synchronized (this) {
               this.field6296 = var2;
               return;
            }
         }

         synchronized (this) {
            this.field6292 = (this.field6292 * -1880294139 + var1) % (-545605385 * this.field6291) * -1503049779;
         }
      } while (!method11343(this, 2063367951));
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("aa")
   void method11351(byte[] var1, int var2, int var3) throws IOException {
      if (var3 >= 0 && var2 >= 0 && var2 + var3 <= var1.length) {
         synchronized (this) {
            if (this.field6296 != null) {
               throw new IOException(this.field6296.toString());
            } else {
               int var5;
               if (this.field6292 * -1880294139 <= 965574750 * this.field6293) {
                  var5 = -545605385 * this.field6291 - this.field6293 * -909433061 + -1371617755 * this.field6292 - 1;
               } else {
                  var5 = this.field6292 * -1880294139 - this.field6293 * -1106213888 - 1;
               }

               if (var5 < var3) {
                  throw new IOException("");
               } else {
                  if (this.field6293 * 657590175 + var3 <= this.field6291 * -194953484) {
                     System.arraycopy(var1, var2, this.field6294, -909433061 * this.field6293, var3);
                  } else {
                     int var6 = -496881000 * this.field6291 - this.field6293 * -909433061;
                     System.arraycopy(var1, var2, this.field6294, 1896765946 * this.field6293, var6);
                     System.arraycopy(var1, var2 + var6, this.field6294, 0, var3 - var6);
                  }

                  this.field6293 = (var3 + this.field6293 * -909433061) % (this.field6291 * 1477120438) * -1749397527;
                  this.notifyAll();
               }
            }
         }
      } else {
         throw new IOException();
      }
   }

   classUE(OutputStream var1, int var2) {
      this.field6289 = var1;
      this.field6291 = (1 + var2) * -828274489;
      this.field6294 = new byte[this.field6291 * -545605385];
      this.field6290 = new Thread(this);
      this.field6290.setDaemon(true);
      this.field6290.start();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;Z)Laar;")
   @ObfuscatedName("hn")
   public static classAAR method11342(classDS var0, boolean var1) {
      if (var0 == null) {
         var0.method3608();
      }

      return WallObject.method4636("2", classYP.field7026.field4051, var1, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   void method11355() {
      synchronized (this) {
         this.field6297 = true;
         this.notifyAll();
      }

      try {
         this.field6290.join();
      } catch (InterruptedException var3) {
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lue;[BII)V")
   @ObfuscatedName("di")
   public static void method11352(classUE var0, byte[] var1, int var2, int var3) throws IOException {
      if (var0 == null) {
         var0.method11350(var1, var2, var2);
      } else if (var3 >= 0 && var2 >= 0 && var2 + var3 <= var1.length) {
         synchronized (var0) {
            if (var0.field6296 != null) {
               throw new IOException(var0.field6296.toString());
            } else {
               int var5;
               if (var0.field6292 * -1880294139 <= -909433061 * var0.field6293) {
                  var5 = -545605385 * var0.field6291 - var0.field6293 * -909433061 + -1880294139 * var0.field6292 - 1;
               } else {
                  var5 = var0.field6292 * -1880294139 - var0.field6293 * -909433061 - 1;
               }

               if (var5 < var3) {
                  throw new IOException("");
               } else {
                  if (var0.field6293 * -909433061 + var3 <= var0.field6291 * -545605385) {
                     System.arraycopy(var1, var2, var0.field6294, -909433061 * var0.field6293, var3);
                  } else {
                     int var6 = -545605385 * var0.field6291 - var0.field6293 * -909433061;
                     System.arraycopy(var1, var2, var0.field6294, -909433061 * var0.field6293, var6);
                     System.arraycopy(var1, var2 + var6, var0.field6294, 0, var3 - var6);
                  }

                  var0.field6293 = (var3 + var0.field6293 * -909433061) % (var0.field6291 * -545605385) * 300473107;
                  var0.notifyAll();
               }
            }
         }
      } else {
         throw new IOException();
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   void method11356() {
      synchronized (this) {
         this.field6297 = true;
         this.notifyAll();
      }

      try {
         this.field6290.join();
      } catch (InterruptedException var3) {
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lue;[BII)V")
   @ObfuscatedName("nk")
   public static void method11353(classUE var0, byte[] var1, int var2, int var3) throws IOException {
      if (var0 == null) {
         var0.method11345(var2);
      }

      if (var3 >= 0 && var2 >= 0 && var2 + var3 <= var1.length) {
         synchronized (var0) {
            if (var0.field6296 != null) {
               throw new IOException(var0.field6296.toString());
            } else {
               int var5;
               if (var0.field6292 * -2126605199 <= 669494848 * var0.field6293) {
                  var5 = -545605385 * var0.field6291 - var0.field6293 * -909433061 + -614198280 * var0.field6292 - 1;
               } else {
                  var5 = var0.field6292 * -1925844898 - var0.field6293 * -909433061 - 1;
               }

               if (var5 < var3) {
                  throw new IOException("");
               } else {
                  if (var0.field6293 * -909433061 + var3 <= var0.field6291 * -809629087) {
                     System.arraycopy(var1, var2, var0.field6294, 2069854708 * var0.field6293, var3);
                  } else {
                     int var6 = -1716723308 * var0.field6291 - var0.field6293 * -909433061;
                     System.arraycopy(var1, var2, var0.field6294, 1707888145 * var0.field6293, var6);
                     System.arraycopy(var1, var2 + var6, var0.field6294, 0, var3 - var6);
                  }

                  var0.field6293 = (var3 + var0.field6293 * -909433061) % (var0.field6291 * -545605385) * 300473107;
                  var0.notifyAll();
               }
            }
         }
      } else {
         throw new IOException();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lue;I)Z")
   @ObfuscatedName("lo")
   public static boolean method11343(classUE var0, int var1) {
      if (var0 == null) {
         return var0.method11345(var1);
      } else {
         try {
            if (var0.field6297) {
               try {
                  var0.field6289.close();
                  if (null == var0.field6296) {
                     if (var1 != 2063367951) {
                        throw new IllegalStateException();
                     }

                     var0.field6296 = new IOException("");
                  }
               } catch (IOException var3) {
                  if (null == var0.field6296) {
                     if (var1 != 2063367951) {
                        throw new IllegalStateException();
                     }

                     var0.field6296 = new IOException(var3);
                  }
               }

               return true;
            } else {
               return false;
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "ue.ab(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpz;)V")
   @ObfuscatedName("fg")
   public static void method11341(classPZ var0) {
      int var1 = 1 + (var0.field5493.length << 1);
      var0.field5493 = Arrays.copyOf(var0.field5493, var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lue;)Z")
   @ObfuscatedName("gu")
   public static boolean method11344(classUE var0) {
      if (var0.field6297) {
         try {
            var0.field6289.close();
            if (null == var0.field6296) {
               var0.field6296 = new IOException("");
            }
         } catch (IOException var2) {
            if (null == var0.field6296) {
               var0.field6296 = new IOException(var2);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   void method11357() {
      synchronized (this) {
         this.field6297 = true;
         this.notifyAll();
      }

      try {
         this.field6290.join();
      } catch (InterruptedException var3) {
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)I")
   @ObfuscatedName("af")
   static final int method11340(int var0, int var1, int var2, int var3, int var4) {
      try {
         return var1 * var2 - var3 * var0 >> 16;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ue.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   boolean method11345(int var1) {
      try {
         if (this.field6297) {
            try {
               this.field6289.close();
               if (null == this.field6296) {
                  if (var1 != 2063367951) {
                     throw new IllegalStateException();
                  }

                  this.field6296 = new IOException("");
               }
            } catch (IOException var3) {
               if (null == this.field6296) {
                  if (var1 != 2063367951) {
                     throw new IllegalStateException();
                  }

                  this.field6296 = new IOException(var3);
               }
            }

            return true;
         } else {
            return false;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ue.ab(" + ')');
      }
   }
}
