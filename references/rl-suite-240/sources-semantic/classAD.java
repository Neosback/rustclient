import java.util.concurrent.locks.ReentrantLock;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ad")
public class classAD {
   @ObfuscatedName("iq")
   static int field141;
   @ObfuscatedSignature(descriptor = "Laj;")
   @ObfuscatedName("af")
   classAJ field138;
   @ObfuscatedName("ae")
   ReentrantLock field139;
   @ObfuscatedSignature(descriptor = "Lbc;")
   @ObfuscatedName("az")
   classBC field137;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field140 = 30;

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIII)V")
   @ObfuscatedName("de")
   static void method325(WorldView var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         Deque2 var7 = var0.field1699[var1][var2][var3];
         if (null != var7) {
            if (var6 != -728364943) {
               throw new IllegalStateException();
            }

            for (TileItem var8 = (TileItem)var7.method7957(); null != var8; var8 = (TileItem)var7.method7965()) {
               if (var6 != -728364943) {
                  return;
               }

               if (var4 == -1370204663 * var8.field1549) {
                  if (var6 != -728364943) {
                     throw new IllegalStateException();
                  }

                  if (var8.field1548 * 1413659045 == var5) {
                     var8.method3415(498599786);
                     break;
                  }
               }
            }

            classUA.method11300(var1, var2, var3, 1802096548);
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ad.de(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lah;B)V")
   @ObfuscatedName("af")
   static final void method324(classAH var0, byte var1) {
      try {
         var0.field150 = false;
         if (var0.field153 != null) {
            if (var1 == 8) {
               return;
            }

            var0.field153.field295 = 0;
         }

         for (classAH var2 = var0.vmethod29(); var2 != null; var2 = var0.vmethod35()) {
            if (var1 == 8) {
               return;
            }

            method324(var2, (byte)65);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ad.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lac;)V")
   @ObfuscatedName("oa")
   public static void method322(classAC var0) {
      if (var0 == null) {
         var0.method310();
      }

      var0.field127.shutdown();
      var0.field127 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   static final void method323(int var0) {
      try {
         String var1 = classKK.field3887;
         classPH.method8924(30, "", var1, 1604010567);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ad.af(" + ')');
      }
   }

   classAD(classBC var1, classAJ var2) {
      this.field137 = var1;
      this.field138 = var2;
      this.field139 = new ReentrantLock();
   }
}
