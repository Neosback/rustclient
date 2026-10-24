import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ed")
public class classED extends classRD {
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field1739 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field1737 = -306674912;
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field1738 = 32;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field1740 = 4096;
   @ObfuscatedName("az")
   final boolean field1736;

   @ObfuscatedSignature(descriptor = "(Lul;Lul;I)Lul;")
   @ObfuscatedName("as")
   static final classUL method3849(classUL var0, classUL var1, int var2) {
      try {
         classUL var3 = classSQ.method10683(var0, -506347951);
         var3.method11454(var1, 1591236864);
         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ed.as(" + ')');
      }
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return method3844(this, (ChatPlayer)var1, (ChatPlayer)var2, 2128884400);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ed.compare(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method3847(Object var1, Object var2) {
      return method3844(this, (ChatPlayer)var1, (ChatPlayer)var2, 2091240104);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method3848(Object var1, Object var2) {
      return method3844(this, (ChatPlayer)var1, (ChatPlayer)var2, 2103792287);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;I)V")
   @ObfuscatedName("az")
   public static void method3846(String var0, String var1, int var2) {
      try {
         classGQ.field2650.put(var0.toLowerCase(), var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ed.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("ae")
   int method3842(ChatPlayer var1, ChatPlayer var2) {
      if (0 != var1.field5667 * -760305529) {
         if (0 == var2.field5667 * -760305529) {
            return this.field1736 ? -1 : 1;
         }
      } else if (var2.field5667 * -760305529 != 0) {
         return this.field1736 ? 1 : -1;
      }

      return this.method9983(var1, var2, 1327671337);
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("af")
   int method3843(ChatPlayer var1, ChatPlayer var2) {
      if (0 != var1.field5667 * -760305529) {
         if (0 == var2.field5667 * -760305529) {
            return this.field1736 ? -1 : 1;
         }
      } else if (var2.field5667 * -760305529 != 0) {
         return this.field1736 ? 1 : -1;
      }

      return this.method9983(var1, var2, 1310912392);
   }

   @ObfuscatedSignature(descriptor = "(Led;Lri;Lri;I)I")
   @ObfuscatedName("cp")
   public static int method3844(classED var0, ChatPlayer var1, ChatPlayer var2, int var3) {
      if (var0 == null) {
         var0.method3845(var1, var1, var3);
      }

      try {
         if (0 != var1.field5667 * -760305529) {
            if (var3 <= 1948874899) {
               throw new IllegalStateException();
            }

            if (0 == var2.field5667 * -760305529) {
               if (var3 <= 1948874899) {
                  throw new IllegalStateException();
               }

               byte var10000;
               if (var0.field1736) {
                  if (var3 <= 1948874899) {
                     throw new IllegalStateException();
                  }

                  var10000 = -1;
               } else {
                  var10000 = 1;
               }

               return var10000;
            }
         } else if (var2.field5667 * -760305529 != 0) {
            if (var3 <= 1948874899) {
               throw new IllegalStateException();
            }

            byte var5;
            if (var0.field1736) {
               if (var3 <= 1948874899) {
                  throw new IllegalStateException();
               }

               var5 = 1;
            } else {
               var5 = -1;
            }

            return var5;
         }

         return var0.method9983(var1, var2, 1714535372);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ed.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)F")
   @ObfuscatedName("af")
   static float method3841(int var0, int var1) {
      try {
         return classGB.field2536[var0];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ed.af(" + ')');
      }
   }

   public classED(boolean var1) {
      this.field1736 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;I)I")
   @ObfuscatedName("az")
   int method3845(ChatPlayer var1, ChatPlayer var2, int var3) {
      try {
         if (0 != var1.field5668 * -760305529) {
            if (var3 <= 1948874899) {
               throw new IllegalStateException();
            }

            if (0 == var2.field5669 * -760305529) {
               if (var3 <= 1948874899) {
                  throw new IllegalStateException();
               }

               byte var10000;
               if (this.field1736) {
                  if (var3 <= 1948874899) {
                     throw new IllegalStateException();
                  }

                  var10000 = -1;
               } else {
                  var10000 = 1;
               }

               return var10000;
            }
         } else if (var2.field5668 * -760305529 != 0) {
            if (var3 <= 1948874899) {
               throw new IllegalStateException();
            }

            byte var5;
            if (this.field1736) {
               if (var3 <= 1948874899) {
                  throw new IllegalStateException();
               }

               var5 = 1;
            } else {
               var5 = -1;
            }

            return var5;
         }

         return this.method9983(var1, var2, 1714535372);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ed.az(" + 41);
      }
   }
}
