package socialbuild.Utility;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Config Class
 * 
 * @author unhappychoice
 * 
 */
public class Config {

   public static ArrayList<String> GROUP_NAME;
   public static ArrayList<Integer> PROMOTE_GOOD;

   private Config() {

   }

   public static Config setConfig(JavaPlugin plugin) {

      instance.plugin = plugin;
      instance.config = plugin.getConfig();

      GROUP_NAME = new ArrayList<String>();
      GROUP_NAME.add("Member");
      GROUP_NAME.add("Mmember");

      PROMOTE_GOOD = new ArrayList<Integer>();
      PROMOTE_GOOD.add(5);
      PROMOTE_GOOD.add(20);

      return instance;
   }

   private JavaPlugin plugin;
   private FileConfiguration config;
   private static Config instance = new Config();

}