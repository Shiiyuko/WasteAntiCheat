package org.stone72.Command;

import cn.nukkit.command.CommandSender;
import org.stone72.Command.Base.BaseCommand;
import org.stone72.Command.Base.BaseSubCommand;
import org.stone72.Command.Sub.Ban;
import org.stone72.Command.Sub.Give;
import org.stone72.Command.Sub.Help;
import org.stone72.Command.Sub.Kick;
import org.stone72.Command.Sub.Reload;
import org.stone72.Command.Sub.Unban;
import org.stone72.Command.Sub.Version;

public class UserCommand extends BaseCommand {
   public UserCommand(String name) {
      super(name, "WasteAC is a lightweight yet practical anti-cheat solution.");
      this.addSubCommand(new Ban("ban", "Ban player", "wac ban <name> <type> <day> <hour> <minute>"));
      this.addSubCommand(new Unban("unban", "Unban player", "wac unban <name>"));
      this.addSubCommand(new Kick("kick", "Kick player", "wac kick <name> <type>"));
      this.addSubCommand(new Reload("reload", "Reload", "wac reload"));
      this.addSubCommand(new Version("version", "Version", "wac version"));
      this.addSubCommand(new Give("give", "Give item", "wac give <id> <damage> <count>"));
      this.addSubCommand(new Help("help", "Help", "wac help"));
      this.loadCommandBase();
   }

   public static void sendUsed(BaseSubCommand cmd, CommandSender sender) {
      sender.sendMessage("Usage: " + cmd.getUsage());
   }
}
