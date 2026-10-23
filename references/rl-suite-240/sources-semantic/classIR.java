import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ir")
public class classIR extends classIS {
   @ObfuscatedName("ae")
   byte field3045;
   @ObfuscatedName("af")
   int field3046;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   public static final int field3048 = 44;
   @ObfuscatedName("az")
   String field3044;
   @ObfuscatedName("nk")
   static int field3049;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   static final int field3047 = 62;

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod510(ClanChannel var1) {
      ClanChannelMember var2 = new ClanChannelMember();
      var2.field2844 = new classAAE(this.field3044);
      var2.field2840 = this.field3046 * -1335973298;
      var2.field2839 = this.field3045;
      var1.method6218(var2, (byte)50);
   }

   @ObfuscatedSignature(descriptor = "(Lie;I)V")
   @ObfuscatedName("af")
   @Override
   void vmethod509(ClanChannel var1, int var2) {
      try {
         ClanChannelMember var3 = new ClanChannelMember();
         var3.field2844 = new classAAE(this.field3044);
         var3.field2840 = this.field3046 * -1130778131;
         var3.field2839 = this.field3045;
         var1.method6218(var3, (byte)50);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ir.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;III)[Lym;")
   @ObfuscatedName("af")
   public static SpritePixels[] method6349(IndexDataBase var0, int var1, int var2, int var3) {
      try {
         if (!classHK.method6100(var0, var1, var2, (byte)-24)) {
            if (var3 != -1253680523) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            SpritePixels[] var5 = new SpritePixels[1616155503 * classYL.field6999];

            for (int var6 = 0; var6 < classYL.field6999 * 1616155503; var6++) {
               if (var3 != -1253680523) {
                  throw new IllegalStateException();
               }

               SpritePixels var7 = var5[var6] = new SpritePixels();
               var7.field7004 = -1073038619 * classYL.field6998;
               var7.field7006 = classYL.field6997 * -1062567517;
               var7.field7008 = classYL.field7000[var6];
               var7.field7009 = classYL.field7001[var6];
               var7.field7005 = EntityOps.field5369[var6];
               var7.field7007 = classGG.field2576[var6];
               int var8 = var7.field7007 * var7.field7005;
               byte[] var9 = classYL.field7002[var6];
               var7.field7003 = new int[var8];

               for (int var10 = 0; var10 < var8; var10++) {
                  if (var3 != -1253680523) {
                     throw new IllegalStateException();
                  }

                  int var11 = var9[var10] & 255;
                  var7.field7003[var10] = classRB.field5637[var11];
               }
            }

            classVZ.method12347((byte)-10);
            return var5;
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "ir.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;Ljava/lang/String;I)V")
   @ObfuscatedName("we")
   public static void method6354(Widget var0, String var1, int var2) {
      if (var0 == null) {
         var0.getScrollY();
      }

      if (var0.field4373 * -1011688428 == 11) {
         if (null == var0.field4266) {
            var0.method7286(-1634155941);
         }

         var0.field4266.put(var1, var2);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod506(classXY var1) {
      if (var1.method13095(-346779531) != 104191455) {
         var1.field6953 -= 796120900;
         classXY.method13115(var1, 302631968);
      }

      this.field3044 = var1.method13125((byte)-1);
      this.field3046 = var1.method13099(-1035866912) * -680867885;
      this.field3045 = var1.method13097((byte)17);
      classXY.method13115(var1, -1015932687);
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod508(ClanChannel var1) {
      ClanChannelMember var2 = new ClanChannelMember();
      var2.field2844 = new classAAE(this.field3044);
      var2.field2840 = this.field3046 * -1130778131;
      var2.field2839 = this.field3045;
      var1.method6218(var2, (byte)50);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod507(classXY var1, int var2) {
      try {
         if (var1.method13095(-346779531) != 255) {
            if (var2 >= 1072982787) {
               throw new IllegalStateException();
            }

            var1.field6953 -= -1095856699;
            classXY.method13115(var1, -954841296);
         }

         this.field3044 = var1.method13125((byte)17);
         this.field3046 = var1.method13099(692985437) * -1116245705;
         this.field3045 = var1.method13097((byte)17);
         classXY.method13115(var1, 1821852560);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ir.az(" + ')');
      }
   }

   classIR(classIX var1) {
      this.this$0 = var1;
      this.field3044 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod511(ClanChannel var1) {
      ClanChannelMember var2 = new ClanChannelMember();
      var2.field2844 = new classAAE(this.field3044);
      var2.field2840 = this.field3046 * -1130778131;
      var2.field2839 = this.field3045;
      var1.method6218(var2, (byte)50);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ag")
   public static boolean method6352(int var0) {
      try {
         if (!classNP.field4698.isEmpty()) {
            if (var0 <= -544099772) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            if (!classNP.field4696.isEmpty()) {
               if (var0 <= -544099772) {
                  throw new IllegalStateException();
               }

               if (classNP.field4696.get(0) != null) {
                  if (var0 <= -544099772) {
                     throw new IllegalStateException();
                  }

                  if (((MidiRequest)classNP.field4696.get(0)).field4639 != null) {
                     return classNU.method8085(((MidiRequest)classNP.field4696.get(0)).field4639, (byte)10);
                  }
               }
            }

            return false;
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ir.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Loi;")
   @ObfuscatedName("az")
   public static WorldEntityConfig method6351(int var0, int var1) {
      try {
         WorldEntityConfig var2 = (WorldEntityConfig)NodeCache.method6460(WorldEntityConfig.field4870, var0);
         if (var2 != null) {
            if (var1 <= -1417546985) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classCB.field649.method11942(72, var0, -1029003973);
            var2 = new WorldEntityConfig(new classXY(var3), var0);
            WorldEntityConfig.field4870.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ir.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lry;Ljava/lang/String;ZB)Ljava/lang/String;")
   @ObfuscatedName("ax")
   static String method6353(classRY var0, String var1, boolean var2, byte var3) {
      try {
         if (var0.field5881 * -3946776555521172023L >= 0L) {
            if (var3 <= 0) {
               throw new IllegalStateException();
            }

            if (var0.field5879 * 5030926867112396125L >= 0L) {
               if (var3 <= 0) {
                  throw new IllegalStateException();
               }

               if (-5305962339389965695L * var0.field5878 >= 0L) {
                  if (var3 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (var0.field5877 * 5393848908079016329L >= 0L) {
                     if (-6607959019370912109L * var0.field5880 >= 0L) {
                        StringBuilder var4 = new StringBuilder();
                        boolean var5 = false;
                        if (var1.equals("d")) {
                           if (var3 <= 0) {
                              throw new IllegalStateException();
                           }

                           if (var0.field5881 * -3946776555521172023L > 0L) {
                              if (var3 <= 0) {
                                 throw new IllegalStateException();
                              }

                              var4.append(-3946776555521172023L * var0.field5881);
                              var5 = true;
                           }
                        }

                        label88: {
                           if (!var1.equals("d")) {
                              if (var3 <= 0) {
                                 throw new IllegalStateException();
                              }

                              if (!var1.equals("h")) {
                                 break label88;
                              }

                              if (var3 <= 0) {
                                 throw new IllegalStateException();
                              }
                           }

                           if (var5) {
                              if (var3 <= 0) {
                                 throw new IllegalStateException();
                              }

                              var4.append(':').append(classGK.method5516(var0.field5879 * 5030926867112396125L));
                           } else if (5030926867112396125L * var0.field5879 > 0L) {
                              if (var3 <= 0) {
                                 throw new IllegalStateException();
                              }

                              var4.append(5030926867112396125L * var0.field5879);
                              var5 = true;
                           }
                        }

                        if (var5) {
                           if (var3 <= 0) {
                              throw new IllegalStateException();
                           }

                           var4.append(':').append(classGK.method5516(-5305962339389965695L * var0.field5878));
                        } else {
                           var4.append(var0.field5878 * -5305962339389965695L);
                           var5 = true;
                        }

                        var4.append(':').append(classGK.method5516(var0.field5877 * 5393848908079016329L));
                        if (var2) {
                           if (var3 <= 0) {
                              throw new IllegalStateException();
                           }

                           var4.append('.').append(classGK.method5516(var0.field5880 * -6607959019370912109L / 10L));
                        }

                        return var4.toString();
                     }

                     if (var3 <= 0) {
                        throw new IllegalStateException();
                     }
                  }
               }
            }
         }

         return "-";
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ir.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod505(classXY var1) {
      if (var1.method13095(-346779531) != -303577241) {
         var1.field6953 -= -1095856699;
         classXY.method13115(var1, -1136569179);
      }

      this.field3044 = var1.method13125((byte)-11);
      this.field3046 = var1.method13099(348431888) * -1116245705;
      this.field3045 = var1.method13097((byte)17);
      classXY.method13115(var1, 1110668784);
   }

   @ObfuscatedSignature(descriptor = "(IZII)V")
   @ObfuscatedName("az")
   public static final void method6350(int var0, boolean var1, int var2, int var3) {
      try {
         if (var0 >= 8000) {
            if (var0 <= 48000) {
               WidgetConfigNode.field4136 = var0 * -411122787;
               classKG.field3581 = var1;
               classKI.field3593 = var2 * 807699829;
               return;
            }

            if (var3 != 561350655) {
               throw new IllegalStateException();
            }
         }

         throw new IllegalArgumentException();
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ir.az(" + ')');
      }
   }
}
