package com.github.prplrose.petalhud.settings;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.ColorHelper;

public interface Settings {


    static float[] colorArrayFromInt(int ARGB) {
        return new float[]{
                ColorHelper.Argb.getRed(ARGB) / 255f,
                ColorHelper.Argb.getGreen(ARGB) / 255f,
                ColorHelper.Argb.getBlue(ARGB) / 255f,
        };
    }

    static int intFromColorArray(float[] colorArray) {
        return ColorHelper.Argb.getArgb(
                255,
                (int) (255 * colorArray[0]),
                (int) (255 * colorArray[1]),
                (int) (255 * colorArray[2])
        );
    }

    static NbtCompound write() {
        return new NbtCompound();
    }

    static void read(NbtCompound nbtCompound) {
    }

}
