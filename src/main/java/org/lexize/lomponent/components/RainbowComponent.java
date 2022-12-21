package org.lexize.lomponent.components;

import org.lexize.lomponent.LomponentStyle;
import org.lexize.lomponent.models.Color;


public class RainbowComponent extends GroupComponent implements ColorizedComponent{
    private float _saturation;
    private float _brightness;
    public RainbowComponent() {
        _saturation = 1;
        _brightness = 1;
    }

    public RainbowComponent(float saturation, float brightness) {
        _saturation = saturation;
        _brightness = brightness;
    }
    @Override
    public Color getColorAt(float i) {
        return Color.fromHSV(i / getLength(), _saturation, _brightness);
    }

    @Override
    public void onStyleGet(LomponentStyle style, float relativePos) {
        super.onStyleGet(style, relativePos);
        style.addStyle("color", getColorAt(relativePos));
    }

    public float getSaturation() {
        return _saturation;
    }
    public float getBrightness() {
        return _brightness;
    }

    public void setSaturation(float sat) {
        _saturation = Math.max(Math.min(sat, 1), 0);
    }

    public void setBrightness(float brightness) {
        _brightness = Math.max(Math.min(brightness, 1), 0);
    }
}
