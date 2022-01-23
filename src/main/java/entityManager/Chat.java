package entityManager;


import org.bukkit.ChatColor;
public interface Chat  {
    String aqua = ChatColor.AQUA + "";
    String black = ChatColor.BLACK + "";
    String blue = ChatColor.BLUE + "";
    String red = ChatColor.RED + "";
    String yellow = ChatColor.YELLOW + "";
    String gold = ChatColor.GOLD + "";
    String gray = ChatColor.GRAY + "";
    String green = ChatColor.GREEN + "";
    String white = ChatColor.WHITE + "";

    String darkAqua = ChatColor.DARK_AQUA + "";
    String darkBlue = ChatColor.DARK_BLUE + "";
    String darkGray = ChatColor.DARK_GRAY + "";
    String darkGreen = ChatColor.DARK_GREEN + "";
    String darkPurple = ChatColor.DARK_PURPLE + "";
    String darkRed = ChatColor.DARK_RED + "";

    String italic = ChatColor.ITALIC + "";
    String pink = ChatColor.LIGHT_PURPLE + "";
    String reset = ChatColor.RESET + "";
    String strike = ChatColor.STRIKETHROUGH + "";
    String PLUGIN_TAG = Chat.red + "[EntityManager] " + Chat.reset;

}
