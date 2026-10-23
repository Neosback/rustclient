import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ib")
public class classIB extends classIS {
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   public static final int field2929 = 16;
   @ObfuscatedName("af")
   byte field2927;
   @ObfuscatedName("ae")
   int field2928;
   @ObfuscatedName("ab")
   String field2931;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   static final int field2930 = 1076101408;
   @ObfuscatedName("az")
   int field2926;
   @ObfuscatedSignature(descriptor = "Lrw;")
   @ObfuscatedName("ec")
   static FriendsChatManager field2933;
   @ToRemove(unused = "true")
   @ObfuscatedName("bk")
   static final String field2932 = "d";

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lff;[I[FIIF)V")
   @ObfuscatedName("vp")
   public static void method6176(classFF var0, int[] var1, float[] var2, int var3, int var4, float var5) {
      classFF.method4660(var0, var1, var2, var3, var0.field2375[var4], var5, classFF.method4653(var2[var3], var5));
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod507(classXY var1, int var2) {
      try {
         var1.method13095(-346779531);
         this.field2926 = var1.method13099(1225577849) * -1162888885;
         this.field2927 = var1.method13097((byte)17);
         this.field2928 = var1.method13099(1375876317) * 1069804709;
         classXY.method13115(var1, 961837390);
         this.field2931 = var1.method13127(2084487682);
         var1.method13095(-346779531);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ib.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;I)V")
   @ObfuscatedName("af")
   @Override
   void vmethod509(ClanChannel var1, int var2) {
      try {
         ClanChannelMember var3 = (ClanChannelMember)var1.field2953.get(287508579 * this.field2926);
         var3.field2839 = this.field2927;
         var3.field2840 = this.field2928 * 735809615;
         var3.field2844 = new classAAE(this.field2931);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ib.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod508(ClanChannel var1) {
      ClanChannelMember var2 = (ClanChannelMember)var1.field2953.get(287508579 * this.field2926);
      var2.field2839 = this.field2927;
      var2.field2840 = this.field2928 * 735809615;
      var2.field2844 = new classAAE(this.field2931);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod506(classXY var1) {
      var1.method13095(-346779531);
      this.field2926 = var1.method13099(447793970) * -1162888885;
      this.field2927 = var1.method13097((byte)17);
      this.field2928 = var1.method13099(1660926076) * -1340994134;
      classXY.method13115(var1, 1430153815);
      this.field2931 = var1.method13127(531729326);
      var1.method13095(-346779531);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod505(classXY var1) {
      var1.method13095(-346779531);
      this.field2926 = var1.method13099(-1090619147) * -1162888885;
      this.field2927 = var1.method13097((byte)17);
      this.field2928 = var1.method13099(1897556601) * -1804201015;
      classXY.method13115(var1, 938421225);
      this.field2931 = var1.method13127(-153802279);
      var1.method13095(-346779531);
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod511(ClanChannel var1) {
      ClanChannelMember var2 = (ClanChannelMember)var1.field2953.get(287508579 * this.field2926);
      var2.field2839 = this.field2927;
      var2.field2840 = this.field2928 * 735809615;
      var2.field2844 = new classAAE(this.field2931);
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod510(ClanChannel var1) {
      ClanChannelMember var2 = (ClanChannelMember)var1.field2953.get(287508579 * this.field2926);
      var2.field2839 = this.field2927;
      var2.field2840 = this.field2928 * 735809615;
      var2.field2844 = new classAAE(this.field2931);
   }

   classIB(classIX var1) {
      this.this$0 = var1;
      this.field2926 = 1162888885;
   }

   @ObfuscatedSignature(descriptor = "(CI)Z")
   @ObfuscatedName("ae")
   public static boolean method6177(char var0, int var1) {
      try {
         if (var0 >= ' ') {
            if (var1 == 229606951) {
               throw new IllegalStateException();
            }

            if (var0 < 127) {
               return true;
            }
         }

         if (var0 > 127) {
            if (var1 == 229606951) {
               throw new IllegalStateException();
            }

            if (var0 < 160) {
               return true;
            }

            if (var1 == 229606951) {
               throw new IllegalStateException();
            }
         }

         if (var0 > 160) {
            if (var1 == 229606951) {
               throw new IllegalStateException();
            }

            if (var0 <= 255) {
               if (var1 == 229606951) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         if (var0 != 0) {
            if (var1 == 229606951) {
               throw new IllegalStateException();
            }

            char[] var2 = classOH.field4867;

            for (int var3 = 0; var3 < var2.length; var3++) {
               if (var1 == 229606951) {
                  throw new IllegalStateException();
               }

               char var4 = var2[var3];
               if (var0 == var4) {
                  if (var1 == 229606951) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ib.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;I)V")
   @ObfuscatedName("af")
   public static void method6175(PacketBuffer var0, int var1) {
      try {
         classZQ var2 = (classZQ)classZJ.field7187.method8208();
         if (var2 == null) {
            if (var1 != 1238864384) {
               throw new IllegalStateException();
            }
         } else {
            int var3 = 702114061 * var0.field6953;
            var0.method13037(var2.field7234 * -1199814365, 1528983391);

            for (int var4 = 0; var4 < var2.field7233 * 569821081; var4++) {
               if (var1 != 1238864384) {
                  return;
               }

               if (0 != var2.field7235[var4]) {
                  if (var1 != 1238864384) {
                     throw new IllegalStateException();
                  }

                  classXY.method13029(var0, var2.field7235[var4], (byte)-12);
               } else {
                  try {
                     int var5 = var2.field7236[var4];
                     if (0 == var5) {
                        if (var1 != 1238864384) {
                           throw new IllegalStateException();
                        }

                        Field var6 = var2.field7238[var4];
                        int var7 = var6.getInt(null);
                        classXY.method13029(var0, 0, (byte)-78);
                        var0.method13037(var7, 1670772688);
                     } else if (1 == var5) {
                        if (var1 != 1238864384) {
                           throw new IllegalStateException();
                        }

                        Field var24 = var2.field7238[var4];
                        var24.setInt(null, var2.field7237[var4]);
                        classXY.method13029(var0, 0, (byte)-75);
                     } else if (var5 == 2) {
                        if (var1 != 1238864384) {
                           throw new IllegalStateException();
                        }

                        Field var25 = var2.field7238[var4];
                        int var28 = var25.getModifiers();
                        classXY.method13029(var0, 0, (byte)-27);
                        var0.method13037(var28, -242961672);
                     }

                     if (var5 != 3) {
                        if (4 == var5) {
                           Method var27 = var2.field7239[var4];
                           int var30 = var27.getModifiers();
                           classXY.method13029(var0, 0, (byte)-54);
                           var0.method13037(var30, 1483575624);
                        }
                     } else {
                        if (var1 != 1238864384) {
                           throw new IllegalStateException();
                        }

                        Method var26 = var2.field7239[var4];
                        byte[][] var29 = var2.field7240[var4];
                        Object[] var8 = new Object[var29.length];

                        for (int var9 = 0; var9 < var29.length; var9++) {
                           if (var1 != 1238864384) {
                              return;
                           }

                           ObjectInputStream var10 = new ObjectInputStream(new ByteArrayInputStream(var29[var9]));
                           var8[var9] = var10.readObject();
                        }

                        Object var31 = var26.invoke(null, var8);
                        if (null == var31) {
                           if (var1 != 1238864384) {
                              throw new IllegalStateException();
                           }

                           classXY.method13029(var0, 0, (byte)-59);
                        } else if (var31 instanceof Number) {
                           if (var1 != 1238864384) {
                              throw new IllegalStateException();
                           }

                           classXY.method13029(var0, 1, (byte)-105);
                           classXY.method13047(var0, ((Number)var31).longValue());
                        } else if (var31 instanceof String) {
                           classXY.method13029(var0, 2, (byte)-1);
                           var0.method13054((String)var31, (short)29215);
                        } else {
                           classXY.method13029(var0, 4, (byte)-93);
                        }
                     }
                  } catch (ClassNotFoundException var11) {
                     classXY.method13029(var0, -10, (byte)-62);
                  } catch (InvalidClassException var12) {
                     classXY.method13029(var0, -11, (byte)-60);
                  } catch (StreamCorruptedException var13) {
                     classXY.method13029(var0, -12, (byte)-29);
                  } catch (OptionalDataException var14) {
                     classXY.method13029(var0, -13, (byte)-75);
                  } catch (IllegalAccessException var15) {
                     classXY.method13029(var0, -14, (byte)-47);
                  } catch (IllegalArgumentException var16) {
                     classXY.method13029(var0, -15, (byte)-73);
                  } catch (InvocationTargetException var17) {
                     classXY.method13029(var0, -16, (byte)-62);
                  } catch (SecurityException var18) {
                     classXY.method13029(var0, -17, (byte)-124);
                  } catch (IOException var19) {
                     classXY.method13029(var0, -18, (byte)-1);
                  } catch (NullPointerException var20) {
                     classXY.method13029(var0, -19, (byte)-95);
                  } catch (Exception var21) {
                     classXY.method13029(var0, -20, (byte)-14);
                  } catch (Throwable var22) {
                     classXY.method13029(var0, -21, (byte)-53);
                  }
               }
            }

            classXY.method13202(var0, var3, -1669758824);
            var2.vmethod398();
         }
      } catch (RuntimeException var23) {
         throw classEG.method3899(var23, "ib.af(" + ')');
      }
   }
}
