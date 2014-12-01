package socialbuild.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by yueki on 2014/12/01.
 */
public class ShowSelfCommand extends SBCommand{

    public ShowSelfCommand(CommandSender sender, String[] args) {
        super(sender, args);
    }

    @Override
    public void execute() {
        _sql.CaliculatePlayerCount(name());
        sendMesage();
    }

    private String name() {
        return _sender.getName();
    }

    private int count() {
        return _sql.getPlayerCount(name());
    }

    private void sendMesage() {
        _sender.sendMessage(ChatColor.DARK_AQUA + "you have " + count() + " good !!");
    }
}
