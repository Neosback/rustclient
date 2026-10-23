import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ObfuscatedName("rl8")
public @interface rl8 {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnw;)Z")
   @ObfuscatedName("hq")
   static boolean method10166(classNW var0) {
      if (var0 == null) {
         var0.method8278();
      }

      return var0.field4784 != var0.field4783.field4780 && var0.field4784 != null;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("sp")
   int method10167();
}
