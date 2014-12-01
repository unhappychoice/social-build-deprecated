package socialbuild.Event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import socialbuild.Utility.Messages;
import socialbuild.Utility.PermissionManager;
import socialbuild.Utility.SQLWrapper;

/**
 * SignPlaceEvent Excutor Class
 * 
 * @author unhappychoice
 * 
 */
public class SBSignPlaceEvent {

   public SBSignPlaceEvent() {
      _sql = SQLWrapper.getInstance();
      _permission = PermissionManager.getInstance();
   }

   public void execution(SignChangeEvent e) {

      if (e.getLine(0).equals("[sb]")) {

         Player player = e.getPlayer();

         // check _permission
         if (!_permission.hasPermission(player, "sb.place")) {
            return;
         }

         int x = e.getBlock().getLocation().getBlockX();
         int y = e.getBlock().getLocation().getBlockY();
         int z = e.getBlock().getLocation().getBlockZ();
         String owner = e.getPlayer().getName();

         if (_sql.isSign(x, y, z) == -1) {
            _sql.insertSign(owner, x, y, z);
            _sql.CaliculatePlayerCount(owner);
         }

         e.setLine(0, ChatColor.BLUE + "SocialBuild");
         e.setLine(1, ChatColor.DARK_AQUA + "good! : 0");
         e.setLine(2, e.getPlayer().getName());

         e.getPlayer().sendMessage(Messages.SIGN_CREATE);
      }

   }

   private SQLWrapper _sql;
   private PermissionManager _permission;
}
