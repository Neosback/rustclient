import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jc")
public class classJC {
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("ac")
   public static final classJC field3126 = new classJC(17);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("aj")
   public static final classJC field3129 = new classJC(7);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("ae")
   public static final classJC field3121 = new classJC(37);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("ag")
   public static final classJC field3123 = new classJC(24);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("af")
   public static final classJC field3125 = new classJC(18);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("aq")
   public static final classJC field3132 = new classJC(7);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("ax")
   public static final classJC field3131 = new classJC(14);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("as")
   public static final classJC field3122 = new classJC(14);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("aa")
   public static final classJC field3127 = new classJC(11);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("ao")
   public static final classJC field3128 = new classJC(2);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("al")
   public static final classJC field3124 = new classJC(4);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("ab")
   public static final classJC field3130 = new classJC(4);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("ay")
   public static final classJC field3120 = new classJC(7);
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   static final int field3134 = 34;
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("ad")
   public static final classJC field3133 = new classJC(6);
   @ObfuscatedSignature(descriptor = "Ljc;")
   @ObfuscatedName("az")
   public static final classJC field3119 = new classJC(-2);
   @ObfuscatedName("ix")
   static int field3135;

   @ObfuscatedSignature(descriptor = "(CII)Ljava/lang/String;")
   @ObfuscatedName("au")
   static String method6395(char var0, int var1, int var2) {
      try {
         char[] var3 = new char[var1];

         for (int var4 = 0; var4 < var1; var4++) {
            var3[var4] = var0;
         }

         return new String(var3);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "jc.au(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Ljc;")
   @ObfuscatedName("ag")
   public static classJC[] method6388() {
      return new classJC[]{
         field3119,
         field3125,
         field3121,
         field3130,
         field3123,
         field3122,
         field3131,
         field3126,
         field3127,
         field3128,
         field3124,
         field3129,
         field3120,
         field3132,
         field3133
      };
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Ljc;")
   @ObfuscatedName("af")
   public static classJC[] method6389() {
      return new classJC[]{
         field3119,
         field3125,
         field3121,
         field3130,
         field3123,
         field3122,
         field3131,
         field3126,
         field3127,
         field3128,
         field3124,
         field3129,
         field3120,
         field3132,
         field3133
      };
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Ljc;")
   @ObfuscatedName("ae")
   public static classJC[] method6390() {
      return new classJC[]{
         field3119,
         field3125,
         field3121,
         field3130,
         field3123,
         field3122,
         field3131,
         field3126,
         field3127,
         field3128,
         field3124,
         field3129,
         field3120,
         field3132,
         field3133
      };
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;III)V")
   @ObfuscatedName("yl")
   public static void method6393(classNU var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method8106(var1, var1, var1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Ljc;")
   @ObfuscatedName("ab")
   public static classJC[] method6391() {
      return new classJC[]{
         field3119,
         field3125,
         field3121,
         field3130,
         field3123,
         field3122,
         field3131,
         field3126,
         field3127,
         field3128,
         field3124,
         field3129,
         field3120,
         field3132,
         field3133
      };
   }

   classJC(int var1) {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzd;)I")
   @ObfuscatedName("lc")
   public static int method6394(classZD var0) {
      if (var0 == null) {
         var0.method13891();
      }

      return classZD.method13949(var0, (byte)89) / var0.field7168.field8;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ag")
   static final boolean method6392(int var0, int var1) {
      try {
         if (var0 >= 61) {
            if (var1 == -581143508) {
               throw new IllegalStateException();
            }

            if (var0 <= 67) {
               if (var1 == -581143508) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "jc.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILoz;ILjava/lang/String;IIIIII)V")
   @ObfuscatedName("fs")
   static void method6396(int var0, classOZ var1, int var2, String var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      try {
         if (-1 != var0) {
            if (var9 != -1533141989) {
               throw new IllegalStateException();
            }

            classOZ var10 = var1.vmethod542(var2, -166157468);
            if (null != var10) {
               if (var9 != -1533141989) {
                  throw new IllegalStateException();
               }

               for (int var11 = var10.vmethod539((short)-18872); var11 >= 0; var11--) {
                  if (var9 != -1533141989) {
                     return;
                  }

                  int var12 = classLD.method6798(var11, var5, (byte)4);
                  String var13 = var10.vmethod551(var11, classLB.field4088, (byte)95);
                  if (var13 != null) {
                     classZO.method14128(var0, var13, var3, var4, var12, var6, var7, -1, var8, -1739941876);
                  }
               }
            }

            if (client.field823) {
               if (var9 != -1533141989) {
                  throw new IllegalStateException();
               }

               for (byte var15 = 1; var15 < 26; var15 += 5) {
                  if (var9 != -1533141989) {
                     throw new IllegalStateException();
                  }

                  int var16 = classLD.method6798(var15, var5, (byte)4);
                  String var17 = classQM.method9703(null, var15, (byte)-123);
                  classZO.method14128(var0, var17, var3, var4, var16, var6, var7, -1, var8, -1739941876);
               }
            }
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "jc.fs(" + ')');
      }
   }
}
