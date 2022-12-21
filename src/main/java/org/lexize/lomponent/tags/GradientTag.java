package org.lexize.lomponent.tags;

import org.lexize.lomponent.LomponentReader;
import org.lexize.lomponent.LomponentSerializer;
import org.lexize.lomponent.components.GradientComponent;
import org.lexize.lomponent.components.GroupComponent;
import org.lexize.lomponent.components.TextComponent;
import org.lexize.lomponent.models.Color;
import org.lexize.lomponent.tags.context.TagContext;
import org.lexize.lomponent.utils.ColorResolver;

import java.util.TreeMap;

public class GradientTag extends Tag {
    @Override
    public String getTagName() {
        return "gradient";
    }

    @Override
    public void onFound(GroupComponent parent, LomponentSerializer serializer, LomponentReader reader, TagContext ctx) {
        String[] args = ctx.getTagArgs();
        if (args.length == 0) {
            parent.add(new TextComponent(ctx.getTagSourceText()));
            return;
        }
        float k = 1f / (args.length - 1);
        TreeMap<Float, Color> colors = new TreeMap<>();
        for (int i = 0; i < args.length; i++) {
            Color c = ColorResolver.getColorFromString(args[i]);
            if (c != null) colors.put(k*i, c);
        }
        GradientComponent component = new GradientComponent(colors);
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
