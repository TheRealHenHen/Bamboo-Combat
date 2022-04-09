package net.bamboo.combat; //By TheRealHenHen

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

import net.bamboo.combat.entity.SpearEntity;
import net.bamboo.combat.entity.SpearEntityModel;
import net.bamboo.combat.entity.SpearEntityModelLayers;
import net.bamboo.combat.entity.SpearEntityRenderer;
import net.bamboo.combat.item.SpearItemRenderer;
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
public class Client implements ClientModInitializer {
	
    public static final EntityModelLayer SPEAR = new EntityModelLayer(new Identifier(Main.MODID, "spear"), "main");

	public void registerThrowingPredicate(Item item) {
		ModelPredicateProviderRegistry.register(item, new Identifier("throwing"), (itemStack, clientWorld, livingEntity, seed) -> {
			if (livingEntity == null) {
				return 0.0F;
			}
			return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		});
	}

	private void register(Item item, EntityType<SpearEntity> type, EntityModelLayer modelLayer, TexturedModelDataProvider provider) {
		Identifier spearId = Registry.ITEM.getId(item);
		Identifier texture = new Identifier(spearId.getNamespace(), "textures/entity/" + spearId.getPath() + "/normal.png");

		SpearItemRenderer spearItemRenderer = new SpearItemRenderer(spearId, texture, modelLayer);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(spearItemRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(item, spearItemRenderer);
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier(spearId + "_in_inventory", "inventory")));
		
        EntityModelLayerRegistry.registerModelLayer(modelLayer, provider);
		EntityRendererRegistry.register(type, (context) -> new SpearEntityRenderer(context, new Identifier(Main.MODID, ("textures/entity/" + spearId.getPath() + "/normal.png")), modelLayer));
	}

    @Override
	public void onInitializeClient() {
		
		//EntityRendererRegistry.register(Main.BAMBOO_SPEAR, (context) -> new SpearEntityRenderer(context));

		registerThrowingPredicate(Main.Bamboo);
		registerThrowingPredicate(Main.Stone);
		registerThrowingPredicate(Main.Copper);
		registerThrowingPredicate(Main.Iron);
		registerThrowingPredicate(Main.Gold);
		registerThrowingPredicate(Main.Diamond);
		registerThrowingPredicate(Main.Netherite);

		
		ClientSidePacketRegistry.INSTANCE.register(new Identifier(Main.MODID, "bamboo_spear"), (context, packet) -> {

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
	
		register(Main.Bamboo, Main.BAMBOO_SPEAR, SpearEntityModelLayers.BAMBOO_SPEAR, SpearEntityModel::getBambooTexturedModelData);
		register(Main.Stone, Main.STONE_BAMBOO_SPEAR, SpearEntityModelLayers.STONE_BAMBOO_SPEAR, SpearEntityModel::getStoneTexturedModelData);

    }

}