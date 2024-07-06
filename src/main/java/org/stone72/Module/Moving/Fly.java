package org.stone72.Module.Moving;

import cn.nukkit.event.player.PlayerToggleFlightEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import java.util.HashMap;
import org.stone72.API.WasteAPI;
import org.stone72.Function.ToolSystem;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Math.Judge;

public class Fly {
   public static final HashMap<String, Float> YSpeed = new HashMap();

   public void check(PlayerToggleFlightEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Moving[5], e.getPlayer()) && e.getPlayer().isSurvival() && Checks.isTypeEnable(WasteAPI.Moving[5], "type-a")) {
         WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Moving[5], "type-a", "gameMode-" + e.getPlayer().getGamemode(), "The player is attempting to fly in survival mode, which may be Fly");
      }

   }

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Moving[5], e.getPlayer()) && e.getPlayer().isSurvival() && ToolSystem.isJoin.contains(e.getPlayer().getName())) {
         boolean onGround = Judge.isOnGround(e.getPlayer()) || Judge.isOnEdge(e.getPlayer(), 0.0) || Judge.isOnEdge(e.getPlayer(), -1.0);
         if (e.getPacket() instanceof PlayerAuthInputPacket && !onGround && e.getPlayer().y > -103.0) {
            PlayerAuthInputPacket packet = (PlayerAuthInputPacket)e.getPacket();
            if (Checks.isTypeEnable(WasteAPI.Moving[5], "type-b") && !ToolSystem.isTel.contains(e.getPlayer().getName()) && ToolSystem.isJoin.contains(e.getPlayer().getName())) {
               if ((Float)YSpeed.getOrDefault(e.getPlayer().getName(), 0.0F) == packet.getDelta().y && !Checks.getType(WasteAPI.Moving[5], "type-b", "data", "float list").FloatListValue.contains(packet.getDelta().y)) {
                  WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Moving[5], "type-b", "deltaY-" + packet.getDelta().y, "When the player sends a packet, it contains abnormal data, which may be WrongRise");
               }

               YSpeed.put(e.getPlayer().getName(), packet.getDelta().y);
            }

            if (Checks.isTypeEnable(WasteAPI.Moving[5], "type-c") && packet.getDelta().y < 0.0F && (double)packet.getDelta().y > -0.5 && Checks.getType(WasteAPI.Moving[5], "type-c", "data", "float list").FloatListValue.contains(packet.getDelta().y)) {
               WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Moving[5], "type-c", "deltaY-" + packet.getDelta().y, "When the player sends a packet, it contains abnormal data, which may be WrongRise");
            }
         }
      }

   }
}
