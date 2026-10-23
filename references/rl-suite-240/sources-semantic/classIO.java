import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("io")
public class classIO extends classHB {
   @ObfuscatedName("ab")
   int field3004;
   @ObfuscatedName("af")
   int field3006;
   @ObfuscatedName("ae")
   int field3005;
   @ObfuscatedName("az")
   int field3008;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field3007 = 22;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laaa;Ljava/lang/String;II)Luq;")
   @ObfuscatedName("cx")
   public static classUQ method6271(AbstractFont var0, String var1, int var2, int var3) {
      if (var0 == null) {
         var0.method53(var1, var2, var2, var2, var2);
      }

      if (var1 != null && !var1.isEmpty()) {
         int var4 = var0.method33(var1);
         int var5 = var0.field9 + var0.field10;
         int var6 = var2 - var4 / 2;
         int var7 = var3 - var0.field9;
         return classPW.method9374(var6, var7, var4, var5, 1444081395);
      } else {
         return classPW.method9374(var2, var3, 0, 0, 1444081395);
      }
   }

   classIO(classIF var1) {
      this.this$0 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      this.field3008 = classXY.method13113(var1, (byte)1) * 642195729;
      this.field3004 = classXY.method13113(var1, (byte)1) * -902955675;
      this.field3006 = var1.method13095(-346779531) * 870058123;
      this.field3005 = var1.method13095(-346779531) * -991622745;
   }

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         var1.method6324(this.field3008 * -327421967, this.field3004 * 627764333, this.field3006 * -1511645405, -1835856361 * this.field3005, (short)16256);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "io.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lwm;")
   @ObfuscatedName("ag")
   static RenderOverview method6272(byte var0) {
      try {
         return client.field1124;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "io.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      this.field3008 = classXY.method13113(var1, (byte)1) * 642195729;
      this.field3004 = classXY.method13113(var1, (byte)1) * 1720158720;
      this.field3006 = var1.method13095(-346779531) * 870058123;
      this.field3005 = var1.method13095(-346779531) * -1083626788;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltw;ZZZ)Z")
   @ObfuscatedName("ey")
   public static boolean method6270(classTW var0, boolean var1, boolean var2, boolean var3) {
      return var0.method11231(var0.field6242 * -1207890233, var1, var2, var3, (byte)-109);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      var1.method6324(this.field3008 * -327421967, this.field3004 * 627764333, this.field3006 * -1511645405, -1835856361 * this.field3005, (short)16256);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      var1.method6324(this.field3008 * 544225221, this.field3004 * 627764333, this.field3006 * -1511645405, 974560067 * this.field3005, (short)16256);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      var1.method6324(this.field3008 * -327421967, this.field3004 * 627764333, this.field3006 * -1511645405, -1835856361 * this.field3005, (short)16256);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         this.field3008 = classXY.method13113(var1, (byte)1) * 642195729;
         this.field3004 = classXY.method13113(var1, (byte)1) * -902955675;
         this.field3006 = var1.method13095(-346779531) * 870058123;
         this.field3005 = var1.method13095(-346779531) * -991622745;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "io.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("gm")
   static final void method6273(byte var0) {
      try {
         classJL var1 = classEF.method3863(classJS.FRIENDCHAT_JOIN_LEAVE, client.field796.field1533, (byte)46);
         classXY.method13029(var1.field3341, 0, (byte)-115);
         client.field796.method3374(var1, -1301097035);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "io.gm(" + ')');
      }
   }
}
