/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerChatEvent
 *  cn.nukkit.event.player.PlayerMoveEvent
 *  cn.nukkit.scheduler.AsyncTask
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package org.stone72.Check;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.scheduler.AsyncTask;
import java.util.HashMap;
import java.util.Map;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class Spammer
implements Listener {
    public static Map<String, Boolean> isKnock = new HashMap();
    public static Map<String, Boolean> isChat;
    public static Map<String, Boolean> isAction;

    static {
        isAction = new HashMap();
        isChat = new HashMap();
    }

    @EventHandler
    public void onChat(PlayerChatEvent playerChatEvent) {
        PlayerChatEvent a = playerChatEvent;
        Spammer a2 = this;
        isChat.put((String) a.getPlayer().getName(), (Boolean) true);
        if (Config.getCheck$enable().contains((Object)"Spammer") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName()) && (Boolean) isAction.getOrDefault((Object) a.getPlayer().getName(), (Boolean) false) && !((Boolean)isKnock.getOrDefault((Object)a.getPlayer().getName(), (Boolean) false)).booleanValue()) {
            if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                WasteAC.PlusVl(a.getPlayer().getName(), "Spammer");
            }
            if (Config.isSetting$debug()) {
                Method.sendDebug(a.getPlayer(), "Spammer", "spammer-True", "The player can still move while sending messages, which may be a Spammer");
            }
            isAction.put((String) a.getPlayer().getName(), (Boolean) false);
        }
        //Server.getInstance().getScheduler().scheduleAsyncTask(WasteAC.plugin, (AsyncTask)new K(a2, (PlayerChatEvent)a));
    }

    public Spammer() {
        Spammer a;
    }

    @EventHandler
    public void onAm(PlayerMoveEvent playerMoveEvent) {
        PlayerMoveEvent a = playerMoveEvent;
        Spammer a2 = this;
        if ((Boolean) isChat.getOrDefault((Object) a.getPlayer().getName(), (Boolean) false) && !Method.nextToLiquid(a.getFrom())) {
            isAction.put((String) a.getPlayer().getName(), (Boolean) true);
        }
    }
}

