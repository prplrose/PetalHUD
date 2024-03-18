package com.github.prplrose.petalhud;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Color(int rgb) {

    public Color(int a, int r, int g, int b) {
        this(a << 24 | r << 16 | g << 8 | b);
    }

    public Color(int r, int g, int b) {
        this(255, r, g, b);
    }

    public static Color fromHSB(float h, float s, float b) {
        return new Color(java.awt.Color.HSBtoRGB(h, s, b));
    }

    public static Color fromHSB(float[] hsb) {
        return fromHSB(hsb[0], hsb[1], hsb[2]);
    }

    public static Color fromHex(String hex) throws NumberFormatException {
        if (hex.charAt(0) == '#') {
            hex = hex.substring(1);
        }
        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        }
        Matcher matcher = (Pattern.compile("/[^a-f0-9A-F]/g")).matcher(hex);
        if (matcher.groupCount() > 0) {
            return null;
        }
        if (hex.length() == 3 || hex.length() == 4) {
            StringBuilder stringBuilder = new StringBuilder();
            for (char c : hex.toCharArray()) {
                stringBuilder.append(c + c);
            }
            hex = stringBuilder.toString();
        }
        if (hex.length() == 6 || hex.length() == 8) {
            return new Color(Integer.decode("0x" + hex));
        }
        return null;
    }

    public static Color fromString(String string) throws NumberFormatException {
        Color color = fromHex(string);
        if (color != null) {
            return color;
        }
        if (!string.matches("/\\d/g"))
            return null;
        return new Color(Integer.decode(string));
    }

    public Color opaque() {
        return new Color(this.rgb | 0xff000000);
    }

    public int aI() {
        return rgb >> 24;
    }

    public float aF() {
        return this.aI() / 255f;
    }

    public int rI() {
        return rgb >> 16 & 0xff;
    }

    public float rF() {
        return this.rI() / 255f;
    }

    public int gI() {
        return rgb >> 8 & 0xff;
    }

    public float gF() {
        return this.gI() / 255f;
    }

    public int bI() {
        return rgb & 0xff;
    }

    public float bF() {
        return this.bI() / 255f;
    }

    public float[] rgbFA() {
        return new float[]{this.rF(), this.gF(), this.bF()};
    }

    public float[] argbFA() {
        return new float[]{this.aF(), this.rF(), this.gF(), this.bF()};
    }

    public int[] rgbIA() {
        return new int[]{this.rI(), this.gI(), this.bI()};
    }

    public int[] argbIA() {
        return new int[]{this.aI(), this.rI(), this.gI(), this.bI()};
    }

    public float[] hsbFA() {
        return java.awt.Color.RGBtoHSB(this.rI(), this.gI(), this.bI(), null);
    }

    @Override
    public String toString() {
        return ("#" + String.format("%06x", this.rgb & 0x00ffffff));
    }
}
