package org.lexize.lomponent.tags;

import org.lexize.lomponent.LomponentReader;
import org.lexize.lomponent.LomponentSerializer;
import org.lexize.lomponent.components.Component;
import org.lexize.lomponent.components.GroupComponent;
import org.lexize.lomponent.components.RainbowComponent;
import org.lexize.lomponent.tags.context.TagContext;

import java.util.function.Supplier;

public class RainbowTag extends Tag {

    @Override
    public String getTagName() {
        return "rainbow";
    }

    @Override
    public void onFound(GroupComponent parent, LomponentSerializer serializer, LomponentReader reader, TagContext ctx) {
        RainbowComponent c = new RainbowComponent();
        String[] args = ctx.getTagArgs();
        if (args.length >= 2) {
            try {
                float s = Float.parseFloat(args[0]);
                float b = Float.parseFloat(args[1]);
                c.setBrightness(b);
                c.setSaturation(s);
            }
            catch (Exception ignored) {}
        }
        parent.add(c);
        readToClosingTag(ctx, c, serializer, reader);
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
