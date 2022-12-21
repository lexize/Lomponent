package org.lexize.lomponent.utils;

import org.lexize.lomponent.LomponentUtils;
import org.lexize.lomponent.models.Color;

import java.util.HashMap;
import java.util.HexFormat;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorResolver {
    private static final HashMap<String, Function<Matcher, Color>> _colorFormatsToComponents = new HashMap<>() {{
        put("^#(?<hex>[a-fA-F0-9]{6})$", (m) -> {
            String hexValue = m.group("hex");
            byte[] hVals = LomponentUtils.fromHex(hexValue);
            return new Color(hVals[0] & 0xFF,hVals[1] & 0xFF,hVals[2] & 0xFF);
        });
        put("^#(?<hex>[a-fA-F0-9]{3})$", (m) -> {
            String hexValue = m.group("hex");
            int shortHex = HexFormat.fromHexDigits(hexValue);
            int r = (int) ((((shortHex >> 8) & 0xF)/15f)*255);
            int g = (int) ((((shortHex >> 4) & 0xF)/15f)*255);
            int b = (int) (((shortHex & 0xF       )/15f)*255);
            return new Color(r,g,b);
        });
        put("^rgb\\(\\s*(?<r>\\d+)\\s*,\\s*(?<g>\\d+)\\s*,\\s*(?<b>\\d+)\\s*\\)$", (m) -> {
            int r,g,b;
            r = Integer.parseInt(m.group("r"));
            g = Integer.parseInt(m.group("g"));
            b = Integer.parseInt(m.group("b"));
            return new Color(r,g,b);
        });
        put("^rgb\\(\\s*(?<r>\\d+\\.\\d+)\\s*,\\s*(?<g>\\d+\\.\\d+)\\s*,\\s*(?<b>\\d+\\.\\d+)\\s*\\)$", (m) -> {
            return new Color(
                    Float.parseFloat(m.group("r")),
                    Float.parseFloat(m.group("g")),
                    Float.parseFloat(m.group("b"))
            );
        });
        put("^black$", (m) -> new Color(0));
        put("^dark_blue", (m) -> new Color(0x0000AA));
        put("^dark_green$", (m) -> new Color(0x00AA00));
        put("^dark_aqua$", (m) -> new Color(0x00AAAA));
        put("^dark_red$", (m) -> new Color(0xAA0000));
        put("^dark_purple$", (m) -> new Color(0xAA00AA));
        put("^gold$", (m) -> new Color(0xFFAA00));
        put("^gray$", (m) -> new Color(0xAAAAAA));
        put("^dark_gray$", (m) -> new Color(0x555555));
        put("^blue$", (m) -> new Color(0x5555FF));
        put("^green$", (m) -> new Color(0x55FF55));
        put("^aqua$", (m) -> new Color(0x55FFFF));
        put("^red$", (m) -> new Color(0xFF5555));
        put("^light_purple$", (m) -> new Color(0xFF55FF));
        put("^yellow$", (m) -> new Color(0xFFFF55));
        put("^white$", (m) -> new Color(0xFFFFFF));
    }};
    public static Color getColorFromString(String source) {
        for (String k :
                _colorFormatsToComponents.keySet()) {
            Matcher m = Pattern.compile(k).matcher(source);
            if (m.matches()) {
                var f = _colorFormatsToComponents.get(k);
                return f.apply(m);
            }
        }
        return null;
    }

    public static boolean matchesAnyColorPattern(String source) {
        for (String k :
                _colorFormatsToComponents.keySet()) {
            Matcher m = Pattern.compile(k).matcher(source);
            if (m.matches()) return true;
        }
        return false;
    }
}
