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


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 =
        modelPartData.addChild("pole", ModelPartBuilder.create().uv(0, -1).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 28.0F, 1.0F), ModelTransform.NONE);
        modelPartData2.addChild("bottom_spike", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 0.5F, 0.7F), ModelTransform.NONE);
        modelPartData2.addChild("top_spike", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 0.5F, 0.4F), ModelTransform.NONE);
        //modelPartData2.addChild("middle_spike", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F), ModelTransform.NONE);
        //modelPartData2.addChild("right_spike", ModelPartBuilder.create().uv(4, 3).mirrored().cuboid(1.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
    
}
