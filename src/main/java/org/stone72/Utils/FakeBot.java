/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.entity.Entity
 *  cn.nukkit.entity.EntityHuman
 *  cn.nukkit.item.Item
 *  cn.nukkit.level.Level
 *  cn.nukkit.level.format.FullChunk
 *  cn.nukkit.nbt.tag.CompoundTag
 *  cn.nukkit.network.protocol.DataPacket
 *  cn.nukkit.network.protocol.EntityEventPacket
 *  cn.nukkit.network.protocol.RemoveEntityPacket
 *  cn.nukkit.scheduler.AsyncTask
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Random
 */
package org.stone72.Utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.EntityEventPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import java.util.Random;
import org.stone72.Check.KillAura;

public class FakeBot
extends EntityHuman {
    String m;
    private static final Item[] a;
    private static final Item[] J;
    private static final Item[] F;
    private static final Item[] h;
    private static final Item[] i;
    Long g;
    boolean ALLATORIxDEMO;

    /*
     * WARNING - void declaration
     */
    public FakeBot(FullChunk a3, CompoundTag a) {
        super((FullChunk)a3, (CompoundTag)a);
        FakeBot a2 = this;
        a2.g = 120L;
        a2.setNameTag(FakeBot.randomName(6, 6));
        a2.setNameTagVisible(true);
        a2.setNameTagAlwaysVisible(true);
        a2.setImmobile(false);
        a2.setHealth(20.0f);
        a2.setMaxHealth(20);
        Item[] itemArray3 = new Item[4];
        itemArray3[0] = FakeBot.a[new Random().nextInt(FakeBot.a.length)];
        itemArray3[1] = i[new Random().nextInt(i.length)];
        itemArray3[2] = J[new Random().nextInt(J.length)];
        itemArray3[3] = F[new Random().nextInt(F.length)];
        a2.getInventory().setItemInHand(h[new Random().nextInt(h.length)]);
        a2.getInventory().setArmorContents(itemArray3);
    }

    public Long getLifeTick() {
        FakeBot a = this;
        return a.g;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean onUpdate(int a) {
        FakeBot fakeBot = this;
        Player a2 = Server.getInstance().getPlayerExact(fakeBot.m);
        FakeBot fakeBot2 = fakeBot;
        Long l = fakeBot2.g;
        fakeBot2.g = fakeBot2.g - 1L;
        FakeBot fakeBot3 = fakeBot;
        Method.seePlayer((Entity)fakeBot3, (Entity)a2);
        fakeBot3.playArm();
        if (!fakeBot3.ALLATORIxDEMO) {
            fakeBot.ALLATORIxDEMO = true;
            //Server.getInstance().getScheduler().scheduleAsyncTask(WasteAC.plugin, (AsyncTask)new D(fakeBot, (Player)a2));
        }
        if (fakeBot.g <= 0L) {
            fakeBot.close();
        }
        return super.onUpdate((int)a);
    }

    /*
     * WARNING - void declaration
     */
    public static String randomName(int a, int n3) {
        String string = "abcdefghijklmnopqrstuvwxyz";
        String string2 = "0123456789";
        String a22 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int n4 = random.nextInt(26);
        stringBuilder.append(a22.charAt(n4));
        int i1 = 0;
        int n5 = i1;
        while (n5 < n3) {
            n4 = random.nextInt(26);
            stringBuilder.append(string.charAt(n4));
            n5 = ++i1;
        }
        int n6 = i1 = 0;
        while (n6 < a) {
            n4 = random.nextInt(10);
            stringBuilder.append(string2.charAt(n4));
            n6 = ++i1;
        }
        return stringBuilder.toString();
    }

    @Override
    public int getNetworkId() {
        return -1;
    }

    static {
        Item[] itemArray = new Item[6];
        itemArray[0] = Item.get((int)268);
        itemArray[1] = Item.get((int)267);
        itemArray[2] = Item.get((int)283);
        itemArray[3] = Item.get((int)276);
        itemArray[4] = Item.get((int)258);
        itemArray[5] = Item.get((int)279);
        h = itemArray;
        Item[] itemArray2 = new Item[5];
        itemArray2[0] = Item.get((int)298);
        itemArray2[1] = Item.get((int)306);
        itemArray2[2] = Item.get((int)314);
        itemArray2[3] = Item.get((int)310);
        itemArray2[4] = Item.get((int)0);
        a = itemArray2;
        Item[] itemArray3 = new Item[5];
        itemArray3[0] = Item.get((int)300);
        itemArray3[1] = Item.get((int)308);
        itemArray3[2] = Item.get((int)316);
        itemArray3[3] = Item.get((int)312);
        itemArray3[4] = Item.get((int)0);
        J = itemArray3;
        Item[] itemArray4 = new Item[5];
        itemArray4[0] = Item.get((int)299);
        itemArray4[1] = Item.get((int)307);
        itemArray4[2] = Item.get((int)315);
        itemArray4[3] = Item.get((int)311);
        itemArray4[4] = Item.get((int)0);
        i = itemArray4;
        Item[] itemArray5 = new Item[5];
        itemArray5[0] = Item.get((int)301);
        itemArray5[1] = Item.get((int)309);
        itemArray5[2] = Item.get((int)317);
        itemArray5[3] = Item.get((int)313);
        itemArray5[4] = Item.get((int)0);
        F = itemArray5;
    }

    @Override
    public float getGravity() {
        return 0.092f;
    }

    @Override
    public void spawnTo(Player player) {
        Player a = player;
        FakeBot a2 = this;
        if (a2.getNetworkId() == -1) {
            super.spawnTo((Player)a);
        }
        if (!a2.hasSpawned.containsKey((Object)a.getLoaderId()) && a2.chunk != null && ((Player)a).usedChunks.containsKey((Object)Level.chunkHash((int)a2.chunk.getX(), (int)a2.chunk.getZ()))) {
            a2.hasSpawned.put((Integer) a.getLoaderId(), (Player) a);
            a.dataPacket(a2.createAddEntityPacket());
        }
    }

    public void playArm() {
        FakeBot fakeBot = this;
        Player player = Server.getInstance().getPlayerExact(fakeBot.m);
        EntityEventPacket a = new EntityEventPacket();
        ((EntityEventPacket)a).eid = fakeBot.getId();
        ((EntityEventPacket)a).event = 4;
        player.dataPacket((DataPacket)a);
    }

    @Override
    public void despawnFrom(Player player) {
        Player a = player;
        FakeBot a2 = this;
        if (a2.getNetworkId() == -1) {
            super.despawnFrom((Player)a);
        }
        if (a2.hasSpawned.containsKey((Object)a.getLoaderId())) {
            RemoveEntityPacket removeEntityPacket = new RemoveEntityPacket();
            removeEntityPacket.eid = a2.getId();
            a.dataPacket((DataPacket)removeEntityPacket);
            a2.hasSpawned.remove((Object)a.getLoaderId());
        }
    }

    @Override
    public void close() {
        FakeBot a = this;
        a.getInventory().clearAll();
        KillAura.playerBot.remove((Object)a.m);
        a.m = null;
        a.ALLATORIxDEMO = false;
        super.close();
    }

    @Override
    public double getStepHeight() {
        return 0.0;
    }

    public void setTarget(String string) {
        Object a = string;
        FakeBot a2 = this;
        a2.m = (String) a;
    }

    public void setLifeTick(Long l) {
        Long a = l;
        FakeBot a2 = this;
        a2.g = a;
    }
}

