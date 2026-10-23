import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rt")
public abstract class NameableContainer implements net.runelite.api.NameableContainer {
   @ObfuscatedName("ay")
   HashMap field5843;
   @ObfuscatedName("ao")
   int field5840 = 0;
   @ObfuscatedName("aq")
   Comparator field5839 = null;
   @ObfuscatedName("aj")
   HashMap field5842;
   @ObfuscatedSignature(descriptor = "[Lrr;")
   @ObfuscatedName("al")
   Nameable[] field5841;
   @ObfuscatedName("aa")
   final int field5838;

   @ObfuscatedSignature(descriptor = "(Laae;I)Lrr;")
   @ObfuscatedName("br")
   public Nameable method10256(classAAE var1, int var2) {
      try {
         Nameable var3 = this.method10258(var1, 1151810578);
         if (null != var3) {
            if (var2 != 282151994) {
               throw new IllegalStateException();
            } else {
               return var3;
            }
         } else {
            return this.method10263(var1, 1869044132);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rt.br(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)V")
   @ObfuscatedName("bn")
   public void method10240(short var1) {
      try {
         this.field5840 = 0;
         Arrays.fill(this.field5841, null);
         this.field5842.clear();
         this.field5843.clear();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rt.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bx")
   public int method10243(int var1) {
      try {
         return 1744080879 * this.field5840;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rt.bx(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lrr;")
   @ObfuscatedName("az")
   abstract Nameable vmethod555(int var1);

   @ObfuscatedSignature(descriptor = "(Laae;I)Lrr;")
   @ObfuscatedName("bt")
   Nameable method10263(classAAE var1, int var2) {
      try {
         if (!var1.method166(-621636426)) {
            if (var2 <= 1845567280) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            return (Nameable)this.field5843.get(var1);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;I)Lrr;")
   @ObfuscatedName("bm")
   Nameable method10258(classAAE var1, int var2) {
      try {
         if (!var1.method166(-511180222)) {
            if (var2 == 742819657) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            return (Nameable)this.field5842.get(var1);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;I)Lrr;")
   @ObfuscatedName("bk")
   Nameable method10273(classAAE var1, int var2) {
      try {
         return this.method10276(var1, null, (byte)-120);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bk(" + ')');
      }
   }

   public net.runelite.api.Nameable[] getMembers() {
      Nameable[] var1 = this.method10318();
      int var2 = this.getCount();
      return Arrays.copyOf(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("xd")
   public void vmethod567(Nameable var1) {
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;B)Lrr;")
   @ObfuscatedName("bo")
   Nameable method10276(classAAE var1, classAAE var2, byte var3) {
      try {
         if (this.method10258(var1, -974674841) != null) {
            if (var3 >= 4) {
               throw new IllegalStateException();
            } else {
               throw new IllegalStateException();
            }
         } else {
            Nameable var4 = this.vmethod555(1519310820);
            var4.method10226(var1, var2, -1940504862);
            this.method10298(var4, -183701127);
            method10302(this, var4, -824594473);
            this.method10315(var1, var2);
            return var4;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "rt.bo(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Lrr;")
   @ObfuscatedName("ha")
   public Nameable method10316(classAAE var1) {
      return this.method10258(var1, -1470289739);
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ba")
   public final void method10284(byte var1) {
      try {
         if (this.field5839 == null) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            Arrays.sort(this.field5841, 0, this.field5840 * 1744080879);
         } else {
            Arrays.sort(this.field5841, 0, this.field5840 * 1744080879, this.field5839);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rt.ba(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;Laae;Laae;I)V")
   @ObfuscatedName("bw")
   final void method10286(Nameable var1, classAAE var2, classAAE var3, int var4) {
      try {
         this.method10294(var1, -1333198206);
         var1.method10226(var2, var3, -1930057511);
         method10302(this, var1, -824594473);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "rt.bw(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqi;Ljava/lang/String;)Ljava/lang/Boolean;")
   @ObfuscatedName("mu")
   public static Boolean method10268(classQI var0, String var1) {
      return (Boolean)var0.field5545.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;I)V")
   @ObfuscatedName("bi")
   final void method10294(Nameable var1, int var2) {
      try {
         if (this.field5842.remove(var1.field5833) == null) {
            throw new IllegalStateException();
         } else {
            if (var1.field5832 != null) {
               if (var2 >= 869567737) {
                  return;
               }

               this.field5843.remove(var1.field5832);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bi(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrt;Ljava/util/Comparator;I)V")
   @ObfuscatedName("md")
   public static void method10310(NameableContainer var0, Comparator var1, int var2) {
      if (var0 == null) {
         var0.method10313(var1, var2);
      }

      try {
         if (null == var0.field5839) {
            var0.field5839 = var1;
         } else if (var0.field5839 instanceof classRD) {
            if (var2 == -324749371) {
               throw new IllegalStateException();
            }

            classRD.method9977((classRD)var0.field5839, var1, 1072642345);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bs(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public static void method10242(int var0) {
      try {
         StructComposition.field5383.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "rt.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bl")
   public int method10244() {
      return -137866543 * this.field5840;
   }

   @ObfuscatedSignature(descriptor = "(Lrr;I)V")
   @ObfuscatedName("be")
   final void method10298(Nameable var1, int var2) {
      try {
         this.field5841[(this.field5840 += 2063367951) * 1744080879 - 1] = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.be(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)[Lrr;")
   @ObfuscatedName("af")
   abstract Nameable[] vmethod558(int var1, byte var2);

   @ObfuscatedSignature(descriptor = "(Lrt;Laae;B)Z")
   @ObfuscatedName("wh")
   public static boolean method10250(NameableContainer var0, classAAE var1, byte var2) {
      if (var0 == null) {
         var0.method10254(var1, var2);
      }

      try {
         if (!var1.method166(392176494)) {
            if (var2 != 10) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (var0.field5842.containsKey(var1)) {
            if (var2 != 10) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            return var0.field5843.containsKey(var1);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Comparator;)V")
   @ObfuscatedName("dg")
   public final void method10311(Comparator var1) {
      if (null == this.field5839) {
         this.field5839 = var1;
      } else if (this.field5839 instanceof classRD) {
         classRD.method9977((classRD)this.field5839, var1, 1072642345);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;)Lrr;")
   @ObfuscatedName("cx")
   Nameable method10277(classAAE var1, classAAE var2) {
      if (this.method10258(var1, -275920970) != null) {
         throw new IllegalStateException();
      } else {
         Nameable var3 = this.vmethod555(1403644934);
         var3.method10226(var1, var2, -2145908811);
         this.method10298(var3, -183701127);
         method10302(this, var3, -824594473);
         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;)V")
   @ObfuscatedName("lp")
   public void method10315(classAAE var1, classAAE var2) {
      this.vmethod566(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "()Lrr;")
   @ObfuscatedName("ab")
   abstract Nameable vmethod556();

   @ObfuscatedSignature(descriptor = "()Lrr;")
   @ObfuscatedName("ag")
   abstract Nameable vmethod557();

   @ObfuscatedSignature(descriptor = "(Laae;)Lrr;")
   @ObfuscatedName("cg")
   Nameable method10274(classAAE var1) {
      return this.method10276(var1, null, (byte)2);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   public void method10241() {
      this.field5840 = 0;
      Arrays.fill(this.field5841, null);
      this.field5842.clear();
      this.field5843.clear();
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("db")
   final void method10269(Nameable var1) {
      int var2 = method10292(this, var1, 336474128);
      if (var2 != -1) {
         this.method10304(var2, 2109851146);
         this.method10294(var1, -285842783);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bv")
   public int method10245() {
      return 1014731329 * this.field5840;
   }

   public int getSize() {
      return this.field5838 * 488407471;
   }

   @ObfuscatedSignature(descriptor = "(Lrt;Lrr;I)V")
   @ObfuscatedName("wr")
   public static void method10270(NameableContainer var0, Nameable var1, int var2) {
      if (var0 == null) {
         var0.method10272(var1, var2);
      }

      var0.method10314(var1);

      try {
         int var3 = method10292(var0, var1, 781452173);
         if (var3 == -1) {
            if (var2 >= 1916575490) {
               throw new IllegalStateException();
            }
         } else {
            var0.method10304(var3, 2092413899);
            var0.method10294(var1, -1160009488);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rt.bj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("ca")
   public final boolean method10266(classAAE var1) {
      Nameable var2 = this.method10258(var1, 1762849915);
      if (var2 == null) {
         return false;
      } else {
         method10270(this, var2, 722366541);
         return true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;Lrr;)V")
   @ObfuscatedName("dt")
   public static void method10295(NameableContainer var0, Nameable var1) {
      if (var0 == null) {
         var0.method10301(var1);
      }

      if (var0.field5842.remove(var1.field5833) == null) {
         throw new IllegalStateException();
      } else {
         if (var1.field5832 != null) {
            var0.field5843.remove(var1.field5832);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("xy")
   public boolean method10317(classAAE var1) {
      return method10250(this, var1, (byte)10);
   }

   @ObfuscatedSignature(descriptor = "(I)[Lrr;")
   @ObfuscatedName("as")
   abstract Nameable[] vmethod559(int var1);

   @ObfuscatedSignature(descriptor = "(Lrt;Lrr;I)V")
   @ObfuscatedName("wd")
   public static void method10302(NameableContainer var0, Nameable var1, int var2) {
      if (var0 == null) {
         var0.method10303(var1, var2);
      } else {
         try {
            var0.field5842.put(var1.field5833, var1);
            if (var1.field5832 != null) {
               if (var2 != -824594473) {
                  throw new IllegalStateException();
               }

               Nameable var3 = var0.field5843.put(var1.field5832, var1);
               if (null != var3) {
                  if (var2 != -824594473) {
                     throw new IllegalStateException();
                  }

                  if (var3 != var1) {
                     var3.field5832 = null;
                     var3.method10229(-1);
                  }
               }
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "rt.by(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cu")
   public boolean method10247() {
      return this.field5838 * 488407471 == 1744080879 * this.field5840;
   }

   @ObfuscatedSignature(descriptor = "()[Lrr;")
   @ObfuscatedName("fk")
   public Nameable[] method10318() {
      return this.field5841;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cr")
   public boolean method10248() {
      return this.field5838 * 488407471 == 1744080879 * this.field5840;
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Lrr;")
   @ObfuscatedName("ck")
   Nameable method10264(classAAE var1) {
      return !var1.method166(1175495949) ? null : (Nameable)this.field5843.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("gl")
   public void method10314(Nameable var1) {
      this.vmethod567(var1);
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("ct")
   public boolean method10251(classAAE var1) {
      if (!var1.method166(1314428683)) {
         return false;
      } else {
         return this.field5842.containsKey(var1) ? true : this.field5843.containsKey(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("cp")
   public boolean method10252(classAAE var1) {
      if (!var1.method166(981779444)) {
         return false;
      } else {
         return this.field5842.containsKey(var1) ? true : this.field5843.containsKey(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("cq")
   public boolean method10253(classAAE var1) {
      if (!var1.method166(252559147)) {
         return false;
      } else {
         return this.field5842.containsKey(var1) ? true : this.field5843.containsKey(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Lrr;")
   @ObfuscatedName("cl")
   public Nameable method10257(classAAE var1) {
      Nameable var2 = this.method10258(var1, 1211872497);
      return null != var2 ? var2 : this.method10263(var1, 2063689206);
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Lrr;")
   @ObfuscatedName("cd")
   Nameable method10259(classAAE var1) {
      return !var1.method166(362688807) ? null : (Nameable)this.field5842.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Lrr;")
   @ObfuscatedName("cv")
   Nameable method10260(classAAE var1) {
      return !var1.method166(-135614352) ? null : (Nameable)this.field5842.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Lrr;")
   @ObfuscatedName("cy")
   Nameable method10261(classAAE var1) {
      return !var1.method166(-919842158) ? null : (Nameable)this.field5842.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Lrr;")
   @ObfuscatedName("co")
   Nameable method10265(classAAE var1) {
      return !var1.method166(1355585728) ? null : (Nameable)this.field5843.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Laae;I)Z")
   @ObfuscatedName("bg")
   public final boolean method10267(classAAE var1, int var2) {
      try {
         Nameable var3 = this.method10258(var1, 2014550388);
         if (var3 == null) {
            if (var2 == -315833907) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            method10270(this, var3, -1126613332);
            return true;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rt.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("dr")
   final void method10271(Nameable var1) {
      int var2 = method10292(this, var1, 504360974);
      if (var2 != -1) {
         this.method10304(var2, 2136174790);
         this.method10294(var1, -450794868);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Lrr;")
   @ObfuscatedName("cz")
   Nameable method10275(classAAE var1) {
      return this.method10276(var1, null, (byte)-99);
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;)Lrr;")
   @ObfuscatedName("cw")
   Nameable method10278(classAAE var1, classAAE var2) {
      if (this.method10258(var1, -629464443) != null) {
         throw new IllegalStateException();
      } else {
         Nameable var3 = this.vmethod555(-2036634699);
         var3.method10226(var1, var2, -1663442090);
         this.method10298(var3, -183701127);
         method10302(this, var3, -824594473);
         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;)Lrr;")
   @ObfuscatedName("cj")
   Nameable method10279(classAAE var1, classAAE var2) {
      if (this.method10258(var1, -1260438771) != null) {
         throw new IllegalStateException();
      } else {
         Nameable var3 = this.vmethod555(-1672285238);
         var3.method10226(var1, var2, -2100035047);
         this.method10298(var3, -183701127);
         method10302(this, var3, -824594473);
         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;)Lrr;")
   @ObfuscatedName("ci")
   Nameable method10280(classAAE var1, classAAE var2) {
      if (this.method10258(var1, 2135494846) != null) {
         throw new IllegalStateException();
      } else {
         Nameable var3 = this.vmethod555(464373723);
         var3.method10226(var1, var2, -1907369293);
         this.method10298(var3, -183701127);
         method10302(this, var3, -824594473);
         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bh")
   public int method10246() {
      return 1744080879 * this.field5840;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cm")
   public final void method10285() {
      if (this.field5839 == null) {
         Arrays.sort(this.field5841, 0, this.field5840 * 1744080879);
      } else {
         Arrays.sort(this.field5841, 0, this.field5840 * 1744080879, this.field5839);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;Laae;Laae;)V")
   @ObfuscatedName("dc")
   final void method10287(Nameable var1, classAAE var2, classAAE var3) {
      this.method10294(var1, -190592125);
      var1.method10226(var2, var3, -1805671888);
      method10302(this, var1, -824594473);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;Laae;Laae;)V")
   @ObfuscatedName("dm")
   final void method10288(Nameable var1, classAAE var2, classAAE var3) {
      this.method10294(var1, -517478367);
      var1.method10226(var2, var3, -2129434859);
      method10302(this, var1, -824594473);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)I")
   @ObfuscatedName("di")
   final int method10290(Nameable var1) {
      for (int var2 = 0; var2 < 810101209 * this.field5840; var2++) {
         if (this.field5841[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)I")
   @ObfuscatedName("dy")
   final int method10291(Nameable var1) {
      for (int var2 = 0; var2 < 1744080879 * this.field5840; var2++) {
         if (this.field5841[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   static void method10255(int var0) {
      try {
         classDI.field1509 = 1311180223;
         classDI.field1517 = new short[4][104][104];
         classCO.field1180 = new short[4][104][104];
         classBY.field619 = new byte[4][104][104];
         classDI.field1516 = new byte[4][104][104];
         classDE.field1408 = new int[4][105][105];
         WallObject.field2248 = new byte[4][105][105];
         classDI.field1515 = new int[105][105];
         classAB.field126 = new int[104];
         classCR.field1227 = new int[104];
         client.field1146 = new int[104];
         GraphicsObject.field476 = new int[104];
         classIC.field2939 = new int[104];
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "rt.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("cn")
   final void method10296(Nameable var1) {
      if (this.field5842.remove(var1.field5833) == null) {
         throw new IllegalStateException();
      } else {
         if (var1.field5832 != null) {
            this.field5843.remove(var1.field5832);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("cf")
   final void method10297(Nameable var1) {
      if (this.field5842.remove(var1.field5833) == null) {
         throw new IllegalStateException();
      } else {
         if (var1.field5832 != null) {
            this.field5843.remove(var1.field5832);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bq")
   final void method10304(int var1, int var2) {
      try {
         this.field5840 -= 2063367951;
         if (var1 < this.field5840 * 1744080879) {
            if (var2 <= 2079045159) {
               throw new IllegalStateException();
            }

            System.arraycopy(this.field5841, 1 + var1, this.field5841, var1, 1744080879 * this.field5840 - var1);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("dt")
   final void method10299(Nameable var1) {
      this.field5841[(this.field5840 += 2063367951) * 1744080879 - 1] = var1;
   }

   NameableContainer(int var1) {
      this.field5838 = 1220638031 * var1;
      this.field5841 = this.vmethod558(var1, (byte)2);
      this.field5842 = new HashMap(var1 / 8);
      this.field5843 = new HashMap(var1 / 8);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("du")
   final void method10300(Nameable var1) {
      this.field5841[(this.field5840 += 2063367951) * 1744080879 - 1] = var1;
   }

   public net.runelite.api.Nameable findByName(String var1) {
      return this.method10316(new classAAE(var1, WidgetNode.field5277));
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("de")
   final void method10305(int var1) {
      this.field5840 -= 2063367951;
      if (var1 < this.field5840 * 1744080879) {
         System.arraycopy(this.field5841, 1 + var1, this.field5841, var1, 1744080879 * this.field5840 - var1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;Laae;)Lrr;")
   @ObfuscatedName("pt")
   public static Nameable method10262(NameableContainer var0, classAAE var1) {
      if (var0 == null) {
         var0.method10246();
      }

      return !var1.method166(1073339404) ? null : (Nameable)var0.field5842.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dz")
   final void method10306(int var1) {
      this.field5840 -= 2063367951;
      if (var1 < this.field5840 * 1744080879) {
         System.arraycopy(this.field5841, 1 + var1, this.field5841, var1, 1744080879 * this.field5840 - var1);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dn")
   public final void method10307() {
      this.field5839 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lrt;Lrr;I)I")
   @ObfuscatedName("nc")
   public static int method10292(NameableContainer var0, Nameable var1, int var2) {
      if (var0 == null) {
         return var0.method10293(var1, var2);
      } else {
         try {
            for (int var3 = 0; var3 < 1744080879 * var0.field5840; var3++) {
               if (var0.field5841[var3] == var1) {
                  if (var2 <= 186988688) {
                     throw new IllegalStateException();
                  }

                  return var3;
               }
            }

            return -1;
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "rt.bb(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bc")
   public boolean method10249(int var1) {
      try {
         boolean var10000;
         if (this.field5838 * 488407471 == 1744080879 * this.field5840) {
            if (var1 != 2037364072) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rt.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("do")
   public final void method10308() {
      this.field5839 = null;
   }

   public int getCount() {
      return this.field5840 * 1744080879;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Comparator;)V")
   @ObfuscatedName("dl")
   public final void method10312(Comparator var1) {
      if (null == this.field5839) {
         this.field5839 = var1;
      } else if (this.field5839 instanceof classRD) {
         classRD.method9977((classRD)this.field5839, var1, 1072642345);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)F")
   @ObfuscatedName("ay")
   static float method10239(int var0, int var1, int var2, int var3) {
      try {
         return (float)(var0 - var1) / (var2 - var1);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rt.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bf")
   public final void method10309(int var1) {
      try {
         this.field5839 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rt.bf(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrt;II)Lrr;")
   @ObfuscatedName("hs")
   public static Nameable method10281(NameableContainer var0, int var1, int var2) {
      if (var0 == null) {
         var0.method10283(var1, var1);
      }

      try {
         if (var1 >= 0) {
            if (var2 <= 1259291220) {
               throw new IllegalStateException();
            }

            if (var1 < var0.field5840 * 1744080879) {
               return var0.field5841[var1];
            }
         }

         throw new ArrayIndexOutOfBoundsException(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;Laae;Laae;)V")
   @ObfuscatedName("dd")
   final void method10289(Nameable var1, classAAE var2, classAAE var3) {
      this.method10298(var1, -517478367);
      var1.method10226(var2, var3, -2129434859);
      method10302(this, var1, -824594473);
   }

   @ObfuscatedSignature(descriptor = "(Laae;B)Z")
   @ObfuscatedName("bp")
   public boolean method10254(classAAE var1, byte var2) {
      try {
         if (!var1.method166(392176494)) {
            if (var2 != 10) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (this.field5842.containsKey(var1)) {
            if (var2 != 10) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            return this.field5843.containsKey(var1);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;I)V")
   @ObfuscatedName("bj")
   final void method10272(Nameable var1, int var2) {
      this.method10314(var1);

      try {
         int var3 = method10292(this, var1, 781452173);
         if (var3 == -1) {
            if (var2 >= 1916575490) {
               throw new IllegalStateException();
            }
         } else {
            this.method10304(var3, 2092413899);
            this.method10294(var1, -1160009488);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rt.bj(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;I)Lrr;")
   @ObfuscatedName("ri")
   public static Nameable method10282(NameableContainer var0, int var1) {
      if (var0 == null) {
         var0.method10246();
      }

      if (var1 >= 0 && var1 < var0.field5840 * 1744080879) {
         return var0.field5841[var1];
      } else {
         throw new ArrayIndexOutOfBoundsException(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lrr;")
   @ObfuscatedName("bu")
   public final Nameable method10283(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 <= 1259291220) {
               throw new IllegalStateException();
            }

            if (var1 < this.field5840 * 1744080879) {
               return this.field5841[var1];
            }
         }

         throw new ArrayIndexOutOfBoundsException(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;I)I")
   @ObfuscatedName("bb")
   final int method10293(Nameable var1, int var2) {
      try {
         for (int var3 = 0; var3 < 1744080879 * this.field5840; var3++) {
            if (this.field5841[var3] == var1) {
               if (var2 <= 186988688) {
                  throw new IllegalStateException();
               }

               return var3;
            }
         }

         return -1;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rt.bb(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;Laae;)V")
   @ObfuscatedName("qb")
   public void vmethod566(classAAE var1, classAAE var2) {
   }

   @ObfuscatedSignature(descriptor = "(Lrr;I)V")
   @ObfuscatedName("by")
   final void method10303(Nameable var1, int var2) {
      try {
         this.field5842.put(var1.field5833, var1);
         if (var1.field5832 != null) {
            if (var2 != -824594473) {
               throw new IllegalStateException();
            }

            Nameable var3 = this.field5842.put(var1.field5832, var1);
            if (null != var3) {
               if (var2 != -824594473) {
                  throw new IllegalStateException();
               }

               if (var3 != var1) {
                  var3.field5833 = null;
                  var3.method10229(-1);
               }
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rt.by(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Comparator;I)V")
   @ObfuscatedName("bs")
   public final void method10313(Comparator var1, int var2) {
      try {
         if (null == this.field5839) {
            this.field5839 = var1;
         } else if (this.field5839 instanceof classRD) {
            if (var2 == -324749371) {
               throw new IllegalStateException();
            }

            classRD.method9977((classRD)this.field5839, var1, 1072642345);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rt.bs(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)V")
   @ObfuscatedName("dh")
   final void method10301(Nameable var1) {
      this.field5841[(this.field5840 += 2063367951) * 1744080879 - 1] = var1;
   }
}
