import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("np")
public class classNP {
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ax")
   public static IndexDataBase field4703;
   @ObfuscatedName("af")
   public static ArrayList field4702 = null;
   @ObfuscatedName("ae")
   public static LinkedList field4695 = new LinkedList();
   @ObfuscatedName("ab")
   public static ArrayList field4696 = new ArrayList(3);
   @ObfuscatedName("ac")
   static ArrayList field4697 = new ArrayList(3);
   @ObfuscatedName("az")
   public static final List field4693 = new ArrayList();
   @ObfuscatedName("aa")
   static ArrayList field4698 = new ArrayList();
   @ObfuscatedName("ao")
   static int field4701 = 0;
   @ObfuscatedName("al")
   static int field4700 = 0;
   @ObfuscatedName("aj")
   static int field4694 = 0;
   @ObfuscatedName("ay")
   static int field4699 = 0;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("an")
   public static void method7998(int var0, int var1) {
      classIF.method6247(var0, var1, 0, 0, (byte)-65);
      field4697.clear();
      field4698.clear();
      if (!field4696.isEmpty() && (0 != var0 || var1 != 0)) {
         field4698.add(new classWU(null, field4701 * -1331669075));
         field4698.add(new classWO(null, 0, false, 1784906769 * field4700));
         ArrayList var3 = new ArrayList();

         for (MidiRequest var5 : field4696) {
            var3.add(var5);
         }

         field4698.add(new classWL(null, var3));
      } else {
         classGK.method5517(1382397317);
      }
   }

   classNP() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ad")
   public static void method7990(int var0, int var1) {
      for (classNQ var3 : field4693) {
         var3.vmethod246(var0, var1, -688853523);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Z")
   @ObfuscatedName("yc")
   public static boolean method8011(classLH var0, int var1) {
      if (var0 == null) {
         var0.method6870(var1);
      }

      if (var0.method7137(var1, -32389191)) {
         var0.method6924(455461300);
         classZP var2 = var0.field4157.method13918((char)var1, -698145269 * var0.field4155, var0.field4153 * 1030611083, (byte)23);
         var0.method6930(classZP.method14129(var2, -1567180497), classZP.method14129(var2, 717730848), 1122842785);
         var0.method7111((byte)-47);
         classLH.method7133(var0, (byte)65);
      }

      return true;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ap")
   public static void method7991(int var0, int var1) {
      for (classNQ var3 : field4693) {
         var3.vmethod246(var0, var1, 1735933590);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/ArrayList;IIIIZ)V")
   @ObfuscatedName("au")
   public static void method7992(ArrayList var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (!var0.isEmpty()) {
         field4695.clear();
         field4698.clear();
         if (var5) {
            classGK.method5517(835587696);
         } else {
            for (int var6 = 0; var6 < field4696.size(); var6++) {
               MidiRequest var7 = (MidiRequest)field4696.get(var6);
               if (var7 == null) {
                  field4696.remove(var6);
                  var6--;
               } else if (var7.field4631) {
                  if (917328323 * var7.field4639.field4749 > 0) {
                     var7.field4639.field4749 -= 201370557;
                  }

                  var7.field4639.method8079((byte)-58);
                  var7.field4639.method8066((byte)0);
                  var7.field4639.method8048(0, -1889816888);
                  field4696.remove(var6);
                  var6--;
               } else {
                  var7.field4631 = true;
               }
            }
         }

         if (!var5) {
            field4697.clear();
         }

         for (MidiRequest var14 : var0) {
            if (-1 != 1942111947 * var14.field4630 && 1846277290 * var14.field4628 != -1) {
               if (!var5) {
                  field4697.add(var14);
               }

               field4695.add(var14);
            }
         }

         if (!field4695.isEmpty()) {
            classIF.method6247(var1, var2, var3, var4, (byte)-128);
            field4698.add(new classWN(null));
            field4698.add(new classWS(null, classIA.field2925, classRM.field5807, field4703));
            ArrayList var13 = new ArrayList();
            var13.add(new classWK(new classWP(null, 0, true, field4699 * 396217257)));
            if (!field4696.isEmpty()) {
               ArrayList var15 = new ArrayList();
               var15.add(new classWU(new classWQ(null, var13), -1350272915 * field4694));
               ArrayList var9 = new ArrayList();

               for (MidiRequest var11 : field4696) {
                  var9.add(var11);
               }

               var15.add(new classWU(new classWO(new classWL(null, var9), 0, false, 1784906769 * field4700), -1331669075 * field4701));
               field4698.add(new classWQ(null, var15));
            } else {
               field4698.add(new classWU(null, 1716922079 * field4694));
               field4698.add(new classWQ(null, var13));
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bw")
   public static boolean method8015() {
      boolean var0 = false;
      boolean var1 = false;
      if (!field4698.isEmpty()) {
         classWT var2 = (classWT)field4698.get(0);
         if (var2 == null) {
            field4698.remove(0);
         } else if (var2.vmethod627((byte)93)) {
            if (var2.method12682(-360357532)) {
               System.out.println("Error in midimanager.service: " + var2.method12687(-596400562));
               var0 = true;
            } else {
               if (var2.method12690((short)-13712) != null) {
                  field4698.add(1, var2.method12690((short)11550));
               }

               var1 = var2.method12683((byte)63);
            }

            field4698.remove(0);
         } else {
            var1 = var2.method12683((byte)35);
         }
      }

      if (var0) {
         field4698.clear();
         classGK.method5517(1234715067);
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/ArrayList;IIIIZ)V")
   @ObfuscatedName("ar")
   public static void method7993(ArrayList var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (!var0.isEmpty()) {
         field4695.clear();
         field4698.clear();
         if (var5) {
            classGK.method5517(2126279217);
         } else {
            for (int var6 = 0; var6 < field4696.size(); var6++) {
               MidiRequest var7 = (MidiRequest)field4696.get(var6);
               if (var7 == null) {
                  field4696.remove(var6);
                  var6--;
               } else if (var7.field4631) {
                  if (917328323 * var7.field4639.field4749 > 0) {
                     var7.field4639.field4749 -= 16852715;
                  }

                  var7.field4639.method8079((byte)64);
                  var7.field4639.method8066((byte)0);
                  var7.field4639.method8048(0, -1531645374);
                  field4696.remove(var6);
                  var6--;
               } else {
                  var7.field4631 = true;
               }
            }
         }

         if (!var5) {
            field4697.clear();
         }

         for (MidiRequest var14 : var0) {
            if (-1 != 1942111947 * var14.field4630 && 450234221 * var14.field4628 != -1) {
               if (!var5) {
                  field4697.add(var14);
               }

               field4695.add(var14);
            }
         }

         if (!field4695.isEmpty()) {
            classIF.method6247(var1, var2, var3, var4, (byte)-32);
            field4698.add(new classWN(null));
            field4698.add(new classWS(null, classIA.field2925, classRM.field5807, field4703));
            ArrayList var13 = new ArrayList();
            var13.add(new classWK(new classWP(null, 0, true, field4699 * 396217257)));
            if (!field4696.isEmpty()) {
               ArrayList var15 = new ArrayList();
               var15.add(new classWU(new classWQ(null, var13), -1350272915 * field4694));
               ArrayList var9 = new ArrayList();

               for (MidiRequest var11 : field4696) {
                  var9.add(var11);
               }

               var15.add(new classWU(new classWO(new classWL(null, var9), 0, false, 1784906769 * field4700), -1331669075 * field4701));
               field4698.add(new classWQ(null, var15));
            } else {
               field4698.add(new classWU(null, -1350272915 * field4694));
               field4698.add(new classWQ(null, var13));
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aw")
   public static void method7995(int var0) {
      if (!field4696.isEmpty()) {
         for (MidiRequest var2 : field4696) {
            if (null != var2) {
               var2.field4633 = var0 * -1016653583;
            }
         }

         MidiRequest var3 = (MidiRequest)field4696.get(0);
         if (var3 != null && null != var3.field4639 && classNU.method8085(var3.field4639, (byte)10) && !var3.field4632) {
            var3.field4639.method8048(var0, -994327294);
            var3.field4629 = var0;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   static void method8022() {
      for (MidiRequest var1 : field4696) {
         if (null != var1) {
            var1.field4639.method8079((byte)-13);
            var1.field4639.method8066((byte)0);
            var1.field4639.method8048(0, -1035531065);
            var1.field4639.field4749 = 0;
            classAC.method311(var1.field4630 * 1942111947, var1.field4628 * 450234221, 1655233109);
         }
      }

      field4696.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("av")
   public static void method7996(int var0) {
      if (!field4696.isEmpty()) {
         for (MidiRequest var2 : field4696) {
            if (null != var2) {
               var2.field4633 = var0 * -1016653583;
            }
         }

         MidiRequest var3 = (MidiRequest)field4696.get(0);
         if (var3 != null && null != var3.field4639 && classNU.method8085(var3.field4639, (byte)10) && !var3.field4632) {
            var3.field4639.method8048(var0, -1178039687);
            var3.field4629 = var0;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bc")
   public static boolean method8004(int var0) {
      if (field4697.isEmpty()) {
         return false;
      } else {
         MidiRequest var1 = (MidiRequest)field4697.get(0);
         return var1 != null && var0 == -213933842 * var1.field4630;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("by")
   static void method8018(int var0, int var1, int var2, int var3) {
      field4701 = var0 * 173745189;
      field4700 = -1367216911 * var1;
      field4694 = -269856411 * var2;
      field4699 = -1506539367 * var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("am")
   public static void method7999(int var0, int var1) {
      classIF.method6247(var0, var1, 0, 0, (byte)-15);
      field4697.clear();
      field4698.clear();
      if (!field4696.isEmpty() && (0 != var0 || var1 != 0)) {
         field4698.add(new classWU(null, field4701 * -1331669075));
         field4698.add(new classWO(null, 0, false, 1784906769 * field4700));
         ArrayList var3 = new ArrayList();

         for (MidiRequest var5 : field4696) {
            var3.add(var5);
         }

         field4698.add(new classWL(null, var3));
      } else {
         classGK.method5517(1000015836);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ah")
   public static boolean method8001() {
      if (!field4698.isEmpty()) {
         return true;
      } else {
         return !field4696.isEmpty() && field4696.get(0) != null && ((MidiRequest)field4696.get(0)).field4639 != null
            ? classNU.method8085(((MidiRequest)field4696.get(0)).field4639, (byte)10)
            : false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bn")
   public static void method8002(int var0, int var1, int var2, int var3) {
      if (field4696.size() > 1
         && field4696.get(0) != null
         && classNU.method8085(((MidiRequest)field4696.get(0)).field4639, (byte)10)
         && field4696.get(1) != null
         && classNU.method8085(((MidiRequest)field4696.get(1)).field4639, (byte)10)) {
         classIF.method6247(var0, var1, var2, var3, (byte)-37);
         field4698.add(new classWA(null));
         ArrayList var4 = new ArrayList();
         var4.add(new classWU(new classWP(null, 1, false, field4699 * 396217257), field4694 * 240984315));
         var4.add(new classWU(new classWO(null, 0, false, field4700 * 1784906769), field4701 * 1604098676));
         field4698.add(new classWQ(null, var4));
         if (field4697.get(0) != null && field4697.get(1) != null) {
            MidiRequest var5 = (MidiRequest)field4697.get(0);
            field4697.set(0, field4696.get(1));
            field4697.set(1, var5);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bx")
   public static void method8003(int var0, int var1, int var2, int var3) {
      if (field4696.size() > 1
         && field4696.get(0) != null
         && classNU.method8085(((MidiRequest)field4696.get(0)).field4639, (byte)10)
         && field4696.get(1) != null
         && classNU.method8085(((MidiRequest)field4696.get(1)).field4639, (byte)10)) {
         classIF.method6247(var0, var1, var2, var3, (byte)-64);
         field4698.add(new classWA(null));
         ArrayList var4 = new ArrayList();
         var4.add(new classWU(new classWP(null, 1, false, field4699 * 396217257), field4694 * -1350272915));
         var4.add(new classWU(new classWO(null, 0, false, field4700 * 1784906769), field4701 * -1331669075));
         field4698.add(new classWQ(null, var4));
         if (field4697.get(0) != null && field4697.get(1) != null) {
            MidiRequest var5 = (MidiRequest)field4697.get(0);
            field4697.set(0, field4696.get(1));
            field4697.set(1, var5);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bp")
   public static boolean method8005(int var0) {
      if (field4697.isEmpty()) {
         return false;
      } else {
         MidiRequest var1 = (MidiRequest)field4697.get(0);
         return var1 != null && var0 == 433890436 * var1.field4630;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("br")
   public static boolean method8006(int var0) {
      if (field4697.isEmpty()) {
         return false;
      } else {
         MidiRequest var1 = (MidiRequest)field4697.get(0);
         return var1 != null && var0 == 1942111947 * var1.field4630;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bm")
   public static boolean method8007() {
      return !field4697.isEmpty();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("bo")
   public static void method8012(IndexDataBase var0, int var1) {
      if (!field4697.isEmpty()) {
         ArrayList var2 = new ArrayList();

         for (MidiRequest var4 : field4697) {
            var4.field4641 = false;
            var4.field4642 = false;
            var4.field4632 = false;
            var4.field4631 = false;
            var4.field4640 = var0;
            var4.field4633 = var1 * -1016653583;
            var4.field4629 = 0.0F;
            var2.add(var4);
         }

         classIJ.method6258(var2, field4701 * -1331669075, field4700 * 1784906769, -1350272915 * field4694, field4699 * 396217257, false, -2056553734);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   public static void method8009() {
      field4697.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bj")
   public static void method8010() {
      field4697.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("bk")
   public static void method8013(IndexDataBase var0, int var1) {
      if (!field4697.isEmpty()) {
         ArrayList var2 = new ArrayList();

         for (MidiRequest var4 : field4697) {
            var4.field4641 = false;
            var4.field4642 = false;
            var4.field4632 = false;
            var4.field4631 = false;
            var4.field4640 = var0;
            var4.field4633 = var1 * -1942414620;
            var4.field4629 = 0.0F;
            var2.add(var4);
         }

         classIJ.method6258(var2, field4701 * -1331669075, field4700 * 1784906769, -1350272915 * field4694, field4699 * 1796004071, false, 482923099);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ak")
   public static void method7997(int var0) {
      if (!field4696.isEmpty()) {
         for (MidiRequest var2 : field4696) {
            if (null != var2) {
               var2.field4633 = var0 * -1016653583;
            }
         }

         MidiRequest var3 = (MidiRequest)field4696.get(0);
         if (var3 != null && null != var3.field4639 && classNU.method8085(var3.field4639, (byte)10) && !var3.field4632) {
            var3.field4639.method8048(var0, -1847458023);
            var3.field4629 = var0;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("bu")
   public static void method8014(IndexDataBase var0, int var1) {
      if (!field4697.isEmpty()) {
         ArrayList var2 = new ArrayList();

         for (MidiRequest var4 : field4697) {
            var4.field4641 = false;
            var4.field4642 = false;
            var4.field4632 = false;
            var4.field4631 = false;
            var4.field4640 = var0;
            var4.field4633 = var1 * 2017941029;
            var4.field4629 = 0.0F;
            var2.add(var4);
         }

         classIJ.method6258(var2, field4701 * -1331669075, field4700 * 1784906769, -1350272915 * field4694, field4699 * 396217257, false, -946454553);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("at")
   public static void method8000(int var0, int var1) {
      classIF.method6247(var0, var1, 0, 0, (byte)-69);
      field4697.clear();
      field4698.clear();
      if (!field4696.isEmpty() && (0 != var0 || var1 != 0)) {
         field4698.add(new classWU(null, field4701 * -1331669075));
         field4698.add(new classWO(null, 0, false, 1784906769 * field4700));
         ArrayList var3 = new ArrayList();

         for (MidiRequest var5 : field4696) {
            var3.add(var5);
         }

         field4698.add(new classWL(null, var3));
      } else {
         classGK.method5517(927120993);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bt")
   public static boolean method8008() {
      return !field4697.isEmpty();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bb")
   public static boolean method8016() {
      boolean var0 = false;
      boolean var1 = false;
      if (!field4698.isEmpty()) {
         classWT var2 = (classWT)field4698.get(0);
         if (var2 == null) {
            field4698.remove(0);
         } else if (var2.vmethod627((byte)77)) {
            if (var2.method12682(345700378)) {
               System.out.println("Error in midimanager.service: " + var2.method12687(564176047));
               var0 = true;
            } else {
               if (var2.method12690((short)1116) != null) {
                  field4698.add(1, var2.method12690((short)15669));
               }

               var1 = var2.method12683((byte)58);
            }

            field4698.remove(0);
         } else {
            var1 = var2.method12683((byte)70);
         }
      }

      if (var0) {
         field4698.clear();
         classGK.method5517(964546712);
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bi")
   static void method8019(int var0, int var1, int var2, int var3) {
      field4701 = var0 * 173745189;
      field4700 = -1367216911 * var1;
      field4694 = -269856411 * var2;
      field4699 = -1506539367 * var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("be")
   static void method8020(int var0, int var1, int var2, int var3) {
      field4701 = var0 * -345384078;
      field4700 = -1367216911 * var1;
      field4694 = 1764561522 * var2;
      field4699 = -1506539367 * var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/ArrayList;IIIIZ)V")
   @ObfuscatedName("ai")
   public static void method7994(ArrayList var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (!var0.isEmpty()) {
         field4695.clear();
         field4698.clear();
         if (var5) {
            classGK.method5517(1543291022);
         } else {
            for (int var6 = 0; var6 < field4696.size(); var6++) {
               MidiRequest var7 = (MidiRequest)field4696.get(var6);
               if (var7 == null) {
                  field4696.remove(var6);
                  var6--;
               } else if (var7.field4631) {
                  if (718030205 * var7.field4639.field4749 > 0) {
                     var7.field4639.field4749 -= 16852715;
                  }

                  var7.field4639.method8079((byte)21);
                  var7.field4639.method8066((byte)0);
                  var7.field4639.method8048(0, -1283171848);
                  field4696.remove(var6);
                  var6--;
               } else {
                  var7.field4631 = true;
               }
            }
         }

         if (!var5) {
            field4697.clear();
         }

         for (MidiRequest var14 : var0) {
            if (-1 != 1942111947 * var14.field4630 && 450234221 * var14.field4628 != -1) {
               if (!var5) {
                  field4697.add(var14);
               }

               field4695.add(var14);
            }
         }

         if (!field4695.isEmpty()) {
            classIF.method6247(var1, var2, var3, var4, (byte)6);
            field4698.add(new classWN(null));
            field4698.add(new classWS(null, classIA.field2925, classRM.field5807, field4703));
            ArrayList var13 = new ArrayList();
            var13.add(new classWK(new classWP(null, 0, true, field4699 * -300244770)));
            if (!field4696.isEmpty()) {
               ArrayList var15 = new ArrayList();
               var15.add(new classWU(new classWQ(null, var13), 1180751192 * field4694));
               ArrayList var9 = new ArrayList();

               for (MidiRequest var11 : field4696) {
                  var9.add(var11);
               }

               var15.add(new classWU(new classWO(new classWL(null, var9), 0, false, 1784906769 * field4700), -1331669075 * field4701));
               field4698.add(new classWQ(null, var15));
            } else {
               field4698.add(new classWU(null, -193149295 * field4694));
               field4698.add(new classWQ(null, var13));
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/ArrayList;IIII)V")
   @ObfuscatedName("bq")
   public static void method8021(ArrayList var0, int var1, int var2, int var3, int var4) {
      field4697.clear();
      field4697.addAll(var0);
      classIF.method6247(var1, var2, var3, var4, (byte)-32);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ba")
   public static boolean method8017() {
      boolean var0 = false;
      boolean var1 = false;
      if (!field4698.isEmpty()) {
         classWT var2 = (classWT)field4698.get(0);
         if (var2 == null) {
            field4698.remove(0);
         } else if (var2.vmethod627((byte)32)) {
            if (var2.method12682(1287677302)) {
               System.out.println("Error in midimanager.service: " + var2.method12687(537966508));
               var0 = true;
            } else {
               if (var2.method12690((short)10676) != null) {
                  field4698.add(1, var2.method12690((short)16321));
               }

               var1 = var2.method12683((byte)88);
            }

            field4698.remove(0);
         } else {
            var1 = var2.method12683((byte)58);
         }
      }

      if (var0) {
         field4698.clear();
         classGK.method5517(1710909786);
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   static void method8023() {
      for (MidiRequest var1 : field4696) {
         if (null != var1) {
            var1.field4639.method8079((byte)44);
            var1.field4639.method8066((byte)0);
            var1.field4639.method8048(0, -602009511);
            var1.field4639.field4749 = 0;
            classAC.method311(var1.field4630 * 1942111947, var1.field4628 * 450234221, 1430970316);
         }
      }

      field4696.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   static void method8024() {
      for (MidiRequest var1 : field4696) {
         if (null != var1) {
            var1.field4639.method8079((byte)-28);
            var1.field4639.method8066((byte)0);
            var1.field4639.method8048(0, -1667497089);
            var1.field4639.field4749 = 0;
            classAC.method311(var1.field4630 * 175373375, var1.field4628 * -1101584675, 364487195);
         }
      }

      field4696.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bl")
   static void method8025() {
      for (MidiRequest var1 : field4696) {
         if (null != var1) {
            var1.field4639.method8079((byte)52);
            var1.field4639.method8066((byte)0);
            var1.field4639.method8048(0, -734783711);
            var1.field4639.field4749 = 0;
            classAC.method311(var1.field4630 * 1942111947, var1.field4628 * 450234221, 1389647141);
         }
      }

      field4696.clear();
   }
}
