package net.bamboo.combat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.bamboo.combat.item.spear.SpearItem;
import net.minecraft.enchantment.LoyaltyEnchantment;
import net.minecraft.item.ItemStack;

@Mixin(LoyaltyEnchantment.class)
public class LoyaltyEnchantmentTarget extends EnchantmentTarget {

    @Override
    public void isAcceptableItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && itemStack.getItem() instanceof SpearItem) {
            cir.setReturnValue(true);
        }
    }

}
