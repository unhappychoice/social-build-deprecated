package socialbuild.Command;

import com.avaje.ebean.Update;
import org.bukkit.command.CommandSender;

/**
 * Created by yueki on 2014/12/01.
 */
public class UpdateCommand extends SBCommand{
    public UpdateCommand(CommandSender sender, String[] args) {
        super(sender, args);
    }

    @Override
    public void execute() {
        _sql.updateOwner(fromOwner(), toOwner());
        sendMessage();
    }

    private String fromOwner() {
        return _args[1];
    }

    private String toOwner() {
        return _args[2];
    }

    private void sendMessage() {
        _sender.sendMessage("Updated Owner!!");
    }
}
