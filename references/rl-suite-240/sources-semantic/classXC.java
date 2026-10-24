import java.util.HashMap;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("xc")
public class classXC {
   @ObfuscatedName("ae")
   HashMap field6821;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("af")
   IndexDataBase field6822;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("az")
   IndexDataBase field6820;
   @ObfuscatedSignature(descriptor = "Lav;")
   @ObfuscatedName("ad")
   public static classAV field6823;

   @ObfuscatedSignature(descriptor = "([Lxj;)Ljava/util/HashMap;")
   @ObfuscatedName("ae")
   public HashMap method12810(classXJ[] var1) {
      HashMap var2 = new HashMap();
      classXJ[] var3 = var1;

      for (int var4 = 0; var4 < var3.length; var4++) {
         classXJ var5 = var3[var4];
         if (this.field6821.containsKey(var5)) {
            var2.put(var5, this.field6821.get(var5));
         } else {
            classZV var6 = classCU.method2671(this.field6820, this.field6822, var5.field6878, "", -2045331213);
            if (null != var6) {
               this.field6821.put(var5, var6);
               var2.put(var5, var6);
            }
         }
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "([Lxj;I)Ljava/util/HashMap;")
   @ObfuscatedName("az")
   public HashMap method12811(classXJ[] var1, int var2) {
      try {
         HashMap var3 = new HashMap();
         classXJ[] var4 = var1;

         for (int var5 = 0; var5 < var4.length; var5++) {
            if (var2 != -1607507693) {
               throw new IllegalStateException();
            }

            classXJ var6 = var4[var5];
            if (this.field6821.containsKey(var6)) {
               var3.put(var6, this.field6821.get(var6));
            } else {
               classZV var7 = classCU.method2671(this.field6820, this.field6822, var6.field6878, "", -1878459293);
               if (null != var7) {
                  if (var2 != -1607507693) {
                     throw new IllegalStateException();
                  }

                  this.field6821.put(var6, var7);
                  var3.put(var6, var7);
               }
            }
         }

         return var3;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "xc.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Lxj;)Ljava/util/HashMap;")
   @ObfuscatedName("af")
   public HashMap method12812(classXJ[] var1) {
      HashMap var2 = new HashMap();
      classXJ[] var3 = var1;

      for (int var4 = 0; var4 < var3.length; var4++) {
         classXJ var5 = var3[var4];
         if (this.field6821.containsKey(var5)) {
            var2.put(var5, this.field6821.get(var5));
         } else {
            classZV var6 = classCU.method2671(this.field6820, this.field6822, var5.field6878, "", 421295953);
            if (null != var6) {
               this.field6821.put(var5, var6);
               var2.put(var5, var6);
            }
         }
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lsn;ILsg;III)V")
   @ObfuscatedName("xv")
   public static void method12814(classSN var0, int var1, classSG var2, int var3, int var4, int var5) {
      if (var0 == null) {
         var0.method10583(var1, var2, var1, var1, var1);
      }

      var0.method10589(rl21.field5726, var1, var2, var3, var4);
   }

   public classXC(IndexDataBase var1, IndexDataBase var2) {
      this.field6820 = var1;
      this.field6822 = var2;
      this.field6821 = new HashMap();
   }

   @ObfuscatedSignature(descriptor = "([Lxj;)Ljava/util/HashMap;")
   @ObfuscatedName("ab")
   public HashMap method12813(classXJ[] var1) {
      HashMap var2 = new HashMap();
      classXJ[] var3 = var1;

      for (int var4 = 0; var4 < var3.length; var4++) {
         classXJ var5 = var3[var4];
         if (this.field6821.containsKey(var5)) {
            var2.put(var5, this.field6821.get(var5));
         } else {
            classZV var6 = classCU.method2671(this.field6820, this.field6822, var5.field6878, "", 1731852835);
            if (null != var6) {
               this.field6821.put(var5, var6);
               var2.put(var5, var6);
            }
         }
      }

      return var2;
   }
}
