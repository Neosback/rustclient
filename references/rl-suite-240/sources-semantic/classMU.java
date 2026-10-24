import java.io.IOException;
import java.net.URL;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.json.JSONObject;

@ObfuscatedName("mu")
public class classMU {
   @ObfuscatedName("al")
   String field4590;
   @ObfuscatedName("aa")
   String field4594;
   @ObfuscatedName("ao")
   String field4589;
   @ObfuscatedSignature(descriptor = "Lmu;")
   @ObfuscatedName("ag")
   static final classMU field4585 = new classMU();
   @ObfuscatedName("as")
   final int field4593 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4597 = 0;
   @ObfuscatedName("ac")
   String field4588;
   @ObfuscatedName("aj")
   long field4591;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4596 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field4595 = 3;
   @ObfuscatedName("ax")
   final int field4587 = 2;
   @ObfuscatedSignature(descriptor = "Lqd;")
   @ObfuscatedName("ay")
   classQD field4599;
   @ObfuscatedSignature(descriptor = "Lqm;")
   @ObfuscatedName("aq")
   classQM field4600;
   @ObfuscatedName("ad")
   int field4592;
   @ObfuscatedName("ap")
   int field4586;
   @ObfuscatedName("au")
   String field4598;
   @ObfuscatedName("ai")
   boolean field4601;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lqm;")
   @ObfuscatedName("bt")
   classQM method7767(String var1) throws IOException {
      URL var2 = new URL(var1);
      classQA var3 = new classQA(var2, classQQ.field5585, this.field4601);

      try {
         JSONObject var4 = new JSONObject();
         classQA.method9461(var3, new classWE(var4), 303547196);
      } catch (Exception var5) {
      }

      return this.field4599.method9537(var3, -639997526);
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;B)V")
   @ObfuscatedName("af")
   public void method7741(int var1, String var2, byte var3) {
      try {
         this.field4586 = var1 * 2100917943;
         this.field4598 = var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mu.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("ae")
   public void method7745(boolean var1, int var2) {
      try {
         this.field4601 = var1;
         this.field4599 = new classQD("crmsession", 1, 1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mu.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Z")
   @ObfuscatedName("br")
   public boolean method7763(String var1) {
      this.method7741(2, var1, (byte)10);
      return true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   public void method7759() {
      if (this.field4600 != null && this.field4600.method9690((byte)-4)) {
         if (this.field4600.method9690((byte)-51) && classQM.method9696(this.field4600, 1325078424).method9557(-1146707731) == 200 && this.field4590.isEmpty()) {
            String var1 = classQM.method9696(this.field4600, -1466561486).method9569(318046972);
            if (var1.isEmpty()) {
               return;
            }

            this.field4590 = var1;
         }

         if (0 != 1065830151 * this.field4586) {
            this.method7756(1508679445);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ac")
   public void method7754(byte var1) {
      try {
         if (this.field4599 != null) {
            if (var1 == 3) {
               throw new IllegalStateException();
            }

            classQD.method9538(this.field4599, (byte)64);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mu.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   void method7756(int var1) {
      try {
         if (this.field4590.isEmpty()) {
            if (var1 == -890289400) {
               throw new IllegalStateException();
            }
         } else {
            String var2 = "";
            switch (1065830151 * this.field4586) {
               case 1:
                  var2 = "events/click";
                  break;
               case 2:
                  var2 = "events/dismissed";
                  break;
               case 3:
                  var2 = "events/impression";
            }

            if (!this.field4598.isEmpty()) {
               String var3 = this.field4588;
               var3 = var3 + var2 + "/" + this.field4594 + "/" + this.field4590 + "/" + this.field4598 + "?userHash=" + this.field4589;

               try {
                  this.field4600 = this.method7768(var3, (byte)20);
               } catch (IOException var5) {
                  this.field4592 = -616310729;
               }

               this.field4592 = -616310729;
               this.field4586 = 0;
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "mu.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Z")
   @ObfuscatedName("aa")
   public boolean method7764(String var1, int var2) {
      try {
         this.method7741(2, var1, (byte)10);
         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mu.aa(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lmu;")
   @ObfuscatedName("aq")
   public static classMU method7737() {
      return field4585;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lmu;")
   @ObfuscatedName("al")
   public static classMU method7738() {
      return field4585;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmu;)V")
   @ObfuscatedName("fi")
   public static void method7760(classMU var0) {
      if (var0 == null) {
         var0.method7759();
      }

      if (var0.field4600 != null && var0.field4600.method9690((byte)21)) {
         if (var0.field4600.method9690((byte)-7)
            && classQM.method9696(var0.field4600, 1209421855).method9557(-1146707731) == 2067719950
            && var0.field4590.isEmpty()) {
            String var1 = classQM.method9696(var0.field4600, -587445950).method9569(-365523304);
            if (var1.isEmpty()) {
               return;
            }

            var0.field4590 = var1;
         }

         if (0 != -1302058492 * var0.field4586) {
            var0.method7756(-1606908458);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lmu;")
   @ObfuscatedName("aj")
   public static classMU method7739() {
      return field4585;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmu;ILjava/lang/String;)V")
   @ObfuscatedName("hb")
   public static void method7742(classMU var0, int var1, String var2) {
      if (var0 == null) {
         var0.method7763(var2);
      }

      var0.field4586 = var1 * 2100917943;
      var0.field4598 = var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lmu;")
   @ObfuscatedName("ay")
   public static classMU method7740() {
      return field4585;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   public void method7761() {
      if (this.field4600 != null && this.field4600.method9690((byte)-10)) {
         if (this.field4600.method9690((byte)-38) && classQM.method9696(this.field4600, -1494242309).method9557(-1146707731) == 200 && this.field4590.isEmpty()
            )
          {
            String var1 = classQM.method9696(this.field4600, 1210808534).method9569(-425277087);
            if (var1.isEmpty()) {
               return;
            }

            this.field4590 = var1;
         }

         if (0 != 1065830151 * this.field4586) {
            this.method7756(-1226087021);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("ak")
   void method7749(String var1, String var2, String var3) {
      if (-1019519609 * this.field4592 != 2) {
         this.field4588 = var1;
         this.field4594 = var2;
         this.field4589 = var3;
         if (!this.field4588.endsWith("/")) {
            this.field4588 = this.field4588 + "/";
         }

         if (!this.field4594.equals("")) {
            String var4 = this.field4588;
            var4 = var4 + "session/open/" + this.field4594;
            if (this.field4589.equals("")) {
            }

            var4 = var4 + "?userHash=" + this.field4589;

            try {
               this.field4600 = this.method7768(var4, (byte)121);
               this.field4591 = classDD.method2988(16711680) * -7959198196523491225L;
            } catch (IOException var6) {
               this.field4600 = null;
               this.field4594 = "";
               this.field4589 = "";
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ar")
   public void method7746(boolean var1) {
      this.field4601 = var1;
      this.field4599 = new classQD("crmsession", 1, 1);
   }

   classMU() {
      this.field4588 = "";
      this.field4594 = "";
      this.field4589 = "";
      this.field4590 = "";
      this.field4591 = 7959198196523491225L;
      this.field4592 = -616310729;
      this.field4586 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("av")
   void method7750(String var1, String var2, String var3) {
      if (-1019519609 * this.field4592 != 2) {
         this.field4588 = var1;
         this.field4594 = var2;
         this.field4589 = var3;
         if (!this.field4588.endsWith("/")) {
            this.field4588 = this.field4588 + "/";
         }

         if (!this.field4594.equals("")) {
            String var4 = this.field4588;
            var4 = var4 + "session/open/" + this.field4594;
            if (this.field4589.equals("")) {
            }

            var4 = var4 + "?userHash=" + this.field4589;

            try {
               this.field4600 = this.method7768(var4, (byte)88);
               this.field4591 = classDD.method2988(16711680) * -7959198196523491225L;
            } catch (IOException var6) {
               this.field4600 = null;
               this.field4594 = "";
               this.field4589 = "";
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)Lqm;")
   @ObfuscatedName("ao")
   classQM method7768(String var1, byte var2) throws IOException {
      try {
         URL var3 = new URL(var1);
         classQA var4 = new classQA(var3, classQQ.field5585, this.field4601);

         try {
            JSONObject var5 = new JSONObject();
            classQA.method9461(var4, new classWE(var5), -1687985292);
         } catch (Exception var6) {
         }

         return this.field4599.method9537(var4, -2142603026);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "mu.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   public void method7753(int var1) {
      try {
         if (null == this.field4600) {
            if (!this.field4590.isEmpty()) {
               if (var1 == 1333907154) {
                  return;
               }

               if (1 == this.field4592 * -1019519609) {
                  long var2 = classDD.method2988(16711680);
                  long var4 = var2 - -7570363771333412009L * this.field4591;
                  String var6 = this.field4588;
                  var6 = var6 + "session/close/" + this.field4594 + "/" + this.field4590;
                  if (this.field4589.isEmpty()) {
                     if (var1 == 1333907154) {
                        throw new IllegalStateException();
                     }

                     var6 = var6 + "?sessionDuration=" + var4;
                  } else {
                     var6 = var6 + "?userHash=" + this.field4589 + "&sessionDuration=" + var4;
                  }

                  try {
                     this.field4600 = this.method7768(var6, (byte)118);
                  } catch (IOException var8) {
                     this.field4592 = -616310729;
                  }
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "mu.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   void method7757() {
      if (!this.field4590.isEmpty()) {
         String var1 = "";
         switch (1065830151 * this.field4586) {
            case 1:
               var1 = "events/click";
               break;
            case 2:
               var1 = "events/dismissed";
               break;
            case 3:
               var1 = "events/impression";
         }

         if (!this.field4598.isEmpty()) {
            String var2 = this.field4588;
            var2 = var2 + var1 + "/" + this.field4594 + "/" + this.field4590 + "/" + this.field4598 + "?userHash=" + this.field4589;

            try {
               this.field4600 = this.method7768(var2, (byte)59);
            } catch (IOException var4) {
               this.field4592 = -616310729;
            }

            this.field4592 = -616310729;
            this.field4586 = 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ai")
   public void method7747(boolean var1) {
      this.field4601 = var1;
      this.field4599 = new classQD("crmsession", 1, 1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lqm;")
   @ObfuscatedName("bg")
   classQM method7769(String var1) throws IOException {
      URL var2 = new URL(var1);
      classQA var3 = new classQA(var2, classQQ.field5585, this.field4601);

      try {
         JSONObject var4 = new JSONObject();
         classQA.method9461(var3, new classWE(var4), -1619499864);
      } catch (Exception var5) {
      }

      return this.field4599.method9537(var3, -845223471);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmu;Z)V")
   @ObfuscatedName("ez")
   public static void method7748(classMU var0, boolean var1) {
      if (var0 == null) {
         var0.method7758();
      }

      var0.field4601 = var1;
      var0.field4599 = new classQD("crmsession", 1, 1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmu;ILjava/lang/String;)V")
   @ObfuscatedName("zb")
   public static void method7743(classMU var0, int var1, String var2) {
      if (var0 == null) {
         var0.method7763(var2);
      }

      var0.field4586 = var1 * 2100917943;
      var0.field4598 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Z")
   @ObfuscatedName("bp")
   public boolean method7765(String var1) {
      this.method7741(2, var1, (byte)10);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Z")
   @ObfuscatedName("bc")
   public boolean method7766(String var1) {
      this.method7741(2, var1, (byte)10);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("at")
   void method7751(String var1, String var2, String var3) {
      if (-1019519609 * this.field4592 != 2) {
         this.field4588 = var1;
         this.field4594 = var2;
         this.field4589 = var3;
         if (!this.field4588.endsWith("/")) {
            this.field4588 = this.field4588 + "/";
         }

         if (!this.field4594.equals("")) {
            String var4 = this.field4588;
            var4 = var4 + "session/open/" + this.field4594;
            if (this.field4589.equals("")) {
            }

            var4 = var4 + "?userHash=" + this.field4589;

            try {
               this.field4600 = this.method7768(var4, (byte)75);
               this.field4591 = classDD.method2988(16711680) * -7959198196523491225L;
            } catch (IOException var6) {
               this.field4600 = null;
               this.field4594 = "";
               this.field4589 = "";
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmu;ILjava/lang/String;)V")
   @ObfuscatedName("gm")
   public static void method7744(classMU var0, int var1, String var2) {
      if (var0 == null) {
         var0.method7767(var2);
      }

      var0.field4586 = var1 * -690347324;
      var0.field4598 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lqm;")
   @ObfuscatedName("bm")
   classQM method7770(String var1) throws IOException {
      URL var2 = new URL(var1);
      classQA var3 = new classQA(var2, classQQ.field5585, this.field4601);

      try {
         JSONObject var4 = new JSONObject();
         classQA.method9461(var3, new classWE(var4), -1356055602);
      } catch (Exception var5) {
      }

      return this.field4599.method9537(var3, -429891255);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   void method7758() {
      if (!this.field4590.isEmpty()) {
         String var1 = "";
         switch (1065830151 * this.field4586) {
            case 1:
               var1 = "events/click";
               break;
            case 2:
               var1 = "events/dismissed";
               break;
            case 3:
               var1 = "events/impression";
         }

         if (!this.field4598.isEmpty()) {
            String var2 = this.field4588;
            var2 = var2 + var1 + "/" + this.field4594 + "/" + this.field4590 + "/" + this.field4598 + "?userHash=" + this.field4589;

            try {
               this.field4600 = this.method7768(var2, (byte)61);
            } catch (IOException var4) {
               this.field4592 = -616310729;
            }

            this.field4592 = -616310729;
            this.field4586 = 0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V")
   @ObfuscatedName("ab")
   void method7752(String var1, String var2, String var3, int var4) {
      try {
         if (-1019519609 * this.field4592 == 2) {
            if (var4 == 1872477243) {
               throw new IllegalStateException();
            }
         } else {
            this.field4588 = var1;
            this.field4594 = var2;
            this.field4589 = var3;
            if (!this.field4588.endsWith("/")) {
               if (var4 == 1872477243) {
                  throw new IllegalStateException();
               }

               this.field4588 = this.field4588 + "/";
            }

            if (this.field4594.equals("")) {
               if (var4 == 1872477243) {
                  throw new IllegalStateException();
               }
            } else {
               String var5 = this.field4588;
               var5 = var5 + "session/open/" + this.field4594;
               if (!this.field4589.equals("") || var4 != 1872477243) {
                  var5 = var5 + "?userHash=" + this.field4589;

                  try {
                     this.field4600 = this.method7768(var5, (byte)118);
                     this.field4591 = classDD.method2988(16711680) * -7959198196523491225L;
                  } catch (IOException var7) {
                     this.field4600 = null;
                     this.field4594 = "";
                     this.field4589 = "";
                  }
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "mu.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lom;")
   @ObfuscatedName("ae")
   public static ObjectComposition method7771(int var0, int var1) {
      synchronized (ObjectComposition.field4964) {
         int var4 = var0;
         int var5 = 1883040848;

         ObjectComposition var10000;
         try {
            ObjectComposition var6 = (ObjectComposition)NodeCache.method6460(ObjectComposition.field4964, var4);
            if (null != var6) {
               if (var5 <= 1234964183) {
                  throw new IllegalStateException();
               }

               var10000 = var6;
            } else {
               byte[] var7 = classAK.field213.method11942(6, var4, -1962631827);
               var6 = new ObjectComposition(new classXY(var7), var4, true);
               ObjectComposition.field4964.method6464(var6, var4);
               var10000 = var6;
            }
         } catch (RuntimeException var8) {
            throw classEG.method3899(var8, "mu.ae(" + ')');
         }

         return var10000;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lmu;B)V")
   @ObfuscatedName("fr")
   public static void method7762(classMU var0, byte var1) {
      if (var0 == null) {
         var0.method7755(var1);
      } else {
         try {
            if (var0.field4600 != null) {
               if (var1 <= 1) {
                  return;
               }

               if (var0.field4600.method9690((byte)26)) {
                  if (var0.field4600.method9690((byte)-50) && classQM.method9696(var0.field4600, -326757245).method9557(-1146707731) == 200) {
                     if (var1 <= 1) {
                        return;
                     }

                     if (var0.field4590.isEmpty()) {
                        if (var1 <= 1) {
                           throw new IllegalStateException();
                        }

                        String var2 = classQM.method9696(var0.field4600, 75473486).method9569(-1249299537);
                        if (var2.isEmpty()) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           return;
                        }

                        var0.field4590 = var2;
                     }
                  }

                  if (0 != 1065830151 * var0.field4586) {
                     if (var1 <= 1) {
                        return;
                     }

                     var0.method7756(-1701150183);
                  }

                  return;
               }

               if (var1 <= 1) {
                  throw new IllegalStateException();
               }
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "mu.ac(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("as")
   public void method7755(byte var1) {
      try {
         if (this.field4599 != null) {
            if (var1 == 3) {
               throw new IllegalStateException();
            }

            classQD.method9538(this.field4599, (byte)64);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mu.as(" + ')');
      }
   }
}
