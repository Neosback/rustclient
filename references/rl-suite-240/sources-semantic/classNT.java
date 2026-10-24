import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nt")
public class classNT extends Node {
   @ObfuscatedName("ar")
   int field4732;
   @ObfuscatedName("al")
   int field4728;
   @ObfuscatedSignature(descriptor = "Lny;")
   @ObfuscatedName("ag")
   classNY field4745;
   @ObfuscatedName("af")
   int field4723;
   @ObfuscatedSignature(descriptor = "Lam;")
   @ObfuscatedName("aw")
   classAM field4742;
   @ObfuscatedName("aa")
   int field4720;
   @ObfuscatedName("ao")
   int field4729;
   @ObfuscatedName("av")
   int field4735;
   @ObfuscatedSignature(descriptor = "Laj;")
   @ObfuscatedName("ab")
   classAJ field4741;
   @ObfuscatedName("ac")
   int field4721;
   @ObfuscatedName("as")
   int field4733;
   @ObfuscatedName("ai")
   int field4725;
   @ObfuscatedName("aq")
   int field4731;
   @ObfuscatedName("ax")
   int field4736;
   @ObfuscatedName("au")
   int field4734;
   @ObfuscatedName("ak")
   int field4727;
   @ObfuscatedName("aj")
   int field4726;
   @ObfuscatedName("ay")
   int field4737;
   @ObfuscatedSignature(descriptor = "Lnr;")
   @ObfuscatedName("ae")
   classNR field4744;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4738 = 1048576;
   @ObfuscatedName("ad")
   int field4722;
   @ObfuscatedName("ap")
   int field4730;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   public static final int field4739 = 32768;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final int field4724 = 22;
   @ToRemove(unused = "true")
   @ObfuscatedName("by")
   static final String field4743 = "oldschool";
   @ObfuscatedName("mz")
   static int field4740;

   classNT() {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   void method8042() {
      this.field4744 = null;
      this.field4741 = null;
      this.field4745 = null;
      this.field4742 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   public static final void method8046(int var0) {
      try {
         classFO.field2350 = false;
         classFO.field2348 = 0;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "nt.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   void method8043() {
      this.field4744 = null;
      this.field4741 = null;
      this.field4745 = null;
      this.field4742 = null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lclient;)V")
   @ObfuscatedName("oc")
   public static void method8047(client var0) {
      if (var0 == null) {
         var0.getMapIcons();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;I)V")
   @ObfuscatedName("ao")
   public static void method8044(classNT var0, int var1) {
      if (var0 == null) {
         var0.method8045(var1);
      }

      try {
         var0.field4744 = null;
         var0.field4741 = null;
         var0.field4745 = null;
         var0.field4742 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nt.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   void method8045(int var1) {
      try {
         this.field4744 = null;
         this.field4741 = null;
         this.field4745 = null;
         this.field4742 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nt.az(" + ')');
      }
   }
}
