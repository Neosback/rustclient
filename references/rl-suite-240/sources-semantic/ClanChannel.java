import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import net.runelite.api.events.ClanMemberJoined;
import net.runelite.api.events.ClanMemberLeft;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ie")
public class ClanChannel extends Node implements net.runelite.api.clan.ClanChannel {
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   public static final int field2949 = 43;
   @ObfuscatedName("af")
   public long field2951;
   @ObfuscatedName("az")
   public List field2953;
   @ObfuscatedName("ae")
   public String field2946 = null;
   @ObfuscatedName("ag")
   public byte field2948;
   @ObfuscatedName("as")
   boolean field2954;
   @ObfuscatedName("ax")
   boolean field2945 = true;
   @ObfuscatedName("ac")
   int[] field2955;
   @ObfuscatedName("ab")
   public byte field2947;
   @ObfuscatedName("ap")
   static String field2952;
   @ToRemove(unused = "true")
   @ObfuscatedName("cq")
   static final int field2950 = 73;

   @ObfuscatedSignature(descriptor = "(Lhn;)V")
   @ObfuscatedName("ay")
   void method6214(ClanChannelMember var1) {
      this.field2953.add(var1);
      this.field2955 = null;
   }

   public ClanChannel(classXY var1) {
      this.method6229(var1, (byte)-18);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ae")
   void method6220(int var1, int var2) {
      this.method6235(var1);

      try {
         this.field2953.remove(var1);
         this.field2955 = null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ie.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)V")
   @ObfuscatedName("as")
   void method6229(classXY var1, byte var2) {
      try {
         int var3 = var1.method13095(-346779531);
         if (0 != (var3 & 1)) {
            if (var2 >= 1) {
               return;
            }

            this.field2954 = true;
         }

         if (0 != (var3 & 2)) {
            if (var2 >= 1) {
               throw new IllegalStateException();
            }

            this.field2945 = true;
         }

         int var4 = 2;
         if (0 != (var3 & 4)) {
            var4 = var1.method13095(-346779531);
         }

         this.field6550 = classXY.method13115(var1, 1943073534);
         this.field2951 = classXY.method13115(var1, 2019814134) * 2301448292752648763L;
         this.field2946 = var1.method13127(155668970);
         var1.method13122((byte)95);
         this.field2948 = var1.method13097((byte)17);
         this.field2947 = var1.method13097((byte)17);
         int var5 = var1.method13099(-1748913958);
         if (var5 > 0) {
            if (var2 >= 1) {
               throw new IllegalStateException();
            }

            this.field2953 = new ArrayList(var5);

            for (int var6 = 0; var6 < var5; var6++) {
               if (var2 >= 1) {
                  throw new IllegalStateException();
               }

               ClanChannelMember var7 = new ClanChannelMember();
               if (this.field2954) {
                  if (var2 >= 1) {
                     throw new IllegalStateException();
                  }

                  classXY.method13115(var1, -1009826537);
               }

               if (this.field2945) {
                  if (var2 >= 1) {
                     throw new IllegalStateException();
                  }

                  var7.field2844 = new classAAE(var1.method13127(503907042));
               }

               var7.field2839 = var1.method13097((byte)17);
               var7.field2840 = var1.method13099(-1944776342) * 179951339;
               if (var4 >= 3) {
                  var1.method13122((byte)29);
               }

               this.field2953.add(var6, var7);
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ie.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;B)I")
   @ObfuscatedName("rk")
   public static int method6222(ClanChannel var0, byte var1) {
      if (var0 == null) {
         var0.method6225(var1);
      }

      try {
         return var0.field2953.size();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ie.ab(" + 41);
      }
   }

   static {
      new BitSet(65536);
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("og")
   public int[] method6210() {
      if (this.field2955 == null) {
         String[] var1 = new String[this.field2953.size()];
         this.field2955 = new int[this.field2953.size()];

         for (int var2 = 0; var2 < this.field2953.size(); this.field2955[var2] = var2++) {
            var1[var2] = classAAE.method160(((ClanChannelMember)this.field2953.get(var2)).field2844, -2003115826);
         }

         classUO.method11476(var1, this.field2955, 1869466533);
      }

      return this.field2955;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("ax")
   public int[] method6211() {
      if (this.field2955 == null) {
         String[] var1 = new String[this.field2953.size()];
         this.field2955 = new int[this.field2953.size()];

         for (int var2 = 0; var2 < this.field2953.size(); this.field2955[var2] = var2++) {
            var1[var2] = classAAE.method160(((ClanChannelMember)this.field2953.get(var2)).field2844, -2003115826);
         }

         classUO.method11476(var1, this.field2955, 1086994240);
      }

      return this.field2955;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ai")
   void method6230(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (0 != (var2 & 1)) {
         this.field2954 = true;
      }

      if (0 != (var2 & 2)) {
         this.field2945 = true;
      }

      int var3 = 2;
      if (0 != (var2 & 4)) {
         var3 = var1.method13095(-346779531);
      }

      this.field6550 = classXY.method13115(var1, -1113640940);
      this.field2951 = classXY.method13115(var1, -486551884) * 2301448292752648763L;
      this.field2946 = var1.method13127(-638105996);
      var1.method13122((byte)80);
      this.field2948 = var1.method13097((byte)17);
      this.field2947 = var1.method13097((byte)17);
      int var4 = var1.method13099(-1127139805);
      if (var4 > 0) {
         this.field2953 = new ArrayList(var4);

         for (int var5 = 0; var5 < var4; var5++) {
            ClanChannelMember var6 = new ClanChannelMember();
            if (this.field2954) {
               classXY.method13115(var1, 829271577);
            }

            if (this.field2945) {
               var6.field2844 = new classAAE(var1.method13127(1641482552));
            }

            var6.field2839 = var1.method13097((byte)17);
            var6.field2840 = var1.method13099(1255418010) * 179951339;
            if (var3 >= 3) {
               var1.method13122((byte)113);
            }

            this.field2953.add(var5, var6);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhn;)V")
   @ObfuscatedName("ao")
   void method6215(ClanChannelMember var1) {
      this.field2953.add(var1);
      this.field2955 = null;
   }

   public List getMembers() {
      return this.field2953;
   }

   @ObfuscatedSignature(descriptor = "(Lhn;)V")
   @ObfuscatedName("al")
   void method6216(ClanChannelMember var1) {
      this.field2953.add(var1);
      this.field2955 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lhn;)V")
   @ObfuscatedName("aj")
   void method6217(ClanChannelMember var1) {
      this.field2953.add(var1);
      this.field2955 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("kk")
   public void method6235(int var1) {
      List var2 = this.getMembers();
      ClanChannelMember var3 = (ClanChannelMember)var2.get(var1);
      classOE.field4841.getCallbacks().post(new ClanMemberLeft(this, var3));
   }

   @ObfuscatedSignature(descriptor = "(Lie;Ljava/lang/String;B)I")
   @ObfuscatedName("pm")
   public static int method6226(ClanChannel var0, String var1, byte var2) {
      if (var0 == null) {
         var0.method6228(var1, var2);
      }

      try {
         if (!var0.field2945) {
            throw new RuntimeException("Displaynames not available");
         } else {
            for (int var3 = 0; var3 < var0.field2953.size(); var3++) {
               if (var2 != 33) {
                  throw new IllegalStateException();
               }

               if (classAAE.method157(((ClanChannelMember)var0.field2953.get(var3)).field2844, 1214968684).equalsIgnoreCase(var1)) {
                  if (var2 != 33) {
                     throw new IllegalStateException();
                  }

                  return var3;
               }
            }

            return -1;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ie.ag(" + 41);
      }
   }

   public String getName() {
      return this.field2946;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lie;Lxy;)V")
   @ObfuscatedName("gq")
   public static void method6231(ClanChannel var0, classXY var1) {
      if (var0 == null) {
         var0.method6233(var1);
      } else {
         int var2 = var1.method13095(-346779531);
         if (0 != (var2 & 1)) {
            var0.field2954 = true;
         }

         if (0 != (var2 & 2)) {
            var0.field2945 = true;
         }

         int var3 = 2;
         if (0 != (var2 & 4)) {
            var3 = var1.method13095(-346779531);
         }

         var0.field6550 = classXY.method13115(var1, -513208596);
         var0.field2951 = classXY.method13115(var1, 436870952) * 2301448292752648763L;
         var0.field2946 = var1.method13127(143390491);
         var1.method13122((byte)20);
         var0.field2948 = var1.method13097((byte)17);
         var0.field2947 = var1.method13097((byte)17);
         int var4 = var1.method13099(1175331380);
         if (var4 > 0) {
            var0.field2953 = new ArrayList(var4);

            for (int var5 = 0; var5 < var4; var5++) {
               ClanChannelMember var6 = new ClanChannelMember();
               if (var0.field2954) {
                  classXY.method13115(var1, -295476293);
               }

               if (var0.field2945) {
                  var6.field2844 = new classAAE(var1.method13127(-1355202949));
               }

               var6.field2839 = var1.method13097((byte)17);
               var6.field2840 = var1.method13099(50340404) * 180057721;
               if (var3 >= 3) {
                  var1.method13122((byte)26);
               }

               var0.field2953.add(var5, var6);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhn;B)V")
   @ObfuscatedName("af")
   void method6218(ClanChannelMember var1, byte var2) {
      try {
         this.field2953.add(var1);
         this.field2955 = null;
         this.method6236(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ie.af(" + ')');
      }
   }

   public net.runelite.api.clan.ClanChannelMember findMember(String var1) {
      classAAE var2 = new classAAE(var1, WidgetNode.field5277);
      var1 = var2.method182();
      if (var1 == null) {
         return null;
      } else {
         List var3 = this.getMembers();
         int[] var4 = this.method6237();
         int var5 = 0;
         int var6 = var4.length - 1;

         while (var5 <= var6) {
            int var7 = var5 + var6 >>> 1;
            int var8 = var4[var7];
            ClanChannelMember var9 = (ClanChannelMember)var3.get(var8);
            int var10 = var9.method6109().method182().compareTo(var1);
            if (var10 < 0) {
               var5 = var7 + 1;
            } else {
               if (var10 <= 0) {
                  return var9;
               }

               var6 = var7 - 1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aq")
   void method6221(int var1) {
      this.field2953.remove(var1);
      this.field2955 = null;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("ac")
   public int[] method6237() {
      return method6213(this, -1957709273);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   public int method6223() {
      return this.field2953.size();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   public int method6224() {
      return this.field2953.size();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("au")
   public int method6227(String var1) {
      if (!this.field2945) {
         throw new RuntimeException("Displaynames not available");
      } else {
         for (int var2 = 0; var2 < this.field2953.size(); var2++) {
            if (classAAE.method157(((ClanChannelMember)this.field2953.get(var2)).field2844, 1523255766).equalsIgnoreCase(var1)) {
               return var2;
            }
         }

         return -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ar")
   void method6232(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (0 != (var2 & 1)) {
         this.field2954 = true;
      }

      if (0 != (var2 & 2)) {
         this.field2945 = true;
      }

      int var3 = 2;
      if (0 != (var2 & 4)) {
         var3 = var1.method13095(-346779531);
      }

      this.field6550 = classXY.method13115(var1, 1694481221);
      this.field2951 = classXY.method13115(var1, 1816813901) * 2301448292752648763L;
      this.field2946 = var1.method13127(738845367);
      var1.method13122((byte)91);
      this.field2948 = var1.method13097((byte)17);
      this.field2947 = var1.method13097((byte)17);
      int var4 = var1.method13099(1660861027);
      if (var4 > 0) {
         this.field2953 = new ArrayList(var4);

         for (int var5 = 0; var5 < var4; var5++) {
            ClanChannelMember var6 = new ClanChannelMember();
            if (this.field2954) {
               classXY.method13115(var1, 654057150);
            }

            if (this.field2945) {
               var6.field2844 = new classAAE(var1.method13127(2115664700));
            }

            var6.field2839 = var1.method13097((byte)17);
            var6.field2840 = var1.method13099(-248176967) * 179951339;
            if (var3 >= 3) {
               var1.method13122((byte)51);
            }

            this.field2953.add(var5, var6);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("aw")
   void method6233(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (0 != (var2 & 1)) {
         this.field2954 = true;
      }

      if (0 != (var2 & 2)) {
         this.field2945 = true;
      }

      int var3 = 2;
      if (0 != (var2 & 4)) {
         var3 = var1.method13095(-346779531);
      }

      this.field6550 = classXY.method13115(var1, -377254497);
      this.field2951 = classXY.method13115(var1, 82911324) * 2301448292752648763L;
      this.field2946 = var1.method13127(98704368);
      var1.method13122((byte)29);
      this.field2948 = var1.method13097((byte)17);
      this.field2947 = var1.method13097((byte)17);
      int var4 = var1.method13099(-296460857);
      if (var4 > 0) {
         this.field2953 = new ArrayList(var4);

         for (int var5 = 0; var5 < var4; var5++) {
            ClanChannelMember var6 = new ClanChannelMember();
            if (this.field2954) {
               classXY.method13115(var1, 2137565659);
            }

            if (this.field2945) {
               var6.field2844 = new classAAE(var1.method13127(497868145));
            }

            var6.field2839 = var1.method13097((byte)17);
            var6.field2840 = var1.method13099(829984968) * 179951339;
            if (var3 >= 3) {
               var1.method13122((byte)116);
            }

            this.field2953.add(var5, var6);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhn;)V")
   @ObfuscatedName("aa")
   void method6219(ClanChannelMember var1) {
      this.field2953.add(var1);
      this.field2955 = null;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ep")
   static void method6234(byte var0) {
      try {
         if (!client.field980) {
            if (var0 >= 0) {
               throw new IllegalStateException();
            }
         } else {
            Widget var1 = classWK.field6689.method7422(565061963 * classBQ.field529, 1333668003 * client.field981, (byte)-103);
            if (var1 != null) {
               if (var0 >= 0) {
                  return;
               }

               if (var1.field4348 != null) {
                  if (var0 >= 0) {
                     return;
                  }

                  ScriptEvent var2 = classUQ.method11527(var1, 1629912630).method14195(var1.field4348, (byte)2).method14226((byte)52);
                  classSZ.method10771(var2, -997665260);
               }
            }

            client.field982 = 1326078651;
            client.field980 = false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ie.ep(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("az")
   public int[] method6212(int var1) {
      try {
         if (this.field2955 == null) {
            if (var1 >= -1492108231) {
               throw new IllegalStateException();
            }

            String[] var2 = new String[this.field2953.size()];
            this.field2955 = new int[this.field2953.size()];

            for (int var3 = 0; var3 < this.field2953.size(); this.field2955[var3] = var3++) {
               var2[var3] = classAAE.method160(((ClanChannelMember)this.field2953.get(var3)).field2844, -2003115826);
            }

            classUO.method11476(var2, this.field2955, 1863276462);
         }

         return this.field2955;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ie.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ab")
   public int method6225(byte var1) {
      try {
         return this.field2953.size();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ie.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhn;)V")
   @ObfuscatedName("ld")
   public void method6236(ClanChannelMember var1) {
      classOE.field4841.getCallbacks().post(new ClanMemberJoined(this, var1));
   }

   @ObfuscatedSignature(descriptor = "(Lie;I)[I")
   @ObfuscatedName("qn")
   public static int[] method6213(ClanChannel var0, int var1) {
      if (var0 == null) {
         return var0.method6212(var1);
      } else {
         try {
            if (var0.field2955 == null) {
               if (var1 >= -1492108231) {
                  throw new IllegalStateException();
               }

               String[] var2 = new String[var0.field2953.size()];
               var0.field2955 = new int[var0.field2953.size()];

               for (int var3 = 0; var3 < var0.field2953.size(); var0.field2955[var3] = var3++) {
                  var2[var3] = classAAE.method160(((ClanChannelMember)var0.field2953.get(var3)).field2844, -2003115826);
               }

               classUO.method11476(var2, var0.field2955, 1863276462);
            }

            return var0.field2955;
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "ie.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)I")
   @ObfuscatedName("ag")
   public int method6228(String var1, byte var2) {
      try {
         if (!this.field2954) {
            throw new RuntimeException("Displaynames not available");
         } else {
            for (int var3 = 0; var3 < this.field2953.size(); var3++) {
               if (var2 != 33) {
                  throw new IllegalStateException();
               }

               if (classAAE.method157(((ClanChannelMember)this.field2953.get(var3)).field2844, 1214968684).equalsIgnoreCase(var1)) {
                  if (var2 != 33) {
                     throw new IllegalStateException();
                  }

                  return var3;
               }
            }

            return -1;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ie.ag(" + 41);
      }
   }
}
