/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.block.BlockAir
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.block.BlockPlaceEvent
 *  cn.nukkit.math.Vector3
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.HashMap
 *  java.util.Map
 */
package org.stone72.Check;

import cn.nukkit.Server;
import cn.nukkit.block.BlockAir;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.math.Vector3;
import java.util.HashMap;
import java.util.Map;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class Scaffold
implements Listener {
    public static Map<String, Double> playerYaw = new HashMap();
    public static Map<String, Integer> placeTick = new HashMap();

    public Scaffold() {
        Scaffold a;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent blockPlaceEvent) {
        boolean bl;
        BlockPlaceEvent a = blockPlaceEvent;
        Scaffold a2 = this;
        BlockPlaceEvent scaffold = a;
        double d2 = scaffold.getPlayer().getPitch();
        double d3 = scaffold.getPlayer().getHeadYaw();
        boolean bl2 = bl = scaffold.getItem().getBlock() != null;
        if (Config.getCheck$enable().contains((Object)"Scaffold") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName())) {
            boolean bl3 = Server.getInstance().getTick() - (Integer)placeTick.getOrDefault((Object)a.getPlayer().getName(), (Integer) 0) < Config.getCheck$Scaffold$min_tick();
            placeTick.put((String) a.getPlayer().getName(), (Integer) Server.getInstance().getTick());
            if (Method.isInteger(d2)) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "Scaffold");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "Scaffold", new StringBuilder().insert(0, "pitch-").append(a.getPlayer().getPitch()).toString(), "When the player places blocks, the perspective is abnormal, which may be Scaffold");
                }
            }
            if (!bl) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "Scaffold");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "Scaffold", "itemGetBlock-Null", "When the player places blocks, the perspective is abnormal, which may be Scaffold");
                }
            }
            if (a.getBlock().y < a.getPlayer().y && d2 < 35.0 && a.getPlayer().getLocation().floor().setY(0.0).subtract(a.getBlock().getLocation().floor().setY(0.0)).length() <= 1.5) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "Scaffold");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "Scaffold", new StringBuilder().insert(0, "pitch-").append(a.getPlayer().getPitch()).toString(), "When the player places blocks, the perspective is abnormal, which may be Scaffold");
                }
            }
            if (playerYaw.containsKey((Object)a.getPlayer().getName()) && bl3 && Method.getGap((Double)playerYaw.get((Object)a.getPlayer().getName()), d3) > Config.getCheck$Scaffold$max_yaw() && a.getBlock().y < a.getPlayer().y && a.getPlayer().getLocation().floor().setY(0.0).subtract(a.getBlock().getLocation().floor().setY(0.0)).length() <= 1.5 && a.getPlayer().getLevel().getBlock((Vector3)a.getBlock().getLocation().add(0.0, -1.0, 0.0)) instanceof BlockAir) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "Scaffold");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "Scaffold", new StringBuilder().insert(0, "oldYaw-").append(playerYaw.get((Object)a.getPlayer().getName())).append(" nowYaw-").append(d3).toString(), "The player quickly place blocks and the perspective changes too much, which may be Scaffold");
                }
            }
            playerYaw.put((String) a.getPlayer().getName(), (Double) d3);
        }
    }
}

