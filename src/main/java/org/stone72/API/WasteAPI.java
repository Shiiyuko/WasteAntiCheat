package org.stone72.API;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.Event;
import cn.nukkit.utils.Config;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.stone72.WasteAC;
import org.stone72.API.Event.PlayerFlagEvent;
import org.stone72.API.Event.PlayerPunishEvent;
import org.stone72.Module.Combat.KillAura;
import org.stone72.Utils.SnowflakeId;
import org.stone72.Utils.Worthless;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Entity.HumanBot;
import org.stone72.Utils.Math.Calculation;
import org.stone72.Utils.Math.DateTime;

public class WasteAPI {
   public static String[] Combat = new String[]{"AutoClicker", "AimBot", "KillAura", "Range"};
   public static String[] Moving = new String[]{"AirJump", "NoSlow", "Speed", "ThroughWall", "Velocity", "Fly", "HighJump"};
   public static String[] Player = new String[]{"InventoryMove", "Scaffold", "Teleport"};
   public static String[] Misc = new String[]{"Spammer", "ManyPackets", "BadPacket"};

   public static Config getPlayerData() {
      return WasteAC.playerData;
   }

   public static void solveCheat(Event e, boolean celled, Player p, String check, String type, String data, String describe) {
      if (WasteAC.getChecks().getBoolean(Checks.getWhereCheck(check) + "." + check + ".cancel") && celled) {
         e.setCancelled();
      }

      if (!org.stone72.Utils.Configs.Config.setting$check_admin) {
         if (!p.isOp()) {
            WasteAC.getApi().plusFlag(p.getName(), check, type, true);
         } else {
            WasteAC.getApi().plusFlag(p.getName(), check, type, true);
         }
      }

      if (org.stone72.Utils.Configs.Config.setting$debug) {
         WasteAC.getApi().sendDebug(p.getName(), check + "." + type, data, describe);
      }

   }

   public static void solveCheat(Event e, boolean celled, Player p, String check, String type, String data, String describe, boolean learnModel, double modelValue) {
      if (WasteAC.getChecks().getBoolean(Checks.getWhereCheck(check) + "." + check + ".cancel") && celled) {
         e.setCancelled();
      }

      if (!org.stone72.Utils.Configs.Config.setting$check_admin) {
         if (!p.isOp()) {
            WasteAC.getApi().plusFlag(p.getName(), check, type, true);
         } else {
            WasteAC.getApi().plusFlag(p.getName(), check, type, true);
         }
      }

      if (org.stone72.Utils.Configs.Config.setting$debug) {
         WasteAC.getApi().sendDebug(p.getName(), check + "." + type, data, describe);
         if (learnModel && modelValue != 0.0) {
            List newModel = Checks.getType(check, type, "data", "double list").DoubleListValue;
            newModel.add(modelValue);
            WasteAC.getChecks().set(Checks.getWhereCheck(check) + "." + check + ".modules." + type + ".data", newModel);
            WasteAC.getChecks().save();
            Checks.initChecks();
         }
      }

   }

   public static boolean canCheck(String check, Player p) {
      return WasteAC.getChecks().getBoolean(Checks.getWhereCheck(check) + "." + check + ".enable") && !org.stone72.Utils.Configs.Config.setting$excluded_worlds.contains(p.getPlayer().level.getName());
   }

   public void broadcastMessage(String playerName, String check) {
      org.stone72.Utils.Configs.Config.server.broadcastMessage(org.stone72.Utils.Configs.Config.setting$broadcast_message.replace("%type%", check).replace("%player%", playerName));
      this.writeLog("WasteAC broadcast claimed that player " + playerName + " was kicked out of the server, check: " + check);
   }

   public void sendDebug(String playerName, String check, String data, String describe) {
      if (org.stone72.Utils.Configs.Config.setting$test_mode$enable) {
         org.stone72.Utils.Configs.Config.server.broadcastMessage(org.stone72.Utils.Configs.Config.prefix + " §fName: " + playerName + " §8| §bCheat: " + check + " §8| §eData: " + data + " §8| §fVl: " + ((HashMap)org.stone72.Utils.Configs.Config.vlMap.getOrDefault(playerName, new HashMap())).getOrDefault(check, 0) + " §8| §7Describe: " + describe);
      } else {
         Player player = org.stone72.Utils.Configs.Config.server.getPlayerExact(playerName);
         Optional optional = Optional.ofNullable(player);
         optional.ifPresent((player1) -> {
            player.sendMessage(org.stone72.Utils.Configs.Config.prefix + " §fName: " + playerName + " §8| §bCheat: " + check + " §8| §eData: " + data + " §8| §fVl: " + ((HashMap)org.stone72.Utils.Configs.Config.vlMap.getOrDefault(playerName, new HashMap())).getOrDefault(check, 0) + " §8| §7Describe: " + describe);
         });
      }

      this.writeLog("Player " + playerName + " flag debug, check: " + check + ", data: " + data);
   }

   public void spawnBot(Player player) {
      try {
         HumanBot botEntity = new HumanBot(player);
         botEntity.spawnTo(player);
         KillAura.playerBot.put(player.getName(), botEntity);
      } catch (UnknownHostException var3) {
         throw new RuntimeException(var3);
      }
   }

   public void writeLog(String s) {
      Calculation.writeFile(WasteAC.getLog_date().getPath(), "[WasteAC-Logger] " + s);
   }

   public void unBanPlayer(String playerName) {
      if (!getPlayerData().exists(playerName + ".kvl") && !getPlayerData().exists(playerName + ".bvl")) {
         getPlayerData().remove(playerName);
      } else {
         getPlayerData().set(playerName + ".banID", "");
         getPlayerData().set(playerName + ".banTime", "");
         getPlayerData().set(playerName + ".check", "");
      }

      getPlayerData().save();
      this.writeLog("Player " + playerName + " has been unbanned by WasteAC");
   }

   public void info(String info) {
      org.stone72.Utils.Configs.Config.logger.info(info);
      this.writeLog(info);
   }

   public void banPlayer(String playerName, String check, int year, int month, int day, int hour, int minute, int second) {
      LocalDateTime ld = DateTime.getDate();
      ld = ld.plusYears((long)year);
      ld = ld.plusMonths((long)month);
      ld = ld.plusDays((long)day);
      ld = ld.plusHours((long)hour);
      ld = ld.plusMinutes((long)minute);
      ld = ld.plusSeconds((long)second);
      SnowflakeId idWorker = new SnowflakeId((long)org.stone72.Utils.Configs.Config.randomNumber.nextInt(31), (long)org.stone72.Utils.Configs.Config.randomNumber.nextInt(31));
      long id = idWorker.nextId() % 100000000L;
      getPlayerData().set(playerName + ".banID", id);
      getPlayerData().set(playerName + ".banTime", DateTime.getDateString(ld));
      getPlayerData().set(playerName + ".check", check);
      getPlayerData().save();
      Player player = org.stone72.Utils.Configs.Config.server.getPlayerExact(playerName);
      Optional optional = Optional.ofNullable(player);
      optional.ifPresent((player1) -> {
         player.kick(org.stone72.Utils.Configs.Config.setting$banned_message.replace("%id%", String.valueOf(id)).replace("%year%", String.valueOf(year)).replace("%month%", String.valueOf(month)).replace("%day%", String.valueOf(day)).replace("%hour%", String.valueOf(hour)).replace("%minute%", String.valueOf(minute)).replace("%second%", String.valueOf(second)).replace("%type%", check).replace("%player%", player.getName()));
      });
      this.writeLog("Player " + playerName + " was banned by WasteAC for " + year + " years, " + month + " months, " + day + " days, " + hour + " hours, " + minute + " minutes, and " + second + " seconds, check: " + check);
   }

   public void kickPlayer(String playerName, String check) {
      Player player = org.stone72.Utils.Configs.Config.server.getPlayerExact(playerName);
      Optional optional = Optional.ofNullable(player);
      optional.ifPresent((player1) -> {
         org.stone72.Utils.Configs.Config.server.getPlayerExact(playerName).kick(org.stone72.Utils.Configs.Config.setting$kick_message.replace("%type%", check).replace("%player%", playerName));
      });
      this.writeLog("Player " + playerName + " was kicked out of the server by WasteAC, check: " + check);
   }

   public void plusFlag(String playerName, String check, String type, boolean CheckAndPlus) {
      org.stone72.Utils.Configs.Config.flagMap.put(playerName, org.stone72.Utils.Configs.Config.flagMap.getOrDefault(playerName, new HashMap()));
      ((HashMap)org.stone72.Utils.Configs.Config.flagMap.get(playerName)).put(check + "." + type, (Integer)((HashMap)org.stone72.Utils.Configs.Config.flagMap.get(playerName)).getOrDefault(check, 0) + 1);
      Player p = Server.getInstance().getPlayerExact(playerName);
      WasteAC.getInstance().getServer().getPluginManager().callEvent(new PlayerFlagEvent(check, type, p, (Integer)((HashMap)org.stone72.Utils.Configs.Config.flagMap.get(playerName)).getOrDefault(check, 0)));
      this.writeLog("WasteAC added 1 flag value to player " + playerName + ", check: " + check + "." + type);
      if (!org.stone72.Utils.Configs.Config.setting$warning$message.isEmpty()) {
         p.sendMessage(org.stone72.Utils.Configs.Config.setting$warning$message.replace("%player%", playerName).replace("%type%", check + "." + type));
      }

      if (!org.stone72.Utils.Configs.Config.setting$warning$title.isEmpty()) {
         String[] t = org.stone72.Utils.Configs.Config.setting$warning$title.split(":");
         p.sendTitle(t[0].replace("%player%", playerName).replace("%type%", check + "." + type), t[1].replace("%player%", playerName).replace("%type%", check + "." + type));
      }

      if (!org.stone72.Utils.Configs.Config.setting$warning$actionbar.isEmpty()) {
         p.sendActionBar(org.stone72.Utils.Configs.Config.setting$warning$actionbar.replace("%player%", playerName).replace("%type%", check + "." + type));
      }

      if (CheckAndPlus && (Integer)((HashMap)org.stone72.Utils.Configs.Config.flagMap.get(playerName)).get(check + "." + type) >= Checks.getTypeFlag(check, type)) {
         this.plusVl(playerName, check, type);
      }

   }

   public void plusVl(String playerName, String check, String type) {
      org.stone72.Utils.Configs.Config.vlMap.put(playerName, org.stone72.Utils.Configs.Config.vlMap.getOrDefault(playerName, new HashMap()));
      ((HashMap)org.stone72.Utils.Configs.Config.vlMap.get(playerName)).put(check + "." + type, (Integer)((HashMap)org.stone72.Utils.Configs.Config.vlMap.get(playerName)).getOrDefault(check, 0) + 1);
      Player p = Server.getInstance().getPlayerExact(playerName);
      WasteAC.getInstance().getServer().getPluginManager().callEvent(new PlayerPunishEvent(check, type, p, (Integer)((HashMap)org.stone72.Utils.Configs.Config.vlMap.get(playerName)).getOrDefault(check, 0), 0));
      if (!Checks.getTypeVl(check, type).isEmpty()) {
         Iterator var5 = Checks.getTypeVl(check, type).iterator();

         while(var5.hasNext()) {
            String vl_e = (String)var5.next();
            String[] run = vl_e.split(":");
            if ((Integer)((HashMap)org.stone72.Utils.Configs.Config.vlMap.get(playerName)).get(check + "." + type) == Integer.parseInt(run[0])) {
               String[] cmd = run[1].split("=");
               if (run[4].equals("true")) {
                  Thread cw = new Thread(new CmdWave(0, run, playerName, check, type, cmd));
                  cw.start();
               } else if (run[4].equals("false")) {
                  org.stone72.Utils.Configs.Config.server.dispatchCommand(org.stone72.Utils.Configs.Config.server.getConsoleSender(), cmd[1].replace("%player%", playerName).replace("%type%", check));
                  if (run[3].equals("true")) {
                     this.broadcastMessage(playerName, check);
                  }

                  if (run[2].equals("true")) {
                     ((HashMap)org.stone72.Utils.Configs.Config.vlMap.get(playerName)).put(check + "." + type, 0);
                  }

                  String[] addKvl = run[5].split("=");
                  this.plusKvl(playerName, Integer.parseInt(addKvl[1]), check);
               }
            }
         }
      }

      this.writeLog("WasteAC added 1 vl value to player " + playerName + ", check: " + check + "." + type);
   }

   public void plusKvl(String playerName, int add, String check) {
      getPlayerData().set(playerName + ".kvl", getPlayerData().getInt(playerName + ".kvl") + add);
      getPlayerData().save();
      Player p = Server.getInstance().getPlayerExact(playerName);
      WasteAC.getInstance().getServer().getPluginManager().callEvent(new PlayerPunishEvent(check, "", p, getPlayerData().getInt(playerName + ".kvl"), 1));
      if (!WasteAC.config().getStringList("setting.kvl_event").isEmpty()) {
         Iterator var5 = WasteAC.config().getStringList("setting.kvl_event").iterator();

         while(var5.hasNext()) {
            String kvl_e = (String)var5.next();
            String[] run = kvl_e.split(":");
            if (getPlayerData().getInt(playerName + ".kvl") == Integer.parseInt(run[0])) {
               String[] cmd = run[1].split("=");
               if (run[4].equals("true")) {
                  Thread cw = new Thread(new CmdWave(1, run, playerName, check, "", cmd));
                  cw.start();
               } else if (run[4].equals("false")) {
                  org.stone72.Utils.Configs.Config.server.dispatchCommand(org.stone72.Utils.Configs.Config.server.getConsoleSender(), cmd[1].replace("%player%", playerName).replace("%type%", check));
                  if (run[3].equals("true")) {
                     this.broadcastMessage(playerName, check);
                  }

                  if (run[2].equals("true")) {
                     getPlayerData().set(playerName + ".kvl", 0);
                     getPlayerData().save();
                  }

                  String[] addBvl = run[5].split("=");
                  this.plusBvl(playerName, Integer.parseInt(addBvl[1]), check);
               }
            }
         }
      }

      this.writeLog("WasteAC added " + add + " kvl value to player " + playerName + ", type: " + check);
   }

   public void plusBvl(String playerName, int add, String check) {
      getPlayerData().set(playerName + ".bvl", getPlayerData().getInt(playerName + ".bvl") + add);
      getPlayerData().save();
      Player p = Server.getInstance().getPlayerExact(playerName);
      WasteAC.getInstance().getServer().getPluginManager().callEvent(new PlayerPunishEvent(check, "", p, getPlayerData().getInt(playerName + ".bvl"), 2));
      if (!WasteAC.config().getStringList("setting.bvl_event").isEmpty()) {
         Iterator var5 = WasteAC.config().getStringList("setting.bvl_event").iterator();

         while(var5.hasNext()) {
            String kvl_e = (String)var5.next();
            String[] run = kvl_e.split(":");
            if (getPlayerData().getInt(playerName + ".bvl") == Integer.parseInt(run[0])) {
               String[] cmd = run[1].split("=");
               if (run[4].equals("true")) {
                  Thread cw = new Thread(new CmdWave(2, run, playerName, check, "", cmd));
                  cw.start();
               } else if (run[4].equals("false")) {
                  org.stone72.Utils.Configs.Config.server.dispatchCommand(org.stone72.Utils.Configs.Config.server.getConsoleSender(), cmd[1].replace("%player%", playerName).replace("%type%", check));
                  if (run[3].equals("true")) {
                     this.broadcastMessage(playerName, check);
                  }

                  if (run[2].equals("true")) {
                     getPlayerData().set(playerName + ".bvl", 0);
                     getPlayerData().save();
                  }
               }
            }
         }
      }

      this.writeLog("WasteAC added " + add + " bvl value to player " + playerName + ", type: " + check);
   }

   private static class CmdWave implements Runnable {
      transient int type;
      transient String[] run;
      transient String playerName;
      transient String check;
      transient String sub;
      transient String[] cmd;

      public CmdWave(int type, String[] run, String playerName, String check, String sub, String[] cmd) {
         this.type = type;
         this.run = run;
         this.sub = sub;
         this.playerName = playerName;
         this.check = check;
         this.cmd = cmd;
      }

      public void run() {
         Worthless.sleep((long)org.stone72.Utils.Configs.Config.setting$CmdWave * 1000L);
         switch (this.type) {
            case 0:
               org.stone72.Utils.Configs.Config.server.dispatchCommand(org.stone72.Utils.Configs.Config.server.getConsoleSender(), this.cmd[1].replace("%player%", this.playerName).replace("%type%", this.check));
               if (this.run[3].equals("true")) {
                  WasteAC.getApi().broadcastMessage(this.playerName, this.check);
               }

               if (this.run[2].equals("true")) {
                  ((HashMap)org.stone72.Utils.Configs.Config.vlMap.get(this.playerName)).put(this.check + "." + this.sub, 0);
               }

               String[] addKvl = this.run[5].split("=");
               WasteAC.getApi().plusKvl(this.playerName, Integer.parseInt(addKvl[1]), this.check);
               break;
            case 1:
               org.stone72.Utils.Configs.Config.server.dispatchCommand(org.stone72.Utils.Configs.Config.server.getConsoleSender(), this.cmd[1].replace("%player%", this.playerName).replace("%type%", this.check));
               if (this.run[3].equals("true")) {
                  WasteAC.getApi().broadcastMessage(this.playerName, this.check);
               }

               if (this.run[2].equals("true")) {
                  WasteAPI.getPlayerData().set(this.playerName + ".kvl", 0);
                  WasteAPI.getPlayerData().save();
               }

               String[] addBvl = this.run[5].split("=");
               WasteAC.getApi().plusBvl(this.playerName, Integer.parseInt(addBvl[1]), this.check);
               break;
            case 2:
               org.stone72.Utils.Configs.Config.server.dispatchCommand(org.stone72.Utils.Configs.Config.server.getConsoleSender(), this.cmd[1].replace("%player%", this.playerName).replace("%type%", this.check));
               if (this.run[3].equals("true")) {
                  WasteAC.getApi().broadcastMessage(this.playerName, this.check);
               }

               if (this.run[2].equals("true")) {
                  WasteAPI.getPlayerData().set(this.playerName + ".bvl", 0);
                  WasteAPI.getPlayerData().save();
               }
         }

      }
   }
}
