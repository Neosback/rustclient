import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ly")
public class classLY {
   @ObfuscatedSignature(descriptor = "Lrj;")
   @ObfuscatedName("ay")
   classRJ field4436;
   @ObfuscatedName("az")
   boolean[] field4431;
   @ObfuscatedSignature(descriptor = "[[Llw;")
   @ObfuscatedName("ax")
   public Widget[][] field4438;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ab")
   IndexDataBase field4432;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ag")
   IndexDataBase field4435;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("aj")
   NodeCache field4430;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("af")
   IndexDataBase field4433;
   @ObfuscatedName("ac")
   Map field4439 = new HashMap();
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("aa")
   NodeCache field4428 = new NodeCache(200);
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ae")
   IndexDataBase field4437;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("al")
   NodeCache field4427;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("as")
   IndexDataBase field4440;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ao")
   NodeCache field4429 = new NodeCache(50);
   @ObfuscatedSignature(descriptor = "Lrj;")
   @ObfuscatedName("aq")
   classRJ field4434;
   @ObfuscatedName("rj")
   static Image field4441;

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ao")
   public boolean method7439(int var1) {
      return this.method7425(var1, -56682132);
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("eo")
   public Widget method7440(int var1) {
      return this.method7421(var1, (byte)126);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ae")
   public boolean method7425(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var1 < this.field4438.length) {
               if (this.field4431[var1]) {
                  if (var2 <= -324749371) {
                     throw new IllegalStateException();
                  }

                  return true;
               }

               if (!this.field4433.method11940(var1, -906797091)) {
                  if (var2 <= -324749371) {
                     throw new IllegalStateException();
                  }

                  return false;
               }

               int var3 = this.field4433.method11943(var1, -1891658016);
               if (var3 == 0) {
                  if (var2 <= -324749371) {
                     throw new IllegalStateException();
                  }

                  this.field4431[var1] = true;
                  return true;
               }

               if (null == this.field4438[var1]) {
                  if (var2 <= -324749371) {
                     throw new IllegalStateException();
                  }

                  this.field4438[var1] = new Widget[var3];
               }

               for (int var4 = 0; var4 < var3; var4++) {
                  if (var2 <= -324749371) {
                     throw new IllegalStateException();
                  }

                  if (null == this.field4438[var1][var4]) {
                     if (var2 <= -324749371) {
                        throw new IllegalStateException();
                     }

                     byte[] var5 = this.field4433.method11942(var1, var4, -600631346);
                     if (null != var5) {
                        if (var2 <= -324749371) {
                           throw new IllegalStateException();
                        }

                        Widget var6 = new Widget();
                        this.field4438[var1][var4] = var6;
                        var6.field4327 = (var4 + (var1 << 16)) * 519254441;
                        if (-1 == var5[0]) {
                           if (var2 <= -324749371) {
                              throw new IllegalStateException();
                           }

                           Widget.method7219(var6, new classXY(var5), 1218236279);
                        } else {
                           var6.method7215(new classXY(var5), (byte)-16);
                        }

                        if (null != this.field4437) {
                           if (var2 <= -324749371) {
                              throw new IllegalStateException();
                           }

                           byte[] var7 = this.field4437.method11942(var1, var4, -608292274);
                           if (null != var7) {
                              if (var2 <= -324749371) {
                                 throw new IllegalStateException();
                              }

                              if (var7.length > 0) {
                                 if (var2 <= -324749371) {
                                    throw new IllegalStateException();
                                 }

                                 Widget.method7223(var6, new classXY(var7), -1144744404);
                                 this.field4439.put(var6.field4330, var1);
                              }
                           }
                        }
                     }
                  }
               }

               for (int var9 = 0; var9 < var3; var9++) {
                  Widget var10 = this.field4438[var1][var9];
                  if (null != var10) {
                     if (var2 <= -324749371) {
                        throw new IllegalStateException();
                     }

                     if (-1502647899 * var10.field4340 != -1) {
                        if (var2 <= -324749371) {
                           throw new IllegalStateException();
                        }

                        Widget var11 = this.field4438[var1][-1502647899 * var10.field4340 & 65535];
                        if (var9 > var11.field4264 * 379541057) {
                           var11.field4264 = -1479820863 * var9;
                        }

                        if (var9 < var11.field4263 * -1210177335) {
                           var11.field4263 = var9 * -452038279;
                        }
                     }
                  }
               }

               this.field4431[var1] = true;
               return true;
            }

            if (var2 <= -324749371) {
               throw new IllegalStateException();
            }
         }

         return false;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ly.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   public void method7433() {
      this.field4428.method6468();
      this.field4429.method6468();
      this.field4427.method6468();
      this.field4430.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lly;I)Z")
   @ObfuscatedName("vq")
   public static boolean method7426(classLY var0, int var1) {
      if (var0 == null) {
         var0.method7432(var1);
      }

      if (var1 >= 0 && var1 < var0.field4438.length) {
         if (var0.field4431[var1]) {
            return true;
         } else if (!var0.field4433.method11940(var1, -906797091)) {
            return false;
         } else {
            int var2 = var0.field4433.method11943(var1, -1881368475);
            if (var2 == 0) {
               var0.field4431[var1] = true;
               return true;
            } else {
               if (null == var0.field4438[var1]) {
                  var0.field4438[var1] = new Widget[var2];
               }

               for (int var3 = 0; var3 < var2; var3++) {
                  if (null == var0.field4438[var1][var3]) {
                     byte[] var4 = var0.field4433.method11942(var1, var3, -1622891954);
                     if (null != var4) {
                        Widget var5 = new Widget();
                        var0.field4438[var1][var3] = var5;
                        var5.field4327 = (var3 + (var1 << 16)) * 519254441;
                        if (-1 == var4[0]) {
                           Widget.method7219(var5, new classXY(var4), 1218236279);
                        } else {
                           var5.method7215(new classXY(var4), (byte)-71);
                        }

                        if (null != var0.field4437) {
                           byte[] var6 = var0.field4437.method11942(var1, var3, -1569057326);
                           if (null != var6 && var6.length > 0) {
                              Widget.method7223(var5, new classXY(var6), 394206728);
                              var0.field4439.put(var5.field4330, var1);
                           }
                        }
                     }
                  }
               }

               for (int var7 = 0; var7 < var2; var7++) {
                  Widget var8 = var0.field4438[var1][var7];
                  if (null != var8 && -1502647899 * var8.field4340 != -1) {
                     Widget var9 = var0.field4438[var1][-1502647899 * var8.field4340 & 65535];
                     if (var7 > var9.field4264 * 379541057) {
                        var9.field4264 = -1479820863 * var7;
                     }

                     if (var7 < var9.field4263 * -1210177335) {
                        var9.field4263 = var7 * -452038279;
                     }
                  }
               }

               var0.field4431[var1] = true;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("iw")
   public boolean method7427(int var1) {
      if (var1 >= 0 && var1 < this.field4438.length) {
         if (this.field4431[var1]) {
            return true;
         } else if (!this.field4433.method11940(var1, -906797091)) {
            return false;
         } else {
            int var2 = this.field4433.method11943(var1, -254740276);
            if (var2 == 0) {
               this.field4431[var1] = true;
               return true;
            } else {
               if (null == this.field4438[var1]) {
                  this.field4438[var1] = new Widget[var2];
               }

               for (int var3 = 0; var3 < var2; var3++) {
                  if (null == this.field4438[var1][var3]) {
                     byte[] var4 = this.field4433.method11942(var1, var3, -1308427039);
                     if (null != var4) {
                        Widget var5 = new Widget();
                        this.field4438[var1][var3] = var5;
                        var5.field4327 = (var3 + (var1 << 16)) * 519254441;
                        if (-1 == var4[0]) {
                           Widget.method7219(var5, new classXY(var4), 1218236279);
                        } else {
                           var5.method7215(new classXY(var4), (byte)-119);
                        }

                        if (null != this.field4437) {
                           byte[] var6 = this.field4437.method11942(var1, var3, -1471794850);
                           if (null != var6 && var6.length > 0) {
                              Widget.method7223(var5, new classXY(var6), -1161191361);
                              this.field4439.put(var5.field4330, var1);
                           }
                        }
                     }
                  }
               }

               for (int var7 = 0; var7 < var2; var7++) {
                  Widget var8 = this.field4438[var1][var7];
                  if (null != var8 && -1502647899 * var8.field4340 != -1) {
                     Widget var9 = this.field4438[var1][-1502647899 * var8.field4340 & -2043971647];
                     if (var7 > var9.field4264 * 306317772) {
                        var9.field4264 = -1479820863 * var7;
                     }

                     if (var7 < var9.field4263 * -1210177335) {
                        var9.field4263 = var7 * -452038279;
                     }
                  }
               }

               this.field4431[var1] = true;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Llw;")
   @ObfuscatedName("az")
   public Widget method7421(int var1, byte var2) {
      try {
         int var3 = var1 >> 16;
         int var4 = var1 & 65535;
         if (this.field4438[var3] != null) {
            if (var2 <= 16) {
               throw new IllegalStateException();
            }

            if (this.field4438[var3][var4] != null) {
               return this.field4438[var3][var4];
            }

            if (var2 <= 16) {
               throw new IllegalStateException();
            }
         }

         boolean var5 = this.method7425(var3, 870024905);
         return !var5 ? null : this.field4438[var3][var4];
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ly.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ab")
   public void method7429(int var1, int var2) {
      try {
         if (-1 == var1) {
            if (var2 == -2026063788) {
               throw new IllegalStateException();
            }
         } else if (this.field4431[var1]) {
            this.field4433.method11941(var1, -1338922845);
            if (null == this.field4438[var1]) {
               if (var2 != -2026063788) {
                  ;
               }
            } else {
               for (int var3 = 0; var3 < this.field4438[var1].length; var3++) {
                  if (var2 == -2026063788) {
                     throw new IllegalStateException();
                  }

                  if (this.field4438[var1][var3] != null) {
                     if (var2 == -2026063788) {
                        throw new IllegalStateException();
                     }

                     this.field4438[var1][var3] = null;
                  }
               }

               this.field4438[var1] = null;
               this.field4431[var1] = false;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ly.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lly;I)V")
   @ObfuscatedName("tk")
   public static void method7430(classLY var0, int var1) {
      if (var0 == null) {
         var0.method7424(var1, var1);
      }

      if (-1 != var1) {
         if (var0.field4431[var1]) {
            var0.field4433.method11941(var1, 1220924443);
            if (null != var0.field4438[var1]) {
               for (int var2 = 0; var2 < var0.field4438[var1].length; var2++) {
                  if (var0.field4438[var1][var2] != null) {
                     var0.field4438[var1][var2] = null;
                  }
               }

               var0.field4438[var1] = null;
               var0.field4431[var1] = false;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ag")
   public void method7434(byte var1) {
      try {
         this.field4428.method6468();
         this.field4429.method6468();
         this.field4427.method6468();
         this.field4430.method6468();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ly.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)Llw;")
   @ObfuscatedName("af")
   public Widget method7422(int var1, int var2, byte var3) {
      try {
         Widget var4 = this.method7421(var1, (byte)82);
         if (-1 == var2) {
            if (var3 >= 32) {
               throw new IllegalStateException();
            } else {
               return var4;
            }
         } else {
            if (null != var4) {
               if (var3 >= 32) {
                  throw new IllegalStateException();
               }

               if (null != var4.field4338) {
                  if (var3 >= 32) {
                     throw new IllegalStateException();
                  }

                  if (var2 < var4.field4338.length) {
                     return var4.field4338[var2];
                  }

                  if (var3 >= 32) {
                     throw new IllegalStateException();
                  }
               }
            }

            return null;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ly.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lly;I)V")
   @ObfuscatedName("wv")
   public static void method7431(classLY var0, int var1) {
      if (var0 == null) {
         var0.method7428(var1);
      }

      if (-1 != var1) {
         if (var0.field4431[var1]) {
            var0.field4433.method11941(var1, 1588962690);
            if (null != var0.field4438[var1]) {
               for (int var2 = 0; var2 < var0.field4438[var1].length; var2++) {
                  if (var0.field4438[var1][var2] != null) {
                     var0.field4438[var1][var2] = null;
                  }
               }

               var0.field4438[var1] = null;
               var0.field4431[var1] = false;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Llw;")
   @ObfuscatedName("ax")
   public Widget method7423(int var1, int var2) {
      Widget var3 = this.method7421(var1, (byte)88);
      if (-1 == var2) {
         return var3;
      } else {
         return null != var3 && null != var3.field4338 && var2 < var3.field4338.length ? var3.field4338[var2] : null;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Llw;")
   @ObfuscatedName("as")
   public Widget method7424(int var1, int var2) {
      Widget var3 = this.method7421(var1, (byte)19);
      if (-1 == var2) {
         return var3;
      } else {
         return null != var3 && null != var3.field4338 && var2 < var3.field4338.length ? var3.field4338[var2] : null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ac")
   public boolean method7428(int var1) {
      if (var1 >= 0 && var1 < this.field4438.length) {
         if (this.field4431[var1]) {
            return true;
         } else if (!this.field4433.method11940(var1, -906797091)) {
            return false;
         } else {
            int var2 = this.field4433.method11943(var1, 1373563660);
            if (var2 == 0) {
               this.field4431[var1] = true;
               return true;
            } else {
               if (null == this.field4438[var1]) {
                  this.field4438[var1] = new Widget[var2];
               }

               for (int var3 = 0; var3 < var2; var3++) {
                  if (null == this.field4438[var1][var3]) {
                     byte[] var4 = this.field4433.method11942(var1, var3, -1318908219);
                     if (null != var4) {
                        Widget var5 = new Widget();
                        this.field4438[var1][var3] = var5;
                        var5.field4327 = (var3 + (var1 << 16)) * 519254441;
                        if (-1 == var4[0]) {
                           Widget.method7219(var5, new classXY(var4), 1218236279);
                        } else {
                           var5.method7215(new classXY(var4), (byte)-6);
                        }

                        if (null != this.field4437) {
                           byte[] var6 = this.field4437.method11942(var1, var3, -1233602195);
                           if (null != var6 && var6.length > 0) {
                              Widget.method7223(var5, new classXY(var6), -472614399);
                              this.field4439.put(var5.field4330, var1);
                           }
                        }
                     }
                  }
               }

               for (int var7 = 0; var7 < var2; var7++) {
                  Widget var8 = this.field4438[var1][var7];
                  if (null != var8 && -1502647899 * var8.field4340 != -1) {
                     Widget var9 = this.field4438[var1][-1502647899 * var8.field4340 & 65535];
                     if (var7 > var9.field4264 * 379541057) {
                        var9.field4264 = -1479820863 * var7;
                     }

                     if (var7 < var9.field4263 * -1210177335) {
                        var9.field4263 = var7 * -452038279;
                     }
                  }
               }

               this.field4431[var1] = true;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   public void method7432(int var1) {
      if (-1 != var1) {
         if (this.field4431[var1]) {
            this.field4433.method11941(var1, 1815138758);
            if (null != this.field4438[var1]) {
               for (int var2 = 0; var2 < this.field4438[var1].length; var2++) {
                  if (this.field4438[var1][var2] != null) {
                     this.field4438[var1][var2] = null;
                  }
               }

               this.field4438[var1] = null;
               this.field4431[var1] = false;
            }
         }
      }
   }

   protected classLY(IndexDataBase var1, IndexDataBase var2, IndexDataBase var3, IndexDataBase var4, IndexDataBase var5) {
      this.field4427 = new NodeCache(20);
      this.field4430 = new NodeCache(8);
      this.field4436 = new classRJ(10, classRB.field5633);
      this.field4434 = new classRJ(10, classRB.field5633);
      int var6 = 0;
      if (null != var1) {
         this.field4433 = var1;
         this.field4432 = var2;
         this.field4435 = var3;
         this.field4440 = var4;
         this.field4437 = var5;
         var6 = this.field4433.method11905((byte)1);
      }

      this.field4438 = new Widget[var6][];
      this.field4431 = new boolean[var6];
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public void method7435() {
      this.field4428.method6468();
      this.field4429.method6468();
      this.field4427.method6468();
      this.field4430.method6468();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   public void method7436() {
      this.field4428.method6468();
      this.field4429.method6468();
      this.field4427.method6468();
      this.field4430.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;)I")
   @ObfuscatedName("de")
   public static int method7438(NPCComposition var0) {
      return 1525345516 * var0.field5285;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   public void method7437() {
      this.field4428.method6468();
      this.field4429.method6468();
      this.field4427.method6468();
      this.field4430.method6468();
   }
}
