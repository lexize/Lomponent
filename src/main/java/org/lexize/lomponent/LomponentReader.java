package org.lexize.lomponent;

import java.util.ArrayList;
import java.util.List;

public class LomponentReader {
    private static final char _tagStart = '<';
    private static final char _tagEnd = '>';
    private static final char _tagSeparator = ':';
    private static final char _tagClosing = '/';
    private static final char _escapeChar = '\\';
    private static final String _tagNamePattern = "^[a-zA-Z0-9\\-_]+$";
    private String _source;
    private int _ind = -1;
    public LomponentReader(String readerSource) {
        _source = readerSource;

    }

    public ReaderEvent readNext() {
        int charsRead = 0;
        int i;
        StringBuilder sb = new StringBuilder();
        boolean nextEscaped = false;
        var mark = mark();
        while (true) {
            i = _read();
            if (i == -1) break;
            char c = (char) i;
            if (nextEscaped) {
                sb.append(c);
                nextEscaped = false;
            }
            else {
                switch (c) {
                    case _tagStart -> {
                        if (sb.length() > 0) {
                            mark.restore();
                            skip(charsRead);
                            return new ReaderEvent(sb.toString(), mark);
                        }
                        else {
                            var event = _readTag(c, mark);
                            if (event.getEventType() == ReaderEvent.EventType.TEXT) {
                                sb.append(event.getSourceText());
                            }
                            else {
                                return event;
                            }
                        }
                    }
                    case _escapeChar -> nextEscaped = true;
                    default -> sb.append(c);
                }
            }
            charsRead++;
        }
        return sb.length() > 0 ? new ReaderEvent(sb.toString(), mark) : new ReaderEvent();
    }

    private ReaderEvent _readTag(char startChar, Mark startMark) {
        StringBuilder sourceText = new StringBuilder();
        StringBuilder tagName = new StringBuilder();
        StringBuilder currentArg = new StringBuilder();
        List<String> args = new ArrayList<>();
        boolean readingTagName = true;
        boolean isTagClosing = false;
        boolean nextEscaped = false;
        sourceText.append(startChar);
        int i;
        int charsRead = 0;
        while (true) {
            i = _read();
            if (i == -1) return new ReaderEvent(sourceText.toString(), startMark);
            char c = (char) i;
            boolean shouldBreak = false;
            StringBuilder csb = readingTagName ? tagName : currentArg;
            if (charsRead == 0 && c == _tagClosing) {
                isTagClosing = true;
                sourceText.append(c);
            }
            else {
                if (nextEscaped) {
                    csb.append(c);
                    sourceText.append(c);
                    nextEscaped = false;
                }
                else {
                    switch (c) {
                        case _escapeChar -> nextEscaped = true;
                        case _tagEnd, _tagSeparator -> {
                            if (readingTagName && tagName.length() == 0) {
                                shouldBreak = true;
                            }
                            else {
                                if (c == _tagEnd) {
                                    if (!readingTagName) {
                                        args.add(currentArg.toString());
                                    }
                                    sourceText.append(c);
                                    return new ReaderEvent(tagName.toString(), sourceText.toString(), args.toArray(new String[args.size()]), isTagClosing, startMark);
                                }
                                else {
                                    if (readingTagName) {
                                        readingTagName = false;
                                    }
                                    else {
                                        args.add(currentArg.toString());
                                        currentArg = new StringBuilder();
                                    }
                                }
                            }
                        }
                        default -> csb.append(c);
                    }
                    if (!nextEscaped) sourceText.append(c);
                }
            }
            charsRead++;
            if (shouldBreak) break;
        }
        return new ReaderEvent(sourceText.toString(), startMark);
    }

    public int _read() {
        if (_ind >= _source.length()-1) return -1;
        else {
            _ind++;
            return _source.charAt(_ind);
        }
    }

    public Mark mark() {
        return new Mark(this);
    }

    public void reset() {
        _ind = -1;
    }

    public int skip(int charAmount) {
        if (_ind >= _source.length()) return 0;
        int r = Math.min(_source.length()-_ind, charAmount);
        r = Math.max(-r, r);
        _ind += r;
        return r;
    }

    public int getCurrentIndex() {
        return _ind;
    }

    public int getCurrentLine() {
        Mark cPos = mark();
        reset();
        int cLine = 0;
        int i = _read();
        while (i != -1 && getCurrentIndex() <= cPos.getSavedIndex()) {
            if ('\n' == (char)i) cLine++;
        }
        cPos.restore();
        return i;
    }

    public static class ReaderEvent {
        public enum EventType {
            TEXT,
            TAG,
            NONE
        }
        private EventType _eventType = EventType.NONE;
        private String _sourceText;
        private String _tagName;
        private String[] _args;
        private Mark _tagStartMark;
        private boolean _isTagClosing;

        public ReaderEvent() {

        }

        public ReaderEvent(String text, Mark tagStartMark) {
            _sourceText = text;
            _eventType = EventType.TEXT;
            _tagStartMark  = tagStartMark;
        }

        public ReaderEvent(String tagName, String sourceText, String[] args, boolean isClosing, Mark tagStartMark) {
            _eventType = EventType.TAG;
            _tagName = tagName;
            _sourceText = sourceText;
            _args = args;
            _isTagClosing = isClosing;
            _tagStartMark  = tagStartMark;
        }

        public EventType getEventType() {
            return _eventType;
        }

        public String getSourceText() {
            return _sourceText;
        }

        public String getTagName() {
            return _tagName;
        }

        public String[] getArgs() {
            return _args;
        }

        public Mark getStartMark() {
            return _tagStartMark;
        }

        public boolean IsTagClosing() {
            return _isTagClosing;
        }
    }

    public static class Mark {
        private final int _savedInd;
        private final LomponentReader _parent;
        private Mark(LomponentReader parent) {
            _savedInd = parent._ind;
            _parent = parent;
        }

        public void restore() {
            _parent._ind = _savedInd;
        }

        public int getSavedIndex() {
            return _savedInd;
        }

    }
}
