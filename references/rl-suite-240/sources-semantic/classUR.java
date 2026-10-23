import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ur")
public abstract class classUR implements classTY {
   @ObfuscatedSignature(descriptor = "Lyy;")
   @ObfuscatedName("az")
   classYY field6371;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6370 = 2;
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("dz")
   static IndexedSprite field6372;

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ae")
   abstract void vmethod581(classXY var1, int var2);

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ab")
   public void method11572(classXY var1, int var2) {
      try {
         while (true) {
            int var3 = var1.method13095(-346779531);
            if (var3 == 0) {
               return;
            }

            classUO var4 = (classUO)classOF.method8436(classRX.method10386(-1951213695), var3, 950555050);
            if (var4 != null) {
               if (var2 != -1830946202) {
                  throw new IllegalStateException();
               }

               switch (1829787843 * var4.field6354) {
                  case 0:
                     classKI[] var5 = new classKI[]{classKI.field3591, classKI.field3590, classKI.field3589, classKI.field3588};
                     classOF.method8436(var5, var1.method13095(-346779531), 522790870);
                     break;
                  case 1:
                     var1.method13134(37223165);
                     break;
                  case 2:
                  default:
                     throw new IllegalStateException("Unrecognised VarTypeEncodingKey - " + var4);
                  case 3:
                     int var6 = var1.method13095(-346779531);
                     this.field6371 = FriendContainer.method10175(var6, 675005370);
                     if (null == this.field6371) {
                        if (var2 != -1830946202) {
                           throw new IllegalStateException();
                        }

                        throw new IllegalStateException("Unknown ScriptVarType ID in VarType.decode: " + var6);
                     }
               }
            } else {
               this.vmethod580(var1, var3, (byte)-66);
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ur.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IB)V")
   @ObfuscatedName("az")
   abstract void vmethod580(classXY var1, int var2, byte var3);

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ao")
   public void method11573(classXY var1) {
      while (true) {
         int var2 = var1.method13095(-346779531);
         if (var2 == 0) {
            return;
         }

         classUO var3 = (classUO)classOF.method8436(classRX.method10386(-1738877096), var2, -370247037);
         if (var3 != null) {
            switch (1829787843 * var3.field6354) {
               case 0:
                  classKI[] var4 = new classKI[]{classKI.field3591, classKI.field3590, classKI.field3589, classKI.field3588};
                  classOF.method8436(var4, var1.method13095(-346779531), -1379317163);
                  break;
               case 1:
                  var1.method13134(-1736994784);
                  break;
               case 2:
               default:
                  throw new IllegalStateException("Unrecognised VarTypeEncodingKey - " + var3);
               case 3:
                  int var5 = var1.method13095(-346779531);
                  this.field6371 = FriendContainer.method10175(var5, 1024371840);
                  if (null == this.field6371) {
                     throw new IllegalStateException("Unknown ScriptVarType ID in VarType.decode: " + var5);
                  }
            }
         } else {
            this.vmethod580(var1, var2, (byte)-5);
         }
      }
   }

   classUR(int var1) {
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("af")
   abstract void vmethod579(classXY var1, int var2);

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ac")
   public void method11574(classXY var1) {
      while (true) {
         int var2 = var1.method13095(-346779531);
         if (var2 == 0) {
            return;
         }

         classUO var3 = (classUO)classOF.method8436(classRX.method10386(-877397248), var2, 2101096005);
         if (var3 != null) {
            switch (1829787843 * var3.field6354) {
               case 0:
                  classKI[] var4 = new classKI[]{classKI.field3591, classKI.field3590, classKI.field3589, classKI.field3588};
                  classOF.method8436(var4, var1.method13095(-346779531), 1288041846);
                  break;
               case 1:
                  var1.method13134(1621200149);
                  break;
               case 2:
               default:
                  throw new IllegalStateException("Unrecognised VarTypeEncodingKey - " + var3);
               case 3:
                  int var5 = var1.method13095(-346779531);
                  this.field6371 = FriendContainer.method10175(var5, 1547033116);
                  if (null == this.field6371) {
                     throw new IllegalStateException("Unknown ScriptVarType ID in VarType.decode: " + var5);
                  }
            }
         } else {
            this.vmethod580(var1, var2, (byte)-78);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("bn")
   static void method11585(byte var0) {
      try {
         if (classAB.field122.method2729((byte)41)) {
            if (var0 != 0) {
               throw new IllegalStateException();
            } else {
               classBK.method1122(0, 0, (byte)76);
            }
         } else {
            classPD.method8880("scape main", 255, (byte)32);
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ur.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("al")
   public void method11575(classXY var1) {
      while (true) {
         int var2 = var1.method13095(-346779531);
         if (var2 == 0) {
            return;
         }

         classUO var3 = (classUO)classOF.method8436(classRX.method10386(-1676692372), var2, 1964774401);
         if (var3 != null) {
            switch (-699991708 * var3.field6354) {
               case 0:
                  classKI[] var4 = new classKI[]{classKI.field3591, classKI.field3590, classKI.field3589, classKI.field3588};
                  classOF.method8436(var4, var1.method13095(-346779531), 762853606);
                  break;
               case 1:
                  var1.method13134(-1439005862);
                  break;
               case 2:
               default:
                  throw new IllegalStateException("Unrecognised VarTypeEncodingKey - " + var3);
               case 3:
                  int var5 = var1.method13095(-346779531);
                  this.field6371 = FriendContainer.method10175(var5, -410694638);
                  if (null == this.field6371) {
                     throw new IllegalStateException("Unknown ScriptVarType ID in VarType.decode: " + var5);
                  }
            }
         } else {
            this.vmethod580(var1, var2, (byte)-94);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("aa")
   public void method11576(classXY var1) {
      while (true) {
         int var2 = var1.method13095(-346779531);
         if (var2 == 0) {
            return;
         }

         classUO var3 = (classUO)classOF.method8436(classRX.method10386(-1685482280), var2, 835197488);
         if (var3 != null) {
            switch (1829787843 * var3.field6354) {
               case 0:
                  classKI[] var4 = new classKI[]{classKI.field3591, classKI.field3590, classKI.field3589, classKI.field3588};
                  classOF.method8436(var4, var1.method13095(-346779531), -1740964773);
                  break;
               case 1:
                  var1.method13134(-1372018119);
                  break;
               case 2:
               default:
                  throw new IllegalStateException("Unrecognised VarTypeEncodingKey - " + var3);
               case 3:
                  int var5 = var1.method13095(-346779531);
                  this.field6371 = FriendContainer.method10175(var5, 608112257);
                  if (null == this.field6371) {
                     throw new IllegalStateException("Unknown ScriptVarType ID in VarType.decode: " + var5);
                  }
            }
         } else {
            this.vmethod580(var1, var2, (byte)-67);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ax")
   public void method11577(classXY var1) {
      while (true) {
         int var2 = var1.method13095(-346779531);
         if (var2 == 0) {
            return;
         }

         classUO var3 = (classUO)classOF.method8436(classRX.method10386(-1075593783), var2, 1129880159);
         if (var3 != null) {
            switch (1829787843 * var3.field6354) {
               case 0:
                  classKI[] var4 = new classKI[]{classKI.field3591, classKI.field3590, classKI.field3589, classKI.field3588};
                  classOF.method8436(var4, var1.method13095(-346779531), -1195889883);
                  break;
               case 1:
                  var1.method13134(1292242393);
                  break;
               case 2:
               default:
                  throw new IllegalStateException("Unrecognised VarTypeEncodingKey - " + var3);
               case 3:
                  int var5 = var1.method13095(-346779531);
                  this.field6371 = FriendContainer.method10175(var5, -532509648);
                  if (null == this.field6371) {
                     throw new IllegalStateException("Unknown ScriptVarType ID in VarType.decode: " + var5);
                  }
            }
         } else {
            this.vmethod580(var1, var2, (byte)-55);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lur;I)Ljava/lang/Object;")
   @ObfuscatedName("yb")
   public static Object method11582(classUR var0, int var1) {
      if (var0 == null) {
         return var0.method11584(var1);
      } else {
         try {
            if (var0.field6371 == classYY.field7109) {
               if (var1 <= 1700637583) {
                  throw new IllegalStateException();
               } else {
                  return 0;
               }
            } else if (var0.field6371 == classYY.field7113) {
               if (var1 <= 1700637583) {
                  throw new IllegalStateException();
               } else {
                  return -1L;
               }
            } else if (classYY.field7111 == var0.field6371) {
               if (var1 <= 1700637583) {
                  throw new IllegalStateException();
               } else {
                  return "";
               }
            } else {
               return null;
            }
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "ur.as(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aj")
   boolean method11578() {
      return this.field6371 != null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ay")
   boolean method11579() {
      return this.field6371 != null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lur;)Ljava/lang/Object;")
   @ObfuscatedName("ye")
   public static Object method11583(classUR var0) {
      if (var0.field6371 == classYY.field7109) {
         return 0;
      } else if (var0.field6371 == classYY.field7113) {
         return -1L;
      } else {
         return classYY.field7111 == var0.field6371 ? "" : null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ag")
   boolean method11580(int var1) {
      try {
         return this.field6371 != null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ur.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lur;I)Z")
   @ObfuscatedName("wf")
   public static boolean method11581(classUR var0, int var1) {
      if (var0 == null) {
         return var0.method11580(var1);
      } else {
         try {
            return var0.field6371 != null;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "ur.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Object;")
   @ObfuscatedName("as")
   Object method11584(int var1) {
      try {
         if (this.field6371 == classYY.field7112) {
            if (var1 <= 1700637583) {
               throw new IllegalStateException();
            } else {
               return 0;
            }
         } else if (this.field6371 == classYY.field7113) {
            if (var1 <= 1700637583) {
               throw new IllegalStateException();
            } else {
               return -1L;
            }
         } else if (classYY.field7109 == this.field6371) {
            if (var1 <= 1700637583) {
               throw new IllegalStateException();
            } else {
               return "";
            }
         } else {
            return null;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ur.as(" + ')');
      }
   }
}
