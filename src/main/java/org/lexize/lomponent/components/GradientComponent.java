package org.lexize.lomponent.components;

import org.lexize.lomponent.LomponentStyle;
import org.lexize.lomponent.models.Color;

import java.util.NavigableMap;
import java.util.TreeMap;

public class GradientComponent extends GroupComponent implements ColorizedComponent{
    TreeMap<Float, Color> _gradientElements;
    public GradientComponent(TreeMap<Float, Color> elements) {
        _gradientElements = elements;
    }
    @Override
    public Color getColorAt(float i) {
        float fk = _gradientElements.floorKey(i);
        float sk = _gradientElements.ceilingKey(i);
        float progress = (i - fk) / (sk - fk);
        if (Float.isNaN(progress)) progress = 0;
        return Color.Lerp(_gradientElements.get(fk), _gradientElements.get(sk), progress);
    }

    @Override
    public void onStyleGet(LomponentStyle style, float relativePos) {
        style.addStyle("color", getColorAt(relativePos / getLength()));
    }
}
