import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fz")
public class classFZ {
   @ObfuscatedSignature(descriptor = "Lfz;")
   @ObfuscatedName("ab")
   static final classFZ field2516 = new classFZ(3);
   @ObfuscatedSignature(descriptor = "Lfz;")
   @ObfuscatedName("af")
   static final classFZ field2514 = new classFZ(1);
   @ObfuscatedSignature(descriptor = "Lfz;")
   @ObfuscatedName("ae")
   public static final classFZ field2515 = new classFZ(2);
   @ObfuscatedName("as")
   final int field2512;
   @ObfuscatedSignature(descriptor = "Lfz;")
   @ObfuscatedName("ag")
   static final classFZ field2517 = new classFZ(4);
   @ObfuscatedSignature(descriptor = "Lfz;")
   @ObfuscatedName("az")
   static final classFZ field2513 = new classFZ(0);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcl;ILtn;Ltw;)V")
   @ObfuscatedName("vt")
   public static void method5307(classCL var0, int var1, classTN var2, classTW var3) {
      var0.field780.put(var1, var2);
      var0.field781.put(var1, var3);
   }

   classFZ(int var1) {
      this.field2512 = var1;
   }
}
