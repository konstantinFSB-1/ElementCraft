package com.yourname.elementcraft;

import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {
    private final JavaPlugin plugin;
    private Connection connection;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/trades.db");

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS trades (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "player_uuid TEXT NOT NULL," +
                        "player_name TEXT NOT NULL," +
                        "trader_uuid TEXT NOT NULL," +
                        "amount INTEGER NOT NULL," +
                        "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Ошибка при инициализации БД: " + e.getMessage());
        }
    }

    public void logTrade(Player player, WanderingTrader trader, int amount) {
        String query = "INSERT INTO trades (player_uuid, player_name, trader_uuid, amount) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, player.getName());
            ps.setString(3, trader.getUniqueId().toString());
            ps.setInt(4, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().warning("Ошибка при логировании: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().warning("Ошибка при закрытии БД: " + e.getMessage());
        }
    }
}