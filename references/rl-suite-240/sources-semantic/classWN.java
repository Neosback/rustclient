import java.util.Arrays;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wn")
public class classWN extends classWT {
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("hs")
   static classVP field6757;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("at")
   static IndexDataBase field6756;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6755 = 2;

   public classWN(classWT var1) {
      super(var1);
      this.field6775 = "AddRequestTask";
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod627(byte var1) {
      try {
         while (!classNP.field4695.isEmpty()) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            MidiRequest var2 = (MidiRequest)classNP.field4695.peek();
            if (null == var2) {
               if (var1 <= 1) {
                  throw new IllegalStateException();
               }

               classNP.field4695.pop();
            } else {
               var2.field4639 = this.method12673(1209653889);
               classNP.field4696.add(var2);
               classNP.field4695.pop();
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wn.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lnu;")
   @ObfuscatedName("as")
   classNU method12673(int var1) {
      try {
         classNU var2 = null;
         Iterator var3 = classNP.field4702.iterator();

         while (var3.hasNext()) {
            if (var1 != 1209653889) {
               throw new IllegalStateException();
            }

            classNU var4 = (classNU)var3.next();
            if (null != var4) {
               if (var1 != 1209653889) {
                  throw new IllegalStateException();
               }

               if (null != var2) {
                  if (var1 != 1209653889) {
                     throw new IllegalStateException();
                  }

                  if (917328323 * var2.field4749 <= 917328323 * var4.field4749) {
                     if (var1 != 1209653889) {
                        throw new IllegalStateException();
                     }

                     if (classNU.method8052(var4, (byte)-124) != 0) {
                        continue;
                     }

                     if (var1 != 1209653889) {
                        throw new IllegalStateException();
                     }

                     if (!classNU.method8085(var4, (byte)10)) {
                        continue;
                     }

                     if (var1 != 1209653889) {
                        throw new IllegalStateException();
                     }
                  }
               }

               var2 = var4;
            }
         }

         if (var2 != null) {
            if (var1 != 1209653889) {
               throw new IllegalStateException();
            }

            var2.field4749 += 16852715;
            if (classNU.method8052(var2, (byte)-108) == 0) {
               if (var1 != 1209653889) {
                  throw new IllegalStateException();
               }

               if (classNU.method8085(var2, (byte)10)) {
                  if (var1 != 1209653889) {
                     throw new IllegalStateException();
                  }

                  var2.method8079((byte)37);
                  var2.method8066((byte)0);
                  var2.method8048(0, -1404437921);
               }
            }
         }

         return var2;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "wn.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llz;)[I")
   @ObfuscatedName("re")
   public static int[] method12675(classLZ var0) {
      return Arrays.copyOf(var0.field4443, -1725791933 * var0.field4447);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   public boolean vmethod628() {
      while (!classNP.field4695.isEmpty()) {
         MidiRequest var1 = (MidiRequest)classNP.field4695.peek();
         if (null == var1) {
            classNP.field4695.pop();
         } else {
            var1.field4639 = this.method12673(1209653889);
            classNP.field4696.add(var1);
            classNP.field4695.pop();
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod629() {
      while (!classNP.field4695.isEmpty()) {
         MidiRequest var1 = (MidiRequest)classNP.field4695.peek();
         if (null == var1) {
            classNP.field4695.pop();
         } else {
            var1.field4639 = this.method12673(1209653889);
            classNP.field4696.add(var1);
            classNP.field4695.pop();
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(Luz;Luz;B)Luz;")
   @ObfuscatedName("ab")
   public static final classUZ method12676(classUZ var0, classUZ var1, byte var2) {
      try {
         classUZ var3 = WorldMapData.method5868(var0, (byte)-27);
         var3.method11834(var1, -291135846);
         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wn.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod630() {
      while (!classNP.field4695.isEmpty()) {
         MidiRequest var1 = (MidiRequest)classNP.field4695.peek();
         if (null == var1) {
            classNP.field4695.pop();
         } else {
            var1.field4639 = this.method12673(1209653889);
            classNP.field4696.add(var1);
            classNP.field4695.pop();
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "()Lnu;")
   @ObfuscatedName("ax")
   classNU method12674() {
      classNU var1 = null;

      for (classNU var3 : classNP.field4702) {
         if (null != var3
            && (
               null == var1
                  || 917328323 * var1.field4749 > 917328323 * var3.field4749
                  || classNU.method8052(var3, (byte)-36) == 0 && classNU.method8085(var3, (byte)10)
            )) {
            var1 = var3;
         }
      }

      if (var1 != null) {
         var1.field4749 += 16852715;
         if (classNU.method8052(var1, (byte)-44) == 0 && classNU.method8085(var1, (byte)10)) {
            var1.method8079((byte)26);
            var1.method8066((byte)0);
            var1.method8048(0, -1267663706);
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod626() {
      while (!classNP.field4695.isEmpty()) {
         MidiRequest var1 = (MidiRequest)classNP.field4695.peek();
         if (null == var1) {
            classNP.field4695.pop();
         } else {
            var1.field4639 = this.method12673(1209653889);
            classNP.field4696.add(var1);
            classNP.field4695.pop();
         }
      }

      return true;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;Z)V")
   @ObfuscatedName("lk")
   public static void method12677(ClientPreferences var0, boolean var1) {
      if (var0 == null) {
         var0.method2798();
      }

      var0.field1351 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(II)Lzf;")
   @ObfuscatedName("az")
   public static classZF method12672(int var0, int var1) {
      try {
         classZF[] var2 = new classZF[]{classZF.field7176, classZF.field7178, classZF.field7177};
         classZF[] var3 = var2;

         for (int var4 = 0; var4 < var3.length; var4++) {
            if (var1 <= 1818461594) {
               throw new IllegalStateException();
            }

            classZF var5 = var3[var4];
            if (-1382377909 * var5.field7179 == var0) {
               if (var1 <= 1818461594) {
                  throw new IllegalStateException();
               }

               return var5;
            }
         }

         return null;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wn.az(" + ')');
      }
   }
}
