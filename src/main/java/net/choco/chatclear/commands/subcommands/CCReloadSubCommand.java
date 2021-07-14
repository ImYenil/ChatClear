package net.choco.chatclear.commands.subcommands;

import net.choco.chatclear.Main;
import net.choco.chatclear.commands.CCCommand;
import net.choco.chatclear.commands.CCSubCommand;
import net.choco.chatclear.language.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CCReloadSubCommand extends CCSubCommand
{
    public CCReloadSubCommand() {
        super(false,"reload", "chatclear.reload", "§e/cc reload §8» §7Reload plugin");
    }

    @Override
    public boolean execute(CCCommand cmd, CommandSender sender, String[] args) {
        if (args.length == 0) {
            Main.getInstance().onDisable();
            Main.getInstance().onEnable();
            Message.reloadMessages();
            sender.sendMessage(cmd.getPlugin().getSettings().getPrefix() + "§aPlugin reloaded!");
            return true;
        } else {
            this.sendUsage(sender);
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] array) {
        return null;
    }
}
