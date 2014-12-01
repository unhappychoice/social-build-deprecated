package socialbuild.Event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import socialbuild.Utility.Config;
import socialbuild.Utility.Messages;
import socialbuild.Utility.PermissionManager;
import socialbuild.Utility.SQLWrapper;

/**
 * SignClickEvent Excutor Class
 * 
 * @author unhappychoice
 * 
 */
public class SBSignClickEvent {

   public SBSignClickEvent() {
      _sql = SQLWrapper.getInstance();
      _permission = PermissionManager.getInstance();
   }

   public void execution(PlayerInteractEvent e) {

      try {

         if (e.getClickedBlock().getType().equals(Material.WALL_SIGN)
               || e.getClickedBlock().getType().equals(Material.SIGN_POST)) {

            Sign sign = (Sign) e.getClickedBlock().getState();
            Player player = e.getPlayer();
            String playername = e.getPlayer().getName();

            if (sign.getLine(0).equals(ChatColor.BLUE + "SocialBuild")) {

               // Left Click
               if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                  if (_permission.checkPermission(player, "sb.vote")) {

                     LeftClick(sign, playername, e);

                  }

                  // Right Click
               } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                  if (_permission.checkPermission(player, "sb.cancel")) {

                     RightClick(sign, playername, e);

                  }
               }
            }
         }
      } catch (NullPointerException ne) {
      }
   }

   private void LeftClick(Sign sign, String playername, PlayerInteractEvent e) {

      // Get sign Location
      Location loc = sign.getLocation();
      int x = loc.getBlockX();
      int y = loc.getBlockY();
      int z = loc.getBlockZ();

      // Check if there is the sign
      int signid = _sql.isSign(x, y, z);

      if (signid != -1) {
         if (!_sql.isPlayer(playername, signid)) {

            // Get parameters
            String owner = _sql.getOwner(signid);
            Player ownerplayer = Bukkit.getServer().getPlayer(owner);

            // Prevent self good
            if (!playername.equals(owner)) {

               // Update the database
               _sql.insertPlayer(playername, signid);
               _sql.CaliculatePlayerCount(owner);

               // Update the sign
               int favcount = _sql.getSignCount(signid);
               sign.setLine(1, ChatColor.DARK_AQUA + "good : " + favcount);
               sign.update();

               // Send Message to player
               e.getPlayer().sendMessage(Messages.PLAYER_GIVE);

               // Send Message to owner
               if (ownerplayer != null) {
                  ownerplayer.sendMessage(playername
                        + Messages.PLAYER_GIVEN);
               }

               // promote
               if (_sql.getPlayerCount(owner) == Config.PROMOTE_GOOD.get(0)) {
                  _permission.promoteGroup(ownerplayer.getName());
               }
            } else {
               e.getPlayer().sendMessage(Messages.ERROR_SELF_GOOD);
            }
         }

         // Prevent break the sign from creative mode
         if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
         }
      }

   }

   private void RightClick(Sign sign, String playername, PlayerInteractEvent e) {

      // Get sign location
      Location loc = sign.getLocation();
      int x = loc.getBlockX();
      int y = loc.getBlockY();
      int z = loc.getBlockZ();

      // Check if there is sign
      int signid = _sql.isSign(x, y, z);
      if (signid != -1) {
         if (_sql.isPlayer(playername, signid)) {

            // Get parameters
            String owner = _sql.getOwner(signid);
            Player ownerplayer = Bukkit.getServer().getPlayer(owner);

            // Update the database
            _sql.deletePlayer(playername, signid);
            _sql.CaliculatePlayerCount(owner);

            // Update the sign
            int favcount = _sql.getSignCount(signid);
            sign.setLine(1, ChatColor.DARK_AQUA + "good : " + favcount);
            sign.update();

            // Send message to player
            e.getPlayer().sendMessage(Messages.PLAYER_CANCEL);
            ownerplayer.sendMessage(playername + Messages.PLAYER_CANCELED);

            // demote
            if (_sql.getPlayerCount(owner) == Config.PROMOTE_GOOD.get(0) - 1) {
               _permission.demoteGroup(owner);
            }
         }
      }

   }

   private SQLWrapper _sql;
   private PermissionManager _permission;
}
