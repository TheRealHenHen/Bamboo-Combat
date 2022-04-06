/*package net.bamboo.combat.mixin;

import com.google.common.collect.ImmutableMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.bamboo.combat.entity.SpearEntityModelLayers;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import net.minecraft.client.render.entity.model.TridentEntityModel;

@Mixin(EntityModels.class)
public class SpearEntityModels {

    @ModifyVariable(method = "getModels", at = @At("HEAD"))
    public void injectModel(ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder) {
        builder.put(SpearEntityModelLayers.BAMBOO_SPEAR, TridentEntityModel.getTexturedModelData());
    }

}*/
