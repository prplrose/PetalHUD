package com.github.prplrose.petalhud.settings;

import com.github.prplrose.petalhud.PetalHUD;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConfigManager {

    private static final File configFile = FabricLoader.getInstance().getConfigDir().resolve("petalhud-config.json").toFile();
    static Logger LOGGER = PetalHUD.LOGGER;

    public static void loadConfig() {
        try {
            Scanner scanner = new Scanner(configFile);
        } catch (FileNotFoundException e) {
            LOGGER.info("Config file not found. Generating one.");
            writeConfig();
        }
    }

    public static void writeConfig() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("crosshair_settings", CrosshairSettings.write());
        try {
            PrintWriter writer = new PrintWriter(configFile, StandardCharsets.UTF_8);
            writer.write(NbtHelper.toFormattedString(nbtCompound));
            writer.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }


}
