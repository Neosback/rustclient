import net.runelite.api.coords.LocalPoint;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("yj")
public class classYJ implements classYB {
   @ObfuscatedName("ae")
   int field6989;
   @ObfuscatedName("az")
   int field6991;
   @ObfuscatedName("af")
   int field6990;
   @ObfuscatedName("bi")
   static int field6992;

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ae")
   public void method13437(int var1, int var2, int var3) {
      this.method13435(var1, var2, var3, 1365691935);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hq")
   @Override
   public int vmethod369() {
      return -640882365 * this.field6989;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("af")
   public void method13435(int var1, int var2, int var3, int var4) {
      try {
         this.field6989 = classKY.method6656(var2, 440265306) * -1254167701;
         this.field6991 = classKY.method6656(var3, 1292039406) * -1343438351;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "yj.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod258(byte var1) {
      try {
         return this.field6990 * -1210178563;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "yj.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bj")
   @Override
   public int vmethod259() {
      return this.field6990 * -1210178563;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bs")
   public void method13436(int var1, int var2, int var3) {
      this.field6989 = classKY.method6656(var2, 1254692947) * -1254167701;
      this.field6991 = classKY.method6656(var3, -164596595) * -1343438351;
   }

   public net.runelite.api.WorldView getWorldView() {
      return classIS.field3051;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kh")
   @Override
   public int vmethod372() {
      return this.field6991 * 1183452945;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ky")
   @Override
   public int vmethod370() {
      return this.field6991 * 1183452945;
   }

   public LocalPoint getCameraFocus() {
      return new LocalPoint(this.field6989 * -640882365, this.field6991 * 1183452945, 0);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ey")
   @Override
   public int vmethod368(int var1) {
      try {
         return -640882365 * this.field6989;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "yj.ey(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ea")
   @Override
   public int vmethod371(int var1) {
      try {
         return this.field6991 * 1183452945;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "yj.ea(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("im")
   @Override
   public float vmethod382() {
      return this.field6989 * -640882365;
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("si")
   @Override
   public float vmethod383() {
      return this.field6991 * 1183452945;
   }
}
