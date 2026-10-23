import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rn")
public class FriendContainer extends NameableContainer implements net.runelite.api.FriendContainer {
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field5815 = 64;
   @ObfuscatedName("ag")
   int field5811;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5816 = 400;
   @ObfuscatedSignature(descriptor = "Lnk;")
   @ObfuscatedName("ab")
   public Deque field5810 = new Deque();
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field5813 = 200;
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("ae")
   final classAAO field5812;
   @ToRemove(unused = "true")
   @ObfuscatedName("bj")
   public static final int field5817 = 71;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field5814 = 25;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   static final String field5818 = "game-session/v1/tokens";

   @ObfuscatedSignature(descriptor = "(Laae;Z)Z")
   @ObfuscatedName("aj")
   public boolean method10176(classAAE var1, boolean var2) {
      Friend var3 = (Friend)this.method10256(var1, 282151994);
      return null == var3 ? false : !var2 || 1407608587 * var3.field5667 != 0;
   }

   @ObfuscatedSignature(descriptor = "(I)Lrr;")
   @ObfuscatedName("az")
   @Override
   Nameable vmethod555(int var1) {
      try {
         return new Friend();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rn.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)[Lrr;")
   @ObfuscatedName("af")
   @Override
   Nameable[] vmethod558(int var1, byte var2) {
      try {
         return new Friend[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rn.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IB)V")
   @ObfuscatedName("ax")
   public void method10182(classXY var1, int var2, byte var3) {
      try {
         while (702114061 * var1.field6953 < var2) {
            if (var3 != 1) {
               throw new IllegalStateException();
            }

            boolean var10000;
            if (var1.method13095(-346779531) == 1) {
               if (var3 != 1) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var4 = var10000;
            classAAE var5 = new classAAE(var1.method13127(-164489557), this.field5812);
            classAAE var6 = new classAAE(var1.method13127(963455264), this.field5812);
            int var7 = var1.method13099(-553521674);
            int var8 = var1.method13095(-346779531);
            int var9 = var1.method13095(-346779531);
            if ((var9 & 2) != 0) {
               if (var3 != 1) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var10 = var10000;
            if ((var9 & 1) != 0) {
               if (var3 != 1) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var11 = var10000;
            if (var7 > 0) {
               if (var3 != 1) {
                  return;
               }

               var1.method13127(602408241);
               var1.method13095(-346779531);
               classXY.method13113(var1, (byte)1);
            }

            var1.method13127(2114272159);
            if (null != var5) {
               if (var3 != 1) {
                  throw new IllegalStateException();
               }

               if (var5.method166(84304373)) {
                  Friend var12 = (Friend)this.method10258(var5, 215663160);
                  if (var4) {
                     if (var3 != 1) {
                        throw new IllegalStateException();
                     }

                     Friend var13 = (Friend)this.method10258(var6, -213415090);
                     if (var13 != null) {
                        if (var3 != 1) {
                           throw new IllegalStateException();
                        }

                        if (var13 != var12) {
                           if (var3 != 1) {
                              return;
                           }

                           if (null != var12) {
                              NameableContainer.method10270(this, var13, 85928142);
                           } else {
                              var12 = var13;
                           }
                        }
                     }
                  }

                  if (null != var12) {
                     if (var3 != 1) {
                        throw new IllegalStateException();
                     }

                     this.method10286(var12, var5, var6, 484569748);
                     if (var7 != var12.field5667 * -760305529) {
                        if (var3 != 1) {
                           throw new IllegalStateException();
                        }

                        boolean var16 = true;

                        for (PendingLogin var14 = (PendingLogin)this.field5810.method7914();
                           var14 != null;
                           var14 = (PendingLogin)Deque.method7921(this.field5810)
                        ) {
                           if (var3 != 1) {
                              throw new IllegalStateException();
                           }

                           if (var14.field5663.equals(var5)) {
                              if (var3 != 1) {
                                 return;
                              }

                              if (var7 != 0) {
                                 if (var3 != 1) {
                                    throw new IllegalStateException();
                                 }

                                 if (var14.field5664 == 0) {
                                    classVM.method12069(var14);
                                    var16 = false;
                                    continue;
                                 }
                              }

                              if (0 == var7 && var14.field5664 != 0) {
                                 if (var3 != 1) {
                                    throw new IllegalStateException();
                                 }

                                 classVM.method12069(var14);
                                 var16 = false;
                              }
                           }
                        }

                        if (var16) {
                           Deque.method7911(this.field5810, new PendingLogin(var5, var7));
                        }
                     }
                  } else {
                     if (this.method10243(-1891707521) >= 400) {
                        continue;
                     }

                     if (var3 != 1) {
                        return;
                     }

                     var12 = (Friend)this.method10276(var5, var6, (byte)1);
                  }

                  if (var7 != var12.field5667 * -760305529) {
                     if (var3 != 1) {
                        throw new IllegalStateException();
                     }

                     var12.field5668 = ((this.field5811 += -546079269) * 1336292947 - 1) * 282868315;
                     if (-1 == var12.field5667 * -760305529 && 0 == var7) {
                        if (var3 != 1) {
                           throw new IllegalStateException();
                        }

                        var12.field5668 = -(var12.field5668 * 1);
                     }

                     var12.field5667 = var7 * 849976631;
                  }

                  var12.field5669 = -998616451 * var8;
                  var12.field5827 = var10;
                  var12.field5826 = var11;
                  continue;
               }

               if (var3 != 1) {
                  throw new IllegalStateException();
               }
            }

            throw new IllegalStateException();
         }

         this.method10284((byte)52);
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "rn.ax(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrn;Laae;Z)Z")
   @ObfuscatedName("re")
   public static boolean method10177(FriendContainer var0, classAAE var1, boolean var2) {
      if (var0 == null) {
         var0.getPendingLogins();
      }

      Friend var3 = (Friend)var0.method10256(var1, 282151994);
      return null == var3 ? false : !var2 || -1495204789 * var3.field5667 != 0;
   }

   @ObfuscatedSignature(descriptor = "()Lrr;")
   @ObfuscatedName("ab")
   @Override
   Nameable vmethod556() {
      return new Friend();
   }

   public FriendContainer(classAAO var1) {
      super(400);
      this.field5811 = -546079269;
      this.field5812 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lrr;")
   @ObfuscatedName("ag")
   @Override
   Nameable vmethod557() {
      return new Friend();
   }

   @ObfuscatedSignature(descriptor = "(I)[Lrr;")
   @ObfuscatedName("as")
   @Override
   Nameable[] vmethod559(int var1) {
      return new Friend[var1];
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ay")
   public void method10183(classXY var1, int var2) {
      while (702114061 * var1.field6953 < var2) {
         boolean var3 = var1.method13095(-346779531) == 1;
         classAAE var4 = new classAAE(var1.method13127(163959395), this.field5812);
         classAAE var5 = new classAAE(var1.method13127(-1111492227), this.field5812);
         int var6 = var1.method13099(-1321718598);
         int var7 = var1.method13095(-346779531);
         int var8 = var1.method13095(-346779531);
         boolean var9 = (var8 & 2) != 0;
         boolean var10 = (var8 & 1) != 0;
         if (var6 > 0) {
            var1.method13127(-1244773458);
            var1.method13095(-346779531);
            classXY.method13113(var1, (byte)1);
         }

         var1.method13127(-1358535128);
         if (null == var4 || !var4.method166(-380005946)) {
            throw new IllegalStateException();
         }

         Friend var11 = (Friend)this.method10258(var4, -483768489);
         if (var3) {
            Friend var12 = (Friend)this.method10258(var5, 10049706);
            if (var12 != null && var12 != var11) {
               if (null != var11) {
                  NameableContainer.method10270(this, var12, -1079214284);
               } else {
                  var11 = var12;
               }
            }
         }

         if (null != var11) {
            this.method10286(var11, var4, var5, 845283771);
            if (var6 != var11.field5667 * -637919054) {
               boolean var14 = true;

               for (PendingLogin var13 = (PendingLogin)this.field5810.method7914(); var13 != null; var13 = (PendingLogin)Deque.method7921(this.field5810)) {
                  if (var13.field5663.equals(var4)) {
                     if (var6 != 0 && var13.field5664 == 0) {
                        classVM.method12069(var13);
                        var14 = false;
                     } else if (0 == var6 && var13.field5664 != 0) {
                        classVM.method12069(var13);
                        var14 = false;
                     }
                  }
               }

               if (var14) {
                  Deque.method7911(this.field5810, new PendingLogin(var4, var6));
               }
            }
         } else {
            if (this.method10243(-1135340799) >= 400) {
               continue;
            }

            var11 = (Friend)this.method10276(var4, var5, (byte)-71);
         }

         if (var6 != var11.field5667 * -1028820137) {
            var11.field5668 = ((this.field5811 += -546079269) * 258969951 - 1) * 282868315;
            if (-1 == var11.field5667 * -760305529 && 0 == var6) {
               var11.field5668 = -(var11.field5668 * 1);
            }

            var11.field5667 = var6 * 849976631;
         }

         var11.field5669 = -179047125 * var7;
         var11.field5827 = var9;
         var11.field5826 = var10;
      }

      this.method10284((byte)72);
   }

   public net.runelite.api.Deque getPendingLogins() {
      return this.field5810;
   }

   @ObfuscatedSignature(descriptor = "(Laae;Z)Z")
   @ObfuscatedName("aa")
   public boolean method10178(classAAE var1, boolean var2) {
      Friend var3 = (Friend)this.method10256(var1, 282151994);
      return null == var3 ? false : !var2 || -760305529 * var3.field5667 != 0;
   }

   @ObfuscatedSignature(descriptor = "(Laae;Z)Z")
   @ObfuscatedName("ao")
   public boolean method10179(classAAE var1, boolean var2) {
      Friend var3 = (Friend)this.method10256(var1, 282151994);
      return null == var3 ? false : !var2 || 1591700924 * var3.field5667 != 0;
   }

   @ObfuscatedSignature(descriptor = "(Laae;Z)Z")
   @ObfuscatedName("al")
   public boolean method10180(classAAE var1, boolean var2) {
      Friend var3 = (Friend)this.method10256(var1, 282151994);
      return null == var3 ? false : !var2 || -760305529 * var3.field5667 != 0;
   }

   @ObfuscatedSignature(descriptor = "(II)Lyy;")
   @ObfuscatedName("af")
   public static classYY method10175(int var0, int var1) {
      try {
         return classYR.field7071[var0];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rn.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("aq")
   public void method10184(classXY var1, int var2) {
      while (702114061 * var1.field6953 < var2) {
         boolean var3 = var1.method13095(-346779531) == 1;
         classAAE var4 = new classAAE(var1.method13127(-618593419), this.field5812);
         classAAE var5 = new classAAE(var1.method13127(-609255046), this.field5812);
         int var6 = var1.method13099(-1192861307);
         int var7 = var1.method13095(-346779531);
         int var8 = var1.method13095(-346779531);
         boolean var9 = (var8 & 2) != 0;
         boolean var10 = (var8 & 1) != 0;
         if (var6 > 0) {
            var1.method13127(566881087);
            var1.method13095(-346779531);
            classXY.method13113(var1, (byte)1);
         }

         var1.method13127(1565399809);
         if (null == var4 || !var4.method166(249763947)) {
            throw new IllegalStateException();
         }

         Friend var11 = (Friend)this.method10258(var4, -1919857173);
         if (var3) {
            Friend var12 = (Friend)this.method10258(var5, 888126702);
            if (var12 != null && var12 != var11) {
               if (null != var11) {
                  NameableContainer.method10270(this, var12, -477148842);
               } else {
                  var11 = var12;
               }
            }
         }

         if (null != var11) {
            this.method10286(var11, var4, var5, 223355795);
            if (var6 != var11.field5667 * 1957680341) {
               boolean var14 = true;

               for (PendingLogin var13 = (PendingLogin)this.field5810.method7914(); var13 != null; var13 = (PendingLogin)Deque.method7921(this.field5810)) {
                  if (var13.field5663.equals(var4)) {
                     if (var6 != 0 && var13.field5664 == 0) {
                        classVM.method12069(var13);
                        var14 = false;
                     } else if (0 == var6 && var13.field5664 != 0) {
                        classVM.method12069(var13);
                        var14 = false;
                     }
                  }
               }

               if (var14) {
                  Deque.method7911(this.field5810, new PendingLogin(var4, var6));
               }
            }
         } else {
            if (this.method10243(-1526041748) >= -2091973663) {
               continue;
            }

            var11 = (Friend)this.method10276(var4, var5, (byte)-28);
         }

         if (var6 != var11.field5667 * 843569712) {
            var11.field5668 = ((this.field5811 += 1868193187) * 1336292947 - 1) * 1023288574;
            if (-1 == var11.field5667 * -1024278346 && 0 == var6) {
               var11.field5668 = -(var11.field5668 * 1);
            }

            var11.field5667 = var6 * 849976631;
         }

         var11.field5669 = -998616451 * var7;
         var11.field5827 = var9;
         var11.field5826 = var10;
      }

      this.method10284((byte)126);
   }

   @ObfuscatedSignature(descriptor = "(Laae;ZI)Z")
   @ObfuscatedName("ae")
   public boolean method10181(classAAE var1, boolean var2, int var3) {
      try {
         Friend var4 = (Friend)this.method10256(var1, 282151994);
         if (null == var4) {
            return false;
         } else {
            if (var2) {
               if (var3 >= -1889939987) {
                  throw new IllegalStateException();
               }

               if (-760305529 * var4.field5667 == 0) {
                  if (var3 >= -1889939987) {
                     throw new IllegalStateException();
                  }

                  return false;
               }
            }

            return true;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "rn.ae(" + ')');
      }
   }
}
