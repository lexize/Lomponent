import org.lexize.lomponent.LomponentReader;
import org.lexize.lomponent.LomponentSerializer;
import org.lexize.lomponent.LomponentStyle;
import org.lexize.lomponent.components.*;
import org.lexize.lomponent.models.Color;
import org.lexize.lomponent.tags.ColorTag;
import org.lexize.lomponent.tags.PlaceholderTag;
import org.lexize.lomponent.tags.RainbowTag;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

public class Test {
    private static final String ESC_STR = "\u001B";
    private static final String RESET_ESC_SEQUENCE_STR = "\u001B[0m";
    public static void main(String[] args) throws IOException {
        String testString = """
                Test text
                <red>Test colored text
                <test_placeholder>
                <b>Test bold text</b>
                \\<b>Escaped bold tag\\</b>
                <reset>This part is in reset tag</reset>, but this not.</red>
                <gradient:red:blue>Test gradient text</gradient>
                <rainbow:0.75:1.0>\\<reset> tag just <reset>clears all style of parent components, but not <rainbow:0.45:1.0>resetting</rainbow> style processing progress.</reset> May be useful sometimes</rainbow>""";
        LomponentSerializer serializer = LomponentSerializer.defaultBuilder().build();
        serializer.addTags(
                new PlaceholderTag("test_placeholder", "Test text from placeholder")
        );
        GroupComponent component = serializer.parse(testString);
        printComponent(component);
    }

    public static void iteratePrint(GroupComponent component, String prefix) {
        for (Component c :
                component.getChildren()) {
            System.out.println(prefix+c.toString());
            if (c instanceof GroupComponent gc) {
                iteratePrint(gc, prefix+"\t");
            }
        }
    }

    public static void printComponent(GroupComponent c) {
        for (Component cc:
                c.getChildren()) {
            if (cc instanceof TextComponent tc) {
                StringBuilder sb = new StringBuilder();
                String content = tc.getContent();
                for (int i = 0; i < content.length(); i++) {
                    LomponentStyle s = LomponentStyle.defaultStyle();
                    tc.onStyleGet(s, i);
                    char ch = content.charAt(i);
                    for (Map.Entry<Object, Object> style :
                            s.getStyles().entrySet()) {
                        Object k = style.getKey();
                        Object v = style.getValue();
                        if (k.equals("color")) {
                            Color col = (Color) v;
                            sb.append(ConsoleFormatting.colorCode(col.getRInt(),col.getGInt(),col.getBInt()));
                        }
                        else if (k instanceof DecorationComponent.Decoration d) {
                            if ((boolean)(v)) sb.append(ConsoleFormatting.getDecoration(d));
                        }
                    }
                    sb.append(ch);
                    sb.append(ConsoleFormatting.getDecoration(DecorationComponent.Decoration.Reset));
                }
                System.out.print(sb);
            }
            if (cc instanceof GroupComponent gc) printComponent(gc);
        }
    }

    private static class ConsoleFormatting {
        public static final char escapeChar = ((char)0x1B);
        public static String colorCode(int r, int g, int b) {
            return escapeChar+"[38;2;%s;%s;%sm".formatted(r,g,b);
        }
        public static String getDecoration(DecorationComponent.Decoration d) {
            return switch (d) {
                case Reset -> escapeChar + "[0m";
                case Bold -> escapeChar + "[1m";
                case Italic -> escapeChar + "[3m";
                case Underlined -> escapeChar + "[4m";
                case Strikethrough -> escapeChar + "[9m";
                case Obfuscated -> "";
            };
        }
    }
}
