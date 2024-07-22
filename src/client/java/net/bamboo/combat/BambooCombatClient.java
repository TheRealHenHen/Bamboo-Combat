package net.bamboo.combat; //By TheRealHenHen


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;

import java.util.UUID;

import net.bamboo.combat.entity.spear.SpearEntityModel;
import net.bamboo.combat.entity.spear.SpearEntityModelLayers;
import net.bamboo.combat.entity.spear.SpearEntityPacket;
import net.bamboo.combat.entity.spear.SpearEntityRenderer;
import net.bamboo.combat.item.BambooItems;
import net.bamboo.combat.item.spear.SpearItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.TexturedModelDataProvider;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class BambooCombatClient implements ClientModInitializer {

	private void registerSpearEntity(SpearItem item, EntityModelLayer modelLayer, TexturedModelDataProvider provider) {

		Identifier spearId = Registries.ITEM.getId(item);

		ModelLoadingPlugin.register((context) -> {
            context.addModels(Identifier.of(BambooCombat.MODID, "item/" + spearId.getPath() + "/" + "gui"));
		});

		EntityModelLayerRegistry.registerModelLayer(modelLayer, provider);
		EntityRendererRegistry.register(item.getEntityType(), (context) -> new SpearEntityRenderer(context,
			Identifier.of(BambooCombat.MODID, ("textures/entity/" + spearId.getPath() + "/normal.png")), modelLayer));

		ModelPredicateProviderRegistry.register(item, new Identifier("throwing"),
			(stack, clientWorld, livingEntity, seed) -> {
				if (livingEntity == null) {
					return 0.0F;
				}
				return livingEntity.isUsingItem() && livingEntity.getActiveItem() == stack ? 1.0F : 0.0F;
			});


        ClientPlayNetworking.registerGlobalReceiver(SpearEntityPacket.PACKET_ID, (payload, context) -> {
			double x = payload.x();
            double y = payload.y();
            double z = payload.z();
            int entityId = payload.entityId();
            UUID entityUuid = payload.entityUuid();
			
			@SuppressWarnings("resource")
			ClientWorld world = MinecraftClient.getInstance().world;
			if (world != null) {
				Entity entity = item.getEntityType().create(world);
				if (entity != null) {
					entity.updatePosition(x, y, z);
					entity.setId(entityId);
					entity.setUuid(entityUuid);
					world.addEntity(entity);
				}
			}
        });

	}

	@Override
	public void onInitializeClient() {

		registerSpearEntity(BambooItems.BAMBOO_SPEAR, SpearEntityModelLayers.BAMBOO_SPEAR, SpearEntityModel::bambooSpear);
		registerSpearEntity(BambooItems.STONE_BAMBOO_SPEAR, SpearEntityModelLayers.STONE_BAMBOO_SPEAR, SpearEntityModel::stoneBambooSpear);
		registerSpearEntity(BambooItems.IRON_BAMBOO_SPEAR, SpearEntityModelLayers.IRON_BAMBOO_SPEAR, SpearEntityModel::ironBambooSpear);
		registerSpearEntity(BambooItems.COPPER_BAMBOO_SPEAR, SpearEntityModelLayers.COPPER_BAMBOO_SPEAR, SpearEntityModel::ironBambooSpear);
		registerSpearEntity(BambooItems.GOLDEN_BAMBOO_SPEAR, SpearEntityModelLayers.GOLDEN_BAMBOO_SPEAR, SpearEntityModel::ironBambooSpear);
		registerSpearEntity(BambooItems.DIAMOND_BAMBOO_SPEAR, SpearEntityModelLayers.DIAMOND_BAMBOO_SPEAR, SpearEntityModel::diamondBambooSpear);
		registerSpearEntity(BambooItems.NETHERITE_BAMBOO_SPEAR, SpearEntityModelLayers.NETHERITE_BAMBOO_SPEAR, SpearEntityModel::netheriteBambooSpear);
		
	}

}