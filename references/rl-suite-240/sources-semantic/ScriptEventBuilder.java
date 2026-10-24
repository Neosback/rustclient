import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zs")
public final class ScriptEventBuilder implements net.runelite.api.ScriptEventBuilder {
   @ObfuscatedName("az")
   boolean field7266 = false;
   @ObfuscatedName("af")
   Object[] field7259 = null;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("ae")
   Widget field7257 = null;
   @ObfuscatedName("ab")
   int field7258 = 0;
   @ObfuscatedName("ay")
   int field7267;
   @ObfuscatedName("as")
   int field7260;
   @ObfuscatedName("ax")
   int field7261;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("ac")
   Widget field7256;
   @ObfuscatedName("aa")
   int field7255;
   @ObfuscatedName("ao")
   int field7264;
   @ObfuscatedName("al")
   String field7265;
   @ObfuscatedName("aj")
   int field7263;
   @ObfuscatedName("ag")
   int field7262 = 0;

   ScriptEventBuilder() {
      this.field7260 = 0;
      this.field7261 = 0;
      this.field7256 = null;
      this.field7255 = 0;
      this.field7264 = 0;
      this.field7265 = null;
      this.field7263 = 0;
      this.field7267 = 0;
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("of")
   public Widget method14229() {
      return this.field7256;
   }

   @ObfuscatedSignature(descriptor = "(ZI)Lzs;")
   @ObfuscatedName("az")
   public ScriptEventBuilder method14187(boolean var1, int var2) {
      try {
         this.field7266 = var1;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Lzs;")
   @ObfuscatedName("aq")
   public ScriptEventBuilder method14188(boolean var1) {
      this.field7266 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(II)Lzs;")
   @ObfuscatedName("al")
   public ScriptEventBuilder method14197(int var1, int var2) {
      try {
         this.field7258 = -871825887 * var1;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Lzs;")
   @ObfuscatedName("ab")
   public ScriptEventBuilder method14200(int var1, byte var2) {
      try {
         this.field7262 = -1434072017 * var1;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnet/runelite/api/widgets/Widget;)Lzs;")
   @ObfuscatedName("fb")
   public ScriptEventBuilder method14230(net.runelite.api.widgets.Widget var1) {
      this.field7257 = (Widget)var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "()Lzr;")
   @ObfuscatedName("bq")
   public ScriptEvent method14228() {
      return new ScriptEvent(this);
   }

   @ObfuscatedSignature(descriptor = "(II)Lzs;")
   @ObfuscatedName("ae")
   public ScriptEventBuilder method14204(int var1, int var2) {
      try {
         this.field7260 = var1 * -785215481;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)Lzs;")
   @ObfuscatedName("ax")
   public ScriptEventBuilder method14213(String var1, byte var2) {
      try {
         this.field7265 = var1;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Lzs;")
   @ObfuscatedName("ad")
   public ScriptEventBuilder method14189(boolean var1) {
      this.field7266 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(II)Lzs;")
   @ObfuscatedName("as")
   public ScriptEventBuilder method14219(int var1, int var2) {
      try {
         this.field7255 = -1978668005 * var1;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("bx")
   public ScriptEventBuilder method14223(int var1) {
      this.field7263 = -557249088 * var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(B)Lzr;")
   @ObfuscatedName("aj")
   public ScriptEvent method14226(byte var1) {
      try {
         return new ScriptEvent(this);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "zs.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Lzs;")
   @ObfuscatedName("ac")
   public ScriptEventBuilder method14215(Widget var1, byte var2) {
      try {
         this.field7256 = var1;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnet/runelite/api/widgets/Widget;)Lzs;")
   @ObfuscatedName("oj")
   public ScriptEventBuilder method14231(net.runelite.api.widgets.Widget var1) {
      this.field7256 = (Widget)var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Z)Lzs;")
   @ObfuscatedName("ap")
   public ScriptEventBuilder method14190(boolean var1) {
      this.field7266 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;)Lzs;")
   @ObfuscatedName("ai")
   public ScriptEventBuilder method14191(Object[] var1) {
      this.field7259 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;)Lzs;")
   @ObfuscatedName("ar")
   public ScriptEventBuilder method14192(Object[] var1) {
      this.field7259 = var1;
      return this;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzs;I)Lzs;")
   @ObfuscatedName("ev")
   public static ScriptEventBuilder method14220(ScriptEventBuilder var0, int var1) {
      if (var0 == null) {
         var0.method14222(var1, var1);
      }

      var0.field7255 = -1978668005 * var1;
      return var0;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;)Lzs;")
   @ObfuscatedName("aw")
   public ScriptEventBuilder method14193(Object[] var1) {
      this.field7259 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;)Lzs;")
   @ObfuscatedName("ak")
   public ScriptEventBuilder method14194(Object[] var1) {
      this.field7259 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "()Lzr;")
   @ObfuscatedName("qj")
   public ScriptEvent method14227() {
      return new ScriptEvent(this);
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("be")
   public ScriptEventBuilder method14198(int var1) {
      this.field7258 = -871825887 * var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("bc")
   public ScriptEventBuilder method14201(int var1) {
      this.field7262 = -1434072017 * var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("bp")
   public ScriptEventBuilder method14202(int var1) {
      this.field7262 = -1434072017 * var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;B)Lzs;")
   @ObfuscatedName("af")
   public ScriptEventBuilder method14195(Object[] var1, byte var2) {
      try {
         this.field7259 = var1;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzs;I)Lzs;")
   @ObfuscatedName("xg")
   public static ScriptEventBuilder method14203(ScriptEventBuilder var0, int var1) {
      if (var0 == null) {
         var0.getArguments();
      }

      var0.field7262 = 197329699 * var1;
      return var0;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("ut")
   public ScriptEventBuilder method14205(int var1) {
      this.field7260 = var1 * -785215481;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("at")
   public ScriptEventBuilder method14208(int var1) {
      this.field7261 = var1 * 1848781009;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(II)Lzs;")
   @ObfuscatedName("aa")
   public ScriptEventBuilder method14222(int var1, int var2) {
      try {
         this.field7264 = -439510407 * var1;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("bb")
   public ScriptEventBuilder method14209(int var1) {
      this.field7261 = var1 * 1848781009;
      return this;
   }

   public int getOp() {
      return this.field7260 * 1913904567;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("br")
   public ScriptEventBuilder method14199(int var1) {
      this.field7258 = -871825887 * var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Lzs;")
   @ObfuscatedName("bk")
   public ScriptEventBuilder method14216(Widget var1) {
      this.field7256 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("by")
   public ScriptEventBuilder method14232(int var1) {
      this.field7260 = var1 * -785215481;
      return this;
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("ro")
   public Widget method14233() {
      return this.field7257;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Lzs;")
   @ObfuscatedName("bo")
   public ScriptEventBuilder method14217(Widget var1) {
      this.field7256 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(II)Lzs;")
   @ObfuscatedName("ag")
   public ScriptEventBuilder method14210(int var1, int var2) {
      try {
         this.field7261 = var1 * 1848781009;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("bt")
   public ScriptEventBuilder method14211(int var1) {
      this.field7261 = var1 * 1848781009;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("av")
   public ScriptEventBuilder method14221(int var1) {
      this.field7255 = -1094000011 * var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;)Lzs;")
   @ObfuscatedName("au")
   public ScriptEventBuilder method14196(Object[] var1) {
      this.field7259 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("am")
   public ScriptEventBuilder method14206(int var1) {
      this.field7260 = var1 * -785215481;
      return this;
   }

   public Object[] getArguments() {
      return this.field7259;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("bm")
   public ScriptEventBuilder method14224(int var1) {
      this.field7263 = -1742323659 * var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Lzs;")
   @ObfuscatedName("bu")
   public ScriptEventBuilder method14218(Widget var1) {
      this.field7256 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;)Lzs;")
   @ObfuscatedName("mn")
   public ScriptEventBuilder method14234(Object[] var1) {
      this.field7259 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(II)Lzs;")
   @ObfuscatedName("ao")
   public ScriptEventBuilder method14212(int var1, int var2) {
      try {
         this.field7255 = var1 * 1848781009;
         return this;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zs.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzs;II)Lzs;")
   @ObfuscatedName("gf")
   public static ScriptEventBuilder method14225(ScriptEventBuilder var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method14212(var1, var1);
      } else {
         try {
            var0.field7263 = -1742323659 * var1;
            return var0;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "zs.al(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lzs;")
   @ObfuscatedName("bg")
   public ScriptEventBuilder method14214(String var1) {
      this.field7265 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)Lzs;")
   @ObfuscatedName("ah")
   public ScriptEventBuilder method14207(int var1) {
      this.field7261 = var1 * -785215481;
      return this;
   }
}
