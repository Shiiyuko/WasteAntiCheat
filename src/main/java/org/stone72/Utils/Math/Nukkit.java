package org.stone72.Utils.Math;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.network.protocol.types.AuthInputAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.stone72.API.WasteAPI;
import org.stone72.Function.ToolSystem;

public class Nukkit {
   public static Block getCollisionBlock(AxisAlignedBB newBB, Location location) {
      AxisAlignedBB bb2 = newBB.clone();
      bb2.expand(-0.2, -0.2, -0.2);
      ArrayList blocksAround = getBlocksAround(location.level, newBB);
      ArrayList collidingBlocks = getCollisionBlocks(blocksAround, newBB);
      Iterator var5 = collidingBlocks.iterator();

      while(var5.hasNext()) {
         Block block = (Block)var5.next();
         if (!block.canPassThrough()) {
            AxisAlignedBB bb = block.getBoundingBox();
            if (bb != null && bb.getMaxY() - newBB.getMinY() >= 0.6 && block.collidesWithBB(bb2)) {
               return block;
            }
         }
      }

      return null;
   }

   private static ArrayList<Block> getCollisionBlocks(ArrayList<Block> blocks, AxisAlignedBB bb) {
      ArrayList<Block> collisionBlocks = new ArrayList();
      Iterator var3 = blocks.iterator();

      while(var3.hasNext()) {
         Block b = (Block)var3.next();
         if (b.collidesWithBB(bb, true)) {
            collisionBlocks.add(b);
         }
      }

      return collisionBlocks;
   }

   private static ArrayList<Block> getBlocksAround(Level level, AxisAlignedBB bb) {
      ArrayList<Block> blocksAround = new ArrayList();
      Vector3 vector3 = new Vector3();
      bb.forEach((x, y, z) -> {
         Block block = level.getBlock(vector3.setComponents((double)x, (double)y, (double)z));
         blocksAround.add(block);
      });
      return blocksAround;
   }

   public static int getMoveDirection(PlayerAuthInputPacket p) {
      if (p.getInputData().contains(AuthInputAction.DOWN)) {
         return 1;
      } else if ((p.getInputData().contains(AuthInputAction.RIGHT) || p.getInputData().contains(AuthInputAction.LEFT)) && (!p.getInputData().contains(AuthInputAction.RIGHT) || !p.getInputData().contains(AuthInputAction.LEFT))) {
         return p.getInputData().contains(AuthInputAction.UP) ? 3 : 2;
      } else {
         return 0;
      }
   }

   public static void seePlayer(Entity p, Entity e) {
      double dx = p.x - e.x;
      double dy = p.y - e.y;
      double dz = p.z - e.z;
      double Yaw = Math.asin(dx / Math.sqrt(dx * dx + dz * dz)) / Math.PI * 180.0;
      double Pitch = (double)Math.round(Math.asin(dy / Math.sqrt(dx * dx + dz * dz + dy * dy)) / Math.PI * 180.0);
      if (dz > 0.0) {
         Yaw = -Yaw + 180.0;
      }

      p.yaw = Yaw;
      p.headYaw = Yaw;
      p.pitch = Pitch;
   }

   public static Block getGroundBlock(Player p) {
      Location l = p.getLocation();
      if (l.y - Math.floor(l.y) >= 0.5) {
         l.setY(Math.floor(l.y) + 1.0);
      }

      return l.getLevel().getBlock(l.add(0.0, -1.0, 0.0));
   }

   public static Vector3 intersectsRay(Vector3[] ray, float minDist, float maxDist, AxisAlignedBB bb) {
      Vector3 invDir = new Vector3(1.0 / ray[1].getX(), 1.0 / ray[1].getY(), 1.0 / ray[1].getZ());
      boolean signDirX = invDir.getX() < 0.0;
      boolean signDirY = invDir.getY() < 0.0;
      boolean signDirZ = invDir.getZ() < 0.0;
      Vector3 max = new Vector3(bb.getMaxX(), bb.getMaxY(), bb.getMaxZ());
      Vector3 min = new Vector3(bb.getMinX(), bb.getMinY(), bb.getMinZ());
      Vector3 bbox = signDirX ? max : min;
      double tmin = (bbox.getX() - ray[0].getX()) * invDir.getX();
      bbox = signDirX ? min : max;
      double tmax = (bbox.getX() - ray[0].getX()) * invDir.getX();
      bbox = signDirY ? max : min;
      double tymin = (bbox.getY() - ray[0].getY()) * invDir.getY();
      bbox = signDirY ? min : max;
      double tymax = (bbox.getY() - ray[0].getY()) * invDir.getY();
      if (!(tmin > tymax) && !(tymin > tmax)) {
         if (tymin > tmin) {
            tmin = tymin;
         }

         if (tymax < tmax) {
            tmax = tymax;
         }

         bbox = signDirZ ? max : min;
         double tzmin = (bbox.getZ() - ray[0].getZ()) * invDir.getZ();
         bbox = signDirZ ? min : max;
         double tzmax = (bbox.getZ() - ray[0].getZ()) * invDir.getZ();
         if (!(tmin > tzmax) && !(tzmin > tmax)) {
            if (tzmin > tmin) {
               tmin = tzmin;
            }

            if (tzmax < tmax) {
               tmax = tzmax;
            }

            if (tmin < (double)maxDist && tmax > (double)minDist) {
               Vector3 dir = new Vector3(ray[1].getX(), ray[1].getY(), ray[1].getZ());
               Vector3 orig = new Vector3(ray[0].getX(), ray[0].getY(), ray[0].getZ());
               return orig.add(dir.multiply(tmin));
            } else {
               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static void checkSpeed(PlayerAuthInputPacket packet, Double speed, Location loc, DataPacketReceiveEvent e, String state) {
      if (((double)packet.getDelta().x > speed || (double)packet.getDelta().x < -speed || (double)packet.getDelta().z > speed || (double)packet.getDelta().z < -speed) && !Judge.isHighMove(e.getPlayer()) && !Judge.isInBlock(loc) && !ToolSystem.isExplosion.contains(e.getPlayer().getName()) && e.getPlayer().isSurvival()) {
         WasteAPI.solveCheat(e, true, e.getPlayer(), "Speed", "type-c", "deltaX-" + packet.getDelta().x + " deltaZ-" + packet.getDelta().z + " moveState-" + state, "When the player is moving, their speed exceeds the normal speed, which may be Speed");
      }

   }
}
