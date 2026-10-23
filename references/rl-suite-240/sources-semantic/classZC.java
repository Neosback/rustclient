import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zc")
public class classZC extends classVJ {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field7148 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field7146 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field7145 = 0;
   @ObfuscatedSignature(descriptor = "[Lyy;")
   @ObfuscatedName("ab")
   classYY[] field7150;
   @ObfuscatedName("ag")
   List field7149;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field7147 = 4;

   public classZC(IndexDataBase var1, int var2) {
      byte[] var3 = var1.method11942(var2, 0, -1251911506);
      if (null != var3) {
         this.method13876(new classXY(var3), (byte)2);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ax")
   void method13875(classXY var1) {
      int var2 = var1.method13176(-1101542591);
      this.field7150 = new classYY[var2];
      this.field7149 = new ArrayList(var2);

      for (int var3 = 0; var3 < var2; var3++) {
         this.field7150[var3] = (classYY)classOF.method8436(classYY.method13822((byte)76), var1.method13095(-346779531), 1583855012);
         int var4 = var1.method13176(-747970285);
         HashMap var5 = new HashMap(var4);

         while (var4-- > 0) {
            Object var6 = classYY.method13827(this.field7150[var3], var1, 827988912);
            int var7 = var1.method13176(-151343769);
            ArrayList var8 = new ArrayList();

            while (var7-- > 0) {
               int var9 = var1.method13176(-1881555353);
               var8.add(var9);
            }

            var5.put(var6, var8);
         }

         this.field7149.add(var3, var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)V")
   @ObfuscatedName("af")
   void method13876(classXY var1, byte var2) {
      try {
         int var3 = var1.method13176(-1998324959);
         this.field7150 = new classYY[var3];
         this.field7149 = new ArrayList(var3);

         for (int var4 = 0; var4 < var3; var4++) {
            if (var2 == 0) {
               return;
            }

            this.field7150[var4] = (classYY)classOF.method8436(classYY.method13822((byte)-4), var1.method13095(-346779531), 54392156);
            int var5 = var1.method13176(-1788832399);
            HashMap var6 = new HashMap(var5);

            while (var5-- > 0) {
               if (var2 == 0) {
                  throw new IllegalStateException();
               }

               Object var7 = classYY.method13827(this.field7150[var4], var1, 827988912);
               int var8 = var1.method13176(-1373334814);
               ArrayList var9 = new ArrayList();

               while (var8-- > 0) {
                  if (var2 == 0) {
                     throw new IllegalStateException();
                  }

                  int var10 = var1.method13176(176951995);
                  var9.add(var10);
               }

               var6.put(var7, var9);
            }

            this.field7149.add(var4, var6);
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "zc.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   public boolean method13871() {
      return this.field7150 != null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   public boolean method13872() {
      return this.field7150 != null;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;IB)Ljava/util/List;")
   @ObfuscatedName("ae")
   public List method13878(Object var1, int var2, byte var3) {
      try {
         if (var2 < 0) {
            if (var3 == 1) {
               throw new IllegalStateException();
            }

            var2 = 0;
         }

         Map var4 = (Map)this.field7149.get(var2);
         return (List)var4.get(var1);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "zc.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lzc;")
   @ObfuscatedName("cb")
   public static classZC method13881(int var0) {
      return classBA.method779(var0, 2135996924);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("as")
   void method13877(classXY var1) {
      int var2 = var1.method13176(221140724);
      this.field7150 = new classYY[var2];
      this.field7149 = new ArrayList(var2);

      for (int var3 = 0; var3 < var2; var3++) {
         this.field7150[var3] = (classYY)classOF.method8436(classYY.method13822((byte)55), var1.method13095(-346779531), -1159903959);
         int var4 = var1.method13176(-249936447);
         HashMap var5 = new HashMap(var4);

         while (var4-- > 0) {
            Object var6 = classYY.method13827(this.field7150[var3], var1, 827988912);
            int var7 = var1.method13176(-915204940);
            ArrayList var8 = new ArrayList();

            while (var7-- > 0) {
               int var9 = var1.method13176(-1022044693);
               var8.add(var9);
            }

            var5.put(var6, var8);
         }

         this.field7149.add(var3, var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;I)Ljava/util/List;")
   @ObfuscatedName("ac")
   public List method13879(Object var1, int var2) {
      if (var2 < 0) {
         var2 = 0;
      }

      Map var3 = (Map)this.field7149.get(var2);
      return (List)var3.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)Lzc;")
   @ObfuscatedName("ab")
   public static classZC method13882(int var0) {
      return classHT.method6149(var0, -1765104593);
   }

   @ObfuscatedSignature(descriptor = "(Lzc;I)Z")
   @ObfuscatedName("mx")
   public static boolean method13873(classZC var0, int var1) {
      if (var0 == null) {
         return var0.method13874(var1);
      } else {
         try {
            boolean var10000;
            if (var0.field7150 != null) {
               if (var1 <= 132222543) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "zc.az(" + ')');
         }
      }
   }

   public classZC(IndexDataBase var1, int var2, int var3) {
      byte[] var4 = var1.method11942(var2, var3 + 1, -960653587);
      if (null != var4) {
         this.method13876(new classXY(var4), (byte)32);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;I)Ljava/util/List;")
   @ObfuscatedName("aa")
   public List method13880(Object var1, int var2) {
      if (var2 < 0) {
         var2 = 0;
      }

      Map var3 = (Map)this.field7149.get(var2);
      return (List)var3.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("az")
   public boolean method13874(int var1) {
      try {
         boolean var10000;
         if (this.field7150 != null) {
            if (var1 <= 132222543) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "zc.az(" + ')');
      }
   }
}
