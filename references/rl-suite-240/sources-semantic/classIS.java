import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("is")
public abstract class classIS extends Node {
   @ObfuscatedSignature(descriptor = "Ldz;")
   @ObfuscatedName("dk")
   public static WorldView field3051;
   @ToRemove(unused = "true")
   @ObfuscatedName("dc")
   public static final int field3050 = 1;
   @ObfuscatedSignature(descriptor = "Ley;")
   @ObfuscatedName("ao")
   static classEY field3052;

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   abstract void vmethod505(classXY var1);

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   abstract void vmethod507(classXY var1, int var2);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;II)V")
   @ObfuscatedName("ov")
   public static void method6355(PlayerComposition var0, int var1, int var2) {
      boolean var3 = var0.field4092 * 1705409403 != var2;
      var0.field4092 = var2 * -2056377421;
      if (var3) {
         if (var0.field4092 * 1705409403 == var1) {
            for (int var4 = 0; var4 < 7; var4++) {
               int var5 = classON.method8644(var4, (byte)119);
               if (var0.field4093[var5] > 0 && var0.field4093[var5] < 2048) {
                  var0.field4093[var5] = var0.field4090[var5];
               }
            }
         } else {
            if (var0.field4093[0] < 2048 || var0.method6764(-931217120)) {
               var0.field4093[2111598757 * classOV.field5164.field5152] = 1;
            }

            for (int var6 = 0; var6 < 7; var6++) {
               int var7 = classON.method8644(var6, (byte)126);
               if (var0.field4093[var7] > 0 && var0.field4093[var7] < 2048) {
                  classGB.method5333(var2, var0.field4093, var6, (byte)8);
               }
            }
         }
      }

      var0.method6754((byte)-44);
   }

   classIS() {
   }

   @ObfuscatedSignature(descriptor = "(Lie;I)V")
   @ObfuscatedName("af")
   abstract void vmethod509(ClanChannel var1, int var2);

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ag")
   abstract void vmethod508(ClanChannel var1);

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("as")
   abstract void vmethod510(ClanChannel var1);

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   abstract void vmethod506(classXY var1);

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ax")
   abstract void vmethod511(ClanChannel var1);
}
