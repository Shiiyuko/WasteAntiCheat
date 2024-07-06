package org.stone72.Module.Misc;

import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.ClientCacheStatusPacket;
import cn.nukkit.scheduler.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import org.checkerframework.checker.units.qual.C;
import org.stone72.WasteAC;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Math.Judge;

public class BadPacket {
   public void check(final PlayerJoinEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Misc[2], e.getPlayer())) {
         final Object ip = e.getPlayer().getAddress();
         if (Checks.isTypeEnable(WasteAPI.Misc[2], "type-a") && Judge.isToolbox(e.getPlayer())) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Misc[2], "type-a", "packet-Bad", "The player's os is Switch, which is likely due to the use of tools such as ToolBox");
         }

         if (Checks.isTypeEnable(WasteAPI.Misc[2], "type-b")) {
            Config.serverTask.scheduleAsyncTask(WasteAC.getInstance(), new AsyncTask() {
               public void onRun() {
                  CompletableFuture.supplyAsync(() -> {
                     try {
                        URL url = new URL("https://check.getipintel.net/check.php?ip=" + ip + "&contact=vnikire@gmail.com");
                        HttpURLConnection con = (HttpURLConnection)url.openConnection();
                        con.setRequestMethod("GET");
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder content = new StringBuilder();

                        String inputLine;
                        while((inputLine = in.readLine()) != null) {
                           content.append(inputLine);
                        }

                        in.close();
                        return Double.parseDouble(content.toString());
                     } catch (Exception var6) {
                        return 0.0;
                     }
                  }).thenAcceptAsync((score) -> {
                     if (score > 0.98) {
                        WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Misc[2], "type-b", "ipScore-" + score, "The player's IP check is abnormal, which may be due to VPN");
                     }

                  }).join();
               }
            });
         }
      }

   }

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Misc[2], e.getPlayer()) && e.getPacket() instanceof ClientCacheStatusPacket) {
         ClientCacheStatusPacket packet = (ClientCacheStatusPacket)e.getPacket();
         if (Checks.isTypeEnable(WasteAPI.Misc[2], "type-c") && !packet.supported) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Misc[2], "type-c", "packet-Bad", "The player sent the wrong package, which may be BedrockTool");
         }
      }

   }
}
