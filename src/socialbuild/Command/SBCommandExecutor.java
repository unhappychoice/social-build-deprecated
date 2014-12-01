package socialbuild.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Command Execute Class
 * 
 * @author unhappychoice
 * 
 */
public class SBCommandExecutor implements CommandExecutor {

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (cannotHandleBySB(command)) {
         return false;
      } else {
         return executeCommand(sender, args);
      }
   }

   private boolean cannotHandleBySB(Command command) {
      return !command.getName().equalsIgnoreCase("sb");
   }

   private boolean executeCommand(CommandSender sender, String[] args) {
      try {
         new SBCommandFactory(sender, args).create().execute();
      } catch(Exception e) {
         e.printStackTrace();
         return false;
      }
      return true;
   }
}
