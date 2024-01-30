/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerLoginEvent
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package org.stone72.Ban;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

import java.util.Calendar;

public class BanSystem
implements Listener {
    /*
     * WARNING - void declaration
     */
    @EventHandler
    public void Join(PlayerLoginEvent event) {
        BanSystem banSystem = this;
        if (WasteAC.playerData().exists(new StringBuilder().insert(0, event.getPlayer().getName()).append(".pardonDate").toString())) {
            Calendar a2;
            if (Method.getCalendar().getTimeInMillis() >= Method.parseDateString(WasteAC.playerData().getString(new StringBuilder().insert(0, event.getPlayer().getName()).append(".pardonDate").toString())).getTimeInMillis()) {
                PlayerLoginEvent v0 = event;
                Method.setPlayerData(v0.getPlayer().getName(), "pardonDate", "");
                Method.setPlayerData(v0.getPlayer().getName(), "check", "");
                return;
            }
            Calendar banSystem2 = a2 = Method.parseDateString(WasteAC.playerData().getString(new StringBuilder().insert(0, event.getPlayer().getName()).append(".pardonDate").toString()));
            Calendar banSystem3 = a2;
            a2.add(1, -Method.getCalendar().get(1));
            banSystem3.add(2, -Method.getCalendar().get(2));
            banSystem3.add(5, -Method.getCalendar().get(5));
            banSystem2.add(10, -Method.getCalendar().get(10));
            banSystem2.add(12, -Method.getCalendar().get(12));
            banSystem2.add(13, -Method.getCalendar().get(13));
            PlayerLoginEvent v3 = event;
            v3.setKickMessage(Config.getSetting$banned_message().replace((CharSequence)"%year%", (CharSequence)String.valueOf((int)a2.get(1))).replace((CharSequence)"%month%", (CharSequence)String.valueOf((int)a2.get(2))).replace((CharSequence)"%day%", (CharSequence)String.valueOf((int)a2.get(5))).replace((CharSequence)"%hour%", (CharSequence)String.valueOf((int)a2.get(10))).replace((CharSequence)"%minute%", (CharSequence)String.valueOf((int)a2.get(12))).replace((CharSequence)"%second%", (CharSequence)String.valueOf((int)a2.get(13))).replace((CharSequence)"%type%", (CharSequence)WasteAC.playerData().getString(new StringBuilder().insert(0, event.getPlayer().getName()).append(".check").toString())).replace((CharSequence)"%player%", (CharSequence)v3.getPlayer().getName()));
            event.setCancelled();
        }
    }

    public BanSystem() {
        BanSystem a;
    }
}

