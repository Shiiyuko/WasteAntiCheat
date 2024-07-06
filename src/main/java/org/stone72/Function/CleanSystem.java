package org.stone72.Function;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import org.stone72.Module.Combat.AutoClicker;
import org.stone72.Module.Combat.KillAura;
import org.stone72.Module.Misc.ManyPackets;
import org.stone72.Module.Moving.Fly;
import org.stone72.Module.Moving.NoSlow;
import org.stone72.Module.Player.InventoryMove;

public class CleanSystem implements Listener {
   @EventHandler
   public void cleanSystem(PlayerQuitEvent e) {
      AutoClicker.attackTick.remove(e.getPlayer().getName());
      AutoClicker.cpsTick.remove(e.getPlayer().getName());
      AutoClicker.cpsMap.remove(e.getPlayer().getName());
      InventoryMove.dropTick.remove(e.getPlayer().getName());
      InventoryMove.dropDelay.remove(e.getPlayer().getName());
      InventoryMove.moveTick.remove(e.getPlayer().getName());
      KillAura.attackMap.remove(e.getPlayer().getName());
      KillAura.attackBot.remove(e.getPlayer().getName());
      KillAura.playerTick.remove(e.getPlayer().getName());
      KillAura.playerBot.remove(e.getPlayer().getName());
      ManyPackets.packetTick.remove(e.getPlayer().getName());
      ManyPackets.packetCount.remove(e.getPlayer().getName());
      NoSlow.useTick.remove(e.getPlayer().getName());
      NoSlow.useCount.remove(e.getPlayer().getName());
      TestSystem.ffaPlayer.remove(e.getPlayer().getName());
      ToolSystem.isKnockback.remove(e.getPlayer().getName());
      ToolSystem.isExplosion.remove(e.getPlayer().getName());
      Fly.YSpeed.remove(e.getPlayer().getName());
      ToolSystem.isTel.remove(e.getPlayer().getName());
      ToolSystem.isJoin.remove(e.getPlayer().getName());
   }
}
