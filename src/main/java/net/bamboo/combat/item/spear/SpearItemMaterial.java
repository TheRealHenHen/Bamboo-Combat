package net.bamboo.combat.item.spear; //By TheRealHenHen

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public class SpearItemMaterial implements ToolMaterial {

    private int durability;
    private Ingredient repairIngredient;
    private TagKey<Block> inverseTag;

    public SpearItemMaterial(Ingredient repairIngredient, int durability, TagKey<Block> inverseTag) {
        this.durability = durability;
        this.repairIngredient = repairIngredient;
        this.inverseTag = inverseTag;
    }

    @Override
    public float getAttackDamage() {
        return -1.0F;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 1;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }
    
}