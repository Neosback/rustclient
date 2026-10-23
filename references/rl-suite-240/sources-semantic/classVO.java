import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vo")
public class classVO extends classVJ {
   @ObfuscatedSignature(descriptor = "Lvo;")
   @ObfuscatedName("el")
   public classVO field6530;
   @ObfuscatedSignature(descriptor = "Lvo;")
   @ObfuscatedName("eh")
   public classVO field6529;
   @ObfuscatedName("ev")
   public long field6528;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhd;)Z")
   @ObfuscatedName("tm")
   public static boolean method12083(WorldMapIcon var0) {
      if (var0 == null) {
         var0.getCoordinate();
      }

      return var0.vmethod486((byte)7) >= 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("mi")
   public void method12078() {
      if (this.field6530 != null) {
         this.field6530.field6529 = this.field6529;
         this.field6529.field6530 = this.field6530;
         this.field6529 = null;
         this.field6530 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("mv")
   public void method12079() {
      if (this.field6530 != null) {
         this.field6530.field6529 = this.field6529;
         this.field6529.field6530 = this.field6530;
         this.field6529 = null;
         this.field6530 = null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvo;)V")
   @ObfuscatedName("ba")
   public static void method12080(classVO var0) {
      if (var0 == null) {
         var0.method12082();
      } else if (var0.field6530 != null) {
         var0.field6530.field6529 = var0.field6529;
         var0.field6529.field6530 = var0.field6530;
         var0.field6529 = null;
         var0.field6530 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("mh")
   public void method12081() {
      if (this.field6530 != null) {
         this.field6530.field6529 = this.field6529;
         this.field6529.field6530 = this.field6530;
         this.field6529 = null;
         this.field6530 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("md")
   public void method12082() {
      if (this.field6530 != null) {
         this.field6529.field6530 = this.field6530;
         this.field6530.field6530 = this.field6529;
         this.field6529 = null;
         this.field6529 = null;
      }
   }
}
