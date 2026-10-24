import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("pp")
public class NpcOverrides implements net.runelite.api.NpcOverrides {
   @ObfuscatedName("af")
   public int[] field5372;
   @ObfuscatedName("ax")
   public int[] field5377;
   @ObfuscatedName("ae")
   public short[] field5373;
   @ObfuscatedSignature(descriptor = "Ldk;")
   @ObfuscatedName("gu")
   static classDK field5378;
   @ObfuscatedName("ag")
   public boolean field5375 = false;
   @ObfuscatedName("az")
   public long field5376;
   @ObfuscatedName("ab")
   short[] field5374;
   @ObfuscatedName("ac")
   public int field5370;
   @ObfuscatedName("as")
   public boolean field5371 = false;

   public NpcOverrides(long var1, int[] var3, short[] var4, short[] var5, boolean var6) {
      this(var1, var3, var4, var5, var6, false, 0, null);
   }

   public NpcOverrides(long var1, int[] var3, short[] var4, short[] var5, boolean var6, boolean var7, int var8, int[] var9) {
      this.field5370 = 0;
      this.field5376 = 1448637320550906347L * var1;
      this.field5372 = var3;
      this.field5373 = var4;
      this.field5374 = var5;
      this.field5375 = var6;
      this.field5371 = var7;
      this.field5370 = var8 * -1590649111;
      this.field5377 = var9;
   }

   public short[] getTextureToReplaceWith() {
      return this.field5374;
   }

   public int[] getModelIds() {
      return this.field5372;
   }

   public short[] getColorToReplaceWith() {
      return this.field5373;
   }

   public boolean useLocalPlayer() {
      return this.field5375;
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIIIIZLvv;Lqn;I)V")
   @ObfuscatedName("ab")
   static void method9135(Widget[] var0, int var1, int var2, int var3, int var4, int var5, int var6, boolean var7, classVV var8, classQN var9, int var10) {
      try {
         for (int var11 = var1; var11 <= var2; var11++) {
            if (var10 == 846735874) {
               throw new IllegalStateException();
            }

            Widget var12 = var0[var11];
            if (null == var12) {
               if (var10 == 846735874) {
                  throw new IllegalStateException();
               }
            } else if (var3 == var12.field4340 * -1502647899) {
               if (var10 == 846735874) {
                  throw new IllegalStateException();
               }

               if (var12.field4261 * 653784551 != var4) {
                  if (var10 == 846735874) {
                     throw new IllegalStateException();
                  }
               } else {
                  classPF.method8890(var12, var5, var6, var7, var8, var9, (short)27391);
                  classMP.method7680(var12, var5, var6, (short)-9244);
                  if (-34809441 * var12.field4339 > -842862191 * var12.field4256 - var12.field4247 * 1115597881) {
                     if (var10 == 846735874) {
                        return;
                     }

                     var12.field4339 = var12.field4256 * -1437415729 - var12.field4247 * 2134006567;
                  }

                  if (-34809441 * var12.field4339 < 0) {
                     if (var10 == 846735874) {
                        throw new IllegalStateException();
                     }

                     var12.field4339 = 0;
                  }

                  if (-41424189 * var12.field4329 > -714521463 * var12.field4267 - 293773697 * var12.field4257) {
                     var12.field4329 = var12.field4267 * -1471306045 - var12.field4257 * 577918571;
                  }

                  if (var12.field4329 * -41424189 < 0) {
                     if (var10 == 846735874) {
                        throw new IllegalStateException();
                     }

                     var12.field4329 = 0;
                  }

                  if (0 == var12.field4373 * -1510882375) {
                     if (var10 == 846735874) {
                        return;
                     }

                     classFC.method4609(var0, var12, var7, var8, var9, -405164896);
                  }
               }
            }
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "pp.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsu;FZB)F")
   @ObfuscatedName("af")
   static float method9136(classSU var0, float var1, boolean var2, byte var3) {
      try {
         float var4 = 0.0F;
         if (var0 != null) {
            if (var3 != 1) {
               throw new IllegalStateException();
            }

            if (var0.method10735((byte)-109) != 0) {
               float var5 = var0.field6066[0].field6101 * 1210377865;
               float var6 = var0.field6066[var0.method10735((byte)-3) - 1].field6101 * 1210377865;
               float var7 = var6 - var5;
               if (var7 == 0.0F) {
                  if (var3 != 1) {
                     throw new IllegalStateException();
                  }

                  return var0.field6066[0].field6100;
               }

               float var8 = 0.0F;
               if (var1 > var6) {
                  var8 = (var1 - var6) / var7;
               } else {
                  var8 = (var1 - var5) / var7;
               }

               float var9 = (int)var8;
               float var10 = Math.abs(var8 - var9);
               float var11 = var10 * var7;
               var9 = Math.abs(var9 + 1.0F);
               float var12 = var9 / 2.0F;
               float var13 = (int)var12;
               var10 = var12 - var13;
               if (var2) {
                  if (classSE.field5910 == var0.field6061) {
                     if (var3 != 1) {
                        throw new IllegalStateException();
                     }

                     if (var10 != 0.0F) {
                        if (var3 != 1) {
                           throw new IllegalStateException();
                        }

                        var11 += var5;
                     } else {
                        var11 = var6 - var11;
                     }
                  } else {
                     label152: {
                        if (var0.field6061 != classSE.field5913) {
                           if (var3 != 1) {
                              throw new IllegalStateException();
                           }

                           if (classSE.field5912 != var0.field6061) {
                              if (classSE.field5911 == var0.field6061) {
                                 if (var3 != 1) {
                                    throw new IllegalStateException();
                                 }

                                 var11 = var5 - var1;
                                 float var14 = var0.field6066[0].field6099;
                                 float var15 = var0.field6066[0].field6097;
                                 var4 = var0.field6066[0].field6100;
                                 if (var14 != 0.0F) {
                                    if (var3 != 1) {
                                       throw new IllegalStateException();
                                    }

                                    var4 -= var15 * var11 / var14;
                                 }

                                 return var4;
                              }
                              break label152;
                           }

                           if (var3 != 1) {
                              throw new IllegalStateException();
                           }
                        }

                        var11 = var6 - var11;
                     }
                  }
               } else if (var0.field6060 == classSE.field5910) {
                  if (0.0F != var10) {
                     if (var3 != 1) {
                        throw new IllegalStateException();
                     }

                     var11 = var6 - var11;
                  } else {
                     var11 += var5;
                  }
               } else {
                  label145: {
                     if (var0.field6060 != classSE.field5913) {
                        if (var3 != 1) {
                           throw new IllegalStateException();
                        }

                        if (var0.field6060 != classSE.field5912) {
                           if (classSE.field5911 == var0.field6060) {
                              if (var3 != 1) {
                                 throw new IllegalStateException();
                              }

                              var11 = var1 - var6;
                              float var26 = var0.field6066[var0.method10735((byte)-77) - 1].field6096;
                              float var27 = var0.field6066[var0.method10735((byte)2) - 1].field6098;
                              var4 = var0.field6066[var0.method10735((byte)-54) - 1].field6100;
                              if (0.0F != var26) {
                                 if (var3 != 1) {
                                    throw new IllegalStateException();
                                 }

                                 var4 += var27 * var11 / var26;
                              }

                              return var4;
                           }
                           break label145;
                        }

                        if (var3 != 1) {
                           throw new IllegalStateException();
                        }
                     }

                     var11 += var5;
                  }
               }

               var4 = classGL.method5519(var0, var11, (byte)127);
               if (var2) {
                  if (var3 != 1) {
                     throw new IllegalStateException();
                  }

                  if (var0.field6061 == classSE.field5912) {
                     float var28 = var0.field6066[var0.method10735((byte)-110) - 1].field6100 - var0.field6066[0].field6100;
                     var4 -= var28 * var9;
                     return var4;
                  }
               }

               if (!var2) {
                  if (var3 != 1) {
                     throw new IllegalStateException();
                  }

                  if (classSE.field5912 == var0.field6060) {
                     if (var3 != 1) {
                        throw new IllegalStateException();
                     }

                     float var16 = var0.field6066[var0.method10735((byte)-31) - 1].field6100 - var0.field6066[0].field6100;
                     var4 += var9 * var16;
                  }
               }

               return var4;
            }

            if (var3 != 1) {
               throw new IllegalStateException();
            }
         }

         return var4;
      } catch (RuntimeException var17) {
         throw classEG.method3899(var17, "pp.af(" + ')');
      }
   }
}
