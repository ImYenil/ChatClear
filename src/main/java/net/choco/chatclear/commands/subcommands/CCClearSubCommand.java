package net.choco.chatclear.commands.subcommands;

import net.choco.chatclear.Main;
import net.choco.chatclear.commands.CCCommand;
import net.choco.chatclear.commands.CCSubCommand;
import net.choco.chatclear.language.Message;
import net.choco.chatclear.utility.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CCClearSubCommand extends CCSubCommand
{

    public CCClearSubCommand() {
        super(false,"clear", "chatclear.clear", "§e/cc clear §e» §7Clear chat");
    }
    @Override
    public boolean execute(CCCommand cmd, CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                for (int x = 0; x < 150; ++x) {
                    if (p.hasPermission("chatclear.bypass") || p.isOp()) {
                        return true;
                    }
                    Bukkit.getServer().broadcastMessage(" ");
                }
                Bukkit.broadcastMessage(Message.CHAT_CLEAR.getMessageWithPrefix().replace("{player}", p.getName()));
                return true;
            }
            sender.sendMessage("§cUsage >> /cc clear §8| §7Clear chat");
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] array) {
        return null;
    }
}
