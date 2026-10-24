import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xh")
public class classXH {
   @ObfuscatedSignature(descriptor = "[Lxg;")
   @ObfuscatedName("az")
   classXG[] field6867 = new classXG[0];
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field6868 = 7;

   @ObfuscatedSignature(descriptor = "(Lxs;I)V")
   @ObfuscatedName("ay")
   public void method12829(PacketBuffer var1, int var2) {
      while (702114061 * var1.field6953 < var2) {
         int var3 = var1.method13095(-346779531);
         int var4 = classXY.method13113(var1, (byte)1);
         Object var5 = null;
         switch (classBW.method1266(var4, (byte)-109).field7105 * -1036606364) {
            case 2:
               var5 = classXY.method13113(var1, (byte)1);
               break;
            case 3:
               var5 = classXY.method13115(var1, 1604974912);
               break;
            case 4:
               var5 = var1.method13127(5651073);
         }

         if (var3 < this.field6867.length && this.field6867[var3] != null && -1472217620 * this.field6867[var3].field6861 == classIZ.method6383(var4, (byte)75)
            )
          {
            int var6 = classCF.method1456(var4, (byte)23);
            switch (classJW.method6452(var4, (byte)101).field4859 * 1805222340) {
               case 0:
                  this.field6867[var3].field6864[var6] = var5;
                  break;
               case 1:
                  this.field6867[var3].field6859[var6] = var5;
                  break;
               case 2:
                  this.field6867[var3].field6862[var6] = var5;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   public void method12824(byte var1) {
      try {
         this.field6867 = new classXG[0];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xh.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;II)V")
   @ObfuscatedName("af")
   public void method12826(PacketBuffer var1, int var2, int var3) {
      try {
         while (702114061 * var1.field6953 < var2) {
            if (var3 == 1156537225) {
               throw new IllegalStateException();
            }

            int var4 = var1.method13095(-346779531);
            if (var4 >= this.field6867.length) {
               if (var3 == 1156537225) {
                  throw new IllegalStateException();
               }

               this.field6867 = Arrays.copyOf(this.field6867, 1 + var4);
            }

            this.method12833(var4, var1, 1762245209);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xh.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILxs;I)V")
   @ObfuscatedName("ab")
   void method12833(int var1, PacketBuffer var2, int var3) {
      try {
         int var4 = var2.method13152(434878691);
         if (-1 == var4) {
            if (this.field6867[var1] != null) {
               if (var3 == 684991541) {
                  throw new IllegalStateException();
               }

               this.method12842(-38539607 * this.field6867[var1].field6861, var1, -1L);
               this.field6867[var1] = null;
            }
         } else {
            long var5;
            boolean var7;
            label64: {
               var5 = classXY.method13115(var2, 2053626902);
               var7 = false;
               if (this.field6867[var1] != null) {
                  if (var3 == 684991541) {
                     throw new IllegalStateException();
                  }

                  if (var4 == -38539607 * this.field6867[var1].field6861) {
                     if (var3 == 684991541) {
                        throw new IllegalStateException();
                     }

                     if (-5931402327633732889L * this.field6867[var1].field6863 == var5) {
                        break label64;
                     }
                  }
               }

               if (this.field6867[var1] != null) {
                  if (var3 == 684991541) {
                     throw new IllegalStateException();
                  }

                  this.method12842(this.field6867[var1].field6861 * -38539607, var1, -1L);
                  this.field6867[var1] = null;
               }

               this.field6867[var1] = new classXG(var1, var4, var5);
               var7 = true;
            }

            classPC var8 = WorldView.method3733(var4, -1060823799);
            classTJ.method10904(var8.field5213, this.field6867[var1].field6859, var2, 465617240);
            classTJ.method10904(var8.field5212, this.field6867[var1].field6862, var2, -1640027915);
            classTJ.method10904(var8.field5211, this.field6867[var1].field6864, var2, 1915877480);
            if (var7) {
               if (var3 == 684991541) {
                  throw new IllegalStateException();
               }

               this.method12842(var4, var1, var5);
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "xh.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lxg;")
   @ObfuscatedName("ag")
   public classXG method12836(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 == -2059690683) {
               throw new IllegalStateException();
            }

            if (var1 < this.field6867.length) {
               if (var2 == -2059690683) {
                  throw new IllegalStateException();
               }

               return this.field6867[var1];
            }
         }

         return null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xh.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lxg;")
   @ObfuscatedName("ar")
   public classXG method12837(int var1) {
      return var1 >= 0 && var1 < this.field6867.length ? this.field6867[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "(Lxs;II)V")
   @ObfuscatedName("ae")
   public void method12830(PacketBuffer var1, int var2, int var3) {
      try {
         while (702114061 * var1.field6953 < var2) {
            if (var3 >= 111634418) {
               throw new IllegalStateException();
            }

            int var4 = var1.method13095(-346779531);
            int var5 = classXY.method13113(var1, (byte)1);
            Object var6 = null;
            switch (classBW.method1266(var5, (byte)18).field7105 * -900901303) {
               case 2:
                  var6 = classXY.method13113(var1, (byte)1);
                  break;
               case 3:
                  var6 = classXY.method13115(var1, -381606880);
                  break;
               case 4:
                  var6 = var1.method13127(-996599750);
            }

            if (var4 < this.field6867.length) {
               if (var3 >= 111634418) {
                  throw new IllegalStateException();
               }

               if (this.field6867[var4] == null) {
                  if (var3 >= 111634418) {
                     throw new IllegalStateException();
                  }
               } else if (-38539607 * this.field6867[var4].field6861 == classIZ.method6383(var5, (byte)85)) {
                  int var7 = classCF.method1456(var5, (byte)67);
                  switch (classJW.method6452(var5, (byte)-33).field4859 * 33726487) {
                     case 0:
                        this.field6867[var4].field6864[var7] = var6;
                        break;
                     case 1:
                        this.field6867[var4].field6859[var7] = var6;
                        break;
                     case 2:
                        this.field6867[var4].field6862[var7] = var6;
                  }
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "xh.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIJ)V")
   @ObfuscatedName("ax")
   void method12842(int var1, int var2, long var3) {
      try {
         ScriptEvent var5 = classCA.method1393(85, -1495127640).method14195(new Object[]{var1, var2, var3}, (byte)2).method14226((byte)102);
         classSZ.method10771(var5, -605675329);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xh.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;I)V")
   @ObfuscatedName("al")
   public void method12827(PacketBuffer var1, int var2) {
      while (702114061 * var1.field6953 < var2) {
         int var3 = var1.method13095(-346779531);
         if (var3 >= this.field6867.length) {
            this.field6867 = Arrays.copyOf(this.field6867, 1 + var3);
         }

         this.method12833(var3, var1, 243159536);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;I)V")
   @ObfuscatedName("aj")
   public void method12828(PacketBuffer var1, int var2) {
      while (702114061 * var1.field6953 < var2) {
         int var3 = var1.method13095(-346779531);
         if (var3 >= this.field6867.length) {
            this.field6867 = Arrays.copyOf(this.field6867, 1 + var3);
         }

         this.method12833(var3, var1, -1300696495);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   public void method12825() {
      this.field6867 = new classXG[0];
   }

   @ObfuscatedSignature(descriptor = "(Lxs;I)V")
   @ObfuscatedName("aq")
   public void method12831(PacketBuffer var1, int var2) {
      while (702114061 * var1.field6953 < var2) {
         int var3 = var1.method13095(-346779531);
         int var4 = classXY.method13113(var1, (byte)1);
         Object var5 = null;
         switch (classBW.method1266(var4, (byte)-20).field7105 * -900901303) {
            case 2:
               var5 = classXY.method13113(var1, (byte)1);
               break;
            case 3:
               var5 = classXY.method13115(var1, 2025992345);
               break;
            case 4:
               var5 = var1.method13127(141736018);
         }

         if (var3 < this.field6867.length && this.field6867[var3] != null && -38539607 * this.field6867[var3].field6861 == classIZ.method6383(var4, (byte)71)) {
            int var6 = classCF.method1456(var4, (byte)24);
            switch (classJW.method6452(var4, (byte)-61).field4859 * 33726487) {
               case 0:
                  this.field6867[var3].field6864[var6] = var5;
                  break;
               case 1:
                  this.field6867[var3].field6859[var6] = var5;
                  break;
               case 2:
                  this.field6867[var3].field6862[var6] = var5;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;I)V")
   @ObfuscatedName("ad")
   public void method12832(PacketBuffer var1, int var2) {
      while (702114061 * var1.field6953 < var2) {
         int var3 = var1.method13095(-346779531);
         int var4 = classXY.method13113(var1, (byte)1);
         Object var5 = null;
         switch (classBW.method1266(var4, (byte)-76).field7105 * -900901303) {
            case 2:
               var5 = classXY.method13113(var1, (byte)1);
               break;
            case 3:
               var5 = classXY.method13115(var1, -428215676);
               break;
            case 4:
               var5 = var1.method13127(1455239792);
         }

         if (var3 < this.field6867.length && this.field6867[var3] != null && -38539607 * this.field6867[var3].field6861 == classIZ.method6383(var4, (byte)69)) {
            int var6 = classCF.method1456(var4, (byte)36);
            switch (classJW.method6452(var4, (byte)-9).field4859 * 33726487) {
               case 0:
                  this.field6867[var3].field6864[var6] = var5;
                  break;
               case 1:
                  this.field6867[var3].field6859[var6] = var5;
                  break;
               case 2:
                  this.field6867[var3].field6862[var6] = var5;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILxs;)V")
   @ObfuscatedName("ap")
   void method12834(int var1, PacketBuffer var2) {
      int var3 = var2.method13152(355879082);
      if (-1 == var3) {
         if (this.field6867[var1] != null) {
            this.method12842(-38539607 * this.field6867[var1].field6861, var1, -1L);
            this.field6867[var1] = null;
         }
      } else {
         long var4 = classXY.method13115(var2, 2009674222);
         boolean var6 = false;
         if (this.field6867[var1] == null
            || var3 != -38539607 * this.field6867[var1].field6861
            || -5931402327633732889L * this.field6867[var1].field6863 != var4) {
            if (this.field6867[var1] != null) {
               this.method12842(this.field6867[var1].field6861 * -38539607, var1, -1L);
               this.field6867[var1] = null;
            }

            this.field6867[var1] = new classXG(var1, var3, var4);
            var6 = true;
         }

         classPC var7 = WorldView.method3733(var3, -2119284705);
         classTJ.method10904(var7.field5213, this.field6867[var1].field6859, var2, -1640529127);
         classTJ.method10904(var7.field5212, this.field6867[var1].field6862, var2, 391246397);
         classTJ.method10904(var7.field5211, this.field6867[var1].field6864, var2, -1581915059);
         if (var6) {
            this.method12842(var3, var1, var4);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([I[Ljava/lang/Object;Lxs;)V")
   @ObfuscatedName("at")
   static void method12845(int[] var0, Object[] var1, PacketBuffer var2) {
      for (int var3 = 0; var3 < var0.length; var3++) {
         switch (FriendContainer.method10175(var0[var3], 1275371533).field7105 * -900901303) {
            case 2:
               var1[var3] = var2.method13177((byte)-92);
               break;
            case 3:
               var1[var3] = classXY.method13115(var2, -151717911);
               break;
            case 4:
               var1[var3] = var2.method13127(60947993);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILxs;)V")
   @ObfuscatedName("au")
   void method12835(int var1, PacketBuffer var2) {
      int var3 = var2.method13152(305636474);
      if (-1 == var3) {
         if (this.field6867[var1] != null) {
            this.method12842(1664380055 * this.field6867[var1].field6861, var1, -1L);
            this.field6867[var1] = null;
         }
      } else {
         long var4 = classXY.method13115(var2, 938244963);
         boolean var6 = false;
         if (this.field6867[var1] == null
            || var3 != -225244800 * this.field6867[var1].field6861
            || -5931402327633732889L * this.field6867[var1].field6863 != var4) {
            if (this.field6867[var1] != null) {
               this.method12842(this.field6867[var1].field6861 * -38539607, var1, -1L);
               this.field6867[var1] = null;
            }

            this.field6867[var1] = new classXG(var1, var3, var4);
            var6 = true;
         }

         classPC var7 = WorldView.method3733(var3, -428530804);
         classTJ.method10904(var7.field5213, this.field6867[var1].field6859, var2, -650852122);
         classTJ.method10904(var7.field5212, this.field6867[var1].field6862, var2, -2090636871);
         classTJ.method10904(var7.field5211, this.field6867[var1].field6864, var2, 724636172);
         if (var6) {
            this.method12842(var3, var1, var4);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(J)Lxg;")
   @ObfuscatedName("aw")
   public classXG method12839(long var1) {
      classXG[] var3 = this.field6867;

      for (int var4 = 0; var4 < var3.length; var4++) {
         classXG var5 = var3[var4];
         if (null != var5 && -5931402327633732889L * var5.field6863 == var1) {
            return var5;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(IIJ)V")
   @ObfuscatedName("ak")
   void method12843(int var1, int var2, long var3) {
      ScriptEvent var5 = classCA.method1393(85, -1964057342).method14195(new Object[]{var1, var2, var3}, (byte)2).method14226((byte)24);
      classSZ.method10771(var5, -1505457920);
   }

   @ObfuscatedSignature(descriptor = "(Lxh;J)Lxg;")
   @ObfuscatedName("cg")
   public static classXG method12840(classXH var0, long var1) {
      if (var0 == null) {
         var0.method12841(var1);
      }

      try {
         classXG[] var3 = var0.field6867;

         for (int var4 = 0; var4 < var3.length; var4++) {
            classXG var5 = var3[var4];
            if (null != var5 && -5931402327633732889L * var5.field6863 == var1) {
               return var5;
            }
         }

         return null;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xh.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIJ)V")
   @ObfuscatedName("av")
   void method12844(int var1, int var2, long var3) {
      ScriptEvent var5 = classCA.method1393(85, -1905575321).method14195(new Object[]{var1, var2, var3}, (byte)2).method14226((byte)25);
      classSZ.method10771(var5, -908918484);
   }

   @ObfuscatedSignature(descriptor = "(I)Lxg;")
   @ObfuscatedName("ai")
   public classXG method12838(int var1) {
      return var1 >= 0 && var1 < this.field6867.length ? this.field6867[var1] : null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([I[Ljava/lang/Object;Lxs;)V")
   @ObfuscatedName("an")
   static void method12846(int[] var0, Object[] var1, PacketBuffer var2) {
      for (int var3 = 0; var3 < var0.length; var3++) {
         switch (FriendContainer.method10175(var0[var3], 244796811).field7105 * -900901303) {
            case 2:
               var1[var3] = var2.method13177((byte)-91);
               break;
            case 3:
               var1[var3] = classXY.method13115(var2, 1333766301);
               break;
            case 4:
               var1[var3] = var2.method13127(-1345780181);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(J)Lxg;")
   @ObfuscatedName("as")
   public classXG method12841(long var1) {
      try {
         classXG[] var3 = this.field6867;

         for (int var4 = 0; var4 < var3.length; var4++) {
            classXG var5 = var3[var4];
            if (null != var5 && -5931402327633732889L * var5.field6863 == var1) {
               return var5;
            }
         }

         return null;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xh.as(" + ')');
      }
   }
}
