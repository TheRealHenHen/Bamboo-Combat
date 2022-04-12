package net.bamboo.combat.entity; //By TheRealHenHen

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class SpearEntityModel extends Model {

    private final ModelPart root;
    
    public SpearEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData modelBamboo() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 =
        modelPartData.addChild("pole", ModelPartBuilder.create().uv(0, -1).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 28.0F, 1.0F), ModelTransform.NONE);
        modelPartData2.addChild("bottom_spike", ModelPartBuilder.create().uv(5, 0).cuboid(-0.5F, -3.5F, -0.2F, 1.0F, 0.5F, 0.7F), ModelTransform.NONE);
        modelPartData2.addChild("top_spike", ModelPartBuilder.create().uv(9, 0).cuboid(-0.5F, -4.0F, 0.1F, 1.0F, 0.5F, 0.4F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 32, 32);
    }

    public static TexturedModelData modelStone() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 =
        modelPartData.addChild("pole", ModelPartBuilder.create().uv(0, -1).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 28.0F, 1.0F), ModelTransform.NONE);
        modelPartData2.addChild("bottom_stone", ModelPartBuilder.create().uv(5, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.NONE);
        modelPartData2.addChild("top_stone", ModelPartBuilder.create().uv(15, 1).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 32, 32);
    }

    public static TexturedModelData modelIron() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 =
        modelPartData.addChild("pole", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, 1.0F, -0.5F, 1.0F, 27.0F, 1.0F), ModelTransform.NONE);
        modelPartData2.addChild("bottom", ModelPartBuilder.create().uv(5, 1).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F), ModelTransform.NONE);
        modelPartData2.addChild("top", ModelPartBuilder.create().uv(20, 1).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F), ModelTransform.NONE);
        modelPartData2.addChild("right", ModelPartBuilder.create().uv(14, 1).cuboid(-1.5F, -1.0F, -0.5F, 0.5F, 1.0F, 1.0F), ModelTransform.NONE);
        modelPartData2.addChild("left", ModelPartBuilder.create().uv(14, 3).cuboid(1.0F, -1.0F, -0.5F, 0.5F, 1.0F, 1.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
    
}
