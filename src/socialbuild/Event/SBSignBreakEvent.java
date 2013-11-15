package socialbuild.Event;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import socialbuild.Utility.Config;
import socialbuild.Utility.Messages;
import socialbuild.Utility.PermissionManager;
import socialbuild.Utility.SQLWrapper;

/**
 * SignBreakEvent Excutor Class
 * 
 * @author unhappychoice
 * 
 */
public class SBSignBreakEvent {

	public SBSignBreakEvent() {
		sql = SQLWrapper.getInstance();
		permission = PermissionManager.getInstance();
	}

	public void execute(BlockBreakEvent e) {

		if (e.getBlock().getType().equals(Material.WALL_SIGN)
				|| e.getBlock().getType().equals(Material.SIGN_POST)) {

			// Get Sign Location
			Sign sign = (Sign) e.getBlock().getState();
			Player player = e.getPlayer();
			
			int x = sign.getLocation().getBlockX();
			int y = sign.getLocation().getBlockY();
			int z = sign.getLocation().getBlockZ();

			// Check if there is the sign and Get ID
			int signid = sql.isSign(x, y, z);
			if (signid != -1 ) {
				
				//Check permission
				if(!permission.checkPermission(player, "sb.break")){
					return;
				}

				String owner;
				owner = sql.getOwner(signid);

				// Check can break sign
				if ( ( player.getName().equals(owner) || player.hasPermission("sb.break.other") ) && player.getGameMode().equals(GameMode.SURVIVAL)) {
					
					sql.deleteSign(x, y, z);
					sql.deletePlayer(signid);
					sql.CaliculatePlayerCount(owner);
					
					// demote
					if (sql.getPlayerCount(owner) == Config.PROMOTE_GOOD.get(0) - 1) {
						permission.demoteGroup(owner);
					}
					
				}
				
				else{
				
					e.getPlayer().sendMessage(Messages.ERROR_BREAK);
					e.setCancelled(true);
					return;
					
				}

			}
		}
	}

	private SQLWrapper sql;
	private PermissionManager permission;
}
