package org.stone72.Module.Moving;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockSnowLayer;
import cn.nukkit.block.BlockTallGrass;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.level.Location;
import org.stone72.API.WasteAPI;
import org.stone72.Function.ToolSystem;
import org.stone72.Thread.BasicTasks;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Math.Judge;

public class Velocity {
   public void check(EntityDamageEvent e, Location lastLoc, Player p) {
      if (WasteAPI.canCheck(WasteAPI.Moving[4], p) && e.getCause().equals(DamageCause.ENTITY_ATTACK) && !BasicTasks.serverLag && !ToolSystem.pingLag.contains(e.getEntity().getName()) && Checks.isTypeEnable(WasteAPI.Moving[4], "type-a")) {
         Block b = lastLoc.getLevel().getBlock(lastLoc.add(0.0, -1.0, 0.0));
         boolean canThan = !(b instanceof BlockAir) && !(b instanceof BlockTallGrass) && !(b instanceof BlockSnowLayer);
         int GroundY = b.getFloorY() + 1;
         double disY = p.y - (double)GroundY;
         if (canThan && disY < Checks.getType(WasteAPI.Moving[4], "type-a", "vertical", "double").DoubleValue && !Checks.getType(WasteAPI.Moving[4], "type-a", "data", "double list").DoubleListValue.contains(disY) && !Judge.isTopJump(e.getEntity())) {
            WasteAPI.solveCheat(e, true, p, WasteAPI.Moving[4], "type-a", "vertical-" + disY, "After the player is attacked, the knockback is smaller than the normal knockback, which may be due to Velocity");
         }
      }

   }
}
