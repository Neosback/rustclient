import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("po")
public class EntityOps implements classOZ, net.runelite.api.EntityOps {
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field5362 = 4;
   @ObfuscatedName("af")
   final List field5361 = new ArrayList();
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   public static final int field5364 = 262144;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final String field5368 = "hidden";
   @ObfuscatedSignature(descriptor = "Lie;")
   @ObfuscatedName("bm")
   static ClanChannel field5366;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   public static final int field5363 = 12;
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   static final int field5365 = 39;
   @ObfuscatedName("as")
   public static int[] field5369;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final String field5367 = "beta";

   @ObfuscatedSignature(descriptor = "(Lxy;IIIIIII)Z")
   @ObfuscatedName("ai")
   boolean method9088(classXY var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      try {
         if (var2 >= var3) {
            if (var8 != 1161561653) {
               throw new IllegalStateException();
            }

            if (var2 <= var4) {
               if (var8 != 1161561653) {
                  throw new IllegalStateException();
               }

               int var19 = var2 - var3;
               String var22 = var1.method13127(113374169);
               if (!var22.equalsIgnoreCase("hidden")) {
                  if (var8 != 1161561653) {
                     throw new IllegalStateException();
                  }

                  this.method9093(var19, var22, (byte)-58);
               }

               return true;
            }
         }

         if (var5 == var2) {
            if (var8 != 1161561653) {
               throw new IllegalStateException();
            } else {
               int var18 = var1.method13095(-346779531);
               int var21 = var1.method13095(-346779531);
               String var24 = var1.method13127(-851437796);
               this.method9094(var18, var21, var24, 1023779009);
               return true;
            }
         } else if (var2 == var6) {
            if (var8 != 1161561653) {
               throw new IllegalStateException();
            } else {
               int var17 = var1.method13095(-346779531);
               int var20 = var1.method13099(1860506613);
               int var23 = var1.method13099(1976193522);
               int var25 = classXY.method13113(var1, (byte)1);
               int var26 = classXY.method13113(var1, (byte)1);
               String var27 = var1.method13127(-441336935);
               this.method9096(var17, var20, var23, var25, var26, var27, (byte)66);
               return true;
            }
         } else if (var7 == var2) {
            if (var8 != 1161561653) {
               throw new IllegalStateException();
            } else {
               int var9 = var1.method13095(-346779531);
               int var10 = var1.method13099(-1756125196);
               int var11 = var1.method13099(67953986);
               int var12 = var1.method13099(1965030463);
               int var13 = classXY.method13113(var1, (byte)1);
               int var14 = classXY.method13113(var1, (byte)1);
               String var15 = var1.method13127(-397015069);
               method9102(this, var9, var10, var11, var12, var13, var14, var15, (byte)11);
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "po.ai(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpo;II[I)Z")
   @ObfuscatedName("tj")
   public static boolean method9110(EntityOps var0, int var1, int var2, int[] var3) {
      if (var0 == null) {
         var0.method9103();
      }

      if (var1 < 0 || var0.field5361.size() <= var1 || var0.field5361.get(var1) == null || var2 < 0) {
         return false;
      } else if (var2 != 0) {
         classPB var5 = (classPB)var0.field5361.get(var1);
         return var5.field5210.method9111(var2, 0, var3, (byte)44);
      } else {
         String var4 = var0.vmethod551(var1, var3, (byte)58);
         return var4 != null && !var4.isEmpty();
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpo;")
   @ObfuscatedName("bs")
   EntityOps method9118() {
      return new EntityOps();
   }

   @ObfuscatedSignature(descriptor = "(IILjava/lang/String;I)V")
   @ObfuscatedName("aw")
   void method9094(int var1, int var2, String var3, int var4) {
      try {
         classPB var5 = (classPB)this.field5361.get(var1);
         if (null == var5.field5210) {
            var5.field5210 = method9120(this, (byte)112);
         }

         var5.field5210.method9093(var2, var3, (byte)-66);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "po.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIILjava/lang/String;B)V")
   @ObfuscatedName("ak")
   void method9096(int var1, int var2, int var3, int var4, int var5, String var6, byte var7) {
      try {
         classPB var8 = (classPB)this.field5361.get(var1);
         if (var8.field5209 == null) {
            var8.field5209 = new ArrayList();
         }

         var8.field5209.add(new classOA(var6, var2, var3, var4, var5));
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "po.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II[IB)Z")
   @ObfuscatedName("an")
   boolean method9111(int var1, int var2, int[] var3, byte var4) {
      try {
         if (var1 >= 0) {
            if (var4 <= 2) {
               throw new IllegalStateException();
            }

            if (this.field5361.size() > var1) {
               if (var4 <= 2) {
                  throw new IllegalStateException();
               }

               if (this.field5361.get(var1) != null) {
                  if (var4 <= 2) {
                     throw new IllegalStateException();
                  }

                  if (var2 >= 0) {
                     if (var2 == 0) {
                        if (var4 <= 2) {
                           throw new IllegalStateException();
                        }

                        String var7 = this.vmethod551(var1, var3, (byte)-5);
                        if (var7 != null) {
                           if (var4 <= 2) {
                              throw new IllegalStateException();
                           }

                           if (!var7.isEmpty()) {
                              return true;
                           }
                        }

                        return false;
                     }

                     classPB var5 = (classPB)this.field5361.get(var1);
                     return var5.field5210.method9111(var2, 0, var3, (byte)20);
                  }

                  if (var4 <= 2) {
                     throw new IllegalStateException();
                  }
               }
            }
         }

         return false;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "po.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpo;")
   @ObfuscatedName("bl")
   EntityOps method9119() {
      return new EntityOps();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   void method9103() {
      this.field5361.clear();
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("af")
   @Override
   public int vmethod539(short var1) {
      try {
         return this.field5361.size() - 1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "po.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpo;I[II)I")
   @ObfuscatedName("kt")
   public static int method9123(EntityOps var0, int var1, int[] var2, int var3) {
      if (var0 == null) {
         return var0.method9127(var1, var2, var1);
      } else {
         try {
            VarbitComposition var5 = (VarbitComposition)NodeCache.method6460(VarbitComposition.field5416, var1);
            VarbitComposition var4;
            if (null != var5) {
               if (var3 <= -2054679977) {
                  throw new IllegalStateException();
               }

               var4 = var5;
            } else {
               byte[] var6 = VarbitComposition.field5421.method11942(14, var1, -1355154202);
               var5 = new VarbitComposition(new classXY(var6));
               VarbitComposition.field5416.method6464(var5, var1);
               var4 = var5;
            }

            return VarbitComposition.method9255(var4, var2[-1681019861 * var4.field5418], (short)-25461);
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "po.bn(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I[IB)Ljava/lang/String;")
   @ObfuscatedName("ab")
   @Override
   public String vmethod551(int var1, int[] var2, byte var3) {
      try {
         if (var1 >= 0) {
            if (var3 == 0) {
               throw new IllegalStateException();
            }

            if (var1 < this.field5361.size()) {
               classPB var4 = (classPB)this.field5361.get(var1);
               if (null == var4) {
                  if (var3 == 0) {
                     throw new IllegalStateException();
                  }

                  return null;
               }

               if (null != var2) {
                  if (var3 == 0) {
                     throw new IllegalStateException();
                  }

                  int var5 = method9115(this, var1, var2, 571937195);
                  if (var5 >= 0) {
                     if (var3 == 0) {
                        throw new IllegalStateException();
                     }

                     classOA var6 = (classOA)var4.field5209.get(var5);
                     return var6.field5208;
                  }
               }

               return var4.field5208;
            }

            if (var3 == 0) {
               throw new IllegalStateException();
            }
         }

         return null;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "po.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Loz;")
   @ObfuscatedName("ag")
   @Override
   public classOZ vmethod542(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 != -166157468) {
               throw new IllegalStateException();
            }

            if (var1 < this.field5361.size()) {
               classPB var3 = (classPB)this.field5361.get(var1);
               if (null == var3) {
                  if (var2 != -166157468) {
                     throw new IllegalStateException();
                  }

                  return null;
               }

               return var3.field5210;
            }

            if (var2 != -166157468) {
               throw new IllegalStateException();
            }
         }

         return null;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "po.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod540(int var1, int var2) {
      try {
         return this.method9111(var1, 0, null, (byte)28);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "po.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Loz;")
   @ObfuscatedName("ad")
   @Override
   public classOZ vmethod553(int var1) {
      if (var1 >= 0 && var1 < this.field5361.size()) {
         classPB var2 = (classPB)this.field5361.get(var1);
         return null == var2 ? null : var2.field5210;
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpo;B)Lpo;")
   @ObfuscatedName("os")
   public static EntityOps method9120(EntityOps var0, byte var1) {
      if (var0 == null) {
         var0.method9122(var1);
      }

      try {
         return new EntityOps();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "po.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ax")
   @Override
   public boolean vmethod544() {
      for (int var1 = 0; var1 < this.field5361.size(); var1++) {
         if (this.field5361.get(var1) != null && ((classPB)this.field5361.get(var1)).field5208 != null) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ac")
   @Override
   public boolean vmethod545() {
      for (int var1 = 0; var1 < this.field5361.size(); var1++) {
         if (this.field5361.get(var1) != null && ((classPB)this.field5361.get(var1)).field5208 != null) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   @Override
   public int vmethod541() {
      return this.field5361.size() - 1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   @Override
   public int vmethod547() {
      return this.field5361.size() - 1;
   }

   public int getNumOps() {
      return this.vmethod539((short)-18872);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpo;I[I)I")
   @ObfuscatedName("gr")
   public static int method9124(EntityOps var0, int var1, int[] var2) {
      if (var0 == null) {
         var0.vmethod544();
      }

      VarbitComposition var4 = (VarbitComposition)NodeCache.method6460(VarbitComposition.field5416, var1);
      VarbitComposition var3;
      if (null != var4) {
         var3 = var4;
      } else {
         byte[] var5 = VarbitComposition.field5421.method11942(14, var1, -1738556661);
         var4 = new VarbitComposition(new classXY(var5));
         VarbitComposition.field5416.method6464(var4, var1);
         var3 = var4;
      }

      return VarbitComposition.method9255(var3, var2[-1681019861 * var3.field5418], (short)1225);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   @Override
   public int vmethod548() {
      return this.field5361.size() - 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("cu")
   public static final String method9128(String var0, int var1) {
      if (var0 == null) {
         var0 = var1 + 1 + ": TEST";
      } else {
         var0 = 1 + var1 + ": " + var0;
      }

      return var0;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ay")
   @Override
   public boolean vmethod550(int var1) {
      return this.method9111(var1, 0, null, (byte)44);
   }

   @ObfuscatedSignature(descriptor = "(I[I)Ljava/lang/String;")
   @ObfuscatedName("aq")
   @Override
   public String vmethod549(int var1, int[] var2) {
      if (var1 >= 0 && var1 < this.field5361.size()) {
         classPB var3 = (classPB)this.field5361.get(var1);
         if (null == var3) {
            return null;
         } else {
            if (null != var2) {
               int var4 = method9115(this, var1, var2, 223091000);
               if (var4 >= 0) {
                  classOA var5 = (classOA)var3.field5209.get(var4);
                  return var5.field5208;
               }
            }

            return var3.field5208;
         }
      } else {
         return null;
      }
   }

   @Nullable
   @ObfuscatedSignature(descriptor = "(I[I)Ljava/lang/String;")
   @ObfuscatedName("bw")
   public String method9134(int var1, int[] var2) {
      return this.vmethod551(var1, var2, (byte)-13);
   }

   @ObfuscatedSignature(descriptor = "(Lpo;I[II)I")
   @ObfuscatedName("yw")
   public static int method9115(EntityOps var0, int var1, int[] var2, int var3) {
      if (var0 == null) {
         return var0.method9116(var1, var2, var1);
      } else {
         try {
            if (var2 != null) {
               if (var3 == 355103608) {
                  throw new IllegalStateException();
               }

               if (var1 < var0.field5361.size()) {
                  classPB var4 = (classPB)var0.field5361.get(var1);
                  if (var4 != null) {
                     if (var3 == 355103608) {
                        throw new IllegalStateException();
                     }

                     if (var4.field5209 != null) {
                        if (var3 == 355103608) {
                           throw new IllegalStateException();
                        }

                        for (int var5 = 0; var5 < var4.field5209.size(); var5++) {
                           if (var3 == 355103608) {
                              throw new IllegalStateException();
                           }

                           classOA var6 = (classOA)var4.field5209.get(var5);
                           int var7;
                           if (var6.field4800 * 1570299213 != 65535) {
                              if (var3 == 355103608) {
                                 throw new IllegalStateException();
                              }

                              var7 = method9123(var0, var6.field4800 * 1570299213, var2, 287615947);
                           } else {
                              var7 = var2[var6.field4803 * 1003932407];
                           }

                           if (var7 >= var6.field4802 * -600528509 && var7 <= var6.field4801 * -74293995) {
                              if (var3 == 355103608) {
                                 throw new IllegalStateException();
                              }

                              return var5;
                           }
                        }
                     }
                  }

                  return -1;
               }

               if (var3 == 355103608) {
                  throw new IllegalStateException();
               }
            }

            return -1;
         } catch (RuntimeException var8) {
            throw classEG.method3899(var8, "po.am(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Loz;")
   @ObfuscatedName("au")
   @Override
   public classOZ vmethod538(int var1) {
      if (var1 >= 0 && var1 < this.field5361.size()) {
         classPB var2 = (classPB)this.field5361.get(var1);
         return null == var2 ? null : var2.field5210;
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bi")
   void method9104() {
      this.field5361.clear();
   }

   @ObfuscatedSignature(descriptor = "(I)Loz;")
   @ObfuscatedName("ap")
   @Override
   public classOZ vmethod554(int var1) {
      if (var1 >= 0 && var1 < this.field5361.size()) {
         classPB var2 = (classPB)this.field5361.get(var1);
         return null == var2 ? null : var2.field5210;
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IIIIII)Z")
   @ObfuscatedName("bc")
   boolean method9089(classXY var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var2 >= var3 && var2 <= var4) {
         int var17 = var2 - var3;
         String var20 = var1.method13127(-634841020);
         if (!var20.equalsIgnoreCase("hidden")) {
            this.method9093(var17, var20, (byte)-20);
         }

         return true;
      } else if (var5 == var2) {
         int var16 = var1.method13095(-346779531);
         int var19 = var1.method13095(-346779531);
         String var22 = var1.method13127(1538095853);
         this.method9094(var16, var19, var22, 1023779009);
         return true;
      } else if (var2 == var6) {
         int var15 = var1.method13095(-346779531);
         int var18 = var1.method13099(1146048691);
         int var21 = var1.method13099(1261360626);
         int var23 = classXY.method13113(var1, (byte)1);
         int var24 = classXY.method13113(var1, (byte)1);
         String var25 = var1.method13127(499224131);
         this.method9096(var15, var18, var21, var23, var24, var25, (byte)8);
         return true;
      } else if (var7 == var2) {
         int var8 = var1.method13095(-346779531);
         int var9 = var1.method13099(-544671130);
         int var10 = var1.method13099(1332224802);
         int var11 = var1.method13099(-1159540884);
         int var12 = classXY.method13113(var1, (byte)1);
         int var13 = classXY.method13113(var1, (byte)1);
         String var14 = var1.method13127(-1056728178);
         method9102(this, var8, var9, var10, var11, var12, var13, var14, (byte)11);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IIIIII)Z")
   @ObfuscatedName("bp")
   boolean method9090(classXY var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var2 >= var3 && var2 <= var4) {
         int var17 = var2 - var3;
         String var20 = var1.method13127(-515689110);
         if (!var20.equalsIgnoreCase("hidden")) {
            this.method9093(var17, var20, (byte)34);
         }

         return true;
      } else if (var5 == var2) {
         int var16 = var1.method13095(-346779531);
         int var19 = var1.method13095(-346779531);
         String var22 = var1.method13127(101642977);
         this.method9094(var16, var19, var22, 1023779009);
         return true;
      } else if (var2 == var6) {
         int var15 = var1.method13095(-346779531);
         int var18 = var1.method13099(-469092688);
         int var21 = var1.method13099(1805746601);
         int var23 = classXY.method13113(var1, (byte)1);
         int var24 = classXY.method13113(var1, (byte)1);
         String var25 = var1.method13127(1483472008);
         this.method9096(var15, var18, var21, var23, var24, var25, (byte)11);
         return true;
      } else if (var7 == var2) {
         int var8 = var1.method13095(-346779531);
         int var9 = var1.method13099(1380351365);
         int var10 = var1.method13099(-1276607113);
         int var11 = var1.method13099(1591351283);
         int var12 = classXY.method13113(var1, (byte)1);
         int var13 = classXY.method13113(var1, (byte)1);
         String var14 = var1.method13127(1287228945);
         method9102(this, var8, var9, var10, var11, var12, var13, var14, (byte)11);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(II[I)Z")
   @ObfuscatedName("bq")
   boolean method9112(int var1, int var2, int[] var3) {
      if (var1 < 0 || this.field5361.size() <= var1 || this.field5361.get(var1) == null || var2 < 0) {
         return false;
      } else if (var2 != 0) {
         classPB var5 = (classPB)this.field5361.get(var1);
         return var5.field5210.method9111(var2, 0, var3, (byte)114);
      } else {
         String var4 = this.vmethod551(var1, var3, (byte)-8);
         return var4 != null && !var4.isEmpty();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpo;)V")
   @ObfuscatedName("uz")
   public static void method9105(EntityOps var0) {
      var0.field5361.clear();
   }

   @ObfuscatedSignature(descriptor = "(IILjava/lang/String;)V")
   @ObfuscatedName("bt")
   void method9095(int var1, int var2, String var3) {
      classPB var4 = (classPB)this.field5361.get(var1);
      if (null == var4.field5210) {
         var4.field5210 = method9120(this, (byte)105);
      }

      var4.field5210.method9093(var2, var3, (byte)-8);
   }

   @ObfuscatedSignature(descriptor = "(IIIIILjava/lang/String;)V")
   @ObfuscatedName("bg")
   void method9097(int var1, int var2, int var3, int var4, int var5, String var6) {
      classPB var7 = (classPB)this.field5361.get(var1);
      if (var7.field5209 == null) {
         var7.field5209 = new ArrayList();
      }

      var7.field5209.add(new classOA(var6, var2, var3, var4, var5));
   }

   @Nullable
   public String getOp(int var1) {
      return this.method9134(var1, classLB.field4088);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;Lrr;Laae;Laae;)V")
   @ObfuscatedName("jh")
   public static void method9117(NameableContainer var0, Nameable var1, classAAE var2, classAAE var3) {
      if (var0 == null) {
         var0.method10289(var1, var2, var2);
      } else {
         var0.method10294(var1, -349734976);
         var1.method10226(var2, var3, -1941374959);
         NameableContainer.method10302(var0, var1, -824594473);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ba")
   void method9106() {
      this.field5361.clear();
   }

   @ObfuscatedSignature(descriptor = "(IIIIIILjava/lang/String;)V")
   @ObfuscatedName("bk")
   void method9099(int var1, int var2, int var3, int var4, int var5, int var6, String var7) {
      classPB var8 = (classPB)this.field5361.get(var1);
      if (null == var8.field5210) {
         var8.field5210 = method9120(this, (byte)15);
      }

      var8.field5210.method9096(var2, var3, var4, var5, var6, var7, (byte)-11);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIILjava/lang/String;)V")
   @ObfuscatedName("bo")
   void method9100(int var1, int var2, int var3, int var4, int var5, int var6, String var7) {
      classPB var8 = (classPB)this.field5361.get(var1);
      if (null == var8.field5210) {
         var8.field5210 = method9120(this, (byte)98);
      }

      var8.field5210.method9096(var2, var3, var4, var5, var6, var7, (byte)26);
   }

   @ObfuscatedSignature(descriptor = "(IIIIILjava/lang/String;)V")
   @ObfuscatedName("bj")
   void method9098(int var1, int var2, int var3, int var4, int var5, String var6) {
      classPB var7 = (classPB)this.field5361.get(var1);
      if (var7.field5209 == null) {
         var7.field5209 = new ArrayList();
      }

      var7.field5209.add(new classOA(var6, var2, var3, var4, var5));
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod546(byte var1) {
      try {
         for (int var2 = 0; var2 < this.field5361.size(); var2++) {
            if (var1 != 0) {
               throw new IllegalStateException();
            }

            if (this.field5361.get(var2) != null) {
               if (var1 != 0) {
                  throw new IllegalStateException();
               }

               if (((classPB)this.field5361.get(var2)).field5208 != null) {
                  if (var1 != 0) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "po.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;)I")
   @ObfuscatedName("ha")
   public static int method9132(Actor var0) {
      return 2030011479 * var0.field1461.field629;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aj")
   @Override
   public boolean vmethod552(int var1) {
      return this.method9111(var1, 0, null, (byte)69);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bb")
   void method9107() {
      this.field5361.clear();
   }

   @ObfuscatedSignature(descriptor = "(II[I)Z")
   @ObfuscatedName("by")
   boolean method9113(int var1, int var2, int[] var3) {
      if (var1 < 0 || this.field5361.size() <= var1 || this.field5361.get(var1) == null || var2 < 0) {
         return false;
      } else if (var2 != 0) {
         classPB var5 = (classPB)this.field5361.get(var1);
         return var5.field5210.method9111(var2, 0, var3, (byte)3);
      } else {
         String var4 = this.vmethod551(var1, var3, (byte)-18);
         return var4 != null && !var4.isEmpty();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("ct")
   public static final String method9129(String var0, int var1) {
      if (var0 == null) {
         var0 = var1 + 1 + ": TEST";
      } else {
         var0 = 1 + var1 + ": " + var0;
      }

      return var0;
   }

   @ObfuscatedSignature(descriptor = "(II[I)Z")
   @ObfuscatedName("bf")
   boolean method9114(int var1, int var2, int[] var3) {
      if (var1 < 0 || this.field5361.size() <= var1 || this.field5361.get(var1) == null || var2 < 0) {
         return false;
      } else if (var2 != 0) {
         classPB var5 = (classPB)this.field5361.get(var1);
         return var5.field5210.method9111(var2, 0, var3, (byte)80);
      } else {
         String var4 = this.vmethod551(var1, var3, (byte)-18);
         return var4 != null && !var4.isEmpty();
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;B)V")
   @ObfuscatedName("ar")
   void method9093(int var1, String var2, byte var3) {
      try {
         if (var1 < 0) {
            if (var3 == 3) {
               throw new IllegalStateException();
            }
         } else {
            for (int var4 = this.field5361.size(); var4 <= var1; var4++) {
               this.field5361.add(null);
            }

            this.field5361.set(var1, new classPB(var2));
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "po.ar(" + ')');
      }
   }

   EntityOps() {
   }

   @ObfuscatedSignature(descriptor = "()Lpo;")
   @ObfuscatedName("bd")
   EntityOps method9121() {
      return new EntityOps();
   }

   @ObfuscatedSignature(descriptor = "(I)Lpo;")
   @ObfuscatedName("wm")
   public EntityOps method9133(int var1) {
      return ((classPB)this.field5361.get(var1)).field5210;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("as")
   @Override
   public boolean vmethod543() {
      for (int var1 = 0; var1 < this.field5361.size(); var1++) {
         if (this.field5361.get(var1) != null && ((classPB)this.field5361.get(var1)).field5208 != null) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(Lpo;I)V")
   @ObfuscatedName("ra")
   public static void method9108(EntityOps var0, int var1) {
      if (var0 == null) {
         var0.method9109(var1);
      }

      try {
         var0.field5361.clear();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "po.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I[I)I")
   @ObfuscatedName("bh")
   int method9125(int var1, int[] var2) {
      VarbitComposition var4 = (VarbitComposition)NodeCache.method6460(VarbitComposition.field5416, var1);
      VarbitComposition var3;
      if (null != var4) {
         var3 = var4;
      } else {
         byte[] var5 = VarbitComposition.field5421.method11942(14, var1, -2140708705);
         var4 = new VarbitComposition(new classXY(var5));
         VarbitComposition.field5416.method6464(var4, var1);
         var3 = var4;
      }

      return VarbitComposition.method9255(var3, var2[464587567 * var3.field5418], (short)-26435);
   }

   @ObfuscatedSignature(descriptor = "(I[I)I")
   @ObfuscatedName("bz")
   int method9126(int var1, int[] var2) {
      VarbitComposition var4 = (VarbitComposition)NodeCache.method6460(VarbitComposition.field5416, var1);
      VarbitComposition var3;
      if (null != var4) {
         var3 = var4;
      } else {
         byte[] var5 = VarbitComposition.field5421.method11942(14, var1, -1766660822);
         var4 = new VarbitComposition(new classXY(var5));
         VarbitComposition.field5416.method6464(var4, var1);
         var3 = var4;
      }

      return VarbitComposition.method9255(var3, var2[773824800 * var3.field5418], (short)15360);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("cr")
   public static final String method9130(String var0, int var1) {
      if (var0 == null) {
         var0 = var1 + 1 + ": TEST";
      } else {
         var0 = 1 + var1 + ": " + var0;
      }

      return var0;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IIIIII)Z")
   @ObfuscatedName("bm")
   boolean method9091(classXY var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var2 >= var3 && var2 <= var4) {
         int var17 = var2 - var3;
         String var20 = var1.method13127(1261840733);
         if (!var20.equalsIgnoreCase("hidden")) {
            this.method9093(var17, var20, (byte)-28);
         }

         return true;
      } else if (var5 == var2) {
         int var16 = var1.method13095(-346779531);
         int var19 = var1.method13095(-346779531);
         String var22 = var1.method13127(622997178);
         this.method9094(var16, var19, var22, 1023779009);
         return true;
      } else if (var2 == var6) {
         int var15 = var1.method13095(-346779531);
         int var18 = var1.method13099(-1572253125);
         int var21 = var1.method13099(846614506);
         int var23 = classXY.method13113(var1, (byte)1);
         int var24 = classXY.method13113(var1, (byte)1);
         String var25 = var1.method13127(-622175458);
         this.method9096(var15, var18, var21, var23, var24, var25, (byte)-28);
         return true;
      } else if (var7 == var2) {
         int var8 = var1.method13095(-346779531);
         int var9 = var1.method13099(-798650124);
         int var10 = var1.method13099(-1952101419);
         int var11 = var1.method13099(-984448061);
         int var12 = classXY.method13113(var1, (byte)1);
         int var13 = classXY.method13113(var1, (byte)1);
         String var14 = var1.method13127(-830494741);
         method9102(this, var8, var9, var10, var11, var12, var13, var14, (byte)11);
         return true;
      } else {
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("cb")
   public static final String method9131(String var0, int var1) {
      if (var0 == null) {
         var0 = var1 + 1 + ": TEST";
      } else {
         var0 = 1 + var1 + ": " + var0;
      }

      return var0;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IIIIII)Z")
   @ObfuscatedName("br")
   boolean method9092(classXY var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var2 >= var3 && var2 <= var4) {
         int var17 = var2 - var3;
         String var20 = var1.method13127(-464845294);
         if (!var20.equalsIgnoreCase("hidden")) {
            this.method9093(var17, var20, (byte)40);
         }

         return true;
      } else if (var5 == var2) {
         int var16 = var1.method13095(-346779531);
         int var19 = var1.method13095(-346779531);
         String var22 = var1.method13127(1718992567);
         this.method9094(var16, var19, var22, 1023779009);
         return true;
      } else if (var2 == var6) {
         int var15 = var1.method13095(-346779531);
         int var18 = var1.method13099(-1087473394);
         int var21 = var1.method13099(-1452890414);
         int var23 = classXY.method13113(var1, (byte)1);
         int var24 = classXY.method13113(var1, (byte)1);
         String var25 = var1.method13127(1120986594);
         this.method9096(var15, var18, var21, var23, var24, var25, (byte)-4);
         return true;
      } else if (var7 == var2) {
         int var8 = var1.method13095(-346779531);
         int var9 = var1.method13099(-429605590);
         int var10 = var1.method13099(1888469593);
         int var11 = var1.method13099(406812709);
         int var12 = classXY.method13113(var1, (byte)1);
         int var13 = classXY.method13113(var1, (byte)1);
         String var14 = var1.method13127(-721500326);
         method9102(this, var8, var9, var10, var11, var12, var13, var14, (byte)11);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIILjava/lang/String;B)V")
   @ObfuscatedName("av")
   void method9101(int var1, int var2, int var3, int var4, int var5, int var6, String var7, byte var8) {
      try {
         classPB var9 = (classPB)this.field5361.get(var1);
         if (null == var9.field5210) {
            if (var8 != 11) {
               throw new IllegalStateException();
            }

            var9.field5210 = method9120(this, (byte)98);
         }

         var9.field5210.method9096(var2, var3, var4, var5, var6, var7, (byte)63);
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "po.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpo;IIIIIILjava/lang/String;B)V")
   @ObfuscatedName("ga")
   public static void method9102(EntityOps var0, int var1, int var2, int var3, int var4, int var5, int var6, String var7, byte var8) {
      if (var0 == null) {
         var0.method9101(var1, var1, var1, var1, var1, var1, var7, var8);
      } else {
         try {
            classPB var9 = (classPB)var0.field5361.get(var1);
            if (null == var9.field5210) {
               if (var8 != 11) {
                  throw new IllegalStateException();
               }

               var9.field5210 = method9120(var0, (byte)98);
            }

            var9.field5210.method9096(var2, var3, var4, var5, var6, var7, (byte)63);
         } catch (RuntimeException var10) {
            throw classEG.method3899(var10, "po.av(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("at")
   void method9109(int var1) {
      try {
         this.field5361.clear();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "po.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I[II)I")
   @ObfuscatedName("am")
   int method9116(int var1, int[] var2, int var3) {
      try {
         if (var2 != null) {
            if (var3 == 355103608) {
               throw new IllegalStateException();
            }

            if (var1 < this.field5361.size()) {
               classPB var4 = (classPB)this.field5361.get(var1);
               if (var4 != null) {
                  if (var3 == 355103608) {
                     throw new IllegalStateException();
                  }

                  if (var4.field5209 != null) {
                     if (var3 == 355103608) {
                        throw new IllegalStateException();
                     }

                     for (int var5 = 0; var5 < var4.field5209.size(); var5++) {
                        if (var3 == 355103608) {
                           throw new IllegalStateException();
                        }

                        classOA var6 = (classOA)var4.field5209.get(var5);
                        int var7;
                        if (var6.field4802 * 1570299213 != 65535) {
                           if (var3 == 355103608) {
                              throw new IllegalStateException();
                           }

                           var7 = method9123(this, var6.field4800 * 1570299213, var2, 287615947);
                        } else {
                           var7 = var2[var6.field4801 * 1003932407];
                        }

                        if (var7 >= var6.field4803 * -600528509 && var7 <= var6.field4801 * -74293995) {
                           if (var3 == 355103608) {
                              throw new IllegalStateException();
                           }

                           return var5;
                        }
                     }
                  }
               }

               return -1;
            }

            if (var3 == 355103608) {
               throw new IllegalStateException();
            }
         }

         return -1;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "po.am(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I[II)I")
   @ObfuscatedName("bn")
   int method9127(int var1, int[] var2, int var3) {
      try {
         VarbitComposition var5 = (VarbitComposition)NodeCache.method6460(VarbitComposition.field5416, var1);
         VarbitComposition var4;
         if (null != var5) {
            if (var3 <= -2054679977) {
               throw new IllegalStateException();
            }

            var4 = var5;
         } else {
            byte[] var6 = VarbitComposition.field5421.method11942(14, var1, -1355154202);
            var5 = new VarbitComposition(new classXY(var6));
            VarbitComposition.field5416.method6464(var5, var1);
            var4 = var5;
         }

         return VarbitComposition.method9255(var4, var2[-1681019861 * var4.field5417], (short)-25461);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "po.bn(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lpo;")
   @ObfuscatedName("ah")
   EntityOps method9122(byte var1) {
      try {
         return new EntityOps();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "po.ah(" + ')');
      }
   }
}
