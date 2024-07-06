package org.stone72.Listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import org.stone72.Module.Misc.BadPacket;
import org.stone72.Module.Misc.ManyPackets;
import org.stone72.Module.Misc.Spammer;

public class Misc implements Listener {
   @EventHandler
   public void misc(PlayerJoinEvent e) {
      (new BadPacket()).check(e);
   }

   @EventHandler
   public void misc(DataPacketReceiveEvent e) {
      (new Spammer()).check(e);
      (new BadPacket()).check(e);
      (new ManyPackets()).check(e);
   }
}
