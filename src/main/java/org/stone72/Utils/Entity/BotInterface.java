package org.stone72.Utils.Entity;

import cn.nukkit.Player;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.network.session.NetworkPlayerSession;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.UUID;
import org.stone72.Utils.Configs.Config;

public class BotInterface implements SourceInterface {
   private final HashMap<String, ClonePlayer> players = new HashMap();
   public final HashMap<InetSocketAddress, Player> player2Address = new HashMap();

   public HashMap<String, ClonePlayer> getPlayers() {
      return this.players;
   }

   public NetworkPlayerSession getSession(InetSocketAddress address) {
      return new BotSession(this, address);
   }

   public ClonePlayer createPlayer(String name, UUID uuid, InetSocketAddress address, Long clientID, Player p) {
      ClonePlayer player = new ClonePlayer(name, uuid, this, clientID, address, p);
      this.players.put(name, player);
      this.player2Address.put(address, player);
      Config.server.addOnlinePlayer(player);
      return player;
   }

   public void removePlayer(Player player, InetSocketAddress address) {
      this.players.remove(player.getName());
      this.player2Address.remove(address);
      Config.server.removeOnlinePlayer(player);
      player.close();
   }

   public int getNetworkLatency(Player player) {
      return 0;
   }

   public void close(Player player) {
   }

   public void close(Player player, String s) {
   }

   public void setName(String s) {
   }

   public boolean process() {
      return true;
   }

   public void shutdown() {
   }

   public void emergencyShutdown() {
   }
}
