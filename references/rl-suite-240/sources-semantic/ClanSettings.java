import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.runelite.api.clan.ClanMember;
import net.runelite.api.clan.ClanRank;
import net.runelite.api.clan.ClanTitle;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("iq")
public class ClanSettings implements net.runelite.api.clan.ClanSettings {
   @ObfuscatedName("al")
   public boolean field3032;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final byte field3019 = 126;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final byte field3018 = 125;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final byte field3016 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final byte field3020 = -1;
   @ObfuscatedName("br")
   int[] field3038;
   @ObfuscatedName("an")
   public int field3011 = 0;
   @ObfuscatedName("aa")
   public String field3014 = null;
   @ObfuscatedName("ai")
   public byte[] field3035;
   @ObfuscatedName("ay")
   public byte field3023;
   @ToRemove(unused = "true")
   @ObfuscatedName("bw")
   static final int field3028 = 79;
   @ObfuscatedName("at")
   public int field3025;
   @ObfuscatedName("aq")
   public byte field3022;
   @ObfuscatedName("ad")
   public byte field3017;
   @ObfuscatedName("av")
   public int field3026;
   @ObfuscatedName("au")
   long[] field3040;
   @ObfuscatedName("ak")
   public boolean[] field3043;
   @ObfuscatedName("ax")
   long field3030;
   @ObfuscatedName("aw")
   public int[] field3037;
   @ToRemove(unused = "true")
   @ObfuscatedName("bj")
   public static final int field3027 = 55;
   @ObfuscatedName("aj")
   public byte field3021;
   @ObfuscatedName("ap")
   public int field3015;
   @ObfuscatedName("ac")
   public int field3012;
   @ObfuscatedName("am")
   long[] field3039;
   @ObfuscatedName("ah")
   public String[] field3041;
   @ObfuscatedName("bn")
   public String[] field3042;
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("bx")
   IterableHashTable field3031;
   @ObfuscatedName("bc")
   boolean field3033;
   @ObfuscatedName("bp")
   boolean field3034;
   @ObfuscatedName("ar")
   int[] field3036;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final byte field3024 = 127;
   @ObfuscatedName("ao")
   int field3013 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("cf")
   static final int field3029 = 200;

   @ObfuscatedSignature(descriptor = "(IJ)Z")
   @ObfuscatedName("cp")
   boolean method6328(int var1, long var2) {
      try {
         if (this.field3031 != null) {
            Node var4 = this.field3031.method13637(var1);
            if (var4 != null) {
               if (var4 instanceof classVK) {
                  classVK var5 = (classVK)var4;
                  if (var5.field6515 == var2) {
                     return false;
                  }

                  var5.field6515 = var2;
                  return true;
               }

               var4.vmethod398();
            }
         } else {
            this.field3031 = new IterableHashTable(4);
         }

         IterableHashTable.method13620(this.field3031, new classVK(var2), var1);
         return true;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "iq.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;IIIIB)I")
   @ObfuscatedName("lv")
   public static int method6321(ClanSettings var0, int var1, int var2, int var3, int var4, byte var5) {
      if (var0 == null) {
         return var0.method6322(var1, var1, var1, var1, var5);
      } else {
         try {
            int var6 = (1 << var3) - 1;
            int var10000;
            if (31 == var4) {
               if (var5 != -1) {
                  throw new IllegalStateException();
               }

               var10000 = -1;
            } else {
               var10000 = (1 << var4 + 1) - 1;
            }

            int var7 = var10000;
            int var8 = var7 ^ var6;
            var2 <<= var3;
            var2 &= var8;
            int var9 = var0.field3038[var1];
            if (var2 == (var9 & var8)) {
               return -1;
            } else {
               var9 &= ~var8;
               var0.field3038[var1] = var9 | var2;
               return var1;
            }
         } catch (RuntimeException var10) {
            throw classEG.method3899(var10, "iq.ad(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("az")
   void method6279(int var1, int var2) {
      try {
         if (this.field3033) {
            if (var2 != -1348272939) {
               throw new IllegalStateException();
            }

            if (null != this.field3039) {
               System.arraycopy(this.field3039, 0, this.field3039 = new long[var1], 0, -845393113 * this.field3025);
            } else {
               this.field3039 = new long[var1];
            }
         }

         if (this.field3034) {
            if (var2 != -1348272939) {
               throw new IllegalStateException();
            }

            if (null != this.field3042) {
               if (var2 != -1348272939) {
                  throw new IllegalStateException();
               }

               System.arraycopy(this.field3042, 0, this.field3042 = new String[var1], 0, -845393113 * this.field3025);
            } else {
               this.field3042 = new String[var1];
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iq.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Liq;ILjava/lang/String;)Z")
   @ObfuscatedName("pp")
   public static boolean method6333(ClanSettings var0, int var1, String var2) {
      if (var0 == null) {
         var0.method6286(var1, var1, var1, var1);
      }

      if (null == var2) {
         var2 = "";
      } else if (var2.length() > 80) {
         var2 = var2.substring(0, 80);
      }

      if (null != var0.field3031) {
         Node var3 = var0.field3031.method13637(var1);
         if (var3 != null) {
            if (var3 instanceof classVE) {
               classVE var4 = (classVE)var3;
               if (var4.field6496 instanceof String) {
                  if (var2.equals(var4.field6496)) {
                     return false;
                  }

                  var4.vmethod398();
                  IterableHashTable.method13620(var0.field3031, new classVE(var2), var4.field6550);
                  return true;
               }
            }

            var3.vmethod398();
         }
      } else {
         var0.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(var0.field3031, new classVE(var2), var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("av")
   void method6275(int var1) {
      if (this.field3033) {
         if (this.field3040 != null) {
            System.arraycopy(this.field3040, 0, this.field3040 = new long[var1], 0, this.field3026 * -1967686263);
         } else {
            this.field3040 = new long[var1];
         }
      }

      if (this.field3034) {
         if (this.field3041 != null) {
            System.arraycopy(this.field3041, 0, this.field3041 = new String[var1], 0, -1967686263 * this.field3026);
         } else {
            this.field3041 = new String[var1];
         }
      }

      if (null != this.field3035) {
         System.arraycopy(this.field3035, 0, this.field3035 = new byte[var1], 0, 1019491924 * this.field3026);
      } else {
         this.field3035 = new byte[var1];
      }

      if (this.field3038 != null) {
         System.arraycopy(this.field3038, 0, this.field3038 = new int[var1], 0, this.field3026 * -1967686263);
      } else {
         this.field3038 = new int[var1];
      }

      if (this.field3037 != null) {
         System.arraycopy(this.field3037, 0, this.field3037 = new int[var1], 0, this.field3026 * -1967686263);
      } else {
         this.field3037 = new int[var1];
      }

      if (null != this.field3043) {
         System.arraycopy(this.field3043, 0, this.field3043 = new boolean[var1], 0, this.field3026 * -1613806328);
      } else {
         this.field3043 = new boolean[var1];
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   void method6298() {
      if (this.field3026 * -337878684 == 0) {
         this.field3012 = -188688071;
         this.field3015 = -2055824740;
      } else {
         this.field3012 = 570694239;
         this.field3015 = -96170216;
         int var1 = 0;
         byte var2 = this.field3035[0];

         for (int var3 = 1; var3 < -1967686263 * this.field3026; var3++) {
            if (this.field3035[var3] > var2) {
               if (-315773561 == var2) {
                  this.field3015 = -131410518 * var1;
               }

               var1 = var3;
               var2 = this.field3035[var3];
            } else if (-1 == this.field3015 * -1725108575 && this.field3035[var3] == 125) {
               this.field3015 = var3 * 1492534466;
            }
         }

         this.field3012 = var1 * 946980713;
         if (-1 != this.field3012 * -1288191853) {
            this.field3035[this.field3012 * -577292071] = 126;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;II)V")
   @ObfuscatedName("ax")
   void method6293(long var1, String var3, int var4, int var5) {
      try {
         if (var3 != null && var3.isEmpty()) {
            if (var5 == 1302417466) {
               throw new IllegalStateException();
            }

            var3 = null;
         }

         if (this.field3033 != var1 > 0L) {
            if (var5 == 1302417466) {
               throw new IllegalStateException();
            } else {
               throw new RuntimeException("");
            }
         } else {
            boolean var10001;
            if (null != var3) {
               if (var5 == 1302417466) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            if (this.field3034 != var10001) {
               if (var5 == 1302417466) {
                  throw new IllegalStateException();
               } else {
                  throw new RuntimeException("");
               }
            } else {
               label110: {
                  label120: {
                     if (var1 > 0L) {
                        if (var5 == 1302417466) {
                           throw new IllegalStateException();
                        }

                        if (this.field3040 == null || this.field3026 * -1967686263 >= this.field3040.length) {
                           break label120;
                        }

                        if (var5 == 1302417466) {
                           throw new IllegalStateException();
                        }
                     }

                     if (null == var3) {
                        break label110;
                     }

                     if (var5 == 1302417466) {
                        throw new IllegalStateException();
                     }

                     if (null != this.field3041) {
                        if (var5 == 1302417466) {
                           throw new IllegalStateException();
                        }

                        if (this.field3026 * -1967686263 < this.field3041.length) {
                           break label110;
                        }

                        if (var5 == 1302417466) {
                           throw new IllegalStateException();
                        }
                     }
                  }

                  this.method6278(-1967686263 * this.field3026 + 5, 1911516175);
               }

               if (this.field3040 != null) {
                  if (var5 == 1302417466) {
                     throw new IllegalStateException();
                  }

                  this.field3040[-1967686263 * this.field3026] = var1;
               }

               if (null != this.field3041) {
                  if (var5 == 1302417466) {
                     return;
                  }

                  this.field3041[this.field3026 * -1967686263] = var3;
               }

               if (-1 == this.field3012 * -577292071) {
                  if (var5 == 1302417466) {
                     throw new IllegalStateException();
                  }

                  this.field3012 = -380568527 * this.field3026;
                  this.field3035[-1967686263 * this.field3026] = 126;
               } else {
                  this.field3035[this.field3026 * -1967686263] = 0;
               }

               this.field3038[this.field3026 * -1967686263] = 0;
               this.field3037[this.field3026 * -1967686263] = var4;
               this.field3043[this.field3026 * -1967686263] = false;
               this.field3026 += -987100999;
               this.field3036 = null;
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "iq.ax(" + ')');
      }
   }

   public ClanTitle titleForRank(ClanRank var1) {
      if (!classOE.field4841.isClientThread()) {
         if (!GameEngine.$assertionsDisabled) {
            throw new AssertionError("must be called on client thread");
         } else {
            throw new IllegalStateException("must be called on client thread");
         }
      } else {
         int var2;
         switch (var1.getRank()) {
            case -1:
               var2 = -1;
               break;
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 91:
            case 92:
            case 93:
            case 94:
            case 106:
            case 107:
            case 108:
            case 109:
            case 111:
            case 112:
            case 113:
            case 114:
            case 116:
            case 117:
            case 118:
            case 119:
            case 121:
            case 122:
            case 123:
            default:
               var2 = this.method6342(1, 0, 9);
               break;
            case 10:
               var2 = this.method6342(1, 10, 19);
               break;
            case 20:
               var2 = this.method6342(1, 20, 29);
               break;
            case 30:
               var2 = this.method6342(2, 0, 9);
               break;
            case 40:
               var2 = this.method6342(2, 10, 19);
               break;
            case 50:
               var2 = this.method6342(2, 20, 29);
               break;
            case 60:
               var2 = this.method6342(3, 0, 9);
               break;
            case 70:
               var2 = this.method6342(3, 10, 19);
               break;
            case 80:
               var2 = this.method6342(3, 20, 29);
               break;
            case 90:
               var2 = this.method6342(4, 0, 9);
               break;
            case 95:
               var2 = this.method6342(5, 20, 29);
               break;
            case 96:
               var2 = this.method6342(102, 0, 9);
               break;
            case 97:
               var2 = this.method6342(102, 10, 19);
               break;
            case 98:
               var2 = this.method6342(102, 20, 29);
               break;
            case 99:
               var2 = this.method6342(103, 0, 9);
               break;
            case 100:
               var2 = -2;
               break;
            case 101:
               var2 = this.method6342(103, 10, 19);
               break;
            case 102:
               var2 = this.method6342(103, 20, 29);
               break;
            case 103:
               var2 = this.method6342(104, 0, 9);
               break;
            case 104:
               var2 = this.method6342(104, 10, 19);
               break;
            case 105:
               var2 = this.method6342(4, 10, 19);
               break;
            case 110:
               var2 = this.method6342(4, 20, 29);
               break;
            case 115:
               var2 = this.method6342(5, 0, 9);
               break;
            case 120:
               var2 = this.method6342(5, 10, 19);
               break;
            case 124:
               var2 = this.method6342(104, 20, 29);
               break;
            case 125:
               var2 = -3;
               break;
            case 126:
               var2 = -4;
               break;
            case 127:
               var2 = -5;
         }

         if (var2 == 1023) {
            return null;
         } else {
            EnumComposition var3 = classOE.field4841.method2252(3797);
            String var4 = var3.getStringValue(var2);
            return !var4.isEmpty() ? new ClanTitle(var2, var4) : null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("af")
   void method6295(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var1 < -1967686263 * this.field3026) {
               this.field3026 -= -987100999;
               this.field3036 = null;
               if (0 == this.field3026 * -1967686263) {
                  if (var2 == -549112886) {
                     throw new IllegalStateException();
                  }

                  this.field3040 = null;
                  this.field3041 = null;
                  this.field3035 = null;
                  this.field3038 = null;
                  this.field3037 = null;
                  this.field3043 = null;
                  this.field3012 = -946980713;
                  this.field3015 = -917140833;
               } else {
                  System.arraycopy(this.field3035, var1 + 1, this.field3035, var1, this.field3026 * -1967686263 - var1);
                  System.arraycopy(this.field3038, var1 + 1, this.field3038, var1, this.field3026 * -1967686263 - var1);
                  System.arraycopy(this.field3037, 1 + var1, this.field3037, var1, this.field3026 * -1967686263 - var1);
                  System.arraycopy(this.field3043, var1 + 1, this.field3043, var1, -1967686263 * this.field3026 - var1);
                  if (null != this.field3040) {
                     if (var2 == -549112886) {
                        return;
                     }

                     System.arraycopy(this.field3040, var1 + 1, this.field3040, var1, this.field3026 * -1967686263 - var1);
                  }

                  if (this.field3041 != null) {
                     if (var2 == -549112886) {
                        throw new IllegalStateException();
                     }

                     System.arraycopy(this.field3041, var1 + 1, this.field3041, var1, this.field3026 * -1967686263 - var1);
                  }

                  this.method6299(-286379203);
               }

               return;
            }

            if (var2 == -549112886) {
               return;
            }
         }

         throw new RuntimeException("");
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iq.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Liq;I)V")
   @ObfuscatedName("rr")
   public static void method6308(ClanSettings var0, int var1) {
      if (var0 == null) {
         var0.method6303(var1);
      }

      var0.field3025 -= -1466794044;
      if (0 == var0.field3025 * -845393113) {
         var0.field3039 = null;
         var0.field3042 = null;
      } else {
         if (var0.field3039 != null) {
            System.arraycopy(var0.field3039, var1 + 1, var0.field3039, var1, var0.field3025 * -1346258179 - var1);
         }

         if (null != var0.field3042) {
            System.arraycopy(var0.field3042, var1 + 1, var0.field3042, var1, 1085234865 * var0.field3025 - var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIS)Z")
   @ObfuscatedName("ap")
   boolean method6323(int var1, int var2, short var3) {
      try {
         if (null != this.field3031) {
            if (var3 == 1000) {
               throw new IllegalStateException();
            }

            Node var4 = this.field3031.method13637(var1);
            if (null != var4) {
               if (var3 == 1000) {
                  throw new IllegalStateException();
               }

               if (var4 instanceof IntegerNode) {
                  if (var3 == 1000) {
                     throw new IllegalStateException();
                  }

                  IntegerNode var5 = (IntegerNode)var4;
                  if (var2 == var5.field6501) {
                     if (var3 == 1000) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }

                  var5.field6501 = var2;
                  return true;
               }

               var4.vmethod398();
            }
         } else {
            this.field3031 = new IterableHashTable(4);
         }

         IterableHashTable.method13620(this.field3031, new IntegerNode(var2), var1);
         return true;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "iq.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;B)V")
   @ObfuscatedName("ao")
   void method6304(long var1, String var3, byte var4) {
      try {
         if (var3 != null) {
            if (var4 == 32) {
               throw new IllegalStateException();
            }

            if (var3.isEmpty()) {
               var3 = null;
            }
         }

         boolean var10001;
         if (var1 > 0L) {
            if (var4 == 32) {
               throw new IllegalStateException();
            }

            var10001 = true;
         } else {
            var10001 = false;
         }

         if (this.field3033 != var10001) {
            throw new RuntimeException("");
         } else {
            if (var3 != null) {
               if (var4 == 32) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            if (this.field3034 != var10001) {
               if (var4 == 32) {
                  throw new IllegalStateException();
               } else {
                  throw new RuntimeException("");
               }
            } else {
               label89: {
                  label99: {
                     if (var1 > 0L) {
                        if (this.field3039 == null) {
                           break label99;
                        }

                        if (var4 == 32) {
                           throw new IllegalStateException();
                        }

                        if (this.field3025 * -845393113 >= this.field3039.length) {
                           break label99;
                        }

                        if (var4 == 32) {
                           throw new IllegalStateException();
                        }
                     }

                     if (null == var3) {
                        break label89;
                     }

                     if (var4 == 32) {
                        throw new IllegalStateException();
                     }

                     if (this.field3042 != null) {
                        if (var4 == 32) {
                           throw new IllegalStateException();
                        }

                        if (-845393113 * this.field3025 < this.field3042.length) {
                           break label89;
                        }
                     }
                  }

                  this.method6279(5 + this.field3025 * -845393113, -1348272939);
               }

               if (null != this.field3039) {
                  this.field3039[this.field3025 * -845393113] = var1;
               }

               if (this.field3042 != null) {
                  this.field3042[-845393113 * this.field3025] = var3;
               }

               this.field3025 += 1554377879;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "iq.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("mc")
   public String[] method6344() {
      return this.field3041;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ac")
   void method6309(int var1, int var2) {
      try {
         this.field3025 -= 1554377879;
         if (0 == this.field3025 * -845393113) {
            this.field3039 = null;
            this.field3042 = null;
         } else {
            if (this.field3039 != null) {
               if (var2 <= -1948715938) {
                  return;
               }

               System.arraycopy(this.field3039, var1 + 1, this.field3039, var1, this.field3025 * -845393113 - var1);
            }

            if (null != this.field3042) {
               System.arraycopy(this.field3042, var1 + 1, this.field3042, var1, -845393113 * this.field3025 - var1);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iq.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IBB)I")
   @ObfuscatedName("aj")
   int method6310(int var1, byte var2, byte var3) {
      try {
         if (126 != var2) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            if (var2 != 127) {
               if (var1 == this.field3012 * -577292071) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (this.field3015 * -1725108575 == -1) {
                     return -1;
                  }

                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (this.field3035[this.field3015 * -1725108575] < 125) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     return -1;
                  }
               }

               if (var2 == this.field3035[var1]) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  return -1;
               }

               this.field3035[var1] = var2;
               this.method6299(274316535);
               return var1;
            }

            if (var3 >= 0) {
               throw new IllegalStateException();
            }
         }

         return -1;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "iq.aj(" + 41);
      }
   }

   public ClanSettings(classXY var1) {
      this.field3012 = -946980713;
      this.field3015 = -917140833;
      method6338(this, var1, 852263864);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Liq;)[I")
   @ObfuscatedName("uo")
   public static int[] method6290(ClanSettings var0) {
      if (var0.field3036 == null) {
         String[] var1 = new String[-1967686263 * var0.field3026];
         var0.field3036 = new int[-1967686263 * var0.field3026];

         for (int var2 = 0; var2 < var0.field3026 * 1416420770; var0.field3036[var2] = var2++) {
            var1[var2] = var0.field3041[var2];
            if (var1[var2] != null) {
               var1[var2] = var1[var2].toLowerCase();
            }
         }

         classUO.method11476(var1, var0.field3036, 99957133);
      }

      return var0.field3036;
   }

   public List getMembers() {
      int var1 = this.method6346();
      ArrayList var2 = new ArrayList(var1);

      for (int var3 = 0; var3 < var1; var3++) {
         var2.add(new rl1(this, var3));
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   void method6299(int var1) {
      try {
         if (this.field3026 * -1967686263 == 0) {
            if (var1 < 289333612) {
               this.field3012 = -946980713;
               this.field3015 = -917140833;
            }
         } else {
            this.field3012 = -946980713;
            this.field3015 = -917140833;
            int var2 = 0;
            byte var3 = this.field3035[0];

            for (int var4 = 1; var4 < -1967686263 * this.field3026; var4++) {
               if (var1 >= 289333612) {
                  return;
               }

               if (this.field3035[var4] > var3) {
                  if (var1 >= 289333612) {
                     throw new IllegalStateException();
                  }

                  if (125 == var3) {
                     if (var1 >= 289333612) {
                        throw new IllegalStateException();
                     }

                     this.field3015 = 917140833 * var2;
                  }

                  var2 = var4;
                  var3 = this.field3035[var4];
               } else if (-1 == this.field3015 * -1725108575) {
                  if (var1 >= 289333612) {
                     throw new IllegalStateException();
                  }

                  if (this.field3035[var4] == 125) {
                     if (var1 >= 289333612) {
                        throw new IllegalStateException();
                     }

                     this.field3015 = var4 * 917140833;
                  }
               }
            }

            this.field3012 = var2 * 946980713;
            if (-1 != this.field3012 * -577292071) {
               if (var1 >= 289333612) {
                  throw new IllegalStateException();
               }

               this.field3035[this.field3012 * -577292071] = 126;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "iq.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIS)Z")
   @ObfuscatedName("au")
   boolean method6324(int var1, int var2, int var3, int var4, short var5) {
      try {
         int var6 = (1 << var3) - 1;
         int var10000;
         if (31 == var4) {
            if (var5 != 16256) {
               throw new IllegalStateException();
            }

            var10000 = -1;
         } else {
            var10000 = (1 << 1 + var4) - 1;
         }

         int var7 = var10000;
         int var8 = var7 ^ var6;
         var2 <<= var3;
         var2 &= var8;
         if (null != this.field3031) {
            if (var5 != 16256) {
               throw new IllegalStateException();
            }

            Node var9 = this.field3031.method13637(var1);
            if (var9 != null) {
               if (var5 != 16256) {
                  throw new IllegalStateException();
               }

               if (var9 instanceof IntegerNode) {
                  IntegerNode var10 = (IntegerNode)var9;
                  if ((var10.field6501 & var8) == var2) {
                     if (var5 != 16256) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }

                  var10.field6501 &= ~var8;
                  var10.field6501 |= var2;
                  return true;
               }

               var9.vmethod398();
            }
         } else {
            this.field3031 = new IterableHashTable(4);
         }

         IterableHashTable.method13620(this.field3031, new IntegerNode(var2), var1);
         return true;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "iq.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("bz")
   boolean method6325(int var1, int var2, int var3, int var4) {
      int var5 = (1 << var3) - 1;
      int var6 = 31 == var4 ? -1 : (1 << 1 + var4) - 1;
      int var7 = var6 ^ var5;
      var2 <<= var3;
      var2 &= var7;
      if (null != this.field3031) {
         Node var8 = this.field3031.method13637(var1);
         if (var8 != null) {
            if (var8 instanceof IntegerNode) {
               IntegerNode var9 = (IntegerNode)var8;
               if ((var9.field6501 & var7) == var2) {
                  return false;
               }

               var9.field6501 &= ~var7;
               var9.field6501 |= var2;
               return true;
            }

            var8.vmethod398();
         }
      } else {
         this.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(this.field3031, new IntegerNode(var2), var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;B)Z")
   @ObfuscatedName("ar")
   boolean method6334(int var1, String var2, byte var3) {
      try {
         if (null == var2) {
            var2 = "";
         } else if (var2.length() > 80) {
            var2 = var2.substring(0, 80);
         }

         if (null != this.field3031) {
            if (var3 >= 106) {
               throw new IllegalStateException();
            }

            Node var4 = this.field3031.method13637(var1);
            if (var4 != null) {
               if (var3 >= 106) {
                  throw new IllegalStateException();
               }

               if (var4 instanceof classVE) {
                  if (var3 >= 106) {
                     throw new IllegalStateException();
                  }

                  classVE var5 = (classVE)var4;
                  if (var5.field6496 instanceof String) {
                     if (var2.equals(var5.field6496)) {
                        return false;
                     }

                     var5.vmethod398();
                     IterableHashTable.method13620(this.field3031, new classVE(var2), var5.field6550);
                     return true;
                  }
               }

               var4.vmethod398();
            }
         } else {
            this.field3031 = new IterableHashTable(4);
         }

         IterableHashTable.method13620(this.field3031, new classVE(var2), var1);
         return true;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "iq.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;IZI)I")
   @ObfuscatedName("sq")
   public static int method6316(ClanSettings var0, int var1, boolean var2, int var3) {
      if (var0 == null) {
         var0.method6320(var1, var2, var1);
      }

      try {
         if (var2 == var0.field3043[var1]) {
            if (var3 <= 1378918676) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            var0.field3043[var1] = var2;
            return var1;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "iq.aq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bx")
   public int method6283(int var1, int var2, int var3) {
      int var4 = -1070409994 == var3 ? -1 : (1 << var3 + 1) - 1;
      return (this.field3038[var1] & var4) >>> var2;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ak")
   void method6276(int var1) {
      if (this.field3033) {
         if (this.field3040 != null) {
            System.arraycopy(this.field3040, 0, this.field3040 = new long[var1], 0, this.field3026 * -1967686263);
         } else {
            this.field3040 = new long[var1];
         }
      }

      if (this.field3034) {
         if (this.field3041 != null) {
            System.arraycopy(this.field3041, 0, this.field3041 = new String[var1], 0, -1967686263 * this.field3026);
         } else {
            this.field3041 = new String[var1];
         }
      }

      if (null != this.field3035) {
         System.arraycopy(this.field3035, 0, this.field3035 = new byte[var1], 0, -1967686263 * this.field3026);
      } else {
         this.field3035 = new byte[var1];
      }

      if (this.field3038 != null) {
         System.arraycopy(this.field3038, 0, this.field3038 = new int[var1], 0, this.field3026 * -1967686263);
      } else {
         this.field3038 = new int[var1];
      }

      if (this.field3037 != null) {
         System.arraycopy(this.field3037, 0, this.field3037 = new int[var1], 0, this.field3026 * -1967686263);
      } else {
         this.field3037 = new int[var1];
      }

      if (null != this.field3043) {
         System.arraycopy(this.field3043, 0, this.field3043 = new boolean[var1], 0, this.field3026 * -1967686263);
      } else {
         this.field3043 = new boolean[var1];
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Liq;IIII)Z")
   @ObfuscatedName("yc")
   public static boolean method6326(ClanSettings var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method6327(var1, var1, var1, var1);
      }

      int var5 = (1 << var3) - 1;
      int var6 = 31 == var4 ? -1 : (1 << 1 + var4) - 1;
      int var7 = var6 ^ var5;
      var2 <<= var3;
      var2 &= var7;
      if (null != var0.field3031) {
         Node var8 = var0.field3031.method13637(var1);
         if (var8 != null) {
            if (var8 instanceof IntegerNode) {
               IntegerNode var9 = (IntegerNode)var8;
               if ((var9.field6501 & var7) == var2) {
                  return false;
               }

               var9.field6501 &= ~var7;
               var9.field6501 |= var2;
               return true;
            }

            var8.vmethod398();
         }
      } else {
         var0.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(var0.field3031, new IntegerNode(var2), var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Integer;")
   @ObfuscatedName("tw")
   public Integer method6345(int var1) {
      return this.method6289(var1, (byte)75);
   }

   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;)V")
   @ObfuscatedName("be")
   void method6305(long var1, String var3) {
      if (var3 != null && var3.isEmpty()) {
         var3 = null;
      }

      if (this.field3033 != var1 > 0L) {
         throw new RuntimeException("");
      } else if (this.field3034 != (var3 != null)) {
         throw new RuntimeException("");
      } else {
         if (var1 > 0L && (this.field3039 == null || this.field3025 * -131578380 >= this.field3039.length)
            || null != var3 && (this.field3042 == null || -845393113 * this.field3025 >= this.field3042.length)) {
            this.method6279(5 + this.field3025 * -845393113, -1348272939);
         }

         if (null != this.field3039) {
            this.field3039[this.field3025 * 332998056] = var1;
         }

         if (this.field3042 != null) {
            this.field3042[-845393113 * this.field3025] = var3;
         }

         this.field3025 += 649056350;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[I")
   @ObfuscatedName("as")
   public int[] method6291(byte var1) {
      try {
         if (this.field3036 == null) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            }

            String[] var2 = new String[-1967686263 * this.field3026];
            this.field3036 = new int[-1967686263 * this.field3026];

            for (int var3 = 0; var3 < this.field3026 * -1967686263; this.field3036[var3] = var3++) {
               if (var1 >= 1) {
                  throw new IllegalStateException();
               }

               var2[var3] = this.field3041[var3];
               if (var2[var3] != null) {
                  if (var1 >= 1) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = var2[var3].toLowerCase();
               }
            }

            classUO.method11476(var2, this.field3036, 481421932);
         }

         return this.field3036;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "iq.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("ah")
   public int method6280(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         for (int var2 = 0; var2 < -1967686263 * this.field3026; var2++) {
            if (this.field3041[var2].equals(var1)) {
               return var2;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("an")
   public int method6281(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         for (int var2 = 0; var2 < -1967686263 * this.field3026; var2++) {
            if (this.field3041[var2].equals(var1)) {
               return var2;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Integer;")
   @ObfuscatedName("br")
   public Integer method6287(int var1) {
      if (null == this.field3031) {
         return null;
      } else {
         Node var2 = this.field3031.method13637(var1);
         return var2 != null && var2 instanceof IntegerNode ? new Integer(((IntegerNode)var2).field6501) : null;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("sx")
   public int method6346() {
      return this.field3026 * -1967686263;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;[Ljava/lang/Object;)[Ljava/lang/Object;")
   @ObfuscatedName("pk")
   public static Object[] method6340(Widget var0, Object[] var1) {
      if (var0 == null) {
         var0.getTargetVerb();
      }

      return var1 != null ? Arrays.copyOf(var1, var1.length) : null;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bc")
   public int method6284(int var1, int var2, int var3) {
      int var4 = 31 == var3 ? -1 : (1 << var3 + 1) - 1;
      return (this.field3038[var1] & var4) >>> var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bw")
   void method6300() {
      if (this.field3026 * -1379492733 == 0) {
         this.field3012 = 775776988;
         this.field3015 = -917140833;
      } else {
         this.field3012 = -946980713;
         this.field3015 = -1181398211;
         int var1 = 0;
         byte var2 = this.field3035[0];

         for (int var3 = 1; var3 < -1967686263 * this.field3026; var3++) {
            if (this.field3035[var3] > var2) {
               if (360473302 == var2) {
                  this.field3015 = 982414571 * var1;
               }

               var1 = var3;
               var2 = this.field3035[var3];
            } else if (-1 == this.field3015 * -1725108575 && this.field3035[var3] == -1353983553) {
               this.field3015 = var3 * 517526604;
            }
         }

         this.field3012 = var1 * 946980713;
         if (-1 != this.field3012 * -577292071) {
            this.field3035[this.field3012 * -577292071] = 126;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/Integer;")
   @ObfuscatedName("bp")
   public Integer method6288(int var1) {
      if (null == this.field3031) {
         return null;
      } else {
         Node var2 = this.field3031.method13637(var1);
         return var2 != null && var2 instanceof IntegerNode ? new Integer(((IntegerNode)var2).field6501) : null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ba")
   void method6301() {
      if (this.field3026 * -1967686263 == 0) {
         this.field3012 = -946980713;
         this.field3015 = -917140833;
      } else {
         this.field3012 = -946980713;
         this.field3015 = -917140833;
         int var1 = 0;
         byte var2 = this.field3035[0];

         for (int var3 = 1; var3 < -1967686263 * this.field3026; var3++) {
            if (this.field3035[var3] > var2) {
               if (125 == var2) {
                  this.field3015 = 917140833 * var1;
               }

               var1 = var3;
               var2 = this.field3035[var3];
            } else if (-1 == this.field3015 * -1725108575 && this.field3035[var3] == 125) {
               this.field3015 = var3 * 917140833;
            }
         }

         this.field3012 = var1 * 946980713;
         if (-1 != this.field3012 * -577292071) {
            this.field3035[this.field3012 * -577292071] = 126;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;I)V")
   @ObfuscatedName("bg")
   void method6294(long var1, String var3, int var4) {
      if (var3 != null && var3.isEmpty()) {
         var3 = null;
      }

      if (this.field3033 != var1 > 0L) {
         throw new RuntimeException("");
      } else if (this.field3034 != (null != var3)) {
         throw new RuntimeException("");
      } else {
         if (var1 > 0L && (this.field3040 == null || this.field3026 * -1967686263 >= this.field3040.length)
            || null != var3 && (null == this.field3041 || this.field3026 * -1967686263 >= this.field3041.length)) {
            this.method6278(-1967686263 * this.field3026 + 5, 1440752857);
         }

         if (this.field3040 != null) {
            this.field3040[-1967686263 * this.field3026] = var1;
         }

         if (null != this.field3041) {
            this.field3041[this.field3026 * -1967686263] = var3;
         }

         if (-1 == this.field3012 * -577292071) {
            this.field3012 = -380568527 * this.field3026;
            this.field3035[-1967686263 * this.field3026] = 126;
         } else {
            this.field3035[this.field3026 * -1967686263] = 0;
         }

         this.field3038[this.field3026 * -1967686263] = 0;
         this.field3037[this.field3026 * -1967686263] = var4;
         this.field3043[this.field3026 * -1967686263] = false;
         this.field3026 += -987100999;
         this.field3036 = null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Liq;I)V")
   @ObfuscatedName("js")
   public static void method6277(ClanSettings var0, int var1) {
      if (var0.field3033) {
         if (var0.field3040 != null) {
            System.arraycopy(var0.field3040, 0, var0.field3040 = new long[var1], 0, var0.field3026 * -1967686263);
         } else {
            var0.field3040 = new long[var1];
         }
      }

      if (var0.field3034) {
         if (var0.field3041 != null) {
            System.arraycopy(var0.field3041, 0, var0.field3041 = new String[var1], 0, -1967686263 * var0.field3026);
         } else {
            var0.field3041 = new String[var1];
         }
      }

      if (null != var0.field3035) {
         System.arraycopy(var0.field3035, 0, var0.field3035 = new byte[var1], 0, -1967686263 * var0.field3026);
      } else {
         var0.field3035 = new byte[var1];
      }

      if (var0.field3038 != null) {
         System.arraycopy(var0.field3038, 0, var0.field3038 = new int[var1], 0, var0.field3026 * -1967686263);
      } else {
         var0.field3038 = new int[var1];
      }

      if (var0.field3037 != null) {
         System.arraycopy(var0.field3037, 0, var0.field3037 = new int[var1], 0, var0.field3026 * -1967686263);
      } else {
         var0.field3037 = new int[var1];
      }

      if (null != var0.field3043) {
         System.arraycopy(var0.field3043, 0, var0.field3043 = new boolean[var1], 0, var0.field3026 * -1967686263);
      } else {
         var0.field3043 = new boolean[var1];
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bj")
   void method6296(int var1) {
      if (var1 >= 0 && var1 < -1967686263 * this.field3026) {
         this.field3026 -= -987100999;
         this.field3036 = null;
         if (0 == this.field3026 * -1967686263) {
            this.field3040 = null;
            this.field3041 = null;
            this.field3035 = null;
            this.field3038 = null;
            this.field3037 = null;
            this.field3043 = null;
            this.field3012 = -946980713;
            this.field3015 = -917140833;
         } else {
            System.arraycopy(this.field3035, var1 + 1, this.field3035, var1, this.field3026 * -1967686263 - var1);
            System.arraycopy(this.field3038, var1 + 1, this.field3038, var1, this.field3026 * -1967686263 - var1);
            System.arraycopy(this.field3037, 1 + var1, this.field3037, var1, this.field3026 * -1967686263 - var1);
            System.arraycopy(this.field3043, var1 + 1, this.field3043, var1, -1967686263 * this.field3026 - var1);
            if (null != this.field3040) {
               System.arraycopy(this.field3040, var1 + 1, this.field3040, var1, this.field3026 * -1967686263 - var1);
            }

            if (this.field3041 != null) {
               System.arraycopy(this.field3041, var1 + 1, this.field3041, var1, this.field3026 * -1967686263 - var1);
            }

            this.method6299(178035997);
         }
      } else {
         throw new RuntimeException("");
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bk")
   void method6297(int var1) {
      if (var1 >= 0 && var1 < -1967686263 * this.field3026) {
         this.field3026 -= -20073127;
         this.field3036 = null;
         if (0 == this.field3026 * -978264140) {
            this.field3040 = null;
            this.field3041 = null;
            this.field3035 = null;
            this.field3038 = null;
            this.field3037 = null;
            this.field3043 = null;
            this.field3012 = -946980713;
            this.field3015 = -1543239170;
         } else {
            System.arraycopy(this.field3035, var1 + 1, this.field3035, var1, this.field3026 * 1787375351 - var1);
            System.arraycopy(this.field3038, var1 + 1, this.field3038, var1, this.field3026 * -1504307712 - var1);
            System.arraycopy(this.field3037, 1 + var1, this.field3037, var1, this.field3026 * -1967686263 - var1);
            System.arraycopy(this.field3043, var1 + 1, this.field3043, var1, -1967686263 * this.field3026 - var1);
            if (null != this.field3040) {
               System.arraycopy(this.field3040, var1 + 1, this.field3040, var1, this.field3026 * -1967686263 - var1);
            }

            if (this.field3041 != null) {
               System.arraycopy(this.field3041, var1 + 1, this.field3041, var1, this.field3026 * -1967686263 - var1);
            }

            this.method6299(104683310);
         }
      } else {
         throw new RuntimeException("");
      }
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("oa")
   public int[] method6347() {
      return this.method6291((byte)-64);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   void method6302() {
      if (this.field3026 * -1967686263 == 0) {
         this.field3012 = -946980713;
         this.field3015 = -917140833;
      } else {
         this.field3012 = -946980713;
         this.field3015 = -917140833;
         int var1 = 0;
         byte var2 = this.field3035[0];

         for (int var3 = 1; var3 < -1967686263 * this.field3026; var3++) {
            if (this.field3035[var3] > var2) {
               if (125 == var2) {
                  this.field3015 = 917140833 * var1;
               }

               var1 = var3;
               var2 = this.field3035[var3];
            } else if (-1 == this.field3015 * -1725108575 && this.field3035[var3] == 125) {
               this.field3015 = var3 * 917140833;
            }
         }

         this.field3012 = var1 * 946980713;
         if (-1 != this.field3012 * -577292071) {
            this.field3035[this.field3012 * -577292071] = 126;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("bq")
   int method6311(int var1, byte var2) {
      if (126 != var2 && var2 != -1498167495) {
         if (var1 != this.field3012 * -577292071 || this.field3015 * -1725108575 != -1 && this.field3035[this.field3015 * -1725108575] >= 916590868) {
            if (var2 == this.field3035[var1]) {
               return -1;
            } else {
               this.field3035[var1] = var2;
               this.method6299(-2015695094);
               return var1;
            }
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ay")
   boolean method6314(int var1, int var2) {
      try {
         if (var1 != -577292071 * this.field3012) {
            if (var2 != -1083013539) {
               throw new IllegalStateException();
            }

            if (this.field3035[var1] != 126) {
               this.field3035[-577292071 * this.field3012] = 125;
               this.field3015 = -1721649351 * this.field3012;
               this.field3035[var1] = 126;
               this.field3012 = 946980713 * var1;
               return true;
            }

            if (var2 != -1083013539) {
               throw new IllegalStateException();
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iq.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("bf")
   int method6312(int var1, byte var2) {
      if (126 != var2 && var2 != 127) {
         if (var1 != this.field3012 * -577292071 || this.field3015 * -1725108575 != -1 && this.field3035[this.field3015 * -1725108575] >= 125) {
            if (var2 == this.field3035[var1]) {
               return -1;
            } else {
               this.field3035[var1] = var2;
               this.method6299(-172096256);
               return var1;
            }
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("bm")
   public int[] method6292() {
      if (this.field3036 == null) {
         String[] var1 = new String[-1967686263 * this.field3026];
         this.field3036 = new int[-1967686263 * this.field3026];

         for (int var2 = 0; var2 < this.field3026 * -1967686263; this.field3036[var2] = var2++) {
            var1[var2] = this.field3041[var2];
            if (var1[var2] != null) {
               var1[var2] = var1[var2].toLowerCase();
            }
         }

         classUO.method11476(var1, this.field3036, 2140825942);
      }

      return this.field3036;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("oj")
   public int method6343(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         var1 = var1.toLowerCase();
         String[] var2 = this.method6344();
         int[] var3 = this.method6347();
         int var4 = 0;
         int var5 = var3.length - 1;

         while (var4 <= var5) {
            int var6 = var4 + var5 >>> 1;
            int var7 = var3[var6];
            String var8 = var2[var7];
            int var9 = var8.toLowerCase().compareTo(var1);
            if (var9 < 0) {
               var4 = var6 + 1;
            } else {
               if (var9 <= 0) {
                  return var7;
               }

               var5 = var6 - 1;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;)V")
   @ObfuscatedName("bi")
   void method6306(long var1, String var3) {
      if (var3 != null && var3.isEmpty()) {
         var3 = null;
      }

      if (this.field3033 != var1 > 0L) {
         throw new RuntimeException("");
      } else if (this.field3034 != (var3 != null)) {
         throw new RuntimeException("");
      } else {
         if (var1 > 0L && (this.field3039 == null || this.field3025 * -845393113 >= this.field3039.length)
            || null != var3 && (this.field3042 == null || 889087089 * this.field3025 >= this.field3042.length)) {
            this.method6279(5 + this.field3025 * -1499856537, -1348272939);
         }

         if (null != this.field3039) {
            this.field3039[this.field3025 * -1345575636] = var1;
         }

         if (this.field3042 != null) {
            this.field3042[1180734584 * this.field3025] = var3;
         }

         this.field3025 += 1554377879;
      }
   }

   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;)V")
   @ObfuscatedName("bb")
   void method6307(long var1, String var3) {
      if (var3 != null && var3.isEmpty()) {
         var3 = null;
      }

      if (this.field3033 != var1 > 0L) {
         throw new RuntimeException("");
      } else if (this.field3034 != (var3 != null)) {
         throw new RuntimeException("");
      } else {
         if (var1 > 0L && (this.field3039 == null || this.field3025 * -845393113 >= this.field3039.length)
            || null != var3 && (this.field3042 == null || -845393113 * this.field3025 >= this.field3042.length)) {
            this.method6279(5 + this.field3025 * -845393113, -1348272939);
         }

         if (null != this.field3039) {
            this.field3039[this.field3025 * -845393113] = var1;
         }

         if (this.field3042 != null) {
            this.field3042[-845393113 * this.field3025] = var3;
         }

         this.field3025 += 1554377879;
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Ljava/lang/Integer;")
   @ObfuscatedName("ag")
   public Integer method6289(int var1, byte var2) {
      try {
         if (null == this.field3031) {
            return null;
         } else {
            Node var3 = this.field3031.method13637(var1);
            if (var3 != null) {
               if (var2 != 75) {
                  throw new IllegalStateException();
               }

               if (var3 instanceof IntegerNode) {
                  return new Integer(((IntegerNode)var3).field6501);
               }

               if (var2 != 75) {
                  throw new IllegalStateException();
               }
            }

            return null;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "iq.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("yr")
   public int method6342(int var1, int var2, int var3) {
      Integer var4 = this.method6345(var1);
      int var5 = var4 == null ? -1 : var4;
      int var6 = 31 - var3;
      return var5 << var6 >>> var2 + var6;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("cv")
   void method6337(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (var2 >= 1 && var2 <= 6) {
         int var3 = var1.method13095(-346779531);
         if (0 != (var3 & 1)) {
            this.field3033 = true;
         }

         if (0 != (var3 & 2)) {
            this.field3034 = true;
         }

         if (!this.field3033) {
            this.field3040 = null;
            this.field3039 = null;
         }

         if (!this.field3034) {
            this.field3041 = null;
            this.field3042 = null;
         }

         this.field3011 = classXY.method13113(var1, (byte)1) * -1766455193;
         this.field3013 = classXY.method13113(var1, (byte)1) * 447663005;
         if (var2 <= 3 && 0 != -1951591755 * this.field3013) {
            this.field3013 += 2097065760;
         }

         this.field3026 = var1.method13099(1085925872) * -987100999;
         this.field3025 = var1.method13095(-346779531) * 1554377879;
         this.field3014 = var1.method13127(982630138);
         if (var2 >= 4) {
            classXY.method13113(var1, (byte)1);
         }

         this.field3032 = var1.method13095(-346779531) == 1;
         this.field3021 = var1.method13097((byte)17);
         this.field3023 = var1.method13097((byte)17);
         this.field3022 = var1.method13097((byte)17);
         this.field3017 = var1.method13097((byte)17);
         if (this.field3026 * -1967686263 > 0) {
            if (this.field3033 && (null == this.field3040 || this.field3040.length < this.field3026 * -1967686263)) {
               this.field3040 = new long[-1967686263 * this.field3026];
            }

            if (this.field3034 && (null == this.field3041 || this.field3041.length < -1967686263 * this.field3026)) {
               this.field3041 = new String[this.field3026 * -1967686263];
            }

            if (this.field3035 == null || this.field3035.length < this.field3026 * -1967686263) {
               this.field3035 = new byte[this.field3026 * -1967686263];
            }

            if (null == this.field3038 || this.field3038.length < -1967686263 * this.field3026) {
               this.field3038 = new int[-1967686263 * this.field3026];
            }

            if (null == this.field3037 || this.field3037.length < this.field3026 * -1967686263) {
               this.field3037 = new int[-1967686263 * this.field3026];
            }

            if (this.field3043 == null || this.field3043.length < -1967686263 * this.field3026) {
               this.field3043 = new boolean[-1967686263 * this.field3026];
            }

            for (int var4 = 0; var4 < this.field3026 * -1967686263; var4++) {
               if (this.field3033) {
                  this.field3040[var4] = classXY.method13115(var1, 885075618);
               }

               if (this.field3034) {
                  this.field3041[var4] = var1.method13125((byte)-11);
               }

               this.field3035[var4] = var1.method13097((byte)17);
               if (var2 >= 2) {
                  this.field3038[var4] = classXY.method13113(var1, (byte)1);
               }

               if (var2 >= 5) {
                  this.field3037[var4] = var1.method13099(-384456557);
               } else {
                  this.field3037[var4] = 0;
               }

               if (var2 >= 6) {
                  this.field3043[var4] = var1.method13095(-346779531) == 1;
               } else {
                  this.field3043[var4] = false;
               }
            }

            this.method6299(-1683694069);
         }

         if (this.field3025 * -845393113 > 0) {
            if (this.field3033 && (null == this.field3039 || this.field3039.length < -845393113 * this.field3025)) {
               this.field3039 = new long[-845393113 * this.field3025];
            }

            if (this.field3034 && (this.field3042 == null || this.field3042.length < this.field3025 * -845393113)) {
               this.field3042 = new String[this.field3025 * -845393113];
            }

            for (int var10 = 0; var10 < -845393113 * this.field3025; var10++) {
               if (this.field3033) {
                  this.field3039[var10] = classXY.method13115(var1, -1480563965);
               }

               if (this.field3034) {
                  this.field3042[var10] = var1.method13125((byte)77);
               }
            }
         }

         if (var2 >= 3) {
            int var11 = var1.method13099(907409062);
            if (var11 > 0) {
               this.field3031 = new IterableHashTable(var11 < 16 ? classBZ.method1387(var11, -1573601361) : 16);

               while (var11-- > 0) {
                  int var5 = classXY.method13113(var1, (byte)1);
                  int var6 = var5 & 1073741823;
                  int var7 = var5 >>> 30;
                  if (var7 == 0) {
                     int var8 = classXY.method13113(var1, (byte)1);
                     IterableHashTable.method13620(this.field3031, new IntegerNode(var8), var6);
                  } else if (var7 == 1) {
                     long var12 = classXY.method13115(var1, 2090571963);
                     IterableHashTable.method13620(this.field3031, new classVK(var12), var6);
                  } else if (2 == var7) {
                     String var13 = var1.method13127(-986049037);
                     IterableHashTable.method13620(this.field3031, new classVE(var13), var6);
                  }
               }
            }
         }
      } else {
         throw new RuntimeException("" + var2);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("bs")
   int method6313(int var1, byte var2) {
      if (126 != var2 && var2 != -1002712297) {
         if (var1 != this.field3012 * -577292071 || this.field3015 * 56653761 != -1 && this.field3035[this.field3015 * -1725108575] >= 1419866522) {
            if (var2 == this.field3035[var1]) {
               return -1;
            } else {
               this.field3035[var1] = var2;
               this.method6299(-1537575234);
               return var1;
            }
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public ClanMember findMember(String var1) {
      int var2 = this.method6343(var1.replace(' ', ' '));
      return var2 == -1 ? null : new rl1(this, var2);
   }

   @ObfuscatedSignature(descriptor = "(IZ)I")
   @ObfuscatedName("bl")
   int method6317(int var1, boolean var2) {
      if (var2 == this.field3043[var1]) {
         return -1;
      } else {
         this.field3043[var1] = var2;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(IZ)I")
   @ObfuscatedName("bv")
   int method6318(int var1, boolean var2) {
      if (var2 == this.field3043[var1]) {
         return -1;
      } else {
         this.field3043[var1] = var2;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(IZ)I")
   @ObfuscatedName("bh")
   int method6319(int var1, boolean var2) {
      if (var2 == this.field3043[var1]) {
         return -1;
      } else {
         this.field3043[var1] = var2;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("al")
   void method6278(int var1, int var2) {
      try {
         if (this.field3033) {
            if (var2 <= -486845133) {
               return;
            }

            if (this.field3040 != null) {
               if (var2 <= -486845133) {
                  throw new IllegalStateException();
               }

               System.arraycopy(this.field3040, 0, this.field3040 = new long[var1], 0, this.field3026 * -1967686263);
            } else {
               this.field3040 = new long[var1];
            }
         }

         if (this.field3034) {
            if (var2 <= -486845133) {
               throw new IllegalStateException();
            }

            if (this.field3041 != null) {
               if (var2 <= -486845133) {
                  throw new IllegalStateException();
               }

               System.arraycopy(this.field3041, 0, this.field3041 = new String[var1], 0, -1967686263 * this.field3026);
            } else {
               this.field3041 = new String[var1];
            }
         }

         if (null != this.field3035) {
            if (var2 <= -486845133) {
               throw new IllegalStateException();
            }

            System.arraycopy(this.field3035, 0, this.field3035 = new byte[var1], 0, -1967686263 * this.field3026);
         } else {
            this.field3035 = new byte[var1];
         }

         if (this.field3038 != null) {
            if (var2 <= -486845133) {
               return;
            }

            System.arraycopy(this.field3038, 0, this.field3038 = new int[var1], 0, this.field3026 * -1967686263);
         } else {
            this.field3038 = new int[var1];
         }

         if (this.field3037 != null) {
            System.arraycopy(this.field3037, 0, this.field3037 = new int[var1], 0, this.field3026 * -1967686263);
         } else {
            this.field3037 = new int[var1];
         }

         if (null != this.field3043) {
            if (var2 <= -486845133) {
               throw new IllegalStateException();
            }

            System.arraycopy(this.field3043, 0, this.field3043 = new boolean[var1], 0, this.field3026 * -1967686263);
         } else {
            this.field3043 = new boolean[var1];
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iq.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Liq;III)I")
   @ObfuscatedName("ng")
   public static int method6285(ClanSettings var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method6278(var1, var1);
      }

      int var4 = 31 == var3 ? -1 : (1 << var3 + 1) - 1;
      return (var0.field3038[var1] & var4) >>> var2;
   }

   @ObfuscatedSignature(descriptor = "(IJ)Z")
   @ObfuscatedName("ai")
   boolean method6329(int var1, long var2) {
      if (this.field3031 != null) {
         Node var4 = this.field3031.method13637(var1);
         if (var4 != null) {
            if (var4 instanceof classVK) {
               classVK var5 = (classVK)var4;
               if (var5.field6515 == var2) {
                  return false;
               }

               var5.field6515 = var2;
               return true;
            }

            var4.vmethod398();
         }
      } else {
         this.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(this.field3031, new classVK(var2), var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(IJ)Z")
   @ObfuscatedName("ct")
   boolean method6330(int var1, long var2) {
      if (this.field3031 != null) {
         Node var4 = this.field3031.method13637(var1);
         if (var4 != null) {
            if (var4 instanceof classVK) {
               classVK var5 = (classVK)var4;
               if (var5.field6515 == var2) {
                  return false;
               }

               var5.field6515 = var2;
               return true;
            }

            var4.vmethod398();
         }
      } else {
         this.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(this.field3031, new classVK(var2), var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(IJ)Z")
   @ObfuscatedName("cu")
   boolean method6331(int var1, long var2) {
      if (this.field3031 != null) {
         Node var4 = this.field3031.method13637(var1);
         if (var4 != null) {
            if (var4 instanceof classVK) {
               classVK var5 = (classVK)var4;
               if (var5.field6515 == var2) {
                  return false;
               }

               var5.field6515 = var2;
               return true;
            }

            var4.vmethod398();
         }
      } else {
         this.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(this.field3031, new classVK(var2), var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(Liq;Lxy;I)V")
   @ObfuscatedName("rw")
   public static void method6338(ClanSettings var0, classXY var1, int var2) {
      if (var0 == null) {
         var0.method6339(var1, var2);
      }

      try {
         int var3 = var1.method13095(-346779531);
         if (var3 >= 1) {
            if (var3 <= 6) {
               int var4 = var1.method13095(-346779531);
               if (0 != (var4 & 1)) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  var0.field3033 = true;
               }

               if (0 != (var4 & 2)) {
                  var0.field3034 = true;
               }

               if (!var0.field3033) {
                  var0.field3040 = null;
                  var0.field3039 = null;
               }

               if (!var0.field3034) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  var0.field3041 = null;
                  var0.field3042 = null;
               }

               var0.field3011 = classXY.method13113(var1, (byte)1) * -1766455193;
               var0.field3013 = classXY.method13113(var1, (byte)1) * 447663005;
               if (var3 <= 3) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  if (0 != -1951591755 * var0.field3013) {
                     if (var2 <= 439419475) {
                        return;
                     }

                     var0.field3013 += 2097065760;
                  }
               }

               var0.field3026 = var1.method13099(-502896030) * -987100999;
               var0.field3025 = var1.method13095(-346779531) * 1554377879;
               var0.field3014 = var1.method13127(1747062831);
               if (var3 >= 4) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  classXY.method13113(var1, (byte)1);
               }

               boolean var10001;
               if (var1.method13095(-346779531) == 1) {
                  if (var2 <= 439419475) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var0.field3032 = var10001;
               var0.field3021 = var1.method13097((byte)17);
               var0.field3023 = var1.method13097((byte)17);
               var0.field3022 = var1.method13097((byte)17);
               var0.field3017 = var1.method13097((byte)17);
               if (var0.field3026 * -1967686263 > 0) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  label371:
                  if (var0.field3033) {
                     if (var2 <= 439419475) {
                        throw new IllegalStateException();
                     }

                     if (null != var0.field3040) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        if (var0.field3040.length >= var0.field3026 * -1967686263) {
                           break label371;
                        }

                        if (var2 <= 439419475) {
                           return;
                        }
                     }

                     var0.field3040 = new long[-1967686263 * var0.field3026];
                  }

                  label361:
                  if (var0.field3034) {
                     if (var2 <= 439419475) {
                        throw new IllegalStateException();
                     }

                     if (null != var0.field3041) {
                        if (var2 <= 439419475) {
                           return;
                        }

                        if (var0.field3041.length >= -1967686263 * var0.field3026) {
                           break label361;
                        }

                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }
                     }

                     var0.field3041 = new String[var0.field3026 * -1967686263];
                  }

                  label352: {
                     if (var0.field3035 != null) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        if (var0.field3035.length >= var0.field3026 * -1967686263) {
                           break label352;
                        }
                     }

                     var0.field3035 = new byte[var0.field3026 * -1967686263];
                  }

                  label346: {
                     if (null != var0.field3038) {
                        if (var2 <= 439419475) {
                           return;
                        }

                        if (var0.field3038.length >= -1967686263 * var0.field3026) {
                           break label346;
                        }

                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }
                     }

                     var0.field3038 = new int[-1967686263 * var0.field3026];
                  }

                  label339: {
                     if (null != var0.field3037) {
                        if (var2 <= 439419475) {
                           return;
                        }

                        if (var0.field3037.length >= var0.field3026 * -1967686263) {
                           break label339;
                        }
                     }

                     var0.field3037 = new int[-1967686263 * var0.field3026];
                  }

                  label333: {
                     if (var0.field3043 != null) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        if (var0.field3043.length >= -1967686263 * var0.field3026) {
                           break label333;
                        }

                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }
                     }

                     var0.field3043 = new boolean[-1967686263 * var0.field3026];
                  }

                  for (int var5 = 0; var5 < var0.field3026 * -1967686263; var5++) {
                     if (var2 <= 439419475) {
                        return;
                     }

                     if (var0.field3033) {
                        var0.field3040[var5] = classXY.method13115(var1, 82002825);
                     }

                     if (var0.field3034) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        var0.field3041[var5] = var1.method13125((byte)45);
                     }

                     var0.field3035[var5] = var1.method13097((byte)17);
                     if (var3 >= 2) {
                        var0.field3038[var5] = classXY.method13113(var1, (byte)1);
                     }

                     if (var3 >= 5) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        var0.field3037[var5] = var1.method13099(709782397);
                     } else {
                        var0.field3037[var5] = 0;
                     }

                     if (var3 >= 6) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        boolean[] var10000 = var0.field3043;
                        boolean var10002;
                        if (var1.method13095(-346779531) == 1) {
                           if (var2 <= 439419475) {
                              throw new IllegalStateException();
                           }

                           var10002 = true;
                        } else {
                           var10002 = false;
                        }

                        var10000[var5] = var10002;
                     } else {
                        var0.field3043[var5] = false;
                     }
                  }

                  var0.method6299(-1752616186);
               }

               if (var0.field3025 * -845393113 > 0) {
                  if (var2 <= 439419475) {
                     throw new IllegalStateException();
                  }

                  label316:
                  if (var0.field3033) {
                     if (var2 <= 439419475) {
                        throw new IllegalStateException();
                     }

                     if (null != var0.field3039) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        if (var0.field3039.length >= -845393113 * var0.field3025) {
                           break label316;
                        }

                        if (var2 <= 439419475) {
                           return;
                        }
                     }

                     var0.field3039 = new long[-845393113 * var0.field3025];
                  }

                  label306:
                  if (var0.field3034) {
                     if (var2 <= 439419475) {
                        return;
                     }

                     if (var0.field3042 != null) {
                        if (var0.field3042.length >= var0.field3025 * -845393113) {
                           break label306;
                        }

                        if (var2 <= 439419475) {
                           return;
                        }
                     }

                     var0.field3042 = new String[var0.field3025 * -845393113];
                  }

                  for (int var12 = 0; var12 < -845393113 * var0.field3025; var12++) {
                     if (var2 <= 439419475) {
                        throw new IllegalStateException();
                     }

                     if (var0.field3033) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        var0.field3039[var12] = classXY.method13115(var1, -851108912);
                     }

                     if (var0.field3034) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        var0.field3042[var12] = var1.method13125((byte)9);
                     }
                  }
               }

               if (var3 >= 3) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  int var13 = var1.method13099(-734598826);
                  if (var13 > 0) {
                     if (var2 <= 439419475) {
                        return;
                     }

                     IterableHashTable var17 = new IterableHashTable;
                     int var10003;
                     if (var13 < 16) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        var10003 = classBZ.method1387(var13, -1616825814);
                     } else {
                        var10003 = 16;
                     }

                     var17./* $VF: Unable to resugar constructor */<init>(var10003);
                     var0.field3031 = var17;

                     while (var13-- > 0) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        int var6 = classXY.method13113(var1, (byte)1);
                        int var7 = var6 & 1073741823;
                        int var8 = var6 >>> 30;
                        if (var8 == 0) {
                           if (var2 <= 439419475) {
                              throw new IllegalStateException();
                           }

                           int var9 = classXY.method13113(var1, (byte)1);
                           IterableHashTable.method13620(var0.field3031, new IntegerNode(var9), var7);
                        } else if (var8 == 1) {
                           if (var2 <= 439419475) {
                              return;
                           }

                           long var14 = classXY.method13115(var1, -1500747717);
                           IterableHashTable.method13620(var0.field3031, new classVK(var14), var7);
                        } else if (2 == var8) {
                           if (var2 <= 439419475) {
                              return;
                           }

                           String var15 = var1.method13127(-118600208);
                           IterableHashTable.method13620(var0.field3031, new classVE(var15), var7);
                        }
                     }
                  }
               }

               return;
            }

            if (var2 <= 439419475) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException("" + var3);
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "iq.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)Z")
   @ObfuscatedName("cb")
   boolean method6332(int var1, long var2) {
      if (this.field3031 != null) {
         Node var4 = this.field3031.method13637(var1);
         if (var4 != null) {
            if (var4 instanceof classVK) {
               classVK var5 = (classVK)var4;
               if (var5.field6515 == var2) {
                  return false;
               }

               var5.field6515 = var2;
               return true;
            }

            var4.vmethod398();
         }
      } else {
         this.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(this.field3031, new classVK(var2), var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("be")
   public byte[] method6348() {
      return this.field3035;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Z")
   @ObfuscatedName("cl")
   boolean method6335(int var1, String var2) {
      if (null == var2) {
         var2 = "";
      } else if (var2.length() > 80) {
         var2 = var2.substring(0, 80);
      }

      if (null != this.field3031) {
         Node var3 = this.field3031.method13637(var1);
         if (var3 != null) {
            if (var3 instanceof classVE) {
               classVE var4 = (classVE)var3;
               if (var4.field6496 instanceof String) {
                  if (var2.equals(var4.field6496)) {
                     return false;
                  }

                  var4.vmethod398();
                  IterableHashTable.method13620(this.field3031, new classVE(var2), var4.field6550);
                  return true;
               }
            }

            var3.vmethod398();
         }
      } else {
         this.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(this.field3031, new classVE(var2), var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Z")
   @ObfuscatedName("cd")
   boolean method6336(int var1, String var2) {
      if (null == var2) {
         var2 = "";
      } else if (var2.length() > 80) {
         var2 = var2.substring(0, 80);
      }

      if (null != this.field3031) {
         Node var3 = this.field3031.method13637(var1);
         if (var3 != null) {
            if (var3 instanceof classVE) {
               classVE var4 = (classVE)var3;
               if (var4.field6496 instanceof String) {
                  if (var2.equals(var4.field6496)) {
                     return false;
                  }

                  var4.vmethod398();
                  IterableHashTable.method13620(this.field3031, new classVE(var2), var4.field6550);
                  return true;
               }
            }

            var3.vmethod398();
         }
      } else {
         this.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(this.field3031, new classVE(var2), var1);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("ab")
   public int method6286(int var1, int var2, int var3, int var4) {
      try {
         int var5 = 31 == var3 ? -1 : (1 << var3 + 1) - 1;
         return (this.field3038[var1] & var5) >>> var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "iq.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("aw")
   void method6339(classXY var1, int var2) {
      try {
         int var3 = var1.method13107(-346779531);
         if (var3 >= 1) {
            if (var3 <= 6) {
               int var4 = var1.method13152(-346779531);
               if (0 != (var4 & 1)) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  this.field3033 = true;
               }

               if (0 != (var4 & 2)) {
                  this.field3033 = true;
               }

               if (!this.field3033) {
                  this.field3039 = null;
                  this.field3039 = null;
               }

               if (!this.field3033) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  this.field3041 = null;
                  this.field3042 = null;
               }

               this.field3012 = classXY.method13113(var1, (byte)1) * -1766455193;
               this.field3013 = classXY.method13113(var1, (byte)1) * 447663005;
               if (var3 <= 3) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  if (0 != -1951591755 * this.field3013) {
                     if (var2 <= 439419475) {
                        return;
                     }

                     this.field3013 += 2097065760;
                  }
               }

               this.field3015 = var1.method13225(-502896030) * -987100999;
               this.field3015 = var1.method13146(-346779531) * 1554377879;
               this.field3014 = var1.method13127(1747062831);
               if (var3 >= 4) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  classXY.method13113(var1, (byte)1);
               }

               boolean var10001;
               if (var1.method13201(-346779531) == 1) {
                  if (var2 <= 439419475) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               this.field3032 = var10001;
               this.field3022 = var1.method13232((byte)17);
               this.field3021 = var1.method13232((byte)17);
               this.field3017 = var1.method13229((byte)17);
               this.field3017 = var1.method13235((byte)17);
               if (this.field3025 * -1967686263 > 0) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  label368:
                  if (this.field3034) {
                     if (var2 <= 439419475) {
                        throw new IllegalStateException();
                     }

                     if (null != this.field3040) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        if (this.field3039.length >= this.field3012 * -1967686263) {
                           break label368;
                        }

                        if (var2 <= 439419475) {
                           return;
                        }
                     }

                     this.field3039 = new long[-1967686263 * this.field3025];
                  }

                  label358:
                  if (this.field3034) {
                     if (var2 <= 439419475) {
                        throw new IllegalStateException();
                     }

                     if (null != this.field3042) {
                        if (var2 <= 439419475) {
                           return;
                        }

                        if (this.field3041.length >= -1967686263 * this.field3026) {
                           break label358;
                        }

                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }
                     }

                     this.field3041 = new String[this.field3026 * -1967686263];
                  }

                  label349: {
                     if (this.field3035 != null) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        if (this.field3035.length >= this.field3025 * -1967686263) {
                           break label349;
                        }
                     }

                     this.field3035 = new byte[this.field3025 * -1967686263];
                  }

                  label343: {
                     if (null != this.field3036) {
                        if (var2 <= 439419475) {
                           return;
                        }

                        if (this.field3036.length >= -1967686263 * this.field3026) {
                           break label343;
                        }

                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }
                     }

                     this.field3038 = new int[-1967686263 * this.field3012];
                  }

                  label336: {
                     if (null != this.field3037) {
                        if (var2 <= 439419475) {
                           return;
                        }

                        if (this.field3037.length >= this.field3015 * -1967686263) {
                           break label336;
                        }
                     }

                     this.field3037 = new int[-1967686263 * this.field3026];
                  }

                  label330: {
                     if (this.field3043 != null) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        if (this.field3043.length >= -1967686263 * this.field3015) {
                           break label330;
                        }

                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }
                     }

                     this.field3043 = new boolean[-1967686263 * this.field3015];
                  }

                  for (int var5 = 0; var5 < this.field3026 * -1967686263; var5++) {
                     if (var2 <= 439419475) {
                        return;
                     }

                     if (this.field3033) {
                        this.field3040[var5] = classXY.method13115(var1, 82002825);
                     }

                     if (this.field3033) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        this.field3041[var5] = var1.method13125((byte)45);
                     }

                     this.field3035[var5] = var1.method13097((byte)17);
                     if (var3 >= 2) {
                        this.field3036[var5] = classXY.method13113(var1, (byte)1);
                     }

                     if (var3 >= 5) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        this.field3037[var5] = var1.method13107(709782397);
                     } else {
                        this.field3037[var5] = 0;
                     }

                     if (var3 >= 6) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        boolean[] var10000 = this.field3043;
                        boolean var10002;
                        if (var1.method13095(-346779531) == 1) {
                           if (var2 <= 439419475) {
                              throw new IllegalStateException();
                           }

                           var10002 = true;
                        } else {
                           var10002 = false;
                        }

                        var10000[var5] = var10002;
                     } else {
                        this.field3043[var5] = false;
                     }
                  }

                  this.method6299(-1752616186);
               }

               if (this.field3015 * -845393113 > 0) {
                  if (var2 <= 439419475) {
                     throw new IllegalStateException();
                  }

                  label313:
                  if (this.field3033) {
                     if (var2 <= 439419475) {
                        throw new IllegalStateException();
                     }

                     if (null != this.field3040) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        if (this.field3039.length >= -845393113 * this.field3015) {
                           break label313;
                        }

                        if (var2 <= 439419475) {
                           return;
                        }
                     }

                     this.field3039 = new long[-845393113 * this.field3026];
                  }

                  label303:
                  if (this.field3033) {
                     if (var2 <= 439419475) {
                        return;
                     }

                     if (this.field3041 != null) {
                        if (this.field3042.length >= this.field3015 * -845393113) {
                           break label303;
                        }

                        if (var2 <= 439419475) {
                           return;
                        }
                     }

                     this.field3042 = new String[this.field3025 * -845393113];
                  }

                  for (int var12 = 0; var12 < -845393113 * this.field3015; var12++) {
                     if (var2 <= 439419475) {
                        throw new IllegalStateException();
                     }

                     if (this.field3033) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        this.field3040[var12] = classXY.method13115(var1, -851108912);
                     }

                     if (this.field3033) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        this.field3041[var12] = var1.method13125((byte)9);
                     }
                  }
               }

               if (var3 >= 3) {
                  if (var2 <= 439419475) {
                     return;
                  }

                  int var13 = var1.method13275(-734598826);
                  if (var13 > 0) {
                     if (var2 <= 439419475) {
                        return;
                     }

                     IterableHashTable var17 = new IterableHashTable;
                     int var10003;
                     if (var13 < 16) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        var10003 = classBZ.method1387(var13, -1616825814);
                     } else {
                        var10003 = 16;
                     }

                     var17./* $VF: Unable to resugar constructor */<init>(var10003);
                     this.field3031 = var17;

                     while (var13-- > 0) {
                        if (var2 <= 439419475) {
                           throw new IllegalStateException();
                        }

                        int var6 = classXY.method13113(var1, (byte)1);
                        int var7 = var6 & 1073741823;
                        int var8 = var6 >>> 30;
                        if (var8 == 0) {
                           if (var2 <= 439419475) {
                              throw new IllegalStateException();
                           }

                           int var9 = classXY.method13113(var1, (byte)1);
                           IterableHashTable.method13620(this.field3031, new IntegerNode(var9), var7);
                        } else if (var8 == 1) {
                           if (var2 <= 439419475) {
                              return;
                           }

                           long var14 = classXY.method13115(var1, -1500747717);
                           IterableHashTable.method13620(this.field3031, new classVK(var14), var7);
                        } else if (2 == var8) {
                           if (var2 <= 439419475) {
                              return;
                           }

                           String var15 = var1.method13127(-118600208);
                           IterableHashTable.method13620(this.field3031, new classVE(var15), var7);
                        }
                     }
                  }
               }

               return;
            }

            if (var2 <= 439419475) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException("" + var3);
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "iq.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("cr")
   boolean method6327(int var1, int var2, int var3, int var4) {
      int var5 = (1 << var3) - 1;
      int var6 = 31 == var4 ? -1 : (1 << 1 + var4) - 1;
      int var7 = var6 ^ var5;
      var2 <<= var3;
      var2 &= var7;
      if (null != this.field3031) {
         Node var8 = this.field3031.method13637(var1);
         if (var8 != null) {
            if (var8 instanceof IntegerNode) {
               IntegerNode var9 = (IntegerNode)var8;
               if ((var9.field6501 & var7) == var2) {
                  return false;
               }

               var9.field6501 &= ~var7;
               var9.field6501 |= var2;
               return true;
            }

            var8.vmethod400();
         }
      } else {
         this.field3031 = new IterableHashTable(4);
      }

      IterableHashTable.method13620(this.field3031, new IntegerNode(var2), var1);
      return true;
   }

   public String getName() {
      return this.field3014;
   }

   @ObfuscatedSignature(descriptor = "(IZI)I")
   @ObfuscatedName("aq")
   int method6320(int var1, boolean var2, int var3) {
      try {
         if (var2 == this.field3043[var1]) {
            if (var3 <= 1378918676) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            this.field3043[var1] = var2;
            return var1;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "iq.aq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIB)I")
   @ObfuscatedName("ad")
   int method6322(int var1, int var2, int var3, int var4, byte var5) {
      try {
         int var6 = (1 << var3) - 1;
         int var10000;
         if (31 == var4) {
            if (var5 != -1) {
               throw new IllegalStateException();
            }

            var10000 = -1;
         } else {
            var10000 = (1 << var4 + 1) - 1;
         }

         int var7 = var10000;
         int var8 = var7 ^ var6;
         var2 <<= var3;
         var2 &= var8;
         int var9 = this.field3038[var1];
         if (var2 == (var9 & var8)) {
            return -1;
         } else {
            var9 &= ~var8;
            this.field3038[var1] = var9 | var2;
            return var1;
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "iq.ad(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("by")
   void method6303(int var1) {
      try {
         if (this.field3012 * -1967686263 == 0) {
            if (var1 < 289333612) {
               this.field3025 = -946980713;
               this.field3011 = -917140833;
            }
         } else {
            this.field3025 = -946980713;
            this.field3026 = -917140833;
            int var2 = 0;
            byte var3 = this.field3035[0];

            for (int var4 = 1; var4 < -1967686263 * this.field3012; var4++) {
               if (var1 >= 289333612) {
                  return;
               }

               if (this.field3035[var4] > var3) {
                  if (var1 >= 289333612) {
                     throw new IllegalStateException();
                  }

                  if (125 == var3) {
                     if (var1 >= 289333612) {
                        throw new IllegalStateException();
                     }

                     this.field3012 = 917140833 * var2;
                  }

                  var2 = var4;
                  var3 = this.field3035[var4];
               } else if (-1 == this.field3026 * -1725108575) {
                  if (var1 >= 289333612) {
                     throw new IllegalStateException();
                  }

                  if (this.field3035[var4] == 125) {
                     if (var1 >= 289333612) {
                        throw new IllegalStateException();
                     }

                     this.field3012 = var4 * 917140833;
                  }
               }
            }

            this.field3025 = var2 * 946980713;
            if (-1 != this.field3026 * -577292071) {
               if (var1 >= 289333612) {
                  throw new IllegalStateException();
               }

               this.field3035[this.field3012 * -577292071] = 126;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "iq.aa(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Liq;Ljava/lang/String;)I")
   @ObfuscatedName("vm")
   public static int method6282(ClanSettings var0, String var1) {
      if (var0 == null) {
         var0.getMembers();
      }

      if (var1 != null && !var1.isEmpty()) {
         for (int var2 = 0; var2 < -1967686263 * var0.field3026; var2++) {
            if (var0.field3041[var2].equals(var1)) {
               return var2;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Liq;I)Z")
   @ObfuscatedName("sj")
   public static boolean method6315(ClanSettings var0, int var1) {
      if (var1 != -577292071 * var0.field3012 && var0.field3035[var1] != 126) {
         var0.field3035[-577292071 * var0.field3012] = 125;
         var0.field3015 = -1721649351 * var0.field3012;
         var0.field3035[var1] = 126;
         var0.field3012 = 946980713 * var1;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)I")
   @ObfuscatedName("ae")
   public int method6341(String var1, int var2) {
      return this.method6281(var1);
   }
}
