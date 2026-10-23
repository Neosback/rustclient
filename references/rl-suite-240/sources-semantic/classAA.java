import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("aa")
public class classAA {
   @ObfuscatedName("az")
   final int field2;
   @ObfuscatedName("af")
   final int field1;
   @ObfuscatedName("ae")
   final String field0;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   int method2() {
      return this.field1;
   }

   classAA(classXY var1) {
      this(var1.method13095(-346779531), var1.method13095(-346779531), var1.method13127(-1268643217));
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("az")
   String method0() {
      return Integer.toHexString(this.field2) + Integer.toHexString(this.field1) + this.field0;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ae")
   String method1() {
      return Integer.toHexString(this.field2) + Integer.toHexString(this.field1) + this.field0;
   }

   classAA(int var1, int var2, String var3) {
      this.field2 = var1;
      this.field1 = var2;
      this.field0 = var3;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   int method3() {
      return this.field1;
   }
}
