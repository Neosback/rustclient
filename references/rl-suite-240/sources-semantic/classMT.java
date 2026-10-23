import java.io.File;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mt")
public class classMT {
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field4581 = 29;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field4580 = 1;
   @ObfuscatedName("ae")
   static Hashtable field4579 = new Hashtable(16);
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field4582 = 3;
   @ObfuscatedName("af")
   static File field4584;
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   static final int field4583 = 1076101600;
   @ObfuscatedName("az")
   static boolean field4578 = false;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   public static void method7730() {
      field4579.clear();
   }

   @ObfuscatedSignature(descriptor = "(B)Lqo;")
   @ObfuscatedName("af")
   static classQO method7735(byte var0) {
      try {
         return new classQO();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mt.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/io/File;")
   @ObfuscatedName("ab")
   static File method7726(String var0) {
      if (!field4578) {
         throw new RuntimeException("");
      } else {
         File var1 = (File)field4579.get(var0);
         if (null != var1) {
            return var1;
         } else {
            File var2 = new File(field4584, var0);
            RandomAccessFile var3 = null;

            try {
               File var4 = new File(var2.getParent());
               if (!var4.exists()) {
                  throw new RuntimeException("");
               } else {
                  var3 = new RandomAccessFile(var2, "rw");
                  int var5 = var3.read();
                  var3.seek(0L);
                  var3.write(var5);
                  var3.seek(0L);
                  var3.close();
                  field4579.put(var0, var2);
                  return var2;
               }
            } catch (Exception var7) {
               try {
                  if (null != var3) {
                     var3.close();
                     Object var8 = null;
                  }
               } catch (Exception var6) {
               }

               throw new RuntimeException();
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/io/File;")
   @ObfuscatedName("ae")
   static File method7727(String var0) {
      if (!field4578) {
         throw new RuntimeException("");
      } else {
         File var1 = (File)field4579.get(var0);
         if (null != var1) {
            return var1;
         } else {
            File var2 = new File(field4584, var0);
            RandomAccessFile var3 = null;

            try {
               File var4 = new File(var2.getParent());
               if (!var4.exists()) {
                  throw new RuntimeException("");
               } else {
                  var3 = new RandomAccessFile(var2, "rw");
                  int var5 = var3.read();
                  var3.seek(0L);
                  var3.write(var5);
                  var3.seek(0L);
                  var3.close();
                  field4579.put(var0, var2);
                  return var2;
               }
            } catch (Exception var7) {
               try {
                  if (null != var3) {
                     var3.close();
                     Object var8 = null;
                  }
               } catch (Exception var6) {
               }

               throw new RuntimeException();
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   public static void method7731() {
      field4579.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public static void method7732() {
      field4579.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public static void method7733() {
      field4579.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxk;Lxs;)V")
   @ObfuscatedName("bk")
   public static void method7734(classXK var0, PacketBuffer var1) {
      if (var0 == null) {
         var0.method12879();
      }

      var0.method12895(var1, 4, 13, 13, 519399154);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/io/File;")
   @ObfuscatedName("ag")
   static File method7728(String var0) {
      if (!field4578) {
         throw new RuntimeException("");
      } else {
         File var1 = (File)field4579.get(var0);
         if (null != var1) {
            return var1;
         } else {
            File var2 = new File(field4584, var0);
            RandomAccessFile var3 = null;

            try {
               File var4 = new File(var2.getParent());
               if (!var4.exists()) {
                  throw new RuntimeException("");
               } else {
                  var3 = new RandomAccessFile(var2, "rw");
                  int var5 = var3.read();
                  var3.seek(0L);
                  var3.write(var5);
                  var3.seek(0L);
                  var3.close();
                  field4579.put(var0, var2);
                  return var2;
               }
            } catch (Exception var7) {
               try {
                  if (null != var3) {
                     var3.close();
                     Object var8 = null;
                  }
               } catch (Exception var6) {
               }

               throw new RuntimeException();
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbr;IIFI)F")
   @ObfuscatedName("ku")
   public static float method7729(classBR var0, int var1, int var2, float var3, int var4) {
      float var5 = var0.field534[var1][0][var2] + var3 * (var0.field534[var1][1][var2] - var0.field534[var1][0][var2]);
      var5 *= 1.2207031E-4F;
      return classBR.method1189(var5, var4);
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)V")
   @ObfuscatedName("ai")
   static final void method7736(Widget var0, int var1, int var2) {
      try {
         if (null == var0.field4310) {
            if (var2 <= 2040728650) {
               throw new IllegalStateException();
            } else {
               throw new RuntimeException();
            }
         } else {
            if (var0.field4402 == null) {
               if (var2 <= 2040728650) {
                  return;
               }

               var0.field4402 = new int[var0.field4310.length];
            }

            var0.field4402[var1] = Integer.MAX_VALUE;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mt.ai(" + ')');
      }
   }

   classMT() throws Throwable {
      throw new Error();
   }
}
