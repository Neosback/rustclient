import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.runelite.api.TileObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ez")
public class Scene extends Renderable implements net.runelite.api.Scene {
   @ObfuscatedName("bf")
   int field2054;
   @ObfuscatedSignature(descriptor = "[Leo;")
   @ObfuscatedName("lo")
   public final GroundObject[] field2009;
   @ObfuscatedSignature(descriptor = "Lrl18;")
   @ObfuscatedName("hx")
   public final rl18 field1990;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2095 = 64;
   @ObfuscatedName("dx")
   static final int[][] field2078 = new int[][]{
      {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
      {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3},
      {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
      {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}
   };
   @ObfuscatedName("hp")
   public final int field1999;
   @ObfuscatedSignature(descriptor = "[Lrl17;")
   @ObfuscatedName("xn")
   public final rl17[] field2018;
   @ObfuscatedSignature(descriptor = "[[[Lrl10;")
   @ObfuscatedName("vg")
   public rl10[][][] field2158;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field2093 = 1024;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field2096 = 3064;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2101 = 1;
   @ObfuscatedName("kg")
   public final Set field2020;
   @ObfuscatedName("xg")
   public final int field2003;
   @ObfuscatedName("cm")
   int field2062;
   @ObfuscatedName("ac")
   static final int field2064 = Math.min(1024, 1);
   @ObfuscatedName("aa")
   static final int field2083 = Math.max(3064, 4160);
   @ObfuscatedName("cs")
   int field2119;
   @ObfuscatedName("kq")
   public int field2028;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field2097 = 1024;
   @ObfuscatedName("ay")
   static final int field2067 = classKG.method6508(724424251) / 1024;
   @ObfuscatedName("bu")
   int field2032;
   @ObfuscatedSignature(descriptor = "[[Lrl17;")
   @ObfuscatedName("go")
   public final rl17[][] field2017;
   @ObfuscatedName("yu")
   public final int[] field2016;
   @ObfuscatedName("mu")
   public int[] field2154;
   @ObfuscatedName("ad")
   static final int[] field2068 = new int[]{19, 55, 38, 155, 255, 110, 137, 205, 76};
   @ObfuscatedName("ap")
   static final int[] field2065 = new int[]{160, 192, 80, 96, 0, 144, 80, 48, 160};
   @ObfuscatedName("bt")
   public int field1988;
   @ObfuscatedName("au")
   static final int[] field2076 = new int[]{76, 8, 137, 4, 0, 1, 38, 2, 19};
   @ObfuscatedName("em")
   public int field2139;
   @ObfuscatedName("ir")
   public static final int[] field2073 = new int[184];
   @ObfuscatedSignature(descriptor = "Lfl;")
   @ObfuscatedName("bn")
   public final classFL field2033;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("dd")
   Deque2 field2047;
   @ObfuscatedName("aw")
   static final int[] field2079 = new int[]{0, 4, 4, 8, 0, 0, 8, 0, 0};
   @ObfuscatedSignature(descriptor = "Lex;")
   @ObfuscatedName("de")
   classEX field2050;
   @ObfuscatedName("pq")
   public boolean field2153;
   @ObfuscatedSignature(descriptor = "Lei;")
   @ObfuscatedName("xe")
   public FloatProjection field2022;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field2103 = 90;
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final int field2102 = 32;
   @ObfuscatedName("al")
   public static final int field2069 = (field2083 - field2064) / 256 + 1;
   @ObfuscatedName("bj")
   public int field2109;
   @ObfuscatedName("vb")
   public int field2035;
   @ObfuscatedName("qt")
   public float field2091;
   @ObfuscatedName("kk")
   public static int field2141;
   @ObfuscatedName("ah")
   final boolean field2060;
   @ObfuscatedName("bi")
   boolean field2031;
   @ObfuscatedSignature(descriptor = "Lfl;")
   @ObfuscatedName("bx")
   final classFL field2059;
   @ObfuscatedName("kz")
   public int field2142;
   @ObfuscatedName("ox")
   public final int field2000;
   @ObfuscatedSignature(descriptor = "[Lfi;")
   @ObfuscatedName("ks")
   public final DecorativeObject[] field2008;
   @ObfuscatedName("lx")
   public int field2143;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field2104 = 4160;
   @ObfuscatedName("dp")
   static BitSet field2148;
   @ObfuscatedName("yg")
   public final int field1998;
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("bm")
   public classUU field2026;
   @ObfuscatedSignature(descriptor = "[Lfe;")
   @ObfuscatedName("fw")
   public final WallObject[] field2007;
   @ObfuscatedSignature(descriptor = "Ldz;")
   @ObfuscatedName("rl")
   public WorldView field2002;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field2100 = 50;
   @ObfuscatedName("bw")
   public int field2045;
   @ObfuscatedName("pp")
   public float field2090;
   @ObfuscatedName("ck")
   int field2113;
   @ObfuscatedName("qd")
   public final byte[] field2014;
   @ObfuscatedName("km")
   public short[][][] field2159;
   @ObfuscatedName("bo")
   int field2024;
   @ObfuscatedName("dq")
   static int field2132;
   @ObfuscatedName("oj")
   public int field2025;
   @ObfuscatedSignature(descriptor = "Lrl16;")
   @ObfuscatedName("un")
   public static final rl16 field2075 = new rl16();
   @ObfuscatedName("sa")
   public int field2034;
   @ObfuscatedSignature(descriptor = "Lfv;")
   @ObfuscatedName("pu")
   public Projection field2021;
   @ObfuscatedName("ib")
   public final int field1994;
   @ObfuscatedName("cf")
   public float field2088;
   @ObfuscatedName("mh")
   public float field2053;
   @ObfuscatedSignature(descriptor = "[[[Lrl10;")
   @ObfuscatedName("us")
   public rl10[][][] field1991;
   @ObfuscatedName("be")
   int field2107;
   @ObfuscatedName("ci")
   int field2036;
   @ObfuscatedName("bq")
   int field2057;
   @ObfuscatedName("bc")
   public int field1989;
   @ObfuscatedName("db")
   int field2048;
   @ObfuscatedName("hb")
   public static java.util.Deque field2074 = new ArrayDeque();
   @ObfuscatedName("bs")
   int field2030;
   @ObfuscatedName("fr")
   public int[][][] field2157;
   @ObfuscatedName("dg")
   static int field2126;
   @ObfuscatedName("bl")
   int field2038;
   @ObfuscatedName("bv")
   int[][][] field2029;
   @ObfuscatedName("bh")
   int field2039;
   @ObfuscatedName("ca")
   public float field2086;
   @ObfuscatedName("rl")
   public final int field2001;
   @ObfuscatedName("rt")
   public final int field1993;
   @ObfuscatedSignature(descriptor = "[Lfj;")
   @ObfuscatedName("zp")
   public final SceneTilePaint[] field2005;
   @ObfuscatedName("bz")
   int field2063;
   @ObfuscatedName("qa")
   public int field2144;
   @ObfuscatedSignature(descriptor = "[Lfb;")
   @ObfuscatedName("ch")
   GameObject[] field2023;
   @ObfuscatedName("qp")
   public final int field1996;
   @ObfuscatedName("zx")
   public final int field1995;
   @ObfuscatedName("cu")
   int field2121;
   @ObfuscatedName("oz")
   public int[][][] field1992;
   @ObfuscatedName("ry")
   public short[][][] field2160;
   @ObfuscatedName("ba")
   public float field2084;
   @ObfuscatedName("cb")
   int field2111;
   @ObfuscatedName("ct")
   int field2120;
   @ObfuscatedName("cp")
   int field2116;
   @ObfuscatedName("cq")
   int field2117;
   @ObfuscatedName("cl")
   int field2114;
   @ObfuscatedName("iq")
   public boolean field2152;
   @ObfuscatedName("cd")
   int field2112;
   @ObfuscatedName("cv")
   int field2122;
   @ObfuscatedName("ea")
   static BitSet field2149;
   @ObfuscatedSignature(descriptor = "[Leq;")
   @ObfuscatedName("jw")
   public final ItemLayer[] field2010;
   @ObfuscatedName("cy")
   int field2123;
   @ObfuscatedName("cc")
   public float field2087;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   static final int field2098 = 128;
   @ObfuscatedName("ai")
   static final int[] field2077 = new int[]{0, 0, 2, 0, 0, 2, 1, 1, 0};
   @ObfuscatedName("cg")
   int field2042;
   @ObfuscatedName("vs")
   public int field2027;
   @ObfuscatedName("jb")
   public Map field1987;
   @ObfuscatedName("ti")
   public float field2092;
   @ObfuscatedName("mc")
   public final byte[] field2011;
   @ObfuscatedName("bd")
   int[][][] field2058;
   @ObfuscatedName("we")
   public int field2146;
   @ObfuscatedSignature(descriptor = "[Lep;")
   @ObfuscatedName("by")
   classEP[] field2046;
   @ObfuscatedName("br")
   public int field2110;
   @ObfuscatedSignature(descriptor = "[Lfb;")
   @ObfuscatedName("uw")
   public final GameObject[] field2012;
   @ObfuscatedName("cz")
   boolean field2041;
   @ObfuscatedName("jl")
   public final byte[] field2013;
   @ObfuscatedName("sn")
   public float field2040;
   @ObfuscatedName("bb")
   public int field2106;
   @ObfuscatedName("cw")
   int field2043;
   @ObfuscatedName("bk")
   public float field2085;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field2094 = 25;
   @ObfuscatedName("ej")
   static int field2138;
   @ObfuscatedSignature(descriptor = "[[Lep;")
   @ObfuscatedName("ce")
   classEP[][] field2037;
   @ObfuscatedName("nm")
   public final int field1997;
   @ObfuscatedName("cr")
   int field2118;
   @ObfuscatedSignature(descriptor = "Lfx;")
   @ObfuscatedName("dc")
   Model field2147;
   @ObfuscatedName("am")
   public static boolean field2081 = false;
   @ObfuscatedName("dm")
   static final int[][] field2082 = new int[][]{
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
      {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1},
      {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
      {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1},
      {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
      {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
      {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0},
      {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1},
      {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}
   };
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final int field2099 = 256;
   @ObfuscatedName("dy")
   static int field2137;
   @ObfuscatedName("cn")
   public float field2089;
   @ObfuscatedName("di")
   static int field2127;
   @ObfuscatedName("tx")
   public int field2145;
   @ObfuscatedName("du")
   static int field2135;
   @ObfuscatedName("ho")
   public byte[][][] field2155;
   @ObfuscatedName("dt")
   static int field2134;
   @ObfuscatedName("bg")
   public int field2108;
   @ObfuscatedName("mk")
   public static final int[] field2072 = new int[184];
   @ObfuscatedName("dr")
   static int field2133;
   @ObfuscatedSignature(descriptor = "[Lfn;")
   @ObfuscatedName("yl")
   public final SceneTileModel[] field2006;
   @ObfuscatedName("dk")
   static int field2129;
   @ObfuscatedName("co")
   int field2115;
   @ObfuscatedSignature(descriptor = "[Lew;")
   @ObfuscatedName("dw")
   Tile[] field2055;
   @ObfuscatedName("dh")
   int field2049;
   @ObfuscatedName("ak")
   static final int[] field2080 = new int[]{1, 1, 0, 0, 0, 8, 0, 0, 8};
   @ObfuscatedName("wr")
   public byte[][][] field2156;
   @ObfuscatedName("dz")
   int field2051;
   @ObfuscatedName("dn")
   int field2052;
   @ObfuscatedName("dv")
   static int field2136;
   @ObfuscatedName("do")
   static int field2131;
   @ObfuscatedName("of")
   public int field1986;
   @ObfuscatedName("mx")
   public long[] field2015;
   @ObfuscatedName("cx")
   int[] field2061;
   @ObfuscatedName("dl")
   static int field2130;
   @ObfuscatedName("za")
   public BitSet field2019;
   @ObfuscatedName("cj")
   int field2044;
   @ObfuscatedName("df")
   static int field2125;
   @ObfuscatedName("ds")
   static int[] field2070 = new int[field2069];
   @ObfuscatedName("dj")
   static int field2128;
   @ObfuscatedName("da")
   static int field2124;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2105 = 128;
   @ObfuscatedName("ey")
   static BitSet field2151;
   @ObfuscatedSignature(descriptor = "[[[Lew;")
   @ObfuscatedName("bp")
   public Tile[][][] field2056;
   @ObfuscatedName("ek")
   static BitSet field2150;
   @ObfuscatedName("ud")
   public final int[] field2004;
   @ObfuscatedName("es")
   static int field2140;
   @ObfuscatedName("ar")
   static final int[] field2066 = new int[]{2, 0, 0, 2, 0, 0, 0, 4, 4};

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dt")
   public void method4358(int var1, int var2, int var3) {
      this.field2042 = var1;
      this.field2043 = var2;
      this.field2044 = var3;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIILee;IJI)Z")
   @ObfuscatedName("qt")
   public boolean method4519(int var1, int var2, int var3, int var4, int var5, int var6, Renderable var7, int var8, long var9, int var11) {
      return method4208(this, var1, var2, var3, var4, var5, var6, var7, var8, var9, var11);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ku")
   public void method4352(boolean var1) {
      if (!method4364(this) || var1) {
         this.field2041 = true;
         this.field2031 = var1;
         this.field2034 = -1;
         this.field2035 = -1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;IIIILee;JI)V")
   @ObfuscatedName("se")
   public static void method4192(Scene var0, int var1, int var2, int var3, int var4, Renderable var5, long var6, int var8) {
      if (var0 == null) {
         var0.method4269(var1, var1, var1, var1, var1, var1);
      }

      if (var5 != null) {
         GroundObject var9 = new GroundObject();
         var9.field1814 = var5;
         var9.field1804 = var2 * 2134971449 + 1250210624;
         var9.field1806 = var3 * 719086569 + -1083896930;
         var9.field1805 = var4 * -1938914931;
         var9.field1813 = var6 * -8879266543907161229L;
         GroundObject.method3963(var9, var8, (byte)2);
         if (var0.field2056[var1][var2][var3] == null) {
            var0.field2056[var1][var2][var3] = new Tile(var1, var2, var3);
         }

         var0.field2056[var1][var2][var3].field1965 = var9;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   public void method4158() {
      this.field2062 = -1;
      this.field2147 = null;
   }

   @ObfuscatedSignature(descriptor = "(Z)I")
   @ObfuscatedName("ax")
   public static int method4161(boolean var0) {
      return var0 ? 1 : 1024;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("rq")
   public int method4415(int var1, int var2, int var3) {
      return this.field2058[var1][var2 + 1][var3 + 1]
            + this.field2058[var1][var2][var3]
            + this.field2058[var1][var2 + 1][var3]
            + this.field2058[var1][var2][var3 + 1]
         >> 2;
   }

   @ObfuscatedSignature(descriptor = "(IIILup;Lup;IIIZ)V")
   @ObfuscatedName("dh")
   public void method4435(int var1, int var2, int var3, classUP var4, classUP var5, int var6, int var7, int var8, boolean var9) {
      int var10 = client.field1021 >> 7 & 15;
      rl23.method10104(var10);
      client.method2378();
      var1 = classKI.field3596 * -325062789;
      var2 = classNL.field4682 * 1615527037;
      var3 = classAI.field167 * 1343311673;
      boolean var11 = classOE.field4841.isGpu();
      if (!var11 && client.field1093 != 0) {
         classFQ.method4977(
            classOE.field4841.getViewportXOffset(),
            classOE.field4841.getViewportYOffset(),
            classOE.field4841.getViewportWidth(),
            classOE.field4841.getViewportHeight(),
            client.field1093
         );
      }

      this.field2116 = Ints.constrainToRange(var1, this.field2110 << 7, (this.field2142 << 7) - 1);
      this.field2117 = var2;
      this.field2114 = Ints.constrainToRange(var3, this.field2145 << 7, (this.field2139 << 7) - 1);
      this.field2084 = Floats.constrainToRange(client.field1057, this.field2110 << 7, (this.field2142 << 7) - 1);
      this.field2086 = client.field1060;
      this.field2091 = Floats.constrainToRange(client.field1055, this.field2145 << 7, (this.field2139 << 7) - 1);
      this.field2112 = this.field2116 >> 7;
      this.field2122 = this.field2114 >> 7;
      this.field2112 = this.field2112 + this.field2146;
      this.field2122 = this.field2122 + this.field2146;
      this.field2119 = var7 >> 7;
      this.field2123 = var8 >> 7;
      this.field2119 = this.field2119 + this.field2146;
      this.field2123 = this.field2123 + this.field2146;
      this.field2025 = var6;
      int var12 = Ints.constrainToRange(var4.field6360 * 1379695375, 1, 4160);
      method4522((var12 - field2064) / 256, var5.field6360 * 1379695375 / 1024);
      this.field2085 = var4.method11525();
      this.field2088 = var4.method11523();
      this.field2092 = var5.method11525();
      this.field2090 = var5.method11523();
      this.field2087 = client.field1052;
      this.field2089 = client.field1040;
      IntProjection var13 = new IntProjection(this.field2116, this.field2117, this.field2114, var4, var5, var7, var8);
      var13.field1926 = this.field2084;
      var13.field1929 = this.field2086;
      var13.field1928 = this.field2091;
      var13.field1927 = this.field2087;
      var13.field1932 = this.field2089;
      var13.field1931 = (float)Math.sin(this.field2087);
      var13.field1933 = (float)Math.cos(this.field2087);
      var13.field1930 = (float)Math.sin(this.field2089);
      var13.field1934 = (float)Math.cos(this.field2089);
      this.field2021 = var13;
      IntProjection.field1937 = var13;
      if (this.field2147 != null && !classOE.field4841.isGpu()) {
         this.field2147.method5283(classFZ.field2517);
         this.field2147.method5290(0, var4, var5, 0, 0, 0, -1L);
      }

      if ((client.field1021 & 16) != 0) {
         this.method4508(true, var9);
         this.method4474(var13, null);
         classFH.field2255.field2213.method4861(this.field2059);
         this.method4511(null);
         classOE.field4841.getCallbacks().drawScene();
      } else if (classFH.field2259.method4972() && !classOE.field4841.isGpu()) {
         classFH.field2255.field2215 = 0;
         this.method4508(true, var9);
         this.method4453(var13);
         classFH.field2255.field2213.method4861(this.field2059);
         classFH.field2255.field2215 = 1;
         this.method4508(false, var9);
         this.method4480(var13);
         classFH.field2255.field2213.method4861(this.field2059);
         classFH.field2255.field2215 = 2;
         classOE.field4841.getCallbacks().drawScene();
         if (client.field1117 != null) {
            client.field1117.postDrawScene();
         }
      } else {
         classFH.field2255.field2215 = 2;
         this.method4508(true, var9);
         this.method4480(var13);
         classFH.field2255.field2213.method4861(this.field2059);
         classOE.field4841.getCallbacks().drawScene();
         if (client.field1117 != null) {
            client.field1117.postDrawScene();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Z)V")
   @ObfuscatedName("pf")
   public static void method4353(Scene var0, boolean var1) {
      if (var0 == null) {
         var0.method4357(var1);
      } else if (!method4364(var0) || var1) {
         var0.field2041 = true;
         var0.field2031 = var1;
         var0.field2034 = -1;
         var0.field2035 = -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIF)V")
   @ObfuscatedName("pj")
   public void method4420(int var1, int var2, float var3) {
      int var4 = var1;
      int var5 = var2;
      Player var6 = classDC.field1391;
      if (this.field2060 && var6 != null && var6.method3281().isTopLevel()) {
         int var7 = var6.field1484 * -1547553299 >> 7;
         int var8 = var6.field1486 * -1272026483 >> 7;
         byte var11 = 70;
         int var12 = (int)Math.hypot(var7 - var1, var8 - var2) - 70;
         if (var12 > 0) {
            var4 = (70 * var1 + var12 * var7) / (70 + var12);
            var5 = (70 * var2 + var12 * var8) / (70 + var12);
         }
      }

      this.field2024 = var4;
      this.field2032 = var5;
      this.field2040 = var3;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   public static int method4169() {
      return field2064;
   }

   public Scene(boolean var1, int var2, int var3, int var4, int var5, classEX var6, int[][][] var7) {
      Scene var8 = this;
      this.field1986 = 0;
      this.field1987 = null;
      this.field1988 = -1;
      this.field1989 = -1;
      this.field1990 = new rl18(this);
      this.field1991 = new rl10[var2][var3][var4];
      this.field1992 = new int[var2][var3][var4];
      int var16 = 32 - Integer.numberOfLeadingZeros(var3 + 3);
      int var17 = 32 - Integer.numberOfLeadingZeros(var4 + 3);
      int var18 = 4 * (1 << var16) * (1 << var17);
      int var19 = var18 << 1;
      this.field1993 = var17;
      this.field1994 = var16 + var17;
      this.field1995 = (1 << var16) - 1;
      this.field1996 = (1 << var17) - 1;
      this.field1997 = 1 << var16 + var17;
      this.field1998 = 1 << var17;
      this.field1999 = 1;
      this.field2000 = var18 - 1;
      this.field2001 = var18 - 2;
      this.field2003 = var18 - 3;
      this.field2004 = new int[var18];
      this.field2005 = new SceneTilePaint[var18];
      this.field2006 = new SceneTileModel[var18];
      this.field2007 = new WallObject[var18];
      this.field2008 = new DecorativeObject[var18];
      this.field2009 = new GroundObject[var18];
      this.field2010 = new ItemLayer[var18];
      this.field2011 = new byte[var18];
      this.field2012 = new GameObject[var18 * 5];
      this.field2013 = new byte[var18 * 5];
      this.field2014 = new byte[var18];
      this.field2015 = new long[var18];
      this.field2016 = new int[var19];
      Arrays.fill(this.field2016, this.field2000);
      this.method4443();
      int var20 = var3 >> 3;
      int var21 = var4 >> 3;
      this.field2017 = new rl17[var20][var21];
      this.field2018 = new rl17[var20 * var21];

      for (int var22 = 0; var22 < var20; var22++) {
         int var23 = 0;

         while (var23 < var21) {
            rl17 var24 = var8.field2017[var22][var23] = new rl17();
            var24.field5710 = var22;
            var24.field5705 = var23++;
         }
      }

      if (var1) {
         var8.field2019 = classLB.field4088 != null ? new BitSet(classLB.field4088.length) : null;
         var8.field2020 = new HashSet();
      } else {
         var8.field2019 = null;
         var8.field2021 = new FloatProjection(new classUU());
         var8.field2022 = new FloatProjection(new classUU());
         var8.field2020 = Collections.emptySet();
      }

      this.field2033 = new classFL();
      this.field2059 = new classFL();
      this.field2025 = 0;
      this.field2026 = new classUU();
      this.field2027 = 0;
      this.field2028 = 0;
      this.field2045 = 0;
      this.field2053 = 1.0F;
      this.field2024 = -1;
      this.field2032 = -1;
      this.field2040 = -1.0F;
      this.field2034 = -1;
      this.field2035 = -1;
      this.field2031 = false;
      this.field2038 = 0;
      this.field2039 = 0;
      this.field2063 = 0;
      this.field2023 = new GameObject[55];
      this.field2041 = false;
      this.field2042 = 0;
      this.field2043 = 0;
      this.field2044 = 0;
      this.field2036 = 4;
      this.field2062 = -1;
      this.field2047 = new Deque2();
      this.field2048 = 0;
      this.field2055 = new Tile[32];
      this.field2049 = 0;
      this.field2050 = classEX.field1975;
      this.field2051 = 0;
      this.field2052 = 0;
      this.field2057 = var2;
      this.field2054 = var3;
      this.field2030 = var4;
      this.field2056 = new Tile[var2][var3][var4];
      this.field2029 = new int[var2][var3 + 1][var4 + 1];
      this.field2058 = var7;
      this.field2049 = var5;
      this.field2050 = var6;
      this.field2060 = var1;
      if (var1) {
         this.field2061 = new int[this.field2036];
         this.field2037 = new classEP[this.field2036][500];
         this.field2046 = new classEP[500];
      }

      this.method4455();
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ao")
   static final int method4172(int var0, int var1) {
      var1 = var1 * (var0 & 127) >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Ldy;Lnet/runelite/api/TileObject;IIIIJ)V")
   @ObfuscatedName("nq")
   public void method4494(Projection var1, DynamicObject var2, TileObject var3, int var4, int var5, int var6, int var7, long var8) {
      assert !var2.field1681;

      int var10 = client.field1021 >> 7 & 15;
      if (var10 > 0) {
         var2.method3726();
         rl23.method10105(var3, var2, var4, var5, var6, var7);
      } else {
         Model var11;
         try {
            var11 = var2.method3853();
         } catch (Exception var13) {
            client.field1028.debug("exception drawing dynamic game entity", var13);
            return;
         }

         if (var11 != null) {
            var2.setModelHeight(var11.getModelHeight());
            int var12 = var2.getAnimationHeightOffset();
            client.field1117.drawDynamic(-1, var1, this, var3, var2, var11, var4, var5, var6 - var12, var7);
            client.method1631(var1, this.field2022, var11, var4, var5, var6 - var12, var7, var8);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)Z")
   @ObfuscatedName("al")
   public static boolean method4174(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var1 < var2 && var1 < var3 && var1 < var4) {
         return false;
      } else if (var1 > var2 && var1 > var3 && var1 > var4) {
         return false;
      } else if (var0 < var5 && var0 < var6 && var0 < var7) {
         return false;
      } else if (var0 > var5 && var0 > var6 && var0 > var7) {
         return false;
      } else {
         int var8 = (var1 - var2) * (var6 - var5) - (var0 - var5) * (var3 - var2);
         int var9 = (var1 - var3) * (var7 - var6) - (var0 - var6) * (var4 - var3);
         int var10 = (var1 - var4) * (var5 - var7) - (var0 - var7) * (var2 - var4);
         if (var8 == 0) {
            if (var9 != 0) {
               return var9 < 0 ? var10 <= 0 : var10 >= 0;
            } else {
               return true;
            }
         } else {
            return var8 < 0 ? var9 <= 0 && var10 <= 0 : var9 >= 0 && var10 >= 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("cz")
   static boolean method4299(int var0, int var1, int var2, int var3, boolean var4) {
      return method4293(field2148, var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("xi")
   public void method4481(int var1) {
      int var2 = (var1 << 1) + 1;
      var2 = this.field2016[var2];
      var2 <<= 1;
      int var5 = var1 << 1;
      this.field2016[var2] = this.field2016[var5];
      var2 = var1 << 1;
      var2 = this.field2016[var2];
      var2 = (var2 << 1) + 1;
      int var9 = (var1 << 1) + 1;
      this.field2016[var2] = this.field2016[var9];
      var2 = (var1 << 1) + 1;
      int var11 = this.field2001;
      int var12 = (var11 << 1) + 1;
      this.field2016[var2] = this.field2016[var12];
      var2 = var1 << 1;
      this.field2016[var2] = this.field2001;
      var2 = (var1 << 1) + 1;
      var2 = this.field2016[var2];
      var2 <<= 1;
      this.field2016[var2] = var1;
      var2 = var1 << 1;
      var2 = this.field2016[var2];
      var2 = (var2 << 1) + 1;
      this.field2016[var2] = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ay")
   public void method4512(int var1) {
      this.field2056 = null;
      this.field2055 = null;
      this.setMinLevel(var1);

      for (int var2 = 0; var2 < this.field2054; var2++) {
         for (int var3 = 0; var3 < this.field2030; var3++) {
            int var4 = this.method4438(var1, var2, var3);
            if (!this.method4426(var4)) {
               this.method4483(var4, var1, var2, var3);
            }

            if (this.field1991[var1][var2][var3] == null) {
               this.field1991[var1][var2][var3] = new rl10(this, var4);
               this.method4456(var1, var2, var3);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;)V")
   @ObfuscatedName("eg")
   public void method4511(Projection var1) {
      this.field2059.method4861(classFH.field2255.field2213);
      classFH.field2255.field2213.method4861(this.field2033);
      client.field1117.drawPass(var1, this, 2);

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = this.field2108 - 1; var3 >= 0; var3--) {
            rl17 var4 = this.field2018[var3];
            if (var4.field5711) {
               client.field1117.drawZoneAlpha(var1, this, var2, var4.field5710, var4.field5705);

               for (int var5 = 0; var5 < var4.field5704.size(); var5++) {
                  GameObject var6 = (GameObject)var4.field5704.get(var5);
                  if (var6.field2185 * 568825275 == var2 && var6.field2198 instanceof Scene) {
                     Scene var7 = (Scene)var6.field2198;
                     var7.method4511(var7.field2022);
                  }
               }
            }
         }
      }

      client.field1117.drawPass(var1, this, 1);
      client.field1117.postSceneDraw(this);
      this.method4548();
      classFH.field2255.field2213.method4861(this.field2059);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("sm")
   public void method4425() {
      for (int var1 = 0; var1 < 4; var1++) {
         for (int var2 = 0; var2 < this.field2054; var2++) {
            for (int var3 = 0; var3 < this.field2030; var3++) {
               int var4 = this.method4438(var1, var2, var3);
               if (this.method4426(var4)) {
                  int var5 = var2 >> 3;
                  int var6 = var3 >> 3;
                  rl17 var7 = this.field2017[var5][var6];
                  if ((this.field2004[var4] & 32) != 0) {
                     assert var1 == 0;

                     int var8 = this.field2058[0][var2][var3];
                     this.method4451(var7, var2, var3, var8);
                  }

                  int var14 = (this.field2155[1][var2][var3] & 2) >> 1;
                  int var9 = var1 + var14;
                  int var10 = this.field2058[var9][var2][var3];
                  this.method4451(var7, var2, var3, var10);
                  int var11 = this.field2004[var4];
                  if ((var11 & 2048) != 0) {
                     GroundObject var12 = this.field2009[var4];
                     if (var12.field1814 instanceof Model) {
                        this.method4431(var10, var7, (Model)var12.field1814);
                     }
                  }

                  if ((var11 & 16384) != 0) {
                     WallObject var15 = this.field2007[var4];
                     if (var15.field2247 instanceof Model) {
                        this.method4431(var10, var7, (Model)var15.field2247);
                     }

                     if (var15.field2246 instanceof Model) {
                        this.method4431(var10, var7, (Model)var15.field2246);
                     }
                  }

                  if ((var11 & 32768) != 0) {
                     DecorativeObject var16 = this.field2008[var4];
                     if (var16.field2277 instanceof Model) {
                        this.method4431(var10, var7, (Model)var16.field2277);
                     }

                     if (var16.field2276 instanceof Model) {
                        this.method4431(var10, var7, (Model)var16.field2276);
                     }
                  }

                  for (int var17 = 0; var17 < this.field2011[var4]; var17++) {
                     GameObject var13 = this.field2012[var4 * 5 + var17];
                     if (var13.field2198 instanceof Model) {
                        this.method4431(var10, var7, (Model)var13.field2198);
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aq")
   public void method4436(int var1, int var2) {
      int var3 = var1 - this.field2146;
      int var4 = var2 - this.field2146;
      this.method4459(this.method4438(0, var1, var2), this.field2003);

      for (int var5 = 0; var5 < 3; var5++) {
         int var6 = this.method4438(var5, var1, var2);
         this.method4459(var6 + this.field1997, var6);
         if (this.method4426(var6)) {
            byte var7 = this.field2011[var6];

            for (int var8 = 0; var8 < var7; var8++) {
               GameObject var9 = this.field2012[var6 * 5 + var8];
               long var10 = var9.field2183 * 1745853678706228013L;
               int var12 = (int)(var10 >> 16 & 7L);
               if (var12 == 2 && var9.field2186 * 1138017605 == var3 && var9.field2192 * -555977777 == var4) {
                  var9.field2185 = (var9.field2185 * 568825275 - 1) * -1965758605;
               }
            }

            WallObject var15 = this.field2007[var6];
            if (var15 != null) {
               var15.field2245--;
            }

            GroundObject var16 = this.field2009[var6];
            if (var16 != null) {
               var16.field1812--;
            }

            DecorativeObject var17 = this.field2008[var6];
            if (var17 != null) {
               var17.field2275--;
            }
         }
      }

      int var13 = this.method4438(0, var1, var2);
      if (!this.method4426(var13)) {
         this.method4483(var13, 0, var1, var2);
      }

      if (this.field1991[0][var1][var2] == null) {
         this.field1991[0][var1][var2] = new rl10(this, var13);
         this.method4456(0, var1, var2);
      }

      this.field2004[var13] = this.field2004[var13] | 32;
      int var14 = this.method4438(3, var1, var2);
      this.method4459(this.field2003, var14);
      this.field2004[var14] = this.field2004[var14] & -2;
      this.method4447(this.field2003);
      this.field1991[3][var1][var2] = null;
      this.method4456(3, var2, var2);
      this.field1991[0][var1][var2].field5691 = new rl10(this, var14);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("mi")
   public void method4520(int var1) {
      this.method4512(var1);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("am")
   public void method4183(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      classEP var9 = new classEP();
      var9.field1819 = var3 / 128 * 704810239;
      var9.field1825 = var4 / 128 * -1209725141;
      var9.field1828 = var5 / 128 * -78990867;
      var9.field1831 = var6 / 128 * 950850697;
      var9.field1816 = var2 * 185901273;
      var9.field1815 = var3 * -1875068183;
      var9.field1832 = var4 * 1706709409;
      var9.field1818 = var5 * 106408303;
      var9.field1827 = var6 * 1541236735;
      var9.field1829 = var7 * -969645925;
      var9.field1821 = var8 * 1077506721;
      this.field2037[var1][this.field2061[var1]++] = var9;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cq")
   public void method4521(boolean var1) {
      method4354(this, var1);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ah")
   public void method4467(int var1, int var2, int var3, int var4) {
      int var5 = this.method4438(var1, var2, var3);
      if ((this.field2155[var1][var2][var3] & 8) != 0) {
         this.field2004[var5] = this.field2004[var5] | 64;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIIIIIIIIIII)V")
   @ObfuscatedName("bn")
   public void method4479(
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20
   ) {
      if (var4 == 0) {
         SceneTilePaint var21 = new SceneTilePaint(var11, var12, var13, var14, -1, var19, false);

         for (int var22 = var1; var22 >= 0; var22--) {
            int var23 = this.method4438(var22, var2, var3);
            if (this.field1991[var22][var2][var3] == null) {
               this.field1991[var22][var2][var3] = new rl10(this, var23);
               this.method4456(var22, var2, var3);
            }

            if (!this.method4426(var23)) {
               this.method4483(var23, var22, var2, var3);
            }
         }

         int var26 = this.method4438(var1, var2, var3);
         this.field2005[var26] = var21;
         this.field2004[var26] = this.field2004[var26] | 256 | (var21.field2284 * -665850825 != 12345678 ? 512 : 0);
      } else if (var4 != 1) {
         SceneTileModel var24 = new SceneTileModel(
            var4,
            var5,
            var6,
            var2 - this.field2146,
            var3 - this.field2146,
            var7,
            var8,
            var9,
            var10,
            var11,
            var12,
            var13,
            var14,
            var15,
            var16,
            var17,
            var18,
            var19,
            Math.max(1, var20)
         );

         for (int var27 = var1; var27 >= 0; var27--) {
            int var31 = this.method4438(var27, var2, var3);
            if (this.field1991[var27][var2][var3] == null) {
               this.field1991[var27][var2][var3] = new rl10(this, var31);
               this.method4456(var27, var2, var3);
            }

            if (!this.method4426(var31)) {
               this.method4483(var31, var27, var2, var3);
            }
         }

         int var28 = this.method4438(var1, var2, var3);
         this.field2006[var28] = var24;
         this.field2004[var28] = this.field2004[var28] | 1024;
      } else {
         SceneTilePaint var25 = new SceneTilePaint(var15, var16, var17, var18, var6, var20, var8 == var7 && var7 == var9 && var10 == var7);

         for (int var29 = var1; var29 >= 0; var29--) {
            int var32 = this.method4438(var29, var2, var3);
            if (this.field1991[var29][var2][var3] == null) {
               this.field1991[var29][var2][var3] = new rl10(this, var32);
               this.method4456(var29, var2, var3);
            }

            if (!this.method4426(var32)) {
               this.method4483(var32, var29, var2, var3);
            }
         }

         int var30 = this.method4438(var1, var2, var3);
         this.field2005[var30] = var25;
         this.field2004[var30] = this.field2004[var30] | 256 | (var25.field2284 * -665850825 != 12345678 ? 512 : 0);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIII)Z")
   @ObfuscatedName("kb")
   static boolean method4349(int var0, int var1, int var2, int var3, int var4) {
      int var5 = classBF.method1000(var3, (byte)-87);
      int var7 = classLL.method7162(var3, 1222866659);
      int var8 = classBF.method1000(var4, (byte)-30);
      int var10 = classLL.method7162(var4, 1222866659);
      int var11 = var2 * var8 + var0 * var10 >> 16;
      int var12 = var2 * var10 - var0 * var8 >> 16;
      int var13 = var1 * var5 + var12 * var7 >> 16;
      int var14 = var1 * var7 - var12 * var5 >> 16;
      if (var13 >= 50 && var13 <= classFQ.method4948()) {
         int var15 = field2137 + var11 * 128 / var13;
         int var16 = field2127 + var14 * 128 / var13;
         return var15 >= field2135 && var15 <= field2133 && var16 >= field2134 && var16 <= field2129;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("dy")
   boolean method4344(int var1, int var2, int var3, int var4) {
      if (!method4325(var1, var2, var3, var4)) {
         return method4319(var1, var2, var3, var4);
      } else {
         method4330(var1, var2, var3, var4, false);
         int var5 = var1 * 256 + field2064;
         int var6 = var2 * 1024;
         int var7 = (var3 - this.field2049 - this.field2051 - 1) * 128;
         int var8 = (var4 - this.field2049 - this.field2052 - 1) * 128;

         for (int var9 = -field2128; var9 <= field2124; var9 += 128) {
            if (method4350(var7, field2070[var1] + var9, var8, var5, var6)) {
               return method4322(var1, var2, var3, var4, true);
            }
         }

         return method4322(var1, var2, var3, var4, false);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIILee;IJZ)Z")
   @ObfuscatedName("bj")
   public boolean method4505(int var1, int var2, int var3, int var4, int var5, Renderable var6, int var7, long var8, boolean var10) {
      if (var6 == null) {
         return true;
      } else {
         int var11 = var2 - var5;
         int var12 = var3 - var5;
         int var13 = var5 + var2;
         int var14 = var3 + var5;
         if (var10) {
            if (var7 > 640 && var7 < 1408) {
               var14 += 128;
            }

            if (var7 > 1152 && var7 < 1920) {
               var13 += 128;
            }

            if (var7 > 1664 || var7 < 384) {
               var12 -= 128;
            }

            if (var7 > 128 && var7 < 896) {
               var11 -= 128;
            }
         }

         var11 >>= 7;
         var12 >>= 7;
         var13 >>= 7;
         var14 >>= 7;
         return this.method4545(var1, var11, var12, var13 - var11 + 1, var14 - var12 + 1, var2, var3, var4, var6, var7, true, var8, 0);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIILee;IZJI)Z")
   @ObfuscatedName("bo")
   boolean method4424(
      int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Renderable var9, int var10, boolean var11, long var12, int var14
   ) {
      int var15 = var2 + this.field2146;
      int var16 = var3 + this.field2146;
      if (var11) {
         boolean var17 = classOE.field4841.getCallbacks().draw(var9, false);
         if (!var17) {
            if ((var6 & 127) == 64 && (var7 & 127) == 64 && var9 instanceof Actor && ((Actor)var9).field1439 * 670857619 == 1) {
               int var31 = var6 >> 7;
               int var33 = var7 >> 7;
               this.field2002.field1701[var31][var33] = -1;
            }

            return false;
         }
      }

      for (int var25 = var15; var25 < var15 + var4; var25++) {
         for (int var18 = var16; var18 < var16 + var5; var18++) {
            if (var25 < 0 || var18 < 0 || var25 >= this.field2054 || var18 >= this.field2030) {
               return false;
            }

            int var19 = this.method4438(var1, var25, var18);
            if (this.method4426(var19) && this.field2011[var19] >= 5) {
               return false;
            }
         }
      }

      GameObject var26;
      if (var11) {
         var26 = (GameObject)field2074.pollLast();
         if (var26 == null) {
            var26 = new GameObject();
         }

         var26.field2195 = 0 * 132222543;
         var26.field2189 = 0 * -1227421221;
      } else {
         var26 = new GameObject();
      }

      var26.method4592(var12);
      var26.method4593(var14);
      var26.method4587(var1);
      var26.method4589(var6);
      var26.method4590(var7);
      var26.method4586(var8);
      var26.method4594(var9);
      var26.method4588(var10);
      var26.field2186 = var2 * 250676621;
      var26.field2192 = var3 * -1058819281;
      var26.field2184 = (var2 + var4 - 1) * 1384467041;
      var26.field2190 = (var3 + var5 - 1) * 1191492373;
      if (var11) {
         rl17 var27 = this.field2017[var15 >> 3][var16 >> 3];
         var27.field5704.add(var26);
      } else if (var9 instanceof DynamicObject) {
         rl17 var28 = this.field2017[var15 >> 3][var16 >> 3];
         var28.field5703.add(var26);
         this.method4348((DynamicObject)var9);
      }

      this.method4513(var9, var10, var6, var8, var7, var2, var3);

      for (int var29 = var15; var29 < var15 + var4; var29++) {
         for (int var32 = var16; var32 < var16 + var5; var32++) {
            byte var20 = 0;
            if (var29 > var15) {
               var20 |= 1;
            }

            if (var29 < var15 + var4 - 1) {
               var20 |= 4;
            }

            if (var32 > var16) {
               var20 |= 8;
            }

            if (var32 < var16 + var5 - 1) {
               var20 |= 2;
            }

            for (int var21 = var1; var21 >= 0; var21--) {
               int var22 = this.method4438(var21, var29, var32);
               if (this.field1991[var21][var29][var32] == null) {
                  this.field1991[var21][var29][var32] = new rl10(this, var22);
                  this.method4456(var21, var29, var32);
               }

               if (!this.method4426(var22)) {
                  this.method4483(var22, var21, var29, var32);
               }
            }

            int var34 = this.method4438(var1, var29, var32);
            byte var35 = this.field2011[var34];
            this.field2012[var34 * 5 + var35] = var26;
            this.field2013[var34 * 5 + var35] = (byte)var20;
            this.field2014[var34] = (byte)(this.field2014[var34] | var20);
            this.field2011[var34]++;
         }
      }

      if (!var11 && this.field2152) {
         assert !(var9 instanceof Actor);

         assert !(var9 instanceof Projectile);

         assert !(var9 instanceof GraphicsObject);

         assert !(var9 instanceof Scene);

         client.field1028.trace("Game object spawn: {}", var26.getId());
         GameObjectSpawned var30 = new GameObjectSpawned();
         var30.setTile(this.field1991[var1][var15][var16]);
         var30.setGameObject(var26);
         classOE.field4841.getCallbacks().post(var30);
         int var24 = this.method4438(var1, var2, var3);
         this.field2015[var24] = this.field2015[var24] | 1080863910568919040L;
      }

      if (this.field2152 && !var11 && client.field1117 != null) {
         client.field1117.invalidateZone(this, var15 >> 3, var16 >> 3);
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;JI)V")
   @ObfuscatedName("fb")
   public void method4193(int var1, int var2, int var3, int var4, Renderable var5, long var6, int var8) {
      if (var5 != null) {
         GroundObject var9 = new GroundObject();
         var9.field1814 = var5;
         var9.field1804 = var2 * -1794546048 + 1250210624;
         var9.field1806 = var3 * -2036886656 + -1018443328;
         var9.field1805 = var4 * 476279895;
         var9.field1813 = var6 * -8879266543907161229L;
         GroundObject.method3963(var9, var8, (byte)2);
         if (this.field2056[var1][var2][var3] == null) {
            this.field2056[var1][var2][var3] = new Tile(var1, var2, var3);
         }

         this.field2056[var1][var2][var3].field1965 = var9;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lew;)Z")
   @ObfuscatedName("ba")
   boolean method4219(Tile var1) {
      if (var1.field1954 * -299736461 != this.field2063) {
         var1.field1954 = this.field2063 * -1699605829;
         var1.field1956 = var1.field1961 * -980607445;
         if (this.field2048 >= this.field2055.length) {
            this.field2055 = Arrays.copyOf(this.field2055, this.field2055.length * 2);
         }

         this.field2055[this.field2048++] = var1;
         return true;
      } else {
         return false;
      }
   }

   public int[][][] getTileHeights() {
      return this.field2058;
   }

   @ObfuscatedSignature(descriptor = "(Lee;Z)Z")
   @ObfuscatedName("zb")
   public static boolean method4478(Renderable var0, boolean var1) {
      return classOE.field4841.getCallbacks().draw(var0, var1);
   }

   @ObfuscatedSignature(descriptor = "(Lfb;)V")
   @ObfuscatedName("bb")
   void method4475(GameObject var1) {
      for (int var2 = var1.field2186 * 1138017605; var2 <= var1.field2184 * 304142753; var2++) {
         int var3 = var2 + this.field2146;

         for (int var4 = var1.field2192 * -555977777; var4 <= var1.field2190 * -544424899; var4++) {
            int var5 = var4 + this.field2146;
            int var6 = this.method4438(var1.field2185 * 568825275, var3, var5);
            if (this.method4426(var6)) {
               byte var7 = this.field2011[var6];

               for (int var8 = 0; var8 < var7; var8++) {
                  GameObject var9 = this.field2012[var6 * 5 + var8];
                  if (var9 == var1) {
                     var7 = --this.field2011[var6];

                     for (int var10 = var8; var10 < var7; var10++) {
                        this.field2012[var6 * 5 + var10] = this.field2012[var6 * 5 + var10 + 1];
                        this.field2013[var6 * 5 + var10] = this.field2013[var6 * 5 + var10 + 1];
                     }

                     this.field2012[var6 * 5 + var7] = null;
                     break;
                  }
               }

               this.field2004[var6] = this.field2004[var6] & -129;
               this.field2014[var6] = 0;

               for (int var12 = 0; var12 < this.field2011[var6]; var12++) {
                  this.field2014[var6] = (byte)(this.field2014[var6] | this.field2013[var6 * 5 + var12]);
               }
            }
         }
      }

      this.method4449(var1);
   }

   @ObfuscatedSignature(descriptor = "([IIIIII)V")
   @ObfuscatedName("co")
   public void method4428(int[] var1, int var2, int var3, int var4, int var5, int var6) {
      var5 += this.field2146;
      var6 += this.field2146;
      int var7 = this.method4438(var4, var5, var6);
      if (this.method4426(var7)) {
         int var8 = this.field2004[var7];
         if ((var8 & 256) != 0) {
            SceneTilePaint var9 = this.field2005[var7];
            int var10 = var9.field2287 * 1843846065;
            if (var10 != 0) {
               for (int var11 = 0; var11 < 4; var11++) {
                  var1[var2] = var10;
                  var1[var2 + 1] = var10;
                  var1[var2 + 2] = var10;
                  var1[var2 + 3] = var10;
                  var2 += var3;
               }
            }
         } else if ((var8 & 1024) != 0) {
            SceneTileModel var20 = this.field2006[var7];
            if (var20 != null) {
               int var21 = var20.field2318;
               int var22 = var20.field2324;
               int var12 = var20.field2331;
               int var13 = var20.field2319;
               int[] var14 = field2082[var21];
               int[] var15 = field2078[var22];
               int var16 = 0;
               if (var12 != 0) {
                  for (int var17 = 0; var17 < 4; var17++) {
                     var1[var2] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var1[var2 + 1] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var1[var2 + 2] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var1[var2 + 3] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var2 += var3;
                  }
               } else {
                  for (int var29 = 0; var29 < 4; var29++) {
                     if (var14[var15[var16++]] != 0) {
                        var1[var2] = var13;
                     }

                     if (var14[var15[var16++]] != 0) {
                        var1[var2 + 1] = var13;
                     }

                     if (var14[var15[var16++]] != 0) {
                        var1[var2 + 2] = var13;
                     }

                     if (var14[var15[var16++]] != 0) {
                        var1[var2 + 3] = var13;
                     }

                     var2 += var3;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("fc")
   public static void method4522(int var0, int var1) {
      method4337(var0, var1);
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;Lee;IIJI)V")
   @ObfuscatedName("fx")
   public void method4523(int var1, int var2, int var3, int var4, Renderable var5, Renderable var6, int var7, int var8, long var9, int var11) {
      this.method4473(var1, var2, var3, var4, var5, var6, var7, var8, var9, var11);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("by")
   public void method4463(int var1, int var2, int var3) {
      var2 += this.field2146;
      var3 += this.field2146;
      int var4 = this.method4438(var1, var2, var3);
      if (this.method4426(var4) && (this.field2004[var4] & 16384) != 0) {
         WallObject var5 = this.field2007[var4];
         this.field2007[var4] = null;
         this.field2004[var4] = this.field2004[var4] & -16385;
         if (var5.field2247 instanceof DynamicObject || var5.field2246 instanceof DynamicObject) {
            rl17 var6 = this.field2017[var2 >> 3][var3 >> 3];
            var6.field5703.remove(var5);
         }

         if (this.field2152) {
            WallObjectDespawned var9 = new WallObjectDespawned();
            var9.setTile(this.field1991[var1][var2][var3]);
            var9.setWallObject(var5);
            classOE.field4841.getCallbacks().post(var9);
         }

         if (this.field2152 && client.field1117 != null) {
            client.field1117.invalidateZone(this, var2 >> 3, var3 >> 3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bq")
   public void method4465(int var1, int var2, int var3) {
      var2 += this.field2146;
      var3 += this.field2146;
      int var4 = this.method4438(var1, var2, var3);
      if (this.method4426(var4) && (this.field2004[var4] & 32768) != 0) {
         DecorativeObject var5 = this.field2008[var4];
         this.field2008[var4] = null;
         this.field2004[var4] = this.field2004[var4] & -32769;
         if (var5.field2277 instanceof DynamicObject || var5.field2276 instanceof DynamicObject) {
            rl17 var6 = this.field2017[var2 >> 3][var3 >> 3];
            var6.field5703.remove(var5);
         }

         if (this.field2152) {
            DecorativeObjectDespawned var9 = new DecorativeObjectDespawned();
            var9.setTile(this.field1991[var1][var2][var3]);
            var9.setDecorativeObject(var5);
            classOE.field4841.getCallbacks().post(var9);
         }

         if (this.field2152 && client.field1117 != null) {
            client.field1117.invalidateZone(this, var2 >> 3, var3 >> 3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bf")
   public void method4464(int var1, int var2, int var3) {
      int var4 = var2 + this.field2146;
      int var5 = var3 + this.field2146;
      int var6 = this.method4438(var1, var4, var5);
      if (this.method4426(var6)) {
         byte var7 = this.field2011[var6];

         for (int var8 = 0; var8 < var7; var8++) {
            GameObject var9 = this.field2012[var6 * 5 + var8];
            long var10 = var9.field2183 * 1745853678706228013L;
            int var12 = (int)(var10 >> 16 & 7L);
            if (var12 == 2 && var2 == var9.field2186 * 1138017605 && var3 == var9.field2192 * -555977777) {
               this.removeGameObject(var9);
               return;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Lex;)V")
   @ObfuscatedName("tg")
   public static void method4282(Scene var0, classEX var1) {
      if (var1 != var0.field2050) {
         var0.field2050 = var1;
         method4333(field2070, field2128, field2124, field2133, field2129);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ey")
   public int method4150() {
      return this.field2062;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("hy")
   public void method4482(int var1, int var2) {
      assert (var2 & 15) == var2;

      this.field2004[var1] = this.field2004[var1] & -15728641;
      this.field2004[var1] = this.field2004[var1] | var2 << 20;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("nx")
   public void method4484(int var1) {
      boolean var2 = this.field2020.add(var1);
      if (var2 && (client.field1021 & 16) != 0 && this.field1987 != null) {
         int[] var3 = (int[])this.field1987.get(var1);
         if (var3 != null) {
            for (int var7 : var3) {
               this.method4484(var7);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Lfe;")
   @ObfuscatedName("bl")
   public WallObject method4515(int var1, int var2, int var3) {
      var2 += this.field2146;
      var3 += this.field2146;
      int var4 = this.method4438(var1, var2, var3);
      return this.method4426(var4) ? this.field2007[var4] : null;
   }

   @ObfuscatedSignature(descriptor = "(III)Lfi;")
   @ObfuscatedName("bv")
   public DecorativeObject method4498(int var1, int var2, int var3) {
      var2 += this.field2146;
      var3 += this.field2146;
      int var4 = this.method4438(var1, var2, var3);
      return this.method4426(var4) ? this.field2008[var4] : null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("mw")
   void method4396() {
      int var1 = this.field2061[this.field2025];
      classEP[] var2 = this.field2037[this.field2025];
      this.field2107 = 0;

      for (int var3 = 0; var3 < var1; var3++) {
         classEP var4 = var2[var3];
         if (var4.field1816 * -71966871 == 1) {
            int var14 = var4.field1819 * 1062499071 - this.field2112 + this.field2049;
            if (var14 >= 0 && var14 <= this.field2049 + this.field2049) {
               int var16 = var4.field1828 * -728017947 - this.field2122 + this.field2049;
               if (var16 < 0) {
                  var16 = 0;
               }

               int var18 = var4.field1831 * -899751495 - this.field2122 + this.field2049;
               if (var18 > this.field2049 + this.field2049) {
                  var18 = this.field2049 + this.field2049;
               }

               boolean var20 = false;

               while (var16 <= var18) {
                  if (this.method4340(var14, var16++)) {
                     var20 = true;
                     break;
                  }
               }

               if (var20) {
                  int var22 = this.field2116 - var4.field1815 * -996295335;
                  if (var22 > 32) {
                     var4.field1830 = -307181543;
                  } else {
                     if (var22 >= -32) {
                        continue;
                     }

                     var4.field1830 = -614363086;
                     var22 = -var22;
                  }

                  var4.field1826 = (var4.field1818 * -832449137 - this.field2114 << 8) / var22 * -1015514901;
                  var4.field1820 = (var4.field1827 * -371025921 - this.field2114 << 8) / var22 * 306530981;
                  var4.field1822 = (var4.field1829 * 318014867 - this.field2117 << 8) / var22 * -1741242211;
                  var4.field1824 = (var4.field1821 * -971837087 - this.field2117 << 8) / var22 * -2051350499;
                  this.field2046[this.field2107++] = var4;
               }
            }
         } else if (var4.field1816 * -71966871 == 2) {
            int var13 = var4.field1828 * -728017947 - this.field2122 + this.field2049;
            if (var13 >= 0 && var13 <= this.field2049 + this.field2049) {
               int var15 = var4.field1819 * 1062499071 - this.field2112 + this.field2049;
               if (var15 < 0) {
                  var15 = 0;
               }

               int var17 = var4.field1825 * -753584253 - this.field2112 + this.field2049;
               if (var17 > this.field2049 + this.field2049) {
                  var17 = this.field2049 + this.field2049;
               }

               boolean var19 = false;

               while (var15 <= var17) {
                  if (this.method4340(var15++, var13)) {
                     var19 = true;
                     break;
                  }
               }

               if (var19) {
                  int var21 = this.field2114 - var4.field1818 * -832449137;
                  if (var21 > 32) {
                     var4.field1830 = -921544629;
                  } else {
                     if (var21 >= -32) {
                        continue;
                     }

                     var4.field1830 = -1228726172;
                     var21 = -var21;
                  }

                  var4.field1823 = (var4.field1815 * -996295335 - this.field2116 << 8) / var21 * -1640466283;
                  var4.field1817 = (var4.field1832 * 1506923105 - this.field2116 << 8) / var21 * 323343539;
                  var4.field1822 = (var4.field1829 * 318014867 - this.field2117 << 8) / var21 * -1741242211;
                  var4.field1824 = (var4.field1821 * -971837087 - this.field2117 << 8) / var21 * -2051350499;
                  this.field2046[this.field2107++] = var4;
               }
            }
         } else if (var4.field1816 * -71966871 == 4) {
            int var5 = var4.field1829 * 318014867 - this.field2117;
            if (var5 > 128) {
               int var6 = var4.field1828 * -728017947 - this.field2122 + this.field2049;
               if (var6 < 0) {
                  var6 = 0;
               }

               int var7 = var4.field1831 * -899751495 - this.field2122 + this.field2049;
               if (var7 > this.field2049 + this.field2049) {
                  var7 = this.field2049 + this.field2049;
               }

               if (var6 <= var7) {
                  int var8 = var4.field1819 * 1062499071 - this.field2112 + this.field2049;
                  if (var8 < 0) {
                     var8 = 0;
                  }

                  int var9 = var4.field1825 * -753584253 - this.field2112 + this.field2049;
                  if (var9 > this.field2049 + this.field2049) {
                     var9 = this.field2049 + this.field2049;
                  }

                  boolean var10 = false;

                  label190:
                  for (int var11 = var8; var11 <= var9; var11++) {
                     for (int var12 = var6; var12 <= var7; var12++) {
                        if (this.method4340(var11, var12)) {
                           var10 = true;
                           break label190;
                        }
                     }
                  }

                  if (var10) {
                     var4.field1830 = -1535907715;
                     var4.field1823 = (var4.field1815 * -996295335 - this.field2116 << 8) / var5 * -1640466283;
                     var4.field1817 = (var4.field1832 * 1506923105 - this.field2116 << 8) / var5 * 323343539;
                     var4.field1826 = (var4.field1818 * -832449137 - this.field2114 << 8) / var5 * -1015514901;
                     var4.field1820 = (var4.field1827 * -371025921 - this.field2114 << 8) / var5 * 306530981;
                     this.field2046[this.field2107++] = var4;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;IIIILee;JLee;Lee;I)V")
   @ObfuscatedName("nm")
   public static void method4444(Scene var0, int var1, int var2, int var3, int var4, Renderable var5, long var6, Renderable var8, Renderable var9, int var10) {
      if (var0 == null) {
         var0.method4445(var1, var1, var1, var1, var5, var6, var5, var5, var1);
      } else {
         int var11 = var2 + var0.field2146;
         int var12 = var3 + var0.field2146;
         ItemLayer var13 = new ItemLayer();
         var13.field1843 = var5;
         var13.field1838 = (var2 * 128 + 64) * -1385855465;
         var13.field1837 = (var3 * 128 + 64) * 2114521817;
         var13.field1839 = var4 * -673866411;
         var13.field1842 = var6;
         var13.field1844 = var8;
         var13.field1845 = var9;
         var13.field1836 = var10 * 1484214011;
         var13.method3977(var1);
         int var14 = var0.method4438(var1, var11, var12);
         if (!var0.method4426(var14)) {
            var0.method4483(var14, var1, var11, var12);
         }

         if (var0.field1991[var1][var11][var12] == null) {
            var0.field1991[var1][var11][var12] = new rl10(var0, var14);
            var0.method4456(var1, var11, var12);
         }

         rl17 var15 = var0.field2017[var11 >> 3][var12 >> 3];
         if ((var0.field2004[var14] & 12288) != 0) {
            var15.field5703.remove(var0.field2010[var14]);
         }

         var0.field2010[var14] = var13;
         var0.field2004[var14] = var0.field2004[var14] & -12289;
         var0.field2004[var14] = var0.field2004[var14] | (var10 != 0 ? 8192 : 4096);
         var15.field5703.add(var13);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Leo;")
   @ObfuscatedName("bz")
   public GroundObject method4468(int var1, int var2, int var3) {
      var2 += this.field2146;
      var3 += this.field2146;
      int var4 = this.method4438(var1, var2, var3);
      return this.method4426(var4) ? this.field2009[var4] : null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIII)Z")
   @ObfuscatedName("ik")
   static boolean method4290(BitSet var0, int var1, int var2, int var3, int var4) {
      return var0.get(var1 * field2136 + var2 * field2131 + var3 * field2126 + var4);
   }

   public byte getOverrideLuminance() {
      return this.field2033.field2300;
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;IJ)Z")
   @ObfuscatedName("gk")
   public boolean method4217(int var1, int var2, int var3, int var4, Renderable var5, int var6, long var7) {
      if (var5 == null) {
         return false;
      } else {
         int var9 = var2 / 128;
         int var10 = var3 / 128;
         if (var9 >= 0 && var10 >= 0 && var9 < this.field2054 && var10 < this.field2030) {
            Tile var11 = this.field2056[var1][var9][var10];
            if (var11 != null && (var11.field1953 == null || var11.field1953.size() != 50)) {
               if (var11.field1953 == null) {
                  var11.field1953 = new ArrayList();
               }

               GameObject var12 = new GameObject();
               var12.field2183 = var7 * -4819202259227702107L;
               var12.field2182 = 0;
               var12.field2185 = var1 * 1383386583;
               var12.field2193 = var2 * 1127193675;
               var12.field2194 = var3 * 1768277495;
               var12.field2188 = var4 * -1294023757;
               var12.field2198 = var5;
               var12.field2187 = var6 * -107181990;
               var12.field2186 = var9 * -515564213;
               var12.field2192 = var10 * -1058819281;
               var12.field2184 = var9 * -1397775053;
               var12.field2190 = var10 * 1872428737;
               var11.field1953.add(var12);
               this.method4219(var11);
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("hj")
   public long method4413(int var1, int var2, int var3) {
      var2 += this.field2146;
      var3 += this.field2146;
      int var4 = this.method4438(var1, var2, var3);
      return this.method4426(var4) && this.field2009[var4] != null ? this.field2009[var4].field1813 * 5132338419383099835L : 0L;
   }

   @ObfuscatedSignature(descriptor = "(Lew;)V")
   @ObfuscatedName("bi")
   void method4228(Tile var1) {
      var1.field1952 = 0;

      for (int var2 = 0; var2 < var1.field1961 * -389646721; var2++) {
         var1.field1952 = (var1.field1952 * -785027619 | var1.field1951[var2]) * -1095478667;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("vb")
   public void method4462(int var1, int var2) {
      assert (var2 & 0xFF) == var2;

      this.field2004[var1] = this.field2004[var1] & 16777215;
      this.field2004[var1] = this.field2004[var1] | var2 << 24;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("cl")
   public void method4466(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2057; var4++) {
         for (int var5 = 0; var5 < this.field2054; var5++) {
            for (int var6 = 0; var6 < this.field2030; var6++) {
               int var7 = this.method4438(var4, var5, var6);
               if (this.method4426(var7)) {
                  WallObject var8 = this.field2007[var7];
                  if (var8 != null && var8.field2247 instanceof ModelData) {
                     ModelData var9 = (ModelData)var8.field2247;
                     this.method4537(var9, var4, var5, var6, 1, 1);
                     if (var8.field2246 instanceof net.runelite.api.ModelData) {
                        ModelData var10 = (ModelData)var8.field2246;
                        this.method4537(var10, var4, var5, var6, 1, 1);
                        ModelData.method4082(var9, var10, 0, 0, 0, false);
                        var8.field2246 = var10.method4093(var10.field1876, var10.field1877, var1, var2, var3);
                     }

                     var8.field2247 = var9.method4093(var9.field1876, var9.field1877, var1, var2, var3);
                  }

                  byte var13 = this.field2011[var7];

                  for (int var14 = 0; var14 < var13; var14++) {
                     GameObject var11 = this.field2012[var7 * 5 + var14];
                     if (var11 != null && var11.field2198 instanceof ModelData) {
                        ModelData var12 = (ModelData)var11.field2198;
                        this.method4537(
                           var12,
                           var4,
                           var5,
                           var6,
                           var11.field2184 * 304142753 - var11.field2186 * 1138017605 + 1,
                           var11.field2190 * -544424899 - var11.field2192 * -555977777 + 1
                        );
                        var11.field2198 = var12.method4093(var12.field1876, var12.field1877, var1, var2, var3);
                     }
                  }

                  GroundObject var15 = this.field2009[var7];
                  if (var15 != null && var15.field1814 instanceof ModelData) {
                     ModelData var16 = (ModelData)var15.field1814;
                     this.method4539(var16, var4, var5, var6);
                     var15.field1814 = var16.method4093(var16.field1876, var16.field1877, var1, var2, var3);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;IIIII)V")
   @ObfuscatedName("ix")
   void method4509(ModelData var1, int var2, int var3, int var4, int var5, int var6) {
      boolean var7 = true;
      int var8 = var3;
      int var9 = var3 + var5;
      int var10 = var4 - 1;
      int var11 = var4 + var6;

      for (int var12 = var2; var12 <= var2 + 1; var12++) {
         if (var12 != this.field2057) {
            for (int var13 = var8; var13 <= var9; var13++) {
               if (var13 >= 0 && var13 < this.field2054) {
                  for (int var14 = var10; var14 <= var11; var14++) {
                     if (var14 >= 0 && var14 < this.field2030 && (!var7 || var13 >= var9 || var14 >= var11 || var14 < var4 && var3 != var13)) {
                        int var15 = this.method4438(var12, var13, var14);
                        if (this.method4426(var15)) {
                           int var16 = this.method4415(var12, var13, var14) - this.method4415(var2, var3, var4);
                           WallObject var17 = this.field2007[var15];
                           if (var17 != null) {
                              if (var17.field2247 instanceof ModelData) {
                                 ModelData var18 = (ModelData)var17.field2247;
                                 ModelData.method4082(var1, var18, (var13 - var3) * 128 + (1 - var5) * 64, var16, (1 - var6) * 64 + (var14 - var4) * 128, var7);
                              }

                              if (var17.field2246 instanceof ModelData) {
                                 ModelData var24 = (ModelData)var17.field2246;
                                 ModelData.method4082(var1, var24, (var13 - var3) * 128 + (1 - var5) * 64, var16, (1 - var6) * 64 + (var14 - var4) * 128, var7);
                              }
                           }

                           byte var25 = this.field2011[var15];

                           for (int var19 = 0; var19 < var25; var19++) {
                              GameObject var20 = this.field2012[var15 * 5 + var19];
                              if (var20 != null && var20.field2198 instanceof ModelData) {
                                 ModelData var21 = (ModelData)var20.field2198;
                                 int var22 = var20.field2184 * 304142753 - var20.field2186 * 1138017605 + 1;
                                 int var23 = var20.field2190 * -544424899 - var20.field2192 * -555977777 + 1;
                                 ModelData.method4082(
                                    var1,
                                    var21,
                                    (var20.field2186 * 1138017605 + this.field2146 - var3) * 128 + (var22 - var5) * 64,
                                    var16,
                                    (var20.field2192 * -555977777 + this.field2146 - var4) * 128 + (var23 - var6) * 64,
                                    var7
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            }

            var8--;
            var7 = false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()[[[Lrl10;")
   @ObfuscatedName("ck")
   public rl10[][][] method4458() {
      return this.field2158;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("cs")
   int method4269(int var1, int var2, int var3, int var4, int var5, int var6) {
      return method4277(this, var1, var2, var3) - method4277(this, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(Lex;)V")
   @ObfuscatedName("im")
   public void method4283(classEX var1) {
      if (var1 != this.field2050) {
         this.field2050 = var1;
         method4333(field2070, field2128, field2124, field2133, field2129);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;III)Lfe;")
   @ObfuscatedName("uv")
   public static WallObject method4245(Scene var0, int var1, int var2, int var3) {
      Tile var4 = var0.field2056[var1][var2][var3];
      return var4 == null ? null : var4.field1968;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ck")
   public void method4281(int var1) {
      this.field2049 = classEK.method3928(25, 90, var1, (byte)17);
      method4286(this.field2049);
      classFQ.method4955(this.field2049);
      method4333(field2070, field2128, field2124, field2133, field2129);
   }

   @ObfuscatedSignature(descriptor = "(IIIIILee;IJZ)V")
   @ObfuscatedName("hg")
   public void method4524(int var1, int var2, int var3, int var4, int var5, Renderable var6, int var7, long var8, boolean var10) {
      this.method4505(var1, var2, var3, var4, var5, var6, var7, var8, var10);
   }

   @ObfuscatedSignature(descriptor = "(Lex;)V")
   @ObfuscatedName("ca")
   public void method4284(classEX var1) {
      if (var1 != this.field2050) {
         this.field2050 = var1;
         method4333(field2070, field2128, field2124, field2133, field2129);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;Z)V")
   @ObfuscatedName("zp")
   public static void method4354(Scene var0, boolean var1) {
      if (var0 == null) {
         var0.method4356(var1);
      }

      if (!method4364(var0) || var1) {
         var0.field2041 = true;
         var0.field2031 = var1;
         var0.field2034 = -1;
         var0.field2035 = -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cc")
   public static void method4286(int var0) {
      int var1 = (field2069 - 1) * field2067 * (var0 + var0 + 1) * (var0 + var0 + 1);
      field2148 = new BitSet(var1);
      field2151 = new BitSet(var1);
      field2136 = field2067 * (var0 + var0 + 1) * (var0 + var0 + 1);
      field2131 = (var0 + var0 + 1) * (var0 + var0 + 1);
      field2126 = var0 + var0 + 1;
      int var2 = field2069 * field2067 * (var0 + var0 + 3) * (var0 + var0 + 3);
      field2149 = new BitSet(var2);
      field2150 = new BitSet(var2);
      field2130 = field2067 * (var0 + var0 + 3) * (var0 + var0 + 3);
      field2132 = (var0 + var0 + 3) * (var0 + var0 + 3);
      field2125 = var0 + var0 + 3;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIII)Z")
   @ObfuscatedName("cf")
   static boolean method4291(BitSet var0, int var1, int var2, int var3, int var4) {
      return var0.get(var1 * field2136 + var2 * field2131 + var3 * field2126 + var4);
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;Lee;IIIIIIJI)V")
   @ObfuscatedName("yd")
   public void method4525(
      int var1,
      int var2,
      int var3,
      int var4,
      Renderable var5,
      Renderable var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      long var13,
      int var15
   ) {
      method4495(this, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var15);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIIIZ)Z")
   @ObfuscatedName("cn")
   static boolean method4293(BitSet var0, int var1, int var2, int var3, int var4, boolean var5) {
      var0.set(var1 * field2136 + var2 * field2131 + var3 * field2126 + var4, var5);
      return var5;
   }

   @ObfuscatedSignature(descriptor = "(IIIIILjava/util/Set;)Z")
   @ObfuscatedName("ow")
   public boolean method4416(int var1, int var2, int var3, int var4, int var5, Set var6) {
      boolean var7 = false;
      int var8 = var5 + ((this.field2155[1][var1 + this.field2146][var2 + this.field2146] & 2) >> 1) & 3;

      for (int var9 = var1; var9 <= var3; var9++) {
         for (int var10 = var2; var10 <= var4; var10++) {
            var7 |= (this.field2155[var8][var9 + this.field2146][var10 + this.field2146] & 8) != 0;
         }
      }

      var5 = var7 ? 0 : var5;
      boolean var14 = this.field1986 != 0 && this.field2060;
      if (var5 >= this.field2038 && (var14 || var5 <= this.field2025)) {
         if (var5 > this.field2002.field1708 * -483624883 && !var6.isEmpty()) {
            for (int var15 = var1; var15 <= var3; var15++) {
               for (int var11 = var2; var11 <= var4; var11++) {
                  int var12 = this.field1992[var5][var15 + this.field2146][var11 + this.field2146];
                  if (var6.contains(var12)) {
                     return false;
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("rc")
   public boolean method4419(int var1, int var2, int var3) {
      return (this.field2155[var1][var2][var3] & 4) != 0;
   }

   public byte getOverrideSaturation() {
      return this.field2033.field2299;
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("ch")
   static boolean method4296(int var0, int var1, int var2, int var3) {
      return method4291(field2148, var0, var1, var2, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIIII)Z")
   @ObfuscatedName("eu")
   public static boolean method4175(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var1 < var2 && var1 < var3 && var1 < var4) {
         return false;
      } else if (var1 > var2 && var1 > var3 && var1 > var4) {
         return false;
      } else if (var0 < var5 && var0 < var6 && var0 < var7) {
         return false;
      } else if (var0 > var5 && var0 > var6 && var0 > var7) {
         return false;
      } else {
         int var8 = (var1 - var2) * (var6 - var5) - (var0 - var5) * (var3 - var2);
         int var9 = (var1 - var3) * (var7 - var6) - (var0 - var6) * (var4 - var3);
         int var10 = (var1 - var4) * (var5 - var7) - (var0 - var7) * (var2 - var4);
         if (var8 == 0) {
            if (var9 != 0) {
               return var9 < 0 ? var10 <= 0 : var10 >= 0;
            } else {
               return true;
            }
         } else {
            return var8 < 0 ? var9 <= 0 && var10 <= 0 : var9 >= 0 && var10 >= 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("cg")
   static boolean method4302(int var0, int var1, int var2, int var3) {
      return method4291(field2151, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("cw")
   static boolean method4307(int var0, int var1, int var2, int var3, boolean var4) {
      return method4293(field2151, var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIII)Z")
   @ObfuscatedName("cj")
   static boolean method4311(BitSet var0, int var1, int var2, int var3, int var4) {
      return var0.get(var1 * field2130 + var2 * field2132 + var3 * field2125 + var4);
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lfv;Lee;IIIIJ)V")
   @ObfuscatedName("er")
   public void method4421(Projection var1, Projection var2, Renderable var3, int var4, int var5, int var6, int var7, long var8) {
      if (var3 instanceof Model) {
         Model var10 = (Model)var3;
         var10.calculateBoundsCylinder();
         client.method1631(var1, var2, var10, var4, var5, var6, var7, var8);
      } else if (var3 instanceof DynamicObject) {
         int var14 = client.field1021 >> 7 & 15;
         DynamicObject var11 = (DynamicObject)var3;
         if (var11.field1681 || var14 > 0) {
            Model var12 = var11.method3723(rl21.field5726);
            if (var12 != null) {
               var12.calculateBoundsCylinder();
               int var13 = var11.getAnimationHeightOffset();

               assert !var11.field1681 || var13 == 0;

               client.method1631(var1, var2, var12, var4, var5, var6 - var13, var7, var8);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;II)Z")
   @ObfuscatedName("md")
   public static boolean method4339(Scene var0, int var1, int var2) {
      if (var0 == null) {
         var0.method4190(var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1, var1);
      }

      if (!method4302(field2140, field2138, var1, var2)) {
         return method4296(field2140, field2138, var1, var2);
      } else {
         method4307(field2140, field2138, var1, var2, false);
         boolean var3 = false;

         for (int var4 = -1; var4 <= 1; var4++) {
            for (int var5 = -1; var5 <= 1; var5++) {
               if (var0.method4346(field2140, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (var0.method4346(field2140, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (var0.method4346(field2140 + 1, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (var0.method4346(field2140 + 1, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }
            }
         }

         return method4299(field2140, field2138, var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;IJ)Z")
   @ObfuscatedName("bu")
   public boolean method4499(int var1, int var2, int var3, int var4, Renderable var5, int var6, long var7) {
      if (var5 == null) {
         return false;
      } else {
         int var9 = (var2 >> 7) + this.field2146;
         int var10 = (var3 >> 7) + this.field2146;
         if (var9 >= 0 && var10 >= 0 && var9 < this.field2054 && var10 < this.field2030) {
            GameObject var11 = (GameObject)field2074.pollLast();
            if (var11 == null) {
               var11 = new GameObject();
            }

            var11.field2183 = var7 * -4819202259227702107L;
            var11.field2182 = 0 * -1989916103;
            var11.field2185 = var1 * -1965758605;
            var11.field2193 = var2 * -1872653325;
            var11.field2194 = var3 * 1768277495;
            var11.field2188 = var4 * -1294023757;
            var11.field2198 = var5;
            var11.field2187 = var6 * -610160131;
            var11.field2186 = (var2 >> 7) * 250676621;
            var11.field2192 = (var3 >> 7) * -1058819281;
            var11.field2184 = (var2 >> 7) * 1384467041;
            var11.field2190 = (var3 >> 7) * 1191492373;
            var11.field2195 = 0 * 132222543;
            var11.field2189 = 0 * -1227421221;
            int var12 = this.method4438(var1, var9, var10);
            if (!this.method4426(var12)) {
               this.method4483(var12, var1, var9, var10);
            }

            if (this.field1991[var1][var9][var10] == null) {
               this.field1991[var1][var9][var10] = new rl10(this, var12);
               this.method4456(var1, var9, var10);
            }

            this.field2004[var12] = this.field2004[var12] | 128;
            rl17 var13 = this.field2017[var9 >> 3][var10 >> 3];
            var13.field5704.add(var11);
            return true;
         } else {
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ot")
   public void method4417() {
      for (int var1 = 0; var1 < this.field2054 >> 3; var1++) {
         for (int var2 = 0; var2 < this.field2030 >> 3; var2++) {
            rl17 var3 = this.field2017[var1][var2];
            boolean var4 = false;

            for (TileObject var6 : var3.field5703) {
               if (var6 instanceof GameObject) {
                  GameObject var10 = (GameObject)var6;
                  var4 |= this.method4500(
                     var10.field2198,
                     var10.getOrientation(),
                     var10.getX(),
                     var10.getZ(),
                     var10.getY(),
                     var10.field2186 * 1138017605,
                     var10.field2192 * -555977777
                  );
               } else if (var6 instanceof WallObject) {
                  WallObject var9 = (WallObject)var6;
                  var4 |= this.method4500(var9.field2247, 0, var9.getX(), var9.getZ(), var9.getY(), var9.getX() >> 7, var9.getY() >> 7)
                     || this.method4500(var9.field2246, 0, var9.getX(), var9.getZ(), var9.getY(), var9.getX() >> 7, var9.getY() >> 7);
               } else if (!(var6 instanceof DecorativeObject)) {
                  if (var6 instanceof GroundObject) {
                     GroundObject var8 = (GroundObject)var6;
                     var4 |= this.method4500(var8.field1814, 0, var8.getX(), var8.getZ(), var8.getY(), var8.getX() >> 7, var8.getY() >> 7);
                  }
               } else {
                  DecorativeObject var7 = (DecorativeObject)var6;
                  var4 |= this.method4500(var7.field2277, 0, var7.getX(), var7.getZ(), var7.getY(), var7.getX() >> 7, var7.getY() >> 7)
                     || this.method4500(var7.field2276, 0, var7.getX(), var7.getZ(), var7.getY(), var7.getX() >> 7, var7.getY() >> 7);
               }
            }

            if (var4 && client.field1117 != null) {
               client.field1117.invalidateZone(this, var3.field5710, var3.field5705);
            }
         }
      }
   }

   public int getRoofRemovalMode() {
      return this.field1986;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("jf")
   static boolean method4303(int var0, int var1, int var2, int var3) {
      return method4291(field2151, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(Lee;IIIIII)Z")
   @ObfuscatedName("nl")
   public boolean method4500(Renderable var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var1 instanceof DynamicObject) {
         DynamicObject var8 = (DynamicObject)var1;
         if (var8.field1663 != -1 && var8.field1666 != classLB.field4088[var8.field1663]) {
            var8.method3727();
            this.method4513(var8, var2, var3, var4, var5, var6, var7);
            return var8.field1681;
         }
      }

      return false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIIIZ)Z")
   @ObfuscatedName("ip")
   static boolean method4294(BitSet var0, int var1, int var2, int var3, int var4, boolean var5) {
      var0.set(var1 * field2136 + var2 * field2131 + var3 * field2126 + var4, var5);
      return var5;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("qg")
   public Model method4526() {
      return this.field2147;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("sx")
   public void method4501() {
      for (int var1 = 0; var1 < this.field2057; var1++) {
         for (int var2 = this.field2146; var2 < this.field2054 - this.field2146; var2++) {
            for (int var3 = this.field2146; var3 < this.field2030 - this.field2146; var3++) {
               rl10 var4 = this.field2158[var1][var2 - this.field2146][var3 - this.field2146];
               if (var4 != null) {
                  this.method4504(var4);
                  if (var4.method10081() != null) {
                     this.method4504(var4.method10081());
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("cm")
   static boolean method4325(int var0, int var1, int var2, int var3) {
      return method4311(field2150, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;JI)V")
   @ObfuscatedName("ai")
   public void method4527(int var1, int var2, int var3, int var4, Renderable var5, long var6, int var8) {
      this.method4470(var1, var2, var3, var4, var5, var6, var8);
   }

   public byte[][][] getTileShapes() {
      return this.field2156;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIIIIIIIIIII)V")
   @ObfuscatedName("db")
   public void method4528(
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20
   ) {
      this.method4479(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20);
   }

   @ObfuscatedSignature(descriptor = "(ILrl17;Lfx;)V")
   @ObfuscatedName("uo")
   public void method4431(int var1, rl17 var2, Model var3) {
      var3.calculateBoundsCylinder();
      int var4 = var1 - var3.field1741 * -1256242689;
      int var5 = var1 + var3.field2490;
      if (var4 < var2.field5706) {
         var2.field5706 = var4;
      }

      if (var5 > var2.field5708) {
         var2.field5708 = var5;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIIIZ)Z")
   @ObfuscatedName("jt")
   static boolean method4314(BitSet var0, int var1, int var2, int var3, int var4, boolean var5) {
      var0.set(var1 * field2130 + var2 * field2132 + var3 * field2125 + var4, var5);
      return var5;
   }

   @ObfuscatedSignature(descriptor = "([IIIII)V")
   @ObfuscatedName("dd")
   public static void method4333(int[] var0, int var1, int var2, int var3, int var4) {
      field2070 = var0;
      field2128 = var1;
      field2124 = var2;
      field2135 = 0;
      field2134 = 0;
      field2133 = var3;
      field2129 = var4;
      field2137 = var3 / 2;
      field2127 = var4 / 2;
      field2151.set(0, field2151.size());
      field2150.set(0, field2150.size());
   }

   @ObfuscatedSignature(descriptor = "(Lfv;ZZ)V")
   @ObfuscatedName("li")
   void method4379(Projection var1, boolean var2, boolean var3) {
      this.field2063++;
      this.field2059.method4843(classFH.field2255.field2213);
      classFH.field2255.field2213.method4843(this.field2033);
      if (var3) {
         this.field2115 = !var2 && this.field2050 != classEX.field1975 ? this.field2119 : this.field2112;
         this.field2113 = !var2 && this.field2050 != classEX.field1975 ? this.field2123 : this.field2122;
         if (this.field2060) {
            this.field2118 = this.field2115 - this.field2049;
            if (this.field2118 < 0) {
               this.field2118 = 0;
            }

            this.field2111 = this.field2113 - this.field2049;
            if (this.field2111 < 0) {
               this.field2111 = 0;
            }

            this.field2121 = this.field2115 + this.field2049;
            if (this.field2121 > this.field2054) {
               this.field2121 = this.field2054;
            }

            this.field2120 = this.field2113 + this.field2049;
            if (this.field2120 > this.field2030) {
               this.field2120 = this.field2030;
            }
         } else {
            this.field2118 = 0;
            this.field2111 = 0;
            this.field2121 = this.field2054;
            this.field2120 = this.field2030;
            this.field2115 = this.field2054 / 2;
            this.field2113 = this.field2030 / 2;
            this.field2049 = Math.max(this.field2115, this.field2113);
         }

         this.field2051 = this.field2112 - this.field2115;
         this.field2052 = this.field2122 - this.field2113;
         if (this.field2060) {
            this.method4397();
         }
      }

      if (classFH.field2255.field2215 == 0) {
         this.method4380(var1, this.field2060);
      } else {
         this.method4384(var1, this.field2060);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("dx")
   boolean method4340(int var1, int var2) {
      if (!method4302(field2140, field2138, var1, var2)) {
         return method4296(field2140, field2138, var1, var2);
      } else {
         method4307(field2140, field2138, var1, var2, false);
         boolean var3 = false;

         for (int var4 = -1; var4 <= 1; var4++) {
            for (int var5 = -1; var5 <= 1; var5++) {
               if (this.method4346(field2140, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140 + 1, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140 + 1, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }
            }
         }

         return method4299(field2140, field2138, var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Z")
   @ObfuscatedName("di")
   static boolean method4350(int var0, int var1, int var2, int var3, int var4) {
      int var5 = classBF.method1000(var3, (byte)-45);
      int var7 = classLL.method7162(var3, 1222866659);
      int var8 = classBF.method1000(var4, (byte)-113);
      int var10 = classLL.method7162(var4, 1222866659);
      int var11 = var2 * var8 + var0 * var10 >> 16;
      int var12 = var2 * var10 - var0 * var8 >> 16;
      int var13 = var1 * var5 + var12 * var7 >> 16;
      int var14 = var1 * var7 - var12 * var5 >> 16;
      if (var13 >= 50 && var13 <= classFQ.method4948()) {
         int var15 = field2137 + var11 * 128 / var13;
         int var16 = field2127 + var14 * 128 / var13;
         return var15 >= field2135 && var15 <= field2133 && var16 >= field2134 && var16 <= field2129;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lh")
   public void method4370() {
      this.field2034 = -1;
      this.field2031 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;IIIILee;JI)V")
   @ObfuscatedName("pz")
   public static void method4194(Scene var0, int var1, int var2, int var3, int var4, Renderable var5, long var6, int var8) {
      if (var0 == null) {
         var0.method4472(var1);
      }

      if (var5 != null) {
         GroundObject var9 = new GroundObject();
         var9.field1814 = var5;
         var9.field1804 = var2 * -1794546048 + 1250210624;
         var9.field1806 = var3 * -2036886656 + -1018443328;
         var9.field1805 = var4 * 476279895;
         var9.field1813 = var6 * -8879266543907161229L;
         GroundObject.method3963(var9, var8, (byte)2);
         if (var0.field2056[var1][var2][var3] == null) {
            var0.field2056[var1][var2][var3] = new Tile(var1, var2, var3);
         }

         var0.field2056[var1][var2][var3].field1965 = var9;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfb;II)I")
   @ObfuscatedName("gv")
   public int method4529(GameObject var1, int var2, int var3) {
      return this.method4393(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIIIIIIIIIII)V")
   @ObfuscatedName("fi")
   public void method4190(
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20
   ) {
      if (var4 == 0) {
         SceneTilePaint var24 = new SceneTilePaint(var11, var12, var13, var14, -1, var19, false);

         for (int var26 = var1; var26 >= 0; var26--) {
            if (this.field2056[var26][var2][var3] == null) {
               this.field2056[var26][var2][var3] = new Tile(var26, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1970 = var24;
      } else if (var4 != 1) {
         if (var20 == 0) {
            var20 = 1;
         }

         SceneTileModel var23 = new SceneTileModel(
            var4, var5, var6, var2, var3, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20
         );

         for (int var25 = var1; var25 >= 0; var25--) {
            if (this.field2056[var25][var2][var3] == null) {
               this.field2056[var25][var2][var3] = new Tile(var25, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1971 = var23;
      } else {
         SceneTilePaint var21 = new SceneTilePaint(var15, var16, var17, var18, var6, var20, var7 == var8 && var7 == var9 && var7 == var10);

         for (int var22 = var1; var22 >= 0; var22--) {
            if (this.field2056[var22][var2][var3] == null) {
               this.field2056[var22][var2][var3] = new Tile(var22, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1970 = var21;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;IIIILee;JLee;Lee;I)V")
   @ObfuscatedName("gy")
   public static void method4200(Scene var0, int var1, int var2, int var3, int var4, Renderable var5, long var6, Renderable var8, Renderable var9, int var10) {
      if (var0 == null) {
         var0.getOverlayIds();
      }

      ItemLayer var11 = new ItemLayer();
      var11.field1843 = var5;
      var11.field1838 = var2 * -1779547208 + 1499563456;
      var11.field1837 = var3 * 1079728895 + 327127294;
      var11.field1839 = var4 * 1193418273;
      var11.field1842 = var6;
      var11.field1844 = var8;
      var11.field1845 = var9;
      var11.field1836 = var10 * 1484214011;
      if (var0.field2056[var1][var2][var3] == null) {
         var0.field2056[var1][var2][var3] = new Tile(var1, var2, var3);
      }

      var0.field2056[var1][var2][var3].field1966 = var11;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Lfb;II)I")
   @ObfuscatedName("fd")
   public static int method4392(Scene var0, GameObject var1, int var2, int var3) {
      if (var0 == null) {
         return var0.getWorldViewId();
      } else {
         int var4 = var2 - var1.field2186 * -1996012957;
         int var5 = var1.field2184 * -161575791 - var2;
         if (var5 > var4) {
            var4 = var5;
         }

         int var6 = var3 - var1.field2192 * -555977777;
         int var7 = var1.field2190 * 340761146 - var3;
         return var7 > var6 ? var4 + var7 : var4 + var6;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dr")
   public void method4361() {
      this.field2031 = true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dw")
   public void method4371() {
      this.field2034 = -1;
      this.field2031 = false;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("hj")
   public void method4506(int var1, int var2) {
      assert (var2 & 15) == var2;

      this.field2004[var1] = this.field2004[var1] & -983041;
      this.field2004[var1] = this.field2004[var1] | var2 << 16;
   }

   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("hq")
   public long method4254(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      return var4 != null && var4.field1965 != null ? var4.field1965.field1813 * 5132338419383099835L : 0L;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("de")
   public void method4375(int var1) {
      this.field2143 = var1;
      this.field2024 = -1;
      this.field2032 = -1;
      this.field2040 = -1.0F;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("je")
   static boolean method4326(int var0, int var1, int var2, int var3) {
      return method4311(field2150, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;Lee;IIJI)V")
   @ObfuscatedName("br")
   public void method4202(int var1, int var2, int var3, int var4, Renderable var5, Renderable var6, int var7, int var8, long var9, int var11) {
      if (var5 != null || var6 != null) {
         WallObject var12 = new WallObject();
         var12.field2238 = var9 * -2636099314526831141L;
         var12.field2239 = var11 * -752902543;
         var12.field2242 = var2 * -1994411555 + 1685800512;
         var12.field2243 = var3 * -669945546 + -326366588;
         var12.field2240 = var4 * -1658828428;
         var12.field2247 = var5;
         var12.field2246 = var6;
         var12.field2244 = var7 * -484103910;
         var12.field2241 = var8 * 760556117;

         for (int var13 = var1; var13 >= 0; var13--) {
            if (this.field2056[var13][var2][var3] == null) {
               this.field2056[var13][var2][var3] = new Tile(var13, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1968 = var12;
      }
   }

   public short[][][] getOverlayIds() {
      return this.field2159;
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Z)V")
   @ObfuscatedName("dn")
   void method4384(Projection var1, boolean var2) {
      this.field2039 = 0;

      for (int var3 = this.field2038; var3 < this.field2057; var3++) {
         Tile[][] var4 = this.field2056[var3];

         for (int var5 = this.field2118; var5 < this.field2121; var5++) {
            for (int var6 = this.field2111; var6 < this.field2120; var6++) {
               Tile var7 = var4[var5][var6];
               if (var7 != null) {
                  if (var7.field1962 * 1390661409 <= this.field2025
                     && (
                        !var2
                           || this.method4340(var5 - this.field2112 + this.field2051 + this.field2049, var6 - this.field2122 + this.field2052 + this.field2049)
                           || this.field2058[var3][var5][var6] - this.field2117 >= 2000
                     )) {
                     var7.field1972 = true;
                     var7.field1974 = true;
                     var7.field1973 = var7.field1961 * -389646721 > 0 || var7.method4122((short)4076);
                     this.field2039++;
                  } else {
                     var7.field1972 = false;
                     var7.field1974 = false;
                     var7.field1960 = 0;
                  }
               }
            }
         }
      }

      int var14 = Math.abs(this.field2051);
      int var15 = Math.abs(this.field2052);

      for (int var16 = this.field2038; var16 < this.field2057; var16++) {
         Tile[][] var18 = this.field2056[var16];

         for (int var20 = -(this.field2049 + var14); var20 <= 0; var20++) {
            int var8 = this.field2112 + var20;
            int var9 = this.field2112 - var20;
            if (var8 >= this.field2118 || var9 < this.field2121) {
               for (int var10 = -(this.field2049 + var15); var10 <= 0; var10++) {
                  int var11 = this.field2122 + var10;
                  int var12 = this.field2122 - var10;
                  if (var8 >= this.field2118 && var8 < this.field2121) {
                     if (var11 >= this.field2111 && var11 < this.field2120) {
                        Tile var13 = var18[var8][var11];
                        if (var13 != null && var13.field1972) {
                           this.method4412(var1, var13, true);
                        }
                     }

                     if (var12 >= this.field2111 && var12 < this.field2120) {
                        Tile var27 = var18[var8][var12];
                        if (var27 != null && var27.field1972) {
                           this.method4412(var1, var27, true);
                        }
                     }
                  }

                  if (var9 >= this.field2118 && var9 < this.field2121) {
                     if (var11 >= this.field2111 && var11 < this.field2120) {
                        Tile var28 = var18[var9][var11];
                        if (var28 != null && var28.field1972) {
                           this.method4412(var1, var28, true);
                        }
                     }

                     if (var12 >= this.field2111 && var12 < this.field2120) {
                        Tile var29 = var18[var9][var12];
                        if (var29 != null && var29.field1972) {
                           this.method4412(var1, var29, true);
                        }
                     }
                  }

                  if (this.field2039 == 0) {
                     this.method4368();
                     classFH.field2255.field2213.method4843(this.field2059);
                     return;
                  }
               }
            }
         }
      }

      for (int var17 = this.field2038; var17 < this.field2057; var17++) {
         Tile[][] var19 = this.field2056[var17];

         for (int var21 = -(this.field2049 + var14); var21 <= 0; var21++) {
            int var22 = this.field2115 + var21;
            int var23 = this.field2115 - var21;
            if (var22 >= this.field2118 || var23 < this.field2121) {
               for (int var24 = -(this.field2049 + var15); var24 <= 0; var24++) {
                  int var25 = this.field2113 + var24;
                  int var26 = this.field2113 - var24;
                  if (var22 >= this.field2118 && var22 < this.field2121) {
                     if (var25 >= this.field2111 && var25 < this.field2120) {
                        Tile var30 = var19[var22][var25];
                        if (var30 != null && var30.field1972) {
                           this.method4412(var1, var30, false);
                        }
                     }

                     if (var26 >= this.field2111 && var26 < this.field2120) {
                        Tile var31 = var19[var22][var26];
                        if (var31 != null && var31.field1972) {
                           this.method4412(var1, var31, false);
                        }
                     }
                  }

                  if (var23 >= this.field2118 && var23 < this.field2121) {
                     if (var25 >= this.field2111 && var25 < this.field2120) {
                        Tile var32 = var19[var23][var25];
                        if (var32 != null && var32.field1972) {
                           this.method4412(var1, var32, false);
                        }
                     }

                     if (var26 >= this.field2111 && var26 < this.field2120) {
                        Tile var33 = var19[var23][var26];
                        if (var33 != null && var33.field1972) {
                           this.method4412(var1, var33, false);
                        }
                     }
                  }

                  if (this.field2039 == 0) {
                     this.method4368();
                     classFH.field2255.field2213.method4843(this.field2059);
                     return;
                  }
               }
            }
         }
      }

      classFH.field2255.field2213.method4843(this.field2059);
      this.method4368();
   }

   @ObfuscatedSignature(descriptor = "(III)Lfe;")
   @ObfuscatedName("uk")
   public WallObject method4530(int var1, int var2, int var3) {
      return this.method4515(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lew;Z)V")
   @ObfuscatedName("do")
   void method4412(Projection var1, Tile var2, boolean var3) {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dq")
   void method4397() {
      int var1 = this.field2061[this.field2025];
      classEP[] var2 = this.field2037[this.field2025];
      this.field2107 = 0;

      for (int var3 = 0; var3 < var1; var3++) {
         classEP var4 = var2[var3];
         if (var4.field1816 * -71966871 == 1) {
            int var14 = var4.field1819 * 1062499071 - this.field2112 + this.field2049;
            if (var14 >= 0 && var14 <= this.field2049 + this.field2049) {
               int var16 = var4.field1828 * -728017947 - this.field2122 + this.field2049;
               if (var16 < 0) {
                  var16 = 0;
               }

               int var18 = var4.field1831 * -899751495 - this.field2122 + this.field2049;
               if (var18 > this.field2049 + this.field2049) {
                  var18 = this.field2049 + this.field2049;
               }

               boolean var20 = false;

               while (var16 <= var18) {
                  if (this.method4340(var14, var16++)) {
                     var20 = true;
                     break;
                  }
               }

               if (var20) {
                  int var22 = this.field2116 - var4.field1815 * -996295335;
                  if (var22 > 32) {
                     var4.field1830 = -307181543;
                  } else {
                     if (var22 >= -32) {
                        continue;
                     }

                     var4.field1830 = -614363086;
                     var22 = -var22;
                  }

                  var4.field1826 = (var4.field1818 * -832449137 - this.field2114 << 8) / var22 * -1015514901;
                  var4.field1820 = (var4.field1827 * -371025921 - this.field2114 << 8) / var22 * 306530981;
                  var4.field1822 = (var4.field1829 * 318014867 - this.field2117 << 8) / var22 * -1741242211;
                  var4.field1824 = (var4.field1821 * -971837087 - this.field2117 << 8) / var22 * -2051350499;
                  this.field2046[this.field2107++] = var4;
               }
            }
         } else if (var4.field1816 * -71966871 == 2) {
            int var13 = var4.field1828 * -728017947 - this.field2122 + this.field2049;
            if (var13 >= 0 && var13 <= this.field2049 + this.field2049) {
               int var15 = var4.field1819 * 1062499071 - this.field2112 + this.field2049;
               if (var15 < 0) {
                  var15 = 0;
               }

               int var17 = var4.field1825 * -753584253 - this.field2112 + this.field2049;
               if (var17 > this.field2049 + this.field2049) {
                  var17 = this.field2049 + this.field2049;
               }

               boolean var19 = false;

               while (var15 <= var17) {
                  if (this.method4340(var15++, var13)) {
                     var19 = true;
                     break;
                  }
               }

               if (var19) {
                  int var21 = this.field2114 - var4.field1818 * -832449137;
                  if (var21 > 32) {
                     var4.field1830 = -921544629;
                  } else {
                     if (var21 >= -32) {
                        continue;
                     }

                     var4.field1830 = -1228726172;
                     var21 = -var21;
                  }

                  var4.field1823 = (var4.field1815 * -996295335 - this.field2116 << 8) / var21 * -1640466283;
                  var4.field1817 = (var4.field1832 * 1506923105 - this.field2116 << 8) / var21 * 323343539;
                  var4.field1822 = (var4.field1829 * 318014867 - this.field2117 << 8) / var21 * -1741242211;
                  var4.field1824 = (var4.field1821 * -971837087 - this.field2117 << 8) / var21 * -2051350499;
                  this.field2046[this.field2107++] = var4;
               }
            }
         } else if (var4.field1816 * -71966871 == 4) {
            int var5 = var4.field1829 * 318014867 - this.field2117;
            if (var5 > 128) {
               int var6 = var4.field1828 * -728017947 - this.field2122 + this.field2049;
               if (var6 < 0) {
                  var6 = 0;
               }

               int var7 = var4.field1831 * -899751495 - this.field2122 + this.field2049;
               if (var7 > this.field2049 + this.field2049) {
                  var7 = this.field2049 + this.field2049;
               }

               if (var6 <= var7) {
                  int var8 = var4.field1819 * 1062499071 - this.field2112 + this.field2049;
                  if (var8 < 0) {
                     var8 = 0;
                  }

                  int var9 = var4.field1825 * -753584253 - this.field2112 + this.field2049;
                  if (var9 > this.field2049 + this.field2049) {
                     var9 = this.field2049 + this.field2049;
                  }

                  boolean var10 = false;

                  label189:
                  for (int var11 = var8; var11 <= var9; var11++) {
                     for (int var12 = var6; var12 <= var7; var12++) {
                        if (this.method4340(var11, var12)) {
                           var10 = true;
                           break label189;
                        }
                     }
                  }

                  if (var10) {
                     var4.field1830 = -1535907715;
                     var4.field1823 = (var4.field1815 * -996295335 - this.field2116 << 8) / var5 * -1640466283;
                     var4.field1817 = (var4.field1832 * 1506923105 - this.field2116 << 8) / var5 * 323343539;
                     var4.field1826 = (var4.field1818 * -832449137 - this.field2114 << 8) / var5 * -1015514901;
                     var4.field1820 = (var4.field1827 * -371025921 - this.field2114 << 8) / var5 * 306530981;
                     this.field2046[this.field2107++] = var4;
                  }
               }
            }
         }
      }
   }

   public int getBaseY() {
      return this.field2109;
   }

   public void buildRoofs() {
      this.field1992 = new int[this.field2057][this.field2054][this.field2030];
      int var1 = 1;
      this.field1987 = new HashMap();
      Set[] var2 = new Set[]{new HashSet(), new HashSet(), new HashSet()};

      for (int var3 = this.field2057 - 1; var3 >= 0; var3--) {
         for (int var4 = 0; var4 < this.field2030; var4++) {
            for (int var5 = 0; var5 < this.field2054; var5++) {
               rl10 var6 = this.field1991[var3][var5][var4];
               if (var6 != null && this.field1992[var3][var5][var4] == 0 && this.method4419(var3, var5, var4)) {
                  this.method4422(var6, var1++, var2);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZ)V")
   @ObfuscatedName("qw")
   public void method4508(boolean var1, boolean var2) {
      this.field2063++;
      this.field2059.method4861(classFH.field2255.field2213);
      classFH.field2255.field2213.method4861(this.field2033);
      if (var1) {
         DrawCallbacks var3 = client.field1117;
         if (var3 != null && this.field2060) {
            classGP.field2648 = 0;
            var3.drawScene(this.field2084, this.field2086, this.field2091, this.field2087, this.field2089, this.field2025);
         }

         int var4 = classOE.field4841.isGpu() ? field2141 : this.field2049;
         int var5 = !var2 && this.field2050 != classEX.field1975 ? this.field2119 : this.field2112;
         int var6 = !var2 && this.field2050 != classEX.field1975 ? this.field2123 : this.field2122;
         if (this.field2060) {
            this.field2118 = Math.max(var5 - var4, this.field2110 + this.field2146);
            this.field2111 = Math.max(var6 - var4, this.field2145 + this.field2146);
            this.field2121 = Math.min(var5 + var4, this.field2142 + this.field2146);
            this.field2120 = Math.min(var6 + var4, this.field2139 + this.field2146);
         } else {
            this.field2118 = 0;
            this.field2111 = 0;
            this.field2121 = this.field2054;
            this.field2120 = this.field2030;
            var5 = this.field2054 / 2;
            var6 = this.field2030 / 2;
            this.field2049 = Math.max(var5, var6);
         }

         this.field2051 = this.field2112 - var5;
         this.field2052 = this.field2122 - var6;
         this.field2039 = 0;
         this.method4493();
         boolean var7 = classOE.field4841.isMenuOpen();
         if (!var7) {
            this.field1988 = this.field2110;
            this.field1989 = this.field2145;
         }

         if ((client.field1021 & 16) != 0) {
            boolean var8 = this.field1986 != 0 && this.field2060;
            int var9 = var8 ? this.field2057 - 1 : this.field2025;
            client.field1117
               .preSceneDraw(
                  this,
                  this.field2022,
                  this.field2084,
                  this.field2086,
                  this.field2091,
                  this.field2087,
                  this.field2089,
                  this.field2038,
                  this.field2002.field1708 * -483624883,
                  var9,
                  this.field2020
               );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;IIIIILee;IJIIII)Z")
   @ObfuscatedName("pu")
   public static boolean method4212(
      Scene var0, int var1, int var2, int var3, int var4, int var5, Renderable var6, int var7, long var8, int var10, int var11, int var12, int var13
   ) {
      if (var0 == null) {
         var0.method4215(var1, var1, var1, var1, var1, var6, var1, var8, var1, var1, var1, var1);
      }

      return var6 == null ? true : var0.method4424(var1, var10, var11, var12 - var10 + 1, var13 - var11 + 1, var2, var3, var4, var6, var7, true, var8, 0);
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("kv")
   boolean method4403(int var1, int var2, int var3, int var4) {
      if (!this.method4399(var1, var2, var3)) {
         return false;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         return this.method4411(var5 + 1, this.field2058[var1][var2][var3] - var4, var6 + 1)
            && this.method4411(var5 + 128 - 1, this.field2058[var1][var2 + 1][var3] - var4, var6 + 1)
            && this.method4411(var5 + 128 - 1, this.field2058[var1][var2 + 1][var3 + 1] - var4, var6 + 128 - 1)
            && this.method4411(var5 + 1, this.field2058[var1][var2][var3 + 1] - var4, var6 + 128 - 1);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("hz")
   public static void method4489(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      Scene var7 = classCQ.field1218.field1687;
      int var8 = var1 + var7.field2146;
      int var9 = var2 + var7.field2146;
      rl17 var10 = var7.field2017[var8 >> 3][var9 >> 3];
      if (var5 == 0) {
         WallObject var11 = var7.method4530(var0, var1, var2);
         if (var11 != null && (var11.field2247 instanceof DynamicObject || var11.field2246 instanceof DynamicObject) && !var10.field5703.contains(var11)) {
            var10.field5703.add(var11);
         }
      } else if (var5 == 1) {
         DecorativeObject var12 = var7.method4542(var0, var1, var2);
         if (var12 != null && (var12.field2277 instanceof DynamicObject || var12.field2276 instanceof DynamicObject) && !var10.field5703.contains(var12)) {
            var10.field5703.add(var12);
         }
      } else if (var5 == 2) {
         GameObject var13 = var7.method4531(var0, var1, var2);
         if (var13 != null && var13.field2198 instanceof DynamicObject && !var10.field5703.contains(var13)) {
            var10.field5703.add(var13);
         }
      } else if (var5 == 3) {
         GroundObject var14 = var7.method4535(var0, var1, var2);
         if (var14 != null && var14.field1814 instanceof DynamicObject && !var10.field5703.contains(var14)) {
            var10.field5703.add(var14);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hp")
   public void method4243(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         var4.field1965 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("df")
   boolean method4399(int var1, int var2, int var3) {
      int var4 = this.field2029[var1][var2][var3];
      if (var4 == -this.field2063) {
         return false;
      } else if (var4 == this.field2063) {
         return true;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         if (this.method4411(var5 + 1, this.field2058[var1][var2][var3], var6 + 1)
            && this.method4411(var5 + 128 - 1, this.field2058[var1][var2 + 1][var3], var6 + 1)
            && this.method4411(var5 + 128 - 1, this.field2058[var1][var2 + 1][var3 + 1], var6 + 128 - 1)
            && this.method4411(var5 + 1, this.field2058[var1][var2][var3 + 1], var6 + 128 - 1)) {
            this.field2029[var1][var2][var3] = this.field2063;
            return true;
         } else {
            this.field2029[var1][var2][var3] = -this.field2063;
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("mb")
   boolean method4401(int var1, int var2, int var3, int var4) {
      if (!this.method4399(var1, var2, var3)) {
         return false;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         int var7 = this.field2058[var1][var2][var3] - 1;
         int var8 = var7 - 120;
         int var9 = var7 - 230;
         int var10 = var7 - 238;
         if (var4 < 16) {
            if (var4 == 1) {
               if (var5 > this.field2116) {
                  if (!this.method4411(var5, var7, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5, var7, var6 + 128)) {
                     return false;
                  }
               }

               if (var1 > 0) {
                  if (!this.method4411(var5, var8, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5, var8, var6 + 128)) {
                     return false;
                  }
               }

               if (!this.method4411(var5, var9, var6)) {
                  return false;
               }

               if (!this.method4411(var5, var9, var6 + 128)) {
                  return false;
               }

               return true;
            }

            if (var4 == 2) {
               if (var6 < this.field2114) {
                  if (!this.method4411(var5, var7, var6 + 128)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var7, var6 + 128)) {
                     return false;
                  }
               }

               if (var1 > 0) {
                  if (!this.method4411(var5, var8, var6 + 128)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var8, var6 + 128)) {
                     return false;
                  }
               }

               if (!this.method4411(var5, var9, var6 + 128)) {
                  return false;
               }

               if (!this.method4411(var5 + 128, var9, var6 + 128)) {
                  return false;
               }

               return true;
            }

            if (var4 == 4) {
               if (var5 < this.field2116) {
                  if (!this.method4411(var5 + 128, var7, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var7, var6 + 128)) {
                     return false;
                  }
               }

               if (var1 > 0) {
                  if (!this.method4411(var5 + 128, var8, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var8, var6 + 128)) {
                     return false;
                  }
               }

               if (!this.method4411(var5 + 128, var9, var6)) {
                  return false;
               }

               if (!this.method4411(var5 + 128, var9, var6 + 128)) {
                  return false;
               }

               return true;
            }

            if (var4 == 8) {
               if (var6 > this.field2114) {
                  if (!this.method4411(var5, var7, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var7, var6)) {
                     return false;
                  }
               }

               if (var1 > 0) {
                  if (!this.method4411(var5, var8, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var8, var6)) {
                     return false;
                  }
               }

               if (!this.method4411(var5, var9, var6)) {
                  return false;
               }

               if (!this.method4411(var5 + 128, var9, var6)) {
                  return false;
               }

               return true;
            }
         }

         if (!this.method4411(var5 + 64, var10, var6 + 64)) {
            return false;
         } else if (var4 == 16) {
            return this.method4411(var5, var9, var6 + 128);
         } else if (var4 == 32) {
            return this.method4411(var5 + 128, var9, var6 + 128);
         } else if (var4 == 64) {
            return this.method4411(var5 + 128, var9, var6);
         } else {
            return var4 == 128 ? this.method4411(var5, var9, var6) : true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;)Z")
   @ObfuscatedName("kw")
   public static boolean method4364(Scene var0) {
      return var0 == null ? var0.method4366() : var0.field2031 && var0.field2034 != -1;
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("ds")
   boolean method4404(int var1, int var2, int var3, int var4) {
      if (!this.method4399(var1, var2, var3)) {
         return false;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         return this.method4411(var5 + 1, this.field2058[var1][var2][var3] - var4, var6 + 1)
            && this.method4411(var5 + 128 - 1, this.field2058[var1][var2 + 1][var3] - var4, var6 + 1)
            && this.method4411(var5 + 128 - 1, this.field2058[var1][var2 + 1][var3 + 1] - var4, var6 + 128 - 1)
            && this.method4411(var5 + 1, this.field2058[var1][var2][var3 + 1] - var4, var6 + 128 - 1);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)Z")
   @ObfuscatedName("da")
   boolean method4406(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var2 == var3 && var4 == var5) {
         if (!this.method4399(var1, var2, var4)) {
            return false;
         } else {
            int var13 = var2 << 7;
            int var15 = var4 << 7;
            return this.method4411(var13 + 1, this.field2058[var1][var2][var4] - var6, var15 + 1)
               && this.method4411(var13 + 128 - 1, this.field2058[var1][var2 + 1][var4] - var6, var15 + 1)
               && this.method4411(var13 + 128 - 1, this.field2058[var1][var2 + 1][var4 + 1] - var6, var15 + 128 - 1)
               && this.method4411(var13 + 1, this.field2058[var1][var2][var4 + 1] - var6, var15 + 128 - 1);
         }
      } else {
         for (int var7 = var2; var7 <= var3; var7++) {
            for (int var8 = var4; var8 <= var5; var8++) {
               if (this.field2029[var1][var7][var8] == -this.field2063) {
                  return false;
               }
            }
         }

         int var12 = (var2 << 7) + 1;
         int var14 = (var4 << 7) + 2;
         int var9 = this.field2058[var1][var2][var4] - var6;
         if (!this.method4411(var12, var9, var14)) {
            return false;
         } else {
            int var10 = (var3 << 7) - 1;
            if (!this.method4411(var10, var9, var14)) {
               return false;
            } else {
               int var11 = (var5 << 7) - 1;
               return !this.method4411(var12, var9, var11) ? false : this.method4411(var10, var9, var11);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;IZ)V")
   @ObfuscatedName("jz")
   public void method4497(Projection var1, int var2, boolean var3) {
      this.method4481(var2);

      label669:
      while (true) {
         int var4;
         int var5;
         int var6;
         int var7;
         int var8;
         int var10;
         int var11;
         int var12;
         while (true) {
            var4 = this.method4507();
            if (var4 == this.field2000) {
               return;
            }

            if (this.method4490(var4)) {
               var5 = var4 >> this.field1993 & this.field1995;
               var6 = var4 & this.field1996;
               var7 = var4 >> this.field1994 & 3;
               var8 = this.method4486(var4);
               int var9 = this.method4472(var4);
               var10 = var5 - this.field2146;
               var11 = var6 - this.field2146;
               var12 = this.field2004[var4];
               if (!this.method4485(var4)) {
                  break;
               }

               if (var3) {
                  if (var7 > 0) {
                     int var13 = var4 - this.field1997;
                     if (this.method4426(var13) && this.method4490(var13)) {
                        continue;
                     }
                  }

                  if (var5 <= this.field2112 && var5 > this.field2118) {
                     int var25 = var4 - this.field1998;
                     if (this.method4426(var25) && this.method4490(var25) && (this.method4485(var25) || (this.field2014[var4] & 1) == 0)) {
                        continue;
                     }
                  }

                  if (var5 >= this.field2112 && var5 < this.field2121 - 1) {
                     int var26 = var4 + this.field1998;
                     if (this.method4426(var26) && this.method4490(var26) && (this.method4485(var26) || (this.field2014[var4] & 4) == 0)) {
                        continue;
                     }
                  }

                  if (var6 <= this.field2122 && var6 > this.field2111) {
                     int var27 = var4 - this.field1999;
                     if (this.method4426(var27) && this.method4490(var27) && (this.method4485(var27) || (this.field2014[var4] & 8) == 0)) {
                        continue;
                     }
                  }

                  if (var6 >= this.field2122 && var6 < this.field2120 - 1) {
                     int var28 = var4 + this.field1999;
                     if (this.method4426(var28) && this.method4490(var28) && (this.method4485(var28) || (this.field2014[var4] & 2) == 0)) {
                        continue;
                     }
                  }
               } else {
                  var3 = true;
               }

               var12 = this.field2004[var4] = this.field2004[var4] & -3;
               if ((var12 & 32) != 0) {
                  int var29 = this.method4438(3, var5, var6);
                  int var14 = this.field2004[var29];
                  if ((var14 & 256) != 0) {
                     if (!this.method4536(0, var5, var6)) {
                        var1.method5054(this, this.field2005[var29], 0, 0, var10, var11);
                     }
                  } else if ((var14 & 1024) != 0 && !this.method4536(0, var5, var6)) {
                     var1.method5055(this, this.field2006[var29], 0, var10, var11);
                  }

                  if ((var14 & 16384) != 0) {
                     WallObject var15 = this.field2007[var29];
                     this.method4460(
                        var1,
                        var15.field2247,
                        0,
                        var15.field2242 * -777838807,
                        var15.field2240 * 1315862339,
                        var15.field2243 * 31139801,
                        var15.field2238 * 7159317397185328723L
                     );
                  }

                  for (int var54 = 0; var54 < this.field2011[var29]; var54++) {
                     GameObject var16 = this.field2012[var29 * 5 + var54];
                     if (var16 != null) {
                        this.method4460(
                           var1,
                           var16.field2198,
                           var16.field2187 * 470594389,
                           var16.field2193 * -1405731525,
                           var16.field2188 * 1269801851,
                           var16.field2194 * -1706390585,
                           var16.field2183 * 1745853678706228013L
                        );
                     }
                  }
               }

               boolean var30 = false;
               if ((var12 & 256) != 0) {
                  if (!this.method4536(var8, var5, var6)) {
                     var30 = true;
                     if ((var12 & 512) != 0 || var7 <= this.field2042) {
                        SceneTilePaint var45 = this.field2005[var4];
                        var1.method5054(this, var45, var9, var8, var10, var11);
                     }
                  }
               } else if ((var12 & 1024) != 0 && !this.method4536(var8, var5, var6)) {
                  var30 = true;
                  var1.method5055(this, this.field2006[var4], var9, var10, var11);
               }

               int var46 = 0;
               int var55 = 0;
               int var61 = 0;
               if ((var12 & 49152) != 0) {
                  if (var5 == this.field2112) {
                     var46++;
                  } else if (this.field2112 < var5) {
                     var46 += 2;
                  }

                  if (var6 == this.field2122) {
                     var46 += 3;
                  } else if (this.field2122 > var6) {
                     var46 += 6;
                  }

                  var55 = field2068[var46];
                  int var17 = field2076[var46];
                  this.method4462(var4, var17);
                  var61 = field2065[var46];

                  assert (~(var55 | var17) & 0xFF) == var61;
               }

               if ((var12 & 16384) != 0) {
                  WallObject var68 = this.field2007[var4];
                  if ((var68.field2244 * 1548562339 & var61) != 0) {
                     byte var18;
                     int var19;
                     switch (var68.field2244 * 1548562339) {
                        case 16:
                           var18 = 3;
                           var19 = field2077[var46];
                           break;
                        case 32:
                           var18 = 6;
                           var19 = field2066[var46];
                           break;
                        case 64:
                           var18 = 12;
                           var19 = field2079[var46];
                           break;
                        case 128:
                           var18 = 9;
                           var19 = field2080[var46];
                           break;
                        default:
                           throw new IllegalStateException();
                     }

                     this.method4506(var4, var18);
                     this.method4482(var4, var19);

                     assert (var18 ^ var19) == var18 - var19;

                     var12 = this.field2004[var4] = this.field2004[var4] | 16;
                  }

                  if ((var68.field2244 * 1548562339 & var55) != 0 && !this.method4533(var8, var5, var6, var68.field2244 * 1548562339)) {
                     this.method4460(
                        var1,
                        var68.field2247,
                        0,
                        var68.field2242 * -777838807,
                        var68.field2240 * 1315862339,
                        var68.field2243 * 31139801,
                        var68.field2238 * 7159317397185328723L
                     );
                  }

                  if ((var68.field2241 * -1413337351 & var55) != 0 && !this.method4533(var8, var5, var6, var68.field2241 * -1413337351)) {
                     this.method4460(
                        var1,
                        var68.field2246,
                        0,
                        var68.field2242 * -777838807,
                        var68.field2240 * 1315862339,
                        var68.field2243 * 31139801,
                        var68.field2238 * 7159317397185328723L
                     );
                  }
               }

               if ((var12 & 32768) != 0) {
                  DecorativeObject var69 = this.field2008[var4];
                  if (!this.method4534(var8, var5, var6, var69.field2277.field1741 * -1256242689)) {
                     if ((var69.field2269 * 254285683 & var55) != 0) {
                        this.method4460(
                           var1,
                           var69.field2277,
                           0,
                           var69.field2273 * 1766830743 + var69.field2274 * -404927711,
                           var69.field2271 * 1698079433,
                           var69.field2266 * -361702377 + var69.field2268 * 856605829,
                           var69.field2264 * -3608662145112923099L
                        );
                     } else if (var69.field2269 * 254285683 == 256) {
                        int var77 = var69.field2273 * 1766830743 - this.field2116;
                        int var86 = var69.field2266 * -361702377 - this.field2114;
                        int var20 = var69.field2265 * -819410985;
                        int var21;
                        if (var20 != 1 && var20 != 2) {
                           var21 = var77;
                        } else {
                           var21 = -var77;
                        }

                        int var22;
                        if (var20 != 2 && var20 != 3) {
                           var22 = var86;
                        } else {
                           var22 = -var86;
                        }

                        if (var22 < var21) {
                           this.method4460(
                              var1,
                              var69.field2277,
                              0,
                              var69.field2273 * 1766830743 + var69.field2274 * -404927711,
                              var69.field2271 * 1698079433,
                              var69.field2266 * -361702377 + var69.field2268 * 856605829,
                              var69.field2264 * -3608662145112923099L
                           );
                        } else if (var69.field2276 != null) {
                           this.method4460(
                              var1,
                              var69.field2276,
                              0,
                              var69.field2273 * 1766830743 + var69.field2267 * -1085897215,
                              var69.field2271 * 1698079433,
                              var69.field2266 * -361702377 + var69.field2270 * 513256357,
                              var69.field2264 * -3608662145112923099L
                           );
                        }
                     }
                  }
               }

               if (var30) {
                  if ((this.field2004[var4] & 2048) != 0) {
                     GroundObject var70 = this.field2009[var4];
                     this.method4460(
                        var1,
                        var70.field1814,
                        0,
                        var70.field1804 * 1333275141,
                        var70.field1805 * -276908185,
                        var70.field1806 * 498815127,
                        var70.field1813 * 5132338419383099835L
                     );
                  }

                  if ((this.field2004[var4] & 4096) != 0) {
                     ItemLayer var71 = this.field2010[var4];
                     if (var71.field1844 != null) {
                        this.method4460(
                           var1, var71.field1844, 0, var71.field1838 * -892202585, var71.field1839 * 1186822141, var71.field1837 * -1209739927, var71.field1842
                        );
                     }

                     if (var71.field1845 != null) {
                        this.method4460(
                           var1, var71.field1845, 0, var71.field1838 * -892202585, var71.field1839 * 1186822141, var71.field1837 * -1209739927, var71.field1842
                        );
                     }

                     if (var71.field1843 != null) {
                        this.method4460(
                           var1, var71.field1843, 0, var71.field1838 * -892202585, var71.field1839 * 1186822141, var71.field1837 * -1209739927, var71.field1842
                        );
                     }
                  }
               }

               byte var72 = this.field2014[var4];
               if (var5 < this.field2112 && var5 >= this.field2118 && var5 < this.field2121 - 1 && (var72 & 4) != 0) {
                  int var78 = var4 + this.field1998;
                  if (this.method4426(var78) && this.method4490(var78)) {
                     this.method4481(var78);
                  }
               }

               if (var6 < this.field2122 && var6 >= this.field2111 && var6 < this.field2120 - 1 && (var72 & 2) != 0) {
                  int var79 = var4 + this.field1999;
                  if (this.method4426(var79) && this.method4490(var79)) {
                     this.method4481(var79);
                  }
               }

               if (var5 > this.field2112 && var5 > this.field2118 && var5 < this.field2121 && (var72 & 1) != 0) {
                  int var80 = var4 - this.field1998;
                  if (this.method4426(var80) && this.method4490(var80)) {
                     this.method4481(var80);
                  }
               }

               if (var6 > this.field2122 && var6 > this.field2111 && var6 < this.field2120 && (var72 & 8) != 0) {
                  int var81 = var4 - this.field1999;
                  if (this.method4426(var81) && this.method4490(var81)) {
                     this.method4481(var81);
                  }
               }
               break;
            }
         }

         if ((var12 & 16) != 0) {
            boolean var31 = true;

            for (int var47 = 0; var47 < this.field2011[var4]; var47++) {
               GameObject var56 = this.field2012[var4 * 5 + var47];
               byte var62 = this.field2013[var4 * 5 + var47];
               if (var56.field2195 * 163498159 != this.field2063 && (var62 & this.method4517(var4)) == this.method4427(var4)) {
                  var31 = false;
                  break;
               }
            }

            if (var31) {
               WallObject var48 = this.field2007[var4];
               if (!this.method4533(var8, var5, var6, var48.field2244 * 1548562339)) {
                  this.method4460(
                     var1,
                     var48.field2247,
                     0,
                     var48.field2242 * -777838807,
                     var48.field2240 * 1315862339,
                     var48.field2243 * 31139801,
                     var48.field2238 * 7159317397185328723L
                  );
               }

               var12 = this.field2004[var4] = this.field2004[var4] & -17;
            }
         }

         if ((var12 & 8) != 0) {
            try {
               var12 = this.field2004[var4] = this.field2004[var4] & -9;
               int var32 = 0;

               label614:
               for (int var49 = 0; var49 < this.field2011[var4]; var49++) {
                  GameObject var57 = this.field2012[var4 * 5 + var49];
                  if (var57.field2195 * 163498159 != this.field2063) {
                     for (int var63 = var57.field2186 * 1138017605; var63 <= var57.field2184 * 304142753; var63++) {
                        for (int var73 = var57.field2192 * -555977777; var73 <= var57.field2190 * -544424899; var73++) {
                           int var82 = var63 + this.field2146;
                           int var87 = var73 + this.field2146;
                           int var90 = this.method4438(var7, var82, var87);
                           if (this.method4485(var90)) {
                              var12 = this.field2004[var4] = this.field2004[var4] | 8;
                              continue label614;
                           }

                           if ((this.field2004[var90] & 16) != 0) {
                              byte var93 = 0;
                              if (var63 > var57.field2186 * 1138017605) {
                                 var93 |= 1;
                              }

                              if (var63 < var57.field2184 * 304142753) {
                                 var93 |= 4;
                              }

                              if (var73 > var57.field2192 * -555977777) {
                                 var93 |= 8;
                              }

                              if (var73 < var57.field2190 * -544424899) {
                                 var93 |= 2;
                              }

                              int var96 = this.method4517(var4) ^ this.method4427(var4);
                              if ((var93 & this.method4517(var90)) == var96) {
                                 var12 = this.field2004[var4] = this.field2004[var4] | 8;
                                 continue label614;
                              }
                           }
                        }
                     }

                     assert var32 < 5;

                     this.field2023[var32++] = var57;
                     var57.field2189 = this.method4529(var57, this.field2112 - this.field2146, this.field2122 - this.field2146) * -1227421221;
                  }
               }

               if ((var12 & 128) != 0) {
                  rl17 var50 = this.field2017[var5 >> 3][var6 >> 3];

                  for (int var58 = 0; var58 < var50.field5704.size(); var58++) {
                     GameObject var64 = (GameObject)var50.field5704.get(var58);
                     if (var64.field2198 instanceof ActorSpotAnim
                        && var64.field2195 * 163498159 != this.field2063
                        && var64.field2186 * 1138017605 == var10
                        && var64.field2192 * -555977777 == var11
                        && var32 < 55) {
                        assert var32 < 55;

                        this.field2023[var32++] = var64;
                        var64.field2189 = this.method4529(var64, this.field2112 - this.field2146, this.field2122 - this.field2146) * -1227421221;
                     }
                  }
               }

               while (true) {
                  int var51 = -50;
                  int var59 = -1;

                  for (int var65 = 0; var65 < var32; var65++) {
                     GameObject var74 = this.field2023[var65];
                     if (var74.field2195 * 163498159 != this.field2063) {
                        if (var74.field2189 * 835963475 > var51) {
                           var51 = var74.field2189 * 835963475;
                           var59 = var65;
                        } else if (var51 == var74.field2189 * 835963475) {
                           int var83 = var74.field2193 * -1405731525 - this.field2116;
                           int var88 = var74.field2194 * -1706390585 - this.field2114;
                           int var91 = this.field2023[var59].field2193 * -1405731525 - this.field2116;
                           int var94 = this.field2023[var59].field2194 * -1706390585 - this.field2114;
                           if (var83 * var83 + var88 * var88 > var91 * var91 + var94 * var94) {
                              var59 = var65;
                           }
                        }
                     }
                  }

                  if (var59 == -1) {
                     if ((var12 & 8) != 0) {
                        continue label669;
                     }
                     break;
                  }

                  GameObject var66 = this.field2023[var59];
                  var66.field2195 = this.field2063 * 132222543;
                  this.method4460(
                     var1,
                     var66.field2198,
                     var66.field2187 * 470594389,
                     var66.field2193 * -1405731525,
                     var66.field2188 * 1269801851,
                     var66.field2194 * -1706390585,
                     var66.field2183 * 1745853678706228013L
                  );

                  for (int var75 = var66.field2186 * 1138017605; var75 <= var66.field2184 * 304142753; var75++) {
                     for (int var84 = var66.field2192 * -555977777; var84 <= var66.field2190 * -544424899; var84++) {
                        int var89 = var75 + this.field2146;
                        int var92 = var84 + this.field2146;
                        int var95 = this.method4438(var7, var89, var92);
                        if ((this.field2004[var95] & 16) != 0) {
                           this.method4481(var95);
                        } else if ((var89 != var5 || var92 != var6) && this.method4490(var95)) {
                           this.method4481(var95);
                        }
                     }
                  }
               }
            } catch (Exception var23) {
               client.field1028.debug("exception drawing game entity", var23);
               var12 = this.field2004[var4] = this.field2004[var4] & -9;
            }
         }

         if (!this.method4490(var4)) {
            assert false : "tile needs backside draw";
         } else if ((var12 & 16) == 0) {
            if (var5 <= this.field2112 && var5 > this.field2118) {
               int var33 = var4 - this.field1998;
               if (this.method4426(var33) && this.method4490(var33)) {
                  continue;
               }
            }

            if (var5 >= this.field2112 && var5 < this.field2121 - 1) {
               int var34 = var4 + this.field1998;
               if (this.method4426(var34) && this.method4490(var34)) {
                  continue;
               }
            }

            if (var6 <= this.field2122 && var6 > this.field2111) {
               int var35 = var4 - this.field1999;
               if (this.method4426(var35) && this.method4490(var35)) {
                  continue;
               }
            }

            if (var6 >= this.field2122 && var6 < this.field2120 - 1) {
               int var36 = var4 + this.field1999;
               if (this.method4426(var36) && this.method4490(var36)) {
                  continue;
               }
            }

            var12 = this.field2004[var4] = this.field2004[var4] & -5;
            this.field2039--;
            if ((var12 & 8192) != 0) {
               ItemLayer var37 = this.field2010[var4];
               if (var37.field1844 != null) {
                  this.method4460(
                     var1,
                     var37.field1844,
                     0,
                     var37.field1838 * -892202585,
                     var37.field1839 * 1186822141 - var37.field1836 * 218008627,
                     var37.field1837 * -1209739927,
                     var37.field1842
                  );
               }

               if (var37.field1845 != null) {
                  this.method4460(
                     var1,
                     var37.field1845,
                     0,
                     var37.field1838 * -892202585,
                     var37.field1839 * 1186822141 - var37.field1836 * 218008627,
                     var37.field1837 * -1209739927,
                     var37.field1842
                  );
               }

               if (var37.field1843 != null) {
                  this.method4460(
                     var1,
                     var37.field1843,
                     0,
                     var37.field1838 * -892202585,
                     var37.field1839 * 1186822141 - var37.field1836 * 218008627,
                     var37.field1837 * -1209739927,
                     var37.field1842
                  );
               }
            }

            if ((var12 & 49152) != 0 && this.method4448(var4) != 0) {
               if ((var12 & 32768) != 0) {
                  DecorativeObject var38 = this.field2008[var4];
                  if (!this.method4534(var8, var5, var6, var38.field2277.field1741 * -1256242689)) {
                     if ((var38.field2269 * 254285683 & this.method4448(var4)) != 0) {
                        this.method4460(
                           var1,
                           var38.field2277,
                           0,
                           var38.field2273 * 1766830743 + var38.field2274 * -404927711,
                           var38.field2271 * 1698079433,
                           var38.field2266 * -361702377 + var38.field2268 * 856605829,
                           var38.field2264 * -3608662145112923099L
                        );
                     } else if (var38.field2269 * 254285683 == 256) {
                        int var52 = var38.field2273 * 1766830743 - this.field2116;
                        int var60 = var38.field2266 * -361702377 - this.field2114;
                        int var67 = var38.field2265 * -819410985;
                        int var76;
                        if (var67 != 1 && var67 != 2) {
                           var76 = var52;
                        } else {
                           var76 = -var52;
                        }

                        int var85;
                        if (var67 != 2 && var67 != 3) {
                           var85 = var60;
                        } else {
                           var85 = -var60;
                        }

                        if (var85 >= var76) {
                           this.method4460(
                              var1,
                              var38.field2277,
                              0,
                              var38.field2273 * 1766830743 + var38.field2274 * -404927711,
                              var38.field2271 * 1698079433,
                              var38.field2266 * -361702377 + var38.field2268 * 856605829,
                              var38.field2264 * -3608662145112923099L
                           );
                        } else if (var38.field2276 != null) {
                           this.method4460(
                              var1,
                              var38.field2276,
                              0,
                              var38.field2273 * 1766830743 + var38.field2267 * -1085897215,
                              var38.field2271 * 1698079433,
                              var38.field2266 * -361702377 + var38.field2270 * 513256357,
                              var38.field2264 * -3608662145112923099L
                           );
                        }
                     }
                  }
               }

               if ((var12 & 16384) != 0) {
                  WallObject var39 = this.field2007[var4];
                  int var53 = this.method4448(var4);
                  if ((var39.field2241 * -1413337351 & var53) != 0 && !this.method4533(var8, var5, var6, var39.field2241 * -1413337351)) {
                     this.method4460(
                        var1,
                        var39.field2246,
                        0,
                        var39.field2242 * -777838807,
                        var39.field2240 * 1315862339,
                        var39.field2243 * 31139801,
                        var39.field2238 * 7159317397185328723L
                     );
                  }

                  if ((var39.field2244 * 1548562339 & var53) != 0 && !this.method4533(var8, var5, var6, var39.field2244 * 1548562339)) {
                     this.method4460(
                        var1,
                        var39.field2247,
                        0,
                        var39.field2242 * -777838807,
                        var39.field2240 * 1315862339,
                        var39.field2243 * 31139801,
                        var39.field2238 * 7159317397185328723L
                     );
                  }
               }
            }

            if (var7 < this.field2057 - 1) {
               int var40 = var4 + this.field1997;
               if (this.method4426(var40) && this.method4490(var40)) {
                  this.method4481(var40);
               }
            }

            if (var5 < this.field2112 && var5 >= this.field2118 && var5 < this.field2121 - 1) {
               int var41 = var4 + this.field1998;
               if (this.method4426(var41) && this.method4490(var41)) {
                  this.method4481(var41);
               }
            }

            if (var6 < this.field2122 && var6 >= this.field2111 && var6 < this.field2120 - 1) {
               int var42 = var4 + this.field1999;
               if (this.method4426(var42) && this.method4490(var42)) {
                  this.method4481(var42);
               }
            }

            if (var5 > this.field2112 && var5 > this.field2118 && var5 < this.field2121) {
               int var43 = var4 - this.field1998;
               if (this.method4426(var43) && this.method4490(var43)) {
                  this.method4481(var43);
               }
            }

            if (var6 > this.field2122 && var6 > this.field2111 && var6 < this.field2120) {
               int var44 = var4 - this.field1999;
               if (this.method4426(var44) && this.method4490(var44)) {
                  this.method4481(var44);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([IIIIII)V")
   @ObfuscatedName("iw")
   public void method4279(int[] var1, int var2, int var3, int var4, int var5, int var6) {
      Tile var7 = this.field2056[var4][var5][var6];
      if (var7 != null) {
         SceneTilePaint var8 = var7.field1970;
         if (var8 != null) {
            int var18 = var8.field2287 * -1850192500;
            if (var18 != 0) {
               for (int var19 = 0; var19 < 4; var19++) {
                  var1[var2] = var18;
                  var1[var2 + 1] = var18;
                  var1[var2 + 2] = var18;
                  var1[var2 + 3] = var18;
                  var2 += var3;
               }
            }
         } else {
            SceneTileModel var9 = var7.field1971;
            if (var9 != null) {
               int var10 = var9.field2318;
               int var11 = var9.field2324;
               int var12 = var9.field2331;
               int var13 = var9.field2319;
               int[] var14 = field2082[var10];
               int[] var15 = field2078[var11];
               int var16 = 0;
               if (var12 != 0) {
                  for (int var17 = 0; var17 < 4; var17++) {
                     var1[var2] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var1[var2 + 1] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var1[var2 + 2] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var1[var2 + 3] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var2 += var3;
                  }
               } else {
                  for (int var26 = 0; var26 < 4; var26++) {
                     if (var14[var15[var16++]] != 0) {
                        var1[var2] = var13;
                     }

                     if (var14[var15[var16++]] != 0) {
                        var1[var2 + 1] = var13;
                     }

                     if (var14[var15[var16++]] != 0) {
                        var1[var2 + 2] = var13;
                     }

                     if (var14[var15[var16++]] != 0) {
                        var1[var2 + 3] = var13;
                     }

                     var2 += var3;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("lx")
   @Override
   void vmethod415(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      IntProjection var15 = IntProjection.field1937;
      float var16 = var15.field1926;
      float var17 = var15.field1929;
      float var18 = var15.field1928;
      classUG var19 = classUG.method11380();
      var19.field6303.method11467(client.method2320(var1 & 2047), 0.0F, 0.0F);
      var19.field6305.method11858(var4, var5, var6);
      classUG var20 = classUG.method11380();
      var20.field6305.method11858(-(this.field2054 * 64) - this.field2027, this.field2028, -(this.field2030 * 64) - this.field2045);
      classUU var21 = ((FloatProjection)this.field2021).field1762;
      var21.method11726(var20);
      classUU var22 = classUU.method11711();
      var22.method11717(1.0F, this.field2053, 1.0F, 1.0F);
      var21.method11728(var22);
      var21.method11728(this.field2026);
      var22.method11726(var19);
      var21.method11728(var22);
      var22.method11720(var21);
      var22.method11721();
      this.field2084 = var22.method11715(var16, var17, var18);
      this.field2086 = var22.method11730(var16, var17, var18);
      this.field2091 = var22.method11716(var16, var17, var18);
      this.field2116 = (int)this.field2084;
      this.field2117 = (int)this.field2086;
      this.field2114 = (int)this.field2091;
      this.field2112 = this.field2116 >> 7;
      this.field2122 = this.field2114 >> 7;
      this.field2119 = (int)var22.method11715(var12, 0.0F, var13) >> 7;
      this.field2123 = (int)var22.method11716(var12, 0.0F, var13) >> 7;
      var19.method11379();
      var20.method11379();
      var22.method11729();
      this.field2022.field1762.method11720(var21);
      classUX var23 = classUX.field6408;
      var23.method11771();
      var23.method11769(-var15.field1927);
      var23.method11772(-var15.field1932);
      var23.method11768(var16, var17, var18);
      classUU var24 = classUU.method11711();
      var24.method11727(var23);
      var24.method11721();
      var21.method11728(var24);
      var24.method11729();
      classFO.field2344 = false;
      ((FloatProjection)this.field2021).field1763 = var10 * 5128300923381284097L;
      if ((client.field1021 & 16) != 0) {
         this.method4508(true, var14);
         this.method4474(this.field2021, this.field2022);
         classFH.field2255.field2213.method4861(this.field2059);
      } else {
         this.method4508(classFH.field2255.field2215 != 1, var14);
         if (classFH.field2255.field2215 == 0) {
            assert classFH.field2259.method4972();

            assert !classOE.field4841.isGpu();

            this.method4453(this.field2021);
         } else {
            this.method4480(this.field2021);
         }

         classFH.field2255.field2213.method4861(this.field2059);
         if (client.field1117 != null) {
            client.field1117.postDrawScene();
         }
      }

      classFO.field2344 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Lfb;)V")
   @ObfuscatedName("dv")
   public static void method4224(Scene var0, GameObject var1) {
      if (var0 == null) {
         var0.getRoofs();
      }

      for (int var2 = var1.field2186 * 1138017605; var2 <= var1.field2184 * 304142753; var2++) {
         for (int var3 = var1.field2192 * -555977777; var3 <= var1.field2190 * -544424899; var3++) {
            Tile var4 = var0.field2056[var1.field2185 * 568825275][var2][var3];
            if (var4 != null) {
               for (int var5 = 0; var5 < var4.field1961 * -389646721; var5++) {
                  if (var4.field1949[var5] == var1) {
                     var4.field1961 -= -71349889;

                     for (int var6 = var5; var6 < var4.field1961 * -389646721; var6++) {
                        var4.field1949[var6] = var4.field1949[var6 + 1];
                        var4.field1951[var6] = var4.field1951[var6 + 1];
                     }

                     var4.field1949[var4.field1961 * -389646721] = null;
                     break;
                  }
               }

               var0.method4228(var4);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ea")
   public int method4151() {
      return this.field2062;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ek")
   public int method4152() {
      return this.field2062;
   }

   @ObfuscatedSignature(descriptor = "(Lfv;)V")
   @ObfuscatedName("bd")
   public void method4480(Projection var1) {
      boolean var2 = this.field1986 != 0 && this.field2060;
      int var3 = classOE.field4841.isGpu() ? field2141 : this.field2049;

      for (int var4 = this.field2057 - 1; var4 >= this.field2038; var4--) {
         boolean var5 = this.method4492(this.field2116, this.field2117, this.field2114, var4, this.field2057 - 1 == var4);

         for (int var6 = this.field2118; var6 < this.field2121; var6++) {
            int var7 = var5 ? field2072[var6] : this.field2111;
            int var8 = var5 ? field2073[var6] : this.field2120 - 1;

            for (int var9 = var7; var9 <= var8; var9++) {
               int var10 = this.method4438(var4, var6, var9);
               if (this.method4426(var10)) {
                  int var11 = this.method4472(var10);
                  int var12 = this.field1992[this.field2002.field1708 * -483624883][var6][var9];
                  if ((var11 <= this.field2025 || var2)
                     && (
                        !this.field2060
                           || var5
                           || this.method4540(var6 - this.field2112 + this.field2051 + this.field2049, var9 - this.field2122 + this.field2052 + this.field2049)
                           || this.field2058[var4][var6][var9] - this.field2117 >= 2000
                     )
                     && (!var2 || this.field2002.field1708 * -483624883 >= var11 || var12 == 0 || !this.field2020.contains(var12))) {
                     int var13 = this.field2004[var10];
                     var13 |= 6;
                     var13 |= this.field2011[var10] <= 0 && (var13 & 128) == 0 ? 0 : 8;
                     var13 &= -16711697;
                     this.field2004[var10] = var13;
                     this.field2039++;
                  } else {
                     this.field2004[var10] = this.field2004[var10] & -23;
                  }
               }
            }
         }
      }

      int var16 = Math.abs(this.field2051);
      int var17 = Math.abs(this.field2052);

      label140:
      for (int var18 = 0; var18 < 2; var18++) {
         boolean var19 = var18 == 0;

         for (int var20 = this.field2038; var20 < this.field2057; var20++) {
            for (int var21 = -(var16 + var3); var21 <= 0; var21++) {
               int var22 = var21 + this.field2112;
               int var23 = this.field2112 - var21;
               if (var22 >= this.field2118 || var23 < this.field2121) {
                  for (int var24 = -(var17 + var3); var24 <= 0; var24++) {
                     int var28 = var24 + this.field2122;
                     int var14 = this.field2122 - var24;
                     if (var22 >= this.field2118 && var22 < this.field2121) {
                        if (var28 >= this.field2111 && var28 < this.field2120) {
                           int var15 = this.method4438(var20, var22, var28);
                           if ((this.field2004[var15] & 3) == 3) {
                              this.method4497(var1, var15, var19);
                           }
                        }

                        if (var14 >= this.field2111 && var14 < this.field2120) {
                           int var29 = this.method4438(var20, var22, var14);
                           if ((this.field2004[var29] & 3) == 3) {
                              this.method4497(var1, var29, var19);
                           }
                        }
                     }

                     if (var23 >= this.field2118 && var23 < this.field2121) {
                        if (var28 >= this.field2111 && var28 < this.field2120) {
                           int var30 = this.method4438(var20, var23, var28);
                           if ((this.field2004[var30] & 3) == 3) {
                              this.method4497(var1, var30, var19);
                           }
                        }

                        if (var14 >= this.field2111 && var14 < this.field2120) {
                           int var31 = this.method4438(var20, var23, var14);
                           if ((this.field2004[var31] & 3) == 3) {
                              this.method4497(var1, var31, var19);
                           }
                        }
                     }

                     if (this.field2039 == 0) {
                        break label140;
                     }
                  }
               }
            }
         }
      }

      this.method4548();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;)V")
   @ObfuscatedName("hu")
   public static void method4362(Scene var0) {
      if (var0 == null) {
         var0.method4396();
      }

      var0.field2031 = true;
   }

   @ObfuscatedSignature(descriptor = "(ILfx;)V")
   @ObfuscatedName("es")
   public void method4155(int var1, Model var2) {
      this.field2062 = var1;
      this.field2147 = var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;)V")
   @ObfuscatedName("rk")
   public static void method4159(Scene var0) {
      if (var0 == null) {
         var0.method4371();
      }

      var0.field2062 = -1;
      var0.field2147 = null;
   }

   @ObfuscatedSignature(descriptor = "(ILfx;)V")
   @ObfuscatedName("ej")
   public void method4156(int var1, Model var2) {
      this.field2062 = var1;
      this.field2147 = var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("el")
   public void method4160() {
      this.field2062 = -1;
      this.field2147 = null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)I")
   @ObfuscatedName("eh")
   public static int method4162(boolean var0) {
      return var0 ? 1 : 1024;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lj")
   void method4367() {
      if (this.field2041) {
         this.field2041 = false;
         this.field2034 = this.field2024;
         this.field2035 = this.field2032;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)I")
   @ObfuscatedName("en")
   public static int method4163(boolean var0) {
      return var0 ? 1 : 1024;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)I")
   @ObfuscatedName("ex")
   public static int method4164(boolean var0) {
      return var0 ? 1 : 1024;
   }

   public byte getOverrideHue() {
      return this.field2033.field2298;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)I")
   @ObfuscatedName("eq")
   public static int method4166(boolean var0) {
      return var0 ? 4160 : -1300232836;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)I")
   @ObfuscatedName("er")
   public static int method4167(boolean var0) {
      return var0 ? 4160 : 3064;
   }

   public byte getOverrideAmount() {
      return this.field2033.field2301;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ru")
   public int method4427(int var1) {
      return this.field2004[var1] >> 20 & 15;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("eo")
   public static int method4170() {
      return field2064;
   }

   public void removeTile(net.runelite.api.Tile var1) {
      rl10 var2 = (rl10)var1;
      int var3 = var2.field5690;
      int var4 = this.field2004[var3];
      int var5 = var3 >> this.field1993 & this.field1995;
      int var6 = var3 & this.field1996;
      rl17 var7 = this.field2017[var5 >> 3][var6 >> 3];
      if ((var4 & 2048) != 0) {
         var7.field5703.remove(this.field2009[var3]);
      }

      if ((var4 & 12288) != 0) {
         var7.field5703.remove(this.field2010[var3]);
      }

      if ((var4 & 16384) != 0) {
         var7.field5703.remove(this.field2007[var3]);
      }

      if ((var4 & 32768) != 0) {
         var7.field5703.remove(this.field2008[var3]);
      }

      byte var8 = this.field2011[var3];

      for (int var9 = 0; var9 < var8; var9++) {
         GameObject var10 = this.field2012[var3 * 5 + var9];
         var7.field5703.remove(var10);
      }

      this.method4447(var3);
      var2.field5691 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lfb;)V")
   @ObfuscatedName("fh")
   public void method4449(GameObject var1) {
      int var2 = (int)(var1.field2183 * 1745853678706228013L >> 16 & 7L);
      if (var2 == 2) {
         assert !(var1.method4591() instanceof Actor);

         assert !(var1.method4591() instanceof Projectile);

         assert !(var1.method4591() instanceof GraphicsObject);

         assert !(var1.method4591() instanceof Scene);

         assert !(var1.method4591() instanceof ActorSpotAnim);

         int var3 = var1.field2186 * 1138017605 + this.field2146;
         int var4 = var1.field2192 * -555977777 + this.field2146;
         int var5 = var1.getPlane();
         client.field1028.trace("Game object despawn: {}", var1.getId());
         if (var1.field2198 instanceof DynamicObject) {
            rl17 var6 = this.field2017[var3 >> 3][var4 >> 3];
            var6.field5703.remove(var1);
         }

         if (this.field2152) {
            GameObjectDespawned var7 = new GameObjectDespawned();
            var7.setTile(this.field1991[var5][var3][var4]);
            var7.setGameObject(var1);
            classOE.field4841.getCallbacks().post(var7);
         }

         if (this.field2152 && client.field1117 != null) {
            client.field1117.invalidateZone(this, var3 >> 3, var4 >> 3);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("et")
   public static int method4171() {
      return field2064;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("zj")
   public void method4483(int var1, int var2, int var3, int var4) {
      this.method4447(var1);
      this.field2004[var1] = 1;
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;IJ)Z")
   @ObfuscatedName("go")
   public boolean method4218(int var1, int var2, int var3, int var4, Renderable var5, int var6, long var7) {
      if (var5 == null) {
         return false;
      } else {
         int var9 = var2 / 128;
         int var10 = var3 / 828775391;
         if (var9 >= 0 && var10 >= 0 && var9 < this.field2054 && var10 < this.field2030) {
            Tile var11 = this.field2056[var1][var9][var10];
            if (var11 != null && (var11.field1953 == null || var11.field1953.size() != 50)) {
               if (var11.field1953 == null) {
                  var11.field1953 = new ArrayList();
               }

               GameObject var12 = new GameObject();
               var12.field2183 = var7 * -4819202259227702107L;
               var12.field2182 = 0;
               var12.field2185 = var1 * 496176618;
               var12.field2193 = var2 * 550346604;
               var12.field2194 = var3 * -94575718;
               var12.field2188 = var4 * 1573958690;
               var12.field2198 = var5;
               var12.field2187 = var6 * 672279374;
               var12.field2186 = var9 * 1137974587;
               var12.field2192 = var10 * -1297334770;
               var12.field2184 = var9 * 1384467041;
               var12.field2190 = var10 * 665976936;
               var11.field1953.add(var12);
               this.method4219(var11);
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hz")
   public int method4507() {
      int var2 = this.field2001;
      var2 <<= 1;
      int var1 = this.field2016[var2];
      if (var1 >= this.field2001) {
         return this.field2000;
      } else {
         this.method4452(var1);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("dc")
   static boolean method4330(int var0, int var1, int var2, int var3, boolean var4) {
      return method4317(field2150, var0, var1, var2, var3, var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIIII)Z")
   @ObfuscatedName("ef")
   public static boolean method4176(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var1 < var2 && var1 < var3 && var1 < var4) {
         return false;
      } else if (var1 > var2 && var1 > var3 && var1 > var4) {
         return false;
      } else if (var0 < var5 && var0 < var6 && var0 < var7) {
         return false;
      } else if (var0 > var5 && var0 > var6 && var0 > var7) {
         return false;
      } else {
         int var8 = (var1 - var2) * (var6 - var5) - (var0 - var5) * (var3 - var2);
         int var9 = (var1 - var3) * (var7 - var6) - (var0 - var6) * (var4 - var3);
         int var10 = (var1 - var4) * (var5 - var7) - (var0 - var7) * (var2 - var4);
         if (var8 == 0) {
            if (var9 != 0) {
               return var9 < 0 ? var10 <= 0 : var10 >= 0;
            } else {
               return true;
            }
         } else {
            return var8 < 0 ? var9 <= 0 && var10 <= 0 : var9 >= 0 && var10 >= 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("mm")
   public boolean method4492(int var1, int var2, int var3, int var4, boolean var5) {
      DrawCallbacks var6 = client.field1117;
      if (classOE.field4841.isGpu() && var6 != null && this.field2060) {
         for (int var7 = this.field2118; var7 < this.field2121; var7++) {
            int var8 = this.field2030;
            int var9 = 0;

            for (int var10 = this.field2111; var10 < this.field2120; var10++) {
               int var11 = this.method4438(var4, var7, var10);
               if (this.method4426(var11)
                  && var6.tileInFrustum(this, this.field2085, this.field2088, this.field2092, this.field2090, var1, var2, var3, var4, var7, var10)) {
                  var8 = Math.min(var8, var10);
                  var9 = Math.max(var9, var10);
               }
            }

            if (var5) {
               field2072[var7] = var8;
               field2073[var7] = var9;
            } else {
               field2072[var7] = Math.min(field2072[var7], var8);
               field2073[var7] = Math.max(field2073[var7], var9);
            }
         }

         for (int var12 = this.field2118 + 1; var12 < this.field2121 - 1; var12++) {
            field2072[var12] = Math.min(field2072[var12], Math.max(field2072[var12 - 1], field2072[var12 + 1]));
            field2073[var12] = Math.max(field2073[var12], Math.min(field2073[var12 - 1], field2073[var12 + 1]));
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;JLee;Lee;I)V")
   @ObfuscatedName("fk")
   public void method4201(int var1, int var2, int var3, int var4, Renderable var5, long var6, Renderable var8, Renderable var9, int var10) {
      ItemLayer var11 = new ItemLayer();
      var11.field1843 = var5;
      var11.field1838 = var2 * -1295840384 + 1499563456;
      var11.field1837 = var3 * 75852928 + -2109557184;
      var11.field1839 = var4 * -673866411;
      var11.field1842 = var6;
      var11.field1844 = var8;
      var11.field1845 = var9;
      var11.field1836 = var10 * 1484214011;
      if (this.field2056[var1][var2][var3] == null) {
         this.field2056[var1][var2][var3] = new Tile(var1, var2, var3);
      }

      this.field2056[var1][var2][var3].field1966 = var11;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;III)V")
   @ObfuscatedName("md")
   public static void method4359(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method4437(var1, var1, var1);
      }

      var0.field2042 = var1;
      var0.field2043 = var2;
      var0.field2044 = var3;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ee")
   public void method4177() {
      for (int var1 = 0; var1 < this.field2057; var1++) {
         for (int var2 = 0; var2 < this.field2054; var2++) {
            for (int var3 = 0; var3 < this.field2030; var3++) {
               this.field2056[var1][var2][var3] = null;
            }
         }
      }

      if (this.field2061 != null) {
         for (int var4 = 0; var4 < this.field2036; var4++) {
            for (int var7 = 0; var7 < this.field2061[var4]; var7++) {
               this.field2037[var4][var7] = null;
            }

            this.field2061[var4] = 0;
         }
      }

      if (this.field2046 != null) {
         Arrays.fill(this.field2046, null);
      }

      this.field2107 = 0;

      for (int var5 = 0; var5 < this.field2048; var5++) {
         this.field2055[var5] = null;
      }

      this.field2048 = 0;

      for (int var6 = 0; var6 < this.field2023.length; var6++) {
         this.field2023[var6] = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("eb")
   public void method4181(int var1) {
      this.field2038 = var1;

      for (int var2 = 0; var2 < this.field2054; var2++) {
         for (int var3 = 0; var3 < this.field2030; var3++) {
            if (this.field2056[var1][var2][var3] == null) {
               this.field2056[var1][var2][var3] = new Tile(var1, var2, var3);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lew;)V")
   @ObfuscatedName("gt")
   void method4229(Tile var1) {
      var1.field1952 = 0;

      for (int var2 = 0; var2 < var1.field1961 * -389646721; var2++) {
         var1.field1952 = (var1.field1952 * -785027619 | var1.field1951[var2]) * -1095478667;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("fo")
   public void method4184(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      classEP var9 = new classEP();
      var9.field1819 = var3 / 128 * 704810239;
      var9.field1825 = var4 / 128 * -1209725141;
      var9.field1828 = var5 / 128 * -78990867;
      var9.field1831 = var6 / 128 * 950850697;
      var9.field1816 = var2 * 185901273;
      var9.field1815 = var3 * -1875068183;
      var9.field1832 = var4 * 1706709409;
      var9.field1818 = var5 * 106408303;
      var9.field1827 = var6 * 1541236735;
      var9.field1829 = var7 * -969645925;
      var9.field1821 = var8 * 1077506721;
      this.field2037[var1][this.field2061[var1]++] = var9;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("fl")
   public void method4185(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      classEP var9 = new classEP();
      var9.field1819 = var3 / 128 * 184212436;
      var9.field1825 = var4 / 2103908952 * -1209725141;
      var9.field1828 = var5 / 1583989872 * -78990867;
      var9.field1831 = var6 / -225053351 * 807895637;
      var9.field1816 = var2 * 185901273;
      var9.field1815 = var3 * -1875068183;
      var9.field1832 = var4 * -251997020;
      var9.field1818 = var5 * -350625218;
      var9.field1827 = var6 * 1541236735;
      var9.field1829 = var7 * 1615984411;
      var9.field1821 = var8 * 1077506721;
      this.field2037[var1][this.field2061[var1]++] = var9;
   }

   @ObfuscatedSignature(descriptor = "(III)Lfb;")
   @ObfuscatedName("mc")
   public GameObject method4531(int var1, int var2, int var3) {
      return method4487(this, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("fj")
   public void method4187(int var1, int var2, int var3, int var4) {
      Tile var5 = this.field2056[var1][var2][var3];
      if (var5 != null) {
         this.field2056[var1][var2][var3].field1962 = var4 * 1248621793;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("fv")
   public int method4196(int var1, int var2, int var3) {
      int var4 = 0;
      Tile var5 = this.field2056[var1][var2][var3];
      if (var5 != null) {
         for (int var6 = 0; var6 < var5.field1961 * -389646721; var6++) {
            if ((var5.field1949[var6].field2182 * 1896767852 & 256) == -368396687 && var5.field1949[var6].field2198 instanceof Model) {
               Model var7 = (Model)var5.field1949[var6].field2198;
               Model.method5254(var7);
               if (var7.field1741 * -762394348 > var4) {
                  var4 = var7.field1741 * -976093565;
               }
            }
         }
      }

      return var4;
   }

   @ObfuscatedSignature(descriptor = "(Lee;IIIIII)V")
   @ObfuscatedName("ax")
   public void method4513(Renderable var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      Model var8 = null;
      int var9 = client.field1021 >> 7 & 15;
      if (!(var1 instanceof DynamicObject) || !((DynamicObject)var1).field1681 && var9 <= 0) {
         if (var1 instanceof Model) {
            var8 = (Model)var1;
         }
      } else {
         var8 = ((DynamicObject)var1).method3728();
      }

      if (var8 != null) {
         var8.calculateExtreme(var2);
         AABB var10 = var8.method5261(var2);
         int var11 = var3 - ((var6 & 7) << 7);
         int var12 = var5 - ((var7 & 7) << 7);
         int var13 = var11 + var10.field2365 * -915116879;
         int var14 = var4 + var10.field2364 * -1027280203;
         int var15 = var12 + var10.field2367 * -81102275;
         int var16 = var13 - var10.field2366 * 1301855055;
         int var17 = var14 - var10.field2363 * -623192411;
         int var18 = var15 - var10.field2368 * 2105673973;
         int var19 = var13 + var10.field2366 * 1301855055;
         int var20 = var14 + var10.field2363 * -623192411;
         int var21 = var15 + var10.field2368 * 2105673973;
         int var22 = var6 + this.field2146;
         int var23 = var7 + this.field2146;
         rl17 var24 = this.field2017[var22 >> 3][var23 >> 3];
         var24.field5707 = Math.min(var24.field5707, var16);
         var24.field5706 = Math.min(var24.field5706, var17);
         var24.field5709 = Math.min(var24.field5709, var18);
         var24.field5701 = Math.max(var24.field5701, var19);
         var24.field5708 = Math.max(var24.field5708, var20);
         var24.field5702 = Math.max(var24.field5702, var21);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("fy")
   public void method4188(int var1, int var2, int var3, int var4) {
      Tile var5 = this.field2056[var1][var2][var3];
      if (var5 != null) {
         this.field2056[var1][var2][var3].field1962 = var4 * -2135733247;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIIIIIIIIIII)V")
   @ObfuscatedName("fg")
   public void method4191(
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20
   ) {
      if (var4 == 0) {
         SceneTilePaint var24 = new SceneTilePaint(var11, var12, var13, var14, -1, var19, false);

         for (int var26 = var1; var26 >= 0; var26--) {
            if (this.field2056[var26][var2][var3] == null) {
               this.field2056[var26][var2][var3] = new Tile(var26, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1970 = var24;
      } else if (var4 != 1) {
         if (var20 == 0) {
            var20 = 1;
         }

         SceneTileModel var23 = new SceneTileModel(
            var4, var5, var6, var2, var3, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20
         );

         for (int var25 = var1; var25 >= 0; var25--) {
            if (this.field2056[var25][var2][var3] == null) {
               this.field2056[var25][var2][var3] = new Tile(var25, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1971 = var23;
      } else {
         SceneTilePaint var21 = new SceneTilePaint(var15, var16, var17, var18, var6, var20, var7 == var8 && var7 == var9 && var7 == var10);

         for (int var22 = var1; var22 >= 0; var22--) {
            if (this.field2056[var22][var2][var3] == null) {
               this.field2056[var22][var2][var3] = new Tile(var22, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1970 = var21;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hd")
   public void method4240(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         for (int var5 = 0; var5 < var4.field1961 * -389646721; var5++) {
            GameObject var6 = var4.field1949[var5];
            long var8 = var6.field2183 * 1745853678706228013L;
            boolean var7 = classCV.method2679(var8) == 2;
            if (var7 && var6.field2186 * 1138017605 == var2 && var6.field2192 * -555977777 == var3) {
               this.method4475(var6);
               return;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldy;)V")
   @ObfuscatedName("pa")
   public void method4348(DynamicObject var1) {
      if (var1 != null) {
         var1.method3727();
         if (var1.field1663 > -1 && this.field2019 != null) {
            this.field2019.set(var1.field1663);
         }

         if (var1.method3730()) {
            var1.field1681 = true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("it")
   int method4272(int var1, int var2, int var3) {
      return (
            this.field2058[var1][var2][var3]
               + this.field2058[var1][var2 + 1][var3]
               + this.field2058[var1][var2][var3 + 1]
               + this.field2058[var1][var2 + 1][var3 + 1]
         )
         / 4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("ju")
   static boolean method4297(int var0, int var1, int var2, int var3) {
      return method4291(field2148, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("fq")
   public int method4197(int var1, int var2, int var3) {
      int var4 = 0;
      Tile var5 = this.field2056[var1][var2][var3];
      if (var5 != null) {
         for (int var6 = 0; var6 < var5.field1961 * -389646721; var6++) {
            if ((var5.field1949[var6].field2182 * -288062455 & 256) == 256 && var5.field1949[var6].field2198 instanceof Model) {
               Model var7 = (Model)var5.field1949[var6].field2198;
               Model.method5254(var7);
               if (var7.field1741 * -1256242689 > var4) {
                  var4 = var7.field1741 * -1256242689;
               }
            }
         }
      }

      return var4;
   }

   @ObfuscatedSignature(descriptor = "([IIIII)V")
   @ObfuscatedName("yw")
   public static void method4532(int[] var0, int var1, int var2, int var3, int var4) {
      method4333(var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("fs")
   public int method4198(int var1, int var2, int var3) {
      int var4 = 0;
      Tile var5 = this.field2056[var1][var2][var3];
      if (var5 != null) {
         for (int var6 = 0; var6 < var5.field1961 * -389646721; var6++) {
            if ((var5.field1949[var6].field2182 * -288062455 & 256) == 256 && var5.field1949[var6].field2198 instanceof Model) {
               Model var7 = (Model)var5.field1949[var6].field2198;
               Model.method5254(var7);
               if (var7.field1741 * -1256242689 > var4) {
                  var4 = var7.field1741 * -1256242689;
               }
            }
         }
      }

      return var4;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("in")
   int method4273(int var1, int var2, int var3) {
      return (
            this.field2058[var1][var2][var3]
               + this.field2058[var1][var2 + 1][var3]
               + this.field2058[var1][var2][var3 + 1]
               + this.field2058[var1][var2 + 1][var3 + 1]
         )
         / 4;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lw")
   public void method4493() {
      boolean var1 = this.field1986 != 0 && this.field2060;
      if (var1) {
         this.field2020.clear();
         Player var2 = classOE.field4841.method2333();
         if (var2 != null && (this.field1986 & 1) != 0) {
            LocalPoint var3 = var2.getLocalLocation();
            if (var3.isInScene()) {
               int var4 = this.field1992[this.field2002.field1708 * -483624883][var3.getSceneX() + this.field2146][var3.getSceneY() + this.field2146];
               this.method4518(var4);
            }
         }

         if (this.field1988 >= this.field2110
            && this.field1988 < this.field2142
            && this.field1989 >= this.field2145
            && this.field1989 < this.field2139
            && (this.field1986 & 2) != 0) {
            int var15 = this.field1992[this.field2002.field1708 * -483624883][this.field1988 + this.field2146][this.field1989 + this.field2146];
            this.method4518(var15);
         }

         LocalPoint var16 = classOE.field4841.getLocalDestinationLocation();
         if (var16 != null && var16.isInScene() && (this.field1986 & 4) != 0) {
            int var17 = this.field1992[this.field2002.field1708 * -483624883][var16.getSceneX() + this.field2146][var16.getSceneY() + this.field2146];
            this.method4518(var17);
         }

         if (classOE.field4841.getCameraPitch() < 2480 && (this.field1986 & 8) != 0 && var2 != null) {
            int var18 = var2.method3305() >> 7;
            int var5 = var2.method3303() >> 7;
            int var6 = classOE.field4841.getCameraX() >> 7;
            int var7 = classOE.field4841.getCameraY() >> 7;
            if (var18 >= this.field2110
               && var5 >= this.field2145
               && var6 >= this.field2110
               && var7 >= this.field2145
               && var18 < this.field2142
               && var5 < this.field2139
               && var6 < this.field2142
               && var7 < this.field2139) {
               int var8 = Math.abs(var18 - var6);
               int var9 = Integer.compare(var18, var6);
               int var10 = -Math.abs(var5 - var7);
               int var11 = Integer.compare(var5, var7);
               int var12 = var8 + var10;

               while (var6 != var18 || var7 != var5) {
                  if (this.method4419(this.field2002.field1708 * -483624883, var6 + this.field2146, var7 + this.field2146)) {
                     int var14 = this.field1992[this.field2002.field1708 * -483624883][var6 + this.field2146][var7 + this.field2146];
                     this.method4518(var14);
                  }

                  int var13 = 2 * var12;
                  if (var13 >= var10) {
                     var12 += var10;
                     var6 += var9;
                  } else {
                     var12 += var8;
                     var7 += var11;
                  }
               }
            }
         }

         this.field2020.remove(0);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("db")
   void method4368() {
      if (this.field2041) {
         this.field2041 = false;
         this.field2034 = this.field2024;
         this.field2035 = this.field2032;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("gs")
   public void method4231(int var1, int var2, int var3, int var4) {
      Tile var5 = this.field2056[var1][var2][var3];
      if (var5 != null) {
         DecorativeObject var6 = var5.field1969;
         if (var6 != null) {
            if (var6.field2274 * -404927711 != 0) {
               var6.field2274 = (var6.field2274 * -404927711 < 0 ? -var4 : var4) * 261748961;
            }

            if (var6.field2268 * 856605829 != 0) {
               var6.field2268 = (var6.field2268 * 856605829 < 0 ? -var4 : var4) * 46830669;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("hh")
   public long method4461(int var1, int var2, int var3) {
      var2 += this.field2146;
      var3 += this.field2146;
      int var4 = this.method4438(var1, var2, var3);
      return this.method4426(var4) && this.field2007[var4] != null ? this.field2007[var4].field2238 * 7159317397185328723L : 0L;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ai")
   public void method4456(int var1, int var2, int var3) {
      if (this.field2158 != null) {
         int var4 = var2 - this.field2146;
         int var5 = var3 - this.field2146;
         if (var4 >= 0 && var4 < this.field2158[var1].length && var5 >= 0 && var5 < this.field2158[var1][var4].length) {
            this.field2158[var1][var4][var5] = this.field1991[var1][var2][var3];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;Lee;IIIIIIJI)V")
   @ObfuscatedName("ff")
   public void method4205(
      int var1,
      int var2,
      int var3,
      int var4,
      Renderable var5,
      Renderable var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      long var13,
      int var15
   ) {
      if (var5 != null) {
         DecorativeObject var16 = new DecorativeObject();
         var16.field2264 = var13 * 2170180156281739181L;
         var16.field2263 = var15 * -47677163;
         var16.field2273 = var2 * 896963456 + -1699001920;
         var16.field2266 = var3 * 856544128 + 428272064;
         var16.field2271 = var4 * 765794169;
         var16.field2277 = var5;
         var16.field2276 = var6;
         var16.field2269 = var7 * 1259333051;
         var16.field2265 = var8 * 1071651815;
         var16.field2274 = var9 * 261748961;
         var16.field2268 = var10 * 46830669;
         var16.field2267 = var11 * -153257471;
         var16.field2270 = var12 * -829209555;

         for (int var17 = var1; var17 >= 0; var17--) {
            if (this.field2056[var17][var2][var3] == null) {
               this.field2056[var17][var2][var3] = new Tile(var17, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1969 = var16;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl10;)V")
   @ObfuscatedName("xj")
   public void method4504(rl10 var1) {
      Callbacks var2 = classOE.field4841.getCallbacks();
      WallObject var3 = var1.method10073();
      if (var3 != null) {
         WallObjectSpawned var4 = new WallObjectSpawned();
         var4.setTile(var1);
         var4.setWallObject(var3);
         var2.post(var4);
      }

      DecorativeObject var13 = var1.method10079();
      if (var13 != null) {
         DecorativeObjectSpawned var5 = new DecorativeObjectSpawned();
         var5.setTile(var1);
         var5.setDecorativeObject(var13);
         var2.post(var5);
      }

      GroundObject var14 = var1.method10072();
      if (var14 != null) {
         GroundObjectSpawned var6 = new GroundObjectSpawned();
         var6.setTile(var1);
         var6.setGroundObject(var14);
         var2.post(var6);
      }

      for (GameObject var9 : var1.method10077()) {
         if (var9 != null) {
            int var10 = var9.field2186 * 1138017605;
            int var11 = var9.field2192 * -555977777;
            if (var10 == var1.method10078() && var11 == var1.method10075() && var9.field2184 * 304142753 < 104 && var9.field2190 * -544424899 < 104) {
               GameObjectSpawned var12 = new GameObjectSpawned();
               var12.setTile(var1);
               var12.setGameObject(var9);
               var2.post(var12);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;Lee;IIIIIIJI)V")
   @ObfuscatedName("fe")
   public void method4206(
      int var1,
      int var2,
      int var3,
      int var4,
      Renderable var5,
      Renderable var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      long var13,
      int var15
   ) {
      if (var5 != null) {
         DecorativeObject var16 = new DecorativeObject();
         var16.field2264 = var13 * 2170180156281739181L;
         var16.field2263 = var15 * -1380406631;
         var16.field2273 = var2 * 896963456 + 1208710401;
         var16.field2266 = var3 * 856544128 + -1766125854;
         var16.field2271 = var4 * 765794169;
         var16.field2277 = var5;
         var16.field2276 = var6;
         var16.field2269 = var7 * 1259333051;
         var16.field2265 = var8 * 1071651815;
         var16.field2274 = var9 * 261748961;
         var16.field2268 = var10 * -148592333;
         var16.field2267 = var11 * -153257471;
         var16.field2270 = var12 * -829209555;

         for (int var17 = var1; var17 >= 0; var17--) {
            if (this.field2056[var17][var2][var3] == null) {
               this.field2056[var17][var2][var3] = new Tile(var17, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1969 = var16;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lfv;)V")
   @ObfuscatedName("it")
   public void method4474(Projection var1, Projection var2) {
      int var3 = this.field2112 >> 3;
      int var4 = this.field2122 >> 3;
      int var5 = field2141 >> 3;
      int var6 = this.field2054 >> 3;
      int var7 = this.field2030 >> 3;
      this.field2108 = 0;

      for (int var8 = -var5; var8 <= var5; var8++) {
         for (int var9 = -var5; var9 <= var5; var9++) {
            int var10 = var3 + var8;
            int var11 = var4 + var9;
            if (var10 >= 0 && var10 < var6 && var11 >= 0 && var11 < var7) {
               rl17 var12 = this.field2018[this.field2108++] = this.field2017[var10][var11];
               var12.field5711 = false;
            }
         }
      }

      Arrays.sort(this.field2018, 0, this.field2108, this.field1990);
      rl23.method10107(var1, this);

      for (int var13 = 0; var13 < this.field2108; var13++) {
         rl17 var16 = this.field2018[var13];
         if (!this.field2060 || this.method4414(var16, var16.field5710, var16.field5705)) {
            var16.field5711 = true;
            client.field1117.drawZoneOpaque(var2, this, var16.field5710, var16.field5705);
            this.method4457(var1, var2, var16, this.field2020);
            this.method4440(var1, var2, var16);
         }
      }

      for (int var14 = this.field2108 - 1; var14 >= 0; var14--) {
         rl17 var17 = this.field2018[var14];
         if (var17.field5711) {
            this.method4471(var1, var2, var17);
         }
      }

      rl23.method10106();
      rl23.method10108();

      for (int var15 = this.field2108 - 1; var15 >= 0; var15--) {
         rl17 var18 = this.field2018[var15];
         if (var18.field5711) {
            this.method4446(var1, var18);
         }
      }

      client.field1117.drawPass(var2, this, 0);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ag")
   public void method4434() {
      if ((client.field1021 & 16) != 0) {
         for (int var1 = 0; var1 < this.field2054 >> 3; var1++) {
            for (int var2 = 0; var2 < this.field2030 >> 3; var2++) {
               rl17 var3 = this.field2017[var1][var2];
               if (var3 != null) {
                  boolean var4 = false;

                  for (int var5 = 0; var5 < var3.field5703.size(); var5++) {
                     TileObject var6 = (TileObject)var3.field5703.get(var5);
                     if (var6 instanceof GameObject) {
                        GameObject var7 = (GameObject)var6;

                        assert var7.field2198 instanceof DynamicObject;

                        DynamicObject var8 = (DynamicObject)var7.field2198;
                        if (!var8.field1681 && var8.method3730()) {
                           var8.method3727();
                           var8.field1681 = true;
                           var4 = true;
                        }
                     } else if (var6 instanceof GroundObject) {
                        GroundObject var9 = (GroundObject)var6;

                        assert var9.field1814 instanceof DynamicObject;

                        DynamicObject var12 = (DynamicObject)var9.field1814;
                        if (!var12.field1681 && var12.method3730()) {
                           var12.method3727();
                           var12.field1681 = true;
                           var4 = true;
                        }
                     } else if (var6 instanceof WallObject) {
                        WallObject var10 = (WallObject)var6;
                        if (var10.field2247 instanceof DynamicObject) {
                           DynamicObject var13 = (DynamicObject)var10.field2247;
                           if (!var13.field1681 && var13.method3730()) {
                              var13.method3727();
                              var13.field1681 = true;
                              var4 = true;
                           }
                        }

                        if (var10.field2246 instanceof DynamicObject) {
                           DynamicObject var14 = (DynamicObject)var10.field2246;
                           if (!var14.field1681 && var14.method3730()) {
                              var14.method3727();
                              var14.field1681 = true;
                              var4 = true;
                           }
                        }
                     } else if (var6 instanceof DecorativeObject) {
                        DecorativeObject var11 = (DecorativeObject)var6;
                        if (var11.field2277 instanceof DynamicObject) {
                           DynamicObject var15 = (DynamicObject)var11.field2277;
                           if (!var15.field1681 && var15.method3730()) {
                              var15.method3727();
                              var15.field1681 = true;
                              var4 = true;
                           }
                        }

                        if (var11.field2276 instanceof DynamicObject) {
                           DynamicObject var16 = (DynamicObject)var11.field2276;
                           if (!var16.field1681 && var16.method3730()) {
                              var16.method3727();
                              var16.field1681 = true;
                              var4 = true;
                           }
                        }
                     }
                  }

                  if (var4) {
                     client.field1117.invalidateZone(this, var3.field5710, var3.field5705);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;Lee;IIIIIIJI)V")
   @ObfuscatedName("fc")
   public void method4207(
      int var1,
      int var2,
      int var3,
      int var4,
      Renderable var5,
      Renderable var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      long var13,
      int var15
   ) {
      if (var5 != null) {
         DecorativeObject var16 = new DecorativeObject();
         var16.field2264 = var13 * 2170180156281739181L;
         var16.field2263 = var15 * -47677163;
         var16.field2273 = var2 * 296492093 + -854633774;
         var16.field2266 = var3 * 856544128 + 688297055;
         var16.field2271 = var4 * 765794169;
         var16.field2277 = var5;
         var16.field2276 = var6;
         var16.field2269 = var7 * 1259333051;
         var16.field2265 = var8 * -351751517;
         var16.field2274 = var9 * 261748961;
         var16.field2268 = var10 * 46830669;
         var16.field2267 = var11 * 16568119;
         var16.field2270 = var12 * -2036977261;

         for (int var17 = var1; var17 >= 0; var17--) {
            if (this.field2056[var17][var2][var3] == null) {
               this.field2056[var17][var2][var3] = new Tile(var17, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1969 = var16;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIILee;IJZ)Z")
   @ObfuscatedName("fm")
   public boolean method4210(int var1, int var2, int var3, int var4, int var5, Renderable var6, int var7, long var8, boolean var10) {
      if (var6 == null) {
         return true;
      } else {
         int var11 = var2 - var5;
         int var12 = var3 - var5;
         int var13 = var2 + var5;
         int var14 = var3 + var5;
         if (var10) {
            if (var7 > 640 && var7 < 1408) {
               var14 += 128;
            }

            if (var7 > 1152 && var7 < 1920) {
               var13 += 128;
            }

            if (var7 > 1664 || var7 < 384) {
               var12 -= 128;
            }

            if (var7 > 128 && var7 < 896) {
               var11 -= 128;
            }
         }

         var11 /= 128;
         var12 /= 128;
         var13 /= 128;
         var14 /= 128;
         return this.method4424(var1, var11, var12, var13 - var11 + 1, var14 - var12 + 1, var2, var3, var4, var6, var7, true, var8, 0);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIILee;IJZ)Z")
   @ObfuscatedName("fw")
   public boolean method4211(int var1, int var2, int var3, int var4, int var5, Renderable var6, int var7, long var8, boolean var10) {
      if (var6 == null) {
         return true;
      } else {
         int var11 = var2 - var5;
         int var12 = var3 - var5;
         int var13 = var2 + var5;
         int var14 = var3 + var5;
         if (var10) {
            if (var7 > 640 && var7 < 1408) {
               var14 += 128;
            }

            if (var7 > -1414134963 && var7 < 1249345380) {
               var13 += 128;
            }

            if (var7 > 1664 || var7 < -258490443) {
               var12 -= 128;
            }

            if (var7 > 128 && var7 < -674450742) {
               var11 -= 128;
            }
         }

         var11 /= 128;
         var12 /= -1011951674;
         var13 /= -1767950335;
         var14 /= 288632182;
         return this.method4424(var1, var11, var12, var13 - var11 + 1, var14 - var12 + 1, var2, var3, var4, var6, var7, true, var8, 0);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;)I")
   @ObfuscatedName("ru")
   public static int method4153(Scene var0) {
      if (var0 == null) {
         var0.method4154();
      }

      return var0.field2062;
   }

   @ObfuscatedSignature(descriptor = "(IIIIILee;IJIIII)Z")
   @ObfuscatedName("gq")
   public boolean method4213(int var1, int var2, int var3, int var4, int var5, Renderable var6, int var7, long var8, int var10, int var11, int var12, int var13) {
      return var6 == null ? true : this.method4424(var1, var10, var11, var12 - var10 + 1, var13 - var11 + 1, var2, var3, var4, var6, var7, true, var8, 0);
   }

   @ObfuscatedSignature(descriptor = "(IIIIILee;IJIIII)Z")
   @ObfuscatedName("gb")
   public boolean method4214(int var1, int var2, int var3, int var4, int var5, Renderable var6, int var7, long var8, int var10, int var11, int var12, int var13) {
      return var6 == null ? true : this.method4424(var1, var10, var11, var12 - var10 + 1, var13 - var11 + 1, var2, var3, var4, var6, var7, true, var8, 0);
   }

   public int[] getMapRegions() {
      return this.field2154;
   }

   public int[][][] getInstanceTemplateChunks() {
      return this.field2157;
   }

   @ObfuscatedSignature(descriptor = "(Z)I")
   @ObfuscatedName("ac")
   public static int method4168(boolean var0) {
      return var0 ? 4160 : 3064;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("em")
   public void method4178() {
      for (int var1 = 0; var1 < this.field2057; var1++) {
         for (int var2 = 0; var2 < this.field2054; var2++) {
            for (int var3 = 0; var3 < this.field2030; var3++) {
               this.field2056[var1][var2][var3] = null;
            }
         }
      }

      if (this.field2061 != null) {
         for (int var4 = 0; var4 < this.field2036; var4++) {
            for (int var7 = 0; var7 < this.field2061[var4]; var7++) {
               this.field2037[var4][var7] = null;
            }

            this.field2061[var4] = 0;
         }
      }

      if (this.field2046 != null) {
         Arrays.fill(this.field2046, null);
      }

      this.field2107 = 0;

      for (int var5 = 0; var5 < this.field2048; var5++) {
         this.field2055[var5] = null;
      }

      this.field2048 = 0;

      for (int var6 = 0; var6 < this.field2023.length; var6++) {
         this.field2023[var6] = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("yx")
   public boolean method4485(int var1) {
      return (this.field2004[var1] & 2) != 0;
   }

   @ObfuscatedSignature(descriptor = "(Lew;)Z")
   @ObfuscatedName("gp")
   boolean method4220(Tile var1) {
      if (var1.field1954 * -299736461 != this.field2063) {
         var1.field1954 = this.field2063 * -1699605829;
         var1.field1956 = var1.field1961 * -980607445;
         if (this.field2048 >= this.field2055.length) {
            this.field2055 = Arrays.copyOf(this.field2055, this.field2055.length * 2);
         }

         this.field2055[this.field2048++] = var1;
         return true;
      } else {
         return false;
      }
   }

   public void removeGameObject(net.runelite.api.GameObject var1) {
      this.method4475((GameObject)var1);
   }

   @ObfuscatedSignature(descriptor = "(Lew;)Z")
   @ObfuscatedName("ga")
   boolean method4221(Tile var1) {
      if (var1.field1954 * -9179043 != this.field2063) {
         var1.field1954 = this.field2063 * -1699605829;
         var1.field1956 = var1.field1961 * -906965805;
         if (this.field2048 >= this.field2055.length) {
            this.field2055 = Arrays.copyOf(this.field2055, this.field2055.length * 2);
         }

         this.field2055[this.field2048++] = var1;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gc")
   public void method4223() {
      for (int var1 = 0; var1 < this.field2048; var1++) {
         Tile var2 = this.field2055[var1];

         for (int var3 = var2.field1956 * 1189212925; var3 < var2.field1961 * -389646721; var3++) {
            var2.field1949[var3] = null;
         }

         var2.field1961 = var2.field1956 * -232476541;
         var2.field1954 = 1699605829;
         if (var2.field1953 != null) {
            var2.field1953.clear();
         }

         this.method4228(var2);
      }

      this.field2048 = 0;
   }

   @ObfuscatedSignature(descriptor = "(ILfx;)V")
   @ObfuscatedName("af")
   public void method4157(int var1, Model var2) {
      this.field2062 = var1;
      this.field2147 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Lew;)V")
   @ObfuscatedName("gn")
   void method4230(Tile var1) {
      var1.field1952 = 0;

      for (int var2 = 0; var2 < var1.field1961 * -389646721; var2++) {
         var1.field1952 = (var1.field1952 * -785027619 | var1.field1951[var2]) * -1095478667;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("us")
   public void method4441() {
      this.field2002 = null;

      for (int var1 = 0; var1 < this.field2054 >> 3; var1++) {
         for (int var2 = 0; var2 < this.field2030 >> 3; var2++) {
            rl17 var3 = this.field2017[var1][var2];

            for (int var4 = 0; var4 < 4; var4++) {
               int var5 = this.method4438(var4, var1, var2);
               WallObject var6 = this.field2007[var5];
               if (var6 != null) {
                  var6.field2238 = -1L * -2636099314526831141L;
                  var6.field2247 = var6.field2246 = null;
               }

               DecorativeObject var7 = this.field2008[var5];
               if (var7 != null) {
                  var7.field2264 = -1L * 2170180156281739181L;
                  var7.field2277 = var7.field2276 = null;
               }

               GroundObject var8 = this.field2009[var5];
               if (var8 != null) {
                  var8.field1813 = -1L * -8879266543907161229L;
                  var8.field1814 = null;
               }

               for (int var9 = 0; var9 < this.field2011[var5]; var9++) {
                  GameObject var10 = this.field2012[var5 * 5 + var9];
                  var10.field2183 = -1L * -4819202259227702107L;
                  var10.field2198 = null;
               }
            }

            for (TileObject var12 : var3.field5703) {
               if (var12 instanceof GameObject) {
                  GameObject var13 = (GameObject)var12;
                  DynamicObject var17 = (DynamicObject)var13.field2198;

                  assert var17.field1665 == this;

                  var17.field1665 = null;
               } else if (var12 instanceof GroundObject) {
                  GroundObject var14 = (GroundObject)var12;
                  DynamicObject var18 = (DynamicObject)var14.field1814;

                  assert var18.field1665 == this;

                  var18.field1665 = null;
               } else if (var12 instanceof WallObject) {
                  WallObject var15 = (WallObject)var12;
                  if (var15.field2247 instanceof DynamicObject) {
                     DynamicObject var19 = (DynamicObject)var15.field2247;

                     assert var19.field1665 == this;

                     var19.field1665 = null;
                  }

                  if (var15.field2246 instanceof DynamicObject) {
                     DynamicObject var20 = (DynamicObject)var15.field2246;

                     assert var20.field1665 == this;

                     var20.field1665 = null;
                  }
               } else if (var12 instanceof DecorativeObject) {
                  DecorativeObject var16 = (DecorativeObject)var12;
                  if (var16.field2277 instanceof DynamicObject) {
                     DynamicObject var21 = (DynamicObject)var16.field2277;

                     assert var21.field1665 == this;

                     var21.field1665 = null;
                  }

                  if (var16.field2276 instanceof DynamicObject) {
                     DynamicObject var22 = (DynamicObject)var16.field2276;

                     assert var22.field1665 == this;

                     var22.field1665 = null;
                  }
               }
            }
         }
      }

      this.method4538();
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hn")
   public void method4258(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2057; var4++) {
         for (int var5 = 0; var5 < this.field2054; var5++) {
            for (int var6 = 0; var6 < this.field2030; var6++) {
               Tile var7 = this.field2056[var4][var5][var6];
               if (var7 != null) {
                  WallObject var8 = var7.field1968;
                  if (var8 != null && var8.field2247 instanceof ModelData) {
                     ModelData var9 = (ModelData)var8.field2247;
                     this.method4509(var9, var4, var5, var6, 1, 1);
                     if (var8.field2246 instanceof ModelData) {
                        ModelData var10 = (ModelData)var8.field2246;
                        this.method4509(var10, var4, var5, var6, 1, 1);
                        ModelData.method3987(var9, var10, 0, 0, 0, false);
                        var8.field2246 = var10.method4070(var10.field1876, var10.field1877, var1, var2, var3);
                     }

                     var8.field2247 = var9.method4070(var9.field1876, var9.field1877, var1, var2, var3);
                  }

                  for (int var12 = 0; var12 < var7.field1961 * -389646721; var12++) {
                     GameObject var14 = var7.field1949[var12];
                     if (var14 != null && var14.field2198 instanceof ModelData) {
                        ModelData var11 = (ModelData)var14.field2198;
                        this.method4509(
                           var11,
                           var4,
                           var5,
                           var6,
                           var14.field2184 * -536942667 - var14.field2186 * 1138017605 + 1,
                           var14.field2190 * 738944045 - var14.field2192 * -744271729 + 1
                        );
                        var14.field2198 = var11.method4070(var11.field1876, var11.field1877, var1, var2, var3);
                     }
                  }

                  GroundObject var13 = var7.field1965;
                  if (var13 != null && var13.field1814 instanceof ModelData) {
                     ModelData var15 = (ModelData)var13.field1814;
                     method4430(this, var15, var4, var5, var6);
                     var13.field1814 = var15.method4070(var15.field1876, var15.field1877, var1, var2, var3);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hs")
   public void method4259(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2057; var4++) {
         for (int var5 = 0; var5 < this.field2054; var5++) {
            for (int var6 = 0; var6 < this.field2030; var6++) {
               Tile var7 = this.field2056[var4][var5][var6];
               if (var7 != null) {
                  WallObject var8 = var7.field1968;
                  if (var8 != null && var8.field2247 instanceof ModelData) {
                     ModelData var9 = (ModelData)var8.field2247;
                     this.method4509(var9, var4, var5, var6, 1, 1);
                     if (var8.field2246 instanceof ModelData) {
                        ModelData var10 = (ModelData)var8.field2246;
                        this.method4509(var10, var4, var5, var6, 1, 1);
                        ModelData.method3987(var9, var10, 0, 0, 0, false);
                        var8.field2246 = var10.method4070(var10.field1876, var10.field1877, var1, var2, var3);
                     }

                     var8.field2247 = var9.method4070(var9.field1876, var9.field1877, var1, var2, var3);
                  }

                  for (int var12 = 0; var12 < var7.field1961 * -389646721; var12++) {
                     GameObject var14 = var7.field1949[var12];
                     if (var14 != null && var14.field2198 instanceof ModelData) {
                        ModelData var11 = (ModelData)var14.field2198;
                        this.method4509(
                           var11,
                           var4,
                           var5,
                           var6,
                           var14.field2184 * 304142753 - var14.field2186 * 1138017605 + 1,
                           var14.field2190 * -544424899 - var14.field2192 * -555977777 + 1
                        );
                        var14.field2198 = var11.method4070(var11.field1876, var11.field1877, var1, var2, var3);
                     }
                  }

                  GroundObject var13 = var7.field1965;
                  if (var13 != null && var13.field1814 instanceof ModelData) {
                     ModelData var15 = (ModelData)var13.field1814;
                     method4430(this, var15, var4, var5, var6);
                     var13.field1814 = var15.method4070(var15.field1876, var15.field1877, var1, var2, var3);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("gy")
   public void method4232(int var1, int var2, int var3, int var4) {
      Tile var5 = this.field2056[var1][var2][var3];
      if (var5 != null) {
         DecorativeObject var6 = var5.field1969;
         if (var6 != null) {
            if (var6.field2274 * 208407183 != 0) {
               var6.field2274 = (var6.field2274 * -404927711 < 0 ? -var4 : var4) * -531053014;
            }

            if (var6.field2268 * 856605829 != 0) {
               var6.field2268 = (var6.field2268 * 1368219853 < 0 ? -var4 : var4) * 46830669;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("rr")
   public static void method4514(int var0) {
      WorldView var1 = classIS.field3051;
      Scene var2 = var1.field1687;
      if (var2.field2019.get(var0)) {
         var2.method4417();
      }

      for (int var3 = 0; var3 < var1.field1691.method14058(); var3++) {
         WorldEntity var4 = (WorldEntity)var1.field1698.method13642(var1.field1691.method14059(var3));
         var4.field6010.field1687.method4417();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("jp")
   static boolean method4320(int var0, int var1, int var2, int var3, boolean var4) {
      return method4317(field2149, var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lfv;Lrl17;Ljava/util/Set;)V")
   @ObfuscatedName("rq")
   public void method4457(Projection var1, Projection var2, rl17 var3, Set var4) {
      for (int var5 = 0; var5 < var3.field5703.size(); var5++) {
         TileObject var6 = (TileObject)var3.field5703.get(var5);
         if (var6 instanceof GameObject) {
            GameObject var7 = (GameObject)var6;

            assert var7.field2198 instanceof DynamicObject;

            DynamicObject var8 = (DynamicObject)var7.field2198;
            if (!var8.field1681
               && this.method4416(
                  var7.field2186 * 1138017605,
                  var7.field2192 * -555977777,
                  var7.field2184 * 304142753,
                  var7.field2190 * -544424899,
                  var7.field2185 * 568825275,
                  var4
               )) {
               this.method4494(
                  var1,
                  var8,
                  var7,
                  var7.field2187 * 470594389,
                  var7.field2193 * -1405731525,
                  var7.field2188 * 1269801851,
                  var7.field2194 * -1706390585,
                  var7.field2183 * 1745853678706228013L
               );
            }
         } else if (var6 instanceof GroundObject) {
            GroundObject var12 = (GroundObject)var6;

            assert var12.field1814 instanceof DynamicObject;

            DynamicObject var16 = (DynamicObject)var12.field1814;
            if (!var16.field1681) {
               int var9 = var12.field1804 * 1333275141 >> 7;
               int var10 = var12.field1806 * 498815127 >> 7;
               if (this.method4416(var9, var10, var9, var10, var12.field1812, var4)) {
                  this.method4494(
                     var1,
                     var16,
                     var12,
                     0,
                     var12.field1804 * 1333275141,
                     var12.field1805 * -276908185,
                     var12.field1806 * 498815127,
                     var12.field1813 * 5132338419383099835L
                  );
               }
            }
         } else if (var6 instanceof WallObject) {
            WallObject var13 = (WallObject)var6;
            int var17 = var13.field2242 * -777838807 >> 7;
            int var20 = var13.field2243 * 31139801 >> 7;
            if (this.method4416(var17, var20, var17, var20, var13.field2245, var4)) {
               if (var13.field2247 instanceof DynamicObject) {
                  DynamicObject var23 = (DynamicObject)var13.field2247;
                  if (!var23.field1681) {
                     this.method4494(
                        var1,
                        var23,
                        var13,
                        0,
                        var13.field2242 * -777838807,
                        var13.field2240 * 1315862339,
                        var13.field2243 * 31139801,
                        var13.field2238 * 7159317397185328723L
                     );
                  }
               }

               if (var13.field2246 instanceof DynamicObject) {
                  DynamicObject var24 = (DynamicObject)var13.field2246;
                  if (!var24.field1681) {
                     this.method4494(
                        var1,
                        var24,
                        var13,
                        0,
                        var13.field2242 * -777838807,
                        var13.field2240 * 1315862339,
                        var13.field2243 * 31139801,
                        var13.field2238 * 7159317397185328723L
                     );
                  }
               }
            }
         } else if (var6 instanceof DecorativeObject) {
            DecorativeObject var14 = (DecorativeObject)var6;
            int var18 = var14.field2273 * 1766830743 >> 7;
            int var21 = var14.field2266 * -361702377 >> 7;
            if (this.method4416(var18, var21, var18, var21, var14.field2275, var4)) {
               if (var14.field2277 instanceof DynamicObject) {
                  DynamicObject var25 = (DynamicObject)var14.field2277;
                  if (!var25.field1681) {
                     this.method4494(
                        var1,
                        var25,
                        var14,
                        0,
                        var14.field2273 * 1766830743 + var14.field2274 * -404927711,
                        var14.field2271 * 1698079433,
                        var14.field2266 * -361702377 + var14.field2268 * 856605829,
                        var14.field2264 * -3608662145112923099L
                     );
                  }
               }

               if (var14.field2276 instanceof DynamicObject) {
                  DynamicObject var26 = (DynamicObject)var14.field2276;
                  if (!var26.field1681) {
                     this.method4494(
                        var1,
                        var26,
                        var14,
                        0,
                        var14.field2273 * 1766830743 + var14.field2267 * -1085897215,
                        var14.field2271 * 1698079433,
                        var14.field2266 * -361702377 + var14.field2270 * 513256357,
                        var14.field2264 * -3608662145112923099L
                     );
                  }
               }
            }
         } else if (var6 instanceof ItemLayer) {
            ItemLayer var15 = (ItemLayer)var6;
            int var19 = var15.field1838 * -892202585 >> 7;
            int var22 = var15.field1837 * -1209739927 >> 7;
            if (this.method4416(var19, var22, var19, var22, var15.field1841, var4)) {
               if (var15.field1844 != null) {
                  TileItem var27 = (TileItem)var15.field1844;
                  Model var11 = var27.method3853();
                  if (var11 == null) {
                     continue;
                  }

                  var27.setModelHeight(var11.getModelHeight());
                  client.field1117
                     .drawDynamic(
                        -1,
                        var1,
                        this,
                        var15,
                        var27,
                        var11,
                        0,
                        var15.field1838 * -892202585,
                        var15.field1839 * 1186822141 - var15.field1836 * 218008627,
                        var15.field1837 * -1209739927
                     );
                  client.method1631(
                     var1,
                     var2,
                     var11,
                     0,
                     var15.field1838 * -892202585,
                     var15.field1839 * 1186822141 - var15.field1836 * 218008627,
                     var15.field1837 * -1209739927,
                     var15.field1842
                  );
               }

               if (var15.field1845 != null) {
                  TileItem var28 = (TileItem)var15.field1845;
                  Model var30 = var28.method3853();
                  if (var30 == null) {
                     continue;
                  }

                  var28.setModelHeight(var30.getModelHeight());
                  client.field1117
                     .drawDynamic(
                        -1,
                        var1,
                        this,
                        var15,
                        var28,
                        var30,
                        0,
                        var15.field1838 * -892202585,
                        var15.field1839 * 1186822141 - var15.field1836 * 218008627,
                        var15.field1837 * -1209739927
                     );
                  client.method1631(
                     var1,
                     var2,
                     var30,
                     0,
                     var15.field1838 * -892202585,
                     var15.field1839 * 1186822141 - var15.field1836 * 218008627,
                     var15.field1837 * -1209739927,
                     var15.field1842
                  );
               }

               if (var15.field1843 != null) {
                  TileItem var29 = (TileItem)var15.field1843;
                  Model var31 = var29.method3853();
                  if (var31 != null) {
                     var29.setModelHeight(var31.getModelHeight());
                     client.field1117
                        .drawDynamic(
                           -1,
                           var1,
                           this,
                           var15,
                           var29,
                           var31,
                           0,
                           var15.field1838 * -892202585,
                           var15.field1839 * 1186822141 - var15.field1836 * 218008627,
                           var15.field1837 * -1209739927
                        );
                     client.method1631(
                        var1,
                        var2,
                        var31,
                        0,
                        var15.field1838 * -892202585,
                        var15.field1839 * 1186822141 - var15.field1836 * 218008627,
                        var15.field1837 * -1209739927,
                        var15.field1842
                     );
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("gx")
   public void method4234(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         var4.field1968 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bv")
   public int method4517(int var1) {
      return this.field2004[var1] >> 16 & 15;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;III)V")
   @ObfuscatedName("gh")
   public static void method4235(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method4262(var1, var1, var1);
      } else {
         Tile var4 = var0.field2056[var1][var2][var3];
         if (var4 != null) {
            var4.field1968 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ge")
   public void method4236(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         var4.field1969 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hz")
   public void method4237(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         var4.field1969 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("dj")
   boolean method4402(int var1, int var2, int var3, int var4) {
      if (!this.method4399(var1, var2, var3)) {
         return false;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         int var7 = this.field2058[var1][var2][var3] - 1;
         int var8 = var7 - 120;
         int var9 = var7 - 1185577113;
         int var10 = var7 - 238;
         if (var4 < 16) {
            if (var4 == 1) {
               if (var5 > this.field2116) {
                  if (!this.method4411(var5, var7, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5, var7, var6 + 128)) {
                     return false;
                  }
               }

               if (var1 > 0) {
                  if (!this.method4411(var5, var8, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5, var8, var6 + 128)) {
                     return false;
                  }
               }

               if (!this.method4411(var5, var9, var6)) {
                  return false;
               }

               if (!this.method4411(var5, var9, var6 + -1976571263)) {
                  return false;
               }

               return true;
            }

            if (var4 == 2) {
               if (var6 < this.field2114) {
                  if (!this.method4411(var5, var7, var6 + 128)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var7, var6 + -854857873)) {
                     return false;
                  }
               }

               if (var1 > 0) {
                  if (!this.method4411(var5, var8, var6 + 128)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var8, var6 + -162860707)) {
                     return false;
                  }
               }

               if (!this.method4411(var5, var9, var6 + 128)) {
                  return false;
               }

               if (!this.method4411(var5 + 128, var9, var6 + 128)) {
                  return false;
               }

               return true;
            }

            if (var4 == 4) {
               if (var5 < this.field2116) {
                  if (!this.method4411(var5 + -1829215160, var7, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var7, var6 + 128)) {
                     return false;
                  }
               }

               if (var1 > 0) {
                  if (!this.method4411(var5 + -730909357, var8, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5 + -1138970473, var8, var6 + 128)) {
                     return false;
                  }
               }

               if (!this.method4411(var5 + 128, var9, var6)) {
                  return false;
               }

               if (!this.method4411(var5 + 2064648336, var9, var6 + 128)) {
                  return false;
               }

               return true;
            }

            if (var4 == 8) {
               if (var6 > this.field2114) {
                  if (!this.method4411(var5, var7, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var7, var6)) {
                     return false;
                  }
               }

               if (var1 > 0) {
                  if (!this.method4411(var5, var8, var6)) {
                     return false;
                  }

                  if (!this.method4411(var5 + 128, var8, var6)) {
                     return false;
                  }
               }

               if (!this.method4411(var5, var9, var6)) {
                  return false;
               }

               if (!this.method4411(var5 + 1183211869, var9, var6)) {
                  return false;
               }

               return true;
            }
         }

         if (!this.method4411(var5 + 1783224692, var10, var6 + 57621730)) {
            return false;
         } else if (var4 == 16) {
            return this.method4411(var5, var9, var6 + 128);
         } else if (var4 == 32) {
            return this.method4411(var5 + 128, var9, var6 + 850464331);
         } else if (var4 == 497147507) {
            return this.method4411(var5 + -2049992861, var9, var6);
         } else {
            return var4 == 1871189580 ? this.method4411(var5, var9, var6) : true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hi")
   public void method4241(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         for (int var5 = 0; var5 < var4.field1961 * -389646721; var5++) {
            GameObject var6 = var4.field1949[var5];
            long var8 = var6.field2183 * 1745853678706228013L;
            boolean var7 = classCV.method2679(var8) == 2;
            if (var7 && var6.field2186 * 1138017605 == var2 && var6.field2192 * -555977777 == var3) {
               this.method4475(var6);
               return;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;)V")
   @ObfuscatedName("fg")
   public static void method4179(Scene var0) {
      if (var0 == null) {
         var0.method4152();
      }

      for (int var1 = 0; var1 < var0.field2057; var1++) {
         for (int var2 = 0; var2 < var0.field2054; var2++) {
            for (int var3 = 0; var3 < var0.field2030; var3++) {
               var0.field2056[var1][var2][var3] = null;
            }
         }
      }

      if (var0.field2061 != null) {
         for (int var4 = 0; var4 < var0.field2036; var4++) {
            for (int var7 = 0; var7 < var0.field2061[var4]; var7++) {
               var0.field2037[var4][var7] = null;
            }

            var0.field2061[var4] = 0;
         }
      }

      if (var0.field2046 != null) {
         Arrays.fill(var0.field2046, null);
      }

      var0.field2107 = 0;

      for (int var5 = 0; var5 < var0.field2048; var5++) {
         var0.field2055[var5] = null;
      }

      var0.field2048 = 0;

      for (int var6 = 0; var6 < var0.field2023.length; var6++) {
         var0.field2023[var6] = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ht")
   public void method4242(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         for (int var5 = 0; var5 < var4.field1961 * -389646721; var5++) {
            GameObject var6 = var4.field1949[var5];
            long var8 = var6.field2183 * 1745853678706228013L;
            boolean var7 = classCV.method2679(var8) == 2;
            if (var7 && var6.field2186 * 1138017605 == var2 && var6.field2192 * 1819687021 == var3) {
               this.method4475(var6);
               return;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("dp")
   boolean method4411(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2107; var4++) {
         classEP var5 = this.field2046[var4];
         if (var5.field1830 * 289281065 == 1) {
            int var6 = var5.field1815 * -996295335 - var1;
            if (var6 > 0) {
               int var7 = var5.field1818 * -832449137 + (var5.field1826 * 149581763 * var6 >> 8);
               int var8 = var5.field1827 * -371025921 + (var5.field1820 * -1009677011 * var6 >> 8);
               int var9 = var5.field1829 * 318014867 + (var5.field1822 * -1408907851 * var6 >> 8);
               int var10 = var5.field1821 * -971837087 + (var5.field1824 * 309256757 * var6 >> 8);
               if (var3 >= var7 && var3 <= var8 && var2 >= var9 && var2 <= var10) {
                  return true;
               }
            }
         } else if (var5.field1830 * 289281065 == 2) {
            int var11 = var1 - var5.field1815 * -996295335;
            if (var11 > 0) {
               int var15 = var5.field1818 * -832449137 + (var5.field1826 * 149581763 * var11 >> 8);
               int var19 = var5.field1827 * -371025921 + (var5.field1820 * -1009677011 * var11 >> 8);
               int var23 = var5.field1829 * 318014867 + (var5.field1822 * -1408907851 * var11 >> 8);
               int var27 = var5.field1821 * -971837087 + (var5.field1824 * 309256757 * var11 >> 8);
               if (var3 >= var15 && var3 <= var19 && var2 >= var23 && var2 <= var27) {
                  return true;
               }
            }
         } else if (var5.field1830 * 289281065 == 3) {
            int var12 = var5.field1818 * -832449137 - var3;
            if (var12 > 0) {
               int var16 = var5.field1815 * -996295335 + (var5.field1823 * 1759679165 * var12 >> 8);
               int var20 = var5.field1832 * 1506923105 + (var5.field1817 * -1152058757 * var12 >> 8);
               int var24 = var5.field1829 * 318014867 + (var5.field1822 * -1408907851 * var12 >> 8);
               int var28 = var5.field1821 * -971837087 + (var5.field1824 * 309256757 * var12 >> 8);
               if (var1 >= var16 && var1 <= var20 && var2 >= var24 && var2 <= var28) {
                  return true;
               }
            }
         } else if (var5.field1830 * 289281065 == 4) {
            int var13 = var3 - var5.field1818 * -832449137;
            if (var13 > 0) {
               int var17 = var5.field1815 * -996295335 + (var5.field1823 * 1759679165 * var13 >> 8);
               int var21 = var5.field1832 * 1506923105 + (var5.field1817 * -1152058757 * var13 >> 8);
               int var25 = var5.field1829 * 318014867 + (var5.field1822 * -1408907851 * var13 >> 8);
               int var29 = var5.field1821 * -971837087 + (var5.field1824 * 309256757 * var13 >> 8);
               if (var1 >= var17 && var1 <= var21 && var2 >= var25 && var2 <= var29) {
                  return true;
               }
            }
         } else if (var5.field1830 * 289281065 == 5) {
            int var14 = var2 - var5.field1829 * 318014867;
            if (var14 > 0) {
               int var18 = var5.field1815 * -996295335 + (var5.field1823 * 1759679165 * var14 >> 8);
               int var22 = var5.field1832 * 1506923105 + (var5.field1817 * -1152058757 * var14 >> 8);
               int var26 = var5.field1818 * -832449137 + (var5.field1826 * 149581763 * var14 >> 8);
               int var30 = var5.field1827 * -371025921 + (var5.field1820 * -1009677011 * var14 >> 8);
               if (var1 >= var18 && var1 <= var22 && var3 >= var26 && var3 <= var30) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("iv")
   public boolean method4533(int var1, int var2, int var3, int var4) {
      return this.method4401(var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Lez;IIII)V")
   @ObfuscatedName("lb")
   public static void method4502(Scene var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method4503(var1, var1, var1, var1);
      } else {
         var2 += var0.field2146;
         var3 += var0.field2146;
         int var5 = var0.method4438(var1, var2, var3);
         if (var0.method4426(var5)) {
            DecorativeObject var6 = var0.field2008[var5];
            if (var6 != null) {
               if (var6.field2274 * -404927711 != 0) {
                  var6.field2274 = (var6.field2274 * -404927711 < 0 ? -var4 : var4) * 261748961;
               }

               if (var6.field2268 * 856605829 != 0) {
                  var6.field2268 = (var6.field2268 * 856605829 < 0 ? -var4 : var4) * 46830669;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("mk")
   public boolean method4534(int var1, int var2, int var3, int var4) {
      return this.method4404(var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hl")
   public void method4244(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         var4.field1965 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("mq")
   @Override
   void vmethod414(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      classUU var15 = classRM.method10172(1778071523);
      classUG var16 = classMQ.method7682(1049769988);
      classUL var17 = var16.field6303;
      int var19 = var1 & 2047;
      float var18 = (float)(var19 / 2048.0F * (Math.PI * 2));
      var17.method11437(var18, 0.0F, 0.0F, -369519070);
      classUZ.method11802(var16.field6305, var4, var5, var6, 2011546842);
      classUG var20 = classMQ.method7682(-1209686922);
      classUZ.method11802(var20.field6305, -(this.field2054 * 64) - this.field2027, this.field2028, -(this.field2030 * 64) - this.field2045, 2066399031);
      classUU var21 = classRM.method10172(1778071523);
      var21.method11706(var20, 1997825264);
      var15.method11675(1.0F, this.field2053, 1.0F, 1.0F, -1412584499);
      var21.method11656(var15, 88268549);
      var21.method11656(this.field2026, -1637257785);
      var15.method11706(var16, 1640394600);
      var21.method11656(var15, -68256233);
      classUZ.method11857(var15, var21, (byte)-78);
      classUU.method11670(var15, -516551499);
      this.field2116 = (int)classUU.method11685(var15, var7, var8, var9, (byte)88);
      this.field2117 = (int)var15.method11687(var7, var8, var9, -286689790);
      this.field2114 = (int)var15.method11689(var7, var8, var9, -1162982420);
      this.field2112 = this.field2116 / 128;
      this.field2122 = this.field2114 / 128;
      this.field2119 = (int)classUU.method11685(var15, var12, 0.0F, var13, (byte)-53) / 128;
      this.field2123 = (int)var15.method11689(var12, 0.0F, var13, 1469205599) / 128;
      classUX var22 = new classUX();
      classUU var23 = classRM.method10172(1778071523);
      var22.method11748(-var2.method11503(-1294028644), 173632492);
      var22.method11753(-var3.method11503(-723957400), 982132619);
      classUX.method11760(var22, var7, var8, var9, (byte)5);
      classUU.method11668(var23, var22, -1959054899);
      classUU.method11670(var23, -1904700161);
      var16.method11364(-857745384);
      var20.method11364(838740671);
      var15.method11615(2076326646);
      var21.method11656(var23, 994612139);
      FloatProjection var24 = new FloatProjection(var21);
      FloatProjection.method3908(var24, var10);
      classTW.method11268((byte)-30);
      this.method4454(var24, var14, classFH.field2255.field2215 != 1);
      classTW.method11268((byte)56);
      var23.method11615(2143193220);
      var21.method11615(1704783583);
   }

   @ObfuscatedSignature(descriptor = "(III)Lfe;")
   @ObfuscatedName("hf")
   public WallObject method4246(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      return var4 == null ? null : var4.field1968;
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("kw")
   boolean method4405(int var1, int var2, int var3, int var4) {
      if (!this.method4399(var1, var2, var3)) {
         return false;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         return this.method4411(var5 + 1, this.field2058[var1][var2][var3] - var4, var6 + 1)
            && this.method4411(var5 + 128 - 1, this.field2058[var1][var2 + 1][var3] - var4, var6 + 1)
            && this.method4411(var5 + 128 - 1, this.field2058[var1][var2 + 1][var3 + 1] - var4, var6 + 128 - 1)
            && this.method4411(var5 + 1, this.field2058[var1][var2][var3 + 1] - var4, var6 + 128 - 1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Lfv;Lew;)V")
   @ObfuscatedName("oz")
   public static void method4388(Scene var0, Projection var1, Tile var2) {
      if (var0 == null) {
         var0.method4511(var1);
      } else if (var2 != null) {
         int var3 = var2.field1950 * -54806681;
         int var4 = var2.field1955 * -2067052011;
         int var5 = var2.field1947 * 860707211;
         int var6 = var2.field1948 * 800564741;
         if (var2.field1947 * -1631770904 == 0 && var2.field1967 != null) {
            var0.method4423(var1, var2.field1967);
         }

         if (var2.field1970 != null) {
            if (!var0.method4399(var6, var3, var4) && (var2.field1970.field2284 * -665850825 != -1426952059 || var5 <= var0.field2042)) {
               var1.vmethod426(var0, var2.field1970, var6, var3, var4, -1478884889);
            }
         } else if (var2.field1971 != null && !var0.method4399(var6, var3, var4)) {
            var1.vmethod431(var0, var2.field1971, var3, var4, -1622535918);
         }

         WallObject var7 = var2.field1968;
         if (var7 != null) {
            if (var7.field2247 != null && !var0.method4401(var6, var3, var4, var7.field2244 * -934006316)) {
               var1.vmethod435(
                  var7.field2247, 0, var7.field2242 * -777838807, var7.field2240 * 1288676392, var7.field2243 * 31139801, var7.field2238 * 7159317397185328723L
               );
            }

            if (var7.field2246 != null && !var0.method4401(var6, var3, var4, var7.field2241 * -1413337351)) {
               var1.vmethod435(
                  var7.field2246,
                  0,
                  var7.field2242 * -777838807,
                  var7.field2240 * -1515157587,
                  var7.field2243 * -1252193898,
                  var7.field2238 * 7159317397185328723L
               );
            }
         }

         DecorativeObject var8 = var2.field1969;
         if (var8 != null && !var0.method4404(var6, var3, var4, var8.field2277.field1741 * -1952736422)) {
            if (var8.field2269 * 846961611 == -1481471005) {
               var1.vmethod435(
                  var8.field2277,
                  0,
                  var8.field2273 * 1766830743 + var8.field2274 * -404927711,
                  var8.field2271 * 1698079433,
                  var8.field2266 * -361702377 + var8.field2268 * 40184986,
                  var8.field2264 * -3608662145112923099L
               );
               if (var8.field2276 != null) {
                  var1.vmethod435(
                     var8.field2276,
                     0,
                     var8.field2273 * 1766830743 + var8.field2267 * 1455253427,
                     var8.field2271 * -1366991944,
                     var8.field2266 * 1346123655 + var8.field2270 * 828654353,
                     var8.field2264 * -3608662145112923099L
                  );
               }
            } else {
               var1.vmethod435(
                  var8.field2277,
                  0,
                  var8.field2273 * -882763528 + var8.field2274 * -404927711,
                  var8.field2271 * 1698079433,
                  var8.field2266 * 1588533613 + var8.field2268 * 856605829,
                  var8.field2264 * -3608662145112923099L
               );
            }
         }

         GroundObject var9 = var2.field1965;
         if (var9 != null) {
            var1.vmethod435(
               var9.field1814, 0, var9.field1804 * -1726810846, var9.field1805 * -276908185, var9.field1806 * 498815127, var9.field1813 * 5132338419383099835L
            );
         }

         ItemLayer var10 = var2.field1966;
         if (var10 != null) {
            if (var10.field1844 != null) {
               var1.vmethod435(
                  var10.field1844,
                  0,
                  var10.field1838 * -892202585,
                  var10.field1839 * 1186822141 - var10.field1836 * 218008627,
                  var10.field1837 * 270985406,
                  var10.field1842
               );
            }

            if (var10.field1845 != null) {
               var1.vmethod435(
                  var10.field1845,
                  0,
                  var10.field1838 * -892202585,
                  var10.field1839 * 1186822141 - var10.field1836 * 218008627,
                  var10.field1837 * -1209739927,
                  var10.field1842
               );
            }

            if (var10.field1843 != null) {
               var1.vmethod435(
                  var10.field1843,
                  0,
                  var10.field1838 * -405788651,
                  var10.field1839 * 1186822141 - var10.field1836 * 1952365616,
                  var10.field1837 * -1209739927,
                  var10.field1842
               );
            }
         }

         for (int var11 = 0; var11 < var2.field1961 * 1995264333; var11++) {
            GameObject var12 = var2.field1949[var11];
            var12.field2195 = var0.field2063 * 2048564324;
            if (var12.field2186 * -509062758 == var2.field1950 * 821923362
               && var12.field2192 * 573032171 == var2.field1955 * 1436652264
               && !var0.method4406(
                  var6,
                  var12.field2186 * 1138017605,
                  var12.field2184 * -767005432,
                  var12.field2192 * 366117412,
                  var12.field2190 * -544424899,
                  var12.field2198.field1741 * -1256242689
               )) {
               try {
                  var1.vmethod435(
                     var12.field2198,
                     var12.field2187 * 470594389,
                     var12.field2193 * -1405731525,
                     var12.field2188 * 1269801851,
                     var12.field2194 * 1739635682,
                     var12.field2183 * 1745853678706228013L
                  );
               } catch (Exception var15) {
               }
            }
         }

         if (var2.method4122((short)5606)) {
            for (int var16 = 0; var16 < var2.field1953.size(); var16++) {
               GameObject var17 = (GameObject)var2.field1953.get(var16);
               var17.field2195 = var0.field2063 * 1946200923;
               if (!var0.method4406(
                  var6,
                  var2.field1950 * 1817344549,
                  var2.field1950 * -817853095,
                  var2.field1955 * -2067052011,
                  var2.field1955 * 550001787,
                  var17.field2198.field1741 * -1256242689
               )) {
                  try {
                     var1.vmethod435(
                        var17.field2198,
                        var17.field2187 * 470594389,
                        var17.field2193 * -1556881435,
                        var17.field2188 * -985902500,
                        var17.field2194 * -1706390585,
                        var17.field2183 * 1745853678706228013L
                     );
                  } catch (Exception var14) {
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hw")
   public void method4238(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         var4.field1969 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Leo;")
   @ObfuscatedName("ha")
   public GroundObject method4249(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      return var4 != null && var4.field1965 != null ? var4.field1965 : null;
   }

   @ObfuscatedSignature(descriptor = "(III)Leo;")
   @ObfuscatedName("ho")
   public GroundObject method4250(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      return var4 != null && var4.field1965 != null ? var4.field1965 : null;
   }

   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("he")
   public long method4251(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      return var4 != null && var4.field1969 != null ? var4.field1969.field2264 * -3608662145112923099L : 0L;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("jw")
   static boolean method4321(int var0, int var1, int var2, int var3, boolean var4) {
      return method4317(field2149, var0, var1, var2, var3, var4);
   }

   public void setRoofRemovalMode(int var1) {
      this.field1986 = var1;
   }

   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("cb")
   public long method4253(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 == null) {
         return 0L;
      } else {
         for (int var5 = 0; var5 < var4.field1961 * -1577096966; var5++) {
            GameObject var6 = var4.field1949[var5];
            long var8 = var6.field2183 * 1745853678706228013L;
            boolean var7 = classCV.method2679(var8) == 2;
            if (var7 && var6.field2186 * 1223384850 == var2 && var6.field2192 * -555977777 == var3) {
               return var6.field2183 * 1745853678706228013L;
            }
         }

         return 0L;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("ct")
   public long method4255(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      return var4 != null && var4.field1965 != null ? var4.field1965.field1813 * 5132338419383099835L : 0L;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)I")
   @ObfuscatedName("eg")
   public static int method4165(boolean var0) {
      return var0 ? 1 : 1024;
   }

   @ObfuscatedSignature(descriptor = "(IIIJ)I")
   @ObfuscatedName("hv")
   public int method4256(int var1, int var2, int var3, long var4) {
      Tile var6 = this.field2056[var1][var2][var3];
      if (var6 == null) {
         return -1;
      } else if (var6.field1968 != null && var6.field1968.field2238 * 7159317397185328723L == var4) {
         return var6.field1968.field2239 * 1869636241 & 0xFF;
      } else if (var6.field1969 != null && var6.field1969.field2264 * -3608662145112923099L == var4) {
         return var6.field1969.field2263 * -1506294211 & 0xFF;
      } else if (var6.field1965 != null && var6.field1965.field1813 * 5132338419383099835L == var4) {
         return GroundObject.method3958(var6.field1965, 595746927) & 0xFF;
      } else {
         for (int var7 = 0; var7 < var6.field1961 * -389646721; var7++) {
            if (var6.field1949[var7].field2183 * 1745853678706228013L == var4) {
               return var6.field1949[var7].field2182 * -288062455 & 0xFF;
            }
         }

         return -1;
      }
   }

   public boolean isInstance() {
      return this.field2153;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("ja")
   static boolean method4304(int var0, int var1, int var2, int var3) {
      return method4291(field2151, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lfv;Lrl17;)V")
   @ObfuscatedName("ih")
   public void method4471(Projection var1, Projection var2, rl17 var3) {
      if (!var3.field5704.isEmpty()) {
         rl16.field5700 = var1;
         var3.field5704.sort(field2075);

         for (int var4 = 0; var4 < var3.field5704.size(); var4++) {
            GameObject var5 = (GameObject)var3.field5704.get(var4);
            if (!(var5.field2198 instanceof Scene)) {
               Renderable var6 = var5.field2198;

               Model var7;
               try {
                  var7 = var6 instanceof Model ? (Model)var6 : var6.method3853();
                  if (var7 == null) {
                     continue;
                  }
               } catch (Exception var9) {
                  client.field1028.debug("exception drawing game entity", var9);
                  continue;
               }

               if (var7 != var6) {
                  var6.setModelHeight(var7.getModelHeight());
               }

               int var8 = var6.getAnimationHeightOffset();
               client.field1117
                  .drawTemp(
                     var1,
                     this,
                     var5,
                     var7,
                     var5.field2187 * 470594389,
                     var5.field2193 * -1405731525,
                     var5.field2188 * 1269801851 - var8,
                     var5.field2194 * -1706390585
                  );
               client.method1631(
                  var1,
                  var2,
                  var7,
                  var5.field2187 * 470594389,
                  var5.field2193 * -1405731525,
                  var5.field2188 * 1269801851 - var8,
                  var5.field2194 * -1706390585,
                  var5.field2183 * 1745853678706228013L
               );
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIII)Z")
   @ObfuscatedName("kt")
   static boolean method4351(int var0, int var1, int var2, int var3, int var4) {
      int var5 = classBF.method1000(var3, (byte)-120);
      int var7 = classLL.method7162(var3, 1222866659);
      int var8 = classBF.method1000(var4, (byte)-85);
      int var10 = classLL.method7162(var4, 1222866659);
      int var11 = var2 * var8 + var0 * var10 >> 16;
      int var12 = var2 * var10 - var0 * var8 >> 16;
      int var13 = var1 * var5 + var12 * var7 >> 16;
      int var14 = var1 * var7 - var12 * var5 >> 16;
      if (var13 >= -1610945124 && var13 <= classFQ.method4948()) {
         int var15 = field2137 + var11 * -770857664 / var13;
         int var16 = field2127 + var14 * 128 / var13;
         return var15 >= field2135 && var15 <= field2133 && var16 >= field2134 && var16 <= field2129;
      } else {
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("kg")
   static void method4336(int var0, int var1) {
      if (var0 < 0) {
         var0 = 0;
      }

      field2140 = var0;
      field2138 = var1;
   }

   @ObfuscatedSignature(descriptor = "(III)Leo;")
   @ObfuscatedName("xe")
   public GroundObject method4535(int var1, int var2, int var3) {
      return this.method4468(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("xm")
   public void method4432(int var1, int var2, int var3) {
      if (var2 >= 0 && var2 < this.field2054 && var3 >= 0 && var3 < this.field2030) {
         int var4 = this.method4438(var1, var2, var3);
         if (this.method4426(var4)) {
            this.method4481(var4);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;III)V")
   @ObfuscatedName("is")
   void method4263(ModelData var1, int var2, int var3, int var4) {
      int var6 = var3 + 1;
      int var7 = var4 - 1;
      int var8 = var4 + 1;

      for (int var9 = var3; var9 <= var6; var9++) {
         if (var9 >= 0 && var9 < this.field2054) {
            for (int var10 = var7; var10 <= var8; var10++) {
               if (var10 >= 0 && var10 < this.field2030 && (var9 >= var6 || var10 >= var8)) {
                  Tile var11 = this.field2056[var2][var9][var10];
                  if (var11 != null && var11.field1965 != null && var11.field1965.field1814 instanceof ModelData) {
                     int var12 = this.method4269(var2, var9, var10, var2, var3, var4);
                     ModelData var13 = (ModelData)var11.field1965.field1814;
                     ModelData.method3987(var1, var13, (var9 - var3) * 128, var12, (var10 - var4) * 128, true);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;III)V")
   @ObfuscatedName("ir")
   void method4264(ModelData var1, int var2, int var3, int var4) {
      int var6 = var3 + 1;
      int var7 = var4 - 1;
      int var8 = var4 + 1;

      for (int var9 = var3; var9 <= var6; var9++) {
         if (var9 >= 0 && var9 < this.field2054) {
            for (int var10 = var7; var10 <= var8; var10++) {
               if (var10 >= 0 && var10 < this.field2030 && (var9 >= var6 || var10 >= var8)) {
                  Tile var11 = this.field2056[var2][var9][var10];
                  if (var11 != null && var11.field1965 != null && var11.field1965.field1814 instanceof ModelData) {
                     int var12 = this.method4269(var2, var9, var10, var2, var3, var4);
                     ModelData var13 = (ModelData)var11.field1965.field1814;
                     ModelData.method3987(var1, var13, (var9 - var3) * 128, var12, (var10 - var4) * 128, true);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;IIIII)V")
   @ObfuscatedName("ie")
   void method4267(ModelData var1, int var2, int var3, int var4, int var5, int var6) {
      boolean var7 = true;
      int var8 = var3;
      int var9 = var3 + var5;
      int var10 = var4 - 1;
      int var11 = var4 + var6;

      for (int var12 = var2; var12 <= var2 + 1; var12++) {
         if (var12 != this.field2057) {
            for (int var13 = var8; var13 <= var9; var13++) {
               if (var13 >= 0 && var13 < this.field2054) {
                  for (int var14 = var10; var14 <= var11; var14++) {
                     if (var14 >= 0 && var14 < this.field2030 && (!var7 || var13 >= var9 || var14 >= var11 || var14 < var4 && var13 != var3)) {
                        Tile var15 = this.field2056[var12][var13][var14];
                        if (var15 != null) {
                           int var16 = this.method4269(var12, var13, var14, var2, var3, var4);
                           WallObject var17 = var15.field1968;
                           if (var17 != null) {
                              if (var17.field2247 instanceof ModelData) {
                                 ModelData var18 = (ModelData)var17.field2247;
                                 ModelData.method3987(
                                    var1,
                                    var18,
                                    (var13 - var3) * 550629966 + (1 - var5) * 709963166,
                                    var16,
                                    (var14 - var4) * 128 + (1 - var6) * -678320273,
                                    var7
                                 );
                              }

                              if (var17.field2246 instanceof ModelData) {
                                 ModelData var23 = (ModelData)var17.field2246;
                                 ModelData.method3987(
                                    var1,
                                    var23,
                                    (var13 - var3) * -724273396 + (1 - var5) * 1049222396,
                                    var16,
                                    (var14 - var4) * 128 + (1 - var6) * -603634666,
                                    var7
                                 );
                              }
                           }

                           for (int var24 = 0; var24 < var15.field1961 * -1229198320; var24++) {
                              GameObject var19 = var15.field1949[var24];
                              if (var19 != null && var19.field2198 instanceof ModelData) {
                                 ModelData var20 = (ModelData)var19.field2198;
                                 int var21 = var19.field2184 * 304142753 - var19.field2186 * -1559065218 + 1;
                                 int var22 = var19.field2190 * -544424899 - var19.field2192 * 1881335404 + 1;
                                 ModelData.method3987(
                                    var1,
                                    var20,
                                    (var19.field2186 * 1779806614 - var3) * 128 + (var21 - var5) * 64,
                                    var16,
                                    (var19.field2192 * -555977777 - var4) * 128 + (var22 - var6) * 194306734,
                                    var7
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            }

            var8--;
            var7 = false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("ih")
   int method4270(int var1, int var2, int var3, int var4, int var5, int var6) {
      return method4277(this, var1, var2, var3) - method4277(this, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("ig")
   int method4271(int var1, int var2, int var3, int var4, int var5, int var6) {
      return method4277(this, var1, var2, var3) - method4277(this, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("fr")
   public boolean method4536(int var1, int var2, int var3) {
      return this.method4399(var1, var2, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;I)V")
   @ObfuscatedName("cj")
   public static void method4376(Scene var0, int var1) {
      if (var0 == null) {
         var0.method4378(var1);
      }

      var0.field2143 = var1;
      var0.field2024 = -1;
      var0.field2032 = -1;
      var0.field2040 = -1.0F;
   }

   public void setMinLevel(int var1) {
      this.field2038 = var1;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("cy")
   int method4274(int var1, int var2, int var3) {
      return (
            this.field2058[var1][var2][var3]
               + this.field2058[var1][var2 + 1][var3]
               + this.field2058[var1][var2][var3 + 1]
               + this.field2058[var1][var2 + 1][var3 + 1]
         )
         / 4;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lp")
   public void method4372() {
      this.field2034 = -1;
      this.field2031 = false;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dm")
   static void method4337(int var0, int var1) {
      if (var0 < 0) {
         var0 = 0;
      }

      field2140 = var0;
      field2138 = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public void method4455() {
      Arrays.fill(this.field2004, 0);
      Arrays.fill(this.field2005, null);
      Arrays.fill(this.field2006, null);
      Arrays.fill(this.field2007, null);
      Arrays.fill(this.field2008, null);
      Arrays.fill(this.field2009, null);
      Arrays.fill(this.field2010, null);
      Arrays.fill(this.field2012, null);
      Arrays.fill(this.field2011, (byte)0);
      Arrays.fill(this.field2023, null);

      for (int var1 = 0; var1 < this.field2054 >> 3; var1++) {
         for (int var2 = 0; var2 < this.field2030 >> 3; var2++) {
            rl17 var3 = this.field2017[var1][var2];
            var3.field5703.clear();
            var3.field5704.clear();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("cu")
   public long method4252(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      return var4 != null && var4.field1969 != null ? var4.field1969.field2264 * -3608662145112923099L : 0L;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cs")
   public void method4459(int var1, int var2) {
      this.field2004[var2] = this.field2004[var1];
      this.field2005[var2] = this.field2005[var1];
      this.field2006[var2] = this.field2006[var1];
      this.field2007[var2] = this.field2007[var1];
      this.field2008[var2] = this.field2008[var1];
      this.field2009[var2] = this.field2009[var1];
      this.field2010[var2] = this.field2010[var1];
      this.field2011[var2] = this.field2011[var1];

      for (int var3 = 0; var3 < 5; var3++) {
         this.field2012[var2 * 5 + var3] = this.field2012[var1 * 5 + var3];
         this.field2013[var2 * 5 + var3] = this.field2013[var1 * 5 + var3];
      }

      this.field2014[var2] = this.field2014[var1];
   }

   @ObfuscatedSignature(descriptor = "([IIIIII)V")
   @ObfuscatedName("if")
   public void method4280(int[] var1, int var2, int var3, int var4, int var5, int var6) {
      Tile var7 = this.field2056[var4][var5][var6];
      if (var7 != null) {
         SceneTilePaint var8 = var7.field1970;
         if (var8 != null) {
            int var18 = var8.field2287 * 1843846065;
            if (var18 != 0) {
               for (int var19 = 0; var19 < 4; var19++) {
                  var1[var2] = var18;
                  var1[var2 + 1] = var18;
                  var1[var2 + 2] = var18;
                  var1[var2 + 3] = var18;
                  var2 += var3;
               }
            }
         } else {
            SceneTileModel var9 = var7.field1971;
            if (var9 != null) {
               int var10 = var9.field2318;
               int var11 = var9.field2324;
               int var12 = var9.field2331;
               int var13 = var9.field2319;
               int[] var14 = field2082[var10];
               int[] var15 = field2078[var11];
               int var16 = 0;
               if (var12 != 0) {
                  for (int var17 = 0; var17 < 4; var17++) {
                     var1[var2] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var1[var2 + 1] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var1[var2 + 2] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var1[var2 + 3] = var14[var15[var16++]] == 0 ? var12 : var13;
                     var2 += var3;
                  }
               } else {
                  for (int var26 = 0; var26 < 4; var26++) {
                     if (var14[var15[var16++]] != 0) {
                        var1[var2] = var13;
                     }

                     if (var14[var15[var16++]] != 0) {
                        var1[var2 + 1] = var13;
                     }

                     if (var14[var15[var16++]] != 0) {
                        var1[var2 + 2] = var13;
                     }

                     if (var14[var15[var16++]] != 0) {
                        var1[var2 + 3] = var13;
                     }

                     var2 += var3;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;Lee;IIJI)V")
   @ObfuscatedName("wu")
   public void method4473(int var1, int var2, int var3, int var4, Renderable var5, Renderable var6, int var7, int var8, long var9, int var11) {
      int var12 = var2 + this.field2146;
      int var13 = var3 + this.field2146;
      if (var5 != null || var6 != null) {
         int var14 = this.method4438(var1, var12, var13);
         WallObject var15 = new WallObject();
         var15.method4649(var9);
         var15.method4648(var11);
         var15.method4646(var2 * 128 + 64);
         var15.method4650(var3 * 128 + 64);
         var15.method4651(var4);
         var15.method4642(var5);
         var15.method4652(var6);
         var15.method4647(var7);
         var15.method4643(var8);

         for (int var16 = var1; var16 >= 0; var16--) {
            int var17 = this.method4438(var16, var12, var13);
            if (!this.method4426(var17)) {
               this.method4483(var17, var16, var12, var13);
            }

            if (this.field1991[var16][var12][var13] == null) {
               this.field1991[var16][var12][var13] = new rl10(this, var17);
               this.method4456(var16, var12, var13);
            }
         }

         assert this.field2007[var14] == null;

         this.field2007[var14] = var15;
         this.field2004[var14] = this.field2004[var14] | 16384;
         this.field2015[var14] = this.field2015[var14] | 1080863910568919040L;
         if (var5 instanceof DynamicObject || var6 instanceof DynamicObject) {
            rl17 var20 = this.field2017[var12 >> 3][var13 >> 3];
            var20.field5703.add(var15);
            this.method4348((DynamicObject)var5);
            this.method4348((DynamicObject)var6);
         }

         this.method4513(var5, 0, var15.getX(), var15.getZ(), var15.getY(), var2, var3);
         this.method4513(var6, 0, var15.getX(), var15.getZ(), var15.getY(), var2, var3);
         var15.field2245 = var1;
         if (this.field2152) {
            WallObjectSpawned var21 = new WallObjectSpawned();
            var21.setTile(this.field1991[var1][var12][var13]);
            var21.setWallObject(var15);
            classOE.field4841.getCallbacks().post(var21);
         }

         if (this.field2152 && client.field1117 != null) {
            client.field1117.invalidateZone(this, var12 >> 3, var13 >> 3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lex;)V")
   @ObfuscatedName("io")
   public void method4285(classEX var1) {
      if (var1 != this.field2050) {
         this.field2050 = var1;
         method4333(field2070, field2128, field2124, field2133, field2129);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hb")
   public void method4518(int var1) {
      if (var1 != 0 && var1 >> 28 == 0) {
         this.method4484(var1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("iv")
   public static void method4287(int var0) {
      int var1 = (field2069 - 1) * field2067 * (var0 + var0 + 1) * (var0 + var0 + 1);
      field2148 = new BitSet(var1);
      field2151 = new BitSet(var1);
      field2136 = field2067 * (var0 + var0 + 1) * (var0 + var0 + 1);
      field2131 = (var0 + var0 + 1) * (var0 + var0 + 1);
      field2126 = var0 + var0 + 1;
      int var2 = field2069 * field2067 * (var0 + var0 + 3) * (var0 + var0 + 3);
      field2149 = new BitSet(var2);
      field2150 = new BitSet(var2);
      field2130 = field2067 * (var0 + var0 + 3) * (var0 + var0 + 3);
      field2132 = (var0 + var0 + 3) * (var0 + var0 + 3);
      field2125 = var0 + var0 + 3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("iq")
   public static void method4288(int var0) {
      int var1 = (field2069 - 1) * field2067 * (var0 + var0 + 1) * (var0 + var0 + 1);
      field2148 = new BitSet(var1);
      field2151 = new BitSet(var1);
      field2136 = field2067 * (var0 + var0 + 1) * (var0 + var0 + 1);
      field2131 = (var0 + var0 + 1) * (var0 + var0 + 1);
      field2126 = var0 + var0 + 1;
      int var2 = field2069 * field2067 * (var0 + var0 + 3) * (var0 + var0 + 3);
      field2149 = new BitSet(var2);
      field2150 = new BitSet(var2);
      field2130 = field2067 * (var0 + var0 + 3) * (var0 + var0 + 3);
      field2132 = (var0 + var0 + 3) * (var0 + var0 + 3);
      field2125 = var0 + var0 + 3;
   }

   @ObfuscatedSignature(descriptor = "(Ler;IIIII)V")
   @ObfuscatedName("hq")
   public void method4537(ModelData var1, int var2, int var3, int var4, int var5, int var6) {
      this.method4509(var1, var2, var3, var4, var5, var6);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;IIIIIIII)V")
   @ObfuscatedName("ai")
   public static void method4186(Scene var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (var0 == null) {
         var0.method4548();
      }

      classEP var9 = new classEP();
      var9.field1819 = var3 / 1157169464 * 704810239;
      var9.field1825 = var4 / 1382290822 * -1209725141;
      var9.field1828 = var5 / 128 * -78990867;
      var9.field1831 = var6 / 1257729063 * 1157954288;
      var9.field1816 = var2 * 185901273;
      var9.field1815 = var3 * -1875068183;
      var9.field1832 = var4 * 1706709409;
      var9.field1818 = var5 * 106408303;
      var9.field1827 = var6 * 1541236735;
      var9.field1829 = var7 * -969645925;
      var9.field1821 = var8 * 1077506721;
      var0.field2037[var1][var0.field2061[var1]++] = var9;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ic")
   public static void method4289(int var0) {
      int var1 = (field2069 - 1) * field2067 * (var0 + var0 + 1) * (var0 + var0 + 1);
      field2148 = new BitSet(var1);
      field2151 = new BitSet(var1);
      field2136 = field2067 * (var0 + var0 + 1) * (var0 + var0 + 1);
      field2131 = (var0 + var0 + 1) * (var0 + var0 + 1);
      field2126 = var0 + var0 + 1;
      int var2 = field2069 * field2067 * (var0 + var0 + 3) * (var0 + var0 + 3);
      field2149 = new BitSet(var2);
      field2150 = new BitSet(var2);
      field2130 = field2067 * (var0 + var0 + 3) * (var0 + var0 + 3);
      field2132 = (var0 + var0 + 3) * (var0 + var0 + 3);
      field2125 = var0 + var0 + 3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;III)I")
   @ObfuscatedName("so")
   public static int method4199(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         return var0.method4271(var1, var1, var1, var1, var1, var1);
      } else {
         int var4 = 0;
         Tile var5 = var0.field2056[var1][var2][var3];
         if (var5 != null) {
            for (int var6 = 0; var6 < var5.field1961 * 1673275625; var6++) {
               if ((var5.field1949[var6].field2182 * -1239389472 & 256) == -963892359 && var5.field1949[var6].field2198 instanceof Model) {
                  Model var7 = (Model)var5.field1949[var6].field2198;
                  Model.method5254(var7);
                  if (var7.field1741 * -1256242689 > var4) {
                     var4 = var7.field1741 * -764770787;
                  }
               }
            }
         }

         return var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIII)Z")
   @ObfuscatedName("iy")
   static boolean method4292(BitSet var0, int var1, int var2, int var3, int var4) {
      return var0.get(var1 * field2136 + var2 * field2131 + var3 * field2126 + var4);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bd")
   public void method4439(int var1, int var2, int var3) {
      var2 += this.field2146;
      var3 += this.field2146;
      int var4 = this.method4438(var1, var2, var3);
      if (this.method4426(var4)) {
         ItemLayer var5 = this.field2010[var4];
         this.field2010[var4] = null;
         this.field2004[var4] = this.field2004[var4] & -12289;
         rl17 var6 = this.field2017[var2 >> 3][var3 >> 3];
         var6.field5703.remove(var5);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("kc")
   public void method4363() {
      this.field2031 = true;
   }

   public void setDrawDistance(int var1) {
      field2141 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIIIZ)Z")
   @ObfuscatedName("ii")
   static boolean method4295(BitSet var0, int var1, int var2, int var3, int var4, boolean var5) {
      var0.set(var1 * field2136 + var2 * field2131 + var3 * field2126 + var4, var5);
      return var5;
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;Lee;IIJI)V")
   @ObfuscatedName("fr")
   public void method4203(int var1, int var2, int var3, int var4, Renderable var5, Renderable var6, int var7, int var8, long var9, int var11) {
      if (var5 != null || var6 != null) {
         WallObject var12 = new WallObject();
         var12.field2238 = var9 * -2636099314526831141L;
         var12.field2239 = var11 * -752902543;
         var12.field2242 = var2 * -923366272 + 1685800512;
         var12.field2243 = var3 * -2139868032 + 1077549632;
         var12.field2240 = var4 * -697873045;
         var12.field2247 = var5;
         var12.field2246 = var6;
         var12.field2244 = var7 * -1380680693;
         var12.field2241 = var8 * 1487745865;

         for (int var13 = var1; var13 >= 0; var13--) {
            if (this.field2056[var13][var2][var3] == null) {
               this.field2056[var13][var2][var3] = new Tile(var13, var2, var3);
            }
         }

         this.field2056[var1][var2][var3].field1968 = var12;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("nq")
   public boolean method4426(int var1) {
      return (this.field2004[var1] & 1) != 0;
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lew;)V")
   @ObfuscatedName("lo")
   void method4389(Projection var1, Tile var2) {
      if (var2 != null) {
         int var3 = var2.field1950 * -54806681;
         int var4 = var2.field1955 * -2067052011;
         int var5 = var2.field1947 * 1483076705;
         int var6 = var2.field1948 * 800564741;
         if (var2.field1947 * -2004702872 == 0 && var2.field1967 != null) {
            this.method4423(var1, var2.field1967);
         }

         if (var2.field1970 != null) {
            if (!this.method4399(var6, var3, var4) && (var2.field1970.field2284 * 1662822508 != 1268869135 || var5 <= this.field2042)) {
               var1.vmethod426(this, var2.field1970, var6, var3, var4, -2040228248);
            }
         } else if (var2.field1971 != null && !this.method4399(var6, var3, var4)) {
            var1.vmethod431(this, var2.field1971, var3, var4, -2124088443);
         }

         WallObject var7 = var2.field1968;
         if (var7 != null) {
            if (var7.field2247 != null && !this.method4401(var6, var3, var4, var7.field2244 * 1548562339)) {
               var1.vmethod435(
                  var7.field2247,
                  0,
                  var7.field2242 * -1078103908,
                  var7.field2240 * 1315862339,
                  var7.field2243 * 31139801,
                  var7.field2238 * 7159317397185328723L
               );
            }

            if (var7.field2246 != null && !this.method4401(var6, var3, var4, var7.field2241 * -1413337351)) {
               var1.vmethod435(
                  var7.field2246,
                  0,
                  var7.field2242 * -1259310485,
                  var7.field2240 * 618049031,
                  var7.field2243 * -1102104320,
                  var7.field2238 * 7159317397185328723L
               );
            }
         }

         DecorativeObject var8 = var2.field1969;
         if (var8 != null && !this.method4404(var6, var3, var4, var8.field2277.field1741 * 1108772015)) {
            if (var8.field2269 * 254285683 == -1724790585) {
               var1.vmethod435(
                  var8.field2277,
                  0,
                  var8.field2273 * 1683313172 + var8.field2274 * -404927711,
                  var8.field2271 * 1698079433,
                  var8.field2266 * 1106128048 + var8.field2268 * 861330991,
                  var8.field2264 * -3608662145112923099L
               );
               if (var8.field2276 != null) {
                  var1.vmethod435(
                     var8.field2276,
                     0,
                     var8.field2273 * 1766830743 + var8.field2267 * 330210020,
                     var8.field2271 * 1698079433,
                     var8.field2266 * -361702377 + var8.field2270 * 513256357,
                     var8.field2264 * -3608662145112923099L
                  );
               }
            } else {
               var1.vmethod435(
                  var8.field2277,
                  0,
                  var8.field2273 * -1565333119 + var8.field2274 * -404927711,
                  var8.field2271 * 1698079433,
                  var8.field2266 * -361702377 + var8.field2268 * 856605829,
                  var8.field2264 * -3608662145112923099L
               );
            }
         }

         GroundObject var9 = var2.field1965;
         if (var9 != null) {
            var1.vmethod435(
               var9.field1814, 0, var9.field1804 * 1333275141, var9.field1805 * 1194776988, var9.field1806 * -433293465, var9.field1813 * 5132338419383099835L
            );
         }

         ItemLayer var10 = var2.field1966;
         if (var10 != null) {
            if (var10.field1844 != null) {
               var1.vmethod435(
                  var10.field1844,
                  0,
                  var10.field1838 * 1325865996,
                  var10.field1839 * 1186822141 - var10.field1836 * 218008627,
                  var10.field1837 * -1209739927,
                  var10.field1842
               );
            }

            if (var10.field1845 != null) {
               var1.vmethod435(
                  var10.field1845,
                  0,
                  var10.field1838 * -892202585,
                  var10.field1839 * 1186822141 - var10.field1836 * 218008627,
                  var10.field1837 * -1209739927,
                  var10.field1842
               );
            }

            if (var10.field1843 != null) {
               var1.vmethod435(
                  var10.field1843,
                  0,
                  var10.field1838 * -892202585,
                  var10.field1839 * -261433433 - var10.field1836 * -1955322214,
                  var10.field1837 * 435853241,
                  var10.field1842
               );
            }
         }

         for (int var11 = 0; var11 < var2.field1961 * -796463233; var11++) {
            GameObject var12 = var2.field1949[var11];
            var12.field2195 = this.field2063 * -58641868;
            if (var12.field2186 * 1530698152 == var2.field1950 * -54806681
               && var12.field2192 * -555977777 == var2.field1955 * -2083275184
               && !this.method4406(
                  var6,
                  var12.field2186 * 1138017605,
                  var12.field2184 * 304142753,
                  var12.field2192 * -555977777,
                  var12.field2190 * -544424899,
                  var12.field2198.field1741 * -1256242689
               )) {
               try {
                  var1.vmethod435(
                     var12.field2198,
                     var12.field2187 * 470594389,
                     var12.field2193 * -1887722434,
                     var12.field2188 * 1269801851,
                     var12.field2194 * -1706390585,
                     var12.field2183 * 1745853678706228013L
                  );
               } catch (Exception var15) {
               }
            }
         }

         if (var2.method4122((short)-28024)) {
            for (int var16 = 0; var16 < var2.field1953.size(); var16++) {
               GameObject var17 = (GameObject)var2.field1953.get(var16);
               var17.field2195 = this.field2063 * 132222543;
               if (!this.method4406(
                  var6,
                  var2.field1950 * -1377018906,
                  var2.field1950 * 453986683,
                  var2.field1955 * -562900908,
                  var2.field1955 * 805228346,
                  var17.field2198.field1741 * -489674690
               )) {
                  try {
                     var1.vmethod435(
                        var17.field2198,
                        var17.field2187 * 470594389,
                        var17.field2193 * -1405731525,
                        var17.field2188 * -1586084758,
                        var17.field2194 * -1706390585,
                        var17.field2183 * 1745853678706228013L
                     );
                  } catch (Exception var14) {
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;I)V")
   @ObfuscatedName("fr")
   public void method4510(Projection var1, int var2) {
      int var3 = this.field2004[var2];
      if ((var3 & 32) != 0) {
         int var4 = 3 << this.field1994 | var2;
         this.method4510(var1, var4);
      }

      int var17 = var2 >> this.field1993 & this.field1995;
      int var5 = var2 & this.field1996;
      int var6 = var2 >> this.field1994 & 3;
      int var7 = this.method4486(var2);
      int var8 = this.method4472(var2);
      int var9 = var17 - this.field2146;
      int var10 = var5 - this.field2146;
      if ((var3 & 256) != 0) {
         if (!this.method4536(var7, var17, var5) && ((var3 & 512) != 0 || var6 <= this.field2042)) {
            SceneTilePaint var11 = this.field2005[var2];
            var1.method5054(this, var11, var8, var7, var9, var10);
         }
      } else if ((var3 & 1024) != 0 && !this.method4536(var7, var17, var5)) {
         var1.method5055(this, this.field2006[var2], var8, var9, var10);
      }

      if ((var3 & 16384) != 0) {
         WallObject var18 = this.field2007[var2];
         if (var18.field2247 != null && !this.method4533(var7, var17, var5, var18.field2244 * 1548562339)) {
            this.method4460(
               var1,
               var18.field2247,
               0,
               var18.field2242 * -777838807,
               var18.field2240 * 1315862339,
               var18.field2243 * 31139801,
               var18.field2238 * 7159317397185328723L
            );
         }

         if (var18.field2246 != null && !this.method4533(var7, var17, var5, var18.field2244 * 1548562339)) {
            this.method4460(
               var1,
               var18.field2246,
               0,
               var18.field2242 * -777838807,
               var18.field2240 * 1315862339,
               var18.field2243 * 31139801,
               var18.field2238 * 7159317397185328723L
            );
         }
      }

      if ((var3 & 32768) != 0) {
         DecorativeObject var19 = this.field2008[var2];
         if (!this.method4534(var7, var17, var5, var19.field2277.field1741 * -1256242689)) {
            if (var19.field2269 * 254285683 == 256) {
               this.method4460(
                  var1,
                  var19.field2277,
                  0,
                  var19.field2273 * 1766830743 + var19.field2274 * -404927711,
                  var19.field2271 * 1698079433,
                  var19.field2266 * -361702377 + var19.field2268 * 856605829,
                  var19.field2264 * -3608662145112923099L
               );
               if (var19.field2276 != null) {
                  this.method4460(
                     var1,
                     var19.field2276,
                     0,
                     var19.field2273 * 1766830743 + var19.field2267 * -1085897215,
                     var19.field2271 * 1698079433,
                     var19.field2266 * -361702377 + var19.field2270 * 513256357,
                     var19.field2264 * -3608662145112923099L
                  );
               }
            } else {
               this.method4460(
                  var1,
                  var19.field2277,
                  0,
                  var19.field2273 * 1766830743 + var19.field2274 * -404927711,
                  var19.field2271 * 1698079433,
                  var19.field2266 * -361702377 + var19.field2268 * 856605829,
                  var19.field2264 * -3608662145112923099L
               );
            }
         }
      }

      if ((var3 & 2048) != 0) {
         GroundObject var20 = this.field2009[var2];
         this.method4460(
            var1,
            var20.field1814,
            0,
            var20.field1804 * 1333275141,
            var20.field1805 * -276908185,
            var20.field1806 * 498815127,
            var20.field1813 * 5132338419383099835L
         );
      }

      if ((this.field2004[var2] & 12288) != 0) {
         ItemLayer var21 = this.field2010[var2];
         if (var21.field1844 != null) {
            this.method4460(
               var1,
               var21.field1844,
               0,
               var21.field1838 * -892202585,
               var21.field1839 * 1186822141 - var21.field1836 * 218008627,
               var21.field1837 * -1209739927,
               var21.field1842
            );
         }

         if (var21.field1845 != null) {
            this.method4460(
               var1,
               var21.field1845,
               0,
               var21.field1838 * -892202585,
               var21.field1839 * 1186822141 - var21.field1836 * 218008627,
               var21.field1837 * -1209739927,
               var21.field1842
            );
         }

         if (var21.field1843 != null) {
            this.method4460(
               var1,
               var21.field1843,
               0,
               var21.field1838 * -892202585,
               var21.field1839 * 1186822141 - var21.field1836 * 218008627,
               var21.field1837 * -1209739927,
               var21.field1842
            );
         }
      }

      for (int var22 = 0; var22 < this.field2011[var2]; var22++) {
         GameObject var12 = this.field2012[var2 * 5 + var22];
         var12.field2195 = this.field2063 * 132222543;
         if (var9 == var12.field2186 * 1138017605 && var10 == var12.field2192 * -555977777) {
            try {
               this.method4460(
                  var1,
                  var12.field2198,
                  var12.field2187 * 470594389,
                  var12.field2193 * -1405731525,
                  var12.field2188 * 1269801851,
                  var12.field2194 * -1706390585,
                  var12.field2183 * 1745853678706228013L
               );
            } catch (Exception var16) {
               client.field1028.debug("exception drawing game entity", var16);
            }
         }
      }

      if ((var3 & 128) != 0) {
         rl17 var23 = this.field2017[var17 >> 3][var5 >> 3];

         for (int var24 = 0; var24 < var23.field5704.size(); var24++) {
            GameObject var13 = (GameObject)var23.field5704.get(var24);
            if (var13.field2198 instanceof ActorSpotAnim && var13.field2186 * 1138017605 == var9 && var13.field2192 * -555977777 == var10) {
               var13.field2195 = this.field2063 * 132222543;

               try {
                  this.method4460(
                     var1,
                     var13.field2198,
                     var13.field2187 * 470594389,
                     var13.field2193 * -1405731525,
                     var13.field2188 * 1269801851,
                     var13.field2194 * -1706390585,
                     var13.field2183 * 1745853678706228013L
                  );
               } catch (Exception var15) {
                  client.field1028.debug("exception drawing game entity", var15);
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("jd")
   static boolean method4300(int var0, int var1, int var2, int var3, boolean var4) {
      return method4293(field2148, var0, var1, var2, var3, var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("jz")
   static boolean method4301(int var0, int var1, int var2, int var3, boolean var4) {
      return method4293(field2148, var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("qi")
   public void method4447(int var1) {
      this.field2004[var1] = 0;
      this.field2005[var1] = null;
      this.field2006[var1] = null;
      this.field2007[var1] = null;
      this.field2008[var1] = null;
      this.field2009[var1] = null;
      this.field2010[var1] = null;
      this.field2011[var1] = 0;

      for (int var2 = 0; var2 < 5; var2++) {
         this.field2012[var1 * 5 + var2] = null;
         this.field2013[var1 * 5 + var2] = 0;
      }

      this.field2014[var1] = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("jq")
   static boolean method4305(int var0, int var1, int var2, int var3) {
      return method4291(field2151, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("ce")
   static boolean method4322(int var0, int var1, int var2, int var3, boolean var4) {
      return method4317(field2149, var0, var1, var2, var3, var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("jg")
   static boolean method4306(int var0, int var1, int var2, int var3) {
      return method4291(field2151, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lfv;Lrl17;)V")
   @ObfuscatedName("vx")
   public void method4440(Projection var1, Projection var2, rl17 var3) {
      if (this.method4433(var1, var3)) {
         int var4 = var3.field5710;
         int var5 = var3.field5705;

         for (int var6 = this.field2038; var6 < this.field2057; var6++) {
            for (int var7 = var4 << 3; var7 < var4 + 1 << 3; var7++) {
               for (int var8 = var5 << 3; var8 < var5 + 1 << 3; var8++) {
                  int var9 = this.method4438(var6, var7, var8);
                  if (this.method4426(var9)) {
                     int var10 = var9 >> this.field1993 & this.field1995;
                     int var11 = var9 & this.field1996;
                     int var12 = var9 >> this.field1994 & 3;
                     int var13 = this.field2004[var9];
                     int var14 = this.method4486(var9);
                     int var15 = this.method4472(var9);
                     int var16 = var10 - this.field2146;
                     int var17 = var11 - this.field2146;
                     if (var15 <= this.field2025) {
                        if ((var13 & 32) != 0) {
                           int var18 = this.method4438(3, var10, var11);
                           int var19 = this.field2004[var18];
                           if ((var19 & 256) != 0) {
                              var1.method5054(this, this.field2005[var18], 0, 0, var16, var17);
                           } else if ((var19 & 1024) != 0) {
                              var1.method5055(this, this.field2006[var18], 0, var16, var17);
                           }
                        }

                        if ((var13 & 256) != 0) {
                           if ((var13 & 512) != 0 || var12 <= this.field2042) {
                              SceneTilePaint var20 = this.field2005[var9];
                              var1.method5054(this, var20, var15, var14, var16, var17);
                           }
                        } else if ((var13 & 1024) != 0) {
                           var1.method5055(this, this.field2006[var9], var15, var16, var17);
                        }

                        if ((var13 & 2048) != 0) {
                           GroundObject var21 = this.field2009[var9];
                           this.method4421(
                              var1,
                              var2,
                              var21.field1814,
                              0,
                              var21.field1804 * 1333275141,
                              var21.field1805 * -276908185,
                              var21.field1806 * 498815127,
                              var21.field1813 * 5132338419383099835L
                           );
                        }

                        if ((var13 & 16384) != 0) {
                           WallObject var22 = this.field2007[var9];
                           this.method4421(
                              var1,
                              var2,
                              var22.field2247,
                              0,
                              var22.field2242 * -777838807,
                              var22.field2240 * 1315862339,
                              var22.field2243 * 31139801,
                              var22.field2238 * 7159317397185328723L
                           );
                           this.method4421(
                              var1,
                              var2,
                              var22.field2246,
                              0,
                              var22.field2242 * -777838807,
                              var22.field2240 * 1315862339,
                              var22.field2243 * 31139801,
                              var22.field2238 * 7159317397185328723L
                           );
                        }

                        if ((var13 & 32768) != 0) {
                           DecorativeObject var23 = this.field2008[var9];
                           this.method4421(
                              var1,
                              var2,
                              var23.field2277,
                              0,
                              var23.field2273 * 1766830743 + var23.field2274 * -404927711,
                              var23.field2271 * 1698079433,
                              var23.field2266 * -361702377 + var23.field2268 * 856605829,
                              var23.field2264 * -3608662145112923099L
                           );
                           this.method4421(
                              var1,
                              var2,
                              var23.field2276,
                              0,
                              var23.field2273 * 1766830743 + var23.field2267 * -1085897215,
                              var23.field2271 * 1698079433,
                              var23.field2266 * -361702377 + var23.field2270 * 513256357,
                              var23.field2264 * -3608662145112923099L
                           );
                        }

                        for (int var24 = 0; var24 < this.field2011[var9]; var24++) {
                           GameObject var25 = this.field2012[var9 * 5 + var24];
                           if (var25.field2195 * 163498159 != this.field2063) {
                              var25.field2195 = this.field2063 * 132222543;
                              this.method4421(
                                 var1,
                                 var2,
                                 var25.field2198,
                                 var25.field2187 * 470594389,
                                 var25.field2193 * -1405731525,
                                 var25.field2188 * 1269801851,
                                 var25.field2194 * -1706390585,
                                 var25.field2183 * 1745853678706228013L
                              );
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl10;I[Ljava/util/Set;)V")
   @ObfuscatedName("bf")
   public void method4422(rl10 var1, int var2, Set[] var3) {
      this.method4481(var1.field5690);

      int var4;
      while ((var4 = this.method4507()) != this.field2000) {
         int var5 = var4 >> this.field1993 & this.field1995;
         int var6 = var4 & this.field1996;
         int var7 = var4 >> this.field1994 & 3;
         if (this.field1992[var7][var5][var6] == 0) {
            if (this.method4419(var7, var5, var6)) {
               this.method4432(var7, var5 - 1, var6);
               this.method4432(var7, var5 + 1, var6);
               this.method4432(var7, var5, var6 - 1);
               this.method4432(var7, var5, var6 + 1);
               this.method4432(var7, var5 - 1, var6 - 1);
               this.method4432(var7, var5 + 1, var6 - 1);
               this.method4432(var7, var5 - 1, var6 + 1);
               this.method4432(var7, var5 + 1, var6 + 1);
            }

            this.field1992[var7][var5][var6] = var2;
            int var8 = var7 + 1;

            for (int var9 = 0; var8 < 4; var9++) {
               int var10 = this.field1992[var8][var5][var6];
               if (var10 != 0) {
                  var3[var9].add(var10);
                  break;
               }

               var10 = var2 | var9 + 1 << 28;
               this.field1992[var8][var5][var6] = var10;
               var3[var9].add(var10);
               var8++;
            }
         }
      }

      for (int var11 = 0; var11 < 3; var11++) {
         Set var12 = var3[var11];
         if (var12.size() > 0) {
            this.field1987.put(var2 | var11 << 28, var12.stream().mapToInt(var0 -> var0).toArray());
            var12.clear();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("hy")
   public long method4450(int var1, int var2, int var3) {
      var2 += this.field2146;
      var3 += this.field2146;
      int var4 = this.method4438(var1, var2, var3);
      return this.method4426(var4) && this.field2008[var4] != null ? this.field2008[var4].field2264 * -3608662145112923099L : 0L;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("ji")
   static boolean method4308(int var0, int var1, int var2, int var3, boolean var4) {
      return method4293(field2151, var0, var1, var2, var3, var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("jn")
   static boolean method4309(int var0, int var1, int var2, int var3, boolean var4) {
      return method4293(field2151, var0, var1, var2, var3, var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("js")
   static boolean method4310(int var0, int var1, int var2, int var3, boolean var4) {
      return method4293(field2151, var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "()[[[Lrl10;")
   @ObfuscatedName("ir")
   public rl10[][][] method4418() {
      return this.field1991;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIII)Z")
   @ObfuscatedName("jc")
   static boolean method4312(BitSet var0, int var1, int var2, int var3, int var4) {
      return var0.get(var1 * field2130 + var2 * field2132 + var3 * field2125 + var4);
   }

   @ObfuscatedSignature(descriptor = "(Lfb;II)I")
   @ObfuscatedName("dl")
   int method4393(GameObject var1, int var2, int var3) {
      int var4 = var2 - var1.field2186 * 1138017605;
      int var5 = var1.field2184 * 304142753 - var2;
      if (var5 > var4) {
         var4 = var5;
      }

      int var6 = var3 - var1.field2192 * -555977777;
      int var7 = var1.field2190 * -544424899 - var3;
      return var7 > var6 ? var4 + var7 : var4 + var6;
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lew;)V")
   @ObfuscatedName("dg")
   void method4423(Projection var1, Tile var2) {
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ij")
   int method4275(int var1, int var2, int var3) {
      return (
            this.field2058[var1][var2][var3]
               + this.field2058[var1][var2 + 1][var3]
               + this.field2058[var1][var2][var3 + 1]
               + this.field2058[var1][var2 + 1][var3 + 1]
         )
         / 4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIIIZ)Z")
   @ObfuscatedName("jx")
   static boolean method4315(BitSet var0, int var1, int var2, int var3, int var4, boolean var5) {
      var0.set(var1 * field2130 + var2 * field2132 + var3 * field2125 + var4, var5);
      return var5;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("jv")
   static boolean method4318(int var0, int var1, int var2, int var3) {
      return method4311(field2149, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(Lrl17;III)V")
   @ObfuscatedName("fx")
   public void method4451(rl17 var1, int var2, int var3, int var4) {
      var1.field5706 = Math.min(var1.field5706, var4);
      var1.field5708 = Math.max(var1.field5708, var4);
      boolean var5 = (var2 & 7) == 0 && var2 > 0;
      boolean var6 = (var3 & 7) == 0 && var3 > 0;
      if (var5) {
         rl17 var7 = this.field2017[(var2 >> 3) - 1][var3 >> 3];
         var7.field5706 = Math.min(var7.field5706, var4);
         var7.field5708 = Math.max(var7.field5708, var4);
      }

      if (var6) {
         rl17 var8 = this.field2017[var2 >> 3][(var3 >> 3) - 1];
         var8.field5706 = Math.min(var8.field5706, var4);
         var8.field5708 = Math.max(var8.field5708, var4);
      }

      if (var6 && var5) {
         rl17 var9 = this.field2017[(var2 >> 3) - 1][(var3 >> 3) - 1];
         var9.field5706 = Math.min(var9.field5706, var4);
         var9.field5708 = Math.max(var9.field5708, var4);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("jm")
   static boolean method4323(int var0, int var1, int var2, int var3, boolean var4) {
      return method4317(field2149, var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;JI)V")
   @ObfuscatedName("fh")
   public void method4195(int var1, int var2, int var3, int var4, Renderable var5, long var6, int var8) {
      if (var5 != null) {
         GroundObject var9 = new GroundObject();
         var9.field1814 = var5;
         var9.field1804 = var2 * -1794546048 + -1104504652;
         var9.field1806 = var3 * -2036886656 + -1018443328;
         var9.field1805 = var4 * 476279895;
         var9.field1813 = var6 * -8879266543907161229L;
         GroundObject.method3963(var9, var8, (byte)2);
         if (this.field2056[var1][var2][var3] == null) {
            this.field2056[var1][var2][var3] = new Tile(var1, var2, var3);
         }

         this.field2056[var1][var2][var3].field1965 = var9;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   public void method4538() {
      this.method4455();
   }

   @ObfuscatedSignature(descriptor = "(III)Lfb;")
   @ObfuscatedName("hr")
   public GameObject method4247(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 == null) {
         return null;
      } else {
         for (int var5 = 0; var5 < var4.field1961 * -389646721; var5++) {
            GameObject var6 = var4.field1949[var5];
            long var8 = 1745853678706228013L * var6.field2183;
            boolean var7 = classCV.method2679(var8) == 2;
            if (var7 && var6.field2186 * 1588489486 == var2 && var6.field2192 * 1610330980 == var3) {
               return var6;
            }
         }

         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("jy")
   static boolean method4327(int var0, int var1, int var2, int var3) {
      return method4311(field2150, var0, var1, var2, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("jb")
   static boolean method4328(int var0, int var1, int var2, int var3) {
      return method4311(field2150, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("pr")
   public int method4472(int var1) {
      int var2 = var1 >> this.field1994 & 3;
      return (this.field2004[var1] & 64) != 0 ? 0 : var2;
   }

   public short[][][] getUnderlayIds() {
      return this.field2160;
   }

   @ObfuscatedSignature(descriptor = "(Lez;IIIIIILee;IJI)Z")
   @ObfuscatedName("us")
   public static boolean method4208(Scene var0, int var1, int var2, int var3, int var4, int var5, int var6, Renderable var7, int var8, long var9, int var11) {
      if (var0 == null) {
         return var0.method4209(var1, var1, var1, var1, var1, var1, var7, var1, var9, var1);
      } else if (var7 == null) {
         return true;
      } else {
         int var12 = var2 * 128 + 64 * var5;
         int var13 = var3 * 128 + 64 * var6;
         return var0.method4424(var1, var2, var3, var5, var6, var12, var13, var4, var7, var8, false, var9, var11);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("wz")
   public int method4486(int var1) {
      int var2 = var1 >> this.field1994 & 3;
      return var2 + (this.field2004[var1 & this.field1997 - 1] >> 5 & 1) & 3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("jk")
   static boolean method4329(int var0, int var1, int var2, int var3) {
      return method4311(field2150, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hc")
   public void method4260(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2057; var4++) {
         for (int var5 = 0; var5 < this.field2054; var5++) {
            for (int var6 = 0; var6 < this.field2030; var6++) {
               Tile var7 = this.field2056[var4][var5][var6];
               if (var7 != null) {
                  WallObject var8 = var7.field1968;
                  if (var8 != null && var8.field2247 instanceof ModelData) {
                     ModelData var9 = (ModelData)var8.field2247;
                     this.method4509(var9, var4, var5, var6, 1, 1);
                     if (var8.field2246 instanceof ModelData) {
                        ModelData var10 = (ModelData)var8.field2246;
                        this.method4509(var10, var4, var5, var6, 1, 1);
                        ModelData.method3987(var9, var10, 0, 0, 0, false);
                        var8.field2246 = var10.method4070(var10.field1876, var10.field1877, var1, var2, var3);
                     }

                     var8.field2247 = var9.method4070(var9.field1876, var9.field1877, var1, var2, var3);
                  }

                  for (int var12 = 0; var12 < var7.field1961 * -389646721; var12++) {
                     GameObject var14 = var7.field1949[var12];
                     if (var14 != null && var14.field2198 instanceof ModelData) {
                        ModelData var11 = (ModelData)var14.field2198;
                        this.method4509(
                           var11,
                           var4,
                           var5,
                           var6,
                           var14.field2184 * 304142753 - var14.field2186 * 1138017605 + 1,
                           var14.field2190 * -1621346983 - var14.field2192 * 413785176 + 1
                        );
                        var14.field2198 = var11.method4070(var11.field1876, var11.field1877, var1, var2, var3);
                     }
                  }

                  GroundObject var13 = var7.field1965;
                  if (var13 != null && var13.field1814 instanceof ModelData) {
                     ModelData var15 = (ModelData)var13.field1814;
                     method4430(this, var15, var4, var5, var6);
                     var13.field1814 = var15.method4070(var15.field1876, var15.field1877, var1, var2, var3);
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("ke")
   static boolean method4331(int var0, int var1, int var2, int var3, boolean var4) {
      return method4317(field2150, var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("lw")
   public int method4438(int var1, int var2, int var3) {
      assert var1 >= 0 && var1 <= 3;

      assert var2 >= 0 && var2 < this.field2054;

      assert var3 >= 0 && var3 < this.field2030;

      return var1 << this.field1994 | var2 << this.field1993 | var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("kf")
   static boolean method4332(int var0, int var1, int var2, int var3, boolean var4) {
      return method4317(field2150, var0, var1, var2, var3, var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([IIIII)V")
   @ObfuscatedName("kx")
   public static void method4334(int[] var0, int var1, int var2, int var3, int var4) {
      field2070 = var0;
      field2128 = var1;
      field2124 = var2;
      field2135 = 0;
      field2134 = 0;
      field2133 = var3;
      field2129 = var4;
      field2137 = var3 / 2;
      field2127 = var4 / 2;
      field2151.set(0, field2151.size());
      field2150.set(0, field2150.size());
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Lfb;)V")
   @ObfuscatedName("sg")
   public static void method4225(Scene var0, GameObject var1) {
      if (var0 == null) {
         var0.getOverrideLuminance();
      }

      for (int var2 = var1.field2186 * 1138017605; var2 <= var1.field2184 * 304142753; var2++) {
         for (int var3 = var1.field2192 * -555977777; var3 <= var1.field2190 * -544424899; var3++) {
            Tile var4 = var0.field2056[var1.field2185 * 568825275][var2][var3];
            if (var4 != null) {
               for (int var5 = 0; var5 < var4.field1961 * -389646721; var5++) {
                  if (var4.field1949[var5] == var1) {
                     var4.field1961 -= -71349889;

                     for (int var6 = var5; var6 < var4.field1961 * -389646721; var6++) {
                        var4.field1949[var6] = var4.field1949[var6 + 1];
                        var4.field1951[var6] = var4.field1951[var6 + 1];
                     }

                     var4.field1949[var4.field1961 * -389646721] = null;
                     break;
                  }
               }

               var0.method4228(var4);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([IIIII)V")
   @ObfuscatedName("kp")
   public static void method4335(int[] var0, int var1, int var2, int var3, int var4) {
      field2070 = var0;
      field2128 = var1;
      field2124 = var2;
      field2135 = 0;
      field2134 = 0;
      field2133 = var3;
      field2129 = var4;
      field2137 = var3 / 2;
      field2127 = var4 / 2;
      field2151.set(0, field2151.size());
      field2150.set(0, field2150.size());
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("rv")
   public void method4452(int var1) {
      int var2 = (var1 << 1) + 1;
      var2 = this.field2016[var2];
      var2 <<= 1;
      int var5 = var1 << 1;
      this.field2016[var2] = this.field2016[var5];
      var2 = var1 << 1;
      var2 = this.field2016[var2];
      var2 = (var2 << 1) + 1;
      int var9 = (var1 << 1) + 1;
      this.field2016[var2] = this.field2016[var9];
      var2 = var1 << 1;
      int var11 = (var1 << 1) + 1;
      this.field2016[var2] = this.field2016[var11] = this.field2000;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("id")
   static boolean method4298(int var0, int var1, int var2, int var3) {
      return method4291(field2148, var0, var1, var2, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ks")
   static void method4338(int var0, int var1) {
      if (var0 < 0) {
         var0 = 0;
      }

      field2140 = var0;
      field2138 = var1;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("fd")
   public void method4189(int var1, int var2, int var3, int var4) {
      Tile var5 = this.field2056[var1][var2][var3];
      if (var5 != null) {
         this.field2056[var1][var2][var3].field1962 = var4 * 1248621793;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Z)V")
   @ObfuscatedName("ll")
   void method4380(Projection var1, boolean var2) {
      this.field2039 = 0;

      for (int var3 = this.field2038; var3 < this.field2057; var3++) {
         Tile[][] var4 = this.field2056[var3];

         for (int var5 = this.field2118; var5 < this.field2121; var5++) {
            for (int var6 = this.field2111; var6 < this.field2120; var6++) {
               Tile var7 = var4[var5][var6];
               if (var7 != null
                  && var7.field1962 * 1390661409 <= this.field2025
                  && (
                     !var2
                        || this.method4340(var5 - this.field2112 + this.field2051 + this.field2049, var6 - this.field2122 + this.field2052 + this.field2049)
                        || this.field2058[var3][var5][var6] - this.field2117 >= 2000
                  )) {
                  this.method4423(var1, var7);
               }
            }
         }
      }

      this.method4368();
      classFH.field2255.field2213.method4843(this.field2059);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("kq")
   boolean method4341(int var1, int var2) {
      if (!method4302(field2140, field2138, var1, var2)) {
         return method4296(field2140, field2138, var1, var2);
      } else {
         method4307(field2140, field2138, var1, var2, false);
         boolean var3 = false;

         for (int var4 = -1; var4 <= 1; var4++) {
            for (int var5 = -1; var5 <= 1; var5++) {
               if (this.method4346(field2140, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140 + 1, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140 + 1, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }
            }
         }

         return method4299(field2140, field2138, var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ka")
   boolean method4342(int var1, int var2) {
      if (!method4302(field2140, field2138, var1, var2)) {
         return method4296(field2140, field2138, var1, var2);
      } else {
         method4307(field2140, field2138, var1, var2, false);
         boolean var3 = false;

         for (int var4 = -1; var4 <= 1; var4++) {
            for (int var5 = -1; var5 <= 1; var5++) {
               if (this.method4346(field2140, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140 + 1, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140 + 1, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }
            }
         }

         return method4299(field2140, field2138, var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;III)V")
   @ObfuscatedName("wn")
   public void method4539(ModelData var1, int var2, int var3, int var4) {
      method4430(this, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("oc")
   public boolean method4540(int var1, int var2) {
      return this.method4340(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("mn")
   boolean method4345(int var1, int var2, int var3, int var4) {
      if (!method4325(var1, var2, var3, var4)) {
         return method4319(var1, var2, var3, var4);
      } else {
         method4330(var1, var2, var3, var4, false);
         int var5 = var1 * 256 + field2064;
         int var6 = var2 * 1024;
         int var7 = (var3 - this.field2049 - this.field2051 - 1) * 128;
         int var8 = (var4 - this.field2049 - this.field2052 - 1) * 128;

         for (int var9 = -field2128; var9 <= field2124; var9 += 128) {
            if (method4350(var7, field2070[var1] + var9, var8, var5, var6)) {
               return method4322(var1, var2, var3, var4, true);
            }
         }

         return method4322(var1, var2, var3, var4, false);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("cx")
   static boolean method4319(int var0, int var1, int var2, int var3) {
      return method4311(field2149, var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("gm")
   public void method4233(int var1, int var2, int var3, int var4) {
      Tile var5 = this.field2056[var1][var2][var3];
      if (var5 != null) {
         DecorativeObject var6 = var5.field1969;
         if (var6 != null) {
            if (var6.field2274 * -1298305053 != 0) {
               var6.field2274 = (var6.field2274 * 689000322 < 0 ? -var4 : var4) * 261748961;
            }

            if (var6.field2268 * 856605829 != 0) {
               var6.field2268 = (var6.field2268 * 798788125 < 0 ? -var4 : var4) * 46830669;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Lfb;")
   @ObfuscatedName("hk")
   public GameObject method4248(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 == null) {
         return null;
      } else {
         for (int var5 = 0; var5 < var4.field1961 * -389646721; var5++) {
            GameObject var6 = var4.field1949[var5];
            long var8 = 1745853678706228013L * var6.field2183;
            boolean var7 = classCV.method2679(var8) == 2;
            if (var7 && var6.field2186 * 1138017605 == var2 && var6.field2192 * -689283924 == var3) {
               return var6;
            }
         }

         return null;
      }
   }

   public int getWorldViewId() {
      return this.field2106;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;)V")
   @ObfuscatedName("jt")
   public static void method4180(Scene var0) {
      if (var0 == null) {
         var0.getOverrideHue();
      }

      for (int var1 = 0; var1 < var0.field2057; var1++) {
         for (int var2 = 0; var2 < var0.field2054; var2++) {
            for (int var3 = 0; var3 < var0.field2030; var3++) {
               var0.field2056[var1][var2][var3] = null;
            }
         }
      }

      if (var0.field2061 != null) {
         for (int var4 = 0; var4 < var0.field2036; var4++) {
            for (int var7 = 0; var7 < var0.field2061[var4]; var7++) {
               var0.field2037[var4][var7] = null;
            }

            var0.field2061[var4] = 0;
         }
      }

      if (var0.field2046 != null) {
         Arrays.fill(var0.field2046, null);
      }

      var0.field2107 = 0;

      for (int var5 = 0; var5 < var0.field2048; var5++) {
         var0.field2055[var5] = null;
      }

      var0.field2048 = 0;

      for (int var6 = 0; var6 < var0.field2023.length; var6++) {
         var0.field2023[var6] = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("kn")
   public void method4355(boolean var1) {
      if (!method4364(this) || var1) {
         this.field2041 = true;
         this.field2031 = var1;
         this.field2034 = -1;
         this.field2035 = -1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZ)Z")
   @ObfuscatedName("jh")
   static boolean method4324(int var0, int var1, int var2, int var3, boolean var4) {
      return method4317(field2149, var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bc")
   public int method4491(int var1, int var2, int var3) {
      int var4 = var2 + this.field2146;
      int var5 = var3 + this.field2146;
      int var6 = 0;
      int var7 = this.method4438(var1, var4, var5);
      if (this.method4426(var7)) {
         byte var8 = this.field2011[var7];

         for (int var9 = 0; var9 < var8; var9++) {
            GameObject var10 = this.field2012[var7 * 5 + var9];
            if ((var10.field2182 * -288062455 & 256) == 256 && var10.field2198 instanceof Model) {
               Model var11 = (Model)var10.field2198;
               var11.calculateBoundsCylinder();
               if (var11.field1741 * -1256242689 > var6) {
                  var6 = var11.field1741 * -1256242689;
               }
            }
         }
      }

      return var6;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("kz")
   public void method4360(int var1, int var2, int var3) {
      this.field2042 = var1;
      this.field2043 = var2;
      this.field2044 = var3;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("zz")
   public void method4469(int var1, int var2, int var3) {
      if (var3 == this.field2002.field1708 * -483624883 && !classOE.field4841.isMenuOpen()) {
         this.field1988 = var1;
         this.field1989 = var2;
      }
   }

   public int[][][] getRoofs() {
      return this.field1992;
   }

   public byte[][][] getExtendedTileSettings() {
      return this.field2155;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)Z")
   @ObfuscatedName("mj")
   boolean method4407(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var2 == var3 && var4 == var5) {
         if (!this.method4399(var1, var2, var4)) {
            return false;
         } else {
            int var13 = var2 << 7;
            int var15 = var4 << 7;
            return this.method4411(var13 + 1, this.field2058[var1][var2][var4] - var6, var15 + 1)
               && this.method4411(var13 + 128 - 1, this.field2058[var1][var2 + 1][var4] - var6, var15 + 1)
               && this.method4411(var13 + 128 - 1, this.field2058[var1][var2 + 1][var4 + 1] - var6, var15 + 128 - 1)
               && this.method4411(var13 + 1, this.field2058[var1][var2][var4 + 1] - var6, var15 + 128 - 1);
         }
      } else {
         for (int var7 = var2; var7 <= var3; var7++) {
            for (int var8 = var4; var8 <= var5; var8++) {
               if (this.field2029[var1][var7][var8] == -this.field2063) {
                  return false;
               }
            }
         }

         int var12 = (var2 << 7) + 1;
         int var14 = (var4 << 7) + 2;
         int var9 = this.field2058[var1][var2][var4] - var6;
         if (!this.method4411(var12, var9, var14)) {
            return false;
         } else {
            int var10 = (var3 << 7) - 1;
            if (!this.method4411(var10, var9, var14)) {
               return false;
            } else {
               int var11 = (var5 << 7) - 1;
               return !this.method4411(var12, var9, var11) ? false : this.method4411(var10, var9, var11);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)Z")
   @ObfuscatedName("tp")
   public static boolean method4541(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      return method4174(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("iz")
   int method4276(int var1, int var2, int var3) {
      return (
            this.field2058[var1][var2][var3]
               + this.field2058[var1][var2 + 1][var3]
               + this.field2058[var1][var2][var3 + 1]
               + this.field2058[var1][var2 + 1][var3 + 1]
         )
         / 4;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("kh")
   public boolean method4365() {
      return this.field2031 && this.field2034 != -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;IIIILee;Lee;IIJI)V")
   @ObfuscatedName("ya")
   public static void method4204(Scene var0, int var1, int var2, int var3, int var4, Renderable var5, Renderable var6, int var7, int var8, long var9, int var11) {
      if (var0 == null) {
         var0.method4214(var1, var1, var1, var1, var1, var5, var1, var9, var1, var1, var1, var1);
      }

      if (var5 != null || var6 != null) {
         WallObject var12 = new WallObject();
         var12.field2238 = var9 * -2636099314526831141L;
         var12.field2239 = var11 * -1331376248;
         var12.field2242 = var2 * 67219237 + 1685800512;
         var12.field2243 = var3 * -2139868032 + 1802625245;
         var12.field2240 = var4 * -1784019733;
         var12.field2247 = var5;
         var12.field2246 = var6;
         var12.field2244 = var7 * -1380680693;
         var12.field2241 = var8 * 1409055500;

         for (int var13 = var1; var13 >= 0; var13--) {
            if (var0.field2056[var13][var2][var3] == null) {
               var0.field2056[var13][var2][var3] = new Tile(var13, var2, var3);
            }
         }

         var0.field2056[var1][var2][var3].field1968 = var12;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Lew;)Z")
   @ObfuscatedName("ct")
   public static boolean method4222(Scene var0, Tile var1) {
      if (var0 == null) {
         var0.method4548();
      }

      if (var1.field1954 * -299736461 != var0.field2063) {
         var1.field1954 = var0.field2063 * -1699605829;
         var1.field1956 = var1.field1961 * -980607445;
         if (var0.field2048 >= var0.field2055.length) {
            var0.field2055 = Arrays.copyOf(var0.field2055, var0.field2055.length * 2);
         }

         var0.field2055[var0.field2048++] = var1;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("mf")
   boolean method4346(int var1, int var2, int var3, int var4) {
      if (!method4325(var1, var2, var3, var4)) {
         return method4319(var1, var2, var3, var4);
      } else {
         method4330(var1, var2, var3, var4, false);
         int var5 = var1 * 256 + field2064;
         int var6 = var2 * 1024;
         int var7 = (var3 - this.field2049 - this.field2051 - 1) * 128;
         int var8 = (var4 - this.field2049 - this.field2052 - 1) * 128;

         for (int var9 = -field2128; var9 <= field2124; var9 += 128) {
            if (method4350(var7, field2070[var1] + var9, var8, var5, var6)) {
               return method4322(var1, var2, var3, var4, true);
            }
         }

         return method4322(var1, var2, var3, var4, false);
      }
   }

   public int getMinLevel() {
      return this.field2038;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lm")
   void method4369() {
      if (this.field2041) {
         this.field2041 = false;
         this.field2034 = this.field2024;
         this.field2035 = this.field2032;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ko")
   boolean method4343(int var1, int var2) {
      if (!method4302(field2140, field2138, var1, var2)) {
         return method4296(field2140, field2138, var1, var2);
      } else {
         method4307(field2140, field2138, var1, var2, false);
         boolean var3 = false;

         for (int var4 = -1; var4 <= 1; var4++) {
            for (int var5 = -1; var5 <= 1; var5++) {
               if (this.method4346(field2140, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140 + 1, field2138, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }

               if (this.method4346(field2140 + 1, (field2138 + 1) % field2067, var1 + var4 + 1, var2 + var5 + 1)) {
                  var3 = true;
                  var4 = 2;
                  break;
               }
            }
         }

         return method4299(field2140, field2138, var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ln")
   public void method4373() {
      this.field2034 = -1;
      this.field2031 = false;
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;JI)V")
   @ObfuscatedName("bx")
   public void method4470(int var1, int var2, int var3, int var4, Renderable var5, long var6, int var8) {
      int var9 = var2 + this.field2146;
      int var10 = var3 + this.field2146;
      if (var5 != null) {
         int var11 = this.method4438(var1, var9, var10);
         GroundObject var12 = new GroundObject();
         var12.method3972(var5);
         var12.method3970(var2 * 128 + 64);
         var12.method3974(var3 * 128 + 64);
         var12.method3975(var4);
         var12.method3969(var6);
         var12.method3971(var8);
         rl10 var13 = this.field1991[var1][var9][var10];
         if (var13 == null) {
            this.field1991[var1][var9][var10] = var13 = new rl10(this, var11);
            this.method4456(var1, var9, var10);
         }

         if (!this.method4426(var11)) {
            this.method4483(var11, var1, var9, var10);
         }

         assert this.field2009[var11] == null;

         this.field2009[var11] = var12;
         this.field2004[var11] = this.field2004[var11] | 2048;
         this.field2015[var11] = this.field2015[var11] | 1080863910568919040L;
         if (var5 instanceof DynamicObject) {
            rl17 var14 = this.field2017[var9 >> 3][var10 >> 3];
            var14.field5703.add(var12);
            this.method4348((DynamicObject)var5);
         }

         this.method4513(var5, 0, var12.getX(), var12.getZ(), var12.getY(), var2, var3);
         var12.field1812 = var1;
         if (this.field2152) {
            GroundObjectSpawned var17 = new GroundObjectSpawned();
            var17.setTile(var13);
            var17.setGroundObject(var12);
            classOE.field4841.getCallbacks().post(var17);
         }

         if (this.field2152 && client.field1117 != null) {
            client.field1117.invalidateZone(this, var9 >> 3, var10 >> 3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILup;Lup;IIIZ)V")
   @ObfuscatedName("lz")
   public void method4374(int var1, int var2, int var3, classUP var4, classUP var5, int var6, int var7, int var8, boolean var9) {
      if (var1 < 0) {
         var1 = 0;
      } else if (var1 >= this.field2054 * 128) {
         var1 = this.field2054 * 1720506926 - 1;
      }

      if (var3 < 0) {
         var3 = 0;
      } else if (var3 >= this.field2030 * -422969528) {
         var3 = this.field2030 * 128 - 1;
      }

      var4.method11490(1, 4160, (byte)15);
      this.field2085 = var4.method11510(2129787521);
      this.field2088 = var4.method11512((byte)116);
      this.field2092 = var5.method11510(2112225602);
      this.field2090 = var5.method11512((byte)111);
      method4337((var4.method11500((short)-30976) - field2064) / -1632316410, var5.method11500((short)-13978) / 1024);
      this.field2116 = var1;
      this.field2117 = var2;
      this.field2114 = var3;
      this.field2112 = var1 / -866401737;
      this.field2122 = var3 / -1459648397;
      this.field2119 = var7 / -799547811;
      this.field2123 = var8 / -1224939909;
      this.field2025 = var6;
      IntProjection var10 = new IntProjection(var1, var2, var3, var4, var5, var7, var8);
      if (this.field2147 != null) {
         this.field2147.method5243(classFZ.field2517);
         this.field2147.method5192(0, var4, var5, 0, 0, 0, -1L);
      }

      if (classFH.method4755()) {
         classFH.field2255.field2215 = 0;
         this.method4454(var10, var9, true);
         classFH.field2255.field2215 = 1;
         this.method4454(var10, var9, false);
         classFH.field2255.field2215 = 2;
      } else {
         classFH.field2255.field2215 = 2;
         this.method4454(var10, var9, true);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lb")
   public void method4377(int var1) {
      this.field2143 = var1;
      this.field2024 = -1;
      this.field2032 = -1;
      this.field2040 = -1.0F;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lg")
   public void method4378(int var1) {
      this.field2143 = var1;
      this.field2024 = -1;
      this.field2032 = -1;
      this.field2040 = -1.0F;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIIIZ)Z")
   @ObfuscatedName("jj")
   static boolean method4316(BitSet var0, int var1, int var2, int var3, int var4, boolean var5) {
      var0.set(var1 * field2130 + var2 * field2132 + var3 * field2125 + var4, var5);
      return var5;
   }

   @ObfuscatedSignature(descriptor = "(Lez;III)I")
   @ObfuscatedName("rv")
   public static int method4277(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method4278(var1, var1, var1);
      }

      return (
            var0.field2058[var1][var2][var3]
               + var0.field2058[var1][var2 + 1][var3]
               + var0.field2058[var1][var2][var3 + 1]
               + var0.field2058[var1][var2 + 1][var3 + 1]
         )
         / 4;
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Z)V")
   @ObfuscatedName("lc")
   void method4381(Projection var1, boolean var2) {
      this.field2039 = 0;

      for (int var3 = this.field2038; var3 < this.field2057; var3++) {
         Tile[][] var4 = this.field2056[var3];

         for (int var5 = this.field2118; var5 < this.field2121; var5++) {
            for (int var6 = this.field2111; var6 < this.field2120; var6++) {
               Tile var7 = var4[var5][var6];
               if (var7 != null
                  && var7.field1962 * 917688813 <= this.field2025
                  && (
                     !var2
                        || this.method4340(var5 - this.field2112 + this.field2051 + this.field2049, var6 - this.field2122 + this.field2052 + this.field2049)
                        || this.field2058[var3][var5][var6] - this.field2117 >= -465454206
                  )) {
                  this.method4423(var1, var7);
               }
            }
         }
      }

      this.method4368();
      classFH.field2255.field2213.method4843(this.field2059);
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Z)V")
   @ObfuscatedName("dv")
   void method4382(Projection var1, boolean var2) {
      this.field2039 = 0;

      for (int var3 = this.field2038; var3 < this.field2057; var3++) {
         Tile[][] var4 = this.field2056[var3];

         for (int var5 = this.field2118; var5 < this.field2121; var5++) {
            for (int var6 = this.field2111; var6 < this.field2120; var6++) {
               Tile var7 = var4[var5][var6];
               if (var7 != null
                  && var7.field1962 * 1390661409 <= this.field2025
                  && (
                     !var2
                        || this.method4340(var5 - this.field2112 + this.field2051 + this.field2049, var6 - this.field2122 + this.field2052 + this.field2049)
                        || this.field2058[var3][var5][var6] - this.field2117 >= 2000
                  )) {
                  this.method4423(var1, var7);
               }
            }
         }
      }

      this.method4368();
      classFH.field2255.field2213.method4843(this.field2059);
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Z)V")
   @ObfuscatedName("lu")
   void method4383(Projection var1, boolean var2) {
      this.field2039 = 0;

      for (int var3 = this.field2038; var3 < this.field2057; var3++) {
         Tile[][] var4 = this.field2056[var3];

         for (int var5 = this.field2118; var5 < this.field2121; var5++) {
            for (int var6 = this.field2111; var6 < this.field2120; var6++) {
               Tile var7 = var4[var5][var6];
               if (var7 != null
                  && var7.field1962 * 1390661409 <= this.field2025
                  && (
                     !var2
                        || this.method4340(var5 - this.field2112 + this.field2051 + this.field2049, var6 - this.field2122 + this.field2052 + this.field2049)
                        || this.field2058[var3][var5][var6] - this.field2117 >= 2000
                  )) {
                  this.method4423(var1, var7);
               }
            }
         }
      }

      this.method4368();
      classFH.field2255.field2213.method4843(this.field2059);
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Z)V")
   @ObfuscatedName("lv")
   void method4385(Projection var1, boolean var2) {
      this.field2039 = 0;

      for (int var3 = this.field2038; var3 < this.field2057; var3++) {
         Tile[][] var4 = this.field2056[var3];

         for (int var5 = this.field2118; var5 < this.field2121; var5++) {
            for (int var6 = this.field2111; var6 < this.field2120; var6++) {
               Tile var7 = var4[var5][var6];
               if (var7 != null) {
                  if (var7.field1962 * -1037639466 <= this.field2025
                     && (
                        !var2
                           || this.method4340(var5 - this.field2112 + this.field2051 + this.field2049, var6 - this.field2122 + this.field2052 + this.field2049)
                           || this.field2058[var3][var5][var6] - this.field2117 >= 1289531338
                     )) {
                     var7.field1972 = true;
                     var7.field1974 = true;
                     var7.field1973 = var7.field1961 * 1562340076 > 0 || var7.method4122((short)10726);
                     this.field2039++;
                  } else {
                     var7.field1972 = false;
                     var7.field1974 = false;
                     var7.field1960 = 0;
                  }
               }
            }
         }
      }

      int var14 = Math.abs(this.field2051);
      int var15 = Math.abs(this.field2052);

      for (int var16 = this.field2038; var16 < this.field2057; var16++) {
         Tile[][] var18 = this.field2056[var16];

         for (int var20 = -(this.field2049 + var14); var20 <= 0; var20++) {
            int var8 = this.field2112 + var20;
            int var9 = this.field2112 - var20;
            if (var8 >= this.field2118 || var9 < this.field2121) {
               for (int var10 = -(this.field2049 + var15); var10 <= 0; var10++) {
                  int var11 = this.field2122 + var10;
                  int var12 = this.field2122 - var10;
                  if (var8 >= this.field2118 && var8 < this.field2121) {
                     if (var11 >= this.field2111 && var11 < this.field2120) {
                        Tile var13 = var18[var8][var11];
                        if (var13 != null && var13.field1972) {
                           this.method4412(var1, var13, true);
                        }
                     }

                     if (var12 >= this.field2111 && var12 < this.field2120) {
                        Tile var27 = var18[var8][var12];
                        if (var27 != null && var27.field1972) {
                           this.method4412(var1, var27, true);
                        }
                     }
                  }

                  if (var9 >= this.field2118 && var9 < this.field2121) {
                     if (var11 >= this.field2111 && var11 < this.field2120) {
                        Tile var28 = var18[var9][var11];
                        if (var28 != null && var28.field1972) {
                           this.method4412(var1, var28, true);
                        }
                     }

                     if (var12 >= this.field2111 && var12 < this.field2120) {
                        Tile var29 = var18[var9][var12];
                        if (var29 != null && var29.field1972) {
                           this.method4412(var1, var29, true);
                        }
                     }
                  }

                  if (this.field2039 == 0) {
                     this.method4368();
                     classFH.field2255.field2213.method4843(this.field2059);
                     return;
                  }
               }
            }
         }
      }

      for (int var17 = this.field2038; var17 < this.field2057; var17++) {
         Tile[][] var19 = this.field2056[var17];

         for (int var21 = -(this.field2049 + var14); var21 <= 0; var21++) {
            int var22 = this.field2115 + var21;
            int var23 = this.field2115 - var21;
            if (var22 >= this.field2118 || var23 < this.field2121) {
               for (int var24 = -(this.field2049 + var15); var24 <= 0; var24++) {
                  int var25 = this.field2113 + var24;
                  int var26 = this.field2113 - var24;
                  if (var22 >= this.field2118 && var22 < this.field2121) {
                     if (var25 >= this.field2111 && var25 < this.field2120) {
                        Tile var30 = var19[var22][var25];
                        if (var30 != null && var30.field1972) {
                           this.method4412(var1, var30, false);
                        }
                     }

                     if (var26 >= this.field2111 && var26 < this.field2120) {
                        Tile var31 = var19[var22][var26];
                        if (var31 != null && var31.field1972) {
                           this.method4412(var1, var31, false);
                        }
                     }
                  }

                  if (var23 >= this.field2118 && var23 < this.field2121) {
                     if (var25 >= this.field2111 && var25 < this.field2120) {
                        Tile var32 = var19[var23][var25];
                        if (var32 != null && var32.field1972) {
                           this.method4412(var1, var32, false);
                        }
                     }

                     if (var26 >= this.field2111 && var26 < this.field2120) {
                        Tile var33 = var19[var23][var26];
                        if (var33 != null && var33.field1972) {
                           this.method4412(var1, var33, false);
                        }
                     }
                  }

                  if (this.field2039 == 0) {
                     this.method4368();
                     classFH.field2255.field2213.method4843(this.field2059);
                     return;
                  }
               }
            }
         }
      }

      classFH.field2255.field2213.method4843(this.field2059);
      this.method4368();
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lew;Z)V")
   @ObfuscatedName("la")
   void method4386(Projection var1, Tile var2, boolean var3) {
      this.field2047.method7946(var2);

      label661:
      while (true) {
         Tile var4;
         int var5;
         int var6;
         int var7;
         int var8;
         Tile[][] var9;
         while (true) {
            var4 = (Tile)this.field2047.method7951();
            if (var4 == null) {
               return;
            }

            if (var4.field1974) {
               var5 = var4.field1950 * 322732253;
               var6 = var4.field1955 * -1784116547;
               var7 = var4.field1947 * 1483076705;
               var8 = var4.field1948 * 800564741;
               var9 = this.field2056[var7];
               if (!var4.field1972) {
                  break;
               }

               if (var3) {
                  if (var7 > 0) {
                     Tile var10 = this.field2056[var7 - 1][var5][var6];
                     if (var10 != null && var10.field1974) {
                        continue;
                     }
                  }

                  if (var5 <= this.field2112 && var5 > this.field2118) {
                     Tile var21 = var9[var5 - 1][var6];
                     if (var21 != null && var21.field1974 && (var21.field1972 || (var4.field1952 * -82581941 & 1) == 0)) {
                        continue;
                     }
                  }

                  if (var5 >= this.field2112 && var5 < this.field2121 - 1) {
                     Tile var22 = var9[var5 + 1][var6];
                     if (var22 != null && var22.field1974 && (var22.field1972 || (var4.field1952 * 1720302903 & 4) == 0)) {
                        continue;
                     }
                  }

                  if (var6 <= this.field2122 && var6 > this.field2111) {
                     Tile var23 = var9[var5][var6 - 1];
                     if (var23 != null && var23.field1974 && (var23.field1972 || (var4.field1952 * 533122567 & 8) == 0)) {
                        continue;
                     }
                  }

                  if (var6 >= this.field2122 && var6 < this.field2120 - 1) {
                     Tile var24 = var9[var5][var6 + 1];
                     if (var24 != null && var24.field1974 && (var24.field1972 || (var4.field1952 * -785027619 & 2) == 0)) {
                        continue;
                     }
                  }
               } else {
                  var3 = true;
               }

               var4.field1972 = false;
               if (var4.field1967 != null) {
                  Tile var25 = var4.field1967;
                  if (var25.field1970 != null) {
                     if (!this.method4399(0, var5, var6)) {
                        var1.vmethod426(this, var25.field1970, 0, var5, var6, -1198642994);
                     }
                  } else if (var25.field1971 != null && !this.method4399(0, var5, var6)) {
                     var1.vmethod431(this, var25.field1971, var5, var6, 2026704799);
                  }

                  WallObject var11 = var25.field1968;
                  if (var11 != null) {
                     var1.vmethod435(
                        var11.field2247,
                        0,
                        var11.field2242 * -777838807,
                        var11.field2240 * -88329071,
                        var11.field2243 * 31139801,
                        var11.field2238 * 7159317397185328723L
                     );
                  }

                  for (int var12 = 0; var12 < var25.field1961 * 915128840; var12++) {
                     GameObject var13 = var25.field1949[var12];
                     if (var13 != null) {
                        var1.vmethod435(
                           var13.field2198,
                           var13.field2187 * 470594389,
                           var13.field2193 * -6821234,
                           var13.field2188 * 1269801851,
                           var13.field2194 * -1706390585,
                           var13.field2183 * 1745853678706228013L
                        );
                     }
                  }

                  if (var25.field1953 != null && !var25.field1953.isEmpty()) {
                     for (int var46 = 0; var46 < var25.field1953.size(); var46++) {
                        GameObject var53 = (GameObject)var25.field1953.get(var46);
                        var1.vmethod435(
                           var53.field2198,
                           var53.field2187 * 470594389,
                           var53.field2193 * -1908392223,
                           var53.field2188 * 1269801851,
                           var53.field2194 * 871859552,
                           var53.field2183 * 1745853678706228013L
                        );
                     }
                  }
               }

               boolean var26 = false;
               if (var4.field1970 != null) {
                  if (!this.method4399(var8, var5, var6)) {
                     var26 = true;
                     if (var4.field1970.field2284 * -665850825 != 12345678 || var7 <= this.field2042) {
                        var1.vmethod426(this, var4.field1970, var8, var5, var6, -628387858);
                     }
                  }
               } else if (var4.field1971 != null && !this.method4399(var8, var5, var6)) {
                  var26 = true;
                  var1.vmethod431(this, var4.field1971, var5, var6, -1950055723);
               }

               int var34 = 0;
               int var47 = 0;
               WallObject var54 = var4.field1968;
               DecorativeObject var14 = var4.field1969;
               if (var54 != null || var14 != null) {
                  if (this.field2112 == var5) {
                     var34++;
                  } else if (this.field2112 < var5) {
                     var34 += 2;
                  }

                  if (this.field2122 == var6) {
                     var34 += 3;
                  } else if (this.field2122 > var6) {
                     var34 += 6;
                  }

                  var47 = field2068[var34];
                  var4.field1964 = field2076[var34] * 893962788;
               }

               if (var54 != null) {
                  if ((var54.field2244 * 1548562339 & field2065[var34]) != 0) {
                     if (var54.field2244 * -2125787007 == 16) {
                        var4.field1960 = -2017892742;
                        var4.field1959 = field2077[var34] * -271757815;
                        var4.field1958 = -1546635933 - var4.field1959 * -640457819;
                     } else if (var54.field2244 * 1548562339 == -344914803) {
                        var4.field1960 = 461324646;
                        var4.field1959 = field2066[var34] * -1241279141;
                        var4.field1958 = 1770432718 - var4.field1959 * -640457819;
                     } else if (var54.field2244 * 1548562339 == 66643623) {
                        var4.field1960 = 922649292;
                        var4.field1959 = field2079[var34] * -271757815;
                        var4.field1958 = -754101860 - var4.field1959 * -640457819;
                     } else {
                        var4.field1960 = 1900479878;
                        var4.field1959 = field2080[var34] * -271757815;
                        var4.field1958 = -1486440048 - var4.field1959 * -640457819;
                     }
                  } else {
                     var4.field1960 = 0;
                  }

                  if ((var54.field2244 * 1548562339 & var47) != 0 && !this.method4401(var8, var5, var6, var54.field2244 * 1396363825)) {
                     var1.vmethod435(
                        var54.field2247,
                        0,
                        var54.field2242 * 1385335661,
                        var54.field2240 * 1315862339,
                        var54.field2243 * 31139801,
                        var54.field2238 * 7159317397185328723L
                     );
                  }

                  if ((var54.field2241 * -1034886024 & var47) != 0 && !this.method4401(var8, var5, var6, var54.field2241 * -1530602966)) {
                     var1.vmethod435(
                        var54.field2246,
                        0,
                        var54.field2242 * 455614865,
                        var54.field2240 * 1315862339,
                        var54.field2243 * 1432549217,
                        var54.field2238 * 7159317397185328723L
                     );
                  }
               }

               if (var14 != null && !this.method4404(var8, var5, var6, var14.field2277.field1741 * -944516245)) {
                  if ((var14.field2269 * 254285683 & var47) != 0) {
                     var1.vmethod435(
                        var14.field2277,
                        0,
                        var14.field2273 * 1887524728 + var14.field2274 * -1188251123,
                        var14.field2271 * -939247107,
                        var14.field2266 * -361702377 + var14.field2268 * 1701104232,
                        var14.field2264 * -3608662145112923099L
                     );
                  } else if (var14.field2269 * 254285683 == 1440807484) {
                     int var15 = var14.field2273 * 533577138 - this.field2116;
                     int var16 = var14.field2266 * -361702377 - this.field2114;
                     int var17 = var14.field2265 * -1474799858;
                     int var18;
                     if (var17 != 1 && var17 != 2) {
                        var18 = var15;
                     } else {
                        var18 = -var15;
                     }

                     int var19;
                     if (var17 != 2 && var17 != 3) {
                        var19 = var16;
                     } else {
                        var19 = -var16;
                     }

                     if (var19 < var18) {
                        var1.vmethod435(
                           var14.field2277,
                           0,
                           var14.field2273 * 279930749 + var14.field2274 * -404927711,
                           var14.field2271 * 550212484,
                           var14.field2266 * -946164839 + var14.field2268 * 856605829,
                           var14.field2264 * -3608662145112923099L
                        );
                     } else if (var14.field2276 != null) {
                        var1.vmethod435(
                           var14.field2276,
                           0,
                           var14.field2273 * 1766830743 + var14.field2267 * -1085897215,
                           var14.field2271 * 770036540,
                           var14.field2266 * -42737406 + var14.field2270 * 513256357,
                           var14.field2264 * -3608662145112923099L
                        );
                     }
                  }
               }

               if (var26) {
                  GroundObject var63 = var4.field1965;
                  if (var63 != null) {
                     var1.vmethod435(
                        var63.field1814,
                        0,
                        var63.field1804 * -2054255181,
                        var63.field1805 * -276908185,
                        var63.field1806 * 1258433260,
                        var63.field1813 * 5132338419383099835L
                     );
                  }

                  ItemLayer var69 = var4.field1966;
                  if (var69 != null && var69.field1836 * 218008627 == 0) {
                     if (var69.field1844 != null) {
                        var1.vmethod435(
                           var69.field1844, 0, var69.field1838 * -2075649563, var69.field1839 * 1755095600, var69.field1837 * -2135559555, var69.field1842
                        );
                     }

                     if (var69.field1845 != null) {
                        var1.vmethod435(
                           var69.field1845, 0, var69.field1838 * 1220529357, var69.field1839 * 1186822141, var69.field1837 * -1209739927, var69.field1842
                        );
                     }

                     if (var69.field1843 != null) {
                        var1.vmethod435(
                           var69.field1843, 0, var69.field1838 * -1343340595, var69.field1839 * 1186822141, var69.field1837 * -1370360400, var69.field1842
                        );
                     }
                  }
               }

               int var64 = var4.field1952 * -785027619;
               if (var64 != 0) {
                  if (var5 < this.field2112 && var5 >= this.field2118 && var5 < this.field2121 - 1 && (var64 & 4) != 0) {
                     Tile var70 = var9[var5 + 1][var6];
                     if (var70 != null && var70.field1974) {
                        this.field2047.method7946(var70);
                     }
                  }

                  if (var6 < this.field2122 && var6 >= this.field2111 && var6 < this.field2120 - 1 && (var64 & 2) != 0) {
                     Tile var71 = var9[var5][var6 + 1];
                     if (var71 != null && var71.field1974) {
                        this.field2047.method7946(var71);
                     }
                  }

                  if (var5 > this.field2112 && var5 > this.field2118 && var5 < this.field2121 && (var64 & 1) != 0) {
                     Tile var72 = var9[var5 - 1][var6];
                     if (var72 != null && var72.field1974) {
                        this.field2047.method7946(var72);
                     }
                  }

                  if (var6 > this.field2122 && var6 > this.field2111 && var6 < this.field2120 && (var64 & 8) != 0) {
                     Tile var73 = var9[var5][var6 - 1];
                     if (var73 != null && var73.field1974) {
                        this.field2047.method7946(var73);
                     }
                  }
               }
               break;
            }
         }

         if (var4.field1960 * 1786861211 != 0) {
            boolean var27 = true;

            for (int var35 = 0; var35 < var4.field1961 * 2143111306; var35++) {
               if (var4.field1949[var35].field2195 * 1585427195 != this.field2063
                  && (var4.field1951[var35] & var4.field1960 * -438031503) == var4.field1959 * 392470585) {
                  var27 = false;
                  break;
               }
            }

            if (var27) {
               WallObject var36 = var4.field1968;
               if (!this.method4401(var8, var5, var6, var36.field2244 * 1548562339)) {
                  var1.vmethod435(
                     var36.field2247,
                     0,
                     var36.field2242 * -777838807,
                     var36.field2240 * 1078421609,
                     var36.field2243 * -959496816,
                     var36.field2238 * 7159317397185328723L
                  );
               }

               var4.field1960 = 0;
            }
         }

         if (var4.field1973) {
            try {
               var4.field1973 = false;
               int var28 = 0;

               label609:
               for (int var37 = 0; var37 < var4.field1961 * -389646721; var37++) {
                  GameObject var48 = var4.field1949[var37];
                  if (var48.field2195 * 943209940 != this.field2063) {
                     for (int var55 = var48.field2186 * -407291055; var55 <= var48.field2184 * 304142753; var55++) {
                        for (int var59 = var48.field2192 * -555977777; var59 <= var48.field2190 * 1020923948; var59++) {
                           Tile var65 = var9[var55][var59];
                           if (var65.field1972) {
                              var4.field1973 = true;
                              continue label609;
                           }

                           if (var65.field1960 * -438031503 != 0) {
                              int var74 = 0;
                              if (var55 > var48.field2186 * 1138017605) {
                                 var74++;
                              }

                              if (var55 < var48.field2184 * 304142753) {
                                 var74 += 4;
                              }

                              if (var59 > var48.field2192 * -35895706) {
                                 var74 += 8;
                              }

                              if (var59 < var48.field2190 * 291461487) {
                                 var74 += 2;
                              }

                              if ((var74 & var65.field1960 * -438031503) == var4.field1958 * 1319121170) {
                                 var4.field1973 = true;
                                 continue label609;
                              }
                           }
                        }
                     }

                     this.field2023[var28++] = var48;
                     var48.field2189 = this.method4393(var48, this.field2112, this.field2122) * -1587470917;
                  }
               }

               if (var4.method4122((short)14124)) {
                  for (int var38 = 0; var38 < var4.field1953.size(); var38++) {
                     GameObject var49 = (GameObject)var4.field1953.get(var38);
                     if (var49.field2195 * -176899493 != this.field2063) {
                        this.field2023[var28++] = var49;
                        var49.field2189 = this.method4393(var49, this.field2112, this.field2122) * -753226779;
                     }
                  }
               }

               while (true) {
                  int var39 = 1683533221;
                  int var50 = -1;

                  for (int var56 = 0; var56 < var28; var56++) {
                     GameObject var60 = this.field2023[var56];
                     if (var60.field2195 * -187532341 != this.field2063) {
                        if (var60.field2189 * 835963475 > var39) {
                           var39 = var60.field2189 * -1826212277;
                           var50 = var56;
                        } else if (var60.field2189 * 835963475 == var39) {
                           int var66 = var60.field2193 * -1405731525 - this.field2116;
                           int var75 = var60.field2194 * -1062449319 - this.field2114;
                           int var78 = this.field2023[var50].field2193 * -1405731525 - this.field2116;
                           int var79 = this.field2023[var50].field2194 * -1706390585 - this.field2114;
                           if (var66 * var66 + var75 * var75 > var78 * var78 + var79 * var79) {
                              var50 = var56;
                           }
                        }
                     }
                  }

                  if (var50 == -1) {
                     if (var4.field1973) {
                        continue label661;
                     }
                     break;
                  }

                  GameObject var57 = this.field2023[var50];
                  var57.field2195 = this.field2063 * -1422097183;
                  if (!this.method4406(
                     var8,
                     var57.field2186 * 1138017605,
                     var57.field2184 * 304142753,
                     var57.field2192 * -555977777,
                     var57.field2190 * -879789992,
                     var57.field2198.field1741 * -1256242689
                  )) {
                     var1.vmethod435(
                        var57.field2198,
                        var57.field2187 * -307690925,
                        var57.field2193 * -1405731525,
                        var57.field2188 * 1269801851,
                        var57.field2194 * -1706390585,
                        var57.field2183 * 1745853678706228013L
                     );
                  }

                  for (int var61 = var57.field2186 * 928438506; var61 <= var57.field2184 * -1814976862; var61++) {
                     for (int var67 = var57.field2192 * -271175504; var67 <= var57.field2190 * -544424899; var67++) {
                        Tile var76 = var9[var61][var67];
                        if (var76.field1960 * -438031503 != 0) {
                           this.field2047.method7946(var76);
                        } else if ((var61 != var5 || var67 != var6) && var76.field1974) {
                           this.field2047.method7946(var76);
                        }
                     }
                  }
               }
            } catch (Exception var20) {
               var4.field1973 = false;
            }
         }

         if (var4.field1974 && var4.field1960 * -438031503 == 0) {
            if (var5 <= this.field2112 && var5 > this.field2118) {
               Tile var29 = var9[var5 - 1][var6];
               if (var29 != null && var29.field1974) {
                  continue;
               }
            }

            if (var5 >= this.field2112 && var5 < this.field2121 - 1) {
               Tile var30 = var9[var5 + 1][var6];
               if (var30 != null && var30.field1974) {
                  continue;
               }
            }

            if (var6 <= this.field2122 && var6 > this.field2111) {
               Tile var31 = var9[var5][var6 - 1];
               if (var31 != null && var31.field1974) {
                  continue;
               }
            }

            if (var6 >= this.field2122 && var6 < this.field2120 - 1) {
               Tile var32 = var9[var5][var6 + 1];
               if (var32 != null && var32.field1974) {
                  continue;
               }
            }

            var4.field1974 = false;
            this.field2039--;
            ItemLayer var33 = var4.field1966;
            if (var33 != null && var33.field1836 * 218008627 != 0) {
               if (var33.field1844 != null) {
                  var1.vmethod435(
                     var33.field1844,
                     0,
                     var33.field1838 * 876965431,
                     var33.field1839 * 1186822141 - var33.field1836 * 218008627,
                     var33.field1837 * -502239712,
                     var33.field1842
                  );
               }

               if (var33.field1845 != null) {
                  var1.vmethod435(
                     var33.field1845,
                     0,
                     var33.field1838 * -892202585,
                     var33.field1839 * 1186822141 - var33.field1836 * 218008627,
                     var33.field1837 * -312810684,
                     var33.field1842
                  );
               }

               if (var33.field1843 != null) {
                  var1.vmethod435(
                     var33.field1843,
                     0,
                     var33.field1838 * 1020081250,
                     var33.field1839 * 961145992 - var33.field1836 * -949399200,
                     var33.field1837 * -1209739927,
                     var33.field1842
                  );
               }
            }

            if (var4.field1964 * 1986405177 != 0) {
               DecorativeObject var40 = var4.field1969;
               if (var40 != null && !this.method4404(var8, var5, var6, var40.field2277.field1741 * -1256242689)) {
                  if ((var40.field2269 * 254285683 & var4.field1964 * 1986405177) != 0) {
                     var1.vmethod435(
                        var40.field2277,
                        0,
                        var40.field2273 * 1481322256 + var40.field2274 * -404927711,
                        var40.field2271 * 1698079433,
                        var40.field2266 * -361702377 + var40.field2268 * -1127816319,
                        var40.field2264 * -3608662145112923099L
                     );
                  } else if (var40.field2269 * 254285683 == -567230744) {
                     int var51 = var40.field2273 * -4263128 - this.field2116;
                     int var58 = var40.field2266 * -361702377 - this.field2114;
                     int var62 = var40.field2265 * -671066975;
                     int var68;
                     if (var62 != 1 && var62 != 2) {
                        var68 = var51;
                     } else {
                        var68 = -var51;
                     }

                     int var77;
                     if (var62 != 2 && var62 != 3) {
                        var77 = var58;
                     } else {
                        var77 = -var58;
                     }

                     if (var77 >= var68) {
                        var1.vmethod435(
                           var40.field2277,
                           0,
                           var40.field2273 * 2011931484 + var40.field2274 * 745550696,
                           var40.field2271 * 1787470087,
                           var40.field2266 * -361702377 + var40.field2268 * -348664289,
                           var40.field2264 * -3608662145112923099L
                        );
                     } else if (var40.field2276 != null) {
                        var1.vmethod435(
                           var40.field2276,
                           0,
                           var40.field2273 * -211985042 + var40.field2267 * -1085897215,
                           var40.field2271 * 1698079433,
                           var40.field2266 * -868527098 + var40.field2270 * 513256357,
                           var40.field2264 * -3608662145112923099L
                        );
                     }
                  }
               }

               WallObject var52 = var4.field1968;
               if (var52 != null) {
                  if ((var52.field2241 * -1413337351 & var4.field1964 * 1986405177) != 0 && !this.method4401(var8, var5, var6, var52.field2241 * 193767008)) {
                     var1.vmethod435(
                        var52.field2246,
                        0,
                        var52.field2242 * 118037168,
                        var52.field2240 * 411396686,
                        var52.field2243 * -16623385,
                        var52.field2238 * 7159317397185328723L
                     );
                  }

                  if ((var52.field2244 * 1548562339 & var4.field1964 * 1986405177) != 0 && !this.method4401(var8, var5, var6, var52.field2244 * 1548562339)) {
                     var1.vmethod435(
                        var52.field2247,
                        0,
                        var52.field2242 * -777838807,
                        var52.field2240 * -517437493,
                        var52.field2243 * -1045252521,
                        var52.field2238 * 7159317397185328723L
                     );
                  }
               }
            }

            if (var7 < this.field2057 - 1) {
               Tile var41 = this.field2056[var7 + 1][var5][var6];
               if (var41 != null && var41.field1974) {
                  this.field2047.method7946(var41);
               }
            }

            if (var5 < this.field2112 && var5 >= this.field2118 && var5 < this.field2121 - 1) {
               Tile var42 = var9[var5 + 1][var6];
               if (var42 != null && var42.field1974) {
                  this.field2047.method7946(var42);
               }
            }

            if (var6 < this.field2122 && var6 >= this.field2111 && var6 < this.field2120 - 1) {
               Tile var43 = var9[var5][var6 + 1];
               if (var43 != null && var43.field1974) {
                  this.field2047.method7946(var43);
               }
            }

            if (var5 > this.field2112 && var5 > this.field2118 && var5 < this.field2121) {
               Tile var44 = var9[var5 - 1][var6];
               if (var44 != null && var44.field1974) {
                  this.field2047.method7946(var44);
               }
            }

            if (var6 > this.field2122 && var6 > this.field2111 && var6 < this.field2120) {
               Tile var45 = var9[var5][var6 - 1];
               if (var45 != null && var45.field1974) {
                  this.field2047.method7946(var45);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("la")
   public static void method4476(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      Scene var7 = classCQ.field1218.field1687;
      int var8 = var1 + var7.field2146;
      int var9 = var2 + var7.field2146;
      if (var5 == 2) {
         GameObject var10 = var7.method4531(var0, var1, var2);
         if (var10 != null && var10.field2198 instanceof DynamicObject && !((DynamicObject)var10.field2198).method3730()) {
            return;
         }
      } else if (var5 == 3) {
         GroundObject var11 = var7.method4535(var0, var1, var2);
         if (var11 != null && var11.field1814 instanceof DynamicObject && !((DynamicObject)var11.field1814).method3730()) {
            return;
         }
      }

      if (client.field1117 != null) {
         client.field1117.invalidateZone(var7, var8 >> 3, var9 >> 3);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;IIIIIIIILee;IZJI)Z")
   @ObfuscatedName("qh")
   public static boolean method4216(
      Scene var0,
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      Renderable var9,
      int var10,
      boolean var11,
      long var12,
      int var14
   ) {
      if (var0 == null) {
         var0.method4462(var1, var1);
      }

      for (int var15 = var2; var15 < var2 + var4; var15++) {
         for (int var16 = var3; var16 < var3 + var5; var16++) {
            if (var15 < 0 || var16 < 0 || var15 >= var0.field2054 || var16 >= var0.field2030) {
               return false;
            }

            Tile var17 = var0.field2056[var1][var15][var16];
            if (var17 != null && var17.field1961 * -8834507 >= 5) {
               return false;
            }
         }
      }

      GameObject var21 = new GameObject();
      var21.field2183 = var12 * -4819202259227702107L;
      var21.field2182 = var14 * -575080916;
      var21.field2185 = var1 * -1965758605;
      var21.field2193 = var6 * -563286342;
      var21.field2194 = var7 * -333886098;
      var21.field2188 = var8 * -1294023757;
      var21.field2198 = var9;
      var21.field2187 = var10 * -128126345;
      var21.field2186 = var2 * -1266763122;
      var21.field2192 = var3 * -1058819281;
      var21.field2184 = (var2 + var4 - 1) * 1384467041;
      var21.field2190 = (var3 + var5 - 1) * 1191492373;

      for (int var22 = var2; var22 < var2 + var4; var22++) {
         for (int var23 = var3; var23 < var3 + var5; var23++) {
            int var18 = 0;
            if (var22 > var2) {
               var18++;
            }

            if (var22 < var2 + var4 - 1) {
               var18 += 4;
            }

            if (var23 > var3) {
               var18 += 8;
            }

            if (var23 < var3 + var5 - 1) {
               var18 += 2;
            }

            for (int var19 = var1; var19 >= 0; var19--) {
               if (var0.field2056[var19][var22][var23] == null) {
                  var0.field2056[var19][var22][var23] = new Tile(var19, var22, var23);
               }
            }

            Tile var24 = var0.field2056[var1][var22][var23];
            if (var11) {
               var0.method4219(var24);
            }

            var24.field1949[var24.field1961 * -901221775] = var21;
            var24.field1951[var24.field1961 * -933601066] = var18;
            var24.field1952 = (var24.field1952 * -785027619 | var18) * -1095478667;
            var24.field1961 += -71349889;
         }
      }

      return true;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIII)Z")
   @ObfuscatedName("jo")
   static boolean method4313(BitSet var0, int var1, int var2, int var3, int var4) {
      return var0.get(var1 * field2130 + var2 * field2132 + var3 * field2125 + var4);
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lew;)V")
   @ObfuscatedName("ls")
   void method4390(Projection var1, Tile var2) {
      if (var2 != null) {
         int var3 = var2.field1950 * -54806681;
         int var4 = var2.field1955 * -2067052011;
         int var5 = var2.field1947 * 1483076705;
         int var6 = var2.field1948 * 1938198001;
         if (var2.field1947 * -1270672859 == 0 && var2.field1967 != null) {
            this.method4423(var1, var2.field1967);
         }

         if (var2.field1970 != null) {
            if (!this.method4399(var6, var3, var4) && (var2.field1970.field2284 * 372976130 != -626353109 || var5 <= this.field2042)) {
               var1.vmethod426(this, var2.field1970, var6, var3, var4, 874226372);
            }
         } else if (var2.field1971 != null && !this.method4399(var6, var3, var4)) {
            var1.vmethod431(this, var2.field1971, var3, var4, 1484234154);
         }

         WallObject var7 = var2.field1968;
         if (var7 != null) {
            if (var7.field2247 != null && !this.method4401(var6, var3, var4, var7.field2244 * 1548562339)) {
               var1.vmethod435(
                  var7.field2247, 0, var7.field2242 * -777838807, var7.field2240 * 1315862339, var7.field2243 * 31139801, var7.field2238 * 7159317397185328723L
               );
            }

            if (var7.field2246 != null && !this.method4401(var6, var3, var4, var7.field2241 * -1748397631)) {
               var1.vmethod435(
                  var7.field2246, 0, var7.field2242 * -777838807, var7.field2240 * 1315862339, var7.field2243 * 31139801, var7.field2238 * 7159317397185328723L
               );
            }
         }

         DecorativeObject var8 = var2.field1969;
         if (var8 != null && !this.method4404(var6, var3, var4, var8.field2277.field1741 * -1256242689)) {
            if (var8.field2269 * 254285683 == 256) {
               var1.vmethod435(
                  var8.field2277,
                  0,
                  var8.field2273 * 1766830743 + var8.field2274 * -1373468864,
                  var8.field2271 * -1257265268,
                  var8.field2266 * 1115068667 + var8.field2268 * -963605192,
                  var8.field2264 * -3608662145112923099L
               );
               if (var8.field2276 != null) {
                  var1.vmethod435(
                     var8.field2276,
                     0,
                     var8.field2273 * 1766830743 + var8.field2267 * 1683764072,
                     var8.field2271 * 1698079433,
                     var8.field2266 * -361702377 + var8.field2270 * 513256357,
                     var8.field2264 * -3608662145112923099L
                  );
               }
            } else {
               var1.vmethod435(
                  var8.field2277,
                  0,
                  var8.field2273 * 1766830743 + var8.field2274 * 1385947540,
                  var8.field2271 * -607784492,
                  var8.field2266 * -361702377 + var8.field2268 * 856605829,
                  var8.field2264 * -3608662145112923099L
               );
            }
         }

         GroundObject var9 = var2.field1965;
         if (var9 != null) {
            var1.vmethod435(
               var9.field1814, 0, var9.field1804 * 1333275141, var9.field1805 * -276908185, var9.field1806 * 498815127, var9.field1813 * 5132338419383099835L
            );
         }

         ItemLayer var10 = var2.field1966;
         if (var10 != null) {
            if (var10.field1844 != null) {
               var1.vmethod435(
                  var10.field1844,
                  0,
                  var10.field1838 * -2078253856,
                  var10.field1839 * 1186822141 - var10.field1836 * -1224490135,
                  var10.field1837 * 1484661171,
                  var10.field1842
               );
            }

            if (var10.field1845 != null) {
               var1.vmethod435(
                  var10.field1845,
                  0,
                  var10.field1838 * -892202585,
                  var10.field1839 * 1186822141 - var10.field1836 * 218008627,
                  var10.field1837 * 1251483933,
                  var10.field1842
               );
            }

            if (var10.field1843 != null) {
               var1.vmethod435(
                  var10.field1843,
                  0,
                  var10.field1838 * -892202585,
                  var10.field1839 * 1186822141 - var10.field1836 * 218008627,
                  var10.field1837 * -1209739927,
                  var10.field1842
               );
            }
         }

         for (int var11 = 0; var11 < var2.field1961 * -389646721; var11++) {
            GameObject var12 = var2.field1949[var11];
            var12.field2195 = this.field2063 * 132222543;
            if (var12.field2186 * 1138017605 == var2.field1950 * -416180782
               && var12.field2192 * 695646168 == var2.field1955 * -2067052011
               && !this.method4406(
                  var6,
                  var12.field2186 * -1635386907,
                  var12.field2184 * 304142753,
                  var12.field2192 * 1148163100,
                  var12.field2190 * -1819860113,
                  var12.field2198.field1741 * -1256242689
               )) {
               try {
                  var1.vmethod435(
                     var12.field2198,
                     var12.field2187 * 1478041859,
                     var12.field2193 * -1405731525,
                     var12.field2188 * 1689974509,
                     var12.field2194 * -1706390585,
                     var12.field2183 * 1745853678706228013L
                  );
               } catch (Exception var15) {
               }
            }
         }

         if (var2.method4122((short)16115)) {
            for (int var16 = 0; var16 < var2.field1953.size(); var16++) {
               GameObject var17 = (GameObject)var2.field1953.get(var16);
               var17.field2195 = this.field2063 * 132222543;
               if (!this.method4406(
                  var6,
                  var2.field1950 * 929582739,
                  var2.field1950 * -629641082,
                  var2.field1955 * -2067052011,
                  var2.field1955 * -2067052011,
                  var17.field2198.field1741 * -1256242689
               )) {
                  try {
                     var1.vmethod435(
                        var17.field2198,
                        var17.field2187 * 470594389,
                        var17.field2193 * 628172248,
                        var17.field2188 * -1471224767,
                        var17.field2194 * -1706390585,
                        var17.field2183 * 1745853678706228013L
                     );
                  } catch (Exception var14) {
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lew;)V")
   @ObfuscatedName("lk")
   void method4391(Projection var1, Tile var2) {
      if (var2 != null) {
         int var3 = var2.field1950 * -54806681;
         int var4 = var2.field1955 * -2067052011;
         int var5 = var2.field1947 * 1483076705;
         int var6 = var2.field1948 * 800564741;
         if (var2.field1947 * 1483076705 == 0 && var2.field1967 != null) {
            this.method4423(var1, var2.field1967);
         }

         if (var2.field1970 != null) {
            if (!this.method4399(var6, var3, var4) && (var2.field1970.field2284 * -665850825 != 12345678 || var5 <= this.field2042)) {
               var1.vmethod426(this, var2.field1970, var6, var3, var4, 1178341524);
            }
         } else if (var2.field1971 != null && !this.method4399(var6, var3, var4)) {
            var1.vmethod431(this, var2.field1971, var3, var4, 1886840969);
         }

         WallObject var7 = var2.field1968;
         if (var7 != null) {
            if (var7.field2247 != null && !this.method4401(var6, var3, var4, var7.field2244 * 1548562339)) {
               var1.vmethod435(
                  var7.field2247, 0, var7.field2242 * -777838807, var7.field2240 * 1315862339, var7.field2243 * 31139801, var7.field2238 * 7159317397185328723L
               );
            }

            if (var7.field2246 != null && !this.method4401(var6, var3, var4, var7.field2241 * -1413337351)) {
               var1.vmethod435(
                  var7.field2246, 0, var7.field2242 * -777838807, var7.field2240 * 1315862339, var7.field2243 * 31139801, var7.field2238 * 7159317397185328723L
               );
            }
         }

         DecorativeObject var8 = var2.field1969;
         if (var8 != null && !this.method4404(var6, var3, var4, var8.field2277.field1741 * -1256242689)) {
            if (var8.field2269 * 254285683 == 256) {
               var1.vmethod435(
                  var8.field2277,
                  0,
                  var8.field2273 * 1766830743 + var8.field2274 * -404927711,
                  var8.field2271 * 1698079433,
                  var8.field2266 * -361702377 + var8.field2268 * 856605829,
                  var8.field2264 * -3608662145112923099L
               );
               if (var8.field2276 != null) {
                  var1.vmethod435(
                     var8.field2276,
                     0,
                     var8.field2273 * 1766830743 + var8.field2267 * -1085897215,
                     var8.field2271 * 1698079433,
                     var8.field2266 * -361702377 + var8.field2270 * 513256357,
                     var8.field2264 * -3608662145112923099L
                  );
               }
            } else {
               var1.vmethod435(
                  var8.field2277,
                  0,
                  var8.field2273 * 1766830743 + var8.field2274 * -404927711,
                  var8.field2271 * 1698079433,
                  var8.field2266 * -361702377 + var8.field2268 * 856605829,
                  var8.field2264 * -3608662145112923099L
               );
            }
         }

         GroundObject var9 = var2.field1965;
         if (var9 != null) {
            var1.vmethod435(
               var9.field1814, 0, var9.field1804 * 1333275141, var9.field1805 * -276908185, var9.field1806 * 498815127, var9.field1813 * 5132338419383099835L
            );
         }

         ItemLayer var10 = var2.field1966;
         if (var10 != null) {
            if (var10.field1844 != null) {
               var1.vmethod435(
                  var10.field1844,
                  0,
                  var10.field1838 * -892202585,
                  var10.field1839 * 1186822141 - var10.field1836 * 218008627,
                  var10.field1837 * -1209739927,
                  var10.field1842
               );
            }

            if (var10.field1845 != null) {
               var1.vmethod435(
                  var10.field1845,
                  0,
                  var10.field1838 * -892202585,
                  var10.field1839 * 1186822141 - var10.field1836 * 218008627,
                  var10.field1837 * -1209739927,
                  var10.field1842
               );
            }

            if (var10.field1843 != null) {
               var1.vmethod435(
                  var10.field1843,
                  0,
                  var10.field1838 * -892202585,
                  var10.field1839 * 1186822141 - var10.field1836 * 218008627,
                  var10.field1837 * -1209739927,
                  var10.field1842
               );
            }
         }

         for (int var11 = 0; var11 < var2.field1961 * -389646721; var11++) {
            GameObject var12 = var2.field1949[var11];
            var12.field2195 = this.field2063 * 132222543;
            if (var12.field2186 * 1138017605 == var2.field1950 * -54806681
               && var12.field2192 * -555977777 == var2.field1955 * -2067052011
               && !this.method4406(
                  var6,
                  var12.field2186 * 1138017605,
                  var12.field2184 * 304142753,
                  var12.field2192 * -555977777,
                  var12.field2190 * -544424899,
                  var12.field2198.field1741 * -1256242689
               )) {
               try {
                  var1.vmethod435(
                     var12.field2198,
                     var12.field2187 * 470594389,
                     var12.field2193 * -1405731525,
                     var12.field2188 * 1269801851,
                     var12.field2194 * -1706390585,
                     var12.field2183 * 1745853678706228013L
                  );
               } catch (Exception var15) {
               }
            }
         }

         if (var2.method4122((short)1181)) {
            for (int var16 = 0; var16 < var2.field1953.size(); var16++) {
               GameObject var17 = (GameObject)var2.field1953.get(var16);
               var17.field2195 = this.field2063 * 132222543;
               if (!this.method4406(
                  var6,
                  var2.field1950 * -54806681,
                  var2.field1950 * -54806681,
                  var2.field1955 * -2067052011,
                  var2.field1955 * -2067052011,
                  var17.field2198.field1741 * -1256242689
               )) {
                  try {
                     var1.vmethod435(
                        var17.field2198,
                        var17.field2187 * 470594389,
                        var17.field2193 * -1405731525,
                        var17.field2188 * 1269801851,
                        var17.field2194 * -1706390585,
                        var17.field2183 * 1745853678706228013L
                     );
                  } catch (Exception var14) {
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("qk")
   public int method4448(int var1) {
      return this.field2004[var1] >> 24 & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "(III)Lfi;")
   @ObfuscatedName("qe")
   public DecorativeObject method4542(int var1, int var2, int var3) {
      return this.method4498(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("sx")
   public void method4543(int var1, int var2, int var3, int var4) {
      method4502(this, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Lfb;II)I")
   @ObfuscatedName("le")
   int method4394(GameObject var1, int var2, int var3) {
      int var4 = var2 - var1.field2186 * 1138017605;
      int var5 = var1.field2184 * 304142753 - var2;
      if (var5 > var4) {
         var4 = var5;
      }

      int var6 = var3 - var1.field2192 * -555977777;
      int var7 = var1.field2190 * -544424899 - var3;
      return var7 > var6 ? var4 + var7 : var4 + var6;
   }

   @ObfuscatedSignature(descriptor = "(Lfv;)V")
   @ObfuscatedName("oe")
   public void method4453(Projection var1) {
      assert !classOE.field4841.isGpu();

      boolean var2 = this.field1986 != 0 && this.field2060;
      this.field2039 = 0;

      for (int var3 = this.field2038; var3 < this.field2057; var3++) {
         for (int var4 = this.field2118; var4 < this.field2121; var4++) {
            for (int var5 = this.field2111; var5 < this.field2120; var5++) {
               int var6 = this.method4438(var3, var4, var5);
               if (this.method4426(var6)) {
                  int var7 = this.method4472(var6);
                  int var8 = this.field1992[this.field2002.field1708 * -483624883][var4][var5];
                  if ((var7 <= this.field2025 || var2)
                     && (
                        !this.field2060
                           || this.method4540(var4 - this.field2112 + this.field2051 + this.field2049, var5 - this.field2122 + this.field2052 + this.field2049)
                           || this.field2058[var3][var4][var5] - this.field2117 >= 2000
                     )
                     && (!var2 || this.field2002.field1708 * -483624883 >= var7 || var8 == 0 || !this.field2020.contains(var8))) {
                     this.method4510(var1, var6);
                  }
               }
            }
         }
      }

      this.method4548();
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("df")
   public void method4544(int var1, int var2, int var3) {
      this.method4466(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIILee;IZJI)Z")
   @ObfuscatedName("sq")
   public boolean method4545(
      int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Renderable var9, int var10, boolean var11, long var12, int var14
   ) {
      return this.method4424(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var14);
   }

   @ObfuscatedSignature(descriptor = "(Ler;IIIII)V")
   @ObfuscatedName("cv")
   void method4268(ModelData var1, int var2, int var3, int var4, int var5, int var6) {
      boolean var7 = true;
      int var8 = var3;
      int var9 = var3 + var5;
      int var10 = var4 - 1;
      int var11 = var4 + var6;

      for (int var12 = var2; var12 <= var2 + 1; var12++) {
         if (var12 != this.field2057) {
            for (int var13 = var8; var13 <= var9; var13++) {
               if (var13 >= 0 && var13 < this.field2054) {
                  for (int var14 = var10; var14 <= var11; var14++) {
                     if (var14 >= 0 && var14 < this.field2030 && (!var7 || var13 >= var9 || var14 >= var11 || var14 < var4 && var13 != var3)) {
                        Tile var15 = this.field2056[var12][var13][var14];
                        if (var15 != null) {
                           int var16 = this.method4269(var12, var13, var14, var2, var3, var4);
                           WallObject var17 = var15.field1968;
                           if (var17 != null) {
                              if (var17.field2247 instanceof ModelData) {
                                 ModelData var18 = (ModelData)var17.field2247;
                                 ModelData.method3987(var1, var18, (var13 - var3) * 128 + (1 - var5) * 64, var16, (var14 - var4) * 128 + (1 - var6) * 64, var7);
                              }

                              if (var17.field2246 instanceof ModelData) {
                                 ModelData var23 = (ModelData)var17.field2246;
                                 ModelData.method3987(var1, var23, (var13 - var3) * 128 + (1 - var5) * 64, var16, (var14 - var4) * 128 + (1 - var6) * 64, var7);
                              }
                           }

                           for (int var24 = 0; var24 < var15.field1961 * -389646721; var24++) {
                              GameObject var19 = var15.field1949[var24];
                              if (var19 != null && var19.field2198 instanceof ModelData) {
                                 ModelData var20 = (ModelData)var19.field2198;
                                 int var21 = var19.field2184 * 304142753 - var19.field2186 * 1138017605 + 1;
                                 int var22 = var19.field2190 * -544424899 - var19.field2192 * -555977777 + 1;
                                 ModelData.method3987(
                                    var1,
                                    var20,
                                    (var19.field2186 * 1138017605 - var3) * 128 + (var21 - var5) * 64,
                                    var16,
                                    (var19.field2192 * -555977777 - var4) * 128 + (var22 - var6) * 64,
                                    var7
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            }

            var8--;
            var7 = false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bg")
   public boolean method4490(int var1) {
      return (this.field2004[var1] & 4) != 0;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("me")
   public void method4546(int var1, int var2) {
      this.method4436(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Lfb;II)I")
   @ObfuscatedName("ld")
   int method4395(GameObject var1, int var2, int var3) {
      int var4 = var2 - var1.field2186 * 1138017605;
      int var5 = var1.field2184 * 304142753 - var2;
      if (var5 > var4) {
         var4 = var5;
      }

      int var6 = var3 - var1.field2192 * -555977777;
      int var7 = var1.field2190 * -544424899 - var3;
      return var7 > var6 ? var4 + var7 : var4 + var6;
   }

   @ObfuscatedSignature(descriptor = "(Lez;IIIILee;Lee;IIIIIIJI)V")
   @ObfuscatedName("zl")
   public static void method4495(
      Scene var0,
      int var1,
      int var2,
      int var3,
      int var4,
      Renderable var5,
      Renderable var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      long var13,
      int var15
   ) {
      if (var0 == null) {
         var0.method4496(var1, var1, var1, var1, var5, var5, var1, var1, var1, var1, var1, var1, var13, var1);
      } else {
         int var16 = var2 + var0.field2146;
         int var17 = var3 + var0.field2146;
         if (var5 != null) {
            int var18 = var0.method4438(var1, var16, var17);
            DecorativeObject var19 = new DecorativeObject();
            var19.method4811(var13);
            var19.method4804(var15);
            var19.method4812(var2 * 128 + 64);
            var19.method4806(var3 * 128 + 64);
            var19.method4809(var4);
            var19.method4813(var5);
            var19.method4807(var6);
            var19.method4802(var7);
            var19.method4808(var8);
            var19.field2274 = var9 * 261748961;
            var19.field2268 = var10 * 46830669;
            var19.field2267 = var11 * -153257471;
            var19.field2270 = var12 * -829209555;

            for (int var20 = var1; var20 >= 0; var20--) {
               int var21 = var0.method4438(var20, var16, var17);
               if (!var0.method4426(var21)) {
                  var0.method4483(var21, var20, var16, var17);
               }

               if (var0.field1991[var20][var16][var17] == null) {
                  var0.field1991[var20][var16][var17] = new rl10(var0, var21);
                  var0.method4456(var20, var16, var17);
               }
            }

            assert var0.field2008[var18] == null;

            var0.field2008[var18] = var19;
            var0.field2004[var18] = var0.field2004[var18] | 32768;
            var19.field2275 = var1;
            if (var5 instanceof DynamicObject || var6 instanceof DynamicObject) {
               rl17 var22 = var0.field2017[var16 >> 3][var17 >> 3];
               var22.field5703.add(var19);
               var0.method4348((DynamicObject)var5);
               var0.method4348((DynamicObject)var6);
            }

            var0.method4513(var5, 0, var19.getX(), var19.getZ(), var19.getY(), var2, var3);
            var0.method4513(var6, 0, var19.getX(), var19.getZ(), var19.getY(), var2, var3);
            if (var0.field2152) {
               DecorativeObjectSpawned var23 = new DecorativeObjectSpawned();
               var23.setTile(var0.field1991[var1][var16][var17]);
               var23.setDecorativeObject(var19);
               classOE.field4841.getCallbacks().post(var23);
            }

            if (var0.field2152 && client.field1117 != null) {
               client.field1117.invalidateZone(var0, var16 >> 3, var17 >> 3);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lq")
   void method4398() {
      int var1 = this.field2061[this.field2025];
      classEP[] var2 = this.field2037[this.field2025];
      this.field2107 = 0;

      for (int var3 = 0; var3 < var1; var3++) {
         classEP var4 = var2[var3];
         if (var4.field1816 * -71966871 == 1) {
            int var14 = var4.field1819 * 1062499071 - this.field2112 + this.field2049;
            if (var14 >= 0 && var14 <= this.field2049 + this.field2049) {
               int var16 = var4.field1828 * -728017947 - this.field2122 + this.field2049;
               if (var16 < 0) {
                  var16 = 0;
               }

               int var18 = var4.field1831 * -899751495 - this.field2122 + this.field2049;
               if (var18 > this.field2049 + this.field2049) {
                  var18 = this.field2049 + this.field2049;
               }

               boolean var20 = false;

               while (var16 <= var18) {
                  if (this.method4340(var14, var16++)) {
                     var20 = true;
                     break;
                  }
               }

               if (var20) {
                  int var22 = this.field2116 - var4.field1815 * -996295335;
                  if (var22 > 32) {
                     var4.field1830 = -307181543;
                  } else {
                     if (var22 >= -32) {
                        continue;
                     }

                     var4.field1830 = -614363086;
                     var22 = -var22;
                  }

                  var4.field1826 = (var4.field1818 * -832449137 - this.field2114 << 8) / var22 * -1015514901;
                  var4.field1820 = (var4.field1827 * -371025921 - this.field2114 << 8) / var22 * 306530981;
                  var4.field1822 = (var4.field1829 * 318014867 - this.field2117 << 8) / var22 * -1741242211;
                  var4.field1824 = (var4.field1821 * -971837087 - this.field2117 << 8) / var22 * -2051350499;
                  this.field2046[this.field2107++] = var4;
               }
            }
         } else if (var4.field1816 * -71966871 == 2) {
            int var13 = var4.field1828 * -728017947 - this.field2122 + this.field2049;
            if (var13 >= 0 && var13 <= this.field2049 + this.field2049) {
               int var15 = var4.field1819 * 1062499071 - this.field2112 + this.field2049;
               if (var15 < 0) {
                  var15 = 0;
               }

               int var17 = var4.field1825 * -753584253 - this.field2112 + this.field2049;
               if (var17 > this.field2049 + this.field2049) {
                  var17 = this.field2049 + this.field2049;
               }

               boolean var19 = false;

               while (var15 <= var17) {
                  if (this.method4340(var15++, var13)) {
                     var19 = true;
                     break;
                  }
               }

               if (var19) {
                  int var21 = this.field2114 - var4.field1818 * -832449137;
                  if (var21 > 32) {
                     var4.field1830 = -921544629;
                  } else {
                     if (var21 >= -32) {
                        continue;
                     }

                     var4.field1830 = -1228726172;
                     var21 = -var21;
                  }

                  var4.field1823 = (var4.field1815 * -996295335 - this.field2116 << 8) / var21 * -1640466283;
                  var4.field1817 = (var4.field1832 * 1506923105 - this.field2116 << 8) / var21 * 323343539;
                  var4.field1822 = (var4.field1829 * 318014867 - this.field2117 << 8) / var21 * -1741242211;
                  var4.field1824 = (var4.field1821 * -971837087 - this.field2117 << 8) / var21 * -2051350499;
                  this.field2046[this.field2107++] = var4;
               }
            }
         } else if (var4.field1816 * -71966871 == 4) {
            int var5 = var4.field1829 * 318014867 - this.field2117;
            if (var5 > 128) {
               int var6 = var4.field1828 * -728017947 - this.field2122 + this.field2049;
               if (var6 < 0) {
                  var6 = 0;
               }

               int var7 = var4.field1831 * -899751495 - this.field2122 + this.field2049;
               if (var7 > this.field2049 + this.field2049) {
                  var7 = this.field2049 + this.field2049;
               }

               if (var6 <= var7) {
                  int var8 = var4.field1819 * 1062499071 - this.field2112 + this.field2049;
                  if (var8 < 0) {
                     var8 = 0;
                  }

                  int var9 = var4.field1825 * -753584253 - this.field2112 + this.field2049;
                  if (var9 > this.field2049 + this.field2049) {
                     var9 = this.field2049 + this.field2049;
                  }

                  boolean var10 = false;

                  label197:
                  for (int var11 = var8; var11 <= var9; var11++) {
                     for (int var12 = var6; var12 <= var7; var12++) {
                        if (this.method4340(var11, var12)) {
                           var10 = true;
                           break label197;
                        }
                     }
                  }

                  if (var10) {
                     var4.field1830 = -1535907715;
                     var4.field1823 = (var4.field1815 * -996295335 - this.field2116 << 8) / var5 * -1640466283;
                     var4.field1817 = (var4.field1832 * 1506923105 - this.field2116 << 8) / var5 * 323343539;
                     var4.field1826 = (var4.field1818 * -832449137 - this.field2114 << 8) / var5 * -1015514901;
                     var4.field1820 = (var4.field1827 * -371025921 - this.field2114 << 8) / var5 * 306530981;
                     this.field2046[this.field2107++] = var4;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lee;IIIIJ)V")
   @ObfuscatedName("im")
   public void method4460(Projection var1, Renderable var2, int var3, int var4, int var5, int var6, long var7) {
      var1.method5058(var2, var3, var4, var5, var6, var7);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bw")
   public void method4442() {
      for (int var1 = 0; var1 < this.field2017.length; var1++) {
         for (int var2 = 0; var2 < this.field2017[var1].length; var2++) {
            rl17 var3 = this.field2017[var1][var2];

            for (int var4 = 0; var4 < var3.field5704.size(); var4++) {
               GameObject var5 = (GameObject)var3.field5704.get(var4);
               this.removeGameObject(var5);
               var5.field2198 = null;
               field2074.addLast(var5);
            }

            var3.field5704.clear();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ew")
   static final int method4173(int var0, int var1) {
      var1 = var1 * (var0 & 127) >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ep")
   public void method4182(int var1) {
      this.field2038 = var1;

      for (int var2 = 0; var2 < this.field2054; var2++) {
         for (int var3 = 0; var3 < this.field2030; var3++) {
            if (this.field2056[var1][var2][var3] == null) {
               this.field2056[var1][var2][var3] = new Tile(var1, var2, var3);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("hu")
   public void method4443() {
      int var1 = this.field2001;
      var1 <<= 1;
      this.field2016[var1] = this.field2001;
      var1 = this.field2001;
      var1 = (var1 << 1) + 1;
      this.field2016[var1] = this.field2001;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/BitSet;IIIIZ)Z")
   @ObfuscatedName("ci")
   static boolean method4317(BitSet var0, int var1, int var2, int var3, int var4, boolean var5) {
      var0.set(var1 * field2130 + var2 * field2132 + var3 * field2125 + var4, var5);
      return var5;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;IIII)Z")
   @ObfuscatedName("mq")
   public static boolean method4347(Scene var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method4535(var1, var1, var1);
      }

      if (!method4325(var1, var2, var3, var4)) {
         return method4319(var1, var2, var3, var4);
      } else {
         method4330(var1, var2, var3, var4, false);
         int var5 = var1 * 256 + field2064;
         int var6 = var2 * 1024;
         int var7 = (var3 - var0.field2049 - var0.field2051 - 1) * 128;
         int var8 = (var4 - var0.field2049 - var0.field2052 - 1) * 128;

         for (int var9 = -field2128; var9 <= field2124; var9 += 128) {
            if (method4350(var7, field2070[var1] + var9, var8, var5, var6)) {
               return method4322(var1, var2, var3, var4, true);
            }
         }

         return method4322(var1, var2, var3, var4, false);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Lfv;Lew;Z)V")
   @ObfuscatedName("dj")
   public static void method4387(Scene var0, Projection var1, Tile var2, boolean var3) {
      var0.field2047.method7946(var2);

      label661:
      while (true) {
         Tile var4;
         int var5;
         int var6;
         int var7;
         int var8;
         Tile[][] var9;
         while (true) {
            var4 = (Tile)var0.field2047.method7951();
            if (var4 == null) {
               return;
            }

            if (var4.field1974) {
               var5 = var4.field1950 * -54806681;
               var6 = var4.field1955 * -2067052011;
               var7 = var4.field1947 * 1483076705;
               var8 = var4.field1948 * 800564741;
               var9 = var0.field2056[var7];
               if (!var4.field1972) {
                  break;
               }

               if (var3) {
                  if (var7 > 0) {
                     Tile var10 = var0.field2056[var7 - 1][var5][var6];
                     if (var10 != null && var10.field1974) {
                        continue;
                     }
                  }

                  if (var5 <= var0.field2112 && var5 > var0.field2118) {
                     Tile var21 = var9[var5 - 1][var6];
                     if (var21 != null && var21.field1974 && (var21.field1972 || (var4.field1952 * -785027619 & 1) == 0)) {
                        continue;
                     }
                  }

                  if (var5 >= var0.field2112 && var5 < var0.field2121 - 1) {
                     Tile var22 = var9[var5 + 1][var6];
                     if (var22 != null && var22.field1974 && (var22.field1972 || (var4.field1952 * -785027619 & 4) == 0)) {
                        continue;
                     }
                  }

                  if (var6 <= var0.field2122 && var6 > var0.field2111) {
                     Tile var23 = var9[var5][var6 - 1];
                     if (var23 != null && var23.field1974 && (var23.field1972 || (var4.field1952 * -785027619 & 8) == 0)) {
                        continue;
                     }
                  }

                  if (var6 >= var0.field2122 && var6 < var0.field2120 - 1) {
                     Tile var24 = var9[var5][var6 + 1];
                     if (var24 != null && var24.field1974 && (var24.field1972 || (var4.field1952 * -785027619 & 2) == 0)) {
                        continue;
                     }
                  }
               } else {
                  var3 = true;
               }

               var4.field1972 = false;
               if (var4.field1967 != null) {
                  Tile var25 = var4.field1967;
                  if (var25.field1970 != null) {
                     if (!var0.method4399(0, var5, var6)) {
                        var1.vmethod426(var0, var25.field1970, 0, var5, var6, 1184911032);
                     }
                  } else if (var25.field1971 != null && !var0.method4399(0, var5, var6)) {
                     var1.vmethod431(var0, var25.field1971, var5, var6, -1559083594);
                  }

                  WallObject var11 = var25.field1968;
                  if (var11 != null) {
                     var1.vmethod435(
                        var11.field2247,
                        0,
                        var11.field2242 * -777838807,
                        var11.field2240 * 1315862339,
                        var11.field2243 * 31139801,
                        var11.field2238 * 7159317397185328723L
                     );
                  }

                  for (int var12 = 0; var12 < var25.field1961 * -389646721; var12++) {
                     GameObject var13 = var25.field1949[var12];
                     if (var13 != null) {
                        var1.vmethod435(
                           var13.field2198,
                           var13.field2187 * 470594389,
                           var13.field2193 * -1405731525,
                           var13.field2188 * 1269801851,
                           var13.field2194 * -1706390585,
                           var13.field2183 * 1745853678706228013L
                        );
                     }
                  }

                  if (var25.field1953 != null && !var25.field1953.isEmpty()) {
                     for (int var46 = 0; var46 < var25.field1953.size(); var46++) {
                        GameObject var53 = (GameObject)var25.field1953.get(var46);
                        var1.vmethod435(
                           var53.field2198,
                           var53.field2187 * 470594389,
                           var53.field2193 * -1405731525,
                           var53.field2188 * 1269801851,
                           var53.field2194 * -1706390585,
                           var53.field2183 * 1745853678706228013L
                        );
                     }
                  }
               }

               boolean var26 = false;
               if (var4.field1970 != null) {
                  if (!var0.method4399(var8, var5, var6)) {
                     var26 = true;
                     if (var4.field1970.field2284 * -665850825 != 12345678 || var7 <= var0.field2042) {
                        var1.vmethod426(var0, var4.field1970, var8, var5, var6, -549838045);
                     }
                  }
               } else if (var4.field1971 != null && !var0.method4399(var8, var5, var6)) {
                  var26 = true;
                  var1.vmethod431(var0, var4.field1971, var5, var6, 801308209);
               }

               int var34 = 0;
               int var47 = 0;
               WallObject var54 = var4.field1968;
               DecorativeObject var14 = var4.field1969;
               if (var54 != null || var14 != null) {
                  if (var0.field2112 == var5) {
                     var34++;
                  } else if (var0.field2112 < var5) {
                     var34 += 2;
                  }

                  if (var0.field2122 == var6) {
                     var34 += 3;
                  } else if (var0.field2122 > var6) {
                     var34 += 6;
                  }

                  var47 = field2068[var34];
                  var4.field1964 = field2076[var34] * -444605687;
               }

               if (var54 != null) {
                  if ((var54.field2244 * 1548562339 & field2065[var34]) != 0) {
                     if (var54.field2244 * 1548562339 == 16) {
                        var4.field1960 = -1916821325;
                        var4.field1959 = field2077[var34] * -271757815;
                        var4.field1958 = -1262267289 - var4.field1959 * -640457819;
                     } else if (var54.field2244 * 1548562339 == 32) {
                        var4.field1960 = 461324646;
                        var4.field1959 = field2066[var34] * -271757815;
                        var4.field1958 = 1770432718 - var4.field1959 * -640457819;
                     } else if (var54.field2244 * 1548562339 == 64) {
                        var4.field1960 = 922649292;
                        var4.field1959 = field2079[var34] * -271757815;
                        var4.field1958 = -754101860 - var4.field1959 * -640457819;
                     } else {
                        var4.field1960 = -1455496679;
                        var4.field1959 = field2080[var34] * -271757815;
                        var4.field1958 = 508165429 - var4.field1959 * -640457819;
                     }
                  } else {
                     var4.field1960 = 0;
                  }

                  if ((var54.field2244 * 1548562339 & var47) != 0 && !var0.method4401(var8, var5, var6, var54.field2244 * 1548562339)) {
                     var1.vmethod435(
                        var54.field2247,
                        0,
                        var54.field2242 * -777838807,
                        var54.field2240 * 1315862339,
                        var54.field2243 * 31139801,
                        var54.field2238 * 7159317397185328723L
                     );
                  }

                  if ((var54.field2241 * -1413337351 & var47) != 0 && !var0.method4401(var8, var5, var6, var54.field2241 * -1413337351)) {
                     var1.vmethod435(
                        var54.field2246,
                        0,
                        var54.field2242 * -777838807,
                        var54.field2240 * 1315862339,
                        var54.field2243 * 31139801,
                        var54.field2238 * 7159317397185328723L
                     );
                  }
               }

               if (var14 != null && !var0.method4404(var8, var5, var6, var14.field2277.field1741 * -1256242689)) {
                  if ((var14.field2269 * 254285683 & var47) != 0) {
                     var1.vmethod435(
                        var14.field2277,
                        0,
                        var14.field2273 * 1766830743 + var14.field2274 * -404927711,
                        var14.field2271 * 1698079433,
                        var14.field2266 * -361702377 + var14.field2268 * 856605829,
                        var14.field2264 * -3608662145112923099L
                     );
                  } else if (var14.field2269 * 254285683 == 256) {
                     int var15 = var14.field2273 * 1766830743 - var0.field2116;
                     int var16 = var14.field2266 * -361702377 - var0.field2114;
                     int var17 = var14.field2265 * -819410985;
                     int var18;
                     if (var17 != 1 && var17 != 2) {
                        var18 = var15;
                     } else {
                        var18 = -var15;
                     }

                     int var19;
                     if (var17 != 2 && var17 != 3) {
                        var19 = var16;
                     } else {
                        var19 = -var16;
                     }

                     if (var19 < var18) {
                        var1.vmethod435(
                           var14.field2277,
                           0,
                           var14.field2273 * 1766830743 + var14.field2274 * -404927711,
                           var14.field2271 * 1698079433,
                           var14.field2266 * -361702377 + var14.field2268 * 856605829,
                           var14.field2264 * -3608662145112923099L
                        );
                     } else if (var14.field2276 != null) {
                        var1.vmethod435(
                           var14.field2276,
                           0,
                           var14.field2273 * 1766830743 + var14.field2267 * -1085897215,
                           var14.field2271 * 1698079433,
                           var14.field2266 * -361702377 + var14.field2270 * 513256357,
                           var14.field2264 * -3608662145112923099L
                        );
                     }
                  }
               }

               if (var26) {
                  GroundObject var63 = var4.field1965;
                  if (var63 != null) {
                     var1.vmethod435(
                        var63.field1814,
                        0,
                        var63.field1804 * 1333275141,
                        var63.field1805 * -276908185,
                        var63.field1806 * 498815127,
                        var63.field1813 * 5132338419383099835L
                     );
                  }

                  ItemLayer var69 = var4.field1966;
                  if (var69 != null && var69.field1836 * 218008627 == 0) {
                     if (var69.field1844 != null) {
                        var1.vmethod435(
                           var69.field1844, 0, var69.field1838 * -892202585, var69.field1839 * 1186822141, var69.field1837 * -1209739927, var69.field1842
                        );
                     }

                     if (var69.field1845 != null) {
                        var1.vmethod435(
                           var69.field1845, 0, var69.field1838 * -892202585, var69.field1839 * 1186822141, var69.field1837 * -1209739927, var69.field1842
                        );
                     }

                     if (var69.field1843 != null) {
                        var1.vmethod435(
                           var69.field1843, 0, var69.field1838 * -892202585, var69.field1839 * 1186822141, var69.field1837 * -1209739927, var69.field1842
                        );
                     }
                  }
               }

               int var64 = var4.field1952 * -785027619;
               if (var64 != 0) {
                  if (var5 < var0.field2112 && var5 >= var0.field2118 && var5 < var0.field2121 - 1 && (var64 & 4) != 0) {
                     Tile var70 = var9[var5 + 1][var6];
                     if (var70 != null && var70.field1974) {
                        var0.field2047.method7946(var70);
                     }
                  }

                  if (var6 < var0.field2122 && var6 >= var0.field2111 && var6 < var0.field2120 - 1 && (var64 & 2) != 0) {
                     Tile var71 = var9[var5][var6 + 1];
                     if (var71 != null && var71.field1974) {
                        var0.field2047.method7946(var71);
                     }
                  }

                  if (var5 > var0.field2112 && var5 > var0.field2118 && var5 < var0.field2121 && (var64 & 1) != 0) {
                     Tile var72 = var9[var5 - 1][var6];
                     if (var72 != null && var72.field1974) {
                        var0.field2047.method7946(var72);
                     }
                  }

                  if (var6 > var0.field2122 && var6 > var0.field2111 && var6 < var0.field2120 && (var64 & 8) != 0) {
                     Tile var73 = var9[var5][var6 - 1];
                     if (var73 != null && var73.field1974) {
                        var0.field2047.method7946(var73);
                     }
                  }
               }
               break;
            }
         }

         if (var4.field1960 * -438031503 != 0) {
            boolean var27 = true;

            for (int var35 = 0; var35 < var4.field1961 * -389646721; var35++) {
               if (var4.field1949[var35].field2195 * 163498159 != var0.field2063
                  && (var4.field1951[var35] & var4.field1960 * -438031503) == var4.field1959 * 392470585) {
                  var27 = false;
                  break;
               }
            }

            if (var27) {
               WallObject var36 = var4.field1968;
               if (!var0.method4401(var8, var5, var6, var36.field2244 * 1548562339)) {
                  var1.vmethod435(
                     var36.field2247,
                     0,
                     var36.field2242 * -777838807,
                     var36.field2240 * 1315862339,
                     var36.field2243 * 31139801,
                     var36.field2238 * 7159317397185328723L
                  );
               }

               var4.field1960 = 0;
            }
         }

         if (var4.field1973) {
            try {
               var4.field1973 = false;
               int var28 = 0;

               label609:
               for (int var37 = 0; var37 < var4.field1961 * -389646721; var37++) {
                  GameObject var48 = var4.field1949[var37];
                  if (var48.field2195 * 163498159 != var0.field2063) {
                     for (int var55 = var48.field2186 * 1138017605; var55 <= var48.field2184 * 304142753; var55++) {
                        for (int var59 = var48.field2192 * -555977777; var59 <= var48.field2190 * -544424899; var59++) {
                           Tile var65 = var9[var55][var59];
                           if (var65.field1972) {
                              var4.field1973 = true;
                              continue label609;
                           }

                           if (var65.field1960 * -438031503 != 0) {
                              int var74 = 0;
                              if (var55 > var48.field2186 * 1138017605) {
                                 var74++;
                              }

                              if (var55 < var48.field2184 * 304142753) {
                                 var74 += 4;
                              }

                              if (var59 > var48.field2192 * -555977777) {
                                 var74 += 8;
                              }

                              if (var59 < var48.field2190 * -544424899) {
                                 var74 += 2;
                              }

                              if ((var74 & var65.field1960 * -438031503) == var4.field1958 * 948803077) {
                                 var4.field1973 = true;
                                 continue label609;
                              }
                           }
                        }
                     }

                     var0.field2023[var28++] = var48;
                     var48.field2189 = var0.method4393(var48, var0.field2112, var0.field2122) * -1227421221;
                  }
               }

               if (var4.method4122((short)2142)) {
                  for (int var38 = 0; var38 < var4.field1953.size(); var38++) {
                     GameObject var49 = (GameObject)var4.field1953.get(var38);
                     if (var49.field2195 * 163498159 != var0.field2063) {
                        var0.field2023[var28++] = var49;
                        var49.field2189 = var0.method4393(var49, var0.field2112, var0.field2122) * -1227421221;
                     }
                  }
               }

               while (true) {
                  int var39 = -50;
                  int var50 = -1;

                  for (int var56 = 0; var56 < var28; var56++) {
                     GameObject var60 = var0.field2023[var56];
                     if (var60.field2195 * 163498159 != var0.field2063) {
                        if (var60.field2189 * 835963475 > var39) {
                           var39 = var60.field2189 * 835963475;
                           var50 = var56;
                        } else if (var60.field2189 * 835963475 == var39) {
                           int var66 = var60.field2193 * -1405731525 - var0.field2116;
                           int var75 = var60.field2194 * -1706390585 - var0.field2114;
                           int var78 = var0.field2023[var50].field2193 * -1405731525 - var0.field2116;
                           int var79 = var0.field2023[var50].field2194 * -1706390585 - var0.field2114;
                           if (var66 * var66 + var75 * var75 > var78 * var78 + var79 * var79) {
                              var50 = var56;
                           }
                        }
                     }
                  }

                  if (var50 == -1) {
                     if (var4.field1973) {
                        continue label661;
                     }
                     break;
                  }

                  GameObject var57 = var0.field2023[var50];
                  var57.field2195 = var0.field2063 * 132222543;
                  if (!var0.method4406(
                     var8,
                     var57.field2186 * 1138017605,
                     var57.field2184 * 304142753,
                     var57.field2192 * -555977777,
                     var57.field2190 * -544424899,
                     var57.field2198.field1741 * -1256242689
                  )) {
                     var1.vmethod435(
                        var57.field2198,
                        var57.field2187 * 470594389,
                        var57.field2193 * -1405731525,
                        var57.field2188 * 1269801851,
                        var57.field2194 * -1706390585,
                        var57.field2183 * 1745853678706228013L
                     );
                  }

                  for (int var61 = var57.field2186 * 1138017605; var61 <= var57.field2184 * 304142753; var61++) {
                     for (int var67 = var57.field2192 * -555977777; var67 <= var57.field2190 * -544424899; var67++) {
                        Tile var76 = var9[var61][var67];
                        if (var76.field1960 * -438031503 != 0) {
                           var0.field2047.method7946(var76);
                        } else if ((var61 != var5 || var67 != var6) && var76.field1974) {
                           var0.field2047.method7946(var76);
                        }
                     }
                  }
               }
            } catch (Exception var20) {
               var4.field1973 = false;
            }
         }

         if (var4.field1974 && var4.field1960 * -438031503 == 0) {
            if (var5 <= var0.field2112 && var5 > var0.field2118) {
               Tile var29 = var9[var5 - 1][var6];
               if (var29 != null && var29.field1974) {
                  continue;
               }
            }

            if (var5 >= var0.field2112 && var5 < var0.field2121 - 1) {
               Tile var30 = var9[var5 + 1][var6];
               if (var30 != null && var30.field1974) {
                  continue;
               }
            }

            if (var6 <= var0.field2122 && var6 > var0.field2111) {
               Tile var31 = var9[var5][var6 - 1];
               if (var31 != null && var31.field1974) {
                  continue;
               }
            }

            if (var6 >= var0.field2122 && var6 < var0.field2120 - 1) {
               Tile var32 = var9[var5][var6 + 1];
               if (var32 != null && var32.field1974) {
                  continue;
               }
            }

            var4.field1974 = false;
            var0.field2039--;
            ItemLayer var33 = var4.field1966;
            if (var33 != null && var33.field1836 * 218008627 != 0) {
               if (var33.field1844 != null) {
                  var1.vmethod435(
                     var33.field1844,
                     0,
                     var33.field1838 * -892202585,
                     var33.field1839 * 1186822141 - var33.field1836 * 218008627,
                     var33.field1837 * -1209739927,
                     var33.field1842
                  );
               }

               if (var33.field1845 != null) {
                  var1.vmethod435(
                     var33.field1845,
                     0,
                     var33.field1838 * -892202585,
                     var33.field1839 * 1186822141 - var33.field1836 * 218008627,
                     var33.field1837 * -1209739927,
                     var33.field1842
                  );
               }

               if (var33.field1843 != null) {
                  var1.vmethod435(
                     var33.field1843,
                     0,
                     var33.field1838 * -892202585,
                     var33.field1839 * 1186822141 - var33.field1836 * 218008627,
                     var33.field1837 * -1209739927,
                     var33.field1842
                  );
               }
            }

            if (var4.field1964 * 1986405177 != 0) {
               DecorativeObject var40 = var4.field1969;
               if (var40 != null && !var0.method4404(var8, var5, var6, var40.field2277.field1741 * -1256242689)) {
                  if ((var40.field2269 * 254285683 & var4.field1964 * 1986405177) != 0) {
                     var1.vmethod435(
                        var40.field2277,
                        0,
                        var40.field2273 * 1766830743 + var40.field2274 * -404927711,
                        var40.field2271 * 1698079433,
                        var40.field2266 * -361702377 + var40.field2268 * 856605829,
                        var40.field2264 * -3608662145112923099L
                     );
                  } else if (var40.field2269 * 254285683 == 256) {
                     int var51 = var40.field2273 * 1766830743 - var0.field2116;
                     int var58 = var40.field2266 * -361702377 - var0.field2114;
                     int var62 = var40.field2265 * -819410985;
                     int var68;
                     if (var62 != 1 && var62 != 2) {
                        var68 = var51;
                     } else {
                        var68 = -var51;
                     }

                     int var77;
                     if (var62 != 2 && var62 != 3) {
                        var77 = var58;
                     } else {
                        var77 = -var58;
                     }

                     if (var77 >= var68) {
                        var1.vmethod435(
                           var40.field2277,
                           0,
                           var40.field2273 * 1766830743 + var40.field2274 * -404927711,
                           var40.field2271 * 1698079433,
                           var40.field2266 * -361702377 + var40.field2268 * 856605829,
                           var40.field2264 * -3608662145112923099L
                        );
                     } else if (var40.field2276 != null) {
                        var1.vmethod435(
                           var40.field2276,
                           0,
                           var40.field2273 * 1766830743 + var40.field2267 * -1085897215,
                           var40.field2271 * 1698079433,
                           var40.field2266 * -361702377 + var40.field2270 * 513256357,
                           var40.field2264 * -3608662145112923099L
                        );
                     }
                  }
               }

               WallObject var52 = var4.field1968;
               if (var52 != null) {
                  if ((var52.field2241 * -1413337351 & var4.field1964 * 1986405177) != 0 && !var0.method4401(var8, var5, var6, var52.field2241 * -1413337351)) {
                     var1.vmethod435(
                        var52.field2246,
                        0,
                        var52.field2242 * -777838807,
                        var52.field2240 * 1315862339,
                        var52.field2243 * 31139801,
                        var52.field2238 * 7159317397185328723L
                     );
                  }

                  if ((var52.field2244 * 1548562339 & var4.field1964 * 1986405177) != 0 && !var0.method4401(var8, var5, var6, var52.field2244 * 1548562339)) {
                     var1.vmethod435(
                        var52.field2247,
                        0,
                        var52.field2242 * -777838807,
                        var52.field2240 * 1315862339,
                        var52.field2243 * 31139801,
                        var52.field2238 * 7159317397185328723L
                     );
                  }
               }
            }

            if (var7 < var0.field2057 - 1) {
               Tile var41 = var0.field2056[var7 + 1][var5][var6];
               if (var41 != null && var41.field1974) {
                  var0.field2047.method7946(var41);
               }
            }

            if (var5 < var0.field2112 && var5 >= var0.field2118 && var5 < var0.field2121 - 1) {
               Tile var42 = var9[var5 + 1][var6];
               if (var42 != null && var42.field1974) {
                  var0.field2047.method7946(var42);
               }
            }

            if (var6 < var0.field2122 && var6 >= var0.field2111 && var6 < var0.field2120 - 1) {
               Tile var43 = var9[var5][var6 + 1];
               if (var43 != null && var43.field1974) {
                  var0.field2047.method7946(var43);
               }
            }

            if (var5 > var0.field2112 && var5 > var0.field2118 && var5 < var0.field2121) {
               Tile var44 = var9[var5 - 1][var6];
               if (var44 != null && var44.field1974) {
                  var0.field2047.method7946(var44);
               }
            }

            if (var6 > var0.field2122 && var6 > var0.field2111 && var6 < var0.field2120) {
               Tile var45 = var9[var5][var6 - 1];
               if (var45 != null && var45.field1974) {
                  var0.field2047.method7946(var45);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)Z")
   @ObfuscatedName("mp")
   boolean method4408(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var2 == var3 && var4 == var5) {
         if (!this.method4399(var1, var2, var4)) {
            return false;
         } else {
            int var13 = var2 << 7;
            int var15 = var4 << 7;
            return this.method4411(var13 + 1, this.field2058[var1][var2][var4] - var6, var15 + 1)
               && this.method4411(var13 + 128 - 1, this.field2058[var1][var2 + 1][var4] - var6, var15 + 1)
               && this.method4411(var13 + 128 - 1, this.field2058[var1][var2 + 1][var4 + 1] - var6, var15 + 128 - 1)
               && this.method4411(var13 + 1, this.field2058[var1][var2][var4 + 1] - var6, var15 + 128 - 1);
         }
      } else {
         for (int var7 = var2; var7 <= var3; var7++) {
            for (int var8 = var4; var8 <= var5; var8++) {
               if (this.field2029[var1][var7][var8] == -this.field2063) {
                  return false;
               }
            }
         }

         int var12 = (var2 << 7) + 1;
         int var14 = (var4 << 7) + 2;
         int var9 = this.field2058[var1][var2][var4] - var6;
         if (!this.method4411(var12, var9, var14)) {
            return false;
         } else {
            int var10 = (var3 << 7) - 1;
            if (!this.method4411(var10, var9, var14)) {
               return false;
            } else {
               int var11 = (var5 << 7) - 1;
               return !this.method4411(var12, var9, var11) ? false : this.method4411(var10, var9, var11);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)Z")
   @ObfuscatedName("mg")
   boolean method4409(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var2 == var3 && var4 == var5) {
         if (!this.method4399(var1, var2, var4)) {
            return false;
         } else {
            int var13 = var2 << 7;
            int var15 = var4 << 7;
            return this.method4411(var13 + 1, this.field2058[var1][var2][var4] - var6, var15 + 1)
               && this.method4411(var13 + -796360025 - 1, this.field2058[var1][var2 + 1][var4] - var6, var15 + 1)
               && this.method4411(var13 + -1962340737 - 1, this.field2058[var1][var2 + 1][var4 + 1] - var6, var15 + 128 - 1)
               && this.method4411(var13 + 1, this.field2058[var1][var2][var4 + 1] - var6, var15 + -1196696347 - 1);
         }
      } else {
         for (int var7 = var2; var7 <= var3; var7++) {
            for (int var8 = var4; var8 <= var5; var8++) {
               if (this.field2029[var1][var7][var8] == -this.field2063) {
                  return false;
               }
            }
         }

         int var12 = (var2 << 7) + 1;
         int var14 = (var4 << 7) + 2;
         int var9 = this.field2058[var1][var2][var4] - var6;
         if (!this.method4411(var12, var9, var14)) {
            return false;
         } else {
            int var10 = (var3 << 7) - 1;
            if (!this.method4411(var10, var9, var14)) {
               return false;
            } else {
               int var11 = (var5 << 7) - 1;
               return !this.method4411(var12, var9, var11) ? false : this.method4411(var10, var9, var11);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)Z")
   @ObfuscatedName("ma")
   boolean method4410(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var2 == var3 && var4 == var5) {
         if (!this.method4399(var1, var2, var4)) {
            return false;
         } else {
            int var13 = var2 << 7;
            int var15 = var4 << 7;
            return this.method4411(var13 + 1, this.field2058[var1][var2][var4] - var6, var15 + 1)
               && this.method4411(var13 + 128 - 1, this.field2058[var1][var2 + 1][var4] - var6, var15 + 1)
               && this.method4411(var13 + 128 - 1, this.field2058[var1][var2 + 1][var4 + 1] - var6, var15 + 128 - 1)
               && this.method4411(var13 + 1, this.field2058[var1][var2][var4 + 1] - var6, var15 + 128 - 1);
         }
      } else {
         for (int var7 = var2; var7 <= var3; var7++) {
            for (int var8 = var4; var8 <= var5; var8++) {
               if (this.field2029[var1][var7][var8] == -this.field2063) {
                  return false;
               }
            }
         }

         int var12 = (var2 << 7) + 1;
         int var14 = (var4 << 7) + 2;
         int var9 = this.field2058[var1][var2][var4] - var6;
         if (!this.method4411(var12, var9, var14)) {
            return false;
         } else {
            int var10 = (var3 << 7) - 1;
            if (!this.method4411(var10, var9, var14)) {
               return false;
            } else {
               int var11 = (var5 << 7) - 1;
               return !this.method4411(var12, var9, var11) ? false : this.method4411(var10, var9, var11);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lrl17;)Z")
   @ObfuscatedName("xe")
   public boolean method4433(Projection var1, rl17 var2) {
      int var3 = var2.field5710;
      int var4 = var2.field5705;
      int var5 = ((var3 << 3) - this.field2146 << 7) + (var2.field5707 + var2.field5701) / 2;
      int var6 = (var2.field5708 + var2.field5706) / 2;
      int var7 = ((var4 << 3) - this.field2146 << 7) + (var2.field5709 + var2.field5702) / 2;
      int var8 = (var2.field5701 - var2.field5707 + 1) / 2;
      int var9 = (var2.field5708 - var2.field5706 + 1) / 2;
      int var10 = (var2.field5702 - var2.field5709 + 1) / 2;
      return client.method2264(var1, 0, 0, 0, var5, var6, var7, var8, var9, var10);
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("qm")
   public static int method4477(int var0, int var1) {
      var1 = (var0 & 127) * var1 >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }

   @ObfuscatedSignature(descriptor = "(Lez;III)V")
   @ObfuscatedName("mk")
   public static void method4226(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method4227(var1, var1, var1);
      }

      var2 += var0.field2146;
      var3 += var0.field2146;
      int var4 = var0.method4438(var1, var2, var3);
      if (var0.method4426(var4) && (var0.field2004[var4] & 2048) != 0) {
         GroundObject var5 = var0.field2009[var4];
         var0.field2009[var4] = null;
         var0.field2004[var4] = var0.field2004[var4] & -2049;
         if (var5.field1814 instanceof DynamicObject) {
            rl17 var6 = var0.field2017[var2 >> 3][var3 >> 3];
            var6.field5703.remove(var5);
         }

         if (var0.field2152) {
            GroundObjectDespawned var9 = new GroundObjectDespawned();
            var9.setTile(var0.field1991[var1][var2][var3]);
            var9.setGroundObject(var5);
            classOE.field4841.getCallbacks().post(var9);
         }

         if (var0.field2152 && client.field1117 != null) {
            client.field1117.invalidateZone(var0, var2 >> 3, var3 >> 3);
         }
      }
   }

   public int getBaseX() {
      return this.field2144;
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("mk")
   @Override
   void vmethod416(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      classUU var15 = classRM.method10172(1778071523);
      classUG var16 = classMQ.method7682(-1059764796);
      classUL var17 = var16.field6303;
      int var19 = var1 & 2047;
      float var18 = (float)(var19 / 2048.0F * (Math.PI * 2));
      var17.method11437(var18, 0.0F, 0.0F, -86203980);
      classUZ.method11802(var16.field6305, var4, var5, var6, 1815114720);
      classUG var20 = classMQ.method7682(28934177);
      classUZ.method11802(var20.field6305, -(this.field2054 * 64) - this.field2027, this.field2028, -(this.field2030 * 64) - this.field2045, 1932719810);
      classUU var21 = classRM.method10172(1778071523);
      var21.method11706(var20, 1541601286);
      var15.method11675(1.0F, this.field2053, 1.0F, 1.0F, -1412584499);
      var21.method11656(var15, -1962310328);
      var21.method11656(this.field2026, 757304521);
      var15.method11706(var16, 1796198258);
      var21.method11656(var15, 973198009);
      classUZ.method11857(var15, var21, (byte)11);
      classUU.method11670(var15, -1577693990);
      this.field2116 = (int)classUU.method11685(var15, var7, var8, var9, (byte)22);
      this.field2117 = (int)var15.method11687(var7, var8, var9, 308001213);
      this.field2114 = (int)var15.method11689(var7, var8, var9, 563985024);
      this.field2112 = this.field2116 / 128;
      this.field2122 = this.field2114 / 128;
      this.field2119 = (int)classUU.method11685(var15, var12, 0.0F, var13, (byte)-29) / 128;
      this.field2123 = (int)var15.method11689(var12, 0.0F, var13, 1227481582) / 128;
      classUX var22 = new classUX();
      classUU var23 = classRM.method10172(1778071523);
      var22.method11748(-var2.method11503(-873887704), 173632492);
      var22.method11753(-var3.method11503(-1933881439), 982132619);
      classUX.method11760(var22, var7, var8, var9, (byte)86);
      classUU.method11668(var23, var22, -1959054899);
      classUU.method11670(var23, -1186101863);
      var16.method11364(-923964429);
      var20.method11364(-16933514);
      var15.method11615(2013999465);
      var21.method11656(var23, 362738109);
      FloatProjection var24 = new FloatProjection(var21);
      FloatProjection.method3908(var24, var10);
      classTW.method11268((byte)-3);
      this.method4454(var24, var14, classFH.field2255.field2215 != 1);
      classTW.method11268((byte)29);
      var23.method11615(2061656568);
      var21.method11615(1659151387);
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("me")
   @Override
   void vmethod417(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      classUU var15 = classRM.method10172(1778071523);
      classUG var16 = classMQ.method7682(-1445277858);
      classUL var17 = var16.field6303;
      int var19 = var1 & 2047;
      float var18 = (float)(var19 / 2048.0F * (Math.PI * 2));
      var17.method11437(var18, 0.0F, 0.0F, -392474146);
      classUZ.method11802(var16.field6305, var4, var5, var6, 2105574261);
      classUG var20 = classMQ.method7682(800674293);
      classUZ.method11802(
         var20.field6305, -(this.field2054 * 64) - this.field2027, this.field2028, -(this.field2030 * -1097442121) - this.field2045, 1773831181
      );
      classUU var21 = classRM.method10172(1778071523);
      var21.method11706(var20, 2118775072);
      var15.method11675(1.0F, this.field2053, 1.0F, 1.0F, -1412584499);
      var21.method11656(var15, -749679020);
      var21.method11656(this.field2026, -1872588850);
      var15.method11706(var16, 1415894900);
      var21.method11656(var15, -166423358);
      classUZ.method11857(var15, var21, (byte)76);
      classUU.method11670(var15, -554959006);
      this.field2116 = (int)classUU.method11685(var15, var7, var8, var9, (byte)-24);
      this.field2117 = (int)var15.method11687(var7, var8, var9, 716587726);
      this.field2114 = (int)var15.method11689(var7, var8, var9, -588608582);
      this.field2112 = this.field2116 / 128;
      this.field2122 = this.field2114 / 362410988;
      this.field2119 = (int)classUU.method11685(var15, var12, 0.0F, var13, (byte)36) / 1245132430;
      this.field2123 = (int)var15.method11689(var12, 0.0F, var13, -255615920) / 1806827785;
      classUX var22 = new classUX();
      classUU var23 = classRM.method10172(1778071523);
      var22.method11748(-var2.method11503(-634700962), 173632492);
      var22.method11753(-var3.method11503(-1800521643), 982132619);
      classUX.method11760(var22, var7, var8, var9, (byte)49);
      classUU.method11668(var23, var22, -1959054899);
      classUU.method11670(var23, -2059406849);
      var16.method11364(-1959808293);
      var20.method11364(-1519274453);
      var15.method11615(1641984670);
      var21.method11656(var23, -488397737);
      FloatProjection var24 = new FloatProjection(var21);
      FloatProjection.method3908(var24, var10);
      classTW.method11268((byte)-52);
      this.method4454(var24, var14, classFH.field2255.field2215 != 1);
      classTW.method11268((byte)17);
      var23.method11615(1647577243);
      var21.method11615(1544122231);
   }

   @ObfuscatedSignature(descriptor = "(ILup;Lup;IIIIIIJIIZ)V")
   @ObfuscatedName("mt")
   @Override
   void vmethod418(
      int var1, classUP var2, classUP var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, boolean var14
   ) {
      classUU var15 = classRM.method10172(1778071523);
      classUG var16 = classMQ.method7682(235537212);
      classUL var17 = var16.field6303;
      int var19 = var1 & 2047;
      float var18 = (float)(var19 / 2048.0F * (Math.PI * 2));
      var17.method11437(var18, 0.0F, 0.0F, -589567001);
      classUZ.method11802(var16.field6305, var4, var5, var6, 2118599735);
      classUG var20 = classMQ.method7682(-872998953);
      classUZ.method11802(var20.field6305, -(this.field2054 * 64) - this.field2027, this.field2028, -(this.field2030 * 64) - this.field2045, 2146272440);
      classUU var21 = classRM.method10172(1778071523);
      var21.method11706(var20, 1886158483);
      var15.method11675(1.0F, this.field2053, 1.0F, 1.0F, -1412584499);
      var21.method11656(var15, -759667496);
      var21.method11656(this.field2026, 937764227);
      var15.method11706(var16, 1194119767);
      var21.method11656(var15, 99988922);
      classUZ.method11857(var15, var21, (byte)34);
      classUU.method11670(var15, 216614897);
      this.field2116 = (int)classUU.method11685(var15, var7, var8, var9, (byte)11);
      this.field2117 = (int)var15.method11687(var7, var8, var9, 1284723913);
      this.field2114 = (int)var15.method11689(var7, var8, var9, 861234028);
      this.field2112 = this.field2116 / -266922065;
      this.field2122 = this.field2114 / 1882402883;
      this.field2119 = (int)classUU.method11685(var15, var12, 0.0F, var13, (byte)56) / 128;
      this.field2123 = (int)var15.method11689(var12, 0.0F, var13, 754453209) / 128;
      classUX var22 = new classUX();
      classUU var23 = classRM.method10172(1778071523);
      var22.method11748(-var2.method11503(-2012613769), 173632492);
      var22.method11753(-var3.method11503(-2087745254), 982132619);
      classUX.method11760(var22, var7, var8, var9, (byte)98);
      classUU.method11668(var23, var22, -1959054899);
      classUU.method11670(var23, -1446443821);
      var16.method11364(60381397);
      var20.method11364(-1267163293);
      var15.method11615(1697997152);
      var21.method11656(var23, 331410632);
      FloatProjection var24 = new FloatProjection(var21);
      FloatProjection.method3908(var24, var10);
      classTW.method11268((byte)36);
      this.method4454(var24, var14, classFH.field2255.field2215 != 1);
      classTW.method11268((byte)-77);
      var23.method11615(1633898212);
      var21.method11615(1912810330);
   }

   @ObfuscatedSignature(descriptor = "(Lez;III)Lfb;")
   @ObfuscatedName("jw")
   public static GameObject method4487(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method4488(var1, var1, var1);
      }

      int var4 = var2 + var0.field2146;
      int var5 = var3 + var0.field2146;
      int var6 = var0.method4438(var1, var4, var5);
      if (!var0.method4426(var6)) {
         return null;
      } else {
         byte var7 = var0.field2011[var6];

         for (int var8 = 0; var8 < var7; var8++) {
            GameObject var9 = var0.field2012[var6 * 5 + var8];
            long var10 = var9.field2183 * 1745853678706228013L;
            int var12 = (int)(var10 >> 16 & 7L);
            if (var12 == 2 && var2 == var9.field2186 * 1138017605 && var3 == var9.field2192 * -555977777) {
               return var9;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;Lrl17;)V")
   @ObfuscatedName("mq")
   public void method4446(Projection var1, rl17 var2) {
      for (int var3 = 0; var3 < var2.field5704.size(); var3++) {
         GameObject var4 = (GameObject)var2.field5704.get(var3);
         if (var4.field2198 instanceof Scene) {
            var1.method5058(
               var4.field2198,
               var4.field2187 * 470594389,
               var4.field2193 * -1405731525,
               var4.field2188 * 1269801851,
               var4.field2194 * -1706390585,
               var4.field2183 * 1745853678706228013L
            );
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;III)V")
   @ObfuscatedName("zg")
   public static void method4261(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method4464(var1, var1, var1);
      }

      for (int var4 = 0; var4 < var0.field2057; var4++) {
         for (int var5 = 0; var5 < var0.field2054; var5++) {
            for (int var6 = 0; var6 < var0.field2030; var6++) {
               Tile var7 = var0.field2056[var4][var5][var6];
               if (var7 != null) {
                  WallObject var8 = var7.field1968;
                  if (var8 != null && var8.field2247 instanceof ModelData) {
                     ModelData var9 = (ModelData)var8.field2247;
                     var0.method4509(var9, var4, var5, var6, 1, 1);
                     if (var8.field2246 instanceof ModelData) {
                        ModelData var10 = (ModelData)var8.field2246;
                        var0.method4509(var10, var4, var5, var6, 1, 1);
                        ModelData.method3987(var9, var10, 0, 0, 0, false);
                        var8.field2246 = var10.method4070(var10.field1876, var10.field1877, var1, var2, var3);
                     }

                     var8.field2247 = var9.method4070(var9.field1876, var9.field1877, var1, var2, var3);
                  }

                  for (int var12 = 0; var12 < var7.field1961 * -389646721; var12++) {
                     GameObject var14 = var7.field1949[var12];
                     if (var14 != null && var14.field2198 instanceof ModelData) {
                        ModelData var11 = (ModelData)var14.field2198;
                        var0.method4509(
                           var11,
                           var4,
                           var5,
                           var6,
                           var14.field2184 * -484358301 - var14.field2186 * 1435073618 + 1,
                           var14.field2190 * 1029339049 - var14.field2192 * -555977777 + 1
                        );
                        var14.field2198 = var11.method4070(var11.field1876, var11.field1877, var1, var2, var3);
                     }
                  }

                  GroundObject var13 = var7.field1965;
                  if (var13 != null && var13.field1814 instanceof ModelData) {
                     ModelData var15 = (ModelData)var13.field1814;
                     method4430(var0, var15, var4, var5, var6);
                     var13.field1814 = var15.method4070(var15.field1876, var15.field1877, var1, var2, var3);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfv;ZZ)V")
   @ObfuscatedName("dz")
   void method4454(Projection var1, boolean var2, boolean var3) {
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;JLee;Lee;I)V")
   @ObfuscatedName("bp")
   public void method4445(int var1, int var2, int var3, int var4, Renderable var5, long var6, Renderable var8, Renderable var9, int var10) {
      int var11 = var2 + this.field2025;
      int var12 = var3 + this.field2028;
      ItemLayer var13 = new ItemLayer();
      var13.field1843 = var5;
      var13.field1836 = (var2 * 128 + 64) * -1385855465;
      var13.field1837 = (var3 * 128 + 64) * 2114521817;
      var13.field1839 = var4 * -673866411;
      var13.field1842 = var6;
      var13.field1845 = var8;
      var13.field1845 = var9;
      var13.field1837 = var10 * 1484214011;
      var13.method3977(var1);
      int var14 = this.method4438(var1, var11, var12);
      if (!this.method4426(var14)) {
         this.method4467(var14, var1, var11, var12);
      }

      if (this.field2158[var1][var11][var12] == null) {
         this.field1991[var1][var11][var12] = new rl10(this, var14);
         this.method4241(var1, var11, var12);
      }

      rl17 var15 = this.field2017[var11 >> 3][var12 >> 3];
      if ((this.field2016[var14] & 12288) != 0) {
         var15.field5704.remove(this.field2010[var14]);
      }

      this.field2010[var14] = var13;
      this.field2016[var14] = this.field2016[var14] & -12289;
      this.field2004[var14] = this.field2004[var14] | (var10 != 0 ? 8192 : 4096);
      var15.field5703.add(var13);
   }

   public int getDrawDistance() {
      return field2141;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("az")
   public int method4154() {
      return this.field2107;
   }

   @ObfuscatedSignature(descriptor = "(III)Lfb;")
   @ObfuscatedName("bh")
   public GameObject method4488(int var1, int var2, int var3) {
      int var4 = var2 + this.field1988;
      int var5 = var3 + this.field2110;
      int var6 = this.method4197(var1, var4, var5);
      if (!this.method4490(var6)) {
         return null;
      } else {
         byte var7 = this.field2014[var6];

         for (int var8 = 0; var8 < var7; var8++) {
            GameObject var9 = this.field2012[var6 * 5 + var8];
            long var10 = var9.field2183 * 1745853678706228013L;
            int var12 = (int)(var10 >> 16 & 7L);
            if (var12 == 2 && var2 == var9.field2187 * 1138017605 && var3 == var9.field2192 * -555977777) {
               return var9;
            }
         }

         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;Ler;III)V")
   @ObfuscatedName("dr")
   public static void method4265(Scene var0, ModelData var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method4266(var1, var2, var2, var2);
      } else {
         int var6 = var3 + 1;
         int var7 = var4 - 1;
         int var8 = var4 + 1;

         for (int var9 = var3; var9 <= var6; var9++) {
            if (var9 >= 0 && var9 < var0.field2054) {
               for (int var10 = var7; var10 <= var8; var10++) {
                  if (var10 >= 0 && var10 < var0.field2030 && (var9 >= var6 || var10 >= var8)) {
                     Tile var11 = var0.field2056[var2][var9][var10];
                     if (var11 != null && var11.field1965 != null && var11.field1965.field1814 instanceof ModelData) {
                        int var12 = var0.method4269(var2, var9, var10, var2, var3, var4);
                        ModelData var13 = (ModelData)var11.field1965.field1814;
                        ModelData.method3987(var1, var13, (var9 - var3) * 128, var12, (var10 - var4) * 128, true);
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("wt")
   public void method4547(int var1, int var2, int var3, int var4) {
      this.method4467(var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIILee;IJI)Z")
   @ObfuscatedName("bt")
   public boolean method4209(int var1, int var2, int var3, int var4, int var5, int var6, Renderable var7, int var8, long var9, int var11) {
      if (var7 == null) {
         return true;
      } else {
         int var12 = var2 * 128 + 64 * var5;
         int var13 = var3 * 128 + 64 * var6;
         return this.method4424(var1, var2, var3, var5, var6, var12, var13, var4, var7, var8, false, var9, var11);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIILee;IJIIII)Z")
   @ObfuscatedName("bk")
   public boolean method4215(int var1, int var2, int var3, int var4, int var5, Renderable var6, int var7, long var8, int var10, int var11, int var12, int var13) {
      return var6 == null ? true : this.method4424(var1, var10, var11, var12 - var10 + 1, var13 - var11 + 1, var2, var3, var4, var6, var7, true, var8, 0);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("tu")
   public void method4548() {
      this.method4368();
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("be")
   public void method4503(int var1, int var2, int var3, int var4) {
      var2 += this.field2027;
      var3 += this.field2045;
      int var5 = this.method4198(var1, var2, var3);
      if (this.method4426(var5)) {
         DecorativeObject var6 = this.field2008[var5];
         if (var6 != null) {
            if (var6.field2266 * -404927711 != 0) {
               var6.field2271 = (var6.field2273 * -404927711 < 0 ? -var4 : var4) * 261748961;
            }

            if (var6.field2271 * 856605829 != 0) {
               var6.field2263 = (var6.field2265 * 856605829 < 0 ? -var4 : var4) * 46830669;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("mz")
   boolean method4400(int var1, int var2, int var3) {
      int var4 = this.field2058[var1][var2][var3];
      if (var4 == -this.field2107) {
         return false;
      } else if (var4 == this.field2113) {
         return true;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         if (this.method4411(var5 + 1, this.field2058[var1][var2][var3], var6 + 1)
            && this.method4411(var5 + 128 - 1, this.field2058[var1][var2 + 1][var3], var6 + 1)
            && this.method4399(var5 + 128 - 1, this.field2029[var1][var2 + 1][var3 + 1], var6 + 128 - 1)
            && this.method4411(var5 + 1, this.field2029[var1][var2][var3 + 1], var6 + 128 - 1)) {
            this.field2029[var1][var2][var3] = this.field2111;
            return true;
         } else {
            this.field2029[var1][var2][var3] = -this.field2112;
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)J")
   @ObfuscatedName("cr")
   public long method4437(int var1, int var2, int var3) {
      int var4 = var2 + this.field2142;
      int var5 = var3 + this.field2109;
      int var6 = this.method4438(var1, var4, var5);
      if (!this.method4490(var6)) {
         return 0L;
      } else {
         byte var7 = this.field2013[var6];

         for (int var8 = 0; var8 < var7; var8++) {
            GameObject var9 = this.field2012[var6 * 5 + var8];
            long var10 = var9.field2183 * 1745853678706228013L;
            int var12 = (int)(var10 >> 16 & 7L);
            if (var12 == 2 && var2 == var9.field2193 * 1138017605 && var3 == var9.field2192 * -555977777) {
               return var9.field2183 * 1745853678706228013L;
            }
         }

         return 0L;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;III)V")
   @ObfuscatedName("ib")
   void method4429(ModelData var1, int var2, int var3, int var4) {
      int var5 = var3 + 1;
      int var6 = var4 + 1;

      for (int var7 = var3; var7 <= var5; var7++) {
         if (var7 >= 0 && var7 < this.field2043) {
            for (int var8 = var4 - 1; var8 <= var6; var8++) {
               if (var8 >= 0 && var8 < this.field2052 && (var7 >= var5 || var8 >= var6)) {
                  int var9 = this.method4198(var2, var7, var8);
                  if (this.method4485(var9)) {
                     GroundObject var10 = this.field2009[var9];
                     if (var10 != null && var10.field1814 instanceof ModelData) {
                        int var11 = this.method4415(var2, var7, var8) - this.method4196(var2, var3, var4);
                        ModelData var12 = (ModelData)var10.field1814;
                        ModelData.method4082(var1, var12, (var7 - var3) * 128, var11, (var8 - var4) * 128, true);
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bs")
   public void method4227(int var1, int var2, int var3) {
      var2 += this.field1989;
      var3 += this.field2045;
      int var4 = this.method4197(var1, var2, var3);
      if (this.method4490(var4) && (this.field2016[var4] & 2048) != 0) {
         GroundObject var5 = this.field2009[var4];
         this.field2009[var4] = null;
         this.field2016[var4] = this.field2016[var4] & -2049;
         if (var5.field1814 instanceof DynamicObject) {
            rl17 var6 = this.field2017[var2 >> 3][var3 >> 3];
            var6.field5703.remove(var5);
         }

         if (this.field2152) {
            GroundObjectDespawned var9 = new GroundObjectDespawned();
            var9.setTile(this.field2158[var1][var2][var3]);
            var9.setGroundObject(var5);
            classOE.field4841.getCallbacks().post(var9);
         }

         if (this.field2152 && client.field1117 != null) {
            client.field1117.invalidateZone(this, var2 >> 3, var3 >> 3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("du")
   public void method4356(boolean var1) {
      if (!method4364(this) || var1) {
         this.field2041 = true;
         this.field2031 = var1;
         this.field2143 = -1;
         this.field1988 = -1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dk")
   public boolean method4366() {
      return this.field2031 && this.field2144 != -1;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("il")
   int method4278(int var1, int var2, int var3) {
      return (
            this.field2058[var1][var2][var3]
               + this.field2029[var1][var2 + 1][var3]
               + this.field2058[var1][var2][var3 + 1]
               + this.field2058[var1][var2 + 1][var3 + 1]
         )
         / 4;
   }

   @ObfuscatedSignature(descriptor = "(IIIILee;Lee;IIIIIIJI)V")
   @ObfuscatedName("bm")
   public void method4496(
      int var1,
      int var2,
      int var3,
      int var4,
      Renderable var5,
      Renderable var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      long var13,
      int var15
   ) {
      int var16 = var2 + this.field2027;
      int var17 = var3 + this.field2034;
      if (var5 != null) {
         int var18 = this.method4415(var1, var16, var17);
         DecorativeObject var19 = new DecorativeObject();
         var19.method4811(var13);
         var19.method4808(var15);
         var19.method4809(var2 * 128 + 64);
         var19.method4804(var3 * 128 + 64);
         var19.method4802(var4);
         var19.method4813(var5);
         var19.method4813(var6);
         var19.method4809(var7);
         var19.method4812(var8);
         var19.field2267 = var9 * 261748961;
         var19.field2265 = var10 * 46830669;
         var19.field2273 = var11 * -153257471;
         var19.field2268 = var12 * -829209555;

         for (int var20 = var1; var20 >= 0; var20--) {
            int var21 = this.method4415(var20, var16, var17);
            if (!this.method4426(var21)) {
               this.method4189(var21, var20, var16, var17);
            }

            if (this.field1991[var20][var16][var17] == null) {
               this.field1991[var20][var16][var17] = new rl10(this, var21);
               this.method4243(var20, var16, var17);
            }
         }

         assert this.field2008[var18] == null;

         this.field2008[var18] = var19;
         this.field2016[var18] = this.field2016[var18] | 32768;
         var19.field2275 = var1;
         if (var5 instanceof DynamicObject || var6 instanceof DynamicObject) {
            rl17 var22 = this.field2017[var16 >> 3][var17 >> 3];
            var22.field5704.add(var19);
            this.method4348((DynamicObject)var5);
            this.method4348((DynamicObject)var6);
         }

         this.method4513(var5, 0, var19.getConfig(), var19.getXOffset(), var19.getPlane(), var2, var3);
         this.method4513(var6, 0, var19.getPlane(), var19.getId(), var19.method4803(), var2, var3);
         if (this.field2153) {
            DecorativeObjectSpawned var23 = new DecorativeObjectSpawned();
            var23.setTile(this.field2158[var1][var16][var17]);
            var23.setDecorativeObject(var19);
            classOE.field4841.getCallbacks().post(var23);
         }

         if (this.field2152 && client.field1117 != null) {
            client.field1117.invalidateZone(this, var16 >> 3, var17 >> 3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;Ler;III)V")
   @ObfuscatedName("nn")
   public static void method4430(Scene var0, ModelData var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method4429(var1, var2, var2, var2);
      }

      int var5 = var3 + 1;
      int var6 = var4 + 1;

      for (int var7 = var3; var7 <= var5; var7++) {
         if (var7 >= 0 && var7 < var0.field2054) {
            for (int var8 = var4 - 1; var8 <= var6; var8++) {
               if (var8 >= 0 && var8 < var0.field2030 && (var7 >= var5 || var8 >= var6)) {
                  int var9 = var0.method4438(var2, var7, var8);
                  if (var0.method4426(var9)) {
                     GroundObject var10 = var0.field2009[var9];
                     if (var10 != null && var10.field1814 instanceof ModelData) {
                        int var11 = var0.method4415(var2, var7, var8) - var0.method4415(var2, var3, var4);
                        ModelData var12 = (ModelData)var10.field1814;
                        ModelData.method4082(var1, var12, (var7 - var3) * 128, var11, (var8 - var4) * 128, true);
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("gj")
   public void method4262(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field2063; var4++) {
         for (int var5 = 0; var5 < this.field2119; var5++) {
            for (int var6 = 0; var6 < this.field2118; var6++) {
               Tile var7 = this.field2056[var4][var5][var6];
               if (var7 != null) {
                  WallObject var8 = var7.field1968;
                  if (var8 != null && var8.field2246 instanceof ModelData) {
                     ModelData var9 = (ModelData)var8.field2246;
                     this.method4268(var9, var4, var5, var6, 1, 1);
                     if (var8.field2246 instanceof ModelData) {
                        ModelData var10 = (ModelData)var8.field2247;
                        this.method4268(var10, var4, var5, var6, 1, 1);
                        ModelData.method3988(var9, var10, 0, 0, 0, false);
                        var8.field2246 = var10.method4059(var10.field1877, var10.field1876, var1, var2, var3);
                     }

                     var8.field2246 = var9.method4070(var9.field1877, var9.field1876, var1, var2, var3);
                  }

                  for (int var12 = 0; var12 < var7.field1952 * -389646721; var12++) {
                     GameObject var14 = var7.field1949[var12];
                     if (var14 != null && var14.field2198 instanceof ModelData) {
                        ModelData var11 = (ModelData)var14.field2198;
                        this.method4509(
                           var11,
                           var4,
                           var5,
                           var6,
                           var14.field2192 * -536942667 - var14.field2185 * 1138017605 + 1,
                           var14.field2186 * 738944045 - var14.field2188 * -744271729 + 1
                        );
                        var14.field2198 = var11.method4060(var11.field1877, var11.field1877, var1, var2, var3);
                     }
                  }

                  GroundObject var13 = var7.field1965;
                  if (var13 != null && var13.field1814 instanceof ModelData) {
                     ModelData var15 = (ModelData)var13.field1814;
                     method4430(this, var15, var4, var5, var6);
                     var13.field1814 = var15.method4059(var15.field1877, var15.field1876, var1, var2, var3);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("hm")
   public void method4239(int var1, int var2, int var3) {
      Tile var4 = this.field2056[var1][var2][var3];
      if (var4 != null) {
         var4.field1969 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;III)V")
   @ObfuscatedName("cd")
   void method4266(ModelData var1, int var2, int var3, int var4) {
      int var6 = var3 + 1;
      int var7 = var4 - 1;
      int var8 = var4 + 1;

      for (int var9 = var3; var9 <= var6; var9++) {
         if (var9 >= 0 && var9 < this.field2118) {
            for (int var10 = var7; var10 <= var8; var10++) {
               if (var10 >= 0 && var10 < this.field2049 && (var9 >= var6 || var10 >= var8)) {
                  Tile var11 = this.field2056[var2][var9][var10];
                  if (var11 != null && var11.field1965 != null && var11.field1965.field1814 instanceof ModelData) {
                     int var12 = this.method4271(var2, var9, var10, var2, var3, var4);
                     ModelData var13 = (ModelData)var11.field1965.field1814;
                     ModelData.method3988(var1, var13, (var9 - var3) * 128, var12, (var10 - var4) * 128, true);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl17;II)Z")
   @ObfuscatedName("yh")
   public boolean method4414(rl17 var1, int var2, int var3) {
      if ((client.field1021 & 32) != 0) {
         return client.field1117.zoneInFrustum(var2, var3, var1.field5708, var1.field5706);
      } else {
         int var4 = var1.field5706;
         int var5 = var1.field5708;
         int var6 = ((var2 << 3) - this.field2146 << 7) + 512 - this.field2116;
         int var7 = ((var3 << 3) - this.field2146 << 7) + 512 - this.field2114;
         int var8 = var5 - this.field2117;
         short var9 = 724;
         int var10 = classFH.field2255.method4632();
         int var11 = classFH.field2255.method4630();
         int var12 = classFH.field2255.method4627();
         int var13 = classFH.field2255.method4628();
         int var14 = classFH.field2255.method4631();
         float var15 = this.field2090 * var7 - this.field2092 * var6;
         float var16 = this.field2085 * var8 + this.field2088 * var15;
         float var17 = this.field2088 * var9;
         float var18 = var16 + var17;
         if (var18 > 50.0F) {
            float var19 = var7 * this.field2092 + this.field2090 * var6;
            float var20 = (var19 - var9) * var10;
            float var21 = (var19 + var9) * var10;
            if (var20 < var11 * var18 && var21 > var12 * var18) {
               float var22 = this.field2088 * var8 - var15 * this.field2085;
               float var23 = this.field2085 * var9;
               float var24 = (var22 + var23) * var10;
               if (var24 > var13 * var18) {
                  float var25 = this.field2088 * (var5 - var4) + var23;
                  float var26 = (var22 - var25) * var10;
                  if (var26 < var14 * var18) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("kj")
   public void method4357(boolean var1) {
      if (!method4364(this) || var1) {
         this.field2041 = true;
         this.field2041 = var1;
         this.field2142 = -1;
         this.field2145 = -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIJ)I")
   @ObfuscatedName("cq")
   public int method4257(int var1, int var2, int var3, long var4) {
      Tile var6 = this.field2056[var1][var2][var3];
      if (var6 == null) {
         return -1;
      } else if (var6.field1968 != null && var6.field1968.field2238 * 7159317397185328723L == var4) {
         return var6.field1968.field2241 * 1869636241 & 0xFF;
      } else if (var6.field1969 != null && var6.field1969.field2264 * -3608662145112923099L == var4) {
         return var6.field1969.field2263 * -1506294211 & 0xFF;
      } else if (var6.field1965 != null && var6.field1965.field1813 * 5132338419383099835L == var4) {
         return GroundObject.method3958(var6.field1965, 595746927) & 0xFF;
      } else {
         for (int var7 = 0; var7 < var6.field1947 * -389646721; var7++) {
            if (var6.field1949[var7].field2183 * 1745853678706228013L == var4) {
               return var6.field1949[var7].field2194 * -288062455 & 0xFF;
            }
         }

         return -1;
      }
   }
}
