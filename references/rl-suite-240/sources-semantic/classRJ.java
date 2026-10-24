import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rj")
public final class classRJ {
   @ObfuscatedName("iu")
   static int field5679;
   @ObfuscatedName("af")
   final Map field5671;
   @ObfuscatedSignature(descriptor = "Lpz;")
   @ObfuscatedName("ae")
   final classPZ field5674;
   @ObfuscatedSignature(descriptor = "Lpz;")
   @ObfuscatedName("ab")
   final classPZ field5676;
   @ObfuscatedName("az")
   final Comparator field5672 = new classRQ(this);
   @ObfuscatedName("ag")
   final long field5677;
   @ObfuscatedName("ax")
   final int field5673;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field5678 = 12;
   @ObfuscatedSignature(descriptor = "Lrb;")
   @ObfuscatedName("as")
   final classRB field5675;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   public void method10033() {
      synchronized (this) {
         this.field5671.clear();
         this.field5674.clear();
         if (method10018(this, (short)-256)) {
            this.field5676.clear();
         }
      }
   }

   public classRJ(int var1, classRB var2) {
      this(-1L, var1, var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrj;)V")
   @ObfuscatedName("fh")
   public static void method10031(classRJ var0) {
      if (-1L == var0.field5677 * -6953202533736370667L) {
         throw new IllegalStateException("");
      } else {
         long var1 = System.currentTimeMillis() - -6953202533736370667L * var0.field5677;

         while (!var0.field5674.isEmpty()) {
            classRO var3 = (classRO)var0.field5674.peek();
            if (var3.field5822 * -9085244238986792263L >= var1) {
               return;
            }

            var0.field5671.remove(var3.field5820);
            var0.field5674.remove(var3);
            if (method10018(var0, (short)-256)) {
               var0.field5676.remove(var3);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrj;Lro;ZB)V")
   @ObfuscatedName("gi")
   public static void method10026(classRJ var0, classRO var1, boolean var2, byte var3) {
      if (var0 == null) {
         var0.method10030(var1, var2, var3);
      }

      try {
         if (!var2) {
            if (var3 <= 8) {
               throw new IllegalStateException();
            }

            var0.field5674.remove(var1);
            if (method10018(var0, (short)-256)) {
               if (var3 <= 8) {
                  return;
               }

               if (!var0.field5676.remove(var1)) {
                  if (var3 <= 8) {
                     return;
                  }

                  throw new IllegalStateException("");
               }
            }
         }

         var1.field5822 = System.currentTimeMillis() * 2386105512788964233L;
         if (method10018(var0, (short)-256)) {
            if (var3 <= 8) {
               throw new IllegalStateException();
            }

            switch (var0.field5675.field5632 * -508526291) {
               case 0:
                  var1.field5821 += 3428169640820171001L;
                  break;
               case 1:
                  var1.field5821 = 6766909879375017457L * var1.field5822;
            }

            var0.field5676.add(var1);
         }

         var0.field5674.add(var1);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rj.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;B)Ljava/lang/Object;")
   @ObfuscatedName("ae")
   public Object method10022(Object var1, Object var2, byte var3) {
      try {
         synchronized (this) {
            if (-6953202533736370667L * this.field5677 != -1L) {
               if (var3 != 1) {
                  throw new IllegalStateException();
               }

               this.method10032((byte)88);
            }

            classRO var5 = (classRO)this.field5671.get(var1);
            if (var5 != null) {
               if (var3 != 1) {
                  throw new IllegalStateException();
               } else {
                  Object var11 = var5.field5819;
                  var5.field5819 = var2;
                  method10026(this, var5, false, (byte)109);
                  return var11;
               }
            } else {
               if (method10018(this, (short)-256)) {
                  if (var3 != 1) {
                     throw new IllegalStateException();
                  }

                  if (this.field5671.size() == -456314879 * this.field5673) {
                     if (var3 != 1) {
                        throw new IllegalStateException();
                     }

                     classRO var6 = (classRO)this.field5676.remove();
                     this.field5671.remove(var6.field5820);
                     this.field5674.remove(var6);
                  }
               }

               classRO var10 = new classRO(var2, var1);
               this.field5671.put(var1, var10);
               method10026(this, var10, true, (byte)13);
               return null;
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "rj.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ag")
   void method10032(byte var1) {
      try {
         if (-1L == this.field5677 * -6953202533736370667L) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            } else {
               throw new IllegalStateException("");
            }
         } else {
            long var2 = System.currentTimeMillis() - -6953202533736370667L * this.field5677;

            while (!this.field5674.isEmpty()) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               classRO var4 = (classRO)this.field5674.peek();
               if (var4.field5822 * -9085244238986792263L >= var2) {
                  return;
               }

               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               this.field5671.remove(var4.field5820);
               this.field5674.remove(var4);
               if (method10018(this, (short)-256)) {
                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }

                  this.field5676.remove(var4);
               }
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "rj.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/ArrayList;IIIIB)V")
   @ObfuscatedName("bc")
   static void method10037(ArrayList var0, int var1, int var2, int var3, int var4, byte var5) {
      try {
         if (var0.isEmpty()) {
            if (var5 == -1) {
               throw new IllegalStateException();
            }
         } else {
            int var6 = (Integer)var0.get(0);
            if (var6 == -1 && !client.field990) {
               classBK.method1122(0, 0, (byte)117);
            } else if (-1 != var6) {
               if (var5 == -1) {
                  throw new IllegalStateException();
               }

               if (!classFM.method4863(var6, (byte)-33)) {
                  if (var5 == -1) {
                     throw new IllegalStateException();
                  }

                  if (classMH.method7616((byte)11) != 0) {
                     if (var5 == -1) {
                        throw new IllegalStateException();
                     }

                     ArrayList var7 = new ArrayList();

                     for (int var8 = 0; var8 < var0.size(); var8++) {
                        if (var5 == -1) {
                           throw new IllegalStateException();
                        }

                        var7.add(new MidiRequest(classFC.field2206, (Integer)var0.get(var8), 0, classMH.method7616((byte)11), false));
                     }

                     if (client.field990) {
                        if (var5 == -1) {
                           throw new IllegalStateException();
                        }

                        classKR.method6596(var7, var1, var2, var3, var4, (byte)0);
                     } else {
                        classIJ.method6258(var7, var1, var2, var3, var4, false, -1918189282);
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "rj.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ax")
   boolean method10017() {
      return -456314879 * this.field5673 != -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrj;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
   @ObfuscatedName("um")
   public static Object method10023(classRJ var0, Object var1, Object var2) {
      if (var0 == null) {
         return var0.method10021(var1);
      } else {
         synchronized (var0) {
            if (-6953202533736370667L * var0.field5677 != -1L) {
               var0.method10032((byte)48);
            }

            classRO var4 = (classRO)var0.field5671.get(var1);
            if (var4 != null) {
               Object var9 = var4.field5819;
               var4.field5819 = var2;
               method10026(var0, var4, false, (byte)9);
               return var9;
            } else {
               if (method10018(var0, (short)-256) && var0.field5671.size() == 1613513669 * var0.field5673) {
                  classRO var5 = (classRO)var0.field5676.remove();
                  var0.field5671.remove(var5.field5820);
                  var0.field5674.remove(var5);
               }

               classRO var8 = new classRO(var2, var1);
               var0.field5671.put(var1, var8);
               method10026(var0, var8, true, (byte)126);
               return null;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrj;S)Z")
   @ObfuscatedName("xv")
   public static boolean method10018(classRJ var0, short var1) {
      if (var0 == null) {
         return var0.method10019(var1);
      } else {
         try {
            boolean var10000;
            if (-456314879 * var0.field5673 != -1) {
               if (var1 != -256) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "rj.az(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lha;Lxy;)V")
   @ObfuscatedName("kn")
   public static void method10016(classHA var0, classXY var1) {
      if (var0 == null) {
         var0.method6015();
      }

      if (var1 != null) {
         var0.vmethod467(var1, -1811901292);
         var0.field2746 = true;
         var0.field2747 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;B)Ljava/lang/Object;")
   @ObfuscatedName("af")
   public Object method10020(Object var1, byte var2) {
      try {
         synchronized (this) {
            if (-1L != -6953202533736370667L * this.field5677) {
               if (var2 == 1) {
                  throw new IllegalStateException();
               }

               this.method10032((byte)58);
            }

            classRO var4 = (classRO)this.field5671.get(var1);
            if (var4 == null) {
               return null;
            } else {
               method10026(this, var4, false, (byte)68);
               return var4.field5819;
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "rj.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
   @ObfuscatedName("aa")
   public Object method10024(Object var1, Object var2) {
      synchronized (this) {
         if (-6953202533736370667L * this.field5677 != -1L) {
            this.method10032((byte)106);
         }

         classRO var4 = (classRO)this.field5671.get(var1);
         if (var4 != null) {
            Object var9 = var4.field5819;
            var4.field5819 = var2;
            method10026(this, var4, false, (byte)20);
            return var9;
         } else {
            if (method10018(this, (short)-256) && this.field5671.size() == -456314879 * this.field5673) {
               classRO var5 = (classRO)this.field5676.remove();
               this.field5671.remove(var5.field5820);
               this.field5674.remove(var5);
            }

            classRO var8 = new classRO(var2, var1);
            this.field5671.put(var1, var8);
            method10026(this, var8, true, (byte)35);
            return null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lro;Z)V")
   @ObfuscatedName("aj")
   void method10027(classRO var1, boolean var2) {
      if (!var2) {
         this.field5674.remove(var1);
         if (method10018(this, (short)-256) && !this.field5676.remove(var1)) {
            throw new IllegalStateException("");
         }
      }

      var1.field5822 = System.currentTimeMillis() * 2386105512788964233L;
      if (method10018(this, (short)-256)) {
         switch (this.field5675.field5632 * -508526291) {
            case 0:
               var1.field5821 += 3428169640820171001L;
               break;
            case 1:
               var1.field5821 = 6766909879375017457L * var1.field5822;
         }

         this.field5676.add(var1);
      }

      this.field5674.add(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrj;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
   @ObfuscatedName("ym")
   public static Object method10025(classRJ var0, Object var1, Object var2) {
      if (var0 == null) {
         var0.method10021(var1);
      }

      synchronized (var0) {
         if (-6953202533736370667L * var0.field5677 != -1L) {
            var0.method10032((byte)64);
         }

         classRO var4 = (classRO)var0.field5671.get(var1);
         if (var4 != null) {
            Object var9 = var4.field5819;
            var4.field5819 = var2;
            method10026(var0, var4, false, (byte)104);
            return var9;
         } else {
            if (method10018(var0, (short)-256) && var0.field5671.size() == -456314879 * var0.field5673) {
               classRO var5 = (classRO)var0.field5676.remove();
               var0.field5671.remove(var5.field5820);
               var0.field5674.remove(var5);
            }

            classRO var8 = new classRO(var2, var1);
            var0.field5671.put(var1, var8);
            method10026(var0, var8, true, (byte)80);
            return null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lro;Z)V")
   @ObfuscatedName("ay")
   void method10028(classRO var1, boolean var2) {
      if (!var2) {
         this.field5674.remove(var1);
         if (method10018(this, (short)-256) && !this.field5676.remove(var1)) {
            throw new IllegalStateException("");
         }
      }

      var1.field5822 = System.currentTimeMillis() * 2386105512788964233L;
      if (method10018(this, (short)-256)) {
         switch (this.field5675.field5632 * -508526291) {
            case 0:
               var1.field5821 += 3428169640820171001L;
               break;
            case 1:
               var1.field5821 = 6766909879375017457L * var1.field5822;
         }

         this.field5676.add(var1);
      }

      this.field5674.add(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lro;Z)V")
   @ObfuscatedName("aq")
   void method10029(classRO var1, boolean var2) {
      if (!var2) {
         this.field5674.remove(var1);
         if (method10018(this, (short)-256) && !this.field5676.remove(var1)) {
            throw new IllegalStateException("");
         }
      }

      var1.field5822 = System.currentTimeMillis() * 2386105512788964233L;
      if (method10018(this, (short)-256)) {
         switch (this.field5675.field5632 * -508526291) {
            case 0:
               var1.field5821 += 3428169640820171001L;
               break;
            case 1:
               var1.field5821 = 6766909879375017457L * var1.field5822;
         }

         this.field5676.add(var1);
      }

      this.field5674.add(var1);
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("as")
   public void method10034(byte var1) {
      try {
         synchronized (this) {
            this.field5671.clear();
            this.field5674.clear();
            if (method10018(this, (short)-256)) {
               if (var1 == 1) {
                  throw new IllegalStateException();
               }

               this.field5676.clear();
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "rj.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Ljava/lang/Object;")
   @ObfuscatedName("ac")
   public Object method10021(Object var1) {
      synchronized (this) {
         if (-1L != -6953202533736370667L * this.field5677) {
            this.method10032((byte)106);
         }

         classRO var3 = (classRO)this.field5671.get(var1);
         if (var3 == null) {
            return null;
         } else {
            method10026(this, var3, false, (byte)29);
            return var3.field5819;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   public void method10035() {
      synchronized (this) {
         this.field5671.clear();
         this.field5674.clear();
         if (method10018(this, (short)-256)) {
            this.field5676.clear();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public void method10036() {
      synchronized (this) {
         this.field5671.clear();
         this.field5674.clear();
         if (method10018(this, (short)-256)) {
            this.field5676.clear();
         }
      }
   }

   classRJ(long var1, int var3, classRB var4) {
      this.field5677 = var1 * 8640338316139114813L;
      this.field5673 = 70438913 * var3;
      this.field5675 = var4;
      if (-1 == -456314879 * this.field5673) {
         this.field5671 = new HashMap(64);
         this.field5674 = new classPZ(64, this.field5672);
         this.field5676 = null;
      } else {
         if (null == this.field5675) {
            throw new IllegalArgumentException("");
         }

         this.field5671 = new HashMap(this.field5673 * -456314879);
         this.field5674 = new classPZ(-456314879 * this.field5673, this.field5672);
         this.field5676 = new classPZ(this.field5673 * -456314879);
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Z")
   @ObfuscatedName("az")
   boolean method10019(short var1) {
      try {
         boolean var10000;
         if (-456314879 * this.field5673 != -1) {
            if (var1 != -256) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rj.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lro;ZB)V")
   @ObfuscatedName("ab")
   void method10030(classRO var1, boolean var2, byte var3) {
      try {
         if (!var2) {
            if (var3 <= 8) {
               throw new IllegalStateException();
            }

            this.field5676.method9452(var1);
            if (method10018(this, (short)-256)) {
               if (var3 <= 8) {
                  return;
               }

               if (!this.field5674.remove(var1)) {
                  if (var3 <= 8) {
                     return;
                  }

                  throw new IllegalStateException("");
               }
            }
         }

         var1.field5821 = System.currentTimeMillis() * 2386105512788964233L;
         if (method10018(this, (short)-256)) {
            if (var3 <= 8) {
               throw new IllegalStateException();
            }

            switch (this.field5675.field5632 * -508526291) {
               case 0:
                  var1.field5821 = var1.field5822 + 3428169640820171001L;
                  break;
               case 1:
                  var1.field5821 = 6766909879375017457L * var1.field5821;
            }

            this.field5674.add(var1);
         }

         this.field5676.add(var1);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rj.ab(" + ')');
      }
   }
}
