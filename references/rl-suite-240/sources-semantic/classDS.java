import java.io.EOFException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.runelite.api.events.VarClientIntChanged;
import net.runelite.api.events.VarClientStrChanged;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ds")
public class classDS {
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field1616 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field1617 = 3;
   @ObfuscatedName("ag")
   final int[] field1612;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final String field1620 = "2";
   @ObfuscatedName("ac")
   long field1619;
   @ObfuscatedName("as")
   final Map field1614;
   @ObfuscatedName("ax")
   boolean field1613 = false;
   @ObfuscatedName("ab")
   final boolean[] field1615;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field1618 = 10000;
   @ObfuscatedSignature(descriptor = "Luk;")
   @ObfuscatedName("la")
   static classUK field1621;

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;I)V")
   @ObfuscatedName("ag")
   void method3566(int var1, Object var2, int var3) {
      try {
         this.field1614.put(var1, var2);
         if (this.field1615[var1]) {
            if (var3 == -2089279832) {
               throw new IllegalStateException();
            }

            this.field1613 = true;
         }

         this.method3606(var1, (String)var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ds.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)J")
   @ObfuscatedName("ab")
   long method3562(int var1, int var2) {
      try {
         Object var3 = this.field1614.get(var1);
         if (var3 instanceof Integer) {
            if (var2 == 793642421) {
               throw new IllegalStateException();
            } else {
               return ((Integer)var3).intValue();
            }
         } else if (var3 instanceof Long) {
            if (var2 == 793642421) {
               throw new IllegalStateException();
            } else {
               return (Long)var3;
            }
         } else {
            return -1L;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ds.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   void method3593() {
      classAAR var1 = this.method3588(false, (byte)-72);

      label191: {
         try {
            byte[] var2 = new byte[(int)var1.method251(-153393736)];
            int var3 = 0;

            while (var3 < var2.length) {
               int var4 = var1.method255(var2, var3, var2.length - var3, -1397369950);
               if (var4 == -1) {
                  throw new EOFException();
               }

               var3 += var4;
            }

            classXY var24 = new classXY(var2);
            if (var24.field6952.length - 702114061 * var24.field6953 < 1) {
               return;
            }

            int var5 = var24.method13095(-346779531);
            if (var5 >= 0 && var5 <= 3) {
               if (var5 >= 2) {
                  int var6 = var24.method13099(-921896574);
                  int var7 = 0;

                  while (true) {
                     if (var7 >= var6) {
                        break label191;
                     }

                     int var8 = var24.method13099(-1935196466);
                     int var9 = var24.method13095(-346779531);
                     classYY var10 = (classYY)classOF.method8436(classYY.method13822((byte)-11), var9, -1811551873);
                     Object var11;
                     if (classYY.field7112 == var10) {
                        var11 = classPD.method8877(var24, true, -193629541);
                     } else {
                        var11 = classYY.method13827(var10, var24, 827988912);
                     }

                     if (var8 >= 0 && var8 < this.field1615.length && this.field1615[var8]) {
                        this.field1614.put(var8, var11);
                     }

                     var7++;
                  }
               } else {
                  int var25 = var24.method13099(1824383334);

                  for (int var26 = 0; var26 < var25; var26++) {
                     int var28 = var24.method13099(-2002502651);
                     int var30 = classXY.method13113(var24, (byte)1);
                     if (var28 >= 0 && var28 < this.field1615.length && this.field1615[var28]) {
                        this.field1614.put(var28, var30);
                     }
                  }

                  int var27 = var24.method13099(-115128737);
                  int var29 = 0;

                  while (true) {
                     if (var29 >= var27) {
                        break label191;
                     }

                     var24.method13099(1624199738);
                     var24.method13127(2095879464);
                     var29++;
                  }
               }
            }
         } catch (Exception var22) {
            break label191;
         } finally {
            try {
               var1.method242((byte)1);
            } catch (Exception var21) {
            }
         }

         return;
      }

      this.field1613 = false;
   }

   @ObfuscatedSignature(descriptor = "(II)Ljava/lang/String;")
   @ObfuscatedName("as")
   String method3570(int var1, int var2) {
      try {
         Object var3 = this.field1614.get(var1);
         if (var3 instanceof String) {
            if (var2 == -682126121) {
               throw new IllegalStateException();
            } else {
               return (String)var3;
            }
         } else {
            return "";
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ds.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Lte;")
   @ObfuscatedName("ax")
   classTE method3574(int var1, byte var2) {
      try {
         Object var3 = this.field1614.get(var1);
         if (var3 instanceof classTE) {
            if (var2 >= 2) {
               throw new IllegalStateException();
            } else {
               return (classTE)var3;
            }
         } else {
            classYY var4 = FriendContainer.method10175(this.field1612[var1], 292847266);
            Object var5;
            switch (var4.field7105 * -900901303) {
               case 2:
                  var5 = classTM.method10983(this.field1612[var1], -973324355);
                  break;
               case 3:
                  var5 = (long)classTM.method10983(this.field1612[var1], -973324355);
                  break;
               case 4:
                  var5 = "";
                  break;
               default:
                  throw new RuntimeException();
            }

            classTE var6 = new classTE(var4, var5, 0, 16);
            this.field1614.put(var1, var6);
            return var6;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ds.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("aa")
   void method3581(byte var1) {
      try {
         for (int var2 = 0; var2 < this.field1615.length; var2++) {
            if (!this.field1615[var2]) {
               if (var1 != 126) {
                  throw new IllegalStateException();
               }

               this.field1614.remove(var2);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ds.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZB)Laar;")
   @ObfuscatedName("ao")
   classAAR method3588(boolean var1, byte var2) {
      try {
         return WallObject.method4636("2", classYP.field7026.field4051, var1, (byte)1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ds.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ay")
   void method3590(int var1) {
      try {
         classAAR var2 = this.method3588(true, (byte)-54);

         try {
            int var3 = 3;
            int var4 = 0;
            Iterator var5 = this.field1614.entrySet().iterator();

            while (var5.hasNext()) {
               if (var1 <= -359666248) {
                  throw new IllegalStateException();
               }

               Entry var6 = (Entry)var5.next();
               int var7 = (Integer)var6.getKey();
               if (this.field1615[var7]) {
                  if (var1 <= -359666248) {
                     throw new IllegalStateException();
                  }

                  Object var8 = var6.getValue();
                  var3 += 3;
                  if (var8 instanceof Integer) {
                     if (var1 <= -359666248) {
                        throw new IllegalStateException();
                     }

                     var3 += 4;
                  } else if (var8 instanceof Long) {
                     if (var1 <= -359666248) {
                        throw new IllegalStateException();
                     }

                     var3 += 8;
                  } else if (var8 instanceof String) {
                     var3 += classAU.method709((String)var8, (byte)87);
                  } else if (var8 instanceof classTE) {
                     var3 += classHK.method6101((classTE)var8, 65280);
                  }

                  var4++;
               }
            }

            classXY var23 = new classXY(var3);
            classXY.method13029(var23, 3, (byte)-101);
            var23.method13031(var4, -1542382992);
            Iterator var24 = this.field1614.entrySet().iterator();

            while (var24.hasNext()) {
               if (var1 <= -359666248) {
                  throw new IllegalStateException();
               }

               Entry var25 = (Entry)var24.next();
               int var26 = (Integer)var25.getKey();
               if (this.field1615[var26]) {
                  var23.method13031(var26, -1542382992);
                  Object var9 = var25.getValue();
                  classYY var10 = classYY.method13823(var9.getClass(), -1990439522);
                  classXY.method13029(var23, -701844459 * var10.field7106, (byte)-62);
                  classYY.method13824(var9, var23, (short)-361);
                  if (var9 instanceof classTE) {
                     ((classTE)var9).field6131 = false;
                  }
               }
            }

            var2.method238(var23.field6952, 0, 702114061 * var23.field6953, -1210097394);
         } catch (Exception var20) {
         } finally {
            try {
               var2.method242((byte)1);
            } catch (Exception var19) {
            }
         }

         this.field1613 = false;
         classDD.method2988(16711680);
      } catch (RuntimeException var22) {
         throw classEG.method3899(var22, "ds.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;I)Lte;")
   @ObfuscatedName("me")
   public static classTE method3575(classDS var0, int var1) {
      if (var0 == null) {
         var0.method3597();
      }

      Object var2 = var0.field1614.get(var1);
      if (var2 instanceof classTE) {
         return (classTE)var2;
      } else {
         classYY var3 = FriendContainer.method10175(var0.field1612[var1], -662921870);
         Object var4;
         switch (var3.field7105 * -900901303) {
            case 2:
               var4 = classTM.method10983(var0.field1612[var1], -973324355);
               break;
            case 3:
               var4 = (long)classTM.method10983(var0.field1612[var1], -973324355);
               break;
            case 4:
               var4 = "";
               break;
            default:
               throw new RuntimeException();
         }

         classTE var5 = new classTE(var3, var4, 0, 16);
         var0.field1614.put(var1, var5);
         return var5;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;)V")
   @ObfuscatedName("pr")
   public static void method3582(classDS var0) {
      if (var0 == null) {
         var0.method3592();
      } else {
         for (int var1 = 0; var1 < var0.field1615.length; var1++) {
            if (!var0.field1615[var1]) {
               var0.field1614.remove(var1);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;)Z")
   @ObfuscatedName("xx")
   public static boolean method3602(classDS var0) {
      if (var0.field1613) {
         return true;
      } else {
         for (Entry var2 : var0.field1614.entrySet()) {
            if (var2.getValue() instanceof classTE) {
               classTE var3 = (classTE)var2.getValue();
               if (var3.field6131) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lds;B)Z")
   @ObfuscatedName("sq")
   public static boolean method3603(classDS var0, byte var1) {
      if (var0 == null) {
         var0.method3604(var1);
      }

      try {
         if (var0.field1613) {
            if (var1 == 0) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            Iterator var2 = var0.field1614.entrySet().iterator();

            while (var2.hasNext()) {
               if (var1 == 0) {
                  throw new IllegalStateException();
               }

               Entry var3 = (Entry)var2.next();
               if (var3.getValue() instanceof classTE) {
                  if (var1 == 0) {
                     throw new IllegalStateException();
                  }

                  classTE var4 = (classTE)var3.getValue();
                  if (var4.field6131) {
                     if (var1 == 0) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }
            }

            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ds.aq(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;I)I")
   @ObfuscatedName("lh")
   public static int method3555(classDS var0, int var1) {
      Object var2 = var0.field1614.get(var1);
      return var2 instanceof Integer ? (Integer)var2 : -1;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("aj")
   void method3594(byte var1) {
      try {
         classAAR var2 = this.method3588(false, (byte)-26);

         label244: {
            try {
               byte[] var3 = new byte[(int)var2.method251(-153393736)];
               int var4 = 0;

               while (var4 < var3.length) {
                  if (var1 <= 1) {
                     throw new IllegalStateException();
                  }

                  int var5 = var2.method255(var3, var4, var3.length - var4, -1397369950);
                  if (var5 == -1) {
                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }

                     throw new EOFException();
                  }

                  var4 += var5;
               }

               classXY var27 = new classXY(var3);
               if (var27.field6952.length - 702114061 * var27.field6953 < 1) {
                  return;
               }

               int var6 = var27.method13095(-346779531);
               if (var6 >= 0 && var6 <= 3) {
                  if (var6 >= 2) {
                     int var7 = var27.method13099(709539780);
                     int var8 = 0;

                     while (true) {
                        if (var8 >= var7) {
                           break label244;
                        }

                        int var9 = var27.method13099(1601048125);
                        int var10 = var27.method13095(-346779531);
                        classYY var11 = (classYY)classOF.method8436(classYY.method13822((byte)-18), var10, -1046137310);
                        Object var12;
                        if (classYY.field7112 == var11) {
                           var12 = classPD.method8877(var27, true, -193629541);
                        } else {
                           var12 = classYY.method13827(var11, var27, 827988912);
                        }

                        if (var9 >= 0) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           if (var9 < this.field1615.length && this.field1615[var9]) {
                              if (var1 <= 1) {
                                 return;
                              }

                              this.field1614.put(var9, var12);
                           }
                        }

                        var8++;
                     }
                  } else {
                     int var28 = var27.method13099(251377840);

                     for (int var29 = 0; var29 < var28; var29++) {
                        if (var1 <= 1) {
                           throw new IllegalStateException();
                        }

                        int var31 = var27.method13099(-1939983567);
                        int var33 = classXY.method13113(var27, (byte)1);
                        if (var31 >= 0) {
                           if (var1 <= 1) {
                              return;
                           }

                           if (var31 < this.field1615.length && this.field1615[var31]) {
                              if (var1 <= 1) {
                                 return;
                              }

                              this.field1614.put(var31, var33);
                           }
                        }
                     }

                     int var30 = var27.method13099(31677533);
                     int var32 = 0;

                     while (true) {
                        if (var32 >= var30) {
                           break label244;
                        }

                        if (var1 <= 1) {
                           throw new IllegalStateException();
                        }

                        var27.method13099(-76674088);
                        var27.method13127(2082619160);
                        var32++;
                     }
                  }
               }
            } catch (Exception var24) {
               break label244;
            } finally {
               try {
                  var2.method242((byte)1);
               } catch (Exception var23) {
               }
            }

            return;
         }

         this.field1613 = false;
      } catch (RuntimeException var26) {
         throw classEG.method3899(var26, "ds.aj(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;)V")
   @ObfuscatedName("xo")
   public static void method3583(classDS var0) {
      if (var0 == null) {
         var0.method3584();
      } else {
         for (int var1 = 0; var1 < var0.field1615.length; var1++) {
            if (!var0.field1615[var1]) {
               var0.field1614.remove(var1);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   void method3599(int var1) {
      try {
         long var2 = classDD.method2988(16711680);
         if (-9088770474988100363L * this.field1619 < var2 - 10000L) {
            this.field1619 = 8207151046932948829L * var2;
            if (method3603(this, (byte)11)) {
               if (var1 != -1244339392) {
                  throw new IllegalStateException();
               }

               this.method3590(382679941);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ds.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ad")
   void method3550(int var1, int var2) {
      this.field1614.put(var1, var2);
      if (this.field1615[var1]) {
         this.field1613 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ap")
   void method3551(int var1, int var2) {
      this.field1614.put(var1, var2);
      if (this.field1615[var1]) {
         this.field1613 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("au")
   void method3552(int var1, int var2) {
      this.field1614.put(var1, var2);
      if (this.field1615[var1]) {
         this.field1613 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lte;")
   @ObfuscatedName("br")
   classTE method3576(int var1) {
      Object var2 = this.field1614.get(var1);
      if (var2 instanceof classTE) {
         return (classTE)var2;
      } else {
         classYY var3 = FriendContainer.method10175(this.field1612[var1], -395989592);
         Object var4;
         switch (var3.field7105 * -900901303) {
            case 2:
               var4 = classTM.method10983(this.field1612[var1], -973324355);
               break;
            case 3:
               var4 = (long)classTM.method10983(this.field1612[var1], -973324355);
               break;
            case 4:
               var4 = "";
               break;
            default:
               throw new RuntimeException();
         }

         classTE var5 = new classTE(var3, var4, 0, 16);
         this.field1614.put(var1, var5);
         return var5;
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)V")
   @ObfuscatedName("aw")
   void method3558(int var1, long var2) {
      this.field1614.put(var1, var2);
      if (this.field1615[var1]) {
         this.field1613 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("ak")
   long method3563(int var1) {
      Object var2 = this.field1614.get(var1);
      if (var2 instanceof Integer) {
         return ((Integer)var2).intValue();
      } else {
         return var2 instanceof Long ? (Long)var2 : -1L;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("at")
   long method3564(int var1) {
      Object var2 = this.field1614.get(var1);
      if (var2 instanceof Integer) {
         return ((Integer)var2).intValue();
      } else {
         return var2 instanceof Long ? (Long)var2 : -1L;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bj")
   void method3584() {
      for (int var1 = 0; var1 < this.field1615.length; var1++) {
         if (!this.field1615[var1]) {
            this.field1614.remove(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("av")
   long method3565(int var1) {
      Object var2 = this.field1614.get(var1);
      if (var2 instanceof Integer) {
         return ((Integer)var2).intValue();
      } else {
         return var2 instanceof Long ? (Long)var2 : -1L;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lds;III)V")
   @ObfuscatedName("iu")
   public static void method3553(classDS var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method3554(var1, var1, var1);
      }

      try {
         var0.field1614.put(var1, var2);
         if (var0.field1615[var1]) {
            if (var3 >= 684645754) {
               var0.method3607(var1, var2);
               return;
            }

            var0.field1613 = true;
         }

         var0.method3607(var1, var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ds.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lds;IJ)V")
   @ObfuscatedName("ss")
   public static void method3559(classDS var0, int var1, long var2) {
      if (var0 == null) {
         var0.method3560(var1, var2);
      } else {
         try {
            var0.field1614.put(var1, var2);
            if (var0.field1615[var1]) {
               var0.field1613 = true;
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "ds.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)V")
   @ObfuscatedName("bd")
   public void method3606(int var1, String var2) {
      classOE.field4841.getCallbacks().post(new VarClientStrChanged(var1));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;)V")
   @ObfuscatedName("fc")
   public static void method3600(classDS var0) {
      if (var0 == null) {
         var0.method3592();
      }

      long var1 = classDD.method2988(16711680);
      if (-9088770474988100363L * var0.field1619 < var1 - 10000L) {
         var0.field1619 = 8207151046932948829L * var1;
         if (method3603(var0, (byte)-25)) {
            var0.method3590(202221874);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("bx")
   String method3571(int var1) {
      Object var2 = this.field1614.get(var1);
      return var2 instanceof String ? (String)var2 : "";
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("bc")
   String method3572(int var1) {
      Object var2 = this.field1614.get(var1);
      return var2 instanceof String ? (String)var2 : "";
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Object;")
   @ObfuscatedName("bt")
   Object method3577(int var1) {
      return -1 != this.field1612[var1] ? this.method3574(var1, (byte)-80) : this.method3570(var1, -448458976);
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;)V")
   @ObfuscatedName("am")
   void method3567(int var1, Object var2) {
      this.field1614.put(var1, var2);
      if (this.field1615[var1]) {
         this.field1613 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lpx;")
   @ObfuscatedName("az")
   public static HealthBarConfig method3549(int var0, int var1) {
      try {
         HealthBarConfig var2 = (HealthBarConfig)NodeCache.method6460(HealthBarConfig.field5473, var0);
         if (null != var2) {
            if (var1 <= 1879621350) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classMN.field4543.method11942(33, var0, -1500169245);
            var2 = new HealthBarConfig(new classXY(var3), var0);
            HealthBarConfig.field5473.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ds.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;)V")
   @ObfuscatedName("le")
   public static void method3585(classDS var0) {
      if (var0 == null) {
         var0.method3595();
      }

      for (int var1 = 0; var1 < var0.field1615.length; var1++) {
         if (!var0.field1615[var1]) {
            var0.field1614.remove(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Object;")
   @ObfuscatedName("bm")
   Object method3578(int var1) {
      return -1 != this.field1612[var1] ? this.method3574(var1, (byte)-27) : this.method3570(var1, 2011665719);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   int method3556(int var1) {
      Object var2 = this.field1614.get(var1);
      return var2 instanceof Integer ? (Integer)var2 : -1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   void method3586() {
      for (int var1 = 0; var1 < this.field1615.length; var1++) {
         if (!this.field1615[var1]) {
            this.field1614.remove(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;)V")
   @ObfuscatedName("ah")
   void method3568(int var1, Object var2) {
      this.field1614.put(var1, var2);
      if (this.field1615[var1]) {
         this.field1613 = true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;ILjava/lang/Object;)V")
   @ObfuscatedName("hv")
   public static void method3569(classDS var0, int var1, Object var2) {
      var0.field1614.put(var1, var2);
      if (var0.field1615[var1]) {
         var0.field1613 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   void method3587() {
      for (int var1 = 0; var1 < this.field1615.length; var1++) {
         if (!this.field1615[var1]) {
            this.field1614.remove(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Laar;")
   @ObfuscatedName("bw")
   classAAR method3589(boolean var1) {
      return WallObject.method4636("2", classYP.field7026.field4051, var1, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bi")
   void method3591() {
      classAAR var1 = this.method3588(true, (byte)-44);

      try {
         int var2 = 3;
         int var3 = 0;

         for (Entry var5 : this.field1614.entrySet()) {
            int var6 = (Integer)var5.getKey();
            if (this.field1615[var6]) {
               Object var7 = var5.getValue();
               var2 += 3;
               if (var7 instanceof Integer) {
                  var2 += 4;
               } else if (var7 instanceof Long) {
                  var2 += 8;
               } else if (var7 instanceof String) {
                  var2 += classAU.method709((String)var7, (byte)108);
               } else if (var7 instanceof classTE) {
                  var2 += classHK.method6101((classTE)var7, 65280);
               }

               var3++;
            }
         }

         classXY var20 = new classXY(var2);
         classXY.method13029(var20, 3, (byte)-127);
         var20.method13031(var3, -1542382992);

         for (Entry var22 : this.field1614.entrySet()) {
            int var23 = (Integer)var22.getKey();
            if (this.field1615[var23]) {
               var20.method13031(var23, -1542382992);
               Object var8 = var22.getValue();
               classYY var9 = classYY.method13823(var8.getClass(), -1553239224);
               classXY.method13029(var20, -701844459 * var9.field7106, (byte)-63);
               classYY.method13824(var8, var20, (short)-24483);
               if (var8 instanceof classTE) {
                  ((classTE)var8).field6131 = false;
               }
            }
         }

         var1.method238(var20.field6952, 0, 702114061 * var20.field6953, -415335409);
      } catch (Exception var18) {
      } finally {
         try {
            var1.method242((byte)1);
         } catch (Exception var17) {
         }
      }

      this.field1613 = false;
      classDD.method2988(16711680);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("be")
   void method3592() {
      classAAR var1 = this.method3588(true, (byte)-54);

      try {
         int var2 = 3;
         int var3 = 0;

         for (Entry var5 : this.field1614.entrySet()) {
            int var6 = (Integer)var5.getKey();
            if (this.field1615[var6]) {
               Object var7 = var5.getValue();
               var2 += 3;
               if (var7 instanceof Integer) {
                  var2 += 4;
               } else if (var7 instanceof Long) {
                  var2 += 8;
               } else if (var7 instanceof String) {
                  var2 += classAU.method709((String)var7, (byte)79);
               } else if (var7 instanceof classTE) {
                  var2 += classHK.method6101((classTE)var7, 65280);
               }

               var3++;
            }
         }

         classXY var20 = new classXY(var2);
         classXY.method13029(var20, 3, (byte)-74);
         var20.method13031(var3, -1542382992);

         for (Entry var22 : this.field1614.entrySet()) {
            int var23 = (Integer)var22.getKey();
            if (this.field1615[var23]) {
               var20.method13031(var23, -1542382992);
               Object var8 = var22.getValue();
               classYY var9 = classYY.method13823(var8.getClass(), -1733408573);
               classXY.method13029(var20, -701844459 * var9.field7106, (byte)-110);
               classYY.method13824(var8, var20, (short)-9227);
               if (var8 instanceof classTE) {
                  ((classTE)var8).field6131 = false;
               }
            }
         }

         var1.method238(var20.field6952, 0, 702114061 * var20.field6953, -1162912707);
      } catch (Exception var18) {
      } finally {
         try {
            var1.method242((byte)1);
         } catch (Exception var17) {
         }
      }

      this.field1613 = false;
      classDD.method2988(16711680);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("by")
   void method3595() {
      classAAR var1 = this.method3588(false, (byte)-96);

      label191: {
         try {
            byte[] var2 = new byte[(int)var1.method251(-153393736)];
            int var3 = 0;

            while (var3 < var2.length) {
               int var4 = var1.method255(var2, var3, var2.length - var3, -1397369950);
               if (var4 == -1) {
                  throw new EOFException();
               }

               var3 += var4;
            }

            classXY var24 = new classXY(var2);
            if (var24.field6952.length - 702114061 * var24.field6953 < 1) {
               return;
            }

            int var5 = var24.method13095(-346779531);
            if (var5 >= 0 && var5 <= 3) {
               if (var5 >= 2) {
                  int var6 = var24.method13099(705725156);
                  int var7 = 0;

                  while (true) {
                     if (var7 >= var6) {
                        break label191;
                     }

                     int var8 = var24.method13099(360601598);
                     int var9 = var24.method13095(-346779531);
                     classYY var10 = (classYY)classOF.method8436(classYY.method13822((byte)-43), var9, 1191245413);
                     Object var11;
                     if (classYY.field7112 == var10) {
                        var11 = classPD.method8877(var24, true, -193629541);
                     } else {
                        var11 = classYY.method13827(var10, var24, 827988912);
                     }

                     if (var8 >= 0 && var8 < this.field1615.length && this.field1615[var8]) {
                        this.field1614.put(var8, var11);
                     }

                     var7++;
                  }
               } else {
                  int var25 = var24.method13099(-1815877627);

                  for (int var26 = 0; var26 < var25; var26++) {
                     int var28 = var24.method13099(1175830310);
                     int var30 = classXY.method13113(var24, (byte)1);
                     if (var28 >= 0 && var28 < this.field1615.length && this.field1615[var28]) {
                        this.field1614.put(var28, var30);
                     }
                  }

                  int var27 = var24.method13099(-697150764);
                  int var29 = 0;

                  while (true) {
                     if (var29 >= var27) {
                        break label191;
                     }

                     var24.method13099(1823330756);
                     var24.method13127(-588242542);
                     var29++;
                  }
               }
            }
         } catch (Exception var22) {
            break label191;
         } finally {
            try {
               var1.method242((byte)1);
            } catch (Exception var21) {
            }
         }

         return;
      }

      this.field1613 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;I)Ljava/lang/String;")
   @ObfuscatedName("pg")
   public static String method3573(classDS var0, int var1) {
      if (var0 == null) {
         var0.method3595();
      }

      Object var2 = var0.field1614.get(var1);
      return var2 instanceof String ? (String)var2 : "";
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("af")
   int method3557(int var1, int var2) {
      try {
         Object var3 = this.field1614.get(var1);
         if (var3 instanceof Integer) {
            if (var2 >= 2013526680) {
               throw new IllegalStateException();
            } else {
               return (Integer)var3;
            }
         } else {
            return -1;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ds.af(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lds;)V")
   @ObfuscatedName("bi")
   public static void method3596(classDS var0) {
      if (var0 == null) {
         var0.method3587();
      } else {
         classAAR var1 = var0.method3588(false, (byte)-24);

         label195: {
            try {
               byte[] var2 = new byte[(int)var1.method251(-153393736)];
               int var3 = 0;

               while (var3 < var2.length) {
                  int var4 = var1.method255(var2, var3, var2.length - var3, -1397369950);
                  if (var4 == -1) {
                     throw new EOFException();
                  }

                  var3 += var4;
               }

               classXY var24 = new classXY(var2);
               if (var24.field6952.length - 702114061 * var24.field6953 < 1) {
                  return;
               }

               int var5 = var24.method13095(-346779531);
               if (var5 >= 0 && var5 <= 3) {
                  if (var5 >= 2) {
                     int var6 = var24.method13099(-857791691);
                     int var7 = 0;

                     while (true) {
                        if (var7 >= var6) {
                           break label195;
                        }

                        int var8 = var24.method13099(321093046);
                        int var9 = var24.method13095(-346779531);
                        classYY var10 = (classYY)classOF.method8436(classYY.method13822((byte)77), var9, 1623504784);
                        Object var11;
                        if (classYY.field7112 == var10) {
                           var11 = classPD.method8877(var24, true, -193629541);
                        } else {
                           var11 = classYY.method13827(var10, var24, 827988912);
                        }

                        if (var8 >= 0 && var8 < var0.field1615.length && var0.field1615[var8]) {
                           var0.field1614.put(var8, var11);
                        }

                        var7++;
                     }
                  } else {
                     int var25 = var24.method13099(559891422);

                     for (int var26 = 0; var26 < var25; var26++) {
                        int var28 = var24.method13099(755005911);
                        int var30 = classXY.method13113(var24, (byte)1);
                        if (var28 >= 0 && var28 < var0.field1615.length && var0.field1615[var28]) {
                           var0.field1614.put(var28, var30);
                        }
                     }

                     int var27 = var24.method13099(-546978296);
                     int var29 = 0;

                     while (true) {
                        if (var29 >= var27) {
                           break label195;
                        }

                        var24.method13099(-1939529292);
                        var24.method13127(-994293465);
                        var29++;
                     }
                  }
               }
            } catch (Exception var22) {
               break label195;
            } finally {
               try {
                  var1.method242((byte)1);
               } catch (Exception var21) {
               }
            }

            return;
         }

         var0.field1613 = false;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   void method3597() {
      classAAR var1 = this.method3588(false, (byte)-55);

      label191: {
         try {
            byte[] var2 = new byte[(int)var1.method251(-153393736)];
            int var3 = 0;

            while (var3 < var2.length) {
               int var4 = var1.method255(var2, var3, var2.length - var3, -1397369950);
               if (var4 == -1) {
                  throw new EOFException();
               }

               var3 += var4;
            }

            classXY var24 = new classXY(var2);
            if (var24.field6952.length - 702114061 * var24.field6953 < 1) {
               return;
            }

            int var5 = var24.method13095(-346779531);
            if (var5 >= 0 && var5 <= 3) {
               if (var5 >= 2) {
                  int var6 = var24.method13099(-871817624);
                  int var7 = 0;

                  while (true) {
                     if (var7 >= var6) {
                        break label191;
                     }

                     int var8 = var24.method13099(1575431533);
                     int var9 = var24.method13095(-346779531);
                     classYY var10 = (classYY)classOF.method8436(classYY.method13822((byte)-31), var9, -1047088468);
                     Object var11;
                     if (classYY.field7112 == var10) {
                        var11 = classPD.method8877(var24, true, -193629541);
                     } else {
                        var11 = classYY.method13827(var10, var24, 827988912);
                     }

                     if (var8 >= 0 && var8 < this.field1615.length && this.field1615[var8]) {
                        this.field1614.put(var8, var11);
                     }

                     var7++;
                  }
               } else {
                  int var25 = var24.method13099(525072211);

                  for (int var26 = 0; var26 < var25; var26++) {
                     int var28 = var24.method13099(630193178);
                     int var30 = classXY.method13113(var24, (byte)1);
                     if (var28 >= 0 && var28 < this.field1615.length && this.field1615[var28]) {
                        this.field1614.put(var28, var30);
                     }
                  }

                  int var27 = var24.method13099(931243160);
                  int var29 = 0;

                  while (true) {
                     if (var29 >= var27) {
                        break label191;
                     }

                     var24.method13099(-2004457063);
                     var24.method13127(211386720);
                     var29++;
                  }
               }
            }
         } catch (Exception var22) {
            break label191;
         } finally {
            try {
               var1.method242((byte)1);
            } catch (Exception var21) {
            }
         }

         return;
      }

      this.field1613 = false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   void method3598() {
      classAAR var1 = this.method3588(false, (byte)-80);

      label191: {
         try {
            byte[] var2 = new byte[(int)var1.method251(-153393736)];
            int var3 = 0;

            while (var3 < var2.length) {
               int var4 = var1.method255(var2, var3, var2.length - var3, -1397369950);
               if (var4 == -1) {
                  throw new EOFException();
               }

               var3 += var4;
            }

            classXY var24 = new classXY(var2);
            if (var24.field6952.length - 103860650 * var24.field6953 < 1) {
               return;
            }

            int var5 = var24.method13095(-346779531);
            if (var5 >= 0 && var5 <= 3) {
               if (var5 >= 2) {
                  int var6 = var24.method13099(-684426503);
                  int var7 = 0;

                  while (true) {
                     if (var7 >= var6) {
                        break label191;
                     }

                     int var8 = var24.method13099(-1093264748);
                     int var9 = var24.method13095(-346779531);
                     classYY var10 = (classYY)classOF.method8436(classYY.method13822((byte)-68), var9, -1881248535);
                     Object var11;
                     if (classYY.field7112 == var10) {
                        var11 = classPD.method8877(var24, true, -193629541);
                     } else {
                        var11 = classYY.method13827(var10, var24, 827988912);
                     }

                     if (var8 >= 0 && var8 < this.field1615.length && this.field1615[var8]) {
                        this.field1614.put(var8, var11);
                     }

                     var7++;
                  }
               } else {
                  int var25 = var24.method13099(919758609);

                  for (int var26 = 0; var26 < var25; var26++) {
                     int var28 = var24.method13099(-629484188);
                     int var30 = classXY.method13113(var24, (byte)1);
                     if (var28 >= 0 && var28 < this.field1615.length && this.field1615[var28]) {
                        this.field1614.put(var28, var30);
                     }
                  }

                  int var27 = var24.method13099(-2142870949);
                  int var29 = 0;

                  while (true) {
                     if (var29 >= var27) {
                        break label191;
                     }

                     var24.method13099(-1832154991);
                     var24.method13127(748053759);
                     var29++;
                  }
               }
            }
         } catch (Exception var22) {
            break label191;
         } finally {
            try {
               var1.method242((byte)1);
            } catch (Exception var21) {
            }
         }

         return;
      }

      this.field1613 = false;
   }

   classDS() {
      int var1 = classBF.field427.method11943(19, -647217945);
      this.field1614 = new HashMap();
      this.field1615 = new boolean[var1];
      this.field1612 = new int[var1];

      for (int var2 = 0; var2 < var1; var2++) {
         classPA var3 = classXN.method12910(var2, 1704034469);
         this.field1615[var2] = var3.field5203;
         this.field1612[var2] = -1406890073 * var3.field5202;
      }

      this.method3594((byte)23);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Map;")
   @ObfuscatedName("er")
   public Map method3608() {
      return this.field1614;
   }

   @ObfuscatedSignature(descriptor = "(Lds;IB)Ljava/lang/Object;")
   @ObfuscatedName("qy")
   public static Object method3579(classDS var0, int var1, byte var2) {
      if (var0 == null) {
         return var0.method3580(var1, var2);
      } else {
         try {
            Object var10000;
            if (-1 != var0.field1612[var1]) {
               if (var2 <= 7) {
                  throw new IllegalStateException();
               }

               var10000 = var0.method3574(var1, (byte)-3);
            } else {
               var10000 = var0.method3570(var1, 368741272);
            }

            return var10000;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "ds.ac(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bv")
   void method3601() {
      long var1 = classDD.method2988(16711680);
      if (-9088770474988100363L * this.field1619 < var1 - 10000L) {
         this.field1619 = 8207151046932948829L * var1;
         if (method3603(this, (byte)38)) {
            this.method3590(1102848362);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("dp")
   static final void method3605(byte var0) {
      try {
         Iterator var1 = client.field815.iterator();

         while (var1.hasNext()) {
            if (var0 <= 0) {
               throw new IllegalStateException();
            }

            WorldView var2 = (WorldView)var1.next();

            for (classCD var3 = (classCD)var2.field1688.method7957(); var3 != null; var3 = (classCD)var2.field1688.method7965()) {
               if (var0 <= 0) {
                  throw new IllegalStateException();
               }

               if (var3.field672 * -356773341 > 0) {
                  if (var0 <= 0) {
                     throw new IllegalStateException();
                  }

                  var3.field672 -= -22663797;
               }

               if (0 == var3.field672 * -356773341) {
                  if (var0 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (-1856029911 * var3.field685 >= 0) {
                     if (var0 <= 0) {
                        return;
                     }

                     int var5 = var3.field685 * -1856029911;
                     int var6 = 2060213209 * var3.field677;
                     ObjectComposition var7 = classMU.method7771(var5, 1916036814);
                     if (11 == var6) {
                        if (var0 <= 0) {
                           throw new IllegalStateException();
                        }

                        var6 = 10;
                     }

                     if (var6 >= 5) {
                        if (var0 <= 0) {
                           throw new IllegalStateException();
                        }

                        if (var6 <= 8) {
                           if (var0 <= 0) {
                              throw new IllegalStateException();
                           }

                           var6 = 4;
                        }
                     }

                     boolean var4 = ObjectComposition.method8562(var7, var6, -656313384);
                     if (!var4) {
                        if (var0 <= 0) {
                           throw new IllegalStateException();
                        }
                        continue;
                     }
                  }

                  classMI.method7635(
                     var2,
                     -1436432311 * var3.field679,
                     2125153617 * var3.field678,
                     var3.field676 * -1779660971,
                     -522576623 * var3.field680,
                     -1856029911 * var3.field685,
                     -1640283079 * var3.field686,
                     var3.field677 * 2060213209,
                     var3.field682 * 1072982787,
                     (byte)2
                  );
                  var3.vmethod398();
               } else {
                  if (-710753241 * var3.field673 > 0) {
                     if (var0 <= 0) {
                        throw new IllegalStateException();
                     }

                     var3.field673 -= 1512991639;
                  }

                  if (var3.field673 * -710753241 == 0) {
                     if (var0 <= 0) {
                        throw new IllegalStateException();
                     }

                     if (-1779660971 * var3.field676 >= 1) {
                        if (var0 <= 0) {
                           return;
                        }

                        if (-522576623 * var3.field680 >= 1) {
                           if (var0 <= 0) {
                              throw new IllegalStateException();
                           }

                           if (var3.field676 * -1779660971 <= 102) {
                              if (var0 <= 0) {
                                 throw new IllegalStateException();
                              }

                              if (var3.field680 * -522576623 <= 102) {
                                 if (var0 <= 0) {
                                    throw new IllegalStateException();
                                 }

                                 if (var3.field675 * 1490231037 >= 0) {
                                    if (var0 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    int var10 = 1490231037 * var3.field675;
                                    int var11 = 1299658649 * var3.field683;
                                    ObjectComposition var12 = classMU.method7771(var10, 1807366534);
                                    if (var11 == 11) {
                                       if (var0 <= 0) {
                                          throw new IllegalStateException();
                                       }

                                       var11 = 10;
                                    }

                                    if (var11 >= 5) {
                                       if (var0 <= 0) {
                                          throw new IllegalStateException();
                                       }

                                       if (var11 <= 8) {
                                          if (var0 <= 0) {
                                             return;
                                          }

                                          var11 = 4;
                                       }
                                    }

                                    boolean var9 = ObjectComposition.method8562(var12, var11, -656313384);
                                    if (!var9) {
                                       if (var0 <= 0) {
                                          throw new IllegalStateException();
                                       }
                                       continue;
                                    }
                                 }

                                 classMI.method7635(
                                    var2,
                                    -1436432311 * var3.field679,
                                    var3.field678 * 2125153617,
                                    var3.field676 * -1779660971,
                                    -522576623 * var3.field680,
                                    var3.field675 * 1490231037,
                                    var3.field684 * 332453363,
                                    var3.field683 * 1299658649,
                                    1072982787 * var3.field682,
                                    (byte)2
                                 );
                                 var3.field673 = -1512991639;
                                 if (-1856029911 * var3.field685 == 1490231037 * var3.field675 && var3.field685 * -1856029911 == -1) {
                                    if (var0 <= 0) {
                                       return;
                                    }

                                    var3.vmethod398();
                                 } else if (var3.field675 * 1490231037 == -1856029911 * var3.field685
                                    && 332453363 * var3.field684 == var3.field686 * -1640283079) {
                                    if (var0 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    if (var3.field683 * 1299658649 == 2060213209 * var3.field677) {
                                       var3.vmethod398();
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ds.dp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("af")
   static void method3561(byte var0) {
      try {
         classDI.field1517 = (short[][][])null;
         classCO.field1180 = (short[][][])null;
         classBY.field619 = (byte[][][])null;
         classDI.field1516 = (byte[][][])null;
         classDE.field1408 = (int[][][])null;
         WallObject.field2248 = (byte[][][])null;
         classDI.field1515 = (int[][])null;
         classAB.field126 = null;
         classCR.field1227 = null;
         client.field1146 = null;
         GraphicsObject.field476 = null;
         classIC.field2939 = null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ds.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Ljava/lang/Object;")
   @ObfuscatedName("ac")
   Object method3580(int var1, byte var2) {
      try {
         Object var10000;
         if (-1 != this.field1612[var1]) {
            if (var2 <= 7) {
               throw new IllegalStateException();
            }

            var10000 = this.method3574(var1, (byte)-3);
         } else {
            var10000 = this.method3570(var1, 368741272);
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ds.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("aq")
   boolean method3604(byte var1) {
      try {
         if (this.field1613) {
            if (var1 == 0) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            Iterator var2 = this.field1614.entrySet().iterator();

            while (var2.hasNext()) {
               if (var1 == 0) {
                  throw new IllegalStateException();
               }

               Entry var3 = (Entry)var2.next();
               if (var3.getValue() instanceof classTE) {
                  if (var1 == 0) {
                     throw new IllegalStateException();
                  }

                  classTE var4 = (classTE)var3.getValue();
                  if (var4.field6131) {
                     if (var1 == 0) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }
            }

            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ds.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)V")
   @ObfuscatedName("ae")
   void method3560(int var1, long var2) {
      try {
         this.field1614.put(var1, var2);
         if (this.field1615[var1]) {
            this.field1613 = true;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ds.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("nq")
   public void method3607(int var1, int var2) {
      classOE.field4841.getCallbacks().post(new VarClientIntChanged(var1));
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("az")
   void method3554(int var1, int var2, int var3) {
      try {
         this.field1614.put(var1, var2);
         if (this.field1615[var1]) {
            if (var3 >= 684645754) {
               this.method3607(var1, var2);
               return;
            }

            this.field1613 = true;
         }

         this.method3607(var1, var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ds.az(" + ')');
      }
   }
}
