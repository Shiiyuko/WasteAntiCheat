/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.entity.Entity
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
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import java.util.HashMap;
import java.util.Map;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class NoSlow
implements Listener {
    public static Map<String, Integer> useTick = new HashMap();
    public static Map<String, Integer> useCount = new HashMap();

    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        PlayerMoveEvent a = playerMoveEvent;
        NoSlow a2 = this;
        Entity entity = a.getPlayer().riding;
        if (Config.getCheck$enable().contains((Object)"NoSlow") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName())) {
            if (Server.getInstance().getTick() - (Integer)useTick.getOrDefault((Object)a.getPlayer().getName(), (Integer) 0) >= Config.getCheck$NoSlow$clear_time()) {
                useCount.put((String) a.getPlayer().getName(), (Integer) 0);
                useTick.put((String) a.getPlayer().getName(), (Integer) Server.getInstance().getTick());
            }
            if (a.getPlayer().isUsingItem()) {
                useCount.put((String) a.getPlayer().getName(), (Integer) ((Integer)useCount.getOrDefault((Object)a.getPlayer().getName(), (Integer) 0) + 1));
            }
            if (a.getPlayer().isUsingItem() && a.getPlayer().isSprinting() && (Integer)useCount.get((Object)a.getPlayer().getName()) >= 14 && a.getPlayer().isSurvival() && Method.maybeUseItem(a.getPlayer()) && !Method.maybeHighMove(a.getFrom()) && !Method.inBlock(a.getFrom())) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "NoSlow");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "NoSlow", new StringBuilder().insert(0, "distance-").append(a.getFrom().getLocation().setY(0.0).subtract(a.getTo().getLocation().setY(0.0)).length()).toString(), "The player do not slow down when he should, which may be NoSlow");
                }
            }
            if ((a.getPlayer().isUsingItem() || a.getPlayer().isSneaking()) && (Integer)useCount.get((Object)a.getPlayer().getName()) >= 14 && a.getFrom().getLocation().setY(0.0).subtract(a.getTo().getLocation().setY(0.0)).length() > Config.getCheck$NoSlow$normal_move() && a.getPlayer().isSurvival() && Method.maybeUseItem(a.getPlayer()) && !Method.inBlock(a.getFrom()) && entity == null && !Method.maybeHighMove(a.getFrom())) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "NoSlow");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "NoSlow", new StringBuilder().insert(0, "distance-").append(a.getFrom().getLocation().setY(0.0).subtract(a.getTo().getLocation().setY(0.0)).length()).toString(), "The player do not slow down when he should, which may be NoSlow");
                }
            }
        }
    }

    public NoSlow() {
        NoSlow a;
    }
}

