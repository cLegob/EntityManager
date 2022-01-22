package entityManager.commands;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import entityManager.Chat;
import entityManager.Utils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import entityManager.EntityManager;
import entityManager.SubCommand;

public class NearbyEntites extends SubCommand {
	private int max = 200;

	public NearbyEntites(EntityManager entityManager) {
		super(entityManager, "near");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ConsoleCommandSender) { return; }

		Player player = (Player) sender;

		int radius = 10;

		if (args.length == 2) radius = Utils.parseNumber(sender, args[1], Chat.red + "There was an error: Invalid number.");

		if (radius > max && radius > -1) {
			sender.sendMessage("You can only search for entities within a radius of " + max + ".");
			return;
		}

		List<Entity> nearby = player.getNearbyEntities(radius, 100, radius);
		var entityMap = new HashMap<EntityType, Integer>();

		nearby.forEach(entity -> {
			if (!(entity instanceof Player)) Utils.count(entity.getType(), entityMap);
		});

		String msg = nearby.size() == 1 ? "There is" + Chat.red + " 1 " + Chat.reset + "entity"
				: "There are " + Chat.red + nearby.size() + " entities";

		sender.sendMessage(msg + Chat.reset + " nearby within a radius of " + radius + ".");

		String mark = Chat.darkGray + "- " + Chat.gray;
		entityMap.entrySet().forEach(entry -> sender.sendMessage(mark + toString(entry)
				+ ": " + Chat.reset + entry.getValue()));
	}

	private static String toString(Entry<EntityType, Integer> entry) {
		return WordUtils.capitalize(entry.getKey().toString().toLowerCase().replace("_", " "));
	}
	
	@Override
	public String description() {
		return "Display nearby entities";
	}

	@Override
	public String permission() {
		return "entitymanager.nearby";
	}

}
