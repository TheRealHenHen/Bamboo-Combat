package net.bamboo.combat.item.spear; //By TheRealHenHen

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class SpearItemMaterial implements ToolMaterial {

    private int durability;
    private Ingredient repairIngredient;

    public SpearItemMaterial(Ingredient repairIngredient, int durability) {
        this.durability = durability;
        this.repairIngredient = repairIngredient;
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
    public int getMiningLevel() {
        return 1;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 1;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }
    
}