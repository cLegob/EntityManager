package entityManager;

import com.google.gson.Gson;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * represents each entity within an EntitySelection
 */
public class EntityManagerEntity {
    private final Entity entity;

    public EntityManagerEntity(Entity entity) {
        this.entity = entity;
    }

    public void remove() {
        if (!isProtected()) {
            entity.remove();
            EntityManager.getPlugin(EntityManager.class).getLogger().info(entity.getName() + " at " + location());
        }
    }

    public boolean isProtected() { return EntityManager.getPlugin(EntityManager.class).getProtected().contains(entity.getType()); }

    public String location() { return Utils.locationToString(entity.getLocation()); }

    public EntityType type() { return entity.getType(); }

    public void respawn() {
        entity.getWorld().spawnEntity(entity.getLocation(), entity.getType());
    }
}
