package net.bamboo.combat.item; //By TheRealHenHen

import net.bamboo.combat.entity.SpearEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Collections;

public class SpearItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {
    private final Identifier id;
    private final Identifier spearId;
    private final Identifier texture;
    private final EntityModelLayer modelLayer;
    private ItemRenderer itemRenderer;
    private SpearEntityModel spearModel;
    private BakedModel inventorySpearModel;

    public SpearItemRenderer(Identifier spearId, Identifier texture, EntityModelLayer modelLayer) {
        this.id = new Identifier(spearId.getNamespace(), spearId.getPath() + "_renderer");
        this.spearId = spearId;
        this.texture = texture;
        this.modelLayer = modelLayer;
    }

    @Override
    public Identifier getFabricId() {
        return this.id;
    }

    @Override
    public Collection<Identifier> getFabricDependencies() {
        return Collections.singletonList(ResourceReloadListenerKeys.MODELS);
    }

    @Override
    public void reload(ResourceManager manager) {
        MinecraftClient mc = MinecraftClient.getInstance();
        this.itemRenderer = mc.getItemRenderer();
        this.spearModel = new SpearEntityModel(mc.getEntityModelLoader().getModelPart(this.modelLayer));
        this.inventorySpearModel = mc.getBakedModelManager().getModel(new ModelIdentifier(this.spearId + "_in_inventory", "inventory"));
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode renderMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        assert this.spearModel != null;
        if (renderMode == ModelTransformation.Mode.GUI || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED) {
            matrices.pop(); // cancel the previous transformation and pray that we are not breaking the state
            matrices.push();
            itemRenderer.renderItem(stack, renderMode, false, matrices, vertexConsumers, light, overlay, this.inventorySpearModel);
        } else {
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.spearModel.getLayer(this.texture), false, stack.hasGlint());
            this.spearModel.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
        }
    }
}