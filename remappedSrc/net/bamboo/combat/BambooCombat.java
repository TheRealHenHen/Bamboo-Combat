package net.bamboo.combat; //By TheRealHenHen

import net.bamboo.combat.item.BambooItems;
import net.fabricmc.api.ModInitializer;

public class BambooCombat implements ModInitializer{

    public static final String MODID = "bamboocombat";
    
    @Override
    public void onInitialize() {
        BambooItems.initialize();
    }

}
