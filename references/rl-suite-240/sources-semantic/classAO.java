import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ao")
public class classAO extends classAH {
   @ObfuscatedName("ae")
   int field245;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("af")
   Deque2 field246;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("az")
   Deque2 field247 = new Deque2();
   @ObfuscatedName("ab")
   int field248;

   @ObfuscatedSignature(descriptor = "(Lvq;Lbp;)V")
   @ObfuscatedName("ai")
   void method626(Node var1, classBP var2) {
      while (var1 != this.field246.field4689 && ((classBP)var1).field522 <= var2.field522) {
         var1 = var1.field6552;
      }

      Deque2.method7939(var2, var1);
      this.field248 = ((classBP)this.field246.field4689.field6552).field522;
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("an")
   @Override
   protected classAH vmethod33() {
      return (classAH)this.field247.method7965();
   }

   @ObfuscatedSignature(descriptor = "(Lah;)V")
   @ObfuscatedName("af")
   public final synchronized void method619(classAH var1) {
      var1.vmethod398();
   }

   @ObfuscatedSignature(descriptor = "(Lbp;)V")
   @ObfuscatedName("ar")
   void method629(classBP var1) {
      var1.vmethod398();
      var1.method1174();
      Node var2 = this.field246.field4689.field6552;
      if (var2 == this.field246.field4689) {
         this.field248 = -1;
      } else {
         this.field248 = ((classBP)var2).field522;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;Lbp;)V")
   @ObfuscatedName("ab")
   void method627(Node var1, classBP var2) {
      while (var1 != this.field246.field4689 && ((classBP)var1).field522 <= var2.field522) {
         var1 = var1.field6552;
      }

      Deque2.method7939(var2, var1);
      this.field248 = ((classBP)this.field246.field4689.field6552).field522;
   }

   @ObfuscatedSignature(descriptor = "(Lbp;)V")
   @ObfuscatedName("ag")
   void method630(classBP var1) {
      var1.vmethod398();
      var1.method1174();
      Node var2 = this.field246.field4689.field6552;
      if (var2 == this.field246.field4689) {
         this.field248 = -1;
      } else {
         this.field248 = ((classBP)var2).field522;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lnn;")
   @ObfuscatedName("qy")
   public Deque2 method642() {
      return this.field247;
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("as")
   @Override
   protected classAH vmethod29() {
      return (classAH)this.field247.method7957();
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("ax")
   @Override
   protected classAH vmethod35() {
      return (classAH)this.field247.method7965();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   @Override
   protected int vmethod32() {
      return 0;
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("aa")
   @Override
   public final synchronized void vmethod30(int[] var1, int var2, int var3) {
      while (this.field248 >= 0) {
         if (this.field245 + var3 < this.field248) {
            this.field245 += var3;
            this.method633(var1, var2, var3);
            return;
         }

         int var4 = this.field248 - this.field245;
         this.method633(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
         this.field245 += var4;
         method621(this);
         classBP var5 = (classBP)this.field246.method7957();
         synchronized (var5) {
            int var7 = var5.method1175(this);
            if (var7 < 0) {
               var5.field522 = 0;
               this.method630(var5);
            } else {
               var5.field522 = var7;
               this.method627(var5.field6552, var5);
            }
         }

         if (var3 == 0) {
            return;
         }
      }

      this.method633(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(Lah;)V")
   @ObfuscatedName("rh")
   public void method643(classAH var1) {
      this.method618(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lao;)V")
   @ObfuscatedName("mt")
   public static void method621(classAO var0) {
      if (var0 == null) {
         var0.method625();
      }

      if (var0.field245 > 0) {
         for (classBP var1 = (classBP)var0.field246.method7957(); var1 != null; var1 = (classBP)var0.field246.method7965()) {
            var1.field522 = var1.field522 - var0.field245;
         }

         var0.field248 = var0.field248 - var0.field245;
         var0.field245 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("ao")
   void method633(int[] var1, int var2, int var3) {
      for (classAH var4 = (classAH)this.field247.method7957(); var4 != null; var4 = (classAH)this.field247.method7965()) {
         var4.method359(var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("am")
   @Override
   protected classAH vmethod44() {
      return (classAH)this.field247.method7965();
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("ah")
   @Override
   protected classAH vmethod34() {
      return (classAH)this.field247.method7965();
   }

   @ObfuscatedSignature(descriptor = "(Lah;)V")
   @ObfuscatedName("ay")
   public final synchronized void method620(classAH var1) {
      var1.vmethod398();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   void method622() {
      if (this.field245 > 0) {
         for (classBP var1 = (classBP)this.field246.method7957(); var1 != null; var1 = (classBP)this.field246.method7965()) {
            var1.field522 = var1.field522 - this.field245;
         }

         this.field248 = this.field248 - this.field245;
         this.field245 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bt")
   void method634(int[] var1, int var2, int var3) {
      for (classAH var4 = (classAH)this.field247.method7957(); var4 != null; var4 = (classAH)this.field247.method7965()) {
         var4.method359(var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   void method623() {
      if (this.field245 > 0) {
         for (classBP var1 = (classBP)this.field246.method7957(); var1 != null; var1 = (classBP)this.field246.method7965()) {
            var1.field522 = var1.field522 - this.field245;
         }

         this.field248 = this.field248 - this.field245;
         this.field245 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;Lbp;)V")
   @ObfuscatedName("au")
   void method628(Node var1, classBP var2) {
      while (var1 != this.field246.field4689 && ((classBP)var1).field522 <= var2.field522) {
         var1 = var1.field6552;
      }

      Deque2.method7939(var2, var1);
      this.field248 = ((classBP)this.field246.field4689.field6552).field522;
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bk")
   void method635(int[] var1, int var2, int var3) {
      for (classAH var4 = (classAH)this.field247.method7957(); var4 != null; var4 = (classAH)this.field247.method7965()) {
         var4.method359(var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   void method624() {
      if (this.field245 > 0) {
         for (classBP var1 = (classBP)this.field246.method7957(); var1 != null; var1 = (classBP)this.field246.method7965()) {
            var1.field522 = var1.field522 - this.field245;
         }

         this.field248 = this.field248 - this.field245;
         this.field245 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lbp;)V")
   @ObfuscatedName("aw")
   void method631(classBP var1) {
      var1.vmethod398();
      var1.method1174();
      Node var2 = this.field246.field4689.field6552;
      if (var2 == this.field246.field4689) {
         this.field248 = -1;
      } else {
         this.field248 = ((classBP)var2).field522;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   @Override
   public final synchronized void vmethod31(int var1) {
      while (this.field248 >= 0) {
         if (this.field245 + var1 < this.field248) {
            this.field245 += var1;
            this.method640(var1);
            return;
         }

         int var2 = this.field248 - this.field245;
         this.method640(var2);
         var1 -= var2;
         this.field245 += var2;
         method621(this);
         classBP var3 = (classBP)this.field246.method7957();
         synchronized (var3) {
            int var5 = var3.method1175(this);
            if (var5 < 0) {
               var3.field522 = 0;
               this.method630(var3);
            } else {
               var3.field522 = var5;
               this.method627(var3.field6552, var3);
            }
         }

         if (var1 == 0) {
            return;
         }
      }

      this.method640(var1);
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("av")
   @Override
   protected classAH vmethod43() {
      return (classAH)this.field247.method7957();
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("at")
   @Override
   protected classAH vmethod27() {
      return (classAH)this.field247.method7957();
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bx")
   @Override
   public final synchronized void vmethod36(int[] var1, int var2, int var3) {
      while (this.field248 >= 0) {
         if (this.field245 + var3 < this.field248) {
            this.field245 += var3;
            this.method633(var1, var2, var3);
            return;
         }

         int var4 = this.field248 - this.field245;
         this.method633(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
         this.field245 += var4;
         method621(this);
         classBP var5 = (classBP)this.field246.method7957();
         synchronized (var5) {
            int var7 = var5.method1175(this);
            if (var7 < 0) {
               var5.field522 = 0;
               this.method630(var5);
            } else {
               var5.field522 = var7;
               this.method627(var5.field6552, var5);
            }
         }

         if (var3 == 0) {
            return;
         }
      }

      this.method633(var1, var2, var3);
   }

   public classAO() {
      this.field246 = new Deque2();
      this.field245 = 0;
      this.field248 = -1;
   }

   @ObfuscatedSignature(descriptor = "(Lbp;)V")
   @ObfuscatedName("ak")
   void method632(classBP var1) {
      var1.vmethod398();
      var1.method1174();
      Node var2 = this.field246.field4689.field6552;
      if (var2 == this.field246.field4689) {
         this.field248 = -1;
      } else {
         this.field248 = ((classBP)var2).field522;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   @Override
   protected int vmethod28() {
      return 0;
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("br")
   @Override
   public final synchronized void vmethod39(int[] var1, int var2, int var3) {
      while (this.field248 >= 0) {
         if (this.field245 + var3 < this.field248) {
            this.field245 += var3;
            this.method633(var1, var2, var3);
            return;
         }

         int var4 = this.field248 - this.field245;
         this.method633(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
         this.field245 += var4;
         method621(this);
         classBP var5 = (classBP)this.field246.method7957();
         synchronized (var5) {
            int var7 = var5.method1175(this);
            if (var7 < 0) {
               var5.field522 = 0;
               this.method630(var5);
            } else {
               var5.field522 = var7;
               this.method627(var5.field6552, var5);
            }
         }

         if (var3 == 0) {
            return;
         }
      }

      this.method633(var1, var2, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lao;I)V")
   @ObfuscatedName("vp")
   public static void method639(classAO var0, int var1) {
      for (classAH var2 = (classAH)var0.field247.method7957(); var2 != null; var2 = (classAH)var0.field247.method7965()) {
         var2.vmethod31(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bc")
   @Override
   public final synchronized void vmethod37(int[] var1, int var2, int var3) {
      while (this.field248 >= 0) {
         if (this.field245 + var3 < this.field248) {
            this.field245 += var3;
            this.method633(var1, var2, var3);
            return;
         }

         int var4 = this.field248 - this.field245;
         this.method633(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
         this.field245 += var4;
         method621(this);
         classBP var5 = (classBP)this.field246.method7957();
         synchronized (var5) {
            int var7 = var5.method1175(this);
            if (var7 < 0) {
               var5.field522 = 0;
               this.method630(var5);
            } else {
               var5.field522 = var7;
               this.method627(var5.field6552, var5);
            }
         }

         if (var3 == 0) {
            return;
         }
      }

      this.method633(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bp")
   @Override
   public final synchronized void vmethod38(int[] var1, int var2, int var3) {
      while (this.field248 >= 0) {
         if (this.field245 + var3 < this.field248) {
            this.field245 += var3;
            this.method633(var1, var2, var3);
            return;
         }

         int var4 = this.field248 - this.field245;
         this.method633(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
         this.field245 += var4;
         method621(this);
         classBP var5 = (classBP)this.field246.method7957();
         synchronized (var5) {
            int var7 = var5.method1175(this);
            if (var7 < 0) {
               var5.field522 = 0;
               this.method630(var5);
            } else {
               var5.field522 = var7;
               this.method627(var5.field6552, var5);
            }
         }

         if (var3 == 0) {
            return;
         }
      }

      this.method633(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bw")
   void method640(int var1) {
      for (classAH var2 = (classAH)this.field247.method7957(); var2 != null; var2 = (classAH)this.field247.method7965()) {
         var2.vmethod31(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bm")
   void method636(int[] var1, int var2, int var3) {
      for (classAH var4 = (classAH)this.field247.method7957(); var4 != null; var4 = (classAH)this.field247.method7965()) {
         var4.method359(var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lah;)V")
   @ObfuscatedName("az")
   public final synchronized void method618(classAH var1) {
      this.field247.method7949(var1);
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bg")
   void method637(int[] var1, int var2, int var3) {
      for (classAH var4 = (classAH)this.field247.method7957(); var4 != null; var4 = (classAH)this.field247.method7965()) {
         var4.method359(var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bj")
   void method638(int[] var1, int var2, int var3) {
      for (classAH var4 = (classAH)this.field247.method7957(); var4 != null; var4 = (classAH)this.field247.method7965()) {
         var4.method359(var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bu")
   @Override
   public final synchronized void vmethod41(int var1) {
      while (this.field248 >= 0) {
         if (this.field245 + var1 < this.field248) {
            this.field245 += var1;
            this.method640(var1);
            return;
         }

         int var2 = this.field248 - this.field245;
         this.method640(var2);
         var1 -= var2;
         this.field245 += var2;
         method621(this);
         classBP var3 = (classBP)this.field246.method7957();
         synchronized (var3) {
            int var5 = var3.method1175(this);
            if (var5 < 0) {
               var3.field522 = 0;
               this.method630(var3);
            } else {
               var3.field522 = var5;
               this.method627(var3.field6552, var3);
            }
         }

         if (var1 == 0) {
            return;
         }
      }

      this.method640(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bo")
   @Override
   public final synchronized void vmethod40(int var1) {
      while (this.field248 >= 0) {
         if (this.field245 + var1 < this.field248) {
            this.field245 += var1;
            this.method640(var1);
            return;
         }

         int var2 = this.field248 - this.field245;
         this.method640(var2);
         var1 -= var2;
         this.field245 += var2;
         method621(this);
         classBP var3 = (classBP)this.field246.method7957();
         synchronized (var3) {
            int var5 = var3.method1175(this);
            if (var5 < 0) {
               var3.field522 = 0;
               this.method630(var3);
            } else {
               var3.field522 = var5;
               this.method627(var3.field6552, var3);
            }
         }

         if (var1 == 0) {
            return;
         }
      }

      this.method640(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ba")
   @Override
   public final synchronized void vmethod42(int var1) {
      while (this.field248 >= 0) {
         if (this.field245 + var1 < this.field248) {
            this.field245 += var1;
            this.method640(var1);
            return;
         }

         int var2 = this.field248 - this.field245;
         this.method640(var2);
         var1 -= var2;
         this.field245 += var2;
         method621(this);
         classBP var3 = (classBP)this.field246.method7957();
         synchronized (var3) {
            int var5 = var3.method1175(this);
            if (var5 < 0) {
               var3.field522 = 0;
               this.method630(var3);
            } else {
               var3.field522 = var5;
               this.method627(var3.field6552, var3);
            }
         }

         if (var1 == 0) {
            return;
         }
      }

      this.method640(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aj")
   void method641(int var1) {
      for (classAH var2 = (classAH)this.field247.method7957(); var2 != null; var2 = (classAH)this.field247.method7965()) {
         var2.vmethod31(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   void method625() {
      if (this.field245 > 0) {
         for (classBP var1 = (classBP)this.field247.method7951(); var1 != null; var1 = (classBP)this.field246.method7965()) {
            var1.field522 = var1.field522 - this.field245;
         }

         this.field248 = this.field248 - this.field248;
         this.field245 = 0;
      }
   }
}
