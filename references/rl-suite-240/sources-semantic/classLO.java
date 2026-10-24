import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lo")
public class classLO {
   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("az")
   static int method7178(int var0, byte var1) {
      try {
         return classGV.field2703[var0 & 2047];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lo.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llr;)V")
   @ObfuscatedName("ab")
   public static void method7172(classLR var0) {
      classLF.field4131 = var0;
   }

   classLO() throws Throwable {
      throw new Error();
   }

   @ObfuscatedSignature(descriptor = "(Ldz;B)V")
   @ObfuscatedName("ct")
   static void method7179(WorldView var0, byte var1) {
      try {
         if (null != classIY.field3096 && 1272389989 * client.field839 > 0) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            int var2 = -1017414016 * client.field989;
            classCI.method1503(var0, var2, classIY.field3096, (byte)94);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lo.ct(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laao;)Ljava/lang/String;")
   @ObfuscatedName("oi")
   public static String method7175(classAAO var0) {
      return var0.field78;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/String;")
   @ObfuscatedName("ax")
   public static String method7176(classXY var0) {
      String var1;
      try {
         int var2 = var0.method13150((short)25770);
         if (var2 > 825621970) {
            var2 = 534556402;
         }

         byte[] var3 = new byte[var2];
         var0.field6953 = var0.field6953 + classLF.field4131.method7192(var0.field6952, -1185599464 * var0.field6953, var3, 0, var2, 989641049) * -1095856699;
         String var4 = classKQ.method6590(var3, 0, var2, (byte)-106);
         var1 = var4;
      } catch (Exception var5) {
         var1 = "Cabbage";
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;Ljava/lang/String;)I")
   @ObfuscatedName("ag")
   public static int method7173(classXY var0, String var1) {
      int var2 = var0.field6953 * 702114061;
      byte[] var3 = classVX.method12333(var1, -1987071451);
      var0.method13084(var3.length, 1699258130);
      var0.field6953 = var0.field6953 + classLF.field4131.method7190(var3, 0, var3.length, var0.field6952, var0.field6953 * 702114061, 951974267) * -1095856699;
      return var0.field6953 * 702114061 - var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laae;)Ljava/lang/String;")
   @ObfuscatedName("dg")
   public static String method7174(classAAE var0) {
      return var0.field42;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("bv")
   static int method7180(int var0, Script var1, boolean var2, int var3) {
      try {
         if (var0 == 6200) {
            if (var3 != -1503643048) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               client.field868 = (short)classZL.method14114(classBB.field323[-324749371 * DynamicObject.field1676], -2001374514);
               if (client.field868 <= 0) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field868 = 256;
               }

               client.field869 = (short)classZL.method14114(classBB.field323[1 + DynamicObject.field1676 * -324749371], -2033994976);
               if (client.field869 <= 0) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field869 = 256;
               }

               return 1;
            }
         } else if (6201 == var0) {
            if (var3 != -1503643048) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               client.field870 = (short)classBB.field323[-324749371 * DynamicObject.field1676];
               if (client.field870 <= 0) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field870 = 256;
               }

               client.field863 = (short)classBB.field323[DynamicObject.field1676 * -324749371 + 1];
               if (client.field863 <= 0) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field863 = 320;
               }

               return 1;
            }
         } else if (6202 == var0) {
            if (var3 != -1503643048) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 480164916;
               client.field877 = (short)classBB.field323[DynamicObject.field1676 * -324749371];
               if (client.field877 <= 0) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field877 = 1;
               }

               client.field873 = (short)classBB.field323[DynamicObject.field1676 * -324749371 + 1];
               if (client.field873 <= 0) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field873 = 32767;
               } else if (client.field873 < client.field877) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field873 = client.field877;
               }

               client.field874 = (short)classBB.field323[2 + -324749371 * DynamicObject.field1676];
               if (client.field874 <= 0) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field874 = 1;
               }

               client.field875 = (short)classBB.field323[DynamicObject.field1676 * -324749371 + 3];
               if (client.field875 <= 0) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field875 = 32767;
               } else if (client.field875 < client.field874) {
                  if (var3 != -1503643048) {
                     throw new IllegalStateException();
                  }

                  client.field875 = client.field874;
               }

               return 1;
            }
         } else if (var0 == 6203) {
            if (null != classWK.field6689.field6618) {
               classRL.method10064(
                  0, 0, 1115597881 * classWK.field6689.field6618.field4247, classWK.field6689.field6618.field4257 * 293773697, false, 317527437
               );
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = client.field991 * 27064125;
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1158148203 * client.field983;
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
            }

            return 1;
         } else if (6204 == var0) {
            if (var3 != -1503643048) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = client.field870;
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = client.field863;
               return 1;
            }
         } else if (6205 == var0) {
            if (var3 != -1503643048) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classIK.method6262(client.field868, (byte)-1);
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classIK.method6262(client.field869, (byte)-1);
               return 1;
            }
         } else if (6220 == var0) {
            if (var3 != -1503643048) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               return 1;
            }
         } else if (var0 == 6221) {
            if (var3 != -1503643048) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               return 1;
            }
         } else if (6222 == var0) {
            if (var3 != -1503643048) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 773246731 * classSA.field5895;
               return 1;
            }
         } else if (6223 == var0) {
            if (var3 != -1503643048) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = IntProjection.field1936 * 8379747;
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lo.bv(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/String;")
   @ObfuscatedName("as")
   public static String method7177(classXY var0) {
      String var1;
      try {
         int var2 = var0.method13150((short)18047);
         if (var2 > 32767) {
            var2 = 32767;
         }

         byte[] var3 = new byte[var2];
         var0.field6953 = var0.field6953 + classLF.field4131.method7192(var0.field6952, -1035352745 * var0.field6953, var3, 0, var2, -1937684609) * 295953285;
         String var4 = classKQ.method6590(var3, 0, var2, (byte)-6);
         var1 = var4;
      } catch (Exception var5) {
         var1 = "Cabbage";
      }

      return var1;
   }
}
