import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pk")
public class WidgetNode extends Node implements net.runelite.api.WidgetNode {
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field5273 = 14;
   @ObfuscatedName("az")
   public int field5275;
   @ObfuscatedName("ae")
   public boolean field5272 = false;
   @ObfuscatedName("af")
   public int field5274;
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   static final int field5276 = 29;
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("gw")
   static classAAO field5277;
   @ObfuscatedName("ky")
   static boolean field5278;

   public int getId() {
      return this.field5275 * 2023647411;
   }

   public int getModalMode() {
      return this.field5274 * -691218541;
   }

   WidgetNode() {
   }
}
