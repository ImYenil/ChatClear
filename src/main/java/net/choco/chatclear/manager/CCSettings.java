package net.choco.chatclear.manager;

import lombok.Getter;
import net.choco.chatclear.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@Getter
public class CCSettings {

    private  String prefix = "§8[§eChatClear.getInstance()§8]§r ";

    private void setPrefix(String prefix) {
        if (prefix != null) {
            this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "§cVariable prefix could not be loaded! Setting it to default (" + this.prefix + ")");
        }
    }

    public void loadSettings() {
        setPrefix(Main.getInstance().getFileManager().getConfig("config.yml").get().getString("prefix"));
    }
}
