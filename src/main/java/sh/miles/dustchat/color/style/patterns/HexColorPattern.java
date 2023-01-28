package sh.miles.dustchat.color.style.patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sh.miles.dustchat.color.ColorUtility;

public class HexColorPattern implements ChatStylePattern {

    public static final Pattern PATTERN = Pattern
            .compile("<color:([0-9A-Fa-f]{6})>|#([0-9A-Fa-f]{6})|#(\\[[0-9A-Fa-f]{6}\\])");

    @Override
    public String process(String message) {
        final Matcher matcher = PATTERN.matcher(message);
        while (matcher.find()) {
            final String color;
            
            if (matcher.group(1) != null) {
                color = matcher.group(1);
            } else if (matcher.group(2) != null) {
                color = matcher.group(2);
            } else {
                color = matcher.group(3);
            }

            message = message.replace(matcher.group(), ColorUtility.getColorFromHex(color) + "");
        }

        return message;
    }

}
