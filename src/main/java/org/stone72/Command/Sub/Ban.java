package org.stone72.Command.Sub;

import cn.nukkit.command.CommandSender;
import org.stone72.WasteAC;
import org.stone72.Command.UserCommand;
import org.stone72.Command.Base.BaseSubCommand;
import org.stone72.Utils.Configs.Config;

public class Ban extends BaseSubCommand {
   public Ban(String name, String description, String usage) {
      super(name, description, usage);
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      if (args.length < 9) {
         UserCommand.sendUsed(this, sender);
         return false;
      } else {
         WasteAC.getApi().banPlayer(args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[8]));
         sender.sendMessage(Config.prefix + " §bCompletion of enforcement");
         return true;
      }
   }
}
