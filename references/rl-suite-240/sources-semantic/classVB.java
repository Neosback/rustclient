import java.io.IOException;
import java.util.zip.CRC32;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vb")
public class classVB {
   @ObfuscatedName("ac")
   public int field6461;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6477 = 1;
   @ObfuscatedName("al")
   long field6479;
   @ObfuscatedName("aw")
   int field6466;
   @ObfuscatedName("ax")
   public int field6456 = 0;
   @ObfuscatedSignature(descriptor = "Lyi;")
   @ObfuscatedName("aj")
   HashTable field6464;
   @ObfuscatedSignature(descriptor = "Luk;")
   @ObfuscatedName("aa")
   classUK field6481;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field6478 = 0;
   @ObfuscatedName("ao")
   int field6457;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field6475 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6476 = 2;
   @ObfuscatedSignature(descriptor = "Lyi;")
   @ObfuscatedName("aq")
   HashTable field6472;
   @ObfuscatedName("ad")
   int field6470;
   @ObfuscatedName("ai")
   int field6465;
   @ObfuscatedSignature(descriptor = "Lyi;")
   @ObfuscatedName("au")
   HashTable field6463;
   @ObfuscatedSignature(descriptor = "Lnh;")
   @ObfuscatedName("ap")
   classNH field6462;
   @ObfuscatedSignature(descriptor = "Lyi;")
   @ObfuscatedName("ar")
   HashTable field6474;
   @ObfuscatedSignature(descriptor = "[Lvp;")
   @ObfuscatedName("bx")
   classVP[] field6458;
   @ObfuscatedName("ak")
   boolean field6484;
   @ObfuscatedSignature(descriptor = "Luc;")
   @ObfuscatedName("av")
   classUC field6480;
   @ObfuscatedSignature(descriptor = "Lxy;")
   @ObfuscatedName("at")
   classXY field6467;
   @ObfuscatedSignature(descriptor = "Lxy;")
   @ObfuscatedName("an")
   classXY field6482;
   @ObfuscatedName("am")
   int field6468;
   @ObfuscatedName("ah")
   CRC32 field6469;
   @ObfuscatedSignature(descriptor = "Lxy;")
   @ObfuscatedName("bn")
   classXY field6483;
   @ObfuscatedName("ay")
   int field6460;
   @ObfuscatedName("bc")
   int field6459;
   @ObfuscatedName("bp")
   int field6471;
   @ObfuscatedName("br")
   byte field6473;

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   public boolean method11955(byte var1) {
      try {
         long var2 = classDD.method2988(16711680);
         int var4 = (int)(var2 - this.field6479 * -2021231288791816661L);
         this.field6479 = -8371170626432079741L * var2;
         if (var4 > 200) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            var4 = 200;
         }

         this.field6457 += 1543721085 * var4;
         if (-1735349139 * this.field6466 == 0) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            if (this.field6470 * 403165785 == 0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               if (136778065 * this.field6465 == 0) {
                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (-1026186423 * this.field6460 == 0) {
                     return true;
                  }
               }
            }
         }

         if (this.field6481 == null) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            try {
               if (-841176875 * this.field6457 > 30000) {
                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  } else {
                     throw new IOException();
                  }
               } else {
                  while (403165785 * this.field6470 < 200 && this.field6460 * -1026186423 > 0) {
                     if (var1 <= 0) {
                        throw new IllegalStateException();
                     }

                     classUC var5 = (classUC)this.field6464.method13423();
                     classXY var6 = new classXY(4);
                     classXY.method13029(var6, 1, (byte)-76);
                     var6.method13032((int)var5.field6550, (byte)-46);
                     this.field6481.vmethod597(var6.field6952, 0, 4, -1032071721);
                     this.field6472.method13418(var5, var5.field6550);
                     this.field6460 -= 1927593721;
                     this.field6470 += 588928489;
                  }

                  while (-1735349139 * this.field6466 < 200) {
                     if (var1 <= 0) {
                        throw new IllegalStateException();
                     }

                     if (136778065 * this.field6465 <= 0) {
                        break;
                     }

                     if (var1 <= 0) {
                        throw new IllegalStateException();
                     }

                     classUC var21 = (classUC)this.field6462.method7894();
                     classXY var23 = new classXY(4);
                     classXY.method13029(var23, 0, (byte)-64);
                     var23.method13032((int)var21.field6550, (byte)-75);
                     this.field6481.vmethod597(var23.field6952, 0, 4, -1032071721);
                     var21.method12035();
                     this.field6474.method13418(var21, var21.field6550);
                     this.field6465 -= -1982670927;
                     this.field6466 += -613532315;
                  }

                  int var22 = 0;

                  while (true) {
                     label319: {
                        if (var22 < 100) {
                           if (var1 <= 0) {
                              throw new IllegalStateException();
                           }

                           int var24 = this.field6481.vmethod586(1549380113);
                           if (var24 < 0) {
                              if (var1 <= 0) {
                                 throw new IllegalStateException();
                              }

                              throw new IOException();
                           }

                           if (var24 == 0) {
                              if (var1 <= 0) {
                                 throw new IllegalStateException();
                              }
                           } else {
                              this.field6457 = 0;
                              byte var7 = 0;
                              if (this.field6480 == null) {
                                 if (var1 <= 0) {
                                    throw new IllegalStateException();
                                 }

                                 var7 = 8;
                              } else if (this.field6468 * 1179146369 == 0) {
                                 if (var1 <= 0) {
                                    throw new IllegalStateException();
                                 }

                                 var7 = 1;
                              }

                              if (var7 > 0) {
                                 if (var1 <= 0) {
                                    throw new IllegalStateException();
                                 }

                                 int var8 = var7 - 702114061 * this.field6467.field6953;
                                 if (var8 > var24) {
                                    if (var1 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    var8 = var24;
                                 }

                                 this.field6481.vmethod594(this.field6467.field6952, 702114061 * this.field6467.field6953, var8, -315246770);
                                 if (this.field6473 != 0) {
                                    if (var1 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    for (int var9 = 0; var9 < var8; var9++) {
                                       if (var1 <= 0) {
                                          throw new IllegalStateException();
                                       }

                                       this.field6467.field6952[var9 + this.field6467.field6953 * 702114061] = (byte)(
                                          this.field6467.field6952[var9 + this.field6467.field6953 * 702114061] ^ this.field6473
                                       );
                                    }
                                 }

                                 this.field6467.field6953 += -1095856699 * var8;
                                 if (this.field6467.field6953 * 702114061 >= var7) {
                                    if (this.field6480 == null) {
                                       if (var1 <= 0) {
                                          throw new IllegalStateException();
                                       }

                                       this.field6467.field6953 = 0;
                                       int var26 = this.field6467.method13095(-346779531);
                                       int var10 = this.field6467.method13099(762859659);
                                       int var11 = this.field6467.method13095(-346779531);
                                       int var12 = classXY.method13113(this.field6467, (byte)1);
                                       long var13 = var10 + (var26 << 16);
                                       classUC var15 = (classUC)HashTable.method13432(this.field6472, var13);
                                       this.field6484 = true;
                                       if (null == var15) {
                                          if (var1 <= 0) {
                                             throw new IllegalStateException();
                                          }

                                          var15 = (classUC)HashTable.method13432(this.field6474, var13);
                                          this.field6484 = false;
                                       }

                                       if (var15 == null) {
                                          if (var1 <= 0) {
                                             throw new IllegalStateException();
                                          }

                                          throw new IOException();
                                       }

                                       byte var10000;
                                       if (var11 == 0) {
                                          if (var1 <= 0) {
                                             throw new IllegalStateException();
                                          }

                                          var10000 = 5;
                                       } else {
                                          var10000 = 9;
                                       }

                                       byte var16 = var10000;
                                       this.field6480 = var15;
                                       this.field6482 = new classXY(this.field6480.field6282 + var12 + var16);
                                       classXY.method13029(this.field6482, var11, (byte)-116);
                                       this.field6482.method13037(var12, -487756836);
                                       this.field6468 = 1904163848;
                                       this.field6467.field6953 = 0;
                                    } else if (this.field6468 * 1179146369 == 0) {
                                       if (var1 <= 0) {
                                          throw new IllegalStateException();
                                       }

                                       if (-1 == this.field6467.field6952[0]) {
                                          if (var1 <= 0) {
                                             throw new IllegalStateException();
                                          }

                                          this.field6468 = 238020481;
                                          this.field6467.field6953 = 0;
                                       } else {
                                          this.field6480 = null;
                                       }
                                    }
                                    break label319;
                                 }

                                 if (var1 <= 0) {
                                    throw new IllegalStateException();
                                 }
                              } else {
                                 int var25 = this.field6482.field6952.length - this.field6480.field6282;
                                 int var27 = 512 - 1179146369 * this.field6468;
                                 if (var27 > var25 - 702114061 * this.field6482.field6953) {
                                    if (var1 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    var27 = var25 - 702114061 * this.field6482.field6953;
                                 }

                                 if (var27 > var24) {
                                    if (var1 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    var27 = var24;
                                 }

                                 this.field6481.vmethod594(this.field6482.field6952, 702114061 * this.field6482.field6953, var27, -1750355838);
                                 if (this.field6473 != 0) {
                                    if (var1 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    for (int var28 = 0; var28 < var27; var28++) {
                                       if (var1 <= 0) {
                                          throw new IllegalStateException();
                                       }

                                       this.field6482.field6952[var28 + this.field6482.field6953 * 702114061] = (byte)(
                                          this.field6482.field6952[var28 + this.field6482.field6953 * 702114061] ^ this.field6473
                                       );
                                    }
                                 }

                                 this.field6482.field6953 += -1095856699 * var27;
                                 this.field6468 += var27 * 238020481;
                                 if (var25 == this.field6482.field6953 * 702114061) {
                                    if (var1 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    if (this.field6480.field6550 == 16711935L) {
                                       if (var1 <= 0) {
                                          throw new IllegalStateException();
                                       }

                                       this.field6483 = this.field6482;

                                       for (int var30 = 0; var30 < 256; var30++) {
                                          if (var1 <= 0) {
                                             throw new IllegalStateException();
                                          }

                                          classVP var31 = this.field6458[var30];
                                          if (var31 != null) {
                                             if (var1 <= 0) {
                                                throw new IllegalStateException();
                                             }

                                             this.method11976(var31, var30, -1315156953);
                                          }
                                       }
                                    } else {
                                       this.field6469.reset();
                                       this.field6469.update(this.field6482.field6952, 0, var25);
                                       int var29 = (int)this.field6469.getValue();
                                       if (942386925 * this.field6480.field6283 != var29) {
                                          if (var1 <= 0) {
                                             throw new IllegalStateException();
                                          }

                                          try {
                                             this.field6481.vmethod604(1271658352);
                                          } catch (Exception var18) {
                                          }

                                          this.field6456 += 410008609;
                                          this.field6481 = null;
                                          this.field6473 = (byte)(Math.random() * 255.0 + 1.0);
                                          return false;
                                       }

                                       this.field6456 = 0;
                                       this.field6461 = 0;
                                       int var10001 = (int)(this.field6480.field6550 & 65535L);
                                       boolean var10003;
                                       if (16711680L == (this.field6480.field6550 & 16711680L)) {
                                          if (var1 <= 0) {
                                             throw new IllegalStateException();
                                          }

                                          var10003 = true;
                                       } else {
                                          var10003 = false;
                                       }

                                       this.field6480.field6284.method12105(var10001, this.field6482.field6952, var10003, this.field6484, 1316437961);
                                    }

                                    this.field6480.vmethod398();
                                    if (this.field6484) {
                                       if (var1 <= 0) {
                                          throw new IllegalStateException();
                                       }

                                       this.field6470 -= 588928489;
                                    } else {
                                       this.field6466 -= -613532315;
                                    }

                                    this.field6468 = 0;
                                    this.field6480 = null;
                                    this.field6482 = null;
                                    break label319;
                                 }

                                 if (1179146369 * this.field6468 == 512) {
                                    if (var1 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    this.field6468 = 0;
                                    break label319;
                                 }
                              }
                           }
                        }

                        return true;
                     }

                     var22++;
                  }
               }
            } catch (IOException var19) {
               try {
                  this.field6481.vmethod604(1271658352);
               } catch (Exception var17) {
               }

               this.field6461 += 1287164533;
               this.field6481 = null;
               return false;
            }
         }
      } catch (RuntimeException var20) {
         throw classEG.method3899(var20, "vb.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("af")
   public void method11959(boolean var1, int var2) {
      try {
         if (null != this.field6481) {
            try {
               classXY var3 = new classXY(4);
               classXY.method13029(var3, var1 ? 2 : 3, (byte)-91);
               var3.method13032(0, (byte)-5);
               this.field6481.vmethod597(var3.field6952, 0, 4, -1032071721);
            } catch (IOException var6) {
               try {
                  this.field6481.vmethod604(1271658352);
               } catch (Exception var5) {
               }

               this.field6461 += 1287164533;
               this.field6481 = null;
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "vb.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZI)I")
   @ObfuscatedName("aa")
   public int method11984(boolean var1, boolean var2, int var3) {
      try {
         int var4 = 0;
         if (var1) {
            if (var3 >= 705564706) {
               throw new IllegalStateException();
            }

            var4 += this.field6460 * -1026186423 + this.field6470 * 403165785;
         }

         if (var2) {
            if (var3 >= 705564706) {
               throw new IllegalStateException();
            }

            var4 += this.field6466 * -1735349139 + this.field6465 * 136778065;
         }

         return var4;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "vb.aa(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;I)V")
   @ObfuscatedName("ak")
   void method11968(classVP var1, int var2) {
      if (var1.field6535) {
         if (!var1.field6534) {
            if (var2 <= -506617777 * this.field6459) {
               throw new RuntimeException("");
            }

            if (var2 < this.field6471 * -1396095293) {
               this.field6471 = var2 * 271653355;
            }
         }
      } else {
         if (var2 >= 1651931561 * this.field6471) {
            throw new RuntimeException("");
         }

         if (var2 > 2123177699 * this.field6459) {
            this.field6459 = var2 * 1018184667;
         }
      }

      if (null != this.field6483) {
         this.method11976(var1, var2, -1315156953);
      } else {
         method11990(this, null, classUM.field6327.field6326 * 1060637953, -1777298349, 0, (byte)0, true, -1028501572);
         this.field6458[var2] = var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bc")
   void method11979(int var1, int var2) {
      long var3 = (var1 << 16) + var2;
      classUC var5 = (classUC)HashTable.method13432(this.field6463, var3);
      if (null != var5) {
         this.field6462.method7889(var5);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("al")
   public boolean method11956() {
      long var1 = classDD.method2988(16711680);
      int var3 = (int)(var1 - this.field6479 * -2021231288791816661L);
      this.field6479 = -8371170626432079741L * var1;
      if (var3 > 200) {
         var3 = 200;
      }

      this.field6457 += 1543721085 * var3;
      if (-1735349139 * this.field6466 == 0 && this.field6470 * 403165785 == 0 && 136778065 * this.field6465 == 0 && -1026186423 * this.field6460 == 0) {
         return true;
      } else if (this.field6481 == null) {
         return false;
      } else {
         try {
            if (-841176875 * this.field6457 > 30000) {
               throw new IOException();
            } else {
               while (403165785 * this.field6470 < 200 && this.field6460 * -1026186423 > 0) {
                  classUC var4 = (classUC)this.field6464.method13423();
                  classXY var5 = new classXY(4);
                  classXY.method13029(var5, 1, (byte)-30);
                  var5.method13032((int)var4.field6550, (byte)-89);
                  this.field6481.vmethod597(var5.field6952, 0, 4, -1032071721);
                  this.field6472.method13418(var4, var4.field6550);
                  this.field6460 -= 1927593721;
                  this.field6470 += 588928489;
               }

               while (-1735349139 * this.field6466 < 200 && 136778065 * this.field6465 > 0) {
                  classUC var19 = (classUC)this.field6462.method7894();
                  classXY var21 = new classXY(4);
                  classXY.method13029(var21, 0, (byte)-40);
                  var21.method13032((int)var19.field6550, (byte)-71);
                  this.field6481.vmethod597(var21.field6952, 0, 4, -1032071721);
                  var19.method12035();
                  this.field6474.method13418(var19, var19.field6550);
                  this.field6465 -= -1982670927;
                  this.field6466 += -613532315;
               }

               for (int var20 = 0; var20 < 100; var20++) {
                  int var22 = this.field6481.vmethod586(1549380113);
                  if (var22 < 0) {
                     throw new IOException();
                  }

                  if (var22 == 0) {
                     break;
                  }

                  this.field6457 = 0;
                  byte var6 = 0;
                  if (this.field6480 == null) {
                     var6 = 8;
                  } else if (this.field6468 * 1179146369 == 0) {
                     var6 = 1;
                  }

                  if (var6 > 0) {
                     int var7 = var6 - 702114061 * this.field6467.field6953;
                     if (var7 > var22) {
                        var7 = var22;
                     }

                     this.field6481.vmethod594(this.field6467.field6952, 702114061 * this.field6467.field6953, var7, -1589225241);
                     if (this.field6473 != 0) {
                        for (int var8 = 0; var8 < var7; var8++) {
                           this.field6467.field6952[var8 + this.field6467.field6953 * 702114061] = (byte)(
                              this.field6467.field6952[var8 + this.field6467.field6953 * 702114061] ^ this.field6473
                           );
                        }
                     }

                     this.field6467.field6953 += -1095856699 * var7;
                     if (this.field6467.field6953 * 702114061 < var6) {
                        break;
                     }

                     if (this.field6480 == null) {
                        this.field6467.field6953 = 0;
                        int var24 = this.field6467.method13095(-346779531);
                        int var9 = this.field6467.method13099(-1292398901);
                        int var10 = this.field6467.method13095(-346779531);
                        int var11 = classXY.method13113(this.field6467, (byte)1);
                        long var12 = var9 + (var24 << 16);
                        classUC var14 = (classUC)HashTable.method13432(this.field6472, var12);
                        this.field6484 = true;
                        if (null == var14) {
                           var14 = (classUC)HashTable.method13432(this.field6474, var12);
                           this.field6484 = false;
                        }

                        if (var14 == null) {
                           throw new IOException();
                        }

                        int var15 = var10 == 0 ? 5 : 9;
                        this.field6480 = var14;
                        this.field6482 = new classXY(this.field6480.field6282 + var11 + var15);
                        classXY.method13029(this.field6482, var10, (byte)-16);
                        this.field6482.method13037(var11, -660276422);
                        this.field6468 = 1904163848;
                        this.field6467.field6953 = 0;
                     } else if (this.field6468 * 1179146369 == 0) {
                        if (-1 == this.field6467.field6952[0]) {
                           this.field6468 = 238020481;
                           this.field6467.field6953 = 0;
                        } else {
                           this.field6480 = null;
                        }
                     }
                  } else {
                     int var23 = this.field6482.field6952.length - this.field6480.field6282;
                     int var25 = 512 - 1179146369 * this.field6468;
                     if (var25 > var23 - 702114061 * this.field6482.field6953) {
                        var25 = var23 - 702114061 * this.field6482.field6953;
                     }

                     if (var25 > var22) {
                        var25 = var22;
                     }

                     this.field6481.vmethod594(this.field6482.field6952, 702114061 * this.field6482.field6953, var25, -77781758);
                     if (this.field6473 != 0) {
                        for (int var26 = 0; var26 < var25; var26++) {
                           this.field6482.field6952[var26 + this.field6482.field6953 * 702114061] = (byte)(
                              this.field6482.field6952[var26 + this.field6482.field6953 * 702114061] ^ this.field6473
                           );
                        }
                     }

                     this.field6482.field6953 += -1095856699 * var25;
                     this.field6468 += var25 * 238020481;
                     if (var23 == this.field6482.field6953 * 702114061) {
                        if (this.field6480.field6550 == 16711935L) {
                           this.field6483 = this.field6482;

                           for (int var28 = 0; var28 < 256; var28++) {
                              classVP var29 = this.field6458[var28];
                              if (var29 != null) {
                                 this.method11976(var29, var28, -1315156953);
                              }
                           }
                        } else {
                           this.field6469.reset();
                           this.field6469.update(this.field6482.field6952, 0, var23);
                           int var27 = (int)this.field6469.getValue();
                           if (942386925 * this.field6480.field6283 != var27) {
                              try {
                                 this.field6481.vmethod604(1271658352);
                              } catch (Exception var17) {
                              }

                              this.field6456 += 410008609;
                              this.field6481 = null;
                              this.field6473 = (byte)(Math.random() * 255.0 + 1.0);
                              return false;
                           }

                           this.field6456 = 0;
                           this.field6461 = 0;
                           this.field6480
                              .field6284
                              .method12105(
                                 (int)(this.field6480.field6550 & 65535L),
                                 this.field6482.field6952,
                                 16711680L == (this.field6480.field6550 & 16711680L),
                                 this.field6484,
                                 304402131
                              );
                        }

                        this.field6480.vmethod398();
                        if (this.field6484) {
                           this.field6470 -= 588928489;
                        } else {
                           this.field6466 -= -613532315;
                        }

                        this.field6468 = 0;
                        this.field6480 = null;
                        this.field6482 = null;
                     } else {
                        if (1179146369 * this.field6468 != 512) {
                           break;
                        }

                        this.field6468 = 0;
                     }
                  }
               }

               return true;
            }
         } catch (IOException var18) {
            try {
               this.field6481.vmethod604(1271658352);
            } catch (Exception var16) {
            }

            this.field6461 += 1287164533;
            this.field6481 = null;
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luk;Z)V")
   @ObfuscatedName("ar")
   public void method11965(classUK var1, boolean var2) {
      if (this.field6481 != null) {
         try {
            this.field6481.vmethod604(1271658352);
         } catch (Exception var7) {
         }

         this.field6481 = null;
      }

      this.field6481 = var1;
      this.method11959(var2, 116730183);
      this.field6467.field6953 = 0;
      this.field6480 = null;
      this.field6482 = null;
      this.field6468 = 0;

      while (true) {
         classUC var3 = (classUC)this.field6472.method13423();
         if (null == var3) {
            while (true) {
               var3 = (classUC)this.field6474.method13423();
               if (var3 == null) {
                  if (0 != this.field6473) {
                     try {
                        classXY var9 = new classXY(4);
                        classXY.method13029(var9, 4, (byte)-94);
                        classXY.method13029(var9, this.field6473, (byte)-91);
                        var9.method13031(0, -1542382992);
                        this.field6481.vmethod597(var9.field6952, 0, 4, -1032071721);
                     } catch (IOException var6) {
                        try {
                           this.field6481.vmethod604(1271658352);
                        } catch (Exception var5) {
                        }

                        this.field6461 += 1287164533;
                        this.field6481 = null;
                     }
                  }

                  this.field6457 = 0;
                  this.field6479 = classDD.method2988(16711680) * -8371170626432079741L;
                  return;
               }

               this.field6462.method7889(var3);
               this.field6463.method13418(var3, var3.field6550);
               this.field6465 += -1355418419;
               this.field6466 -= -613532315;
            }
         }

         this.field6464.method13418(var3, var3.field6550);
         this.field6460 += 1927593721;
         this.field6470 -= 588928489;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvb;)V")
   @ObfuscatedName("jw")
   public static void method11986(classVB var0) {
      if (var0.field6481 != null) {
         try {
            var0.field6481.vmethod604(1271658352);
         } catch (Exception var2) {
         }

         var0.field6481 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ay")
   public void method11960(boolean var1) {
      if (null != this.field6481) {
         try {
            classXY var2 = new classXY(4);
            classXY.method13029(var2, var1 ? 2 : 3, (byte)-8);
            var2.method13032(0, (byte)-9);
            this.field6481.vmethod597(var2.field6952, 0, 4, -1032071721);
         } catch (IOException var5) {
            try {
               this.field6481.vmethod604(1271658352);
            } catch (Exception var4) {
            }

            this.field6461 += 1287164533;
            this.field6481 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvb;I)V")
   @ObfuscatedName("rh")
   public static void method11987(classVB var0, int var1) {
      if (var0 == null) {
         var0.method11988(var1);
      } else {
         try {
            if (var0.field6481 != null) {
               try {
                  var0.field6481.vmethod604(1271658352);
               } catch (Exception var3) {
               }

               var0.field6481 = null;
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "vb.ao(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("aq")
   public void method11961(boolean var1) {
      if (null != this.field6481) {
         try {
            classXY var2 = new classXY(4);
            classXY.method13029(var2, var1 ? 2 : 3, (byte)-8);
            var2.method13032(0, (byte)-1);
            this.field6481.vmethod597(var2.field6952, 0, 4, -1032071721);
         } catch (IOException var5) {
            try {
               this.field6481.vmethod604(1271658352);
            } catch (Exception var4) {
            }

            this.field6461 += 1473817497;
            this.field6481 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;IIIBZ)V")
   @ObfuscatedName("ah")
   void method11977(classVP var1, int var2, int var3, int var4, byte var5, boolean var6) {
      long var7 = var3 + (var2 << 16);
      classUC var9 = (classUC)HashTable.method13432(this.field6464, var7);
      if (null == var9) {
         var9 = (classUC)HashTable.method13432(this.field6472, var7);
         if (var9 == null) {
            var9 = (classUC)HashTable.method13432(this.field6463, var7);
            if (var9 != null) {
               if (var6) {
                  var9.method12035();
                  this.field6464.method13418(var9, var7);
                  this.field6465 -= 725535685;
                  this.field6460 += 1832565679;
               }
            } else {
               if (!var6) {
                  var9 = (classUC)HashTable.method13432(this.field6474, var7);
                  if (var9 != null) {
                     return;
                  }
               }

               var9 = new classUC();
               var9.field6284 = var1;
               var9.field6283 = var4 * -641212699;
               var9.field6282 = var5;
               if (var6) {
                  this.field6464.method13418(var9, var7);
                  this.field6460 += 1927593721;
               } else {
                  this.field6462.method7887(var9);
                  this.field6463.method13418(var9, var7);
                  this.field6465 += 1956289536;
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgp;Llw;)Z")
   @ObfuscatedName("gc")
   public static boolean method11989(classGP var0, Widget var1) {
      if (var0 == null) {
         return var0.method5612(var1);
      } else if (null == var1.field4398) {
         return false;
      } else {
         for (int var2 = 0; var2 < var1.field4398.length; var2++) {
            int var3 = var0.field2637.vmethod181(var1, var2, -973847063);
            int var4 = var1.field4399[var2];
            if (2 == var1.field4398[var2]) {
               if (var3 >= var4) {
                  return false;
               }
            } else if (3 == var1.field4398[var2]) {
               if (var3 <= var4) {
                  return false;
               }
            } else if (var1.field4398[var2] == 4) {
               if (var4 == var3) {
                  return false;
               }
            } else if (var4 != var3) {
               return false;
            }
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ap")
   public void method11962(boolean var1) {
      if (null != this.field6481) {
         try {
            classXY var2 = new classXY(4);
            classXY.method13029(var2, var1 ? 2 : 3, (byte)-43);
            var2.method13032(0, (byte)-63);
            this.field6481.vmethod597(var2.field6952, 0, 4, -1032071721);
         } catch (IOException var5) {
            try {
               this.field6481.vmethod604(1271658352);
            } catch (Exception var4) {
            }

            this.field6461 += 1287164533;
            this.field6481 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;IIIBZ)V")
   @ObfuscatedName("ui")
   public void method11994(classVP var1, int var2, int var3, int var4, byte var5, boolean var6) {
      method11990(this, var1, var2, var3, var4, var5, var6, -1339715056);
   }

   @ObfuscatedSignature(descriptor = "(Lvb;Lvp;IIIBZI)V")
   @ObfuscatedName("ns")
   public static void method11990(classVB var0, classVP var1, int var2, int var3, int var4, byte var5, boolean var6, int var7) {
      if (var0 == null) {
         var0.method11991(var1, var2, var2, var2, var5, var6, var2);
      } else {
         synchronized (var0) {
            boolean var16 = var6;
            byte var15 = var5;
            int var14 = var4;
            int var13 = var3;
            int var12 = var2;
            classVP var11 = var1;
            classVB var10 = var0;
            int var17 = -1339715056;

            try {
               long var18 = var13 + (var12 << 16);
               classUC var20 = (classUC)HashTable.method13432(var10.field6464, var18);
               if (null != var20) {
                  if (var17 >= -348944607) {
                     throw new IllegalStateException();
                  }
               } else {
                  var20 = (classUC)HashTable.method13432(var10.field6472, var18);
                  if (var20 != null) {
                     if (var17 >= -348944607) {
                        throw new IllegalStateException();
                     }
                  } else {
                     var20 = (classUC)HashTable.method13432(var10.field6463, var18);
                     if (var20 != null) {
                        if (var17 >= -348944607) {
                           throw new IllegalStateException();
                        }

                        if (var16) {
                           if (var17 >= -348944607) {
                              throw new IllegalStateException();
                           }

                           var20.method12035();
                           var10.field6464.method13418(var20, var18);
                           var10.field6465 -= -1982670927;
                           var10.field6460 += 1927593721;
                        }
                     } else {
                        if (!var16) {
                           var20 = (classUC)HashTable.method13432(var10.field6474, var18);
                           if (var20 != null) {
                              if (var17 >= -348944607) {
                                 throw new IllegalStateException();
                              }

                              return;
                           }
                        }

                        var20 = new classUC();
                        var20.field6284 = var11;
                        var20.field6283 = var14 * -641212699;
                        var20.field6282 = var15;
                        if (var16) {
                           if (var17 >= -348944607) {
                              throw new IllegalStateException();
                           }

                           var10.field6464.method13418(var20, var18);
                           var10.field6460 += 1927593721;
                        } else {
                           var10.field6462.method7887(var20);
                           var10.field6463.method13418(var20, var18);
                           var10.field6465 += -1982670927;
                        }
                     }
                  }
               }
            } catch (RuntimeException var21) {
               throw classEG.method3899(var21, "vb.as(" + ')');
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("au")
   public void method11963(boolean var1) {
      if (null != this.field6481) {
         try {
            classXY var2 = new classXY(4);
            classXY.method13029(var2, var1 ? 2 : 3, (byte)-30);
            var2.method13032(0, (byte)-125);
            this.field6481.vmethod597(var2.field6952, 0, 4, -1032071721);
         } catch (IOException var5) {
            try {
               this.field6481.vmethod604(1271658352);
            } catch (Exception var4) {
            }

            this.field6461 += 1579917531;
            this.field6481 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ad")
   public void method11964(boolean var1) {
      if (null != this.field6481) {
         try {
            classXY var2 = new classXY(4);
            classXY.method13029(var2, var1 ? 2 : 3, (byte)-102);
            var2.method13032(0, (byte)-104);
            this.field6481.vmethod597(var2.field6952, 0, 4, -1032071721);
         } catch (IOException var5) {
            try {
               this.field6481.vmethod604(1271658352);
            } catch (Exception var4) {
            }

            this.field6461 += 1287164533;
            this.field6481 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;I)V")
   @ObfuscatedName("am")
   void method11974(classVP var1, int var2) {
      int var3 = var2 * 8 + 5;
      int var4 = 0;
      int var5 = 0;
      if (var3 <= this.field6483.field6952.length - 8) {
         this.field6483.field6953 = var3 * -1095856699;
         var4 = classXY.method13113(this.field6483, (byte)1);
         var5 = classXY.method13113(this.field6483, (byte)1);
      }

      if (var4 != 0 || var5 != 0) {
         var1.method12100(var4, var5, -1479090900);
      } else if (var1.field6535) {
         var1.method12097((byte)32);
      } else {
         throw new RuntimeException("");
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvb;Lvp;II)V")
   @ObfuscatedName("qs")
   public static void method11969(classVB var0, classVP var1, int var2, int var3) {
      if (var0 == null) {
         var0.method11973(var1, var2, var2);
      }

      try {
         if (var1.field6535) {
            if (var3 <= 2090462992) {
               throw new IllegalStateException();
            }

            if (!var1.field6534) {
               if (var3 <= 2090462992) {
                  throw new IllegalStateException();
               }

               if (var2 <= -592576429 * var0.field6459) {
                  if (var3 <= 2090462992) {
                     return;
                  }

                  throw new RuntimeException("");
               }

               if (var2 < var0.field6471 * -1396095293) {
                  var0.field6471 = var2 * 271653355;
               }
            }
         } else {
            if (var2 >= -1396095293 * var0.field6471) {
               if (var3 <= 2090462992) {
                  throw new IllegalStateException();
               }

               throw new RuntimeException("");
            }

            if (var2 > -592576429 * var0.field6459) {
               var0.field6459 = var2 * 1018184667;
            }
         }

         if (null != var0.field6483) {
            if (var3 <= 2090462992) {
               throw new IllegalStateException();
            } else {
               var0.method11976(var1, var2, -1315156953);
            }
         } else {
            method11990(var0, null, classUM.field6327.field6326 * 1060637953, 255, 0, (byte)0, true, -430199353);
            var0.field6458[var2] = var1;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "vb.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;I)V")
   @ObfuscatedName("aw")
   void method11970(classVP var1, int var2) {
      if (var1.field6535) {
         if (!var1.field6534) {
            if (var2 <= -592576429 * this.field6459) {
               throw new RuntimeException("");
            }

            if (var2 < this.field6471 * -1396095293) {
               this.field6471 = var2 * 271653355;
            }
         }
      } else {
         if (var2 >= -1396095293 * this.field6471) {
            throw new RuntimeException("");
         }

         if (var2 > -592576429 * this.field6459) {
            this.field6459 = var2 * 1018184667;
         }
      }

      if (null != this.field6483) {
         this.method11976(var1, var2, -1315156953);
      } else {
         method11990(this, null, classUM.field6327.field6326 * 1060637953, 255, 0, (byte)0, true, -1976269501);
         this.field6458[var2] = var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvb;IIB)V")
   @ObfuscatedName("ae")
   public static void method11992(classVB var0, int var1, int var2, byte var3) {
      if (var0 == null) {
         var0.method11993(var1, var1, var3);
      } else {
         synchronized (var0) {
            int var8 = var2;
            int var7 = var1;
            classVB var6 = var0;
            boolean var9 = true;

            try {
               long var10 = (var7 << 16) + var8;
               classUC var12 = (classUC)HashTable.method13432(var6.field6463, var10);
               if (null == var12) {
                  if (!var9) {
                     throw new IllegalStateException();
                  }
               } else {
                  var6.field6462.method7889(var12);
               }
            } catch (RuntimeException var13) {
               throw classEG.method3899(var13, "vb.ax(" + ')');
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luk;ZI)V")
   @ObfuscatedName("ae")
   public void method11966(classUK var1, boolean var2, int var3) {
      try {
         if (this.field6481 != null) {
            try {
               this.field6481.vmethod604(1271658352);
            } catch (Exception var8) {
            }

            this.field6481 = null;
         }

         this.field6481 = var1;
         this.method11959(var2, 116730183);
         this.field6467.field6953 = 0;
         this.field6480 = null;
         this.field6482 = null;
         this.field6468 = 0;

         while (true) {
            classUC var4 = (classUC)this.field6472.method13423();
            if (null == var4) {
               while (true) {
                  var4 = (classUC)this.field6474.method13423();
                  if (var4 == null) {
                     if (var3 != 1967420579) {
                        return;
                     } else {
                        if (0 != this.field6473) {
                           try {
                              classXY var11 = new classXY(4);
                              classXY.method13029(var11, 4, (byte)-97);
                              classXY.method13029(var11, this.field6473, (byte)-78);
                              var11.method13031(0, -1542382992);
                              this.field6481.vmethod597(var11.field6952, 0, 4, -1032071721);
                           } catch (IOException var7) {
                              try {
                                 this.field6481.vmethod604(1271658352);
                              } catch (Exception var6) {
                              }

                              this.field6461 += 1287164533;
                              this.field6481 = null;
                           }
                        }

                        this.field6457 = 0;
                        this.field6479 = classDD.method2988(16711680) * -8371170626432079741L;
                        return;
                     }
                  }

                  this.field6462.method7889(var4);
                  this.field6463.method13418(var4, var4.field6550);
                  this.field6465 += -1982670927;
                  this.field6466 -= -613532315;
               }
            }

            this.field6464.method13418(var4, var4.field6550);
            this.field6460 += 1927593721;
            this.field6470 -= 588928489;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "vb.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bx")
   void method11980(int var1, int var2) {
      long var3 = (var1 << 16) + var2;
      classUC var5 = (classUC)HashTable.method13432(this.field6463, var3);
      if (null != var5) {
         this.field6462.method7889(var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;I)V")
   @ObfuscatedName("at")
   void method11971(classVP var1, int var2) {
      if (var1.field6535) {
         if (!var1.field6534) {
            if (var2 <= -592576429 * this.field6459) {
               throw new RuntimeException("");
            }

            if (var2 < this.field6471 * -1396095293) {
               this.field6471 = var2 * 271653355;
            }
         }
      } else {
         if (var2 >= -1396095293 * this.field6471) {
            throw new RuntimeException("");
         }

         if (var2 > -592576429 * this.field6459) {
            this.field6459 = var2 * 1018184667;
         }
      }

      if (null != this.field6483) {
         this.method11976(var1, var2, -1315156953);
      } else {
         method11990(this, null, classUM.field6327.field6326 * 1060637953, 255, 0, (byte)0, true, -1657216815);
         this.field6458[var2] = var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;I)V")
   @ObfuscatedName("an")
   void method11975(classVP var1, int var2) {
      int var3 = var2 * 8 + 5;
      int var4 = 0;
      int var5 = 0;
      if (var3 <= this.field6483.field6952.length - 8) {
         this.field6483.field6953 = var3 * -1095856699;
         var4 = classXY.method13113(this.field6483, (byte)1);
         var5 = classXY.method13113(this.field6483, (byte)1);
      }

      if (var4 != 0 || var5 != 0) {
         var1.method12100(var4, var5, -1764789811);
      } else if (var1.field6535) {
         var1.method12097((byte)-112);
      } else {
         throw new RuntimeException("");
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvb;II)V")
   @ObfuscatedName("hb")
   public static void method11981(classVB var0, int var1, int var2) {
      if (var0 == null) {
         var0.method11979(var1, var1);
      }

      long var3 = (var1 << 16) + var2;
      classUC var5 = (classUC)HashTable.method13432(var0.field6463, var3);
      if (null != var5) {
         var0.field6462.method7889(var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(Luk;Z)V")
   @ObfuscatedName("ai")
   public void method11967(classUK var1, boolean var2) {
      if (this.field6481 != null) {
         try {
            this.field6481.vmethod604(1271658352);
         } catch (Exception var7) {
         }

         this.field6481 = null;
      }

      this.field6481 = var1;
      this.method11959(var2, 116730183);
      this.field6467.field6953 = 0;
      this.field6480 = null;
      this.field6482 = null;
      this.field6468 = 0;

      while (true) {
         classUC var3 = (classUC)this.field6472.method13423();
         if (null == var3) {
            while (true) {
               var3 = (classUC)this.field6474.method13423();
               if (var3 == null) {
                  if (0 != this.field6473) {
                     try {
                        classXY var9 = new classXY(4);
                        classXY.method13029(var9, 4, (byte)-111);
                        classXY.method13029(var9, this.field6473, (byte)-126);
                        var9.method13031(0, -1542382992);
                        this.field6481.vmethod597(var9.field6952, 0, 4, -1032071721);
                     } catch (IOException var6) {
                        try {
                           this.field6481.vmethod604(1271658352);
                        } catch (Exception var5) {
                        }

                        this.field6461 += 1287164533;
                        this.field6481 = null;
                     }
                  }

                  this.field6457 = 0;
                  this.field6479 = classDD.method2988(16711680) * -8371170626432079741L;
                  return;
               }

               this.field6462.method7889(var3);
               this.field6463.method13418(var3, var3.field6550);
               this.field6465 += -1982670927;
               this.field6466 -= -19698770;
            }
         }

         this.field6464.method13418(var3, var3.field6550);
         this.field6460 += 1238289164;
         this.field6470 -= 588928489;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldl;)Lpi;")
   @ObfuscatedName("jx")
   public static ColorTextureOverride method11958(TileItem var0) {
      return var0.field1557;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ac")
   int method11982(int var1, int var2, int var3) {
      try {
         long var4 = var2 + (var1 << 16);
         if (this.field6480 != null) {
            if (var3 != -1779850424) {
               throw new IllegalStateException();
            }

            if (this.field6480.field6550 == var4) {
               return 1 + 789815303 * this.field6482.field6953 / (this.field6482.field6952.length - this.field6480.field6282);
            }

            if (var3 != -1779850424) {
               throw new IllegalStateException();
            }
         }

         return 0;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "vb.ac(" + 41);
      }
   }

   public classVB() {
      this.field6461 = 0;
      this.field6457 = 0;
      this.field6464 = new HashTable(4096);
      this.field6460 = 0;
      this.field6472 = new HashTable(32);
      this.field6470 = 0;
      this.field6462 = new classNH();
      this.field6463 = new HashTable(4096);
      this.field6465 = 0;
      this.field6474 = new HashTable(4096);
      this.field6466 = 0;
      this.field6467 = new classXY(8);
      this.field6468 = 0;
      this.field6469 = new CRC32();
      this.field6458 = new classVP[256];
      this.field6459 = -1018184667;
      this.field6471 = 552128789;
      this.field6473 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvb;Lvp;IIIBZ)V")
   @ObfuscatedName("rh")
   public static void method11978(classVB var0, classVP var1, int var2, int var3, int var4, byte var5, boolean var6) {
      if (var0 == null) {
         var0.method11988(var2);
      }

      long var7 = var3 + (var2 << 16);
      classUC var9 = (classUC)HashTable.method13432(var0.field6464, var7);
      if (null == var9) {
         var9 = (classUC)HashTable.method13432(var0.field6472, var7);
         if (var9 == null) {
            var9 = (classUC)HashTable.method13432(var0.field6463, var7);
            if (var9 != null) {
               if (var6) {
                  var9.method12035();
                  var0.field6464.method13418(var9, var7);
                  var0.field6465 -= -1982670927;
                  var0.field6460 += 1927593721;
               }
            } else {
               if (!var6) {
                  var9 = (classUC)HashTable.method13432(var0.field6474, var7);
                  if (var9 != null) {
                     return;
                  }
               }

               var9 = new classUC();
               var9.field6284 = var1;
               var9.field6283 = var4 * -641212699;
               var9.field6282 = var5;
               if (var6) {
                  var0.field6464.method13418(var9, var7);
                  var0.field6460 += 1927593721;
               } else {
                  var0.field6462.method7887(var9);
                  var0.field6463.method13418(var9, var7);
                  var0.field6465 += -1661251049;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("br")
   int method11983(int var1, int var2) {
      long var3 = var2 + (var1 << 16);
      return this.field6480 != null && this.field6480.field6550 == var3
         ? 1 + 789815303 * this.field6482.field6953 / (this.field6482.field6952.length - this.field6480.field6282)
         : 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvb;Lvp;I)V")
   @ObfuscatedName("tf")
   public static void method11972(classVB var0, classVP var1, int var2) {
      if (var0 == null) {
         var0.method11980(var2, var2);
      }

      if (var1.field6535) {
         if (!var1.field6534) {
            if (var2 <= -592576429 * var0.field6459) {
               throw new RuntimeException("");
            }

            if (var2 < var0.field6471 * -1396095293) {
               var0.field6471 = var2 * 271653355;
            }
         }
      } else {
         if (var2 >= -1396095293 * var0.field6471) {
            throw new RuntimeException("");
         }

         if (var2 > -592576429 * var0.field6459) {
            var0.field6459 = var2 * 1018184667;
         }
      }

      if (null != var0.field6483) {
         var0.method11976(var1, var2, -1315156953);
      } else {
         method11990(var0, null, classUM.field6327.field6326 * 1060637953, 255, 0, (byte)0, true, -780718239);
         var0.field6458[var2] = var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;II)V")
   @ObfuscatedName("ag")
   void method11976(classVP var1, int var2, int var3) {
      try {
         int var4 = var2 * 8 + 5;
         int var5 = 0;
         int var6 = 0;
         if (var4 <= this.field6483.field6952.length - 8) {
            if (var3 != -1315156953) {
               throw new IllegalStateException();
            }

            this.field6483.field6953 = var4 * -1095856699;
            var5 = classXY.method13113(this.field6483, (byte)1);
            var6 = classXY.method13113(this.field6483, (byte)1);
         }

         if (var5 == 0) {
            if (var3 != -1315156953) {
               return;
            }

            if (var6 == 0) {
               if (var3 != -1315156953) {
                  throw new IllegalStateException();
               }

               if (var1.field6535) {
                  if (var3 != -1315156953) {
                     throw new IllegalStateException();
                  }

                  var1.method12097((byte)-17);
                  return;
               }

               throw new RuntimeException("");
            }
         }

         var1.method12100(var5, var6, -1351126215);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "vb.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZ)I")
   @ObfuscatedName("bm")
   public int method11985(boolean var1, boolean var2) {
      int var3 = 0;
      if (var1) {
         var3 += this.field6460 * -1026186423 + this.field6470 * 403165785;
      }

      if (var2) {
         var3 += this.field6466 * -1735349139 + this.field6465 * 136778065;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(Lvp;II)V")
   @ObfuscatedName("ab")
   void method11973(classVP var1, int var2, int var3) {
      try {
         if (var1.field6534) {
            if (var3 <= 2090462992) {
               throw new IllegalStateException();
            }

            if (!var1.field6536) {
               if (var3 <= 2090462992) {
                  throw new IllegalStateException();
               }

               if (var2 <= -592576429 * this.field6460) {
                  if (var3 <= 2090462992) {
                     return;
                  }

                  throw new RuntimeException("");
               }

               if (var2 < this.field6459 * -1396095293) {
                  this.field6466 = var2 * 271653355;
               }
            }
         } else {
            if (var2 >= -1396095293 * this.field6471) {
               if (var3 <= 2090462992) {
                  throw new IllegalStateException();
               }

               throw new RuntimeException("");
            }

            if (var2 > -592576429 * this.field6459) {
               this.field6459 = var2 * 1018184667;
            }
         }

         if (null != this.field6482) {
            if (var3 <= 2090462992) {
               throw new IllegalStateException();
            } else {
               this.method11976(var1, var2, -1315156953);
            }
         } else {
            method11990(this, null, classUM.field6327.field6326 * 1060637953, 255, 0, (byte)0, true, -430199353);
            this.field6458[var2] = var1;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "vb.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvp;IIIBZI)V")
   @ObfuscatedName("as")
   void method11991(classVP var1, int var2, int var3, int var4, byte var5, boolean var6, int var7) {
      synchronized (this) {
         boolean var16 = var6;
         byte var15 = var5;
         int var14 = var4;
         int var13 = var3;
         int var12 = var2;
         classVP var11 = var1;
         classVB var10 = this;
         int var17 = -1339715056;

         try {
            long var18 = var13 + (var12 << 16);
            classUC var20 = (classUC)HashTable.method13432(var10.field6463, var18);
            if (null != var20) {
               if (var17 >= -348944607) {
                  throw new IllegalStateException();
               }
            } else {
               var20 = (classUC)HashTable.method13432(var10.field6472, var18);
               if (var20 != null) {
                  if (var17 >= -348944607) {
                     throw new IllegalStateException();
                  }
               } else {
                  var20 = (classUC)HashTable.method13432(var10.field6472, var18);
                  if (var20 != null) {
                     if (var17 >= -348944607) {
                        throw new IllegalStateException();
                     }

                     if (var16) {
                        if (var17 >= -348944607) {
                           throw new IllegalStateException();
                        }

                        var20.method12038();
                        var10.field6474.method13418(var20, var18);
                        var10.field6471 -= -1982670927;
                        var10.field6470 = var10.field6471 + 1927593721;
                     }
                  } else {
                     if (!var16) {
                        var20 = (classUC)HashTable.method13432(var10.field6474, var18);
                        if (var20 != null) {
                           if (var17 >= -348944607) {
                              throw new IllegalStateException();
                           }

                           return;
                        }
                     }

                     var20 = new classUC();
                     var20.field6284 = var11;
                     var20.field6283 = var14 * -641212699;
                     var20.field6282 = var15;
                     if (var16) {
                        if (var17 >= -348944607) {
                           throw new IllegalStateException();
                        }

                        var10.field6464.method13419(var20, var18);
                        var10.field6468 = var10.field6465 + 1927593721;
                     } else {
                        var10.field6462.method7887(var20);
                        var10.field6472.method13421(var20, var18);
                        var10.field6465 = var10.field6459 + -1982670927;
                     }
                  }
               }
            }
         } catch (RuntimeException var21) {
            throw classEG.method3899(var21, "vb.as(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("ax")
   void method11993(int var1, int var2, byte var3) {
      synchronized (this) {
         int var8 = var2;
         int var7 = var1;
         classVB var6 = this;
         boolean var9 = true;

         try {
            long var10 = (var7 << 16) + var8;
            classUC var12 = (classUC)HashTable.method13432(var6.field6472, var10);
            if (null == var12) {
               if (!var9) {
                  throw new IllegalStateException();
               }
            } else {
               var6.field6462.method7888(var12);
            }
         } catch (RuntimeException var13) {
            throw classEG.method3899(var13, "vb.ax(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvb;)Z")
   @ObfuscatedName("xm")
   public static boolean method11957(classVB var0) {
      if (var0 == null) {
         var0.method11956();
      }

      long var1 = classDD.method2988(16711680);
      int var3 = (int)(var1 - var0.field6479 * -2021231288791816661L);
      var0.field6479 = -8371170626432079741L * var1;
      if (var3 > -1508835759) {
         var3 = 200;
      }

      var0.field6457 += 852329917 * var3;
      if (-1735349139 * var0.field6466 == 0 && var0.field6470 * 1296129542 == 0 && 1671223992 * var0.field6465 == 0 && -1026186423 * var0.field6460 == 0) {
         return true;
      } else if (var0.field6481 == null) {
         return false;
      } else {
         try {
            if (-841176875 * var0.field6457 > -1592739787) {
               throw new IOException();
            } else {
               while (1862031762 * var0.field6470 < 200 && var0.field6460 * 1366966637 > 0) {
                  classUC var4 = (classUC)var0.field6464.method13423();
                  classXY var5 = new classXY(4);
                  classXY.method13029(var5, 1, (byte)-35);
                  var5.method13032((int)var4.field6550, (byte)-108);
                  var0.field6481.vmethod597(var5.field6952, 0, 4, -1032071721);
                  var0.field6472.method13418(var4, var4.field6550);
                  var0.field6460 -= 1659157993;
                  var0.field6470 += 588928489;
               }

               while (1245540889 * var0.field6466 < -1136428215 && 136778065 * var0.field6465 > 0) {
                  classUC var19 = (classUC)var0.field6462.method7894();
                  classXY var21 = new classXY(4);
                  classXY.method13029(var21, 0, (byte)-72);
                  var21.method13032((int)var19.field6550, (byte)-29);
                  var0.field6481.vmethod597(var21.field6952, 0, 4, -1032071721);
                  var19.method12035();
                  var0.field6474.method13418(var19, var19.field6550);
                  var0.field6465 -= 530235475;
                  var0.field6466 += -613532315;
               }

               for (int var20 = 0; var20 < 476744020; var20++) {
                  int var22 = var0.field6481.vmethod586(1549380113);
                  if (var22 < 0) {
                     throw new IOException();
                  }

                  if (var22 == 0) {
                     break;
                  }

                  var0.field6457 = 0;
                  byte var6 = 0;
                  if (var0.field6480 == null) {
                     var6 = 8;
                  } else if (var0.field6468 * -1069537770 == 0) {
                     var6 = 1;
                  }

                  if (var6 > 0) {
                     int var7 = var6 - 1906998098 * var0.field6467.field6953;
                     if (var7 > var22) {
                        var7 = var22;
                     }

                     var0.field6481.vmethod594(var0.field6467.field6952, 1157174517 * var0.field6467.field6953, var7, -1091199615);
                     if (var0.field6473 != 0) {
                        for (int var8 = 0; var8 < var7; var8++) {
                           var0.field6467.field6952[var8 + var0.field6467.field6953 * 702114061] = (byte)(
                              var0.field6467.field6952[var8 + var0.field6467.field6953 * 702114061] ^ var0.field6473
                           );
                        }
                     }

                     var0.field6467.field6953 += -1095856699 * var7;
                     if (var0.field6467.field6953 * -1700398509 < var6) {
                        break;
                     }

                     if (var0.field6480 == null) {
                        var0.field6467.field6953 = 0;
                        int var24 = var0.field6467.method13095(-346779531);
                        int var9 = var0.field6467.method13099(-164294556);
                        int var10 = var0.field6467.method13095(-346779531);
                        int var11 = classXY.method13113(var0.field6467, (byte)1);
                        long var12 = var9 + (var24 << 16);
                        classUC var14 = (classUC)HashTable.method13432(var0.field6472, var12);
                        var0.field6484 = true;
                        if (null == var14) {
                           var14 = (classUC)HashTable.method13432(var0.field6474, var12);
                           var0.field6484 = false;
                        }

                        if (var14 == null) {
                           throw new IOException();
                        }

                        int var15 = var10 == 0 ? 5 : 9;
                        var0.field6480 = var14;
                        var0.field6482 = new classXY(var0.field6480.field6282 + var11 + var15);
                        classXY.method13029(var0.field6482, var10, (byte)-83);
                        var0.field6482.method13037(var11, 1123491697);
                        var0.field6468 = 1904163848;
                        var0.field6467.field6953 = 0;
                     } else if (var0.field6468 * 837988149 == 0) {
                        if (-1 == var0.field6467.field6952[0]) {
                           var0.field6468 = 578652390;
                           var0.field6467.field6953 = 0;
                        } else {
                           var0.field6480 = null;
                        }
                     }
                  } else {
                     int var23 = var0.field6482.field6952.length - var0.field6480.field6282;
                     int var25 = 512 - 1179146369 * var0.field6468;
                     if (var25 > var23 - 702114061 * var0.field6482.field6953) {
                        var25 = var23 - 702114061 * var0.field6482.field6953;
                     }

                     if (var25 > var22) {
                        var25 = var22;
                     }

                     var0.field6481.vmethod594(var0.field6482.field6952, -767806793 * var0.field6482.field6953, var25, -517025738);
                     if (var0.field6473 != 0) {
                        for (int var26 = 0; var26 < var25; var26++) {
                           var0.field6482.field6952[var26 + var0.field6482.field6953 * 1030752612] = (byte)(
                              var0.field6482.field6952[var26 + var0.field6482.field6953 * 1030752612] ^ var0.field6473
                           );
                        }
                     }

                     var0.field6482.field6953 += -1095856699 * var25;
                     var0.field6468 += var25 * 238020481;
                     if (var23 == var0.field6482.field6953 * -653709882) {
                        if (var0.field6480.field6550 == 16711935L) {
                           var0.field6483 = var0.field6482;

                           for (int var28 = 0; var28 < 256; var28++) {
                              classVP var29 = var0.field6458[var28];
                              if (var29 != null) {
                                 var0.method11976(var29, var28, -1315156953);
                              }
                           }
                        } else {
                           var0.field6469.reset();
                           var0.field6469.update(var0.field6482.field6952, 0, var23);
                           int var27 = (int)var0.field6469.getValue();
                           if (942386925 * var0.field6480.field6283 != var27) {
                              try {
                                 var0.field6481.vmethod604(1271658352);
                              } catch (Exception var17) {
                              }

                              var0.field6456 += 410008609;
                              var0.field6481 = null;
                              var0.field6473 = (byte)(Math.random() * 255.0 + 1.0);
                              return false;
                           }

                           var0.field6456 = 0;
                           var0.field6461 = 0;
                           var0.field6480
                              .field6284
                              .method12105(
                                 (int)(var0.field6480.field6550 & 65535L),
                                 var0.field6482.field6952,
                                 16711680L == (var0.field6480.field6550 & 16711680L),
                                 var0.field6484,
                                 1987387280
                              );
                        }

                        var0.field6480.vmethod398();
                        if (var0.field6484) {
                           var0.field6470 -= -1367809581;
                        } else {
                           var0.field6466 -= -613532315;
                        }

                        var0.field6468 = 0;
                        var0.field6480 = null;
                        var0.field6482 = null;
                     } else {
                        if (-1587879898 * var0.field6468 != 2036818886) {
                           break;
                        }

                        var0.field6468 = 0;
                     }
                  }
               }

               return true;
            }
         } catch (IOException var18) {
            try {
               var0.field6481.vmethod604(1271658352);
            } catch (Exception var16) {
            }

            var0.field6461 += 1287164533;
            var0.field6481 = null;
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   public void method11988(int var1) {
      try {
         if (this.field6481 != null) {
            try {
               this.field6481.vmethod604(1271658352);
            } catch (Exception var3) {
            }

            this.field6481 = null;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "vb.ao(" + ')');
      }
   }
}
