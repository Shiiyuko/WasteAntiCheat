package org.stone72.Command.Sub;

import cn.nukkit.command.CommandSender;
import org.stone72.API.WasteAPI;
import org.stone72.Command.Base.BaseSubCommand;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;

public class Reload extends BaseSubCommand {
   public Reload(String name, String description, String usage) {
      super(name, description, usage);
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      Config.initConfig();
      Checks.initChecks();
      WasteAPI.getPlayerData().reload();
      sender.sendMessage(Config.prefix + " §bCompletion of enforcement");
      return true;
   }
}
