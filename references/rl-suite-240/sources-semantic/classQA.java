import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import net.runelite.api.events.PostAnimation;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qa")
public class classQA {
   @ObfuscatedName("ag")
   boolean field5505 = false;
   @ObfuscatedSignature(descriptor = "Lqy;")
   @ObfuscatedName("af")
   final classQY field5502;
   @ObfuscatedSignature(descriptor = "Lqq;")
   @ObfuscatedName("ae")
   final classQQ field5503;
   @ObfuscatedSignature(descriptor = "Lwg;")
   @ObfuscatedName("ab")
   classWG field5508;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field5506 = 16777215;
   @ObfuscatedName("as")
   boolean field5504 = false;
   @ObfuscatedName("az")
   final HttpsURLConnection field5500;
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   public static final int field5507 = 33;
   @ObfuscatedName("ax")
   int field5501 = -1265793504;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;)I")
   @ObfuscatedName("da")
   public static int method9476(Actor var0) {
      int var1 = 128 * var0.field1472[-1798356091 * var0.field1471 - 1] + var0.field1439 * -14785344;
      int var2 = var0.field1431[var0.field1471 * -1798356091 - 1] * 128 + var0.field1439 * -14785344;
      if (var0.field1484 * -1547553299 < var1) {
         if (-1272026483 * var0.field1486 < var2) {
            return 1280;
         } else {
            return var0.field1486 * -1272026483 > var2 ? 1792 : 1536;
         }
      } else if (-1547553299 * var0.field1484 > var1) {
         if (var0.field1486 * -1272026483 < var2) {
            return 768;
         } else {
            return var0.field1486 * -1272026483 > var2 ? 256 : 512;
         }
      } else if (var0.field1486 * -1272026483 < var2) {
         return 1024;
      } else {
         return var0.field1486 * -1272026483 > var2 ? 0 : -1;
      }
   }

   public classQA(URL var1, classQQ var2, classQY var3, boolean var4) throws IOException {
      if (!classQQ.method9751(var2, 1240044487)) {
         throw new UnsupportedEncodingException("Unsupported request method used " + var2.method9754(88836456));
      } else {
         this.field5500 = (HttpsURLConnection)var1.openConnection();
         if (!var4) {
            this.field5500.setSSLSocketFactory(classQL.method9657(1598274060));
         }

         this.field5503 = var2;
         this.field5502 = var3 != null ? var3 : new classQY();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lqy;")
   @ObfuscatedName("az")
   public classQY method9459(int var1) {
      try {
         return this.field5502;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qa.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lqg;")
   @ObfuscatedName("ad")
   classQG method9471() {
      try {
         if (!this.field5505 || this.field5500.getResponseCode() == -1) {
            return new classQG("No REST response has been received yet.");
         }
      } catch (IOException var10) {
         this.field5500.disconnect();
         return new classQG("Error decoding REST response code: " + var10.getMessage());
      }

      classQG var3;
      try {
         return new classQG(this.field5500);
      } catch (IOException var8) {
         var3 = new classQG("Error decoding REST response: " + var8.getMessage());
      } finally {
         this.field5500.disconnect();
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   boolean method9468(int var1) throws IOException {
      try {
         if (!this.field5505) {
            if (var1 == -1522817247) {
               throw new IllegalStateException();
            }

            this.method9467(880555563);
         }

         this.field5500.connect();
         boolean var10000;
         if (this.field5500.getResponseCode() == -1) {
            if (var1 == -1522817247) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qa.ab(" + ')');
      }
   }

   public classQA(URL var1, classQQ var2, boolean var3) throws IOException {
      this(var1, var2, new classQY(), var3);
   }

   @ObfuscatedSignature(descriptor = "()Lqy;")
   @ObfuscatedName("as")
   public classQY method9460() {
      return this.field5502;
   }

   @ObfuscatedSignature(descriptor = "(Lqa;Lwg;I)V")
   @ObfuscatedName("px")
   public static void method9461(classQA var0, classWG var1, int var2) {
      if (var0 == null) {
         var0.method9464(var1, var2);
      } else {
         try {
            if (!var0.field5505) {
               if (var1 == null) {
                  if (var2 == -103254840) {
                     throw new IllegalStateException();
                  } else {
                     var0.field5502.method9892("Content-Type", -945488981);
                     var0.field5508 = null;
                  }
               } else {
                  var0.field5508 = var1;
                  if (var0.field5508.vmethod631((byte)63) != null) {
                     var0.field5502.method9904(var0.field5508.vmethod631((byte)62), (byte)19);
                  } else {
                     var0.field5502.method9907(-874709466);
                  }
               }
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "qa.af(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;[SIII)V")
   @ObfuscatedName("af")
   static void method9458(String[] var0, short[] var1, int var2, int var3, int var4) {
      try {
         if (var2 < var3) {
            int var5 = (var2 + var3) / 2;
            int var6 = var2;
            String var7 = var0[var5];
            var0[var5] = var0[var3];
            var0[var3] = var7;
            short var8 = var1[var5];
            var1[var5] = var1[var3];
            var1[var3] = var8;

            for (int var9 = var2; var9 < var3; var9++) {
               if (var4 <= -1310151407) {
                  return;
               }

               if (null != var7) {
                  if (var4 <= -1310151407) {
                     throw new IllegalStateException();
                  }

                  if (var0[var9] == null || var0[var9].compareTo(var7) >= (var9 & 1)) {
                     continue;
                  }

                  if (var4 <= -1310151407) {
                     throw new IllegalStateException();
                  }
               }

               String var10 = var0[var9];
               var0[var9] = var0[var6];
               var0[var6] = var10;
               short var11 = var1[var9];
               var1[var9] = var1[var6];
               var1[var6++] = var11;
            }

            var0[var3] = var0[var6];
            var0[var6] = var7;
            var1[var3] = var1[var6];
            var1[var6] = var8;
            method9458(var0, var1, var2, var6 - 1, -1295167483);
            method9458(var0, var1, var6 + 1, var3, -449139230);
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "qa.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwg;)V")
   @ObfuscatedName("aa")
   public void method9462(classWG var1) {
      if (!this.field5505) {
         if (var1 == null) {
            this.field5502.method9892("Content-Type", 2139296128);
            this.field5508 = null;
         } else {
            this.field5508 = var1;
            if (this.field5508.vmethod631((byte)36) != null) {
               this.field5502.method9904(this.field5508.vmethod631((byte)107), (byte)19);
            } else {
               this.field5502.method9907(-974748383);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgc;IIIIZ)V")
   @ObfuscatedName("za")
   public static void method9474(CollisionData var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (var0 == null) {
         var0.method5385(var1, var1, var1, var1, var5);
      }

      var1 -= 370701143 * var0.field2556;
      var2 -= 1140151217 * var0.field2557;
      if (var3 == 0) {
         if (0 == var4) {
            CollisionData.method5367(var0, var1, var2, 128, (byte)-66);
            CollisionData.method5367(var0, var1 - 1, var2, 8, (byte)-62);
         }

         if (var4 == 1) {
            CollisionData.method5367(var0, var1, var2, 2, (byte)-39);
            CollisionData.method5367(var0, var1, var2 + 1, 32, (byte)-67);
         }

         if (var4 == 2) {
            CollisionData.method5367(var0, var1, var2, 8, (byte)-38);
            CollisionData.method5367(var0, 1 + var1, var2, 128, (byte)-121);
         }

         if (var4 == 3) {
            CollisionData.method5367(var0, var1, var2, 32, (byte)-17);
            CollisionData.method5367(var0, var1, var2 - 1, 2, (byte)-42);
         }
      }

      if (1 == var3 || 3 == var3) {
         if (var4 == 0) {
            CollisionData.method5367(var0, var1, var2, 1, (byte)-98);
            CollisionData.method5367(var0, var1 - 1, var2 + 1, 16, (byte)-42);
         }

         if (1 == var4) {
            CollisionData.method5367(var0, var1, var2, 4, (byte)-50);
            CollisionData.method5367(var0, 1 + var1, var2 + 1, 64, (byte)-21);
         }

         if (2 == var4) {
            CollisionData.method5367(var0, var1, var2, 16, (byte)-50);
            CollisionData.method5367(var0, var1 + 1, var2 - 1, 1, (byte)-10);
         }

         if (var4 == 3) {
            CollisionData.method5367(var0, var1, var2, 64, (byte)9);
            CollisionData.method5367(var0, var1 - 1, var2 - 1, 4, (byte)-43);
         }
      }

      if (var3 == 2) {
         if (0 == var4) {
            CollisionData.method5367(var0, var1, var2, 130, (byte)-107);
            CollisionData.method5367(var0, var1 - 1, var2, 8, (byte)-30);
            CollisionData.method5367(var0, var1, 1 + var2, 32, (byte)-71);
         }

         if (1 == var4) {
            CollisionData.method5367(var0, var1, var2, 10, (byte)-115);
            CollisionData.method5367(var0, var1, var2 + 1, 32, (byte)-42);
            CollisionData.method5367(var0, var1 + 1, var2, 128, (byte)-91);
         }

         if (var4 == 2) {
            CollisionData.method5367(var0, var1, var2, 40, (byte)-44);
            CollisionData.method5367(var0, 1 + var1, var2, 128, (byte)-33);
            CollisionData.method5367(var0, var1, var2 - 1, 2, (byte)-29);
         }

         if (var4 == 3) {
            CollisionData.method5367(var0, var1, var2, 160, (byte)5);
            CollisionData.method5367(var0, var1, var2 - 1, 2, (byte)-28);
            CollisionData.method5367(var0, var1 - 1, var2, 8, (byte)-59);
         }
      }

      if (var5) {
         if (0 == var3) {
            if (var4 == 0) {
               CollisionData.method5367(var0, var1, var2, 65536, (byte)-54);
               CollisionData.method5367(var0, var1 - 1, var2, 4096, (byte)-92);
            }

            if (1 == var4) {
               CollisionData.method5367(var0, var1, var2, 1024, (byte)-9);
               CollisionData.method5367(var0, var1, var2 + 1, 16384, (byte)-43);
            }

            if (2 == var4) {
               CollisionData.method5367(var0, var1, var2, 4096, (byte)-126);
               CollisionData.method5367(var0, 1 + var1, var2, 65536, (byte)-100);
            }

            if (3 == var4) {
               CollisionData.method5367(var0, var1, var2, 16384, (byte)-84);
               CollisionData.method5367(var0, var1, var2 - 1, 1024, (byte)-114);
            }
         }

         if (1 == var3 || var3 == 3) {
            if (var4 == 0) {
               CollisionData.method5367(var0, var1, var2, 512, (byte)-27);
               CollisionData.method5367(var0, var1 - 1, 1 + var2, 8192, (byte)-94);
            }

            if (var4 == 1) {
               CollisionData.method5367(var0, var1, var2, 2048, (byte)-13);
               CollisionData.method5367(var0, 1 + var1, 1 + var2, 32768, (byte)-59);
            }

            if (2 == var4) {
               CollisionData.method5367(var0, var1, var2, 8192, (byte)-114);
               CollisionData.method5367(var0, var1 + 1, var2 - 1, 512, (byte)-88);
            }

            if (3 == var4) {
               CollisionData.method5367(var0, var1, var2, 32768, (byte)-111);
               CollisionData.method5367(var0, var1 - 1, var2 - 1, 2048, (byte)-42);
            }
         }

         if (var3 == 2) {
            if (0 == var4) {
               CollisionData.method5367(var0, var1, var2, 66560, (byte)-99);
               CollisionData.method5367(var0, var1 - 1, var2, 4096, (byte)2);
               CollisionData.method5367(var0, var1, var2 + 1, 16384, (byte)-99);
            }

            if (1 == var4) {
               CollisionData.method5367(var0, var1, var2, 5120, (byte)-102);
               CollisionData.method5367(var0, var1, 1 + var2, 16384, (byte)-84);
               CollisionData.method5367(var0, 1 + var1, var2, 65536, (byte)-58);
            }

            if (var4 == 2) {
               CollisionData.method5367(var0, var1, var2, 20480, (byte)-27);
               CollisionData.method5367(var0, var1 + 1, var2, 65536, (byte)-27);
               CollisionData.method5367(var0, var1, var2 - 1, 1024, (byte)-115);
            }

            if (3 == var4) {
               CollisionData.method5367(var0, var1, var2, 81920, (byte)-14);
               CollisionData.method5367(var0, var1, var2 - 1, 1024, (byte)-15);
               CollisionData.method5367(var0, var1 - 1, var2, 4096, (byte)-77);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   void method9465() throws ProtocolException {
      if (!this.field5505) {
         this.field5500.setRequestMethod(this.field5503.method9754(425966815));
         this.field5502.method9882(this.field5500, (byte)0);
         if (this.field5503.method9758(643990299) && null != this.field5508) {
            this.field5500.setDoOutput(true);
            ByteArrayOutputStream var1 = new ByteArrayOutputStream();

            try {
               var1.write(this.field5508.vmethod637(-1390912683));
               var1.writeTo(this.field5500.getOutputStream());
            } catch (IOException var11) {
               var11.printStackTrace();
            } finally {
               try {
                  var1.close();
               } catch (IOException var10) {
                  var10.printStackTrace();
               }
            }
         }

         this.field5500.setConnectTimeout(-794691121 * this.field5501);
         this.field5500.setInstanceFollowRedirects(this.field5504);
         this.field5505 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lqg;")
   @ObfuscatedName("ag")
   classQG method9472(byte var1) {
      try {
         try {
            label65: {
               if (this.field5505) {
                  if (var1 == 1) {
                     throw new IllegalStateException();
                  }

                  if (this.field5500.getResponseCode() != -1) {
                     break label65;
                  }

                  if (var1 == 1) {
                     throw new IllegalStateException();
                  }
               }

               return new classQG("No REST response has been received yet.");
            }
         } catch (IOException var12) {
            this.field5500.disconnect();
            return new classQG("Error decoding REST response code: " + var12.getMessage());
         }

         classQG var4;
         try {
            return new classQG(this.field5500);
         } catch (IOException var10) {
            var4 = new classQG("Error decoding REST response: " + var10.getMessage());
         } finally {
            this.field5500.disconnect();
         }

         return var4;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "qa.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aj")
   boolean method9469() throws IOException {
      if (!this.field5505) {
         this.method9467(880555563);
      }

      this.field5500.connect();
      return this.field5500.getResponseCode() == -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqa;Lwg;)V")
   @ObfuscatedName("pg")
   public static void method9463(classQA var0, classWG var1) {
      if (!var0.field5505) {
         if (var1 == null) {
            var0.field5502.method9892("Content-Type", 902626256);
            var0.field5508 = null;
         } else {
            var0.field5508 = var1;
            if (var0.field5508.vmethod631((byte)103) != null) {
               var0.field5502.method9904(var0.field5508.vmethod631((byte)11), (byte)19);
            } else {
               var0.field5502.method9907(-1316975470);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ay")
   boolean method9470() throws IOException {
      if (!this.field5505) {
         this.method9467(880555563);
      }

      this.field5500.connect();
      return this.field5500.getResponseCode() == -1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   void method9466() throws ProtocolException {
      if (!this.field5505) {
         this.field5500.setRequestMethod(this.field5503.method9754(766059727));
         this.field5502.method9882(this.field5500, (byte)0);
         if (this.field5503.method9758(643990299) && null != this.field5508) {
            this.field5500.setDoOutput(true);
            ByteArrayOutputStream var1 = new ByteArrayOutputStream();

            try {
               var1.write(this.field5508.vmethod637(-1390912683));
               var1.writeTo(this.field5500.getOutputStream());
            } catch (IOException var11) {
               var11.printStackTrace();
            } finally {
               try {
                  var1.close();
               } catch (IOException var10) {
                  var10.printStackTrace();
               }
            }
         }

         this.field5500.setConnectTimeout(1848947901 * this.field5501);
         this.field5500.setInstanceFollowRedirects(this.field5504);
         this.field5505 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lqg;")
   @ObfuscatedName("aq")
   classQG method9473() {
      try {
         if (!this.field5505 || this.field5500.getResponseCode() == -1) {
            return new classQG("No REST response has been received yet.");
         }
      } catch (IOException var10) {
         this.field5500.disconnect();
         return new classQG("Error decoding REST response code: " + var10.getMessage());
      }

      classQG var3;
      try {
         return new classQG(this.field5500);
      } catch (IOException var8) {
         var3 = new classQG("Error decoding REST response: " + var8.getMessage());
      } finally {
         this.field5500.disconnect();
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(II)Lou;")
   @ObfuscatedName("az")
   public static Animation method9477(int var0, int var1) {
      synchronized (Animation.field5139) {
         int var5 = var0;
         int var6 = -1676962954;

         Animation var10000;
         try {
            Animation var7 = (Animation)NodeCache.method6460(Animation.field5139, var5);
            if (var7 != null) {
               if (var6 == 793448697) {
                  throw new IllegalStateException();
               }

               var10000 = var7;
            } else {
               byte[] var8 = Animation.field5151.method11942(12, var5, -617271788);
               var7 = new Animation(new classXY(var8), var5);
               Animation.field5139.method6464(var7, var5);
               var10000 = var7;
            }
         } catch (RuntimeException var9) {
            throw classEG.method3899(var9, "qa.az(" + ')');
         }

         Animation var3 = var10000;
         if (var3.field5116 == -1) {
            var3.field5116 = var0;
            classOE.field4841.getCallbacks().post(new PostAnimation(var3));
         }

         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;I)J")
   @ObfuscatedName("ad")
   public static long method9475(classTE var0, int var1) {
      try {
         classON.method8646(var0, classYY.field7109, -1336024436);
         int[] var2 = var0.method10791(1680031641);
         int var3 = var0.method10797(-1859683172);
         long var4 = 0L;

         for (int var6 = 0; var6 < var3; var6++) {
            if (var1 >= 1336932499) {
               throw new IllegalStateException();
            }

            var4 += var2[var6];
         }

         return var4;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "qa.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ae")
   void method9467(int var1) throws ProtocolException {
      try {
         if (this.field5505) {
            if (var1 != 880555563) {
               throw new IllegalStateException();
            }
         } else {
            this.field5500.setRequestMethod(this.field5503.method9754(-640518930));
            this.field5502.method9882(this.field5500, (byte)0);
            label76:
            if (!this.field5503.method9758(643990299) || null == this.field5508) {
               this.field5500.setConnectTimeout(-794691121 * this.field5501);
               this.field5500.setInstanceFollowRedirects(this.field5504);
               this.field5505 = true;
            } else if (var1 != 880555563) {
               throw new IllegalStateException();
            } else {
               this.field5500.setDoOutput(true);
               ByteArrayOutputStream var2 = new ByteArrayOutputStream();

               try {
                  try {
                     var2.write(this.field5508.vmethod637(-1390912683));
                     var2.writeTo(this.field5500.getOutputStream());
                  } catch (IOException var13) {
                     var13.printStackTrace();
                  }
                  break label76;
               } finally {
                  try {
                     var2.close();
                  } catch (IOException var12) {
                     var12.printStackTrace();
                  }
               }
            }
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "qa.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwg;I)V")
   @ObfuscatedName("af")
   public void method9464(classWG var1, int var2) {
      try {
         if (!this.field5504) {
            if (var1 == null) {
               if (var2 == -103254840) {
                  throw new IllegalStateException();
               } else {
                  this.field5502.method9892("Content-Type", -945488981);
                  this.field5508 = null;
               }
            } else {
               this.field5508 = var1;
               if (this.field5508.vmethod631((byte)63) != null) {
                  this.field5502.method9904(this.field5508.vmethod631((byte)62), (byte)19);
               } else {
                  this.field5502.method9907(-874709466);
               }
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qa.af(" + ')');
      }
   }
}
