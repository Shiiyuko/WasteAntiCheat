package org.stone72.Function;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerItemConsumeEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.scheduler.AsyncTask;
import java.util.ArrayList;
import org.stone72.WasteAC;
import org.stone72.Utils.Worthless;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Math.Judge;
import org.stone72.Utils.Math.Nukkit;

public class TestSystem implements Listener {
   public static ArrayList<Block> placeBlock = new ArrayList();
   public static ArrayList<String> ffaPlayer = new ArrayList();
   public static ArrayList<String> authPacket = new ArrayList();
   public static ArrayList<String> otherPacket = new ArrayList();

   @EventHandler
   public void testMode(final PlayerChatEvent e) {
      if (Config.setting$test_mode$enable && Config.setting$test_mode$level.equals(e.getPlayer().getLevel()) && e.getPlayer().isOp()) {
         switch (e.getMessage().toLowerCase()) {
            case "*enable-authpacketshow":
               authPacket.add(e.getPlayer().getName());
               break;
            case "*enable-otherpacketshow":
               otherPacket.add(e.getPlayer().getName());
               break;
            case "*disable-authpacketshow":
               authPacket.remove(e.getPlayer().getName());
               break;
            case "*disable-otherpacketshow":
               otherPacket.remove(e.getPlayer().getName());
         }
      }

      if (e.getMessage().equals("WasteAntiCheat - Enable Super Invincible Rampage mode")) {
         if (Config.randomNumber.nextInt(100) == 0) {
            e.getPlayer().sendMessage("§l§cError §7>> §bWasteAntiCheat §chas activated §eSuper Invincible Rampage §cmode!");
            Config.serverTask.scheduleAsyncTask(WasteAC.getInstance(), new AsyncTask() {
               public void onRun() {
                  Worthless.sleep(1500L);
                  WasteAC.getApi().kickPlayer(e.getPlayer().getName(), "Enable Super Invincible Rampage mode");
               }
            });
         } else {
            e.getPlayer().sendMessage("§l§eWarning §7>> §bWasteAntiCheat §eSuper Invincible Rampage §cMode cannot be activated!");
         }
      }

   }

   @EventHandler
   public void testMode(EntityExplodeEvent e) {
      if (Config.setting$test_mode$enable && Config.setting$test_mode$level.equals(e.getEntity().getLevel())) {
         e.setCancelled();
      }

   }

   @EventHandler
   public void testMode(DataPacketReceiveEvent e) {
      boolean isAuthPacket = e.getPacket() instanceof PlayerAuthInputPacket;
      if (authPacket.contains(e.getPlayer().getName()) && isAuthPacket) {
         e.getPlayer().sendMessage("Received " + e.getPacket() + "||" + Nukkit.getGroundBlock(e.getPlayer()));
      }

      if (otherPacket.contains(e.getPlayer().getName()) && !isAuthPacket) {
         e.getPlayer().sendMessage("Receive " + e.getPacket());
         WasteAC.getApi().info("Receive " + e.getPacket());
      }

   }

   @EventHandler
   public void testMode(PlayerMoveEvent e) {
      if (Config.setting$test_mode$enable && Config.setting$test_mode$level.equals(e.getPlayer().getLevel())) {
         Position p = e.getPlayer().getPosition();
         if (Judge.isInRange(p, Config.setting$test_mode$ffa_range$start, Config.setting$test_mode$ffa_range$end)) {
            if (!ffaPlayer.contains(e.getPlayer().getName())) {
               ffaPlayer.add(e.getPlayer().getName());
               e.getPlayer().sendTitle("§bFFA已开启", "§7使用wac give命令获取物品");
            }
         } else if (ffaPlayer.contains(e.getPlayer().getName())) {
            ffaPlayer.remove(e.getPlayer().getName());
            e.getPlayer().sendTitle("§cFFA已关闭");
         }

         e.getPlayer().getFoodData().reset();
      }

   }

   @EventHandler
   public void testMode(PlayerItemConsumeEvent e) {
      if (Config.setting$test_mode$enable && Config.setting$test_mode$level.equals(e.getPlayer().getLevel()) && !Config.setting$test_mode$world_admin.contains(e.getPlayer().getName()) && ffaPlayer.contains(e.getPlayer().getName())) {
         e.getPlayer().getInventory().addItem(new Item[]{e.getItem()});
      }

   }

   @EventHandler
   public void testMode(BlockPlaceEvent e) {
      if (Config.setting$test_mode$enable && Config.setting$test_mode$level.equals(e.getPlayer().getLevel()) && !Config.setting$test_mode$world_admin.contains(e.getPlayer().getName())) {
         if (!ffaPlayer.contains(e.getPlayer().getName())) {
            e.setCancelled();
         } else if (Judge.isInRange(e.getBlock(), Config.setting$test_mode$ffa_range$start, Config.setting$test_mode$ffa_range$end)) {
            placeBlock.add(e.getBlock());
         } else {
            e.setCancelled();
         }
      }

   }

   @EventHandler
   public void testMode(PlayerLoginEvent e) {
      if (Config.setting$test_mode$enable && !Config.setting$test_mode$player_join && !e.getPlayer().isOp()) {
         e.setKickMessage("§l§cSorry. You can't join this server now");
         e.setCancelled(true);
      }

   }

   @EventHandler
   public void testMode(BlockBreakEvent e) {
      if (Config.setting$test_mode$enable && Config.setting$test_mode$level.equals(e.getPlayer().getLevel()) && !Config.setting$test_mode$world_admin.contains(e.getPlayer().getName())) {
         if (!ffaPlayer.contains(e.getPlayer().getName())) {
            e.setCancelled();
         } else if (!placeBlock.contains(e.getBlock())) {
            e.setCancelled();
         } else {
            placeBlock.remove(e.getBlock());
         }
      }

   }

   @EventHandler
   public void testMode(EntityDamageByEntityEvent e) {
      if (Config.setting$test_mode$enable && e.getDamager() instanceof Player && Config.setting$test_mode$level.equals(e.getDamager().getLevel()) && !Config.setting$test_mode$world_admin.contains(e.getDamager().getName())) {
         Player damager = (Player)e.getDamager();
         if ((!ffaPlayer.contains(damager.getName()) || !ffaPlayer.contains(e.getEntity().getName())) && e.getEntity() instanceof Player) {
            e.setCancelled();
         }
      }

   }
}
