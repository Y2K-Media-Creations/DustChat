package sh.miles.dustchat.color.style;

import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;

public enum ModifierType {

    BOLD(ChatColor.BOLD),
    ITALIC(ChatColor.ITALIC),
    UNDERLINE(ChatColor.UNDERLINE),
    STRIKETHROUGH(ChatColor.STRIKETHROUGH),
    OBFUSCATED(ChatColor.MAGIC),
    RESET(ChatColor.RESET);

    private final ChatColor color;

    ModifierType(ChatColor color) {
        this.color = color;
    }

    public ChatColor getModifier() {
        return color;
    }

    public static ModifierType fromString(@NonNull final String modifierType) {
        for (ModifierType type : values()) {
            if (type.name().equalsIgnoreCase(modifierType)) {
                return type;
            }
        }
        return RESET;
    }

}
