package org.lexize.lomponent.components;

import org.lexize.lomponent.LomponentStyle;
import org.lexize.lomponent.models.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorComponent extends GroupComponent implements ColorizedComponent {
    private int _r;
    private int _g;
    private int _b;

    private Color _c;

    public ColorComponent(Color c) {
        _c = c;
    }

    public int getR() {
        return _r;
    }
    public int getG() {
        return _g;
    }
    public int getB() {
        return _b;
    }

    @Override
    public String toString() {
        return "Color component: %s %s %s".formatted(getR(), getG(), getB());
    }
    @Override
    public Color getColorAt(float i) {
        return _c;
    }

    @Override
    public void onStyleGet(LomponentStyle style, float relativePos) {
        super.onStyleGet(style, relativePos);
        style.addStyle("color", getColorAt(relativePos));
    }
}
