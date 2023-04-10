package entityManager.teleporter;

import entityManager.EntityManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class Listeners implements Listener {
    private final EntityManager plugin;
    private final EntityTeleportMap map;

    public Listeners(EntityManager p) {
        p.getServer().getPluginManager().registerEvents(this, p);
        plugin = p;
        map = plugin.getEntityListMap();
    }

    private final ItemStack selectionWand = new ItemStack(Material.ARROW);
    private final ItemStack teleportWand = new ItemStack(Material.BLAZE_ROD);

    /*
    Represents an event that is called when a player right clicks an entity that also contains the location where the entity was clicked.
     */
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("entitymanager.teleport")) return;
        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (!plugin.wandEnabled(p)) return;

        ItemStack inHand = p.getInventory().getItemInMainHand();
        if (inHand.equals(selectionWand)) {
            EntityList theList = map.getPlayerList(p);
            Entity theEntity = e.getRightClicked();

            theList.add(p, theEntity);
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!plugin.wandEnabled(p)) {
            return;
        }

        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        ItemStack inHand = p.getInventory().getItemInMainHand();
        if (inHand.equals(teleportWand)) {
            Location location = e.getPlayer().getLocation();
            TeleportUtils.teleport(p, location, map);
        }
    }

}
