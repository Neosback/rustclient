import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("aae")
public class classAAE implements Comparable {
   @ObfuscatedName("az")
   String field41;
   @ObfuscatedName("af")
   String field42;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field43 = 1;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ay")
   public boolean method165() {
      return this.field41 != null;
   }

   @ObfuscatedSignature(descriptor = "(Laae;I)Ljava/lang/String;")
   @ObfuscatedName("du")
   public static String method157(classAAE var0, int var1) {
      if (var0 == null) {
         return var0.method159(var1);
      } else {
         try {
            return var0.field42;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "aae.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("al")
   public String method174() {
      return method157(this, 556080291);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gd")
   public String method158() {
      return this.field42;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   public boolean method166(int var1) {
      try {
         boolean var10000;
         if (this.field41 != null) {
            if (var1 <= -947621106) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "aae.ab(" + ')');
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         if (var1 instanceof classAAE) {
            classAAE var2 = (classAAE)var1;
            if (this.field41 == null) {
               return null == var2.field41;
            } else if (null == var2.field41) {
               return false;
            } else {
               return this.hashCode() != var2.hashCode() ? false : this.field41.equals(var2.field41);
            }
         } else {
            return false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "aae.equals(" + ')');
      }
   }

   @Override
   public String toString() {
      try {
         return method157(this, 1585434746);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "aae.toString(" + ')');
      }
   }

   @Override
   public int hashCode() {
      try {
         return null == this.field41 ? 0 : this.field41.hashCode();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "aae.hashCode(" + 41);
      }
   }

   @Override
   public int compareTo(Object var1) {
      try {
         return method178(this, (classAAE)var1, 1600959519);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "aae.compareTo(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aa")
   public String method182() {
      return this.field41;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("mk")
   public String method183() {
      return this.field42;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ao")
   public String method175() {
      return method157(this, 1165451519);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Lex;)V")
   @ObfuscatedName("ws")
   public static void method181(Scene var0, classEX var1) {
      if (var1 != var0.field2050) {
         var0.field2050 = var1;
         Scene.method4333(Scene.field2070, Scene.field2128, Scene.field2124, Scene.field2133, Scene.field2129);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gi")
   public String method176() {
      return method157(this, 1900267168);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method168(Object var1) {
      if (var1 instanceof classAAE) {
         classAAE var2 = (classAAE)var1;
         if (this.field41 == null) {
            return null == var2.field41;
         } else if (null == var2.field41) {
            return false;
         } else {
            return this.hashCode() != var2.hashCode() ? false : this.field41.equals(var2.field41);
         }
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;I)Ljava/lang/String;")
   @ObfuscatedName("zk")
   public static String method160(classAAE var0, int var1) {
      if (var0 == null) {
         return var0.method164(var1);
      } else {
         try {
            return var0.field41;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "aae.af(" + ')');
         }
      }
   }

   public classAAE(String var1) {
      this.field42 = var1;
      this.field41 = classBW.method1259(var1, classAAO.field79, -2140318512);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method171() {
      return null == this.field41 ? 0 : this.field41.hashCode();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afr")
   public int method172() {
      return null == this.field41 ? 0 : this.field41.hashCode();
   }

   public classAAE(String var1, classAAO var2) {
      this.field42 = var1;
      this.field41 = classBW.method1259(var1, var2, -1490299890);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afh")
   public int method173() {
      return null == this.field41 ? 0 : this.field41.hashCode();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("as")
   public String method161() {
      return this.field41;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laae;Ljava/lang/Object;)Z")
   @ObfuscatedName("hq")
   public static boolean method169(classAAE var0, Object var1) {
      if (var0 == null) {
         var0.method161();
      }

      if (var1 instanceof classAAE) {
         classAAE var2 = (classAAE)var1;
         if (var0.field41 == null) {
            return null == var2.field41;
         } else if (null == var2.field41) {
            return false;
         } else {
            return var0.hashCode() != var2.hashCode() ? false : var0.field41.equals(var2.field41);
         }
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("yi")
   public String method162() {
      return this.field41;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method163() {
      return this.field41;
   }

   @ObfuscatedSignature(descriptor = "(Laae;)I")
   @ObfuscatedName("ad")
   public int method177(classAAE var1) {
      if (null == this.field41) {
         return null == var1.field41 ? 0 : 1;
      } else {
         return var1.field41 == null ? -1 : this.field41.compareTo(var1.field41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;I)I")
   @ObfuscatedName("bj")
   public static int method178(classAAE var0, classAAE var1, int var2) {
      if (var0 == null) {
         return var0.method180(var0, var2);
      } else {
         try {
            if (null == var0.field41) {
               if (var2 != 1600959519) {
                  throw new IllegalStateException();
               } else {
                  return null == var1.field41 ? 0 : 1;
               }
            } else if (var1.field41 == null) {
               if (var2 != 1600959519) {
                  throw new IllegalStateException();
               } else {
                  return -1;
               }
            } else {
               return var0.field41.compareTo(var1.field41);
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "aae.ag(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)I")
   @ObfuscatedName("ap")
   public int method179(classAAE var1) {
      if (null == this.field41) {
         return null == var1.field41 ? 0 : 1;
      } else {
         return var1.field41 == null ? -1 : this.field41.compareTo(var1.field41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("af")
   public String method164(int var1) {
      try {
         return this.field42;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "aae.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxk;I)I")
   @ObfuscatedName("ms")
   public static int method170(classXK var0, int var1) {
      return var0.field6885[var1];
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aj")
   public boolean method167() {
      return this.field42 != null;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("az")
   public String method159(int var1) {
      try {
         return this.field41;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "aae.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;I)I")
   @ObfuscatedName("ag")
   public int method180(classAAE var1, int var2) {
      try {
         if (null == this.field42) {
            if (var2 != 1600959519) {
               throw new IllegalStateException();
            } else {
               return null == var1.field41 ? 0 : 1;
            }
         } else if (var1.field42 == null) {
            if (var2 != 1600959519) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            return this.field41.compareTo(var1.field42);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "aae.ag(" + 41);
      }
   }
}
