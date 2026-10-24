import java.awt.Polygon;
import java.awt.Shape;
import javax.annotation.Nullable;
import net.runelite.api.Perspective;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dn")
public final class NPC extends Actor implements net.runelite.api.NPC {
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field1575 = 33;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final float field1572 = 0.01F;
   @ObfuscatedName("ac")
   String field1569;
   @ObfuscatedName("ab")
   static int field1570 = 1703859013;
   @ObfuscatedName("ag")
   static int field1571 = 1679619839;
   @ObfuscatedSignature(descriptor = "Lpl;")
   @ObfuscatedName("as")
   NPCComposition field1579;
   @ObfuscatedName("ax")
   boolean field1567 = false;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field1573 = -5;
   @ObfuscatedName("aa")
   int field1568;
   @ObfuscatedSignature(descriptor = "Lyx;")
   @ObfuscatedName("ao")
   classYX field1583;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   public static final int field1577 = 27;
   @ObfuscatedSignature(descriptor = "Lpp;")
   @ObfuscatedName("aj")
   NpcOverrides field1580;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field1576 = 31;
   @ObfuscatedSignature(descriptor = "Lpp;")
   @ObfuscatedName("al")
   NpcOverrides field1581;
   @ObfuscatedSignature(descriptor = "Llc;")
   @ObfuscatedName("ad")
   PlayerComposition field1578;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("ha")
   static classVP field1582;
   @ObfuscatedName("ay")
   boolean field1584;
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   public static final int field1574 = 54;
   @ObfuscatedSignature(descriptor = "Lfl;")
   @ObfuscatedName("aq")
   final classFL field1566;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("af")
   void method3449(String var1, byte var2) {
      try {
         String var10001;
         if (var1 == null) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            var10001 = "";
         } else {
            var10001 = var1;
         }

         this.field1569 = var10001;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dn.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("cx")
   @Override
   public classFL vmethod270() {
      return this.field1584 ? this.method3504((byte)-87) : null;
   }

   @ObfuscatedSignature(descriptor = "(Ldn;IISB)V")
   @ObfuscatedName("ax")
   public static void method3481(NPC var0, int var1, int var2, short var3, byte var4) {
      if (var0 == null) {
         var0.method3486(var1, var1, var3, var4);
      }

      try {
         if (null == var0.field1583) {
            if (var4 != 4) {
               throw new IllegalStateException();
            }

            var0.field1583 = new classYX(var0.field1579);
         }

         var0.field1583.method13811(var1, var2, var3, -1871315217);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dn.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ax")
   boolean method3457(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 == 1142231971) {
               throw new IllegalStateException();
            }

            if (var1 <= 4) {
               boolean var10000;
               if ((-792802207 * this.field1568 & 1 << var1) != 0) {
                  if (var2 == 1142231971) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
               } else {
                  var10000 = false;
               }

               return var10000;
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dn.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIS)V")
   @ObfuscatedName("cn")
   void method3482(int var1, int var2, short var3) {
      if (null == this.field1583) {
         this.field1583 = new classYX(this.field1579);
      }

      this.field1583.method13811(var1, var2, var3, -1871315217);
   }

   @ObfuscatedSignature(descriptor = "(IIZB)V")
   @ObfuscatedName("ao")
   final void method3471(int var1, int var2, boolean var3, byte var4) {
      try {
         if (this.field1463.method9772(374233424)) {
            if (var4 == 0) {
               throw new IllegalStateException();
            }

            if (this.field1463.method9775((byte)119).field5117 * 221400229 == 1) {
               if (var4 == 0) {
                  throw new IllegalStateException();
               }

               Actor.method3111(this, -806296543);
            }
         }

         if (!var3) {
            if (var4 == 0) {
               return;
            }

            int var5 = var1 - this.field1472[0];
            int var6 = var2 - this.field1431[0];
            if (var5 >= -8) {
               if (var4 == 0) {
                  return;
               }

               if (var5 <= 8) {
                  if (var4 == 0) {
                     throw new IllegalStateException();
                  }

                  if (var6 >= -8) {
                     if (var4 == 0) {
                        throw new IllegalStateException();
                     }

                     if (var6 <= 8) {
                        if (var4 == 0) {
                           throw new IllegalStateException();
                        }

                        this.method3120(var1, var2, classGT.field2678, -638911196);
                        return;
                     }
                  }
               }
            }
         }

         this.method3123(var1, var2, 527441438);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "dn.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("cv")
   short[] method3478() {
      return null != this.field1583 ? this.field1583.method13809(1766940225) : this.field1579.method9021(659715329);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cg")
   void method3490() {
      this.field1583 = null;
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("bn")
   @Override
   final boolean vmethod262(byte var1) {
      try {
         return null != this.field1579;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("cd")
   short[] method3479() {
      return null != this.field1583 ? this.field1583.method13809(-761591449) : this.field1579.method9021(-559014387);
   }

   @ObfuscatedSignature(descriptor = "(I)[S")
   @ObfuscatedName("aj")
   short[] method3480(int var1) {
      try {
         if (null != this.field1583) {
            if (var1 <= 702114061) {
               throw new IllegalStateException();
            } else {
               return this.field1583.method13809(-1378357);
            }
         } else {
            return this.field1579.method9021(-240101723);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpl;")
   @ObfuscatedName("hn")
   public NPCComposition method3513() {
      return this.field1579;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;[I[S)V")
   @ObfuscatedName("nv")
   public static void method3487(NPC var0, int[] var1, short[] var2) {
      if (var0.field1583 == null) {
         var0.field1583 = new classYX(var0.field1579);
      }

      var0.field1583.method13816(var1, var2, -405729472);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("eg")
   @Override
   boolean vmethod377(int var1) {
      try {
         return this.field1579.field5311;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.eg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("as")
   void method3453(int var1, byte var2) {
      try {
         this.field1568 = 937220001 * var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dn.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("am")
   void method3491(int var1) {
      try {
         this.field1583 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lpp;")
   @ObfuscatedName("ah")
   NpcOverrides method3493(byte var1) {
      try {
         return this.field1580;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpp;S)V")
   @ObfuscatedName("bc")
   void method3500(NpcOverrides var1, short var2) {
      try {
         this.field1581 = var1;
         if (var1.field5371) {
            if (var2 <= 357) {
               throw new IllegalStateException();
            }

            if (null == this.field1578) {
               if (var2 <= 357) {
                  throw new IllegalStateException();
               }

               this.field1578 = new PlayerComposition();
            }

            int[] var3 = new int[5];
            if (null != var1.field5373) {
               if (var2 <= 357) {
                  throw new IllegalStateException();
               }

               for (int var4 = 0; var4 < 5; var4++) {
                  if (var2 <= 357) {
                     throw new IllegalStateException();
                  }

                  var3[var4] = var1.field5373[var4];
               }
            }

            this.field1578.method6719(var1.field5377, var1.field5372, null, false, var3, 1836325209 * var1.field5370, -1, 0, -1798890011);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dn.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bo")
   void method3503(int var1) {
      try {
         this.field1581 = null;
         this.field1578 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.bo(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("es")
   @Override
   int vmethod373(int var1) {
      try {
         int var10000;
         if (-1 == 182527809 * this.field1579.field5286) {
            if (var1 != 1468278006) {
               throw new IllegalStateException();
            }

            var10000 = this.field1468 * -1576740359;
         } else {
            var10000 = this.field1579.field5286 * 182527809;
         }

         return var10000 + this.vmethod98(436160785);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.es(" + 41);
      }
   }

   public short[] getOverheadSpriteIds() {
      if (this.field1583 != null) {
         return (short[])this.field1583.field7103.clone();
      } else {
         NPCComposition var1 = this.method3509();
         return var1 != null && var1.field5329 != null ? (short[])var1.field5329.clone() : null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bp")
   @Override
   int vmethod267(int var1) {
      try {
         int var10000;
         if (this.method3180(-921463806)) {
            if (var1 != -1423776655) {
               throw new IllegalStateException();
            }

            var10000 = 0;
         } else {
            var10000 = this.field1579.method9030(1363929196);
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.bp(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpp;B)V")
   @ObfuscatedName("bx")
   void method3496(NpcOverrides var1, byte var2) {
      try {
         this.field1580 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dn.bx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ig")
   @Override
   boolean vmethod379() {
      return this.field1579.field5311;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;B)Ljava/lang/String;")
   @ObfuscatedName("az")
   public static String method3448(CharSequence var0, byte var1) {
      try {
         int var2 = var0.length();
         StringBuilder var3 = new StringBuilder(var2);

         for (int var4 = 0; var4 < var2; var4++) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            char var5;
            label115: {
               var5 = var0.charAt(var4);
               if (var5 >= 'a') {
                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (var5 <= 'z') {
                     break label115;
                  }

                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }
               }

               if (var5 >= 'A') {
                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (var5 <= 'Z') {
                     break label115;
                  }

                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }
               }

               if (var5 >= '0') {
                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (var5 <= '9') {
                     break label115;
                  }

                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }
               }

               if (var5 != '.') {
                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (var5 != '-') {
                     if (var1 <= 0) {
                        throw new IllegalStateException();
                     }

                     if ('*' != var5) {
                        if (var1 <= 0) {
                           throw new IllegalStateException();
                        }

                        if ('_' != var5) {
                           if (var5 == ' ') {
                              if (var1 <= 0) {
                                 throw new IllegalStateException();
                              }

                              var3.append('+');
                           } else {
                              byte var6 = classOG.method8465(var5, (byte)35);
                              var3.append('%');
                              int var7 = var6 >> 4 & 15;
                              if (var7 >= 10) {
                                 if (var1 <= 0) {
                                    throw new IllegalStateException();
                                 }

                                 var3.append((char)(55 + var7));
                              } else {
                                 var3.append((char)(48 + var7));
                              }

                              var7 = var6 & 15;
                              if (var7 >= 10) {
                                 var3.append((char)(55 + var7));
                              } else {
                                 var3.append((char)(48 + var7));
                              }
                           }
                           continue;
                        }

                        if (var1 <= 0) {
                           throw new IllegalStateException();
                        }
                     }
                  }
               }
            }

            var3.append(var5);
         }

         return var3.toString();
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "dn.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("bm")
   @Override
   public float vmethod276(int var1) {
      try {
         if (this.field1584) {
            if (var1 <= 1658477188) {
               throw new IllegalStateException();
            } else {
               return 0.01F;
            }
         } else {
            return 1.0F;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.bm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bt")
   @Override
   public int vmethod278(int var1) {
      try {
         return this.field1584 ? -5 : 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.bt(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lfz;")
   @ObfuscatedName("bg")
   @Override
   protected classFZ vmethod260(byte var1) {
      try {
         if (null != this.field1581) {
            if (var1 <= 4) {
               throw new IllegalStateException();
            }

            if (this.field1581.field5371) {
               return classFZ.field2515;
            }
         }

         classFZ var10000;
         if (!this.field1579.field5332) {
            if (var1 <= 4) {
               throw new IllegalStateException();
            }

            var10000 = classFZ.field2515;
         } else {
            var10000 = super.vmethod260((byte)106);
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.bg(" + ')');
      }
   }

   public String getName() {
      if (this.field1579 == null) {
         return null;
      } else {
         String var1 = this.method3511();
         return var1.replace(' ', ' ');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ad")
   @Override
   protected final Model vmethod92() {
      if (this.field1579 == null) {
         return null;
      } else {
         classQR var1 = this.method3185(-957600076);
         classQR var2 = this.method3187(var1, 387169795);
         if (null == var1 && var2 == null) {
            var2 = this.field1464;
            if (this.field1464.method9805(30, -1418022292)) {
               return null;
            }
         }

         Animation var3 = null == var1 ? null : var1.method9775((byte)126);
         Animation var4 = null == var2 ? null : var2.method9775((byte)75);
         int var5 = null == var1 ? -1 : var1.method9780(1973948872);
         int var6 = var2 == null ? -1 : var2.method9780(1690202925);
         Model var7 = null;
         if (null != this.field1581) {
            if (this.field1581.field5375) {
               Player var8 = classMB.method7564(290261570);
               if (null != var8) {
                  var7 = var8.field1283.method6744(var3, var5, var4, var6, (byte)1);
               }
            } else if (this.field1581.field5371) {
               if (this.field1578 != null) {
                  var7 = this.field1578.method6744(var3, var5, var4, var6, (byte)1);
               }
            } else {
               var7 = NPCComposition.method8985(this.field1579, var3, var5, var4, var6, this.field1581, -766186715);
            }
         } else {
            var7 = NPCComposition.method8985(this.field1579, var3, var5, var4, var6, this.field1581, -1091059971);
         }

         if (var7 == null) {
            return null;
         } else {
            Model.method5254(var7);
            this.field1468 = 2010593719 * var7.field1741;
            int var9 = var7.field2417;
            if (1 == -1325375202 * this.field1579.field5282) {
               var7.field2419 = true;
            }

            if (this.method3190(-1831794059)) {
               var7.method5232(this.field1433, (short)var9);
            } else {
               classKU.method6608(var7);
            }

            var7.method5230(null != this.field1475 ? this.field1475.method272(client.field856 * 1612595797) : 0);
            if (this.field1584) {
               var7.method5232(this.method3504((byte)-45), (short)var7.field2417);
               var7.method5247(0.01F);
               ItemLayer.method3980(var7, -2143705616);
            }

            return var7;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ap")
   @Override
   protected final Model vmethod93() {
      if (this.field1579 == null) {
         return null;
      } else {
         classQR var1 = this.method3185(-1303404224);
         classQR var2 = this.method3187(var1, 812754771);
         if (null == var1 && var2 == null) {
            var2 = this.field1464;
            if (this.field1464.method9805(30, -214155733)) {
               return null;
            }
         }

         Animation var3 = null == var1 ? null : var1.method9775((byte)121);
         Animation var4 = null == var2 ? null : var2.method9775((byte)123);
         int var5 = null == var1 ? -1 : var1.method9780(1873455220);
         int var6 = var2 == null ? -1 : var2.method9780(2102505247);
         Model var7 = null;
         if (null != this.field1581) {
            if (this.field1581.field5375) {
               Player var8 = classMB.method7564(1104105836);
               if (null != var8) {
                  var7 = var8.field1283.method6744(var3, var5, var4, var6, (byte)1);
               }
            } else if (this.field1581.field5371) {
               if (this.field1578 != null) {
                  var7 = this.field1578.method6744(var3, var5, var4, var6, (byte)1);
               }
            } else {
               var7 = NPCComposition.method8985(this.field1579, var3, var5, var4, var6, this.field1581, -1697078269);
            }
         } else {
            var7 = NPCComposition.method8985(this.field1579, var3, var5, var4, var6, this.field1581, -1561970053);
         }

         if (var7 == null) {
            return null;
         } else {
            Model.method5254(var7);
            this.field1468 = 2010593719 * var7.field1741;
            int var9 = var7.field2417;
            if (1 == -1216348145 * this.field1579.field5282) {
               var7.field2419 = true;
            }

            if (this.method3190(-127718153)) {
               var7.method5232(this.field1433, (short)var9);
            } else {
               classKU.method6608(var7);
            }

            var7.method5230(null != this.field1475 ? this.field1475.method272(client.field856 * 1612595797) : 0);
            if (this.field1584) {
               var7.method5232(this.method3504((byte)99), (short)var7.field2417);
               var7.method5247(0.01F);
               ItemLayer.method3980(var7, -5);
            }

            return var7;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ai")
   @Override
   protected boolean vmethod94() {
      if (this.field1579 == null) {
         return false;
      } else if (this.method3159(-966919849)) {
         return true;
      } else if (this.field1475 != null && classAAV.method277(this.field1475, client.field856 * 1612595797)) {
         return true;
      } else {
         Model var1 = null;
         if (null != this.field1581) {
            if (this.field1581.field5375) {
               Player var2 = classMB.method7564(-61149835);
               if (var2 != null) {
                  var1 = var2.field1283.method6744(null, -1, null, -1, (byte)1);
               }
            } else if (this.field1581.field5371) {
               if (null != this.field1578) {
                  var1 = this.field1578.method6744(null, -1, null, -1, (byte)1);
               }
            } else {
               var1 = NPCComposition.method8985(this.field1579, null, -1, null, -1, this.field1581, -730695928);
            }
         } else {
            var1 = NPCComposition.method8985(this.field1579, null, -1, null, -1, this.field1581, -1602996996);
         }

         return null != var1 && null != var1.field2442;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ar")
   @Override
   protected boolean vmethod97() {
      if (this.field1579 == null) {
         return false;
      } else if (this.method3159(-491948979)) {
         return true;
      } else if (this.field1475 != null && classAAV.method277(this.field1475, client.field856 * 1612595797)) {
         return true;
      } else {
         Model var1 = null;
         if (null != this.field1581) {
            if (this.field1581.field5375) {
               Player var2 = classMB.method7564(-751529625);
               if (var2 != null) {
                  var1 = var2.field1283.method6744(null, -1, null, -1, (byte)1);
               }
            } else if (this.field1581.field5371) {
               if (null != this.field1578) {
                  var1 = this.field1578.method6744(null, -1, null, -1, (byte)1);
               }
            } else {
               var1 = NPCComposition.method8985(this.field1579, null, -1, null, -1, this.field1581, -787300165);
            }
         } else {
            var1 = NPCComposition.method8985(this.field1579, null, -1, null, -1, this.field1581, -637419320);
         }

         return null != var1 && null != var1.field2442;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cc")
   @Override
   final boolean vmethod263() {
      return null != this.field1579;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ck")
   @Override
   final boolean vmethod264() {
      return null != this.field1579;
   }

   @ObfuscatedSignature(descriptor = "(B)Lfl;")
   @ObfuscatedName("bu")
   classFL method3504(byte var1) {
      try {
         int var2 = this.field1579.method9034(-52154858);
         classFL var3 = this.field1566;
         byte var4 = classGK.method5502(var2, -1821178453);
         byte var5 = classGK.method5503(var2, -1665176581);
         byte var6 = (byte)(var2 & 127);
         classFL.method4850(var3, var4, var5, var6, (byte)127);
         return this.field1566;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "dn.bu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ca")
   @Override
   final boolean vmethod266() {
      return null != this.field1579;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cw")
   @Override
   int vmethod268() {
      return this.method3180(-921463806) ? 0 : this.field1579.method9030(758746747);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cj")
   @Override
   int vmethod269() {
      return this.method3180(-921463806) ? 0 : this.field1579.method9030(962137520);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;IIS)V")
   @ObfuscatedName("jm")
   public static void method3483(NPC var0, int var1, int var2, short var3) {
      if (null == var0.field1583) {
         var0.field1583 = new classYX(var0.field1579);
      }

      var0.field1583.method13811(var1, var2, var3, -1871315217);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   @Override
   protected boolean vmethod96() {
      if (this.field1579 == null) {
         return false;
      } else if (this.method3159(-510671558)) {
         return true;
      } else if (this.field1475 != null && classAAV.method277(this.field1475, client.field856 * 1612595797)) {
         return true;
      } else {
         Model var1 = null;
         if (null != this.field1581) {
            if (this.field1581.field5375) {
               Player var2 = classMB.method7564(21052915);
               if (var2 != null) {
                  var1 = var2.field1283.method6744(null, -1, null, -1, (byte)1);
               }
            } else if (this.field1581.field5371) {
               if (null != this.field1578) {
                  var1 = this.field1578.method6744(null, -1, null, -1, (byte)1);
               }
            } else {
               var1 = NPCComposition.method8985(this.field1579, null, -1, null, -1, this.field1581, -266918321);
            }
         } else {
            var1 = NPCComposition.method8985(this.field1579, null, -1, null, -1, this.field1581, -783416523);
         }

         return null != var1 && null != var1.field2442;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpp;")
   @ObfuscatedName("lm")
   NpcOverrides method3494() {
      return this.field1580;
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("ce")
   @Override
   public classFL vmethod272() {
      return this.field1584 ? this.method3504((byte)73) : null;
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("cm")
   @Override
   public classFL vmethod273() {
      return this.field1584 ? this.method3504((byte)10) : null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bi")
   void method3454(int var1) {
      this.field1568 = 1402934361 * var1;
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("dd")
   @Override
   public float vmethod277() {
      return this.field1584 ? 0.01F : 1.0F;
   }

   NPC(int var1) {
      super(var1);
      this.field1569 = "";
      this.field1568 = -1010951041;
      this.field1566 = new classFL((byte)0, (byte)0, (byte)0, (byte)0);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dx")
   @Override
   public int vmethod279() {
      return this.field1584 ? -5 : 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hh")
   @Override
   int vmethod374() {
      return (-1 == 33031770 * this.field1579.field5286 ? this.field1468 * 636656845 : this.field1579.field5286 * 182527809) + this.vmethod98(-1271283736);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hv")
   @Override
   int vmethod375() {
      return (-1 == 182527809 * this.field1579.field5286 ? this.field1468 * -1576740359 : this.field1579.field5286 * -303139422) + this.vmethod98(2000238900);
   }

   public Shape getConvexHull() {
      Model var1 = this.method3853();
      if (var1 == null) {
         return null;
      } else {
         WorldView var2 = this.method3281();
         int var3 = Perspective.getFootprintTileHeight(classOE.field4841, this.getLocalLocation(), var2.field1708 * -483624883, this.getFootprintSize());
         var3 -= this.getAnimationHeightOffset();
         return var1.method5280(var2, super.field1484 * -1547553299, super.field1486 * -1272026483, this.getCurrentOrientation(), var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpl;)V")
   @ObfuscatedName("ol")
   public void method3510(NPCComposition var1) {
      if (var1 == null) {
         classOE.field4841.getCallbacks().post(new NpcDespawned(this));
      } else {
         NPCComposition var2 = this.field1579;
         if (var2 == null) {
            client.field1032.add(new NpcSpawned(this));
         } else if (var1.getId() != var2.getId()) {
            client.field1034.add(new NpcChanged(this, var2));
         }
      }
   }

   public int getIndex() {
      return super.field1478 * -1315528093;
   }

   public int getCombatLevel() {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else if (super.field1477 * 705538257 != -1) {
         return super.field1477 * 705538257;
      } else {
         NPCComposition var1 = this.field1579;
         if (var1 != null && var1.getConfigs() != null) {
            var1 = var1.method9049();
         }

         return var1 == null ? -1 : var1.getCombatLevel();
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hc")
   @Override
   int vmethod376() {
      return (-1 == -416076566 * this.field1579.field5286 ? this.field1468 * -1865898707 : this.field1579.field5286 * -420360001) + this.vmethod98(600590848);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ih")
   @Override
   boolean vmethod378() {
      return this.field1579.field5311;
   }

   @ObfuscatedSignature(descriptor = "([I[SI)V")
   @ObfuscatedName("aq")
   void method3488(int[] var1, short[] var2, int var3) {
      try {
         if (this.field1583 == null) {
            if (var3 <= -966335656) {
               throw new IllegalStateException();
            }

            this.field1583 = new classYX(this.field1579);
         }

         this.field1583.method13816(var1, var2, 768968807);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dn.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("it")
   @Override
   boolean vmethod380() {
      return this.field1579.field5311;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lj")
   void method3492() {
      this.field1583 = null;
   }

   public int getId() {
      NPCComposition var1 = this.field1579;
      if (var1 != null && var1.getConfigs() != null) {
         var1 = var1.method9049();
      }

      return var1 == null ? -1 : var1.getId();
   }

   @Override
   public Polygon getCanvasTilePoly() {
      NPCComposition var1 = this.method3509();
      if (var1 == null) {
         return null;
      } else {
         int var2 = var1.getSize();
         return Perspective.getCanvasTileAreaPoly(classOE.field4841, this.getLocalLocation(), var2);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("lu")
   classFL method3505() {
      int var1 = this.field1579.method9034(-52154858);
      classFL var2 = this.field1566;
      byte var3 = classGK.method5502(var1, -1913667842);
      byte var4 = classGK.method5503(var1, -1665176581);
      byte var5 = (byte)(var1 & 127);
      classFL.method4850(var2, var3, var4, var5, (byte)127);
      return this.field1566;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("bb")
   void method3450(String var1) {
      this.field1569 = var1 == null ? "" : var1;
   }

   @ObfuscatedSignature(descriptor = "(Ldn;I)V")
   @ObfuscatedName("qq")
   public static void method3501(NPC var0, int var1) {
      if (var0 == null) {
         var0.method3502(var1);
      } else {
         try {
            var0.field1580 = null;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "dn.bk(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;I)V")
   @ObfuscatedName("ag")
   static void method3508(String var0, StringBuilder var1, classQI var2, int var3) {
      try {
         int var4 = var0.indexOf(61);
         if (-1 == var4) {
            String[] var5 = var0.split(":");
            String var6 = var2.method9624(var5[0], 2145269203);
            if (null != var6) {
               if (var3 >= -44590225) {
                  throw new IllegalStateException();
               }

               if (var5.length > 1) {
                  if (var3 >= -44590225) {
                     throw new IllegalStateException();
                  }

                  String var7 = var5[1].toLowerCase();
                  if (var7.equals("lower")) {
                     if (var3 >= -44590225) {
                        throw new IllegalStateException();
                     }

                     var6 = classPD.method8879(var6, true, 956525589);
                  } else if (var7.equals("upper")) {
                     if (var3 >= -44590225) {
                        throw new IllegalStateException();
                     }

                     var6 = classPD.method8879(var6, false, -184991073);
                  }
               }
            }

            var1.append(var6);
         } else {
            String var9 = var0.substring(0, var4);
            String var10 = var0.substring(1 + var4);
            var10 = classME.method7603(var10, -1767313942);
            var2.method9607(var9, var10, -2073541729);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "dn.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("be")
   void method3455(int var1) {
      this.field1568 = 937220001 * var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("by")
   void method3456(int var1) {
      this.field1568 = -1026244725 * var1;
   }

   @ObfuscatedSignature(descriptor = "(Ldn;ILgt;I)V")
   @ObfuscatedName("wv")
   public static void method3465(NPC var0, int var1, classGT var2, int var3) {
      if (var0 == null) {
         var0.method3470(var1, var2, var1);
      } else {
         try {
            int var4 = var0.field1472[0];
            int var5 = var0.field1431[0];
            if (0 == var1) {
               if (var3 == -17325597) {
                  return;
               }

               var4--;
               var5++;
            }

            if (1 == var1) {
               if (var3 == -17325597) {
                  throw new IllegalStateException();
               }

               var5++;
            }

            if (2 == var1) {
               var4++;
               var5++;
            }

            if (var1 == 3) {
               if (var3 == -17325597) {
                  throw new IllegalStateException();
               }

               var4--;
            }

            if (var1 == 4) {
               if (var3 == -17325597) {
                  throw new IllegalStateException();
               }

               var4++;
            }

            if (5 == var1) {
               if (var3 == -17325597) {
                  throw new IllegalStateException();
               }

               var4--;
               var5--;
            }

            if (6 == var1) {
               if (var3 == -17325597) {
                  return;
               }

               var5--;
            }

            if (7 == var1) {
               var4++;
               var5--;
            }

            if (var0.field1463.method9772(374233424)) {
               if (var3 == -17325597) {
                  return;
               }

               if (var0.field1463.method9775((byte)67).field5117 * 221400229 == 1) {
                  if (var3 == -17325597) {
                     return;
                  }

                  Actor.method3111(var0, 1870015014);
               }
            }

            var0.method3120(var4, var5, var2, -1015799936);
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "dn.aa(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bs")
   boolean method3458(int var1) {
      return var1 >= 0 && var1 <= 4 ? (1594792805 * this.field1568 & 1 << var1) != 0 : true;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bl")
   final String method3462() {
      if (!this.field1569.isEmpty()) {
         return this.field1569;
      } else {
         NPCComposition var1 = this.field1579;
         if (null != var1.field5323) {
            var1 = var1.method8998(652890385);
            if (var1 == null) {
               var1 = this.field1579;
            }
         }

         return var1.method9038((byte)53);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bv")
   final String method3463() {
      if (!this.field1569.isEmpty()) {
         return this.field1569;
      } else {
         NPCComposition var1 = this.field1579;
         if (null != var1.field5323) {
            var1 = var1.method8998(652890385);
            if (var1 == null) {
               var1 = this.field1579;
            }
         }

         return var1.method9038((byte)-18);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldn;I)[I")
   @ObfuscatedName("gm")
   public static int[] method3474(NPC var0, int var1) {
      if (var0 == null) {
         var0.method3477(var1);
      }

      try {
         if (var0.field1583 != null) {
            if (var1 >= -977573417) {
               throw new IllegalStateException();
            } else {
               return classYX.method13806(var0.field1583, 1894471617);
            }
         } else {
            return var0.field1579.method9015((byte)3);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.al(" + ')');
      }
   }

   public int[] getOverheadArchiveIds() {
      if (this.field1583 != null) {
         return (int[])this.field1583.field7104.clone();
      } else {
         NPCComposition var1 = this.method3509();
         return var1 != null && var1.field5328 != null ? (int[])var1.field5328.clone() : null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ac")
   final String method3464(int var1) {
      try {
         if (!this.field1569.isEmpty()) {
            if (var1 >= 404226072) {
               throw new IllegalStateException();
            } else {
               return this.field1569;
            }
         } else {
            NPCComposition var2 = this.field1579;
            if (null != var2.field5323) {
               if (var1 >= 404226072) {
                  throw new IllegalStateException();
               }

               var2 = var2.method8998(652890385);
               if (var2 == null) {
                  if (var1 >= 404226072) {
                     throw new IllegalStateException();
                  }

                  var2 = this.field1579;
               }
            }

            return var2.method9038((byte)-28);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dn.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILgt;)V")
   @ObfuscatedName("cr")
   final void method3466(int var1, classGT var2) {
      int var3 = this.field1472[0];
      int var4 = this.field1431[0];
      if (0 == var1) {
         var3--;
         var4++;
      }

      if (1 == var1) {
         var4++;
      }

      if (2 == var1) {
         var3++;
         var4++;
      }

      if (var1 == 3) {
         var3--;
      }

      if (var1 == 4) {
         var3++;
      }

      if (5 == var1) {
         var3--;
         var4--;
      }

      if (6 == var1) {
         var4--;
      }

      if (7 == var1) {
         var3++;
         var4--;
      }

      if (this.field1463.method9772(374233424) && this.field1463.method9775((byte)77).field5117 * 221400229 == 1) {
         Actor.method3111(this, -26801499);
      }

      this.method3120(var3, var4, var2, -1835517117);
   }

   @ObfuscatedSignature(descriptor = "()Lpl;")
   @ObfuscatedName("ji")
   public NPCComposition method3509() {
      NPCComposition var1 = this.method3513();
      if (var1 != null && var1.getConfigs() != null) {
         var1 = var1.method9049();
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ba")
   void method3451(String var1) {
      this.field1569 = var1 == null ? "" : var1;
   }

   @ObfuscatedSignature(descriptor = "(ILgt;)V")
   @ObfuscatedName("bh")
   final void method3467(int var1, classGT var2) {
      int var3 = this.field1472[0];
      int var4 = this.field1431[0];
      if (0 == var1) {
         var3--;
         var4++;
      }

      if (1 == var1) {
         var4++;
      }

      if (2 == var1) {
         var3++;
         var4++;
      }

      if (var1 == 3) {
         var3--;
      }

      if (var1 == 4) {
         var3++;
      }

      if (5 == var1) {
         var3--;
         var4--;
      }

      if (6 == var1) {
         var4--;
      }

      if (7 == var1) {
         var3++;
         var4--;
      }

      if (this.field1463.method9772(374233424) && this.field1463.method9775((byte)36).field5117 * 221400229 == 1) {
         Actor.method3111(this, -378184761);
      }

      this.method3120(var3, var4, var2, -208441912);
   }

   @ObfuscatedSignature(descriptor = "(IIZ)V")
   @ObfuscatedName("cb")
   final void method3472(int var1, int var2, boolean var3) {
      if (this.field1463.method9772(374233424) && this.field1463.method9775((byte)113).field5117 * 221400229 == 1) {
         Actor.method3111(this, 1509198029);
      }

      if (!var3) {
         int var4 = var1 - this.field1472[0];
         int var5 = var2 - this.field1431[0];
         if (var4 >= 224541102 && var4 <= 8 && var5 >= -8 && var5 <= 8) {
            this.method3120(var1, var2, classGT.field2678, 583273175);
            return;
         }
      }

      this.method3123(var1, var2, 527441438);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("il")
   @Override
   boolean vmethod381() {
      return this.field1579.field5311;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("cq")
   int[] method3475() {
      return this.field1583 != null ? classYX.method13806(this.field1583, -106050322) : this.field1579.method9015((byte)73);
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("cl")
   int[] method3476() {
      return this.field1583 != null ? classYX.method13806(this.field1583, 1825333754) : this.field1579.method9015((byte)64);
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ua")
   public Model method3512() {
      NPC var1 = this;
      int var2 = -891174633;

      try {
         Model var10000;
         if (var1.field1579 == null) {
            if (var2 <= -1767431455) {
               throw new IllegalStateException();
            }

            Object var12 = null;
            var10000 = (Model)var12;
         } else {
            classQR var3 = var1.method3185(-384373404);
            classQR var4 = var1.method3187(var3, -1714817131);
            if (null == var3) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               if (var4 == null) {
                  if (var2 <= -1767431455) {
                     throw new IllegalStateException();
                  }

                  var4 = var1.field1464;
                  if (var1.field1464.method9805(30, -1980368678)) {
                     if (var2 <= -1767431455) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }
               }
            }

            Animation var16;
            if (null == var3) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var16 = null;
            } else {
               var16 = var3.method9775((byte)89);
            }

            Animation var5 = var16;
            Animation var17;
            if (null == var4) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var17 = null;
            } else {
               var17 = var4.method9775((byte)49);
            }

            Animation var6 = var17;
            int var7 = null == var3 ? -1 : var3.method9780(1876575022);
            int var18;
            if (var4 == null) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var18 = -1;
            } else {
               var18 = var4.method9780(2130609443);
            }

            int var8 = var18;
            Model var9 = null;
            if (null != var1.field1581) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               if (var1.field1581.field5375) {
                  if (var2 <= -1767431455) {
                     throw new IllegalStateException();
                  }

                  Player var10 = classMB.method7564(1134144035);
                  if (null != var10) {
                     if (var2 <= -1767431455) {
                        throw new IllegalStateException();
                     }

                     var9 = var10.field1283.method6744(var5, var7, var6, var8, (byte)1);
                  }
               } else if (var1.field1581.field5371) {
                  if (var2 <= -1767431455) {
                     throw new IllegalStateException();
                  }

                  if (var1.field1578 != null) {
                     if (var2 <= -1767431455) {
                        throw new IllegalStateException();
                     }

                     var9 = var1.field1578.method6744(var5, var7, var6, var8, (byte)1);
                  }
               } else {
                  var9 = NPCComposition.method8985(var1.field1579, var5, var7, var6, var8, var1.field1581, -1812942431);
               }
            } else {
               var9 = NPCComposition.method8985(var1.field1579, var5, var7, var6, var8, var1.field1581, -308934681);
            }

            if (var9 == null) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               Object var13 = null;
               var10000 = (Model)var13;
            } else {
               Model.method5254(var9);
               var1.field1468 = 2010593719 * var9.field1741;
               int var15 = var9.field2417;
               if (1 == -1216348145 * var1.field1579.field5282) {
                  if (var2 <= -1767431455) {
                     throw new IllegalStateException();
                  }

                  var9.field2419 = true;
               }

               if (var1.method3190(-1329859236)) {
                  if (var2 <= -1767431455) {
                     throw new IllegalStateException();
                  }

                  var9.method5232(var1.field1433, (short)var15);
               } else {
                  classKU.method6608(var9);
               }

               var9.method5230(null != var1.field1475 ? var1.field1475.method272(client.field856 * 1612595797) : 0);
               if (var1.field1584) {
                  if (var2 <= -1767431455) {
                     throw new IllegalStateException();
                  }

                  var9.method5232(var1.method3504((byte)91), (short)var9.field2417);
                  var9.method5247(0.01F);
                  ItemLayer.method3980(var9, -5);
               }

               var10000 = var9;
            }
         }

         return var10000;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "dn.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("of")
   public String method3511() {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         return this.method3464(-694108261);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cf")
   @Override
   final boolean vmethod265() {
      return null != this.field1579;
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("ae")
   @Override
   protected final Model vmethod104(int var1) {
      if (client.field1115 != null
         && client.field1115
            .test(super.field1463.field5596 * 1684838611 != -1 ? super.field1463.field5596 * 1684838611 : super.field1462.field5596 * 1684838611)) {
         int var2 = this.getAnimationFrame();
         int var3 = this.getPoseAnimationFrame();

         Model var4;
         try {
            super.field1463.field5600 = (-2147483648 | Math.max(super.field1463.field5602 * -1399668821 - 1, 0) << 16 | var2) * -885908119;
            super.field1462.field5600 = (-1073741824 | Math.max(super.field1462.field5602 * -1399668821 - 1, 0) << 16 | var3) * -885908119;
            var4 = this.method3512();
         } finally {
            super.field1463.field5600 = var2 * -885908119;
            super.field1462.field5600 = var3 * -885908119;
         }

         return var4;
      } else {
         return this.method3512();
      }
   }

   @Nullable
   public net.runelite.api.NpcOverrides getModelOverrides() {
      return this.field1581;
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("ci")
   @Override
   public classFL vmethod271() {
      return this.field1584 ? this.method3504((byte)-16) : null;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   @Override
   protected boolean vmethod95(int var1) {
      try {
         if (this.field1579 == null) {
            if (var1 == -702405562) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (this.method3159(-857460300)) {
            if (var1 == -702405562) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            if (this.field1475 != null) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               if (classAAV.method277(this.field1475, client.field856 * 1612595797)) {
                  if (var1 == -702405562) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }

            Model var2 = null;
            if (null != this.field1581) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               if (this.field1581.field5375) {
                  if (var1 == -702405562) {
                     throw new IllegalStateException();
                  }

                  Player var3 = classMB.method7564(-1270183808);
                  if (var3 != null) {
                     if (var1 == -702405562) {
                        throw new IllegalStateException();
                     }

                     var2 = var3.field1283.method6744(null, -1, null, -1, (byte)1);
                  }
               } else if (this.field1581.field5371) {
                  if (var1 == -702405562) {
                     throw new IllegalStateException();
                  }

                  if (null != this.field1578) {
                     if (var1 == -702405562) {
                        throw new IllegalStateException();
                     }

                     var2 = this.field1578.method6744(null, -1, null, -1, (byte)1);
                  }
               } else {
                  var2 = NPCComposition.method8985(this.field1579, null, -1, null, -1, this.field1581, -1207683968);
               }
            } else {
               var2 = NPCComposition.method8985(this.field1579, null, -1, null, -1, this.field1581, -1895949318);
            }

            if (null != var2) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               if (null != var2.field2442) {
                  return true;
               }
            }

            return false;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dn.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIS)V")
   @ObfuscatedName("cs")
   void method3484(int var1, int var2, short var3) {
      if (null == this.field1583) {
         this.field1583 = new classYX(this.field1579);
      }

      this.field1583.method13811(var1, var2, var3, -1871315217);
   }

   @ObfuscatedSignature(descriptor = "()Lfz;")
   @ObfuscatedName("cp")
   @Override
   protected classFZ vmethod261() {
      if (null != this.field1581 && this.field1581.field5371) {
         return classFZ.field2515;
      } else {
         return !this.field1579.field5332 ? classFZ.field2515 : super.vmethod260((byte)108);
      }
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("dc")
   @Override
   public float vmethod275() {
      return this.field1584 ? 0.01F : 1.0F;
   }

   @ObfuscatedSignature(descriptor = "()Lpp;")
   @ObfuscatedName("lp")
   NpcOverrides method3495() {
      return this.field1580;
   }

   @ObfuscatedSignature(descriptor = "([I[S)V")
   @ObfuscatedName("ch")
   void method3489(int[] var1, short[] var2) {
      if (this.field1583 == null) {
         this.field1583 = new classYX(this.field1579);
      }

      this.field1583.method13816(var1, var2, -250151651);
   }

   @ObfuscatedSignature(descriptor = "(Lpp;)V")
   @ObfuscatedName("lf")
   void method3497(NpcOverrides var1) {
      this.field1580 = var1;
   }

   @Nullable
   public net.runelite.api.NpcOverrides getChatheadOverrides() {
      return this.field1580;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;I)Z")
   @ObfuscatedName("cq")
   public static boolean method3459(NPC var0, int var1) {
      if (var0 == null) {
         var0.method3475();
      }

      return var1 >= 0 && var1 <= 4 ? (-792802207 * var0.field1568 & 1 << var1) != 0 : true;
   }

   @ObfuscatedSignature(descriptor = "(ILgt;)V")
   @ObfuscatedName("cu")
   final void method3468(int var1, classGT var2) {
      int var3 = this.field1472[0];
      int var4 = this.field1431[0];
      if (0 == var1) {
         var3--;
         var4++;
      }

      if (1 == var1) {
         var4++;
      }

      if (2 == var1) {
         var3++;
         var4++;
      }

      if (var1 == 3) {
         var3--;
      }

      if (var1 == 4) {
         var3++;
      }

      if (5 == var1) {
         var3--;
         var4--;
      }

      if (6 == var1) {
         var4--;
      }

      if (7 == var1) {
         var3++;
         var4--;
      }

      if (this.field1463.method9772(374233424) && this.field1463.method9775((byte)17).field5117 * 221400229 == 1) {
         Actor.method3111(this, -1262737815);
      }

      this.method3120(var3, var4, var2, -1618033851);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;I)Z")
   @ObfuscatedName("gi")
   public static boolean method3460(NPC var0, int var1) {
      if (var0 == null) {
         return var0.method3461(var1);
      } else {
         return var1 >= 0 && var1 <= 4 ? (-792802207 * var0.field1568 & 1 << var1) != 0 : true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpp;)V")
   @ObfuscatedName("lh")
   void method3498(NpcOverrides var1) {
      this.field1580 = var1;
   }

   @ObfuscatedSignature(descriptor = "(IIS)V")
   @ObfuscatedName("co")
   void method3485(int var1, int var2, short var3) {
      if (null == this.field1583) {
         this.field1583 = new classYX(this.field1579);
      }

      this.field1583.method13811(var1, var2, var3, -1871315217);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dm")
   @Override
   public int vmethod280() {
      return this.field1584 ? 1492525834 : 0;
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("lg")
   classFL method3506() {
      int var1 = this.field1579.method9034(-52154858);
      classFL var2 = this.field1566;
      byte var3 = classGK.method5502(var1, -1864703506);
      byte var4 = classGK.method5503(var1, -1665176581);
      byte var5 = (byte)(var1 & 127);
      classFL.method4850(var2, var3, var4, var5, (byte)926264651);
      return this.field1566;
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("li")
   classFL method3507() {
      int var1 = this.field1579.method9034(-52154858);
      classFL var2 = this.field1566;
      byte var3 = classGK.method5502(var1, -1798125925);
      byte var4 = classGK.method5503(var1, -1665176581);
      byte var5 = (byte)(var1 & 127);
      classFL.method4850(var2, var3, var4, var5, (byte)127);
      return this.field1566;
   }

   @ObfuscatedSignature(descriptor = "(Lpp;)V")
   @ObfuscatedName("ln")
   void method3499(NpcOverrides var1) {
      this.field1580 = var1;
   }

   @ObfuscatedSignature(descriptor = "(ILgt;)V")
   @ObfuscatedName("bz")
   final void method3469(int var1, classGT var2) {
      int var3 = this.field1472[0];
      int var4 = this.field1431[0];
      if (0 == var1) {
         var3--;
         var4++;
      }

      if (1 == var1) {
         var4++;
      }

      if (2 == var1) {
         var3++;
         var4++;
      }

      if (var1 == 3) {
         var3--;
      }

      if (var1 == 4) {
         var3++;
      }

      if (5 == var1) {
         var3--;
         var4--;
      }

      if (6 == var1) {
         var4--;
      }

      if (7 == var1) {
         var3++;
         var4--;
      }

      if (this.field1463.method9772(374233424) && this.field1463.method9775((byte)55).field5117 * 221400229 == 1) {
         Actor.method3111(this, -1866972276);
      }

      this.method3120(var3, var4, var2, 96013266);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;IIZ)V")
   @ObfuscatedName("yc")
   public static void method3473(NPC var0, int var1, int var2, boolean var3) {
      if (var0.field1463.method9772(374233424) && var0.field1463.method9775((byte)123).field5117 * 221400229 == 1) {
         Actor.method3111(var0, -213705491);
      }

      if (!var3) {
         int var4 = var1 - var0.field1472[0];
         int var5 = var2 - var0.field1431[0];
         if (var4 >= -8 && var4 <= 8 && var5 >= -8 && var5 <= 8) {
            var0.method3120(var1, var2, classGT.field2678, 112412752);
            return;
         }
      }

      var0.method3123(var1, var2, 527441438);
   }

   @ObfuscatedSignature(descriptor = "(B)Lfl;")
   @ObfuscatedName("br")
   @Override
   public classFL vmethod274(byte var1) {
      try {
         if (this.field1584) {
            if (var1 >= 0) {
               throw new IllegalStateException();
            } else {
               return this.method3504((byte)47);
            }
         } else {
            return null;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.br(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("al")
   int[] method3477(int var1) {
      try {
         if (this.field1583 != null) {
            if (var1 >= -977573417) {
               throw new IllegalStateException();
            } else {
               return classYX.method13806(this.field1583, 1894471617);
            }
         } else {
            return this.field1579.method9015((byte)3);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILgt;I)V")
   @ObfuscatedName("aa")
   final void method3470(int var1, classGT var2, int var3) {
      try {
         int var4 = this.field1472[0];
         int var5 = super.field1436[0];
         if (0 == var1) {
            if (var3 == -17325597) {
               return;
            }

            var4--;
            var5++;
         }

         if (1 == var1) {
            if (var3 == -17325597) {
               throw new IllegalStateException();
            }

            var5++;
         }

         if (2 == var1) {
            var4++;
            var5++;
         }

         if (var1 == 3) {
            if (var3 == -17325597) {
               throw new IllegalStateException();
            }

            var4--;
         }

         if (var1 == 4) {
            if (var3 == -17325597) {
               throw new IllegalStateException();
            }

            var4++;
         }

         if (5 == var1) {
            if (var3 == -17325597) {
               throw new IllegalStateException();
            }

            var4--;
            var5--;
         }

         if (6 == var1) {
            if (var3 == -17325597) {
               return;
            }

            var5--;
         }

         if (7 == var1) {
            var4++;
            var5--;
         }

         if (super.field1462.method9772(374233424)) {
            if (var3 == -17325597) {
               return;
            }

            if (super.field1499.method9775((byte)67).field5122 * 221400229 == 1) {
               if (var3 == -17325597) {
                  return;
               }

               Actor.method3111(this, 1870015014);
            }
         }

         this.method3120(var4, var5, var2, -1015799936);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "dn.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bk")
   void method3502(int var1) {
      try {
         this.field1580 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dn.bk(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("bw")
   void method3452(String var1) {
      this.field1569 = var1 == null ? "" : var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bf")
   boolean method3461(int var1) {
      return var1 >= 0 && var1 <= 4 ? (-792802207 * this.field1568 & 1 << var1) != 0 : true;
   }

   @ObfuscatedSignature(descriptor = "(IISB)V")
   @ObfuscatedName("ay")
   void method3486(int var1, int var2, short var3, byte var4) {
      try {
         if (null == this.field1583) {
            if (var4 != 4) {
               throw new IllegalStateException();
            }

            this.field1583 = new classYX(this.field1579);
         }

         this.field1583.method13811(var1, var2, var3, -1871315217);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dn.ay(" + ')');
      }
   }
}
