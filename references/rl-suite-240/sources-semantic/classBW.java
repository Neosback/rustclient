import net.runelite.api.events.RemovedFriend;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bw")
public class classBW {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field598 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field596 = 1;
   @ObfuscatedName("ax")
   int field594 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field595 = 2;
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("ab")
   final classAAO field592;
   @ObfuscatedSignature(descriptor = "Lrf;")
   @ObfuscatedName("as")
   public final classRF field591;
   @ObfuscatedSignature(descriptor = "Lrn;")
   @ObfuscatedName("ag")
   public final FriendContainer field593;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   public static final int field597 = 24;
   @ToRemove(unused = "true")
   @ObfuscatedName("cr")
   static final int field599 = 68;

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("bu")
   final void method1281(classXY var1, int var2) {
      this.field593.method10182(var1, var2, (byte)1);
      this.field594 = 596332666;
      classPW.method9403(1216544001);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("as")
   boolean method1276(int var1) {
      try {
         boolean var10000;
         if (215887637 * this.field594 == 2) {
            if (var1 >= 139838013) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bw.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("yg")
   public void method1339(String var1) {
      net.runelite.api.Friend var2 = (net.runelite.api.Friend)this.method1343().findByName(var1);
      if (var2 != null) {
         classOE.field4841.getCallbacks().post(new RemovedFriend(var2));
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   final void method1279(int var1) {
      try {
         this.field594 = -1849317315;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bw.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cf")
   final boolean method1321() {
      return this.field591.method10249(2037364072) || this.field591.method10243(-387387364) >= 100 && 1 != 1610258521 * client.field848;
   }

   @ObfuscatedSignature(descriptor = "(Lbw;B)Z")
   @ObfuscatedName("sj")
   public static boolean method1311(classBW var0, byte var1) {
      if (var0 == null) {
         var0.method1324(var1);
      }

      try {
         if (!var0.field593.method10249(2037364072)) {
            if (var0.field593.method10243(-1485829891) < 200) {
               return false;
            }

            if (var1 <= 3) {
               throw new IllegalStateException();
            }

            if (1 == client.field848 * 1610258521) {
               return false;
            }

            if (var1 <= 3) {
               throw new IllegalStateException();
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bw.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   final void method1286(int var1) {
      try {
         for (PendingLogin var2 = (PendingLogin)this.field593.field5810.method7914();
            var2 != null;
            var2 = (PendingLogin)Deque.method7921(this.field593.field5810)
         ) {
            if (var1 >= -2090297944) {
               throw new IllegalStateException();
            }

            if (1669590409 * var2.field5665 < classDD.method2988(16711680) / 1000L - 5L) {
               if (var1 >= -2090297944) {
                  throw new IllegalStateException();
               }

               if (var2.field5664 > 0) {
                  if (var1 >= -2090297944) {
                     return;
                  }

                  classPH.method8924(5, "", var2.field5663 + classKK.field3769, 1997157230);
               }

               if (var2.field5664 == 0) {
                  if (var1 >= -2090297944) {
                     return;
                  }

                  classPH.method8924(5, "", var2.field5663 + classKK.field3704, 1671185975);
               }

               classVM.method12069(var2);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "bw.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;ZI)Z")
   @ObfuscatedName("al")
   final boolean method1295(classAAE var1, boolean var2, int var3) {
      try {
         if (null == var1) {
            if (var3 <= 391357984) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (var1.equals(classOE.field4841.vmethod255((byte)103))) {
            if (var3 <= 391357984) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else if (this.field593.method10181(var1, var2, -2046062906)) {
            if (var3 <= 391357984) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bw.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;I)Z")
   @ObfuscatedName("aj")
   final boolean method1299(classAAE var1, int var2) {
      try {
         if (var1 == null) {
            if (var2 != -1181618808) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (NameableContainer.method10250(this.field591, var1, (byte)10)) {
            if (var2 != -1181618808) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "bw.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lbw;Laae;S)Z")
   @ObfuscatedName("iv")
   public static boolean method1334(classBW var0, classAAE var1, short var2) {
      if (var0 == null) {
         return var0.method1337(var1, var2);
      } else {
         try {
            Friend var3 = (Friend)var0.field593.method10256(var1, 282151994);
            if (var3 != null) {
               if (var2 == 160) {
                  throw new IllegalStateException();
               }

               if (ChatPlayer.method10010(var3, -2092993019)) {
                  if (var2 == 160) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }

            return false;
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "bw.ar(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;Ljava/lang/String;Z)V")
   @ObfuscatedName("ji")
   public static void method1330(classBW var0, String var1, boolean var2) {
      if (var0 == null) {
         var0.method1318(var1);
      }

      if (var1 != null) {
         classAAE var3 = new classAAE(var1, var0.field592);
         if (var3.method166(-884069400)) {
            if (var0.field591.method10267(var3, 726858335)) {
               client.field825.method9283(856626137);
               if (var2) {
                  classJL var4 = classEF.method3863(classJS.IGNORELIST_DEL, client.field796.field1533, (byte)39);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)112), (byte)-1);
                  var4.field3341.method13054(var1, (short)16430);
                  client.field796.method3374(var4, -1301097035);
               }
            }

            classHG.method6042((byte)32);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("ay")
   final void method1305(String var1, int var2) {
      try {
         if (null != var1) {
            classAAE var3 = new classAAE(var1, this.field592);
            if (var3.method166(-53981419)) {
               if (method1311(this, (byte)109)) {
                  if (var2 == -886915762) {
                     throw new IllegalStateException();
                  } else {
                     String var7 = classKK.field3645;
                     classPH.method8924(30, "", var7, 1335251297);
                  }
               } else {
                  classAAE var4 = classOE.field4841.vmethod255((byte)70);
                  if (null != var4) {
                     if (var2 == -886915762) {
                        return;
                     }

                     if (var4.equals(var3)) {
                        if (var2 == -886915762) {
                           return;
                        }

                        classAD.method323(-855954109);
                        return;
                     }
                  }

                  if (this.method1295(var3, false, 2073566234)) {
                     if (var2 != -886915762) {
                        String var8 = var1 + classKK.field3790;
                        classPH.method8924(30, "", var8, 1241405821);
                     }
                  } else if (this.method1299(var3, -1181618808)) {
                     if (var2 == -886915762) {
                        throw new IllegalStateException();
                     } else {
                        classNA.method7801(var1, 1323786599);
                     }
                  } else {
                     classJL var5 = classEF.method3863(classJS.FRIENDLIST_ADD, client.field796.field1533, (byte)-4);
                     classXY.method13029(var5.field3341, classAU.method709(var1, (byte)107), (byte)-128);
                     var5.field3341.method13054(var1, (short)4936);
                     client.field796.method3374(var5, -1301097035);
                  }
               }
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "bw.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ck")
   final void method1316(String var1) {
      if (null != var1) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(1252145016)) {
            if (this.method1322((byte)24)) {
               classBA.method749(-974042043);
            } else {
               classAAE var3 = classOE.field4841.vmethod255((byte)44);
               if (null != var3 && var3.equals(var2)) {
                  String var6 = classKK.field3794;
                  classPH.method8924(30, "", var6, 1277599493);
               } else if (this.method1299(var2, -1181618808)) {
                  String var5 = var1 + classKK.field3792;
                  classPH.method8924(30, "", var5, 1958958128);
               } else if (this.method1295(var2, false, 1755805766)) {
                  classDT.method3614(var1, (byte)56);
               } else {
                  classJL var4 = classEF.method3863(classJS.IGNORELIST_ADD, client.field796.field1533, (byte)-92);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)83), (byte)-54);
                  var4.field3341.method13054(var1, (short)5771);
                  client.field796.method3374(var4, -1301097035);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cy")
   final boolean method1312() {
      return this.field593.method10249(2037364072) || this.field593.method10243(-1954472056) >= 200 && 1 != client.field848 * 1610258521;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("au")
   final void method1325(String var1, byte var2) {
      this.method1339(var1);

      try {
         if (var1 == null) {
            if (var2 == 1) {
               throw new IllegalStateException();
            }
         } else {
            classAAE var3 = new classAAE(var1, this.field592);
            if (!var3.method166(1489933299)) {
               if (var2 == 1) {
                  throw new IllegalStateException();
               }
            } else {
               if (this.field593.method10267(var3, -2031138017)) {
                  client.field825.method9283(540899429);
                  classJL var4 = classEF.method3863(classJS.FRIENDLIST_DEL, client.field796.field1533, (byte)-27);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)77), (byte)-38);
                  var4.field3341.method13054(var1, (short)13396);
                  client.field796.method3374(var4, -1301097035);
               }

               classPW.method9403(-207042932);
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "bw.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("cg")
   final void method1326(String var1) {
      if (var1 != null) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(756097414)) {
            if (this.field593.method10267(var2, -1293162660)) {
               client.field825.method9283(692040091);
               classJL var3 = classEF.method3863(classJS.FRIENDLIST_DEL, client.field796.field1533, (byte)57);
               classXY.method13029(var3.field3341, classAU.method709(var1, (byte)83), (byte)-99);
               var3.field3341.method13054(var1, (short)8665);
               client.field796.method3374(var3, -1301097035);
            }

            classPW.method9403(549526107);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ZI)V")
   @ObfuscatedName("ai")
   public final void method1331(String var1, boolean var2, int var3) {
      this.method1340(var1, var2);

      try {
         if (var1 == null) {
            if (var3 > 998486118) {
               ;
            }
         } else {
            classAAE var4 = new classAAE(var1, this.field592);
            if (!var4.method166(-591637563)) {
               if (var3 <= 998486118) {
                  throw new IllegalStateException();
               }
            } else {
               if (this.field591.method10267(var4, -716292657)) {
                  client.field825.method9283(198805685);
                  if (var2) {
                     if (var3 <= 998486118) {
                        throw new IllegalStateException();
                     }

                     classJL var5 = classEF.method3863(classJS.IGNORELIST_DEL, client.field796.field1533, (byte)-18);
                     classXY.method13029(var5.field3341, classAU.method709(var1, (byte)71), (byte)-11);
                     var5.field3341.method13054(var1, (short)18148);
                     client.field796.method3374(var5, -1301097035);
                  }
               }

               classHG.method6042((byte)-27);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "bw.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("co")
   final void method1317(String var1) {
      if (null != var1) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(1115080568)) {
            if (this.method1322((byte)24)) {
               classBA.method749(-728213315);
            } else {
               classAAE var3 = classOE.field4841.vmethod255((byte)69);
               if (null != var3 && var3.equals(var2)) {
                  String var6 = classKK.field3794;
                  classPH.method8924(30, "", var6, 1609586069);
               } else if (this.method1299(var2, -1181618808)) {
                  String var5 = var1 + classKK.field3792;
                  classPH.method8924(30, "", var5, 1517428602);
               } else if (this.method1295(var2, false, 1920835225)) {
                  classDT.method3614(var1, (byte)69);
               } else {
                  classJL var4 = classEF.method3863(classJS.IGNORELIST_ADD, client.field796.field1533, (byte)94);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)123), (byte)-110);
                  var4.field3341.method13054(var1, (short)22468);
                  client.field796.method3374(var4, -1301097035);
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("aw")
   static final void method1260(String var0) {
      String var1 = classKK.field3795 + var0 + classKK.field3796;
      classPH.method8924(30, "", var1, 2098079339);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("br")
   static final void method1271(String var0) {
      String var1 = classKK.field3797 + var0 + classKK.field3798;
      classPH.method8924(30, "", var1, 1649694087);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Z)V")
   @ObfuscatedName("ci")
   public final void method1332(String var1, boolean var2) {
      if (var1 != null) {
         classAAE var3 = new classAAE(var1, this.field592);
         if (var3.method166(-280857570)) {
            if (this.field591.method10267(var3, -503332637)) {
               client.field825.method9283(502979143);
               if (var2) {
                  classJL var4 = classEF.method3863(classJS.IGNORELIST_DEL, client.field796.field1533, (byte)-55);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)91), (byte)-124);
                  var4.field3341.method13054(var1, (short)980);
                  client.field796.method3374(var4, -1301097035);
               }
            }

            classHG.method6042((byte)11);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("at")
   static final void method1261(String var0) {
      String var1 = classKK.field3795 + var0 + classKK.field3796;
      classPH.method8924(30, "", var1, 1468984224);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   static final void method1264() {
      String var0 = classKK.field3887;
      classPH.method8924(30, "", var0, 1246268325);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   static final void method1267() {
      String var0 = classKK.field3791;
      classPH.method8924(30, "", var0, 2128293357);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   static final void method1268() {
      String var0 = classKK.field3791;
      classPH.method8924(30, "", var0, 2139050159);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bx")
   static final void method1269() {
      String var0 = classKK.field3791;
      classPH.method8924(30, "", var0, 1565754274);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("bw")
   final void method1282(classXY var1, int var2) {
      this.field593.method10182(var1, var2, (byte)1);
      this.field594 = 596332666;
      classPW.method9403(-1900330370);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("bp")
   static final void method1272(String var0) {
      String var1 = classKK.field3797 + var0 + classKK.field3798;
      classPH.method8924(30, "", var1, 1217476571);
   }

   classBW(classAAO var1) {
      this.field592 = var1;
      this.field593 = new FriendContainer(var1);
      this.field591 = new classRF(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;)V")
   @ObfuscatedName("bt")
   public static void method1287(classBW var0) {
      for (PendingLogin var1 = (PendingLogin)var0.field593.field5810.method7914(); var1 != null; var1 = (PendingLogin)Deque.method7921(var0.field593.field5810)) {
         if (1669590409 * var1.field5665 < classDD.method2988(16711680) / 1000L - 5L) {
            if (var1.field5664 > 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3769, 1879383153);
            }

            if (var1.field5664 == 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3704, 1687053373);
            }

            classVM.method12069(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("cs")
   public boolean method1341(classAAE var1) {
      return this.method1299(var1, -1181618808);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Z)V")
   @ObfuscatedName("qa")
   public void method1340(String var1, boolean var2) {
      net.runelite.api.Ignore var3 = (net.runelite.api.Ignore)this.method1344().findByName(var1);
      if (var3 != null) {
         classOE.field4841.getCallbacks().post(new RemovedFriend(var3));
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("bm")
   static final void method1273(String var0) {
      String var1 = classKK.field3797 + var0 + classKK.field3798;
      classPH.method8924(30, "", var1, 2061600228);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bj")
   boolean method1277() {
      return 215887637 * this.field594 == 2;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bk")
   boolean method1278() {
      return -962063433 * this.field594 == 2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;)V")
   @ObfuscatedName("fh")
   public static void method1288(classBW var0) {
      for (PendingLogin var1 = (PendingLogin)var0.field593.field5810.method7914(); var1 != null; var1 = (PendingLogin)Deque.method7921(var0.field593.field5810)) {
         if (-717270528 * var1.field5665 < classDD.method2988(16711680) / 1000L - 5L) {
            if (var1.field5664 > 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3769, 1588910059);
            }

            if (var1.field5664 == 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3704, 1312810624);
            }

            classVM.method12069(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ap")
   final boolean method1322(byte var1) {
      try {
         if (!this.field591.method10249(2037364072)) {
            if (var1 != 24) {
               throw new IllegalStateException();
            }

            if (this.field591.method10243(-1185182511) < 100) {
               return false;
            }

            if (var1 != 24) {
               throw new IllegalStateException();
            }

            if (1 == 1610258521 * client.field848) {
               return false;
            }

            if (var1 != 24) {
               throw new IllegalStateException();
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bw.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("cn")
   final void method1327(String var1) {
      if (var1 != null) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(1953768461)) {
            if (this.field593.method10267(var2, 1722157389)) {
               client.field825.method9283(617237875);
               classJL var3 = classEF.method3863(classJS.FRIENDLIST_DEL, client.field796.field1533, (byte)87);
               classXY.method13029(var3.field3341, classAU.method709(var1, (byte)98), (byte)-87);
               var3.field3341.method13054(var1, (short)10772);
               client.field796.method3374(var3, -1301097035);
            }

            classPW.method9403(-885148365);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;Laae;)Z")
   @ObfuscatedName("td")
   public static boolean method1335(classBW var0, classAAE var1) {
      Friend var2 = (Friend)var0.field593.method10256(var1, 282151994);
      return var2 != null && ChatPlayer.method10010(var2, -2034169450);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ak")
   static final void method1262(String var0) {
      String var1 = classKK.field3795 + var0 + classKK.field3796;
      classPH.method8924(30, "", var1, 1436535380);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("bb")
   final void method1283(classXY var1, int var2) {
      this.field593.method10182(var1, var2, (byte)1);
      this.field594 = 596332666;
      classPW.method9403(-527562367);
   }

   @ObfuscatedSignature(descriptor = "(Laae;Z)Z")
   @ObfuscatedName("ti")
   public boolean method1342(classAAE var1, boolean var2) {
      return this.method1295(var1, var2, 728254179);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;)Z")
   @ObfuscatedName("uz")
   public static boolean method1323(classBW var0) {
      return var0.field591.method10249(2037364072) || var0.field591.method10243(-1164656627) >= 100 && 1 != 1610258521 * client.field848;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("be")
   final void method1289() {
      for (PendingLogin var1 = (PendingLogin)this.field593.field5810.method7914(); var1 != null; var1 = (PendingLogin)Deque.method7921(this.field593.field5810)) {
         if (53410780 * var1.field5665 < classDD.method2988(16711680) / 1000L - 5L) {
            if (var1.field5664 > 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3769, 1397022553);
            }

            if (var1.field5664 == 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3704, 1817164469);
            }

            classVM.method12069(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lbw;Lxy;II)V")
   @ObfuscatedName("ju")
   public static void method1284(classBW var0, classXY var1, int var2, int var3) {
      if (var0 == null) {
         var0.method1285(var1, var2, var2);
      }

      try {
         var0.field593.method10182(var1, var2, (byte)1);
         var0.field594 = 596332666;
         classPW.method9403(182571929);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bw.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   final void method1292() {
      this.field594 = 0;
      this.field593.method10240((short)22091);
      this.field591.method10240((short)18338);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("bt")
   static final void method1274(String var0) {
      String var1 = classKK.field3797 + var0 + classKK.field3798;
      classPH.method8924(30, "", var1, 1232874355);
   }

   @ObfuscatedSignature(descriptor = "()Lrn;")
   @ObfuscatedName("mu")
   public FriendContainer method1343() {
      return this.field593;
   }

   @ObfuscatedSignature(descriptor = "(Laae;Z)Z")
   @ObfuscatedName("bl")
   final boolean method1296(classAAE var1, boolean var2) {
      if (null == var1) {
         return false;
      } else {
         return var1.equals(classOE.field4841.vmethod255((byte)75)) ? true : this.field593.method10181(var1, var2, -2083902275);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("bh")
   final boolean method1300(classAAE var1) {
      return var1 == null ? false : NameableContainer.method10250(this.field591, var1, (byte)10);
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("bz")
   final boolean method1301(classAAE var1) {
      return var1 == null ? false : NameableContainer.method10250(this.field591, var1, (byte)10);
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("cu")
   final boolean method1302(classAAE var1) {
      return var1 == null ? false : NameableContainer.method10250(this.field591, var1, (byte)10);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("cb")
   final void method1306(String var1) {
      if (null != var1) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(1220803059)) {
            if (method1311(this, (byte)23)) {
               String var5 = classKK.field3645;
               classPH.method8924(30, "", var5, 1160042620);
            } else {
               classAAE var3 = classOE.field4841.vmethod255((byte)115);
               if (null != var3 && var3.equals(var2)) {
                  classAD.method323(-855954109);
               } else if (this.method1295(var2, false, 1675057866)) {
                  String var6 = var1 + classKK.field3790;
                  classPH.method8924(30, "", var6, 1568798867);
               } else if (this.method1299(var2, -1181618808)) {
                  classNA.method7801(var1, 2123265837);
               } else {
                  classJL var4 = classEF.method3863(classJS.FRIENDLIST_ADD, client.field796.field1533, (byte)71);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)84), (byte)-115);
                  var4.field3341.method13054(var1, (short)25494);
                  client.field796.method3374(var4, -1301097035);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ct")
   final void method1307(String var1) {
      if (null != var1) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(1661536918)) {
            if (method1311(this, (byte)35)) {
               String var5 = classKK.field3645;
               classPH.method8924(30, "", var5, 1320406536);
            } else {
               classAAE var3 = classOE.field4841.vmethod255((byte)70);
               if (null != var3 && var3.equals(var2)) {
                  classAD.method323(-855954109);
               } else if (this.method1295(var2, false, 1867772189)) {
                  String var6 = var1 + classKK.field3790;
                  classPH.method8924(30, "", var6, 1545929195);
               } else if (this.method1299(var2, -1181618808)) {
                  classNA.method7801(var1, 2071478326);
               } else {
                  classJL var4 = classEF.method3863(classJS.FRIENDLIST_ADD, client.field796.field1533, (byte)-83);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)95), (byte)-102);
                  var4.field3341.method13054(var1, (short)3053);
                  client.field796.method3374(var4, -1301097035);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("cp")
   final void method1308(String var1) {
      if (null != var1) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(792654581)) {
            if (method1311(this, (byte)61)) {
               String var5 = classKK.field3645;
               classPH.method8924(30, "", var5, 1127978586);
            } else {
               classAAE var3 = classOE.field4841.vmethod255((byte)109);
               if (null != var3 && var3.equals(var2)) {
                  classAD.method323(-855954109);
               } else if (this.method1295(var2, false, 1001765526)) {
                  String var6 = var1 + classKK.field3790;
                  classPH.method8924(30, "", var6, 1800927397);
               } else if (this.method1299(var2, -1181618808)) {
                  classNA.method7801(var1, 1916589239);
               } else {
                  classJL var4 = classEF.method3863(classJS.FRIENDLIST_ADD, client.field796.field1533, (byte)-64);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)124), (byte)-118);
                  var4.field3341.method13054(var1, (short)5194);
                  client.field796.method3374(var4, -1301097035);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("cq")
   final void method1309(String var1) {
      if (null != var1) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(166348403)) {
            if (method1311(this, (byte)42)) {
               String var5 = classKK.field3645;
               classPH.method8924(30, "", var5, 1170817750);
            } else {
               classAAE var3 = classOE.field4841.vmethod255((byte)102);
               if (null != var3 && var3.equals(var2)) {
                  classAD.method323(-855954109);
               } else if (this.method1295(var2, false, 1316839156)) {
                  String var6 = var1 + classKK.field3790;
                  classPH.method8924(30, "", var6, 1878988503);
               } else if (this.method1299(var2, -1181618808)) {
                  classNA.method7801(var1, 2097689307);
               } else {
                  classJL var4 = classEF.method3863(classJS.FRIENDLIST_ADD, client.field796.field1533, (byte)-16);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)98), (byte)-86);
                  var4.field3341.method13054(var1, (short)21222);
                  client.field796.method3374(var4, -1301097035);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lrf;")
   @ObfuscatedName("po")
   public classRF method1344() {
      return this.field591;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("cl")
   final void method1310(String var1) {
      if (null != var1) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(1967711338)) {
            if (method1311(this, (byte)20)) {
               String var5 = classKK.field3645;
               classPH.method8924(30, "", var5, 1755028932);
            } else {
               classAAE var3 = classOE.field4841.vmethod255((byte)49);
               if (null != var3 && var3.equals(var2)) {
                  classAD.method323(-855954109);
               } else if (this.method1295(var2, false, 1051908481)) {
                  String var6 = var1 + classKK.field3790;
                  classPH.method8924(30, "", var6, 1927719525);
               } else if (this.method1299(var2, -1181618808)) {
                  classNA.method7801(var1, 1822477227);
               } else {
                  classJL var4 = classEF.method3863(classJS.FRIENDLIST_ADD, client.field796.field1533, (byte)-16);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)73), (byte)-38);
                  var4.field3341.method13054(var1, (short)9901);
                  client.field796.method3374(var4, -1301097035);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cd")
   final boolean method1313() {
      return this.field593.method10249(2037364072) || this.field593.method10243(-1571057932) >= -699968365 && 1 != client.field848 * 1610258521;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cv")
   final boolean method1314() {
      return this.field593.method10249(2037364072) || this.field593.method10243(-1050473645) >= 200 && 1 != client.field848 * 1610258521;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   final void method1290() {
      for (PendingLogin var1 = (PendingLogin)this.field593.field5810.method7914(); var1 != null; var1 = (PendingLogin)Deque.method7921(this.field593.field5810)) {
         if (1669590409 * var1.field5665 < classDD.method2988(16711680) / 1000L - 5L) {
            if (var1.field5664 > 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3769, 1782922320);
            }

            if (var1.field5664 == 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3704, 1743829179);
            }

            classVM.method12069(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cs")
   final boolean method1315() {
      return this.field593.method10249(2037364072) || this.field593.method10243(-358458507) >= 200 && 1 != client.field848 * 1610258521;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;Laae;Z)Z")
   @ObfuscatedName("ko")
   public static boolean method1297(classBW var0, classAAE var1, boolean var2) {
      if (var0 == null) {
         return var0.method1321();
      } else if (null == var1) {
         return false;
      } else {
         return var1.equals(classOE.field4841.vmethod255((byte)76)) ? true : var0.field593.method10181(var1, var2, -2091803639);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   static final void method1265() {
      String var0 = classKK.field3887;
      classPH.method8924(30, "", var0, 1532789371);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bc")
   static final void method1270() {
      String var0 = classKK.field3791;
      classPH.method8924(30, "", var0, 1450270474);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ca")
   final void method1318(String var1) {
      if (null != var1) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(1600364524)) {
            if (this.method1322((byte)24)) {
               classBA.method749(-706720082);
            } else {
               classAAE var3 = classOE.field4841.vmethod255((byte)63);
               if (null != var3 && var3.equals(var2)) {
                  String var6 = classKK.field3794;
                  classPH.method8924(30, "", var6, 2087967001);
               } else if (this.method1299(var2, -1181618808)) {
                  String var5 = var1 + classKK.field3792;
                  classPH.method8924(30, "", var5, 1745810423);
               } else if (this.method1295(var2, false, 684363451)) {
                  classDT.method3614(var1, (byte)73);
               } else {
                  classJL var4 = classEF.method3863(classJS.IGNORELIST_ADD, client.field796.field1533, (byte)11);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)58), (byte)-71);
                  var4.field3341.method13054(var1, (short)13509);
                  client.field796.method3374(var4, -1301097035);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fd")
   static void method1338(int var0, int var1, int var2) {
      try {
         if (null == classON.field4991 || classOE.field4841.method2344(classON.field4991.field608 * 568212409, classON.field4991.field618, (byte)100)) {
            classOE.field4841.method1651(var0, var1, -1225618138);
         } else if (-1927903443 * client.field975.field6795 > 0) {
            if (var2 <= 665885693) {
               throw new IllegalStateException();
            }

            classON.method8649(var0, var1, (byte)-6);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "bw.fd(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lbw;Ljava/lang/String;I)V")
   @ObfuscatedName("et")
   public static void method1319(classBW var0, String var1, int var2) {
      if (var0 == null) {
         var0.method1320(var1, var2);
      } else {
         try {
            if (null == var1) {
               if (var2 != -169290065) {
                  throw new IllegalStateException();
               }
            } else {
               classAAE var3 = new classAAE(var1, var0.field592);
               if (!var3.method166(720233136)) {
                  if (var2 != -169290065) {
                     throw new IllegalStateException();
                  }
               } else if (var0.method1322((byte)24)) {
                  classBA.method749(-656086396);
               } else {
                  classAAE var4 = classOE.field4841.vmethod255((byte)98);
                  if (null != var4) {
                     if (var2 != -169290065) {
                        throw new IllegalStateException();
                     }

                     if (var4.equals(var3)) {
                        if (var2 != -169290065) {
                           throw new IllegalStateException();
                        }

                        String var8 = classKK.field3794;
                        classPH.method8924(30, "", var8, 1688593320);
                        return;
                     }
                  }

                  if (var0.method1299(var3, -1181618808)) {
                     if (var2 != -169290065) {
                        throw new IllegalStateException();
                     } else {
                        String var7 = var1 + classKK.field3792;
                        classPH.method8924(30, "", var7, 1109837735);
                     }
                  } else if (var0.method1295(var3, false, 1026057647)) {
                     if (var2 != -169290065) {
                        throw new IllegalStateException();
                     } else {
                        classDT.method3614(var1, (byte)54);
                     }
                  } else {
                     classJL var5 = classEF.method3863(classJS.IGNORELIST_ADD, client.field796.field1533, (byte)12);
                     classXY.method13029(var5.field3341, classAU.method709(var1, (byte)78), (byte)-62);
                     var5.field3341.method13054(var1, (short)8390);
                     client.field796.method3374(var5, -1301097035);
                  }
               }
            }
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "bw.ad(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("av")
   static final void method1263(String var0) {
      String var1 = classKK.field3795 + var0 + classKK.field3796;
      classPH.method8924(30, "", var1, 1713221122);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ch")
   final void method1328(String var1) {
      if (var1 != null) {
         classAAE var2 = new classAAE(var1, this.field592);
         if (var2.method166(-4988351)) {
            if (this.field593.method10267(var2, 1239113959)) {
               client.field825.method9283(623098488);
               classJL var3 = classEF.method3863(classJS.FRIENDLIST_DEL, client.field796.field1533, (byte)-102);
               classXY.method13029(var3.field3341, classAU.method709(var1, (byte)91), (byte)-12);
               var3.field3341.method13054(var1, (short)26832);
               client.field796.method3374(var3, -1301097035);
            }

            classPW.method9403(213193272);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;Z)Z")
   @ObfuscatedName("bd")
   final boolean method1298(classAAE var1, boolean var2) {
      if (null == var1) {
         return false;
      } else {
         return var1.equals(classOE.field4841.vmethod255((byte)61)) ? true : this.field593.method10181(var1, var2, -1989858874);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Z)V")
   @ObfuscatedName("cj")
   public final void method1333(String var1, boolean var2) {
      if (var1 != null) {
         classAAE var3 = new classAAE(var1, this.field592);
         if (var3.method166(33006293)) {
            if (this.field591.method10267(var3, -1764000461)) {
               client.field825.method9283(1077766240);
               if (var2) {
                  classJL var4 = classEF.method3863(classJS.IGNORELIST_DEL, client.field796.field1533, (byte)-2);
                  classXY.method13029(var4.field3341, classAU.method709(var1, (byte)95), (byte)-101);
                  var4.field3341.method13054(var1, (short)23043);
                  client.field796.method3374(var4, -1301097035);
               }
            }

            classHG.method6042((byte)-64);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("cx")
   final boolean method1336(classAAE var1) {
      Friend var2 = (Friend)this.field593.method10256(var1, 282151994);
      return var2 != null && ChatPlayer.method10010(var2, -2128771078);
   }

   @ObfuscatedSignature(descriptor = "(IB)Lyy;")
   @ObfuscatedName("ae")
   public static classYY method1266(int var0, byte var1) {
      try {
         switch (var0 >> 16 & 3) {
            case 0:
               return classYY.field7109;
            case 1:
               return classYY.field7113;
            case 2:
               return classYY.field7111;
            case 3:
               return classYY.field7112;
            default:
               throw new IllegalArgumentException("invalid base type");
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bw.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;Laae;)Z")
   @ObfuscatedName("uv")
   public static boolean method1303(classBW var0, classAAE var1) {
      if (var0 == null) {
         var0.method1304(var1);
      }

      return var1 == null ? false : NameableContainer.method10250(var0.field591, var1, (byte)10);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;Ljava/lang/String;)V")
   @ObfuscatedName("xf")
   public static void method1329(classBW var0, String var1) {
      if (var0 == null) {
         var0.method1328(var1);
      } else if (var1 != null) {
         classAAE var2 = new classAAE(var1, var0.field592);
         if (var2.method166(1260526473)) {
            if (var0.field593.method10267(var2, 1644864300)) {
               client.field825.method9283(1426693767);
               classJL var3 = classEF.method3863(classJS.FRIENDLIST_DEL, client.field796.field1533, (byte)110);
               classXY.method13029(var3.field3341, classAU.method709(var1, (byte)100), (byte)-121);
               var3.field3341.method13054(var1, (short)32261);
               client.field796.method3374(var3, -1301097035);
            }

            classPW.method9403(2127711670);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lbw;I)V")
   @ObfuscatedName("mt")
   public static void method1293(classBW var0, int var1) {
      if (var0 == null) {
         var0.method1294(var1);
      } else {
         try {
            var0.field594 = 0;
            var0.field593.method10240((short)25479);
            var0.field591.method10240((short)24231);
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "bw.ao(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lyn;IJ)J")
   @ObfuscatedName("ae")
   static long method1275(IterableHashTable var0, int var1, long var2) {
      try {
         if (var0 == null) {
            return var2;
         } else {
            classVK var4 = (classVK)var0.method13637(var1);
            return var4 == null ? var2 : var4.field6515;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "bw.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;Laao;I)Ljava/lang/String;")
   @ObfuscatedName("az")
   public static String method1259(CharSequence var0, classAAO var1, int var2) {
      try {
         if (var0 == null) {
            return null;
         } else {
            int var3 = 0;

            int var4;
            for (var4 = var0.length(); var3 < var4; var3++) {
               if (var2 >= -225915965) {
                  throw new IllegalStateException();
               }

               boolean var10000;
               label237: {
                  char var6 = var0.charAt(var3);
                  if (160 != var6) {
                     if (var2 >= -225915965) {
                        throw new IllegalStateException();
                     }

                     if (' ' != var6) {
                        if (var2 >= -225915965) {
                           throw new IllegalStateException();
                        }

                        if (var6 != '_') {
                           if (var2 >= -225915965) {
                              throw new IllegalStateException();
                           }

                           if (var6 != '-') {
                              var10000 = false;
                              break label237;
                           }

                           if (var2 >= -225915965) {
                              throw new IllegalStateException();
                           }
                        }
                     }
                  }

                  var10000 = true;
               }

               boolean var5 = var10000;
               if (!var5) {
                  break;
               }

               if (var2 >= -225915965) {
                  throw new IllegalStateException();
               }
            }

            while (var4 > var3) {
               if (var2 >= -225915965) {
                  throw new IllegalStateException();
               }

               boolean var23;
               label218: {
                  char var16 = var0.charAt(var4 - 1);
                  if (160 != var16) {
                     if (var2 >= -225915965) {
                        throw new IllegalStateException();
                     }

                     if (' ' != var16 && '_' != var16) {
                        if (var2 >= -225915965) {
                           throw new IllegalStateException();
                        }

                        if ('-' != var16) {
                           var23 = false;
                           break label218;
                        }

                        if (var2 >= -225915965) {
                           throw new IllegalStateException();
                        }
                     }
                  }

                  var23 = true;
               }

               boolean var14 = var23;
               if (!var14) {
                  break;
               }

               if (var2 >= -225915965) {
                  throw new IllegalStateException();
               }

               var4--;
            }

            int var15 = var4 - var3;
            if (var15 >= 1) {
               if (var2 >= -225915965) {
                  throw new IllegalStateException();
               }

               byte var7;
               if (null == var1) {
                  if (var2 >= -225915965) {
                     throw new IllegalStateException();
                  }

                  var7 = 12;
               } else {
                  switch (-670896561 * var1.field77) {
                     case 6:
                        var7 = 20;
                        break;
                     default:
                        var7 = 12;
                  }
               }

               if (var15 <= var7) {
                  if (var2 >= -225915965) {
                     throw new IllegalStateException();
                  }

                  StringBuilder var17 = new StringBuilder(var15);

                  for (int var18 = var3; var18 < var4; var18++) {
                     if (var2 >= -225915965) {
                        throw new IllegalStateException();
                     }

                     char var8 = var0.charAt(var18);
                     boolean var9;
                     if (Character.isISOControl(var8)) {
                        if (var2 >= -225915965) {
                           throw new IllegalStateException();
                        }

                        var9 = false;
                     } else if (classSN.method10575(var8, -324154109)) {
                        if (var2 >= -225915965) {
                           throw new IllegalStateException();
                        }

                        var9 = true;
                     } else {
                        label257: {
                           char[] var10 = classAAB.field26;

                           for (int var11 = 0; var11 < var10.length; var11++) {
                              if (var2 >= -225915965) {
                                 throw new IllegalStateException();
                              }

                              char var12 = var10[var11];
                              if (var12 == var8) {
                                 if (var2 >= -225915965) {
                                    throw new IllegalStateException();
                                 }

                                 var9 = true;
                                 break label257;
                              }
                           }

                           var10 = classAAB.field27;

                           for (int var21 = 0; var21 < var10.length; var21++) {
                              if (var2 >= -225915965) {
                                 throw new IllegalStateException();
                              }

                              char var22 = var10[var21];
                              if (var22 == var8) {
                                 if (var2 >= -225915965) {
                                    throw new IllegalStateException();
                                 }

                                 var9 = true;
                                 break label257;
                              }
                           }

                           var9 = false;
                        }
                     }

                     if (!var9) {
                        if (var2 >= -225915965) {
                           throw new IllegalStateException();
                        }
                     } else {
                        char var20 = classHZ.method6173(var8, (byte)-53);
                        if (var20 != 0) {
                           var17.append(var20);
                        }
                     }
                  }

                  if (var17.length() == 0) {
                     if (var2 >= -225915965) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }

                  return var17.toString();
               }
            }

            return null;
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "bw.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   final void method1294(int var1) {
      try {
         this.field594 = 0;
         this.field593.method10240((short)25479);
         this.field591.method10240((short)24231);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bw.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("ad")
   final void method1320(String var1, int var2) {
      try {
         if (null == var1) {
            if (var2 != -169290065) {
               throw new IllegalStateException();
            }
         } else {
            classAAE var3 = new classAAE(var1, this.field592);
            if (!var3.method166(720233136)) {
               if (var2 != -169290065) {
                  throw new IllegalStateException();
               }
            } else if (this.method1322((byte)24)) {
               classBA.method771(-656086396);
            } else {
               classAAE var4 = classOE.field4841.vmethod255((byte)98);
               if (null != var4) {
                  if (var2 != -169290065) {
                     throw new IllegalStateException();
                  }

                  if (var4.equals(var3)) {
                     if (var2 != -169290065) {
                        throw new IllegalStateException();
                     }

                     String var8 = classKK.field3830;
                     classPH.method8924(30, "", var8, 1688593320);
                     return;
                  }
               }

               if (this.method1299(var3, -1181618808)) {
                  if (var2 != -169290065) {
                     throw new IllegalStateException();
                  } else {
                     String var7 = var1 + classKK.field3702;
                     classPH.method8924(30, "", var7, 1109837735);
                  }
               } else if (this.method1295(var3, false, 1026057647)) {
                  if (var2 != -169290065) {
                     throw new IllegalStateException();
                  } else {
                     classDT.method3614(var1, (byte)54);
                  }
               } else {
                  classJL var5 = classEF.method3863(classJS.RESUME_P_STRINGDIALOG, client.field796.field1533, (byte)12);
                  classXY.method13029(var5.field3341, classAU.method709(var1, (byte)78), (byte)-62);
                  var5.field3341.method13054(var1, (short)8390);
                  client.field796.method3374(var5, -1301097035);
               }
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "bw.ad(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;)V")
   @ObfuscatedName("dl")
   public static void method1291(classBW var0) {
      for (PendingLogin var1 = (PendingLogin)var0.field593.field5810.method7914(); var1 != null; var1 = (PendingLogin)Deque.method7921(var0.field593.field5810)) {
         if (1669590409 * var1.field5665 < classDD.method2988(16711680) / 1000L - 5L) {
            if (var1.field5664 > 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3769, 1561448384);
            }

            if (var1.field5664 == 0) {
               classPH.method8924(5, "", var1.field5663 + classKK.field3704, 1487014823);
            }

            classVM.method12069(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("aq")
   final boolean method1324(byte var1) {
      try {
         if (!this.field591.method10249(2037364072)) {
            if (var1 != 24) {
               throw new IllegalStateException();
            }

            if (this.field591.method10243(-1185182511) < 100) {
               return false;
            }

            if (var1 != 24) {
               throw new IllegalStateException();
            }

            if (1 == 1610258521 * client.field817) {
               return false;
            }

            if (var1 != 24) {
               throw new IllegalStateException();
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bw.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   final void method1280() {
      this.field594 = -1972978593;
   }

   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("cr")
   final boolean method1304(classAAE var1) {
      return var1 == null ? false : NameableContainer.method10250(this.field591, var1, (byte)10);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)V")
   @ObfuscatedName("ac")
   final void method1285(classXY var1, int var2, int var3) {
      try {
         this.field593.method10182(var1, var2, (byte)1);
         this.field594 = 596332666;
         classPW.method9403(182571929);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bw.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Laae;S)Z")
   @ObfuscatedName("ar")
   final boolean method1337(classAAE var1, short var2) {
      try {
         Friend var3 = (Friend)this.field593.method10256(var1, 282151994);
         if (var3 != null) {
            if (var2 == 160) {
               throw new IllegalStateException();
            }

            if (ChatPlayer.method10010(var3, -2092993019)) {
               if (var2 == 160) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bw.ar(" + ')');
      }
   }
}
