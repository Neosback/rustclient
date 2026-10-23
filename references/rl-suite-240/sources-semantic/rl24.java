import java.util.IdentityHashMap;
import java.util.Map;
import net.runelite.api.RuneLiteObjectController;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl24")
public class rl24 extends Renderable {
   @ObfuscatedName("qq")
   public final RuneLiteObjectController field5750;
   @ObfuscatedName("cm")
   public static Map field5751 = new IdentityHashMap();
   @ObfuscatedName("ve")
   public int field5752;

   @ObfuscatedSignature(descriptor = "(Ldz;)V")
   @ObfuscatedName("xf")
   public static void method10115(WorldView var0) {
      for (WorldEntity var2 : var0.worldEntities()) {
         method10115(var2.field6010);
      }

      for (rl24 var3 = (rl24)var0.field1683.method7976(); var3 != null; var3 = (rl24)var0.field1683.method7977()) {
         var3.method10116(var0);
      }
   }

   @Override
   public int getRenderMode() {
      return this.field5750.getRenderMode();
   }

   @ObfuscatedSignature(descriptor = "(Lnet/runelite/api/RuneLiteObjectController;)V")
   @ObfuscatedName("da")
   public static void method10112(RuneLiteObjectController var0) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         rl24 var1 = (rl24)field5751.remove(var0);
         if (var1 != null) {
            var1.method12135();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnet/runelite/api/RuneLiteObjectController;)V")
   @ObfuscatedName("rv")
   public static void method10113(RuneLiteObjectController var0) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         if (!field5751.containsKey(var0)) {
            rl24 var1 = new rl24(var0);
            field5751.put(var0, var1);
            WorldView var2 = classOE.field4841.method2298(var0.getWorldView());
            var2.field1683.method7974(var1);
         }
      }
   }

   public rl24(RuneLiteObjectController var1) {
      this.field5750 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)Laag;")
   @ObfuscatedName("tk")
   public static classAAG method10111(classLH var0) {
      return var0.field4157.method13915(var0.field4150 * 1072642345, var0.field4155 * -698145269, 389044079);
   }

   @ObfuscatedSignature(descriptor = "(Ldz;)V")
   @ObfuscatedName("ke")
   public void method10116(WorldView var1) {
      try {
         this.field5750.tick(client.field950 * -322689719);
         if (this.method12134() == null) {
            return;
         }

         var1.field1687
            .method4524(
               this.field5750.getLevel(),
               this.field5750.getX(),
               this.field5750.getY(),
               this.field5750.getZ(),
               this.field5750.getRadius(),
               this,
               this.field5750.getOrientation(),
               -1L,
               this.field5750.isDrawFrontTilesFirst()
            );
      } catch (Exception var3) {
         if (this.field5752 < 10) {
            this.field5752++;
            client.field1028.warn("Exception ticking RuneLiteObjectController {}", this.field5750, var3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnet/runelite/api/RuneLiteObjectController;)Z")
   @ObfuscatedName("ti")
   public static boolean method10114(RuneLiteObjectController var0) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         return field5751.containsKey(var0);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("ae")
   @Override
   public Model vmethod104(int var1) {
      try {
         return (Model)this.field5750.getModel();
      } catch (Exception var3) {
         if (this.field5752 < 10) {
            this.field5752++;
            client.field1028.warn("Exception rendering RuneLiteObjectController {}", this.field5750, var3);
         }

         return null;
      }
   }
}
