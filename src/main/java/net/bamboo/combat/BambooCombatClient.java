package net.bamboo.combat; //By TheRealHenHen

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
		Identifier spearId = Registry.ITEM.getId(item);

		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier(spearId + "/" + "gui", "inventory")));
		
        EntityModelLayerRegistry.registerModelLayer(modelLayer, provider);
		EntityRendererRegistry.register(item.getEntityType(), (context) -> new SpearEntityRenderer(context, new Identifier(BambooCombat.MODID, ("textures/entity/" + spearId.getPath() + "/normal.png")), modelLayer));
		
		ModelPredicateProviderRegistry.register(item, new Identifier("throwing"), (stack, clientWorld, livingEntity, seed) -> {
			if (livingEntity == null) {
				return 0.0F;
			}
			return livingEntity.isUsingItem() && livingEntity.getActiveItem() == stack ? 1.0F : 0.0F;
		});
	}

    @Override
	public void onInitializeClient() {

		ClientPlayNetworking.registerGlobalReceiver(new Identifier(BambooCombat.MODID, "bamboo_spear"), (client, handler, buf, sender) -> {

			double x = buf.readDouble();
			double y = buf.readDouble();
			double z = buf.readDouble();

			int entityID = buf.readInt();
			UUID entityUUID = buf.readUuid();
			MinecraftClient mc = MinecraftClient.getInstance();

			client.execute(() -> {
				SpearEntity spear = new SpearEntity(mc.world, x, y, z, entityID, entityUUID);
				mc.world.addEntity(entityID, spear);
			});
		});
	
		register(BambooItems.BAMBOO, SpearEntityModelLayers.BAMBOO, SpearEntityModel::modelBamboo);
		register(BambooItems.STONE, SpearEntityModelLayers.STONE, SpearEntityModel::modelStone);
		register(BambooItems.COPPER, SpearEntityModelLayers.COPPER, SpearEntityModel::modelIron);
		register(BambooItems.IRON, SpearEntityModelLayers.IRON, SpearEntityModel::modelIron);
		register(BambooItems.GOLD, SpearEntityModelLayers.GOLD, SpearEntityModel::modelIron);
		register(BambooItems.DIAMOND, SpearEntityModelLayers.DIAMOND, SpearEntityModel::modelDiamond);
		register(BambooItems.NETHERITE, SpearEntityModelLayers.NETHERITE, SpearEntityModel::modelNetherite);

    }

}