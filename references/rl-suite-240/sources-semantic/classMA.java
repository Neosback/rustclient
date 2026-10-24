import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.json.JSONObject;

@ObfuscatedName("ma")
public class classMA {
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   static final String field4467 = "s";
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4465 = -1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field4460 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field4462 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field4464 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field4459 = 7;
   @ObfuscatedSignature(descriptor = "Lmu;")
   @ObfuscatedName("ao")
   classMU field4458;
   @ObfuscatedName("au")
   int field4451;
   @ObfuscatedName("aj")
   int field4455 = -2063965041;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4461 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   public static final int field4463 = 36;
   @ObfuscatedName("ay")
   String field4452;
   @ObfuscatedName("ad")
   String field4457;
   @ObfuscatedSignature(descriptor = "Lms;")
   @ObfuscatedName("al")
   classMS field4454;
   @ObfuscatedSignature(descriptor = "Ley;")
   @ObfuscatedName("ap")
   classEY field4456;
   @ToRemove(unused = "true")
   @ObfuscatedName("br")
   static final int field4466 = 5;
   @ObfuscatedName("aq")
   String field4453;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cs")
   void method7522() {
      this.field4456 = null;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;S)V")
   @ObfuscatedName("ae")
   public void method7476(String var1, String var2, String var3, short var4) {
      try {
         this.field4458 = classHS.method6136(-1819480864);
         this.field4457 = var1;
         this.field4458.method7752(this.field4457, var2, var3, -714017036);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ma.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lmu;")
   @ObfuscatedName("az")
   public classMU method7472(byte var1) {
      try {
         return this.field4458;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Lda;I)Z")
   @ObfuscatedName("af")
   public boolean method7474(String var1, String var2, classDA var3, int var4) {
      try {
         if (null != var1) {
            if (var4 <= -748802209) {
               throw new IllegalStateException();
            }

            if (!var1.isEmpty()) {
               if (var3 == null) {
                  if (var4 <= -748802209) {
                     throw new IllegalStateException();
                  }

                  return false;
               }

               method7523(this, 830641263);

               try {
                  this.field4453 = var1;
                  this.field4456 = var3.method2871(new URL(this.field4453), -1550682573);
                  this.field4455 = 0;
               } catch (MalformedURLException var6) {
                  method7523(this, 830641263);
                  this.field4455 = -334074428;
                  return false;
               }

               if (!var2.isEmpty()) {
                  if (var4 <= -748802209) {
                     throw new IllegalStateException();
                  }

                  this.field4452 = var2;
               }

               return true;
            }

            if (var4 <= -748802209) {
               throw new IllegalStateException();
            }
         }

         return false;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ma.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lda;)V")
   @ObfuscatedName("at")
   public void method7478(classDA var1) {
      switch (this.field4455 * 1284902940) {
         case 0:
            this.method7528(var1, 244281169);
            break;
         case 1:
            this.method7533((byte)-76);
            break;
         default:
            return;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("av")
   public void method7477(String var1, String var2, String var3) {
      this.field4458 = classHS.method6136(103280021);
      this.field4457 = var1;
      this.field4458.method7752(this.field4457, var2, var3, -1354653624);
   }

   @ObfuscatedSignature(descriptor = "(Lda;)V")
   @ObfuscatedName("ca")
   void method7527(classDA var1) {
      if (this.field4456 != null && this.field4456.method4136(2143399039)) {
         byte[] var2 = this.field4456.method4141(1145062404);
         if (var2 == null) {
            method7523(this, 830641263);
            this.field4455 = -334074428;
         } else {
            try {
               classWE var3 = new classWE(var2);
               JSONObject var4 = var3.method12421(-1454626184);
               if (var4 == null) {
                  return;
               }

               try {
                  this.field4451 = var4.getInt("version") * 2102857035;
               } catch (Exception var7) {
                  method7523(this, 830641263);
                  this.field4455 = -501111642;
                  return;
               }

               if (-77327261 * this.field4451 < 2) {
                  if (!this.field4454.method7709(var4, this.field4451 * -77327261, var1, -871865115)) {
                     this.field4455 = -501111642;
                  }
               } else if (this.field4451 * -77327261 == 2) {
                  classMI var5 = classMI.field4507;
                  var5.method7617(var4, -77327261 * this.field4451, var1, (byte)62);
                  this.field4454 = var5.method7631(this.field4452, 1875992713);
                  if (this.field4454 != null) {
                     this.method7533((byte)43);
                     this.field4455 = 2063965041;
                  } else {
                     this.field4455 = 1562853399;
                  }
               }
            } catch (UnsupportedEncodingException var8) {
               method7523(this, 830641263);
               this.field4455 = -501111642;
               return;
            }

            if (this.field4454 != null) {
               this.field4455 = (!this.field4454.field4567.isEmpty() ? 1 : 2) * 2063965041;
            }

            this.field4456 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("as")
   public boolean method7484(byte var1) {
      try {
         boolean var10000;
         if (this.field4454 != null) {
            if (var1 != 0) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;S)I")
   @ObfuscatedName("ax")
   public int method7487(String var1, short var2) {
      try {
         int var10000;
         if (this.field4454.field4570.containsKey(var1)) {
            if (var2 >= 376) {
               throw new IllegalStateException();
            }

            var10000 = (Integer)this.field4454.field4570.get(var1);
         } else {
            var10000 = -1;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ma.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)Ljava/lang/String;")
   @ObfuscatedName("ac")
   public String method7493(String var1, byte var2) {
      try {
         Object var10000;
         if (this.field4454.field4571.containsKey(var1)) {
            if (var2 == 6) {
               throw new IllegalStateException();
            }

            var10000 = this.field4454.field4571.get(var1);
         } else {
            var10000 = null;
         }

         return (String)var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ma.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Ljava/util/ArrayList;")
   @ObfuscatedName("aa")
   public ArrayList method7496(short var1) {
      try {
         return this.field4454.field4567;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/util/ArrayList;")
   @ObfuscatedName("al")
   public ArrayList method7503(byte var1) {
      try {
         return this.field4454.field4573;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("aj")
   public String method7506(int var1) {
      try {
         return this.field4454.field4568;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("ay")
   public float[] method7512(int var1) {
      try {
         return this.field4454.field4569;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lda;I)V")
   @ObfuscatedName("ap")
   void method7528(classDA var1, int var2) {
      try {
         if (this.field4456 != null) {
            if (var2 != 244281169) {
               return;
            }

            if (this.field4456.method4136(2112237512)) {
               byte[] var3 = this.field4456.method4141(1145062404);
               if (var3 == null) {
                  if (var2 != 244281169) {
                     throw new IllegalStateException();
                  }

                  method7523(this, 830641263);
                  this.field4455 = -334074428;
                  return;
               }

               try {
                  classWE var4 = new classWE(var3);
                  JSONObject var5 = var4.method12421(-1229780595);
                  if (var5 == null) {
                     return;
                  }

                  try {
                     this.field4451 = var5.getInt("version") * 2102857035;
                  } catch (Exception var8) {
                     method7523(this, 830641263);
                     this.field4455 = -501111642;
                     return;
                  }

                  if (-77327261 * this.field4451 < 2) {
                     if (var2 != 244281169) {
                        throw new IllegalStateException();
                     }

                     if (!this.field4454.method7709(var5, this.field4451 * -77327261, var1, -1765815068)) {
                        if (var2 != 244281169) {
                           throw new IllegalStateException();
                        }

                        this.field4455 = -501111642;
                     }
                  } else if (this.field4451 * -77327261 == 2) {
                     classMI var6 = classMI.field4507;
                     var6.method7617(var5, -77327261 * this.field4451, var1, (byte)62);
                     this.field4454 = var6.method7631(this.field4452, 2120152379);
                     if (this.field4454 != null) {
                        if (var2 != 244281169) {
                           return;
                        }

                        this.method7533((byte)-85);
                        this.field4455 = 2063965041;
                     } else {
                        this.field4455 = 1562853399;
                     }
                  }
               } catch (UnsupportedEncodingException var9) {
                  method7523(this, 830641263);
                  this.field4455 = -501111642;
                  return;
               }

               if (this.field4454 != null) {
                  byte var10001;
                  if (!this.field4454.field4567.isEmpty()) {
                     if (var2 != 244281169) {
                        return;
                     }

                     var10001 = 1;
                  } else {
                     var10001 = 2;
                  }

                  this.field4455 = var10001 * 2063965041;
               }

               this.field4456 = null;
               return;
            }

            if (var2 != 244281169) {
               throw new IllegalStateException();
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "ma.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("bv")
   public float[] method7513() {
      return this.field4454.field4569;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lda;I)Z")
   @ObfuscatedName("ai")
   public boolean method7537(String var1, classDA var2, int var3) {
      try {
         try {
            JSONObject var4 = new classWE(var1.getBytes()).method12421(-1045745323);

            try {
               this.field4451 = var4.getInt("version") * 2102857035;
            } catch (Exception var6) {
               method7523(this, 830641263);
               this.field4455 = -501111642;
               return false;
            }

            if (!this.field4454.method7709(var4, this.field4451 * -77327261, var2, 1889800462)) {
               if (var3 <= 992027348) {
                  throw new IllegalStateException();
               }

               this.field4455 = -501111642;
            }

            byte var10001;
            if (!this.field4454.field4567.isEmpty()) {
               if (var3 <= 992027348) {
                  throw new IllegalStateException();
               }

               var10001 = 1;
            } else {
               var10001 = 2;
            }

            this.field4455 = var10001 * 2063965041;
         } catch (UnsupportedEncodingException var7) {
            this.field4455 = -501111642;
         }

         boolean var10000;
         if (2013644177 * this.field4455 < 3) {
            if (var3 <= 992027348) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ma.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Lda;)Z")
   @ObfuscatedName("ak")
   public boolean method7475(String var1, String var2, classDA var3) {
      if (null == var1 || var1.isEmpty()) {
         return false;
      } else if (var3 == null) {
         return false;
      } else {
         method7523(this, 830641263);

         try {
            this.field4453 = var1;
            this.field4456 = var3.method2871(new URL(this.field4453), -437927472);
            this.field4455 = 0;
         } catch (MalformedURLException var5) {
            method7523(this, 830641263);
            this.field4455 = -334074428;
            return false;
         }

         if (!var2.isEmpty()) {
            this.field4452 = var2;
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/ArrayList;")
   @ObfuscatedName("bw")
   public ArrayList method7499() {
      return this.field4454.field4566;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)Ljava/lang/String;")
   @ObfuscatedName("jz")
   public static String method7516(classMA var0) {
      return var0.field4454.method7701(-2132132729);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/ArrayList;")
   @ObfuscatedName("by")
   public ArrayList method7504() {
      return this.field4454.field4573;
   }

   @ObfuscatedSignature(descriptor = "(Lda;)V")
   @ObfuscatedName("an")
   public void method7479(classDA var1) {
      switch (this.field4455 * 2013644177) {
         case 0:
            this.method7528(var1, 244281169);
            break;
         case 1:
            this.method7533((byte)43);
            break;
         default:
            return;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("au")
   void method7533(byte var1) {
      try {
         Iterator var2 = this.field4454.field4567.iterator();

         while (var2.hasNext()) {
            if (var1 == 0) {
               throw new IllegalStateException();
            }

            classMN var3 = (classMN)var2.next();
            if (var3.field4542 != null && !var3.field4542.method4136(2112828241)) {
               return;
            }
         }

         var2 = this.field4454.field4567.iterator();

         while (var2.hasNext()) {
            if (var1 == 0) {
               return;
            }

            classMN var7 = (classMN)var2.next();
            if (var7.field4542 != null) {
               byte[] var4 = var7.field4542.method4141(1145062404);
               if (var4 != null && var4.length > 0) {
                  if (var1 == 0) {
                     return;
                  }

                  this.field4455 = -167037214;
                  return;
               }
            }
         }

         method7523(this, 830641263);
         this.field4455 = 1729890613;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ma.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   public int method7481() {
      return this.field4455 * 50507538;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bx")
   public boolean method7485() {
      return this.field4454 != null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;Ljava/lang/String;)I")
   @ObfuscatedName("nc")
   public static int method7488(classMA var0, String var1) {
      return var0.field4454.field4570.containsKey(var1) ? (Integer)var0.field4454.field4570.get(var1) : -1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bc")
   public boolean method7486() {
      return this.field4454 != null;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("bt")
   public int method7489(String var1) {
      return this.field4454.field4570.containsKey(var1) ? (Integer)this.field4454.field4570.get(var1) : -1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("bp")
   public int method7490(String var1) {
      return this.field4454.field4570.containsKey(var1) ? (Integer)this.field4454.field4570.get(var1) : -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)V")
   @ObfuscatedName("ty")
   public static void method7534(classMA var0) {
      if (var0 == null) {
         var0.method7510();
      }

      for (classMN var2 : var0.field4454.field4567) {
         if (var2.field4542 != null && !var2.field4542.method4136(2114548647)) {
            return;
         }
      }

      for (classMN var5 : var0.field4454.field4567) {
         if (var5.field4542 != null) {
            byte[] var3 = var5.field4542.method4141(1145062404);
            if (var3 != null && var3.length > 0) {
               var0.field4455 = -167037214;
               return;
            }
         }
      }

      method7523(var0, 830641263);
      var0.field4455 = 1729890613;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/ArrayList;")
   @ObfuscatedName("be")
   public ArrayList method7505() {
      return this.field4454.field4573;
   }

   public classMA(classMA var1) {
      this.field4451 = 0;
      if (var1 != null) {
         this.field4458 = var1.field4458;
         this.field4454 = var1.field4454;
         this.field4455 = var1.field4455 * 1;
         this.field4453 = var1.field4453;
         this.field4457 = var1.field4457;
         this.field4452 = var1.field4452;
         this.field4456 = null;
         this.field4451 = 1 * var1.field4451;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lma;I)V")
   @ObfuscatedName("hb")
   public static void method7523(classMA var0, int var1) {
      if (var0 == null) {
         var0.method7526(var1);
      } else {
         try {
            var0.field4456 = null;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "ma.ad(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bk")
   public String method7494(String var1) {
      return (String)(this.field4454.field4571.containsKey(var1) ? this.field4454.field4571.get(var1) : null);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/ArrayList;")
   @ObfuscatedName("bb")
   public ArrayList method7500() {
      return this.field4454.field4566;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("br")
   public int method7491(String var1) {
      return this.field4454.field4570.containsKey(var1) ? (Integer)this.field4454.field4570.get(var1) : -1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/ArrayList;")
   @ObfuscatedName("ba")
   public ArrayList method7497() {
      return this.field4454.field4567;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bj")
   public String method7495(String var1) {
      return (String)(this.field4454.field4571.containsKey(var1) ? this.field4454.field4571.get(var1) : null);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ch")
   void method7535() {
      for (classMN var2 : this.field4454.field4567) {
         if (var2.field4542 != null && !var2.field4542.method4136(1885666989)) {
            return;
         }
      }

      for (classMN var5 : this.field4454.field4567) {
         if (var5.field4542 != null) {
            byte[] var3 = var5.field4542.method4141(1145062404);
            if (var3 != null && var3.length > 0) {
               this.field4455 = -167037214;
               return;
            }
         }
      }

      method7523(this, 830641263);
      this.field4455 = 1729890613;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bd")
   public String method7507() {
      return this.field4454.field4568;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bs")
   public String method7508() {
      return this.field4454.field4568;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)[F")
   @ObfuscatedName("gm")
   public static float[] method7514(classMA var0) {
      return var0.field4454.field4569;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public int method7482() {
      return this.field4455 * 2013644177;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   public int method7483(int var1) {
      try {
         return this.field4455 * 2013644177;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ct")
   public String method7509() {
      return this.field4454.field4568;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("cp")
   public String method7510() {
      return this.field4454.field4568;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bf")
   public String method7511() {
      return this.field4454.field4568;
   }

   public classMA() {
      this.field4451 = 0;
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("bz")
   public float[] method7515() {
      return this.field4454.field4569;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bq")
   public String method7517() {
      return this.field4454.method7701(-1297125300);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bl")
   public String method7518() {
      return this.field4454.method7701(-1431884642);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("cu")
   public String method7519() {
      return this.field4454.method7701(-1284145959);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("cr")
   public String method7520() {
      return this.field4454.method7701(-1796493913);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cd")
   void method7524() {
      this.field4456 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cv")
   void method7525() {
      this.field4456 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lda;)V")
   @ObfuscatedName("cy")
   void method7529(classDA var1) {
      if (this.field4456 != null && this.field4456.method4136(2029559026)) {
         byte[] var2 = this.field4456.method4141(1145062404);
         if (var2 == null) {
            method7523(this, 830641263);
            this.field4455 = -334074428;
         } else {
            try {
               classWE var3 = new classWE(var2);
               JSONObject var4 = var3.method12421(-1477810738);
               if (var4 == null) {
                  return;
               }

               try {
                  this.field4451 = var4.getInt("version") * 2102857035;
               } catch (Exception var7) {
                  method7523(this, 830641263);
                  this.field4455 = -501111642;
                  return;
               }

               if (-77327261 * this.field4451 < 2) {
                  if (!this.field4454.method7709(var4, this.field4451 * -77327261, var1, -882397869)) {
                     this.field4455 = -501111642;
                  }
               } else if (this.field4451 * -77327261 == 2) {
                  classMI var5 = classMI.field4507;
                  var5.method7617(var4, -77327261 * this.field4451, var1, (byte)62);
                  this.field4454 = var5.method7631(this.field4452, 2002298986);
                  if (this.field4454 != null) {
                     this.method7533((byte)-2);
                     this.field4455 = 2063965041;
                  } else {
                     this.field4455 = 1562853399;
                  }
               }
            } catch (UnsupportedEncodingException var8) {
               method7523(this, 830641263);
               this.field4455 = -501111642;
               return;
            }

            if (this.field4454 != null) {
               this.field4455 = (!this.field4454.field4567.isEmpty() ? 1 : 2) * 2063965041;
            }

            this.field4456 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lda;)V")
   @ObfuscatedName("co")
   void method7530(classDA var1) {
      if (this.field4456 != null && this.field4456.method4136(1946529288)) {
         byte[] var2 = this.field4456.method4141(1145062404);
         if (var2 == null) {
            method7523(this, 830641263);
            this.field4455 = -334074428;
         } else {
            try {
               classWE var3 = new classWE(var2);
               JSONObject var4 = var3.method12421(2115028351);
               if (var4 == null) {
                  return;
               }

               try {
                  this.field4451 = var4.getInt("version") * 2102857035;
               } catch (Exception var7) {
                  method7523(this, 830641263);
                  this.field4455 = 1127564862;
                  return;
               }

               if (-953926871 * this.field4451 < 2) {
                  if (!this.field4454.method7709(var4, this.field4451 * 1066612620, var1, -1192841249)) {
                     this.field4455 = -501111642;
                  }
               } else if (this.field4451 * 1877070884 == 2) {
                  classMI var5 = classMI.field4507;
                  var5.method7617(var4, -77327261 * this.field4451, var1, (byte)62);
                  this.field4454 = var5.method7631(this.field4452, 1816029823);
                  if (this.field4454 != null) {
                     this.method7533((byte)-92);
                     this.field4455 = 156629063;
                  } else {
                     this.field4455 = 873531110;
                  }
               }
            } catch (UnsupportedEncodingException var8) {
               method7523(this, 830641263);
               this.field4455 = -235323912;
               return;
            }

            if (this.field4454 != null) {
               this.field4455 = (!this.field4454.field4567.isEmpty() ? 1 : 2) * 879183050;
            }

            this.field4456 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lda;)V")
   @ObfuscatedName("ck")
   void method7531(classDA var1) {
      if (this.field4456 != null && this.field4456.method4136(1883882099)) {
         byte[] var2 = this.field4456.method4141(1145062404);
         if (var2 == null) {
            method7523(this, 830641263);
            this.field4455 = 1960821333;
         } else {
            try {
               classWE var3 = new classWE(var2);
               JSONObject var4 = var3.method12421(-409604338);
               if (var4 == null) {
                  return;
               }

               try {
                  this.field4451 = var4.getInt("version") * 2102857035;
               } catch (Exception var7) {
                  method7523(this, 830641263);
                  this.field4455 = -501111642;
                  return;
               }

               if (-77327261 * this.field4451 < 2) {
                  if (!this.field4454.method7709(var4, this.field4451 * -77327261, var1, 206530222)) {
                     this.field4455 = -1138037083;
                  }
               } else if (this.field4451 * -26250067 == 2) {
                  classMI var5 = classMI.field4507;
                  var5.method7617(var4, -77327261 * this.field4451, var1, (byte)62);
                  this.field4454 = var5.method7631(this.field4452, 1837565031);
                  if (this.field4454 != null) {
                     this.method7533((byte)44);
                     this.field4455 = -435892734;
                  } else {
                     this.field4455 = 1562853399;
                  }
               }
            } catch (UnsupportedEncodingException var8) {
               method7523(this, 830641263);
               this.field4455 = -910156673;
               return;
            }

            if (this.field4454 != null) {
               this.field4455 = (!this.field4454.field4567.isEmpty() ? 1 : 2) * 2063965041;
            }

            this.field4456 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lda;I)V")
   @ObfuscatedName("ab")
   public void method7480(classDA var1, int var2) {
      try {
         switch (this.field4455 * 2013644177) {
            case 0:
               this.method7528(var1, 244281169);
               break;
            case 1:
               this.method7533((byte)-66);
               break;
            default:
               return;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ma.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lda;)V")
   @ObfuscatedName("cc")
   void method7532(classDA var1) {
      if (this.field4456 != null && this.field4456.method4136(1918253488)) {
         byte[] var2 = this.field4456.method4141(1145062404);
         if (var2 == null) {
            method7523(this, 830641263);
            this.field4455 = -334074428;
         } else {
            try {
               classWE var3 = new classWE(var2);
               JSONObject var4 = var3.method12421(2025364238);
               if (var4 == null) {
                  return;
               }

               try {
                  this.field4451 = var4.getInt("version") * -1948279175;
               } catch (Exception var7) {
                  method7523(this, 830641263);
                  this.field4455 = 1624395695;
                  return;
               }

               if (-77327261 * this.field4451 < 2) {
                  if (!this.field4454.method7709(var4, this.field4451 * -77327261, var1, 544090393)) {
                     this.field4455 = -838783784;
                  }
               } else if (this.field4451 * 1986726248 == 2) {
                  classMI var5 = classMI.field4507;
                  var5.method7617(var4, -77327261 * this.field4451, var1, (byte)62);
                  this.field4454 = var5.method7631(this.field4452, 1856021970);
                  if (this.field4454 != null) {
                     this.method7533((byte)62);
                     this.field4455 = 2063965041;
                  } else {
                     this.field4455 = 1562853399;
                  }
               }
            } catch (UnsupportedEncodingException var8) {
               method7523(this, 830641263);
               this.field4455 = -501111642;
               return;
            }

            if (this.field4454 != null) {
               this.field4455 = (!this.field4454.field4567.isEmpty() ? 1 : 2) * 1839944167;
            }

            this.field4456 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/ArrayList;")
   @ObfuscatedName("bo")
   public ArrayList method7498() {
      return this.field4454.field4567;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("aq")
   public String method7521(int var1) {
      try {
         return this.field4454.method7701(-1747304644);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lda;)Z")
   @ObfuscatedName("cz")
   public boolean method7538(String var1, classDA var2) {
      try {
         JSONObject var3 = new classWE(var1.getBytes()).method12421(715370973);

         try {
            this.field4451 = var3.getInt("version") * 2102857035;
         } catch (Exception var5) {
            method7523(this, 830641263);
            this.field4455 = -501111642;
            return false;
         }

         if (!this.field4454.method7709(var3, this.field4451 * -77327261, var2, -825536582)) {
            this.field4455 = -501111642;
         }

         this.field4455 = (!this.field4454.field4567.isEmpty() ? 1 : 2) * 2063965041;
      } catch (UnsupportedEncodingException var6) {
         this.field4455 = -501111642;
      }

      return 2013644177 * this.field4455 < 3;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lda;)Z")
   @ObfuscatedName("cg")
   public boolean method7539(String var1, classDA var2) {
      try {
         JSONObject var3 = new classWE(var1.getBytes()).method12421(-1749028600);

         try {
            this.field4451 = var3.getInt("version") * 2102857035;
         } catch (Exception var5) {
            method7523(this, 830641263);
            this.field4455 = -501111642;
            return false;
         }

         if (!this.field4454.method7709(var3, this.field4451 * -77327261, var2, 424477085)) {
            this.field4455 = 502777749;
         }

         this.field4455 = (!this.field4454.field4567.isEmpty() ? 1 : 2) * -160518245;
      } catch (UnsupportedEncodingException var6) {
         this.field4455 = -655046227;
      }

      return 2013644177 * this.field4455 < 3;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lda;)Z")
   @ObfuscatedName("cw")
   public boolean method7540(String var1, classDA var2) {
      try {
         JSONObject var3 = new classWE(var1.getBytes()).method12421(1092751847);

         try {
            this.field4451 = var3.getInt("version") * -2058911701;
         } catch (Exception var5) {
            method7523(this, 830641263);
            this.field4455 = 1035077920;
            return false;
         }

         if (!this.field4454.method7709(var3, this.field4451 * -77327261, var2, 138812393)) {
            this.field4455 = -501111642;
         }

         this.field4455 = (!this.field4454.field4567.isEmpty() ? 1 : 2) * 2063965041;
      } catch (UnsupportedEncodingException var6) {
         this.field4455 = 654427332;
      }

      return 2013644177 * this.field4455 < 3;
   }

   @ObfuscatedSignature(descriptor = "(B)[Lkw;")
   @ObfuscatedName("ag")
   public static classKW[] method7471(byte var0) {
      try {
         return new classKW[]{classKW.field4037, classKW.field4041, classKW.field4039, classKW.field4038};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ma.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lma;I)Ljava/util/ArrayList;")
   @ObfuscatedName("kb")
   public static ArrayList method7501(classMA var0, int var1) {
      if (var0 == null) {
         var0.method7502(var1);
      }

      try {
         return var0.field4454.field4566;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;II)V")
   @ObfuscatedName("ag")
   static void method7541(classTE var0, int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 <= 404036101) {
               throw new IllegalStateException();
            }

            if (var1 < var0.method10797(-1919429894)) {
               return;
            }

            if (var2 <= 404036101) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException();
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ma.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/util/ArrayList;")
   @ObfuscatedName("ao")
   public ArrayList method7502(int var1) {
      try {
         return this.field4454.field4573;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.ao(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;Ljava/lang/String;)I")
   @ObfuscatedName("nd")
   public static int method7492(classMA var0, String var1) {
      return var0.field4454.field4570.containsKey(var1) ? (Integer)var0.field4454.field4570.get(var1) : -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)V")
   @ObfuscatedName("ox")
   public static void method7536(classMA var0) {
      for (classMN var2 : var0.field4454.field4567) {
         if (var2.field4542 != null && !var2.field4542.method4136(2137417646)) {
            return;
         }
      }

      for (classMN var5 : var0.field4454.field4567) {
         if (var5.field4542 != null) {
            byte[] var3 = var5.field4542.method4141(1145062404);
            if (var3 != null && var3.length > 0) {
               var0.field4455 = -167037214;
               return;
            }
         }
      }

      method7523(var0, 830641263);
      var0.field4455 = 1729890613;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ad")
   void method7526(int var1) {
      try {
         this.field4456 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ma.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lmu;")
   @ObfuscatedName("ar")
   public classMU method7473() {
      return this.field4458;
   }
}
