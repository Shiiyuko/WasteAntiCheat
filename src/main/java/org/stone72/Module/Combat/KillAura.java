package org.stone72.Module.Combat;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.AnimatePacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.MovePlayerPacket;
import java.util.ArrayList;
import java.util.HashMap;
import org.stone72.WasteAC;
import org.stone72.API.WasteAPI;
import org.stone72.API.Event.PlayerAttackBotEvent;
import org.stone72.Function.ToolSystem;
import org.stone72.Thread.BasicTasks;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Entity.HumanBot;
import org.stone72.Utils.Math.Judge;

public class KillAura {
   public static final HashMap<String, Entity> attackMap = new HashMap();
   public static final HashMap<String, Integer> playerTick = new HashMap();
   public static final HashMap<String, HumanBot> playerBot = new HashMap();
   public static final HashMap<String, Integer> attackBot = new HashMap();
   public static final HashMap<String, Integer> aniCount = new HashMap();
   public static final ArrayList<String> inCombo = new ArrayList();
   public static final ArrayList<String> isAura = new ArrayList();
   public static final HashMap<String, Integer> packTick = new HashMap();
   public static final HashMap<String, Integer> hopTick = new HashMap();
   public static final HashMap<String, Integer> hopCount = new HashMap();

   public void check(EntityDamageByEntityEvent e) {
      if (e.getDamager() instanceof Player && WasteAPI.canCheck(WasteAPI.Combat[2], (Player)e.getDamager()) && !e.isCancelled() && !BasicTasks.serverLag && !ToolSystem.pingLag.contains(e.getDamager().getName())) {
         Player player = (Player)e.getDamager();
         Entity entity = e.getEntity();
         if (Checks.isTypeEnable(WasteAPI.Combat[2], "type-a")) {
            if (Config.server.getTick() - (Integer)playerTick.getOrDefault(player.getName(), 0) < Checks.getType(WasteAPI.Combat[2], "type-a", "min_switch_delay", "int").IntValue) {
               if (attackMap.containsKey(player.getName()) && !((Entity)attackMap.get(player.getName())).equals(entity)) {
                  WasteAPI.solveCheat(e, true, player, WasteAPI.Combat[2], "type-a", "tick-" + playerTick.get(player.getName()) + " damage-" + e.getDamage() + " finalDamage-" + e.getFinalDamage() + " originalDamage-" + e.getOriginalDamage(), "When player attack, they switch targets in a short period of time, which may be KillAura or AimBot");
               }
            } else {
               attackMap.remove(player.getName());
            }

            playerTick.put(player.getName(), Config.server.getTick());
            attackMap.put(player.getName(), entity);
         }

         if (Checks.isTypeEnable(WasteAPI.Combat[2], "type-b")) {
            if (!playerBot.containsKey(player.getName())) {
               WasteAC.getApi().spawnBot(player);
            } else {
               ((HumanBot)playerBot.get(player.getName())).addLifeTick(20L);
            }
         }

         if (Checks.isTypeEnable(WasteAPI.Combat[2], "type-b") && e.getEntity() instanceof HumanBot) {
            e.setDamage(0.0F);
            e.setAttackCooldown(0);
            e.setCancelled(false);
            HumanBot bot = (HumanBot)e.getEntity();
            Player p = (Player)e.getDamager();
            WasteAC.getInstance().getServer().getPluginManager().callEvent(new PlayerAttackBotEvent(p));
            attackBot.put(p.getName(), (Integer)attackBot.getOrDefault(p.getName(), 0) + 1);
            bot.addLifeTick(20L);
            if ((Integer)attackBot.get(p.getName()) > Checks.getType(WasteAPI.Combat[2], "type-b", "max_attack_bot", "int").IntValue) {
               WasteAPI.solveCheat(e, true, p, WasteAPI.Combat[2], "type-b", "attackBot-" + attackBot.get(p.getName()) + " damage-" + e.getDamage(), "The player attack FakePlayer multiple times during attacks, which may be KillAura or AimBot");
            }
         }
      }

   }

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Combat[2], e.getPlayer())) {
         if (Checks.isTypeEnable(WasteAPI.Combat[2], "type-c")) {
            if (e.getPacket() instanceof AnimatePacket && !inCombo.contains(e.getPlayer().getName())) {
               aniCount.put(e.getPlayer().getName(), (Integer)aniCount.getOrDefault(e.getPlayer().getName(), 0) + 1);
            }

            if (e.getPacket() instanceof InventoryTransactionPacket) {
               InventoryTransactionPacket p = (InventoryTransactionPacket)e.getPacket();
               if (p.transactionType == 3) {
                  if (!inCombo.contains(e.getPlayer().getName())) {
                     if ((Integer)aniCount.getOrDefault(e.getPlayer().getName(), 0) == 1) {
                        isAura.add(e.getPlayer().getName());
                     }

                     inCombo.add(e.getPlayer().getName());
                     aniCount.remove(e.getPlayer().getName());
                  }

                  packTick.put(e.getPlayer().getName(), Config.server.getTick());
               }
            }
         }

         if (Checks.isTypeEnable(WasteAPI.Combat[2], "type-d") && e.getPacket() instanceof MovePlayerPacket) {
            Block b = e.getPlayer().getLevel().getBlock(e.getPlayer().add(0.0, -1.0, 0.0));
            if (Config.server.getTick() - (Integer)hopTick.getOrDefault(e.getPlayer().getName(), 0) >= 20) {
               hopCount.put(e.getPlayer().getName(), 0);
               packTick.put(e.getPlayer().getName(), Config.server.getTick());
            }

            hopCount.put(e.getPlayer().getName(), (Integer)hopCount.getOrDefault(e.getPlayer().getName(), 0) + 1);
            if ((Integer)hopCount.getOrDefault(e.getPlayer().getName(), 0) > Checks.getType(WasteAPI.Combat[2], "type-d", "hop_count", "int").IntValue && !Judge.isTopJump(e.getPlayer().getLocation()) && b.isNormalBlock()) {
               WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Combat[2], "type-d", "hopDelay-" + hopCount.getOrDefault(e.getPlayer().getName(), 0), "The player are landing quickly multiple times, which may be a WrongMove");
            }
         }
      }

   }
}
