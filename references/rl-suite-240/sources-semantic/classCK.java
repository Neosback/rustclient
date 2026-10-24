import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ck")
public class classCK implements classXZ {
   @ObfuscatedName("as")
   final int field770;
   @ObfuscatedSignature(descriptor = "Lck;")
   @ObfuscatedName("af")
   static final classCK field769 = new classCK(1);
   @ObfuscatedSignature(descriptor = "Lck;")
   @ObfuscatedName("az")
   static final classCK field765 = new classCK(0);
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field772 = 8192;
   @ObfuscatedSignature(descriptor = "Lck;")
   @ObfuscatedName("ag")
   static final classCK field768 = new classCK(4);
   @ObfuscatedSignature(descriptor = "Lck;")
   @ObfuscatedName("ae")
   static final classCK field767 = new classCK(2);
   @ToRemove(unused = "true")
   @ObfuscatedName("cv")
   static final int field775 = 100;
   @ObfuscatedName("ik")
   static int field776;
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("cc")
   static IndexedSprite field778;
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   public static final int field773 = 25;
   @ObfuscatedSignature(descriptor = "Lck;")
   @ObfuscatedName("ab")
   static final classCK field766 = new classCK(3);
   @ToRemove(unused = "true")
   @ObfuscatedName("bq")
   static final int field774 = 55;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   public static final int field771 = 18;
   @ObfuscatedName("pl")
   static int field777;

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return -1776113227 * this.field770;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ck.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("gu")
   static void method1571(byte[] var0, int var1, int var2) {
      try {
         if (null == client.field945) {
            if (var2 == -6840416) {
               throw new IllegalStateException();
            }

            client.field945 = new byte[24];
         }

         classZZ.method14276(var0, var1, client.field945, 0, 24);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ck.gu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return -1776113227 * this.field770;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;Luz;)V")
   @ObfuscatedName("yq")
   public static void method1570(classUZ var0, classUZ var1) {
      if (var0 == null) {
         var0.method11832(var0);
      } else {
         classUZ.method11802(var0, var1.field6425, var1.field6423, var1.field6424, 1805805294);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return -572266813 * this.field770;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return -1776113227 * this.field770;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("az")
   public static int method1569(int var0, int var1) {
      try {
         return var0 >> 11 & 63;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ck.az(" + 41);
      }
   }

   classCK(int var1) {
      this.field770 = -171328355 * var1;
   }
}
