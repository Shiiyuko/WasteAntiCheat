package org.stone72.Module.Player;

import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import java.util.HashMap;
import org.stone72.API.WasteAPI;
import org.stone72.Function.ToolSystem;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Math.Judge;

public class Teleport {
   public static final HashMap<String, Vector3f> lastPos = new HashMap();

   public void check(PlayerMoveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Player[2], e.getPlayer()) && Checks.isTypeEnable(WasteAPI.Player[2], "type-a")) {
         double dis = e.getTo().y - e.getFrom().y;
         if (e.getTo().y > e.getFrom().y && dis > Checks.getType(WasteAPI.Player[2], "type-a", "max_move", "double").DoubleValue && e.getPlayer().isSurvival() && !Judge.isSkate(e.getFrom(), 3)) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[2], "type-a", "speed-" + dis, "The player has moved too far in a short period of time, which may be due to Teleport");
         }
      }

   }

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Player[2], e.getPlayer()) && e.getPacket() instanceof PlayerAuthInputPacket && Checks.isTypeEnable(WasteAPI.Player[2], "type-b")) {
         PlayerAuthInputPacket packet = (PlayerAuthInputPacket)e.getPacket();
         if (lastPos.containsKey(e.getPlayer().getName()) && packet.getDelta().x == 0.0F && packet.getDelta().z == 0.0F && !((Vector3f)lastPos.get(e.getPlayer().getName())).equals(packet.getPosition().floor().setY(0.0F)) && !ToolSystem.isTel.contains(e.getPlayer().getName()) && ToolSystem.isJoin.contains(e.getPlayer().getName()) && !ToolSystem.isRespawn.contains(e.getPlayer().getName()) && !ToolSystem.isTel.contains(e.getPlayer().getName())) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Player[2], "type-b", "packet-Bad", "The player has moved too far in a short period of time, which may be due to Teleport");
         }

         lastPos.put(e.getPlayer().getName(), packet.getPosition().floor().setY(0.0F));
      }

   }
}
