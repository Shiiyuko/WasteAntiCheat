/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerJoinEvent
 *  java.lang.Object
 */
package org.stone72.Check;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class BadPacket
implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        PlayerJoinEvent a = playerJoinEvent;
        BadPacket a2 = this;
        if (Config.getCheck$enable().contains((Object)"BadPacket") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName()) && Method.isToolbox(a.getPlayer())) {
            if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                WasteAC.PlusVl(a.getPlayer().getName(), "BadPacket");
            }
            if (Config.isSetting$debug()) {
                Method.sendDebug(a.getPlayer(), "BadPacket", "os-Switch", "The player's os is Switch, which is likely due to the use of tools such as ToolBox");
            }
        }
    }

    public BadPacket() {
        BadPacket a;
    }
}

