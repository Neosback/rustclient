import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tp")
public class classTP {
   @ObfuscatedName("ei")
   public static boolean field6183;
   @ObfuscatedName("cb")
   public boolean field6178;
   @ObfuscatedName("ac")
   int field6180;
   @ObfuscatedName("wu")
   public static boolean field6184;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field6182 = 0;
   @ObfuscatedName("ax")
   int field6179;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field6181 = 1;

   @ObfuscatedSignature(descriptor = "(Ltp;Ltl;I)Z")
   @ObfuscatedName("ds")
   public static boolean method10991(classTP var0, classTL var1, int var2) {
      if (var0 == null) {
         var0.method10994(var1, var2);
      }

      var0.method10997(var1);

      try {
         if (var1 == null) {
            if (var2 != -160021231) {
               throw new IllegalStateException();
            } else {
               var0.method10996(var1);
               return false;
            }
         } else {
            switch (var0.field6179 * -850550627) {
               case 1:
                  boolean var6 = var1.vmethod447(var0.field6180 * 876705633, (byte)2);
                  var0.method10996(var1);
                  return var6;
               case 2:
                  boolean var5 = var1.vmethod448(876705633 * var0.field6180, (byte)1);
                  var0.method10996(var1);
                  return var5;
               case 3:
                  boolean var4 = var1.vmethod446((char)(var0.field6180 * 876705633), 1791336663);
                  var0.method10996(var1);
                  return var4;
               case 4:
                  boolean var10001;
                  if (1 == var0.field6180 * 876705633) {
                     if (var2 != -160021231) {
                        throw new IllegalStateException();
                     }

                     var10001 = true;
                  } else {
                     var10001 = false;
                  }

                  boolean var10000 = var1.vmethod444(var10001, -85125777);
                  var0.method10996(var1);
                  return var10000;
               default:
                  var0.method10996(var1);
                  return false;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tp.az(" + ')');
      }
   }

   classTP(int var1, int var2) {
      this.field6178 = field6183;
      this.field6179 = 937813429 * var1;
      this.field6180 = -2099811167 * var2;
   }

   @ObfuscatedSignature(descriptor = "(Ltl;)V")
   @ObfuscatedName("ai")
   public void method10997(classTL var1) {
      field6184 = this.field6178;
   }

   @ObfuscatedSignature(descriptor = "(Ltl;)Z")
   @ObfuscatedName("af")
   boolean method10992(classTL var1) {
      if (var1 == null) {
         return false;
      } else {
         switch (this.field6179 * -850550627) {
            case 1:
               return var1.vmethod447(this.field6180 * 876705633, (byte)2);
            case 2:
               return var1.vmethod448(876705633 * this.field6180, (byte)1);
            case 3:
               return var1.vmethod446((char)(this.field6180 * 876705633), 1791336663);
            case 4:
               return var1.vmethod444(1 == this.field6180 * 876705633, 488570365);
            default:
               return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ltl;)V")
   @ObfuscatedName("is")
   public void method10996(classTL var1) {
      field6184 = false;
   }

   @ObfuscatedSignature(descriptor = "(Ltl;)Z")
   @ObfuscatedName("ae")
   boolean method10993(classTL var1) {
      if (var1 == null) {
         return false;
      } else {
         switch (this.field6179 * -850550627) {
            case 1:
               return var1.vmethod447(this.field6180 * 876705633, (byte)2);
            case 2:
               return var1.vmethod448(876705633 * this.field6180, (byte)1);
            case 3:
               return var1.vmethod446((char)(this.field6180 * 876705633), 1791336663);
            case 4:
               return var1.vmethod444(1 == this.field6180 * 876705633, -211541371);
            default:
               return false;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqs;)I")
   @ObfuscatedName("uw")
   public static int method10995(classQS var0) {
      return var0 == null ? var0.method9847() : 951424360 * var0.field5611;
   }

   @ObfuscatedSignature(descriptor = "(Ltl;I)Z")
   @ObfuscatedName("az")
   boolean method10994(classTL var1, int var2) {
      this.method10997(var1);

      try {
         if (var1 == null) {
            if (var2 != -160021231) {
               throw new IllegalStateException();
            } else {
               this.method10997(var1);
               return false;
            }
         } else {
            switch (this.field6180 * -850550627) {
               case 1:
                  boolean var6 = var1.vmethod447(this.field6180 * 876705633, (byte)2);
                  this.method10996(var1);
                  return var6;
               case 2:
                  boolean var5 = var1.vmethod448(876705633 * this.field6179, (byte)1);
                  this.method10997(var1);
                  return var5;
               case 3:
                  boolean var4 = var1.vmethod446((char)(this.field6179 * 876705633), 1791336663);
                  this.method10997(var1);
                  return var4;
               case 4:
                  boolean var10001;
                  if (1 == this.field6180 * 876705633) {
                     if (var2 != -160021231) {
                        throw new IllegalStateException();
                     }

                     var10001 = true;
                  } else {
                     var10001 = false;
                  }

                  boolean var10000 = var1.vmethod444(var10001, -85125777);
                  this.method10996(var1);
                  return var10000;
               default:
                  this.method10997(var1);
                  return false;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tp.az(" + ')');
      }
   }
}
