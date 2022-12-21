package org.lexize.lomponent.components;

import org.lexize.lomponent.LomponentStyle;

import java.util.ArrayList;
import java.util.List;

public class DecorationComponent extends GroupComponent {
    public Decoration decoration;
    public DecorationComponent(Decoration dec) {
        decoration = dec;
    }

    public enum Decoration {
        Reset("reset","r"),
        Bold("bold", "b"),
        Italic("italic","em", "i"),
        Underlined("underlined","u"),
        Strikethrough("strikethrough","st"),
        Obfuscated("obfuscated","obf");
        private final String[] _tagNames;
        Decoration(String... tagNames) {
            _tagNames = tagNames;
        }
        public String[] getTagNames() {
            return _tagNames;
        }
    }
    @Override
    public String toString() {
        return "Decoration tag: %s".formatted(decoration);
    }

    @Override
    public void onStyleGet(LomponentStyle style, float relativePos) {
        if (decoration == Decoration.Reset) {
            style.clearStyles();
        }
        else {
            super.onStyleGet(style, relativePos);
            style.addStyle(decoration, true);
        }
    }
}
