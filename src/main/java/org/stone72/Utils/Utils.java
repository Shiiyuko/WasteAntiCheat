package org.stone72.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
   public static String sendGetRequest(String apiUrl) {
      HttpURLConnection connection = null;
      BufferedReader reader = null;
      StringBuilder response = new StringBuilder();

      try {
         URL url = new URL(apiUrl);
         connection = (HttpURLConnection)url.openConnection();
         connection.setRequestMethod("GET");
         int responseCode = connection.getResponseCode();
         if (responseCode != 200) {
            throw new IOException("无法解析的认证CODE: " + responseCode);
         }

         reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

         String line;
         while((line = reader.readLine()) != null) {
            response.append(line);
         }
      } catch (MalformedURLException var15) {
         throw new IllegalArgumentException("无效的 URL: " + apiUrl, var15);
      } catch (IOException var16) {
         throw new RuntimeException("向 URL 发送 GET 请求时出错: " + apiUrl, var16);
      } finally {
         if (reader != null) {
            try {
               reader.close();
            } catch (IOException var14) {
               var14.printStackTrace();
            }
         }

         if (connection != null) {
            connection.disconnect();
         }

      }

      return response.toString();
   }

   public static String extractSystemTime(String json) {
      return json.substring(json.indexOf("\"sysTime2\":\"") + 12, json.indexOf("\",\"sysTime1\""));
   }
}
