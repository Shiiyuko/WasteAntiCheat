package org.stone72.Module.Moving;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.player.PlayerMoveEvent;
import java.util.HashMap;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Math.Judge;

public class NoSlow {
   public static HashMap<String, Integer> useTick = new HashMap();
   public static HashMap<String, Integer> useCount = new HashMap();

   public void check(PlayerMoveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Moving[1], e.getPlayer())) {
         Entity en = e.getPlayer().riding;
         int use_count = Checks.getType(WasteAPI.Moving[1], "type-b", "use_count", "int").IntValue;
         if (Checks.isTypeEnable(WasteAPI.Moving[1], "type-a")) {
            if (Config.server.getTick() - (Integer)useTick.getOrDefault(e.getPlayer().getName(), 0) >= Checks.getType(WasteAPI.Moving[1], "type-a", "clear_time", "int").IntValue) {
               useCount.put(e.getPlayer().getName(), 0);
               useTick.put(e.getPlayer().getName(), Config.server.getTick());
            }

            if (e.getPlayer().isUsingItem()) {
               useCount.put(e.getPlayer().getName(), (Integer)useCount.getOrDefault(e.getPlayer().getName(), 0) + 1);
            } else {
               useCount.put(e.getPlayer().getName(), 0);
            }

            if (e.getPlayer().isSprinting() && (Integer)useCount.get(e.getPlayer().getName()) >= use_count && e.getPlayer().isSurvival() && Judge.isUseItem(e.getPlayer())) {
               WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Moving[1], "type-a", "moveState-Sprinting", "The player sprint while using items, which may be NoSlow");
            }
         }

         if (Checks.isTypeEnable(WasteAPI.Moving[1], "type-b") && (Integer)useCount.get(e.getPlayer().getName()) >= use_count && e.getFrom().getLocation().setY(0.0).distance(e.getTo().getLocation().setY(0.0)) > (double)Checks.getType(WasteAPI.Moving[1], "type-b", "normal_move", "int").IntValue && e.getPlayer().isSurvival() && Judge.isUseItem(e.getPlayer()) && en == null) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Moving[1], "type-b", "distance-" + e.getFrom().getLocation().setY(0.0).distance(e.getTo().getLocation().setY(0.0)), "When player use items, their speed exceeds the normal speed, which may be NoSlow");
         }
      }

   }
}
