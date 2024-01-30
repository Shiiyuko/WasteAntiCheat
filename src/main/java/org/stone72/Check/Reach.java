/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.entity.EntityDamageByEntityEvent
 *  cn.nukkit.math.Vector3
 *  java.lang.Object
 *  java.lang.StringBuilder
 */
package org.stone72.Check;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.math.Vector3;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class Reach
implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        EntityDamageByEntityEvent a = entityDamageByEntityEvent;
        Reach a2 = this;
        EntityDamageByEntityEvent reach = a;
        Vector3 vector3 = Method.getKnockVector3(a.getEntity(), reach.getDamager().x - a.getEntity().x, a.getDamager().z - a.getEntity().z, a.getKnockBack());
        double d2 = reach.getDamager().getLocation().setY(0.0).subtract(vector3).subtract(a.getEntity().getLocation().setY(0.0)).length();
        if (Config.getCheck$enable().contains((Object)"Reach") && !Config.getSetting$excluded_worlds().contains((Object)a.getDamager().getLevel().getName()) && a.getDamager() instanceof Player && a.getEntity() instanceof Player && d2 > Config.getCheck$Reach$max_reach() && ((Player)a.getDamager()).isSurvival()) {
            if (!((Player)a.getDamager()).isOp() && !Config.isSetting$debug()) {
                WasteAC.PlusVl(a.getDamager().getName(), "Reach");
            }
            if (Config.isSetting$debug()) {
                Method.sendDebug((Player)a.getDamager(), "Reach", new StringBuilder().insert(0, "reach-").append(d2).toString(), "The player is too far away from the target during the attack, which may be a Reach");
            }
        }
    }

    public Reach() {
        Reach a;
    }
}

