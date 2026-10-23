import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetDrag;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pq")
public class classPQ {
   @ObfuscatedSignature(descriptor = "Ljx;")
   @ObfuscatedName("az")
   final classJX field5380;
   @ObfuscatedSignature(descriptor = "Ljv;")
   @ObfuscatedName("af")
   final classJV field5381;
   @ObfuscatedSignature(descriptor = "Lpu;")
   @ObfuscatedName("ae")
   final classPU field5379;
   @ObfuscatedSignature(descriptor = "Lqs;")
   @ObfuscatedName("ab")
   final classQS field5382 = new classQS();

   @ObfuscatedSignature(descriptor = "(IIILvv;Lqn;I)V")
   @ObfuscatedName("az")
   public void method9137(int var1, int var2, int var3, classVV var4, classQN var5, int var6) {
      try {
         var4.field6610 = var1 * 1130371137;
         classCN.method2541(var4.field6610 * -77072447, var2, var3, false, var4, var5, (short)217);
         this.method9188(var1, var4, -1584753516);
         method9181(this, -77072447 * var4.field6610, var4, 1310148379);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "pq.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;I)V")
   @ObfuscatedName("af")
   public void method9140(classVV var1, int var2) {
      try {
         if (-1 != -77072447 * var1.field6610) {
            var1.method7429(var1.field6610 * -77072447, 1153929065);
            var1.field6610 = -1130371137;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pq.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpk;ZLvv;B)V")
   @ObfuscatedName("ab")
   public void method9150(WidgetNode var1, boolean var2, classVV var3, byte var4) {
      this.method9203(var1, var2, var3);

      try {
         int var5 = 2023647411 * var1.field5275;
         var1.vmethod398();
         if (var2) {
            if (var4 >= 0) {
               throw new IllegalStateException();
            }

            var3.method7429(var5, 1143930425);
         }

         classVV.method12204(var3, var5, -1118565855);
         method9173(this, classQP.field5580, var3, -1117646971);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "pq.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILnv;)V")
   @ObfuscatedName("lp")
   public static void method9201(int var0, classNV var1) {
      for (ScriptEvent var2 = (ScriptEvent)var1.method8263(); var2 != null; var2 = (ScriptEvent)var1.method8265()) {
         Widget var3 = var2.method14185();
         int var4 = WidgetUtil.componentToInterface(var3.getId());
         if (var0 == var4) {
            var2.method12135();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpk;ZLvv;)V")
   @ObfuscatedName("uo")
   public void method9207(WidgetNode var1, boolean var2, classVV var3) {
      this.method9150(var1, var2, var3, (byte)-113);
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIIIIIIIILvv;ILqn;)V")
   @ObfuscatedName("of")
   public void method9205(
      Widget[] var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      classVV var12,
      int var13,
      classQN var14
   ) {
      for (int var15 = var2; var15 <= var3; var15++) {
         Widget var16 = var1[var15];
         if (var16 != null && var16.field4340 * -1502647899 == var4 && var16.field4261 * 653784551 == var5 && !var16.isSelfHidden()) {
            if (var4 != -1) {
               var16.method7361(var4);
            }

            int var17 = var10 + var16.getRelativeX();
            int var18 = var11 + var16.getRelativeY();
            var16.method7368(var17);
            var16.method7364(var18);
            if (var16.getType() == 0 && var16.getIndex() == -1) {
               WidgetNode var19 = (WidgetNode)var12.field6611.method13434(var16.getId());
               if (var19 != null) {
                  int var20 = var16.getId();
                  int var21 = var19.getId();
                  Widget[] var22 = classWK.field6689.field4438[var21];

                  for (Widget var26 : var22) {
                     if (var26.field4340 * -1502647899 == -1) {
                        var26.method7361(var20);
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;IIIILqn;B)V")
   @ObfuscatedName("as")
   public void method9204(classVV var1, int var2, int var3, int var4, int var5, classQN var6, byte var7) {
      if (client.method2338()) {
         var5 = 0;
      }

      classQN var13 = var6;
      int var12 = var5;
      int var11 = var4;
      int var10 = var3;
      int var9 = var2;
      classVV var8 = var1;
      classPQ var16 = this;
      boolean var14 = true;

      try {
         classVC.method11999(classVV.method12187(var8, 1593113106), (byte)-22);
         var8.method12226((byte)0).method12261(-624973020);
         if (-77072447 * var8.field6610 == -1) {
            if (!var14) {
               throw new IllegalStateException();
            }
         } else {
            classQS.method9831(
               var16.field5382,
               classTZ.field6249 * 2090434187,
               classTZ.field6253 * -38255113,
               classTZ.field6262 * -2144333897,
               classTZ.field6259 * 1229235359,
               classTZ.field6264 * 1422993977,
               classTZ.field6255 * 1243590197,
               var12,
               3392313
            );
            var16.method9161(-77072447 * var8.field6610, 0, 0, var9, var10, 0, 0, var8, var11, var13, (byte)-8);
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "pq.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIILvv;ILqn;B)V")
   @ObfuscatedName("ax")
   void method9161(int var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8, int var9, classQN var10, byte var11) {
      try {
         if (var8.method7425(var1, -56682132)) {
            method9164(
               this, var8.field4438[var1], 0, var8.field4438[var1].length - 1, -1, -1, var2, var3, var4, var5, var6, var7, var8, var9, var10, (byte)-103
            );
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "pq.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIIIIILvv;I)V")
   @ObfuscatedName("aa")
   void method9167(Widget var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8, int var9) {
      try {
         byte var10;
         if (var8.field6617) {
            if (var9 <= 945021779) {
               throw new IllegalStateException();
            }

            var10 = 32;
         } else {
            var10 = 0;
         }

         label151: {
            var8.field6617 = false;
            if (classQS.method9834(this.field5382, -361602593) != 1) {
               if (this.field5381.vmethod168(-1996096499)) {
                  break label151;
               }

               if (var9 <= 945021779) {
                  throw new IllegalStateException();
               }

               if (classQS.method9834(this.field5382, -361602593) != 4) {
                  break label151;
               }

               if (var9 <= 945021779) {
                  throw new IllegalStateException();
               }
            }

            if (var6 >= var2) {
               if (var9 <= 945021779) {
                  throw new IllegalStateException();
               }

               if (var6 < 16 + var2) {
                  if (var9 <= 945021779) {
                     throw new IllegalStateException();
                  }

                  if (var7 >= var3) {
                     if (var9 <= 945021779) {
                        return;
                     }

                     if (var7 < var3 + 16) {
                        if (var9 <= 945021779) {
                           return;
                        }

                        var1.field4329 -= -299739220;
                        break label151;
                     }
                  }
               }
            }

            if (var6 >= var2) {
               if (var9 <= 945021779) {
                  throw new IllegalStateException();
               }

               if (var6 < 16 + var2) {
                  if (var9 <= 945021779) {
                     throw new IllegalStateException();
                  }

                  if (var7 >= var3 + var4 - 16) {
                     if (var9 <= 945021779) {
                        throw new IllegalStateException();
                     }

                     if (var7 < var3 + var4) {
                        if (var9 <= 945021779) {
                           throw new IllegalStateException();
                        }

                        var1.field4329 += -299739220;
                        break label151;
                     }
                  }
               }
            }

            if (var6 >= var2 - var10) {
               if (var9 <= 945021779) {
                  return;
               }

               if (var6 < var10 + var2 + 16 && var7 >= 16 + var3 && var7 < var4 + var3 - 16) {
                  if (var9 <= 945021779) {
                     throw new IllegalStateException();
                  }

                  int var11 = var4 * (var4 - 32) / var5;
                  if (var11 < 8) {
                     if (var9 <= 945021779) {
                        throw new IllegalStateException();
                     }

                     var11 = 8;
                  }

                  int var12 = var7 - var3 - 16 - var11 / 2;
                  int var13 = var4 - 32 - var11;
                  var1.field4329 = (var5 - var4) * var12 / var13 * -1148676629;
                  var8.field6617 = true;
               }
            }
         }

         if (this.field5382.method9853((byte)-32) != 0) {
            if (var9 <= 945021779) {
               throw new IllegalStateException();
            }

            int var15 = var1.field4247 * 1115597881;
            if (var6 >= var2 - var15 && var7 >= var3) {
               if (var9 <= 945021779) {
                  throw new IllegalStateException();
               }

               if (var6 < 16 + var2) {
                  if (var9 <= 945021779) {
                     throw new IllegalStateException();
                  }

                  if (var7 <= var3 + var4) {
                     var1.field4329 = var1.field4329 + this.field5382.method9853((byte)-8) * -150840753;
                  }
               }
            }
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "pq.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILqp;Lvv;)V")
   @ObfuscatedName("bf")
   void method9177(int var1, classQP var2, classVV var3) {
      if (var3.method7425(var1, 2086532451)) {
         this.method9187(var3.field4438[var1], var2, var3, (byte)3);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILqp;Lvv;B)V")
   @ObfuscatedName("al")
   void method9178(int var1, classQP var2, classVV var3, byte var4) {
      try {
         if (!var3.method7425(var1, 1925767696)) {
            if (var4 < 2) {
               ;
            }
         } else {
            this.method9187(var3.field4438[var1], var2, var3, (byte)3);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "pq.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;IIIILqn;)V")
   @ObfuscatedName("br")
   public void method9158(classVV var1, int var2, int var3, int var4, int var5, classQN var6) {
      classVC.method11999(classVV.method12187(var1, 706353244), (byte)-30);
      var1.method12226((byte)0).method12261(-624973020);
      if (-77072447 * var1.field6610 != -1) {
         classQS.method9831(
            this.field5382,
            classTZ.field6249 * 2090434187,
            classTZ.field6253 * -38255113,
            classTZ.field6262 * -2144333897,
            classTZ.field6259 * 1229235359,
            classTZ.field6264 * 1422993977,
            classTZ.field6255 * 1243590197,
            var5,
            3392313
         );
         this.method9161(-77072447 * var1.field6610, 0, 0, var2, var3, 0, 0, var1, var4, var6, (byte)33);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILvv;I)V")
   @ObfuscatedName("aq")
   void method9188(int var1, classVV var2, int var3) {
      try {
         if (!var2.method7425(var1, 822839678)) {
            if (var3 >= -1159932592) {
               throw new IllegalStateException();
            }
         } else {
            Widget[] var4 = var2.field4438[var1];

            for (int var5 = 0; var5 < var4.length; var5++) {
               Widget var6 = var4[var5];
               if (null == var6) {
                  if (var3 >= -1159932592) {
                     throw new IllegalStateException();
                  }
               } else {
                  var6.field4319 = 0;
                  var6.field4325 = 0;
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "pq.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILvv;)V")
   @ObfuscatedName("zg")
   public void method9208(int var1, classVV var2) {
      method9181(this, var1, var2, 1310148379);
   }

   @ObfuscatedSignature(descriptor = "(Lqn;Lvv;B)V")
   @ObfuscatedName("ad")
   public void method9191(classQN var1, classVV var2, byte var3) {
      try {
         ScriptEvent var4 = var1.method9719(-1432447279);

         while (var4 != null) {
            ScriptEvent var5 = var4;
            var4 = var1.method9719(-1432447279);
            Widget var6 = var5.method14144(-457118805);
            if (var6.field4258 * 63131743 >= 0) {
               Widget var7 = var2.method7421(-1502647899 * var6.field4340, (byte)126);
               if (null == var7) {
                  continue;
               }

               if (var3 == 1) {
                  throw new IllegalStateException();
               }

               if (var7.field4338 == null) {
                  continue;
               }

               if (var3 == 1) {
                  throw new IllegalStateException();
               }

               if (63131743 * var6.field4258 >= var7.field4338.length) {
                  continue;
               }

               if (var3 == 1) {
                  return;
               }

               if (var6 != var7.field4338[var6.field4258 * 63131743]) {
                  if (var3 == 1) {
                     throw new IllegalStateException();
                  }
                  continue;
               }
            }

            classSZ.method10771(var5, -330603966);
         }

         var4 = var1.method9723(-1956158393);

         while (null != var4) {
            ScriptEvent var11 = var4;
            var4 = var1.method9723(764118853);
            Widget var14 = var11.method14144(980686676);
            if (var14.field4258 * 63131743 >= 0) {
               if (var3 == 1) {
                  throw new IllegalStateException();
               }

               Widget var17 = var2.method7421(-1502647899 * var14.field4340, (byte)18);
               if (null == var17) {
                  continue;
               }

               if (var3 == 1) {
                  return;
               }

               if (null == var17.field4338) {
                  continue;
               }

               if (var3 == 1) {
                  throw new IllegalStateException();
               }

               if (63131743 * var14.field4258 >= var17.field4338.length) {
                  continue;
               }

               if (var3 == 1) {
                  throw new IllegalStateException();
               }

               if (var14 != var17.field4338[var14.field4258 * 63131743]) {
                  if (var3 == 1) {
                     throw new IllegalStateException();
                  }
                  continue;
               }
            }

            classSZ.method10771(var11, -1992248587);
         }

         var4 = classQN.method9717(var1, (byte)100);

         while (var4 != null) {
            if (var3 == 1) {
               throw new IllegalStateException();
            }

            ScriptEvent var12 = var4;
            var4 = classQN.method9717(var1, (byte)74);
            Widget var15 = var12.method14144(2009836360);
            if (var15.field4258 * 63131743 >= 0) {
               Widget var18 = var2.method7421(var15.field4340 * -1502647899, (byte)44);
               if (null == var18) {
                  continue;
               }

               if (var3 == 1) {
                  return;
               }

               if (null == var18.field4338) {
                  continue;
               }

               if (var3 == 1) {
                  throw new IllegalStateException();
               }

               if (63131743 * var15.field4258 >= var18.field4338.length) {
                  continue;
               }

               if (var15 != var18.field4338[63131743 * var15.field4258]) {
                  if (var3 == 1) {
                     throw new IllegalStateException();
                  }
                  continue;
               }
            }

            classSZ.method10771(var12, -1724521522);
         }

         boolean var13 = false;

         for (classFM var16 = var1.method9724((byte)101); null != var16; var16 = var1.method9724((byte)1)) {
            if (var3 == 1) {
               throw new IllegalStateException();
            }

            if (12 == var16.field2304.field4373 * -1510882375) {
               if (var3 == 1) {
                  return;
               }

               var13 = true;
            }

            method9196(this, var16, var2, 1472436763);
         }

         if (!var13 && 1 == 1229235359 * classTZ.field6259) {
            if (var3 == 1) {
               throw new IllegalStateException();
            }

            this.field5379.vmethod357(null, 2109787976);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "pq.ad(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpq;Llw;IIIIIILvv;)V")
   @ObfuscatedName("cm")
   public static void method9168(classPQ var0, Widget var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8) {
      if (var0 == null) {
         var0.method9190(var2, var8);
      }

      byte var9;
      if (var8.field6617) {
         var9 = 32;
      } else {
         var9 = 0;
      }

      var8.field6617 = false;
      if (classQS.method9834(var0.field5382, -361602593) == 1 || !var0.field5381.vmethod168(61514311) && classQS.method9834(var0.field5382, -361602593) == 4) {
         if (var6 >= var2 && var6 < 16 + var2 && var7 >= var3 && var7 < var3 + 16) {
            var1.field4329 -= -299739220;
         } else if (var6 >= var2 && var6 < 16 + var2 && var7 >= var3 + var4 - 16 && var7 < var3 + var4) {
            var1.field4329 += -299739220;
         } else if (var6 >= var2 - var9 && var6 < var9 + var2 + 16 && var7 >= 16 + var3 && var7 < var4 + var3 - 16) {
            int var10 = var4 * (var4 - 32) / var5;
            if (var10 < 8) {
               var10 = 8;
            }

            int var11 = var7 - var3 - 16 - var10 / 2;
            int var12 = var4 - 32 - var10;
            var1.field4329 = (var5 - var4) * var11 / var12 * -1148676629;
            var8.field6617 = true;
         }
      }

      if (var0.field5382.method9853((byte)-13) != 0) {
         int var13 = var1.field4247 * 1115597881;
         if (var6 >= var2 - var13 && var7 >= var3 && var6 < 16 + var2 && var7 <= var3 + var4) {
            var1.field4329 = var1.field4329 + var0.field5382.method9853((byte)-70) * -150840753;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;)V")
   @ObfuscatedName("av")
   public void method9141(classVV var1) {
      if (-1 != -77072447 * var1.field6610) {
         var1.method7429(var1.field6610 * -77072447, -1465814359);
         var1.field6610 = -1130371137;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpq;[Llw;IIIIIIIIIILvv;ILqn;B)V")
   @ObfuscatedName("px")
   public static void method9164(
      classPQ var0,
      Widget[] var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      classVV var12,
      int var13,
      classQN var14,
      byte var15
   ) {
      if (var0 == null) {
         var0.method9166(var1, var2, var2, var2, var2, var2, var2, var2, var2, var2, var2, var12, var2, var14, var15);
      } else {
         var0.method9205(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14);

         try {
            boolean var16 = var0.field5381.vmethod156((byte)0);
            classFA var17 = var0.field5380.vmethod72(94586861);

            for (int var18 = var2; var18 <= var3; var18++) {
               if (var15 >= 9) {
                  throw new IllegalStateException();
               }

               Widget var19 = var1[var18];
               if (var19 == null) {
                  if (var15 >= 9) {
                     throw new IllegalStateException();
                  }
               } else if (-1502647899 * var19.field4340 == var4) {
                  if (var15 >= 9) {
                     return;
                  }

                  if (653784551 * var19.field4261 == var5 && var12.method12194(var19, (byte)-45)) {
                     if (var19.field4273) {
                        if (var12.method12196(var19, (byte)0)) {
                           continue;
                        }
                     } else if (var19.field4373 * -1510882375 == 0) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (!classVV.method12187(var12, 1645420575).method12002(var19, (byte)68)) {
                           if (var15 >= 9) {
                              return;
                           }

                           if (var12.method12196(var19, (byte)0)) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }
                              continue;
                           }
                        }
                     }

                     if (-1510882375 * var19.field4373 == 11) {
                        if (var19.method7325(var12, var0.field5380.vmethod78((byte)7), 2146600203)) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var19.method7296(1355775728)) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              classFC.method4609(var19.field4338, var19, true, var12, var14, -1178832808);
                              Widget.method7322(var19, -1626603432)
                                 .method7472((byte)-113)
                                 .method7741(3, Widget.method7322(var19, -1626603432).method7521(1235443706), (byte)10);
                           }

                           if (null != var19.field4396) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              ScriptEvent var20 = classUQ.method11527(var19, 1821758521).method14195(var19.field4396, (byte)2).method14226((byte)13);
                              var14.method9709(var20, 1697206599);
                           }
                        }
                     } else if (12 == var19.field4373 * -1510882375) {
                        var19.method7350(var12, 1317942401);
                     }

                     int var38 = var19.field4279 * 744286069 + var10;
                     int var21 = var11 + 1570844829 * var19.field4255;
                     int var22;
                     int var23;
                     int var24;
                     int var25;
                     if (9 == -1510882375 * var19.field4373) {
                        if (var15 >= 9) {
                           return;
                        }

                        int var26 = var38;
                        int var27 = var21;
                        int var28 = 1115597881 * var19.field4247 + var38;
                        int var29 = var21 + var19.field4257 * 293773697;
                        if (var28 < var38) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           var26 = var28;
                           var28 = var38;
                        }

                        if (var29 < var21) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           var27 = var29;
                           var29 = var21;
                        }

                        var28++;
                        var29++;
                        int var10000;
                        if (var26 > var6) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           var10000 = var26;
                        } else {
                           var10000 = var6;
                        }

                        var22 = var10000;
                        if (var27 > var7) {
                           if (var15 >= 9) {
                              return;
                           }

                           var10000 = var27;
                        } else {
                           var10000 = var7;
                        }

                        var23 = var10000;
                        var24 = var28 < var8 ? var28 : var8;
                        if (var29 < var9) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           var10000 = var29;
                        } else {
                           var10000 = var9;
                        }

                        var25 = var10000;
                     } else {
                        int var39 = var19.field4247 * 1115597881 + var38;
                        int var41 = var19.field4257 * 293773697 + var21;
                        int var98;
                        if (var38 > var6) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           var98 = var38;
                        } else {
                           var98 = var6;
                        }

                        var22 = var98;
                        if (var21 > var7) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           var98 = var21;
                        } else {
                           var98 = var7;
                        }

                        var23 = var98;
                        if (var39 < var8) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           var98 = var39;
                        } else {
                           var98 = var8;
                        }

                        var24 = var98;
                        var25 = var41 < var9 ? var41 : var9;
                     }

                     var12.method12226((byte)0).method12263(var19, var38, var21, 2103779794);
                     boolean var40 = false;
                     if (var19.field4358) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        switch (var12.field6613 * -698898037) {
                           case 0:
                              var40 = true;
                           case 1:
                           default:
                              break;
                           case 2:
                              if (-1163656551 * var19.field4327 >>> 16 == -336184109 * var12.field6614) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 var40 = true;
                              }
                              break;
                           case 3:
                              if (-1163656551 * var19.field4327 == var12.field6614 * -336184109) {
                                 var40 = true;
                              }
                        }
                     }

                     if (!var40) {
                        if (var15 >= 9) {
                           return;
                        }

                        if (var19.field4273) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var22 >= var24) {
                              continue;
                           }

                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var23 >= var25) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }
                              continue;
                           }
                        }
                     }

                     if (var19.field4273) {
                        if (var19.field4401) {
                           if (var0.field5382.method9838((byte)25) >= var22) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var0.field5382.method9840(-391255169) >= var23) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var0.field5382.method9838((byte)84) < var24 && var0.field5382.method9840(308056551) < var25) {
                                    var14.method9726(1763721559);
                                    var12.method12226((byte)0).method12317((byte)-22);
                                    if (!var16) {
                                       if (var15 >= 9) {
                                          return;
                                       }

                                       var0.field5379.vmethod317(891814198);
                                    }
                                 }
                              }
                           }
                        } else if (var19.field4326) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var0.field5382.method9838((byte)27) >= var22) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var0.field5382.method9840(121629884) >= var23 && var0.field5382.method9838((byte)9) < var24) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (var0.field5382.method9840(78121091) < var25) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    var14.method9733(-810050472);
                                 }
                              }
                           }
                        }
                     }

                     int var42 = var0.field5382.method9838((byte)45);
                     int var44 = var0.field5382.method9840(844620399);
                     if (var0.field5382.method9843(1784200476) != 0) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        var42 = var0.field5382.method9848((byte)60);
                        var44 = var0.field5382.method9850(1997119720);
                     }

                     boolean var101;
                     label1502: {
                        if (var42 >= var22) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var44 >= var23 && var42 < var24) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var44 < var25) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 var101 = true;
                                 break label1502;
                              }
                           }
                        }

                        var101 = false;
                     }

                     boolean var46 = var101;
                     if (var19.field4332 * -90794217 == 1337) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (!var0.field5381.vmethod166((byte)-2)) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (!var16) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var46) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 var0.field5379.vmethod321(var42, var44, var22, var23, -113743310);
                              }
                           }
                        }
                     } else if (1338 == -90794217 * var19.field4332) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        classNS.method8041(var19, var38, var21, (byte)84);
                     } else {
                        if (1400 == var19.field4332 * -90794217) {
                           if (var15 >= 9) {
                              return;
                           }

                           RenderOverview.method12456(
                              var0.field5380.vmethod84(1601996065),
                              var42,
                              var44,
                              var46,
                              var38,
                              var21,
                              var19.field4247 * 1115597881,
                              var19.field4257 * 293773697,
                              (byte)79
                           );
                        }

                        if (!var16 && var46) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (-90794217 * var19.field4332 == 1400) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              var0.field5380
                                 .vmethod84(454138964)
                                 .method12645(var38, var21, var19.field4247 * 1115597881, 293773697 * var19.field4257, var42, var44, -757606060);
                           } else {
                              var0.field5379.vmethod324(var19, (byte)-37);
                           }
                        }

                        if (var40) {
                           for (int var30 = 0; var30 < var19.field4310.length; var30++) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              boolean var31 = false;
                              boolean var32 = false;
                              if (!var31) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 label1440:
                                 if (null != var19.field4310[var30]) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    int var33 = 0;

                                    while (true) {
                                       if (var33 >= var19.field4310[var30].length) {
                                          break label1440;
                                       }

                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       boolean var34 = false;
                                       if (null != var19.field4402) {
                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          var34 = var17.method4563(var19.field4310[var30][var33], 1030177718);
                                       }

                                       label1528: {
                                          if (!var0.field5380.vmethod74((byte)29).method5087(var19.field4310[var30][var33], 1520410371)) {
                                             if (!var34) {
                                                break label1528;
                                             }

                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }
                                          }

                                          var31 = true;
                                          if (var19.field4402 != null) {
                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }

                                             if (var19.field4402[var30] > var13) {
                                                break label1440;
                                             }

                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }
                                          }

                                          byte var35 = var19.field4344[var30][var33];
                                          if (var35 == 0) {
                                             break;
                                          }

                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          if ((var35 & 8) != 0) {
                                             if (var17.method4563(86, -207049828) || var17.method4563(82, -227637202)) {
                                                break label1528;
                                             }

                                             if (var17.method4563(81, 53705378)) {
                                                if (var15 >= 9) {
                                                   throw new IllegalStateException();
                                                }
                                                break label1528;
                                             }
                                          }

                                          if (0 != (var35 & 2)) {
                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }

                                             if (!var17.method4563(86, 2129917571)) {
                                                if (var15 >= 9) {
                                                   throw new IllegalStateException();
                                                }
                                                break label1528;
                                             }
                                          }

                                          if ((var35 & 1) != 0) {
                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }

                                             if (!var17.method4563(82, 125044319)) {
                                                break label1528;
                                             }
                                          }

                                          if ((var35 & 4) == 0) {
                                             break;
                                          }

                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          if (var17.method4563(81, -99888740)) {
                                             break;
                                          }

                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }
                                       }

                                       var33++;
                                    }

                                    var32 = true;
                                 }
                              }

                              if (var32) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var30 < 10) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    var0.field5379
                                       .vmethod329(
                                          1 + var30, -1163656551 * var19.field4327, var19.field4258 * 63131743, var19.field4317 * 2073671441, "", -510577957
                                       );
                                 } else if (var30 == 10) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    var0.field5379.vmethod337((byte)-80);
                                    String var61 = var12.method12201(var19, (byte)-124);
                                    if (var61 == null) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       var61 = classKK.field3613;
                                    }

                                    String var80 = var19.field4307 + classQY.method9885(16777215, (byte)101);
                                    int var87 = classVV.method12246(var12, var19, (byte)0);
                                    var0.field5379
                                       .vmethod334(
                                          -1163656551 * var19.field4327,
                                          63131743 * var19.field4258,
                                          classCK.method1569(var87, -2126468581),
                                          var19.field4317 * 2073671441,
                                          var61,
                                          var80,
                                          (byte)104
                                       );
                                 }

                                 int var62 = var19.field4368[var30];
                                 if (null == var19.field4402) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    var19.field4402 = new int[var19.field4310.length];
                                 }

                                 if (null == var19.field4369) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    var19.field4369 = new int[var19.field4310.length];
                                 }

                                 if (0 != var62) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (0 == var19.field4402[var30]) {
                                       if (var15 >= 9) {
                                          return;
                                       }

                                       var19.field4402[var30] = var62 + var13 + var19.field4369[var30];
                                    } else {
                                       var19.field4402[var30] = var13 + var62;
                                    }
                                 } else {
                                    var19.field4402[var30] = Integer.MAX_VALUE;
                                 }
                              }

                              if (!var31) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (null != var19.field4402) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    var19.field4402[var30] = 0;
                                 }
                              }
                           }
                        }

                        if (var19.field4273) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           label1425: {
                              if (var0.field5382.method9838((byte)104) >= var22) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var0.field5382.method9840(1542204717) >= var23) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (var0.field5382.method9838((byte)62) < var24) {
                                       if (var15 >= 9) {
                                          return;
                                       }

                                       if (var0.field5382.method9840(-211701125) < var25) {
                                          var101 = true;
                                          break label1425;
                                       }
                                    }
                                 }
                              }

                              var101 = false;
                           }

                           label1415: {
                              label1414: {
                                 var46 = var101;
                                 if (classQS.method9834(var0.field5382, -361602593) != 1) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (var0.field5381.vmethod168(-672770366) || classQS.method9834(var0.field5382, -361602593) != 4) {
                                       break label1414;
                                    }

                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }
                                 }

                                 if (var46) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    var101 = true;
                                    break label1415;
                                 }
                              }

                              var101 = false;
                           }

                           boolean var48;
                           label1403: {
                              label1402: {
                                 var48 = var101;
                                 if (var0.field5382.method9843(1784200476) != 1) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    if (var0.field5381.vmethod168(-1931282748) || var0.field5382.method9843(1784200476) != 4) {
                                       break label1402;
                                    }

                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }
                                 }

                                 if (var0.field5382.method9848((byte)13) >= var22) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (var0.field5382.method9850(1997119720) >= var23) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       if (var0.field5382.method9848((byte)-58) < var24) {
                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          if (var0.field5382.method9850(1997119720) < var25) {
                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }

                                             var101 = true;
                                             break label1403;
                                          }
                                       }
                                    }
                                 }
                              }

                              var101 = false;
                           }

                           boolean var50 = var101;
                           if (var50) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (!var0.field5381.vmethod156((byte)0)
                                 && var12.method12233(
                                    var19, var0.field5382.method9848((byte)6) - var38, var0.field5382.method9850(1997119720) - var21, 1283450176
                                 )) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 var0.field5379.vmethod343((byte)31);
                              }
                           }

                           if (Widget.method7263(var19, 525274146)) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var50) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 var14.method9714(
                                    new classFM(0, -38255113 * classTZ.field6253 - var38, classTZ.field6262 * -2144333897 - var21, var19), -1243787837
                                 );
                              }

                              if (var48) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 var14.method9714(
                                    new classFM(1, classTZ.field6253 * -38255113 - var38, classTZ.field6262 * -2144333897 - var21, var19), -1974220788
                                 );
                              }
                           }

                           if (-90794217 * var19.field4332 == 1400) {
                              if (var15 >= 9) {
                                 return;
                              }

                              var0.field5380.vmethod84(-587532282).method12458(var42, var44, var46 & var48, var46 & var50, -1962520718);
                           }

                           if (var12.method12230(-431955666)) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (classVV.method12228(var12, (byte)-48) != var19) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var46) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (classUH.method11381(classVV.method12246(var12, var19, (byte)0), (byte)-81)) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       var12.method12226((byte)0).method12272(var19, (byte)-30);
                                    }
                                 }
                              }
                           }

                           var12.method12226((byte)0).method12265(var19, var38, var21, (short)-32309);
                           if (var19.field4313) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var46 && var0.field5382.method9853((byte)-31) != 0 && var19.field4385 != null) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var51 = classUQ.method11527(var19, 541902774)
                                    .method14187(true, 754222515)
                                    .method14195(var19.field4385, (byte)2)
                                    .method14200(var0.field5382.method9853((byte)-26), (byte)1)
                                    .method14226((byte)45);
                                 var14.method9709(var51, 1697206599);
                              }

                              label1377: {
                                 if (!var12.method12230(-431955666)) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (!var0.field5381.vmethod156((byte)0)) {
                                       break label1377;
                                    }

                                    if (var15 >= 9) {
                                       return;
                                    }
                                 }

                                 var50 = false;
                                 var48 = false;
                                 var46 = false;
                              }

                              if (!var19.field4321) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var50) {
                                    var19.field4321 = true;
                                    if (null != var19.field4357) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       ScriptEvent var52 = classUQ.method11527(var19, -82195840)
                                          .method14187(true, 1196267688)
                                          .method14195(var19.field4357, (byte)2)
                                          .method14197(1422993977 * classTZ.field6264 - var38, -1985759611)
                                          .method14200(1243590197 * classTZ.field6255 - var21, (byte)1)
                                          .method14226((byte)121);
                                       var14.method9709(var52, 1697206599);
                                    }
                                 }
                              }

                              if (var19.field4321 && var48) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (null != var19.field4375) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    ScriptEvent var53 = classUQ.method11527(var19, 447233436)
                                       .method14187(true, -813950478)
                                       .method14195(var19.field4375, (byte)2)
                                       .method14197(classTZ.field6253 * -38255113 - var38, -1797422897)
                                       .method14200(classTZ.field6262 * -2144333897 - var21, (byte)1)
                                       .method14226((byte)98);
                                    var14.method9709(var53, 1697206599);
                                 }
                              }

                              if (var19.field4321) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (!var48) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    var19.field4321 = false;
                                    if (var19.field4355 != null) {
                                       if (var15 >= 9) {
                                          return;
                                       }

                                       ScriptEvent var54 = classUQ.method11527(var19, 251761525)
                                          .method14187(true, -83845772)
                                          .method14195(var19.field4355, (byte)2)
                                          .method14197(-38255113 * classTZ.field6253 - var38, -1090908077)
                                          .method14200(classTZ.field6262 * -2144333897 - var21, (byte)1)
                                          .method14226((byte)5);
                                       classQN.method9711(var14, var54, 1513375706);
                                    }
                                 }
                              }

                              if (var48) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (var19.field4376 != null) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    ScriptEvent var55 = classUQ.method11527(var19, 292910948)
                                       .method14187(true, -816691237)
                                       .method14195(var19.field4376, (byte)2)
                                       .method14197(classTZ.field6253 * -38255113 - var38, -1963929087)
                                       .method14200(classTZ.field6262 * -2144333897 - var21, (byte)1)
                                       .method14226((byte)98);
                                    var14.method9709(var55, 1697206599);
                                 }
                              }

                              if (!var19.field4320) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var46) {
                                    var19.field4320 = true;
                                    if (var19.field4400 != null) {
                                       ScriptEvent var56 = classUQ.method11527(var19, 325458216)
                                          .method14187(true, 1882697465)
                                          .method14195(var19.field4400, (byte)2)
                                          .method14197(-38255113 * classTZ.field6253 - var38, -2087208916)
                                          .method14200(classTZ.field6262 * -2144333897 - var21, (byte)1)
                                          .method14226((byte)105);
                                       var14.method9709(var56, 1697206599);
                                    }
                                 }
                              }

                              if (var19.field4320) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (var46) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (null != var19.field4284) {
                                       ScriptEvent var57 = classUQ.method11527(var19, 1235048948)
                                          .method14187(true, -1385284032)
                                          .method14195(var19.field4284, (byte)2)
                                          .method14197(classTZ.field6253 * -38255113 - var38, -1441134992)
                                          .method14200(-2144333897 * classTZ.field6262 - var21, (byte)1)
                                          .method14226((byte)105);
                                       var14.method9709(var57, 1697206599);
                                    }
                                 }
                              }

                              if (var19.field4320 && !var46) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 var19.field4320 = false;
                                 if (var19.field4359 != null) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    ScriptEvent var58 = classUQ.method11527(var19, 449928328)
                                       .method14187(true, 1982805502)
                                       .method14195(var19.field4359, (byte)2)
                                       .method14197(-38255113 * classTZ.field6253 - var38, -1869798690)
                                       .method14200(classTZ.field6262 * -2144333897 - var21, (byte)1)
                                       .method14226((byte)30);
                                    classQN.method9711(var14, var58, 1210007885);
                                 }
                              }

                              if (null != var19.field4341) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var59 = classUQ.method11527(var19, 1089979455).method14195(var19.field4341, (byte)2).method14226((byte)4);
                                 var14.method9710(var59, (byte)-85);
                              }

                              classPV var60 = var0.field5380.vmethod81(1237108207);
                              if (var19.field4380 != null && classPV.method9321(var60, (byte)-97) > -405159805 * var19.field4323) {
                                 label1356: {
                                    if (var19.field4278 != null) {
                                       if (classPV.method9321(var60, (byte)-61) - var19.field4323 * -405159805 <= 32) {
                                          int var64 = var19.field4323 * -405159805;

                                          while (true) {
                                             if (var64 >= classPV.method9321(var60, (byte)-119)) {
                                                break label1356;
                                             }

                                             int var81 = classPV.method9317(var60, var64, -445124758);

                                             for (int var88 = 0; var88 < var19.field4278.length; var88++) {
                                                if (var15 >= 9) {
                                                   throw new IllegalStateException();
                                                }

                                                if (var81 == var19.field4278[var88]) {
                                                   if (var15 >= 9) {
                                                      throw new IllegalStateException();
                                                   }

                                                   ScriptEvent var36 = classUQ.method11527(var19, 341775175)
                                                      .method14195(var19.field4380, (byte)2)
                                                      .method14226((byte)64);
                                                   var14.method9709(var36, 1697206599);
                                                   break label1356;
                                                }
                                             }

                                             var64++;
                                          }
                                       }

                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }
                                    }

                                    ScriptEvent var63 = classUQ.method11527(var19, -146679022).method14195(var19.field4380, (byte)2).method14226((byte)120);
                                    var14.method9709(var63, 1697206599);
                                 }

                                 var19.field4323 = classPV.method9321(var60, (byte)0) * -1433629141;
                              }

                              if (var19.field4353 != null) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var60.method9327(230517950) > var19.field4298 * 790413063) {
                                    label1330: {
                                       if (var19.field4265 != null) {
                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          if (var60.method9327(230517950) - var19.field4298 * 790413063 <= 32) {
                                             int var66 = 790413063 * var19.field4298;

                                             while (true) {
                                                if (var66 >= var60.method9327(230517950)) {
                                                   break label1330;
                                                }

                                                if (var15 >= 9) {
                                                   throw new IllegalStateException();
                                                }

                                                int var82 = var60.method9326(var66, 393471540);

                                                for (int var89 = 0; var89 < var19.field4265.length; var89++) {
                                                   if (var82 == var19.field4265[var89]) {
                                                      if (var15 >= 9) {
                                                         throw new IllegalStateException();
                                                      }

                                                      ScriptEvent var94 = classUQ.method11527(var19, 682125758)
                                                         .method14195(var19.field4353, (byte)2)
                                                         .method14226((byte)27);
                                                      var14.method9709(var94, 1697206599);
                                                      break label1330;
                                                   }
                                                }

                                                var66++;
                                             }
                                          }

                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }
                                       }

                                       ScriptEvent var65 = classUQ.method11527(var19, 639570543).method14195(var19.field4353, (byte)2).method14226((byte)126);
                                       var14.method9709(var65, 1697206599);
                                    }

                                    var19.field4298 = var60.method9327(230517950) * -425558345;
                                 }
                              }

                              if (null != var19.field4382) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var60.method9332(1288219174) > var19.field4324 * 1396002941) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    label1303: {
                                       if (var19.field4383 != null) {
                                          if (var15 >= 9) {
                                             return;
                                          }

                                          if (var60.method9332(1288219174) - 1396002941 * var19.field4324 <= 32) {
                                             int var68 = 1396002941 * var19.field4324;

                                             while (true) {
                                                if (var68 >= var60.method9332(1288219174)) {
                                                   break label1303;
                                                }

                                                int var83 = var60.method9330(var68, -1111491396);

                                                for (int var90 = 0; var90 < var19.field4383.length; var90++) {
                                                   if (var15 >= 9) {
                                                      throw new IllegalStateException();
                                                   }

                                                   if (var19.field4383[var90] == var83) {
                                                      if (var15 >= 9) {
                                                         throw new IllegalStateException();
                                                      }

                                                      ScriptEvent var95 = classUQ.method11527(var19, 1366800940)
                                                         .method14195(var19.field4382, (byte)2)
                                                         .method14226((byte)46);
                                                      var14.method9709(var95, 1697206599);
                                                      break label1303;
                                                   }
                                                }

                                                var68++;
                                             }
                                          }

                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }
                                       }

                                       ScriptEvent var67 = classUQ.method11527(var19, -67274947).method14195(var19.field4382, (byte)2).method14226((byte)90);
                                       var14.method9709(var67, 1697206599);
                                    }

                                    var19.field4324 = var60.method9332(1288219174) * 297445589;
                                 }
                              }

                              if (var60.method9363(2081930497) > var19.field4322 * -1941419841) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var19.field4363 != null) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    ScriptEvent var69 = classUQ.method11527(var19, 763523364).method14195(var19.field4363, (byte)2).method14226((byte)53);
                                    var14.method9709(var69, 1697206599);
                                 }
                              }

                              if (var60.method9336(-1286699135) > var19.field4322 * -1941419841) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var19.field4381 != null) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    ScriptEvent var70 = classUQ.method11527(var19, 1282543540).method14195(var19.field4381, (byte)2).method14226((byte)6);
                                    var14.method9709(var70, 1697206599);
                                 }
                              }

                              if (var60.method9339(-2046021315) > var19.field4322 * -1941419841) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (var19.field4389 != null) {
                                    ScriptEvent var71 = classUQ.method11527(var19, 313901820).method14195(var19.field4389, (byte)2).method14226((byte)2);
                                    var14.method9709(var71, 1697206599);
                                 }
                              }

                              if (var60.method9342(-1087972267) > -1941419841 * var19.field4322) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (null != var19.field4390) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    ScriptEvent var72 = classUQ.method11527(var19, 1736207581).method14195(var19.field4390, (byte)2).method14226((byte)122);
                                    var14.method9709(var72, 1697206599);
                                 }
                              }

                              if (classPV.method9345(var60, (byte)-1) > var19.field4322 * -1941419841) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (null != var19.field4391) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    ScriptEvent var73 = classUQ.method11527(var19, 1571576681).method14195(var19.field4391, (byte)2).method14226((byte)64);
                                    var14.method9709(var73, 1697206599);
                                 }
                              }

                              if (var60.method9349(-693284363) > -1941419841 * var19.field4322) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (null != var19.field4394) {
                                    ScriptEvent var74 = classUQ.method11527(var19, 1456317932).method14195(var19.field4394, (byte)2).method14226((byte)91);
                                    var14.method9709(var74, 1697206599);
                                 }
                              }

                              if (var60.method9354(-2110015517) > -1941419841 * var19.field4322) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var19.field4395 != null) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    ScriptEvent var75 = classUQ.method11527(var19, 707258764).method14195(var19.field4395, (byte)2).method14226((byte)51);
                                    var14.method9709(var75, 1697206599);
                                 }
                              }

                              if (var60.method9358((byte)4) > var19.field4322 * -1941419841) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var19.field4392 != null) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    ScriptEvent var76 = classUQ.method11527(var19, 700046352).method14195(var19.field4392, (byte)2).method14226((byte)119);
                                    var14.method9709(var76, 1697206599);
                                 }
                              }

                              var19.field4322 = var60.method9314(342962976) * 1478436671;
                              if (var19.field4386 != null) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 classFW var77 = var0.field5380.vmethod74((byte)72);

                                 for (int var84 = 0; var84 < classFW.method5066(var77, 892616072); var84++) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    ScriptEvent var91 = classUQ.method11527(var19, 300971475)
                                       .method14195(var19.field4386, (byte)2)
                                       .method14219(var77.method5079(var84, 2069198089), 641798308)
                                       .method14222(var77.method5085(var84, -1837535319), 1410490447)
                                       .method14226((byte)126);
                                    var14.method9709(var91, 1697206599);
                                 }
                              }

                              if (null != var19.field4387) {
                                 int[] var78 = var17.method4578(-1056195858);

                                 for (int var85 = 0; var85 < var78.length; var85++) {
                                    ScriptEvent var92 = classUQ.method11527(var19, 1154772678)
                                       .method14195(var19.field4387, (byte)2)
                                       .method14219(var78[var85], -1942436019)
                                       .method14226((byte)42);
                                    var14.method9709(var92, 1697206599);
                                 }
                              }

                              if (null != var19.field4361) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 int[] var79 = classFA.method4574(var17, 1765027070);

                                 for (int var86 = 0; var86 < var79.length; var86++) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    ScriptEvent var93 = classUQ.method11527(var19, 99096592)
                                       .method14195(var19.field4361, (byte)2)
                                       .method14219(var79[var86], 349750555)
                                       .method14226((byte)73);
                                    var14.method9709(var93, 1697206599);
                                 }
                              }
                           }
                        }

                        if (!var19.field4273) {
                           if (var12.method12230(-431955666)) {
                              continue;
                           }

                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var0.field5381.vmethod156((byte)0)) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }
                              continue;
                           }

                           label1239: {
                              if (-2003253155 * var19.field4314 < 0) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var19.field4270 * 318120869 == 0) {
                                    break label1239;
                                 }

                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }
                              }

                              if (var0.field5382.method9838((byte)96) >= var22) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (var0.field5382.method9840(758337458) >= var23) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (var0.field5382.method9838((byte)119) < var24) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       if (var0.field5382.method9840(859048038) < var25) {
                                          if (var15 >= 9) {
                                             return;
                                          }

                                          if (var19.field4314 * -2003253155 >= 0) {
                                             if (var15 >= 9) {
                                                return;
                                             }

                                             classVC.method12003(classVV.method12187(var12, 252097765), var1[-2003253155 * var19.field4314], -1766608502);
                                          } else {
                                             classVC.method12003(classVV.method12187(var12, 2064189916), var19, -1864074974);
                                          }
                                       }
                                    }
                                 }
                              }
                           }

                           if (8 == -1510882375 * var19.field4373) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var0.field5382.method9838((byte)35) >= var22) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (var0.field5382.method9840(1525346113) >= var23) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (var0.field5382.method9838((byte)81) < var24) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       if (var0.field5382.method9840(-1039273438) < var25) {
                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          classVV.method12187(var12, 946925930).method12010(var19, -1106344526);
                                       }
                                    }
                                 }
                              }
                           }

                           if (-714521463 * var19.field4267 > 293773697 * var19.field4257) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              var0.method9167(
                                 var19,
                                 var19.field4247 * 1115597881 + var38,
                                 var21,
                                 293773697 * var19.field4257,
                                 -714521463 * var19.field4267,
                                 var0.field5382.method9838((byte)120),
                                 var0.field5382.method9840(1376332737),
                                 var12,
                                 1303753171
                              );
                           }
                        }

                        if (-1510882375 * var19.field4373 == 0) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           method9164(
                              var0,
                              var1,
                              var19.field4263 * -1210177335,
                              var19.field4264 * 379541057,
                              -1163656551 * var19.field4327,
                              63131743 * var19.field4258,
                              var22,
                              var23,
                              var24,
                              var25,
                              var38 - var19.field4339 * -34809441,
                              var21 - var19.field4329 * -41424189,
                              var12,
                              var13,
                              var14,
                              (byte)-28
                           );
                           if (var19.field4338 != null) {
                              if (var15 >= 9) {
                                 return;
                              }

                              method9164(
                                 var0,
                                 var19.field4338,
                                 0,
                                 var19.field4338.length - 1,
                                 var19.field4327 * -1163656551,
                                 -1,
                                 var22,
                                 var23,
                                 var24,
                                 var25,
                                 var38 - var19.field4339 * -34809441,
                                 var21 - var19.field4329 * -41424189,
                                 var12,
                                 var13,
                                 var14,
                                 (byte)-90
                              );
                           }

                           if (-1 == 63131743 * var19.field4258) {
                              if (var15 >= 9) {
                                 return;
                              }

                              WidgetNode var49 = (WidgetNode)HashTable.method13432(var12.field6611, -1163656551 * var19.field4327);
                              if (var49 != null) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (-691218541 * var49.field5274 == 0) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    if (-38255113 * classTZ.field6253 >= var22) {
                                       if (var15 >= 9) {
                                          return;
                                       }

                                       if (-2144333897 * classTZ.field6262 >= var23 && -38255113 * classTZ.field6253 < var24) {
                                          if (var15 >= 9) {
                                             return;
                                          }

                                          if (-2144333897 * classTZ.field6262 < var25 && !var0.field5381.vmethod156((byte)0)) {
                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }

                                             var14.method9726(1806526567);
                                             var12.method12226((byte)0).method12317((byte)51);
                                             if (!var16) {
                                                if (var15 >= 9) {
                                                   throw new IllegalStateException();
                                                }

                                                var0.field5379.vmethod317(891814198);
                                             }
                                          }
                                       }
                                    }
                                 }

                                 var0.method9161(var49.field5275 * 2023647411, var22, var23, var24, var25, var38, var21, var12, var13, var14, (byte)9);
                              }
                           }
                        }
                     }
                  }
               }
            }
         } catch (RuntimeException var37) {
            throw classEG.method3899(var37, "pq.ac(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;)V")
   @ObfuscatedName("aw")
   public void method9198(classVV var1) {
      classVW var2 = var1.method12226((byte)0);
      if (var2.method12278(1394014890)) {
         var2.method12274((byte)19);
         int var3 = var2.method12292((byte)34);
         int var4 = classVW.method12294(var2, (byte)-43);
         if (var2.method12326(-1519344304) && var2.method12329(718275352)) {
            Widget var5 = var2.method12282(-1767002121);
            Widget var6 = var2.method12284(-764534869);
            int var7 = var2.method12304(-375999162);
            int var8 = var2.method12310((byte)-96);
            int var9 = classVW.method12298(var2, (byte)1);
            int var10 = var2.method12302(-2113254100);
            int var11 = -38255113 * classTZ.field6253;
            int var12 = -2144333897 * classTZ.field6262;
            var11 -= var3;
            var12 -= var4;
            if (var11 < var7) {
               var11 = var7;
            }

            if (1115597881 * var5.field4247 + var11 > 1115597881 * var6.field4247 + var7) {
               var11 = var6.field4247 * 1115597881 + var7 - var5.field4247 * 1115597881;
            }

            if (var12 < var8) {
               var12 = var8;
            }

            if (var12 + var5.field4257 * 293773697 > 293773697 * var6.field4257 + var8) {
               var12 = var8 + 293773697 * var6.field4257 - 293773697 * var5.field4257;
            }

            int var13 = var11 - var9;
            int var14 = var12 - var10;
            int var15 = var5.field4366 * -782836685;
            if (var2.method12314((byte)-28) > var5.field4311 * 135384009 && (var13 > var15 || var13 < -var15 || var14 > var15 || var14 < -var15)) {
               classVW.method12321(var2, -1811178912);
            }

            int var16 = var11 - var7 + -34809441 * var6.field4339;
            int var17 = var6.field4329 * -41424189 + (var12 - var8);
            if (var5.field4377 != null && classVW.method12324(var2, (short)15876)) {
               ScriptEvent var18 = classUQ.method11527(var5, -171077490)
                  .method14195(var5.field4377, (byte)2)
                  .method14197(var16, -1278971402)
                  .method14200(var17, (byte)1)
                  .method14226((byte)56);
               classSZ.method10771(var18, -1434636174);
            }

            if (0 == classTZ.field6249 * 2090434187) {
               if (classVW.method12324(var2, (short)16045)) {
                  if (null != var5.field4378) {
                     ScriptEvent var21 = classUQ.method11527(var5, 2055490702)
                        .method14195(var5.field4378, (byte)2)
                        .method14197(var16, -1691346852)
                        .method14200(var17, (byte)1)
                        .method14215(var2.method12288(271014207), (byte)115)
                        .method14226((byte)50);
                     this.field5379.vmethod347(var21, -1336876629);
                  }

                  this.field5379.vmethod360(var5, var2.method12288(271014207), -1922763908);
               } else {
                  this.field5379.vmethod363(var9 + var3, var4 + var10, -511275367);
               }

               var2.method12255(2146507811);
            }
         } else {
            if (var2.method12314((byte)-22) > 1) {
               if (!classVW.method12324(var2, (short)25499) && this.field5381.vmethod158(658002820) > 0) {
                  this.field5379.vmethod344(var3 + classVW.method12298(var2, (byte)1), var4 + var2.method12302(-1832940626), (byte)22);
               }

               var2.method12255(2144374889);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpq;Lqn;Lvv;)V")
   @ObfuscatedName("ix")
   public static void method9192(classPQ var0, classQN var1, classVV var2) {
      if (var0 == null) {
         var0.method9144(var2);
      }

      ScriptEvent var3 = var1.method9719(-1432447279);

      while (var3 != null) {
         ScriptEvent var4 = var3;
         var3 = var1.method9719(-1432447279);
         Widget var5 = var4.method14144(385395161);
         if (var5.field4258 * 63131743 >= 0) {
            Widget var6 = var2.method7421(410502503 * var5.field4340, (byte)44);
            if (null == var6
               || var6.field4338 == null
               || 63131743 * var5.field4258 >= var6.field4338.length
               || var5 != var6.field4338[var5.field4258 * 1194219397]) {
               continue;
            }
         }

         classSZ.method10771(var4, -1696775060);
      }

      var3 = var1.method9723(-165324379);

      while (null != var3) {
         ScriptEvent var9 = var3;
         var3 = var1.method9723(356881506);
         Widget var12 = var9.method14144(688429728);
         if (var12.field4258 * -885405631 >= 0) {
            Widget var15 = var2.method7421(1439899044 * var12.field4340, (byte)122);
            if (null == var15
               || null == var15.field4338
               || 63131743 * var12.field4258 >= var15.field4338.length
               || var12 != var15.field4338[var12.field4258 * -670637786]) {
               continue;
            }
         }

         classSZ.method10771(var9, -951292320);
      }

      var3 = classQN.method9717(var1, (byte)117);

      while (var3 != null) {
         ScriptEvent var10 = var3;
         var3 = classQN.method9717(var1, (byte)17);
         Widget var13 = var10.method14144(694927926);
         if (var13.field4258 * 389416460 >= 0) {
            Widget var16 = var2.method7421(var13.field4340 * -1502647899, (byte)94);
            if (null == var16
               || null == var16.field4338
               || 63131743 * var13.field4258 >= var16.field4338.length
               || var13 != var16.field4338[1772792238 * var13.field4258]) {
               continue;
            }
         }

         classSZ.method10771(var10, -885478793);
      }

      boolean var11 = false;

      for (classFM var14 = var1.method9724((byte)80); null != var14; var14 = var1.method9724((byte)24)) {
         if (12 == var14.field2304.field4373 * -1510882375) {
            var11 = true;
         }

         method9196(var0, var14, var2, 818237321);
      }

      if (!var11 && 1 == 1229235359 * classTZ.field6259) {
         var0.field5379.vmethod357(null, 2001429318);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpk;ZLvv;)V")
   @ObfuscatedName("uh")
   public void method9203(WidgetNode var1, boolean var2, classVV var3) {
      classOE.field4841.getCallbacks().post(new WidgetClosed(var1.getId(), var1.getModalMode(), var2));
      if (var2) {
         int var4 = var1.getId();
         method9201(var4, client.field824.field5566);
         method9201(var4, client.field824.field5564);
         method9201(var4, client.field824.field5565);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILvv;Lqn;)V")
   @ObfuscatedName("ar")
   public void method9138(int var1, int var2, int var3, classVV var4, classQN var5) {
      var4.field6610 = var1 * 1130371137;
      classCN.method2541(var4.field6610 * -77072447, var2, var3, false, var4, var5, (short)217);
      this.method9188(var1, var4, -1599344500);
      method9181(this, -77072447 * var4.field6610, var4, -765568144);
   }

   @ObfuscatedSignature(descriptor = "(Lvv;)V")
   @ObfuscatedName("ed")
   public void method9142(classVV var1) {
      if (-1 != -77072447 * var1.field6610) {
         var1.method7429(var1.field6610 * -77072447, -1637327880);
         var1.field6610 = -1130371137;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;)V")
   @ObfuscatedName("cp")
   public void method9143(classVV var1) {
      if (-1 != -77072447 * var1.field6610) {
         var1.method7429(var1.field6610 * -77072447, -1528241920);
         var1.field6610 = -1130371137;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpq;Lfm;Lvv;I)V")
   @ObfuscatedName("ac")
   public static void method9196(classPQ var0, classFM var1, classVV var2, int var3) {
      if (var0 == null) {
         var0.method9197(var1, var2, var3);
      } else {
         try {
            if (var1 != null && var1.field2304 != null) {
               if (var1.field2304.field4258 * 63131743 >= 0) {
                  Widget var4 = var2.method7421(-1502647899 * var1.field2304.field4340, (byte)31);
                  if (null == var4) {
                     return;
                  }

                  if (var3 == -948608385) {
                     throw new IllegalStateException();
                  }

                  if (null == var4.field4338) {
                     return;
                  }

                  if (var3 == -948608385) {
                     throw new IllegalStateException();
                  }

                  if (var4.field4338.length == 0) {
                     return;
                  }

                  if (var3 == -948608385) {
                     throw new IllegalStateException();
                  }

                  if (63131743 * var1.field2304.field4258 >= var4.field4338.length) {
                     return;
                  }

                  if (var4.field4338[63131743 * var1.field2304.field4258] != var1.field2304) {
                     if (var3 == -948608385) {
                        return;
                     }

                     return;
                  }
               }

               if (-1510882375 * var1.field2304.field4373 == 11) {
                  if (var3 == -948608385) {
                     throw new IllegalStateException();
                  }

                  if (0 == var1.field2306 * -1735555937) {
                     if (var3 == -948608385) {
                        throw new IllegalStateException();
                     }

                     if (var1.field2304.method7294(1605458035 * var1.field2305, var1.field2303 * 213240559, 0, 0, (byte)91)) {
                        if (var3 == -948608385) {
                           throw new IllegalStateException();
                        }

                        var0.field5379.vmethod354(var1.field2304, (byte)127);
                     }

                     return;
                  }
               }

               if (-1510882375 * var1.field2304.field4373 == 12) {
                  if (var3 == -948608385) {
                     return;
                  }

                  classLH var6 = var1.field2304.method7334(-619129432);
                  if (var6 != null) {
                     if (var3 == -948608385) {
                        throw new IllegalStateException();
                     }

                     if (var6.method7030(-880967958)) {
                        if (var3 == -948608385) {
                           throw new IllegalStateException();
                        }

                        switch (-1735555937 * var1.field2306) {
                           case 0:
                              var0.field5379.vmethod357(var1.field2304, 1750352878);
                              var6.method6987(
                                 1605458035 * var1.field2305,
                                 var1.field2303 * 213240559,
                                 var0.field5380.vmethod72(-1370068174).method4563(82, 353603741),
                                 var0.field5380.vmethod72(-429159985).method4563(81, 742348860),
                                 14704747
                              );
                              break;
                           case 1:
                              classLH.method6993(var6, var1.field2305 * 1605458035, 213240559 * var1.field2303, -1740986807);
                        }
                     }
                  }
               }
            }
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "pq.ap(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;)V")
   @ObfuscatedName("ak")
   public void method9144(classVV var1) {
      if (-1 != 1014821904 * var1.field6610) {
         var1.method7429(var1.field6610 * 1719901921, -793773478);
         var1.field6610 = -2124398386;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILvv;Lqn;)Lpk;")
   @ObfuscatedName("an")
   public WidgetNode method9145(int var1, int var2, int var3, classVV var4, classQN var5) {
      WidgetNode var6 = new WidgetNode();
      var6.field5275 = var2 * 1771210363;
      var6.field5274 = -908534117 * var3;
      var4.field6611.method13418(var6, var1);
      this.method9188(var2, var4, -2044306888);
      Widget var7 = var4.method7421(var1, (byte)85);
      var4.method12240((byte)8);
      classFC.method4609(var4.field4438[var1 >> 16], var7, false, var4, var5, -1070344421);
      method9181(this, var2, var4, -1350338802);
      method9173(this, classQP.field5580, var4, -515058630);
      return var6;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpq;IIIIIIILvv;ILqn;)V")
   @ObfuscatedName("wr")
   public static void method9162(classPQ var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8, int var9, classQN var10) {
      if (var0 == null) {
         var0.method9142(var8);
      }

      if (var8.method7425(var1, 1412712374)) {
         method9164(var0, var8.field4438[var1], 0, var8.field4438[var1].length - 1, -1, -1, var2, var3, var4, var5, var6, var7, var8, var9, var10, (byte)-25);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILvv;Lqn;)Lpk;")
   @ObfuscatedName("am")
   public WidgetNode method9146(int var1, int var2, int var3, classVV var4, classQN var5) {
      WidgetNode var6 = new WidgetNode();
      var6.field5275 = var2 * 1771210363;
      var6.field5274 = -75682176 * var3;
      var4.field6611.method13418(var6, var1);
      this.method9188(var2, var4, -1673408409);
      Widget var7 = var4.method7421(var1, (byte)86);
      var4.method12240((byte)8);
      classFC.method4609(var4.field4438[var1 >> 16], var7, false, var4, var5, -760152405);
      method9181(this, var2, var4, -401433394);
      method9173(this, classQP.field5580, var4, 22383252);
      return var6;
   }

   @ObfuscatedSignature(descriptor = "(Lpq;ILvv;I)V")
   @ObfuscatedName("zw")
   public static void method9181(classPQ var0, int var1, classVV var2, int var3) {
      if (var0 == null) {
         var0.method9184(var1, var2, var1);
      }

      try {
         if (var1 == -1) {
            if (var3 >= 1377666464) {
               throw new IllegalStateException();
            } else {
               var0.method9202(var1, var2);
            }
         } else if (!var2.method7425(var1, 943864010)) {
            if (var3 >= 1377666464) {
               var0.method9202(var1, var2);
            } else {
               var0.method9202(var1, var2);
            }
         } else {
            Widget[] var4 = var2.field4438[var1];

            for (int var5 = 0; var5 < var4.length; var5++) {
               if (var3 >= 1377666464) {
                  throw new IllegalStateException();
               }

               Widget var6 = var4[var5];
               if (var6.field4374 != null) {
                  if (var3 >= 1377666464) {
                     var0.method9202(var1, var2);
                     return;
                  }

                  ScriptEvent var7 = classUQ.method11527(var6, 841725730).method14195(var6.field4374, (byte)2).method14226((byte)8);
                  var0.field5379.vmethod349(var7, -1973248401);
               }
            }

            var0.method9202(var1, var2);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "pq.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILvv;Lqn;)Lpk;")
   @ObfuscatedName("ah")
   public WidgetNode method9147(int var1, int var2, int var3, classVV var4, classQN var5) {
      WidgetNode var6 = new WidgetNode();
      var6.field5275 = var2 * 1771210363;
      var6.field5274 = -784381136 * var3;
      var4.field6611.method13418(var6, var1);
      this.method9188(var2, var4, -1367449369);
      Widget var7 = var4.method7421(var1, (byte)105);
      var4.method12240((byte)8);
      classFC.method4609(var4.field4438[var1 >> 16], var7, false, var4, var5, 1290718548);
      method9181(this, var2, var4, -506615352);
      method9173(this, classQP.field5580, var4, -133586400);
      return var6;
   }

   @ObfuscatedSignature(descriptor = "(Lpk;ZLvv;)V")
   @ObfuscatedName("bn")
   public void method9151(WidgetNode var1, boolean var2, classVV var3) {
      int var4 = -1550147844 * var1.field5275;
      var1.vmethod398();
      if (var2) {
         var3.method7429(var4, -846623488);
      }

      classVV.method12204(var3, var4, 1479147290);
      method9173(this, classQP.field5580, var3, -894669533);
   }

   @ObfuscatedSignature(descriptor = "(Lpk;ZLvv;)V")
   @ObfuscatedName("bx")
   public void method9152(WidgetNode var1, boolean var2, classVV var3) {
      int var4 = 2023647411 * var1.field5275;
      var1.vmethod398();
      if (var2) {
         var3.method7429(var4, 566771252);
      }

      classVV.method12204(var3, var4, 632232789);
      method9173(this, classQP.field5580, var3, -1304817961);
   }

   @ObfuscatedSignature(descriptor = "(IILvv;Lqn;)V")
   @ObfuscatedName("bp")
   public void method9155(int var1, int var2, classVV var3, classQN var4) {
      WidgetNode var5 = (WidgetNode)HashTable.method13432(var3.field6611, var1);
      WidgetNode var6 = (WidgetNode)HashTable.method13432(var3.field6611, var2);
      if (var6 != null) {
         this.method9150(var6, var5 == null || var5.field5275 * 2023647411 != var6.field5275 * 2023647411, var3, (byte)-88);
      }

      if (var5 != null) {
         var5.vmethod398();
         var3.field6611.method13418(var5, var2);
      }

      Widget var7 = var3.method7421(var2, (byte)92);
      if (var7 != null) {
         classFC.method4609(var3.field4438[var7.field4327 * -1163656551 >>> 16], var7, true, var3, var4, -1423804462);
      }

      method9173(this, classQP.field5580, var3, -192286525);
   }

   @ObfuscatedSignature(descriptor = "(Lvv;IIIILqn;)V")
   @ObfuscatedName("bt")
   public void method9159(classVV var1, int var2, int var3, int var4, int var5, classQN var6) {
      classVC.method11999(classVV.method12187(var1, 83511866), (byte)2);
      var1.method12226((byte)0).method12261(-624973020);
      if (-77072447 * var1.field6610 != -1) {
         classQS.method9831(
            this.field5382,
            classTZ.field6249 * 2090434187,
            classTZ.field6253 * -38255113,
            classTZ.field6262 * -2144333897,
            classTZ.field6259 * 1229235359,
            classTZ.field6264 * 1422993977,
            classTZ.field6255 * 1243590197,
            var5,
            3392313
         );
         this.method9161(-77072447 * var1.field6610, 0, 0, var2, var3, 0, 0, var1, var4, var6, (byte)33);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpq;IILvv;Lqn;B)V")
   @ObfuscatedName("zm")
   public static void method9156(classPQ var0, int var1, int var2, classVV var3, classQN var4, byte var5) {
      if (var0 == null) {
         var0.method9157(var1, var1, var3, var4, var5);
      } else {
         try {
            WidgetNode var6 = (WidgetNode)HashTable.method13432(var3.field6611, var1);
            WidgetNode var7 = (WidgetNode)HashTable.method13432(var3.field6611, var2);
            if (var7 != null) {
               if (var5 != 0) {
                  throw new IllegalStateException();
               }

               boolean var10002;
               label51: {
                  if (var6 != null) {
                     if (var5 != 0) {
                        throw new IllegalStateException();
                     }

                     if (var6.field5275 * 2023647411 == var7.field5275 * 2023647411) {
                        var10002 = false;
                        break label51;
                     }

                     if (var5 != 0) {
                        throw new IllegalStateException();
                     }
                  }

                  var10002 = true;
               }

               var0.method9150(var7, var10002, var3, (byte)-93);
            }

            if (var6 != null) {
               if (var5 != 0) {
                  throw new IllegalStateException();
               }

               var6.vmethod398();
               var3.field6611.method13418(var6, var2);
            }

            Widget var8 = var3.method7421(var2, (byte)47);
            if (var8 != null) {
               if (var5 != 0) {
                  throw new IllegalStateException();
               }

               classFC.method4609(var3.field4438[var8.field4327 * -1163656551 >>> 16], var8, true, var3, var4, 897179765);
            }

            method9173(var0, classQP.field5580, var3, -1543721167);
         } catch (RuntimeException var9) {
            throw classEG.method3899(var9, "pq.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILvv;Lqn;)V")
   @ObfuscatedName("ai")
   public void method9139(int var1, int var2, int var3, classVV var4, classQN var5) {
      var4.field6610 = var1 * 2017155750;
      classCN.method2541(var4.field6610 * -77072447, var2, var3, false, var4, var5, (short)217);
      this.method9188(var1, var4, -1710425320);
      method9181(this, -58062239 * var4.field6610, var4, -34121923);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIILvv;ILqn;)V")
   @ObfuscatedName("bg")
   void method9163(int var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8, int var9, classQN var10) {
      if (var8.method7425(var1, 562789226)) {
         method9164(this, var8.field4438[var1], 0, var8.field4438[var1].length - 1, -1, -1, var2, var3, var4, var5, var6, var7, var8, var9, var10, (byte)-41);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILvv;)V")
   @ObfuscatedName("uu")
   public void method9202(int var1, classVV var2) {
      Widget[][] var3 = var2.field4438;
      boolean var4 = var3 != null && var3[var1] != null;
      if (var4) {
         WidgetLoaded var5 = new WidgetLoaded();
         var5.setGroupId(var1);
         classOE.field4841.getCallbacks().post(var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;B)V")
   @ObfuscatedName("au")
   public void method9199(classVV var1, byte var2) {
      this.method9206(var1);

      try {
         classVW var3 = var1.method12226((byte)0);
         if (var3.method12278(1663421763)) {
            var3.method12274((byte)19);
            int var4 = var3.method12292((byte)4);
            int var5 = classVW.method12294(var3, (byte)-86);
            if (var3.method12326(1475678285)) {
               if (var2 != 0) {
                  return;
               }

               if (var3.method12329(-307638323)) {
                  Widget var6 = var3.method12282(-1516943161);
                  Widget var7 = var3.method12284(1901590269);
                  int var8 = var3.method12304(48117700);
                  int var9 = var3.method12310((byte)-11);
                  int var10 = classVW.method12298(var3, (byte)1);
                  int var11 = var3.method12302(-1597679967);
                  int var12 = -38255113 * classTZ.field6253;
                  int var13 = -2144333897 * classTZ.field6262;
                  var12 -= var4;
                  var13 -= var5;
                  if (var12 < var8) {
                     var12 = var8;
                  }

                  if (1115597881 * var6.field4247 + var12 > 1115597881 * var7.field4247 + var8) {
                     var12 = var7.field4247 * 1115597881 + var8 - var6.field4247 * 1115597881;
                  }

                  if (var13 < var9) {
                     if (var2 != 0) {
                        return;
                     }

                     var13 = var9;
                  }

                  if (var13 + var6.field4257 * 293773697 > 293773697 * var7.field4257 + var9) {
                     if (var2 != 0) {
                        throw new IllegalStateException();
                     }

                     var13 = var9 + 293773697 * var7.field4257 - 293773697 * var6.field4257;
                  }

                  int var14 = var12 - var10;
                  int var15 = var13 - var11;
                  int var16 = var6.field4366 * -782836685;
                  label139:
                  if (var3.method12314((byte)9) > var6.field4311 * 135384009) {
                     if (var14 <= var16) {
                        if (var2 != 0) {
                           throw new IllegalStateException();
                        }

                        if (var14 >= -var16) {
                           if (var2 != 0) {
                              throw new IllegalStateException();
                           }

                           if (var15 <= var16) {
                              if (var2 != 0) {
                                 return;
                              }

                              if (var15 >= -var16) {
                                 break label139;
                              }

                              if (var2 != 0) {
                                 throw new IllegalStateException();
                              }
                           }
                        }
                     }

                     classVW.method12321(var3, -1717841667);
                  }

                  int var17 = var12 - var8 + -34809441 * var7.field4339;
                  int var18 = var7.field4329 * -41424189 + (var13 - var9);
                  if (var6.field4377 != null) {
                     if (var2 != 0) {
                        throw new IllegalStateException();
                     }

                     if (classVW.method12324(var3, (short)3311)) {
                        ScriptEvent var19 = classUQ.method11527(var6, 607173943)
                           .method14195(var6.field4377, (byte)2)
                           .method14197(var17, -1985090638)
                           .method14200(var18, (byte)1)
                           .method14226((byte)120);
                        classSZ.method10771(var19, -743728140);
                     }
                  }

                  if (0 == classTZ.field6249 * 2090434187) {
                     if (var2 != 0) {
                        throw new IllegalStateException();
                     }

                     if (classVW.method12324(var3, (short)15535)) {
                        if (var2 != 0) {
                           throw new IllegalStateException();
                        }

                        if (null != var6.field4378) {
                           if (var2 != 0) {
                              throw new IllegalStateException();
                           }

                           ScriptEvent var23 = classUQ.method11527(var6, 552092874)
                              .method14195(var6.field4378, (byte)2)
                              .method14197(var17, -1652226865)
                              .method14200(var18, (byte)1)
                              .method14215(var3.method12288(271014207), (byte)90)
                              .method14226((byte)122);
                           this.field5379.vmethod347(var23, -1670979320);
                        }

                        this.field5379.vmethod360(var6, var3.method12288(271014207), -2046971986);
                     } else {
                        this.field5379.vmethod363(var10 + var4, var5 + var11, -511275367);
                     }

                     var3.method12255(2123816356);
                  }

                  return;
               }

               if (var2 != 0) {
                  throw new IllegalStateException();
               }
            }

            if (var3.method12314((byte)4) > 1) {
               if (var2 != 0) {
                  return;
               }

               if (!classVW.method12324(var3, (short)2082)) {
                  if (var2 != 0) {
                     throw new IllegalStateException();
                  }

                  if (this.field5381.vmethod158(658002820) > 0) {
                     this.field5379.vmethod344(var4 + classVW.method12298(var3, (byte)1), var5 + var3.method12302(-1852585598), (byte)112);
                  }
               }

               var3.method12255(2137302152);
            }
         }
      } catch (RuntimeException var20) {
         throw classEG.method3899(var20, "pq.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqn;Lvv;)V")
   @ObfuscatedName("cu")
   public void method9193(classQN var1, classVV var2) {
      ScriptEvent var3 = var1.method9719(-1432447279);

      while (var3 != null) {
         ScriptEvent var4 = var3;
         var3 = var1.method9719(-1432447279);
         Widget var5 = var4.method14144(-764734020);
         if (var5.field4258 * 63131743 >= 0) {
            Widget var6 = var2.method7421(-1502647899 * var5.field4340, (byte)122);
            if (null == var6
               || var6.field4338 == null
               || 63131743 * var5.field4258 >= var6.field4338.length
               || var5 != var6.field4338[var5.field4258 * 63131743]) {
               continue;
            }
         }

         classSZ.method10771(var4, -1518596918);
      }

      var3 = var1.method9723(-1618186875);

      while (null != var3) {
         ScriptEvent var9 = var3;
         var3 = var1.method9723(-1672572561);
         Widget var12 = var9.method14144(-883464418);
         if (var12.field4258 * 63131743 >= 0) {
            Widget var15 = var2.method7421(-1502647899 * var12.field4340, (byte)67);
            if (null == var15
               || null == var15.field4338
               || 63131743 * var12.field4258 >= var15.field4338.length
               || var12 != var15.field4338[var12.field4258 * 63131743]) {
               continue;
            }
         }

         classSZ.method10771(var9, -919736810);
      }

      var3 = classQN.method9717(var1, (byte)117);

      while (var3 != null) {
         ScriptEvent var10 = var3;
         var3 = classQN.method9717(var1, (byte)14);
         Widget var13 = var10.method14144(1795899255);
         if (var13.field4258 * 63131743 >= 0) {
            Widget var16 = var2.method7421(var13.field4340 * -1502647899, (byte)80);
            if (null == var16
               || null == var16.field4338
               || 63131743 * var13.field4258 >= var16.field4338.length
               || var13 != var16.field4338[63131743 * var13.field4258]) {
               continue;
            }
         }

         classSZ.method10771(var10, -1603499491);
      }

      boolean var11 = false;

      for (classFM var14 = var1.method9724((byte)110); null != var14; var14 = var1.method9724((byte)10)) {
         if (12 == var14.field2304.field4373 * -1510882375) {
            var11 = true;
         }

         method9196(this, var14, var2, -1853435511);
      }

      if (!var11 && 1 == 1229235359 * classTZ.field6259) {
         this.field5379.vmethod357(null, 1692985201);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpq;Lqn;Lvv;)V")
   @ObfuscatedName("uv")
   public static void method9194(classPQ var0, classQN var1, classVV var2) {
      if (var0 == null) {
         var0.method9143(var2);
      } else {
         ScriptEvent var3 = var1.method9719(-1432447279);

         while (var3 != null) {
            ScriptEvent var4 = var3;
            var3 = var1.method9719(-1432447279);
            Widget var5 = var4.method14144(-306909430);
            if (var5.field4258 * 63131743 >= 0) {
               Widget var6 = var2.method7421(-1502647899 * var5.field4340, (byte)117);
               if (null == var6
                  || var6.field4338 == null
                  || 1300520343 * var5.field4258 >= var6.field4338.length
                  || var5 != var6.field4338[var5.field4258 * -573530876]) {
                  continue;
               }
            }

            classSZ.method10771(var4, -1861315236);
         }

         var3 = var1.method9723(1488447084);

         while (null != var3) {
            ScriptEvent var9 = var3;
            var3 = var1.method9723(-375746252);
            Widget var12 = var9.method14144(-1541294117);
            if (var12.field4258 * 63131743 >= 0) {
               Widget var15 = var2.method7421(-1800430650 * var12.field4340, (byte)127);
               if (null == var15
                  || null == var15.field4338
                  || 63131743 * var12.field4258 >= var15.field4338.length
                  || var12 != var15.field4338[var12.field4258 * 1427483844]) {
                  continue;
               }
            }

            classSZ.method10771(var9, -877394763);
         }

         var3 = classQN.method9717(var1, (byte)81);

         while (var3 != null) {
            ScriptEvent var10 = var3;
            var3 = classQN.method9717(var1, (byte)46);
            Widget var13 = var10.method14144(1111123811);
            if (var13.field4258 * 63131743 >= 0) {
               Widget var16 = var2.method7421(var13.field4340 * -1502647899, (byte)28);
               if (null == var16
                  || null == var16.field4338
                  || 63131743 * var13.field4258 >= var16.field4338.length
                  || var13 != var16.field4338[1715870257 * var13.field4258]) {
                  continue;
               }
            }

            classSZ.method10771(var10, -1238935588);
         }

         boolean var11 = false;

         for (classFM var14 = var1.method9724((byte)63); null != var14; var14 = var1.method9724((byte)57)) {
            if (12 == var14.field2304.field4373 * -1510882375) {
               var11 = true;
            }

            method9196(var0, var14, var2, -929918431);
         }

         if (!var11 && 1 == 567742702 * classTZ.field6259) {
            var0.field5379.vmethod357(null, 2016929771);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpq;Lvv;IIIILqn;)V")
   @ObfuscatedName("wb")
   public static void method9160(classPQ var0, classVV var1, int var2, int var3, int var4, int var5, classQN var6) {
      if (var0 == null) {
         var0.method9208(var2, var1);
      } else {
         classVC.method11999(classVV.method12187(var1, 256966096), (byte)-103);
         var1.method12226((byte)0).method12261(-624973020);
         if (-77072447 * var1.field6610 != -1) {
            classQS.method9831(
               var0.field5382,
               classTZ.field6249 * 2090434187,
               classTZ.field6253 * 15498753,
               classTZ.field6262 * 676434572,
               classTZ.field6259 * 1229235359,
               classTZ.field6264 * 1422993977,
               classTZ.field6255 * 1788344936,
               var5,
               3392313
            );
            var0.method9161(21303142 * var1.field6610, 0, 0, var2, var3, 0, 0, var1, var4, var6, (byte)69);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILqp;Lvv;)V")
   @ObfuscatedName("bq")
   void method9179(int var1, classQP var2, classVV var3) {
      if (var3.method7425(var1, 1242108226)) {
         this.method9187(var3.field4438[var1], var2, var3, (byte)3);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIIIIILvv;)V")
   @ObfuscatedName("ba")
   void method9169(Widget var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8) {
      byte var9;
      if (var8.field6617) {
         var9 = 32;
      } else {
         var9 = 0;
      }

      var8.field6617 = false;
      if (classQS.method9834(this.field5382, -361602593) == 1 || !this.field5381.vmethod168(-1442780434) && classQS.method9834(this.field5382, -361602593) == 4
         )
       {
         if (var6 >= var2 && var6 < 16 + var2 && var7 >= var3 && var7 < var3 + 16) {
            var1.field4329 -= -299739220;
         } else if (var6 >= var2 && var6 < 16 + var2 && var7 >= var3 + var4 - 16 && var7 < var3 + var4) {
            var1.field4329 += -299739220;
         } else if (var6 >= var2 - var9 && var6 < var9 + var2 + 16 && var7 >= 16 + var3 && var7 < var4 + var3 - 16) {
            int var10 = var4 * (var4 - -1192116798) / var5;
            if (var10 < 8) {
               var10 = 8;
            }

            int var11 = var7 - var3 - 16 - var10 / 2;
            int var12 = var4 - 32 - var10;
            var1.field4329 = (var5 - var4) * var11 / var12 * -1864070355;
            var8.field6617 = true;
         }
      }

      if (this.field5382.method9853((byte)-92) != 0) {
         int var13 = var1.field4247 * -1919926931;
         if (var6 >= var2 - var13 && var7 >= var3 && var6 < 16 + var2 && var7 <= var3 + var4) {
            var1.field4329 = var1.field4329 + this.field5382.method9853((byte)-17) * -150840753;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIIIIILvv;)V")
   @ObfuscatedName("bw")
   void method9170(Widget var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8) {
      byte var9;
      if (var8.field6617) {
         var9 = 32;
      } else {
         var9 = 0;
      }

      var8.field6617 = false;
      if (classQS.method9834(this.field5382, -361602593) == 1 || !this.field5381.vmethod168(-1240968793) && classQS.method9834(this.field5382, -361602593) == 4
         )
       {
         if (var6 >= var2 && var6 < 16 + var2 && var7 >= var3 && var7 < var3 + 16) {
            var1.field4329 -= -299739220;
         } else if (var6 >= var2 && var6 < 16 + var2 && var7 >= var3 + var4 - 16 && var7 < var3 + var4) {
            var1.field4329 += -299739220;
         } else if (var6 >= var2 - var9 && var6 < var9 + var2 + 16 && var7 >= 16 + var3 && var7 < var4 + var3 - 16) {
            int var10 = var4 * (var4 - 32) / var5;
            if (var10 < 8) {
               var10 = 8;
            }

            int var11 = var7 - var3 - 16 - var10 / 2;
            int var12 = var4 - 32 - var10;
            var1.field4329 = (var5 - var4) * var11 / var12 * -1148676629;
            var8.field6617 = true;
         }
      }

      if (this.field5382.method9853((byte)-23) != 0) {
         int var13 = var1.field4247 * 1115597881;
         if (var6 >= var2 - var13 && var7 >= var3 && var6 < 16 + var2 && var7 <= var3 + var4) {
            var1.field4329 = var1.field4329 + this.field5382.method9853((byte)-36) * -150840753;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqp;Lvv;)V")
   @ObfuscatedName("bb")
   public void method9172(classQP var1, classVV var2) {
      if (-1 != var2.field6610 * -77072447) {
         this.method9178(-77072447 * var2.field6610, var1, var2, (byte)-53);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILqp;Lvv;)V")
   @ObfuscatedName("by")
   void method9180(int var1, classQP var2, classVV var3) {
      if (var3.method7425(var1, 104081870)) {
         this.method9187(var3.field4438[var1], var2, var3, (byte)3);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpq;Lqp;Lvv;I)V")
   @ObfuscatedName("nz")
   public static void method9173(classPQ var0, classQP var1, classVV var2, int var3) {
      if (var0 == null) {
         var0.method9176(var1, var2, var3);
      }

      try {
         if (-1 != var2.field6610 * -77072447) {
            var0.method9178(-77072447 * var2.field6610, var1, var2, (byte)-124);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pq.ao(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpq;Lpk;ZLvv;)V")
   @ObfuscatedName("ep")
   public static void method9153(classPQ var0, WidgetNode var1, boolean var2, classVV var3) {
      if (var0 == null) {
         var0.method9151(var1, var2, var3);
      }

      int var4 = 2023647411 * var1.field5275;
      var1.vmethod398();
      if (var2) {
         var3.method7429(var4, 288952925);
      }

      classVV.method12204(var3, var4, 1007249099);
      method9173(var0, classQP.field5580, var3, -1088057362);
   }

   @ObfuscatedSignature(descriptor = "(IIILvv;Lqn;I)Lpk;")
   @ObfuscatedName("ae")
   public WidgetNode method9148(int var1, int var2, int var3, classVV var4, classQN var5, int var6) {
      try {
         WidgetNode var7 = new WidgetNode();
         var7.field5275 = var2 * 1771210363;
         var7.field5274 = -908534117 * var3;
         var4.field6611.method13418(var7, var1);
         this.method9188(var2, var4, -1544827161);
         Widget var8 = var4.method7421(var1, (byte)113);
         var4.method12240((byte)8);
         classFC.method4609(var4.field4438[var1 >> 16], var8, false, var4, var5, 460100382);
         method9181(this, var2, var4, -1676173428);
         method9173(this, classQP.field5580, var4, -1878880951);
         return var7;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "pq.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqp;Lvv;)V")
   @ObfuscatedName("be")
   public void method9174(classQP var1, classVV var2) {
      if (-1 != var2.field6610 * -77072447) {
         this.method9178(-77072447 * var2.field6610, var1, var2, (byte)-125);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILvv;)V")
   @ObfuscatedName("bs")
   void method9182(int var1, classVV var2) {
      if (var1 != -1) {
         if (var2.method7425(var1, 1273850621)) {
            Widget[] var3 = var2.field4438[var1];

            for (int var4 = 0; var4 < var3.length; var4++) {
               Widget var5 = var3[var4];
               if (var5.field4374 != null) {
                  ScriptEvent var6 = classUQ.method11527(var5, 1116135879).method14195(var5.field4374, (byte)2).method14226((byte)115);
                  this.field5379.vmethod349(var6, -2113542081);
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpq;[Llw;IIIIIIIIIILvv;ILqn;)V")
   @ObfuscatedName("mm")
   public static void method9165(
      classPQ var0,
      Widget[] var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      classVV var12,
      int var13,
      classQN var14
   ) {
      boolean var15 = var0.field5381.vmethod156((byte)0);
      classFA var16 = var0.field5380.vmethod72(-1751247255);

      for (int var17 = var2; var17 <= var3; var17++) {
         Widget var18 = var1[var17];
         if (var18 != null
            && -1502647899 * var18.field4340 == var4
            && 653784551 * var18.field4261 == var5
            && var12.method12194(var18, (byte)-83)
            && (
               var18.field4273
                  ? !var12.method12196(var18, (byte)0)
                  : var18.field4373 * -1510882375 != 0
                     || classVV.method12187(var12, -128961542).method12002(var18, (byte)82)
                     || !var12.method12196(var18, (byte)0)
            )) {
            if (-1510882375 * var18.field4373 == 11) {
               if (var18.method7325(var12, var0.field5380.vmethod78((byte)-50), 1679933735)) {
                  if (var18.method7296(1355775728)) {
                     classFC.method4609(var18.field4338, var18, true, var12, var14, 1574025796);
                     Widget.method7322(var18, -1626603432)
                        .method7472((byte)9)
                        .method7741(3, Widget.method7322(var18, -1626603432).method7521(644161130), (byte)10);
                  }

                  if (null != var18.field4396) {
                     ScriptEvent var19 = classUQ.method11527(var18, 287733073).method14195(var18.field4396, (byte)2).method14226((byte)81);
                     var14.method9709(var19, 1697206599);
                  }
               }
            } else if (12 == var18.field4373 * -1510882375) {
               var18.method7350(var12, 1279009205);
            }

            int var36 = var18.field4279 * 744286069 + var10;
            int var20 = var11 + 1570844829 * var18.field4255;
            int var21;
            int var22;
            int var23;
            int var24;
            if (9 == -1510882375 * var18.field4373) {
               int var25 = var36;
               int var26 = var20;
               int var27 = 1115597881 * var18.field4247 + var36;
               int var28 = var20 + var18.field4257 * 293773697;
               if (var27 < var36) {
                  var25 = var27;
                  var27 = var36;
               }

               if (var28 < var20) {
                  var26 = var28;
                  var28 = var20;
               }

               var27++;
               var28++;
               var21 = var25 > var6 ? var25 : var6;
               var22 = var26 > var7 ? var26 : var7;
               var23 = var27 < var8 ? var27 : var8;
               var24 = var28 < var9 ? var28 : var9;
            } else {
               int var37 = var18.field4247 * 1115597881 + var36;
               int var39 = var18.field4257 * 293773697 + var20;
               var21 = var36 > var6 ? var36 : var6;
               var22 = var20 > var7 ? var20 : var7;
               var23 = var37 < var8 ? var37 : var8;
               var24 = var39 < var9 ? var39 : var9;
            }

            var12.method12226((byte)0).method12263(var18, var36, var20, -1621064682);
            boolean var38 = false;
            if (var18.field4358) {
               switch (var12.field6613 * -698898037) {
                  case 0:
                     var38 = true;
                  case 1:
                  default:
                     break;
                  case 2:
                     if (-1163656551 * var18.field4327 >>> 16 == -336184109 * var12.field6614) {
                        var38 = true;
                     }
                     break;
                  case 3:
                     if (-1163656551 * var18.field4327 == var12.field6614 * -336184109) {
                        var38 = true;
                     }
               }
            }

            if (var38 || !var18.field4273 || var21 < var23 && var22 < var24) {
               if (var18.field4273) {
                  if (var18.field4401) {
                     if (var0.field5382.method9838((byte)104) >= var21
                        && var0.field5382.method9840(543128027) >= var22
                        && var0.field5382.method9838((byte)68) < var23
                        && var0.field5382.method9840(676723738) < var24) {
                        var14.method9726(2112254256);
                        var12.method12226((byte)0).method12317((byte)60);
                        if (!var15) {
                           var0.field5379.vmethod317(891814198);
                        }
                     }
                  } else if (var18.field4326
                     && var0.field5382.method9838((byte)116) >= var21
                     && var0.field5382.method9840(1693453516) >= var22
                     && var0.field5382.method9838((byte)126) < var23
                     && var0.field5382.method9840(1345288589) < var24) {
                     var14.method9733(-1478111027);
                  }
               }

               int var40 = var0.field5382.method9838((byte)99);
               int var42 = var0.field5382.method9840(559054605);
               if (var0.field5382.method9843(1784200476) != 0) {
                  var40 = var0.field5382.method9848((byte)-64);
                  var42 = var0.field5382.method9850(1997119720);
               }

               boolean var44 = var40 >= var21 && var42 >= var22 && var40 < var23 && var42 < var24;
               if (var18.field4332 * -90794217 == 1337) {
                  if (!var0.field5381.vmethod166((byte)-88) && !var15 && var44) {
                     var0.field5379.vmethod321(var40, var42, var21, var22, -972257835);
                  }
               } else if (1338 == -90794217 * var18.field4332) {
                  classNS.method8041(var18, var36, var20, (byte)106);
               } else {
                  if (1400 == var18.field4332 * -90794217) {
                     RenderOverview.method12456(
                        var0.field5380.vmethod84(774055787),
                        var40,
                        var42,
                        var44,
                        var36,
                        var20,
                        var18.field4247 * 1115597881,
                        var18.field4257 * 293773697,
                        (byte)74
                     );
                  }

                  if (!var15 && var44) {
                     if (-90794217 * var18.field4332 == 1400) {
                        var0.field5380
                           .vmethod84(1945838822)
                           .method12645(var36, var20, var18.field4247 * 1115597881, 293773697 * var18.field4257, var40, var42, -1221860810);
                     } else {
                        var0.field5379.vmethod324(var18, (byte)-25);
                     }
                  }

                  if (var38) {
                     for (int var29 = 0; var29 < var18.field4310.length; var29++) {
                        boolean var30 = false;
                        boolean var31 = false;
                        if (!var30 && null != var18.field4310[var29]) {
                           for (int var32 = 0; var32 < var18.field4310[var29].length; var32++) {
                              boolean var33 = false;
                              if (null != var18.field4402) {
                                 var33 = var16.method4563(var18.field4310[var29][var32], 2010805321);
                              }

                              if (var0.field5380.vmethod74((byte)58).method5087(var18.field4310[var29][var32], 1307123110) || var33) {
                                 var30 = true;
                                 if (var18.field4402 != null && var18.field4402[var29] > var13) {
                                    break;
                                 }

                                 byte var34 = var18.field4344[var29][var32];
                                 if (var34 == 0
                                    || (
                                          (var34 & 8) == 0
                                             || !var16.method4563(86, 1771265661) && !var16.method4563(82, -191016173) && !var16.method4563(81, 1551555928)
                                       )
                                       && (0 == (var34 & 2) || var16.method4563(86, 2090121774))
                                       && ((var34 & 1) == 0 || var16.method4563(82, 1278023649))
                                       && ((var34 & 4) == 0 || var16.method4563(81, 1197761504))) {
                                    var31 = true;
                                    break;
                                 }
                              }
                           }
                        }

                        if (var31) {
                           if (var29 < 10) {
                              var0.field5379
                                 .vmethod329(
                                    1 + var29, -1163656551 * var18.field4327, var18.field4258 * 63131743, var18.field4317 * 2073671441, "", -1339238434
                                 );
                           } else if (var29 == 10) {
                              var0.field5379.vmethod337((byte)-35);
                              String var59 = var12.method12201(var18, (byte)-49);
                              if (var59 == null) {
                                 var59 = classKK.field3613;
                              }

                              String var78 = var18.field4307 + classQY.method9885(16777215, (byte)59);
                              int var85 = classVV.method12246(var12, var18, (byte)0);
                              var0.field5379
                                 .vmethod334(
                                    -1163656551 * var18.field4327,
                                    63131743 * var18.field4258,
                                    classCK.method1569(var85, -2095649936),
                                    var18.field4317 * 2073671441,
                                    var59,
                                    var78,
                                    (byte)39
                                 );
                           }

                           int var60 = var18.field4368[var29];
                           if (null == var18.field4402) {
                              var18.field4402 = new int[var18.field4310.length];
                           }

                           if (null == var18.field4369) {
                              var18.field4369 = new int[var18.field4310.length];
                           }

                           if (0 != var60) {
                              if (0 == var18.field4402[var29]) {
                                 var18.field4402[var29] = var60 + var13 + var18.field4369[var29];
                              } else {
                                 var18.field4402[var29] = var13 + var60;
                              }
                           } else {
                              var18.field4402[var29] = Integer.MAX_VALUE;
                           }
                        }

                        if (!var30 && null != var18.field4402) {
                           var18.field4402[var29] = 0;
                        }
                     }
                  }

                  if (var18.field4273) {
                     var44 = var0.field5382.method9838((byte)118) >= var21
                        && var0.field5382.method9840(-2109370369) >= var22
                        && var0.field5382.method9838((byte)103) < var23
                        && var0.field5382.method9840(-222069661) < var24;
                     boolean var46 = (
                           classQS.method9834(var0.field5382, -361602593) == 1
                              || !var0.field5381.vmethod168(-164930398) && classQS.method9834(var0.field5382, -361602593) == 4
                        )
                        && var44;
                     boolean var48 = (
                           var0.field5382.method9843(1784200476) == 1 || !var0.field5381.vmethod168(-1838220262) && var0.field5382.method9843(1784200476) == 4
                        )
                        && var0.field5382.method9848((byte)-87) >= var21
                        && var0.field5382.method9850(1997119720) >= var22
                        && var0.field5382.method9848((byte)4) < var23
                        && var0.field5382.method9850(1997119720) < var24;
                     if (var48
                        && !var0.field5381.vmethod156((byte)0)
                        && var12.method12233(var18, var0.field5382.method9848((byte)-85) - var36, var0.field5382.method9850(1997119720) - var20, 157750249)) {
                        var0.field5379.vmethod343((byte)127);
                     }

                     if (Widget.method7263(var18, 1850584872)) {
                        if (var48) {
                           var14.method9714(new classFM(0, -38255113 * classTZ.field6253 - var36, classTZ.field6262 * -2144333897 - var20, var18), -1687891513);
                        }

                        if (var46) {
                           var14.method9714(new classFM(1, classTZ.field6253 * -38255113 - var36, classTZ.field6262 * -2144333897 - var20, var18), -1923322349);
                        }
                     }

                     if (-90794217 * var18.field4332 == 1400) {
                        var0.field5380.vmethod84(571479845).method12458(var40, var42, var44 & var46, var44 & var48, -1687964001);
                     }

                     if (var12.method12230(-431955666)
                        && classVV.method12228(var12, (byte)-117) != var18
                        && var44
                        && classUH.method11381(classVV.method12246(var12, var18, (byte)0), (byte)-78)) {
                        var12.method12226((byte)0).method12272(var18, (byte)-54);
                     }

                     var12.method12226((byte)0).method12265(var18, var36, var20, (short)-15558);
                     if (var18.field4313) {
                        if (var44 && var0.field5382.method9853((byte)-105) != 0 && var18.field4385 != null) {
                           ScriptEvent var49 = classUQ.method11527(var18, 1209669318)
                              .method14187(true, 371510532)
                              .method14195(var18.field4385, (byte)2)
                              .method14200(var0.field5382.method9853((byte)-17), (byte)1)
                              .method14226((byte)60);
                           var14.method9709(var49, 1697206599);
                        }

                        if (var12.method12230(-431955666) || var0.field5381.vmethod156((byte)0)) {
                           var48 = false;
                           var46 = false;
                           var44 = false;
                        }

                        if (!var18.field4321 && var48) {
                           var18.field4321 = true;
                           if (null != var18.field4357) {
                              ScriptEvent var50 = classUQ.method11527(var18, 131335369)
                                 .method14187(true, -1472899770)
                                 .method14195(var18.field4357, (byte)2)
                                 .method14197(1422993977 * classTZ.field6264 - var36, -2097630996)
                                 .method14200(1243590197 * classTZ.field6255 - var20, (byte)1)
                                 .method14226((byte)89);
                              var14.method9709(var50, 1697206599);
                           }
                        }

                        if (var18.field4321 && var46 && null != var18.field4375) {
                           ScriptEvent var51 = classUQ.method11527(var18, 1806650447)
                              .method14187(true, -340540116)
                              .method14195(var18.field4375, (byte)2)
                              .method14197(classTZ.field6253 * -38255113 - var36, -2092074401)
                              .method14200(classTZ.field6262 * -2144333897 - var20, (byte)1)
                              .method14226((byte)101);
                           var14.method9709(var51, 1697206599);
                        }

                        if (var18.field4321 && !var46) {
                           var18.field4321 = false;
                           if (var18.field4355 != null) {
                              ScriptEvent var52 = classUQ.method11527(var18, 64655907)
                                 .method14187(true, 2023026715)
                                 .method14195(var18.field4355, (byte)2)
                                 .method14197(-38255113 * classTZ.field6253 - var36, -1961329802)
                                 .method14200(classTZ.field6262 * -2144333897 - var20, (byte)1)
                                 .method14226((byte)110);
                              classQN.method9711(var14, var52, 2050667622);
                           }
                        }

                        if (var46 && var18.field4376 != null) {
                           ScriptEvent var53 = classUQ.method11527(var18, 322852413)
                              .method14187(true, -1528215060)
                              .method14195(var18.field4376, (byte)2)
                              .method14197(classTZ.field6253 * -38255113 - var36, -1516113047)
                              .method14200(classTZ.field6262 * -2144333897 - var20, (byte)1)
                              .method14226((byte)15);
                           var14.method9709(var53, 1697206599);
                        }

                        if (!var18.field4320 && var44) {
                           var18.field4320 = true;
                           if (var18.field4400 != null) {
                              ScriptEvent var54 = classUQ.method11527(var18, 1784095699)
                                 .method14187(true, 845187551)
                                 .method14195(var18.field4400, (byte)2)
                                 .method14197(-38255113 * classTZ.field6253 - var36, -2123085474)
                                 .method14200(classTZ.field6262 * -2144333897 - var20, (byte)1)
                                 .method14226((byte)22);
                              var14.method9709(var54, 1697206599);
                           }
                        }

                        if (var18.field4320 && var44 && null != var18.field4284) {
                           ScriptEvent var55 = classUQ.method11527(var18, -63200349)
                              .method14187(true, 202711738)
                              .method14195(var18.field4284, (byte)2)
                              .method14197(classTZ.field6253 * -38255113 - var36, -2075702945)
                              .method14200(-2144333897 * classTZ.field6262 - var20, (byte)1)
                              .method14226((byte)81);
                           var14.method9709(var55, 1697206599);
                        }

                        if (var18.field4320 && !var44) {
                           var18.field4320 = false;
                           if (var18.field4359 != null) {
                              ScriptEvent var56 = classUQ.method11527(var18, 1360563971)
                                 .method14187(true, -1113485663)
                                 .method14195(var18.field4359, (byte)2)
                                 .method14197(-38255113 * classTZ.field6253 - var36, -1695340073)
                                 .method14200(classTZ.field6262 * -2144333897 - var20, (byte)1)
                                 .method14226((byte)105);
                              classQN.method9711(var14, var56, -1639183105);
                           }
                        }

                        if (null != var18.field4341) {
                           ScriptEvent var57 = classUQ.method11527(var18, 581422148).method14195(var18.field4341, (byte)2).method14226((byte)79);
                           var14.method9710(var57, (byte)-10);
                        }

                        classPV var58 = var0.field5380.vmethod81(207462339);
                        if (var18.field4380 != null && classPV.method9321(var58, (byte)-95) > -405159805 * var18.field4323) {
                           if (var18.field4278 != null && classPV.method9321(var58, (byte)-83) - var18.field4323 * -405159805 <= 32) {
                              label815:
                              for (int var62 = var18.field4323 * -405159805; var62 < classPV.method9321(var58, (byte)-7); var62++) {
                                 int var79 = classPV.method9317(var58, var62, -1472778680);

                                 for (int var86 = 0; var86 < var18.field4278.length; var86++) {
                                    if (var79 == var18.field4278[var86]) {
                                       ScriptEvent var35 = classUQ.method11527(var18, 2525273).method14195(var18.field4380, (byte)2).method14226((byte)59);
                                       var14.method9709(var35, 1697206599);
                                       break label815;
                                    }
                                 }
                              }
                           } else {
                              ScriptEvent var61 = classUQ.method11527(var18, 657643735).method14195(var18.field4380, (byte)2).method14226((byte)107);
                              var14.method9709(var61, 1697206599);
                           }

                           var18.field4323 = classPV.method9321(var58, (byte)-28) * -1433629141;
                        }

                        if (var18.field4353 != null && var58.method9327(230517950) > var18.field4298 * 790413063) {
                           if (var18.field4265 != null && var58.method9327(230517950) - var18.field4298 * 790413063 <= 32) {
                              label791:
                              for (int var64 = 790413063 * var18.field4298; var64 < var58.method9327(230517950); var64++) {
                                 int var80 = var58.method9326(var64, 220101944);

                                 for (int var87 = 0; var87 < var18.field4265.length; var87++) {
                                    if (var80 == var18.field4265[var87]) {
                                       ScriptEvent var92 = classUQ.method11527(var18, 136539607).method14195(var18.field4353, (byte)2).method14226((byte)110);
                                       var14.method9709(var92, 1697206599);
                                       break label791;
                                    }
                                 }
                              }
                           } else {
                              ScriptEvent var63 = classUQ.method11527(var18, 697495202).method14195(var18.field4353, (byte)2).method14226((byte)41);
                              var14.method9709(var63, 1697206599);
                           }

                           var18.field4298 = var58.method9327(230517950) * -425558345;
                        }

                        if (null != var18.field4382 && var58.method9332(1288219174) > var18.field4324 * 1396002941) {
                           if (var18.field4383 != null && var58.method9332(1288219174) - 1396002941 * var18.field4324 <= 32) {
                              label767:
                              for (int var66 = 1396002941 * var18.field4324; var66 < var58.method9332(1288219174); var66++) {
                                 int var81 = var58.method9330(var66, 1065250396);

                                 for (int var88 = 0; var88 < var18.field4383.length; var88++) {
                                    if (var18.field4383[var88] == var81) {
                                       ScriptEvent var93 = classUQ.method11527(var18, 724829529).method14195(var18.field4382, (byte)2).method14226((byte)36);
                                       var14.method9709(var93, 1697206599);
                                       break label767;
                                    }
                                 }
                              }
                           } else {
                              ScriptEvent var65 = classUQ.method11527(var18, 2060752939).method14195(var18.field4382, (byte)2).method14226((byte)91);
                              var14.method9709(var65, 1697206599);
                           }

                           var18.field4324 = var58.method9332(1288219174) * 297445589;
                        }

                        if (var58.method9363(-176012129) > var18.field4322 * -1941419841 && var18.field4363 != null) {
                           ScriptEvent var67 = classUQ.method11527(var18, 730626900).method14195(var18.field4363, (byte)2).method14226((byte)15);
                           var14.method9709(var67, 1697206599);
                        }

                        if (var58.method9336(-2047294149) > var18.field4322 * -1941419841 && var18.field4381 != null) {
                           ScriptEvent var68 = classUQ.method11527(var18, 507672593).method14195(var18.field4381, (byte)2).method14226((byte)30);
                           var14.method9709(var68, 1697206599);
                        }

                        if (var58.method9339(-1983380543) > var18.field4322 * -1941419841 && var18.field4389 != null) {
                           ScriptEvent var69 = classUQ.method11527(var18, 1279220568).method14195(var18.field4389, (byte)2).method14226((byte)86);
                           var14.method9709(var69, 1697206599);
                        }

                        if (var58.method9342(-923783653) > -1941419841 * var18.field4322 && null != var18.field4390) {
                           ScriptEvent var70 = classUQ.method11527(var18, 1683576032).method14195(var18.field4390, (byte)2).method14226((byte)28);
                           var14.method9709(var70, 1697206599);
                        }

                        if (classPV.method9345(var58, (byte)-1) > var18.field4322 * -1941419841 && null != var18.field4391) {
                           ScriptEvent var71 = classUQ.method11527(var18, 682554510).method14195(var18.field4391, (byte)2).method14226((byte)5);
                           var14.method9709(var71, 1697206599);
                        }

                        if (var58.method9349(1389741871) > -1941419841 * var18.field4322 && null != var18.field4394) {
                           ScriptEvent var72 = classUQ.method11527(var18, 721987947).method14195(var18.field4394, (byte)2).method14226((byte)48);
                           var14.method9709(var72, 1697206599);
                        }

                        if (var58.method9354(-2110015517) > -1941419841 * var18.field4322 && var18.field4395 != null) {
                           ScriptEvent var73 = classUQ.method11527(var18, 704467664).method14195(var18.field4395, (byte)2).method14226((byte)30);
                           var14.method9709(var73, 1697206599);
                        }

                        if (var58.method9358((byte)39) > var18.field4322 * -1941419841 && var18.field4392 != null) {
                           ScriptEvent var74 = classUQ.method11527(var18, 1368369539).method14195(var18.field4392, (byte)2).method14226((byte)82);
                           var14.method9709(var74, 1697206599);
                        }

                        var18.field4322 = var58.method9314(342962976) * 1478436671;
                        if (var18.field4386 != null) {
                           classFW var75 = var0.field5380.vmethod74((byte)27);

                           for (int var82 = 0; var82 < classFW.method5066(var75, -1103716758); var82++) {
                              ScriptEvent var89 = classUQ.method11527(var18, 704999611)
                                 .method14195(var18.field4386, (byte)2)
                                 .method14219(var75.method5079(var82, 2127322297), 108880585)
                                 .method14222(var75.method5085(var82, -1750243230), 390601550)
                                 .method14226((byte)122);
                              var14.method9709(var89, 1697206599);
                           }
                        }

                        if (null != var18.field4387) {
                           int[] var76 = var16.method4578(-556200040);

                           for (int var83 = 0; var83 < var76.length; var83++) {
                              ScriptEvent var90 = classUQ.method11527(var18, 1508605786)
                                 .method14195(var18.field4387, (byte)2)
                                 .method14219(var76[var83], -1226112461)
                                 .method14226((byte)117);
                              var14.method9709(var90, 1697206599);
                           }
                        }

                        if (null != var18.field4361) {
                           int[] var77 = classFA.method4574(var16, -212435337);

                           for (int var84 = 0; var84 < var77.length; var84++) {
                              ScriptEvent var91 = classUQ.method11527(var18, 2043144148)
                                 .method14195(var18.field4361, (byte)2)
                                 .method14219(var77[var84], 2040212231)
                                 .method14226((byte)48);
                              var14.method9709(var91, 1697206599);
                           }
                        }
                     }
                  }

                  if (!var18.field4273) {
                     if (var12.method12230(-431955666) || var0.field5381.vmethod156((byte)0)) {
                        continue;
                     }

                     if ((-2003253155 * var18.field4314 >= 0 || var18.field4270 * 318120869 != 0)
                        && var0.field5382.method9838((byte)75) >= var21
                        && var0.field5382.method9840(1732141895) >= var22
                        && var0.field5382.method9838((byte)93) < var23
                        && var0.field5382.method9840(-1064552944) < var24) {
                        if (var18.field4314 * -2003253155 >= 0) {
                           classVC.method12003(classVV.method12187(var12, 514117950), var1[-2003253155 * var18.field4314], -1614698460);
                        } else {
                           classVC.method12003(classVV.method12187(var12, 47497121), var18, -1651325217);
                        }
                     }

                     if (8 == -1510882375 * var18.field4373
                        && var0.field5382.method9838((byte)55) >= var21
                        && var0.field5382.method9840(1996077445) >= var22
                        && var0.field5382.method9838((byte)7) < var23
                        && var0.field5382.method9840(248520098) < var24) {
                        classVV.method12187(var12, 302380637).method12010(var18, 1312686469);
                     }

                     if (-714521463 * var18.field4267 > 293773697 * var18.field4257) {
                        var0.method9167(
                           var18,
                           var18.field4247 * 1115597881 + var36,
                           var20,
                           293773697 * var18.field4257,
                           -714521463 * var18.field4267,
                           var0.field5382.method9838((byte)85),
                           var0.field5382.method9840(1178516075),
                           var12,
                           2002015450
                        );
                     }
                  }

                  if (-1510882375 * var18.field4373 == 0) {
                     method9164(
                        var0,
                        var1,
                        var18.field4263 * -1210177335,
                        var18.field4264 * 379541057,
                        -1163656551 * var18.field4327,
                        63131743 * var18.field4258,
                        var21,
                        var22,
                        var23,
                        var24,
                        var36 - var18.field4339 * -34809441,
                        var20 - var18.field4329 * -41424189,
                        var12,
                        var13,
                        var14,
                        (byte)-89
                     );
                     if (var18.field4338 != null) {
                        method9164(
                           var0,
                           var18.field4338,
                           0,
                           var18.field4338.length - 1,
                           var18.field4327 * -1163656551,
                           -1,
                           var21,
                           var22,
                           var23,
                           var24,
                           var36 - var18.field4339 * -34809441,
                           var20 - var18.field4329 * -41424189,
                           var12,
                           var13,
                           var14,
                           (byte)-22
                        );
                     }

                     if (-1 == 63131743 * var18.field4258) {
                        WidgetNode var47 = (WidgetNode)HashTable.method13432(var12.field6611, -1163656551 * var18.field4327);
                        if (var47 != null) {
                           if (-691218541 * var47.field5274 == 0
                              && -38255113 * classTZ.field6253 >= var21
                              && -2144333897 * classTZ.field6262 >= var22
                              && -38255113 * classTZ.field6253 < var23
                              && -2144333897 * classTZ.field6262 < var24
                              && !var0.field5381.vmethod156((byte)0)) {
                              var14.method9726(1932473333);
                              var12.method12226((byte)0).method12317((byte)-21);
                              if (!var15) {
                                 var0.field5379.vmethod317(891814198);
                              }
                           }

                           var0.method9161(var47.field5275 * 2023647411, var21, var22, var23, var24, var36, var20, var12, var13, var14, (byte)-2);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;)Z")
   @ObfuscatedName("at")
   public static boolean method9154(WorldView var0) {
      if (var0 == null) {
         var0.method3796();
      }

      return 0 == var0.field1697 * 1182734434;
   }

   @ObfuscatedSignature(descriptor = "(ILvv;)V")
   @ObfuscatedName("bd")
   void method9183(int var1, classVV var2) {
      if (var1 != -1) {
         if (var2.method7425(var1, 1044960666)) {
            Widget[] var3 = var2.field4438[var1];

            for (int var4 = 0; var4 < var3.length; var4++) {
               Widget var5 = var3[var4];
               if (var5.field4374 != null) {
                  ScriptEvent var6 = classUQ.method11527(var5, 1110098055).method14195(var5.field4374, (byte)2).method14226((byte)10);
                  this.field5379.vmethod349(var6, 574387989);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;)V")
   @ObfuscatedName("cq")
   public void method9206(classVV var1) {
      classVW var2 = var1.field6612;
      if (var2.field6628 != null && var2.field6625 && var2.field6619) {
         int var3 = classTZ.field6253 * -38255113;
         int var4 = classTZ.field6262 * -2144333897;
         var3 -= var2.field6621 * -314698085;
         var4 -= var2.field6622 * -542846241;
         if (var3 < var2.field6626 * -279181121) {
            var3 = var2.field6626 * -279181121;
         }

         if (var3 + var2.field6628.field4247 * 1115597881 > var2.field6626 * -279181121 + var2.field6620.field4247 * 1115597881) {
            var3 = var2.field6626 * -279181121 + var2.field6620.field4247 * 1115597881 - var2.field6628.field4247 * 1115597881;
         }

         if (var4 < var2.field6627 * -2119087041) {
            var4 = var2.field6627 * -2119087041;
         }

         if (var4 + var2.field6628.field4257 * 293773697 > var2.field6627 * -2119087041 + var2.field6620.field4257 * 293773697) {
            var4 = var2.field6627 * -2119087041 + var2.field6620.field4257 * 293773697 - var2.field6628.field4257 * 293773697;
         }

         int var5 = var3 - var2.field6629 * 985413999;
         int var6 = var4 - var2.field6630 * 1430697363;
         int var7 = var2.field6628.field4366 * -782836685;
         if (var2.field6632 * 42513585 + 1 > var2.field6628.field4311 * 135384009 && (var5 > var7 || var5 < -var7 || var6 > var7 || var6 < -var7)) {
            WidgetDrag var8 = new WidgetDrag();
            classOE.field4841.getCallbacks().post(var8);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;Lqp;Lvv;)V")
   @ObfuscatedName("bl")
   void method9185(Widget[] var1, classQP var2, classVV var3) {
      for (int var4 = 0; var4 < var1.length; var4++) {
         Widget var5 = var1[var4];
         if (null != var5) {
            if (0 == var5.field4373 * -1510882375) {
               if (null != var5.field4338) {
                  this.method9187(var5.field4338, var2, var3, (byte)3);
               }

               if (63131743 * var5.field4258 == -1) {
                  WidgetNode var6 = (WidgetNode)HashTable.method13432(var3.field6611, -1163656551 * var5.field4327);
                  if (var6 != null) {
                     this.method9178(var6.field5275 * 2023647411, var2, var3, (byte)-41);
                  }
               }
            }

            if (classQP.field5579 == var2 && null != var5.field4404) {
               ScriptEvent var7 = classUQ.method11527(var5, 1902572016).method14195(var5.field4404, (byte)2).method14226((byte)71);
               this.field5379.vmethod347(var7, -1934930606);
            }

            if (var2 == classQP.field5580 && null != var5.field4393) {
               if (var5.field4258 * 63131743 >= 0) {
                  Widget var8 = var3.method7421(-1163656551 * var5.field4327, (byte)97);
                  if (null == var8
                     || var8.field4338 == null
                     || var5.field4258 * 63131743 >= var8.field4338.length
                     || var5 != var8.field4338[var5.field4258 * 63131743]) {
                     continue;
                  }
               }

               ScriptEvent var9 = classUQ.method11527(var5, 460157621).method14195(var5.field4393, (byte)2).method14226((byte)105);
               this.field5379.vmethod347(var9, -1714527610);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;Lqp;Lvv;)V")
   @ObfuscatedName("nq")
   public void method9209(Widget[] var1, classQP var2, classVV var3) {
      this.method9187(var1, var2, var3, (byte)3);
   }

   @ObfuscatedSignature(descriptor = "([Llw;Lqp;Lvv;)V")
   @ObfuscatedName("bv")
   void method9186(Widget[] var1, classQP var2, classVV var3) {
      for (int var4 = 0; var4 < var1.length; var4++) {
         Widget var5 = var1[var4];
         if (null != var5) {
            if (0 == var5.field4373 * -1510882375) {
               if (null != var5.field4338) {
                  this.method9187(var5.field4338, var2, var3, (byte)3);
               }

               if (63131743 * var5.field4258 == -1) {
                  WidgetNode var6 = (WidgetNode)HashTable.method13432(var3.field6611, -1163656551 * var5.field4327);
                  if (var6 != null) {
                     this.method9178(var6.field5275 * 2023647411, var2, var3, (byte)-50);
                  }
               }
            }

            if (classQP.field5579 == var2 && null != var5.field4404) {
               ScriptEvent var7 = classUQ.method11527(var5, -18382433).method14195(var5.field4404, (byte)2).method14226((byte)73);
               this.field5379.vmethod347(var7, -2081526055);
            }

            if (var2 == classQP.field5580 && null != var5.field4393) {
               if (var5.field4258 * 63131743 >= 0) {
                  Widget var8 = var3.method7421(-1163656551 * var5.field4327, (byte)96);
                  if (null == var8
                     || var8.field4338 == null
                     || var5.field4258 * 63131743 >= var8.field4338.length
                     || var5 != var8.field4338[var5.field4258 * 63131743]) {
                     continue;
                  }
               }

               ScriptEvent var9 = classUQ.method11527(var5, 1211554199).method14195(var5.field4393, (byte)2).method14226((byte)15);
               this.field5379.vmethod347(var9, -1962788610);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILvv;)V")
   @ObfuscatedName("bh")
   void method9189(int var1, classVV var2) {
      if (var2.method7425(var1, 909666691)) {
         Widget[] var3 = var2.field4438[var1];

         for (int var4 = 0; var4 < var3.length; var4++) {
            Widget var5 = var3[var4];
            if (null != var5) {
               var5.field4319 = 0;
               var5.field4325 = 0;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILvv;)V")
   @ObfuscatedName("bz")
   void method9190(int var1, classVV var2) {
      if (var2.method7425(var1, 1212426477)) {
         Widget[] var3 = var2.field4438[var1];

         for (int var4 = 0; var4 < var3.length; var4++) {
            Widget var5 = var3[var4];
            if (null != var5) {
               var5.field4319 = 0;
               var5.field4325 = 0;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIIIIILvv;)V")
   @ObfuscatedName("bu")
   void method9171(Widget var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8) {
      byte var9;
      if (var8.field6617) {
         var9 = 32;
      } else {
         var9 = 0;
      }

      var8.field6617 = false;
      if (classQS.method9834(this.field5382, -361602593) == 1 || !this.field5381.vmethod168(-1534036577) && classQS.method9834(this.field5382, -361602593) == 4
         )
       {
         if (var6 >= var2 && var6 < 16 + var2 && var7 >= var3 && var7 < var3 + 16) {
            var1.field4329 -= -299739220;
         } else if (var6 >= var2 && var6 < 16 + var2 && var7 >= var3 + var4 - 16 && var7 < var3 + var4) {
            var1.field4329 += -299739220;
         } else if (var6 >= var2 - var9 && var6 < var9 + var2 + 16 && var7 >= 16 + var3 && var7 < var4 + var3 - 16) {
            int var10 = var4 * (var4 - 32) / var5;
            if (var10 < 8) {
               var10 = 8;
            }

            int var11 = var7 - var3 - 16 - var10 / 2;
            int var12 = var4 - 32 - var10;
            var1.field4329 = (var5 - var4) * var11 / var12 * -1148676629;
            var8.field6617 = true;
         }
      }

      if (this.field5382.method9853((byte)-102) != 0) {
         int var13 = var1.field4247 * 1115597881;
         if (var6 >= var2 - var13 && var7 >= var3 && var6 < 16 + var2 && var7 <= var3 + var4) {
            var1.field4329 = var1.field4329 + this.field5382.method9853((byte)-52) * -150840753;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpq;Lqp;Lvv;)V")
   @ObfuscatedName("rh")
   public static void method9175(classPQ var0, classQP var1, classVV var2) {
      if (var0 == null) {
         var0.method9174(var1, var2);
      }

      if (-1 != var2.field6610 * -77072447) {
         var0.method9178(-77072447 * var2.field6610, var1, var2, (byte)-46);
      }
   }

   public classPQ(classJX var1, classJV var2, classPU var3) {
      this.field5380 = var1;
      this.field5381 = var2;
      this.field5379 = var3;
   }

   @ObfuscatedSignature(descriptor = "(Lqn;Lvv;)V")
   @ObfuscatedName("ct")
   public void method9195(classQN var1, classVV var2) {
      ScriptEvent var3 = var1.method9719(-1432447279);

      while (var3 != null) {
         ScriptEvent var4 = var3;
         var3 = var1.method9719(-1432447279);
         Widget var5 = var4.method14144(247830512);
         if (var5.field4258 * 1988220111 >= 0) {
            Widget var6 = var2.method7421(-1502647899 * var5.field4340, (byte)39);
            if (null == var6
               || var6.field4338 == null
               || 2125812260 * var5.field4258 >= var6.field4338.length
               || var5 != var6.field4338[var5.field4258 * 63131743]) {
               continue;
            }
         }

         classSZ.method10771(var4, -2097672102);
      }

      var3 = var1.method9723(-1606946478);

      while (null != var3) {
         ScriptEvent var9 = var3;
         var3 = var1.method9723(1534126105);
         Widget var12 = var9.method14144(-1690394113);
         if (var12.field4258 * 63131743 >= 0) {
            Widget var15 = var2.method7421(-1502647899 * var12.field4340, (byte)95);
            if (null == var15
               || null == var15.field4338
               || 63131743 * var12.field4258 >= var15.field4338.length
               || var12 != var15.field4338[var12.field4258 * 769957341]) {
               continue;
            }
         }

         classSZ.method10771(var9, -262789979);
      }

      var3 = classQN.method9717(var1, (byte)111);

      while (var3 != null) {
         ScriptEvent var10 = var3;
         var3 = classQN.method9717(var1, (byte)29);
         Widget var13 = var10.method14144(886185647);
         if (var13.field4258 * 388722140 >= 0) {
            Widget var16 = var2.method7421(var13.field4340 * 192552022, (byte)63);
            if (null == var16
               || null == var16.field4338
               || 1463760122 * var13.field4258 >= var16.field4338.length
               || var13 != var16.field4338[63131743 * var13.field4258]) {
               continue;
            }
         }

         classSZ.method10771(var10, -863316723);
      }

      boolean var11 = false;

      for (classFM var14 = var1.method9724((byte)22); null != var14; var14 = var1.method9724((byte)76)) {
         if (12 == var14.field2304.field4373 * -1510882375) {
            var11 = true;
         }

         method9196(this, var14, var2, -1744225250);
      }

      if (!var11 && 1 == -1017308361 * classTZ.field6259) {
         this.field5379.vmethod357(null, 1970952563);
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;Lqp;Lvv;B)V")
   @ObfuscatedName("ay")
   void method9187(Widget[] var1, classQP var2, classVV var3, byte var4) {
      try {
         for (int var5 = 0; var5 < var1.length; var5++) {
            if (var4 != 3) {
               throw new IllegalStateException();
            }

            Widget var6 = var1[var5];
            if (null == var6) {
               if (var4 != 3) {
                  throw new IllegalStateException();
               }
            } else {
               if (0 == var6.field4373 * -1510882375) {
                  if (var4 != 3) {
                     throw new IllegalStateException();
                  }

                  if (null != var6.field4338) {
                     if (var4 != 3) {
                        return;
                     }

                     this.method9187(var6.field4338, var2, var3, (byte)3);
                  }

                  if (63131743 * var6.field4258 == -1) {
                     if (var4 != 3) {
                        throw new IllegalStateException();
                     }

                     WidgetNode var7 = (WidgetNode)HashTable.method13432(var3.field6611, -1163656551 * var6.field4327);
                     if (var7 != null) {
                        if (var4 != 3) {
                           throw new IllegalStateException();
                        }

                        this.method9178(var7.field5275 * 2023647411, var2, var3, (byte)-111);
                     }
                  }
               }

               if (classQP.field5579 == var2 && null != var6.field4404) {
                  if (var4 != 3) {
                     throw new IllegalStateException();
                  }

                  ScriptEvent var9 = classUQ.method11527(var6, 1000282158).method14195(var6.field4404, (byte)2).method14226((byte)118);
                  this.field5379.vmethod347(var9, -1390295200);
               }

               if (var2 == classQP.field5580 && null != var6.field4393) {
                  if (var4 != 3) {
                     throw new IllegalStateException();
                  }

                  if (var6.field4258 * 63131743 >= 0) {
                     if (var4 != 3) {
                        throw new IllegalStateException();
                     }

                     Widget var10 = var3.method7421(-1163656551 * var6.field4327, (byte)62);
                     if (null == var10) {
                        continue;
                     }

                     if (var4 != 3) {
                        throw new IllegalStateException();
                     }

                     if (var10.field4338 == null) {
                        continue;
                     }

                     if (var4 != 3) {
                        throw new IllegalStateException();
                     }

                     if (var6.field4258 * 63131743 >= var10.field4338.length) {
                        continue;
                     }

                     if (var4 != 3) {
                        throw new IllegalStateException();
                     }

                     if (var6 != var10.field4338[var6.field4258 * 63131743]) {
                        if (var4 != 3) {
                           throw new IllegalStateException();
                        }
                        continue;
                     }
                  }

                  ScriptEvent var11 = classUQ.method11527(var6, 753665777).method14195(var6.field4393, (byte)2).method14226((byte)70);
                  this.field5379.vmethod347(var11, -1627367532);
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "pq.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;)V")
   @ObfuscatedName("at")
   public void method9200(classVV var1) {
      classVW var2 = var1.method12226((byte)0);
      if (var2.method12278(1446562899)) {
         var2.method12274((byte)19);
         int var3 = var2.method12292((byte)2);
         int var4 = classVW.method12294(var2, (byte)-13);
         if (var2.method12326(236756604) && var2.method12329(-513900158)) {
            Widget var5 = var2.method12282(-641372995);
            Widget var6 = var2.method12284(1813734215);
            int var7 = var2.method12304(-1927006611);
            int var8 = var2.method12310((byte)-90);
            int var9 = classVW.method12298(var2, (byte)1);
            int var10 = var2.method12302(-1494652093);
            int var11 = -38255113 * classTZ.field6253;
            int var12 = -2144333897 * classTZ.field6262;
            var11 -= var3;
            var12 -= var4;
            if (var11 < var7) {
               var11 = var7;
            }

            if (1115597881 * var5.field4247 + var11 > -1749394224 * var6.field4247 + var7) {
               var11 = var6.field4247 * 2073711389 + var7 - var5.field4247 * 1568979112;
            }

            if (var12 < var8) {
               var12 = var8;
            }

            if (var12 + var5.field4257 * 805641197 > 293773697 * var6.field4257 + var8) {
               var12 = var8 + 1828929115 * var6.field4257 - -130183486 * var5.field4257;
            }

            int var13 = var11 - var9;
            int var14 = var12 - var10;
            int var15 = var5.field4366 * -782836685;
            if (var2.method12314((byte)-89) > var5.field4311 * 135384009 && (var13 > var15 || var13 < -var15 || var14 > var15 || var14 < -var15)) {
               classVW.method12321(var2, -521298073);
            }

            int var16 = var11 - var7 + -34809441 * var6.field4339;
            int var17 = var6.field4329 * -41424189 + (var12 - var8);
            if (var5.field4377 != null && classVW.method12324(var2, (short)5803)) {
               ScriptEvent var18 = classUQ.method11527(var5, 164435947)
                  .method14195(var5.field4377, (byte)2)
                  .method14197(var16, -1574127030)
                  .method14200(var17, (byte)1)
                  .method14226((byte)37);
               classSZ.method10771(var18, -924254771);
            }

            if (0 == classTZ.field6249 * 2090434187) {
               if (classVW.method12324(var2, (short)32599)) {
                  if (null != var5.field4378) {
                     ScriptEvent var21 = classUQ.method11527(var5, 1538320051)
                        .method14195(var5.field4378, (byte)2)
                        .method14197(var16, -1720279542)
                        .method14200(var17, (byte)1)
                        .method14215(var2.method12288(271014207), (byte)92)
                        .method14226((byte)69);
                     this.field5379.vmethod347(var21, -1890707290);
                  }

                  this.field5379.vmethod360(var5, var2.method12288(271014207), -2138234737);
               } else {
                  this.field5379.vmethod363(var9 + var3, var4 + var10, -511275367);
               }

               var2.method12255(2139715829);
            }
         } else {
            if (var2.method12314((byte)-82) > 1) {
               if (!classVW.method12324(var2, (short)23487) && this.field5381.vmethod158(658002820) > 0) {
                  this.field5379.vmethod344(var3 + classVW.method12298(var2, (byte)1), var4 + var2.method12302(-1963417291), (byte)33);
               }

               var2.method12255(2124738752);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqp;Lvv;I)V")
   @ObfuscatedName("ao")
   public void method9176(classQP var1, classVV var2, int var3) {
      try {
         if (-1 != var2.field6613 * -77072447) {
            this.method9178(-77072447 * var2.field6614, var1, var2, (byte)-124);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pq.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILvv;Lqn;B)V")
   @ObfuscatedName("ag")
   public void method9157(int var1, int var2, classVV var3, classQN var4, byte var5) {
      try {
         WidgetNode var6 = (WidgetNode)HashTable.method13432(var3.field6611, var1);
         WidgetNode var7 = (WidgetNode)HashTable.method13432(var3.field6611, var2);
         if (var7 != null) {
            if (var5 != 0) {
               throw new IllegalStateException();
            }

            boolean var10002;
            label48: {
               if (var6 != null) {
                  if (var5 != 0) {
                     throw new IllegalStateException();
                  }

                  if (var6.field5275 * 2023647411 == var7.field5275 * 2023647411) {
                     var10002 = false;
                     break label48;
                  }

                  if (var5 != 0) {
                     throw new IllegalStateException();
                  }
               }

               var10002 = true;
            }

            this.method9150(var7, var10002, var3, (byte)-93);
         }

         if (var6 != null) {
            if (var5 != 0) {
               throw new IllegalStateException();
            }

            var6.vmethod400();
            var3.field6616.method13419(var6, var2);
         }

         Widget var8 = var3.method7421(var2, (byte)47);
         if (var8 != null) {
            if (var5 != 0) {
               throw new IllegalStateException();
            }

            classFC.method4609(var3.field4438[var8.field4257 * -1163656551 >>> 16], var8, true, var3, var4, 897179765);
         }

         method9173(this, classQP.field5580, var3, -1543721167);
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "pq.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfm;Lvv;I)V")
   @ObfuscatedName("ap")
   void method9197(classFM var1, classVV var2, int var3) {
      try {
         if (var1 != null && var1.field2304 != null) {
            if (var1.field2304.field4282 * 63131743 >= 0) {
               Widget var4 = var2.method7421(-1502647899 * var1.field2304.field4283, (byte)31);
               if (null == var4) {
                  return;
               }

               if (var3 == -948608385) {
                  throw new IllegalStateException();
               }

               if (null == var4.field4338) {
                  return;
               }

               if (var3 == -948608385) {
                  throw new IllegalStateException();
               }

               if (var4.field4338.length == 0) {
                  return;
               }

               if (var3 == -948608385) {
                  throw new IllegalStateException();
               }

               if (63131743 * var1.field2304.field4373 >= var4.field4338.length) {
                  return;
               }

               if (var4.field4338[63131743 * var1.field2304.field4272] != var1.field2304) {
                  if (var3 == -948608385) {
                     return;
                  }

                  return;
               }
            }

            if (-1510882375 * var1.field2304.field4272 == 11) {
               if (var3 == -948608385) {
                  throw new IllegalStateException();
               }

               if (0 == var1.field2305 * -1735555937) {
                  if (var3 == -948608385) {
                     throw new IllegalStateException();
                  }

                  if (var1.field2304.method7294(1605458035 * var1.field2303, var1.field2303 * 213240559, 0, 0, (byte)91)) {
                     if (var3 == -948608385) {
                        throw new IllegalStateException();
                     }

                     this.field5379.vmethod354(var1.field2304, (byte)127);
                  }

                  return;
               }
            }

            if (-1510882375 * var1.field2304.field4298 == 12) {
               if (var3 == -948608385) {
                  return;
               }

               classLH var6 = var1.field2304.method7334(-619129432);
               if (var6 != null) {
                  if (var3 == -948608385) {
                     throw new IllegalStateException();
                  }

                  if (var6.method6872(-880967958)) {
                     if (var3 == -948608385) {
                        throw new IllegalStateException();
                     }

                     switch (-1735555937 * var1.field2306) {
                        case 0:
                           this.field5379.vmethod357(var1.field2304, 1750352878);
                           var6.method6987(
                              1605458035 * var1.field2303,
                              var1.field2303 * 213240559,
                              this.field5380.vmethod72(-1370068174).method4557(82, 353603741),
                              this.field5380.vmethod72(-429159985).method4557(81, 742348860),
                              14704747
                           );
                           break;
                        case 1:
                           classLH.method6993(var6, var1.field2305 * 1605458035, 213240559 * var1.field2305, -1740986807);
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "pq.ap(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lte;)[J")
   @ObfuscatedName("an")
   public static long[] method9149(classTE var0) {
      return var0.field6125;
   }

   @ObfuscatedSignature(descriptor = "(ILvv;I)V")
   @ObfuscatedName("aj")
   void method9184(int var1, classVV var2, int var3) {
      try {
         if (var1 == -1) {
            if (var3 >= 1377666464) {
               throw new IllegalStateException();
            } else {
               this.method9202(var1, var2);
            }
         } else if (!var2.method7425(var1, 943864010)) {
            if (var3 >= 1377666464) {
               this.method9208(var1, var2);
            } else {
               this.method9202(var1, var2);
            }
         } else {
            Widget[] var4 = var2.field4438[var1];

            for (int var5 = 0; var5 < var4.length; var5++) {
               if (var3 >= 1377666464) {
                  throw new IllegalStateException();
               }

               Widget var6 = var4[var5];
               if (var6.field4391 != null) {
                  if (var3 >= 1377666464) {
                     this.method9208(var1, var2);
                     return;
                  }

                  ScriptEvent var7 = classUQ.method11527(var6, 841725730).method14195(var6.field4382, (byte)2).method14226((byte)8);
                  this.field5379.vmethod349(var7, -1973248401);
               }
            }

            this.method9202(var1, var2);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "pq.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIIIIIIIILvv;ILqn;B)V")
   @ObfuscatedName("ac")
   void method9166(
      Widget[] var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      classVV var12,
      int var13,
      classQN var14,
      byte var15
   ) {
      this.method9205(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14);

      try {
         boolean var16 = this.field5381.vmethod156((byte)0);
         classFA var17 = this.field5380.vmethod72(94586861);

         for (int var18 = var2; var18 <= var3; var18++) {
            if (var15 >= 9) {
               throw new IllegalStateException();
            }

            Widget var19 = var1[var18];
            if (var19 == null) {
               if (var15 >= 9) {
                  throw new IllegalStateException();
               }
            } else if (-1502647899 * var19.field4248 == var4) {
               if (var15 >= 9) {
                  return;
               }

               if (653784551 * var19.field4245 == var5 && var12.method12241(var19, (byte)-45)) {
                  if (var19.field4312) {
                     if (var12.method12241(var19, (byte)0)) {
                        continue;
                     }
                  } else if (var19.field4311 * -1510882375 == 0) {
                     if (var15 >= 9) {
                        throw new IllegalStateException();
                     }

                     if (!classVV.method12187(var12, 1645420575).method12002(var19, (byte)68)) {
                        if (var15 >= 9) {
                           return;
                        }

                        if (var12.method12241(var19, (byte)0)) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }
                           continue;
                        }
                     }
                  }

                  if (-1510882375 * var19.field4268 == 11) {
                     if (var19.method7325(var12, this.field5380.vmethod78((byte)7), 2146600203)) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (var19.method7298(1355775728)) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           classFC.method4609(var19.field4338, var19, true, var12, var14, -1178832808);
                           Widget.method7322(var19, -1626603432)
                              .method7472((byte)-113)
                              .method7741(3, Widget.method7322(var19, -1626603432).method7521(1235443706), (byte)10);
                        }

                        if (null != var19.field4389) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           ScriptEvent var20 = classUQ.method11527(var19, 1821758521).method14195(var19.field4393, (byte)2).method14226((byte)13);
                           var14.method9709(var20, 1697206599);
                        }
                     }
                  } else if (12 == var19.field4243 * -1510882375) {
                     var19.method7350(var12, 1317942401);
                  }

                  int var38 = var19.field4240 * 744286069 + var10;
                  int var21 = var11 + 1570844829 * var19.field4256;
                  int var22;
                  int var23;
                  int var24;
                  int var25;
                  if (9 == -1510882375 * var19.field4319) {
                     if (var15 >= 9) {
                        return;
                     }

                     int var26 = var38;
                     int var27 = var21;
                     int var28 = 1115597881 * var19.field4329 + var38;
                     int var29 = var21 + var19.field4318 * 293773697;
                     if (var28 < var38) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        var26 = var28;
                        var28 = var38;
                     }

                     if (var29 < var21) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        var27 = var29;
                        var29 = var21;
                     }

                     var28++;
                     var29++;
                     int var10000;
                     if (var26 > var6) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        var10000 = var26;
                     } else {
                        var10000 = var6;
                     }

                     var22 = var10000;
                     if (var27 > var7) {
                        if (var15 >= 9) {
                           return;
                        }

                        var10000 = var27;
                     } else {
                        var10000 = var7;
                     }

                     var23 = var10000;
                     var24 = var28 < var8 ? var28 : var8;
                     if (var29 < var9) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        var10000 = var29;
                     } else {
                        var10000 = var9;
                     }

                     var25 = var10000;
                  } else {
                     int var39 = var19.field4283 * 1115597881 + var38;
                     int var41 = var19.field4264 * 293773697 + var21;
                     int var98;
                     if (var38 > var6) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        var98 = var38;
                     } else {
                        var98 = var6;
                     }

                     var22 = var98;
                     if (var21 > var7) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        var98 = var21;
                     } else {
                        var98 = var7;
                     }

                     var23 = var98;
                     if (var39 < var8) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        var98 = var39;
                     } else {
                        var98 = var8;
                     }

                     var24 = var98;
                     var25 = var41 < var9 ? var41 : var9;
                  }

                  var12.method12226((byte)0).method12263(var19, var38, var21, 2103779794);
                  boolean var40 = false;
                  if (var19.field4350) {
                     if (var15 >= 9) {
                        throw new IllegalStateException();
                     }

                     switch (var12.field6614 * -698898037) {
                        case 0:
                           var40 = true;
                        case 1:
                        default:
                           break;
                        case 2:
                           if (-1163656551 * var19.field4271 >>> 16 == -336184109 * var12.field6613) {
                              if (var15 >= 9) {
                                 return;
                              }

                              var40 = true;
                           }
                           break;
                        case 3:
                           if (-1163656551 * var19.field4239 == var12.field6614 * -336184109) {
                              var40 = true;
                           }
                     }
                  }

                  if (!var40) {
                     if (var15 >= 9) {
                        return;
                     }

                     if (var19.field4296) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (var22 >= var24) {
                           continue;
                        }

                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (var23 >= var25) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }
                           continue;
                        }
                     }
                  }

                  if (var19.field4321) {
                     if (var19.field4273) {
                        if (this.field5382.method9838((byte)25) >= var22) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (this.field5382.method9850(-391255169) >= var23) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (this.field5382.method9838((byte)84) < var24 && this.field5382.method9843(308056551) < var25) {
                                 var14.method9726(1763721559);
                                 var12.method12226((byte)0).method12274((byte)-22);
                                 if (!var16) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    this.field5379.vmethod317(891814198);
                                 }
                              }
                           }
                        }
                     } else if (var19.field4304) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (this.field5382.method9848((byte)27) >= var22) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (this.field5382.method9843(121629884) >= var23 && this.field5382.method9848((byte)9) < var24) {
                              if (var15 >= 9) {
                                 return;
                              }

                              if (this.field5382.method9840(78121091) < var25) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 var14.method9726(-810050472);
                              }
                           }
                        }
                     }
                  }

                  int var42 = this.field5382.method9838((byte)45);
                  int var44 = this.field5382.method9840(844620399);
                  if (this.field5382.method9850(1784200476) != 0) {
                     if (var15 >= 9) {
                        throw new IllegalStateException();
                     }

                     var42 = this.field5382.method9853((byte)60);
                     var44 = this.field5382.method9843(1997119720);
                  }

                  boolean var101;
                  label1500: {
                     if (var42 >= var22) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (var44 >= var23 && var42 < var24) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var44 < var25) {
                              if (var15 >= 9) {
                                 return;
                              }

                              var101 = true;
                              break label1500;
                           }
                        }
                     }

                     var101 = false;
                  }

                  boolean var46 = var101;
                  if (var19.field4238 * -90794217 == 1337) {
                     if (var15 >= 9) {
                        throw new IllegalStateException();
                     }

                     if (!this.field5381.vmethod166((byte)-2)) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (!var16) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var46) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              this.field5379.vmethod321(var42, var44, var22, var23, -113743310);
                           }
                        }
                     }
                  } else if (1338 == -90794217 * var19.field4247) {
                     if (var15 >= 9) {
                        throw new IllegalStateException();
                     }

                     classNS.method8041(var19, var38, var21, (byte)84);
                  } else {
                     if (1400 == var19.field4239 * -90794217) {
                        if (var15 >= 9) {
                           return;
                        }

                        RenderOverview.method12456(
                           this.field5380.vmethod84(1601996065),
                           var42,
                           var44,
                           var46,
                           var38,
                           var21,
                           var19.field4283 * 1115597881,
                           var19.field4243 * 293773697,
                           (byte)79
                        );
                     }

                     if (!var16 && var46) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (-90794217 * var19.field4253 == 1400) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           this.field5380
                              .vmethod84(454138964)
                              .method12645(var38, var21, var19.field4253 * 1115597881, 293773697 * var19.field4248, var42, var44, -757606060);
                        } else {
                           this.field5379.vmethod324(var19, (byte)-37);
                        }
                     }

                     if (var40) {
                        for (int var30 = 0; var30 < var19.field4344.length; var30++) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           boolean var31 = false;
                           boolean var32 = false;
                           if (!var31) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              label1438:
                              if (null != var19.field4310[var30]) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 int var33 = 0;

                                 while (true) {
                                    if (var33 >= var19.field4310[var30].length) {
                                       break label1438;
                                    }

                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    boolean var34 = false;
                                    if (null != var19.field4278) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       var34 = var17.method4563(var19.field4344[var30][var33], 1030177718);
                                    }

                                    label1523: {
                                       if (!this.field5380.vmethod74((byte)29).method5087(var19.field4344[var30][var33], 1520410371)) {
                                          if (!var34) {
                                             break label1523;
                                          }

                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }
                                       }

                                       var31 = true;
                                       if (var19.field4402 != null) {
                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          if (var19.field4399[var30] > var13) {
                                             break label1438;
                                          }

                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }
                                       }

                                       byte var35 = var19.field4344[var30][var33];
                                       if (var35 == 0) {
                                          break;
                                       }

                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       if ((var35 & 8) != 0) {
                                          if (var17.method4557(86, -207049828) || var17.method4563(82, -227637202)) {
                                             break label1523;
                                          }

                                          if (var17.method4563(81, 53705378)) {
                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }
                                             break label1523;
                                          }
                                       }

                                       if (0 != (var35 & 2)) {
                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          if (!var17.method4557(86, 2129917571)) {
                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }
                                             break label1523;
                                          }
                                       }

                                       if ((var35 & 1) != 0) {
                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          if (!var17.method4563(82, 125044319)) {
                                             break label1523;
                                          }
                                       }

                                       if ((var35 & 4) == 0) {
                                          break;
                                       }

                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       if (var17.method4557(81, -99888740)) {
                                          break;
                                       }

                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }
                                    }

                                    var33++;
                                 }

                                 var32 = true;
                              }
                           }

                           if (var32) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var30 < 10) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 this.field5379
                                    .vmethod329(
                                       1 + var30, -1163656551 * var19.field4255, var19.field4325 * 63131743, var19.field4279 * 2073671441, "", -510577957
                                    );
                              } else if (var30 == 10) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 this.field5379.vmethod337((byte)-80);
                                 String var61 = var12.method12201(var19, (byte)-124);
                                 if (var61 == null) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    var61 = classKK.field3701;
                                 }

                                 String var80 = var19.field4299 + classQY.method9885(16777215, (byte)101);
                                 int var87 = classVV.method12246(var12, var19, (byte)0);
                                 this.field5379
                                    .vmethod334(
                                       -1163656551 * var19.field4240,
                                       63131743 * var19.field4289,
                                       classCK.method1569(var87, -2126468581),
                                       var19.field4249 * 2073671441,
                                       var61,
                                       var80,
                                       (byte)104
                                    );
                              }

                              int var62 = var19.field4278[var30];
                              if (null == var19.field4388) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 var19.field4369 = new int[var19.field4310.length];
                              }

                              if (null == var19.field4369) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 var19.field4399 = new int[var19.field4344.length];
                              }

                              if (0 != var62) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (0 == var19.field4368[var30]) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    var19.field4368[var30] = var62 + var13 + var19.field4370[var30];
                                 } else {
                                    var19.field4370[var30] = var13 + var62;
                                 }
                              } else {
                                 var19.field4402[var30] = Integer.MAX_VALUE;
                              }
                           }

                           if (!var31) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (null != var19.field4368) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 var19.field4265[var30] = 0;
                              }
                           }
                        }
                     }

                     if (var19.field4401) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        label1423: {
                           if (this.field5382.method9838((byte)104) >= var22) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (this.field5382.method9850(1542204717) >= var23) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (this.field5382.method9838((byte)62) < var24) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    if (this.field5382.method9840(-211701125) < var25) {
                                       var101 = true;
                                       break label1423;
                                    }
                                 }
                              }
                           }

                           var101 = false;
                        }

                        label1413: {
                           label1412: {
                              var46 = var101;
                              if (classQS.method9834(this.field5382, -361602593) != 1) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (this.field5381.vmethod168(-672770366) || classQS.method9834(this.field5382, -361602593) != 4) {
                                    break label1412;
                                 }

                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }
                              }

                              if (var46) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 var101 = true;
                                 break label1413;
                              }
                           }

                           var101 = false;
                        }

                        boolean var48;
                        label1401: {
                           label1400: {
                              var48 = var101;
                              if (this.field5382.method9840(1784200476) != 1) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (this.field5381.vmethod168(-1931282748) || this.field5382.method9850(1784200476) != 4) {
                                    break label1400;
                                 }

                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }
                              }

                              if (this.field5382.method9838((byte)13) >= var22) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (this.field5382.method9850(1997119720) >= var23) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (this.field5382.method9853((byte)-58) < var24) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       if (this.field5382.method9850(1997119720) < var25) {
                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          var101 = true;
                                          break label1401;
                                       }
                                    }
                                 }
                              }
                           }

                           var101 = false;
                        }

                        boolean var50 = var101;
                        if (var50) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (!this.field5381.vmethod156((byte)0)
                              && var12.method12233(var19, this.field5382.method9848((byte)6) - var38, this.field5382.method9850(1997119720) - var21, 1283450176)
                              )
                            {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              this.field5379.vmethod343((byte)31);
                           }
                        }

                        if (Widget.method7263(var19, 525274146)) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var50) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              var14.method9714(
                                 new classFM(0, -38255113 * classTZ.field6253 - var38, classTZ.field6262 * -2144333897 - var21, var19), -1243787837
                              );
                           }

                           if (var48) {
                              if (var15 >= 9) {
                                 return;
                              }

                              var14.method9714(
                                 new classFM(1, classTZ.field6262 * -38255113 - var38, classTZ.field6253 * -2144333897 - var21, var19), -1974220788
                              );
                           }
                        }

                        if (-90794217 * var19.field4327 == 1400) {
                           if (var15 >= 9) {
                              return;
                           }

                           this.field5380.vmethod84(-587532282).method12458(var42, var44, var46 & var48, var46 & var50, -1962520718);
                        }

                        if (var12.method12230(-431955666)) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (classVV.method12228(var12, (byte)-48) != var19) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var46) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (classUH.method11381(classVV.method12246(var12, var19, (byte)0), (byte)-81)) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    var12.method12226((byte)0).method12272(var19, (byte)-30);
                                 }
                              }
                           }
                        }

                        var12.method12226((byte)0).method12265(var19, var38, var21, (short)-32309);
                        if (var19.field4296) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (var46 && this.field5382.method9853((byte)-31) != 0 && var19.field4391 != null) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              ScriptEvent var51 = classUQ.method11527(var19, 541902774)
                                 .method14187(true, 754222515)
                                 .method14195(var19.field4381, (byte)2)
                                 .method14200(this.field5382.method9838((byte)-26), (byte)1)
                                 .method14226((byte)45);
                              var14.method9709(var51, 1697206599);
                           }

                           label1375: {
                              if (!var12.method12230(-431955666)) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (!this.field5381.vmethod156((byte)0)) {
                                    break label1375;
                                 }

                                 if (var15 >= 9) {
                                    return;
                                 }
                              }

                              var50 = false;
                              var48 = false;
                              var46 = false;
                           }

                           if (!var19.field4304) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var50) {
                                 var19.field4269 = true;
                                 if (null != var19.field4376) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    ScriptEvent var52 = classUQ.method11527(var19, -82195840)
                                       .method14187(true, 1196267688)
                                       .method14195(var19.field4396, (byte)2)
                                       .method14222(1422993977 * classTZ.field6264 - var38, -1985759611)
                                       .method14200(1243590197 * classTZ.field6262 - var21, (byte)1)
                                       .method14226((byte)121);
                                    var14.method9709(var52, 1697206599);
                                 }
                              }
                           }

                           if (var19.field4326 && var48) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (null != var19.field4385) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var53 = classUQ.method11527(var19, 447233436)
                                    .method14187(true, -813950478)
                                    .method14195(var19.field4384, (byte)2)
                                    .method14222(classTZ.field6253 * -38255113 - var38, -1797422897)
                                    .method14200(classTZ.field6259 * -2144333897 - var21, (byte)1)
                                    .method14226((byte)98);
                                 var14.method9709(var53, 1697206599);
                              }
                           }

                           if (var19.field4312) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (!var48) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 var19.field4312 = false;
                                 if (var19.field4353 != null) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    ScriptEvent var54 = classUQ.method11527(var19, 251761525)
                                       .method14187(true, -83845772)
                                       .method14195(var19.field4400, (byte)2)
                                       .method14219(-38255113 * classTZ.field6262 - var38, -1090908077)
                                       .method14200(classTZ.field6253 * -2144333897 - var21, (byte)1)
                                       .method14226((byte)5);
                                    classQN.method9711(var14, var54, 1513375706);
                                 }
                              }
                           }

                           if (var48) {
                              if (var15 >= 9) {
                                 return;
                              }

                              if (var19.field4400 != null) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var55 = classUQ.method11527(var19, 292910948)
                                    .method14187(true, -816691237)
                                    .method14195(var19.field4386, (byte)2)
                                    .method14197(classTZ.field6249 * -38255113 - var38, -1963929087)
                                    .method14200(classTZ.field6253 * -2144333897 - var21, (byte)1)
                                    .method14226((byte)98);
                                 var14.method9709(var55, 1697206599);
                              }
                           }

                           if (!var19.field4320) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var46) {
                                 var19.field4350 = true;
                                 if (var19.field4244 != null) {
                                    ScriptEvent var56 = classUQ.method11527(var19, 325458216)
                                       .method14187(true, 1882697465)
                                       .method14195(var19.field4348, (byte)2)
                                       .method14222(-38255113 * classTZ.field6253 - var38, -2087208916)
                                       .method14200(classTZ.field6249 * -2144333897 - var21, (byte)1)
                                       .method14226((byte)105);
                                    var14.method9709(var56, 1697206599);
                                 }
                              }
                           }

                           if (var19.field4313) {
                              if (var15 >= 9) {
                                 return;
                              }

                              if (var46) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (null != var19.field4284) {
                                    ScriptEvent var57 = classUQ.method11527(var19, 1235048948)
                                       .method14187(true, -1385284032)
                                       .method14195(var19.field4353, (byte)2)
                                       .method14222(classTZ.field6259 * -38255113 - var38, -1441134992)
                                       .method14200(-2144333897 * classTZ.field6264 - var21, (byte)1)
                                       .method14226((byte)105);
                                    var14.method9709(var57, 1697206599);
                                 }
                              }
                           }

                           if (var19.field4358 && !var46) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              var19.field4273 = false;
                              if (var19.field4353 != null) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var58 = classUQ.method11527(var19, 449928328)
                                    .method14187(true, 1982805502)
                                    .method14195(var19.field4377, (byte)2)
                                    .method14204(-38255113 * classTZ.field6253 - var38, -1869798690)
                                    .method14200(classTZ.field6259 * -2144333897 - var21, (byte)1)
                                    .method14226((byte)30);
                                 classQN.method9711(var14, var58, 1210007885);
                              }
                           }

                           if (null != var19.field4396) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              ScriptEvent var59 = classUQ.method11527(var19, 1089979455).method14195(var19.field4341, (byte)2).method14226((byte)4);
                              var14.method9710(var59, (byte)-85);
                           }

                           classPV var60 = this.field5380.vmethod81(1237108207);
                           if (var19.field4284 != null && classPV.method9321(var60, (byte)-97) > -405159805 * var19.field4317) {
                              label1354: {
                                 if (var19.field4278 != null) {
                                    if (classPV.method9321(var60, (byte)-61) - var19.field4289 * -405159805 <= 32) {
                                       int var64 = var19.field4253 * -405159805;

                                       while (true) {
                                          if (var64 >= classPV.method9321(var60, (byte)-119)) {
                                             break label1354;
                                          }

                                          int var81 = classPV.method9317(var60, var64, -445124758);

                                          for (int var88 = 0; var88 < var19.field4388.length; var88++) {
                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }

                                             if (var81 == var19.field4383[var88]) {
                                                if (var15 >= 9) {
                                                   throw new IllegalStateException();
                                                }

                                                ScriptEvent var36 = classUQ.method11527(var19, 341775175)
                                                   .method14195(var19.field4381, (byte)2)
                                                   .method14226((byte)64);
                                                var14.method9709(var36, 1697206599);
                                                break label1354;
                                             }
                                          }

                                          var64++;
                                       }
                                    }

                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }
                                 }

                                 ScriptEvent var63 = classUQ.method11527(var19, -146679022).method14195(var19.field4386, (byte)2).method14226((byte)120);
                                 var14.method9709(var63, 1697206599);
                              }

                              var19.field4267 = classPV.method9321(var60, (byte)0) * -1433629141;
                           }

                           if (var19.field4393 != null) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var60.method9342(230517950) > var19.field4308 * 790413063) {
                                 label1328: {
                                    if (var19.field4383 != null) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       if (var60.method9339(230517950) - var19.field4257 * 790413063 <= 32) {
                                          int var66 = 790413063 * var19.field4323;

                                          while (true) {
                                             if (var66 >= var60.method9323(230517950)) {
                                                break label1328;
                                             }

                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }

                                             int var82 = var60.method9326(var66, 393471540);

                                             for (int var89 = 0; var89 < var19.field4399.length; var89++) {
                                                if (var82 == var19.field4370[var89]) {
                                                   if (var15 >= 9) {
                                                      throw new IllegalStateException();
                                                   }

                                                   ScriptEvent var94 = classUQ.method11527(var19, 682125758)
                                                      .method14195(var19.field4379, (byte)2)
                                                      .method14226((byte)27);
                                                   var14.method9709(var94, 1697206599);
                                                   break label1328;
                                                }
                                             }

                                             var66++;
                                          }
                                       }

                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }
                                    }

                                    ScriptEvent var65 = classUQ.method11527(var19, 639570543).method14195(var19.field4359, (byte)2).method14226((byte)126);
                                    var14.method9709(var65, 1697206599);
                                 }

                                 var19.field4248 = var60.method9349(230517950) * -425558345;
                              }
                           }

                           if (null != var19.field4384) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var60.method9325(1288219174) > var19.field4289 * 1396002941) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 label1301: {
                                    if (var19.field4402 != null) {
                                       if (var15 >= 9) {
                                          return;
                                       }

                                       if (var60.method9332(1288219174) - 1396002941 * var19.field4283 <= 32) {
                                          int var68 = 1396002941 * var19.field4322;

                                          while (true) {
                                             if (var68 >= var60.method9325(1288219174)) {
                                                break label1301;
                                             }

                                             int var83 = var60.method9326(var68, -1111491396);

                                             for (int var90 = 0; var90 < var19.field4278.length; var90++) {
                                                if (var15 >= 9) {
                                                   throw new IllegalStateException();
                                                }

                                                if (var19.field4369[var90] == var83) {
                                                   if (var15 >= 9) {
                                                      throw new IllegalStateException();
                                                   }

                                                   ScriptEvent var95 = classUQ.method11527(var19, 1366800940)
                                                      .method14195(var19.field4384, (byte)2)
                                                      .method14226((byte)46);
                                                   var14.method9709(var95, 1697206599);
                                                   break label1301;
                                                }
                                             }

                                             var68++;
                                          }
                                       }

                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }
                                    }

                                    ScriptEvent var67 = classUQ.method11527(var19, -67274947).method14195(var19.field4359, (byte)2).method14226((byte)90);
                                    var14.method9709(var67, 1697206599);
                                 }

                                 var19.field4289 = var60.method9324(1288219174) * 297445589;
                              }
                           }

                           if (var60.method9342(2081930497) > var19.field4256 * -1941419841) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var19.field4385 != null) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var69 = classUQ.method11527(var19, 763523364).method14195(var19.field4361, (byte)2).method14226((byte)53);
                                 var14.method9709(var69, 1697206599);
                              }
                           }

                           if (var60.method9342(-1286699135) > var19.field4297 * -1941419841) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var19.field4244 != null) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var70 = classUQ.method11527(var19, 1282543540).method14195(var19.field4244, (byte)2).method14226((byte)6);
                                 var14.method9709(var70, 1697206599);
                              }
                           }

                           if (var60.method9354(-2046021315) > var19.field4250 * -1941419841) {
                              if (var15 >= 9) {
                                 return;
                              }

                              if (var19.field4363 != null) {
                                 ScriptEvent var71 = classUQ.method11527(var19, 313901820).method14195(var19.field4377, (byte)2).method14226((byte)2);
                                 var14.method9709(var71, 1697206599);
                              }
                           }

                           if (var60.method9336(-1087972267) > -1941419841 * var19.field4319) {
                              if (var15 >= 9) {
                                 return;
                              }

                              if (null != var19.field4385) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var72 = classUQ.method11527(var19, 1736207581).method14195(var19.field4384, (byte)2).method14226((byte)122);
                                 var14.method9709(var72, 1697206599);
                              }
                           }

                           if (classPV.method9345(var60, (byte)-1) > var19.field4308 * -1941419841) {
                              if (var15 >= 9) {
                                 return;
                              }

                              if (null != var19.field4374) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var73 = classUQ.method11527(var19, 1571576681).method14195(var19.field4380, (byte)2).method14226((byte)64);
                                 var14.method9709(var73, 1697206599);
                              }
                           }

                           if (var60.method9323(-693284363) > -1941419841 * var19.field4325) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (null != var19.field4378) {
                                 ScriptEvent var74 = classUQ.method11527(var19, 1456317932).method14195(var19.field4404, (byte)2).method14226((byte)91);
                                 var14.method9709(var74, 1697206599);
                              }
                           }

                           if (var60.method9349(-2110015517) > -1941419841 * var19.field4263) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var19.field4382 != null) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 ScriptEvent var75 = classUQ.method11527(var19, 707258764).method14195(var19.field4395, (byte)2).method14226((byte)51);
                                 var14.method9709(var75, 1697206599);
                              }
                           }

                           if (var60.method9322((byte)4) > var19.field4311 * -1941419841) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var19.field4348 != null) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 ScriptEvent var76 = classUQ.method11527(var19, 700046352).method14195(var19.field4377, (byte)2).method14226((byte)119);
                                 var14.method9709(var76, 1697206599);
                              }
                           }

                           var19.field4260 = var60.method9349(342962976) * 1478436671;
                           if (var19.field4384 != null) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              classFW var77 = this.field5380.vmethod74((byte)72);

                              for (int var84 = 0; var84 < classFW.method5066(var77, 892616072); var84++) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 ScriptEvent var91 = classUQ.method11527(var19, 300971475)
                                    .method14195(var19.field4244, (byte)2)
                                    .method14210(var77.method5085(var84, 2069198089), 641798308)
                                    .method14222(var77.method5085(var84, -1837535319), 1410490447)
                                    .method14226((byte)126);
                                 var14.method9709(var91, 1697206599);
                              }
                           }

                           if (null != var19.field4355) {
                              int[] var78 = var17.method4578(-1056195858);

                              for (int var85 = 0; var85 < var78.length; var85++) {
                                 ScriptEvent var92 = classUQ.method11527(var19, 1154772678)
                                    .method14195(var19.field4353, (byte)2)
                                    .method14210(var78[var85], -1942436019)
                                    .method14226((byte)42);
                                 var14.method9709(var92, 1697206599);
                              }
                           }

                           if (null != var19.field4341) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              int[] var79 = classFA.method4574(var17, 1765027070);

                              for (int var86 = 0; var86 < var79.length; var86++) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 ScriptEvent var93 = classUQ.method11527(var19, 99096592)
                                    .method14195(var19.field4391, (byte)2)
                                    .method14219(var79[var86], 349750555)
                                    .method14226((byte)73);
                                 var14.method9709(var93, 1697206599);
                              }
                           }
                        }
                     }

                     if (!var19.field4371) {
                        if (var12.method12230(-431955666)) {
                           continue;
                        }

                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        if (this.field5381.vmethod156((byte)0)) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }
                           continue;
                        }

                        label1237: {
                           if (-2003253155 * var19.field4339 < 0) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (var19.field4260 * 318120869 == 0) {
                                 break label1237;
                              }

                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }
                           }

                           if (this.field5382.method9838((byte)96) >= var22) {
                              if (var15 >= 9) {
                                 throw new IllegalStateException();
                              }

                              if (this.field5382.method9840(758337458) >= var23) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (this.field5382.method9838((byte)119) < var24) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (this.field5382.method9850(859048038) < var25) {
                                       if (var15 >= 9) {
                                          return;
                                       }

                                       if (var19.field4248 * -2003253155 >= 0) {
                                          if (var15 >= 9) {
                                             return;
                                          }

                                          classVC.method12003(classVV.method12187(var12, 252097765), var1[-2003253155 * var19.field4267], -1766608502);
                                       } else {
                                          classVC.method12003(classVV.method12187(var12, 2064189916), var19, -1864074974);
                                       }
                                    }
                                 }
                              }
                           }
                        }

                        if (8 == -1510882375 * var19.field4255) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           if (this.field5382.method9838((byte)35) >= var22) {
                              if (var15 >= 9) {
                                 return;
                              }

                              if (this.field5382.method9840(1525346113) >= var23) {
                                 if (var15 >= 9) {
                                    throw new IllegalStateException();
                                 }

                                 if (this.field5382.method9853((byte)81) < var24) {
                                    if (var15 >= 9) {
                                       throw new IllegalStateException();
                                    }

                                    if (this.field5382.method9840(-1039273438) < var25) {
                                       if (var15 >= 9) {
                                          throw new IllegalStateException();
                                       }

                                       classVV.method12187(var12, 946925930).method12010(var19, -1106344526);
                                    }
                                 }
                              }
                           }
                        }

                        if (-714521463 * var19.field4239 > 293773697 * var19.field4314) {
                           if (var15 >= 9) {
                              throw new IllegalStateException();
                           }

                           this.method9167(
                              var19,
                              var19.field4373 * 1115597881 + var38,
                              var21,
                              293773697 * var19.field4245,
                              -714521463 * var19.field4318,
                              this.field5382.method9838((byte)120),
                              this.field5382.method9840(1376332737),
                              var12,
                              1303753171
                           );
                        }
                     }

                     if (-1510882375 * var19.field4253 == 0) {
                        if (var15 >= 9) {
                           throw new IllegalStateException();
                        }

                        method9164(
                           this,
                           var1,
                           var19.field4336 * -1210177335,
                           var19.field4268 * 379541057,
                           -1163656551 * var19.field4260,
                           63131743 * var19.field4349,
                           var22,
                           var23,
                           var24,
                           var25,
                           var38 - var19.field4319 * -34809441,
                           var21 - var19.field4331 * -41424189,
                           var12,
                           var13,
                           var14,
                           (byte)-28
                        );
                        if (var19.field4338 != null) {
                           if (var15 >= 9) {
                              return;
                           }

                           method9164(
                              this,
                              var19.field4338,
                              0,
                              var19.field4338.length - 1,
                              var19.field4333 * -1163656551,
                              -1,
                              var22,
                              var23,
                              var24,
                              var25,
                              var38 - var19.field4339 * -34809441,
                              var21 - var19.field4259 * -41424189,
                              var12,
                              var13,
                              var14,
                              (byte)-90
                           );
                        }

                        if (-1 == 63131743 * var19.field4291) {
                           if (var15 >= 9) {
                              return;
                           }

                           WidgetNode var49 = (WidgetNode)HashTable.method13432(var12.field6611, -1163656551 * var19.field4294);
                           if (var49 != null) {
                              if (var15 >= 9) {
                                 return;
                              }

                              if (-691218541 * var49.field5274 == 0) {
                                 if (var15 >= 9) {
                                    return;
                                 }

                                 if (-38255113 * classTZ.field6249 >= var22) {
                                    if (var15 >= 9) {
                                       return;
                                    }

                                    if (-2144333897 * classTZ.field6255 >= var23 && -38255113 * classTZ.field6249 < var24) {
                                       if (var15 >= 9) {
                                          return;
                                       }

                                       if (-2144333897 * classTZ.field6255 < var25 && !this.field5381.vmethod156((byte)0)) {
                                          if (var15 >= 9) {
                                             throw new IllegalStateException();
                                          }

                                          var14.method9733(1806526567);
                                          var12.method12226((byte)0).method12274((byte)51);
                                          if (!var16) {
                                             if (var15 >= 9) {
                                                throw new IllegalStateException();
                                             }

                                             this.field5379.vmethod317(891814198);
                                          }
                                       }
                                    }
                                 }
                              }

                              this.method9161(var49.field5275 * 2023647411, var22, var23, var24, var25, var38, var21, var12, var13, var14, (byte)9);
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var37) {
         throw classEG.method3899(var37, "pq.ac(" + ')');
      }
   }
}
