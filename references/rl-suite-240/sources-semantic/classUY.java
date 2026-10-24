import net.runelite.api.events.PostStructComposition;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("uy")
public abstract class classUY extends classTH implements classAAJ {
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   static final int field6422 = 18;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   public int method11773() {
      return -1627988871 * this.field6135;
   }

   @ObfuscatedSignature(descriptor = "(II)Lur;")
   @ObfuscatedName("az")
   protected abstract classUR vmethod606(int var1, int var2);

   @ObfuscatedSignature(descriptor = "(I)Lur;")
   @ObfuscatedName("as")
   protected abstract classUR vmethod607(int var1);

   @ObfuscatedSignature(descriptor = "(I)Lur;")
   @ObfuscatedName("ax")
   protected abstract classUR vmethod608(int var1);

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   public int method11774() {
      return -1627988871 * this.field6135;
   }

   @ObfuscatedSignature(descriptor = "(I)Lur;")
   @ObfuscatedName("ag")
   protected abstract classUR vmethod609(int var1);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxs;I)I")
   @ObfuscatedName("zu")
   public static int method11779(PacketBuffer var0, int var1) {
      return var1 - 702114061 * var0.field6953;
   }

   protected classUY(classKX var1, classXO var2, int var3) {
      super(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IB)Ljava/lang/Object;")
   @ObfuscatedName("ae")
   @Override
   public Object vmethod6(int var1, byte var2) {
      try {
         classUR var3 = this.vmethod606(var1, 1241421818);
         if (null != var3) {
            if (classUR.method11581(var3, 1953474919)) {
               return classUR.method11582(var3, 1949231943);
            }

            if (var2 >= 0) {
               throw new IllegalStateException();
            }
         }

         return null;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uy.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)Laaz;")
   @ObfuscatedName("ab")
   public classAAZ method11777(classXY var1, int var2) {
      try {
         int var3 = var1.method13099(-1621289879);
         classUR var4 = this.vmethod606(var3, 20231137);
         classAAZ var5 = new classAAZ(var3);
         Class var6 = var4.field6371.field7107;
         if (Integer.class == var6) {
            var5.field120 = classXY.method13113(var1, (byte)1);
         } else if (Long.class == var6) {
            var5.field120 = classXY.method13115(var1, 1652502764);
         } else if (var6 == String.class) {
            var5.field120 = var1.method13134(1119042216);
         } else {
            if (!classXV.class.isAssignableFrom(var6)) {
               throw new IllegalStateException();
            }

            if (var2 <= -785197785) {
               throw new IllegalStateException();
            }

            try {
               classXV var7 = (classXV)var6.newInstance();
               var7.method13008(var1, (byte)-17);
               var5.field120 = var7;
            } catch (InstantiationException var8) {
            } catch (IllegalAccessException var9) {
            }
         }

         return var5;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "uy.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   public int method11775() {
      return 1493591413 * this.field6135;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("af")
   public int method11776(byte var1) {
      try {
         return -1627988871 * this.field6135;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uy.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Object;")
   @ObfuscatedName("al")
   @Override
   public Object vmethod5(int var1) {
      classUR var2 = this.vmethod606(var1, -1663914667);
      return null != var2 && classUR.method11581(var2, 2022911592) ? classUR.method11582(var2, 2093263743) : null;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Object;")
   @ObfuscatedName("aj")
   @Override
   public Object vmethod7(int var1) {
      classUR var2 = this.vmethod606(var1, 940192300);
      return null != var2 && classUR.method11581(var2, 2131644615) ? classUR.method11582(var2, 1891825846) : null;
   }

   @ObfuscatedSignature(descriptor = "(Lym;IIII)V")
   @ObfuscatedName("hx")
   public static void method11780(SpritePixels var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method13599(var1, var1, var1, var1);
      }

      var0.method13601(var0.field7004 << 3, var0.field7006 << 3, var1 << 4, var2 << 4, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Laaz;")
   @ObfuscatedName("ay")
   public classAAZ method11778(classXY var1) {
      int var2 = var1.method13099(-1639067797);
      classUR var3 = this.vmethod606(var2, 1337955535);
      classAAZ var4 = new classAAZ(var2);
      Class var5 = var3.field6371.field7107;
      if (Integer.class == var5) {
         var4.field120 = classXY.method13113(var1, (byte)1);
      } else if (Long.class == var5) {
         var4.field120 = classXY.method13115(var1, -1316144705);
      } else if (var5 == String.class) {
         var4.field120 = var1.method13134(656006653);
      } else {
         if (!classXV.class.isAssignableFrom(var5)) {
            throw new IllegalStateException();
         }

         try {
            classXV var6 = (classXV)var5.newInstance();
            var6.method13008(var1, (byte)-61);
            var4.field120 = var6;
         } catch (InstantiationException var7) {
         } catch (IllegalAccessException var8) {
         }
      }

      return var4;
   }

   @ObfuscatedSignature(descriptor = "(II)Lpr;")
   @ObfuscatedName("az")
   public static StructComposition method11781(int var0, int var1) {
      int var4 = var0;
      int var5 = 64572192;

      StructComposition var10000;
      try {
         StructComposition var6 = (StructComposition)NodeCache.method6460(StructComposition.field5383, var4);
         if (var6 != null) {
            var10000 = var6;
         } else {
            byte[] var7 = StructComposition.field5389.method11942(34, var4, -1876871032);
            var6 = new StructComposition(new classXY(var7), var4);
            StructComposition.field5383.method6464(var6, var4);
            var10000 = var6;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "uy.az(" + ')');
      }

      StructComposition var2 = var10000;
      if (var2.getId() == -1) {
         var2.field5384 = var0;
         PostStructComposition var3 = new PostStructComposition();
         var3.setStructComposition(var2);
         classOE.field4841.getCallbacks().post(var3);
      }

      return var2;
   }
}
