package entityManager.commands;

import entityManager.Chat;
import entityManager.EntityManager;
import entityManager.SubCommand;
import entityManager.Utils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RemoveEntities extends SubCommand {
    private boolean includeVillagers = false;
    private int max = 50;
    private int radius = 10;

    public RemoveEntities(EntityManager plugin) {
        super(plugin, "remove");
    }

    private List<Entity> deleted = new ArrayList<Entity>();
    private Map<EntityType, Integer> map = new HashMap<EntityType, Integer>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Sorry, that only works as a player.");
            return;
        }

        if (!player.hasPermission("entitymanager.use")) {
            sender.sendMessage(plugin.noPermission);
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("Usage: /entity remove <radius>");
            return;
        }

        int radius = Utils.parseNumber(sender, args[1], Chat.red + "Error: That's not a number");

        if (args.length >= 3) {
            if (args[2].equals("includevillagers")) includeVillagers = true;
        }

        if (radius > max || radius < 1) {
            sender.sendMessage("You can only remove entities within a radius of " + max + ".");
            return;
        }

        List<Entity> nearby = player.getNearbyEntities(radius, 200, radius);

        nearby.forEach(entity -> {
            if (!(entity instanceof Player)) {
                clear(sender, entity);
            }
        });

        String s = includeVillagers ? ", including villagers." : ".";

        sender.sendMessage("Removed " + Chat.red + deleted.size() + Chat.reset + " entities within a radius of "
                + radius + s);

        String mark = Chat.darkGray + "- " + Chat.gray;
        map.entrySet().forEach(entry -> sender.sendMessage(mark + s(entry)
                + ": " + Chat.reset + entry.getValue()));
        return;
    }


    private void clear(CommandSender sender, Entity e) {
        if (includeVillagers) {
            if (e.getType() == EntityType.VILLAGER) {
                e.remove();
                deleted.add(e);
                Utils.count(e.getType(), map);
            }
        }
        if (!isProtected(e)) {
            e.remove();
            deleted.add(e);
            Utils.count(e.getType(), map);
        }
    }

    private String s(Entry<EntityType, Integer> entry) {
        return WordUtils.capitalize(entry.getKey().toString().toLowerCase().replace("_", " "));
    }

    private boolean isProtected(Entity e) {
        ArrayList<EntityType> protect = new ArrayList<EntityType>();
        protect.add(EntityType.VILLAGER);
        protect.add(EntityType.STRIDER);
        protect.add(EntityType.ARMOR_STAND);
        protect.add(EntityType.ITEM_FRAME);
        protect.add(EntityType.WOLF);
        protect.add(EntityType.BOAT);
        protect.add(EntityType.HORSE);
        protect.add(EntityType.ENDER_CRYSTAL);
        protect.add(EntityType.TRADER_LLAMA);
        protect.add(EntityType.WANDERING_TRADER);
        protect.add(EntityType.CAT);
        protect.add(EntityType.PAINTING);
        protect.add(EntityType.LEASH_HITCH);
        protect.add(EntityType.TROPICAL_FISH);
        protect.add(EntityType.MINECART);
        protect.add(EntityType.MINECART_CHEST);
        protect.add(EntityType.MINECART_HOPPER);
        protect.add(EntityType.MINECART_FURNACE);
        protect.add(EntityType.ELDER_GUARDIAN);
        protect.add(EntityType.GUARDIAN);
        protect.add(EntityType.DOLPHIN);
        protect.add(EntityType.LLAMA);
        return protect.contains(e.getType());
    }

    @Override
    public String description() {
        return "Remove nearby entities";
    }

}
