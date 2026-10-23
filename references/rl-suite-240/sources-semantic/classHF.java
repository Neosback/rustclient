import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hf")
public class classHF extends WorldMapIcon {
   @ObfuscatedName("ae")
   final int field2782;
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   static final String field2786 = "c";
   @ObfuscatedName("az")
   final int field2780;
   @ObfuscatedName("ab")
   final int field2783;
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   public static final int field2784 = 75;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   public static final int field2785 = 64;
   @ObfuscatedSignature(descriptor = "Lhw;")
   @ObfuscatedName("af")
   final classHW field2781;

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("ag")
   @Override
   int vmethod494(short var1) {
      try {
         return this.field2782 * -772211955;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hf.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   @Override
   int vmethod495() {
      return this.field2782 * -772211955;
   }

   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("af")
   public static String method6038(long var0) {
      try {
         if (var0 > 0L && var0 < 6582952005840035281L) {
            if (var0 % 37L == 0L) {
               return null;
            } else {
               int var2 = 0;

               for (long var3 = var0; 0L != var3; var3 /= 37L) {
                  var2++;
               }

               StringBuilder var5 = new StringBuilder(var2);

               while (0L != var0) {
                  long var6 = var0;
                  var0 /= 37L;
                  char var8 = classOB.field4806[(int)(var6 - 37L * var0)];
                  if ('_' == var8) {
                     int var9 = var5.length() - 1;
                     var5.setCharAt(var9, Character.toUpperCase(var5.charAt(var9)));
                     var8 = 160;
                  }

                  var5.append(var8);
               }

               var5.reverse();
               var5.setCharAt(0, Character.toUpperCase(var5.charAt(0)));
               return var5.toString();
            }
         } else {
            return null;
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "hf.af(" + ')');
      }
   }

   classHF(classKY var1, classKY var2, int var3, classHW var4) {
      super(var1, var2);
      this.field2783 = var3 * -251594745;
      this.field2781 = var4;
      classPS var5 = SceneTilePaint.method4817(this.vmethod486((byte)-31), (byte)-50);
      SpritePixels var6 = var5.method9236(false, -1163794975);
      if (null != var6) {
         this.field2780 = -36644511 * var6.field7005;
         this.field2782 = var6.field7007 * -1564324923;
      } else {
         this.field2780 = 0;
         this.field2782 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   @Override
   int vmethod493(int var1) {
      try {
         return this.field2780 * -1877374303;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hf.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   @Override
   public int vmethod488() {
      return -1532014907 * this.field2783;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   @Override
   public int vmethod487() {
      return -1143445676 * this.field2783;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   @Override
   public int vmethod489() {
      return -1047464521 * this.field2783;
   }

   @ObfuscatedSignature(descriptor = "()Lhw;")
   @ObfuscatedName("aj")
   @Override
   classHW vmethod490() {
      return this.field2781;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   @Override
   int vmethod492() {
      return this.field2780 * -1877374303;
   }

   @ObfuscatedSignature(descriptor = "(B)Lhw;")
   @ObfuscatedName("ae")
   @Override
   classHW vmethod491(byte var1) {
      try {
         return this.field2781;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hf.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   @Override
   int vmethod496() {
      return this.field2782 * -772211955;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   @Override
   int vmethod497() {
      return this.field2782 * -779082207;
   }

   @ObfuscatedSignature(descriptor = "(I)[Lkc;")
   @ObfuscatedName("ag")
   public static classKC[] method6037(int var0) {
      try {
         return new classKC[]{classKC.field3542, classKC.field3541, classKC.field3540};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "hf.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("af")
   @Override
   public int vmethod486(byte var1) {
      try {
         return -1047464521 * this.field2783;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hf.af(" + 41);
      }
   }
}
