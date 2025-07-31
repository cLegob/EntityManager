package entityManager;

import entityManager.commands.*;
import entityManager.commands.teleportCommands.EntityTeleport;
import entityManager.commands.teleportCommands.EntityTeleportListClear;
import entityManager.commands.teleportCommands.WandToggleCommand;
import entityManager.teleporter.EntityTeleportMap;
import entityManager.teleporter.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class EntityManager extends JavaPlugin {
    public static final String VERSION = "1.5";
    public static final String NO_PERMISSION = Chat.red + "You do not have permission to do that.";
    public static ItemStack SELECTION_WAND = new ItemStack(Material.ARROW);
    public static ItemStack TELEPORT_WAND = new ItemStack(Material.BLAZE_ROD);
    public ArrayList<SubCommand> commands = new ArrayList<>();

    private final EntityTeleportMap entityListMap = new EntityTeleportMap();
    private final SelectionMap selectionMap = new SelectionMap();
    private final Map<UUID, Boolean> wandMap = new HashMap<>();

    public EntityTeleportMap getEntityListMap() {
        return entityListMap;
    }

    public SelectionMap getSelectionMap() {
        return selectionMap;
    }

    public Map<UUID, Boolean> getWandMap() {
        return wandMap;
    }

    public void onEnable() {
        Bukkit.getLogger().info("Enabling EntityManager v" + VERSION + "...");

        commands.add(new LagReport(this));
        commands.add(new NearbyEntites(this));
        commands.add(new RemoveEntities(this));
        commands.add(new SearchEntity(this));
        commands.add(new WandToggleCommand(this));
        commands.add(new EntityTeleportListClear(this));
        commands.add(new EntityTeleport(this));
        commands.add(new ConfirmCommand(this));
        commands.add(new UndoRemovalCommand(this));
        commands.add(new GetOwner(this));

        getPlugin(EntityManager.class).saveDefaultConfig();

        new Listeners(this);
    }

    public void onDisable() {
        Bukkit.getLogger().info("Disabling EntityManager v" + VERSION + "...");
    }

    public boolean wandEnabled(Player p) {
        wandMap.putIfAbsent(p.getUniqueId(), false);
        return wandMap.get(p.getUniqueId());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("entitymanager.use")) {
            sender.sendMessage(Chat.PLUGIN_TAG + "Version " + VERSION);
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(
                    Chat.gray + " --------" + Chat.gold + " Entity Manager " + Chat.gray + "--------");
            for (SubCommand command : commands)
                sender.sendMessage(
                        Chat.red + "/" + command.getName() + Chat.reset + " - " + command.description());
            return true;
        }

        for (SubCommand command : commands) {
            if (command.getName().equals(args[0])) {
                command.execute(sender, args);
                break;
            }
        }
        return false;
    }

    public void msg(CommandSender aSender, String aMessage) {
        aSender.sendMessage(Chat.PLUGIN_TAG + aMessage);
    }

    public List<EntityType> getProtected() {
        var theList = new ArrayList<EntityType>();
        getConfig().getStringList("protected").forEach(name -> theList.add(EntityType.valueOf(name)));
        return theList;
    }

    public static List<String> getEntityTypes() {
        ArrayList<String> results = new ArrayList<String>();
        for (EntityType m : EntityType.values())
            results.add(m.name());
        return results;
    }

    private List<String> getResults(String[] args, List<String> toSearch) {
        List<String> results = new ArrayList<>();
        for (String string : toSearch) {
            if (string.toLowerCase().startsWith(args[1].toLowerCase()))
                results.add(string);
        }
        return results;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if (!commandSender.hasPermission("entitymanager.use"))
            return List.of();
        List<String> results = new ArrayList<>();
        List<String> aList = new ArrayList<>();
        commands.forEach(cmd -> aList.add(cmd.getName()));

        if (args.length == 1) {
            for (String a : aList) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    results.add(a);
            }
            return results;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("search")) {
                return getResults(args, getEntityTypes());
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("remove")) {
                return getResults(args, getConfig().getStringList("protected"));
            }
        }
        return List.of();
    }

}
