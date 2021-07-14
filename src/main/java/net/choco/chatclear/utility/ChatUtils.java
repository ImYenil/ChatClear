package net.choco.chatclear.utility;

import org.bukkit.*;

public class ChatUtils
{
    public static String getColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
