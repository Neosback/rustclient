import javax.annotation.Nullable;
import net.runelite.api.events.PostObjectComposition;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("om")
public class ObjectComposition extends classVJ implements net.runelite.api.ObjectComposition, rl9 {
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field4976 = 18;
   @ObfuscatedSignature(descriptor = "Lwj;")
   @ObfuscatedName("df")
   final classWJ field4961;
   @ObfuscatedName("dh")
   int field4957;
   @ObfuscatedName("cx")
   int field4947;
   @ObfuscatedName("dd")
   public int field4922;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field4971 = 21;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field4974 = 23;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field4934 = 27;
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   static final int field4973 = 32;
   @rl8(method10167 = 17)
   @ObfuscatedName("du")
   public int field4952;
   @ToRemove(unused = "true")
   @ObfuscatedName("ba")
   static final int field4977 = 78;
   @ToRemove(unused = "true")
   @ObfuscatedName("bz")
   static final int field4978 = 101;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("cb")
   static NodeCache field4964 = new NodeCache(4096);
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ct")
   public static NodeCache field4966 = new NodeCache(500);
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4923 = 2;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("cd")
   static NodeCache field4970 = new NodeCache(256);
   @ObfuscatedName("cp")
   static boolean field4969 = false;
   @ObfuscatedSignature(descriptor = "[Ler;")
   @ObfuscatedName("cs")
   static ModelData[] field4967 = new ModelData[4];
   @ObfuscatedName("cy")
   public final int field4920;
   @ObfuscatedName("ck")
   final int[] field4918;
   @ObfuscatedName("co")
   final int[] field4937;
   @ObfuscatedName("ca")
   String field4943;
   @ObfuscatedName("cc")
   short[] field4938;
   @ObfuscatedName("cf")
   short[] field4939;
   @ObfuscatedName("cn")
   final short[] field4940;
   @ObfuscatedName("ch")
   final short[] field4941;
   @ObfuscatedName("cg")
   public int field4942;
   @ObfuscatedName("dz")
   int field4959;
   @ObfuscatedName("cw")
   public final int field4944;
   @ObfuscatedName("dj")
   public final boolean field4945;
   @ObfuscatedName("ci")
   public final int field4921;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4926 = 14;
   @ObfuscatedName("ce")
   final boolean field4930;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4946 = 7;
   @ObfuscatedName("dc")
   public final int field4949;
   @ObfuscatedName("db")
   int field4927;
   @ObfuscatedName("cm")
   public final boolean field4962;
   @ObfuscatedName("dx")
   int field4919;
   @ObfuscatedSignature(descriptor = "Lpo;")
   @ObfuscatedName("dy")
   final EntityOps field4933;
   @rl8(method10167 = 17)
   @ObfuscatedName("di")
   public int field4951;
   @ObfuscatedName("dk")
   int field4955;
   @ObfuscatedName("dt")
   boolean field4953;
   @ObfuscatedName("da")
   public final boolean field4948;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("cv")
   static NodeCache field4965 = new NodeCache(256);
   @ObfuscatedName("dr")
   public boolean field4936;
   @ObfuscatedName("dm")
   int field4950;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field4975 = 40;
   @ObfuscatedName("de")
   int field4958;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4972 = 6;
   @ObfuscatedName("cj")
   public final boolean field4931;
   @ObfuscatedName("dv")
   public final int field4954;
   @ObfuscatedName("do")
   public final int field4935;
   @ObfuscatedName("dg")
   public final int[] field4928;
   @ObfuscatedName("dl")
   final int field4924;
   @ObfuscatedName("dq")
   final int field4960;
   @ObfuscatedName("cz")
   public int field4925;
   @ObfuscatedSignature(descriptor = "Lww;")
   @ObfuscatedName("ds")
   final classWW field4932;
   @ObfuscatedName("dw")
   int field4956;
   @ObfuscatedName("dn")
   public final boolean field4963;
   @rl8(method10167 = 16)
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("dp")
   IterableHashTable field4929;

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cl")
   public String method8595(int var1, String var2) {
      return classFS.method4998(this.field4929, var1, var2, (byte)73);
   }

   ObjectComposition(classXY var1, int var2, boolean var3) {
      this.field4920 = var2 * 442451425;
      int[] var4 = null;
      int[] var5 = null;
      String var6 = classKK.field3613;
      short[] var7 = null;
      short[] var8 = null;
      short[] var9 = null;
      short[] var10 = null;
      int var11 = 1;
      int var12 = 1;
      byte var13 = 2;
      boolean var14 = true;
      int var15 = -1;
      int var16 = -1;
      boolean var17 = false;
      boolean var18 = false;
      int var19 = -1;
      int var20 = 16;
      byte var21 = 0;
      int var22 = 0;
      EntityOps var23 = method8560(this, (byte)27);
      int var24 = -1;
      int var25 = -1;
      boolean var26 = false;
      boolean var27 = true;
      int var28 = 128;
      int var29 = 128;
      int var30 = 128;
      int var31 = 0;
      int var32 = 0;
      int var33 = 0;
      boolean var34 = false;
      boolean var35 = false;
      int var36 = -1;
      int var37 = 0;
      int[] var38 = null;
      int var39 = -1;
      int var40 = -1;
      classWJ var41 = new classWJ();
      classWW var42 = new classWW();
      boolean var43 = true;
      boolean var44 = false;
      IterableHashTable var45 = null;
      if (var1 != null && var1.field6952 != null) {
         label317:
         while (true) {
            int var46 = var1.method13095(-346779531);
            switch (var46) {
               case 0:
                  break label317;
               case 1:
               case 3:
               case 4:
               case 5:
               case 8:
               case 9:
               case 10:
               case 11:
               case 12:
               case 13:
               case 16:
               case 20:
               case 25:
               case 26:
               case 35:
               case 36:
               case 37:
               case 38:
               case 42:
               case 43:
               case 44:
               case 45:
               case 46:
               case 47:
               case 48:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
               case 58:
               case 59:
               case 60:
               case 63:
               case 76:
               case 80:
               case 83:
               case 84:
               case 85:
               case 86:
               case 87:
               case 88:
               case 94:
               case 97:
               case 98:
               case 99:
               case 103:
               case 104:
               case 105:
               case 106:
               case 107:
               case 108:
               case 109:
               case 110:
               case 111:
               case 112:
               case 113:
               case 114:
               case 115:
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
               case 139:
               case 140:
               case 141:
               case 142:
               case 143:
               case 144:
               case 145:
               case 146:
               case 147:
               case 148:
               case 149:
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
               case 160:
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
               case 200:
               case 201:
               case 202:
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
                  break;
               case 2:
                  var6 = var1.method13127(-515409055);
                  break;
               case 6:
                  int var61 = var1.method13095(-346779531);
                  if (var61 <= 0) {
                     break;
                  }

                  if (null != var4 && !field4969) {
                     var1.field6953 = var1.field6953 + var61 * (classXM.field6886.method12906((byte)3) + 1) * -1095856699;
                  } else {
                     var5 = new int[var61];
                     var4 = new int[var61];

                     for (int var68 = 0; var68 < var61; var68++) {
                        var4[var68] = var1.method13173(classXM.field6886.field6888, 1851122325);
                        var5[var68] = var1.method13095(-346779531);
                     }
                  }
                  break;
               case 7:
                  int var60 = var1.method13095(-346779531);
                  if (var60 <= 0) {
                     break;
                  }

                  if (var4 != null && !field4969) {
                     var1.field6953 = var1.field6953 + var60 * classXM.field6886.method12906((byte)3) * -1095856699;
                  } else {
                     var5 = null;
                     var4 = new int[var60];

                     for (int var67 = 0; var67 < var60; var67++) {
                        var4[var67] = var1.method13173(classXM.field6886.field6888, 1451941769);
                     }
                  }
                  break;
               case 14:
                  var11 = var1.method13095(-346779531);
                  break;
               case 15:
                  var12 = var1.method13095(-346779531);
                  break;
               case 17:
                  var13 = 0;
                  var14 = false;
                  break;
               case 18:
                  var14 = false;
                  break;
               case 19:
                  var15 = var1.method13095(-346779531);
                  break;
               case 21:
                  var16 = 0;
                  break;
               case 22:
                  var17 = true;
                  break;
               case 23:
                  var18 = true;
                  break;
               case 24:
                  var19 = var1.method13099(-1323360305);
                  if (65535 == var19) {
                     var19 = -1;
                  }
                  break;
               case 27:
                  var13 = 1;
                  break;
               case 28:
                  var20 = var1.method13095(-346779531);
                  break;
               case 29:
                  var21 = var1.method13097((byte)17);
                  break;
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 100:
               case 101:
               case 102:
                  var23.method9088(var1, var46, 30, 34, 100, 101, 102, 1161561653);
                  break;
               case 39:
                  var22 = var1.method13097((byte)17) * 5;
                  break;
               case 40:
                  int var59 = var1.method13095(-346779531);
                  var7 = new short[var59];
                  var8 = new short[var59];

                  for (int var66 = 0; var66 < var59; var66++) {
                     var7[var66] = (short)var1.method13099(-1846630518);
                     var8[var66] = (short)var1.method13099(-2047651131);
                  }
                  break;
               case 41:
                  int var58 = var1.method13095(-346779531);
                  var9 = new short[var58];
                  var10 = new short[var58];

                  for (int var65 = 0; var65 < var58; var65++) {
                     var9[var65] = (short)var1.method13099(-102554966);
                     var10[var65] = (short)var1.method13099(1164501279);
                  }
                  break;
               case 61:
                  var1.method13099(-172294366);
                  break;
               case 62:
                  var26 = true;
                  break;
               case 64:
                  var27 = false;
                  break;
               case 65:
                  var28 = var1.method13099(-2080287386);
                  break;
               case 66:
                  var29 = var1.method13099(38657320);
                  break;
               case 67:
                  var30 = var1.method13099(1915859492);
                  break;
               case 68:
                  var25 = var1.method13099(-641531789);
                  break;
               case 69:
                  var1.method13095(-346779531);
                  break;
               case 70:
                  var31 = var1.method13104((byte)16);
                  break;
               case 71:
                  var32 = var1.method13104((byte)16);
                  break;
               case 72:
                  var33 = var1.method13104((byte)16);
                  break;
               case 73:
                  var34 = true;
                  break;
               case 74:
                  var35 = true;
                  break;
               case 75:
                  var36 = var1.method13095(-346779531);
                  break;
               case 77:
               case 92:
                  var39 = var1.method13099(1946771396);
                  if (65535 == var39) {
                     var39 = -1;
                  }

                  var40 = var1.method13099(-1928013773);
                  if (var40 == 65535) {
                     var40 = -1;
                  }

                  int var57 = -1;
                  if (92 == var46) {
                     var57 = var1.method13099(1361074484);
                     if (65535 == var57) {
                        var57 = -1;
                     }
                  }

                  int var64 = var1.method13095(-346779531);
                  var38 = new int[2 + var64];
                  int var69 = 0;

                  for (; var69 <= var64; var69++) {
                     var38[var69] = var1.method13099(-1528622532);
                     if (var38[var69] == 65535) {
                        var38[var69] = -1;
                     }
                  }

                  var38[var64 + 1] = var57;
                  break;
               case 78:
                  var41.field6687 = var1.method13099(-1827717086) * -418754533;
                  var41.field6684 = var1.method13095(-346779531) * 39261697;
                  var41.field6686 = var1.method13095(-346779531) * -286691225;
                  break;
               case 79:
                  var42.field6782 = var1.method13099(-1148648289) * -1412665981;
                  var42.field6781 = var1.method13099(-564967977) * -736985931;
                  var41.field6684 = var1.method13095(-346779531) * 39261697;
                  var41.field6686 = var1.method13095(-346779531) * -286691225;
                  int var56 = var1.method13095(-346779531);
                  var42.field6783 = new int[var56];

                  for (int var63 = 0; var63 < var56; var63++) {
                     var42.field6783[var63] = var1.method13099(10166996);
                  }
                  break;
               case 81:
                  var16 = var1.method13095(-346779531) * 256;
                  break;
               case 82:
                  var24 = var1.method13099(-467223174);
                  break;
               case 89:
                  var43 = false;
                  break;
               case 90:
                  var44 = true;
                  break;
               case 91:
                  classWD var55 = var41.field6685;
                  int var49 = var1.method13095(-346779531);
                  classZL var62 = classZL.field7199[var49];
                  var55.method12391(var62, -179178084);
                  break;
               case 93:
                  int var48 = var1.method13095(-346779531);
                  classZL var47 = classZL.field7199[var48];
                  int var50 = var1.method13099(885772415) * 20;
                  classWD.method12386(var41.field6685, var47, var50, (byte)81);
                  int var52 = var1.method13095(-346779531);
                  classZL var51 = classZL.field7199[var52];
                  int var54 = var1.method13099(1322257324) * 20;
                  var41.field6685.method12389(var51, var54, -388881497);
                  break;
               case 95:
                  var41.field6688 = (classKC)classOF.method8436(classHF.method6037(-966579297), var1.method13095(-346779531), 145379329);
                  break;
               case 96:
                  var37 = var1.method13095(-346779531);
                  break;
               case 249:
                  var45 = classQH.method9572(var1, var45, 1978086108);
            }
         }
      }

      if (-1 == var15) {
         var15 = 0;
         if (null != var4 && (var5 == null || var5[0] == 10)) {
            var15 = 1;
         }

         if (var23.vmethod546((byte)0)) {
            var15 = 1;
         }
      }

      if (var36 == -1) {
         var36 = var13 != 0 ? 1 : 0;
      }

      if (var3 && var35) {
         var13 = 0;
         var14 = false;
      }

      this.field4918 = var4;
      this.field4937 = var5;
      this.field4943 = var6;
      this.field4938 = var7;
      this.field4939 = var8;
      this.field4940 = var9;
      this.field4941 = var10;
      this.field4942 = -1279242757 * var11;
      this.field4922 = var12 * -401373769;
      this.field4944 = var13 * -1601421407;
      this.field4945 = var14;
      this.field4921 = -1410837359 * var15;
      this.field4947 = var16 * 450557271;
      this.field4930 = var17;
      this.field4948 = var18;
      this.field4949 = -129385453 * var19;
      this.field4925 = -241033065 * var20;
      this.field4950 = var21 * -462559883;
      this.field4919 = -1630376027 * var22;
      this.field4933 = var23;
      this.field4951 = 1022644465 * var24;
      this.field4952 = var25 * -2041631353;
      this.field4953 = var26;
      this.field4936 = var27;
      this.field4955 = -717840543 * var28;
      this.field4927 = 1701928533 * var29;
      this.field4956 = -861285851 * var30;
      this.field4957 = var31 * -638985293;
      this.field4958 = 943269191 * var32;
      this.field4959 = -2113260135 * var33;
      this.field4931 = var34;
      this.field4954 = var36 * 1383196645;
      this.field4935 = -357278883 * var37;
      this.field4928 = var38;
      this.field4924 = var39 * -1595279675;
      this.field4960 = var40 * 883773675;
      this.field4961 = var41;
      this.field4932 = var42;
      this.field4962 = var43;
      this.field4963 = var44;
      if (null == var45) {
         this.field4929 = new IterableHashTable(1);
      } else {
         this.field4929 = var45;
      }

      this.method8626(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ax")
   public final boolean method8567(int var1) {
      try {
         if (null == this.field4918) {
            if (var1 == -1147741534) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            boolean var2 = true;

            for (int var3 = 0; var3 < this.field4918.length; var3++) {
               if (var1 == -1147741534) {
                  throw new IllegalStateException();
               }

               var2 &= classKD.field3552.method11944(this.field4918[var3], 0, (byte)79);
            }

            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "om.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpo;")
   @ObfuscatedName("ew")
   public EntityOps method8631() {
      return this.field4933;
   }

   @ObfuscatedSignature(descriptor = "(II[[IIIII)Lee;")
   @ObfuscatedName("ac")
   public final Renderable method8571(int var1, int var2, int[][] var3, int var4, int var5, int var6, int var7) {
      try {
         long var8;
         if (this.field4937 == null) {
            if (var7 >= 682279145) {
               throw new IllegalStateException();
            }

            var8 = var2 + (this.field4920 * -381960671 << 10);
         } else {
            var8 = var2 + (this.field4920 * -381960671 << 10) + (var1 << 3);
         }

         Object var10 = (Renderable)NodeCache.method6460(field4970, var8);
         if (var10 == null) {
            if (var7 >= 682279145) {
               throw new IllegalStateException();
            }

            ModelData var11 = method8627(this, var1, var2, -1973084331);
            if (var11 == null) {
               if (var7 >= 682279145) {
                  throw new IllegalStateException();
               }

               return null;
            }

            if (!this.field4930) {
               if (var7 >= 682279145) {
                  throw new IllegalStateException();
               }

               var10 = var11.method4070(54395613 * this.field4950 + 64, 768 + -1693032223 * this.field4919, -50, -10, -50);
            } else {
               var11.field1876 = (short)(64 + 54395613 * this.field4950);
               var11.field1877 = (short)(768 + -1693032223 * this.field4919);
               ModelData.method4048(var11);
               var10 = var11;
            }

            field4970.method6464((classVJ)var10, var8);
         }

         if (this.field4930) {
            if (var7 >= 682279145) {
               throw new IllegalStateException();
            }

            var10 = ((ModelData)var10).method4010();
         }

         if (this.field4947 * 1531627111 >= 0) {
            if (var7 >= 682279145) {
               throw new IllegalStateException();
            }

            if (var10 instanceof Model) {
               if (var7 >= 682279145) {
                  throw new IllegalStateException();
               }

               var10 = ((Model)var10).method5258(var3, var4, var5, var6, true, 1531627111 * this.field4947);
            } else if (var10 instanceof ModelData) {
               if (var7 >= 682279145) {
                  throw new IllegalStateException();
               }

               var10 = ModelData.method4014((ModelData)var10, var3, var4, var5, var6, true, this.field4947 * 1531627111);
            }
         }

         return (Renderable)var10;
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "om.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II[[IIIILou;II)Lfx;")
   @ObfuscatedName("ao")
   public final Model method8630(int var1, int var2, int[][] var3, int var4, int var5, int var6, Animation var7, int var8, int var9) {
      return this.method8629(rl21.field5726, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("rl")
   public boolean method8632() {
      return this.method8567(-874336012);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IZ)V")
   @ObfuscatedName("oo")
   public void method8626(classXY var1, int var2, boolean var3) {
      if (this.field4947 * 1531627111 > 0 && (client.field1021 & 16) == 0) {
         this.field4947 = -1 * 450557271;
      }

      PostObjectComposition var4 = new PostObjectComposition(this);
      classOE.field4841.getCallbacks().post(var4);
   }

   public void setMapIconId(int var1) {
      this.field4951 = var1 * 1022644465;
   }

   public int getMapSceneId() {
      return this.field4952 * 1680279607;
   }

   @ObfuscatedSignature(descriptor = "(II)Ler;")
   @ObfuscatedName("bf")
   final ModelData method8578(int var1, int var2) {
      ModelData var3 = null;
      if (this.field4937 == null) {
         if (10 != var1) {
            return null;
         }

         if (this.field4918 == null) {
            return null;
         }

         boolean var4 = this.field4953;
         if (var1 == 2 && var2 > 3) {
            var4 = !var4;
         }

         int var5 = this.field4918.length;

         for (int var6 = 0; var6 < var5; var6++) {
            int var7 = this.field4918[var6];
            int var8 = var7;
            if (var4) {
               var8 = -var7;
            }

            var3 = (ModelData)NodeCache.method6460(field4966, var8);
            if (var3 == null) {
               var3 = ModelData.method4067(classKD.field3552, var7, 0);
               if (null == var3) {
                  return null;
               }

               if (var4) {
                  var3.method4039();
               }

               field4966.method6464(var3, var8);
            }

            if (var5 > 1) {
               field4967[var6] = var3;
            }
         }

         if (var5 > 1) {
            var3 = new ModelData(field4967, var5);
         }
      } else {
         int var10 = -1;

         for (int var12 = 0; var12 < this.field4937.length; var12++) {
            if (this.field4937[var12] == var1) {
               var10 = var12;
               break;
            }
         }

         if (-1 == var10) {
            return null;
         }

         int var13 = this.field4918[var10];
         int var15 = var13;
         boolean var17 = this.field4953 ^ var2 > 3;
         if (var17) {
            var15 = var13 + 65536;
         }

         var3 = (ModelData)NodeCache.method6460(field4966, var15);
         if (var3 == null) {
            var3 = ModelData.method4067(classKD.field3552, var13, 0);
            if (null == var3) {
               return null;
            }

            if (var17) {
               var3.method4039();
            }

            field4966.method6464(var3, var15);
         }
      }

      boolean var11;
      if (-1739570015 * this.field4955 == 128 && 1556070562 * this.field4927 == 678446451 && 128 == 1247638989 * this.field4956) {
         var11 = false;
      } else {
         var11 = true;
      }

      boolean var14;
      if (0 == 1498747178 * this.field4957 && 0 == 2128147575 * this.field4958 && 0 == this.field4959 * 410541903) {
         var14 = false;
      } else {
         var14 = true;
      }

      ModelData var16 = new ModelData(var3, 0 == var2 && !var11 && !var14, null == this.field4938, null == this.field4940, true);
      if (var1 == 4 && var2 > 3) {
         var16.method4027(256);
         var16.method4072(45, 0, -45);
      }

      var2 &= 3;
      if (1 == var2) {
         var16.method4073();
      } else if (2 == var2) {
         ModelData.method4078(var16);
      } else if (3 == var2) {
         Ignore.method10238(var16);
      }

      if (null != this.field4938) {
         for (int var18 = 0; var18 < this.field4938.length; var18++) {
            var16.method4031(this.field4938[var18], this.field4939[var18]);
         }
      }

      if (null != this.field4940) {
         for (int var19 = 0; var19 < this.field4940.length; var19++) {
            var16.method4035(this.field4940[var19], this.field4941[var19]);
         }
      }

      if (var11) {
         classSG.method10466(var16, 465675757 * this.field4955, this.field4927 * -1787252995, 927725997 * this.field4956);
      }

      if (var14) {
         var16.method4072(-2036368304 * this.field4957, -1514674730 * this.field4958, this.field4959 * -1155641687);
      }

      return var16;
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("aq")
   public long method8591(int var1, long var2) {
      try {
         return classBW.method1275(this.field4929, var1, var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "om.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("ad")
   public String method8596(int var1, String var2, int var3) {
      try {
         return classFS.method4998(this.field4929, var1, var2, (byte)117);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "om.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ap")
   public boolean method8601(int var1) {
      try {
         if (null == this.field4928) {
            if (var1 != 1754865660) {
               throw new IllegalStateException();
            } else {
               if (-1727985133 * this.field4961.field6687 == -1) {
                  if (null == this.field4932.field6783) {
                     return false;
                  }

                  if (var1 != 1754865660) {
                     throw new IllegalStateException();
                  }
               }

               return true;
            }
         } else {
            int[] var2 = this.field4928;
            int var3 = 0;

            while (true) {
               if (var3 >= var2.length) {
                  return false;
               }

               int var4 = var2[var3];
               if (var4 != -1) {
                  if (var1 != 1754865660) {
                     throw new IllegalStateException();
                  }

                  ObjectComposition var5 = classMU.method7771(var4, 1742402583);
                  if (var5.field4961.field6687 * -1727985133 != -1) {
                     break;
                  }

                  if (null != var5.field4932.field6783) {
                     if (var1 != 1754865660) {
                        throw new IllegalStateException();
                     }
                     break;
                  }
               }

               var3++;
            }

            return true;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "om.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lwj;")
   @ObfuscatedName("au")
   public classWJ method8606(int var1) {
      try {
         return this.field4961;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "om.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lww;")
   @ObfuscatedName("ai")
   public classWW method8608(int var1) {
      try {
         return this.field4932;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "om.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lwj;")
   @ObfuscatedName("cn")
   public classWJ method8607() {
      return this.field4961;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bm")
   public static void method8556() {
      field4964.method6468();
      field4966.method6468();
      field4970.method6468();
      field4965.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;IJ)J")
   @ObfuscatedName("fx")
   public static long method8592(ObjectComposition var0, int var1, long var2) {
      return classBW.method1275(var0.field4929, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cy")
   public boolean method8602() {
      if (null == this.field4928) {
         return -1727985133 * this.field4961.field6687 != -1 || null != this.field4932.field6783;
      } else {
         int[] var1 = this.field4928;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            if (var3 != -1) {
               ObjectComposition var4 = classMU.method7771(var3, 1386702190);
               if (var4.field4961.field6687 * -1727985133 != -1 || null != var4.field4932.field6783) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()Loz;")
   @ObfuscatedName("av")
   public classOZ method8546() {
      return this.field4933;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("sk")
   public int method8633() {
      return this.field4942 * -582306509;
   }

   public int getMapIconId() {
      return this.field4951 * 1412472849;
   }

   public String[] getActions() {
      String[] var1 = new String[this.field4933.field5361.size()];

      for (int var2 = 0; var2 < this.field4933.field5361.size(); var2++) {
         classPB var3 = (classPB)this.field4933.field5361.get(var2);
         if (var3 != null) {
            var1[var2] = var3.field5208;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Loz;")
   @ObfuscatedName("at")
   public classOZ method8547() {
      return this.field4933;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;Z)V")
   @ObfuscatedName("an")
   public static void method8549(IndexDataBase var0, IndexDataBase var1, boolean var2) {
      classAK.field213 = var0;
      classKD.field3552 = var1;
      field4969 = var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;Z)V")
   @ObfuscatedName("am")
   public static void method8550(IndexDataBase var0, IndexDataBase var1, boolean var2) {
      classAK.field213 = var0;
      classKD.field3552 = var1;
      field4969 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Lom;II)Z")
   @ObfuscatedName("mq")
   public static boolean method8562(ObjectComposition var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method8566(var1, var1);
      } else {
         try {
            if (null == var0.field4937) {
               if (var2 != -656313384) {
                  throw new IllegalStateException();
               } else if (null == var0.field4918) {
                  return true;
               } else if (10 != var1) {
                  return true;
               } else {
                  boolean var6 = true;

                  for (int var4 = 0; var4 < var0.field4918.length; var4++) {
                     if (var2 != -656313384) {
                        throw new IllegalStateException();
                     }

                     var6 &= classKD.field3552.method11944(var0.field4918[var4], 0, (byte)-51);
                  }

                  return var6;
               }
            } else {
               for (int var3 = 0; var3 < var0.field4937.length; var3++) {
                  if (var2 != -656313384) {
                     throw new IllegalStateException();
                  }

                  if (var1 == var0.field4937[var3]) {
                     if (var2 != -656313384) {
                        throw new IllegalStateException();
                     }

                     return classKD.field3552.method11944(var0.field4918[var3], 0, (byte)-54);
                  }
               }

               return true;
            }
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "om.as(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lww;")
   @ObfuscatedName("ch")
   public classWW method8609() {
      return this.field4932;
   }

   public net.runelite.api.IterableHashTable getParams() {
      return this.field4929;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lom;")
   @ObfuscatedName("bn")
   public static ObjectComposition method8551(int var0) {
      ObjectComposition var1 = (ObjectComposition)NodeCache.method6460(field4964, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classAK.field213.method11942(6, var0, -2130343881);
         var1 = new ObjectComposition(new classXY(var2), var0, true);
         field4964.method6464(var1, var0);
         return var1;
      }
   }

   public String getName() {
      return this.field4943;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;)Lom;")
   @ObfuscatedName("am")
   public static ObjectComposition method8582(ObjectComposition var0) {
      if (var0 == null) {
         var0.getVarPlayerId();
      }

      int var1 = -1;
      if (1087117325 * var0.field4924 != -1) {
         var1 = classEM.method3946(var0.field4924 * 1087117325, -1577287914);
      } else if (-1 != -1047341117 * var0.field4960) {
         var1 = classLB.field4088[var0.field4960 * -1047341117];
      }

      int var2;
      if (var1 >= 0 && var1 < var0.field4928.length - 1) {
         var2 = var0.field4928[var1];
      } else {
         var2 = var0.field4928[var0.field4928.length - 1];
      }

      return -1 != var2 ? classMU.method7771(var2, 1740798143) : null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lom;")
   @ObfuscatedName("bx")
   public static ObjectComposition method8552(int var0) {
      ObjectComposition var1 = (ObjectComposition)NodeCache.method6460(field4964, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classAK.field213.method11942(6, var0, -2109516495);
         var1 = new ObjectComposition(new classXY(var2), var0, true);
         field4964.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;)Z")
   @ObfuscatedName("zv")
   public static boolean method8568(ObjectComposition var0) {
      if (null == var0.field4918) {
         return true;
      } else {
         boolean var1 = true;

         for (int var2 = 0; var2 < var0.field4918.length; var2++) {
            var1 &= classKD.field3552.method11944(var0.field4918[var2], 0, (byte)23);
         }

         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lom;")
   @ObfuscatedName("bc")
   public static ObjectComposition method8553(int var0) {
      ObjectComposition var1 = (ObjectComposition)NodeCache.method6460(field4964, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classAK.field213.method11942(6, var0, -1624545876);
         var1 = new ObjectComposition(new classXY(var2), var0, true);
         field4964.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lom;")
   @ObfuscatedName("bp")
   public static ObjectComposition method8554(int var0) {
      ObjectComposition var1 = (ObjectComposition)NodeCache.method6460(field4964, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classAK.field213.method11942(6, var0, -1352222775);
         var1 = new ObjectComposition(new classXY(var2), var0, true);
         field4964.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("br")
   public static void method8557() {
      field4964.method6468();
      field4966.method6468();
      field4970.method6468();
      field4965.method6468();
   }

   @ObfuscatedSignature(descriptor = "()Lom;")
   @ObfuscatedName("xf")
   public ObjectComposition method8634() {
      return method8585(this, 1360030034);
   }

   public void setMapSceneId(int var1) {
      this.field4952 = var1 * -2041631353;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("dc")
   public String method8620() {
      return this.field4943;
   }

   @ObfuscatedSignature(descriptor = "(Lom;III)Ler;")
   @ObfuscatedName("sb")
   public static ModelData method8627(ObjectComposition var0, int var1, int var2, int var3) {
      if (var0 == null) {
         return var0.method8628(var1, var1, var1);
      } else {
         synchronized (field4966) {
            int var8 = var2;
            int var7 = var1;
            ObjectComposition var6 = var0;
            int var9 = -1973084331;

            ModelData var34;
            try {
               ModelData var10 = null;
               if (var6.field4937 == null) {
                  if (10 != var7) {
                     return null;
                  }

                  if (var6.field4918 == null) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }

                  boolean var11 = var6.field4953;
                  if (var7 == 2) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     if (var8 > 3) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        boolean var10000;
                        if (!var11) {
                           if (var9 != -1973084331) {
                              throw new IllegalStateException();
                           }

                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        var11 = var10000;
                     }
                  }

                  int var12 = var6.field4918.length;

                  for (int var13 = 0; var13 < var12; var13++) {
                     int var14 = var6.field4918[var13];
                     int var15 = var14;
                     if (var11) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        var15 = -var14;
                     }

                     var10 = (ModelData)NodeCache.method6460(field4966, var15);
                     if (var10 == null) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        var10 = ModelData.method4067(classKD.field3552, var14, 0);
                        if (null == var10) {
                           if (var9 != -1973084331) {
                              throw new IllegalStateException();
                           }

                           return null;
                        }

                        if (var11) {
                           if (var9 != -1973084331) {
                              throw new IllegalStateException();
                           }

                           var10.method4039();
                        }

                        field4966.method6464(var10, var15);
                     }

                     if (var12 > 1) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        field4967[var13] = var10;
                     }
                  }

                  if (var12 > 1) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     var10 = new ModelData(field4967, var12);
                  }
               } else {
                  int var24 = -1;

                  for (int var26 = 0; var26 < var6.field4937.length; var26++) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     if (var6.field4937[var26] == var7) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        var24 = var26;
                        break;
                     }
                  }

                  if (-1 == var24) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }

                  int var27 = var6.field4918[var24];
                  int var29 = var27;
                  boolean var10001;
                  if (var8 > 3) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     var10001 = true;
                  } else {
                     var10001 = false;
                  }

                  boolean var31 = var6.field4953 ^ var10001;
                  if (var31) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     var29 = var27 + 65536;
                  }

                  var10 = (ModelData)NodeCache.method6460(field4966, var29);
                  if (var10 == null) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     var10 = ModelData.method4067(classKD.field3552, var27, 0);
                     if (null == var10) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        return null;
                     }

                     if (var31) {
                        var10.method4039();
                     }

                     field4966.method6464(var10, var29);
                  }
               }

               boolean var25;
               label325: {
                  if (-1739570015 * var6.field4955 == 128 && -1787252995 * var6.field4927 == 128) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     if (128 == 927725997 * var6.field4956) {
                        var25 = false;
                        break label325;
                     }

                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }
                  }

                  var25 = true;
               }

               boolean var28;
               label317: {
                  if (0 == 916605819 * var6.field4957 && 0 == 2128147575 * var6.field4958) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     if (0 == var6.field4959 * -1155641687) {
                        var28 = false;
                        break label317;
                     }

                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }
                  }

                  var28 = true;
               }

               boolean var10003;
               label309: {
                  var34 = new ModelData;
                  if (0 == var8) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     if (!var25) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        if (!var28) {
                           if (var9 != -1973084331) {
                              throw new IllegalStateException();
                           }

                           var10003 = true;
                           break label309;
                        }
                     }
                  }

                  var10003 = false;
               }

               boolean var10004;
               if (null == var6.field4938) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var10004 = true;
               } else {
                  var10004 = false;
               }

               boolean var10005;
               if (null == var6.field4940) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var10005 = true;
               } else {
                  var10005 = false;
               }

               var34./* $VF: Unable to resugar constructor */<init>(var10, var10003, var10004, var10005, true);
               ModelData var30 = var34;
               if (var7 == 4) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  if (var8 > 3) {
                     var30.method4027(256);
                     var30.method4072(45, 0, -45);
                  }
               }

               var8 &= 3;
               if (1 == var8) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var30.method4073();
               } else if (2 == var8) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  ModelData.method4078(var30);
               } else if (3 == var8) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  Ignore.method10238(var30);
               }

               if (null != var6.field4938) {
                  for (int var32 = 0; var32 < var6.field4938.length; var32++) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     var30.method4031(var6.field4938[var32], var6.field4939[var32]);
                  }
               }

               if (null != var6.field4940) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  for (int var33 = 0; var33 < var6.field4940.length; var33++) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     var30.method4035(var6.field4940[var33], var6.field4941[var33]);
                  }
               }

               if (var25) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  classSG.method10466(var30, -1739570015 * var6.field4955, var6.field4927 * -1787252995, 927725997 * var6.field4956);
               }

               if (var28) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var30.method4072(916605819 * var6.field4957, 2128147575 * var6.field4958, var6.field4959 * -1155641687);
               }

               var34 = var30;
            } catch (RuntimeException var16) {
               throw classEG.method3899(var16, "om.al(" + ')');
            }

            return var34;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bt")
   public static void method8558() {
      field4964.method6468();
      field4966.method6468();
      field4970.method6468();
      field4965.method6468();
   }

   @ObfuscatedSignature(descriptor = "(Lom;II)I")
   @ObfuscatedName("ru")
   public static int method8611(ObjectComposition var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method8616(var1, var1);
      } else {
         try {
            if (var1 != 1) {
               if (var2 >= -1327280404) {
                  throw new IllegalStateException();
               }

               if (3 != var1) {
                  return var0.field4942 * -582306509;
               }
            }

            return 402876423 * var0.field4922;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "om.ar(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpo;")
   @ObfuscatedName("bg")
   EntityOps method8559() {
      return new EntityOps();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("dd")
   public String method8621() {
      return this.field4943;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ba")
   public final boolean method8569() {
      if (null == this.field4918) {
         return true;
      } else {
         boolean var1 = true;

         for (int var2 = 0; var2 < this.field4918.length; var2++) {
            var1 &= classKD.field3552.method11944(this.field4918[var2], 0, (byte)60);
         }

         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lom;")
   @ObfuscatedName("ah")
   public static ObjectComposition method8555(int var0) {
      ObjectComposition var1 = (ObjectComposition)NodeCache.method6460(field4964, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classAK.field213.method11942(6, var0, -2041051687);
         var1 = new ObjectComposition(new classXY(var2), var0, true);
         field4964.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ci")
   public int method8617(int var1) {
      return 1 != var1 && 3 != var1 ? this.field4922 * 402876423 : -582306509 * this.field4942;
   }

   public int[] getImpostorIds() {
      return this.field4928;
   }

   public int getVarbitId() {
      return this.field4924 * 1087117325;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bo")
   public final boolean method8563(int var1) {
      if (null != this.field4937) {
         for (int var4 = 0; var4 < this.field4937.length; var4++) {
            if (var1 == this.field4937[var4]) {
               return classKD.field3552.method11944(this.field4918[var4], 0, (byte)-120);
            }
         }

         return true;
      } else if (null == this.field4918) {
         return true;
      } else if (10 != var1) {
         return true;
      } else {
         boolean var2 = true;

         for (int var3 = 0; var3 < this.field4918.length; var3++) {
            var2 &= classKD.field3552.method11944(this.field4918[var3], 0, (byte)-19);
         }

         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ce")
   public int method8612(int var1) {
      return var1 != 1 && 3 != var1 ? this.field4942 * -582306509 : 402876423 * this.field4922;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;II[[IIII)Lfx;")
   @ObfuscatedName("xe")
   public static Model method8574(ObjectComposition var0, int var1, int var2, int[][] var3, int var4, int var5, int var6) {
      if (var0 == null) {
         var0.method8576(var1, var1, var3, var1, var1, var1);
      }

      long var7;
      if (null == var0.field4937) {
         var7 = (var0.field4920 * -381960671 << 10) + var2;
      } else {
         var7 = (var1 << 3) + (var0.field4920 * -381960671 << 10) + var2;
      }

      Model var9 = (Model)NodeCache.method6460(field4965, var7);
      if (null == var9) {
         ModelData var10 = method8627(var0, var1, var2, -1973084331);
         if (null == var10) {
            return null;
         }

         var9 = var10.method4070(var0.field4950 * 54395613 + 64, var0.field4919 * -1693032223 + 768, -50, -10, -50);
         field4965.method6464(var9, var7);
      }

      if (var0.field4947 * 1531627111 >= 0) {
         var9 = var9.method5258(var3, var4, var5, var6, true, 1531627111 * var0.field4947);
      }

      return var9;
   }

   @ObfuscatedSignature(descriptor = "(II[[IIIILou;I)Lfx;")
   @ObfuscatedName("by")
   public final Model method8577(int var1, int var2, int[][] var3, int var4, int var5, int var6, Animation var7, int var8) {
      long var9;
      if (this.field4937 == null) {
         var9 = (this.field4920 * -381960671 << 10) + var2;
      } else {
         var9 = var2 + (var1 << 3) + (this.field4920 * 1814617244 << 10);
      }

      Model var11 = (Model)NodeCache.method6460(field4965, var9);
      if (var11 == null) {
         ModelData var12 = method8627(this, var1, var2, -1973084331);
         if (var12 == null) {
            return null;
         }

         var11 = var12.method4070(-1042493251 + this.field4950 * 855392251, -1385446499 + this.field4919 * -1693032223, -1803986547, -10, -249356147);
         field4965.method6464(var11, var9);
      }

      if (var7 == null && -1 == this.field4947 * 1531627111) {
         return var11;
      } else {
         if (null != var7) {
            var11 = Animation.method8826(var7, var11, var8, var2, (byte)55);
         } else {
            var11 = var11.method5106(true);
         }

         if (this.field4947 * 1531627111 >= 0 && var3 != null) {
            var11 = var11.method5258(var3, var4, var5, var6, false, -1799680311 * this.field4947);
         }

         return var11;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Ler;")
   @ObfuscatedName("bs")
   final ModelData method8579(int var1, int var2) {
      ModelData var3 = null;
      if (this.field4937 == null) {
         if (10 != var1) {
            return null;
         }

         if (this.field4918 == null) {
            return null;
         }

         boolean var4 = this.field4953;
         if (var1 == 2 && var2 > 3) {
            var4 = !var4;
         }

         int var5 = this.field4918.length;

         for (int var6 = 0; var6 < var5; var6++) {
            int var7 = this.field4918[var6];
            int var8 = var7;
            if (var4) {
               var8 = -var7;
            }

            var3 = (ModelData)NodeCache.method6460(field4966, var8);
            if (var3 == null) {
               var3 = ModelData.method4067(classKD.field3552, var7, 0);
               if (null == var3) {
                  return null;
               }

               if (var4) {
                  var3.method4039();
               }

               field4966.method6464(var3, var8);
            }

            if (var5 > 1) {
               field4967[var6] = var3;
            }
         }

         if (var5 > 1) {
            var3 = new ModelData(field4967, var5);
         }
      } else {
         int var10 = -1;

         for (int var12 = 0; var12 < this.field4937.length; var12++) {
            if (this.field4937[var12] == var1) {
               var10 = var12;
               break;
            }
         }

         if (-1 == var10) {
            return null;
         }

         int var13 = this.field4918[var10];
         int var15 = var13;
         boolean var17 = this.field4953 ^ var2 > 3;
         if (var17) {
            var15 = var13 + -747842956;
         }

         var3 = (ModelData)NodeCache.method6460(field4966, var15);
         if (var3 == null) {
            var3 = ModelData.method4067(classKD.field3552, var13, 0);
            if (null == var3) {
               return null;
            }

            if (var17) {
               var3.method4039();
            }

            field4966.method6464(var3, var15);
         }
      }

      boolean var11;
      if (909269177 * this.field4955 == 128 && -1118843512 * this.field4927 == 128 && 116012078 == 927725997 * this.field4956) {
         var11 = false;
      } else {
         var11 = true;
      }

      boolean var14;
      if (0 == 916605819 * this.field4957 && 0 == 2128147575 * this.field4958 && 0 == this.field4959 * -1155641687) {
         var14 = false;
      } else {
         var14 = true;
      }

      ModelData var16 = new ModelData(var3, 0 == var2 && !var11 && !var14, null == this.field4938, null == this.field4940, true);
      if (var1 == 4 && var2 > 3) {
         var16.method4027(256);
         var16.method4072(45, 0, -45);
      }

      var2 &= 3;
      if (1 == var2) {
         var16.method4073();
      } else if (2 == var2) {
         ModelData.method4078(var16);
      } else if (3 == var2) {
         Ignore.method10238(var16);
      }

      if (null != this.field4938) {
         for (int var18 = 0; var18 < this.field4938.length; var18++) {
            var16.method4031(this.field4938[var18], this.field4939[var18]);
         }
      }

      if (null != this.field4940) {
         for (int var19 = 0; var19 < this.field4940.length; var19++) {
            var16.method4035(this.field4940[var19], this.field4941[var19]);
         }
      }

      if (var11) {
         classSG.method10466(var16, -1739570015 * this.field4955, this.field4927 * -1787252995, 927725997 * this.field4956);
      }

      if (var14) {
         var16.method4072(916605819 * this.field4957, 2128147575 * this.field4958, this.field4959 * -1155641687);
      }

      return var16;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bk")
   public final boolean method8564(int var1) {
      if (null != this.field4937) {
         for (int var4 = 0; var4 < this.field4937.length; var4++) {
            if (var1 == this.field4937[var4]) {
               return classKD.field3552.method11944(this.field4918[var4], 0, (byte)42);
            }
         }

         return true;
      } else if (null == this.field4918) {
         return true;
      } else if (10 != var1) {
         return true;
      } else {
         boolean var2 = true;

         for (int var3 = 0; var3 < this.field4918.length; var3++) {
            var2 &= classKD.field3552.method11944(this.field4918[var3], 0, (byte)29);
         }

         return var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;I)Z")
   @ObfuscatedName("vh")
   public static boolean method8565(ObjectComposition var0, int var1) {
      if (null != var0.field4937) {
         for (int var4 = 0; var4 < var0.field4937.length; var4++) {
            if (var1 == var0.field4937[var4]) {
               return classKD.field3552.method11944(var0.field4918[var4], 0, (byte)-40);
            }
         }

         return true;
      } else if (null == var0.field4918) {
         return true;
      } else if (10 != var1) {
         return true;
      } else {
         boolean var2 = true;

         for (int var3 = 0; var3 < var0.field4918.length; var3++) {
            var2 &= classKD.field3552.method11944(var0.field4918[var3], 0, (byte)-66);
         }

         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lom;")
   @ObfuscatedName("bl")
   public final ObjectComposition method8583() {
      int var1 = -1;
      if (1087117325 * this.field4924 != -1) {
         var1 = classEM.method3946(this.field4924 * 1087117325, -1660517570);
      } else if (-1 != -1047341117 * this.field4960) {
         var1 = classLB.field4088[this.field4960 * -1047341117];
      }

      int var2;
      if (var1 >= 0 && var1 < this.field4928.length - 1) {
         var2 = this.field4928[var1];
      } else {
         var2 = this.field4928[this.field4928.length - 1];
      }

      return -1 != var2 ? classMU.method7771(var2, 1941489345) : null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;II)Ler;")
   @ObfuscatedName("hn")
   public static ModelData method8580(ObjectComposition var0, int var1, int var2) {
      if (var0 == null) {
         var0.getVarPlayerId();
      }

      ModelData var3 = null;
      if (var0.field4937 == null) {
         if (10 != var1) {
            return null;
         }

         if (var0.field4918 == null) {
            return null;
         }

         boolean var4 = var0.field4953;
         if (var1 == 2 && var2 > 3) {
            var4 = !var4;
         }

         int var5 = var0.field4918.length;

         for (int var6 = 0; var6 < var5; var6++) {
            int var7 = var0.field4918[var6];
            int var8 = var7;
            if (var4) {
               var8 = -var7;
            }

            var3 = (ModelData)NodeCache.method6460(field4966, var8);
            if (var3 == null) {
               var3 = ModelData.method4067(classKD.field3552, var7, 0);
               if (null == var3) {
                  return null;
               }

               if (var4) {
                  var3.method4039();
               }

               field4966.method6464(var3, var8);
            }

            if (var5 > 1) {
               field4967[var6] = var3;
            }
         }

         if (var5 > 1) {
            var3 = new ModelData(field4967, var5);
         }
      } else {
         int var10 = -1;

         for (int var12 = 0; var12 < var0.field4937.length; var12++) {
            if (var0.field4937[var12] == var1) {
               var10 = var12;
               break;
            }
         }

         if (-1 == var10) {
            return null;
         }

         int var13 = var0.field4918[var10];
         int var15 = var13;
         boolean var17 = var0.field4953 ^ var2 > 3;
         if (var17) {
            var15 = var13 + 65536;
         }

         var3 = (ModelData)NodeCache.method6460(field4966, var15);
         if (var3 == null) {
            var3 = ModelData.method4067(classKD.field3552, var13, 0);
            if (null == var3) {
               return null;
            }

            if (var17) {
               var3.method4039();
            }

            field4966.method6464(var3, var15);
         }
      }

      boolean var11;
      if (-1739570015 * var0.field4955 == 128 && -1787252995 * var0.field4927 == 128 && 128 == 927725997 * var0.field4956) {
         var11 = false;
      } else {
         var11 = true;
      }

      boolean var14;
      if (0 == 916605819 * var0.field4957 && 0 == 2128147575 * var0.field4958 && 0 == var0.field4959 * -1155641687) {
         var14 = false;
      } else {
         var14 = true;
      }

      ModelData var16 = new ModelData(var3, 0 == var2 && !var11 && !var14, null == var0.field4938, null == var0.field4940, true);
      if (var1 == 4 && var2 > 3) {
         var16.method4027(256);
         var16.method4072(45, 0, -45);
      }

      var2 &= 3;
      if (1 == var2) {
         var16.method4073();
      } else if (2 == var2) {
         ModelData.method4078(var16);
      } else if (3 == var2) {
         Ignore.method10238(var16);
      }

      if (null != var0.field4938) {
         for (int var18 = 0; var18 < var0.field4938.length; var18++) {
            var16.method4031(var0.field4938[var18], var0.field4939[var18]);
         }
      }

      if (null != var0.field4940) {
         for (int var19 = 0; var19 < var0.field4940.length; var19++) {
            var16.method4035(var0.field4940[var19], var0.field4941[var19]);
         }
      }

      if (var11) {
         classSG.method10466(var16, -1739570015 * var0.field4955, var0.field4927 * -1787252995, 927725997 * var0.field4956);
      }

      if (var14) {
         var16.method4072(916605819 * var0.field4957, 2128147575 * var0.field4958, var0.field4959 * -1155641687);
      }

      return var16;
   }

   @ObfuscatedSignature(descriptor = "()Lom;")
   @ObfuscatedName("bv")
   public final ObjectComposition method8584() {
      int var1 = -1;
      if (1087117325 * this.field4924 != -1) {
         var1 = classEM.method3946(this.field4924 * 1087117325, -1287755388);
      } else if (-1 != -1047341117 * this.field4960) {
         var1 = classLB.field4088[this.field4960 * -1047341117];
      }

      int var2;
      if (var1 >= 0 && var1 < this.field4928.length - 1) {
         var2 = this.field4928[var1];
      } else {
         var2 = this.field4928[this.field4928.length - 1];
      }

      return -1 != var2 ? classMU.method7771(var2, 1488710693) : null;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bz")
   public int method8587(int var1, int var2) {
      return classGQ.method5635(this.field4929, var1, var2, -1434352038);
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("cr")
   public int method8588(int var1, int var2) {
      return classGQ.method5635(this.field4929, var1, var2, -966746575);
   }

   static {
      field4965.method6473(256);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cx")
   public int method8618(int var1) {
      return 1 != var1 && 3 != var1 ? this.field4922 * 402876423 : -582306509 * this.field4942;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;II)Ler;")
   @ObfuscatedName("yz")
   public static ModelData method8581(ObjectComposition var0, int var1, int var2) {
      if (var0 == null) {
         var0.method8613(var1);
      }

      ModelData var3 = null;
      if (var0.field4937 == null) {
         if (10 != var1) {
            return null;
         }

         if (var0.field4918 == null) {
            return null;
         }

         boolean var4 = var0.field4953;
         if (var1 == 2 && var2 > 3) {
            var4 = !var4;
         }

         int var5 = var0.field4918.length;

         for (int var6 = 0; var6 < var5; var6++) {
            int var7 = var0.field4918[var6];
            int var8 = var7;
            if (var4) {
               var8 = -var7;
            }

            var3 = (ModelData)NodeCache.method6460(field4966, var8);
            if (var3 == null) {
               var3 = ModelData.method4067(classKD.field3552, var7, 0);
               if (null == var3) {
                  return null;
               }

               if (var4) {
                  var3.method4039();
               }

               field4966.method6464(var3, var8);
            }

            if (var5 > 1) {
               field4967[var6] = var3;
            }
         }

         if (var5 > 1) {
            var3 = new ModelData(field4967, var5);
         }
      } else {
         int var10 = -1;

         for (int var12 = 0; var12 < var0.field4937.length; var12++) {
            if (var0.field4937[var12] == var1) {
               var10 = var12;
               break;
            }
         }

         if (-1 == var10) {
            return null;
         }

         int var13 = var0.field4918[var10];
         int var15 = var13;
         boolean var17 = var0.field4953 ^ var2 > 3;
         if (var17) {
            var15 = var13 + 65536;
         }

         var3 = (ModelData)NodeCache.method6460(field4966, var15);
         if (var3 == null) {
            var3 = ModelData.method4067(classKD.field3552, var13, 0);
            if (null == var3) {
               return null;
            }

            if (var17) {
               var3.method4039();
            }

            field4966.method6464(var3, var15);
         }
      }

      boolean var11;
      if (-1739570015 * var0.field4955 == -646336826 && 2050582693 * var0.field4927 == 128 && 1247934193 == 1806556256 * var0.field4956) {
         var11 = false;
      } else {
         var11 = true;
      }

      boolean var14;
      if (0 == 2086558097 * var0.field4957 && 0 == 2128147575 * var0.field4958 && 0 == var0.field4959 * -1575524189) {
         var14 = false;
      } else {
         var14 = true;
      }

      ModelData var16 = new ModelData(var3, 0 == var2 && !var11 && !var14, null == var0.field4938, null == var0.field4940, true);
      if (var1 == 4 && var2 > 3) {
         var16.method4027(465718752);
         var16.method4072(-761527048, 0, -45);
      }

      var2 &= 3;
      if (1 == var2) {
         var16.method4073();
      } else if (2 == var2) {
         ModelData.method4078(var16);
      } else if (3 == var2) {
         Ignore.method10238(var16);
      }

      if (null != var0.field4938) {
         for (int var18 = 0; var18 < var0.field4938.length; var18++) {
            var16.method4031(var0.field4938[var18], var0.field4939[var18]);
         }
      }

      if (null != var0.field4940) {
         for (int var19 = 0; var19 < var0.field4940.length; var19++) {
            var16.method4035(var0.field4940[var19], var0.field4941[var19]);
         }
      }

      if (var11) {
         classSG.method10466(var16, -1739570015 * var0.field4955, var0.field4927 * 840287888, 927725997 * var0.field4956);
      }

      if (var14) {
         var16.method4072(916605819 * var0.field4957, 2128147575 * var0.field4958, var0.field4959 * -1155641687);
      }

      return var16;
   }

   public int getVarPlayerId() {
      return this.field4960 * -1047341117;
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("ct")
   public long method8593(int var1, long var2) {
      return classBW.method1275(this.field4929, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(II)Ler;")
   @ObfuscatedName("mq")
   public ModelData method8635(int var1, int var2) {
      return method8627(this, var1, var2, -1973084331);
   }

   @ObfuscatedSignature(descriptor = "(Lom;B)Lpo;")
   @ObfuscatedName("ut")
   public static EntityOps method8560(ObjectComposition var0, byte var1) {
      if (var0 == null) {
         return var0.method8561(var1);
      } else {
         try {
            return new EntityOps();
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "om.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cq")
   public String method8597(int var1, String var2) {
      return classFS.method4998(this.field4929, var1, var2, (byte)11);
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aw")
   public int method8619(int var1, int var2) {
      try {
         if (1 != var1) {
            if (var2 <= -543461949) {
               throw new IllegalStateException();
            }

            if (3 != var1) {
               return this.field4922 * 402876423;
            }

            if (var2 <= -543461949) {
               throw new IllegalStateException();
            }
         }

         return -582306509 * this.field4942;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "om.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cd")
   public String method8598(int var1, String var2) {
      return classFS.method4998(this.field4929, var1, var2, (byte)96);
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cv")
   public String method8599(int var1, String var2) {
      return classFS.method4998(this.field4929, var1, var2, (byte)48);
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;II[[IIIILou;I)Lfx;")
   @ObfuscatedName("fv")
   public Model method8629(
      @Nullable rl21 param1, int nullx, int nullxx, int[][] nullxxx, int nullxxxx, int nullxxxxx, int nullxxxxxx, Animation nullxxxxxxx, int nullxxxxxxxx
   ) {
      long var10;
      if (this.field4937 == null) {
         var10 = nullxx + (this.field4920 * -381960671 << 10);
      } else {
         var10 = nullxx + (nullx << 3) + (this.field4920 * -381960671 << 10);
      }

      Model var12;
      synchronized (field4965) {
         var12 = (Model)field4965.method6479(var10);
      }

      if (var12 == null) {
         ModelData var18 = this.method8635(nullx, nullxx);
         if (var18 == null) {
            return null;
         }

         var12 = var18.method4093(this.field4950 * 54395613 + 64, this.field4919 * -1693032223 + 768, -50, -10, -50);
         synchronized (field4965) {
            classZP.method14133(field4965, var12, var10);
         }
      }

      if (nullxxxxxxx == null && this.field4947 * 1531627111 == -1) {
         return var12;
      } else {
         if (nullxxxxxxx != null) {
            assert var1 != null;

            var12 = nullxxxxxxx.method8828(var1, var12, nullxxxxxxxx, nullxx);
         } else if (var1 != null) {
            var12 = var12.method5253(var1, true);
         }

         if (this.field4947 * 1531627111 >= 0 && nullxxx != null) {
            var12 = var12.method5293(nullxxx, nullxxxx, nullxxxxx, nullxxxxxx, var1 == null, this.field4947 * 1531627111);
         }

         return var12;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cs")
   public String method8600(int var1, String var2) {
      return classFS.method4998(this.field4929, var1, var2, (byte)115);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("co")
   public boolean method8603() {
      if (null == this.field4928) {
         return -1727985133 * this.field4961.field6687 != -1 || null != this.field4932.field6783;
      } else {
         int[] var1 = this.field4928;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            if (var3 != -1) {
               ObjectComposition var4 = classMU.method7771(var3, 1306610335);
               if (var4.field4961.field6687 * -1727985133 != -1 || null != var4.field4932.field6783) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Loz;")
   @ObfuscatedName("az")
   public classOZ method8548(byte var1) {
      try {
         return this.field4933;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "om.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ca")
   public boolean method8604() {
      if (null == this.field4928) {
         return -1727985133 * this.field4961.field6687 != -1 || null != this.field4932.field6783;
      } else {
         int[] var1 = this.field4928;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            if (var3 != -1) {
               ObjectComposition var4 = classMU.method7771(var3, 2003358347);
               if (var4.field4961.field6687 * -1727985133 != -1 || null != var4.field4932.field6783) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(II[[IIII)Lee;")
   @ObfuscatedName("dl")
   public Renderable method8636(int var1, int var2, int[][] var3, int var4, int var5, int var6) {
      return this.method8571(var1, var2, var3, var4, var5, var6, -663588657);
   }

   @ObfuscatedSignature(descriptor = "(II[[IIII)Lee;")
   @ObfuscatedName("bi")
   public final Renderable method8572(int var1, int var2, int[][] var3, int var4, int var5, int var6) {
      long var7;
      if (this.field4937 == null) {
         var7 = var2 + (this.field4920 * 546843132 << 10);
      } else {
         var7 = var2 + (this.field4920 * -549251343 << 10) + (var1 << 3);
      }

      Object var9 = (Renderable)NodeCache.method6460(field4970, var7);
      if (var9 == null) {
         ModelData var10 = method8627(this, var1, var2, -1973084331);
         if (var10 == null) {
            return null;
         }

         if (!this.field4930) {
            var9 = var10.method4070(2125349463 * this.field4950 + 64, -1693244053 + -1693032223 * this.field4919, -50, -10, -50);
         } else {
            var10.field1876 = (short)(1175168992 + 54395613 * this.field4950);
            var10.field1877 = (short)(-112183664 + -1693032223 * this.field4919);
            ModelData.method4048(var10);
            var9 = var10;
         }

         field4970.method6464((classVJ)var9, var7);
      }

      if (this.field4930) {
         var9 = ((ModelData)var9).method4010();
      }

      if (this.field4947 * 1531627111 >= 0) {
         if (var9 instanceof Model) {
            var9 = ((Model)var9).method5258(var3, var4, var5, var6, true, 1531627111 * this.field4947);
         } else if (var9 instanceof ModelData) {
            var9 = ModelData.method4014((ModelData)var9, var3, var4, var5, var6, true, this.field4947 * -627801912);
         }
      }

      return (Renderable)var9;
   }

   @ObfuscatedSignature(descriptor = "()Lww;")
   @ObfuscatedName("cz")
   public classWW method8610() {
      return this.field4932;
   }

   public int getSizeY() {
      return this.field4922 * 402876423;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cj")
   public int method8613(int var1) {
      return var1 != 1 && 3 != var1 ? this.field4942 * -582306509 : -78318575 * this.field4922;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cw")
   public int method8614(int var1) {
      return var1 != 1 && 3 != var1 ? this.field4942 * -582306509 : 402876423 * this.field4922;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;)Z")
   @ObfuscatedName("mp")
   public static boolean method8570(ObjectComposition var0) {
      if (null == var0.field4918) {
         return true;
      } else {
         boolean var1 = true;

         for (int var2 = 0; var2 < var0.field4918.length; var2++) {
            var1 &= classKD.field3552.method11944(var0.field4918[var2], 0, (byte)88);
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cg")
   public int method8615(int var1) {
      return var1 != 1 && 3 != var1 ? this.field4942 * -582306509 : 402876423 * this.field4922;
   }

   @ObfuscatedSignature(descriptor = "(II[[IIIII)Lfx;")
   @ObfuscatedName("aa")
   public final Model method8575(int var1, int var2, int[][] var3, int var4, int var5, int var6, int var7) {
      try {
         long var8;
         if (null == this.field4937) {
            if (var7 >= 1106423215) {
               throw new IllegalStateException();
            }

            var8 = (this.field4920 * -381960671 << 10) + var2;
         } else {
            var8 = (var1 << 3) + (this.field4920 * -381960671 << 10) + var2;
         }

         Model var10 = (Model)NodeCache.method6460(field4965, var8);
         if (null == var10) {
            if (var7 >= 1106423215) {
               throw new IllegalStateException();
            }

            ModelData var11 = method8627(this, var1, var2, -1973084331);
            if (null == var11) {
               if (var7 >= 1106423215) {
                  throw new IllegalStateException();
               }

               return null;
            }

            var10 = var11.method4070(this.field4950 * 54395613 + 64, this.field4919 * -1693032223 + 768, -50, -10, -50);
            field4965.method6464(var10, var8);
         }

         if (this.field4947 * 1531627111 >= 0) {
            if (var7 >= 1106423215) {
               throw new IllegalStateException();
            }

            var10 = var10.method5258(var3, var4, var5, var6, true, 1531627111 * this.field4947);
         }

         return var10;
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "om.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II[[IIII)Lee;")
   @ObfuscatedName("bb")
   public final Renderable method8573(int var1, int var2, int[][] var3, int var4, int var5, int var6) {
      long var7;
      if (this.field4937 == null) {
         var7 = var2 + (this.field4920 * -381960671 << 10);
      } else {
         var7 = var2 + (this.field4920 * -381960671 << 10) + (var1 << 3);
      }

      Object var9 = (Renderable)NodeCache.method6460(field4970, var7);
      if (var9 == null) {
         ModelData var10 = method8627(this, var1, var2, -1973084331);
         if (var10 == null) {
            return null;
         }

         if (!this.field4930) {
            var9 = var10.method4070(54395613 * this.field4950 + 64, 768 + -1693032223 * this.field4919, -50, -10, -50);
         } else {
            var10.field1876 = (short)(64 + 54395613 * this.field4950);
            var10.field1877 = (short)(768 + -1693032223 * this.field4919);
            ModelData.method4048(var10);
            var9 = var10;
         }

         field4970.method6464((classVJ)var9, var7);
      }

      if (this.field4930) {
         var9 = ((ModelData)var9).method4010();
      }

      if (this.field4947 * 1531627111 >= 0) {
         if (var9 instanceof Model) {
            var9 = ((Model)var9).method5258(var3, var4, var5, var6, true, 1531627111 * this.field4947);
         } else if (var9 instanceof ModelData) {
            var9 = ModelData.method4014((ModelData)var9, var3, var4, var5, var6, true, this.field4947 * 1531627111);
         }
      }

      return (Renderable)var9;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("kz")
   public boolean method8637() {
      return this.method8601(1754865660);
   }

   @ObfuscatedSignature(descriptor = "(I)Lom;")
   @ObfuscatedName("um")
   public static ObjectComposition method8638(int var0) {
      return classMU.method7771(var0, 1883040848);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("cm")
   public String method8622() {
      return this.field4943;
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("cb")
   public long method8594(int var1, long var2) {
      return classBW.method1275(this.field4929, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ck")
   public boolean method8605() {
      if (null == this.field4928) {
         return -1727985133 * this.field4961.field6687 != -1 || null != this.field4932.field6783;
      } else {
         int[] var1 = this.field4928;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            if (var3 != -1) {
               ObjectComposition var4 = classMU.method7771(var3, 1535696733);
               if (var4.field4961.field6687 * -1727985133 != -1 || null != var4.field4932.field6783) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("dm")
   public String method8623() {
      return this.field4943;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kx")
   public int method8639() {
      return this.field4922 * 402876423;
   }

   @ObfuscatedSignature(descriptor = "(Lyn;)V")
   @ObfuscatedName("pr")
   @Override
   public void vmethod537(IterableHashTable var1) {
      this.field4929 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lom;S)Ljava/lang/String;")
   @ObfuscatedName("pf")
   public static String method8624(ObjectComposition var0, short var1) {
      if (var0 == null) {
         return var0.method8625(var1);
      } else {
         try {
            return var0.field4943;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "om.ak(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lpo;")
   @ObfuscatedName("ag")
   EntityOps method8561(byte var1) {
      try {
         return new EntityOps();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "om.ag(" + ')');
      }
   }

   public int getId() {
      return this.field4920 * -381960671;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("as")
   public final boolean method8566(int var1, int var2) {
      try {
         if (null == this.field4918) {
            if (var2 != -656313384) {
               throw new IllegalStateException();
            } else if (null == this.field4937) {
               return true;
            } else if (10 != var1) {
               return true;
            } else {
               boolean var6 = true;

               for (int var4 = 0; var4 < this.field4937.length; var4++) {
                  if (var2 != -656313384) {
                     throw new IllegalStateException();
                  }

                  var6 &= classKD.field3552.method11944(this.field4918[var4], 0, (byte)-51);
               }

               return var6;
            }
         } else {
            for (int var3 = 0; var3 < this.field4918.length; var3++) {
               if (var2 != -656313384) {
                  throw new IllegalStateException();
               }

               if (var1 == this.field4937[var3]) {
                  if (var2 != -656313384) {
                     throw new IllegalStateException();
                  }

                  return classKD.field3552.method11944(this.field4937[var3], 0, (byte)-54);
               }
            }

            return true;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "om.as(" + ')');
      }
   }

   public int getSizeX() {
      return this.field4942 * -582306509;
   }

   @ObfuscatedSignature(descriptor = "(III)Ler;")
   @ObfuscatedName("al")
   final ModelData method8628(int var1, int var2, int var3) {
      synchronized (field4966) {
         int var8 = var2;
         int var7 = var1;
         ObjectComposition var6 = this;
         int var9 = -1973084331;

         ModelData var34;
         try {
            ModelData var10 = null;
            if (var6.field4937 == null) {
               if (10 != var7) {
                  return null;
               }

               if (var6.field4937 == null) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  return null;
               }

               boolean var11 = var6.field4953;
               if (var7 == 2) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  if (var8 > 3) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     boolean var10000;
                     if (!var11) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        var10000 = true;
                     } else {
                        var10000 = false;
                     }

                     var11 = var10000;
                  }
               }

               int var12 = var6.field4937.length;

               for (int var13 = 0; var13 < var12; var13++) {
                  int var14 = var6.field4918[var13];
                  int var15 = var14;
                  if (var11) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     var15 = -var14;
                  }

                  var10 = (ModelData)NodeCache.method6460(field4966, var15);
                  if (var10 == null) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     var10 = ModelData.method3986(classKD.field3552, var14, 0);
                     if (null == var10) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        return null;
                     }

                     if (var11) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        var10.method4023();
                     }

                     field4966.method6464(var10, var15);
                  }

                  if (var12 > 1) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     field4967[var13] = var10;
                  }
               }

               if (var12 > 1) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var10 = new ModelData(field4967, var12);
               }
            } else {
               int var24 = -1;

               for (int var26 = 0; var26 < var6.field4918.length; var26++) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  if (var6.field4918[var26] == var7) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     var24 = var26;
                     break;
                  }
               }

               if (-1 == var24) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  return null;
               }

               int var27 = var6.field4937[var24];
               int var29 = var27;
               boolean var10001;
               if (var8 > 3) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
               } else {
                  var10001 = false;
               }

               boolean var31 = var6.field4953 ^ var10001;
               if (var31) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var29 = var27 + 65536;
               }

               var10 = (ModelData)NodeCache.method6460(field4966, var29);
               if (var10 == null) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var10 = ModelData.method3986(classKD.field3552, var27, 0);
                  if (null == var10) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }

                  if (var31) {
                     var10.method4073();
                  }

                  field4966.method6466(var10, var29);
               }
            }

            boolean var25;
            label323: {
               if (-1739570015 * var6.field4950 == 128 && -1787252995 * var6.field4919 == 128) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  if (128 == 927725997 * var6.field4959) {
                     var25 = false;
                     break label323;
                  }

                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }
               }

               var25 = true;
            }

            boolean var28;
            label315: {
               if (0 == 916605819 * var6.field4955 && 0 == 2128147575 * var6.field4959) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  if (0 == var6.field4956 * -1155641687) {
                     var28 = false;
                     break label315;
                  }

                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }
               }

               var28 = true;
            }

            boolean var10003;
            label307: {
               var34 = new ModelData;
               if (0 == var8) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  if (!var25) {
                     if (var9 != -1973084331) {
                        throw new IllegalStateException();
                     }

                     if (!var28) {
                        if (var9 != -1973084331) {
                           throw new IllegalStateException();
                        }

                        var10003 = true;
                        break label307;
                     }
                  }
               }

               var10003 = false;
            }

            boolean var10004;
            if (null == var6.field4938) {
               if (var9 != -1973084331) {
                  throw new IllegalStateException();
               }

               var10004 = true;
            } else {
               var10004 = false;
            }

            boolean var10005;
            if (null == var6.field4941) {
               if (var9 != -1973084331) {
                  throw new IllegalStateException();
               }

               var10005 = true;
            } else {
               var10005 = false;
            }

            var34./* $VF: Unable to resugar constructor */<init>(var10, var10003, var10004, var10005, true);
            ModelData var30 = var34;
            if (var7 == 4) {
               if (var9 != -1973084331) {
                  throw new IllegalStateException();
               }

               if (var8 > 3) {
                  var30.method4027(256);
                  var30.method4045(45, 0, -45);
               }
            }

            var8 &= 3;
            if (1 == var8) {
               if (var9 != -1973084331) {
                  throw new IllegalStateException();
               }

               var30.method4021();
            } else if (2 == var8) {
               if (var9 != -1973084331) {
                  throw new IllegalStateException();
               }

               ModelData.method4078(var30);
            } else if (3 == var8) {
               if (var9 != -1973084331) {
                  throw new IllegalStateException();
               }

               Ignore.method10238(var30);
            }

            if (null != var6.field4938) {
               for (int var32 = 0; var32 < var6.field4938.length; var32++) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var30.method4036(var6.field4939[var32], var6.field4939[var32]);
               }
            }

            if (null != var6.field4940) {
               if (var9 != -1973084331) {
                  throw new IllegalStateException();
               }

               for (int var33 = 0; var33 < var6.field4940.length; var33++) {
                  if (var9 != -1973084331) {
                     throw new IllegalStateException();
                  }

                  var30.method4035(var6.field4941[var33], var6.field4940[var33]);
               }
            }

            if (var25) {
               if (var9 != -1973084331) {
                  throw new IllegalStateException();
               }

               classSG.method10466(var30, -1739570015 * var6.field4955, var6.field4947 * -1787252995, 927725997 * var6.field4955);
            }

            if (var28) {
               if (var9 != -1973084331) {
                  throw new IllegalStateException();
               }

               var30.method4066(916605819 * var6.field4957, 2128147575 * var6.field4919, var6.field4957 * -1155641687);
            }

            var34 = var30;
         } catch (RuntimeException var16) {
            throw classEG.method3899(var16, "om.al(" + ')');
         }

         return var34;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ay")
   public int method8589(int var1, int var2, int var3) {
      try {
         return classGQ.method5635(this.field4929, var1, var2, 1332934508);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "om.ay(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ar")
   public int method8616(int var1, int var2) {
      try {
         if (var1 != 1) {
            if (var2 >= -1327280404) {
               throw new IllegalStateException();
            }

            if (3 != var1) {
               return this.field4925 * -582306509;
            }
         }

         return 402876423 * this.field4942;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "om.ar(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II[[IIII)Lfx;")
   @ObfuscatedName("be")
   public final Model method8576(int var1, int var2, int[][] var3, int var4, int var5, int var6) {
      long var7;
      if (null == this.field4918) {
         var7 = (this.field4920 * -381960671 << 10) + var2;
      } else {
         var7 = (var1 << 3) + (this.field4954 * -381960671 << 10) + var2;
      }

      Model var9 = (Model)NodeCache.method6460(field4964, var7);
      if (null == var9) {
         ModelData var10 = method8627(this, var1, var2, -1973084331);
         if (null == var10) {
            return null;
         }

         var9 = var10.method4070(this.field4950 * 54395613 + 64, this.field4958 * -1693032223 + 768, -50, -10, -50);
         field4964.method6466(var9, var7);
      }

      if (this.field4958 * 1531627111 >= 0) {
         var9 = var9.method5258(var3, var4, var5, var6, true, 1531627111 * this.field4919);
      }

      return var9;
   }

   @ObfuscatedSignature(descriptor = "(Lom;III)I")
   @ObfuscatedName("sa")
   public static int method8590(ObjectComposition var0, int var1, int var2, int var3) {
      if (var0 == null) {
         return var0.method8589(var1, var1, var1);
      } else {
         try {
            return classGQ.method5635(var0.field4929, var1, var2, 1332934508);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "om.ay(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lom;I)Lom;")
   @ObfuscatedName("ou")
   public static ObjectComposition method8585(ObjectComposition var0, int var1) {
      if (var0 == null) {
         var0.method8586(var1);
      }

      try {
         int var2 = -1;
         if (1087117325 * var0.field4924 != -1) {
            var2 = classEM.method3946(var0.field4924 * 1087117325, -1344456616);
         } else if (-1 != -1047341117 * var0.field4960) {
            var2 = classLB.field4088[var0.field4960 * -1047341117];
         }

         if (var2 >= 0) {
            if (var1 == 1566578759) {
               throw new IllegalStateException();
            }

            if (var2 < var0.field4928.length - 1) {
               int var5 = var0.field4928[var2];
               return -1 != var5 ? classMU.method7771(var5, 2052883572) : null;
            }

            if (var1 == 1566578759) {
               throw new IllegalStateException();
            }
         }

         int var3 = var0.field4928[var0.field4928.length - 1];
         return -1 != var3 ? classMU.method7771(var3, 2052883572) : null;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "om.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lom;")
   @ObfuscatedName("aj")
   public final ObjectComposition method8586(int var1) {
      try {
         int var2 = -1;
         if (1087117325 * this.field4924 != -1) {
            var2 = classEM.method3946(this.field4960 * 1087117325, -1344456616);
         } else if (-1 != -1047341117 * this.field4960) {
            var2 = classLB.field4088[this.field4960 * -1047341117];
         }

         if (var2 >= 0) {
            if (var1 == 1566578759) {
               throw new IllegalStateException();
            }

            if (var2 < this.field4928.length - 1) {
               int var5 = this.field4928[var2];
               return -1 != var5 ? classMU.method7771(var5, 2052883572) : null;
            }

            if (var1 == 1566578759) {
               throw new IllegalStateException();
            }
         }

         int var3 = this.field4928[this.field4928.length - 1];
         return -1 != var3 ? classMU.method7771(var3, 2052883572) : null;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "om.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Ljava/lang/String;")
   @ObfuscatedName("ak")
   public String method8625(short var1) {
      try {
         return this.field4943;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "om.ak(" + ')');
      }
   }
}
