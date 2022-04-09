package net.bamboo.combat.entity; //By TheRealHenHen

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class SpearEntityModelLayers {

    private static final String MAIN = "main";
    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();
    public static final EntityModelLayer BAMBOO_SPEAR = registerMain("bamboo_spear");
    public static final EntityModelLayer STONE_BAMBOO_SPEAR = registerMain("stone_bamboo_spear");

    private static EntityModelLayer registerMain(String id) {
        return register(id, MAIN);
    }

    private static EntityModelLayer register(String id, String layer) {
        EntityModelLayer entityModelLayer = create(id, layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        }
        return entityModelLayer;
    }

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(new Identifier("bamboo_spear", id), layer);
    }
    
}
