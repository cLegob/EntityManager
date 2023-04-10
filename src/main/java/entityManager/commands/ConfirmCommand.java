package entityManager.commands;

import entityManager.Chat;
import entityManager.EntityManager;
import entityManager.EntitySelection;
import entityManager.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ConfirmCommand extends SubCommand {

    public ConfirmCommand(EntityManager plugin) {
        super(plugin, "confirm");
    }

    @Override
    public String description() {
        return "confirm a removal selection";
    }

    @Override
    public String permission() {
        return "entitymanager.remove";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Chat.red + "That's a player only command.");
            return;
        }

        EntitySelection selection = plugin.getSelectionMap().getPlayerSelection(player);
        if (selection == null) {
            sender.sendMessage(Chat.red + "You don't have anything to confirm.");
            return;
        }

        selection.removeEntitites();
    }
}
