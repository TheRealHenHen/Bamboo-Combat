package net.bamboo.combat.item; //By TheRealHenHen

import net.bamboo.combat.BambooCombat;
import net.bamboo.combat.entity.spear.SpearEntityTypes;
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

        BAMBOO = register(new SpearItem(new SpearItemMaterial
            (100, Ingredient.ofItems(Items.BAMBOO)),
            5, 1.6F, 1.2F, 0.9F, 3, 0, 0, SpearEntityTypes.BAMBOO), "bamboo_spear");

        STONE = register(new SpearItem(new SpearItemMaterial
            (200, Ingredient.ofItems(Items.BAMBOO, Items.COBBLESTONE, Items.COBBLED_DEEPSLATE, Items.BLACKSTONE, Items.FLINT)),
            6, 1.4F, 1.5F, 0.92F, 6, 1, 5, SpearEntityTypes.STONE), "stone_bamboo_spear");

        IRON = register(new SpearItem(new SpearItemMaterial
            (400, Ingredient.ofItems(Items.BAMBOO, Items.IRON_INGOT)),
            7, 1.4F, 1.7F, 0.94F, 8, 2, 20, SpearEntityTypes.IRON), "iron_bamboo_spear");

        GOLD = register(new SpearItem(new SpearItemMaterial
            (100, Ingredient.ofItems(Items.BAMBOO, Items.GOLD_INGOT)),
            7, 1.2F, 2.4F, 0.98F, 17, 3, 5, SpearEntityTypes.GOLD), "golden_bamboo_spear");

        COPPER = register(new SpearItem(new SpearItemMaterial
            (350, Ingredient.ofItems(Items.BAMBOO, Items.COPPER_INGOT)),
            8, 1.1F, 2.0F, 0.95F, 11, 2, 15, SpearEntityTypes.COPPER), "copper_bamboo_spear");

        DIAMOND = register(new SpearItem(new SpearItemMaterial
            (1000, Ingredient.ofItems(Items.BAMBOO, Items.DIAMOND)),
            8, 1.3F, 2.1F, 0.96F, 11, 4, 40, SpearEntityTypes.DIAMOND), "diamond_bamboo_spear");
            
        NETHERITE = register(new SpearItem(new SpearItemMaterial
            (1500, Ingredient.ofItems(Items.NETHERITE_INGOT)),
            9, 1.1F, 2.7F, 1.0F, 13, 5, 0, SpearEntityTypes.NETHERITE), "netherite_bamboo_spear");

    }

}
