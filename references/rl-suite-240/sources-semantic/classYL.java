import java.util.ArrayList;
import java.util.List;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yl")
public class classYL {
   @ObfuscatedName("af")
   static int field6998;
   @ObfuscatedName("ab")
   public static int[] field7000;
   @ObfuscatedName("ae")
   static int field6997;
   @ObfuscatedName("ag")
   public static int[] field7001;
   @ObfuscatedName("az")
   static int field6999;
   @ObfuscatedName("aa")
   public static byte[][] field7002;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Ljava/lang/String;Ljava/lang/String;)Lyz;")
   @ObfuscatedName("av")
   public static IndexedSprite method13461(IndexDataBase var0, String var1, String var2) {
      if (!IndexDataBase.method11923(var0, var1, var2, 895198534)) {
         return null;
      } else {
         int var3 = IndexDataBase.method11918(var0, var1, -520753992);
         int var4 = var0.method11922(var3, var2, (byte)-98);
         return classTH.method10857(var0, var3, var4, 807518710);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;II)Lyz;")
   @ObfuscatedName("aq")
   static IndexedSprite method13453(IndexDataBase var0, int var1, int var2) {
      if (!classHK.method6100(var0, var1, var2, (byte)-122)) {
         return null;
      } else {
         IndexedSprite var4 = new IndexedSprite();
         var4.field7122 = field6998 * -1073038619;
         var4.field7118 = field6997 * 1770455687;
         var4.field7120 = field7000[0];
         var4.field7121 = field7001[0];
         var4.field7119 = EntityOps.field5369[0];
         var4.field7117 = classGG.field2576[0];
         var4.field7124 = classRB.field5637;
         var4.field7123 = field7002[0];
         classVZ.method12347((byte)20);
         return var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;II)Lyz;")
   @ObfuscatedName("ad")
   static IndexedSprite method13454(IndexDataBase var0, int var1, int var2) {
      if (!classHK.method6100(var0, var1, var2, (byte)-34)) {
         return null;
      } else {
         IndexedSprite var4 = new IndexedSprite();
         var4.field7122 = field6998 * -807631027;
         var4.field7118 = field6997 * 1824918833;
         var4.field7120 = field7000[0];
         var4.field7121 = field7001[0];
         var4.field7119 = EntityOps.field5369[0];
         var4.field7117 = classGG.field2576[0];
         var4.field7124 = classRB.field5637;
         var4.field7123 = field7002[0];
         classVZ.method12347((byte)72);
         return var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bi")
   public static void method13482() {
      field7000 = null;
      field7001 = null;
      EntityOps.field5369 = null;
      classGG.field2576 = null;
      classRB.field5637 = null;
      field7002 = (byte[][])null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;II)[Lym;")
   @ObfuscatedName("au")
   public static SpritePixels[] method13455(IndexDataBase var0, int var1, int var2) {
      if (!classHK.method6100(var0, var1, var2, (byte)-3)) {
         return null;
      } else {
         SpritePixels[] var4 = new SpritePixels[1616155503 * field6999];

         for (int var5 = 0; var5 < field6999 * -511692290; var5++) {
            SpritePixels var6 = var4[var5] = new SpritePixels();
            var6.field7004 = -1073038619 * field6998;
            var6.field7006 = field6997 * 322720053;
            var6.field7008 = field7000[var5];
            var6.field7009 = field7001[var5];
            var6.field7005 = EntityOps.field5369[var5];
            var6.field7007 = classGG.field2576[var5];
            int var7 = var6.field7007 * var6.field7005;
            byte[] var8 = field7002[var5];
            var6.field7003 = new int[var7];

            for (int var9 = 0; var9 < var7; var9++) {
               int var10 = var8[var9] & 994095698;
               var6.field7003[var9] = classRB.field5637[var10];
            }
         }

         classVZ.method12347((byte)-2);
         return var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;II)Lym;")
   @ObfuscatedName("ai")
   public static SpritePixels method13457(IndexDataBase var0, int var1, int var2) {
      return !classHK.method6100(var0, var1, var2, (byte)-21) ? null : classSA.method10408((byte)-54);
   }

   classYL() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Ljava/lang/String;Ljava/lang/String;)[Lyz;")
   @ObfuscatedName("aw")
   public static IndexedSprite[] method13459(IndexDataBase var0, String var1, String var2) {
      if (!IndexDataBase.method11923(var0, var1, var2, 1011275913)) {
         return null;
      } else {
         int var3 = IndexDataBase.method11918(var0, var1, -705410592);
         int var4 = var0.method11922(var3, var2, (byte)-112);
         IndexedSprite[] var5;
         if (!classHK.method6100(var0, var3, var4, (byte)-108)) {
            var5 = null;
         } else {
            var5 = classFS.method4999((byte)-72);
         }

         return var5;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Ljava/lang/String;Ljava/lang/String;)[Lyz;")
   @ObfuscatedName("ak")
   public static IndexedSprite[] method13460(IndexDataBase var0, String var1, String var2) {
      if (!IndexDataBase.method11923(var0, var1, var2, 1735022414)) {
         return null;
      } else {
         int var3 = IndexDataBase.method11918(var0, var1, 1423201720);
         int var4 = var0.method11922(var3, var2, (byte)-56);
         IndexedSprite[] var5;
         if (!classHK.method6100(var0, var3, var4, (byte)1)) {
            var5 = null;
         } else {
            var5 = classFS.method4999((byte)69);
         }

         return var5;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;Ljava/lang/String;Ljava/lang/String;)Lzv;")
   @ObfuscatedName("an")
   public static classZV method13464(IndexDataBase var0, IndexDataBase var1, String var2, String var3) {
      if (!IndexDataBase.method11923(var0, var2, var3, 2110361279)) {
         return null;
      } else {
         int var4 = IndexDataBase.method11918(var0, var2, 2083537609);
         int var5 = var0.method11922(var4, var3, (byte)-11);
         classZV var6;
         if (!classHK.method6100(var0, var4, var5, (byte)-99)) {
            var6 = null;
         } else {
            byte[] var8 = var1.method11942(var4, var5, -1355610797);
            classZV var7;
            if (var8 == null) {
               var7 = null;
            } else {
               classZV var9 = new classZV(var8, field7000, field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, field7002);
               classVZ.method12347((byte)-5);
               var7 = var9;
            }

            var6 = var7;
         }

         return var6;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Ljava/lang/String;Ljava/lang/String;)Lyz;")
   @ObfuscatedName("at")
   public static IndexedSprite method13462(IndexDataBase var0, String var1, String var2) {
      if (!IndexDataBase.method11923(var0, var1, var2, 1663310988)) {
         return null;
      } else {
         int var3 = IndexDataBase.method11918(var0, var1, 455923992);
         int var4 = var0.method11922(var3, var2, (byte)-36);
         return classTH.method10857(var0, var3, var4, -1730303153);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("by")
   public static void method13483() {
      field7000 = null;
      field7001 = null;
      EntityOps.field5369 = null;
      classGG.field2576 = null;
      classRB.field5637 = null;
      field7002 = (byte[][])null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Ljava/util/ArrayList;Ljava/util/List;)V")
   @ObfuscatedName("qv")
   public static void method13463(WorldMapRegion var0, ArrayList var1, List var2) {
      var0.field2657.clear();

      for (classGF var4 : var1) {
         if (var4.method6014((byte)15) == var0.field2660 * -551401517 && var4.method6016((byte)-80) == var0.field2654 * 1312423097) {
            var0.field2655.add(var4);
         }
      }

      var0.method5670(var2, (byte)12);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;Ljava/lang/String;Ljava/lang/String;)Lzv;")
   @ObfuscatedName("am")
   public static classZV method13465(IndexDataBase var0, IndexDataBase var1, String var2, String var3) {
      if (!IndexDataBase.method11923(var0, var2, var3, 823422888)) {
         return null;
      } else {
         int var4 = IndexDataBase.method11918(var0, var2, 1595147653);
         int var5 = var0.method11922(var4, var3, (byte)-2);
         classZV var6;
         if (!classHK.method6100(var0, var4, var5, (byte)-40)) {
            var6 = null;
         } else {
            byte[] var8 = var1.method11942(var4, var5, -1039067449);
            classZV var7;
            if (var8 == null) {
               var7 = null;
            } else {
               classZV var9 = new classZV(var8, field7000, field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, field7002);
               classVZ.method12347((byte)15);
               var7 = var9;
            }

            var6 = var7;
         }

         return var6;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;Ljava/lang/String;Ljava/lang/String;)Lzv;")
   @ObfuscatedName("ah")
   public static classZV method13466(IndexDataBase var0, IndexDataBase var1, String var2, String var3) {
      if (!IndexDataBase.method11923(var0, var2, var3, 795297944)) {
         return null;
      } else {
         int var4 = IndexDataBase.method11918(var0, var2, 1479060711);
         int var5 = var0.method11922(var4, var3, (byte)-108);
         classZV var6;
         if (!classHK.method6100(var0, var4, var5, (byte)-94)) {
            var6 = null;
         } else {
            byte[] var8 = var1.method11942(var4, var5, -1781382106);
            classZV var7;
            if (var8 == null) {
               var7 = null;
            } else {
               classZV var9 = new classZV(var8, field7000, field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, field7002);
               classVZ.method12347((byte)23);
               var7 = var9;
            }

            var6 = var7;
         }

         return var6;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;I)Lyz;")
   @ObfuscatedName("bn")
   public static IndexedSprite method13467(IndexDataBase var0, int var1) {
      if (!classLN.method7170(var0, var1, (byte)0)) {
         return null;
      } else {
         IndexedSprite var3 = new IndexedSprite();
         var3.field7122 = field6998 * 1224052395;
         var3.field7118 = field6997 * -1062567517;
         var3.field7120 = field7000[0];
         var3.field7121 = field7001[0];
         var3.field7119 = EntityOps.field5369[0];
         var3.field7117 = classGG.field2576[0];
         var3.field7124 = classRB.field5637;
         var3.field7123 = field7002[0];
         classVZ.method12347((byte)-2);
         return var3;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;I)Lyz;")
   @ObfuscatedName("bx")
   public static IndexedSprite method13468(IndexDataBase var0, int var1) {
      if (!classLN.method7170(var0, var1, (byte)0)) {
         return null;
      } else {
         IndexedSprite var3 = new IndexedSprite();
         var3.field7122 = field6998 * -1300955236;
         var3.field7118 = field6997 * -1169812479;
         var3.field7120 = field7000[0];
         var3.field7121 = field7001[0];
         var3.field7119 = EntityOps.field5369[0];
         var3.field7117 = classGG.field2576[0];
         var3.field7124 = classRB.field5637;
         var3.field7123 = field7002[0];
         classVZ.method12347((byte)48);
         return var3;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lyz;")
   @ObfuscatedName("bc")
   public static IndexedSprite[] method13469() {
      IndexedSprite[] var0 = new IndexedSprite[field6999 * 1616155503];

      for (int var1 = 0; var1 < field6999 * 789071698; var1++) {
         IndexedSprite var2 = var0[var1] = new IndexedSprite();
         var2.field7122 = field6998 * 413808059;
         var2.field7118 = 669627286 * field6997;
         var2.field7120 = field7000[var1];
         var2.field7121 = field7001[var1];
         var2.field7119 = EntityOps.field5369[var1];
         var2.field7117 = classGG.field2576[var1];
         var2.field7124 = classRB.field5637;
         var2.field7123 = field7002[var1];
      }

      classVZ.method12347((byte)48);
      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lyz;")
   @ObfuscatedName("bp")
   public static IndexedSprite[] method13470() {
      IndexedSprite[] var0 = new IndexedSprite[field6999 * 1616155503];

      for (int var1 = 0; var1 < field6999 * 1040868257; var1++) {
         IndexedSprite var2 = var0[var1] = new IndexedSprite();
         var2.field7122 = field6998 * -811801536;
         var2.field7118 = -72144473 * field6997;
         var2.field7120 = field7000[var1];
         var2.field7121 = field7001[var1];
         var2.field7119 = EntityOps.field5369[var1];
         var2.field7117 = classGG.field2576[var1];
         var2.field7124 = classRB.field5637;
         var2.field7123 = field7002[var1];
      }

      classVZ.method12347((byte)43);
      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("br")
   static SpritePixels method13471() {
      SpritePixels var0 = new SpritePixels();
      var0.field7004 = field6998 * -1073038619;
      var0.field7006 = field6997 * -1062567517;
      var0.field7008 = field7000[0];
      var0.field7009 = field7001[0];
      var0.field7005 = EntityOps.field5369[0];
      var0.field7007 = classGG.field2576[0];
      int var1 = var0.field7007 * var0.field7005;
      byte[] var2 = field7002[0];
      var0.field7003 = new int[var1];

      for (int var3 = 0; var3 < var1; var3++) {
         var0.field7003[var3] = classRB.field5637[var2[var3] & 255];
      }

      classVZ.method12347((byte)39);
      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("bm")
   static SpritePixels method13472() {
      SpritePixels var0 = new SpritePixels();
      var0.field7004 = field6998 * -1073038619;
      var0.field7006 = field6997 * -1062567517;
      var0.field7008 = field7000[0];
      var0.field7009 = field7001[0];
      var0.field7005 = EntityOps.field5369[0];
      var0.field7007 = classGG.field2576[0];
      int var1 = var0.field7007 * var0.field7005;
      byte[] var2 = field7002[0];
      var0.field7003 = new int[var1];

      for (int var3 = 0; var3 < var1; var3++) {
         var0.field7003[var3] = classRB.field5637[var2[var3] & 255];
      }

      classVZ.method12347((byte)-89);
      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("ba")
   static void method13477(byte[] var0) {
      classXY var1 = new classXY(var0);
      var1.field6953 = (var0.length - 2) * 2053553688;
      field6999 = var1.method13099(18008090) * 130492380;
      field7000 = new int[1616155503 * field6999];
      field7001 = new int[field6999 * -152914609];
      EntityOps.field5369 = new int[1616155503 * field6999];
      classGG.field2576 = new int[403012285 * field6999];
      field7002 = new byte[field6999 * 1559992669][];
      var1.field6953 = 659177515 * (var0.length - 7 - field6999 * -2107671026);
      field6998 = var1.method13099(-1607706557) * 1527476973;
      field6997 = var1.method13099(-1669447094) * 1769247414;
      int var2 = (var1.method13095(-346779531) & -982406193) + 1;

      for (int var3 = 0; var3 < field6999 * 2064483529; var3++) {
         field7000[var3] = var1.method13099(1619527892);
      }

      for (int var13 = 0; var13 < field6999 * 320654209; var13++) {
         field7001[var13] = var1.method13099(-1189459514);
      }

      for (int var14 = 0; var14 < field6999 * 1616155503; var14++) {
         EntityOps.field5369[var14] = var1.method13099(978824665);
      }

      for (int var15 = 0; var15 < field6999 * -749412981; var15++) {
         classGG.field2576[var15] = var1.method13099(-1402523735);
      }

      var1.field6953 = (var0.length - 7 - field6999 * -482586195 - (var2 - 1) * 3) * -1095856699;
      classRB.field5637 = new int[var2];

      for (int var16 = 1; var16 < var2; var16++) {
         classRB.field5637[var16] = var1.method13105(-758448158);
         if (0 == classRB.field5637[var16]) {
            classRB.field5637[var16] = 1;
         }
      }

      var1.field6953 = 0;

      for (int var17 = 0; var17 < field6999 * 1616155503; var17++) {
         int var4 = EntityOps.field5369[var17];
         int var5 = classGG.field2576[var17];
         int var6 = var4 * var5;
         byte[] var7 = new byte[var6];
         field7002[var17] = var7;
         int var8 = var1.method13095(-346779531);
         boolean var9 = (var8 & 1) == 1;
         boolean var10 = 2 == (var8 & 2);
         if (!var9) {
            for (int var18 = 0; var18 < var6; var18++) {
               var7[var18] = var1.method13097((byte)17);
            }
         } else {
            for (int var11 = 0; var11 < var4; var11++) {
               for (int var12 = 0; var12 < var5; var12++) {
                  var7[var11 + var4 * var12] = var1.method13097((byte)17);
               }
            }
         }

         if (var10) {
            var1.field6953 += -1095856699 * var6;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;I)Z")
   @ObfuscatedName("bg")
   static boolean method13474(IndexDataBase var0, int var1) {
      byte[] var2 = IndexDataBase.method11891(var0, var1, 1080224828);
      if (var2 == null) {
         return false;
      } else {
         classYS.method13666(var2, -1108124092);
         return true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;I)Z")
   @ObfuscatedName("bj")
   static boolean method13475(IndexDataBase var0, int var1) {
      byte[] var2 = IndexDataBase.method11891(var0, var1, -1201729758);
      if (var2 == null) {
         return false;
      } else {
         classYS.method13666(var2, -1108124092);
         return true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;I)Z")
   @ObfuscatedName("bk")
   static boolean method13476(IndexDataBase var0, int var1) {
      byte[] var2 = IndexDataBase.method11891(var0, var1, -917323686);
      if (var2 == null) {
         return false;
      } else {
         classYS.method13666(var2, -1108124092);
         return true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("bo")
   static void method13478(byte[] var0) {
      classXY var1 = new classXY(var0);
      var1.field6953 = (var0.length - 2) * 1601993802;
      field6999 = var1.method13099(-1901436227) * -931176980;
      field7000 = new int[1616155503 * field6999];
      field7001 = new int[field6999 * 1616155503];
      EntityOps.field5369 = new int[1198446922 * field6999];
      classGG.field2576 = new int[1616155503 * field6999];
      field7002 = new byte[field6999 * 783193958][];
      var1.field6953 = -1894381068 * (var0.length - 7 - field6999 * 44342136);
      field6998 = var1.method13099(-542899814) * -1821953140;
      field6997 = var1.method13099(1694860603) * 1330952715;
      int var2 = (var1.method13095(-346779531) & 0xFF) + 1;

      for (int var3 = 0; var3 < field6999 * 1616155503; var3++) {
         field7000[var3] = var1.method13099(908218162);
      }

      for (int var13 = 0; var13 < field6999 * 1196463563; var13++) {
         field7001[var13] = var1.method13099(1752261971);
      }

      for (int var14 = 0; var14 < field6999 * 219892676; var14++) {
         EntityOps.field5369[var14] = var1.method13099(-816174142);
      }

      for (int var15 = 0; var15 < field6999 * 1371418118; var15++) {
         classGG.field2576[var15] = var1.method13099(-1453648936);
      }

      var1.field6953 = (var0.length - 7 - field6999 * 44342136 - (var2 - 1) * 3) * -70107574;
      classRB.field5637 = new int[var2];

      for (int var16 = 1; var16 < var2; var16++) {
         classRB.field5637[var16] = var1.method13105(-758448158);
         if (0 == classRB.field5637[var16]) {
            classRB.field5637[var16] = 1;
         }
      }

      var1.field6953 = 0;

      for (int var17 = 0; var17 < field6999 * 1616155503; var17++) {
         int var4 = EntityOps.field5369[var17];
         int var5 = classGG.field2576[var17];
         int var6 = var4 * var5;
         byte[] var7 = new byte[var6];
         field7002[var17] = var7;
         int var8 = var1.method13095(-346779531);
         boolean var9 = (var8 & 1) == 1;
         boolean var10 = 2 == (var8 & 2);
         if (!var9) {
            for (int var18 = 0; var18 < var6; var18++) {
               var7[var18] = var1.method13097((byte)17);
            }
         } else {
            for (int var11 = 0; var11 < var4; var11++) {
               for (int var12 = 0; var12 < var5; var12++) {
                  var7[var11 + var4 * var12] = var1.method13097((byte)17);
               }
            }
         }

         if (var10) {
            var1.field6953 += -1212672062 * var6;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("bu")
   static void method13479(byte[] var0) {
      classXY var1 = new classXY(var0);
      var1.field6953 = (var0.length - 2) * -1095856699;
      field6999 = var1.method13099(943946862) * 502976131;
      field7000 = new int[-183080607 * field6999];
      field7001 = new int[field6999 * 1616155503];
      EntityOps.field5369 = new int[1616155503 * field6999];
      classGG.field2576 = new int[973199484 * field6999];
      field7002 = new byte[field6999 * -2017134195][];
      var1.field6953 = -1095856699 * (var0.length - 7 - field6999 * -992851953);
      field6998 = var1.method13099(-768676231) * 703458394;
      field6997 = var1.method13099(340122354) * 1330952715;
      int var2 = (var1.method13095(-346779531) & 1436952347) + 1;

      for (int var3 = 0; var3 < field6999 * -505134487; var3++) {
         field7000[var3] = var1.method13099(666512451);
      }

      for (int var13 = 0; var13 < field6999 * -236368806; var13++) {
         field7001[var13] = var1.method13099(-751040237);
      }

      for (int var14 = 0; var14 < field6999 * 149113308; var14++) {
         EntityOps.field5369[var14] = var1.method13099(-2097997327);
      }

      for (int var15 = 0; var15 < field6999 * 1616155503; var15++) {
         classGG.field2576[var15] = var1.method13099(601903699);
      }

      var1.field6953 = (var0.length - 7 - field6999 * 44342136 - (var2 - 1) * 3) * -1095856699;
      classRB.field5637 = new int[var2];

      for (int var16 = 1; var16 < var2; var16++) {
         classRB.field5637[var16] = var1.method13105(-758448158);
         if (0 == classRB.field5637[var16]) {
            classRB.field5637[var16] = 1;
         }
      }

      var1.field6953 = 0;

      for (int var17 = 0; var17 < field6999 * 1616155503; var17++) {
         int var4 = EntityOps.field5369[var17];
         int var5 = classGG.field2576[var17];
         int var6 = var4 * var5;
         byte[] var7 = new byte[var6];
         field7002[var17] = var7;
         int var8 = var1.method13095(-346779531);
         boolean var9 = (var8 & 1) == 1;
         boolean var10 = 2 == (var8 & 2);
         if (!var9) {
            for (int var18 = 0; var18 < var6; var18++) {
               var7[var18] = var1.method13097((byte)17);
            }
         } else {
            for (int var11 = 0; var11 < var4; var11++) {
               for (int var12 = 0; var12 < var5; var12++) {
                  var7[var11 + var4 * var12] = var1.method13097((byte)17);
               }
            }
         }

         if (var10) {
            var1.field6953 += 1248587121 * var6;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("bb")
   static void method13480(byte[] var0) {
      classXY var1 = new classXY(var0);
      var1.field6953 = (var0.length - 2) * -1095856699;
      field6999 = var1.method13099(68052884) * 1826761615;
      field7000 = new int[1616155503 * field6999];
      field7001 = new int[field6999 * 1616155503];
      EntityOps.field5369 = new int[1616155503 * field6999];
      classGG.field2576 = new int[1616155503 * field6999];
      field7002 = new byte[field6999 * 1616155503][];
      var1.field6953 = -1095856699 * (var0.length - 7 - field6999 * 44342136);
      field6998 = var1.method13099(-1401084917) * 1527476973;
      field6997 = var1.method13099(1300250818) * 1330952715;
      int var2 = (var1.method13095(-346779531) & 0xFF) + 1;

      for (int var3 = 0; var3 < field6999 * 1616155503; var3++) {
         field7000[var3] = var1.method13099(769479827);
      }

      for (int var13 = 0; var13 < field6999 * 1616155503; var13++) {
         field7001[var13] = var1.method13099(-1668468611);
      }

      for (int var14 = 0; var14 < field6999 * 1616155503; var14++) {
         EntityOps.field5369[var14] = var1.method13099(-1612178738);
      }

      for (int var15 = 0; var15 < field6999 * 1616155503; var15++) {
         classGG.field2576[var15] = var1.method13099(442670040);
      }

      var1.field6953 = (var0.length - 7 - field6999 * 44342136 - (var2 - 1) * 3) * -1095856699;
      classRB.field5637 = new int[var2];

      for (int var16 = 1; var16 < var2; var16++) {
         classRB.field5637[var16] = var1.method13105(-758448158);
         if (0 == classRB.field5637[var16]) {
            classRB.field5637[var16] = 1;
         }
      }

      var1.field6953 = 0;

      for (int var17 = 0; var17 < field6999 * 1616155503; var17++) {
         int var4 = EntityOps.field5369[var17];
         int var5 = classGG.field2576[var17];
         int var6 = var4 * var5;
         byte[] var7 = new byte[var6];
         field7002[var17] = var7;
         int var8 = var1.method13095(-346779531);
         boolean var9 = (var8 & 1) == 1;
         boolean var10 = 2 == (var8 & 2);
         if (!var9) {
            for (int var18 = 0; var18 < var6; var18++) {
               var7[var18] = var1.method13097((byte)17);
            }
         } else {
            for (int var11 = 0; var11 < var4; var11++) {
               for (int var12 = 0; var12 < var5; var12++) {
                  var7[var11 + var4 * var12] = var1.method13097((byte)17);
               }
            }
         }

         if (var10) {
            var1.field6953 += -1095856699 * var6;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;II)Lym;")
   @ObfuscatedName("ar")
   public static SpritePixels method13458(IndexDataBase var0, int var1, int var2) {
      return !classHK.method6100(var0, var1, var2, (byte)-79) ? null : classSA.method10408((byte)6);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("be")
   public static void method13484() {
      field7000 = null;
      field7001 = null;
      EntityOps.field5369 = null;
      classGG.field2576 = null;
      classRB.field5637 = null;
      field7002 = (byte[][])null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("bw")
   static void method13481(byte[] var0) {
      classXY var1 = new classXY(var0);
      var1.field6953 = (var0.length - 2) * -1240084969;
      field6999 = var1.method13099(1632575619) * 1826761615;
      field7000 = new int[414299968 * field6999];
      field7001 = new int[field6999 * 143589938];
      EntityOps.field5369 = new int[-894063266 * field6999];
      classGG.field2576 = new int[1360867942 * field6999];
      field7002 = new byte[field6999 * 1616155503][];
      var1.field6953 = -2098783334 * (var0.length - 7 - field6999 * 739361259);
      field6998 = var1.method13099(834313450) * 1527476973;
      field6997 = var1.method13099(-1362278303) * 413996496;
      int var2 = (var1.method13095(-346779531) & -911573328) + 1;

      for (int var3 = 0; var3 < field6999 * 1953080487; var3++) {
         field7000[var3] = var1.method13099(-882835619);
      }

      for (int var13 = 0; var13 < field6999 * 1616155503; var13++) {
         field7001[var13] = var1.method13099(1398469454);
      }

      for (int var14 = 0; var14 < field6999 * 1616155503; var14++) {
         EntityOps.field5369[var14] = var1.method13099(-1909030951);
      }

      for (int var15 = 0; var15 < field6999 * 1616155503; var15++) {
         classGG.field2576[var15] = var1.method13099(638896044);
      }

      var1.field6953 = (var0.length - 7 - field6999 * 44342136 - (var2 - 1) * 3) * -1095856699;
      classRB.field5637 = new int[var2];

      for (int var16 = 1; var16 < var2; var16++) {
         classRB.field5637[var16] = var1.method13105(-758448158);
         if (0 == classRB.field5637[var16]) {
            classRB.field5637[var16] = 1;
         }
      }

      var1.field6953 = 0;

      for (int var17 = 0; var17 < field6999 * 1616155503; var17++) {
         int var4 = EntityOps.field5369[var17];
         int var5 = classGG.field2576[var17];
         int var6 = var4 * var5;
         byte[] var7 = new byte[var6];
         field7002[var17] = var7;
         int var8 = var1.method13095(-346779531);
         boolean var9 = (var8 & 1) == 1;
         boolean var10 = 2 == (var8 & 2);
         if (!var9) {
            for (int var18 = 0; var18 < var6; var18++) {
               var7[var18] = var1.method13097((byte)17);
            }
         } else {
            for (int var11 = 0; var11 < var4; var11++) {
               for (int var12 = 0; var12 < var5; var12++) {
                  var7[var11 + var4 * var12] = var1.method13097((byte)17);
               }
            }
         }

         if (var10) {
            var1.field6953 += -1095856699 * var6;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;II)[Lym;")
   @ObfuscatedName("ap")
   public static SpritePixels[] method13456(IndexDataBase var0, int var1, int var2) {
      if (!classHK.method6100(var0, var1, var2, (byte)-83)) {
         return null;
      } else {
         SpritePixels[] var4 = new SpritePixels[1616155503 * field6999];

         for (int var5 = 0; var5 < field6999 * 1616155503; var5++) {
            SpritePixels var6 = var4[var5] = new SpritePixels();
            var6.field7004 = -1073038619 * field6998;
            var6.field7006 = field6997 * -1062567517;
            var6.field7008 = field7000[var5];
            var6.field7009 = field7001[var5];
            var6.field7005 = EntityOps.field5369[var5];
            var6.field7007 = classGG.field2576[var5];
            int var7 = var6.field7007 * var6.field7005;
            byte[] var8 = field7002[var5];
            var6.field7003 = new int[var7];

            for (int var9 = 0; var9 < var7; var9++) {
               int var10 = var8[var9] & 255;
               var6.field7003[var9] = classRB.field5637[var10];
            }
         }

         classVZ.method12347((byte)84);
         return var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;II)Z")
   @ObfuscatedName("bt")
   public static boolean method13473(IndexDataBase var0, int var1, int var2) {
      byte[] var3 = var0.method11942(var1, var2, -1099767856);
      if (null == var3) {
         return false;
      } else {
         classYS.method13666(var3, -1108124092);
         return true;
      }
   }
}
