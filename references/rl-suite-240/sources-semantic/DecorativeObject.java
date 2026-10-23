import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import javax.annotation.Nullable;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.TileObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.geometry.Shapes;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fi")
public final class DecorativeObject implements net.runelite.api.DecorativeObject, TileObject {
   @ObfuscatedName("aa")
   int field2265;
   @ObfuscatedName("ay")
   int field2274;
   @ObfuscatedName("ae")
   public long field2264 = 0L;
   @ObfuscatedName("ao")
   int field2271;
   @ObfuscatedSignature(descriptor = "Lee;")
   @ObfuscatedName("az")
   public Renderable field2277;
   @ObfuscatedName("al")
   int field2270;
   @ObfuscatedName("ch")
   public int field2275;
   @ObfuscatedName("aj")
   int field2269;
   @ObfuscatedName("ax")
   int field2273;
   @ObfuscatedName("ab")
   int field2266;
   @ObfuscatedName("ag")
   int field2268;
   @ObfuscatedName("ac")
   int field2267;
   @ObfuscatedSignature(descriptor = "Lee;")
   @ObfuscatedName("af")
   public Renderable field2276;
   @ObfuscatedName("as")
   int field2263 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   public static final int field2272 = 262144;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("pj")
   public void method4802(int var1) {
      this.field2269 = var1 * 1259333051;
   }

   @Nullable
   public String getOpOverride(int var1) {
      classCD var2 = this.method4800().method3793(this.getHash());
      return var2 == null ? null : var2.method1431(var1);
   }

   @ObfuscatedSignature(descriptor = "()Ldz;")
   @ObfuscatedName("me")
   public WorldView method4800() {
      return WorldView.method3792(this.method4798());
   }

   public Point getCanvasTextLocation(Graphics2D var1, String var2, int var3) {
      return Perspective.getCanvasTextLocation(classOE.field4841, var1, this.getLocalLocation(), var2, var3);
   }

   public int getConfig() {
      return this.field2263 * -1506294211;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("vc")
   public int method4798() {
      long var1 = this.getHash();
      int var3 = (int)(var1 >> 52 & 4095L);
      if (var3 == 4095L) {
         var3 = -1;
      }

      return var3;
   }

   public boolean isOpShown(int var1) {
      classCD var2 = this.method4800().method3793(this.getHash());
      return var2 == null ? true : var2.method1430(var1);
   }

   public int getXOffset() {
      return this.field2274 * -404927711;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mr")
   public int method4803() {
      return this.field2265 * -819410985;
   }

   public int getXOffset2() {
      return this.field2267 * -1085897215;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("jl")
   public void method4804(int var1) {
      this.field2263 = var1 * -47677163;
   }

   public int getId() {
      long var1 = this.getHash();
      return (int)(var1 >>> 20 & 4294967295L);
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("cg")
   public Model method4799() {
      Renderable var1 = this.method4810();
      if (var1 == null) {
         return null;
      } else {
         Model var2;
         if (var1 instanceof Model) {
            var2 = (Model)var1;
         } else {
            var2 = var1.method3853();
         }

         return var2;
      }
   }

   public Shape getConvexHull2() {
      Model var1 = this.method4799();
      return var1 == null
         ? null
         : var1.method5280(
            this.method4800(),
            this.field2273 * 1766830743 + this.field2267 * -1085897215,
            this.field2266 * -361702377 + this.field2270 * 513256357,
            0,
            this.getZ()
         );
   }

   @ObfuscatedSignature(descriptor = "()Lee;")
   @ObfuscatedName("vg")
   public Renderable method4805() {
      return this.field2277;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("em")
   public void method4806(int var1) {
      this.field2266 = var1 * 1214651303;
   }

   public WorldPoint getWorldLocation() {
      WorldView var1 = WorldView.method3792(this.method4798());
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

   public int getYOffset() {
      return this.field2268 * 856605829;
   }

   public int getPlane() {
      return this.field2275;
   }

   public int getY() {
      return this.field2266 * -361702377;
   }

   public int getYOffset2() {
      return this.field2270 * 513256357;
   }

   public LocalPoint getLocalLocation() {
      return new LocalPoint(this.getX(), this.getY(), this.method4798());
   }

   public long getHash() {
      return this.field2264 * -3608662145112923099L;
   }

   @ObfuscatedSignature(descriptor = "(Lee;)V")
   @ObfuscatedName("oz")
   public void method4807(Renderable var1) {
      this.field2276 = var1;
   }

   public int getZ() {
      return this.field2271 * 1698079433;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("zj")
   public void method4808(int var1) {
      this.field2265 = var1 * 1071651815;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lq")
   public void method4809(int var1) {
      this.field2271 = var1 * 765794169;
   }

   @ObfuscatedSignature(descriptor = "()Lee;")
   @ObfuscatedName("ne")
   public Renderable method4810() {
      return this.field2276;
   }

   public Point getCanvasLocation() {
      return this.getCanvasLocation(0);
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

   public Point getCanvasLocation(int var1) {
      return Perspective.localToCanvas(classOE.field4841, this.getLocalLocation(), this.getPlane(), var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;Lef;I)V")
   @ObfuscatedName("hg")
   public static void method4797(Model var0, classEF var1, int var2) {
      if (var0 == null) {
         var0.getTexIndices3();
      }

      var0.method5277(var1, var2);
   }

   public Shape getConvexHull() {
      Model var1 = this.method4801();
      return var1 == null
         ? null
         : var1.method5280(
            this.method4800(),
            this.field2273 * 1766830743 + this.field2274 * -404927711,
            this.field2266 * -361702377 + this.field2268 * 856605829,
            0,
            this.getZ()
         );
   }

   public int getX() {
      return this.field2273 * 1766830743;
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("zi")
   public void method4811(long var1) {
      this.field2264 = var1 * 2170180156281739181L;
   }

   public Shape getClickbox() {
      WorldView var1 = this.method4800();
      Shape var2 = Perspective.getClickbox(
         classOE.field4841,
         var1,
         this.method4801(),
         0,
         this.field2273 * 1766830743 + this.field2274 * -404927711,
         this.field2266 * -361702377 + this.field2268 * 856605829,
         this.field2271 * 1698079433
      );
      Shape var3 = Perspective.getClickbox(
         classOE.field4841,
         var1,
         this.method4799(),
         0,
         this.field2273 * 1766830743 + this.field2267 * -1085897215,
         this.field2266 * -361702377,
         this.field2271 * 1698079433 + this.field2270 * 513256357
      );
      if (var2 != null && var3 != null) {
         return new Shapes(new Shape[]{var2, var3});
      } else {
         return var2 != null ? var2 : var3;
      }
   }

   public Point getMinimapLocation() {
      return Perspective.localToMinimap(classOE.field4841, this.getLocalLocation());
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("es")
   public Model method4801() {
      Renderable var1 = this.method4805();
      if (var1 == null) {
         return null;
      } else {
         Model var2;
         if (var1 instanceof Model) {
            var2 = (Model)var1;
         } else {
            var2 = var1.method3853();
         }

         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("qs")
   public void method4812(int var1) {
      this.field2273 = var1 * -697635545;
   }

   @ObfuscatedSignature(descriptor = "(Lee;)V")
   @ObfuscatedName("ae")
   public void method4813(Renderable var1) {
      this.field2277 = var1;
   }

   DecorativeObject() {
   }
}
