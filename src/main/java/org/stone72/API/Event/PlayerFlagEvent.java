package org.stone72.API.Event;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class PlayerFlagEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final transient String check;
   private final transient String type;
   private final transient Player player;
   private final transient int flag;

   public PlayerFlagEvent(String check, String type, Player player, int flag) {
      this.check = check;
      this.player = player;
      this.type = type;
      this.flag = flag;
   }

   public static HandlerList getHandlers() {
      return handlers;
   }

   public String getCheck() {
      return this.check;
   }

   public String getType() {
      return this.type;
   }

   public Player getPlayer() {
      return this.player;
   }

   public int getFlag() {
      return this.flag;
   }
}
