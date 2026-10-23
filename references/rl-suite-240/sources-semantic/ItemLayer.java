import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import javax.annotation.Nullable;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.TileObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.JavaScriptCallback;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("eq")
public final class ItemLayer implements net.runelite.api.ItemLayer, TileObject {
   @ObfuscatedSignature(descriptor = "Lee;")
   @ObfuscatedName("ag")
   Renderable field1844;
   @ObfuscatedName("ae")
   int field1837;
   @ObfuscatedName("az")
   int field1839;
   @ObfuscatedName("tg")
   public int field1841;
   @ObfuscatedSignature(descriptor = "Lee;")
   @ObfuscatedName("ab")
   Renderable field1843;
   @ObfuscatedName("af")
   int field1838;
   @ObfuscatedSignature(descriptor = "Lee;")
   @ObfuscatedName("as")
   Renderable field1845;
   @ObfuscatedName("ax")
   long field1842;
   @ObfuscatedName("ac")
   int field1836;
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   static final int field1840 = 97;

   public Point getMinimapLocation() {
      return Perspective.localToMinimap(classOE.field4841, this.getLocalLocation());
   }

   @ObfuscatedSignature(descriptor = "()Lee;")
   @ObfuscatedName("mf")
   public Renderable method3981() {
      return this.field1845;
   }

   ItemLayer() {
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

   @ObfuscatedSignature(descriptor = "()Ldz;")
   @ObfuscatedName("kc")
   public WorldView method3979() {
      return WorldView.method3792(this.method3978());
   }

   public Point getCanvasLocation() {
      return this.getCanvasLocation(0);
   }

   public Shape getClickbox() {
      Area var1 = new Area();
      WorldView var2 = this.method3979();

      for (Renderable var6 : new Renderable[]{this.method3982(), this.method3981(), this.method3983()}) {
         if (var6 != null) {
            Model var7 = var6.method3853();
            if (var7 != null) {
               Shape var8 = Perspective.getClickbox(
                  classOE.field4841,
                  var2,
                  var7,
                  0,
                  this.field1838 * -892202585,
                  this.field1837 * -1209739927,
                  this.field1839 * 1186822141 - this.field1836 * 218008627
               );
               if (var8 != null) {
                  var1.add(new Area(var8));
               }
            }
         }
      }

      return var1;
   }

   public long getHash() {
      return this.field1842;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;I)V")
   @ObfuscatedName("au")
   public static void method3980(Model var0, int var1) {
      if (var0 == null) {
         var0.method5241(var1);
      } else {
         if (var1 != 0) {
            for (int var2 = 0; var2 < var0.field2422; var2++) {
               var0.field2428[var2] = var0.field2428[var2] + var1;
            }
         }
      }
   }

   public int getZ() {
      return this.field1839 * 1186822141;
   }

   public LocalPoint getLocalLocation() {
      return new LocalPoint(this.getX(), this.getY(), this.method3978());
   }

   @ObfuscatedSignature(descriptor = "()Lee;")
   @ObfuscatedName("nx")
   public Renderable method3982() {
      return this.field1843;
   }

   public int getPlane() {
      return this.field1841;
   }

   public int getHeight() {
      return this.field1836 * 218008627;
   }

   public int getY() {
      return this.field1837 * -1209739927;
   }

   public Point getCanvasLocation(int var1) {
      return Perspective.localToCanvas(classOE.field4841, this.getLocalLocation(), this.getPlane(), var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ck")
   public void method3977(int var1) {
      this.field1841 = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("yd")
   public int method3978() {
      long var1 = this.getHash();
      int var3 = (int)(var1 >> 52 & 4095L);
      if (var3 == 4095L) {
         var3 = -1;
      }

      return var3;
   }

   @Nullable
   public String getOpOverride(int var1) {
      classCD var2 = this.method3979().method3793(this.getHash());
      return var2 == null ? null : var2.method1431(var1);
   }

   public int getId() {
      long var1 = this.getHash();
      return (int)(var1 >>> 20 & 4294967295L);
   }

   public int getX() {
      return this.field1838 * -892202585;
   }

   public WorldPoint getWorldLocation() {
      WorldView var1 = WorldView.method3792(this.method3978());
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

   @ObfuscatedSignature(descriptor = "()Lee;")
   @ObfuscatedName("dn")
   public Renderable method3983() {
      return this.field1844;
   }

   public Point getCanvasTextLocation(Graphics2D var1, String var2, int var3) {
      return Perspective.getCanvasTextLocation(classOE.field4841, var1, this.getLocalLocation(), var2, var3);
   }

   public boolean isOpShown(int var1) {
      classCD var2 = this.method3979().method3793(this.getHash());
      return var2 == null ? true : var2.method1430(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lzr;IIB)V")
   @ObfuscatedName("af")
   static void method3976(ScriptEvent var0, int var1, int var2, byte var3) {
      Object[] var4 = var0.getArguments();
      if (var4 != null && var4.length > 0 && var4[0] instanceof JavaScriptCallback) {
         try {
            ((JavaScriptCallback)var4[0]).run(var0);
         } catch (Exception var17) {
            client.field1028.error("Error in JavaScriptCallback", var17);
         }
      } else {
         try {
            client.field1132 = var0;
            int var9 = var2;
            int var8 = var1;
            ScriptEvent var7 = var0;
            byte var10 = -55;

            try {
               Object[] var11 = var7.method14142(2099330486);
               Script var12;
               if (classTS.method11184(ScriptEvent.method14149(var7, 335454777), -324749371)) {
                  if (var10 >= 1) {
                     throw new IllegalStateException();
                  }

                  classAC.field132 = (classHV)var11[0];
                  classPS var13 = SceneTilePaint.method4817(classAC.field132.field2895 * -753629693, (byte)-48);
                  var12 = classCE.method1434(ScriptEvent.method14149(var7, -2116113327), var13.field5403 * 872689451, 1254355271 * var13.field5402, (short)5691);
               } else if (classJG.method6407(ScriptEvent.method14149(var7, 530150576), -1688993416)) {
                  if (var10 >= 1) {
                     throw new IllegalStateException();
                  }

                  int var20 = (Integer)var11[0];
                  var12 = classCE.method1434(ScriptEvent.method14149(var7, -2002140776), var20, -1, (short)20718);
               } else if (classMZ.method7794(ScriptEvent.method14149(var7, -866893993), (byte)-97)) {
                  int var21 = (Integer)var11[0];
                  var12 = classCE.method1434(ScriptEvent.method14149(var7, 1200058477), var21, -1, (short)13921);
               } else if (ScriptEvent.method14149(var7, -1488556078) == 86) {
                  int var22 = (Integer)var11[0];
                  var12 = classCE.method1434(ScriptEvent.method14149(var7, 1538912943), var22, -1, (short)24104);
               } else {
                  int var23 = (Integer)var11[0];
                  var12 = classSK.method10506(var23, 2038806036);
               }

               if (null != var12) {
                  if (var10 >= 1) {
                     throw new IllegalStateException();
                  }

                  classLD.method6799(var7, var12, var8, var9, 1450398201);
               }
            } catch (RuntimeException var18) {
               throw classEG.method3899(var18, "eq.af(" + ')');
            }
         } finally {
            client.field1102 = null;
         }
      }
   }
}
