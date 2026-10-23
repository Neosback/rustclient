import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@ObfuscatedName("ms")
public class classMS {
   @ObfuscatedName("aa")
   String field4568;
   @ObfuscatedName("af")
   ArrayList field4573 = new ArrayList();
   @ObfuscatedName("ae")
   ArrayList field4567 = new ArrayList();
   @ObfuscatedName("ao")
   int field4572;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4574 = 2;
   @ObfuscatedName("as")
   float[] field4569;
   @ObfuscatedName("ab")
   ArrayList field4566 = new ArrayList();
   @ObfuscatedName("ag")
   String field4576;
   @ObfuscatedName("ax")
   Map field4570;
   @ObfuscatedName("ac")
   Map field4571;
   @ObfuscatedSignature(descriptor = "[Lym;")
   @ObfuscatedName("mp")
   static SpritePixels[] field4577;
   @ToRemove(unused = "true")
   @ObfuscatedName("bd")
   static final int field4575 = 58;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("ai")
   static int method7696(String var0) {
      if (var0.equals("centre")) {
         return 1;
      } else {
         return !var0.equals("bottom") && !var0.equals("right") ? 0 : 2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;ILda;)Z")
   @ObfuscatedName("ah")
   boolean method7708(JSONObject var1, int var2, classDA var3) {
      JSONObject var4 = var1;
      if (var2 < 2) {
         try {
            var4 = var4.getJSONObject("message");
         } catch (Exception var13) {
            this.method7705(447937518);
            return false;
         }

         try {
            this.method7710(var4.getJSONArray("images"), var3, 1160694231);
         } catch (Exception var12) {
            this.field4567.clear();
         }

         try {
            this.method7719(var4.getJSONArray("labels"), (byte)80);
         } catch (Exception var11) {
            this.field4566.clear();
         }
      } else {
         try {
            this.method7715(var4.getJSONObject("image"), var3, (byte)42);
         } catch (Exception var10) {
            this.field4567.clear();
         }

         try {
            this.method7720(var4.getJSONObject("label"), -442344273);
         } catch (Exception var9) {
            this.field4566.clear();
         }
      }

      try {
         this.method7722(var4.getJSONObject("behaviour"), -1395077632);
      } catch (Exception var8) {
         this.field4568 = null;
         this.field4569[0] = 0.0F;
         this.field4569[1] = 0.0F;
         this.field4569[2] = 1.0F;
         this.field4569[3] = 1.0F;
         this.field4573.clear();
      }

      try {
         JSONObject var5 = var4.optJSONObject("meta");
         if (var5 != null) {
            method7724(this, var4.getJSONObject("meta"), -1172378539);
         }
      } catch (Exception var7) {
         this.field4570.clear();
         this.field4571.clear();
      }

      if (2 == var2) {
         try {
            this.field4576 = var4.getString("id");
            this.field4572 = Integer.parseInt(var4.getString("priority")) * 1935874185;
         } catch (Exception var6) {
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ab")
   int method7703(byte var1) {
      try {
         return this.field4572 * 810409401;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ms.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   void method7705(int var1) {
      try {
         this.field4568 = null;
         this.field4569[0] = 0.0F;
         this.field4569[1] = 0.0F;
         this.field4569[2] = 1.0F;
         this.field4569[3] = 1.0F;
         this.field4573.clear();
         this.field4567.clear();
         this.field4566.clear();
         this.field4570.clear();
         this.field4571.clear();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ms.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONArray;Lda;I)V")
   @ObfuscatedName("ax")
   void method7710(JSONArray var1, classDA var2, int var3) throws JSONException {
      try {
         if (var1 == null) {
            if (var3 == 1160694231) {
               ;
            }
         } else {
            for (int var4 = 0; var4 < var1.length(); var4++) {
               if (var3 != 1160694231) {
                  throw new IllegalStateException();
               }

               try {
                  JSONObject var5 = var1.getJSONObject(var4);
                  classMN var6 = new classMN(this);
                  var6.field4542 = var2.method2871(new URL(var5.getString("src")), 2028007151);
                  var6.field4539 = classCL.method1574(var5, "placement", 1052274019);
                  this.field4567.add(var6);
               } catch (MalformedURLException var7) {
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ms.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;Lda;B)V")
   @ObfuscatedName("ac")
   void method7715(JSONObject var1, classDA var2, byte var3) throws JSONException {
      try {
         try {
            classMN var4 = new classMN(this);
            var4.field4542 = var2.method2871(new URL(var1.getString("src")), 334155730);
            var4.field4539 = classCL.method1574(var1, "placement", 1805024149);
            this.field4567.add(var4);
         } catch (MalformedURLException var5) {
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ms.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aw")
   String method7700() {
      return this.field4576;
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;I)V")
   @ObfuscatedName("ao")
   void method7720(JSONObject var1, int var2) throws JSONException {
      try {
         classML var3 = new classML(this);
         var3.field4530 = var1.optString("text");
         var3.field4521 = classDP.method3525(var1.optString("align_x"), -425559892) * -1559171701;
         var3.field4524 = classDP.method3525(var1.optString("align_y"), 1237641541) * 1098088659;
         var3.field4522 = var1.optInt("font") * -1060228675;
         var3.field4523 = classCL.method1574(var1, "placement", 1267787127);
         this.field4566.add(var3);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ms.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;I)V")
   @ObfuscatedName("al")
   void method7722(JSONObject var1, int var2) throws JSONException {
      try {
         if (null != var1) {
            this.field4569 = classCL.method1574(var1, "clickbounds", 1429637239);
            this.field4568 = var1.getString("endpoint");
            String[] var3 = JSONObject.getNames(var1);

            for (int var4 = 0; var4 < var1.length(); var4++) {
               if (var2 >= 916013409) {
                  return;
               }

               if (!var3[var4].equals("clickbounds")) {
                  if (var2 >= 916013409) {
                     return;
                  }

                  if (!var3[var4].equals("endpoint")) {
                     if (var2 >= 916013409) {
                        throw new IllegalStateException();
                     }

                     try {
                        int var5 = var1.getInt(var3[var4]);
                        this.field4573.add(new classMZ(this, var3[var4], var5));
                     } catch (Exception var8) {
                        try {
                           String var6 = var1.getString(var3[var4]);
                           if (var6.equals("true")) {
                              if (var2 >= 916013409) {
                                 throw new IllegalStateException();
                              }

                              this.field4573.add(new classMZ(this, var3[var4], 1));
                           } else if (var6.equals("false")) {
                              this.field4573.add(new classMZ(this, var3[var4], 0));
                           } else {
                              this.field4573.add(new classMP(this, var3[var4], var6));
                           }
                        } catch (Exception var7) {
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ms.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;Ljava/lang/String;)[F")
   @ObfuscatedName("ay")
   static float[] method7693(JSONObject var0, String var1) throws JSONException {
      float[] var2 = new float[4];

      try {
         JSONArray var3 = var0.getJSONArray(var1);
         var2[0] = (float)var3.optDouble(0, 0.0);
         var2[1] = (float)var3.optDouble(1, 0.0);
         var2[2] = (float)var3.optDouble(2, 1.0);
         var2[3] = (float)var3.optDouble(3, 1.0);
      } catch (JSONException var4) {
         var2[0] = 0.0F;
         var2[1] = 0.0F;
         var2[2] = 1.0F;
         var2[3] = 1.0F;
      }

      return var2;
   }

   classMS() {
      this.field4568 = null;
      this.field4569 = new float[4];
      this.field4570 = new HashMap();
      this.field4571 = new HashMap();
      this.field4572 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;Ljava/lang/String;)[F")
   @ObfuscatedName("ad")
   static float[] method7694(JSONObject var0, String var1) throws JSONException {
      float[] var2 = new float[4];

      try {
         JSONArray var3 = var0.getJSONArray(var1);
         var2[0] = (float)var3.optDouble(0, 0.0);
         var2[1] = (float)var3.optDouble(1, 0.0);
         var2[2] = (float)var3.optDouble(2, 1.0);
         var2[3] = (float)var3.optDouble(3, 1.0);
      } catch (JSONException var4) {
         var2[0] = 0.0F;
         var2[1] = 0.0F;
         var2[2] = 1.0F;
         var2[3] = 1.0F;
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("ap")
   static int method7697(String var0) {
      if (var0.equals("centre")) {
         return 1;
      } else {
         return !var0.equals("bottom") && !var0.equals("right") ? 0 : 2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("au")
   static int method7698(String var0) {
      if (var0.equals("centre")) {
         return 1;
      } else {
         return !var0.equals("bottom") && !var0.equals("right") ? 0 : 2;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   void method7706() {
      this.field4568 = null;
      this.field4569[0] = 0.0F;
      this.field4569[1] = 0.0F;
      this.field4569[2] = 1.0F;
      this.field4569[3] = 1.0F;
      this.field4573.clear();
      this.field4567.clear();
      this.field4566.clear();
      this.field4570.clear();
      this.field4571.clear();
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ae")
   String method7701(int var1) {
      try {
         return this.field4576;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ms.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ak")
   String method7702() {
      return this.field4576;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   int method7704() {
      return this.field4572 * 810409401;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   void method7707() {
      this.field4568 = null;
      this.field4569[0] = 0.0F;
      this.field4569[1] = 0.0F;
      this.field4569[2] = 1.0F;
      this.field4569[3] = 1.0F;
      this.field4573.clear();
      this.field4567.clear();
      this.field4566.clear();
      this.field4570.clear();
      this.field4571.clear();
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;Lda;)V")
   @ObfuscatedName("bm")
   void method7716(JSONObject var1, classDA var2) throws JSONException {
      try {
         classMN var3 = new classMN(this);
         var3.field4542 = var2.method2871(new URL(var1.getString("src")), -720624244);
         var3.field4539 = classCL.method1574(var1, "placement", 2137692084);
         this.field4567.add(var3);
      } catch (MalformedURLException var4) {
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lms;Lorg/json/JSONObject;)V")
   @ObfuscatedName("hr")
   public static void method7723(classMS var0, JSONObject var1) throws JSONException {
      if (var0 == null) {
         var0.method7707();
      }

      if (null != var1) {
         var0.field4569 = classCL.method1574(var1, "clickbounds", 1802720126);
         var0.field4568 = var1.getString("endpoint");
         String[] var2 = JSONObject.getNames(var1);

         for (int var3 = 0; var3 < var1.length(); var3++) {
            if (!var2[var3].equals("clickbounds") && !var2[var3].equals("endpoint")) {
               try {
                  int var4 = var1.getInt(var2[var3]);
                  var0.field4573.add(new classMZ(var0, var2[var3], var4));
               } catch (Exception var7) {
                  try {
                     String var5 = var1.getString(var2[var3]);
                     if (var5.equals("true")) {
                        var0.field4573.add(new classMZ(var0, var2[var3], 1));
                     } else if (var5.equals("false")) {
                        var0.field4573.add(new classMZ(var0, var2[var3], 0));
                     } else {
                        var0.field4573.add(new classMP(var0, var2[var3], var5));
                     }
                  } catch (Exception var6) {
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;ILda;I)Z")
   @ObfuscatedName("as")
   boolean method7709(JSONObject var1, int var2, classDA var3, int var4) {
      try {
         JSONObject var5 = var1;
         if (var2 < 2) {
            if (var4 == 371320039) {
               throw new IllegalStateException();
            }

            try {
               var5 = var5.getJSONObject("message");
            } catch (Exception var14) {
               this.method7705(1257596365);
               return false;
            }

            try {
               this.method7710(var5.getJSONArray("images"), var3, 1160694231);
            } catch (Exception var13) {
               this.field4567.clear();
            }

            try {
               this.method7719(var5.getJSONArray("labels"), (byte)9);
            } catch (Exception var12) {
               this.field4566.clear();
            }
         } else {
            try {
               this.method7715(var5.getJSONObject("image"), var3, (byte)-88);
            } catch (Exception var11) {
               this.field4567.clear();
            }

            try {
               this.method7720(var5.getJSONObject("label"), 168120078);
            } catch (Exception var10) {
               this.field4566.clear();
            }
         }

         try {
            this.method7722(var5.getJSONObject("behaviour"), -490407247);
         } catch (Exception var9) {
            this.field4568 = null;
            this.field4569[0] = 0.0F;
            this.field4569[1] = 0.0F;
            this.field4569[2] = 1.0F;
            this.field4569[3] = 1.0F;
            this.field4573.clear();
         }

         try {
            JSONObject var6 = var5.optJSONObject("meta");
            if (var6 != null) {
               if (var4 == 371320039) {
                  throw new IllegalStateException();
               }

               method7724(this, var5.getJSONObject("meta"), 379372864);
            }
         } catch (Exception var8) {
            this.field4570.clear();
            this.field4571.clear();
         }

         if (2 == var2) {
            if (var4 == 371320039) {
               throw new IllegalStateException();
            }

            try {
               this.field4576 = var5.getString("id");
               this.field4572 = Integer.parseInt(var5.getString("priority")) * 1935874185;
            } catch (Exception var7) {
            }
         }

         return true;
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "ms.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lms;Lorg/json/JSONObject;I)V")
   @ObfuscatedName("nm")
   public static void method7724(classMS var0, JSONObject var1, int var2) throws JSONException {
      if (var0 == null) {
         var0.method7725(var1, var2);
      }

      try {
         String[] var3 = JSONObject.getNames(var1);

         for (int var4 = 0; var4 < var1.length(); var4++) {
            if (var2 >= 680806733) {
               throw new IllegalStateException();
            }

            try {
               int var5 = var1.optInt(var3[var4]);
               var0.field4570.put(var3[var4], var5);
            } catch (Exception var8) {
               try {
                  String var6 = var1.optString(var3[var4]);
                  if (var6.equals("true")) {
                     var0.field4570.put(var3[var4], 1);
                  } else if (var6.equals("false")) {
                     if (var2 >= 680806733) {
                        throw new IllegalStateException();
                     }

                     var0.field4570.put(var3[var4], 0);
                  } else {
                     var0.field4571.put(var3[var4], var6);
                  }
               } catch (Exception var7) {
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ms.aj(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("ar")
   static int method7699(String var0) {
      if (var0.equals("centre")) {
         return 1;
      } else {
         return !var0.equals("bottom") && !var0.equals("right") ? 0 : 2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONArray;Lda;)V")
   @ObfuscatedName("bc")
   void method7711(JSONArray var1, classDA var2) throws JSONException {
      if (var1 != null) {
         for (int var3 = 0; var3 < var1.length(); var3++) {
            try {
               JSONObject var4 = var1.getJSONObject(var3);
               classMN var5 = new classMN(this);
               var5.field4542 = var2.method2871(new URL(var4.getString("src")), -344605840);
               var5.field4539 = classCL.method1574(var4, "placement", 2134063842);
               this.field4567.add(var5);
            } catch (MalformedURLException var6) {
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONArray;Lda;)V")
   @ObfuscatedName("bn")
   void method7712(JSONArray var1, classDA var2) throws JSONException {
      if (var1 != null) {
         for (int var3 = 0; var3 < var1.length(); var3++) {
            try {
               JSONObject var4 = var1.getJSONObject(var3);
               classMN var5 = new classMN(this);
               var5.field4542 = var2.method2871(new URL(var4.getString("src")), 1405828777);
               var5.field4539 = classCL.method1574(var4, "placement", 1565801645);
               this.field4567.add(var5);
            } catch (MalformedURLException var6) {
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;Lda;)V")
   @ObfuscatedName("bp")
   void method7717(JSONObject var1, classDA var2) throws JSONException {
      try {
         classMN var3 = new classMN(this);
         var3.field4542 = var2.method2871(new URL(var1.getString("src")), 1520260868);
         var3.field4539 = classCL.method1574(var1, "placement", 1475730755);
         this.field4567.add(var3);
      } catch (MalformedURLException var4) {
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;Lda;)V")
   @ObfuscatedName("br")
   void method7718(JSONObject var1, classDA var2) throws JSONException {
      try {
         classMN var3 = new classMN(this);
         var3.field4542 = var2.method2871(new URL(var1.getString("src")), -147012574);
         var3.field4539 = classCL.method1574(var1, "placement", 1937140880);
         this.field4567.add(var3);
      } catch (MalformedURLException var4) {
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONArray;B)V")
   @ObfuscatedName("aa")
   void method7719(JSONArray var1, byte var2) throws JSONException {
      try {
         if (var1 == null) {
            if (var2 <= 0) {
               throw new IllegalStateException();
            }
         } else {
            for (int var3 = 0; var3 < var1.length(); var3++) {
               if (var2 <= 0) {
                  throw new IllegalStateException();
               }

               JSONObject var4 = var1.getJSONObject(var3);
               classML var5 = new classML(this);
               var5.field4530 = var4.getString("text");
               var5.field4521 = classDP.method3525(var4.getString("align_x"), 2146938382) * -1559171701;
               var5.field4524 = classDP.method3525(var4.getString("align_y"), 1962077229) * 1098088659;
               var5.field4522 = var4.getInt("font") * -1060228675;
               var5.field4523 = classCL.method1574(var4, "placement", 2023730283);
               this.field4566.add(var5);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ms.aa(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lms;Lorg/json/JSONObject;)V")
   @ObfuscatedName("qs")
   public static void method7721(classMS var0, JSONObject var1) throws JSONException {
      if (var0 == null) {
         var0.method7706();
      }

      classML var2 = new classML(var0);
      var2.field4530 = var1.optString("text");
      var2.field4521 = classDP.method3525(var1.optString("align_x"), 1965265562) * -1559171701;
      var2.field4524 = classDP.method3525(var1.optString("align_y"), -1328323843) * 1098088659;
      var2.field4522 = var1.optInt("font") * -1060228675;
      var2.field4523 = classCL.method1574(var1, "placement", 1980975034);
      var0.field4566.add(var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;Ljava/lang/String;)[F")
   @ObfuscatedName("aq")
   static float[] method7695(JSONObject var0, String var1) throws JSONException {
      float[] var2 = new float[4];

      try {
         JSONArray var3 = var0.getJSONArray(var1);
         var2[0] = (float)var3.optDouble(0, 0.0);
         var2[1] = (float)var3.optDouble(1, 0.0);
         var2[2] = (float)var3.optDouble(2, 1.0);
         var2[3] = (float)var3.optDouble(3, 1.0);
      } catch (JSONException var4) {
         var2[0] = 0.0F;
         var2[1] = 0.0F;
         var2[2] = 1.0F;
         var2[3] = 1.0F;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lva;Lva;II)Lfs;")
   @ObfuscatedName("az")
   public static classFS method7692(IndexDataBase var0, IndexDataBase var1, int var2, int var3) {
      try {
         boolean var4 = true;
         int var5 = -1;
         int[] var6 = var0.method11898(var2, -1652699151);

         for (int var7 = 0; var7 < var6.length; var7++) {
            byte[] var8 = var0.method11937(var2, var6[var7], 1959126511);
            if (var8 == null) {
               if (var3 >= -1290955240) {
                  throw new IllegalStateException();
               }

               var4 = false;
            } else if (var5 == -1) {
               if (var3 >= -1290955240) {
                  throw new IllegalStateException();
               }

               var5 = (var8[0] & 255) << 8 | var8[1] & 255;
            }
         }

         if (var5 != -1) {
            if (var3 >= -1290955240) {
               throw new IllegalStateException();
            }

            byte[] var11 = var1.method11937(var5, 0, -558073755);
            if (var11 == null) {
               if (var3 >= -1290955240) {
                  throw new IllegalStateException();
               }

               var4 = false;
            }
         } else {
            var4 = false;
         }

         if (var4) {
            try {
               return new classFS(var0, var1, var2);
            } catch (Exception var9) {
               return null;
            }
         } else if (var3 >= -1290955240) {
            throw new IllegalStateException();
         } else {
            return null;
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "ms.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lms;Lorg/json/JSONArray;Lda;)V")
   @ObfuscatedName("vm")
   public static void method7713(classMS var0, JSONArray var1, classDA var2) throws JSONException {
      if (var0 == null) {
         var0.method7714(var1, var2);
      } else if (var1 != null) {
         for (int var3 = 0; var3 < var1.length(); var3++) {
            try {
               JSONObject var4 = var1.getJSONObject(var3);
               classMN var5 = new classMN(var0);
               var5.field4542 = var2.method2871(new URL(var4.getString("src")), -1509955178);
               var5.field4539 = classCL.method1574(var4, "placement", 1590611687);
               var0.field4567.add(var5);
            } catch (MalformedURLException var6) {
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONArray;Lda;)V")
   @ObfuscatedName("bx")
   void method7714(JSONArray var1, classDA var2) {
      if (var1 != null) {
         for (int var3 = 0; var3 < var1.length(); var3++) {
            try {
               JSONObject var4 = var1.getJSONObject(var3);
               classMN var5 = new classMN(this);
               var5.field4542 = var2.method2871(new URL(var4.getString("src")), -344605840);
               var5.field4539 = classCL.method1574(var4, "placement", 2134063842);
               this.field4573.add(var5);
            } catch (MalformedURLException var6) {
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/json/JSONObject;I)V")
   @ObfuscatedName("aj")
   void method7725(JSONObject var1, int var2) {
      try {
         String[] var3 = JSONObject.getNames(var1);

         for (int var4 = 0; var4 < var1.length(); var4++) {
            if (var2 >= 680806733) {
               throw new IllegalStateException();
            }

            try {
               int var5 = var1.optInt(var3[var4]);
               this.field4570.put(var3[var4], var5);
            } catch (Exception var8) {
               try {
                  String var6 = var1.optString(var3[var4]);
                  if (var6.equals("true")) {
                     this.field4570.put(var3[var4], 1);
                  } else if (var6.equals("false")) {
                     if (var2 >= 680806733) {
                        throw new IllegalStateException();
                     }

                     this.field4571.put(var3[var4], 0);
                  } else {
                     this.field4571.put(var3[var4], var6);
                  }
               } catch (Exception var7) {
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "ms.aj(" + ')');
      }
   }
}
