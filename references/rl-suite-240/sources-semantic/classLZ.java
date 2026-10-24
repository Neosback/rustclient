import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lz")
public class classLZ {
   @ObfuscatedName("ax")
   int[] field4442;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4445 = 3;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("ab")
   Widget field4449;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("ij")
   static classVP field4450;
   @ObfuscatedName("as")
   int[] field4443 = new int[32];
   @ObfuscatedName("ag")
   int field4447;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4446 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4448 = 0;
   @ObfuscatedName("ac")
   int field4444;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ay")
   final void method7445(int var1) {
      if (this.field4447 * -1725791933 == this.field4443.length) {
         this.field4443 = Arrays.copyOf(this.field4443, this.field4443.length * 2);
      }

      this.field4443[(this.field4447 += 391385963) * -1725791933 - 1] = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public int method7464(int var1) {
      try {
         if (this.field4444 * -1791640697 >= this.field4447 * -1725791933) {
            if (var1 >= -414689364) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            return this.field4443[(this.field4444 += 1687099959) * -1791640697 - 1];
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lz.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   public int method7454(int var1) {
      try {
         return this.field4447 * -1725791933;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lz.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("ag")
   public Widget method7459(int var1) {
      try {
         int var2 = this.method7464(-800637093);
         Widget var10000;
         if (var2 >= 0) {
            if (var1 != -1992217275) {
               throw new IllegalStateException();
            }

            var10000 = this.field4449.field4338[var2];
         } else {
            var10000 = null;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lz.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llz;)Llw;")
   @ObfuscatedName("qo")
   public static Widget method7460(classLZ var0) {
      if (var0 == null) {
         var0.method7455();
      }

      int var1 = var0.method7464(-865800882);
      return var1 >= 0 ? var0.field4449.field4338[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aj")
   final void method7446(int var1) {
      if (this.field4447 * 747387314 == this.field4443.length) {
         this.field4443 = Arrays.copyOf(this.field4443, this.field4443.length * 2);
      }

      this.field4443[(this.field4447 += 391385963) * -1725791933 - 1] = var1;
   }

   public classLZ() {
      this.field4442 = new int[32];
      this.field4444 = 0;
   }

   @ObfuscatedSignature(descriptor = "(ILlw;I)V")
   @ObfuscatedName("aa")
   public void method7441(int var1, Widget var2, int var3) {
      this.field4449 = var2;
      this.field4447 = 0;
      this.field4444 = 0;
      if (var1 >= 0 && var1 < 3) {
         if (null == var2 || var2.field4258 * 63131743 == -1 && var2.field4373 * -1510882375 == 0) {
            if (var2 != null && var2.field4338 != null) {
               if (var1 == 0) {
                  Widget[] var4 = var2.field4338;

                  for (int var5 = 0; var5 < var4.length; var5++) {
                     Widget var6 = var4[var5];
                     if (null != var6) {
                        this.method7448(63131743 * var6.field4258, (byte)88);
                     }
                  }
               } else {
                  boolean var7 = 1 != var1;
                  if (-1 == var3) {
                     method7452(this, var2, var7, -1296840327);
                  } else if (var3 >= 0 && var3 < var2.field4338.length && null != var2.field4338[var3]) {
                     method7452(this, var2.field4338[var3], var7, -2090951979);
                  }
               }
            }
         } else {
            throw new RuntimeException("");
         }
      } else {
         throw new RuntimeException("");
      }
   }

   @ObfuscatedSignature(descriptor = "(ILlw;I)V")
   @ObfuscatedName("ao")
   public void method7442(int var1, Widget var2, int var3) {
      this.field4449 = var2;
      this.field4447 = 0;
      this.field4444 = 0;
      if (var1 >= 0 && var1 < 3) {
         if (null == var2 || var2.field4258 * 63131743 == -1 && var2.field4373 * -1510882375 == 0) {
            if (var2 != null && var2.field4338 != null) {
               if (var1 == 0) {
                  Widget[] var4 = var2.field4338;

                  for (int var5 = 0; var5 < var4.length; var5++) {
                     Widget var6 = var4[var5];
                     if (null != var6) {
                        this.method7448(344174041 * var6.field4258, (byte)91);
                     }
                  }
               } else {
                  boolean var7 = 1 != var1;
                  if (-1 == var3) {
                     method7452(this, var2, var7, -1272060004);
                  } else if (var3 >= 0 && var3 < var2.field4338.length && null != var2.field4338[var3]) {
                     method7452(this, var2.field4338[var3], var7, -707375077);
                  }
               }
            }
         } else {
            throw new RuntimeException("");
         }
      } else {
         throw new RuntimeException("");
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   final void method7447(int var1) {
      if (this.field4447 * -1725791933 == this.field4443.length) {
         this.field4443 = Arrays.copyOf(this.field4443, this.field4443.length * 2);
      }

      this.field4443[(this.field4447 += 391385963) * -1725791933 - 1] = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llz;ILjava/lang/Object;Ljava/lang/Object;)V")
   @ObfuscatedName("eg")
   public static void method7468(classLZ var0, int var1, Object var2, Object var3) {
      int[] var4 = var0.field4443;
      int var5 = -1725791933 * var0.field4447;
      var0.field4443 = var0.field4442;
      var0.field4447 = 0;
      var0.field4442 = var4;

      for (int var6 = 0; var6 < var5; var6++) {
         Widget var7 = var0.field4449.field4338[var4[var6]];
         if (null != var7) {
            Object var8 = var7.method7267(var1, var3, 1469006910);
            if (var8 == var2 || var8 != null && var8.equals(var2)) {
               var0.method7448(var4[var6], (byte)55);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;Ljava/lang/Object;B)V")
   @ObfuscatedName("ac")
   public void method7469(int var1, Object var2, Object var3, byte var4) {
      try {
         int[] var5 = this.field4443;
         int var6 = -1725791933 * this.field4447;
         this.field4443 = this.field4442;
         this.field4447 = 0;
         this.field4442 = var5;

         for (int var7 = 0; var7 < var6; var7++) {
            Widget var8 = this.field4449.field4338[var5[var7]];
            if (null != var8) {
               if (var4 >= 71) {
                  throw new IllegalStateException();
               }

               Object var9 = var8.method7267(var1, var3, 1993641127);
               if (var9 != var2) {
                  if (var4 >= 71) {
                     throw new IllegalStateException();
                  }

                  if (var9 == null) {
                     continue;
                  }

                  if (var4 >= 71) {
                     throw new IllegalStateException();
                  }

                  if (!var9.equals(var2)) {
                     continue;
                  }

                  if (var4 >= 71) {
                     throw new IllegalStateException();
                  }
               }

               this.method7448(var5[var7], (byte)36);
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "lz.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("af")
   final void method7448(int var1, byte var2) {
      try {
         if (this.field4447 * -1725791933 == this.field4443.length) {
            if (var2 <= 20) {
               throw new IllegalStateException();
            }

            this.field4443 = Arrays.copyOf(this.field4443, this.field4443.length * 2);
         }

         this.field4443[(this.field4447 += 391385963) * -1725791933 - 1] = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lz.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Z)V")
   @ObfuscatedName("aq")
   final void method7449(Widget var1, boolean var2) {
      int var3 = var1 == this.field4449 ? 0 : var1.field4263 * -1210177335;
      int var4 = this.field4449 == var1 ? var1.field4338.length - 1 : var1.field4264 * 1871894748;

      for (int var5 = var3; var5 <= var4; var5++) {
         Widget var6 = this.field4449.field4338[var5];
         if (null != var6 && var6.field4261 * 653784551 == var1.field4258 * 63131743) {
            this.method7448(var6.field4258 * 63131743, (byte)45);
            if (var2 && var6.field4263 * -1210177335 <= 379541057 * var6.field4264) {
               method7452(this, var6, true, -1206036438);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laae;Ljava/lang/Object;)I")
   @ObfuscatedName("ph")
   public static int method7458(classAAE var0, Object var1) {
      if (var0 == null) {
         var0.method165();
      }

      return classAAE.method178(var0, (classAAE)var1, 1600959519);
   }

   @ObfuscatedSignature(descriptor = "(Llw;Z)V")
   @ObfuscatedName("ad")
   final void method7450(Widget var1, boolean var2) {
      int var3 = var1 == this.field4449 ? 0 : var1.field4263 * -1210177335;
      int var4 = this.field4449 == var1 ? var1.field4338.length - 1 : var1.field4264 * 379541057;

      for (int var5 = var3; var5 <= var4; var5++) {
         Widget var6 = this.field4449.field4338[var5];
         if (null != var6 && var6.field4261 * 653784551 == var1.field4258 * 63131743) {
            this.method7448(var6.field4258 * 63131743, (byte)93);
            if (var2 && var6.field4263 * -1210177335 <= 379541057 * var6.field4264) {
               method7452(this, var6, true, -1174421858);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Z)V")
   @ObfuscatedName("ap")
   final void method7451(Widget var1, boolean var2) {
      int var3 = var1 == this.field4449 ? 0 : var1.field4263 * -1210177335;
      int var4 = this.field4449 == var1 ? var1.field4338.length - 1 : var1.field4264 * -1821963048;

      for (int var5 = var3; var5 <= var4; var5++) {
         Widget var6 = this.field4449.field4338[var5];
         if (null != var6 && var6.field4261 * -494335492 == var1.field4258 * -1524269907) {
            this.method7448(var6.field4258 * -767283484, (byte)55);
            if (var2 && var6.field4263 * -1210177335 <= 379541057 * var6.field4264) {
               method7452(this, var6, true, -1625789106);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ZI)Ljava/lang/String;")
   @ObfuscatedName("gx")
   static String method7470(String var0, boolean var1, int var2) {
      try {
         String var10000;
         if (var1) {
            if (var2 >= 1899288588) {
               throw new IllegalStateException();
            }

            var10000 = "https://";
         } else {
            var10000 = "http://";
         }

         String var3 = var10000;
         if (client.field941 * 2064080735 == 1) {
            var0 = var0 + "-wtrc";
         } else if (2 == client.field941 * 2064080735) {
            if (var2 >= 1899288588) {
               throw new IllegalStateException();
            }

            var0 = var0 + "-wtqa";
         } else if (3 == client.field941 * 2064080735) {
            var0 = var0 + "-wtwip";
         } else if (5 == client.field941 * 2064080735) {
            if (var2 >= 1899288588) {
               throw new IllegalStateException();
            }

            var0 = var0 + "-wti";
         } else if (client.field941 * 2064080735 == 4) {
            if (var2 >= 1899288588) {
               throw new IllegalStateException();
            }

            var0 = "local";
         }

         String var4 = "";
         if (classQW.field5620 != null) {
            if (var2 >= 1899288588) {
               throw new IllegalStateException();
            }

            var4 = "/p=" + classQW.field5620;
         }

         String var5 = "runescape.com";
         return var3 + var0 + "." + var5 + "/l=" + classLU.field4230 + "/a=" + Projectile.field570 * 606350751 + var4 + "/";
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lz.gx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llz;Llw;ZI)V")
   @ObfuscatedName("cs")
   public static void method7452(classLZ var0, Widget var1, boolean var2, int var3) {
      if (var0 == null) {
         var0.method7453(var1, var2, var3);
      } else {
         try {
            int var10000;
            if (var1 == var0.field4449) {
               if (var3 >= -179907272) {
                  throw new IllegalStateException();
               }

               var10000 = 0;
            } else {
               var10000 = var1.field4263 * -1210177335;
            }

            int var4 = var10000;
            int var5 = var0.field4449 == var1 ? var1.field4338.length - 1 : var1.field4264 * 379541057;

            for (int var6 = var4; var6 <= var5; var6++) {
               if (var3 >= -179907272) {
                  return;
               }

               Widget var7 = var0.field4449.field4338[var6];
               if (null != var7) {
                  if (var3 >= -179907272) {
                     throw new IllegalStateException();
                  }

                  if (var7.field4261 * 653784551 == var1.field4258 * 63131743) {
                     if (var3 >= -179907272) {
                        throw new IllegalStateException();
                     }

                     var0.method7448(var7.field4258 * 63131743, (byte)41);
                     if (var2) {
                        if (var3 >= -179907272) {
                           throw new IllegalStateException();
                        }

                        if (var7.field4263 * -1210177335 <= 379541057 * var7.field4264) {
                           if (var3 >= -179907272) {
                              throw new IllegalStateException();
                           }

                           method7452(var0, var7, true, -1870760730);
                        }
                     }
                  }
               }
            }
         } catch (RuntimeException var8) {
            throw classEG.method3899(var8, "lz.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ai")
   public int method7455() {
      return this.field4447 * -1725791933;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llz;)I")
   @ObfuscatedName("mm")
   public static int method7456(classLZ var0) {
      return var0.field4447 * -1725791933;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llz;)Llw;")
   @ObfuscatedName("zs")
   public static Widget method7461(classLZ var0) {
      if (var0 == null) {
         return var0.method7463();
      } else {
         int var1 = var0.method7464(-1714516639);
         return var1 >= 0 ? var0.field4449.field4338[var1] : null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("at")
   public Widget method7462() {
      int var1 = this.method7464(-2088605868);
      return var1 >= 0 ? this.field4449.field4338[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   public int method7457() {
      return this.field4447 * -1725791933;
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("aw")
   public Widget method7463() {
      int var1 = this.method7464(-1566879046);
      return var1 >= 0 ? this.field4449.field4338[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("am")
   public int[] method7465() {
      return Arrays.copyOf(this.field4443, 1342430018 * this.field4447);
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("bn")
   public int[] method7466() {
      return Arrays.copyOf(this.field4443, -1725791933 * this.field4447);
   }

   @ObfuscatedSignature(descriptor = "(Llz;ILlw;II)V")
   @ObfuscatedName("me")
   public static void method7443(classLZ var0, int var1, Widget var2, int var3, int var4) {
      if (var0 == null) {
         var0.method7444(var1, var2, var1, var1);
      }

      try {
         var0.field4449 = var2;
         var0.field4447 = 0;
         var0.field4444 = 0;
         if (var1 >= 0) {
            if (var4 != 1453250184) {
               return;
            }

            if (var1 < 3) {
               if (null != var2) {
                  if (var2.field4258 * 63131743 != -1) {
                     throw new RuntimeException("");
                  }

                  if (var4 != 1453250184) {
                     throw new IllegalStateException();
                  }

                  if (var2.field4373 * -1510882375 != 0) {
                     if (var4 != 1453250184) {
                        throw new IllegalStateException();
                     }

                     throw new RuntimeException("");
                  }
               }

               if (var2 != null) {
                  if (var4 != 1453250184) {
                     throw new IllegalStateException();
                  }

                  if (var2.field4338 != null) {
                     if (var1 == 0) {
                        if (var4 != 1453250184) {
                           return;
                        }

                        Widget[] var5 = var2.field4338;

                        for (int var6 = 0; var6 < var5.length; var6++) {
                           Widget var7 = var5[var6];
                           if (null != var7) {
                              if (var4 != 1453250184) {
                                 throw new IllegalStateException();
                              }

                              var0.method7448(63131743 * var7.field4258, (byte)29);
                           }
                        }
                     } else {
                        boolean var10000;
                        if (1 != var1) {
                           if (var4 != 1453250184) {
                              throw new IllegalStateException();
                           }

                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        boolean var9 = var10000;
                        if (-1 == var3) {
                           method7452(var0, var2, var9, -1397243012);
                        } else if (var3 >= 0 && var3 < var2.field4338.length) {
                           if (var4 != 1453250184) {
                              throw new IllegalStateException();
                           }

                           if (null != var2.field4338[var3]) {
                              if (var4 != 1453250184) {
                                 throw new IllegalStateException();
                              }

                              method7452(var0, var2.field4338[var3], var9, -1895341729);
                           }
                        }
                     }

                     return;
                  }

                  if (var4 != 1453250184) {
                     return;
                  }
               }

               return;
            }

            if (var4 != 1453250184) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException("");
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lz.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("ax")
   public int[] method7467(int var1) {
      try {
         return Arrays.copyOf(this.field4443, -1725791933 * this.field4447);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lz.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILlw;II)V")
   @ObfuscatedName("az")
   public void method7444(int var1, Widget var2, int var3, int var4) {
      try {
         this.field4449 = var2;
         this.field4447 = 0;
         this.field4444 = 0;
         if (var1 >= 0) {
            if (var4 != 1453250184) {
               return;
            }

            if (var1 < 3) {
               if (null != var2) {
                  if (var2.field4302 * 63131743 != -1) {
                     throw new RuntimeException("");
                  }

                  if (var4 != 1453250184) {
                     throw new IllegalStateException();
                  }

                  if (var2.field4246 * -1510882375 != 0) {
                     if (var4 != 1453250184) {
                        throw new IllegalStateException();
                     }

                     throw new RuntimeException("");
                  }
               }

               if (var2 != null) {
                  if (var4 != 1453250184) {
                     throw new IllegalStateException();
                  }

                  if (var2.field4338 != null) {
                     if (var1 == 0) {
                        if (var4 != 1453250184) {
                           return;
                        }

                        Widget[] var5 = var2.field4338;

                        for (int var6 = 0; var6 < var5.length; var6++) {
                           Widget var7 = var5[var6];
                           if (null != var7) {
                              if (var4 != 1453250184) {
                                 throw new IllegalStateException();
                              }

                              this.method7448(63131743 * var7.field4297, (byte)29);
                           }
                        }
                     } else {
                        boolean var10000;
                        if (1 != var1) {
                           if (var4 != 1453250184) {
                              throw new IllegalStateException();
                           }

                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        boolean var9 = var10000;
                        if (-1 == var3) {
                           method7452(this, var2, var9, -1397243012);
                        } else if (var3 >= 0 && var3 < var2.field4338.length) {
                           if (var4 != 1453250184) {
                              throw new IllegalStateException();
                           }

                           if (null != var2.field4338[var3]) {
                              if (var4 != 1453250184) {
                                 throw new IllegalStateException();
                              }

                              method7452(this, var2.field4338[var3], var9, -1895341729);
                           }
                        }
                     }

                     return;
                  }

                  if (var4 != 1453250184) {
                     return;
                  }
               }

               return;
            }

            if (var4 != 1453250184) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException("");
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lz.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;ZI)V")
   @ObfuscatedName("ae")
   final void method7453(Widget var1, boolean var2, int var3) {
      try {
         int var10000;
         if (var1 == this.field4449) {
            if (var3 >= -179907272) {
               throw new IllegalStateException();
            }

            var10000 = 0;
         } else {
            var10000 = var1.field4346 * -1210177335;
         }

         int var4 = var10000;
         int var5 = this.field4449 == var1 ? var1.field4338.length - 1 : var1.field4372 * 379541057;

         for (int var6 = var4; var6 <= var5; var6++) {
            if (var3 >= -179907272) {
               return;
            }

            Widget var7 = this.field4449.field4338[var6];
            if (null != var7) {
               if (var3 >= -179907272) {
                  throw new IllegalStateException();
               }

               if (var7.field4290 * 653784551 == var1.field4286 * 63131743) {
                  if (var3 >= -179907272) {
                     throw new IllegalStateException();
                  }

                  this.method7448(var7.field4246 * 63131743, (byte)41);
                  if (var2) {
                     if (var3 >= -179907272) {
                        throw new IllegalStateException();
                     }

                     if (var7.field4243 * -1210177335 <= 379541057 * var7.field4272) {
                        if (var3 >= -179907272) {
                           throw new IllegalStateException();
                        }

                        method7452(this, var7, true, -1870760730);
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lz.ae(" + ')');
      }
   }
}
