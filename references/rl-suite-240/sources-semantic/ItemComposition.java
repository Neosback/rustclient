import java.util.Arrays;
import net.runelite.api.events.PostItemComposition;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("op")
public class ItemComposition extends classVJ implements net.runelite.api.ItemComposition, rl9 {
   @ObfuscatedName("wj")
   public String field5075;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   public static final int field5070 = 35;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field5069 = 6;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field5066 = 5;
   @ObfuscatedName("fj")
   public final int field5011;
   @ObfuscatedName("fl")
   final int field5050;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field5026 = 14;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field5051 = 15;
   @ObfuscatedName("ej")
   public final int field5008;
   @rl8(method10167 = 16)
   @ObfuscatedName("dl")
   short[] field5020;
   @ToRemove(unused = "true")
   @ObfuscatedName("cd")
   static final int field5074 = 113;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("dk")
   static NodeCache field5004 = new NodeCache(64);
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("db")
   static NodeCache field5005 = new NodeCache(50);
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field5072 = 36;
   @ObfuscatedName("de")
   final int field5012;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field5068 = 3;
   @ObfuscatedName("er")
   public final int field5056;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5073 = 2;
   @rl8(method10167 = 16)
   @ObfuscatedName("do")
   short[] field5038;
   @ObfuscatedName("em")
   public final int field5028;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field5067 = 9;
   @rl8(method10167 = 16)
   @ObfuscatedName("dq")
   short[] field5015;
   @ObfuscatedName("df")
   public int field5032;
   @ObfuscatedName("ev")
   public final boolean field5014;
   @ObfuscatedName("dj")
   public int field5034;
   @ObfuscatedName("da")
   public int field5052;
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final int field5071 = 37;
   @ObfuscatedName("eu")
   final int field5025;
   @ObfuscatedName("ea")
   public final int field5053;
   @ObfuscatedName("ek")
   public final int field5041;
   @ObfuscatedName("es")
   public final int field5042;
   @ObfuscatedName("dn")
   String field5040;
   @ObfuscatedName("fi")
   final int field5043;
   @ObfuscatedName("dp")
   public int field5036;
   @ObfuscatedSignature(descriptor = "Lpo;")
   @ObfuscatedName("el")
   final EntityOps field5046;
   @ObfuscatedName("dv")
   public final String field5031;
   @ObfuscatedName("ed")
   public final int field5019;
   @ObfuscatedName("en")
   public final int field5047;
   @ObfuscatedName("ex")
   final int[] field5054;
   @ObfuscatedName("eh")
   public final String[] field5023;
   @ObfuscatedName("eg")
   public final String[][] field5048;
   @ObfuscatedName("eo")
   public final int field5049;
   @ObfuscatedName("et")
   final int field5021;
   @ObfuscatedName("ew")
   final int field5057;
   @rl8(method10167 = 16)
   @ObfuscatedName("dg")
   short[] field5039;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("dw")
   public static NodeCache field5006 = new NodeCache(200);
   @ObfuscatedName("eq")
   final int[] field5055;
   @ObfuscatedName("ez")
   public final int field5045;
   @ObfuscatedName("ei")
   public final int field5009;
   @rl8(method10167 = 16)
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("ee")
   IterableHashTable field5030;
   @ObfuscatedName("nf")
   public int field5007 = -2;
   @ObfuscatedName("eb")
   boolean field5059;
   @ObfuscatedName("ep")
   public final boolean field5060;
   @ObfuscatedName("ec")
   public final boolean field5061;
   @ObfuscatedName("fo")
   final int field5062;
   @ObfuscatedName("ds")
   public int field5033;
   @ObfuscatedName("dz")
   int field5035;
   @ObfuscatedName("fd")
   public final int field5063;
   @ObfuscatedName("fy")
   final int field5022;
   @ObfuscatedName("fg")
   final int field5029;
   @ObfuscatedName("ey")
   public int field5037;
   @ObfuscatedName("fb")
   final int field5064;
   @ObfuscatedName("fu")
   final int field5016;
   @ObfuscatedName("fz")
   final int field5017;
   @ObfuscatedName("fh")
   final int field5065;
   @ObfuscatedName("fq")
   final int field5010;
   @ObfuscatedName("fp")
   final int field5024;
   @ObfuscatedName("fs")
   final int field5027;
   @ObfuscatedName("fv")
   final int field5044;
   @ObfuscatedName("fn")
   final int field5013;
   @ObfuscatedName("fk")
   final int field5018;
   @ObfuscatedName("ef")
   public final int field5058;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("hc")
   static classVP field5076;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dx")
   boolean method8737() {
      return null != this.field5015;
   }

   public int getYan2d() {
      return this.field5034 * -1663465803;
   }

   public int getHaPrice() {
      int var1 = this.getPrice();
      return (int)(var1 * 0.6F);
   }

   @ObfuscatedSignature(descriptor = "(Lop;B)Loz;")
   @ObfuscatedName("xo")
   public static classOZ method8666(ItemComposition var0, byte var1) {
      if (var0 == null) {
         return var0.method8669(var1);
      } else {
         try {
            return var0.field5046;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "op.af(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Ler;")
   @ObfuscatedName("ac")
   public final ModelData method8688(int var1, int var2) {
      try {
         if (null != this.field5054 && var1 > 1) {
            if (var2 == 1413803387) {
               throw new IllegalStateException();
            }

            int var3 = -1;

            for (int var4 = 0; var4 < 10; var4++) {
               if (var1 >= this.field5055[var4]) {
                  if (var2 == 1413803387) {
                     throw new IllegalStateException();
                  }

                  if (0 != this.field5055[var4]) {
                     if (var2 == 1413803387) {
                        throw new IllegalStateException();
                     }

                     var3 = this.field5054[var4];
                  }
               }
            }

            if (var3 != -1) {
               if (var2 == 1413803387) {
                  throw new IllegalStateException();
               }

               return classOB.method8329(var3, (byte)-27).method8688(1, -582229412);
            }
         }

         ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, this.field5035 * -1092439433, 0);
         if (null == var6) {
            return null;
         } else {
            label63: {
               if (-1567835707 * this.field5021 == 128) {
                  if (var2 == 1413803387) {
                     throw new IllegalStateException();
                  }

                  if (128 == -1367777757 * this.field5057) {
                     if (var2 == 1413803387) {
                        throw new IllegalStateException();
                     }

                     if (128 == 58165945 * this.field5025) {
                        break label63;
                     }
                  }
               }

               classSG.method10466(var6, this.field5021 * -1567835707, -1367777757 * this.field5057, this.field5025 * 58165945);
            }

            this.method8720(var6, null, 1388508531);
            return var6;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "op.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;I)Ler;")
   @ObfuscatedName("ax")
   public static ModelData method8689(ItemComposition var0, int var1) {
      if (var0 == null) {
         return var0.method8691(var1);
      } else {
         if (null != var0.field5054 && var1 > 1) {
            int var2 = -1;

            for (int var3 = 0; var3 < 10; var3++) {
               if (var1 >= var0.field5055[var3] && 0 != var0.field5055[var3]) {
                  var2 = var0.field5054[var3];
               }
            }

            if (var2 != -1) {
               return classOB.method8329(var2, (byte)1).method8688(1, 336931009);
            }
         }

         ModelData var4 = ModelData.method4067(WorldMapRenderer.field2700, var0.field5035 * -1092439433, 0);
         if (null == var4) {
            return null;
         } else {
            if (-1567835707 * var0.field5021 != 128 || 128 != -1367777757 * var0.field5057 || 128 != 58165945 * var0.field5025) {
               classSG.method10466(var4, var0.field5021 * -1567835707, -1367777757 * var0.field5057, var0.field5025 * 58165945);
            }

            var0.method8720(var4, null, 651439827);
            return var4;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpi;I)Lfx;")
   @ObfuscatedName("ao")
   public final Model method8698(ColorTextureOverride var1, int var2) {
      try {
         Model var3 = var1.field5266;
         if (var3 != null) {
            if (var2 >= 458130152) {
               throw new IllegalStateException();
            } else {
               return var3;
            }
         } else {
            ModelData var4 = ModelData.method4067(WorldMapRenderer.field2700, 1320690989 * var1.field5261, 0);
            if (null == var4) {
               if (var2 >= 458130152) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               label42: {
                  if (128 == -1567835707 * this.field5021) {
                     if (var2 >= 458130152) {
                        throw new IllegalStateException();
                     }

                     if (-1367777757 * this.field5057 == 128) {
                        if (128 == this.field5025 * 58165945) {
                           break label42;
                        }

                        if (var2 >= 458130152) {
                           throw new IllegalStateException();
                        }
                     }
                  }

                  classSG.method10466(var4, this.field5021 * -1567835707, this.field5057 * -1367777757, this.field5025 * 58165945);
               }

               this.method8720(var4, var1, -793555524);
               var3 = var4.method4070(1845819663 * this.field5058 + 64, 768 + this.field5028 * -1636300529, -50, -10, -50);
               var3.field2419 = true;
               var1.field5266 = var3;
               return var3;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "op.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lop;")
   @ObfuscatedName("al")
   public ItemComposition method8701(int var1, int var2) {
      try {
         if (null != this.field5054) {
            if (var2 <= 829149665) {
               throw new IllegalStateException();
            }

            if (var1 > 1) {
               if (var2 <= 829149665) {
                  throw new IllegalStateException();
               }

               int var3 = -1;

               for (int var4 = 0; var4 < 10; var4++) {
                  if (var2 <= 829149665) {
                     throw new IllegalStateException();
                  }

                  if (var1 >= this.field5055[var4]) {
                     if (var2 <= 829149665) {
                        throw new IllegalStateException();
                     }

                     if (this.field5055[var4] != 0) {
                        if (var2 <= 829149665) {
                           throw new IllegalStateException();
                        }

                        var3 = this.field5054[var4];
                     }
                  }
               }

               if (var3 != -1) {
                  if (var2 <= 829149665) {
                     throw new IllegalStateException();
                  }

                  return classOB.method8329(var3, (byte)17);
               }
            }
         }

         return this;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "op.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILpi;I)Ler;")
   @ObfuscatedName("ay")
   public final ModelData method8709(int var1, ColorTextureOverride var2, int var3) {
      try {
         int var4 = 1435937611 * this.field5029;
         int var5 = this.field5043 * 1179925911;
         int var6 = 1140068891 * this.field5010;
         if (1 == var1) {
            if (var3 != 1986500445) {
               throw new IllegalStateException();
            }

            var4 = -1246287245 * this.field5016;
            var5 = this.field5017 * 1780591021;
            var6 = this.field5024 * 1055956965;
         }

         if (var2 != null) {
            if (var3 != 1986500445) {
               throw new IllegalStateException();
            }

            if (var2.method8941(var1, -1598719426)) {
               var4 = ColorTextureOverride.method8949(var2, var1, (byte)-23);
            }
         }

         if (var4 == -1) {
            if (var3 != 1986500445) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            ModelData var7 = ModelData.method4067(WorldMapRenderer.field2700, var4, 0);
            if (-1 != var5) {
               if (var3 != 1986500445) {
                  throw new IllegalStateException();
               }

               ModelData var8 = ModelData.method4067(WorldMapRenderer.field2700, var5, 0);
               if (-1 != var6) {
                  if (var3 != 1986500445) {
                     throw new IllegalStateException();
                  }

                  ModelData var9 = ModelData.method4067(WorldMapRenderer.field2700, var6, 0);
                  ModelData[] var10 = new ModelData[]{var7, var8, var9};
                  var7 = new ModelData(var10, 3);
               } else {
                  ModelData[] var12 = new ModelData[]{var7, var8};
                  var7 = new ModelData(var12, 2);
               }
            }

            if (0 == var1 && 0 != this.field5064 * 1146551173) {
               if (var3 != 1986500445) {
                  throw new IllegalStateException();
               }

               var7.method4072(0, 1146551173 * this.field5064, 0);
            }

            if (1 == var1) {
               if (var3 != 1986500445) {
                  throw new IllegalStateException();
               }

               if (0 != -1325382577 * this.field5065) {
                  if (var3 != 1986500445) {
                     throw new IllegalStateException();
                  }

                  var7.method4072(0, this.field5065 * -1325382577, 0);
               }
            }

            this.method8720(var7, var2, 331104662);
            return var7;
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "op.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILpi;I)Z")
   @ObfuscatedName("aq")
   public final boolean method8714(int var1, ColorTextureOverride var2, int var3) {
      try {
         int var4 = -270643873 * this.field5027;
         int var5 = this.field5044 * -318962229;
         if (1 == var1) {
            var4 = 170424773 * this.field5013;
            var5 = -1490499797 * this.field5018;
         }

         if (null != var2) {
            if (var3 == 699854489) {
               throw new IllegalStateException();
            }

            if (ColorTextureOverride.method8943(var2, var1, 938234762)) {
               if (var3 == 699854489) {
                  throw new IllegalStateException();
               }

               var4 = ColorTextureOverride.method8954(var2, var1, 1991916254);
            }
         }

         if (var4 == -1) {
            if (var3 == 699854489) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            boolean var6 = true;
            if (!WorldMapRenderer.field2700.method11944(var4, 0, (byte)17)) {
               if (var3 == 699854489) {
                  throw new IllegalStateException();
               }

               var6 = false;
            }

            if (var5 != -1) {
               if (var3 == 699854489) {
                  throw new IllegalStateException();
               }

               if (!WorldMapRenderer.field2700.method11944(var5, 0, (byte)-7)) {
                  if (var3 == 699854489) {
                     throw new IllegalStateException();
                  }

                  var6 = false;
               }
            }

            return var6;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "op.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpo;")
   @ObfuscatedName("bn")
   EntityOps method8663() {
      return new EntityOps();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;ILpi;)Ler;")
   @ObfuscatedName("jk")
   public static ModelData method8710(ItemComposition var0, int var1, ColorTextureOverride var2) {
      int var3 = -1250696238 * var0.field5029;
      int var4 = var0.field5043 * 1179925911;
      int var5 = 1140068891 * var0.field5010;
      if (1 == var1) {
         var3 = 736960605 * var0.field5016;
         var4 = var0.field5017 * 242622001;
         var5 = var0.field5024 * 323047018;
      }

      if (var2 != null && var2.method8941(var1, -1588177379)) {
         var3 = ColorTextureOverride.method8949(var2, var1, (byte)23);
      }

      if (var3 == -1) {
         return null;
      } else {
         ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, var3, 0);
         if (-1 != var4) {
            ModelData var7 = ModelData.method4067(WorldMapRenderer.field2700, var4, 0);
            if (-1 != var5) {
               ModelData var8 = ModelData.method4067(WorldMapRenderer.field2700, var5, 0);
               ModelData[] var9 = new ModelData[]{var6, var7, var8};
               var6 = new ModelData(var9, 3);
            } else {
               ModelData[] var10 = new ModelData[]{var6, var7};
               var6 = new ModelData(var10, 2);
            }
         }

         if (0 == var1 && 0 != var0.field5064 * -408388760) {
            var6.method4072(0, 1765449929 * var0.field5064, 0);
         }

         if (1 == var1 && 0 != 1182565682 * var0.field5065) {
            var6.method4072(0, var0.field5065 * -8822325, 0);
         }

         var0.method8720(var6, var2, 1946050416);
         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;ILop;Lop;Z)V")
   @ObfuscatedName("qh")
   public void method8748(classXY var1, int var2, ItemComposition var3, ItemComposition var4, boolean var5) {
      if (this.field5040.endsWith(" (Members)")) {
         this.field5075 = this.field5040.substring(0, this.field5040.length() - " (Members)".length());
      } else {
         this.field5075 = this.field5040;
      }

      PostItemComposition var6 = new PostItemComposition(this);
      classOE.field4841.getCallbacks().post(var6);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("be")
   public static void method8683(boolean var0) {
      if (classJP.field3355 != var0) {
         classLI.method7149(168002647);
         classJP.field3355 = var0;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("di")
   boolean method8738() {
      return null != this.field5015;
   }

   public void setYan2d(int var1) {
      this.field5034 = var1 * -1388726371;
   }

   public void setColorToReplace(short[] var1) {
      this.field5038 = var1;
   }

   public int getZan2d() {
      return this.field5052 * 163610477;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("ar")
   public String method8729(int var1, String var2, int var3) {
      try {
         return classFS.method4998(this.field5030, var1, var2, (byte)19);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "op.ar(" + ')');
      }
   }

   public int getPlaceholderId() {
      return this.field5011 * 1197673963;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("bk")
   static final String method8676(int var0) {
      if (var0 < 100000) {
         return "<col=ffff00>" + var0 + "</col>";
      } else {
         return var0 < 10000000 ? "<col=ffffff>" + var0 / 1000 + classKK.field3804 + "</col>" : "<col=00ff80>" + var0 / 1000000 + classKK.field3785 + "</col>";
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpo;")
   @ObfuscatedName("ah")
   EntityOps method8664() {
      return new EntityOps();
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ak")
   boolean method8739(int var1) {
      try {
         boolean var10000;
         if (null != this.field5015) {
            if (var1 >= 1375698635) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "op.av(" + ')');
      }
   }

   public int getXan2d() {
      return this.field5033 * 320831297;
   }

   @ObfuscatedSignature(descriptor = "(Lop;III)I")
   @ObfuscatedName("wc")
   public static int method8721(ItemComposition var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method8725(var1, var1, var1);
      }

      try {
         return classGQ.method5635(var0.field5030, var1, var2, 376591025);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "op.au(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("at")
   public String method8741(int var1) {
      try {
         return this.field5040;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "op.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("cz")
   public int method8722(int var1, int var2) {
      return classGQ.method5635(this.field5030, var1, var2, 666503844);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("br")
   public static ItemComposition method8671(int var0) {
      ItemComposition var1 = (ItemComposition)NodeCache.method6460(field5004, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classOT.field5115.method11942(10, var0, -1018665989);
         if (null == var2) {
            var1 = new ItemComposition(null, var0, null, null, true);
         } else {
            classXY var3 = new classXY(var2);
            int[] var5 = new int[2];
            int var6 = var3.method13095(-346779531);
            if (var6 != -2098466477 && 139 != var6 && 148 != var6) {
               Arrays.fill(var5, -1);
            } else {
               var5[0] = var3.method13099(561495792);
               var3.method13095(-346779531);
               var5[1] = var3.method13099(-1883670695);
            }

            var3.field6953 = 0;
            ItemComposition var9 = var5[0] == -1 ? null : classOB.method8329(var5[0], (byte)-19);
            ItemComposition var7 = var5[1] == -1 ? null : classOB.method8329(var5[1], (byte)51);
            var1 = new ItemComposition(var3, var0, var9, var7, true);
         }

         field5004.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("ct")
   public ItemComposition method8702(int var1) {
      if (null != this.field5054 && var1 > 1) {
         int var2 = -1;

         for (int var3 = 0; var3 < 10; var3++) {
            if (var1 >= this.field5055[var3] && this.field5055[var3] != 0) {
               var2 = this.field5054[var3];
            }
         }

         if (var2 != -1) {
            return classOB.method8329(var2, (byte)25);
         }
      }

      return this;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aw")
   public int method8749(byte var1) {
      int var8;
      if (this.field5007 == -2) {
         ItemComposition var4 = this;
         byte var2 = 25;

         try {
            if (var4.field5022 * 1011947055 == -1 || null == var4.field5023) {
               var1 = (byte)-1;
               var8 = var1;
            } else if (var4.field5022 * 1011947055 >= 0) {
               if (var2 == 0) {
                  throw new IllegalStateException();
               }

               if (null != var4.field5023[var4.field5022 * 1011947055]) {
                  if (var2 == 0) {
                     throw new IllegalStateException();
                  }

                  var8 = 1011947055 * var4.field5022;
               } else {
                  var8 = -1;
               }

               var1 = var8;
               var8 = var1;
            } else {
               byte var9;
               if (classKK.field3675.equalsIgnoreCase(var4.field5023[4])) {
                  if (var2 == 0) {
                     throw new IllegalStateException();
                  }

                  var9 = 4;
               } else {
                  var9 = -1;
               }

               byte var6 = var9;
               var8 = var6;
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "op.aw(" + 41);
         }
      } else {
         var8 = this.field5007;
      }

      return var8;
   }

   public int getInventoryModel() {
      return this.field5035 * -1092439433;
   }

   public boolean isGeTradeable() {
      return this.field5060;
   }

   public boolean isTradeable() {
      return this.field5059;
   }

   @ObfuscatedSignature(descriptor = "()Loz;")
   @ObfuscatedName("bp")
   public classOZ method8667() {
      return this.field5046;
   }

   public int getPrice() {
      return this.field5041 * 665046345;
   }

   @ObfuscatedSignature(descriptor = "()Loz;")
   @ObfuscatedName("bx")
   public classOZ method8668() {
      return this.field5046;
   }

   @ObfuscatedSignature(descriptor = "(ILpi;I)Ler;")
   @ObfuscatedName("ad")
   public final ModelData method8716(int var1, ColorTextureOverride var2, int var3) {
      try {
         int var4 = this.field5027 * -270643873;
         int var5 = -318962229 * this.field5044;
         if (1 == var1) {
            if (var3 != -1218924652) {
               throw new IllegalStateException();
            }

            var4 = 170424773 * this.field5013;
            var5 = this.field5018 * -1490499797;
         }

         if (null != var2) {
            if (var3 != -1218924652) {
               throw new IllegalStateException();
            }

            if (ColorTextureOverride.method8943(var2, var1, 938234762)) {
               if (var3 != -1218924652) {
                  throw new IllegalStateException();
               }

               var4 = ColorTextureOverride.method8954(var2, var1, -1957028464);
            }
         }

         if (-1 == var4) {
            if (var3 != -1218924652) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, var4, 0);
            if (var5 != -1) {
               if (var3 != -1218924652) {
                  throw new IllegalStateException();
               }

               ModelData var7 = ModelData.method4067(WorldMapRenderer.field2700, var5, 0);
               ModelData[] var8 = new ModelData[]{var6, var7};
               var6 = new ModelData(var8, 2);
            }

            this.method8720(var6, var2, 946260232);
            return var6;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "op.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("ai")
   public long method8726(int var1, long var2) {
      try {
         return classBW.method1275(this.field5030, var1, var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "op.ai(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;)I")
   @ObfuscatedName("je")
   public static int method8733(ItemComposition var0) {
      if (var0 == null) {
         return var0.method8734();
      } else if (var0.field5022 * 1011947055 == -1 || null == var0.field5023) {
         return -1;
      } else if (var0.field5022 * 1011947055 >= 0) {
         return null != var0.field5023[var0.field5022 * 1011947055] ? 1011947055 * var0.field5022 : -1;
      } else {
         return classKK.field3675.equalsIgnoreCase(var0.field5023[4]) ? 4 : -1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("bt")
   public static ItemComposition method8672(int var0) {
      ItemComposition var1 = (ItemComposition)NodeCache.method6460(field5004, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classOT.field5115.method11942(10, var0, -1791739969);
         if (null == var2) {
            var1 = new ItemComposition(null, var0, null, null, true);
         } else {
            classXY var3 = new classXY(var2);
            int[] var5 = new int[2];
            int var6 = var3.method13095(-346779531);
            if (var6 != 97 && -1500868747 != var6 && 148 != var6) {
               Arrays.fill(var5, -1);
            } else {
               var5[0] = var3.method13099(1148186132);
               var3.method13095(-346779531);
               var5[1] = var3.method13099(-1988948320);
            }

            var3.field6953 = 0;
            ItemComposition var9 = var5[0] == -1 ? null : classOB.method8329(var5[0], (byte)-67);
            ItemComposition var7 = var5[1] == -1 ? null : classOB.method8329(var5[1], (byte)16);
            var1 = new ItemComposition(var3, var0, var9, var7, true);
         }

         field5004.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIZII)Lym;")
   @ObfuscatedName("bg")
   public static final SpritePixels method8674(int var0, int var1, int var2, int var3, int var4, boolean var5, int var6, int var7) {
      if (var6 != 36 || var7 != 32) {
         var4 = 0;
      } else if (-1 == var1) {
         var4 = 0;
      } else if (2 == var4 && 1 != var1) {
         var4 = 1;
      }

      long var8 = ((long)var1 << 16) + var0 + ((long)var2 << 38) + ((long)var4 << 40) + ((long)var3 << 42);
      if (!var5 && var6 == 36 && var7 == 32) {
         SpritePixels var10 = (SpritePixels)NodeCache.method6460(field5006, var8);
         if (var10 != null) {
            return var10;
         }
      }

      ItemComposition var11 = classOB.method8329(var0, (byte)12);
      if (var1 > 1 && null != var11.field5054) {
         int var12 = -1;

         for (int var13 = 0; var13 < 10; var13++) {
            if (var1 >= var11.field5055[var13] && 0 != var11.field5055[var13]) {
               var12 = var11.field5054[var13];
            }
         }

         if (-1 != var12) {
            var11 = classOB.method8329(var12, (byte)-15);
         }
      }

      Model var24 = method8696(var11, 1, -298459383);
      if (var24 == null) {
         return null;
      } else {
         SpritePixels var25 = null;
         if (1236138331 * var11.field5049 != -1) {
            var25 = classLJ.method7157(1357747305 * var11.field5056, 10, 1, 0, 0, true, var6, var7, 1683831829);
            if (var25 == null) {
               return null;
            }
         } else if (-1 != var11.field5050 * 1835686777) {
            var25 = classLJ.method7157(var11.field5062 * 641055003, var1, var2, var3, 0, false, var6, var7, 1683831829);
            if (null == var25) {
               return null;
            }
         } else if (-1 != -2023616411 * var11.field5063) {
            var25 = classLJ.method7157(1197673963 * var11.field5011, var1, 0, 0, 0, false, var6, var7, 1683831829);
            if (null == var25) {
               return null;
            }
         }

         int[] var14 = classYW.field7102;
         int var15 = classYW.field7100;
         int var16 = classYW.field7099;
         float[] var17 = classYW.field7101;
         int[] var18 = new int[4];
         classYW.method13714(var18);
         SpritePixels var23 = new SpritePixels(var6, var7);
         classFH.method4758(var23.field7003, var6, var7, null);
         classYW.method13722();
         classFH.method4760();
         int var19 = var7 >> 1;
         classFH.method4776(var19, var19);
         classFH.field2255.field2210 = false;
         if (var11.field5063 * -2023616411 != -1) {
            var25.method13555(0, 0);
         }

         int var20 = var11.field5032 * 1694643499;
         if (var6 != 36) {
            var20 = 32 * var20 / var6;
         }

         if (var5) {
            var20 = (int)(var20 * 1.5);
         } else if (var2 == 2) {
            var20 = (int)(1.04 * var20);
         }

         int var21 = var20 * classFH.field2251[var11.field5033 * 320831297] >> 16;
         int var22 = var20 * classFH.field2252[320831297 * var11.field5033] >> 16;
         Model.method5254(var24);
         var24.method5269(
            0,
            -1663465803 * var11.field5034,
            163610477 * var11.field5052,
            320831297 * var11.field5033,
            1898608961 * var11.field5036,
            var21 + -1256242689 * var24.field1741 / 2 + -1593790933 * var11.field5037,
            var22 + var11.field5037 * -1593790933
         );
         if (var11.field5050 * 1835686777 != -1) {
            var25.method13555(0, 0);
         }

         if (var2 >= 1) {
            var23.method13543(1);
         }

         if (var2 >= 2) {
            var23.method13543(16777215);
         }

         if (0 != var3) {
            SpritePixels.method13548(var23, var3);
         }

         classFH.method4758(var23.field7003, var6, var7, null);
         if (-1 != 1236138331 * var11.field5049) {
            var25.method13555(0, 0);
         }

         if (1 == var4 || 2 == var4 && var11.field5053 * 1633663665 == 1) {
            classNE.field4665.method51(classLH.method7144(var1, 2053793081), 0, 9, 16776960, 1);
         }

         if (!var5 && 36 == var6 && 32 == var7) {
            field5006.method6464(var23, var8);
         }

         classFH.method4758(var14, var15, var16, var17);
         classYW.method13718(var18);
         classFH.method4760();
         classFH.field2255.field2210 = true;
         return var23;
      }
   }

   public int getNote() {
      return this.field5049 * 1236138331;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIZII)Lym;")
   @ObfuscatedName("bj")
   public static final SpritePixels method8675(int var0, int var1, int var2, int var3, int var4, boolean var5, int var6, int var7) {
      if (var6 != -312366200 || var7 != 32) {
         var4 = 0;
      } else if (-1 == var1) {
         var4 = 0;
      } else if (2 == var4 && 1 != var1) {
         var4 = 1;
      }

      long var8 = ((long)var1 << 16) + var0 + ((long)var2 << -1914704479) + ((long)var4 << -1786782675) + ((long)var3 << 42);
      if (!var5 && var6 == 1086989495 && var7 == -1835691286) {
         SpritePixels var10 = (SpritePixels)NodeCache.method6460(field5006, var8);
         if (var10 != null) {
            return var10;
         }
      }

      ItemComposition var11 = classOB.method8329(var0, (byte)19);
      if (var1 > 1 && null != var11.field5054) {
         int var12 = -1;

         for (int var13 = 0; var13 < 10; var13++) {
            if (var1 >= var11.field5055[var13] && 0 != var11.field5055[var13]) {
               var12 = var11.field5054[var13];
            }
         }

         if (-1 != var12) {
            var11 = classOB.method8329(var12, (byte)-25);
         }
      }

      Model var24 = method8696(var11, 1, -732256111);
      if (var24 == null) {
         return null;
      } else {
         SpritePixels var25 = null;
         if (902793838 * var11.field5049 != -1) {
            var25 = classLJ.method7157(1357747305 * var11.field5056, 10, 1, 0, 0, true, var6, var7, 1683831829);
            if (var25 == null) {
               return null;
            }
         } else if (-1 != var11.field5050 * 1835686777) {
            var25 = classLJ.method7157(var11.field5062 * 641055003, var1, var2, var3, 0, false, var6, var7, 1683831829);
            if (null == var25) {
               return null;
            }
         } else if (-1 != -2023616411 * var11.field5063) {
            var25 = classLJ.method7157(6903103 * var11.field5011, var1, 0, 0, 0, false, var6, var7, 1683831829);
            if (null == var25) {
               return null;
            }
         }

         int[] var14 = classYW.field7102;
         int var15 = classYW.field7100;
         int var16 = classYW.field7099;
         float[] var17 = classYW.field7101;
         int[] var18 = new int[4];
         classYW.method13714(var18);
         SpritePixels var23 = new SpritePixels(var6, var7);
         classFH.method4758(var23.field7003, var6, var7, null);
         classYW.method13722();
         classFH.method4760();
         int var19 = var7 >> 1;
         classFH.method4776(var19, var19);
         classFH.field2255.field2210 = false;
         if (var11.field5063 * -870995309 != -1) {
            var25.method13555(0, 0);
         }

         int var20 = var11.field5032 * -2084481691;
         if (var6 != -751854891) {
            var20 = 32 * var20 / var6;
         }

         if (var5) {
            var20 = (int)(var20 * 1.5);
         } else if (var2 == 2) {
            var20 = (int)(1.04 * var20);
         }

         int var21 = var20 * classFH.field2251[var11.field5033 * 320831297] >> 16;
         int var22 = var20 * classFH.field2252[320831297 * var11.field5033] >> 16;
         Model.method5254(var24);
         var24.method5269(
            0,
            1603015838 * var11.field5034,
            1539052839 * var11.field5052,
            1625374268 * var11.field5033,
            1898608961 * var11.field5036,
            var21 + 1457118944 * var24.field1741 / 2 + -319025700 * var11.field5037,
            var22 + var11.field5037 * 336968249
         );
         if (var11.field5050 * 1835686777 != -1) {
            var25.method13555(0, 0);
         }

         if (var2 >= 1) {
            var23.method13543(1);
         }

         if (var2 >= 2) {
            var23.method13543(16777215);
         }

         if (0 != var3) {
            SpritePixels.method13548(var23, var3);
         }

         classFH.method4758(var23.field7003, var6, var7, null);
         if (-1 != 1236138331 * var11.field5049) {
            var25.method13555(0, 0);
         }

         if (1 == var4 || 2 == var4 && var11.field5053 * 852572643 == 1) {
            classNE.field4665.method51(classLH.method7144(var1, 1410754082), 0, 9, 16776960, 1);
         }

         if (!var5 && -647262508 == var6 && 32 == var7) {
            field5006.method6464(var23, var8);
         }

         classFH.method4758(var14, var15, var16, var17);
         classYW.method13718(var18);
         classFH.method4760();
         classFH.field2255.field2210 = true;
         return var23;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dw")
   public static int method8746() {
      return classCS.field1246 * -1766337085 - 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("bo")
   static final String method8677(int var0) {
      if (var0 < 471261072) {
         return "<col=ffff00>" + var0 + "</col>";
      } else {
         return var0 < 481682444
            ? "<col=ffffff>" + var0 / -1476484895 + classKK.field3804 + "</col>"
            : "<col=00ff80>" + var0 / -2098803328 + classKK.field3785 + "</col>";
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("bu")
   static final String method8678(int var0) {
      if (var0 < -1665438837) {
         return "<col=ffff00>" + var0 + "</col>";
      } else {
         return var0 < 10000000
            ? "<col=ffffff>" + var0 / 1000 + classKK.field3804 + "</col>"
            : "<col=00ff80>" + var0 / 1865489402 + classKK.field3785 + "</col>";
      }
   }

   public int getContrast() {
      return this.field5028 * -1636300529;
   }

   public int getPlaceholderTemplateId() {
      return this.field5063 * -2023616411;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dy")
   boolean method8740() {
      return null != this.field5015;
   }

   public boolean isMembers() {
      return this.field5014;
   }

   public int getShiftClickActionIndex() {
      return this.method8749((byte)25);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;ILpi;)Z")
   @ObfuscatedName("wh")
   public static boolean method8703(ItemComposition var0, int var1, ColorTextureOverride var2) {
      int var3 = var0.field5029 * 1357704716;
      int var4 = var0.field5043 * -2034374715;
      int var5 = 323873585 * var0.field5010;
      if (var1 == 1) {
         var3 = 295252139 * var0.field5016;
         var4 = var0.field5017 * -823410619;
         var5 = var0.field5024 * 1055956965;
      }

      if (var2 != null && var2.method8941(var1, 1826944147)) {
         var3 = ColorTextureOverride.method8949(var2, var1, (byte)62);
      }

      if (-1 == var3) {
         return true;
      } else {
         boolean var6 = true;
         if (!WorldMapRenderer.field2700.method11944(var3, 0, (byte)9)) {
            var6 = false;
         }

         if (-1 != var4 && !WorldMapRenderer.field2700.method11944(var4, 0, (byte)63)) {
            var6 = false;
         }

         if (-1 != var5 && !WorldMapRenderer.field2700.method11944(var5, 0, (byte)-104)) {
            var6 = false;
         }

         return var6;
      }
   }

   public boolean isStackable() {
      return this.method8751() == 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bs")
   public static void method8684(boolean var0) {
      if (classJP.field3355 != var0) {
         classLI.method7149(1540393474);
         classJP.field3355 = var0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bb")
   public static void method8679() {
      field5004.method6468();
      field5005.method6468();
      field5006.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bi")
   public static void method8680() {
      field5004.method6468();
      field5005.method6468();
      field5006.method6468();
   }

   public void setName(String var1) {
      this.field5040 = var1;
   }

   static {
      field5004.method6473(1024);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("by")
   public static void method8685(boolean var0) {
      if (classJP.field3355 != var0) {
         classLI.method7149(-83899757);
         classJP.field3355 = var0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bq")
   public static void method8686(boolean var0) {
      if (classJP.field3355 != var0) {
         classLI.method7149(-466542089);
         classJP.field3355 = var0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bf")
   public static void method8687(boolean var0) {
      if (classJP.field3355 != var0) {
         classLI.method7149(414837972);
         classJP.field3355 = var0;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cm")
   public String method8730(int var1, String var2) {
      return classFS.method4998(this.field5030, var1, var2, (byte)66);
   }

   public void setTextureToReplaceWith(short[] var1) {
      this.field5015 = var1;
   }

   public void setInventoryModel(int var1) {
      this.field5035 = var1 * -1068841145;
   }

   @ObfuscatedSignature(descriptor = "(I)Ler;")
   @ObfuscatedName("bd")
   public final ModelData method8690(int var1) {
      if (null != this.field5054 && var1 > 1) {
         int var2 = -1;

         for (int var3 = 0; var3 < 10; var3++) {
            if (var1 >= this.field5055[var3] && 0 != this.field5055[var3]) {
               var2 = this.field5054[var3];
            }
         }

         if (var2 != -1) {
            return classOB.method8329(var2, (byte)-97).method8688(1, -545953769);
         }
      }

      ModelData var4 = ModelData.method4067(WorldMapRenderer.field2700, this.field5035 * -1461985842, 0);
      if (null == var4) {
         return null;
      } else {
         if (1680516822 * this.field5021 != 128 || -1191862482 != -1367777757 * this.field5057 || -387468548 != 58165945 * this.field5025) {
            classSG.method10466(var4, this.field5021 * 1806637968, -1408246867 * this.field5057, this.field5025 * 58165945);
         }

         this.method8720(var4, null, -1742091839);
         return var4;
      }
   }

   public short[] getTextureToReplaceWith() {
      return this.field5015;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("ce")
   public String method8731(int var1, String var2) {
      return classFS.method4998(this.field5030, var1, var2, (byte)126);
   }

   public short[] getColorToReplace() {
      return this.field5038;
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("bz")
   public final Model method8692(int var1) {
      if (null != this.field5054 && var1 > 1) {
         int var2 = -1;

         for (int var3 = 0; var3 < 10; var3++) {
            if (var1 >= this.field5055[var3] && this.field5055[var3] != 0) {
               var2 = this.field5054[var3];
            }
         }

         if (-1 != var2) {
            return method8696(classOB.method8329(var2, (byte)-13), 1, -359686508);
         }
      }

      Model var4 = (Model)NodeCache.method6460(field5005, 501443835 * this.field5012);
      if (null != var4) {
         return var4;
      } else {
         ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, this.field5035 * -1092439433, 0);
         if (var6 == null) {
            return null;
         } else {
            if (128 != this.field5021 * -1567835707 || 128 != -1367777757 * this.field5057 || this.field5025 * 58165945 != 128) {
               classSG.method10466(var6, this.field5021 * -1567835707, this.field5057 * -1367777757, this.field5025 * 58165945);
            }

            this.method8720(var6, null, 682729635);
            var4 = var6.method4070(64 + 1845819663 * this.field5058, 768 + -1636300529 * this.field5028, -50, -10, -50);
            var4.field2419 = true;
            field5005.method6464(var4, 501443835 * this.field5012);
            return var4;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("cr")
   public final Model method8693(int var1) {
      if (null != this.field5054 && var1 > 1) {
         int var2 = -1;

         for (int var3 = 0; var3 < 10; var3++) {
            if (var1 >= this.field5055[var3] && this.field5055[var3] != 0) {
               var2 = this.field5054[var3];
            }
         }

         if (-1 != var2) {
            return method8696(classOB.method8329(var2, (byte)45), 1, 912409530);
         }
      }

      Model var4 = (Model)NodeCache.method6460(field5005, 501443835 * this.field5012);
      if (null != var4) {
         return var4;
      } else {
         ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, this.field5035 * -1092439433, 0);
         if (var6 == null) {
            return null;
         } else {
            if (128 != this.field5021 * -1567835707 || 128 != -1367777757 * this.field5057 || this.field5025 * 58165945 != 128) {
               classSG.method10466(var6, this.field5021 * -1567835707, this.field5057 * -1367777757, this.field5025 * 58165945);
            }

            this.method8720(var6, null, -531819644);
            var4 = var6.method4070(64 + 1845819663 * this.field5058, 768 + -1636300529 * this.field5028, -50, -10, -50);
            var4.field2419 = true;
            field5005.method6464(var4, 501443835 * this.field5012);
            return var4;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;ILpi;)Ler;")
   @ObfuscatedName("tv")
   public static ModelData method8711(ItemComposition var0, int var1, ColorTextureOverride var2) {
      int var3 = 1435937611 * var0.field5029;
      int var4 = var0.field5043 * 1891849656;
      int var5 = 1140068891 * var0.field5010;
      if (1 == var1) {
         var3 = 600435068 * var0.field5016;
         var4 = var0.field5017 * 1324916797;
         var5 = var0.field5024 * 1055956965;
      }

      if (var2 != null && var2.method8941(var1, 456403126)) {
         var3 = ColorTextureOverride.method8949(var2, var1, (byte)-3);
      }

      if (var3 == -1) {
         return null;
      } else {
         ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, var3, 0);
         if (-1 != var4) {
            ModelData var7 = ModelData.method4067(WorldMapRenderer.field2700, var4, 0);
            if (-1 != var5) {
               ModelData var8 = ModelData.method4067(WorldMapRenderer.field2700, var5, 0);
               ModelData[] var9 = new ModelData[]{var6, var7, var8};
               var6 = new ModelData(var9, 3);
            } else {
               ModelData[] var10 = new ModelData[]{var6, var7};
               var6 = new ModelData(var10, 2);
            }
         }

         if (0 == var1 && 0 != var0.field5064 * -1230210348) {
            var6.method4072(0, -1352528329 * var0.field5064, 0);
         }

         if (1 == var1 && 0 != -1325382577 * var0.field5065) {
            var6.method4072(0, var0.field5065 * -407884498, 0);
         }

         var0.method8720(var6, var2, 426112940);
         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("cj")
   public long method8727(int var1, long var2) {
      return classBW.method1275(this.field5030, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Lpi;)Lfx;")
   @ObfuscatedName("cb")
   public final Model method8699(ColorTextureOverride var1) {
      Model var2 = var1.field5266;
      if (var2 != null) {
         return var2;
      } else {
         ModelData var3 = ModelData.method4067(WorldMapRenderer.field2700, 1320690989 * var1.field5261, 0);
         if (null == var3) {
            return null;
         } else {
            if (128 != -1567835707 * this.field5021 || -1367777757 * this.field5057 != 128 || 128 != this.field5025 * 58165945) {
               classSG.method10466(var3, this.field5021 * -1567835707, this.field5057 * -1367777757, this.field5025 * 58165945);
            }

            this.method8720(var3, var1, -1988817717);
            var2 = var3.method4070(1845819663 * this.field5058 + 64, 768 + this.field5028 * -1636300529, -50, -10, -50);
            var2.field2419 = true;
            var1.field5266 = var2;
            return var2;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("bm")
   public static ItemComposition method8673(int var0) {
      ItemComposition var1 = (ItemComposition)NodeCache.method6460(field5004, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classOT.field5115.method11942(10, var0, -1443156630);
         if (null == var2) {
            var1 = new ItemComposition(null, var0, null, null, true);
         } else {
            classXY var3 = new classXY(var2);
            int[] var5 = new int[2];
            int var6 = var3.method13095(-346779531);
            if (var6 != 97 && 139 != var6 && 148 != var6) {
               Arrays.fill(var5, -1);
            } else {
               var5[0] = var3.method13099(263697987);
               var3.method13095(-346779531);
               var5[1] = var3.method13099(-1501548958);
            }

            var3.field6953 = 0;
            ItemComposition var9 = var5[0] == -1 ? null : classOB.method8329(var5[0], (byte)46);
            ItemComposition var7 = var5[1] == -1 ? null : classOB.method8329(var5[1], (byte)-86);
            var1 = new ItemComposition(var3, var0, var9, var7, true);
         }

         field5004.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILpi;)Z")
   @ObfuscatedName("cp")
   public final boolean method8704(int var1, ColorTextureOverride var2) {
      int var3 = this.field5029 * 229685856;
      int var4 = this.field5043 * -1820110788;
      int var5 = -1334677546 * this.field5010;
      if (var1 == 1) {
         var3 = 2058597109 * this.field5016;
         var4 = this.field5017 * -1959611066;
         var5 = this.field5024 * 1981578109;
      }

      if (var2 != null && var2.method8941(var1, -934478747)) {
         var3 = ColorTextureOverride.method8949(var2, var1, (byte)-65);
      }

      if (-1 == var3) {
         return true;
      } else {
         boolean var6 = true;
         if (!WorldMapRenderer.field2700.method11944(var3, 0, (byte)-18)) {
            var6 = false;
         }

         if (-1 != var4 && !WorldMapRenderer.field2700.method11944(var4, 0, (byte)-71)) {
            var6 = false;
         }

         if (-1 != var5 && !WorldMapRenderer.field2700.method11944(var5, 0, (byte)-23)) {
            var6 = false;
         }

         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILpi;)Z")
   @ObfuscatedName("cq")
   public final boolean method8705(int var1, ColorTextureOverride var2) {
      int var3 = this.field5029 * 1435937611;
      int var4 = this.field5043 * 1179925911;
      int var5 = 1140068891 * this.field5010;
      if (var1 == 1) {
         var3 = -1246287245 * this.field5016;
         var4 = this.field5017 * 1780591021;
         var5 = this.field5024 * 1055956965;
      }

      if (var2 != null && var2.method8941(var1, -1169647077)) {
         var3 = ColorTextureOverride.method8949(var2, var1, (byte)79);
      }

      if (-1 == var3) {
         return true;
      } else {
         boolean var6 = true;
         if (!WorldMapRenderer.field2700.method11944(var3, 0, (byte)-97)) {
            var6 = false;
         }

         if (-1 != var4 && !WorldMapRenderer.field2700.method11944(var4, 0, (byte)33)) {
            var6 = false;
         }

         if (-1 != var5 && !WorldMapRenderer.field2700.method11944(var5, 0, (byte)12)) {
            var6 = false;
         }

         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lpo;")
   @ObfuscatedName("az")
   EntityOps method8665(byte var1) {
      try {
         return new EntityOps();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "op.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILpi;)Ler;")
   @ObfuscatedName("cv")
   public final ModelData method8712(int var1, ColorTextureOverride var2) {
      int var3 = 1435937611 * this.field5029;
      int var4 = this.field5043 * 1179925911;
      int var5 = 1140068891 * this.field5010;
      if (1 == var1) {
         var3 = -1246287245 * this.field5016;
         var4 = this.field5017 * 1780591021;
         var5 = this.field5024 * 1055956965;
      }

      if (var2 != null && var2.method8941(var1, 98485987)) {
         var3 = ColorTextureOverride.method8949(var2, var1, (byte)-70);
      }

      if (var3 == -1) {
         return null;
      } else {
         ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, var3, 0);
         if (-1 != var4) {
            ModelData var7 = ModelData.method4067(WorldMapRenderer.field2700, var4, 0);
            if (-1 != var5) {
               ModelData var8 = ModelData.method4067(WorldMapRenderer.field2700, var5, 0);
               ModelData[] var9 = new ModelData[]{var6, var7, var8};
               var6 = new ModelData(var9, 3);
            } else {
               ModelData[] var10 = new ModelData[]{var6, var7};
               var6 = new ModelData(var10, 2);
            }
         }

         if (0 == var1 && 0 != this.field5064 * 1146551173) {
            var6.method4072(0, 1146551173 * this.field5064, 0);
         }

         if (1 == var1 && 0 != -1325382577 * this.field5065) {
            var6.method4072(0, this.field5065 * -1325382577, 0);
         }

         this.method8720(var6, var2, 618580462);
         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILpi;)Ler;")
   @ObfuscatedName("co")
   public final ModelData method8713(int var1, ColorTextureOverride var2) {
      int var3 = 1435937611 * this.field5029;
      int var4 = this.field5043 * 1179925911;
      int var5 = 1140068891 * this.field5010;
      if (1 == var1) {
         var3 = -1246287245 * this.field5016;
         var4 = this.field5017 * 1780591021;
         var5 = this.field5024 * 1055956965;
      }

      if (var2 != null && var2.method8941(var1, -797943705)) {
         var3 = ColorTextureOverride.method8949(var2, var1, (byte)-55);
      }

      if (var3 == -1) {
         return null;
      } else {
         ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, var3, 0);
         if (-1 != var4) {
            ModelData var7 = ModelData.method4067(WorldMapRenderer.field2700, var4, 0);
            if (-1 != var5) {
               ModelData var8 = ModelData.method4067(WorldMapRenderer.field2700, var5, 0);
               ModelData[] var9 = new ModelData[]{var6, var7, var8};
               var6 = new ModelData(var9, 3);
            } else {
               ModelData[] var10 = new ModelData[]{var6, var7};
               var6 = new ModelData(var10, 2);
            }
         }

         if (0 == var1 && 0 != this.field5064 * 1146551173) {
            var6.method4072(0, 1146551173 * this.field5064, 0);
         }

         if (1 == var1 && 0 != -1325382577 * this.field5065) {
            var6.method4072(0, this.field5065 * -1325382577, 0);
         }

         this.method8720(var6, var2, 1947094201);
         return var6;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bw")
   public static void method8681() {
      field5004.method6468();
      field5005.method6468();
      field5006.method6468();
   }

   @ObfuscatedSignature(descriptor = "(ILpi;)Ler;")
   @ObfuscatedName("ca")
   public final ModelData method8717(int var1, ColorTextureOverride var2) {
      int var3 = this.field5027 * -270643873;
      int var4 = -318962229 * this.field5044;
      if (1 == var1) {
         var3 = 170424773 * this.field5013;
         var4 = this.field5018 * -1490499797;
      }

      if (null != var2 && ColorTextureOverride.method8943(var2, var1, 938234762)) {
         var3 = ColorTextureOverride.method8954(var2, var1, 141890165);
      }

      if (-1 == var3) {
         return null;
      } else {
         ModelData var5 = ModelData.method4067(WorldMapRenderer.field2700, var3, 0);
         if (var4 != -1) {
            ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, var4, 0);
            ModelData[] var7 = new ModelData[]{var5, var6};
            var5 = new ModelData(var7, 2);
         }

         this.method8720(var5, var2, -231448513);
         return var5;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;Lpi;)V")
   @ObfuscatedName("cf")
   void method8719(ModelData var1, ColorTextureOverride var2) {
      if (this.field5038 != null) {
         short[] var3 = this.field5038;
         short[] var4 = this.field5039;
         if (var2 != null && var2.field5259 != null && this.field5039.length == var2.field5259.length) {
            var4 = var2.field5259;
         }

         for (int var5 = 0; var5 < this.field5038.length; var5++) {
            var1.method4031(var3[var5], var4[var5]);
         }
      }

      if (null != this.field5020) {
         short[] var6 = this.field5020;
         short[] var7 = this.field5015;
         if (var2 != null && var2.field5258 != null && this.field5015.length == var2.field5258.length) {
            var7 = var2.field5258;
         }

         for (int var8 = 0; var8 < this.field5020.length; var8++) {
            var1.method4035(var6[var8], var7[var8]);
         }
      }
   }

   public int getId() {
      return this.field5012 * 501443835;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("an")
   public boolean method8744(int var1) {
      try {
         return this.field5059;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "op.an(" + ')');
      }
   }

   public void setXan2d(int var1) {
      this.field5033 = var1 * 1728909505;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cx")
   public String method8732(int var1, String var2) {
      return classFS.method4998(this.field5030, var1, var2, (byte)79);
   }

   public void setTextureToReplace(short[] var1) {
      this.field5020 = var1;
   }

   public String getMembersName() {
      return this.field5075;
   }

   public short[] getColorToReplaceWith() {
      return this.field5039;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("cg")
   public int method8723(int var1, int var2) {
      return classGQ.method5635(this.field5030, var1, var2, 1308048585);
   }

   public net.runelite.api.IterableHashTable getParams() {
      return this.field5030;
   }

   public void setZan2d(int var1) {
      this.field5052 = var1 * 1973314149;
   }

   @ObfuscatedSignature(descriptor = "(ILpi;)Z")
   @ObfuscatedName("ck")
   public final boolean method8715(int var1, ColorTextureOverride var2) {
      int var3 = -270643873 * this.field5027;
      int var4 = this.field5044 * -318962229;
      if (1 == var1) {
         var3 = 170424773 * this.field5013;
         var4 = -1490499797 * this.field5018;
      }

      if (null != var2 && ColorTextureOverride.method8943(var2, var1, 938234762)) {
         var3 = ColorTextureOverride.method8954(var2, var1, -375356849);
      }

      if (var3 == -1) {
         return true;
      } else {
         boolean var5 = true;
         if (!WorldMapRenderer.field2700.method11944(var3, 0, (byte)-23)) {
            var5 = false;
         }

         if (var4 != -1 && !WorldMapRenderer.field2700.method11944(var4, 0, (byte)38)) {
            var5 = false;
         }

         return var5;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lop;ILpi;S)Z")
   @ObfuscatedName("va")
   public static boolean method8706(ItemComposition var0, int var1, ColorTextureOverride var2, short var3) {
      if (var0 == null) {
         var0.method8708(var1, var2, var3);
      }

      try {
         int var4 = var0.field5029 * 1435937611;
         int var5 = var0.field5043 * 1179925911;
         int var6 = 1140068891 * var0.field5010;
         if (var1 == 1) {
            if (var3 == 5017) {
               throw new IllegalStateException();
            }

            var4 = -1246287245 * var0.field5016;
            var5 = var0.field5017 * 1780591021;
            var6 = var0.field5024 * 1055956965;
         }

         if (var2 != null) {
            if (var3 == 5017) {
               throw new IllegalStateException();
            }

            if (var2.method8941(var1, 1873480736)) {
               if (var3 == 5017) {
                  throw new IllegalStateException();
               }

               var4 = ColorTextureOverride.method8949(var2, var1, (byte)-89);
            }
         }

         if (-1 == var4) {
            if (var3 == 5017) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            boolean var7 = true;
            if (!WorldMapRenderer.field2700.method11944(var4, 0, (byte)35)) {
               if (var3 == 5017) {
                  throw new IllegalStateException();
               }

               var7 = false;
            }

            if (-1 != var5) {
               if (var3 == 5017) {
                  throw new IllegalStateException();
               }

               if (!WorldMapRenderer.field2700.method11944(var5, 0, (byte)-53)) {
                  var7 = false;
               }
            }

            if (-1 != var6) {
               if (var3 == 5017) {
                  throw new IllegalStateException();
               }

               if (!WorldMapRenderer.field2700.method11944(var6, 0, (byte)101)) {
                  if (var3 == 5017) {
                     throw new IllegalStateException();
                  }

                  var7 = false;
               }
            }

            return var7;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "op.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("ci")
   public long method8728(int var1, long var2) {
      return classBW.method1275(this.field5030, var1, var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;ILpi;)Z")
   @ObfuscatedName("af")
   public static boolean method8707(ItemComposition var0, int var1, ColorTextureOverride var2) {
      if (var0 == null) {
         var0.method8700(var2);
      }

      int var3 = var0.field5029 * 1435937611;
      int var4 = var0.field5043 * 1179925911;
      int var5 = 1140068891 * var0.field5010;
      if (var1 == 1) {
         var3 = -1246287245 * var0.field5016;
         var4 = var0.field5017 * 1780591021;
         var5 = var0.field5024 * 1055956965;
      }

      if (var2 != null && var2.method8941(var1, 508455355)) {
         var3 = ColorTextureOverride.method8949(var2, var1, (byte)-107);
      }

      if (-1 == var3) {
         return true;
      } else {
         boolean var6 = true;
         if (!WorldMapRenderer.field2700.method11944(var3, 0, (byte)61)) {
            var6 = false;
         }

         if (-1 != var4 && !WorldMapRenderer.field2700.method11944(var4, 0, (byte)44)) {
            var6 = false;
         }

         if (-1 != var5 && !WorldMapRenderer.field2700.method11944(var5, 0, (byte)24)) {
            var6 = false;
         }

         return var6;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpi;)Lfx;")
   @ObfuscatedName("cu")
   public final Model method8700(ColorTextureOverride var1) {
      Model var2 = var1.field5266;
      if (var2 != null) {
         return var2;
      } else {
         ModelData var3 = ModelData.method4067(WorldMapRenderer.field2700, 1320690989 * var1.field5261, 0);
         if (null == var3) {
            return null;
         } else {
            if (128 != -1567835707 * this.field5021 || -1367777757 * this.field5057 != 128 || 128 != this.field5025 * 58165945) {
               classSG.method10466(var3, this.field5021 * -1567835707, this.field5057 * -1367777757, this.field5025 * 58165945);
            }

            this.method8720(var3, var1, -2062266506);
            var2 = var3.method4070(1845819663 * this.field5058 + 64, 768 + this.field5028 * -1636300529, -50, -10, -50);
            var2.field2419 = true;
            var1.field5266 = var2;
            return var2;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;I)Lfx;")
   @ObfuscatedName("tp")
   public static Model method8694(ItemComposition var0, int var1) {
      if (null != var0.field5054 && var1 > 1) {
         int var2 = -1;

         for (int var3 = 0; var3 < 10; var3++) {
            if (var1 >= var0.field5055[var3] && var0.field5055[var3] != 0) {
               var2 = var0.field5054[var3];
            }
         }

         if (-1 != var2) {
            return method8696(classOB.method8329(var2, (byte)1), 1, -1409561541);
         }
      }

      Model var4 = (Model)NodeCache.method6460(field5005, 501443835 * var0.field5012);
      if (null != var4) {
         return var4;
      } else {
         ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, var0.field5035 * -1092439433, 0);
         if (var6 == null) {
            return null;
         } else {
            if (446765916 != var0.field5021 * -472201330 || 128 != -1629433313 * var0.field5057 || var0.field5025 * 58165945 != 128) {
               classSG.method10466(var6, var0.field5021 * -1567835707, var0.field5057 * -1367777757, var0.field5025 * 58165945);
            }

            var0.method8720(var6, null, -1757832547);
            var4 = var6.method4070(1406743090 + 1359909781 * var0.field5058, -649529554 + -612359240 * var0.field5028, -1753294748, -796583298, -50);
            var4.field2419 = true;
            field5005.method6464(var4, -365109785 * var0.field5012);
            return var4;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("dt")
   public String method8742() {
      return this.field5040;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ba")
   public static void method8682() {
      field5004.method6468();
      field5005.method6468();
      field5006.method6468();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dd")
   boolean method8735() {
      return null != this.field5039;
   }

   @ObfuscatedSignature(descriptor = "(ILpi;)Ler;")
   @ObfuscatedName("cc")
   public final ModelData method8718(int var1, ColorTextureOverride var2) {
      int var3 = this.field5027 * -270643873;
      int var4 = -318962229 * this.field5044;
      if (1 == var1) {
         var3 = 170424773 * this.field5013;
         var4 = this.field5018 * -1490499797;
      }

      if (null != var2 && ColorTextureOverride.method8943(var2, var1, 938234762)) {
         var3 = ColorTextureOverride.method8954(var2, var1, -1413638824);
      }

      if (-1 == var3) {
         return null;
      } else {
         ModelData var5 = ModelData.method4067(WorldMapRenderer.field2700, var3, 0);
         if (var4 != -1) {
            ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, var4, 0);
            ModelData[] var7 = new ModelData[]{var5, var6};
            var5 = new ModelData(var7, 2);
         }

         this.method8720(var5, var2, -1469572665);
         return var5;
      }
   }

   public int getLinkedNoteId() {
      return this.field5056 * 1357747305;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("xn")
   public int method8750() {
      return this.field5029 * 1435937611;
   }

   @ObfuscatedSignature(descriptor = "(Ler;Lpi;I)V")
   @ObfuscatedName("ap")
   void method8720(ModelData var1, ColorTextureOverride var2, int var3) {
      try {
         if (this.field5038 != null) {
            short[] var4 = this.field5038;
            short[] var5 = this.field5039;
            if (var2 != null && var2.field5259 != null) {
               if (var3 == 1198748727) {
                  throw new IllegalStateException();
               }

               if (this.field5039.length == var2.field5259.length) {
                  if (var3 == 1198748727) {
                     throw new IllegalStateException();
                  }

                  var5 = var2.field5259;
               }
            }

            for (int var6 = 0; var6 < this.field5038.length; var6++) {
               if (var3 == 1198748727) {
                  return;
               }

               var1.method4031(var4[var6], var5[var6]);
            }
         }

         if (null != this.field5020) {
            if (var3 == 1198748727) {
               return;
            }

            short[] var8 = this.field5020;
            short[] var9 = this.field5015;
            if (var2 != null) {
               if (var3 == 1198748727) {
                  throw new IllegalStateException();
               }

               if (var2.field5258 != null) {
                  if (var3 == 1198748727) {
                     throw new IllegalStateException();
                  }

                  if (this.field5015.length == var2.field5258.length) {
                     if (var3 == 1198748727) {
                        throw new IllegalStateException();
                     }

                     var9 = var2.field5258;
                  }
               }
            }

            for (int var10 = 0; var10 < this.field5020.length; var10++) {
               if (var3 == 1198748727) {
                  return;
               }

               var1.method4035(var8[var10], var9[var10]);
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "op.ap(" + ')');
      }
   }

   public String[] getInventoryActions() {
      return this.field5023;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ch")
   public int method8724(int var1, int var2) {
      return classGQ.method5635(this.field5030, var1, var2, 457021445);
   }

   public int getAmbient() {
      return this.field5058 * 1845819663;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("du")
   public String method8743() {
      return this.field5040;
   }

   public String[][] getSubops() {
      return this.field5048;
   }

   public void setColorToReplaceWith(short[] var1) {
      this.field5039 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("av")
   boolean method8736(int var1) {
      try {
         boolean var10000;
         if (null != this.field5039) {
            if (var1 >= -528692156) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "op.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dr")
   public boolean method8745() {
      return this.field5059;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("db")
   public static int method8747() {
      return classCS.field1246 * -172787477 - 1;
   }

   ItemComposition(classXY var1, int var2, ItemComposition var3, ItemComposition var4, boolean var5) {
      this.field5012 = -1958887885 * var2;
      int var6 = 0;
      String var7 = classKK.field3613;
      String var8 = classKK.field3613;
      short[] var9 = null;
      short[] var10 = null;
      short[] var11 = null;
      short[] var12 = null;
      int var13 = 2000;
      int var14 = 0;
      int var15 = 0;
      int var16 = 0;
      int var17 = 0;
      int var18 = 0;
      int var19 = 0;
      int var20 = 1;
      int var21 = -1;
      int var22 = -1;
      int var23 = -1;
      boolean var24 = false;
      EntityOps var25 = this.method8665((byte)25);
      String[] var26 = new String[]{null, null, null, null, classKK.field3675};
      String[][] var27 = (String[][])null;
      int var28 = -1;
      int[] var29 = null;
      int[] var30 = null;
      int var31 = -1;
      int var32 = -1;
      int var33 = 128;
      int var34 = 128;
      int var35 = 128;
      byte var36 = 0;
      int var37 = 0;
      int var38 = 0;
      int var39 = 0;
      IterableHashTable var40 = null;
      boolean var41 = true;
      boolean var42 = false;
      boolean var43 = false;
      int var44 = -1;
      int var45 = -1;
      int var46 = -1;
      int var47 = -1;
      byte var48 = -2;
      int var49 = -1;
      int var50 = -1;
      int var51 = 0;
      int var52 = -1;
      int var53 = -1;
      int var54 = 0;
      int var55 = -1;
      int var56 = -1;
      int var57 = -1;
      int var58 = -1;
      int var59 = -1;
      int var60 = -1;
      var25.method9093(2, classKK.field3803, (byte)6);
      if (var1 != null && null != var1.field6952) {
         label397:
         while (true) {
            int var61 = var1.method13095(-346779531);
            switch (var61) {
               case 0:
                  break label397;
               case 1:
               case 10:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               case 28:
               case 29:
               case 55:
               case 56:
               case 57:
               case 58:
               case 59:
               case 60:
               case 61:
               case 62:
               case 63:
               case 64:
               case 66:
               case 67:
               case 68:
               case 69:
               case 70:
               case 71:
               case 72:
               case 73:
               case 74:
               case 76:
               case 77:
               case 78:
               case 79:
               case 80:
               case 81:
               case 82:
               case 83:
               case 84:
               case 85:
               case 86:
               case 87:
               case 88:
               case 89:
               case 90:
               case 91:
               case 92:
               case 93:
               case 96:
               case 99:
               case 116:
               case 117:
               case 118:
               case 119:
               case 120:
               case 121:
               case 122:
               case 123:
               case 124:
               case 125:
               case 126:
               case 127:
               case 128:
               case 129:
               case 130:
               case 131:
               case 132:
               case 133:
               case 134:
               case 135:
               case 136:
               case 137:
               case 138:
               case 141:
               case 142:
               case 143:
               case 144:
               case 145:
               case 146:
               case 147:
               case 150:
               case 151:
               case 152:
               case 153:
               case 154:
               case 155:
               case 156:
               case 157:
               case 158:
               case 159:
               case 161:
               case 162:
               case 163:
               case 164:
               case 165:
               case 166:
               case 167:
               case 168:
               case 169:
               case 170:
               case 171:
               case 172:
               case 173:
               case 174:
               case 175:
               case 176:
               case 177:
               case 178:
               case 179:
               case 180:
               case 181:
               case 182:
               case 183:
               case 184:
               case 185:
               case 186:
               case 187:
               case 188:
               case 189:
               case 190:
               case 191:
               case 192:
               case 193:
               case 194:
               case 195:
               case 196:
               case 197:
               case 198:
               case 199:
               case 203:
               case 204:
               case 205:
               case 206:
               case 207:
               case 208:
               case 209:
               case 210:
               case 211:
               case 212:
               case 213:
               case 214:
               case 215:
               case 216:
               case 217:
               case 218:
               case 219:
               case 220:
               case 221:
               case 222:
               case 223:
               case 224:
               case 225:
               case 226:
               case 227:
               case 228:
               case 229:
               case 230:
               case 231:
               case 232:
               case 233:
               case 234:
               case 235:
               case 236:
               case 237:
               case 238:
               case 239:
               case 240:
               case 241:
               case 242:
               case 243:
               case 244:
               case 245:
               case 246:
               case 247:
               case 248:
               default:
                  continue;
               case 2:
                  var7 = var1.method13127(758624956);
                  continue;
               case 3:
                  var8 = var1.method13127(786369912);
                  continue;
               case 4:
                  var13 = var1.method13099(-2138153189);
                  continue;
               case 5:
                  var14 = var1.method13099(-629975210);
                  continue;
               case 6:
                  var15 = var1.method13099(-2014502119);
                  continue;
               case 7:
                  var17 = var1.method13099(-616024580);
                  if (var17 > 32767) {
                     var17 -= 65536;
                  }
                  continue;
               case 8:
                  var18 = var1.method13099(922208087);
                  if (var18 > 32767) {
                     var18 -= 65536;
                  }
                  continue;
               case 9:
                  var1.method13127(-512316701);
                  continue;
               case 11:
                  var19 = 1;
                  continue;
               case 12:
                  var20 = classXY.method13113(var1, (byte)1);
                  continue;
               case 13:
                  var21 = var1.method13095(-346779531);
                  continue;
               case 14:
                  var22 = var1.method13095(-346779531);
                  continue;
               case 15:
                  var41 = false;
                  continue;
               case 16:
                  var24 = true;
                  continue;
               case 27:
                  var23 = var1.method13095(-346779531);
                  continue;
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 200:
               case 201:
               case 202:
                  var25.method9088(var1, var61, 30, 34, 200, 201, 202, 1161561653);
                  continue;
               case 35:
               case 36:
               case 37:
               case 38:
               case 39:
                  var26[var61 - 35] = var1.method13127(616224821);
                  continue;
               case 40:
                  int var71 = var1.method13095(-346779531);
                  var9 = new short[var71];
                  var10 = new short[var71];
                  int var74 = 0;

                  while (true) {
                     if (var74 >= var71) {
                        continue label397;
                     }

                     var9[var74] = (short)var1.method13099(762099035);
                     var10[var74] = (short)var1.method13099(-1299973714);
                     var74++;
                  }
               case 41:
                  int var70 = var1.method13095(-346779531);
                  var11 = new short[var70];
                  var12 = new short[var70];
                  int var73 = 0;

                  while (true) {
                     if (var73 >= var70) {
                        continue label397;
                     }

                     var11[var73] = (short)var1.method13099(-1945013046);
                     var12[var73] = (short)var1.method13099(1655174411);
                     var73++;
                  }
               case 42:
                  var48 = var1.method13097((byte)17);
                  continue;
               case 43:
                  int var62 = var1.method13095(-346779531);
                  if (null == var27) {
                     var27 = new String[5][];
                  }

                  boolean var63 = var62 >= 0 && var62 < 5;
                  if (var63 && var27[var62] == null) {
                     var27[var62] = new String[20];
                  }

                  while (true) {
                     int var64 = var1.method13095(-346779531) - 1;
                     if (var64 == -1) {
                        continue label397;
                     }

                     String var65 = var1.method13127(1568482341);
                     if (var63 && var64 >= 0 && var64 < 20) {
                        var27[var62][var64] = var65;
                     }
                  }
               case 44:
                  var6 = var1.method13173(classXM.field6886.field6888, 1472752456);
                  continue;
               case 45:
                  var49 = var1.method13173(classXM.field6886.field6888, 1906716019);
                  var51 = var1.method13095(-346779531);
                  continue;
               case 46:
                  var50 = var1.method13173(classXM.field6886.field6888, 1809984855);
                  continue;
               case 47:
                  var55 = var1.method13173(classXM.field6886.field6888, 1925301324);
                  continue;
               case 48:
                  var52 = var1.method13173(classXM.field6886.field6888, 1528286961);
                  var54 = var1.method13095(-346779531);
                  continue;
               case 49:
                  var53 = var1.method13173(classXM.field6886.field6888, 1625198394);
                  continue;
               case 50:
                  var56 = var1.method13173(classXM.field6886.field6888, 1663065129);
                  continue;
               case 51:
                  var57 = var1.method13173(classXM.field6886.field6888, 1931215053);
                  continue;
               case 52:
                  var58 = var1.method13173(classXM.field6886.field6888, 1649838812);
                  continue;
               case 53:
                  var59 = var1.method13173(classXM.field6886.field6888, 1918388706);
                  continue;
               case 54:
                  var60 = var1.method13173(classXM.field6886.field6888, 1858049712);
                  continue;
               case 65:
                  var42 = true;
                  var43 = true;
                  continue;
               case 75:
                  var39 = var1.method13104((byte)16);
                  continue;
               case 94:
                  var28 = var1.method13099(1734013249);
                  continue;
               case 95:
                  var16 = var1.method13099(1707621308);
                  continue;
               case 97:
                  var31 = var1.method13099(-1736084497);
                  continue;
               case 98:
                  var32 = var1.method13099(-1400051452);
                  continue;
               case 100:
               case 101:
               case 102:
               case 103:
               case 104:
               case 105:
               case 106:
               case 107:
               case 108:
               case 109:
                  if (var29 == null) {
                     var29 = new int[10];
                     var30 = new int[10];
                  }

                  var29[var61 - 100] = var1.method13099(-248531488);
                  var30[var61 - 100] = var1.method13099(-541455458);
                  continue;
               case 110:
                  var33 = var1.method13099(278666739);
                  continue;
               case 111:
                  var34 = var1.method13099(-52034166);
                  continue;
               case 112:
                  var35 = var1.method13099(-1755046705);
                  continue;
               case 113:
                  var36 = var1.method13097((byte)17);
                  continue;
               case 114:
                  var37 = var1.method13097((byte)17) * 5;
                  continue;
               case 115:
                  var38 = var1.method13095(-346779531);
                  continue;
               case 139:
                  var44 = var1.method13099(1686312871);
                  continue;
               case 140:
                  var45 = var1.method13099(1139718612);
                  continue;
               case 148:
                  var46 = var1.method13099(1404586075);
                  continue;
               case 149:
                  var47 = var1.method13099(-113666036);
                  continue;
               case 160:
                  var19 = 2;
                  continue;
               case 249:
            }

            var40 = classQH.method9572(var1, var40, 1978086108);
         }
      } else {
         var41 = false;
      }

      if (var19 == 1) {
         var39 = 0;
      }

      if (null != var4) {
         var6 = var4.field5035 * -1092439433;
         var13 = 1694643499 * var4.field5032;
         var14 = var4.field5033 * 320831297;
         var15 = -1663465803 * var4.field5034;
         var16 = 163610477 * var4.field5052;
         var17 = 1898608961 * var4.field5036;
         var18 = var4.field5037 * -1593790933;
         if (var45 != -1) {
            var9 = var3.field5038;
            var10 = var3.field5039;
            var11 = var3.field5020;
            var12 = var3.field5015;
         } else {
            var9 = var4.field5038;
            var10 = var4.field5039;
            var11 = var4.field5020;
            var12 = var4.field5015;
         }

         if (var32 != -1) {
            var7 = var3.field5040;
            var8 = classKK.field3606;
            var24 = var3.field5014;
            var20 = 665046345 * var3.field5041;
            var41 = var3.method8744(1437914346);
            var19 = 1;
         }

         if (var45 != -1) {
            var7 = var3.field5040;
            var8 = var3.field5031;
            var24 = var3.field5014;
            var19 = 1633663665 * var3.field5053;
            var28 = var3.field5047 * -807855685;
            var21 = 1481543581 * var3.field5042;
            var22 = -1462570117 * var3.field5008;
            var23 = var3.field5019 * -1681179861;
            var49 = var3.field5029 * 1435937611;
            var50 = 1179925911 * var3.field5043;
            var55 = 1140068891 * var3.field5010;
            var52 = -1246287245 * var3.field5016;
            var53 = 1780591021 * var3.field5017;
            var56 = var3.field5024 * 1055956965;
            var57 = var3.field5027 * -270643873;
            var58 = -318962229 * var3.field5044;
            var59 = 170424773 * var3.field5013;
            var60 = var3.field5018 * -1490499797;
            var38 = var3.field5045 * -115663245;
            var25 = var3.field5046;
            var39 = var3.field5009 * -692383701;
            var26 = new String[5];
            if (null != var3.field5023) {
               for (int var67 = 0; var67 < 4; var67++) {
                  var26[var67] = var3.field5023[var67];
               }
            }

            var26[4] = classKK.field3770;
            if (null != var3.field5048) {
               var27 = new String[5][];
               System.arraycopy(var3.field5048, 0, var27, 0, 4);
            } else {
               var27 = (String[][])null;
            }

            var20 = 0;
            var41 = false;
            var42 = false;
            var43 = false;
         }

         if (var47 != -1) {
            var19 = var4.field5053 * 1633663665;
            var28 = var4.field5047 * -807855685;
            var7 = var3.field5040;
            var8 = var3.field5031;
            var20 = 0;
            var24 = false;
            var41 = false;
            var42 = false;
            var43 = false;
         }
      }

      if (!classJP.field3355 && var24) {
         if (null == var4) {
            var7 = var7 + classKK.field3604;
         }

         var8 = classKK.field3687;
         var43 = false;
         var41 = false;
         EntityOps.method9108(var25, -1092663845);
         var25.method9093(2, classKK.field3803, (byte)-72);

         for (int var68 = 0; var68 < var26.length; var68++) {
            if (4 != var68) {
               if (null != var27) {
                  var27[var68] = null;
               }

               var26[var68] = null;
            }
         }

         var48 = -2;
         var28 = -1;
         var38 = 0;
         if (var5 && var40 != null) {
            for (Node var69 = var40.method13628(); var69 != null; var69 = classDI.method3311(var40)) {
               classPN var72 = classNE.method7875((int)var69.field6550, (byte)-33);
               if (var72.field5353) {
                  var69.vmethod398();
               }
            }
         }
      }

      this.field5035 = var6 * -1068841145;
      this.field5040 = var7;
      this.field5031 = var8;
      this.field5038 = var9;
      this.field5039 = var10;
      this.field5020 = var11;
      this.field5015 = var12;
      this.field5032 = 1399644547 * var13;
      this.field5033 = var14 * 1728909505;
      this.field5034 = var15 * -1388726371;
      this.field5052 = 1973314149 * var16;
      this.field5036 = -1110501695 * var17;
      this.field5037 = var18 * -177954685;
      this.field5053 = 1880378961 * var19;
      this.field5041 = -391727879 * var20;
      this.field5042 = var21 * 1057713845;
      this.field5008 = var22 * 1065808819;
      this.field5019 = var23 * -101602429;
      this.field5014 = var24;
      this.field5046 = var25;
      this.field5023 = var26;
      this.field5048 = var27;
      this.field5047 = var28 * -1432304781;
      this.field5054 = var29;
      this.field5055 = var30;
      this.field5056 = var31 * 1761688537;
      this.field5049 = 919703251 * var32;
      this.field5021 = 1036410125 * var33;
      this.field5057 = 2142323083 * var34;
      this.field5025 = var35 * 1156537225;
      this.field5058 = var36 * -474032657;
      this.field5028 = var37 * 1805239791;
      this.field5045 = 847886011 * var38;
      this.field5009 = var39 * 1167928963;
      this.field5059 = var41;
      this.field5060 = var42;
      this.field5061 = var43;
      this.field5062 = -2107049709 * var44;
      this.field5050 = var45 * 1870012105;
      this.field5011 = 886944451 * var46;
      this.field5063 = var47 * 1534730605;
      this.field5022 = var48 * 785248463;
      this.field5029 = -249035165 * var49;
      this.field5043 = 815403559 * var50;
      this.field5064 = 1559610701 * var51;
      this.field5016 = 1374063291 * var52;
      this.field5017 = var53 * -1456496091;
      this.field5065 = 2047890095 * var54;
      this.field5010 = 999454739 * var55;
      this.field5024 = var56 * 1357022701;
      this.field5027 = 1233473183 * var57;
      this.field5044 = var58 * 1736964067;
      this.field5013 = 1282710797 * var59;
      this.field5018 = var60 * 404471683;
      if (null == var40) {
         this.field5030 = new IterableHashTable(1);
      } else {
         this.field5030 = var40;
      }

      this.method8748(var1, var2, var3, var4, var5);
   }

   @ObfuscatedSignature(descriptor = "(Lyn;)V")
   @ObfuscatedName("pr")
   @Override
   public void vmethod537(IterableHashTable var1) {
      this.field5030 = var1;
   }

   @ObfuscatedSignature(descriptor = "(B)Loz;")
   @ObfuscatedName("af")
   public classOZ method8669(byte var1) {
      try {
         return this.field5046;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "op.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lfx;")
   @ObfuscatedName("aa")
   public final Model method8695(int var1, int var2) {
      try {
         if (null != this.field5055) {
            if (var2 >= 1213519585) {
               throw new IllegalStateException();
            }

            if (var1 > 1) {
               if (var2 >= 1213519585) {
                  throw new IllegalStateException();
               }

               int var3 = -1;

               for (int var4 = 0; var4 < 10; var4++) {
                  if (var2 >= 1213519585) {
                     throw new IllegalStateException();
                  }

                  if (var1 >= this.field5054[var4]) {
                     if (var2 >= 1213519585) {
                        throw new IllegalStateException();
                     }

                     if (this.field5054[var4] != 0) {
                        var3 = this.field5055[var4];
                     }
                  }
               }

               if (-1 != var3) {
                  if (var2 >= 1213519585) {
                     throw new IllegalStateException();
                  }

                  return method8696(classOB.method8329(var3, (byte)26), 1, 557403969);
               }
            }
         }

         Model var6 = (Model)NodeCache.method6460(field5004, 501443835 * this.field5057);
         if (null != var6) {
            if (var2 >= 1213519585) {
               throw new IllegalStateException();
            } else {
               return var6;
            }
         } else {
            ModelData var8 = ModelData.method4067(WorldMapRenderer.field2700, this.field5035 * -1092439433, 0);
            if (var8 == null) {
               if (var2 >= 1213519585) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               label77: {
                  if (128 == this.field5024 * -1567835707 && 128 == -1367777757 * this.field5025) {
                     if (var2 >= 1213519585) {
                        throw new IllegalStateException();
                     }

                     if (this.field5043 * 58165945 == 128) {
                        break label77;
                     }

                     if (var2 >= 1213519585) {
                        throw new IllegalStateException();
                     }
                  }

                  classSG.method10466(var8, this.field5012 * -1567835707, this.field5018 * -1367777757, this.field5010 * 58165945);
               }

               this.method8720(var8, null, 1041866684);
               var6 = var8.method4070(64 + 1845819663 * this.field5011, 768 + -1636300529 * this.field5009, -50, -10, -50);
               var6.field2419 = true;
               field5005.method6464(var6, 501443835 * this.field5017);
               return var6;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "op.aa(" + ')');
      }
   }

   public short[] getTextureToReplace() {
      return this.field5020;
   }

   @ObfuscatedSignature(descriptor = "(Lop;II)Lfx;")
   @ObfuscatedName("xz")
   public static Model method8696(ItemComposition var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method8695(var1, var1);
      } else {
         try {
            if (null != var0.field5054) {
               if (var2 >= 1213519585) {
                  throw new IllegalStateException();
               }

               if (var1 > 1) {
                  if (var2 >= 1213519585) {
                     throw new IllegalStateException();
                  }

                  int var3 = -1;

                  for (int var4 = 0; var4 < 10; var4++) {
                     if (var2 >= 1213519585) {
                        throw new IllegalStateException();
                     }

                     if (var1 >= var0.field5055[var4]) {
                        if (var2 >= 1213519585) {
                           throw new IllegalStateException();
                        }

                        if (var0.field5055[var4] != 0) {
                           var3 = var0.field5054[var4];
                        }
                     }
                  }

                  if (-1 != var3) {
                     if (var2 >= 1213519585) {
                        throw new IllegalStateException();
                     }

                     return method8696(classOB.method8329(var3, (byte)26), 1, 557403969);
                  }
               }
            }

            Model var6 = (Model)NodeCache.method6460(field5005, 501443835 * var0.field5012);
            if (null != var6) {
               if (var2 >= 1213519585) {
                  throw new IllegalStateException();
               } else {
                  return var6;
               }
            } else {
               ModelData var8 = ModelData.method4067(WorldMapRenderer.field2700, var0.field5035 * -1092439433, 0);
               if (var8 == null) {
                  if (var2 >= 1213519585) {
                     throw new IllegalStateException();
                  } else {
                     return null;
                  }
               } else {
                  label80: {
                     if (128 == var0.field5021 * -1567835707 && 128 == -1367777757 * var0.field5057) {
                        if (var2 >= 1213519585) {
                           throw new IllegalStateException();
                        }

                        if (var0.field5025 * 58165945 == 128) {
                           break label80;
                        }

                        if (var2 >= 1213519585) {
                           throw new IllegalStateException();
                        }
                     }

                     classSG.method10466(var8, var0.field5021 * -1567835707, var0.field5057 * -1367777757, var0.field5025 * 58165945);
                  }

                  var0.method8720(var8, null, 1041866684);
                  var6 = var8.method4070(64 + 1845819663 * var0.field5058, 768 + -1636300529 * var0.field5028, -50, -10, -50);
                  var6.field2419 = true;
                  field5005.method6464(var6, 501443835 * var0.field5012);
                  return var6;
               }
            }
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "op.aa(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILpi;S)Z")
   @ObfuscatedName("aj")
   public final boolean method8708(int var1, ColorTextureOverride var2, short var3) {
      try {
         int var4 = this.field5044 * 1435937611;
         int var5 = this.field5065 * 1179925911;
         int var6 = 1140068891 * this.field5021;
         if (var1 == 1) {
            if (var3 == 5017) {
               throw new IllegalStateException();
            }

            var4 = -1246287245 * this.field5012;
            var5 = this.field5064 * 1780591021;
            var6 = this.field5050 * 1055956965;
         }

         if (var2 != null) {
            if (var3 == 5017) {
               throw new IllegalStateException();
            }

            if (var2.method8941(var1, 1873480736)) {
               if (var3 == 5017) {
                  throw new IllegalStateException();
               }

               var4 = ColorTextureOverride.method8949(var2, var1, (byte)-89);
            }
         }

         if (-1 == var4) {
            if (var3 == 5017) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            boolean var7 = true;
            if (!WorldMapRenderer.field2700.method11944(var4, 0, (byte)35)) {
               if (var3 == 5017) {
                  throw new IllegalStateException();
               }

               var7 = false;
            }

            if (-1 != var5) {
               if (var3 == 5017) {
                  throw new IllegalStateException();
               }

               if (!WorldMapRenderer.field2700.method11944(var5, 0, (byte)-53)) {
                  var7 = false;
               }
            }

            if (-1 != var6) {
               if (var3 == 5017) {
                  throw new IllegalStateException();
               }

               if (!WorldMapRenderer.field2700.method11944(var6, 0, (byte)101)) {
                  if (var3 == 5017) {
                     throw new IllegalStateException();
                  }

                  var7 = false;
               }
            }

            return var7;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "op.aj(" + ')');
      }
   }

   public void setShiftClickActionIndex(int var1) {
      this.field5007 = var1;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("au")
   public int method8725(int var1, int var2, int var3) {
      try {
         return classGQ.method5635(this.field5030, var1, var2, 376591025);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "op.au(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Loz;")
   @ObfuscatedName("bc")
   public classOZ method8670() {
      return this.field5046;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;I)Lfx;")
   @ObfuscatedName("wy")
   public static Model method8697(ItemComposition var0, int var1) {
      if (null != var0.field5054 && var1 > 1) {
         int var2 = -1;

         for (int var3 = 0; var3 < 10; var3++) {
            if (var1 >= var0.field5055[var3] && var0.field5055[var3] != 0) {
               var2 = var0.field5054[var3];
            }
         }

         if (-1 != var2) {
            return method8696(classOB.method8329(var2, (byte)71), 1, -1208920301);
         }
      }

      Model var4 = (Model)NodeCache.method6460(field5005, 501443835 * var0.field5012);
      if (null != var4) {
         return var4;
      } else {
         ModelData var6 = ModelData.method4067(WorldMapRenderer.field2700, var0.field5035 * -1092439433, 0);
         if (var6 == null) {
            return null;
         } else {
            if (128 != var0.field5021 * -1567835707 || 128 != -1367777757 * var0.field5057 || var0.field5025 * 58165945 != 128) {
               classSG.method10466(var6, var0.field5021 * -1567835707, var0.field5057 * -1367777757, var0.field5025 * 58165945);
            }

            var0.method8720(var6, null, 389728438);
            var4 = var6.method4070(64 + 1845819663 * var0.field5058, 768 + -1636300529 * var0.field5028, -50, -10, -50);
            var4.field2419 = true;
            field5005.method6464(var4, 501443835 * var0.field5012);
            return var4;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ler;")
   @ObfuscatedName("bl")
   public final ModelData method8691(int var1) {
      if (null != this.field5054 && var1 > 1) {
         int var2 = -1;

         for (int var3 = 0; var3 < 10; var3++) {
            if (var1 >= this.field5055[var3] && 0 != this.field5055[var3]) {
               var2 = this.field5055[var3];
            }
         }

         if (var2 != -1) {
            return classOB.method8329(var2, (byte)-97).method8688(1, -545953769);
         }
      }

      ModelData var4 = ModelData.method3985(WorldMapRenderer.field2700, this.field5035 * -1461985842, 0);
      if (null == var4) {
         return null;
      } else {
         if (1680516822 * this.field5024 != 128 || -1191862482 != -1367777757 * this.field5065 || -387468548 != 58165945 * this.field5062) {
            classSG.method10466(var4, this.field5043 * 1806637968, -1408246867 * this.field5027, this.field5022 * 58165945);
         }

         this.method8720(var4, null, -1742091839);
         return var4;
      }
   }

   public String getName() {
      return this.field5040;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ih")
   public int method8751() {
      return this.field5053 * 1633663665;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dc")
   public int method8734() {
      if (this.field5065 * 1011947055 == -1 || null == this.field5023) {
         return -1;
      } else if (this.field5022 * 1011947055 >= 0) {
         return null != this.field5023[this.field5010 * 1011947055] ? 1011947055 * this.field5057 : -1;
      } else {
         return classKK.field3755.equalsIgnoreCase(this.field5023[4]) ? 4 : -1;
      }
   }
}
