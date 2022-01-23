package entityManager;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Map;

public class Utils {

    public static String locationToString(Location l) {
        return "x" + l.getBlockX() + ", y" + l.getBlockY() + ", z" + l.getBlockZ();
    }

    public static String formatEntity(String name) {
        return StringUtils.capitalize(name.toLowerCase().replace("_", " "));
    }

    public static String worldToString(World w) {
    	String name = w.getName();
        if (name.equals("world")) return "overworld";
        if (name.equals("world_nether")) return "nether";
        if (name.equals("world_the_end")) return "end";
        return "null";
    }

    public static int parseNumber(CommandSender sender, String number, String error) {
        int result = -1;
        try {
            result = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            EntityManager.getPlugin(EntityManager.class).msg(sender, error);
        }
        return result;
    }

    public static void count(EntityType e, Map<EntityType, Integer> map) {
        map.putIfAbsent(e, 0);
        map.put(e, map.get(e) + 1);
    }

}
