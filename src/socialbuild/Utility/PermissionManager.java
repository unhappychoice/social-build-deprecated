package socialbuild.Utility;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * Permission Class
 * 
 * @author unhappychoice
 * 
 */
public class PermissionManager {



   public static PermissionManager getInstance() {
      return instance;
   }

   public boolean checkPermission(Player player, String permission) {
      if (player.hasPermission(permission)) {
         return true;
      } else {
         player.sendMessage(Messages.ERROR_NO_PERM);
         return false;
      }
   }

   public boolean checkPermission(CommandSender sender, String permission) {
      if (sender.hasPermission(permission)) {
         return true;
      } else {
         sender.sendMessage(Messages.ERROR_NO_PERM);
         return false;
      }
   }

   public void promoteGroup(String _user) {

      PermissionUser user = PermissionsEx.getUser(_user);
      String[] groups = user.getGroupsNames();

      for (String groupname : groups) {
         if (groupname.equals(Config.GROUP_NAME.get(0))) {

            user.addGroup(Config.GROUP_NAME.get(1));
            user.removeGroup(Config.GROUP_NAME.get(0));
            Bukkit.broadcastMessage(_user + Messages.PLAYER_PROMOTED);

         }
      }
   }

   public void demoteGroup(String _user) {

      PermissionUser user = PermissionsEx.getUser(_user);
      String[] groups = user.getGroupsNames();

      for (String groupname : groups) {
         if (groupname.equals(Config.GROUP_NAME.get(1))) {

            user.removeGroup(Config.GROUP_NAME.get(1));
            user.addGroup(Config.GROUP_NAME.get(0));
            Bukkit.broadcastMessage(_user + Messages.PLAYER_DEMOTED);

         }
      }
   }

   private PermissionManager() {}
   private static PermissionManager instance = new PermissionManager();
}
