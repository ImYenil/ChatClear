package net.choco.chatclear.commands;

import lombok.Getter;
import net.choco.chatclear.Main;
import net.choco.chatclear.commands.subcommands.CCClearSubCommand;
import net.choco.chatclear.commands.subcommands.CCReloadSubCommand;
import net.choco.chatclear.language.Message;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CCCommand extends Command {

    private static final String AVAILABLE_COMMANDS_MSG = "§6✪ §e§lChatClear §6✪ §8- §6Available Commands";
    private static final String ARGUMENT_HELP_MSG = "§b[] §8- §7Optional argument §b<> §8- §7Required argument";

    private static final HashMap<String, CCSubCommand>  subCommands;

    static {
        subCommands = new HashMap<>();
        subCommands.put("clear", new CCClearSubCommand());
        subCommands.put("reload", new CCReloadSubCommand());
    }

    @Getter
    private Main plugin;

    public CCCommand(Main plugin) {
        super(plugin.getFileManager().getConfig("config.yml").get().getString("main_command.name"));
        this.plugin = plugin;
        this.description = plugin.getFileManager().getConfig("config.yml").get().getString("main_command.description");
        this.setAliases(plugin.getFileManager().getConfig("config.yml").get().getStringList("main_command.aliases"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length > 0) {
            CCSubCommand subCommand = getSubCommand(args[0]);
            if (subCommand != null) {
                if (sender.hasPermission(subCommand.getPermissionRequired())) {
                    return subCommand.execute(this, sender, Arrays.copyOfRange(args, 1, args.length));
                } else {
                    sender.sendMessage(Message.NO_PERMISSION.getMessageWithPrefix());
                }
            }
        } else {
            return commandUsage(sender);
        }
        return false;
    }

    private CCSubCommand getSubCommand(String name) {
        return subCommands.get(name.toLowerCase());
    }

    private boolean commandUsage(CommandSender p) {
        p.sendMessage(AVAILABLE_COMMANDS_MSG);
        p.sendMessage(ARGUMENT_HELP_MSG);

        for (CCSubCommand subCommand : subCommands.values()) {
            if (p.hasPermission(subCommand.getPermissionRequired())) {
                subCommand.sendUsage(p);
            }
        }
        return true;
    }

    public List<String> tabComplete(CommandSender commandSender, String s, String[] array, Location location) {
        return this.tabComplete(commandSender, s, array);
    }

    public List<String> tabComplete(CommandSender commandSender, String s, String[] array) {
        if (Arrays.asList(array).size() < 1) {
            return null;
        }
        CCSubCommand subCommand = this.getSubCommand(array[0]);
        if (subCommand != null) {
            return subCommand.onTabComplete(commandSender, this, s, array);
        }
        return new ArrayList<>(subCommands.keySet());
    }
}