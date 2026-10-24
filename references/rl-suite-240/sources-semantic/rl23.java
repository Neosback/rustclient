import net.runelite.api.TileObject;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl23")
class rl23 extends Thread {
   @ObfuscatedSignature(descriptor = "[Lrl23;")
   @ObfuscatedName("cd")
   public static rl23[] field5749;
   @ObfuscatedName("pu")
   public static final Object field5745 = new Object();
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("pg")
   public static final Deque2 field5744 = new Deque2();
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("ws")
   public static final Deque2 field5743 = new Deque2();
   @ObfuscatedName("gv")
   public final int field5741;
   @ObfuscatedSignature(descriptor = "Lfv;")
   @ObfuscatedName("dj")
   public static Projection field5748;
   @ObfuscatedSignature(descriptor = "Lez;")
   @ObfuscatedName("rc")
   public static Scene field5747;
   @ObfuscatedName("at")
   public static int field5746;
   @ObfuscatedName("jw")
   public volatile boolean field5739 = true;
   @ObfuscatedSignature(descriptor = "Lrl21;")
   @ObfuscatedName("st")
   public final rl21 field5740 = new rl21();

   @ObfuscatedSignature(descriptor = "(Lnet/runelite/api/TileObject;Ldy;IIII)V")
   @ObfuscatedName("ap")
   public static void method10105(TileObject var0, DynamicObject var1, int var2, int var3, int var4, int var5) {
      assert field5747 != null;

      rl22 var6;
      synchronized (field5744) {
         var6 = (rl22)field5744.method7978();
      }

      if (var6 == null) {
         var6 = new rl22();
      }

      var6.field5738 = var0;
      var6.field5737 = var1;
      var6.field5734 = var2;
      var6.field5736 = var3;
      var6.field5733 = var4;
      var6.field5735 = var5;
      synchronized (field5743) {
         field5743.method7974(var6);
         field5743.notify();
      }

      synchronized (field5745) {
         field5746++;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lez;)V")
   @ObfuscatedName("ta")
   public static void method10107(Projection var0, Scene var1) {
      field5748 = var0;
      field5747 = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bt")
   public static void method10106() {
      while (true) {
         rl22 var0;
         synchronized (field5743) {
            var0 = (rl22)field5743.method7978();
         }

         if (var0 == null) {
            while (true) {
               synchronized (field5745) {
                  if (field5746 == 0) {
                     return;
                  }

                  try {
                     field5745.wait();
                  } catch (InterruptedException var4) {
                  }
               }
            }
         }

         method10109(-1, rl21.field5726, var0);
         method10110(var0);
      }
   }

   public rl23(int var1, String var2) {
      super(var2);
      this.field5741 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lrl22;)V")
   @ObfuscatedName("nk")
   public static void method10110(rl22 var0) {
      synchronized (field5744) {
         field5744.method7974(var0);
      }

      synchronized (field5745) {
         field5746--;
         if (field5746 == 0) {
            field5745.notify();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILrl21;Lrl22;)V")
   @ObfuscatedName("id")
   public static void method10109(int var0, rl21 var1, rl22 var2) {
      DynamicObject var3 = var2.field5737;
      TileObject var4 = var2.field5738;
      int var5 = var2.field5734;
      int var6 = var2.field5736;
      int var7 = var2.field5733;
      int var8 = var2.field5735;

      Model var9;
      try {
         var9 = var3.method3723(var1);
      } catch (Exception var11) {
         client.field1028.debug("exception drawing dynamic game entity", var11);
         return;
      }

      if (var9 != null) {
         var3.setModelHeight(var9.getModelHeight());
         int var10 = var3.getAnimationHeightOffset();
         client.field1117.drawDynamic(var0, field5748, field5747, var4, var3, var9, var5, var6, var7 - var10, var8);
      }
   }

   @Override
   public void run() {
      client.field1028.trace("{} start", this.getName());

      while (this.field5739) {
         rl22 var1;
         synchronized (field5743) {
            var1 = (rl22)field5743.method7978();
            if (var1 == null) {
               try {
                  field5743.wait();
               } catch (InterruptedException var5) {
               }
               continue;
            }
         }

         method10109(this.field5741, this.field5740, var1);
         method10110(var1);
      }

      client.field1028.trace("{} stop", this.getName());
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lu")
   public static void method10108() {
      field5748 = null;
      field5747 = null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqn;)Lzr;")
   @ObfuscatedName("ws")
   public static ScriptEvent method10101(classQN var0) {
      return (ScriptEvent)var0.field5566.method8204();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)I")
   @ObfuscatedName("ih")
   public static int method10103(ClientPreferences var0) {
      return 1095114165 * var0.field1348;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("mr")
   public static void method10104(int var0) {
      if (field5749 != null) {
         if (field5749.length == var0) {
            return;
         }

         for (rl23 var4 : field5749) {
            var4.field5739 = false;
         }

         synchronized (field5743) {
            field5743.notifyAll();
         }
      }

      field5749 = new rl23[var0];

      for (int var8 = 0; var8 < var0; var8++) {
         field5749[var8] = new rl23(var8, "RenderThread" + var8);
         field5749[var8].start();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;)V")
   @ObfuscatedName("ds")
   public static void method10102(classVV var0) {
      if (var0 == null) {
         var0.method12184();
      }

      var0.field6615 = null;
   }
}
