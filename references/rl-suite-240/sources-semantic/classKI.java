import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ki")
public class classKI implements classXZ {
   @ObfuscatedSignature(descriptor = "Lki;")
   @ObfuscatedName("ae")
   public static final classKI field3590 = new classKI(1);
   @ObfuscatedSignature(descriptor = "Lki;")
   @ObfuscatedName("af")
   public static final classKI field3589 = new classKI(0);
   @ToRemove(unused = "true")
   @ObfuscatedName("bv")
   static final int field3595 = 103;
   @ObfuscatedSignature(descriptor = "Lki;")
   @ObfuscatedName("ab")
   public static final classKI field3591 = new classKI(2);
   @ObfuscatedName("ag")
   final int field3592;
   @ObfuscatedName("aq")
   public static int field3593;
   @ObfuscatedSignature(descriptor = "Lki;")
   @ObfuscatedName("az")
   public static final classKI field3588 = new classKI(-1);
   @ObfuscatedName("jl")
   static int field3596;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   static final int field3594 = 0;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldl;Lpi;)V")
   @ObfuscatedName("mp")
   public static void method6524(TileItem var0, ColorTextureOverride var1) {
      if (var0 == null) {
         var0.getDespawnTime();
      }

      var0.field1557 = var1;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("be")
   static int method6526(int var0, Script var1, boolean var2, int var3) {
      try {
         if (4200 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var29 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classOB.method8329(var29, (byte)-22).method8741(1248604557);
               return 1;
            }
         } else if (4201 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var28 = classBB.field323[DynamicObject.field1676 * -324749371];
               int var38 = classBB.field323[1 + DynamicObject.field1676 * -324749371];
               ItemComposition var40 = classOB.method8329(var28, (byte)34);
               if (var38 >= 1) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  if (var38 <= 5) {
                     if (var3 <= 952444385) {
                        throw new IllegalStateException();
                     }

                     if (ItemComposition.method8666(var40, (byte)84).vmethod540(var38 - 1, -1607978885)) {
                        classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = ItemComposition.method8666(var40, (byte)91)
                           .vmethod551(var38 - 1, classLB.field4088, (byte)12);
                        return 1;
                     }
                  }
               }

               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               return 1;
            }
         } else if (4202 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var27 = classBB.field323[-324749371 * DynamicObject.field1676];
               int var37 = classBB.field323[-324749371 * DynamicObject.field1676 + 1];
               ItemComposition var39 = classOB.method8329(var27, (byte)66);
               if (var37 >= 1) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  if (var37 <= 5) {
                     if (var3 <= 952444385) {
                        throw new IllegalStateException();
                     }

                     if (null != var39.field5023[var37 - 1]) {
                        if (var3 <= 952444385) {
                           throw new IllegalStateException();
                        }

                        classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var39.field5023[var37 - 1];
                        return 1;
                     }
                  }
               }

               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               return 1;
            }
         } else if (var0 == 4222) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 360123687;
               int var26 = classBB.field323[DynamicObject.field1676 * -324749371];
               int var36 = classBB.field323[DynamicObject.field1676 * -324749371 + 1];
               int var6 = classBB.field323[2 + DynamicObject.field1676 * -324749371];
               ItemComposition var7 = classOB.method8329(var26, (byte)-2);
               String var8 = null;
               if (var36 >= 1) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  if (var36 <= 5) {
                     if (var3 <= 952444385) {
                        throw new IllegalStateException();
                     }

                     if (var7.field5023[var36 - 1] != null && var7.field5048 != null) {
                        if (var3 <= 952444385) {
                           throw new IllegalStateException();
                        }

                        if (var7.field5048[var36 - 1] != null) {
                           if (var3 <= 952444385) {
                              throw new IllegalStateException();
                           }

                           if (var6 >= 1) {
                              if (var3 <= 952444385) {
                                 throw new IllegalStateException();
                              }

                              if (var6 <= var7.field5048[var36 - 1].length) {
                                 if (var3 <= 952444385) {
                                    throw new IllegalStateException();
                                 }

                                 var8 = var7.field5048[var36 - 1][var6 - 1];
                              }
                           }
                        }
                     }
                  }
               }

               Object[] var43 = classBB.field328;
               int var47 = (classBB.field338 += 1222866659) * 1375698635 - 1;
               String var50;
               if (var8 != null) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  var50 = var8;
               } else {
                  var50 = "";
               }

               var43[var47] = var50;
               return 1;
            }
         } else if (4203 == var0) {
            int var25 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classOB.method8329(var25, (byte)66).field5041 * 665046345;
            return 1;
         } else if (var0 == 4204) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var24 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               int[] var42 = classBB.field323;
               int var46 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var49;
               if (classOB.method8329(var24, (byte)42).field5053 * 1633663665 == 1) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  var49 = 1;
               } else {
                  var49 = 0;
               }

               var42[var46] = var49;
               return 1;
            }
         } else if (4205 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var23 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               ItemComposition var35 = classOB.method8329(var23, (byte)1);
               if (var35.field5049 * 1236138331 == -1) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  if (1357747305 * var35.field5056 >= 0) {
                     if (var3 <= 952444385) {
                        throw new IllegalStateException();
                     }

                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1357747305 * var35.field5056;
                     return 1;
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var23;
               return 1;
            }
         } else if (4206 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var22 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               ItemComposition var34 = classOB.method8329(var22, (byte)-5);
               if (1236138331 * var34.field5049 >= 0) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  if (var34.field5056 * 1357747305 >= 0) {
                     if (var3 <= 952444385) {
                        throw new IllegalStateException();
                     }

                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1357747305 * var34.field5056;
                     return 1;
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var22;
               return 1;
            }
         } else if (4207 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var21 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               int[] var41 = classBB.field323;
               int var45 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var48;
               if (classOB.method8329(var21, (byte)-15).field5014) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  var48 = 1;
               } else {
                  var48 = 0;
               }

               var41[var45] = var48;
               return 1;
            }
         } else if (var0 == 4208) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var20 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               ItemComposition var33 = classOB.method8329(var20, (byte)73);
               if (-1 == var33.field5063 * -2023616411) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  if (1197673963 * var33.field5011 >= 0) {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var33.field5011 * 1197673963;
                     return 1;
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var20;
               return 1;
            }
         } else if (var0 == 4209) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var19 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               ItemComposition var32 = classOB.method8329(var19, (byte)68);
               if (var32.field5063 * -2023616411 >= 0) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  if (var32.field5011 * 1197673963 >= 0) {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1197673963 * var32.field5011;
                     return 1;
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var19;
               return 1;
            }
         } else if (4210 == var0) {
            String var18 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
            int var31 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            boolean var44;
            if (var31 == 1) {
               if (var3 <= 952444385) {
                  throw new IllegalStateException();
               }

               var44 = true;
            } else {
               var44 = false;
            }

            classOG.method8468(var18, var44, (short)9757);
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 807205665 * World.field1164;
            return 1;
         } else if (4211 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               if (classDI.field1514 != null) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  if (509024185 * classMI.field4508 < World.field1164 * 807205665) {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classDI.field1514[(classMI.field4508 += 814857865) * 509024185
                           - 1]
                        & '\uffff';
                     return 1;
                  }

                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               return 1;
            }
         } else if (4212 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               classMI.field4508 = 0;
               return 1;
            }
         } else if (var0 == 4213) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var17 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               int var30 = classOB.method8329(var17, (byte)37).method8749((byte)25);
               if (-1 == var30) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var30;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1 + var30;
               }

               return 1;
            }
         } else if (var0 == 4214) {
            int var16 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classOB.method8329(var16, (byte)-55).field5042 * 1481543581;
            return 1;
         } else if (var0 == 4215) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var15 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classOB.method8329(var15, (byte)30).field5008 * -1462570117;
               return 1;
            }
         } else if (4216 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var14 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classOB.method8329(var14, (byte)55).field5019 * -1681179861;
               return 1;
            }
         } else if (4217 == var0) {
            int var13 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            ItemComposition var5 = classOB.method8329(var13, (byte)69);
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -692383701 * var5.field5009;
            return 1;
         } else if (var0 == 4218) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var12 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classOB.method8329(var12, (byte)-26).field5031;
               return 1;
            }
         } else if (4219 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var11 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classOB.method8329(var11, (byte)77).field5047 * -807855685;
               return 1;
            }
         } else if (var0 == 4220) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var10 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               int[] var10000 = classBB.field323;
               int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var10002;
               if (classOB.method8329(var10, (byte)-64).method8744(2049644975)) {
                  if (var3 <= 952444385) {
                     throw new IllegalStateException();
                  }

                  var10002 = 1;
               } else {
                  var10002 = 0;
               }

               var10000[var10001] = var10002;
               return 1;
            }
         } else if (var0 == 4223) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               return 1;
            }
         } else if (4224 == var0) {
            if (var3 <= 952444385) {
               throw new IllegalStateException();
            } else {
               int var4;
               label421: {
                  var4 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                  if (var4 >= 0) {
                     if (var3 <= 952444385) {
                        throw new IllegalStateException();
                     }

                     if (var4 <= ChatLineBuffer.method1257(525803676)) {
                        break label421;
                     }

                     if (var3 <= 952444385) {
                        throw new IllegalStateException();
                     }
                  }

                  var4 = -1;
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4;
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ki.be(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;Ljava/lang/Object;)Z")
   @ObfuscatedName("yo")
   public static boolean method6525(classNV var0, Object var1) {
      return var0 == null ? var0.method8258(var1) : var0.method8240((Node)var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lte;)I")
   @ObfuscatedName("vc")
   public static int method6523(classTE var0) {
      return var0 == null ? var0.method10799() : var0.field6129 * -1943548931;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field3592 * 177689655;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ki.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field3592 * 177689655;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldj;)V")
   @ObfuscatedName("xi")
   public static void method6522(classDJ var0) {
      classNV.method8193(var0.field1520);
      var0.field1519 = 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field3592 * 177689655;
   }

   classKI(int var1) {
      this.field3592 = var1 * -1918590585;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field3592 * 177689655;
   }
}
