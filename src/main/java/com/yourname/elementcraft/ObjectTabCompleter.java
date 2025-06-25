package com.yourname.elementcraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ObjectTabCompleter implements TabCompleter {
    private final ElementCraft plugin;

    public ObjectTabCompleter(ElementCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        ObjectManager manager = plugin.getObjectManager();

        if (args.length == 1) {
            completions.add("create");
            completions.add("delete");
            completions.add("info");
            completions.add("add");
            completions.add("remove");
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "delete":
                case "info":
                case "add":
                case "remove":
                    completions.addAll(manager.getObjectNames());
                    break;
            }
        } else if (args.length == 3 && ("add".equalsIgnoreCase(args[0]) || "remove".equalsIgnoreCase(args[0]))) {
            String subCommand = args[0].toLowerCase();
            String objectName = args[1];

            if ("add".equals(subCommand)) {
                completions.addAll(manager.getAvailableRules(objectName));
            } else if ("remove".equals(subCommand)) {
                completions.addAll(manager.getRemovableRules(objectName));
            }
        }

        return filterCompletions(args[args.length - 1], completions);
    }

    private List<String> filterCompletions(String input, List<String> options) {
        List<String> result = new ArrayList<>();
        for (String option : options) {
            if (option.toLowerCase().startsWith(input.toLowerCase())) {
                result.add(option);
            }
        }
        return result;
    }
}