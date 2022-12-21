package org.lexize.lomponent.tags;

import org.lexize.lomponent.LomponentReader;
import org.lexize.lomponent.LomponentSerializer;
import org.lexize.lomponent.LomponentUtils;
import org.lexize.lomponent.components.ColorComponent;
import org.lexize.lomponent.components.GroupComponent;
import org.lexize.lomponent.models.Color;
import org.lexize.lomponent.tags.context.ColorContext;
import org.lexize.lomponent.utils.ColorResolver;

import java.util.HashMap;
import java.util.HexFormat;
import java.util.function.Function;
import java.util.regex.Matcher;

public class ColorTag extends Tag<ColorContext>{
    @Override
    public String getTagName() {
        return "color";
    }

    @Override
    public ColorContext onMatch(LomponentReader.ReaderEvent event) {
        ColorContext ctx = new ColorContext();
        ctx.applyFromEvent(event);
        if (ColorResolver.matchesAnyColorPattern(event.getTagName())) {
            ctx.isColorInName(true);
            return ctx;
        } else if (event.getTagName().equals(getTagName())) {
            return ctx;
        }
        return null;
    }

    @Override
    public boolean closeTagMatch(String closeTagName, ColorContext sourceTagContext) {
        return super.closeTagMatch(closeTagName, sourceTagContext) || (sourceTagContext.isColorInName() && closeTagName.equals(getTagName()));
    }

    @Override
    public void onFound(GroupComponent parent, LomponentSerializer serializer, LomponentReader reader, ColorContext ctx) {
        String[] args = ctx.getTagArgs();
        ColorComponent component = new ColorComponent(new Color());
        String colorSource = ctx.isColorInName() ? ctx.getTagName() : args.length > 0 ? args[0] : null;
        if (colorSource != null) {
            Color c = ColorResolver.getColorFromString(colorSource);
            if (c != null) component = new ColorComponent(c);
        }
        parent.add(component);
        readToClosingTag(ctx, component,serializer,reader);
    }
}
