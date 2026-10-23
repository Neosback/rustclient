import java.awt.datatransfer.Clipboard;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fc")
public class classFC implements classTL {
   @ObfuscatedName("af")
   boolean field2199;
   @ToRemove(unused = "true")
   @ObfuscatedName("br")
   static final int field2205 = 1076101603;
   @ObfuscatedName("ae")
   boolean field2200;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("az")
   Widget field2201 = null;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   static final int field2203 = 39;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final int field2204 = 5;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("hy")
   static classVP field2206;
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   static final int field2202 = 40;

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("az")
   public Widget method4595(int var1) {
      try {
         return this.field2201;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fc.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("ad")
   public void method4599(Widget var1) {
      if (var1 != null) {
         if (this.field2201 != var1) {
            this.method4605((byte)46);
            this.field2201 = var1;
         }

         classLX var2 = var1.method7343(-2139383269);
         if (var2 != null) {
            if (!var2.field4417.method7027(1270661639) && var2.field4423 != null) {
               ScriptEvent var3 = classUQ.method11527(var1, 1995815078).method14195(var2.field4423, (byte)2).method14226((byte)89);
               client.field824.method9709(var3, 1697206599);
            }

            var2.field4417.method6847(true, 1530556286);
         }
      } else {
         this.method4605((byte)109);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)V")
   @ObfuscatedName("af")
   public void method4600(Widget var1, byte var2) {
      try {
         if (var1 != null) {
            if (var2 == 2) {
               throw new IllegalStateException();
            }

            if (this.field2201 != var1) {
               if (var2 == 2) {
                  return;
               }

               this.method4605((byte)51);
               this.field2201 = var1;
            }

            classLX var3 = var1.method7343(-1453299098);
            if (var3 != null) {
               if (var2 == 2) {
                  return;
               }

               if (!var3.field4417.method7027(1570032005)) {
                  if (var2 == 2) {
                     throw new IllegalStateException();
                  }

                  if (var3.field4423 != null) {
                     if (var2 == 2) {
                        throw new IllegalStateException();
                     }

                     ScriptEvent var4 = classUQ.method11527(var1, 19062755).method14195(var3.field4423, (byte)2).method14226((byte)68);
                     client.field824.method9709(var4, 1697206599);
                  }
               }

               var3.field4417.method6847(true, 2013040570);
            }
         } else {
            this.method4605((byte)8);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "fc.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lkq;)I")
   @ObfuscatedName("wi")
   public static int method4608(classKQ var0) {
      return -1218877699 * var0.field3996;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ae")
   public void method4605(byte var1) {
      try {
         if (null == this.field2201) {
            if (var1 <= 3) {
               throw new IllegalStateException();
            }
         } else {
            classLX var2 = this.field2201.method7343(-1336148185);
            Widget var3 = this.field2201;
            this.field2201 = null;
            if (var2 != null) {
               if (var2.field4417.method7027(1936314001)) {
                  if (var1 <= 3) {
                     throw new IllegalStateException();
                  }

                  if (null != var2.field4423) {
                     if (var1 <= 3) {
                        return;
                     }

                     ScriptEvent var4 = classUQ.method11527(var3, 873340457).method14195(var2.field4423, (byte)2).method14226((byte)80);
                     client.field824.method9709(var4, 1697206599);
                  }
               }

               var2.field4417.method6847(false, 1653515093);
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "fc.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod447(int var1, byte var2) {
      try {
         if (this.field2201 == null) {
            if (var2 != 2) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            classTT var3 = this.field2201.method7338(1289741507);
            if (var3 == null) {
               if (var2 != 2) {
                  throw new IllegalStateException();
               } else {
                  return false;
               }
            } else {
               if (var3.method11205(var1, -2115143667)) {
                  switch (var1) {
                     case 81:
                        this.field2200 = true;
                        break;
                     case 82:
                        this.field2199 = true;
                        break;
                     default:
                        this.method4611(var1, (byte)4);
                  }
               }

               return classTT.method11200(var3, var1, -1831933183);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "fc.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod448(int var1, byte var2) {
      try {
         switch (var1) {
            case 81:
               this.field2200 = false;
               return false;
            case 82:
               this.field2199 = false;
               return false;
            default:
               return false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fc.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ah")
   boolean method4610(int var1) {
      if (this.field2201 == null) {
         return false;
      } else {
         classLH var2 = this.field2201.method7334(-926237410);
         if (var2 != null && var2.method7043((byte)-26)) {
            switch (var1) {
               case 13:
                  this.method4605((byte)118);
                  return true;
               case 48:
                  if (this.field2199) {
                     var2.method6926((byte)-52);
                  }

                  return true;
               case 65:
                  if (this.field2199) {
                     Clipboard var7 = GameEngine.method11022(classOE.field4841, (byte)-77);
                     var2.method6998(var7, -1969797108);
                  }

                  return true;
               case 66:
                  if (this.field2199) {
                     Clipboard var6 = GameEngine.method11022(classOE.field4841, (byte)-97);
                     var2.method6995(var6, -887645341);
                  }

                  return true;
               case 67:
                  if (this.field2199) {
                     Clipboard var5 = GameEngine.method11022(classOE.field4841, (byte)-22);
                     classLH.method7002(var2, var5, -87014864);
                  }

                  return true;
               case 84:
                  if (var2.method7058(1617848039) == 0) {
                     classLH.method6906(var2, 10, 532262135);
                  } else if (this.field2200 && var2.method7086((byte)0)) {
                     classLH.method6906(var2, 10, 1823632524);
                  } else {
                     classLX var3 = this.field2201.method7343(-1245103605);
                     ScriptEvent var4 = classUQ.method11527(this.field2201, 121484135).method14195(var3.field4425, (byte)2).method14226((byte)14);
                     client.field824.method9709(var4, 1697206599);
                     this.method4605((byte)15);
                  }

                  return true;
               case 85:
                  if (this.field2199) {
                     var2.method6917((byte)19);
                  } else {
                     classLH.method6907(var2, -2045553548);
                  }

                  return true;
               case 96:
                  if (this.field2199) {
                     var2.method6956(this.field2200, (byte)-49);
                  } else {
                     var2.method6947(this.field2200, 1708762862);
                  }

                  return true;
               case 97:
                  if (this.field2199) {
                     var2.method6958(this.field2200, 1295536453);
                  } else {
                     classLH.method6952(var2, this.field2200, (byte)11);
                  }

                  return true;
               case 98:
                  if (this.field2199) {
                     var2.method7004((byte)0);
                  } else {
                     var2.method6960(this.field2200, (byte)78);
                  }

                  return true;
               case 99:
                  if (this.field2199) {
                     classLH.method7006(var2, (byte)33);
                  } else {
                     classLH.method6970(var2, this.field2200, 1071340814);
                  }

                  return true;
               case 101:
                  if (this.field2199) {
                     var2.method6918(1487431897);
                  } else {
                     var2.method6913((byte)94);
                  }

                  return true;
               case 102:
                  if (this.field2199) {
                     classLH.method6943(var2, this.field2200, (byte)40);
                  } else {
                     var2.method6932(this.field2200, (byte)113);
                  }

                  return true;
               case 103:
                  if (this.field2199) {
                     var2.method6945(this.field2200, -665535259);
                  } else {
                     classLH.method6939(var2, this.field2200, -1262860432);
                  }

                  return true;
               case 104:
                  if (this.field2199) {
                     var2.method6977(this.field2200, 173697590);
                  } else {
                     var2.method6973(this.field2200, 1596149983);
                  }

                  return true;
               case 105:
                  if (this.field2199) {
                     var2.method6982(this.field2200, -1545688886);
                  } else {
                     var2.method6975(this.field2200, 988636327);
                  }

                  return true;
               default:
                  return false;
            }
         } else {
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)Z")
   @ObfuscatedName("ax")
   @Override
   public boolean vmethod444(boolean var1, int var2) {
      try {
         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fc.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("bf")
   static int method4613(int var0, Script var1, boolean var2, int var3) {
      try {
         if (var0 == 5504) {
            if (var3 != -1165804573) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var7 = classBB.field323[DynamicObject.field1676 * -324749371];
               int var5 = classBB.field323[-324749371 * DynamicObject.field1676 + 1];
               if (!client.field934) {
                  if (var3 != -1165804573) {
                     throw new IllegalStateException();
                  }

                  classUP.method11494(client.field943, classEG.method3897(var7, (byte)39), -1345215934);
                  classUP.method11494(client.field995, classEG.method3897(var5, (byte)31), -1739806269);
               }

               return 1;
            }
         } else if (5505 == var0) {
            if (var3 != -1165804573) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = client.field943.method11515(-2071860481);
               return 1;
            }
         } else if (var0 == 5506) {
            if (var3 != -1165804573) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = client.field995.method11515(-952996512);
               return 1;
            }
         } else if (var0 == 5530) {
            if (var3 != -1165804573) {
               throw new IllegalStateException();
            } else {
               int var4 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (var4 < 0) {
                  if (var3 != -1165804573) {
                     throw new IllegalStateException();
                  }

                  var4 = 0;
               }

               client.field841 = var4 * 643105531;
               return 1;
            }
         } else if (var0 == 5531) {
            if (var3 != -1165804573) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -184240077 * client.field841;
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "fc.bf(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(CI)Z")
   @ObfuscatedName("as")
   @Override
   public boolean vmethod446(char var1, int var2) {
      try {
         if (this.field2201 == null) {
            if (var2 != 1791336663) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (!classIB.method6177(var1, -1608469876)) {
            if (var2 != 1791336663) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            classLH var3 = this.field2201.method7334(-416361573);
            if (null != var3) {
               if (var2 != 1791336663) {
                  throw new IllegalStateException();
               }

               if (var3.method7043((byte)-55)) {
                  classTT var4 = this.field2201.method7338(1289741507);
                  if (var4 == null) {
                     return false;
                  }

                  if (var4.method11208(var1, 731299091)) {
                     classLH.method6906(var3, var1, 779015235);
                  }

                  return var4.method11203(var1, -2071087427);
               }

               if (var2 != 1791336663) {
                  throw new IllegalStateException();
               }
            }

            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "fc.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("ao")
   public Widget method4596() {
      return this.field2201;
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("al")
   public Widget method4597() {
      return this.field2201;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   public void method4606() {
      if (null != this.field2201) {
         classLX var1 = this.field2201.method7343(1725460326);
         Widget var2 = this.field2201;
         this.field2201 = null;
         if (var1 != null) {
            if (var1.field4417.method7027(1037802945) && null != var1.field4423) {
               ScriptEvent var3 = classUQ.method11527(var2, -3732236).method14195(var1.field4423, (byte)2).method14226((byte)60);
               client.field824.method9709(var3, 1697206599);
            }

            var1.field4417.method6847(false, 1567522650);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("ay")
   public void method4601(Widget var1) {
      if (var1 != null) {
         if (this.field2201 != var1) {
            this.method4605((byte)103);
            this.field2201 = var1;
         }

         classLX var2 = var1.method7343(-1007933513);
         if (var2 != null) {
            if (!var2.field4417.method7027(1042511688) && var2.field4423 != null) {
               ScriptEvent var3 = classUQ.method11527(var1, 477128989).method14195(var2.field4423, (byte)2).method14226((byte)30);
               client.field824.method9709(var3, 1697206599);
            }

            var2.field4417.method6847(true, 1893640975);
         }
      } else {
         this.method4605((byte)40);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("aq")
   public void method4602(Widget var1) {
      if (var1 != null) {
         if (this.field2201 != var1) {
            this.method4605((byte)89);
            this.field2201 = var1;
         }

         classLX var2 = var1.method7343(-1859797915);
         if (var2 != null) {
            if (!var2.field4417.method7027(1418404830) && var2.field4423 != null) {
               ScriptEvent var3 = classUQ.method11527(var1, -18973962).method14195(var2.field4423, (byte)2).method14226((byte)17);
               client.field824.method9709(var3, 1697206599);
            }

            var2.field4417.method6847(true, 1949755012);
         }
      } else {
         this.method4605((byte)125);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ac")
   boolean method4611(int var1, byte var2) {
      try {
         if (this.field2201 == null) {
            if (var2 == 8) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            classLH var3 = this.field2201.method7334(-710547776);
            if (var3 != null) {
               if (var2 == 8) {
                  throw new IllegalStateException();
               }

               if (var3.method7043((byte)4)) {
                  switch (var1) {
                     case 13:
                        this.method4605((byte)115);
                        return true;
                     case 48:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           var3.method6926((byte)-82);
                        }

                        return true;
                     case 65:
                        if (this.field2199) {
                           Clipboard var9 = GameEngine.method11022(classOE.field4841, (byte)-5);
                           var3.method6998(var9, -1743371418);
                        }

                        return true;
                     case 66:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           Clipboard var8 = GameEngine.method11022(classOE.field4841, (byte)-34);
                           var3.method6995(var8, -887645341);
                        }

                        return true;
                     case 67:
                        if (this.field2199) {
                           Clipboard var7 = GameEngine.method11022(classOE.field4841, (byte)-79);
                           classLH.method7002(var3, var7, 971324073);
                        }

                        return true;
                     case 84:
                        if (var3.method7058(-636173591) == 0) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           classLH.method6906(var3, 10, 1471322356);
                        } else {
                           if (this.field2200) {
                              if (var2 == 8) {
                                 throw new IllegalStateException();
                              }

                              if (var3.method7086((byte)0)) {
                                 classLH.method6906(var3, 10, -2052003972);
                                 return true;
                              }
                           }

                           classLX var4 = this.field2201.method7343(1425325542);
                           ScriptEvent var5 = classUQ.method11527(this.field2201, 870533817).method14195(var4.field4425, (byte)2).method14226((byte)38);
                           client.field824.method9709(var5, 1697206599);
                           this.method4605((byte)62);
                        }

                        return true;
                     case 85:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           var3.method6917((byte)3);
                        } else {
                           classLH.method6907(var3, -2061291396);
                        }

                        return true;
                     case 96:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           var3.method6956(this.field2200, (byte)-24);
                        } else {
                           var3.method6947(this.field2200, 868396801);
                        }

                        return true;
                     case 97:
                        if (this.field2199) {
                           var3.method6958(this.field2200, 866082776);
                        } else {
                           classLH.method6952(var3, this.field2200, (byte)62);
                        }

                        return true;
                     case 98:
                        if (this.field2199) {
                           var3.method7004((byte)0);
                        } else {
                           var3.method6960(this.field2200, (byte)-72);
                        }

                        return true;
                     case 99:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           classLH.method7006(var3, (byte)104);
                        } else {
                           classLH.method6970(var3, this.field2200, -1506955074);
                        }

                        return true;
                     case 101:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           var3.method6918(506101956);
                        } else {
                           var3.method6913((byte)91);
                        }

                        return true;
                     case 102:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           classLH.method6943(var3, this.field2200, (byte)32);
                        } else {
                           var3.method6932(this.field2200, (byte)30);
                        }

                        return true;
                     case 103:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           var3.method6945(this.field2200, -665535259);
                        } else {
                           classLH.method6939(var3, this.field2200, -1660850042);
                        }

                        return true;
                     case 104:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           var3.method6977(this.field2200, 173697590);
                        } else {
                           var3.method6973(this.field2200, 1162981241);
                        }

                        return true;
                     case 105:
                        if (this.field2199) {
                           if (var2 == 8) {
                              throw new IllegalStateException();
                           }

                           var3.method6982(this.field2200, -1545688886);
                        } else {
                           var3.method6975(this.field2200, 988636327);
                        }

                        return true;
                     default:
                        return false;
                  }
               }

               if (var2 == 8) {
                  throw new IllegalStateException();
               }
            }

            return false;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "fc.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("ap")
   public void method4603(Widget var1) {
      if (var1 != null) {
         if (this.field2201 != var1) {
            this.method4605((byte)59);
            this.field2201 = var1;
         }

         classLX var2 = var1.method7343(1910315408);
         if (var2 != null) {
            if (!var2.field4417.method7027(982716387) && var2.field4423 != null) {
               ScriptEvent var3 = classUQ.method11527(var1, 594079630).method14195(var2.field4423, (byte)2).method14226((byte)19);
               client.field824.method9709(var3, 1697206599);
            }

            var2.field4417.method6847(true, 1588806639);
         }
      } else {
         this.method4605((byte)91);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfc;)V")
   @ObfuscatedName("je")
   public static void method4607(classFC var0) {
      if (null != var0.field2201) {
         classLX var1 = var0.field2201.method7343(-1526634917);
         Widget var2 = var0.field2201;
         var0.field2201 = null;
         if (var1 != null) {
            if (var1.field4417.method7027(1264639224) && null != var1.field4423) {
               ScriptEvent var3 = classUQ.method11527(var2, -132233407).method14195(var1.field4423, (byte)2).method14226((byte)81);
               client.field824.method9709(var3, 1697206599);
            }

            var1.field4417.method6847(false, 2100103659);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("aa")
   public Widget method4598() {
      return this.field2201;
   }

   @ObfuscatedSignature(descriptor = "(Z)Z")
   @ObfuscatedName("am")
   @Override
   public boolean vmethod445(boolean var1) {
      return false;
   }

   public classFC() {
      this.field2199 = false;
      this.field2200 = false;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ar")
   @Override
   public boolean vmethod439(int var1) {
      if (this.field2201 == null) {
         return false;
      } else {
         classTT var2 = this.field2201.method7338(1289741507);
         if (var2 == null) {
            return false;
         } else {
            if (var2.method11205(var1, -1501551007)) {
               switch (var1) {
                  case 81:
                     this.field2200 = true;
                     break;
                  case 82:
                     this.field2199 = true;
                     break;
                  default:
                     this.method4611(var1, (byte)-64);
               }
            }

            return classTT.method11200(var2, var1, -1825274409);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aw")
   @Override
   public boolean vmethod440(int var1) {
      switch (var1) {
         case 81:
            this.field2200 = false;
            return false;
         case 82:
            this.field2199 = false;
            return false;
         default:
            return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("aj")
   public void method4604(Widget var1) {
      if (var1 != null) {
         if (this.field2201 != var1) {
            this.method4605((byte)13);
            this.field2201 = var1;
         }

         classLX var2 = var1.method7343(1426850858);
         if (var2 != null) {
            if (!var2.field4417.method7027(1419512645) && var2.field4423 != null) {
               ScriptEvent var3 = classUQ.method11527(var1, 314397734).method14195(var2.field4423, (byte)2).method14226((byte)109);
               client.field824.method9709(var3, 1697206599);
            }

            var2.field4417.method6847(true, 1447369405);
         }
      } else {
         this.method4605((byte)123);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ak")
   @Override
   public boolean vmethod441(int var1) {
      switch (var1) {
         case 81:
            this.field2200 = false;
            return false;
         case 82:
            this.field2199 = false;
            return false;
         default:
            return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("an")
   @Override
   public boolean vmethod443(char var1) {
      if (this.field2201 == null) {
         return false;
      } else if (!classIB.method6177(var1, 1284636004)) {
         return false;
      } else {
         classLH var2 = this.field2201.method7334(-1566426096);
         if (null != var2 && var2.method7043((byte)-68)) {
            classTT var3 = this.field2201.method7338(1289741507);
            if (var3 == null) {
               return false;
            } else {
               if (var3.method11208(var1, 731299091)) {
                  classLH.method6906(var2, var1, -532908386);
               }

               return var3.method11203(var1, -2022070801);
            }
         } else {
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("av")
   @Override
   public boolean vmethod438(int var1) {
      if (this.field2201 == null) {
         return false;
      } else {
         classTT var2 = this.field2201.method7338(1289741507);
         if (var2 == null) {
            return false;
         } else {
            if (var2.method11205(var1, 121037862)) {
               switch (var1) {
                  case 81:
                     this.field2200 = true;
                     break;
                  case 82:
                     this.field2199 = true;
                     break;
                  default:
                     this.method4611(var1, (byte)35);
               }
            }

            return classTT.method11200(var2, var1, -2050129091);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("at")
   @Override
   public boolean vmethod442(int var1) {
      switch (var1) {
         case 81:
            this.field2200 = false;
            return false;
         case 82:
            this.field2199 = false;
            return false;
         default:
            return false;
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;Llw;ZLvv;Lqn;I)V")
   @ObfuscatedName("af")
   public static void method4609(Widget[] var0, Widget var1, boolean var2, classVV var3, classQN var4, int var5) {
      try {
         int var6 = 0 != -842862191 * var1.field4256 ? var1.field4256 * -842862191 : var1.field4247 * 1115597881;
         int var10000;
         if (0 != var1.field4267 * -714521463) {
            if (var5 == -1037162862) {
               throw new IllegalStateException();
            }

            var10000 = var1.field4267 * -714521463;
         } else {
            var10000 = 293773697 * var1.field4257;
         }

         int var7 = var10000;
         NpcOverrides.method9135(
            var0,
            -1210177335 * var1.field4263,
            var1.field4264 * 379541057,
            var1.field4327 * -1163656551,
            63131743 * var1.field4258,
            var6,
            var7,
            var2,
            var3,
            var4,
            1717101466
         );
         if (var1.field4338 != null) {
            if (var5 == -1037162862) {
               return;
            }

            NpcOverrides.method9135(var1.field4338, 0, var1.field4338.length - 1, var1.field4327 * -1163656551, -1, var6, var7, var2, var3, var4, 819056711);
         }

         if (-1 == var1.field4258 * 63131743) {
            if (var5 == -1037162862) {
               throw new IllegalStateException();
            }

            WidgetNode var8 = (WidgetNode)HashTable.method13432(var3.field6611, var1.field4327 * -1163656551);
            if (null != var8) {
               if (var5 == -1037162862) {
                  return;
               }

               classCN.method2541(2023647411 * var8.field5275, var6, var7, var2, var3, var4, (short)217);
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "fc.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;II)V")
   @ObfuscatedName("ae")
   static void method4612(String var0, StringBuilder var1, classQI var2, int var3, int var4) {
      try {
         String[] var5 = var0.split("=");
         if (var5.length == 1) {
            if (var4 != 2073705266) {
               throw new IllegalStateException();
            }

            var5 = var0.split(":");
            int var6 = var2.method9611(var5[0], 1849728926);
            String var7 = Integer.toString(var6);
            if (var5.length > 1) {
               if (var4 != 2073705266) {
                  throw new IllegalStateException();
               }

               String var8 = var5[1].toLowerCase();
               classRZ var9 = classAI.method363(classXO.field6890, 1883191353);
               if (var8.equals("spacer")) {
                  if (var4 != 2073705266) {
                     throw new IllegalStateException();
                  }

                  var7 = classXW.method13010(var6, 0, true, true, var9, (byte)30);
               } else if (var8.equals("percent")) {
                  var7 = classXW.method13010(var6, 0, false, true, var9, (byte)38);
                  var7 = var7 + "%";
               } else if (var8.equals("date")) {
                  var7 = classGQ.method5636(var6, (byte)19);
               } else if (var8.startsWith("spacer_")) {
                  if (var4 != 2073705266) {
                     throw new IllegalStateException();
                  }

                  classRA var34 = Projectile.method1213(var8.substring("spacer_".length()), 763367599);
                  var7 = classXW.method13010(var6, 618506607 * var34.field5629, true, var34.field5628, var9, (byte)29);
               } else if (var8.startsWith("decimal_")) {
                  classRA var33 = Projectile.method1213(var8.substring("decimal_".length()), 633990357);
                  var7 = classXW.method13010(var6, 618506607 * var33.field5629, false, var33.field5628, var9, (byte)-84);
               } else if (var8.startsWith("scale_")) {
                  if (var4 != 2073705266) {
                     throw new IllegalStateException();
                  }

                  String var35 = var8.substring("scale_".length());
                  boolean var36 = true;
                  if (var35.endsWith("_z")) {
                     if (var4 != 2073705266) {
                        return;
                     }

                     var36 = false;
                     var35 = var35.substring(0, var35.length() - "_z".length());
                  }

                  String[] var37 = var35.split("_");
                  int var39 = Integer.parseInt(var37[0]);
                  int var10000;
                  if (var37.length > 1) {
                     if (var4 != 2073705266) {
                        return;
                     }

                     var10000 = Integer.parseInt(var37[1]);
                  } else {
                     var10000 = 0;
                  }

                  int var41 = var10000;
                  classRL var32 = new classRL(var39, var41, var36);
                  var7 = classIG.method6249(var6, var32.field5683 * -527595341, -212446981 * var32.field5685, var32.field5684, var9, 1711827676);
               } else if (var8.startsWith("percent_")) {
                  classRA var31 = Projectile.method1213(var8.substring("percent_".length()), -1110347053);
                  var7 = classYY.method13818(var6, var31.field5629 * 618506607, var31.field5628, var9, (byte)8);
               } else if (var8.startsWith("zeropad_")) {
                  if (var4 != 2073705266) {
                     throw new IllegalStateException();
                  }

                  int var30 = Integer.parseInt(var8.substring("zeropad_".length()));
                  if (var6 >= 0) {
                     var7 = String.format("%0" + var30 + "d", var6);
                  }
               } else if (var8.startsWith("time_")) {
                  if (var4 != 2073705266) {
                     throw new IllegalStateException();
                  }

                  String var11 = var8.substring("time_".length());
                  String[] var13 = var11.split("_");
                  classRM var14 = new classRM();
                  var14.field5806 = var13[0];
                  var14.field5805 = var13[1];
                  classRM var12;
                  if (var14.field5805.equals("clk")) {
                     if (var4 != 2073705266) {
                        return;
                     }

                     var12 = var14;
                  } else {
                     String var15 = var13[2];
                     if (var15.equals("dt")) {
                        if (var4 != 2073705266) {
                           throw new IllegalStateException();
                        }

                        var14.field5804 = "d";
                        var14.field5808 = true;
                     } else if (var15.equals("ht")) {
                        if (var4 != 2073705266) {
                           throw new IllegalStateException();
                        }

                        var14.field5804 = "h";
                        var14.field5808 = true;
                     } else {
                        var14.field5804 = var15;
                     }

                     boolean var10001;
                     label268: {
                        if (var13.length > 3) {
                           if (var4 != 2073705266) {
                              throw new IllegalStateException();
                           }

                           if (var13[3].equals("ms")) {
                              if (var4 != 2073705266) {
                                 throw new IllegalStateException();
                              }

                              var10001 = true;
                              break label268;
                           }
                        }

                        var10001 = false;
                     }

                     var14.field5809 = var10001;
                     var12 = var14;
                  }

                  classQO var38 = classMT.method7735((byte)40);
                  String var17 = var12.field5806;
                  long var40;
                  if ("c".equals(var17)) {
                     if (var4 != 2073705266) {
                        throw new IllegalStateException();
                     }

                     var40 = 600L * var6;
                  } else if ("s".equals(var17)) {
                     if (var4 != 2073705266) {
                        throw new IllegalStateException();
                     }

                     var40 = 1000L * var6;
                  } else if ("m".equals(var17)) {
                     if (var4 != 2073705266) {
                        throw new IllegalStateException();
                     }

                     var40 = 60000L * var6;
                  } else {
                     var40 = var6;
                  }

                  String var10;
                  label261: {
                     if (var12.field5805.equals("w")) {
                        if (var4 != 2073705266) {
                           return;
                        }

                        label257: {
                           if (!var12.field5806.equals("s")) {
                              if (var4 != 2073705266) {
                                 throw new IllegalStateException();
                              }

                              if (!var12.field5806.equals("c")) {
                                 break label257;
                              }

                              if (var4 != 2073705266) {
                                 return;
                              }
                           }

                           if (var40 < 2000L) {
                              if (var4 != 2073705266) {
                                 throw new IllegalStateException();
                              }

                              var10 = "a moment";
                              break label261;
                           }
                        }

                        if (var12.field5806.equals("m")) {
                           if (var4 != 2073705266) {
                              throw new IllegalStateException();
                           }

                           if (var40 <= 60000L) {
                              if (var4 != 2073705266) {
                                 throw new IllegalStateException();
                              }

                              var10 = "a minute";
                              break label261;
                           }
                        }
                     }

                     if (var12.field5805.equals("clk")) {
                        if (var4 != 2073705266) {
                           throw new IllegalStateException();
                        }

                        long var21 = var40 / 60000L % 1440L;
                        if (var21 < 0L) {
                           if (var4 != 2073705266) {
                              throw new IllegalStateException();
                           }

                           var21 += 1440L;
                        }

                        long var23 = var21 / 60L;
                        long var25 = var21 % 60L;
                        String var20 = classGK.method5516(var23) + ":" + classGK.method5516(var25);
                        var10 = var20;
                     } else {
                        classRY var42 = classDE.method3000(var40, var12.field5804, -765485579);
                        if (var12.field5805.equals("n")) {
                           if (var4 != 2073705266) {
                              throw new IllegalStateException();
                           }

                           var10 = classIR.method6353(var42, var12.field5804, var12.field5809, (byte)47);
                        } else if (var12.field5805.equals("w")) {
                           if (var4 != 2073705266) {
                              throw new IllegalStateException();
                           }

                           var10 = classQO.method9736(var38, var42, var12.field5804, false, var12.field5808, 329825058);
                        } else if (var12.field5805.equals("wm")) {
                           if (var4 != 2073705266) {
                              throw new IllegalStateException();
                           }

                           var10 = classQO.method9736(var38, var42, var12.field5804, true, var12.field5808, 329825058);
                        } else {
                           var10 = Long.toString(var6);
                        }
                     }
                  }

                  var7 = var10;
               }
            }

            var1.append(var7);
         } else if (var5[1].equals("pronoun")) {
            if (var4 != 2073705266) {
               throw new IllegalStateException();
            }

            var2.method9596(var5[0], var3, 1711641727);
         } else {
            var2.method9595(var5[0], var5[1], 81802972);
         }
      } catch (RuntimeException var27) {
         throw classEG.method3899(var27, "fc.ae(" + ')');
      }
   }
}
