import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl21")
public class rl21 {
   @ObfuscatedName("fv")
   public float field5728;
   @ObfuscatedSignature(descriptor = "[Lrl2;")
   @ObfuscatedName("qe")
   public rl2[] field5720 = new rl2[128];
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("ao")
   public classUU field5723;
   @ObfuscatedName("ln")
   public byte[] field5722;
   @ObfuscatedName("uj")
   public int field5732;
   @ObfuscatedName("cw")
   public float field5727;
   @ObfuscatedName("kp")
   public int field5730;
   @ObfuscatedSignature(descriptor = "Lrl21;")
   @ObfuscatedName("zj")
   public static final rl21 field5726 = new rl21(true);
   @ObfuscatedName("gh")
   public float field5729;
   @ObfuscatedSignature(descriptor = "Lfx;")
   @ObfuscatedName("eg")
   public Model field5721;
   @ObfuscatedName("sq")
   public int field5731;
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("jg")
   public classUU field5725;
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("tm")
   public classUU field5724;

   public rl21() {
      this.field5721 = new Model();
      this.field5722 = new byte[1];
      this.field5723 = new classUU();
      this.field5724 = new classUU();
      this.field5725 = new classUU();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lclient;)Z")
   @ObfuscatedName("zd")
   public static boolean method10100(client var0) {
      if (var0 == null) {
         var0.method2129();
      }

      return null != var0.field1103;
   }

   @ObfuscatedSignature(descriptor = "(Luu;I)V")
   @ObfuscatedName("eb")
   public void method10099(classUU var1, int var2) {
      if (var2 >= this.field5720.length) {
         this.field5720 = Arrays.copyOf(this.field5720, var2 + 1);
      }

      rl2 var3 = this.field5720[var2];
      if (var3 == null) {
         var3 = this.field5720[var2] = new rl2();
      }

      var3.field5714.method11720(var1);
      var3.field5717 = var3.field5718 = true;
   }

   public rl21(boolean var1) {
      this.field5721 = Model.field2445;
      this.field5722 = Model.field2470;
      this.field5723 = Model.field2473;
      this.field5724 = Model.field2458;
      this.field5725 = Model.field2475;
   }
}
