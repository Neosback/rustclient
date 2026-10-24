import java.util.ArrayList;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rv")
public class classRV {
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("az")
   classVP field5855;
   @ObfuscatedSignature(descriptor = "Lao;")
   @ObfuscatedName("af")
   classAO field5853;
   @ObfuscatedSignature(descriptor = "[Lwc;")
   @ObfuscatedName("ag")
   final classWC[] field5847 = new classWC[8];
   @ObfuscatedName("ab")
   boolean field5856;
   @ObfuscatedName("ax")
   long[] field5857;
   @ObfuscatedSignature(descriptor = "[Lwc;")
   @ObfuscatedName("as")
   final classWC[] field5846 = new classWC[8];
   @ToRemove(unused = "true")
   @ObfuscatedName("cb")
   static final int field5852 = 70;
   @ObfuscatedName("ac")
   final ArrayList field5848 = new ArrayList();
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final int field5851 = 14;
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   public static final int field5849 = 33;
   @ObfuscatedSignature(descriptor = "Lpg;")
   @ObfuscatedName("ae")
   classPG field5854;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field5850 = 1024;

   @ObfuscatedSignature(descriptor = "(Lpg;ZZ)V")
   @ObfuscatedName("ac")
   public void method10341(classPG var1, boolean var2, boolean var3) {
      if (this.field5854 != null) {
         if (var1.method8904((byte)2) == this.field5854.method8904((byte)2)) {
            return;
         }

         this.method10346(var2, 387864771);
      }

      this.field5854 = var1;
      this.field5856 = var3;

      for (int var4 = 0; var4 < var1.method8907((byte)124); var4++) {
         this.field5847[var4] = null;
      }

      if (var1.method8914(-192129905) > 0) {
         this.field5857 = new long[var1.method8914(-192129905)];
         long var8 = classDD.method2988(16711680);

         for (int var6 = 0; var6 < var1.method8914(-192129905); var6++) {
            int var7 = classIT.method6357(
               classPG.method8915(var1, var6, (byte)0).field6782 * 1662242418, classPG.method8915(var1, var6, (byte)0).field6781 * -1823764067, -1925061547
            );
            this.field5857[var6] = var7 + var8;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;Lao;I)V")
   @ObfuscatedName("az")
   public void method10339(classVP var1, classAO var2, int var3) {
      try {
         this.field5855 = var1;
         this.field5853 = var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rv.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrv;Z)V")
   @ObfuscatedName("ks")
   public static void method10345(classRV var0, boolean var1) {
      if (null != var0.field5854) {
         for (int var2 = 0; var2 < var0.field5854.method8907((byte)96); var2++) {
            if (null != var0.field5847[var2]) {
               if (var1) {
                  var0.method10356(var0.field5847[var2], 1690745559);
               } else if (var0.field5847[var2].method12373(120041229) != null) {
                  var0.field5853.method619(var0.field5847[var2].method12373(120041229));
               }

               var0.field5847[var2] = null;
            }
         }

         for (int var3 = 0; var3 < var0.field5854.method8914(-192129905); var3++) {
            if (null != var0.field5846[var3]) {
               if (var1) {
                  var0.method10356(var0.field5846[var3], 1335840193);
               } else if (var0.field5846[var3].method12373(120041229) != null) {
                  var0.field5853.method619(var0.field5846[var3].method12373(120041229));
               }

               var0.field5846[var3] = null;
            }
         }

         var0.field5854 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpg;ZZI)V")
   @ObfuscatedName("af")
   public void method10342(classPG var1, boolean var2, boolean var3, int var4) {
      try {
         if (this.field5854 != null) {
            if (var4 != 2053173530) {
               throw new IllegalStateException();
            }

            if (var1.method8904((byte)2) == this.field5854.method8904((byte)2)) {
               if (var4 != 2053173530) {
                  throw new IllegalStateException();
               }

               return;
            }

            this.method10346(var2, 2047124406);
         }

         this.field5854 = var1;
         this.field5856 = var3;

         for (int var5 = 0; var5 < var1.method8907((byte)85); var5++) {
            if (var4 != 2053173530) {
               throw new IllegalStateException();
            }

            this.field5847[var5] = null;
         }

         if (var1.method8914(-192129905) > 0) {
            if (var4 != 2053173530) {
               throw new IllegalStateException();
            }

            this.field5857 = new long[var1.method8914(-192129905)];
            long var10 = classDD.method2988(16711680);

            for (int var7 = 0; var7 < var1.method8914(-192129905); var7++) {
               if (var4 != 2053173530) {
                  throw new IllegalStateException();
               }

               int var8 = classIT.method6357(
                  classPG.method8915(var1, var7, (byte)0).field6782 * 1861224747, classPG.method8915(var1, var7, (byte)0).field6781 * -1823764067, 309965552
               );
               this.field5857[var7] = var8 + var10;
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "rv.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("ae")
   public void method10346(boolean var1, int var2) {
      try {
         if (null != this.field5854) {
            for (int var3 = 0; var3 < this.field5854.method8907((byte)70); var3++) {
               if (var2 == -1025573428) {
                  return;
               }

               if (null != this.field5847[var3]) {
                  if (var2 == -1025573428) {
                     throw new IllegalStateException();
                  }

                  if (var1) {
                     if (var2 == -1025573428) {
                        throw new IllegalStateException();
                     }

                     this.method10356(this.field5847[var3], -1401181198);
                  } else if (this.field5847[var3].method12373(120041229) != null) {
                     this.field5853.method619(this.field5847[var3].method12373(120041229));
                  }

                  this.field5847[var3] = null;
               }
            }

            for (int var5 = 0; var5 < this.field5854.method8914(-192129905); var5++) {
               if (var2 == -1025573428) {
                  throw new IllegalStateException();
               }

               if (null != this.field5846[var5]) {
                  if (var2 == -1025573428) {
                     throw new IllegalStateException();
                  }

                  if (var1) {
                     if (var2 == -1025573428) {
                        throw new IllegalStateException();
                     }

                     this.method10356(this.field5846[var5], 1852675166);
                  } else if (this.field5846[var5].method12373(120041229) != null) {
                     if (var2 == -1025573428) {
                        return;
                     }

                     this.field5853.method619(this.field5846[var5].method12373(120041229));
                  }

                  this.field5846[var5] = null;
               }
            }

            this.field5854 = null;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rv.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ab")
   public void method10348(int var1, byte var2) {
      try {
         if (!this.field5848.isEmpty()) {
            if (var2 <= 0) {
               throw new IllegalStateException();
            } else {
               Iterator var12 = this.field5848.iterator();

               while (var12.hasNext()) {
                  classWC var14 = (classWC)var12.next();
                  if (classWC.method12367(var14, -433946480) == 0) {
                     if (var14.method12373(120041229) != null) {
                        if (var2 <= 0) {
                           throw new IllegalStateException();
                        }

                        this.field5853.method619(var14.method12373(120041229));
                     }

                     var12.remove();
                  } else {
                     var14.method12365((byte)-79);
                  }
               }
            }
         } else if (this.field5854 != null) {
            for (int var3 = 0; var3 < this.field5854.method8907((byte)65); var3++) {
               if (var2 <= 0) {
                  return;
               }

               classWC var4 = this.field5847[var3];
               if (var4 != null) {
                  if (var2 <= 0) {
                     return;
                  }

                  if (var4.method12373(120041229) != null) {
                     if (var2 <= 0) {
                        throw new IllegalStateException();
                     }

                     var4.method12358(var1, var1, this.field5854.method8921(530502105), 1874267267);
                     var4.method12365((byte)43);
                     continue;
                  }
               }

               this.field5847[var3] = method10352(this, classPG.method8911(this.field5854, var3, -21568319), var1, -1, -1721476862);
            }

            for (int var11 = 0; var11 < this.field5854.method8914(-192129905); var11++) {
               if (var2 <= 0) {
                  throw new IllegalStateException();
               }

               classWC var13 = this.field5846[var11];
               if (var13 != null) {
                  if (var2 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (var13.method12373(120041229) != null) {
                     if (var2 <= 0) {
                        return;
                     }

                     var13.method12358(var1, var1, this.field5854.method8921(881848896), 2020061076);
                     var13.method12365((byte)50);
                     continue;
                  }
               }

               classWW var5 = classPG.method8915(this.field5854, var11, (byte)0);
               if (var5 != null) {
                  if (var2 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (null != var5.field6783) {
                     if (var2 <= 0) {
                        return;
                     }

                     long var6 = classDD.method2988(16711680);
                     if (var6 >= this.field5857[var11]) {
                        if (var2 <= 0) {
                           throw new IllegalStateException();
                        }

                        if (var1 > 0) {
                           if (var2 <= 0) {
                              return;
                           }

                           int var8 = var5.field6783[(int)(Math.random() * var5.field6783.length)];
                           this.field5846[var11] = method10352(this, var8, var1, 0, -1822202006);
                           if (null != this.field5846[var11]) {
                              this.field5846[var11]
                                 .method12362(
                                    var1,
                                    var1,
                                    classWD.method12396(this.field5854.method8921(804985711), -1095100093),
                                    this.field5854.method8921(854698690).method12394((byte)33),
                                    1596960535
                                 );
                              this.field5846[var11].method12365((byte)4);
                           }

                           int var9 = classIT.method6357(1861224747 * var5.field6782, var5.field6781 * -1823764067, 1921385445);
                           this.field5857[var11] = var9 + var6;
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "rv.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrv;IIII)Lwc;")
   @ObfuscatedName("mi")
   public static classWC method10352(classRV var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method10354(var1, var1, var1, var1);
      }

      try {
         int var5 = var0.field5855.method11943(var1, -1845183590) - 1;
         classAL var6 = classAL.method434(var0.field5855, var1, var5);
         if (null != var6) {
            if (var4 >= 988361718) {
               throw new IllegalStateException();
            } else {
               classAJ var7;
               if (0 == var5) {
                  if (var4 >= 988361718) {
                     throw new IllegalStateException();
                  }

                  var7 = var6.method438(var0.field5856);
               } else {
                  if (1 != var5) {
                     return null;
                  }

                  if (var4 >= 988361718) {
                     throw new IllegalStateException();
                  }

                  var7 = var6.method442();
               }

               classWC var8 = new classWC(var7, var2, var3);
               if (var8.method12373(120041229) == null) {
                  if (var4 >= 988361718) {
                     throw new IllegalStateException();
                  } else {
                     return null;
                  }
               } else {
                  var0.field5853.method618(var8.method12373(120041229));
                  return var8;
               }
            }
         } else {
            return null;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "rv.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrv;Lwc;)V")
   @ObfuscatedName("dx")
   public static void method10355(classRV var0, classWC var1) {
      if (var0 == null) {
         var0.method10358(var1);
      }

      var1.method12362(
         0,
         classWC.method12367(var1, 252402109),
         var0.field5854.method8921(1263011861).method12404((byte)71),
         var0.field5854.method8921(1601755666).method12402(205116196),
         -2125913153
      );
      var1.method12365((byte)-15);
      var0.field5848.add(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lwc;I)V")
   @ObfuscatedName("as")
   void method10356(classWC var1, int var2) {
      try {
         var1.method12362(
            0,
            classWC.method12367(var1, -1755472270),
            this.field5854.method8921(1318887486).method12404((byte)76),
            this.field5854.method8921(1701894842).method12402(-145399264),
            -777956723
         );
         var1.method12365((byte)55);
         this.field5848.add(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rv.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;Lao;)V")
   @ObfuscatedName("ax")
   public void method10340(classVP var1, classAO var2) {
      this.field5855 = var1;
      this.field5853 = var2;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ad")
   public void method10349(int var1) {
      if (!this.field5848.isEmpty()) {
         Iterator var10 = this.field5848.iterator();

         while (var10.hasNext()) {
            classWC var12 = (classWC)var10.next();
            if (classWC.method12367(var12, -1627845312) == 0) {
               if (var12.method12373(120041229) != null) {
                  this.field5853.method619(var12.method12373(120041229));
               }

               var10.remove();
            } else {
               var12.method12365((byte)-57);
            }
         }
      } else if (this.field5854 != null) {
         for (int var2 = 0; var2 < this.field5854.method8907((byte)124); var2++) {
            classWC var3 = this.field5847[var2];
            if (var3 != null && var3.method12373(120041229) != null) {
               var3.method12358(var1, var1, this.field5854.method8921(463810449), 1884656411);
               var3.method12365((byte)-78);
            } else {
               this.field5847[var2] = method10352(this, classPG.method8911(this.field5854, var2, 1774885302), var1, -1, -1840289314);
            }
         }

         for (int var9 = 0; var9 < this.field5854.method8914(-192129905); var9++) {
            classWC var11 = this.field5846[var9];
            if (var11 != null && var11.method12373(120041229) != null) {
               var11.method12358(var1, var1, this.field5854.method8921(2050403053), 2142096633);
               var11.method12365((byte)-41);
            } else {
               classWW var4 = classPG.method8915(this.field5854, var9, (byte)0);
               if (var4 != null && null != var4.field6783) {
                  long var5 = classDD.method2988(16711680);
                  if (var5 >= this.field5857[var9] && var1 > 0) {
                     int var7 = var4.field6783[(int)(Math.random() * var4.field6783.length)];
                     this.field5846[var9] = method10352(this, var7, var1, 0, 894732604);
                     if (null != this.field5846[var9]) {
                        this.field5846[var9]
                           .method12362(
                              var1,
                              var1,
                              classWD.method12396(this.field5854.method8921(1701400838), -1174354542),
                              this.field5854.method8921(-1064782206).method12394((byte)74),
                              1135225719
                           );
                        this.field5846[var9].method12365((byte)10);
                     }

                     int var8 = classIT.method6357(1861224747 * var4.field6782, var4.field6781 * -1823764067, 1459813555);
                     this.field5857[var9] = var8 + var5;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpg;ZZ)V")
   @ObfuscatedName("aa")
   public void method10343(classPG var1, boolean var2, boolean var3) {
      if (this.field5854 != null) {
         if (var1.method8904((byte)2) == this.field5854.method8904((byte)2)) {
            return;
         }

         this.method10346(var2, 1436660051);
      }

      this.field5854 = var1;
      this.field5856 = var3;

      for (int var4 = 0; var4 < var1.method8907((byte)75); var4++) {
         this.field5847[var4] = null;
      }

      if (var1.method8914(-192129905) > 0) {
         this.field5857 = new long[var1.method8914(-192129905)];
         long var8 = classDD.method2988(16711680);

         for (int var6 = 0; var6 < var1.method8914(-192129905); var6++) {
            int var7 = classIT.method6357(
               classPG.method8915(var1, var6, (byte)0).field6782 * 1861224747, classPG.method8915(var1, var6, (byte)0).field6781 * -1823764067, -1371640211
            );
            this.field5857[var6] = var7 + var8;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("al")
   public void method10347(boolean var1) {
      if (null != this.field5854) {
         for (int var2 = 0; var2 < this.field5854.method8907((byte)84); var2++) {
            if (null != this.field5847[var2]) {
               if (var1) {
                  this.method10356(this.field5847[var2], -682134017);
               } else if (this.field5847[var2].method12373(120041229) != null) {
                  this.field5853.method619(this.field5847[var2].method12373(120041229));
               }

               this.field5847[var2] = null;
            }
         }

         for (int var3 = 0; var3 < this.field5854.method8914(-192129905); var3++) {
            if (null != this.field5846[var3]) {
               if (var1) {
                  this.method10356(this.field5846[var3], 2025263114);
               } else if (this.field5846[var3].method12373(120041229) != null) {
                  this.field5853.method619(this.field5846[var3].method12373(120041229));
               }

               this.field5846[var3] = null;
            }
         }

         this.field5854 = null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lclient;)V")
   @ObfuscatedName("lp")
   public static void method10359(client var0) {
      if (var0 == null) {
         var0.method2130();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aq")
   public void method10350(int var1) {
      if (!this.field5848.isEmpty()) {
         Iterator var10 = this.field5848.iterator();

         while (var10.hasNext()) {
            classWC var12 = (classWC)var10.next();
            if (classWC.method12367(var12, -40593813) == 0) {
               if (var12.method12373(120041229) != null) {
                  this.field5853.method619(var12.method12373(120041229));
               }

               var10.remove();
            } else {
               var12.method12365((byte)-53);
            }
         }
      } else if (this.field5854 != null) {
         for (int var2 = 0; var2 < this.field5854.method8907((byte)87); var2++) {
            classWC var3 = this.field5847[var2];
            if (var3 != null && var3.method12373(120041229) != null) {
               var3.method12358(var1, var1, this.field5854.method8921(-1791054698), 1841381729);
               var3.method12365((byte)114);
            } else {
               this.field5847[var2] = method10352(this, classPG.method8911(this.field5854, var2, -1522190981), var1, -1, -1082416350);
            }
         }

         for (int var9 = 0; var9 < this.field5854.method8914(-192129905); var9++) {
            classWC var11 = this.field5846[var9];
            if (var11 != null && var11.method12373(120041229) != null) {
               var11.method12358(var1, var1, this.field5854.method8921(-528275792), 2078769831);
               var11.method12365((byte)55);
            } else {
               classWW var4 = classPG.method8915(this.field5854, var9, (byte)0);
               if (var4 != null && null != var4.field6783) {
                  long var5 = classDD.method2988(16711680);
                  if (var5 >= this.field5857[var9] && var1 > 0) {
                     int var7 = var4.field6783[(int)(Math.random() * var4.field6783.length)];
                     this.field5846[var9] = method10352(this, var7, var1, 0, 954404396);
                     if (null != this.field5846[var9]) {
                        this.field5846[var9]
                           .method12362(
                              var1,
                              var1,
                              classWD.method12396(this.field5854.method8921(-1282030007), -3680128),
                              this.field5854.method8921(-149596976).method12394((byte)56),
                              -1594457878
                           );
                        this.field5846[var9].method12365((byte)-77);
                     }

                     int var8 = classIT.method6357(1861224747 * var4.field6782, var4.field6781 * -1823764067, 2110517802);
                     this.field5857[var9] = var8 + var5;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpg;ZZ)V")
   @ObfuscatedName("ao")
   public void method10344(classPG var1, boolean var2, boolean var3) {
      if (this.field5854 != null) {
         if (var1.method8904((byte)2) == this.field5854.method8904((byte)2)) {
            return;
         }

         this.method10346(var2, 638031046);
      }

      this.field5854 = var1;
      this.field5856 = var3;

      for (int var4 = 0; var4 < var1.method8907((byte)88); var4++) {
         this.field5847[var4] = null;
      }

      if (var1.method8914(-192129905) > 0) {
         this.field5857 = new long[var1.method8914(-192129905)];
         long var8 = classDD.method2988(16711680);

         for (int var6 = 0; var6 < var1.method8914(-192129905); var6++) {
            int var7 = classIT.method6357(
               classPG.method8915(var1, var6, (byte)0).field6782 * -1296940717, classPG.method8915(var1, var6, (byte)0).field6781 * -1823764067, -358676291
            );
            this.field5857[var6] = var7 + var8;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ay")
   public void method10351(int var1) {
      if (!this.field5848.isEmpty()) {
         Iterator var10 = this.field5848.iterator();

         while (var10.hasNext()) {
            classWC var12 = (classWC)var10.next();
            if (classWC.method12367(var12, -468597562) == 0) {
               if (var12.method12373(120041229) != null) {
                  this.field5853.method619(var12.method12373(120041229));
               }

               var10.remove();
            } else {
               var12.method12365((byte)-52);
            }
         }
      } else if (this.field5854 != null) {
         for (int var2 = 0; var2 < this.field5854.method8907((byte)115); var2++) {
            classWC var3 = this.field5847[var2];
            if (var3 != null && var3.method12373(120041229) != null) {
               var3.method12358(var1, var1, this.field5854.method8921(-1612172406), 2087623526);
               var3.method12365((byte)1);
            } else {
               this.field5847[var2] = method10352(this, classPG.method8911(this.field5854, var2, -254296276), var1, -1, 716533035);
            }
         }

         for (int var9 = 0; var9 < this.field5854.method8914(-192129905); var9++) {
            classWC var11 = this.field5846[var9];
            if (var11 != null && var11.method12373(120041229) != null) {
               var11.method12358(var1, var1, this.field5854.method8921(515509743), 1993251071);
               var11.method12365((byte)-76);
            } else {
               classWW var4 = classPG.method8915(this.field5854, var9, (byte)0);
               if (var4 != null && null != var4.field6783) {
                  long var5 = classDD.method2988(16711680);
                  if (var5 >= this.field5857[var9] && var1 > 0) {
                     int var7 = var4.field6783[(int)(Math.random() * var4.field6783.length)];
                     this.field5846[var9] = method10352(this, var7, var1, 0, -166637560);
                     if (null != this.field5846[var9]) {
                        this.field5846[var9]
                           .method12362(
                              var1,
                              var1,
                              classWD.method12396(this.field5854.method8921(-6662426), 672291611),
                              this.field5854.method8921(1372424398).method12394((byte)45),
                              844571001
                           );
                        this.field5846[var9].method12365((byte)-26);
                     }

                     int var8 = classIT.method6357(1861224747 * var4.field6782, var4.field6781 * -1200249787, 1937377641);
                     this.field5857[var9] = var8 + var5;
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrv;Lwc;)V")
   @ObfuscatedName("kr")
   public static void method10357(classRV var0, classWC var1) {
      if (var0 == null) {
         var0.method10358(var1);
      }

      var1.method12362(
         0,
         classWC.method12367(var1, 737960846),
         var0.field5854.method8921(-1294237009).method12404((byte)-19),
         var0.field5854.method8921(2134314988).method12402(1120761904),
         -1545970898
      );
      var1.method12365((byte)-24);
      var0.field5848.add(var1);
   }

   @ObfuscatedSignature(descriptor = "(III)Lwc;")
   @ObfuscatedName("ap")
   classWC method10353(int var1, int var2, int var3) {
      int var4 = this.field5855.method11943(var1, -1476236100) - 1;
      classAL var5 = classAL.method434(this.field5855, var1, var4);
      if (null != var5) {
         classAJ var6;
         if (0 == var4) {
            var6 = var5.method438(this.field5856);
         } else {
            if (1 != var4) {
               return null;
            }

            var6 = var5.method442();
         }

         classWC var7 = new classWC(var6, var2, var3);
         if (var7.method12373(120041229) == null) {
            return null;
         } else {
            this.field5853.method618(var7.method12373(120041229));
            return var7;
         }
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Lwc;")
   @ObfuscatedName("ag")
   classWC method10354(int var1, int var2, int var3, int var4) {
      try {
         int var5 = this.field5855.method11943(var1, -1845183590) - 1;
         classAL var6 = classAL.method435(this.field5855, var1, var5);
         if (null != var6) {
            if (var4 >= 988361718) {
               throw new IllegalStateException();
            } else {
               classAJ var7;
               if (0 == var5) {
                  if (var4 >= 988361718) {
                     throw new IllegalStateException();
                  }

                  var7 = var6.method446(this.field5856);
               } else {
                  if (1 != var5) {
                     return null;
                  }

                  if (var4 >= 988361718) {
                     throw new IllegalStateException();
                  }

                  var7 = var6.method442();
               }

               classWC var8 = new classWC(var7, var2, var3);
               if (var8.method12373(120041229) == null) {
                  if (var4 >= 988361718) {
                     throw new IllegalStateException();
                  } else {
                     return null;
                  }
               } else {
                  this.field5853.method618(var8.method12373(120041229));
                  return var8;
               }
            }
         } else {
            return null;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "rv.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwc;)V")
   @ObfuscatedName("au")
   void method10358(classWC var1) {
      var1.method12362(
         0,
         classWC.method12367(var1, 737960846),
         this.field5854.method8921(-1294237009).method12404((byte)-19),
         this.field5854.method8921(2134314988).method12407(1120761904),
         -1545970898
      );
      var1.method12365((byte)-24);
      this.field5848.add(var1);
   }
}
