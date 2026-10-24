import java.util.HashMap;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qi")
public class classQI {
   @ObfuscatedName("af")
   final HashMap field5544;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field5547 = 1000;
   @ObfuscatedName("ae")
   final HashMap field5545;
   @ObfuscatedName("ab")
   final HashMap field5546;
   @ObfuscatedName("az")
   final HashMap field5543 = new HashMap();
   @ObfuscatedSignature(descriptor = "Lgp;")
   @ObfuscatedName("ck")
   static classGP field5548;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bb")
   void method9627() {
      this.field5543.clear();
      this.field5544.clear();
      this.field5545.clear();
      this.field5546.clear();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;I)V")
   @ObfuscatedName("ae")
   void method9599(String var1, String var2, int var3) {
      try {
         long var4 = 0L;

         try {
            var4 = Long.parseLong(var2);
         } catch (NumberFormatException var7) {
         }

         this.field5544.put(var1, var4);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "qi.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;II)V")
   @ObfuscatedName("af")
   void method9596(String var1, int var2, int var3) {
      try {
         this.field5543.put(var1, var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qi.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)J")
   @ObfuscatedName("bm")
   long method9614(String var1) {
      Long var2 = (Long)this.field5544.get(var1);
      return var2 == null ? -1L : var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;S)V")
   @ObfuscatedName("ab")
   void method9602(String var1, String var2, short var3) {
      try {
         int var4 = -1;

         try {
            var4 = Integer.parseInt(var2);
         } catch (NumberFormatException var6) {
         }

         switch (var4) {
            case 0:
               this.field5545.put(var1, false);
               return;
            case 1:
               this.field5545.put(var1, true);
               return;
            default:
               this.field5545.put(var1, null);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "qi.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;I)V")
   @ObfuscatedName("ag")
   void method9607(String var1, String var2, int var3) {
      try {
         this.field5546.put(var1, var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qi.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqi;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("rr")
   public static void method9600(classQI var0, String var1, String var2) {
      if (var0 == null) {
         var0.method9601(var1, var1);
      }

      long var3 = 0L;

      try {
         var3 = Long.parseLong(var2);
      } catch (NumberFormatException var6) {
      }

      var0.field5544.put(var1, var3);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)I")
   @ObfuscatedName("as")
   int method9611(String var1, int var2) {
      try {
         Integer var3 = (Integer)this.field5543.get(var1);
         int var10000;
         if (var3 == null) {
            if (var2 <= 578907746) {
               throw new IllegalStateException();
            }

            var10000 = -1;
         } else {
            var10000 = var3;
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qi.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("ai")
   void method9603(String var1, String var2) {
      int var3 = -1;

      try {
         var3 = Integer.parseInt(var2);
      } catch (NumberFormatException var5) {
      }

      switch (var3) {
         case 0:
            this.field5545.put(var1, false);
            return;
         case 1:
            this.field5545.put(var1, true);
            return;
         default:
            this.field5545.put(var1, null);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqi;Ljava/lang/String;)J")
   @ObfuscatedName("ca")
   public static long method9615(classQI var0, String var1) {
      Long var2 = (Long)var0.field5544.get(var1);
      return var2 == null ? -1L : var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("aw")
   void method9604(String var1, String var2) {
      int var3 = -1;

      try {
         var3 = Integer.parseInt(var2);
      } catch (NumberFormatException var5) {
      }

      switch (var3) {
         case 0:
            this.field5545.put(var1, false);
            return;
         case 1:
            this.field5545.put(var1, true);
            return;
         default:
            this.field5545.put(var1, null);
      }
   }

   public classQI() {
      this.field5544 = new HashMap();
      this.field5545 = new HashMap();
      this.field5546 = new HashMap();
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   void method9628(int var1) {
      try {
         this.field5543.clear();
         this.field5544.clear();
         this.field5545.clear();
         this.field5546.clear();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qi.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)J")
   @ObfuscatedName("ax")
   long method9616(String var1, byte var2) {
      try {
         Long var3 = (Long)this.field5544.get(var1);
         return var3 == null ? -1L : var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qi.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("aj")
   void method9597(String var1, int var2) {
      this.field5543.put(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("ap")
   void method9601(String var1, String var2) {
      long var3 = 0L;

      try {
         var3 = Long.parseLong(var2);
      } catch (NumberFormatException var6) {
      }

      this.field5544.put(var1, var3);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ba")
   void method9629() {
      this.field5543.clear();
      this.field5544.clear();
      this.field5545.clear();
      this.field5546.clear();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("ar")
   void method9605(String var1, String var2) {
      int var3 = -1;

      try {
         var3 = Integer.parseInt(var2);
      } catch (NumberFormatException var5) {
      }

      switch (var3) {
         case 0:
            this.field5545.put(var1, false);
            return;
         case 1:
            this.field5545.put(var1, true);
            return;
         default:
            this.field5545.put(var1, null);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;I)I")
   @ObfuscatedName("ao")
   public static int method9632(CharSequence var0, int var1) {
      try {
         int var2 = var0.length();
         int var3 = 0;

         for (int var4 = 0; var4 < var2; var4++) {
            var3 = (var3 << 5) - var3 + classOG.method8465(var0.charAt(var4), (byte)-41);
         }

         return var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "qi.ao(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("ak")
   void method9606(String var1, String var2) {
      int var3 = -1;

      try {
         var3 = Integer.parseInt(var2);
      } catch (NumberFormatException var5) {
      }

      switch (var3) {
         case 0:
            this.field5545.put(var1, false);
            return;
         case 1:
            this.field5545.put(var1, true);
            return;
         default:
            this.field5545.put(var1, null);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("av")
   void method9608(String var1, String var2) {
      this.field5546.put(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("at")
   void method9609(String var1, String var2) {
      this.field5546.put(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("al")
   void method9594(String var1, String var2) {
      int var3 = 0;

      try {
         var3 = Integer.parseInt(var2);
      } catch (NumberFormatException var5) {
      }

      this.field5543.put(var1, var3);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("ah")
   int method9612(String var1) {
      Integer var2 = (Integer)this.field5543.get(var1);
      return var2 == null ? -1 : var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("bn")
   int method9613(String var1) {
      Integer var2 = (Integer)this.field5543.get(var1);
      return var2 == null ? -1 : var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)J")
   @ObfuscatedName("bx")
   long method9617(String var1) {
      Long var2 = (Long)this.field5544.get(var1);
      return var2 == null ? -1L : var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)J")
   @ObfuscatedName("bc")
   long method9618(String var1) {
      Long var2 = (Long)this.field5544.get(var1);
      return var2 == null ? -1L : var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)J")
   @ObfuscatedName("br")
   long method9619(String var1) {
      Long var2 = (Long)this.field5544.get(var1);
      return var2 == null ? -1L : var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/Boolean;")
   @ObfuscatedName("aa")
   Boolean method9625(String var1, int var2) {
      try {
         return (Boolean)this.field5545.get(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qi.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bg")
   String method9620(String var1) {
      return (String)this.field5546.get(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqi;)V")
   @ObfuscatedName("xf")
   public static void method9630(classQI var0) {
      var0.field5543.clear();
      var0.field5544.clear();
      var0.field5545.clear();
      var0.field5546.clear();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bj")
   String method9621(String var1) {
      return (String)this.field5546.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bk")
   String method9622(String var1) {
      return (String)this.field5546.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bt")
   String method9623(String var1) {
      return (String)this.field5546.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/Boolean;")
   @ObfuscatedName("bo")
   Boolean method9626(String var1) {
      return (Boolean)this.field5545.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("an")
   void method9610(String var1, String var2) {
      this.field5546.put(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("ac")
   String method9624(String var1, int var2) {
      try {
         return (String)this.field5546.get(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qi.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bi")
   void method9631() {
      this.field5543.clear();
      this.field5544.clear();
      this.field5545.clear();
      this.field5546.clear();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;I)V")
   @ObfuscatedName("az")
   void method9595(String var1, String var2, int var3) {
      try {
         int var4 = 0;

         try {
            var4 = Integer.parseInt(var2);
         } catch (NumberFormatException var6) {
         }

         this.field5543.put(var1, var4);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "qi.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("ad")
   static int method9633(int var0, Script var1, boolean var2, byte var3) {
      try {
         boolean var4 = true;
         Widget var5;
         if (var0 >= 2000) {
            if (var3 == 2) {
               throw new IllegalStateException();
            }

            var0 -= 1000;
            var5 = classWK.field6689.method7421(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371], (byte)80);
            var4 = false;
         } else {
            Widget var10000;
            if (var2) {
               if (var3 == 2) {
                  throw new IllegalStateException();
               }

               var10000 = classSZ.field6108;
            } else {
               var10000 = TileItem.field1556;
            }

            var5 = var10000;
         }

         if (var0 == 1300) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               int var23 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] - 1;
               if (var23 >= 0) {
                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }

                  if (var23 < 32) {
                     String var29 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
                     var29 = classUP.method11483(var29, Nameable.method10228(-2032868552), classBB.field334, classBB.field333, (byte)0);
                     var5.method7255(var23, var29, (byte)-94);
                     return 1;
                  }

                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }
               }

               classBB.field338 -= 1222866659;
               return 1;
            }
         } else if (1301 == var0) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var22 = classBB.field323[DynamicObject.field1676 * -324749371];
               int var28 = classBB.field323[1 + -324749371 * DynamicObject.field1676];
               var5.field4309 = classWK.field6689.method7422(var22, var28, (byte)-4);
               return 1;
            }
         } else if (var0 == 1302) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               boolean var38;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }

                  var38 = true;
               } else {
                  var38 = false;
               }

               var5.field4312 = var38;
               return 1;
            }
         } else if (var0 == 1303) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               var5.field4366 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 230129403;
               return 1;
            }
         } else if (var0 == 1304) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               var5.field4311 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * -2074329479;
               return 1;
            }
         } else if (var0 == 1305) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               String var20 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               var20 = classUP.method11483(var20, Nameable.method10228(-1357449656), classBB.field334, classBB.field333, (byte)0);
               var5.field4307 = var20;
               return 1;
            }
         } else if (var0 == 1306) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               String var18 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               var18 = classUP.method11483(var18, Nameable.method10228(-860122174), classBB.field334, classBB.field333, (byte)0);
               var5.field4343 = var18;
               return 1;
            }
         } else if (1307 == var0) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               var5.field4335 = null;
               var5.field4351 = (String[][])null;
               return 1;
            }
         } else if (1308 == var0) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               boolean var10001;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var5.field4371 = var10001;
               return 1;
            }
         } else if (1309 == var0) {
            DynamicObject.field1676 -= 120041229;
            return 1;
         } else if (var0 == 1310) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               int var17 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] - 1;
               if (var17 >= 0) {
                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }

                  if (var17 <= 9) {
                     if (var3 == 2) {
                        throw new IllegalStateException();
                     }

                     if (var5.field4351 != null) {
                        var5.field4351[var17] = null;
                        return 1;
                     }
                  }
               }

               return 1;
            }
         } else if (var0 == 1311) {
            int var16 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] - 1;
            int var27 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] - 1;
            if (var27 >= 0) {
               if (var3 == 2) {
                  throw new IllegalStateException();
               }

               if (var27 <= 9) {
                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }

                  if (var16 >= 0) {
                     String var36 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
                     var36 = classUP.method11483(var36, Nameable.method10228(-77610910), classBB.field334, classBB.field333, (byte)0);
                     var5.method7257(var27, var16, var36, -512976453);
                     return 1;
                  }
               }
            }

            throw new RuntimeException();
         } else if (var0 == 1312) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               int var15 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (var15 == -1) {
                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }

                  var5.field4308 = 695305012;
               } else if (var15 >= 1) {
                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }

                  if (var15 <= 32) {
                     if (var3 == 2) {
                        throw new IllegalStateException();
                     }

                     var5.field4308 = 173826253 * (var15 - 1);
                  }
               }

               return 1;
            }
         } else if (1350 == var0) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               byte[] var14 = null;
               byte[] var26 = null;
               if (var4) {
                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }

                  DynamicObject.field1676 -= 1200412290;

                  byte var33;
                  for (var33 = 0; var33 < 10; var33 += 2) {
                     if (var3 == 2) {
                        throw new IllegalStateException();
                     }

                     if (classBB.field323[DynamicObject.field1676 * -324749371 + var33] < 0) {
                        if (var3 == 2) {
                           throw new IllegalStateException();
                        }
                        break;
                     }
                  }

                  if (var33 > 0) {
                     if (var3 == 2) {
                        throw new IllegalStateException();
                     }

                     var14 = new byte[var33 / 2];
                     var26 = new byte[var33 / 2];

                     for (int var34 = var33 - 2; var34 >= 0; var34 -= 2) {
                        var14[var34 / 2] = (byte)classBB.field323[DynamicObject.field1676 * -324749371 + var34];
                        var26[var34 / 2] = (byte)classBB.field323[1 + DynamicObject.field1676 * -324749371 + var34];
                     }
                  }
               } else {
                  DynamicObject.field1676 -= 240082458;
                  var14 = new byte[]{(byte)classBB.field323[DynamicObject.field1676 * -324749371]};
                  var26 = new byte[]{(byte)classBB.field323[DynamicObject.field1676 * -324749371 + 1]};
               }

               int var35 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] - 1;
               if (var35 >= 0) {
                  if (var35 <= 9) {
                     classMK.method7638(var5, var35, var14, var26, (byte)-74);
                     return 1;
                  }

                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }
               }

               throw new RuntimeException();
            }
         } else if (var0 == 1351) {
            DynamicObject.field1676 -= 240082458;
            byte var13 = 10;
            byte[] var25 = new byte[]{(byte)classBB.field323[-324749371 * DynamicObject.field1676]};
            byte[] var32 = new byte[]{(byte)classBB.field323[DynamicObject.field1676 * -324749371 + 1]};
            classMK.method7638(var5, var13, var25, var32, (byte)-7);
            return 1;
         } else if (1352 == var0) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 360123687;
               int var12 = classBB.field323[DynamicObject.field1676 * -324749371] - 1;
               int var24 = classBB.field323[1 + DynamicObject.field1676 * -324749371];
               int var31 = classBB.field323[-324749371 * DynamicObject.field1676 + 2];
               if (var12 >= 0) {
                  if (var12 <= 9) {
                     CollisionData.method5413(var5, var12, var24, var31, 2090793764);
                     return 1;
                  }

                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }
               }

               throw new RuntimeException();
            }
         } else if (1353 == var0) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               byte var11 = 10;
               int var7 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               int var8 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               CollisionData.method5413(var5, var11, var7, var8, 1253285194);
               return 1;
            }
         } else if (var0 == 1354) {
            if (var3 == 2) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               int var10 = classBB.field323[DynamicObject.field1676 * -324749371] - 1;
               if (var10 >= 0) {
                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }

                  if (var10 <= 9) {
                     classMT.method7736(var5, var10, 2052530156);
                     return 1;
                  }

                  if (var3 == 2) {
                     throw new IllegalStateException();
                  }
               }

               throw new RuntimeException();
            }
         } else if (var0 == 1355) {
            byte var6 = 10;
            classMT.method7736(var5, var6, 2105626186);
            return 1;
         } else {
            return 2;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "qi.ad(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("aq")
   void method9598(String var1, int var2) {
      this.field5545.put(var1, var2);
   }
}
