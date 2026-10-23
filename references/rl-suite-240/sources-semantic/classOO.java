import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("oo")
public class classOO {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5003 = 2047;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field5001 = 65535;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4997 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field4996 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field4999 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4995 = 18;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field5000 = 17;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field4994 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4998 = 20;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field5002 = 13;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvw;)Z")
   @ObfuscatedName("so")
   public static boolean method8661(classVW var0) {
      return var0.field6624;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lyy;")
   @ObfuscatedName("ao")
   public static classYY method8655(int var0) {
      switch (var0 >> 16 & 3) {
         case 0:
            return classYY.field7109;
         case 1:
            return classYY.field7113;
         case 2:
            return classYY.field7111;
         case 3:
            return classYY.field7112;
         default:
            throw new IllegalArgumentException("invalid base type");
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   public static int method8651(int var0) {
      return var0 >> 20 & 2047;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   public static int method8652(int var0) {
      return var0 >> 20 & 98775873;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Log;")
   @ObfuscatedName("ax")
   public static classOG method8653(int var0) {
      switch (var0 >> 18 & 3) {
         case 0:
            return classOG.field4860;
         case 1:
            return classOG.field4861;
         case 2:
            return classOG.field4862;
         default:
            throw new IllegalArgumentException("invalid scope");
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("al")
   public static int method8657(int var0) {
      return var0 & 105034253;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpr;ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("eu")
   public static String method8660(StructComposition var0, int var1, String var2) {
      return classFS.method4998(var0.field5385, var1, var2, (byte)111);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lyy;")
   @ObfuscatedName("aa")
   public static classYY method8656(int var0) {
      switch (var0 >> 16 & 3) {
         case 0:
            return classYY.field7109;
         case 1:
            return classYY.field7113;
         case 2:
            return classYY.field7111;
         case 3:
            return classYY.field7112;
         default:
            throw new IllegalArgumentException("invalid base type");
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bx")
   static final void method8662(int var0) {
   }

   classOO() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aj")
   public static int method8658(int var0) {
      return var0 & 65535;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ay")
   public static int method8659(int var0) {
      return var0 & 65535;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Log;")
   @ObfuscatedName("ac")
   public static classOG method8654(int var0) {
      switch (var0 >> 18 & 3) {
         case 0:
            return classOG.field4860;
         case 1:
            return classOG.field4861;
         case 2:
            return classOG.field4862;
         default:
            throw new IllegalArgumentException("invalid scope");
      }
   }
}
