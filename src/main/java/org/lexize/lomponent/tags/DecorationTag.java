package org.lexize.lomponent.tags;

import org.lexize.lomponent.LomponentReader;
import org.lexize.lomponent.LomponentSerializer;
import org.lexize.lomponent.components.DecorationComponent;
import org.lexize.lomponent.components.GroupComponent;
import org.lexize.lomponent.tags.context.TagContext;

import java.util.function.Supplier;

public class DecorationTag extends Tag {
    private String _name;
    private DecorationComponent.Decoration _decoration;
    public DecorationTag(String name, DecorationComponent.Decoration dec) {
        _name = name;
        _decoration = dec;
    }
    @Override
    public String getTagName() {
        return _name;
    }



    @Override
    public void onFound(GroupComponent parent, LomponentSerializer serializer, LomponentReader reader, TagContext ctx) {
        DecorationComponent component = new DecorationComponent(_decoration);
        parent.add(component);
        readToClosingTag(ctx, component, serializer, reader);
    }

    @Override
    public TagContext onMatch(LomponentReader.ReaderEvent event) {
        if (event.getTagName().equals(getTagName())) {
            TagContext ctx = new TagContext();
            ctx.applyFromEvent(event);
            return ctx;
        }
        return null;
    }
}
