import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("iz")
public class classIZ extends classHB {
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field3101 = 13;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field3099 = 64;
   @ObfuscatedName("az")
   int field3098;
   @ObfuscatedName("af")
   boolean field3103;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field3100 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("cp")
   static final int field3102 = 16;

   classIZ(classIF var1) {
      this.this$0 = var1;
      this.field3098 = -1376629843;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         this.field3098 = var1.method13099(-1009266385) * 1376629843;
         this.field3103 = var1.method13095(-346779531) == 1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iz.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         ClanSettings.method6316(var1, 800501723 * this.field3098, this.field3103, 2072579966);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iz.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      this.field3098 = var1.method13099(-1334039576) * 1376629843;
      this.field3103 = var1.method13095(-346779531) == 1;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      ClanSettings.method6316(var1, -2086270710 * this.field3098, this.field3103, 1901524512);
   }

   @ObfuscatedSignature(descriptor = "(Luz;Luz;I)Luz;")
   @ObfuscatedName("as")
   public static final classUZ method6384(classUZ var0, classUZ var1, int var2) {
      try {
         classUZ var3 = WorldMapData.method5868(var0, (byte)-35);
         var3.method11839(var1, -1351554887);
         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "iz.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      ClanSettings.method6316(var1, 800501723 * this.field3098, this.field3103, 1923317305);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      ClanSettings.method6316(var1, 800501723 * this.field3098, this.field3103, 2070297393);
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("az")
   public static int method6383(int var0, byte var1) {
      try {
         return var0 >> 20 & 2047;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "iz.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      this.field3098 = var1.method13099(-236751831) * 1376629843;
      this.field3103 = var1.method13095(-346779531) == 1;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)I")
   @ObfuscatedName("az")
   public static int method6382(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         int var7 = 0;
         if (var0 < var2) {
            if (var6 >= -852633712) {
               throw new IllegalStateException();
            }

            var7 += var2 - var0;
         } else if (var0 > var4) {
            if (var6 >= -852633712) {
               throw new IllegalStateException();
            }

            var7 += var0 - var4;
         }

         if (var1 < var3) {
            if (var6 >= -852633712) {
               throw new IllegalStateException();
            }

            var7 += var3 - var1;
         } else if (var1 > var5) {
            if (var6 >= -852633712) {
               throw new IllegalStateException();
            }

            var7 += var1 - var5;
         }

         return var7;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "iz.az(" + 41);
      }
   }
}
