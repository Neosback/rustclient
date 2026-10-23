import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gg")
public abstract class classGG {
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final int field2572 = 8;
   @ObfuscatedName("af")
   public int field2571;
   @ObfuscatedName("ab")
   public int field2569;
   @ObfuscatedName("az")
   public int field2573;
   @ObfuscatedName("ax")
   public static int[] field2576;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("ih")
   static classVP field2574;
   @ObfuscatedName("ae")
   public int field2570;
   @ObfuscatedSignature(descriptor = "Lym;")
   @ObfuscatedName("mf")
   static SpritePixels field2575;

   @ObfuscatedSignature(descriptor = "(IIILgc;)Z")
   @ObfuscatedName("ae")
   protected abstract boolean vmethod146(int var1, int var2, int var3, CollisionData var4);

   @ObfuscatedSignature(descriptor = "(IIILgc;B)Z")
   @ObfuscatedName("az")
   protected abstract boolean vmethod144(int var1, int var2, int var3, CollisionData var4, byte var5);

   @ObfuscatedSignature(descriptor = "(IIILgc;)Z")
   @ObfuscatedName("af")
   protected abstract boolean vmethod145(int var1, int var2, int var3, CollisionData var4);

   protected classGG() {
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ae")
   public static void method5455(int var0) {
      try {
         classPA.field5201.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "gg.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lup;)F")
   @ObfuscatedName("dc")
   public static float method5456(classUP var0) {
      return classED.method3841(var0.field6360 * 1379695375, 1196830319);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ad")
   static void method5457(int var0) {
      try {
         int var1 = classAB.field122.method2831(1873816533);
         int var2 = client.field978 * -1239932395 + var1;
         long var5 = System.currentTimeMillis();
         int var4 = (int)(var5 / 86400000L) - 11745;
         if (var2 <= var4) {
            if (var0 <= -324749371) {
               return;
            }

            classAB.field122.method2833(var4, (byte)66);
            classAC.method321(38, 2132665134);
         } else {
            classBJ.method1121(true, -347989319);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gg.ad(" + ')');
      }
   }
}
