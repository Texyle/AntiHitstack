package me.texyle.antihitstacking;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> autocomplete = new ArrayList<>();

        if (args.length == 1) {
            autocomplete.add("reload");
            return autocomplete;
        }

        return null;
    }
}
