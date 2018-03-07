package fr.poudlardrp.citizens.api.util;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Colorizer {
    private static Pattern COLOR_MATCHER;
    private static String GROUP = ChatColor.COLOR_CHAR + "$1";

    static {
        String colors = "";
        for (ChatColor color : ChatColor.values())
            colors += color.getChar();
        COLOR_MATCHER = Pattern.compile("\\<([COLORS])\\>".replace("COLORS", colors), Pattern.CASE_INSENSITIVE);
    }

    public static String parseColors(String parsed) {
        Matcher matcher = COLOR_MATCHER.matcher(ChatColor.translateAlternateColorCodes('&', parsed));
        return matcher.replaceAll(GROUP);
    }

    public static String stripColors(String parsed) {
        Matcher matcher = COLOR_MATCHER.matcher(parsed);
        return matcher.replaceAll("");
    }
}
