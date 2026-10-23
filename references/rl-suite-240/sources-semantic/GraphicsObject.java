import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GraphicsObjectCreated;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bi")
public final class GraphicsObject extends Renderable implements net.runelite.api.GraphicsObject {
   @ObfuscatedName("aw")
   static int[] field476;
   @ObfuscatedName("as")
   int field470;
   @ObfuscatedName("ol")
   static int field474;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field472 = 3;
   @ObfuscatedName("ax")
   int field468;
   @ObfuscatedName("ab")
   int field469;
   @ObfuscatedSignature(descriptor = "[Laac;")
   @ObfuscatedName("ay")
   public static classAAC[] field477;
   @ObfuscatedSignature(descriptor = "Lqr;")
   @ObfuscatedName("ac")
   final classQR field464;
   @ObfuscatedName("af")
   int field465;
   @ObfuscatedName("ae")
   int field467;
   @ObfuscatedName("ag")
   int field471;
   @ObfuscatedSignature(descriptor = "Lsm;")
   @ObfuscatedName("kv")
   static classSM field475;
   @ObfuscatedSignature(descriptor = "Ldz;")
   @ObfuscatedName("az")
   final WorldView field466;
   @ToRemove(unused = "true")
   @ObfuscatedName("cr")
   static final int field473 = 106;

   public int getStartCycle() {
      return this.field470 * 1835952965;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public static void method1099(int var0) {
      try {
         classOW.field5181.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "bi.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("yb")
   public void method1105() {
      GraphicsObjectCreated var1 = new GraphicsObjectCreated(this);
      classOE.field4841.getCallbacks().post(var1);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ai")
   @Override
   protected boolean vmethod94() {
      if (this.field464.method9772(374233424) && this.field464.method9775((byte)50).method8786(this.field464.method9780(1833310060), 1284412146)) {
         return true;
      } else {
         classOR var1 = classAI.method367(-1841951179 * this.field465, -2130613085);
         Model var2 = classOR.method8760(var1, 123197522);
         return null != var2 && var2.field2442 != null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("ae")
   @Override
   protected final Model vmethod104(int var1) {
      return this.method1112();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("gl")
   public int method1113() {
      return this.field468 * -1285652349;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("df")
   public int method1114() {
      return this.field467 * 1586395561;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aa")
   boolean method1094() {
      return !this.field464.method9772(374233424);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   @Override
   protected boolean vmethod95(int var1) {
      try {
         if (this.field464.method9772(374233424)) {
            if (var1 == -702405562) {
               throw new IllegalStateException();
            }

            if (this.field464.method9775((byte)95).method8786(this.field464.method9780(1810602030), 1284412146)) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         classOR var2 = classAI.method367(-1841951179 * this.field465, -2142576131);
         Model var3 = classOR.method8760(var2, 1685298176);
         if (null == var3 || var3.field2442 == null) {
            return false;
         } else if (var1 == -702405562) {
            throw new IllegalStateException();
         } else {
            return true;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bi.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltt;I)I")
   @ObfuscatedName("vk")
   public static int method1098(classTT var0, int var1) {
      if (var0 == null) {
         var0.method11201(var1, var1);
      }

      return classTT.method11210(var0, var1, 120041229) ? var0.field6228[var1] : 0;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lk")
   public void method1110(int var1) {
      int var3 = var1;
      GraphicsObject var2 = this;
      int var4 = 2001249014;

      try {
         if (var2.method1096(-1464464194)) {
            if (var4 == 347879187) {
               throw new IllegalStateException();
            }
         } else {
            classAT.field277.method280(var2.field466, var2.field467 * 1586395561, -1285652349 * var2.field468, false, 1260699196);
            int var5 = classRD.method9980(var2.field464, var3, classAT.field277, -1252552908);
            classAT.field277.method284(1522032505);
            if (0 != (var5 & 1)) {
               if (var4 == 347879187) {
                  throw new IllegalStateException();
               }

               classQR.method9765(var2.field464, -1474253031);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "bi.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   @Override
   protected int vmethod98(int var1) {
      try {
         int var10000;
         if (this.field464.method9772(374233424)) {
            if (var1 <= -1498749565) {
               throw new IllegalStateException();
            }

            var10000 = this.field464.method9775((byte)1).field5136 * -687960895;
         } else {
            var10000 = 0;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bi.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ri")
   public Model method1112() {
      GraphicsObject var1 = this;
      int var2 = -891174633;

      try {
         Model var10000;
         if (var1.field464.method9805(30, -1109955801)) {
            if (var2 <= -1767431455) {
               throw new IllegalStateException();
            }

            var1 = null;
            var10000 = var1;
         } else {
            classOR var3 = classAI.method367(var1.field465 * -1841951179, -1696391316);
            Model var4 = var3.method8757(var1.field464.method9780(1922448585), 1593421514);
            if (var4 == null) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var1 = null;
               var10000 = var1;
            } else {
               var10000 = var4;
            }
         }

         return var10000;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "bi.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ac")
   boolean method1095() {
      return !this.field464.method9772(374233424);
   }

   public LocalPoint getLocation() {
      return new LocalPoint(this.method1114(), this.method1113(), this.field466);
   }

   GraphicsObject(WorldView var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      this.field464 = new classQR();
      this.field466 = var1;
      this.field465 = 1497060381 * var2;
      this.field471 = 1755098239 * var3;
      this.field467 = var4 * 1654733465;
      this.field468 = -776090069 * var5;
      this.field469 = var6 * 475390849;
      this.field470 = 2008838029 * (var8 + var7);
      classQR.method9770(this.field464, classAI.method367(-1841951179 * this.field465, -2033763623).field5081 * 696329207, -2091936876);
      this.method1105();
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("az")
   boolean method1096(int var1) {
      try {
         boolean var10000;
         if (!this.field464.method9772(374233424)) {
            if (var1 >= -57176573) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bi.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   @Override
   protected int vmethod99() {
      return this.field464.method9772(374233424) ? this.field464.method9775((byte)35).field5136 * -125401106 : 0;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aj")
   final void method1100(int var1) {
      if (!this.method1096(-999939569)) {
         classAT.field277.method280(this.field466, this.field467 * 1586395561, -1285652349 * this.field468, false, -1241447601);
         int var2 = classRD.method9980(this.field464, var1, classAT.field277, -446443166);
         classAT.field277.method284(-1884292736);
         if (0 != (var2 & 1)) {
            classQR.method9765(this.field464, 1711434617);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ii")
   public void method1115(int var1) {
      this.field467 = var1 * 1654733465;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ad")
   @Override
   protected final Model vmethod92() {
      if (this.field464.method9805(30, -1136562440)) {
         return null;
      } else {
         classOR var1 = classAI.method367(this.field465 * 2138398622, -1757120346);
         Model var2 = var1.method8757(this.field464.method9780(2102348236), 1763791194);
         return var2 == null ? null : var2;
      }
   }

   public int getLevel() {
      return this.field471 * 1040651135;
   }

   public boolean finished() {
      return this.field464.field5597 == null;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ap")
   @Override
   protected final Model vmethod93() {
      if (this.field464.method9805(30, -885166570)) {
         return null;
      } else {
         classOR var1 = classAI.method367(this.field465 * -1841951179, -1632774000);
         Model var2 = var1.method8757(this.field464.method9780(1930803832), 1684359103);
         return var2 == null ? null : var2;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   @Override
   protected boolean vmethod96() {
      if (this.field464.method9772(374233424) && this.field464.method9775((byte)114).method8786(this.field464.method9780(1733913483), 1284412146)) {
         return true;
      } else {
         classOR var1 = classAI.method367(-1841951179 * this.field465, -1532281291);
         Model var2 = classOR.method8760(var1, 595564444);
         return null != var2 && var2.field2442 != null;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILlw;Llw;B)Llw;")
   @ObfuscatedName("ao")
   static Widget method1106(int var0, Widget var1, Widget var2, byte var3) {
      try {
         int var4 = 0;
         Widget var5 = null;

         for (int var6 = 1 + 63131743 * var2.field4258; var6 < var1.field4338.length; var6++) {
            if (var3 != 0) {
               throw new IllegalStateException();
            }

            Widget var7 = var1.field4338[var6];
            if (null == var7) {
               if (var3 != 0) {
                  throw new IllegalStateException();
               }
            } else if (var7.field4261 * 653784551 == var2.field4258 * 63131743) {
               if (++var4 - 1 == var0) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  var5 = var7;
                  break;
               }
            }
         }

         return var5;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "bi.ao(" + ')');
      }
   }

   public void setFinished(boolean var1) {
      if (!var1) {
         throw new UnsupportedOperationException();
      } else {
         this.field464.method9826();
      }
   }

   @ObfuscatedSignature(descriptor = "()Ldz;")
   @ObfuscatedName("gr")
   public WorldView method1116() {
      return this.field466;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ar")
   @Override
   protected boolean vmethod97() {
      if (this.field464.method9772(374233424) && this.field464.method9775((byte)99).method8786(this.field464.method9780(2000766998), 1284412146)) {
         return true;
      } else {
         classOR var1 = classAI.method367(-1841951179 * this.field465, -1286288334);
         Model var2 = classOR.method8760(var1, -1408799262);
         return null != var2 && var2.field2442 != null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aq")
   final void method1101(int var1) {
      if (!this.method1096(-1279099477)) {
         classAT.field277.method280(this.field466, this.field467 * 406257313, 1533760510 * this.field468, false, -1378138958);
         int var2 = classRD.method9980(this.field464, var1, classAT.field277, -192061079);
         classAT.field277.method284(1580106184);
         if (0 != (var2 & 1)) {
            classQR.method9765(this.field464, -1721841807);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   @Override
   protected int vmethod100() {
      return this.field464.method9772(374233424) ? this.field464.method9775((byte)24).field5136 * -687960895 : 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   @Override
   protected int vmethod101() {
      return this.field464.method9772(374233424) ? this.field464.method9775((byte)94).field5136 * -687960895 : 0;
   }

   @ObfuscatedSignature(descriptor = "()Lou;")
   @ObfuscatedName("im")
   public Animation method1111() {
      return this.field464.field5597;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   @Override
   protected int vmethod102() {
      return this.field464.method9772(374233424) ? this.field464.method9775((byte)103).field5136 * -687960895 : 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbi;I)V")
   @ObfuscatedName("en")
   public static void method1102(GraphicsObject var0, int var1) {
      if (var0 == null) {
         var0.method1101(var1);
      } else if (!var0.method1096(-997627355)) {
         classAT.field277.method280(var0.field466, var0.field467 * 1586395561, -1285652349 * var0.field468, false, -416704557);
         int var2 = classRD.method9980(var0.field464, var1, classAT.field277, -853080321);
         classAT.field277.method284(107381215);
         if (0 != (var2 & 1)) {
            classQR.method9765(var0.field464, -1105023259);
         }
      }
   }

   public int getId() {
      return this.field465 * -1841951179;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   @Override
   protected int vmethod103() {
      return this.field464.method9772(374233424) ? this.field464.method9775((byte)112).field5136 * -687960895 : 0;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ai")
   static void method1108(int var0) {
      try {
         classDJ.method3370(client.field796, -720419209);
         client.field796.field1527 = 0;
         client.field796.field1521.field6953 = 0;
         client.field796.field1518 = null;
         client.field796.field1531 = null;
         client.field796.field1530 = null;
         client.field796.field1529 = null;
         client.field796.field1523 = 0;
         client.field796.field1522 = 0;
         client.field940 = 0;
         client.field881 = "";
         AABB.method4937(-698578324);
         classBA.field305 = 0;
         client.field860 = 0;
         classIS.field3051.method3743(-1008620329);
         ItemContainer.field454 = new HashTable(32);
         classJB.method6386(30, -1192966987);
         if (client.field796 != null && client.field796.field1533 != null) {
            classJL var1 = classEF.method3863(classJS.WINDOW_STATUS, client.field796.field1533, (byte)-43);
            classXY.method13029(var1.field3341, classLH.method7145(-2145629943), (byte)-76);
            var1.field3341.method13031(classSA.field5895 * 773246731, -1542382992);
            var1.field3341.method13031(IntProjection.field1936 * 8379747, -1542382992);
            client.field796.method3374(var1, -1301097035);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bi.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ai")
   static final int method1107(int var0, int var1, int var2) {
      try {
         int var3 = classAK.method425(var0 - 1, var1 - 1, 1802280853)
            + classAK.method425(1 + var0, var1 - 1, 1573622702)
            + classAK.method425(var0 - 1, 1 + var1, 1808549159)
            + classAK.method425(1 + var0, var1 + 1, 1339759405);
         int var4 = classAK.method425(var0 - 1, var1, 2048099358)
            + classAK.method425(1 + var0, var1, 1397693901)
            + classAK.method425(var0, var1 - 1, 1404031470)
            + classAK.method425(var0, 1 + var1, 1594740970);
         int var5 = classAK.method425(var0, var1, 2085991794);
         return var3 / 16 + var4 / 8 + var5 / 4;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "bi.ai(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("so")
   public void method1117(int var1) {
      this.field468 = var1 * -776090069;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ay")
   final void method1103(int var1) {
      if (!this.method1096(-1969342095)) {
         classAT.field277.method280(this.field466, this.field467 * 1586395561, -1285652349 * this.field468, false, -1844648843);
         int var2 = classRD.method9980(this.field464, var1, classAT.field277, -381287340);
         classAT.field277.method284(-1658017930);
         if (0 != (var2 & 1)) {
            classQR.method9765(this.field464, -674118334);
         }
      }
   }

   public int getAnimationFrame() {
      return this.field464.field5600 * 292569817;
   }

   public int getZ() {
      return this.field469 * -1107927935;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqn;Lzr;)V")
   @ObfuscatedName("lt")
   public static void method1093(classQN var0, ScriptEvent var1) {
      var0.field5566.method8196(var1);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("af")
   final void method1109(int var1, int var2) {
      this.method1110(var1);
   }

   public GraphicsObject() {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;)I")
   @ObfuscatedName("rg")
   public static int method1104(WorldMapData var0) {
      return var0.field2732.field4055 * -210579119;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ao")
   boolean method1097() {
      return !this.field464.method9772(374233424);
   }
}
