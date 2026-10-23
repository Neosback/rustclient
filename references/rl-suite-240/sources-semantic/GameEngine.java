import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.concurrent.Semaphore;
import net.runelite.api.ClientConfiguration;
import net.runelite.api.Constants;
import net.runelite.api.events.FocusChanged;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tq")
public abstract class GameEngine extends Panel implements Runnable, FocusListener, WindowListener, rl0, net.runelite.api.GameEngine {
   @ObfuscatedName("rb")
   static long[] field6193 = new long[32];
   @ObfuscatedName("qb")
   public static int field6191 = 0;
   @ObfuscatedName("xl")
   public final ComponentListener field6199 = new rl12(this);
   @ObfuscatedSignature(descriptor = "Ltq;")
   @ObfuscatedName("qw")
   static GameEngine field6187 = null;
   @ObfuscatedName("ss")
   volatile long field6208;
   @ObfuscatedName("rl")
   static long field6189 = 0L;
   @ObfuscatedName("rv")
   int field6213;
   @ObfuscatedName("qg")
   protected static int field6185 = 1308258444;
   @ObfuscatedName("rf")
   static int field6194 = -499435836;
   @ObfuscatedName("mu")
   public Dimension field6218;
   @ObfuscatedName("rh")
   int field6211;
   @ObfuscatedName("rg")
   static volatile boolean field6188 = true;
   @ObfuscatedName("ra")
   static int field6192 = 585748989;
   @ObfuscatedSignature(descriptor = "Lti;")
   @ObfuscatedName("rn")
   static classTI field6196 = new classTI();
   @ObfuscatedName("xu")
   public boolean field6200 = false;
   @ObfuscatedName("rd")
   static long field6197 = -6728113685686042049L;
   @ObfuscatedName("rc")
   static long field6186 = 1622412589189406289L;
   @ObfuscatedName("rz")
   public Thread field6221;
   @ObfuscatedName("rw")
   final EventQueue field6209;
   @ObfuscatedName("rm")
   static long[] field6195 = new long[32];
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field6210 = 71;
   @ObfuscatedName("rr")
   public boolean field6201 = true;
   @ObfuscatedName("rt")
   boolean field6202 = false;
   @ObfuscatedName("ow")
   public ClientConfiguration field6223;
   @ObfuscatedName("rs")
   int field6203 = 0;
   @ObfuscatedName("rx")
   static boolean field6190 = false;
   @ObfuscatedName("rp")
   int field6212;
   @ObfuscatedName("so")
   int field6216;
   @ObfuscatedName("sb")
   int field6214;
   @ObfuscatedName("sm")
   int field6215;
   @ObfuscatedName("sz")
   Frame field6219;
   @ObfuscatedName("sp")
   Canvas field6217;
   @ObfuscatedName("sr")
   volatile boolean field6205;
   @ObfuscatedName("eg")
   public Semaphore field6222;
   @ObfuscatedName("sc")
   boolean field6206;
   @ObfuscatedName("sx")
   volatile boolean field6207;
   @ObfuscatedName("rk")
   static int field6198 = 0;
   @ObfuscatedSignature(descriptor = "Ltc;")
   @ObfuscatedName("si")
   classTC field6224;
   @ObfuscatedName("sq")
   Clipboard field6220;
   @ObfuscatedName("ri")
   int field6204 = 0;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;B)V")
   @ObfuscatedName("afd")
   final void method11153(Object var1, byte var2) {
      if (!classOE.field4841.isGpu() && this.field6209 != null) {
         for (int var3 = 0; var3 < 50 && this.field6209.peekEvent() != null; var3++) {
            try {
               Thread.sleep(1L);
            } catch (InterruptedException var5) {
            }
         }

         if (var1 != null) {
            this.field6209.postEvent(new ActionEvent(var1, 1001, "dummy"));
         }
      }

      if (client.field1117 != null) {
         client.field1117.draw(classGP.field2648);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lmf;")
   @ObfuscatedName("afo")
   protected classMF method11011(byte var1) {
      try {
         if (this.field6224 == null) {
            this.field6224 = new classTC();
            this.field6224.method10781(this.field6217, 1087436969);
         }

         return this.field6224;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.afo(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aji")
   final void method11046() {
      Container var1 = this.method11131(-381017106);
      if (null != var1) {
         classUQ var2 = this.method11137(161498288);
         this.field6212 = Math.max(518523273 * var2.field6368, 237759111 * this.field6213) * -994011262;
         this.field6211 = Math.max(var2.field6369 * -874893249, -450704939 * this.field6216) * -181673913;
         if (39467035 * this.field6212 <= 0) {
            this.field6212 = 833806867;
         }

         if (1525008759 * this.field6211 <= 0) {
            this.field6211 = -181673913;
         }

         classSA.field5895 = Math.min(1100793051 * this.field6212, this.field6214 * 1937290719) * -2089155421;
         IntProjection.field1936 = Math.min(1119626313 * this.field6211, -777828750 * this.field6215) * 841518155;
         this.field6203 = 1962298787 * ((39467035 * this.field6212 - classSA.field5895 * -579192442) / 2);
         this.field6204 = 0;
         this.field6217.setSize(89789507 * classSA.field5895, 2143590714 * IntProjection.field1936);
         classWO.field6761 = new MainBufferProvider(classSA.field5895 * 773246731, IntProjection.field1936 * -1607235138, this.field6217, this.field6201);
         if (var1 == this.field6219) {
            Insets var3 = this.field6219.getInsets();
            this.field6217.setLocation(1023492508 * this.field6203 + var3.left, -673776453 * this.field6204 + var3.top);
         } else {
            this.field6217.setLocation(this.field6203 * 1451619303, this.field6204 * -1827559305);
         }

         this.field6205 = true;
         this.vmethod250((byte)100);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("afl")
   public final void method11102(FocusEvent var1) {
      field6188 = true;
      this.field6205 = true;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("afi")
   protected final void method11032(int var1) {
      try {
         field6196.method10875((byte)30);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.afi(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gz")
   public void method11159() {
      this.vmethod228(1624818309);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("ll")
   public static void method11071(GameEngine var0) {
      if (var0 == null) {
         var0.method11043();
      }

      try {
         if (null != classDQ.field1602) {
            String var1 = classDQ.field1602.toLowerCase();
            if (var1.indexOf("sun") != -1 || var1.indexOf("apple") != -1) {
               String var2 = classMB.field4477;
               if (var2.equals("1.1")
                  || var2.startsWith("1.1.")
                  || var2.equals("1.2")
                  || var2.startsWith("1.2.")
                  || var2.equals("1.3")
                  || var2.startsWith("1.3.")
                  || var2.equals("1.4")
                  || var2.startsWith("1.4.")
                  || var2.equals("1.5")
                  || var2.startsWith("1.5.")
                  || var2.equals("1.6.0")) {
                  method11129(var0, "wrongjava", 1717214884);
                  return;
               }

               if (var2.startsWith("1.6.0_")) {
                  int var3 = 6;

                  while (var3 < var2.length() && classEB.method3818(var2.charAt(var3), (byte)-98)) {
                     var3++;
                  }

                  String var4 = var2.substring(6, var3);
                  if (IntProjection.method4108(var4, (byte)-1) && classJF.method6402(var4, 215229911) < 10) {
                     method11129(var0, "wrongjava", 1993685585);
                     return;
                  }
               }

               field6192 = -1366222351;
            }
         }

         var0.setFocusCycleRoot(true);
         var0.method11063(2057236764);
         var0.vmethod228(1624818309);
         classTW.field6248 = classEF.method3857((byte)-68);

         while (-4157353362753048141L * field6189 == 0L || classDD.method2988(16711680) < field6189 * -4157353362753048141L) {
            ActorSpotAnim.field5962 = classTW.field6248.vmethod535(field6185 * -124206665, -1581411499 * field6192, -1162884564) * -1242900443;

            for (int var6 = 0; var6 < 1906148269 * ActorSpotAnim.field5962; var6++) {
               var0.method11074(-252904814);
            }

            var0.method11083(1159221952);
            var0.method11153(var0.field6217, (byte)-1);
         }
      } catch (Exception var5) {
         classAAU.method270(null, var5, (byte)38);
         method11129(var0, "crash", 1096285188);
      }

      var0.method11092((byte)-1);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("rk")
   public boolean method11148() {
      return this.field6201;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ajg")
   final void method11086() {
      classUQ var1 = this.method11137(-731027267);
      if (518523273 * var1.field6368 != 39467035 * this.field6212 || -874893249 * var1.field6369 != 1525008759 * this.field6211 || this.field6206) {
         method11047(this, 1503200566);
         this.field6206 = false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("fr")
   public static void method11103(GameEngine var0, FocusEvent var1) {
      if (var0 == null) {
         var0.method11029();
      }

      field6188 = true;
      var0.field6205 = true;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("aga")
   protected final void method11079(byte var1) {
      try {
         this.field6205 = true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.aga(" + ')');
      }
   }

   public Canvas getCanvas() {
      return this.field6217;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("age")
   void method11051(byte var1) {
      try {
         int var2 = this.field6203 * 1451619303;
         int var3 = this.field6204 * -673776453;
         int var4 = this.field6212 * 39467035 - 773246731 * classSA.field5895 - var2;
         int var5 = 1525008759 * this.field6211 - 8379747 * IntProjection.field1936 - var3;
         if (var2 <= 0) {
            if (var1 >= 0) {
               throw new IllegalStateException();
            }

            if (var4 <= 0) {
               if (var1 >= 0) {
                  return;
               }

               if (var3 <= 0) {
                  if (var1 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (var5 <= 0) {
                     return;
                  }
               }
            }
         }

         try {
            Container var6 = this.method11131(-998200741);
            int var7 = 0;
            int var8 = 0;
            if (var6 == this.field6219) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               Insets var9 = this.field6219.getInsets();
               var7 = var9.left;
               var8 = var9.top;
            }

            Graphics var12 = var6.getGraphics();
            var12.setColor(Color.black);
            if (var2 > 0) {
               var12.fillRect(var7, var8, var2, 1525008759 * this.field6211);
            }

            if (var3 > 0) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               var12.fillRect(var7, var8, this.field6212 * 39467035, var3);
            }

            if (var4 > 0) {
               var12.fillRect(this.field6212 * 39467035 + var7 - var4, var8, var4, this.field6211 * 1525008759);
            }

            if (var5 > 0) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               var12.fillRect(var7, var8 + 1525008759 * this.field6211 - var5, this.field6212 * 39467035, var5);
            }
         } catch (Exception var10) {
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "tq.age(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("agb")
   final void method11158(byte var1) {
      this.method11168(false);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("jh")
   public static void method11125(GameEngine var0) {
      if (var0 == null) {
         var0.method11061();
      }

      classLY.field4441 = null;
      classAB.field123 = null;
      classAF.field145 = null;
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("agf")
   protected final void method11055(int var1, int var2, int var3, int var4, int var5) {
      try {
         try {
            if (null != field6187) {
               if (var5 >= 1651537548) {
                  throw new IllegalStateException();
               }

               field6198 += 354831441;
               if (221150385 * field6198 >= 3) {
                  if (var5 >= 1651537548) {
                     throw new IllegalStateException();
                  }

                  method11129(this, "alreadyloaded", 1813484727);
                  return;
               }

               this.method11149().vmethod560(this.method11157(), "_self");
               return;
            }

            field6187 = this;
            classSA.field5895 = var1 * -2089155421;
            client.method2112(-1);
            IntProjection.field1936 = 841518155 * var2;
            client.method2225(-1);
            classAAL.field57 = var3 * -154973313;
            classAAF.field46 = -609160337 * var4;
            classAAF.field49 = this.method11147();
            if (classWW.field6784 == null) {
               if (var5 >= 1651537548) {
                  throw new IllegalStateException();
               }

               classWW.field6784 = new classMB();
            }

            classMB.method7559(classWW.field6784, this, 1, 16502140);
         } catch (Exception var7) {
            classAAU.method270(null, var7, (byte)83);
            method11129(this, "crash", 1494929511);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "tq.agf(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("adl")
   protected abstract void vmethod251();

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("agg")
   protected void method11152(boolean var1, int var2) {
      if (this.field6201 != var1) {
         this.field6201 = var1;
         var1 &= !classOE.field4841.isGpu();
         classWO.field6761.method13369(var1);
         classWO.field6761.method13368();
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("akg")
   public final void method11114(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ltq;B)Ljava/awt/datatransfer/Clipboard;")
   @ObfuscatedName("rt")
   public static Clipboard method11022(GameEngine var0, byte var1) {
      if (var0 == null) {
         var0.method11025(var1);
      }

      try {
         return var0.field6220;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.afv(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aco")
   protected abstract void vmethod243();

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("agu")
   void method11074(int var1) {
      try {
         long var2 = classDD.method2988(16711680);
         long var4 = field6193[75872381 * classMD.field4485];
         field6193[75872381 * classMD.field4485] = var2;
         classMD.field4485 = (1 + 75872381 * classMD.field4485 & 31) * -911507243;
         if (var4 != 0L) {
            if (var1 == -1485028444) {
               throw new IllegalStateException();
            }

            if (var2 > var4) {
            }
         }

         synchronized (this) {
            classDD.field1403 = field6188;
         }

         this.vmethod231((byte)91);
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "tq.agu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajj")
   public final void method11106(WindowEvent var1) {
   }

   public abstract void init();

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("agz")
   final void method11087(int var1) {
      try {
         classUQ var2 = this.method11137(790055939);
         if (518523273 * var2.field6368 == 39467035 * this.field6212) {
            if (var1 <= 387260065) {
               return;
            }

            if (-874893249 * var2.field6369 == 1525008759 * this.field6211) {
               if (var1 <= 387260065) {
                  throw new IllegalStateException();
               }

               if (!this.field6206) {
                  return;
               }

               if (var1 <= 387260065) {
                  throw new IllegalStateException();
               }
            }
         }

         method11047(this, 1684778192);
         this.field6206 = false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tq.agz(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)Z")
   @ObfuscatedName("ea")
   public static boolean method11065(GameEngine var0) {
      if (var0 == null) {
         var0.run();
      }

      String var1 = var0.method11157().getHost().toLowerCase();
      if (var1.equals("jagex.com") || var1.endsWith(".jagex.com")) {
         return true;
      } else if (var1.equals("runescape.com") || var1.endsWith(".runescape.com")) {
         return true;
      } else if (var1.endsWith("127.0.0.1")) {
         return true;
      } else {
         while (!var1.isEmpty() && var1.charAt(var1.length() - 1) >= 1955637880 && var1.charAt(var1.length() - 1) <= 1363998760) {
            var1 = var1.substring(0, var1.length() - 1);
         }

         if (var1.endsWith("192.168.1.")) {
            return true;
         } else {
            method11129(var0, "invalidhost", -843943309);
            return false;
         }
      }
   }

   public void setConfiguration(ClientConfiguration var1) {
      if (this.field6223 != null) {
         throw new IllegalStateException();
      } else {
         this.field6223 = var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ags")
   final void method11088(int var1) {
      try {
         this.field6206 = true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.ags(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("agr")
   final synchronized void method11092(byte var1) {
      try {
         if (field6190) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            }
         } else {
            field6190 = true;

            try {
               this.field6217.removeFocusListener(this);
            } catch (Exception var6) {
            }

            try {
               this.vmethod240(1781169542);
            } catch (Exception var5) {
            }

            if (this.field6219 != null) {
               if (var1 >= 1) {
                  throw new IllegalStateException();
               }

               try {
                  System.exit(0);
               } catch (Throwable var4) {
               }
            }

            if (classWW.field6784 != null) {
               if (var1 >= 1) {
                  throw new IllegalStateException();
               }

               try {
                  classWW.field6784.method7543((short)20932);
               } catch (Exception var3) {
               }
            }

            this.vmethod207(294971065);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "tq.agr(" + ')');
      }
   }

   public final void start() {
      this.method11155();

      try {
         if (this == field6187 && !field6190) {
            field6189 = 0L;
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "tq.start(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Luq;")
   @ObfuscatedName("aki")
   classUQ method11134() {
      Container var1 = this.method11131(-1698579921);
      int var2 = Math.max(var1.getWidth(), 237759111 * this.field6213);
      int var3 = Math.max(var1.getHeight(), this.field6216 * -450704939);
      if (this.field6219 != null) {
         Insets var4 = this.field6219.getInsets();
         var2 -= var4.right + var4.left;
         var3 -= var4.top + var4.bottom;
      }

      return new classUQ(var2, var3);
   }

   public final void destroy() {
      try {
         if (field6187 == this && !field6190) {
            field6189 = classDD.method2988(16711680) * -7013317878618397317L;
            Friend.method10197(5000L);
            this.method11092((byte)-11);
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "tq.destroy(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Luq;")
   @ObfuscatedName("akz")
   classUQ method11135() {
      Container var1 = this.method11131(-1181729155);
      int var2 = Math.max(var1.getWidth(), 237759111 * this.field6213);
      int var3 = Math.max(var1.getHeight(), this.field6216 * -450704939);
      if (this.field6219 != null) {
         Insets var4 = this.field6219.getInsets();
         var2 -= var4.right + var4.left;
         var3 -= var4.top + var4.bottom;
      }

      return new classUQ(var2, var3);
   }

   @Override
   public final synchronized void paint(Graphics var1) {
      this.method11145(var1);

      try {
         if (field6187 == this && !field6190) {
            this.field6205 = true;
            if (classDD.method2988(16711680) - this.field6208 * -5853619249873121909L > 1000L) {
               Rectangle var2 = var1.getClipBounds();
               if (null == var2 || var2.width >= 773246731 * classSA.field5895 && var2.height >= 8379747 * IntProjection.field1936) {
                  this.field6207 = true;
               }
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tq.paint(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("agv")
   protected final void method11126(int var1) {
      try {
         classLY.field4441 = null;
         classAB.field123 = null;
         classAF.field145 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.agv(" + ')');
      }
   }

   @Override
   public final void focusLost(FocusEvent var1) {
      try {
         field6188 = false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.focusLost(" + ')');
      }
   }

   @Override
   public final void windowActivated(WindowEvent var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.windowActivated(" + ')');
      }
   }

   public boolean isClientThread() {
      return this.field6221 == Thread.currentThread();
   }

   @Override
   public final void windowClosed(WindowEvent var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.windowClosed(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("hs")
   protected abstract void vmethod237(boolean var1, int var2);

   @Override
   public final void windowDeactivated(WindowEvent var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.windowDeactivated(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/Throwable;)V")
   @ObfuscatedName("oc")
   public static void method11160(String var0, Throwable var1) {
      classAAU.method270(var0, var1, (byte)102);
   }

   @Override
   public final void windowDeiconified(WindowEvent var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.windowDeiconified(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajl")
   public final void method11109(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ltq;Ljava/lang/String;I)V")
   @ObfuscatedName("kx")
   public static void method11015(GameEngine var0, String var1, int var2) {
      if (var0 == null) {
         var0.method11021(var1, var2);
      }

      try {
         var0.field6220.setContents(new StringSelection(var1), null);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tq.afw(" + ')');
      }
   }

   @Override
   public final void windowOpened(WindowEvent var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.windowOpened(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ltq;I)V")
   @ObfuscatedName("yw")
   public static void method11047(GameEngine var0, int var1) {
      if (var0 == null) {
         var0.method11173(var1);
      }

      var0.method11142();

      try {
         Container var2 = var0.method11131(-1484383774);
         if (null == var2) {
            var0.method11146();
         } else {
            classUQ var3 = var0.method11137(918325363);
            var0.field6212 = Math.max(518523273 * var3.field6368, 237759111 * var0.field6213) * 833806867;
            var0.field6211 = Math.max(var3.field6369 * -874893249, -450704939 * var0.field6216) * -181673913;
            if (39467035 * var0.field6212 <= 0) {
               if (var1 <= 1315923617) {
                  throw new IllegalStateException();
               }

               var0.field6212 = 833806867;
            }

            if (1525008759 * var0.field6211 <= 0) {
               if (var1 <= 1315923617) {
                  throw new IllegalStateException();
               }

               var0.field6211 = -181673913;
            }

            classSA.field5895 = Math.min(39467035 * var0.field6212, var0.field6214 * 1937290719) * -2089155421;
            client.method2112(-1);
            IntProjection.field1936 = Math.min(1525008759 * var0.field6211, 697093715 * var0.field6215) * 841518155;
            client.method2225(-1);
            var0.field6203 = 828993495 * ((39467035 * var0.field6212 - classSA.field5895 * 773246731) / 2);
            var0.field6204 = 0;
            var0.field6217.setSize(773246731 * classSA.field5895, 8379747 * IntProjection.field1936);
            classWO.field6761 = new MainBufferProvider(classSA.field5895 * 773246731, IntProjection.field1936 * 8379747, var0.field6217, var0.field6201);
            if (var2 == var0.field6219) {
               if (var1 <= 1315923617) {
                  throw new IllegalStateException();
               }

               Insets var4 = var0.field6219.getInsets();
               var0.field6217.setLocation(1451619303 * var0.field6203 + var4.left, -673776453 * var0.field6204 + var4.top);
            } else {
               var0.field6217.setLocation(var0.field6203 * 1451619303, var0.field6204 * -673776453);
            }

            var0.field6205 = true;
            var0.vmethod250((byte)17);
            var0.method11146();
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tq.agm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aia")
   void method11075() {
      long var1 = classDD.method2988(16711680);
      long var3 = field6193[75872381 * classMD.field4485];
      field6193[75872381 * classMD.field4485] = var1;
      classMD.field4485 = (1 + 75872381 * classMD.field4485 & 1764087297) * -911507243;
      if (var3 != 0L && var1 > var3) {
      }

      synchronized (this) {
         classDD.field1403 = field6188;
      }

      this.vmethod231((byte)96);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hb")
   protected abstract void vmethod228(int var1);

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("vu")
   public void method11161() {
      this.method11074(2073411019);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajm")
   public final void method11113(WindowEvent var1) {
   }

   @Override
   public void run() {
      this.field6221 = Thread.currentThread();
      this.field6221.setName("Client");
      this.field6221.setUncaughtExceptionHandler((var0, var1x) -> method11160(null, var1x));

      try {
         this.setFocusCycleRoot(true);
         this.method11166();
         this.method11159();
         classTW.field6248 = new classMG();
         if (Boolean.getBoolean("runelite.delaystart")) {
            this.field6222 = new Semaphore(0);
            this.field6222.acquire();
         }

         while (0L == field6189 * -4157353362753048141L) {
            ActorSpotAnim.field5962 = classTW.field6248.vmethod536(20, 1) * -1242900443;

            for (int var1 = 0; var1 < ActorSpotAnim.field5962 * 1906148269; var1++) {
               this.method11161();
            }

            if (classMG.field4501) {
               this.method11171();
               this.method11165(this.field6217);
            }
         }
      } catch (Exception var2) {
         method11160(null, var2);
         this.method11163("crash");
      }

      this.method11164();
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ir")
   protected abstract void vmethod240(int var1);

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;ZZB)V")
   @ObfuscatedName("agk")
   protected final void method11121(int var1, String var2, boolean var3, boolean var4, byte var5) {
      try {
         try {
            Graphics var6 = this.field6217.getGraphics();
            if (null == classAB.field123) {
               classAB.field123 = new Font("Helvetica", 1, 13);
               classAF.field145 = this.field6217.getFontMetrics(classAB.field123);
            }

            if (var3) {
               if (var5 == 1) {
                  return;
               }

               if (!var4) {
                  if (var5 == 1) {
                     throw new IllegalStateException();
                  }

                  var6.setColor(Color.black);
                  var6.fillRect(0, 0, classSA.field5895 * 773246731, 8379747 * IntProjection.field1936);
               }
            }

            Color var7 = new Color(140, 17, 17);

            try {
               if (null == classLY.field4441) {
                  if (var5 == 1) {
                     throw new IllegalStateException();
                  }

                  classLY.field4441 = this.field6217.createImage(304, 34);
               }

               Graphics var8 = classLY.field4441.getGraphics();
               var8.setColor(var7);
               var8.drawRect(0, 0, 303, 33);
               var8.fillRect(2, 2, var1 * 3, 30);
               var8.setColor(Color.black);
               var8.drawRect(1, 1, 301, 31);
               var8.fillRect(2 + 3 * var1, 2, 300 - var1 * 3, 30);
               var8.setFont(classAB.field123);
               var8.setColor(Color.white);
               var8.drawString(var2, (304 - classAF.field145.stringWidth(var2)) / 2, 22);
               int var10002 = classSA.field5895 * 773246731 / 2 - 152;
               int var10003 = IntProjection.field1936 * 8379747 / 2;
               byte var10004;
               if (var4) {
                  if (var5 == 1) {
                     throw new IllegalStateException();
                  }

                  var10004 = 50;
               } else {
                  var10004 = -18;
               }

               var6.drawImage(classLY.field4441, var10002, var10003 + var10004, null);
            } catch (Exception var11) {
               int var9 = classSA.field5895 * 773246731 / 2 - 152;
               int var10 = 8379747 * IntProjection.field1936 / 2 - 18;
               var6.setColor(var7);
               var6.drawRect(var9, var10, 303, 33);
               var6.fillRect(var9 + 2, 2 + var10, 3 * var1, 30);
               var6.setColor(Color.black);
               var6.drawRect(1 + var9, var10 + 1, 301, 31);
               var6.fillRect(var9 + 2 + var1 * 3, 2 + var10, 300 - 3 * var1, 30);
               var6.setFont(classAB.field123);
               var6.setColor(Color.white);
               var6.drawString(var2, var9 + (304 - classAF.field145.stringWidth(var2)) / 2, var10 + 22);
            }
         } catch (Exception var12) {
            this.field6217.repaint();
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "tq.agk(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Luq;")
   @ObfuscatedName("akt")
   classUQ method11136() {
      Container var1 = this.method11131(-1757963978);
      int var2 = Math.max(var1.getWidth(), 2128739322 * this.field6213);
      int var3 = Math.max(var1.getHeight(), this.field6216 * -450704939);
      if (this.field6219 != null) {
         Insets var4 = this.field6219.getInsets();
         var2 -= var4.right + var4.left;
         var3 -= var4.top + var4.bottom;
      }

      return new classUQ(var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/awt/Container;")
   @ObfuscatedName("agj")
   Container method11131(int var1) {
      try {
         if (null != this.field6219) {
            if (var1 >= 1024425027) {
               throw new IllegalStateException();
            } else {
               return this.field6219;
            }
         } else {
            return this;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.agj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("agt")
   protected final boolean method11139(byte var1) {
      try {
         boolean var10000;
         if (null != this.field6219) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.agt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("adf")
   protected abstract void vmethod254();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("pe")
   public static void method11072(GameEngine var0) {
      if (var0 == null) {
         var0.method11033();
      } else {
         try {
            if (null != classDQ.field1602) {
               String var1 = classDQ.field1602.toLowerCase();
               if (var1.indexOf("sun") != -1 || var1.indexOf("apple") != -1) {
                  String var2 = classMB.field4477;
                  if (var2.equals("1.1")
                     || var2.startsWith("1.1.")
                     || var2.equals("1.2")
                     || var2.startsWith("1.2.")
                     || var2.equals("1.3")
                     || var2.startsWith("1.3.")
                     || var2.equals("1.4")
                     || var2.startsWith("1.4.")
                     || var2.equals("1.5")
                     || var2.startsWith("1.5.")
                     || var2.equals("1.6.0")) {
                     method11129(var0, "wrongjava", 2079324763);
                     return;
                  }

                  if (var2.startsWith("1.6.0_")) {
                     int var3 = 6;

                     while (var3 < var2.length() && classEB.method3818(var2.charAt(var3), (byte)-48)) {
                        var3++;
                     }

                     String var4 = var2.substring(6, var3);
                     if (IntProjection.method4108(var4, (byte)-1) && classJF.method6402(var4, -700834590) < 10) {
                        method11129(var0, "wrongjava", -498527929);
                        return;
                     }
                  }

                  field6192 = -1366222351;
               }
            }

            var0.setFocusCycleRoot(true);
            var0.method11063(2126562725);
            var0.vmethod228(1624818309);
            classTW.field6248 = classEF.method3857((byte)-63);

            while (-4157353362753048141L * field6189 == 0L || classDD.method2988(16711680) < field6189 * -4157353362753048141L) {
               ActorSpotAnim.field5962 = classTW.field6248.vmethod535(field6185 * 1440739541, 1149475075 * field6192, -1162884564) * -193062407;

               for (int var6 = 0; var6 < 279313160 * ActorSpotAnim.field5962; var6++) {
                  var0.method11074(-275223484);
               }

               var0.method11083(2116789255);
               var0.method11153(var0.field6217, (byte)-1);
            }
         } catch (Exception var5) {
            classAAU.method270(null, var5, (byte)25);
            method11129(var0, "crash", 1316802590);
         }

         var0.method11092((byte)-81);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)Ljava/awt/Container;")
   @ObfuscatedName("bl")
   public static Container method11132(GameEngine var0) {
      return (Container)(null != var0.field6219 ? var0.field6219 : var0);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   public void method11073() {
      try {
         if (null != classDQ.field1602) {
            String var1 = classDQ.field1602.toLowerCase();
            if (var1.indexOf("sun") != -1 || var1.indexOf("apple") != -1) {
               String var2 = classMB.field4477;
               if (var2.equals("1.1")
                  || var2.startsWith("1.1.")
                  || var2.equals("1.2")
                  || var2.startsWith("1.2.")
                  || var2.equals("1.3")
                  || var2.startsWith("1.3.")
                  || var2.equals("1.4")
                  || var2.startsWith("1.4.")
                  || var2.equals("1.5")
                  || var2.startsWith("1.5.")
                  || var2.equals("1.6.0")) {
                  method11129(this, "wrongjava", 966850894);
                  return;
               }

               if (var2.startsWith("1.6.0_")) {
                  int var3 = 6;

                  while (var3 < var2.length() && classEB.method3818(var2.charAt(var3), (byte)-30)) {
                     var3++;
                  }

                  String var4 = var2.substring(6, var3);
                  if (IntProjection.method4108(var4, (byte)-1) && classJF.method6402(var4, 1137653930) < 10) {
                     method11129(this, "wrongjava", -769319227);
                     return;
                  }
               }

               field6192 = 437855323;
            }
         }

         this.setFocusCycleRoot(true);
         this.method11063(1766008000);
         this.vmethod228(1624818309);
         classTW.field6248 = classEF.method3857((byte)-91);

         while (-4157353362753048141L * field6189 == 0L || classDD.method2988(16711680) < field6189 * -4157353362753048141L) {
            ActorSpotAnim.field5962 = classTW.field6248.vmethod535(field6185 * 867481676, -2028709291 * field6192, -1162884564) * -1025597522;

            for (int var6 = 0; var6 < -219142571 * ActorSpotAnim.field5962; var6++) {
               this.method11074(2045060950);
            }

            this.method11083(1899420747);
            this.method11153(this.field6217, (byte)-1);
         }
      } catch (Exception var5) {
         classAAU.method270(null, var5, (byte)123);
         method11129(this, "crash", 196044972);
      }

      this.method11092((byte)-118);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aan")
   protected abstract void vmethod208();

   @Override
   public final void focusGained(FocusEvent var1) {
      this.method11156(var1);

      try {
         field6188 = true;
         this.field6205 = true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.focusGained(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aht")
   protected final void method11026() {
      classQY.method9920(-1555149582);
      field6196.method10869(this.field6217, 828566663);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aca")
   protected abstract void vmethod230();

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aip")
   protected final boolean method11066() {
      String var1 = this.method11157().getHost().toLowerCase();
      if (var1.equals("jagex.com") || var1.endsWith(".jagex.com")) {
         return true;
      } else if (var1.equals("runescape.com") || var1.endsWith(".runescape.com")) {
         return true;
      } else if (var1.endsWith("127.0.0.1")) {
         return true;
      } else {
         while (!var1.isEmpty() && var1.charAt(var1.length() - 1) >= '0' && var1.charAt(var1.length() - 1) <= '9') {
            var1 = var1.substring(0, var1.length() - 1);
         }

         if (var1.endsWith("192.168.1.")) {
            return true;
         } else {
            method11129(this, "invalidhost", 467219196);
            return false;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("agy")
   protected static final void method11003() {
      classTW.field6248.vmethod530((byte)5);

      for (int var0 = 0; var0 < 32; var0++) {
         field6195[var0] = 0L;
      }

      for (int var1 = 0; var1 < 32; var1++) {
         field6193[var1] = 0L;
      }

      ActorSpotAnim.field5962 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ach")
   protected abstract void vmethod233();

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;ZZ)V")
   @ObfuscatedName("akb")
   protected final void method11122(int var1, String var2, boolean var3, boolean var4) {
      try {
         Graphics var5 = this.field6217.getGraphics();
         if (null == classAB.field123) {
            classAB.field123 = new Font("Helvetica", 1, 13);
            classAF.field145 = this.field6217.getFontMetrics(classAB.field123);
         }

         if (var3 && !var4) {
            var5.setColor(Color.black);
            var5.fillRect(0, 0, classSA.field5895 * 773246731, 8379747 * IntProjection.field1936);
         }

         Color var6 = new Color(228569689, 17, 17);

         try {
            if (null == classLY.field4441) {
               classLY.field4441 = this.field6217.createImage(304, 34);
            }

            Graphics var7 = classLY.field4441.getGraphics();
            var7.setColor(var6);
            var7.drawRect(0, 0, -2030130181, -1183911800);
            var7.fillRect(2, 2, var1 * 3, 30);
            var7.setColor(Color.black);
            var7.drawRect(1, 1, 301, -1895772977);
            var7.fillRect(2 + 3 * var1, 2, 300 - var1 * 3, 30);
            var7.setFont(classAB.field123);
            var7.setColor(Color.white);
            var7.drawString(var2, (2030203799 - classAF.field145.stringWidth(var2)) / 2, 22);
            var5.drawImage(classLY.field4441, classSA.field5895 * 773246731 / 2 - 152, IntProjection.field1936 * 302815990 / 2 + (var4 ? 50 : -18), null);
         } catch (Exception var10) {
            int var8 = classSA.field5895 * 773246731 / 2 - 152;
            int var9 = 8379747 * IntProjection.field1936 / 2 - 18;
            var5.setColor(var6);
            var5.drawRect(var8, var9, 1631460915, 33);
            var5.fillRect(var8 + 2, 2 + var9, 3 * var1, 30);
            var5.setColor(Color.black);
            var5.drawRect(1 + var8, var9 + 1, 301, 31);
            var5.fillRect(var8 + 2 + var1 * 3, 2 + var9, 300 - 3 * var1, 30);
            var5.setFont(classAB.field123);
            var5.setColor(Color.white);
            var5.drawString(var2, var8 + (1810179764 - classAF.field145.stringWidth(var2)) / 2, var9 + 22);
         }
      } catch (Exception var11) {
         this.field6217.repaint();
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("acw")
   protected abstract void vmethod239(boolean var1);

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("in")
   public void method11162(boolean var1) {
      this.field6206 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("go")
   public void method11163(String var1) {
      method11129(this, var1, -57794124);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("acx")
   protected abstract void vmethod232();

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("acd")
   protected abstract void vmethod241();

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("aix")
   protected void method11064(boolean var1) {
      if (this.field6201 != var1) {
         this.field6201 = var1;
         if (classWO.field6761 != null) {
            classWO.field6761.method13364(var1, (byte)28);
            classWO.field6761.method13361(61241127);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("acq")
   protected abstract void vmethod244();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("sp")
   public static void method11041(GameEngine var0) {
      Canvas var1 = var0.field6217;
      var1.addMouseListener(classTZ.field6257);
      var1.addMouseMotionListener(classTZ.field6257);
      var1.addFocusListener(classTZ.field6257);
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("hn")
   protected abstract void vmethod250(byte var1);

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ady")
   protected abstract void vmethod253();

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ada")
   protected abstract void vmethod252();

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("acr")
   protected abstract void vmethod242();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("am")
   public static void method11127(GameEngine var0) {
      if (var0 == null) {
         var0.method11068();
      }

      classLY.field4441 = null;
      classAB.field123 = null;
      classAF.field145 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("qr")
   public void method11164() {
      this.method11092((byte)-11);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("zr")
   public static void method11096(GameEngine var0) {
      if (var0 == null) {
         var0.method11140();
      }

      if (field6187 == var0 && !field6190) {
         field6189 = classDD.method2988(16711680) * -7013317878618397317L;
         Friend.method10197(5000L);
         var0.method11092((byte)-96);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ajb")
   public final void method11097() {
      if (field6187 == this && !field6190) {
         field6189 = classDD.method2988(16711680) * -7013317878618397317L;
         Friend.method10197(5000L);
         this.method11092((byte)-17);
      }
   }

   @Override
   public final void windowClosing(WindowEvent var1) {
      try {
         this.destroy();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.windowClosing(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("afg")
   public final void method11104(FocusEvent var1) {
      field6188 = false;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("aff")
   public final void method11105(FocusEvent var1) {
      field6188 = false;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Graphics;)V")
   @ObfuscatedName("afx")
   public final void method11100(Graphics var1) {
      this.paint(var1);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/awt/Container;")
   @ObfuscatedName("akl")
   Container method11133() {
      return (Container)(null != this.field6219 ? this.field6219 : this);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzs;Ljava/lang/String;)Lzs;")
   @ObfuscatedName("xe")
   public static ScriptEventBuilder method11010(ScriptEventBuilder var0, String var1) {
      if (var0 == null) {
         var0.method14214(var1);
      }

      var0.field7265 = var1;
      return var0;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Graphics;)V")
   @ObfuscatedName("afk")
   public final synchronized void method11101(Graphics var1) {
      if (field6187 == this && !field6190) {
         this.field6205 = true;
         if (classDD.method2988(16711680) - this.field6208 * -5853619249873121909L > 1000L) {
            Rectangle var2 = var1.getClipBounds();
            if (null == var2 || var2.width >= -1526156459 * classSA.field5895 && var2.height >= 8379747 * IntProjection.field1936) {
               this.field6207 = true;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("agq")
   protected static final int method10998() {
      return field6196.method10862(621560484);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("agc")
   protected static final int method10999() {
      return field6196.method10862(1778578561);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("bd")
   public static void method11048(GameEngine var0) {
      if (var0 == null) {
         var0.method11084();
      } else {
         Container var1 = var0.method11131(181052028);
         if (null != var1) {
            classUQ var2 = var0.method11137(1329131020);
            var0.field6212 = Math.max(444480655 * var2.field6368, -1983342777 * var0.field6213) * 833806867;
            var0.field6211 = Math.max(var2.field6369 * 2072708115, -814860968 * var0.field6216) * -181673913;
            if (69365437 * var0.field6212 <= 0) {
               var0.field6212 = 342373637;
            }

            if (2143909619 * var0.field6211 <= 0) {
               var0.field6211 = -181673913;
            }

            classSA.field5895 = Math.min(39467035 * var0.field6212, var0.field6214 * 1678469514) * -2089155421;
            IntProjection.field1936 = Math.min(-322847495 * var0.field6211, 697093715 * var0.field6215) * 841518155;
            var0.field6203 = 828993495 * ((39467035 * var0.field6212 - classSA.field5895 * 773246731) / 2);
            var0.field6204 = 0;
            var0.field6217.setSize(2108718372 * classSA.field5895, 1302063658 * IntProjection.field1936);
            classWO.field6761 = new MainBufferProvider(classSA.field5895 * -449368026, IntProjection.field1936 * 8379747, var0.field6217, var0.field6201);
            if (var1 == var0.field6219) {
               Insets var3 = var0.field6219.getInsets();
               var0.field6217.setLocation(1451619303 * var0.field6203 + var3.left, -2105698082 * var0.field6204 + var3.top);
            } else {
               var0.field6217.setLocation(var0.field6203 * -200174874, var0.field6204 * -1650576623);
            }

            var0.field6205 = true;
            var0.vmethod250((byte)62);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("agx")
   protected static final boolean method11000() {
      return field6196.method10865(-1983657248);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aid")
   final synchronized void method11060() {
      Container var1 = this.method11131(-1161154185);
      if (null != this.field6217) {
         this.field6217.removeFocusListener(this);
         var1.remove(this.field6217);
      }

      classSA.field5895 = Math.max(var1.getWidth(), this.field6213 * -248855302) * -2139325209;
      IntProjection.field1936 = Math.max(var1.getHeight(), this.field6216 * -450704939) * 841518155;
      if (null != this.field6219) {
         Insets var2 = this.field6219.getInsets();
         classSA.field5895 = classSA.field5895 - (var2.left + var2.right) * -2089155421;
         IntProjection.field1936 = IntProjection.field1936 - 841518155 * (var2.top + var2.bottom);
      }

      this.field6217 = new classTD(this);
      var1.setBackground(Color.BLACK);
      var1.setLayout(null);
      var1.add(this.field6217);
      this.field6217.setSize(classSA.field5895 * 773246731, IntProjection.field1936 * 8379747);
      this.field6217.setVisible(true);
      this.field6217.setBackground(Color.BLACK);
      if (this.field6219 == var1) {
         Insets var3 = this.field6219.getInsets();
         this.field6217.setLocation(var3.left + -695928323 * this.field6203, this.field6204 * 438949312 + var3.top);
      } else {
         this.field6217.setLocation(1451619303 * this.field6203, this.field6204 * 315102557);
      }

      this.field6217.addFocusListener(this);
      this.field6217.requestFocus();
      this.field6205 = true;
      if (null != classWO.field6761
         && classSA.field5895 * -379657341 == classWO.field6761.field6966 * -1238133717
         && IntProjection.field1936 * 1793131877 == classWO.field6761.field6965 * 1855103015) {
         MainBufferProvider.method10850((MainBufferProvider)classWO.field6761, this.field6217, (byte)-113);
         classWO.field6761.vmethod578(0, 0, (byte)113);
      } else {
         classWO.field6761 = new MainBufferProvider(-2043236416 * classSA.field5895, 8379747 * IntProjection.field1936, this.field6217, this.field6201);
      }

      this.field6207 = false;
      this.field6208 = classDD.method2988(16711680) * -1528671439279181277L;
   }

   public void unblockStartup() {
      if (this.field6222 != null) {
         this.field6222.release();
      }
   }

   @ObfuscatedSignature(descriptor = "(Ltq;B)Z")
   @ObfuscatedName("eo")
   public static boolean method11067(GameEngine var0, byte var1) {
      if (var0 == null) {
         var0.method11070(var1);
      }

      try {
         String var2 = var0.method11157().getHost().toLowerCase();
         if (!var2.equals("jagex.com")) {
            if (var1 >= 2) {
               throw new IllegalStateException();
            }

            if (!var2.endsWith(".jagex.com")) {
               if (!var2.equals("runescape.com")) {
                  if (var1 >= 2) {
                     throw new IllegalStateException();
                  }

                  if (!var2.endsWith(".runescape.com")) {
                     if (var2.endsWith("127.0.0.1")) {
                        if (var1 >= 2) {
                           throw new IllegalStateException();
                        }

                        return true;
                     }

                     while (!var2.isEmpty() && var2.charAt(var2.length() - 1) >= '0') {
                        if (var1 >= 2) {
                           throw new IllegalStateException();
                        }

                        if (var2.charAt(var2.length() - 1) > '9') {
                           break;
                        }

                        if (var1 >= 2) {
                           throw new IllegalStateException();
                        }

                        var2 = var2.substring(0, var2.length() - 1);
                     }

                     if (var2.endsWith("192.168.1.")) {
                        if (var1 >= 2) {
                           throw new IllegalStateException();
                        }

                        return true;
                     }

                     method11129(var0, "invalidhost", 1608342709);
                     return false;
                  }

                  if (var1 >= 2) {
                     throw new IllegalStateException();
                  }
               }

               return true;
            }

            if (var1 >= 2) {
               throw new IllegalStateException();
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tq.ago(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("agn")
   protected static final boolean method11001() {
      return field6196.method10865(-687071513);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ps")
   public void method11140() {
      field6194 = 0 * 2069175365;
   }

   @Override
   public final void update(Graphics var1) {
      try {
         this.paint(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.update(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ahj")
   protected void method11016(String var1) {
      this.field6220.setContents(new StringSelection(var1), null);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ahp")
   protected static int method11005() {
      int var0 = 0;
      if (null == classAT.field279 || !classAT.field279.isValid()) {
         try {
            for (GarbageCollectorMXBean var2 : ManagementFactory.getGarbageCollectorMXBeans()) {
               if (var2.isValid()) {
                  classAT.field279 = var2;
                  field6186 = 1622412589189406289L;
                  field6197 = -6728113685686042049L;
               }
            }
         } catch (Throwable var9) {
         }
      }

      if (classAT.field279 != null) {
         long var10 = classDD.method2988(16711680);
         long var3 = classAT.field279.getCollectionTime();
         if (-1L != 3566866902695694913L * field6197) {
            long var5 = var3 - field6197 * 3566866902695694913L;
            long var7 = var10 - field6186 * -5967588509917407921L;
            if (var7 != 0L) {
               var0 = (int)(var5 * 100L / var7);
            }
         }

         field6197 = var3 * 6728113685686042049L;
         field6186 = -1622412589189406289L * var10;
      }

      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ahy")
   protected static int method11006() {
      int var0 = 0;
      if (null == classAT.field279 || !classAT.field279.isValid()) {
         try {
            for (GarbageCollectorMXBean var2 : ManagementFactory.getGarbageCollectorMXBeans()) {
               if (var2.isValid()) {
                  classAT.field279 = var2;
                  field6186 = 1622412589189406289L;
                  field6197 = -6728113685686042049L;
               }
            }
         } catch (Throwable var9) {
         }
      }

      if (classAT.field279 != null) {
         long var10 = classDD.method2988(16711680);
         long var3 = classAT.field279.getCollectionTime();
         if (-1L != 3566866902695694913L * field6197) {
            long var5 = var3 - field6197 * 3566866902695694913L;
            long var7 = var10 - field6186 * -5967588509917407921L;
            if (var7 != 0L) {
               var0 = (int)(var5 * 100L / var7);
            }
         }

         field6197 = var3 * 6728113685686042049L;
         field6186 = -1622412589189406289L * var10;
      }

      return var0;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ahl")
   protected final void method11007(int var1, int var2) {
      if (1937290719 * this.field6214 != var1 || this.field6215 * 697093715 != var2) {
         this.method11088(2142319093);
      }

      this.field6214 = -63355361 * var1;
      this.field6215 = 1815127515 * var2;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ahx")
   protected final void method11008(int var1, int var2) {
      if (1937290719 * this.field6214 != var1 || this.field6215 * 697093715 != var2) {
         this.method11088(840777380);
      }

      this.field6214 = -63355361 * var1;
      this.field6215 = -1005506903 * var2;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ahc")
   protected final void method11009(int var1, int var2) {
      if (1937290719 * this.field6214 != var1 || this.field6215 * 697093715 != var2) {
         this.method11088(448953458);
      }

      this.field6214 = -63355361 * var1;
      this.field6215 = 1815127515 * var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ais")
   final synchronized void method11061() {
      Container var1 = this.method11131(423774202);
      if (null != this.field6217) {
         this.field6217.removeFocusListener(this);
         var1.remove(this.field6217);
      }

      classSA.field5895 = Math.max(var1.getWidth(), this.field6213 * 237759111) * -2089155421;
      IntProjection.field1936 = Math.max(var1.getHeight(), this.field6216 * -450704939) * 841518155;
      if (null != this.field6219) {
         Insets var2 = this.field6219.getInsets();
         classSA.field5895 = classSA.field5895 - (var2.left + var2.right) * -2089155421;
         IntProjection.field1936 = IntProjection.field1936 - 841518155 * (var2.top + var2.bottom);
      }

      this.field6217 = new classTD(this);
      var1.setBackground(Color.BLACK);
      var1.setLayout(null);
      var1.add(this.field6217);
      this.field6217.setSize(classSA.field5895 * 773246731, IntProjection.field1936 * 8379747);
      this.field6217.setVisible(true);
      this.field6217.setBackground(Color.BLACK);
      if (this.field6219 == var1) {
         Insets var3 = this.field6219.getInsets();
         this.field6217.setLocation(var3.left + 1451619303 * this.field6203, this.field6204 * -673776453 + var3.top);
      } else {
         this.field6217.setLocation(1451619303 * this.field6203, this.field6204 * -673776453);
      }

      this.field6217.addFocusListener(this);
      this.field6217.requestFocus();
      this.field6205 = true;
      if (null != classWO.field6761
         && classSA.field5895 * 773246731 == classWO.field6761.field6966 * -1238133717
         && IntProjection.field1936 * 8379747 == classWO.field6761.field6965 * 1855103015) {
         MainBufferProvider.method10850((MainBufferProvider)classWO.field6761, this.field6217, (byte)-120);
         classWO.field6761.vmethod578(0, 0, (byte)119);
      } else {
         classWO.field6761 = new MainBufferProvider(773246731 * classSA.field5895, 8379747 * IntProjection.field1936, this.field6217, this.field6201);
      }

      this.field6207 = false;
      this.field6208 = classDD.method2988(16711680) * -1528671439279181277L;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ahs")
   protected void method11017(String var1) {
      this.field6220.setContents(new StringSelection(var1), null);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/net/URL;")
   @ObfuscatedName("zx")
   public URL method11157() {
      return this.method11147();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ahu")
   protected void method11018(String var1) {
      this.field6220.setContents(new StringSelection(var1), null);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("aiw")
   protected final void method11056(int var1, int var2, int var3, int var4) {
      try {
         if (null != field6187) {
            field6198 += 354831441;
            if (221150385 * field6198 >= 3) {
               method11129(this, "alreadyloaded", 1174780290);
               return;
            }

            this.method11149().vmethod560(this.method11157(), "_self");
            return;
         }

         field6187 = this;
         classSA.field5895 = var1 * -2089155421;
         IntProjection.field1936 = 841518155 * var2;
         classAAL.field57 = var3 * -154973313;
         classAAF.field46 = -609160337 * var4;
         classAAF.field49 = this.method11147();
         if (classWW.field6784 == null) {
            classWW.field6784 = new classMB();
         }

         classMB.method7559(classWW.field6784, this, 1, 382179368);
      } catch (Exception var6) {
         classAAU.method270(null, var6, (byte)12);
         method11129(this, "crash", -18099327);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ahf")
   protected void method11019(String var1) {
      this.field6220.setContents(new StringSelection(var1), null);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/awt/datatransfer/Clipboard;")
   @ObfuscatedName("ahq")
   public Clipboard method11023() {
      return this.field6220;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/awt/datatransfer/Clipboard;")
   @ObfuscatedName("aho")
   public Clipboard method11024() {
      return this.field6220;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("ti")
   public static void method11082(GameEngine var0) {
      if (var0 == null) {
         var0.method11174();
      } else {
         Container var1 = var0.method11131(-867050494);
         long var2 = classDD.method2988(16711680);
         long var4 = field6195[-1804509917 * classFW.field2415];
         field6195[classFW.field2415 * -1804509917] = var2;
         classFW.field2415 = (1 + classFW.field2415 * -1804509917 & 31) * 1510849163;
         if (0L != var4 && var2 > var4) {
            int var6 = (int)(var2 - var4);
            field6191 = ((var6 >> 1) + 32000) / var6 * -393098901;
         }

         if ((field6194 += 2069175365) * 801347213 - 1 > 50) {
            field6194 -= 379553146;
            var0.field6205 = true;
            var0.field6217.setSize(classSA.field5895 * 773246731, IntProjection.field1936 * 8379747);
            var0.field6217.setVisible(true);
            if (var1 == var0.field6219) {
               Insets var7 = var0.field6219.getInsets();
               var0.field6217.setLocation(var7.left + 1451619303 * var0.field6203, var7.top + -673776453 * var0.field6204);
            } else {
               var0.field6217.setLocation(var0.field6203 * 1451619303, var0.field6204 * -673776453);
            }
         }

         if (var0.field6207) {
            var0.method11158((byte)10);
         }

         var0.method11087(1293126270);
         var0.vmethod237(var0.field6205, 1529356435);
         if (var0.field6205) {
            var0.method11051((byte)-88);
         }

         var0.field6205 = false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ltq;I)V")
   @ObfuscatedName("br")
   public static void method11042(GameEngine var0, int var1) {
      if (var0 == null) {
         var0.method11045(var1);
      } else {
         try {
            Canvas var2 = var0.field6217;
            var2.addMouseListener(classTZ.field6257);
            var2.addMouseMotionListener(classTZ.field6257);
            var2.addFocusListener(classTZ.field6257);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "tq.afm(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("afu")
   protected final void method11144(int var1, int var2, byte var3) {
      if (classOE.field4841.isStretchedEnabled() && classOE.field4841.isResized()) {
         Dimension var4 = classOE.field4841.getRealDimensions();
         var1 = var4.width;
         var2 = var4.height;
      }

      int var7 = var2;
      int var6 = var1;
      GameEngine var5 = this;
      byte var8 = 81;

      try {
         label29: {
            if (1937290719 * var5.field6214 == var6) {
               if (var8 <= 8) {
                  throw new IllegalStateException();
               }

               if (var5.field6215 * 697093715 == var7) {
                  break label29;
               }

               if (var8 <= 8) {
                  throw new IllegalStateException();
               }
            }

            var5.method11088(1092999054);
         }

         var5.field6214 = -63355361 * var6;
         var5.field6215 = 1815127515 * var7;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "tq.afu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ahe")
   protected final void method11027() {
      classQY.method9920(-2010438817);
      field6196.method10869(this.field6217, -723229338);
   }

   @ObfuscatedSignature(descriptor = "(Ltq;Ljava/lang/String;I)V")
   @ObfuscatedName("tv")
   public static void method11129(GameEngine var0, String var1, int var2) {
      if (var0 == null) {
         var0.method11020(var1, var2);
      }

      try {
         if (var0.field6202) {
            if (var2 <= -877384413) {
               throw new IllegalStateException();
            }
         } else {
            var0.field6202 = true;
            System.out.println("error_game_" + var1);

            try {
               var0.method11149().vmethod560(new URL(var0.method11147(), "error_game_" + var1 + ".ws"), "_self");
            } catch (Exception var4) {
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tq.agh(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)V")
   @ObfuscatedName("nv")
   public void method11165(Object var1) {
      this.method11153(var1, (byte)-1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ahg")
   protected final void method11028() {
      classQY.method9920(-1727724451);
      field6196.method10869(this.field6217, 1670143838);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ahv")
   protected final void method11029() {
      classQY.method9920(-2096028747);
      field6196.method10869(this.field6217, -2110296974);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ahw")
   protected final void method11033() {
      field6196.method10875((byte)-108);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("acg")
   protected abstract void vmethod234();

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("jn")
   public void method11156(FocusEvent var1) {
      FocusChanged var2 = new FocusChanged();
      var2.setFocused(true);
      classOE.field4841.getCallbacks().post(var2);
   }

   public Thread getClientThread() {
      return this.field6221;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ahb")
   protected final void method11034() {
      field6196.method10875((byte)12);
   }

   @ObfuscatedSignature(descriptor = "(Ltl;I)V")
   @ObfuscatedName("ahk")
   protected void method11039(classTL var1, int var2) {
      field6196.method10859(var1, var2, 806192515);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ahn")
   protected final void method11043() {
      Canvas var1 = this.field6217;
      var1.addMouseListener(classTZ.field6257);
      var1.addMouseMotionListener(classTZ.field6257);
      var1.addFocusListener(classTZ.field6257);
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("in")
   protected abstract void vmethod231(byte var1);

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ajo")
   final void method11049() {
      Container var1 = this.method11131(-924541457);
      if (null != var1) {
         classUQ var2 = this.method11137(-439287817);
         this.field6212 = Math.max(518523273 * var2.field6368, 237759111 * this.field6213) * 833806867;
         this.field6211 = Math.max(var2.field6369 * -874893249, -450704939 * this.field6216) * -181673913;
         if (39467035 * this.field6212 <= 0) {
            this.field6212 = 833806867;
         }

         if (1525008759 * this.field6211 <= 0) {
            this.field6211 = -181673913;
         }

         classSA.field5895 = Math.min(39467035 * this.field6212, this.field6214 * 1937290719) * -2089155421;
         IntProjection.field1936 = Math.min(1525008759 * this.field6211, 697093715 * this.field6215) * 841518155;
         this.field6203 = 828993495 * ((39467035 * this.field6212 - classSA.field5895 * 773246731) / 2);
         this.field6204 = 0;
         this.field6217.setSize(773246731 * classSA.field5895, 8379747 * IntProjection.field1936);
         classWO.field6761 = new MainBufferProvider(classSA.field5895 * 773246731, IntProjection.field1936 * 8379747, this.field6217, this.field6201);
         if (var1 == this.field6219) {
            Insets var3 = this.field6219.getInsets();
            this.field6217.setLocation(1451619303 * this.field6203 + var3.left, -673776453 * this.field6204 + var3.top);
         } else {
            this.field6217.setLocation(this.field6203 * 1451619303, this.field6204 * -673776453);
         }

         this.field6205 = true;
         this.vmethod250((byte)119);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aig")
   final void method11050() {
      Container var1 = this.method11131(-217206153);
      if (null != var1) {
         classUQ var2 = this.method11137(1114203534);
         this.field6212 = Math.max(1142070103 * var2.field6368, 237759111 * this.field6213) * 490288056;
         this.field6211 = Math.max(var2.field6369 * -874893249, 466774250 * this.field6216) * -665172647;
         if (39467035 * this.field6212 <= 0) {
            this.field6212 = 833806867;
         }

         if (-72991790 * this.field6211 <= 0) {
            this.field6211 = -181673913;
         }

         classSA.field5895 = Math.min(-738935847 * this.field6212, this.field6214 * 1937290719) * -1191905764;
         IntProjection.field1936 = Math.min(1525008759 * this.field6211, 697093715 * this.field6215) * 841518155;
         this.field6203 = 579240516 * ((1216810758 * this.field6212 - classSA.field5895 * -448671383) / 2);
         this.field6204 = 0;
         this.field6217.setSize(-1925046179 * classSA.field5895, 1483836700 * IntProjection.field1936);
         classWO.field6761 = new MainBufferProvider(classSA.field5895 * 1492222171, IntProjection.field1936 * 1962161828, this.field6217, this.field6201);
         if (var1 == this.field6219) {
            Insets var3 = this.field6219.getInsets();
            this.field6217.setLocation(1451619303 * this.field6203 + var3.left, 2086326995 * this.field6204 + var3.top);
         } else {
            this.field6217.setLocation(this.field6203 * 812696419, this.field6204 * -673776453);
         }

         this.field6205 = true;
         this.vmethod250((byte)110);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("zs")
   public static void method11035(GameEngine var0) {
      if (var0 == null) {
         var0.method11089();
      }

      field6196.method10875((byte)24);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("wd")
   public void method11151() {
      if (this.field6217 != null) {
         this.field6217.removeComponentListener(this.field6199);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ahi")
   protected final void method11036() {
      field6196.method10875((byte)-91);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("agi")
   void method11083(int var1) {
      this.method11140();

      try {
         Container var2 = this.method11131(700771541);
         long var3 = classDD.method2988(16711680);
         long var5 = field6195[-1804509917 * classFW.field2415];
         field6195[classFW.field2415 * -1804509917] = var3;
         classFW.field2415 = (1 + classFW.field2415 * -1804509917 & 31) * 1510849163;
         if (0L != var5 && var3 > var5) {
            if (var1 <= -3745303) {
               throw new IllegalStateException();
            }

            int var7 = (int)(var3 - var5);
            field6191 = ((var7 >> 1) + 32000) / var7 * -393098901;
         }

         if ((field6194 += 2069175365) * 801347213 - 1 > 50) {
            if (var1 <= -3745303) {
               return;
            }

            field6194 -= 379553146;
            this.field6205 = true;
            this.field6217.setSize(classSA.field5895 * 773246731, IntProjection.field1936 * 8379747);
            this.field6217.setVisible(true);
            if (var2 == this.field6219) {
               if (var1 <= -3745303) {
                  return;
               }

               Insets var9 = this.field6219.getInsets();
               this.field6217.setLocation(var9.left + 1451619303 * this.field6203, var9.top + -673776453 * this.field6204);
            } else {
               this.field6217.setLocation(this.field6203 * 1451619303, this.field6204 * -673776453);
            }
         }

         if (this.field6207) {
            if (var1 <= -3745303) {
               throw new IllegalStateException();
            }

            this.method11158((byte)-76);
         }

         this.method11087(795576551);
         this.vmethod237(this.field6205, -397184520);
         if (this.field6205) {
            if (var1 <= -3745303) {
               return;
            }

            this.method11051((byte)-87);
         }

         this.field6205 = false;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "tq.agi(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("air")
   void method11052() {
      int var1 = this.field6203 * 1451619303;
      int var2 = this.field6204 * -673776453;
      int var3 = this.field6212 * 39467035 - 773246731 * classSA.field5895 - var1;
      int var4 = 1525008759 * this.field6211 - 8379747 * IntProjection.field1936 - var2;
      if (var1 > 0 || var3 > 0 || var2 > 0 || var4 > 0) {
         try {
            Container var5 = this.method11131(269048633);
            int var6 = 0;
            int var7 = 0;
            if (var5 == this.field6219) {
               Insets var8 = this.field6219.getInsets();
               var6 = var8.left;
               var7 = var8.top;
            }

            Graphics var10 = var5.getGraphics();
            var10.setColor(Color.black);
            if (var1 > 0) {
               var10.fillRect(var6, var7, var1, 1525008759 * this.field6211);
            }

            if (var2 > 0) {
               var10.fillRect(var6, var7, this.field6212 * 39467035, var2);
            }

            if (var3 > 0) {
               var10.fillRect(this.field6212 * 39467035 + var6 - var3, var7, var3, this.field6211 * 1525008759);
            }

            if (var4 > 0) {
               var10.fillRect(var6, var7 + 1525008759 * this.field6211 - var4, this.field6212 * 39467035, var4);
            }
         } catch (Exception var9) {
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ql")
   public void method11155() {
      this.addHierarchyListener(var0 -> {
         if ((var0.getChangeFlags() & 2L) != 0L && classOE.field4841.isDisplayable()) {
            client.method2374();
         }
      });
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("tw")
   public void method11166() {
      this.method11063(1880212578);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ho")
   protected abstract void vmethod207(int var1);

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aie")
   final void method11053() {
      field6196.method10872(this.field6217, (byte)31);
      Canvas var1 = this.field6217;
      var1.removeMouseListener(classTZ.field6257);
      var1.removeMouseMotionListener(classTZ.field6257);
      var1.removeFocusListener(classTZ.field6257);
      classTZ.field6254 = 0;
      if (null != this.field6224) {
         this.field6224.method10785(this.field6217, 798417951);
      }

      this.method11063(2058027058);
      field6196.method10869(this.field6217, 796787445);
      Canvas var2 = this.field6217;
      var2.addMouseListener(classTZ.field6257);
      var2.addMouseMotionListener(classTZ.field6257);
      var2.addFocusListener(classTZ.field6257);
      if (this.field6224 != null) {
         this.field6224.method10781(this.field6217, -571610227);
      }

      this.method11088(1646159564);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ain")
   protected final void method11044() {
      Canvas var1 = this.field6217;
      var1.addMouseListener(classTZ.field6257);
      var1.addMouseMotionListener(classTZ.field6257);
      var1.addFocusListener(classTZ.field6257);
   }

   public final void stop() {
      try {
         if (field6187 == this && !field6190) {
            field6189 = (classDD.method2988(16711680) + 4000L) * -7013317878618397317L;
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "tq.stop(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("aiq")
   protected final void method11057(int var1, int var2, int var3, int var4) {
      try {
         if (null != field6187) {
            field6198 += 354831441;
            if (221150385 * field6198 >= 3) {
               method11129(this, "alreadyloaded", 30111921);
               return;
            }

            this.method11149().vmethod560(this.method11157(), "_self");
            return;
         }

         field6187 = this;
         classSA.field5895 = var1 * -2089155421;
         IntProjection.field1936 = 841518155 * var2;
         classAAL.field57 = var3 * -154973313;
         classAAF.field46 = -609160337 * var4;
         classAAF.field49 = this.method11147();
         if (classWW.field6784 == null) {
            classWW.field6784 = new classMB();
         }

         classMB.method7559(classWW.field6784, this, 1, 2083130524);
      } catch (Exception var6) {
         classAAU.method270(null, var6, (byte)123);
         method11129(this, "crash", -44083956);
      }
   }

   protected GameEngine() {
      this.field6205 = true;
      this.field6206 = false;
      this.field6207 = false;
      this.field6208 = 0L;
      EventQueue var1 = null;

      try {
         var1 = Toolkit.getDefaultToolkit().getSystemEventQueue();
      } catch (Throwable var4) {
      }

      this.field6209 = var1;
      classTX var2 = new classTX();
      classAK.field211 = var2;
   }

   public void resizeCanvas() {
      method11047(this, 1684778192);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aib")
   final synchronized void method11062() {
      Container var1 = this.method11131(-889495304);
      if (null != this.field6217) {
         this.field6217.removeFocusListener(this);
         var1.remove(this.field6217);
      }

      classSA.field5895 = Math.max(var1.getWidth(), this.field6213 * 1927716663) * -124507522;
      IntProjection.field1936 = Math.max(var1.getHeight(), this.field6216 * -450704939) * 841518155;
      if (null != this.field6219) {
         Insets var2 = this.field6219.getInsets();
         classSA.field5895 = classSA.field5895 - (var2.left + var2.right) * 1247489739;
         IntProjection.field1936 = IntProjection.field1936 - 841518155 * (var2.top + var2.bottom);
      }

      this.field6217 = new classTD(this);
      var1.setBackground(Color.BLACK);
      var1.setLayout(null);
      var1.add(this.field6217);
      this.field6217.setSize(classSA.field5895 * -405479738, IntProjection.field1936 * 8379747);
      this.field6217.setVisible(true);
      this.field6217.setBackground(Color.BLACK);
      if (this.field6219 == var1) {
         Insets var3 = this.field6219.getInsets();
         this.field6217.setLocation(var3.left + 1451619303 * this.field6203, this.field6204 * 948918294 + var3.top);
      } else {
         this.field6217.setLocation(255584615 * this.field6203, this.field6204 * -673776453);
      }

      this.field6217.addFocusListener(this);
      this.field6217.requestFocus();
      this.field6205 = true;
      if (null != classWO.field6761
         && classSA.field5895 * 773246731 == classWO.field6761.field6966 * -1770606652
         && IntProjection.field1936 * 1683477505 == classWO.field6761.field6965 * 623188320) {
         MainBufferProvider.method10850((MainBufferProvider)classWO.field6761, this.field6217, (byte)-126);
         classWO.field6761.vmethod578(0, 0, (byte)84);
      } else {
         classWO.field6761 = new MainBufferProvider(773246731 * classSA.field5895, 8379747 * IntProjection.field1936, this.field6217, this.field6201);
      }

      this.field6207 = false;
      this.field6208 = classDD.method2988(16711680) * -1528671439279181277L;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("mf")
   public boolean method11167() {
      return this.field6206;
   }

   @Override
   public final void windowIconified(WindowEvent var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.windowIconified(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajq")
   public final void method11107(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("do")
   public void method11146() {
      if (classOE.field4841.isStretchedEnabled()) {
         Canvas var1 = this.getCanvas();
         Dimension var2 = classOE.field4841.getStretchedDimensions();
         var1.setSize(var2);
         var1.setLocation((var1.getParent().getWidth() - var2.width) / 2, 0);
         var1.validate();
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cz")
   public void method11150() {
      this.field6217.addComponentListener(this.field6199);
      this.field6218 = this.field6217.getSize();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ail")
   protected final boolean method11068() {
      String var1 = this.method11157().getHost().toLowerCase();
      if (var1.equals("jagex.com") || var1.endsWith(".jagex.com")) {
         return true;
      } else if (var1.equals("runescape.com") || var1.endsWith(".runescape.com")) {
         return true;
      } else if (var1.endsWith("127.0.0.1")) {
         return true;
      } else {
         while (!var1.isEmpty() && var1.charAt(var1.length() - 1) >= '0' && var1.charAt(var1.length() - 1) <= '9') {
            var1 = var1.substring(0, var1.length() - 1);
         }

         if (var1.endsWith("192.168.1.")) {
            return true;
         } else {
            method11129(this, "invalidhost", 2098035956);
            return false;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;ILjava/lang/String;ZZ)V")
   @ObfuscatedName("yd")
   public static void method11123(GameEngine var0, int var1, String var2, boolean var3, boolean var4) {
      try {
         Graphics var5 = var0.field6217.getGraphics();
         if (null == classAB.field123) {
            classAB.field123 = new Font("Helvetica", 1, 13);
            classAF.field145 = var0.field6217.getFontMetrics(classAB.field123);
         }

         if (var3 && !var4) {
            var5.setColor(Color.black);
            var5.fillRect(0, 0, classSA.field5895 * 773246731, 8379747 * IntProjection.field1936);
         }

         Color var6 = new Color(140, 17, 17);

         try {
            if (null == classLY.field4441) {
               classLY.field4441 = var0.field6217.createImage(304, 34);
            }

            Graphics var7 = classLY.field4441.getGraphics();
            var7.setColor(var6);
            var7.drawRect(0, 0, 303, 1687275269);
            var7.fillRect(2, 2, var1 * 3, 30);
            var7.setColor(Color.black);
            var7.drawRect(1, 1, 1777835743, -998577418);
            var7.fillRect(2 + 3 * var1, 2, 300 - var1 * 3, 30);
            var7.setFont(classAB.field123);
            var7.setColor(Color.white);
            var7.drawString(var2, (-1114244648 - classAF.field145.stringWidth(var2)) / 2, 22);
            var5.drawImage(classLY.field4441, classSA.field5895 * -699590131 / 2 - 152, IntProjection.field1936 * 8379747 / 2 + (var4 ? 50 : 1729743090), null);
         } catch (Exception var10) {
            int var8 = classSA.field5895 * 773246731 / 2 - 152;
            int var9 = -775651097 * IntProjection.field1936 / 2 - 18;
            var5.setColor(var6);
            var5.drawRect(var8, var9, 645193539, 33);
            var5.fillRect(var8 + 2, 2 + var9, 3 * var1, 30);
            var5.setColor(Color.black);
            var5.drawRect(1 + var8, var9 + 1, 301, 31);
            var5.fillRect(var8 + 2 + var1 * 3, 2 + var9, 300 - 3 * var1, 30);
            var5.setFont(classAB.field123);
            var5.setColor(Color.white);
            var5.drawString(var2, var8 + (304 - classAF.field145.stringWidth(var2)) / 2, var9 + 22);
         }
      } catch (Exception var11) {
         var0.field6217.repaint();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("agd")
   protected static final boolean method11002() {
      return field6196.method10865(-1485013847);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aio")
   protected final boolean method11069() {
      String var1 = this.method11157().getHost().toLowerCase();
      if (var1.equals("jagex.com") || var1.endsWith(".jagex.com")) {
         return true;
      } else if (var1.equals("runescape.com") || var1.endsWith(".runescape.com")) {
         return true;
      } else if (var1.endsWith("127.0.0.1")) {
         return true;
      } else {
         while (!var1.isEmpty() && var1.charAt(var1.length() - 1) >= '0' && var1.charAt(var1.length() - 1) <= '9') {
            var1 = var1.substring(0, var1.length() - 1);
         }

         if (var1.endsWith("192.168.1.")) {
            return true;
         } else {
            method11129(this, "invalidhost", 1054576696);
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aiz")
   void method11076() {
      long var1 = classDD.method2988(16711680);
      long var3 = field6193[75872381 * classMD.field4485];
      field6193[75872381 * classMD.field4485] = var1;
      classMD.field4485 = (1 + 75872381 * classMD.field4485 & 31) * -911507243;
      if (var3 != 0L && var1 > var3) {
      }

      synchronized (this) {
         classDD.field1403 = field6188;
      }

      this.vmethod231((byte)97);
   }

   @ObfuscatedSignature(descriptor = "(Ltq;I)V")
   @ObfuscatedName("tt")
   public static void method11013(GameEngine var0, int var1) {
      if (var0 == null) {
         var0.method11014(var1);
      }

      try {
         var0.field6220 = var0.getToolkit().getSystemClipboard();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.aft(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aim")
   void method11077() {
      long var1 = classDD.method2988(16711680);
      long var3 = field6193[75872381 * classMD.field4485];
      field6193[75872381 * classMD.field4485] = var1;
      classMD.field4485 = (1 + 75872381 * classMD.field4485 & 1941023212) * -212431108;
      if (var3 != 0L && var1 > var3) {
      }

      synchronized (this) {
         classDD.field1403 = field6188;
      }

      this.vmethod231((byte)115);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("act")
   protected abstract void vmethod236(boolean var1);

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajk")
   public final void method11108(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("yv")
   public void method11168(boolean var1) {
      this.field6207 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("nk")
   public boolean method11169() {
      return this.field6207;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aic")
   protected final void method11080() {
      this.field6205 = true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ajv")
   void method11084() {
      Container var1 = this.method11131(1019202001);
      long var2 = classDD.method2988(16711680);
      long var4 = field6195[-1804509917 * classFW.field2415];
      field6195[classFW.field2415 * -1804509917] = var2;
      classFW.field2415 = (1 + classFW.field2415 * -1804509917 & 31) * 1510849163;
      if (0L != var4 && var2 > var4) {
         int var6 = (int)(var2 - var4);
         field6191 = ((var6 >> 1) + 32000) / var6 * -393098901;
      }

      if ((field6194 += 2069175365) * 801347213 - 1 > 50) {
         field6194 -= 379553146;
         this.field6205 = true;
         this.field6217.setSize(classSA.field5895 * 773246731, IntProjection.field1936 * 8379747);
         this.field6217.setVisible(true);
         if (var1 == this.field6219) {
            Insets var7 = this.field6219.getInsets();
            this.field6217.setLocation(var7.left + 1451619303 * this.field6203, var7.top + -673776453 * this.field6204);
         } else {
            this.field6217.setLocation(this.field6203 * 1451619303, this.field6204 * -673776453);
         }
      }

      if (this.field6207) {
         this.method11158((byte)-6);
      }

      this.method11087(1585812784);
      this.vmethod237(this.field6205, 1698349365);
      if (this.field6205) {
         this.method11051((byte)-3);
      }

      this.field6205 = false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ajh")
   void method11085() {
      Container var1 = this.method11131(-1359522947);
      long var2 = classDD.method2988(16711680);
      long var4 = field6195[676723006 * classFW.field2415];
      field6195[classFW.field2415 * -1804509917] = var2;
      classFW.field2415 = (1 + classFW.field2415 * -1804509917 & 1864477113) * 1510849163;
      if (0L != var4 && var2 > var4) {
         int var6 = (int)(var2 - var4);
         field6191 = ((var6 >> 1) + 32000) / var6 * -1784476173;
      }

      if ((field6194 += -961246446) * 801347213 - 1 > 50) {
         field6194 -= 379553146;
         this.field6205 = true;
         this.field6217.setSize(classSA.field5895 * -186829873, IntProjection.field1936 * 8379747);
         this.field6217.setVisible(true);
         if (var1 == this.field6219) {
            Insets var7 = this.field6219.getInsets();
            this.field6217.setLocation(var7.left + 605956500 * this.field6203, var7.top + -673776453 * this.field6204);
         } else {
            this.field6217.setLocation(this.field6203 * 1688986815, this.field6204 * -94523477);
         }
      }

      if (this.field6207) {
         this.method11158((byte)20);
      }

      this.method11087(732119250);
      this.vmethod237(this.field6205, 873375846);
      if (this.field6205) {
         this.method11051((byte)-4);
      }

      this.field6205 = false;
   }

   public void initialize() {
      this.setSize(Constants.GAME_FIXED_SIZE);
      this.init();
      this.start();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aiu")
   final void method11089() {
      this.field6206 = true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aii")
   final void method11090() {
      this.field6206 = true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ajp")
   public final void method11093() {
      if (this == field6187 && !field6190) {
         field6189 = 0L;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("qi")
   public static void method11030(GameEngine var0) {
      if (var0 == null) {
         var0.method11155();
      } else {
         classQY.method9920(-1517706091);
         field6196.method10869(var0.field6217, -126373605);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("aij")
   protected final void method11058(int var1, int var2, int var3, int var4) {
      try {
         if (null != field6187) {
            field6198 += 354831441;
            if (221150385 * field6198 >= 3) {
               method11129(this, "alreadyloaded", -417596165);
               return;
            }

            this.method11149().vmethod560(this.method11157(), "_self");
            return;
         }

         field6187 = this;
         classSA.field5895 = var1 * -2089155421;
         IntProjection.field1936 = 841518155 * var2;
         classAAL.field57 = var3 * -154973313;
         classAAF.field46 = -609160337 * var4;
         classAAF.field49 = this.method11147();
         if (classWW.field6784 == null) {
            classWW.field6784 = new classMB();
         }

         classMB.method7559(classWW.field6784, this, 1, 612936669);
      } catch (Exception var6) {
         classAAU.method270(null, var6, (byte)13);
         method11129(this, "crash", 1668276056);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Ljava/lang/String;)V")
   @ObfuscatedName("yu")
   public static void method11130(GameEngine var0, String var1) {
      if (!var0.field6202) {
         var0.field6202 = true;
         System.out.println("error_game_" + var1);

         try {
            var0.method11149().vmethod560(new URL(var0.method11147(), "error_game_" + var1 + ".ws"), "_self");
         } catch (Exception var3) {
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aiy")
   protected final void method11081() {
      this.field6205 = true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ajt")
   public final void method11098() {
      if (field6187 == this && !field6190) {
         field6189 = classDD.method2988(16711680) * -7013317878618397317L;
         Friend.method10197(5000L);
         this.method11092((byte)-98);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("agp")
   protected static final void method11004() {
      classTW.field6248.vmethod530((byte)5);

      for (int var0 = 0; var0 < 32; var0++) {
         field6195[var0] = 0L;
      }

      for (int var1 = 0; var1 < 32; var1++) {
         field6193[var1] = 0L;
      }

      ActorSpotAnim.field5962 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Ltl;II)V")
   @ObfuscatedName("afa")
   protected void method11040(classTL var1, int var2, int var3) {
      try {
         field6196.method10859(var1, var2, 806192515);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "tq.afa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("acv")
   protected abstract void vmethod229();

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajs")
   public final void method11110(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajf")
   public final void method11111(WindowEvent var1) {
      this.destroy();
   }

   @ObfuscatedSignature(descriptor = "()Lrl0;")
   @ObfuscatedName("mj")
   public rl0 method11149() {
      return this;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("abe")
   public abstract void vmethod209();

   @ObfuscatedSignature(descriptor = "()Lmf;")
   @ObfuscatedName("ahd")
   protected classMF method11012() {
      if (this.field6224 == null) {
         this.field6224 = new classTC();
         this.field6224.method10781(this.field6217, -347735156);
      }

      return this.field6224;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("acl")
   protected abstract void vmethod235();

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajx")
   public final void method11115(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajr")
   public final void method11116(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("acp")
   protected abstract void vmethod238(boolean var1);

   @ObfuscatedSignature(descriptor = "(Ltq;I)V")
   @ObfuscatedName("qk")
   public static void method11031(GameEngine var0, int var1) {
      if (var0 == null) {
         var0.method11091(var1);
      } else {
         try {
            classQY.method9920(-1472726644);
            field6196.method10869(var0.field6217, 1110326982);
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "tq.afn(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("xn")
   public void method11142() {
      if (classOE.field4841.isStretchedEnabled()) {
         classOE.field4841.invalidateStretching(false);
         if (classOE.field4841.isResized()) {
            Dimension var1 = classOE.field4841.getRealDimensions();
            this.method11172(var1.width);
            this.method11170(var1.height);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("lk")
   public static void method11037(GameEngine var0) {
      if (var0 == null) {
         var0.method11038();
      } else {
         field6196.method10875((byte)-73);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("md")
   public void method11170(int var1) {
      this.field6215 = var1 * 1815127515;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("akd")
   public final void method11117(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(I)Luq;")
   @ObfuscatedName("agw")
   classUQ method11137(int var1) {
      try {
         Container var2 = this.method11131(-302065058);
         int var3 = Math.max(var2.getWidth(), 237759111 * this.field6213);
         int var4 = Math.max(var2.getHeight(), this.field6216 * -450704939);
         if (this.field6219 != null) {
            if (var1 >= 1445006785) {
               throw new IllegalStateException();
            }

            Insets var5 = this.field6219.getInsets();
            var3 -= var5.right + var5.left;
            var4 -= var5.top + var5.bottom;
         }

         return new classUQ(var3, var4);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "tq.agw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("aky")
   public final void method11118(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ako")
   public final void method11119(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Graphics;)V")
   @ObfuscatedName("ow")
   public final void method11145(Graphics var1) {
      if (!this.field6200) {
         this.field6200 = true;
         var1.clearRect(0, 0, this.getWidth(), this.getHeight());
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("ng")
   public static void method11094(GameEngine var0) {
      if (var0 == null) {
         var0.method11151();
      }

      if (field6187 == var0 && !field6190) {
         field6189 = (classDD.method2988(16711680) + 4000L) * -7013317878618397317L;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("jt")
   public void method11171() {
      this.method11083(598114799);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("nx")
   public static void method11095(GameEngine var0) {
      if (var0 == null) {
         var0.method11036();
      }

      if (field6187 == var0 && !field6190) {
         field6189 = (classDD.method2988(16711680) + 4000L) * -7013317878618397317L;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("agl")
   final synchronized void method11063(int var1) {
      this.method11151();

      try {
         Container var2 = this.method11131(-1603288198);
         if (null != this.field6217) {
            if (var1 <= 1278010550) {
               throw new IllegalStateException();
            }

            this.field6217.removeFocusListener(this);
            var2.remove(this.field6217);
         }

         classSA.field5895 = Math.max(var2.getWidth(), this.field6213 * 237759111) * -2089155421;
         client.method2112(-1);
         IntProjection.field1936 = Math.max(var2.getHeight(), this.field6216 * -450704939) * 841518155;
         client.method2225(-1);
         if (null != this.field6219) {
            if (var1 <= 1278010550) {
               throw new IllegalStateException();
            }

            Insets var3 = this.field6219.getInsets();
            classSA.field5895 = classSA.field5895 - (var3.left + var3.right) * -2089155421;
            client.method2112(-1);
            IntProjection.field1936 = IntProjection.field1936 - 841518155 * (var3.top + var3.bottom);
            client.method2225(-1);
         }

         this.field6217 = new classTD(this);
         var2.setBackground(Color.BLACK);
         var2.setLayout(null);
         var2.add(this.field6217);
         this.field6217.setSize(classSA.field5895 * 773246731, IntProjection.field1936 * 8379747);
         this.field6217.setVisible(true);
         this.field6217.setBackground(Color.BLACK);
         if (this.field6219 == var2) {
            if (var1 <= 1278010550) {
               throw new IllegalStateException();
            }

            Insets var5 = this.field6219.getInsets();
            this.field6217.setLocation(var5.left + 1451619303 * this.field6203, this.field6204 * -673776453 + var5.top);
         } else {
            this.field6217.setLocation(1451619303 * this.field6203, this.field6204 * -673776453);
         }

         label60: {
            this.field6217.addFocusListener(this);
            this.field6217.requestFocus();
            this.field6205 = true;
            if (null != classWO.field6761 && classSA.field5895 * 773246731 == classWO.field6761.field6966 * -1238133717) {
               if (var1 <= 1278010550) {
                  throw new IllegalStateException();
               }

               if (IntProjection.field1936 * 8379747 == classWO.field6761.field6965 * 1855103015) {
                  if (var1 <= 1278010550) {
                     throw new IllegalStateException();
                  }

                  MainBufferProvider.method10850((MainBufferProvider)classWO.field6761, this.field6217, (byte)-69);
                  classWO.field6761.vmethod578(0, 0, (byte)123);
                  break label60;
               }
            }

            classWO.field6761 = new MainBufferProvider(773246731 * classSA.field5895, 8379747 * IntProjection.field1936, this.field6217, this.field6201);
         }

         this.field6207 = false;
         this.field6208 = classDD.method2988(16711680) * -1528671439279181277L;
         this.method11150();
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "tq.agl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("acm")
   protected abstract void vmethod227();

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;ZZ)V")
   @ObfuscatedName("akw")
   protected final void method11124(int var1, String var2, boolean var3, boolean var4) {
      try {
         Graphics var5 = this.field6217.getGraphics();
         if (null == classAB.field123) {
            classAB.field123 = new Font("Helvetica", 1, 13);
            classAF.field145 = this.field6217.getFontMetrics(classAB.field123);
         }

         if (var3 && !var4) {
            var5.setColor(Color.black);
            var5.fillRect(0, 0, classSA.field5895 * 773246731, 8379747 * IntProjection.field1936);
         }

         Color var6 = new Color(140, 17, 17);

         try {
            if (null == classLY.field4441) {
               classLY.field4441 = this.field6217.createImage(-144180440, 34);
            }

            Graphics var7 = classLY.field4441.getGraphics();
            var7.setColor(var6);
            var7.drawRect(0, 0, -380954787, -2116906192);
            var7.fillRect(2, 2, var1 * 3, 30);
            var7.setColor(Color.black);
            var7.drawRect(1, 1, 917921617, -1275408469);
            var7.fillRect(2 + 3 * var1, 2, -910325526 - var1 * 3, 30);
            var7.setFont(classAB.field123);
            var7.setColor(Color.white);
            var7.drawString(var2, (304 - classAF.field145.stringWidth(var2)) / 2, 22);
            var5.drawImage(
               classLY.field4441, classSA.field5895 * -15963207 / 2 - 1764028865, IntProjection.field1936 * 8379747 / 2 + (var4 ? 1124945180 : -18), null
            );
         } catch (Exception var10) {
            int var8 = classSA.field5895 * -142546908 / 2 - 152;
            int var9 = -1020043590 * IntProjection.field1936 / 2 - 18;
            var5.setColor(var6);
            var5.drawRect(var8, var9, 1136214020, 33);
            var5.fillRect(var8 + 2, 2 + var9, 3 * var1, 30);
            var5.setColor(Color.black);
            var5.drawRect(1 + var8, var9 + 1, 301, 31);
            var5.fillRect(var8 + 2 + var1 * 3, 2 + var9, 300 - 3 * var1, 30);
            var5.setFont(classAB.field123);
            var5.setColor(Color.white);
            var5.drawString(var2, var8 + (304 - classAF.field145.stringWidth(var2)) / 2, var9 + 22);
         }
      } catch (Exception var11) {
         this.field6217.repaint();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("mf")
   public static void method11099(GameEngine var0) {
      if (var0 == null) {
         var0.method11169();
      }

      if (field6187 == var0 && !field6190) {
         field6189 = classDD.method2988(16711680) * -7013317878618397317L;
         Friend.method10197(5000L);
         var0.method11092((byte)-23);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("akr")
   public final void method11120(WindowEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("akn")
   protected final void method11128() {
      classLY.field4441 = null;
      classAB.field123 = null;
      classAF.field145 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bi")
   public void method11172(int var1) {
      this.field6214 = var1 * -63355361;
   }

   @ObfuscatedSignature(descriptor = "()Luq;")
   @ObfuscatedName("akf")
   classUQ method11138() {
      Container var1 = this.method11131(-1937732386);
      int var2 = Math.max(var1.getWidth(), 237759111 * this.field6213);
      int var3 = Math.max(var1.getHeight(), this.field6216 * -450704939);
      if (this.field6219 != null) {
         Insets var4 = this.field6219.getInsets();
         var2 -= var4.right + var4.left;
         var3 -= var4.top + var4.bottom;
      }

      return new classUQ(var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ait")
   protected final void method11059(int var1, int var2, int var3, int var4) {
      try {
         if (null != field6187) {
            field6198 += 682260341;
            if (221150385 * field6198 >= 3) {
               method11129(this, "alreadyloaded", 1679138725);
               return;
            }

            this.method11149().vmethod560(this.method11157(), "_self");
            return;
         }

         field6187 = this;
         classSA.field5895 = var1 * -1571951132;
         IntProjection.field1936 = -329626580 * var2;
         classAAL.field57 = var3 * -154973313;
         classAAF.field46 = -1564700842 * var4;
         classAAF.field49 = this.method11147();
         if (classWW.field6784 == null) {
            classWW.field6784 = new classMB();
         }

         classMB.method7559(classWW.field6784, this, 1, -455791678);
      } catch (Exception var6) {
         classAAU.method270(null, var6, (byte)88);
         method11129(this, "crash", 254297309);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/net/URL;)V")
   @ObfuscatedName("cl")
   @Override
   public void vmethod561(URL var1) {
      String var2 = var1.getPath();
      if (var2.startsWith("/error_game_")) {
         this.field6223.onError(var2.replace("/", "").replace(".ws", ""));
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/net/URL;Ljava/lang/String;)V")
   @ObfuscatedName("gs")
   @Override
   public void vmethod560(URL var1, String var2) {
      this.vmethod561(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aft")
   protected void method11014(int var1) {
      try {
         this.field6220 = this.getToolkit().getSystemClipboard();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.aft(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("hf")
   public static void method11078(GameEngine var0) {
      if (var0 == null) {
         var0.method11026();
      } else {
         long var1 = classDD.method2988(16711680);
         long var3 = field6193[75872381 * classMD.field4485];
         field6193[-1459854889 * classMD.field4485] = var1;
         classMD.field4485 = (1 + 75872381 * classMD.field4485 & 31) * -2008577211;
         if (var3 != 0L && var1 > var3) {
         }

         synchronized (var0) {
            classDD.field1403 = field6188;
         }

         var0.vmethod231((byte)105);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/awt/datatransfer/Clipboard;")
   @ObfuscatedName("afv")
   public Clipboard method11025(byte var1) {
      try {
         return this.field6220;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.afv(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/net/URL;")
   @ObfuscatedName("ul")
   public URL method11147() {
      return this.field6223.getCodeBase();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("uk")
   public static void method11054(GameEngine var0) {
      field6196.method10872(var0.field6217, (byte)-92);
      Canvas var1 = var0.field6217;
      var1.removeMouseListener(classTZ.field6257);
      var1.removeMouseMotionListener(classTZ.field6257);
      var1.removeFocusListener(classTZ.field6257);
      classTZ.field6254 = 0;
      if (null != var0.field6224) {
         var0.field6224.method10785(var0.field6217, 1886339831);
      }

      var0.method11063(1596975679);
      field6196.method10869(var0.field6217, 1509592828);
      Canvas var2 = var0.field6217;
      var2.addMouseListener(classTZ.field6257);
      var2.addMouseMotionListener(classTZ.field6257);
      var2.addFocusListener(classTZ.field6257);
      if (var0.field6224 != null) {
         var0.field6224.method10781(var0.field6217, -1191183928);
      }

      var0.method11088(1539154266);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("afm")
   protected final void method11045(int var1) {
      try {
         Canvas var2 = this.field6217;
         var2.addMouseListener(classTZ.field6257);
         var2.addMouseMotionListener(classTZ.field6257);
         var2.addFocusListener(classTZ.field6257);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tq.afm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("agm")
   final void method11173(int var1) {
      this.field6213 = var1 * -63355361;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("afn")
   protected final void method11091(int var1) {
      try {
         this.field6202 = true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tq.ags(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aha")
   protected final void method11038() {
      field6196.method10875((byte)-73);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("agh")
   protected void method11020(String var1, int var2) {
      try {
         this.field6220.setContents(new StringSelection(var1), null);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tq.afw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("ab")
   public String method11141(String var1) {
      return this.field6223.getParameter(var1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ajd")
   void method11174() {
      this.vmethod207(1624818309);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("ajw")
   public final void method11112(WindowEvent var1) {
      this.method11097();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("afw")
   protected void method11021(String var1, int var2) {
      try {
         this.field6220.setContents(new StringSelection(var1), null);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tq.afw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ago")
   protected final boolean method11070(byte var1) {
      try {
         String var2 = this.method11157().getHost().toLowerCase();
         if (!var2.equals("jagex.com")) {
            if (var1 >= 2) {
               throw new IllegalStateException();
            }

            if (!var2.endsWith(".jagex.com")) {
               if (!var2.equals("runescape.com")) {
                  if (var1 >= 2) {
                     throw new IllegalStateException();
                  }

                  if (!var2.endsWith(".runescape.com")) {
                     if (var2.endsWith("127.0.0.1")) {
                        if (var1 >= 2) {
                           throw new IllegalStateException();
                        }

                        return true;
                     }

                     while (!var2.isEmpty() && var2.charAt(var2.length() - 1) >= '0') {
                        if (var1 >= 2) {
                           throw new IllegalStateException();
                        }

                        if (var2.charAt(var2.length() - 1) > '9') {
                           break;
                        }

                        if (var1 >= 2) {
                           throw new IllegalStateException();
                        }

                        var2 = var2.substring(0, var2.length() - 1);
                     }

                     if (var2.endsWith("192.168.1.")) {
                        if (var1 >= 2) {
                           throw new IllegalStateException();
                        }

                        return true;
                     }

                     method11129(this, "invalidhost", 1608342709);
                     return false;
                  }

                  if (var1 >= 2) {
                     throw new IllegalStateException();
                  }
               }

               return true;
            }

            if (var1 >= 2) {
               throw new IllegalStateException();
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tq.ago(" + ')');
      }
   }
}
