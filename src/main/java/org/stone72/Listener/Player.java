package org.stone72.Listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import org.stone72.Module.Player.InventoryMove;
import org.stone72.Module.Player.Scaffold;
import org.stone72.Module.Player.Teleport;

public class Player implements Listener {
   @EventHandler
   public void player(BlockPlaceEvent e) {
      (new Scaffold()).check(e);
   }

   @EventHandler
   public void player(DataPacketReceiveEvent e) {
      (new Scaffold()).check(e);
      (new InventoryMove()).check(e);
      (new Teleport()).check(e);
   }

   @EventHandler
   public void player(PlayerMoveEvent e) {
      (new Teleport()).check(e);
   }

   @EventHandler
   public void player(PlayerDropItemEvent e) {
      (new InventoryMove()).check(e);
   }

   @EventHandler
   public void player(InventoryCloseEvent e) {
      (new InventoryMove()).check(e);
   }

   @EventHandler
   public void player(InventoryClickEvent e) {
      (new InventoryMove()).check(e);
   }

   @EventHandler
   public void player(InventoryOpenEvent e) {
      (new InventoryMove()).check(e);
   }
}
