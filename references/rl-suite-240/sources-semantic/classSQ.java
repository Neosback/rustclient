import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sq")
public abstract class classSQ {
   @ObfuscatedName("ai")
   public static int[] field6026;
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   static final int field6025 = 3;

   @ObfuscatedSignature(descriptor = "(Lgy;III)Z")
   @ObfuscatedName("ae")
   abstract boolean vmethod571(classGY var1, int var2, int var3, int var4);

   @ObfuscatedSignature(descriptor = "(Lgy;Lsr;I)V")
   @ObfuscatedName("ps")
   public void method10687(classGY var1, classSR var2, int var3) {
      this.vmethod568(var1, var2, var3, (byte)63);
   }

   classSQ() {
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("al")
   abstract void vmethod574(int var1, int var2);

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ab")
   abstract void vmethod573(int var1, int var2, int var3);

   @ObfuscatedSignature(descriptor = "(Lgy;Lsr;I)V")
   @ObfuscatedName("ac")
   abstract void vmethod569(classGY var1, classSR var2, int var3);

   @ObfuscatedSignature(descriptor = "(Lgy;Lsr;I)V")
   @ObfuscatedName("aa")
   abstract void vmethod570(classGY var1, classSR var2, int var3);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbv;)I")
   @ObfuscatedName("ku")
   public static int method10684(ChatLineBuffer var0) {
      return var0.field583 * 1160694231;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)I")
   @ObfuscatedName("jz")
   public static int method10685(classLH var0) {
      return var0.field4150 * 1072642345;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;)Z")
   @ObfuscatedName("nv")
   public static boolean method10686(Scene var0) {
      return var0.field2031 && var0.field2034 != -1;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;II)Z")
   @ObfuscatedName("ao")
   abstract boolean vmethod572(classGY var1, int var2, int var3);

   @ObfuscatedSignature(descriptor = "(Lgy;II)Z")
   @ObfuscatedName("bh")
   public boolean method10688(classGY var1, int var2, int var3) {
      return this.vmethod571(var1, var2, var3, 1938173336);
   }

   @ObfuscatedSignature(descriptor = "(Lgy;Lsr;IB)V")
   @ObfuscatedName("af")
   abstract void vmethod568(classGY var1, classSR var2, int var3, byte var4);

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aj")
   abstract void vmethod575(int var1, int var2);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)F")
   @ObfuscatedName("aq")
   static float method10681(int var0, int var1, int var2) {
      return (float)(var0 - var1) / (var2 - var1);
   }

   @ObfuscatedSignature(descriptor = "(Lgy;DI)Z")
   @ObfuscatedName("pn")
   public boolean vmethod576(classGY var1, double var2, int var4) {
      return this.method10688(var1, (int)var2, var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)F")
   @ObfuscatedName("ad")
   static float method10682(int var0, int var1, int var2) {
      return (float)(var0 - var1) / (var2 - var1);
   }

   @ObfuscatedSignature(descriptor = "(Lul;I)Lul;")
   @ObfuscatedName("ae")
   static classUL method10683(classUL var0, int var1) {
      try {
         synchronized (classUL.field6321) {
            if (classUL.field6319 * 1171203923 == 0) {
               if (var1 != -506347951) {
                  throw new IllegalStateException();
               } else {
                  return new classUL(var0);
               }
            } else {
               classUL.field6321[(classUL.field6319 -= -1712212773) * 1171203923].method11428(var0, -569935459);
               return classUL.field6321[1171203923 * classUL.field6319];
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "sq.ae(" + ')');
      }
   }
}
