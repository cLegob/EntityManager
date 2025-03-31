package entityManager.EntityAI;

import entityManager.Chat;
import entityManager.EntityManager;
import entityManager.teleporter.EntityList;
import entityManager.teleporter.EntityTeleportMap;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.UUID;

public class EntityAIListener implements Listener {
    private final EntityManager plugin;

    public EntityAIListener(EntityManager p) {
        p.getServer().getPluginManager().registerEvents(this, p);
        plugin = p;
    }

    @EventHandler
    public void onRightClickEntity(PlayerInteractAtEntityEvent e) {
        toggleAI(e.getPlayer(), e.getRightClicked(), false); // Disable AI
        e.setCancelled(true);
    }

    @EventHandler
    public void onLeftClickEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player player) {
            toggleAI(player, e.getEntity(), true); // Enable AI
            e.setCancelled(true);
        }
    }

    private void toggleAI(Player p, Entity entity, boolean enableAI) {
        if (!p.hasPermission("entitymanager.toggleai")) return;
        if (!plugin.toggleAiEnabled(p)) return;

        if (entity instanceof LivingEntity livingEntity) {
            PersistentDataContainer data = livingEntity.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "NoAI");

            if (enableAI) {
                // Enable AI: Remove the NoAI tag and enable AI
                data.remove(key);
                livingEntity.setAI(true);
                p.sendMessage(Chat.darkGreen + "AI Enabled for " + entity.getName());
            } else {
                // Disable AI: Set the NoAI tag and disable AI
                data.set(key, PersistentDataType.BYTE, (byte) 1);
                livingEntity.setAI(false);
                p.sendMessage(Chat.darkRed + "AI Disabled for " + entity.getName());
            }
        }
    }
}
