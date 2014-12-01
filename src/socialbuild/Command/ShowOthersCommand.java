package socialbuild.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by yueki on 2014/12/01.
 */
public class ShowOthersCommand extends SBCommand {
    public ShowOthersCommand(CommandSender sender, String[] args) {
        super(sender, args);
    }

    @Override
    public void execute() {
        _sql.CaliculatePlayerCount(name());
        sendMessage();
    }

    private String name() {
        return _args[0];
    }

    private int count() {
        return _sql.getPlayerCount(name());
    }

    private void sendMessage() {
        _sender.sendMessage(ChatColor.DARK_AQUA + name() + " have " + count() + " good !!");
    }
}
