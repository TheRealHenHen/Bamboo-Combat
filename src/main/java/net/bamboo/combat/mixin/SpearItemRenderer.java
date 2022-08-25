package net.bamboo.combat.mixin; //By TheRealHenHen

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.bamboo.combat.item.BambooItems;
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

		if (renderMode == Mode.GUI || renderMode == Mode.FIXED || renderMode == Mode.GROUND) {
			if (stack.isOf(BambooItems.BAMBOO_SPEAR))
				return getTexture("bamboocombat:bamboo_spear/gui#inventory");

			else if (stack.isOf(BambooItems.STONE_BAMBOO_SPEAR))
				return getTexture("bamboocombat:stone_bamboo_spear/gui#inventory");

			else if (stack.isOf(BambooItems.COPPER_BAMBOO_SPEAR))
	 			return getTexture("bamboocombat:copper_bamboo_spear/gui#inventory");

			else if (stack.isOf(BambooItems.IRON_BAMBOO_SPEAR))
				return getTexture("bamboocombat:iron_bamboo_spear/gui#inventory");

			else if (stack.isOf(BambooItems.GOLDEN_BAMBOO_SPEAR))
				return getTexture("bamboocombat:golden_bamboo_spear/gui#inventory");

			else if (stack.isOf(BambooItems.DIAMOND_BAMBOO_SPEAR))
				return getTexture("bamboocombat:diamond_bamboo_spear/gui#inventory");
				
			else if (stack.isOf(BambooItems.NETHERITE_BAMBOO_SPEAR))
				return getTexture("bamboocombat:netherite_bamboo_spear/gui#inventory");
		}
		return defaultModel;
    }
}