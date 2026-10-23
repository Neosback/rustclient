import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tr")
public class classTR {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6225 = 0;

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("co")
   static int method11179(int var0, Script var1, boolean var2, int var3) {
      try {
         if (var0 == 13337) {
            if (var3 <= 1490009034) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1023779009 * classBB.field327;
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "tr.co(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lky;)I")
   @ObfuscatedName("ie")
   public static int method11178(classKY var0) {
      if (var0 == null) {
         var0.toString();
      }

      return classKY.method6663(var0, -1889487620);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("af")
   public static void method11175(String var0) {
      if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
         try {
            Desktop.getDesktop().browse(new URI(var0));
         } catch (Exception var2) {
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ae")
   public static void method11176(String var0) {
      if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
         try {
            Desktop.getDesktop().browse(new URI(var0));
         } catch (Exception var2) {
         }
      }
   }

   classTR() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ab")
   public static void method11177(String var0) {
      if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
         try {
            Desktop.getDesktop().browse(new URI(var0));
         } catch (Exception var2) {
         }
      }
   }
}
