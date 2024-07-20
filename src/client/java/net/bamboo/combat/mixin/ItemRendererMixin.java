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
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

	@Shadow
	private ItemModels models; 

	BakedModel getTexture(String texture) {
		return models.getModelManager().getModel(Identifier.of(BambooCombat.MODID, texture));
	}

	@ModifyVariable(at = @At("HEAD"), method = "renderItem", argsOnly = true)
	private BakedModel scratch_guiModel(BakedModel model, ItemStack stack, ModelTransformationMode renderMode) {

		if (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.FIXED || renderMode == ModelTransformationMode.GROUND) {

			if (stack.isOf(BambooItems.BAMBOO_SPEAR))
				return getTexture("item/bamboo_spear/gui");

			else if (stack.isOf(BambooItems.STONE_BAMBOO_SPEAR))
				return getTexture("item/stone_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.IRON_BAMBOO_SPEAR))
				return getTexture("item/iron_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.COPPER_BAMBOO_SPEAR))
				return getTexture("item/copper_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.GOLDEN_BAMBOO_SPEAR))
				return getTexture("item/golden_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.DIAMOND_BAMBOO_SPEAR))
				return getTexture("item/diamond_bamboo_spear/gui");

			else if (stack.isOf(BambooItems.NETHERITE_BAMBOO_SPEAR))
				return getTexture("item/netherite_bamboo_spear/gui");

		}
		
		return model;
	}
}