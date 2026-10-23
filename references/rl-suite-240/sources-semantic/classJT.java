import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jt")
public interface classJT {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;Z)V")
   @ObfuscatedName("su")
   static void method6449(classQC var0, boolean var1) {
      if (var0 == null) {
         var0.method9495();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxj;)Lzv;")
   @ObfuscatedName("ag")
   classZV vmethod106(classXJ var1);

   @ObfuscatedSignature(descriptor = "(I)[Lyz;")
   @ObfuscatedName("af")
   IndexedSprite[] vmethod108(int var1);

   @ObfuscatedSignature(descriptor = "()[Lyz;")
   @ObfuscatedName("ao")
   IndexedSprite[] vmethod109();

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("ae")
   SpritePixels vmethod111(int var1);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)Llw;")
   @ObfuscatedName("hb")
   static Widget method6448(classVV var0, Widget var1) {
      if (var0 == null) {
         var0.method12193(var1);
      }

      Widget var2 = var0.method12219(var1, (byte)-42);
      if (var2 == null) {
         var2 = var1.field4309;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lxj;)Lzv;")
   @ObfuscatedName("ab")
   classZV vmethod107(classXJ var1);

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("as")
   SpritePixels vmethod112();

   @ObfuscatedSignature(descriptor = "(Lxj;I)Lzv;")
   @ObfuscatedName("az")
   classZV vmethod105(classXJ var1, int var2);

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("ac")
   SpritePixels vmethod113();

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("ax")
   SpritePixels vmethod110();

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("aa")
   SpritePixels vmethod114();
}
