import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cb")
public class classCB implements classXZ {
   @ObfuscatedSignature(descriptor = "Lcb;")
   @ObfuscatedName("af")
   static final classCB field639 = new classCB(1, 2);
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("hn")
   static classVP field650;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("av")
   public static IndexDataBase field649;
   @ObfuscatedSignature(descriptor = "Lcb;")
   @ObfuscatedName("ab")
   static final classCB field644 = new classCB(3, 4);
   @ObfuscatedSignature(descriptor = "Lcb;")
   @ObfuscatedName("as")
   static final classCB field643 = new classCB(5, 6);
   @ObfuscatedSignature(descriptor = "Lcb;")
   @ObfuscatedName("ag")
   static final classCB field642 = new classCB(4, 5);
   @ObfuscatedSignature(descriptor = "Lcb;")
   @ObfuscatedName("ax")
   static final classCB field641 = new classCB(6, 14);
   @ObfuscatedSignature(descriptor = "Lcb;")
   @ObfuscatedName("ac")
   static final classCB field645 = new classCB(7, 19);
   @ObfuscatedSignature(descriptor = "Lcb;")
   @ObfuscatedName("aa")
   static final classCB field646 = new classCB(8, 22);
   @ObfuscatedName("ao")
   final int field648;
   @ObfuscatedName("al")
   final int field647;
   @ObfuscatedSignature(descriptor = "Lcb;")
   @ObfuscatedName("az")
   static final classCB field638 = new classCB(0, -1);
   @ObfuscatedSignature(descriptor = "Lcb;")
   @ObfuscatedName("ae")
   static final classCB field640 = new classCB(2, 3);

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return 350850931 * this.field647;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cb.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return 350850931 * this.field647;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laae;)I")
   @ObfuscatedName("vy")
   public static int method1401(classAAE var0) {
      return null == var0.field41 ? 0 : var0.field41.hashCode();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return 350850931 * this.field647;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lcb;")
   @ObfuscatedName("ac")
   static classCB[] method1397() {
      return new classCB[]{field646, field641, field642, field640, field644, field643, field639, field645, field638};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lcb;")
   @ObfuscatedName("as")
   static classCB[] method1398() {
      return new classCB[]{field646, field641, field642, field640, field644, field643, field639, field645, field638};
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return 350850931 * this.field647;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lcb;")
   @ObfuscatedName("ax")
   static classCB[] method1399() {
      return new classCB[]{field646, field641, field642, field640, field644, field643, field639, field645, field638};
   }

   classCB(int var1, int var2) {
      this.field648 = -191909887 * var1;
      this.field647 = var2 * -220254789;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lcb;")
   @ObfuscatedName("aa")
   static classCB[] method1400() {
      return new classCB[]{field646, field641, field642, field640, field644, field643, field639, field645, field638};
   }

   @ObfuscatedSignature(descriptor = "(Ldz;I)V")
   @ObfuscatedName("dy")
   static void method1402(WorldView var0, int var1) {
      int var2 = classOE.field4841.method2442();
      if (!client.$assertionsDisabled && var2 != 25 && var2 != 30) {
         throw new AssertionError();
      } else if (!client.$assertionsDisabled && !client.field806) {
         throw new AssertionError();
      } else if (client.field1122 != null) {
         if (client.field1122.method10127()) {
            rl4 var4 = client.field1122;
            client.field1122 = null;
            client.field1137 = false;
            client.method2294(var4);
            client.field806 = false;
            classDA.field1364 = null;
         }
      } else {
         rl4 var3 = new rl4(client.field1117, 0, var0, client.field1007);
         var0.field1712 = var3.field5770.field6883;
         var0.field1710 = var3.field5770.field6882;
         var0.field1711 = var3.field5770.field6885;
         if (!client.$assertionsDisabled && classCQ.field1218 != var0) {
            throw new AssertionError();
         } else {
            var3.field5785 = classCQ.field1218.field1708 * -483624883;
            if (!client.$assertionsDisabled && classDA.field1364 != var0) {
               throw new AssertionError();
            } else if (!client.$assertionsDisabled && client.field1137) {
               throw new AssertionError();
            } else {
               client.field1137 = true;
               if (!client.$assertionsDisabled && client.field1122 != null) {
                  throw new AssertionError();
               } else {
                  client.field1122 = var3;
                  var3.method10126();
               }
            }
         }
      }
   }
}
