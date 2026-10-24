import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xi")
class classXI implements Iterator {
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field6870 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final int field6869 = 28;
   @ObfuscatedName("az")
   int field6871;

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("aj")
   public Object method12851() {
      int var1 = (this.field6871 += -1778383927) * -345274759 - 1;
      classUF var2 = (classUF)classID.method6185(this.this$0.field6922, var1);
      return var2 != null ? var2 : classXR.method12936(this.this$0, var1, 147996753);
   }

   classXI(classXR var1) {
      this.this$0 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method12847() {
      return -345274759 * this.field6871 < this.this$0.method11776((byte)-57);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laaa;II)V")
   @ObfuscatedName("ik")
   public static void method12857(AbstractFont var0, int var1, int var2) {
      if (var0 == null) {
         var0.method95(var1, var1, var1, var1);
      }

      AbstractFont.field11 = -1;
      AbstractFont.field15 = -1;
      AbstractFont.field16 = var2;
      AbstractFont.field14 = var2;
      AbstractFont.field17 = var1;
      AbstractFont.field12 = var1;
      AbstractFont.field13 = 256;
      AbstractFont.field18 = 0;
      AbstractFont.field19 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxi;)Z")
   @ObfuscatedName("vo")
   public static boolean method12848(classXI var0) {
      return -345274759 * var0.field6871 < var0.this$0.method11776((byte)-44);
   }

   @Override
   public void remove() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xi.remove(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ao")
   public Object method12852() {
      int var1 = (this.field6871 += -1778383927) * -345274759 - 1;
      classUF var2 = (classUF)classID.method6185(this.this$0.field6922, var1);
      return var2 != null ? var2 : classXR.method12936(this.this$0, var1, -853134992);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("al")
   public Object method12853() {
      int var1 = (this.field6871 += -1778383927) * -434097062 - 1;
      classUF var2 = (classUF)classID.method6185(this.this$0.field6922, var1);
      return var2 != null ? var2 : classXR.method12936(this.this$0, var1, -1467959802);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxi;)V")
   @ObfuscatedName("gt")
   public static void method12855(classXI var0) {
      if (var0 == null) {
         var0.hasNext();
      }

      throw new UnsupportedOperationException();
   }

   @Override
   public boolean hasNext() {
      try {
         return -345274759 * this.field6871 < this.this$0.method11776((byte)-62);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xi.hasNext(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   public boolean method12849() {
      return -345274759 * this.field6871 < this.this$0.method11776((byte)-114);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ay")
   public Object method12854() {
      int var1 = (this.field6871 += -1778383927) * -345274759 - 1;
      classUF var2 = (classUF)classID.method6185(this.this$0.field6922, var1);
      return var2 != null ? var2 : classXR.method12936(this.this$0, var1, -528950311);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method12850() {
      return -345274759 * this.field6871 < this.this$0.method11776((byte)-46);
   }

   @Override
   public Object next() {
      try {
         int var1 = (this.field6871 += -1778383927) * -345274759 - 1;
         classUF var2 = (classUF)classID.method6185(this.this$0.field6922, var1);
         return var2 != null ? var2 : classXR.method12936(this.this$0, var1, -1359164034);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xi.next(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public void method12856() {
      throw new UnsupportedOperationException();
   }
}
