package entityManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public abstract class SubCommand implements TabCompleter {
	protected EntityManager plugin;
	private final String name;

	public SubCommand(EntityManager plugin, String name) {
		this.name = name;
		this.plugin = plugin;
	}

	public String getName() {
		return name;
	}
	
	public abstract String description();

	public abstract String permission();

	public abstract void execute(CommandSender sender, String[] args);

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return Collections.emptyList();
	}

}