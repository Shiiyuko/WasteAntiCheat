package org.stone72.Module.Moving;

import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.network.protocol.types.AuthInputAction;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Math.Judge;

public class HighJump {
   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Moving[6], e.getPlayer()) && e.getPacket() instanceof PlayerAuthInputPacket) {
         PlayerAuthInputPacket packet = (PlayerAuthInputPacket)e.getPacket();
         if (Checks.isTypeEnable(WasteAPI.Moving[6], "type-a") && !Judge.isSkate(e.getPlayer(), 3) && packet.getInputData().contains(AuthInputAction.NORTH_JUMP) && (double)packet.getDelta().y > Checks.getType(WasteAPI.Moving[6], "type-a", "max_high", "double").DoubleValue) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Moving[6], "type-a", "deltaY-" + packet.getDelta().y, "When the player sends a packet, it contains abnormal data, which may be HighJump");
         }
      }

   }
}
