package net.bamboo.combat.mixin;

import com.google.common.collect.ImmutableMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.bamboo.combat.entity.SpearEntityModel;
import net.bamboo.combat.entity.SpearEntityModelLayer;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;

@Mixin(EntityModels.class)
public class SpearEntityModels {
    
    @Shadow private ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder = ImmutableMap.builder();

    @Inject(at = @At("RETURN"), method = "getModels")
    private void injected(CallbackInfo info) {
        builder.put(SpearEntityModelLayer.BAMBOO_SPEAR, SpearEntityModel.getTexturedModelData());
    }

}
