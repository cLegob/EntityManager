package entityManager.EntityAI;

import entityManager.Chat;
import entityManager.EntityManager;
import entityManager.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.UUID;

public class EntityAICommand extends SubCommand implements Listener {
    private final Map<UUID, Boolean> map;

    public EntityAICommand(EntityManager entityManager) {
        super(entityManager, "toggleai");
        map = plugin.getAiMap();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Player only command.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("Usage: /entity toggleai");
            return;
        }

        UUID pUUID = p.getUniqueId();

        map.putIfAbsent(pUUID, false);

        if (map.get(pUUID)) {
            map.put(pUUID, false);
            plugin.msg(p, "AI toggling is now " + Chat.red + "OFF" + Chat.reset + ".");
            return;
        }

        map.put(pUUID, true);
        plugin.msg(p, "AI toggling is now " + Chat.green + "ON" + Chat.reset
                + ". Left click an entity to toggle its AI " + Chat.green + "ON" + Chat.reset + ". Right click an entity to toggle its AI " + Chat.red + "OFF" + Chat.reset + ".");
    }

    @Override
    public String description() {
        return "Toggle AI for entities";
    }

    @Override
    public String permission() {
        return "entitymanager.toggleai";
    }

}
