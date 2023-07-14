package net.bamboo.combat.item; //By TheRealHenHen

import net.bamboo.combat.BambooCombat;
import net.bamboo.combat.entity.spear.SpearEntityTypes;
import net.bamboo.combat.item.spear.SpearItem;
import net.bamboo.combat.item.spear.SpearItemMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BambooItems {

        public static SpearItem BAMBOO_SPEAR;
        public static SpearItem STONE_BAMBOO_SPEAR;
        public static SpearItem COPPER_BAMBOO_SPEAR;
        public static SpearItem IRON_BAMBOO_SPEAR;
        public static SpearItem GOLDEN_BAMBOO_SPEAR;
        public static SpearItem DIAMOND_BAMBOO_SPEAR;
        public static SpearItem NETHERITE_BAMBOO_SPEAR;

        private static SpearItem register(String id, SpearItem item) {
                return Registry.register(Registry.ITEM, new Identifier(BambooCombat.MODID, id), item);
        }

        public static void initialize() {

                BAMBOO_SPEAR = register("bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO), BambooCombat.config.bambooSpear.durability),
                        BambooCombat.config.bambooSpear, SpearEntityTypes.BAMBOO_SPEAR, new Item.Settings()));

                STONE_BAMBOO_SPEAR = register("stone_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.COBBLESTONE, Items.COBBLED_DEEPSLATE, Items.BLACKSTONE, Items.FLINT),
                        BambooCombat.config.stoneBambooSpear.durability), BambooCombat.config.stoneBambooSpear, SpearEntityTypes.STONE_BAMBOO_SPEAR, new Item.Settings()));
            
                IRON_BAMBOO_SPEAR = register("iron_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.IRON_INGOT), BambooCombat.config.ironBambooSpear.durability),
                        BambooCombat.config.ironBambooSpear, SpearEntityTypes.IRON_BAMBOO_SPEAR, new Item.Settings()));
        
                COPPER_BAMBOO_SPEAR = register("copper_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.COPPER_INGOT), BambooCombat.config.copperBambooSpear.durability),
                        BambooCombat.config.copperBambooSpear, SpearEntityTypes.COPPER_BAMBOO_SPEAR, new Item.Settings()));
        
                GOLDEN_BAMBOO_SPEAR = register("golden_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.GOLD_INGOT), BambooCombat.config.goldenBambooSpear.durability),
                        BambooCombat.config.goldenBambooSpear, SpearEntityTypes.GOLDEN_BAMBOO_SPEAR, new Item.Settings()));
                        
                DIAMOND_BAMBOO_SPEAR = register("diamond_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.DIAMOND), BambooCombat.config.diamondBambooSpear.durability),
                        BambooCombat.config.diamondBambooSpear, SpearEntityTypes.DIAMOND_BAMBOO_SPEAR, new Item.Settings()));
                    
                NETHERITE_BAMBOO_SPEAR = register("netherite_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.NETHERITE_INGOT), BambooCombat.config.netheriteBambooSpear.durability),
                        BambooCombat.config.netheriteBambooSpear, SpearEntityTypes.NETHERITE_BAMBOO_SPEAR, new Item.Settings().fireproof()));

        }

}
