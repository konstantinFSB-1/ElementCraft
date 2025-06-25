package com.yourname.elementcraft;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HotbarListener implements Listener {
    private final Map<UUID, ItemStack[]> secondaryHotbars = new HashMap<>();
    private final Map<UUID, Boolean> isSecondaryActive = new HashMap<>();
    private final Plugin plugin;

    public HotbarListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) { 
            event.setCancelled(true); 
            toggleHotbar(player);
        }
    }

    private void toggleHotbar(Player player) {
        UUID uuid = player.getUniqueId();
        ItemStack[] currentHotbar = getHotbarItems(player);

        if (!isSecondaryActive.getOrDefault(uuid, false)) {
            
            secondaryHotbars.put(uuid, currentHotbar);
            ItemStack[] savedHotbar = loadSecondaryHotbar(player);

            if (savedHotbar != null) {
                setHotbarItems(player, savedHotbar);
            } else {
                setHotbarItems(player, new ItemStack[9]);
            }

            isSecondaryActive.put(uuid, true);
            player.sendMessage("§aПереключено на второй хотбар");
        } else {
            
            ItemStack[] secondaryHotbar = getHotbarItems(player);
            saveSecondaryHotbar(player, secondaryHotbar);
            setHotbarItems(player, secondaryHotbars.get(uuid));
            isSecondaryActive.put(uuid, false);
            player.sendMessage("§aПереключено на основной хотбар");
        }
    }

    private ItemStack[] getHotbarItems(Player player) {
        ItemStack[] hotbar = new ItemStack[9];
        for (int i = 0; i < 9; i++) {
            hotbar[i] = player.getInventory().getItem(i) != null ?
                    player.getInventory().getItem(i).clone() :
                    null;
        }
        return hotbar;
    }

    private void setHotbarItems(Player player, ItemStack[] items) {
        for (int i = 0; i < 9 && i < items.length; i++) {
            player.getInventory().setItem(i, items[i] != null ? items[i].clone() : null);
        }
        player.updateInventory();
    }

    private void saveSecondaryHotbar(Player player, ItemStack[] items) {
        NamespacedKey key = new NamespacedKey(plugin, "secondary_hotbar");
        player.getPersistentDataContainer().set(key, PersistentDataType.STRING, serializeItemStackArray(items));
    }

    private ItemStack[] loadSecondaryHotbar(Player player) {
        NamespacedKey key = new NamespacedKey(plugin, "secondary_hotbar");
        String serialized = player.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        return serialized != null ? deserializeItemStackArray(serialized) : null;
    }

    private String serializeItemStackArray(ItemStack[] items) {
        StringBuilder builder = new StringBuilder();
        for (ItemStack item : items) {
            if (item != null) {
                builder.append(item.getType()).append(",")
                        .append(item.getAmount()).append(",")
                        .append(";");
            } else {
                builder.append("AIR,0,");
            }
        }
        return builder.toString();
    }

    private ItemStack[] deserializeItemStackArray(String data) {
        String[] parts = data.split(";");
        ItemStack[] items = new ItemStack[9];
        for (int i = 0; i < Math.min(parts.length, 9); i++) {
            String[] itemData = parts[i].split(",");
            if (itemData.length >= 2 && !itemData[0].equals("AIR")) {
                items[i] = new ItemStack(
                        Material.valueOf(itemData[0]),
                        Integer.parseInt(itemData[1])
                );
            }
        }
        return items;
    }
}
