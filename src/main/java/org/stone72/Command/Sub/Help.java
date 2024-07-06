package org.stone72.Command.Sub;

import cn.nukkit.command.CommandSender;
import org.stone72.Command.Base.BaseCommand;
import org.stone72.Command.Base.BaseSubCommand;

public class Help extends BaseSubCommand {
   public Help(String name, String description, String usage) {
      super(name, description, usage);
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      BaseCommand.sendHelp(sender, BaseCommand.subCommand);
      return true;
   }
}
