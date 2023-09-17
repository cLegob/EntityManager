package entityManager.commands.teleportCommands;

import entityManager.Chat;
import entityManager.EntityManager;
import entityManager.SubCommand;
import entityManager.teleporter.EntityTeleportMap;
import entityManager.teleporter.TeleportUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class EntityTeleport extends SubCommand implements Listener {
    private final EntityTeleportMap theMap;

    public EntityTeleport(EntityManager p) {
        super(p, "teleport");
        theMap = p.getEntityListMap();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Chat.red + "That's a player only command.");
            return;
        }

        if (theMap.getPlayerList(player).isEmpty()) {
            sender.sendMessage(Chat.red + "Your entity teleport list is empty. " + Chat.reset
                    + "Use a(n) " + EntityManager.SELECTION_WAND.getType() + " to select new entities to teleport.");
            return;
        }

        TeleportUtils.teleport(player, player.getLocation(), theMap);
        player.sendMessage("Teleported. Use " + Chat.gray + "/em clearteleportlist " + Chat.reset + "to clear your list.");
    }

    @Override
    public String description() {
        return "Teleport an entity";
    }

    @Override
    public String permission() {
        return "entitymanager.teleport";
    }
}
