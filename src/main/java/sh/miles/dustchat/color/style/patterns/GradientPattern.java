package sh.miles.dustchat.color.style.patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sh.miles.dustchat.color.ColorUtility;

public class GradientPattern implements ChatStylePattern {

    @SuppressWarnings("java:S103")
    public static final Pattern PATTERN = Pattern.compile(
            "<gradient:([0-9A-Fa-f]{6}):([0-9A-Fa-f]{6})>(.*)</gradient>|<range:([0-9A-Fa-f]{6}):([0-9A-Fa-f]{6})>(.*)</range>");

    @Override
    public String process(String message) {
        final Matcher matcher = PATTERN.matcher(message);
        while (matcher.find()) {
            final String color1 = matcher.group(1) != null ? matcher.group(1) : matcher.group(3);
            final String color2 = matcher.group(2) != null ? matcher.group(2) : matcher.group(4);
            final String text = matcher.group(3) != null ? matcher.group(3) : matcher.group(5);

            final net.md_5.bungee.api.ChatColor[] gradient = ColorUtility.gradient(color1, color2, text.length());

            message = message.replace(matcher.group(), ColorUtility.applyColorArray(text, gradient));
        }

        return message;
    }

}
