package org.stone72.Module.Player;

import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.AnimatePacket;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.InteractPacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.PlayerActionPacket;
import java.util.ArrayList;
import java.util.HashMap;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;

public class InventoryMove {
   public static HashMap<String, Integer> moveTick = new HashMap();
   public static HashMap<String, Integer> openTick = new HashMap();
   public static ArrayList<String> hasAction = new ArrayList();
   public static ArrayList<String> isSwing = new ArrayList();
   public static ArrayList<String> isStart = new ArrayList();
   public static HashMap<String, Integer> dropTick = new HashMap();
   public static HashMap<String, Integer> dropDelay = new HashMap();

   public void check(PlayerDropItemEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Player[0], e.getPlayer()) && !e.isCancelled() && Checks.isTypeEnable(WasteAPI.Player[0], "type-d")) {
         int tick = Config.server.getTick() - (Integer)dropTick.getOrDefault(e.getPlayer().getName(), 0);
         boolean sameDelay = tick == (Integer)dropDelay.getOrDefault(e.getPlayer().getName(), 0) && tick < Checks.getType(WasteAPI.Player[0], "type-a", "max_tick", "int").IntValue;
         dropTick.put(e.getPlayer().getName(), Config.server.getTick());
         dropDelay.put(e.getPlayer().getName(), tick);
         if (sameDelay) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[0], "type-d", "dropTick-" + tick, "The player abnormal movement of items, this may be InventoryMove");
         }
      }

   }

   public void check(InventoryOpenEvent e) {
      if (Checks.isTypeEnable(WasteAPI.Player[0], "type-a")) {
         openTick.put(e.getPlayer().getName(), Config.server.getTick());
      }

   }

   public void check(InventoryClickEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Player[0], e.getPlayer()) && Checks.isTypeEnable(WasteAPI.Player[0], "type-a")) {
         int tick2 = Config.server.getTick() - (Integer)openTick.getOrDefault(e.getPlayer().getName(), 0);
         if (tick2 < Checks.getType(WasteAPI.Player[0], "type-a", "min_start", "int").IntValue && e.getSourceItem() != null) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[0], "type-a", "startTick-" + tick2 + "item-" + e.getSourceItem(), "The player abnormal movement of items, this may be InventoryMove");
         }
      }

   }

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Player[0], e.getPlayer()) && e.getPlayer().isSurvival()) {
         InventoryTransactionPacket packet;
         if (Checks.isTypeEnable(WasteAPI.Player[0], "type-b")) {
            if (e.getPacket() instanceof InteractPacket) {
               InteractPacket p = (InteractPacket)e.getPacket();
               if (p.action == 6) {
                  isStart.add(e.getPlayer().getName());
               }
            }

            if (e.getPacket() instanceof PlayerActionPacket) {
               PlayerActionPacket actionPacket = (PlayerActionPacket)e.getPacket();
               if (actionPacket.action == 29 && !hasAction.contains(e.getPlayer().getName())) {
                  hasAction.add(e.getPlayer().getName());
               }
            }

            if (e.getPacket() instanceof ContainerClosePacket) {
               hasAction.remove(e.getPlayer().getName());
               isStart.remove(e.getPlayer().getName());
            }

            if (e.getPacket() instanceof InventoryTransactionPacket) {
               packet = (InventoryTransactionPacket)e.getPacket();
               if (packet.transactionType == 0 && !hasAction.contains(e.getPlayer().getName()) && !isSwing.contains(e.getPlayer().getName()) && !isStart.contains(e.getPlayer().getName())) {
                  WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[0], "type-b", "packet-Bad", "The player abnormal movement of items, this may be InventoryMove");
               }
            }
         }

         if (Checks.isTypeEnable(WasteAPI.Player[0], "type-c")) {
            if (e.getPacket() instanceof AnimatePacket) {
               isSwing.add(e.getPlayer().getName());
            }

            if (e.getPacket() instanceof InventoryTransactionPacket) {
               packet = (InventoryTransactionPacket)e.getPacket();
               if (packet.transactionType == 0) {
                  if (!isSwing.contains(e.getPlayer().getName()) && !isStart.contains(e.getPlayer().getName())) {
                     WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[0], "type-c", "packet-Bad", "The player abnormal movement of items, this may be InventoryMove");
                  }

                  isSwing.remove(e.getPlayer().getName());
               }
            }
         }
      }

   }

   public void check(InventoryCloseEvent e) {
      if (Checks.isTypeEnable(WasteAPI.Player[0], "type-a")) {
         openTick.remove(e.getPlayer().getName());
      }

      if (Checks.isTypeEnable(WasteAPI.Player[0], "type-b")) {
         hasAction.remove(e.getPlayer().getName());
      }

   }
}
