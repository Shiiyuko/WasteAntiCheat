/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.command.Command
 *  cn.nukkit.command.CommandSender
 *  cn.nukkit.entity.data.Skin
 *  cn.nukkit.event.Listener
 *  cn.nukkit.plugin.Plugin
 *  cn.nukkit.plugin.PluginBase
 *  cn.nukkit.scheduler.AsyncTask
 *  cn.nukkit.utils.Config
 *  java.io.File
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.security.MessageDigest
 *  java.util.Base64
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Map
 */
package org.stone72;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;
import org.checkerframework.checker.units.qual.K;
import org.stone72.Ban.BanSystem;
import org.stone72.Check.*;
import org.stone72.Utils.Config;
import org.stone72.Utils.Method;

import java.io.File;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
* NICE SKIDDING
* */

public class WasteAC
extends PluginBase {
    private static cn.nukkit.utils.Config g;
    public static Skin DEFAULT_SKIN;
    public static String prefix;
    public static Plugin plugin;
    private static cn.nukkit.utils.Config ALLATORIxDEMO;
    public static String stopTime;

    /*
     * WARNING - void declaration
     */
    public static void PlusVl(String string, String a) {
        String string3;
        String string4 = string3 = string;
        String string5 = string3;
        Config.getVlMap().put((String) string4, Config.getVlMap().getOrDefault((Object)string4, new HashMap<>()));
        ((Map)Config.getVlMap().get((Object)string3)).put((Object)a, (Object)((Integer)((Map)Config.getVlMap().get((Object)string3)).getOrDefault((Object)a, (Object)0) + 1));
        if (!WasteAC.config().getStringList(new StringBuilder().insert(0, "setting.vl_event.").append((String)a).toString()).isEmpty()) {
            Iterator<String> iterator = WasteAC.config().getStringList(new StringBuilder().insert(0, "setting.vl_event.").append((String)a).toString()).iterator();
            while (iterator.hasNext()) {
                String[] a2 = (iterator.next()).split(":");
                if ((Integer)((Map)Config.getVlMap().get((Object)string3)).get((Object)a) != Integer.parseInt((String)a2[0])) {
                    continue;
                }
                String[] stringArray = a2[1].split("=");
                if (a2[4].equals((Object)"true")) {
                    //Server.getInstance().getScheduler().scheduleAsyncTask(plugin, (AsyncTask)new G(stringArray, string3, (String)a, a2));
                    continue;
                }
                if (!a2[4].equals((Object)"false")) {
                    continue;
                }
                Server.getInstance().dispatchCommand((CommandSender)Server.getInstance().getConsoleSender(), stringArray[1].replace((CharSequence)"%player%", (CharSequence)string3).replace((CharSequence)"%type%", (CharSequence)a));
                if (a2[3].equals((Object)"true")) {
                    Method.broadcastMessage(string3, (String)a);
                }
                if (a2[2].equals((Object)"true")) {
                    ((Map)Config.getVlMap().get((Object)string3)).put((Object)a, (Object)0);
                }
                a2 = a2[5].split("=");
                WasteAC.PlusKvl(string3, Integer.parseInt((String)a2[1]), (String)a);
            }
        }
    }

    @Override
    public void onDisable() {
        this.getLogger().info("WasteAC关闭成功");
    }

    public static boolean checkBuy() {
        // Funny funny auth
       return true;
    }

    static {
        prefix = "§l[§bWasteAC§f]";
        stopTime = "2038-01-01";
    }

    @Override
    public void onEnable() {
        WasteAC a22;
        WasteAC wasteAC = this;
        plugin = wasteAC;
        if (!plugin.getDataFolder().exists()) {
            wasteAC.getDataFolder().mkdirs();
        }
        WasteAC wasteAC2 = wasteAC;
        wasteAC2.saveDefaultConfig();
        g = wasteAC2.getConfig();
        Config.initConfig();
        ALLATORIxDEMO = new cn.nukkit.utils.Config(new File(wasteAC.getDataFolder(), "/playerData.yml"), 2);
        wasteAC2.getLogger().info("§b __          __     _____ ");
        wasteAC2.getLogger().info("§b \\ \\        / /\\   / ____|");
        wasteAC2.getLogger().info("§b  \\ \\  /\\  / /  \\ | |     ");
        wasteAC2.getLogger().info("§b   \\ \\/  \\/ / /\\ \\| |     ");
        wasteAC2.getLogger().info("§b    \\  /\\  / ____ \\ |____ ");
        wasteAC2.getLogger().info("§b     \\/  \\/_/    \\_\\_____|");
        wasteAC2.getLogger().info("§b                          ");
        wasteAC2.getLogger().info(new StringBuilder().insert(0, "§e本机HWID: ").append(WasteAC.getHWID()).append(" WasteAC验证中").toString());
        if (!WasteAC.checkBuy()) {
            return;
        }
        wasteAC.getLogger().info("§e验证成功,插件开始加载");
        WasteAC wasteAC3 = wasteAC;
        WasteAC wasteAC4 = wasteAC;
        //Server.getInstance().getScheduler().scheduleAsyncTask((Plugin)wasteAC4, (AsyncTask)new b(wasteAC4));
        WasteAC wasteAC5 = wasteAC;
        WasteAC wasteAC6 = wasteAC;
        wasteAC5.getLogger().info(new StringBuilder().insert(0, "§eWasteAC启动成功 当前版本: ").append(wasteAC6.getDescription().getVersion()).toString());
        wasteAC6.getLogger().info(new StringBuilder().insert(0, "§e目前已加载 ").append(Config.getCheck$enable().size()).append(" 项功能").toString());
        wasteAC5.getLogger().info("§e命令: 输入wac help提示命令帮助");
        Skin skinSkidder = new Skin();
        skinSkidder.setSkinData(Base64.getDecoder().decode("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ll9B/5ZfQf+WX0H/lm9b/6p9Zv+Wb1v/lm9b/4dVO/+WX0H/ll9B/5ZfQf+qfWb/lm9b/6p9Zv+Wb1v/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABWScz/VknM/1ZJzP9WScz/KCgo/ygoKP8oKCj/KCgo/wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKio/wDMzP8AzMz/AKio/1ExJf9qQDD/UTEl/2pAMP8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA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"));
        skinSkidder.setSkinId("skin");
        skinSkidder.setTrusted(true);
        skinSkidder.setGeometryData("skin");
        skinSkidder.setGeometryName("Geometry.Bot");
        skinSkidder.setSkinResourcePatch(Skin.GEOMETRY_CUSTOM);
        DEFAULT_SKIN = skinSkidder;
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new KillAura(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new AutoClicker(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new Reach(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new AimBot(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new Speed(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new WrongRise(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new Velocity(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new AirJump(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new BadPacket(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new Scaffold(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new ThroughWall(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new NoSlow(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new BHop(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new InventoryMove(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new Timer(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new Spammer(), (Plugin)wasteAC);
        wasteAC5.getServer().getPluginManager().registerEvents((Listener)new BanSystem(), (Plugin)wasteAC);
        int reduceVL = WasteAC.config().getInt("setting.reduce_vl") * 20;
        WasteAC wasteAC9 = wasteAC;
        //Server.getInstance().getScheduler().scheduleRepeatingTask((Plugin)wasteAC9, (Runnable)new L(wasteAC9), reduceVL);
        reduceVL = WasteAC.config().getInt("check.KillAura.remove_tick");
        WasteAC wasteAC10 = wasteAC;
        //Server.getInstance().getScheduler().scheduleRepeatingTask((Plugin)wasteAC10, (Runnable)new d(wasteAC10), reduceVL);
    }

    public static cn.nukkit.utils.Config config() {
        return g;
    }

    /*
     * WARNING - void declaration
     */
    public static void PlusKvl(String string, int a2, String a3) {
        String string3 = string;
        Method.setPlayerData(string3, "kvl", ALLATORIxDEMO.getInt(new StringBuilder().insert(0, string3).append(".kvl").toString()) + a2);
        if (!WasteAC.config().getStringList("setting.kvl_event").isEmpty()) {
            Iterator<String> events = WasteAC.config().getStringList("setting.kvl_event").iterator();
            while (events.hasNext()) {
                String[] a4 = (events.next()).split(":");
                if (ALLATORIxDEMO.getInt(new StringBuilder().insert(0, string3).append(".kvl").toString()) != Integer.parseInt((String)a4[0])) {
                    continue;
                }
                String[] stringArray = a4[1].split("=");
                if (a4[4].equals((Object)"true")) {
                    //Server.getInstance().getScheduler().scheduleAsyncTask(plugin, (AsyncTask)new K(stringArray, string3, (String)a3, a4));
                    continue;
                }
                if (!a4[4].equals((Object)"false")) {
                    continue;
                }
                Server.getInstance().dispatchCommand((CommandSender)Server.getInstance().getConsoleSender(), stringArray[1].replace((CharSequence)"%player%", (CharSequence)string3).replace((CharSequence)"%type%", (CharSequence)a3));
                if (a4[3].equals((Object)"true")) {
                    Method.broadcastMessage(string3, (String)a3);
                }
                if (a4[2].equals((Object)"true")) {
                    Method.setPlayerData(string3, "kvl", 0);
                }
                a4 = a4[5].split("=");
                WasteAC.PlusBvl(string3, Integer.parseInt((String)a4[1]), (String)a3);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void PlusBvl(String string, int a2, String a3) {
        String string3 = string;
        Method.setPlayerData(string3, "bvl", ALLATORIxDEMO.getInt(new StringBuilder().insert(0, string3).append(".bvl").toString()) + a2);
        if (!WasteAC.config().getStringList("setting.bvl_event").isEmpty()) {
            Iterator<String> events = WasteAC.config().getStringList("setting.bvl_event").iterator();
            while (events.hasNext()) {
                String[] a4 = (events.next()).split(":");
                if (ALLATORIxDEMO.getInt(new StringBuilder().insert(0, string3).append(".bvl").toString()) != Integer.parseInt((String)a4[0])) {
                    continue;
                }
                String[] stringArray = a4[1].split("=");
                if (a4[4].equals((Object)"true")) {
                    //Server.getInstance().getScheduler().scheduleAsyncTask(plugin, (AsyncTask)new D(stringArray, string3, (String)a3, a4));
                    continue;
                }
                if (!a4[4].equals((Object)"false")) {
                    continue;
                }
                Server.getInstance().dispatchCommand((CommandSender)Server.getInstance().getConsoleSender(), stringArray[1].replace((CharSequence)"%player%", (CharSequence)string3).replace((CharSequence)"%type%", (CharSequence)a3));
                if (a4[3].equals((Object)"true")) {
                    Method.broadcastMessage(string3, (String)a3);
                }
                if (!a4[2].equals((Object)"true")) {
                    continue;
                }
                Method.setPlayerData(string3, "bvl", 0);
            }
        }
    }

    public static cn.nukkit.utils.Config playerData() {
        return ALLATORIxDEMO;
    }

    public static String getHWID() {
        StringBuilder stringBuilder;
        try {
            int n;
            String string = new StringBuilder().insert(0, System.getenv((String)"COMPUTERNAME")).append(System.getProperty((String)"user.name")).append(System.getenv((String)"PROCESSOR_IDENTIFIER")).append(System.getenv((String)"PROCESSOR_LEVEL")).toString();
            MessageDigest messageDigest = MessageDigest.getInstance((String)"MD5");
            messageDigest.update(string.getBytes());
            stringBuilder = new StringBuilder();
            byte[] byArray = messageDigest.digest();
            int n2 = byArray.length;
            int n3 = n = 0;
            while (n3 < n2) {
                byte by = byArray[n];
                String string2 = Integer.toHexString((int)(0xFF & by));
                if (string2.length() == 1) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(string2);
                n3 = ++n;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return "Error";
        }
        return stringBuilder.toString();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean onCommand(CommandSender a, Command command, String string, String[] a4) {
        String[] stringArray2 = a4;
        String[] a2 = stringArray2;
        if (a.getName().equalsIgnoreCase("wac")) {
            if (a4.length == 0) {
                return false;
            }
            if (a4[0].equalsIgnoreCase("ver")) {
                a.sendMessage(prefix + " §fName: WasteAc §8| §bVersion: IDK §8| §eType: Cracked Edition");
                return true;
            }
            if (a4[0].equalsIgnoreCase("ban")) {
                if (a4.length < 9) {
                    return false;
                }
                Method.banPlayer(a4[1], a4[2], Integer.parseInt((String)a4[3]), Integer.parseInt((String)a4[4]), Integer.parseInt((String)a4[5]), Integer.parseInt((String)a4[6]), Integer.parseInt((String)a4[7]), Integer.parseInt((String)a4[8]));
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §bCompletion of enforcement").toString());
                return true;
            }
            if (a4[0].equalsIgnoreCase("unban")) {
                if (a4.length < 2) {
                    return false;
                }
                Method.unBanPlayer(a4[1]);
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §bCompletion of enforcement").toString());
                return true;
            }
            if ("kick".equalsIgnoreCase(a4[0])) {
                if (a4.length < 3) {
                    return false;
                }
                a = Server.getInstance().getPlayerExact(a4[1]);
                Method.kickPlayer((Player) a, a4[2]);
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §bCompletion of enforcement").toString());
                return true;
            }
            if (a4[0].equalsIgnoreCase("reload")) {
                WasteAC.config().reload();
                Config.initConfig();
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §bCompletion of enforcement").toString());
                return true;
            }
            if (a4[0].equalsIgnoreCase("help")) {
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §7-------------").toString());
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §bwac help §7- §e输出WasteAC命令帮助").toString());
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §bwac ver §7- §e输出WasteAC版本号").toString());
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §bwac reload §7- §e重载WasteAC配置文件").toString());
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §bwac kick [name] [type] §7- §eWasteAC踢出玩家").toString());
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §bwac ban [name] [type] [year] [month] [day] [hour] [minute] [second] §7- §eWasteAC封禁玩家").toString());
                a.sendMessage(new StringBuilder().insert(0, prefix).append(" §7-------------").toString());
                return true;
            }
            return true;
        }
        return false;
    }

    public WasteAC() {
        WasteAC a;
    }
}

