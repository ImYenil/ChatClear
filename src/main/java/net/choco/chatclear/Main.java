package net.choco.chatclear;

import lombok.Getter;
import net.choco.chatclear.commands.CCCommand;
import net.choco.chatclear.manager.CCSettings;
import net.choco.chatclear.manager.FileManager;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.*;
import org.bukkit.*;

import java.lang.reflect.Field;

public class Main extends JavaPlugin
{

    @Getter
    private static Main instance;

    @Getter
    private static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "ChatClear" + ChatColor.DARK_GRAY + "]" + ChatColor.RESET;

    @Getter
    private FileManager fileManager;

    @Getter
    private CCSettings settings;
    
    public void onEnable() {
        instance = this;
        long startTime = System.currentTimeMillis();

        fileManager = new FileManager(this);
        fileManager.getConfig("config.yml").copyDefaults(true).save();
        fileManager.getConfig("translates.yml").copyDefaults(true).save();

        settings = new CCSettings();
        settings.loadSettings();

        registerCCCommand();
        log(LOG_LEVEL.INFO, "The plugin has been activated (" + (System.currentTimeMillis() - startTime) / 1000.0 + "s)");
    }
    
    public void onDisable() {
        log(LOG_LEVEL.INFO, "The plugin has been disabled");
    }

    private void registerCCCommand() {

        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);

            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            CCCommand cmd = new CCCommand(this);
            commandMap.register(cmd.getName(), cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void log(LOG_LEVEL level, String text) {
        getInstance().getServer().getConsoleSender().sendMessage(Main.PREFIX + " " + ChatColor.DARK_GRAY + "[" + level.getName() + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + text);
    }
    
    public enum LOG_LEVEL
    {
        INFO("INFO", 0, ChatColor.GREEN + "INFO"), 
        WARNING("WARNING", 1, ChatColor.YELLOW + "WARNING"), 
        ERROR("ERROR", 2, ChatColor.RED + "ERROR"), 
        DEBUG("DEBUG", 3, ChatColor.AQUA + "DEBUG");
        
        private final String name;
        
        private LOG_LEVEL(String s, int n, String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
