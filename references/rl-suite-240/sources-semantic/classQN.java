import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qn")
public class classQN {
   @ObfuscatedSignature(descriptor = "Lnv;")
   @ObfuscatedName("af")
   classNV field5565;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("ib")
   static classVP field5570;
   @ToRemove(unused = "true")
   @ObfuscatedName("bh")
   static final int field5569 = 100;
   @ObfuscatedSignature(descriptor = "Lnv;")
   @ObfuscatedName("ab")
   classNV field5567;
   @ObfuscatedSignature(descriptor = "Lnv;")
   @ObfuscatedName("az")
   classNV field5566 = new classNV();
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   public static final int field5568 = 25;
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("ch")
   static IndexedSprite field5571;
   @ObfuscatedSignature(descriptor = "Lnv;")
   @ObfuscatedName("ae")
   classNV field5564;

   @ObfuscatedSignature(descriptor = "(Lqn;Lzr;I)V")
   @ObfuscatedName("en")
   public static void method9711(classQN var0, ScriptEvent var1, int var2) {
      if (var0 == null) {
         var0.method9713(var1, var2);
      }

      try {
         var0.field5564.method8196(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qn.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfm;I)V")
   @ObfuscatedName("ab")
   void method9714(classFM var1, int var2) {
      try {
         this.field5567.method8196(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qn.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqn;Lzr;)V")
   @ObfuscatedName("eq")
   public static void method9705(classQN var0, ScriptEvent var1) {
      var0.field5566.method8196(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lzr;B)V")
   @ObfuscatedName("af")
   void method9710(ScriptEvent var1, byte var2) {
      try {
         this.field5565.method8196(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qn.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lfm;")
   @ObfuscatedName("ac")
   classFM method9724(byte var1) {
      try {
         return (classFM)this.field5567.method8204();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qn.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lzr;")
   @ObfuscatedName("as")
   ScriptEvent method9719(int var1) {
      try {
         return (ScriptEvent)this.field5565.method8204();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qn.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lzr;")
   @ObfuscatedName("at")
   ScriptEvent method9720() {
      return (ScriptEvent)this.field5565.method8204();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bx")
   void method9729() {
      for (classFM var1 = (classFM)this.field5567.method8208(); null != var1; var1 = (classFM)this.field5567.method8216()) {
         var1.vmethod398();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqn;)Lzr;")
   @ObfuscatedName("xj")
   public static ScriptEvent method9721(classQN var0) {
      return (ScriptEvent)var0.field5565.method8204();
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   void method9726(int var1) {
      try {
         for (ScriptEvent var2 = (ScriptEvent)this.field5566.method8208(); null != var2; var2 = (ScriptEvent)this.field5566.method8216()) {
            if (var1 <= 1422993977) {
               throw new IllegalStateException();
            }

            if (ScriptEvent.method14182(var2, -1750837176)) {
               if (var1 <= 1422993977) {
                  return;
               }

               var2.vmethod398();
               var2.method14144(-309191397).field4320 = false;
            }
         }

         this.method9730(-1498603398);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qn.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   void method9730(int var1) {
      try {
         for (classFM var2 = (classFM)this.field5567.method8208(); null != var2; var2 = (classFM)this.field5567.method8216()) {
            if (var1 == 814915695) {
               return;
            }

            var2.vmethod398();
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qn.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   void method9733(int var1) {
      try {
         for (ScriptEvent var2 = (ScriptEvent)this.field5566.method8208(); null != var2; var2 = (ScriptEvent)this.field5566.method8216()) {
            if (var1 == 440418608) {
               throw new IllegalStateException();
            }

            if (ScriptEvent.method14182(var2, -1578156326)) {
               if (var1 == 440418608) {
                  return;
               }

               if (var2.method14142(1853529316) == var2.method14144(1930927931).field4385) {
                  if (var1 == 440418608) {
                     throw new IllegalStateException();
                  }

                  var2.vmethod398();
               }
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qn.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzr;)V")
   @ObfuscatedName("ad")
   public void method9706(ScriptEvent var1) {
      this.field5566.method8196(var1);
   }

   @ObfuscatedSignature(descriptor = "()Lfm;")
   @ObfuscatedName("ah")
   classFM method9725() {
      return (classFM)this.field5567.method8204();
   }

   @ObfuscatedSignature(descriptor = "(Lzr;)V")
   @ObfuscatedName("ap")
   public void method9707(ScriptEvent var1) {
      this.field5566.method8196(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lzr;)V")
   @ObfuscatedName("ay")
   public void method9708(ScriptEvent var1) {
      this.field5566.method8196(var1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bp")
   void method9727() {
      for (ScriptEvent var1 = (ScriptEvent)this.field5566.method8208(); null != var1; var1 = (ScriptEvent)this.field5566.method8216()) {
         if (ScriptEvent.method14182(var1, -1238165748)) {
            var1.vmethod398();
            var1.method14144(1323379080).field4320 = false;
         }
      }

      this.method9730(-1051245);
   }

   @ObfuscatedSignature(descriptor = "(Lqn;B)Lzr;")
   @ObfuscatedName("zw")
   public static ScriptEvent method9717(classQN var0, byte var1) {
      if (var0 == null) {
         return var0.method9718(var1);
      } else {
         try {
            return (ScriptEvent)var0.field5566.method8204();
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "qn.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfm;)V")
   @ObfuscatedName("aw")
   void method9715(classFM var1) {
      this.field5567.method8196(var1);
   }

   public classQN() {
      this.field5565 = new classNV();
      this.field5564 = new classNV();
      this.field5567 = new classNV();
   }

   @ObfuscatedSignature(descriptor = "(Lzr;)V")
   @ObfuscatedName("au")
   void method9712(ScriptEvent var1) {
      this.field5564.method8196(var1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bm")
   void method9728() {
      for (ScriptEvent var1 = (ScriptEvent)this.field5566.method8208(); null != var1; var1 = (ScriptEvent)this.field5566.method8216()) {
         if (ScriptEvent.method14182(var1, -1590274217)) {
            var1.vmethod398();
            var1.method14144(2116735544).field4320 = false;
         }
      }

      this.method9730(-773742811);
   }

   @ObfuscatedSignature(descriptor = "()Lzr;")
   @ObfuscatedName("am")
   ScriptEvent method9722() {
      return (ScriptEvent)this.field5564.method8204();
   }

   @ObfuscatedSignature(descriptor = "(I)Lzr;")
   @ObfuscatedName("ax")
   ScriptEvent method9723(int var1) {
      try {
         return (ScriptEvent)this.field5564.method8204();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qn.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   void method9731() {
      for (classFM var1 = (classFM)this.field5567.method8208(); null != var1; var1 = (classFM)this.field5567.method8216()) {
         var1.vmethod398();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzr;I)V")
   @ObfuscatedName("az")
   public void method9709(ScriptEvent var1, int var2) {
      try {
         this.field5566.method8196(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qn.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("br")
   void method9732() {
      for (classFM var1 = (classFM)this.field5567.method8208(); null != var1; var1 = (classFM)this.field5567.method8216()) {
         var1.vmethod398();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfm;)V")
   @ObfuscatedName("ar")
   void method9716(classFM var1) {
      this.field5567.method8196(var1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bc")
   void method9734() {
      for (ScriptEvent var1 = (ScriptEvent)this.field5566.method8208(); null != var1; var1 = (ScriptEvent)this.field5566.method8216()) {
         if (ScriptEvent.method14182(var1, -1646822706) && var1.method14142(1834571774) == var1.method14144(1403727204).field4385) {
            var1.vmethod398();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzr;I)V")
   @ObfuscatedName("ae")
   void method9713(ScriptEvent var1, int var2) {
      try {
         this.field5564.method8203(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qn.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lzr;")
   @ObfuscatedName("ag")
   ScriptEvent method9718(byte var1) {
      try {
         return (ScriptEvent)this.field5566.method8206();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qn.ag(" + ')');
      }
   }
}
