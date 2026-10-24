import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("oy")
public class classOY extends classVJ {
   @ObfuscatedName("ag")
   public final int field5191;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field5194 = 5;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ae")
   public static IndexDataBase field5199;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ab")
   public static NodeCache field5192 = new NodeCache(64);
   @ToRemove(unused = "true")
   @ObfuscatedName("bo")
   static final int field5198 = 106;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   static final int field5195 = 19;
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("aq")
   static IndexedSprite[] field5200;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5197 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final char field5193 = 'À';
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   public static final int field5196 = 65536;

   classOY(classXY var1) {
      int var2 = 0;
      if (null != var1 && var1.field6952 != null) {
         label26:
         while (true) {
            int var3 = var1.method13095(-346779531);
            switch (var3) {
               case 0:
                  break label26;
               case 5:
                  var2 = var1.method13099(1304636744);
            }
         }
      }

      this.field5191 = var2 * -1695411655;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)V")
   @ObfuscatedName("zh")
   public static void method8857(classLH var0, int var1) {
      var0.field4157.method13913(var1, (byte)112);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loy;")
   @ObfuscatedName("ae")
   public static classOY method8855(int var0) {
      classOY var1 = (classOY)NodeCache.method6460(field5192, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5199.method11942(16, var0, -1710366334);
         var1 = new classOY(new classXY(var2));
         field5192.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loy;")
   @ObfuscatedName("af")
   public static classOY method8856(int var0) {
      classOY var1 = (classOY)NodeCache.method6460(field5192, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5199.method11942(16, var0, -1064434243);
         var1 = new classOY(new classXY(var2));
         field5192.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("cr")
   static int method8858(int var0, Script var1, boolean var2, int var3) {
      try {
         if (var0 == 6754) {
            int var8 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            NPCComposition var9 = classOH.method8480(var8, 677467691);
            Object[] var10 = classBB.field328;
            int var11 = (classBB.field338 += 1222866659) * 1375698635 - 1;
            String var12;
            if (null != var9) {
               if (var3 == -1506417701) {
                  throw new IllegalStateException();
               }

               var12 = var9.method9038((byte)55);
            } else {
               var12 = "";
            }

            var10[var11] = var12;
            return 1;
         } else if (6764 == var0) {
            if (var3 == -1506417701) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               NPCComposition var7 = classOH.method8480(classBB.field323[-324749371 * DynamicObject.field1676], -385869);
               int var5 = classBB.field323[1 + DynamicObject.field1676 * -324749371];
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var7.method9017(var5, (byte)-51);
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = NPCComposition.method9027(var7, var5, (byte)-74);
               return 1;
            }
         } else if (var0 == 6765) {
            if (var3 == -1506417701) {
               throw new IllegalStateException();
            } else {
               NPCComposition var4 = classOH.method8480(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371], 826357941);
               int[] var10000 = classBB.field323;
               int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               int var10002;
               if (var4 != null) {
                  if (var3 == -1506417701) {
                     throw new IllegalStateException();
                  }

                  var10002 = 1781570005 * var4.field5318;
               } else {
                  var10002 = 0;
               }

               var10000[var10001] = var10002;
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "oy.cr(" + 41);
      }
   }
}
