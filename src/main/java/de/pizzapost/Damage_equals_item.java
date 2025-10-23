package de.pizzapost;

import de.pizzapost.damage_equals_item.gamerules.ModGamerules;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Damage_equals_item implements ModInitializer {
	public static final String MOD_ID = "damage_equals_item";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModGamerules.registerGamerules();
		ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, amount, amount2, hitResult) -> {
			if (entity instanceof ServerPlayerEntity serverPlayer) {
				for (int i = 0; i < amount; i++) {
					Registries.ITEM.getRandom(entity.getEntityWorld().getRandom()).ifPresent(entry -> {
						serverPlayer.getInventory().offerOrDrop(entry.value().getDefaultStack());
					});
				}
			}
		});
		PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
			if (world instanceof ServerWorld serverWorld) {
				System.out.println(serverWorld.getGameRules().getBoolean(ModGamerules.DROP_ITEMS_ON_BLOCK_BREAK));
				if (serverWorld.getGameRules().getBoolean(ModGamerules.DROP_ITEMS_ON_BLOCK_BREAK)) {
					return true;
				} else {
					world.breakBlock(pos, false);
					return false;
				}
			} return true;
		});
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.player;
			if (player.hasPermissionLevel(2)) {
				Text welcomeMessage = Text.translatable("damage_equals_item.welcome_message")
						.formatted(Formatting.GOLD);
				player.sendMessage(welcomeMessage, false);
			}
		});
	}
}