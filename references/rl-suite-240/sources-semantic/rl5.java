import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("rl5")
public class rl5 implements MenuEntry {
   @ObfuscatedName("pd")
   public int field5800;
   @ObfuscatedSignature(descriptor = "Lwz;")
   @ObfuscatedName("cu")
   public final Menu field5799;
   @ObfuscatedName("yn")
   public Consumer field5801;

   @ObfuscatedSignature(descriptor = "()Ldz;")
   @ObfuscatedName("ll")
   public WorldView method10148() {
      return WorldView.method3792(this.getWorldViewId());
   }

   @ObfuscatedSignature(descriptor = "()Lwz;")
   @ObfuscatedName("fx")
   public Menu method10156() {
      return this.field5799.field6804[this.field5800];
   }

   @ObfuscatedSignature(descriptor = "(I)Lrl5;")
   @ObfuscatedName("cm")
   public rl5 method10155(int var1) {
      this.field5799.field6799[this.field5800] = var1;
      return this;
   }

   public boolean isItemOp() {
      MenuAction var1 = this.getType();
      if (var1 == MenuAction.CC_OP || var1 == MenuAction.CC_OP_LOW_PRIORITY || var1 == MenuAction.RUNELITE_WIDGET) {
         int var2 = this.field5799.field6799[this.field5800];
         int var3 = this.field5799.field6797[this.field5800];
         if (var3 == 9764864) {
            switch (var2) {
               case 1:
               case 2:
               case 3:
               case 4:
               case 6:
               case 7:
                  return true;
               case 5:
            }
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lrl5;")
   @ObfuscatedName("yf")
   public rl5 method10157(String var1) {
      this.field5799.field6802[this.field5800] = var1;
      return this;
   }

   public int getWorldViewId() {
      return this.field5799.field6801[this.field5800];
   }

   @Override
   public int hashCode() {
      byte var1 = 59;
      int var2 = 1;
      var2 = var2 * 59 + this.getIdentifier();
      var2 = var2 * 59 + this.getParam0();
      var2 = var2 * 59 + this.getParam1();
      var2 = var2 * 59 + this.getItemId();
      var2 = var2 * 59 + (this.isForceLeftClick() ? 79 : 97);
      var2 = var2 * 59 + this.getWorldViewId();
      var2 = var2 * 59 + (this.isDeprioritized() ? 79 : 97);
      String var3 = this.getOption();
      var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
      String var4 = this.getTarget();
      var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
      MenuAction var5 = this.getType();
      return var2 * 59 + (var5 == null ? 43 : var5.hashCode());
   }

   public boolean isForceLeftClick() {
      return this.field5799.field6805[this.field5800];
   }

   public int getItemId() {
      return this.field5799.field6800[this.field5800];
   }

   @ObfuscatedSignature(descriptor = "(I)Lrl5;")
   @ObfuscatedName("sv")
   public rl5 method10159(int var1) {
      this.field5799.field6797[this.field5800] = var1;
      return this;
   }

   public String getTarget() {
      return this.field5799.field6803[this.field5800];
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof rl5)) {
         return false;
      } else {
         rl5 var2 = (rl5)var1;
         if (!var2.method10150(this)) {
            return false;
         } else if (this.getIdentifier() != var2.getIdentifier()) {
            return false;
         } else if (this.getParam0() != var2.getParam0()) {
            return false;
         } else if (this.getParam1() != var2.getParam1()) {
            return false;
         } else if (this.getItemId() != var2.getItemId()) {
            return false;
         } else if (this.isForceLeftClick() != var2.isForceLeftClick()) {
            return false;
         } else if (this.getWorldViewId() != var2.getWorldViewId()) {
            return false;
         } else if (this.isDeprioritized() != var2.isDeprioritized()) {
            return false;
         } else {
            String var3 = this.getOption();
            String var4 = var2.getOption();
            if (var3 == null ? var4 == null : var3.equals(var4)) {
               String var5 = this.getTarget();
               String var6 = var2.getTarget();
               if (var5 == null ? var6 == null : var5.equals(var6)) {
                  MenuAction var7 = this.getType();
                  MenuAction var8 = var2.getType();
                  return var7 == null ? var8 == null : var7.equals(var8);
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lrl5;")
   @ObfuscatedName("xw")
   public rl5 method10154(int var1) {
      this.field5799.field6800[this.field5800] = var1;
      return this;
   }

   public MenuEntry setDeprioritized(boolean var1) {
      if (var1) {
         if (this.field5799.field6798[this.field5800] < 2000) {
            this.field5799.field6798[this.field5800] = this.field5799.field6798[this.field5800] + 2000;
         }
      } else if (this.field5799.field6798[this.field5800] >= 2000) {
         this.field5799.field6798[this.field5800] = this.field5799.field6798[this.field5800] - 2000;
      }

      return this;
   }

   public MenuAction getType() {
      int var1 = this.field5799.field6798[this.field5800];
      if (var1 >= 2000) {
         var1 -= 2000;
      }

      return MenuAction.of(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/function/Consumer;)Lrl5;")
   @ObfuscatedName("zg")
   public rl5 method10153(Consumer var1) {
      this.field5801 = var1;
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lrl5;")
   @ObfuscatedName("ga")
   public rl5 method10158(String var1) {
      this.field5799.field6803[this.field5800] = var1;
      return this;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Nullable
   public net.runelite.api.Player getPlayer() {
      int var1 = this.getIdentifier();
      switch (this.getType()) {
         case WALK:
            var1--;
         case ITEM_USE_ON_PLAYER:
         case WIDGET_TARGET_ON_PLAYER:
         case PLAYER_FIRST_OPTION:
         case PLAYER_SECOND_OPTION:
         case PLAYER_THIRD_OPTION:
         case PLAYER_FOURTH_OPTION:
         case PLAYER_FIFTH_OPTION:
         case PLAYER_SIXTH_OPTION:
         case PLAYER_SEVENTH_OPTION:
         case PLAYER_EIGHTH_OPTION:
         case RUNELITE_PLAYER:
            WorldView var2 = this.method10148();
            if (var2 == null) {
               return null;
            }

            return (net.runelite.api.Player)var2.field1692.method13451(var1);
         default:
            return null;
      }
   }

   public net.runelite.api.Menu createSubMenu() {
      return this.field5799.field6804[this.field5800] = new Menu(false);
   }

   @ObfuscatedSignature(descriptor = "(Z)Lrl5;")
   @ObfuscatedName("od")
   public rl5 method10151(boolean var1) {
      this.field5799.field6805[this.field5800] = var1;
      return this;
   }

   public int getIdentifier() {
      return this.field5799.field6799[this.field5800];
   }

   @ObfuscatedSignature(descriptor = "(I)Lrl5;")
   @ObfuscatedName("np")
   public rl5 method10152(int var1) {
      this.field5799.field6801[this.field5800] = var1;
      return this;
   }

   public String getOption() {
      return this.field5799.field6802[this.field5800];
   }

   public int getParam1() {
      return this.field5799.field6797[this.field5800];
   }

   public void deleteSubMenu() {
      this.field5799.field6804[this.field5800] = null;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public net.runelite.api.widgets.Widget getWidget() {
      switch (this.getType()) {
         case WIDGET_TARGET:
         case WIDGET_TARGET_ON_WIDGET:
         case WIDGET_CONTINUE:
         case CC_OP:
         case CC_OP_LOW_PRIORITY:
         case RUNELITE_WIDGET:
            int var1 = this.getParam1();
            int var2 = this.getParam0();
            Object var3 = classOE.field4841.method2281(var1);
            if (var3 != null && var2 > -1) {
               var3 = var3.getChild(var2);
            }

            return (net.runelite.api.widgets.Widget)var3;
         default:
            return null;
      }
   }

   public int getParam0() {
      return this.field5799.field6796[this.field5800];
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("hf")
   public boolean method10150(Object var1) {
      return var1 instanceof rl5;
   }

   @ObfuscatedSignature(descriptor = "(I)Lrl5;")
   @ObfuscatedName("hf")
   public rl5 method10149(int var1) {
      this.field5799.field6796[this.field5800] = var1;
      return this;
   }

   public boolean isDeprioritized() {
      return this.field5799.field6798[this.field5800] >= 2000;
   }

   public int getItemOp() {
      MenuAction var1 = this.getType();
      if (var1 == MenuAction.CC_OP || var1 == MenuAction.CC_OP_LOW_PRIORITY || var1 == MenuAction.RUNELITE_WIDGET) {
         int var2 = this.field5799.field6799[this.field5800];
         int var3 = this.field5799.field6797[this.field5800];
         int var4 = this.field5799.field6796[this.field5800];
         if (var3 == 9764864) {
            switch (var2) {
               case 1:
                  Widget var5 = classOE.field4841.method2281(var3);
                  if (var5 != null && var4 != -1) {
                     net.runelite.api.widgets.Widget var7 = var5.getChild(var4);
                     if (var7 != null && var7.getItemId() > -1) {
                        int var6 = classOE.field4841.method2255(var7.getItemId()).getShiftClickActionIndex();
                        if (var6 >= 0) {
                           return var6 + 1;
                        }
                     }
                  }
                  break;
               case 2:
                  return 1;
               case 3:
                  return 2;
               case 4:
                  return 3;
               case 5:
               default:
                  break;
               case 6:
                  return 4;
               case 7:
                  return 5;
            }
         }
      }

      return -1;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Nullable
   public net.runelite.api.NPC getNpc() {
      switch (this.getType()) {
         case ITEM_USE_ON_NPC:
         case WIDGET_TARGET_ON_NPC:
         case NPC_FIRST_OPTION:
         case NPC_SECOND_OPTION:
         case NPC_THIRD_OPTION:
         case NPC_FOURTH_OPTION:
         case NPC_FIFTH_OPTION:
         case EXAMINE_NPC:
            WorldView var1 = this.method10148();
            int var2 = this.getIdentifier();
            return (net.runelite.api.NPC)var1.field1696.method13451(var2);
         default:
            return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnet/runelite/api/MenuAction;)Lrl5;")
   @ObfuscatedName("cp")
   public rl5 method10147(MenuAction var1) {
      int var2 = this.field5799.field6798[this.field5800];
      short var3 = 0;
      if (var2 >= 2000) {
         var3 = 2000;
      }

      this.field5799.field6798[this.field5800] = var1.getId() + var3;
      return this;
   }

   @Nullable
   public net.runelite.api.Actor getActor() {
      net.runelite.api.NPC var1 = this.getNpc();
      return (net.runelite.api.Actor)(var1 != null ? var1 : this.getPlayer());
   }

   public Consumer onClick() {
      return this.field5801;
   }

   @Override
   public String toString() {
      return "MenuEntryImpl(getOption="
         + this.getOption()
         + ", getTarget="
         + this.getTarget()
         + ", getIdentifier="
         + this.getIdentifier()
         + ", getType="
         + this.getType()
         + ", getParam0="
         + this.getParam0()
         + ", getParam1="
         + this.getParam1()
         + ", getItemId="
         + this.getItemId()
         + ", isForceLeftClick="
         + this.isForceLeftClick()
         + ", getWorldViewId="
         + this.getWorldViewId()
         + ", isDeprioritized="
         + this.isDeprioritized()
         + ")";
   }

   public rl5(Menu var1, int var2) {
      this.field5799 = var1;
      this.field5800 = var2;
   }
}
