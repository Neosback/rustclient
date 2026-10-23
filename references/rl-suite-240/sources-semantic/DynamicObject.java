import javax.annotation.Nullable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dy")
public class DynamicObject extends Renderable implements net.runelite.api.DynamicObject {
   @ToRemove(unused = "true")
   @ObfuscatedName("bw")
   static final int field1680 = 54;
   @ObfuscatedName("pe")
   public boolean field1681;
   @ObfuscatedName("ab")
   final int field1675;
   @ObfuscatedName("as")
   final int field1674;
   @ObfuscatedName("ag")
   final int field1667;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   public static final int field1678 = 40;
   @ObfuscatedName("ax")
   final int field1671;
   @ObfuscatedName("ae")
   final int field1664;
   @ObfuscatedSignature(descriptor = "Lqr;")
   @ObfuscatedName("ac")
   final classQR field1668;
   @ObfuscatedName("kb")
   public int field1666;
   @ObfuscatedName("zf")
   public int field1663 = -1;
   @ObfuscatedSignature(descriptor = "Lez;")
   @ObfuscatedName("cu")
   public Scene field1665;
   @ObfuscatedSignature(descriptor = "Lqr;")
   @ObfuscatedName("aa")
   final classQR field1672;
   @ObfuscatedName("ao")
   int field1670;
   @ObfuscatedName("af")
   final int field1669;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   public static final int field1677 = 99;
   @ObfuscatedName("aq")
   static int field1676;
   @ObfuscatedSignature(descriptor = "Ldz;")
   @ObfuscatedName("az")
   final WorldView field1673;
   @ObfuscatedName("ld")
   static byte[][] field1682;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   public static final int field1679 = 32;

   public int getAnimCycle() {
      return -1;
   }

   public int getAnimFrame() {
      return this.field1668.field5600 * 292569817;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ar")
   @Override
   protected boolean vmethod97() {
      ObjectComposition var1 = classMU.method7771(this.field1675 * 1680132843, 1765271651);
      if (null != var1.field4928) {
         var1 = ObjectComposition.method8585(var1, 1772412596);
      }

      if (null == var1) {
         return false;
      } else if (this.field1668.method9772(374233424) && this.field1668.method9775((byte)39).method8786(this.field1668.method9780(1809600475), 1284412146)) {
         return true;
      } else if (this.field1672.method9772(374233424) && this.field1672.method9775((byte)111).method8786(this.field1672.method9780(2072772326), 1284412146)) {
         return true;
      } else {
         Model var2 = var1.method8630(this.field1669 * 480644337, -1904611005 * this.field1667, (int[][])null, 0, 0, 0, null, -1, -1135264680);
         return var2 != null && null != var2.field2442;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lom;")
   @ObfuscatedName("it")
   public ObjectComposition method3731() {
      ObjectComposition var1 = ObjectComposition.method8638(this.field1675 * 1680132843);
      if (var1.field4928 != null) {
         var1 = this.method3725(var1);
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("ae")
   @Override
   protected final Model vmethod104(int var1) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         this.method3726();
         return this.method3732(rl21.field5726, this.field1668);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   @Override
   protected boolean vmethod95(int var1) {
      try {
         ObjectComposition var2 = classMU.method7771(this.field1675 * 1680132843, 1790166623);
         if (null != var2.field4928) {
            var2 = ObjectComposition.method8585(var2, -1407992321);
         }

         if (null == var2) {
            if (var1 == -702405562) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            if (this.field1668.method9772(374233424)) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               if (this.field1668.method9775((byte)74).method8786(this.field1668.method9780(1905034411), 1284412146)) {
                  if (var1 == -702405562) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }

            if (this.field1672.method9772(374233424)) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               if (this.field1672.method9775((byte)53).method8786(this.field1672.method9780(1942594289), 1284412146)) {
                  if (var1 == -702405562) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }

            Model var3 = var2.method8630(this.field1669 * 480644337, -1904611005 * this.field1667, (int[][])null, 0, 0, 0, null, -1, -1135264680);
            if (var3 != null) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               if (null != var3.field2442) {
                  if (var1 == -702405562) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }

            return false;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dy.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ad")
   @Override
   protected final Model vmethod92() {
      ObjectComposition var1 = classMU.method7771(this.field1675 * 1680132843, 2028646052);
      if (null != var1.field4928) {
         var1 = ObjectComposition.method8585(var1, -815248374);
      }

      if (null == var1) {
         return null;
      } else {
         int var2;
         int var3;
         if (1 != -1904611005 * this.field1667 && 595445699 * this.field1667 != 3) {
            var2 = -582306509 * var1.field4942;
            var3 = var1.field4922 * 642445882;
         } else {
            var2 = var1.field4922 * 564213346;
            var3 = var1.field4942 * -582306509;
         }

         int var4 = (var2 >> 1) + 1026205107 * this.field1671;
         int var5 = 1772309092 * this.field1671 + (var2 + 1 >> 1);
         int var6 = -2058575863 * this.field1664 + (var3 >> 1);
         int var7 = (var3 + 1 >> 1) + -2058575863 * this.field1664;
         int[][] var8 = this.field1673.field1686[1159378693 * this.field1674];
         int var9 = var8[var4][var7] + var8[var5][var6] + var8[var4][var6] + var8[var5][var7] >> 2;
         int var10 = (1230521602 * this.field1671 << 7) + (var2 << 6);
         int var11 = (-2058575863 * this.field1664 << 7) + (var3 << 6);
         int var12 = client.field856 * 1612595797 - this.field1670 * 1216078950;
         this.field1670 = client.field856 * -474013645;
         classAT.field277.method280(this.field1673, var10, var11, false, -2144027360);
         int var13 = classRD.method9980(this.field1668, var12, classAT.field277, -2003029019);
         classAT.field277.method284(1718019056);
         if ((var13 & 1) != 0 && (var13 & 8) != 0) {
            classQR.method9765(this.field1668, 1298913438);
         }

         if (this.field1668.method9805(30, -65318458)) {
            if (this.field1672.method9772(374233424)) {
               int var14 = classRD.method9980(this.field1672, var12, null, -926798770);
               if (0 != (var14 & 1) && (var14 & 8) != 0) {
                  classQR.method9765(this.field1672, -1829889297);
               }

               if (!this.field1672.method9805(30, -2124200801)) {
                  return var1.method8630(
                     480644337 * this.field1669,
                     this.field1667 * -1904611005,
                     var8,
                     var10,
                     var9,
                     var11,
                     this.field1672.method9775((byte)108),
                     this.field1672.method9780(2083438286),
                     -1135264680
                  );
               }
            }

            return null;
         } else {
            return var1.method8630(
               this.field1669 * 480644337,
               this.field1667 * -1904611005,
               var8,
               var10,
               var9,
               var11,
               this.field1668.method9775((byte)34),
               this.field1668.method9780(1823620257),
               -1135264680
            );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   @Override
   protected int vmethod101() {
      classQR var1 = this.field1668;
      if (null == var1) {
         var1 = this.field1672;
      }

      if (null == var1) {
         return 0;
      } else {
         return var1.method9772(374233424) ? var1.method9775((byte)107).field5136 * -1989858266 : 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ly")
   public Model method3729() {
      return this.field1668.field5597 != null ? null : this.method3732(null, this.field1668);
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("wl")
   public Model method3728() {
      return this.method3732(null, classQR.field5601);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fy")
   public void method3727() {
      ObjectComposition var1 = ObjectComposition.method8638(this.field1675 * 1680132843);
      if (var1.field4928 != null) {
         if (var1.field4924 * 1087117325 != -1) {
            this.field1663 = client.method1859(var1.field4924 * 1087117325);
            this.field1666 = classLB.field4088[this.field1663];
         } else if (var1.field4960 * -1047341117 != -1) {
            this.field1663 = var1.field4960 * -1047341117;
            this.field1666 = classLB.field4088[this.field1663];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;Lqr;)Lfx;")
   @ObfuscatedName("gw")
   public Model method3732(@Nullable rl21 param1, classQR nullx) {
      ObjectComposition var3 = ObjectComposition.method8638(this.field1675 * 1680132843);
      if (var3.field4928 != null) {
         var3 = this.method3725(var3);
      }

      if (var3 == null) {
         return null;
      } else {
         int var4;
         int var5;
         if (this.field1667 * -1904611005 != 1 && this.field1667 * -1904611005 != 3) {
            var4 = var3.field4942 * -582306509;
            var5 = var3.field4922 * 402876423;
         } else {
            var4 = var3.field4922 * 402876423;
            var5 = var3.field4942 * -582306509;
         }

         int var6 = this.field1671 * 1026205107 + this.field1665.field2146;
         int var7 = this.field1664 * -2058575863 + this.field1665.field2146;
         int var8 = (var4 >> 1) + var6;
         int var9 = (var4 + 1 >> 1) + var6;
         int var10 = (var5 >> 1) + var7;
         int var11 = (var5 + 1 >> 1) + var7;
         int[][] var12 = this.field1665.field2058[this.field1674 * 1513770239];
         int var13 = var12[var8][var11] + var12[var8][var10] + var12[var9][var10] + var12[var9][var11] >> 2;
         int var14 = (var6 << 7) + (var4 << 6);
         int var15 = (var7 << 7) + (var5 << 6);
         int var16;
         if (client.field1115 != null && nullx.field5596 * 1684838611 != -1 && client.field1115.test(nullx.field5596 * 1684838611)) {
            var16 = -2147483648 | Math.max(nullx.field5602 * -1399668821 - 1, 0) << 16 | nullx.field5600 * 292569817;
         } else {
            var16 = nullx.field5600 * 292569817;
         }

         return var3.method8629(var1, this.field1669 * 480644337, this.field1667 * -1904611005, var12, var14, var13, var15, nullx.field5597, var16);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ap")
   @Override
   protected final Model vmethod93() {
      ObjectComposition var1 = classMU.method7771(this.field1675 * 918486231, 1726862138);
      if (null != var1.field4928) {
         var1 = ObjectComposition.method8585(var1, -725568927);
      }

      if (null == var1) {
         return null;
      } else {
         int var2;
         int var3;
         if (1 != -1904611005 * this.field1667 && 1232529933 * this.field1667 != 3) {
            var2 = -582306509 * var1.field4942;
            var3 = var1.field4922 * 383180666;
         } else {
            var2 = var1.field4922 * 1926398856;
            var3 = var1.field4942 * 1687510084;
         }

         int var4 = (var2 >> 1) + -712724519 * this.field1671;
         int var5 = -1826158038 * this.field1671 + (var2 + 1 >> 1);
         int var6 = -2058575863 * this.field1664 + (var3 >> 1);
         int var7 = (var3 + 1 >> 1) + -982825143 * this.field1664;
         int[][] var8 = this.field1673.field1686[1513770239 * this.field1674];
         int var9 = var8[var4][var7] + var8[var5][var6] + var8[var4][var6] + var8[var5][var7] >> 2;
         int var10 = (1026205107 * this.field1671 << 7) + (var2 << 6);
         int var11 = (-2058575863 * this.field1664 << 7) + (var3 << 6);
         int var12 = client.field856 * 1612595797 - this.field1670 * 1618438999;
         this.field1670 = client.field856 * -1037089410;
         classAT.field277.method280(this.field1673, var10, var11, false, 1418461139);
         int var13 = classRD.method9980(this.field1668, var12, classAT.field277, -870496540);
         classAT.field277.method284(-1483083354);
         if ((var13 & 1) != 0 && (var13 & 8) != 0) {
            classQR.method9765(this.field1668, -927386427);
         }

         if (this.field1668.method9805(30, -836391410)) {
            if (this.field1672.method9772(374233424)) {
               int var14 = classRD.method9980(this.field1672, var12, null, -1346755143);
               if (0 != (var14 & 1) && (var14 & 8) != 0) {
                  classQR.method9765(this.field1672, -114479556);
               }

               if (!this.field1672.method9805(30, -1952796173)) {
                  return var1.method8630(
                     480644337 * this.field1669,
                     this.field1667 * -1904611005,
                     var8,
                     var10,
                     var9,
                     var11,
                     this.field1672.method9775((byte)117),
                     this.field1672.method9780(1893974363),
                     -1135264680
                  );
               }
            }

            return null;
         } else {
            return var1.method8630(
               this.field1669 * 497545433,
               this.field1667 * -2104311888,
               var8,
               var10,
               var9,
               var11,
               this.field1668.method9775((byte)40),
               this.field1668.method9780(2146529707),
               -1135264680
            );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("sl")
   public boolean method3730() {
      return this.field1668.field5597 == null;
   }

   @ObfuscatedSignature(descriptor = "()Lou;")
   @ObfuscatedName("sr")
   public Animation method3724() {
      return this.field1668.field5597;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   @Override
   protected boolean vmethod96() {
      ObjectComposition var1 = classMU.method7771(this.field1675 * 1214763282, 1521515488);
      if (null != var1.field4928) {
         var1 = ObjectComposition.method8585(var1, -1725007026);
      }

      if (null == var1) {
         return false;
      } else if (this.field1668.method9772(374233424) && this.field1668.method9775((byte)63).method8786(this.field1668.method9780(1914883703), 1284412146)) {
         return true;
      } else if (this.field1672.method9772(374233424) && this.field1672.method9775((byte)49).method8786(this.field1672.method9780(2088283637), 1284412146)) {
         return true;
      } else {
         Model var2 = var1.method8630(this.field1669 * -500462189, 540706257 * this.field1667, (int[][])null, 0, 0, 0, null, -1, -1135264680);
         return var2 != null && null != var2.field2442;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ai")
   @Override
   protected boolean vmethod94() {
      ObjectComposition var1 = classMU.method7771(this.field1675 * 1680132843, 1243378607);
      if (null != var1.field4928) {
         var1 = ObjectComposition.method8585(var1, 427100777);
      }

      if (null == var1) {
         return false;
      } else if (this.field1668.method9772(374233424) && this.field1668.method9775((byte)89).method8786(this.field1668.method9780(1851262562), 1284412146)) {
         return true;
      } else if (this.field1672.method9772(374233424) && this.field1672.method9775((byte)25).method8786(this.field1672.method9780(2101440328), 1284412146)) {
         return true;
      } else {
         Model var2 = var1.method8630(this.field1669 * 1029319934, -1904611005 * this.field1667, (int[][])null, 0, 0, 0, null, -1, -1135264680);
         return var2 != null && null != var2.field2442;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   @Override
   protected int vmethod98(int var1) {
      try {
         classQR var2 = this.field1668;
         if (null == var2) {
            if (var1 <= -1498749565) {
               throw new IllegalStateException();
            }

            var2 = this.field1672;
         }

         if (null == var2) {
            return 0;
         } else {
            return var2.method9772(374233424) ? var2.method9775((byte)88).field5136 * -687960895 : 0;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dy.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   @Override
   protected int vmethod99() {
      classQR var1 = this.field1668;
      if (null == var1) {
         var1 = this.field1672;
      }

      if (null == var1) {
         return 0;
      } else {
         return var1.method9772(374233424) ? var1.method9775((byte)127).field5136 * -687960895 : 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   @Override
   protected int vmethod100() {
      classQR var1 = this.field1668;
      if (null == var1) {
         var1 = this.field1672;
      }

      if (null == var1) {
         return 0;
      } else {
         return var1.method9772(374233424) ? var1.method9775((byte)7).field5136 * -687960895 : 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;)Lfx;")
   @ObfuscatedName("cl")
   public Model method3723(@Nullable rl21 param1) {
      return this.method3732(var1, this.field1668);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("en")
   public void method3726() {
      ObjectComposition var1 = ObjectComposition.method8638(this.field1675 * 1680132843);
      if (var1.field4928 != null) {
         var1 = this.method3725(var1);
      }

      if (var1 != null) {
         int var2;
         int var3;
         if (this.field1667 * -1904611005 != 1 && this.field1667 * -1904611005 != 3) {
            var2 = var1.field4942 * -582306509;
            var3 = var1.field4922 * 402876423;
         } else {
            var2 = var1.field4922 * 402876423;
            var3 = var1.field4942 * -582306509;
         }

         int var4 = (this.field1671 * 1026205107 << 7) + (var2 << 6);
         int var5 = (this.field1664 * -2058575863 << 7) + (var3 << 6);
         int var6 = client.field856 * 1612595797 - this.field1670 * 1618438999;
         this.field1670 = client.field856 * 1612595797 * 1780002919;
         classAT.field277.method289(this.field1673, var4, var5, false);
         int var7 = Animation.method8832(this.field1668, var6, classAT.field277);
         classAT.field277.method288();
         if ((var7 & 1) != 0 && (var7 & 8) != 0) {
            this.field1668.method9826();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   @Override
   protected int vmethod103() {
      classQR var1 = this.field1668;
      if (null == var1) {
         var1 = this.field1672;
      }

      if (null == var1) {
         return 0;
      } else {
         return var1.method9772(374233424) ? var1.method9775((byte)93).field5136 * -687960895 : 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lom;)Lom;")
   @ObfuscatedName("wi")
   public ObjectComposition method3725(ObjectComposition var1) {
      if (this.field1663 == -1) {
         return null;
      } else {
         int var2 = -1;
         if (var1.field4924 * 1087117325 != -1) {
            var2 = client.method2377(var1.field4924 * 1087117325, this.field1666);
         } else if (var1.field4960 * -1047341117 != -1) {
            var2 = this.field1666;
         }

         int var3;
         if (var2 >= 0 && var2 < var1.field4928.length - 1) {
            var3 = var1.field4928[var2];
         } else {
            var3 = var1.field4928[var1.field4928.length - 1];
         }

         return var3 != -1 ? ObjectComposition.method8638(var3) : null;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   @Override
   protected int vmethod102() {
      classQR var1 = this.field1668;
      if (null == var1) {
         var1 = this.field1672;
      }

      if (null == var1) {
         return 0;
      } else {
         return var1.method9772(374233424) ? var1.method9775((byte)22).field5136 * -687960895 : 0;
      }
   }

   DynamicObject(WorldView var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, Renderable var10) {
      this.field1665 = var1.field1687;
      if (var10 instanceof DynamicObject) {
         this.field1663 = ((DynamicObject)var10).field1663;
         this.field1666 = ((DynamicObject)var10).field1666;
      }

      this.field1673 = var1;
      this.field1675 = 1126337987 * var2;
      this.field1669 = -962894831 * var3;
      this.field1667 = -704371861 * var4;
      this.field1674 = 1436332799 * var5;
      this.field1671 = var6 * 884511099;
      this.field1664 = -437719495 * var7;
      this.field1668 = new classQR();
      this.field1672 = new classQR();
      if (var8 != -1) {
         classQR.method9770(this.field1668, var8, -2084590414);
         classQR.method9770(this.field1672, var8, -2138286862);
         Animation var11 = this.field1668.method9775((byte)124);
         this.field1670 = -474013645 * client.field856 - 1780002919;
         if (var9 && 1665914959 * var11.field5132 != -1) {
            this.field1668.method9818((byte)0);
         }

         if (var10 instanceof DynamicObject) {
            DynamicObject var12 = (DynamicObject)var10;
            if (var12.field1668.method9776(500216846) == var8) {
               if (0 == 1697446663 * var11.field5120) {
                  this.field1668.method9761(var12.field1668, (byte)20);
                  this.field1670 = var12.field1670 * 1;
               }

               this.field1672.method9761(var12.field1672, (byte)-13);
            } else if (!var12.field1668.method9807((byte)-30)) {
               this.field1672.method9761(var12.field1672, (byte)87);
            } else {
               this.field1672.method9761(var12.field1668, (byte)40);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([JIII)V")
   @ObfuscatedName("bc")
   static void method3722(long[] var0, int var1, int var2, int var3) {
      try {
         while (var1 < var2) {
            if (var3 <= -117964552) {
               throw new IllegalStateException();
            }

            long var4 = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var4;
            var1++;
            var2--;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "dy.bc(" + ')');
      }
   }
}
