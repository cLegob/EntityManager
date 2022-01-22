package entityManager.commands;

import entityManager.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RemoveEntities extends SubCommand {

    public RemoveEntities(EntityManager plugin) {
        super(plugin, "remove");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Chat.red + "Sorry, that only works as a player.");
            return;
        }

        if (!player.hasPermission("entitymanager.use")) {
            sender.sendMessage(EntityManager.NO_PERMISSION);
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("Usage: /entity remove <radius> <excluded>");
            return;
        }

        int radius = Utils.parseNumber(sender, args[1], Chat.red + "Error: That's not a number");

        if (radius == -1) return;

        int max = 50;
        if (radius > max || radius < 1) {
            sender.sendMessage("You can only remove entities within a radius of " + max + ".");
            return;
        }

        EntitySelection selected;
        List<Entity> nearby = player.getNearbyEntities(radius, 50, radius);

        EntityType toInclude = null;

        if (args.length  >= 3) {
            toInclude = EntityType.valueOf(args[2].toUpperCase()); //todo handle null
        }

        EntityType finalToInclude = toInclude; // && en.getType() != finalToInclude
        nearby.removeIf(en -> plugin.getProtected().contains(en.getType()));

        selected = new EntitySelection(player, nearby, toInclude);

        String toDelete = selected.total()  > 1 ? "You are about to remove " + Chat.red + "1" + Chat.reset + " entity:"
                : "You are about to remove " + Chat.red + selected.total() + Chat.reset + " entities.";

        sender.sendMessage(toDelete);
        sender.sendMessage(selected.toString());
        sender.sendMessage("Type" + Chat.red + " /em confirm" + Chat.reset + " to confirm this removal.");
    }

    @Override
    public String description() {
        return "Remove nearby entities";
    }

    @Override
    public String permission() {
        return "entitymanager.remove";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("entitymanager.remove")) return Collections.emptyList();

        if (args.length == 1) {
            return Arrays.asList("5", "10", "15", "20");
        }

        if (args.length > 2) {
            var results = new ArrayList<String>();
            for (EntityType m : EntityType.values()) results.add(m.name());
            return results;
        }
        return Collections.emptyList();
    }

}



















