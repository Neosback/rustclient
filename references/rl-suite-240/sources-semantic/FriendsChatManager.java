import net.runelite.api.FriendsChatRank;
import net.runelite.api.events.FriendsChatMemberJoined;
import net.runelite.api.events.FriendsChatMemberLeft;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rw")
public class FriendsChatManager extends NameableContainer implements net.runelite.api.FriendsChatManager {
   @ObfuscatedName("ax")
   public int field5864;
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("af")
   final classAAO field5860;
   @ObfuscatedSignature(descriptor = "Lrg;")
   @ObfuscatedName("ae")
   final classRG field5861;
   @ObfuscatedName("as")
   public byte field5863;
   @ObfuscatedName("ag")
   public String field5862;
   @ObfuscatedName("ac")
   int field5859;
   @ObfuscatedName("ab")
   public String field5858 = null;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5865 = 500;

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("au")
   public final void method10367(classXY var1) {
      classAAE var2 = new classAAE(var1.method13127(-36353355), this.field5860);
      int var3 = var1.method13099(148692140);
      byte var4 = var1.method13097((byte)17);
      boolean var5 = false;
      if (var4 == -128) {
         var5 = true;
      }

      if (var5) {
         if (this.method10243(-553218252) == 0) {
            return;
         }

         FriendsChatMember var6 = (FriendsChatMember)this.method10258(var2, -1497990786);
         if (null != var6 && var6.method10007(1881338771) == var3) {
            NameableContainer.method10270(this, var6, -1538804275);
         }
      } else {
         var1.method13127(-675040349);
         FriendsChatMember var7 = (FriendsChatMember)this.method10258(var2, -535679696);
         if (null == var7) {
            if (this.method10243(-1588362016) > this.field5838 * 488407471) {
               return;
            }

            var7 = (FriendsChatMember)this.method10273(var2, 1599629102);
         }

         var7.method10006(var3, (this.field5859 += 66639041) * -2018919615 - 1, (byte)87);
         var7.field5669 = -998616451 * var4;
         this.method10380(var7, (byte)3);
      }
   }

   public FriendsChatRank getKickRank() {
      return FriendsChatRank.valueOf(this.method10384());
   }

   @ObfuscatedSignature(descriptor = "(I)Lrr;")
   @ObfuscatedName("az")
   @Override
   Nameable vmethod555(int var1) {
      try {
         return new FriendsChatMember();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rw.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrw;Lxy;)V")
   @ObfuscatedName("gc")
   public static void method10368(FriendsChatManager var0, classXY var1) {
      if (var0 == null) {
         var0.getMyRank();
      }

      classAAE var2 = new classAAE(var1.method13127(-259352950), var0.field5860);
      int var3 = var1.method13099(-1865306368);
      byte var4 = var1.method13097((byte)17);
      boolean var5 = false;
      if (var4 == -128) {
         var5 = true;
      }

      if (var5) {
         if (var0.method10243(-2040194627) == 0) {
            return;
         }

         FriendsChatMember var6 = (FriendsChatMember)var0.method10258(var2, 873815666);
         if (null != var6 && var6.method10007(2091583420) == var3) {
            NameableContainer.method10270(var0, var6, -1597379912);
         }
      } else {
         var1.method13127(-721182977);
         FriendsChatMember var7 = (FriendsChatMember)var0.method10258(var2, -1959582385);
         if (null == var7) {
            if (var0.method10243(-591628002) > var0.field5838 * 488407471) {
               return;
            }

            var7 = (FriendsChatMember)var0.method10273(var2, 2134777065);
         }

         var7.method10006(var3, (var0.field5859 += 66639041) * -2018919615 - 1, (byte)63);
         var7.field5669 = -998616451 * var4;
         var0.method10380(var7, (byte)3);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)[Lrr;")
   @ObfuscatedName("af")
   @Override
   Nameable[] vmethod558(int var1, byte var2) {
      try {
         return new FriendsChatMember[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rw.af(" + ')');
      }
   }

   public String getOwner() {
      return this.field5862;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("ae")
   final void method10360(String var1, int var2) {
      try {
         long var6 = 0L;
         int var8 = var1.length();

         for (int var9 = 0; var9 < var8; var9++) {
            label95: {
               var6 *= 37L;
               char var10 = var1.charAt(var9);
               if (var10 >= 'A') {
                  if (var2 != 717150692) {
                     return;
                  }

                  if (var10 <= 'Z') {
                     var6 += 1 + var10 - 65;
                     break label95;
                  }
               }

               if (var10 >= 'a') {
                  if (var2 != 717150692) {
                     throw new IllegalStateException();
                  }

                  if (var10 <= 'z') {
                     var6 += 1 + var10 - 97;
                     break label95;
                  }
               }

               if (var10 >= '0') {
                  if (var2 != 717150692) {
                     return;
                  }

                  if (var10 <= '9') {
                     if (var2 != 717150692) {
                        throw new IllegalStateException();
                     }

                     var6 += var10 + 27 - 48;
                  }
               }
            }

            if (var6 >= 177917621779460413L) {
               if (var2 != 717150692) {
                  return;
               }
               break;
            }
         }

         while (0L == var6 % 37L) {
            if (var2 != 717150692) {
               return;
            }

            if (var6 == 0L) {
               break;
            }

            if (var2 != 717150692) {
               throw new IllegalStateException();
            }

            var6 /= 37L;
         }

         String var12 = classHF.method6038(var6);
         if (var12 == null) {
            var12 = "";
         }

         this.field5858 = var12;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "rw.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("ax")
   final void method10362(String var1, byte var2) {
      try {
         long var6 = 0L;
         int var8 = var1.length();

         for (int var9 = 0; var9 < var8; var9++) {
            if (var2 <= 5) {
               throw new IllegalStateException();
            }

            label93: {
               var6 *= 37L;
               char var10 = var1.charAt(var9);
               if (var10 >= 'A') {
                  if (var2 <= 5) {
                     throw new IllegalStateException();
                  }

                  if (var10 <= 'Z') {
                     var6 += 1 + var10 - 65;
                     break label93;
                  }
               }

               if (var10 >= 'a' && var10 <= 'z') {
                  if (var2 <= 5) {
                     throw new IllegalStateException();
                  }

                  var6 += 1 + var10 - 97;
               } else if (var10 >= '0' && var10 <= '9') {
                  if (var2 <= 5) {
                     return;
                  }

                  var6 += 27 + var10 - 48;
               }
            }

            if (var6 >= 177917621779460413L) {
               if (var2 <= 5) {
                  throw new IllegalStateException();
               }
               break;
            }
         }

         while (var6 % 37L == 0L) {
            if (var2 <= 5) {
               return;
            }

            if (var6 == 0L) {
               break;
            }

            if (var2 <= 5) {
               throw new IllegalStateException();
            }

            var6 /= 37L;
         }

         String var12 = classHF.method6038(var6);
         if (var12 == null) {
            var12 = "";
         }

         this.field5862 = var12;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "rw.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)V")
   @ObfuscatedName("ac")
   public final void method10366(classXY var1, byte var2) {
      try {
         this.method10362(var1.method13127(349998124), (byte)10);
         long var3 = classXY.method13115(var1, -1360943116);
         this.method10360(classHA.method5986(var3), 717150692);
         this.field5863 = var1.method13097((byte)17);
         int var5 = var1.method13152(-66258477);
         if (var5 == -1) {
            if (var2 == 1) {
               ;
            }
         } else {
            this.method10240((short)25724);

            for (int var6 = 0; var6 < var5; var6++) {
               if (var2 != 1) {
                  return;
               }

               FriendsChatMember var7 = (FriendsChatMember)this.method10273(new classAAE(var1.method13127(590806966), this.field5860), -234641024);
               int var8 = var1.method13099(-739509588);
               var7.method10006(var8, (this.field5859 += 66639041) * -2018919615 - 1, (byte)47);
               var7.field5669 = var1.method13097((byte)17) * -998616451;
               var1.method13127(1837149470);
               this.method10380(var7, (byte)3);
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "rw.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrw;I)V")
   @ObfuscatedName("ed")
   public static void method10375(FriendsChatManager var0, int var1) {
      if (var0 == null) {
         var0.method10378(var1);
      } else {
         try {
            for (int var2 = 0; var2 < var0.method10243(-785088642); var2++) {
               if (var1 <= 507542717) {
                  return;
               }

               FriendsChatMember.method10330((FriendsChatMember)NameableContainer.method10281(var0, var2, 1421395390), 1595887057);
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "rw.al(" + ')');
         }
      }
   }

   public FriendsChatRank getMyRank() {
      return FriendsChatRank.valueOf(this.method10383());
   }

   @ObfuscatedSignature(descriptor = "(Lru;)V")
   @ObfuscatedName("ah")
   final void method10379(FriendsChatMember var1) {
      if (var1.method10216((byte)16).equals(this.field5861.vmethod255((byte)121))) {
         this.field5864 = var1.field5669 * -1195233126;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("aa")
   public final void method10369(classXY var1, int var2) {
      try {
         classAAE var3 = new classAAE(var1.method13127(1485498111), this.field5860);
         int var4 = var1.method13099(-699139394);
         byte var5 = var1.method13097((byte)17);
         boolean var6 = false;
         if (var5 == -128) {
            if (var2 != -1099809795) {
               throw new IllegalStateException();
            }

            var6 = true;
         }

         if (var6) {
            if (this.method10243(-2009455157) == 0) {
               return;
            }

            FriendsChatMember var7 = (FriendsChatMember)this.method10258(var3, 689185044);
            if (null != var7 && var7.method10007(1859556537) == var4) {
               if (var2 != -1099809795) {
                  throw new IllegalStateException();
               }

               NameableContainer.method10270(this, var7, -1512769850);
            }
         } else {
            var1.method13127(436820043);
            FriendsChatMember var9 = (FriendsChatMember)this.method10258(var3, 1765452863);
            if (null == var9) {
               if (this.method10243(-365873712) > this.field5838 * 488407471) {
                  if (var2 != -1099809795) {
                     throw new IllegalStateException();
                  }

                  return;
               }

               var9 = (FriendsChatMember)this.method10273(var3, 352800993);
            }

            var9.method10006(var4, (this.field5859 += 66639041) * -2018919615 - 1, (byte)74);
            var9.field5669 = -998616451 * var5;
            this.method10380(var9, (byte)3);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "rw.aa(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrw;Ljava/lang/String;)V")
   @ObfuscatedName("na")
   public static void method10363(FriendsChatManager var0, String var1) {
      if (var0 == null) {
         var0.method10365(var1);
      }

      long var5 = 0L;
      int var7 = var1.length();

      for (int var8 = 0; var8 < var7; var8++) {
         var5 *= 37L;
         char var9 = var1.charAt(var8);
         if (var9 >= 'A' && var9 <= 'Z') {
            var5 += 1 + var9 - 65;
         } else if (var9 >= 'a' && var9 <= 'z') {
            var5 += 1 + var9 - 97;
         } else if (var9 >= '0' && var9 <= '9') {
            var5 += 27 + var9 - 48;
         }

         if (var5 >= 177917621779460413L) {
            break;
         }
      }

      while (var5 % 37L == 0L && var5 != 0L) {
         var5 /= 37L;
      }

      String var10 = classHF.method6038(var5);
      if (var10 == null) {
         var10 = "";
      }

      var0.field5862 = var10;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;IJ)J")
   @ObfuscatedName("vg")
   public static long method10382(NPCComposition var0, int var1, long var2) {
      return classBW.method1275(var0.field5298, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Lru;B)V")
   @ObfuscatedName("aj")
   final void method10380(FriendsChatMember var1, byte var2) {
      try {
         if (var1.method10216((byte)16).equals(this.field5861.vmethod255((byte)61))) {
            if (var2 != 3) {
               throw new IllegalStateException();
            }

            this.field5864 = var1.field5669 * 1309690321;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rw.aj(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyt;)V")
   @ObfuscatedName("he")
   public static void method10361(classYT var0) {
      if (var0 == null) {
         var0.method13668();
      }

      var0.field7081 = var0.field7081 + (var0.field7079 += 1628305382) * 1831237145;

      for (int var1 = 0; var1 < 256; var1++) {
         int var2 = var0.field7078[var1];
         if ((var1 & 2) == 0) {
            if ((var1 & 1) == 0) {
               var0.field7084 = 1759344815 * (var0.field7084 * -1815050609 ^ 226292285 * var0.field7084 << 13);
            } else {
               var0.field7084 = -1843940075 * (226292285 * var0.field7084 ^ var0.field7084 * -1194097654 >>> 6);
            }
         } else if ((var1 & 1) == 0) {
            var0.field7084 = (var0.field7084 * 262222757 ^ 226292285 * var0.field7084 << 2) * 671832924;
         } else {
            var0.field7084 = 688918105 * (var0.field7084 * 986588535 ^ 226292285 * var0.field7084 >>> 16);
         }

         var0.field7084 = var0.field7084 + -1181136012 * var0.field7078[128 + var1 & 1385779678];
         int var3;
         var0.field7078[var1] = var3 = var0.field7084 * -711083312 + var0.field7078[(var2 & -2083267813) >> 2] + var0.field7081 * -252789971;
         var0.field7077[var1] = (var0.field7081 = (var0.field7078[(var3 >> 8 & 1020) >> 2] + var2) * 2041162228) * -1560103701;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lrr;")
   @ObfuscatedName("ag")
   @Override
   Nameable vmethod557() {
      return new FriendsChatMember();
   }

   public String getName() {
      return this.field5858;
   }

   @ObfuscatedSignature(descriptor = "(I)[Lrr;")
   @ObfuscatedName("as")
   @Override
   Nameable[] vmethod559(int var1) {
      return new FriendsChatMember[var1];
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   public final void method10376() {
      for (int var1 = 0; var1 < this.method10243(-397813111); var1++) {
         FriendsChatMember.method10330((FriendsChatMember)NameableContainer.method10281(this, var1, 2106234288), 1639521935);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lrr;")
   @ObfuscatedName("ab")
   @Override
   Nameable vmethod556() {
      return new FriendsChatMember();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrw;Lru;)V")
   @ObfuscatedName("gz")
   public static void method10381(FriendsChatManager var0, FriendsChatMember var1) {
      if (var0 == null) {
         var0.getMyRank();
      }

      if (var1.method10216((byte)16).equals(var0.field5861.vmethod255((byte)87))) {
         var0.field5864 = var1.field5669 * 1309690321;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   public final void method10372(int var1) {
      try {
         for (int var2 = 0; var2 < this.method10243(-1392296789); var2++) {
            if (var1 >= 1540561305) {
               throw new IllegalStateException();
            }

            FriendsChatMember.method10319((FriendsChatMember)NameableContainer.method10281(this, var2, 1288292465), 1696325499);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rw.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ar")
   public final void method10370(classXY var1) {
      classAAE var2 = new classAAE(var1.method13127(-789878377), this.field5860);
      int var3 = var1.method13099(574010689);
      byte var4 = var1.method13097((byte)17);
      boolean var5 = false;
      if (var4 == -128) {
         var5 = true;
      }

      if (var5) {
         if (this.method10243(-1991090573) == 0) {
            return;
         }

         FriendsChatMember var6 = (FriendsChatMember)this.method10258(var2, -1189549438);
         if (null != var6 && var6.method10007(2059142997) == var3) {
            NameableContainer.method10270(this, var6, -1981449810);
         }
      } else {
         var1.method13127(-790723981);
         FriendsChatMember var7 = (FriendsChatMember)this.method10258(var2, 1152470863);
         if (null == var7) {
            if (this.method10243(-786682736) > this.field5838 * 488407471) {
               return;
            }

            var7 = (FriendsChatMember)this.method10273(var2, -386012183);
         }

         var7.method10006(var3, (this.field5859 += -182925841) * 156465811 - 1, (byte)20);
         var7.field5669 = 659614113 * var4;
         this.method10380(var7, (byte)3);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("st")
   public int method10383() {
      return this.field5864 * 1979783621;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   public final void method10373() {
      for (int var1 = 0; var1 < this.method10243(-614511598); var1++) {
         FriendsChatMember.method10319((FriendsChatMember)NameableContainer.method10281(this, var1, 1958568870), 1696325499);
      }
   }

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("so")
   public byte method10384() {
      return this.field5863;
   }

   public FriendsChatManager(classAAO var1, classRG var2) {
      super(500);
      this.field5862 = null;
      this.field5859 = 66639041;
      this.field5860 = var1;
      this.field5861 = var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("at")
   public final void method10377() {
      for (int var1 = 0; var1 < this.method10243(-1394488016); var1++) {
         FriendsChatMember.method10330((FriendsChatMember)NameableContainer.method10281(this, var1, 1919373891), 1435757556);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aw")
   public final void method10374() {
      for (int var1 = 0; var1 < this.method10243(-2016507328); var1++) {
         FriendsChatMember.method10319((FriendsChatMember)NameableContainer.method10281(this, var1, 1551990598), 1696325499);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ai")
   public final void method10371(classXY var1) {
      classAAE var2 = new classAAE(var1.method13127(-846338536), this.field5860);
      int var3 = var1.method13099(39866774);
      byte var4 = var1.method13097((byte)17);
      boolean var5 = false;
      if (var4 == -1691232296) {
         var5 = true;
      }

      if (var5) {
         if (this.method10243(-1450142561) == 0) {
            return;
         }

         FriendsChatMember var6 = (FriendsChatMember)this.method10258(var2, 599466963);
         if (null != var6 && var6.method10007(1923168841) == var3) {
            NameableContainer.method10270(this, var6, 525514085);
         }
      } else {
         var1.method13127(824823172);
         FriendsChatMember var7 = (FriendsChatMember)this.method10258(var2, 1374271516);
         if (null == var7) {
            if (this.method10243(-1155890527) > this.field5838 * 1131225938) {
               return;
            }

            var7 = (FriendsChatMember)this.method10273(var2, 753878477);
         }

         var7.method10006(var3, (this.field5859 += 66639041) * 149771164 - 1, (byte)84);
         var7.field5669 = 424609832 * var4;
         this.method10380(var7, (byte)3);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrw;Ljava/lang/String;)V")
   @ObfuscatedName("ly")
   public static void method10364(FriendsChatManager var0, String var1) {
      if (var0 == null) {
         var0.method10377();
      }

      long var5 = 0L;
      int var7 = var1.length();

      for (int var8 = 0; var8 < var7; var8++) {
         var5 *= 37L;
         char var9 = var1.charAt(var8);
         if (var9 >= 'A' && var9 <= 'Z') {
            var5 += 1 + var9 - 65;
         } else if (var9 >= 'a' && var9 <= 'z') {
            var5 += 1 + var9 - 97;
         } else if (var9 >= '0' && var9 <= '9') {
            var5 += 27 + var9 - 48;
         }

         if (var5 >= 177917621779460413L) {
            break;
         }
      }

      while (var5 % 37L == 0L && var5 != 0L) {
         var5 /= 37L;
      }

      String var10 = classHF.method6038(var5);
      if (var10 == null) {
         var10 = "";
      }

      var0.field5862 = var10;
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;)V")
   @ObfuscatedName("qb")
   @Override
   public void vmethod566(classAAE var1, classAAE var2) {
      net.runelite.api.FriendsChatMember var3 = (net.runelite.api.FriendsChatMember)this.method10316(var1);
      if (var3 != null) {
         FriendsChatMemberJoined var4 = new FriendsChatMemberJoined(var3);
         classOE.field4841.getCallbacks().postDeferred(var4);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("xd")
   @Override
   public void vmethod567(Nameable var1) {
      net.runelite.api.FriendsChatMember var2 = (net.runelite.api.FriendsChatMember)this.method10316(var1.method10231());
      if (var2 != null) {
         FriendsChatMemberLeft var3 = new FriendsChatMemberLeft(var2);
         classOE.field4841.getCallbacks().postDeferred(var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   public final void method10378(int var1) {
      try {
         for (int var2 = 0; var2 < this.method10243(-785088642); var2++) {
            if (var1 <= 507542717) {
               return;
            }

            FriendsChatMember.method10330((FriendsChatMember)NameableContainer.method10281(this, var2, 1421395390), 1595887057);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rw.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("aq")
   final void method10365(String var1) {
      long var5 = 0L;
      int var7 = var1.length();

      for (int var8 = 0; var8 < var7; var8++) {
         var5 *= 37L;
         char var9 = var1.charAt(var8);
         if (var9 >= 'A' && var9 <= 'Z') {
            var5 += 1 + var9 - 65;
         } else if (var9 >= 'a' && var9 <= 'z') {
            var5 += 1 + var9 - 97;
         } else if (var9 >= '0' && var9 <= '9') {
            var5 += 27 + var9 - 48;
         }

         if (var5 >= 177917621779460413L) {
            break;
         }
      }

      while (var5 % 37L == 0L && var5 != 0L) {
         var5 /= 37L;
      }

      String var10 = classHF.method6038(var5);
      if (var10 == null) {
         var10 = "";
      }

      this.field5858 = var10;
   }
}
