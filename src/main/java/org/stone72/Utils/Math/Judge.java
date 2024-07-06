package org.stone72.Utils.Math;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockIce;
import cn.nukkit.block.BlockLiquid;
import cn.nukkit.block.BlockSlime;
import cn.nukkit.block.BlockSnowLayer;
import cn.nukkit.block.BlockSoulSand;
import cn.nukkit.block.BlockStairs;
import cn.nukkit.block.BlockTallGrass;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityVehicle;
import cn.nukkit.entity.passive.EntityDonkey;
import cn.nukkit.entity.passive.EntityHorse;
import cn.nukkit.entity.passive.EntityLlama;
import cn.nukkit.entity.passive.EntityPig;
import cn.nukkit.entity.passive.EntitySkeletonHorse;
import cn.nukkit.entity.passive.EntityZombieHorse;
import cn.nukkit.item.ItemBow;
import cn.nukkit.item.ItemCrossbow;
import cn.nukkit.item.ItemEdible;
import cn.nukkit.item.ItemTrident;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.network.protocol.types.AuthInputAction;
import cn.nukkit.utils.LoginChainData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Judge {
   public static boolean isHighMove(Player l) {
      return !isOnGround(l) && !isOnEdge(l, 0.0) && !isOnEdge(l, -1.0);
   }

   public static boolean isInRange(Position p, Position start, Position end) {
      return p.x <= end.x && p.x >= start.x && p.z <= end.z && p.z >= start.z;
   }

   public static boolean isSkate(Location l, int height) {
      List<Block> bl = new ArrayList();

      for(int a = 0; a <= height; ++a) {
         bl.add(l.getLevel().getBlock(l.add(-1.0, (double)(-a), 0.0)));
         bl.add(l.getLevel().getBlock(l.add(0.0, (double)(-a), -1.0)));
         bl.add(l.getLevel().getBlock(l.add(0.0, (double)(-a), 1.0)));
         bl.add(l.getLevel().getBlock(l.add(-1.0, (double)(-a), 1.0)));
         bl.add(l.getLevel().getBlock(l.add(1.0, (double)(-a), -1.0)));
         bl.add(l.getLevel().getBlock(l.add(1.0, (double)(-a), 1.0)));
         bl.add(l.getLevel().getBlock(l.add(-1.0, (double)(-a), -1.0)));
         bl.add(l.getLevel().getBlock(l.add(0.0, (double)(-a), 0.0)));
      }

      Iterator var5 = bl.iterator();

      Block b;
      do {
         if (!var5.hasNext()) {
            return false;
         }

         b = (Block)var5.next();
      } while(!(b instanceof BlockIce) && !(b instanceof BlockSlime));

      return true;
   }

   public static boolean isInGround(Player e) {
      Block b = Nukkit.getGroundBlock(e);
      if (!(b instanceof BlockAir) && !(b instanceof BlockTallGrass) && !(b instanceof BlockSnowLayer)) {
         return e.y == b.y + 1.0;
      } else {
         return false;
      }
   }

   public static boolean isGetOff(Location l) {
      Entity[] var1 = l.getLevel().getEntities();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Entity entity = var1[var3];
         if ((entity instanceof EntityVehicle || entity instanceof EntitySkeletonHorse || entity instanceof EntityZombieHorse || entity instanceof EntityHorse || entity instanceof EntityDonkey || entity instanceof EntityPig || entity instanceof EntityLlama) && l.getLocation().setY(0.0).distance(entity.getLocation().setY(0.0)) <= 2.35) {
            return true;
         }
      }

      return false;
   }

   public static boolean isTopJump(Location l) {
      Block[] blocks = new Block[]{l.getLevel().getBlock(l.add(-1.0, 2.0, 0.0)), l.getLevel().getBlock(l.add(1.0, 2.0, 0.0)), l.getLevel().getBlock(l.add(0.0, 2.0, -1.0)), l.getLevel().getBlock(l.add(0.0, 2.0, 1.0)), l.getLevel().getBlock(l.add(-1.0, 2.0, 1.0)), l.getLevel().getBlock(l.add(1.0, 2.0, -1.0)), l.getLevel().getBlock(l.add(1.0, 2.0, 1.0)), l.getLevel().getBlock(l.add(-1.0, 2.0, -1.0)), l.getLevel().getBlock(l.add(0.0, 2.0, 0.0))};
      Block[] var2 = blocks;
      int var3 = blocks.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Block b = var2[var4];
         if (b.isSolid()) {
            return true;
         }
      }

      return false;
   }

   public static boolean isUseItem(Player p) {
      return !p.getInventory().getItemInHand().isNull() && (p.getInventory().getItemInHand() instanceof ItemEdible || p.getInventory().getItemInHand() instanceof ItemBow || p.getInventory().getItemInHand() instanceof ItemTrident || p.getInventory().getItemInHand() instanceof ItemCrossbow);
   }

   public static boolean isToolbox(Player player) {
      LoginChainData clientData = player.getLoginChainData();
      if (clientData.getDeviceOS() == 1) {
         String[] modelSplit = clientData.getDeviceModel().split(" ");
         if (modelSplit.length != 0) {
            return !modelSplit[0].equals(modelSplit[0].toUpperCase());
         }
      }

      return false;
   }

   public static boolean isInBlock(Location l) {
      Block[] blocks = new Block[]{l.getLevel().getBlock(l.add(0.0, 1.0, 0.0)), l.getLevel().getBlock(l)};
      Block[] var2 = blocks;
      int var3 = blocks.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Block b = var2[var4];
         if (b.isNormalBlock() && !(b instanceof BlockAir) && !(b instanceof BlockTallGrass) && !(b instanceof BlockSnowLayer) && !b.canBeClimbed() && !b.canPassThrough() && !(b instanceof BlockStairs) && !(b instanceof BlockSoulSand)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isOnGround(Player e) {
      Object b = Nukkit.getGroundBlock(e);
      return !(b instanceof BlockAir) && !(b instanceof BlockTallGrass) && !(b instanceof BlockSnowLayer);
   }

   public static boolean isInLiquid(Location l) {
      Block[] blocks = new Block[]{l.getLevel().getBlock(l), l.getLevel().getBlock(l.add(1.0, 1.0, 0.0)), l.getLevel().getBlock(l.add(-1.0, 1.0, 0.0)), l.getLevel().getBlock(l.add(0.0, 1.0, 1.0)), l.getLevel().getBlock(l.add(0.0, 1.0, -1.0)), l.getLevel().getBlock(l.add(1.0, 1.0, 1.0)), l.getLevel().getBlock(l.add(-1.0, 1.0, -1.0)), l.getLevel().getBlock(l.add(1.0, 1.0, -1.0)), l.getLevel().getBlock(l.add(1.0, 0.0, 0.0)), l.getLevel().getBlock(l.add(1.0, 0.0, 0.0)), l.getLevel().getBlock(l.add(-1.0, 0.0, 0.0)), l.getLevel().getBlock(l.add(0.0, 0.0, 1.0)), l.getLevel().getBlock(l.add(0.0, 0.0, -1.0)), l.getLevel().getBlock(l.add(1.0, 0.0, 1.0)), l.getLevel().getBlock(l.add(-1.0, 0.0, -1.0)), l.getLevel().getBlock(l.add(1.0, 0.0, -1.0)), l.getLevel().getBlock(l.add(-1.0, 0.0, 1.0))};
      Block[] var2 = blocks;
      int var3 = blocks.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object b = var2[var4];
         if (b instanceof BlockLiquid) {
            return true;
         }
      }

      return false;
   }

   public static boolean isOnEdge(Location l, double a) {
      Block[] blocks = new Block[]{l.getLevel().getBlock(l.add(-1.0, a, 0.0)), l.getLevel().getBlock(l.add(1.0, a, 0.0)), l.getLevel().getBlock(l.add(0.0, a, -1.0)), l.getLevel().getBlock(l.add(0.0, a, 1.0)), l.getLevel().getBlock(l.add(-1.0, a, 1.0)), l.getLevel().getBlock(l.add(1.0, a, -1.0)), l.getLevel().getBlock(l.add(1.0, a, 1.0)), l.getLevel().getBlock(l.add(-1.0, a, -1.0))};
      Block[] var4 = blocks;
      int var5 = blocks.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object b = var4[var6];
         if (!(b instanceof BlockAir) && !(b instanceof BlockTallGrass) && !(b instanceof BlockSnowLayer)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isMove(PlayerAuthInputPacket p) {
      return p.getInputData().contains(AuthInputAction.LEFT) || p.getInputData().contains(AuthInputAction.RIGHT) || p.getInputData().contains(AuthInputAction.UP) || p.getInputData().contains(AuthInputAction.DOWN);
   }
}
