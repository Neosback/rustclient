import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketImpl;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("uh")
public class classUH extends classUK {
   @ObfuscatedName("az")
   Socket field6307;
   @ObfuscatedSignature(descriptor = "Lub;")
   @ObfuscatedName("af")
   classUB field6308;
   @ObfuscatedSignature(descriptor = "Lue;")
   @ObfuscatedName("ae")
   classUE field6309;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   public static final int field6310 = 19;

   @ObfuscatedSignature(descriptor = "()Ljava/io/FileDescriptor;")
   @ObfuscatedName("we")
   public FileDescriptor method11385() {
      if (this.field6308.field6277 instanceof FileInputStream) {
         FileInputStream var6 = (FileInputStream)this.field6308.field6277;

         try {
            return var6.getFD();
         } catch (IOException var4) {
            return null;
         }
      } else {
         try {
            Field var1 = Socket.class.getDeclaredField("impl");
            var1.setAccessible(true);
            SocketImpl var2 = (SocketImpl)var1.get(this.field6307);
            Method var3 = SocketImpl.class.getDeclaredMethod("getFileDescriptor");
            var3.setAccessible(true);
            return (FileDescriptor)var3.invoke(var2);
         } catch (InaccessibleObjectException | ReflectiveOperationException var5) {
            return null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   @Override
   public int vmethod586(int var1) throws IOException {
      try {
         return this.field6308.method11307((short)-27360);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uh.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod582(int var1, int var2) throws IOException {
      try {
         return this.field6308.method11304(var1, -437638879);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uh.az(" + ')');
      }
   }

   public classUH(Socket var1, int var2, int var3) throws IOException {
      this.field6307 = var1;
      this.field6307.setSoTimeout(30000);
      this.field6307.setTcpNoDelay(true);
      this.field6307.setReceiveBufferSize(65536);
      this.field6307.setSendBufferSize(65536);
      this.field6308 = new classUB(this.field6307.getInputStream(), var2);
      this.field6309 = new classUE(this.field6307.getOutputStream(), var3);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("abc")
   protected void method11382() {
      this.vmethod604(1271658352);
   }

   @ObfuscatedSignature(descriptor = "([BIII)I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod594(byte[] var1, int var2, int var3, int var4) throws IOException {
      try {
         return this.field6308.method11315(var1, var2, var3, (byte)2);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uh.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([BIII)V")
   @ObfuscatedName("ag")
   @Override
   public void vmethod597(byte[] var1, int var2, int var3, int var4) throws IOException {
      try {
         this.field6309.method11349(var1, var2, var3, 1079426491);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uh.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ax")
   @Override
   public boolean vmethod583(int var1) throws IOException {
      return this.field6308.method11304(var1, -205385795);
   }

   @Override
   protected void finalize() {
      try {
         this.vmethod604(1271658352);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "uh.finalize(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ac")
   @Override
   public boolean vmethod584(int var1) throws IOException {
      return this.field6308.method11304(var1, -1282584019);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("abt")
   protected void method11383() {
      this.vmethod604(1271658352);
   }

   @ObfuscatedSignature(descriptor = "([BII)I")
   @ObfuscatedName("au")
   @Override
   public int vmethod595(byte[] var1, int var2, int var3) throws IOException {
      return this.field6308.method11315(var1, var2, var3, (byte)42);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   @Override
   public void vmethod600() {
      this.field6309.method11354(1722769006);

      try {
         this.field6307.close();
      } catch (IOException var2) {
      }

      this.field6308.method11319((byte)4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luh;)V")
   @ObfuscatedName("kz")
   public static void method11384(classUH var0) {
      if (var0 == null) {
         var0.finalize();
      }

      var0.vmethod604(1271658352);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   @Override
   public void vmethod601() {
      this.field6309.method11354(-2063166249);

      try {
         this.field6307.close();
      } catch (IOException var2) {
      }

      this.field6308.method11319((byte)4);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   @Override
   public int vmethod587() throws IOException {
      return this.field6308.method11307((short)-5554);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aj")
   @Override
   public int vmethod588() throws IOException {
      return this.field6308.method11307((short)-26836);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aa")
   @Override
   public boolean vmethod585(int var1) throws IOException {
      return this.field6308.method11304(var1, -2142054528);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   @Override
   public int vmethod589() throws IOException {
      return this.field6308.method11310((short)252);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   @Override
   public int vmethod590() throws IOException {
      return this.field6308.method11310((short)252);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   @Override
   public int vmethod591() throws IOException {
      return this.field6308.method11310((short)252);
   }

   @ObfuscatedSignature(descriptor = "([BII)I")
   @ObfuscatedName("ap")
   @Override
   public int vmethod596(byte[] var1, int var2, int var3) throws IOException {
      return this.field6308.method11315(var1, var2, var3, (byte)33);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   @Override
   public void vmethod602() {
      this.field6309.method11354(-1651027236);

      try {
         this.field6307.close();
      } catch (IOException var2) {
      }

      this.field6308.method11319((byte)4);
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("ai")
   @Override
   public void vmethod598(byte[] var1, int var2, int var3) throws IOException {
      this.field6309.method11349(var1, var2, var3, 1079426491);
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("ar")
   @Override
   public void vmethod599(byte[] var1, int var2, int var3) throws IOException {
      this.field6309.method11349(var1, var2, var3, 1079426491);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aw")
   @Override
   public void vmethod603() {
      this.field6309.method11354(-426428696);

      try {
         this.field6307.close();
      } catch (IOException var2) {
      }

      this.field6308.method11319((byte)4);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   @Override
   public void vmethod604(int var1) {
      try {
         this.field6309.method11354(31174107);

         try {
            this.field6307.close();
         } catch (IOException var3) {
         }

         this.field6308.method11319((byte)4);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uh.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   @Override
   public int vmethod592() throws IOException {
      return this.field6308.method11310((short)252);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("at")
   @Override
   public void vmethod605() {
      this.field6309.method11354(-88719412);

      try {
         this.field6307.close();
      } catch (IOException var2) {
      }

      this.field6308.method11319((byte)4);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod593(int var1) throws IOException {
      try {
         return this.field6308.method11310((short)252);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uh.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ae")
   public static boolean method11381(int var0, byte var1) {
      try {
         boolean var10000;
         if ((var0 >> 20 & 1) != 0) {
            if (var1 >= 0) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uh.ae(" + ')');
      }
   }
}
