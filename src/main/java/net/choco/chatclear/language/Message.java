package net.choco.chatclear.language;

import lombok.Getter;
import lombok.Setter;
import net.choco.chatclear.Main;
import org.bukkit.ChatColor;

import java.util.Arrays;

public enum Message {

    NO_PERMISSION("messages.no_permission"),
    CHAT_CLEAR("messages.chat_clear");

    @Getter @Setter
    private String message;
    @Getter
    private String path;

    Message(String path) {
        this.path = path;
        this.message = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getFileManager().getConfig("translates.yml").get().getString(this.path));
    }

    public String getMessageWithPrefix() {
        return Main.getInstance().getSettings().getPrefix() + this.message;
    }

    public static void reloadMessages() {
        Arrays.stream(values()).forEach(m -> {
            m.setMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getFileManager().getConfig("translates.yml").get().getString(m.getPath())));
        });
    }
}
