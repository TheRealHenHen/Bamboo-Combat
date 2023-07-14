package net.bamboo.combat; //By TheRealHenHen

import net.bamboo.combat.config.Config;
import net.bamboo.combat.config.ConfigFile;
import net.bamboo.combat.item.BambooItems;
import net.fabricmc.api.ModInitializer;

public class BambooCombat implements ModInitializer{

    public static final String MODID = "bamboocombat";
    public static Config config;
    
    @Override
    public void onInitialize() {
        ConfigFile.initialize();
        config = ConfigFile.INSTANCE;
        BambooItems.initialize();
    }

}
