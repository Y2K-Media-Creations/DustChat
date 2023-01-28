package sh.miles.dustchat.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import sh.miles.dustchat.color.style.ModifierType;
import sh.miles.dustchat.color.style.patterns.ChatStylePattern;
import sh.miles.dustchat.color.style.patterns.GradientPattern;
import sh.miles.dustchat.color.style.patterns.HexColorPattern;
import sh.miles.dustchat.color.style.patterns.ModifierPattern;
import sh.miles.dustchat.color.style.patterns.WordColorPattern;

public final class ColorUtility {

    private static final ChatStylePattern[] COLOR_PATTERNS = new ChatStylePattern[] {
            new HexColorPattern(),
            new GradientPattern(),
            new WordColorPattern(),
            new ModifierPattern(),
    };

    private ColorUtility() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String bukkitColor(@NonNull final String message) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }

    public static net.md_5.bungee.api.ChatColor getColorFromHex(@NonNull final String color) {
        return net.md_5.bungee.api.ChatColor.of(new Color(Integer.parseInt(color, 16)));
    }

    public static String style(@NonNull final String message) {
        String processedMessage = message;
        for (final ChatStylePattern pattern : COLOR_PATTERNS) {
            processedMessage = pattern.process(processedMessage);
        }
        return processedMessage;
    }

    public static List<String> style(@NonNull final List<String> messages) {
        final List<String> processedMessages = new ArrayList<>();
        for (final String message : messages) {
            processedMessages.add(style(message));
        }
        return processedMessages;
    }

    @SuppressWarnings("java:S864")
    public static net.md_5.bungee.api.ChatColor[] gradient(String color0, String color1, int size) {
        net.md_5.bungee.api.ChatColor[] colors = new net.md_5.bungee.api.ChatColor[size];

        Color c0 = getColorFromHex(color0).getColor();
        Color c1 = getColorFromHex(color1).getColor();

        int r = Math.abs(c0.getRed() - c1.getRed()) / (size - 1);
        int g = Math.abs(c0.getGreen() - c1.getGreen()) / (size - 1);
        int b = Math.abs(c0.getBlue() - c1.getBlue()) / (size - 1);

        for (int i = 0; i < size; i++) {

            int red = c0.getRed() < c1.getRed() ? c0.getRed() + (r * i) : c0.getRed() - (r * i);
            int green = c0.getGreen() < c1.getGreen() ? c0.getGreen() + (g * i) : c0.getGreen() - (g * i);
            int blue = c0.getBlue() < c1.getBlue() ? c0.getBlue() + (b * i) : c0.getBlue() - (b * i);

            colors[i] = net.md_5.bungee.api.ChatColor.of(new Color(red, green, blue));
        }

        return colors;
    }

    @SuppressWarnings("all")
    public static String applyColorArray(String message, net.md_5.bungee.api.ChatColor[] colors) {
        StringBuilder builder = new StringBuilder();
        final List<ModifierType> modifiers = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            final char c = message.charAt(i);

            // Checks for already parsed color codes
            if (c == '&' || c == '§') {
                // pushes the index forward to skip the color code that follows
                i++;
                continue;
            }

            // Checks for unparsed color codes
            final int endIndex = message.indexOf('>', i);
            if (c == '<' && endIndex != -1) {
                final String format = message.substring(i, endIndex + 1);
                i = endIndex;
                // Special case for modifiers
                if (format.contains("mod")) {
                    modifiers.add(ModifierType.fromString(format.split(":")[1].replace(">", "")));
                    continue;
                } else {
                    final int nextFormatIndex = message.indexOf('<', i);
                    final int endOfFormatIndex = nextFormatIndex == -1 ? message.length() : nextFormatIndex;
                    final String text = message.substring(i + 1, endOfFormatIndex);
                    i = endOfFormatIndex - 1;
                    builder.append(format).append(text);
                    continue;
                }
            }
            builder.append(colors[i]);
            for (ModifierType modifier : modifiers) {
                builder.append(modifier.getModifier());
            }
            builder.append(c);
        }
        return style(builder.toString());
    }

    /*
     * public static String applyModifier(String message, ModifierType modifier) {
     * StringBuilder builder = new StringBuilder();
     * for (int i = 0; i < message.length(); i++) {
     * final char c = message.charAt(i);
     * builder.append(modifier.getModifier()).append(c);
     * }
     * return builder.toString();
     * }
     */

    // write a method that takes a string and a modifier and applies the modifier to
    // the string
    // this method should only apply to modifiers to the head of a color code
    // for example, if the string is "&cHello, &bWorld!" and the modifier is
    // ModifierType.BOLD, the method should return "&c&lHello, &b&lWorld!"
    public static String applyModifier(String message, ModifierType modifier) {
        StringBuilder sb = new StringBuilder();
        boolean colorCodeFound = false;
        boolean firstChar = true;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == '§') {
                colorCodeFound = true;
                sb.append(c);
                i++;
                sb.append(message.charAt(i));
                sb.append(modifier.getModifier().toString());
            } else if (colorCodeFound) {
                colorCodeFound = false;
                sb.append(c);
            } else {
                if (firstChar) {
                    sb.append(modifier.getModifier().toString());
                    firstChar = false;
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String removeRepeatModifiers(String message) {
        StringBuilder sb = new StringBuilder();
        char lastColorCode = ' ';
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == '§') {
                i++;
                char next = message.charAt(i);
                if (next != lastColorCode) {
                    sb.append(c);
                    sb.append(next);
                    lastColorCode = next;
                }
            } else {
                sb.append(c);
                lastColorCode = ' ';
            }
        }
        return sb.toString();
    }
}
