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

public class BambooItems {

        public static SpearItem BAMBOO_SPEAR;
        public static SpearItem STONE_BAMBOO_SPEAR;
        public static SpearItem COPPER_BAMBOO_SPEAR;
        public static SpearItem IRON_BAMBOO_SPEAR;
        public static SpearItem GOLDEN_BAMBOO_SPEAR;
        public static SpearItem DIAMOND_BAMBOO_SPEAR;
        public static SpearItem NETHERITE_BAMBOO_SPEAR;

        private static SpearItem register(String id, SpearItem item) {
                SpearItem spear = Registry.register(Registries.ITEM, new Identifier(BambooCombat.MODID, id), item);
                ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.addBefore(Items.SHIELD, spear));
                return spear;
        }

        public static void initialize() {

                BAMBOO_SPEAR = register("bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO),
                        BambooCombat.config.bambooSpear.durability),
                        BambooCombat.config.bambooSpear.attackDamage,
                        BambooCombat.config.bambooSpear.attackSpeed,
                        BambooCombat.config.bambooSpear.throwDistance,
                        BambooCombat.config.bambooSpear.dragInWater,
                        BambooCombat.config.bambooSpear.throwDelay,
                        BambooCombat.config.bambooSpear.pierceLevel,
                        BambooCombat.config.bambooSpear.burnTicks,
                        BambooCombat.config.bambooSpear.durabilityDecreaseAfterThrown,
                        BambooCombat.config.bambooSpear.throwDamageDecreaseAfterPierce,
                        BambooCombat.config.bambooSpear.canCriticalThrow,
                        SpearEntityTypes.BAMBOO_SPEAR, new Item.Settings()));

                STONE_BAMBOO_SPEAR = register("stone_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.COBBLESTONE, Items.COBBLED_DEEPSLATE, Items.BLACKSTONE, Items.FLINT),
                        BambooCombat.config.stoneBambooSpear.durability), 
                        BambooCombat.config.stoneBambooSpear.attackDamage,
                        BambooCombat.config.stoneBambooSpear.attackSpeed,
                        BambooCombat.config.stoneBambooSpear.throwDistance,
                        BambooCombat.config.stoneBambooSpear.dragInWater,
                        BambooCombat.config.stoneBambooSpear.throwDelay,
                        BambooCombat.config.stoneBambooSpear.pierceLevel,
                        BambooCombat.config.stoneBambooSpear.burnTicks,
                        BambooCombat.config.stoneBambooSpear.durabilityDecreaseAfterThrown,
                        BambooCombat.config.stoneBambooSpear.throwDamageDecreaseAfterPierce,
                        BambooCombat.config.stoneBambooSpear.canCriticalThrow,
                        SpearEntityTypes.STONE_BAMBOO_SPEAR, new Item.Settings()));
            
                IRON_BAMBOO_SPEAR = register("iron_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.IRON_INGOT),
                        BambooCombat.config.ironBambooSpear.durability),
                        BambooCombat.config.ironBambooSpear.attackDamage,
                        BambooCombat.config.ironBambooSpear.attackSpeed,
                        BambooCombat.config.ironBambooSpear.throwDistance,
                        BambooCombat.config.ironBambooSpear.dragInWater,
                        BambooCombat.config.ironBambooSpear.throwDelay,
                        BambooCombat.config.ironBambooSpear.pierceLevel,
                        BambooCombat.config.ironBambooSpear.burnTicks,
                        BambooCombat.config.ironBambooSpear.durabilityDecreaseAfterThrown,
                        BambooCombat.config.ironBambooSpear.throwDamageDecreaseAfterPierce,
                        BambooCombat.config.ironBambooSpear.canCriticalThrow,
                        SpearEntityTypes.IRON_BAMBOO_SPEAR, new Item.Settings()));
        
                COPPER_BAMBOO_SPEAR = register("copper_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.COPPER_INGOT),
                        BambooCombat.config.copperBambooSpear.durability),
                        BambooCombat.config.copperBambooSpear.attackDamage,
                        BambooCombat.config.copperBambooSpear.attackSpeed,
                        BambooCombat.config.copperBambooSpear.throwDistance,
                        BambooCombat.config.copperBambooSpear.dragInWater,
                        BambooCombat.config.copperBambooSpear.throwDelay,
                        BambooCombat.config.copperBambooSpear.pierceLevel,
                        BambooCombat.config.copperBambooSpear.burnTicks,
                        BambooCombat.config.copperBambooSpear.durabilityDecreaseAfterThrown,
                        BambooCombat.config.copperBambooSpear.throwDamageDecreaseAfterPierce,
                        BambooCombat.config.copperBambooSpear.canCriticalThrow,
                        SpearEntityTypes.COPPER_BAMBOO_SPEAR, new Item.Settings()));
        
                GOLDEN_BAMBOO_SPEAR = register("golden_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.GOLD_INGOT),
                        BambooCombat.config.goldenBambooSpear.durability),
                        BambooCombat.config.goldenBambooSpear.attackDamage,
                        BambooCombat.config.goldenBambooSpear.attackSpeed,
                        BambooCombat.config.goldenBambooSpear.throwDistance,
                        BambooCombat.config.goldenBambooSpear.dragInWater,
                        BambooCombat.config.goldenBambooSpear.throwDelay,
                        BambooCombat.config.goldenBambooSpear.pierceLevel,
                        BambooCombat.config.goldenBambooSpear.burnTicks,
                        BambooCombat.config.goldenBambooSpear.durabilityDecreaseAfterThrown,
                        BambooCombat.config.goldenBambooSpear.throwDamageDecreaseAfterPierce,
                        BambooCombat.config.goldenBambooSpear.canCriticalThrow,
                        SpearEntityTypes.GOLDEN_BAMBOO_SPEAR, new Item.Settings()));
                        
                DIAMOND_BAMBOO_SPEAR = register("diamond_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.BAMBOO, Items.DIAMOND),
                        BambooCombat.config.diamondBambooSpear.durability),
                        BambooCombat.config.diamondBambooSpear.attackDamage,
                        BambooCombat.config.diamondBambooSpear.attackSpeed,
                        BambooCombat.config.diamondBambooSpear.throwDistance,
                        BambooCombat.config.diamondBambooSpear.dragInWater,
                        BambooCombat.config.diamondBambooSpear.throwDelay,
                        BambooCombat.config.diamondBambooSpear.pierceLevel,
                        BambooCombat.config.diamondBambooSpear.burnTicks,
                        BambooCombat.config.diamondBambooSpear.durabilityDecreaseAfterThrown,
                        BambooCombat.config.diamondBambooSpear.throwDamageDecreaseAfterPierce,
                        BambooCombat.config.diamondBambooSpear.canCriticalThrow,
                        SpearEntityTypes.DIAMOND_BAMBOO_SPEAR, new Item.Settings()));
                    
                NETHERITE_BAMBOO_SPEAR = register("netherite_bamboo_spear", new SpearItem(new SpearItemMaterial(
                        Ingredient.ofItems(Items.NETHERITE_INGOT),
                        BambooCombat.config.netheriteBambooSpear.durability),
                        BambooCombat.config.netheriteBambooSpear.attackDamage,
                        BambooCombat.config.netheriteBambooSpear.attackSpeed,
                        BambooCombat.config.netheriteBambooSpear.throwDistance,
                        BambooCombat.config.netheriteBambooSpear.dragInWater,
                        BambooCombat.config.netheriteBambooSpear.throwDelay,
                        BambooCombat.config.netheriteBambooSpear.pierceLevel,
                        BambooCombat.config.netheriteBambooSpear.burnTicks,
                        BambooCombat.config.netheriteBambooSpear.durabilityDecreaseAfterThrown,
                        BambooCombat.config.netheriteBambooSpear.throwDamageDecreaseAfterPierce,
                        BambooCombat.config.netheriteBambooSpear.canCriticalThrow,
                        SpearEntityTypes.NETHERITE_BAMBOO_SPEAR, new Item.Settings().fireproof()));

        }

}
