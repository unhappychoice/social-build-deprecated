package socialbuild.Command;

import org.bukkit.command.CommandSender;

/**
 * Created by yueki on 2014/12/01.
 */
public class TopCommand extends SBCommand{
    public TopCommand(CommandSender sender, String[] args) {
        super(sender, args);
    }

    @Override
    public void execute() {
        sendMessage(_sql.getRanking());
    }

    private void sendMessage(String[] messages) {
        for (String m : messages ) {
            _sender.sendMessage(m);
        }
    }
}
