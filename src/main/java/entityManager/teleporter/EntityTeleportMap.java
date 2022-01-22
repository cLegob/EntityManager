package entityManager.teleporter;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityTeleportMap {
    private final Map<UUID, EntityList> map = new HashMap<>();

    public EntityList getPlayerList(Player p) {
        map.putIfAbsent(p.getUniqueId(), new EntityList());
        return map.get(p.getUniqueId());
    }

}
