package org.stone72.API.Event;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class PlayerAttackBotEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final transient Player player;

   public PlayerAttackBotEvent(Player player) {
      this.player = player;
   }

   public static HandlerList getHandlers() {
      return handlers;
   }

   public Player getPlayer() {
      return this.player;
   }
}
