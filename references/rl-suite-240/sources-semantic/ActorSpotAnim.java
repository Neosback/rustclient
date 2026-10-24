import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("si")
public class ActorSpotAnim extends Renderable implements net.runelite.api.ActorSpotAnim {
   @ObfuscatedName("as")
   final boolean field5960;
   @ObfuscatedSignature(descriptor = "Lqr;")
   @ObfuscatedName("af")
   public final classQR field5956;
   @ObfuscatedName("ae")
   public int field5959;
   @ObfuscatedName("az")
   public int field5957 = 861680777;
   @ObfuscatedSignature(descriptor = "Ldh;")
   @ObfuscatedName("ag")
   final Actor field5955;
   @ObfuscatedName("re")
   static int field5962;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final int field5961 = 765;
   @ObfuscatedName("ab")
   int field5958;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsi;)Z")
   @ObfuscatedName("to")
   public static boolean method10500(ActorSpotAnim var0) {
      if (var0 == null) {
         var0.method10503();
      }

      return var0.field5960;
   }

   public void setCycle(int var1) {
      this.field5956.field5602 = var1 * -1873132797;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   @Override
   protected int vmethod100() {
      return this.field5955.vmethod98(-312983606);
   }

   public int getFrame() {
      return this.field5956.field5600 * 292569817;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   @Override
   protected boolean vmethod96() {
      if (null == this.field5955) {
         return false;
      } else if (1612595797 * client.field856 < this.field5959 * 582342939) {
         return false;
      } else if (this.field5956.method9772(374233424) && this.field5956.method9775((byte)68).method8786(this.field5956.method9780(2049074655), 1284412146)) {
         return true;
      } else {
         Model var1 = classOR.method8760(classAI.method367(this.field5957 * -1839511320, -1302911466), -376558306);
         return null != var1 && null != var1.field2442;
      }
   }

   public void setHeight(int var1) {
      this.field5958 = var1 * -1763452565;
   }

   public int getCycle() {
      return this.field5956.field5602 * -1399668821;
   }

   public void setStartCycle(int var1) {
      this.field5959 = var1 * 668116217;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("az")
   public boolean method10496(int var1) {
      try {
         return classOR.method8762(classAI.method367(-1568506297 * this.field5957, -2050013648), -344613019);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "si.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lup;)I")
   @ObfuscatedName("or")
   public static int method10499(classUP var0) {
      return 1379695375 * var0.field6360;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   @Override
   protected int vmethod99() {
      return this.field5955.vmethod98(2131348794);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   @Override
   protected boolean vmethod95(int var1) {
      try {
         if (null == this.field5955) {
            if (var1 == -702405562) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (1612595797 * client.field856 < this.field5959 * 1168432457) {
            if (var1 == -702405562) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            if (this.field5956.method9772(374233424)) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               if (this.field5956.method9775((byte)94).method8786(this.field5956.method9780(1795053762), 1284412146)) {
                  if (var1 == -702405562) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }

            Model var2 = classOR.method8760(classAI.method367(this.field5957 * -1568506297, -1538969825), 1068370205);
            if (null == var2 || null == var2.field2442) {
               return false;
            } else if (var1 == -702405562) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "si.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsi;)Z")
   @ObfuscatedName("bt")
   public static boolean method10497(ActorSpotAnim var0) {
      if (var0 == null) {
         var0.method10498();
      }

      return classOR.method8762(classAI.method367(-1568506297 * var0.field5957, -1277442260), -866382518);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("af")
   public boolean method10501(int var1) {
      try {
         return this.field5960;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "si.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ad")
   @Override
   protected final Model vmethod92() {
      if (null == this.field5955) {
         return null;
      } else if (1612595797 * client.field856 < 216014736 * this.field5959) {
         return null;
      } else if (this.field5956.method9805(30, -1142483741)) {
         return null;
      } else {
         classOR var1 = classAI.method367(-1568506297 * this.field5957, -2039373745);
         Model var2 = var1.method8757(this.field5956.method9780(1750593616), 1603539580);
         if (var2 == null) {
            return null;
         } else {
            ItemLayer.method3980(var2, -(-666341653 * this.field5958));
            if (this.field5955.method3178(-1969508409) == 1) {
               var2.field2419 = true;
            }

            var2.method5232(this.field5955.vmethod274((byte)-70), (short)var2.field2417);
            var2.method5247(this.field5955.vmethod276(2074946198));
            ItemLayer.method3980(var2, this.field5955.vmethod278(-1426279030));
            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ap")
   @Override
   protected final Model vmethod93() {
      if (null == this.field5955) {
         return null;
      } else if (1612595797 * client.field856 < 1610117276 * this.field5959) {
         return null;
      } else if (this.field5956.method9805(30, -681914151)) {
         return null;
      } else {
         classOR var1 = classAI.method367(-216247058 * this.field5957, -2089406467);
         Model var2 = var1.method8757(this.field5956.method9780(1769764105), 2108134630);
         if (var2 == null) {
            return null;
         } else {
            ItemLayer.method3980(var2, -(825461571 * this.field5958));
            if (this.field5955.method3178(1977588115) == 1) {
               var2.field2419 = true;
            }

            var2.method5232(this.field5955.vmethod274((byte)-39), (short)var2.field2417);
            var2.method5247(this.field5955.vmethod276(1701522977));
            ItemLayer.method3980(var2, this.field5955.vmethod278(-1791702410));
            return var2;
         }
      }
   }

   public int getHeight() {
      return this.field5958 * 825461571;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ac")
   public boolean method10502() {
      return this.field5960;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ai")
   @Override
   protected boolean vmethod94() {
      if (null == this.field5955) {
         return false;
      } else if (1612595797 * client.field856 < this.field5959 * 1168432457) {
         return false;
      } else if (this.field5956.method9772(374233424) && this.field5956.method9775((byte)27).method8786(this.field5956.method9780(1749874533), 1284412146)) {
         return true;
      } else {
         Model var1 = classOR.method8760(classAI.method367(this.field5957 * -1568506297, -1941507830), -579858414);
         return null != var1 && null != var1.field2442;
      }
   }

   public int getId() {
      return this.field5957 * -1568506297;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ar")
   @Override
   protected boolean vmethod97() {
      if (null == this.field5955) {
         return false;
      } else if (1612595797 * client.field856 < this.field5959 * 1168432457) {
         return false;
      } else if (this.field5956.method9772(374233424) && this.field5956.method9775((byte)19).method8786(this.field5956.method9780(1876883277), 1284412146)) {
         return true;
      } else {
         Model var1 = classOR.method8760(classAI.method367(this.field5957 * -1568506297, -2108963618), 219138195);
         return null != var1 && null != var1.field2442;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   @Override
   protected int vmethod98(int var1) {
      try {
         return this.field5955.vmethod98(1242417669);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "si.ag(" + 41);
      }
   }

   public void setId(int var1) {
      this.field5957 = var1 * -861680777;
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("ae")
   @Override
   protected final Model vmethod104(int var1) {
      int var2 = this.field5956.field5600 * 292569817;
      if (var2 != -1 && client.field1115 != null && client.field1115.test(this.field5956.field5596 * 1684838611)) {
         this.field5956.field5600 = (-2147483648 | Math.max(this.field5956.field5602 * -1399668821 - 1, 0) << 16 | var2) * -885908119;
      }

      Model var3;
      try {
         ActorSpotAnim var5 = this;
         int var6 = -891174633;

         Model var10000;
         try {
            if (null == var5.field5955) {
               if (var6 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var5 = null;
               var10000 = var5;
            } else if (1612595797 * client.field856 < 1168432457 * var5.field5959) {
               if (var6 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var5 = null;
               var10000 = var5;
            } else if (var5.field5956.method9805(30, -1003247398)) {
               if (var6 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var5 = null;
               var10000 = var5;
            } else {
               classOR var7 = classAI.method367(-1568506297 * var5.field5957, -1406103855);
               Model var8 = var7.method8757(var5.field5956.method9780(2073738908), 875936462);
               if (var8 == null) {
                  if (var6 <= -1767431455) {
                     throw new IllegalStateException();
                  }

                  var5 = null;
                  var10000 = var5;
               } else {
                  ItemLayer.method3980(var8, -(825461571 * var5.field5958));
                  if (var5.field5955.method3178(-2032654562) == 1) {
                     if (var6 <= -1767431455) {
                        throw new IllegalStateException();
                     }

                     var8.field2419 = true;
                  }

                  var8.method5232(var5.field5955.vmethod274((byte)-87), (short)var8.field2417);
                  var8.method5247(var5.field5955.vmethod276(1859691167));
                  ItemLayer.method3980(var8, var5.field5955.vmethod278(-1814170184));
                  var10000 = var8;
               }
            }
         } catch (RuntimeException var11) {
            throw classEG.method3899(var11, "si.ae(" + ')');
         }

         var3 = var10000;
      } finally {
         this.field5956.field5600 = var2 * -885908119;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   @Override
   protected int vmethod101() {
      return this.field5955.vmethod98(608492936);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   @Override
   protected int vmethod102() {
      return this.field5955.vmethod98(1845331531);
   }

   public int getStartCycle() {
      return this.field5959 * 1168432457;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   @Override
   protected int vmethod103() {
      return this.field5955.vmethod98(1690808201);
   }

   public ActorSpotAnim(int var1, int var2, int var3, Actor var4, boolean var5) {
      this.field5956 = new classQR();
      this.field5959 = 0;
      this.field5958 = 0;
      this.field5957 = -861680777 * var1;
      this.field5958 = var2 * -1763452565;
      this.field5959 = var3 * 668116217;
      this.field5955 = var4;
      if (-1568506297 * this.field5957 >= 0) {
         classQR.method9770(this.field5956, classAI.method367(-1568506297 * this.field5957, -1795637479).field5081 * 696329207, -2084471964);
      }

      this.field5960 = var5;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ax")
   public boolean method10498() {
      return classOR.method8762(classAI.method367(-1568506297 * this.field5959, -1277442260), -866382518);
   }

   public void setFrame(int var1) {
      this.field5956.field5600 = var1 * -885908119;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ao")
   public boolean method10503() {
      return this.field5960;
   }
}
