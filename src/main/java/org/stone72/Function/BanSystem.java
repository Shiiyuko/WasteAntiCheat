package org.stone72.Function;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import java.time.LocalDateTime;
import org.stone72.WasteAC;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Math.DateTime;

public class BanSystem implements Listener {
   @EventHandler
   public void listener(PlayerLoginEvent e) {
      if (WasteAPI.getPlayerData().exists(e.getPlayer().getName() + ".banTime")) {
         LocalDateTime banTime = DateTime.parseDateString(WasteAPI.getPlayerData().getString(e.getPlayer().getName() + ".banTime"));
         LocalDateTime dtLocal = LocalDateTime.now();
         String[] date = DateTime.dateDifference(dtLocal, banTime);
         if (dtLocal.isAfter(banTime)) {
            WasteAC.getApi().unBanPlayer(e.getPlayer().getName());
         } else {
            e.setKickMessage(Config.setting$banned_message.replace("%year%", date[0]).replace("%month%", date[1]).replace("%day%", date[2]).replace("%hour%", date[3]).replace("%minute%", date[4]).replace("%second%", date[5]).replace("%type%", WasteAPI.getPlayerData().getString(e.getPlayer().getName() + ".check")).replace("%player%", e.getPlayer().getName()));
            e.setCancelled();
         }
      }

   }
}
