/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.block.Block
 *  cn.nukkit.block.BlockAir
 *  cn.nukkit.block.BlockIce
 *  cn.nukkit.block.BlockLiquid
 *  cn.nukkit.block.BlockStairs
 *  cn.nukkit.entity.Entity
 *  cn.nukkit.entity.item.EntityVehicle
 *  cn.nukkit.entity.passive.EntityDonkey
 *  cn.nukkit.entity.passive.EntityHorse
 *  cn.nukkit.entity.passive.EntityLlama
 *  cn.nukkit.entity.passive.EntityPig
 *  cn.nukkit.entity.passive.EntitySkeletonHorse
 *  cn.nukkit.entity.passive.EntityZombieHorse
 *  cn.nukkit.item.ItemBow
 *  cn.nukkit.item.ItemCrossbow
 *  cn.nukkit.item.ItemEdible
 *  cn.nukkit.item.ItemTrident
 *  cn.nukkit.level.Location
 *  cn.nukkit.math.Vector3
 *  cn.nukkit.nbt.tag.CompoundTag
 *  cn.nukkit.utils.LoginChainData
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Calendar
 *  java.util.Calendar$Builder
 *  java.util.TimeZone
 */
package org.stone72.Utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockIce;
import cn.nukkit.block.BlockLiquid;
import cn.nukkit.block.BlockStairs;
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
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.LoginChainData;
import java.util.Calendar;
import java.util.TimeZone;
import org.stone72.Check.KillAura;
import org.stone72.Utils.Config;
import org.stone72.Utils.FakeBot;
import org.stone72.WasteAC;

public class Method {
    public static void unBanPlayer(String a) {
        String string = a;
        Method.setPlayerData(string, "pardonDate", "");
        Method.setPlayerData(string, "check", "");
    }

    public static double getGap(double a, double a2) {
        if (a > a2) {
            return a - a2;
        }
        return a2 - a;
    }

    public static void seePlayer(Entity entity, Entity entity2) {
        Entity a;
        Entity a2 = entity2;
        Entity entity3 = a = entity;
        double d2 = entity3.x - a2.x;
        double d3 = entity3.y - a2.y;
        double d4 = entity3.z - a2.z;
        double d5 = d2;
        double d6 = d4;
        double d7 = Math.asin((double)(d5 / Math.sqrt((double)(d5 * d5 + d6 * d6)))) / Math.PI * 180.0;
        double d8 = d2;
        double d9 = d4;
        double d10 = d3;
        d2 = Math.round((double)(Math.asin((double)(d10 / Math.sqrt((double)(d8 * d8 + d9 * d9 + d10 * d3)))) / Math.PI * 180.0));
        if (d4 > 0.0) {
            d7 = -d7 + 180.0;
        }
        Entity entity4 = a;
        entity4.yaw = d7;
        entity4.headYaw = d7;
        a.pitch = d2;
    }

    public static void broadcastMessage(String string, String string2) {
        String a = string2;
        String a2 = string;
        Server.getInstance().broadcastMessage(Config.getSetting$broadcast_message().replace((CharSequence)"%type%", (CharSequence)a).replace((CharSequence)"%player%", (CharSequence)a2));
    }

    public static void kickPlayer(Player player, String string) {
        Object a = string;
        Player a2 = player;
        if (a2 != null) {
            Player player2 = a2;
            player2.kick(Config.getSetting$kick_message().replace((CharSequence)"%type%", (CharSequence)a).replace((CharSequence)"%player%", (CharSequence)player2.getName()));
        }
    }

    public Method() {
        Method a;
    }

    /*
     * WARNING - void declaration
     */
    public static void sendDebug(Player player, String a2, String a, String string3) {
        Player a3;
        Object a4 = string3;
        Player player2 = a3 = player;
        Player player3 = a3;
        player3.sendMessage(WasteAC.prefix + " §fName: " + player3.getName() + " §8| §bCheat: " + (String)a2 + " §8| §eData: " + (String)a + " §8| §fVl: 0 §8| §7Describe: " + (String)a4);
    }

    public static boolean highMove(Location location) {
        int a;
        Location location2 = location;
        boolean bl = true;
        int n = 0;
        int n2 = a = 1;
        while (n2 <= 3) {
            boolean bl2;
            if (n < 2) {
                ++n;
            }
            boolean bl3 = bl2 = !(location2.getLevel().getBlock((Vector3)location2.add(0.0, (double)(-a), 0.0)) instanceof BlockAir);
            if (bl2 || Method.edge(location2, n)) {
                bl = false;
            }
            n2 = ++a;
        }
        return bl;
    }

    public static boolean inBlock(Location location) {
        Location location2;
        Location location3 = location2 = location;
        Block block = location3.getLevel().getBlock(new Vector3(location2.x, location2.y + 1.0, location2.z));
        Block a = location3.getLevel().getBlock(new Vector3(location2.x, location2.y, location2.z));
        return block.isNormalBlock() && block.isSolid() && !block.canBeClimbed() && !block.canPassThrough() && !(block instanceof BlockStairs) || a.isNormalBlock() && a.isSolid() && !a.canBeClimbed() && !a.canPassThrough() && !(a instanceof BlockStairs);
    }

    public static String getDateString(Calendar a) {
        return a.get(1) + "/" + (a.get(2) + 1) + "/" + a.get(5) + "/" + a.get(10) + "/" + a.get(12) + "/" + a.get(13);
    }

    public static Calendar getCalendar2() {
        Calendar calendar = Calendar.getInstance((TimeZone)TimeZone.getTimeZone((String)"GMT+08:00"));
        return new Calendar.Builder().set(1, calendar.get(1)).set(2, calendar.get(2)).set(5, calendar.get(5)).set(10, 0).set(12, 0).set(13, 0).build();
    }

    /*
     * WARNING - void declaration
     */
    public static void setPlayerData(String string, String a, Object object) {
        Object a2 = object;
        String a3 = string;
        WasteAC.playerData().set(new StringBuilder().insert(0, a3).append(".").append((String)a).toString(), a2);
        WasteAC.playerData().save();
    }

    public static boolean maybeTopJump(Location location) {
        boolean a;
        Location location2 = location;
        boolean bl = !(location2.getLevel().getBlock((Vector3)location2.add(-1.0, 2.0, 0.0)) instanceof BlockAir);
        boolean bl2 = !(location2.getLevel().getBlock((Vector3)location2.add(1.0, 2.0, 0.0)) instanceof BlockAir);
        boolean bl3 = !(location2.getLevel().getBlock((Vector3)location2.add(0.0, 2.0, -1.0)) instanceof BlockAir);
        boolean bl4 = !(location2.getLevel().getBlock((Vector3)location2.add(0.0, 2.0, 1.0)) instanceof BlockAir);
        boolean bl5 = !(location2.getLevel().getBlock((Vector3)location2.add(-1.0, 2.0, 1.0)) instanceof BlockAir);
        boolean bl6 = !(location2.getLevel().getBlock((Vector3)location2.add(1.0, 2.0, -1.0)) instanceof BlockAir);
        boolean bl7 = !(location2.getLevel().getBlock((Vector3)location2.add(1.0, 2.0, 1.0)) instanceof BlockAir);
        boolean bl8 = !(location2.getLevel().getBlock((Vector3)location2.add(-1.0, 2.0, -1.0)) instanceof BlockAir);
        boolean bl9 = a = !(location2.getLevel().getBlock((Vector3)location2.add(0.0, 2.0, 0.0)) instanceof BlockAir);
        return bl || bl2 || bl3 || bl4 || bl5 || bl6 || bl7 || bl8 || a;
    }

    public static boolean isToolbox(Player player) {
        // Skidded from AntiToolbox XD
        return false;
    }

    public static Calendar parseDateString2(String string) {
        String string2;
        String string3 = string2 = string;
        String[] a = string3.split("/");
        if (string3.length() >= 3) {
            return new Calendar.Builder().set(1, Integer.parseInt((String)a[0])).set(2, Integer.parseInt((String)a[1]) - 1).set(5, Integer.parseInt((String)a[2])).set(10, 0).set(12, 0).set(13, 0).build();
        }
        return new Calendar.Builder().setInstant(0L).build();
    }

    public static boolean maybeUseItem(Player a) {
        return !a.getInventory().getItemInHand().isNull() && (a.getInventory().getItemInHand() instanceof ItemEdible || a.getInventory().getItemInHand() instanceof ItemBow || a.getInventory().getItemInHand() instanceof ItemTrident || a.getInventory().getItemInHand() instanceof ItemCrossbow);
    }

    public static boolean maybeGetOff(Location location) {
        int n;
        Location location2 = location;
        Entity[] entityArray = location2.getLevel().getEntities();
        int n2 = entityArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            Location a = entityArray[n];
            if ((a instanceof EntityVehicle || a instanceof EntitySkeletonHorse || a instanceof EntityZombieHorse || a instanceof EntityHorse || a instanceof EntityDonkey || a instanceof EntityPig || a instanceof EntityLlama) && location2.getLocation().setY(0.0).subtract(a.getLocation().setY(0.0)).length() <= 2.35) {
                return true;
            }
            n3 = ++n;
        }
        return false;
    }

    public static boolean maybeHighMove(Location location) {
        int a;
        Location location2 = location;
        boolean bl = true;
        int n = a = 1;
        while (n <= 2) {
            boolean bl2;
            boolean bl3 = bl2 = !(location2.getLevel().getBlock((Vector3)location2.add(0.0, (double)(-a), 0.0)) instanceof BlockAir);
            if (bl2 || Method.edge(location2, a)) {
                bl = false;
            }
            n = ++a;
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    public static void banPlayer(String string, String string2, int a, int a2, int a3, int a4, int a5, int a6) {
    // 懒得修了
        //        String string3 = string;
//        Calendar a8 = Method.getCalendar();
//        a8.add(1, (int)a7);
//        a8.add(2, (int)a6);
//        a8.add(5, (int)a5);
//        a8.add(10, (int)a4);
//        a8.add(12, (int)a3);
//        a8.add(13, (int)a2);
//        Method.setPlayerData(string2, "pardonDate", Method.getDateString((Calendar)a8));
//        Method.setPlayerData(string2, "check", a);
//        a8 = Server.getInstance().getPlayerExact(string3);
//        if (a8 != null) {
//            String string8 = a8;
//            string8.kick(Config.getSetting$banned_message().replace((CharSequence)"%year%", (CharSequence)String.valueOf((int)a7)).replace((CharSequence)"%month%", (CharSequence)String.valueOf((int)a6)).replace((CharSequence)"%day%", (CharSequence)String.valueOf((int)a5)).replace((CharSequence)"%hour%", (CharSequence)String.valueOf((int)a4)).replace((CharSequence)"%minute%", (CharSequence)String.valueOf((int)a3)).replace((CharSequence)"%second%", (CharSequence)String.valueOf((int)a2)).replace((CharSequence)"%type%", (CharSequence)a).replace((CharSequence)"%player%", (CharSequence)string8.getName()));
//        }
    }

    public static Vector3 getKnockVector3(Entity a, double a2, double a3, double a4) {
        double d2 = a2;
        double d3 = a3;
        double d4 = Math.sqrt((double)(d2 * d2 + d3 * d3));
        if (!(d4 <= 0.0)) {
            d4 = 1.0 / d4;
            Vector3 vector3 = new Vector3(a.motionX, 0.0, a.motionZ);
            vector3.x /= 2.0;
            vector3.z /= 2.0;
            vector3.x += a2 * d4 * a4;
            vector3.z += a3 * d4 * a4;
            return vector3;
        }
        return new Vector3(0.0, 0.0, 0.0);
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance((TimeZone)TimeZone.getTimeZone((String)"GMT+08:00"));
    }

    public static boolean nextToLiquid(Location location) {
        Location location2;
        Location location3 = location2 = location;
        Location location4 = location2;
        Block block = location3.getLevel().getBlock((Vector3)location4);
        Block block2 = location3.getLevel().getBlock((Vector3)location2.add(1.0, 1.0, 0.0));
        Block block3 = location4.getLevel().getBlock((Vector3)location2.add(-1.0, 1.0, 0.0));
        Block block4 = location3.getLevel().getBlock((Vector3)location2.add(0.0, 1.0, 1.0));
        Block block5 = location3.getLevel().getBlock((Vector3)location2.add(0.0, 1.0, -1.0));
        Block block6 = location3.getLevel().getBlock((Vector3)location2.add(1.0, 1.0, 1.0));
        Block block7 = location3.getLevel().getBlock((Vector3)location2.add(-1.0, 1.0, -1.0));
        Block block8 = location3.getLevel().getBlock((Vector3)location2.add(1.0, 1.0, -1.0));
        Block block9 = location3.getLevel().getBlock((Vector3)location2.add(-1.0, 1.0, 1.0));
        Block block10 = location3.getLevel().getBlock((Vector3)location2);
        Block block11 = location3.getLevel().getBlock((Vector3)location2.add(1.0, 0.0, 0.0));
        Block block12 = location3.getLevel().getBlock((Vector3)location2.add(-1.0, 0.0, 0.0));
        Block block13 = location3.getLevel().getBlock((Vector3)location2.add(0.0, 0.0, 1.0));
        Block block14 = location3.getLevel().getBlock((Vector3)location2.add(0.0, 0.0, -1.0));
        Block block15 = location3.getLevel().getBlock((Vector3)location2.add(1.0, 0.0, 1.0));
        Block block16 = location3.getLevel().getBlock((Vector3)location2.add(-1.0, 0.0, -1.0));
        Block block17 = location3.getLevel().getBlock((Vector3)location2.add(1.0, 0.0, -1.0));
        Location a = location3.getLevel().getBlock((Vector3)location2.add(-1.0, 0.0, 1.0)).getLocation();
        return block instanceof BlockLiquid || block2 instanceof BlockLiquid || block3 instanceof BlockLiquid || block4 instanceof BlockLiquid || block5 instanceof BlockLiquid || block6 instanceof BlockLiquid || block7 instanceof BlockLiquid || block8 instanceof BlockLiquid || block9 instanceof BlockLiquid || block10 instanceof BlockLiquid || block11 instanceof BlockLiquid || block12 instanceof BlockLiquid || block13 instanceof BlockLiquid || block14 instanceof BlockLiquid || block15 instanceof BlockLiquid || block16 instanceof BlockLiquid || block17 instanceof BlockLiquid;
    }

    public static boolean edge(Location location, int n) {
        int a = n;
        Location a2 = location;
        boolean bl = !(a2.getLevel().getBlock((Vector3)a2.add(-1.0, (double)(-a), 0.0)) instanceof BlockAir);
        boolean bl2 = !(a2.getLevel().getBlock((Vector3)a2.add(1.0, (double)(-a), 0.0)) instanceof BlockAir);
        boolean bl3 = !(a2.getLevel().getBlock((Vector3)a2.add(0.0, (double)(-a), -1.0)) instanceof BlockAir);
        boolean bl4 = !(a2.getLevel().getBlock((Vector3)a2.add(0.0, (double)(-a), 1.0)) instanceof BlockAir);
        boolean bl5 = !(a2.getLevel().getBlock((Vector3)a2.add(-1.0, (double)(-a), 1.0)) instanceof BlockAir);
        boolean bl6 = !(a2.getLevel().getBlock((Vector3)a2.add(1.0, (double)(-a), -1.0)) instanceof BlockAir);
        boolean bl7 = !(a2.getLevel().getBlock((Vector3)a2.add(1.0, (double)(-a), 1.0)) instanceof BlockAir);
        int n2 = a = !(a2.getLevel().getBlock((Vector3)a2.add(-1.0, (double)(-a), -1.0)) instanceof BlockAir) ? 1 : 0;
        return bl || bl2 || bl3 || bl4 || bl5 || bl6 || bl7 || a != 0;
    }

    public static void spawnBot(Player player) {
        Player player2 = player;
        if (!KillAura.playerBot.containsKey((Object)player2.getName())) {
            Position a = player2.getPosition();
            CompoundTag compoundTag = Entity.getDefaultNBT(a);
            compoundTag.putCompound("Skin", new CompoundTag().putByteArray("Data", WasteAC.DEFAULT_SKIN.getSkinData().data).putString("ModelId", WasteAC.DEFAULT_SKIN.getSkinId()));
            FakeBot object = (FakeBot) (a = new FakeBot(a.getChunk(), compoundTag));
            object.setSkin(WasteAC.DEFAULT_SKIN);
            object.setPosition((Vector3)player2.getLocation().add(new Vector3(player2.getDirectionPlane().x, 0.0, player2.getDirectionPlane().y).multiply(-2.5).add(0.0, 0.8, 0.0)));
            Object object2 = a;
            ((FakeBot)((Object)object2)).setTarget(player2.getName());
            ((FakeBot)((Object)object2)).spawnTo(player2);
            KillAura.playerBot.put((String) player2.getName(), (FakeBot) a);
        }
    }

    public static Calendar parseDateString(String string) {
        String string2;
        String string3 = string2 = string;
        String[] a = string3.split("/");
        if (string3.length() >= 6) {
            return new Calendar.Builder().set(1, Integer.parseInt((String)a[0])).set(2, Integer.parseInt((String)a[1]) - 1).set(5, Integer.parseInt((String)a[2])).set(10, Integer.parseInt((String)a[3])).set(12, Integer.parseInt((String)a[4])).set(13, Integer.parseInt((String)a[5])).build();
        }
        return new Calendar.Builder().setInstant(0L).build();
    }

    public static boolean maybeSkate(Location location) {
        Location location2 = location;
        boolean bl = location2.getLevel().getBlock((Vector3)location2.add(-1.0, -1.0, 0.0)) instanceof BlockIce;
        boolean bl2 = location2.getLevel().getBlock((Vector3)location2.add(1.0, -1.0, 0.0)) instanceof BlockIce;
        boolean bl3 = location2.getLevel().getBlock((Vector3)location2.add(0.0, -1.0, -1.0)) instanceof BlockIce;
        boolean bl4 = location2.getLevel().getBlock((Vector3)location2.add(0.0, -1.0, 1.0)) instanceof BlockIce;
        boolean bl5 = location2.getLevel().getBlock((Vector3)location2.add(-1.0, -1.0, 1.0)) instanceof BlockIce;
        boolean bl6 = location2.getLevel().getBlock((Vector3)location2.add(1.0, -1.0, -1.0)) instanceof BlockIce;
        boolean bl7 = location2.getLevel().getBlock((Vector3)location2.add(1.0, -1.0, 1.0)) instanceof BlockIce;
        boolean bl8 = location2.getLevel().getBlock((Vector3)location2.add(-1.0, -1.0, -1.0)) instanceof BlockIce;
        boolean a = location2.getLevel().getBlock((Vector3)location2.add(0.0, -1.0, 0.0)) instanceof BlockIce;
        return bl || bl2 || bl3 || bl4 || bl5 || bl6 || bl7 || bl8 || a;
    }

    public static boolean isInteger(double a) {
        return Math.floor((double)a) == a;
    }
}

