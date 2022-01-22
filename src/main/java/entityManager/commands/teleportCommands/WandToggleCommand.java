package entityManager.commands.teleportCommands;

import entityManager.Chat;
import entityManager.EntityManager;
import entityManager.SubCommand;
import entityManager.Utils;
import entityManager.teleporter.EntityList;
import entityManager.teleporter.EntityTeleportMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.UUID;

public class WandToggleCommand extends SubCommand implements Listener {
    private Map<UUID, Boolean> map;

    public WandToggleCommand(EntityManager p) {
        super(p, "togglewand");
        map = p.getWandMap();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Chat.red + "That's a player only command.");
            return;
        }

        if (map.get(player.getUniqueId())) {
            map.put(player.getUniqueId(), false);
            Utils.message(player, "Toggled wand usage " + Chat.red + "OFF" + Chat.reset + ".");
            return;
        }

        map.put(player.getUniqueId(), true);
        Utils.message(player, "Toggled wand usage " + Chat.green + "ON" + Chat.reset + ".");
    }

    @Override
    public String description() {
        return "Toggle the wand";
    }

    @Override
    public String permission() {
        return "entitymanager.teleport";
    }
}
