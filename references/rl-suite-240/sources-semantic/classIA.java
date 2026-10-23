import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ia")
public class classIA extends classIS {
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final int field2921 = 65536;
   @ObfuscatedName("af")
   byte field2918;
   @ObfuscatedName("ae")
   int field2919;
   @ObfuscatedName("ab")
   String field2924;
   @ObfuscatedName("az")
   int field2917;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ag")
   public static IndexDataBase field2925;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2922 = 7;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field2920 = 28;
   @ToRemove(unused = "true")
   @ObfuscatedName("be")
   static final int field2923 = 63;

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod505(classXY var1) {
      this.field2917 = var1.method13099(1342024445) * -1326993401;
      this.field2918 = var1.method13097((byte)17);
      this.field2919 = var1.method13099(70727043) * 1810015857;
      classXY.method13115(var1, 1619290492);
      this.field2924 = var1.method13127(1395808511);
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod508(ClanChannel var1) {
      ClanChannelMember var2 = (ClanChannelMember)var1.field2953.get(1665855653 * this.field2917);
      var2.field2839 = this.field2918;
      var2.field2840 = this.field2919 * -1466320636;
      var2.field2844 = new classAAE(this.field2924);
   }

   @ObfuscatedSignature(descriptor = "(Lie;I)V")
   @ObfuscatedName("af")
   @Override
   void vmethod509(ClanChannel var1, int var2) {
      try {
         ClanChannelMember var3 = (ClanChannelMember)var1.field2953.get(1665855653 * this.field2917);
         var3.field2839 = this.field2918;
         var3.field2840 = this.field2919 * 1642452849;
         var3.field2844 = new classAAE(this.field2924);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ia.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod506(classXY var1) {
      this.field2917 = var1.method13099(683294502) * -848595155;
      this.field2918 = var1.method13097((byte)17);
      this.field2919 = var1.method13099(96279455) * -832897509;
      classXY.method13115(var1, 1275235733);
      this.field2924 = var1.method13127(1218420373);
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod510(ClanChannel var1) {
      ClanChannelMember var2 = (ClanChannelMember)var1.field2953.get(1665855653 * this.field2917);
      var2.field2839 = this.field2918;
      var2.field2840 = this.field2919 * 1642452849;
      var2.field2844 = new classAAE(this.field2924);
   }

   classIA(classIX var1) {
      this.this$0 = var1;
      this.field2917 = 848595155;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod507(classXY var1, int var2) {
      try {
         this.field2917 = var1.method13099(500121357) * -848595155;
         this.field2918 = var1.method13097((byte)17);
         this.field2919 = var1.method13099(-1680678979) * -832897509;
         classXY.method13115(var1, 829292351);
         this.field2924 = var1.method13127(-226046619);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ia.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod511(ClanChannel var1) {
      ClanChannelMember var2 = (ClanChannelMember)var1.field2953.get(209597842 * this.field2917);
      var2.field2839 = this.field2918;
      var2.field2840 = this.field2919 * 1642452849;
      var2.field2844 = new classAAE(this.field2924);
   }
}
