package socialbuild.Utility;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;

/**
 * Locate Message Class
 * 
 * @author unhappychoice
 * 
 */
public class Messages {

   public static String ERROR_NO_PERM;
   public static String ERROR_SELF_GOOD;
   public static String ERROR_BREAK;

   public static String PLAYER_GIVE;
   public static String PLAYER_GIVEN;
   public static String PLAYER_CANCEL;
   public static String PLAYER_CANCELED;
   public static String PLAYER_PROMOTED;
   public static String PLAYER_DEMOTED;

   public static String SIGN_CREATE;

   public static void loadMessages() {

      ResourceBundle bundle = null;

      try {
         bundle = ResourceBundle.getBundle("messages");
      } catch (MissingResourceException e) {
         e.getStackTrace();
         return;
      }

      try {

         ERROR_NO_PERM = ChatColor.RED + bundle.getString("error_no_perm");
         ERROR_SELF_GOOD = ChatColor.RED + bundle.getString("error_self_good");
         ERROR_BREAK = ChatColor.RED + bundle.getString("error_break");

         PLAYER_GIVE = ChatColor.DARK_AQUA + bundle.getString("player_give");
         PLAYER_GIVEN = ChatColor.DARK_AQUA + bundle.getString("player_given");
         PLAYER_CANCEL = ChatColor.DARK_AQUA + bundle.getString("player_cancel");
         PLAYER_CANCELED = ChatColor.DARK_AQUA + bundle.getString("player_canceled");
         PLAYER_PROMOTED = ChatColor.YELLOW + bundle.getString("player_promoted");
         PLAYER_DEMOTED = ChatColor.YELLOW + bundle.getString("player_demoted");

         SIGN_CREATE = ChatColor.DARK_AQUA + bundle.getString("sign_create");

      } catch (MissingResourceException e) {
         e.getStackTrace();
      }
   }
}
