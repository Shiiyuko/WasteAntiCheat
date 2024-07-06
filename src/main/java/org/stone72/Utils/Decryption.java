package org.stone72.Utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Decryption {
   public static byte[] encryptContent(String input) throws Exception {
      byte[] fileBytes = input.getBytes();
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] md5Hash = md.digest("WasteAC".getBytes());
      Cipher rsaCipher = Cipher.getInstance("RSA");
      rsaCipher.init(1, getPublicKey());
      byte[] encryptedKey = rsaCipher.doFinal(md5Hash);
      Cipher aesCipher = Cipher.getInstance("AES");
      SecretKeySpec aesKey = new SecretKeySpec(md5Hash, "AES");
      aesCipher.init(1, aesKey);
      byte[] encryptedContent = aesCipher.doFinal(fileBytes);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      outputStream.write(encryptedKey);
      outputStream.write(encryptedContent);
      return outputStream.toByteArray();
   }

   private static PublicKey getPublicKey() throws Exception {
      byte[] keyBytes = loadPublicKeyFromURL("https://oss.chaoxingscitech.cn/PUBLIC_KEY.key");
      X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePublic(spec);
   }

   public static String decrypt_AES(String encryptedText) throws Exception {
      SecretKeySpec aesKey = new SecretKeySpec("ZVFvg4TpuL8sBAzP9mFnj3cIYCa5g2Vi".getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(2, aesKey);
      byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
      return new String(decryptedBytes);
   }

   public static byte[] loadPublicKeyFromURL(String publicKeyUrl) throws Exception {
      URL url = new URL(publicKeyUrl);
      URLConnection connection = url.openConnection();
      InputStream inputStream = connection.getInputStream();
      Throwable var4 = null;

      try {
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         byte[] buffer = new byte[4096];

         int bytesRead;
         while((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
         }

         byte[] var8 = byteArrayOutputStream.toByteArray();
         return var8;
      } catch (Throwable var17) {
         var4 = var17;
         throw var17;
      } finally {
         if (inputStream != null) {
            if (var4 != null) {
               try {
                  inputStream.close();
               } catch (Throwable var16) {
                  var4.addSuppressed(var16);
               }
            } else {
               inputStream.close();
            }
         }

      }
   }
}
