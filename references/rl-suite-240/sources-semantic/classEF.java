import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ef")
public class classEF {
   @ObfuscatedName("ae")
   int field1742;
   @ObfuscatedSignature(descriptor = "[Lsg;")
   @ObfuscatedName("af")
   classSG[] field1743;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final boolean field1746 = false;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field1744 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field1745 = 512;

   @ObfuscatedSignature(descriptor = "(Lef;I)[Lsg;")
   @ObfuscatedName("rq")
   public static classSG[] method3871(classEF var0, int var1) {
      if (var0 == null) {
         return var0.method3874(var1);
      } else {
         try {
            return var0.field1743;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "ef.ab(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILym;B)V")
   @ObfuscatedName("ax")
   static void method3882(int var0, int var1, int var2, SpritePixels var3, byte var4) {
      try {
         int var5 = 128 * var2;
         var5 += client.field995.method11515(1337910085);
         var5 &= 2047;
         byte var6 = 30;
         int var7 = var0 - (int)(classWW.method12700(var5, -2076197325) * var6);
         double var9 = classGP.method5582(var5, 1579021839) / 65536.0;
         int var11 = (int)(var9 * var6) + var1;
         int var12 = Math.max(var3.field7005, var3.field7007);
         int var13 = var12 / 2;
         int var15 = var7 - var13;
         int var16 = var11 - var13;
         int var17 = var3.field7005;
         int var18 = var3.field7007;
         int var19 = var3.field7005 / 2;
         int var20 = var3.field7007 / 2;
         int var22 = var5 & 2047;
         float var21 = (float)((Math.PI * 2) * (var22 / 2048.0F));
         var3.method13593(var15, var16, var17, var18, var19, var20, var21, 256);
      } catch (RuntimeException var23) {
         throw classEG.method3899(var23, "ef.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   void method3858(int var1) {
      this.method3885();

      try {
         classSG[] var2 = this.field1743;

         for (int var3 = 0; var3 < var2.length; var3++) {
            if (var1 <= 2130045616) {
               return;
            }

            classSG var4 = var2[var3];
            if (-521703669 * var4.field5922 >= 0) {
               if (var1 <= 2130045616) {
                  return;
               }

               var4.field5927 = this.field1743[var4.field5922 * -521703669];
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ef.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   public int method3864(int var1) {
      try {
         return this.field1743.length;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ef.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Lsg;")
   @ObfuscatedName("ae")
   public classSG method3866(int var1, byte var2) {
      try {
         if (var1 >= this.method3864(1353996394)) {
            if (var2 == 1) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            return this.field1743[var1];
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ef.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lsg;")
   @ObfuscatedName("ad")
   public classSG method3867(int var1) {
      return var1 >= this.method3864(-1129027061) ? null : this.field1743[var1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lef;Lsn;I[ZZ)V")
   @ObfuscatedName("cs")
   public static void method3880(classEF var0, classSN var1, int var2, boolean[] var3, boolean var4) {
      if (var0 == null) {
         var0.method3881(var1, var2, var3, var4);
      }

      int var5 = var1.method10559(372651847);
      int var6 = 0;
      classSG[] var7 = method3871(var0, -1148706420);

      for (int var8 = 0; var8 < var7.length; var8++) {
         classSG var9 = var7[var8];
         if (var3 == null || var3[var6] == var4) {
            classXC.method12814(var1, var2, var9, var6, var5, -732732242);
         }

         var6++;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   void method3859() {
      classSG[] var1 = this.field1743;

      for (int var2 = 0; var2 < var1.length; var2++) {
         classSG var3 = var1[var2];
         if (1635126909 * var3.field5922 >= 0) {
            var3.field5927 = this.field1743[var3.field5922 * -1006780702];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;I)V")
   @ObfuscatedName("sv")
   public static void method3883(Actor var0, int var1) {
      var0.field1461.field627 = var1 * -689558257;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   void method3860() {
      classSG[] var1 = this.field1743;

      for (int var2 = 0; var2 < var1.length; var2++) {
         classSG var3 = var1[var2];
         if (-521703669 * var3.field5922 >= 0) {
            var3.field5927 = this.field1743[var3.field5922 * -521703669];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   void method3861() {
      classSG[] var1 = this.field1743;

      for (int var2 = 0; var2 < var1.length; var2++) {
         classSG var3 = var1[var2];
         if (-521703669 * var3.field5922 >= 0) {
            var3.field5927 = this.field1743[var3.field5922 * -521703669];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("aw")
   public void method3875(classSN var1, int var2) {
      this.method3886(var1, var2, null, false, (byte)59);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   void method3862() {
      classSG[] var1 = this.field1743;

      for (int var2 = 0; var2 < var1.length; var2++) {
         classSG var3 = var1[var2];
         if (-521703669 * var3.field5922 >= 0) {
            var3.field5927 = this.field1743[var3.field5922 * -521703669];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("av")
   public void method3876(classSN var1, int var2) {
      this.method3886(var1, var2, null, false, (byte)47);
   }

   @ObfuscatedSignature(descriptor = "(I)Lsg;")
   @ObfuscatedName("aq")
   public classSG method3868(int var1) {
      return var1 >= this.method3864(-1125212950) ? null : this.field1743[var1];
   }

   @ObfuscatedSignature(descriptor = "()[Lsg;")
   @ObfuscatedName("au")
   classSG[] method3872() {
      return this.field1743;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("rx")
   public void method3885() {
      int var1 = 0;

      while (var1 < this.field1743.length) {
         this.field1743[var1].field5926 = var1++;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lsg;")
   @ObfuscatedName("ap")
   public classSG method3869(int var1) {
      return var1 >= this.method3864(-1925436490) ? null : this.field1743[var1];
   }

   @ObfuscatedSignature(descriptor = "()[Lsg;")
   @ObfuscatedName("ai")
   classSG[] method3873() {
      return this.field1743;
   }

   @ObfuscatedSignature(descriptor = "(Ljs;Lyt;B)Ljl;")
   @ObfuscatedName("af")
   public static classJL method3863(classJS var0, classYT var1, byte var2) {
      try {
         classJL var3 = SceneTilePaint.method4814(2062869276);
         var3.field3340 = var0;
         var3.field3337 = -1950310809 * var0.field3369;
         if (-1669214905 * var3.field3337 == -1) {
            if (var2 == 80) {
               throw new IllegalStateException();
            }

            var3.field3341 = new PacketBuffer(260);
         } else if (-1669214905 * var3.field3337 == -2) {
            if (var2 == 80) {
               throw new IllegalStateException();
            }

            var3.field3341 = new PacketBuffer(10000);
         } else if (var3.field3337 * -1669214905 <= 18) {
            var3.field3341 = new PacketBuffer(20);
         } else if (var3.field3337 * -1669214905 <= 98) {
            if (var2 == 80) {
               throw new IllegalStateException();
            }

            var3.field3341 = new PacketBuffer(100);
         } else {
            var3.field3341 = new PacketBuffer(260);
         }

         var3.field3341.method12959(var1, -1178783538);
         var3.field3341.method12964(var3.field3340.field3370 * 484572505, (byte)-68);
         var3.field3333 = 0;
         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ef.af(" + ')');
      }
   }

   classEF(classXY var1, int var2) {
      this.field1743 = new classSG[var2];
      this.field1742 = var1.method13095(-346779531) * -983578009;

      for (int var3 = 0; var3 < this.field1743.length; var3++) {
         classSG var4 = new classSG(-2083016361 * this.field1742, var1, false);
         this.field1743[var3] = var4;
      }

      this.method3858(2142449012);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lef;)I")
   @ObfuscatedName("io")
   public static int method3865(classEF var0) {
      return var0.field1743.length;
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("ak")
   public void method3877(classSN var1, int var2) {
      this.method3886(var1, var2, null, false, (byte)97);
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I[ZZB)V")
   @ObfuscatedName("as")
   void method3886(classSN var1, int var2, boolean[] var3, boolean var4, byte var5) {
      this.method3888(rl21.field5726, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I)V")
   @ObfuscatedName("at")
   public void method3878(classSN var1, int var2) {
      this.method3886(var1, var2, null, false, (byte)102);
   }

   @ObfuscatedSignature(descriptor = "(Lsn;II)V")
   @ObfuscatedName("ag")
   public void method3879(classSN var1, int var2, int var3) {
      try {
         this.method3886(var1, var2, null, false, (byte)116);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ef.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;Lsn;I[ZZ)V")
   @ObfuscatedName("tj")
   public void method3888(rl21 var1, classSN var2, int var3, boolean[] var4, boolean var5) {
      int var6 = var2.field5989 * -1268206765;
      int var7 = 0;

      for (classSG var11 : this.field1743) {
         if (var4 == null || var5 == var4[var7]) {
            var2.method10589(var1, var3, var11, var7, var6);
         }

         var7++;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lsg;")
   @ObfuscatedName("ay")
   public classSG method3870(int var1) {
      return var1 >= this.method3864(-1009985795) ? null : this.field1743[var1];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lx")
   public int method3889() {
      return this.method3864(1786141898);
   }

   @ObfuscatedSignature(descriptor = "(B)Lmh;")
   @ObfuscatedName("ax")
   public static classMH method3857(byte var0) {
      try {
         try {
            return new classMG();
         } catch (Throwable var2) {
            return new classMQ();
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ef.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsn;I[ZZ)V")
   @ObfuscatedName("am")
   void method3881(classSN var1, int var2, boolean[] var3, boolean var4) {
      int var5 = var1.method10559(-1222070415);
      int var6 = 0;
      classSG[] var7 = method3871(this, -1477469449);

      for (int var8 = 0; var8 < var7.length; var8++) {
         classSG var9 = var7[var8];
         if (var3 == null || var3[var6] == var4) {
            classXC.method12814(var1, var2, var9, var6, var5, -108490733);
         }

         var6++;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("bj")
   static int method3884(int var0, Script var1, boolean var2, int var3) {
      try {
         if (var0 == 3400) {
            if (var3 <= -1510882375) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var11 = classBB.field323[-324749371 * DynamicObject.field1676];
               int var13 = classBB.field323[1 + DynamicObject.field1676 * -324749371];
               EnumComposition var14 = classKD.method6500(var11, 530175687);
               if (!var14.method8377('s', (byte)-6)) {
               }

               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = EnumComposition.method8358(var14, var13, -1652195152);
               return 1;
            }
         } else if (3408 == var0) {
            DynamicObject.field1676 -= 480164916;
            char var10 = (char)classBB.field323[DynamicObject.field1676 * -324749371];
            char var12 = (char)classBB.field323[1 + DynamicObject.field1676 * -324749371];
            int var6 = classBB.field323[-324749371 * DynamicObject.field1676 + 2];
            int var7 = classBB.field323[3 + DynamicObject.field1676 * -324749371];
            EnumComposition var8 = classKD.method6500(var6, 1708727636);
            if (EnumComposition.method8373(var8, var10, 1682366299)) {
               if (var3 <= -1510882375) {
                  throw new IllegalStateException();
               }

               if (var8.method8377(var12, (byte)-11)) {
                  if (var8.method8377('s', (byte)-5)) {
                     if (var3 <= -1510882375) {
                        throw new IllegalStateException();
                     }

                     classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = EnumComposition.method8358(var8, var7, -1652195152);
                  } else if (var8.method8377('Ï', (byte)-1)) {
                     classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = var8.method8368(var7, -980214786);
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var8.method8362(var7, -1739848449);
                  }

                  return 1;
               }

               if (var3 <= -1510882375) {
                  throw new IllegalStateException();
               }
            }

            if (var12 == 's') {
               if (var3 <= -1510882375) {
                  throw new IllegalStateException();
               }

               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classKK.field3613;
            } else if (var12 == 207) {
               if (var3 <= -1510882375) {
                  throw new IllegalStateException();
               }

               classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = 0L;
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
            }

            return 1;
         } else if (var0 == 3411) {
            if (var3 <= -1510882375) {
               throw new IllegalStateException();
            } else {
               int var4 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               EnumComposition var5 = classKD.method6500(var4, 697661822);
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = EnumComposition.method8346(var5, 1033162533);
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ef.bj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lsg;")
   @ObfuscatedName("if")
   public classSG method3890(int var1) {
      return this.method3866(var1, (byte)95);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("wq")
   public void method3887() {
      for (int var1 = 0; var1 < this.field1742 * -2083016361; var1++) {
         for (classSG var5 : this.field1743) {
            var5.method10478(var1);
         }
      }

      for (int var6 = 0; var6 < this.field1742 * -2083016361; var6++) {
         for (classSG var10 : this.field1743) {
            var10.method10473(var6);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Lsg;")
   @ObfuscatedName("ab")
   classSG[] method3874(int var1) {
      try {
         return this.field1743;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ef.ab(" + ')');
      }
   }
}
