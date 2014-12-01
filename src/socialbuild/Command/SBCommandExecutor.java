package socialbuild.Command;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import socialbuild.Utility.PermissionManager;
import socialbuild.Utility.SQLWrapper;

/**
 * Command Execute Class
 * 
 * @author unhappychoice
 * 
 */
public class SBCommandExecutor implements CommandExecutor {

   public SBCommandExecutor(JavaPlugin plugin, Logger log) {
      _plugin = plugin;
      _log = log;
      _sql = SQLWrapper.getInstance();
      _permission = PermissionManager.getInstance();
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

      if (!cmd.getName().equalsIgnoreCase("sb")) {
         return false;
      }

      if (args.length == 0 && _permission.checkPermission(sender, "sb.self")) {
         command_show_self(sender);
      } else if (args[0].equalsIgnoreCase("update") && _permission.checkPermission(sender, "sb.update")) {
         command_update_owner(sender, args[1], args[2]);
      } else if (args[0].equalsIgnoreCase("top") && _permission.checkPermission(sender, "sb.top")) {
         command_show_top(sender);
      } else if (args[0].equalsIgnoreCase("reload") && _permission.checkPermission(sender, "sb.reload")) {
         command_reload(sender);
      } else if (_permission.checkPermission(sender, "sb.other")) {
         command_show_other(sender, args[0]);
      } else {
         return false;
      }
      return true;
   }

   private boolean command_show_self(CommandSender sender) {
      _sql.CaliculatePlayerCount(sender.getName());
      int count = _sql.getPlayerCount(sender.getName());
      sender.sendMessage(ChatColor.DARK_AQUA + "you have " + count + " good !!");
      return true;
   }

   private boolean command_show_top(CommandSender sender) {
      String[] message = new String[12];
      message = _sql.getRanking();
      for (int i = 0; i < 12; i++) {
         if (message[i] == null) {
            return true;
         }
         sender.sendMessage(message[i]);
      }
      return true;
   }

   private boolean command_reload(CommandSender sender) {
      _log.info("start reloading");
      _plugin.reloadConfig();
      _log.info("reload completed");
      return true;
   }

   private boolean command_show_other(CommandSender sender, String playername) {
      _sql.CaliculatePlayerCount(playername);
      int count = _sql.getPlayerCount(playername);
      sender.sendMessage(ChatColor.DARK_AQUA + playername + " have " + count + " good !!");
      return true;
   }

   private boolean command_update_owner(CommandSender sender, String from_owner, String to_owner) {
      _sql.updateOwner(from_owner, to_owner);
      sender.sendMessage("Updated Owner!!");
      return true;
   }

   private JavaPlugin _plugin;
   private SQLWrapper _sql;
   private Logger _log;
   private PermissionManager _permission;
}
