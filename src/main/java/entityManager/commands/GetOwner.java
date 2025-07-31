package entityManager.commands;

import entityManager.EntityManager;
import entityManager.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.util.RayTraceResult;

public class GetOwner extends SubCommand {
    public GetOwner(EntityManager entityManager) {
        super(entityManager, "owner");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;
        Entity lookingAt = getEntityLookingAt(sender); // see if sender is looking at an entity
        if (lookingAt == null) {
            String msg = "No entity found in your crosshairs, move closer or try again.";
            sender.sendMessage(msg);
            return;
        }
        Player entityOwner = (Player) getEntityOwner(lookingAt);
        if (entityOwner == null) {
            String msg = lookingAt.getName() + " does not have an owner.";
            return;
        }
        String msg = lookingAt.getName() + " is owned by " + entityOwner.getName();
        sender.sendMessage(msg);
    }

    @Override
    public String description() {
        return "Returns the owner of the entity being looked at";
    }

    @Override
    public String permission() {
        return "entitymanager.owner";
    }

    private Entity getEntityLookingAt(CommandSender sender) {
        Player player =  (Player) sender;
        RayTraceResult result = player.rayTraceBlocks(10); // scan 10 blocks in front of the player
        if (result != null) {
            return result.getHitEntity();
        }
        return null;
    }

    private AnimalTamer getEntityOwner(Entity entity) {
        boolean isTamed = false;
        if (entity instanceof Tameable tameable) {
            isTamed = tameable.isTamed();
            if (isTamed) return tameable.getOwner();
        }
        return null;
    }
}
