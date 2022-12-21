package org.lexize.lomponent;

import org.lexize.lomponent.components.DecorationComponent;
import org.lexize.lomponent.tags.*;

import java.util.ArrayList;
import java.util.List;

public class LomponentStandardTags {
    public static Tag[] getStandardTags() {
        List<Tag> stg = new ArrayList<>();
        stg.addAll(List.of(getStandardFormattingTags()));
        stg.addAll(List.of(getStandardColorTags()));
        return stg.toArray(new Tag[0]);
    }
    public static Tag[] getStandardFormattingTags() {
        List<Tag> stg = new ArrayList<>();
        stg.add(new ColorTag());
        stg.add(new GradientTag());
        stg.add(new RainbowTag());
        stg.addAll(List.of(getDecorationTags()));
        return stg.toArray(new Tag[0]);
    }
    public static Tag[] getStandardColorTags() {
        return new Tag[] {

        };
    }

    public static Tag[] getDecorationTags() {
        List<Tag> stg = new ArrayList<>();
        for (DecorationComponent.Decoration dec :
                DecorationComponent.Decoration.values()) {
            for (String n :
                    dec.getTagNames()) {
                stg.add(new DecorationTag(n,dec));
            }
        }
        return stg.toArray(new Tag[0]);
    }
}
