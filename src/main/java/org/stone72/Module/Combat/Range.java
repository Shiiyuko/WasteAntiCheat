package org.stone72.Module.Combat;

import cn.nukkit.Player;
import cn.nukkit.block.BlockAir;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import org.stone72.API.WasteAPI;
import org.stone72.Function.ToolSystem;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Math.Calculation;
import org.stone72.Utils.Math.Nukkit;

public class Range {
   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Combat[3], e.getPlayer()) && Checks.isTypeEnable(WasteAPI.Combat[3], "type-a") && e.getPacket() instanceof PlayerAuthInputPacket && ToolSystem.battleQueue.containsKey(e.getPlayer().getName()) && ToolSystem.battleQueue.get(e.getPlayer().getName()) instanceof Player) {
         Entity entity = (Entity)ToolSystem.battleQueue.get(e.getPlayer().getName());
         Player attacker = e.getPlayer();
         Location attackerEyeLocation = attacker.getLocation().add(0.0, 1.62, 0.0);
         Vector3 attackerDirection = attacker.getDirectionVector();
         double maxReach = Checks.getType(WasteAPI.Combat[3], "type-a", "normal_reach", "double").DoubleValue;
         double threshold = Checks.getType(WasteAPI.Combat[3], "type-a", "threshold", "double").DoubleValue;
         Vector3 eyePos = new Vector3(attackerEyeLocation.getX(), attacker.isSneaking() ? attackerEyeLocation.getY() - 0.08 : attackerEyeLocation.getY(), attackerEyeLocation.getZ());
         Vector3[] attackerRay = new Vector3[]{eyePos, attackerDirection};
         AxisAlignedBB victimAABB = entity.boundingBox.clone();
         victimAABB.expand(threshold, threshold, threshold);
         Vector3 intersectVec3d = Nukkit.intersectsRay(attackerRay, 0.0F, Float.MAX_VALUE, victimAABB);
         if (intersectVec3d != null) {
            Location intersect = new Location(intersectVec3d.getX(), intersectVec3d.getY(), intersectVec3d.getZ(), attacker.level);
            double interDistance = Calculation.round(intersect.distance(attackerEyeLocation), 2);
            if (interDistance > maxReach) {
               WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Combat[3], "type-a", "reach-" + interDistance + " block", "The player's attack is too far away, which may be a Range");
            }
         }

         ToolSystem.battleQueue.remove(e.getPlayer().getName());
      }

   }

   public void check(BlockPlaceEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Combat[3], e.getPlayer()) && e.getPlayer().isSurvival() && e.getBlock().isNormalBlock()) {
         Position pp = e.getPlayer().getPosition();
         Location bp = e.getBlock().getLocation();
         if (Checks.isTypeEnable(WasteAPI.Combat[3], "type-b")) {
            int vertical = (int)Calculation.change((double)(e.getPlayer().getFloorY() - e.getBlock().getFloorY()));
            int horizontal = (int)pp.setY(0.0).floor().distance(bp.setY(0.0).floor());
            if (!(e.getBlockReplace() instanceof BlockAir)) {
               --vertical;
               --horizontal;
            }

            if (vertical > Checks.getType(WasteAPI.Combat[3], "type-b", "max_vertical", "int").IntValue || horizontal > Checks.getType(WasteAPI.Combat[3], "type-b", "max_horizontal", "int").IntValue) {
               WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Combat[3], "type-b", "blockReach-" + vertical + " block-" + e.getBlock(), "When the player places blocks, the distance exceeds the normal distance, which may result in a Reach (BlockReach)");
            }
         }
      }

   }
}
