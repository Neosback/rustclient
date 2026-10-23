import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.WorldEntityDespawned;
import net.runelite.api.events.WorldViewUnloaded;
import net.runelite.api.hooks.Callbacks;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@ObfuscatedName("cl")
public class classCL implements Iterable {
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   static final int field783 = 16;
   @ObfuscatedName("af")
   final HashMap field780;
   @ObfuscatedSignature(descriptor = "Ltn;")
   @ObfuscatedName("ab")
   classTN field786;
   @ObfuscatedSignature(descriptor = "Ltw;")
   @ObfuscatedName("ag")
   classTW field787;
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("az")
   final IterableHashTable field782 = new IterableHashTable(16);
   @ObfuscatedSignature(descriptor = "Ldz;")
   @ObfuscatedName("as")
   WorldView field785;
   @ObfuscatedName("ae")
   final HashMap field781;
   @ObfuscatedName("ax")
   int field779;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   public static final int field784 = 22;

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;Ljava/lang/String;I)[F")
   @ObfuscatedName("az")
   static float[] method1574(JSONObject var0, String var1, int var2) throws JSONException {
      try {
         float[] var3 = new float[4];

         try {
            JSONArray var4 = var0.getJSONArray(var1);
            var3[0] = (float)var4.optDouble(0, 0.0);
            var3[1] = (float)var4.optDouble(1, 0.0);
            var3[2] = (float)var4.optDouble(2, 1.0);
            var3[3] = (float)var4.optDouble(3, 1.0);
         } catch (JSONException var5) {
            var3[0] = 0.0F;
            var3[1] = 0.0F;
            var3[2] = 1.0F;
            var3[3] = 1.0F;
         }

         return var3;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cl.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILex;I)Ldz;")
   @ObfuscatedName("af")
   WorldView method1575(int var1, int var2, int var3, int var4, classEX var5, int var6) {
      try {
         WorldView var7 = new WorldView(var1, var2, var3, var4, var5);
         IterableHashTable.method13620(this.field782, var7, var1);
         this.field779 += -302879415;
         return var7;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "cl.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Ldz;")
   @ObfuscatedName("an")
   WorldView method1589(int var1, int var2) {
      for (WorldView var4 : this) {
         if (!var4.method3734(-2087374717)) {
            int var5 = -1444178379 * var4.field1706;
            int var6 = -351145363 * var4.field1707;
            int var7 = -2132630835 * var4.field1694 + var5;
            int var8 = var4.field1690 * -1816400270 + var6;
            if (var1 >= var5 && var2 >= var6 && var1 < var7 && var2 < var8) {
               return var4;
            }
         }
      }

      return this.field785;
   }

   @ObfuscatedSignature(descriptor = "(ILcl;)Ldz;")
   @ObfuscatedName("ko")
   public static WorldView method1623(int var0, classCL var1) {
      return classQB.method9481(var0, var1, -780819548);
   }

   @ObfuscatedSignature(descriptor = "(IS)Ldz;")
   @ObfuscatedName("ag")
   public WorldView method1584(int var1, short var2) {
      try {
         return (WorldView)this.field782.method13637(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cl.ag(" + ')');
      }
   }

   @Override
   public Iterator iterator() {
      try {
         return this.field782.iterator();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "cl.iterator(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILcl;)Ldz;")
   @ObfuscatedName("we")
   public static WorldView method1624(int var0, classCL var1) {
      return classHB.method6020(var0, var1, -849117969);
   }

   @ObfuscatedSignature(descriptor = "(III)Ldz;")
   @ObfuscatedName("ap")
   WorldView method1590(int var1, int var2, int var3) {
      try {
         Iterator var4 = this.iterator();

         while (var4.hasNext()) {
            if (var3 >= 130197942) {
               throw new IllegalStateException();
            }

            WorldView var5 = (WorldView)var4.next();
            if (var5.method3734(-2087374717)) {
               if (var3 >= 130197942) {
                  throw new IllegalStateException();
               }
            } else {
               int var6 = -1444178379 * var5.field1706;
               int var7 = -351145363 * var5.field1707;
               int var8 = 1296729483 * var5.field1694 + var6;
               int var9 = var5.field1690 * -1269171107 + var7;
               if (var1 >= var6) {
                  if (var3 >= 130197942) {
                     throw new IllegalStateException();
                  }

                  if (var2 >= var7) {
                     if (var3 >= 130197942) {
                        throw new IllegalStateException();
                     }

                     if (var1 < var8) {
                        if (var3 >= 130197942) {
                           throw new IllegalStateException();
                        }

                        if (var2 < var9) {
                           if (var3 >= 130197942) {
                              throw new IllegalStateException();
                           }

                           return var5;
                        }
                     }
                  }
               }
            }
         }

         return this.field785;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "cl.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lsp;")
   @ObfuscatedName("bc")
   WorldEntity method1593(int var1) {
      return (WorldEntity)this.method1588(-708227910).field1698.method13637(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ldz;)V")
   @ObfuscatedName("yx")
   public void method1620(WorldView var1) {
      if (var1 != null) {
         if (var1.field1711 != null) {
            classOE.field4841.getCallbacks().post(new WorldViewUnloaded(var1));
         }

         if (client.field1117 != null) {
            client.field1117.despawnWorldView(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aj")
   void method1605(int var1, int var2) {
      try {
         this.field780.remove(var1);
         this.field781.remove(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cl.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILex;)Ldz;")
   @ObfuscatedName("cw")
   public WorldView method1625(int var1, int var2, int var3, int var4, classEX var5) {
      return this.method1575(var1, var2, var3, var4, var5, -1393038213);
   }

   @ObfuscatedSignature(descriptor = "(II)Ltn;")
   @ObfuscatedName("ay")
   classTN method1609(int var1, int var2) {
      try {
         classTN var3 = (classTN)this.field780.get(var1);
         classTN var10000;
         if (var3 != null) {
            if (var2 != 206128317) {
               throw new IllegalStateException();
            }

            var10000 = var3;
         } else {
            var10000 = this.field786;
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cl.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lsp;")
   @ObfuscatedName("ac")
   WorldEntity method1594(int var1, int var2) {
      try {
         return (WorldEntity)this.method1588(-1107644636).field1698.method13637(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cl.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bw")
   void method1615(int var1) {
      try {
         Iterator var2 = this.field782.iterator();

         while (var2.hasNext()) {
            if (var1 == -1755329566) {
               throw new IllegalStateException();
            }

            WorldView var3 = (WorldView)var2.next();
            method1582(this, var3, (byte)1);
         }

         this.field782.method13623();
         this.field779 = 0;
         this.field780.clear();
         this.field781.clear();
         this.field786 = classTN.field6170;
         this.field787 = classTW.field6241;
         if (null != this.field785) {
            this.field785.method3737(1840403635);
            IterableHashTable.method13620(this.field782, this.field785, 0L);
            this.field779 = -302879415;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cl.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Ldz;")
   @ObfuscatedName("ax")
   WorldView method1572(int var1, int var2, int var3) {
      this.field785 = this.method1575(0, var1, var2, var3, classEX.field1975, -1393038213);
      return this.field785;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ba")
   void method1576(int var1) {
      WorldView var2 = (WorldView)this.field782.method13637(var1);
      method1582(this, var2, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "(II)Ldz;")
   @ObfuscatedName("am")
   WorldView method1591(int var1, int var2) {
      for (WorldView var4 : this) {
         if (!var4.method3734(-2087374717)) {
            int var5 = -1444178379 * var4.field1706;
            int var6 = -351145363 * var4.field1707;
            int var7 = 1296729483 * var4.field1694 + var5;
            int var8 = var4.field1690 * -1269171107 + var6;
            if (var1 >= var5 && var2 >= var6 && var1 < var7 && var2 < var8) {
               return var4;
            }
         }
      }

      return this.field785;
   }

   @ObfuscatedSignature(descriptor = "(I)Ltn;")
   @ObfuscatedName("lo")
   public classTN method1626(int var1) {
      return this.method1609(var1, 206128317);
   }

   @ObfuscatedSignature(descriptor = "(Ldz;)V")
   @ObfuscatedName("ar")
   void method1580(WorldView var1) {
      if (var1 != null) {
         this.field780.remove(var1.field1697 * 2140889407);
         this.field781.remove(2140889407 * var1.field1697);
         WorldView.method3759(var1, (byte)-22);
         var1.vmethod398();
         this.field779 -= -302879415;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("eo")
   static final void method1619(int var0, int var1, int var2) {
      try {
         if (-1927903443 * client.field975.field6795 < 2) {
            if (var2 != 1459476222) {
               throw new IllegalStateException();
            }

            if (client.field946 * -512792823 == 0) {
               if (var2 != 1459476222) {
                  return;
               }

               if (!client.field980) {
                  return;
               }
            }
         }

         if (!client.field859) {
            if (var2 != 1459476222) {
               throw new IllegalStateException();
            }
         } else {
            String var5;
            label79: {
               int var3 = -1927903443 * client.field975.field6795 - 1;
               if (client.field946 * -512792823 == 1) {
                  if (var2 != 1459476222) {
                     throw new IllegalStateException();
                  }

                  if (-1927903443 * client.field975.field6795 < 2) {
                     if (var2 != 1459476222) {
                        throw new IllegalStateException();
                     }

                     var5 = classKK.field3772 + classKK.field3783 + client.field805 + " " + classDO.field1588;
                     break label79;
                  }
               }

               if (client.field980) {
                  if (var2 != 1459476222) {
                     throw new IllegalStateException();
                  }

                  if (-1927903443 * client.field975.field6795 < 2) {
                     if (var2 != 1459476222) {
                        return;
                     }

                     var5 = client.field925 + classKK.field3783 + client.field984 + " " + classDO.field1588;
                     break label79;
                  }
               }

               var5 = client.field975.method12710(var3, -115655011);
            }

            if (client.field975.field6795 * -1927903443 > 2) {
               if (var2 != 1459476222) {
                  throw new IllegalStateException();
               }

               var5 = var5 + classQY.method9885(16777215, (byte)104) + " " + '/' + " " + (-1927903443 * client.field975.field6795 - 2) + classKK.field3776;
            }

            classBB.field341.method92(var5, 4 + var0, 15 + var1, 16777215, 0, 1612595797 * client.field856 / 1000);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cl.eo(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ldz;")
   @ObfuscatedName("ak")
   public WorldView method1585(int var1) {
      return (WorldView)this.field782.method13637(var1);
   }

   @ObfuscatedSignature(descriptor = "()Ldz;")
   @ObfuscatedName("av")
   public WorldView method1587() {
      return this.field785;
   }

   @ObfuscatedSignature(descriptor = "(II)Ldz;")
   @ObfuscatedName("at")
   WorldView method1592(int var1, int var2) {
      for (WorldView var4 : this) {
         if (!var4.method3734(-2087374717)) {
            int var5 = -1444178379 * var4.field1706;
            int var6 = -351145363 * var4.field1707;
            int var7 = 1296729483 * var4.field1694 + var5;
            int var8 = var4.field1690 * -1269171107 + var6;
            if (var1 >= var5 && var2 >= var6 && var1 < var7 && var2 < var8) {
               return var4;
            }
         }
      }

      return this.field785;
   }

   @ObfuscatedSignature(descriptor = "(II)Ltw;")
   @ObfuscatedName("aq")
   classTW method1611(int var1, int var2) {
      try {
         classTW var3 = (classTW)this.field781.get(var1);
         classTW var10000;
         if (var3 != null) {
            if (var2 >= 1058766086) {
               throw new IllegalStateException();
            }

            var10000 = var3;
         } else {
            var10000 = this.field787;
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cl.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcl;B)I")
   @ObfuscatedName("gc")
   public static int method1597(classCL var0, byte var1) {
      if (var0 == null) {
         return var0.method1599(var1);
      } else {
         try {
            return var0.field779 * -1688595207;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "cl.aa(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILtn;Ltw;I)V")
   @ObfuscatedName("al")
   void method1603(int var1, classTN var2, classTW var3, int var4) {
      try {
         this.field780.put(var1, var2);
         this.field781.put(var1, var3);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "cl.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lsp;")
   @ObfuscatedName("ah")
   WorldEntity method1595(int var1) {
      return (WorldEntity)this.method1588(-1922624912).field1698.method13637(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)Lsp;")
   @ObfuscatedName("bx")
   WorldEntity method1596(int var1) {
      return (WorldEntity)this.method1588(-1954751538).field1698.method13637(var1);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("be")
   public Iterator method1613() {
      return this.field782.iterator();
   }

   classCL() {
      this.field780 = new HashMap(16);
      this.field781 = new HashMap(16);
      this.field779 = 0;
      this.method1615(-1780765646);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bm")
   int method1598() {
      return this.field779 * -1688595207;
   }

   @ObfuscatedSignature(descriptor = "(Ltn;Ltw;)V")
   @ObfuscatedName("bg")
   void method1600(classTN var1, classTW var2) {
      this.field786 = var1;
      this.field787 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Ltn;Ltw;)V")
   @ObfuscatedName("bj")
   void method1601(classTN var1, classTW var2) {
      this.field786 = var1;
      this.field787 = var2;
   }

   @ObfuscatedSignature(descriptor = "(I)Ltw;")
   @ObfuscatedName("bi")
   classTW method1612(int var1) {
      classTW var2 = (classTW)this.field781.get(var1);
      return var2 != null ? var2 : this.field787;
   }

   @ObfuscatedSignature(descriptor = "(ILtn;Ltw;)V")
   @ObfuscatedName("bo")
   void method1604(int var1, classTN var2, classTW var3) {
      this.field780.put(var1, var2);
      this.field781.put(var1, var3);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ad")
   void method1606(int var1) {
      this.field780.remove(var1);
      this.field781.remove(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)Ldz;")
   @ObfuscatedName("hx")
   public WorldView method1627(int var1) {
      return this.method1584(var1, (short)2276);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bu")
   void method1607(int var1) {
      this.field780.remove(var1);
      this.field781.remove(var1);
   }

   @ObfuscatedSignature(descriptor = "(IIII)Ldz;")
   @ObfuscatedName("az")
   WorldView method1573(int var1, int var2, int var3, int var4) {
      try {
         this.field785 = this.method1575(0, var1, var2, var3, classEX.field1975, -1393038213);
         return this.field785;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "cl.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcl;Ldz;)V")
   @ObfuscatedName("tc")
   public static void method1581(classCL var0, WorldView var1) {
      if (var0 == null) {
         var0.method1614();
      }

      if (var1 != null) {
         var0.field780.remove(var1.field1697 * -1821332246);
         var0.field781.remove(669987788 * var1.field1697);
         WorldView.method3759(var1, (byte)-51);
         var1.vmethod398();
         var0.field779 -= -302879415;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ltn;")
   @ObfuscatedName("bb")
   classTN method1610(int var1) {
      classTN var2 = (classTN)this.field780.get(var1);
      return var2 != null ? var2 : this.field786;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   void method1616() {
      for (WorldView var2 : this.field782) {
         method1582(this, var2, (byte)1);
      }

      this.field782.method13623();
      this.field779 = 0;
      this.field780.clear();
      this.field781.clear();
      this.field786 = classTN.field6170;
      this.field787 = classTW.field6241;
      if (null != this.field785) {
         this.field785.method3737(-674612632);
         IterableHashTable.method13620(this.field782, this.field785, 0L);
         this.field779 = -302879415;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcl;IB)V")
   @ObfuscatedName("hv")
   public static void method1577(classCL var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method1578(var1, var2);
      } else {
         var0.method1621(var1);

         try {
            WorldView var3 = (WorldView)var0.field782.method13637(var1);
            method1582(var0, var3, (byte)1);
            var0.method1622(var1);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "cl.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ldz;")
   @ObfuscatedName("aw")
   public WorldView method1586(int var1) {
      return (WorldView)this.field782.method13637(var1);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("by")
   public Iterator method1614() {
      return this.field782.iterator();
   }

   @ObfuscatedSignature(descriptor = "(Ltn;Ltw;I)V")
   @ObfuscatedName("ao")
   void method1602(classTN var1, classTW var2, int var3) {
      try {
         this.field786 = var1;
         this.field787 = var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cl.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)I")
   @ObfuscatedName("av")
   static int method1617(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         int var7 = classIZ.method6382(var0, var1, var2, var3, var4, var5, -1006283816);
         return Math.max(var7 - 64, 0);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "cl.av(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("au")
   void method1608(int var1) {
      this.field780.remove(var1);
      this.field781.remove(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)Ldz;")
   @ObfuscatedName("as")
   public WorldView method1588(int var1) {
      try {
         return this.field785;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cl.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("dn")
   static void method1618(int var0, int var1, int var2, int var3, int var4, int var5) {
      try {
         classJB.method6387(classCQ.field1218, var0, var1, var2, var3, var4, 887760572);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cl.dn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Ldz;")
   @ObfuscatedName("os")
   public WorldView method1628(int var1, int var2) {
      return this.method1590(var1, var2, -1987881682);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ch")
   public void method1622(int var1) {
      WorldEntity var2 = (WorldEntity)classIS.field3051.field1698.method13642(var1);
      if (var2 != null) {
         client.field1028.debug("WorldEntity despawn: {}", var2.field6007 * -1807801405);
         classOE.field4841.getCallbacks().post(new WorldEntityDespawned(var2));
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("py")
   public void method1621(int var1) {
      WorldView var2 = this.method1627(var1);
      if (var2 != null) {
         Callbacks var3 = classOE.field4841.getCallbacks();

         for (NPC var5 : var2.npcs()) {
            var3.post(new NpcDespawned(var5));
         }
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIB)V")
   @ObfuscatedName("az")
   public static void method1579(String var0, String var1, String var2, int var3, int var4, byte var5) throws IOException {
      try {
         if (var1 != null) {
            if (var5 != 1) {
               return;
            }

            if (!var1.isEmpty()) {
               var0 = var0 + "-" + var1;
            }
         }

         classUP.field6361 = var4 * -552347913;
         MidiRequest.field4635 = var3 * 381969765;

         try {
            Script.field499 = System.getProperty("os.name");
         } catch (Exception var41) {
            Script.field499 = "Unknown";
         }

         classZW.field7282 = Script.field499.toLowerCase();
         ClanChannel.field2952 = null;

         try {
            ClanChannel.field2952 = System.getProperty("jagex.userhome");
         } catch (Exception var40) {
         }

         if (ClanChannel.field2952 == null) {
            if (var5 != 1) {
               throw new IllegalStateException();
            }

            try {
               ClanChannel.field2952 = System.getProperty("user.home");
            } catch (Exception var39) {
            }
         }

         if (null != ClanChannel.field2952) {
            ClanChannel.field2952 = ClanChannel.field2952 + "/";
         }

         try {
            if (classZW.field7282.startsWith("win")) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               if (ClanChannel.field2952 == null) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  ClanChannel.field2952 = client.method2380("USERPROFILE");
               }
            } else if (null == ClanChannel.field2952) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               ClanChannel.field2952 = client.method2380("HOME");
            }

            if (null != ClanChannel.field2952) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               ClanChannel.field2952 = ClanChannel.field2952 + "/";
            }
         } catch (Exception var38) {
         }

         if (null == ClanChannel.field2952) {
            ClanChannel.field2952 = "~/";
         }

         classWQ.field6766 = new String[]{"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", ClanChannel.field2952, "/tmp/", ""};
         classCV.field1303 = new String[]{".jagex_cache_" + MidiRequest.field4635 * 1602897005, ".file_store_" + MidiRequest.field4635 * 1602897005};

         label695:
         for (int var6 = 0; var6 < 4; var6++) {
            if (var5 != 1) {
               throw new IllegalStateException();
            }

            String var8 = 0 == var6 ? "" : "" + var6;
            classDB.field1377 = new File(ClanChannel.field2952, "jagex_cl_" + var0 + "_" + var2 + var8 + ".dat");
            String var9 = null;
            String var10 = null;
            boolean var11 = false;
            if (classDB.field1377.exists()) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               classAAR var12 = null;
               boolean var33 = false /* VF: Semaphore variable */;

               label690: {
                  label689: {
                     try {
                        var33 = true;
                        var12 = new classAAR(classDB.field1377, "rw", 10000L);
                        classXY var13 = new classXY((int)var12.method251(-153393736));

                        while (702114061 * var13.field6953 < var13.field6952.length) {
                           if (var5 != 1) {
                              throw new IllegalStateException();
                           }

                           int var14 = var12.method255(
                              var13.field6952, var13.field6953 * 702114061, var13.field6952.length - 702114061 * var13.field6953, -1397369950
                           );
                           if (var14 == -1) {
                              throw new IOException();
                           }

                           var13.field6953 += -1095856699 * var14;
                        }

                        var13.field6953 = 0;
                        int var67 = var13.method13095(-346779531);
                        if (var67 >= 1) {
                           if (var5 != 1) {
                              throw new IllegalStateException();
                           }

                           if (var67 <= 3) {
                              int var15 = 0;
                              if (var67 > 1) {
                                 if (var5 != 1) {
                                    throw new IllegalStateException();
                                 }

                                 var15 = var13.method13095(-346779531);
                              }

                              if (var67 <= 2) {
                                 var9 = var13.method13134(-1671998117);
                                 if (1 == var15) {
                                    if (var5 != 1) {
                                       throw new IllegalStateException();
                                    }

                                    var10 = var13.method13134(941660467);
                                    var33 = false;
                                 } else {
                                    var33 = false;
                                 }
                              } else {
                                 var9 = classXY.method13137(var13, 1921404263);
                                 if (var15 == 1) {
                                    if (var5 != 1) {
                                       throw new IllegalStateException();
                                    }

                                    var10 = classXY.method13137(var13, -754422716);
                                    var33 = false;
                                 } else {
                                    var33 = false;
                                 }
                              }
                              break label689;
                           }
                        }

                        throw new IOException("" + var67);
                     } catch (Exception var44) {
                        var44.printStackTrace();
                        var33 = false;
                     } finally {
                        if (var33) {
                           try {
                              if (null != var12) {
                                 var12.method242((byte)1);
                              }
                           } catch (IOException var34) {
                           }
                        }
                     }

                     try {
                        if (null != var12) {
                           var12.method242((byte)1);
                        }
                     } catch (IOException var36) {
                     }
                     break label690;
                  }

                  try {
                     if (null != var12) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        var12.method242((byte)1);
                     }
                  } catch (IOException var37) {
                  }
               }

               if (null != var9) {
                  File var62 = new File(var9);
                  if (!var62.exists()) {
                     var9 = null;
                  }
               }

               if (null != var9) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  File var63 = new File(var9, "test.dat");
                  if (!classBB.method788(var63, true, -872624671)) {
                     var9 = null;
                  }
               }
            }

            if (var9 == null) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               if (0 == var6) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  label657:
                  for (int var59 = 0; var59 < classCV.field1303.length; var59++) {
                     for (int var64 = 0; var64 < classWQ.field6766.length; var64++) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        File var68 = new File(classWQ.field6766[var64] + classCV.field1303[var59] + File.separatorChar + var0 + File.separatorChar);
                        if (var68.exists()) {
                           if (var5 != 1) {
                              throw new IllegalStateException();
                           }

                           if (classBB.method788(new File(var68, "test.dat"), true, -1932202852)) {
                              var9 = var68.toString();
                              var11 = true;
                              break label657;
                           }
                        }
                     }
                  }
               }
            }

            if (var9 == null) {
               if (var5 != 1) {
                  return;
               }

               var9 = ClanChannel.field2952
                  + File.separatorChar
                  + "jagexcache"
                  + var8
                  + File.separatorChar
                  + var0
                  + File.separatorChar
                  + var2
                  + File.separatorChar;
               var11 = true;
            }

            if (var10 != null) {
               if (var5 != 1) {
                  return;
               }

               File var60 = new File(var10);
               File var65 = new File(var9);

               try {
                  File[] var69 = var60.listFiles();
                  File[] var71 = var69;

                  for (int var16 = 0; var16 < var71.length; var16++) {
                     if (var5 != 1) {
                        return;
                     }

                     File var17 = var71[var16];
                     File var18 = new File(var65, var17.getName());
                     boolean var19 = var17.renameTo(var18);
                     if (!var19) {
                        throw new IOException();
                     }
                  }
               } catch (Exception var43) {
                  var43.printStackTrace();
               }

               var11 = true;
            }

            if (var11) {
               if (var5 != 1) {
                  return;
               }

               File var61 = new File(var9);
               Object var66 = null;

               try {
                  classAAR var70 = new classAAR(classDB.field1377, "rw", 10000L);
                  classXY var72 = new classXY(500);
                  classXY.method13029(var72, 3, (byte)-55);
                  byte var10001;
                  if (null != var66) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     var10001 = 1;
                  } else {
                     var10001 = 0;
                  }

                  classXY.method13029(var72, var10001, (byte)-74);
                  classXY.method13064(var72, var61.getPath(), -910745300);
                  if (var66 != null) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     classXY.method13064(var72, var66.getPath(), -910745300);
                  }

                  var70.method238(var72.field6952, 0, 702114061 * var72.field6953, -1522899189);
                  var70.method242((byte)1);
               } catch (IOException var35) {
                  var35.printStackTrace();
               }
            }

            File var7 = new File(var9);
            classSL.field5973 = var7;
            if (!classSL.field5973.exists()) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               classSL.field5973.mkdirs();
            }

            File[] var50 = classSL.field5973.listFiles();
            if (var50 != null) {
               File[] var53 = var50;

               for (int var56 = 0; var56 < var53.length; var56++) {
                  if (var5 != 1) {
                     return;
                  }

                  File var58 = var53[var56];
                  if (!classBB.method788(var58, false, 1172032847)) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }
                     continue label695;
                  }
               }
            }
            break;
         }

         File var47 = classSL.field5973;
         classMT.field4584 = var47;
         if (classMT.field4584.exists()) {
            classMT.field4578 = true;

            try {
               File var48 = new File(ClanChannel.field2952, "random.dat");
               if (var48.exists()) {
                  classME.field4488 = new classAAC(new classAAR(var48, "rw", 25L), 24, 0);
               } else {
                  label598:
                  for (int var51 = 0; var51 < classCV.field1303.length; var51++) {
                     for (int var54 = 0; var54 < classWQ.field6766.length; var54++) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        File var57 = new File(classWQ.field6766[var54] + classCV.field1303[var51] + File.separatorChar + "random.dat");
                        if (var57.exists()) {
                           if (var5 != 1) {
                              return;
                           }

                           classME.field4488 = new classAAC(new classAAR(var57, "rw", 25L), 24, 0);
                           break label598;
                        }
                     }
                  }
               }

               if (classME.field4488 == null) {
                  if (var5 != 1) {
                     return;
                  }

                  RandomAccessFile var52 = new RandomAccessFile(var48, "rw");
                  int var55 = var52.read();
                  var52.seek(0L);
                  var52.write(var55);
                  var52.seek(0L);
                  var52.close();
                  classME.field4488 = new classAAC(new classAAR(var48, "rw", 25L), 24, 0);
               }
            } catch (IOException var42) {
            }

            classME.field4486 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.dat2", -2138930382), "rw", 1048576000L), 5200, 0);
            classME.field4487 = new classAAC(new classAAR(classBJ.method1118("main_file_cache.idx255", -2139055959), "rw", 1048576L), 6000, 0);
            GraphicsObject.field477 = new classAAC[classUP.field6361 * -646722873];

            for (int var49 = 0; var49 < -646722873 * classUP.field6361; var49++) {
               if (var5 != 1) {
                  return;
               }

               GraphicsObject.field477[var49] = new classAAC(
                  new classAAR(classBJ.method1118("main_file_cache.idx" + var49, -374206652), "rw", 1048576L), 6000, 0
               );
            }
         } else if (var5 == 1) {
            throw new RuntimeException("");
         }
      } catch (RuntimeException var46) {
         throw classEG.method3899(var46, "cl.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ae")
   void method1578(int var1, byte var2) {
      this.method1622(var1);

      try {
         WorldView var3 = (WorldView)this.field782.method13617(var1);
         method1582(this, var3, (byte)1);
         this.method1622(var1);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cl.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcl;Ldz;B)V")
   @ObfuscatedName("gf")
   public static void method1582(classCL var0, WorldView var1, byte var2) {
      if (var0 == null) {
         var0.method1583(var1, var2);
      } else {
         var0.method1620(var1);

         try {
            if (var1 != null) {
               if (var2 != 1) {
                  throw new IllegalStateException();
               }

               var0.field780.remove(var1.field1697 * 2140889407);
               var0.field781.remove(2140889407 * var1.field1697);
               WorldView.method3759(var1, (byte)-59);
               var1.vmethod398();
               var0.field779 -= -302879415;
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "cl.ab(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;B)V")
   @ObfuscatedName("ab")
   void method1583(WorldView var1, byte var2) {
      this.method1620(var1);

      try {
         if (var1 != null) {
            if (var2 != 1) {
               throw new IllegalStateException();
            }

            this.field781.remove(var1.field1694 * 2140889407);
            this.field780.remove(2140889407 * var1.field1709);
            WorldView.method3759(var1, (byte)-59);
            var1.vmethod398();
            this.field779 -= -302879415;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cl.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aa")
   int method1599(byte var1) {
      try {
         return this.field779 * -1688595207;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cl.aa(" + 41);
      }
   }
}
