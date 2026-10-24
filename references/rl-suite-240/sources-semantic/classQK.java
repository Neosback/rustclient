import java.io.IOException;
import java.util.concurrent.Callable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qk")
public class classQK implements Callable {
   @ObfuscatedSignature(descriptor = "Lqa;")
   @ObfuscatedName("az")
   final classQA field5555;

   @ObfuscatedSignature(descriptor = "(Lxy;BIB)I")
   @ObfuscatedName("af")
   public static int method9651(classXY var0, byte var1, int var2, byte var3) {
      try {
         int var4 = var1 >> var2 & 3;
         if (var4 == 3) {
            if (var3 <= 7) {
               throw new IllegalStateException();
            } else {
               return classXY.method13113(var0, (byte)1);
            }
         } else if (2 == var4) {
            if (var3 <= 7) {
               throw new IllegalStateException();
            } else {
               return var0.method13104((byte)16);
            }
         } else if (1 == var4) {
            if (var3 <= 7) {
               throw new IllegalStateException();
            } else {
               return var0.method13097((byte)17);
            }
         } else {
            return 0;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "qk.af(" + 41);
      }
   }

   @Override
   public Object call() throws Exception {
      try {
         try {
            while (this.field5555.method9468(819117750)) {
               Friend.method10197(10L);
            }
         } catch (IOException var2) {
            return new classQG("Error servicing REST query: " + var2.getMessage());
         }

         return this.field5555.method9472((byte)47);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qk.call(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgn;)I")
   @ObfuscatedName("vq")
   public static int method9652(classGN var0) {
      return 1086959991 * var0.field2628;
   }

   classQK(classQD var1, classQA var2) {
      this.this$0 = var1;
      this.field5555 = var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lky;)Ljava/lang/String;")
   @ObfuscatedName("ji")
   public static String method9655(classKY var0) {
      if (var0 == null) {
         var0.method6665();
      }

      return var0.method6686(",", 1492919650);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("az")
   public Object method9653() throws Exception {
      try {
         while (this.field5555.method9468(-1143283457)) {
            Friend.method10197(10L);
         }
      } catch (IOException var2) {
         return new classQG("Error servicing REST query: " + var2.getMessage());
      }

      return this.field5555.method9472((byte)95);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("af")
   public Object method9654() throws Exception {
      try {
         while (this.field5555.method9468(1333093394)) {
            Friend.method10197(10L);
         }
      } catch (IOException var2) {
         return new classQG("Error servicing REST query: " + var2.getMessage());
      }

      return this.field5555.method9472((byte)-3);
   }
}
