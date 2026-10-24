import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bv")
public class ChatLineBuffer implements net.runelite.api.ChatLineBuffer {
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("de")
   static IndexedSprite field590;
   @ToRemove(unused = "true")
   @ObfuscatedName("bo")
   static final int field588 = 51;
   @ObfuscatedName("ae")
   int field583;
   @ObfuscatedSignature(descriptor = "[Lcg;")
   @ObfuscatedName("af")
   MessageNode[] field580 = new MessageNode[100];
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field585 = 2301979;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field582 = 9;
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final String field589 = "_z";
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field586 = 100;
   @ToRemove(unused = "true")
   @ObfuscatedName("bh")
   public static final int field587 = 66;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field584 = 7;

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Lcg;")
   @ObfuscatedName("az")
   MessageNode method1250(int var1, String var2, String var3, String var4, int var5) {
      try {
         MessageNode var6 = this.field580[99];

         for (int var7 = this.field583 * 1160694231; var7 > 0; var7--) {
            if (var5 >= 714218958) {
               throw new IllegalStateException();
            }

            if (100 != var7) {
               this.field580[var7] = this.field580[var7 - 1];
            }
         }

         if (var6 == null) {
            if (var5 >= 714218958) {
               throw new IllegalStateException();
            }

            var6 = new MessageNode(var1, var2, var4, var3);
         } else {
            var6.vmethod398();
            var6.method12035();
            var6.method1461(var1, var2, var4, var3, (byte)-84);
         }

         this.field580[0] = var6;
         if (this.field583 * 1160694231 < 100) {
            if (var5 >= 714218958) {
               throw new IllegalStateException();
            }

            this.field583 += -771671577;
         }

         return var6;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "bv.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("sm")
   public void method1258(int var1) {
      this.field583 = var1 * -771671577;
   }

   @ObfuscatedSignature(descriptor = "(II)Lcg;")
   @ObfuscatedName("af")
   MessageNode method1252(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 == 620798481) {
               throw new IllegalStateException();
            }

            if (var1 < 1160694231 * this.field583) {
               return this.field580[var1];
            }

            if (var2 == 620798481) {
               throw new IllegalStateException();
            }
         }

         return null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "bv.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("am")
   public static int method1257(int var0) {
      try {
         return classCS.field1246 * -1766337085 - 1;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "bv.am(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcg;")
   @ObfuscatedName("ab")
   MessageNode method1251(int var1, String var2, String var3, String var4) {
      MessageNode var5 = this.field580[99];

      for (int var6 = this.field583 * 1160694231; var6 > 0; var6--) {
         if (100 != var6) {
            this.field580[var6] = this.field580[var6 - 1];
         }
      }

      if (var5 == null) {
         var5 = new MessageNode(var1, var2, var4, var3);
      } else {
         var5.vmethod398();
         var5.method12035();
         var5.method1461(var1, var2, var4, var3, (byte)-98);
      }

      this.field580[0] = var5;
      if (this.field583 * 1160694231 < 100) {
         this.field583 += -771671577;
      }

      return var5;
   }

   ChatLineBuffer() {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbv;I)Lcg;")
   @ObfuscatedName("ly")
   public static MessageNode method1253(ChatLineBuffer var0, int var1) {
      if (var0 == null) {
         var0.getLines();
      }

      return var1 >= 0 && var1 < 1160694231 * var0.field583 ? var0.field580[var1] : null;
   }

   public int getLength() {
      return this.field583 * 1160694231;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("as")
   int method1254() {
      return this.field583 * 1160694231;
   }

   public net.runelite.api.MessageNode[] getLines() {
      return this.field580;
   }

   public void removeMessageNode(net.runelite.api.MessageNode var1) {
      net.runelite.api.MessageNode[] var2 = this.getLines();
      int var3 = this.getLength();
      int var4 = -1;

      for (int var5 = 0; var5 < var3; var5++) {
         if (var2[var5] == var1) {
            var4 = var5;
            break;
         }
      }

      if (var4 != -1) {
         assert var3 - 1 >= var4;

         System.arraycopy(var2, var4 + 1, var2, var4, var3 - 1 - var4);
         var2[var3 - 1] = null;
         this.method1258(var3 - 1);
         classVJ var6 = (classVJ)var1;
         var6.method12135();
         var6.method12038();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lbv;B)I")
   @ObfuscatedName("pl")
   public static int method1255(ChatLineBuffer var0, byte var1) {
      if (var0 == null) {
         var0.method1256(var1);
      }

      try {
         return var0.field583 * 1160694231;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bv.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ae")
   int method1256(byte var1) {
      try {
         return this.field583 * 1160694231;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bv.ae(" + 41);
      }
   }
}
