import net.runelite.api.FriendsChatRank;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ru")
public class FriendsChatMember extends ChatPlayer implements net.runelite.api.FriendsChatMember {
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("az")
   classRC field5844 = classRC.field5638;
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("af")
   classRC field5845 = classRC.field5638;

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ab")
   void method10324(byte var1) {
      try {
         classRC var10001;
         if (NameableContainer.method10250(classQQ.field5595.field593, this.field5833, (byte)10)) {
            if (var1 != 1) {
               throw new IllegalStateException();
            }

            var10001 = classRC.field5640;
         } else {
            var10001 = classRC.field5639;
         }

         this.field5844 = var10001;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ru.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lru;I)V")
   @ObfuscatedName("kq")
   public static void method10319(FriendsChatMember var0, int var1) {
      if (var0 == null) {
         var0.method10320(var1);
      }

      try {
         var0.field5844 = classRC.field5638;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ru.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("as")
   public final boolean method10332(int var1) {
      try {
         if (this.field5845 == classRC.field5638) {
            if (var1 >= 590677983) {
               throw new IllegalStateException();
            }

            this.method10335(1196222169);
         }

         boolean var10000;
         if (this.field5845 == classRC.field5640) {
            if (var1 >= 590677983) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ru.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   void method10327() {
      this.field5845 = classRC.field5638;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lru;)V")
   @ObfuscatedName("za")
   public static void method10325(FriendsChatMember var0) {
      var0.field5844 = NameableContainer.method10250(classQQ.field5595.field593, var0.field5833, (byte)10) ? classRC.field5640 : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   void method10335(int var1) {
      try {
         classRC var10001;
         if (NameableContainer.method10250(classQQ.field5595.field591, this.field5833, (byte)10)) {
            if (var1 != 1196222169) {
               throw new IllegalStateException();
            }

            var10001 = classRC.field5640;
         } else {
            var10001 = classRC.field5639;
         }

         this.field5845 = var10001;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ru.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   void method10336() {
      this.field5845 = NameableContainer.method10250(classQQ.field5595.field591, this.field5833, (byte)10) ? classRC.field5640 : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   void method10328() {
      this.field5845 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   void method10326() {
      this.field5844 = NameableContainer.method10250(classQQ.field5595.field593, this.field5833, (byte)10) ? classRC.field5640 : classRC.field5639;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lru;)Z")
   @ObfuscatedName("hb")
   public static boolean method10333(FriendsChatMember var0) {
      if (var0.field5845 == classRC.field5638) {
         var0.method10335(1196222169);
      }

      return var0.field5845 == classRC.field5640;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lru;)V")
   @ObfuscatedName("tb")
   public static void method10337(FriendsChatMember var0) {
      var0.field5845 = NameableContainer.method10250(classQQ.field5595.field591, var0.field5833, (byte)10) ? classRC.field5640 : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   void method10329() {
      this.field5845 = classRC.field5638;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lru;)Z")
   @ObfuscatedName("uv")
   public static boolean method10321(FriendsChatMember var0) {
      if (classRC.field5638 == var0.field5844) {
         var0.method10324((byte)1);
      }

      return classRC.field5640 == var0.field5844;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   public final boolean method10334() {
      if (this.field5845 == classRC.field5638) {
         this.method10335(1196222169);
      }

      return this.field5845 == classRC.field5640;
   }

   FriendsChatMember() {
   }

   public FriendsChatRank getRank() {
      return FriendsChatRank.valueOf(this.method10015());
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("af")
   public final boolean method10322(int var1) {
      try {
         if (classRC.field5638 == this.field5844) {
            if (var1 <= 1175335984) {
               throw new IllegalStateException();
            }

            this.method10324((byte)1);
         }

         boolean var10000;
         if (classRC.field5640 == this.field5844) {
            if (var1 <= 1175335984) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ru.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   void method10338() {
      this.field5845 = NameableContainer.method10250(classQQ.field5595.field591, this.field5833, (byte)10) ? classRC.field5640 : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;Lgy;FLgy;S)V")
   @ObfuscatedName("az")
   static void method10323(classGY var0, classGY var1, float var2, classGY var3, short var4) {
      try {
         var2 = classBF.method1001(0.0F, 1.0F, var2, 466287385);
         int var5 = classGY.method5930(var1, 1368589216) - classGY.method5930(var0, 20317292);
         int var6 = var1.method5936(1690676705) - var0.method5936(1690676705);
         var5 = (int)(var5 * var2);
         var6 = (int)(var6 * var2);
         var3.method5957(classGY.method5930(var0, 712339550) + var5, var0.method5936(1690676705) + var6, (byte)5);
         int var7 = classNF.method7876(classGY.method5947(var0, 2079664854), classGY.method5947(var1, 2019635063), (byte)45);
         var7 = (int)(var7 * var2);
         var3.method5950(classGY.method5947(var0, 1496029351) + var7, -1974460731);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ru.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lru;I)V")
   @ObfuscatedName("jx")
   public static void method10330(FriendsChatMember var0, int var1) {
      if (var0 == null) {
         var0.method10331(var1);
      } else {
         try {
            var0.field5845 = classRC.field5638;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "ru.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   void method10331(int var1) {
      try {
         this.field5845 = classRC.field5639;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ru.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   void method10320(int var1) {
      try {
         this.field5844 = classRC.field5639;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ru.az(" + ')');
      }
   }
}
