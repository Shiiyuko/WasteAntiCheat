package org.stone72.Module.Moving;

import cn.nukkit.event.player.PlayerMoveEvent;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Math.Judge;

public class ThroughWall {
   public void check(PlayerMoveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Moving[3], e.getPlayer())) {
         Object riding = e.getPlayer().riding;
         if (Judge.isInBlock(e.getTo()) && e.getFrom().getLocation().setY(0.0).distance(e.getTo().getLocation().setY(0.0)) > Checks.getType(WasteAPI.Moving[3], "type-a", "max_move", "double").DoubleValue && e.getPlayer().isSurvival() && !e.getPlayer().isSwimming() && riding == null && !Judge.isGetOff(e.getFrom())) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Moving[3], "type-a", "inBlock-true block-" + e.getTo().getLevel().getBlock(e.getTo()), "The player can still move within the blocks, which may be through Wall (NoClip)");
         }
      }

   }
}
