package org.stone72.Module.Combat;

import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import java.util.HashMap;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;

public class AimBot {
   public static final HashMap<String, Float> lastYaw = new HashMap();
   public static final HashMap<String, Float> lastPitch = new HashMap();
   public static final HashMap<String, Double> vl = new HashMap();

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Combat[1], e.getPlayer()) && Checks.isTypeEnable(WasteAPI.Combat[1], "type-a") && e.getPacket() instanceof PlayerAuthInputPacket) {
         PlayerAuthInputPacket p = (PlayerAuthInputPacket)e.getPacket();
         float getLastYaw = (Float)lastYaw.getOrDefault(e.getPlayer().getName(), p.getYaw());
         if (p.getYaw() != getLastYaw) {
            float getLastPitch = (Float)lastPitch.getOrDefault(e.getPlayer().getName(), p.getPitch());
            if (!vl.containsKey(e.getPlayer().getName())) {
               vl.put(e.getPlayer().getName(), 0.0);
            }

            float distance = Math.abs(p.getYaw() - getLastYaw) % 360.0F;
            if (distance > 180.0F) {
               distance = 360.0F - distance;
            }

            if (getLastPitch == p.getPitch() && (double)distance >= Checks.getType(WasteAPI.Combat[1], "type-a", "angle_diff", "double").DoubleValue && (double)distance < Checks.getType(WasteAPI.Combat[1], "type-a", "max_diff", "double").DoubleValue && getLastPitch != 89.899994F && p.getPitch() != 89.899994F) {
               vl.put(e.getPlayer().getName(), (Double)vl.get(e.getPlayer().getName()) + Checks.getType(WasteAPI.Combat[1], "type-a", "outlier_judge", "double").DoubleValue);
               if ((Double)vl.get(e.getPlayer().getName()) >= Checks.getType(WasteAPI.Combat[1], "type-a", "max_valve", "double").DoubleValue) {
                  WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Combat[1], "type-a", "diff-" + distance + " pitch-" + p.getPitch(), "The player's movement perspective is too smooth, which may be due to AimBot");
               }
            } else {
               vl.put(e.getPlayer().getName(), (Double)vl.get(e.getPlayer().getName()) - Checks.getType(WasteAPI.Combat[1], "type-a", "reduce", "double").DoubleValue);
            }
         }

         lastYaw.put(e.getPlayer().getName(), p.getYaw());
         lastPitch.put(e.getPlayer().getName(), p.getPitch());
         if ((Double)vl.getOrDefault(e.getPlayer().getName(), 0.0) < 0.0) {
            vl.put(e.getPlayer().getName(), 0.0);
         }
      }

   }
}
