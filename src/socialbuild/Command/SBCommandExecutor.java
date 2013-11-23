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
		this.plugin = plugin;
		this.log = log;
		this.sql = SQLWrapper.getInstance();
		this.permission = PermissionManager.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("sb")) {

			if (args.length == 0
					&& permission.checkPermission(sender, "sb.self")) {

				command_show_self(sender);
				return true;

			} else if (args[0].equalsIgnoreCase("update")
					&& permission.checkPermission(sender, "sb.update")) {

				command_update_owner(sender, args[1], args[2]);
				return true;

			} else if (args[0].equalsIgnoreCase("top")
					&& permission.checkPermission(sender, "sb.top")) {

				command_show_top(sender);
				return true;

			} else if (args[0].equalsIgnoreCase("reload")
					&& permission.checkPermission(sender, "sb.reload")) {

				command_reload(sender);
				return true;

			} else if (permission.checkPermission(sender, "sb.other")) {

				command_show_other(sender, args[0]);
				return true;

			}

		}

		return false;
	}

	private boolean command_show_self(CommandSender sender) {

		int count = 0;

		sql.CaliculatePlayerCount(sender.getName());

		count = sql.getPlayerCount(sender.getName());

		sender.sendMessage(ChatColor.DARK_AQUA + "you have " + count
				+ " good !!");

		return true;

	}

	private boolean command_show_top(CommandSender sender) {

		String[] message = new String[12];
		message = sql.getRanking();

		for (int i = 0; i < 12; i++) {
			if (message[i] == null) {
				return true;
			}
			sender.sendMessage(message[i]);
		}
		return true;
	}

	private boolean command_reload(CommandSender sender) {

		log.info("start reloading");

		plugin.reloadConfig();

		log.info("reload completed");

		return true;
	}

	private boolean command_show_other(CommandSender sender, String playername) {

		int count = 0;

		sql.CaliculatePlayerCount(playername);
		count = sql.getPlayerCount(playername);

		sender.sendMessage(ChatColor.DARK_AQUA + playername + " have " + count
				+ " good !!");
		return true;
	}

	private boolean command_update_owner(CommandSender sender,
			String from_owner, String to_owner) {

		sql.updateOwner(from_owner, to_owner);
		sender.sendMessage("Updated Owner!!");

		return true;
	}

	private JavaPlugin plugin;
	private SQLWrapper sql;
	private Logger log;
	private PermissionManager permission;
}
