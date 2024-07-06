package org.stone72.Utils.Entity;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.PlayerFood;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityRideable;
import cn.nukkit.inventory.PlayerEnderChestInventory;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.PlayerOffhandInventory;
import cn.nukkit.lang.TextContainer;
import cn.nukkit.level.Level;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.LoginChainData;
import com.google.gson.JsonObject;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;
import org.stone72.Utils.Configs.Config;

public class ClonePlayer extends Player {
   private final LoginChainData lcd;

   public ClonePlayer(final String name, final UUID uuid, SourceInterface interfaz, final Long clientID, InetSocketAddress socketAddress, Player p) {
      super(interfaz, clientID, socketAddress);
      this.uuid = uuid;
      this.skin = Config.DEFAULT_SKIN;
      this.username = name;
      this.displayName = this.username;
      this.iusername = this.username.toLowerCase();
      this.randomClientId = clientID;
      this.rawUUID = Binary.writeUUID(this.uuid);
      this.inventory = new PlayerInventory(this);
      this.offhandInventory = new PlayerOffhandInventory(this);
      this.enderChestInventory = new PlayerEnderChestInventory(this);
      this.namedTag = Entity.getDefaultNBT(p);
      this.setNameTag(name);
      this.adventureSettings = new AdventureSettings(this);
      this.foodData = new PlayerFood(this, 20, 20.0F);
      this.lcd = new LoginChainData() {
         public String getUsername() {
            return name;
         }

         public UUID getClientUUID() {
            return uuid;
         }

         public String getIdentityPublicKey() {
            return name;
         }

         public long getClientId() {
            return clientID;
         }

         public String getServerAddress() {
            return ClonePlayer.this.server.getIp();
         }

         public String getDeviceModel() {
            return "Virtual Controller";
         }

         public int getDeviceOS() {
            return 0;
         }

         public String getDeviceId() {
            return "Virtual Devices";
         }

         public String getGameVersion() {
            return "1.22.0";
         }

         public int getGuiScale() {
            return 0;
         }

         public String getLanguageCode() {
            return "Chinese";
         }

         public String getXUID() {
            return name;
         }

         public boolean isXboxAuthed() {
            return true;
         }

         public int getCurrentInputMode() {
            return 0;
         }

         public int getDefaultInputMode() {
            return 0;
         }

         public String getCapeData() {
            return "";
         }

         public int getUIProfile() {
            return 0;
         }

         @Nullable
         @Override
         public String getWaterdogXUID() {
            return null;
         }

         @Nullable
         @Override
         public String getWaterdogIP() {
            return null;
         }

         public JsonObject getRawData() {
            return new JsonObject();
         }

         public String getTitleId() {
            return "";
         }
      };
      this.loggedIn = true;
   }

   public LoginChainData getLoginChainData() {
      return this.lcd;
   }

   public void kill() {
   }

   public void knockBack(Entity attacker, double damage, double x, double z, double base) {
   }

   public void close(TextContainer message, String reason, boolean notify) {
      if (this.connected && !this.closed) {
         this.connected = false;
         if (this.getName() != null && !this.getName().isEmpty() && this.fishing != null) {
            this.stopFishing(false);
         }

         this.hiddenPlayers.clear();
         this.removeAllWindows(true);
         Iterator var4 = this.usedChunks.keySet().iterator();

         while(var4.hasNext()) {
            Object o = (Long)var4.next();
            long index = (long) o;
            int chunkX = Level.getHashX(index);
            int chunkZ = Level.getHashZ(index);
            this.level.unregisterChunkLoader(this, chunkX, chunkZ);
            this.usedChunks.remove(index);
            Iterator var10 = this.level.getChunkEntities(chunkX, chunkZ).values().iterator();

            while(var10.hasNext()) {
               Entity entity = (Entity)var10.next();
               if (entity != this) {
                  entity.getViewers().remove(this.getLoaderId());
               }
            }
         }

         if (this.inventory != null && this.loggedIn) {
            var4 = this.inventory.getViewers().iterator();

            while(var4.hasNext()) {
               Player viewer = (Player)var4.next();
               viewer.removeWindow(this.inventory);
            }
         }

         this.closed = true;
         if (this.chunk != null) {
            this.chunk.removeEntity(this);
         }

         if (this.level != null) {
            this.level.removeEntity(this);
         }
      }

      this.interfaz.close(this, notify ? reason : "");
      if (this.loggedIn) {
         this.server.removeOnlinePlayer(this);
      }

      this.loggedIn = false;
      this.spawned = false;
      this.usedChunks.clear();
      this.hasSpawned.clear();
      this.spawnPosition = null;
      if (this.riding instanceof EntityRideable) {
         this.riding.passengers.remove(this);
      }

      this.riding = null;
      if (this.inventory != null) {
         this.inventory = null;
      }

      this.chunk = null;
      this.server.removePlayer(this);
   }
}
