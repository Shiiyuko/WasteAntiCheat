/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.entity.Entity
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerMoveEvent
 *  cn.nukkit.event.player.PlayerToggleFlightEvent
 *  cn.nukkit.item.ItemElytra
 *  cn.nukkit.level.Location
 *  java.lang.Object
 *  java.lang.StringBuilder
 */
package org.stone72.Check;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerToggleFlightEvent;
import cn.nukkit.item.ItemElytra;
import cn.nukkit.level.Location;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class WrongRise
implements Listener {
    @EventHandler
    public void onFlight(PlayerToggleFlightEvent playerToggleFlightEvent) {
        PlayerToggleFlightEvent a = playerToggleFlightEvent;
        WrongRise a2 = this;
        if (Config.getCheck$enable().contains((Object)"WrongRise") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName()) && a.getPlayer().isSurvival()) {
            if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                WasteAC.PlusVl(a.getPlayer().getName(), "WrongRise");
            }
            if (Config.isSetting$debug()) {
                Method.sendDebug(a.getPlayer(), "WrongRise", new StringBuilder().insert(0, "gameMode-").append(a.getPlayer().getGamemode()).toString(), "The player is attempting to fly in survival mode, which may be Fly");
            }
        }
    }

    public WrongRise() {
        WrongRise a;
    }

    @EventHandler
    public void onWrongRise(PlayerMoveEvent playerMoveEvent) {
        PlayerMoveEvent a = playerMoveEvent;
        WrongRise a2 = this;
        PlayerMoveEvent wrongRise = a;
        Entity entity = wrongRise.getPlayer().riding;
        boolean bl = wrongRise.getPlayer().onGround;
        if (Config.getCheck$enable().contains((Object)"WrongRise") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName())) {
            if (!(bl || a.getTo().y != a.getFrom().y || Method.isInteger(a.getTo().y) || !a.getPlayer().isSurvival() || Method.nextToLiquid((Location)a.getPlayer()) || entity != null || Method.maybeGetOff(a.getFrom()))) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "WrongRise");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "WrongRise", "onGround-false", "When the player is not on the ground, he can still move, which may be Fly");
                }
            }
            if (!bl && Method.highMove(a.getFrom().getLocation()) && a.getTo().y > a.getFrom().y && a.getTo().y - a.getFrom().y > Config.getCheck$WrongRise$max_rise() && a.getPlayer().isSurvival() && !(a.getPlayer().getInventory().getChestplate() instanceof ItemElytra)) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "WrongRise");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "WrongRise", new StringBuilder().insert(0, "formY-").append(a.getFrom().y).append(" toY-").append(a.getTo().y).toString(), "When the player is not on the ground, he can still move up , which may be Fly or HighJump");
                }
            }
        }
    }
}

