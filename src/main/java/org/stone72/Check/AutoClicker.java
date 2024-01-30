/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.entity.EntityDamageByEntityEvent
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.HashMap
 *  java.util.Map
 */
package org.stone72.Check;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import java.util.HashMap;
import java.util.Map;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class AutoClicker
implements Listener {
    private static final Map<String, Integer> g = new HashMap();
    public static Map<String, Integer> cpsMap;
    private static final Map<String, Integer> ALLATORIxDEMO;

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        EntityDamageByEntityEvent a = entityDamageByEntityEvent;
        AutoClicker a2 = this;
        if (a.getDamager() instanceof Player && Config.getCheck$enable().contains((Object)"AutoClicker") && !Config.getSetting$excluded_worlds().contains((Object)a.getDamager().getLevel().getName())) {
            a.setCancelled(true);
            if (Server.getInstance().getTick() - (Integer)ALLATORIxDEMO.getOrDefault((Object)a.getDamager().getName(), (Integer) 0) >= 20) {
                cpsMap.put((String) a.getDamager().getName(), (Integer) 0);
                ALLATORIxDEMO.put((String) a.getDamager().getName(), (Integer) Server.getInstance().getTick());
            }
            cpsMap.put((String) a.getDamager().getName(), (Integer) ((Integer)cpsMap.getOrDefault((Object)a.getDamager().getName(), (Integer) 0) + 1));
            if (Server.getInstance().getTick() - (Integer)g.getOrDefault((Object)a.getDamager().getName(), (Integer) 0) >= a.getAttackCooldown()) {
                a.setCancelled(false);
                g.put((String) a.getDamager().getName(), (Integer) Server.getInstance().getTick());
            }
            a.setAttackCooldown(0);
            if ((Integer)cpsMap.getOrDefault((Object)a.getDamager().getName(), (Integer) 0) > Config.getCheck$AutoClicker$max_cps()) {
                if (!((Player)a.getDamager()).isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getDamager().getName(), "AutoClicker");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug((Player)a.getDamager(), "AutoClicker", new StringBuilder().insert(0, " cps-").append(cpsMap.getOrDefault((Object)a.getDamager().getName(), (Integer) 0)).toString(), "The player clicked too quickly during the attack, which may be due to AutoClicker");
                }
            }
        }
    }

    static {
        ALLATORIxDEMO = new HashMap();
        cpsMap = new HashMap();
    }

    public AutoClicker() {
        AutoClicker a;
    }
}

