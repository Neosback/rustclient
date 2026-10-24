import java.time.LocalDate;
import net.runelite.api.clan.ClanMember;
import net.runelite.api.clan.ClanRank;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("rl1")
public class rl1 implements ClanMember {
   @ObfuscatedSignature(descriptor = "Liq;")
   @ObfuscatedName("hm")
   public final ClanSettings field5687;
   @ObfuscatedName("tj")
   public final int field5688;

   @Override
   public int hashCode() {
      byte var1 = 59;
      int var2 = 1;
      String var3 = this.getName();
      var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
      ClanRank var4 = this.getRank();
      return var2 * 59 + (var4 == null ? 43 : var4.hashCode());
   }

   public ClanRank getRank() {
      return client.method2350(this.field5687.method6348()[this.field5688]);
   }

   public String getName() {
      return this.field5687.method6344()[this.field5688];
   }

   public LocalDate getJoinDate() {
      int var1 = this.field5687.field3037[this.field5688];
      return LocalDate.ofEpochDay(11745 + var1);
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof rl1)) {
         return false;
      } else {
         rl1 var2 = (rl1)var1;
         if (!var2.method10066(this)) {
            return false;
         } else {
            String var3 = this.getName();
            String var4 = var2.getName();
            if (var3 == null ? var4 == null : var3.equals(var4)) {
               ClanRank var5 = this.getRank();
               ClanRank var6 = var2.getRank();
               return var5 == null ? var6 == null : var5.equals(var6);
            } else {
               return false;
            }
         }
      }
   }

   @Override
   public String toString() {
      return "ClanMemberImpl(getName=" + this.getName() + ", getRank=" + this.getRank() + ")";
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("sp")
   public boolean method10066(Object var1) {
      return var1 instanceof rl1;
   }

   public rl1(ClanSettings var1, int var2) {
      this.field5687 = var1;
      this.field5688 = var2;
   }
}
