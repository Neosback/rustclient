import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pi")
public class ColorTextureOverride implements net.runelite.api.ColorTextureOverride {
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field5262 = 8;
   @ObfuscatedSignature(descriptor = "Lfx;")
   @ObfuscatedName("aj")
   public Model field5266;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   public static final int field5264 = 91;
   @ObfuscatedName("as")
   public short[] field5259;
   @ObfuscatedName("ag")
   public short[] field5258;
   @ObfuscatedName("ax")
   int field5255 = 1424760829;
   @ObfuscatedName("ac")
   int field5256 = 286785645;
   @ObfuscatedName("aa")
   int field5257 = -1097885037;
   @ObfuscatedName("ao")
   int field5260 = 74289375;
   @ObfuscatedName("al")
   public int field5261;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5265 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field5263 = 2;

   @ObfuscatedSignature(descriptor = "(Lpi;II)Z")
   @ObfuscatedName("nu")
   public static boolean method8943(ColorTextureOverride var0, int var1, int var2) {
      if (var0 == null) {
         var0.method8946(var1, var1);
      }

      try {
         if (0 == var1) {
            if (var2 != 938234762) {
               throw new IllegalStateException();
            }

            if (-292131739 * var0.field5257 != -1) {
               return true;
            }

            if (var2 != 938234762) {
               throw new IllegalStateException();
            }
         }

         if (var1 != 1 || 10867937 * var0.field5260 == -1) {
            return false;
         } else if (var2 != 938234762) {
            throw new IllegalStateException();
         } else {
            return true;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pi.ag(" + ')');
      }
   }

   public short[] getColorToReplaceWith() {
      return this.field5259;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzr;)Llw;")
   @ObfuscatedName("oa")
   public static Widget method8936(ScriptEvent var0) {
      return var0.field7244;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpi;I)Z")
   @ObfuscatedName("uw")
   public static boolean method8944(ColorTextureOverride var0, int var1) {
      return var0 == null ? var0.method8942(var1) : 0 == var1 && -292131739 * var0.field5257 != -1 || var1 == 1 && 10867937 * var0.field5260 != -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpi;I)Z")
   @ObfuscatedName("bd")
   public static boolean method8939(ColorTextureOverride var0, int var1) {
      if (var0 == null) {
         var0.method8937(var1);
      }

      return 0 == var1 && -1 != 956156587 * var0.field5255 || var1 == 1 && var0.field5256 * -1158941029 != -1;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("al")
   boolean method8940(int var1) {
      return 0 == var1 && -1 != 956156587 * this.field5255 || var1 == 1 && this.field5256 * -1158941029 != -1;
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ae")
   public boolean method8938(byte var1) {
      try {
         boolean var10000;
         if (null != this.field5258) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pi.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("au")
   int method8947(int var1) {
      return var1 == 0 ? 22782873 * this.field5255 : -1158941029 * this.field5256;
   }

   @ObfuscatedSignature(descriptor = "(Lpi;II)I")
   @ObfuscatedName("ni")
   public static int method8954(ColorTextureOverride var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method8956(var1, var1);
      } else {
         try {
            int var10000;
            if (0 == var1) {
               if (var2 == -1601733392) {
                  throw new IllegalStateException();
               }

               var10000 = var0.field5257 * -292131739;
            } else {
               var10000 = 10867937 * var0.field5260;
            }

            return var10000;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "pi.ax(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ad")
   int method8948(int var1) {
      return var1 == 0 ? -1063788746 * this.field5255 : -1158941029 * this.field5256;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ab")
   boolean method8941(int var1, int var2) {
      try {
         if (0 == var1) {
            if (var2 <= -1663814620) {
               throw new IllegalStateException();
            }

            if (-1 != 956156587 * this.field5255) {
               return true;
            }

            if (var2 <= -1663814620) {
               throw new IllegalStateException();
            }
         }

         if (var1 != 1 || this.field5256 * -1158941029 == -1) {
            return false;
         } else if (var2 <= -1663814620) {
            throw new IllegalStateException();
         } else {
            return true;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pi.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ay")
   boolean method8942(int var1) {
      return 0 == var1 && -1 != 956156587 * this.field5255 || var1 == 1 && this.field5256 * -1158941029 != -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILxy;)Lpi;")
   @ObfuscatedName("aa")
   public static ColorTextureOverride method8934(int var0, classXY var1) {
      int var2 = var1.method13095(-346779531);
      boolean var3 = (var2 & 1) != 0;
      boolean var4 = 0 != (var2 & 2);
      boolean var5 = (var2 & 4) != 0;
      boolean var6 = 0 != (var2 & 8);
      ColorTextureOverride var7 = new ColorTextureOverride(var0);
      if (var3) {
         int var8 = var1.method13095(-346779531);
         int[] var9 = new int[]{var8 & 15, var8 >> 4 & 15};
         boolean var10 = null != var7.field5259 && var7.field5259.length == var9.length;

         for (int var11 = 0; var11 < 2; var11++) {
            if (15 != var9[var11]) {
               short var12 = (short)var1.method13099(-958221560);
               if (var10) {
                  var7.field5259[var9[var11]] = var12;
               }
            }
         }
      }

      if (var4) {
         int var13 = var1.method13095(-346779531);
         int[] var14 = new int[]{var13 & 15, var13 >> 4 & 15};
         boolean var15 = null != var7.field5258 && var7.field5258.length == var14.length;

         for (int var16 = 0; var16 < 2; var16++) {
            if (15 != var14[var16]) {
               short var17 = (short)var1.method13099(-1039993073);
               if (var15) {
                  var7.field5258[var14[var16]] = var17;
               }
            }
         }
      }

      if (var5) {
         var7.field5255 = var1.method13173(classXM.field6886.field6888, 1919755029) * -1424760829;
         var7.field5256 = var1.method13173(classXM.field6886.field6888, 1450990995) * -286785645;
      }

      if (var6) {
         var7.field5257 = var1.method13173(classXM.field6886.field6888, 1783821026) * 1097885037;
         var7.field5260 = var1.method13173(classXM.field6886.field6888, 1721372049) * -74289375;
      }

      return var7;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aj")
   boolean method8945(int var1) {
      return 0 == var1 && 889924629 * this.field5257 != -1 || var1 == 1 && 10867937 * this.field5260 != -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILxy;)Lpi;")
   @ObfuscatedName("ac")
   public static ColorTextureOverride method8935(int var0, classXY var1) {
      int var2 = var1.method13095(-346779531);
      boolean var3 = (var2 & 1) != 0;
      boolean var4 = 0 != (var2 & 2);
      boolean var5 = (var2 & 4) != 0;
      boolean var6 = 0 != (var2 & 8);
      ColorTextureOverride var7 = new ColorTextureOverride(var0);
      if (var3) {
         int var8 = var1.method13095(-346779531);
         int[] var9 = new int[]{var8 & 15, var8 >> 4 & 15};
         boolean var10 = null != var7.field5259 && var7.field5259.length == var9.length;

         for (int var11 = 0; var11 < 2; var11++) {
            if (15 != var9[var11]) {
               short var12 = (short)var1.method13099(-911564702);
               if (var10) {
                  var7.field5259[var9[var11]] = var12;
               }
            }
         }
      }

      if (var4) {
         int var13 = var1.method13095(-346779531);
         int[] var14 = new int[]{var13 & 15, var13 >> 4 & 15};
         boolean var15 = null != var7.field5258 && var7.field5258.length == var14.length;

         for (int var16 = 0; var16 < 2; var16++) {
            if (15 != var14[var16]) {
               short var17 = (short)var1.method13099(923550907);
               if (var15) {
                  var7.field5258[var14[var16]] = var17;
               }
            }
         }
      }

      if (var5) {
         var7.field5255 = var1.method13173(classXM.field6886.field6888, 1672296045) * -1424760829;
         var7.field5256 = var1.method13173(classXM.field6886.field6888, 1678631184) * -286785645;
      }

      if (var6) {
         var7.field5257 = var1.method13173(classXM.field6886.field6888, 1857278622) * 1097885037;
         var7.field5260 = var1.method13173(classXM.field6886.field6888, 2146828395) * -74289375;
      }

      return var7;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("af")
   public boolean method8937(int var1) {
      try {
         boolean var10000;
         if (this.field5259 != null) {
            if (var1 >= -631212490) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pi.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpi;IB)I")
   @ObfuscatedName("pq")
   public static int method8949(ColorTextureOverride var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method8952(var1, var2);
      }

      try {
         return var1 == 0 ? 956156587 * var0.field5255 : -1158941029 * var0.field5256;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pi.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ap")
   int method8950(int var1) {
      return var1 == 0 ? 956156587 * this.field5255 : -1158941029 * this.field5256;
   }

   public ColorTextureOverride(int var1) {
      ItemComposition var2 = classOB.method8329(var1, (byte)52);
      if (var2.method8736(-1879922488)) {
         this.field5259 = new short[var2.field5039.length];
         System.arraycopy(var2.field5039, 0, this.field5259, 0, this.field5259.length);
      }

      if (var2.method8739(509268873)) {
         this.field5258 = new short[var2.field5015.length];
         System.arraycopy(var2.field5015, 0, this.field5258, 0, this.field5258.length);
      }

      this.field5261 = var2.field5035 * -1321466701;
   }

   public short[] getTextureToReplaceWith() {
      return this.field5258;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   int method8951(int var1) {
      return var1 == 0 ? 956156587 * this.field5255 : -1158941029 * this.field5256;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   int method8955(int var1) {
      return 0 == var1 ? this.field5257 * -292131739 : 10867937 * this.field5260;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ag")
   boolean method8946(int var1, int var2) {
      try {
         if (0 == var1) {
            if (var2 != 938234762) {
               throw new IllegalStateException();
            }

            if (-292131739 * this.field5257 != -1) {
               return true;
            }

            if (var2 != 938234762) {
               throw new IllegalStateException();
            }
         }

         if (var1 != 1 || 10867937 * this.field5256 == -1) {
            return false;
         } else if (var2 != 938234762) {
            throw new IllegalStateException();
         } else {
            return true;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pi.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("as")
   int method8952(int var1, byte var2) {
      try {
         return var1 == 0 ? 956156587 * this.field5255 : -1158941029 * this.field5257;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pi.as(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Laaa;)Z")
   @ObfuscatedName("se")
   public static boolean method8953(classLH var0, AbstractFont var1) {
      boolean var2 = !var0.field4145;
      var0.field4157.method13905(var1, 1792850232);
      var0.field4142.method13905(var1, 463994915);
      var0.field4145 = true;
      var2 |= var0.method6884(var0.field4152 * -1184890025, var0.field4158 * -2131320667, -420130938);
      var2 |= var0.method6930(var0.field4150 * 1072642345, -698145269 * var0.field4155, 1122842785);
      if (var0.method7111((byte)-88)) {
         classLH.method7133(var0, (byte)115);
         var2 = true;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ax")
   int method8956(int var1, int var2) {
      try {
         int var10000;
         if (0 == var1) {
            if (var2 == -1601733392) {
               throw new IllegalStateException();
            }

            var10000 = this.field5255 * -292131739;
         } else {
            var10000 = 10867937 * this.field5255;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pi.ax(" + 41);
      }
   }
}
