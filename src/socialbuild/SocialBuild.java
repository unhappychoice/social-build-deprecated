package socialbuild;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import socialbuild.Command.SBCommandExecutor;
import socialbuild.Event.SBEventListener;
import socialbuild.Utility.Config;
import socialbuild.Utility.Messages;
import socialbuild.Utility.SQLWrapper;

/**
 * SocialBuild main Class
 * 
 * @author unhappychoice
 * 
 */
public class SocialBuild extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {

		Initialize();
		log.info("Enabled");

	}

	@Override
	public void onDisable() {

		log.info("Disabeled");
		sql.close();

	}

	private void Initialize() {

		saveDefaultConfig();

		log = getLogger();

		Messages.loadMessages();
		log.info("Read the Locale File");

		sql = SQLWrapper.getInstance(log);
		sql.Connect();
		sql.isTable();

		Config.setConfig(this);

		executor = new SBCommandExecutor(this, log);
		getCommand("sb").setExecutor(executor);

		getServer().getPluginManager().registerEvents(new SBEventListener(),
				this);

	}

	private Logger log;
	private SQLWrapper sql;
	private SBCommandExecutor executor;
}