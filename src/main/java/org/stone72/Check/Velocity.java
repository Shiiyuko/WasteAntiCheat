/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.entity.EntityDamageByEntityEvent
 *  cn.nukkit.event.player.PlayerMoveEvent
 *  cn.nukkit.scheduler.AsyncTask
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package org.stone72.Check;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.scheduler.AsyncTask;
import java.util.HashMap;
import java.util.Map;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class Velocity
implements Listener {
    public static Map<String, Boolean> isKnock = new HashMap();
    public static Map<String, Boolean> isBack = new HashMap();

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        EntityDamageByEntityEvent a = entityDamageByEntityEvent;
        Velocity a2 = this;
        if (a.getEntity() instanceof Player) {
            if (a.getDamage() < a.getEntity().getHealth()) {
                isKnock.put((String) a.getEntity().getName(), (Boolean) true);
            } else {
                isKnock.put((String) a.getEntity().getName(), (Boolean) false);
            }
            if (Config.getCheck$enable().contains((Object)"Velocity") && !Config.getSetting$excluded_worlds().contains((Object)a.getEntity().getLevel().getName())) {
                if (((Boolean)isKnock.getOrDefault((Object)a.getEntity().getName(), (Boolean) false)).booleanValue() && !((Boolean)isBack.getOrDefault((Object)a.getEntity().getName(), (Boolean) true)).booleanValue() && !a.isCancelled() && !a.getEntity().isImmobile()) {
                    if (!((Player)a.getEntity()).isOp() && !Config.isSetting$debug()) {
                        WasteAC.PlusVl(a.getEntity().getName(), "Velocity");
                    }
                    if (Config.isSetting$debug()) {
                        Method.sendDebug((Player)a.getEntity(), "Velocity", "move-false", "After being attacked, the player did not move, which may be due to Velocity");
                    }
                }
                //Server.getInstance().getScheduler().scheduleAsyncTask(WasteAC.plugin, (AsyncTask)new D(a2, (EntityDamageByEntityEvent)a));
            }
        }
    }

    @EventHandler
    public void onAttack(PlayerMoveEvent playerMoveEvent) {
        PlayerMoveEvent a = playerMoveEvent;
        Velocity a2 = this;
        if (((Boolean)isKnock.getOrDefault((Object)a.getPlayer().getName(), (Boolean) false)).booleanValue()) {
            if (a.getFrom().y != a.getTo().y) {
                isBack.put((String) a.getPlayer().getName(), (Boolean) true);
                isKnock.put((String) a.getPlayer().getName(), (Boolean) false);
                return;
            }
            isBack.put((String) a.getPlayer().getName(), (Boolean) false);
            isKnock.put((String) a.getPlayer().getName(), (Boolean) true);
        }
    }

    public Velocity() {
        Velocity a;
    }
}

