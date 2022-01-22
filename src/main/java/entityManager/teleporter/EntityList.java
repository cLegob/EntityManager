package entityManager.teleporter;

import entityManager.Chat;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class EntityList {
    private final Set<Entity> list = new HashSet<>();

    /**
     * add an Entity into the set
     * @param entity - the non-player entity to add
     */
    public void add(Player p, Entity entity) {
        if (entity instanceof Player) {
            return;
        }

        if (list.add(entity)) {
            p.sendMessage("Added " + Chat.red + entity.getName() + Chat.reset + " to your list.");
            return;
        }

        p.sendMessage(Chat.red + entity.getName() + " is already in your list.");
    }

    public void teleport(Location location) {
        list.forEach(entity -> entity.teleport(location));
    }

    public boolean isEmpty() { return list.isEmpty(); }

    public void clear() {
        list.clear();
    }

}
