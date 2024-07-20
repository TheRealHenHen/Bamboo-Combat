package net.bamboo.combat.item; //By TheRealHenHen

import net.bamboo.combat.BambooCombat;
import net.bamboo.combat.entity.spear.SpearEntityTypes;
import net.bamboo.combat.item.spear.SpearItem;
import net.bamboo.combat.item.spear.SpearItemMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;

public class BambooItems {

        public static SpearItem BAMBOO_SPEAR;
        public static SpearItem STONE_BAMBOO_SPEAR;
        public static SpearItem COPPER_BAMBOO_SPEAR;
        public static SpearItem IRON_BAMBOO_SPEAR;
        public static SpearItem GOLDEN_BAMBOO_SPEAR;
        public static SpearItem DIAMOND_BAMBOO_SPEAR;
        public static SpearItem NETHERITE_BAMBOO_SPEAR;

        private static SpearItem register(String id, SpearItem item) {
                SpearItem spear = Registry.register(Registries.ITEM, Identifier.of(BambooCombat.MODID, id), item);
                ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.addBefore(Items.MACE, spear));
                return spear;
        }

        public static void initialize() {

                BAMBOO_SPEAR = register("bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO), BambooCombat.config.bambooSpear.durability, BlockTags.INCORRECT_FOR_WOODEN_TOOL),
                        BambooCombat.config.bambooSpear, SpearEntityTypes.BAMBOO_SPEAR, new Item.Settings()));

                STONE_BAMBOO_SPEAR = register("stone_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.COBBLESTONE, Items.COBBLED_DEEPSLATE, Items.BLACKSTONE, Items.FLINT), BambooCombat.config.stoneBambooSpear.durability, BlockTags.INCORRECT_FOR_STONE_TOOL),
                        BambooCombat.config.stoneBambooSpear, SpearEntityTypes.STONE_BAMBOO_SPEAR, new Item.Settings()));
            
                IRON_BAMBOO_SPEAR = register("iron_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.IRON_INGOT), BambooCombat.config.ironBambooSpear.durability, BlockTags.INCORRECT_FOR_IRON_TOOL),
                        BambooCombat.config.ironBambooSpear, SpearEntityTypes.IRON_BAMBOO_SPEAR, new Item.Settings()));
        
                COPPER_BAMBOO_SPEAR = register("copper_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.COPPER_INGOT), BambooCombat.config.copperBambooSpear.durability, BlockTags.INCORRECT_FOR_IRON_TOOL),
                        BambooCombat.config.copperBambooSpear, SpearEntityTypes.COPPER_BAMBOO_SPEAR, new Item.Settings()));
        
                GOLDEN_BAMBOO_SPEAR = register("golden_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.GOLD_INGOT), BambooCombat.config.goldenBambooSpear.durability, BlockTags.INCORRECT_FOR_GOLD_TOOL),
                        BambooCombat.config.goldenBambooSpear, SpearEntityTypes.GOLDEN_BAMBOO_SPEAR, new Item.Settings()));
                        
                DIAMOND_BAMBOO_SPEAR = register("diamond_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.DIAMOND), BambooCombat.config.diamondBambooSpear.durability, BlockTags.INCORRECT_FOR_DIAMOND_TOOL),
                        BambooCombat.config.diamondBambooSpear, SpearEntityTypes.DIAMOND_BAMBOO_SPEAR, new Item.Settings()));
                    
                NETHERITE_BAMBOO_SPEAR = register("netherite_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.NETHERITE_INGOT), BambooCombat.config.netheriteBambooSpear.durability, BlockTags.INCORRECT_FOR_NETHERITE_TOOL),
                        BambooCombat.config.netheriteBambooSpear, SpearEntityTypes.NETHERITE_BAMBOO_SPEAR, new Item.Settings().fireproof()));

        }

}
