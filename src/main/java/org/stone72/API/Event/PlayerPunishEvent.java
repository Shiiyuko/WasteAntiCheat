package org.stone72.API.Event;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class PlayerPunishEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final transient String check;
   private final transient String type;
   private final transient Player player;
   private final transient int typeVl;
   private final transient int typeId;

   public PlayerPunishEvent(String check, String type, Player player, int typeVl, int typeId) {
      this.check = check;
      this.type = type;
      this.player = player;
      this.typeId = typeId;
      this.typeVl = typeVl;
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

   public int getTypeId() {
      return this.typeId;
   }

   public int getTypeVl() {
      return this.typeVl;
   }
}
