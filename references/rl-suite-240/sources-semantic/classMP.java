import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mp")
public class classMP extends classMD {
   @ObfuscatedName("az")
   String field4549;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field4550 = 1024;

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ac")
   @Override
   public String vmethod520() {
      return this.field4549;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod525(int var1) {
      try {
         return 1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mp.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ax")
   @Override
   public String vmethod521() {
      return this.field4549;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod526() {
      return 1;
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIS)V")
   @ObfuscatedName("az")
   static void method7680(Widget var0, int var1, int var2, short var3) {
      try {
         if (var0.field4262 * 404669979 == 0) {
            if (var3 == 2048) {
               throw new IllegalStateException();
            }

            var0.field4279 = var0.field4246 * 1865716123;
            var0.method7357(-1);
         } else if (1 == var0.field4262 * 404669979) {
            if (var3 == 2048) {
               throw new IllegalStateException();
            }

            var0.field4279 = ((var1 - 1115597881 * var0.field4247) / 2 + 975005911 * var0.field4246) * 2084925661;
            var0.method7357(-1);
         } else if (var0.field4262 * 404669979 == 2) {
            if (var3 == 2048) {
               throw new IllegalStateException();
            }

            var0.field4279 = (var1 - var0.field4247 * 1115597881 - var0.field4246 * 975005911) * 2084925661;
            var0.method7357(-1);
         } else if (404669979 * var0.field4262 == 3) {
            var0.field4279 = 2084925661 * (var1 * var0.field4246 * 975005911 >> 14);
            var0.method7357(-1);
         } else if (4 == var0.field4262 * 404669979) {
            var0.field4279 = 2084925661 * ((var1 - 1115597881 * var0.field4247) / 2 + (var0.field4246 * 975005911 * var1 >> 14));
            var0.method7357(-1);
         } else {
            var0.field4279 = (var1 - var0.field4247 * 1115597881 - (var1 * var0.field4246 * 975005911 >> 14)) * 2084925661;
            var0.method7357(-1);
         }

         if (0 == var0.field4248 * 923944517) {
            if (var3 == 2048) {
               return;
            }

            var0.field4255 = -160021231 * var0.field4283;
            var0.method7367(-1);
         } else if (923944517 * var0.field4248 == 1) {
            var0.field4255 = ((var2 - 293773697 * var0.field4257) / 2 + var0.field4283 * -265516179) * -217131595;
            var0.method7367(-1);
         } else if (2 == var0.field4248 * 923944517) {
            if (var3 == 2048) {
               throw new IllegalStateException();
            }

            var0.field4255 = (var2 - 293773697 * var0.field4257 - -265516179 * var0.field4283) * -217131595;
            var0.method7367(-1);
         } else if (3 == var0.field4248 * 923944517) {
            var0.field4255 = (var2 * -265516179 * var0.field4283 >> 14) * -217131595;
            var0.method7367(-1);
         } else if (4 == var0.field4248 * 923944517) {
            if (var3 == 2048) {
               throw new IllegalStateException();
            }

            var0.field4255 = -217131595 * ((var2 * var0.field4283 * -265516179 >> 14) + (var2 - var0.field4257 * 293773697) / 2);
            var0.method7367(-1);
         } else {
            var0.field4255 = (var2 - var0.field4257 * 293773697 - (var2 * -265516179 * var0.field4283 >> 14)) * -217131595;
            var0.method7367(-1);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mp.az(" + ')');
      }
   }

   classMP(classMS var1, String var2, String var3) {
      super(var1, var2);
      this.this$0 = var1;
      this.field4549 = var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqg;)Ljava/lang/String;")
   @ObfuscatedName("ip")
   public static String method7681(classQG var0) {
      return var0.field5530;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("as")
   @Override
   public String vmethod519(int var1) {
      try {
         return this.field4549;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mp.as(" + ')');
      }
   }
}
