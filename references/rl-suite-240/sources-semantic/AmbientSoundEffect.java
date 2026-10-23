import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dw")
public abstract class AmbientSoundEffect extends Node implements net.runelite.api.AmbientSoundEffect {
   @ObfuscatedSignature(descriptor = "Lzl;")
   @ObfuscatedName("au")
   static final classZL field1644 = classZL.field7192;
   @ObfuscatedName("ax")
   int field1646;
   @ObfuscatedName("ac")
   int field1645;
   @ObfuscatedSignature(descriptor = "Lww;")
   @ObfuscatedName("aj")
   classWW field1640;
   @ObfuscatedSignature(descriptor = "Lwj;")
   @ObfuscatedName("ao")
   classWJ field1651;
   @ObfuscatedSignature(descriptor = "Lwc;")
   @ObfuscatedName("aa")
   classWC field1649;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("ad")
   static final Deque2 field1643 = new Deque2();
   @ObfuscatedName("ay")
   int field1642;
   @ObfuscatedName("aq")
   boolean field1641 = false;
   @ToRemove(unused = "true")
   @ObfuscatedName("bi")
   static final int field1647 = 62;
   @ToRemove(unused = "true")
   @ObfuscatedName("bu")
   static final int field1648 = 77;
   @ObfuscatedSignature(descriptor = "Lwc;")
   @ObfuscatedName("al")
   classWC field1650;
   @ObfuscatedName("as")
   final int field1639;

   @ObfuscatedSignature(descriptor = "(IIIIIIIZI)V")
   @ObfuscatedName("ak")
   void method3653(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, int var9) {
      try {
         int var10 = classEA.method3816((byte)18);
         if (this.field1641) {
            if (var9 != -317896765) {
               throw new IllegalStateException();
            } else {
               if (this.method3649(2028765609)) {
                  if (var9 != -317896765) {
                     throw new IllegalStateException();
                  }

                  this.field1641 = false;
               }
            }
         } else {
            if (-1 == -1727985133 * this.field1651.field6687) {
               if (var9 != -317896765) {
                  throw new IllegalStateException();
               }

               if (null != this.field1640) {
                  if (var9 != -317896765) {
                     throw new IllegalStateException();
                  }

                  if (this.field1640.field6783 == null) {
                     if (var9 != -317896765) {
                        return;
                     }

                     return;
                  }
               }
            }

            if (var10 != 0) {
               if (var9 != -317896765) {
                  throw new IllegalStateException();
               }

               if (var8) {
                  int var11 = classCL.method1617(var1, var2, var3, var4, var5, var6, -2135106240);
                  if (var11 > this.field1646 * -1766162897) {
                     this.method3668(1940029505);
                     return;
                  }

                  double var12;
                  if (this.field1645 * -875852501 < this.field1646 * -1766162897) {
                     if (var9 != -317896765) {
                        return;
                     }

                     var12 = classIC.method6178(
                        this.field1646 * -1766162897 - var11,
                        this.field1646 * -1766162897 - this.field1645 * -875852501,
                        this.field1651.field6685.method12407(900016178),
                        (byte)64
                     );
                  } else {
                     var12 = 1.0;
                  }

                  int var14 = (int)Math.ceil(var12 * var10);
                  if (null != this.field1650) {
                     if (var9 != -317896765) {
                        throw new IllegalStateException();
                     }

                     this.method3666(this.field1650, var14, var10, (byte)117);
                  } else if (this.field1651.field6687 * -1727985133 >= 0) {
                     if (var9 != -317896765) {
                        return;
                     }

                     if (var14 > 0) {
                        if (var9 != -317896765) {
                           throw new IllegalStateException();
                        }

                        this.field1650 = this.method3665(-1727985133 * this.field1651.field6687, var14, -1, (byte)-22);
                     }
                  }

                  if (this.field1649 != null) {
                     if (var9 != -317896765) {
                        return;
                     }

                     if (this.field1649.method12373(120041229) != null) {
                        if (var9 != -317896765) {
                           return;
                        }

                        this.method3666(this.field1649, var14, var10, (byte)9);
                        return;
                     }
                  }

                  if (null != this.field1640) {
                     if (var9 != -317896765) {
                        return;
                     }

                     if (this.field1640.field6783 != null) {
                        if (var9 != -317896765) {
                           throw new IllegalStateException();
                        }

                        if ((this.field1642 -= var7 * 1652054587) * 371683059 <= 0) {
                           if (var9 != -317896765) {
                              throw new IllegalStateException();
                           }

                           if (var14 > 0) {
                              int var15 = this.field1640.field6783[(int)(Math.random() * this.field1640.field6783.length)];
                              this.field1649 = this.method3665(var15, var14, 0, (byte)102);
                              if (null != this.field1649) {
                                 this.field1649
                                    .method12362(
                                       var14,
                                       var10,
                                       classWD.method12396(this.field1651.field6685, -651765444),
                                       this.field1651.field6685.method12394((byte)31),
                                       1100996272
                                    );
                                 this.field1649.method12365((byte)-69);
                              }

                              this.field1642 = classIT.method6357(this.field1640.field6782 * 1861224747, -1823764067 * this.field1640.field6781, -1658390328)
                                 * 1652054587;
                           }
                        }
                     }
                  }

                  return;
               }

               if (var9 != -317896765) {
                  throw new IllegalStateException();
               }
            }

            if (null != this.field1650) {
               this.field1650.method12362(0, var10, 150, field1644, 2053763897);
               this.field1650.method12365((byte)-47);
            }

            if (null != this.field1649) {
               this.field1649.method12362(0, var10, 150, field1644, -137855803);
               this.field1649.method12365((byte)-88);
            }
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "dw.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aw")
   boolean method3649(int var1) {
      try {
         this.method3668(1411566915);
         if (null != this.field1650 && classWC.method12367(this.field1650, 332372396) == 0) {
            this.method3671(this.field1650, 826643967);
            this.field1650 = null;
         }

         if (null != this.field1649) {
            if (var1 <= 836200180) {
               throw new IllegalStateException();
            }

            if (classWC.method12367(this.field1649, -97982290) == 0) {
               if (var1 <= 836200180) {
                  throw new IllegalStateException();
               }

               this.method3671(this.field1649, 826643967);
               this.field1649 = null;
            }
         }

         if (null == this.field1650) {
            if (var1 <= 836200180) {
               throw new IllegalStateException();
            }

            if (this.field1649 == null) {
               if (var1 <= 836200180) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dw.aw(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("bf")
   static int method3658(int var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = classIZ.method6382(var0, var1, var2, var3, var4, var5, -1589544329);
      return Math.max(var6 - 64, 0);
   }

   @ObfuscatedSignature(descriptor = "(Lwj;Lww;B)V")
   @ObfuscatedName("at")
   void method3661(classWJ var1, classWW var2, byte var3) {
      try {
         if (null != var1) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            this.field1651 = var1;
            this.field1646 = classKY.method6649(1535961601 * var1.field6684, 346918112) * -1362786097;
            this.field1645 = classKY.method6649(-2063946921 * var1.field6686, 1426498609) * 788580739;
         }

         if (var2 != null) {
            this.field1640 = var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dw.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)Lwc;")
   @ObfuscatedName("an")
   classWC method3665(int var1, int var2, int var3, byte var4) {
      try {
         int var5 = classTZ.field6271.method11943(var1, -1637846082) - 1;
         classAL var6 = classAL.method434(classTZ.field6271, var1, var5);
         if (null != var6) {
            classAJ var7;
            if (var5 == 0) {
               if (var4 == 56) {
                  throw new IllegalStateException();
               }

               var7 = var6.method438(classAB.field122.method2824(197040242));
            } else {
               if (var5 != 1) {
                  return null;
               }

               var7 = var6.method442();
            }

            classWC var8 = new classWC(var7, var2, var3);
            if (var8.method12373(120041229) == null) {
               if (var4 == 56) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               classQO.field5578.method618(var8.method12373(120041229));
               return var8;
            }
         } else {
            return null;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "dw.an(" + ')');
      }
   }

   public int getSoundEffectId() {
      return this.field1651.field6687 * -1727985133;
   }

   @ObfuscatedSignature(descriptor = "(Lwc;IIB)V")
   @ObfuscatedName("am")
   void method3666(classWC var1, int var2, int var3, byte var4) {
      try {
         var1.method12365((byte)36);
         var1.method12358(var2, var3, this.field1651.field6685, 1877069434);
         var1.method12365((byte)11);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dw.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ah")
   void method3668(int var1) {
      try {
         if (this.field1651 == null) {
            if (var1 > 721877044) {
               ;
            }
         } else {
            classWD var2 = this.field1651.field6685;
            if (null != this.field1650) {
               if (var1 <= 721877044) {
                  throw new IllegalStateException();
               }

               this.field1650
                  .method12362(0, classWC.method12367(this.field1650, -2079214722), var2.method12404((byte)-26), var2.method12402(-1868524944), -667286928);
               this.field1650.method12365((byte)-38);
            }

            if (null != this.field1649) {
               if (var1 <= 721877044) {
                  throw new IllegalStateException();
               }

               this.field1649
                  .method12362(0, classWC.method12367(this.field1649, -36021692), var2.method12404((byte)57), var2.method12402(358370578), -372632873);
               this.field1649.method12365((byte)23);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dw.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwc;I)V")
   @ObfuscatedName("bn")
   void method3671(classWC var1, int var2) {
      try {
         if (null != var1) {
            if (var2 != 826643967) {
               throw new IllegalStateException();
            }

            if (var1.method12373(120041229) != null) {
               classQO.field5578.method619(var1.method12373(120041229));
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dw.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bx")
   void method3675(int var1) {
      try {
         field1643.method7946(this);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dw.bx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cp")
   void method3669() {
      if (this.field1651 != null) {
         classWD var1 = this.field1651.field6685;
         if (null != this.field1650) {
            this.field1650
               .method12362(0, classWC.method12367(this.field1650, -1321201890), var1.method12404((byte)-50), var1.method12402(1305296700), 366127353);
            this.field1650.method12365((byte)-83);
         }

         if (null != this.field1649) {
            this.field1649
               .method12362(0, classWC.method12367(this.field1649, -1924792783), var1.method12404((byte)-60), var1.method12402(637590000), -802230290);
            this.field1649.method12365((byte)-65);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   abstract int vmethod194(int var1);

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   abstract int vmethod197(int var1);

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   abstract void vmethod193();

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bm")
   int method3678(int var1) {
      try {
         return this.field1651 != null ? this.field1651.field6687 * -1727985133 : -1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dw.bc(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bp")
   int method3683(int var1) {
      try {
         return this.field1646 * -1766162897;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dw.br(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   abstract int vmethod205(int var1);

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gu")
   @Override
   public void vmethod398() {
      try {
         super.vmethod398();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "dw.gr(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   abstract int vmethod202(int var1);

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ba")
   boolean method3650() {
      this.method3668(1062476384);
      if (null != this.field1650 && classWC.method12367(this.field1650, -873369617) == 0) {
         this.method3671(this.field1650, 826643967);
         this.field1650 = null;
      }

      if (null != this.field1649 && classWC.method12367(this.field1649, -44216448) == 0) {
         this.method3671(this.field1649, 826643967);
         this.field1649 = null;
      }

      return null == this.field1650 && this.field1649 == null;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("br")
   int method3687(int var1) {
      try {
         return this.field1645 * -875852501;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dw.bm(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("xk")
   public void method3694() {
      this.vmethod189(-380921692);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method3690() {
      return super.toString();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldw;)Ljava/lang/String;")
   @ObfuscatedName("vs")
   public static String method3691(AmbientSoundEffect var0) {
      if (var0 == null) {
         var0.method3684();
      }

      return var0.toString();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   abstract void vmethod190();

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   abstract void vmethod191();

   @Override
   public String toString() {
      try {
         return super.toString();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "dw.toString(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldw;Lwc;)V")
   @ObfuscatedName("ow")
   public static void method3672(AmbientSoundEffect var0, classWC var1) {
      if (var0 == null) {
         var0.method3690();
      }

      if (null != var1 && var1.method12373(120041229) != null) {
         classQO.field5578.method619(var1.method12373(120041229));
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   abstract void vmethod192();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   abstract int vmethod196();

   @ObfuscatedSignature(descriptor = "(Lbx;Lbt;)[F")
   @ObfuscatedName("js")
   public static float[] method3674(classBX var0, classBT var1) {
      return var0 == null ? var0.method1354(var1) : var0.field603[var0.method1353(var1)];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cd")
   int method3679() {
      return 1995575523 * this.field1639;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   abstract int vmethod195();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   abstract int vmethod199();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   abstract int vmethod200();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   abstract int vmethod203();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   abstract int vmethod206();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ai")
   abstract int vmethod204();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bt")
   static void method3645() {
      for (AmbientSoundEffect var0 = (AmbientSoundEffect)field1643.method7957(); var0 != null; var0 = (AmbientSoundEffect)field1643.method7965()) {
         if (var0.method3649(1040567269)) {
            var0.vmethod398();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   static void method3646() {
      for (AmbientSoundEffect var0 = (AmbientSoundEffect)field1643.method7957(); var0 != null; var0 = (AmbientSoundEffect)field1643.method7965()) {
         if (var0.method3649(1389470177)) {
            var0.vmethod398();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bj")
   static void method3647() {
      for (AmbientSoundEffect var0 = (AmbientSoundEffect)field1643.method7957(); var0 != null; var0 = (AmbientSoundEffect)field1643.method7965()) {
         if (var0.method3649(931809131)) {
            var0.vmethod398();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   static void method3648() {
      for (AmbientSoundEffect var0 = (AmbientSoundEffect)field1643.method7957(); var0 != null; var0 = (AmbientSoundEffect)field1643.method7965()) {
         if (var0.method3649(1046166903)) {
            var0.vmethod398();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldw;)V")
   @ObfuscatedName("nc")
   public static void method3670(AmbientSoundEffect var0) {
      if (var0.field1651 != null) {
         classWD var1 = var0.field1651.field6685;
         if (null != var0.field1650) {
            var0.field1650
               .method12362(0, classWC.method12367(var0.field1650, -1620503145), var1.method12404((byte)-45), var1.method12402(388328350), -676047776);
            var0.field1650.method12365((byte)-35);
         }

         if (null != var0.field1649) {
            var0.field1649.method12362(0, classWC.method12367(var0.field1649, 1251441133), var1.method12404((byte)8), var1.method12402(760323860), -1426355550);
            var0.field1649.method12365((byte)31);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bo")
   boolean method3651() {
      this.method3668(1086047683);
      if (null != this.field1650 && classWC.method12367(this.field1650, -612117624) == 0) {
         this.method3671(this.field1650, 826643967);
         this.field1650 = null;
      }

      if (null != this.field1649 && classWC.method12367(this.field1649, -1424071566) == 0) {
         this.method3671(this.field1649, 826643967);
         this.field1649 = null;
      }

      return null == this.field1650 && this.field1649 == null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bu")
   boolean method3652() {
      this.method3668(1572990823);
      if (null != this.field1650 && classWC.method12367(this.field1650, 1017513061) == 0) {
         this.method3671(this.field1650, 826643967);
         this.field1650 = null;
      }

      if (null != this.field1649 && classWC.method12367(this.field1649, -1439076916) == 0) {
         this.method3671(this.field1649, 826643967);
         this.field1649 = null;
      }

      return null == this.field1650 && this.field1649 == null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("by")
   static int method3659(int var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = classIZ.method6382(var0, var1, var2, var3, var4, var5, -1277969903);
      return Math.max(var6 - 232842550, 0);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldw;)Ljava/lang/String;")
   @ObfuscatedName("jc")
   public static String method3692(AmbientSoundEffect var0) {
      if (var0 == null) {
         var0.vmethod399();
      }

      return var0.toString();
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIZ)V")
   @ObfuscatedName("bw")
   void method3654(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      int var9 = classEA.method3816((byte)-7);
      if (this.field1641) {
         if (this.method3649(978640030)) {
            this.field1641 = false;
         }
      } else if (-1 != -1727985133 * this.field1651.field6687 || null == this.field1640 || this.field1640.field6783 != null) {
         if (var9 != 0 && var8) {
            int var10 = classCL.method1617(var1, var2, var3, var4, var5, var6, -1877872796);
            if (var10 > this.field1646 * -1766162897) {
               this.method3668(2038043865);
            } else {
               double var11;
               if (this.field1645 * -875852501 < this.field1646 * -1766162897) {
                  var11 = classIC.method6178(
                     this.field1646 * -1766162897 - var10,
                     this.field1646 * -1766162897 - this.field1645 * -875852501,
                     this.field1651.field6685.method12407(984296783),
                     (byte)64
                  );
               } else {
                  var11 = 1.0;
               }

               int var13 = (int)Math.ceil(var11 * var9);
               if (null != this.field1650) {
                  this.method3666(this.field1650, var13, var9, (byte)12);
               } else if (this.field1651.field6687 * -1727985133 >= 0 && var13 > 0) {
                  this.field1650 = this.method3665(-1727985133 * this.field1651.field6687, var13, -1, (byte)61);
               }

               if (this.field1649 != null && this.field1649.method12373(120041229) != null) {
                  this.method3666(this.field1649, var13, var9, (byte)23);
               } else if (null != this.field1640 && this.field1640.field6783 != null && (this.field1642 -= var7 * 1652054587) * 371683059 <= 0 && var13 > 0) {
                  int var14 = this.field1640.field6783[(int)(Math.random() * this.field1640.field6783.length)];
                  this.field1649 = this.method3665(var14, var13, 0, (byte)-12);
                  if (null != this.field1649) {
                     this.field1649
                        .method12362(
                           var13, var9, classWD.method12396(this.field1651.field6685, -100460695), this.field1651.field6685.method12394((byte)8), -587619717
                        );
                     this.field1649.method12365((byte)-107);
                  }

                  this.field1642 = classIT.method6357(this.field1640.field6782 * 1861224747, -1823764067 * this.field1640.field6781, 1840907776) * 1652054587;
               }
            }
         } else {
            if (null != this.field1650) {
               this.field1650.method12362(0, var9, 150, field1644, -356348602);
               this.field1650.method12365((byte)-94);
            }

            if (null != this.field1649) {
               this.field1649.method12362(0, var9, 150, field1644, 876507658);
               this.field1649.method12365((byte)24);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   abstract int vmethod198();

   @ObfuscatedSignature(descriptor = "(IIIIIIIZ)V")
   @ObfuscatedName("bi")
   void method3655(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      int var9 = classEA.method3816((byte)59);
      if (this.field1641) {
         if (this.method3649(1293879766)) {
            this.field1641 = false;
         }
      } else if (-1 != -1727985133 * this.field1651.field6687 || null == this.field1640 || this.field1640.field6783 != null) {
         if (var9 != 0 && var8) {
            int var10 = classCL.method1617(var1, var2, var3, var4, var5, var6, -1699666983);
            if (var10 > this.field1646 * -1766162897) {
               this.method3668(1903028359);
            } else {
               double var11;
               if (this.field1645 * -875852501 < this.field1646 * -1766162897) {
                  var11 = classIC.method6178(
                     this.field1646 * -1766162897 - var10,
                     this.field1646 * -1766162897 - this.field1645 * -875852501,
                     this.field1651.field6685.method12407(2069009348),
                     (byte)64
                  );
               } else {
                  var11 = 1.0;
               }

               int var13 = (int)Math.ceil(var11 * var9);
               if (null != this.field1650) {
                  this.method3666(this.field1650, var13, var9, (byte)50);
               } else if (this.field1651.field6687 * -1727985133 >= 0 && var13 > 0) {
                  this.field1650 = this.method3665(-1727985133 * this.field1651.field6687, var13, -1, (byte)69);
               }

               if (this.field1649 != null && this.field1649.method12373(120041229) != null) {
                  this.method3666(this.field1649, var13, var9, (byte)79);
               } else if (null != this.field1640 && this.field1640.field6783 != null && (this.field1642 -= var7 * 1652054587) * 371683059 <= 0 && var13 > 0) {
                  int var14 = this.field1640.field6783[(int)(Math.random() * this.field1640.field6783.length)];
                  this.field1649 = this.method3665(var14, var13, 0, (byte)-18);
                  if (null != this.field1649) {
                     this.field1649
                        .method12362(
                           var13, var9, classWD.method12396(this.field1651.field6685, 528015592), this.field1651.field6685.method12394((byte)14), -224308325
                        );
                     this.field1649.method12365((byte)-77);
                  }

                  this.field1642 = classIT.method6357(this.field1640.field6782 * 1861224747, -1823764067 * this.field1640.field6781, -913992954) * 1652054587;
               }
            }
         } else {
            if (null != this.field1650) {
               this.field1650.method12362(0, var9, 150, field1644, 321215234);
               this.field1650.method12365((byte)-20);
            }

            if (null != this.field1649) {
               this.field1649.method12362(0, var9, 150, field1644, 1681895998);
               this.field1649.method12365((byte)12);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   abstract void vmethod189(int var1);

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aj")
   abstract int vmethod201();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("bq")
   static int method3660(int var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = classIZ.method6382(var0, var1, var2, var3, var4, var5, -1373185982);
      return Math.max(var6 - 64, 0);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIZ)V")
   @ObfuscatedName("be")
   void method3656(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      int var9 = classEA.method3816((byte)97);
      if (this.field1641) {
         if (this.method3649(2112160538)) {
            this.field1641 = false;
         }
      } else if (-1 != 1161259135 * this.field1651.field6687 || null == this.field1640 || this.field1640.field6783 != null) {
         if (var9 != 0 && var8) {
            int var10 = classCL.method1617(var1, var2, var3, var4, var5, var6, 1047750096);
            if (var10 > this.field1646 * -1766162897) {
               this.method3668(1676105596);
            } else {
               double var11;
               if (this.field1645 * 1973474334 < this.field1646 * 2074955993) {
                  var11 = classIC.method6178(
                     this.field1646 * -1616992666 - var10,
                     this.field1646 * -918204251 - this.field1645 * -875852501,
                     this.field1651.field6685.method12407(1809335950),
                     (byte)64
                  );
               } else {
                  var11 = 1.0;
               }

               int var13 = (int)Math.ceil(var11 * var9);
               if (null != this.field1650) {
                  this.method3666(this.field1650, var13, var9, (byte)32);
               } else if (this.field1651.field6687 * -1727985133 >= 0 && var13 > 0) {
                  this.field1650 = this.method3665(-1727985133 * this.field1651.field6687, var13, -1, (byte)11);
               }

               if (this.field1649 != null && this.field1649.method12373(120041229) != null) {
                  this.method3666(this.field1649, var13, var9, (byte)4);
               } else if (null != this.field1640 && this.field1640.field6783 != null && (this.field1642 -= var7 * 1652054587) * 371683059 <= 0 && var13 > 0) {
                  int var14 = this.field1640.field6783[(int)(Math.random() * this.field1640.field6783.length)];
                  this.field1649 = this.method3665(var14, var13, 0, (byte)5);
                  if (null != this.field1649) {
                     this.field1649
                        .method12362(
                           var13, var9, classWD.method12396(this.field1651.field6685, 1655368514), this.field1651.field6685.method12394((byte)19), -1330803375
                        );
                     this.field1649.method12365((byte)-2);
                  }

                  this.field1642 = classIT.method6357(this.field1640.field6782 * 1861224747, -1823764067 * this.field1640.field6781, -1694781495) * 1493326758;
               }
            }
         } else {
            if (null != this.field1650) {
               this.field1650.method12362(0, var9, 150, field1644, 147272855);
               this.field1650.method12365((byte)-56);
            }

            if (null != this.field1649) {
               this.field1649.method12362(0, var9, 150, field1644, 119456063);
               this.field1649.method12365((byte)8);
            }
         }
      }
   }

   public int[] getBackgroundSoundEffectIds() {
      return this.field1640.field6783;
   }

   @ObfuscatedSignature(descriptor = "(Lwj;Lww;)V")
   @ObfuscatedName("bs")
   void method3662(classWJ var1, classWW var2) {
      if (null != var1) {
         this.field1651 = var1;
         this.field1646 = classKY.method6649(1535961601 * var1.field6684, -182725428) * -1362786097;
         this.field1645 = classKY.method6649(-2063946921 * var1.field6686, 1850738940) * 788580739;
      }

      if (var2 != null) {
         this.field1640 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwc;)V")
   @ObfuscatedName("ct")
   void method3673(classWC var1) {
      if (null != var1 && var1.method12373(120041229) != null) {
         classQO.field5578.method619(var1.method12373(120041229));
      }
   }

   AmbientSoundEffect(int var1, classWJ var2, classWW var3) {
      this.field1639 = -2052082485 * var1;
      this.method3661(var2, var3, (byte)-49);
      if (this.field1640 != null && null != this.field1640.field6783) {
         this.field1642 = classIT.method6357(var3.field6782 * 1861224747, var3.field6781 * -1823764067, 749176852) * 1652054587;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwc;II)V")
   @ObfuscatedName("bl")
   void method3667(classWC var1, int var2, int var3) {
      var1.method12365((byte)5);
      var1.method12358(var2, var3, this.field1651.field6685, 1921728525);
      var1.method12365((byte)16);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cs")
   int method3684() {
      return this.field1646 * -1766162897;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cq")
   void method3676() {
      field1643.method7946(this);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bz")
   void method3677() {
      field1643.method7946(this);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldw;Lwj;Lww;)V")
   @ObfuscatedName("xc")
   public static void method3663(AmbientSoundEffect var0, classWJ var1, classWW var2) {
      if (var0 == null) {
         var0.method3664(var1, var2);
      } else {
         if (null != var1) {
            var0.field1651 = var1;
            var0.field1646 = classKY.method6649(1535961601 * var1.field6684, 217170018) * -1362786097;
            var0.field1645 = classKY.method6649(-2063946921 * var1.field6686, -200153630) * 788580739;
         }

         if (var2 != null) {
            var0.field1640 = var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cl")
   int method3680() {
      return 1995575523 * this.field1639;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIZ)V")
   @ObfuscatedName("bb")
   void method3657(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      int var9 = classEA.method3816((byte)45);
      if (this.field1641) {
         if (this.method3649(905272872)) {
            this.field1641 = false;
         }
      } else if (-1 != -1727985133 * this.field1651.field6687 || null == this.field1640 || this.field1640.field6783 != null) {
         if (var9 != 0 && var8) {
            int var10 = classCL.method1617(var1, var2, var3, var4, var5, var6, 201966252);
            if (var10 > this.field1646 * -1766162897) {
               this.method3668(1808612347);
            } else {
               double var11;
               if (this.field1645 * -875852501 < this.field1646 * -1331620499) {
                  var11 = classIC.method6178(
                     this.field1646 * -1766162897 - var10,
                     this.field1646 * 49196164 - this.field1645 * -875852501,
                     this.field1651.field6685.method12407(730244766),
                     (byte)64
                  );
               } else {
                  var11 = 1.0;
               }

               int var13 = (int)Math.ceil(var11 * var9);
               if (null != this.field1650) {
                  this.method3666(this.field1650, var13, var9, (byte)90);
               } else if (this.field1651.field6687 * -1615483551 >= 0 && var13 > 0) {
                  this.field1650 = this.method3665(-1989997505 * this.field1651.field6687, var13, -1, (byte)107);
               }

               if (this.field1649 != null && this.field1649.method12373(120041229) != null) {
                  this.method3666(this.field1649, var13, var9, (byte)82);
               } else if (null != this.field1640 && this.field1640.field6783 != null && (this.field1642 -= var7 * 1652054587) * -861698674 <= 0 && var13 > 0) {
                  int var14 = this.field1640.field6783[(int)(Math.random() * this.field1640.field6783.length)];
                  this.field1649 = this.method3665(var14, var13, 0, (byte)-71);
                  if (null != this.field1649) {
                     this.field1649
                        .method12362(
                           var13, var9, classWD.method12396(this.field1651.field6685, -1398242749), this.field1651.field6685.method12394((byte)62), -1389908939
                        );
                     this.field1649.method12365((byte)-63);
                  }

                  this.field1642 = classIT.method6357(this.field1640.field6782 * 1380628973, -1823764067 * this.field1640.field6781, 1414496214) * -1511514849;
               }
            }
         } else {
            if (null != this.field1650) {
               this.field1650.method12362(0, var9, 1239063122, field1644, 676113529);
               this.field1650.method12365((byte)-89);
            }

            if (null != this.field1649) {
               this.field1649.method12362(0, var9, -1611420403, field1644, -195666947);
               this.field1649.method12365((byte)26);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cv")
   int method3681() {
      return -654753858 * this.field1639;
   }

   public int getPlane() {
      return this.field1639 * 1995575523;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cy")
   int method3685() {
      return this.field1646 * -1766162897;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("co")
   int method3686() {
      return this.field1646 * 55320627;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ck")
   int method3688() {
      return this.field1645 * 267687475;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gh")
   @Override
   public void vmethod399() {
      super.vmethod398();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gr")
   @Override
   public void vmethod400() {
      super.vmethod398();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gf")
   @Override
   public void vmethod401() {
      super.vmethod398();
   }

   @ObfuscatedSignature(descriptor = "(Lte;Lte;IIII)V")
   @ObfuscatedName("aj")
   public static void method3693(classTE var0, classTE var1, int var2, int var3, int var4, int var5) {
      try {
         classCQ.method2564(var0, 301090434);
         classHO.method6112(var1, var0.field6126, (byte)-22);
         int var6 = var0.method10797(-2137488650);
         int var7 = var1.method10797(-214843922);
         if (-1 == var4) {
            if (var5 <= 664753940) {
               throw new IllegalStateException();
            }

            var4 = var0.method10797(-1337154478) - var2;
         }

         if (0 != var4) {
            if (var5 > 664753940) {
               if (var1 == var0) {
                  if (var5 <= 664753940) {
                     return;
                  }

                  if (var2 == var3) {
                     if (var5 <= 664753940) {
                        return;
                     }

                     return;
                  }
               }

               if (var2 >= 0 && var4 + var2 <= var6) {
                  if (var5 <= 664753940) {
                     throw new IllegalStateException();
                  }

                  if (var3 >= 0) {
                     if (var5 <= 664753940) {
                        throw new IllegalStateException();
                     }

                     if (var4 + var3 <= var7) {
                        classMX.method7786(var0, var2, var1, var3, var4, -2142246254);
                        var1.field6131 = true;
                        return;
                     }

                     if (var5 <= 664753940) {
                        return;
                     }
                  }
               }

               throw new RuntimeException();
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "dw.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwj;Lww;)V")
   @ObfuscatedName("bd")
   void method3664(classWJ var1, classWW var2) {
      if (null != var1) {
         this.field1651 = var1;
         this.field1646 = classKY.method6640(1535961601 * var1.field6687, 217170018) * -1362786097;
         this.field1646 = classKY.method6649(-2063946921 * var1.field6684, -200153630) * 788580739;
      }

      if (var2 != null) {
         this.field1640 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldw;I)I")
   @ObfuscatedName("ed")
   public static int method3682(AmbientSoundEffect var0, int var1) {
      if (var0 == null) {
         var0.method3689(var1);
      }

      try {
         return 1995575523 * var0.field1639;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dw.bp(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bc")
   int method3689(int var1) {
      try {
         return this.field1642 * -875852501;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dw.bm(" + 41);
      }
   }
}
