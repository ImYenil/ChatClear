package net.choco.chatclear.commands;

import lombok.Getter;
import org.bukkit.command.*;

public abstract class CCSubCommand implements TabCompleter {

    protected static final String NEW = "§c§lNEW ";

    private final String[] usage;
    @Getter
    private String subCommand;
    @Getter
    private String permissionRequired;
    @Getter
    private boolean isNew;

    protected CCSubCommand(boolean isNew, String subCommand, String permissionRequired, String... usage) {
        this.isNew = isNew;
        this.subCommand = subCommand;
        this.permissionRequired = permissionRequired;
        this.usage = usage;
    }

    public abstract boolean execute(CCCommand cmd, CommandSender sender, String[] args);

    public void sendUsage(CommandSender sender) {
        if (this.usage == null) {
            return;
        }
        for (String s : this.usage) {
            if (this.isNew) {
                sender.sendMessage(NEW + s);
            } else {
                sender.sendMessage(s);
            }
        }
    }
}
