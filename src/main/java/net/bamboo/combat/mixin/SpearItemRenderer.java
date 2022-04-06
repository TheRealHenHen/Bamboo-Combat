/*package net.bamboo.combat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.bamboo.combat.Main;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;

@Mixin(ItemRenderer.class)
public class SpearItemRenderer {

    @Shadow private ItemModels models;

	BakedModel getTexture(String texture) {
		return models.getModelManager().getModel(new ModelIdentifier(texture));
	}

    @ModifyVariable(at = @At("HEAD"), method = "renderItem", argsOnly = true)
    private BakedModel scratch_guiModel(BakedModel defaultModel, ItemStack stack, ModelTransformation.Mode renderMode) {

		if ((renderMode == Mode.GUI) || (renderMode == Mode.FIXED)) {
			if (stack.isOf(Main.Bamboo))
				return getTexture("bamboospear:bamboo_spear#inventory");

			else if (stack.isOf(Main.Stone))
				return getTexture("bamboospear:bamboo_spear#inventory");

			else if (stack.isOf(Main.Copper))
	 			return getTexture("minecraft:bamboo#inventory");

			else if (stack.isOf(Main.Iron))
				return getTexture("minecraft:bamboo#inventory");

			else if (stack.isOf(Main.Gold))
				return getTexture("minecraft:bamboo#inventory");

			else if (stack.isOf(Main.Diamond))
				return getTexture("minecraft:bamboo#inventory");
				
			else if (stack.isOf(Main.Netherite))
				return getTexture("minecraft:bamboo#inventory");
		}
		return defaultModel;
    }
}*/