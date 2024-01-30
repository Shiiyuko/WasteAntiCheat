/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.inventory.InventoryClickEvent
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.HashMap
 *  java.util.Map
 */
package org.stone72.Check;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryClickEvent;
import java.util.HashMap;
import java.util.Map;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;
import org.stone72.WasteAC;

public class InventoryMove
implements Listener {
    public static Map<String, Integer> moveDelay;
    public static Map<String, Integer> moveTick;
    public static Map<String, Integer> moveSlot;

    @EventHandler
    public void onInventoryMove(InventoryClickEvent inventoryClickEvent) {
        InventoryClickEvent a = inventoryClickEvent;
        InventoryMove a2 = this;
        if (Config.getCheck$enable().contains((Object)"InventoryMove") && !Config.getSetting$excluded_worlds().contains((Object)a.getPlayer().getLevel().getName())) {
            int n = Server.getInstance().getTick() - (Integer)moveTick.getOrDefault((Object)a.getPlayer().getName(), (Integer) 0);
            boolean bl = n == 0 && (Integer)moveDelay.getOrDefault((Object)a.getPlayer().getName(), (Integer) 0) == 0;
            moveTick.put((String) a.getPlayer().getName(), (Integer) Server.getInstance().getTick());
            moveDelay.put((String) a.getPlayer().getName(), (Integer) n);
            if (moveSlot.containsKey((Object)a.getPlayer().getName()) && bl && ((Integer)moveSlot.get((Object)a.getPlayer().getName())).intValue() != a.getSlot() && a.getSourceItem().getMaxStackSize() == 1) {
                if (!a.getPlayer().isOp() && !Config.isSetting$debug()) {
                    WasteAC.PlusVl(a.getPlayer().getName(), "InventoryMove");
                }
                if (Config.isSetting$debug()) {
                    Method.sendDebug(a.getPlayer(), "InventoryMove", new StringBuilder().insert(0, "moveTick-").append(n).append(" slot-").append(moveSlot.get((Object)a.getPlayer().getName())).toString(), "The player changing items too quickly may be InventoryMove");
                }
            }
            moveSlot.put((String) a.getPlayer().getName(), (Integer) a.getSlot());
        }
    }

    static {
        moveTick = new HashMap();
        moveDelay = new HashMap();
        moveSlot = new HashMap();
    }

    public InventoryMove() {
        InventoryMove a;
    }
}

