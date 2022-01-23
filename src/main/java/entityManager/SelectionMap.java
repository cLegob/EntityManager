package entityManager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectionMap {
    private final Map<UUID, EntitySelection> selections = new HashMap<>();

    public EntitySelection getPlayerSelection(Player player) {
        return selections.get(player.getUniqueId());
    }

    public void setSelection(Player player, EntitySelection theSelection) {
        selections.put(player.getUniqueId(), theSelection);
    }

    public void destroySelection(Player player) {
        selections.put(player.getUniqueId(), null);
    }
}
