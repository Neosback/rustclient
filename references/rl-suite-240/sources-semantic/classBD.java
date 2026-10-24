import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bd")
public class classBD {
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   public static final int field377 = 0;
   @ObfuscatedName("af")
   Future field375;
   @ToRemove(unused = "true")
   @ObfuscatedName("bq")
   static final int field378 = 65;
   @ObfuscatedName("az")
   ExecutorService field374 = Executors.newSingleThreadExecutor();
   @ToRemove(unused = "true")
   @ObfuscatedName("bv")
   static final int field379 = 96;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field376 = 20;

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("af")
   boolean method983(int var1) {
      try {
         return this.field375.isDone();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bd.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/security/SecureRandom;")
   @ObfuscatedName("ae")
   SecureRandom method988(byte var1) {
      try {
         try {
            return (SecureRandom)this.field375.get();
         } catch (Exception var3) {
            return classGP.method5581((byte)1);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bd.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   void method978() {
      this.field374.shutdown();
      this.field374 = null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ax")
   boolean method984() {
      return this.field375.isDone();
   }

   @ObfuscatedSignature(descriptor = "(Lbd;I)V")
   @ObfuscatedName("ob")
   public static void method979(classBD var0, int var1) {
      if (var0 == null) {
         var0.method982(var1);
      }

      try {
         var0.field374.shutdown();
         var0.field374 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bd.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   void method980() {
      this.field374.shutdown();
      this.field374 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lyy;Ljava/lang/Object;B)V")
   @ObfuscatedName("cf")
   static void method996(classYY var0, Object var1, byte var2) {
      try {
         if (var0 == null) {
            if (var2 > 48) {
               throw new IllegalStateException("pushValueOfType() failure - null baseVarType");
            }
         } else {
            switch (-900901303 * var0.field7105) {
               case 2:
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = (Integer)var1;
                  break;
               case 3:
                  classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = (Long)var1;
                  break;
               case 4:
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var1;
                  break;
               default:
                  throw new IllegalStateException("pushValueOfType() failure - unsupported type");
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "bd.cf(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aa")
   boolean method985() {
      return this.field375.isDone();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbd;)Ljava/security/SecureRandom;")
   @ObfuscatedName("qd")
   public static SecureRandom method989(classBD var0) {
      if (var0 == null) {
         var0.method992();
      }

      try {
         return (SecureRandom)var0.field375.get();
      } catch (Exception var2) {
         return classGP.method5581((byte)1);
      }
   }

   classBD() {
      this.field375 = this.field374.submit(new classDU());
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ao")
   boolean method986() {
      return this.field375.isDone();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ac")
   boolean method987() {
      return this.field375.isDone();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/security/SecureRandom;")
   @ObfuscatedName("al")
   SecureRandom method990() {
      try {
         return (SecureRandom)this.field375.get();
      } catch (Exception var2) {
         return classGP.method5581((byte)1);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/security/SecureRandom;")
   @ObfuscatedName("ay")
   SecureRandom method991() {
      try {
         return (SecureRandom)this.field375.get();
      } catch (Exception var2) {
         return classGP.method5581((byte)1);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("au")
   static void method995(byte var0) {
      try {
         client.field894 = 9147219051952896537L;
         if (null != NpcOverrides.field5378) {
            if (var0 <= 126) {
               throw new IllegalStateException();
            }

            NpcOverrides.field5378.field1536 = 0;
         }

         classDD.field1403 = true;
         client.field836 = true;
         client.field924 = -4495327398113701859L;
         classZJ.field7187 = new classNV();
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
         client.field821 = 0;
         client.field942.method6561((byte)-40);
         AABB.method4937(-1512642514);
         classTZ.field6258 = 0;
         client.method1819(-1);
         classMD.method7577((byte)121);
         client.field946 = 0;
         client.field980 = false;
         classSM.method10537(GraphicsObject.field475, -1824285602);
         classUP.method11494(client.field995, 0, -1739123893);
         client.field968 = 0;
         classMC.field4479 = null;
         classBA.field305 = 0;
         classBA.field311 = 1545843635;
         client.field860 = 0;
         client.field835 = 0;
         client.field917 = classCK.field766;
         client.field898 = classCK.field766;
         client.field815.method1615(-77096468);
         client.field847 = 0;
         Deque2.method7942(client.field818);
         classYE.field6975.method13623();
         client.field838.method3058((byte)81);
         classBW.method1293(classQQ.field5595, 1423332917);
         Arrays.fill(classLB.field4089, 0);
         Arrays.fill(classLB.field4088, 0);
         if (null != classSX.field6094) {
            if (var0 <= 126) {
               throw new IllegalStateException();
            }

            classSX.field6094.method3581((byte)126);
         }

         client.field926.clear();
         if (-1 != -77072447 * classWK.field6689.field6610) {
            if (var0 <= 126) {
               throw new IllegalStateException();
            }

            classWK.field6689.method7429(classWK.field6689.field6610 * -77072447, 1312232715);
         }

         for (WidgetNode var1 = (WidgetNode)classWK.field6689.field6611.method13423();
            null != var1;
            var1 = (WidgetNode)classWK.field6689.field6611.method13427()
         ) {
            if (var0 <= 126) {
               throw new IllegalStateException();
            }

            classGB.field2544.method9150(var1, true, classWK.field6689, (byte)-113);
         }

         classVV.method12186(classWK.field6689, (byte)125);
         AABB.method4937(-1522154020);
         client.field993.method6722(null, null, new int[]{0, 0, 0, 0, 0}, 0, -1, -741931325);

         for (int var3 = 0; var3 < 8; var3++) {
            if (var0 <= 126) {
               return;
            }

            client.field969[var3] = null;
            client.method2327(var3);
            client.field802[var3] = false;
         }

         ItemContainer.field454 = new HashTable(32);
         client.field892 = true;
         if (client.field796 != null) {
            if (var0 <= 126) {
               throw new IllegalStateException();
            }

            if (client.field796.field1533 == null) {
               if (var0 <= 126) {
                  throw new IllegalStateException();
               }
            } else {
               classJL var4 = classEF.method3863(classJS.WINDOW_STATUS, client.field796.field1533, (byte)22);
               classXY.method13029(var4.field3341, classLH.method7145(1904094042), (byte)-55);
               var4.field3341.method13031(773246731 * classSA.field5895, -1542382992);
               var4.field3341.method13031(IntProjection.field1936 * 8379747, -1542382992);
               client.field796.method3374(var4, -1301097035);
            }
         }

         classIB.field2933 = null;
         client.method2315(-1);
         classBM.field513 = null;
         Arrays.fill(client.field866, null);
         classKA.field3511 = null;
         client.method2365(-1);
         Arrays.fill(client.field867, null);
         classOE.field4841.field791.method12824((byte)-81);

         for (int var5 = 0; var5 < 8; var5++) {
            if (var0 <= 126) {
               throw new IllegalStateException();
            }

            client.field865[var5] = new GrandExchangeOffer();
            client.method2288(var5);
         }

         classON.field4992 = null;
         client.field799 = 0;
         client.field876 = 0;
         client.field991 = 0;
         client.field983 = 0;
         classOE.field4841.method11152(true, 477266699);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bd.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("ax")
   public static void method994(boolean var0, int var1) {
      try {
         if (classJP.field3355 != var0) {
            if (var1 <= 1718748145) {
               throw new IllegalStateException();
            }

            classLI.method7149(1438574197);
            classJP.field3355 = var0;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bd.ax(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbd;)V")
   @ObfuscatedName("jn")
   public static void method981(classBD var0) {
      var0.field374.shutdown();
      var0.field374 = null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/security/SecureRandom;")
   @ObfuscatedName("aj")
   SecureRandom method992() {
      try {
         return (SecureRandom)this.field375.get();
      } catch (Exception var2) {
         return classGP.method5581((byte)1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;I)[I")
   @ObfuscatedName("mr")
   public static int[] method993(IndexDataBase var0, int var1) {
      if (var0 == null) {
         var0.vmethod613(var1);
      }

      return var1 >= 0 && var1 < var0.field6453.length ? var0.field6453[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   void method982(int var1) {
      try {
         this.field374.shutdown();
         this.field374 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bd.az(" + ')');
      }
   }
}
