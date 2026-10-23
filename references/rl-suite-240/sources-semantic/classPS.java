import net.runelite.api.worldmap.MapElementConfig;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ps")
public class classPS extends classVJ implements MapElementConfig {
   @ObfuscatedName("at")
   int[] field5414;
   @ObfuscatedName("af")
   public static int field5405;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ae")
   static NodeCache field5404 = new NodeCache(256);
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ab")
   public static IndexDataBase field5411;
   @ObfuscatedName("as")
   public final int field5403;
   @ObfuscatedName("ao")
   public int field5393 = -776403169;
   @ObfuscatedName("ac")
   int field5391 = 998477221;
   @ObfuscatedName("ap")
   int[] field5413;
   @ObfuscatedName("am")
   public int field5406;
   @ToRemove(unused = "true")
   @ObfuscatedName("br")
   static final int field5408 = 33;
   @ObfuscatedName("aj")
   public boolean field5390;
   @ObfuscatedName("ay")
   public boolean field5397;
   @ObfuscatedSignature(descriptor = "Lol;")
   @ObfuscatedName("av")
   public classOL field5395;
   @ObfuscatedName("ad")
   public String field5410;
   @ObfuscatedName("aw")
   int field5399;
   @ObfuscatedName("ax")
   public int field5392 = 0;
   @ObfuscatedName("ai")
   int field5401;
   @ObfuscatedName("ar")
   int field5396;
   @ObfuscatedName("au")
   int field5394;
   @ObfuscatedSignature(descriptor = "Lpe;")
   @ObfuscatedName("ak")
   public classPE field5400;
   @ObfuscatedName("al")
   public int field5402;
   @ObfuscatedName("aq")
   public String[] field5398;
   @ObfuscatedName("an")
   byte[] field5412;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5407 = 0;
   @ObfuscatedName("aa")
   public String field5409;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public static void method9224() {
      field5404.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lps;")
   @ObfuscatedName("aa")
   public static classPS method9222(int var0) {
      return var0 >= 0 && var0 < classYV.field7093.length && classYV.field7093[var0] != null ? classYV.field7093[var0] : new classPS(var0);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lps;)V")
   @ObfuscatedName("bw")
   public static void method9231(classPS var0) {
      if (null != var0.field5413) {
         for (byte var1 = 0; var1 < var0.field5413.length; var1 += 2) {
            if (var0.field5413[var1] < var0.field5394 * -1409238271) {
               var0.field5394 = var0.field5413[var1] * 429409537;
            } else if (var0.field5413[var1] > var0.field5396 * 723964761) {
               var0.field5396 = var0.field5413[var1] * -1560918807;
            }

            if (var0.field5413[1 + var1] < var0.field5401 * -1912538141) {
               var0.field5401 = 1145053643 * var0.field5413[var1 + 1];
            } else if (var0.field5413[1 + var1] > 814813303 * var0.field5399) {
               var0.field5399 = var0.field5413[var1 + 1] * -1314797241;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)V")
   @ObfuscatedName("ab")
   void method9229(classXY var1, int var2, int var3) {
      try {
         if (1 == var2) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            this.field5393 = classXY.method13169(var1, -324749371) * 776403169;
         } else if (2 == var2) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            this.field5391 = classXY.method13169(var1, -324749371) * -998477221;
         } else if (var2 == 3) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            this.field5409 = var1.method13127(-337135206);
         } else if (4 == var2) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            this.field5406 = var1.method13105(-758448158) * 1409547555;
         } else if (var2 == 5) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            var1.method13105(-758448158);
         } else if (6 == var2) {
            this.field5392 = var1.method13095(-346779531) * -17682209;
         } else if (var2 == 7) {
            int var4 = var1.method13095(-346779531);
            if (0 == (var4 & 1)) {
               this.field5390 = false;
            }

            if (2 == (var4 & 2)) {
               if (var3 >= -1241739344) {
                  throw new IllegalStateException();
               }

               this.field5397 = true;
            }
         } else if (8 == var2) {
            var1.method13095(-346779531);
         } else if (var2 >= 10 && var2 <= 14) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            this.field5398[var2 - 10] = var1.method13127(-973105624);
         } else if (var2 == 15) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            int var8 = var1.method13095(-346779531);
            this.field5413 = new int[2 * var8];

            for (int var5 = 0; var5 < 2 * var8; var5++) {
               if (var3 >= -1241739344) {
                  return;
               }

               this.field5413[var5] = var1.method13104((byte)16);
            }

            classXY.method13113(var1, (byte)1);
            int var10 = var1.method13095(-346779531);
            this.field5414 = new int[var10];

            for (int var6 = 0; var6 < this.field5414.length; var6++) {
               if (var3 >= -1241739344) {
                  throw new IllegalStateException();
               }

               this.field5414[var6] = classXY.method13113(var1, (byte)1);
            }

            this.field5412 = new byte[var8];

            for (int var11 = 0; var11 < var8; var11++) {
               if (var3 >= -1241739344) {
                  return;
               }

               this.field5412[var11] = var1.method13097((byte)17);
            }
         } else if (var2 == 16) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }
         } else if (17 == var2) {
            this.field5410 = var1.method13127(207753851);
         } else if (var2 == 18) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            classXY.method13169(var1, -324749371);
         } else if (19 == var2) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            this.field5402 = var1.method13099(-696547813) * 384460919;
         } else if (var2 == 21) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            classXY.method13113(var1, (byte)1);
         } else if (var2 == 22) {
            if (var3 >= -1241739344) {
               return;
            }

            classXY.method13113(var1, (byte)1);
         } else if (23 == var2) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            var1.method13095(-346779531);
            var1.method13095(-346779531);
            var1.method13095(-346779531);
         } else if (var2 == 24) {
            if (var3 >= -1241739344) {
               return;
            }

            var1.method13104((byte)16);
            var1.method13104((byte)16);
         } else if (var2 == 25) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            classXY.method13169(var1, -324749371);
         } else if (28 == var2) {
            var1.method13095(-346779531);
         } else if (29 == var2) {
            if (var3 >= -1241739344) {
               throw new IllegalStateException();
            }

            this.field5400 = (classPE)classOF.method8436(classLP.method7185(874163108), var1.method13095(-346779531), -971717788);
         } else if (var2 == 30) {
            if (var3 >= -1241739344) {
               return;
            }

            classOL[] var9 = new classOL[]{classOL.field4913, classOL.field4912, classOL.field4914};
            this.field5395 = (classOL)classOF.method8436(var9, var1.method13095(-346779531), 414905435);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ps.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   public void method9232(int var1) {
      try {
         if (null != this.field5413) {
            if (var1 == -2009876679) {
               throw new IllegalStateException();
            }

            for (byte var2 = 0; var2 < this.field5413.length; var2 += 2) {
               if (var1 == -2009876679) {
                  throw new IllegalStateException();
               }

               if (this.field5413[var2] < this.field5394 * -1409238271) {
                  if (var1 == -2009876679) {
                     throw new IllegalStateException();
                  }

                  this.field5394 = this.field5413[var2] * 429409537;
               } else if (this.field5413[var2] > this.field5396 * 723964761) {
                  this.field5396 = this.field5413[var2] * -1560918807;
               }

               if (this.field5413[1 + var2] < this.field5401 * -1912538141) {
                  this.field5401 = 1145053643 * this.field5413[var2 + 1];
               } else if (this.field5413[1 + var2] > 814813303 * this.field5399) {
                  if (var1 == -2009876679) {
                     throw new IllegalStateException();
                  }

                  this.field5399 = this.field5413[var2 + 1] * -1314797241;
               }
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ps.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Lym;")
   @ObfuscatedName("za")
   public SpritePixels method9245(boolean var1) {
      return this.method9236(var1, 592907760);
   }

   @ObfuscatedSignature(descriptor = "(ZI)Lym;")
   @ObfuscatedName("as")
   public SpritePixels method9236(boolean var1, int var2) {
      try {
         int var3 = var1 ? 1153920467 * this.field5391 : 1274039073 * this.field5393;
         return this.method9238(var3, (short)3724);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ps.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IS)Lym;")
   @ObfuscatedName("ax")
   SpritePixels method9238(int var1, short var2) {
      try {
         if (var1 < 0) {
            if (var2 <= 232) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            SpritePixels var3 = (SpritePixels)NodeCache.method6460(field5404, var1);
            if (null != var3) {
               if (var2 <= 232) {
                  throw new IllegalStateException();
               } else {
                  return var3;
               }
            } else {
               var3 = classPN.method9084(field5411, var1, 0, -2075524879);
               if (var3 != null) {
                  if (var2 <= 232) {
                     throw new IllegalStateException();
                  }

                  field5404.method6464(var3, var1);
               }

               return var3;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ps.ax(" + ')');
      }
   }

   public classPS(int var1) {
      this.field5390 = true;
      this.field5397 = false;
      this.field5398 = new String[5];
      this.field5394 = 1718074111;
      this.field5401 = 1002430005;
      this.field5396 = Integer.MIN_VALUE;
      this.field5399 = Integer.MIN_VALUE;
      this.field5400 = classPE.field5228;
      this.field5395 = classOL.field4913;
      this.field5402 = -384460919;
      this.field5403 = var1 * 492640131;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   public static void method9225() {
      field5404.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lps;Z)Lym;")
   @ObfuscatedName("cl")
   public static SpritePixels method9237(classPS var0, boolean var1) {
      if (var0 == null) {
         var0.method9235();
      }

      int var2 = var1 ? 1153920467 * var0.field5391 : -1968481889 * var0.field5393;
      return var0.method9238(var2, (short)11797);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lps;")
   @ObfuscatedName("ao")
   public static classPS method9223(int var0) {
      return var0 >= 0 && var0 < classYV.field7093.length && classYV.field7093[var0] != null ? classYV.field7093[var0] : new classPS(var0);
   }

   public int getCategory() {
      return this.field5402 * 1254355271;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)V")
   @ObfuscatedName("ae")
   public void method9226(classXY var1, byte var2) {
      try {
         while (true) {
            int var3 = var1.method13095(-346779531);
            if (0 == var3) {
               if (var2 >= 6) {
                  return;
               }

               return;
            }

            this.method9229(var1, var3, -1692111790);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ps.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   public int method9240() {
      return this.field5403 * 872689451;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("aq")
   public void method9227(classXY var1) {
      while (true) {
         int var2 = var1.method13095(-346779531);
         if (0 == var2) {
            return;
         }

         this.method9229(var1, var2, -1665293350);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lps;Lxy;)V")
   @ObfuscatedName("jh")
   public static void method9228(classPS var0, classXY var1) {
      if (var0 == null) {
         var0.method9233();
      } else {
         while (true) {
            int var2 = var1.method13095(-346779531);
            if (0 == var2) {
               return;
            }

            var0.method9229(var1, var2, -1692095083);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgu;)Z")
   @ObfuscatedName("rl")
   public static boolean method9243(WorldMapRenderer var0) {
      return var0.field2685;
   }

   @ObfuscatedSignature(descriptor = "(Lps;B)I")
   @ObfuscatedName("gq")
   public static int method9241(classPS var0, byte var1) {
      if (var0 == null) {
         var0.method9242(var1);
      }

      try {
         return var0.field5403 * 872689451;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ps.ac(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ad")
   void method9230(classXY var1, int var2) {
      if (1 == var2) {
         this.field5393 = classXY.method13169(var1, -324749371) * 776403169;
      } else if (2 == var2) {
         this.field5391 = classXY.method13169(var1, -324749371) * -998477221;
      } else if (var2 == 3) {
         this.field5409 = var1.method13127(-306123980);
      } else if (4 == var2) {
         this.field5406 = var1.method13105(-758448158) * 1409547555;
      } else if (var2 == 5) {
         var1.method13105(-758448158);
      } else if (6 == var2) {
         this.field5392 = var1.method13095(-346779531) * -1551911823;
      } else if (var2 == 7) {
         int var3 = var1.method13095(-346779531);
         if (0 == (var3 & 1)) {
            this.field5390 = false;
         }

         if (2 == (var3 & 2)) {
            this.field5397 = true;
         }
      } else if (8 == var2) {
         var1.method13095(-346779531);
      } else if (var2 >= 10 && var2 <= 14) {
         this.field5398[var2 - 10] = var1.method13127(622726786);
      } else if (var2 == 15) {
         int var6 = var1.method13095(-346779531);
         this.field5413 = new int[2 * var6];

         for (int var4 = 0; var4 < 2 * var6; var4++) {
            this.field5413[var4] = var1.method13104((byte)16);
         }

         classXY.method13113(var1, (byte)1);
         int var8 = var1.method13095(-346779531);
         this.field5414 = new int[var8];

         for (int var5 = 0; var5 < this.field5414.length; var5++) {
            this.field5414[var5] = classXY.method13113(var1, (byte)1);
         }

         this.field5412 = new byte[var6];

         for (int var9 = 0; var9 < var6; var9++) {
            this.field5412[var9] = var1.method13097((byte)17);
         }
      } else if (var2 != 16) {
         if (17 == var2) {
            this.field5410 = var1.method13127(-637679169);
         } else if (var2 == 18) {
            classXY.method13169(var1, -324749371);
         } else if (19 == var2) {
            this.field5402 = var1.method13099(336475091) * -1772509582;
         } else if (var2 == 21) {
            classXY.method13113(var1, (byte)1);
         } else if (var2 == 22) {
            classXY.method13113(var1, (byte)1);
         } else if (23 == var2) {
            var1.method13095(-346779531);
            var1.method13095(-346779531);
            var1.method13095(-346779531);
         } else if (var2 == 24) {
            var1.method13104((byte)16);
            var1.method13104((byte)16);
         } else if (var2 == 25) {
            classXY.method13169(var1, -324749371);
         } else if (28 == var2) {
            var1.method13095(-346779531);
         } else if (29 == var2) {
            this.field5400 = (classPE)classOF.method8436(classLP.method7185(494844112), var1.method13095(-346779531), -1114593499);
         } else if (var2 == 30) {
            classOL[] var7 = new classOL[]{classOL.field4913, classOL.field4912, classOL.field4914};
            this.field5395 = (classOL)classOF.method8436(var7, var1.method13095(-346779531), 1980613816);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   public void method9233() {
      if (null != this.field5413) {
         for (byte var1 = 0; var1 < this.field5413.length; var1 += 2) {
            if (this.field5413[var1] < this.field5394 * -1409238271) {
               this.field5394 = this.field5413[var1] * 429409537;
            } else if (this.field5413[var1] > this.field5396 * 723964761) {
               this.field5396 = this.field5413[var1] * 1610387878;
            }

            if (this.field5413[1 + var1] < this.field5401 * -1912538141) {
               this.field5401 = 1145053643 * this.field5413[var1 + 1];
            } else if (this.field5413[1 + var1] > 814813303 * this.field5399) {
               this.field5399 = this.field5413[var1 + 1] * -113898180;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lps;)V")
   @ObfuscatedName("od")
   public static void method9234(classPS var0) {
      if (null != var0.field5413) {
         for (byte var1 = 0; var1 < var0.field5413.length; var1 += 2) {
            if (var0.field5413[var1] < var0.field5394 * 865751516) {
               var0.field5394 = var0.field5413[var1] * 429409537;
            } else if (var0.field5413[var1] > var0.field5396 * 723964761) {
               var0.field5396 = var0.field5413[var1] * -1560918807;
            }

            if (var0.field5413[1 + var1] < var0.field5401 * -1010761760) {
               var0.field5401 = 1145053643 * var0.field5413[var1 + 1];
            } else if (var0.field5413[1 + var1] > 1238286228 * var0.field5399) {
               var0.field5399 = var0.field5413[var1 + 1] * -1314797241;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public void method9235() {
      if (null != this.field5413) {
         for (byte var1 = 0; var1 < this.field5413.length; var1 += 2) {
            if (this.field5413[var1] < this.field5394 * -1409238271) {
               this.field5394 = this.field5413[var1] * 429409537;
            } else if (this.field5413[var1] > this.field5396 * -700804307) {
               this.field5396 = this.field5413[var1] * -1560918807;
            }

            if (this.field5413[1 + var1] < this.field5401 * -1912538141) {
               this.field5401 = -1577271871 * this.field5413[var1 + 1];
            } else if (this.field5413[1 + var1] > 814813303 * this.field5399) {
               this.field5399 = this.field5413[var1 + 1] * -2057379115;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;)[I")
   @ObfuscatedName("rh")
   public static int[] method9244(NPCComposition var0) {
      if (var0 == null) {
         var0.method9050();
      }

      return var0.field5328;
   }

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("ak")
   SpritePixels method9239(int var1) {
      if (var1 < 0) {
         return null;
      } else {
         SpritePixels var2 = (SpritePixels)NodeCache.method6460(field5404, var1);
         if (null != var2) {
            return var2;
         } else {
            var2 = classPN.method9084(field5411, var1, 0, -1652458336);
            if (var2 != null) {
               field5404.method6464(var2, var1);
            }

            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IILge;I)I")
   @ObfuscatedName("az")
   static int method9221(int var0, int var1, classGE var2, int var3) {
      try {
         classOW var4 = var2.vmethod463(var0, (byte)125);
         if (null == var4) {
            return var1;
         } else if (var4.field5168 * 151173899 >= 0) {
            if (var3 >= -678421703) {
               throw new IllegalStateException();
            } else {
               int var9 = classBU.method1246(2023234915 * var4.field5177, -2131451469 * var4.field5171, 1086307051 * var4.field5169, (byte)-1);
               int var10 = classDB.method2880(var9, 96, 1375698635);
               return classFH.field2249[var10] | 0xFF000000;
            }
         } else if (-982434803 * var4.field5174 >= 0) {
            if (var3 >= -678421703) {
               throw new IllegalStateException();
            } else {
               int var8 = classDB.method2880(classFH.field2255.field2237.vmethod408(var4.field5174 * -982434803, -1619632466), 96, 1375698635);
               return classFH.field2249[var8] | 0xFF000000;
            }
         } else if (var4.field5173 * 2136479275 == 16711935) {
            if (var3 >= -678421703) {
               throw new IllegalStateException();
            } else {
               return var1;
            }
         } else {
            int var5 = classBU.method1246(2106821511 * var4.field5178, var4.field5179 * -1352766871, var4.field5180 * -1560131225, (byte)-120);
            int var6 = classDB.method2880(var5, 96, 1375698635);
            return classFH.field2249[var6] | 0xFF000000;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ps.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ac")
   public int method9242(byte var1) {
      try {
         return this.field5403 * 872689451;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ps.ac(" + 41);
      }
   }
}
