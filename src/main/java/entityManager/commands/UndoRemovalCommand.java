package entityManager.commands;

import entityManager.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UndoRemovalCommand extends SubCommand {

    public UndoRemovalCommand(EntityManager plugin) {
        super(plugin, "undo");
    }

    @Override
    public String description() {
        return "undo a removal";
    }

    @Override
    public String permission() {
        return "entitymanager.undo";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            plugin.msg(sender, "That's a player only command.");
            return;
        }

        EntitySelection selection = plugin.getSelectionMap().getPlayerSelection(player);

        if (selection == null) {
            plugin.msg(player, "You don't have anything to undo.");
            return;
        }

        plugin.getSelectionMap().getPlayerSelection(player).undoRemoval();
    }
}
