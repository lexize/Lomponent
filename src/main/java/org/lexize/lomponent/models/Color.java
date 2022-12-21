package org.lexize.lomponent.models;

public class Color {
    private float _r,_g,_b;

    public Color() {
        _r = _g = _b = 1;
    }

    public Color(float r, float g, float b) {
        _r = Math.max(Math.min(r, 1), 0);
        _g = Math.max(Math.min(g, 1), 0);
        _b = Math.max(Math.min(b, 1), 0);
    }
    public Color(int r, int g, int b) {
        _r = Math.max(Math.min(r / 255f, 1), 0);
        _g = Math.max(Math.min(g / 255f, 1), 0);
        _b = Math.max(Math.min(b / 255f, 1), 0);
    }
    public Color(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        _r = Math.max(Math.min(r / 255f, 1), 0);
        _g = Math.max(Math.min(g / 255f, 1), 0);
        _b = Math.max(Math.min(b / 255f, 1), 0);
    }

    public float getR() {
        return _r;
    }
    public float getG() {
        return _g;
    }
    public float getB() {
        return _b;
    }

    public int getRInt() {
        return Math.round(_r * 255);
    }
    public int getGInt() {
        return Math.round(_g * 255);
    }
    public int getBInt() {
        return Math.round(_b * 255);
    }

    public static Color Lerp(Color a, Color b, float w) {
        w = Math.min(1, Math.max(0,w));
        return new Color(a._r + w * (b._r - a._r),a._g + w * (b._g - a._g),a._b + w * (b._b - a._b));
    }

    public static Color fromHSV(float h, float s, float v) {
        float r = 0;
        float g = 0;
        float b = 0;
        if (h < 1f/6) {
            r = 1;
            g = h * 6;
        }
        else if (h < 2f/6) {
            r = 1 - ((h - 1f/6) * 6);
            g = 1;
        }
        else if (h < 3f/6) {
            g = 1;
            b = ((h - 2f/6)*6);
        }
        else if (h < 4f/6) {
            b = 1;
            g = 1 - ((h - 3f/6) * 6);
        }
        else if (h < 5f/6) {
            b = 1;
            r = ((h - 4f/6)*6);
        }
        else {
            r = 1;
            b = 1 - ((h - 5f/6) * 6);
        }
        r = r + ((1-r)*(1-s));
        g = g + ((1-g)*(1-s));
        b = b + ((1-b)*(1-s));
        r *= v;
        g *= v;
        b *= v;
        return new Color(r,g,b);
    }
}
