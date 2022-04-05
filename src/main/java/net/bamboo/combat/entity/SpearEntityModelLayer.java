package net.bamboo.combat.entity;

import java.util.Set;

import com.google.common.collect.Sets;

import org.spongepowered.asm.mixin.Shadow;

import net.bamboo.combat.Main;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class SpearEntityModelLayer extends EntityModelLayers {
    
    @Shadow private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();

    @Shadow private static EntityModelLayer registerMain(String id) {
        return register(id, "main");
    }

    @Shadow private static EntityModelLayer register(String id, String layer) {
        EntityModelLayer entityModelLayer = create(id, layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        }
        return entityModelLayer;
    }

    @Shadow private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(new Identifier(Main.MODID, id), layer);
    }

    public static final EntityModelLayer BAMBOO_SPEAR = registerMain("bamboo_spear");
    
}
