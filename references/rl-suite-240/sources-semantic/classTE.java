import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("te")
public class classTE {
   @ObfuscatedName("ao")
   public boolean field6131;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6127 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field6128 = 3;
   @ObfuscatedName("aa")
   public final boolean field6122;
   @ObfuscatedSignature(descriptor = "Lyy;")
   @ObfuscatedName("ac")
   public final classYY field6126;
   @ObfuscatedName("ad")
   public long[] field6125;
   @ObfuscatedName("aj")
   public int field6129;
   @ObfuscatedName("al")
   public int field6121;
   @ObfuscatedName("ay")
   Object field6120;
   @ObfuscatedName("aq")
   int[] field6124;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field6130 = 5000;
   @ObfuscatedName("ap")
   public Object[] field6123;

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public final int method10797(int var1) {
      try {
         return this.field6129 * -1943548931;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "te.ab(" + 41);
      }
   }

   public classTE(classYY var1, Object var2, int var3, int var4) {
      this.field6122 = true;
      this.field6126 = var1;
      this.field6121 = var4 * 404453943;
      this.field6120 = var2;
      if (classYY.field7109 == var1) {
         this.field6124 = new int[var4];
      } else if (classYY.field7113 == var1) {
         this.field6125 = new long[var4];
      } else if (var1 == classYY.field7111) {
         this.field6123 = new String[var4];
      }

      this.method10828(var3, (byte)2);
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("az")
   public final int[] method10791(int var1) {
      try {
         return this.field6124;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "te.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[J")
   @ObfuscatedName("af")
   public final long[] method10793(int var1) {
      try {
         return this.field6125;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "te.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Ljava/lang/Object;")
   @ObfuscatedName("ae")
   public final Object[] method10796(int var1) {
      try {
         return this.field6123;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "te.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;I)V")
   @ObfuscatedName("as")
   final void method10807(int var1, Object var2, int var3) {
      try {
         if (classYY.field7109 == this.field6126) {
            if (var3 == -764360928) {
               throw new IllegalStateException();
            }

            this.field6124[var1] = (Integer)var2;
         } else if (this.field6126 == classYY.field7113) {
            if (var3 == -764360928) {
               return;
            }

            this.field6125[var1] = (Long)var2;
         } else {
            this.field6123[var1] = var2;
         }

         this.field6131 = true;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "te.as(" + ')');
      }
   }

   public classTE(classYY var1, boolean var2) {
      this.field6126 = var1;
      this.field6122 = var2;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ba")
   final void method10813(int var1) {
      if (!this.field6122) {
         throw new RuntimeException();
      } else if (var1 >= 0 && var1 <= 1205727595) {
         if (var1 > this.field6121 * 922180487) {
            this.method10825(method10822(this, var1, 120041229), -483624883);
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;)V")
   @ObfuscatedName("br")
   final void method10808(int var1, Object var2) {
      if (classYY.field7109 == this.field6126) {
         this.field6124[var1] = (Integer)var2;
      } else if (this.field6126 == classYY.field7113) {
         this.field6125[var1] = (Long)var2;
      } else {
         this.field6123[var1] = var2;
      }

      this.field6131 = true;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("ar")
   public final int[] method10792() {
      return this.field6124;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([I)Lte;")
   @ObfuscatedName("bz")
   public static classTE method10837(int[] var0) {
      classTE var1 = new classTE(classYY.field7109, false);
      var1.field6124 = var0;
      var1.field6129 = var0.length * -605546848;
      var1.field6121 = var0.length * -534213669;
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)Lte;")
   @ObfuscatedName("bv")
   static classTE method10833(String[] var0) {
      classTE var1 = new classTE(classYY.field7111, true);
      var1.field6123 = var0;
      var1.field6129 = -1063196331 * var0.length;
      var1.field6121 = var0.length * 404453943;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()[J")
   @ObfuscatedName("av")
   public final long[] method10794() {
      return this.field6125;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   public final int method10798() {
      return this.field6129 * -1943548931;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Object;")
   @ObfuscatedName("bn")
   final Object method10800(int var1) {
      if (classYY.field7109 == this.field6126) {
         return this.field6124[var1];
      } else {
         return classYY.field7113 == this.field6126 ? this.field6125[var1] : this.field6123[var1];
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Object;")
   @ObfuscatedName("bx")
   final Object method10801(int var1) {
      if (classYY.field7109 == this.field6126) {
         return this.field6124[var1];
      } else {
         return classYY.field7113 == this.field6126 ? this.field6125[var1] : this.field6123[var1];
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lym;I)V")
   @ObfuscatedName("ma")
   public static void method10846(SpritePixels var0, int var1) {
      if (var0 == null) {
         var0.method13613(var1);
      }

      if (var0.field7005 != var0.field7004 || var0.field7007 != var0.field7006) {
         int var2 = var1;
         if (var1 > var0.field7008) {
            var2 = var0.field7008;
         }

         int var3 = var1;
         if (var1 + var0.field7008 + var0.field7005 > var0.field7004) {
            var3 = var0.field7004 - var0.field7008 - var0.field7005;
         }

         int var4 = var1;
         if (var1 > var0.field7009) {
            var4 = var0.field7009;
         }

         int var5 = var1;
         if (var1 + var0.field7009 + var0.field7007 > var0.field7006) {
            var5 = var0.field7006 - var0.field7009 - var0.field7007;
         }

         int var6 = var0.field7005 + var2 + var3;
         int var7 = var0.field7007 + var4 + var5;
         int[] var8 = new int[var6 * var7];

         for (int var9 = 0; var9 < var0.field7007; var9++) {
            for (int var10 = 0; var10 < var0.field7005; var10++) {
               var8[(var9 + var4) * var6 + var10 + var2] = var0.field7003[var9 * var0.field7005 + var10];
            }
         }

         var0.field7003 = var8;
         var0.field7005 = var6;
         var0.field7007 = var7;
         var0.field7008 -= var2;
         var0.field7009 -= var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;Z)Lte;")
   @ObfuscatedName("cl")
   public static classTE method10843(classXY var0, boolean var1) {
      int var2 = var0.method13099(-415805025);
      if (var2 >= 0 && var2 <= 5000) {
         int var3 = var0.method13095(-346779531);
         if (var3 == 0 || 1 == var3) {
            int[] var9 = new int[var2];

            for (int var13 = 0; var13 < var2; var13++) {
               var9[var13] = classXY.method13113(var0, (byte)1);
            }

            return var1 ? classKH.method6512(var9, (byte)116) : classDG.method3085(var9, -530642757);
         } else if (var3 == 2 || var3 == 3) {
            long[] var8 = new long[var2];

            for (int var11 = 0; var11 < var2; var11++) {
               var8[var11] = classXY.method13115(var0, -299072268);
            }

            classTE var12;
            if (var1) {
               var12 = classEK.method3929(var8, (byte)49);
            } else {
               classTE var14 = new classTE(classYY.field7113, false);
               var14.field6125 = var8;
               var14.field6129 = -1063196331 * var8.length;
               var14.field6121 = var8.length * 404453943;
               var12 = var14;
            }

            return var12;
         } else if (var3 == 4) {
            String[] var4 = new String[var2];

            for (int var5 = 0; var5 < var2; var5++) {
               var4[var5] = var0.method13127(2029779499);
            }

            classTE var10;
            if (var1) {
               var10 = SceneTilePaint.method4823(var4, 325060923);
            } else {
               classTE var7 = new classTE(classYY.field7111, false);
               var7.field6123 = var4;
               var7.field6129 = var4.length * -1063196331;
               var7.field6121 = var4.length * 404453943;
               var10 = var7;
            }

            return var10;
         } else if (var3 == 255) {
            return null;
         } else {
            throw new RuntimeException();
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Object;")
   @ObfuscatedName("bp")
   final Object method10802(int var1) {
      if (classYY.field7109 == this.field6126) {
         return this.field6124[var1];
      } else {
         return classYY.field7113 == this.field6126 ? this.field6125[var1] : this.field6123[var1];
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lte;I)V")
   @ObfuscatedName("tg")
   public static void method10809(classTE var0, int var1) {
      if (var0 == null) {
         var0.method10797(var1);
      }

      var0.method10817(var1, 1733599349);
      var0.method10828(var1, (byte)2);
      var0.field6131 = true;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([I)Lte;")
   @ObfuscatedName("bq")
   public static classTE method10829(int[] var0) {
      classTE var1 = new classTE(classYY.field7109, true);
      var1.field6124 = var0;
      var1.field6129 = var0.length * -1063196331;
      var1.field6121 = var0.length * 404453943;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lte;II)V")
   @ObfuscatedName("lb")
   public static void method10810(classTE var0, int var1, int var2) {
      if (var0 == null) {
         var0.method10827(var1, var1);
      } else {
         try {
            var0.method10817(var1, 472983705);
            var0.method10828(var1, (byte)2);
            var0.field6131 = true;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "te.ax(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bm")
   public final void method10811(int var1) {
      this.method10817(var1, 1423762563);
      this.method10828(var1, (byte)2);
      this.field6131 = true;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bg")
   public final void method10812(int var1) {
      this.method10817(var1, 140117236);
      this.method10828(var1, (byte)2);
      this.field6131 = true;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bj")
   final void method10814(int var1) {
      if (!this.field6122) {
         throw new RuntimeException();
      } else if (var1 >= 0 && var1 <= 5000) {
         if (var1 > this.field6121 * 922180487) {
            this.method10825(method10822(this, var1, 120041229), -483624883);
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;IB)Ljava/lang/Object;")
   @ObfuscatedName("zi")
   public static Object method10803(classTE var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method10806(var1, var2);
      }

      try {
         if (classYY.field7109 == var0.field6126) {
            if (var2 != -1) {
               throw new IllegalStateException();
            } else {
               return var0.field6124[var1];
            }
         } else if (classYY.field7113 == var0.field6126) {
            if (var2 != -1) {
               throw new IllegalStateException();
            } else {
               return var0.field6125[var1];
            }
         } else {
            return var0.field6123[var1];
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "te.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lte;I)Ljava/lang/Object;")
   @ObfuscatedName("ps")
   public static Object method10804(classTE var0, int var1) {
      if (classYY.field7109 == var0.field6126) {
         return var0.field6124[var1];
      } else {
         return classYY.field7113 == var0.field6126 ? var0.field6125[var1] : var0.field6123[var1];
      }
   }

   @ObfuscatedSignature(descriptor = "()[J")
   @ObfuscatedName("ak")
   public final long[] method10795() {
      return this.field6125;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bo")
   final void method10815(int var1) {
      if (!this.field6122) {
         throw new RuntimeException();
      } else if (var1 >= 0 && var1 <= 5000) {
         if (var1 > this.field6121 * 922180487) {
            this.method10825(method10822(this, var1, 120041229), -483624883);
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("al")
   final void method10828(int var1, byte var2) {
      try {
         int var3 = this.field6129 * -1943548931;
         this.field6129 = var1 * -1063196331;
         if (var1 < var3) {
            if (var2 != 2) {
               throw new IllegalStateException();
            }

            if (classYY.field7109 == this.field6126) {
               if (var2 != 2) {
                  throw new IllegalStateException();
               }

               Arrays.fill(this.field6124, var1, var3, 0);
            } else if (classYY.field7113 == this.field6126) {
               Arrays.fill(this.field6125, var1, var3, 0L);
            } else {
               Arrays.fill(this.field6123, var1, var3, null);
            }
         } else if (var1 > var3) {
            if (this.field6126 == classYY.field7109) {
               if (var2 != 2) {
                  throw new IllegalStateException();
               }

               int var4 = (Integer)this.field6120;
               if (0 != var4) {
                  if (var2 != 2) {
                     throw new IllegalStateException();
                  }

                  Arrays.fill(this.field6124, var3, var1, var4);
               }
            } else if (classYY.field7113 == this.field6126) {
               if (var2 != 2) {
                  throw new IllegalStateException();
               }

               long var7 = (Long)this.field6120;
               if (var7 != 0L) {
                  if (var2 != 2) {
                     throw new IllegalStateException();
                  }

                  Arrays.fill(this.field6125, var3, var1, var7);
               }
            } else if (null != this.field6120) {
               if (var2 != 2) {
                  throw new IllegalStateException();
               }

               Arrays.fill(this.field6123, var3, var1, this.field6120);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "te.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)V")
   @ObfuscatedName("au")
   public void method10840(classXY var1, byte var2) {
      try {
         var1.method13031(-1943548931 * this.field6129, -1542382992);
         if (classYY.field7109 == this.field6126) {
            if (var2 == -73) {
               return;
            }

            byte var10001;
            if (null != this.field6120 && (Integer)this.field6120 == -1) {
               if (var2 == -73) {
                  throw new IllegalStateException();
               }

               var10001 = 1;
            } else {
               var10001 = 0;
            }

            classXY.method13029(var1, var10001, (byte)-94);

            for (int var3 = 0; var3 < -1943548931 * this.field6129; var3++) {
               var1.method13037(this.field6124[var3], 1710743860);
            }
         } else if (this.field6126 == classYY.field7113) {
            if (var2 == -73) {
               throw new IllegalStateException();
            }

            classXY.method13029(var1, this.field6120 != null && (Long)this.field6120 == -1L ? 3 : 2, (byte)-13);

            for (int var5 = 0; var5 < this.field6129 * -1943548931; var5++) {
               classXY.method13047(var1, this.field6125[var5]);
            }
         } else if (classYY.field7111 == this.field6126) {
            if (var2 == -73) {
               throw new IllegalStateException();
            }

            classXY.method13029(var1, 4, (byte)-30);

            for (int var6 = 0; var6 < -1943548931 * this.field6129; var6++) {
               if (var2 == -73) {
                  throw new IllegalStateException();
               }

               var1.method13054((String)this.field6123[var6], (short)25085);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "te.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bi")
   int method10820(int var1) {
      int var2 = (this.field6121 * 922180487 >> 1) + this.field6121 * 922180487;
      return Math.max(var1, Math.min(var2, 5000));
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bw")
   int method10821(int var1) {
      int var2 = (this.field6121 * 922180487 >> 1) + this.field6121 * 922180487;
      return Math.max(var1, Math.min(var2, 5000));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([I)Lte;")
   @ObfuscatedName("by")
   public static classTE method10830(int[] var0) {
      classTE var1 = new classTE(classYY.field7109, true);
      var1.field6124 = var0;
      var1.field6129 = var0.length * -1063196331;
      var1.field6121 = var0.length * 404453943;
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)Lte;")
   @ObfuscatedName("bl")
   static classTE method10834(String[] var0) {
      classTE var1 = new classTE(classYY.field7111, true);
      var1.field6123 = var0;
      var1.field6129 = 1956881527 * var0.length;
      var1.field6121 = var0.length * -1221531616;
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lte;I)Ljava/lang/Object;")
   @ObfuscatedName("jb")
   public static Object method10805(classTE var0, int var1) {
      if (classYY.field7109 == var0.field6126) {
         return var0.field6124[var1];
      } else {
         return classYY.field7113 == var0.field6126 ? var0.field6125[var1] : var0.field6123[var1];
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([J)Lte;")
   @ObfuscatedName("bf")
   static classTE method10831(long[] var0) {
      classTE var1 = new classTE(classYY.field7113, true);
      var1.field6125 = var0;
      var1.field6129 = var0.length * -1063196331;
      var1.field6121 = var0.length * 404453943;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lte;II)I")
   @ObfuscatedName("no")
   public static int method10822(classTE var0, int var1, int var2) {
      if (var0 == null) {
         var0.method10824(var1, var1);
      }

      try {
         int var3 = (var0.field6121 * 922180487 >> 1) + var0.field6121 * 922180487;
         return Math.max(var1, Math.min(var3, 5000));
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "te.aa(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([J)Lte;")
   @ObfuscatedName("bs")
   static classTE method10832(long[] var0) {
      classTE var1 = new classTE(classYY.field7113, true);
      var1.field6125 = var0;
      var1.field6129 = var0.length * -1063196331;
      var1.field6121 = var0.length * 404453943;
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)Lte;")
   @ObfuscatedName("bd")
   static classTE method10835(String[] var0) {
      classTE var1 = new classTE(classYY.field7111, true);
      var1.field6123 = var0;
      var1.field6129 = -1063196331 * var0.length;
      var1.field6121 = var0.length * 404453943;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bb")
   int method10823(int var1) {
      int var2 = (this.field6121 * 922180487 >> 1) + this.field6121 * 922180487;
      return Math.max(var1, Math.min(var2, 5000));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lte;I)V")
   @ObfuscatedName("jr")
   public static void method10816(classTE var0, int var1) {
      if (var0 == null) {
         var0.method10819(var1);
      }

      if (!var0.field6122) {
         throw new RuntimeException();
      } else if (var1 >= 0 && var1 <= 5000) {
         if (var1 > var0.field6121 * 922180487) {
            var0.method10825(method10822(var0, var1, 120041229), -483624883);
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ac")
   final void method10817(int var1, int var2) {
      try {
         if (!this.field6122) {
            if (var2 <= 107806170) {
               throw new IllegalStateException();
            } else {
               throw new RuntimeException();
            }
         } else {
            if (var1 >= 0) {
               if (var2 <= 107806170) {
                  throw new IllegalStateException();
               }

               if (var1 <= 5000) {
                  if (var1 > this.field6121 * 922180487) {
                     if (var2 <= 107806170) {
                        throw new IllegalStateException();
                     }

                     this.method10825(method10822(this, var1, 120041229), -483624883);
                  }

                  return;
               }

               if (var2 <= 107806170) {
                  throw new IllegalStateException();
               }
            }

            throw new RuntimeException();
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "te.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)Lte;")
   @ObfuscatedName("bh")
   static classTE method10836(String[] var0) {
      classTE var1 = new classTE(classYY.field7111, true);
      var1.field6123 = var0;
      var1.field6129 = -1063196331 * var0.length;
      var1.field6121 = var0.length * 404453943;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ao")
   void method10825(int var1, int var2) {
      try {
         if (this.field6121 * 922180487 != var1) {
            if (var2 != -483624883) {
               throw new IllegalStateException();
            }

            this.field6121 = var1 * 404453943;
            if (this.field6126 == classYY.field7109) {
               if (var2 != -483624883) {
                  return;
               }

               this.field6124 = Arrays.copyOf(this.field6124, var1);
            } else if (this.field6126 == classYY.field7113) {
               if (var2 != -483624883) {
                  throw new IllegalStateException();
               }

               this.field6125 = Arrays.copyOf(this.field6125, var1);
            } else if (classYY.field7111 == this.field6126) {
               if (var2 != -483624883) {
                  throw new IllegalStateException();
               }

               this.field6123 = Arrays.copyOf(this.field6123, var1);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "te.ao(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lte;)I")
   @ObfuscatedName("cr")
   public static int method10838(classTE var0) {
      int var1 = 3;
      if (var0 != null) {
         switch (var0.field6126.field7105 * -972641695) {
            case 2:
               var1 += 176544829 * var0.field6129;
               break;
            case 3:
               var1 += var0.field6129 * 1631477736;
               break;
            case 4:
               for (int var2 = 0; var2 < var0.field6129 * -1943548931; var2++) {
                  var1 += classAU.method709((String)var0.field6123[var2], (byte)77);
               }
               break;
            default:
               throw new IllegalArgumentException();
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lte;)I")
   @ObfuscatedName("cu")
   public static int method10839(classTE var0) {
      int var1 = 3;
      if (var0 != null) {
         switch (var0.field6126.field7105 * -900901303) {
            case 2:
               var1 += 815738868 * var0.field6129;
               break;
            case 3:
               var1 += var0.field6129 * 1631477736;
               break;
            case 4:
               for (int var2 = 0; var2 < var0.field6129 * -1943548931; var2++) {
                  var1 += classAU.method709((String)var0.field6123[var2], (byte)106);
               }
               break;
            default:
               throw new IllegalArgumentException();
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("cb")
   public void method10841(classXY var1) {
      var1.method13031(-1943548931 * this.field6129, -1542382992);
      if (classYY.field7109 == this.field6126) {
         classXY.method13029(var1, null != this.field6120 && (Integer)this.field6120 == -1 ? 1 : 0, (byte)-97);

         for (int var2 = 0; var2 < -1943548931 * this.field6129; var2++) {
            var1.method13037(this.field6124[var2], -193597109);
         }
      } else if (this.field6126 == classYY.field7113) {
         classXY.method13029(var1, this.field6120 != null && (Long)this.field6120 == -1L ? 3 : 2, (byte)-59);

         for (int var3 = 0; var3 < this.field6129 * -1943548931; var3++) {
            classXY.method13047(var1, this.field6125[var3]);
         }
      } else if (classYY.field7111 == this.field6126) {
         classXY.method13029(var1, 4, (byte)-13);

         for (int var4 = 0; var4 < -1943548931 * this.field6129; var4++) {
            var1.method13054((String)this.field6123[var4], (short)26595);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ct")
   public void method10842(classXY var1) {
      var1.method13031(-1943548931 * this.field6129, -1542382992);
      if (classYY.field7109 == this.field6126) {
         classXY.method13029(var1, null != this.field6120 && (Integer)this.field6120 == -1 ? 1 : 0, (byte)-49);

         for (int var2 = 0; var2 < -1943548931 * this.field6129; var2++) {
            var1.method13037(this.field6124[var2], 576025583);
         }
      } else if (this.field6126 == classYY.field7113) {
         classXY.method13029(var1, this.field6120 != null && (Long)this.field6120 == -1L ? 3 : 2, (byte)-90);

         for (int var3 = 0; var3 < this.field6129 * -1250620310; var3++) {
            classXY.method13047(var1, this.field6125[var3]);
         }
      } else if (classYY.field7111 == this.field6126) {
         classXY.method13029(var1, 4, (byte)-89);

         for (int var4 = 0; var4 < -1943548931 * this.field6129; var4++) {
            var1.method13054((String)this.field6123[var4], (short)11373);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;Z)Lte;")
   @ObfuscatedName("cp")
   public static classTE method10844(classXY var0, boolean var1) {
      int var2 = var0.method13099(-103836328);
      if (var2 >= 0 && var2 <= 5000) {
         int var3 = var0.method13095(-346779531);
         if (var3 == 0 || 1 == var3) {
            int[] var9 = new int[var2];

            for (int var13 = 0; var13 < var2; var13++) {
               var9[var13] = classXY.method13113(var0, (byte)1);
            }

            return var1 ? classKH.method6512(var9, (byte)5) : classDG.method3085(var9, -530642757);
         } else if (var3 == 2 || var3 == 3) {
            long[] var8 = new long[var2];

            for (int var11 = 0; var11 < var2; var11++) {
               var8[var11] = classXY.method13115(var0, 244337670);
            }

            classTE var12;
            if (var1) {
               var12 = classEK.method3929(var8, (byte)49);
            } else {
               classTE var14 = new classTE(classYY.field7113, false);
               var14.field6125 = var8;
               var14.field6129 = -1063196331 * var8.length;
               var14.field6121 = var8.length * 404453943;
               var12 = var14;
            }

            return var12;
         } else if (var3 == 4) {
            String[] var4 = new String[var2];

            for (int var5 = 0; var5 < var2; var5++) {
               var4[var5] = var0.method13127(944597331);
            }

            classTE var10;
            if (var1) {
               var10 = SceneTilePaint.method4823(var4, -2022602313);
            } else {
               classTE var7 = new classTE(classYY.field7111, false);
               var7.field6123 = var4;
               var7.field6129 = var4.length * -1052601557;
               var7.field6121 = var4.length * 404453943;
               var10 = var7;
            }

            return var10;
         } else if (var3 == 255) {
            return null;
         } else {
            throw new RuntimeException();
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lte;I)V")
   @ObfuscatedName("zq")
   public static void method10826(classTE var0, int var1) {
      if (var0.field6121 * 922180487 != var1) {
         var0.field6121 = var1 * 404453943;
         if (var0.field6126 == classYY.field7109) {
            var0.field6124 = Arrays.copyOf(var0.field6124, var1);
         } else if (var0.field6126 == classYY.field7113) {
            var0.field6125 = Arrays.copyOf(var0.field6125, var1);
         } else if (classYY.field7111 == var0.field6126) {
            var0.field6123 = Arrays.copyOf(var0.field6123, var1);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;Z)Lte;")
   @ObfuscatedName("cq")
   public static classTE method10845(classXY var0, boolean var1) {
      int var2 = var0.method13099(-836339954);
      if (var2 >= 0 && var2 <= 5000) {
         int var3 = var0.method13095(-346779531);
         if (var3 == 0 || 1 == var3) {
            int[] var9 = new int[var2];

            for (int var13 = 0; var13 < var2; var13++) {
               var9[var13] = classXY.method13113(var0, (byte)1);
            }

            return var1 ? classKH.method6512(var9, (byte)9) : classDG.method3085(var9, -530642757);
         } else if (var3 == 2 || var3 == 3) {
            long[] var8 = new long[var2];

            for (int var11 = 0; var11 < var2; var11++) {
               var8[var11] = classXY.method13115(var0, 1895486891);
            }

            classTE var12;
            if (var1) {
               var12 = classEK.method3929(var8, (byte)49);
            } else {
               classTE var14 = new classTE(classYY.field7113, false);
               var14.field6125 = var8;
               var14.field6129 = -1063196331 * var8.length;
               var14.field6121 = var8.length * 404453943;
               var12 = var14;
            }

            return var12;
         } else if (var3 == 4) {
            String[] var4 = new String[var2];

            for (int var5 = 0; var5 < var2; var5++) {
               var4[var5] = var0.method13127(465506);
            }

            classTE var10;
            if (var1) {
               var10 = SceneTilePaint.method4823(var4, 326583914);
            } else {
               classTE var7 = new classTE(classYY.field7111, false);
               var7.field6123 = var4;
               var7.field6129 = var4.length * -1063196331;
               var7.field6121 = var4.length * 404453943;
               var10 = var7;
            }

            return var10;
         } else if (var3 == 255) {
            return null;
         } else {
            throw new RuntimeException();
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bu")
   final void method10818(int var1) {
      if (!this.field6122) {
         throw new RuntimeException();
      } else if (var1 >= 0 && var1 <= -1262220156) {
         if (var1 > this.field6121 * 416449250) {
            this.method10825(method10822(this, var1, 120041229), -483624883);
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Ljava/lang/Object;")
   @ObfuscatedName("ag")
   final Object method10806(int var1, byte var2) {
      try {
         if (classYY.field7112 == this.field6126) {
            if (var2 != -1) {
               throw new IllegalStateException();
            } else {
               return this.field6124[var1];
            }
         } else if (classYY.field7113 == this.field6126) {
            if (var2 != -1) {
               throw new IllegalStateException();
            } else {
               return this.field6125[var1];
            }
         } else {
            return this.field6123[var1];
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "te.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ax")
   public final void method10827(int var1, int var2) {
      try {
         if (this.field6129 * 922180487 != var1) {
            if (var2 != -483624883) {
               throw new IllegalStateException();
            }

            this.field6121 = var1 * 404453943;
            if (this.field6126 == classYY.field7112) {
               if (var2 != -483624883) {
                  return;
               }

               this.field6124 = Arrays.copyOf(this.field6124, var1);
            } else if (this.field6126 == classYY.field7111) {
               if (var2 != -483624883) {
                  throw new IllegalStateException();
               }

               this.field6125 = Arrays.copyOf(this.field6125, var1);
            } else if (classYY.field7113 == this.field6126) {
               if (var2 != -483624883) {
                  throw new IllegalStateException();
               }

               this.field6123 = Arrays.copyOf(this.field6123, var1);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "te.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aa")
   int method10824(int var1, int var2) {
      try {
         int var3 = (this.field6129 * 922180487 >> 1) + this.field6121 * 922180487;
         return Math.max(var1, Math.min(var3, 5000));
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "te.aa(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bk")
   final void method10819(int var1) {
      if (!this.field6122) {
         throw new RuntimeException();
      } else if (var1 >= 0 && var1 <= 5000) {
         if (var1 > this.field6121 * 922180487) {
            this.method10825(method10822(this, var1, 120041229), -483624883);
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public final int method10799() {
      return this.field6129 * -1943548931;
   }
}
