package sh.miles.dustchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public final class DustChatTest extends BasicTest {

    @Test
    public void testColorizeNull() {
        assertNull(DustChat.colorize(null), "colorize(null) should return null");
    }

    @Test
    public void testStripColorNull() {
        assertNull(DustChat.stripColor(null), "stripColor(null) should return null");
    }

    @Test
    public void testColorize() {
        assertEquals("§c§lHello, §b§lWorld!", DustChat.colorize("<word:red><mod:bold>Hello, <word:aqua><mod:bold>World!"),
                "colorize() should colorize the message");

    }

    
}
