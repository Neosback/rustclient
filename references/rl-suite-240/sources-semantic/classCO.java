import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("co")
public class classCO extends Node {
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field1178 = 30;
   @ObfuscatedSignature(descriptor = "Lnv;")
   @ObfuscatedName("ab")
   classNV field1174 = new classNV();
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field1176 = 9;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field1177 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   public static final int field1179 = 8;
   @ObfuscatedName("aj")
   static short[][][] field1180;
   @ObfuscatedSignature(descriptor = "Lpx;")
   @ObfuscatedName("ae")
   HealthBarConfig field1175;

   @ObfuscatedSignature(descriptor = "(IIIIB)V")
   @ObfuscatedName("az")
   void method2549(int var1, int var2, int var3, int var4, byte var5) {
      try {
         classCV var6 = null;
         int var7 = 0;

         for (classCV var8 = (classCV)this.field1174.method8208(); var8 != null; var8 = (classCV)this.field1174.method8216()) {
            if (var5 <= 1) {
               throw new IllegalStateException();
            }

            var7++;
            if (1576496181 * var8.field1298 == var1) {
               if (var5 <= 1) {
                  throw new IllegalStateException();
               }

               var8.method2676(var1, var2, var3, var4, -1187795306);
               return;
            }

            if (1576496181 * var8.field1298 <= var1) {
               if (var5 <= 1) {
                  return;
               }

               var6 = var8;
            }
         }

         if (null != var6) {
            classNV.method8189(new classCV(var1, var2, var3, var4), var6);
            if (var7 >= 4) {
               if (var5 <= 1) {
                  throw new IllegalStateException();
               }

               this.field1174.method8208().vmethod398();
            }
         } else if (var5 <= 1) {
            throw new IllegalStateException();
         } else {
            if (var7 < 4) {
               if (var5 <= 1) {
                  return;
               }

               this.field1174.method8200(new classCV(var1, var2, var3, var4));
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "co.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lcv;")
   @ObfuscatedName("af")
   classCV method2552(int var1, int var2) {
      try {
         classCV var3 = (classCV)this.field1174.method8208();
         if (var3 != null) {
            if (var2 >= -750192613) {
               throw new IllegalStateException();
            }

            if (1576496181 * var3.field1298 <= var1) {
               for (classCV var4 = (classCV)this.field1174.method8216(); null != var4; var4 = (classCV)this.field1174.method8216()) {
                  if (var2 >= -750192613) {
                     throw new IllegalStateException();
                  }

                  if (var4.field1298 * 1576496181 > var1) {
                     break;
                  }

                  if (var2 >= -750192613) {
                     throw new IllegalStateException();
                  }

                  var3.vmethod398();
                  var3 = var4;
               }

               if (this.field1175.field5478 * -1486866911 + 1576496181 * var3.field1298 + -425086145 * var3.field1295 > var1) {
                  if (var2 >= -750192613) {
                     throw new IllegalStateException();
                  }

                  return var3;
               }

               var3.vmethod398();
               return null;
            }

            if (var2 >= -750192613) {
               throw new IllegalStateException();
            }
         }

         return null;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "co.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ht")
   static void method2559(byte var0) {
      try {
         if (1 == client.field968 * -496203881) {
            if (var0 != 12) {
               throw new IllegalStateException();
            }

            client.field953 = true;
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "co.ht(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ab")
   void method2550(int var1, int var2, int var3, int var4) {
      classCV var5 = null;
      int var6 = 0;

      for (classCV var7 = (classCV)this.field1174.method8208(); var7 != null; var7 = (classCV)this.field1174.method8216()) {
         var6++;
         if (1576496181 * var7.field1298 == var1) {
            var7.method2676(var1, var2, var3, var4, -2070137717);
            return;
         }

         if (859522264 * var7.field1298 <= var1) {
            var5 = var7;
         }
      }

      if (null == var5) {
         if (var6 < 4) {
            this.field1174.method8200(new classCV(var1, var2, var3, var4));
         }
      } else {
         classNV.method8189(new classCV(var1, var2, var3, var4), var5);
         if (var6 >= 4) {
            this.field1174.method8208().vmethod398();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ag")
   void method2551(int var1, int var2, int var3, int var4) {
      classCV var5 = null;
      int var6 = 0;

      for (classCV var7 = (classCV)this.field1174.method8208(); var7 != null; var7 = (classCV)this.field1174.method8216()) {
         var6++;
         if (1576496181 * var7.field1298 == var1) {
            var7.method2676(var1, var2, var3, var4, -560476653);
            return;
         }

         if (1576496181 * var7.field1298 <= var1) {
            var5 = var7;
         }
      }

      if (null == var5) {
         if (var6 < 4) {
            this.field1174.method8200(new classCV(var1, var2, var3, var4));
         }
      } else {
         classNV.method8189(new classCV(var1, var2, var3, var4), var5);
         if (var6 >= 4) {
            this.field1174.method8208().vmethod398();
         }
      }
   }

   classCO(HealthBarConfig var1) {
      this.field1175 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lpx;")
   @ObfuscatedName("tb")
   public HealthBarConfig method2560() {
      return this.field1175;
   }

   @ObfuscatedSignature(descriptor = "(I)Lcv;")
   @ObfuscatedName("ax")
   classCV method2553(int var1) {
      classCV var2 = (classCV)this.field1174.method8208();
      if (var2 != null && 1576496181 * var2.field1298 <= var1) {
         for (classCV var3 = (classCV)this.field1174.method8216();
            null != var3 && var3.field1298 * 1576496181 <= var1;
            var3 = (classCV)this.field1174.method8216()
         ) {
            var2.vmethod398();
            var2 = var3;
         }

         if (this.field1175.field5478 * -1486866911 + 1576496181 * var2.field1298 + -425086145 * var2.field1295 > var1) {
            return var2;
         } else {
            var2.vmethod398();
            return null;
         }
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ae")
   boolean method2557(byte var1) {
      try {
         return this.field1174.method8221();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "co.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lcv;")
   @ObfuscatedName("qq")
   public classCV method2561(int var1) {
      return this.method2552(var1, -1606368617);
   }

   @ObfuscatedSignature(descriptor = "(I)Lcv;")
   @ObfuscatedName("aa")
   classCV method2554(int var1) {
      classCV var2 = (classCV)this.field1174.method8208();
      if (var2 != null && 1576496181 * var2.field1298 <= var1) {
         for (classCV var3 = (classCV)this.field1174.method8216();
            null != var3 && var3.field1298 * 1576496181 <= var1;
            var3 = (classCV)this.field1174.method8216()
         ) {
            var2.vmethod398();
            var2 = var3;
         }

         if (this.field1175.field5478 * -1486866911 + 1576496181 * var2.field1298 + -425086145 * var2.field1295 > var1) {
            return var2;
         } else {
            var2.vmethod398();
            return null;
         }
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lcv;")
   @ObfuscatedName("as")
   classCV method2555(int var1) {
      classCV var2 = (classCV)this.field1174.method8208();
      if (var2 != null && 1576496181 * var2.field1298 <= var1) {
         for (classCV var3 = (classCV)this.field1174.method8216();
            null != var3 && var3.field1298 * 1576496181 <= var1;
            var3 = (classCV)this.field1174.method8216()
         ) {
            var2.vmethod398();
            var2 = var3;
         }

         if (this.field1175.field5478 * -1486866911 + 1576496181 * var2.field1298 + -425086145 * var2.field1295 > var1) {
            return var2;
         } else {
            var2.vmethod398();
            return null;
         }
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lcv;")
   @ObfuscatedName("ac")
   classCV method2556(int var1) {
      classCV var2 = (classCV)this.field1174.method8208();
      if (var2 != null && 1576496181 * var2.field1298 <= var1) {
         for (classCV var3 = (classCV)this.field1174.method8216();
            null != var3 && var3.field1298 * 1576496181 <= var1;
            var3 = (classCV)this.field1174.method8216()
         ) {
            var2.vmethod398();
            var2 = var3;
         }

         if (this.field1175.field5478 * -1486866911 + 1576496181 * var2.field1298 + -425086145 * var2.field1295 > var1) {
            return var2;
         } else {
            var2.vmethod398();
            return null;
         }
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("az")
   public static int method2548(int var0, int var1, int var2) {
      try {
         return var1 + (-3 - var0 << 8);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "co.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ao")
   boolean method2558() {
      return this.field1174.method8221();
   }
}
