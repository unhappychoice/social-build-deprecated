package socialbuild.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Event Listener
 * 
 * @author unhappychoice
 * 
 */
public class SBEventListener implements Listener {

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent e) {
      new SBPlayerJoinEvent().execution(e);
   }

   @EventHandler
   public void onClickSign(PlayerInteractEvent e) {
      new SBSignClickEvent().execution(e);
   }

   @EventHandler
   public void onSignPlace(SignChangeEvent e) {
      new SBSignPlaceEvent().execution(e);
   }

   @EventHandler
   public void onSignBreak(BlockBreakEvent e) {
      new SBSignBreakEvent().execute(e);
   }
}
