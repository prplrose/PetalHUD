package com.github.prplrose.petalhud.settings;

import net.minecraft.nbt.NbtCompound;

public class CrosshairSettings implements Settings {
    public static boolean vanilla = false;
    public static float[] x_color = {0.0f, 1.0f, 1.0f};
    public static float[] y_color = {0.5f, 0.5f, 1.0f};
    public static float[] z_color = {0.0f, 1.0f, 0.0f};

    public static NbtCompound write() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putBoolean("vanilla", vanilla);
        nbtCompound.putInt("x_color", Settings.intFromColorArray(x_color));
        nbtCompound.putInt("y_color", Settings.intFromColorArray(y_color));
        nbtCompound.putInt("z_color", Settings.intFromColorArray(z_color));

        return nbtCompound;
    }

    public static void read(NbtCompound nbtCompound) {
        if (nbtCompound.contains("vanilla"))
            vanilla = nbtCompound.getBoolean("vanilla");
        if (nbtCompound.contains("x_color"))
            x_color = Settings.colorArrayFromInt(nbtCompound.getInt("x_color"));
        if (nbtCompound.contains("y_color"))
            y_color = Settings.colorArrayFromInt(nbtCompound.getInt("y_color"));
        if (nbtCompound.contains("z_color"))
            z_color = Settings.colorArrayFromInt(nbtCompound.getInt("z_color"));
    }


}
