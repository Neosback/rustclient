import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qh")
public class classQH {
   @ObfuscatedName("ab")
   int field5539;
   @ObfuscatedName("af")
   int field5536;
   @ToRemove(unused = "true")
   @ObfuscatedName("by")
   static final int field5541 = 54;
   @ObfuscatedName("ae")
   boolean field5537 = false;
   @ToRemove(unused = "true")
   @ObfuscatedName("bk")
   public static final int field5540 = 4096;
   @ObfuscatedName("az")
   int field5538;
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("cs")
   static IndexedSprite field5542;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqh;I)Z")
   @ObfuscatedName("ft")
   public static boolean method9577(classQH var0, int var1) {
      return var0 == null ? var0.method9581(var1) : -1966181807 * var0.field5538 > -2060505227 * var0.field5539 && -1966181807 * var0.field5538 <= var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aw")
   public void method9590(int var1) {
      this.field5539 = var1 * -537204515;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqh;I)Z")
   @ObfuscatedName("yt")
   public static boolean method9585(classQH var0, int var1) {
      if (var0 == null) {
         var0.method9580(var1, var1);
      }

      return !method9582(var0, var1, (short)-7062) && var1 >= var0.field5536 * -1432447279;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqh;I)Z")
   @ObfuscatedName("ih")
   public static boolean method9578(classQH var0, int var1) {
      return -1966181807 * var0.field5538 > -2060505227 * var0.field5539 && -1966181807 * var0.field5538 <= var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("af")
   public boolean method9573(int var1) {
      try {
         return this.field5537;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qh.af(" + ')');
      }
   }

   public classQH(int var1, int var2, boolean var3) {
      this.field5539 = 0;
      this.field5538 = var1 * 1718621873;
      this.field5536 = var2 * 1071612465;
      this.field5537 = var3;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Lyn;I)Lyn;")
   @ObfuscatedName("az")
   static final IterableHashTable method9572(classXY var0, IterableHashTable var1, int var2) {
      try {
         int var3 = var0.method13095(-346779531);
         if (var1 == null) {
            int var4 = classBZ.method1387(var3, -323192748);
            var1 = new IterableHashTable(var4);
         }

         for (int var9 = 0; var9 < var3; var9++) {
            if (var2 != 1978086108) {
               throw new IllegalStateException();
            }

            int var5 = var0.method13095(-346779531);
            int var6 = var0.method13105(-758448158);
            Object var7;
            if (var5 == 1) {
               if (var2 != 1978086108) {
                  throw new IllegalStateException();
               }

               var7 = new classVE(var0.method13127(-505567903));
            } else if (var5 == 2) {
               var7 = new classVK(classXY.method13115(var0, 1592033524));
            } else {
               var7 = new IntegerNode(classXY.method13113(var0, (byte)1));
            }

            IterableHashTable.method13620(var1, (Node)var7, var6);
         }

         return var1;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "qh.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("as")
   public void method9591(int var1, int var2) {
      try {
         this.field5539 = var1 * -537204515;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qh.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aq")
   public boolean method9579(int var1) {
      return -1966181807 * this.field5538 > -2060505227 * this.field5539 && -1966181807 * this.field5538 <= var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aa")
   public boolean method9574() {
      return this.field5537;
   }

   @ObfuscatedSignature(descriptor = "(Lqh;IS)Z")
   @ObfuscatedName("ep")
   public static boolean method9582(classQH var0, int var1, short var2) {
      if (var0 == null) {
         return var0.method9584(var1, var2);
      } else {
         try {
            boolean var10000;
            if (var1 >= var0.field5538 * -1966181807 && var1 < -1432447279 * var0.field5536) {
               if (var2 == 255) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "qh.ab(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ao")
   public boolean method9575() {
      return this.field5537;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("al")
   public boolean method9576() {
      return this.field5537;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ad")
   public boolean method9583(int var1) {
      return var1 >= this.field5538 * -1966181807 && var1 < -1432447279 * this.field5536;
   }

   @ObfuscatedSignature(descriptor = "(IIZI)V")
   @ObfuscatedName("az")
   public void method9571(int var1, int var2, boolean var3, int var4) {
      try {
         this.field5538 = 1718621873 * var1;
         this.field5536 = 1071612465 * var2;
         this.field5537 = var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "qh.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ap")
   public boolean method9586(int var1) {
      return !method9582(this, var1, (short)-24461) && var1 >= this.field5536 * -1432447279;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ar")
   public boolean method9587(int var1) {
      return !method9582(this, var1, (short)-609) && var1 >= this.field5536 * -1432447279;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ai")
   public boolean method9588(int var1) {
      return !method9582(this, var1, (short)-24992) && var1 >= this.field5536 * -1432447279;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ae")
   public boolean method9589(int var1, int var2) {
      try {
         if (!method9582(this, var1, (short)15858)) {
            if (var2 >= 659647835) {
               throw new IllegalStateException();
            }

            if (var1 >= this.field5536 * -1432447279) {
               if (var2 >= 659647835) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qh.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;)I")
   @ObfuscatedName("fx")
   public static int method9593(RenderOverview var0) {
      return 166969213 * var0.field6692;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("afb")
   protected static final boolean method9592(int var0) {
      try {
         return GameEngine.field6196.method10865(-1914370237);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qh.afb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ag")
   public boolean method9580(int var1, int var2) {
      try {
         if (-1966181807 * this.field5538 > -2060505227 * this.field5539) {
            if (var2 == -708993696) {
               throw new IllegalStateException();
            }

            if (-1966181807 * this.field5538 <= var1) {
               if (var2 == -708993696) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qh.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aj")
   public boolean method9581(int var1) {
      return -1966181807 * this.field5538 > -2060505227 * this.field5538 && -1966181807 * this.field5539 <= var1;
   }

   @ObfuscatedSignature(descriptor = "(IS)Z")
   @ObfuscatedName("ab")
   public boolean method9584(int var1, short var2) {
      try {
         boolean var10000;
         if (var1 >= this.field5539 * -1966181807 && var1 < -1432447279 * this.field5539) {
            if (var2 == 255) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qh.ab(" + ')');
      }
   }
}
