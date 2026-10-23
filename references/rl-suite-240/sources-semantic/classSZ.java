import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sz")
public class classSZ {
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   public static final int field6106 = 10;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6104 = 1048576000;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   public static final int field6105 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ct")
   public static final int field6107 = 1010;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   public static final int field6103 = 17;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("at")
   static Widget field6108;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;Lsp;Z)Z")
   @ObfuscatedName("ae")
   public static boolean method10766(WorldEntity var0, WorldEntity var1, boolean var2) {
      if (var0 == var1) {
         return true;
      } else if (var1 == null) {
         return true;
      } else {
         return null == var0 ? false : var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIILfp;I)I")
   @ObfuscatedName("aw")
   static int method10772(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, AABB var9, int var10) {
      try {
         int var11 = var0 + AABB.method4917(var9, -898703852);
         int var12 = var0 + var9.method4919(-1189386226);
         int var13 = var1 + var9.method4923(535313713);
         int var14 = var1 + var9.method4927((byte)4);
         int var15 = var2 + var9.method4929(1833874923);
         int var16 = var2 + AABB.method4932(var9, (byte)57);
         float var17 = var6 - var3;
         float var18 = var7 - var4;
         float var19 = var8 - var5;
         float var20 = 0.0F;
         float var21 = 1.0F;
         float var22 = 1.0E-9F;
         if (Math.abs(var17) < 1.0E-9F) {
            if (var10 == 347407279) {
               throw new IllegalStateException();
            }

            if (var3 < var11) {
               return -1;
            }

            if (var10 == 347407279) {
               throw new IllegalStateException();
            }

            if (var3 > var12) {
               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }

               return -1;
            }
         } else {
            float var23 = 1.0F / var17;
            float var24 = (var11 - var3) * var23;
            float var25 = var23 * (var12 - var3);
            if (var24 > var25) {
               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }

               float var26 = var24;
               var24 = var25;
               var25 = var26;
            }

            var20 = Math.max(var20, var24);
            var21 = Math.min(var21, var25);
            if (var20 > var21) {
               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }

               return -1;
            }
         }

         if (Math.abs(var18) < 1.0E-9F) {
            if (var10 == 347407279) {
               throw new IllegalStateException();
            }

            if (var4 < var13) {
               return -1;
            }

            if (var10 == 347407279) {
               throw new IllegalStateException();
            }

            if (var4 > var14) {
               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }

               return -1;
            }
         } else {
            float var29 = 1.0F / var18;
            float var32 = var29 * (var13 - var4);
            float var35 = var29 * (var14 - var4);
            if (var32 > var35) {
               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }

               float var38 = var32;
               var32 = var35;
               var35 = var38;
            }

            var20 = Math.max(var20, var32);
            var21 = Math.min(var21, var35);
            if (var20 > var21) {
               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }

               return -1;
            }
         }

         label123:
         if (Math.abs(var19) < 1.0E-9F) {
            if (var10 == 347407279) {
               throw new IllegalStateException();
            }

            if (var5 >= var15) {
               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }

               if (var5 <= var16) {
                  break label123;
               }

               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }
            }

            return -1;
         } else {
            float var30 = 1.0F / var19;
            float var33 = var30 * (var15 - var5);
            float var36 = var30 * (var16 - var5);
            if (var33 > var36) {
               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }

               float var39 = var33;
               var33 = var36;
               var36 = var39;
            }

            var20 = Math.max(var20, var33);
            var21 = Math.min(var21, var36);
            if (var20 > var21) {
               if (var10 == 347407279) {
                  throw new IllegalStateException();
               }

               return -1;
            }
         }

         float var31 = var3 + var20 * var17;
         float var34 = var20 * var18 + var4;
         float var37 = var5 + var19 * var20;
         return (int)Math.sqrt(var37 * var37 + (var31 * var31 + var34 * var34));
      } catch (RuntimeException var27) {
         throw classEG.method3899(var27, "sz.aw(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;Lsp;Z)Z")
   @ObfuscatedName("af")
   public static boolean method10767(WorldEntity var0, WorldEntity var1, boolean var2) {
      if (var0 == var1) {
         return true;
      } else if (var1 == null) {
         return true;
      } else {
         return null == var0 ? false : var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;Lsp;Z)Z")
   @ObfuscatedName("ab")
   public static boolean method10768(WorldEntity var0, WorldEntity var1, boolean var2) {
      if (var0 == var1) {
         return true;
      } else if (var1 == null) {
         return true;
      } else {
         return null == var0 ? false : var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;Lsp;Z)Z")
   @ObfuscatedName("ag")
   public static boolean method10769(WorldEntity var0, WorldEntity var1, boolean var2) {
      if (var0 == var1) {
         return true;
      } else if (var1 == null) {
         return true;
      } else {
         return null == var0 ? false : var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;Lsp;Z)Z")
   @ObfuscatedName("as")
   public static boolean method10770(WorldEntity var0, WorldEntity var1, boolean var2) {
      if (var0 == var1) {
         return true;
      } else if (var1 == null) {
         return true;
      } else {
         return null == var0 ? false : var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzr;I)V")
   @ObfuscatedName("az")
   public static void method10771(ScriptEvent var0, int var1) {
      try {
         ItemLayer.method3976(var0, 500000, 475000, (byte)-55);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sz.az(" + ')');
      }
   }

   classSZ() throws Throwable {
      throw new Error();
   }
}
