import net.runelite.api.events.NameableNameChanged;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rr")
public class Nameable implements Comparable, net.runelite.api.Nameable {
   @ObfuscatedSignature(descriptor = "Laae;")
   @ObfuscatedName("ae")
   classAAE field5833;
   @ObfuscatedSignature(descriptor = "Laae;")
   @ObfuscatedName("ab")
   classAAE field5832;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   static final int field5831 = 26;

   @ObfuscatedSignature(descriptor = "(Laae;Laae;)V")
   @ObfuscatedName("bm")
   void method10225(classAAE var1, classAAE var2) {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         this.field5833 = var1;
         this.field5832 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Laae;")
   @ObfuscatedName("at")
   public classAAE method10216(byte var1) {
      try {
         return this.field5833;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rr.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/String;")
   @ObfuscatedName("an")
   public String method10220(byte var1) {
      try {
         if (null == this.field5833) {
            if (var1 != 1) {
               throw new IllegalStateException();
            } else {
               return "";
            }
         } else {
            return classAAE.method157(this.field5833, 1523204793);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rr.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Laae;")
   @ObfuscatedName("bn")
   public classAAE method10217() {
      return this.field5833;
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;I)V")
   @ObfuscatedName("ah")
   void method10226(classAAE var1, classAAE var2, int var3) {
      try {
         if (var1 == null) {
            if (var3 < -1645256279) {
               throw new NullPointerException();
            }
         } else {
            this.field5833 = var1;
            this.field5832 = var2;
            this.method10229(-1);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rr.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cg")
   static int method10228(int var0) {
      try {
         int var1 = 2;
         Player var2 = classMB.method7564(-979057698);
         if (null != var2) {
            if (var0 >= 408219128) {
               throw new IllegalStateException();
            }

            if (null != var2.field1283) {
               if (var0 >= 408219128) {
                  throw new IllegalStateException();
               }

               var1 = -513083879 * var2.field1283.field4091;
            }
         }

         return var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rr.cg(" + 41);
      }
   }

   @Override
   public int compareTo(Object var1) {
      try {
         return this.vmethod562((Nameable)var1, -30374874);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rr.compareTo(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ot")
   public void method10229(int var1) {
      NameableNameChanged var2 = new NameableNameChanged(this);
      classOE.field4841.getCallbacks().post(var2);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)I")
   @ObfuscatedName("ae")
   public int vmethod565(Object var1) {
      return this.vmethod562((Nameable)var1, -30374874);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)I")
   @ObfuscatedName("ak")
   public int vmethod563(Nameable var1) {
      return classAAE.method178(this.field5833, var1.field5833, 1600959519);
   }

   @ObfuscatedSignature(descriptor = "()Laae;")
   @ObfuscatedName("jk")
   public classAAE method10230() {
      return this.field5832;
   }

   Nameable() {
   }

   @ObfuscatedSignature(descriptor = "()Laae;")
   @ObfuscatedName("bx")
   public classAAE method10218() {
      return this.field5833;
   }

   public String getName() {
      return this.method10231().method183();
   }

   @ObfuscatedSignature(descriptor = "()Laae;")
   @ObfuscatedName("bc")
   public classAAE method10219() {
      return this.field5833;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("br")
   public String method10221() {
      return null == this.field5833 ? "" : classAAE.method157(this.field5833, 727142096);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bp")
   public String method10223() {
      return this.field5832 == null ? "" : classAAE.method157(this.field5832, 1695596637);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;I)I")
   @ObfuscatedName("aw")
   public int vmethod562(Nameable var1, int var2) {
      try {
         return classAAE.method178(this.field5833, var1.field5833, 1600959519);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rr.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;B)Ljava/lang/String;")
   @ObfuscatedName("gv")
   public static String method10224(Nameable var0, byte var1) {
      if (var0 == null) {
         var0.method10222(var1);
      }

      try {
         return var0.field5832 == null ? "" : classAAE.method157(var0.field5832, 1422453419);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rr.am(" + ')');
      }
   }

   public String getPrevName() {
      classAAE var1 = this.method10230();
      return var1 == null ? null : var1.method183();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgn;)I")
   @ObfuscatedName("kd")
   public static int method10227(classGN var0) {
      return var0.field2625[0].length;
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)I")
   @ObfuscatedName("av")
   public int vmethod564(Nameable var1) {
      return classAAE.method178(this.field5833, var1.field5833, 1600959519);
   }

   @ObfuscatedSignature(descriptor = "()Laae;")
   @ObfuscatedName("ae")
   public classAAE method10231() {
      return this.field5833;
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/String;")
   @ObfuscatedName("am")
   public String method10222(byte var1) {
      try {
         if (null == this.field5833) {
            if (var1 != 1) {
               throw new IllegalStateException();
            } else {
               return "";
            }
         } else {
            return classAAE.method157(this.field5832, 1523204793);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rr.an(" + ')');
      }
   }
}
