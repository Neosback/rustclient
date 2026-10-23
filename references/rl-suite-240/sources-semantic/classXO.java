import java.util.Locale;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@classXA
@ObfuscatedName("xo")
@classZY
public class classXO implements classXZ {
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   public static final int field6901 = 1;
   @ObfuscatedName("ff")
   static long field6903;
   @ObfuscatedSignature(descriptor = "Lxo;")
   @ObfuscatedName("ae")
   public static final classXO field6893 = new classXO("FR", "fr", "French", classXQ.field6908, 2, "FR");
   @ObfuscatedSignature(descriptor = "Lxo;")
   @ObfuscatedName("ab")
   static final classXO field6891 = new classXO("PT", "pt", "Portuguese", classXQ.field6908, 3, "BR");
   @ObfuscatedSignature(descriptor = "Lxo;")
   @ObfuscatedName("ag")
   static final classXO field6897 = new classXO("NL", "nl", "Dutch", classXQ.field6907, 4, "NL");
   @ObfuscatedSignature(descriptor = "Lxo;")
   @ObfuscatedName("as")
   public static final classXO field6892 = new classXO("ES", "es", "Spanish", classXQ.field6907, 5, "ES");
   @ObfuscatedSignature(descriptor = "Lxo;")
   @ObfuscatedName("ax")
   static final classXO field6895 = new classXO("ES_MX", "es-mx", "Spanish (Latin American)", classXQ.field6908, 6, "MX");
   @ObfuscatedSignature(descriptor = "Lxo;")
   @ObfuscatedName("af")
   static final classXO field6896 = new classXO("DE", "de", "German", classXQ.field6908, 1, "DE");
   @ObfuscatedSignature(descriptor = "[Lxo;")
   @ObfuscatedName("aa")
   static final classXO[] field6894;
   @ObfuscatedName("ao")
   final int field6899;
   @ObfuscatedName("al")
   final String field6900;
   @ObfuscatedSignature(descriptor = "Lxo;")
   @ObfuscatedName("az")
   public static final classXO field6890 = new classXO("EN", "en", "English", classXQ.field6908, 0, "GB");
   @ObfuscatedName("ac")
   final String field6898;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field6902 = 19;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)I")
   @ObfuscatedName("ld")
   public static int method12925(classLH var0) {
      return 1173906730 * var0.field4155;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("bq")
   static int method12926(int var0, Script var1, boolean var2, int var3) {
      try {
         if (var0 == 5306) {
            if (var3 <= -297600906) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classLH.method7145(591510276);
               return 1;
            }
         } else if (5307 == var0) {
            int var6 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            if (1 != var6) {
               if (var3 <= -297600906) {
                  throw new IllegalStateException();
               }

               if (2 != var6) {
                  return 1;
               }

               if (var3 <= -297600906) {
                  throw new IllegalStateException();
               }
            }

            classUO.method11477(var6, -2061563598);
            return 1;
         } else if (5308 == var0) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ClientPreferences.method2796(classAB.field122, -79298167);
            return 1;
         } else if (var0 != 5309) {
            if (var0 == 5310) {
               if (var3 <= -297600906) {
                  throw new IllegalStateException();
               } else {
                  DynamicObject.field1676 -= 120041229;
                  return 1;
               }
            } else {
               return 2;
            }
         } else {
            int var4 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            if (1 != var4) {
               if (var3 <= -297600906) {
                  throw new IllegalStateException();
               }

               if (var4 != 2) {
                  return 1;
               }
            }

            classAB.field122.method2799(var4, 1278411597);
            return 1;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xo.bq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/String;")
   @ObfuscatedName("as")
   String method12916(byte var1) {
      try {
         return this.field6900;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xo.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lxo;")
   @ObfuscatedName("ac")
   static classXO[] method12913() {
      return new classXO[]{field6890, field6891, field6893, field6897, field6895, field6896, field6892};
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field6899 * 68738523;
   }

   static {
      classXO[] var0 = method12914(-56169549);
      field6894 = new classXO[var0.length];
      classXO[] var1 = var0;

      for (int var2 = 0; var2 < var1.length; var2++) {
         classXO var3 = var1[var2];
         if (field6894[68738523 * var3.field6899] != null) {
            throw new IllegalStateException();
         }

         field6894[68738523 * var3.field6899] = var3;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxo;)Ljava/lang/String;")
   @ObfuscatedName("rc")
   public static String method12917(classXO var0) {
      return var0.field6900;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field6899 * 68738523;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xo.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field6899 * 68738523;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field6899 * 68738523;
   }

   @ObfuscatedSignature(descriptor = "(I)[Lxo;")
   @ObfuscatedName("ag")
   static classXO[] method12914(int var0) {
      try {
         return new classXO[]{field6890, field6891, field6893, field6897, field6895, field6896, field6892};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xo.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gi")
   public String method12921() {
      return this.method12916((byte)-49).toLowerCase(Locale.ENGLISH);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gd")
   public String method12922() {
      return this.method12916((byte)-111).toLowerCase(Locale.ENGLISH);
   }

   classXO(String var1, String var2, String var3, classXQ var4, int var5, String var6) {
      this.field6898 = var1;
      this.field6900 = var2;
      this.field6899 = var5 * 1071765075;
      if (null != var6) {
         new Locale(var2.substring(0, 2), var6);
      } else {
         new Locale(var2.substring(0, 2));
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lxo;")
   @ObfuscatedName("ax")
   static classXO[] method12915() {
      return new classXO[]{field6890, field6891, field6893, field6897, field6895, field6896, field6892};
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ao")
   String method12918() {
      return this.field6900;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ay")
   String method12919() {
      return this.field6900;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method12923() {
      return this.method12916((byte)-8).toLowerCase(Locale.ENGLISH);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aa")
   String method12920() {
      return this.field6900;
   }

   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("as")
   public static int method12924(long var0) {
      try {
         int var2 = (int)(var0 >>> 52 & 4095L);
         if (4095L == var2) {
            var2 = -1;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xo.as(" + 41);
      }
   }

   @Override
   public String toString() {
      try {
         return this.method12916((byte)-57).toLowerCase(Locale.ENGLISH);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xo.toString(" + ')');
      }
   }
}
