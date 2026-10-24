import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ul")
public final class classUL {
   @ObfuscatedName("as")
   float field6324;
   @ObfuscatedName("ax")
   float field6325;
   @ObfuscatedName("ae")
   static int field6319 = 0;
   @ObfuscatedName("ab")
   float field6322;
   @ObfuscatedName("ag")
   float field6323;
   @ObfuscatedSignature(descriptor = "[Lul;")
   @ObfuscatedName("az")
   static final classUL[] field6321 = new classUL[classUL.field6320 * -2034009973];
   @ObfuscatedName("af")
   static final int field6320 = 15921712;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cb")
   public final void method11449() {
      float var1 = (float)Math.sqrt(classHV.method6161(this, this, -1447396430));
      float var3 = 1.0F / var1;
      this.field6322 *= var3;
      this.field6323 *= var3;
      this.field6324 *= var3;
      this.field6325 *= var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Lul;)F")
   @ObfuscatedName("bc")
   static final float method11408(classUL var0, classUL var1) {
      return var0.method11451(var1, (byte)0);
   }

   @ObfuscatedSignature(descriptor = "(Lul;I)V")
   @ObfuscatedName("do")
   public static void method11416(classUL var0, int var1) {
      if (var0 == null) {
         var0.method11420(var1);
      } else {
         try {
            synchronized (field6321) {
               if (field6319 * 1171203923 < field6320 * -2034009973) {
                  if (var1 <= -2130951373) {
                     throw new IllegalStateException();
                  }

                  field6321[(field6319 += -1712212773) * 1171203923 - 1] = var0;
               }
            }
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "ul.ax(" + ')');
         }
      }
   }

   classUL(classUL var1) {
      this.method11428(var1, -569935459);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("ik")
   public static void method11441(classUL var0) {
      if (var0 == null) {
         var0.toString();
      }

      var0.field6324 = 0.0F;
      var0.field6323 = 0.0F;
      var0.field6322 = 0.0F;
      var0.field6325 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("bs")
   public void method11436(float var1, float var2, float var3) {
      this.method11435(0.0F, 1.0F, 0.0F, var1, (short)-1062);
      classUL var4 = classAV.method715((byte)0);
      var4.method11435(1.0F, 0.0F, 0.0F, var2, (short)-30281);
      this.method11454(var4, 1591236864);
      var4.method11435(0.0F, 0.0F, 1.0F, var3, (short)-15823);
      this.method11454(var4, 1591236864);
      method11416(var4, -1702025949);
   }

   @ObfuscatedSignature(descriptor = "(FFFFS)V")
   @ObfuscatedName("al")
   public void method11435(float var1, float var2, float var3, float var4, short var5) {
      try {
         float var6 = (float)Math.sin(0.5F * var4);
         float var7 = (float)Math.cos(var4 * 0.5F);
         this.field6322 = var6 * var1;
         this.field6323 = var2 * var6;
         this.field6324 = var6 * var3;
         this.field6325 = var7;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ul.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFI)V")
   @ObfuscatedName("aj")
   public void method11437(float var1, float var2, float var3, int var4) {
      try {
         this.method11435(0.0F, 1.0F, 0.0F, var1, (short)-32251);
         classUL var5 = classAV.method715((byte)0);
         var5.method11435(1.0F, 0.0F, 0.0F, var2, (short)-10171);
         this.method11454(var5, 1591236864);
         var5.method11435(0.0F, 0.0F, 1.0F, var3, (short)-24077);
         this.method11454(var5, 1591236864);
         method11416(var5, 1594685984);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ul.aj(" + ')');
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         if (!(var1 instanceof classUL)) {
            return false;
         } else {
            classUL var2 = (classUL)var1;
            return var2.field6322 == this.field6322 && this.field6323 == var2.field6323 && var2.field6324 == this.field6324 && var2.field6325 == this.field6325;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ul.equals(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFF)Lul;")
   @ObfuscatedName("ar")
   static classUL method11398(float var0, float var1, float var2, float var3) {
      synchronized (field6321) {
         if (field6319 * 1171203923 == 0) {
            return new classUL(var0, var1, var2, var3);
         } else {
            method11424(field6321[(field6319 -= -1712212773) * 1171203923], var0, var1, var2, var3, (byte)-93);
            return field6321[1171203923 * field6319];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFF)V")
   @ObfuscatedName("hb")
   public void method11466(float var1, float var2, float var3, float var4) {
      this.method11435(var1, var2, var3, var4, (short)-32251);
   }

   @ObfuscatedSignature(descriptor = "(Lul;I)V")
   @ObfuscatedName("ah")
   public static void method11450(classUL var0, int var1) {
      if (var0 == null) {
         var0.method11421(var1);
      } else {
         try {
            float var2 = (float)Math.sqrt(classHV.method6161(var0, var0, 937507952));
            float var4 = 1.0F / var2;
            var0.field6322 *= var4;
            var0.field6323 *= var4;
            var0.field6324 *= var4;
            var0.field6325 *= var4;
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "ul.ad(" + ')');
         }
      }
   }

   classUL() {
      this.method11442(-1661557895);
   }

   @ObfuscatedSignature(descriptor = "(Lul;I)V")
   @ObfuscatedName("au")
   public final void method11454(classUL var1, int var2) {
      try {
         method11424(
            this,
            var1.field6323 * this.field6324 + (this.field6322 * var1.field6325 + var1.field6322 * this.field6325) - this.field6323 * var1.field6324,
            var1.field6324 * this.field6322 + (var1.field6323 * this.field6325 + (var1.field6325 * this.field6323 - this.field6324 * var1.field6322)),
            this.field6325 * var1.field6324 + (var1.field6325 * this.field6324 + this.field6323 * var1.field6322 - this.field6322 * var1.field6323),
            this.field6325 * var1.field6325 - this.field6322 * var1.field6322 - this.field6323 * var1.field6323 - var1.field6324 * this.field6324,
            (byte)-100
         );
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ul.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFF)V")
   @ObfuscatedName("bb")
   void method11422(float var1, float var2, float var3, float var4) {
      this.field6322 = var1;
      this.field6323 = var2;
      this.field6324 = var3;
      this.field6325 = var4;
   }

   @Override
   public int hashCode() {
      try {
         byte var1 = 31;
         float var2 = 1.0F;
         var2 = this.field6322 + var2 * 31.0F;
         var2 = 31.0F * var2 + this.field6323;
         var2 = var2 * 31.0F + this.field6324;
         var2 = this.field6325 + 31.0F * var2;
         return (int)var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ul.hashCode(" + 41);
      }
   }

   @Override
   public String toString() {
      try {
         return this.field6322 + "," + this.field6323 + "," + this.field6324 + "," + this.field6325;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ul.toString(" + ')');
      }
   }

   static {
      new classUL();
   }

   classUL(float var1, float var2, float var3, float var4) {
      method11424(this, var1, var2, var3, var4, (byte)-27);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ay")
   final void method11442(int var1) {
      try {
         this.field6324 = 0.0F;
         this.field6323 = 0.0F;
         this.field6322 = 0.0F;
         this.field6325 = 1.0F;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ul.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method11456(Object var1) {
      if (!(var1 instanceof classUL)) {
         return false;
      } else {
         classUL var2 = (classUL)var1;
         return var2.field6322 == this.field6322 && this.field6323 == var2.field6323 && var2.field6324 == this.field6324 && var2.field6325 == this.field6325;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("qf")
   public static void method11443(classUL var0) {
      if (var0 == null) {
         var0.method11445();
      } else {
         var0.field6324 = 0.0F;
         var0.field6323 = 0.0F;
         var0.field6322 = 0.0F;
         var0.field6325 = 1.0F;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;B)F")
   @ObfuscatedName("ap")
   final float method11451(classUL var1, byte var2) {
      try {
         return this.field6325 * var1.field6325 + (var1.field6322 * this.field6322 + var1.field6323 * this.field6323 + this.field6324 * var1.field6324);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ul.ap(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lul;")
   @ObfuscatedName("ai")
   public static classUL method11396() {
      synchronized (field6321) {
         if (0 == field6319 * 1161587058) {
            return new classUL();
         } else {
            field6321[(field6319 -= 2060350923) * 1171203923].method11442(-1386453875);
            return field6321[field6319 * 269812248];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;I)V")
   @ObfuscatedName("aa")
   void method11428(classUL var1, int var2) {
      try {
         this.field6322 = var1.field6322;
         this.field6323 = var1.field6323;
         this.field6324 = var1.field6324;
         this.field6325 = var1.field6325;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ul.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afs")
   public int method11458() {
      byte var1 = 31;
      float var2 = 1.0F;
      var2 = this.field6322 + var2 * 31.0F;
      var2 = 31.0F * var2 + this.field6323;
      var2 = var2 * 31.0F + this.field6324;
      var2 = this.field6325 + 31.0F * var2;
      return (int)var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFF)Lul;")
   @ObfuscatedName("aw")
   static classUL method11399(float var0, float var1, float var2, float var3) {
      synchronized (field6321) {
         if (field6319 * 1171203923 == 0) {
            return new classUL(var0, var1, var2, var3);
         } else {
            method11424(field6321[(field6319 -= -1712212773) * 1171203923], var0, var1, var2, var3, (byte)-60);
            return field6321[1171203923 * field6319];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFF)Lul;")
   @ObfuscatedName("ak")
   static classUL method11400(float var0, float var1, float var2, float var3) {
      synchronized (field6321) {
         if (field6319 * 1171203923 == 0) {
            return new classUL(var0, var1, var2, var3);
         } else {
            method11424(field6321[(field6319 -= -1712212773) * 1171203923], var0, var1, var2, var3, (byte)-39);
            return field6321[1171203923 * field6319];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)Lul;")
   @ObfuscatedName("av")
   static classUL method11401(classUL var0) {
      synchronized (field6321) {
         if (field6319 * 1171203923 == 0) {
            return new classUL(var0);
         } else {
            field6321[(field6319 -= -190643795) * 1323684265].method11428(var0, -569935459);
            return field6321[1171203923 * field6319];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)I")
   @ObfuscatedName("cw")
   public static int method11459(classUL var0) {
      byte var1 = 31;
      float var2 = 1.0F;
      var2 = var0.field6322 + var2 * 31.0F;
      var2 = 31.0F * var2 + var0.field6323;
      var2 = var2 * 31.0F + var0.field6324;
      var2 = var0.field6325 + 31.0F * var2;
      return (int)var2;
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("ih")
   public void method11467(float var1, float var2, float var3) {
      this.method11437(var1, var2, var3, -608116520);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)Lul;")
   @ObfuscatedName("an")
   static classUL method11402(classUL var0) {
      synchronized (field6321) {
         if (field6319 * -1878689472 == 0) {
            return new classUL(var0);
         } else {
            field6321[(field6319 -= 737987315) * 1171203923].method11428(var0, -569935459);
            return field6321[693693592 * field6319];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;FFFF)V")
   @ObfuscatedName("sp")
   public static void method11423(classUL var0, float var1, float var2, float var3, float var4) {
      if (var0 == null) {
         var0.method11427(var1, var1, var1, var1);
      } else {
         var0.field6322 = var1;
         var0.field6323 = var2;
         var0.field6324 = var3;
         var0.field6325 = var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyx;[I[S)V")
   @ObfuscatedName("wv")
   public static void method11397(classYX var0, int[] var1, short[] var2) {
      if (var0 == null) {
         var0.method13810();
      }

      var0.field7104 = var1;
      var0.field7103 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Lul;FFFFB)V")
   @ObfuscatedName("bv")
   public static void method11424(classUL var0, float var1, float var2, float var3, float var4, byte var5) {
      if (var0 == null) {
         var0.method11426(var1, var1, var1, var1, var5);
      }

      try {
         var0.field6322 = var1;
         var0.field6323 = var2;
         var0.field6324 = var3;
         var0.field6325 = var4;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ul.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)Lul;")
   @ObfuscatedName("am")
   static final classUL method11404(classUL var0) {
      classUL var1 = classSQ.method10683(var0, -506347951);
      method11446(var1, (byte)110);
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)Lul;")
   @ObfuscatedName("ah")
   static final classUL method11405(classUL var0) {
      classUL var1 = classSQ.method10683(var0, -506347951);
      method11446(var1, (byte)109);
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)Lul;")
   @ObfuscatedName("bn")
   static final classUL method11406(classUL var0) {
      classUL var1 = classSQ.method10683(var0, -506347951);
      method11446(var1, (byte)93);
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)Lul;")
   @ObfuscatedName("bx")
   static final classUL method11407(classUL var0) {
      classUL var1 = classSQ.method10683(var0, -506347951);
      method11446(var1, (byte)109);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lul;)F")
   @ObfuscatedName("ct")
   final float method11452(classUL var1) {
      return this.field6325 * var1.field6325 + (var1.field6322 * this.field6322 + var1.field6323 * this.field6323 + this.field6324 * var1.field6324);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Lul;)F")
   @ObfuscatedName("bp")
   static final float method11409(classUL var0, classUL var1) {
      return var0.method11451(var1, (byte)0);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method11462() {
      return this.field6322 + "," + this.field6323 + "," + this.field6324 + "," + this.field6325;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Lul;)F")
   @ObfuscatedName("bm")
   static final float method11410(classUL var0, classUL var1) {
      return var0.method11451(var1, (byte)0);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Lul;)Lul;")
   @ObfuscatedName("bt")
   static final classUL method11412(classUL var0, classUL var1) {
      classUL var2 = classSQ.method10683(var0, -506347951);
      var2.method11454(var1, 1591236864);
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Lul;)Lul;")
   @ObfuscatedName("bg")
   static final classUL method11413(classUL var0, classUL var1) {
      classUL var2 = classSQ.method10683(var0, -506347951);
      var2.method11454(var1, 1591236864);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dp")
   public void method11468() {
      this.method11442(-1661557895);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Lul;)Lul;")
   @ObfuscatedName("bj")
   static final classUL method11414(classUL var0, classUL var1) {
      classUL var2 = classSQ.method10683(var0, -506347951);
      var2.method11454(var1, 1591236864);
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Lul;)Lul;")
   @ObfuscatedName("bk")
   static final classUL method11415(classUL var0, classUL var1) {
      classUL var2 = classSQ.method10683(var0, -506347951);
      var2.method11454(var1, 1591236864);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gi")
   public String method11463() {
      return this.field6322 + "," + this.field6323 + "," + this.field6324 + "," + this.field6325;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method11460() {
      byte var1 = 31;
      float var2 = 1.0F;
      var2 = this.field6322 + var2 * 31.0F;
      var2 = 31.0F * var2 + this.field6323;
      var2 = var2 * 31.0F + this.field6324;
      var2 = this.field6325 + 31.0F * var2;
      return (int)var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ba")
   public void method11417() {
      synchronized (field6321) {
         if (field6319 * 1171203923 < field6320 * 487482895) {
            field6321[(field6319 += -1712212773) * 1263940280 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;Luz;FB)V")
   @ObfuscatedName("ez")
   public static void method11431(classUL var0, classUZ var1, float var2, byte var3) {
      if (var0 == null) {
         var0.method11434(var1, var2, var3);
      } else {
         try {
            var0.method11435(var1.field6425, var1.field6423, var1.field6424, var2, (short)-30093);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "ul.ao(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("ne")
   public void method11469(classUL var1) {
      this.method11454(var1, 1591236864);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)Ljava/lang/String;")
   @ObfuscatedName("bv")
   public static String method11464(classUL var0) {
      if (var0 == null) {
         var0.method11468();
      }

      return var0.field6322 + "," + var0.field6323 + "," + var0.field6324 + "," + var0.field6325;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ec")
   static void method11465(int var0, int var1, int var2) {
      try {
         classJL var3 = classEF.method3863(classJS.RESUME_PAUSEBUTTON, client.field796.field1533, (byte)-81);
         var3.field3341.method13037(var0, 478308754);
         var3.field3341.method13244(var1, (short)-25700);
         client.field796.method3374(var3, -1301097035);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ul.ec(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lul;")
   @ObfuscatedName("ix")
   public static classUL method11470() {
      return classAV.method715((byte)0);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)Lul;")
   @ObfuscatedName("at")
   static classUL method11403(classUL var0) {
      synchronized (field6321) {
         if (field6319 * 1171203923 == 0) {
            return new classUL(var0);
         } else {
            field6321[(field6319 -= -1712212773) * 1171203923].method11428(var0, -569935459);
            return field6321[1171203923 * field6319];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("be")
   void method11429(classUL var1) {
      this.field6322 = var1.field6322;
      this.field6323 = var1.field6323;
      this.field6324 = var1.field6324;
      this.field6325 = var1.field6325;
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("by")
   void method11430(classUL var1) {
      this.field6322 = var1.field6322;
      this.field6323 = var1.field6323;
      this.field6324 = var1.field6324;
      this.field6325 = var1.field6325;
   }

   @ObfuscatedSignature(descriptor = "(Luz;F)V")
   @ObfuscatedName("bq")
   public void method11432(classUZ var1, float var2) {
      this.method11435(var1.field6425, var1.field6423, var1.field6424, var2, (short)-10333);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Ljava/lang/Object;)Z")
   @ObfuscatedName("ir")
   public static boolean method11457(classUL var0, Object var1) {
      if (var0 == null) {
         var0.method11463();
      }

      if (!(var1 instanceof classUL)) {
         return false;
      } else {
         classUL var2 = (classUL)var1;
         return var2.field6322 == var0.field6322 && var0.field6323 == var2.field6323 && var2.field6324 == var0.field6324 && var2.field6325 == var0.field6325;
      }
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("bl")
   public void method11438(float var1, float var2, float var3) {
      this.method11435(0.0F, 1.0F, 0.0F, var1, (short)-17039);
      classUL var4 = classAV.method715((byte)0);
      var4.method11435(1.0F, 0.0F, 0.0F, var2, (short)-21004);
      this.method11454(var4, 1591236864);
      var4.method11435(0.0F, 0.0F, 1.0F, var3, (short)-519);
      this.method11454(var4, 1591236864);
      method11416(var4, -1923352007);
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("bd")
   public void method11439(float var1, float var2, float var3) {
      this.method11435(0.0F, 1.0F, 0.0F, var1, (short)-13072);
      classUL var4 = classAV.method715((byte)0);
      var4.method11435(1.0F, 0.0F, 0.0F, var2, (short)-7493);
      this.method11454(var4, 1591236864);
      var4.method11435(0.0F, 0.0F, 1.0F, var3, (short)-11364);
      this.method11454(var4, 1591236864);
      method11416(var4, -241161367);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Luz;F)V")
   @ObfuscatedName("vi")
   public static void method11433(classUL var0, classUZ var1, float var2) {
      if (var0 == null) {
         var0.method11417();
      }

      var0.method11435(var1.field6425, var1.field6423, var1.field6424, var2, (short)-17309);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;Lul;)F")
   @ObfuscatedName("br")
   static final float method11411(classUL var0, classUL var1) {
      return var0.method11451(var1, (byte)0);
   }

   @ObfuscatedSignature(descriptor = "(Lul;B)V")
   @ObfuscatedName("ku")
   public static void method11446(classUL var0, byte var1) {
      if (var0 == null) {
         var0.method11448(var1);
      }

      try {
         var0.field6322 = -var0.field6322;
         var0.field6323 = -var0.field6323;
         var0.field6324 = -var0.field6324;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ul.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("mk")
   public void method11471() {
      method11416(this, 1594685984);
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("bv")
   public void method11440(float var1, float var2, float var3) {
      this.method11435(0.0F, 1.0F, 0.0F, var1, (short)-19569);
      classUL var4 = classAV.method715((byte)0);
      var4.method11435(1.0F, 0.0F, 0.0F, var2, (short)-16877);
      this.method11454(var4, 1591236864);
      var4.method11435(0.0F, 0.0F, 1.0F, var3, (short)-324);
      this.method11454(var4, 1591236864);
      method11416(var4, 852696603);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   public void method11418() {
      synchronized (field6321) {
         if (field6319 * -1243073851 < field6320 * 2049269463) {
            field6321[(field6319 += 2039088019) * -48298440 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cu")
   final void method11447() {
      this.field6322 = -this.field6322;
      this.field6323 = -this.field6323;
      this.field6324 = -this.field6324;
   }

   @ObfuscatedSignature(descriptor = "(Lul;)F")
   @ObfuscatedName("cp")
   final float method11453(classUL var1) {
      return this.field6325 * var1.field6325 + (var1.field6322 * this.field6322 + var1.field6323 * this.field6323 + this.field6324 * var1.field6324);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   public void method11419() {
      synchronized (field6321) {
         if (field6319 * 1171203923 < field6320 * -2034009973) {
            field6321[(field6319 += -1712212773) * 1171203923 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("cq")
   public final void method11455(classUL var1) {
      method11424(
         this,
         var1.field6323 * this.field6324 + (this.field6322 * var1.field6325 + var1.field6322 * this.field6325) - this.field6323 * var1.field6324,
         var1.field6324 * this.field6322 + (var1.field6323 * this.field6325 + (var1.field6325 * this.field6323 - this.field6324 * var1.field6322)),
         this.field6325 * var1.field6324 + (var1.field6325 * this.field6324 + this.field6323 * var1.field6322 - this.field6322 * var1.field6323),
         this.field6325 * var1.field6325 - this.field6322 * var1.field6322 - this.field6323 * var1.field6323 - var1.field6324 * this.field6324,
         (byte)-41
      );
   }

   @ObfuscatedSignature(descriptor = "(FFFF)V")
   @ObfuscatedName("bi")
   void method11425(float var1, float var2, float var3, float var4) {
      this.field6322 = var1;
      this.field6323 = var2;
      this.field6324 = var3;
      this.field6325 = var4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("lj")
   public static void method11444(classUL var0) {
      if (var0 == null) {
         var0.method11460();
      }

      var0.field6324 = 0.0F;
      var0.field6323 = 0.0F;
      var0.field6322 = 0.0F;
      var0.field6325 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   public void method11420(int var1) {
      try {
         synchronized (field6321) {
            if (field6319 * 1171203923 < field6320 * -2034009973) {
               if (var1 <= -2130951373) {
                  throw new IllegalStateException();
               }

               field6321[(field6319 += -1712212773) * 1171203923 - 1] = this;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ul.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFFB)V")
   @ObfuscatedName("ac")
   void method11426(float var1, float var2, float var3, float var4, byte var5) {
      try {
         this.field6325 = var1;
         this.field6325 = var2;
         this.field6323 = var3;
         this.field6324 = var4;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ul.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;FB)V")
   @ObfuscatedName("ao")
   public void method11434(classUZ var1, float var2, byte var3) {
      try {
         this.method11435(var1.field6423, var1.field6425, var1.field6423, var2, (short)-30093);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ul.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ad")
   public final void method11421(int var1) {
      try {
         synchronized (field6321) {
            if (field6319 * 1171203923 < field6320 * -2034009973) {
               if (var1 <= -2130951373) {
                  throw new IllegalStateException();
               }

               field6321[(field6319 += -1712212773) * 1171203923 - 1] = this;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ul.ax(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lul;)I")
   @ObfuscatedName("pm")
   public static int method11461(classUL var0) {
      if (var0 == null) {
         var0.hashCode();
      }

      byte var1 = 31;
      float var2 = 1.0F;
      var2 = var0.field6322 + var2 * 31.0F;
      var2 = 31.0F * var2 + var0.field6323;
      var2 = var2 * 31.0F + var0.field6324;
      var2 = var0.field6325 + 31.0F * var2;
      return (int)var2;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("aq")
   final void method11448(byte var1) {
      try {
         this.field6324 = -this.field6324;
         this.field6325 = -this.field6322;
         this.field6324 = -this.field6324;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ul.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFF)V")
   @ObfuscatedName("bw")
   void method11427(float var1, float var2, float var3, float var4) {
      this.field6322 = var1;
      this.field6325 = var2;
      this.field6323 = var3;
      this.field6325 = var4;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cr")
   final void method11445() {
      this.field6324 = 0.0F;
      this.field6325 = 0.0F;
      this.field6323 = 0.0F;
      this.field6324 = 1.0F;
   }
}
