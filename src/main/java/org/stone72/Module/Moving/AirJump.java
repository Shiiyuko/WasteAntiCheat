package org.stone72.Module.Moving;

import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.network.protocol.types.AuthInputAction;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Math.Judge;

public class AirJump {
   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Moving[0], e.getPlayer()) && e.getPlayer().isSurvival() && e.getPacket() instanceof PlayerAuthInputPacket) {
         PlayerAuthInputPacket packet = (PlayerAuthInputPacket)e.getPacket();
         boolean onGround = Judge.isOnGround(e.getPlayer()) || Judge.isOnEdge(e.getPlayer(), 0.0) || Judge.isOnEdge(e.getPlayer(), -1.0);
         if (Checks.isTypeEnable(WasteAPI.Moving[0], "type-a") && !onGround && e.getPlayer().y > -103.0 && packet.getInputData().contains(AuthInputAction.START_JUMPING)) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Moving[0], "type-a", "onGround-false", "When the player sends a packet, it contains abnormal data, which may be AirJump");
         }
      }

   }
}
