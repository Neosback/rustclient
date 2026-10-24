import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fs")
public class classFS extends classVJ {
   @ObfuscatedSignature(descriptor = "[Let;")
   @ObfuscatedName("az")
   classET[] field2390;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   public static final int field2391 = 72;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2392 = 12;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;I)Lfs;")
   @ObfuscatedName("ae")
   public static classFS method4992(IndexDataBase var0, IndexDataBase var1, int var2) {
      boolean var3 = true;
      int var4 = -1;
      int[] var5 = var0.method11898(var2, -1691540534);

      for (int var6 = 0; var6 < var5.length; var6++) {
         byte[] var7 = var0.method11937(var2, var5[var6], 1540366080);
         if (var7 == null) {
            var3 = false;
         } else if (var4 == -1) {
            var4 = (var7[0] & 255) << 8 | var7[1] & 255;
         }
      }

      if (var4 != -1) {
         byte[] var9 = var1.method11937(var4, 0, 1375257937);
         if (var9 == null) {
            var3 = false;
         }
      } else {
         var3 = false;
      }

      if (!var3) {
         return null;
      } else {
         try {
            return new classFS(var0, var1, var2);
         } catch (Exception var8) {
            return null;
         }
      }
   }

   classFS(IndexDataBase var1, IndexDataBase var2, int var3) {
      int var4 = var1.method11943(var3, -82193186);
      this.field2390 = new classET[var4];
      classEM var5 = null;
      int[] var6 = var1.method11898(var3, -1864891576);

      for (int var7 = 0; var7 < var6.length; var7++) {
         byte[] var8 = var1.method11942(var3, var6[var7], -1921665201);
         if (var5 == null) {
            int var9 = (var8[0] & 255) << 8 | var8[1] & 255;
            byte[] var10 = var2.method11937(var9, 0, -94514870);
            var5 = new classEM(var9, var10);
         }

         this.field2390[var6[var7]] = new classET(var8, var5);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;I)Lfs;")
   @ObfuscatedName("ab")
   public static classFS method4993(IndexDataBase var0, IndexDataBase var1, int var2) {
      boolean var3 = true;
      int var4 = -1;
      int[] var5 = var0.method11898(var2, -1790781682);

      for (int var6 = 0; var6 < var5.length; var6++) {
         byte[] var7 = var0.method11937(var2, var5[var6], 1734752706);
         if (var7 == null) {
            var3 = false;
         } else if (var4 == -1) {
            var4 = (var7[0] & 255) << 8 | var7[1] & 255;
         }
      }

      if (var4 != -1) {
         byte[] var9 = var1.method11937(var4, 0, 446516911);
         if (var9 == null) {
            var3 = false;
         }
      } else {
         var3 = false;
      }

      if (!var3) {
         return null;
      } else {
         try {
            return new classFS(var0, var1, var2);
         } catch (Exception var8) {
            return null;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;)I")
   @ObfuscatedName("vt")
   public static int method5001(Actor var0) {
      return 635707271 * var0.field1461.field630;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ag")
   public boolean method4994(int var1) {
      return this.field2390[var1].field1908;
   }

   @ObfuscatedSignature(descriptor = "(I)Lfs;")
   @ObfuscatedName("lv")
   public static classFS method5003(int var0) {
      return classCN.method2547(var0, 1275003202);
   }

   @ObfuscatedSignature(descriptor = "(Lfs;IB)Z")
   @ObfuscatedName("ea")
   public static boolean method4995(classFS var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method4997(var1, var2);
      }

      try {
         return var0.field2390[var1].field1908;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fs.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ax")
   public boolean method4996(int var1) {
      return this.field2390[var1].field1908;
   }

   @ObfuscatedSignature(descriptor = "(B)[Lyz;")
   @ObfuscatedName("ac")
   public static IndexedSprite[] method4999(byte var0) {
      try {
         IndexedSprite[] var1 = new IndexedSprite[classYL.field6999 * 1616155503];

         for (int var2 = 0; var2 < classYL.field6999 * 1616155503; var2++) {
            if (var0 == 0) {
               throw new IllegalStateException();
            }

            IndexedSprite var3 = var1[var2] = new IndexedSprite();
            var3.field7122 = classYL.field6998 * -1073038619;
            var3.field7118 = -1062567517 * classYL.field6997;
            var3.field7120 = classYL.field7000[var2];
            var3.field7121 = classYL.field7001[var2];
            var3.field7119 = EntityOps.field5369[var2];
            var3.field7117 = classGG.field2576[var2];
            var3.field7124 = classRB.field5637;
            var3.field7123 = classYL.field7002[var2];
         }

         classVZ.method12347((byte)-99);
         return var1;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "fs.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lyn;ILjava/lang/String;B)Ljava/lang/String;")
   @ObfuscatedName("ab")
   static String method4998(IterableHashTable var0, int var1, String var2, byte var3) {
      try {
         if (null == var0) {
            if (var3 <= 9) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            classVE var4 = (classVE)var0.method13637(var1);
            if (null == var4) {
               if (var3 <= 9) {
                  throw new IllegalStateException();
               } else {
                  return var2;
               }
            } else {
               return (String)var4.field6496;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "fs.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("rz")
   public boolean method5004(int var1) {
      return method4995(this, var1, (byte)75);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   public static void method5000(int var0) {
      try {
         NPCComposition.field5334.method6468();
         NPCComposition.field5335.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "fs.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("cy")
   static int method5002(int var0, Script var1, boolean var2, byte var3) {
      try {
         if (var0 == 8501) {
            if (var3 >= 3) {
               throw new IllegalStateException();
            } else {
               int[] var20;
               int var22;
               byte var24;
               label135: {
                  classXG var12 = classXH.method12840(classOE.field4841.field791, classBB.field322[(classXT.field6937 -= -1297854163) * 18294437]);
                  int var14 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                  var20 = classBB.field323;
                  var22 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
                  if (var12 != null) {
                     if (var3 >= 3) {
                        throw new IllegalStateException();
                     }

                     if (var14 == var12.field6861 * -38539607) {
                        if (var3 >= 3) {
                           throw new IllegalStateException();
                        }

                        var24 = 1;
                        break label135;
                     }
                  }

                  var24 = 0;
               }

               var20[var22] = var24;
               return 1;
            }
         } else if (var0 == 8520) {
            if (var3 >= 3) {
               throw new IllegalStateException();
            } else {
               classXG var11 = classOE.field4841.field791.method12836(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371], -112924041);
               long[] var19 = classBB.field322;
               int var21 = (classXT.field6937 += -1297854163) * 18294437 - 1;
               long var23;
               if (var11 != null) {
                  if (var3 >= 3) {
                     throw new IllegalStateException();
                  }

                  var23 = -5931402327633732889L * var11.field6863;
               } else {
                  var23 = -1L;
               }

               var19[var21] = var23;
               return 1;
            }
         } else if (var0 == 8521) {
            classXG var10 = classXH.method12840(classOE.field4841.field791, classBB.field322[(classXT.field6937 -= -1297854163) * 18294437]);
            int[] var18 = classBB.field323;
            int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
            int var10002;
            if (null != var10) {
               if (var3 >= 3) {
                  throw new IllegalStateException();
               }

               var10002 = var10.field6860 * 1743701503;
            } else {
               var10002 = -1;
            }

            var18[var10001] = var10002;
            return 1;
         } else if (var0 == 8560) {
            if (var3 >= 3) {
               throw new IllegalStateException();
            } else {
               int var9 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classXG var13 = classXH.method12840(classOE.field4841.field791, classBB.field322[(classXT.field6937 -= -1297854163) * 18294437]);
               if (null == var13) {
                  if (var3 >= 3) {
                     throw new IllegalStateException();
                  }

                  classPC var16 = WorldView.method3733(classIZ.method6383(var9, (byte)108), 805125283);
                  classBD.method996(classBW.method1266(var9, (byte)-82), var16.field5214[classCF.method1456(var9, (byte)20)], (byte)125);
               } else {
                  if (var13.field6861 * -38539607 != classIZ.method6383(var9, (byte)76)) {
                     throw new RuntimeException("");
                  }

                  if (var3 >= 3) {
                     throw new IllegalStateException();
                  }

                  classBD.method996(classBW.method1266(var9, (byte)-36), var13.field6859[classCF.method1456(var9, (byte)13)], (byte)106);
               }

               return 1;
            }
         } else if (8570 == var0) {
            if (var3 >= 3) {
               throw new IllegalStateException();
            } else {
               int var4 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classXG var5 = classXH.method12840(classOE.field4841.field791, classBB.field322[(classXT.field6937 -= -1297854163) * 18294437]);
               if (var5 == null) {
                  if (var3 >= 3) {
                     throw new IllegalStateException();
                  }

                  classPC var6 = WorldView.method3733(classIZ.method6383(var4, (byte)124), -2100207088);
                  Object[] var10000;
                  if (classJW.method6452(var4, (byte)-75) == classOG.field4862) {
                     if (var3 >= 3) {
                        throw new IllegalStateException();
                     }

                     var10000 = var6.field5216;
                  } else {
                     var10000 = var6.field5215;
                  }

                  Object[] var7 = var10000;
                  classBD.method996(classBW.method1266(var4, (byte)-66), var7[classCF.method1456(var4, (byte)-19)], (byte)118);
               } else {
                  if (var5.field6861 * -38539607 != classIZ.method6383(var4, (byte)36)) {
                     throw new RuntimeException("");
                  }

                  if (var3 >= 3) {
                     throw new IllegalStateException();
                  }

                  Object[] var17;
                  if (classJW.method6452(var4, (byte)-27) == classOG.field4862) {
                     if (var3 >= 3) {
                        throw new IllegalStateException();
                     }

                     var17 = var5.field6864;
                  } else {
                     var17 = var5.field6862;
                  }

                  Object[] var15 = var17;
                  classBD.method996(classBW.method1266(var4, (byte)-98), var15[classCF.method1456(var4, (byte)-31)], (byte)72);
               }

               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "fs.cy(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("af")
   public boolean method4997(int var1, byte var2) {
      try {
         return this.field2390[var1].field1908;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fs.af(" + ')');
      }
   }
}
