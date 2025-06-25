package com.yourname.elementcraft;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ElementCraft extends JavaPlugin {
    private static ElementCraft instance;
    private ObjectManager objectManager;
    private DatabaseManager databaseManager; // Объявляем переменную

    @Override
    public void onEnable() {
        instance = this;
        objectManager = new ObjectManager(this);
        databaseManager = new DatabaseManager(this); // Инициализируем

        Objects.requireNonNull(getCommand("object")).setExecutor(new ObjectCommand(this));
        Objects.requireNonNull(getCommand("object")).setTabCompleter(new ObjectTabCompleter(this));

        getServer().getPluginManager().registerEvents(new WanderingTraderListener(), this);
        getServer().getPluginManager().registerEvents(new HotbarListener(this), this);

        saveDefaultConfig();
        objectManager.loadObjects();

        getLogger().info("ElementCraft enabled!");
    }

    @Override
    public void onDisable() {
        objectManager.saveObjects();
        if (databaseManager != null) {
            databaseManager.close();
        }
        getLogger().info("ElementCraft disabled!");
    }

    public static ElementCraft getInstance() {
        return instance;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}