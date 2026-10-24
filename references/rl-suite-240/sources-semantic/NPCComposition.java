import java.awt.event.WindowEvent;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pl")
public class NPCComposition extends classVJ implements net.runelite.api.NPCComposition, rl9 {
   @ObfuscatedName("do")
   final int field5322;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ae")
   public static IndexDataBase field5292;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ab")
   public static IndexDataBase field5288;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field5338 = 12;
   @ObfuscatedName("dt")
   final short[] field5314;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field5339 = 34;
   @ObfuscatedName("dv")
   final int field5321;
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   static final int field5341 = 79;
   @ToRemove(unused = "true")
   @ObfuscatedName("bj")
   static final int field5340 = 102;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("co")
   static NodeCache field5335 = new NodeCache(50);
   @ToRemove(unused = "true")
   @ObfuscatedName("cd")
   static final int field5344 = 251;
   @ObfuscatedName("df")
   final int field5295;
   @ObfuscatedName("eo")
   public final boolean field5332;
   @ObfuscatedName("ck")
   public final int field5300;
   @ObfuscatedName("ca")
   String field5291;
   @ObfuscatedName("cc")
   public final int field5282;
   @ObfuscatedName("cf")
   final int[] field5290;
   @ObfuscatedName("dy")
   public final int field5301;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("cy")
   static NodeCache field5334 = new NodeCache(64);
   @ObfuscatedName("cz")
   public final int field5289;
   @ObfuscatedName("cg")
   public final int field5305;
   @ObfuscatedName("cw")
   public final int field5283;
   @ObfuscatedName("cj")
   public final int field5306;
   @ObfuscatedName("ci")
   public final int field5284;
   @ObfuscatedName("cx")
   public final int field5307;
   @ObfuscatedName("ce")
   public final int field5279;
   @ObfuscatedName("cm")
   public final int field5308;
   @ObfuscatedName("dc")
   public final int field5299;
   @ObfuscatedName("dd")
   public final int field5309;
   @ToRemove(unused = "true")
   @ObfuscatedName("bq")
   static final int field5342 = 118;
   @ObfuscatedName("cn")
   final int[] field5302;
   @ObfuscatedName("dh")
   public final boolean field5317;
   @ObfuscatedName("di")
   public final int field5310;
   @ObfuscatedName("du")
   public final boolean field5311;
   @ObfuscatedName("en")
   final short[] field5312;
   @ObfuscatedName("dr")
   public final short[] field5313;
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   static final int field5336 = 77;
   @ObfuscatedName("db")
   public final short[] field5315;
   @ObfuscatedSignature(descriptor = "Lpo;")
   @ObfuscatedName("dw")
   final EntityOps field5316;
   @ObfuscatedName("ch")
   public final int field5304;
   @ObfuscatedName("ex")
   final int field5330;
   @ObfuscatedName("dz")
   final int field5319;
   @ObfuscatedName("dn")
   final int field5320;
   @ObfuscatedName("de")
   public final int field5318;
   @ObfuscatedName("dm")
   public final int field5280;
   @ObfuscatedName("dl")
   public final int[] field5323;
   @ObfuscatedName("dg")
   public final int field5297;
   @ObfuscatedName("dq")
   final int field5294;
   @ObfuscatedName("eh")
   final int[] field5327;
   @ObfuscatedName("ds")
   public final boolean field5324;
   @ObfuscatedName("dj")
   public final boolean field5325;
   @ObfuscatedName("dk")
   final short[] field5329;
   @ObfuscatedName("dp")
   public final boolean field5293;
   @ObfuscatedName("ey")
   public final int field5286;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field5287 = 8;
   @ObfuscatedName("da")
   public final boolean field5326;
   @ObfuscatedName("eg")
   final int[] field5328;
   @ObfuscatedName("dx")
   public final int field5296;
   @rl8(method10167 = 16)
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("ea")
   IterableHashTable field5298;
   @ObfuscatedName("eq")
   final boolean field5331;
   @ObfuscatedName("er")
   final int field5285;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field5337 = 33;
   @ObfuscatedSignature(descriptor = "Lwj;")
   @ObfuscatedName("et")
   public final classWJ field5281;
   @ObfuscatedSignature(descriptor = "Lww;")
   @ObfuscatedName("ew")
   public final classWW field5333;
   @ObfuscatedSignature(descriptor = "Loq;")
   @ObfuscatedName("eu")
   public final classOQ field5303;
   @ToRemove(unused = "true")
   @ObfuscatedName("bu")
   static final int field5343 = 57;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dc")
   public boolean method9041() {
      if (this.field5323 == null) {
         return -1 != this.field5281.field6687 * -1727985133 || this.field5333.field6783 != null;
      } else {
         int[] var1 = this.field5323;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            if (-1 != var3) {
               NPCComposition var4 = classOH.method8480(var3, -1200041233);
               if (-1 != -1727985133 * var4.field5281.field6687 || null != var4.field5333.field6783) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean isFollower() {
      return this.field5326;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("sm")
   public boolean method9047() {
      return this.method9002((byte)-97);
   }

   NPCComposition(classXY var1, int var2) {
      this.field5300 = var2 * 827844159;
      String var3 = classKK.field3613;
      int var4 = 1;
      int[] var5 = null;
      int[] var6 = null;
      int var7 = -1;
      int var8 = -1;
      int var9 = -1;
      int var10 = -1;
      int var11 = -1;
      int var12 = -1;
      int var13 = -1;
      int var14 = -1;
      int var15 = -1;
      int var16 = -1;
      int var17 = -1;
      int var18 = -1;
      int var19 = -1;
      int var20 = -1;
      int var21 = -1;
      boolean var22 = false;
      short[] var23 = null;
      short[] var24 = null;
      short[] var25 = null;
      short[] var26 = null;
      EntityOps var27 = method8974(this, -835605451);
      boolean var28 = true;
      int var29 = -1;
      int var30 = 128;
      int var31 = 128;
      byte var32 = 0;
      int var33 = 0;
      int var34 = 32;
      int[] var35 = null;
      int var36 = -1;
      int var37 = -1;
      boolean var38 = true;
      boolean var39 = true;
      boolean var40 = false;
      boolean var41 = false;
      int var42 = -1;
      IterableHashTable var43 = null;
      int[] var44 = new int[]{1, 1, 1, 1, 1, 1};
      int[] var45 = null;
      short[] var46 = null;
      int var47 = -1;
      boolean var48 = false;
      int var49 = 39188;
      boolean var50 = true;
      classWJ var51 = new classWJ();
      classWW var52 = new classWW();
      classOQ var53 = classOQ.field5079;
      if (var1 != null && null != var1.field6952) {
         label299:
         while (true) {
            int var54 = var1.method13095(-346779531);
            switch (var54) {
               case 0:
                  break label299;
               case 1:
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
               case 19:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               case 27:
               case 28:
               case 29:
               case 35:
               case 36:
               case 37:
               case 38:
               case 39:
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
               case 64:
               case 65:
               case 66:
               case 67:
               case 68:
               case 69:
               case 70:
               case 71:
               case 72:
               case 73:
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
               case 94:
               case 96:
               case 104:
               case 105:
               case 108:
               case 110:
               case 112:
               case 113:
               case 119:
               case 120:
               case 121:
               case 125:
               case 127:
               case 128:
               case 129:
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
               case 250:
               default:
                  break;
               case 2:
                  var3 = var1.method13127(1381224448);
                  break;
               case 12:
                  var4 = var1.method13095(-346779531);
                  break;
               case 13:
                  var7 = var1.method13099(-11551235);
                  break;
               case 14:
                  var10 = var1.method13099(674915822);
                  break;
               case 15:
                  var8 = var1.method13099(975161411);
                  break;
               case 16:
                  var9 = var1.method13099(-1694115320);
                  break;
               case 17:
                  var10 = var1.method13099(-525059563);
                  var11 = var1.method13099(1819068317);
                  var12 = var1.method13099(-106680584);
                  var13 = var1.method13099(1105178497);
                  break;
               case 18:
                  var1.method13099(-1614430796);
                  break;
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 251:
               case 252:
               case 253:
                  var27.method9088(var1, var54, 30, 34, 251, 252, 253, 1161561653);
                  break;
               case 40:
                  int var70 = var1.method13095(-346779531);
                  var23 = new short[var70];
                  var24 = new short[var70];

                  for (int var78 = 0; var78 < var70; var78++) {
                     var23[var78] = (short)var1.method13099(-2087008104);
                     var24[var78] = (short)var1.method13099(1175337329);
                  }
                  break;
               case 41:
                  int var69 = var1.method13095(-346779531);
                  var25 = new short[var69];
                  var26 = new short[var69];

                  for (int var77 = 0; var77 < var69; var77++) {
                     var25[var77] = (short)var1.method13099(1006054699);
                     var26[var77] = (short)var1.method13099(-2139171520);
                  }
                  break;
               case 61:
                  int var68 = var1.method13095(-346779531);
                  var5 = new int[var68];

                  for (int var76 = 0; var76 < var68; var76++) {
                     var5[var76] = var1.method13173(classXM.field6886.field6888, 1716326015);
                  }
                  break;
               case 62:
                  int var67 = var1.method13095(-346779531);
                  var6 = new int[var67];

                  for (int var75 = 0; var75 < var67; var75++) {
                     var6[var75] = var1.method13173(classXM.field6886.field6888, 1518099007);
                  }
                  break;
               case 74:
                  var44[0] = var1.method13099(-1821699879);
                  break;
               case 75:
                  var44[1] = var1.method13099(-2120146721);
                  break;
               case 76:
                  var44[2] = var1.method13099(-1124357946);
                  break;
               case 77:
                  var44[3] = var1.method13099(-1693678342);
                  break;
               case 78:
                  var44[4] = var1.method13099(336447020);
                  break;
               case 79:
                  var44[5] = var1.method13099(-1926068077);
                  break;
               case 93:
                  var28 = false;
                  break;
               case 95:
                  var29 = var1.method13099(24265317);
                  break;
               case 97:
                  var30 = var1.method13099(188656600);
                  break;
               case 98:
                  var31 = var1.method13099(237873694);
                  break;
               case 99:
                  var53 = classOQ.field5077;
                  break;
               case 100:
                  var32 = var1.method13097((byte)17);
                  break;
               case 101:
                  var33 = var1.method13097((byte)17) * 5;
                  break;
               case 102:
                  int var66 = var1.method13095(-346779531);
                  int var74 = 0;

                  for (int var80 = var66; 0 != var80; var80 >>= 1) {
                     var74++;
                  }

                  var45 = new int[var74];
                  var46 = new short[var74];

                  for (int var81 = 0; var81 < var74; var81++) {
                     if ((var66 & 1 << var81) == 0) {
                        var45[var81] = -1;
                        var46[var81] = -1;
                     } else {
                        var45[var81] = classXY.method13169(var1, -324749371);
                        var46[var81] = (short)var1.method13152(-730728427);
                     }
                  }
                  break;
               case 103:
                  var34 = var1.method13099(134377154);
                  break;
               case 106:
               case 118:
                  var36 = var1.method13099(-360424813);
                  if (var36 == 65535) {
                     var36 = -1;
                  }

                  var37 = var1.method13099(-635983963);
                  if (65535 == var37) {
                     var37 = -1;
                  }

                  int var65 = -1;
                  if (118 == var54) {
                     var65 = var1.method13099(543231650);
                     if (var65 == 65535) {
                        var65 = -1;
                     }
                  }

                  int var73 = var1.method13095(-346779531);
                  var35 = new int[2 + var73];

                  for (int var79 = 0; var79 <= var73; var79++) {
                     var35[var79] = var1.method13099(531671492);
                     if (var35[var79] == 65535) {
                        var35[var79] = -1;
                     }
                  }

                  var35[1 + var73] = var65;
                  break;
               case 107:
                  var38 = false;
                  break;
               case 109:
                  var39 = false;
                  break;
               case 111:
                  var53 = classOQ.field5078;
                  break;
               case 114:
                  var14 = var1.method13099(-171247890);
                  break;
               case 115:
                  var14 = var1.method13099(576052994);
                  var15 = var1.method13099(-1631180024);
                  var16 = var1.method13099(-1498861295);
                  var17 = var1.method13099(-1932561699);
                  break;
               case 116:
                  var18 = var1.method13099(388202665);
                  break;
               case 117:
                  var18 = var1.method13099(344265763);
                  var19 = var1.method13099(-792186032);
                  var20 = var1.method13099(624450092);
                  var21 = var1.method13099(-684860613);
                  break;
               case 122:
                  var40 = true;
                  break;
               case 123:
                  var41 = true;
                  break;
               case 124:
                  var42 = var1.method13099(-242714651);
                  break;
               case 126:
                  var47 = var1.method13099(1481172224);
                  break;
               case 130:
                  var22 = true;
                  break;
               case 145:
                  var48 = true;
                  break;
               case 146:
                  var49 = var1.method13099(-1210526976);
                  break;
               case 147:
                  var50 = false;
                  break;
               case 148:
                  var51.field6687 = var1.method13099(1934168574) * -418754533;
                  var51.field6684 = var1.method13095(-346779531) * 39261697;
                  var51.field6686 = var1.method13095(-346779531) * -286691225;
                  break;
               case 149:
                  classWD var64 = var51.field6685;
                  int var57 = var1.method13095(-346779531);
                  classZL var72 = classZL.field7199[var57];
                  var64.method12391(var72, -179178084);
                  break;
               case 150:
                  int var71 = var1.method13095(-346779531);
                  classZL var63 = classZL.field7199[var71];
                  int var58 = var1.method13099(-466516573) * 20;
                  classWD.method12386(var51.field6685, var63, var58, (byte)111);
                  int var60 = var1.method13095(-346779531);
                  classZL var59 = classZL.field7199[var60];
                  int var62 = var1.method13099(776673976) * 20;
                  var51.field6685.method12389(var59, var62, -388881497);
                  break;
               case 151:
                  var51.field6688 = (classKC)classOF.method8436(classHF.method6037(-966579297), var1.method13095(-346779531), -2046684356);
                  break;
               case 152:
                  var52.field6782 = var1.method13099(-1356571755) * -1412665981;
                  var52.field6781 = var1.method13099(-2076965834) * -736985931;
                  var51.field6684 = var1.method13095(-346779531) * 39261697;
                  var51.field6686 = var1.method13095(-346779531) * -286691225;
                  int var55 = var1.method13095(-346779531);
                  var52.field6783 = new int[var55];

                  for (int var56 = 0; var56 < var55; var56++) {
                     var52.field6783[var56] = var1.method13099(1445422137);
                  }
                  break;
               case 249:
                  var43 = classQH.method9572(var1, var43, 1978086108);
            }
         }
      }

      if (-1 == var47) {
         var47 = (int)(0.4F * (var4 * 128));
      }

      this.field5291 = var3;
      this.field5282 = var4 * -949031185;
      this.field5290 = var5;
      this.field5302 = var6;
      this.field5304 = 1220125627 * var7;
      this.field5289 = 674030075 * var8;
      this.field5305 = 1213713169 * var9;
      this.field5283 = var10 * -804691593;
      this.field5306 = var11 * -2105781777;
      this.field5284 = -1432063099 * var12;
      this.field5307 = 852782415 * var13;
      this.field5279 = -1755050233 * var14;
      this.field5308 = -67317255 * var15;
      this.field5299 = var16 * 1677703629;
      this.field5309 = -1133315765 * var17;
      this.field5280 = var18 * 698705141;
      this.field5296 = 684682811 * var19;
      this.field5301 = var20 * -2023412849;
      this.field5310 = -991336805 * var21;
      this.field5311 = var22;
      this.field5312 = var23;
      this.field5313 = var24;
      this.field5314 = var25;
      this.field5315 = var26;
      this.field5316 = var27;
      this.field5317 = var28;
      this.field5318 = var29 * 2135414653;
      this.field5319 = var30 * -1731973987;
      this.field5320 = var31 * 1397641381;
      this.field5321 = var32 * -852812645;
      this.field5322 = var33 * -757704127;
      this.field5297 = var34 * -1360446703;
      this.field5323 = var35;
      this.field5294 = -373079405 * var36;
      this.field5295 = var37 * 718644969;
      this.field5324 = var38;
      this.field5325 = var39;
      this.field5326 = var40;
      this.field5293 = var41;
      this.field5286 = var42 * -1483773759;
      if (var43 == null) {
         this.field5298 = new IterableHashTable(1);
      } else {
         this.field5298 = var43;
      }

      this.field5327 = var44;
      this.field5328 = var45;
      this.field5329 = var46;
      this.field5330 = var47 * -390173693;
      this.field5331 = var48;
      this.field5285 = var49 * 928365823;
      this.field5332 = var50;
      this.field5281 = var51;
      this.field5333 = var52;
      this.field5303 = var53;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("aj")
   public String method9008(int var1, String var2, int var3) {
      try {
         return classFS.method4998(this.field5298, var1, var2, (byte)27);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pl.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([ILpp;I)Ler;")
   @ObfuscatedName("ax")
   ModelData method8992(int[] var1, NpcOverrides var2, int var3) {
      try {
         int[] var4 = var1;
         if (null != var2) {
            if (var3 != 795096365) {
               throw new IllegalStateException();
            }

            if (null != var2.field5372) {
               if (var3 != 795096365) {
                  throw new IllegalStateException();
               }

               var4 = var2.field5372;
            }
         }

         if (null == var4) {
            if (var3 != 795096365) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            boolean var5 = false;

            for (int var6 = 0; var6 < var4.length; var6++) {
               if (var3 != 795096365) {
                  throw new IllegalStateException();
               }

               if (-1 != var4[var6]) {
                  if (var3 != 795096365) {
                     throw new IllegalStateException();
                  }

                  if (!field5288.method11944(var4[var6], 0, (byte)12)) {
                     if (var3 != 795096365) {
                        throw new IllegalStateException();
                     }

                     var5 = true;
                  }
               }
            }

            if (var5) {
               return null;
            } else {
               ModelData[] var11 = new ModelData[var4.length];

               for (int var7 = 0; var7 < var4.length; var7++) {
                  if (var3 != 795096365) {
                     throw new IllegalStateException();
                  }

                  var11[var7] = ModelData.method4067(field5288, var4[var7], 0);
               }

               ModelData var12;
               if (var11.length == 1) {
                  if (var3 != 795096365) {
                     throw new IllegalStateException();
                  }

                  var12 = var11[0];
                  if (var12 == null) {
                     if (var3 != 795096365) {
                        throw new IllegalStateException();
                     }

                     var12 = new ModelData(var11, var11.length);
                  }
               } else {
                  var12 = new ModelData(var11, var11.length);
               }

               if (this.field5312 != null) {
                  if (var3 != 795096365) {
                     throw new IllegalStateException();
                  }

                  short[] var8 = this.field5313;
                  if (null != var2) {
                     if (var3 != 795096365) {
                        throw new IllegalStateException();
                     }

                     if (var2.field5373 != null) {
                        if (var3 != 795096365) {
                           throw new IllegalStateException();
                        }

                        var8 = var2.field5373;
                     }
                  }

                  for (int var9 = 0; var9 < this.field5312.length; var9++) {
                     if (var3 != 795096365) {
                        throw new IllegalStateException();
                     }

                     var12.method4031(this.field5312[var9], var8[var9]);
                  }
               }

               if (this.field5314 != null) {
                  if (var3 != 795096365) {
                     throw new IllegalStateException();
                  }

                  short[] var13 = this.field5315;
                  if (var2 != null && null != var2.field5374) {
                     if (var3 != 795096365) {
                        throw new IllegalStateException();
                     }

                     var13 = var2.field5374;
                  }

                  for (int var14 = 0; var14 < this.field5314.length; var14++) {
                     if (var3 != 795096365) {
                        throw new IllegalStateException();
                     }

                     var12.method4035(this.field5314[var14], var13[var14]);
                  }
               }

               return var12;
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "pl.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/String;")
   @ObfuscatedName("ak")
   public String method9038(byte var1) {
      try {
         return this.field5291;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)S")
   @ObfuscatedName("ca")
   public short method9026(int var1) {
      return this.field5329 != null && var1 < this.field5329.length ? this.field5329[var1] : -1;
   }

   @ObfuscatedSignature(descriptor = "(I)Lpl;")
   @ObfuscatedName("ac")
   public final NPCComposition method8998(int var1) {
      try {
         int var2 = -1;
         if (-1 != -771216485 * this.field5294) {
            if (var1 != 652890385) {
               throw new IllegalStateException();
            }

            var2 = classEM.method3946(this.field5294 * -771216485, -752703544);
         } else if (123179353 * this.field5295 != -1) {
            if (var1 != 652890385) {
               throw new IllegalStateException();
            }

            var2 = classLB.field4088[123179353 * this.field5295];
         }

         int var3;
         label42: {
            if (var2 >= 0) {
               if (var1 != 652890385) {
                  throw new IllegalStateException();
               }

               if (var2 < this.field5323.length - 1) {
                  var3 = this.field5323[var2];
                  break label42;
               }
            }

            var3 = this.field5323[this.field5323.length - 1];
         }

         if (var3 != -1) {
            if (var1 != 652890385) {
               throw new IllegalStateException();
            } else {
               return classOH.method8480(var3, 990349769);
            }
         } else {
            return null;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pl.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("al")
   public long method9005(int var1, long var2) {
      try {
         return classBW.method1275(this.field5298, var1, var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pl.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpp;B)Ler;")
   @ObfuscatedName("as")
   public final ModelData method8987(NpcOverrides var1, byte var2) {
      try {
         if (null != this.field5323) {
            NPCComposition var3 = this.method8998(652890385);
            if (var3 == null) {
               if (var2 <= 3) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               return var3.method8987(var1, (byte)8);
            }
         } else {
            return this.method8992(this.field5302, var1, 795096365);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pl.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[I")
   @ObfuscatedName("aq")
   public int[] method9015(byte var1) {
      try {
         return this.field5328;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.aq(" + ')');
      }
   }

   public int getId() {
      return this.field5300 * 283969983;
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("ad")
   public int method9017(int var1, byte var2) {
      try {
         if (null != this.field5328) {
            if (var1 < this.field5328.length) {
               return this.field5328[var1];
            }

            if (var2 >= 1) {
               throw new IllegalStateException();
            }
         }

         return -1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pl.ad(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[S")
   @ObfuscatedName("ap")
   public short[] method9021(int var1) {
      try {
         return this.field5329;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.ap(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;[ILpp;)Ler;")
   @ObfuscatedName("hf")
   public static ModelData method8993(NPCComposition var0, int[] var1, NpcOverrides var2) {
      if (var0 == null) {
         var0.getSize();
      }

      int[] var3 = var1;
      if (null != var2 && null != var2.field5372) {
         var3 = var2.field5372;
      }

      if (null == var3) {
         return null;
      } else {
         boolean var4 = false;

         for (int var5 = 0; var5 < var3.length; var5++) {
            if (-1 != var3[var5] && !field5288.method11944(var3[var5], 0, (byte)23)) {
               var4 = true;
            }
         }

         if (var4) {
            return null;
         } else {
            ModelData[] var9 = new ModelData[var3.length];

            for (int var6 = 0; var6 < var3.length; var6++) {
               var9[var6] = ModelData.method4067(field5288, var3[var6], 0);
            }

            ModelData var10;
            if (var9.length == 1) {
               var10 = var9[0];
               if (var10 == null) {
                  var10 = new ModelData(var9, var9.length);
               }
            } else {
               var10 = new ModelData(var9, var9.length);
            }

            if (var0.field5312 != null) {
               short[] var7 = var0.field5313;
               if (null != var2 && var2.field5373 != null) {
                  var7 = var2.field5373;
               }

               for (int var8 = 0; var8 < var0.field5312.length; var8++) {
                  var10.method4031(var0.field5312[var8], var7[var8]);
               }
            }

            if (var0.field5314 != null) {
               short[] var11 = var0.field5315;
               if (var2 != null && null != var2.field5374) {
                  var11 = var2.field5374;
               }

               for (int var12 = 0; var12 < var0.field5314.length; var12++) {
                  var10.method4035(var0.field5314[var12], var11[var12]);
               }
            }

            return var10;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("cy")
   public short[] method9022() {
      return this.field5329;
   }

   @ObfuscatedSignature(descriptor = "()Loz;")
   @ObfuscatedName("ah")
   public classOZ method8969() {
      return this.field5316;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ar")
   public boolean method9031(int var1) {
      try {
         return this.field5331;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aw")
   public int method9034(int var1) {
      try {
         return -1776205057 * this.field5285;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpl;I)Lpo;")
   @ObfuscatedName("rm")
   public static EntityOps method8974(NPCComposition var0, int var1) {
      if (var0 == null) {
         var0.method8976(var1);
      }

      try {
         return new EntityOps();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cz")
   public int method9035() {
      return -1776205057 * this.field5285;
   }

   @ObfuscatedSignature(descriptor = "()Loz;")
   @ObfuscatedName("at")
   public classOZ method8970() {
      return this.field5316;
   }

   @ObfuscatedSignature(descriptor = "()Loz;")
   @ObfuscatedName("an")
   public classOZ method8971() {
      return this.field5316;
   }

   @ObfuscatedSignature(descriptor = "([ILpp;)Ler;")
   @ObfuscatedName("be")
   ModelData method8994(int[] var1, NpcOverrides var2) {
      int[] var3 = var1;
      if (null != var2 && null != var2.field5372) {
         var3 = var2.field5372;
      }

      if (null == var3) {
         return null;
      } else {
         boolean var4 = false;

         for (int var5 = 0; var5 < var3.length; var5++) {
            if (-1 != var3[var5] && !field5288.method11944(var3[var5], 0, (byte)84)) {
               var4 = true;
            }
         }

         if (var4) {
            return null;
         } else {
            ModelData[] var9 = new ModelData[var3.length];

            for (int var6 = 0; var6 < var3.length; var6++) {
               var9[var6] = ModelData.method4067(field5288, var3[var6], 0);
            }

            ModelData var10;
            if (var9.length == 1) {
               var10 = var9[0];
               if (var10 == null) {
                  var10 = new ModelData(var9, var9.length);
               }
            } else {
               var10 = new ModelData(var9, var9.length);
            }

            if (this.field5312 != null) {
               short[] var7 = this.field5313;
               if (null != var2 && var2.field5373 != null) {
                  var7 = var2.field5373;
               }

               for (int var8 = 0; var8 < this.field5312.length; var8++) {
                  var10.method4031(this.field5312[var8], var7[var8]);
               }
            }

            if (this.field5314 != null) {
               short[] var11 = this.field5315;
               if (var2 != null && null != var2.field5374) {
                  var11 = var2.field5374;
               }

               for (int var12 = 0; var12 < this.field5314.length; var12++) {
                  var10.method4035(this.field5314[var12], var11[var12]);
               }
            }

            return var10;
         }
      }
   }

   public boolean isInteractible() {
      return this.field5324;
   }

   @ObfuscatedSignature(descriptor = "()Loz;")
   @ObfuscatedName("am")
   public classOZ method8972() {
      return this.field5316;
   }

   @ObfuscatedSignature(descriptor = "()Lpo;")
   @ObfuscatedName("bn")
   EntityOps method8975() {
      return new EntityOps();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;I)I")
   @ObfuscatedName("as")
   public static int method9018(NPCComposition var0, int var1) {
      return null != var0.field5328 && var1 < var0.field5328.length ? var0.field5328[var1] : -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpl;")
   @ObfuscatedName("bx")
   public static NPCComposition method8977(int var0) {
      NPCComposition var1 = (NPCComposition)NodeCache.method6460(field5334, var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = field5292.method11942(9, var0, -1420278537);
         var1 = new NPCComposition(new classXY(var2), var0);
         field5334.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpo;")
   @ObfuscatedName("qi")
   public EntityOps method9048() {
      return this.field5316;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpl;")
   @ObfuscatedName("bc")
   public static NPCComposition method8978(int var0) {
      NPCComposition var1 = (NPCComposition)NodeCache.method6460(field5334, var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = field5292.method11942(9, var0, -1740563463);
         var1 = new NPCComposition(new classXY(var2), var0);
         field5334.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bp")
   public static void method8979() {
      field5334.method6468();
      field5335.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("br")
   public static void method8980() {
      field5334.method6468();
      field5335.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;Lpp;)Ler;")
   @ObfuscatedName("ly")
   public static ModelData method8988(NPCComposition var0, NpcOverrides var1) {
      if (null != var0.field5323) {
         NPCComposition var2 = var0.method8998(652890385);
         return var2 == null ? null : var2.method8987(var1, (byte)84);
      } else {
         return var0.method8992(var0.field5302, var1, 795096365);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lou;ILou;ILpp;)Lfx;")
   @ObfuscatedName("bm")
   public final Model method8982(Animation var1, int var2, Animation var3, int var4, NpcOverrides var5) {
      if (null != this.field5323) {
         NPCComposition var10 = this.method8998(652890385);
         return var10 == null ? null : method8985(var10, var1, var2, var3, var4, var5, -1192552610);
      } else {
         long var6 = this.field5300 * 283969983;
         if (var5 != null) {
            var6 |= -4980301671096129853L * var5.field5376 << 16;
         }

         Model var8 = (Model)NodeCache.method6460(field5335, var6);
         if (var8 == null) {
            ModelData var9 = this.method8992(this.field5290, var5, 795096365);
            if (null == var9) {
               return null;
            }

            var8 = var9.method4070(423192979 * this.field5321 + 64, 850 + 2098640321 * this.field5322, -30, -50, -30);
            field5335.method6464(var8, var6);
         }

         Model var11;
         if (null != var1 && null != var3) {
            var11 = var1.method8830(var8, var2, var3, var4, -1195884941);
         } else if (var1 != null) {
            var11 = var1.method8829(var8, var2, (byte)0);
         } else if (var3 != null) {
            var11 = var3.method8829(var8, var4, (byte)0);
         } else {
            var11 = var8.method5106(true);
         }

         if (128 != this.field5319 * 781957557 || 611915565 * this.field5320 != 128) {
            var11.method5251(781957557 * this.field5319, this.field5320 * 611915565, 781957557 * this.field5319);
         }

         return var11;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;)Z")
   @ObfuscatedName("fc")
   public static boolean method9000(NPCComposition var0) {
      if (var0.field5323 == null) {
         return true;
      } else {
         int var1 = -1;
         if (var0.field5294 * -771216485 != -1) {
            var1 = classEM.method3946(-771216485 * var0.field5294, -1473414039);
         } else if (-1 != 123179353 * var0.field5295) {
            var1 = classLB.field4088[123179353 * var0.field5295];
         }

         return var1 >= 0 && var1 < var0.field5323.length ? var0.field5323[var1] != -1 : -1 != var0.field5323[var0.field5323.length - 1];
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpl;")
   @ObfuscatedName("gc")
   public NPCComposition method9049() {
      return this.method8998(652890385);
   }

   @ObfuscatedSignature(descriptor = "(Lpl;IB)S")
   @ObfuscatedName("kw")
   public static short method9027(NPCComposition var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method9029(var1, var2);
      }

      try {
         if (var0.field5329 != null) {
            if (var1 < var0.field5329.length) {
               return var0.field5329[var1];
            }

            if (var2 >= 0) {
               throw new IllegalStateException();
            }
         }

         return -1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pl.au(" + ')');
      }
   }

   public short[] getColorToReplace() {
      return this.field5312;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;Lpp;)Ler;")
   @ObfuscatedName("vx")
   public static ModelData method8989(NPCComposition var0, NpcOverrides var1) {
      if (null != var0.field5323) {
         NPCComposition var2 = var0.method8998(652890385);
         return var2 == null ? null : var2.method8987(var1, (byte)123);
      } else {
         return var0.method8992(var0.field5302, var1, 795096365);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lou;ILou;ILpp;)Lfx;")
   @ObfuscatedName("bt")
   public final Model method8983(Animation var1, int var2, Animation var3, int var4, NpcOverrides var5) {
      if (null != this.field5323) {
         NPCComposition var10 = this.method8998(652890385);
         return var10 == null ? null : method8985(var10, var1, var2, var3, var4, var5, -828731087);
      } else {
         long var6 = this.field5300 * 1959413853;
         if (var5 != null) {
            var6 |= -4980301671096129853L * var5.field5376 << 16;
         }

         Model var8 = (Model)NodeCache.method6460(field5335, var6);
         if (var8 == null) {
            ModelData var9 = this.method8992(this.field5290, var5, 795096365);
            if (null == var9) {
               return null;
            }

            var8 = var9.method4070(423192979 * this.field5321 + 844156957, 850 + -1738706466 * this.field5322, -30, 1808646333, -1774326506);
            field5335.method6464(var8, var6);
         }

         Model var11;
         if (null != var1 && null != var3) {
            var11 = var1.method8830(var8, var2, var3, var4, -1737939264);
         } else if (var1 != null) {
            var11 = var1.method8829(var8, var2, (byte)0);
         } else if (var3 != null) {
            var11 = var3.method8829(var8, var4, (byte)0);
         } else {
            var11 = var8.method5106(true);
         }

         if (128 != this.field5319 * 1835812871 || 1270703591 * this.field5320 != 128) {
            var11.method5251(781957557 * this.field5319, this.field5320 * -440064221, 781957557 * this.field5319);
         }

         return var11;
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("bv")
   public long method9006(int var1, long var2) {
      return classBW.method1275(this.field5298, var1, var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("gg")
   public static void method8981(GameEngine var0, WindowEvent var1) {
      if (var0 == null) {
         var0.method11112(var1);
      } else {
         var0.destroy();
      }
   }

   @ObfuscatedSignature(descriptor = "([ILpp;)Ler;")
   @ObfuscatedName("ba")
   ModelData method8995(int[] var1, NpcOverrides var2) {
      int[] var3 = var1;
      if (null != var2 && null != var2.field5372) {
         var3 = var2.field5372;
      }

      if (null == var3) {
         return null;
      } else {
         boolean var4 = false;

         for (int var5 = 0; var5 < var3.length; var5++) {
            if (-1 != var3[var5] && !field5288.method11944(var3[var5], 0, (byte)-21)) {
               var4 = true;
            }
         }

         if (var4) {
            return null;
         } else {
            ModelData[] var9 = new ModelData[var3.length];

            for (int var6 = 0; var6 < var3.length; var6++) {
               var9[var6] = ModelData.method4067(field5288, var3[var6], 0);
            }

            ModelData var10;
            if (var9.length == 1) {
               var10 = var9[0];
               if (var10 == null) {
                  var10 = new ModelData(var9, var9.length);
               }
            } else {
               var10 = new ModelData(var9, var9.length);
            }

            if (this.field5312 != null) {
               short[] var7 = this.field5313;
               if (null != var2 && var2.field5373 != null) {
                  var7 = var2.field5373;
               }

               for (int var8 = 0; var8 < this.field5312.length; var8++) {
                  var10.method4031(this.field5312[var8], var7[var8]);
               }
            }

            if (this.field5314 != null) {
               short[] var11 = this.field5315;
               if (var2 != null && null != var2.field5374) {
                  var11 = var2.field5374;
               }

               for (int var12 = 0; var12 < this.field5314.length; var12++) {
                  var10.method4035(this.field5314[var12], var11[var12]);
               }
            }

            return var10;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([ILpp;)Ler;")
   @ObfuscatedName("bw")
   ModelData method8996(int[] var1, NpcOverrides var2) {
      int[] var3 = var1;
      if (null != var2 && null != var2.field5372) {
         var3 = var2.field5372;
      }

      if (null == var3) {
         return null;
      } else {
         boolean var4 = false;

         for (int var5 = 0; var5 < var3.length; var5++) {
            if (-1 != var3[var5] && !field5288.method11944(var3[var5], 0, (byte)60)) {
               var4 = true;
            }
         }

         if (var4) {
            return null;
         } else {
            ModelData[] var9 = new ModelData[var3.length];

            for (int var6 = 0; var6 < var3.length; var6++) {
               var9[var6] = ModelData.method4067(field5288, var3[var6], 0);
            }

            ModelData var10;
            if (var9.length == 1) {
               var10 = var9[0];
               if (var10 == null) {
                  var10 = new ModelData(var9, var9.length);
               }
            } else {
               var10 = new ModelData(var9, var9.length);
            }

            if (this.field5312 != null) {
               short[] var7 = this.field5313;
               if (null != var2 && var2.field5373 != null) {
                  var7 = var2.field5373;
               }

               for (int var8 = 0; var8 < this.field5312.length; var8++) {
                  var10.method4031(this.field5312[var8], var7[var8]);
               }
            }

            if (this.field5314 != null) {
               short[] var11 = this.field5315;
               if (var2 != null && null != var2.field5374) {
                  var11 = var2.field5374;
               }

               for (int var12 = 0; var12 < this.field5314.length; var12++) {
                  var10.method4035(this.field5314[var12], var11[var12]);
               }
            }

            return var10;
         }
      }
   }

   public String getName() {
      return this.field5291;
   }

   @ObfuscatedSignature(descriptor = "([ILpp;)Ler;")
   @ObfuscatedName("bb")
   ModelData method8997(int[] var1, NpcOverrides var2) {
      int[] var3 = var1;
      if (null != var2 && null != var2.field5372) {
         var3 = var2.field5372;
      }

      if (null == var3) {
         return null;
      } else {
         boolean var4 = false;

         for (int var5 = 0; var5 < var3.length; var5++) {
            if (-1 != var3[var5] && !field5288.method11944(var3[var5], 0, (byte)-73)) {
               var4 = true;
            }
         }

         if (var4) {
            return null;
         } else {
            ModelData[] var9 = new ModelData[var3.length];

            for (int var6 = 0; var6 < var3.length; var6++) {
               var9[var6] = ModelData.method4067(field5288, var3[var6], 0);
            }

            ModelData var10;
            if (var9.length == 1) {
               var10 = var9[0];
               if (var10 == null) {
                  var10 = new ModelData(var9, var9.length);
               }
            } else {
               var10 = new ModelData(var9, var9.length);
            }

            if (this.field5312 != null) {
               short[] var7 = this.field5313;
               if (null != var2 && var2.field5373 != null) {
                  var7 = var2.field5373;
               }

               for (int var8 = 0; var8 < this.field5312.length; var8++) {
                  var10.method4031(this.field5312[var8], var7[var8]);
               }
            }

            if (this.field5314 != null) {
               short[] var11 = this.field5315;
               if (var2 != null && null != var2.field5374) {
                  var11 = var2.field5374;
               }

               for (int var12 = 0; var12 < this.field5314.length; var12++) {
                  var10.method4035(this.field5314[var12], var11[var12]);
               }
            }

            return var10;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)S")
   @ObfuscatedName("cc")
   public short method9028(int var1) {
      return this.field5329 != null && var1 < this.field5329.length ? this.field5329[var1] : -1;
   }

   public int[] getModels() {
      return this.field5290;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   public int method9030(int var1) {
      try {
         return this.field5330 * -1509122389;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.ai(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Loz;")
   @ObfuscatedName("az")
   public classOZ method8973(int var1) {
      try {
         return this.field5316;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lou;ILou;ILpp;)Lfx;")
   @ObfuscatedName("bg")
   public final Model method8984(Animation var1, int var2, Animation var3, int var4, NpcOverrides var5) {
      if (null != this.field5323) {
         NPCComposition var10 = this.method8998(652890385);
         return var10 == null ? null : method8985(var10, var1, var2, var3, var4, var5, -848657705);
      } else {
         long var6 = this.field5300 * 283969983;
         if (var5 != null) {
            var6 |= -4980301671096129853L * var5.field5376 << 16;
         }

         Model var8 = (Model)NodeCache.method6460(field5335, var6);
         if (var8 == null) {
            ModelData var9 = this.method8992(this.field5290, var5, 795096365);
            if (null == var9) {
               return null;
            }

            var8 = var9.method4070(423192979 * this.field5321 + 64, 850 + 2098640321 * this.field5322, -30, -50, -30);
            field5335.method6464(var8, var6);
         }

         Model var11;
         if (null != var1 && null != var3) {
            var11 = var1.method8830(var8, var2, var3, var4, 1490505301);
         } else if (var1 != null) {
            var11 = var1.method8829(var8, var2, (byte)0);
         } else if (var3 != null) {
            var11 = var3.method8829(var8, var4, (byte)0);
         } else {
            var11 = var8.method5106(true);
         }

         if (128 != this.field5319 * 781957557 || 611915565 * this.field5320 != 128) {
            var11.method5251(781957557 * this.field5319, this.field5320 * 611915565, 781957557 * this.field5319);
         }

         return var11;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpl;B)Z")
   @ObfuscatedName("az")
   public static boolean method9042(NPCComposition var0, byte var1) {
      if (var0 == null) {
         return var0.method9046(var1);
      } else {
         try {
            if (var0.field5323 == null) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               } else {
                  if (-1 == var0.field5281.field6687 * -1727985133) {
                     if (var1 >= 0) {
                        throw new IllegalStateException();
                     }

                     if (var0.field5333.field6783 == null) {
                        return false;
                     }
                  }

                  return true;
               }
            } else {
               int[] var2 = var0.field5323;
               int var3 = 0;

               while (true) {
                  if (var3 >= var2.length) {
                     return false;
                  }

                  if (var1 >= 0) {
                     throw new IllegalStateException();
                  }

                  int var4 = var2[var3];
                  if (-1 != var4) {
                     if (var1 >= 0) {
                        throw new IllegalStateException();
                     }

                     NPCComposition var5 = classOH.method8480(var4, 1854770430);
                     if (-1 != -1727985133 * var5.field5281.field6687) {
                        break;
                     }

                     if (var1 >= 0) {
                        throw new IllegalStateException();
                     }

                     if (null != var5.field5333.field6783) {
                        if (var1 >= 0) {
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
            throw classEG.method3899(var6, "pl.av(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)J")
   @ObfuscatedName("bl")
   public long method9007(int var1, long var2) {
      return classBW.method1275(this.field5298, var1, var2);
   }

   public int getFootprintSize() {
      return this.field5330 * -1509122389;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bf")
   public boolean method9001() {
      if (this.field5323 == null) {
         return true;
      } else {
         int var1 = -1;
         if (this.field5294 * -771216485 != -1) {
            var1 = classEM.method3946(-771216485 * this.field5294, -1610705117);
         } else if (-1 != 123179353 * this.field5295) {
            var1 = classLB.field4088[123179353 * this.field5295];
         }

         return var1 >= 0 && var1 < this.field5323.length ? this.field5323[var1] != -1 : -1 != this.field5323[this.field5323.length - 1];
      }
   }

   public int[] getChatheadModels() {
      return this.field5302;
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("av")
   public boolean method9002(byte var1) {
      try {
         if (this.field5323 == null) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            int var2 = -1;
            if (this.field5294 * -771216485 != -1) {
               if (var1 >= 1) {
                  throw new IllegalStateException();
               }

               var2 = classEM.method3946(-771216485 * this.field5294, -2115024723);
            } else if (-1 != 123179353 * this.field5295) {
               if (var1 >= 1) {
                  throw new IllegalStateException();
               }

               var2 = classLB.field4088[123179353 * this.field5295];
            }

            if (var2 >= 0) {
               if (var1 >= 1) {
                  throw new IllegalStateException();
               }

               if (var2 < this.field5323.length) {
                  boolean var4;
                  if (this.field5323[var2] != -1) {
                     if (var1 >= 1) {
                        throw new IllegalStateException();
                     }

                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  return var4;
               }

               if (var1 >= 1) {
                  throw new IllegalStateException();
               }
            }

            boolean var10000;
            if (-1 != this.field5323[this.field5323.length - 1]) {
               if (var1 >= 1) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pl.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cu")
   public boolean method9012() {
      return this.field5328 != null && null != this.field5329;
   }

   public net.runelite.api.IterableHashTable getParams() {
      return this.field5298;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cb")
   public boolean method9013() {
      return this.field5328 != null && null != this.field5329;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("cp")
   public int[] method9016() {
      return this.field5328;
   }

   public int getHeightScale() {
      return this.field5320 * 611915565;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cd")
   public int method9019(int var1) {
      return null != this.field5328 && var1 < this.field5328.length ? this.field5328[var1] : -1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cv")
   public int method9020(int var1) {
      return null != this.field5328 && var1 < this.field5328.length ? this.field5328[var1] : -1;
   }

   public boolean isMinimapVisible() {
      return this.field5317;
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("cs")
   public short[] method9023() {
      return this.field5329;
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("co")
   public short[] method9024() {
      return this.field5329;
   }

   public short[] getColorToReplaceWith() {
      return this.field5313;
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("ck")
   public short[] method9025() {
      return this.field5329;
   }

   public int getSize() {
      return this.field5282 * -1216348145;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bz")
   public String method9009(int var1, String var2) {
      return classFS.method4998(this.field5298, var1, var2, (byte)119);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cn")
   public boolean method9032() {
      return this.field5331;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ch")
   public int method9036() {
      return 1794625942 * this.field5285;
   }

   public String[] getActions() {
      String[] var1 = new String[this.field5316.field5361.size()];

      for (int var2 = 0; var2 < this.field5316.field5361.size(); var2++) {
         classPB var3 = (classPB)this.field5316.field5361.get(var2);
         if (var3 != null) {
            var1[var2] = var3.field5208;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cw")
   public int method9037() {
      return -1776205057 * this.field5285;
   }

   public int[] getStats() {
      return this.field5327;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("cj")
   public String method9039() {
      return this.field5291;
   }

   @ObfuscatedSignature(descriptor = "(Lpl;III)I")
   @ObfuscatedName("ao")
   public static int method9003(NPCComposition var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method9004(var1, var1, var1);
      }

      try {
         return classGQ.method5635(var0.field5298, var1, var2, 416109443);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pl.ao(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cx")
   public boolean method9043() {
      if (this.field5323 == null) {
         return -1 != this.field5281.field6687 * -1618323126 || this.field5333.field6783 != null;
      } else {
         int[] var1 = this.field5323;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            if (-1 != var3) {
               NPCComposition var4 = classOH.method8480(var3, 1133371951);
               if (-1 != -1727985133 * var4.field5281.field6687 || null != var4.field5333.field6783) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ce")
   public boolean method9044() {
      if (this.field5323 == null) {
         return -1 != this.field5281.field6687 * -1529649401 || this.field5333.field6783 != null;
      } else {
         int[] var1 = this.field5323;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            if (-1 != var3) {
               NPCComposition var4 = classOH.method8480(var3, -1601329360);
               if (-1 != 1685055551 * var4.field5281.field6687 || null != var4.field5333.field6783) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cm")
   public boolean method9045() {
      if (this.field5323 == null) {
         return -1 != this.field5281.field6687 * -1727985133 || this.field5333.field6783 != null;
      } else {
         int[] var1 = this.field5323;

         for (int var2 = 0; var2 < var1.length; var2++) {
            int var3 = var1[var2];
            if (-1 != var3) {
               NPCComposition var4 = classOH.method8480(var3, -377396942);
               if (-1 != -1727985133 * var4.field5281.field6687 || null != var4.field5333.field6783) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;Lpp;)Ler;")
   @ObfuscatedName("ut")
   public static ModelData method8990(NPCComposition var0, NpcOverrides var1) {
      if (var0 == null) {
         var0.isMinimapVisible();
      }

      if (null != var0.field5323) {
         NPCComposition var2 = var0.method8998(652890385);
         return var2 == null ? null : var2.method8987(var1, (byte)99);
      } else {
         return var0.method8992(var0.field5302, var1, 795096365);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpl;Lou;ILou;ILpp;I)Lfx;")
   @ObfuscatedName("zw")
   public static Model method8985(NPCComposition var0, Animation var1, int var2, Animation var3, int var4, NpcOverrides var5, int var6) {
      if (var0 == null) {
         return var0.method8986(var1, var2, var1, var2, var5, var2);
      } else {
         try {
            if (null != var0.field5323) {
               NPCComposition var12 = var0.method8998(652890385);
               if (var12 == null) {
                  if (var6 >= -160372750) {
                     throw new IllegalStateException();
                  } else {
                     return null;
                  }
               } else {
                  return method8985(var12, var1, var2, var3, var4, var5, -520150610);
               }
            } else {
               long var7 = var0.field5300 * 283969983;
               if (var5 != null) {
                  if (var6 >= -160372750) {
                     throw new IllegalStateException();
                  }

                  var7 |= -4980301671096129853L * var5.field5376 << 16;
               }

               Model var9 = (Model)NodeCache.method6460(field5335, var7);
               if (var9 == null) {
                  if (var6 >= -160372750) {
                     throw new IllegalStateException();
                  }

                  ModelData var10 = var0.method8992(var0.field5290, var5, 795096365);
                  if (null == var10) {
                     if (var6 >= -160372750) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }

                  var9 = var10.method4070(423192979 * var0.field5321 + 64, 850 + 2098640321 * var0.field5322, -30, -50, -30);
                  field5335.method6464(var9, var7);
               }

               Model var13;
               label90: {
                  if (null != var1) {
                     if (var6 >= -160372750) {
                        throw new IllegalStateException();
                     }

                     if (null != var3) {
                        if (var6 >= -160372750) {
                           throw new IllegalStateException();
                        }

                        var13 = var1.method8830(var9, var2, var3, var4, -1239761340);
                        break label90;
                     }
                  }

                  if (var1 != null) {
                     if (var6 >= -160372750) {
                        throw new IllegalStateException();
                     }

                     var13 = var1.method8829(var9, var2, (byte)0);
                  } else if (var3 != null) {
                     var13 = var3.method8829(var9, var4, (byte)0);
                  } else {
                     var13 = var9.method5106(true);
                  }
               }

               if (128 == var0.field5319 * 781957557) {
                  if (var6 >= -160372750) {
                     throw new IllegalStateException();
                  }

                  if (611915565 * var0.field5320 == 128) {
                     return var13;
                  }

                  if (var6 >= -160372750) {
                     throw new IllegalStateException();
                  }
               }

               var13.method5251(781957557 * var0.field5319, var0.field5320 * 611915565, 781957557 * var0.field5319);
               return var13;
            }
         } catch (RuntimeException var11) {
            throw classEG.method3899(var11, "pl.ag(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;Lpp;)Ler;")
   @ObfuscatedName("yx")
   public static ModelData method8991(NPCComposition var0, NpcOverrides var1) {
      if (var0 == null) {
         var0.method9045();
      }

      if (null != var0.field5323) {
         NPCComposition var2 = var0.method8998(652890385);
         return var2 == null ? null : var2.method8987(var1, (byte)108);
      } else {
         return var0.method8992(var0.field5302, var1, 795096365);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lyn;)V")
   @ObfuscatedName("pr")
   @Override
   public void vmethod537(IterableHashTable var1) {
      this.field5298 = var1;
   }

   public int[] getConfigs() {
      return this.field5323;
   }

   @ObfuscatedSignature(descriptor = "(I)Lpo;")
   @ObfuscatedName("af")
   EntityOps method8976(int var1) {
      try {
         return new EntityOps();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ay")
   public boolean method9033(int var1) {
      try {
         return this.field5331;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("aa")
   public boolean method9046(byte var1) {
      try {
         if (this.field5323 == null) {
            if (var1 >= 0) {
               throw new IllegalStateException();
            } else {
               if (-1 == this.field5281.field6687 * -1727985133) {
                  if (var1 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (this.field5333.field6783 == null) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            int[] var2 = this.field5323;
            int var3 = 0;

            while (true) {
               if (var3 >= var2.length) {
                  return false;
               }

               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               int var4 = var2[var3];
               if (-1 != var4) {
                  if (var1 >= 0) {
                     throw new IllegalStateException();
                  }

                  NPCComposition var5 = classOH.method8480(var4, 1854770430);
                  if (-1 != -1727985133 * var5.field5281.field6684) {
                     break;
                  }

                  if (var1 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (null != var5.field5333.field6783) {
                     if (var1 >= 0) {
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
         throw classEG.method3899(var6, "pl.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lou;ILou;ILpp;I)Lfx;")
   @ObfuscatedName("ag")
   public final Model method8986(Animation var1, int var2, Animation var3, int var4, NpcOverrides var5, int var6) {
      try {
         if (null != this.field5323) {
            NPCComposition var12 = this.method8998(652890385);
            if (var12 == null) {
               if (var6 >= -160372750) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               return method8985(var12, var1, var2, var3, var4, var5, -520150610);
            }
         } else {
            long var7 = this.field5280 * 283969983;
            if (var5 != null) {
               if (var6 >= -160372750) {
                  throw new IllegalStateException();
               }

               var7 |= -4980301671096129853L * var5.field5376 << 16;
            }

            Model var9 = (Model)NodeCache.method6460(field5334, var7);
            if (var9 == null) {
               if (var6 >= -160372750) {
                  throw new IllegalStateException();
               }

               ModelData var10 = this.method8992(this.field5302, var5, 795096365);
               if (null == var10) {
                  if (var6 >= -160372750) {
                     throw new IllegalStateException();
                  }

                  return null;
               }

               var9 = var10.method4059(423192979 * this.field5330 + 64, 850 + 2098640321 * this.field5294, -30, -50, -30);
               field5334.method6466(var9, var7);
            }

            Model var13;
            label88: {
               if (null != var1) {
                  if (var6 >= -160372750) {
                     throw new IllegalStateException();
                  }

                  if (null != var3) {
                     if (var6 >= -160372750) {
                        throw new IllegalStateException();
                     }

                     var13 = var1.method8830(var9, var2, var3, var4, -1239761340);
                     break label88;
                  }
               }

               if (var1 != null) {
                  if (var6 >= -160372750) {
                     throw new IllegalStateException();
                  }

                  var13 = var1.method8829(var9, var2, (byte)0);
               } else if (var3 != null) {
                  var13 = var3.method8829(var9, var4, (byte)0);
               } else {
                  var13 = var9.method5110(true);
               }
            }

            if (128 == this.field5320 * 781957557) {
               if (var6 >= -160372750) {
                  throw new IllegalStateException();
               }

               if (611915565 * this.field5295 == 128) {
                  return var13;
               }

               if (var6 >= -160372750) {
                  throw new IllegalStateException();
               }
            }

            var13.method5281(781957557 * this.field5294, this.field5295 * 611915565, 781957557 * this.field5319);
            return var13;
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "pl.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)S")
   @ObfuscatedName("au")
   public short method9029(int var1, byte var2) {
      try {
         if (this.field5312 != null) {
            if (var1 < this.field5314.length) {
               return this.field5314[var1];
            }

            if (var2 >= 0) {
               throw new IllegalStateException();
            }
         }

         return -1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pl.au(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("tt")
   public static String method9010(NPCComposition var0, int var1, String var2) {
      return classFS.method4998(var0.field5298, var1, var2, (byte)109);
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("cq")
   public int[] method9050() {
      return this.field5302;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cf")
   public int method9051() {
      return this.field5320 * 781957557;
   }

   public int getCombatLevel() {
      return this.field5318 * 1781570005;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ci")
   public String method9040() {
      return this.field5291;
   }

   @ObfuscatedSignature(descriptor = "(Lpl;I)Z")
   @ObfuscatedName("ww")
   public static boolean method9014(NPCComposition var0, int var1) {
      if (var0 == null) {
         var0.method9033(var1);
      }

      try {
         boolean var10000;
         if (var0.field5328 != null && null != var0.field5329) {
            if (var1 != -267153217) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pl.ay(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("zz")
   public static String method9011(NPCComposition var0, int var1, String var2) {
      if (var0 == null) {
         var0.getWidthScale();
      }

      return classFS.method4998(var0.field5298, var1, var2, (byte)93);
   }

   public int getWidthScale() {
      return this.field5319 * 781957557;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;)Lpl;")
   @ObfuscatedName("pz")
   public static NPCComposition method8999(NPCComposition var0) {
      if (var0 == null) {
         var0.getOps();
      }

      int var1 = -1;
      if (-1 != -771216485 * var0.field5294) {
         var1 = classEM.method3946(var0.field5294 * 1082956305, -1355372185);
      } else if (105808121 * var0.field5295 != -1) {
         var1 = classLB.field4088[123179353 * var0.field5295];
      }

      int var2;
      if (var1 >= 0 && var1 < var0.field5323.length - 1) {
         var2 = var0.field5323[var1];
      } else {
         var2 = var0.field5323[var0.field5323.length - 1];
      }

      return var2 != -1 ? classOH.method8480(var2, 977579935) : null;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ao")
   public int method9004(int var1, int var2, int var3) {
      try {
         return classGQ.method5635(this.field5298, var1, var2, 416109443);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pl.ao(" + 41);
      }
   }
}
