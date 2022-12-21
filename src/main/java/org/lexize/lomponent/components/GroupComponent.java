package org.lexize.lomponent.components;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupComponent extends Component {
    private List<Component> _children = new ArrayList<>();
    public Component[] getChildren() {
        return _children.toArray(new Component[0]);
    }

    public void add(Component component) {
        _children.add(component);
        component.setParent(this);
    }

    @Override
    public int getLength() {
        int l = 0;
        for (Component c :
                _children) {
            l += c.getLength();
        }
        return l;
    }
}
