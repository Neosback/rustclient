import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("od")
public class EnumComposition extends classVJ implements net.runelite.api.EnumComposition {
   @ObfuscatedName("ay")
   final String field4825;
   @ObfuscatedName("ar")
   final long[] field4827;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4830 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4829 = 4;
   @ObfuscatedSignature(descriptor = "Lte;")
   @ObfuscatedName("av")
   classTE field4836;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ao")
   static NodeCache field4818 = new NodeCache(64);
   @ObfuscatedName("al")
   final char field4823;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field4832 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4831 = 2;
   @ObfuscatedName("aq")
   final int field4819;
   @ObfuscatedName("aj")
   final char field4820;
   @ObfuscatedName("ap")
   final int field4821;
   @ObfuscatedName("au")
   final int[] field4822;
   @ObfuscatedName("ai")
   final int[] field4826;
   @ObfuscatedName("ad")
   final long field4824;
   @ObfuscatedName("aw")
   final String[] field4828;
   @ObfuscatedSignature(descriptor = "Lte;")
   @ObfuscatedName("ak")
   classTE field4835;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4833 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("cs")
   static final int field4834 = 253;

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("av")
   public String method8357(int var1) {
      for (int var2 = 0; var2 < 592403487 * this.field4821; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4828[var2];
         }
      }

      return this.field4825;
   }

   @ObfuscatedSignature(descriptor = "(II)J")
   @ObfuscatedName("ac")
   public long method8368(int var1, int var2) {
      try {
         for (int var3 = 0; var3 < this.field4821 * -49805; var3++) {
            if (var2 == -560924540) {
               throw new IllegalStateException();
            }

            if (this.field4822[var3] == var1) {
               if (var2 == -560924540) {
                  throw new IllegalStateException();
               }

               return this.field4827[var3];
            }
         }

         return this.field4824;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "od.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ax")
   public int method8362(int var1, int var2) {
      try {
         for (int var3 = 0; var3 < this.field4821 * -49805; var3++) {
            if (var2 != -1739848449) {
               throw new IllegalStateException();
            }

            if (this.field4822[var3] == var1) {
               if (var2 != -1739848449) {
                  throw new IllegalStateException();
               }

               return this.field4826[var3];
            }
         }

         return this.field4819 * -1138023367;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "od.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lte;")
   @ObfuscatedName("ag")
   public classTE method8354(byte var1) {
      try {
         if (this.field4836 == null) {
            if (var1 >= 49) {
               throw new IllegalStateException();
            }

            if (this.field4820 == 's') {
               if (var1 >= 49) {
                  throw new IllegalStateException();
               }

               String[] var3 = this.field4828;
               classTE var4 = new classTE(classYY.field7111, false);
               var4.field6123 = var3;
               var4.field6129 = var3.length * -1063196331;
               var4.field6121 = 404453943 * var3.length;
               this.field4836 = var4;
            } else if (this.field4820 == 207) {
               if (var1 >= 49) {
                  throw new IllegalStateException();
               }

               long[] var6 = this.field4827;
               classTE var7 = new classTE(classYY.field7113, false);
               var7.field6125 = var6;
               var7.field6129 = -1063196331 * var6.length;
               var7.field6121 = 404453943 * var6.length;
               this.field4836 = var7;
            } else {
               this.field4836 = classDG.method3085(this.field4826, -530642757);
            }
         }

         return this.field4836;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "od.ag(" + ')');
      }
   }

   public String[] getStringVals() {
      return this.field4828;
   }

   public long getLongValue(int var1) {
      int var2 = this.method8384(var1);
      return var2 == -1 ? this.field4824 : this.getLongVals()[var2];
   }

   public long[] getLongVals() {
      return this.field4827;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("nm")
   public int method8384(int var1) {
      int[] var2 = this.getKeys();
      if (var2 == null) {
         return -1;
      } else {
         for (int var3 = 0; var3 < var2.length; var3++) {
            if (var2[var3] == var1) {
               return var3;
            }
         }

         return -1;
      }
   }

   public String getStringValue(int var1) {
      int var2 = this.method8384(var1);
      return var2 == -1 ? this.field4825 : this.getStringVals()[var2];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lod;C)Z")
   @ObfuscatedName("mi")
   public static boolean method8372(EnumComposition var0, char var1) {
      return var0 == null ? var0.method8376(var1) : var1 == var0.field4823;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lod;")
   @ObfuscatedName("al")
   public static EnumComposition method8342(int var0) {
      EnumComposition var1 = (EnumComposition)NodeCache.method6460(field4818, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classLH.field4166.method11942(8, var0, -1053320097);
         var1 = new EnumComposition(new classXY(var2));
         field4818.method6464(var1, var0);
         return var1;
      }
   }

   public int[] getIntVals() {
      return this.field4826;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public static void method8344() {
      field4818.method6468();
   }

   @ObfuscatedSignature(descriptor = "(Lod;S)Lte;")
   @ObfuscatedName("zi")
   public static classTE method8349(EnumComposition var0, short var1) {
      if (var0 == null) {
         var0.method8353(var1);
      }

      try {
         if (var0.field4835 == null) {
            if (var1 <= 16383) {
               throw new IllegalStateException();
            }

            var0.field4835 = classDG.method3085(var0.field4822, -530642757);
         }

         return var0.field4835;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "od.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public static void method8345() {
      field4818.method6468();
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("as")
   static void method8343(int var0, int var1) {
      try {
         ItemContainer var2 = (ItemContainer)HashTable.method13432(ItemContainer.field454, var0);
         if (null == var2) {
            if (var1 == -14238601) {
               throw new IllegalStateException();
            }
         } else {
            var2.vmethod398();
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "od.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lod;II)Ljava/lang/String;")
   @ObfuscatedName("ed")
   public static String method8358(EnumComposition var0, int var1, int var2) {
      if (var0 == null) {
         var0.method8361(var1, var1);
      }

      try {
         for (int var3 = 0; var3 < -49805 * var0.field4821; var3++) {
            if (var2 != -1652195152) {
               throw new IllegalStateException();
            }

            if (var0.field4822[var3] == var1) {
               if (var2 != -1652195152) {
                  throw new IllegalStateException();
               }

               return var0.field4828[var3];
            }
         }

         return var0.field4825;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "od.as(" + ')');
      }
   }

   EnumComposition(classXY var1) {
      char var2 = 0;
      char var3 = 0;
      String var4 = classKK.field3613;
      int var5 = 0;
      long var6 = 0L;
      int var8 = 0;
      int[] var9 = null;
      int[] var10 = null;
      long[] var11 = null;
      String[] var12 = null;
      if (var1 != null && null != var1.field6952) {
         label74:
         while (true) {
            int var13 = var1.method13095(-346779531);
            switch (var13) {
               case 0:
                  break label74;
               case 1:
                  var2 = (char)var1.method13095(-346779531);
                  break;
               case 2:
                  var3 = (char)var1.method13095(-346779531);
                  break;
               case 3:
                  var4 = var1.method13127(-58225108);
                  break;
               case 4:
                  var5 = classXY.method13113(var1, (byte)1);
                  break;
               case 5:
                  var8 = var1.method13099(-158140219);
                  var9 = new int[var8];
                  var12 = new String[var8];

                  for (int var16 = 0; var16 < var8; var16++) {
                     var9[var16] = classXY.method13113(var1, (byte)1);
                     var12[var16] = var1.method13127(798287587);
                  }
                  break;
               case 6:
                  var8 = var1.method13099(1682822604);
                  var9 = new int[var8];
                  var10 = new int[var8];

                  for (int var15 = 0; var15 < var8; var15++) {
                     var9[var15] = classXY.method13113(var1, (byte)1);
                     var10[var15] = classXY.method13113(var1, (byte)1);
                  }
                  break;
               case 7:
                  var8 = var1.method13099(1053909075);
                  var9 = new int[var8];
                  var11 = new long[var8];

                  for (int var14 = 0; var14 < var8; var14++) {
                     var9[var14] = classXY.method13113(var1, (byte)1);
                     var11[var14] = classXY.method13115(var1, 268905974);
                  }
                  break;
               case 8:
                  var6 = classXY.method13115(var1, -967525541);
            }
         }
      }

      this.field4823 = var2;
      this.field4820 = var3;
      this.field4825 = var4;
      this.field4819 = 983403017 * var5;
      this.field4824 = var6;
      this.field4821 = 1495412667 * var8;
      this.field4822 = var9;
      this.field4826 = var10;
      this.field4827 = var11;
      this.field4828 = var12;
   }

   @ObfuscatedSignature(descriptor = "()Lte;")
   @ObfuscatedName("ap")
   public classTE method8350() {
      if (this.field4835 == null) {
         this.field4835 = classDG.method3085(this.field4822, -530642757);
      }

      return this.field4835;
   }

   @ObfuscatedSignature(descriptor = "(Lod;CI)Z")
   @ObfuscatedName("qd")
   public static boolean method8373(EnumComposition var0, char var1, int var2) {
      if (var0 == null) {
         return var0.method8375(var1, var2);
      } else {
         try {
            boolean var10000;
            if (var1 == var0.field4823) {
               if (var2 <= 1625824677) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "od.aa(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lod;I)I")
   @ObfuscatedName("mp")
   public static int method8363(EnumComposition var0, int var1) {
      if (var0 == null) {
         var0.method8359(var1);
      }

      for (int var2 = 0; var2 < var0.field4821 * -49805; var2++) {
         if (var0.field4822[var2] == var1) {
            return var0.field4826[var2];
         }
      }

      return var0.field4819 * -1138023367;
   }

   @ObfuscatedSignature(descriptor = "(CB)Z")
   @ObfuscatedName("ao")
   public boolean method8377(char var1, byte var2) {
      try {
         boolean var10000;
         if (var1 == this.field4820) {
            if (var2 >= 1) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "od.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lte;")
   @ObfuscatedName("ai")
   public classTE method8351() {
      if (this.field4835 == null) {
         this.field4835 = classDG.method3085(this.field4822, -530642757);
      }

      return this.field4835;
   }

   public int getIntValue(int var1) {
      int var2 = this.method8384(var1);
      return var2 == -1 ? this.field4819 * -1138023367 : this.getIntVals()[var2];
   }

   @ObfuscatedSignature(descriptor = "()Lte;")
   @ObfuscatedName("aw")
   public classTE method8355() {
      if (this.field4836 == null) {
         if (this.field4820 == 's') {
            String[] var2 = this.field4828;
            classTE var3 = new classTE(classYY.field7111, false);
            var3.field6123 = var2;
            var3.field6129 = var2.length * 1156792243;
            var3.field6121 = 404453943 * var2.length;
            this.field4836 = var3;
         } else if (this.field4820 == 207) {
            long[] var4 = this.field4827;
            classTE var5 = new classTE(classYY.field7113, false);
            var5.field6125 = var4;
            var5.field6129 = -168386912 * var4.length;
            var5.field6121 = -754990032 * var4.length;
            this.field4836 = var5;
         } else {
            this.field4836 = classDG.method3085(this.field4826, -530642757);
         }
      }

      return this.field4836;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ak")
   public String method8359(int var1) {
      for (int var2 = 0; var2 < -49805 * this.field4821; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4828[var2];
         }
      }

      return this.field4825;
   }

   @ObfuscatedSignature(descriptor = "()Lte;")
   @ObfuscatedName("au")
   public classTE method8352() {
      if (this.field4835 == null) {
         this.field4835 = classDG.method3085(this.field4822, -530642757);
      }

      return this.field4835;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("at")
   public String method8360(int var1) {
      for (int var2 = 0; var2 < -49805 * this.field4821; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4828[var2];
         }
      }

      return this.field4825;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("an")
   public int method8364(int var1) {
      for (int var2 = 0; var2 < this.field4821 * -49805; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4826[var2];
         }
      }

      return this.field4819 * -1138023367;
   }

   public int size() {
      return this.field4821 * -49805;
   }

   @ObfuscatedSignature(descriptor = "(Lod;I)I")
   @ObfuscatedName("eq")
   public static int method8346(EnumComposition var0, int var1) {
      if (var0 == null) {
         var0.method8367(var1);
      }

      try {
         return -49805 * var0.field4821;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "od.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("am")
   public int method8365(int var1) {
      for (int var2 = 0; var2 < this.field4821 * -49805; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4826[var2];
         }
      }

      return this.field4819 * -436097230;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ah")
   public int method8366(int var1) {
      for (int var2 = 0; var2 < this.field4821 * -1046687206; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4826[var2];
         }
      }

      return this.field4819 * -1138023367;
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("bg")
   public boolean method8378(char var1) {
      return var1 == this.field4820;
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("bc")
   public long method8369(int var1) {
      for (int var2 = 0; var2 < this.field4821 * -49805; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4827[var2];
         }
      }

      return this.field4824;
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("bp")
   public long method8370(int var1) {
      for (int var2 = 0; var2 < this.field4821 * 1804948365; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4827[var2];
         }
      }

      return this.field4824;
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("br")
   public long method8371(int var1) {
      for (int var2 = 0; var2 < this.field4821 * -49805; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4827[var2];
         }
      }

      return this.field4824;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   public int method8347() {
      return -49805 * this.field4821;
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("bt")
   public boolean method8374(char var1) {
      return var1 == this.field4823;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;)Llx;")
   @ObfuscatedName("at")
   public static classLX method8382(Widget var0) {
      return var0.field4367;
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("bj")
   public boolean method8379(char var1) {
      return var1 == this.field4820;
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("bk")
   public boolean method8380(char var1) {
      return var1 == this.field4820;
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("bo")
   public boolean method8381(char var1) {
      return var1 == this.field4820;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   public int method8348() {
      return -49805 * this.field4821;
   }

   @ObfuscatedSignature(descriptor = "(B)Lsp;")
   @ObfuscatedName("ed")
   static WorldEntity method8383(byte var0) {
      try {
         return (WorldEntity)classIS.field3051.field1698.method13637(client.field847 * -2034209657);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "od.ed(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   public int method8367(int var1) {
      for (int var2 = 0; var2 < this.field4819 * -49805; var2++) {
         if (this.field4822[var2] == var1) {
            return this.field4826[var2];
         }
      }

      return this.field4821 * -436097230;
   }

   @ObfuscatedSignature(descriptor = "(S)Lte;")
   @ObfuscatedName("ab")
   public classTE method8353(short var1) {
      try {
         if (this.field4836 == null) {
            if (var1 <= 16383) {
               throw new IllegalStateException();
            }

            this.field4835 = classDG.method3085(this.field4822, -530642757);
         }

         return this.field4836;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "od.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lod;)Lte;")
   @ObfuscatedName("gt")
   public static classTE method8356(EnumComposition var0) {
      if (var0.field4836 == null) {
         if (var0.field4820 == 's') {
            String[] var2 = var0.field4828;
            classTE var3 = new classTE(classYY.field7111, false);
            var3.field6123 = var2;
            var3.field6129 = var2.length * -1095887332;
            var3.field6121 = 404453943 * var2.length;
            var0.field4836 = var3;
         } else if (var0.field4820 == -1475086442) {
            long[] var4 = var0.field4827;
            classTE var5 = new classTE(classYY.field7113, false);
            var5.field6125 = var4;
            var5.field6129 = 1850772652 * var4.length;
            var5.field6121 = 1203877263 * var4.length;
            var0.field4836 = var5;
         } else {
            var0.field4836 = classDG.method3085(var0.field4826, -530642757);
         }
      }

      return var0.field4836;
   }

   @ObfuscatedSignature(descriptor = "(CI)Z")
   @ObfuscatedName("aa")
   public boolean method8375(char var1, int var2) {
      try {
         boolean var10000;
         if (var1 == this.field4823) {
            if (var2 <= 1625824677) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "od.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Ljava/lang/String;")
   @ObfuscatedName("as")
   public String method8361(int var1, int var2) {
      try {
         for (int var3 = 0; var3 < -49805 * this.field4819; var3++) {
            if (var2 != -1652195152) {
               throw new IllegalStateException();
            }

            if (this.field4822[var3] == var1) {
               if (var2 != -1652195152) {
                  throw new IllegalStateException();
               }

               return this.field4828[var3];
            }
         }

         return this.field4825;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "od.as(" + ')');
      }
   }

   public int[] getKeys() {
      return this.field4822;
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("bm")
   public boolean method8376(char var1) {
      return var1 == this.field4823;
   }
}
