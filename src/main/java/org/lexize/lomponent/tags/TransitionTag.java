package org.lexize.lomponent.tags;

import org.lexize.lomponent.LomponentReader;
import org.lexize.lomponent.LomponentSerializer;
import org.lexize.lomponent.components.GroupComponent;
import org.lexize.lomponent.tags.context.TagContext;

public class TransitionTag extends Tag{
    @Override
    public String getTagName() {
        return null;
    }

    @Override
    public void onFound(GroupComponent parent, LomponentSerializer serializer, LomponentReader reader, TagContext ctx) {

    }

    @Override
    public TagContext onMatch(LomponentReader.ReaderEvent event) {
        return null;
    }
}
