import java.util.HashSet;
import java.util.Set;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xq")
public class classXQ implements classXZ {
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("ae")
   static final classXQ field6910 = new classXQ("", 2, new classXW[]{classXW.field6946, classXW.field6947, classXW.field6945});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("af")
   static final classXQ field6916 = new classXQ("", 1, new classXW[]{classXW.field6946, classXW.field6945, classXW.field6944});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("ax")
   static final classXQ field6909 = new classXQ("", 6, new classXW[]{classXW.field6945});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("ab")
   static final classXQ field6907 = new classXQ("", 3, new classXW[]{classXW.field6946});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("aj")
   static final classXQ field6911 = new classXQ("", 12, new classXW[]{classXW.field6946, classXW.field6945});
   @ObfuscatedName("aq")
   final int field6905;
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("ag")
   static final classXQ field6913 = new classXQ("", 4);
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("ac")
   static final classXQ field6912 = new classXQ("", 8, new classXW[]{classXW.field6946, classXW.field6945});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("aa")
   static final classXQ field6917 = new classXQ("", 9, new classXW[]{classXW.field6946, classXW.field6947});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("az")
   static final classXQ field6908 = new classXQ("", 0, new classXW[]{classXW.field6945, classXW.field6944});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("al")
   static final classXQ field6915 = new classXQ("", 11, new classXW[]{classXW.field6946});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("as")
   static final classXQ field6914 = new classXQ("", 5, new classXW[]{classXW.field6946, classXW.field6945});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("ay")
   static final classXQ field6919 = new classXQ("", 13, new classXW[]{classXW.field6946});
   @ObfuscatedSignature(descriptor = "Lxq;")
   @ObfuscatedName("ao")
   static final classXQ field6918 = new classXQ("", 10, new classXW[]{classXW.field6946});
   @ObfuscatedName("ad")
   final Set field6906 = new HashSet();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return -1655695213 * this.field6905;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("gz")
   static final void method12935(int var0, int var1) {
      Scene.method4514(var0);

      try {
         for (WorldView var3 : client.field815) {
            var3.method3774(-1592433219);
         }

         int var6 = classGN.method5538(var0, (byte)1).field5191 * 2063088649;
         if (0 == var6) {
            if (var1 == 541084081) {
               throw new IllegalStateException();
            }
         } else {
            int var7 = classLB.field4088[var0];
            if (1 == var6) {
               if (var1 == 541084081) {
                  return;
               }

               if (var7 == 1) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  classHA.method6019(0.9);
               }

               if (2 == var7) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  classHA.method6019(0.8);
               }

               if (3 == var7) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  classHA.method6019(0.7);
               }

               if (4 == var7) {
                  classHA.method6019(0.6);
               }
            }

            if (3 == var6) {
               if (var1 == 541084081) {
                  throw new IllegalStateException();
               }

               if (0 == var7) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  classAB.field122.method2763(255, 1222866659);
                  classKL.method6536(-1894929341);
               }

               if (var7 == 1) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  classAB.field122.method2763(192, 1222866659);
                  classKL.method6536(-122589735);
               }

               if (var7 == 2) {
                  classAB.field122.method2763(128, 1222866659);
                  classKL.method6536(-536779833);
               }

               if (3 == var7) {
                  if (var1 == 541084081) {
                     return;
                  }

                  classAB.field122.method2763(64, 1222866659);
                  classKL.method6536(-1563284186);
               }

               if (var7 == 4) {
                  if (var1 == 541084081) {
                     return;
                  }

                  classAB.field122.method2763(0, 1222866659);
                  classKL.method6536(-1139972936);
               }
            }

            if (4 == var6) {
               if (var1 == 541084081) {
                  return;
               }

               if (0 == var7) {
                  if (var1 == 541084081) {
                     return;
                  }

                  ClientPreferences.method2774(classAB.field122, 127, -1580279836);
                  classGI.method5493(-1779263393);
               }

               if (var7 == 1) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  ClientPreferences.method2774(classAB.field122, 96, -1169712968);
                  classGI.method5493(-1894517613);
               }

               if (var7 == 2) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  ClientPreferences.method2774(classAB.field122, 64, -1282325137);
                  classGI.method5493(-2006285235);
               }

               if (var7 == 3) {
                  if (var1 == 541084081) {
                     return;
                  }

                  ClientPreferences.method2774(classAB.field122, 32, -448671078);
                  classGI.method5493(-2093856574);
               }

               if (var7 == 4) {
                  if (var1 == 541084081) {
                     return;
                  }

                  ClientPreferences.method2774(classAB.field122, 0, -1537904000);
                  classGI.method5493(-2043956577);
               }
            }

            if (5 == var6) {
               if (var1 == 541084081) {
                  throw new IllegalStateException();
               }

               boolean var10000;
               if (var7 == 1) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
               } else {
                  var10000 = false;
               }

               client.field973 = var10000;
            }

            if (var6 == 6) {
               if (var1 == 541084081) {
                  throw new IllegalStateException();
               }

               client.field985 = var7 * 1235129253;
            }

            if (9 == var6) {
            }

            if (10 == var6) {
               if (var1 == 541084081) {
                  throw new IllegalStateException();
               }

               if (var7 == 0) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  ClientPreferences.method2778(classAB.field122, 127, (byte)52);
                  classDO.method3523((byte)39);
               }

               if (1 == var7) {
                  ClientPreferences.method2778(classAB.field122, 96, (byte)-45);
                  classDO.method3523((byte)103);
               }

               if (var7 == 2) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  ClientPreferences.method2778(classAB.field122, 64, (byte)-33);
                  classDO.method3523((byte)80);
               }

               if (var7 == 3) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  ClientPreferences.method2778(classAB.field122, 32, (byte)-99);
                  classDO.method3523((byte)116);
               }

               if (4 == var7) {
                  ClientPreferences.method2778(classAB.field122, 0, (byte)20);
                  classDO.method3523((byte)25);
               }
            }

            if (17 == var6) {
               if (var1 == 541084081) {
                  return;
               }

               if (!client.field926.contains(var0)) {
                  client.field926.add(var0);
               }
            }

            if (18 == var6) {
               if (var1 == 541084081) {
                  throw new IllegalStateException();
               }

               classCK[] var4 = new classCK[]{classCK.field768, classCK.field765, classCK.field766, classCK.field767, classCK.field769};
               client.field917 = (classCK)classOF.method8436(var4, var7, 330820815);
               if (null == client.field917) {
                  if (var1 == 541084081) {
                     return;
                  }

                  client.field917 = classCK.field765;
               }
            }

            if (var6 == 19) {
               if (var1 == 541084081) {
                  throw new IllegalStateException();
               }

               if (var7 == -1) {
                  if (var1 == 541084081) {
                     throw new IllegalStateException();
                  }

                  client.field972 = 2023588213;
               } else {
                  client.field972 = -2023588213 * (var7 & 2047);
               }
            }

            if (var6 == 22) {
               if (var1 == 541084081) {
                  throw new IllegalStateException();
               }

               classCK[] var8 = new classCK[]{classCK.field768, classCK.field765, classCK.field766, classCK.field767, classCK.field769};
               client.field898 = (classCK)classOF.method8436(var8, var7, -14253417);
               if (null == client.field898) {
                  if (var1 == 541084081) {
                     return;
                  }

                  client.field898 = classCK.field765;
               }
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xq.gz(" + ')');
      }
   }

   classXQ(String var1, int var2, classXW[] var3) {
      this.field6905 = var2 * -485964389;
      classXW[] var4 = var3;

      for (int var5 = 0; var5 < var4.length; var5++) {
         classXW var6 = var4[var5];
         this.field6906.add(var6);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return -1655695213 * this.field6905;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xq.az(" + 41);
      }
   }

   static {
      method12930((byte)105);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return -1655695213 * this.field6905;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return -1655695213 * this.field6905;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)I")
   @ObfuscatedName("az")
   static int method12931(String var0, int var1) {
      try {
         int var3;
         for (var3 = var0.indexOf(60, 0); -1 != var3 && var3 > 0; var3 = var0.indexOf(60, 1 + var3)) {
            if (var1 <= 2014430317) {
               throw new IllegalStateException();
            }

            if (var0.charAt(var3 - 1) != '\\') {
               break;
            }
         }

         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xq.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqq;)Ljava/lang/String;")
   @ObfuscatedName("cn")
   public static String method12928(classQQ var0) {
      return var0.field5591;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpi;I)I")
   @ObfuscatedName("yg")
   public static int method12932(ColorTextureOverride var0, int var1) {
      return 0 == var1 ? var0.field5257 * -292131739 : 10867937 * var0.field5260;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lxq;")
   @ObfuscatedName("as")
   static classXQ[] method12929() {
      return new classXQ[]{
         field6918, field6914, field6916, field6909, field6917, field6911, field6908, field6912, field6913, field6910, field6915, field6919, field6907
      };
   }

   classXQ(String var1, int var2) {
      this.field6905 = -485964389 * var2;
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("bn")
   static final void method12934(boolean var0, int var1) {
      try {
         if (var0) {
            if (var1 != 402128428) {
               return;
            }

            classEN var10000;
            if (classBF.field392) {
               if (var1 != 402128428) {
                  throw new IllegalStateException();
               }

               var10000 = classEN.field1797;
            } else {
               var10000 = classEN.field1795;
            }

            client.field928 = var10000;
         } else {
            classEN var3;
            if (ClientPreferences.method2805(classAB.field122, classBF.field389, (short)10983)) {
               if (var1 != 402128428) {
                  throw new IllegalStateException();
               }

               var3 = classEN.field1793;
            } else {
               var3 = classEN.field1796;
            }

            client.field928 = var3;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xq.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[Lxq;")
   @ObfuscatedName("ag")
   static classXQ[] method12930(byte var0) {
      try {
         return new classXQ[]{
            field6918, field6914, field6916, field6909, field6917, field6911, field6908, field6912, field6913, field6910, field6915, field6919, field6907
         };
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xq.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcp;I)V")
   @ObfuscatedName("aq")
   static void method12933(classCP var0, int var1) {
      try {
         if (var0 == client.field902) {
            if (var1 <= -1271687819) {
               throw new IllegalStateException();
            }
         } else {
            client.field902 = var0;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xq.aq(" + ')');
      }
   }
}
