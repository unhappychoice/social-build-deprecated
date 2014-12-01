package socialbuild.Command;

import org.bukkit.command.CommandSender;
import socialbuild.Utility.PermissionManager;

/**
 * Created by yueki on 2014/12/01.
 */
public class SBCommandFactory {

    /**
     * コンストラクタ
     * @param sender
     * @param args
     */
    public SBCommandFactory(CommandSender sender, String[] args) {
        _sender = sender;
        _args = args;
        _permission = PermissionManager.getInstance();
    }

    /**
     * ファクトリメソッド
     * @return
     */
    public SBCommand create() {
        if      (selfCommand())   { return new ShowSelfCommand(_sender, _args); }
        else if (updateCommand()) { return new UpdateCommand(_sender, _args); }
        else if (topCommand())    { return new TopCommand(_sender, _args); }
        else if (othersCommand()) { return new ShowOthersCommand(_sender, _args); }
        else                      { return null; }
    }

    private boolean selfCommand() {
        return _args.length == 0 && _permission.hasPermission(_sender, SELF_PERMISSION);
    }

    private boolean updateCommand() {
        return _args[0].equalsIgnoreCase("update") && _permission.hasPermission(_sender, UPDATE_PERMISSION);
    }

    private boolean topCommand() {
        return _args[0].equalsIgnoreCase("top") && _permission.hasPermission(_sender, TOP_PERMISSION);
    }

    private boolean othersCommand() {
        return _permission.hasPermission(_sender, OTHER_PERMISSION);
    }

    private static String SELF_PERMISSION = "sb.self";
    private static String UPDATE_PERMISSION = "sb.update";
    private static String TOP_PERMISSION = "sb.top";
    private static String OTHER_PERMISSION = "sb.other";
    CommandSender _sender;
    String[] _args;
    PermissionManager _permission;
}
