package org.stone72.Utils.Configs;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.stone72.WasteAC;

public class Checks {
   public static boolean isTypeEnable(String check, String type) {
      return WasteAC.getChecks().getBoolean(getWhereCheck(check) + "." + check + ".modules." + type + ".enable") && WasteAC.getChecks().getBoolean(getWhereCheck(check) + "." + check + ".enable");
   }

   public static int getTypeFlag(String check, String type) {
      return WasteAC.getChecks().getInt(getWhereCheck(check) + "." + check + ".modules." + type + ".flag_value");
   }

   public static List<String> getTypeVl(String check, String type) {
      return WasteAC.getChecks().getStringList(getWhereCheck(check) + "." + check + ".modules." + type + ".vl_event");
   }

   public static Container getType(String check, String type, String p, String attribute) {
      String key = getWhereCheck(check) + "." + check + ".modules." + type + "." + p;
      switch (attribute.toLowerCase()) {
         case "string":
            return new Container(WasteAC.getChecks().getString(key));
         case "int":
            return new Container(WasteAC.getChecks().getInt(key));
         case "double":
            return new Container(WasteAC.getChecks().getDouble(key));
         case "boolean":
            return new Container(WasteAC.getChecks().getBoolean(key));
         case "long":
            return new Container(WasteAC.getChecks().getLong(key));
         case "double list":
            return new Container(WasteAC.getChecks().getDoubleList(key), "double");
         case "float list":
            return new Container(WasteAC.getChecks().getFloatList(key), "float");
         case "int list":
            return new Container(WasteAC.getChecks().getIntegerList(key), "int");
         case "string list":
            return new Container(WasteAC.getChecks().getStringList(key), "string");
         default:
            return new Container();
      }
   }

   public static int getEnableCount() {
      int count = 0;

      try {
         List lines = Files.readAllLines(Paths.get(WasteAC.checksPath), StandardCharsets.UTF_8);
         Iterator var2 = lines.iterator();

         while(var2.hasNext()) {
            String s = (String)var2.next();
            if (s.replace(" ", "").equalsIgnoreCase("enable:true")) {
               ++count;
            }
         }

         return count;
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public static String getWhereCheck(String check) {
      if (WasteAC.getChecks().exists("Combat_Check." + check)) {
         return "Combat_Check";
      } else if (WasteAC.getChecks().exists("Moving_Check." + check)) {
         return "Moving_Check";
      } else {
         return WasteAC.getChecks().exists("Player_Check." + check) ? "Player_Check" : "Misc_Check";
      }
   }

   public static void initChecks() {
      WasteAC.getChecks().reload();
   }

   public static class Container {
      public int IntValue = -1;
      public long LongValue = -1L;
      public double DoubleValue = -1.0;
      public boolean BooleanValue = false;
      public String StringValue = null;
      public List<Double> DoubleListValue = new ArrayList();
      public List<Float> FloatListValue = new ArrayList();
      public List<Integer> IntListValue = new ArrayList();
      public List<String> StringListValue = new ArrayList();

      public Container() {
      }

      public Container(int i) {
         this.IntValue = i;
      }

      public Container(double d) {
         this.DoubleValue = d;
      }

      public Container(boolean b) {
         this.BooleanValue = b;
      }

      public Container(long l) {
         this.LongValue = l;
      }

      public Container(String s) {
         this.StringValue = s;
      }

      public Container(List l, String type) {
         switch (type.toLowerCase()) {
            case "double":
               this.DoubleListValue = l;
               break;
            case "string":
               this.StringListValue = l;
               break;
            case "float":
               this.FloatListValue = l;
               break;
            case "int":
               this.IntListValue = l;
         }

      }
   }
}
