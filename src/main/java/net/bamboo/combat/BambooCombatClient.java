package net.bamboo.combat; //By TheRealHenHen

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;

import java.util.UUID;

import net.bamboo.combat.entity.spear.SpearEntity;
import net.bamboo.combat.entity.spear.SpearEntityModel;
import net.bamboo.combat.entity.spear.SpearEntityModelLayers;
import net.bamboo.combat.entity.spear.SpearEntityRenderer;
import net.bamboo.combat.item.BambooItems;
import net.bamboo.combat.item.spear.SpearItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.TexturedModelDataProvider;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class BambooCombatClient implements ClientModInitializer {

	private void register(SpearItem item, EntityModelLayer modelLayer, TexturedModelDataProvider provider) {

		Identifier spearId = Registries.ITEM.getId(item);

		ModelLoadingRegistry.INSTANCE.registerModelProvider(
			(manager, out) -> out.accept(new ModelIdentifier(BambooCombat.MODID, spearId.getPath() + "/" + "gui", "inventory")));

		EntityModelLayerRegistry.registerModelLayer(modelLayer, provider);
		EntityRendererRegistry.register(item.getEntityType(), (context) -> new SpearEntityRenderer(context,
			new Identifier(BambooCombat.MODID, ("textures/entity/" + spearId.getPath() + "/normal.png")), modelLayer));

		ModelPredicateProviderRegistry.register(item, new Identifier("throwing"),
			(stack, clientWorld, livingEntity, seed) -> {
				if (livingEntity == null) {
					return 0.0F;
				}
				return livingEntity.isUsingItem() && livingEntity.getActiveItem() == stack ? 1.0F : 0.0F;
			});

		ClientPlayNetworking.registerGlobalReceiver(new Identifier(BambooCombat.MODID, spearId.getPath()),
				(client, player, packet, sender) -> {

					double x = packet.readDouble();
					double y = packet.readDouble();
					double z = packet.readDouble();

					int entityID = packet.readInt();
					UUID entityUUID = packet.readUuid();
					MinecraftClient mc = MinecraftClient.getInstance();

					mc.world.addEntity(entityID, new SpearEntity(mc.world, x, y, z, entityID, entityUUID));
				});

	}

	@Override
	public void onInitializeClient() {

		register(BambooItems.BAMBOO_SPEAR, SpearEntityModelLayers.BAMBOO_SPEAR, SpearEntityModel::BambooSpear);
		register(BambooItems.STONE_BAMBOO_SPEAR, SpearEntityModelLayers.STONE_BAMBOO_SPEAR, SpearEntityModel::StoneBambooSpear);
		register(BambooItems.IRON_BAMBOO_SPEAR, SpearEntityModelLayers.IRON_BAMBOO_SPEAR, SpearEntityModel::IronBambooSpear);
		register(BambooItems.COPPER_BAMBOO_SPEAR, SpearEntityModelLayers.COPPER_BAMBOO_SPEAR, SpearEntityModel::IronBambooSpear);
		register(BambooItems.GOLDEN_BAMBOO_SPEAR, SpearEntityModelLayers.GOLDEN_BAMBOO_SPEAR, SpearEntityModel::IronBambooSpear);
		register(BambooItems.DIAMOND_BAMBOO_SPEAR, SpearEntityModelLayers.DIAMOND_BAMBOO_SPEAR, SpearEntityModel::DiamondBambooSpear);
		register(BambooItems.NETHERITE_BAMBOO_SPEAR, SpearEntityModelLayers.NETHERITE_BAMBOO_SPEAR, SpearEntityModel::NetheriteBambooSpear);

	}

}