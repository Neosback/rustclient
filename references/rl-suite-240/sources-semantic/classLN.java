import java.net.MalformedURLException;
import java.net.URL;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ln")
public class classLN {
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   public static final int field4189 = 19;
   @ObfuscatedSignature(descriptor = "Ley;")
   @ObfuscatedName("az")
   classEY field4188;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final long field4190 = 86400000L;
   @ObfuscatedSignature(descriptor = "Lym;")
   @ObfuscatedName("af")
   SpritePixels field4191;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lln;)Lym;")
   @ObfuscatedName("lr")
   public static SpritePixels method7165(classLN var0) {
      if (null == var0.field4191 && var0.field4188 != null && var0.field4188.method4136(1653383308)) {
         if (var0.field4188.method4141(1145062404) != null) {
            var0.field4191 = classIT.method6356(var0.field4188.method4141(1145062404), 1525019442);
         }

         var0.field4188 = null;
      }

      return var0.field4191;
   }

   classLN(String var1, classDA var2) {
      try {
         this.field4188 = var2.method2871(new URL(var1), 869537695);
      } catch (MalformedURLException var4) {
         this.field4188 = null;
      }
   }

   classLN(classEY var1) {
      this.field4188 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("ab")
   SpritePixels method7166() {
      if (null == this.field4191 && this.field4188 != null && this.field4188.method4136(2052128257)) {
         if (this.field4188.method4141(1145062404) != null) {
            this.field4191 = classIT.method6356(this.field4188.method4141(1145062404), 1867148155);
         }

         this.field4188 = null;
      }

      return this.field4191;
   }

   @ObfuscatedSignature(descriptor = "(Lln;I)Lym;")
   @ObfuscatedName("as")
   public static SpritePixels method7167(classLN var0, int var1) {
      if (var0 == null) {
         return var0.method7169(var1);
      } else {
         try {
            if (null == var0.field4191) {
               if (var1 <= -386472562) {
                  throw new IllegalStateException();
               }

               if (var0.field4188 != null) {
                  if (var1 <= -386472562) {
                     throw new IllegalStateException();
                  }

                  if (var0.field4188.method4136(1864295050)) {
                     if (var0.field4188.method4141(1145062404) != null) {
                        if (var1 <= -386472562) {
                           throw new IllegalStateException();
                        }

                        var0.field4191 = classIT.method6356(var0.field4188.method4141(1145062404), 1561807024);
                     }

                     var0.field4188 = null;
                  }
               }
            }

            return var0.field4191;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "ln.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;IB)Z")
   @ObfuscatedName("al")
   static boolean method7170(IndexDataBase var0, int var1, byte var2) {
      try {
         byte[] var3 = IndexDataBase.method11891(var0, var1, 1334120179);
         if (var3 == null) {
            if (var2 != 0) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            classYS.method13666(var3, -1108124092);
            return true;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ln.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;I)V")
   @ObfuscatedName("ee")
   static void method7171(int var0, String var1, int var2) {
      try {
         int var3 = client.field838.field1417 * -2087374717;
         int[] var4 = client.field838.field1421;
         boolean var5 = false;
         classAAE var6 = new classAAE(var1, WidgetNode.field5277);
         WorldView var7 = client.field815.method1584(client.field847 * -2034209657, (short)6359);
         WorldView var8 = var7;
         if (null == var7) {
            if (var2 <= 1518330686) {
               throw new IllegalStateException();
            } else {
               classPH.method8924(4, "", classKK.field3638 + var1 + ", current world null.", 1408645042);
            }
         } else {
            for (int var9 = 0; var9 < var3; var9++) {
               Player var10 = (Player)IndexedObjectSet.method13449(var8.field1692, var4[var9]);
               if (null != var10) {
                  if (var2 <= 1518330686) {
                     throw new IllegalStateException();
                  }

                  if (-2130951373 * client.field846 != -1315528093 * var10.field1478) {
                     if (var2 <= 1518330686) {
                        throw new IllegalStateException();
                     }

                     if (var10.field1281 != null) {
                        if (var2 <= 1518330686) {
                           return;
                        }

                        if (var10.field1281.equals(var6)) {
                           if (var2 <= 1518330686) {
                              return;
                           }

                           if (1 == var0) {
                              if (var2 <= 1518330686) {
                                 throw new IllegalStateException();
                              }

                              classJL var11 = classEF.method3863(classJS.OPPLAYER1, client.field796.field1533, (byte)24);
                              var11.field3341.method13211(0, (byte)-20);
                              var11.field3341.method13031(var4[var9], -1542382992);
                              client.field796.method3374(var11, -1301097035);
                           } else if (var0 == 4) {
                              if (var2 <= 1518330686) {
                                 throw new IllegalStateException();
                              }

                              classJL var13 = classEF.method3863(classJS.OPPLAYER4, client.field796.field1533, (byte)18);
                              var13.field3341.method13214(0, (byte)-6);
                              var13.field3341.method13031(var4[var9], -1542382992);
                              client.field796.method3374(var13, -1301097035);
                           } else if (6 == var0) {
                              if (var2 <= 1518330686) {
                                 throw new IllegalStateException();
                              }

                              classJL var14 = classEF.method3863(classJS.OPPLAYER6, client.field796.field1533, (byte)14);
                              var14.field3341.method13209(0, -1910538783);
                              classXY.method13239(var14.field3341, var4[var9], (byte)-26);
                              client.field796.method3374(var14, -1301097035);
                           } else if (7 == var0) {
                              classJL var15 = classEF.method3863(classJS.OPPLAYER7, client.field796.field1533, (byte)-75);
                              var15.field3341.method13209(0, -1910538783);
                              classXY.method13239(var15.field3341, var4[var9], (byte)12);
                              client.field796.method3374(var15, -1301097035);
                           }

                           var5 = true;
                           break;
                        }
                     }
                  }
               }
            }

            if (!var5) {
               if (var2 <= 1518330686) {
                  return;
               }

               classPH.method8924(4, "", classKK.field3638 + var1, 1289350155);
            }
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "ln.ee(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("ae")
   SpritePixels method7168() {
      if (null == this.field4191 && this.field4188 != null && this.field4188.method4136(1473792848)) {
         if (this.field4188.method4141(1145062404) != null) {
            this.field4191 = classIT.method6356(this.field4188.method4141(1145062404), 1783541196);
         }

         this.field4188 = null;
      }

      return this.field4191;
   }

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("az")
   SpritePixels method7169(int var1) {
      try {
         if (null == this.field4191) {
            if (var1 <= -386472562) {
               throw new IllegalStateException();
            }

            if (this.field4188 != null) {
               if (var1 <= -386472562) {
                  throw new IllegalStateException();
               }

               if (this.field4188.method4136(1864295050)) {
                  if (this.field4188.method4141(1145062404) != null) {
                     if (var1 <= -386472562) {
                        throw new IllegalStateException();
                     }

                     this.field4191 = classIT.method6356(this.field4188.method4141(1145062404), 1561807024);
                  }

                  this.field4188 = null;
               }
            }
         }

         return this.field4191;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ln.az(" + ')');
      }
   }
}
