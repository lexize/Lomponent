package org.lexize.lomponent;

import org.lexize.lomponent.components.DecorationComponent;

import java.util.HashMap;
import java.util.Map;

public class LomponentStyle {

    private final Map<Object, Object> _styles = new HashMap<>();
    public void addStyle(Object k, Object v) {
        _styles.put(k, v);
    }
    public boolean hasStyle(Object k) {
        return _styles.containsKey(k);
    }

    public Map<Object, Object> getStyles() {
        return _styles;
    }

    public void clearStyles() {
        _styles.clear();
    }

    public static LomponentStyle defaultStyle() {
        LomponentStyle b = new LomponentStyle();
        for (DecorationComponent.Decoration decoration:
                DecorationComponent.Decoration.values()) {
            b.addStyle(decoration, false);
        }
        return b;
    }
}
