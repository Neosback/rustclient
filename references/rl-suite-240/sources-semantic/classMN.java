import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mn")
public class classMN {
   @ObfuscatedName("af")
   public float[] field4539;
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   public static final int field4541 = 46;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4540 = 5;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("aq")
   public static IndexDataBase field4543;
   @ObfuscatedSignature(descriptor = "Ley;")
   @ObfuscatedName("az")
   public classEY field4542;

   @ObfuscatedSignature(descriptor = "(Lsp;Ldz;I)Z")
   @ObfuscatedName("cc")
   static boolean method7666(WorldEntity var0, WorldView var1, int var2) {
      try {
         for (int var3 = 0; var3 < -2087374717 * client.field838.field1417; var3++) {
            if (var2 >= 88068473) {
               throw new IllegalStateException();
            }

            Player var4 = (Player)IndexedObjectSet.method13449(var1.field1692, client.field838.field1421[var3]);
            if (classFA.method4576(var0, var4, -1555620546)) {
               if (var2 >= 88068473) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         for (int var8 = 0; var8 < var1.field1695.method14050((byte)10); var8++) {
            if (var2 >= 88068473) {
               throw new IllegalStateException();
            }

            NPC var10 = (NPC)IndexedObjectSet.method13449(var1.field1696, var1.field1695.method14048(var8, 2081965195));
            if (null != var10) {
               if (var2 >= 88068473) {
                  throw new IllegalStateException();
               }

               if (var10.field1579.method8973(936289412).vmethod546((byte)0)) {
                  if (var2 >= 88068473) {
                     throw new IllegalStateException();
                  }

                  if (classFA.method4576(var0, var10, -1555620546)) {
                     return true;
                  }
               }
            }
         }

         classUQ var9 = PlayerComposition.method6721(var0.field6017, var0.field6024.method8515(-1468800021), -734679843);

         for (int var11 = 0; var11 < var1.field1691.method14050((byte)10); var11++) {
            WorldEntity var5 = (WorldEntity)var1.field1698.method13637(var1.field1691.method14048(var11, 1881451855));
            if (null != var5) {
               if (var2 >= 88068473) {
                  throw new IllegalStateException();
               }

               if (var0 != var5) {
                  if (var2 >= 88068473) {
                     throw new IllegalStateException();
                  }

                  if (1612595797 * client.field856 == var5.field6010.field1687.field2143) {
                     classUQ var6 = PlayerComposition.method6721(var5.field6017, var5.field6024.method8515(1210286274), 181881556);
                     if (var9.method11546(var6, -1807797079)) {
                        if (var2 >= 88068473) {
                           throw new IllegalStateException();
                        }

                        classUQ.method11530(var9, (byte)-71);
                        classUQ.method11530(var6, (byte)-72);
                        return true;
                     }

                     classUQ.method11530(var6, (byte)-116);
                  }
               }
            }
         }

         classUQ.method11530(var9, (byte)-100);
         return false;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "mn.cc(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("wd")
   public static int method7665(classXY var0) {
      return 128 - var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "(ILlw;IIIII[FB)Llw;")
   @ObfuscatedName("az")
   static Widget method7664(int var0, Widget var1, int var2, int var3, int var4, int var5, int var6, float[] var7, byte var8) {
      try {
         Widget var9 = new Widget();
         var9.field4373 = 685068937 * var0;
         var9.field4340 = 498391781 * var1.field4327;
         var9.field4258 = var2 * -1612510305;
         var9.field4273 = true;
         var9.field4262 = var3 * 1632281619;
         var9.field4248 = var4 * 454876301;
         var9.field4249 = var5 * -1430539503;
         var9.field4250 = -565540095 * var6;
         var9.field4246 = (int)(1115597881 * var1.field4247 * var7[0]) * -1076753689;
         var9.field4283 = 1989424741 * (int)(var7[1] * (293773697 * var1.field4257));
         var9.field4253 = 1034606045 * (int)(var1.field4247 * 1115597881 * var7[2]);
         var9.field4333 = -259501473 * (int)(293773697 * var1.field4257 * var7[3]);
         return var9;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "mn.az(" + ')');
      }
   }

   classMN(classMS var1) {
      this.this$0 = var1;
      this.field4539 = new float[4];
   }
}
