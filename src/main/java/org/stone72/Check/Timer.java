/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerMoveEvent
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
import java.util.HashMap;
import java.util.Map;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class Timer
implements Listener {
    private static final Map<String, Integer> g;
    private static final Map<String, Integer> ALLATORIxDEMO;

    @EventHandler
    public void onTimer(PlayerMoveEvent playerMoveEvent) {
        PlayerMoveEvent a = playerMoveEvent;
        Timer a2 = this;
        if (Config.getCheck$enable().contains((Object)"Timer") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName())) {
            if (Server.getInstance().getTick() - (Integer)ALLATORIxDEMO.getOrDefault((Object)a.getPlayer().getName(), (Integer) 0) >= 20) {
                g.put((String) a.getPlayer().getName(), (Integer) 0);
                ALLATORIxDEMO.put((String) a.getPlayer().getName(), (Integer) Server.getInstance().getTick());
            }
            g.put((String) a.getPlayer().getName(), (Integer) ((Integer)g.getOrDefault((Object)a.getPlayer().getName(), (Integer) 0) + 1));
            if ((Integer)g.get((Object)a.getPlayer().getName()) > Config.getCheck$Timer$max_packet()) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "Timer");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "Timer", new StringBuilder().insert(0, "packet-").append(g.get((Object)a.getPlayer().getName())).toString(), "The player has sent out too many moves, which may be a Timer");
                }
            }
        }
    }

    public Timer() {
        Timer a;
    }

    static {
        ALLATORIxDEMO = new HashMap();
        g = new HashMap();
    }
}

