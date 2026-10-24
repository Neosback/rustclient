import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("my")
final class classMY implements Comparator {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4618 = 10;
   @ToRemove(unused = "true")
   @ObfuscatedName("bb")
   static final int field4619 = 81;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field4616 = 7;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4617 = 4;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method7792(Object var1) {
      return super.equals(var1);
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return method7788(this, (classNM)var1, (classNM)var2, 6041249);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "my.compare(" + 41);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "my.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method7793(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method7790(Object var1, Object var2) {
      return method7788(this, (classNM)var1, (classNM)var2, -1798948815);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method7791(Object var1, Object var2) {
      return method7788(this, (classNM)var1, (classNM)var2, 853304648);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("as")
   public static boolean method7787(int var0, int var1) {
      try {
         boolean var10000;
         if (var0 >= classKB.field3525.field3536 * 78062377 && var0 <= classKB.field3528.field3536 * 78062377) {
            if (var1 <= -49744082) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "my.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lmy;Lnm;Lnm;I)I")
   @ObfuscatedName("pb")
   public static int method7788(classMY var0, classNM var1, classNM var2, int var3) {
      if (var0 == null) {
         return var0.method7789(var1, var1, var3);
      } else {
         try {
            return var1.method7934(985767106).compareTo(var2.method7934(985767106));
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "my.az(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;I)I")
   @ObfuscatedName("az")
   int method7789(classNM var1, classNM var2, int var3) {
      try {
         return var1.method7934(985767106).compareTo(var2.method7934(985767106));
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "my.az(" + 41);
      }
   }
}
