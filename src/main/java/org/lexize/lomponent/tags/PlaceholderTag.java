package org.lexize.lomponent.tags;

import org.lexize.lomponent.LomponentReader;
import org.lexize.lomponent.LomponentSerializer;
import org.lexize.lomponent.components.GroupComponent;
import org.lexize.lomponent.components.TextComponent;
import org.lexize.lomponent.tags.context.TagContext;

import java.util.function.Supplier;

public class PlaceholderTag extends Tag {
    private String _name;
    private String _text;
    public PlaceholderTag(String name, String text) {
        _name = name;
        _text = text;
    }
    @Override
    public String getTagName() {
        return _name;
    }

    @Override
    public void onFound(GroupComponent parent, LomponentSerializer serializer, LomponentReader reader, TagContext ctx) {
        parent.add(new TextComponent(_text));
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
