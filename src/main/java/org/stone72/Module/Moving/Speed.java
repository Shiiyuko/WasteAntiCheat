package org.stone72.Module.Moving;

import cn.nukkit.Server;
import cn.nukkit.event.Event;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.level.Location;
import cn.nukkit.network.protocol.PlayerAuthInputPacket;
import cn.nukkit.network.protocol.types.AuthInputAction;
import cn.nukkit.scheduler.AsyncTask;
import org.stone72.API.WasteAPI;
import org.stone72.Function.ToolSystem;
import org.stone72.Thread.BasicTasks;
import org.stone72.Utils.Configs.Checks;
import org.stone72.Utils.Configs.Config;
import org.stone72.Utils.Math.Judge;
import org.stone72.Utils.Math.Nukkit;
import org.stone72.Utils.Worthless;
import org.stone72.WasteAC;

import java.util.ArrayList;
import java.util.HashMap;

public class Speed {
   public static ArrayList<String> isSkate = new ArrayList();
   public static HashMap<String, Integer> keepTick = new HashMap();
   public static HashMap<String, Integer> keepCount = new HashMap();

   public void check(DataPacketReceiveEvent e) {
      if (WasteAPI.canCheck(WasteAPI.Moving[2], e.getPlayer()) && e.getPacket() instanceof PlayerAuthInputPacket) {
         PlayerAuthInputPacket packet = (PlayerAuthInputPacket)e.getPacket();
         if (Checks.isTypeEnable(WasteAPI.Moving[2], "type-a")
                 && packet.getInputData().contains(AuthInputAction.START_SPRINTING)
                 && !Judge.isMove((PlayerAuthInputPacket)packet)
                 && packet.getDelta().x == 0.0F
                 && packet.getDelta().z == 0.0F) {
            WasteAPI.solveCheat(
                    e,
                    true,
                    e.getPlayer(),
                    WasteAPI.Moving[2],
                    "type-a",
                    "packet-Bad",
                    "When the player sends a packet, it contains abnormal data, which may be Speed"
            );
         }

         if (Checks.isTypeEnable(WasteAPI.Moving[2], "type-b")
                 && packet.getInputData().contains(AuthInputAction.START_JUMPING)
                 && !packet.getInputData().contains(AuthInputAction.JUMPING)) {
            WasteAPI.solveCheat(
                    (Event)e,
                    true,
                    e.getPlayer(),
                    WasteAPI.Moving[2],
                    "type-b",
                    "packet-Bad",
                    "When the player sends a packet, it contains abnormal data, which may be Speed"
            );
         }

         if (Checks.isTypeEnable(WasteAPI.Moving[2], "type-c") && !BasicTasks.serverLag && !ToolSystem.pingLag.contains(e.getPlayer().getName())) {
            int keep_count = Checks.getType(WasteAPI.Moving[2], "type-c", "keep_count", "int").IntValue;
            Location loc = e.getPlayer().getLocation();
            if (Judge.isSkate((Location)loc, 2) && !isSkate.contains(e.getPlayer().getName())) {
               isSkate.add(e.getPlayer().getName());
            } else {
               Server.getInstance().getScheduler().scheduleAsyncTask(WasteAC.getInstance(), new AsyncTask() {
                  public void onRun() {
                     if (Speed.isSkate.contains(e.getPlayer().getName())) {
                        Worthless.sleep((long)WasteAC.getChecks().getInt("Tool.delay_skate_calculate"));
                        Speed.isSkate.remove(e.getPlayer().getName());
                     }
                  }
               });
            }

            if (!isSkate.contains(e.getPlayer().getName())) {
               switch(Nukkit.getMoveDirection((PlayerAuthInputPacket)packet)) {
                  case 0:
                     keepCount.remove(e.getPlayer().getName());
                     keepTick.put(e.getPlayer().getName(), Config.server.getTick());
                     Nukkit.checkSpeed(
                             (PlayerAuthInputPacket)packet,
                             Checks.getType(WasteAPI.Moving[2], "type-c", "forward_move", "double").DoubleValue,
                             (Location)loc,
                             (DataPacketReceiveEvent)e,
                             "forward"
                     );
                     break;
                  case 1:
                     keepCount.put(e.getPlayer().getName(), keepCount.getOrDefault(e.getPlayer().getName(), 0) + 1);
                     if (keepCount.get(e.getPlayer().getName()) >= keep_count) {
                        if (!ToolSystem.isKnockback.contains(e.getPlayer().getName())) {
                           if (packet.getMotion().y > 0.0) {
                              WasteAPI.solveCheat(
                                      (Event)e,
                                      true,
                                      e.getPlayer(),
                                      "Speed",
                                      "type-c",
                                      "motionZ-" + packet.getMotion().y + " moveState-behind_move",
                                      "When the player is moving, their speed exceeds the normal speed, which may be Speed"
                              );
                           }

                           Nukkit.checkSpeed(
                                   (PlayerAuthInputPacket)packet,
                                   Checks.getType(WasteAPI.Moving[2], "type-c", "behind_move", "double").DoubleValue,
                                   (Location)loc,
                                   (DataPacketReceiveEvent)e,
                                   "behind"
                           );
                        } else {
                           Nukkit.checkSpeed(
                                   (PlayerAuthInputPacket)packet,
                                   Checks.getType(WasteAPI.Moving[2], "type-c", "behind_knock", "double").DoubleValue,
                                   (Location)loc,
                                   (DataPacketReceiveEvent)e,
                                   "behind_knock"
                           );
                        }
                     }
                     break;
                  case 2:
                     keepCount.put(e.getPlayer().getName(), keepCount.getOrDefault(e.getPlayer().getName(), 0) + 1);
                     if (keepCount.get(e.getPlayer().getName()) >= keep_count) {
                        if (!ToolSystem.isKnockback.contains(e.getPlayer().getName())) {
                           Nukkit.checkSpeed(
                                   (PlayerAuthInputPacket)packet,
                                   Checks.getType(WasteAPI.Moving[2], "type-c", "left_move", "double").DoubleValue,
                                   (Location)loc,
                                   (DataPacketReceiveEvent)e,
                                   "left"
                           );
                        } else {
                           Nukkit.checkSpeed(
                                   (PlayerAuthInputPacket)packet,
                                   Checks.getType(WasteAPI.Moving[2], "type-c", "left_knock", "double").DoubleValue,
                                   (Location)loc,
                                   (DataPacketReceiveEvent)e,
                                   "left_knock"
                           );
                        }
                     }
                     break;
                  default:
                     keepCount.remove(e.getPlayer().getName());
                     keepTick.put(e.getPlayer().getName(), Config.server.getTick());
                     Nukkit.checkSpeed(
                             (PlayerAuthInputPacket)packet,
                             Checks.getType(WasteAPI.Moving[2], "type-c", "up_left_move", "double").DoubleValue,
                             (Location)loc,
                             (DataPacketReceiveEvent)e,
                             "up_left"
                     );
               }
            }
         }
      }
   }
}
