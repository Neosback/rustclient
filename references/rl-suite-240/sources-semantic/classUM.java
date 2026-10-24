import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("um")
public class classUM {
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("av")
   public static final classUM field6348 = new classUM(21);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("af")
   public static final classUM field6330 = new classUM(0);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ae")
   public static final classUM field6329 = new classUM(1);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ab")
   public static final classUM field6339 = new classUM(2);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("aq")
   public static final classUM field6343 = new classUM(12);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("as")
   public static final classUM field6332 = new classUM(4);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("az")
   static final classUM field6327 = new classUM(255);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ac")
   public static final classUM field6334 = new classUM(6);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ad")
   public static final classUM field6341 = new classUM(13);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ao")
   public static final classUM field6336 = new classUM(8);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("al")
   public static final classUM field6340 = new classUM(9);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("am")
   static final classUM field6349 = new classUM(24);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ay")
   public static final classUM field6351 = new classUM(11);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ai")
   public static final classUM field6344 = new classUM(17);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ax")
   public static final classUM field6333 = new classUM(5);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("aj")
   public static final classUM field6342 = new classUM(10);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("au")
   public static final classUM field6337 = new classUM(15);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("aa")
   public static final classUM field6335 = new classUM(7);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ar")
   public static final classUM field6352 = new classUM(18);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("aw")
   public static final classUM field6346 = new classUM(19);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ak")
   public static final classUM field6328 = new classUM(20);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ap")
   public static final classUM field6338 = new classUM(14);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ag")
   public static final classUM field6331 = new classUM(3);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("an")
   public static final classUM field6350 = new classUM(23);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("at")
   public static final classUM field6345 = new classUM(22);
   @ObfuscatedSignature(descriptor = "Lum;")
   @ObfuscatedName("ah")
   static final classUM field6347 = new classUM(25);
   @ObfuscatedName("bn")
   public final int field6326;

   classUM(int var1) {
      this.field6326 = var1 * -596577535;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;I)V")
   @ObfuscatedName("ae")
   static void method11472(WorldView var0, int var1, int var2, classUP var3, classKH var4, int var5) {
      try {
         for (int var6 = 0; var6 < var0.field1695.method14050((byte)10); var6++) {
            NPC var7 = (NPC)IndexedObjectSet.method13449(var0.field1696, var0.field1695.method14048(var6, 1956621640));
            if (var7 != null) {
               if (var5 <= -213891969) {
                  throw new IllegalStateException();
               }

               if (var7.vmethod262((byte)94)) {
                  NPCComposition var8 = var7.field1579;
                  if (null != var8) {
                     if (var5 <= -213891969) {
                        throw new IllegalStateException();
                     }

                     if (null != var8.field5323) {
                        if (var5 <= -213891969) {
                           return;
                        }

                        var8 = var8.method8998(652890385);
                     }
                  }

                  if (null != var8) {
                     if (var5 <= -213891969) {
                        return;
                     }

                     if (var8.field5317) {
                        if (var5 <= -213891969) {
                           throw new IllegalStateException();
                        }

                        if (var8.field5324) {
                           if (var5 <= -213891969) {
                              throw new IllegalStateException();
                           }

                           classUZ var9 = classYY.method13833(var0, -1547553299 * var7.field1484, -1272026483 * var7.field1486, (byte)121);
                           int var10 = (int)var9.field6425;
                           int var11 = (int)var9.field6424;
                           var9.method11797(1821141675);
                           int var12 = var10 / 32 - client.field963 * 2106329293 / 32;
                           int var13 = var11 / 32 - client.field987 * -2126074583 / 32;
                           classBO.method1170(var1, var2, var12, var13, var3, classPE.field5232[1], var4, (byte)103);
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "um.ae(" + ')');
      }
   }
}
