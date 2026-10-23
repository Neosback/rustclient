import java.io.UnsupportedEncodingException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@ObfuscatedName("we")
public class classWE implements classWG {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6673 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field6674 = 42;
   @ObfuscatedName("af")
   JSONObject field6672;

   @ObfuscatedSignature(descriptor = "(B)Lwy;")
   @ObfuscatedName("az")
   @Override
   public classWY vmethod631(byte var1) {
      try {
         return classWY.field6789;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "we.az(" + ')');
      }
   }

   public classWE(String var1) throws UnsupportedEncodingException {
      this.method12417(var1, -1204201464);
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("aq")
   void method12412(byte[] var1) throws UnsupportedEncodingException {
      String var2 = new String(var1, "UTF-8");
      this.method12417(var2, -1204201464);
   }

   @ObfuscatedSignature(descriptor = "(I)Lorg/json/JSONObject;")
   @ObfuscatedName("al")
   public JSONObject method12421(int var1) {
      try {
         return this.field6672;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "we.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("ao")
   void method12417(String var1, int var2) throws UnsupportedEncodingException {
      try {
         try {
            if (var1.charAt(0) == '{') {
               if (var2 != -1204201464) {
                  return;
               }

               this.field6672 = new JSONObject(var1);
            } else {
               if (var1.charAt(0) != '[') {
                  throw new UnsupportedEncodingException("Invalid JSON passed to the JSON content builder.");
               }

               if (var2 != -1204201464) {
                  return;
               }

               JSONArray var3 = new JSONArray(var1);
               this.field6672 = new JSONObject();
               this.field6672.put("arrayValues", var3);
            }
         } catch (JSONException var4) {
            throw new UnsupportedEncodingException(var4.getMessage());
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "we.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwe;[BI)V")
   @ObfuscatedName("xc")
   public static void method12413(classWE var0, byte[] var1, int var2) throws UnsupportedEncodingException {
      if (var0 == null) {
         var0.method12416(var1, var2);
      }

      try {
         String var3 = new String(var1, "UTF-8");
         var0.method12417(var3, -1204201464);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "we.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ag")
   @Override
   public byte[] vmethod634() throws UnsupportedEncodingException {
      return null == this.field6672 ? new byte[0] : this.field6672.toString().getBytes("UTF-8");
   }

   @ObfuscatedSignature(descriptor = "()Lwy;")
   @ObfuscatedName("ab")
   @Override
   public classWY vmethod632() {
      return classWY.field6789;
   }

   @ObfuscatedSignature(descriptor = "()Lwy;")
   @ObfuscatedName("ae")
   @Override
   public classWY vmethod633() {
      return classWY.field6789;
   }

   public classWE(JSONObject var1) {
      this.field6672 = var1;
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("as")
   @Override
   public byte[] vmethod635() throws UnsupportedEncodingException {
      return null == this.field6672 ? new byte[0] : this.field6672.toString().getBytes("UTF-8");
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ax")
   @Override
   public byte[] vmethod636() throws UnsupportedEncodingException {
      return null == this.field6672 ? new byte[0] : this.field6672.toString().getBytes("UTF-8");
   }

   public classWE(byte[] var1) throws UnsupportedEncodingException {
      method12413(this, var1, 617089741);
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("aj")
   void method12414(byte[] var1) throws UnsupportedEncodingException {
      String var2 = new String(var1, "UTF-8");
      this.method12417(var2, -1204201464);
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("ay")
   void method12415(byte[] var1) throws UnsupportedEncodingException {
      String var2 = new String(var1, "UTF-8");
      this.method12417(var2, -1204201464);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;I)V")
   @ObfuscatedName("vf")
   public static void method12425(Model var0, int var1) {
      if (var0 == null) {
         var0.getFaceRenderPriorities();
      }

      int var2 = classFH.method4725();
      int var3 = classFH.method4730();
      int var4 = 0;
      int var5 = var0.field2427[var1];
      int var6 = var0.field2430[var1];
      int var7 = var0.field2431[var1];
      int var8 = Model.field2474[var5];
      int var9 = Model.field2474[var6];
      int var10 = Model.field2474[var7];
      if (var8 >= 50) {
         Model.field2476[var4] = (int)Model.field2464[var5];
         Model.field2477[var4] = (int)Model.field2450[var5];
         Model.field2452[var4] = Model.field2448[var5];
         Model.field2480[var4++] = var0.field2432[var1];
      } else {
         int var11 = Model.field2468[var5];
         int var12 = Model.field2451[var5];
         int var13 = var0.field2432[var1];
         if (var10 >= 50) {
            int var14 = (50 - var8) * Model.field2467[var10 - var8];
            Model.field2476[var4] = var2 + (var11 + ((Model.field2468[var7] - var11) * var14 >> 16)) * classFH.method4747() / 50;
            Model.field2477[var4] = var3 + (var12 + ((Model.field2451[var7] - var12) * var14 >> 16)) * classFH.method4747() / 50;
            Model.field2452[var4] = 50.0F;
            Model.field2480[var4++] = var13 + ((var0.field2433[var1] - var13) * var14 >> 16);
         }

         if (var9 >= 50) {
            int var35 = (50 - var8) * Model.field2467[var9 - var8];
            Model.field2476[var4] = var2 + (var11 + ((Model.field2468[var6] - var11) * var35 >> 16)) * classFH.method4747() / 50;
            Model.field2477[var4] = var3 + (var12 + ((Model.field2451[var6] - var12) * var35 >> 16)) * classFH.method4747() / 50;
            Model.field2452[var4] = 50.0F;
            Model.field2480[var4++] = var13 + ((var0.field2426[var1] - var13) * var35 >> 16);
         }
      }

      if (var9 >= 50) {
         Model.field2476[var4] = (int)Model.field2464[var6];
         Model.field2477[var4] = (int)Model.field2450[var6];
         Model.field2452[var4] = Model.field2448[var6];
         Model.field2480[var4++] = var0.field2426[var1];
      } else {
         int var26 = Model.field2468[var6];
         int var29 = Model.field2451[var6];
         int var32 = var0.field2426[var1];
         if (var8 >= 50) {
            int var36 = (50 - var9) * Model.field2467[var8 - var9];
            Model.field2476[var4] = var2 + (var26 + ((Model.field2468[var5] - var26) * var36 >> 16)) * classFH.method4747() / 50;
            Model.field2477[var4] = var3 + (var29 + ((Model.field2451[var5] - var29) * var36 >> 16)) * classFH.method4747() / 50;
            Model.field2452[var4] = 50.0F;
            Model.field2480[var4++] = var32 + ((var0.field2432[var1] - var32) * var36 >> 16);
         }

         if (var10 >= 50) {
            int var37 = (50 - var9) * Model.field2467[var10 - var9];
            Model.field2476[var4] = var2 + (var26 + ((Model.field2468[var7] - var26) * var37 >> 16)) * classFH.method4747() / 50;
            Model.field2477[var4] = var3 + (var29 + ((Model.field2451[var7] - var29) * var37 >> 16)) * classFH.method4747() / 50;
            Model.field2452[var4] = 50.0F;
            Model.field2480[var4++] = var32 + ((var0.field2433[var1] - var32) * var37 >> 16);
         }
      }

      if (var10 >= 50) {
         Model.field2476[var4] = (int)Model.field2464[var7];
         Model.field2477[var4] = (int)Model.field2450[var7];
         Model.field2452[var4] = Model.field2448[var7];
         Model.field2480[var4++] = var0.field2433[var1];
      } else {
         int var27 = Model.field2468[var7];
         int var30 = Model.field2451[var7];
         int var33 = var0.field2433[var1];
         if (var9 >= 50) {
            int var38 = (50 - var10) * Model.field2467[var9 - var10];
            Model.field2476[var4] = var2 + (var27 + ((Model.field2468[var6] - var27) * var38 >> 16)) * classFH.method4747() / 50;
            Model.field2477[var4] = var3 + (var30 + ((Model.field2451[var6] - var30) * var38 >> 16)) * classFH.method4747() / 50;
            Model.field2452[var4] = 50.0F;
            Model.field2480[var4++] = var33 + ((var0.field2426[var1] - var33) * var38 >> 16);
         }

         if (var8 >= 50) {
            int var39 = (50 - var10) * Model.field2467[var8 - var10];
            Model.field2476[var4] = var2 + (var27 + ((Model.field2468[var5] - var27) * var39 >> 16)) * classFH.method4747() / 50;
            Model.field2477[var4] = var3 + (var30 + ((Model.field2451[var5] - var30) * var39 >> 16)) * classFH.method4747() / 50;
            Model.field2452[var4] = 50.0F;
            Model.field2480[var4++] = var33 + ((var0.field2432[var1] - var33) * var39 >> 16);
         }
      }

      int var28 = Model.field2476[0];
      int var31 = Model.field2476[1];
      int var34 = Model.field2476[2];
      int var40 = Model.field2477[0];
      int var15 = Model.field2477[1];
      int var16 = Model.field2477[2];
      int var17 = var0.field2436 == null ? 0 : var0.field2436[var1] * 2;
      float var18 = classJE.method6398(Model.field2452[0] - var17, 2105000881);
      float var19 = classJE.method6398(Model.field2452[1] - var17, 2108969058);
      float var20 = classJE.method6398(Model.field2452[2] - var17, 2135725121);
      classFH.field2255.field2209 = false;
      int var21 = classFH.method4751();
      if (var4 == 3) {
         if (var28 < 0 || var31 < 0 || var34 < 0 || var28 > var21 || var31 > var21 || var34 > var21) {
            classFH.field2255.field2209 = true;
         }

         Model.method5221(var0, var1, var40, var15, var16, var28, var31, var34, var18, var19, var20, Model.field2480[0], Model.field2480[1], Model.field2480[2]);
      }

      if (var4 == 4) {
         if (var28 < 0 || var31 < 0 || var34 < 0 || var28 > var21 || var31 > var21 || var34 > var21 || Model.field2476[3] < 0 || Model.field2476[3] > var21) {
            classFH.field2255.field2209 = true;
         }

         if (var0.field2435 != null && var0.field2435[var1] != -1) {
            int var24;
            int var41;
            int var43;
            if (var0.field2434 != null && var0.field2434[var1] != -1) {
               int var25 = var0.field2434[var1] & 255;
               var41 = var0.field2437[var25];
               var43 = var0.field2438[var25];
               var24 = var0.field2439[var25];
            } else {
               var41 = var5;
               var43 = var6;
               var24 = var7;
            }

            short var44 = var0.field2435[var1];
            if (var0.field2433[var1] == -1) {
               classFH.method4787(
                  var40,
                  var15,
                  var16,
                  var28,
                  var31,
                  var34,
                  var18,
                  var19,
                  var20,
                  var0.field2432[var1],
                  var0.field2432[var1],
                  var0.field2432[var1],
                  Model.field2468[var41],
                  Model.field2468[var43],
                  Model.field2468[var24],
                  Model.field2451[var41],
                  Model.field2451[var43],
                  Model.field2451[var24],
                  Model.field2474[var41],
                  Model.field2474[var43],
                  Model.field2474[var24],
                  var44
               );
               classFH.method4787(
                  var40,
                  var16,
                  Model.field2477[3],
                  var28,
                  var34,
                  Model.field2476[3],
                  var18,
                  var20,
                  Model.field2452[3],
                  var0.field2432[var1],
                  var0.field2432[var1],
                  var0.field2432[var1],
                  Model.field2468[var41],
                  Model.field2468[var43],
                  Model.field2468[var24],
                  Model.field2451[var41],
                  Model.field2451[var43],
                  Model.field2451[var24],
                  Model.field2474[var41],
                  Model.field2474[var43],
                  Model.field2474[var24],
                  var44
               );
            } else {
               classFH.method4787(
                  var40,
                  var15,
                  var16,
                  var28,
                  var31,
                  var34,
                  var18,
                  var19,
                  var20,
                  Model.field2480[0],
                  Model.field2480[1],
                  Model.field2480[2],
                  Model.field2468[var41],
                  Model.field2468[var43],
                  Model.field2468[var24],
                  Model.field2451[var41],
                  Model.field2451[var43],
                  Model.field2451[var24],
                  Model.field2474[var41],
                  Model.field2474[var43],
                  Model.field2474[var24],
                  var44
               );
               classFH.method4787(
                  var40,
                  var16,
                  Model.field2477[3],
                  var28,
                  var34,
                  Model.field2476[3],
                  var18,
                  var20,
                  Model.field2452[3],
                  Model.field2480[0],
                  Model.field2480[2],
                  Model.field2480[3],
                  Model.field2468[var41],
                  Model.field2468[var43],
                  Model.field2468[var24],
                  Model.field2451[var41],
                  Model.field2451[var43],
                  Model.field2451[var24],
                  Model.field2474[var41],
                  Model.field2474[var43],
                  Model.field2474[var24],
                  var44
               );
            }
         } else {
            boolean var22 = var0.method5219(var1);
            if (var0.field2433[var1] == -1 && var22) {
               int var42 = Model.field2460[var0.field2432[var1]];
               classFH.method4793(var40, var15, var16, var28, var31, var34, var18, var19, var20, var42);
               classFH.method4793(var40, var16, Model.field2477[3], var28, var34, Model.field2476[3], var18, var20, Model.field2452[3], var42);
            } else if (var0.field2433[var1] == -1) {
               int var23 = Model.field2460[var0.field2432[var1]];
               classFH.method4782(var40, var15, var16, var28, var31, var34, var18, var19, var20, var23);
               classFH.method4782(var40, var16, Model.field2477[3], var28, var34, Model.field2476[3], var18, var20, Model.field2452[3], var23);
            } else if (var22) {
               classFH.method4792(var40, var15, var16, var28, var31, var34, var18, var19, var20, Model.field2480[0], Model.field2480[1], Model.field2480[2]);
               classFH.method4792(
                  var40,
                  var16,
                  Model.field2477[3],
                  var28,
                  var34,
                  Model.field2476[3],
                  var18,
                  var20,
                  Model.field2452[3],
                  Model.field2480[0],
                  Model.field2480[2],
                  Model.field2480[3]
               );
            } else {
               classFH.method4778(var40, var15, var16, var28, var31, var34, var18, var19, var20, Model.field2480[0], Model.field2480[1], Model.field2480[2]);
               classFH.method4778(
                  var40,
                  var16,
                  Model.field2477[3],
                  var28,
                  var34,
                  Model.field2476[3],
                  var18,
                  var20,
                  Model.field2452[3],
                  Model.field2480[0],
                  Model.field2480[2],
                  Model.field2480[3]
               );
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ad")
   void method12418(String var1) throws UnsupportedEncodingException {
      try {
         if (var1.charAt(0) == '{') {
            this.field6672 = new JSONObject(var1);
         } else {
            if (var1.charAt(0) != '[') {
               throw new UnsupportedEncodingException("Invalid JSON passed to the JSON content builder.");
            }

            JSONArray var2 = new JSONArray(var1);
            this.field6672 = new JSONObject();
            this.field6672.put("arrayValues", var2);
         }
      } catch (JSONException var3) {
         throw new UnsupportedEncodingException(var3.getMessage());
      }
   }

   @ObfuscatedSignature(descriptor = "()Lorg/json/JSONObject;")
   @ObfuscatedName("ar")
   public JSONObject method12422() {
      return this.field6672;
   }

   @ObfuscatedSignature(descriptor = "(I)[B")
   @ObfuscatedName("af")
   @Override
   public byte[] vmethod637(int var1) throws UnsupportedEncodingException {
      try {
         if (null == this.field6672) {
            if (var1 != -1390912683) {
               throw new IllegalStateException();
            } else {
               return new byte[0];
            }
         } else {
            return this.field6672.toString().getBytes("UTF-8");
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "we.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lorg/json/JSONObject;")
   @ObfuscatedName("au")
   public JSONObject method12423() {
      return this.field6672;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwe;Ljava/lang/String;)V")
   @ObfuscatedName("xb")
   public static void method12419(classWE var0, String var1) throws UnsupportedEncodingException {
      if (var0 == null) {
         var0.method12420(var1);
      }

      try {
         if (var1.charAt(0) == '{') {
            var0.field6672 = new JSONObject(var1);
         } else {
            if (var1.charAt(0) != '[') {
               throw new UnsupportedEncodingException("Invalid JSON passed to the JSON content builder.");
            }

            JSONArray var2 = new JSONArray(var1);
            var0.field6672 = new JSONObject();
            var0.field6672.put("arrayValues", var2);
         }
      } catch (JSONException var3) {
         throw new UnsupportedEncodingException(var3.getMessage());
      }
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ac")
   @Override
   public byte[] vmethod638() throws UnsupportedEncodingException {
      return null == this.field6672 ? new byte[0] : this.field6672.toString().getBytes("UTF-8");
   }

   @ObfuscatedSignature(descriptor = "([BI)V")
   @ObfuscatedName("aa")
   void method12416(byte[] var1, int var2) {
      try {
         String var3 = new String(var1, "UTF-8");
         this.method12417(var3, -1204201464);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "we.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ap")
   void method12420(String var1) {
      try {
         if (var1.charAt(0) == '{') {
            this.field6672 = new JSONObject(var1);
         } else {
            if (var1.charAt(0) != '[') {
               throw new UnsupportedEncodingException("Invalid JSON passed to the JSON content builder.");
            }

            JSONArray var2 = new JSONArray(var1);
            this.field6672 = new JSONObject();
            this.field6672.put("arrayValues", var2);
         }
      } catch (JSONException var3) {
         throw new UnsupportedEncodingException(var3.getMessage());
      }
   }

   @ObfuscatedSignature(descriptor = "()Lorg/json/JSONObject;")
   @ObfuscatedName("ai")
   public JSONObject method12424() {
      return this.field6672;
   }
}
