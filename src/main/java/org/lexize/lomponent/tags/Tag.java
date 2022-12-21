package org.lexize.lomponent.tags;

import org.lexize.lomponent.LomponentReader;
import org.lexize.lomponent.LomponentSerializer;
import org.lexize.lomponent.components.GroupComponent;
import org.lexize.lomponent.components.TextComponent;
import org.lexize.lomponent.tags.context.TagContext;

import java.util.function.Supplier;

public abstract class Tag<T extends TagContext> {
    public abstract String getTagName();
    public abstract void onFound(GroupComponent parent, LomponentSerializer serializer, LomponentReader reader, T ctx);
    public abstract T onMatch(LomponentReader.ReaderEvent event);
    public boolean closeTagMatch(String closeTagName, T sourceTagContext) {
        return sourceTagContext.getTagName().equals(closeTagName);
    }
    protected void
    readToClosingTag(T sourceTagContext, GroupComponent parent, LomponentSerializer serializer, LomponentReader reader) {
        while (true) {
            LomponentReader.ReaderEvent event = reader.readNext();
            switch (event.getEventType()) {
                case TAG -> {
                    if (!event.IsTagClosing()) {
                        var tag = serializer.getByReaderEvent(event);
                        if (tag != null) tag.getKey().onFound(parent, serializer, reader, tag.getValue());
                        else parent.add(new TextComponent(event.getSourceText()));
                    }
                    else {
                        if (!closeTagMatch(event.getTagName(), sourceTagContext)) {
                            if (serializer.strictMode()) throw new RuntimeException("Tag %s isnt closed!".formatted(getTagName()));
                            event.getStartMark().restore();
                        }
                        return;
                    }
                }
                case TEXT -> parent.add(new TextComponent(event.getSourceText()));
                case NONE -> {
                    if (serializer.strictMode()) throw new RuntimeException("Tag %s isnt closed!".formatted(getTagName()));
                    return;
                }
            }
        }
    }
}
