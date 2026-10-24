import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@ObfuscatedName("mi")
public class classMI {
   @ObfuscatedSignature(descriptor = "Lmi;")
   @ObfuscatedName("af")
   static final classMI field4507 = new classMI();
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final String field4509 = "osrs";
   @ObfuscatedName("ae")
   Map field4506 = new HashMap();
   @ObfuscatedName("ab")
   int field4505 = 0;
   @ObfuscatedName("gq")
   static int field4508;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("ae")
   public static String method7619(String var0, int var1) {
      try {
         return (String)classGQ.field2650.get(var0.toLowerCase());
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mi.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;ILda;B)V")
   @ObfuscatedName("az")
   void method7617(JSONObject var1, int var2, classDA var3, byte var4) {
      try {
         this.field4506.clear();

         JSONObject var5;
         try {
            var5 = var1;
            this.field4505 = var2 * -1363556091;
            var1.getLong("lastfullsyncat");
            var1.getLong("lastcardupdatedat");
         } catch (Exception var8) {
            return;
         }

         try {
            this.method7620(var5.getJSONArray("crmcomponents"), 229940173 * this.field4505, var3, (byte)1);
         } catch (Exception var7) {
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "mi.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONArray;ILda;B)V")
   @ObfuscatedName("af")
   void method7620(JSONArray var1, int var2, classDA var3, byte var4) throws JSONException {
      try {
         if (var1 != null) {
            for (int var5 = 0; var5 < var1.length(); var5++) {
               if (var4 != 1) {
                  return;
               }

               JSONObject var6 = var1.getJSONObject(var5);
               if (!var6.getString("game").equals("osrs")) {
                  if (var4 != 1) {
                     throw new IllegalStateException();
                  }
               } else if (var6.getBoolean("removed")) {
                  if (var4 != 1) {
                     throw new IllegalStateException();
                  }
               } else {
                  String var7 = var6.getString("platform");
                  if (!var7.equals("mobile")) {
                     if (var4 != 1) {
                        throw new IllegalStateException();
                     }

                     if (!var7.equals("android")) {
                        if (var4 != 1) {
                           throw new IllegalStateException();
                        }

                        if (var7.equals("ios")) {
                           if (var4 != 1) {
                              throw new IllegalStateException();
                           }
                        } else {
                           classMS var8 = new classMS();

                           try {
                              var8.method7709(var6, var2, var3, -2022071435);
                           } catch (Exception var10) {
                           }

                           if (!var8.field4568.isEmpty()) {
                              String var9 = var6.getString("location");
                              this.field4506.put(var9, var8);
                           }
                        }
                     }
                  }
               }
            }

            this.field4506 = this.method7624((short)211);
            classDD.method2988(16711680);
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "mi.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Ljava/util/Map;")
   @ObfuscatedName("ae")
   Map method7624(short var1) {
      try {
         HashMap var2 = new HashMap();
         ArrayList var3 = new ArrayList();
         var3.addAll(this.field4506.entrySet());
         boolean var4 = false;

         while (!var4) {
            if (var1 != 211) {
               throw new IllegalStateException();
            }

            var4 = true;

            for (int var6 = 0; var6 < var3.size() - 1; var6++) {
               if (var1 != 211) {
                  throw new IllegalStateException();
               }

               if (((classMS)((Entry)var3.get(var6)).getValue()).method7703((byte)20) > ((classMS)((Entry)var3.get(1 + var6)).getValue()).method7703((byte)-33)
                  )
                {
                  if (var1 != 211) {
                     throw new IllegalStateException();
                  }

                  Entry var5 = (Entry)var3.get(var6);
                  var3.set(var6, var3.get(1 + var6));
                  var3.set(var6 + 1, var5);
                  var4 = false;
               }
            }
         }

         Iterator var9 = var3.iterator();

         while (var9.hasNext()) {
            if (var1 != 211) {
               throw new IllegalStateException();
            }

            Entry var7 = (Entry)var9.next();
            var2.put(var7.getKey(), var7.getValue());
         }

         return var2;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "mi.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Lms;")
   @ObfuscatedName("ab")
   classMS method7631(String var1, int var2) {
      try {
         return (classMS)this.field4506.get(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mi.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmi;)Ljava/util/Map;")
   @ObfuscatedName("hb")
   public static Map method7625(classMI var0) {
      if (var0 == null) {
         var0.method7629();
      }

      HashMap var1 = new HashMap();
      ArrayList var2 = new ArrayList();
      var2.addAll(var0.field4506.entrySet());
      boolean var3 = false;

      while (!var3) {
         var3 = true;

         for (int var5 = 0; var5 < var2.size() - 1; var5++) {
            if (((classMS)((Entry)var2.get(var5)).getValue()).method7703((byte)-59) > ((classMS)((Entry)var2.get(1 + var5)).getValue()).method7703((byte)7)) {
               Entry var4 = (Entry)var2.get(var5);
               var2.set(var5, var2.get(1 + var5));
               var2.set(var5 + 1, var4);
               var3 = false;
            }
         }
      }

      for (Entry var6 : var2) {
         var1.put(var6.getKey(), var6.getValue());
      }

      return var1;
   }

   classMI() {
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;ILda;)V")
   @ObfuscatedName("ag")
   void method7618(JSONObject var1, int var2, classDA var3) {
      this.field4506.clear();

      JSONObject var4;
      try {
         var4 = var1;
         this.field4505 = var2 * -1363556091;
         var1.getLong("lastfullsyncat");
         var1.getLong("lastcardupdatedat");
      } catch (Exception var7) {
         return;
      }

      try {
         this.method7620(var4.getJSONArray("crmcomponents"), 229940173 * this.field4505, var3, (byte)1);
      } catch (Exception var6) {
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Map;")
   @ObfuscatedName("aj")
   Map method7626() {
      HashMap var1 = new HashMap();
      ArrayList var2 = new ArrayList();
      var2.addAll(this.field4506.entrySet());
      boolean var3 = false;

      while (!var3) {
         var3 = true;

         for (int var5 = 0; var5 < var2.size() - 1; var5++) {
            if (((classMS)((Entry)var2.get(var5)).getValue()).method7703((byte)54) > ((classMS)((Entry)var2.get(1 + var5)).getValue()).method7703((byte)-44)) {
               Entry var4 = (Entry)var2.get(var5);
               var2.set(var5, var2.get(1 + var5));
               var2.set(var5 + 1, var4);
               var3 = false;
            }
         }
      }

      for (Entry var6 : var2) {
         var1.put(var6.getKey(), var6.getValue());
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONArray;ILda;)V")
   @ObfuscatedName("ax")
   void method7621(JSONArray var1, int var2, classDA var3) throws JSONException {
      if (var1 != null) {
         for (int var4 = 0; var4 < var1.length(); var4++) {
            JSONObject var5 = var1.getJSONObject(var4);
            if (var5.getString("game").equals("osrs") && !var5.getBoolean("removed")) {
               String var6 = var5.getString("platform");
               if (!var6.equals("mobile") && !var6.equals("android") && !var6.equals("ios")) {
                  classMS var7 = new classMS();

                  try {
                     var7.method7709(var5, var2, var3, 1486517862);
                  } catch (Exception var9) {
                  }

                  if (!var7.field4568.isEmpty()) {
                     String var8 = var5.getString("location");
                     this.field4506.put(var8, var7);
                  }
               }
            }
         }

         this.field4506 = this.method7624((short)211);
         classDD.method2988(16711680);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Map;")
   @ObfuscatedName("aa")
   Map method7627() {
      HashMap var1 = new HashMap();
      ArrayList var2 = new ArrayList();
      var2.addAll(this.field4506.entrySet());
      boolean var3 = false;

      while (!var3) {
         var3 = true;

         for (int var5 = 0; var5 < var2.size() - 1; var5++) {
            if (((classMS)((Entry)var2.get(var5)).getValue()).method7703((byte)-59) > ((classMS)((Entry)var2.get(1 + var5)).getValue()).method7703((byte)-2)) {
               Entry var4 = (Entry)var2.get(var5);
               var2.set(var5, var2.get(1 + var5));
               var2.set(var5 + 1, var4);
               var3 = false;
            }
         }
      }

      for (Entry var6 : var2) {
         var1.put(var6.getKey(), var6.getValue());
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONArray;ILda;)V")
   @ObfuscatedName("as")
   void method7622(JSONArray var1, int var2, classDA var3) throws JSONException {
      if (var1 != null) {
         for (int var4 = 0; var4 < var1.length(); var4++) {
            JSONObject var5 = var1.getJSONObject(var4);
            if (var5.getString("game").equals("osrs") && !var5.getBoolean("removed")) {
               String var6 = var5.getString("platform");
               if (!var6.equals("mobile") && !var6.equals("android") && !var6.equals("ios")) {
                  classMS var7 = new classMS();

                  try {
                     var7.method7709(var5, var2, var3, -1630162249);
                  } catch (Exception var9) {
                  }

                  if (!var7.field4568.isEmpty()) {
                     String var8 = var5.getString("location");
                     this.field4506.put(var8, var7);
                  }
               }
            }
         }

         this.field4506 = this.method7624((short)211);
         classDD.method2988(16711680);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Map;")
   @ObfuscatedName("ao")
   Map method7628() {
      HashMap var1 = new HashMap();
      ArrayList var2 = new ArrayList();
      var2.addAll(this.field4506.entrySet());
      boolean var3 = false;

      while (!var3) {
         var3 = true;

         for (int var5 = 0; var5 < var2.size() - 1; var5++) {
            if (((classMS)((Entry)var2.get(var5)).getValue()).method7703((byte)-111) > ((classMS)((Entry)var2.get(1 + var5)).getValue()).method7703((byte)-28)) {
               Entry var4 = (Entry)var2.get(var5);
               var2.set(var5, var2.get(1 + var5));
               var2.set(var5 + 1, var4);
               var3 = false;
            }
         }
      }

      for (Entry var6 : var2) {
         var1.put(var6.getKey(), var6.getValue());
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Map;")
   @ObfuscatedName("al")
   Map method7629() {
      HashMap var1 = new HashMap();
      ArrayList var2 = new ArrayList();
      var2.addAll(this.field4506.entrySet());
      boolean var3 = false;

      while (!var3) {
         var3 = true;

         for (int var5 = 0; var5 < var2.size() - 1; var5++) {
            if (((classMS)((Entry)var2.get(var5)).getValue()).method7703((byte)-111) > ((classMS)((Entry)var2.get(1 + var5)).getValue()).method7703((byte)-74)) {
               Entry var4 = (Entry)var2.get(var5);
               var2.set(var5, var2.get(1 + var5));
               var2.set(var5 + 1, var4);
               var3 = false;
            }
         }
      }

      for (Entry var6 : var2) {
         var1.put(var6.getKey(), var6.getValue());
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONArray;ILda;)V")
   @ObfuscatedName("ac")
   void method7623(JSONArray var1, int var2, classDA var3) throws JSONException {
      if (var1 != null) {
         for (int var4 = 0; var4 < var1.length(); var4++) {
            JSONObject var5 = var1.getJSONObject(var4);
            if (var5.getString("game").equals("osrs") && !var5.getBoolean("removed")) {
               String var6 = var5.getString("platform");
               if (!var6.equals("mobile") && !var6.equals("android") && !var6.equals("ios")) {
                  classMS var7 = new classMS();

                  try {
                     var7.method7709(var5, var2, var3, 350632438);
                  } catch (Exception var9) {
                  }

                  if (!var7.field4568.isEmpty()) {
                     String var8 = var5.getString("location");
                     this.field4506.put(var8, var7);
                  }
               }
            }
         }

         this.field4506 = this.method7624((short)211);
         classDD.method2988(16711680);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Map;")
   @ObfuscatedName("aq")
   Map method7630() {
      HashMap var1 = new HashMap();
      ArrayList var2 = new ArrayList();
      var2.addAll(this.field4506.entrySet());
      boolean var3 = false;

      while (!var3) {
         var3 = true;

         for (int var5 = 0; var5 < var2.size() - 1; var5++) {
            if (((classMS)((Entry)var2.get(var5)).getValue()).method7703((byte)-29) > ((classMS)((Entry)var2.get(1 + var5)).getValue()).method7703((byte)38)) {
               Entry var4 = (Entry)var2.get(var5);
               var2.set(var5, var2.get(1 + var5));
               var2.set(var5 + 1, var4);
               var3 = false;
            }
         }
      }

      for (Entry var6 : var2) {
         var1.put(var6.getKey(), var6.getValue());
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lms;")
   @ObfuscatedName("ap")
   classMS method7632(String var1) {
      return (classMS)this.field4506.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lms;")
   @ObfuscatedName("ad")
   classMS method7633(String var1) {
      return (classMS)this.field4506.get(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;Lxy;)V")
   @ObfuscatedName("qd")
   public static void method7634(Widget var0, classXY var1) {
      if (var0 == null) {
         var0.isSelfHidden();
      }

      var0.field4273 = false;
      var0.field4373 = var1.method13095(-346779531) * -128351660;
      var0.field4245 = var1.method13095(-346779531) * -811438059;
      var0.field4332 = var1.method13099(1401870401) * -411168601;
      var0.field4246 = var1.method13104((byte)16) * -1076753689;
      var0.field4283 = var1.method13104((byte)16) * 1286043683;
      var0.field4253 = var1.method13099(-60978058) * -1283191478;
      var0.field4333 = var1.method13099(1042200530) * -259501473;
      var0.field4345 = var1.method13095(-346779531) * 204805685;
      var0.field4340 = var1.method13099(1143601347) * 328312260;
      if (-621277242 == var0.field4340 * -1502647899) {
         var0.field4340 = -70821381;
      } else {
         var0.field4340 = -1504986339 * ((-682734575 * var0.field4327 & -65536) + -1502647899 * var0.field4340);
      }

      var0.field4314 = var1.method13099(1687274602) * 335111401;
      if (-2003253155 * var0.field4314 == -1912793676) {
         var0.field4314 = 1709618187;
      }

      int var2 = var1.method13095(-346779531);
      if (var2 > 0) {
         var0.field4398 = new int[var2];
         var0.field4399 = new int[var2];

         for (int var3 = 0; var3 < var2; var3++) {
            var0.field4398[var3] = var1.method13095(-346779531);
            var0.field4399[var3] = var1.method13099(-551266725);
         }
      }

      int var7 = var1.method13095(-346779531);
      if (var7 > 0) {
         var0.field4397 = new int[var7][];

         for (int var4 = 0; var4 < var7; var4++) {
            int var5 = var1.method13099(-80780034);
            var0.field4397[var4] = new int[var5];

            for (int var6 = 0; var6 < var5; var6++) {
               var0.field4397[var4][var6] = var1.method13099(-374051823);
               if (var0.field4397[var4][var6] == 65535) {
                  var0.field4397[var4][var6] = -1;
               }
            }
         }
      }

      if (0 == 1850134265 * var0.field4373) {
         var0.field4267 = var1.method13099(1546002490) * 1065509630;
         var0.field4287 = var1.method13095(-346779531) == 1;
      }

      if (933354515 * var0.field4373 == 1) {
         var1.method13099(-1915000606);
         var1.method13095(-346779531);
      }

      if (3 == -1510882375 * var0.field4373) {
         var0.field4269 = var1.method13095(-346779531) == 1;
      }

      if (4 == -1510882375 * var0.field4373 || 1 == var0.field4373 * -683575018) {
         var0.field4302 = var1.method13095(-346779531) * 571804797;
         var0.field4303 = var1.method13095(-346779531) * -2106140265;
         var0.field4301 = var1.method13095(-346779531) * -763080175;
         var0.field4243 = var1.method13099(399617741) * 590574429;
         if (65535 == var0.field4243 * -1803847435) {
            var0.field4243 = -590574429;
         }

         var0.field4304 = var1.method13095(-346779531) == 1;
      }

      if (var0.field4373 * 1297721274 == 4) {
         var0.field4299 = var1.method13127(-12428429);
         var0.field4300 = var1.method13127(2111397842);
      }

      if (1 == -1510882375 * var0.field4373 || 3 == var0.field4373 * -1510882375 || var0.field4373 * -1094427885 == 4) {
         var0.field4268 = classXY.method13113(var1, (byte)1) * 1590850751;
      }

      if (748512751 * var0.field4373 == 3 || 4 == var0.field4373 * -1510882375) {
         var0.field4293 = classXY.method13113(var1, (byte)1) * -1914223631;
         var0.field4270 = classXY.method13113(var1, (byte)1) * -1882190803;
         var0.field4271 = classXY.method13113(var1, (byte)1) * -1240050015;
      }

      if (5 == var0.field4373 * -1517632705) {
         var0.field4346 = classXY.method13113(var1, (byte)1) * 1518739138;
         var0.field4347 = classXY.method13113(var1, (byte)1) * -421179023;
      }

      if (-1510882375 * var0.field4373 == 6) {
         var0.field4331 = 656328391;
         var0.field4285 = -780893970;
         var0.field4372 = var1.method13173(classXM.field6886.field6888, 1906598215) * -87747870;
         var0.field4352 = var1.method13173(classXM.field6886.field6888, 1817992397) * 607878109;
         var0.field4286 = var1.method13099(1354374627) * 742198477;
         if (-142663889 == 511129499 * var0.field4286) {
            var0.field4286 = 439398981;
         }

         var0.field4291 = var1.method13099(-315022870) * 860859185;
         if (821151237 * var0.field4291 == 343415003) {
            var0.field4291 = -668997325;
         }

         var0.field4336 = var1.method13099(1127683100) * 2091023102;
         var0.field4354 = var1.method13099(-1891040268) * 221177667;
         var0.field4290 = var1.method13099(527139157) * 993003239;
      }

      if (8 == -1510882375 * var0.field4373) {
         var0.field4299 = var1.method13127(-104668453);
      }

      if (var0.field4245 * -851058883 == 2) {
         var0.field4343 = var1.method13127(281444230);
         var0.field4315 = var1.method13127(-470459418);
         int var8 = var1.method13099(-265984299) & 1535487212;
         var0.field4318 = (var0.field4318 * 1595830249 | var8 << 11) * -478897898;
      }

      if (var0.field4245 * -851058883 == 1 || 4 == var0.field4245 * -851058883 || 5 == -851058883 * var0.field4245 || 6 == var0.field4245 * -851058883) {
         var0.field4316 = var1.method13127(396039062);
         if (var0.field4316.isEmpty()) {
            if (1 == var0.field4245 * -851058883) {
               var0.field4316 = classKK.field3793;
            }

            if (4 == 2094523513 * var0.field4245) {
               var0.field4316 = classKK.field3610;
            }

            if (-1420259847 * var0.field4245 == 5) {
               var0.field4316 = classKK.field3610;
            }

            if (-851058883 * var0.field4245 == 6) {
               var0.field4316 = classKK.field3775;
            }
         }
      }

      if (1394324302 * var0.field4245 == 1 || 4 == -851058883 * var0.field4245 || -851058883 * var0.field4245 == 5) {
         var0.field4318 = -1082124704 * (1595830249 * var0.field4318 | 4194304);
      }

      if (-241045171 * var0.field4245 == 6) {
         var0.field4318 = (var0.field4318 * 1595830249 | 1) * 997939289;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIIIIIB)V")
   @ObfuscatedName("ey")
   static final void method7635(WorldView var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, byte var9) {
      boolean var10 = client.field886;

      try {
         client.field886 = false;
         int var20 = var8;
         int var19 = var7;
         int var18 = var6;
         int var17 = var5;
         int var16 = var4;
         int var15 = var3;
         int var14 = var2;
         int var13 = var1;
         WorldView var12 = var0;
         byte var21 = 2;

         try {
            Scene var22 = var12.field1687;
            if (var15 >= 1) {
               if (var21 != 2) {
                  throw new IllegalStateException();
               }

               if (var16 >= 1) {
                  if (var21 != 2) {
                     throw new IllegalStateException();
                  }

                  if (var15 <= var12.field1694 * 1296729483 - 2) {
                     if (var21 != 2) {
                        throw new IllegalStateException();
                     }

                     if (var16 <= -1269171107 * var12.field1690 - 2) {
                        if (client.field886) {
                           if (var21 != 2) {
                              return;
                           }

                           if (-483624883 * var12.field1708 != var13) {
                              if (var21 != 2) {
                                 throw new IllegalStateException();
                              }

                              return;
                           }
                        }

                        Renderable var23 = null;
                        Renderable var24 = null;
                        int var25 = -1;
                        long var26 = 0L;
                        int var28 = 0;
                        int var29 = 0;
                        if (0 == var14) {
                           if (var21 != 2) {
                              throw new IllegalStateException();
                           }

                           var26 = var22.method4461(var13, var15, var16);
                        }

                        if (1 == var14) {
                           var26 = var22.method4450(var13, var15, var16);
                        }

                        if (var14 == 2) {
                           if (var21 != 2) {
                              return;
                           }

                           var26 = rl0.method10065(var22, var13, var15, var16);
                        }

                        if (var14 == 3) {
                           if (var21 != 2) {
                              return;
                           }

                           var26 = var22.method4413(var13, var15, var16);
                        }

                        if (0L != var26) {
                           if (var21 != 2) {
                              return;
                           }

                           int var30 = classKK.method6532(var22, var13, var15, var16, var26);
                           var25 = classCN.method2542(var26);
                           var28 = var30 & 31;
                           var29 = var30 >> 6 & 3;
                           ObjectComposition var31 = classMU.method7771(var25, 1505586716);
                           var12.method3764(var13, var15, var16, var31, var29, (byte)106);
                           if (0 == var14) {
                              if (var21 != 2) {
                                 throw new IllegalStateException();
                              }

                              WallObject var32 = var22.method4515(var13, var15, var16);
                              var23 = var32.field2247;
                              var24 = var32.field2246;
                              var22.method4463(var13, var15, var16);
                              if (var31.field4944 * -2040437663 != 0) {
                                 CollisionData.method5406(var12.field1685[var13], var15, var16, var28, var29, var31.field4945, -2142373270);
                              }
                           }

                           if (var14 == 1) {
                              DecorativeObject var41 = var22.method4498(var13, var15, var16);
                              var23 = var41.field2277;
                              var24 = var41.field2276;
                              var22.method4465(var13, var15, var16);
                           }

                           if (var14 == 2) {
                              var23 = Scene.method4487(var22, var13, var15, var16).field2198;
                              var22.method4464(var13, var15, var16);
                              if (-582306509 * var31.field4942 + var15 > 1296729483 * var12.field1694 - 1) {
                                 return;
                              }

                              if (var21 != 2) {
                                 throw new IllegalStateException();
                              }

                              if (var16 + -582306509 * var31.field4942 > -1269171107 * var12.field1690 - 1
                                 || var15 + 402876423 * var31.field4922 > var12.field1694 * 1296729483 - 1) {
                                 return;
                              }

                              if (402876423 * var31.field4922 + var16 > var12.field1690 * -1269171107 - 1) {
                                 if (var21 != 2) {
                                    throw new IllegalStateException();
                                 }

                                 return;
                              }

                              if (0 != -2040437663 * var31.field4944) {
                                 if (var21 != 2) {
                                    throw new IllegalStateException();
                                 }

                                 var12.field1685[var13]
                                    .method5408(var15, var16, -582306509 * var31.field4942, var31.field4922 * 402876423, var29, var31.field4945, (byte)13);
                              }
                           }

                           if (3 == var14) {
                              if (var21 != 2) {
                                 throw new IllegalStateException();
                              }

                              var23 = var22.method4468(var13, var15, var16).field1814;
                              Scene.method4226(var22, var13, var15, var16);
                              if (-2040437663 * var31.field4944 == 1) {
                                 if (var21 != 2) {
                                    throw new IllegalStateException();
                                 }

                                 var12.field1685[var13].method5411(var15, var16, 1400157407);
                              }
                           }
                        }

                        if (var17 >= 0) {
                           if (var21 != 2) {
                              throw new IllegalStateException();
                           }

                           int var39 = var13;
                           if (classSG.method10467(var12, var13, var15, var16, 1423908992)) {
                              if (var21 != 2) {
                                 throw new IllegalStateException();
                              }

                              var39 = var13 + 1;
                           }

                           if (var17 != var25) {
                              if (var21 != 2) {
                                 throw new IllegalStateException();
                              }

                              var23 = null;
                              var24 = null;
                           }

                           classFK.method4839(var12, var13, var39, var15, var16, var17, var18, var19, var20, var12.field1685[var13], var23, var24, 1370119256);
                           ObjectComposition var40 = classMU.method7771(var17, 1715466882);
                           if (null != var40) {
                              if (var21 != 2) {
                                 throw new IllegalStateException();
                              }

                              if (var40.method8601(1754865660) && var21 == 2) {
                                 var12.method3755(var39, var15, var16, var40, var18, 1781268585);
                              }
                           }
                        }
                     }
                  }
               }
            }
         } catch (RuntimeException var35) {
            throw classEG.method3899(var35, "mi.ey(" + ')');
         }
      } finally {
         client.field886 = var10;
      }
   }
}
