import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("md")
public abstract class classMD {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4482 = 0;
   @ObfuscatedName("ab")
   String field4480;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4481 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field4483 = 1;
   @ObfuscatedName("ow")
   static int field4484;
   @ObfuscatedName("ro")
   static int field4485;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ag")
   public int vmethod522() {
      return -1;
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/String;")
   @ObfuscatedName("aa")
   public String method7578(byte var1) {
      try {
         return this.field4480;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "md.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("as")
   static void method7577(byte var0) {
      try {
         classCN.field1169.clear();
         classCN.field1172.method13623();
         classCN.field1171.method8296();
         classCN.field1170 = 0;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "md.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("as")
   public String vmethod519(int var1) {
      try {
         return null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "md.as(" + ')');
      }
   }

   classMD(classMS var1, String var2) {
      this.this$0 = var1;
      this.field4480 = var2;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   public int vmethod523(int var1) {
      try {
         return -1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "md.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   public int vmethod524() {
      return -1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   public abstract int vmethod525(int var1);

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ac")
   public String vmethod520() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ax")
   public String vmethod521() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ao")
   public String method7579() {
      return this.field4480;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lua;I)I")
   @ObfuscatedName("uc")
   public static int method7576(classUA var0, int var1) {
      return var0.field6272.vmethod641(var1, (byte)8);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("al")
   public String method7580() {
      return this.field4480;
   }

   @ObfuscatedSignature(descriptor = "([BB)[B")
   @ObfuscatedName("ba")
   static final byte[] method7581(byte[] var0, byte var1) {
      classXY var2 = new classXY(var0);
      int var3 = var2.method13322();
      int var4 = var2.method13325();
      if (var4 >= 0) {
         if (var3 == 0) {
            byte[] var10 = new byte[var4];
            var2.method13315(var10, 0, var4);
            return var10;
         } else {
            int var5 = var2.method13325();
            if (var5 < 0) {
               throw new RuntimeException();
            } else {
               byte[] var6 = new byte[var5];
               if (var3 == 1) {
                  classVP.method12123(var6, var5, var0, var4, 9);
               } else {
                  synchronized (IndexDataBase.field6437) {
                     IndexDataBase.field6437.method268(var2, var6);
                  }
               }

               return var6;
            }
         }
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   public abstract int vmethod526();
}
