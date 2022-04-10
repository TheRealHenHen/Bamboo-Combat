package net.bamboo.combat.item;

import net.bamboo.combat.BambooCombat;
import net.bamboo.combat.entity.SpearEntityTypes;
import net.bamboo.combat.item.spear.SpearItem;
import net.bamboo.combat.item.spear.SpearItemMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BambooItems {
    
    
    public static SpearItem BAMBOO;
    public static SpearItem STONE;
    public static SpearItem COPPER;
    public static SpearItem IRON;
    public static SpearItem GOLD;
    public static SpearItem DIAMOND;
    public static SpearItem NETHERITE;

    private static SpearItem register(SpearItem item, String id) {
        return Registry.register(Registry.ITEM, new Identifier(BambooCombat.MODID, id), item);
    }

    public static void initialize() {

        //(durability, repairIngredient),
        //attackDamage, attackSpeed, throwDistance, fireProof (boolean), spearPierceLevel

        BAMBOO = register(new SpearItem(new SpearItemMaterial
            (75, Ingredient.ofItems(Items.BAMBOO)),
            3, 2, 0.6F, 5, false, 0, SpearEntityTypes.BAMBOO), "bamboo_spear");

        STONE = register(new SpearItem(new SpearItemMaterial
            (125, Ingredient.ofItems(Items.BAMBOO, Items.COBBLESTONE, Items.COBBLED_DEEPSLATE, Items.FLINT)),
            5, 1.6F, 1.1F, 15, false, 1, SpearEntityTypes.STONE), "stone_bamboo_spear"); //3.0

        COPPER = register(new SpearItem(new SpearItemMaterial
            (175, Ingredient.ofItems(Items.BAMBOO, Items.COPPER_INGOT)),
            7, 1.2F, 1.6F, 25, false, 3, SpearEntityTypes.BAMBOO), "copper_bamboo_spear"); //8.96

        IRON = register(new SpearItem(new SpearItemMaterial
            (200, Ingredient.ofItems(Items.BAMBOO, Items.IRON_INGOT)),
            6, 1.4F, 1.5F, 20, false, 2, SpearEntityTypes.BAMBOO), "iron_bamboo_spear"); //7.874

        GOLD = register(new SpearItem(new SpearItemMaterial
            (50, Ingredient.ofItems(Items.BAMBOO, Items.GOLD_INGOT)),
            7, 0.8F, 2.1F, 35, false, 5, SpearEntityTypes.BAMBOO), "golden_bamboo_spear"); //19.3

        DIAMOND = register(new SpearItem(new SpearItemMaterial
            (400, Ingredient.ofItems(Items.BAMBOO, Items.DIAMOND)),
            8, 1.6F, 1.3F, 15, false, 2, SpearEntityTypes.BAMBOO), "diamond_bamboo_spear"); //3.53
            
        NETHERITE = register(new SpearItem(new SpearItemMaterial
            (600, Ingredient.ofItems(Items.NETHERITE_INGOT)),
            9, 1, 2.1F, 30, true, 5, SpearEntityTypes.BAMBOO), "netherite_bamboo_spear");

    }

}
