import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("of")
public class classOF extends classVJ {
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field4856 = 78;
   @ObfuscatedName("an")
   final short[] field4844;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4851 = 40;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field4853 = 41;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4852 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field4854 = 75;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field4855 = 74;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("av")
   static NodeCache field4850 = new NodeCache(64);
   @ObfuscatedName("at")
   final int[] field4843;
   @ObfuscatedName("bx")
   final int[] field4848;
   @ObfuscatedName("bn")
   final short[] field4847;
   @ObfuscatedName("ah")
   final short[] field4846;
   @ObfuscatedName("bp")
   final int field4842;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4857 = 1;
   @ObfuscatedName("bc")
   public final boolean field4849;
   @ObfuscatedName("am")
   final short[] field4845;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   public static final int field4858 = 38;

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("aq")
   public boolean method8443(int var1, int var2) {
      return var2 == 1 ? var1 + 7 == -472152487 * this.field4842 : -472152487 * this.field4842 == var1;
   }

   classOF(classXY var1) {
      int[] var2 = null;
      short[] var3 = null;
      short[] var4 = null;
      short[] var5 = null;
      short[] var6 = null;
      int[] var7 = new int[]{-1, -1, -1, -1, -1};
      boolean var8 = false;
      int var9 = -1;
      if (null != var1 && null != var1.field6952) {
         while (true) {
            int var10 = var1.method13095(-346779531);
            if (0 == var10) {
               break;
            }

            switch (var10) {
               case 1:
                  var9 = var1.method13095(-346779531);
                  break;
               case 3:
                  var8 = true;
                  break;
               case 5:
                  int var14 = var1.method13095(-346779531);
                  var2 = new int[var14];

                  for (int var16 = 0; var16 < var14; var16++) {
                     var2[var16] = var1.method13173(classXM.field6886.field6888, 1793957453);
                  }
                  break;
               case 40:
                  int var13 = var1.method13095(-346779531);
                  var3 = new short[var13];
                  var4 = new short[var13];

                  for (int var15 = 0; var15 < var13; var15++) {
                     var3[var15] = (short)var1.method13099(-1391345931);
                     var4[var15] = (short)var1.method13099(-809343746);
                  }
                  break;
               case 41:
                  int var11 = var1.method13095(-346779531);
                  var5 = new short[var11];
                  var6 = new short[var11];

                  for (int var12 = 0; var12 < var11; var12++) {
                     var5[var12] = (short)var1.method13099(601297684);
                     var6[var12] = (short)var1.method13099(-461039493);
                  }
                  break;
               case 70:
               case 71:
               case 72:
               case 73:
               case 74:
               case 75:
               case 76:
               case 77:
               case 78:
               case 79:
                  var7[var10 - 70] = var1.method13173(classXM.field6886.field6888, 2002691714);
            }
         }
      }

      this.field4843 = var2;
      this.field4844 = var3;
      this.field4845 = var4;
      this.field4846 = var5;
      this.field4847 = var6;
      this.field4848 = var7;
      this.field4849 = var8;
      this.field4842 = var9 * -1237098007;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   public static int method8460() {
      return -1529735849 * classEY.field1981;
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("ae")
   public boolean method8444(int var1, int var2, int var3) {
      try {
         if (var2 == 1) {
            if (var3 >= -213633548) {
               throw new IllegalStateException();
            } else {
               boolean var5;
               if (var1 + 7 == -472152487 * this.field4842) {
                  if (var3 >= -213633548) {
                     throw new IllegalStateException();
                  }

                  var5 = true;
               } else {
                  var5 = false;
               }

               return var5;
            }
         } else {
            boolean var10000;
            if (-472152487 * this.field4842 == var1) {
               if (var3 >= -213633548) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "of.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("as")
   public boolean method8453(int var1) {
      try {
         boolean var2 = true;

         for (int var3 = 0; var3 < 5; var3++) {
            if (var1 >= 312604843) {
               throw new IllegalStateException();
            }

            if (-1 != this.field4848[var3]) {
               if (var1 >= 312604843) {
                  throw new IllegalStateException();
               }

               if (!classOX.field5190.method11944(this.field4848[var3], 0, (byte)-54)) {
                  var2 = false;
               }
            }
         }

         return var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "of.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ler;")
   @ObfuscatedName("ax")
   public ModelData method8456(int var1) {
      try {
         ModelData[] var2 = new ModelData[5];
         int var3 = 0;

         for (int var4 = 0; var4 < 5; var4++) {
            if (var1 == 293773697) {
               throw new IllegalStateException();
            }

            if (-1 != this.field4848[var4]) {
               var2[var3++] = ModelData.method4067(classOX.field5190, this.field4848[var4], 0);
            }
         }

         ModelData var7 = new ModelData(var2, var3);
         if (this.field4844 != null) {
            if (var1 == 293773697) {
               throw new IllegalStateException();
            }

            for (int var5 = 0; var5 < this.field4844.length; var5++) {
               var7.method4031(this.field4844[var5], this.field4845[var5]);
            }
         }

         if (null != this.field4846) {
            if (var1 == 293773697) {
               throw new IllegalStateException();
            }

            for (int var8 = 0; var8 < this.field4846.length; var8++) {
               if (var1 == 293773697) {
                  throw new IllegalStateException();
               }

               var7.method4035(this.field4846[var8], this.field4847[var8]);
            }
         }

         return var7;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "of.ax(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;Laae;)Z")
   @ObfuscatedName("ug")
   public static boolean method8442(NameableContainer var0, classAAE var1) {
      if (var0 == null) {
         var0.method10307();
      }

      Nameable var2 = var0.method10258(var1, 1990998649);
      if (var2 == null) {
         return false;
      } else {
         NameableContainer.method10270(var0, var2, -396801539);
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("ai")
   public ModelData method8451() {
      if (this.field4843 == null) {
         return null;
      } else {
         ModelData[] var1 = new ModelData[this.field4843.length];

         for (int var2 = 0; var2 < this.field4843.length; var2++) {
            var1[var2] = ModelData.method4067(classOX.field5190, this.field4843[var2], 0);
         }

         ModelData var4;
         if (1 == var1.length) {
            var4 = var1[0];
         } else {
            var4 = new ModelData(var1, var1.length);
         }

         if (null != this.field4844) {
            for (int var3 = 0; var3 < this.field4844.length; var3++) {
               var4.method4031(this.field4844[var3], this.field4845[var3]);
            }
         }

         if (this.field4846 != null) {
            for (int var5 = 0; var5 < this.field4846.length; var5++) {
               var4.method4035(this.field4846[var5], this.field4847[var5]);
            }
         }

         return var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lof;")
   @ObfuscatedName("ao")
   public static classOF method8438(int var0) {
      classOF var1 = (classOF)NodeCache.method6460(field4850, var0);
      if (null != var1) {
         return var1;
      } else if (classLP.field4194 == null) {
         return null;
      } else {
         byte[] var2 = classLP.field4194.method11942(3, var0, -2099544452);
         var1 = new classOF(new classXY(var2));
         field4850.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   public static void method8440() {
      field4850.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public static void method8441() {
      field4850.method6468();
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("at")
   public ModelData method8457() {
      ModelData[] var1 = new ModelData[5];
      int var2 = 0;

      for (int var3 = 0; var3 < 5; var3++) {
         if (-1 != this.field4848[var3]) {
            var1[var2++] = ModelData.method4067(classOX.field5190, this.field4848[var3], 0);
         }
      }

      ModelData var5 = new ModelData(var1, var2);
      if (this.field4844 != null) {
         for (int var4 = 0; var4 < this.field4844.length; var4++) {
            var5.method4031(this.field4844[var4], this.field4845[var4]);
         }
      }

      if (null != this.field4846) {
         for (int var6 = 0; var6 < this.field4846.length; var6++) {
            var5.method4035(this.field4846[var6], this.field4847[var6]);
         }
      }

      return var5;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ad")
   public boolean method8445(int var1, int var2) {
      return var2 == 1 ? var1 + 7 == 18219166 * this.field4842 : -1247501727 * this.field4842 == var1;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ap")
   public boolean method8446(int var1, int var2) {
      return var2 == 1 ? var1 + 7 == -472152487 * this.field4842 : 962878288 * this.field4842 == var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method8448() {
      if (null == this.field4843) {
         return true;
      } else {
         boolean var1 = true;

         for (int var2 = 0; var2 < this.field4843.length; var2++) {
            if (!classOX.field5190.method11944(this.field4843[var2], 0, (byte)43)) {
               var1 = false;
            }
         }

         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ah")
   public static int method8461() {
      return -1529735849 * classEY.field1981;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ar")
   public boolean method8454() {
      boolean var1 = true;

      for (int var2 = 0; var2 < 5; var2++) {
         if (-1 != this.field4848[var2] && !classOX.field5190.method11944(this.field4848[var2], 0, (byte)-51)) {
            var1 = false;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aw")
   public boolean method8455() {
      boolean var1 = true;

      for (int var2 = 0; var2 < 5; var2++) {
         if (-1 != this.field4848[var2] && !classOX.field5190.method11944(this.field4848[var2], 0, (byte)-43)) {
            var1 = false;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("ak")
   public ModelData method8458() {
      ModelData[] var1 = new ModelData[5];
      int var2 = 0;

      for (int var3 = 0; var3 < 5; var3++) {
         if (-1 != this.field4848[var3]) {
            var1[var2++] = ModelData.method4067(classOX.field5190, this.field4848[var3], 0);
         }
      }

      ModelData var5 = new ModelData(var1, var2);
      if (this.field4844 != null) {
         for (int var4 = 0; var4 < this.field4844.length; var4++) {
            var5.method4031(this.field4844[var4], this.field4845[var4]);
         }
      }

      if (null != this.field4846) {
         for (int var6 = 0; var6 < this.field4846.length; var6++) {
            var5.method4035(this.field4846[var6], this.field4847[var6]);
         }
      }

      return var5;
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("av")
   public ModelData method8459() {
      ModelData[] var1 = new ModelData[5];
      int var2 = 0;

      for (int var3 = 0; var3 < 5; var3++) {
         if (-1 != this.field4848[var3]) {
            var1[var2++] = ModelData.method4067(classOX.field5190, this.field4848[var3], 0);
         }
      }

      ModelData var5 = new ModelData(var1, var2);
      if (this.field4844 != null) {
         for (int var4 = 0; var4 < this.field4844.length; var4++) {
            var5.method4031(this.field4844[var4], this.field4845[var4]);
         }
      }

      if (null != this.field4846) {
         for (int var6 = 0; var6 < this.field4846.length; var6++) {
            var5.method4035(this.field4846[var6], this.field4847[var6]);
         }
      }

      return var5;
   }

   @ObfuscatedSignature(descriptor = "(I)Ler;")
   @ObfuscatedName("ag")
   public ModelData method8452(int var1) {
      try {
         if (this.field4843 == null) {
            if (var1 == 1835499108) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            ModelData[] var2 = new ModelData[this.field4843.length];

            for (int var3 = 0; var3 < this.field4843.length; var3++) {
               if (var1 == 1835499108) {
                  throw new IllegalStateException();
               }

               var2[var3] = ModelData.method4067(classOX.field5190, this.field4843[var3], 0);
            }

            ModelData var6;
            if (1 == var2.length) {
               if (var1 == 1835499108) {
                  throw new IllegalStateException();
               }

               var6 = var2[0];
            } else {
               var6 = new ModelData(var2, var2.length);
            }

            if (null != this.field4844) {
               if (var1 == 1835499108) {
                  throw new IllegalStateException();
               }

               for (int var4 = 0; var4 < this.field4844.length; var4++) {
                  if (var1 == 1835499108) {
                     throw new IllegalStateException();
                  }

                  var6.method4031(this.field4844[var4], this.field4845[var4]);
               }
            }

            if (this.field4846 != null) {
               if (var1 == 1835499108) {
                  throw new IllegalStateException();
               }

               for (int var7 = 0; var7 < this.field4846.length; var7++) {
                  if (var1 == 1835499108) {
                     throw new IllegalStateException();
                  }

                  var6.method4035(this.field4846[var7], this.field4847[var7]);
               }
            }

            return var6;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "of.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lof;")
   @ObfuscatedName("aa")
   public static classOF method8439(int var0) {
      classOF var1 = (classOF)NodeCache.method6460(field4850, var0);
      if (null != var1) {
         return var1;
      } else if (classLP.field4194 == null) {
         return null;
      } else {
         byte[] var2 = classLP.field4194.method11942(3, var0, -1536490744);
         var1 = new classOF(new classXY(var2));
         field4850.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public static int method8462() {
      return -1529735849 * classEY.field1981;
   }

   @ObfuscatedSignature(descriptor = "(Lof;B)Z")
   @ObfuscatedName("ui")
   public static boolean method8449(classOF var0, byte var1) {
      if (var0 == null) {
         return var0.method8450(var1);
      } else {
         try {
            if (null == var0.field4843) {
               if (var1 >= 1) {
                  throw new IllegalStateException();
               } else {
                  return true;
               }
            } else {
               boolean var2 = true;

               for (int var3 = 0; var3 < var0.field4843.length; var3++) {
                  if (var1 >= 1) {
                     throw new IllegalStateException();
                  }

                  if (!classOX.field5190.method11944(var0.field4843[var3], 0, (byte)43)) {
                     if (var1 >= 1) {
                        throw new IllegalStateException();
                     }

                     var2 = false;
                  }
               }

               return var2;
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "of.ab(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ay")
   public boolean method8447(int var1, int var2) {
      return var2 == 1 ? var1 + 7 == -472152487 * this.field4842 : -472152487 * this.field4842 == var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   public static int method8463() {
      return -1529735849 * classEY.field1981;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bx")
   public static int method8464() {
      return -1284282331 * classEY.field1981;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ae")
   static void method8437(int var0) {
      try {
         classVR.field6553[0] = 0;

         for (short var1 = 0; var1 < 2048; var1 += 128) {
            int var2 = classMO.method7667(var1, (byte)1);
            short var3 = 0;
            if (var1 > 0) {
               if (var0 <= -148653043) {
                  throw new IllegalStateException();
               }

               if (var1 < 1024) {
                  if (var0 <= -148653043) {
                     throw new IllegalStateException();
                  }

                  var3 |= 272;
               }
            }

            if (var1 > 512) {
               if (var0 <= -148653043) {
                  throw new IllegalStateException();
               }

               if (var1 < 1536) {
                  if (var0 <= -148653043) {
                     throw new IllegalStateException();
                  }

                  var3 |= 1088;
               }
            }

            if (var1 > 1024) {
               if (var0 <= -148653043) {
                  throw new IllegalStateException();
               }

               var3 |= 544;
            }

            label95: {
               if (var1 <= 1536) {
                  if (var1 >= 512) {
                     break label95;
                  }

                  if (var0 <= -148653043) {
                     throw new IllegalStateException();
                  }
               }

               var3 |= 2176;
            }

            label89: {
               if (var1 != 0) {
                  if (var0 <= -148653043) {
                     throw new IllegalStateException();
                  }

                  if (1024 != var1) {
                     break label89;
                  }

                  if (var0 <= -148653043) {
                     throw new IllegalStateException();
                  }
               }

               var3 |= 48;
            }

            label82: {
               if (512 != var1) {
                  if (var0 <= -148653043) {
                     throw new IllegalStateException();
                  }

                  if (1536 != var1) {
                     break label82;
                  }

                  if (var0 <= -148653043) {
                     throw new IllegalStateException();
                  }
               }

               var3 |= 1152;
            }

            classVR.field6553[var2] = var3;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "of.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Lxz;II)Lxz;")
   @ObfuscatedName("az")
   public static classXZ method8436(classXZ[] var0, int var1, int var2) {
      try {
         classXZ[] var3 = var0;

         for (int var4 = 0; var4 < var3.length; var4++) {
            if (var2 == -172824696) {
               throw new IllegalStateException();
            }

            classXZ var5 = var3[var4];
            if (var1 == var5.vmethod8((byte)1)) {
               return var5;
            }
         }

         return null;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "of.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ab")
   public boolean method8450(byte var1) {
      try {
         if (null == this.field4848) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            boolean var2 = true;

            for (int var3 = 0; var3 < this.field4843.length; var3++) {
               if (var1 >= 1) {
                  throw new IllegalStateException();
               }

               if (!classOX.field5190.method11944(this.field4848[var3], 0, (byte)43)) {
                  if (var1 >= 1) {
                     throw new IllegalStateException();
                  }

                  var2 = false;
               }
            }

            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "of.ab(" + ')');
      }
   }
}
