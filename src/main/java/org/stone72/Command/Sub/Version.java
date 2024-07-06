package org.stone72.Command.Sub;

import cn.nukkit.command.CommandSender;
import org.stone72.WasteAC;
import org.stone72.Command.Base.BaseSubCommand;
import org.stone72.Utils.Configs.Config;

public class Version extends BaseSubCommand {
   public Version(String name, String description, String usage) {
      super(name, description, usage);
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      String[] ver = WasteAC.getInstance().getDescription().getVersion().split("-");
      sender.sendMessage(Config.prefix + " §fName: WasteAC §8| §bVersion: " + WasteAC.getInstance().getDescription().getVersion() + " §8| §eType: " + ver[1] + " Edition");
      return true;
   }
}
