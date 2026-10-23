import java.util.ArrayList;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pd")
public final class classPD {
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("dw")
   static IndexedSprite[] field5225;
   @ObfuscatedName("az")
   final Object field5222;
   @ToRemove(unused = "true")
   @ObfuscatedName("bs")
   static final int field5224 = 57;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   static final int field5223 = 1;
   @ObfuscatedName("af")
   int field5221;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;IB)V")
   @ObfuscatedName("br")
   static void method8880(String var0, int var1, byte var2) {
      try {
         classBK.method1122(0, 0, (byte)49);
         ArrayList var3 = new ArrayList();
         var3.add(new MidiRequest(classFC.field2206, var0, "", var1, false));
         classIJ.method6258(var3, 0, 0, 0, 100, false, -1473723703);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pd.br(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;III)Lgx;")
   @ObfuscatedName("fu")
   public static WorldMapData method8878(RenderOverview var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method12573(var1, var1, var1);
      }

      for (WorldMapData var5 : var0.field6745.values()) {
         if (WorldMapData.method5858(var5, var1, var2, var3, (byte)46)) {
            return var5;
         }
      }

      return null;
   }

   classPD(Object var1, int var2) {
      this.field5222 = var1;
      this.field5221 = var2 * 39206217;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;ZI)Lte;")
   @ObfuscatedName("ai")
   public static classTE method8877(classXY var0, boolean var1, int var2) {
      try {
         int var3 = var0.method13099(1206608293);
         if (var3 >= 0) {
            if (var2 != -193629541) {
               throw new IllegalStateException();
            }

            if (var3 <= 5000) {
               int var4 = var0.method13095(-346779531);
               if (var4 != 0) {
                  if (var2 != -193629541) {
                     throw new IllegalStateException();
                  }

                  if (1 != var4) {
                     if (var4 != 2) {
                        if (var2 != -193629541) {
                           throw new IllegalStateException();
                        }

                        if (var4 != 3) {
                           if (var4 == 4) {
                              if (var2 != -193629541) {
                                 throw new IllegalStateException();
                              }

                              String[] var11 = new String[var3];

                              for (int var14 = 0; var14 < var3; var14++) {
                                 if (var2 != -193629541) {
                                    throw new IllegalStateException();
                                 }

                                 var11[var14] = var0.method13127(576806578);
                              }

                              classTE var15;
                              if (var1) {
                                 if (var2 != -193629541) {
                                    throw new IllegalStateException();
                                 }

                                 var15 = SceneTilePaint.method4823(var11, -1179293840);
                              } else {
                                 classTE var16 = new classTE(classYY.field7111, false);
                                 var16.field6123 = var11;
                                 var16.field6129 = var11.length * -1063196331;
                                 var16.field6121 = var11.length * 404453943;
                                 var15 = var16;
                              }

                              return var15;
                           }

                           if (var4 == 255) {
                              if (var2 != -193629541) {
                                 throw new IllegalStateException();
                              }

                              return null;
                           }

                           throw new RuntimeException();
                        }

                        if (var2 != -193629541) {
                           throw new IllegalStateException();
                        }
                     }

                     long[] var10 = new long[var3];

                     for (int var12 = 0; var12 < var3; var12++) {
                        if (var2 != -193629541) {
                           throw new IllegalStateException();
                        }

                        var10[var12] = classXY.method13115(var0, 813735498);
                     }

                     classTE var13;
                     if (var1) {
                        if (var2 != -193629541) {
                           throw new IllegalStateException();
                        }

                        var13 = classEK.method3929(var10, (byte)49);
                     } else {
                        classTE var8 = new classTE(classYY.field7113, false);
                        var8.field6125 = var10;
                        var8.field6129 = -1063196331 * var10.length;
                        var8.field6121 = var10.length * 404453943;
                        var13 = var8;
                     }

                     return var13;
                  }

                  if (var2 != -193629541) {
                     throw new IllegalStateException();
                  }
               }

               int[] var5 = new int[var3];

               for (int var6 = 0; var6 < var3; var6++) {
                  if (var2 != -193629541) {
                     throw new IllegalStateException();
                  }

                  var5[var6] = classXY.method13113(var0, (byte)1);
               }

               return var1 ? classKH.method6512(var5, (byte)124) : classDG.method3085(var5, -530642757);
            }

            if (var2 != -193629541) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException();
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "pd.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ZI)Ljava/lang/String;")
   @ObfuscatedName("aa")
   static String method8879(String var0, boolean var1, int var2) {
      try {
         StringBuilder var3 = new StringBuilder();
         int var4 = 0;

         while (var4 < var0.length()) {
            char var5 = var0.charAt(var4);
            if ('<' == var5) {
               int var6 = var0.indexOf(62, var4 + 1);
               if (-1 != var6) {
                  var3.append(var0, var4, 1 + var6);
                  var4 = 1 + var6;
                  continue;
               }
            }

            if (var5 == '@') {
               if (var2 == 1385470159) {
                  throw new IllegalStateException();
               }

               int var8 = var0.indexOf(64, var4 + 1);
               if (var8 != -1) {
                  if (var2 == 1385470159) {
                     throw new IllegalStateException();
                  }

                  var3.append(var0, var4, 1 + var8);
                  var4 = 1 + var8;
                  continue;
               }
            }

            char var10001;
            if (var1) {
               if (var2 == 1385470159) {
                  throw new IllegalStateException();
               }

               var10001 = Character.toLowerCase(var5);
            } else {
               var10001 = Character.toUpperCase(var5);
            }

            var3.append(var10001);
            var4++;
         }

         return var3.toString();
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "pd.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;B)V")
   @ObfuscatedName("az")
   public static void method8876(IndexDataBase var0, byte var1) {
      try {
         DBRowConfig.field7175 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pd.az(" + ')');
      }
   }
}
