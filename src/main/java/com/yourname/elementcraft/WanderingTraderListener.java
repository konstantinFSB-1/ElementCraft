package com.yourname.elementcraft;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.Material;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.Collections;
import java.util.Random;

public class WanderingTraderListener implements Listener {
    private final Random random = new Random();
    private static final String TRADE_KEY = "ender_pearl_trade_added";

    @EventHandler
    public void onTradeSetup(VillagerAcquireTradeEvent event) {
        if (!(event.getEntity() instanceof WanderingTrader)) return;

        WanderingTrader trader = (WanderingTrader) event.getEntity();

        if (trader.getPersistentDataContainer().has(new NamespacedKey(ElementCraft.getInstance(), TRADE_KEY))) {
            return;
        }

        MerchantRecipe trade = createEnderPearlTrade();

        trader.setRecipes(Collections.singletonList(trade));

        trader.getPersistentDataContainer().set(
                new NamespacedKey(ElementCraft.getInstance(), TRADE_KEY),
                PersistentDataType.BYTE, (byte) 1
        );
    }

    private MerchantRecipe createEnderPearlTrade() {
        ItemStack pearl = new ItemStack(Material.ENDER_PEARL, 1);
        ItemStack gold = new ItemStack(Material.GOLD_INGOT, random.nextInt(3) + 1); // 1-3 золота

        MerchantRecipe recipe = new MerchantRecipe(
                pearl,
                0,                 
                12,                
                false,              
                1,                  
                1.0f               
        );
        recipe.addIngredient(gold);

        return recipe;
    }
}
