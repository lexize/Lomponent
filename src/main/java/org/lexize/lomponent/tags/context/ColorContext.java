package org.lexize.lomponent.tags.context;

public class ColorContext extends TagContext {
    private boolean _colorInName;
    public void isColorInName(boolean b) {
        _colorInName = b;
    }

    public boolean isColorInName() {
        return _colorInName;
    }
}
