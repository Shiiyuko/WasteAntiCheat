/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 */
package org.stone72.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.stone72.WasteAC;

public class Config {
    public static int check$Scaffold$min_tick;
    public static double check$ThroughWall$max_move;
    public static double check$BHop$normal_move;
    public static List<String> setting$excluded_worlds;
    public static int check$NoSlow$clear_time;
    public static int setting$port;
    public static int check$AutoClicker$max_cps;
    public static double check$Speed$max_move;
    public static double check$NoSlow$normal_move;
    public static String setting$kick_message;
    public static boolean setting$debug;
    public static String setting$banned_message;
    public static List<String> check$enable;
    public static int check$Timer$max_packet;
    public static Map<String, Map<String, Integer>> vlMap;
    public static double check$WrongRise$max_rise;
    public static double check$Reach$max_reach;
    public static String setting$broadcast_message;
    public static int check$KillAura$min_attack_delay;
    public static double check$Scaffold$max_yaw;
    public static int check$KillAura$max_attack_bot;

    public static int getSetting$port() {
        return setting$port;
    }

    public static int getCheck$KillAura$max_attack_bot() {
        return check$KillAura$max_attack_bot;
    }

    public static double getCheck$BHop$normal_move() {
        return check$BHop$normal_move;
    }

    public static double getCheck$Speed$max_move() {
        return check$Speed$max_move;
    }

    public static String getSetting$banned_message() {
        return setting$banned_message;
    }

    public static int getCheck$AutoClicker$max_cps() {
        return check$AutoClicker$max_cps;
    }

    public static double getCheck$Scaffold$max_yaw() {
        return check$Scaffold$max_yaw;
    }

    public static List<String> getSetting$excluded_worlds() {
        return setting$excluded_worlds;
    }

    public static double getCheck$NoSlow$normal_move() {
        return check$NoSlow$normal_move;
    }

    public static double getCheck$ThroughWall$max_move() {
        return check$ThroughWall$max_move;
    }

    public static String getSetting$broadcast_message() {
        return setting$broadcast_message;
    }

    public static boolean isSetting$debug() {
        return setting$debug;
    }

    public static int getCheck$Timer$max_packet() {
        return check$Timer$max_packet;
    }

    public static Map<String, Map<String, Integer>> getVlMap() {
        return vlMap;
    }

    public static double getCheck$WrongRise$max_rise() {
        return check$WrongRise$max_rise;
    }

    public static int getCheck$Scaffold$min_tick() {
        return check$Scaffold$min_tick;
    }

    public static void initConfig() {
        check$enable = WasteAC.config().getStringList("check.enable");
        check$KillAura$min_attack_delay = WasteAC.config().getInt("check.KillAura.min_attack_delay");
        check$KillAura$max_attack_bot = WasteAC.config().getInt("check.KillAura.max_attack_bot");
        check$AutoClicker$max_cps = WasteAC.config().getInt("check.AutoClicker.max_cps");
        check$ThroughWall$max_move = WasteAC.config().getDouble("check.ThroughWall.max_move");
        check$Scaffold$min_tick = WasteAC.config().getInt("check.Scaffold.min_tick");
        check$Scaffold$max_yaw = WasteAC.config().getDouble("check.Scaffold.max_yaw");
        check$WrongRise$max_rise = WasteAC.config().getDouble("check.WrongRise.max_rise");
        check$Reach$max_reach = WasteAC.config().getDouble("check.Reach.max_reach");
        check$BHop$normal_move = WasteAC.config().getDouble("check.BHop.normal_move");
        check$NoSlow$normal_move = WasteAC.config().getDouble("check.NoSlow.normal_move");
        check$NoSlow$clear_time = WasteAC.config().getInt("check.NoSlow.clear_time");
        check$Timer$max_packet = WasteAC.config().getInt("check.Timer.max_packet");
        check$Speed$max_move = WasteAC.config().getDouble("check.Speed.max_move");
        setting$port = WasteAC.config().getInt("setting.port");
        setting$excluded_worlds = WasteAC.config().getStringList("setting.excluded_worlds");
        setting$debug = WasteAC.config().getBoolean("setting.debug");
        setting$kick_message = WasteAC.config().getString("setting.kick_message");
        setting$banned_message = WasteAC.config().getString("setting.banned_message");
        setting$broadcast_message = WasteAC.config().getString("setting.broadcast_message");
    }

    public Config() {
        Config a;
    }

    static {
        vlMap = new HashMap();
    }

    public static List<String> getCheck$enable() {
        return check$enable;
    }

    public static int getCheck$KillAura$min_attack_delay() {
        return check$KillAura$min_attack_delay;
    }

    public static int getCheck$NoSlow$clear_time() {
        return check$NoSlow$clear_time;
    }

    public static double getCheck$Reach$max_reach() {
        return check$Reach$max_reach;
    }

    public static String getSetting$kick_message() {
        return setting$kick_message;
    }
}

