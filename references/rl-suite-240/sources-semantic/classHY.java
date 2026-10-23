import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hy")
public class classHY extends WorldMapData {
   @ObfuscatedName("al")
   HashSet field2910;
   @ObfuscatedName("ay")
   List field2911;
   @ObfuscatedName("aj")
   ArrayList field2909;
   @ToRemove(unused = "true")
   @ObfuscatedName("bz")
   static final int field2908 = 146;

   @ObfuscatedSignature(descriptor = "(Lxy;ZI)V")
   @ObfuscatedName("ce")
   void method6167(classXY var1, boolean var2, int var3) {
      try {
         this.field2911 = new LinkedList();
         int var4 = var1.method13099(-907205401);

         for (int var5 = 0; var5 < var4; var5++) {
            if (var3 == 1407924950) {
               throw new IllegalStateException();
            }

            int var6 = classXY.method13169(var1, -324749371);
            classKY var7 = new classKY(classXY.method13113(var1, (byte)1));
            boolean var10000;
            if (var1.method13095(-346779531) == 1) {
               if (var3 == 1407924950) {
                  return;
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var8 = var10000;
            if (!var2) {
               if (var8) {
                  continue;
               }

               if (var3 == 1407924950) {
                  return;
               }
            }

            this.field2911.add(new classHF(null, var7, var6, null));
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "hy.ce(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhy;Lxy;Z)V")
   @ObfuscatedName("zc")
   public static void method6168(classHY var0, classXY var1, boolean var2) {
      if (var0 == null) {
         var0.method6170(var1, var2);
      }

      var0.field2911 = new LinkedList();
      int var3 = var1.method13099(1309326328);

      for (int var4 = 0; var4 < var3; var4++) {
         int var5 = classXY.method13169(var1, -324749371);
         classKY var6 = new classKY(classXY.method13113(var1, (byte)1));
         boolean var7 = var1.method13095(-346779531) == 1;
         if (var2 || !var7) {
            var0.field2911.add(new classHF(null, var6, var5, null));
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Lxy;IZB)V")
   @ObfuscatedName("cx")
   void method6166(classXY var1, classXY var2, int var3, boolean var4, byte var5) {
      try {
         this.method5849(var1, var3, 1246613763);
         int var6 = var2.method13099(-411743696);
         this.field2910 = new HashSet(var6);

         for (int var7 = 0; var7 < var6; var7++) {
            if (var5 >= 1) {
               throw new IllegalStateException();
            }

            classGL var8 = new classGL();

            try {
               var8.method5518(var2, -417591206);
            } catch (IllegalStateException var12) {
               continue;
            }

            this.field2910.add(var8);
         }

         int var14 = var2.method13099(-949121677);
         this.field2909 = new ArrayList(var14);

         for (int var15 = 0; var15 < var14; var15++) {
            if (var5 >= 1) {
               throw new IllegalStateException();
            }

            classGF var9 = new classGF();

            try {
               var9.method5431(var2, 1660020430);
            } catch (IllegalStateException var11) {
               continue;
            }

            this.field2909.add(var9);
         }

         this.method6167(var2, var4, 982814688);
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "hy.cx(" + ')');
      }
   }

   classHY() {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhy;Lxy;Z)V")
   @ObfuscatedName("dw")
   public static void method6169(classHY var0, classXY var1, boolean var2) {
      if (var0 == null) {
         var0.method6170(var1, var2);
      }

      var0.field2911 = new LinkedList();
      int var3 = var1.method13099(-180237716);

      for (int var4 = 0; var4 < var3; var4++) {
         int var5 = classXY.method13169(var1, -324749371);
         classKY var6 = new classKY(classXY.method13113(var1, (byte)1));
         boolean var7 = var1.method13095(-346779531) == 1;
         if (var2 || !var7) {
            var0.field2911.add(new classHF(null, var6, var5, null));
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Z)V")
   @ObfuscatedName("dd")
   void method6170(classXY var1, boolean var2) {
      this.field2911 = new LinkedList();
      int var3 = var1.method13099(752768595);

      for (int var4 = 0; var4 < var3; var4++) {
         int var5 = classXY.method13169(var1, -324749371);
         classKY var6 = new classKY(classXY.method13113(var1, (byte)1));
         boolean var7 = var1.method13095(-346779531) == 1;
         if (var2 || !var7) {
            this.field2911.add(new classHF(null, var6, var5, null));
         }
      }
   }
}
