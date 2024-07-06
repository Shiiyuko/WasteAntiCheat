package org.stone72.Module.Combat;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import java.util.HashMap;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;

public class AutoClicker {
   public static final HashMap<String, Integer> attackTick = new HashMap();
   public static final HashMap<String, Integer> cpsTick = new HashMap();
   public static HashMap<String, Integer> cpsMap = new HashMap();
   public static HashMap<String, Long> damageTick = new HashMap();
   public static HashMap<String, Long> damageDelay = new HashMap();

   public void check(EntityDamageByEntityEvent e) {
      if (e.getDamager() instanceof Player && WasteAPI.canCheck(WasteAPI.Combat[0], (Player)e.getDamager())) {
         Player p = (Player)e.getDamager();
         int cooldown = e.getAttackCooldown();
         if (Checks.isTypeEnable(WasteAPI.Combat[0], "type-a")) {
            e.setCancelled(true);
            if (Config.server.getTick() - (Integer)attackTick.getOrDefault(e.getDamager().getName(), 0) >= cooldown) {
               e.setCancelled(false);
               attackTick.put(p.getName(), Config.server.getTick());
            }

            e.setAttackCooldown(0);
            if (Config.server.getTick() - (Integer)cpsTick.getOrDefault(p.getName(), 0) >= 20) {
               cpsMap.put(p.getName(), 0);
               cpsTick.put(p.getName(), Config.server.getTick());
            }

            cpsMap.put(p.getName(), (Integer)cpsMap.getOrDefault(p.getName(), 0) + 1);
            if ((Integer)cpsMap.getOrDefault(p.getName(), 0) > Checks.getType(WasteAPI.Combat[0], "type-a", "max_cps", "int").IntValue) {
               WasteAPI.solveCheat(e, true, p, WasteAPI.Combat[0], "type-a", "cps-" + cpsMap.getOrDefault(e.getDamager().getName(), 0), "The player clicked too quickly during the attack, which may be due to AutoClicker");
            }
         }

         if (Checks.isTypeEnable(WasteAPI.Combat[0], "type-b")) {
            long tick = System.currentTimeMillis() - (Long)damageTick.getOrDefault(p.getName(), 0L);
            damageTick.put(p.getName(), System.currentTimeMillis());
            boolean sameDelay = tick == (Long)damageDelay.getOrDefault(p.getName(), 0L) && tick < (long)Checks.getType(WasteAPI.Combat[0], "type-b", "max_tick", "int").IntValue;
            damageDelay.put(p.getName(), tick);
            if (sameDelay) {
               WasteAPI.solveCheat(e, true, p, WasteAPI.Combat[0], "type-a", "delay-" + tick, "The player's click delay is consistent, which may be due to AutoClicker");
            }
         }
      }

   }
}
