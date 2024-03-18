package com.github.prplrose.petalhud;

import com.github.prplrose.petalhud.settings.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetalHUD implements ModInitializer {

    public static Logger LOGGER = LoggerFactory.getLogger("Petal HUD");
    public static final String MODID = "petalhud";
    public static CarpetMod CARPET_MOD;

    @Override
    public void onInitialize() {

        ConfigManager.loadConfig();

        boolean isCarpetInstalled = FabricLoader.getInstance().isModLoaded("carpet");
        if (isCarpetInstalled) {
            CARPET_MOD = new CarpetMod();
        }
    }


}
