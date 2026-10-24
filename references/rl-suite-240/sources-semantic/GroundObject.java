import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.datatransfer.Clipboard;
import javax.annotation.Nullable;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.TileObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("eo")
public final class GroundObject implements net.runelite.api.GroundObject, TileObject {
   @ObfuscatedName("as")
   int field1807;
   @ObfuscatedName("af")
   public long field1813;
   @ObfuscatedName("ae")
   int field1805;
   @ObfuscatedName("bl")
   static int field1809;
   @ObfuscatedName("qh")
   public int field1812;
   @ObfuscatedName("ag")
   int field1806;
   @ObfuscatedName("ab")
   int field1804;
   @ToRemove(unused = "true")
   @ObfuscatedName("bk")
   static final int field1808 = 74;
   @ToRemove(unused = "true")
   @ObfuscatedName("cq")
   static final int field1810 = 152;
   @ToRemove(unused = "true")
   @ObfuscatedName("cv")
   public static final int field1811 = 82;
   @ObfuscatedSignature(descriptor = "Lee;")
   @ObfuscatedName("az")
   public Renderable field1814;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final byte field1803 = 1;

   public Point getCanvasTextLocation(Graphics2D var1, String var2, int var3) {
      return Perspective.getCanvasTextLocation(classOE.field4841, var1, this.getLocalLocation(), var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("be")
   public void method3969(long var1) {
      this.field1813 = var1 * -8879266543907161229L;
   }

   @ObfuscatedSignature(descriptor = "()Ldz;")
   @ObfuscatedName("qk")
   public WorldView method3968() {
      return WorldView.method3792(this.method3967());
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   void method3962(int var1) {
      this.field1807 = -845194935 * var1;
   }

   public Point getCanvasLocation(int var1) {
      return Perspective.localToCanvas(classOE.field4841, this.getLocalLocation(), this.getPlane(), var1);
   }

   public boolean isOpShown(int var1) {
      classCD var2 = this.method3968().method3793(this.getHash());
      return var2 == null ? true : var2.method1430(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fj")
   public void method3970(int var1) {
      this.field1804 = var1 * 19534541;
   }

   @ObfuscatedSignature(descriptor = "(Leo;IB)V")
   @ObfuscatedName("rs")
   public static void method3963(GroundObject var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method3964(var1, var2);
      } else {
         try {
            var0.field1807 = -845194935 * var1;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "eo.af(" + ')');
         }
      }
   }

   public int getX() {
      return this.field1804 * 1333275141;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("mc")
   public void method3971(int var1) {
      this.field1807 = var1 * -845194935;
   }

   public LocalPoint getLocalLocation() {
      return new LocalPoint(this.getX(), this.getY(), this.method3967());
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   int method3956() {
      return this.field1807 * -1235687175;
   }

   public int getZ() {
      return this.field1805 * -276908185;
   }

   public Polygon getCanvasTilePoly() {
      int var1 = 1;
      int var2 = 1;
      if (this instanceof GameObject) {
         GameObject var3 = (GameObject)this;
         var1 = var3.field2184 * 304142753 - var3.field2186 * 1138017605 + 1;
         var2 = var3.field2190 * -544424899 - var3.field2192 * -555977777 + 1;
      }

      return Perspective.getCanvasTileAreaPoly(classOE.field4841, this.getLocalLocation(), var1, var2, this.getPlane(), 0);
   }

   @ObfuscatedSignature(descriptor = "(Lee;)V")
   @ObfuscatedName("tv")
   public void method3972(Renderable var1) {
      this.field1814 = var1;
   }

   public Point getCanvasLocation() {
      return this.getCanvasLocation(0);
   }

   public int getPlane() {
      return this.field1812;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   int method3957() {
      return this.field1807 * -1235687175;
   }

   @ObfuscatedSignature(descriptor = "()Lee;")
   @ObfuscatedName("zp")
   public Renderable method3973() {
      return this.field1814;
   }

   public Shape getClickbox() {
      return Perspective.getClickbox(
         classOE.field4841, this.method3968(), this.method3966(), 0, this.field1804 * 1333275141, this.field1806 * 498815127, this.field1805 * -276908185
      );
   }

   public int getId() {
      long var1 = this.getHash();
      return (int)(var1 >>> 20 & 4294967295L);
   }

   public int getY() {
      return this.field1806 * 498815127;
   }

   @ObfuscatedSignature(descriptor = "(Leo;I)I")
   @ObfuscatedName("jy")
   public static int method3958(GroundObject var0, int var1) {
      if (var0 == null) {
         var0.method3961(var1);
      }

      try {
         return var0.field1807 * -1235687175;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "eo.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("qg")
   public void method3974(int var1) {
      this.field1806 = var1 * -1962070233;
   }

   public WorldPoint getWorldLocation() {
      WorldView var1 = WorldView.method3792(this.method3967());
      if (var1 == null) {
         var1 = classIS.field3051;
      }

      if (this instanceof GameObject) {
         GameObject var2 = (GameObject)this;
         int var3 = var2.field2186 * 1138017605;
         int var4 = var2.field2192 * -555977777;
         int var5 = var2.field2184 * 304142753 - var3;
         int var6 = var2.field2190 * -544424899 - var4;
         return WorldPoint.fromScene(var1, var3 + var5 / 2, var4 + var6 / 2, this.getPlane());
      } else {
         return WorldPoint.fromLocal(var1, this.getX(), this.getY(), this.getPlane());
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)Ljava/awt/datatransfer/Clipboard;")
   @ObfuscatedName("gt")
   public static Clipboard method3965(GameEngine var0) {
      return var0.field6220;
   }

   GroundObject() {
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   public void method3975(int var1) {
      this.field1805 = var1 * 476279895;
   }

   public Shape getConvexHull() {
      Model var1 = this.method3966();
      return var1 == null ? null : var1.method5280(this.method3968(), this.getX(), this.getY(), 0, this.getZ());
   }

   @Nullable
   public String getOpOverride(int var1) {
      classCD var2 = this.method3968().method3793(this.getHash());
      return var2 == null ? null : var2.method1431(var1);
   }

   public Point getMinimapLocation() {
      return Perspective.localToMinimap(classOE.field4841, this.getLocalLocation());
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("as")
   int method3959() {
      return this.field1807 * -1235687175;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("do")
   public int method3967() {
      long var1 = this.getHash();
      int var3 = (int)(var1 >> 52 & 4095L);
      if (var3 == 4095L) {
         var3 = -1;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ag")
   int method3960() {
      return this.field1807 * -172950048;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   int method3961(int var1) {
      try {
         return this.field1805 * -1235687175;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "eo.az(" + 41);
      }
   }

   public long getHash() {
      return this.field1813 * 5132338419383099835L;
   }

   public int getConfig() {
      return this.field1807 * -1235687175;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ei")
   public Model method3966() {
      Renderable var1 = this.method3973();
      if (var1 == null) {
         return null;
      } else {
         return var1 instanceof Model ? (Model)var1 : var1.method3853();
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("af")
   void method3964(int var1, byte var2) {
      try {
         this.field1806 = -845194935 * var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "eo.af(" + ')');
      }
   }
}
