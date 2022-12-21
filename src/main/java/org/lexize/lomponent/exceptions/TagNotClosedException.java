package org.lexize.lomponent.exceptions;

import org.lexize.lomponent.LomponentReader;

public class TagNotClosedException extends Exception {
    private String _tagName;
    private LomponentReader _reader;
    public TagNotClosedException(String tagName, LomponentReader reader) {
        _tagName = tagName;
        _reader = reader;
    }

    @Override
    public String getMessage() {
        return "Expected tag %s closing at line %s col %s".formatted(_tagName, _reader.getCurrentLine(), 0);
    }
}
