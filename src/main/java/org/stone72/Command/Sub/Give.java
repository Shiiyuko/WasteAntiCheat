package org.stone72.Command.Sub;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import org.stone72.Command.UserCommand;
import org.stone72.Command.Base.BaseSubCommand;
import org.stone72.Utils.Configs.Config;

public class Give extends BaseSubCommand {
   public Give(String name, String description, String usage) {
      super(name, description, usage);
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      if (sender.isPlayer()) {
         if (args.length < 4) {
            UserCommand.sendUsed(this, sender);
            return false;
         } else if (sender.isPlayer()) {
            int id;
            try {
               id = ItemID.class.getField(args[1].toUpperCase()).getInt((Object)null);
            } catch (NoSuchFieldException | IllegalAccessException var8) {
               try {
                  id = BlockID.class.getField(args[1].toUpperCase()).getInt((Object)null);
               } catch (NoSuchFieldException | IllegalAccessException var7) {
                  id = 1000000;
               }
            }

            if (id != 1000000) {
               Item i = Item.get(id, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
               ((Player)sender).getInventory().addItem(new Item[]{i});
               sender.sendMessage(Config.prefix + " §bGive you " + i.getName() + "x" + i.getCount());
            } else {
               sender.sendMessage(Config.prefix + " §bThere is no item with ID " + args[1]);
            }

            return true;
         } else {
            sender.sendMessage(Config.prefix + " §bYou are not a player");
            return true;
         }
      } else {
         sender.sendMessage("The console is unable to execute this command!");
         return false;
      }
   }
}
