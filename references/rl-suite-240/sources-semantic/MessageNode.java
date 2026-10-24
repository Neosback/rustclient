import java.net.MalformedURLException;
import java.net.URL;
import net.runelite.api.ChatMessageType;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cg")
public class MessageNode extends classVJ implements net.runelite.api.MessageNode {
   @ObfuscatedName("ae")
   int field724;
   @ObfuscatedName("af")
   int field725;
   @ObfuscatedName("as")
   String field730;
   @ObfuscatedName("os")
   public String field732;
   @ObfuscatedName("az")
   int field727;
   @ObfuscatedSignature(descriptor = "Laae;")
   @ObfuscatedName("ag")
   classAAE field728;
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("ac")
   classRC field723;
   @ObfuscatedName("ax")
   String field731;
   @ObfuscatedName("ab")
   String field729;
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("aa")
   classRC field722;
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("ca")
   static IndexedSprite[] field733;
   @ObfuscatedName("zg")
   public int field721 = (int)(System.currentTimeMillis() / 1000L);
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   public static final int field726 = 29;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcg;)V")
   @ObfuscatedName("gu")
   public static void method1475(MessageNode var0) {
      if (var0 == null) {
         var0.getId();
      }

      var0.field722 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("gy")
   static final void method1493(int var0, int var1, int var2) {
      try {
         ClanChannel var10000;
         if (var0 >= 0) {
            if (var2 == -2028542429) {
               throw new IllegalStateException();
            }

            var10000 = client.field867[var0];
         } else {
            var10000 = classKA.field3511;
         }

         ClanChannel var3 = var10000;
         if (var3 != null) {
            if (var2 == -2028542429) {
               throw new IllegalStateException();
            }

            if (var1 >= 0) {
               if (var2 == -2028542429) {
                  throw new IllegalStateException();
               }

               if (var1 < ClanChannel.method6222(var3, (byte)-40)) {
                  ClanChannelMember var4 = (ClanChannelMember)var3.field2953.get(var1);
                  if (-1 != var4.field2839) {
                     return;
                  }

                  String var5 = classAAE.method157(var4.field2844, 1679046558);
                  classJL var6 = classEF.method3863(classJS.CLANCHANNEL_KICKUSER, client.field796.field1533, (byte)-16);
                  classXY.method13029(var6.field3341, 3 + classAU.method709(var5, (byte)109), (byte)-119);
                  classXY.method13029(var6.field3341, var0, (byte)-30);
                  var6.field3341.method13031(var1, -1542382992);
                  var6.field3341.method13054(var5, (short)6913);
                  client.field796.method3374(var6, -1301097035);
                  return;
               }

               if (var2 == -2028542429) {
                  return;
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "cg.gy(" + ')');
      }
   }

   public void setRuneLiteFormatMessage(String var1) {
      this.field732 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   void method1464(int var1) {
      try {
         this.field723 = classRC.field5638;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cg.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ae")
   final boolean method1467(int var1) {
      try {
         if (this.field723 == classRC.field5638) {
            if (var1 <= -460495914) {
               throw new IllegalStateException();
            }

            this.method1473((byte)-108);
         }

         boolean var10000;
         if (classRC.field5640 == this.field723) {
            if (var1 <= -460495914) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cg.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bn")
   final boolean method1481() {
      if (classRC.field5638 == this.field722) {
         method1487(this, -2079666610);
      }

      return this.field722 == classRC.field5640;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;B)V")
   @ObfuscatedName("az")
   void method1461(int var1, String var2, String var3, String var4, byte var5) {
      try {
         int var6 = (classCN.field1170 += 544858879) * -985850625 - 1;
         this.field727 = 55099013 * var6;
         this.field725 = client.field856 * 1149202047;
         this.field724 = var1 * 302524819;
         this.field729 = var2;
         this.method1488(-867691668);
         this.field730 = var3;
         this.field731 = var4;
         this.method1464(1287164533);
         this.method1479(-533308968);
         this.method1495(var1, var2, var3, var4);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "cg.az(" + ')');
      }
   }

   public int getTimestamp() {
      return this.field721;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("as")
   final boolean method1482(int var1) {
      try {
         if (classRC.field5638 == this.field722) {
            method1487(this, -1965250193);
         }

         boolean var10000;
         if (this.field722 == classRC.field5640) {
            if (var1 <= 1770980892) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cg.as(" + ')');
      }
   }

   public void setTimestamp(int var1) {
      this.field721 = var1;
   }

   public String getName() {
      return this.field729;
   }

   MessageNode(int var1, String var2, String var3, String var4) {
      this.field723 = classRC.field5638;
      this.field722 = classRC.field5638;
      this.method1461(var1, var2, var3, var4, (byte)-70);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ac")
   final void method1488(int var1) {
      try {
         if (null != this.field729) {
            if (var1 <= -1967686263) {
               throw new IllegalStateException();
            }

            this.field728 = new classAAE(classKW.method6620(this.field729, 88707466), WidgetNode.field5277);
         } else {
            this.field728 = null;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cg.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bm")
   final void method1489() {
      if (null != this.field729) {
         this.field728 = new classAAE(classKW.method6620(this.field729, 1689865045), WidgetNode.field5277);
      } else {
         this.field728 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[Lku;")
   @ObfuscatedName("ag")
   public static classKU[] method1460(byte var0) {
      try {
         return new classKU[]{classKU.field4023, classKU.field4026, classKU.field4024, classKU.field4025};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "cg.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   void method1465() {
      this.field723 = classRC.field5638;
   }

   public String getSender() {
      return this.field730;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcg;)V")
   @ObfuscatedName("vo")
   public static void method1486(MessageNode var0) {
      if (var0 == null) {
         var0.getTimestamp();
      }

      var0.field722 = NameableContainer.method10250(classQQ.field5595.field591, var0.field728, (byte)10) ? classRC.field5640 : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   final boolean method1468() {
      if (this.field723 == classRC.field5638) {
         this.method1473((byte)-50);
      }

      return classRC.field5640 == this.field723;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("aa")
   void method1462(int var1, String var2, String var3, String var4) {
      int var5 = (classCN.field1170 += 544858879) * -985850625 - 1;
      this.field727 = 55099013 * var5;
      this.field725 = client.field856 * 1149202047;
      this.field724 = var1 * 302524819;
      this.field729 = var2;
      this.method1488(116783986);
      this.field730 = var3;
      this.field731 = var4;
      this.method1464(1287164533);
      this.method1479(1920169371);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   final boolean method1469() {
      if (this.field723 == classRC.field5638) {
         this.method1473((byte)-5);
      }

      return classRC.field5640 == this.field723;
   }

   public void setName(String var1) {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         this.field729 = var1;
         int var2 = var1.lastIndexOf(62);
         if (var2 != -1) {
            var1 = var1.substring(var2 + 1);
         }

         this.field728 = new classAAE(var1, WidgetNode.field5277);
      }
   }

   public String getRuneLiteFormatMessage() {
      return this.field732;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   void method1471() {
      this.field723 = NameableContainer.method10250(classQQ.field5595.field593, this.field728, (byte)10) ? classRC.field5640 : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aw")
   void method1472() {
      this.field723 = NameableContainer.method10250(classQQ.field5595.field593, this.field728, (byte)10) ? classRC.field5640 : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   void method1476() {
      this.field722 = classRC.field5638;
   }

   public void setValue(String var1) {
      ChatMessageType var2 = ChatMessageType.of(this.field724 * -601203557);
      if ((var2 == ChatMessageType.CLAN_CHAT || var2 == ChatMessageType.CLAN_MESSAGE) && this.field731 != null && this.field731.startsWith("|")) {
         this.field731 = "|" + var1;
      } else {
         this.field731 = var1;
      }
   }

   public int getId() {
      return this.field727 * 707758669;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   void method1477() {
      this.field722 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   void method1478() {
      this.field722 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("ao")
   void method1463(int var1, String var2, String var3, String var4) {
      int var5 = (classCN.field1170 += 544858879) * -599409003 - 1;
      this.field727 = -1095037074 * var5;
      this.field725 = client.field856 * 1149202047;
      this.field724 = var1 * 302524819;
      this.field729 = var2;
      this.method1488(-1168561304);
      this.field730 = var3;
      this.field731 = var4;
      this.method1464(1287164533);
      this.method1479(-436229319);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ah")
   final boolean method1483() {
      if (classRC.field5638 == this.field722) {
         method1487(this, -1625357087);
      }

      return this.field722 == classRC.field5640;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   void method1466() {
      this.field723 = classRC.field5638;
   }

   public void setSender(String var1) {
      this.field730 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bx")
   final boolean method1484() {
      if (classRC.field5638 == this.field722) {
         method1487(this, -2086972625);
      }

      return this.field722 == classRC.field5640;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ab")
   void method1473(byte var1) {
      try {
         classRC var10001;
         if (NameableContainer.method10250(classQQ.field5595.field593, this.field728, (byte)10)) {
            if (var1 >= 2) {
               throw new IllegalStateException();
            }

            var10001 = classRC.field5640;
         } else {
            var10001 = classRC.field5639;
         }

         this.field723 = var10001;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cg.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("br")
   final void method1490() {
      if (null != this.field729) {
         this.field728 = new classAAE(classKW.method6620(this.field729, 1572821090), WidgetNode.field5277);
      } else {
         this.field728 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   void method1479(int var1) {
      try {
         this.field722 = classRC.field5638;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cg.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcg;I)V")
   @ObfuscatedName("md")
   public static void method1487(MessageNode var0, int var1) {
      if (var0 == null) {
         var0.method1480(var1);
      } else {
         try {
            classRC var10001;
            if (NameableContainer.method10250(classQQ.field5595.field591, var0.field728, (byte)10)) {
               if (var1 >= -1507017492) {
                  throw new IllegalStateException();
               }

               var10001 = classRC.field5640;
            } else {
               var10001 = classRC.field5639;
            }

            var0.field722 = var10001;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "cg.ax(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   final boolean method1470() {
      if (this.field723 == classRC.field5638) {
         this.method1473((byte)-75);
      }

      return classRC.field5640 == this.field723;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcg;)Z")
   @ObfuscatedName("tq")
   public static boolean method1485(MessageNode var0) {
      if (classRC.field5638 == var0.field722) {
         method1487(var0, -1605558729);
      }

      return var0.field722 == classRC.field5640;
   }

   public ChatMessageType getType() {
      ChatMessageType var1 = ChatMessageType.of(this.field724 * -601203557);
      if (var1 == ChatMessageType.CLAN_CHAT) {
         if (this.field731 != null && this.field731.startsWith("|")) {
            return ChatMessageType.CLAN_GIM_CHAT;
         }
      } else if (var1 == ChatMessageType.CLAN_MESSAGE && this.field731 != null && this.field731.startsWith("|")) {
         return ChatMessageType.CLAN_GIM_MESSAGE;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("hz")
   public static boolean method1494(byte var0) {
      try {
         boolean var10000;
         if (client.field912 * 313368463 >= 2) {
            if (var0 >= 3) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "cg.hz(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("kw")
   public void method1495(int var1, String var2, String var3, String var4) {
      this.field732 = null;
      this.field721 = (int)(System.currentTimeMillis() / 1000L);
      if (var2 != null) {
         int var5 = var2.lastIndexOf(62);
         if (var5 != -1) {
            this.field728 = new classAAE(var2.substring(var5 + 1), WidgetNode.field5277);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Z")
   @ObfuscatedName("af")
   static boolean method1492(String var0, int var1) {
      try {
         if (var0 == null) {
            if (var1 == -885661276) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            try {
               new URL(var0);
               return true;
            } catch (MalformedURLException var3) {
               return false;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cg.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcg;)V")
   @ObfuscatedName("mq")
   public static void method1474(MessageNode var0) {
      var0.field723 = NameableContainer.method10250(classQQ.field5595.field593, var0.field728, (byte)10) ? classRC.field5640 : classRC.field5639;
   }

   public String getValue() {
      if (this.field731 != null && this.field731.startsWith("|")) {
         ChatMessageType var1 = ChatMessageType.of(this.field724 * -601203557);
         if (var1 == ChatMessageType.CLAN_CHAT || var1 == ChatMessageType.CLAN_MESSAGE) {
            return this.field731.substring(1);
         }
      }

      return this.field731;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcg;)V")
   @ObfuscatedName("lb")
   public static void method1491(MessageNode var0) {
      if (null != var0.field729) {
         var0.field728 = new classAAE(classKW.method6620(var0.field729, -1026176828), WidgetNode.field5277);
      } else {
         var0.field728 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   void method1480(int var1) {
      try {
         this.field723 = classRC.field5639;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cg.ag(" + ')');
      }
   }
}
