import java.util.Date;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lx")
public class classLX {
   @ObfuscatedName("ax")
   public Object[] field4426;
   @ObfuscatedSignature(descriptor = "Lli;")
   @ObfuscatedName("af")
   classLI field4419;
   @ObfuscatedSignature(descriptor = "Ltt;")
   @ObfuscatedName("ae")
   classTT field4418;
   @ObfuscatedSignature(descriptor = "Llh;")
   @ObfuscatedName("az")
   public classLH field4417 = new classLH();
   @ObfuscatedName("ab")
   public Object[] field4423;
   @ObfuscatedName("ag")
   public Object[] field4424;
   @ObfuscatedName("as")
   public Object[] field4425;
   @ToRemove(unused = "true")
   @ObfuscatedName("dm")
   public static final int field4420 = 36;
   @ObfuscatedName("dc")
   static boolean field4422;
   @ObfuscatedName("ej")
   static int field4421;

   @ObfuscatedSignature(descriptor = "(Luu;B)Luu;")
   @ObfuscatedName("af")
   public static classUU method7419(classUU var0, byte var1) {
      try {
         synchronized (classUU.field6380) {
            if (0 == classUU.field6381 * -1618719293) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               } else {
                  return new classUU(var0);
               }
            } else {
               classUZ.method11857(classUU.field6380[(classUU.field6381 -= 943786731) * -1618719293], var0, (byte)57);
               return classUU.field6380[classUU.field6381 * -1618719293];
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lx.af(" + ')');
      }
   }

   classLX() {
      this.field4419 = new classLI();
      this.field4418 = new classTT();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Date;I)Z")
   @ObfuscatedName("aa")
   static boolean method7420(Date var0, int var1) {
      try {
         Date var2 = classSV.method10740(53480384);
         return var0.after(var2);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lx.aa(" + ')');
      }
   }
}
