package net.bamboo.combat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.UUID;

import net.bamboo.combat.entity.SpearEntity;
import net.bamboo.combat.entity.SpearEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {

	public void registerModelPredicate(Item item) {
		ModelPredicateProviderRegistry.register(item, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, seed) -> {
			if (livingEntity == null) {
				return 0.0F;
			}
			return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		});
	}

    @Override
	public void onInitializeClient() {

		EntityRendererRegistry.register(Main.BAMBOO_SPEAR, (context) -> new SpearEntityRenderer(context));
		
		registerModelPredicate(Main.Bamboo);
		registerModelPredicate(Main.Stone);
		registerModelPredicate(Main.Copper);
		registerModelPredicate(Main.Iron);
		registerModelPredicate(Main.Gold);
		registerModelPredicate(Main.Diamond);
		registerModelPredicate(Main.Netherite);
		
		ClientSidePacketRegistry.INSTANCE.register(new Identifier(Main.MODID, "bamboo_spear"), (context, packet) -> {

			double x = packet.readDouble();
			double y = packet.readDouble();
			double z = packet.readDouble();

			int entityID = packet.readInt();
			UUID entityUUID = packet.readUuid();

			context.getTaskQueue().execute(() -> {
				SpearEntity spear = new SpearEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
				MinecraftClient.getInstance().world.addEntity(entityID, spear);
			});

		});

    }
	
}