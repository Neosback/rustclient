import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sg")
public class classSG {
   @ObfuscatedSignature(descriptor = "[Luu;")
   @ObfuscatedName("as")
   classUU[] field5924;
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("aa")
   classUU field5928;
   @ObfuscatedSignature(descriptor = "Lsg;")
   @ObfuscatedName("ae")
   public classSG field5927;
   @ObfuscatedName("ag")
   boolean field5921;
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("ao")
   classUU field5930;
   @ObfuscatedName("ab")
   boolean field5920;
   @ObfuscatedSignature(descriptor = "[Luu;")
   @ObfuscatedName("ax")
   classUU[] field5925;
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("ac")
   classUU field5929;
   @ObfuscatedName("az")
   public final int field5922;
   @ObfuscatedName("og")
   public int field5926;
   @ObfuscatedSignature(descriptor = "[Luu;")
   @ObfuscatedName("af")
   final classUU[] field5923;
   @ObfuscatedName("aj")
   float[][] field5931;
   @ObfuscatedName("ay")
   float[][] field5933;
   @ObfuscatedName("al")
   float[][] field5932;

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("bu")
   float[] method10461(int var1) {
      return this.field5932[var1];
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   void method10430(byte var1) {
      try {
         this.field5931 = new float[this.field5923.length][3];
         this.field5933 = new float[this.field5923.length][3];
         this.field5932 = new float[this.field5923.length][3];
         classUU var2 = classRM.method10172(1778071523);

         for (int var3 = 0; var3 < this.field5923.length; var3++) {
            classUU var4 = this.method10433(var3, (byte)10);
            classUZ.method11857(var2, var4, (byte)-47);
            classUU.method11670(var2, -56392464);
            this.field5931[var3] = var2.method11629(966942861);
            this.field5933[var3][0] = var4.field6391;
            this.field5933[var3][1] = var4.field6399;
            this.field5933[var3][2] = var4.field6395;
            this.field5932[var3] = var4.method11702(986444461);
         }

         var2.method11615(1707831254);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "sg.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("hz")
   public classUU method10476(int var1) {
      return this.method10433(var1, (byte)10);
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("qq")
   public classUU method10477(int var1) {
      return this.method10470(var1, 55397094);
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;)Luu;")
   @ObfuscatedName("wf")
   public classUU method10472(rl21 var1) {
      rl2 var2 = var1.field5720[this.field5926];
      if (var2.field5717) {
         var2.field5715.method11720(var2.field5714);
         if (this.field5927 != null) {
            var2.field5715.method11728(this.field5927.method10472(var1));
         }

         var2.field5717 = false;
      }

      return var2.field5715;
   }

   @ObfuscatedSignature(descriptor = "(IB)Luu;")
   @ObfuscatedName("af")
   classUU method10433(int var1, byte var2) {
      try {
         return this.field5923[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sg.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Luu;")
   @ObfuscatedName("ae")
   classUU method10470(int var1, int var2) {
      if (this.field5924[var1] == null) {
         classUU var3 = this.method10476(var1);
         if (this.field5927 != null) {
            var3 = new classUU(var3);
            var3.method11728(this.field5927.method10477(var1));
         }

         this.field5924[var1] = var3;
      }

      return this.field5924[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("aq")
   classUU method10434(int var1) {
      return this.field5923[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("vl")
   public classUU method10478(int var1) {
      return method10474(this, var1, (byte)-3);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fn")
   public void method10473(int var1) {
      this.field5923[var1] = null;
      this.field5924 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("ag")
   public classUU method10471(int var1) {
      rl2 var2 = rl21.field5726.field5720[this.field5926];
      return var2.field5714;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsg;I)Luu;")
   @ObfuscatedName("nk")
   public static classUU method10435(classSG var0, int var1) {
      if (var0.field5924[var1] == null) {
         classUU var2 = var0.method10433(var1, (byte)111);
         if (var0.field5927 != null) {
            var2 = new classUU(var2);
            var2.method11656(var0.field5927.method10470(var1, 585432322), -1742291172);
         }

         var0.field5924[var1] = var2;
      }

      return var0.field5924[var1];
   }

   @ObfuscatedSignature(descriptor = "(Luu;I)V")
   @ObfuscatedName("as")
   void method10445(classUU var1, int var2) {
      try {
         classUZ.method11857(this.field5929, var1, (byte)7);
         this.field5921 = true;
         this.field5920 = true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sg.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("aw")
   classUU method10436(int var1) {
      if (this.field5924[var1] == null) {
         classUU var2 = this.method10433(var1, (byte)52);
         if (this.field5927 != null) {
            var2 = new classUU(var2);
            var2.method11656(this.field5927.method10470(var1, -1226312150), -479714307);
         }

         this.field5924[var1] = var2;
      }

      return this.field5924[var1];
   }

   @ObfuscatedSignature(descriptor = "(II)Luu;")
   @ObfuscatedName("ac")
   public classUU method10451(int var1, int var2) {
      try {
         if (this.field5920) {
            if (var2 != 131168747) {
               throw new IllegalStateException();
            }

            classUZ.method11857(this.field5930, method10474(this, var1, (byte)-3), (byte)-94);
            this.field5930.method11656(this.method10447(-1065936393), -204408522);
            this.field5920 = false;
         }

         return this.field5930;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sg.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)[F")
   @ObfuscatedName("ao")
   float[] method10453(int var1, int var2) {
      try {
         return this.field5931[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sg.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("bm")
   float[] method10454(int var1) {
      return this.field5931[var1];
   }

   @ObfuscatedSignature(descriptor = "(II)[F")
   @ObfuscatedName("aa")
   float[] method10462(int var1, int var2) {
      try {
         return this.field5932[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sg.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("hg")
   public float[] method10479(int var1) {
      return this.method10453(var1, -2034173247);
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("nl")
   public float[] method10480(int var1) {
      return this.method10462(var1, -16766288);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   void method10431() {
      this.field5931 = new float[this.field5923.length][3];
      this.field5933 = new float[this.field5923.length][3];
      this.field5932 = new float[this.field5923.length][3];
      classUU var1 = classRM.method10172(1778071523);

      for (int var2 = 0; var2 < this.field5923.length; var2++) {
         classUU var3 = this.method10433(var2, (byte)90);
         classUZ.method11857(var1, var3, (byte)-27);
         classUU.method11670(var1, -1298350421);
         this.field5931[var2] = var1.method11629(1615612734);
         this.field5933[var2][0] = var3.field6391;
         this.field5933[var2][1] = var3.field6399;
         this.field5933[var2][2] = var3.field6395;
         this.field5932[var2] = var3.method11702(986444461);
      }

      var1.method11615(1961003628);
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("av")
   classUU method10441(int var1) {
      if (null == this.field5925[var1]) {
         this.field5925[var1] = new classUU(this.method10470(var1, -1630528307));
         classUU.method11670(this.field5925[var1], -575747350);
      }

      return this.field5925[var1];
   }

   @ObfuscatedSignature(descriptor = "(Lsg;IB)Luu;")
   @ObfuscatedName("hg")
   public static classUU method10474(classSG var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method10475(var1, var2);
      }

      if (var0.field5925[var1] == null) {
         classUU var3 = new classUU(var0.method10477(var1));
         var3.method11721();
         var0.field5925[var1] = var3;
      }

      return var0.field5925[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("bg")
   float[] method10457(int var1) {
      return this.field5933[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("ak")
   classUU method10437(int var1) {
      if (this.field5924[var1] == null) {
         classUU var2 = this.method10433(var1, (byte)88);
         if (this.field5927 != null) {
            var2 = new classUU(var2);
            var2.method11656(this.field5927.method10470(var1, 884608822), 1084677221);
         }

         this.field5924[var1] = var2;
      }

      return this.field5924[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("au")
   classUU method10438(int var1) {
      if (this.field5924[var1] == null) {
         classUU var2 = this.method10433(var1, (byte)87);
         if (this.field5927 != null) {
            var2 = new classUU(var2);
            var2.method11656(this.field5927.method10470(var1, 413405924), -1208372749);
         }

         this.field5924[var1] = var2;
      }

      return this.field5924[var1];
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;I)Luu;")
   @ObfuscatedName("gi")
   public classUU method10469(rl21 var1, int var2) {
      rl2 var3 = var1.field5720[this.field5926];
      if (var3.field5718) {
         var3.field5716.method11720(this.method10478(var2));
         var3.field5716.method11728(this.method10472(var1));
         var3.field5718 = false;
      }

      return var3.field5716;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   void method10432() {
      this.field5931 = new float[this.field5923.length][3];
      this.field5933 = new float[this.field5923.length][3];
      this.field5932 = new float[this.field5923.length][3];
      classUU var1 = classRM.method10172(1778071523);

      for (int var2 = 0; var2 < this.field5923.length; var2++) {
         classUU var3 = this.method10433(var2, (byte)49);
         classUZ.method11857(var1, var3, (byte)104);
         classUU.method11670(var1, -1455755593);
         this.field5931[var2] = var1.method11629(-1493867438);
         this.field5933[var2][0] = var3.field6391;
         this.field5933[var2][1] = var3.field6399;
         this.field5933[var2][2] = var3.field6395;
         this.field5932[var2] = var3.method11702(986444461);
      }

      var1.method11615(1543161437);
   }

   @ObfuscatedSignature(descriptor = "(Ler;III)V")
   @ObfuscatedName("gz")
   public static void method10466(ModelData var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method4066(var1, var1, var1);
      } else {
         for (int var4 = 0; var4 < var0.field1846; var4++) {
            var0.field1853[var4] = var1 * var0.field1853[var4] / 128.0F;
            var0.field1854[var4] = var2 * var0.field1854[var4] / 128.0F;
            var0.field1855[var4] = var3 * var0.field1855[var4] / 128.0F;
         }

         var0.method4084();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("bp")
   public classUU method10452(int var1) {
      if (this.field5920) {
         classUZ.method11857(this.field5930, method10474(this, var1, (byte)-63), (byte)29);
         this.field5930.method11656(this.method10447(-1819297163), -2078213200);
         this.field5920 = false;
      }

      return this.field5930;
   }

   @ObfuscatedSignature(descriptor = "()Luu;")
   @ObfuscatedName("an")
   public classUU method10443() {
      return this.field5929;
   }

   @ObfuscatedSignature(descriptor = "()Luu;")
   @ObfuscatedName("am")
   public classUU method10444() {
      return this.field5929;
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("ah")
   void method10446(classUU var1) {
      classUZ.method11857(this.field5929, var1, (byte)74);
      this.field5921 = true;
      this.field5920 = true;
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("ax")
   classUU method10447(int var1) {
      try {
         if (this.field5921) {
            if (var1 == 1870919123) {
               throw new IllegalStateException();
            }

            classUZ.method11857(this.field5928, this.method10471(-2034392768), (byte)4);
            if (null != this.field5927) {
               if (var1 == 1870919123) {
                  throw new IllegalStateException();
               }

               this.field5928.method11656(this.field5927.method10447(-304311430), -365415784);
            }

            this.field5921 = false;
         }

         return this.field5928;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sg.ax(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lclient;I)V")
   @ObfuscatedName("xm")
   public static void method10468(client var0, int var1) {
      if (var0 == null) {
         var0.method2388();
      }

      client.field872 = var1 * -168933321;
      if (-1 == -185864825 * client.field872) {
         classIS.field3051.field1687.method4158();
      }
   }

   @ObfuscatedSignature(descriptor = "()Luu;")
   @ObfuscatedName("bx")
   classUU method10448() {
      if (this.field5921) {
         classUZ.method11857(this.field5928, this.method10471(-2133598912), (byte)22);
         if (null != this.field5927) {
            this.field5928.method11656(this.field5927.method10447(404339773), -388573751);
         }

         this.field5921 = false;
      }

      return this.field5928;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("di")
   public void method10481() {
      this.method10430((byte)-65);
   }

   @ObfuscatedSignature(descriptor = "()Luu;")
   @ObfuscatedName("bn")
   classUU method10449() {
      if (this.field5921) {
         classUZ.method11857(this.field5928, this.method10471(-2057048639), (byte)-88);
         if (null != this.field5927) {
            this.field5928.method11656(this.field5927.method10447(-1520100119), 584151555);
         }

         this.field5921 = false;
      }

      return this.field5928;
   }

   @ObfuscatedSignature(descriptor = "()Luu;")
   @ObfuscatedName("bc")
   classUU method10450() {
      if (this.field5921) {
         classUZ.method11857(this.field5928, this.method10471(-1960989982), (byte)49);
         if (null != this.field5927) {
            this.field5928.method11656(this.field5927.method10447(-1483142212), -1173551112);
         }

         this.field5921 = false;
      }

      return this.field5928;
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("br")
   float[] method10455(int var1) {
      return this.field5931[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("bj")
   float[] method10458(int var1) {
      return this.field5933[var1];
   }

   public classSG(int var1, classXY var2, boolean var3) {
      this.field5921 = this.field5920 = true;
      this.field5922 = var2.method13327() * 172778659;
      this.field5923 = new classUU[var1];
      this.field5924 = new classUU[var1];
      this.field5925 = new classUU[var1];

      for (int var4 = 0; var4 < var1; var4++) {
         this.field5923[var4] = new classUU(var2, var3);
         var2.method13326();
         var2.method13326();
         var2.method13326();
      }

      this.method10481();
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("em")
   public void method10482(classUU var1) {
      this.method10445(var1, 1262310232);
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("bt")
   float[] method10456(int var1) {
      return this.field5931[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("ga")
   public float[] method10483(int var1) {
      return this.method10460(var1, -316051969);
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("bk")
   float[] method10459(int var1) {
      return this.field5933[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("bo")
   float[] method10463(int var1) {
      return this.field5932[var1];
   }

   @ObfuscatedSignature(descriptor = "(II)[F")
   @ObfuscatedName("al")
   float[] method10460(int var1, int var2) {
      try {
         return this.field5933[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sg.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("ba")
   float[] method10464(int var1) {
      return this.field5932[var1];
   }

   @ObfuscatedSignature(descriptor = "(Lte;IIB)V")
   @ObfuscatedName("al")
   public static void method10465(classTE var0, int var1, int var2, byte var3) {
      try {
         classOL.method8544(var0, 1327640383);
         if (var1 >= 0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            if (var2 >= 0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               if (var1 < var0.method10797(-1023280051)) {
                  if (var3 >= 0) {
                     return;
                  }

                  if (var2 < var0.method10797(-767501679)) {
                     if (var2 != var1) {
                        if (var3 >= 0) {
                           return;
                        }

                        if (var0.field6126 == classYY.field7109) {
                           if (var3 >= 0) {
                              throw new IllegalStateException();
                           }

                           int[] var4 = var0.method10791(1384288442);
                           int var5 = var4[var1];
                           var4[var1] = var4[var2];
                           var4[var2] = var5;
                        } else if (classYY.field7113 == var0.field6126) {
                           long[] var8 = var0.method10793(-1063134522);
                           long var10 = var8[var1];
                           var8[var1] = var8[var2];
                           var8[var2] = var10;
                        } else {
                           Object[] var9 = var0.method10796(246853485);
                           Object var11 = var9[var1];
                           var9[var1] = var9[var2];
                           var9[var2] = var11;
                        }

                        var0.field6131 = true;
                     }

                     return;
                  }

                  if (var3 >= 0) {
                     return;
                  }
               }
            }
         }

         throw new RuntimeException();
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "sg.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsg;I)Luu;")
   @ObfuscatedName("ed")
   public static classUU method10439(classSG var0, int var1) {
      if (var0 == null) {
         var0.method10440(var1);
      }

      if (var0.field5924[var1] == null) {
         classUU var2 = var0.method10433(var1, (byte)72);
         if (var0.field5927 != null) {
            var2 = new classUU(var2);
            var2.method11656(var0.field5927.method10470(var1, -1652078851), -717763018);
         }

         var0.field5924[var1] = var2;
      }

      return var0.field5924[var1];
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIII)Z")
   @ObfuscatedName("ci")
   static boolean method10467(WorldView var0, int var1, int var2, int var3, int var4) {
      try {
         if (var1 < 3) {
            if (var4 <= 718661024) {
               throw new IllegalStateException();
            }

            if (2 == (var0.field1700[1][var2][var3] & 2)) {
               if (var4 <= 718661024) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "sg.ci(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("ar")
   classUU method10440(int var1) {
      if (this.field5925[var1] == null) {
         classUU var2 = this.method10433(var1, (byte)72);
         if (this.field5927 != null) {
            var2 = new classUU(var2);
            var2.method11656(this.field5927.method10470(var1, -1652078851), -717763018);
         }

         this.field5924[var1] = var2;
      }

      return this.field5924[var1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsg;I)Luu;")
   @ObfuscatedName("oc")
   public static classUU method10442(classSG var0, int var1) {
      if (null == var0.field5925[var1]) {
         var0.field5925[var1] = new classUU(var0.method10470(var1, -1635901835));
         classUU.method11670(var0.field5925[var1], -1314355848);
      }

      return var0.field5925[var1];
   }

   @ObfuscatedSignature(descriptor = "(IB)Luu;")
   @ObfuscatedName("ab")
   classUU method10475(int var1, byte var2) {
      if (this.field5924[var1] == null) {
         classUU var3 = new classUU(this.method10477(var1));
         var3.method11638();
         this.field5924[var1] = var3;
      }

      return this.field5925[var1];
   }
}
