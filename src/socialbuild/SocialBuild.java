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
      loadResources();
      setupDatabase();
      setListeners();
      getLogger().info("Enabled");
   }

   @Override
   public void onDisable() {
      getLogger().info("Disabeled");
      _sql.close();
   }

   private void loadResources() {
      saveDefaultConfig();
      Messages.loadMessages();
      Config.setConfig(this);
   }

   private void setupDatabase() {
      _sql = SQLWrapper.getInstance();
      _sql.Connect();
      _sql.isTable();
   }

   private void setListeners() {
      getCommand("sb").setExecutor(new SBCommandExecutor());
      getServer().getPluginManager().registerEvents(new SBEventListener(), this);
   }

   private SQLWrapper _sql;
}