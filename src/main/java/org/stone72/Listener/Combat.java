package org.stone72.Listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import org.stone72.Module.Combat.AimBot;
import org.stone72.Module.Combat.AutoClicker;
import org.stone72.Module.Combat.KillAura;
import org.stone72.Module.Combat.Range;

public class Combat implements Listener {
   @EventHandler
   public void combat(DataPacketReceiveEvent e) {
      (new KillAura()).check(e);
      (new Range()).check(e);
      (new AimBot()).check(e);
   }

   @EventHandler
   public void combat(EntityDamageByEntityEvent e) {
      (new AutoClicker()).check(e);
      (new KillAura()).check(e);
   }

   @EventHandler
   public void combat(BlockPlaceEvent e) {
      (new Range()).check(e);
   }
}
