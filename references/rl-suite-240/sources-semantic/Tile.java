import java.util.ArrayList;
import java.util.List;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ew")
public final class Tile extends Node implements net.runelite.api.Tile {
   @ObfuscatedSignature(descriptor = "Leo;")
   @ObfuscatedName("au")
   GroundObject field1965;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field1957 = 50;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field1963 = 5;
   @ObfuscatedSignature(descriptor = "Lfn;")
   @ObfuscatedName("ab")
   public SceneTileModel field1971;
   @ObfuscatedName("bn")
   int field1964;
   @ObfuscatedName("as")
   int field1961;
   @ObfuscatedSignature(descriptor = "[Lfb;")
   @ObfuscatedName("ax")
   GameObject[] field1949 = new GameObject[5];
   @ObfuscatedName("ac")
   int field1956;
   @ObfuscatedName("aa")
   int field1954 = 1699605829;
   @ObfuscatedName("al")
   int field1947;
   @ObfuscatedName("an")
   int field1960;
   @ObfuscatedName("aj")
   int field1950;
   @ObfuscatedName("ay")
   int field1955;
   @ObfuscatedSignature(descriptor = "Lfe;")
   @ObfuscatedName("ad")
   WallObject field1968;
   @ObfuscatedSignature(descriptor = "Lfj;")
   @ObfuscatedName("ae")
   public SceneTilePaint field1970;
   @ObfuscatedSignature(descriptor = "Lfi;")
   @ObfuscatedName("ap")
   DecorativeObject field1969;
   @ObfuscatedName("aq")
   int field1948;
   @ObfuscatedName("ai")
   int[] field1951;
   @ObfuscatedName("ar")
   int field1952;
   @ObfuscatedName("ao")
   ArrayList field1953 = null;
   @ObfuscatedName("ak")
   boolean field1972;
   @ObfuscatedName("av")
   boolean field1974;
   @ObfuscatedName("at")
   boolean field1973;
   @ObfuscatedSignature(descriptor = "Leq;")
   @ObfuscatedName("ag")
   ItemLayer field1966;
   @ObfuscatedName("am")
   int field1959;
   @ObfuscatedName("ah")
   int field1958;
   @ObfuscatedName("aw")
   int field1962;
   @ObfuscatedSignature(descriptor = "Lew;")
   @ObfuscatedName("bx")
   Tile field1967;

   @ObfuscatedSignature(descriptor = "()Leo;")
   @ObfuscatedName("ab")
   public GroundObject method4125() {
      return this.field1965;
   }

   public Point getSceneLocation() {
      throw new IllegalStateException();
   }

   public WorldPoint getWorldLocation() {
      throw new IllegalStateException();
   }

   @ObfuscatedSignature(descriptor = "(S)Z")
   @ObfuscatedName("af")
   boolean method4122(short var1) {
      try {
         if (this.field1953 != null) {
            if (var1 == 256) {
               throw new IllegalStateException();
            }

            if (!this.field1953.isEmpty()) {
               return true;
            }
         }

         return false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ew.af(" + ')');
      }
   }

   public List getGroundItems() {
      throw new IllegalStateException();
   }

   @ObfuscatedSignature(descriptor = "()Lew;")
   @ObfuscatedName("nc")
   public Tile method4126() {
      return this.field1967;
   }

   public void setSceneTilePaint(net.runelite.api.SceneTilePaint var1) {
      this.field1970 = (SceneTilePaint)var1;
   }

   @ObfuscatedSignature(descriptor = "()[Lfb;")
   @ObfuscatedName("jq")
   public GameObject[] method4127() {
      return this.field1949;
   }

   @ObfuscatedSignature(descriptor = "(I)Leo;")
   @ObfuscatedName("az")
   public GroundObject method4119(int var1) {
      try {
         return this.field1965;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ew.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfj;")
   @ObfuscatedName("ru")
   public SceneTilePaint method4128() {
      return this.field1970;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;)V")
   @ObfuscatedName("en")
   public static void method4124(Scene var0) {
      if (var0 == null) {
         var0.getOverrideAmount();
      }

      var0.field2031 = true;
   }

   @ObfuscatedSignature(descriptor = "()Leq;")
   @ObfuscatedName("qe")
   public ItemLayer method4129() {
      return this.field1966;
   }

   public void setSceneTileModel(net.runelite.api.SceneTileModel var1) {
      this.field1971 = (SceneTileModel)var1;
   }

   public LocalPoint getLocalLocation() {
      throw new IllegalStateException();
   }

   public int getPlane() {
      return this.field1947 * 1483076705;
   }

   Tile(int var1, int var2, int var3) {
      this.field1951 = new int[5];
      this.field1952 = 0;
      this.field1948 = (this.field1947 = -1766608991 * var1) * -754964563;
      this.field1950 = var2 * 1003910231;
      this.field1955 = 1268069693 * var3;
   }

   public int getRenderLevel() {
      return this.field1948 * 800564741;
   }

   @ObfuscatedSignature(descriptor = "()Leo;")
   @ObfuscatedName("ag")
   public GroundObject method4120() {
      return this.field1965;
   }

   @ObfuscatedSignature(descriptor = "()Leo;")
   @ObfuscatedName("oh")
   public GroundObject method4121() {
      return this.field1965;
   }

   @ObfuscatedSignature(descriptor = "()Lfn;")
   @ObfuscatedName("gw")
   public SceneTileModel method4130() {
      return this.field1971;
   }

   @ObfuscatedSignature(descriptor = "()Lfi;")
   @ObfuscatedName("jk")
   public DecorativeObject method4131() {
      return this.field1969;
   }

   @ObfuscatedSignature(descriptor = "()Lfe;")
   @ObfuscatedName("cn")
   public WallObject method4132() {
      return this.field1968;
   }

   public void setGroundObject(net.runelite.api.GroundObject var1) {
      this.field1965 = (GroundObject)var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ax")
   boolean method4123() {
      return this.field1953 != null && !this.field1953.isEmpty();
   }
}
