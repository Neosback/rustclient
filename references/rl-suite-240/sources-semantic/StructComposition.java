import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pr")
public class StructComposition extends classVJ implements net.runelite.api.StructComposition, rl9 {
   @rl8(method10167 = 16)
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("ab")
   IterableHashTable field5385;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   public static final int field5387 = 2097152;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ae")
   static NodeCache field5383 = new NodeCache(64);
   @ObfuscatedName("ow")
   public int field5384 = -1;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5388 = 249;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("af")
   public static IndexDataBase field5389;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field5386 = 30;

   public net.runelite.api.IterableHashTable getParams() {
      return this.field5385;
   }

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("ae")
   public int method9212(int var1, int var2, byte var3) {
      try {
         return classGQ.method5635(this.field5385, var1, var2, -828988955);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pr.ae(" + 41);
      }
   }

   public int getId() {
      return this.field5384;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("al")
   public int method9213(int var1, int var2) {
      return classGQ.method5635(this.field5385, var1, var2, 373211485);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpr;")
   @ObfuscatedName("as")
   public static StructComposition method9210(int var0) {
      StructComposition var1 = (StructComposition)NodeCache.method6460(field5383, var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = field5389.method11942(-281613, var0, -1592183963);
         var1 = new StructComposition(new classXY(var2), var0);
         field5383.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("ab")
   public long method9215(int var1, long var2) {
      try {
         return classBW.method1275(this.field5385, var1, var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pr.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public static void method9211() {
      field5383.method6468();
   }

   @ObfuscatedSignature(descriptor = "(Lpr;ILjava/lang/String;S)Ljava/lang/String;")
   @ObfuscatedName("hs")
   public static String method9219(StructComposition var0, int var1, String var2, short var3) {
      if (var0 == null) {
         var0.method9220(var1, var2, var3);
      }

      try {
         return classFS.method4998(var0.field5385, var1, var2, (byte)99);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pr.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ac")
   public int method9214(int var1, int var2) {
      return classGQ.method5635(this.field5385, var1, var2, -349746830);
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("aj")
   public long method9216(int var1, long var2) {
      return classBW.method1275(this.field5385, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("ay")
   public long method9217(int var1, long var2) {
      return classBW.method1275(this.field5385, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("aq")
   public long method9218(int var1, long var2) {
      return classBW.method1275(this.field5385, var1, var2);
   }

   StructComposition(classXY var1, int var2) {
      IterableHashTable var3 = null;
      if (null != var1 && var1.field6952 != null) {
         label32:
         while (true) {
            int var4 = var1.method13095(-346779531);
            switch (var4) {
               case 0:
                  break label32;
               case 249:
                  var3 = classQH.method9572(var1, var3, 1978086108);
            }
         }
      }

      if (var3 == null) {
         this.field5385 = new IterableHashTable(1);
      } else {
         this.field5385 = var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lyn;)V")
   @ObfuscatedName("pr")
   @Override
   public void vmethod537(IterableHashTable var1) {
      this.field5385 = var1;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;S)Ljava/lang/String;")
   @ObfuscatedName("ag")
   public String method9220(int var1, String var2, short var3) {
      try {
         return classFS.method4998(this.field5385, var1, var2, (byte)99);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pr.ag(" + ')');
      }
   }
}
