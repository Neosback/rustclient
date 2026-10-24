import net.runelite.api.Rasterizer;
import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("rl13")
class rl13 implements Rasterizer {
   public void rasterFlat(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      classFQ.method4975(var1, var2, var3, var4, var5, var6, 0.0F, 0.0F, 0.0F, var7);
   }

   public void setDrawRegion(int var1, int var2, int var3, int var4) {
      classYW.method13710(var1, var2, var3, var4);
   }

   public void setRasterGouraudLowRes(boolean var1) {
      classFH.field2255.method4626(var1);
   }

   public int getHeight() {
      return classYW.field7099;
   }

   public void fillRectangle(int var1, int var2, int var3, int var4, int var5) {
      classYW.method13737(var1, var2, var3, var4, var5);
   }

   public void rasterGouraud(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      classFQ.method4985(var1, var2, var3, var4, var5, var6, 0.0F, 0.0F, 0.0F, var7, var8, var9);
   }

   public void resetRasterClipping() {
      classFH.method4760();
   }

   public int[] getPixels() {
      return classYW.field7102;
   }

   public int getWidth() {
      return classYW.field7100;
   }

   public rl13() {
   }
}
