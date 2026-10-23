import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ze")
public class DBRowConfig extends classVJ implements net.runelite.api.dbtable.DBRowConfig {
   @ObfuscatedName("ax")
   public final int field7171;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field7173 = 255;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ab")
   static IndexDataBase field7175;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field7172 = 15;
   @ObfuscatedName("as")
   final Object[][] field7170;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field7174 = 4;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ag")
   public static NodeCache field7169 = new NodeCache(64);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ag")
   public static void method14029(IndexDataBase var0) {
      field7175 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ab")
   public static void method14030(IndexDataBase var0) {
      field7175 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("as")
   public static void method14031(IndexDataBase var0) {
      field7175 = var0;
   }

   @ObfuscatedSignature(descriptor = "(I)[Ljava/lang/Object;")
   @ObfuscatedName("tk")
   public Object[] method14041(int var1) {
      return method14036(this, var1, -1748206208);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lze;")
   @ObfuscatedName("ax")
   public static DBRowConfig method14032(int var0) {
      DBRowConfig var1 = (DBRowConfig)NodeCache.method6460(field7169, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field7175.method11942(38, var0, -816950851);
         var1 = new DBRowConfig(new classXY(var2), var0, -1);
         field7169.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lze;II)[Ljava/lang/Object;")
   @ObfuscatedName("pe")
   public static Object[] method14036(DBRowConfig var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method14040(var1, var1);
      } else {
         try {
            if (var0.field7170 == null) {
               if (var2 >= -1668547461) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               return var0.field7170[var1];
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "ze.ae(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lze;")
   @ObfuscatedName("aa")
   public static DBRowConfig method14033(int var0) {
      DBRowConfig var1 = (DBRowConfig)NodeCache.method6460(field7169, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field7175.method11942(38, var0, -1835031851);
         var1 = new DBRowConfig(new classXY(var2), var0, -1);
         field7169.method6464(var1, var0);
         return var1;
      }
   }

   public int getTableID() {
      return this.field7171 * 1026569721;
   }

   @ObfuscatedSignature(descriptor = "(I)[Ljava/lang/Object;")
   @ObfuscatedName("aq")
   public Object[] method14037(int var1) {
      return this.field7170 == null ? null : this.field7170[var1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lze;")
   @ObfuscatedName("ao")
   public static DBRowConfig method14034(int var0) {
      DBRowConfig var1 = (DBRowConfig)NodeCache.method6460(field7169, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field7175.method11942(-1018306527, var0, -866745503);
         var1 = new DBRowConfig(new classXY(var2), var0, -1);
         field7169.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lze;")
   @ObfuscatedName("ac")
   public static DBRowConfig method14035(int var0) {
      DBRowConfig var1 = (DBRowConfig)NodeCache.method6460(field7169, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field7175.method11942(38, var0, -764903838);
         var1 = new DBRowConfig(new classXY(var2), var0, -1);
         field7169.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Ljava/lang/Object;")
   @ObfuscatedName("aj")
   public Object[] method14038(int var1) {
      return this.field7170 == null ? null : this.field7170[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)[Ljava/lang/Object;")
   @ObfuscatedName("ay")
   public Object[] method14039(int var1) {
      return this.field7170 == null ? null : this.field7170[var1];
   }

   DBRowConfig(classXY var1, int var2, int var3) {
      Object[][] var4 = (Object[][])null;
      int[][] var5 = (int[][])null;
      int var6 = -1;
      if (var1 != null && var1.field6952 != null) {
         label80:
         while (true) {
            int var7 = var1.method13095(-346779531);
            switch (var7) {
               case 0:
                  break label80;
               case 1:
               case 2:
               default:
                  break;
               case 3:
                  int var8 = Math.max(var1.method13095(-346779531), var3);
                  var4 = new Object[var8][];
                  var5 = new int[var8][];

                  for (int var9 = var1.method13095(-346779531); 255 != var9; var9 = var1.method13095(-346779531)) {
                     int var10 = var1.method13095(-346779531);
                     int[] var11 = new int[var10];

                     for (int var12 = 0; var12 < var10; var12++) {
                        var11[var12] = var1.method13150((short)23116);
                     }

                     int var15 = var1.method13150((short)24730);
                     Object[] var16 = new Object[var15 * var11.length];

                     for (int var17 = 0; var17 < var15; var17++) {
                        for (int var18 = 0; var18 < var11.length; var18++) {
                           int var19 = var17 * var11.length + var18;
                           classYY var20 = FriendContainer.method10175(var11[var18], 1994570088);
                           var16[var19] = classYY.method13827(var20, var1, 827988912);
                        }
                     }

                     var4[var9] = var16;
                     var5[var9] = var11;
                  }
                  break;
               case 4:
                  var6 = var1.method13176(-1071170881);
            }
         }
      }

      if (var4 == null && var3 > 0) {
         var4 = new Object[var3][];
         var5 = new int[var3][];
      }

      this.field7170 = var4;
      this.field7171 = 586972233 * var6;
   }

   @ObfuscatedSignature(descriptor = "(II)[Ljava/lang/Object;")
   @ObfuscatedName("ae")
   public Object[] method14040(int var1, int var2) {
      try {
         if (this.field7170 == null) {
            if (var2 >= -1668547461) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            return this.field7170[var1];
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ze.ae(" + ')');
      }
   }
}
