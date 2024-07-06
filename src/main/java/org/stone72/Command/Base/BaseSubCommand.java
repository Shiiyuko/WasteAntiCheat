package org.stone72.Command.Base;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

public abstract class BaseSubCommand {
   private final String name;
   private final String Description;
   private final String usage;

   protected BaseSubCommand(String name, String description, String usage) {
      this.name = name.toLowerCase();
      this.Description = description;
      this.usage = usage;
   }

   public boolean canUser(CommandSender sender) {
      return true;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.Description;
   }

   public String getUsage() {
      return this.usage;
   }

   public String[] getAliases() {
      return new String[0];
   }

   public abstract boolean execute(CommandSender var1, String var2, String[] var3);

   public CommandParameter[] getParameters() {
      return new CommandParameter[0];
   }
}
