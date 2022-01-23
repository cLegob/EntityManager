package entityManager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Logger;

public class EntitySelection {
    private final EntityManager em = EntityManager.getPlugin(EntityManager.class);
    private final Logger log = em.getLogger();
    private Map<EntityType, Integer> countMap = new HashMap<>();
    private final List<EntityManagerEntity> entities = new ArrayList<>();
    private final Player player;
    private final int total;

    public EntitySelection(Player player, List<Entity> selected) {
        selected.removeIf(e -> e instanceof Player);
        em.getSelectionMap().setSelection(player, this);
        this.player = player;
        selected.forEach(entity -> entities.add(new EntityManagerEntity(entity)));

        /*
        calculate number of each entity type
         */
        entities.forEach(e -> {
            countMap.putIfAbsent(e.type(), 0);
            countMap.compute(e.type(), (key, value) -> value += 1);
        });

        total = total();
    }

    public Player getPlayer() {
        return player;
    }

    private int total() {
        int sum = 0;
        for (int val : countMap.values()) {
            sum += val;
        }
        return sum;
    }

    /**
     * Get the total number of entities in this selection
     *
     * @return total number of entities
     */
    public int getTotal() {
        return total;
    }

    /**
     * re-spawn entities that were deleted (doesn't account for any NBT data at the moment)
     */
    public void undoRemoval() {
        log.info(player.getName() + " re-spawned the following entitie(s)");
        entities.forEach(EntityManagerEntity::respawn);
        em.msg(player, "Successfully undone previous removal of " + Chat.red + total() + Chat.reset + " entities.");
        em.msg(player, Chat.gray + "Note: /em undo does not bring back NBT data. Coming soon!");
        em.getSelectionMap().destroySelection(player); // destroy this selection Object
    }

    /**
     * Delete the group of entities
     */
    public void removeEntitites() {
        log.info(player.getName() + " cleared the following entitie(s):");

        entities.forEach(EntityManagerEntity::remove);
        em.msg(player, "Removed " + Chat.red + total + Chat.reset + " entities.");
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        countMap.forEach((key, value) ->
                b.append(Chat.darkGray).append("- ").append(Chat.gray)
                        .append(Utils.formatEntity(key.name())).append(": ")
                        .append(Chat.reset).append(value).append("\n"));
        return b.toString();
    }

    /*
     * not my code, need to revisit this method
     */
    public void sortMap() {
        List<Map.Entry<EntityType, Integer>> list = new LinkedList<>(countMap.entrySet());
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        HashMap<EntityType, Integer> sorted = new LinkedHashMap<>();

        list.forEach(entry -> sorted.put(entry.getKey(), entry.getValue()));
        countMap = sorted;
    }

    // todo - do this better
    public void setMinimumResult(int minimum) {
        var toRemove = new ArrayList<>();
        countMap.forEach((aKey, anIntValue) -> {
            if (anIntValue < minimum) {
                toRemove.add(aKey);
            }
        });
        toRemove.forEach(entity -> countMap.remove(entity));
    }

}


