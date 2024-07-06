package org.stone72.Command.Base;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.stone72.Command.Sub.Give;
import org.stone72.Utils.Configs.Config;

public class BaseCommand extends Command {
   public static final ArrayList<BaseSubCommand> subCommand = new ArrayList();
   private final transient ConcurrentHashMap<String, Integer> subCommands = new ConcurrentHashMap();

   public BaseCommand(String name, String description) {
      super(name, description);
   }

   public static void sendHelp(CommandSender sender, ArrayList<BaseSubCommand> SubCommand) {
      Iterator var2 = SubCommand.iterator();

      while(var2.hasNext()) {
         BaseSubCommand subCommand = (BaseSubCommand)var2.next();
         sender.sendMessage(Config.prefix + " §b" + subCommand.getUsage() + " §7- §e" + subCommand.getDescription());
      }

   }

   public boolean hasPermission(CommandSender sender) {
      return sender.isOp();
   }

   public boolean execute(CommandSender sender, String s, String[] args) {
      if (args.length == 0) {
         sendHelp(sender, subCommand);
         return false;
      } else {
         Object SubCommand = args[0].toLowerCase();
         if (!this.subCommands.containsKey(args[0].toLowerCase())) {
            sender.sendMessage("此命令不存在");
            return true;
         } else {
            BaseSubCommand command = (BaseSubCommand)subCommand.get((Integer)this.subCommands.get(SubCommand));
            if (!this.hasPermission(sender) && !(command instanceof Give)) {
               sender.sendMessage("你没有权限执行这个指令！");
               return true;
            } else if (!command.canUser(sender)) {
               if (sender.isPlayer()) {
                  sender.sendMessage("你没有权限执行这个指令！");
               } else {
                  sender.sendMessage("控制台无法执行这个指令！");
               }

               return true;
            } else {
               return command.execute(sender, s, args);
            }
         }
      }
   }

   protected void addSubCommand(BaseSubCommand cmd) {
      subCommand.add(cmd);
      int commandId = subCommand.size() - 1;
      this.subCommands.put(cmd.getName().toLowerCase(), commandId);
      String[] var3 = cmd.getAliases();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String alias = var3[var5];
         this.subCommands.put(alias.toLowerCase(), commandId);
      }

   }

   protected void loadCommandBase() {
      this.commandParameters.clear();
      Iterator var1 = BaseCommand.subCommand.iterator();

      while(var1.hasNext()) {
         BaseSubCommand subCommand = (BaseSubCommand)var1.next();
         List<CommandParameter> parameters = new LinkedList();
         parameters.add(CommandParameter.newEnum(subCommand.getName(), new String[]{subCommand.getName()}));
         parameters.addAll(Arrays.asList(subCommand.getParameters()));
         this.commandParameters.put(subCommand.getName(), parameters.toArray(new CommandParameter[0]));
      }

   }
}
