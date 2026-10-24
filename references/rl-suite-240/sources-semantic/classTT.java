import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tt")
public class classTT {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field6230 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field6229 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6231 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field6232 = 4;
   @ObfuscatedName("ax")
   int[] field6228 = new int[112];
   @ObfuscatedName("ac")
   int[] field6227 = new int[192];
   @ToRemove(unused = "true")
   @ObfuscatedName("dy")
   public static final int field6233 = 104;

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("ah")
   public boolean method11207(char var1) {
      return this.method11213(var1, 1890044400) && (this.field6227[var1] == 2 || 3 == this.field6227[var1]);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltt;I)Z")
   @ObfuscatedName("lc")
   public static boolean method11215(classTT var0, int var1) {
      if (var0 == null) {
         return var0.method11220(var1);
      } else if (var1 >= 0 && var1 < 4) {
         return true;
      } else {
         System.out.println("Invalid mode: " + var1);
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(CIB)V")
   @ObfuscatedName("af")
   public void method11191(char var1, int var2, byte var3) {
      try {
         if (this.method11213(var1, 1690223281) && this.method11217(var2, -677813658)) {
            if (var3 <= 0) {
               return;
            }

            this.field6227[var1] = var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "tt.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ae")
   public int method11193(int var1, int var2) {
      try {
         if (method11210(this, var1, 120041229)) {
            if (var2 <= -1030414496) {
               throw new IllegalStateException();
            } else {
               return this.field6228[var1];
            }
         } else {
            return 0;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(CI)V")
   @ObfuscatedName("ad")
   public void method11192(char var1, int var2) {
      if (this.method11213(var1, 1902939094) && this.method11217(var2, 988611010)) {
         this.field6227[var1] = var2;
      }
   }

   public classTT() {
      Arrays.fill(this.field6228, 3);
      Arrays.fill(this.field6227, 3);
   }

   @ObfuscatedSignature(descriptor = "(CI)Z")
   @ObfuscatedName("as")
   public boolean method11203(char var1, int var2) {
      try {
         if (this.method11213(var1, 2065992043)) {
            if (var2 >= -1354864661) {
               throw new IllegalStateException();
            }

            if (this.field6227[var1] == 1) {
               return true;
            }

            if (3 == this.field6227[var1]) {
               if (var2 >= -1354864661) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ax")
   public boolean method11205(int var1, int var2) {
      try {
         if (method11210(this, var1, 120041229)) {
            if (var2 >= 357511497) {
               throw new IllegalStateException();
            }

            if (2 == this.field6228[var1]) {
               return true;
            }

            if (var2 >= 357511497) {
               throw new IllegalStateException();
            }

            if (this.field6228[var1] == 3) {
               if (var2 >= 357511497) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ltt;II)Z")
   @ObfuscatedName("ya")
   public static boolean method11210(classTT var0, int var1, int var2) {
      if (var0 == null) {
         var0.method11201(var1, var1);
      }

      try {
         if (var1 >= 0) {
            if (var2 != 120041229) {
               throw new IllegalStateException();
            }

            if (var1 < 112) {
               return true;
            }

            if (var2 != 120041229) {
               throw new IllegalStateException();
            }
         }

         System.out.println("Invalid keycode: " + var1);
         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(CI)Z")
   @ObfuscatedName("ac")
   public boolean method11208(char var1, int var2) {
      try {
         if (this.method11213(var1, 1893354406)) {
            if (var2 != 731299091) {
               throw new IllegalStateException();
            }

            if (this.field6227[var1] == 2) {
               return true;
            }

            if (var2 != 731299091) {
               throw new IllegalStateException();
            }

            if (3 == this.field6227[var1]) {
               if (var2 != 731299091) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvw;)Llw;")
   @ObfuscatedName("cx")
   public static Widget method11221(classVW var0) {
      return var0.field6623;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltt;I)Z")
   @ObfuscatedName("vv")
   public static boolean method11216(classTT var0, int var1) {
      if (var0 == null) {
         return var0.method11218(var1);
      } else if (var1 >= 0 && var1 < 4) {
         return true;
      } else {
         System.out.println("Invalid mode: " + var1);
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltt;I)Z")
   @ObfuscatedName("sn")
   public static boolean method11211(classTT var0, int var1) {
      if (var0 == null) {
         var0.method11199(var1);
      }

      if (var1 >= 0 && var1 < 112) {
         return true;
      } else {
         System.out.println("Invalid keycode: " + var1);
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(CI)Z")
   @ObfuscatedName("ao")
   boolean method11213(char var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 <= 1562555760) {
               throw new IllegalStateException();
            }

            if (var1 < 192) {
               return true;
            }

            if (var2 <= 1562555760) {
               throw new IllegalStateException();
            }
         }

         System.out.println("Invalid keychar: " + var1);
         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("aa")
   boolean method11217(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 == -1191008513) {
               throw new IllegalStateException();
            }

            if (var1 < 4) {
               return true;
            }

            if (var2 == -1191008513) {
               throw new IllegalStateException();
            }
         }

         System.out.println("Invalid mode: " + var1);
         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;[BII)V")
   @ObfuscatedName("li")
   public static void method11223(classXY var0, byte[] var1, int var2, int var3) {
      if (var0 == null) {
         var0.method13143(var1, var2, var2);
      }

      for (int var4 = var3 + var2 - 1; var4 >= var2; var4--) {
         var1[var4] = (byte)(var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] - 128);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ay")
   public void method11186(int var1, int var2) {
      if (method11210(this, var1, 120041229) && this.method11217(var2, 256763138)) {
         this.field6228[var1] = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aq")
   public void method11187(int var1, int var2) {
      if (method11210(this, var1, 120041229) && this.method11217(var2, 1740472912)) {
         this.field6228[var1] = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aj")
   public void method11188(int var1, int var2) {
      if (method11210(this, var1, 120041229) && this.method11217(var2, -2111500041)) {
         this.field6228[var1] = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bt")
   boolean method11218(int var1) {
      if (var1 >= 0 && var1 < 4) {
         return true;
      } else {
         System.out.println("Invalid mode: " + var1);
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ap")
   public int method11194(int var1) {
      return method11210(this, var1, 120041229) ? this.field6228[var1] : 0;
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("at")
   public boolean method11204(char var1) {
      return this.method11213(var1, 2110384787) && (this.field6227[var1] == 1 || 3 == this.field6227[var1]);
   }

   @ObfuscatedSignature(descriptor = "(C)I")
   @ObfuscatedName("ar")
   public int method11195(char var1) {
      return this.method11213(var1, 1978640149) ? this.field6227[var1] : 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltt;C)I")
   @ObfuscatedName("ek")
   public static int method11196(classTT var0, char var1) {
      if (var0 == null) {
         var0.method11209(var1);
      }

      return var0.method11213(var1, 1903195633) ? var0.field6227[var1] : 0;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ak")
   public boolean method11198(int var1) {
      return method11210(this, var1, 120041229) && (this.field6228[var1] == 1 || this.field6228[var1] == 3);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("av")
   public boolean method11199(int var1) {
      return method11210(this, var1, 120041229) && (this.field6228[var1] == 1 || this.field6228[var1] == 3);
   }

   @ObfuscatedSignature(descriptor = "(Ltt;II)Z")
   @ObfuscatedName("xp")
   public static boolean method11200(classTT var0, int var1, int var2) {
      if (var0 == null) {
         var0.method11202(var1, var1);
      }

      try {
         if (method11210(var0, var1, 120041229)) {
            if (var2 >= -1687866249) {
               throw new IllegalStateException();
            }

            if (var0.field6228[var1] == 1 || var0.field6228[var1] == 3) {
               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("an")
   public boolean method11206(int var1) {
      return method11210(this, var1, 120041229) && (2 == this.field6228[var1] || this.field6228[var1] == 3);
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("am")
   public boolean method11209(char var1) {
      return this.method11213(var1, 1814642560) && (this.field6227[var1] == 2 || 3 == this.field6227[var1]);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bp")
   boolean method11219(int var1) {
      if (var1 >= 0 && var1 < 4) {
         return true;
      } else {
         System.out.println("Invalid mode: " + var1);
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bx")
   boolean method11212(int var1) {
      if (var1 >= 0 && var1 < 112) {
         return true;
      } else {
         System.out.println("Invalid keycode: " + var1);
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("bc")
   boolean method11214(char var1) {
      if (var1 >= 0 && var1 < 192) {
         return true;
      } else {
         System.out.println("Invalid keychar: " + var1);
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(CI)Z")
   @ObfuscatedName("aj")
   public static boolean method11222(char var0, int var1) {
      try {
         if (var0 >= 'A') {
            if (var1 >= -1133777048) {
               throw new IllegalStateException();
            }

            if (var0 <= 'Z') {
               return true;
            }

            if (var1 >= -1133777048) {
               throw new IllegalStateException();
            }
         }

         if (var0 >= 'a') {
            if (var1 >= -1133777048) {
               throw new IllegalStateException();
            }

            if (var0 <= 'z') {
               return true;
            }
         }

         return false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tt.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bg")
   boolean method11220(int var1) {
      if (var1 >= 0 && var1 < 4) {
         return true;
      } else {
         System.out.println("Invalid mode: " + var1);
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(CI)I")
   @ObfuscatedName("ab")
   public int method11197(char var1, int var2) {
      try {
         if (this.method11213(var1, 1594031959)) {
            if (var2 <= -1784280523) {
               throw new IllegalStateException();
            } else {
               return this.field6227[var1];
            }
         } else {
            return 0;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ff")
   static boolean method11224(int var0) {
      try {
         return client.field976;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "tt.ff(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ltt;III)V")
   @ObfuscatedName("lq")
   public static void method11189(classTT var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method11190(var1, var1, var1);
      } else {
         try {
            if (method11210(var0, var1, 120041229)) {
               if (var3 <= 1326720602) {
                  throw new IllegalStateException();
               }

               if (var0.method11217(var2, 1677759112)) {
                  if (var3 <= 1326720602) {
                     return;
                  }

                  var0.field6228[var1] = var2;
               }
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "tt.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("az")
   public void method11190(int var1, int var2, int var3) {
      try {
         if (method11210(this, var1, 120041229)) {
            if (var3 <= 1326720602) {
               throw new IllegalStateException();
            }

            if (this.method11217(var2, 1677759112)) {
               if (var3 <= 1326720602) {
                  return;
               }

               this.field6228[var1] = var2;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "tt.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("al")
   boolean method11201(int var1, int var2) {
      try {
         if (method11210(this, var1, 120041229)) {
            if (var2 >= -1687866249) {
               throw new IllegalStateException();
            }

            if (this.field6227[var1] == 1 || this.field6228[var1] == 3) {
               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ag")
   public boolean method11202(int var1, int var2) {
      try {
         if (method11210(this, var1, 120041229)) {
            if (var2 >= -1687866249) {
               throw new IllegalStateException();
            }

            if (this.field6228[var1] == 1 || this.field6227[var1] == 3) {
               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tt.ag(" + ')');
      }
   }
}
