package de.pizzapost.damage_equals_item.gamerules;

import de.pizzapost.Damage_equals_item;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGamerules {
    public static final GameRules.Key<GameRules.BooleanRule> DROP_ITEMS_ON_BLOCK_BREAK =
            GameRuleRegistry.register("dropItemsOnBlockBreak", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> ALLOW_CRAFTING =
            GameRuleRegistry.register("allowCrafting", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(false));
    public static void registerGamerules() {
        Damage_equals_item.LOGGER.info("Registering gamerules for " + Damage_equals_item.MOD_ID);
    }
}
