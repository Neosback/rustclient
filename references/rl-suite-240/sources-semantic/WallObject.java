import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
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

@ObfuscatedName("fe")
public final class WallObject implements net.runelite.api.WallObject, TileObject {
   @ObfuscatedName("ag")
   int field2242;
   @ObfuscatedName("aa")
   int field2239;
   @ObfuscatedName("ae")
   public long field2238 = 0L;
   @ObfuscatedSignature(descriptor = "Lee;")
   @ObfuscatedName("af")
   public Renderable field2246;
   @ObfuscatedName("ac")
   int field2241;
   @ObfuscatedName("as")
   int field2243;
   @ObfuscatedName("ax")
   int field2244;
   @ObfuscatedName("ab")
   int field2240;
   @ObfuscatedName("ad")
   static byte[][][] field2248;
   @ObfuscatedName("iy")
   public int field2245;
   @ObfuscatedSignature(descriptor = "Lee;")
   @ObfuscatedName("az")
   public Renderable field2247;

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("js")
   public Model method4640() {
      Renderable var1 = this.method4645();
      if (var1 == null) {
         return null;
      } else {
         return var1 instanceof Model ? (Model)var1 : var1.method3853();
      }
   }

   public Point getCanvasLocation() {
      return this.getCanvasLocation(0);
   }

   public Point getMinimapLocation() {
      return Perspective.localToMinimap(classOE.field4841, this.getLocalLocation());
   }

   @ObfuscatedSignature(descriptor = "(Lee;)V")
   @ObfuscatedName("gl")
   public void method4642(Renderable var1) {
      this.field2247 = var1;
   }

   public Point getCanvasLocation(int var1) {
      return Perspective.localToCanvas(classOE.field4841, this.getLocalLocation(), this.getPlane(), var1);
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("jb")
   public Model method4639() {
      Renderable var1 = this.method4644();
      if (var1 == null) {
         return null;
      } else {
         return var1 instanceof Model ? (Model)var1 : var1.method3853();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("wy")
   public void method4643(int var1) {
      this.field2241 = var1 * 1487745865;
   }

   public Shape getConvexHull() {
      Model var1 = this.method4640();
      if (var1 == null) {
         return null;
      } else {
         int var2 = Perspective.getTileHeight(classOE.field4841, this.getLocalLocation(), this.getPlane());
         return var1.method5280(this.method4641(), this.field2242 * -777838807, this.field2243 * 31139801, 0, var2);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lee;")
   @ObfuscatedName("vg")
   public Renderable method4644() {
      return this.field2246;
   }

   public int getOrientationA() {
      return this.field2244 * 1548562339;
   }

   public int getConfig() {
      return this.field2239 * 1869636241;
   }

   @ObfuscatedSignature(descriptor = "()Lee;")
   @ObfuscatedName("ce")
   public Renderable method4645() {
      return this.field2247;
   }

   public int getX() {
      return this.field2242 * -777838807;
   }

   public int getPlane() {
      return this.field2245;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;ZB)Laar;")
   @ObfuscatedName("ae")
   public static classAAR method4636(String var0, String var1, boolean var2, byte var3) {
      try {
         File var4 = new File(classSL.field5973, "preferences" + var0 + ".dat");
         if (var4.exists()) {
            try {
               return new classAAR(var4, "rw", 10000L);
            } catch (IOException var10) {
            }
         }

         String var5 = "";
         if (33 == 1602897005 * MidiRequest.field4635) {
            if (var3 != 1) {
               throw new IllegalStateException();
            }

            var5 = "_rc";
         } else if (MidiRequest.field4635 * 1602897005 == 34) {
            if (var3 != 1) {
               throw new IllegalStateException();
            }

            var5 = "_wip";
         }

         File var6 = new File(ClanChannel.field2952, "jagex_" + var1 + "_preferences" + var0 + var5 + ".dat");
         if (!var2) {
            if (var3 != 1) {
               throw new IllegalStateException();
            }

            if (var6.exists()) {
               try {
                  return new classAAR(var6, "rw", 10000L);
               } catch (IOException var9) {
               }
            }
         }

         try {
            return new classAAR(var4, "rw", 10000L);
         } catch (IOException var8) {
            throw new RuntimeException();
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "fe.ae(" + ')');
      }
   }

   public boolean isOpShown(int var1) {
      classCD var2 = this.method4641().method3793(this.getHash());
      return var2 == null ? true : var2.method1430(var1);
   }

   public int getY() {
      return this.field2243 * 31139801;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("rf")
   public void method4646(int var1) {
      this.field2242 = var1 * -1785598695;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("bu")
   static int method4637(int var0, Script var1, boolean var2, byte var3) {
      try {
         return 2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "fe.bu(" + 41);
      }
   }

   public int getOrientationB() {
      return this.field2241 * -1413337351;
   }

   public WorldPoint getWorldLocation() {
      WorldView var1 = WorldView.method3792(this.method4638());
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

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("uv")
   public int method4638() {
      long var1 = this.getHash();
      int var3 = (int)(var1 >> 52 & 4095L);
      if (var3 == 4095L) {
         var3 = -1;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ld")
   public void method4647(int var1) {
      this.field2244 = var1 * -1380680693;
   }

   @Nullable
   public String getOpOverride(int var1) {
      classCD var2 = this.method4641().method3793(this.getHash());
      return var2 == null ? null : var2.method1431(var1);
   }

   public int getId() {
      long var1 = this.getHash();
      return (int)(var1 >>> 20 & 4294967295L);
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

   public Shape getConvexHull2() {
      Model var1 = this.method4639();
      if (var1 == null) {
         return null;
      } else {
         int var2 = Perspective.getTileHeight(classOE.field4841, this.getLocalLocation(), this.getPlane());
         return var1.method5280(this.method4641(), this.field2242 * -777838807, this.field2243 * 31139801, 0, var2);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("iz")
   public void method4648(int var1) {
      this.field2239 = var1 * -752902543;
   }

   public int getZ() {
      return this.field2240 * 1315862339;
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("hj")
   public void method4649(long var1) {
      this.field2238 = var1 * -2636099314526831141L;
   }

   public long getHash() {
      return this.field2238 * 7159317397185328723L;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ox")
   public void method4650(int var1) {
      this.field2243 = var1 * 151054441;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmm;Ljava/lang/Integer;)V")
   @ObfuscatedName("br")
   public static void method4635(GrandExchangeOffer var0, Integer var1) {
      if (var0 == null) {
         var0.getPrice();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gl")
   public void method4651(int var1) {
      this.field2240 = var1 * -697873045;
   }

   @ObfuscatedSignature(descriptor = "(Lee;)V")
   @ObfuscatedName("ou")
   public void method4652(Renderable var1) {
      this.field2246 = var1;
   }

   public Shape getClickbox() {
      WorldView var1 = this.method4641();
      Shape var2 = Perspective.getClickbox(
         classOE.field4841, var1, this.method4640(), 0, this.field2242 * -777838807, this.field2243 * 31139801, this.field2240 * 1315862339
      );
      Shape var3 = Perspective.getClickbox(
         classOE.field4841, var1, this.method4639(), 0, this.field2242 * -777838807, this.field2243 * 31139801, this.field2240 * 1315862339
      );
      if (var2 != null && var3 != null) {
         return new Shapes(new Shape[]{var2, var3});
      } else {
         return var2 != null ? var2 : var3;
      }
   }

   public LocalPoint getLocalLocation() {
      return new LocalPoint(this.getX(), this.getY(), this.method4638());
   }

   @ObfuscatedSignature(descriptor = "()Ldz;")
   @ObfuscatedName("ye")
   public WorldView method4641() {
      return WorldView.method3792(this.method4638());
   }

   public Point getCanvasTextLocation(Graphics2D var1, String var2, int var3) {
      return Perspective.getCanvasTextLocation(classOE.field4841, var1, this.getLocalLocation(), var2, var3);
   }

   WallObject() {
      this.field2239 = 0;
   }
}
