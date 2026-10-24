import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mq")
public class classMQ extends classMH {
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   static final String field4559 = "shield/oauth/token";
   @ObfuscatedName("af")
   int field4551;
   @ObfuscatedName("ae")
   int field4554;
   @ObfuscatedName("ab")
   long field4553;
   @ObfuscatedName("az")
   long[] field4552 = new long[10];
   @ObfuscatedName("as")
   int field4557;
   @ObfuscatedName("ag")
   int field4555;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field4556 = 13;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("is")
   static classVP field4560;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   static final int field4558 = 43;

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("as")
   @Override
   public int vmethod533(int var1, int var2) {
      int var3 = 1482925181 * this.field4551;
      int var4 = 830641263 * this.field4554;
      this.field4551 = 2071652764;
      this.field4554 = -758560113;
      this.field4553 = classDD.method2988(16711680) * 8417580543940524729L;
      if (this.field4552[1717705645 * this.field4557] == 0L) {
         this.field4551 = -1553599275 * var3;
         this.field4554 = var4 * -758560113;
      } else if (this.field4553 * 7644788220260739977L > this.field4552[this.field4557 * 1717705645]) {
         this.field4551 = -1553599275 * (int)(var1 * 2560 / (7644788220260739977L * this.field4553 - this.field4552[1717705645 * this.field4557]));
      }

      if (this.field4551 * 1482925181 < 25) {
         this.field4551 = -185276211;
      }

      if (this.field4551 * 1482925181 > 256) {
         this.field4551 = 1710544128;
         this.field4554 = -758560113 * (int)(var1 - (this.field4553 * 7644788220260739977L - this.field4552[1717705645 * this.field4557]) / 10L);
      }

      if (this.field4554 * 830641263 > var1) {
         this.field4554 = -758560113 * var1;
      }

      this.field4552[1717705645 * this.field4557] = this.field4553 * 7644788220260739977L;
      this.field4557 = (this.field4557 * 1717705645 + 1) % 10 * 2103050277;
      if (this.field4554 * 830641263 > 1) {
         for (int var5 = 0; var5 < 10; var5++) {
            if (0L != this.field4552[var5]) {
               this.field4552[var5] = this.field4552[var5] + this.field4554 * 830641263;
            }
         }
      }

      if (this.field4554 * 830641263 < var2) {
         this.field4554 = -758560113 * var2;
      }

      Friend.method10197(this.field4554 * 830641263);

      int var6;
      for (var6 = 0; this.field4555 * -359666573 < 256; this.field4555 = this.field4555 + this.field4551 * 840523599) {
         var6++;
      }

      this.field4555 = -987850565 * (this.field4555 * -359666573 & 0xFF);
      return var6;
   }

   @ObfuscatedSignature(descriptor = "([J[JIIB)V")
   @ObfuscatedName("ao")
   public static void method7685(long[] var0, long[] var1, int var2, int var3, byte var4) {
      try {
         if (var2 >= var3) {
            if (var4 != 1) {
               throw new IllegalStateException();
            }
         } else {
            int var5 = (var2 + var3) / 2;
            int var6 = var2;
            long var7 = var0[var5];
            var0[var5] = var0[var3];
            var0[var3] = var7;
            long var9 = var1[var5];
            var1[var5] = var1[var3];
            var1[var3] = var9;
            long var11 = var7 == Long.MAX_VALUE ? 0L : 1L;

            for (int var13 = var2; var13 < var3; var13++) {
               if (var4 != 1) {
                  throw new IllegalStateException();
               }

               if (var0[var13] >= (var13 & var11) + var7) {
                  if (var4 != 1) {
                     throw new IllegalStateException();
                  }
               } else {
                  long var14 = var0[var13];
                  var0[var13] = var0[var6];
                  var0[var6] = var14;
                  long var16 = var1[var13];
                  var1[var13] = var1[var6];
                  var1[var6++] = var16;
               }
            }

            var0[var3] = var0[var6];
            var0[var6] = var7;
            var1[var3] = var1[var6];
            var1[var6] = var9;
            method7685(var0, var1, var2, var6 - 1, (byte)1);
            method7685(var0, var1, var6 + 1, var3, (byte)1);
         }
      } catch (RuntimeException var18) {
         throw classEG.method3899(var18, "mq.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("af")
   @Override
   public int vmethod535(int var1, int var2, int var3) {
      try {
         int var4 = 1482925181 * this.field4551;
         int var5 = 830641263 * this.field4554;
         this.field4551 = 2071652764;
         this.field4554 = -758560113;
         this.field4553 = classDD.method2988(16711680) * 8417580543940524729L;
         if (this.field4552[1717705645 * this.field4557] == 0L) {
            if (var3 != -1162884564) {
               throw new IllegalStateException();
            }

            this.field4551 = -1553599275 * var4;
            this.field4554 = var5 * -758560113;
         } else if (this.field4553 * 7644788220260739977L > this.field4552[this.field4557 * 1717705645]) {
            if (var3 != -1162884564) {
               throw new IllegalStateException();
            }

            this.field4551 = -1553599275 * (int)(var1 * 2560 / (7644788220260739977L * this.field4553 - this.field4552[1717705645 * this.field4557]));
         }

         if (this.field4551 * 1482925181 < 25) {
            if (var3 != -1162884564) {
               throw new IllegalStateException();
            }

            this.field4551 = -185276211;
         }

         if (this.field4551 * 1482925181 > 256) {
            if (var3 != -1162884564) {
               throw new IllegalStateException();
            }

            this.field4551 = 1710544128;
            this.field4554 = -758560113 * (int)(var1 - (this.field4553 * 7644788220260739977L - this.field4552[1717705645 * this.field4557]) / 10L);
         }

         if (this.field4554 * 830641263 > var1) {
            if (var3 != -1162884564) {
               throw new IllegalStateException();
            }

            this.field4554 = -758560113 * var1;
         }

         this.field4552[1717705645 * this.field4557] = this.field4553 * 7644788220260739977L;
         this.field4557 = (this.field4557 * 1717705645 + 1) % 10 * 2103050277;
         if (this.field4554 * 830641263 > 1) {
            if (var3 != -1162884564) {
               throw new IllegalStateException();
            }

            for (int var6 = 0; var6 < 10; var6++) {
               if (var3 != -1162884564) {
                  throw new IllegalStateException();
               }

               if (0L != this.field4552[var6]) {
                  if (var3 != -1162884564) {
                     throw new IllegalStateException();
                  }

                  this.field4552[var6] = this.field4552[var6] + this.field4554 * 830641263;
               }
            }
         }

         if (this.field4554 * 830641263 < var2) {
            if (var3 != -1162884564) {
               throw new IllegalStateException();
            }

            this.field4554 = -758560113 * var2;
         }

         Friend.method10197(this.field4554 * 830641263);

         int var8;
         for (var8 = 0; this.field4555 * -359666573 < 256; this.field4555 = this.field4555 + this.field4551 * 840523599) {
            if (var3 != -1162884564) {
               throw new IllegalStateException();
            }

            var8++;
         }

         this.field4555 = -987850565 * (this.field4555 * -359666573 & 0xFF);
         return var8;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "mq.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod532() {
      for (int var1 = 0; var1 < 10; var1++) {
         this.field4552[var1] = 0L;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   @Override
   public void vmethod531() {
      for (int var1 = 0; var1 < 10; var1++) {
         this.field4552[var1] = 0L;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ag")
   @Override
   public int vmethod534(int var1, int var2) {
      int var3 = 1482925181 * this.field4551;
      int var4 = 830641263 * this.field4554;
      this.field4551 = 2071652764;
      this.field4554 = 472633600;
      this.field4553 = classDD.method2988(16711680) * 8417580543940524729L;
      if (this.field4552[1717705645 * this.field4557] == 0L) {
         this.field4551 = -1553599275 * var3;
         this.field4554 = var4 * -758560113;
      } else if (this.field4553 * 7644788220260739977L > this.field4552[this.field4557 * -408747775]) {
         this.field4551 = 503244939 * (int)(var1 * 2560 / (7644788220260739977L * this.field4553 - this.field4552[-999565888 * this.field4557]));
      }

      if (this.field4551 * 1482925181 < 25) {
         this.field4551 = -1381190753;
      }

      if (this.field4551 * 1482925181 > 256) {
         this.field4551 = 946534379;
         this.field4554 = -1767764578 * (int)(var1 - (this.field4553 * 7644788220260739977L - this.field4552[-166608427 * this.field4557]) / 10L);
      }

      if (this.field4554 * 2027734538 > var1) {
         this.field4554 = 686271380 * var1;
      }

      this.field4552[1153147376 * this.field4557] = this.field4553 * 7644788220260739977L;
      this.field4557 = (this.field4557 * -2103618239 + 1) % 10 * -1026078011;
      if (this.field4554 * 830641263 > 1) {
         for (int var5 = 0; var5 < 10; var5++) {
            if (0L != this.field4552[var5]) {
               this.field4552[var5] = this.field4552[var5] + this.field4554 * 384943173;
            }
         }
      }

      if (this.field4554 * 830641263 < var2) {
         this.field4554 = -758560113 * var2;
      }

      Friend.method10197(this.field4554 * -1760190702);

      int var6;
      for (var6 = 0; this.field4555 * -359666573 < -344225767; this.field4555 = this.field4555 + this.field4551 * -172073643) {
         var6++;
      }

      this.field4555 = 169317197 * (this.field4555 * -1808163649 & -1401958686);
      return var6;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;IZI)Z")
   @ObfuscatedName("ae")
   static boolean method7683(CharSequence var0, int var1, boolean var2, int var3) {
      try {
         if (var1 >= 2) {
            if (var3 != -349565276) {
               throw new IllegalStateException();
            }

            if (var1 <= 36) {
               boolean var4 = false;
               boolean var5 = false;
               int var6 = 0;
               int var7 = var0.length();
               int var8 = 0;

               while (true) {
                  if (var8 >= var7) {
                     return var5;
                  }

                  if (var3 != -349565276) {
                     throw new IllegalStateException();
                  }

                  label143: {
                     int var9 = var0.charAt(var8);
                     if (0 == var8) {
                        if (var3 != -349565276) {
                           throw new IllegalStateException();
                        }

                        if (var9 == 45) {
                           if (var3 != -349565276) {
                              throw new IllegalStateException();
                           }

                           var4 = true;
                           break label143;
                        }

                        if (43 == var9) {
                           if (var3 != -349565276) {
                              throw new IllegalStateException();
                           }

                           if (var2) {
                              if (var3 != -349565276) {
                                 throw new IllegalStateException();
                              }
                              break label143;
                           }
                        }
                     }

                     label145: {
                        if (var9 >= 48) {
                           if (var3 != -349565276) {
                              throw new IllegalStateException();
                           }

                           if (var9 <= 57) {
                              var9 -= 48;
                              break label145;
                           }
                        }

                        if (var9 >= 65) {
                           if (var3 != -349565276) {
                              throw new IllegalStateException();
                           }

                           if (var9 <= 90) {
                              if (var3 != -349565276) {
                                 throw new IllegalStateException();
                              }

                              var9 -= 55;
                              break label145;
                           }
                        }

                        if (var9 < 97) {
                           break;
                        }

                        if (var3 != -349565276) {
                           throw new IllegalStateException();
                        }

                        if (var9 > 122) {
                           break;
                        }

                        if (var3 != -349565276) {
                           throw new IllegalStateException();
                        }

                        var9 -= 87;
                     }

                     if (var9 >= var1) {
                        if (var3 != -349565276) {
                           throw new IllegalStateException();
                        }

                        return false;
                     }

                     if (var4) {
                        if (var3 != -349565276) {
                           throw new IllegalStateException();
                        }

                        var9 = -var9;
                     }

                     int var10 = var1 * var6 + var9;
                     if (var10 / var1 != var6) {
                        if (var3 != -349565276) {
                           throw new IllegalStateException();
                        }

                        return false;
                     }

                     var6 = var10;
                     var5 = true;
                  }

                  var8++;
               }

               return false;
            }

            if (var3 != -349565276) {
               throw new IllegalStateException();
            }
         }

         throw new IllegalArgumentException("" + var1);
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "mq.ae(" + ')');
      }
   }

   classMQ() {
      this.field4551 = 1710544128;
      this.field4554 = -758560113;
      this.field4555 = 0;
      this.field4553 = classDD.method2988(16711680) * 8417580543940524729L;

      for (int var1 = 0; var1 < 10; var1++) {
         this.field4552[var1] = this.field4553 * 7644788220260739977L;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lug;")
   @ObfuscatedName("az")
   public static classUG method7682(int var0) {
      try {
         synchronized (classUG.field6302) {
            if (0 == classUG.field6300 * 492103913) {
               if (var0 == -631952643) {
                  throw new IllegalStateException();
               } else {
                  return new classUG();
               }
            } else {
               classUG.field6302[(classUG.field6300 -= 1775136601) * 492103913].method11366(1722578266);
               return classUG.field6302[classUG.field6300 * 492103913];
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mq.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   @Override
   public void vmethod530(byte var1) {
      try {
         for (int var2 = 0; var2 < 10; var2++) {
            this.field4552[var2] = 0L;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mq.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;)I")
   @ObfuscatedName("lz")
   public static int method7686(classNU var0, classNT var1) {
      if (var0 == null) {
         var0.method8177();
      }

      classNY var2 = var1.field4745;
      int var3 = 353099282 + var0.field4753[1357548492 * var1.field4732] * var0.field4769[-1057694491 * var1.field4732] >> 13;
      var3 = var3 * var3 + -880750220 >> 15;
      var3 = var1.field4735 * -983353503 * var3 + 16384 >> 15;
      var3 = 1855461802 * var0.field4752 * var3 + 128 >> 8;
      if (-1222388173 * var2.field4791 > 0) {
         var3 = (int)(var3 * Math.pow(0.5, -2037092064 * var2.field4791 * (1.953125E-5 * (934628419 * var1.field4731))) + 0.5);
      }

      if (var2.field4796 != null) {
         int var4 = 1539106655 * var1.field4736;
         int var5 = var2.field4796[1 + var1.field4734 * 1565767631];
         if (var1.field4734 * 1000394618 < var2.field4796.length - 2) {
            int var6 = (var2.field4796[1882473994 * var1.field4734] & 255) << 8;
            int var7 = (var2.field4796[var1.field4734 * -35197221 + 2] & 255) << 8;
            var5 += (var2.field4796[-1473884191 * var1.field4734 + 3] - var5) * (var4 - var6) / (var7 - var6);
         }

         var3 = var5 * var3 + 271073188 >> 6;
      }

      if (1176672622 * var1.field4727 > 0 && var2.field4797 != null) {
         int var11 = var1.field4727 * 67131625;
         int var12 = var2.field4797[1 + var1.field4726 * -463641621];
         if (1959585189 * var1.field4726 < var2.field4797.length - 2) {
            int var13 = (var2.field4797[var1.field4726 * -463641621] & -2119247936) << 8;
            int var14 = (var2.field4797[-463641621 * var1.field4726 + 2] & 255) << 8;
            var12 += (var11 - var13) * (var2.field4797[3 + var1.field4726 * -217386509] - var12) / (var14 - var13);
         }

         var3 = var3 * var12 + -396636216 >> 6;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("ax")
   public static int method7684(int var0, int var1, int var2, int var3) {
      try {
         int var4 = (int)classOW.method8842(var2 - var1 + 1, -194270549);
         var4 <<= var1;
         return var0 | var4;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "mq.ax(" + 41);
      }
   }
}
