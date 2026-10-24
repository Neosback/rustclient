import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("su")
public class classSU {
   @ObfuscatedName("ar")
   float[] field6065;
   @ObfuscatedName("av")
   float field6054;
   @ObfuscatedName("ai")
   int field6044;
   @ObfuscatedSignature(descriptor = "Lse;")
   @ObfuscatedName("ab")
   classSE field6060;
   @ObfuscatedName("ay")
   float field6056;
   @ObfuscatedName("as")
   boolean field6063;
   @ObfuscatedName("ax")
   float field6055;
   @ObfuscatedName("ac")
   float field6046;
   @ObfuscatedName("ao")
   float field6050;
   @ObfuscatedSignature(descriptor = "[Lsy;")
   @ObfuscatedName("ag")
   classSY[] field6066;
   @ObfuscatedName("aa")
   float field6045;
   @ObfuscatedName("aj")
   float field6048;
   @ObfuscatedSignature(descriptor = "Lse;")
   @ObfuscatedName("ae")
   classSE field6061;
   @ObfuscatedName("aq")
   float field6052;
   @ObfuscatedName("ad")
   float field6047;
   @ObfuscatedName("ap")
   float field6051;
   @ObfuscatedName("au")
   boolean field6043 = true;
   @ObfuscatedName("af")
   boolean field6062;
   @ObfuscatedName("al")
   float field6049;
   @ObfuscatedName("aw")
   int field6058;
   @ObfuscatedName("ak")
   int field6057;
   @ObfuscatedName("az")
   boolean field6064;
   @ObfuscatedName("at")
   float field6053;
   @ToRemove(unused = "true")
   @ObfuscatedName("bo")
   static final int field6059 = 43;

   @ObfuscatedSignature(descriptor = "(F)Lsy;")
   @ObfuscatedName("bj")
   classSY method10730(float var1) {
      int var2 = this.method10726(var1, 1056549537);
      return var2 >= 0 && var2 < this.field6066.length ? this.field6066[var2] : null;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)I")
   @ObfuscatedName("az")
   int method10702(classXY var1, int var2, int var3) {
      try {
         int var4 = var1.method13099(461953608);
         classJR.method6445(var1.method13095(-346779531), -1726225436);
         this.field6061 = classOB.method8328(var1.method13095(-346779531), -47642443);
         this.field6060 = classOB.method8328(var1.method13095(-346779531), -47642443);
         this.field6064 = var1.method13095(-346779531) != 0;
         this.field6066 = new classSY[var4];
         classSY var5 = null;

         for (int var6 = 0; var6 < var4; var6++) {
            classSY var7 = new classSY();
            classSY.method10761(var7, var1, var2, -968542288);
            this.field6066[var6] = var7;
            if (null != var5) {
               if (var3 >= -239626205) {
                  throw new IllegalStateException();
               }

               var5.field6102 = var7;
            }

            var5 = var7;
         }

         return var4;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "su.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   void method10706(int var1) {
      try {
         this.field6058 = 1062926601 * this.field6066[0].field6101;
         this.field6057 = this.field6066[this.method10735((byte)-82) - 1].field6101 * -2049065071;
         this.field6065 = new float[this.method10722(1841746750) + 1];

         for (int var2 = method10714(this, (byte)-53); var2 <= method10718(this, 2112203563); var2++) {
            if (var1 == -203474865) {
               throw new IllegalStateException();
            }

            this.field6065[var2 - method10714(this, (byte)6)] = classGL.method5519(this, var2, (byte)127);
         }

         this.field6066 = null;
         this.field6054 = classGL.method5519(this, method10714(this, (byte)38) - 1, (byte)127);
         this.field6053 = classGL.method5519(this, method10718(this, 2112203563) + 1, (byte)127);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "su.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;Lxy;I)I")
   @ObfuscatedName("pb")
   public static int method10703(classSU var0, classXY var1, int var2) {
      if (var0 == null) {
         var0.method10708();
      }

      int var3 = var1.method13099(-199029456);
      classJR.method6445(var1.method13095(-346779531), -2106840990);
      var0.field6061 = classOB.method8328(var1.method13095(-346779531), -47642443);
      var0.field6060 = classOB.method8328(var1.method13095(-346779531), -47642443);
      var0.field6064 = var1.method13095(-346779531) != 0;
      var0.field6066 = new classSY[var3];
      classSY var4 = null;

      for (int var5 = 0; var5 < var3; var5++) {
         classSY var6 = new classSY();
         classSY.method10761(var6, var1, var2, -968542288);
         var0.field6066[var5] = var6;
         if (null != var4) {
            var4.field6102 = var6;
         }

         var4 = var6;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(II)F")
   @ObfuscatedName("ae")
   public float method10709(int var1, int var2) {
      try {
         if (var1 < method10714(this, (byte)29)) {
            if (var2 >= -449100855) {
               throw new IllegalStateException();
            } else {
               return this.field6054;
            }
         } else if (var1 > method10718(this, 2112203563)) {
            if (var2 >= -449100855) {
               throw new IllegalStateException();
            } else {
               return this.field6053;
            }
         } else {
            return this.field6065[var1 - method10714(this, (byte)-11)];
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "su.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(F)I")
   @ObfuscatedName("bm")
   int method10725(float var1) {
      if (this.field6044 * -150645242 < 0
         || !(this.field6066[this.field6044 * -140518255].field6101 * 1826620491 <= var1)
         || null != this.field6066[1675896408 * this.field6044].field6102
            && !(this.field6066[-140518255 * this.field6044].field6102.field6101 * -1024343040 > var1)) {
         if (!(var1 < method10714(this, (byte)-70)) && !(var1 > method10718(this, 2112203563))) {
            int var2 = this.method10735((byte)-58);
            int var3 = -140518255 * this.field6044;
            if (var2 > 0) {
               int var4 = 0;
               int var5 = var2 - 1;

               do {
                  int var6 = var4 + var5 >> 1;
                  if (var1 < 1663788557 * this.field6066[var6].field6101) {
                     if (var1 > -1313790553 * this.field6066[var6 - 1].field6101) {
                        var3 = var6 - 1;
                        break;
                     }

                     var5 = var6 - 1;
                  } else {
                     if (!(var1 > -464025976 * this.field6066[var6].field6101)) {
                        var3 = var6;
                        break;
                     }

                     if (var1 < 1186255148 * this.field6066[var6 + 1].field6101) {
                        var3 = var6;
                        break;
                     }

                     var4 = var6 + 1;
                  }
               } while (var4 <= var5);
            }

            if (-140518255 * this.field6044 != var3) {
               this.field6044 = -1321120036 * var3;
               this.field6043 = true;
            }

            return this.field6044 * -140518255;
         } else {
            return -1;
         }
      } else {
         return this.field6044 * 1244266308;
      }
   }

   @ObfuscatedSignature(descriptor = "(FI)I")
   @ObfuscatedName("ax")
   int method10726(float var1, int var2) {
      try {
         if (this.field6044 * -140518255 >= 0 && this.field6066[this.field6044 * -140518255].field6101 * 1210377865 <= var1) {
            if (var2 != 1056549537) {
               throw new IllegalStateException();
            }

            if (null == this.field6066[-140518255 * this.field6044].field6102) {
               return this.field6044 * -140518255;
            }

            if (this.field6066[-140518255 * this.field6044].field6102.field6101 * 1210377865 > var1) {
               if (var2 != 1056549537) {
                  throw new IllegalStateException();
               }

               return this.field6044 * -140518255;
            }
         }

         if (!(var1 < method10714(this, (byte)15))) {
            if (var2 != 1056549537) {
               throw new IllegalStateException();
            }

            if (!(var1 > method10718(this, 2112203563))) {
               int var3 = this.method10735((byte)-109);
               int var4 = -140518255 * this.field6044;
               if (var3 > 0) {
                  if (var2 != 1056549537) {
                     throw new IllegalStateException();
                  }

                  int var5 = 0;
                  int var6 = var3 - 1;

                  while (true) {
                     int var7 = var5 + var6 >> 1;
                     if (var1 < 1210377865 * this.field6066[var7].field6101) {
                        if (var2 != 1056549537) {
                           throw new IllegalStateException();
                        }

                        if (var1 > 1210377865 * this.field6066[var7 - 1].field6101) {
                           if (var2 != 1056549537) {
                              throw new IllegalStateException();
                           }

                           var4 = var7 - 1;
                           break;
                        }

                        var6 = var7 - 1;
                     } else {
                        if (!(var1 > 1210377865 * this.field6066[var7].field6101)) {
                           var4 = var7;
                           break;
                        }

                        if (var2 != 1056549537) {
                           throw new IllegalStateException();
                        }

                        if (var1 < 1210377865 * this.field6066[var7 + 1].field6101) {
                           var4 = var7;
                           break;
                        }

                        var5 = var7 + 1;
                     }

                     if (var5 > var6) {
                        if (var2 != 1056549537) {
                           throw new IllegalStateException();
                        }
                        break;
                     }
                  }
               }

               if (-140518255 * this.field6044 != var4) {
                  if (var2 != 1056549537) {
                     throw new IllegalStateException();
                  }

                  this.field6044 = 458712177 * var4;
                  this.field6043 = true;
               }

               return this.field6044 * -140518255;
            }

            if (var2 != 1056549537) {
               throw new IllegalStateException();
            }
         }

         return -1;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "su.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(FI)Lsy;")
   @ObfuscatedName("ac")
   classSY method10731(float var1, int var2) {
      try {
         int var3 = this.method10726(var1, 1056549537);
         if (var3 >= 0) {
            if (var2 != 1391304917) {
               throw new IllegalStateException();
            }

            if (var3 < this.field6066.length) {
               if (var2 != 1391304917) {
                  throw new IllegalStateException();
               }

               return this.field6066[var3];
            }
         }

         return null;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "su.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   int method10717() {
      return 1286089593 * this.field6057;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)I")
   @ObfuscatedName("ao")
   int method10704(classXY var1, int var2) {
      int var3 = var1.method13099(439261380);
      classJR.method6445(var1.method13095(-346779531), -121571945);
      this.field6061 = classOB.method8328(var1.method13095(-346779531), -47642443);
      this.field6060 = classOB.method8328(var1.method13095(-346779531), -47642443);
      this.field6064 = var1.method13095(-346779531) != 0;
      this.field6066 = new classSY[var3];
      classSY var4 = null;

      for (int var5 = 0; var5 < var3; var5++) {
         classSY var6 = new classSY();
         classSY.method10761(var6, var1, var2, -968542288);
         this.field6066[var5] = var6;
         if (null != var4) {
            var4.field6102 = var6;
         }

         var4 = var6;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)I")
   @ObfuscatedName("aj")
   int method10705(classXY var1, int var2) {
      int var3 = var1.method13099(1229038560);
      classJR.method6445(var1.method13095(-346779531), -694550972);
      this.field6061 = classOB.method8328(var1.method13095(-346779531), -47642443);
      this.field6060 = classOB.method8328(var1.method13095(-346779531), -47642443);
      this.field6064 = var1.method13095(-346779531) != 0;
      this.field6066 = new classSY[var3];
      classSY var4 = null;

      for (int var5 = 0; var5 < var3; var5++) {
         classSY var6 = new classSY();
         classSY.method10761(var6, var1, var2, -968542288);
         this.field6066[var5] = var6;
         if (null != var4) {
            var4.field6102 = var6;
         }

         var4 = var6;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   void method10707() {
      this.field6058 = 1062926601 * this.field6066[0].field6101;
      this.field6057 = this.field6066[this.method10735((byte)-10) - 1].field6101 * -2049065071;
      this.field6065 = new float[this.method10722(1841746750) + 1];

      for (int var1 = method10714(this, (byte)-78); var1 <= method10718(this, 2112203563); var1++) {
         this.field6065[var1 - method10714(this, (byte)-79)] = classGL.method5519(this, var1, (byte)127);
      }

      this.field6066 = null;
      this.field6054 = classGL.method5519(this, method10714(this, (byte)-30) - 1, (byte)127);
      this.field6053 = classGL.method5519(this, method10718(this, 2112203563) + 1, (byte)127);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;F)Lsy;")
   @ObfuscatedName("vq")
   public static classSY method10732(classSU var0, float var1) {
      if (var0 == null) {
         var0.method10727(var1);
      }

      int var2 = var0.method10726(var1, 1056549537);
      return var2 >= 0 && var2 < var0.field6066.length ? var0.field6066[var2] : null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   void method10708() {
      this.field6058 = 1062926601 * this.field6066[0].field6101;
      this.field6057 = this.field6066[this.method10735((byte)-19) - 1].field6101 * -2049065071;
      this.field6065 = new float[this.method10722(1841746750) + 1];

      for (int var1 = method10714(this, (byte)-76); var1 <= method10718(this, 2112203563); var1++) {
         this.field6065[var1 - method10714(this, (byte)9)] = classGL.method5519(this, var1, (byte)127);
      }

      this.field6066 = null;
      this.field6054 = classGL.method5519(this, method10714(this, (byte)-65) - 1, (byte)127);
      this.field6053 = classGL.method5519(this, method10718(this, 2112203563) + 1, (byte)127);
   }

   @ObfuscatedSignature(descriptor = "(Lsu;I)I")
   @ObfuscatedName("gl")
   public static int method10718(classSU var0, int var1) {
      if (var0 == null) {
         var0.method10721(var1);
      }

      try {
         return 1286089593 * var0.field6057;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "su.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("ap")
   public float method10710(int var1) {
      if (var1 < method10714(this, (byte)8)) {
         return this.field6054;
      } else {
         return var1 > method10718(this, 2112203563) ? this.field6053 : this.field6065[var1 - method10714(this, (byte)-5)];
      }
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("au")
   public float method10711(int var1) {
      if (var1 < method10714(this, (byte)-8)) {
         return this.field6054;
      } else {
         return var1 > method10718(this, 2112203563) ? this.field6053 : this.field6065[var1 - method10714(this, (byte)106)];
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ar")
   int method10713() {
      return this.field6058 * 1541446983;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bu")
   int method10719() {
      return 1286089593 * this.field6057;
   }

   @ObfuscatedSignature(descriptor = "(Lsu;B)I")
   @ObfuscatedName("uz")
   public static int method10714(classSU var0, byte var1) {
      if (var0 == null) {
         return var0.method10716(var1);
      } else {
         try {
            return var0.field6058 * -1666621055;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "su.ab(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bp")
   int method10720() {
      return 1286089593 * this.field6057;
   }

   classSU() {
      this.field6044 = 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   int method10715() {
      return this.field6058 * -1666621055;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bc")
   int method10733() {
      return null == this.field6066 ? 0 : this.field6066.length;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   int method10722(int var1) {
      try {
         return method10718(this, 2112203563) - method10714(this, (byte)37);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "su.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   int method10723() {
      return method10718(this, 2112203563) - method10714(this, (byte)-3);
   }

   @ObfuscatedSignature(descriptor = "(F)I")
   @ObfuscatedName("bt")
   int method10727(float var1) {
      if (this.field6044 * 1960684384 < 0
         || !(this.field6066[this.field6044 * -695120208].field6101 * 1210377865 <= var1)
         || null != this.field6066[-140518255 * this.field6044].field6102
            && !(this.field6066[1809739930 * this.field6044].field6102.field6101 * 1759980431 > var1)) {
         if (!(var1 < method10714(this, (byte)32)) && !(var1 > method10718(this, 2112203563))) {
            int var2 = this.method10735((byte)-10);
            int var3 = -140518255 * this.field6044;
            if (var2 > 0) {
               int var4 = 0;
               int var5 = var2 - 1;

               do {
                  int var6 = var4 + var5 >> 1;
                  if (var1 < 1503302405 * this.field6066[var6].field6101) {
                     if (var1 > -765743235 * this.field6066[var6 - 1].field6101) {
                        var3 = var6 - 1;
                        break;
                     }

                     var5 = var6 - 1;
                  } else {
                     if (!(var1 > 1210377865 * this.field6066[var6].field6101)) {
                        var3 = var6;
                        break;
                     }

                     if (var1 < -719361413 * this.field6066[var6 + 1].field6101) {
                        var3 = var6;
                        break;
                     }

                     var4 = var6 + 1;
                  }
               } while (var4 <= var5);
            }

            if (-645984073 * this.field6044 != var3) {
               this.field6044 = 458712177 * var3;
               this.field6043 = true;
            }

            return this.field6044 * 1377126748;
         } else {
            return -1;
         }
      } else {
         return this.field6044 * -2016388855;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("bh")
   static int method10737(int var0, Script var1, boolean var2, byte var3) {
      try {
         if (6500 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int[] var32 = classBB.field323;
               int var33 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var34;
               if (classKB.method6484(210701286)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  var34 = 1;
               } else {
                  var34 = 0;
               }

               var32[var33] = var34;
               return 1;
            }
         } else if (var0 == 6501) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               World var17 = classIV.method6362(-126674783);
               if (var17 != null) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 851246551 * var17.field1159;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -458552905 * var17.field1158;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var17.field1165;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -288138629 * var17.field1160;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 597852065 * var17.field1157;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var17.field1167;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               }

               return 1;
            }
         } else if (6502 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               World var16 = classSX.method10755(-1953402072);
               if (null != var16) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var16.field1159 * 851246551;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var16.field1158 * -458552905;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var16.field1165;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -288138629 * var16.field1160;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var16.field1157 * 597852065;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var16.field1167;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               }

               return 1;
            }
         } else if (var0 == 6506) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int var15 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               World var24 = null;

               for (int var30 = 0; var30 < World.field1151 * -622036481; var30++) {
                  if (World.field1168[var30].field1159 * 851246551 == var15) {
                     var24 = World.field1168[var30];
                     break;
                  }
               }

               if (var24 != null) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var24.field1159 * 851246551;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -458552905 * var24.field1158;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var24.field1165;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -288138629 * var24.field1160;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 597852065 * var24.field1157;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var24.field1167;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               }

               return 1;
            }
         } else if (6507 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 480164916;
               int var14 = classBB.field323[DynamicObject.field1676 * -324749371];
               boolean var23 = classBB.field323[DynamicObject.field1676 * -324749371 + 1] == 1;
               int var29 = classBB.field323[2 + DynamicObject.field1676 * -324749371];
               boolean var31;
               if (classBB.field323[3 + -324749371 * DynamicObject.field1676] == 1) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  var31 = true;
               } else {
                  var31 = false;
               }

               boolean var7 = var31;
               classRD.method9987(var14, var23, var29, var7, -1753404561);
               return 1;
            }
         } else if (6511 != var0) {
            if (6512 == var0) {
               client.field1004 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1;
               return 1;
            } else if (var0 == 6513) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  DynamicObject.field1676 -= 240082458;
                  int var13 = classBB.field323[-324749371 * DynamicObject.field1676];
                  int var22 = classBB.field323[1 + DynamicObject.field1676 * -324749371];
                  classPN var28 = classNE.method7875(var22, (byte)95);
                  if (var28.method9075((byte)2)) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classOH.method8480(var13, -507551289)
                        .method9008(var22, var28.field5349, -1931341831);
                  } else if (classPN.method9076(var28, (byte)45)) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = classOH.method8480(var13, -621792759)
                        .method9005(var22, 3335646069137558745L * var28.field5352);
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = NPCComposition.method9003(
                        classOH.method8480(var13, -1919145862), var22, var28.field5350 * -1601970641, 199627827
                     );
                  }

                  return 1;
               }
            } else if (var0 == 6514) {
               DynamicObject.field1676 -= 240082458;
               int var12 = classBB.field323[-324749371 * DynamicObject.field1676];
               int var21 = classBB.field323[-324749371 * DynamicObject.field1676 + 1];
               classPN var27 = classNE.method7875(var21, (byte)-109);
               if (var27.method9075((byte)2)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classMU.method7771(var12, 2013377934)
                     .method8596(var21, var27.field5349, 1238797461);
               } else if (classPN.method9076(var27, (byte)94)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = classMU.method7771(var12, 1412304112)
                     .method8591(var21, var27.field5352 * 3335646069137558745L);
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ObjectComposition.method8590(
                     classMU.method7771(var12, 2031921184), var21, -1601970641 * var27.field5350, 238072989
                  );
               }

               return 1;
            } else if (var0 == 6515) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  DynamicObject.field1676 -= 240082458;
                  int var11 = classBB.field323[-324749371 * DynamicObject.field1676];
                  int var20 = classBB.field323[1 + DynamicObject.field1676 * -324749371];
                  classPN var26 = classNE.method7875(var20, (byte)-42);
                  if (var26.method9075((byte)2)) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classOB.method8329(var11, (byte)8)
                        .method8729(var20, var26.field5349, 1410603517);
                  } else if (classPN.method9076(var26, (byte)19)) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = classOB.method8329(var11, (byte)77)
                        .method8726(var20, 3335646069137558745L * var26.field5352);
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ItemComposition.method8721(
                        classOB.method8329(var11, (byte)39), var20, -1601970641 * var26.field5350, 1513363876
                     );
                  }

                  return 1;
               }
            } else if (6516 == var0) {
               DynamicObject.field1676 -= 240082458;
               int var10 = classBB.field323[DynamicObject.field1676 * -324749371];
               int var19 = classBB.field323[1 + DynamicObject.field1676 * -324749371];
               classPN var25 = classNE.method7875(var19, (byte)61);
               if (var25.method9075((byte)2)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = StructComposition.method9219(
                     classUY.method11781(var10, 64572192), var19, var25.field5349, (short)-2821
                  );
               } else if (classPN.method9076(var25, (byte)89)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = classUY.method11781(var10, 64572192)
                     .method9215(var19, 3335646069137558745L * var25.field5352);
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classUY.method11781(var10, 64572192)
                     .method9212(var19, var25.field5350 * -1601970641, (byte)92);
               }

               return 1;
            } else if (6518 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  int[] var10000 = classBB.field323;
                  int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
                  byte var10002;
                  if (client.field891) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var10002 = 1;
                  } else {
                     var10002 = 0;
                  }

                  var10000[var10001] = var10002;
                  return 1;
               }
            } else if (var0 == 6519) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = client.field889 * 461082519;
                  return 1;
               }
            } else if (var0 == 6520) {
               return 1;
            } else if (6521 == var0) {
               return 1;
            } else if (var0 == 6522) {
               classBB.field338 -= 1222866659;
               DynamicObject.field1676 -= 120041229;
               return 1;
            } else if (6523 == var0) {
               classBB.field338 -= 1222866659;
               DynamicObject.field1676 -= 120041229;
               return 1;
            } else if (6524 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                  return 1;
               }
            } else if (6525 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
                  return 1;
               }
            } else if (var0 == 6526) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
                  return 1;
               }
            } else if (6527 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -372108587 * client.field890;
                  return 1;
               }
            } else if (var0 == 6528) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  DynamicObject.field1676 -= 240082458;
                  int var9 = classBB.field323[-324749371 * DynamicObject.field1676];
                  int var18 = classBB.field323[-324749371 * DynamicObject.field1676 + 1];
                  classPN var6 = classNE.method7875(var18, (byte)-61);
                  if (var6.method9075((byte)2)) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classMR.method7688(var9, (byte)-5)
                        .method8340(var18, var6.field5349, (byte)-92);
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classOC.method8337(
                        classMR.method7688(var9, (byte)2), var18, -1601970641 * var6.field5350, (byte)-111
                     );
                  }

                  return 1;
               }
            } else if (var0 == 6531) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 240;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
                  return 1;
               }
            } else {
               return 2;
            }
         } else {
            int var4 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            if (var4 >= 0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               if (var4 < -622036481 * World.field1151) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  World var5 = World.field1168[var4];
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var5.field1159 * 851246551;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -458552905 * var5.field1158;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var5.field1165;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -288138629 * var5.field1160;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 597852065 * var5.field1157;
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var5.field1167;
                  return 1;
               }
            }

            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
            classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
            classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
            return 1;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "su.bh(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(F)I")
   @ObfuscatedName("br")
   int method10728(float var1) {
      if (this.field6044 * -1691494996 < 0
         || !(this.field6066[this.field6044 * 1462367808].field6101 * 846099456 <= var1)
         || null != this.field6066[-977017365 * this.field6044].field6102
            && !(this.field6066[-840621513 * this.field6044].field6102.field6101 * 1359655238 > var1)) {
         if (!(var1 < method10714(this, (byte)-61)) && !(var1 > method10718(this, 2112203563))) {
            int var2 = this.method10735((byte)-68);
            int var3 = -67131809 * this.field6044;
            if (var2 > 0) {
               int var4 = 0;
               int var5 = var2 - 1;

               do {
                  int var6 = var4 + var5 >> 1;
                  if (var1 < 1210377865 * this.field6066[var6].field6101) {
                     if (var1 > -549718150 * this.field6066[var6 - 1].field6101) {
                        var3 = var6 - 1;
                        break;
                     }

                     var5 = var6 - 1;
                  } else {
                     if (!(var1 > 178611407 * this.field6066[var6].field6101)) {
                        var3 = var6;
                        break;
                     }

                     if (var1 < 1210377865 * this.field6066[var6 + 1].field6101) {
                        var3 = var6;
                        break;
                     }

                     var4 = var6 + 1;
                  }
               } while (var4 <= var5);
            }

            if (-1409367975 * this.field6044 != var3) {
               this.field6044 = -1959130335 * var3;
               this.field6043 = true;
            }

            return this.field6044 * -140518255;
         } else {
            return -1;
         }
      } else {
         return this.field6044 * -140518255;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   int method10734() {
      return null == this.field6066 ? 0 : this.field6066.length;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aa")
   int method10735(byte var1) {
      try {
         if (null == this.field6066) {
            if (var1 >= 8) {
               throw new IllegalStateException();
            } else {
               return 0;
            }
         } else {
            return this.field6066.length;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "su.aa(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bo")
   int method10736() {
      return null == this.field6066 ? 0 : this.field6066.length;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bk")
   int method10724() {
      return method10718(this, 2112203563) - method10714(this, (byte)94);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;I)F")
   @ObfuscatedName("hj")
   public static float method10712(classSU var0, int var1) {
      if (var0 == null) {
         var0.method10724();
      }

      if (var1 < method10714(var0, (byte)4)) {
         return var0.field6054;
      } else {
         return var1 > method10718(var0, 2112203563) ? var0.field6053 : var0.field6065[var1 - method10714(var0, (byte)103)];
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;Ljava/lang/String;Ljava/lang/String;I)Lyz;")
   @ObfuscatedName("ag")
   public static IndexedSprite method10729(IndexDataBase var0, String var1, String var2, int var3) {
      try {
         if (!IndexDataBase.method11923(var0, var1, var2, -578231059)) {
            return null;
         } else {
            int var4 = IndexDataBase.method11918(var0, var1, -240675144);
            int var5 = var0.method11922(var4, var2, (byte)-110);
            return classTH.method10857(var0, var4, var5, -704867707);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "su.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   int method10721(int var1) {
      try {
         return 1286089593 * this.field6058;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "su.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ab")
   int method10716(byte var1) {
      try {
         return this.field6058 * -1666621055;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "su.ab(" + 41);
      }
   }
}
