package org.stone72;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Objects;
import org.stone72.API.WasteAPI;
import org.stone72.Command.UserCommand;
import org.stone72.Function.TestSystem;
import org.stone72.Thread.BasicTasks;
import org.stone72.Utils.Worthless;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Math.DateTime;

public class WasteAC extends PluginBase {
   public static String checksPath;
   public static Config playerData;
   private static Config config;
   private static File dataFolder;
   private static Config checks;
   private static File log_date;
   private static File pluginLogs;
   private static WasteAC plugin;
   private static WasteAPI wacApi;

   public void onLoad() {
      this.getLogger().info("正在执行初始化...");
      plugin = this;
      wacApi = new WasteAPI();
      dataFolder = this.getDataFolder();
      pluginLogs = new File(dataFolder, "pluginLogs");
      log_date = new File(pluginLogs, "log-" + DateTime.getDateString(DateTime.getDate()) + ".txt");
      this.getLogger().info("初始化赋值完成");
      if (!dataFolder.exists()) {
         dataFolder.mkdirs();
      }

      if (!pluginLogs.exists()) {
         pluginLogs.mkdirs();
      }

      this.saveDefaultConfig();
      this.saveResource("checks.yml", false);
      config = this.getConfig();
      File checksFile = new File(dataFolder, "checks.yml");
      checksPath = checksFile.getPath();
      checks = new Config(checksFile, 2);
      if (!log_date.exists()) {
         try {
            log_date.createNewFile();
         } catch (IOException var3) {
            this.getLogger().info("无法创建日志文件: " + log_date.getName());
            throw new RuntimeException(var3);
         }
      }

      this.getLogger().info("初始化配置文件完成");
      this.getLogger().info("初始化完成");
   }

   public void onEnable() {
      org.stone72.Utils.Configs.Config.logger.info("§b __          __     _____ ");
      org.stone72.Utils.Configs.Config.logger.info("§b \\ \\        / /\\   / ____|");
      org.stone72.Utils.Configs.Config.logger.info("§b  \\ \\  /\\  / /  \\ | |     ");
      org.stone72.Utils.Configs.Config.logger.info("§b   \\ \\/  \\/ / /\\ \\| |     ");
      org.stone72.Utils.Configs.Config.logger.info("§b    \\  /\\  / ____ \\ |____ ");
      org.stone72.Utils.Configs.Config.logger.info("§b     \\/  \\/_/    \\_\\_____|");
      org.stone72.Utils.Configs.Config.logger.info("§b                          ");
      org.stone72.Utils.Configs.Config.logger.info("§e本机HWID: " + Worthless.getHWID() + " WasteAC验证中");
      this.getServer().getCommandMap().register("wac", new UserCommand("wac"));
      Checks.initChecks();
      org.stone72.Utils.Configs.Config.initConfig();
      (new BasicTasks()).startTask(Worthless.coupling());
      playerData = new Config(new File(org.stone72.Utils.Configs.Config.setting$playerData), 2);
      if (org.stone72.Utils.Configs.Config.setting$auto_del_logs > 0) {
         File[] var1 = (File[])Objects.requireNonNull(pluginLogs.listFiles());
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            File fs = var1[var3];
            LocalDateTime logTime = DateTime.parseDateString(fs.getName().replace(".txt", "").replace("log-", ""));
            logTime = logTime.plusDays((long)org.stone72.Utils.Configs.Config.setting$auto_del_logs);
            if (DateTime.getDate().isAfter(logTime)) {
               fs.delete();
            }
         }
      }

   }

   public void onDisable() {
      if (org.stone72.Utils.Configs.Config.setting$test_mode$enable && !TestSystem.placeBlock.isEmpty()) {
         Iterator var1 = TestSystem.placeBlock.iterator();

         while(var1.hasNext()) {
            Block b = (Block)var1.next();
            b.onBreak((Item)null);
         }
      }

      org.stone72.Utils.Configs.Config.logger.info("WasteAC关闭成功");
   }

   public static Config config() {
      return config;
   }

   public static File dataFolder() {
      return dataFolder;
   }

   public static WasteAC getInstance() {
      return plugin;
   }

   public static File getLog_date() {
      return log_date;
   }

   public static Config getChecks() {
      return checks;
   }

   public static WasteAPI getApi() {
      return wacApi;
   }
}
