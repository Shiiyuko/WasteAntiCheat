package org.stone72.Utils.Entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.EntityEventPacket;
import cn.nukkit.scheduler.AsyncTask;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.UUID;
import org.stone72.WasteAC;
import org.stone72.Module.Combat.KillAura;
import org.stone72.Utils.Worthless;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Math.Nukkit;

public class HumanBot extends EntityHuman {
   private static final Item[] weapon = new Item[]{Item.get(268), Item.get(267), Item.get(283), Item.get(276), Item.get(258), Item.get(279)};
   private static final Item[] helmet = new Item[]{Item.get(298), Item.get(306), Item.get(314), Item.get(310), Item.get(0)};
   private static final Item[] leggings = new Item[]{Item.get(300), Item.get(308), Item.get(316), Item.get(312), Item.get(0)};
   private static final Item[] chestplate = new Item[]{Item.get(299), Item.get(307), Item.get(315), Item.get(311), Item.get(0)};
   private static final Item[] boots = new Item[]{Item.get(301), Item.get(309), Item.get(317), Item.get(313), Item.get(0)};
   public final transient Long maxLife = 160L;
   private final transient Player target;
   private transient boolean isMove = false;
   private final transient InetSocketAddress address;
   private transient Long lifeTick;
   private static final BotInterface botInterface = new BotInterface();
   private final transient ClonePlayer botPlayer;

   public HumanBot(Player target) throws UnknownHostException {
      super(target.getPosition().getChunk(), Entity.getDefaultNBT(target.getPosition()).putCompound("Skin", (new CompoundTag()).putByteArray("Data", Config.DEFAULT_SKIN.getSkinData().data).putString("ModelId", Config.DEFAULT_SKIN.getSkinId())));
      String name = String.valueOf(randomName());
      this.setNameTag(name);
      this.canPassThrough();
      this.setNameTagVisible(true);
      this.setNameTagAlwaysVisible(true);
      this.setImmobile(false);
      this.setHealth(20.0F);
      this.setMaxHealth(20);
      this.target = target;
      this.setPosition(target.getPosition().add((new Vector3(target.getDirectionPlane().x, 0.0, target.getDirectionPlane().y)).multiply(-2.8).add(0.0, 0.8, 0.0)));
      this.setSkin(Config.DEFAULT_SKIN);
      this.address = new InetSocketAddress(InetAddress.getLocalHost(), Config.randomNumber.nextInt(65536));
      this.botPlayer = botInterface.createPlayer(name, UUID.randomUUID(), this.address, Config.randomNumber.nextLong(), target);
      this.lifeTick = this.maxLife;
      Item[] armor = new Item[]{helmet[Config.randomNumber.nextInt(helmet.length)], chestplate[Config.randomNumber.nextInt(chestplate.length)], leggings[Config.randomNumber.nextInt(leggings.length)], boots[Config.randomNumber.nextInt(boots.length)]};
      this.getInventory().setItemInHand(weapon[Config.randomNumber.nextInt(weapon.length)]);
      this.getInventory().setArmorContents(armor);
   }

   public static StringBuilder randomName() {
      String Lowercase = "abcdefghijklmnopqrstuvwxyz";
      String Number = "0123456789";
      String Uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      return (new StringBuilder()).append(Uppercase.charAt(Config.randomNumber.nextInt(26))).append(Lowercase.charAt(Config.randomNumber.nextInt(26))).append(Lowercase.charAt(Config.randomNumber.nextInt(26))).append(Lowercase.charAt(Config.randomNumber.nextInt(26))).append(Lowercase.charAt(Config.randomNumber.nextInt(26))).append(Lowercase.charAt(Config.randomNumber.nextInt(26))).append(Lowercase.charAt(Config.randomNumber.nextInt(26))).append(Number.charAt(Config.randomNumber.nextInt(10))).append(Number.charAt(Config.randomNumber.nextInt(10))).append(Number.charAt(Config.randomNumber.nextInt(10))).append(Number.charAt(Config.randomNumber.nextInt(10))).append(Number.charAt(Config.randomNumber.nextInt(10))).append(Number.charAt(Config.randomNumber.nextInt(10)));
   }

   public void addLifeTick(Long lifeTick) {
      if (this.lifeTick + lifeTick > this.maxLife) {
         this.lifeTick = this.maxLife;
      } else {
         this.lifeTick = this.lifeTick + lifeTick;
      }

   }

   public boolean onUpdate(int tickDiff) {
      final Player player = this.target;
      if (player.isAlive()) {
         this.lifeTick = this.lifeTick - 1L;
         Nukkit.seePlayer(this, player);
         this.playArm(player);
         if (!this.isMove) {
            this.isMove = true;
            Config.serverTask.scheduleAsyncTask(WasteAC.getInstance(), new AsyncTask() {
               public void onRun() {
                  while(HumanBot.this.lifeTick > 0L) {
                     HumanBot.this.teleport(player.getPosition().add((new Vector3(player.getDirectionPlane().x, 0.0, player.getDirectionPlane().y)).multiply(-2.8).add(0.0, 0.8, 0.0)));
                     Iterator var1 = HumanBot.this.getLevel().getPlayers().values().iterator();

                     while(var1.hasNext()) {
                        Player p = (Player)var1.next();
                        if (!p.getName().equals(player.getName())) {
                           HumanBot.this.despawnFrom(p);
                        }
                     }

                     Worthless.sleep(150L);
                  }

               }
            });
         }

         if (this.lifeTick <= 0L) {
            this.close();
         }
      } else {
         this.close();
      }

      return super.onUpdate(tickDiff);
   }

   public void playArm(Player player) {
      EntityEventPacket pk = new EntityEventPacket();
      pk.eid = this.getId();
      pk.event = 4;
      player.dataPacket(pk);
   }

   public void close() {
      this.getInventory().clearAll();
      KillAura.playerBot.remove(this.target.getName());
      this.isMove = false;
      botInterface.removePlayer(this.botPlayer, this.address);
      super.close();
   }
}
