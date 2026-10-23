import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cd")
public final class classCD extends Node {
   @ObfuscatedName("aq")
   int field672;
   @ObfuscatedName("ax")
   int field686;
   @ObfuscatedName("ab")
   int field676;
   @ObfuscatedName("ag")
   int field680;
   @ObfuscatedName("ap")
   String[] field688;
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   static final int field681 = 131072;
   @ObfuscatedName("ac")
   int field677;
   @ObfuscatedName("aa")
   int field675;
   @ObfuscatedName("ao")
   int field684;
   @ObfuscatedName("as")
   int field685;
   @ObfuscatedName("aj")
   int field682;
   @ObfuscatedName("ay")
   int field673 = 0;
   @ObfuscatedName("ae")
   int field678;
   @ObfuscatedName("ad")
   int field674;
   @ObfuscatedName("af")
   int field679;
   @ObfuscatedName("al")
   int field683;
   @ToRemove(unused = "true")
   @ObfuscatedName("cg")
   static final int field687 = 89;

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ae")
   boolean method1417(int var1, int var2) {
      try {
         if (this.field688 != null && var1 >= 0) {
            if (var2 <= -839788185) {
               throw new IllegalStateException();
            }

            if (var1 < this.field688.length && this.field688[var1] != null) {
               if (var2 <= -839788185) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cd.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("az")
   void method1409(int var1, byte var2) {
      try {
         this.field674 = var1 * 1378784235;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cd.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcd;I)Ljava/lang/String;")
   @ObfuscatedName("gj")
   public static String method1422(classCD var0, int var1) {
      return var0.field688 != null && var1 >= 0 && var1 < var0.field688.length ? var0.field688[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("ay")
   static int method1429(int var0, Script var1, boolean var2, byte var3) {
      try {
         Widget var4;
         if (var0 >= 2000) {
            if (var3 != 0) {
               throw new IllegalStateException();
            }

            var0 -= 1000;
            int var5 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            var4 = classWK.field6689.method7421(var5, (byte)94);
         } else {
            Widget var10000;
            if (var2) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               }

               var10000 = classSZ.field6108;
            } else {
               var10000 = TileItem.field1556;
            }

            var4 = var10000;
         }

         if (var0 == 1100) {
            DynamicObject.field1676 -= 240082458;
            int var42 = classBB.field323[DynamicObject.field1676 * -324749371];
            int var50 = classBB.field323[1 + -324749371 * DynamicObject.field1676];
            if (12 == -1510882375 * var4.field4373) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               }

               classLH var51 = var4.method7334(-1371767503);
               if (null != var51) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  if (var51.method6884(var42, var50, 135170137)) {
                  }
               }
            } else {
               var4.field4339 = var42 * 731382367;
               if (-34809441 * var4.field4339 > var4.field4256 * -842862191 - var4.field4247 * 1115597881) {
                  var4.field4339 = var4.field4256 * -1437415729 - 2134006567 * var4.field4247;
               }

               if (var4.field4339 * -34809441 < 0) {
                  var4.field4339 = 0;
               }

               var4.field4329 = -1148676629 * var50;
               if (var4.field4329 * -41424189 > -714521463 * var4.field4267 - var4.field4257 * 293773697) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var4.field4329 = var4.field4267 * -1471306045 - var4.field4257 * 577918571;
               }

               if (-41424189 * var4.field4329 < 0) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var4.field4329 = 0;
               }
            }

            return 1;
         } else if (1101 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.field4268 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 1590850751;
               return 1;
            }
         } else if (var0 == 1102) {
            boolean var64;
            if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               }

               var64 = true;
            } else {
               var64 = false;
            }

            var4.field4269 = var64;
            return 1;
         } else if (1103 == var0) {
            var4.field4345 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * -2056841747;
            return 1;
         } else if (1104 == var0) {
            var4.field4275 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 327091521;
            return 1;
         } else if (var0 == 1105) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.field4346 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 857041169;
               return 1;
            }
         } else if (var0 == 1106) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.field4349 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * -727683123;
               return 1;
            }
         } else if (1107 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               boolean var63;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var63 = true;
               } else {
                  var63 = false;
               }

               var4.field4280 = var63;
               return 1;
            }
         } else if (1108 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.field4331 = 656328391;
               var4.field4372 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 434741949;
               return 1;
            }
         } else if (1109 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 720247374;
               var4.field4288 = 1981476425 * classBB.field323[-324749371 * DynamicObject.field1676];
               var4.field4289 = 1954863581 * classBB.field323[1 + -324749371 * DynamicObject.field1676];
               var4.field4354 = 1703663435 * classBB.field323[2 + -324749371 * DynamicObject.field1676];
               var4.field4290 = classBB.field323[3 + -324749371 * DynamicObject.field1676] * 993003239;
               var4.field4292 = classBB.field323[-324749371 * DynamicObject.field1676 + 4] * 863028557;
               var4.field4336 = classBB.field323[DynamicObject.field1676 * -324749371 + 5] * 1556726191;
               return 1;
            }
         } else if (1110 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               int var41 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (var4.field4286 * 2114568197 != var41) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var4.field4286 = 742198477 * var41;
                  var4.field4319 = 0;
                  var4.field4325 = 0;
               }

               return 1;
            }
         } else if (var0 == 1111) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               boolean var62;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var62 = true;
               } else {
                  var62 = false;
               }

               var4.field4295 = var62;
               return 1;
            }
         } else if (1112 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               String var39 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               var39 = classUP.method11483(var39, Nameable.method10228(-1119970828), classBB.field334, classBB.field333, (byte)0);
               if (12 == -1510882375 * var4.field4373) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  classLH var49 = var4.method7334(-328935466);
                  if (var49 != null) {
                     if (var3 != 0) {
                        throw new IllegalStateException();
                     }

                     if (var49.method7043((byte)1)) {
                        classLH.method6855(var49, var39, 381707883);
                        return 1;
                     }
                  }

                  var4.field4299 = var39;
               } else if (!var39.equals(var4.field4299)) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var4.field4299 = var39;
               }

               return 1;
            }
         } else if (1113 == var0) {
            var4.field4243 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 590574429;
            if (-1510882375 * var4.field4373 == 12) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               }

               classLH var38 = var4.method7334(-178449929);
               if (null != var38) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  classLH.method6903(var38, -1159491061);
               }
            }

            return 1;
         } else if (var0 == 1114) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 360123687;
               if (12 == var4.field4373 * -1510882375) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  classLH var37 = var4.method7334(-823814283);
                  if (null != var37) {
                     var37.method6889(
                        classBB.field323[DynamicObject.field1676 * -324749371], classBB.field323[1 + -324749371 * DynamicObject.field1676], -981774839
                     );
                     var37.method6890(classBB.field323[DynamicObject.field1676 * -324749371 + 2], 879336967);
                  }
               } else {
                  var4.field4302 = 571804797 * classBB.field323[DynamicObject.field1676 * -324749371];
                  var4.field4303 = -2106140265 * classBB.field323[-324749371 * DynamicObject.field1676 + 1];
                  var4.field4301 = classBB.field323[2 + DynamicObject.field1676 * -324749371] * -763080175;
               }

               return 1;
            }
         } else if (1115 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               boolean var61;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var61 = true;
               } else {
                  var61 = false;
               }

               var4.field4304 = var61;
               return 1;
            }
         } else if (var0 == 1116) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.field4281 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * -1993421947;
               return 1;
            }
         } else if (var0 == 1117) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.field4282 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 37017731;
               return 1;
            }
         } else if (1118 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               boolean var60;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var60 = true;
               } else {
                  var60 = false;
               }

               var4.field4350 = var60;
               return 1;
            }
         } else if (var0 == 1119) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               boolean var59;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var59 = true;
               } else {
                  var59 = false;
               }

               var4.field4254 = var59;
               return 1;
            }
         } else if (1120 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               var4.field4256 = -803344527 * classBB.field323[DynamicObject.field1676 * -324749371];
               var4.field4267 = -251301447 * classBB.field323[-324749371 * DynamicObject.field1676 + 1];
               if (0 == var4.field4373 * -1510882375) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  Widget[] var58;
                  if (-1 == var4.field4258 * 63131743) {
                     if (var3 != 0) {
                        throw new IllegalStateException();
                     }

                     var58 = classWK.field6689.field4438[-1163656551 * var4.field4327 >> 16];
                  } else {
                     var58 = classWK.field6689.method7421(var4.field4340 * -1502647899, (byte)125).field4338;
                  }

                  Widget[] var36 = var58;
                  classFC.method4609(var36, var4, false, classWK.field6689, client.field824, -62120686);
               }

               return 1;
            }
         } else if (var0 == 1121) {
            classUL.method11465(-1163656551 * var4.field4327, var4.field4258 * 63131743, 1283248075);
            classWK.field6689.method12238(var4, 1200783607);
            return 1;
         } else if (var0 == 1122) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.field4347 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 1515656587;
               return 1;
            }
         } else if (var0 == 1123) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.field4293 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * -1914223631;
               return 1;
            }
         } else if (var0 == 1124) {
            var4.field4274 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 61705535;
            return 1;
         } else if (var0 == 1125) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               int var35 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classYE var48 = (classYE)classOF.method8436(classCX.method2696(751400922), var35, 474920566);
               if (var48 != null) {
                  var4.field4305 = var48;
               }

               return 1;
            }
         } else if (var0 == 1126) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               boolean var57;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var57 = true;
               } else {
                  var57 = false;
               }

               boolean var34 = var57;
               var4.field4276 = var34;
               return 1;
            }
         } else if (1127 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               boolean var56;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var56 = true;
               } else {
                  var56 = false;
               }

               boolean var33 = var56;
               var4.field4296 = var33;
               return 1;
            }
         } else if (var0 == 1129) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.field4337 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               return 1;
            }
         } else if (var0 == 1130) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               Widget.method7278(
                  var4, (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635], "", classAV.field294, classBF.method1044(-1013173859)
               );
               return 1;
            }
         } else if (1131 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               var4.method7289(classBB.field323[DynamicObject.field1676 * -324749371], classBB.field323[DynamicObject.field1676 * -324749371 + 1], -1844055709);
               return 1;
            }
         } else if (1132 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               Widget.method7292(
                  var4,
                  (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635],
                  classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371],
                  (byte)61
               );
               return 1;
            }
         } else if (1133 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               classLI var32 = var4.method7337(1601124651);
               if (var32 != null) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var32.field4170 = 1594234983 * classBB.field323[DynamicObject.field1676 * -324749371];
               }

               return 1;
            }
         } else if (1134 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               classLI var31 = var4.method7337(553208368);
               if (null != var31) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var31.field4168 = classBB.field323[DynamicObject.field1676 * -324749371] * 27298427;
               }

               return 1;
            }
         } else if (var0 == 1135) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               classBB.field338 -= 1222866659;
               classLH var30 = var4.method7334(-1044537720);
               if (var30 != null) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  String var46 = (String)classBB.field328[classBB.field338 * 1375698635];
                  var46 = classUP.method11483(var46, Nameable.method10228(-518504381), classBB.field334, classBB.field333, (byte)0);
                  var4.field4300 = var46;
               }

               return 1;
            }
         } else if (var0 == 1136) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               classLI var29 = var4.method7337(1538856326);
               if (null != var29) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var29.field4167 = classBB.field323[DynamicObject.field1676 * -324749371] * 798448585;
               }

               return 1;
            }
         } else if (1137 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               classLH var28 = var4.method7334(-2005700341);
               if (var28 != null) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  if (var28.method6869(classBB.field323[DynamicObject.field1676 * -324749371], -133076799) && var3 != 0) {
                     throw new IllegalStateException();
                  }
               }

               return 1;
            }
         } else if (1138 == var0) {
            DynamicObject.field1676 -= 120041229;
            classLH var27 = var4.method7334(-392838421);
            if (var27 != null) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               }

               if (var27.method6874(classBB.field323[-324749371 * DynamicObject.field1676], (byte)1)) {
               }
            }

            return 1;
         } else if (1139 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               classLH var26 = var4.method7334(-729594808);
               if (null != var26) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  if (classLH.method6877(var26, classBB.field323[DynamicObject.field1676 * -324749371], (byte)-35)) {
                  }
               }

               return 1;
            }
         } else if (1140 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               boolean var55;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var55 = true;
               } else {
                  var55 = false;
               }

               boolean var25 = var55;
               client.field909.method4605((byte)26);
               classLH var45 = var4.method7334(-1108914295);
               if (var45 != null && var45.method6847(var25, 1451056731)) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  if (var25) {
                     if (var3 != 0) {
                        throw new IllegalStateException();
                     }

                     client.field909.method4600(var4, (byte)-56);
                  }
               }

               return 1;
            }
         } else if (1141 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               boolean var54;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var54 = true;
               } else {
                  var54 = false;
               }

               boolean var24 = var54;
               if (!var24) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  if (client.field909.method4595(2041398062) == var4) {
                     if (var3 != 0) {
                        throw new IllegalStateException();
                     }

                     client.field909.method4605((byte)47);
                  }
               }

               classLH var44 = var4.method7334(-29116185);
               if (null != var44) {
                  var44.method6848(var24, -701629532);
               }

               return 1;
            }
         } else if (1142 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               classLH var23 = var4.method7334(-1957811422);
               if (null != var23) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  if (var23.method6930(
                        classBB.field323[DynamicObject.field1676 * -324749371], classBB.field323[DynamicObject.field1676 * -324749371 + 1], 1122842785
                     )
                     && var3 != 0) {
                     throw new IllegalStateException();
                  }
               }

               return 1;
            }
         } else if (1143 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               classLH var22 = var4.method7334(-118543802);
               if (var22 != null) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  if (var22.method6930(
                        classBB.field323[DynamicObject.field1676 * -324749371], classBB.field323[DynamicObject.field1676 * -324749371], 1122842785
                     )
                     && var3 != 0) {
                     throw new IllegalStateException();
                  }
               }

               return 1;
            }
         } else if (1144 == var0) {
            DynamicObject.field1676 -= 120041229;
            classLH var21 = var4.method7334(-1531097886);
            if (null != var21) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               }

               classLH.method7072(var21, classBB.field323[-324749371 * DynamicObject.field1676], (short)248);
            }

            return 1;
         } else if (1145 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               classLH var20 = var4.method7334(-529353642);
               if (var20 != null) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var20.method7060(classBB.field323[-324749371 * DynamicObject.field1676], (byte)0);
               }

               return 1;
            }
         } else if (var0 == 1146) {
            DynamicObject.field1676 -= 120041229;
            classLH var19 = var4.method7334(-763924149);
            if (var19 != null) {
               var19.method6895(classBB.field323[DynamicObject.field1676 * -324749371], 1201353478);
            }

            return 1;
         } else if (1147 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               classLH var18 = var4.method7334(-1119143627);
               if (null != var18) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var18.method7084(classBB.field323[-324749371 * DynamicObject.field1676], 2117141590);
               }

               return 1;
            }
         } else if (1148 == var0) {
            DynamicObject.field1676 -= 240082458;
            classTT var17 = var4.method7338(1289741507);
            if (var17 != null) {
               classTT.method11189(
                  var17, classBB.field323[DynamicObject.field1676 * -324749371], classBB.field323[-324749371 * DynamicObject.field1676 + 1], 1658283540
               );
            }

            return 1;
         } else if (var0 == 1149) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               classTT var16 = var4.method7338(1289741507);
               if (null != var16) {
                  var16.method11191(
                     (char)classBB.field323[DynamicObject.field1676 * -324749371], classBB.field323[1 + -324749371 * DynamicObject.field1676], (byte)67
                  );
               }

               return 1;
            }
         } else if (1150 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               var4.method7283((String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635], classAV.field294, -413942015);
               return 1;
            }
         } else if (1151 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else {
               classBB.field338 -= -626367319;
               String var15 = (String)classBB.field328[1375698635 * classBB.field338];
               String var43 = (String)classBB.field328[1 + classBB.field338 * 1375698635];
               String var7 = (String)classBB.field328[1375698635 * classBB.field338 + 2];
               long var8 = classBF.method1044(1371349330);
               long var10 = classKN.method6538(1249938208);
               String var12 = classEA.method3814(-1455188761).method12179(classEA.method3814(1494413329).field6571 * 857134555, 1878865176);
               if (var8 != -1L) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var15 = var15.replaceAll("%userid%", Long.toString(var8));
               }

               if (var10 != -1L) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var15 = var15.replaceAll("%userhash%", Long.toString(var10));
               }

               if (!var12.isEmpty()) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var15 = var15.replaceAll("%deviceid%", var12);
               }

               Widget.method7280(var4, var15, var43, var7, var12, Long.toString(var10), classAV.field294, -1916406987);
               return 1;
            }
         } else if (1152 == var0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            } else if (-1510882375 * var4.field4373 == 11) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var52;
                  label833: {
                     classMA var14 = Widget.method7322(var4, -1626603432);
                     if (var14 != null) {
                        if (var3 != 0) {
                           throw new IllegalStateException();
                        }

                        if (var14.method7484((byte)0)) {
                           if (var3 != 0) {
                              throw new IllegalStateException();
                           }

                           if (classHS.method6136(-1634436868).method7764(var14.method7521(-498465286), 337515225)) {
                              if (var3 != 0) {
                                 throw new IllegalStateException();
                              }

                              var52 = true;
                              break label833;
                           }
                        }
                     }

                     var52 = false;
                  }

                  boolean var6 = var52;
                  int[] var53 = classBB.field323;
                  int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
                  byte var10002;
                  if (var6) {
                     if (var3 != 0) {
                        throw new IllegalStateException();
                     }

                     var10002 = 1;
                  } else {
                     var10002 = 0;
                  }

                  var53[var10001] = var10002;
                  return 1;
               }
            } else {
               throw new RuntimeException();
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "cd.ay(" + 41);
      }
   }

   classCD() {
      this.field672 = 22663797;
      this.field674 = -207361675;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ab")
   boolean method1413(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 >= -427441494) {
               throw new IllegalStateException();
            }

            if (var1 <= 4) {
               if (var2 >= -427441494) {
                  throw new IllegalStateException();
               }

               return 0 != (-1371603773 * this.field674 & 1 << var1);
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cd.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   void method1410(int var1) {
      this.field674 = var1 * 761005750;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcd;I)Z")
   @ObfuscatedName("ea")
   public static boolean method1418(classCD var0, int var1) {
      return var0.field688 != null && var1 >= 0 && var1 < var0.field688.length && var0.field688[var1] != null;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aa")
   boolean method1414(int var1) {
      return var1 >= 0 && var1 <= 4 ? 0 != (-1995553998 * this.field674 & 1 << var1) : true;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ao")
   boolean method1415(int var1) {
      return var1 >= 0 && var1 <= 4 ? 0 != (-1371603773 * this.field674 & 1 << var1) : true;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("al")
   boolean method1416(int var1) {
      return var1 >= 0 && var1 <= 4 ? 0 != (-1371603773 * this.field674 & 1 << var1) : true;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aj")
   boolean method1419(int var1) {
      return this.field688 != null && var1 >= 0 && var1 < this.field688.length && this.field688[var1] != null;
   }

   @ObfuscatedSignature(descriptor = "(II)Ljava/lang/String;")
   @ObfuscatedName("ag")
   String method1423(int var1, int var2) {
      try {
         if (this.field688 != null) {
            if (var2 == -209761696) {
               throw new IllegalStateException();
            }

            if (var1 >= 0) {
               if (var2 == -209761696) {
                  throw new IllegalStateException();
               }

               if (var1 < this.field688.length) {
                  if (var2 == -209761696) {
                     throw new IllegalStateException();
                  }

                  return this.field688[var1];
               }
            }
         }

         return null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cd.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ap")
   String method1424(int var1) {
      return this.field688 != null && var1 >= 0 && var1 < this.field688.length ? this.field688[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("au")
   String method1425(int var1) {
      return this.field688 != null && var1 >= 0 && var1 < this.field688.length ? this.field688[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ai")
   String method1426(int var1) {
      return this.field688 != null && var1 >= 0 && var1 < this.field688.length ? this.field688[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)Ljava/lang/Class;")
   @ObfuscatedName("ab")
   static Class method1428(String var0, byte var1) throws ClassNotFoundException {
      try {
         if (var0.equals("B")) {
            if (var1 == 63) {
               throw new IllegalStateException();
            } else {
               return byte.class;
            }
         } else if (var0.equals("I")) {
            return int.class;
         } else if (var0.equals("S")) {
            if (var1 == 63) {
               throw new IllegalStateException();
            } else {
               return short.class;
            }
         } else if (var0.equals("J")) {
            if (var1 == 63) {
               throw new IllegalStateException();
            } else {
               return long.class;
            }
         } else if (var0.equals("Z")) {
            if (var1 == 63) {
               throw new IllegalStateException();
            } else {
               return boolean.class;
            }
         } else if (var0.equals("F")) {
            if (var1 == 63) {
               throw new IllegalStateException();
            } else {
               return float.class;
            }
         } else if (var0.equals("D")) {
            if (var1 == 63) {
               throw new IllegalStateException();
            } else {
               return double.class;
            }
         } else if (var0.equals("C")) {
            if (var1 == 63) {
               throw new IllegalStateException();
            } else {
               return char.class;
            }
         } else {
            return var0.equals("void") ? void.class : Class.forName(var0);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cd.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("xz")
   public boolean method1430(int var1) {
      return this.method1413(var1, -2035539133);
   }

   @ObfuscatedSignature(descriptor = "(Llw;IILvv;Lqn;I)V")
   @ObfuscatedName("as")
   public static void method1427(Widget var0, int var1, int var2, classVV var3, classQN var4, int var5) {
      try {
         Widget var10000;
         if (-1502647899 * var0.field4340 == -1) {
            if (var5 >= 1362128893) {
               throw new IllegalStateException();
            }

            var10000 = null;
         } else {
            var10000 = var3.method7422(var0.field4340 * -1502647899, 653784551 * var0.field4261, (byte)-81);
         }

         Widget var8 = var10000;
         int var6;
         int var7;
         if (null == var8) {
            var6 = var1;
            var7 = var2;
         } else {
            var6 = 1115597881 * var8.field4247;
            var7 = 293773697 * var8.field4257;
         }

         classPF.method8890(var0, var6, var7, false, var3, var4, (short)24336);
         classMP.method7680(var0, var6, var7, (short)3711);
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "cd.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("mb")
   public String method1431(int var1) {
      return this.method1423(var1, -1148961647);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcd;I)Z")
   @ObfuscatedName("ix")
   public static boolean method1420(classCD var0, int var1) {
      return var0.field688 != null && var1 >= 0 && var1 < var0.field688.length && var0.field688[var1] != null;
   }

   @ObfuscatedSignature(descriptor = "(IS)I")
   @ObfuscatedName("af")
   public static int method1408(int var0, short var1) {
      try {
         return var0 >> 17 & 7;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cd.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcd;[Ljava/lang/String;I)V")
   @ObfuscatedName("ey")
   public static void method1411(classCD var0, String[] var1, int var2) {
      if (var0 == null) {
         var0.method1412(var1, var2);
      } else {
         try {
            var0.field688 = var1;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "cd.af(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lx")
   public int method1432() {
      return this.field672 * -356773341;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aq")
   boolean method1421(int var1) {
      return this.field688 != null && var1 >= 0 && var1 < this.field688.length && this.field688[var1] != null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("sb")
   public void method1433(int var1) {
      this.field673 = var1 * 1512991639;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;I)V")
   @ObfuscatedName("af")
   void method1412(String[] var1, int var2) {
      try {
         this.field688 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cd.af(" + ')');
      }
   }
}
