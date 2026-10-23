import java.util.Arrays;
import net.runelite.api.kit.KitType;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lc")
public class PlayerComposition implements net.runelite.api.PlayerComposition {
   @ObfuscatedName("aq")
   public int field4092;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4102 = 2048;
   @ObfuscatedName("ae")
   public static short[] field4106;
   @ObfuscatedName("ab")
   public static short[][] field4108;
   @ToRemove(unused = "true")
   @ObfuscatedName("bh")
   static final int field4103 = 145;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ax")
   static NodeCache field4098 = new NodeCache(260);
   @ObfuscatedSignature(descriptor = "Lrj;")
   @ObfuscatedName("ac")
   static classRJ field4101 = new classRJ(16, classRB.field5633);
   @ObfuscatedName("aa")
   static int field4099 = 0;
   @ObfuscatedName("ao")
   int[] field4090;
   @ObfuscatedName("ay")
   public int field4091 = 1540749783;
   @ObfuscatedName("aj")
   int[] field4096;
   @ObfuscatedName("ar")
   boolean field4094;
   @ObfuscatedName("ap")
   long field4104;
   @ObfuscatedName("ad")
   int field4097;
   @ObfuscatedName("al")
   int[] field4093;
   @ObfuscatedSignature(descriptor = "[Lpi;")
   @ObfuscatedName("ai")
   ColorTextureOverride[] field4095;
   @ObfuscatedName("ag")
   public static short[] field4107;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4100 = 256;
   @ObfuscatedName("au")
   long field4105;

   @ObfuscatedSignature(descriptor = "(Llc;III)V")
   @ObfuscatedName("xj")
   public static void method6759(PlayerComposition var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method6763(var1, var1, var1);
      }

      try {
         boolean var4 = var0.field4092 * 1705409403 != var2;
         var0.field4092 = var2 * -2056377421;
         if (var4) {
            if (var3 <= 702114061) {
               return;
            }

            if (var0.field4092 * 1705409403 == var1) {
               for (int var5 = 0; var5 < 7; var5++) {
                  if (var3 <= 702114061) {
                     throw new IllegalStateException();
                  }

                  int var6 = classON.method8644(var5, (byte)20);
                  if (var0.field4093[var6] > 0) {
                     if (var3 <= 702114061) {
                        return;
                     }

                     if (var0.field4093[var6] < 2048) {
                        if (var3 <= 702114061) {
                           throw new IllegalStateException();
                        }

                        var0.field4093[var6] = var0.field4090[var6];
                     }
                  }
               }
            } else {
               label76: {
                  if (var0.field4093[0] >= 2048) {
                     if (!var0.method6764(-1364702200)) {
                        break label76;
                     }

                     if (var3 <= 702114061) {
                        throw new IllegalStateException();
                     }
                  }

                  var0.field4093[2111598757 * classOV.field5164.field5152] = 1;
               }

               for (int var8 = 0; var8 < 7; var8++) {
                  if (var3 <= 702114061) {
                     throw new IllegalStateException();
                  }

                  int var9 = classON.method8644(var8, (byte)7);
                  if (var0.field4093[var9] > 0 && var0.field4093[var9] < 2048) {
                     classGB.method5333(var2, var0.field4093, var8, (byte)8);
                  }
               }
            }
         }

         var0.method6754((byte)-120);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lc.ad(" + ')');
      }
   }

   PlayerComposition(PlayerComposition var1) {
      this.field4092 = 0;
      this.field4094 = false;
      if (var1 != null) {
         int[] var2 = Arrays.copyOf(var1.field4093, var1.field4093.length);
         int[] var3 = Arrays.copyOf(var1.field4090, var1.field4090.length);
         ColorTextureOverride[] var4 = null != var1.field4095 ? Arrays.copyOf(var1.field4095, var1.field4095.length) : null;
         int[] var5 = Arrays.copyOf(var1.field4096, var1.field4096.length);
         this.method6719(var3, var2, var4, false, var5, var1.field4092 * 1705409403, 729560399 * var1.field4097, -513083879 * var1.field4091, -1165627242);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ca")
   public void method6768(int var1, int var2) {
      this.field4096[var1] = var2;
      this.method6754((byte)-4);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ar")
   boolean method6776(int var1, int var2) {
      try {
         if (var1 >= 256) {
            if (var2 <= -285373106) {
               throw new IllegalStateException();
            }

            if (var1 < 2048) {
               if (var2 <= -285373106) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lc.ar(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;[I[I[Lpi;Z[IIII)V")
   @ObfuscatedName("wh")
   public static void method6716(
      PlayerComposition var0, int[] var1, int[] var2, ColorTextureOverride[] var3, boolean var4, int[] var5, int var6, int var7, int var8
   ) {
      if (var0 == null) {
         var0.method6766();
      }

      var0.field4095 = var3;
      var0.field4094 = var4;
      var0.field4091 = var8 * -1540749783;
      var0.method6722(var1, var2, var5, var6, var7, -741931325);
   }

   @ObfuscatedSignature(descriptor = "([I[I[IIII)V")
   @ObfuscatedName("ab")
   public void method6722(int[] var1, int[] var2, int[] var3, int var4, int var5, int var6) {
      try {
         if (null == var1) {
            if (var6 != -741931325) {
               return;
            }

            var1 = method6727(this, var4, -613532315);
         }

         if (var2 == null) {
            if (var6 != -741931325) {
               throw new IllegalStateException();
            }

            var2 = method6727(this, var4, -613532315);
         }

         this.field4090 = var1;
         this.field4093 = var2;
         this.field4096 = var3;
         this.field4092 = var4 * -2056377421;
         this.field4097 = -739655249 * var5;
         method6742(this, -2063928533);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lc.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cn")
   public void method6771(int var1) {
      ItemComposition var2 = classOB.method8329(var1, (byte)6);
      this.field4093[var2.field5042 * 1481543581] = var1 + -895302807;
      if (-1 != var2.field5008 * -1928515087) {
         this.field4093[-1462570117 * var2.field5008] = 0;
      }

      if (-1 != var2.field5019 * -1681179861) {
         this.field4093[var2.field5019 * -1681179861] = 0;
      }

      this.method6754((byte)-39);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;I)V")
   @ObfuscatedName("oy")
   public static void method6772(PlayerComposition var0, int var1) {
      if (var0 == null) {
         var0.method6753();
      }

      ItemComposition var2 = classOB.method8329(var1, (byte)9);
      var0.field4093[var2.field5042 * 1481543581] = var1 + 2048;
      if (-1 != var2.field5008 * -1462570117) {
         var0.field4093[-1462570117 * var2.field5008] = 0;
      }

      if (-1 != var2.field5019 * -1681179861) {
         var0.field4093[var2.field5019 * -1681179861] = 0;
      }

      var0.method6754((byte)-93);
   }

   @ObfuscatedSignature(descriptor = "(IZB)V")
   @ObfuscatedName("ax")
   public void method6731(int var1, boolean var2, byte var3) {
      try {
         int var4 = this.field4096[var1];
         if (!var2) {
            while (true) {
               if (--var4 < 0) {
                  if (var3 == -1) {
                     throw new IllegalStateException();
                  }

                  var4 = field4108[var1].length - 1;
               }

               boolean var7;
               if (var1 == 4 && var4 >= 8) {
                  if (var3 == -1) {
                     return;
                  }

                  var7 = false;
               } else {
                  var7 = true;
               }

               if (var7) {
                  if (var3 == -1) {
                     throw new IllegalStateException();
                  }
                  break;
               }
            }
         } else {
            boolean var5;
            do {
               if (++var4 >= field4108[var1].length) {
                  if (var3 == -1) {
                     throw new IllegalStateException();
                  }

                  var4 = 0;
               }

               if (4 == var1 && var4 >= 8) {
                  if (var3 == -1) {
                     throw new IllegalStateException();
                  }

                  var5 = false;
               } else {
                  var5 = true;
               }
            } while (!var5);

            if (var3 == -1) {
               throw new IllegalStateException();
            }
         }

         this.field4096[var1] = var4;
         method6742(this, -2058558179);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lc.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ac")
   public void method6733(int var1, int var2) {
      try {
         if (var1 == 1705409403 * this.field4092) {
            if (var2 <= -1365782714) {
               throw new IllegalStateException();
            }
         } else {
            this.method6722(null, null, this.field4096, var1, -1, -741931325);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lc.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("aa")
   public void method6736(classXY var1, int var2) {
      try {
         classXY.method13029(var1, 1705409403 * this.field4092, (byte)-40);

         for (int var3 = 0; var3 < 7; var3++) {
            if (var2 <= -137008206) {
               return;
            }

            int var4 = this.field4093[classON.method8644(var3, (byte)70)];
            if (var4 == 0) {
               var1.method13031(-1, -1542382992);
            } else {
               var1.method13031(var4 - 256, -1542382992);
            }
         }

         for (int var6 = 0; var6 < 5; var6++) {
            if (var2 <= -137008206) {
               return;
            }

            classXY.method13029(var1, this.field4096[var6], (byte)-81);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lc.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lof;")
   @ObfuscatedName("cj")
   classOF method6779(int var1) {
      return classHW.method6163(var1 - -1012457609, -2047686091);
   }

   @ObfuscatedSignature(descriptor = "(Llc;B)Ler;")
   @ObfuscatedName("sa")
   public static ModelData method6746(PlayerComposition var0, byte var1) {
      if (var0 == null) {
         return var0.method6749(var1);
      } else {
         try {
            if (729560399 * var0.field4097 != -1) {
               if (var1 != 0) {
                  throw new IllegalStateException();
               } else {
                  return classOH.method8480(var0.field4097 * 729560399, -1861935222).method8987(null, (byte)61);
               }
            } else {
               boolean var2 = false;

               for (int var3 = 0; var3 < 12; var3++) {
                  if (var1 != 0) {
                     throw new IllegalStateException();
                  }

                  int var4 = var0.field4093[var3];
                  if (var0.method6776(var4, 1588657882)) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     classOF var5 = var0.method6780(var4, -1927903443);
                     if (null != var5) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        if (!var5.method8453(-1104719451)) {
                           if (var1 != 0) {
                              throw new IllegalStateException();
                           }

                           var2 = true;
                        }
                     }
                  }

                  if (method6783(var0, var4, 1604621880)) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     ColorTextureOverride var10000;
                     if (var0.field4095 == null) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        var10000 = null;
                     } else {
                        var10000 = var0.field4095[var3];
                     }

                     ColorTextureOverride var12 = var10000;
                     if (!var0.method6785(var4, (byte)0).method8714(1705409403 * var0.field4092, var12, -1724421765)) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        var2 = true;
                     }
                  }
               }

               if (!var2) {
                  ModelData[] var10 = new ModelData[12];
                  int var11 = 0;

                  for (int var13 = 0; var13 < 12; var13++) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     int var6 = var0.field4093[var13];
                     if (var0.method6776(var6, 1111921296)) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        classOF var7 = var0.method6780(var6, -1927903443);
                        ModelData var8 = null;
                        if (var7 != null) {
                           if (var1 != 0) {
                              throw new IllegalStateException();
                           }

                           var8 = var7.method8456(-528630105);
                        }

                        if (var8 != null) {
                           if (var1 != 0) {
                              throw new IllegalStateException();
                           }

                           var10[var11++] = var8;
                        }
                     }

                     if (method6783(var0, var6, 1289614546)) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        ColorTextureOverride var18;
                        if (var0.field4095 == null) {
                           if (var1 != 0) {
                              throw new IllegalStateException();
                           }

                           var18 = null;
                        } else {
                           var18 = var0.field4095[var13];
                        }

                        ColorTextureOverride var16 = var18;
                        ModelData var17 = var0.method6785(var6, (byte)0).method8716(1705409403 * var0.field4092, var16, -1218924652);
                        if (var17 != null) {
                           if (var1 != 0) {
                              throw new IllegalStateException();
                           }

                           var10[var11++] = var17;
                        }
                     }
                  }

                  ModelData var14 = new ModelData(var10, var11);

                  for (int var15 = 0; var15 < 5; var15++) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     if (var0.field4096[var15] < field4108[var15].length) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        var14.method4031(field4106[var15], field4108[var15][var0.field4096[var15]]);
                     }

                     if (var0.field4096[var15] < classEH.field1760[var15].length) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        var14.method4031(field4107[var15], classEH.field1760[var15][var0.field4096[var15]]);
                     }
                  }

                  return var14;
               } else if (var1 != 0) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            }
         } catch (RuntimeException var9) {
            throw classEG.method3899(var9, "lc.aj(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;)I")
   @ObfuscatedName("vu")
   public static int method6750(PlayerComposition var0) {
      long var1 = 4603856865473899623L * var0.field4104;
      if (var0.field4097 * 729560399 != -1) {
         var1 = -65536L | 729560399 * var0.field4097;
      }

      Integer var3 = (Integer)field4101.method10020(var1, (byte)64);
      if (null == var3) {
         var3 = (field4099 += 523117267) * -552072195 - 1;
         field4101.method10022(var1, var3, (byte)1);
         field4099 = 523117267 * (-1103723173 * field4099 % 65535);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ay")
   public int method6751(int var1) {
      try {
         long var2 = 4603856865473899623L * this.field4104;
         if (this.field4097 * 729560399 != -1) {
            if (var1 != 170077571) {
               throw new IllegalStateException();
            }

            var2 = -65536L | 729560399 * this.field4097;
         }

         Integer var4 = (Integer)field4101.method10020(var2, (byte)86);
         if (null == var4) {
            if (var1 != 170077571) {
               throw new IllegalStateException();
            }

            var4 = (field4099 += 523117267) * -1103723173 - 1;
            field4101.method10022(var2, var4, (byte)1);
            field4099 = 523117267 * (-1103723173 * field4099 % 65535);
         }

         return var4;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lc.ay(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("aq")
   void method6754(byte var1) {
      try {
         this.method6722(this.field4090, this.field4093, this.field4096, this.field4092 * 1705409403, this.field4097 * 729560399, -741931325);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lc.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lou;ILou;I)Lfx;")
   @ObfuscatedName("bd")
   public Model method6743(Animation var1, int var2, Animation var3, int var4) {
      if (729560399 * this.field4097 != -1) {
         return NPCComposition.method8985(classOH.method8480(-1841550095 * this.field4097, -836857354), var1, var2, var3, var4, null, -1745187945);
      } else {
         long var5 = this.field4104 * 4603856865473899623L;
         int[] var7 = this.field4093;
         if (null != var1 && (-885490691 * var1.field5137 >= 0 || -874614675 * var1.field5138 >= 0)) {
            var7 = new int[12];
            System.arraycopy(this.field4093, 0, var7, 0, var7.length);
            if (-885490691 * var1.field5137 >= 0) {
               var5 ^= (long)(-885490691 * var1.field5137 - this.field4093[2111598757 * classOV.field5158.field5152]) << -70733173;
               var7[2111598757 * classOV.field5158.field5152] = this.method6791(var1.field5137 * -331467183, -653463055);
            }

            if (var1.field5138 * 484651797 >= 0) {
               var5 ^= (long)(484651797 * var1.field5138 - this.field4093[-1606522744 * classOV.field5162.field5152]) << -274699349;
               var7[classOV.field5162.field5152 * -318613003] = this.method6791(725681904 * var1.field5138, -653463055);
            }
         }

         Model var8 = (Model)NodeCache.method6460(field4098, var5);
         if (var8 == null) {
            boolean var9 = false;

            for (int var10 = 0; var10 < 12; var10++) {
               int var11 = var7[var10];
               if (this.method6776(var11, 78282324)) {
                  classOF var12 = this.method6780(var11, -1927903443);
                  if (var12 != null && !classOF.method8449(var12, (byte)-22)) {
                     var9 = true;
                  }
               }

               if (method6783(this, var11, 1347949179)) {
                  ColorTextureOverride var20 = null == this.field4095 ? null : this.field4095[var10];
                  if (!ItemComposition.method8706(this.method6785(var11, (byte)0), this.field4092 * 1931442818, var20, (short)2425)) {
                     var9 = true;
                  }
               }
            }

            if (var9) {
               if (this.field4105 * 2990377911060063119L != -1L) {
                  var8 = (Model)NodeCache.method6460(field4098, 2990377911060063119L * this.field4105);
               }

               if (var8 == null) {
                  return null;
               }
            }

            if (null == var8) {
               ModelData[] var18 = new ModelData[12];
               int var19 = 0;

               for (int var21 = 0; var21 < 12; var21++) {
                  int var13 = var7[var21];
                  if (this.method6776(var13, -279891923)) {
                     classOF var14 = this.method6780(var13, -1927903443);
                     ModelData var15 = null;
                     if (var14 != null) {
                        var15 = var14.method8452(-597664344);
                     }

                     if (var15 != null) {
                        var18[var19++] = var15;
                     }
                  }

                  if (method6783(this, var13, 2033277357)) {
                     ItemComposition var24 = this.method6785(var13, (byte)0);
                     ColorTextureOverride var25 = null == this.field4095 ? null : this.field4095[var21];
                     ModelData var16 = var24.method8709(178328413 * this.field4092, var25, 1986500445);
                     if (null != var16) {
                        var18[var19++] = var16;
                     }
                  }
               }

               ModelData var22 = new ModelData(var18, var19);

               for (int var23 = 0; var23 < 5; var23++) {
                  if (this.field4096[var23] < field4108[var23].length) {
                     var22.method4031(field4106[var23], field4108[var23][this.field4096[var23]]);
                  }

                  if (this.field4096[var23] < classEH.field1760[var23].length) {
                     var22.method4031(field4107[var23], classEH.field1760[var23][this.field4096[var23]]);
                  }
               }

               var8 = var22.method4070(64, 445702533, -1429097299, 719957821, 442960209);
               field4098.method6464(var8, var5);
               this.field4105 = var5 * 1604562035348527983L;
            }
         }

         Model var17;
         if (var1 == null && var3 == null) {
            var17 = var8.method5106(true);
         } else if (null != var1 && null != var3) {
            var17 = var1.method8830(var8, var2, var3, var4, -275350468);
         } else if (null != var1) {
            var17 = var1.method8829(var8, var2, (byte)0);
         } else {
            var17 = var3.method8829(var8, var4, (byte)0);
         }

         return var17;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lof;")
   @ObfuscatedName("aw")
   classOF method6780(int var1, int var2) {
      try {
         return classHW.method6163(var1 - 256, -1003483374);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lc.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Lop;")
   @ObfuscatedName("av")
   ItemComposition method6785(int var1, byte var2) {
      try {
         return classOB.method8329(var1 - 2048, (byte)24);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lc.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("at")
   int method6791(int var1, int var2) {
      try {
         return var1 - 512 + 2048;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lc.at(" + 41);
      }
   }

   public void removeColorTextureOverride(KitType var1) {
      if (this.field4095 != null) {
         this.field4095[var1.getIndex()] = null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I[II)V")
   @ObfuscatedName("an")
   static void method6711(int var0, int[] var1, int var2) {
      for (int var3 = 0; var3 < classOA.method8319((byte)74); var3++) {
         classOF var4 = classHW.method6163(var3, -1610483605);
         if (null != var4 && !var4.field4849 && var4.method8444(var2, var0, -461388995)) {
            var1[classON.method8644(var2, (byte)23)] = 1862739419 + var3;
            break;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bi")
   public void method6734(int var1) {
      if (var1 != -1037064520 * this.field4092) {
         this.method6722(null, null, this.field4096, var1, -1, -741931325);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lou;ILou;IB)Lfx;")
   @ObfuscatedName("al")
   public Model method6744(Animation var1, int var2, Animation var3, int var4, byte var5) {
      try {
         if (729560399 * this.field4097 != -1) {
            return NPCComposition.method8985(classOH.method8480(729560399 * this.field4097, -2073626017), var1, var2, var3, var4, null, -1479569895);
         } else {
            long var6;
            int[] var8;
            var6 = this.field4104 * 4603856865473899623L;
            var8 = this.field4093;
            label270:
            if (null != var1) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               if (-885490691 * var1.field5137 < 0) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  if (484651797 * var1.field5138 < 0) {
                     break label270;
                  }
               }

               var8 = new int[12];
               System.arraycopy(this.field4093, 0, var8, 0, var8.length);
               if (-885490691 * var1.field5137 >= 0) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  var6 ^= (long)(-885490691 * var1.field5137 - this.field4093[2111598757 * classOV.field5158.field5152]) << 40;
                  var8[2111598757 * classOV.field5158.field5152] = this.method6791(var1.field5137 * -885490691, -653463055);
               }

               if (var1.field5138 * 484651797 >= 0) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  var6 ^= (long)(484651797 * var1.field5138 - this.field4093[2111598757 * classOV.field5162.field5152]) << 48;
                  var8[classOV.field5162.field5152 * 2111598757] = this.method6791(484651797 * var1.field5138, -653463055);
               }
            }

            Model var9 = (Model)NodeCache.method6460(field4098, var6);
            if (var9 == null) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               boolean var10 = false;

               for (int var11 = 0; var11 < 12; var11++) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  int var12 = var8[var11];
                  if (this.method6776(var12, 954594038)) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     classOF var13 = this.method6780(var12, -1927903443);
                     if (var13 != null) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        if (!classOF.method8449(var13, (byte)-11)) {
                           if (var5 != 1) {
                              throw new IllegalStateException();
                           }

                           var10 = true;
                        }
                     }
                  }

                  if (method6783(this, var12, 2025551705)) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     ColorTextureOverride var10000;
                     if (null == this.field4095) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        var10000 = null;
                     } else {
                        var10000 = this.field4095[var11];
                     }

                     ColorTextureOverride var22 = var10000;
                     if (!ItemComposition.method8706(this.method6785(var12, (byte)0), this.field4092 * 1705409403, var22, (short)8479)) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        var10 = true;
                     }
                  }
               }

               if (var10) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  if (this.field4105 * 2990377911060063119L != -1L) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     var9 = (Model)NodeCache.method6460(field4098, 2990377911060063119L * this.field4105);
                  }

                  if (var9 == null) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }
               }

               if (null == var9) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  ModelData[] var20 = new ModelData[12];
                  int var21 = 0;

                  for (int var23 = 0; var23 < 12; var23++) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     int var14 = var8[var23];
                     if (this.method6776(var14, 620306450)) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        classOF var15 = this.method6780(var14, -1927903443);
                        ModelData var16 = null;
                        if (var15 != null) {
                           if (var5 != 1) {
                              throw new IllegalStateException();
                           }

                           var16 = var15.method8452(2000779005);
                        }

                        if (var16 != null) {
                           if (var5 != 1) {
                              throw new IllegalStateException();
                           }

                           var20[var21++] = var16;
                        }
                     }

                     if (method6783(this, var14, 1643713934)) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        ItemComposition var26 = this.method6785(var14, (byte)0);
                        ColorTextureOverride var28;
                        if (null == this.field4095) {
                           if (var5 != 1) {
                              throw new IllegalStateException();
                           }

                           var28 = null;
                        } else {
                           var28 = this.field4095[var23];
                        }

                        ColorTextureOverride var27 = var28;
                        ModelData var17 = var26.method8709(1705409403 * this.field4092, var27, 1986500445);
                        if (null != var17) {
                           if (var5 != 1) {
                              throw new IllegalStateException();
                           }

                           var20[var21++] = var17;
                        }
                     }
                  }

                  ModelData var24 = new ModelData(var20, var21);

                  for (int var25 = 0; var25 < 5; var25++) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     if (this.field4096[var25] < field4108[var25].length) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        var24.method4031(field4106[var25], field4108[var25][this.field4096[var25]]);
                     }

                     if (this.field4096[var25] < classEH.field1760[var25].length) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        var24.method4031(field4107[var25], classEH.field1760[var25][this.field4096[var25]]);
                     }
                  }

                  var9 = var24.method4070(64, 850, -30, -50, -30);
                  field4098.method6464(var9, var6);
                  this.field4105 = var6 * 1604562035348527983L;
               }
            }

            if (var1 == null) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               if (var3 == null) {
                  Model var31 = var9.method5106(true);
                  return var31;
               }
            }

            if (null != var1) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               if (null != var3) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  Model var30 = var1.method8830(var9, var2, var3, var4, -1500497548);
                  return var30;
               }
            }

            Model var19;
            if (null != var1) {
               var19 = var1.method8829(var9, var2, (byte)0);
            } else {
               var19 = var3.method8829(var9, var4, (byte)0);
            }

            return var19;
         }
      } catch (RuntimeException var18) {
         throw classEG.method3899(var18, "lc.al(" + ')');
      }
   }

   public int getGender() {
      return this.field4092 * 1705409403;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   public static void method6712() {
      field4098.method6468();
      field4101.method10034((byte)55);
      field4099 = 0;
   }

   @ObfuscatedSignature(descriptor = "([I[I[Lpi;Z[IIII)V")
   @ObfuscatedName("bc")
   public void method6717(int[] var1, int[] var2, ColorTextureOverride[] var3, boolean var4, int[] var5, int var6, int var7, int var8) {
      this.field4095 = var3;
      this.field4094 = var4;
      this.field4091 = var8 * -1540749783;
      this.method6722(var1, var2, var5, var6, var7, -741931325);
   }

   public int[] getEquipmentIds() {
      return this.field4093;
   }

   @ObfuscatedSignature(descriptor = "([I[I[Lpi;Z[IIII)V")
   @ObfuscatedName("bp")
   public void method6718(int[] var1, int[] var2, ColorTextureOverride[] var3, boolean var4, int[] var5, int var6, int var7, int var8) {
      this.field4095 = var3;
      this.field4094 = var4;
      this.field4091 = var8 * -1540749783;
      this.method6722(var1, var2, var5, var6, var7, -741931325);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cl")
   public void method6760(int var1, int var2) {
      boolean var3 = this.field4092 * 1705409403 != var2;
      this.field4092 = var2 * -2056377421;
      if (var3) {
         if (this.field4092 * 1705409403 == var1) {
            for (int var4 = 0; var4 < 7; var4++) {
               int var5 = classON.method8644(var4, (byte)64);
               if (this.field4093[var5] > 0 && this.field4093[var5] < 2048) {
                  this.field4093[var5] = this.field4090[var5];
               }
            }
         } else {
            if (this.field4093[0] < 2048 || this.method6764(-298054372)) {
               this.field4093[2111598757 * classOV.field5164.field5152] = 1;
            }

            for (int var6 = 0; var6 < 7; var6++) {
               int var7 = classON.method8644(var6, (byte)51);
               if (this.field4093[var7] > 0 && this.field4093[var7] < 2048) {
                  classGB.method5333(var2, this.field4093, var6, (byte)8);
               }
            }
         }
      }

      this.method6754((byte)-16);
   }

   @ObfuscatedSignature(descriptor = "([I[I[III)V")
   @ObfuscatedName("bt")
   public void method6723(int[] var1, int[] var2, int[] var3, int var4, int var5) {
      if (null == var1) {
         var1 = method6727(this, var4, -613532315);
      }

      if (var2 == null) {
         var2 = method6727(this, var4, -613532315);
      }

      this.field4090 = var1;
      this.field4093 = var2;
      this.field4096 = var3;
      this.field4092 = var4 * -2056377421;
      this.field4097 = -739655249 * var5;
      method6742(this, -1882347187);
   }

   @ObfuscatedSignature(descriptor = "([I[I[III)V")
   @ObfuscatedName("bg")
   public void method6724(int[] var1, int[] var2, int[] var3, int var4, int var5) {
      if (null == var1) {
         var1 = method6727(this, var4, -613532315);
      }

      if (var2 == null) {
         var2 = method6727(this, var4, -613532315);
      }

      this.field4090 = var1;
      this.field4093 = var2;
      this.field4096 = var3;
      this.field4092 = var4 * -2056377421;
      this.field4097 = -739655249 * var5;
      method6742(this, -1949201221);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;)I")
   @ObfuscatedName("xw")
   public static int method6752(PlayerComposition var0) {
      long var1 = 4603856865473899623L * var0.field4104;
      if (var0.field4097 * 729560399 != -1) {
         var1 = -65536L | 949039467 * var0.field4097;
      }

      Integer var3 = (Integer)field4101.method10020(var1, (byte)-60);
      if (null == var3) {
         var3 = (field4099 += 703355159) * -2097455668 - 1;
         field4101.method10022(var1, var3, (byte)1);
         field4099 = 523117267 * (-1676262503 * field4099 % -1336300771);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "([I[I[III)V")
   @ObfuscatedName("bj")
   public void method6725(int[] var1, int[] var2, int[] var3, int var4, int var5) {
      if (null == var1) {
         var1 = method6727(this, var4, -613532315);
      }

      if (var2 == null) {
         var2 = method6727(this, var4, -613532315);
      }

      this.field4090 = var1;
      this.field4093 = var2;
      this.field4096 = var3;
      this.field4092 = var4 * -2056377421;
      this.field4097 = -739655249 * var5;
      method6742(this, -1891520813);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;IZ)V")
   @ObfuscatedName("fl")
   public static void method6729(PlayerComposition var0, int var1, boolean var2) {
      int var3 = var0.field4093[classON.method8644(var1, (byte)83)];
      if (var3 != 0) {
         var3 -= 256;

         classOF var4;
         do {
            if (!var2) {
               if (--var3 < 0) {
                  var3 = classOA.method8319((byte)49) - 1;
               }
            } else if (++var3 >= classOA.method8319((byte)37)) {
               var3 = 0;
            }

            var4 = classHW.method6163(var3, -674263494);
         } while (null == var4 || var4.field4849 || !var4.method8444(var1, var0.field4092 * 1705409403, -540002980));

         var0.field4093[classON.method8644(var1, (byte)66)] = 256 + var3;
         method6742(var0, -1915979469);
      }
   }

   @ObfuscatedSignature(descriptor = "(IZS)V")
   @ObfuscatedName("as")
   public void method6730(int var1, boolean var2, short var3) {
      try {
         int var4 = this.field4093[classON.method8644(var1, (byte)31)];
         if (var4 == 0) {
            if (var3 == 5125) {
               throw new IllegalStateException();
            }
         } else {
            var4 -= 256;

            while (true) {
               if (!var2) {
                  if (var3 == 5125) {
                     throw new IllegalStateException();
                  }

                  if (--var4 < 0) {
                     if (var3 == 5125) {
                        return;
                     }

                     var4 = classOA.method8319((byte)124) - 1;
                  }
               } else if (++var4 >= classOA.method8319((byte)121)) {
                  if (var3 == 5125) {
                     throw new IllegalStateException();
                  }

                  var4 = 0;
               }

               classOF var5 = classHW.method6163(var4, -1778233858);
               if (null != var5) {
                  if (var3 == 5125) {
                     throw new IllegalStateException();
                  }

                  if (!var5.field4849) {
                     if (var3 == 5125) {
                        return;
                     }

                     if (var5.method8444(var1, this.field4092 * 1705409403, -869632549)) {
                        if (var3 == 5125) {
                           throw new IllegalStateException();
                        }

                        this.field4093[classON.method8644(var1, (byte)125)] = 256 + var4;
                        method6742(this, -1863993132);
                        return;
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lc.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("bu")
   int[] method6726(int var1) {
      int[] var2 = new int[12];

      for (int var3 = 0; var3 < 7; var3++) {
         classGB.method5333(var1, var2, var3, (byte)8);
      }

      return var2;
   }

   public net.runelite.api.ColorTextureOverride createColorTextureOverride(KitType var1, int var2) {
      ColorTextureOverride var3 = new ColorTextureOverride(var2);
      if (this.field4095 == null) {
         this.field4095 = new ColorTextureOverride[12];
      }

      this.field4095[var1.getIndex()] = var3;
      return var3;
   }

   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("dc")
   ItemComposition method6786(int var1) {
      return classOB.method8329(var1 - 2048, (byte)-20);
   }

   public int getEquipmentId(KitType var1) {
      int var2 = this.getEquipmentIds()[var1.getIndex()];
      return var2 < 2048 ? -1 : var2 - 2048;
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("bw")
   public void method6732(int var1, boolean var2) {
      int var3 = this.field4096[var1];
      boolean var4;
      if (!var2) {
         do {
            if (--var3 < 0) {
               var3 = field4108[var1].length - 1;
            }

            if (var1 == 4 && var3 >= 8) {
               var4 = false;
            } else {
               var4 = true;
            }
         } while (!var4);
      } else {
         do {
            if (++var3 >= field4108[var1].length) {
               var3 = 0;
            }

            if (4 == var1 && var3 >= 8) {
               var4 = false;
            } else {
               var4 = true;
            }
         } while (!var4);
      }

      this.field4096[var1] = var3;
      method6742(this, -1800863170);
   }

   public net.runelite.api.ColorTextureOverride getColorTextureOverride(KitType var1) {
      return this.field4095 != null ? this.field4095[var1.getIndex()] : null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bb")
   public void method6735(int var1) {
      if (var1 != 953957193 * this.field4092) {
         this.method6722(null, null, this.field4096, var1, -1, -741931325);
      }
   }

   public int getTransformedNpcId() {
      return this.field4097 * 729560399;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cp")
   void method6755() {
      this.method6722(this.field4090, this.field4093, this.field4096, this.field4092 * 1705409403, this.field4097 * 729560399, -741931325);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("be")
   public void method6737(classXY var1) {
      classXY.method13029(var1, 1705409403 * this.field4092, (byte)-69);

      for (int var2 = 0; var2 < 7; var2++) {
         int var3 = this.field4093[classON.method8644(var2, (byte)116)];
         if (var3 == 0) {
            var1.method13031(-1, -1542382992);
         } else {
            var1.method13031(var3 - 256, -1542382992);
         }
      }

      for (int var4 = 0; var4 < 5; var4++) {
         classXY.method13029(var1, this.field4096[var4], (byte)-62);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("by")
   public void method6738(classXY var1) {
      classXY.method13029(var1, 1705409403 * this.field4092, (byte)-9);

      for (int var2 = 0; var2 < 7; var2++) {
         int var3 = this.field4093[classON.method8644(var2, (byte)110)];
         if (var3 == 0) {
            var1.method13031(-1, -1542382992);
         } else {
            var1.method13031(var3 - 256, -1542382992);
         }
      }

      for (int var4 = 0; var4 < 5; var4++) {
         classXY.method13029(var1, this.field4096[var4], (byte)-97);
      }
   }

   public void setHash() {
      method6742(this, -2063928533);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   void method6739() {
      long var1 = 4603856865473899623L * this.field4104;
      long[] var3 = classXY.field6954;
      this.field4104 = -5187355721230823255L;

      for (int var4 = 0; var4 < 12; var4++) {
         this.field4104 = 5187355721230823255L
            * (4603856865473899623L * this.field4104 >>> 8 ^ var3[(int)((4603856865473899623L * this.field4104 ^ this.field4093[var4] >> 24) & 255L)]);
         this.field4104 = 5187355721230823255L
            * (4603856865473899623L * this.field4104 >>> 8 ^ var3[(int)((4603856865473899623L * this.field4104 ^ this.field4093[var4] >> 16) & 255L)]);
         this.field4104 = 5187355721230823255L
            * (4603856865473899623L * this.field4104 >>> 8 ^ var3[(int)((this.field4104 * 4603856865473899623L ^ this.field4093[var4] >> 8) & 255L)]);
         this.field4104 = 5187355721230823255L
            * (this.field4104 * 4603856865473899623L >>> 8 ^ var3[(int)((this.field4104 * 4603856865473899623L ^ this.field4093[var4]) & 255L)]);
      }

      if (this.field4095 != null) {
         for (int var6 = 0; var6 < this.field4095.length; var6++) {
            if (this.field4095[var6] != null) {
               if (this.field4095[var6].field5259 != null) {
                  for (int var5 = 0; var5 < this.field4095[var6].field5259.length; var5++) {
                     this.field4104 = (
                           this.field4104 * 4603856865473899623L >>> 8
                              ^ var3[(int)((this.field4104 * 4603856865473899623L ^ this.field4095[var6].field5259[var5] >> 8) & 255L)]
                        )
                        * 5187355721230823255L;
                     this.field4104 = 5187355721230823255L
                        * (
                           this.field4104 * 4603856865473899623L >>> 8
                              ^ var3[(int)((this.field4104 * 4603856865473899623L ^ this.field4095[var6].field5259[var5]) & 255L)]
                        );
                  }
               }

               if (null != this.field4095[var6].field5258) {
                  for (int var8 = 0; var8 < this.field4095[var6].field5258.length; var8++) {
                     this.field4104 = (
                           this.field4104 * 4603856865473899623L >>> 8
                              ^ var3[(int)((4603856865473899623L * this.field4104 ^ this.field4095[var6].field5258[var8] >> 8) & 255L)]
                        )
                        * 5187355721230823255L;
                     this.field4104 = (
                           4603856865473899623L * this.field4104 >>> 8
                              ^ var3[(int)((this.field4104 * 4603856865473899623L ^ this.field4095[var6].field5258[var8]) & 255L)]
                        )
                        * 5187355721230823255L;
                  }
               }
            }
         }
      }

      for (int var7 = 0; var7 < 5; var7++) {
         this.field4104 = 5187355721230823255L
            * (4603856865473899623L * this.field4104 >>> 8 ^ var3[(int)((this.field4104 * 4603856865473899623L ^ this.field4096[var7]) & 255L)]);
      }

      this.field4104 = 5187355721230823255L
         * (this.field4104 * 4603856865473899623L >>> 8 ^ var3[(int)((4603856865473899623L * this.field4104 ^ this.field4092 * 1705409403) & 255L)]);
      if (0L != var1 && var1 != this.field4104 * 4603856865473899623L || this.field4094) {
         field4098.method6461(var1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   public static void method6713() {
      field4098.method6468();
      field4101.method10034((byte)-4);
      field4099 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Lou;ILou;I)Lfx;")
   @ObfuscatedName("bl")
   public Model method6745(Animation var1, int var2, Animation var3, int var4) {
      if (-638706963 * this.field4097 != -1) {
         return NPCComposition.method8985(classOH.method8480(729560399 * this.field4097, -709509739), var1, var2, var3, var4, null, -863432020);
      } else {
         long var5 = this.field4104 * 4603856865473899623L;
         int[] var7 = this.field4093;
         if (null != var1 && (-885490691 * var1.field5137 >= 0 || -1904462584 * var1.field5138 >= 0)) {
            var7 = new int[12];
            System.arraycopy(this.field4093, 0, var7, 0, var7.length);
            if (-885490691 * var1.field5137 >= 0) {
               var5 ^= (long)(-885490691 * var1.field5137 - this.field4093[-496542408 * classOV.field5158.field5152]) << 40;
               var7[2111598757 * classOV.field5158.field5152] = this.method6791(var1.field5137 * -885490691, -653463055);
            }

            if (var1.field5138 * 484651797 >= 0) {
               var5 ^= (long)(484651797 * var1.field5138 - this.field4093[1172071680 * classOV.field5162.field5152]) << -2042940738;
               var7[classOV.field5162.field5152 * 2111598757] = this.method6791(484651797 * var1.field5138, -653463055);
            }
         }

         Model var8 = (Model)NodeCache.method6460(field4098, var5);
         if (var8 == null) {
            boolean var9 = false;

            for (int var10 = 0; var10 < 12; var10++) {
               int var11 = var7[var10];
               if (this.method6776(var11, -79847893)) {
                  classOF var12 = this.method6780(var11, -1927903443);
                  if (var12 != null && !classOF.method8449(var12, (byte)-126)) {
                     var9 = true;
                  }
               }

               if (method6783(this, var11, 1722141783)) {
                  ColorTextureOverride var20 = null == this.field4095 ? null : this.field4095[var10];
                  if (!ItemComposition.method8706(this.method6785(var11, (byte)0), this.field4092 * 1705409403, var20, (short)5783)) {
                     var9 = true;
                  }
               }
            }

            if (var9) {
               if (this.field4105 * 2990377911060063119L != -1L) {
                  var8 = (Model)NodeCache.method6460(field4098, 2990377911060063119L * this.field4105);
               }

               if (var8 == null) {
                  return null;
               }
            }

            if (null == var8) {
               ModelData[] var18 = new ModelData[12];
               int var19 = 0;

               for (int var21 = 0; var21 < 12; var21++) {
                  int var13 = var7[var21];
                  if (this.method6776(var13, 1917139111)) {
                     classOF var14 = this.method6780(var13, -1927903443);
                     ModelData var15 = null;
                     if (var14 != null) {
                        var15 = var14.method8452(-1470068746);
                     }

                     if (var15 != null) {
                        var18[var19++] = var15;
                     }
                  }

                  if (method6783(this, var13, 1769043077)) {
                     ItemComposition var24 = this.method6785(var13, (byte)0);
                     ColorTextureOverride var25 = null == this.field4095 ? null : this.field4095[var21];
                     ModelData var16 = var24.method8709(546017664 * this.field4092, var25, 1986500445);
                     if (null != var16) {
                        var18[var19++] = var16;
                     }
                  }
               }

               ModelData var22 = new ModelData(var18, var19);

               for (int var23 = 0; var23 < 5; var23++) {
                  if (this.field4096[var23] < field4108[var23].length) {
                     var22.method4031(field4106[var23], field4108[var23][this.field4096[var23]]);
                  }

                  if (this.field4096[var23] < classEH.field1760[var23].length) {
                     var22.method4031(field4107[var23], classEH.field1760[var23][this.field4096[var23]]);
                  }
               }

               var8 = var22.method4070(64, 2037323978, -30, -1866285890, -2108633733);
               field4098.method6464(var8, var5);
               this.field4105 = var5 * 1604562035348527983L;
            }
         }

         Model var17;
         if (var1 == null && var3 == null) {
            var17 = var8.method5106(true);
         } else if (null != var1 && null != var3) {
            var17 = var1.method8830(var8, var2, var3, var4, -1663471158);
         } else if (null != var1) {
            var17 = var1.method8829(var8, var2, (byte)0);
         } else {
            var17 = var3.method8829(var8, var4, (byte)0);
         }

         return var17;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("bv")
   ModelData method6747() {
      if (729560399 * this.field4097 != -1) {
         return classOH.method8480(this.field4097 * 729560399, 961611086).method8987(null, (byte)111);
      } else {
         boolean var1 = false;

         for (int var2 = 0; var2 < 12; var2++) {
            int var3 = this.field4093[var2];
            if (this.method6776(var3, 559660560)) {
               classOF var4 = this.method6780(var3, -1927903443);
               if (null != var4 && !var4.method8453(-2013053923)) {
                  var1 = true;
               }
            }

            if (method6783(this, var3, 1515726625)) {
               ColorTextureOverride var10 = this.field4095 == null ? null : this.field4095[var2];
               if (!this.method6785(var3, (byte)0).method8714(1705409403 * this.field4092, var10, -1494620860)) {
                  var1 = true;
               }
            }
         }

         if (var1) {
            return null;
         } else {
            ModelData[] var8 = new ModelData[12];
            int var9 = 0;

            for (int var11 = 0; var11 < 12; var11++) {
               int var5 = this.field4093[var11];
               if (this.method6776(var5, 645907320)) {
                  classOF var6 = this.method6780(var5, -1927903443);
                  ModelData var7 = null;
                  if (var6 != null) {
                     var7 = var6.method8456(1486198657);
                  }

                  if (var7 != null) {
                     var8[var9++] = var7;
                  }
               }

               if (method6783(this, var5, 1340149426)) {
                  ColorTextureOverride var14 = this.field4095 == null ? null : this.field4095[var11];
                  ModelData var15 = this.method6785(var5, (byte)0).method8716(1705409403 * this.field4092, var14, -1218924652);
                  if (var15 != null) {
                     var8[var9++] = var15;
                  }
               }
            }

            ModelData var12 = new ModelData(var8, var9);

            for (int var13 = 0; var13 < 5; var13++) {
               if (this.field4096[var13] < field4108[var13].length) {
                  var12.method4031(field4106[var13], field4108[var13][this.field4096[var13]]);
               }

               if (this.field4096[var13] < classEH.field1760[var13].length) {
                  var12.method4031(field4107[var13], classEH.field1760[var13][this.field4096[var13]]);
               }
            }

            return var12;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("bh")
   ModelData method6748() {
      if (944852237 * this.field4097 != -1) {
         return classOH.method8480(this.field4097 * 729560399, 797631757).method8987(null, (byte)38);
      } else {
         boolean var1 = false;

         for (int var2 = 0; var2 < 12; var2++) {
            int var3 = this.field4093[var2];
            if (this.method6776(var3, -11239959)) {
               classOF var4 = this.method6780(var3, -1927903443);
               if (null != var4 && !var4.method8453(-1699579423)) {
                  var1 = true;
               }
            }

            if (method6783(this, var3, 1937951953)) {
               ColorTextureOverride var10 = this.field4095 == null ? null : this.field4095[var2];
               if (!this.method6785(var3, (byte)0).method8714(1705409403 * this.field4092, var10, 143265631)) {
                  var1 = true;
               }
            }
         }

         if (var1) {
            return null;
         } else {
            ModelData[] var8 = new ModelData[12];
            int var9 = 0;

            for (int var11 = 0; var11 < 12; var11++) {
               int var5 = this.field4093[var11];
               if (this.method6776(var5, 575043643)) {
                  classOF var6 = this.method6780(var5, -1927903443);
                  ModelData var7 = null;
                  if (var6 != null) {
                     var7 = var6.method8456(-310347414);
                  }

                  if (var7 != null) {
                     var8[var9++] = var7;
                  }
               }

               if (method6783(this, var5, 1633448407)) {
                  ColorTextureOverride var14 = this.field4095 == null ? null : this.field4095[var11];
                  ModelData var15 = this.method6785(var5, (byte)0).method8716(224650426 * this.field4092, var14, -1218924652);
                  if (var15 != null) {
                     var8[var9++] = var15;
                  }
               }
            }

            ModelData var12 = new ModelData(var8, var9);

            for (int var13 = 0; var13 < 5; var13++) {
               if (this.field4096[var13] < field4108[var13].length) {
                  var12.method4031(field4106[var13], field4108[var13][this.field4096[var13]]);
               }

               if (this.field4096[var13] < classEH.field1760[var13].length) {
                  var12.method4031(field4107[var13], classEH.field1760[var13][this.field4096[var13]]);
               }
            }

            return var12;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;I)Lop;")
   @ObfuscatedName("ms")
   public static ItemComposition method6787(PlayerComposition var0, int var1) {
      if (var0 == null) {
         var0.method6764(var1);
      }

      return classOB.method8329(var1 - 2048, (byte)-17);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bz")
   public int method6753() {
      long var1 = 4603856865473899623L * this.field4104;
      if (this.field4097 * 729560399 != -1) {
         var1 = -65536L | 729560399 * this.field4097;
      }

      Integer var3 = (Integer)field4101.method10020(var1, (byte)-58);
      if (null == var3) {
         var3 = (field4099 += 523117267) * -1103723173 - 1;
         field4101.method10022(var1, var3, (byte)1);
         field4099 = 523117267 * (-1103723173 * field4099 % 65535);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(Llc;II)Z")
   @ObfuscatedName("ll")
   public static boolean method6783(PlayerComposition var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method6784(var1, var1);
      } else {
         try {
            return var1 >= 2048;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "lc.ak(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ai")
   public void method6773(int var1, int var2) {
      try {
         ItemComposition var3 = classOB.method8329(var1, (byte)69);
         this.field4093[var3.field5042 * 1481543581] = var1 + 2048;
         if (-1 != var3.field5008 * -1462570117) {
            if (var2 <= 16711680) {
               throw new IllegalStateException();
            }

            this.field4093[-1462570117 * var3.field5008] = 0;
         }

         if (-1 != var3.field5019 * -1681179861) {
            if (var2 <= 16711680) {
               throw new IllegalStateException();
            }

            this.field4093[var3.field5019 * -1681179861] = 0;
         }

         this.method6754((byte)-4);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lc.ai(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;)V")
   @ObfuscatedName("by")
   public static void method6740(PlayerComposition var0) {
      long var1 = 4603856865473899623L * var0.field4104;
      long[] var3 = classXY.field6954;
      var0.field4104 = -5187355721230823255L;

      for (int var4 = 0; var4 < 12; var4++) {
         var0.field4104 = 5187355721230823255L
            * (4603856865473899623L * var0.field4104 >>> 8 ^ var3[(int)((4603856865473899623L * var0.field4104 ^ var0.field4093[var4] >> 24) & 255L)]);
         var0.field4104 = 5187355721230823255L
            * (4603856865473899623L * var0.field4104 >>> 8 ^ var3[(int)((4603856865473899623L * var0.field4104 ^ var0.field4093[var4] >> 16) & 255L)]);
         var0.field4104 = 5187355721230823255L
            * (4603856865473899623L * var0.field4104 >>> 8 ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4093[var4] >> 8) & 255L)]);
         var0.field4104 = 5187355721230823255L
            * (var0.field4104 * 4603856865473899623L >>> 8 ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4093[var4]) & 255L)]);
      }

      if (var0.field4095 != null) {
         for (int var6 = 0; var6 < var0.field4095.length; var6++) {
            if (var0.field4095[var6] != null) {
               if (var0.field4095[var6].field5259 != null) {
                  for (int var5 = 0; var5 < var0.field4095[var6].field5259.length; var5++) {
                     var0.field4104 = (
                           var0.field4104 * 4603856865473899623L >>> 8
                              ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4095[var6].field5259[var5] >> 8) & 255L)]
                        )
                        * 5187355721230823255L;
                     var0.field4104 = 5187355721230823255L
                        * (
                           var0.field4104 * 4603856865473899623L >>> 8
                              ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4095[var6].field5259[var5]) & 255L)]
                        );
                  }
               }

               if (null != var0.field4095[var6].field5258) {
                  for (int var8 = 0; var8 < var0.field4095[var6].field5258.length; var8++) {
                     var0.field4104 = (
                           var0.field4104 * 4603856865473899623L >>> 8
                              ^ var3[(int)((4603856865473899623L * var0.field4104 ^ var0.field4095[var6].field5258[var8] >> 8) & 255L)]
                        )
                        * 5187355721230823255L;
                     var0.field4104 = (
                           4603856865473899623L * var0.field4104 >>> 8
                              ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4095[var6].field5258[var8]) & 255L)]
                        )
                        * 5187355721230823255L;
                  }
               }
            }
         }
      }

      for (int var7 = 0; var7 < 5; var7++) {
         var0.field4104 = 5187355721230823255L
            * (4603856865473899623L * var0.field4104 >>> 8 ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4096[var7]) & 255L)]);
      }

      var0.field4104 = 5187355721230823255L
         * (var0.field4104 * 4603856865473899623L >>> 8 ^ var3[(int)((4603856865473899623L * var0.field4104 ^ var0.field4092 * 2038309953) & 255L)]);
      if (0L != var1 && var1 != var0.field4104 * 4603856865473899623L || var0.field4094) {
         field4098.method6461(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cb")
   void method6756() {
      this.method6722(this.field4090, this.field4093, this.field4096, this.field4092 * 1705409403, this.field4097 * 729560399, -741931325);
   }

   public net.runelite.api.ColorTextureOverride[] getColorTextureOverrides() {
      return this.field4095;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ct")
   void method6757() {
      this.method6722(this.field4090, this.field4093, this.field4096, this.field4092 * 1705409403, this.field4097 * 729560399, -741931325);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("am")
   public static int method6793(classXY var0) {
      if (var0 == null) {
         return var0.method13162();
      } else {
         var0.field6953 += 2103253898;
         int var1 = ((var0.field6952[-245403722 * var0.field6953 - 1] & -640618010) << 8) + (var0.field6952[1254311905 * var0.field6953 - 2] & 255);
         if (var1 > 32767) {
            var1 -= 407778780;
         }

         return var1;
      }
   }

   public void setTransformedNpcId(int var1) {
      this.field4097 = var1 * -739655249;
   }

   public PlayerComposition() {
      this.field4092 = 0;
      this.field4094 = false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cq")
   void method6758() {
      this.method6722(this.field4090, this.field4093, this.field4096, this.field4092 * 1705409403, this.field4097 * 729560399, -741931325);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cv")
   public void method6761(int var1, int var2) {
      boolean var3 = this.field4092 * 1705409403 != var2;
      this.field4092 = var2 * -2056377421;
      if (var3) {
         if (this.field4092 * -1173949456 == var1) {
            for (int var4 = 0; var4 < 7; var4++) {
               int var5 = classON.method8644(var4, (byte)109);
               if (this.field4093[var5] > 0 && this.field4093[var5] < -1586103662) {
                  this.field4093[var5] = this.field4090[var5];
               }
            }
         } else {
            if (this.field4093[0] < 593511480 || this.method6764(-1772013193)) {
               this.field4093[-2132852362 * classOV.field5164.field5152] = 1;
            }

            for (int var6 = 0; var6 < 7; var6++) {
               int var7 = classON.method8644(var6, (byte)90);
               if (this.field4093[var7] > 0 && this.field4093[var7] < -65455964) {
                  classGB.method5333(var2, this.field4093, var6, (byte)8);
               }
            }
         }
      }

      this.method6754((byte)-118);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;I)I")
   @ObfuscatedName("rg")
   public static int method6792(PlayerComposition var0, int var1) {
      if (var0 == null) {
         var0.method6762(var1, var1, var1);
      }

      return var1 - 512 + 1343688928;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ap")
   boolean method6764(int var1) {
      try {
         if (method6783(this, this.field4093[0], 1563080477)) {
            if (var1 >= 128386535) {
               throw new IllegalStateException();
            } else {
               ItemComposition var2 = this.method6785(this.field4093[0], (byte)0);
               if (-1462570117 * var2.field5008 != classOV.field5164.field5152 * 2111598757) {
                  if (var1 >= 128386535) {
                     throw new IllegalStateException();
                  }

                  if (2111598757 * classOV.field5164.field5152 != var2.field5019 * -1681179861) {
                     if (var1 >= 128386535) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }

               return false;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lc.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cy")
   boolean method6765() {
      if (!method6783(this, this.field4093[0], 2112935319)) {
         return false;
      } else {
         ItemComposition var1 = this.method6785(this.field4093[0], (byte)0);
         return -1462570117 * var1.field5008 != classOV.field5164.field5152 * 2111598757
            && 2111598757 * classOV.field5164.field5152 != var1.field5019 * -1681179861;
      }
   }

   public int[] getColors() {
      return this.field4096;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("co")
   boolean method6766() {
      if (!method6783(this, this.field4093[0], 1456336828)) {
         return false;
      } else {
         ItemComposition var1 = this.method6785(this.field4093[0], (byte)0);
         return -531614822 * var1.field5008 != classOV.field5164.field5152 * 419616825
            && 2111598757 * classOV.field5164.field5152 != var1.field5019 * 833054836;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ck")
   public void method6769(int var1, int var2) {
      this.field4096[var1] = var2;
      this.method6754((byte)-102);
   }

   @ObfuscatedSignature(descriptor = "([I[I[Lpi;Z[IIIII)V")
   @ObfuscatedName("ae")
   public void method6719(int[] var1, int[] var2, ColorTextureOverride[] var3, boolean var4, int[] var5, int var6, int var7, int var8, int var9) {
      try {
         this.field4095 = var3;
         this.field4094 = var4;
         this.field4091 = var8 * -1540749783;
         this.method6722(var1, var2, var5, var6, var7, -741931325);
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "lc.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cc")
   public void method6774(int var1) {
      ItemComposition var2 = classOB.method8329(var1, (byte)-127);
      this.field4093[var2.field5042 * 1481543581] = var1 + 2048;
      if (-1 != var2.field5008 * -1462570117) {
         this.field4093[-1462570117 * var2.field5008] = 0;
      }

      if (-1 != var2.field5019 * -1681179861) {
         this.field4093[var2.field5019 * -1681179861] = 0;
      }

      this.method6754((byte)-82);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("cz")
   boolean method6777(int var1) {
      return var1 >= 256 && var1 < 2048;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bx")
   public static void method6714() {
      field4098.method6468();
      field4101.method10034((byte)43);
      field4099 = 0;
   }

   @ObfuscatedSignature(descriptor = "([I[I[Lpi;Z[IIII)V")
   @ObfuscatedName("br")
   public void method6720(int[] var1, int[] var2, ColorTextureOverride[] var3, boolean var4, int[] var5, int var6, int var7, int var8) {
      this.field4095 = var3;
      this.field4094 = var4;
      this.field4091 = var8 * -1540749783;
      this.method6722(var1, var2, var5, var6, var7, -741931325);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("cg")
   boolean method6778(int var1) {
      return var1 >= 256 && var1 < -341569567;
   }

   @ObfuscatedSignature(descriptor = "(Llc;III)V")
   @ObfuscatedName("vq")
   public static void method6770(PlayerComposition var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method6762(var1, var1, var1);
      } else {
         try {
            var0.field4096[var1] = var2;
            var0.method6754((byte)-44);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "lc.au(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   public static void method6715() {
      field4098.method6468();
      field4101.method10034((byte)-33);
      field4099 = 0;
   }

   @ObfuscatedSignature(descriptor = "(I)Lof;")
   @ObfuscatedName("ci")
   classOF method6781(int var1) {
      return classHW.method6163(var1 - 256, -1526982709);
   }

   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("cx")
   ItemComposition method6788(int var1) {
      return classOB.method8329(var1 - 2048, (byte)-33);
   }

   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("ce")
   ItemComposition method6789(int var1) {
      return classOB.method8329(var1 - 493624874, (byte)-71);
   }

   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("cm")
   ItemComposition method6790(int var1) {
      return classOB.method8329(var1 - 2048, (byte)-43);
   }

   public boolean isFemale() {
      return this.getGender() == 1;
   }

   @ObfuscatedSignature(descriptor = "(Llc;II)[I")
   @ObfuscatedName("hd")
   public static int[] method6727(PlayerComposition var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method6728(var1, var1);
      } else {
         try {
            int[] var3 = new int[12];

            for (int var4 = 0; var4 < 7; var4++) {
               if (var2 != -613532315) {
                  throw new IllegalStateException();
               }

               classGB.method5333(var1, var3, var4, (byte)8);
            }

            return var3;
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "lc.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cf")
   public void method6775(int var1) {
      ItemComposition var2 = classOB.method8329(var1, (byte)70);
      this.field4093[var2.field5042 * 1481543581] = var1 + 2048;
      if (-1 != var2.field5008 * -1462570117) {
         this.field4093[-1462570117 * var2.field5008] = 0;
      }

      if (-1 != var2.field5019 * -1681179861) {
         this.field4093[var2.field5019 * -1681179861] = 0;
      }

      this.method6754((byte)-102);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cs")
   boolean method6767() {
      if (!method6783(this, this.field4093[0], 2021752832)) {
         return false;
      } else {
         ItemComposition var1 = this.method6785(this.field4093[0], (byte)0);
         return -1462570117 * var1.field5008 != classOV.field5164.field5152 * -454852785
            && -314088753 * classOV.field5164.field5152 != var1.field5019 * -1681179861;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;Lvh;I)Luq;")
   @ObfuscatedName("ab")
   public static classUQ method6721(classGY var0, classVH var1, int var2) {
      try {
         int var3 = -1311898939 * var1.field6644;
         int var4 = var1.field6643 * -1680480153;
         int var5 = 1905552099 * var1.field6648;
         int var6 = 455672321 * var1.field6645;
         int var7 = classGY.method5947(var0, 1827496185);
         int var8 = classGY.method5930(var0, 1199841383);
         int var9 = var0.method5936(1690676705);
         double var10 = classGP.method5582(var7, 1380982973) / 65536.0;
         double var14 = classWW.method12700(var7, 1271892013);
         int var16 = (int)(var10 * var3 - var4 * var14);
         int var17 = (int)(var4 * var10 + var3 * var14);
         var8 += var16;
         var9 += var17;
         int var18 = (int)(var10 * var5 - var14 * var6);
         int var19 = (int)(var14 * var5 + var10 * var6);
         int var20 = (int)(var10 * var5 + var6 * var14);
         int var21 = (int)(var6 * var10 - var14 * var5);
         int var22 = var8 - var18;
         int var23 = var9 + var19;
         int var24 = var8 + var20;
         int var25 = var21 + var9;
         int var26 = var8 + var18;
         int var27 = var9 - var19;
         int var28 = var8 - var20;
         int var29 = var9 - var21;
         int var30 = Math.min(var22, Math.min(var24, Math.min(var26, var28)));
         int var31 = Math.max(var22, Math.max(var24, Math.max(var26, var28)));
         int var32 = Math.min(var23, Math.min(var25, Math.min(var27, var29)));
         int var33 = Math.max(var23, Math.max(var25, Math.max(var27, var29)));
         return classPW.method9374(var30, var32, var31 - var30 + 1, 1 + (var33 - var32), 1444081395);
      } catch (RuntimeException var34) {
         throw classEG.method3899(var34, "lc.ab(" + ')');
      }
   }

   public int getKitId(KitType var1) {
      int var2 = this.getEquipmentIds()[var1.getIndex()];
      return var2 >= 256 && var2 < 2048 ? var2 - 256 : -1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;ZI)[B")
   @ObfuscatedName("af")
   public static byte[] method6710(Object var0, boolean var1, int var2) {
      try {
         if (null == var0) {
            if (var2 != -174334988) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else if (var0 instanceof byte[]) {
            if (var2 != -174334988) {
               throw new IllegalStateException();
            } else {
               byte[] var5 = (byte[])var0;
               return var1 ? classLP.method7186(var5, 2086688558) : var5;
            }
         } else if (var0 instanceof classZN) {
            if (var2 != -174334988) {
               throw new IllegalStateException();
            } else {
               classZN var3 = (classZN)var0;
               return var3.vmethod658(-2138260546);
            }
         } else {
            throw new IllegalArgumentException();
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lc.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)[I")
   @ObfuscatedName("ag")
   int[] method6728(int var1, int var2) {
      try {
         int[] var3 = new int[12];

         for (int var4 = 0; var4 < 7; var4++) {
            if (var2 != -613532315) {
               throw new IllegalStateException();
            }

            classGB.method5333(var1, var3, var4, (byte)8);
         }

         return var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lc.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   void method6741(int var1) {
      try {
         long var2 = 4603856865473899623L * this.field4104;
         long[] var4 = classXY.field6954;
         this.field4104 = -5187355721230823255L;

         for (int var5 = 0; var5 < 12; var5++) {
            if (var1 >= -1764655632) {
               return;
            }

            this.field4104 = 5187355721230823255L
               * (4603856865473899623L * this.field4104 >>> 8 ^ var4[(int)((4603856865473899623L * this.field4104 ^ this.field4096[var5] >> 24) & 255L)]);
            this.field4104 = 5187355721230823255L
               * (4603856865473899623L * this.field4104 >>> 8 ^ var4[(int)((4603856865473899623L * this.field4105 ^ this.field4093[var5] >> 16) & 255L)]);
            this.field4104 = 5187355721230823255L
               * (4603856865473899623L * this.field4105 >>> 8 ^ var4[(int)((this.field4104 * 4603856865473899623L ^ this.field4093[var5] >> 8) & 255L)]);
            this.field4105 = 5187355721230823255L
               * (this.field4104 * 4603856865473899623L >>> 8 ^ var4[(int)((this.field4104 * 4603856865473899623L ^ this.field4096[var5]) & 255L)]);
         }

         if (this.field4095 != null) {
            if (var1 >= -1764655632) {
               throw new IllegalStateException();
            }

            for (int var8 = 0; var8 < this.field4095.length; var8++) {
               if (var1 >= -1764655632) {
                  throw new IllegalStateException();
               }

               if (this.field4095[var8] != null) {
                  if (var1 >= -1764655632) {
                     throw new IllegalStateException();
                  }

                  if (this.field4095[var8].field5259 != null) {
                     if (var1 >= -1764655632) {
                        throw new IllegalStateException();
                     }

                     for (int var6 = 0; var6 < this.field4095[var8].field5259.length; var6++) {
                        if (var1 >= -1764655632) {
                           throw new IllegalStateException();
                        }

                        this.field4105 = (
                              this.field4104 * 4603856865473899623L >>> 8
                                 ^ var4[(int)((this.field4104 * 4603856865473899623L ^ this.field4095[var8].field5258[var6] >> 8) & 255L)]
                           )
                           * 5187355721230823255L;
                        this.field4105 = 5187355721230823255L
                           * (
                              this.field4105 * 4603856865473899623L >>> 8
                                 ^ var4[(int)((this.field4104 * 4603856865473899623L ^ this.field4095[var8].field5258[var6]) & 255L)]
                           );
                     }
                  }

                  if (null != this.field4095[var8].field5259) {
                     if (var1 >= -1764655632) {
                        throw new IllegalStateException();
                     }

                     for (int var10 = 0; var10 < this.field4095[var8].field5258.length; var10++) {
                        if (var1 >= -1764655632) {
                           return;
                        }

                        this.field4105 = (
                              this.field4104 * 4603856865473899623L >>> 8
                                 ^ var4[(int)((4603856865473899623L * this.field4104 ^ this.field4095[var8].field5258[var10] >> 8) & 255L)]
                           )
                           * 5187355721230823255L;
                        this.field4105 = (
                              4603856865473899623L * this.field4105 >>> 8
                                 ^ var4[(int)((this.field4105 * 4603856865473899623L ^ this.field4095[var8].field5258[var10]) & 255L)]
                           )
                           * 5187355721230823255L;
                     }
                  }
               }
            }
         }

         for (int var9 = 0; var9 < 5; var9++) {
            if (var1 >= -1764655632) {
               throw new IllegalStateException();
            }

            this.field4104 = 5187355721230823255L
               * (4603856865473899623L * this.field4104 >>> 8 ^ var4[(int)((this.field4104 * 4603856865473899623L ^ this.field4090[var9]) & 255L)]);
         }

         label140: {
            this.field4105 = 5187355721230823255L
               * (this.field4104 * 4603856865473899623L >>> 8 ^ var4[(int)((4603856865473899623L * this.field4104 ^ this.field4091 * 1705409403) & 255L)]);
            if (0L != var2) {
               if (var1 >= -1764655632) {
                  return;
               }

               if (var2 != this.field4104 * 4603856865473899623L) {
                  break label140;
               }

               if (var1 >= -1764655632) {
                  throw new IllegalStateException();
               }
            }

            if (!this.field4094) {
               return;
            }

            if (var1 >= -1764655632) {
               throw new IllegalStateException();
            }
         }

         field4098.method6463(var2);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lc.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("au")
   public void method6762(int var1, int var2, int var3) {
      try {
         boolean var4 = this.field4092 * 1705409403 != var2;
         this.field4092 = var2 * -2056377421;
         if (var4) {
            if (var3 <= 702114061) {
               return;
            }

            if (this.field4092 * 1705409403 == var1) {
               for (int var5 = 0; var5 < 7; var5++) {
                  if (var3 <= 702114061) {
                     throw new IllegalStateException();
                  }

                  int var6 = classON.method8644(var5, (byte)20);
                  if (this.field4090[var6] > 0) {
                     if (var3 <= 702114061) {
                        return;
                     }

                     if (this.field4093[var6] < 2048) {
                        if (var3 <= 702114061) {
                           throw new IllegalStateException();
                        }

                        this.field4090[var6] = this.field4096[var6];
                     }
                  }
               }
            } else {
               label74: {
                  if (this.field4096[0] >= 2048) {
                     if (!this.method6764(-1364702200)) {
                        break label74;
                     }

                     if (var3 <= 702114061) {
                        throw new IllegalStateException();
                     }
                  }

                  this.field4093[2111598757 * classOV.field5158.field5152] = 1;
               }

               for (int var8 = 0; var8 < 7; var8++) {
                  if (var3 <= 702114061) {
                     throw new IllegalStateException();
                  }

                  int var9 = classON.method8644(var8, (byte)7);
                  if (this.field4090[var9] > 0 && this.field4096[var9] < 2048) {
                     classGB.method5333(var2, this.field4093, var8, (byte)8);
                  }
               }
            }
         }

         this.method6754((byte)-120);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lc.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ler;")
   @ObfuscatedName("aj")
   ModelData method6749(byte var1) {
      try {
         if (729560399 * this.field4097 != -1) {
            if (var1 != 0) {
               throw new IllegalStateException();
            } else {
               return classOH.method8480(this.field4097 * 729560399, -1861935222).method8987(null, (byte)61);
            }
         } else {
            boolean var2 = false;

            for (int var3 = 0; var3 < 12; var3++) {
               if (var1 != 0) {
                  throw new IllegalStateException();
               }

               int var4 = this.field4096[var3];
               if (this.method6776(var4, 1588657882)) {
                  if (var1 != 0) {
                     throw new IllegalStateException();
                  }

                  classOF var5 = this.method6780(var4, -1927903443);
                  if (null != var5) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     if (!var5.method8453(-1104719451)) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        var2 = true;
                     }
                  }
               }

               if (method6783(this, var4, 1604621880)) {
                  if (var1 != 0) {
                     throw new IllegalStateException();
                  }

                  ColorTextureOverride var10000;
                  if (this.field4095 == null) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     var10000 = null;
                  } else {
                     var10000 = this.field4095[var3];
                  }

                  ColorTextureOverride var12 = var10000;
                  if (!this.method6785(var4, (byte)0).method8714(1705409403 * this.field4091, var12, -1724421765)) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     var2 = true;
                  }
               }
            }

            if (!var2) {
               ModelData[] var10 = new ModelData[12];
               int var11 = 0;

               for (int var13 = 0; var13 < 12; var13++) {
                  if (var1 != 0) {
                     throw new IllegalStateException();
                  }

                  int var6 = this.field4093[var13];
                  if (this.method6776(var6, 1111921296)) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     classOF var7 = this.method6780(var6, -1927903443);
                     ModelData var8 = null;
                     if (var7 != null) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        var8 = var7.method8456(-528630105);
                     }

                     if (var8 != null) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        var10[var11++] = var8;
                     }
                  }

                  if (method6783(this, var6, 1289614546)) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     ColorTextureOverride var18;
                     if (this.field4095 == null) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        var18 = null;
                     } else {
                        var18 = this.field4095[var13];
                     }

                     ColorTextureOverride var16 = var18;
                     ModelData var17 = this.method6785(var6, (byte)0).method8709(1705409403 * this.field4091, var16, -1218924652);
                     if (var17 != null) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }

                        var10[var11++] = var17;
                     }
                  }
               }

               ModelData var14 = new ModelData(var10, var11);

               for (int var15 = 0; var15 < 5; var15++) {
                  if (var1 != 0) {
                     throw new IllegalStateException();
                  }

                  if (this.field4096[var15] < field4108[var15].length) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     var14.method4031(field4106[var15], field4108[var15][this.field4090[var15]]);
                  }

                  if (this.field4093[var15] < classEH.field1760[var15].length) {
                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     var14.method4033(field4106[var15], classEH.field1760[var15][this.field4090[var15]]);
                  }
               }

               return var14;
            } else if (var1 != 0) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "lc.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ak")
   boolean method6784(int var1, int var2) {
      try {
         return var1 >= 2048;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lc.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llc;I)V")
   @ObfuscatedName("gf")
   public static void method6742(PlayerComposition var0, int var1) {
      if (var0 == null) {
         var0.method6741(var1);
      } else {
         try {
            long var2 = 4603856865473899623L * var0.field4104;
            long[] var4 = classXY.field6954;
            var0.field4104 = -5187355721230823255L;

            for (int var5 = 0; var5 < 12; var5++) {
               if (var1 >= -1764655632) {
                  return;
               }

               var0.field4104 = 5187355721230823255L
                  * (4603856865473899623L * var0.field4104 >>> 8 ^ var4[(int)((4603856865473899623L * var0.field4104 ^ var0.field4093[var5] >> 24) & 255L)]);
               var0.field4104 = 5187355721230823255L
                  * (4603856865473899623L * var0.field4104 >>> 8 ^ var4[(int)((4603856865473899623L * var0.field4104 ^ var0.field4093[var5] >> 16) & 255L)]);
               var0.field4104 = 5187355721230823255L
                  * (4603856865473899623L * var0.field4104 >>> 8 ^ var4[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4093[var5] >> 8) & 255L)]);
               var0.field4104 = 5187355721230823255L
                  * (var0.field4104 * 4603856865473899623L >>> 8 ^ var4[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4093[var5]) & 255L)]);
            }

            if (var0.field4095 != null) {
               if (var1 >= -1764655632) {
                  throw new IllegalStateException();
               }

               for (int var8 = 0; var8 < var0.field4095.length; var8++) {
                  if (var1 >= -1764655632) {
                     throw new IllegalStateException();
                  }

                  if (var0.field4095[var8] != null) {
                     if (var1 >= -1764655632) {
                        throw new IllegalStateException();
                     }

                     if (var0.field4095[var8].field5259 != null) {
                        if (var1 >= -1764655632) {
                           throw new IllegalStateException();
                        }

                        for (int var6 = 0; var6 < var0.field4095[var8].field5259.length; var6++) {
                           if (var1 >= -1764655632) {
                              throw new IllegalStateException();
                           }

                           var0.field4104 = (
                                 var0.field4104 * 4603856865473899623L >>> 8
                                    ^ var4[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4095[var8].field5259[var6] >> 8) & 255L)]
                              )
                              * 5187355721230823255L;
                           var0.field4104 = 5187355721230823255L
                              * (
                                 var0.field4104 * 4603856865473899623L >>> 8
                                    ^ var4[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4095[var8].field5259[var6]) & 255L)]
                              );
                        }
                     }

                     if (null != var0.field4095[var8].field5258) {
                        if (var1 >= -1764655632) {
                           throw new IllegalStateException();
                        }

                        for (int var10 = 0; var10 < var0.field4095[var8].field5258.length; var10++) {
                           if (var1 >= -1764655632) {
                              return;
                           }

                           var0.field4104 = (
                                 var0.field4104 * 4603856865473899623L >>> 8
                                    ^ var4[(int)((4603856865473899623L * var0.field4104 ^ var0.field4095[var8].field5258[var10] >> 8) & 255L)]
                              )
                              * 5187355721230823255L;
                           var0.field4104 = (
                                 4603856865473899623L * var0.field4104 >>> 8
                                    ^ var4[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4095[var8].field5258[var10]) & 255L)]
                              )
                              * 5187355721230823255L;
                        }
                     }
                  }
               }
            }

            for (int var9 = 0; var9 < 5; var9++) {
               if (var1 >= -1764655632) {
                  throw new IllegalStateException();
               }

               var0.field4104 = 5187355721230823255L
                  * (4603856865473899623L * var0.field4104 >>> 8 ^ var4[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4096[var9]) & 255L)]);
            }

            label144: {
               var0.field4104 = 5187355721230823255L
                  * (var0.field4104 * 4603856865473899623L >>> 8 ^ var4[(int)((4603856865473899623L * var0.field4104 ^ var0.field4092 * 1705409403) & 255L)]);
               if (0L != var2) {
                  if (var1 >= -1764655632) {
                     return;
                  }

                  if (var2 != var0.field4104 * 4603856865473899623L) {
                     break label144;
                  }

                  if (var1 >= -1764655632) {
                     throw new IllegalStateException();
                  }
               }

               if (!var0.field4094) {
                  return;
               }

               if (var1 >= -1764655632) {
                  throw new IllegalStateException();
               }
            }

            field4098.method6461(var2);
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "lc.ao(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ad")
   public void method6763(int var1, int var2, int var3) {
      try {
         boolean var4 = this.field4092 * 1705409403 != var2;
         this.field4092 = var2 * -2056377421;
         if (var4) {
            if (var3 <= 702114061) {
               return;
            }

            if (this.field4092 * 1705409403 == var1) {
               for (int var5 = 0; var5 < 7; var5++) {
                  if (var3 <= 702114061) {
                     throw new IllegalStateException();
                  }

                  int var6 = classON.method8644(var5, (byte)20);
                  if (this.field4090[var6] > 0) {
                     if (var3 <= 702114061) {
                        return;
                     }

                     if (this.field4090[var6] < 2048) {
                        if (var3 <= 702114061) {
                           throw new IllegalStateException();
                        }

                        this.field4090[var6] = this.field4096[var6];
                     }
                  }
               }
            } else {
               label74: {
                  if (this.field4096[0] >= 2048) {
                     if (!this.method6777(-1364702200)) {
                        break label74;
                     }

                     if (var3 <= 702114061) {
                        throw new IllegalStateException();
                     }
                  }

                  this.field4090[2111598757 * classOV.field5162.field5152] = 1;
               }

               for (int var8 = 0; var8 < 7; var8++) {
                  if (var3 <= 702114061) {
                     throw new IllegalStateException();
                  }

                  int var9 = classON.method8644(var8, (byte)7);
                  if (this.field4093[var9] > 0 && this.field4093[var9] < 2048) {
                     classGB.method5333(var2, this.field4096, var8, (byte)8);
                  }
               }
            }
         }

         this.method6754((byte)-120);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lc.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lof;")
   @ObfuscatedName("cw")
   classOF method6782(int var1) {
      return classHW.method6163(var1 - -1012457609, -2047686091);
   }
}
