import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lu")
public class classLU implements classXZ {
   @ObfuscatedSignature(descriptor = "Llu;")
   @ObfuscatedName("ae")
   public static final classLU field4226 = new classLU(0, 2);
   @ObfuscatedSignature(descriptor = "Llu;")
   @ObfuscatedName("af")
   public static final classLU field4224 = new classLU(2, 1);
   @ObfuscatedName("as")
   final int field4227;
   @ObfuscatedSignature(descriptor = "Llu;")
   @ObfuscatedName("ab")
   public static final classLU field4225 = new classLU(1, 3);
   @ObfuscatedName("ag")
   public final int field4228;
   @ObfuscatedSignature(descriptor = "Llu;")
   @ObfuscatedName("az")
   public static final classLU field4223 = new classLU(3, 0);
   @ObfuscatedSignature(descriptor = "Lxo;")
   @ObfuscatedName("gn")
   static classXO field4230;
   @ToRemove(unused = "true")
   @ObfuscatedName("cg")
   public static final int field4229 = 0;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;Z)V")
   @ObfuscatedName("cg")
   public static void method7198(RenderOverview var0, boolean var1) {
      if (var0 == null) {
         var0.getWorldMapData();
      }

      var0.field6696 = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return 1891504423 * this.field4227;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return -1980609666 * this.field4227;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return 1891504423 * this.field4227;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lu.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return 1891504423 * this.field4227;
   }

   @ObfuscatedSignature(descriptor = "(IIFI)I")
   @ObfuscatedName("ax")
   public static int method7197(int var0, int var1, float var2, int var3) {
      try {
         return (int)(var2 * (var1 - var0)) + var0;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lu.ax(" + 41);
      }
   }

   classLU(int var1, int var2) {
      this.field4228 = 1730165009 * var1;
      this.field4227 = -1438232937 * var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ZI)V")
   @ObfuscatedName("bz")
   static final void method7199(String var0, boolean var1, int var2) {
      try {
         if (!client.field845) {
            if (var2 != -397716407) {
               ;
            }
         } else {
            byte var3 = 4;
            int var4 = 6 + var3;
            int var5 = 6 + var3;
            int var6 = classVP.field6548.method39(var0, 250);
            int var7 = classVP.field6548.method41(var0, 250) * 13;
            classYW.method13737(var4 - var3, var5 - var3, var3 + var3 + var6, var3 + var7 + var3, 0);
            classYW.method13746(var4 - var3, var5 - var3, var6 + var3 + var3, var3 + var7 + var3, 16777215);
            classAAO.method225(classVP.field6548, var0, var4, var5, var6, var7, 16777215, -1, 1, 1, 0);
            if (var1) {
               if (var2 == -397716407) {
                  throw new IllegalStateException();
               }

               classWO.field6761.vmethod578(0, 0, (byte)113);
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lu.bz(" + ')');
      }
   }
}
