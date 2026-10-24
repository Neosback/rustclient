import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xr")
public class classXR extends classUY implements classUT {
   @ObfuscatedSignature(descriptor = "Lid;")
   @ObfuscatedName("af")
   final classID field6922 = new classID(64);
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ae")
   final IndexDataBase field6921;
   @ObfuscatedName("ab")
   final int field6920;
   @ObfuscatedName("dc")
   static int field6923;

   @ObfuscatedSignature(descriptor = "(II)Lur;")
   @ObfuscatedName("az")
   @Override
   protected classUR vmethod606(int var1, int var2) {
      try {
         synchronized (this.field6922) {
            classUF var3 = (classUF)classID.method6185(this.field6922, var1);
            if (null == var3) {
               if (var2 == -600712840) {
                  throw new IllegalStateException();
               }

               var3 = method12936(this, var1, -684085159);
               this.field6922.method6194(var3, var1);
            }

            return var3;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "xr.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxr;)V")
   @ObfuscatedName("tm")
   public static void method12943(classXR var0) {
      synchronized (var0.field6922) {
         var0.field6922.method6208();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lur;")
   @ObfuscatedName("as")
   @Override
   protected classUR vmethod607(int var1) {
      synchronized (this.field6922) {
         classUF var2 = (classUF)classID.method6185(this.field6922, var1);
         if (null == var2) {
            var2 = method12936(this, var1, 504848329);
            this.field6922.method6194(var2, var1);
         }

         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lur;")
   @ObfuscatedName("ax")
   @Override
   protected classUR vmethod608(int var1) {
      synchronized (this.field6922) {
         classUF var2 = (classUF)classID.method6185(this.field6922, var1);
         if (null == var2) {
            var2 = method12936(this, var1, 294972746);
            this.field6922.method6194(var2, var1);
         }

         return var2;
      }
   }

   @Override
   public Iterator iterator() {
      try {
         return new classXI(this);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xr.iterator(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("by")
   public Iterator method12950() {
      return new classXI(this);
   }

   @ObfuscatedSignature(descriptor = "(I)Lur;")
   @ObfuscatedName("ag")
   @Override
   protected classUR vmethod609(int var1) {
      synchronized (this.field6922) {
         classUF var2 = (classUF)classID.method6185(this.field6922, var1);
         if (null == var2) {
            var2 = method12936(this, var1, -438976494);
            this.field6922.method6194(var2, var1);
         }

         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("be")
   public Iterator method12951() {
      return new classXI(this);
   }

   public classXR(classKX var1, int var2, classXO var3, IndexDataBase var4) {
      super(var1, var3, var4 != null ? var4.method11943(var2, -785956127) : 0);
      this.field6921 = var4;
      this.field6920 = 472608345 * var2;
   }

   @ObfuscatedSignature(descriptor = "(Lxr;B)V")
   @ObfuscatedName("tv")
   public static void method12944(classXR var0, byte var1) {
      if (var0 == null) {
         var0.method12949(var1);
      }

      try {
         synchronized (var0.field6922) {
            var0.field6922.method6208();
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xr.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxr;II)Luf;")
   @ObfuscatedName("qh")
   public static classUF method12936(classXR var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method12942(var1, var1);
      } else {
         try {
            byte[] var3 = var0.field6921.method11942(1925511657 * var0.field6920, var1, -1784809144);
            classUF var4 = new classUF(var1);
            if (null != var3) {
               var4.method11572(new classXY(var3), -1830946202);
            }

            return var4;
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "xr.aq(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Luf;")
   @ObfuscatedName("ap")
   classUF method12937(int var1) {
      byte[] var2 = this.field6921.method11942(1925511657 * this.field6920, var1, -1887504987);
      classUF var3 = new classUF(var1);
      if (null != var2) {
         var3.method11572(new classXY(var2), -1830946202);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(I)Luf;")
   @ObfuscatedName("au")
   classUF method12938(int var1) {
      byte[] var2 = this.field6921.method11942(-324184084 * this.field6920, var1, -1908654534);
      classUF var3 = new classUF(var1);
      if (null != var2) {
         var3.method11572(new classXY(var2), -1830946202);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   public void method12945() {
      synchronized (this.field6922) {
         this.field6922.method6208();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)V")
   @ObfuscatedName("zc")
   public static void method12952(classVV var0, Widget var1) {
      if (var0 == null) {
         var0.method12193(var1);
      }

      var0.field6615 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;)Llx;")
   @ObfuscatedName("ok")
   public static classLX method12953(Widget var0) {
      return var0.field4367;
   }

   @ObfuscatedSignature(descriptor = "(I)Luf;")
   @ObfuscatedName("ar")
   classUF method12939(int var1) {
      byte[] var2 = this.field6921.method11942(1925511657 * this.field6920, var1, -1404446809);
      classUF var3 = new classUF(var1);
      if (null != var2) {
         var3.method11572(new classXY(var2), -1830946202);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(I)Luf;")
   @ObfuscatedName("aw")
   classUF method12940(int var1) {
      byte[] var2 = this.field6921.method11942(1925511657 * this.field6920, var1, -2084427893);
      classUF var3 = new classUF(var1);
      if (null != var2) {
         var3.method11572(new classXY(var2), -1830946202);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(I)Luf;")
   @ObfuscatedName("ai")
   classUF method12941(int var1) {
      byte[] var2 = this.field6921.method11942(1925511657 * this.field6920, var1, -1046223987);
      classUF var3 = new classUF(var1);
      if (null != var2) {
         var3.method11572(new classXY(var2), -1830946202);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   public void method12946() {
      synchronized (this.field6922) {
         this.field6922.method6208();
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   public void method12947() {
      synchronized (this.field6922) {
         this.field6922.method6208();
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   public void method12948() {
      synchronized (this.field6922) {
         this.field6922.method6208();
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Luf;")
   @ObfuscatedName("aq")
   classUF method12942(int var1, int var2) {
      try {
         byte[] var3 = this.field6921.method11937(1925511657 * this.field6920, var1, -1784809144);
         classUF var4 = new classUF(var1);
         if (null != var3) {
            var4.method11572(new classXY(var3), -1830946202);
         }

         return var4;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xr.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ad")
   public void method12949(byte var1) {
      try {
         synchronized (this.field6922) {
            this.field6922.method6209();
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xr.ad(" + ')');
      }
   }
}
