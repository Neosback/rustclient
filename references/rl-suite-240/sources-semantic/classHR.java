import java.awt.event.WindowEvent;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hr")
public class classHR implements classXZ {
   @ObfuscatedSignature(descriptor = "Lhr;")
   @ObfuscatedName("ab")
   static final classHR field2869 = new classHR(3, (byte)3);
   @ToRemove(unused = "true")
   @ObfuscatedName("bv")
   static final int field2875 = 16;
   @ObfuscatedSignature(descriptor = "Lhr;")
   @ObfuscatedName("ae")
   static final classHR field2870 = new classHR(0, (byte)2);
   @ObfuscatedSignature(descriptor = "Lhr;")
   @ObfuscatedName("af")
   static final classHR field2871 = new classHR(1, (byte)1);
   @ObfuscatedName("ag")
   final int field2873;
   @ObfuscatedName("as")
   final byte field2872;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field2874 = 7;
   @ToRemove(unused = "true")
   @ObfuscatedName("cl")
   static final int field2876 = 1013;
   @ObfuscatedSignature(descriptor = "Lhr;")
   @ObfuscatedName("az")
   static final classHR field2868 = new classHR(2, (byte)0);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lhr;")
   @ObfuscatedName("ax")
   static classHR[] method6128() {
      return new classHR[]{field2869, field2871, field2868, field2870};
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field2872;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field2872;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hr.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;)Llv;")
   @ObfuscatedName("nt")
   public static classLV method6132(Actor var0) {
      return var0.field1461.field625;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field2872;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("bk")
   static int method6134(int var0, Script var1, boolean var2, byte var3) {
      try {
         if (3500 == var0) {
            int var7 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = client.field932.method4563(var7, 1280114740) ? 1 : 0;
            return 1;
         } else if (var0 == 3501) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               int var6 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = client.field932.method4557(var6, -653933511) ? 1 : 0;
               return 1;
            }
         } else if (var0 == 3502) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               int var4 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               int[] var10000 = classBB.field323;
               int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var10002;
               if (client.field932.method4567(var4, (byte)-118)) {
                  if (var3 == 1) {
                     throw new IllegalStateException();
                  }

                  var10002 = 1;
               } else {
                  var10002 = 0;
               }

               var10000[var10001] = var10002;
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "hr.bk(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field2872;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lhr;")
   @ObfuscatedName("as")
   static classHR[] method6129() {
      return new classHR[]{field2869, field2871, field2868, field2870};
   }

   classHR(int var1, byte var2) {
      this.field2873 = 1701367181 * var1;
      this.field2872 = var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lhr;")
   @ObfuscatedName("ac")
   static classHR[] method6130() {
      return new classHR[]{field2869, field2871, field2868, field2870};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ua")
   public static void method6133(GameEngine var0, WindowEvent var1) {
      if (var0 == null) {
         var0.method11093();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Lhr;")
   @ObfuscatedName("ag")
   static classHR[] method6131(int var0) {
      try {
         return new classHR[]{field2869, field2871, field2868, field2870};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "hr.ag(" + ')');
      }
   }
}
