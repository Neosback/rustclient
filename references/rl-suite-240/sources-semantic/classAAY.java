import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("aay")
public class classAAY {
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   static final int field118 = 62;
   @ObfuscatedName("af")
   public int field116 = 0;
   @ObfuscatedName("ae")
   public int field115 = 0;
   @ObfuscatedName("az")
   public char field117;

   classAAY() {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsg;I)Luu;")
   @ObfuscatedName("kz")
   public static classUU method290(classSG var0, int var1) {
      return var0.field5923[var1];
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Lxs;I)V")
   @ObfuscatedName("eg")
   static void method291(WorldView var0, PacketBuffer var1, int var2) {
      client.method2385(var0, var1);

      try {
         WorldView var3 = classCQ.field1218;
         var3.field1706 = var1.method13099(-1596984619) * 182422557;
         var3.field1707 = var1.method13099(1871705287) * 771221349;
         int var4 = 1296729483 * var3.field1694 / 8;
         int var5 = -1269171107 * var3.field1690 / 8;
         client.field1007.method12893(var1, var4, var5, (byte)38);
         WidgetConfigNode.field4139 = new byte[client.field1007.method12876(-251280248)][];
         DynamicObject.field1682 = new byte[client.field1007.method12876(852620275)][];
         client.field838.method3039(var3, -545217909);
         classJB.method6386(25, -1192966987);
         client.field806 = true;
         classDA.field1364 = var3;
         classCB.method1402(var3, -1886918929);
         client.method2269(var0, var1);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "aay.eg(" + ')');
      }
   }
}
