package com.yourname.elementcraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class ObjectCommand implements CommandExecutor {
    private final ElementCraft plugin;

    public ObjectCommand(ElementCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cИспользование: /object <create|delete|info|add|remove> [аргументы]");
            return false;
        }

        String subCommand = args[0].toLowerCase();
        ObjectManager manager = plugin.getObjectManager();

        switch (subCommand) {
            case "create":
                return handleCreate(sender, args, manager);
            case "delete":
                return handleDelete(sender, args, manager);
            case "info":
                return handleInfo(sender, args, manager);
            case "add":
                return handleAdd(sender, args, manager);
            case "remove":
                return handleRemove(sender, args, manager);
            default:
                sender.sendMessage("§cНеизвестная подкоманда: " + subCommand);
                return false;
        }
    }

    private boolean handleCreate(CommandSender sender, String[] args, ObjectManager manager) {
        if (args.length != 2) {
            sender.sendMessage("§cИспользование: /object create <name>");
            return false;
        }

        String name = args[1];
        if (manager.createObject(name)) {
            sender.sendMessage("§aОбъект '" + name + "' успешно создан!");
            return true;
        } else {
            sender.sendMessage("§cОбъект '" + name + "' уже существует!");
            return false;
        }
    }

    private boolean handleDelete(CommandSender sender, String[] args, ObjectManager manager) {
        if (args.length != 2) {
            sender.sendMessage("§cИспользование: /object delete <name>");
            return false;
        }

        String name = args[1];
        if (manager.deleteObject(name)) {
            sender.sendMessage("§aОбъект '" + name + "' успешно удалён!");
            return true;
        } else {
            sender.sendMessage("§cОбъект '" + name + "' не найден!");
            return false;
        }
    }

    private boolean handleInfo(CommandSender sender, String[] args, ObjectManager manager) {
        if (args.length != 2) {
            sender.sendMessage("§cИспользование: /object info <name>");
            return false;
        }

        String name = args[1];
        Set<String> rules = manager.getRules(name);

        if (rules == null) {
            sender.sendMessage("§cОбъект '" + name + "' не найден!");
            return false;
        }

        if (rules.isEmpty()) {
            sender.sendMessage("§eОбъект '" + name + "' не содержит правил");
        } else {
            sender.sendMessage("§aПравила объекта '" + name + "': " + String.join(", ", rules));
        }
        return true;
    }

    private boolean handleAdd(CommandSender sender, String[] args, ObjectManager manager) {
        if (args.length != 3) {
            sender.sendMessage("§cИспользование: /object add <name> <rule>");
            return false;
        }

        String name = args[1];
        String rule = args[2].toUpperCase();

        if (manager.addRule(name, rule)) {
            sender.sendMessage("§aПравило '" + rule + "' добавлено в объект '" + name + "'");
            return true;
        } else {
            sender.sendMessage("§cНе удалось добавить правило! Проверьте имя объекта или правило");
            return false;
        }
    }

    private boolean handleRemove(CommandSender sender, String[] args, ObjectManager manager) {
        if (args.length != 3) {
            sender.sendMessage("§cИспользование: /object remove <name> <rule>");
            return false;
        }

        String name = args[1];
        String rule = args[2].toUpperCase();

        if (manager.removeRule(name, rule)) {
            sender.sendMessage("§aПравило '" + rule + "' удалено из объекта '" + name + "'");
            return true;
        } else {
            sender.sendMessage("§cНе удалось удалить правило! Проверьте имя объекта или правило");
            return false;
        }
    }
}