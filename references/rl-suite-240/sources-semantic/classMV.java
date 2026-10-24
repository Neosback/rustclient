import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mv")
public class classMV {
   @ObfuscatedName("ae")
   public static Comparator field4604 = new classMY();
   @ObfuscatedName("ab")
   public static Comparator field4605 = new classMC();
   @ObfuscatedName("af")
   public static Comparator field4603 = new classMO();
   @ObfuscatedName("az")
   public static Comparator field4602 = new classMX();
   @ObfuscatedName("ag")
   public final List field4606;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field4607 = 18;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcj;Lcl;)V")
   @ObfuscatedName("zs")
   public static void method7772(classCJ var0, classCL var1) {
      if (var0 == null) {
         var0.method1523(var1);
      }

      classUZ var2 = var0.method1525(
         -1159045401 * var0.field751.field6074,
         -212202065 * var0.field751.field6077,
         112069096 * var0.field751.field6076,
         var0.field751.field6073 * -178421715,
         var1,
         -77101865
      );
      var2.field6425 = var2.field6425 + 870654719 * var0.field751.field6078;
      var2.field6423 = var2.field6423 - 59218765 * var0.field751.field6086;
      var2.field6424 = var2.field6424 + var0.field751.field6089 * 645704545;
      var0.field759 = var0.field754 = var2.field6425;
      var0.field752 = var0.field745 = var2.field6423;
      var0.field753 = var0.field748 = var2.field6424;
   }

   static {
      new classNA();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Comparator;ZB)V")
   @ObfuscatedName("az")
   public void method7773(Comparator var1, boolean var2, byte var3) {
      try {
         if (var2) {
            if (var3 >= -1) {
               return;
            }

            Collections.sort(this.field4606, var1);
         } else {
            Collections.sort(this.field4606, Collections.reverseOrder(var1));
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mv.az(" + ')');
      }
   }

   public classMV(classXY var1, boolean var2) {
      int var3 = var1.method13099(1043215320);
      boolean var4 = var1.method13095(-346779531) == 1;
      byte var5;
      if (var4) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      int var6 = var1.method13099(1135316952);
      this.field4606 = new ArrayList(var6);

      for (int var7 = 0; var7 < var6; var7++) {
         this.field4606.add(new classNM(var1, var5, var3));
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Comparator;Z)V")
   @ObfuscatedName("af")
   public void method7774(Comparator var1, boolean var2) {
      if (var2) {
         Collections.sort(this.field4606, var1);
      } else {
         Collections.sort(this.field4606, Collections.reverseOrder(var1));
      }
   }
}
