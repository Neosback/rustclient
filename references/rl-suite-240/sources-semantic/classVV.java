import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vv")
public class classVV extends classLY {
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("am")
   Widget field6615;
   @ObfuscatedSignature(descriptor = "Lyi;")
   @ObfuscatedName("au")
   public HashTable field6616 = new HashTable(512);
   @ObfuscatedSignature(descriptor = "Lyi;")
   @ObfuscatedName("ad")
   public HashTable field6611;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("ai")
   public Widget field6618;
   @ObfuscatedName("at")
   public int field6613;
   @ObfuscatedSignature(descriptor = "Lvw;")
   @ObfuscatedName("ah")
   final classVW field6612;
   @ObfuscatedSignature(descriptor = "Lvc;")
   @ObfuscatedName("bn")
   final classVC field6609;
   @ObfuscatedName("ap")
   public int field6610 = -1130371137;
   @ObfuscatedName("an")
   public int field6614;
   @ObfuscatedName("bx")
   public boolean field6617;

   @ObfuscatedSignature(descriptor = "(Llw;I)Ljava/lang/String;")
   @ObfuscatedName("bd")
   public String method12198(Widget var1, int var2) {
      int var4 = method12214(this, var1, (short)255);
      boolean var3 = (var4 >> var2 & 1) != 0;
      if (!var3 && null == var1.field4384) {
         return null;
      } else {
         return null != var1.field4335 && var1.field4335.length > var2 && var1.field4335[var2] != null && !var1.field4335[var2].trim().isEmpty()
            ? var1.field4335[var2]
            : null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;I)Lvc;")
   @ObfuscatedName("nq")
   public static classVC method12187(classVV var0, int var1) {
      if (var0 == null) {
         return var0.method12191(var1);
      } else {
         try {
            return var0.field6609;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "vv.ar(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("cc")
   public Widget method12227() {
      return this.field6612.method12282(-974097448);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)Llw;")
   @ObfuscatedName("oy")
   public static Widget method12217(classVV var0, Widget var1) {
      if (var0 == null) {
         var0.method12188();
      }

      int var2 = classCD.method1408(method12246(var0, var1, (byte)0), (short)256);
      if (var2 == 0) {
         return null;
      } else {
         for (int var3 = 0; var3 < var2; var3++) {
            var1 = var0.method7421(var1.field4340 * 458555883, (byte)51);
            if (null == var1) {
               return null;
            }
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Z")
   @ObfuscatedName("aw")
   public boolean method12196(Widget var1, byte var2) {
      try {
         return var1.field4287;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vv.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;B)Llw;")
   @ObfuscatedName("ip")
   public static Widget method12228(classVV var0, byte var1) {
      if (var0 == null) {
         var0.method12229(var1);
      }

      try {
         return var0.field6612.method12282(-975656414);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vv.bp(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)Z")
   @ObfuscatedName("jw")
   public static boolean method12192(classVV var0, Widget var1) {
      if (var0 == null) {
         var0.method12195(var1);
      }

      return var1.method7259(-947902929)
         || method12246(var0, var1, (byte)0) != 0
         || method12214(var0, var1, (short)255) != 0
         || var1 == var0.field6612.method12284(-1484005868);
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("bt")
   public boolean method12234(byte var1) {
      try {
         boolean var10000;
         if (null == this.field6615) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vv.bt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Ljava/lang/String;")
   @ObfuscatedName("at")
   public String method12201(Widget var1, byte var2) {
      try {
         if (classCK.method1569(method12246(this, var1, (byte)0), -2129730757) == 0) {
            if (var2 >= 30) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            if (var1.field4343 != null) {
               if (var2 >= 30) {
                  throw new IllegalStateException();
               }

               if (!var1.field4343.trim().isEmpty()) {
                  return var1.field4343;
               }

               if (var2 >= 30) {
                  throw new IllegalStateException();
               }
            }

            return null;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vv.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Llw;")
   @ObfuscatedName("cd")
   public Widget method12218(Widget var1) {
      int var2 = classCD.method1408(method12246(this, var1, (byte)0), (short)256);
      if (var2 == 0) {
         return null;
      } else {
         for (int var3 = 0; var3 < var2; var3++) {
            var1 = this.method7421(var1.field4340 * -1502647899, (byte)114);
            if (null == var1) {
               return null;
            }
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Llw;")
   @ObfuscatedName("bn")
   public Widget method12219(Widget var1, byte var2) {
      try {
         int var3 = classCD.method1408(method12246(this, var1, (byte)0), (short)256);
         if (var3 == 0) {
            if (var2 >= 1) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            for (int var4 = 0; var4 < var3; var4++) {
               if (var2 >= 1) {
                  throw new IllegalStateException();
               }

               var1 = this.method7421(var1.field4340 * -1502647899, (byte)43);
               if (null == var1) {
                  if (var2 >= 1) {
                     throw new IllegalStateException();
                  }

                  return null;
               }
            }

            return var1;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "vv.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("br")
   public boolean method12230(int var1) {
      try {
         return this.field6612.method12278(2030554553);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vv.br(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;)Z")
   @ObfuscatedName("rw")
   public static boolean method12235(classVV var0) {
      return null == var0.field6615;
   }

   @ObfuscatedSignature(descriptor = "(Llw;III)Z")
   @ObfuscatedName("bm")
   public boolean method12233(Widget var1, int var2, int var3, int var4) {
      try {
         if (this.field6612.method12278(1398626350)) {
            return false;
         } else {
            if (null != var1) {
               if (var4 <= -213201407) {
                  throw new IllegalStateException();
               }

               if (method12224(this, var1, (byte)-16) != null) {
                  this.field6612.method12250(var1, method12224(this, var1, (byte)-79), var2, var3, (byte)-60);
                  return true;
               }

               if (var4 <= -213201407) {
                  throw new IllegalStateException();
               }
            }

            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "vv.bm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)I")
   @ObfuscatedName("kr")
   public int method12248(Widget var1) {
      return method12246(this, var1, (byte)0);
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)V")
   @ObfuscatedName("bg")
   public void method12238(Widget var1, int var2) {
      try {
         this.field6615 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vv.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("bj")
   public void method12240(byte var1) {
      try {
         this.field6615 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vv.bj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Z")
   @ObfuscatedName("ak")
   public boolean method12241(Widget var1, byte var2) {
      try {
         boolean var10000;
         if (this.field6615 == var1) {
            if (var2 >= 100) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vv.bk(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   public void method12183() {
      this.field6610 = -1130371137;
      this.field6611 = new HashTable(8);
      this.field6615 = null;
      this.field6612.method12255(2129513656);
      this.field6612.method12261(-624973020);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   public void method12184() {
      this.field6610 = -1130371137;
      this.field6611 = new HashTable(8);
      this.field6615 = null;
      this.field6612.method12255(2137154234);
      this.field6612.method12261(-624973020);
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("ci")
   public void method12239(Widget var1) {
      this.field6615 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lvc;")
   @ObfuscatedName("bi")
   public classVC method12188() {
      return this.field6609;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("be")
   public boolean method12193(Widget var1) {
      return var1.method7259(-947902929)
         || method12246(this, var1, (byte)0) != 0
         || method12214(this, var1, (short)255) != 0
         || var1 == this.field6612.method12284(-412972097);
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("dc")
   public boolean method12242(Widget var1) {
      return this.field6615 == var1;
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Z")
   @ObfuscatedName("bk")
   public boolean method12194(Widget var1, byte var2) {
      try {
         if (!var1.method7259(-947902929)) {
            if (var2 == 6) {
               throw new IllegalStateException();
            }

            if (method12246(this, var1, (byte)0) == 0) {
               if (var2 == 6) {
                  throw new IllegalStateException();
               }

               if (method12214(this, var1, (short)255) == 0) {
                  if (var2 == 6) {
                     throw new IllegalStateException();
                  }

                  if (var1 != this.field6612.method12284(-1276148624)) {
                     return false;
                  }

                  if (var2 == 6) {
                     throw new IllegalStateException();
                  }
               }
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vv.aw(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)I")
   @ObfuscatedName("bv")
   public static int method12208(classVV var0, Widget var1) {
      if (var0 == null) {
         var0.method12212(var1);
      }

      WidgetConfigNode var2 = (WidgetConfigNode)HashTable.method13432(var0.field6616, 464145912 * var1.field4327);
      WidgetConfigNode var3 = classFW.method5089(var2, var1.field4258 * 63131743, 397306859);
      return var3 != null ? var3.method6830((byte)63) : var1.field4318 * 1887401626;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Ljava/lang/String;")
   @ObfuscatedName("bv")
   public String method12202(Widget var1) {
      if (classCK.method1569(method12246(this, var1, (byte)0), -2092177232) == 0) {
         return null;
      } else {
         return var1.field4343 != null && !var1.field4343.trim().isEmpty() ? var1.field4343 : null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;II)V")
   @ObfuscatedName("ye")
   public static void method12204(classVV var0, int var1, int var2) {
      if (var0 == null) {
         var0.method12207(var1, var1);
      }

      try {
         for (WidgetConfigNode var3 = (WidgetConfigNode)var0.field6616.method13423(); var3 != null; var3 = (WidgetConfigNode)var0.field6616.method13427()) {
            if ((var3.field6550 >> 16 & 65535L) == var1) {
               var3.vmethod398();
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "vv.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bh")
   public void method12205(int var1) {
      for (WidgetConfigNode var2 = (WidgetConfigNode)this.field6616.method13423(); var2 != null; var2 = (WidgetConfigNode)this.field6616.method13427()) {
         if ((var2.field6550 >> 16 & 65535L) == var1) {
            var2.vmethod398();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)I")
   @ObfuscatedName("qk")
   public static int method12209(classVV var0, Widget var1) {
      WidgetConfigNode var2 = (WidgetConfigNode)HashTable.method13432(var0.field6616, -1163656551 * var1.field4327);
      WidgetConfigNode var3 = classFW.method5089(var2, var1.field4258 * 1789871699, -322460111);
      return var3 != null ? var3.method6830((byte)14) : var1.field4318 * 1595830249;
   }

   public classVV(IndexDataBase var1, IndexDataBase var2, IndexDataBase var3, IndexDataBase var4, IndexDataBase var5) {
      super(var1, var2, var3, var4, var5);
      this.field6611 = new HashTable(8);
      this.field6618 = null;
      this.field6613 = 0;
      this.field6614 = -1264941915;
      this.field6615 = null;
      this.field6612 = new classVW();
      this.field6609 = new classVC();
      this.field6617 = false;
   }

   @ObfuscatedSignature(descriptor = "()Lvc;")
   @ObfuscatedName("bb")
   public classVC method12189() {
      return this.field6609;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)Llw;")
   @ObfuscatedName("ze")
   public static Widget method12220(classVV var0, Widget var1) {
      if (var0 == null) {
         var0.method12239(var1);
      }

      int var2 = classCD.method1408(method12246(var0, var1, (byte)0), (short)256);
      if (var2 == 0) {
         return null;
      } else {
         for (int var3 = 0; var3 < var2; var3++) {
            var1 = var0.method7421(var1.field4340 * 285697408, (byte)121);
            if (null == var1) {
               return null;
            }
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Llw;")
   @ObfuscatedName("cy")
   Widget method12222(Widget var1) {
      Widget var2 = this.method12219(var1, (byte)-105);
      if (var2 == null) {
         var2 = var1.field4309;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)I")
   @ObfuscatedName("cr")
   public int method12210(Widget var1) {
      WidgetConfigNode var2 = (WidgetConfigNode)HashTable.method13432(this.field6616, -1163656551 * var1.field4327);
      WidgetConfigNode var3 = classFW.method5089(var2, var1.field4258 * -1078646016, -569178960);
      return var3 != null ? var3.method6830((byte)42) : var1.field4318 * -1256877351;
   }

   @ObfuscatedSignature(descriptor = "()Lvc;")
   @ObfuscatedName("ba")
   public classVC method12190() {
      return this.field6609;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)I")
   @ObfuscatedName("cp")
   public int method12213(Widget var1) {
      WidgetConfigNode var2 = (WidgetConfigNode)HashTable.method13432(this.field6616, var1.field4327 * -2026377804);
      WidgetConfigNode var3 = classFW.method5089(var2, var1.field4258 * 63131743, 1687174860);
      if (var3 != null) {
         return var3.method6834(120041229);
      } else {
         int var5 = var1.field4318 * 1595830249;
         return var5 >> 1 & 1006152527;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;Llw;S)I")
   @ObfuscatedName("cm")
   public static int method12214(classVV var0, Widget var1, short var2) {
      if (var0 == null) {
         return var0.method12216(var1, var2);
      } else {
         try {
            WidgetConfigNode var3 = (WidgetConfigNode)HashTable.method13432(var0.field6616, var1.field4327 * -1163656551);
            WidgetConfigNode var4 = classFW.method5089(var3, var1.field4258 * 63131743, -414664663);
            if (var4 != null) {
               if (var2 != 255) {
                  throw new IllegalStateException();
               } else {
                  return var4.method6834(120041229);
               }
            } else {
               int var6 = var1.field4318 * 1595830249;
               return var6 >> 1 & 1023;
            }
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "vv.ah(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)I")
   @ObfuscatedName("cq")
   public int method12215(Widget var1) {
      WidgetConfigNode var2 = (WidgetConfigNode)HashTable.method13432(this.field6616, var1.field4327 * -1163656551);
      WidgetConfigNode var3 = classFW.method5089(var2, var1.field4258 * 63131743, -633324221);
      if (var3 != null) {
         return var3.method6834(120041229);
      } else {
         int var5 = var1.field4318 * 1595830249;
         return var5 >> 1 & 1023;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bz")
   public void method12206(int var1) {
      for (WidgetConfigNode var2 = (WidgetConfigNode)this.field6616.method13423(); var2 != null; var2 = (WidgetConfigNode)this.field6616.method13427()) {
         if ((var2.field6550 >> 16 & 65535L) == var1) {
            var2.vmethod398();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Llw;")
   @ObfuscatedName("cv")
   public Widget method12221(Widget var1) {
      int var2 = classCD.method1408(method12246(this, var1, (byte)0), (short)256);
      if (var2 == 0) {
         return null;
      } else {
         for (int var3 = 0; var3 < var2; var3++) {
            var1 = this.method7421(var1.field4340 * -1502647899, (byte)37);
            if (null == var1) {
               return null;
            }
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)I")
   @ObfuscatedName("cu")
   public int method12211(Widget var1) {
      WidgetConfigNode var2 = (WidgetConfigNode)HashTable.method13432(this.field6616, -1163656551 * var1.field4327);
      WidgetConfigNode var3 = classFW.method5089(var2, var1.field4258 * 63131743, 570299943);
      return var3 != null ? var3.method6830((byte)104) : var1.field4318 * 1595830249;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Llw;")
   @ObfuscatedName("ck")
   Widget method12223(Widget var1) {
      Widget var2 = this.method12219(var1, (byte)-120);
      if (var2 == null) {
         var2 = var1.field4309;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cf")
   public boolean method12231() {
      return this.field6612.method12278(1574293478);
   }

   @ObfuscatedSignature(descriptor = "(Lvv;Llw;B)I")
   @ObfuscatedName("mr")
   public static int method12246(classVV var0, Widget var1, byte var2) {
      if (var0 == null) {
         return var0.method12247(var1, var2);
      } else {
         Widget var5 = var1;
         classVV var4 = var0;
         boolean var6 = false;

         int var10000;
         try {
            WidgetConfigNode var7 = (WidgetConfigNode)HashTable.method13432(var4.field6616, -1163656551 * var5.field4327);
            WidgetConfigNode var8 = classFW.method5089(var7, var5.field4258 * 63131743, 2074995174);
            if (var8 != null) {
               if (var6) {
                  throw new IllegalStateException();
               }

               int var10 = var8.method6830((byte)37);
               var10000 = var10;
            } else {
               int var11 = var5.field4318 * 1595830249;
               var10000 = var11;
            }
         } catch (RuntimeException var9) {
            throw classEG.method3899(var9, "vv.am(" + 41);
         }

         int var3 = var10000;
         if (client.field1136) {
            var3 |= 2097152;
         }

         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cn")
   public boolean method12232() {
      return this.field6612.method12278(1500138385);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ch")
   public boolean method12236() {
      return null == this.field6615;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cg")
   public boolean method12237() {
      return null == this.field6615;
   }

   @ObfuscatedSignature(descriptor = "(Lvv;Llw;B)Llw;")
   @ObfuscatedName("zj")
   public static Widget method12224(classVV var0, Widget var1, byte var2) {
      if (var0 == null) {
         return var0.method12225(var1, var2);
      } else {
         try {
            Widget var3 = var0.method12219(var1, (byte)-76);
            if (var3 == null) {
               if (var2 >= 1) {
                  throw new IllegalStateException();
               }

               var3 = var1.field4309;
            }

            return var3;
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "vv.bx(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)Ljava/lang/String;")
   @ObfuscatedName("ss")
   public static String method12203(classVV var0, Widget var1) {
      if (var0 == null) {
         var0.method12245(var1);
      }

      if (classCK.method1569(method12246(var0, var1, (byte)0), -2115348171) == 0) {
         return null;
      } else {
         return var1.field4343 != null && !var1.field4343.trim().isEmpty() ? var1.field4343 : null;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lvw;")
   @ObfuscatedName("bc")
   public classVW method12226(byte var1) {
      try {
         return this.field6612;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vv.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("bq")
   public boolean method12197(Widget var1) {
      return var1.field4287;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("dd")
   public boolean method12243(Widget var1) {
      return this.field6615 == var1;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("dm")
   public boolean method12244(Widget var1) {
      return this.field6615 == var1;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("dx")
   public boolean method12245(Widget var1) {
      return this.field6615 == var1;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ai")
   public void method12185(byte var1) {
      try {
         this.field6610 = -1130371137;
         this.field6616 = new HashTable(8);
         this.field6615 = null;
         this.field6612.method12255(2130314559);
         this.field6612.method12255(-624973020);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vv.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lvc;")
   @ObfuscatedName("ar")
   public classVC method12191(int var1) {
      try {
         return this.field6609;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vv.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)I")
   @ObfuscatedName("am")
   public int method12247(Widget var1, byte var2) {
      Widget var5 = var1;
      classVV var4 = this;
      boolean var6 = false;

      int var10000;
      try {
         WidgetConfigNode var7 = (WidgetConfigNode)HashTable.method13432(var4.field6616, -1163656551 * var5.field4292);
         WidgetConfigNode var8 = classFW.method5089(var7, var5.field4247 * 63131743, 2074995174);
         if (var8 != null) {
            if (var6) {
               throw new IllegalStateException();
            }

            int var10 = var8.method6830((byte)37);
            var10000 = var10;
         } else {
            int var11 = var5.field4270 * 1595830249;
            var10000 = var11;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "vv.am(" + 41);
      }

      int var3 = var10000;
      if (client.field1144) {
         var3 |= 2097152;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(Llw;S)I")
   @ObfuscatedName("ah")
   public int method12216(Widget var1, short var2) {
      try {
         WidgetConfigNode var3 = (WidgetConfigNode)HashTable.method13432(this.field6616, var1.field4342 * -1163656551);
         WidgetConfigNode var4 = classFW.method5089(var3, var1.field4302 * 63131743, -414664663);
         if (var4 != null) {
            if (var2 != 255) {
               throw new IllegalStateException();
            } else {
               return var4.method6834(120041229);
            }
         } else {
            int var6 = var1.field4303 * 1595830249;
            return var6 >> 1 & 1023;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "vv.ah(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Llw;")
   @ObfuscatedName("bx")
   Widget method12225(Widget var1, byte var2) {
      try {
         Widget var3 = this.method12219(var1, (byte)-76);
         if (var3 == null) {
            if (var2 >= 1) {
               throw new IllegalStateException();
            }

            var3 = var1.field4309;
         }

         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "vv.bx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)I")
   @ObfuscatedName("cb")
   public int method12212(Widget var1) {
      WidgetConfigNode var2 = (WidgetConfigNode)HashTable.method13432(this.field6611, -1163656551 * var1.field4290);
      WidgetConfigNode var3 = classFW.method5089(var2, var1.field4298 * 63131743, 570299943);
      return var3 != null ? var3.method6830((byte)104) : var1.field4342 * 1595830249;
   }

   @ObfuscatedSignature(descriptor = "(Lvv;B)V")
   @ObfuscatedName("ed")
   public static void method12186(classVV var0, byte var1) {
      if (var0 == null) {
         var0.method12185(var1);
      } else {
         try {
            var0.field6610 = -1130371137;
            var0.field6611 = new HashTable(8);
            var0.field6615 = null;
            var0.field6612.method12255(2130314559);
            var0.field6612.method12261(-624973020);
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "vv.ai(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)Ljava/lang/String;")
   @ObfuscatedName("av")
   public String method12199(Widget var1, int var2, int var3) {
      try {
         int var5 = method12214(this, var1, (short)255);
         boolean var10000;
         if ((var5 >> var2 & 1) != 0) {
            if (var3 != -1760527185) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var4 = var10000;
         if (!var4 && null == var1.field4389) {
            if (var3 != -1760527185) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            if (null != var1.field4335) {
               if (var3 != -1760527185) {
                  throw new IllegalStateException();
               }

               if (var1.field4335.length > var2 && var1.field4335[var2] != null) {
                  if (var3 != -1760527185) {
                     throw new IllegalStateException();
                  }

                  if (!var1.field4335[var2].trim().isEmpty()) {
                     return var1.field4335[var2];
                  }

                  if (var3 != -1760527185) {
                     throw new IllegalStateException();
                  }
               }
            }

            return null;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "vv.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvv;Llw;II)Ljava/lang/String;")
   @ObfuscatedName("bd")
   public static String method12200(classVV var0, Widget var1, int var2, int var3) {
      if (var0 == null) {
         var0.method12199(var1, var2, var2);
      }

      try {
         int var5 = method12214(var0, var1, (short)255);
         boolean var10000;
         if ((var5 >> var2 & 1) != 0) {
            if (var3 != -1760527185) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var4 = var10000;
         if (!var4 && null == var1.field4384) {
            if (var3 != -1760527185) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            if (null != var1.field4335) {
               if (var3 != -1760527185) {
                  throw new IllegalStateException();
               }

               if (var1.field4335.length > var2 && var1.field4335[var2] != null) {
                  if (var3 != -1760527185) {
                     throw new IllegalStateException();
                  }

                  if (!var1.field4335[var2].trim().isEmpty()) {
                     return var1.field4335[var2];
                  }

                  if (var3 != -1760527185) {
                     throw new IllegalStateException();
                  }
               }
            }

            return null;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "vv.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("an")
   public void method12207(int var1, int var2) {
      try {
         for (WidgetConfigNode var3 = (WidgetConfigNode)this.field6611.method13422(); var3 != null; var3 = (WidgetConfigNode)this.field6611.method13430()) {
            if ((var3.field6550 >> 16 & 65535L) == var1) {
               var3.vmethod398();
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "vv.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("by")
   public boolean method12195(Widget var1) {
      return var1.method7296(-947902929)
         || method12246(this, var1, (byte)0) != 0
         || method12214(this, var1, (short)255) != 0
         || var1 == this.field6612.method12282(-1484005868);
   }

   @ObfuscatedSignature(descriptor = "(B)Llw;")
   @ObfuscatedName("bp")
   public Widget method12229(byte var1) {
      try {
         return this.field6612.method12282(-975656414);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vv.bp(" + ')');
      }
   }
}
