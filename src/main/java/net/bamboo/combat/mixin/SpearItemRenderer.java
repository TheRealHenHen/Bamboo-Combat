package net.bamboo.combat.mixin; //By TheRealHenHen

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.bamboo.combat.BambooCombat;
import net.bamboo.combat.item.BambooItems;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;

@Mixin(ItemRenderer.class)
public class SpearItemRenderer {

	@Shadow
	private ItemModels models;

	BakedModel getTexture(String texture) {
		return models.getModelManager().getModel(new ModelIdentifier(BambooCombat.MODID, texture, "inventory"));
	}

	@ModifyVariable(at = @At("HEAD"), method = "renderItem", argsOnly = true)
	private BakedModel scratch_guiModel(BakedModel defaultModel, ItemStack stack, ModelTransformationMode renderMode) {

		if (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.FIXED || renderMode == ModelTransformationMode.GROUND) {
			if (stack.isOf(BambooItems.BAMBOO_SPEAR))
				return getTexture("bamboo_spear/gui");

			else if (stack.isOf(BambooItems.STONE_BAMBOO_SPEAR))
				return getTexture("stone_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.COPPER_BAMBOO_SPEAR))
				return getTexture("copper_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.IRON_BAMBOO_SPEAR))
				return getTexture("iron_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.GOLDEN_BAMBOO_SPEAR))
				return getTexture("golden_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.DIAMOND_BAMBOO_SPEAR))
				return getTexture("diamond_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.NETHERITE_BAMBOO_SPEAR))
				return getTexture("netherite_bamboo_spear/gui");
		}
		return defaultModel;
	}
}