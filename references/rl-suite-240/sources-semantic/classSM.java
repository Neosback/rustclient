import net.runelite.api.events.AreaSoundEffectPlayed;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sm")
public class classSM {
   @ObfuscatedName("af")
   int field5974;
   @ObfuscatedSignature(descriptor = "Lso;")
   @ObfuscatedName("ab")
   final classSO field5977;
   @ObfuscatedSignature(descriptor = "[Lsb;")
   @ObfuscatedName("ae")
   final classSB[] field5975;
   @ObfuscatedName("sa")
   public int field5979;
   @ToRemove(unused = "true")
   @ObfuscatedName("bd")
   static final int field5978 = 1001;
   @ObfuscatedName("az")
   final int field5976 = 50;

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ax")
   public boolean method10543(int var1) {
      try {
         return 2086456713 * this.field5974 >= 50;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sm.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)V")
   @ObfuscatedName("az")
   public void method10517(int var1, int var2, int var3, byte var4) {
      try {
         if (this.field5977.vmethod393(826200091) != 0) {
            if (var4 != 0) {
               throw new IllegalStateException();
            }

            if (var2 != 0) {
               if (var4 != 0) {
                  throw new IllegalStateException();
               }

               this.method10527(0, var1, 0, 0, 0, 0, var2, var3, false, 158705590);
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "sm.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsm;I)I")
   @ObfuscatedName("qa")
   public static int method10539(classSM var0, int var1) {
      if (var0 == null) {
         return var0.method10542(var1);
      } else {
         try {
            return 2086456713 * var0.field5974;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "sm.as(" + 41);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsm;IIIIIIII)V")
   @ObfuscatedName("mj")
   public static void method10522(classSM var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (var0.field5977.vmethod388(-1252480360) != 0 && var7 > 0) {
         var0.method10527(var1, var2, var3, var4, var5, var6, var7, var8, false, -518799957);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIII)V")
   @ObfuscatedName("af")
   public void method10523(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      try {
         if (this.field5977.vmethod388(-1543227081) != 0) {
            if (var9 >= 16711680) {
               throw new IllegalStateException();
            }

            if (var7 > 0) {
               if (var9 >= 16711680) {
                  throw new IllegalStateException();
               }

               this.method10527(var1, var2, var3, var4, var5, var6, var7, var8, false, 1849062476);
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "sm.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIZI)V")
   @ObfuscatedName("ae")
   public void method10527(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, int var10) {
      this.method10548(var1, var2, var3, var4, var5, var6, var7, var8, var9);

      try {
         if (this.field5974 * 2086456713 >= 50) {
            if (var10 == 1830375960) {
               throw new IllegalStateException();
            } else {
               this.method10549(var1, var2, var3, var4, var5, var6, var7, var8, var9);
            }
         } else {
            label56: {
               if (var5 > 0) {
                  if (var10 == 1830375960) {
                     throw new IllegalStateException();
                  }

                  if (this.field5977.vmethod388(-1993123292) == 0) {
                     break label56;
                  }

                  if (var10 == 1830375960) {
                     throw new IllegalStateException();
                  }
               }

               if (var5 == 0) {
                  if (var10 == 1830375960) {
                     this.method10549(var1, var2, var3, var4, var5, var6, var7, var8, var9);
                     return;
                  }

                  if (this.field5977.vmethod393(826200091) == 0) {
                     if (var10 == 1830375960) {
                        this.method10549(var1, var2, var3, var4, var5, var6, var7, var8, var9);
                        return;
                     }
                     break label56;
                  }
               }

               this.field5975[this.field5974 * 2086456713].field5900 = var1 * 322732777;
               this.field5975[2086456713 * this.field5974].field5902 = null;
               this.field5975[this.field5974 * 2086456713].field5901 = -643204909 * ((var3 << 16) + (var4 << 8) + var5);
               this.field5975[2086456713 * this.field5974].field5897 = -1575487195 * var2;
               this.field5975[2086456713 * this.field5974].field5899 = -1206170819 * var7;
               this.field5975[2086456713 * this.field5974].field5898 = -509408507 * var8;
               this.field5975[this.field5974 * 2086456713].field5896 = var6 * -1796103743;
               this.field5975[this.field5974 * 2086456713].field5903 = var9;
               this.field5974 += 55425209;
               this.method10549(var1, var2, var3, var4, var5, var6, var7, var8, var9);
               return;
            }

            this.method10549(var1, var2, var3, var4, var5, var6, var7, var8, var9);
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "sm.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIZ)V")
   @ObfuscatedName("aw")
   public void method10548(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9) {
      this.field5979 = this.field5974 * 2086456713;
   }

   @ObfuscatedSignature(descriptor = "(II)Lsb;")
   @ObfuscatedName("ac")
   public classSB method10546(int var1, int var2) {
      try {
         return this.field5975[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sm.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("aa")
   public void method10518(int var1, int var2, int var3) {
      if (this.field5977.vmethod393(826200091) != 0 && var2 != 0) {
         this.method10527(0, var1, 0, 0, 0, 0, var2, var3, false, -1570602466);
      }
   }

   public classSM(classSO var1) {
      this.field5974 = 0;
      this.field5975 = new classSB[50];
      this.field5977 = var1;

      for (int var2 = 0; var2 < 50; var2++) {
         this.field5975[var2] = new classSB();
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bn")
   public boolean method10544() {
      return 2086456713 * this.field5974 >= -1411936261;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("aj")
   public void method10519(int var1, int var2, int var3) {
      if (this.field5977.vmethod393(826200091) != 0 && var2 != 0) {
         this.method10527(0, var1, 0, 0, 0, 0, var2, var3, false, 2029973896);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laav;I)B")
   @ObfuscatedName("zp")
   public static byte method10532(classAAV var0, int var1) {
      if (var1 < var0.field105) {
         return var0.field108;
      } else if (var1 >= var0.field104) {
         var0.field108 = var0.field106;
         return var0.field108;
      } else {
         int var2 = var0.field107 & -159438526;
         int var3 = var0.field106 & 1554860076;
         float var4 = (float)(var1 - var0.field105) / (var0.field104 - var0.field105);
         var0.field108 = (byte)classLU.method7197(var2, var3, var4, 120041229);
         return var0.field108;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("ay")
   public void method10524(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (this.field5977.vmethod388(-793885386) != 0 && var7 > 0) {
         this.method10527(var1, var2, var3, var4, var5, var6, var7, var8, false, -234363442);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("aq")
   public void method10525(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (this.field5977.vmethod388(-1238654290) != 0 && var7 > 0) {
         this.method10527(var1, var2, var3, var4, var5, var6, var7, var8, false, -1037756107);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsm;IIIIIIIIZ)V")
   @ObfuscatedName("dt")
   public static void method10528(classSM var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9) {
      if (var0.field5974 * 1678676266 < 99016510) {
         if ((var5 <= 0 || var0.field5977.vmethod388(-2066635095) != 0) && (var5 != 0 || var0.field5977.vmethod393(826200091) != 0)) {
            var0.field5975[var0.field5974 * -416038650].field5900 = var1 * 322732777;
            var0.field5975[-7635991 * var0.field5974].field5902 = null;
            var0.field5975[var0.field5974 * -757781128].field5901 = 1732440181 * ((var3 << 16) + (var4 << 8) + var5);
            var0.field5975[2086456713 * var0.field5974].field5897 = 1301054265 * var2;
            var0.field5975[2086456713 * var0.field5974].field5899 = -1569785761 * var7;
            var0.field5975[776328298 * var0.field5974].field5898 = -1601054310 * var8;
            var0.field5975[var0.field5974 * 471411417].field5896 = var6 * -239393433;
            var0.field5975[var0.field5974 * 1354149762].field5903 = var9;
            var0.field5974 += 55425209;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("al")
   public void method10520(int var1, int var2, int var3) {
      if (this.field5977.vmethod393(826200091) != 0 && var2 != 0) {
         this.method10527(0, var1, 0, 0, 0, 0, var2, var3, false, 2090846689);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIZ)V")
   @ObfuscatedName("rv")
   public void method10529(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9) {
      if (this.field5974 * 2086456713 < 50) {
         if ((var5 <= 0 || this.field5977.vmethod388(-861667635) != 0) && (var5 != 0 || this.field5977.vmethod393(826200091) != 0)) {
            this.field5975[this.field5974 * 2086456713].field5900 = var1 * 322732777;
            this.field5975[2086456713 * this.field5974].field5902 = null;
            this.field5975[this.field5974 * 2086456713].field5901 = -643204909 * ((var3 << 16) + (var4 << 8) + var5);
            this.field5975[2086456713 * this.field5974].field5897 = -1575487195 * var2;
            this.field5975[2086456713 * this.field5974].field5899 = -1206170819 * var7;
            this.field5975[2086456713 * this.field5974].field5898 = -509408507 * var8;
            this.field5975[this.field5974 * 2086456713].field5896 = var6 * -1796103743;
            this.field5975[this.field5974 * 2086456713].field5903 = var9;
            this.field5974 += 55425209;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIZ)V")
   @ObfuscatedName("lo")
   public void method10549(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9) {
      if (this.field5979 + 1 == this.field5974 * 2086456713) {
         boolean var10;
         if (var3 == 0 && var4 == 0 && var5 == 0) {
            SoundEffectPlayed var12 = new SoundEffectPlayed(Actor.field1496);
            var12.setSoundId(var2);
            var12.setDelay(var8);
            classOE.field4841.getCallbacks().post(var12);
            var10 = var12.isConsumed();
         } else {
            AreaSoundEffectPlayed var11 = new AreaSoundEffectPlayed(Actor.field1496);
            var11.setSoundId(var2);
            var11.setSceneX(var3);
            var11.setSceneY(var4);
            var11.setRange(var5);
            var11.setDelay(var8);
            classOE.field4841.getCallbacks().post(var11);
            var10 = var11.isConsumed();
         }

         if (var10) {
            this.field5974 = (this.field5974 * 2086456713 - 1) * 55425209;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIZ)V")
   @ObfuscatedName("js")
   public void method10550(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9) {
      this.method10527(var1, var2, var3, var4, var5, var6, var7, var8, var9, -1876940219);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsm;IIIIIIIIZ)V")
   @ObfuscatedName("rp")
   public static void method10530(classSM var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9) {
      if (var0.field5974 * 2086456713 < 50) {
         if ((var5 <= 0 || var0.field5977.vmethod388(-1625214020) != 0) && (var5 != 0 || var0.field5977.vmethod393(826200091) != 0)) {
            var0.field5975[var0.field5974 * 2086456713].field5900 = var1 * 322732777;
            var0.field5975[2086456713 * var0.field5974].field5902 = null;
            var0.field5975[var0.field5974 * 2086456713].field5901 = -643204909 * ((var3 << 16) + (var4 << 8) + var5);
            var0.field5975[2086456713 * var0.field5974].field5897 = -1575487195 * var2;
            var0.field5975[2086456713 * var0.field5974].field5899 = -1206170819 * var7;
            var0.field5975[2086456713 * var0.field5974].field5898 = -509408507 * var8;
            var0.field5975[var0.field5974 * 2086456713].field5896 = var6 * -1796103743;
            var0.field5975[var0.field5974 * 2086456713].field5903 = var9;
            var0.field5974 += 55425209;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIZ)V")
   @ObfuscatedName("au")
   public void method10531(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9) {
      if (this.field5974 * 2086456713 < 50) {
         if ((var5 <= 0 || this.field5977.vmethod388(-615876880) != 0) && (var5 != 0 || this.field5977.vmethod393(826200091) != 0)) {
            this.field5975[this.field5974 * -1180203821].field5900 = var1 * -1176004337;
            this.field5975[-1954165076 * this.field5974].field5902 = null;
            this.field5975[this.field5974 * 2086456713].field5901 = -1144242945 * ((var3 << 16) + (var4 << 8) + var5);
            this.field5975[2086456713 * this.field5974].field5897 = -1575487195 * var2;
            this.field5975[2086456713 * this.field5974].field5899 = 2124364414 * var7;
            this.field5975[2086456713 * this.field5974].field5898 = -509408507 * var8;
            this.field5975[this.field5974 * 2086456713].field5896 = var6 * 3029621;
            this.field5975[this.field5974 * 2086456713].field5903 = var9;
            this.field5974 += -1958188563;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ak")
   public void method10533(int var1) {
      this.field5974 -= -58424959;

      for (int var2 = var1; var2 < this.field5974 * 2086456713; var2++) {
         classSB.method10410(this.field5975[var2], this.field5975[var2 + 1], -1965745720);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("av")
   public void method10534(int var1) {
      this.field5974 -= 55425209;

      for (int var2 = var1; var2 < this.field5974 * 2086456713; var2++) {
         classSB.method10410(this.field5975[var2], this.field5975[var2 + 1], -1960851939);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ah")
   public int method10540() {
      return 2086456713 * this.field5974;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ao")
   public void method10521(int var1, int var2, int var3) {
      if (this.field5977.vmethod393(826200091) != 0 && var2 != 0) {
         this.method10527(0, var1, 0, 0, 0, 0, var2, var3, false, -203503683);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsm;IIIIIIII)V")
   @ObfuscatedName("bj")
   public static void method10526(classSM var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (var0.field5977.vmethod388(-701870121) != 0 && var7 > 0) {
         var0.method10527(var1, var2, var3, var4, var5, var6, var7, var8, false, -128298263);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("sj")
   public void method10551(int var1) {
      this.method10536(var1, (byte)-104);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   public int method10541() {
      return 2086456713 * this.field5974;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsm;I)V")
   @ObfuscatedName("ht")
   public static void method10535(classSM var0, int var1) {
      if (var0 == null) {
         var0.method10524(var1, var1, var1, var1, var1, var1, var1, var1);
      } else {
         var0.field5974 -= 55425209;

         for (int var2 = var1; var2 < var0.field5974 * 2086456713; var2++) {
            classSB.method10410(var0.field5975[var2], var0.field5975[var2 + 1], -1864657622);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsm;I)V")
   @ObfuscatedName("ip")
   public static void method10537(classSM var0, int var1) {
      if (var0 == null) {
         var0.method10538(var1);
      }

      try {
         var0.field5974 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sm.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ab")
   public void method10536(int var1, byte var2) {
      try {
         this.field5974 -= 55425209;

         for (int var3 = var1; var3 < this.field5974 * 2086456713; var3++) {
            if (var2 >= 8) {
               return;
            }

            classSB.method10410(this.field5975[var3], this.field5975[var3 + 1], -2134176686);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "sm.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bx")
   public boolean method10545() {
      return 2086456713 * this.field5974 >= 355913289;
   }

   @ObfuscatedSignature(descriptor = "(I)Lsb;")
   @ObfuscatedName("bp")
   public classSB method10547(int var1) {
      return this.field5975[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   public void method10538(int var1) {
      try {
         this.field5974 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sm.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   public int method10542(int var1) {
      try {
         return 2086456713 * this.field5974;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sm.as(" + 41);
      }
   }
}
