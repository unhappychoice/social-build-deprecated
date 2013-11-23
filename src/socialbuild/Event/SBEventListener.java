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

	public SBEventListener() {
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		player_join_event = new SBPlayerJoinEvent();
		player_join_event.execution(e);

	}

	@EventHandler
	public void onClickSign(PlayerInteractEvent e) {

		sign_click_event = new SBSignClickEvent();
		sign_click_event.execution(e);

	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {

		sign_place_event = new SBSignPlaceEvent();
		sign_place_event.execution(e);

	}

	@EventHandler
	public void onSignBreak(BlockBreakEvent e) {

		sign_break_event = new SBSignBreakEvent();
		sign_break_event.execute(e);

	}

	private SBPlayerJoinEvent player_join_event;
	private SBSignClickEvent sign_click_event;
	private SBSignPlaceEvent sign_place_event;
	private SBSignBreakEvent sign_break_event;
}
