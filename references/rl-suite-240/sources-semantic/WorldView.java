import java.util.Iterator;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AmbientSoundEffectCreated;
import net.runelite.api.events.ItemSpawned;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dz")
public class WorldView extends Node implements net.runelite.api.WorldView {
   @ObfuscatedName("as")
   public int field1694;
   @ObfuscatedName("kd")
   public int[] field1711;
   @ObfuscatedSignature(descriptor = "[[[Lnn;")
   @ObfuscatedName("wr")
   public final Deque2[][][] field1684;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("az")
   final Deque2 field1693;
   @ObfuscatedName("xi")
   public boolean field1710;
   @ObfuscatedSignature(descriptor = "Lez;")
   @ObfuscatedName("ae")
   public Scene field1687;
   @ObfuscatedSignature(descriptor = "[Lgc;")
   @ObfuscatedName("ab")
   CollisionData[] field1685;
   @ObfuscatedName("ag")
   public int field1708;
   @ObfuscatedName("ao")
   int[][] field1701;
   @ObfuscatedName("av")
   static int[][] field1703 = new int[2][3];
   @ObfuscatedName("va")
   public int field1709;
   @ObfuscatedName("aa")
   int field1706;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("lk")
   public Deque2 field1683 = new Deque2();
   @ObfuscatedName("af")
   public int field1697;
   @ObfuscatedName("ac")
   int field1707;
   @ObfuscatedName("al")
   int[][][] field1686;
   @ObfuscatedName("ro")
   public int[][][] field1712;
   @ObfuscatedName("aj")
   byte[][][] field1700;
   @ObfuscatedSignature(descriptor = "Lyk;")
   @ObfuscatedName("aq")
   public IndexedObjectSet field1692;
   @ObfuscatedSignature(descriptor = "Lzh;")
   @ObfuscatedName("aw")
   classZH field1691;
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("ad")
   IterableHashTable field1698;
   @ObfuscatedSignature(descriptor = "[[[Lnn;")
   @ObfuscatedName("ap")
   Deque2[][][] field1699;
   @ObfuscatedName("ax")
   public int field1690;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("ai")
   Deque2 field1689;
   @ObfuscatedSignature(descriptor = "Lzh;")
   @ObfuscatedName("ar")
   classZH field1695;
   @ObfuscatedName("ak")
   static int[][] field1702 = new int[2][3];
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("au")
   Deque2 field1688;
   @ObfuscatedSignature(descriptor = "Lyk;")
   @ObfuscatedName("ay")
   public IndexedObjectSet field1696;
   @ObfuscatedName("at")
   static int[][] field1704 = new int[2][3];

   public int[] getMapRegions() {
      return this.field1711;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("az")
   public boolean method3734(int var1) {
      try {
         boolean var10000;
         if (0 == this.field1697 * 2140889407) {
            if (var1 != -2087374717) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dz.az(" + ')');
      }
   }

   public boolean isTopLevel() {
      return this.field1697 * 2140889407 == 0;
   }

   public int getSizeX() {
      return this.field1694 * 1296729483;
   }

   public int getSizeY() {
      return this.field1690 * -1269171107;
   }

   public net.runelite.api.Tile getSelectedSceneTile() {
      int var1 = classOE.field4841.isMenuOpen() ? this.field1687.field2034 : this.field1687.field2024;
      int var2 = classOE.field4841.isMenuOpen() ? this.field1687.field2035 : this.field1687.field2032;
      return var1 >= this.field1687.field2110 && var2 >= this.field1687.field2145 && var1 < this.field1687.field2142 && var2 < this.field1687.field2139
         ? this.field1687.field1991[this.getPlane()][var1 + this.field1687.field2146][var2 + this.field1687.field2146]
         : null;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIII)Lky;")
   @ObfuscatedName("ed")
   public static classKY method3740(WorldView var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         return var0.method3742(var1, var1, var1, var1);
      } else {
         try {
            return new classKY(var1, var2 - var0.field1706 * -1444178379, var3 - -351145363 * var0.field1707);
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "dz.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   void method3737(int var1) {
      try {
         this.field1695.method14053(2124753242);
         this.field1691.method14053(2005189327);
         this.field1692.method13443();
         this.field1696.method13443();
         this.field1698.method13623();
         Deque2.method7942(this.field1689);
         this.field1688 = new Deque2();

         for (int var2 = 0; var2 < 4; var2++) {
            if (var1 == 874904841) {
               return;
            }

            for (int var3 = 0; var3 < this.field1694 * 1296729483; var3++) {
               if (var1 == 874904841) {
                  throw new IllegalStateException();
               }

               for (int var4 = 0; var4 < this.field1690 * -1269171107; var4++) {
                  if (var1 == 874904841) {
                     throw new IllegalStateException();
                  }

                  this.field1699[var2][var3][var4] = null;
               }
            }
         }

         this.field1687.method4455();

         for (int var6 = 0; var6 < 4; var6++) {
            if (var1 == 874904841) {
               throw new IllegalStateException();
            }

            CollisionData.method5356(this.field1685[var6], (byte)-61);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dz.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfn;IIII)I")
   @ObfuscatedName("bl")
   int method3778(SceneTileModel var1, int var2, int var3, int var4, int var5) {
      for (int var6 = 0; var6 < var1.field2328.length; var6++) {
         int var7 = var1.field2320[var1.field2328[var6]];
         int var8 = var1.field2325[var1.field2328[var6]];
         int var9 = var1.field2320[var1.field2329[var6]];
         int var10 = var1.field2325[var1.field2329[var6]];
         int var11 = var1.field2320[var1.field2327[var6]];
         int var12 = var1.field2325[var1.field2327[var6]];
         if (Scene.method4174(var4, var5, var8, var10, var12, var7, var9, var11)) {
            int var13 = var1.field2321[var1.field2328[var6]];
            int var14 = var1.field2321[var1.field2329[var6]];
            int var15 = var1.field2321[var1.field2327[var6]];
            float var16 = classVI.method12032(var4, var5, var7, var9, var11, var8, var10, var12, var13, var14, var15, -2120871691);
            return (int)var16;
         }
      }

      return -1067550383;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aq")
   void method3743(int var1) {
      try {
         this.field1692.method13443();
         Iterator var2 = this.field1696.iterator();

         while (var2.hasNext()) {
            if (var1 != -1008620329) {
               throw new IllegalStateException();
            }

            Actor var3 = (Actor)var2.next();
            Actor.method3119(var3, -1633029114);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dz.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfj;IIIII)I")
   @ObfuscatedName("sv")
   public int method3800(SceneTilePaint var1, int var2, int var3, int var4, int var5, int var6) {
      return this.method3787(var1, var2, var3, var4, var5, var6, 206456977);
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("as")
   boolean method3747(int var1, int var2, int var3, int var4) {
      try {
         if (var1 >= 0) {
            if (var4 <= -426741308) {
               throw new IllegalStateException();
            }

            if (var1 < 4) {
               if (var4 <= -426741308) {
                  throw new IllegalStateException();
               }

               if (method3745(this, var2, var3, (byte)1)) {
                  if (var4 <= -426741308) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dz.as(" + ')');
      }
   }

   public int[][][] getInstanceTemplateChunks() {
      return this.field1712;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;III)Z")
   @ObfuscatedName("dt")
   public static boolean method3748(WorldView var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.getSizeY();
      }

      return var1 >= 0 && var1 < 4 && method3745(var0, var2, var3, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "(IB)Lct;")
   @ObfuscatedName("ax")
   Player method3752(int var1, byte var2) {
      try {
         return (Player)IndexedObjectSet.method13449(this.field1692, var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dz.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILom;II)V")
   @ObfuscatedName("ac")
   void method3755(int var1, int var2, int var3, ObjectComposition var4, int var5, int var6) {
      try {
         int var7 = ObjectComposition.method8611(var4, var5, -1896072079);
         int var8 = var4.method8619(var5, 1249328515);
         int var9 = classKY.method6649(var2, -527572662);
         int var10 = classKY.method6649(var3, 218210818);
         int var11 = classKY.method6649(var7 + var2, 2004953659);
         int var12 = classKY.method6649(var3 + var8, -402193513);
         classCR var13 = new classCR(var1, var9, var10, var11, var12, var4);
         this.field1693.method7946(var13);
         this.method3795(var1, var2, var3, var4, var5);
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "dz.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("bp")
   boolean method3749(int var1, int var2, int var3) {
      return var1 >= 0 && var1 < 4 && method3745(this, var2, var3, (byte)1);
   }

   public net.runelite.api.IndexedObjectSet npcs() {
      return this.field1696;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;)V")
   @ObfuscatedName("tr")
   public static void method3738(WorldView var0) {
      var0.field1695.method14053(2101919824);
      var0.field1691.method14053(2088514586);
      var0.field1692.method13443();
      var0.field1696.method13443();
      var0.field1698.method13623();
      Deque2.method7942(var0.field1689);
      var0.field1688 = new Deque2();

      for (int var1 = 0; var1 < 4; var1++) {
         for (int var2 = 0; var2 < var0.field1694 * 1296729483; var2++) {
            for (int var3 = 0; var3 < var0.field1690 * -1269171107; var3++) {
               var0.field1699[var1][var2][var3] = null;
            }
         }
      }

      var0.field1687.method4455();

      for (int var4 = 0; var4 < 4; var4++) {
         CollisionData.method5356(var0.field1685[var4], (byte)-50);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("vw")
   public void method3801() {
      method3759(this, (byte)-59);
   }

   @ObfuscatedSignature(descriptor = "(IIILom;IB)V")
   @ObfuscatedName("al")
   void method3764(int var1, int var2, int var3, ObjectComposition var4, int var5, byte var6) {
      try {
         if (null != var4) {
            if (var6 <= 1) {
               return;
            }

            if (var4.method8601(1754865660)) {
               if (var6 <= 1) {
                  throw new IllegalStateException();
               }

               int var7 = ObjectComposition.method8611(var4, var5, -1979058614);
               int var8 = var4.method8619(var5, -276309713);
               int var9 = classKY.method6649(var2 + var7, 118380155);
               int var10 = classKY.method6649(var8 + var3, -16555961);
               var2 = classKY.method6649(var2, 2138051003);
               var3 = classKY.method6649(var3, 875762498);
               int var11 = var4.method8606(-1249402524).field6687 * -1727985133;
               int var12 = classKY.method6649(var4.method8606(-1821838560).field6684 * 1535961601, 1598319191);
               int var13 = Math.max(classKY.method6649(var4.method8606(-1616879893).field6686 * -2063946921 - 1, 1314812060), 0);
               if (var4.field4928 != null) {
                  if (var6 <= 1) {
                     throw new IllegalStateException();
                  }

                  ObjectComposition var14 = ObjectComposition.method8585(var4, 1360030034);
                  if (null != var14) {
                     if (var6 <= 1) {
                        throw new IllegalStateException();
                     }

                     var11 = var14.method8606(-1456592265).field6687 * -1727985133;
                     var12 = classKY.method6649(var14.method8606(-1802769323).field6684 * 1535961601, -376664328);
                     var13 = Math.max(classKY.method6649(var4.method8606(-1814346068).field6686 * -2063946921 - 1, 1359448822), 0);
                  }
               }

               for (AmbientSoundEffect var18 = (AmbientSoundEffect)this.field1693.method7957();
                  var18 != null;
                  var18 = (AmbientSoundEffect)this.field1693.method7965()
               ) {
                  if (var6 <= 1) {
                     throw new IllegalStateException();
                  }

                  if (AmbientSoundEffect.method3682(var18, 218576106) == var1) {
                     if (var6 <= 1) {
                        throw new IllegalStateException();
                     }

                     if (var18.vmethod194(-1994134615) == var2 && var18.vmethod202(-1735750709) == var3 && var18.vmethod197(-1282897451) == var9) {
                        if (var6 <= 1) {
                           return;
                        }

                        if (var18.vmethod205(24841222) == var10) {
                           if (var6 <= 1) {
                              throw new IllegalStateException();
                           }

                           if (var18.method3678(588158173) == var11 && var18.method3683(1698165211) == var12) {
                              if (var6 <= 1) {
                                 throw new IllegalStateException();
                              }

                              if (var18.method3687(1084310679) == var13) {
                                 if (var6 <= 1) {
                                    return;
                                 }

                                 var18.method3675(806947207);
                                 break;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "dz.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bm")
   public void method3799() {
      this.field1687.field2002 = this;
      this.field1687.field2106 = this.field1697 * 2140889407;
      this.field1687.field2155 = this.field1700;
   }

   @ObfuscatedSignature(descriptor = "(J)Lcd;")
   @ObfuscatedName("wi")
   public classCD method3793(long var1) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else if ((var1 >> 16 & 7L) != 2L) {
         return null;
      } else {
         int var3 = (int)(var1 >> 0 & 127L);
         int var4 = (int)(var1 >> 7 & 127L);
         int var5 = (int)(var1 >> 14 & 3L);
         int var6 = (int)(var1 >> 20 & 4294967295L);

         for (classCD var7 = (classCD)this.field1688.method7976(); var7 != null; var7 = (classCD)this.field1688.method7977()) {
            if (var6 == var7.field675 * 1490231037
               && var5 == var7.field679 * -1436432311
               && var3 == var7.field676 * -1779660971
               && var4 == var7.field680 * -522576623) {
               return var7;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILom;I)V")
   @ObfuscatedName("lz")
   public void method3795(int var1, int var2, int var3, ObjectComposition var4, int var5) {
      AmbientSoundEffect var6 = (AmbientSoundEffect)this.field1693.method7975();
      AmbientSoundEffectCreated var7 = new AmbientSoundEffectCreated(var6);
      classOE.field4841.getCallbacks().post(var7);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IIIII)I")
   @ObfuscatedName("ez")
   public static int method3785(WorldView var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         var0.getTileHeights();
      }

      int var6 = var3 & -545782521;
      int var7 = var4 & 127;
      int var8 = var0.field1686[var5][var1 + 1][var2] * var6 + (128 - var6) * var0.field1686[var5][var1][var2] >> 7;
      int var9 = var0.field1686[var5][1 + var1][var2 + 1] * var6 + var0.field1686[var5][var1][1 + var2] * (128 - var6) >> 7;
      return var8 * (128 - var7) + var9 * var7 >> 7;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IIILom;I)V")
   @ObfuscatedName("um")
   public static void method3765(WorldView var0, int var1, int var2, int var3, ObjectComposition var4, int var5) {
      if (var0 == null) {
         var0.method3797(var1);
      }

      if (null != var4 && var4.method8601(1754865660)) {
         int var6 = ObjectComposition.method8611(var4, var5, -1385793668);
         int var7 = var4.method8619(var5, -71238519);
         int var8 = classKY.method6649(var2 + var6, 259812693);
         int var9 = classKY.method6649(var7 + var3, 14668127);
         var2 = classKY.method6649(var2, 1315254214);
         var3 = classKY.method6649(var3, -274226004);
         int var10 = var4.method8606(-1254459553).field6687 * -1727985133;
         int var11 = classKY.method6649(var4.method8606(-2033610111).field6684 * 1535961601, 1858804740);
         int var12 = Math.max(classKY.method6649(var4.method8606(-1298161905).field6686 * -1426226189 - 1, 1232211719), 0);
         if (var4.field4928 != null) {
            ObjectComposition var13 = ObjectComposition.method8585(var4, -1451486665);
            if (null != var13) {
               var10 = var13.method8606(-1280306061).field6687 * -1727985133;
               var11 = classKY.method6649(var13.method8606(-2060063372).field6684 * -1317976076, 447338614);
               var12 = Math.max(classKY.method6649(var4.method8606(-1742013372).field6686 * 835235075 - 1, 1305088228), 0);
            }
         }

         for (AmbientSoundEffect var16 = (AmbientSoundEffect)var0.field1693.method7957();
            var16 != null;
            var16 = (AmbientSoundEffect)var0.field1693.method7965()
         ) {
            if (AmbientSoundEffect.method3682(var16, -2134311774) == var1
               && var16.vmethod194(-401460861) == var2
               && var16.vmethod202(-1903305469) == var3
               && var16.vmethod197(-1630259211) == var8
               && var16.vmethod205(24841222) == var9
               && var16.method3678(-209940790) == var10
               && var16.method3683(1705283341) == var11
               && var16.method3687(1197148899) == var12) {
               var16.method3675(-2006988233);
               break;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;Lfj;IIIII)I")
   @ObfuscatedName("dm")
   public static int method3781(WorldView var0, SceneTilePaint var1, int var2, int var3, int var4, int var5, int var6) {
      if (var0 == null) {
         return var0.method3776(var2, var2, var2);
      } else if (var1.field2281) {
         return var0.field1686[var6][var2][var3];
      } else {
         TextureProvider.method3821(0, var0.field1686, var6, var2, var3, field1702[0], field1703[0], field1704[0], 1181281029);
         TextureProvider.method3821(1, var0.field1686, var6, var2, var3, field1702[1], field1703[1], field1704[1], 1181281029);

         for (int var7 = 0; var7 < 2; var7++) {
            int var8 = field1702[var7][0];
            int var9 = field1704[var7][0];
            int var10 = field1702[var7][1];
            int var11 = field1704[var7][1];
            int var12 = field1702[var7][2];
            int var13 = field1704[var7][2];
            if (Scene.method4174(var4, var5, var9, var11, var13, var8, var10, var12)) {
               int var14 = field1703[var7][0];
               int var15 = field1703[var7][1];
               int var16 = field1703[var7][2];
               float var17 = classVI.method12032(var4, var5, var8, var10, var12, var9, var11, var13, var14, var15, var16, -798762912);
               return (int)var17;
            }
         }

         return -1779719045;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsp;ILdw;I)Z")
   @ObfuscatedName("aj")
   boolean method3768(WorldEntity var1, int var2, AmbientSoundEffect var3, int var4) {
      try {
         WorldEntity var5 = client.field815.method1594(this.field1697 * 2140889407, -4048135);
         boolean var10000;
         if (var1 == var5) {
            if (var4 >= 1009431996) {
               throw new IllegalStateException();
            }

            var10000 = 1;
         } else {
            var10000 = 0;
         }

         boolean var6 = (boolean)var10000;
         if (var6) {
            if (var4 >= 1009431996) {
               throw new IllegalStateException();
            }

            if (var2 != AmbientSoundEffect.method3682(var3, -724053856)) {
               if (var4 >= 1009431996) {
                  throw new IllegalStateException();
               }

               return false;
            }
         } else {
            if (var1 != null) {
               if (var4 >= 1009431996) {
                  throw new IllegalStateException();
               }

               if (var1.field6024.method8500(-1821270104) != var2) {
                  if (var4 >= 1009431996) {
                     throw new IllegalStateException();
                  }

                  return false;
               }
            }

            if (null != var5) {
               if (var4 >= 1009431996) {
                  throw new IllegalStateException();
               }

               if (var5.field6024.method8500(-1821270104) != AmbientSoundEffect.method3682(var3, 734241976)) {
                  if (var4 >= 1009431996) {
                     throw new IllegalStateException();
                  }

                  return false;
               }
            }

            if (null == var1) {
               if (var4 >= 1009431996) {
                  throw new IllegalStateException();
               }

               var10000 = var2;
            } else {
               var10000 = var1.vmethod258((byte)-26);
            }

            int var7 = var10000;
            int var8 = var5 == null ? AmbientSoundEffect.method3682(var3, -948186153) : var5.vmethod258((byte)-122);
            if (var7 != var8) {
               return false;
            }
         }

         switch (-762797223 * var3.field1651.field6688.field3538) {
            case 0:
               return true;
            case 1:
               return var6;
            case 2:
               if (var1 != var5) {
                  if (var4 >= 1009431996) {
                     throw new IllegalStateException();
                  }

                  if (var5 != null) {
                     return false;
                  }

                  if (var4 >= 1009431996) {
                     throw new IllegalStateException();
                  }
               }

               return true;
            default:
               return false;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "dz.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsp;ILdw;)Z")
   @ObfuscatedName("bb")
   boolean method3769(WorldEntity var1, int var2, AmbientSoundEffect var3) {
      WorldEntity var4 = client.field815.method1594(this.field1697 * 2140889407, -307658315);
      boolean var5 = var1 == var4;
      if (var5) {
         if (var2 != AmbientSoundEffect.method3682(var3, -2079686616)) {
            return false;
         }
      } else {
         if (var1 != null && var1.field6024.method8500(-1821270104) != var2) {
            return false;
         }

         if (null != var4 && var4.field6024.method8500(-1821270104) != AmbientSoundEffect.method3682(var3, -997052817)) {
            return false;
         }

         int var6 = null == var1 ? var2 : var1.vmethod258((byte)-114);
         int var7 = var4 == null ? AmbientSoundEffect.method3682(var3, -476094703) : var4.vmethod258((byte)-101);
         if (var6 != var7) {
            return false;
         }
      }

      switch (-762797223 * var3.field1651.field6688.field3538) {
         case 0:
            return true;
         case 1:
            return var5;
         case 2:
            return var1 == var4 || var4 == null;
         default:
            return false;
      }
   }

   WorldView(int var1, int var2, int var3, int var4, classEX var5) {
      this.field1684 = new Deque2[4][var2][var3];
      this.field1693 = new Deque2();
      this.field1685 = new CollisionData[4];
      this.field1688 = new Deque2();
      this.field1689 = new Deque2();
      this.field1695 = new classZH(149);
      this.field1691 = new classZH(25);
      this.field1697 = 318705343 * var1;
      this.field1694 = -1577911773 * var2;
      this.field1690 = var3 * -1665681419;
      this.field1692 = new IndexedObjectSet(var1 == 0 ? 512 : 8);
      this.field1696 = new IndexedObjectSet(0 == var1 ? 128 : 8);
      this.field1698 = new IterableHashTable(var1 == 0 ? 32 : 1);
      this.field1699 = new Deque2[4][var2][var3];
      this.field1686 = new int[4][var2 + 1][var3 + 1];
      this.field1700 = new byte[4][var2][var3];
      this.field1701 = new int[var2][var3];
      boolean var6 = var1 != 0;

      for (int var7 = 0; var7 < 4; var7++) {
         this.field1685[var7] = new CollisionData(var2, var3, var6);
      }

      this.field1687 = new Scene(this.method3734(-2087374717), 4, var2, var3, var4, var5, this.field1686);
      this.method3799();
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ms")
   public int method3802(int var1, int var2, int var3) {
      return this.method3794(var1, var2, var3, (byte)0);
   }

   public net.runelite.api.IndexedObjectSet players() {
      return this.field1692;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   void method3774(int var1) {
      try {
         for (AmbientSoundEffect var2 = (AmbientSoundEffect)this.field1693.method7957(); null != var2; var2 = (AmbientSoundEffect)this.field1693.method7965()) {
            if (var1 != -1592433219) {
               return;
            }

            var2.vmethod189(-380921692);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dz.aq(" + ')');
      }
   }

   public boolean contains(LocalPoint var1) {
      if (var1.getWorldView() != this.field1697 * 2140889407) {
         return false;
      } else {
         int var2 = var1.getX();
         int var3 = var1.getY();
         var2 >>= 7;
         var3 >>= 7;
         return var2 >= 0 && var2 < this.field1694 * 1296729483 && var3 >= 0 && var3 < this.field1690 * -1269171107;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfn;IIIIB)I")
   @ObfuscatedName("ap")
   int method3779(SceneTileModel var1, int var2, int var3, int var4, int var5, byte var6) {
      try {
         for (int var7 = 0; var7 < var1.field2328.length; var7++) {
            if (var6 == 15) {
               throw new IllegalStateException();
            }

            int var8 = var1.field2320[var1.field2328[var7]];
            int var9 = var1.field2325[var1.field2328[var7]];
            int var10 = var1.field2320[var1.field2329[var7]];
            int var11 = var1.field2325[var1.field2329[var7]];
            int var12 = var1.field2320[var1.field2327[var7]];
            int var13 = var1.field2325[var1.field2327[var7]];
            if (Scene.method4174(var4, var5, var9, var11, var13, var8, var10, var12)) {
               int var14 = var1.field2321[var1.field2328[var7]];
               int var15 = var1.field2321[var1.field2329[var7]];
               int var16 = var1.field2321[var1.field2327[var7]];
               float var17 = classVI.method12032(var4, var5, var8, var10, var12, var9, var11, var13, var14, var15, var16, -126722077);
               return (int)var17;
            }
         }

         return -2147483647;
      } catch (RuntimeException var18) {
         throw classEG.method3899(var18, "dz.ap(" + 41);
      }
   }

   public int getTileHeight(int var1, int var2, int var3) {
      int var4 = (var1 >> 7) + this.field1687.field2146;
      int var5 = (var2 >> 7) + this.field1687.field2146;
      int var6 = this.field1687.field2146 << 1;
      if (var4 >= 0 && var5 >= 0 && var4 < this.field1694 * 1296729483 + var6 && var5 < this.field1690 * -1269171107 + var6) {
         int var7 = var3;
         if (var3 > 0 && (this.field1687.field2155[1][var4][var5] & 2) == 2) {
            var7 = var3 - 1;
         }

         rl10 var8 = this.field1687.field1991[var7][var4][var5];
         int var9 = -2147483647;
         if (var8 != null) {
            SceneTileModel var10 = var8.method10074();
            if (var10 != null) {
               var9 = this.method3803(var10, -1, -1, var1, var2);
            }

            SceneTilePaint var11 = var8.method10080();
            if (var9 == -2147483647 && var11 != null) {
               var9 = this.method3800(var11, var1 >> 7, var2 >> 7, var1, var2, var3);
            }
         }

         if (var9 == -2147483647) {
            var9 = this.method3790(var4, var5, var1, var2, var3);
         }

         if (var8 != null) {
            GroundObject var12 = var8.method10072();
            if (var12 != null) {
               ObjectComposition var13 = ObjectComposition.method8638(var12.getId());
               var9 -= var13.field4935 * 977129717;
            }
         }

         return var9;
      } else {
         return 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfj;IIIIII)I")
   @ObfuscatedName("au")
   int method3787(SceneTilePaint var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      var2 += this.field1687.field2146;
      var3 += this.field1687.field2146;
      var4 += this.field1687.field2146 << 7;
      var5 += this.field1687.field2146 << 7;
      int[][][] var8 = this.field1686;
      this.field1686 = this.field1687.field2058;
      int var16 = var6;
      int var15 = var5;
      int var14 = var4;
      int var13 = var3;
      int var12 = var2;
      SceneTilePaint var11 = var1;
      WorldView var10 = this;
      int var17 = 206456977;

      int var10000;
      try {
         if (var11.field2281) {
            if (var17 <= -360080939) {
               throw new IllegalStateException();
            }

            int var34 = var10.field1686[var16][var12][var13];
            var10000 = var34;
         } else {
            label49: {
               TextureProvider.method3821(0, var10.field1686, var16, var12, var13, field1702[0], field1703[0], field1704[0], 1181281029);
               TextureProvider.method3821(1, var10.field1686, var16, var12, var13, field1702[1], field1703[1], field1704[1], 1181281029);

               for (int var18 = 0; var18 < 2; var18++) {
                  if (var17 <= -360080939) {
                     throw new IllegalStateException();
                  }

                  int var19 = field1702[var18][0];
                  int var20 = field1704[var18][0];
                  int var21 = field1702[var18][1];
                  int var22 = field1704[var18][1];
                  int var23 = field1702[var18][2];
                  int var24 = field1704[var18][2];
                  if (Scene.method4174(var14, var15, var20, var22, var24, var19, var21, var23)) {
                     if (var17 <= -360080939) {
                        throw new IllegalStateException();
                     }

                     int var25 = field1703[var18][0];
                     int var26 = field1703[var18][1];
                     int var27 = field1703[var18][2];
                     float var28 = classVI.method12032(var14, var15, var19, var21, var23, var20, var22, var24, var25, var26, var27, 824506377);
                     int var35 = (int)var28;
                     var10000 = var35;
                     break label49;
                  }
               }

               int var36 = -2147483647;
               var10000 = var36;
            }
         }
      } catch (RuntimeException var29) {
         throw classEG.method3899(var29, "dz.au(" + 41);
      }

      int var9 = var10000;
      this.field1686 = var8;
      return var9;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("ai")
   int method3786(int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         int var7 = var3 & 127;
         int var8 = var4 & 127;
         int var9 = this.field1686[var5][var1 + 1][var2] * var7 + (128 - var7) * this.field1686[var5][var1][var2] >> 7;
         int var10 = this.field1686[var5][1 + var1][var2 + 1] * var7 + this.field1686[var5][var1][1 + var2] * (128 - var7) >> 7;
         return var9 * (128 - var8) + var10 * var8 >> 7;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "dz.ai(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ldz;")
   @ObfuscatedName("nx")
   public static WorldView method3792(int var0) {
      return var0 == -1 ? classIS.field3051 : client.field815.method1627(var0);
   }

   public int getBaseX() {
      return this.field1706 * -1444178379;
   }

   @ObfuscatedSignature(descriptor = "(Lfn;IIII)I")
   @ObfuscatedName("gb")
   public int method3803(SceneTileModel var1, int var2, int var3, int var4, int var5) {
      return this.method3779(var1, var2, var3, var4, var5, (byte)-87);
   }

   @ObfuscatedSignature(descriptor = "(IIILom;I)V")
   @ObfuscatedName("ba")
   void method3766(int var1, int var2, int var3, ObjectComposition var4, int var5) {
      if (null != var4 && var4.method8601(1754865660)) {
         int var6 = ObjectComposition.method8611(var4, var5, -1469446953);
         int var7 = var4.method8619(var5, 1388814788);
         int var8 = classKY.method6649(var2 + var6, -38304338);
         int var9 = classKY.method6649(var7 + var3, 706360866);
         var2 = classKY.method6649(var2, 54716419);
         var3 = classKY.method6649(var3, 1652895060);
         int var10 = var4.method8606(-1980312574).field6687 * -1727985133;
         int var11 = classKY.method6649(var4.method8606(-1931582258).field6684 * 2097251426, 848002459);
         int var12 = Math.max(classKY.method6649(var4.method8606(-1766750118).field6686 * -670347941 - 1, -120301236), 0);
         if (var4.field4928 != null) {
            ObjectComposition var13 = ObjectComposition.method8585(var4, -454388092);
            if (null != var13) {
               var10 = var13.method8606(-2146431517).field6687 * 479392374;
               var11 = classKY.method6649(var13.method8606(-2131632046).field6684 * -157597696, 842843077);
               var12 = Math.max(classKY.method6649(var4.method8606(-1234850696).field6686 * 236744595 - 1, -120922420), 0);
            }
         }

         for (AmbientSoundEffect var16 = (AmbientSoundEffect)this.field1693.method7957();
            var16 != null;
            var16 = (AmbientSoundEffect)this.field1693.method7965()
         ) {
            if (AmbientSoundEffect.method3682(var16, -708560233) == var1
               && var16.vmethod194(-514193801) == var2
               && var16.vmethod202(-2016889769) == var3
               && var16.vmethod197(-1174423672) == var8
               && var16.vmethod205(24841222) == var9
               && var16.method3678(6744971) == var10
               && var16.method3683(1960118269) == var11
               && var16.method3687(836999950) == var12) {
               var16.method3675(1602895441);
               break;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfj;IIIII)I")
   @ObfuscatedName("bh")
   int method3782(SceneTilePaint var1, int var2, int var3, int var4, int var5, int var6) {
      if (var1.field2281) {
         return this.field1686[var6][var2][var3];
      } else {
         TextureProvider.method3821(0, this.field1686, var6, var2, var3, field1702[0], field1703[0], field1704[0], 1181281029);
         TextureProvider.method3821(1, this.field1686, var6, var2, var3, field1702[1], field1703[1], field1704[1], 1181281029);

         for (int var7 = 0; var7 < 2; var7++) {
            int var8 = field1702[var7][0];
            int var9 = field1704[var7][0];
            int var10 = field1702[var7][1];
            int var11 = field1704[var7][1];
            int var12 = field1702[var7][2];
            int var13 = field1704[var7][2];
            if (Scene.method4174(var4, var5, var9, var11, var13, var8, var10, var12)) {
               int var14 = field1703[var7][0];
               int var15 = field1703[var7][1];
               int var16 = field1703[var7][2];
               float var17 = classVI.method12032(var4, var5, var8, var10, var12, var9, var11, var13, var14, var15, var16, 1207987102);
               return (int)var17;
            }
         }

         return -2147483647;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   void method3739() {
      this.field1695.method14053(2136612390);
      this.field1691.method14053(2027831199);
      this.field1692.method13443();
      this.field1696.method13443();
      this.field1698.method13623();
      Deque2.method7942(this.field1689);
      this.field1688 = new Deque2();

      for (int var1 = 0; var1 < 4; var1++) {
         for (int var2 = 0; var2 < this.field1694 * 1824995108; var2++) {
            for (int var3 = 0; var3 < this.field1690 * -1952049998; var3++) {
               this.field1699[var1][var2][var3] = null;
            }
         }
      }

      this.field1687.method4455();

      for (int var4 = 0; var4 < 4; var4++) {
         CollisionData.method5356(this.field1685[var4], (byte)-61);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cg")
   public void method3791() {
      if (classOE.field4841.method2333() != null) {
         rl10[][][] var1 = this.field1687.method4458();
         Deque2[][] var2 = this.field1699[this.field1708 * -483624883];

         for (int var3 = 0; var3 < 104; var3++) {
            for (int var4 = 0; var4 < 104; var4++) {
               Deque2 var5 = var2[var3][var4];
               if (var5 != null) {
                  rl10 var6 = var1[this.field1708 * -483624883][var3][var4];
                  if (this.field1687.method4426(var6.field5690)) {
                     assert var6.method10071() != null;

                     for (TileItem var7 = (TileItem)var5.method7976(); var7 != null; var7 = (TileItem)var5.method7977()) {
                        var7.method3422(var3);
                        var7.method3425(var4);
                        var7.field1554 = this.getId();
                        ItemSpawned var8 = new ItemSpawned(var6, var7);
                        classOE.field4841.getCallbacks().post(var8);
                     }
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;Lfj;IIIII)I")
   @ObfuscatedName("ca")
   public static int method3783(WorldView var0, SceneTilePaint var1, int var2, int var3, int var4, int var5, int var6) {
      if (var0 == null) {
         var0.method3749(var2, var2, var2);
      }

      if (var1.field2281) {
         return var0.field1686[var6][var2][var3];
      } else {
         TextureProvider.method3821(0, var0.field1686, var6, var2, var3, field1702[0], field1703[0], field1704[0], 1181281029);
         TextureProvider.method3821(1, var0.field1686, var6, var2, var3, field1702[1], field1703[1], field1704[1], 1181281029);

         for (int var7 = 0; var7 < 2; var7++) {
            int var8 = field1702[var7][0];
            int var9 = field1704[var7][0];
            int var10 = field1702[var7][1];
            int var11 = field1704[var7][1];
            int var12 = field1702[var7][2];
            int var13 = field1704[var7][2];
            if (Scene.method4174(var4, var5, var9, var11, var13, var8, var10, var12)) {
               int var14 = field1703[var7][0];
               int var15 = field1703[var7][1];
               int var16 = field1703[var7][2];
               float var17 = classVI.method12032(var4, var5, var8, var10, var12, var9, var11, var13, var14, var15, var16, -1417746303);
               return (int)var17;
            }
         }

         return -2147483647;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;III)I")
   @ObfuscatedName("jd")
   public static int method3775(WorldView var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.players();
      }

      int var4 = classKY.method6652(var1, (byte)-116);
      int var5 = classKY.method6652(var2, (byte)-90);
      if (var4 >= 0 && var5 >= 0 && var4 < var0.field1700[0].length && var5 < var0.field1700[0][0].length) {
         int var6 = var3;
         if (classSG.method10467(var0, var3, var4, var5, 1853916563)) {
            var6 = var3 + 1;
         }

         Tile var7 = var0.field1687.field2056[var3][var4][var5];
         int var8 = -2147483647;
         if (null != var7 && null != var7.field1971) {
            var8 = var0.method3779(var7.field1971, var4, var5, var1, var2, (byte)42);
         }

         if (var8 == 546989114 && var7 != null && null != var7.field1970) {
            var8 = var0.method3787(var7.field1970, var4, var5, var1, var2, var6, 1270870153);
         }

         if (var8 == -735589286) {
            var8 = var0.method3786(var4, var5, var1, var2, var6, -1309490563);
         }

         if (var7 != null) {
            GroundObject var9 = var7.method4119(-1348999018);
            if (var9 != null) {
               int var10 = classCN.method2542(5132338419383099835L * var9.field1813);
               ObjectComposition var11 = classMU.method7771(var10, 2145631976);
               var8 -= var11.field4935 * -1241704068;
            }
         }

         return var8;
      } else {
         return 0;
      }
   }

   public net.runelite.api.IndexedObjectSet worldViews() {
      return new rl20(this);
   }

   @ObfuscatedSignature(descriptor = "(Lsp;IIIIB)V")
   @ObfuscatedName("ay")
   void method3772(WorldEntity var1, int var2, int var3, int var4, int var5, byte var6) {
      try {
         for (AmbientSoundEffect var7 = (AmbientSoundEffect)this.field1693.method7957(); var7 != null; var7 = (AmbientSoundEffect)this.field1693.method7965()) {
            if (var6 == 21) {
               throw new IllegalStateException();
            }

            boolean var8 = this.method3768(var1, var2, var7, -1369702083);
            classUZ var9 = classYY.method13833(this, var7.vmethod194(1723576406), var7.vmethod202(-1994246712), (byte)126);
            classUZ var10 = classYY.method13833(this, var7.vmethod197(-502458478), var7.vmethod205(24841222), (byte)121);
            int var11 = (int)var9.field6425;
            int var12 = (int)var9.field6424;
            int var13 = (int)var10.field6425;
            int var14 = (int)var10.field6424;
            var7.method3653(var3, var4, var11, var12, var13, var14, var5, var8, -317896765);
            var9.method11797(2103636030);
            var10.method11797(1989188357);
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "dz.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILom;I)V")
   @ObfuscatedName("bw")
   void method3767(int var1, int var2, int var3, ObjectComposition var4, int var5) {
      if (null != var4 && var4.method8601(1754865660)) {
         int var6 = ObjectComposition.method8611(var4, var5, -1864693449);
         int var7 = var4.method8619(var5, -379919079);
         int var8 = classKY.method6649(var2 + var6, 320901553);
         int var9 = classKY.method6649(var7 + var3, 774580669);
         var2 = classKY.method6649(var2, 477085862);
         var3 = classKY.method6649(var3, 482330648);
         int var10 = var4.method8606(-1961192357).field6687 * -1727985133;
         int var11 = classKY.method6649(var4.method8606(-1928333442).field6684 * 1535961601, 1176038325);
         int var12 = Math.max(classKY.method6649(var4.method8606(-1737807445).field6686 * -2063946921 - 1, 1405612104), 0);
         if (var4.field4928 != null) {
            ObjectComposition var13 = ObjectComposition.method8585(var4, 1416338340);
            if (null != var13) {
               var10 = var13.method8606(-1238397547).field6687 * -1727985133;
               var11 = classKY.method6649(var13.method8606(-1280541610).field6684 * 916920447, 354204743);
               var12 = Math.max(classKY.method6649(var4.method8606(-1453710146).field6686 * -2063946921 - 1, 1342633251), 0);
            }
         }

         for (AmbientSoundEffect var16 = (AmbientSoundEffect)this.field1693.method7957();
            var16 != null;
            var16 = (AmbientSoundEffect)this.field1693.method7965()
         ) {
            if (AmbientSoundEffect.method3682(var16, -1564730956) == var1
               && var16.vmethod194(-351228449) == var2
               && var16.vmethod202(-1638844544) == var3
               && var16.vmethod197(-93233651) == var8
               && var16.vmethod205(24841222) == var9
               && var16.method3678(-1596606650) == var10
               && var16.method3683(2005517782) == var11
               && var16.method3687(2110735869) == var12) {
               var16.method3675(-435518763);
               break;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldn;)V")
   @ObfuscatedName("bg")
   void method3756(NPC var1) {
      if (NPCComposition.method9042(var1.field1579, (byte)-118)) {
         classCH var2 = new classCH(var1);
         this.field1693.method7946(var2);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Lky;")
   @ObfuscatedName("an")
   classKY method3741(int var1, int var2, int var3) {
      return new classKY(var1, var2 - this.field1706 * -1444178379, var3 - -351145363 * this.field1707);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)I")
   @ObfuscatedName("iy")
   public int method3790(int var1, int var2, int var3, int var4, int var5) {
      int var6 = var3 & 127;
      int var7 = var4 & 127;
      int var8 = this.field1687.field2058[var5][var1][var2] * (128 - var6) + var6 * this.field1687.field2058[var5][var1 + 1][var2] >> 7;
      int var9 = this.field1687.field2058[var5][var1 + 1][var2 + 1] * var6 + this.field1687.field2058[var5][var1][var2 + 1] * (128 - var6) >> 7;
      return var9 * var7 + var8 * (128 - var7) >> 7;
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("bx")
   boolean method3750(int var1, int var2, int var3) {
      return var1 >= 0 && var1 < 4 && method3745(this, var2, var3, (byte)1);
   }

   public int getId() {
      return this.field1697 * 2140889407;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aw")
   public boolean method3735() {
      return 0 == this.field1697 * 2140889407;
   }

   @ObfuscatedSignature(descriptor = "(IIIB)I")
   @ObfuscatedName("ad")
   final int method3794(int var1, int var2, int var3, byte var4) {
      int var5 = (var1 >> 7) + this.field1687.field2146;
      int var6 = (var2 >> 7) + this.field1687.field2146;
      int var7 = this.field1687.field2146 << 1;
      if (var5 >= 0 && var6 >= 0 && var5 < this.field1694 * 1296729483 + var7 && var6 < this.field1690 * -1269171107 + var7) {
         rl10 var8 = this.field1687.field1991[var3][var5][var6];
         int var9 = var3;
         if (var3 < 3 && (this.field1687.field2155[1][var5][var6] & 2) == 2) {
            var9 = var3 + 1;
         }

         int var10 = -2147483647;
         if (var8 != null) {
            SceneTileModel var11 = var8.method10074();
            if (var11 != null) {
               var10 = this.method3803(var11, -1, -1, var1, var2);
            }

            SceneTilePaint var12 = var8.method10080();
            if (var10 == -2147483647 && var12 != null) {
               var10 = this.method3800(var12, var1 >> 7, var2 >> 7, var1, var2, var9);
            }
         }

         if (var10 == -2147483647) {
            var10 = this.method3790(var5, var6, var1, var2, var9);
         }

         if (var8 != null) {
            GroundObject var13 = var8.method10072();
            if (var13 != null) {
               ObjectComposition var14 = ObjectComposition.method8638(var13.getId());
               var10 -= var14.field4935 * 977129717;
            }
         }

         return var10;
      } else {
         return 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lct;")
   @ObfuscatedName("br")
   Player method3753(int var1) {
      return (Player)IndexedObjectSet.method13449(this.field1692, var1);
   }

   @ObfuscatedSignature(descriptor = "(Ldz;B)V")
   @ObfuscatedName("dd")
   public static void method3759(WorldView var0, byte var1) {
      if (var0 == null) {
         var0.method3763(var1);
      } else {
         try {
            for (AmbientSoundEffect var2 = (AmbientSoundEffect)var0.field1693.method7957();
               null != var2;
               var2 = (AmbientSoundEffect)var0.field1693.method7965()
            ) {
               if (var1 == 7) {
                  throw new IllegalStateException();
               }

               var2.method3675(-788480491);
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "dz.ao(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldn;)V")
   @ObfuscatedName("bt")
   void method3757(NPC var1) {
      if (NPCComposition.method9042(var1.field1579, (byte)-117)) {
         classCH var2 = new classCH(var1);
         this.field1693.method7946(var2);
      }
   }

   public int getPlane() {
      return this.field1708 * -483624883;
   }

   @ObfuscatedSignature(descriptor = "(Ldn;B)V")
   @ObfuscatedName("aa")
   void method3758(NPC var1, byte var2) {
      try {
         if (NPCComposition.method9042(var1.field1579, (byte)-109)) {
            if (var2 == 0) {
               return;
            }

            classCH var3 = new classCH(var1);
            this.field1693.method7946(var3);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dz.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ah")
   public boolean method3744(int var1, int var2) {
      return var1 >= 0 && var2 >= 0 && var1 < -1092024236 * this.field1694 && var2 < this.field1690 * -1269171107;
   }

   @ObfuscatedSignature(descriptor = "(Lsp;ILdw;)Z")
   @ObfuscatedName("bi")
   boolean method3770(WorldEntity var1, int var2, AmbientSoundEffect var3) {
      WorldEntity var4 = client.field815.method1594(this.field1697 * 2140889407, -1301152667);
      boolean var5 = var1 == var4;
      if (var5) {
         if (var2 != AmbientSoundEffect.method3682(var3, -250185208)) {
            return false;
         }
      } else {
         if (var1 != null && var1.field6024.method8500(-1821270104) != var2) {
            return false;
         }

         if (null != var4 && var4.field6024.method8500(-1821270104) != AmbientSoundEffect.method3682(var3, -90354477)) {
            return false;
         }

         int var6 = null == var1 ? var2 : var1.vmethod258((byte)-3);
         int var7 = var4 == null ? AmbientSoundEffect.method3682(var3, 246347845) : var4.vmethod258((byte)-42);
         if (var6 != var7) {
            return false;
         }
      }

      switch (-762797223 * var3.field1651.field6688.field3538) {
         case 0:
            return true;
         case 1:
            return var5;
         case 2:
            return var1 == var4 || var4 == null;
         default:
            return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsp;ILdw;)Z")
   @ObfuscatedName("be")
   boolean method3771(WorldEntity var1, int var2, AmbientSoundEffect var3) {
      WorldEntity var4 = client.field815.method1594(this.field1697 * -2041911845, 969897101);
      boolean var5 = var1 == var4;
      if (var5) {
         if (var2 != AmbientSoundEffect.method3682(var3, -1412022133)) {
            return false;
         }
      } else {
         if (var1 != null && var1.field6024.method8500(-1821270104) != var2) {
            return false;
         }

         if (null != var4 && var4.field6024.method8500(-1821270104) != AmbientSoundEffect.method3682(var3, -1883809231)) {
            return false;
         }

         int var6 = null == var1 ? var2 : var1.vmethod258((byte)-117);
         int var7 = var4 == null ? AmbientSoundEffect.method3682(var3, -114625676) : var4.vmethod258((byte)-85);
         if (var6 != var7) {
            return false;
         }
      }

      switch (1014698730 * var3.field1651.field6688.field3538) {
         case 0:
            return true;
         case 1:
            return var5;
         case 2:
            return var1 == var4 || var4 == null;
         default:
            return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()[Lgc;")
   @ObfuscatedName("im")
   public CollisionData[] method3789() {
      return this.field1685;
   }

   public net.runelite.api.Projection getCanvasProjection() {
      if (this.field1687.field2021 instanceof FloatProjection) {
         FloatProjection var1 = (FloatProjection)this.field1687.field2021;
         if (classUU.field6382.equals(var1.field1762)) {
            return null;
         }
      }

      return this.field1687.field2021;
   }

   public byte[][][] getTileSettings() {
      return this.field1700;
   }

   @ObfuscatedSignature(descriptor = "(Lsp;IIII)V")
   @ObfuscatedName("by")
   void method3773(WorldEntity var1, int var2, int var3, int var4, int var5) {
      for (AmbientSoundEffect var6 = (AmbientSoundEffect)this.field1693.method7957(); var6 != null; var6 = (AmbientSoundEffect)this.field1693.method7965()) {
         boolean var7 = this.method3768(var1, var2, var6, -12900460);
         classUZ var8 = classYY.method13833(this, var6.vmethod194(996455154), var6.vmethod202(-1912391592), (byte)103);
         classUZ var9 = classYY.method13833(this, var6.vmethod197(-2045925366), var6.vmethod205(24841222), (byte)101);
         int var10 = (int)var8.field6425;
         int var11 = (int)var8.field6424;
         int var12 = (int)var9.field6425;
         int var13 = (int)var9.field6424;
         var6.method3653(var3, var4, var10, var11, var12, var13, var5, var7, -317896765);
         var8.method11797(2019549796);
         var9.method11797(2064159509);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lez;")
   @ObfuscatedName("sp")
   public Scene method3798() {
      return this.field1687;
   }

   public boolean isInstance() {
      return this.field1710;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   void method3760() {
      for (AmbientSoundEffect var1 = (AmbientSoundEffect)this.field1693.method7957(); null != var1; var1 = (AmbientSoundEffect)this.field1693.method7965()) {
         var1.method3675(1921254323);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bf")
   final int method3776(int var1, int var2, int var3) {
      int var4 = classKY.method6652(var1, (byte)-34);
      int var5 = classKY.method6652(var2, (byte)-83);
      if (var4 >= 0 && var5 >= 0 && var4 < this.field1700[0].length && var5 < this.field1700[0][0].length) {
         int var6 = var3;
         if (classSG.method10467(this, var3, var4, var5, 913356480)) {
            var6 = var3 + 1;
         }

         Tile var7 = this.field1687.field2056[var3][var4][var5];
         int var8 = -2147483647;
         if (null != var7 && null != var7.field1971) {
            var8 = this.method3779(var7.field1971, var4, var5, var1, var2, (byte)-3);
         }

         if (var8 == -2147483647 && var7 != null && null != var7.field1970) {
            var8 = this.method3787(var7.field1970, var4, var5, var1, var2, var6, -116813574);
         }

         if (var8 == -2147483647) {
            var8 = this.method3786(var4, var5, var1, var2, var6, -2106838170);
         }

         if (var7 != null) {
            GroundObject var9 = var7.method4119(-1671345169);
            if (var9 != null) {
               int var10 = classCN.method2542(5132338419383099835L * var9.field1813);
               ObjectComposition var11 = classMU.method7771(var10, 2140568875);
               var8 -= var11.field4935 * 977129717;
            }
         }

         return var8;
      } else {
         return 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bs")
   final int method3777(int var1, int var2, int var3) {
      int var4 = classKY.method6652(var1, (byte)4);
      int var5 = classKY.method6652(var2, (byte)-58);
      if (var4 >= 0 && var5 >= 0 && var4 < this.field1700[0].length && var5 < this.field1700[0][0].length) {
         int var6 = var3;
         if (classSG.method10467(this, var3, var4, var5, 1922926643)) {
            var6 = var3 + 1;
         }

         Tile var7 = this.field1687.field2056[var3][var4][var5];
         int var8 = -2147483647;
         if (null != var7 && null != var7.field1971) {
            var8 = this.method3779(var7.field1971, var4, var5, var1, var2, (byte)-93);
         }

         if (var8 == -2147483647 && var7 != null && null != var7.field1970) {
            var8 = this.method3787(var7.field1970, var4, var5, var1, var2, var6, -40645488);
         }

         if (var8 == -2147483647) {
            var8 = this.method3786(var4, var5, var1, var2, var6, -379258305);
         }

         if (var7 != null) {
            GroundObject var9 = var7.method4119(-1691787276);
            if (var9 != null) {
               int var10 = classCN.method2542(5132338419383099835L * var9.field1813);
               ObjectComposition var11 = classMU.method7771(var10, 1314043398);
               var8 -= var11.field4935 * 977129717;
            }
         }

         return var8;
      } else {
         return 0;
      }
   }

   public boolean contains(WorldPoint var1) {
      int var2 = var1.getX();
      int var3 = var1.getY();
      return var2 >= this.field1706 * -1444178379
         && var2 < this.field1706 * -1444178379 + this.field1694 * 1296729483
         && var3 >= this.field1707 * -351145363
         && var3 < this.field1707 * -351145363 + this.field1690 * -1269171107;
   }

   public int getYellowClickAction() {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         classTN var1 = client.field815.method1626(this.field1697 * 2140889407);
         return var1.method13328();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;)V")
   @ObfuscatedName("mn")
   public static void method3761(WorldView var0) {
      for (AmbientSoundEffect var1 = (AmbientSoundEffect)var0.field1693.method7957(); null != var1; var1 = (AmbientSoundEffect)var0.field1693.method7965()) {
         var1.method3675(-1800330744);
      }
   }

   public net.runelite.api.IndexedObjectSet worldEntities() {
      return this.field1698;
   }

   public int getBaseY() {
      return this.field1707 * -351145363;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ar")
   public boolean method3736() {
      return 0 == this.field1697 * -576365327;
   }

   public net.runelite.api.Projectile createProjectile(
      int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, net.runelite.api.Actor var11, int var12, int var13
   ) {
      return classOE.field4841.method2261(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
   }

   public net.runelite.api.Projection getMainWorldProjection() {
      return classUU.field6382.equals(this.field1687.field2022.field1762) ? null : this.field1687.field2022;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIB)Z")
   @ObfuscatedName("kd")
   public static boolean method3745(WorldView var0, int var1, int var2, byte var3) {
      if (var0 == null) {
         return var0.method3746(var1, var1, var3);
      } else {
         try {
            if (var1 >= 0 && var2 >= 0) {
               if (var3 != 1) {
                  throw new IllegalStateException();
               }

               if (var1 < 1296729483 * var0.field1694) {
                  if (var3 != 1) {
                     throw new IllegalStateException();
                  }

                  if (var2 < var0.field1690 * -1269171107) {
                     if (var3 != 1) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }
            }

            return false;
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "dz.ag(" + ')');
         }
      }
   }

   public net.runelite.api.Deque getGraphicsObjects() {
      return this.field1689;
   }

   @ObfuscatedSignature(descriptor = "(Lfn;IIII)I")
   @ObfuscatedName("bd")
   int method3780(SceneTileModel var1, int var2, int var3, int var4, int var5) {
      for (int var6 = 0; var6 < var1.field2328.length; var6++) {
         int var7 = var1.field2320[var1.field2328[var6]];
         int var8 = var1.field2325[var1.field2328[var6]];
         int var9 = var1.field2320[var1.field2329[var6]];
         int var10 = var1.field2325[var1.field2329[var6]];
         int var11 = var1.field2320[var1.field2327[var6]];
         int var12 = var1.field2325[var1.field2327[var6]];
         if (Scene.method4174(var4, var5, var8, var10, var12, var7, var9, var11)) {
            int var13 = var1.field2321[var1.field2328[var6]];
            int var14 = var1.field2321[var1.field2329[var6]];
            int var15 = var1.field2321[var1.field2327[var6]];
            float var16 = classVI.method12032(var4, var5, var7, var9, var11, var8, var10, var12, var13, var14, var15, -2078008879);
            return (int)var16;
         }
      }

      return -2147483647;
   }

   @ObfuscatedSignature(descriptor = "(Lfj;IIIII)I")
   @ObfuscatedName("cr")
   int method3784(SceneTilePaint var1, int var2, int var3, int var4, int var5, int var6) {
      if (var1.field2281) {
         return this.field1686[var6][var2][var3];
      } else {
         TextureProvider.method3821(0, this.field1686, var6, var2, var3, field1702[0], field1703[0], field1704[0], 1181281029);
         TextureProvider.method3821(1, this.field1686, var6, var2, var3, field1702[1], field1703[1], field1704[1], 1181281029);

         for (int var7 = 0; var7 < 2; var7++) {
            int var8 = field1702[var7][0];
            int var9 = field1704[var7][0];
            int var10 = field1702[var7][1];
            int var11 = field1704[var7][1];
            int var12 = field1702[var7][2];
            int var13 = field1704[var7][2];
            if (Scene.method4174(var4, var5, var9, var11, var13, var8, var10, var12)) {
               int var14 = field1703[var7][0];
               int var15 = field1703[var7][1];
               int var16 = field1703[var7][2];
               float var17 = classVI.method12032(var4, var5, var8, var10, var12, var9, var11, var13, var14, var15, var16, 253137403);
               return (int)var17;
            }
         }

         return -2147483647;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lpc;")
   @ObfuscatedName("az")
   public static classPC method3733(int var0, int var1) {
      try {
         classPC var2 = (classPC)NodeCache.method6460(classPC.field5217, var0);
         if (null != var2) {
            if (var1 >= 1074259988) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classPC.field5220.method11942(75, var0, -1793184063);
            var2 = new classPC(new classXY(var3), var0);
            classPC.field5217.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dz.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIISISIII)V")
   @ObfuscatedName("dw")
   static void method3788(WorldView var0, int var1, int var2, int var3, int var4, int var5, short var6, int var7, short var8, int var9, int var10, int var11) {
      try {
         Deque2 var12 = var0.field1699[var1][var2][var3];
         if (var12 != null) {
            if (var11 == -1151520074) {
               throw new IllegalStateException();
            }

            for (TileItem var13 = (TileItem)var12.method7957(); var13 != null; var13 = (TileItem)var12.method7965()) {
               if (var11 == -1151520074) {
                  return;
               }

               if (-1370204663 * var13.field1549 == var4) {
                  if (var11 == -1151520074) {
                     return;
                  }

                  if (var13.field1548 * 1413659045 == var5) {
                     ColorTextureOverride var14 = TileItem.method3410(var13, (short)-15479);
                     if (null == var14) {
                        if (var11 == -1151520074) {
                           throw new IllegalStateException();
                        }

                        var14 = new ColorTextureOverride(var4);
                     }

                     if (var14.method8937(-1606068063)) {
                        if (var11 == -1151520074) {
                           throw new IllegalStateException();
                        }

                        if (var7 > -1) {
                           if (var11 == -1151520074) {
                              return;
                           }

                           if (var7 < var14.field5259.length) {
                              if (var11 == -1151520074) {
                                 throw new IllegalStateException();
                              }

                              var14.field5259[var7] = var6;
                           }
                        }
                     }

                     if (var14.method8938((byte)-120)) {
                        if (var11 == -1151520074) {
                           throw new IllegalStateException();
                        }

                        if (var9 > -1) {
                           if (var11 == -1151520074) {
                              throw new IllegalStateException();
                           }

                           if (var9 < var14.field5258.length) {
                              if (var11 == -1151520074) {
                                 return;
                              }

                              var14.field5258[var9] = var8;
                           }
                        }
                     }

                     if (var10 > -1) {
                        if (var11 == -1151520074) {
                           throw new IllegalStateException();
                        }

                        var14.field5261 = 1795846821 * var10;
                        var14.field5266 = null;
                     }

                     var13.method3413(var14, 502280405);
                     break;
                  }
               }
            }

            classUA.method11300(var1, var2, var3, -2030172471);
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "dz.dw(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;III)Z")
   @ObfuscatedName("zj")
   public static boolean method3751(WorldView var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method3798();
      }

      return var1 >= 0 && var1 < 4 && method3745(var0, var2, var3, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "(I)Lct;")
   @ObfuscatedName("vt")
   public Player method3804(int var1) {
      return this.method3752(var1, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "(IIII)Lky;")
   @ObfuscatedName("ae")
   classKY method3742(int var1, int var2, int var3, int var4) {
      try {
         return new classKY(var1, var2 - this.field1706 * -1444178379, var3 - -351145363 * this.field1706);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dz.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)Z")
   @ObfuscatedName("ag")
   public boolean method3746(int var1, int var2, byte var3) {
      try {
         if (var1 >= 0 && var2 >= 0) {
            if (var3 != 1) {
               throw new IllegalStateException();
            }

            if (var1 < 1296729483 * this.field1694) {
               if (var3 != 1) {
                  throw new IllegalStateException();
               }

               if (var2 < this.field1690 * -1269171107) {
                  if (var3 != 1) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dz.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;)V")
   @ObfuscatedName("ma")
   public static void method3762(WorldView var0) {
      for (AmbientSoundEffect var1 = (AmbientSoundEffect)var0.field1693.method7957(); null != var1; var1 = (AmbientSoundEffect)var0.field1693.method7965()) {
         var1.method3675(224255861);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hg")
   public void method3797(int var1) {
      this.field1709 = this.field1708 * -483624883;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ao")
   void method3763(byte var1) {
      try {
         for (AmbientSoundEffect var2 = (AmbientSoundEffect)this.field1693.method7965(); null != var2; var2 = (AmbientSoundEffect)this.field1693.method7978()) {
            if (var1 == 7) {
               throw new IllegalStateException();
            }

            var2.method3675(-788480491);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dz.ao(" + ')');
      }
   }

   public int[][][] getTileHeights() {
      return this.field1686;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ak")
   public boolean method3796() {
      return this.field1694 * 2140889407 == 0;
   }

   @ObfuscatedSignature(descriptor = "(I)Lct;")
   @ObfuscatedName("bm")
   Player method3754(int var1) {
      return (Player)IndexedObjectSet.method13449(this.field1696, var1);
   }
}
