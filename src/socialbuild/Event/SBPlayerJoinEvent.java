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

		sql = SQLWrapper.getInstance();
		permission = PermissionManager.getInstance();
	}

	public void execution(PlayerJoinEvent e) {

		String playername = e.getPlayer().getName();
		sql.CaliculatePlayerCount(playername);

		// demote
		if (sql.getPlayerCount(playername) <= Config.PROMOTE_GOOD.get(0)) {
			permission.demoteGroup(playername);
		}
		// promote
		if (sql.getPlayerCount(playername) >= Config.PROMOTE_GOOD.get(0)) {
			permission.promoteGroup(playername);
		}
	}

	private PermissionManager permission;
	private SQLWrapper sql;
}
