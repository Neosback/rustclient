import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pc")
public class classPC extends classVJ {
   @ObfuscatedName("as")
   public final int[] field5213;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field5218 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5219 = 7;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ag")
   static NodeCache field5217 = new NodeCache(8);
   @ObfuscatedName("ax")
   public final int[] field5212;
   @ObfuscatedName("ac")
   public final int[] field5211;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ab")
   public static IndexDataBase field5220;
   @ObfuscatedName("aa")
   public final Object[] field5214;
   @ObfuscatedName("ao")
   public final Object[] field5215;
   @ObfuscatedName("al")
   public final Object[] field5216;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpc;")
   @ObfuscatedName("ae")
   public static classPC method8870(int var0) {
      classPC var1 = (classPC)NodeCache.method6460(field5217, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5220.method11942(75, var0, -1924681401);
         var1 = new classPC(new classXY(var2), var0);
         field5217.method6464(var1, var0);
         return var1;
      }
   }

   classPC(classXY var1, int var2) {
      int[] var3 = new int[0];
      int[] var4 = new int[0];
      int[] var5 = new int[0];
      Object[] var6 = new Object[0];
      Object[] var7 = new Object[0];
      Object[] var8 = new Object[0];
      if (var1 != null && var1.field6952 != null) {
         label60:
         while (true) {
            int var9 = var1.method13095(-346779531);
            switch (var9) {
               case 0:
                  break label60;
               case 7:
                  var3 = new int[var1.method13099(-394615724)];
                  var6 = new Object[var3.length];

                  for (int var12 = 0; var12 < var3.length; var12++) {
                     var3[var12] = var1.method13158((byte)46);
                     var6[var12] = classYY.method13827(FriendContainer.method10175(var3[var12], 677004428), var1, 827988912);
                  }
                  break;
               case 8:
                  var4 = new int[var1.method13099(-1086424955)];
                  var7 = new Object[var4.length];

                  for (int var11 = 0; var11 < var4.length; var11++) {
                     var4[var11] = var1.method13158((byte)37);
                     var7[var11] = classYY.method13827(FriendContainer.method10175(var4[var11], 265449944), var1, 827988912);
                  }
                  break;
               case 11:
                  var5 = new int[var1.method13099(-1443937456)];
                  var8 = new Object[var5.length];

                  for (int var10 = 0; var10 < var5.length; var10++) {
                     var5[var10] = var1.method13158((byte)23);
                     var8[var10] = classYY.method13827(FriendContainer.method10175(var5[var10], 1939035565), var1, 827988912);
                  }
            }
         }
      }

      this.field5213 = var3;
      this.field5212 = var4;
      this.field5211 = var5;
      this.field5214 = var6;
      this.field5215 = var7;
      this.field5216 = var8;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpc;")
   @ObfuscatedName("ab")
   public static classPC method8871(int var0) {
      classPC var1 = (classPC)NodeCache.method6460(field5217, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5220.method11942(75, var0, -619608434);
         var1 = new classPC(new classXY(var2), var0);
         field5217.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ag")
   public static void method8872() {
      field5217.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   public static void method8873() {
      field5217.method6468();
   }

   @ObfuscatedSignature(descriptor = "(ILky;ZI)V")
   @ObfuscatedName("ck")
   static void method8875(int var0, classKY var1, boolean var2, int var3) {
      try {
         RenderOverview var4 = classIO.method6272((byte)-93);
         WorldMapData var5 = var4.method12558(var0, 1778951456);
         classKY var6 = classOA.method8320(1544651080);
         RenderOverview.method12513(var4, var5, var6, var1, var2, (byte)87);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "pc.ck(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;Ljava/lang/String;I)V")
   @ObfuscatedName("aa")
   static void method8874(classVP var0, String var1, int var2) {
      try {
         classDT var3 = new classDT(var0, var1);
         client.field1003.add(var3);
         client.field1005 = client.field1005 + var3.field1624 * 2074986393;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pc.aa(" + ')');
      }
   }
}
