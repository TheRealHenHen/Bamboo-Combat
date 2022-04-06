package net.bamboo.combat; //By TheRealHenHen

import net.bamboo.combat.entity.SpearEntity;
import net.bamboo.combat.item.SpearItemMaterial;
import net.bamboo.combat.item.SpearItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer{

    public static final String MODID = "bamboocombat";
    
    public static final EntityType<SpearEntity> BAMBOO_SPEAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MODID, "bamboo_spear"),
			FabricEntityTypeBuilder.<SpearEntity>create(SpawnGroup.MISC, SpearEntity::new)
				.dimensions(EntityDimensions.fixed(0.5F, 0.5F))
				.trackRangeBlocks(4).trackedUpdateRate(10)
				.build()
    );
    
    //(durability, repairIngredient),
    //attackDamage, attackSpeed, throwDistance, fireProof (boolean), spearPierceLevel
    public static final Item Bamboo = new SpearItem(new SpearItemMaterial
        (75, Ingredient.ofItems(Items.BAMBOO)),
        3, 2, 0.6F, 5, false, 0);

    public static final Item Stone = new SpearItem(new SpearItemMaterial
        (125, Ingredient.ofItems(Items.BAMBOO, Items.COBBLESTONE, Items.COBBLED_DEEPSLATE, Items.FLINT)),
        5, 1.6F, 1.6F, 15, false, 1); //3.0

    public static final Item Copper = new SpearItem(new SpearItemMaterial
        (175, Ingredient.ofItems(Items.BAMBOO, Items.COPPER_INGOT)),
        7, 1.2F, 2.1F, 25, false, 3); //8.96

    public static final Item Iron = new SpearItem(new SpearItemMaterial
        (200, Ingredient.ofItems(Items.BAMBOO, Items.IRON_INGOT)),
        6, 1.4F, 2.1F, 20, false, 2); //7.874

    public static final Item Gold = new SpearItem(new SpearItemMaterial
        (50, Ingredient.ofItems(Items.BAMBOO, Items.GOLD_INGOT)),
        7, 0.8F, 2.6F, 35, false, 5); //19.3

    public static final Item Diamond = new SpearItem(new SpearItemMaterial
        (400, Ingredient.ofItems(Items.BAMBOO, Items.DIAMOND)),
        8, 1.6F, 1.6F, 15, false, 2); //3.53
        
    public static final Item Netherite = new SpearItem(new SpearItemMaterial
        (600, Ingredient.ofItems(Items.NETHERITE_INGOT)),
        9, 1, 2.6F, 30, true, 5);
    
    @Override
    public void onInitialize() {

        Registry.register(Registry.ITEM, new Identifier(MODID,"bamboo_spear"), Bamboo); 
        Registry.register(Registry.ITEM, new Identifier(MODID,"stone_bamboo_spear"), Stone); //3.0
        Registry.register(Registry.ITEM, new Identifier(MODID,"copper_bamboo_spear"), Copper); //8.96
        Registry.register(Registry.ITEM, new Identifier(MODID,"iron_bamboo_spear"), Iron); //7.874
        Registry.register(Registry.ITEM, new Identifier(MODID,"golden_bamboo_spear"), Gold); //19.3
        Registry.register(Registry.ITEM, new Identifier(MODID,"diamond_bamboo_spear"), Diamond); //3.53
        Registry.register(Registry.ITEM, new Identifier(MODID,"netherite_bamboo_spear"), Netherite); // 17.112
        
    }

}
