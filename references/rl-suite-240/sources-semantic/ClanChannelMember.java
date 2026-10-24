import net.runelite.api.clan.ClanRank;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hn")
public class ClanChannelMember implements net.runelite.api.clan.ClanChannelMember {
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field2842 = 2;
   @ObfuscatedName("az")
   public byte field2839;
   @ObfuscatedName("af")
   public int field2840;
   @ObfuscatedSignature(descriptor = "Laae;")
   @ObfuscatedName("ae")
   public classAAE field2844;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   public static final int field2841 = 1024;
   @ToRemove(unused = "true")
   @ObfuscatedName("bd")
   static final int field2843 = 124;

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("ix")
   public byte method6108() {
      return this.field2839;
   }

   @ObfuscatedSignature(descriptor = "(Lnet/runelite/api/Nameable;)I")
   @ObfuscatedName("eq")
   public int method6107(net.runelite.api.Nameable var1) {
      return this.getName().compareTo(var1.getName());
   }

   @ObfuscatedSignature(descriptor = "()Laae;")
   @ObfuscatedName("oc")
   public classAAE method6109() {
      return this.field2844;
   }

   public String getPrevName() {
      return null;
   }

   public int getWorld() {
      return this.field2840 * -906550845;
   }

   public String getName() {
      classAAE var1 = this.method6109();
      if (var1 == null) {
         return null;
      } else {
         String var2 = var1.method183();
         return var2 == null ? null : var2.replace(' ', ' ');
      }
   }

   public ClanRank getRank() {
      return client.method2350(this.method6108());
   }

   ClanChannelMember() {
   }
}
