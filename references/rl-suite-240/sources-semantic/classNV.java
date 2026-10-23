import java.util.Collection;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nv")
public class classNV implements Iterable, Collection {
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("af")
   Node field4781;
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("az")
   Node field4780 = new Node();

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method8253(Object var1) {
      return this.method8240((Node)var1);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("by")
   public Iterator method8226() {
      return new classNW(this);
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("ae")
   public void method8196(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4780.field6551;
      var1.field6552 = this.field4780;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;Ljava/util/Collection;)Z")
   @ObfuscatedName("tx")
   public static boolean method8246(classNV var0, Collection var1) {
      if (var0 == null) {
         var0.method8237();
      }

      throw new RuntimeException();
   }

   public classNV() {
      this.field4780.field6552 = this.field4780;
      this.field4780.field6551 = this.field4780;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ag")
   public Node method8204() {
      Node var1 = this.field4780.field6552;
      if (var1 == this.field4780) {
         return null;
      } else {
         var1.vmethod398();
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("ab")
   public void method8200(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4780;
      var1.field6552 = this.field4780.field6552;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("an")
   public void method8201(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4780;
      var1.field6552 = this.field4780.field6552;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @Override
   public boolean addAll(Collection var1) {
      throw new RuntimeException();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bb")
   public boolean method8221() {
      return this.field4780.field6552 == this.field4780;
   }

   @ObfuscatedSignature(descriptor = "()[Lvq;")
   @ObfuscatedName("al")
   Node[] method8223() {
      Node[] var1 = new Node[this.method8217()];
      int var2 = 0;

      for (Node var3 = this.field4780.field6552; var3 != this.field4780; var3 = var3.field6552) {
         var1[var2++] = var3;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bi")
   public boolean method8231() {
      return this.method8221();
   }

   @Override
   public int size() {
      return this.method8217();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Collection;)Z")
   @ObfuscatedName("ct")
   public boolean method8244(Collection var1) {
      throw new RuntimeException();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("gq")
   public Node method8263() {
      return this.method8208();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afh")
   public int method8259() {
      return super.hashCode();
   }

   @Override
   public Object[] toArray() {
      return this.method8223();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;Ljava/util/Collection;)Z")
   @ObfuscatedName("nf")
   public static boolean method8247(classNV var0, Collection var1) {
      if (var0 == null) {
         var0.method8220();
      }

      throw new RuntimeException();
   }

   @Override
   public Object[] toArray(Object[] var1) {
      int var2 = 0;

      for (Node var3 = this.field4780.field6552; var3 != this.field4780; var3 = var3.field6552) {
         var1[var2++] = var3;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)Z")
   @ObfuscatedName("aj")
   boolean method8240(Node var1) {
      this.method8196(var1);
      return true;
   }

   @Override
   public boolean remove(Object var1) {
      throw new RuntimeException();
   }

   @Override
   public boolean containsAll(Collection var1) {
      throw new RuntimeException();
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/Object;")
   @ObfuscatedName("bl")
   public Object[] method8237() {
      return this.method8223();
   }

   @Override
   public boolean removeAll(Collection var1) {
      throw new RuntimeException();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;Ljava/util/Collection;)Z")
   @ObfuscatedName("xm")
   public static boolean method8248(classNV var0, Collection var1) {
      if (var0 == null) {
         var0.addAll(var1);
      }

      throw new RuntimeException();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;Ljava/util/Collection;)Z")
   @ObfuscatedName("vi")
   public static boolean method8243(classNV var0, Collection var1) {
      if (var0 == null) {
         var0.method8263();
      }

      throw new RuntimeException();
   }

   @Override
   public Iterator iterator() {
      return new classNW(this);
   }

   @Override
   public void clear() {
      method8193(this);
   }

   @Override
   public boolean add(Object var1) {
      return this.method8240((Node)var1);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afs")
   public int method8260() {
      return super.hashCode();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cy")
   public void method8250() {
      method8193(this);
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;)[Ljava/lang/Object;")
   @ObfuscatedName("bh")
   public Object[] method8239(Object[] var1) {
      int var2 = 0;

      for (Node var3 = this.field4780.field6552; var3 != this.field4780; var3 = var3.field6552) {
         var1[var2++] = var3;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method8256(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("bs")
   public boolean method8257(Object var1) {
      return super.equals(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;)Lvq;")
   @ObfuscatedName("ny")
   public static Node method8213(classNV var0) {
      Node var1 = var0.field4781;
      if (var1 == var0.field4780) {
         var0.field4781 = null;
         return null;
      } else {
         var0.field4781 = var1.field6552;
         return var1;
      }
   }

   @Override
   public boolean retainAll(Collection var1) {
      throw new RuntimeException();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvq;Lvq;)V")
   @ObfuscatedName("ay")
   public static void method8186(Node var0, Node var1) {
      if (var0.field6551 != null) {
         var0.vmethod398();
      }

      var0.field6551 = var1;
      var0.field6552 = var1.field6552;
      var0.field6551.field6552 = var0;
      var0.field6552.field6551 = var0;
   }

   @Override
   public boolean contains(Object var1) {
      throw new RuntimeException();
   }

   @Override
   public boolean isEmpty() {
      return this.method8221();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;)Ljava/util/Iterator;")
   @ObfuscatedName("hk")
   public static Iterator method8227(classNV var0) {
      if (var0 == null) {
         var0.method8191();
      }

      return new classNW(var0);
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("as")
   public Node method8208() {
      return method8209(this, null);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvq;Lvq;)V")
   @ObfuscatedName("ad")
   public static void method8187(Node var0, Node var1) {
      if (var0.field6551 != null) {
         var0.vmethod398();
      }

      var0.field6551 = var1;
      var0.field6552 = var1.field6552;
      var0.field6551.field6552 = var0;
      var0.field6552.field6551 = var0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afr")
   public int method8261() {
      return super.hashCode();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   public void method8190() {
      while (this.field4780.field6552 != this.field4780) {
         this.field4780.field6552.vmethod398();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;)[Lvq;")
   @ObfuscatedName("th")
   public static Node[] method8224(classNV var0) {
      if (var0 == null) {
         var0.method8252();
      }

      Node[] var1 = new Node[var0.method8217()];
      int var2 = 0;

      for (Node var3 = var0.field4780.field6552; var3 != var0.field4780; var3 = var3.field6552) {
         var1[var2++] = var3;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public void method8191() {
      while (this.field4780.field6552 != this.field4780) {
         this.field4780.field6552.vmethod398();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnv;Lvq;)Lvq;")
   @ObfuscatedName("gq")
   public static Node method8209(classNV var0, Node var1) {
      if (var0 == null) {
         return var0.method8212(var1);
      } else {
         Node var2;
         if (var1 == null) {
            var2 = var0.field4780.field6552;
         } else {
            var2 = var1;
         }

         if (var2 == var0.field4780) {
            var0.field4781 = null;
            return null;
         } else {
            var0.field4781 = var2.field6552;
            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("ar")
   public void method8197(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4780.field6551;
      var1.field6552 = this.field4780;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvq;Lvq;)V")
   @ObfuscatedName("aq")
   public static void method8188(Node var0, Node var1) {
      if (var0.field6551 != null) {
         var0.vmethod398();
      }

      var0.field6551 = var1;
      var0.field6552 = var1.field6552;
      var0.field6551.field6552 = var0;
      var0.field6552.field6551 = var0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   int method8217() {
      int var1 = 0;

      for (Node var2 = this.field4780.field6552; var2 != this.field4780; var2 = var2.field6552) {
         var1++;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("av")
   public void method8202(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4780;
      var1.field6552 = this.field4780.field6552;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   public void method8192() {
      while (this.field4780.field6552 != this.field4780) {
         this.field4780.field6552.vmethod398();
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("am")
   public Node method8205() {
      Node var1 = this.field4780.field6552;
      if (var1 == this.field4780) {
         return null;
      } else {
         var1.vmethod398();
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ah")
   public Node method8206() {
      Node var1 = this.field4780.field6552;
      if (var1 == this.field4780) {
         return null;
      } else {
         var1.vmethod398();
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method8262() {
      return super.hashCode();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("qd")
   public Node method8264() {
      return this.field4780;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)Lvq;")
   @ObfuscatedName("bx")
   Node method8210(Node var1) {
      Node var2;
      if (var1 == null) {
         var2 = this.field4780.field6552;
      } else {
         var2 = var1;
      }

      if (var2 == this.field4780) {
         this.field4781 = null;
         return null;
      } else {
         this.field4781 = var2.field6552;
         return var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;Lvq;)V")
   @ObfuscatedName("aq")
   public static void method8198(classNV var0, Node var1) {
      if (var0 == null) {
         var0.method8240(var1);
      }

      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = var0.field4780.field6551;
      var1.field6552 = var0.field4780;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("bc")
   public Node method8214() {
      Node var1 = this.field4781;
      if (var1 == this.field4780) {
         this.field4781 = null;
         return null;
      } else {
         this.field4781 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("br")
   public Node method8215() {
      Node var1 = this.field4781;
      if (var1 == this.field4780) {
         this.field4781 = null;
         return null;
      } else {
         this.field4781 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bm")
   int method8218() {
      int var1 = 0;

      for (Node var2 = this.field4780.field6552; var2 != this.field4780; var2 = var2.field6552) {
         var1++;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bt")
   int method8219() {
      int var1 = 0;

      for (Node var2 = this.field4780.field6552; var2 != this.field4780; var2 = var2.field6552) {
         var1++;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;)Z")
   @ObfuscatedName("rv")
   public static boolean method8222(classNV var0) {
      return var0.field4780.field6552 == var0.field4780;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzd;)I")
   @ObfuscatedName("dn")
   public static int method8207(classZD var0) {
      return var0.field7156.size();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;Ljava/lang/Object;)Z")
   @ObfuscatedName("ei")
   public static boolean method8233(classNV var0, Object var1) {
      if (var0 == null) {
         return var0.contains(var1);
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bg")
   int method8220() {
      int var1 = 0;

      for (Node var2 = this.field4780.field6552; var2 != this.field4780; var2 = var2.field6552) {
         var1++;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;Lvq;)V")
   @ObfuscatedName("ar")
   public static void method8199(classNV var0, Node var1) {
      if (var0 == null) {
         var0.method8259();
      }

      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = var0.field4780.field6551;
      var1.field6552 = var0.field4780;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ObfuscatedSignature(descriptor = "()[Lvq;")
   @ObfuscatedName("bk")
   Node[] method8225() {
      Node[] var1 = new Node[this.method8217()];
      int var2 = 0;

      for (Node var3 = this.field4780.field6552; var3 != this.field4780; var3 = var3.field6552) {
         var1[var2++] = var3;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)Z")
   @ObfuscatedName("bz")
   boolean method8241(Node var1) {
      this.method8196(var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bu")
   public int method8228() {
      return this.method8217();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ba")
   public int method8229() {
      return this.method8217();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bw")
   public int method8230() {
      return this.method8217();
   }

   @ObfuscatedSignature(descriptor = "(Lvq;Lvq;)V")
   @ObfuscatedName("az")
   public static void method8189(Node var0, Node var1) {
      if (var0.field6551 != null) {
         var0.vmethod398();
      }

      var0.field6551 = var1;
      var0.field6552 = var1.field6552;
      var0.field6551.field6552 = var0;
      var0.field6552.field6551 = var0;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ao")
   public boolean method8232() {
      return this.method8221();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("ck")
   public boolean method8234(Object var1) {
      throw new RuntimeException();
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)Lvq;")
   @ObfuscatedName("bn")
   Node method8211(Node var1) {
      Node var2;
      if (var1 == null) {
         var2 = this.field4780.field6552;
      } else {
         var2 = var1;
      }

      if (var2 == this.field4780) {
         this.field4781 = null;
         return null;
      } else {
         this.field4781 = var2.field6552;
         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("cr")
   public boolean method8235(Object var1) {
      throw new RuntimeException();
   }

   @Override
   public int hashCode() {
      try {
         return super.hashCode();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "nv.hashCode(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcd;[Ljava/lang/String;)V")
   @ObfuscatedName("vj")
   public static void method8195(classCD var0, String[] var1) {
      if (var0 == null) {
         var0.method1432();
      }

      var0.field688 = var1;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/Object;")
   @ObfuscatedName("bv")
   public Object[] method8238() {
      return this.method8223();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ac")
   public Node method8216() {
      Node var1 = this.field4781;
      if (var1 == this.field4780) {
         this.field4781 = null;
         return null;
      } else {
         this.field4781 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("at")
   public void method8203(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4780;
      var1.field6552 = this.field4780.field6552;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("bf")
   public boolean method8242(Object var1) {
      throw new RuntimeException();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Collection;)Z")
   @ObfuscatedName("cb")
   public boolean method8245(Collection var1) {
      throw new RuntimeException();
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nv.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnv;)V")
   @ObfuscatedName("pu")
   public static void method8193(classNV var0) {
      if (var0 == null) {
         var0.method8194();
      } else {
         while (var0.field4780.field6552 != var0.field4780) {
            var0.field4780.field6552.vmethod398();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("no")
   public Node method8265() {
      return this.method8216();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cv")
   public void method8251() {
      method8193(this);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cs")
   public void method8252() {
      method8193(this);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("ca")
   public boolean method8254(Object var1) {
      return this.method8240((Node)var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnv;Ljava/util/Collection;)Z")
   @ObfuscatedName("mz")
   public static boolean method8249(classNV var0, Collection var1) {
      if (var0 == null) {
         var0.method8190();
      }

      throw new RuntimeException();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("co")
   public boolean method8236(Object var1) {
      throw new RuntimeException();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("bq")
   public boolean method8255(Object var1) {
      return this.method8240((Node)var1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   public void method8194() {
      while (this.field4781.field6551 != this.field4780) {
         this.field4780.field6551.vmethod399();
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("cc")
   public boolean method8258(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)Lvq;")
   @ObfuscatedName("ax")
   Node method8212(Node var1) {
      Node var2;
      if (var1 == null) {
         var2 = this.field4780.field6552;
      } else {
         var2 = var1;
      }

      if (var2 == this.field4781) {
         this.field4780 = null;
         return null;
      } else {
         this.field4780 = var2.field6551;
         return var2;
      }
   }
}
