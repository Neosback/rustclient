import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sc")
public class classSC extends classSQ {
   @ObfuscatedName("ab")
   int field5906;
   @ObfuscatedName("az")
   static final int field5904 = client.field812 * 1683728927 + 1831209147;
   @ObfuscatedSignature(descriptor = "Lgy;")
   @ObfuscatedName("ae")
   final classGY field5907;
   @ObfuscatedName("ag")
   int field5908;
   @ObfuscatedSignature(descriptor = "Lgy;")
   @ObfuscatedName("af")
   final classGY field5905 = new classGY();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgy;Lgy;FLgy;)V")
   @ObfuscatedName("ag")
   static void method10414(classGY var0, classGY var1, float var2, classGY var3) {
      var2 = classBF.method1001(0.0F, 1.0F, var2, 1649794621);
      int var4 = classGY.method5930(var1, 877192080) - classGY.method5930(var0, 299508150);
      int var5 = var1.method5936(1690676705) - var0.method5936(1690676705);
      var4 = (int)(var4 * var2);
      var5 = (int)(var5 * var2);
      var3.method5957(classGY.method5930(var0, 286070242) + var4, var0.method5936(1690676705) + var5, (byte)5);
      int var6 = classNF.method7876(classGY.method5947(var0, 1305253738), classGY.method5947(var1, 1866390882), (byte)112);
      var6 = (int)(var6 * var2);
      var3.method5950(classGY.method5947(var0, 1220177858) + var6, 229110535);
   }

   @ObfuscatedSignature(descriptor = "(Lgy;Lgy;DLgy;)V")
   @ObfuscatedName("fg")
   public static void method10419(classGY var0, classGY var1, double var2, classGY var4) {
      if (var2 < 0.0) {
         var2 = 0.0;
      }

      if (var2 > 1.0) {
         var2 = 1.0;
      }

      int var5 = var1.field2739 * -787916131 - var0.field2739 * -787916131;
      int var6 = var1.field2740 * -478154003 - var0.field2740 * -478154003;
      var5 = (int)(var2 * var5);
      var6 = (int)(var2 * var6);
      var4.field2739 = (var0.field2739 * -787916131 + var5) * 1397226421;
      var4.field2740 = (var0.field2740 * -478154003 + var6) * 1296710373;
      int var7 = var1.field2742 * 1859166197 - var0.field2742 * 1859166197 & 2047;
      if (var7 > 1024) {
         var7 = -(2048 - var7);
      }

      var7 = (int)(var7 * var2);
      var4.field2742 = (var0.field2742 * 1859166197 + var7 & 2047) * -429665187;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;Lsr;IB)V")
   @ObfuscatedName("af")
   @Override
   void vmethod568(classGY var1, classSR var2, int var3, byte var4) {
      try {
         this.field5905.method5953(var1, 521750869);
         this.field5907.method5953(var2.field6027, 104573632);
         this.field5906 = -782499801 * (var3 - 1);
         this.field5908 = field5904 * 1285481973 + var2.field6028 * 1771193875;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "sc.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ak")
   static final void method10418(int var0, int var1) {
      try {
         classGI.method5492((byte)75);
         switch (var0) {
            case 1:
               Player.method2660(1450325754);
               break;
            case 2:
               classAC.method321(24, 2130611027);
               WorldEntity.method10671(classKK.field3839, classKK.field3840, classKK.field3841, (byte)-96);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sc.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod573(int var1, int var2, int var3) {
      try {
         this.field5905.method5963(var1, var2, (byte)107);
         this.field5907.method5963(var1, var2, (byte)125);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "sc.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("al")
   @Override
   void vmethod574(int var1, int var2) {
      this.field5905.method5963(var1, var2, (byte)105);
      this.field5907.method5963(var1, var2, (byte)102);
   }

   @ObfuscatedSignature(descriptor = "(Lgy;III)Z")
   @ObfuscatedName("ae")
   @Override
   boolean vmethod571(classGY var1, int var2, int var3, int var4) {
      try {
         if (this.field5906 * -1143115881 >= this.field5908 * 823938901) {
            if (var4 <= 517141950) {
               throw new IllegalStateException();
            } else {
               var1.method5953(this.field5907, -148868463);
               return true;
            }
         } else {
            float var5 = NameableContainer.method10239(var2, this.field5906 * -1143115881, 823938901 * this.field5908, 862332991);
            FriendsChatMember.method10323(this.field5905, this.field5907, var5, var1, (short)246);
            boolean var10000;
            if (var5 >= 1.0F) {
               if (var4 <= 517141950) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "sc.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgy;Lgy;FLgy;)V")
   @ObfuscatedName("as")
   static void method10415(classGY var0, classGY var1, float var2, classGY var3) {
      var2 = classBF.method1001(0.0F, 1.0F, var2, -1186059843);
      int var4 = classGY.method5930(var1, 1941607674) - classGY.method5930(var0, 977605635);
      int var5 = var1.method5936(1690676705) - var0.method5936(1690676705);
      var4 = (int)(var4 * var2);
      var5 = (int)(var5 * var2);
      var3.method5957(classGY.method5930(var0, 536377897) + var4, var0.method5936(1690676705) + var5, (byte)5);
      int var6 = classNF.method7876(classGY.method5947(var0, 2008553523), classGY.method5947(var1, 1870037958), (byte)115);
      var6 = (int)(var6 * var2);
      var3.method5950(classGY.method5947(var0, 1662433654) + var6, -534132521);
   }

   @ObfuscatedSignature(descriptor = "(Lgy;Lsr;I)V")
   @ObfuscatedName("ac")
   @Override
   void vmethod569(classGY var1, classSR var2, int var3) {
      this.field5905.method5953(var1, 1578416802);
      this.field5907.method5953(var2.field6027, -699182635);
      this.field5906 = -782499801 * (var3 - 1);
      this.field5908 = field5904 * 1285481973 + var2.field6028 * 1771193875;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;Z)V")
   @ObfuscatedName("hf")
   public static void method10417(classQC var0, boolean var1) {
      if (var0 == null) {
         var0.method9504(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;Lsr;I)V")
   @ObfuscatedName("aa")
   @Override
   void vmethod570(classGY var1, classSR var2, int var3) {
      this.field5905.method5953(var1, -1724473401);
      this.field5907.method5953(var2.field6027, 1514411741);
      this.field5906 = -782499801 * (var3 - 1);
      this.field5908 = field5904 * 1285481973 + var2.field6028 * 1771193875;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;II)Z")
   @ObfuscatedName("ao")
   @Override
   boolean vmethod572(classGY var1, int var2, int var3) {
      if (this.field5906 * -1143115881 >= this.field5908 * 823938901) {
         var1.method5953(this.field5907, -1216196930);
         return true;
      } else {
         float var4 = NameableContainer.method10239(var2, this.field5906 * 1707513826, 1874518264 * this.field5908, 1880698948);
         FriendsChatMember.method10323(this.field5905, this.field5907, var4, var1, (short)246);
         return var4 >= 1.0F;
      }
   }

   classSC() {
      this.field5907 = new classGY();
      this.field5906 = 0;
      this.field5908 = 0;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aj")
   @Override
   void vmethod575(int var1, int var2) {
      this.field5905.method5963(var1, var2, (byte)104);
      this.field5907.method5963(var1, var2, (byte)123);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgy;Lgy;FLgy;)V")
   @ObfuscatedName("ax")
   static void method10416(classGY var0, classGY var1, float var2, classGY var3) {
      var2 = classBF.method1001(0.0F, 1.0F, var2, -1981250567);
      int var4 = classGY.method5930(var1, 389090945) - classGY.method5930(var0, 234244656);
      int var5 = var1.method5936(1690676705) - var0.method5936(1690676705);
      var4 = (int)(var4 * var2);
      var5 = (int)(var5 * var2);
      var3.method5957(classGY.method5930(var0, -312504964) + var4, var0.method5936(1690676705) + var5, (byte)5);
      int var6 = classNF.method7876(classGY.method5947(var0, 1364128016), classGY.method5947(var1, 1206933441), (byte)69);
      var6 = (int)(var6 * var2);
      var3.method5950(classGY.method5947(var0, 1504179727) + var6, -152012751);
   }

   @ObfuscatedSignature(descriptor = "(DII)D")
   @ObfuscatedName("gf")
   public static double method10420(double var0, int var2, int var3) {
      return (var0 - var2) / (var3 - var2);
   }

   @ObfuscatedSignature(descriptor = "(Lgy;DI)Z")
   @ObfuscatedName("pn")
   @Override
   public boolean vmethod576(classGY var1, double var2, int var4) {
      if (this.field5906 * -1143115881 >= this.field5908 * 823938901) {
         var1.method5980(this.field5907);
         return true;
      } else {
         double var5 = method10420(var2, this.field5906 * -1143115881, this.field5908 * 823938901);
         method10419(this.field5905, this.field5907, var5, var1);
         return var5 >= 1.0;
      }
   }
}
