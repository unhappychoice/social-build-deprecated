package socialbuild.Event;

import org.bukkit.event.player.PlayerJoinEvent;

import socialbuild.Utility.Config;
import socialbuild.Utility.PermissionManager;
import socialbuild.Utility.SQLWrapper;

/**
 * PlayerJoinEvent Excutor Class
 * 
 * @author unhappychoice
 * 
 */
public class SBPlayerJoinEvent {

   public SBPlayerJoinEvent() {
      _sql = SQLWrapper.getInstance();
      _permission = PermissionManager.getInstance();
   }

   public void execution(PlayerJoinEvent e) {
      String name = e.getPlayer().getName();
      _sql.CaliculatePlayerCount(name);

      // demote
      if (_sql.getPlayerCount(name) < Config.PROMOTE_GOOD.get(0)) {
         _permission.demoteGroup(name);
      }
      // promote
      if (_sql.getPlayerCount(name) >= Config.PROMOTE_GOOD.get(0)) {
         _permission.promoteGroup(name);
      }
   }

   private PermissionManager _permission;
   private SQLWrapper _sql;
}
