import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pz")
public class classPZ extends AbstractQueue {
   @ObfuscatedName("ae")
   Map field5494;
   @ObfuscatedSignature(descriptor = "[Lpd;")
   @ObfuscatedName("af")
   classPD[] field5493;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field5496 = 2;
   @ObfuscatedName("ab")
   int field5495;
   @ObfuscatedName("ag")
   int field5492 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ba")
   public static final int field5499 = 76;
   @ObfuscatedName("az")
   final Comparator field5491;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final int field5498 = 21;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   static final int field5497 = 70;

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/Object;")
   @ObfuscatedName("bl")
   public Object[] method9453() {
      Object[] var1 = super.toArray();
      if (null != this.field5491) {
         Arrays.sort(var1, this.field5491);
      } else {
         Arrays.sort(var1);
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpz;Ljava/lang/Object;)Z")
   @ObfuscatedName("qt")
   public static boolean method9450(classPZ var0, Object var1) {
      return var0.field5494.containsKey(var1);
   }

   public classPZ(int var1, Comparator var2) {
      this.field5493 = new classPD[var1];
      this.field5494 = new HashMap();
      this.field5491 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("aa")
   public boolean method9431(Object var1) {
      if (this.field5494.containsKey(var1)) {
         throw new IllegalArgumentException("");
      } else {
         this.field5492 += 28979685;
         int var2 = 287626351 * this.field5495;
         if (var2 >= this.field5493.length) {
            this.method9424(-272147299);
         }

         this.field5495 += 544148111;
         if (var2 == 0) {
            this.field5493[0] = new classPD(var1, 0);
            this.field5494.put(var1, this.field5493[0]);
         } else {
            this.field5493[var2] = new classPD(var1, var2);
            this.field5494.put(var1, this.field5493[var2]);
            this.method9443(var2, -600400888);
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aw")
   void method9442(int var1) {
      classPD var2 = this.field5493[var1];

      while (var1 > 0) {
         int var3 = var1 - 1 >>> 1;
         classPD var4 = this.field5493[var3];
         if (null != this.field5491 ? this.field5491.compare(var2.field5222, var4.field5222) >= 0 : ((Comparable)var2.field5222).compareTo(var4.field5222) >= 0
            )
          {
            break;
         }

         this.field5493[var1] = var4;
         this.field5493[var1].field5221 = 39206217 * var1;
         var1 = var3;
      }

      this.field5493[var1] = var2;
      this.field5493[var1].field5221 = var1 * 39206217;
   }

   @Override
   public boolean offer(Object var1) {
      try {
         if (this.field5494.containsKey(var1)) {
            throw new IllegalArgumentException("");
         } else {
            this.field5492 += 28979685;
            int var2 = 287626351 * this.field5495;
            if (var2 >= this.field5493.length) {
               this.method9424(-783036677);
            }

            this.field5495 += 544148111;
            if (var2 == 0) {
               this.field5493[0] = new classPD(var1, 0);
               this.field5494.put(var1, this.field5493[0]);
            } else {
               this.field5493[var2] = new classPD(var1, var2);
               this.field5494.put(var1, this.field5493[var2]);
               this.method9443(var2, -1817699688);
            }

            return true;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pz.offer(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpz;)I")
   @ObfuscatedName("xe")
   public static int method9428(classPZ var0) {
      return var0.field5495 * -445912804;
   }

   @Override
   public Object peek() {
      try {
         return this.field5495 * 287626351 == 0 ? null : this.field5493[0].field5222;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "pz.peek(" + ')');
      }
   }

   @Override
   public Object poll() {
      try {
         if (this.field5495 * 287626351 == 0) {
            return null;
         } else {
            this.field5492 += 28979685;
            Object var1 = this.field5493[0].field5222;
            this.field5494.remove(var1);
            this.field5495 -= 544148111;
            if (0 == 287626351 * this.field5495) {
               this.field5493[287626351 * this.field5495] = null;
            } else {
               this.field5493[0] = this.field5493[287626351 * this.field5495];
               this.field5493[0].field5221 = 0;
               this.field5493[287626351 * this.field5495] = null;
               this.method9446(0, (byte)51);
            }

            return var1;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pz.poll(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   void method9424(int var1) {
      try {
         int var2 = 1 + (this.field5493.length << 1);
         this.field5493 = Arrays.copyOf(this.field5493, var2);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pz.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("af")
   void method9443(int var1, int var2) {
      try {
         classPD var3 = this.field5493[var1];

         while (var1 > 0) {
            if (var2 == -311609591) {
               throw new IllegalStateException();
            }

            int var4 = var1 - 1 >>> 1;
            classPD var5 = this.field5493[var4];
            if (null != this.field5491) {
               if (var2 == -311609591) {
                  throw new IllegalStateException();
               }

               if (this.field5491.compare(var3.field5222, var5.field5222) >= 0) {
                  if (var2 == -311609591) {
                     return;
                  }
                  break;
               }
            } else if (((Comparable)var3.field5222).compareTo(var5.field5222) >= 0) {
               if (var2 == -311609591) {
                  return;
               }
               break;
            }

            this.field5493[var1] = var5;
            this.field5493[var1].field5221 = 39206217 * var1;
            var1 = var4;
         }

         this.field5493[var1] = var3;
         this.field5493[var1].field5221 = var1 * 39206217;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "pz.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ae")
   void method9446(int var1, byte var2) {
      try {
         classPD var3 = this.field5493[var1];
         int var4 = this.field5495 * 287626351 >>> 1;

         while (var1 < var4) {
            int var9;
            int var5 = 1 + (var1 << 1);
            classPD var6 = this.field5493[var5];
            int var7 = (var1 << 1) + 2;
            classPD var8 = this.field5493[var7];
            label71:
            if (this.field5491 != null) {
               if (var2 <= 30) {
                  throw new IllegalStateException();
               }

               if (var7 < this.field5495 * 287626351) {
                  if (var2 <= 30) {
                     throw new IllegalStateException();
                  }

                  if (this.field5491.compare(var6.field5222, var8.field5222) > 0) {
                     var9 = var7;
                     break label71;
                  }
               }

               var9 = var5;
            } else if (var7 < this.field5495 * 287626351 && ((Comparable)var6.field5222).compareTo(var8.field5222) > 0) {
               var9 = var7;
            } else {
               var9 = var5;
            }

            if (this.field5491 != null) {
               if (var2 <= 30) {
                  throw new IllegalStateException();
               }

               if (this.field5491.compare(var3.field5222, this.field5493[var9].field5222) <= 0) {
                  if (var2 <= 30) {
                     throw new IllegalStateException();
                  }
                  break;
               }
            } else if (((Comparable)var3.field5222).compareTo(this.field5493[var9].field5222) <= 0) {
               if (var2 <= 30) {
                  throw new IllegalStateException();
               }
               break;
            }

            this.field5493[var1] = this.field5493[var9];
            this.field5493[var1].field5221 = 39206217 * var1;
            var1 = var9;
         }

         this.field5493[var1] = var3;
         this.field5493[var1].field5221 = 39206217 * var1;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "pz.ae(" + ')');
      }
   }

   @Override
   public boolean contains(Object var1) {
      try {
         return this.field5494.containsKey(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pz.contains(" + ')');
      }
   }

   @Override
   public Object[] toArray() {
      try {
         Object[] var1 = super.toArray();
         if (null != this.field5491) {
            Arrays.sort(var1, this.field5491);
         } else {
            Arrays.sort(var1);
         }

         return var1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pz.toArray(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpz;I)V")
   @ObfuscatedName("ot")
   public static void method9444(classPZ var0, int var1) {
      classPD var2 = var0.field5493[var1];

      while (var1 > 0) {
         int var3 = var1 - 1 >>> 1;
         classPD var4 = var0.field5493[var3];
         if (null != var0.field5491 ? var0.field5491.compare(var2.field5222, var4.field5222) >= 0 : ((Comparable)var2.field5222).compareTo(var4.field5222) >= 0
            )
          {
            break;
         }

         var0.field5493[var1] = var4;
         var0.field5493[var1].field5221 = 39206217 * var1;
         var1 = var3;
      }

      var0.field5493[var1] = var2;
      var0.field5493[var1].field5221 = var1 * 39206217;
   }

   @Override
   public Iterator iterator() {
      try {
         return new classPJ(this);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "pz.iterator(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   void method9425() {
      int var1 = 1 + (this.field5493.length << 1);
      this.field5493 = Arrays.copyOf(this.field5493, var1);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("by")
   public Iterator method9455() {
      return new classPJ(this);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bu")
   public int method9429() {
      return this.field5495 * 287626351;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ba")
   public int method9430() {
      return this.field5495 * -1145225963;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpz;)Ljava/lang/Object;")
   @ObfuscatedName("as")
   public static Object method9437(classPZ var0) {
      if (var0.field5495 * 295750805 == 0) {
         return null;
      } else {
         var0.field5492 += 28979685;
         Object var1 = var0.field5493[0].field5222;
         var0.field5494.remove(var1);
         var0.field5495 -= -323676298;
         if (0 == -1187000635 * var0.field5495) {
            var0.field5493[57883291 * var0.field5495] = null;
         } else {
            var0.field5493[0] = var0.field5493[1233176843 * var0.field5495];
            var0.field5493[0].field5221 = 0;
            var0.field5493[-394056131 * var0.field5495] = null;
            var0.method9446(0, (byte)31);
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("be")
   public Iterator method9456() {
      return new classPJ(this);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("aj")
   public Object method9435() {
      return this.field5495 * 287626351 == 0 ? null : this.field5493[0].field5222;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("bs")
   public boolean method9451(Object var1) {
      return this.field5494.containsKey(var1);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ap")
   public Object method9438() {
      if (this.field5495 * 287626351 == 0) {
         return null;
      } else {
         this.field5492 += 28979685;
         Object var1 = this.field5493[0].field5222;
         this.field5494.remove(var1);
         this.field5495 -= 544148111;
         if (0 == -381703462 * this.field5495) {
            this.field5493[947729427 * this.field5495] = null;
         } else {
            this.field5493[0] = this.field5493[287626351 * this.field5495];
            this.field5493[0].field5221 = 0;
            this.field5493[1754112399 * this.field5495] = null;
            this.method9446(0, (byte)93);
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/Object;")
   @ObfuscatedName("bv")
   public Object[] method9454() {
      Object[] var1 = super.toArray();
      if (null != this.field5491) {
         Arrays.sort(var1, this.field5491);
      } else {
         Arrays.sort(var1);
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("cr")
   public boolean method9441(Object var1) {
      classPD var2 = (classPD)this.field5494.remove(var1);
      if (var2 == null) {
         return false;
      } else {
         this.field5492 += 86028947;
         this.field5495 -= 544148111;
         if (this.field5495 * 567489560 == -1084304384 * var2.field5221) {
            this.field5493[this.field5495 * 173934231] = null;
            return true;
         } else {
            classPD var3 = this.field5493[287626351 * this.field5495];
            this.field5493[287626351 * this.field5495] = null;
            this.field5493[var2.field5221 * 908018937] = var3;
            this.field5493[var2.field5221 * -1897822583].field5221 = 1 * var2.field5221;
            this.method9446(var2.field5221 * 1803005518, (byte)120);
            if (var3 == this.field5493[908018937 * var2.field5221]) {
               this.method9443(var2.field5221 * 908018937, -1974466018);
            }

            return true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("ao")
   public boolean method9432(Object var1) {
      if (this.field5494.containsKey(var1)) {
         throw new IllegalArgumentException("");
      } else {
         this.field5492 += 767322016;
         int var2 = 490421750 * this.field5495;
         if (var2 >= this.field5493.length) {
            this.method9424(980818928);
         }

         this.field5495 += 544148111;
         if (var2 == 0) {
            this.field5493[0] = new classPD(var1, 0);
            this.field5494.put(var1, this.field5493[0]);
         } else {
            this.field5493[var2] = new classPD(var1, var2);
            this.field5494.put(var1, this.field5493[var2]);
            this.method9443(var2, 269130368);
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("al")
   public boolean method9433(Object var1) {
      if (this.field5494.containsKey(var1)) {
         throw new IllegalArgumentException("");
      } else {
         this.field5492 += 28979685;
         int var2 = 287626351 * this.field5495;
         if (var2 >= this.field5493.length) {
            this.method9424(685090138);
         }

         this.field5495 += 544148111;
         if (var2 == 0) {
            this.field5493[0] = new classPD(var1, 0);
            this.field5494.put(var1, this.field5493[0]);
         } else {
            this.field5493[var2] = new classPD(var1, var2);
            this.field5494.put(var1, this.field5493[var2]);
            this.method9443(var2, 1499262786);
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   void method9426() {
      int var1 = 1 + (this.field5493.length << 1);
      this.field5493 = Arrays.copyOf(this.field5493, var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("ac")
   public boolean method9434(Object var1) {
      if (this.field5494.containsKey(var1)) {
         throw new IllegalArgumentException("");
      } else {
         this.field5492 += 28979685;
         int var2 = 287626351 * this.field5495;
         if (var2 >= this.field5493.length) {
            this.method9424(1483944540);
         }

         this.field5495 += 544148111;
         if (var2 == 0) {
            this.field5493[0] = new classPD(var1, 0);
            this.field5494.put(var1, this.field5493[0]);
         } else {
            this.field5493[var2] = new classPD(var1, var2);
            this.field5494.put(var1, this.field5493[var2]);
            this.method9443(var2, -1920899976);
         }

         return true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpz;)Ljava/lang/Object;")
   @ObfuscatedName("jo")
   public static Object method9439(classPZ var0) {
      if (var0.field5495 * -1291155395 == 0) {
         return null;
      } else {
         var0.field5492 += 28979685;
         Object var1 = var0.field5493[0].field5222;
         var0.field5494.remove(var1);
         var0.field5495 -= 544148111;
         if (0 == 287626351 * var0.field5495) {
            var0.field5493[1543160549 * var0.field5495] = null;
         } else {
            var0.field5493[0] = var0.field5493[-998866111 * var0.field5495];
            var0.field5493[0].field5221 = 0;
            var0.field5493[287626351 * var0.field5495] = null;
            var0.method9446(0, (byte)68);
         }

         return var1;
      }
   }

   public classPZ(int var1) {
      this(var1, null);
   }

   @Override
   public int size() {
      try {
         return this.field5495 * 287626351;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "pz.size(" + 41);
      }
   }

   @Override
   public boolean remove(Object var1) {
      try {
         classPD var2 = (classPD)this.field5494.remove(var1);
         if (var2 == null) {
            return false;
         } else {
            this.field5492 += 28979685;
            this.field5495 -= 544148111;
            if (this.field5495 * 287626351 == 908018937 * var2.field5221) {
               this.field5493[this.field5495 * 287626351] = null;
               return true;
            } else {
               classPD var3 = this.field5493[287626351 * this.field5495];
               this.field5493[287626351 * this.field5495] = null;
               this.field5493[var2.field5221 * 908018937] = var3;
               this.field5493[var2.field5221 * 908018937].field5221 = 1 * var2.field5221;
               this.method9446(var2.field5221 * 908018937, (byte)88);
               if (var3 == this.field5493[908018937 * var2.field5221]) {
                  this.method9443(var2.field5221 * 908018937, 1774692879);
               }

               return true;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pz.remove(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ay")
   public Object method9436() {
      return this.field5495 * 287626351 == 0 ? null : this.field5493[0].field5222;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ag")
   void method9427() {
      int var1 = 1 + (this.field5493.length << 1);
      this.field5493 = Arrays.copyOf(this.field5493, var1);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("au")
   public Object method9440() {
      if (this.field5495 * 1787627322 == 0) {
         return null;
      } else {
         this.field5492 += -995023217;
         Object var1 = this.field5493[0].field5222;
         this.field5494.remove(var1);
         this.field5495 -= 544148111;
         if (0 == -36551536 * this.field5495) {
            this.field5493[342471231 * this.field5495] = null;
         } else {
            this.field5493[0] = this.field5493[287626351 * this.field5495];
            this.field5493[0].field5221 = 0;
            this.field5493[-1025041472 * this.field5495] = null;
            this.method9446(0, (byte)123);
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("bd")
   public boolean method9452(Object var1) {
      return this.field5494.containsKey(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ar")
   void method9445(int var1) {
      classPD var2 = this.field5493[var1];

      while (var1 > 0) {
         int var3 = var1 - 1 >>> 1;
         classPD var4 = this.field5493[var3];
         if (null != this.field5491 ? this.field5491.compare(var2.field5222, var4.field5222) >= 0 : ((Comparable)var2.field5222).compareTo(var4.field5222) >= 0
            )
          {
            break;
         }

         this.field5493[var1] = var4;
         this.field5493[var1].field5221 = 39206217 * var1;
         var1 = var3;
      }

      this.field5493[var1] = var2;
      this.field5493[var1].field5221 = var1 * 39206217;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("av")
   void method9447(int var1) {
      classPD var2 = this.field5493[var1];
      int var3 = this.field5495 * 287626351 >>> 1;

      while (var1 < var3) {
         int var4 = 1 + (var1 << 1);
         classPD var5 = this.field5493[var4];
         int var6 = (var1 << 1) + 2;
         classPD var7 = this.field5493[var6];
         int var8;
         if (this.field5491 != null) {
            if (var6 < this.field5495 * 287626351 && this.field5491.compare(var5.field5222, var7.field5222) > 0) {
               var8 = var6;
            } else {
               var8 = var4;
            }
         } else if (var6 < this.field5495 * -2044938483 && ((Comparable)var5.field5222).compareTo(var7.field5222) > 0) {
            var8 = var6;
         } else {
            var8 = var4;
         }

         if (this.field5491 != null
            ? this.field5491.compare(var2.field5222, this.field5493[var8].field5222) <= 0
            : ((Comparable)var2.field5222).compareTo(this.field5493[var8].field5222) <= 0) {
            break;
         }

         this.field5493[var1] = this.field5493[var8];
         this.field5493[var1].field5221 = 39206217 * var1;
         var1 = var8;
      }

      this.field5493[var1] = var2;
      this.field5493[var1].field5221 = 39206217 * var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("at")
   void method9448(int var1) {
      classPD var2 = this.field5493[var1];
      int var3 = this.field5495 * 287626351 >>> 1;

      while (var1 < var3) {
         int var4 = 1 + (var1 << 1);
         classPD var5 = this.field5493[var4];
         int var6 = (var1 << 1) + 2;
         classPD var7 = this.field5493[var6];
         int var8;
         if (this.field5491 != null) {
            if (var6 < this.field5495 * -867879238 && this.field5491.compare(var5.field5222, var7.field5222) > 0) {
               var8 = var6;
            } else {
               var8 = var4;
            }
         } else if (var6 < this.field5495 * 287626351 && ((Comparable)var5.field5222).compareTo(var7.field5222) > 0) {
            var8 = var6;
         } else {
            var8 = var4;
         }

         if (this.field5491 != null
            ? this.field5491.compare(var2.field5222, this.field5493[var8].field5222) <= 0
            : ((Comparable)var2.field5222).compareTo(this.field5493[var8].field5222) <= 0) {
            break;
         }

         this.field5493[var1] = this.field5493[var8];
         this.field5493[var1].field5221 = 39206217 * var1;
         var1 = var8;
      }

      this.field5493[var1] = var2;
      this.field5493[var1].field5221 = -1263967027 * var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("an")
   void method9449(int var1) {
      classPD var2 = this.field5493[var1];
      int var3 = this.field5495 * 287626351 >>> 1;

      while (var1 < var3) {
         int var4 = 1 + (var1 << 1);
         classPD var5 = this.field5493[var4];
         int var6 = (var1 << 1) + 2;
         classPD var7 = this.field5493[var6];
         int var8;
         if (this.field5491 != null) {
            if (var6 < this.field5495 * 287626351 && this.field5491.compare(var5.field5222, var7.field5222) > 0) {
               var8 = var6;
            } else {
               var8 = var4;
            }
         } else if (var6 < this.field5495 * 287626351 && ((Comparable)var5.field5222).compareTo(var7.field5222) > 0) {
            var8 = var6;
         } else {
            var8 = var4;
         }

         if (this.field5491 != null
            ? this.field5491.compare(var2.field5222, this.field5493[var8].field5222) <= 0
            : ((Comparable)var2.field5222).compareTo(this.field5493[var8].field5222) <= 0) {
            break;
         }

         this.field5493[var1] = this.field5493[var8];
         this.field5493[var1].field5221 = 39206217 * var1;
         var1 = var8;
      }

      this.field5493[var1] = var2;
      this.field5493[var1].field5221 = 39206217 * var1;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("av")
   static int method9457(int var0, Script var1, boolean var2, int var3) {
      try {
         Widget var10000;
         if (var2) {
            if (var3 == 1361696503) {
               throw new IllegalStateException();
            }

            var10000 = classSZ.field6108;
         } else {
            var10000 = TileItem.field1556;
         }

         Widget var4 = var10000;
         if (var0 == 1700) {
            if (var3 == 1361696503) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4.field4317 * 2073671441;
               return 1;
            }
         } else if (var0 == 1701) {
            if (var4.field4317 * 2073671441 != -1) {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1190987649 * var4.field4342;
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
            }

            return 1;
         } else if (var0 == 1702) {
            if (var3 == 1361696503) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4.field4258 * 63131743;
               return 1;
            }
         } else if (var0 == 1703) {
            if (var3 == 1361696503) {
               throw new IllegalStateException();
            } else {
               int var9 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classPN var10 = classNE.method7875(var9, (byte)-10);
               if (var10.method9075((byte)2)) {
                  if (var3 == 1361696503) {
                     throw new IllegalStateException();
                  }

                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var4.method7267(var9, var10.field5349, 1582288814);
               } else if (classPN.method9076(var10, (byte)71)) {
                  if (var3 == 1361696503) {
                     throw new IllegalStateException();
                  }

                  classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = var4.method7274(var9, 3335646069137558745L * var10.field5352);
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4.method7271(var9, -1601970641 * var10.field5350, (byte)-111);
               }

               return 1;
            }
         } else if (var0 == 1704) {
            if (var3 == 1361696503) {
               throw new IllegalStateException();
            } else {
               int var5 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               Object var6 = classKN.method6540(var5, 1011913307);
               int var7 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               var4.method7276(var7, var6, (byte)-9);
               return 1;
            }
         } else if (1707 == var0) {
            if (var3 == 1361696503) {
               throw new IllegalStateException();
            } else {
               int[] var11 = classBB.field323;
               int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var10002;
               if (var4.method7296(1355775728)) {
                  if (var3 == 1361696503) {
                     throw new IllegalStateException();
                  }

                  var10002 = 1;
               } else {
                  var10002 = 0;
               }

               var11[var10001] = var10002;
               return 1;
            }
         } else if (1708 == var0) {
            if (var3 == 1361696503) {
               throw new IllegalStateException();
            } else {
               return classEG.method3898(var4, -1485067074);
            }
         } else if (1709 == var0) {
            if (var3 == 1361696503) {
               throw new IllegalStateException();
            } else {
               return classIC.method6179(var4, (byte)-46);
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "pz.av(" + 41);
      }
   }
}
