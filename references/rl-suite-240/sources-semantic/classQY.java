import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qy")
public class classQY {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final String field5626 = "Accept";
   @ObfuscatedName("as")
   final DecimalFormat field5624;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final String field5627 = "Content-Type";
   @ObfuscatedName("ag")
   final Map field5622;
   @ObfuscatedName("ab")
   final Map field5623 = new HashMap();
   @ToRemove(unused = "true")
   @ObfuscatedName("bk")
   public static final int field5625 = 72;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqy;Lwy;F)V")
   @ObfuscatedName("qr")
   public static void method9910(classQY var0, classWY var1, float var2) {
      var0.field5622.put(var1, Math.max(0.0F, Math.min(1.0F, var2)));
      var0.method9914(-1828246286);
   }

   @ObfuscatedSignature(descriptor = "(Ljavax/net/ssl/HttpsURLConnection;B)V")
   @ObfuscatedName("az")
   public void method9882(HttpsURLConnection var1, byte var2) {
      try {
         Iterator var3 = this.field5623.entrySet().iterator();

         while (var3.hasNext()) {
            if (var2 != 0) {
               throw new IllegalStateException();
            }

            Entry var4 = (Entry)var3.next();
            var1.setRequestProperty((String)var4.getKey(), (String)var4.getValue());
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "qy.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqy;Ljava/lang/String;)V")
   @ObfuscatedName("rx")
   public static void method9891(classQY var0, String var1) {
      if (var0 == null) {
         var0.method9903(var1);
      }

      if (var1 != null && !var1.isEmpty()) {
         var0.field5623.remove(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/util/Map;")
   @ObfuscatedName("af")
   public Map method9886(byte var1) {
      try {
         return this.field5623;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qy.af(" + ')');
      }
   }

   public classQY() {
      this.field5622 = new HashMap();
      this.field5624 = new DecimalFormat();
      this.field5624.setMaximumFractionDigits(2);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Map;")
   @ObfuscatedName("ap")
   public Map method9887() {
      return this.field5623;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqy;Lqb;Ljava/lang/String;)V")
   @ObfuscatedName("yi")
   public static void method9893(classQY var0, classQB var1, String var2) {
      if (var0 == null) {
         var0.method9889(var2, var2);
      }

      String var3 = String.format("%s %s", classQB.method9478(var1, -1995884178), var2);
      method9888(var0, "Authorization", var3, (short)10575);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("as")
   public void method9901(String var1, byte var2) {
      try {
         method9896(this, classQB.field5510, var1, -1638196283);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qy.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwy;B)V")
   @ObfuscatedName("ac")
   public void method9904(classWY var1, byte var2) {
      try {
         this.field5623.put("Content-Type", var1.method12703(-1999936381));
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qy.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Ljava/lang/String;")
   @ObfuscatedName("af")
   public static String method9885(int var0, byte var1) {
      try {
         return "<col=" + Integer.toHexString(var0) + ">";
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qy.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwy;I)V")
   @ObfuscatedName("ao")
   public void method9908(classWY var1, int var2) {
      try {
         this.method9911(var1, 1.0F, 2016173534);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qy.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwy;FI)V")
   @ObfuscatedName("al")
   void method9911(classWY var1, float var2, int var3) {
      try {
         this.field5622.put(var1, Math.max(0.0F, Math.min(1.0F, var2)));
         this.method9914(-1828246286);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qy.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqy;Ljava/lang/String;Ljava/lang/String;S)V")
   @ObfuscatedName("fm")
   public static void method9888(classQY var0, String var1, String var2, short var3) {
      if (var0 == null) {
         var0.method9890(var1, var1, var3);
      }

      try {
         if (null != var1) {
            if (var3 <= 151) {
               throw new IllegalStateException();
            }

            if (!var1.isEmpty()) {
               if (var3 <= 151) {
                  throw new IllegalStateException();
               }

               String var10002;
               if (null != var2) {
                  if (var3 <= 151) {
                     return;
                  }

                  var10002 = var2;
               } else {
                  var10002 = "";
               }

               var0.field5623.put(var1, var10002);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qy.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aj")
   void method9914(int var1) {
      try {
         this.field5623.remove("Accept");
         if (!this.field5622.isEmpty()) {
            this.field5623.put("Accept", this.method9918(-1722587460));
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qy.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ay")
   String method9918(int var1) {
      try {
         ArrayList var2 = new ArrayList(this.field5622.entrySet());
         Collections.sort(var2, new classQX(this));
         StringBuilder var3 = new StringBuilder();
         Iterator var4 = var2.iterator();

         while (var4.hasNext()) {
            if (var1 >= 1179829004) {
               throw new IllegalStateException();
            }

            Entry var5 = (Entry)var4.next();
            if (var3.length() > 0) {
               if (var1 >= 1179829004) {
                  throw new IllegalStateException();
               }

               var3.append(",");
            }

            var3.append(((classWY)var5.getKey()).method12703(841183347));
            float var6 = (Float)var5.getValue();
            if (var6 < 1.0F) {
               if (var1 >= 1179829004) {
                  throw new IllegalStateException();
               }

               String var7 = this.field5624.format(var6);
               var3.append(";q=").append(var7);
            }
         }

         return var3.toString();
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "qy.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("av")
   public void method9898(String var1) {
      method9896(this, classQB.field5509, var1, 12461873);
   }

   @ObfuscatedSignature(descriptor = "(Ljavax/net/ssl/HttpsURLConnection;)V")
   @ObfuscatedName("ad")
   public void method9883(HttpsURLConnection var1) {
      for (Entry var3 : this.field5623.entrySet()) {
         var1.setRequestProperty((String)var3.getKey(), (String)var3.getValue());
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("ab")
   public void method9892(String var1, int var2) {
      try {
         if (var1 != null) {
            if (var2 <= -1190919909) {
               throw new IllegalStateException();
            }

            if (!var1.isEmpty()) {
               if (var2 <= -1190919909) {
                  return;
               }

               this.field5623.remove(var1);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qy.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("au")
   public void method9889(String var1, String var2) {
      if (null != var1 && !var1.isEmpty()) {
         this.field5623.put(var1, null != var2 ? var2 : "");
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwy;F)V")
   @ObfuscatedName("bm")
   void method9912(classWY var1, float var2) {
      this.field5622.put(var1, Math.max(0.0F, Math.min(1.0F, var2)));
      this.method9914(-1828246286);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("ax")
   public void method9899(String var1, byte var2) {
      try {
         method9896(this, classQB.field5509, var1, -38291990);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qy.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqb;Ljava/lang/String;)V")
   @ObfuscatedName("aw")
   void method9894(classQB var1, String var2) {
      String var3 = String.format("%s %s", classQB.method9478(var1, -1995884178), var2);
      method9888(this, "Authorization", var3, (short)14833);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ak")
   public void method9900(String var1) {
      method9896(this, classQB.field5509, var1, 1409233758);
   }

   @ObfuscatedSignature(descriptor = "(Ljavax/net/ssl/HttpsURLConnection;)V")
   @ObfuscatedName("aq")
   public void method9884(HttpsURLConnection var1) {
      for (Entry var3 : this.field5623.entrySet()) {
         var1.setRequestProperty((String)var3.getKey(), (String)var3.getValue());
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("at")
   public void method9902(String var1) {
      method9896(this, classQB.field5510, var1, -690167923);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("an")
   public void method9903(String var1) {
      method9896(this, classQB.field5510, var1, -798980611);
   }

   @ObfuscatedSignature(descriptor = "(Lwy;)V")
   @ObfuscatedName("bx")
   public void method9905(classWY var1) {
      this.field5623.put("Content-Type", var1.method12703(-449720529));
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   public void method9906() {
      this.field5623.remove("Content-Type");
   }

   @ObfuscatedSignature(descriptor = "(Lwy;)V")
   @ObfuscatedName("am")
   public void method9909(classWY var1) {
      this.method9911(var1, 1.0F, 1956766109);
   }

   @ObfuscatedSignature(descriptor = "(Lwy;F)V")
   @ObfuscatedName("br")
   void method9913(classWY var1, float var2) {
      this.field5622.put(var1, Math.max(0.0F, Math.min(1.0F, var2)));
      this.method9914(-1828246286);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   public void method9907(int var1) {
      try {
         this.field5623.remove("Content-Type");
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qy.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bt")
   void method9915() {
      this.field5623.remove("Accept");
      if (!this.field5622.isEmpty()) {
         this.field5623.put("Accept", this.method9918(-833882570));
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   void method9916() {
      this.field5623.remove("Accept");
      if (!this.field5622.isEmpty()) {
         this.field5623.put("Accept", this.method9918(-1470800084));
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bj")
   void method9917() {
      this.field5623.remove("Accept");
      if (!this.field5622.isEmpty()) {
         this.field5623.put("Accept", this.method9918(-920972436));
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bk")
   String method9919() {
      ArrayList var1 = new ArrayList(this.field5622.entrySet());
      Collections.sort(var1, new classQX(this));
      StringBuilder var2 = new StringBuilder();

      for (Entry var4 : var1) {
         if (var2.length() > 0) {
            var2.append(",");
         }

         var2.append(((classWY)var4.getKey()).method12703(158812179));
         float var5 = (Float)var4.getValue();
         if (var5 < 1.0F) {
            String var6 = this.field5624.format(var5);
            var2.append(";q=").append(var6);
         }
      }

      return var2.toString();
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   static void method9920(int var0) {
      try {
         if (classDQ.field1602.toLowerCase().indexOf("microsoft") != -1) {
            if (var0 >= -1257745409) {
               throw new IllegalStateException();
            }

            classTK.field6147[186] = 57;
            classTK.field6147[187] = 27;
            classTK.field6147[188] = 71;
            classTK.field6147[189] = 26;
            classTK.field6147[190] = 72;
            classTK.field6147[191] = 73;
            classTK.field6147[192] = 58;
            classTK.field6147[219] = 42;
            classTK.field6147[220] = 74;
            classTK.field6147[221] = 43;
            classTK.field6147[222] = 59;
            classTK.field6147[223] = 28;
         } else {
            classTK.field6147[44] = 71;
            classTK.field6147[45] = 26;
            classTK.field6147[46] = 72;
            classTK.field6147[47] = 73;
            classTK.field6147[59] = 57;
            classTK.field6147[61] = 27;
            classTK.field6147[91] = 42;
            classTK.field6147[92] = 74;
            classTK.field6147[93] = 43;
            classTK.field6147[192] = 28;
            classTK.field6147[222] = 58;
            classTK.field6147[520] = 59;
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qy.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqb;Ljava/lang/String;I)V")
   @ObfuscatedName("ag")
   void method9895(classQB var1, String var2, int var3) {
      try {
         String var4 = String.format("%s %s", classQB.method9478(var1, -1995884178), var2);
         method9888(this, "Authorization", var4, (short)23632);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "qy.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lef;)[Lsg;")
   @ObfuscatedName("fr")
   public static classSG[] method9897(classEF var0) {
      if (var0 == null) {
         var0.method3885();
      }

      return var0.field1743;
   }

   @ObfuscatedSignature(descriptor = "(Lqy;Lqb;Ljava/lang/String;I)V")
   @ObfuscatedName("ut")
   public static void method9896(classQY var0, classQB var1, String var2, int var3) {
      if (var0 == null) {
         var0.method9895(var1, var2, var3);
      } else {
         try {
            String var4 = String.format("%s %s", classQB.method9478(var1, -1995884178), var2);
            method9888(var0, "Authorization", var4, (short)23632);
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "qy.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;S)V")
   @ObfuscatedName("ae")
   public void method9890(String var1, String var2, short var3) {
      try {
         if (null != var1) {
            if (var3 <= 151) {
               throw new IllegalStateException();
            }

            if (!var1.isEmpty()) {
               if (var3 <= 151) {
                  throw new IllegalStateException();
               }

               String var10002;
               if (null != var2) {
                  if (var3 <= 151) {
                     return;
                  }

                  var10002 = var2;
               } else {
                  var10002 = "";
               }

               this.field5622.put(var1, var10002);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qy.ae(" + ')');
      }
   }
}
