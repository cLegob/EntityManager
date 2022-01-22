package entityManager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class EntitySelection {
    private final EntityManager em = EntityManager.getPlugin(EntityManager.class);
    private final Logger log = em.getLogger();
    private final Map<EntityType, Integer> countMap = new HashMap<>();
    private final List<EntityManagerEntity> entities = new ArrayList<>();
    private final Player player;
    private final EntityType included;

    public EntitySelection(Player player, List<Entity> selected, EntityType included) {
        this.player = player;
        this.included = included;
        selected.forEach(entity -> entities.add(new EntityManagerEntity(entity)));
        em.getSelectionMap().setSelection(player, this);
    }

    public Player getPlayer() {
        return player;
    }

    public void count() {
        entities.forEach(e -> {
            countMap.putIfAbsent(e.type(), 0);
            countMap.compute(e.type(), (key, value) -> value += 1);
        });
    }

    public int total() {
        int sum = 0;
        for (int val : countMap.values()) sum += val;
        return sum;
    }

    /**
     * re-spawn entities that were deleted (doesn't account for any NBT data at the moment)
     */
    public void undoRemoval() {
        log.info(player.getName() + " re-spawned the following entitie(s)");
        entities.forEach(EntityManagerEntity::respawn);
        Utils.message(player, "Successfully undone previous removal of " + Chat.red + total() + Chat.reset + " entities.");
    }

    /**
     * Delete the group of entities
     */
    public void removeEntitites() {
        log.info(player.getName() + " cleared the following entitie(s):");

        entities.forEach(EntityManagerEntity::remove);
        Utils.message(player, "Removed " + Chat.red + total() + Chat.reset + " entities.");
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        count();
        countMap.forEach((key, value) ->
                b.append(Chat.darkGray).append("- ").append(Chat.gray)
                .append(Utils.formatEntity(key.name())).append(": ")
                .append(Chat.reset).append(value).append("\n"));
        return b.toString();
    }

}
