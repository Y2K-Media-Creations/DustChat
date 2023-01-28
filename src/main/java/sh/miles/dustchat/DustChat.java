package sh.miles.dustchat;

import net.md_5.bungee.api.ChatColor;
import sh.miles.dustchat.color.ColorUtility;

public final class DustChat {

    private DustChat() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String colorize(String message) {
        if (message == null) {
            return null;
        }
        return ColorUtility.style(message);
    }

    public static String stripColor(String message) {
        if (message == null) {
            return null;
        }
        return ChatColor.stripColor(message);
    }

}
