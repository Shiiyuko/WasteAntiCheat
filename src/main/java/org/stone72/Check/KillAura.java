/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.entity.EntityDamageByEntityEvent
 *  cn.nukkit.event.entity.EntityDamageEvent
 *  cn.nukkit.scheduler.AsyncTask
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
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.scheduler.AsyncTask;
import java.util.HashMap;
import java.util.Map;
import org.stone72.Check.BHop;
import org.stone72.Check.Spammer;
import org.stone72.Utils.Config;
import org.stone72.Utils.FakeBot;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class KillAura
implements Listener {
    private static final Map<String, Integer> g;
    public static final Map<String, Integer> attackBot;
    private static final Map<String, String> ALLATORIxDEMO;
    public static final Map<String, FakeBot> playerBot;

    static {
        ALLATORIxDEMO = new HashMap();
        g = new HashMap();
        playerBot = new HashMap();
        attackBot = new HashMap();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent entityDamageEvent) {
        EntityDamageEvent a = entityDamageEvent;
        KillAura a2 = this;
        if (a.getEntity() instanceof FakeBot) {
            a.setCancelled(true);
            return;
        }
        if (a.getEntity() instanceof Player) {
            Spammer.isKnock.put((String) a.getEntity().getName(), (Boolean) true);
            if (a.getDamage() > a.getEntity().getHealth()) {
                BHop.moveL.remove((Object)a.getEntity().getName());
                BHop.move2L.remove((Object)a.getEntity().getName());
                BHop.moveTick.put((String) a.getEntity().getName(), (Integer) Server.getInstance().getTick());
            }
            //Server.getInstance().getScheduler().scheduleAsyncTask(WasteAC.plugin, (AsyncTask)new G(a2, (EntityDamageEvent)a));
        }
    }

    public KillAura() {
        KillAura a;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        EntityDamageByEntityEvent a = entityDamageByEntityEvent;
        KillAura a2 = this;
        if (a.getDamager() instanceof Player && a.getEntity() instanceof Player) {
            Player player = (Player)a.getDamager();
            Player player2 = (Player)a.getEntity();
            if (Config.getCheck$enable().contains((Object)"KillAura") && !Config.getSetting$excluded_worlds().contains((Object)player.getLevel().getName())) {
                boolean bl = false;
                if ((Integer)g.getOrDefault((Object)player.getName(), (Integer) 0) != 0) {
                    if (Server.getInstance().getTick() - (Integer)g.get((Object)player.getName()) < Config.getCheck$KillAura$min_attack_delay()) {
                        if (ALLATORIxDEMO.containsKey((Object)player.getName()) && !((String)ALLATORIxDEMO.get((Object)player.getName())).equals((Object)player2.getName())) {
                            bl = true;
                        }
                    } else if (Server.getInstance().getTick() - (Integer)g.get((Object)player.getName()) >= 20) {
                        ALLATORIxDEMO.remove((Object)player.getName());
                    }
                }
                g.put((String) player.getName(), (Integer) Server.getInstance().getTick());
                ALLATORIxDEMO.put((String) player.getName(), (String) player2.getName());
                Method.spawnBot(player);
                if (playerBot.containsKey((Object)player.getName()) && !a.isCancelled()) {
                    ((FakeBot)((Object)playerBot.get((Object)player.getName()))).setLifeTick(((FakeBot)((Object)playerBot.get((Object)player.getName()))).getLifeTick() + 20L);
                }
                if (bl) {
                    if (!player.isOp() && !Config.isSetting$debug()) {
                        WasteAC.PlusVl(player.getName(), "KillAura");
                    }
                    if (Config.isSetting$debug()) {
                        Method.sendDebug((Player)a.getDamager(), "KillAura", new StringBuilder().insert(0, "tick-").append(g.get((Object)player.getName())).toString(), "The player attack 2 or more players in an instant, similar to KillAura or AimBot or HitBox");
                    }
                }
            }
        }
        if (a.getDamager() instanceof Player && a.getEntity() instanceof FakeBot) {
            EntityDamageByEntityEvent killAura = a;
            EntityDamageByEntityEvent killAura2 = a;
            killAura2.setDamage(0.0f);
            killAura2.setKnockBack(0.0f);
            killAura.setAttackCooldown(0);
            killAura.setCancelled(false);
            if (Config.getCheck$enable().contains((Object)"KillAura") && !Config.getSetting$excluded_worlds().contains((Object)a.getDamager().getLevel().getName())) {
                attackBot.put((String) a.getDamager().getName(), (Integer) ((Integer)attackBot.getOrDefault((Object)a.getDamager().getName(), (Integer) 0) + 1));
                ((FakeBot)a.getEntity()).setLifeTick(((FakeBot)a.getEntity()).getLifeTick() + 20L);
                if ((Integer)attackBot.get((Object)a.getDamager().getName()) > Config.getCheck$KillAura$max_attack_bot()) {
                    if (!((Player)a.getDamager()).isOp() && !Config.isSetting$debug()) {
                        WasteAC.PlusVl(a.getDamager().getName(), "KillAura");
                    }
                    if (Config.isSetting$debug()) {
                        Method.sendDebug((Player)a.getDamager(), "KillAura", new StringBuilder().insert(0, "attackBot-").append(attackBot.get((Object)a.getDamager().getName())).toString(), "When the player attacks, he repeatedly attack the bot, which may be KillAura");
                    }
                }
            }
        }
    }
}

