package org.stone72.Utils.Entity;

import cn.nukkit.Player;
import cn.nukkit.network.CompressionProvider;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.session.NetworkPlayerSession;
import java.net.InetSocketAddress;

public class BotSession implements NetworkPlayerSession {
   private final BotInterface botInterface;
   private final InetSocketAddress address;
   private CompressionProvider compressionProvider;

   public BotSession(BotInterface botInterface, InetSocketAddress address) {
      this.compressionProvider = CompressionProvider.NONE;
      this.botInterface = botInterface;
      this.address = address;
   }

   public CompressionProvider getCompression() {
      return this.compressionProvider;
   }

   public void sendPacket(DataPacket dataPacket) {
   }

   public void sendImmediatePacket(DataPacket dataPacket, Runnable runnable) {
   }

   public void disconnect(String s) {
   }

   public Player getPlayer() {
      return (Player)this.botInterface.player2Address.get(this.address);
   }

   public void setCompression(CompressionProvider compressionProvider) {
      this.compressionProvider = compressionProvider;
   }
}
