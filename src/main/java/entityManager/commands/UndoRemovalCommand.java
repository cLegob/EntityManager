package entityManager.commands;

import entityManager.Chat;
import entityManager.EntityManager;
import entityManager.EntitySelection;
import entityManager.SubCommand;
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
            sender.sendMessage(Chat.red + "That's a player only command.");
            return;
        }

        EntitySelection selection = plugin.getSelectionMap().getPlayerSelection(player);

        if (selection == null) {
            sender.sendMessage(Chat.red + "You don't have anything to undo.");
            return;
        }

        plugin.getSelectionMap().getPlayerSelection(player).undoRemoval();
    }
}
