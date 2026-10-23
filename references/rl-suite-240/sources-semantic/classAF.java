import java.awt.FontMetrics;
import java.util.Arrays;
import java.util.concurrent.Callable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("af")
public class classAF implements Callable {
   @ToRemove(unused = "true")
   @ObfuscatedName("ce")
   public static final int field144 = 98;
   @ObfuscatedSignature(descriptor = "Lxy;")
   @ObfuscatedName("az")
   final classXY field143;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("he")
   static classVP field146;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("ig")
   static classVP field147;
   @ObfuscatedSignature(descriptor = "Lae;")
   @ObfuscatedName("af")
   final classAE field142;
   @ObfuscatedName("bf")
   static FontMetrics field145;

   classAF(classAC var1, classXY var2, classAE var3) {
      this.this$0 = var1;
      this.field143 = var2;
      this.field142 = var3;
   }

   @ObfuscatedSignature(descriptor = "(ILpl;IZLdn;Ljava/lang/String;IIIIB)V")
   @ObfuscatedName("fk")
   static void method333(int var0, NPCComposition var1, int var2, boolean var3, NPC var4, String var5, int var6, int var7, int var8, int var9, byte var10) {
      try {
         if (var1.method8973(936289412).vmethod546((byte)0)) {
            if (var10 == 7) {
               throw new IllegalStateException();
            }

            for (int var11 = 4; var11 >= 0; var11--) {
               if (!client.field823) {
                  if (var10 == 7) {
                     throw new IllegalStateException();
                  }

                  if (classGB.field2545) {
                     continue;
                  }

                  if (var10 == 7) {
                     throw new IllegalStateException();
                  }

                  if (!var4.method3457(var11, -94498273)) {
                     if (var10 == 7) {
                        throw new IllegalStateException();
                     }
                     continue;
                  }
               }

               String var12 = var1.method8973(936289412).vmethod551(var11, classLB.field4088, (byte)24);
               if (var3) {
                  if (var10 == 7) {
                     return;
                  }

                  if (null == var12) {
                     continue;
                  }

                  if (var10 == 7) {
                     return;
                  }

                  if (!var12.equalsIgnoreCase(classKK.field3774)) {
                     if (var10 == 7) {
                        throw new IllegalStateException();
                     }
                     continue;
                  }
               }

               if (!var3) {
                  if (var10 == 7) {
                     throw new IllegalStateException();
                  }

                  if (var12 != null) {
                     if (var10 == 7) {
                        return;
                     }

                     if (var12.equalsIgnoreCase(classKK.field3774)) {
                        if (var10 == 7) {
                           return;
                        }
                        continue;
                     }
                  }
               }

               if (client.field823) {
                  if (var10 == 7) {
                     throw new IllegalStateException();
                  }

                  var12 = classQM.method9703(var12, var11, (byte)-21);
               }

               if (var12 != null) {
                  if (var10 == 7) {
                     throw new IllegalStateException();
                  }

                  if (var3) {
                     if (var10 == 7) {
                        return;
                     }

                     if (client.field898 == classCK.field766) {
                        if (var10 == 7) {
                           throw new IllegalStateException();
                        }
                        continue;
                     }
                  }

                  label141: {
                     label187: {
                        if (var3) {
                           if (var10 == 7) {
                              throw new IllegalStateException();
                           }

                           if (client.field898 == classCK.field769) {
                              break label187;
                           }
                        }

                        if (classCK.field765 != client.field898) {
                           break label141;
                        }

                        if (var10 == 7) {
                           throw new IllegalStateException();
                        }

                        if (var1.field5318 * 1781570005 <= var2) {
                           break label141;
                        }

                        if (var10 == 7) {
                           return;
                        }
                     }

                     var0 = 2000;
                  }

                  int var13 = 0;
                  if (0 == var11) {
                     if (var10 == 7) {
                        throw new IllegalStateException();
                     }

                     var13 = 9 + var0;
                  }

                  if (1 == var11) {
                     if (var10 == 7) {
                        throw new IllegalStateException();
                     }

                     var13 = var0 + 10;
                  }

                  if (2 == var11) {
                     var13 = 11 + var0;
                  }

                  if (var11 == 3) {
                     if (var10 == 7) {
                        throw new IllegalStateException();
                     }

                     var13 = 12 + var0;
                  }

                  if (4 == var11) {
                     var13 = var0 + 13;
                  }

                  int var14 = Friend.method10203(var12, var5, var13, var6, var7, var8, -1, false, var9, -324749371);
                  classJC.method6396(var14, var1.method8973(936289412), var11, var5, var13, var6, var7, var8, var9, -1533141989);
               }
            }
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "af.fk(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvw;)V")
   @ObfuscatedName("iy")
   public static void method327(classVW var0) {
      if (var0 == null) {
         var0.method12260();
      }

      var0.field6628 = null;
      var0.field6620 = null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("af")
   public Object method328() {
      return this.field142.vmethod20(this.field143);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)V")
   @ObfuscatedName("js")
   public static void method330(classVV var0, Widget var1) {
      if (var0 == null) {
         var0.method12195(var1);
      }

      var0.field6615 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;II)V")
   @ObfuscatedName("ag")
   static void method329(WorldView var0, int var1, int var2, classUP var3, classKH var4, int var5, int var6) {
      try {
         if (1 == var5) {
            if (var6 >= -1673958617) {
               throw new IllegalStateException();
            }

            NPC var7 = classCW.method2688(client.field942.method6572(-987724280), client.field815, 499194751);
            if (var7 != null) {
               if (var6 >= -1673958617) {
                  throw new IllegalStateException();
               }

               classNF.method7880(
                  var7.method3177((byte)4), var7.field1484 * -1547553299, var7.field1486 * -1272026483, var1, var2, var3, classCQ.field1219[1], var4, (byte)109
               );
            }
         } else if (var5 == 2) {
            if (var6 >= -1673958617) {
               throw new IllegalStateException();
            }

            WorldView var11 = client.field815
               .method1590(classKQ.method6577(client.field942, 1166741555), classKQ.method6579(client.field942, -1955951477), -992137014);
            int var8 = classKY.method6649(classKQ.method6577(client.field942, 636704452) - -1444178379 * var11.field1706, 1247224829)
               + client.field942.method6580((byte)30);
            int var9 = classKY.method6649(classKQ.method6579(client.field942, -1026635899) - -351145363 * var11.field1707, -415490596)
               + client.field942.method6584((byte)83);
            classNF.method7880(var11, var8, var9, var1, var2, var3, classCQ.field1219[1], var4, (byte)78);
         } else if (3 == var5) {
            if (var6 >= -1673958617) {
               throw new IllegalStateException();
            }

            Player var12 = classEN.method3954(client.field942.method6572(-839253239), client.field815, -1096730237);
            if (var12 != null) {
               if (var6 >= -1673958617) {
                  throw new IllegalStateException();
               }

               classNF.method7880(
                  var12.method3177((byte)7),
                  -1547553299 * var12.field1484,
                  -1272026483 * var12.field1486,
                  var1,
                  var2,
                  var3,
                  classCQ.field1219[1],
                  var4,
                  (byte)99
               );
            }
         } else if (var5 == 4) {
            int var13 = client.field942.method6572(-438197316);
            WorldEntity var14 = (WorldEntity)classIS.field3051.field1698.method13637(var13);
            if (null != var14) {
               if (var6 >= -1673958617) {
                  throw new IllegalStateException();
               }

               classNF.method7880(
                  classIS.field3051, var14.vmethod368(311606126), var14.vmethod371(-524024903), var1, var2, var3, classCQ.field1219[1], var4, (byte)47
               );
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "af.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;II)I")
   @ObfuscatedName("cz")
   public static int method332(ItemComposition var0, int var1, int var2) {
      return classGQ.method5635(var0.field5030, var1, var2, 1255818871);
   }

   @Override
   public Object call() {
      try {
         return this.field142.vmethod20(this.field143);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "af.call(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[Lcb;")
   @ObfuscatedName("ag")
   static classCB[] method326(byte var0) {
      try {
         return new classCB[]{
            classCB.field646,
            classCB.field641,
            classCB.field642,
            classCB.field640,
            classCB.field644,
            classCB.field643,
            classCB.field639,
            classCB.field645,
            classCB.field638
         };
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "af.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;Ljava/lang/Object;III)V")
   @ObfuscatedName("ax")
   public static void method331(classTE var0, Object var1, int var2, int var3, int var4) {
      try {
         classOL.method8544(var0, 1327640383);
         if (var2 < 0) {
            if (var4 >= 30953652) {
               throw new IllegalStateException();
            }

            var2 = 0;
         }

         int var5;
         label46: {
            var5 = var2 + var3;
            if (var3 >= 0) {
               if (var4 >= 30953652) {
                  throw new IllegalStateException();
               }

               if (var5 >= 0) {
                  if (var5 <= var0.method10797(243125187)) {
                     break label46;
                  }

                  if (var4 >= 30953652) {
                     throw new IllegalStateException();
                  }
               }
            }

            var5 = var0.method10797(-228256047);
         }

         if (classYY.field7109 == var0.field6126) {
            Arrays.fill(var0.method10791(1721725735), var2, var5, ((Integer)var1).intValue());
         } else if (classYY.field7113 == var0.field6126) {
            if (var4 >= 30953652) {
               return;
            }

            Arrays.fill(var0.method10793(-1895332582), var2, var5, ((Long)var1).longValue());
         } else {
            Arrays.fill(var0.method10796(-136674534), var2, var5, var1);
         }

         var0.field6131 = true;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "af.ax(" + ')');
      }
   }
}
