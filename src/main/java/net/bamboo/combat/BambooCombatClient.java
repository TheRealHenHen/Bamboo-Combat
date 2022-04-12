package net.bamboo.combat; //By TheRealHenHen

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

import net.bamboo.combat.entity.SpearEntity;
import net.bamboo.combat.entity.SpearEntityModel;
import net.bamboo.combat.entity.SpearEntityModelLayers;
import net.bamboo.combat.entity.SpearEntityRenderer;
import net.bamboo.combat.item.BambooItems;
import net.bamboo.combat.item.spear.SpearItem;
import net.bamboo.combat.item.spear.SpearItemRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.TexturedModelDataProvider;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

@Environment(EnvType.CLIENT)
public class BambooCombatClient implements ClientModInitializer {

	private void register(SpearItem item, EntityModelLayer modelLayer, TexturedModelDataProvider provider) {
		Identifier spearId = Registry.ITEM.getId(item);
		Identifier texture = new Identifier(spearId.getNamespace(), "textures/entity/" + spearId.getPath() + "/normal.png");

		SpearItemRenderer spearItemRenderer = new SpearItemRenderer(spearId, texture, modelLayer);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(spearItemRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(item, spearItemRenderer);
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier(spearId + "/" + spearId.getPath() + "_gui", "inventory")));
		
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
		
		//EntityRendererRegistry.register(Main.BAMBOO_SPEAR, (context) -> new SpearEntityRenderer(context));

		
		ClientSidePacketRegistry.INSTANCE.register(new Identifier(BambooCombat.MODID, "bamboo_spear"), (context, packet) -> {

			double x = packet.readDouble();
			double y = packet.readDouble();
			double z = packet.readDouble();

			int entityID = packet.readInt();
			UUID entityUUID = packet.readUuid();
			MinecraftClient mc = MinecraftClient.getInstance();

			context.getTaskQueue().execute(() -> {
				SpearEntity spear = new SpearEntity(mc.world, x, y, z, entityID, entityUUID);
				mc.world.addEntity(entityID, spear);
			});
		});
	
		register(BambooItems.BAMBOO, SpearEntityModelLayers.BAMBOO, SpearEntityModel::modelBamboo);
		register(BambooItems.STONE, SpearEntityModelLayers.STONE, SpearEntityModel::modelStone);
		register(BambooItems.COPPER, SpearEntityModelLayers.COPPER, SpearEntityModel::modelIron);
		register(BambooItems.IRON, SpearEntityModelLayers.IRON, SpearEntityModel::modelIron);
		register(BambooItems.GOLD, SpearEntityModelLayers.GOLD, SpearEntityModel::modelIron);
		register(BambooItems.DIAMOND, SpearEntityModelLayers.DIAMOND, SpearEntityModel::modelIron);
		register(BambooItems.NETHERITE, SpearEntityModelLayers.NETHERITE, SpearEntityModel::modelIron);

    }

}