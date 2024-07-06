package org.stone72.Listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerToggleFlightEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import org.stone72.Module.Moving.AirJump;
import org.stone72.Module.Moving.Fly;
import org.stone72.Module.Moving.HighJump;
import org.stone72.Module.Moving.NoSlow;
import org.stone72.Module.Moving.Speed;
import org.stone72.Module.Moving.ThroughWall;

public class Moving implements Listener {
   @EventHandler
   public void moving(DataPacketReceiveEvent e) {
      (new Speed()).check(e);
      (new Fly()).check(e);
      (new HighJump()).check(e);
      (new AirJump()).check(e);
   }

   @EventHandler
   public void moving(PlayerMoveEvent e) {
      (new NoSlow()).check(e);
      (new ThroughWall()).check(e);
   }

   @EventHandler
   public void check(PlayerToggleFlightEvent e) {
      (new Fly()).check(e);
   }
}
