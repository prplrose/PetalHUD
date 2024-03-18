package com.github.prplrose.petalhud.performanceindicators;

public class Tickrate {

    private static float mspt;

    private static Source source = Source.VANILLA;

    public static float get() {
        return mspt;
    }

    public static void set(float ms) {
        mspt = ms;
    }

    public static Source getSource() {
        return source;
    }

    public enum Source {
        VANILLA,
        CARPET
    }

}
