import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("az")
public class classAZ implements classAE {
   @ObfuscatedSignature(descriptor = "(Lxy;)Lxy;")
   @ObfuscatedName("as")
   @Override
   public classXY vmethod22(classXY var1) {
      classXY var2 = new classXY(-250087846);
      method745(this, var1, var2);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Lxy;")
   @ObfuscatedName("az")
   @Override
   public classXY vmethod20(classXY var1) {
      classXY var2 = new classXY(100);
      method745(this, var1, var2);
      return var2;
   }

   classAZ() {
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Lxy;")
   @ObfuscatedName("ag")
   @Override
   public classXY vmethod21(classXY var1) {
      classXY var2 = new classXY(100);
      method745(this, var1, var2);
      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;)Lvc;")
   @ObfuscatedName("ed")
   public static classVC method748(classVV var0) {
      return var0.field6609;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Lxy;")
   @ObfuscatedName("ab")
   @Override
   public classXY vmethod18(classXY var1) {
      classXY var2 = new classXY(100);
      method745(this, var1, var2);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Lxy;")
   @ObfuscatedName("ae")
   @Override
   public classXY vmethod19(classXY var1) {
      classXY var2 = new classXY(1279964818);
      method745(this, var1, var2);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Lxy;)V")
   @ObfuscatedName("ax")
   void method743(classXY var1, classXY var2) {
      classAA var3 = new classAA(var1);
      classAG var4 = new classAG(var3);
      long var5 = 0L;

      while (!var4.method346(var3.method2(), var3.method0(), var5)) {
         var5++;
      }

      classXY.method13047(var2, var5);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Lxy;)V")
   @ObfuscatedName("ac")
   void method744(classXY var1, classXY var2) {
      classAA var3 = new classAA(var1);
      classAG var4 = new classAG(var3);
      long var5 = 0L;

      while (!var4.method346(var3.method2(), var3.method0(), var5)) {
         var5++;
      }

      classXY.method13047(var2, var5);
   }

   @ObfuscatedSignature(descriptor = "(Laz;Lxy;Lxy;)V")
   @ObfuscatedName("rx")
   public static void method745(classAZ var0, classXY var1, classXY var2) {
      if (var0 == null) {
         var0.method747(var1, var1);
      } else {
         classAA var3 = new classAA(var1);
         classAG var4 = new classAG(var3);
         long var5 = 0L;

         while (!var4.method346(var3.method2(), var3.method0(), var5)) {
            var5++;
         }

         classXY.method13047(var2, var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Lxy;)V")
   @ObfuscatedName("aa")
   void method746(classXY var1, classXY var2) {
      classAA var3 = new classAA(var1);
      classAG var4 = new classAG(var3);
      long var5 = 0L;

      while (!var4.method346(var3.method2(), var3.method0(), var5)) {
         var5++;
      }

      classXY.method13047(var2, var5);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Lxy;)V")
   @ObfuscatedName("af")
   void method747(classXY var1, classXY var2) {
      classAA var3 = new classAA(var1);
      classAG var4 = new classAG(var3);
      long var5 = 0L;

      while (!var4.method345(var3.method3(), var3.method1(), var5)) {
         var5++;
      }

      classXY.method13047(var2, var5);
   }
}
