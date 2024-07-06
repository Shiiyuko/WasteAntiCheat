package org.stone72.Module.Player;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockDirt;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.ItemSpawnEgg;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AnimatePacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import cn.nukkit.network.protocol.PlayerActionPacket;
import java.util.ArrayList;
import java.util.HashMap;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Math.Calculation;

public class Scaffold {
   public static final ArrayList<String> isStart = new ArrayList();
   public static final HashMap<String, Integer> invCount = new HashMap();
   public static final HashMap<String, Integer> aniCount = new HashMap();
   public static final ArrayList<String> isPlace = new ArrayList();
   public static final HashMap<String, Integer> packTick = new HashMap();

   public void check(BlockPlaceEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Player[1], e.getPlayer()) && Checks.isTypeEnable(WasteAPI.Player[1], "type-a") && Calculation.isInteger(e.getPlayer().getPitch())) {
         WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[1], "type-a", "pitch-" + e.getPlayer().getPitch(), "When the player places blocks, the perspective is abnormal, which may be Scaffold");
      }

   }

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Player[1], e.getPlayer())) {
         if (Checks.isTypeEnable(WasteAPI.Player[1], "type-b")) {
            if (e.getPacket() instanceof PlayerActionPacket) {
               PlayerActionPacket p = (PlayerActionPacket)e.getPacket();
               Block b = e.getPlayer().level.getBlock(new Vector3((double)p.x, (double)p.y, (double)p.z));
               if (p.action == 28 && !(e.getPlayer().getInventory().getItemInHand() instanceof ItemSpawnEgg) && (b instanceof BlockDirt || !b.canBeActivated())) {
                  isStart.add(e.getPlayer().getName());
                  aniCount.remove(e.getPlayer().getName());
               }

               if (p.action == 29 && isStart.contains(e.getPlayer().getName())) {
                  if (!isPlace.contains(e.getPlayer().getName()) && !e.getPlayer().getInventory().getItemInHand().isNull()) {
                     if ((Integer)invCount.getOrDefault(e.getPlayer().getName(), 0) < 2) {
                        WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[1], "type-b", "packet-Bad", "The player sent a BadPacket while placing blocks, which may be a Scaffold");
                     }

                     isPlace.add(e.getPlayer().getName());
                  }

                  aniCount.remove(e.getPlayer().getName());
                  invCount.remove(e.getPlayer().getName());
                  isStart.remove(e.getPlayer().getName());
               }
            }

            if (e.getPacket() instanceof InventoryTransactionPacket) {
               InventoryTransactionPacket p = (InventoryTransactionPacket)e.getPacket();
               if (p.transactionType == 2 && isStart.contains(e.getPlayer().getName())) {
                  if (Config.server.getTick() - (Integer)packTick.getOrDefault(e.getPlayer().getName(), 0) >= Checks.getType(WasteAPI.Player[1], "type-b", "place_tick", "int").IntValue) {
                     isPlace.remove(e.getPlayer().getName());
                     invCount.remove(e.getPlayer().getName());
                     isStart.remove(e.getPlayer().getName());
                  }

                  packTick.put(e.getPlayer().getName(), Config.server.getTick());
                  if (!isPlace.contains(e.getPlayer().getName())) {
                     invCount.put(e.getPlayer().getName(), (Integer)invCount.getOrDefault(e.getPlayer().getName(), 0) + 1);
                  }
               }
            }

            if (e.getPacket() instanceof AnimatePacket && isStart.contains(e.getPlayer().getName())) {
               aniCount.put(e.getPlayer().getName(), (Integer)aniCount.getOrDefault(e.getPlayer().getName(), 0) + 1);
            }
         }

         if (e.getPacket() instanceof MobEquipmentPacket) {
            MobEquipmentPacket p = (MobEquipmentPacket)e.getPacket();
            if (Checks.isTypeEnable(WasteAPI.Player[1], "type-b") && p.item == null && isStart.contains(e.getPlayer().getName())) {
               if ((Integer)aniCount.getOrDefault(e.getPlayer().getName(), 0) > 1) {
                  WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[1], "type-b", "packet-Bad", "The player sent a BadPacket while placing blocks, which may be a Scaffold");
               }

               aniCount.remove(e.getPlayer().getName());
            }

            if (Checks.isTypeEnable(WasteAPI.Player[1], "type-c") && p.eid == 0L) {
               WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[1], "type-c", "packet-Bad", "The player sent a BadPacket while placing blocks, which may be a Scaffold");
            }
         }
      }

   }
}
