package org.stone72.Command.Sub;

import cn.nukkit.command.CommandSender;
import org.stone72.WasteAC;
import org.stone72.Command.UserCommand;
import org.stone72.Command.Base.BaseSubCommand;
import org.stone72.Utils.Configs.Config;

public class Unban extends BaseSubCommand {
   public Unban(String name, String description, String usage) {
      super(name, description, usage);
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      if (args.length < 2) {
         UserCommand.sendUsed(this, sender);
         return false;
      } else {
         WasteAC.getApi().unBanPlayer(args[1]);
         sender.sendMessage(Config.prefix + " §bCompletion of enforcement");
         return true;
      }
   }
}
