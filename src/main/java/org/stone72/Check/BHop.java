/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerMoveEvent
 *  cn.nukkit.event.player.PlayerTeleportEvent
 *  cn.nukkit.level.Location
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.HashMap
 *  java.util.Map
 */
package org.stone72.Check;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Location;
import java.util.HashMap;
import java.util.Map;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class BHop
implements Listener {
    public static final Map<String, Location> move2L;
    public static final Map<String, Integer> moveTick;
    public static final Map<String, Location> moveL;

    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        PlayerMoveEvent a = playerMoveEvent;
        BHop a2 = this;
        if (Config.getCheck$enable().contains((Object)"BHop") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName())) {
            if (!Method.maybeTopJump(a.getFrom()) && a.getPlayer().isSurvival() && !Method.maybeSkate(a.getFrom())) {
                if (Server.getInstance().getTick() - (Integer)moveTick.getOrDefault((Object)a.getPlayer().getName(), (Integer) 0) >= 10) {
                    if (!moveL.containsKey((Object)a.getPlayer().getName())) {
                        moveL.put((String) a.getPlayer().getName(), (Location) a.getFrom().getLocation());
                    } else if (!move2L.containsKey((Object)a.getPlayer().getName())) {
                        move2L.put((String) a.getPlayer().getName(), (Location) a.getFrom().getLocation());
                    } else if (((Location)moveL.get((Object)a.getPlayer().getName())).setY(0.0).subtract(((Location)move2L.get((Object)a.getPlayer().getName())).setY(0.0)).length() > Config.getCheck$BHop$normal_move()) {
                        if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                            WasteAC.PlusVl(a.getPlayer().getName(), "BHop");
                        }
                        if (Config.isSetting$debug()) {
                            Method.sendDebug(a.getPlayer(), "BHop", new StringBuilder().insert(0, "distance-").append(((Location)moveL.get((Object)a.getPlayer().getName())).setY(0.0).subtract(((Location)move2L.get((Object)a.getPlayer().getName())).setY(0.0)).length()).toString(), "The player's movement is abnormal, this may be BHop");
                        }
                        moveL.remove((Object)a.getPlayer().getName());
                        move2L.remove((Object)a.getPlayer().getName());
                    } else {
                        moveL.remove((Object)a.getPlayer().getName());
                        move2L.remove((Object)a.getPlayer().getName());
                    }
                    moveTick.put((String) a.getPlayer().getName(), (Integer) Server.getInstance().getTick());
                    return;
                }
            } else {
                moveL.remove((Object)a.getPlayer().getName());
                move2L.remove((Object)a.getPlayer().getName());
                moveTick.put((String) a.getPlayer().getName(), (Integer) Server.getInstance().getTick());
            }
        }
    }

    public BHop() {
        BHop a;
    }

    static {
        moveL = new HashMap();
        move2L = new HashMap();
        moveTick = new HashMap();
    }

    @EventHandler
    public void onMove(PlayerTeleportEvent playerTeleportEvent) {
        PlayerTeleportEvent a = playerTeleportEvent;
        BHop a2 = this;
        if (Config.getCheck$enable().contains((Object)"BHop") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName())) {
            moveL.remove((Object)a.getPlayer().getName());
            move2L.remove((Object)a.getPlayer().getName());
            moveTick.put((String) a.getPlayer().getName(), (Integer) Server.getInstance().getTick());
        }
    }
}

