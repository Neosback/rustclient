import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jz")
public final class NodeCache implements net.runelite.api.NodeCache {
   @ObfuscatedName("zm")
   public float field3499 = 0.0F;
   @ObfuscatedName("af")
   int field3501;
   @ObfuscatedName("km")
   public int field3505;
   @ObfuscatedSignature(descriptor = "Lvj;")
   @ObfuscatedName("az")
   classVJ field3504 = new classVJ();
   @ObfuscatedName("ae")
   int field3503;
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("ab")
   IterableHashTable field3500;
   @ObfuscatedSignature(descriptor = "Lnz;")
   @ObfuscatedName("ag")
   classNZ field3502 = new classNZ();

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("ac")
   public void method6461(long var1) {
      classVJ var3 = (classVJ)this.field3500.method13637(var1);
      if (var3 != null) {
         var3.vmethod398();
         var3.method12035();
         this.field3503++;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("be")
   public void method6474() {
      this.field3505 = this.field3501;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   public void method6467() {
      this.field3502.method8296();
      this.field3500.method13623();
      this.field3504 = new classVJ();
      this.field3503 = this.field3501;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   public void method6468() {
      this.field3502.method8296();
      this.field3500.method13623();
      this.field3504 = new classVJ();
      this.field3503 = this.field3501;
      this.method6475();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljz;)V")
   @ObfuscatedName("ju")
   public static void method6469(NodeCache var0) {
      var0.field3502.method8296();
      var0.field3500.method13623();
      var0.field3504 = new classVJ();
      var0.field3503 = var0.field3501;
   }

   @ObfuscatedSignature(descriptor = "(Lvj;J)V")
   @ObfuscatedName("ae")
   public void method6464(classVJ var1, long var2) {
      if (this.field3503 == 0) {
         classVJ var4 = this.field3502.method8303();
         var4.vmethod398();
         var4.method12035();
         if (var4 == this.field3504) {
            var4 = this.field3502.method8303();
            var4.vmethod398();
            var4.method12035();
         }
      } else {
         this.field3503--;
      }

      IterableHashTable.method13620(this.field3500, var1, var2);
      this.field3502.method8302(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljz;)V")
   @ObfuscatedName("pg")
   public static void method6477(String var0, NodeCache var1) {
      synchronized (var1) {
         NodeCache var3 = var1;
         boolean var4 = var3.method6476();
         var3.field3499 = var3.field3499 * 0.92F + (var4 ? 0.07999998F : 0.0F);
         if (var4) {
            if (var3.field3499 > 0.2F) {
               client.field1028.trace("cache {} is thrashing", var0);
            }

            if (var3.field3499 > 0.9F && var3.field3501 < var3.field3505 * 8) {
               var3.method6478(var3.field3501 * 2);
               client.field1028.info("cache {} thrashing, enlarging to {} entries", var0, var3.field3501);
            }
         }

         IterableHashTable.method13640(var3.field3502, var3.field3504);
      }
   }

   public NodeCache(int var1) {
      this.field3501 = var1;
      this.field3503 = var1;
      byte var2 = 1;

      while (var2 + var2 < var1) {
         var2 += var2;
      }

      this.field3500 = new IterableHashTable(var2);
      this.method6474();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public void method6470() {
      this.field3502.method8296();
      this.field3500.method13623();
      this.field3504 = new classVJ();
      this.field3503 = this.field3501;
   }

   @ObfuscatedSignature(descriptor = "(J)Lvj;")
   @ObfuscatedName("nt")
   public classVJ method6479(long var1) {
      return method6460(this, var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("qq")
   public void method6473(int var1) {
      this.method6478(var1);
      this.field3505 = this.field3501;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcl;I)Lsp;")
   @ObfuscatedName("gm")
   public static WorldEntity method6472(classCL var0, int var1) {
      if (var0 == null) {
         var0.method1611(var1, var1);
      }

      return (WorldEntity)var0.method1588(839252882).field1698.method13637(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljz;J)V")
   @ObfuscatedName("fy")
   public static void method6462(NodeCache var0, long var1) {
      classVJ var3 = (classVJ)var0.field3500.method13637(var1);
      if (var3 != null) {
         var3.vmethod398();
         var3.method12035();
         var0.field3503++;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("nm")
   public void method6475() {
      this.field3499 = 0.0F;
      IterableHashTable.method13640(this.field3502, this.field3504);
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("af")
   public void method6463(long var1) {
      classVJ var3 = (classVJ)this.field3500.method13637(var1);
      if (var3 != null) {
         var3.vmethod398();
         var3.method12035();
         this.field3503++;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljz;)V")
   @ObfuscatedName("hg")
   public static void method6471(NodeCache var0) {
      var0.field3502.method8296();
      var0.field3500.method13623();
      var0.field3504 = new classVJ();
      var0.field3503 = var0.field3501;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("sa")
   public boolean method6476() {
      return this.field3503 <= 0 && this.field3504.method12037() == null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljz;J)Lvj;")
   @ObfuscatedName("be")
   public static classVJ method6459(NodeCache var0, long var1) {
      if (var0 == null) {
         var0.method6482(var1);
      }

      classVJ var3 = (classVJ)var0.field3500.method13637(var1);
      if (var3 != null) {
         var0.field3502.method8302(var3);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("tv")
   public void method6478(int var1) {
      if (var1 > this.field3501) {
         byte var2 = 1;

         while (var2 < var1) {
            var2 += var2;
         }

         this.field3501 = var2;
         this.reset();
         this.field3500 = new IterableHashTable(var2);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("xt")
   public static void method6480() {
      method6477("EnumDefinition_cached", EnumComposition.field4818);
      method6477("animPoseCache", Animation.field5143);
      method6477("animationCache", Animation.field5139);
      method6477("animayaPoses", Animation.field5142);
      method6477("dbRowConfigCache", DBRowConfig.field7169);
      method6477("dbTableConfigCache", classZU.field7269);
      method6477("dbTableIndexCache", client.field979);
      method6477("field4546", WorldEntityConfig.field4870);
      method6477("field4739", classOW.field5181);
      method6477("field4817", classPS.field5404);
      method6477("field4842", classPA.field5201);
      method6477("field4963", classPC.field5217);
      method6477("field4974", classPH.field5250);
      method6477("field4978", classPF.field5233);
      method6477("field844", client.field938);
      method6477("healthBarCache", HealthBarConfig.field5473);
      method6477("healthBarSpriteCache", HealthBarConfig.field5474);
      method6477("hitslpatSpriteCache", classPW.field5444);
      method6477("hitsplatCache", classPW.field5443);
      method6477("hitsplatCompCache", classPG.field5236);
      method6477("hitsplatFontCache", classPW.field5445);
      method6477("hitsplatFontCache", classOF.field4850);
      method6477("inventoryCache", classOC.field4812);
      method6477("itemModelCache", ItemComposition.field5005);
      method6477("itemSpriteCache", ItemComposition.field5006);
      method6477("items", ItemComposition.field5004);
      method6477("npcModelCache", NPCComposition.field5335);
      method6477("npcs", NPCComposition.field5334);
      method6477("objModelCacheSpawn", ObjectComposition.field4965);
      method6477("objModelDataCache", ObjectComposition.field4966);
      method6477("objects", ObjectComposition.field4964);
      method6477("paramCompositionCache", classPN.field5348);
      method6477("playerModelCache", PlayerComposition.field4098);
      method6477("scriptCache", Script.field491);
      method6477("spotanimCache", classOR.field5096);
      method6477("spotanimModelCache", classOR.field5097);
      method6477("structCompositionCache", StructComposition.field5383);
      method6477("varbits", VarbitComposition.field5416);
      method6477("varplayers", classOY.field5192);
      method6477("wecSpriteCache", WorldEntityConfig.field4871);
   }

   @ObfuscatedSignature(descriptor = "(Ljz;J)Lvj;")
   @ObfuscatedName("rz")
   public static classVJ method6460(NodeCache var0, long var1) {
      if (var0 == null) {
         var0.method6481(var1);
      }

      classVJ var3 = (classVJ)var0.field3500.method13637(var1);
      if (var3 != null) {
         var0.field3502.method8302(var3);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(J)Lvj;")
   @ObfuscatedName("az")
   public classVJ method6481(long var1) {
      return method6460(this, var1);
   }

   @ObfuscatedSignature(descriptor = "(J)Lvj;")
   @ObfuscatedName("ag")
   public classVJ method6482(long var1) {
      return method6460(this, var1);
   }

   public void reset() {
      this.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljz;Lvj;J)V")
   @ObfuscatedName("cr")
   public static void method6465(NodeCache var0, classVJ var1, long var2) {
      if (var0 == null) {
         var0.method6466(var1, var2);
      } else {
         if (var0.field3503 == 0) {
            classVJ var4 = var0.field3502.method8303();
            var4.vmethod398();
            var4.method12035();
            if (var4 == var0.field3504) {
               var4 = var0.field3502.method8303();
               var4.vmethod398();
               var4.method12035();
            }
         } else {
            var0.field3503--;
         }

         IterableHashTable.method13620(var0.field3500, var1, var2);
         var0.field3502.method8302(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvj;J)V")
   @ObfuscatedName("aa")
   public void method6466(classVJ var1, long var2) {
      if (this.field3501 == 0) {
         classVJ var4 = this.field3502.method8305();
         var4.method12135();
         var4.method12035();
         if (var4 == this.field3504) {
            var4 = this.field3502.method8304();
            var4.vmethod401();
            var4.method12035();
         }
      } else {
         this.field3503--;
      }

      IterableHashTable.method13620(this.field3500, var1, var2);
      this.field3502.method8300(var1);
   }
}
