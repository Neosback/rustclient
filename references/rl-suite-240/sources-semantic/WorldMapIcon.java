import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hd")
public abstract class WorldMapIcon implements net.runelite.api.worldmap.WorldMapIcon {
   @ObfuscatedSignature(descriptor = "Lky;")
   @ObfuscatedName("ax")
   public final classKY field2767;
   @ObfuscatedSignature(descriptor = "Lky;")
   @ObfuscatedName("ac")
   public final classKY field2768;
   @ObfuscatedName("aa")
   int field2769;
   @ObfuscatedName("ao")
   int field2770;

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("af")
   public abstract int vmethod486(byte var1);

   @ObfuscatedSignature(descriptor = "(IIB)Z")
   @ObfuscatedName("au")
   boolean method6024(int var1, int var2, byte var3) {
      try {
         if (this.method6028(var1, var2, -1042187112)) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            return method6031(this, var1, var2, 196548377);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "hd.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lhw;")
   @ObfuscatedName("ae")
   abstract classHW vmethod491(byte var1);

   @ObfuscatedSignature(descriptor = "(Lhd;III)Z")
   @ObfuscatedName("un")
   public static boolean method6031(WorldMapIcon var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method6030(var1, var1, var1);
      }

      try {
         classHW var4 = var0.vmethod491((byte)-71);
         if (var4 == null) {
            if (var3 == -1902233820) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            if (var1 >= var0.field2769 * 797816725 - 2116737063 * var4.field2900 / 2) {
               if (var3 == -1902233820) {
                  throw new IllegalStateException();
               }

               if (var1 <= var4.field2900 * 2116737063 / 2 + 797816725 * var0.field2769) {
                  if (var2 >= var0.field2770 * 655244251) {
                     if (var2 <= var4.field2897 * -737746087 + var0.field2770 * 655244251) {
                        return true;
                     }

                     if (var3 == -1902233820) {
                        throw new IllegalStateException();
                     }
                  }

                  return false;
               }

               if (var3 == -1902233820) {
                  throw new IllegalStateException();
               }
            }

            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "hd.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   abstract int vmethod493(int var1);

   @ObfuscatedSignature(descriptor = "()Lky;")
   @ObfuscatedName("rz")
   public classKY method6035() {
      return this.field2767;
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("ag")
   abstract int vmethod494(short var1);

   @ObfuscatedSignature(descriptor = "()Lhw;")
   @ObfuscatedName("aj")
   abstract classHW vmethod490();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   abstract int vmethod492();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   public abstract int vmethod488();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   public abstract int vmethod487();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhd;II)Z")
   @ObfuscatedName("gx")
   public static boolean method6025(WorldMapIcon var0, int var1, int var2) {
      if (var0 == null) {
         var0.method6029(var1, var1);
      }

      return var0.method6028(var1, var2, -1042187112) ? true : method6031(var0, var1, var2, 1672440760);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   public abstract int vmethod489();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;Z)V")
   @ObfuscatedName("az")
   public static void method6033(RenderOverview var0, boolean var1) {
      if (var0 == null) {
         var0.method12619();
      } else {
         var0.field6696 = var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("ar")
   boolean method6028(int var1, int var2, int var3) {
      try {
         if (!this.method6026((byte)-13)) {
            if (var3 != -1042187112) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            classPS var4 = SceneTilePaint.method4817(this.vmethod486((byte)-34), (byte)-12);
            int var5 = this.vmethod493(-1358808218);
            int var6 = this.vmethod494((short)16349);
            switch (var4.field5400.field5229 * 1700578181) {
               case 0:
                  if (var1 <= this.field2769 * 797816725 - var5) {
                     return false;
                  }

                  if (var3 != -1042187112) {
                     throw new IllegalStateException();
                  }

                  if (var1 > this.field2769 * 797816725) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
                  break;
               case 1:
                  if (var1 < this.field2769 * 797816725 - var5 / 2 || var1 > var5 / 2 + 797816725 * this.field2769) {
                     return false;
                  }
                  break;
               case 2:
                  if (var1 < 797816725 * this.field2769) {
                     return false;
                  }

                  if (var3 != -1042187112) {
                     throw new IllegalStateException();
                  }

                  if (var1 >= var5 + this.field2769 * 797816725) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
            }

            switch (-1205275063 * var4.field5395.field4915) {
               case 0:
                  if (var2 < 655244251 * this.field2770 - var6 / 2) {
                     return false;
                  }

                  if (var3 != -1042187112) {
                     throw new IllegalStateException();
                  }

                  if (var2 > this.field2770 * 655244251 + var6 / 2) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
                  break;
               case 1:
                  if (var2 <= 655244251 * this.field2770 - var6) {
                     return false;
                  }

                  if (var2 > this.field2770 * 655244251) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
                  break;
               case 2:
                  if (var2 < this.field2770 * 655244251) {
                     return false;
                  }

                  if (var2 >= var6 + this.field2770 * 655244251) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
            }

            return true;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "hd.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   abstract int vmethod497();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   abstract int vmethod496();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   abstract int vmethod495();

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ai")
   boolean method6026(byte var1) {
      try {
         boolean var10000;
         if (this.vmethod486((byte)-66) >= 0) {
            if (var1 >= 0) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hd.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("am")
   boolean method6032(int var1, int var2) {
      classHW var3 = this.vmethod491((byte)40);
      if (var3 == null) {
         return false;
      } else {
         return var1 < this.field2769 * 797816725 - 2116737063 * var3.field2900 / 2 || var1 > var3.field2900 * 2116737063 / 2 + 797816725 * this.field2769
            ? false
            : var2 >= this.field2770 * 655244251 && var2 <= var3.field2897 * -737746087 + this.field2770 * 655244251;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("at")
   boolean method6027() {
      return this.vmethod486((byte)-101) >= 0;
   }

   public WorldPoint getCoordinate() {
      classKY var1 = this.method6035();
      return new WorldPoint(var1.field4053 * 1870919123, var1.field4054 * 819227381, var1.field4055 * -210579119);
   }

   public int getType() {
      return this.vmethod486((byte)-119);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("an")
   boolean method6029(int var1, int var2) {
      if (!this.method6026((byte)-103)) {
         return false;
      } else {
         classPS var3 = SceneTilePaint.method4817(this.vmethod486((byte)-114), (byte)-81);
         int var4 = this.vmethod493(-1468528891);
         int var5 = this.vmethod494((short)5677);
         switch (var3.field5400.field5229 * 1700578181) {
            case 0:
               if (var1 <= this.field2769 * 797816725 - var4 || var1 > this.field2769 * 797816725) {
                  return false;
               }
               break;
            case 1:
               if (var1 < this.field2769 * 797816725 - var4 / 2 || var1 > var4 / 2 + 797816725 * this.field2769) {
                  return false;
               }
               break;
            case 2:
               if (var1 < 797816725 * this.field2769 || var1 >= var4 + this.field2769 * 797816725) {
                  return false;
               }
         }

         switch (-1205275063 * var3.field5395.field4915) {
            case 0:
               if (var2 >= 655244251 * this.field2770 - var5 / 2 && var2 <= this.field2770 * 655244251 + var5 / 2) {
                  break;
               }

               return false;
            case 1:
               if (var2 > 655244251 * this.field2770 - var5 && var2 <= this.field2770 * 655244251) {
                  break;
               }

               return false;
            case 2:
               if (var2 < this.field2770 * 655244251 || var2 >= var5 + this.field2770 * 655244251) {
                  return false;
               }
         }

         return true;
      }
   }

   WorldMapIcon(classKY var1, classKY var2) {
      this.field2768 = var1;
      this.field2767 = var2;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("bw")
   static final void method6034(byte var0) {
      try {
         if (!client.field960) {
            if (var0 <= 64) {
               throw new IllegalStateException();
            }

            int var1 = classKY.method6656(GameObject.field2197 * -1584251835, -1889906639);
            int var2 = classKY.method6656(-214044967 * GraphicsObject.field474, 1693229959);
            int var3 = classKV.method6613(classIS.field3051, var1, var2, classIS.field3051.field1708 * -483624883, (byte)122) - classBQ.field530 * 1492293289;
            classHA.method6018(var1, var3, var2, 885661509);
         } else if (null != client.field944) {
            if (var0 <= 64) {
               throw new IllegalStateException();
            }

            classKI.field3596 = client.field944.vmethod614(1736248623) * -1197566029;
            classAI.field167 = client.field944.vmethod618(-1326078651) * -337711351;
            if (client.field1002) {
               if (var0 <= 64) {
                  throw new IllegalStateException();
               }

               classNL.field4682 = client.field944.vmethod620(1122221346) * -477405995;
            } else {
               classNL.field4682 = (
                     classKV.method6613(
                           classIS.field3051,
                           -325062789 * classKI.field3596,
                           1343311673 * classAI.field167,
                           -483624883 * classIS.field3051.field1708,
                           (byte)122
                        )
                        - client.field944.vmethod620(593117606)
                  )
                  * -477405995;
            }

            client.field944.method12336((byte)-56);
         }

         if (!client.field992) {
            int var13;
            int var14;
            int var15;
            label118: {
               if (client.field831) {
                  if (var0 <= 64) {
                     throw new IllegalStateException();
                  }

                  if (client.field997 != null) {
                     if (var0 <= 64) {
                        throw new IllegalStateException();
                     }

                     var13 = client.field997.vmethod614(1795660463);
                     var14 = client.field997.vmethod618(-1326078651);
                     var15 = classKV.method6613(classIS.field3051, var13, var14, -483624883 * classIS.field3051.field1708, (byte)122)
                        - client.field997.vmethod620(1205980509);
                     client.field997.method12336((byte)-57);
                     classHE.field2779 = classCQ.field1203 * -856235905;
                     if (client.field997.method12340((byte)120)) {
                        if (var0 <= 64) {
                           throw new IllegalStateException();
                        }

                        client.field831 = false;
                        client.field997 = null;
                     }
                     break label118;
                  }
               }

               var13 = classKY.method6656(classOR.field5102 * -1861237657, -142680752);
               var14 = classKY.method6656(742819657 * classCQ.field1217, 1302326797);
               var15 = classKV.method6613(classIS.field3051, var13, var14, -483624883 * classIS.field3051.field1708, (byte)122)
                  - 1218236279 * classMD.field4484;
            }

            int var4 = var13 - classKI.field3596 * -325062789;
            int var5 = var15 - 1615527037 * classNL.field4682;
            int var6 = var14 - 1343311673 * classAI.field167;
            int var7 = (int)Math.sqrt(var4 * var4 + var6 * var6);
            int var8 = classIU.method6359(Math.atan2(var5, var7));
            short var10 = 8192;
            if (var8 > var10) {
               if (var0 <= 64) {
                  throw new IllegalStateException();
               }

               var8 -= classKG.method6508(787321987);
            }

            int var11 = -classIU.method6359(Math.atan2(var4, var6));
            classMH.method7615(var8, var11, (short)19447);
         } else {
            if (client.field996 != null) {
               if (var0 <= 64) {
                  throw new IllegalStateException();
               }

               classUP.method11494(client.field915, client.field996.method12014((byte)-91), -1749416718);
               classUP.method11494(client.field915, classKC.method6497(client.field915.method11500((short)-7662), -181998943), -1582187546);
               client.field996.method12336((byte)-37);
            }

            if (null != client.field814) {
               if (var0 <= 64) {
                  return;
               }

               classUP.method11494(client.field797, client.field814.method12014((byte)-61), -1765419919);
               client.field814.method12336((byte)-16);
            }

            if (client.field994) {
               if (var0 <= 64) {
                  throw new IllegalStateException();
               }

               if (client.field996 != null) {
                  if (!client.field996.method12340((byte)105)) {
                     return;
                  }

                  if (var0 <= 64) {
                     throw new IllegalStateException();
                  }
               }

               if (client.field814 != null) {
                  if (var0 <= 64) {
                     return;
                  }

                  if (!client.field814.method12340((byte)5)) {
                     return;
                  }

                  if (var0 <= 64) {
                     throw new IllegalStateException();
                  }
               }

               client.field996 = null;
               client.field814 = null;
               client.field992 = false;
               classHE.field2779 = classCQ.field1203 * -856235905;
               client.field994 = false;
            }
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "hd.bw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("aw")
   boolean method6030(int var1, int var2, int var3) {
      try {
         if (!this.method6026((byte)-13)) {
            if (var3 != -1042187112) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            classPS var4 = SceneTilePaint.method4817(this.vmethod486((byte)-34), (byte)-12);
            int var5 = this.vmethod493(-1358808218);
            int var6 = this.vmethod494((short)16349);
            switch (var4.field5400.field5229 * 1700578181) {
               case 0:
                  if (var1 <= this.field2770 * 797816725 - var5) {
                     return false;
                  }

                  if (var3 != -1042187112) {
                     throw new IllegalStateException();
                  }

                  if (var1 > this.field2770 * 797816725) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
                  break;
               case 1:
                  if (var1 < this.field2769 * 797816725 - var5 / 2 || var1 > var5 / 2 + 797816725 * this.field2769) {
                     return false;
                  }
                  break;
               case 2:
                  if (var1 < 797816725 * this.field2769) {
                     return false;
                  }

                  if (var3 != -1042187112) {
                     throw new IllegalStateException();
                  }

                  if (var1 >= var5 + this.field2769 * 797816725) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
            }

            switch (-1205275063 * var4.field5395.field4915) {
               case 0:
                  if (var2 < 655244251 * this.field2769 - var6 / 2) {
                     return false;
                  }

                  if (var3 != -1042187112) {
                     throw new IllegalStateException();
                  }

                  if (var2 > this.field2770 * 655244251 + var6 / 2) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
                  break;
               case 1:
                  if (var2 <= 655244251 * this.field2770 - var6) {
                     return false;
                  }

                  if (var2 > this.field2770 * 655244251) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
                  break;
               case 2:
                  if (var2 < this.field2769 * 655244251) {
                     return false;
                  }

                  if (var2 >= var6 + this.field2769 * 655244251) {
                     if (var3 != -1042187112) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
            }

            return true;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "hd.ar(" + ')');
      }
   }
}
