import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nz")
public class classNZ implements Iterable {
   @ObfuscatedSignature(descriptor = "Lvj;")
   @ObfuscatedName("af")
   classVJ field4799;
   @ObfuscatedSignature(descriptor = "Lvj;")
   @ObfuscatedName("az")
   public classVJ field4798 = new classVJ();

   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("ay")
   public void method8299(classVJ var1) {
      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = this.field4798.field6514;
      var1.field6513 = this.field4798;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }

   public classNZ() {
      this.field4798.field6513 = this.field4798;
      this.field4798.field6514 = this.field4798;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   public void method8296() {
      while (this.field4798.field6513 != this.field4798) {
         this.field4798.field6513.method12035();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvj;Lvj;)V")
   @ObfuscatedName("az")
   public static void method8294(classVJ var0, classVJ var1) {
      if (var0.field6514 != null) {
         var0.method12035();
      }

      var0.field6514 = var1;
      var0.field6513 = var1.field6513;
      var0.field6514.field6513 = var0;
      var0.field6513.field6514 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Ljava/lang/String;Ljava/lang/String;)Z")
   @ObfuscatedName("zk")
   public static boolean method8317(IndexDataBase var0, String var1, String var2) {
      if (var0 == null) {
         var0.method11951(var1);
      }

      var1 = var1.toLowerCase();
      var2 = var2.toLowerCase();
      int var3 = var0.field6442.method14236(classQI.method9632(var1, 1922263806), -2100537001);
      if (var3 < 0) {
         return false;
      } else {
         int var4 = var0.field6452[var3].method14236(classQI.method9632(var2, 923161390), -2141017414);
         return var4 >= 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnz;)Lvj;")
   @ObfuscatedName("fx")
   public static classVJ method8310(classNZ var0) {
      classVJ var1 = var0.field4799;
      if (var1 == var0.field4798) {
         var0.field4799 = null;
         return null;
      } else {
         var0.field4799 = var1.field6513;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvj;")
   @ObfuscatedName("ab")
   public classVJ method8303() {
      classVJ var1 = this.field4798.field6513;
      if (var1 == this.field4798) {
         return null;
      } else {
         var1.method12035();
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvj;")
   @ObfuscatedName("ag")
   public classVJ method8304() {
      return classZM.method14116(this, null);
   }

   @Override
   public Iterator iterator() {
      return new classNX(this);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)Z")
   @ObfuscatedName("rm")
   public static boolean method8316(ClientPreferences var0) {
      return var0.field1349;
   }

   @ObfuscatedSignature(descriptor = "()Lvj;")
   @ObfuscatedName("ax")
   public classVJ method8311() {
      classVJ var1 = this.field4799;
      if (var1 == this.field4798) {
         this.field4799 = null;
         return null;
      } else {
         this.field4799 = var1.field6513;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("be")
   public Iterator method8314() {
      return new classNX(this);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnz;)Lvj;")
   @ObfuscatedName("qc")
   public static classVJ method8312(classNZ var0) {
      classVJ var1 = var0.field4799;
      if (var1 == var0.field4798) {
         var0.field4799 = null;
         return null;
      } else {
         var0.field4799 = var1.field6513;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("by")
   public Iterator method8315() {
      return new classNX(this);
   }

   @ObfuscatedSignature(descriptor = "(Lvj;Lvj;)V")
   @ObfuscatedName("ac")
   public static void method8295(classVJ var0, classVJ var1) {
      if (var0.field6514 != null) {
         var0.method12035();
      }

      var0.field6514 = var1;
      var0.field6513 = var1.field6513;
      var0.field6514.field6513 = var0;
      var0.field6513.field6514 = var0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   public void method8297() {
      while (this.field4798.field6513 != this.field4798) {
         this.field4798.field6513.method12035();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("al")
   public void method8300(classVJ var1) {
      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = this.field4798.field6514;
      var1.field6513 = this.field4798;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   public void method8298() {
      while (this.field4798.field6513 != this.field4798) {
         this.field4798.field6513.method12035();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("aq")
   public void method8301(classVJ var1) {
      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = this.field4798.field6514;
      var1.field6513 = this.field4798;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("ae")
   public void method8302(classVJ var1) {
      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = this.field4798.field6514;
      var1.field6513 = this.field4798;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;)V")
   @ObfuscatedName("yw")
   public static void method8318(Actor var0) {
      var0.field1461.method1388(387771610);
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)Lvj;")
   @ObfuscatedName("ai")
   classVJ method8306(classVJ var1) {
      classVJ var2;
      if (var1 == null) {
         var2 = this.field4798.field6513;
      } else {
         var2 = var1;
      }

      if (var2 == this.field4798) {
         this.field4799 = null;
         return null;
      } else {
         this.field4799 = var2.field6513;
         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)Lvj;")
   @ObfuscatedName("ar")
   classVJ method8307(classVJ var1) {
      classVJ var2;
      if (var1 == null) {
         var2 = this.field4798.field6513;
      } else {
         var2 = var1;
      }

      if (var2 == this.field4798) {
         this.field4799 = null;
         return null;
      } else {
         this.field4799 = var2.field6513;
         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)Lvj;")
   @ObfuscatedName("aw")
   classVJ method8308(classVJ var1) {
      classVJ var2;
      if (var1 == null) {
         var2 = this.field4798.field6513;
      } else {
         var2 = var1;
      }

      if (var2 == this.field4798) {
         this.field4799 = null;
         return null;
      } else {
         this.field4799 = var2.field6513;
         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvj;")
   @ObfuscatedName("av")
   public classVJ method8313() {
      classVJ var1 = this.field4799;
      if (var1 == this.field4798) {
         this.field4799 = null;
         return null;
      } else {
         this.field4799 = var1.field6513;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvj;")
   @ObfuscatedName("ap")
   public classVJ method8305() {
      return classZM.method14116(this, null);
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)Lvj;")
   @ObfuscatedName("as")
   classVJ method8309(classVJ var1) {
      classVJ var2;
      if (var1 == null) {
         var2 = this.field4798.field6513;
      } else {
         var2 = var1;
      }

      if (var2 == this.field4798) {
         this.field4799 = null;
         return null;
      } else {
         this.field4799 = var2.field6513;
         return var2;
      }
   }
}
