package org.stone72.Module.Misc;

import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.TextPacket;
import java.util.HashMap;
import org.stone72.API.WasteAPI;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Math.Calculation;

public class Spammer {
   public static HashMap<String, Integer> sendTick = new HashMap();
   public static HashMap<String, Integer> sendDelay = new HashMap();
   public static HashMap<String, String> lastMes = new HashMap();

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Misc[0], e.getPlayer()) && e.getPacket() instanceof TextPacket) {
         TextPacket packet = (TextPacket)e.getPacket();
         if (Checks.isTypeEnable(WasteAPI.Misc[0], "type-a") && packet.xboxUserId.isEmpty()) {
            WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Misc[0], "type-a", "packet-Bad", "The player sent a BadPacket while placing blocks, which may be a Spammer");
         }

         int checkLength;
         if (Checks.isTypeEnable(WasteAPI.Misc[0], "type-b")) {
            checkLength = Config.server.getTick() - (Integer)sendTick.getOrDefault(e.getPlayer().getName(), 0);
            boolean sameDelay = checkLength == (Integer)sendDelay.getOrDefault(e.getPlayer().getName(), 0) && checkLength < Checks.getType(WasteAPI.Misc[0], "type-b", "max_tick", "int").IntValue;
            sendTick.put(e.getPlayer().getName(), Config.server.getTick());
            sendDelay.put(e.getPlayer().getName(), checkLength);
            if (sameDelay) {
               WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Misc[0], "type-b", "sendTick-" + checkLength, "The player abnormal movement of items, this may be Spammer");
            }
         }

         if (Checks.isTypeEnable(WasteAPI.Misc[0], "type-c")) {
            checkLength = Checks.getType(WasteAPI.Misc[0], "type-c", "character", "int").IntValue;
            String mes = packet.message.toLowerCase();
            String mess = mes.length() >= checkLength ? mes.substring(0, checkLength) : mes;
            if (lastMes.containsKey(e.getPlayer().getName()) && (double)Calculation.repeat(mes) / (double)mes.length() < Checks.getType(WasteAPI.Misc[0], "type-c", "similarity", "double").DoubleValue && ((String)lastMes.get(e.getPlayer().getName())).equals(mess)) {
               WasteAPI.solveCheat(e, true, e.getPlayer(), WasteAPI.Misc[0], "type-c", "sameLength-" + mess.length(), "The player abnormal movement of items, this may be Spammer");
            }

            lastMes.put(e.getPlayer().getName(), mess);
         }
      }

   }
}
