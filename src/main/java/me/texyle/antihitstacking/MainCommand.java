package me.texyle.antihitstacking;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Unknown command");
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                EntityDamageByEntity.getInstance().reload();
                sender.sendMessage(ChatColor.GREEN + "Config reloaded");
            }
        }

        return true;
    }
}
