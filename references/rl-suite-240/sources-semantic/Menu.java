import java.util.Arrays;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.BeforeMenuRender;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wz")
public class Menu implements net.runelite.api.Menu {
   @ObfuscatedName("ad")
   int field6810;
   @ObfuscatedName("af")
   public int field6795;
   @ObfuscatedName("ae")
   public final int[] field6796;
   @ObfuscatedName("hn")
   public int field6793 = -1;
   @ObfuscatedName("ab")
   public final int[] field6797;
   @ObfuscatedName("ag")
   public final int[] field6798;
   @ObfuscatedName("as")
   public final int[] field6799;
   @ObfuscatedName("ax")
   public final int[] field6800;
   @ObfuscatedName("ac")
   public final int[] field6801;
   @ObfuscatedName("aa")
   public final String[] field6802;
   @ObfuscatedName("az")
   boolean field6808;
   @ObfuscatedName("ar")
   long field6817;
   @ObfuscatedName("aj")
   public final boolean[] field6805;
   @ObfuscatedName("ay")
   int field6815;
   @ObfuscatedName("nk")
   public int field6816;
   @ObfuscatedName("ao")
   public final String[] field6803;
   @ObfuscatedName("dw")
   public int field6794 = -1;
   @ObfuscatedName("au")
   int field6814;
   @ObfuscatedName("lr")
   public final int field6806;
   @ObfuscatedSignature(descriptor = "[Lrl5;")
   @ObfuscatedName("zb")
   public final rl5[] field6807;
   @ObfuscatedName("ap")
   int field6812;
   @ObfuscatedSignature(descriptor = "[Lwz;")
   @ObfuscatedName("al")
   public final Menu[] field6804;
   @ObfuscatedName("ai")
   int field6811;
   @ObfuscatedName("aq")
   int field6813;

   @ObfuscatedSignature(descriptor = "(Lwz;IIB)V")
   @ObfuscatedName("ru")
   public static void method12717(Menu var0, int var1, int var2, byte var3) {
      if (var0 == null) {
         var0.method12720(var1, var1, var3);
      }

      try {
         var0.method12765(-156515775);
         var0.field6815 = 602060077 * (var1 - 1308041153 * var0.field6810 / 2);
         if (var0.field6810 * 1308041153 + 727605925 * var0.field6815 > 773246731 * classSA.field5895) {
            if (var3 != 0) {
               return;
            }

            var0.field6815 = classSA.field5895 * -2095371281 - var0.field6810 * -649721363;
         }

         if (var0.field6815 * 727605925 < 0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            }

            var0.field6815 = 0;
         }

         var0.field6813 = var2 * -317857149;
         if (var0.field6813 * 1457978411 + var0.field6812 * -531540521 > IntProjection.field1936 * 8379747) {
            if (var3 != 0) {
               throw new IllegalStateException();
            }

            var0.field6813 = IntProjection.field1936 * 132558761 - -28198139 * var0.field6812;
         }

         if (1457978411 * var0.field6813 < 0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            }

            var0.field6813 = 0;
         }

         if (var0.field6814 * -1985221643 != -1) {
            if (var3 != 0) {
               return;
            }

            if (null != var0.field6804[var0.field6814 * -1985221643]) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               }

               var0.field6804[var0.field6814 * -1985221643].method12767(var0, (short)4036);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wz.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   public final void method12740() {
      this.method12744(-781508173);
   }

   @ObfuscatedSignature(descriptor = "(II)Ljava/lang/String;")
   @ObfuscatedName("az")
   public final String method12710(int var1, int var2) {
      try {
         if (var1 < 0) {
            if (var2 != -115655011) {
               throw new IllegalStateException();
            } else {
               return "";
            }
         } else if (!this.field6803[var1].isEmpty()) {
            if (var2 != -115655011) {
               throw new IllegalStateException();
            } else {
               return this.field6802[var1] + classKK.field3783 + this.field6803[var1];
            }
         } else {
            return this.field6802[var1];
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wz.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ap")
   public final String method12711(int var1) {
      if (var1 < 0) {
         return "";
      } else {
         return !this.field6803[var1].isEmpty() ? this.field6802[var1] + classKK.field3783 + this.field6803[var1] : this.field6802[var1];
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwz;S)V")
   @ObfuscatedName("ab")
   final void method12767(Menu var1, short var2) {
      this.method12760(var1);
      this.field6815 = (var1.field6810 * 1308041153 + var1.field6815 * 727605925) * 602060077;
      if (this.field6815 * 727605925 + this.field6810 * 1308041153 > classSA.field5895 * 773246731) {
         this.field6815 = (var1.field6815 * 727605925 - this.field6810 * 1308041153) * 602060077;
      }

      if (this.field6815 * 727605925 < 0) {
         this.field6815 = 0 * 602060077;
      }

      assert var1 == client.field975;

      this.field6813 = (var1.field6813 * 1457978411 + (var1.field6795 * -1927903443 - 1 - var1.field6814 * -1985221643 - client.field1087) * 15) * -317857149;
      if (this.field6812 * -531540521 + this.field6813 * 1457978411 > IntProjection.field1936 * 8379747) {
         this.field6813 = (IntProjection.field1936 * 8379747 - this.field6812 * -531540521) * -317857149;
      }

      if (this.field6813 * 1457978411 < 0) {
         this.field6813 = 0 * -317857149;
      }
   }

   public MenuEntry[] getMenuEntries() {
      return Arrays.copyOf(this.field6807, this.field6795 * -1927903443);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("as")
   public final void method12725(int var1, int var2) {
      try {
         if (var1 < 0) {
            if (var2 != -1111856393) {
               throw new IllegalStateException();
            }
         } else {
            classQP.method9746(
               this.field6796[var1],
               this.field6797[var1],
               this.field6798[var1],
               this.field6799[var1],
               this.field6800[var1],
               this.field6801[var1],
               this.field6802[var1],
               this.field6803[var1],
               classTZ.field6264 * 1422993977,
               1243590197 * classTZ.field6255,
               (byte)-44
            );
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wz.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("ax")
   public final boolean method12762(int var1, int var2, int var3) {
      boolean var4;
      if (this.field6814 * -1985221643 != -1
         && this.field6804[this.field6814 * -1985221643] != null
         && this.field6804[this.field6814 * -1985221643].method12770(var1, var2)) {
         var4 = true;
      } else if (var1 >= this.field6815 * 727605925 - 10
         && var1 <= this.field6810 * 1308041153 + this.field6815 * 727605925 + 10
         && var2 >= this.field6813 * 1457978411 - 10
         && var2 <= this.field6812 * -531540521 + this.field6813 * 1457978411 + 10) {
         int var5 = this.method12768(var1, var2);
         if (var5 != -1 && var5 != this.field6814 * -1985221643) {
            long var6 = classOE.field4841.getGameCycle();
            int var8 = var5;
            long var9 = Math.max(var6, this.field6817 * -7988158060854933767L);
            if (this.field6814 * -1985221643 != -1) {
               if (this.method12761(var1, var2)) {
                  var8 = -1;
                  var9 = Long.MAX_VALUE;
               } else if (this.field6811 * -392503493 == -1) {
                  if (var1 == this.field6793 && var2 == this.field6794) {
                     var9 = var6 + 8L;
                  } else {
                     var9 = var6 + 2L;
                  }
               }
            }

            this.field6811 = var8 * -164716045;
            this.field6817 = var9 * 8994289655695694665L;
            if (this.field6817 * -7988158060854933767L <= var6) {
               this.field6811 = -1 * -164716045;
               this.method12775();
               Menu var11 = this.field6804[var5];
               if (var11 != null) {
                  this.field6814 = var5 * -1982257059;
                  var11.method12776(this);
                  client.field1033 = 0;
                  client.field1013 = 0;
                  if (var11.field6812 * -531540521 > classOE.field4841.getCanvasHeight()) {
                     client.field1013 = 0;
                     client.field1033 = (var11.field6812 * -531540521 - classOE.field4841.getCanvasHeight() + 14) / 15;
                  }
               }
            }
         }

         var4 = true;
      } else {
         var4 = false;
      }

      this.field6793 = var1;
      this.field6794 = var2;
      return var4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;)V")
   @ObfuscatedName("ls")
   public static void method12743(Menu var0) {
      if (-1985221643 * var0.field6814 != -1) {
         if (null != var0.field6804[-1985221643 * var0.field6814]) {
            method12741(var0.field6804[var0.field6814 * -1985221643], -1932347280);
         }

         var0.field6814 = 1982257059;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   public final void method12737(int var1) {
      try {
         this.field6814 = 1982257059;

         for (int var2 = 0; var2 < this.field6795 * -1927903443; var2++) {
            if (null != this.field6804[var2]) {
               if (var1 != 921182066) {
                  throw new IllegalStateException();
               }

               this.field6804[var2].method12737(921182066);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wz.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   public final void method12749() {
      int var1 = this.field6815 * 727605925;
      int var2 = 1457978411 * this.field6813;
      int var3 = 6116423;
      classYW.method13737(var1, var2, this.field6810 * 1308041153, this.field6812 * -531540521, var3);
      if (this.field6808) {
         classYW.method13737(var1 + 1, var2 + 1, 1308041153 * this.field6810 - 2, 16, 0);
         classYW.method13746(1 + var1, 18 + var2, this.field6810 * 1308041153 - 2, this.field6812 * -531540521 - 19, 0);
         classBB.field341.method51(classKK.field3845, 3 + var1, 14 + var2, var3, -1);
      } else {
         classYW.method13746(var1 + 1, var2 + 1, this.field6810 * 1308041153 - 2, this.field6812 * -531540521 - 2, 0);
      }

      int var4 = classTZ.field6253 * -38255113;
      int var5 = -2144333897 * classTZ.field6262;

      for (int var6 = 0; var6 < -1927903443 * this.field6795; var6++) {
         int var7 = 14 + var2 + (this.field6795 * -1927903443 - 1 - var6) * 15;
         if (this.field6808) {
            var7 += 17;
         }

         int var8 = 16777215;
         if (var4 > var1 && var4 < var1 + 1308041153 * this.field6810 && var5 > var7 - 13 && var5 < 3 + var7) {
            var8 = 16776960;
         }

         if (var6 == this.field6814 * -1985221643 || -392503493 * this.field6811 == var6 && this.field6804[var6] != null) {
            classYW.method13737(2 + var1, var7 - 12, this.field6810 * 1308041153 - 4, 15, 7496785);
         }

         classBB.field341.method51(this.method12710(var6, -115655011), var1 + 3, var7, var8, 0);
         if (this.field6804[var6] != null) {
            AbstractFont.method94(classBB.field341, 62, 1308041153 * this.field6810 + var1 - 10, var7, 16777215);
         }
      }

      if (-1 != -1985221643 * this.field6814 && null != this.field6804[this.field6814 * -1985221643]) {
         this.field6804[this.field6814 * -1985221643].method12755(-2000686243);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwz;III)I")
   @ObfuscatedName("od")
   public static int method12763(Menu var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method12764(var1, var1, var1);
      }

      int var4 = var0 == client.field975 ? client.field1087 : client.field1013;

      for (int var5 = 0; var5 < var0.field6795 * -1927903443; var5++) {
         int var6 = var0.field6813 * 1457978411 + (var0.field6795 * -1927903443 - 1 - var5 - var4) * 15 + 14;
         if (var0.field6808) {
            var6 += 17;
         }

         if (var1 > var0.field6815 * 727605925 && var1 < var0.field6810 * 1308041153 + var0.field6815 * 727605925 && var2 > var6 - 13 && var2 < var6 + 3) {
            return var5;
         }
      }

      return -1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   public final void method12738() {
      this.field6814 = 1982257059;

      for (int var1 = 0; var1 < this.field6795 * -1927903443; var1++) {
         if (null != this.field6804[var1]) {
            this.field6804[var1].method12737(921182066);
         }
      }
   }

   public void removeMenuEntry(MenuEntry var1) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         rl5 var2 = (rl5)var1;
         int var3 = var2.field5800;
         if (var2.field5799 == this && var3 < this.field6795 * -1927903443) {
            assert var3 >= 0 && var3 < this.field6795 * -1927903443;

            for (int var4 = var3; var4 < this.field6795 * -1927903443 - 1; this.field6807[var4].field5800 = var4++) {
               this.field6802[var4] = this.field6802[var4 + 1];
               this.field6803[var4] = this.field6803[var4 + 1];
               this.field6799[var4] = this.field6799[var4 + 1];
               this.field6798[var4] = this.field6798[var4 + 1];
               this.field6796[var4] = this.field6796[var4 + 1];
               this.field6797[var4] = this.field6797[var4 + 1];
               this.field6800[var4] = this.field6800[var4 + 1];
               this.field6801[var4] = this.field6801[var4 + 1];
               this.field6805[var4] = this.field6805[var4 + 1];
               this.field6804[var4] = this.field6804[var4 + 1];
               this.field6807[var4] = this.field6807[var4 + 1];
            }

            var2.field5800 = this.field6795 * -1927903443 - 1;
            this.field6807[this.field6795 * -1927903443 - 1] = var2;
            this.field6795 = (this.field6795 * -1927903443 - 1) * 1932681381;
            this.field6816 = this.field6795 * -1927903443;
         } else {
            throw new IllegalArgumentException("entry doesn't belong to menu");
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ar")
   public final void method12718(int var1, int var2) {
      this.method12765(1407551530);
      this.field6815 = 602060077 * (var1 - 1913297146 * this.field6810 / 2);
      if (this.field6810 * -636172166 + 825361118 * this.field6815 > 773246731 * classSA.field5895) {
         this.field6815 = classSA.field5895 * -2095371281 - this.field6810 * -649721363;
      }

      if (this.field6815 * 727605925 < 0) {
         this.field6815 = 0;
      }

      this.field6813 = var2 * 484110040;
      if (this.field6813 * 1457978411 + this.field6812 * -531540521 > IntProjection.field1936 * 8379747) {
         this.field6813 = IntProjection.field1936 * -123479189 - -28198139 * this.field6812;
      }

      if (1457978411 * this.field6813 < 0) {
         this.field6813 = 0;
      }

      if (this.field6814 * -1985221643 != -1 && null != this.field6804[this.field6814 * -1985221643]) {
         this.field6804[this.field6814 * -796274021].method12767(this, (short)6966);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("mc")
   public void method12766() {
      this.field6817 = -1L * 8994289655695694665L;
   }

   @ObfuscatedSignature(descriptor = "(Lwz;I)V")
   @ObfuscatedName("gu")
   public static void method12741(Menu var0, int var1) {
      if (var0 == null) {
         var0.method12777(var1);
      }

      var0.method12766();

      try {
         var0.method12744(-781508173);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wz.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;IIIIIZII)I")
   @ObfuscatedName("aj")
   public final int method12747(String var1, String var2, int var3, int var4, int var5, int var6, int var7, boolean var8, int var9, int var10) {
      try {
         if (-1927903443 * this.field6795 < this.field6802.length) {
            if (var10 >= 657374242) {
               throw new IllegalStateException();
            } else {
               this.field6802[-1927903443 * this.field6795] = var1;
               this.field6803[this.field6795 * -1927903443] = var2;
               this.field6798[-1927903443 * this.field6795] = var3;
               this.field6799[-1927903443 * this.field6795] = var4;
               this.field6796[-1927903443 * this.field6795] = var5;
               this.field6797[this.field6795 * -1927903443] = var6;
               this.field6800[-1927903443 * this.field6795] = var7;
               this.field6801[this.field6795 * -1927903443] = var9;
               this.field6805[-1927903443 * this.field6795] = var8;
               this.field6804[this.field6795 * -1927903443] = null;
               int var10000 = this.field6795 += 1932681381;
               this.method12757(-1);
               return var10000 * -1927903443 - 1;
            }
         } else {
            return -1;
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "wz.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   final void method12744(int var1) {
      try {
         if (-1985221643 * this.field6814 != -1) {
            if (null != this.field6804[-1985221643 * this.field6814]) {
               method12741(this.field6804[this.field6814 * -1985221643], -1332660037);
            }

            this.field6814 = 1982257059;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wz.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;II)V")
   @ObfuscatedName("ow")
   public static void method12719(Menu var0, int var1, int var2) {
      if (var0 == null) {
         var0.method12754();
      }

      var0.method12765(-818090009);
      var0.field6815 = 1642600503 * (var1 - -2092263792 * var0.field6810 / 2);
      if (var0.field6810 * 1308041153 + 1666175372 * var0.field6815 > -717161267 * classSA.field5895) {
         var0.field6815 = classSA.field5895 * -2095371281 - var0.field6810 * -208644946;
      }

      if (var0.field6815 * -1117783628 < 0) {
         var0.field6815 = 0;
      }

      var0.field6813 = var2 * -361229084;
      if (var0.field6813 * -2010837480 + var0.field6812 * -531540521 > IntProjection.field1936 * 1769000792) {
         var0.field6813 = IntProjection.field1936 * -1549978419 - -28198139 * var0.field6812;
      }

      if (219920206 * var0.field6813 < 0) {
         var0.field6813 = 0;
      }

      if (var0.field6814 * 79511146 != -1 && null != var0.field6804[var0.field6814 * -1985221643]) {
         var0.field6804[var0.field6814 * 677406674].method12767(var0, (short)-21612);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;II)Z")
   @ObfuscatedName("dr")
   public static boolean method12730(Menu var0, int var1, int var2) {
      if (var0.field6814 * -1985221643 != -1
         && var0.field6804[-1985221643 * var0.field6814] != null
         && var0.field6804[var0.field6814 * -1985221643].method12762(var1, var2, -2098090181)) {
         return true;
      } else if (var1 >= 565096300 * var0.field6815 - 10
         && var1 <= 10 + 1308041153 * var0.field6810 + var0.field6815 * -744021948
         && var2 >= 313356009 * var0.field6813 - 10
         && var2 <= 10 + 1457978411 * var0.field6813 + var0.field6812 * -531540521) {
         int var3 = method12763(var0, var1, var2, 1189052319);
         if (var3 != -1 && var3 != -1985221643 * var0.field6814) {
            if (-1144516730 * var0.field6811 != var3) {
               var0.field6811 = var3 * -1734459145;
               var0.field6817 = classDD.method2988(16711680) * 8994289655695694665L;
               if (var0.field6814 * -1618947856 != -1) {
                  var0.field6817 += 5062261947113863564L;
               }
            }

            if (var0.field6817 * -7988158060854933767L <= classDD.method2988(16711680)) {
               var0.field6811 = 164716045;
               var0.method12744(-781508173);
               if (var0.field6804[var3] != null) {
                  var0.field6814 = var3 * -1982257059;
                  var0.field6804[var3].method12767(var0, (short)-13464);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("aq")
   public final String method12712(int var1) {
      if (var1 < 0) {
         return "";
      } else {
         return !this.field6803[var1].isEmpty() ? this.field6802[var1] + classKK.field3783 + this.field6803[var1] : this.field6802[var1];
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   void method12715() {
      this.field6810 = classBB.field341.method33(classKK.field3845) * 1702686785;

      for (int var1 = 0; var1 < -1927903443 * this.field6795; var1++) {
         int var2 = classBB.field341.method33(this.method12710(var1, -115655011));
         if (null != this.field6804[var1]) {
            var2 += 15;
         }

         if (var2 > this.field6810 * 1452049825) {
            this.field6810 = 1702686785 * var2;
         }
      }

      this.field6810 += 736592392;
      this.field6812 = 237352860 + this.field6795 * 175834901;
      if (this.field6808) {
         this.field6812 += 1068087870;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("gk")
   public int method12768(int var1, int var2) {
      return method12763(this, var1, var2, -521318322);
   }

   public int getMenuHeight() {
      return this.field6812 * -531540521;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("mu")
   public void method12769(int var1) {
      this.method12725(var1, -1111856393);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   void method12716() {
      this.field6810 = classBB.field341.method33(classKK.field3845) * 1702686785;

      for (int var1 = 0; var1 < -1927903443 * this.field6795; var1++) {
         int var2 = classBB.field341.method33(this.method12710(var1, -115655011));
         if (null != this.field6804[var1]) {
            var2 += 15;
         }

         if (var2 > this.field6810 * 1308041153) {
            this.field6810 = 1702686785 * var2;
         }
      }

      this.field6810 += 736592392;
      this.field6812 = 237352860 + this.field6795 * 175834901;
      if (this.field6808) {
         this.field6812 += 1068087870;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("be")
   public final void method12750() {
      int var1 = this.field6815 * 727605925;
      int var2 = 1457978411 * this.field6813;
      int var3 = 6116423;
      classYW.method13737(var1, var2, this.field6810 * 1308041153, this.field6812 * -531540521, var3);
      if (this.field6808) {
         classYW.method13737(var1 + 1, var2 + 1, 1308041153 * this.field6810 - 2, 16, 0);
         classYW.method13746(1 + var1, 18 + var2, this.field6810 * 1308041153 - 2, this.field6812 * -531540521 - 19, 0);
         classBB.field341.method51(classKK.field3845, 3 + var1, 14 + var2, var3, -1);
      } else {
         classYW.method13746(var1 + 1, var2 + 1, this.field6810 * 1308041153 - 2, this.field6812 * -531540521 - 2, 0);
      }

      int var4 = classTZ.field6253 * -38255113;
      int var5 = -2144333897 * classTZ.field6262;

      for (int var6 = 0; var6 < -1927903443 * this.field6795; var6++) {
         int var7 = 14 + var2 + (this.field6795 * -1927903443 - 1 - var6) * 15;
         if (this.field6808) {
            var7 += 17;
         }

         int var8 = 16777215;
         if (var4 > var1 && var4 < var1 + 1308041153 * this.field6810 && var5 > var7 - 13 && var5 < 3 + var7) {
            var8 = 16776960;
         }

         if (var6 == this.field6814 * -1985221643 || -392503493 * this.field6811 == var6 && this.field6804[var6] != null) {
            classYW.method13737(2 + var1, var7 - 12, this.field6810 * 1308041153 - 4, 15, 7496785);
         }

         classBB.field341.method51(this.method12710(var6, -115655011), var1 + 3, var7, var8, 0);
         if (this.field6804[var6] != null) {
            AbstractFont.method94(classBB.field341, 62, 1308041153 * this.field6810 + var1 - 10, var7, 16777215);
         }
      }

      if (-1 != -1985221643 * this.field6814 && null != this.field6804[this.field6814 * -1985221643]) {
         this.field6804[this.field6814 * -1985221643].method12755(-1262452597);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ak")
   public boolean method12770(int var1, int var2) {
      return this.method12762(var1, var2, -2034522084);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;IIIIIZI)I")
   @ObfuscatedName("bi")
   public final int method12748(String var1, String var2, int var3, int var4, int var5, int var6, int var7, boolean var8, int var9) {
      if (-1927903443 * this.field6795 < this.field6802.length) {
         this.field6802[-1927903443 * this.field6795] = var1;
         this.field6803[this.field6795 * 1243222294] = var2;
         this.field6798[-1927903443 * this.field6795] = var3;
         this.field6799[2018550998 * this.field6795] = var4;
         this.field6796[-1927903443 * this.field6795] = var5;
         this.field6797[this.field6795 * -1927903443] = var6;
         this.field6800[-1927903443 * this.field6795] = var7;
         this.field6801[this.field6795 * 481472702] = var9;
         this.field6805[938195248 * this.field6795] = var8;
         this.field6804[this.field6795 * 1044966345] = null;
         return (this.field6795 += 1141238489) * -197275653 - 1;
      } else {
         return -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ub")
   public void method12757(int var1) {
      int var2 = this.field6816;
      int var3 = this.field6795 * -1927903443;
      this.field6816 = var3;
      if (var3 < var2) {
         for (int var4 = var3; var4 < var2; var4++) {
            this.field6807[var4].field5801 = null;
         }
      } else if (var3 == var2 + 1) {
         rl5 var6 = this.field6807[var2];
         if (var6 == null) {
            var6 = this.field6807[var2] = new rl5(this, var2);
         } else {
            var6.field5801 = null;
         }

         MenuEntryAdded var5 = new MenuEntryAdded(var6);
         classOE.field4841.getCallbacks().post(var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwz;)V")
   @ObfuscatedName("av")
   final void method12721(Menu var1) {
      this.method12765(813742314);
      this.field6815 = var1.field6810 * -649721363 + var1.field6815 * 1;
      if (this.field6810 * 1308041153 + 727605925 * this.field6815 > 773246731 * classSA.field5895) {
         this.field6815 = 1 * var1.field6815 - this.field6810 * -649721363;
      }

      if (727605925 * this.field6815 < 0) {
         this.field6815 = 0;
      }

      int var2 = var1.field6795 * 1146219427 - 15 - var1.field6814 * 286446427 + var1.field6813 * 1457978411;
      if (var1.field6808) {
         var2 += 17;
      }

      int var3 = 19 + var2;
      this.field6813 = var2 * -317857149;
      if (this.field6813 * 1457978411 + this.field6812 * -531540521 > 8379747 * IntProjection.field1936) {
         this.field6813 = (var3 - this.field6812 * -531540521) * -317857149;
      }

      if (1457978411 * this.field6813 < 0) {
         this.field6813 = 0;
      }
   }

   public int getMenuX() {
      return this.field6815 * 727605925;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;)V")
   @ObfuscatedName("hp")
   public static void method12751(Menu var0) {
      int var1 = var0.field6815 * 727605925;
      int var2 = 1457978411 * var0.field6813;
      int var3 = 6116423;
      classYW.method13737(var1, var2, var0.field6810 * -1420652696, var0.field6812 * 767956428, var3);
      if (var0.field6808) {
         classYW.method13737(var1 + 1, var2 + 1, 1308041153 * var0.field6810 - 2, 16, 0);
         classYW.method13746(1 + var1, 18 + var2, var0.field6810 * 1308041153 - 2, var0.field6812 * -1220125631 - 19, 0);
         classBB.field341.method51(classKK.field3845, 3 + var1, 14 + var2, var3, -1);
      } else {
         classYW.method13746(var1 + 1, var2 + 1, var0.field6810 * 1308041153 - 2, var0.field6812 * -1364787884 - 2, 0);
      }

      int var4 = classTZ.field6253 * 613408966;
      int var5 = -1630179091 * classTZ.field6262;

      for (int var6 = 0; var6 < -1927903443 * var0.field6795; var6++) {
         int var7 = 14 + var2 + (var0.field6795 * -1927903443 - 1 - var6) * 15;
         if (var0.field6808) {
            var7 += 17;
         }

         int var8 = 16777215;
         if (var4 > var1 && var4 < var1 + 1308041153 * var0.field6810 && var5 > var7 - 13 && var5 < 3 + var7) {
            var8 = 1172532167;
         }

         if (var6 == var0.field6814 * -1607949428 || -2128790815 * var0.field6811 == var6 && var0.field6804[var6] != null) {
            classYW.method13737(2 + var1, var7 - 12, var0.field6810 * 1308041153 - 4, 15, -1172428688);
         }

         classBB.field341.method51(var0.method12710(var6, -115655011), var1 + 3, var7, var8, 0);
         if (var0.field6804[var6] != null) {
            AbstractFont.method94(classBB.field341, 62, 1643328402 * var0.field6810 + var1 - 10, var7, 16777215);
         }
      }

      if (-1 != -1985221643 * var0.field6814 && null != var0.field6804[var0.field6814 * -1954792566]) {
         var0.field6804[var0.field6814 * -1985221643].method12755(-1803682473);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;I)Ljava/lang/String;")
   @ObfuscatedName("wr")
   public static String method12713(Menu var0, int var1) {
      if (var0 == null) {
         var0.method12768(var1, var1);
      }

      if (var1 < 0) {
         return "";
      } else {
         return !var0.field6803[var1].isEmpty() ? var0.field6802[var1] + classKK.field3783 + var0.field6803[var1] : var0.field6802[var1];
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)F")
   @ObfuscatedName("wy")
   public static float method12756(int var0, int var1, int var2, int var3) {
      return ((float)var1 - var3) / (var2 - var0);
   }

   @ObfuscatedSignature(descriptor = "(Lwz;)V")
   @ObfuscatedName("at")
   final void method12722(Menu var1) {
      this.method12765(-910756639);
      this.field6815 = var1.field6810 * -649721363 + var1.field6815 * 1;
      if (this.field6810 * 1308041153 + 727605925 * this.field6815 > 773246731 * classSA.field5895) {
         this.field6815 = 1 * var1.field6815 - this.field6810 * -649721363;
      }

      if (727605925 * this.field6815 < 0) {
         this.field6815 = 0;
      }

      int var2 = var1.field6795 * 1146219427 - 15 - var1.field6814 * 286446427 + var1.field6813 * 1457978411;
      if (var1.field6808) {
         var2 += 17;
      }

      int var3 = 19 + var2;
      this.field6813 = var2 * -317857149;
      if (this.field6813 * 1457978411 + this.field6812 * -531540521 > 8379747 * IntProjection.field1936) {
         this.field6813 = (var3 - this.field6812 * -531540521) * -317857149;
      }

      if (1457978411 * this.field6813 < 0) {
         this.field6813 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("qm")
   public void method12771() {
      method12741(this, -45769207);
   }

   @ObfuscatedSignature(descriptor = "(I)Lrl5;")
   @ObfuscatedName("fd")
   public rl5 method12759(int var1) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else if (this.field6795 * -1927903443 >= this.field6806) {
         throw new IllegalStateException("Menu has exceeded the maximum number of entries (" + this.field6806 + ")");
      } else {
         if (var1 < 0) {
            var1 = this.field6795 * -1927903443 + var1 + 1;
            if (var1 < 0) {
               throw new IllegalArgumentException();
            }
         }

         rl5 var2;
         if (var1 < this.field6795 * -1927903443) {
            rl5 var3 = this.field6807[this.field6795 * -1927903443];
            if (var3 == null) {
               var3 = this.field6807[this.field6795 * -1927903443] = new rl5(this, this.field6795 * -1927903443);
            }

            for (int var4 = this.field6795 * -1927903443; var4 > var1; this.field6807[var4].field5800 = var4--) {
               this.field6802[var4] = this.field6802[var4 - 1];
               this.field6803[var4] = this.field6803[var4 - 1];
               this.field6799[var4] = this.field6799[var4 - 1];
               this.field6798[var4] = this.field6798[var4 - 1];
               this.field6796[var4] = this.field6796[var4 - 1];
               this.field6797[var4] = this.field6797[var4 - 1];
               this.field6800[var4] = this.field6800[var4 - 1];
               this.field6801[var4] = this.field6801[var4 - 1];
               this.field6805[var4] = this.field6805[var4 - 1];
               this.field6804[var4] = this.field6804[var4 - 1];
               this.field6807[var4] = this.field6807[var4 - 1];
            }

            this.field6795 = (this.field6795 * -1927903443 + 1) * 1932681381;
            this.field6816 = this.field6795 * -1927903443;
            var2 = var3;
            this.field6807[var1] = var3;
            var3.field5800 = var1;
         } else {
            if (var1 != this.field6795 * -1927903443) {
               throw new IllegalArgumentException();
            }

            var2 = this.field6807[this.field6795 * -1927903443];
            if (var2 == null) {
               var2 = this.field6807[this.field6795 * -1927903443] = new rl5(this, this.field6795 * -1927903443);
            }

            this.field6795 = (this.field6795 * -1927903443 + 1) * 1932681381;
            this.field6816 = this.field6795 * -1927903443;
         }

         this.field6802[var2.field5800] = "";
         this.field6803[var2.field5800] = "";
         this.field6798[var2.field5800] = MenuAction.RUNELITE.getId();
         this.field6799[var2.field5800] = 0;
         this.field6796[var2.field5800] = 0;
         this.field6797[var2.field5800] = 0;
         this.field6800[var2.field5800] = -1;
         this.field6801[var2.field5800] = 0;
         this.field6804[var2.field5800] = null;
         var2.field5801 = null;
         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("pn")
   public void method12772(int var1, int var2) {
      this.method12733(var1, var2, (short)-8747);
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("am")
   final int method12723(int var1, int var2) {
      for (int var3 = 0; var3 < -1927903443 * this.field6795; var3++) {
         int var4 = 15 * (this.field6795 * -1927903443 - 1 - var3) + 14 + this.field6813 * 1457978411;
         if (this.field6808) {
            var4 += 17;
         }

         if (var1 > 727605925 * this.field6815 && var1 < 1308041153 * this.field6810 + 727605925 * this.field6815 && var2 > var4 - 13 && var2 < var4 + 3) {
            return var3;
         }
      }

      return -1;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("fs")
   public boolean method12761(int var1, int var2) {
      if (this.field6814 * -1985221643 == -1) {
         return false;
      } else {
         Menu var3 = this.field6804[this.field6814 * -1985221643];
         if (var3 == null) {
            return false;
         } else if (var3.field6815 * 727605925 > this.field6815 * 727605925) {
            int var9 = var3.field6815 * 727605925;
            float var10 = method12756(var1, var2, var9, var3.field6813 * 1457978411);
            float var11 = method12756(this.field6793, this.field6794, var9, var3.field6813 * 1457978411);
            float var12 = method12756(var1, var2, var9, var3.field6813 * 1457978411 + var3.field6812 * -531540521);
            float var13 = method12756(this.field6793, this.field6794, var9, var3.field6813 * 1457978411 + var3.field6812 * -531540521);
            return var10 >= var11 && var12 < var13 || var10 > var11 && var12 <= var13;
         } else {
            int var4 = this.field6815 * 727605925;
            float var5 = method12756(var1, var2, var4, var3.field6813 * 1457978411);
            float var6 = method12756(this.field6793, this.field6794, var4, var3.field6813 * 1457978411);
            float var7 = method12756(var1, var2, var4, var3.field6813 * 1457978411 + var3.field6812 * -531540521);
            float var8 = method12756(this.field6793, this.field6794, var4, var3.field6813 * 1457978411 + var3.field6812 * -531540521);
            return var5 <= var6 && var7 > var8 || var5 < var6 && var7 >= var8;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ay")
   public final void method12726(int var1) {
      if (var1 >= 0) {
         classQP.method9746(
            this.field6796[var1],
            this.field6797[var1],
            this.field6798[var1],
            this.field6799[var1],
            this.field6800[var1],
            this.field6801[var1],
            this.field6802[var1],
            this.field6803[var1],
            classTZ.field6264 * 1422993977,
            -1782692480 * classTZ.field6255,
            (byte)-54
         );
      }
   }

   @ObfuscatedSignature(descriptor = "(IIS)Z")
   @ObfuscatedName("ac")
   public final boolean method12733(int var1, int var2, short var3) {
      try {
         if (-1 != -1985221643 * this.field6814) {
            if (var3 == 765) {
               throw new IllegalStateException();
            }

            if (null != this.field6804[-1985221643 * this.field6814]) {
               if (var3 == 765) {
                  throw new IllegalStateException();
               }

               if (this.field6804[-1985221643 * this.field6814].method12733(var1, var2, (short)-13951)) {
                  if (var3 == 765) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         int var4 = method12763(this, var1, var2, -18870212);
         if (-1 != var4) {
            if (var3 == 765) {
               throw new IllegalStateException();
            } else {
               this.method12725(var4, -1111856393);
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "wz.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("bm")
   public final boolean method12731(int var1, int var2) {
      if (this.field6814 * 1256246264 != -1
         && this.field6804[1011150737 * this.field6814] != null
         && this.field6804[this.field6814 * -1985221643].method12762(var1, var2, -1655548224)) {
         return true;
      } else if (var1 >= -1997806456 * this.field6815 - 10
         && var1 <= 10 + 1560970217 * this.field6810 + this.field6815 * 727605925
         && var2 >= 1457978411 * this.field6813 - 10
         && var2 <= 10 + -1125919218 * this.field6813 + this.field6812 * -531540521) {
         int var3 = method12763(this, var1, var2, -653432933);
         if (var3 != -1 && var3 != -1018693876 * this.field6814) {
            if (-392503493 * this.field6811 != var3) {
               this.field6811 = var3 * 1320938569;
               this.field6817 = classDD.method2988(16711680) * 8994289655695694665L;
               if (this.field6814 * 2039411425 != -1) {
                  this.field6817 += 5062261947113863564L;
               }
            }

            if (this.field6817 * -7988158060854933767L <= classDD.method2988(16711680)) {
               this.field6811 = 164716045;
               this.method12744(-781508173);
               if (this.field6804[var3] != null) {
                  this.field6814 = var3 * -1982257059;
                  this.field6804[var3].method12767(this, (short)11042);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void setMenuEntries(MenuEntry[] var1) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         boolean var2 = false;
         if (classON.field4991 != null && this.field6795 * -1927903443 > 0) {
            var2 = classON.field4991.method1368() == this.field6798[this.field6795 * -1927903443 - 1]
               && classON.field4991.method1374() == this.field6799[this.field6795 * -1927903443 - 1]
               && classON.field4991.method1369() == this.field6802[this.field6795 * -1927903443 - 1]
               && classON.field4991.method1362() == this.field6803[this.field6795 * -1927903443 - 1]
               && classON.field4991.method1375() == this.field6796[this.field6795 * -1927903443 - 1]
               && classON.field4991.method1373() == this.field6797[this.field6795 * -1927903443 - 1]
               && classON.field4991.method1360() == this.field6800[this.field6795 * -1927903443 - 1]
               && classON.field4991.method1370() == this.field6801[this.field6795 * -1927903443 - 1];
         }

         for (int var3 = 0; var3 < var1.length; var3++) {
            rl5 var4 = (rl5)var1[var3];
            if (var4.field5800 != var3) {
               if (var4.field5799 != this) {
                  throw new IllegalArgumentException("menu entry at index " + var3 + " doesn't belong to this menu");
               }

               if (var4.field5800 <= var3) {
                  throw new IllegalArgumentException("menu entry out of order (are you passing the same menu twice?)");
               }

               this.method12758(var4.field5800, var3);
            }
         }

         this.field6795 = var1.length * 1932681381;
         this.field6816 = var1.length;
         if (var2 && this.field6795 * -1927903443 > 0) {
            assert classON.field4991 != null;

            classON.field4991.method1367(this.field6798[this.field6795 * -1927903443 - 1]);
            classON.field4991.method1366(this.field6799[this.field6795 * -1927903443 - 1]);
            classON.field4991.method1361(this.field6802[this.field6795 * -1927903443 - 1]);
            classON.field4991.method1372(this.field6803[this.field6795 * -1927903443 - 1]);
            classON.field4991.method1364(this.field6796[this.field6795 * -1927903443 - 1]);
            classON.field4991.method1371(this.field6797[this.field6795 * -1927903443 - 1]);
            classON.field4991.method1363(this.field6800[this.field6795 * -1927903443 - 1]);
            classON.field4991.method1365(this.field6801[this.field6795 * -1927903443 - 1]);
            classON.field4991.field617 = this.field6807[this.field6795 * -1927903443 - 1].field5801;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("bt")
   public final boolean method12734(int var1, int var2) {
      if (-1 != -1985221643 * this.field6814
         && null != this.field6804[-1985221643 * this.field6814]
         && this.field6804[-1985221643 * this.field6814].method12733(var1, var2, (short)14810)) {
         return true;
      } else {
         int var3 = method12763(this, var1, var2, -1069010009);
         if (-1 != var3) {
            this.method12725(var3, -1111856393);
            return true;
         } else {
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("bg")
   public final boolean method12735(int var1, int var2) {
      if (-1 != -1985221643 * this.field6814
         && null != this.field6804[683048908 * this.field6814]
         && this.field6804[-1985221643 * this.field6814].method12733(var1, var2, (short)-13089)) {
         return true;
      } else {
         int var3 = method12763(this, var1, var2, 163313233);
         if (-1 != var3) {
            this.method12725(var3, -1111856393);
            return true;
         } else {
            return false;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;)V")
   @ObfuscatedName("cs")
   public static void method12739(Menu var0) {
      if (var0 == null) {
         var0.method12746();
      }

      var0.field6814 = 1982257059;

      for (int var1 = 0; var1 < var0.field6795 * -1927903443; var1++) {
         if (null != var0.field6804[var1]) {
            var0.field6804[var1].method12737(921182066);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("nv")
   public void method12773(int var1, int var2) {
      method12717(this, var1, var2, (byte)0);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("bj")
   public final boolean method12736(int var1, int var2) {
      if (-1 != -1985221643 * this.field6814
         && null != this.field6804[-1985221643 * this.field6814]
         && this.field6804[-1985221643 * this.field6814].method12733(var1, var2, (short)16742)) {
         return true;
      } else {
         int var3 = method12763(this, var1, var2, -1267883019);
         if (-1 != var3) {
            this.method12725(var3, -1111856393);
            return true;
         } else {
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("sd")
   public void method12774() {
      this.method12737(921182066);
   }

   public int getMenuWidth() {
      return this.field6810 * 1308041153;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ah")
   public final void method12727(int var1) {
      if (var1 >= 0) {
         classQP.method9746(
            this.field6796[var1],
            this.field6797[var1],
            this.field6798[var1],
            this.field6799[var1],
            this.field6800[var1],
            this.field6801[var1],
            this.field6802[var1],
            this.field6803[var1],
            classTZ.field6264 * 1422993977,
            1243590197 * classTZ.field6255,
            (byte)-92
         );
      }
   }

   public int getMenuY() {
      return this.field6813 * 1457978411;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("xy")
   public void method12758(int var1, int var2) {
      assert var1 != var2;

      String var3 = this.field6802[var1];
      this.field6802[var1] = this.field6802[var2];
      this.field6802[var2] = var3;
      var3 = this.field6803[var1];
      this.field6803[var1] = this.field6803[var2];
      this.field6803[var2] = var3;
      int var4 = this.field6799[var1];
      this.field6799[var1] = this.field6799[var2];
      this.field6799[var2] = var4;
      var4 = this.field6798[var1];
      this.field6798[var1] = this.field6798[var2];
      this.field6798[var2] = var4;
      var4 = this.field6796[var1];
      this.field6796[var1] = this.field6796[var2];
      this.field6796[var2] = var4;
      var4 = this.field6797[var1];
      this.field6797[var1] = this.field6797[var2];
      this.field6797[var2] = var4;
      var4 = this.field6800[var1];
      this.field6800[var1] = this.field6800[var2];
      this.field6800[var2] = var4;
      var4 = this.field6801[var1];
      this.field6801[var1] = this.field6801[var2];
      this.field6801[var2] = var4;
      boolean var5 = this.field6805[var1];
      this.field6805[var1] = this.field6805[var2];
      this.field6805[var2] = var5;
      Menu var6 = this.field6804[var1];
      this.field6804[var1] = this.field6804[var2];
      this.field6804[var2] = var6;

      assert this.field6807[var1].field5799 == this;

      assert this.field6807[var2].field5799 == this;

      assert this.field6807[var1].field5800 == var1;

      assert this.field6807[var2].field5800 == var2;

      rl5 var7 = this.field6807[var1];
      this.field6807[var1] = this.field6807[var2];
      this.field6807[var2] = var7;
      this.field6807[var1].field5800 = var1;
      this.field6807[var2].field5800 = var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   public final void method12752() {
      int var1 = this.field6815 * 727605925;
      int var2 = 1457978411 * this.field6813;
      int var3 = 6116423;
      classYW.method13737(var1, var2, this.field6810 * 1308041153, this.field6812 * -531540521, var3);
      if (this.field6808) {
         classYW.method13737(var1 + 1, var2 + 1, 1308041153 * this.field6810 - 2, 16, 0);
         classYW.method13746(1 + var1, 18 + var2, this.field6810 * 1308041153 - 2, this.field6812 * -531540521 - 19, 0);
         classBB.field341.method51(classKK.field3845, 3 + var1, 14 + var2, var3, -1);
      } else {
         classYW.method13746(var1 + 1, var2 + 1, this.field6810 * 1308041153 - 2, this.field6812 * -531540521 - 2, 0);
      }

      int var4 = classTZ.field6253 * -38255113;
      int var5 = -2144333897 * classTZ.field6262;

      for (int var6 = 0; var6 < -1927903443 * this.field6795; var6++) {
         int var7 = 14 + var2 + (this.field6795 * -1927903443 - 1 - var6) * 15;
         if (this.field6808) {
            var7 += 17;
         }

         int var8 = 16777215;
         if (var4 > var1 && var4 < var1 + 1308041153 * this.field6810 && var5 > var7 - 13 && var5 < 3 + var7) {
            var8 = 16776960;
         }

         if (var6 == this.field6814 * -1985221643 || -392503493 * this.field6811 == var6 && this.field6804[var6] != null) {
            classYW.method13737(2 + var1, var7 - 12, this.field6810 * 1308041153 - 4, 15, 7496785);
         }

         classBB.field341.method51(this.method12710(var6, -115655011), var1 + 3, var7, var8, 0);
         if (this.field6804[var6] != null) {
            AbstractFont.method94(classBB.field341, 62, 1308041153 * this.field6810 + var1 - 10, var7, 16777215);
         }
      }

      if (-1 != -1985221643 * this.field6814 && null != this.field6804[this.field6814 * -1985221643]) {
         this.field6804[this.field6814 * -1985221643].method12755(-1765428614);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;)V")
   @ObfuscatedName("ci")
   public static void method12753(Menu var0) {
      int var1 = var0.field6815 * 727605925;
      int var2 = 1457978411 * var0.field6813;
      int var3 = 6116423;
      classYW.method13737(var1, var2, var0.field6810 * 1308041153, var0.field6812 * -531540521, var3);
      if (var0.field6808) {
         classYW.method13737(var1 + 1, var2 + 1, 1308041153 * var0.field6810 - 2, 16, 0);
         classYW.method13746(1 + var1, 18 + var2, var0.field6810 * 1308041153 - 2, var0.field6812 * -531540521 - 19, 0);
         classBB.field341.method51(classKK.field3845, 3 + var1, 14 + var2, var3, -1);
      } else {
         classYW.method13746(var1 + 1, var2 + 1, var0.field6810 * 1308041153 - 2, var0.field6812 * -531540521 - 2, 0);
      }

      int var4 = classTZ.field6253 * -38255113;
      int var5 = -2144333897 * classTZ.field6262;

      for (int var6 = 0; var6 < -1927903443 * var0.field6795; var6++) {
         int var7 = 14 + var2 + (var0.field6795 * -1927903443 - 1 - var6) * 15;
         if (var0.field6808) {
            var7 += 17;
         }

         int var8 = 16777215;
         if (var4 > var1 && var4 < var1 + 1308041153 * var0.field6810 && var5 > var7 - 13 && var5 < 3 + var7) {
            var8 = 16776960;
         }

         if (var6 == var0.field6814 * -1985221643 || -392503493 * var0.field6811 == var6 && var0.field6804[var6] != null) {
            classYW.method13737(2 + var1, var7 - 12, var0.field6810 * 1308041153 - 4, 15, 7496785);
         }

         classBB.field341.method51(var0.method12710(var6, -115655011), var1 + 3, var7, var8, 0);
         if (var0.field6804[var6] != null) {
            AbstractFont.method94(classBB.field341, 62, 1308041153 * var0.field6810 + var1 - 10, var7, 16777215);
         }
      }

      if (-1 != -1985221643 * var0.field6814 && null != var0.field6804[var0.field6814 * -1985221643]) {
         var0.field6804[var0.field6814 * -1985221643].method12755(-1894339153);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bx")
   public final void method12728(int var1) {
      if (var1 >= 0) {
         classQP.method9746(
            this.field6796[var1],
            this.field6797[var1],
            this.field6798[var1],
            this.field6799[var1],
            this.field6800[var1],
            this.field6801[var1],
            this.field6802[var1],
            this.field6803[var1],
            classTZ.field6264 * 1422993977,
            1243590197 * classTZ.field6255,
            (byte)-75
         );
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ba")
   public final void method12742() {
      this.method12744(-781508173);
   }

   @ObfuscatedSignature(descriptor = "(Lwz;)V")
   @ObfuscatedName("gm")
   public void method12760(Menu var1) {
      int var2;
      if (var1 == null) {
         var2 = classBB.field341.getTextWidth("Choose Option");
      } else {
         var2 = classBB.field341.getTextWidth(var1.field6803[var1.field6814 * -1985221643]);
      }

      for (int var3 = 0; var3 < this.field6795 * -1927903443; var3++) {
         String var4 = this.field6802[var3];
         if (!this.field6803[var3].isEmpty()) {
            var4 = var4 + " " + this.field6803[var3];
         }

         if (this.field6804[var3] != null) {
            var4 = var4 + " <gt>";
         }

         int var5 = classBB.field341.getTextWidth(var4);
         if (var5 > var2) {
            var2 = var5;
         }
      }

      var2 += 8;
      int var7 = this.field6795 * -1927903443 * 15 + 4;
      if (this.field6808) {
         var7 += 18;
      }

      this.field6810 = var2 * 1702686785;
      this.field6812 = var7 * -2088145433;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bb")
   final void method12745() {
      if (-141318718 * this.field6814 != -1) {
         if (null != this.field6804[2032838106 * this.field6814]) {
            method12741(this.field6804[this.field6814 * -1985221643], 37541411);
         }

         this.field6814 = 1982257059;
      }
   }

   public Menu(boolean var1) {
      int var2 = var1 ? 500 : 64;
      this.field6795 = 0 * 1932681381;
      this.field6796 = new int[var2];
      this.field6797 = new int[var2];
      this.field6798 = new int[var2];
      this.field6799 = new int[var2];
      this.field6800 = new int[var2];
      this.field6801 = new int[var2];
      this.field6802 = new String[var2];
      this.field6803 = new String[var2];
      this.field6804 = new Menu[var2];
      this.field6805 = new boolean[var2];
      this.field6806 = var2;
      this.field6807 = new rl5[var2];
      this.field6808 = true;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;II)I")
   @ObfuscatedName("uv")
   public static int method12724(Menu var0, int var1, int var2) {
      if (var0 == null) {
         var0.method12728(var1);
      }

      for (int var3 = 0; var3 < -1927903443 * var0.field6795; var3++) {
         int var4 = 15 * (var0.field6795 * -1927903443 - 1 - var3) + 14 + var0.field6813 * 1457978411;
         if (var0.field6808) {
            var4 += 17;
         }

         if (var1 > 727605925 * var0.field6815 && var1 < 1308041153 * var0.field6810 + 727605925 * var0.field6815 && var2 > var4 - 13 && var2 < var4 + 3) {
            return var3;
         }
      }

      return -1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   public final void method12755(int var1) {
      BeforeMenuRender var2 = new BeforeMenuRender();
      classOE.field4841.getCallbacks().post(var2);
      if (!var2.isConsumed()) {
         client.method2237(this);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   public final void method12754() {
      int var1 = this.field6815 * 727605925;
      int var2 = 1457978411 * this.field6813;
      int var3 = 6116423;
      classYW.method13737(var1, var2, this.field6810 * 1308041153, this.field6812 * -531540521, var3);
      if (this.field6808) {
         classYW.method13737(var1 + 1, var2 + 1, 1308041153 * this.field6810 - 2, 16, 0);
         classYW.method13746(1 + var1, 18 + var2, this.field6810 * 1308041153 - 2, this.field6812 * -531540521 - 19, 0);
         classBB.field341.method51(classKK.field3845, 3 + var1, 14 + var2, var3, -1);
      } else {
         classYW.method13746(var1 + 1, var2 + 1, this.field6810 * 1308041153 - 2, this.field6812 * -531540521 - 2, 0);
      }

      int var4 = classTZ.field6253 * -38255113;
      int var5 = -2144333897 * classTZ.field6262;

      for (int var6 = 0; var6 < -1927903443 * this.field6795; var6++) {
         int var7 = 14 + var2 + (this.field6795 * -1927903443 - 1 - var6) * 15;
         if (this.field6808) {
            var7 += 17;
         }

         int var8 = 16777215;
         if (var4 > var1 && var4 < var1 + 1308041153 * this.field6810 && var5 > var7 - 13 && var5 < 3 + var7) {
            var8 = 16776960;
         }

         if (var6 == this.field6814 * -1985221643 || -392503493 * this.field6811 == var6 && this.field6804[var6] != null) {
            classYW.method13737(2 + var1, var7 - 12, this.field6810 * 1308041153 - 4, 15, 7496785);
         }

         classBB.field341.method51(this.method12710(var6, -115655011), var1 + 3, var7, var8, 0);
         if (this.field6804[var6] != null) {
            AbstractFont.method94(classBB.field341, 62, 1308041153 * this.field6810 + var1 - 10, var7, 16777215);
         }
      }

      if (-1 != -1985221643 * this.field6814 && null != this.field6804[this.field6814 * -1985221643]) {
         this.field6804[this.field6814 * -1985221643].method12755(-1689280628);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("rj")
   public void method12775() {
      this.method12744(-781508173);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;II)Z")
   @ObfuscatedName("up")
   public static boolean method12732(Menu var0, int var1, int var2) {
      if (var0 == null) {
         var0.method12765(var1);
      }

      if (var0.field6814 * 1251479852 != -1
         && var0.field6804[-1985221643 * var0.field6814] != null
         && var0.field6804[var0.field6814 * -1985221643].method12762(var1, var2, -1553941953)) {
         return true;
      } else if (var1 >= -1250270610 * var0.field6815 - 10
         && var1 <= 10 + 215304835 * var0.field6810 + var0.field6815 * -548447980
         && var2 >= 1457978411 * var0.field6813 - 10
         && var2 <= 10 + 1457978411 * var0.field6813 + var0.field6812 * -531540521) {
         int var3 = method12763(var0, var1, var2, -149630139);
         if (var3 != -1 && var3 != -1664095241 * var0.field6814) {
            if (-392503493 * var0.field6811 != var3) {
               var0.field6811 = var3 * 1098245094;
               var0.field6817 = classDD.method2988(16711680) * 8994289655695694665L;
               if (var0.field6814 * -1985221643 != -1) {
                  var0.field6817 += 5062261947113863564L;
               }
            }

            if (var0.field6817 * -7988158060854933767L <= classDD.method2988(16711680)) {
               var0.field6811 = 164716045;
               var0.method12744(-781508173);
               if (var0.field6804[var3] != null) {
                  var0.field6814 = var3 * -1982257059;
                  var0.field6804[var3].method12767(var0, (short)-8110);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwz;)V")
   @ObfuscatedName("is")
   public void method12776(Menu var1) {
      this.method12767(var1, (short)-1785);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   void method12765(int var1) {
      this.method12760(null);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Ljava/lang/String;I)I")
   @ObfuscatedName("af")
   public static int method12709(classXY var0, String var1, int var2) {
      try {
         int var3 = var0.field6953 * 702114061;
         byte[] var4 = classVX.method12333(var1, -1925400387);
         var0.method13084(var4.length, -1454653314);
         var0.field6953 = var0.field6953
            + classLF.field4131.method7190(var4, 0, var4.length, var0.field6952, var0.field6953 * 702114061, -962292593) * -1095856699;
         return var0.field6953 * 702114061 - var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "wz.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("ab")
   static void method12714(int var0, int var1, int var2, int var3, int var4) {
      try {
         ItemContainer var5 = (ItemContainer)HashTable.method13432(ItemContainer.field454, var0);
         if (var5 == null) {
            var5 = new ItemContainer();
            ItemContainer.field454.method13418(var5, var0);
         }

         if (var5.field456.length <= var1) {
            if (var4 == 1575592240) {
               throw new IllegalStateException();
            }

            int[] var6 = new int[var1 + 1];
            int[] var7 = new int[1 + var1];

            for (int var8 = 0; var8 < var5.field456.length; var8++) {
               var6[var8] = var5.field456[var8];
               var7[var8] = var5.field455[var8];
            }

            for (int var10 = var5.field456.length; var10 < var1; var10++) {
               if (var4 == 1575592240) {
                  throw new IllegalStateException();
               }

               var6[var10] = -1;
               var7[var10] = 0;
            }

            var5.field456 = var6;
            var5.field455 = var7;
         }

         var5.field456[var1] = var2;
         var5.field455[var1] = var3;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "wz.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;I)V")
   @ObfuscatedName("br")
   public static void method12729(Menu var0, int var1) {
      if (var0 == null) {
         var0.method12772(var1, var1);
      }

      if (var1 >= 0) {
         classQP.method9746(
            var0.field6796[var1],
            var0.field6797[var1],
            var0.field6798[var1],
            var0.field6799[var1],
            var0.field6800[var1],
            var0.field6801[var1],
            var0.field6802[var1],
            var0.field6803[var1],
            classTZ.field6264 * 1422993977,
            1243590197 * classTZ.field6255,
            (byte)-41
         );
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("ae")
   public final void method12720(int var1, int var2, byte var3) {
      try {
         this.method12765(-156515775);
         this.field6814 = 602060077 * (var1 - 1308041153 * this.field6812 / 2);
         if (this.field6810 * 1308041153 + 727605925 * this.field6811 > 773246731 * classSA.field5895) {
            if (var3 != 0) {
               return;
            }

            this.field6815 = classSA.field5895 * -2095371281 - this.field6813 * -649721363;
         }

         if (this.field6812 * 727605925 < 0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            }

            this.field6810 = 0;
         }

         this.field6815 = var2 * -317857149;
         if (this.field6811 * 1457978411 + this.field6812 * -531540521 > IntProjection.field1936 * 8379747) {
            if (var3 != 0) {
               throw new IllegalStateException();
            }

            this.field6812 = IntProjection.field1936 * 132558761 - -28198139 * this.field6811;
         }

         if (1457978411 * this.field6810 < 0) {
            if (var3 != 0) {
               throw new IllegalStateException();
            }

            this.field6810 = 0;
         }

         if (this.field6814 * -1985221643 != -1) {
            if (var3 != 0) {
               return;
            }

            if (null != this.field6804[this.field6815 * -1985221643]) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               }

               this.field6804[this.field6810 * -1985221643].method12767(this, (short)4036);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wz.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ag")
   final int method12764(int var1, int var2, int var3) {
      int var4 = this == client.field975 ? client.field1092 : client.field1087;

      for (int var5 = 0; var5 < this.field6794 * -1927903443; var5++) {
         int var6 = this.field6810 * 1457978411 + (this.field6794 * -1927903443 - 1 - var5 - var4) * 15 + 14;
         if (this.field6808) {
            var6 += 17;
         }

         if (var1 > this.field6814 * 727605925 && var1 < this.field6810 * 1308041153 + this.field6812 * 727605925 && var2 > var6 - 13 && var2 < var6 + 3) {
            return var5;
         }
      }

      return -1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   public final void method12746() {
      if (-1985221643 * this.field6814 != -1) {
         if (null != this.field6804[-1985221643 * this.field6811]) {
            method12741(this.field6804[this.field6812 * -1985221643], -1932347280);
         }

         this.field6814 = 1982257059;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bc")
   public final void method12777(int var1) {
      this.method12718(var1, -1111856393);
   }
}
