package net.bamboo.combat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.bamboo.combat.item.spear.SpearItem;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.item.ItemStack;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentTarget {

    @Inject(method = "isAcceptableItem", at = @At(value = "RETURN"), cancellable = true)
    public void isAcceptableItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && itemStack.getItem() instanceof SpearItem) {
            cir.setReturnValue(true);
        }
    }

}
