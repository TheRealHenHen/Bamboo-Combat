package net.bamboo.combat.mixin;

import net.bamboo.combat.item.spear.SpearItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class SpearEnchantmentTarget {

    @Shadow @Final public EnchantmentTarget type;

    @Inject(method = "isAcceptableItem", at = @At(value = "RETURN"), cancellable = true)
    public void isAcceptableItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && itemStack.getItem() instanceof SpearItem && type == EnchantmentTarget.TRIDENT) {
            cir.setReturnValue(true);
        }
    }

}
