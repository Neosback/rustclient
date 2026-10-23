import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("xm")
public class classXM {
   @ObfuscatedSignature(descriptor = "Lxf;")
   @ObfuscatedName("ae")
   public final classXF field6888;
   @ObfuscatedSignature(descriptor = "Lxm;")
   @ObfuscatedName("af")
   public static final classXM field6887 = new classXM(new classXP());
   @ObfuscatedSignature(descriptor = "Lxm;")
   @ObfuscatedName("az")
   public static final classXM field6886 = new classXM(new classXX());

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   public int method12906(byte var1) {
      try {
         return this.field6888.vmethod648((byte)32).field6929 * 198139297;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xm.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   public int method12907() {
      return this.field6888.vmethod648((byte)32).field6929 * -1736259265;
   }

   classXM(classXF var1) {
      this.field6888 = var1;
   }
}
