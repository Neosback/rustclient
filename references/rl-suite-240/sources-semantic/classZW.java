import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import sun.misc.Unsafe;

@ObfuscatedName("zw")
public class classZW extends classZN {
   @ObfuscatedName("ht")
   public static final Unsafe field7277 = method14252();
   @ObfuscatedName("aq")
   public static String field7282;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field7279 = 24624;
   @ObfuscatedName("cq")
   public int field7280;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ae")
   static IndexDataBase field7284;
   @ObfuscatedName("ff")
   public long field7281;
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field7278 = 16384;
   @ObfuscatedName("az")
   ByteBuffer field7283;

   classZW() {
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("ap")
   @Override
   void vmethod664(byte[] var1) {
      this.field7283 = ByteBuffer.allocateDirect(var1.length);
      ((Buffer)this.field7283).position(0);
      this.field7283.put(var1);
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("xr")
   public void method14254(byte[] var1) {
      this.vmethod659(var1, 473372216);
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("aq")
   @Override
   void vmethod665(byte[] var1) {
      this.field7283 = ByteBuffer.allocateDirect(var1.length);
      ((Buffer)this.field7283).position(0);
      this.field7283.put(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)[B")
   @ObfuscatedName("ab")
   @Override
   byte[] vmethod658(int var1) {
      if (field7277 != null) {
         byte[] var3 = new byte[this.field7280];
         field7277.copyMemory(null, this.field7281, var3, Unsafe.ARRAY_BYTE_BASE_OFFSET, this.field7280);
         return var3;
      } else {
         byte[] var2 = new byte[this.field7283.capacity()];
         this.field7283.position(0);
         this.field7283.get(var2);
         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lsun/misc/Unsafe;")
   @ObfuscatedName("ux")
   public static Unsafe method14252() {
      try {
         Field var0 = Unsafe.class.getDeclaredField("theUnsafe");
         if (!var0.getType().equals(Unsafe.class)) {
            return null;
         } else {
            int var1 = var0.getModifiers();
            if (Modifier.isStatic(var1) && Modifier.isFinal(var1)) {
               var0.setAccessible(true);
               return (Unsafe)var0.get(null);
            } else {
               return null;
            }
         }
      } catch (Exception var2) {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("aj")
   @Override
   byte[] vmethod662() {
      byte[] var1 = new byte[this.field7283.capacity()];
      ((Buffer)this.field7283).position(0);
      this.field7283.get(var1);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("pd")
   public void method14253() {
      if (field7277 != null) {
         field7277.freeMemory(this.field7281);
         this.field7281 = 0L;
         this.field7280 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ay")
   @Override
   byte[] vmethod660() {
      byte[] var1 = new byte[this.field7283.capacity()];
      ((Buffer)this.field7283).position(0);
      this.field7283.get(var1);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("ad")
   @Override
   void vmethod663(byte[] var1) {
      this.field7283 = ByteBuffer.allocateDirect(var1.length);
      ((Buffer)this.field7283).position(0);
      this.field7283.put(var1);
   }

   @ObfuscatedSignature(descriptor = "([BI)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod659(byte[] var1, int var2) {
      if (field7277 != null) {
         assert this.field7281 == 0L;

         long var3 = field7277.allocateMemory(var1.length);
         field7277.copyMemory(var1, Unsafe.ARRAY_BYTE_BASE_OFFSET, null, var3, var1.length);
         this.field7281 = var3;
         this.field7280 = var1.length;
      } else {
         this.field7283 = ByteBuffer.allocateDirect(var1.length);
         this.field7283.position(0);
         this.field7283.put(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("au")
   @Override
   void vmethod666(byte[] var1) {
      this.field7283 = ByteBuffer.allocateDirect(var1.length);
      ((Buffer)this.field7283).position(0);
      this.field7283.put(var1);
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("al")
   @Override
   byte[] vmethod661() {
      byte[] var1 = new byte[this.field7283.capacity()];
      ((Buffer)this.field7283).position(0);
      this.field7283.get(var1);
      return var1;
   }

   static {
      if (field7277 == null) {
         client.field1028.info("unable to find Unsafe, using direct buffer");
      }
   }
}
