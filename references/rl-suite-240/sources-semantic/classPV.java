import java.util.ArrayList;
import java.util.List;
import net.runelite.api.MenuEntry;
import net.runelite.api.Skill;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.StatChanged;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pv")
public class classPV {
   @ObfuscatedName("al")
   int field5431;
   @ObfuscatedName("ae")
   int field5430;
   @ObfuscatedName("ag")
   int field5425;
   @ObfuscatedName("aa")
   int field5429;
   @ObfuscatedName("ai")
   int field5438;
   @ObfuscatedName("as")
   final int[] field5426;
   @ObfuscatedName("ax")
   int field5427;
   @ObfuscatedName("ac")
   final int[] field5428;
   @ObfuscatedName("ad")
   int field5424;
   @ObfuscatedName("ao")
   final int[] field5435;
   @ObfuscatedName("ab")
   final int[] field5439;
   @ObfuscatedName("aj")
   int field5433;
   @ObfuscatedName("ay")
   int field5436;
   @ObfuscatedName("aq")
   int field5432;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field5440 = 31;
   @ObfuscatedName("ap")
   int field5437;
   @ObfuscatedName("au")
   int field5423;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5441 = 32;
   @ObfuscatedName("ar")
   int field5434;
   @ObfuscatedName("sc")
   public final List field5422 = new ArrayList();
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   public static final int field5442 = 1024;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cm")
   int method9335() {
      return 186634899 * this.field5433;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   public void method9262(int var1) {
      try {
         this.field5430 += 2070229217;
         this.method9368();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cu")
   public void method9302() {
      this.field5438 = this.field5430 * -1807734201;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ae")
   public void method9272(int var1, byte var2) {
      this.method9370(var1);

      try {
         this.field5428[(this.field5429 += -612394997) * -362269789 - 1 & 31] = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pv.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bm")
   public void method9265(int var1) {
      this.field5439[(this.field5425 += 1362128893) * 1886152021 - 1 & 31] = var1;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ag")
   public void method9278(int var1, int var2) {
      try {
         this.field5435[(this.field5431 += -75721583) * -1415467919 - 1 & 31] = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pv.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ca")
   int method9319() {
      return this.field5425 * 1886152021;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("aa")
   public void method9294(byte var1) {
      try {
         this.field5424 = -699888003 * this.field5430;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpv;I)V")
   @ObfuscatedName("ep")
   public static void method9290(classPV var0, int var1) {
      if (var0 == null) {
         var0.method9293(var1);
      } else {
         try {
            var0.field5432 = -312040537 * var0.field5430;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "pv.ac(" + ')');
         }
      }
   }

   public classPV() {
      this.field5430 = 2070229217;
      this.field5439 = new int[32];
      this.field5425 = 0;
      this.field5426 = new int[32];
      this.field5427 = 0;
      this.field5428 = new int[32];
      this.field5429 = 0;
      this.field5435 = new int[32];
      this.field5431 = 0;
      this.field5433 = 0;
      this.field5436 = 0;
      this.field5432 = 0;
      this.field5424 = 0;
      this.field5437 = 0;
      this.field5423 = 0;
      this.field5438 = 0;
      this.field5434 = 0;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aj")
   public void method9303(int var1) {
      try {
         this.field5438 = this.field5430 * -1807734201;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ad")
   int method9315(int var1) {
      return this.field5439[var1 & 31];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dh")
   int method9347() {
      return 1056232063 * this.field5437;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cy")
   int method9327(int var1) {
      try {
         return this.field5427 * -634175101;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.ar(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcd;I)V")
   @ObfuscatedName("ck")
   public static void method9264(classCD var0, int var1) {
      if (var0 == null) {
         var0.method1421(var1);
      }

      var0.field674 = var1 * 1378784235;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dy")
   int method9343() {
      return this.field5424 * 1331129227;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("rx")
   public void method9371(int var1) {
      this.method9272(var1, (byte)57);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cn")
   int method9332(int var1) {
      try {
         return -362269789 * this.field5429;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.ak(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   int method9336(int var1) {
      try {
         return 186634899 * this.field5433;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.av(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cg")
   int method9339(int var1) {
      try {
         return this.field5436 * -120272575;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.at(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("av")
   int method9342(int var1) {
      try {
         return -1113846793 * this.field5432;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.an(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpv;I)V")
   @ObfuscatedName("xs")
   public static void method9273(classPV var0, int var1) {
      if (var0 == null) {
         var0.method9282(var1);
      }

      var0.field5428[(var0.field5429 += -612394997) * -362269789 - 1 & 31] = var1;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("af")
   public void method9266(int var1, byte var2) {
      try {
         this.field5439[(this.field5425 += 1362128893) * 1886152021 - 1 & 31] = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pv.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cj")
   int method9333() {
      return -1951985883 * this.field5429;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("bx")
   int method9358(byte var1) {
      try {
         return this.field5438 * 718414679;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.bx(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ak")
   int method9363(int var1) {
      try {
         return 1796613727 * this.field5434;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.bc(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bp")
   public void method9263() {
      this.field5430 += -1382978234;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dd")
   int method9340() {
      return this.field5436 * -120272575;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bt")
   public void method9267(int var1) {
      this.field5439[(this.field5425 += 1955907487) * 1886152021 - 1 & 31] = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cv")
   public void method9309() {
      this.field5425 += 638451616;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dr")
   int method9348() {
      return 1056232063 * this.field5437;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bk")
   public void method9274(int var1) {
      this.field5428[(this.field5429 += 1787332678) * -362269789 - 1 & 31] = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bo")
   public void method9276(int var1) {
      this.field5426[(this.field5427 += 1089918251) * -634175101 - 1 & 31] = var1 & 32767;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bu")
   public void method9279(int var1) {
      this.field5435[(this.field5431 += -75721583) * -1415467919 - 1 & 31] = var1;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ai")
   int method9330(int var1, int var2) {
      try {
         return this.field5428[var1 & 31];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pv.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("sf")
   public void method9372(int var1) {
      this.method9266(var1, (byte)67);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bw")
   public void method9280(int var1) {
      this.field5435[(this.field5431 += -75721583) * -1216136354 - 1 & -1579403281] = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dx")
   int method9344() {
      return this.field5424 * -905640843;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("be")
   public void method9285() {
      this.field5436 = this.field5430 * -803606047;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("by")
   public void method9286() {
      this.field5436 = this.field5430 * -1585984081;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   public void method9291() {
      this.field5432 = -312040537 * this.field5430;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   public void method9295() {
      this.field5424 = -699888003 * this.field5430;
   }

   @ObfuscatedSignature(descriptor = "(Lpv;I)V")
   @ObfuscatedName("vq")
   public static void method9296(classPV var0, int var1) {
      if (var0 == null) {
         var0.method9268(var1);
      } else {
         try {
            var0.field5437 = var0.field5430 * 2126449247;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "pv.ao(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpv;I)V")
   @ObfuscatedName("ef")
   public static void method9299(classPV var0, int var1) {
      if (var0 == null) {
         var0.method9301(var1);
      }

      try {
         var0.field5423 = var0.field5430 * -1827024473;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bl")
   public void method9297() {
      this.field5437 = this.field5430 * 986962992;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bv")
   public void method9298() {
      this.field5437 = this.field5430 * 43570621;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bz")
   public void method9300() {
      this.field5423 = this.field5430 * -1827024473;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cr")
   public void method9304() {
      this.field5438 = this.field5430 * -1807734201;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dn")
   int method9353() {
      return this.field5423 * -2078654985;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cb")
   public void method9305() {
      this.field5438 = this.field5430 * -1807734201;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ct")
   public void method9306() {
      this.field5438 = this.field5430 * -1807734201;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cp")
   public void method9307() {
      this.field5434 = 849426047 * this.field5430;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cq")
   public void method9310() {
      this.field5425 += 638451616;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ci")
   int method9334() {
      return -362269789 * this.field5429;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ep")
   public void method9369(int var1) {
      var1 &= 32767;
      HashTable var2 = classOE.field4841.method2397();
      ItemContainer var3 = (ItemContainer)var2.method13434(var1);
      ItemContainer var4 = (ItemContainer)var2.method13434(var1 | 32768);
      if (var3 != null) {
         ItemContainerChanged var5 = new ItemContainerChanged(var1, var3);
         this.field5422.add(var5);
      }

      if (var4 != null) {
         ItemContainerChanged var7 = new ItemContainerChanged(var1 | 32768, var4);
         this.field5422.add(var7);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cd")
   public void method9311() {
      this.field5425 += 638451616;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ov")
   public void method9370(int var1) {
      Skill[] var2 = Skill.values();
      if (var1 >= 0 && var1 < var2.length) {
         int[] var3 = classOE.field4841.getSkillExperiences();
         int[] var4 = classOE.field4841.getBoostedSkillLevels();
         int[] var5 = classOE.field4841.getRealSkillLevels();
         StatChanged var6 = new StatChanged(var2[var1], var3[var1], var5[var1], var4[var1]);
         classOE.field4841.getCallbacks().post(var6);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpv;I)V")
   @ObfuscatedName("zm")
   public static void method9287(classPV var0, int var1) {
      if (var0 == null) {
         var0.method9289(var1);
      } else {
         try {
            var0.field5436 = var0.field5430 * -803606047;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "pv.ax(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("da")
   int method9364() {
      return 2013328175 * this.field5434;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("at")
   int method9316(int var1) {
      return this.field5439[var1 & 31];
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   public void method9283(int var1) {
      try {
         this.field5433 = this.field5430 * 751702011;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("an")
   int method9354(int var1) {
      try {
         return this.field5423 * -2078654985;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.bn(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bn")
   int method9314(int var1) {
      try {
         return this.field5430 * 2077496097;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.ad(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cf")
   int method9323(int var1) {
      return this.field5426[var1 & 31];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cz")
   int method9328() {
      return this.field5427 * -634175101;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("co")
   int method9324(int var1) {
      return this.field5426[var1 & 31];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ch")
   int method9329() {
      return this.field5427 * -634175101;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bc")
   int method9349(int var1) {
      try {
         return 1056232063 * this.field5437;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.ah(" + 41);
      }
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("tj")
   public void method9368() {
      client.method2155();

      for (ItemContainerChanged var2 : this.field5422) {
         classOE.field4841.getCallbacks().post(var2);
      }

      this.field5422.clear();
      classOE.field4841.getCallbacks().post(new ClientTick());
      if (classOE.field4841.getOculusOrbState() != 0) {
         MenuEntry[] var7 = classOE.field4841.getMenuEntries();
         ArrayList var8 = new ArrayList(var7.length);

         for (MenuEntry var6 : var7) {
            switch (var6.getType()) {
               default:
                  var8.add(var6);
               case WIDGET_TARGET_ON_GAME_OBJECT:
               case GAME_OBJECT_FIRST_OPTION:
               case GAME_OBJECT_SECOND_OPTION:
               case GAME_OBJECT_THIRD_OPTION:
               case GAME_OBJECT_FOURTH_OPTION:
               case GAME_OBJECT_FIFTH_OPTION:
               case WIDGET_TARGET_ON_NPC:
               case NPC_FIRST_OPTION:
               case NPC_SECOND_OPTION:
               case NPC_THIRD_OPTION:
               case NPC_FOURTH_OPTION:
               case NPC_FIFTH_OPTION:
               case WIDGET_TARGET_ON_PLAYER:
               case WIDGET_TARGET_ON_GROUND_ITEM:
               case GROUND_ITEM_FIRST_OPTION:
               case GROUND_ITEM_SECOND_OPTION:
               case GROUND_ITEM_THIRD_OPTION:
               case GROUND_ITEM_FOURTH_OPTION:
               case GROUND_ITEM_FIFTH_OPTION:
               case PLAYER_FIRST_OPTION:
               case PLAYER_SECOND_OPTION:
               case PLAYER_THIRD_OPTION:
               case PLAYER_FOURTH_OPTION:
               case PLAYER_FIFTH_OPTION:
               case PLAYER_SIXTH_OPTION:
               case PLAYER_SEVENTH_OPTION:
               case PLAYER_EIGHTH_OPTION:
            }
         }

         classOE.field4841.setMenuEntries(var8.toArray(new MenuEntry[0]));
      }

      classIS.field3051.field1687.method4434();
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cc")
   int method9331(int var1) {
      return this.field5428[var1 & 31];
   }

   @ObfuscatedSignature(descriptor = "(Lpv;B)I")
   @ObfuscatedName("it")
   public static int method9345(classPV var0, byte var1) {
      if (var0 == null) {
         var0.method9361(var1);
      }

      try {
         return var0.field5424 * -905640843;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.am(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cl")
   public void method9312() {
      this.field5425 += 638451616;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ah")
   int method9325(int var1) {
      return this.field5426[var1 & 31];
   }

   @ObfuscatedSignature(descriptor = "(Lpv;II)I")
   @ObfuscatedName("xc")
   public static int method9317(classPV var0, int var1, int var2) {
      if (var0 == null) {
         var0.method9318(var1, var1);
      }

      try {
         return var0.field5439[var1 & 31];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pv.ap(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bi")
   public void method9288() {
      this.field5436 = this.field5430 * -1458261562;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ce")
   int method9337() {
      return -127397349 * this.field5433;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dc")
   int method9341() {
      return this.field5436 * -120272575;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("xt")
   public void method9373(int var1) {
      method9277(this, var1, (byte)50);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cx")
   int method9338() {
      return -296195950 * this.field5433;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aq")
   public void method9313(int var1) {
      try {
         this.field5425 += 638451616;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("du")
   int method9346() {
      return this.field5424 * -905640843;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dt")
   int method9350() {
      return 1056232063 * this.field5437;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   public void method9292() {
      this.field5432 = -312040537 * this.field5430;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dk")
   int method9351() {
      return 1056232063 * this.field5437;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dw")
   int method9352() {
      return 1056232063 * this.field5437;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ap")
   int method9326(int var1, int var2) {
      try {
         return this.field5426[var1 & 31];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pv.ai(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("de")
   int method9355() {
      return this.field5423 * -2078654985;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dz")
   int method9356() {
      return this.field5423 * -2078654985;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dj")
   int method9365() {
      return 1796613727 * this.field5434;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("do")
   int method9357() {
      return this.field5423 * -1686259781;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dg")
   int method9359() {
      return this.field5438 * 718414679;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ba")
   public void method9281(int var1) {
      this.field5435[(this.field5431 += -75721583) * -1415467919 - 1 & 31] = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dq")
   int method9360() {
      return this.field5438 * 718414679;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("df")
   int method9366() {
      return 1796613727 * this.field5434;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ds")
   int method9367() {
      return 1796613727 * this.field5434;
   }

   @ObfuscatedSignature(descriptor = "(Lpv;IB)V")
   @ObfuscatedName("oh")
   public static void method9277(classPV var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method9275(var1, var2);
      }

      var0.method9369(var1);

      try {
         var0.field5426[(var0.field5427 += 1089918251) * -634175101 - 1 & 31] = var1 & 32767;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pv.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ck")
   int method9320() {
      return this.field5425 * 1236436392;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   public void method9289(int var1) {
      try {
         this.field5425 = this.field5432 * -803606047;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ac")
   public void method9293(int var1) {
      try {
         this.field5432 = -312040537 * this.field5437;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   public void method9301(int var1) {
      try {
         this.field5434 = this.field5433 * -1827024473;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpv;B)I")
   @ObfuscatedName("nl")
   public static int method9321(classPV var0, byte var1) {
      if (var0 == null) {
         return var0.method9322(var1);
      } else {
         try {
            return var0.field5425 * 1886152021;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "pv.au(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ay")
   public void method9284(int var1) {
      try {
         this.field5432 = this.field5436 * 751702011;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   public void method9268(int var1) {
      this.field5435[(this.field5433 = this.field5432 + 1362128893) * 1886152021 - 1 & 31] = var1;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aw")
   int method9318(int var1, int var2) {
      try {
         return this.field5428[var1 & 31];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pv.ap(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("au")
   int method9322(byte var1) {
      try {
         return this.field5433 * 1886152021;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.au(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("am")
   int method9361(byte var1) {
      try {
         return this.field5429 * 718414679;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pv.bx(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("br")
   public void method9269(int var1) {
      this.field5435[(this.field5431 = this.field5429 + 1362128893) * 1886152021 - 1 & 31] = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bj")
   public void method9282(int var1) {
      this.field5428[(this.field5429 = this.field5431 + -75721583) * -1216136354 - 1 & -1579403281] = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bg")
   public void method9270(int var1) {
      this.field5426[(this.field5431 = this.field5436 + 1362128893) * 1285770944 - 1 & 1124523962] = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpv;I)V")
   @ObfuscatedName("ij")
   public static void method9271(classPV var0, int var1) {
      if (var0 == null) {
         var0.method9269(var1);
      } else {
         var0.field5439[(var0.field5425 += 1362128893) * 1886152021 - 1 & 31] = var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpv;I)V")
   @ObfuscatedName("ko")
   public static void method9308(classPV var0, int var1) {
      if (var0 == null) {
         var0.method9284(var1);
      } else {
         try {
            var0.field5434 = 849426047 * var0.field5430;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "pv.ay(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dl")
   int method9362() {
      return this.field5429 * 718414679;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ab")
   public void method9275(int var1, byte var2) {
      this.method9270(var1);

      try {
         this.field5439[(this.field5437 += -612394997) * -362269789 - 1 & 31] = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pv.ae(" + ')');
      }
   }
}
