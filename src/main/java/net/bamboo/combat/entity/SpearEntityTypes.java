package net.bamboo.combat.entity; //By TheRealHenHen

import net.bamboo.combat.BambooCombat;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpearEntityTypes {
    
    private static EntityType<SpearEntity> registerEntity(String id) {
        return Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(BambooCombat.MODID, id),
			FabricEntityTypeBuilder.<SpearEntity>create(SpawnGroup.MISC, SpearEntity::new)
				.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
				.trackRangeBlocks(4).trackedUpdateRate(10)
				.build()
        );
    }
    
    public static final EntityType<SpearEntity> BAMBOO = registerEntity("bamboo_spear");
    public static final EntityType<SpearEntity> STONE = registerEntity("stone_bamboo_spear");
    public static final EntityType<SpearEntity> COPPER = registerEntity("copper_bamboo_spear");
    public static final EntityType<SpearEntity> IRON = registerEntity("iron_bamboo_spear");
    public static final EntityType<SpearEntity> GOLD = registerEntity("golden_bamboo_spear");
    public static final EntityType<SpearEntity> DIAMOND = registerEntity("diamond_bamboo_spear");
    public static final EntityType<SpearEntity> NETHERITE = registerEntity("netherite_bamboo_spear");

}
