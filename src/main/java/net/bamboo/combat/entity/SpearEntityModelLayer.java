package net.bamboo.combat.entity;

import java.util.Set;

import com.google.common.collect.Sets;

import net.bamboo.combat.Main;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class SpearEntityModelLayer extends EntityModelLayers {
    
    public static final EntityModelLayer BAMBOO_SPEAR = registerMain("bamboo_spear");
    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();

    private static EntityModelLayer registerMain(String id) {
        return register(id, "main");
    }

    private static EntityModelLayer register(String id, String layer) {
        EntityModelLayer entityModelLayer = create(id, layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        }
        return entityModelLayer;
    }

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(new Identifier(Main.MODID, id), layer);
    }

}
