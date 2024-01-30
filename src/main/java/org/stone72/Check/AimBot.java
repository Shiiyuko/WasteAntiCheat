/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.entity.Entity
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.entity.EntityDamageByEntityEvent
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.StringBuilder
 */
package org.stone72.Check;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class AimBot
implements Listener {
    /*
     * WARNING - void declaration
     */
    private /* synthetic */ boolean ALLATORIxDEMO(Entity a, Entity a2) {
        AimBot a3 = this;
        Entity aimBot = a2;
        double d2 = ((Entity)aimBot).x - a.x;
        double d3 = ((Entity)aimBot).z - a.z;
        double d4 = d2;
        double d5 = d3;
        d2 = Math.asin((double)(d4 / Math.sqrt((double)(d4 * d4 + d5 * d5)))) / Math.PI * 180.0;
        if (d3 > 0.0) {
            d2 = -d2 + 180.0;
        }
        if (((Entity)a2).headYaw > d2) {
            return ((Entity)a2).headYaw - d2 < 0.025;
        }
        return d2 - ((Entity)a2).headYaw < 0.025;
    }

    public AimBot() {
        AimBot a;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        EntityDamageByEntityEvent a = entityDamageByEntityEvent;
        AimBot a2 = this;
        boolean bl = a2.ALLATORIxDEMO(a.getDamager(), a.getEntity());
        if (Config.getCheck$enable().contains((Object)"AimBot") && !Config.getSetting$excluded_worlds().contains((Object)a.getDamager().getLevel().getName()) && a.getDamager() instanceof Player && bl) {
            if (!((Player)a.getDamager()).isOp() && !Config.isSetting$debug()) {
                WasteAC.PlusVl(a.getDamager().getName(), "AimBot");
            }
            if (Config.isSetting$debug()) {
                Method.sendDebug((Player)a.getDamager(), "AimBot", new StringBuilder().insert(0, "nowYaw-").append(a.getDamager().getHeadYaw()).toString(), "The player aim too accurately when attacking, which may be due to AimBot");
            }
        }
    }
}

