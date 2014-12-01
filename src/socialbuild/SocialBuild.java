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
      initialize();

      _log.info("Enabled");
   }

   @Override
   public void onDisable() {
      _log.info("Disabeled");
      _sql.close();
   }

   private void initialize() {
      saveDefaultConfig();
      Messages.loadMessages();

      _log = getLogger();

      _sql = SQLWrapper.getInstance(_log);
      _sql.Connect();
      _sql.isTable();

      Config.setConfig(this);

      getCommand("sb").setExecutor(new SBCommandExecutor(this, _log));
      getServer().getPluginManager().registerEvents(new SBEventListener(), this);
   }

   private Logger _log;
   private SQLWrapper _sql;
}