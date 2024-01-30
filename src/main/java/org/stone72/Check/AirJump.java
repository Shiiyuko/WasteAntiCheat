/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.entity.Entity
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerJumpEvent
 *  java.lang.Object
 */
package org.stone72.Check;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJumpEvent;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class AirJump
implements Listener {
    @EventHandler
    public void onJump(PlayerJumpEvent playerJumpEvent) {
        boolean bl;
        PlayerJumpEvent a = playerJumpEvent;
        AirJump a2 = this;
        PlayerJumpEvent airJump = a;
        Entity entity = airJump.getPlayer().riding;
        boolean bl2 = bl = !airJump.getPlayer().onGround && !Method.edge(a.getPlayer().getLocation(), 1);
        if (Config.getCheck$enable().contains((Object)"AirJump") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName()) && bl && entity == null) {
            if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                WasteAC.PlusVl(a.getPlayer().getName(), "AirJump");
            }
            if (Config.isSetting$debug()) {
                Method.sendDebug(a.getPlayer(), "AirJump", "[onGround-false/edge-false]", "The player jumping in the air may be AirJump");
            }
        }
    }

    public AirJump() {
        AirJump a;
    }
}

