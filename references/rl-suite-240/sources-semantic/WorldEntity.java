import net.runelite.api.coords.LocalPoint;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sp")
public class WorldEntity extends Node implements classYB, net.runelite.api.WorldEntity {
   @ObfuscatedName("au")
   public int field6007;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6022 = -1200;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6021 = 31;
   @ObfuscatedSignature(descriptor = "Lgy;")
   @ObfuscatedName("ab")
   public final classGY field6017;
   @ObfuscatedName("aa")
   int field6009;
   @ObfuscatedSignature(descriptor = "Ldz;")
   @ObfuscatedName("as")
   public WorldView field6010;
   @ObfuscatedName("ag")
   public int field6018;
   @ObfuscatedSignature(descriptor = "[Lsr;")
   @ObfuscatedName("ac")
   classSR[] field6008;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final float field6020 = 0.01F;
   @ObfuscatedSignature(descriptor = "Loi;")
   @ObfuscatedName("ax")
   public WorldEntityConfig field6024;
   @ObfuscatedName("ao")
   public int field6016;
   @ObfuscatedName("aj")
   int field6011;
   @ObfuscatedSignature(descriptor = "Lsq;")
   @ObfuscatedName("ay")
   classSQ field6012;
   @ObfuscatedName("xu")
   public boolean field6006 = false;
   @ObfuscatedName("aq")
   boolean field6013;
   @ObfuscatedSignature(descriptor = "Lqr;")
   @ObfuscatedName("ad")
   public classQR field6014;
   @ObfuscatedSignature(descriptor = "Lqr;")
   @ObfuscatedName("ap")
   public classQR field6015;
   @ObfuscatedSignature(descriptor = "Lkr;")
   @ObfuscatedName("al")
   classKR field6019;
   @ToRemove(unused = "true")
   @ObfuscatedName("cl")
   static final int field6023 = 249;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cx")
   public final void method10663(int var1) {
      if (-1706960194 * this.field6009 == 0) {
         this.method10657(this.field6008[0].field6027, (byte)-63);
      } else {
         if (!this.field6013) {
            this.field6012.vmethod568(this.field6017, this.field6008[0], var1, (byte)6);
            this.field6013 = true;
         }

         if (this.field6012.vmethod571(this.field6017, var1, this.field6009 * 1612343003, 2111579848)) {
            this.field6009 -= 1733918897;
            this.field6013 = false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bp")
   public boolean method10600() {
      return this.field6010.field1687.field2053 == 0.01F;
   }

   @ObfuscatedSignature(descriptor = "(Lsp;ZI)Lkw;")
   @ObfuscatedName("oe")
   public static classKW method10617(WorldEntity var0, boolean var1, int var2) {
      if (var0 == null) {
         var0.method10621(var1, var2);
      }

      try {
         if (var1) {
            if (var2 == -1640531527) {
               throw new IllegalStateException();
            } else {
               return classKW.field4041;
            }
         } else {
            return var0.field6024.method8520(2136997395);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("ae")
   public void method10597(boolean var1, int var2) {
      try {
         classQR var3 = this.method10595(-873839240);
         classQR var10000;
         if (var3 != null) {
            if (var2 <= 1772825224) {
               throw new IllegalStateException();
            }

            var10000 = var3;
         } else {
            var10000 = this.field6014;
         }

         classQR var4 = var10000;
         this.field6010.field1687.field2026.method11635(-1782703087);
         if (null != var4) {
            if (var2 <= 1772825224) {
               throw new IllegalStateException();
            }

            if (var4.method9776(500216846) != -1 && var4.method9807((byte)-52) && Animation.method8805(var4.method9775((byte)24), (byte)0)) {
               if (var2 <= 1772825224) {
                  return;
               }

               classSN var5 = var4.method9775((byte)56).method8817(-1744796165);
               classEF var6 = var5.field5981.method3948((byte)-26);
               classSG var7 = var6.method3866(0, (byte)95);
               if (var7 != null) {
                  if (var2 <= 1772825224) {
                     return;
                  }

                  var6.method3879(var5, var4.method9780(1984534226), -1468766705);
                  classUZ.method11857(this.field6010.field1687.field2026, var7.method10471(-2048083282), (byte)-94);
                  this.field6010.field1687.field2026.field6399 = -this.field6010.field1687.field2026.field6399;
               }
            }
         }

         this.field6010.field1687.field2025 = -483624883 * this.field6010.field1708;
         if (var1) {
            if (var2 <= 1772825224) {
               return;
            }

            this.field6010.field1687.field2028 = -1200;
            this.field6010.field1687.field2053 = 0.01F;
            int var11 = this.field6024.method8530(-906701437);
            classFL var12 = this.field6010.field1687.field2033;
            byte var13 = classGK.method5502(var11, -1823464209);
            byte var8 = classGK.method5503(var11, -1665176581);
            byte var9 = (byte)(var11 & 127);
            classFL.method4850(var12, var13, var8, var9, (byte)127);
         } else {
            this.field6010.field1687.field2028 = 0;
            this.field6010.field1687.field2053 = 1.0F;
            classFL.method4856(this.field6010.field1687.field2033);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "sp.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("sy")
   public void method10675() {
      client.field1038.add(this);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   public boolean method10601(int var1) {
      try {
         return this.field6010.field1687.field2053 == 0.01F;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Lkw;")
   @ObfuscatedName("bi")
   public classKW method10618(boolean var1) {
      return var1 ? classKW.field4041 : this.field6024.method8520(1221832000);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   public int method10602(int var1) {
      try {
         return this.field6017.method5933(1170569827);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ea")
   @Override
   public int vmethod371(int var1) {
      try {
         return this.field6017.method5936(1690676705);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.ea(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod258(byte var1) {
      try {
         if (this.field6018 * -1427480299 != 0) {
            if (var1 >= 0) {
               throw new IllegalStateException();
            }

            WorldView var2 = client.field815.method1584(-1427480299 * this.field6018, (short)30221);
            if (var2 != null) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               return var2.field1708 * -483624883;
            }
         }

         return classIS.field3051.field1708 * -483624883;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aq")
   public int method10604(int var1) {
      try {
         return classGY.method5947(this.field6017, 1938488016);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lgy;")
   @ObfuscatedName("ax")
   public classGY method10606(int var1) {
      try {
         classGY var10000;
         if (-1956329391 * this.field6009 == 0) {
            if (var1 >= 704602430) {
               throw new IllegalStateException();
            }

            var10000 = this.field6017;
         } else {
            var10000 = this.field6008[0].field6027;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Loi;I)V")
   @ObfuscatedName("ac")
   public void method10610(WorldEntityConfig var1, int var2) {
      try {
         this.field6024 = var1;
         this.field6010.field1687.field2027 = var1.method8507(-2078533940);
         this.field6010.field1687.field2045 = var1.method8511((byte)-10);
         method10641(this, var1.method8512((byte)-18), (byte)-48);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lkr;I)V")
   @ObfuscatedName("al")
   public void method10622(classKR var1, int var2) {
      try {
         this.field6019 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.al(" + ')');
      }
   }

   public int getOwnerType() {
      return this.field6019.method13328();
   }

   @ObfuscatedSignature(descriptor = "()Lgy;")
   @ObfuscatedName("bk")
   public classGY method10607() {
      return -1956329391 * this.field6009 == 0 ? this.field6017 : this.field6008[0].field6027;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   public int method10635(int var1) {
      try {
         return this.field6010.field1690 * 377427776 + this.field6024.method8511((byte)-10);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.ad(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)V")
   @ObfuscatedName("uk")
   public void method10656(classGY var1) {
      this.field6017.method5953(var1, -1957102677);
      this.field6008[0].field6027.method5953(var1, 1084681233);
      this.field6009 = 0;
      this.field6013 = false;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ai")
   public void method10648(int var1, int var2, int var3) {
      try {
         for (int var4 = 0; var4 < this.field6008.length; var4++) {
            if (var3 <= 797468011) {
               throw new IllegalStateException();
            }

            this.field6008[var4].field6027.method5963(var1, var2, (byte)117);
         }

         this.field6017.method5963(var1, var2, (byte)101);
         this.field6012.vmethod573(var1, var2, -1079091816);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "sp.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ldz;")
   @ObfuscatedName("zs")
   public WorldView method10676() {
      return this.field6010;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Lgy;B)V")
   @ObfuscatedName("ar")
   public final void method10653(WorldView var1, classGY var2, byte var3) {
      try {
         int var4 = var2.method5940(-1063803863);
         int var5 = var2.method5944((byte)-36);
         if (!WorldView.method3745(var1, var4, var5, (byte)1)) {
            if (var3 != 0) {
               return;
            }

            this.method10657(var2, (byte)-103);
         } else {
            int var6 = classGY.method5930(var2, 856187210) - classGY.method5930(this.field6008[0].field6027, 23684708);
            int var7 = var2.method5936(1690676705) - this.field6008[0].field6027.method5936(1690676705);
            method10660(this, var2, Math.abs(var6), Math.abs(var7), 62620156);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "sp.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;B)V")
   @ObfuscatedName("aw")
   public void method10657(classGY var1, byte var2) {
      this.method10672(var1);

      try {
         this.field6017.method5953(var1, 578993763);
         this.field6008[0].field6027.method5953(var1, 919843294);
         this.field6009 = 0;
         this.field6013 = false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ci")
   public final void method10664(int var1) {
      if (-1956329391 * this.field6009 == 0) {
         this.method10657(this.field6008[0].field6027, (byte)-49);
      } else {
         if (!this.field6013) {
            this.field6012.vmethod568(this.field6017, this.field6008[0], var1, (byte)16);
            this.field6013 = true;
         }

         if (this.field6012.vmethod571(this.field6017, var1, this.field6009 * -1956329391, 1263072438)) {
            this.field6009 -= 1733918897;
            this.field6013 = false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("co")
   public void method10649(int var1, int var2) {
      for (int var3 = 0; var3 < this.field6008.length; var3++) {
         this.field6008[var3].field6027.method5963(var1, var2, (byte)79);
      }

      this.field6017.method5963(var1, var2, (byte)119);
      this.field6012.vmethod573(var1, var2, -1419331369);
   }

   @ObfuscatedSignature(descriptor = "(IIB)Luz;")
   @ObfuscatedName("at")
   public classUZ method10666(int var1, int var2, byte var3) {
      try {
         classUG var4 = classMQ.method7682(-989125358);
         classUU var5 = classRM.method10172(1778071523);
         classUL var6 = var4.field6303;
         int var8 = classGY.method5947(this.field6017, 1880969876);
         var8 &= 2047;
         float var7 = (float)((Math.PI * 2) * (var8 / 2048.0F));
         var6.method11437(var7, 0.0F, 0.0F, -608116520);
         classUZ.method11802(var4.field6305, classGY.method5930(this.field6017, 152053513), 0.0F, this.field6017.method5936(1690676705), 1685737082);
         var1 -= method10634(this, -532164052);
         var2 -= this.method10635(1874950260);
         var5.method11706(var4, 1861666336);
         var4.method11364(-428825938);
         classUZ var9 = classUU.method11680(var5, var1, 0.0F, var2, 1746397518);
         var5.method11615(2033466195);
         return var9;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "sp.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)Luz;")
   @ObfuscatedName("an")
   public classUZ method10667(int var1, int var2, byte var3) {
      try {
         classUG var4 = classMQ.method7682(-1532875664);
         classUU var5 = classRM.method10172(1778071523);
         classUL var6 = var4.field6303;
         int var8 = classGY.method5947(this.field6017, 1777638888);
         var8 &= 2047;
         float var7 = (float)(var8 / 2048.0F * (Math.PI * 2));
         var6.method11437(var7, 0.0F, 0.0F, 2100974472);
         classUZ.method11802(var4.field6305, classGY.method5930(this.field6017, 277844038), 0.0F, this.field6017.method5936(1690676705), 1713688340);
         var5.method11706(var4, 1204407126);
         classUU.method11670(var5, -1126922930);
         var4.method11364(-333835718);
         classUZ var9 = classUU.method11680(var5, var1, 0.0F, var2, 1969403077);
         var9.method11823(method10634(this, -1942590158), 0.0F, this.method10635(2088304455), -1062600558);
         var5.method11615(2093398077);
         return var9;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "sp.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bj")
   @Override
   public int vmethod259() {
      if (this.field6018 * -1427480299 != 0) {
         WorldView var1 = client.field815.method1584(-1427480299 * this.field6018, (short)-3992);
         if (var1 != null) {
            return var1.field1708 * -483624883;
         }
      }

      return classIS.field3051.field1708 * -483624883;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hq")
   @Override
   public int vmethod369() {
      return classGY.method5930(this.field6017, 252711677);
   }

   @ObfuscatedSignature(descriptor = "()Lqr;")
   @ObfuscatedName("am")
   classQR method10593() {
      return 0 == -1902770053 * this.field6016 && this.field6015.method9772(374233424) && this.field6015.method9775((byte)41).method8820((byte)92)
         ? this.field6015
         : null;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bx")
   public void method10598(boolean var1) {
      classQR var2 = this.method10595(-873839240);
      classQR var3 = var2 != null ? var2 : this.field6014;
      this.field6010.field1687.field2026.method11635(-246549455);
      if (null != var3 && var3.method9776(500216846) != -1 && var3.method9807((byte)-108) && Animation.method8805(var3.method9775((byte)44), (byte)0)) {
         classSN var4 = var3.method9775((byte)18).method8817(1643765412);
         classEF var5 = var4.field5981.method3948((byte)5);
         classSG var6 = var5.method3866(0, (byte)35);
         if (var6 != null) {
            var5.method3879(var4, var3.method9780(1914775810), -1468766705);
            classUZ.method11857(this.field6010.field1687.field2026, var6.method10471(-2087567950), (byte)-91);
            this.field6010.field1687.field2026.field6399 = -this.field6010.field1687.field2026.field6399;
         }
      }

      this.field6010.field1687.field2025 = 2025563689 * this.field6010.field1708;
      if (var1) {
         this.field6010.field1687.field2028 = -1002495153;
         this.field6010.field1687.field2053 = 0.01F;
         int var9 = this.field6024.method8530(1938294297);
         classFL var10 = this.field6010.field1687.field2033;
         byte var11 = classGK.method5502(var9, -1800368190);
         byte var7 = classGK.method5503(var9, -1665176581);
         byte var8 = (byte)(var9 & 905041216);
         classFL.method4850(var10, var11, var7, var8, (byte)-369898630);
      } else {
         this.field6010.field1687.field2028 = 0;
         this.field6010.field1687.field2053 = 1.0F;
         classFL.method4856(this.field6010.field1687.field2033);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cp")
   public int method10636() {
      return this.field6010.field1690 * 377427776 + this.field6024.method8511((byte)68);
   }

   @ObfuscatedSignature(descriptor = "(Lkr;)V")
   @ObfuscatedName("bd")
   public void method10623(classKR var1) {
      this.field6019 = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kh")
   @Override
   public int vmethod372() {
      return this.field6017.method5936(1690676705);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ky")
   @Override
   public int vmethod370() {
      return this.field6017.method5936(1690676705);
   }

   @ObfuscatedSignature(descriptor = "(Lkr;)V")
   @ObfuscatedName("bf")
   public void method10624(classKR var1) {
      this.field6019 = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("br")
   public int method10603() {
      return this.field6017.method5933(1204387032);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("av")
   public final void method10673(int var1, int var2) {
   }

   @ObfuscatedSignature(descriptor = "()Loi;")
   @ObfuscatedName("jg")
   public WorldEntityConfig method10677() {
      return this.field6024;
   }

   @ObfuscatedSignature(descriptor = "()Lkr;")
   @ObfuscatedName("bw")
   public classKR method10613() {
      return this.field6019;
   }

   @ObfuscatedSignature(descriptor = "(Loi;)V")
   @ObfuscatedName("bo")
   public void method10611(WorldEntityConfig var1) {
      this.field6024 = var1;
      this.field6010.field1687.field2027 = var1.method8507(-1652450642);
      this.field6010.field1687.field2045 = var1.method8511((byte)-88);
      method10641(this, var1.method8512((byte)-77), (byte)-30);
   }

   @ObfuscatedSignature(descriptor = "(Lsp;Lgy;III)V")
   @ObfuscatedName("hy")
   public static void method10660(WorldEntity var0, classGY var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method10662(var1, var2, var2, var2);
      } else {
         try {
            if (-1956329391 * var0.field6009 < 9) {
               if (var4 == 1222866659) {
                  return;
               }

               var0.field6009 += 1733918897;
            }

            for (int var5 = -1956329391 * var0.field6009; var5 > 0; var5--) {
               classSR var6 = var0.field6008[var5];
               var0.field6008[var5] = var0.field6008[var5 - 1];
               var0.field6008[var5 - 1] = var6;
            }

            var0.field6008[0].field6027.method5953(var1, -167262994);
            var0.field6008[0].field6028 = client.field856 * -1876258533;
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "sp.ak(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Loi;)V")
   @ObfuscatedName("bu")
   public void method10612(WorldEntityConfig var1) {
      this.field6024 = var1;
      this.field6010.field1687.field2027 = var1.method8507(-1855780411);
      this.field6010.field1687.field2045 = var1.method8511((byte)-9);
      method10641(this, var1.method8512((byte)59), (byte)-85);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("cr")
   public boolean method10630(int var1) {
      return var1 >= 0 && var1 <= 4 ? 0 != (-763793253 * this.field6011 & 1 << var1) : true;
   }

   @ObfuscatedSignature(descriptor = "()Lkr;")
   @ObfuscatedName("bb")
   public classKR method10614() {
      return this.field6019;
   }

   @ObfuscatedSignature(descriptor = "()Lqr;")
   @ObfuscatedName("ah")
   classQR method10594() {
      return 0 == -1902770053 * this.field6016 && this.field6015.method9772(374233424) && this.field6015.method9775((byte)47).method8820((byte)-5)
         ? this.field6015
         : null;
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ay")
   public boolean method10631(int var1, byte var2) {
      try {
         if (var1 >= 0) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            if (var1 <= 4) {
               if (var2 <= 1) {
                  throw new IllegalStateException();
               }

               return 0 != (-763793253 * this.field6011 & 1 << var1);
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Lkw;")
   @ObfuscatedName("be")
   public classKW method10619(boolean var1) {
      return var1 ? classKW.field4041 : this.field6024.method8520(1420708477);
   }

   public LocalPoint getCameraFocus() {
      return this.getLocalLocation();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;Lgy;)V")
   @ObfuscatedName("vw")
   public static void method10658(WorldEntity var0, classGY var1) {
      var0.field6017.method5953(var1, -730153298);
      var0.field6008[0].field6027.method5953(var1, 1669841926);
      var0.field6009 = 0;
      var0.field6013 = false;
   }

   @ObfuscatedSignature(descriptor = "(Z)Lkw;")
   @ObfuscatedName("bq")
   public classKW method10620(boolean var1) {
      return var1 ? classKW.field4041 : this.field6024.method8520(40625837);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;)Lwj;")
   @ObfuscatedName("vi")
   public static classWJ method10670(ObjectComposition var0) {
      return var0.field4961;
   }

   @ObfuscatedSignature(descriptor = "(Lkr;)V")
   @ObfuscatedName("bl")
   public void method10625(classKR var1) {
      this.field6019 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Lqr;")
   @ObfuscatedName("af")
   classQR method10595(int var1) {
      try {
         if (0 == -1902770053 * this.field6016) {
            if (var1 != -873839240) {
               throw new IllegalStateException();
            }

            if (this.field6015.method9772(374233424)) {
               if (var1 != -873839240) {
                  throw new IllegalStateException();
               }

               if (this.field6015.method9775((byte)66).method8820((byte)115)) {
                  if (var1 != -873839240) {
                     throw new IllegalStateException();
                  }

                  return this.field6015;
               }
            }
         }

         return null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ey")
   @Override
   public int vmethod368(int var1) {
      try {
         return classGY.method5930(this.field6017, 766668145);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.ey(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Luz;")
   @ObfuscatedName("cm")
   public classUZ method10668(int var1, int var2) {
      classUG var3 = classMQ.method7682(366539406);
      classUU var4 = classRM.method10172(1778071523);
      classUL var5 = var3.field6303;
      int var7 = classGY.method5947(this.field6017, 1629522068);
      var7 &= 2047;
      float var6 = (float)(var7 / 2048.0F * (Math.PI * 2));
      var5.method11437(var6, 0.0F, 0.0F, 96113479);
      classUZ.method11802(var3.field6305, classGY.method5930(this.field6017, 886335028), 0.0F, this.field6017.method5936(1690676705), 2116555934);
      var4.method11706(var3, 1651017828);
      classUU.method11670(var4, 154152551);
      var3.method11364(-584363827);
      classUZ var8 = classUU.method11680(var4, var1, 0.0F, var2, -288434109);
      var8.method11823(method10634(this, -2099053425), 0.0F, this.method10635(-288724597), -1062600558);
      var4.method11615(2111172299);
      return var8;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bv")
   public void method10627(int var1) {
      this.field6011 = -1259548253 * var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bh")
   public boolean method10632(int var1) {
      return var1 >= 0 && var1 <= 4 ? 0 != (-763793253 * this.field6011 & 1 << var1) : true;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bz")
   public boolean method10633(int var1) {
      return var1 >= 0 && var1 <= 4 ? 0 != (-1720764155 * this.field6011 & 1 << var1) : true;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;I)V")
   @ObfuscatedName("ks")
   public static void method10640(WorldEntity var0, int var1) {
      classQR.method9770(var0.field6014, var1, -2008790253);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cq")
   public int method10637() {
      return this.field6010.field1690 * 377427776 + this.field6024.method8511((byte)30);
   }

   @ObfuscatedSignature(descriptor = "(Lsp;IB)V")
   @ObfuscatedName("qv")
   public static void method10641(WorldEntity var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method10643(var1, var2);
      }

      try {
         classQR.method9770(var0.field6014, var1, -2114326922);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ct")
   public int method10638() {
      return this.field6010.field1690 * 377427776 + this.field6024.method8511((byte)-107);
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("au")
   public int method10644(byte var1) {
      try {
         return this.field6015.method9776(500216846);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.au(" + 41);
      }
   }

   public LocalPoint getTargetLocation() {
      return this.field6009 * -1956329391 > 0
         ? new LocalPoint(this.field6008[0].field6027.field2739 * -787916131, this.field6008[0].field6027.field2740 * -478154003, 0)
         : this.getLocalLocation();
   }

   public WorldEntity(int var1, WorldView var2) {
      this.method10675();
      this.field6017 = new classGY();
      this.field6007 = 0;
      this.field6008 = new classSR[10];
      this.field6009 = 0;
      this.field6018 = 0;
      this.field6019 = classKR.field4004;
      this.field6011 = -1784824627;
      this.field6012 = new classSC();
      this.field6013 = false;
      this.field6014 = new classQR();
      this.field6015 = new classQR();
      this.field6016 = 0;
      this.field6007 = -662280981 * var1;
      this.field6010 = var2;
      this.field6009 = 0;

      for (int var3 = 0; var3 < 10; var3++) {
         this.field6008[var3] = new classSR();
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aj")
   public void method10650(int var1, int var2) {
      for (int var3 = 0; var3 < this.field6008.length; var3++) {
         this.field6008[var3].field6027.method5963(var1, var2, (byte)81);
      }

      this.field6017.method5963(var1, var2, (byte)79);
      this.field6012.vmethod573(var1, var2, -1959195373);
   }

   @ObfuscatedSignature(descriptor = "(Lsp;II)V")
   @ObfuscatedName("bt")
   public static void method10628(WorldEntity var0, int var1, int var2) {
      if (var0 == null) {
         var0.method10629(var1, var1);
      } else {
         try {
            var0.field6011 = -1165953645 * var1;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "sp.aj(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cv")
   public int method10645() {
      return this.field6015.method9776(500216846);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;)I")
   @ObfuscatedName("up")
   public static int method10605(WorldEntity var0) {
      return classGY.method5947(var0.field6017, 2070687641);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cs")
   public int method10646() {
      return this.field6015.method9776(500216846);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cy")
   public int method10647() {
      return this.field6015.method9776(500216846);
   }

   public int getOrientation() {
      return this.field6017.field2742 * 1859166197;
   }

   @ObfuscatedSignature(descriptor = "(Lsp;I)I")
   @ObfuscatedName("xm")
   public static int method10634(WorldEntity var0, int var1) {
      if (var0 == null) {
         return var0.method10639(var1);
      } else {
         try {
            return var0.field6010.field1694 * 1386308288 + var0.field6024.method8507(-1991754207);
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "sp.aq(" + 41);
         }
      }
   }

   public LocalPoint getLocalLocation() {
      return new LocalPoint(this.field6017.field2739 * -787916131, this.field6017.field2740 * -478154003, 0);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ca")
   public void method10651(int var1, int var2) {
      for (int var3 = 0; var3 < this.field6008.length; var3++) {
         this.field6008[var3].field6027.method5963(var1, var2, (byte)81);
      }

      this.field6017.method5963(var1, var2, (byte)113);
      this.field6012.vmethod573(var1, var2, -1520996730);
   }

   @ObfuscatedSignature(descriptor = "()Lgy;")
   @ObfuscatedName("bg")
   public classGY method10608() {
      return 740420949 * this.field6009 == 0 ? this.field6017 : this.field6008[0].field6027;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;Z)V")
   @ObfuscatedName("be")
   public static void method10599(WorldEntity var0, boolean var1) {
      if (var0 == null) {
         var0.method10603();
      }

      classQR var2 = var0.method10595(-873839240);
      classQR var3 = var2 != null ? var2 : var0.field6014;
      var0.field6010.field1687.field2026.method11635(-1327161563);
      if (null != var3 && var3.method9776(500216846) != -1 && var3.method9807((byte)-82) && Animation.method8805(var3.method9775((byte)110), (byte)0)) {
         classSN var4 = var3.method9775((byte)88).method8817(140288715);
         classEF var5 = var4.field5981.method3948((byte)38);
         classSG var6 = var5.method3866(0, (byte)-1);
         if (var6 != null) {
            var5.method3879(var4, var3.method9780(1798412406), -1468766705);
            classUZ.method11857(var0.field6010.field1687.field2026, var6.method10471(-1993724010), (byte)38);
            var0.field6010.field1687.field2026.field6399 = -var0.field6010.field1687.field2026.field6399;
         }
      }

      var0.field6010.field1687.field2025 = -1606618975 * var0.field6010.field1708;
      if (var1) {
         var0.field6010.field1687.field2028 = -1200;
         var0.field6010.field1687.field2053 = 0.01F;
         int var9 = var0.field6024.method8530(-1845887744);
         classFL var10 = var0.field6010.field1687.field2033;
         byte var11 = classGK.method5502(var9, -1961320422);
         byte var7 = classGK.method5503(var9, -1665176581);
         byte var8 = (byte)(var9 & -1033650383);
         classFL.method4850(var10, var11, var7, var8, (byte)127);
      } else {
         var0.field6010.field1687.field2028 = 0;
         var0.field6010.field1687.field2053 = 1.0F;
         classFL.method4856(var0.field6010.field1687.field2033);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ck")
   public void method10652(int var1, int var2) {
      for (int var3 = 0; var3 < this.field6008.length; var3++) {
         this.field6008[var3].field6027.method5963(var1, var2, (byte)79);
      }

      this.field6017.method5963(var1, var2, (byte)89);
      this.field6012.vmethod573(var1, var2, -1711046143);
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Lgy;)V")
   @ObfuscatedName("cn")
   public final void method10654(WorldView var1, classGY var2) {
      int var3 = var2.method5940(1261838927);
      int var4 = var2.method5944((byte)-10);
      if (!WorldView.method3745(var1, var3, var4, (byte)1)) {
         this.method10657(var2, (byte)-41);
      } else {
         int var5 = classGY.method5930(var2, 2076805807) - classGY.method5930(this.field6008[0].field6027, 1371718340);
         int var6 = var2.method5936(1690676705) - this.field6008[0].field6027.method5936(1690676705);
         method10660(this, var2, Math.abs(var5), Math.abs(var6), -959279101);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Lgy;)V")
   @ObfuscatedName("cf")
   public final void method10655(WorldView var1, classGY var2) {
      int var3 = var2.method5940(-2119487929);
      int var4 = var2.method5944((byte)24);
      if (!WorldView.method3745(var1, var3, var4, (byte)1)) {
         this.method10657(var2, (byte)0);
      } else {
         int var5 = classGY.method5930(var2, 1006591670) - classGY.method5930(this.field6008[0].field6027, 613593174);
         int var6 = var2.method5936(1690676705) - this.field6008[0].field6027.method5936(1690676705);
         method10660(this, var2, Math.abs(var5), Math.abs(var6), -2068936468);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)V")
   @ObfuscatedName("ee")
   public void method10659(classGY var1) {
      this.field6017.method5953(var1, -457105262);
      this.field6008[0].field6027.method5953(var1, 1130706594);
      this.field6009 = 0;
      this.field6013 = false;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cj")
   public final void method10665(int var1) {
      if (-1956329391 * this.field6009 == 0) {
         this.method10657(this.field6008[0].field6027, (byte)-123);
      } else {
         if (!this.field6013) {
            this.field6012.vmethod568(this.field6017, this.field6008[0], var1, (byte)2);
            this.field6013 = true;
         }

         if (this.field6012.vmethod571(this.field6017, var1, this.field6009 * -1956329391, 752947192)) {
            this.field6009 -= 1733918897;
            this.field6013 = false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;II)V")
   @ObfuscatedName("cw")
   void method10661(classGY var1, int var2, int var3) {
      if (-1956329391 * this.field6009 < 9) {
         this.field6009 += 1129391170;
      }

      for (int var4 = 1444063469 * this.field6009; var4 > 0; var4--) {
         classSR var5 = this.field6008[var4];
         this.field6008[var4] = this.field6008[var4 - 1];
         this.field6008[var4 - 1] = var5;
      }

      this.field6008[0].field6027.method5953(var1, -1712466395);
      this.field6008[0].field6028 = client.field856 * -1876258533;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)V")
   @ObfuscatedName("ch")
   public void method10672(classGY var1) {
      this.field6006 = false;
   }

   public int getTargetOrientation() {
      return this.field6009 * -1956329391 > 0 ? this.field6008[0].field6027.field2742 * 1859166197 : this.getOrientation();
   }

   @ObfuscatedSignature(descriptor = "(II)Luz;")
   @ObfuscatedName("ce")
   public classUZ method10669(int var1, int var2) {
      classUG var3 = classMQ.method7682(1074089605);
      classUU var4 = classRM.method10172(1778071523);
      classUL var5 = var3.field6303;
      int var7 = classGY.method5947(this.field6017, 1987525348);
      var7 &= 2047;
      float var6 = (float)(var7 / 2048.0F * (Math.PI * 2));
      var5.method11437(var6, 0.0F, 0.0F, 1088633836);
      classUZ.method11802(var3.field6305, classGY.method5930(this.field6017, 1494712558), 0.0F, this.field6017.method5936(1690676705), 2145348735);
      var4.method11706(var3, 1273531332);
      classUU.method11670(var4, 44537014);
      var3.method11364(159225740);
      classUZ var8 = classUU.method11680(var4, var1, 0.0F, var2, 2115726596);
      var8.method11823(method10634(this, -494658626), 0.0F, this.method10635(2056199487), -1062600558);
      var4.method11615(1723780482);
      return var8;
   }

   @ObfuscatedSignature(descriptor = "(Lkr;)V")
   @ObfuscatedName("bs")
   public void method10626(classKR var1) {
      this.field6019 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lgy;")
   @ObfuscatedName("bt")
   public classGY method10609() {
      return -1956329391 * this.field6009 == 0 ? this.field6017 : this.field6008[0].field6027;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cd")
   void method10642(int var1) {
      classQR.method9770(this.field6014, var1, -2125854107);
   }

   public LocalPoint transformToMainWorld(LocalPoint var1) {
      if (var1.getWorldView() != this.field6007 * -1807801405) {
         throw new IllegalArgumentException("LocalPoint doesn't belong do this WorldEntity");
      } else {
         classUZ var2 = this.method10679(var1.getX(), var1.getY());
         LocalPoint var3 = new LocalPoint((int)var2.field6425, (int)var2.field6424, 0);
         var2.method11861();
         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lqr;")
   @ObfuscatedName("bn")
   classQR method10596() {
      return 0 == -1902770053 * this.field6016 && this.field6015.method9772(374233424) && this.field6015.method9775((byte)16).method8820((byte)92)
         ? this.field6015
         : null;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cc")
   public void method10678(int var1, int var2) {
      this.method10648(var1, var2, 1658344131);
   }

   @ObfuscatedSignature(descriptor = "(Lsp;I)Lkr;")
   @ObfuscatedName("qz")
   public static classKR method10615(WorldEntity var0, int var1) {
      if (var0 == null) {
         var0.method10616(var1);
      }

      try {
         return var0.field6019;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;B)V")
   @ObfuscatedName("ar")
   static void method10671(String var0, String var1, String var2, byte var3) {
      try {
         classBF.field396 = var0;
         classBF.field397 = var1;
         classBF.field398 = var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "sp.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("te")
   @Override
   public int vmethod384() {
      return this.vmethod368(311606126);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bl")
   @Override
   public int vmethod385() {
      return this.vmethod371(-267974568);
   }

   @ObfuscatedSignature(descriptor = "(II)Luz;")
   @ObfuscatedName("wq")
   public classUZ method10679(int var1, int var2) {
      return this.method10666(var1, var2, (byte)49);
   }

   public boolean isHiddenForOverlap() {
      return this.method10601(1954640952);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("vv")
   @Override
   public int vmethod386() {
      return this.vmethod258((byte)-13);
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("im")
   @Override
   public float vmethod382() {
      return this.field6017.field2739 * -787916131;
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("si")
   @Override
   public float vmethod383() {
      return this.field6017.field2740 * -478154003;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("yk")
   public void method10674(int var1) {
      if (this.field6009 * -1956329391 == 0) {
         this.method10680(this.field6008[0].field6027);
      } else {
         if (!this.field6013) {
            if (this.field6006) {
               this.field6012.vmethod576(this.field6017, var1 - 1, this.field6009 * -1956329391);
            }

            this.field6012.method10687(this.field6017, this.field6008[0], var1);
            this.field6013 = true;
         }

         if (this.field6012.vmethod576(this.field6017, var1 + classMG.field4498, this.field6009 * -1956329391)) {
            this.field6009 = (this.field6009 * -1956329391 - 1) * 1733918897;
            this.field6013 = false;
         }

         this.field6006 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lkr;")
   @ObfuscatedName("aa")
   public classKR method10616(int var1) {
      try {
         return this.field6019;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)Lkw;")
   @ObfuscatedName("ao")
   public classKW method10621(boolean var1, int var2) {
      try {
         if (var1) {
            if (var2 == -1640531527) {
               throw new IllegalStateException();
            } else {
               return classKW.field4040;
            }
         } else {
            return this.field6024.method8520(2136997395);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("yn")
   public void method10629(int var1, int var2) {
      try {
         this.field6011 = -1165953645 * var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ad")
   public int method10639(int var1) {
      try {
         return this.field6010.field1708 * 377427776 + this.field6024.method8512((byte)-10);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sp.ad(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)V")
   @ObfuscatedName("cz")
   public void method10680(classGY var1) {
      this.method10657(var1, (byte)-117);
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ap")
   void method10643(int var1, byte var2) {
      try {
         classQR.method9770(this.field6014, var1, -2114326922);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sp.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;III)V")
   @ObfuscatedName("ak")
   void method10662(classGY var1, int var2, int var3, int var4) {
      try {
         if (-1956329391 * this.field6011 < 9) {
            if (var4 == 1222866659) {
               return;
            }

            this.field6009 = this.field6011 + 1733918897;
         }

         for (int var5 = -1956329391 * this.field6011; var5 > 0; var5--) {
            classSR var6 = this.field6008[var5];
            this.field6008[var5] = this.field6008[var5 - 1];
            this.field6008[var5 - 1] = var6;
         }

         this.field6008[0].field6027.method5953(var1, -167262994);
         this.field6008[0].field6028 = client.field1094 * -1876258533;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "sp.ak(" + ')');
      }
   }
}
