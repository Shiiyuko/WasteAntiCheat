/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.entity.Entity
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerMoveEvent
 *  java.lang.Object
 *  java.lang.StringBuilder
 */
package org.stone72.Check;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class Speed
implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        PlayerMoveEvent a = playerMoveEvent;
        Speed a2 = this;
        Entity entity = a.getPlayer().riding;
        if (Config.getCheck$enable().contains((Object)"Speed") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName()) && a.getFrom().getLocation().setY(0.0).subtract(a.getTo().getLocation().setY(0.0)).length() > Config.getCheck$Speed$max_move() && a.getFrom().getLocation().setY(0.0).subtract(a.getTo().getLocation().setY(0.0)).length() != 1.0 && !Method.inBlock(a.getFrom()) && a.getPlayer().isSurvival() && entity == null && !Method.maybeGetOff(a.getFrom()) && !Method.maybeHighMove(a.getFrom())) {
            if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                WasteAC.PlusVl(a.getPlayer().getName(), "Speed");
            }
            if (Config.isSetting$debug()) {
                Method.sendDebug(a.getPlayer(), "Speed", new StringBuilder().insert(0, "distance-").append(a.getFrom().getLocation().setY(0.0).subtract(a.getTo().getLocation().setY(0.0)).length()).toString(), "The player moves a long distance in an instant, which may be a Speed");
            }
        }
    }

    public Speed() {
        Speed a;
    }
}

