package net.bamboo.combat.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.bamboo.combat.item.spear.SpearItem;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.LoyaltyEnchantment;
import net.minecraft.item.ItemStack;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Shadow @Final public EnchantmentTarget type;
    
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> callback) {

        Enchantment enchantment = (Enchantment) (Object) this;

        if (enchantment instanceof DamageEnchantment) {
            if (!(stack.getItem() instanceof SpearItem)) {
                return;
            }
            callback.setReturnValue(true);
        } else if (enchantment instanceof LoyaltyEnchantment) {
            if (!(stack.getItem() instanceof SpearItem)) {
                return;
            }
            callback.setReturnValue(true);
        }

    }

}
