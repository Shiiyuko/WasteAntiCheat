package org.stone72.Utils.Math;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Calculation {
   public static boolean isInteger(double number) {
      return Math.floor(number) == number;
   }

   public static double getMiddle(double num1, double num2) {
      return num1 <= num2 ? (num2 - num1) / 2.0 + num1 : (num1 - num2) / 2.0 + num2;
   }

   public static double change(double number) {
      return number < 0.0 ? -number : number;
   }

   public static double positive(double number) {
      return number < 0.0 ? 0.0 : number;
   }

   public static int repeat(String input) {
      AtomicInteger count = new AtomicInteger();
      Map<Character, Long> charCountMap = input.chars().mapToObj((c) -> {
         return (char)c;
      }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      charCountMap.forEach((character, bit) -> {
         if (bit > 1L) {
            count.addAndGet(Math.toIntExact(bit));
         }

      });
      return count.get();
   }

   public static double round(double number, int decimals) {
      number *= Math.pow(10.0, (double)decimals);
      number = (double)Math.round(number);
      return number / Math.pow(10.0, (double)decimals);
   }

   public static double wrapAngle(double angle) {
      angle %= 360.0;
      if (angle >= 180.0) {
         angle -= 360.0;
      }

      if (angle < -180.0) {
         angle += 360.0;
      }

      return angle;
   }

   public static void writeFile(String path, String s) {
      try {
         List lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
         BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
         lines.add(s);
         Iterator var4 = lines.iterator();

         while(var4.hasNext()) {
            Object s1 = (String)var4.next();
            fw.write(s1 + "\n");
         }

         fw.close();
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }
}
