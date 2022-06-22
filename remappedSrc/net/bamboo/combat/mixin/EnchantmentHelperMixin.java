package net.bamboo.combat.mixin;

import java.util.Iterator;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.bamboo.combat.item.spear.SpearItem;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    
    @Inject(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;", shift = Shift.BY, by = 3), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void spearEnchantments(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<?>> info,
        List<EnchantmentLevelEntry> entries, Item item, boolean isBook, Iterator<?> enchantmentsIterator, Enchantment enchantment) {

        if ((!(enchantment instanceof DamageEnchantment) && (enchantment != Enchantments.LOYALTY)) || !(stack.getItem() instanceof SpearItem)) {
            return;
        }

        for(int level = enchantment.getMaxLevel(); level > enchantment.getMinLevel() - 1; --level) {
            if (power >= enchantment.getMinPower(level) && power <= enchantment.getMaxPower(level)) {
                entries.add(new EnchantmentLevelEntry(enchantment, level));
                break;
            }
        }

    }

}
