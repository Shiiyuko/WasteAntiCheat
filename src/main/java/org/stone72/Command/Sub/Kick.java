package org.stone72.Command.Sub;

import cn.nukkit.command.CommandSender;
import org.stone72.WasteAC;
import org.stone72.Command.UserCommand;
import org.stone72.Command.Base.BaseSubCommand;
import org.stone72.Utils.Configs.Config;

public class Kick extends BaseSubCommand {
   public Kick(String name, String description, String usage) {
      super(name, description, usage);
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      if (args.length < 3) {
         UserCommand.sendUsed(this, sender);
         return false;
      } else {
         WasteAC.getApi().kickPlayer(args[1], args[2]);
         sender.sendMessage(Config.prefix + " §bCompletion of enforcement");
         return true;
      }
   }
}
