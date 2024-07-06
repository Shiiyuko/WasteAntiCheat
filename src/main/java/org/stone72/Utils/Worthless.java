package org.stone72.Utils;

import cn.nukkit.scheduler.Task;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Base64;
import org.stone72.WasteAC;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Math.DateTime;

public class Worthless {
   public static String getHWID() {
      try {
         String toEncrypt = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
         MessageDigest md = MessageDigest.getInstance("MD5");
         md.update(toEncrypt.getBytes());
         byte[] byteData = md.digest();
         StringBuilder hexString = new StringBuilder();
         byte[] var4 = byteData;
         int var5 = byteData.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            byte aByteData = var4[var6];
            String hex = Integer.toHexString(255 & aByteData);
            if (hex.length() == 1) {
               hexString.append('0');
            }

            hexString.append(hex);
         }

         return hexString.toString();
      } catch (Exception var9) {
         WasteAC.getApi().info("获取错误机器码: " + var9.getMessage());
         return "Error";
      }
   }

   public static boolean coupling() {
      return true; // NICE VERIFY
   }

   private static void scheduleDisableTask(final LocalDateTime endDate) {
      Config.serverTask.scheduleRepeatingTask(WasteAC.getInstance(), new Task() {
         public void onRun(int i) {
            if (DateTime.getDate().isAfter(endDate)) {
               WasteAC.getApi().info("订阅已过期，请续订您的订阅。");
               Config.server.getPluginManager().disablePlugin(WasteAC.getInstance());
            }

         }
      }, 3600);
   }

   public static void sleep(long milliseconds) {
      try {
         Thread.sleep(milliseconds);
      } catch (InterruptedException var3) {
         throw new RuntimeException(var3);
      }
   }
}
