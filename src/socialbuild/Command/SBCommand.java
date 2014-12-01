package socialbuild.Command;

import org.bukkit.command.CommandSender;
import socialbuild.Utility.SQLWrapper;

/**
 * Created by yueki on 2014/12/01.
 */
public abstract class SBCommand {

    public SBCommand(CommandSender sender, String[] args) {
        _sender = sender;
        _args = args;
        _sql = SQLWrapper.getInstance();
    }

    abstract public void execute();

    protected CommandSender _sender;
    protected String[] _args;
    protected SQLWrapper _sql;
}
