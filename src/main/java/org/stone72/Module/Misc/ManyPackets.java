package org.stone72.Module.Misc;

import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import java.util.HashMap;
import org.stone72.API.WasteAPI;
import org.stone72.Function.ToolSystem;
import org.stone72.Thread.BasicTasks;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;

public class ManyPackets {
   public static final HashMap<String, Integer> packetTick = new HashMap();
   public static final HashMap<String, Integer> packetCount = new HashMap();
   public static final HashMap<String, Double> vl = new HashMap();

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Misc[1], e.getPlayer()) && e.getPlayer().isSurvival() && e.getPacket() instanceof PlayerAuthInputPacket && !BasicTasks.serverLag && !ToolSystem.pingLag.contains(e.getPlayer().getName()) && Checks.isTypeEnable(WasteAPI.Misc[1], "type-a")) {
         if (!vl.containsKey(e.getPlayer().getName())) {
            vl.put(e.getPlayer().getName(), 0.0);
         }

         if (Config.server.getTick() - (Integer)packetTick.getOrDefault(e.getPlayer().getName(), 0) >= Checks.getType(WasteAPI.Misc[1], "type-a", "max_tick", "int").IntValue) {
            packetCount.put(e.getPlayer().getName(), 0);
            packetTick.put(e.getPlayer().getName(), Config.server.getTick());
         }

         packetCount.put(e.getPlayer().getName(), (Integer)packetCount.getOrDefault(e.getPlayer().getName(), 0) + 1);
         if ((Integer)packetCount.getOrDefault(e.getPlayer().getName(), 0) > Checks.getType(WasteAPI.Misc[1], "type-a", "max_packet", "int").IntValue) {
            vl.put(e.getPlayer().getName(), (Double)vl.get(e.getPlayer().getName()) + Checks.getType(WasteAPI.Misc[1], "type-a", "outlier_judge", "double").DoubleValue);
            if ((Double)vl.get(e.getPlayer().getName()) >= Checks.getType(WasteAPI.Misc[1], "type-a", "max_valve", "double").DoubleValue) {
               WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Misc[1], "type-a", "packet-" + packetCount.getOrDefault(e.getPlayer().getName(), 0) + " ping-" + e.getPlayer().getPing(), "The player executed the action too quickly, which may be ManyPacket");
            }
         } else {
            vl.put(e.getPlayer().getName(), (Double)vl.get(e.getPlayer().getName()) - Checks.getType(WasteAPI.Misc[1], "type-a", "reduce", "double").DoubleValue);
         }

         if ((Double)vl.get(e.getPlayer().getName()) < 0.0) {
            vl.put(e.getPlayer().getName(), 0.0);
         }
      }

   }
}
