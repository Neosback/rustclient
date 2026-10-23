import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hp")
public class classHP {
   @ToRemove(unused = "true")
   @ObfuscatedName("ba")
   public static final int field2860 = 4194304;
   @ObfuscatedSignature(descriptor = "Lhp;")
   @ObfuscatedName("af")
   public static final classHP field2855 = new classHP(2, 1, 2);
   @ObfuscatedSignature(descriptor = "Lhp;")
   @ObfuscatedName("ae")
   public static final classHP field2856 = new classHP(0, 2, 0);
   @ObfuscatedSignature(descriptor = "Lhp;")
   @ObfuscatedName("az")
   public static final classHP field2854 = new classHP(1, 0, 3);
   @ObfuscatedName("ag")
   final int field2852;
   @ObfuscatedName("as")
   final int field2853;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field2857 = 8;
   @ObfuscatedName("ab")
   final int field2851;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field2858 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("bf")
   static final int field2861 = 122;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field2859 = 30;

   @ObfuscatedSignature(descriptor = "(FI)Z")
   @ObfuscatedName("ae")
   boolean method6121(float var1, int var2) {
      try {
         boolean var10000;
         if (var1 >= -918748885 * this.field2853) {
            if (var2 <= -1264009661) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "hp.ae(" + ')');
      }
   }

   classHP(int var1, int var2, int var3) {
      this.field2851 = 1224731469 * var1;
      this.field2852 = -1147798695 * var2;
      this.field2853 = -547375741 * var3;
   }

   @ObfuscatedSignature(descriptor = "(F)Z")
   @ObfuscatedName("ao")
   boolean method6122(float var1) {
      return var1 >= -918748885 * this.field2853;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lhp;")
   @ObfuscatedName("as")
   static classHP[] method6115() {
      return new classHP[]{field2856, field2854, field2855};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lhp;")
   @ObfuscatedName("ab")
   static classHP[] method6116() {
      return new classHP[]{field2856, field2854, field2855};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lhp;")
   @ObfuscatedName("ag")
   static classHP[] method6117() {
      return new classHP[]{field2856, field2854, field2855};
   }

   @ObfuscatedSignature(descriptor = "(B)[Lhp;")
   @ObfuscatedName("az")
   static classHP[] method6118(byte var0) {
      try {
         return new classHP[]{field2856, field2854, field2855};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "hp.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Lhp;")
   @ObfuscatedName("af")
   static classHP method6119(int var0, byte var1) {
      try {
         classHP[] var2 = method6118((byte)11);

         for (int var3 = 0; var3 < var2.length; var3++) {
            if (var1 <= 2) {
               throw new IllegalStateException();
            }

            classHP var4 = var2[var3];
            if (var0 == 1937022185 * var4.field2852) {
               return var4;
            }
         }

         return null;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "hp.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(F)Z")
   @ObfuscatedName("aa")
   boolean method6123(float var1) {
      return var1 >= -918748885 * this.field2853;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lhp;")
   @ObfuscatedName("ax")
   static classHP method6120(int var0) {
      classHP[] var1 = method6118((byte)-79);

      for (int var2 = 0; var2 < var1.length; var2++) {
         classHP var3 = var1[var2];
         if (var0 == 1937022185 * var3.field2852) {
            return var3;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(F)Z")
   @ObfuscatedName("ac")
   boolean method6124(float var1) {
      return var1 >= -918748885 * this.field2853;
   }

   @ObfuscatedSignature(descriptor = "([Lym;I)V")
   @ObfuscatedName("az")
   static void method6125(SpritePixels[] var0, int var1) {
      try {
         classCE.field713 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hp.az(" + ')');
      }
   }
}
