package org.lexize.lomponent.components;

public class TextComponent extends Component{
    private String _content;

    public TextComponent(String text) {
        _content = text;
    }

    public String getContent() {
        return _content;
    }

    @Override
    public String toString() {
        return "Text component: %s".formatted(getContent());
    }

    @Override
    public int getLength() {
        return _content.length();
    }
}
