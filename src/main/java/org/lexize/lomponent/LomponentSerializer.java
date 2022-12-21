package org.lexize.lomponent;

import org.lexize.lomponent.components.ParentComponent;
import org.lexize.lomponent.components.TextComponent;
import org.lexize.lomponent.tags.Tag;
import org.lexize.lomponent.tags.context.TagContext;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LomponentSerializer {
    private List<Tag> _tags = new ArrayList<>();
    private boolean _strictMode = false;
    public ParentComponent parse(String sourceText) {
        ParentComponent parent = new ParentComponent();
        LomponentReader reader = new LomponentReader(sourceText);
        LomponentReader.ReaderEvent event = reader.readNext();
        while (event.getEventType() != LomponentReader.ReaderEvent.EventType.NONE) {
            switch (event.getEventType()) {
                case TEXT -> parent.add(new TextComponent(event.getSourceText()));
                case TAG -> {
                    if (!event.IsTagClosing()) {
                        var tag = getByReaderEvent(event);
                        if (tag != null) {
                            tag.getKey().onFound(parent, this, reader, tag.getValue());
                        }
                        else parent.add(new TextComponent(event.getSourceText()));
                    }
                    else {
                        parent.add(new TextComponent(event.getSourceText()));
                    }
                }
            }
            event = reader.readNext();
        }
        return parent;
    }
    public <T extends TagContext> Map.Entry<Tag<T>, T> getByReaderEvent(LomponentReader.ReaderEvent event) {
        for (Tag<T> tag :
                _tags) {
            T ctx = (T) tag.onMatch(event);
            if (ctx != null) return new AbstractMap.SimpleEntry<>(tag, ctx);
        }
        return null;
    }
    public void addTags(Tag... tags) {
        _tags.addAll(List.of(tags));
    }
    public boolean strictMode() {
        return _strictMode;
    }
    public void strictMode(boolean b) {
        _strictMode = b;
    }

    public Builder getBuilder() {
        Builder b = newBuilder();
        b.strictMode(b.strictMode());
        b.addTags(_tags.toArray(new Tag[0]));
        return b;
    }

    public static Builder defaultBuilder() {
        Builder b = newBuilder();
        b.strictMode(b.strictMode());
        b.addTags(LomponentStandardTags.getStandardTags());
        return b;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() {}
        private List<Tag> _tags = new ArrayList<>();
        private boolean _strictMode = false;
        public LomponentSerializer build() {
            LomponentSerializer serializer = new LomponentSerializer();
            serializer._tags = _tags;
            serializer._strictMode = _strictMode;
            return serializer;
        }

        public boolean strictMode() {
            return _strictMode;
        }
        public Builder strictMode(boolean b) {
            _strictMode = b;
            return this;
        }
        public Builder setTags(Tag... tags) {
            clearTags();
            addTags(tags);
            return this;
        }
        public Builder addTags(Tag... tags) {
            _tags.addAll(List.of(tags));
            return this;
        }
        public Builder addStandardTags() {
            addTags(LomponentStandardTags.getStandardTags());
            return this;
        }
        public Builder addStandardFormattingTags() {
            addTags(LomponentStandardTags.getStandardFormattingTags());
            return this;
        }
        public Builder addStandardColorTags() {
            addTags(LomponentStandardTags.getStandardColorTags());
            return this;
        }
        public Builder clearTags() {
            _tags.clear();
            return this;
        }
    }
}
