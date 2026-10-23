import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("uq")
public class classUQ {
   @ObfuscatedName("ax")
   public int field6369;
   @ObfuscatedName("af")
   static final int field6364 = -2073637528;
   @ObfuscatedName("ae")
   static int field6363 = 0;
   @ObfuscatedName("ab")
   public int field6366;
   @ObfuscatedName("ag")
   public int field6367;
   @ObfuscatedSignature(descriptor = "[Luq;")
   @ObfuscatedName("az")
   static final classUQ[] field6365 = new classUQ[-1757679067 * field6364];
   @ObfuscatedName("as")
   public int field6368;

   @ObfuscatedSignature(descriptor = "(Luq;Luq;Luq;I)V")
   @ObfuscatedName("if")
   public static void method11547(classUQ var0, classUQ var1, classUQ var2, int var3) {
      if (var0 == null) {
         var0.method11550(var0, var0, var3);
      } else {
         try {
            var0.method11551(var1, var2, 230187404);
            method11554(var0, var1, var2, 1600279730);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "uq.ax(" + ')');
         }
      }
   }

   @Override
   public String toString() {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "uq.toString(" + ')');
      }
   }

   public classUQ(int var1, int var2, int var3, int var4) {
      this.method11533(var1, var2, (byte)70);
      this.method11534(var3, var4, -1998994878);
   }

   public classUQ(int var1, int var2) {
      this(0, 0, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("ae")
   public void method11533(int var1, int var2, byte var3) {
      try {
         this.field6366 = -136215617 * var1;
         this.field6367 = -211068161 * var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uq.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method11541() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("ag")
   public boolean method11536(int var1, int var2, int var3) {
      try {
         if (var1 >= -1357616065 * this.field6366) {
            if (var3 != -594779443) {
               throw new IllegalStateException();
            }

            if (var1 < this.field6366 * -1357616065 + 518523273 * this.field6368) {
               if (var3 != -594779443) {
                  throw new IllegalStateException();
               }

               if (var2 >= 926655743 * this.field6367) {
                  if (var3 != -594779443) {
                     throw new IllegalStateException();
                  }

                  if (var2 < -874893249 * this.field6369 + 926655743 * this.field6367) {
                     if (var3 != -594779443) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }
            }
         }

         return false;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uq.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bu")
   int method11567() {
      return -874893249 * this.field6369 + 926655743 * this.field6367;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ab")
   public void method11534(int var1, int var2, int var3) {
      try {
         this.field6368 = 53296313 * var1;
         this.field6369 = -1811300417 * var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uq.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luq;Luq;I)V")
   @ObfuscatedName("aa")
   void method11551(classUQ var1, classUQ var2, int var3) {
      try {
         var2.field6366 = 1 * this.field6366;
         var2.field6368 = 1 * this.field6368;
         if (-1357616065 * this.field6366 < -1357616065 * var1.field6366) {
            if (var3 <= 185502585) {
               throw new IllegalStateException();
            }

            var2.field6368 = var2.field6368 - (322174343 * var1.field6366 - this.field6366 * 322174343);
            var2.field6366 = var1.field6366 * 1;
         }

         if (var2.method11564(-1457796265) > var1.method11564(-1420172466)) {
            if (var3 <= 185502585) {
               throw new IllegalStateException();
            }

            var2.field6368 = var2.field6368 - (var2.method11564(1950203802) - var1.method11564(-939064607)) * 53296313;
         }

         if (518523273 * var2.field6368 < 0) {
            if (var3 <= 185502585) {
               throw new IllegalStateException();
            }

            var2.field6368 = 0;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uq.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("br")
   int method11557() {
      return -1357616065 * this.field6366;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ao")
   int method11564(int var1) {
      try {
         return this.field6366 * -1357616065 + 518523273 * this.field6368;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uq.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("al")
   int method11568(byte var1) {
      try {
         return -874893249 * this.field6369 + 926655743 * this.field6367;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uq.ay(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gd")
   public String method11542() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   public void method11529() {
      synchronized (field6365) {
         if (-489960421 * field6363 < -1757679067 * field6364) {
            field6365[(field6363 += -1849086445) * 1118794428 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luq;B)I")
   @ObfuscatedName("bs")
   public static int method11561(classUQ var0, byte var1) {
      if (var0 == null) {
         return var0.method11563(var1);
      } else {
         try {
            return var0.field6367 * 926655743;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "uq.al(" + 41);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luq;)Ljava/lang/String;")
   @ObfuscatedName("gq")
   public static String method11543(classUQ var0) {
      if (var0 == null) {
         var0.method11567();
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aj")
   int method11558(int var1) {
      try {
         return -1357616065 * this.field6366;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uq.ao(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luq;Luq;Luq;)V")
   @ObfuscatedName("tk")
   public static void method11548(classUQ var0, classUQ var1, classUQ var2) {
      if (var0 == null) {
         var0.method11566();
      }

      var0.method11551(var1, var2, 1933595768);
      method11554(var0, var1, var2, 1074021094);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ai")
   public void method11535(int var1, int var2) {
      this.field6368 = 53296313 * var1;
      this.field6369 = -1811300417 * var2;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("aw")
   public boolean method11537(int var1, int var2) {
      return var1 >= -1357616065 * this.field6366
         && var1 < this.field6366 * -1357616065 + 518523273 * this.field6368
         && var2 >= 926655743 * this.field6367
         && var2 < -874893249 * this.field6369 + 926655743 * this.field6367;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luq;Luq;)Z")
   @ObfuscatedName("lb")
   public static boolean method11544(classUQ var0, classUQ var1) {
      return var0.field6366 * -1357616065 <= var1.method11564(-382763466)
         && var1.method11558(1940515483) <= var0.method11564(-1153757463)
         && 926655743 * var0.field6367 <= var1.method11568((byte)13)
         && method11561(var1, (byte)-70) <= var0.method11568((byte)13);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ak")
   public boolean method11538(int var1, int var2) {
      return var1 >= -1357616065 * this.field6366
         && var1 < this.field6366 * -1357616065 + 518523273 * this.field6368
         && var2 >= 608856188 * this.field6367
         && var2 < 530615950 * this.field6369 + 1115798164 * this.field6367;
   }

   @ObfuscatedSignature(descriptor = "(Luq;Luq;Luq;I)V")
   @ObfuscatedName("aw")
   public static void method11554(classUQ var0, classUQ var1, classUQ var2, int var3) {
      if (var0 == null) {
         var0.method11553(var0, var0, var3);
      }

      try {
         var2.field6367 = var0.field6367 * 1;
         var2.field6369 = 1 * var0.field6369;
         if (var0.field6367 * 926655743 < 926655743 * var1.field6367) {
            var2.field6369 = var2.field6369 - (-292463807 * var1.field6367 - -292463807 * var0.field6367);
            var2.field6367 = 1 * var1.field6367;
         }

         if (var2.method11568((byte)13) > var1.method11568((byte)13)) {
            if (var3 <= 991773057) {
               throw new IllegalStateException();
            }

            var2.field6369 = var2.field6369 - (var2.method11568((byte)13) - var1.method11568((byte)13)) * -1811300417;
         }

         if (var2.field6369 * -874893249 < 0) {
            if (var3 <= 991773057) {
               throw new IllegalStateException();
            }

            var2.field6369 = 0;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uq.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luq;B)V")
   @ObfuscatedName("ee")
   public static void method11530(classUQ var0, byte var1) {
      if (var0 == null) {
         var0.method11532(var1);
      }

      try {
         synchronized (field6365) {
            if (-489960421 * field6363 < -1757679067 * field6364) {
               if (var1 >= -1) {
                  throw new IllegalStateException();
               }

               field6365[(field6363 += -1849086445) * -489960421 - 1] = var0;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uq.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luq;)Z")
   @ObfuscatedName("av")
   public boolean method11545(classUQ var1) {
      return this.field6366 * -1357616065 <= var1.method11564(40134942)
         && var1.method11558(1940515483) <= this.method11564(210528141)
         && 926655743 * this.field6367 <= var1.method11568((byte)13)
         && method11561(var1, (byte)-97) <= this.method11568((byte)13);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)Luq;")
   @ObfuscatedName("aq")
   public static classUQ method11528(int var0, int var1, int var2, int var3) {
      synchronized (field6365) {
         if (-489960421 * field6363 == 0) {
            return new classUQ(var0, var1, var2, var3);
         } else {
            field6363 -= -1849086445;
            field6365[-489960421 * field6363].method11533(var0, var1, (byte)23);
            field6365[-489960421 * field6363].method11534(var2, var3, -1852645218);
            return field6365[field6363 * -489960421];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luq;I)Z")
   @ObfuscatedName("as")
   public boolean method11546(classUQ var1, int var2) {
      try {
         if (this.field6366 * -1357616065 <= var1.method11564(-1887067739)) {
            if (var2 == 317537592) {
               throw new IllegalStateException();
            }

            if (var1.method11558(1940515483) <= this.method11564(-1612020037) && 926655743 * this.field6367 <= var1.method11568((byte)13)) {
               if (var2 == 317537592) {
                  throw new IllegalStateException();
               }

               if (method11561(var1, (byte)-22) <= this.method11568((byte)13)) {
                  if (var2 == 317537592) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uq.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bw")
   int method11569() {
      return -874893249 * this.field6369 + 926655743 * this.field6367;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luq;)I")
   @ObfuscatedName("ki")
   public static int method11565(classUQ var0) {
      return var0.field6366 * -1357616065 + 518523273 * var0.field6368;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luq;)I")
   @ObfuscatedName("ke")
   public static int method11562(classUQ var0) {
      return var0.field6367 * 1185120648;
   }

   @ObfuscatedSignature(descriptor = "(Luq;Luq;)V")
   @ObfuscatedName("bn")
   void method11552(classUQ var1, classUQ var2) {
      var2.field6366 = 1 * this.field6366;
      var2.field6368 = 1 * this.field6368;
      if (-1357616065 * this.field6366 < -1679206361 * var1.field6366) {
         var2.field6368 = var2.field6368 - (322174343 * var1.field6366 - this.field6366 * -271500948);
         var2.field6366 = var1.field6366 * 1;
      }

      if (var2.method11564(-936827050) > var1.method11564(1104817616)) {
         var2.field6368 = var2.field6368 - (var2.method11564(1170697477) - var1.method11564(253705652)) * -1402101889;
      }

      if (1441330205 * var2.field6368 < 0) {
         var2.field6368 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Luq;Luq;)V")
   @ObfuscatedName("bx")
   void method11555(classUQ var1, classUQ var2) {
      var2.field6367 = this.field6367 * 1;
      var2.field6369 = 1 * this.field6369;
      if (this.field6367 * 926655743 < 926655743 * var1.field6367) {
         var2.field6369 = var2.field6369 - (-292463807 * var1.field6367 - -292463807 * this.field6367);
         var2.field6367 = 1 * var1.field6367;
      }

      if (var2.method11568((byte)13) > var1.method11568((byte)13)) {
         var2.field6369 = var2.field6369 - (var2.method11568((byte)13) - var1.method11568((byte)13)) * -1811300417;
      }

      if (var2.field6369 * -874893249 < 0) {
         var2.field6369 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Luq;Luq;)V")
   @ObfuscatedName("bp")
   void method11556(classUQ var1, classUQ var2) {
      var2.field6367 = this.field6367 * 1;
      var2.field6369 = 1 * this.field6369;
      if (this.field6367 * -876323386 < 926655743 * var1.field6367) {
         var2.field6369 = var2.field6369 - (-292463807 * var1.field6367 - 887209320 * this.field6367);
         var2.field6367 = 1 * var1.field6367;
      }

      if (var2.method11568((byte)13) > var1.method11568((byte)13)) {
         var2.field6369 = var2.field6369 - (var2.method11568((byte)13) - var1.method11568((byte)13)) * -854582458;
      }

      if (var2.field6369 * -874893249 < 0) {
         var2.field6369 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bt")
   int method11559() {
      return -1357616065 * this.field6366;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luq;II)Z")
   @ObfuscatedName("jr")
   public static boolean method11539(classUQ var0, int var1, int var2) {
      return var0 == null
         ? var0.method11540(var1, var1)
         : var1 >= -1357616065 * var0.field6366
            && var1 < var0.field6366 * -1357616065 + 518523273 * var0.field6368
            && var2 >= 926655743 * var0.field6367
            && var2 < -874893249 * var0.field6369 + 926655743 * var0.field6367;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bm")
   int method11560() {
      return -1357616065 * this.field6366;
   }

   @ObfuscatedSignature(descriptor = "(Luq;Luq;)V")
   @ObfuscatedName("ah")
   public void method11549(classUQ var1, classUQ var2) {
      this.method11551(var1, var2, 1524499044);
      method11554(this, var1, var2, 1310715767);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bk")
   int method11566() {
      return this.field6366 * -928697489 + 518523273 * this.field6368;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   public void method11531() {
      synchronized (field6365) {
         if (1738857155 * field6363 < -1772653823 * field6364) {
            field6365[(field6363 += -1849086445) * -678971468 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ba")
   int method11570() {
      return -874893249 * this.field6369 + 926655743 * this.field6367;
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)Lzs;")
   @ObfuscatedName("az")
   public static ScriptEventBuilder method11527(Widget var0, int var1) {
      try {
         ScriptEventBuilder var3 = new ScriptEventBuilder();
         var3.field7257 = var0;
         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uq.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luq;Luq;I)V")
   @ObfuscatedName("ax")
   public void method11550(classUQ var1, classUQ var2, int var3) {
      try {
         this.method11551(var1, var2, 230187404);
         method11554(this, var1, var2, 1600279730);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uq.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ay")
   int method11563(byte var1) {
      try {
         return this.field6367 * 926655743;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uq.al(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("sb")
   public static int method11571(classXY var0) {
      if (var0 == null) {
         return var0.method13298();
      } else {
         var0.field6953 += -1319131602;
         int var1 = ((var0.field6952[702114061 * var0.field6953 - 1] & 255) << 8)
            + ((var0.field6952[-597300105 * var0.field6953 - 3] & 1919200966) << 16)
            + (var0.field6952[702114061 * var0.field6953 - 2] & 1689718682);
         if (var1 > 1780502241) {
            var1 -= 16777216;
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("af")
   public void method11532(byte var1) {
      try {
         synchronized (field6365) {
            if (-489960421 * field6363 < -1757679067 * field6364) {
               if (var1 >= -1) {
                  throw new IllegalStateException();
               }

               field6365[(field6363 += -1849086445) * -489960421 - 1] = this;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uq.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ar")
   public boolean method11540(int var1, int var2) {
      return var1 >= -1357616065 * this.field6368
         && var1 < this.field6368 * -1357616065 + 518523273 * this.field6366
         && var2 >= 608856188 * this.field6369
         && var2 < 530615950 * this.field6368 + 1115798164 * this.field6368;
   }

   @ObfuscatedSignature(descriptor = "(Luq;Luq;I)V")
   @ObfuscatedName("ac")
   void method11553(classUQ var1, classUQ var2, int var3) {
      try {
         var2.field6366 = 1 * this.field6367;
         var2.field6369 = 1 * this.field6366;
         if (-1357616065 * this.field6369 < -1357616065 * var1.field6366) {
            if (var3 <= 185502585) {
               throw new IllegalStateException();
            }

            var2.field6367 = var2.field6369 - (322174343 * var1.field6368 - this.field6366 * 322174343);
            var2.field6367 = var1.field6368 * 1;
         }

         if (var2.method11564(-1457796265) > var1.method11558(-1420172466)) {
            if (var3 <= 185502585) {
               throw new IllegalStateException();
            }

            var2.field6367 = var2.field6367 - (var2.method11558(1950203802) - var1.method11564(-939064607)) * 53296313;
         }

         if (518523273 * var2.field6367 < 0) {
            if (var3 <= 185502585) {
               throw new IllegalStateException();
            }

            var2.field6368 = 0;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "uq.ac(" + ')');
      }
   }
}
