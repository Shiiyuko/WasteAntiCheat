package org.stone72.Utils.Math;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.TimeZone;

public class DateTime {
   public static String[] dateDifference(LocalDateTime dtLocal, LocalDateTime dtLocal2) {
      String[] date = new String[6];
      Period period = Period.between(dtLocal.toLocalDate(), dtLocal2.toLocalDate());
      Duration duration = Duration.between(dtLocal, dtLocal2);
      date[0] = String.valueOf(period.getYears());
      date[1] = String.valueOf(period.getMonths());
      date[2] = String.valueOf(period.getDays());
      date[3] = String.valueOf(duration.toHours() % 24L);
      date[4] = String.valueOf(duration.toMinutes() % 60L);
      date[5] = String.valueOf(duration.getSeconds() % 60L);
      return date;
   }

   public static String getDateString(LocalDateTime ld) {
      return ld.getYear() + "." + ld.getMonthValue() + "." + ld.getDayOfMonth() + "." + ld.getHour() + "." + ld.getMinute() + "." + ld.getSecond();
   }

   public static LocalDateTime parseDateString(String string) {
      String[] strings = string.split("\\.");
      return string.length() >= 6 ? LocalDateTime.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5])) : LocalDateTime.now();
   }

   public static LocalDateTime getDate() {
      return LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId());
   }
}
