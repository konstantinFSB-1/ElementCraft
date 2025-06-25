package com.yourname.elementcraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ObjectManager {
    private final JavaPlugin plugin;
    private final Map<String, Set<String>> objects = new HashMap<>();
    private static final List<String> ALL_RULES = Arrays.asList("A", "B", "C", "D", "E");

    public ObjectManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadObjects() {
        FileConfiguration config = plugin.getConfig();
        if (config.contains("objects")) {
            for (String name : config.getConfigurationSection("objects").getKeys(false)) {
                Set<String> rules = new HashSet<>(config.getStringList("objects." + name + ".rules"));
                objects.put(name, rules);
            }
        }
    }

    public void saveObjects() {
        FileConfiguration config = plugin.getConfig();
        for (Map.Entry<String, Set<String>> entry : objects.entrySet()) {
            config.set("objects." + entry.getKey() + ".rules", new ArrayList<>(entry.getValue()));
        }
        plugin.saveConfig();
    }

    public boolean createObject(String name) {
        if (objects.containsKey(name)) return false;
        objects.put(name, new HashSet<>());
        return true;
    }

    public boolean deleteObject(String name) {
        return objects.remove(name) != null;
    }

    public boolean addRule(String name, String rule) {
        if (!objects.containsKey(name) || !ALL_RULES.contains(rule)) return false;
        return objects.get(name).add(rule);
    }

    public boolean removeRule(String name, String rule) {
        if (!objects.containsKey(name)) return false;
        return objects.get(name).remove(rule);
    }

    public Set<String> getRules(String name) {
        return objects.getOrDefault(name, Collections.emptySet());
    }

    public List<String> getObjectNames() {
        return new ArrayList<>(objects.keySet());
    }

    public List<String> getAvailableRules(String name) {
        Set<String> current = objects.getOrDefault(name, Collections.emptySet());
        List<String> available = new ArrayList<>(ALL_RULES);
        available.removeAll(current);
        return available;
    }

    public List<String> getRemovableRules(String name) {
        return new ArrayList<>(objects.getOrDefault(name, Collections.emptySet()));
    }
}