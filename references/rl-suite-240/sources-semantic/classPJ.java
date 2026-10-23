import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pj")
public class classPJ implements Iterator {
   @ObfuscatedName("ae")
   int field5268;
   @ObfuscatedName("af")
   int field5269 = 0;
   @ObfuscatedSignature(descriptor = "Lpz;")
   @ObfuscatedName("az")
   classPZ field5267;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final String field5271 = "random.dat";
   @ToRemove(unused = "true")
   @ObfuscatedName("bz")
   public static final int field5270 = 67;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   public void method8966() {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean hasNext() {
      try {
         return this.field5269 * -984169853 < 287626351 * this.field5267.field5495;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "pj.hasNext(" + ')');
      }
   }

   classPJ(classPZ var1) {
      this.field5268 = this.field5267.field5492 * 2038097811;
      this.field5267 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method8958() {
      return this.field5269 * -984169853 < 287626351 * this.field5267.field5495;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("al")
   public Object method8962() {
      if (-160205185 * this.field5268 != this.field5267.field5492 * 868197357) {
         throw new ConcurrentModificationException();
      } else if (this.field5269 * -984169853 < this.field5267.field5495 * 287626351) {
         Object var1 = this.field5267.field5493[this.field5269 * -984169853].field5222;
         this.field5269 += -376628181;
         return var1;
      } else {
         throw new NoSuchElementException();
      }
   }

   @Override
   public void remove() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "pj.remove(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ao")
   public Object method8963() {
      if (-1162884564 * this.field5268 != this.field5267.field5492 * 1405692877) {
         throw new ConcurrentModificationException();
      } else if (this.field5269 * -984169853 < this.field5267.field5495 * -206018461) {
         Object var1 = this.field5267.field5493[this.field5269 * 1770533645].field5222;
         this.field5269 += -258712084;
         return var1;
      } else {
         throw new NoSuchElementException();
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   public boolean method8959() {
      return this.field5269 * -984169853 < 287626351 * this.field5267.field5495;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   public boolean method8960() {
      return this.field5269 * -984169853 < 287626351 * this.field5267.field5495;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method8961() {
      return this.field5269 * -276240757 < 722093119 * this.field5267.field5495;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwb;)I")
   @ObfuscatedName("ne")
   public static int method8957(classWB var0) {
      if (var0 == null) {
         return var0.method12356();
      } else {
         int var1 = 0;
         if (var0.field6652 != null) {
            var1 += var0.field6652.hashCode();
         }

         if (var0.field6653 != null) {
            var1 += 1703952098 * var0.field6653.hashCode();
         }

         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpj;)Ljava/lang/Object;")
   @ObfuscatedName("be")
   public static Object method8964(classPJ var0) {
      if (-160205185 * var0.field5268 != var0.field5267.field5492 * 868197357) {
         throw new ConcurrentModificationException();
      } else if (var0.field5269 * -984169853 < var0.field5267.field5495 * 287626351) {
         Object var1 = var0.field5267.field5493[var0.field5269 * -984169853].field5222;
         var0.field5269 += -376628181;
         return var1;
      } else {
         throw new NoSuchElementException();
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ay")
   public Object method8965() {
      if (-160205185 * this.field5268 != this.field5267.field5492 * 868197357) {
         throw new ConcurrentModificationException();
      } else if (this.field5269 * -984169853 < this.field5267.field5495 * 287626351) {
         Object var1 = this.field5267.field5493[this.field5269 * -984169853].field5222;
         this.field5269 += -376628181;
         return var1;
      } else {
         throw new NoSuchElementException();
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public void method8967() {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object next() {
      try {
         if (-160205185 * this.field5268 != this.field5267.field5492 * 868197357) {
            throw new ConcurrentModificationException();
         } else if (this.field5269 * -984169853 < this.field5267.field5495 * 287626351) {
            Object var1 = this.field5267.field5493[this.field5269 * -984169853].field5222;
            this.field5269 += -376628181;
            return var1;
         } else {
            throw new NoSuchElementException();
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pj.next(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;IIB)V")
   @ObfuscatedName("bp")
   static void method8968(Object[] var0, int var1, int var2, byte var3) {
      try {
         while (var1 < var2) {
            if (var3 <= 1) {
               return;
            }

            Object var4 = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var4;
            var1++;
            var2--;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "pj.bp(" + ')');
      }
   }
}
