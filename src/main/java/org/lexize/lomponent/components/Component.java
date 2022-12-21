package org.lexize.lomponent.components;

import org.lexize.lomponent.LomponentStyle;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    private GroupComponent _parent;
    public GroupComponent getParent() {
        return _parent;
    }

    protected void setParent(GroupComponent component) {
        _parent = component;
    }
    public int getLength() {
        return 0;
    }

    public int getRelativePosition(Component other) {
        return this.getGlobalPosition() - other.getGlobalPosition();
    }

    public int getGlobalPosition() {
        if (_parent != null) {
            int p = 0;
            Component[] parentChildren = getParent().getChildren();
            for (Component c:
                 parentChildren) {
                if (c != this) p += c.getLength();
                else break;
            }
            return p + getParent().getGlobalPosition();
        }
        return 0;
    }

    public void onStyleGet(LomponentStyle style, float relativePos) {
        if (_parent != null) _parent.onStyleGet(style, getRelativePosition(_parent)+relativePos);
    }

    public Component[] getPath() {
        if (_parent == null) return new Component[0];
        List<Component> components = new ArrayList<>(List.of(getParent().getPath()));
        components.add(getParent());
        return components.toArray(new Component[0]);
    }
}
