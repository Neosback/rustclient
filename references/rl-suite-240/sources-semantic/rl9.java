import net.runelite.api.ParamHolder;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl9")
public interface rl9 extends ParamHolder {
   default int getIntValue(int var1) {
      Node var2 = this.method10170(var1);
      if (var2 != null) {
         IntegerNode var5 = (IntegerNode)var2;
         return var5.getValue();
      } else {
         classPN var3 = classOE.field4841.method2253(var1);
         classYY var4 = classYY.method13835(var3.field5351 * 1468976603);
         if (var4 != classYY.field7109) {
            throw new IllegalArgumentException("trying to get int from " + var4 + " param");
         } else {
            return var3.method9086();
         }
      }
   }

   default String getStringValue(int var1) {
      Node var2 = this.method10170(var1);
      if (var2 != null) {
         classVE var5 = (classVE)var2;
         return (String)var5.method12013();
      } else {
         classPN var3 = classOE.field4841.method2253(var1);
         classYY var4 = classYY.method13835(var3.field5351 * 1468976603);
         if (var4 != classYY.field7111) {
            throw new IllegalArgumentException("trying to get string from " + var4 + " param");
         } else {
            return var3.method9085();
         }
      }
   }

   default void setValue(int var1, String var2) {
      this.method10171(var1, new classVE(var2));
   }

   @ObfuscatedSignature(descriptor = "(ILvq;)V")
   @ObfuscatedName("qb")
   default void method10171(int var1, Node var2) {
      classPN var3 = classOE.field4841.method2253(var1);
      classYY var4 = classYY.method13835(var3.field5351 * 1468976603);
      if (var2 instanceof IntegerNode && var4 != classYY.field7109) {
         throw new IllegalArgumentException("trying to put int into " + var4 + " param");
      } else if (var2 instanceof classVK && var4 != classYY.field7113) {
         throw new IllegalArgumentException("trying to put long into " + var4 + " param");
      } else if (var2 instanceof classVE && var4 != classYY.field7111) {
         throw new IllegalArgumentException("trying to put string into " + var4 + " param");
      } else {
         if (this.getParams() == null) {
            this.vmethod537(new IterableHashTable(16));
         }

         this.getParams().put(var2, var1);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lvq;")
   @ObfuscatedName("zo")
   default Node method10170(int var1) {
      return this.getParams() == null ? null : (Node)this.getParams().get(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxs;I)I")
   @ObfuscatedName("km")
   static int method10168(PacketBuffer var0, int var1) {
      if (var0 == null) {
         return var0.method12986(var1);
      } else {
         int var2 = 512124769 * var0.field6925 >> 3;
         int var3 = 8 - (var0.field6925 * -653463055 & 7);
         int var4 = 0;

         for (var0.field6925 += var1 * -1278857492; var1 > var3; var3 = 8) {
            var4 += (var0.field6952[var2++] & PacketBuffer.field6924[var3]) << var1 - var3;
            var1 -= var3;
         }

         if (var3 == var1) {
            var4 += var0.field6952[var2] & PacketBuffer.field6924[var3];
         } else {
            var4 += var0.field6952[var2] >> var3 - var1 & PacketBuffer.field6924[var1];
         }

         return var4;
      }
   }

   default long getLongValue(int var1) {
      Node var2 = this.method10170(var1);
      if (var2 != null) {
         classVE var5 = (classVE)var2;
         return (Long)var5.method12013();
      } else {
         classPN var3 = classOE.field4841.method2253(var1);
         classYY var4 = classYY.method13835(var3.field5351 * 1468976603);
         if (var4 != classYY.field7113) {
            throw new IllegalArgumentException("trying to get long from " + var4 + " param");
         } else {
            return var3.method9087();
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lclient;)Z")
   @ObfuscatedName("bn")
   static boolean method10169(client var0) {
      return null != var0.field1103;
   }

   default void setValue(int var1, int var2) {
      this.method10171(var1, new IntegerNode(var2));
   }

   default void setValue(int var1, long var2) {
      this.method10171(var1, new classVK(var2));
   }

   @ObfuscatedSignature(descriptor = "(Lyn;)V")
   @ObfuscatedName("pr")
   void vmethod537(IterableHashTable var1);
}
