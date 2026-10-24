import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("kq")
public class classKQ {
   @ObfuscatedName("ai")
   int field3991;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field3989 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field3988 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   static final int field4001 = 360;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field3999 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field3986 = 3;
   @ObfuscatedName("aw")
   int field3998;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4000 = 0;
   @ObfuscatedName("aq")
   int field3995;
   @ObfuscatedName("ad")
   int field3987;
   @ObfuscatedName("ap")
   int field3994;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field3993 = 5;
   @ObfuscatedName("au")
   int field3997;
   @ObfuscatedName("ar")
   int field3996;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field3990 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field3985 = 4;
   @ObfuscatedName("ko")
   public int field4003;
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   public static final int field4002 = 69;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   static final int field3992 = 76;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public void method6560() {
      this.field3995 = 0;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   public void method6561(byte var1) {
      try {
         this.field3995 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("af")
   public boolean method6566(byte var1) {
      try {
         boolean var10000;
         if (0 != this.field3995 * -955075037) {
            if (var1 == 16) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   public int method6569(int var1) {
      try {
         return -955075037 * this.field3995;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public int method6572(int var1) {
      try {
         return 605243999 * this.field3987;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method6567() {
      return 0 != this.field3995 * 1562555760;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ax")
   public int method6580(byte var1) {
      try {
         return -1987862123 * this.field3991;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public int method6582() {
      return -1218877699 * this.field3996;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public void method6562() {
      this.field3995 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("bm")
   public void method6592(classXY var1) {
      this.field4003 = classOE.field4841.getPlane();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   public void method6563() {
      this.field3995 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("br")
   public void method6587(classXY var1) {
      int var2 = var1.method13095(-346779531);
      switch (var2) {
         case 1:
            this.field3995 = 396274059;
            break;
         case 2:
            this.field3991 = 1776840512;
            this.field3996 = -98986688;
            this.field3995 = 792548118;
            break;
         case 3:
            this.field3991 = 0;
            this.field3996 = -98986688;
            this.field3995 = 792548118;
            break;
         case 4:
            this.field3991 = -741286272;
            this.field3996 = -98986688;
            this.field3995 = 792548118;
            break;
         case 5:
            this.field3991 = 1776840512;
            this.field3996 = 0;
            this.field3995 = 792548118;
            break;
         case 6:
            this.field3991 = 1776840512;
            this.field3996 = -197973376;
            this.field3995 = 792548118;
         case 7:
         case 8:
         case 9:
         default:
            break;
         case 10:
            this.field3995 = 1188822177;
            break;
         case 11:
            this.field3995 = 1585096236;
      }

      switch (-955075037 * this.field3995) {
         case 0:
            break;
         case 1:
         case 3:
            this.field3987 = var1.method13099(1603344816) * -650407521;
            break;
         case 2:
            this.field3994 = var1.method13099(249121794) * 90661409;
            this.field3997 = var1.method13099(-439830955) * -1745534545;
            this.field3998 = var1.method13095(-346779531) * -1197797973;
            break;
         case 4:
            this.field3987 = var1.method13099(811387063) * -650407521;
            this.field3998 = var1.method13105(-758448158) * -1197797973;
            break;
         default:
            throw new RuntimeException("");
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lkq;)I")
   @ObfuscatedName("dm")
   public static int method6585(classKQ var0) {
      return var0.field3998 * -1052801789;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   public void method6564() {
      this.field3995 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public void method6565() {
      this.field3995 = 0;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method6568() {
      return 0 != this.field3995 * 307883535;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ao")
   public void method6588(classXY var1, int var2) {
      this.method6592(var1);

      try {
         int var3 = var1.method13095(-346779531);
         switch (var3) {
            case 1:
               this.field3995 = 396274059;
               break;
            case 2:
               this.field3991 = 1776840512;
               this.field3996 = -98986688;
               this.field3995 = 792548118;
               break;
            case 3:
               this.field3991 = 0;
               this.field3996 = -98986688;
               this.field3995 = 792548118;
               break;
            case 4:
               this.field3991 = -741286272;
               this.field3996 = -98986688;
               this.field3995 = 792548118;
               break;
            case 5:
               this.field3991 = 1776840512;
               this.field3996 = 0;
               this.field3995 = 792548118;
               break;
            case 6:
               this.field3991 = 1776840512;
               this.field3996 = -197973376;
               this.field3995 = 792548118;
            case 7:
            case 8:
            case 9:
            default:
               break;
            case 10:
               this.field3995 = 1188822177;
               break;
            case 11:
               this.field3995 = 1585096236;
         }

         switch (-955075037 * this.field3995) {
            case 0:
               break;
            case 1:
            case 3:
               this.field3987 = var1.method13099(-764144338) * -650407521;
               break;
            case 2:
               this.field3994 = var1.method13099(-1896950449) * 90661409;
               this.field3997 = var1.method13099(1173961941) * -1745534545;
               this.field3998 = var1.method13095(-346779531) * -1197797973;
               break;
            case 4:
               this.field3987 = var1.method13099(1527862757) * -650407521;
               this.field3998 = var1.method13105(-758448158) * -1197797973;
               break;
            default:
               throw new RuntimeException("");
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "kq.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ai")
   public int method6570() {
      return -955075037 * this.field3995;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ar")
   public int method6571() {
      return 1589514569 * this.field3995;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   public int method6573() {
      return 605243999 * this.field3987;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   public int method6576() {
      return this.field3994 * 636264929;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   public int method6578() {
      return -501172913 * this.field3997;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   public int method6581() {
      return -1987862123 * this.field3991;
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("aa")
   public int method6586(short var1) {
      try {
         return this.field3998 * -1052801789;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.aa(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ah")
   public int method6583() {
      return 176768681 * this.field3996;
   }

   @ObfuscatedSignature(descriptor = "(Lkq;I)I")
   @ObfuscatedName("qa")
   public static int method6577(classKQ var0, int var1) {
      if (var0 == null) {
         var0.method6574(var1);
      }

      try {
         return var0.field3994 * 636264929;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([BIIB)Ljava/lang/String;")
   @ObfuscatedName("as")
   public static String method6590(byte[] var0, int var1, int var2, byte var3) {
      try {
         char[] var4 = new char[var2];
         int var5 = 0;

         for (int var6 = 0; var6 < var2; var6++) {
            if (var3 >= 1) {
               throw new IllegalStateException();
            }

            int var7 = var0[var1 + var6] & 255;
            if (0 == var7) {
               if (var3 >= 1) {
                  throw new IllegalStateException();
               }
            } else {
               if (var7 >= 128 && var7 < 160) {
                  char var8 = classOH.field4867[var7 - 128];
                  if (0 == var8) {
                     if (var3 >= 1) {
                        throw new IllegalStateException();
                     }

                     var8 = '?';
                  }

                  var7 = var8;
               }

               var4[var5++] = (char)var7;
            }
         }

         return new String(var4, 0, var5);
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "kq.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;)I")
   @ObfuscatedName("dd")
   public static int method6591(Actor var0) {
      return 635707271 * var0.field1461.field630;
   }

   @ObfuscatedSignature(descriptor = "(Lkq;I)I")
   @ObfuscatedName("yf")
   public static int method6579(classKQ var0, int var1) {
      if (var0 == null) {
         return var0.method6575(var1);
      } else {
         try {
            return -501172913 * var0.field3997;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "kq.as(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("yw")
   public void method6589(classXY var1) {
      int var2 = var1.method13095(-346779531);
      switch (var2) {
         case 1:
            this.field3995 = 388009509;
            break;
         case 2:
            this.field3991 = 1776840512;
            this.field3996 = -98986688;
            this.field3995 = 65700897;
            break;
         case 3:
            this.field3991 = 0;
            this.field3996 = -98986688;
            this.field3995 = 792548118;
            break;
         case 4:
            this.field3991 = -741286272;
            this.field3996 = 1438674894;
            this.field3995 = 792548118;
            break;
         case 5:
            this.field3991 = -1129540213;
            this.field3996 = 0;
            this.field3995 = 792548118;
            break;
         case 6:
            this.field3991 = 1776840512;
            this.field3996 = 1364509005;
            this.field3995 = 792548118;
         case 7:
         case 8:
         case 9:
         default:
            break;
         case 10:
            this.field3995 = 1243797061;
            break;
         case 11:
            this.field3995 = -1636449491;
      }

      switch (-2024976262 * this.field3995) {
         case 0:
            break;
         case 1:
         case 3:
            this.field3987 = var1.method13099(1465812137) * -650407521;
            break;
         case 2:
            this.field3994 = var1.method13099(165416355) * 90661409;
            this.field3997 = var1.method13099(296131001) * -1745534545;
            this.field3998 = var1.method13095(-346779531) * -1197797973;
            break;
         case 4:
            this.field3987 = var1.method13099(-779074281) * 1111540284;
            this.field3998 = var1.method13105(-758448158) * -2134244930;
            break;
         default:
            throw new RuntimeException("");
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ac")
   public int method6584(byte var1) {
      try {
         return -1218877699 * this.field3996;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.ac(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   public int method6574(int var1) {
      try {
         return 605243999 * this.field3996;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   public int method6575(int var1) {
      try {
         return 605243999 * this.field3991;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kq.ab(" + 41);
      }
   }
}
