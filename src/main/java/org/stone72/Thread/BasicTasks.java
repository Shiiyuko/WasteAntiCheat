package org.stone72.Thread;

import cn.nukkit.event.Event;
import cn.nukkit.item.Item;
import cn.nukkit.scheduler.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.stone72.WasteAC;
import org.stone72.API.WasteAPI;
import org.stone72.Function.BanSystem;
import org.stone72.Function.CleanSystem;
import org.stone72.Function.TestSystem;
import org.stone72.Function.ToolSystem;
import org.stone72.Listener.Combat;
import org.stone72.Listener.Misc;
import org.stone72.Listener.Moving;
import org.stone72.Listener.Player;
import org.stone72.Module.Combat.KillAura;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;

public class BasicTasks {
   public static boolean serverLag = false;
   public static int lagRestore = 0;
   int timeCount = 0;

   public void startTask(boolean validate) {
      if (validate) {
         Config.logger.info("§e验证成功,插件开始加载");
         WasteAC.getInstance().getServer().getPluginManager().registerEvents(new Combat(), WasteAC.getInstance());
         WasteAC.getInstance().getServer().getPluginManager().registerEvents(new Moving(), WasteAC.getInstance());
         WasteAC.getInstance().getServer().getPluginManager().registerEvents(new Player(), WasteAC.getInstance());
         WasteAC.getInstance().getServer().getPluginManager().registerEvents(new Misc(), WasteAC.getInstance());
         WasteAC.getInstance().getServer().getPluginManager().registerEvents(new TestSystem(), WasteAC.getInstance());
         WasteAC.getInstance().getServer().getPluginManager().registerEvents(new BanSystem(), WasteAC.getInstance());
         WasteAC.getInstance().getServer().getPluginManager().registerEvents(new CleanSystem(), WasteAC.getInstance());
         WasteAC.getInstance().getServer().getPluginManager().registerEvents(new ToolSystem(), WasteAC.getInstance());
         Config.serverTask.scheduleRepeatingTask(WasteAC.getInstance(), new Task() {
            public void onRun(int i) {
               ++BasicTasks.this.timeCount;
               Iterator var2;
               String e;
               if (!KillAura.packTick.keySet().isEmpty()) {
                  var2 = KillAura.packTick.keySet().iterator();

                  while(var2.hasNext()) {
                     e = (String)var2.next();
                     if (Config.server.getTick() - (Integer)KillAura.packTick.get(e) >= Checks.getType(WasteAPI.Combat[2], "type-c", "combo_tick", "int").IntValue) {
                        KillAura.inCombo.remove(e);
                        KillAura.aniCount.remove(e);
                        KillAura.isAura.remove(e);
                     }
                  }
               }

               if (!WasteAPI.getPlayerData().getKeys(false).isEmpty() && BasicTasks.this.timeCount % (WasteAC.config().getInt("setting.reduce_kvl") * 20) == 0) {
                  var2 = WasteAPI.getPlayerData().getKeys(false).iterator();

                  while(var2.hasNext()) {
                     e = (String)var2.next();
                     if (WasteAPI.getPlayerData().getInt(e + ".kvl") != 0) {
                        WasteAPI.getPlayerData().set(e + ".kvl", WasteAPI.getPlayerData().getInt(e + ".kvl") - 1);
                     }
                  }
               }

               Iterator var4;
               String check;
               if (!Config.vlMap.keySet().isEmpty() && BasicTasks.this.timeCount % (WasteAC.config().getInt("setting.reduce_vl") * 20) == 0) {
                  var2 = Config.vlMap.keySet().iterator();

                  while(var2.hasNext()) {
                     e = (String)var2.next();
                     var4 = ((HashMap)Config.vlMap.get(e)).keySet().iterator();

                     while(var4.hasNext()) {
                        check = (String)var4.next();
                        if ((Integer)((HashMap)Config.vlMap.get(e)).get(check) > 0) {
                           ((HashMap)Config.vlMap.get(e)).put(check, (Integer)((HashMap)Config.vlMap.get(e)).get(check) - 1);
                        }
                     }
                  }
               }

               if (!Config.flagMap.keySet().isEmpty() && BasicTasks.this.timeCount % (WasteAC.config().getInt("setting.reduce_flag") * 20) == 0) {
                  var2 = Config.flagMap.keySet().iterator();

                  while(var2.hasNext()) {
                     e = (String)var2.next();
                     var4 = ((HashMap)Config.flagMap.get(e)).keySet().iterator();

                     while(var4.hasNext()) {
                        check = (String)var4.next();
                        if ((Integer)((HashMap)Config.flagMap.get(e)).get(check) > 0) {
                           ((HashMap)Config.flagMap.get(e)).put(check, (Integer)((HashMap)Config.flagMap.get(e)).get(check) - 1);
                        }
                     }
                  }
               }

               if (Checks.isTypeEnable(WasteAPI.Combat[2], "type-b") && BasicTasks.this.timeCount % Checks.getType(WasteAPI.Combat[2], "type-b", "remove_tick", "int").IntValue == 0 && !KillAura.attackBot.keySet().isEmpty()) {
                  var2 = KillAura.attackBot.keySet().iterator();

                  while(var2.hasNext()) {
                     e = (String)var2.next();
                     if ((Integer)KillAura.attackBot.get(e) > 0) {
                        KillAura.attackBot.put(e, (Integer)KillAura.attackBot.get(e) - 1);
                     }
                  }
               }

               if ((double)Config.server.getTicksPerSecond() < Config.setting$process_lag$min_tps) {
                  BasicTasks.serverLag = true;
                  BasicTasks.lagRestore = 0;
               } else if (BasicTasks.serverLag) {
                  if (BasicTasks.lagRestore < Config.setting$process_lag$restore_tick) {
                     ++BasicTasks.lagRestore;
                  } else {
                     BasicTasks.serverLag = false;
                  }
               }

               if (!KillAura.isAura.isEmpty() && BasicTasks.this.timeCount % 10 == 0) {
                  var2 = KillAura.isAura.iterator();

                  while(var2.hasNext()) {
                     e = (String)var2.next();
                     cn.nukkit.Player p = Config.server.getPlayerExact(e);
                     if (p != null) {
                        WasteAPI.solveCheat((Event)null, false, p, WasteAPI.Combat[2], "type-c", "packet-Bad", "The player sent a BadPacket while placing blocks, which may be a KillAura");
                        if (WasteAC.getChecks().getBoolean(Checks.getWhereCheck(WasteAPI.Combat[2]) + "." + WasteAPI.Combat[2] + ".cancel")) {
                           p.setRotation(p.yaw, -89.899994);
                        }
                     }
                  }
               }

               if (Config.setting$test_mode$enable && !TestSystem.placeBlock.isEmpty() && BasicTasks.this.timeCount % 6000 == 0) {
                  int[] count = new int[]{0};
                  List l = new ArrayList();
                  TestSystem.placeBlock.forEach((block) -> {
                     block.onBreak((Item)null);
                     l.add(block);
                     int var10002 = count[0]++;
                  });
                  TestSystem.placeBlock.removeAll(l);
                  Config.server.broadcastMessage(Config.prefix + " §bBlocks cleared | Count: " + count[0]);
               }

            }
         }, 1);
         Config.logger.info("§eWasteAC启动成功 当前版本: " + WasteAC.getInstance().getDescription().getVersion());
         Config.logger.info("§e目前已加载 " + Checks.getEnableCount() + " 项功能");
         Config.logger.info("§e命令: 输入wac help提示命令帮助");
      }

   }
}
