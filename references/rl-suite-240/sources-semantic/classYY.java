import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yy")
public class classYY implements classXZ {
   @ObfuscatedSignature(descriptor = "Lyy;")
   @ObfuscatedName("ab")
   public static final classYY field7112 = new classYY(0, 5, classTE.class, new classYO());
   @ObfuscatedSignature(descriptor = "Lyy;")
   @ObfuscatedName("ae")
   public static final classYY field7111 = new classYY(4, 2, String.class, new classZB());
   @ObfuscatedName("ac")
   public final Class field7107;
   @ObfuscatedSignature(descriptor = "Lyy;")
   @ObfuscatedName("af")
   public static final classYY field7113 = new classYY(3, 1, Long.class, new classYF());
   @ObfuscatedSignature(descriptor = "Lyy;")
   @ObfuscatedName("ag")
   static final classYY field7110 = new classYY(1, 6, Object.class, new classYP());
   @ObfuscatedName("as")
   public final int field7105;
   @ObfuscatedName("ax")
   public final int field7106;
   @ObfuscatedSignature(descriptor = "Lyy;")
   @ObfuscatedName("az")
   public static final classYY field7109 = new classYY(2, 0, Integer.class, new classYV());
   @ObfuscatedSignature(descriptor = "Lyh;")
   @ObfuscatedName("aa")
   final classYH field7108;
   @ToRemove(unused = "true")
   @ObfuscatedName("bo")
   static final int field7115 = 75;
   @ObfuscatedName("os")
   static boolean field7116;
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   static final int field7114 = 0;

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field7106 * -701844459;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "yy.az(" + 41);
      }
   }

   @Override
   public String toString() {
      if (this == field7109) {
         return "int";
      } else if (this == field7113) {
         return "long";
      } else {
         return this == field7111 ? "string" : "unknown";
      }
   }

   @ObfuscatedSignature(descriptor = "(JIZLrz;B)Ljava/lang/String;")
   @ObfuscatedName("ae")
   static String method13818(long var0, int var2, boolean var3, classRZ var4, byte var5) {
      try {
         return classXW.method13010(var0, var2, false, var3, var4, (byte)-11) + "%";
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "yy.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)Z")
   @ObfuscatedName("qv")
   public static boolean method13832(classLH var0) {
      return var0.field4155 * 340310096 != var0.field4150 * 1072642345;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Class;I)Lyy;")
   @ObfuscatedName("as")
   public static classYY method13823(Class var0, int var1) {
      try {
         classYY[] var2 = method13822((byte)-4);

         for (int var3 = 0; var3 < var2.length; var3++) {
            if (var1 >= -1503548527) {
               throw new IllegalStateException();
            }

            classYY var4 = var2[var3];
            if (var0 == var4.field7107) {
               if (var1 >= -1503548527) {
                  throw new IllegalStateException();
               }

               return var4;
            }
         }

         return null;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "yy.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lyy;Lxy;I)Ljava/lang/Object;")
   @ObfuscatedName("gm")
   public static Object method13827(classYY var0, classXY var1, int var2) {
      if (var0 == null) {
         return var0.method13831(var1, var2);
      } else {
         try {
            return var0.field7108.vmethod653(var1, (byte)-99);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "yy.ac(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;S)V")
   @ObfuscatedName("ax")
   public static void method13824(Object var0, classXY var1, short var2) {
      try {
         Class var4 = var0.getClass();
         classYY var5 = method13823(var4, -1986258481);
         if (var5 == null) {
            if (var2 != 255) {
               throw new IllegalArgumentException();
            }
         } else {
            classYH var3 = var5.field7108;
            var3.vmethod656(var0, var1, 1083304569);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "yy.ax(" + ')');
      }
   }

   classYY(int var1, int var2, Class var3, classYH var4) {
      this.field7105 = 935492601 * var1;
      this.field7106 = 2019130173 * var2;
      this.field7107 = var3;
      this.field7108 = var4;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("au")
   public Object method13828(classXY var1) {
      return this.field7108.vmethod653(var1, (byte)-19);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;)V")
   @ObfuscatedName("ay")
   public static void method13825(Object var0, classXY var1) {
      Class var3 = var0.getClass();
      classYY var4 = method13823(var3, -1510627076);
      if (var4 == null) {
         throw new IllegalArgumentException();
      } else {
         classYH var2 = var4.field7108;
         var2.vmethod656(var0, var1, -407735841);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lyy;")
   @ObfuscatedName("aa")
   public static classYY[] method13819() {
      return new classYY[]{field7109, field7113, field7112, field7111, field7110};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lyy;")
   @ObfuscatedName("ao")
   public static classYY[] method13820() {
      return new classYY[]{field7109, field7113, field7112, field7111, field7110};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lyy;")
   @ObfuscatedName("al")
   public static classYY[] method13821() {
      return new classYY[]{field7109, field7113, field7112, field7111, field7110};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;)V")
   @ObfuscatedName("aj")
   public static void method13826(Object var0, classXY var1) {
      Class var3 = var0.getClass();
      classYY var4 = method13823(var3, -2101844078);
      if (var4 == null) {
         throw new IllegalArgumentException();
      } else {
         classYH var2 = var4.field7108;
         var2.vmethod656(var0, var1, 1021411155);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[Lyy;")
   @ObfuscatedName("ag")
   public static classYY[] method13822(byte var0) {
      try {
         return new classYY[]{field7109, field7113, field7112, field7111, field7110};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "yy.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field7106 * -602961010;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ap")
   public Object method13829(classXY var1) {
      return this.field7108.vmethod653(var1, (byte)58);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field7106 * -701844459;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ad")
   public Object method13830(classXY var1) {
      return this.field7108.vmethod653(var1, (byte)123);
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIB)Luz;")
   @ObfuscatedName("bi")
   static classUZ method13833(WorldView var0, int var1, int var2, byte var3) {
      try {
         if (var0 != null) {
            if (var3 <= 99) {
               throw new IllegalStateException();
            }

            if (var0 != classIS.field3051) {
               WorldEntity var4 = (WorldEntity)classIS.field3051.field1698.method13637(2140889407 * var0.field1697);
               if (var4 == null) {
                  if (var3 <= 99) {
                     throw new IllegalStateException();
                  }

                  return classIK.method6261(var1, 0.0F, var2, (byte)4);
               }

               return var4.method10666(var1, var2, (byte)49);
            }

            if (var3 <= 99) {
               throw new IllegalStateException();
            }
         }

         return classIK.method6261(var1, 0.0F, var2, (byte)-64);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "yy.bi(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lyy;")
   @ObfuscatedName("ue")
   public static classYY method13835(int var0) {
      return FriendContainer.method10175(var0, 675005370);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field7106 * -701844459;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hp")
   static void method13834(int var0) {
      try {
         ClientPreferences.method2794(classAB.field122, 347524667 * client.field887, -16777216);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "yy.hp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)Ljava/lang/Object;")
   @ObfuscatedName("ac")
   public Object method13831(classXY var1, int var2) {
      try {
         return this.field7108.vmethod653(var1, (byte)-99);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "yy.ac(" + ')');
      }
   }
}
