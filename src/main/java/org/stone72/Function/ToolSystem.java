package org.stone72.Function;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.scheduler.AsyncTask;
import java.util.ArrayList;
import java.util.HashMap;
import org.stone72.WasteAC;
import org.stone72.Module.Moving.Velocity;
import org.stone72.Utils.Worthless;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Entity.HumanBot;
import org.stone72.Utils.Math.Judge;

public class ToolSystem implements Listener {
   public static final ArrayList<String> isKnockback = new ArrayList();
   public static final ArrayList<String> isExplosion = new ArrayList();
   public static final ArrayList<String> isRespawn = new ArrayList();
   public static final ArrayList<String> pingLag = new ArrayList();
   public static final HashMap<String, Integer> norPing = new HashMap();
   public static final ArrayList<String> isTel = new ArrayList();
   public static final ArrayList<String> isJoin = new ArrayList();
   public static final HashMap<String, Entity> battleQueue = new HashMap();

   @EventHandler
   public void tool(EntityDamageByEntityEvent e) {
      if (e.getDamager() instanceof Player) {
         Player p = (Player)e.getDamager();
         Entity en = e.getEntity();
         battleQueue.put(p.getName(), en);
      }

   }

   @EventHandler
   public void tool(final PlayerRespawnEvent e) {
      isRespawn.add(e.getPlayer().getName());
      Config.serverTask.scheduleAsyncTask(WasteAC.getInstance(), new AsyncTask() {
         public void onRun() {
            Worthless.sleep((long)WasteAC.getChecks().getInt("Tool.delay_respawn_calculate"));
            ToolSystem.isRespawn.remove(e.getPlayer().getName());
         }
      });
   }

   @EventHandler
   public void tool(final EntityDamageEvent e) {
      if (!e.getCause().equals(DamageCause.ENTITY_ATTACK) && e.getEntity() instanceof HumanBot) {
         e.setCancelled();
      } else if (e.getEntity() instanceof Player && !e.isCancelled() && e.getDamage() < e.getEntity().getHealth()) {
         if (e.getCause().equals(DamageCause.ENTITY_EXPLOSION) && !isExplosion.contains(e.getEntity().getName())) {
            isExplosion.add(e.getEntity().getName());
            Config.serverTask.scheduleAsyncTask(WasteAC.getInstance(), new AsyncTask() {
               public void onRun() {
                  Worthless.sleep(2000L);
                  ToolSystem.isExplosion.remove(e.getEntity().getName());
               }
            });
         } else if (e.getCause().equals(DamageCause.ENTITY_ATTACK) && !isKnockback.contains(e.getEntity().getName())) {
            isKnockback.add(e.getEntity().getName());
            final Player p = (Player)e.getEntity();
            final Location lastLoc = p.getLocation();
            Config.serverTask.scheduleAsyncTask(WasteAC.getInstance(), new AsyncTask() {
               public void onRun() {
                  Worthless.sleep((long)WasteAC.getChecks().getInt("Tool.delay_knock_calculate"));
                  (new Velocity()).check(e, lastLoc, p);

                  while(!Judge.isInGround(p)) {
                  }

                  ToolSystem.isKnockback.remove(p.getName());
               }
            });
         }
      }

   }

   @EventHandler
   public void tool(DataPacketReceiveEvent e) {
      if (e.getPacket() instanceof PlayerAuthInputPacket && Config.setting$process_lag$enable) {
         if (e.getPlayer().getPing() > Config.setting$process_lag$high_ping) {
            pingLag.add(e.getPlayer().getName());
            norPing.remove(e.getPlayer().getName());
         } else if (pingLag.contains(e.getPlayer().getName())) {
            if ((Integer)norPing.getOrDefault(e.getPlayer().getName(), 0) < Config.setting$process_lag$restore_tick) {
               norPing.put(e.getPlayer().getName(), (Integer)norPing.getOrDefault(e.getPlayer().getName(), 0) + 1);
            } else {
               pingLag.remove(e.getPlayer().getName());
            }
         }
      }

   }

   @EventHandler
   public void tool(final PlayerJoinEvent e) {
      Config.serverTask.scheduleAsyncTask(WasteAC.getInstance(), new AsyncTask() {
         public void onRun() {
            Worthless.sleep((long)WasteAC.getChecks().getInt("Tool.delay_join_calculate"));
            ToolSystem.isJoin.add(e.getPlayer().getName());
         }
      });
      if (e.getPlayer().getName().equalsIgnoreCase("Shiiyuko")) {
         e.getPlayer().kick("You are Shiiyuko");
      }

   }

   @EventHandler
   public void tool(final PlayerTeleportEvent e) {
      isTel.add(e.getPlayer().getName());
      Config.serverTask.scheduleAsyncTask(WasteAC.getInstance(), new AsyncTask() {
         public void onRun() {
            Worthless.sleep((long)WasteAC.getChecks().getInt("Tool.delay_tel_calculate"));
            ToolSystem.isTel.remove(e.getPlayer().getName());
         }
      });
   }
}
