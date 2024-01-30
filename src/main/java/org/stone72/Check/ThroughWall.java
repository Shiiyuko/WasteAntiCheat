/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.entity.Entity
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerMoveEvent
 *  java.lang.Object
 */
package org.stone72.Check;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class ThroughWall
implements Listener {
    public ThroughWall() {
        ThroughWall a;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        PlayerMoveEvent a = playerMoveEvent;
        ThroughWall a2 = this;
        Entity entity = a.getPlayer().riding;
        if (Config.getCheck$enable().contains((Object)"ThroughWall") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName()) && Method.inBlock(a.getFrom()) && a.getFrom().getLocation().setY(0.0).subtract(a.getTo().getLocation().setY(0.0)).length() > Config.getCheck$ThroughWall$max_move() && a.getPlayer().isSurvival() && !a.getPlayer().isSwimming() && entity == null && !Method.maybeGetOff(a.getFrom())) {
            if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                WasteAC.PlusVl(a.getPlayer().getName(), "ThroughWall");
            }
            if (Config.isSetting$debug()) {
                Method.sendDebug(a.getPlayer(), "ThroughWall", "inBlock-true", "The player is moving within the block, which may be ThroughWall (onClip and Phase, etc)");
            }
        }
    }
}

