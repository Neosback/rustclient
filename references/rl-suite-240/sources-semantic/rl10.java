import java.util.ArrayList;
import java.util.List;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl10")
public class rl10 implements net.runelite.api.Tile {
   @ObfuscatedSignature(descriptor = "Lrl10;")
   @ObfuscatedName("hm")
   public rl10 field5691;
   @ObfuscatedName("ly")
   public final int field5690;
   @ObfuscatedSignature(descriptor = "Lez;")
   @ObfuscatedName("pl")
   public final Scene field5689;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgn;)I")
   @ObfuscatedName("ml")
   public static int method10067(classGN var0) {
      return var0.field2625[0].length;
   }

   public void setSceneTileModel(net.runelite.api.SceneTileModel var1) {
      this.field5689.field2006[this.field5690] = (SceneTileModel)var1;
   }

   @ObfuscatedSignature(descriptor = "()[Lfb;")
   @ObfuscatedName("ue")
   public GameObject[] method10077() {
      return new GameObject[]{
         this.field5689.field2012[this.field5690 * 5 + 0],
         this.field5689.field2012[this.field5690 * 5 + 1],
         this.field5689.field2012[this.field5690 * 5 + 2],
         this.field5689.field2012[this.field5690 * 5 + 3],
         this.field5689.field2012[this.field5690 * 5 + 4]
      };
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;)V")
   @ObfuscatedName("lp")
   public static void method10069(NameableContainer var0) {
      if (var0 == null) {
         var0.method10308();
      } else {
         var0.field5839 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ik")
   public int method10076() {
      return this.field5689.field2011[this.field5690];
   }

   @ObfuscatedSignature(descriptor = "()Lfn;")
   @ObfuscatedName("uv")
   public SceneTileModel method10074() {
      return this.field5689.field2006[this.field5690];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("wl")
   public int method10075() {
      int var1 = this.field5690 & this.field5689.field1996;
      return var1 - this.field5689.field2146;
   }

   @ObfuscatedSignature(descriptor = "()Lfj;")
   @ObfuscatedName("nx")
   public SceneTilePaint method10080() {
      return this.field5689.field2005[this.field5690];
   }

   @ObfuscatedSignature(descriptor = "()Lrl10;")
   @ObfuscatedName("ey")
   public rl10 method10081() {
      return this.field5691;
   }

   public int getRenderLevel() {
      return this.field5689.method4486(this.field5690);
   }

   @ObfuscatedSignature(descriptor = "()Leo;")
   @ObfuscatedName("bz")
   public GroundObject method10072() {
      return this.field5689.field2009[this.field5690];
   }

   public Point getSceneLocation() {
      int var1 = this.field5690 >> this.field5689.field1993 & this.field5689.field1995;
      int var2 = this.field5690 & this.field5689.field1996;
      var1 -= this.field5689.field2146;
      var2 -= this.field5689.field2146;
      return new Point(var1, var2);
   }

   public LocalPoint getLocalLocation() {
      int var1 = this.field5690 >> this.field5689.field1993 & this.field5689.field1995;
      int var2 = this.field5690 & this.field5689.field1996;
      var1 -= this.field5689.field2146;
      var2 -= this.field5689.field2146;
      return LocalPoint.fromScene(var1, var2, this.field5689);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("gn")
   public int method10078() {
      int var1 = this.field5690 >> this.field5689.field1993 & this.field5689.field1995;
      return var1 - this.field5689.field2146;
   }

   public void setSceneTilePaint(net.runelite.api.SceneTilePaint var1) {
      this.field5689.field2005[this.field5690] = (SceneTilePaint)var1;
   }

   public WorldPoint getWorldLocation() {
      int var1 = this.field5690 >> this.field5689.field1994 & 3;
      int var2 = this.field5690 >> this.field5689.field1993 & this.field5689.field1995;
      int var3 = this.field5690 & this.field5689.field1996;
      var2 -= this.field5689.field2146;
      var3 -= this.field5689.field2146;
      WorldView var4 = this.field5689.field2002;
      return WorldPoint.fromScene(var4, var2, var3, var1);
   }

   public void setGroundObject(net.runelite.api.GroundObject var1) {
      this.field5689.field2009[this.field5690] = (GroundObject)var1;
      if (var1 == null) {
         this.field5689.field2004[this.field5690] = this.field5689.field2004[this.field5690] & -2049;
      } else {
         this.field5689.field2004[this.field5690] = this.field5689.field2004[this.field5690] | 2048;
      }
   }

   public int getPlane() {
      return this.field5690 >> this.field5689.field1994 & 3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldw;Lwc;II)V")
   @ObfuscatedName("tt")
   public static void method10068(AmbientSoundEffect var0, classWC var1, int var2, int var3) {
      if (var0 == null) {
         var0.method3677();
      }

      var1.method12365((byte)-7);
      var1.method12358(var2, var3, var0.field1651.field6685, 2008698017);
      var1.method12365((byte)-3);
   }

   @ObfuscatedSignature(descriptor = "()Leq;")
   @ObfuscatedName("nm")
   public ItemLayer method10071() {
      return this.field5689.field2010[this.field5690];
   }

   @ObfuscatedSignature(descriptor = "()Lfi;")
   @ObfuscatedName("vz")
   public DecorativeObject method10079() {
      return this.field5689.field2008[this.field5690];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("sk")
   public int method10070() {
      return this.field5689.method4472(this.field5690);
   }

   @ObfuscatedSignature(descriptor = "()Lfe;")
   @ObfuscatedName("qz")
   public WallObject method10073() {
      return this.field5689.field2007[this.field5690];
   }

   public List getGroundItems() {
      ItemLayer var1 = this.method10071();
      if (var1 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();

         for (Object var3 = var1.method3982(); var3 instanceof TileItem; var3 = ((Node)var3).method12134()) {
            var2.add((TileItem)var3);
         }

         return var2;
      }
   }

   public rl10(Scene var1, int var2) {
      this.field5689 = var1;
      this.field5690 = var2;
   }
}
