import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ws")
public class classWS extends classWT {
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("af")
   IndexDataBase field6771;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ae")
   IndexDataBase field6769;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   public static final int field6772 = 17;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("az")
   IndexDataBase field6770;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod629() {
      int var1 = 0;

      for (MidiRequest var3 : classNP.field4696) {
         if (var3 != null && var3.field4639.field4749 * 917328323 > 1 && var3.field4639.method8087((short)26640)) {
            classWT.method12694(this, "Attempted to load patches of already loading midiplayer!", (byte)52);
            return true;
         }

         if (null != var3 && !var3.field4642) {
            try {
               if (var3.field4640 != null && var3.field4630 * 1942111947 != -1 && var3.field4628 * -637815256 != -1) {
                  if (var3.field4638 == null) {
                     var3.field4638 = classNO.method7987(var3.field4640, 1942111947 * var3.field4630, 450234221 * var3.field4628);
                     if (null == var3.field4638) {
                        continue;
                     }
                  }

                  if (null == var3.field4637) {
                     var3.field4637 = new classAT(this.field6770, this.field6769);
                  }

                  if (classNU.method8058(var3.field4639, var3.field4638, this.field6771, var3.field4637, 1766023070)) {
                     var1++;
                     var3.field4642 = true;
                     var3.field4639.method8060(-175382759);
                  }
               } else {
                  var1++;
               }
            } catch (Exception var5) {
               classAAU.method270(null, var5, (byte)81);
               classWT.method12694(this, var5.getMessage(), (byte)-13);
               return true;
            }
         } else {
            var1++;
         }
      }

      return var1 == classNP.field4696.size();
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod627(byte var1) {
      try {
         int var2 = 0;
         Iterator var3 = classNP.field4696.iterator();

         while (var3.hasNext()) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            MidiRequest var4 = (MidiRequest)var3.next();
            if (var4 != null) {
               if (var1 <= 1) {
                  throw new IllegalStateException();
               }

               if (var4.field4639.field4749 * 917328323 > 1) {
                  if (var1 <= 1) {
                     throw new IllegalStateException();
                  }

                  if (var4.field4639.method8087((short)16662)) {
                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }

                     classWT.method12694(this, "Attempted to load patches of already loading midiplayer!", (byte)68);
                     return true;
                  }
               }
            }

            if (null != var4) {
               if (!var4.field4642) {
                  try {
                     if (var4.field4640 != null) {
                        if (var1 <= 1) {
                           throw new IllegalStateException();
                        }

                        if (var4.field4630 * 1942111947 != -1) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           if (var4.field4628 * 450234221 != -1) {
                              if (var4.field4638 == null) {
                                 if (var1 <= 1) {
                                    throw new IllegalStateException();
                                 }

                                 var4.field4638 = classNO.method7987(var4.field4640, 1942111947 * var4.field4630, 450234221 * var4.field4628);
                                 if (null == var4.field4638) {
                                    if (var1 <= 1) {
                                       throw new IllegalStateException();
                                    }
                                    continue;
                                 }
                              }

                              if (null == var4.field4637) {
                                 if (var1 <= 1) {
                                    throw new IllegalStateException();
                                 }

                                 var4.field4637 = new classAT(this.field6770, this.field6769);
                              }

                              if (classNU.method8058(var4.field4639, var4.field4638, this.field6771, var4.field4637, 1762295839)) {
                                 if (var1 <= 1) {
                                    throw new IllegalStateException();
                                 }

                                 var2++;
                                 var4.field4642 = true;
                                 var4.field4639.method8060(-2040324162);
                              }
                              continue;
                           }

                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }
                        }
                     }

                     var2++;
                     continue;
                  } catch (Exception var6) {
                     classAAU.method270(null, var6, (byte)92);
                     classWT.method12694(this, var6.getMessage(), (byte)-87);
                     return true;
                  }
               }

               if (var1 <= 1) {
                  throw new IllegalStateException();
               }
            }

            var2++;
         }

         if (var2 != classNP.field4696.size()) {
            return false;
         } else if (var1 <= 1) {
            throw new IllegalStateException();
         } else {
            return true;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ws.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   public boolean vmethod628() {
      int var1 = 0;

      for (MidiRequest var3 : classNP.field4696) {
         if (var3 != null && var3.field4639.field4749 * 1777782214 > 1 && var3.field4639.method8087((short)25466)) {
            classWT.method12694(this, "Attempted to load patches of already loading midiplayer!", (byte)32);
            return true;
         }

         if (null != var3 && !var3.field4642) {
            try {
               if (var3.field4640 != null && var3.field4630 * 1942111947 != -1 && var3.field4628 * 450234221 != -1) {
                  if (var3.field4638 == null) {
                     var3.field4638 = classNO.method7987(var3.field4640, -1470733929 * var3.field4630, 450234221 * var3.field4628);
                     if (null == var3.field4638) {
                        continue;
                     }
                  }

                  if (null == var3.field4637) {
                     var3.field4637 = new classAT(this.field6770, this.field6769);
                  }

                  if (classNU.method8058(var3.field4639, var3.field4638, this.field6771, var3.field4637, 1597325418)) {
                     var1++;
                     var3.field4642 = true;
                     var3.field4639.method8060(-731841728);
                  }
               } else {
                  var1++;
               }
            } catch (Exception var5) {
               classAAU.method270(null, var5, (byte)78);
               classWT.method12694(this, var5.getMessage(), (byte)1);
               return true;
            }
         } else {
            var1++;
         }
      }

      return var1 == classNP.field4696.size();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzs;Ljava/lang/String;)Lzs;")
   @ObfuscatedName("pe")
   public static ScriptEventBuilder method12681(ScriptEventBuilder var0, String var1) {
      if (var0 == null) {
         var0.method14233();
      }

      var0.field7265 = var1;
      return var0;
   }

   public classWS(classWT var1, IndexDataBase var2, IndexDataBase var3, IndexDataBase var4) {
      super(var1);
      this.field6771 = var2;
      this.field6769 = var3;
      this.field6770 = var4;
      this.field6775 = "LoadSongTask";
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod626() {
      int var1 = 0;

      for (MidiRequest var3 : classNP.field4696) {
         if (var3 != null && var3.field4639.field4749 * 917328323 > 1 && var3.field4639.method8087((short)18485)) {
            classWT.method12694(this, "Attempted to load patches of already loading midiplayer!", (byte)-81);
            return true;
         }

         if (null != var3 && !var3.field4642) {
            try {
               if (var3.field4640 != null && var3.field4630 * 1942111947 != -1 && var3.field4628 * -630372102 != -1) {
                  if (var3.field4638 == null) {
                     var3.field4638 = classNO.method7987(var3.field4640, -1924128385 * var3.field4630, 529751017 * var3.field4628);
                     if (null == var3.field4638) {
                        continue;
                     }
                  }

                  if (null == var3.field4637) {
                     var3.field4637 = new classAT(this.field6770, this.field6769);
                  }

                  if (classNU.method8058(var3.field4639, var3.field4638, this.field6771, var3.field4637, 1625746580)) {
                     var1++;
                     var3.field4642 = true;
                     var3.field4639.method8060(654712694);
                  }
               } else {
                  var1++;
               }
            } catch (Exception var5) {
               classAAU.method270(null, var5, (byte)6);
               classWT.method12694(this, var5.getMessage(), (byte)-65);
               return true;
            }
         } else {
            var1++;
         }
      }

      return var1 == classNP.field4696.size();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod630() {
      int var1 = 0;

      for (MidiRequest var3 : classNP.field4696) {
         if (var3 != null && var3.field4639.field4749 * 917328323 > 1 && var3.field4639.method8087((short)27754)) {
            classWT.method12694(this, "Attempted to load patches of already loading midiplayer!", (byte)2);
            return true;
         }

         if (null != var3 && !var3.field4642) {
            try {
               if (var3.field4640 != null && var3.field4630 * 1942111947 != -1 && var3.field4628 * 450234221 != -1) {
                  if (var3.field4638 == null) {
                     var3.field4638 = classNO.method7987(var3.field4640, 1942111947 * var3.field4630, 450234221 * var3.field4628);
                     if (null == var3.field4638) {
                        continue;
                     }
                  }

                  if (null == var3.field4637) {
                     var3.field4637 = new classAT(this.field6770, this.field6769);
                  }

                  if (classNU.method8058(var3.field4639, var3.field4638, this.field6771, var3.field4637, 1978381900)) {
                     var1++;
                     var3.field4642 = true;
                     var3.field4639.method8060(-1399596704);
                  }
               } else {
                  var1++;
               }
            } catch (Exception var5) {
               classAAU.method270(null, var5, (byte)21);
               classWT.method12694(this, var5.getMessage(), (byte)-17);
               return true;
            }
         } else {
            var1++;
         }
      }

      return var1 == classNP.field4696.size();
   }
}
