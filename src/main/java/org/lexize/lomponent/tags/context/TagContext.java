package org.lexize.lomponent.tags.context;

import org.lexize.lomponent.LomponentReader;

import java.util.function.Supplier;

public class TagContext {
    protected String _tagName;
    protected String[] _tagArgs;
    protected String _tagSourceText;
    protected LomponentReader.Mark _tagStartMark;
    public TagContext() {

    }

    public static <T extends TagContext> T fromReaderEvent(LomponentReader.ReaderEvent event, Supplier<T> ctxSupplier) {
        T ctx = ctxSupplier.get();
        ctx._tagArgs = event.getArgs();
        ctx._tagName = event.getTagName();
        ctx._tagSourceText = event.getSourceText();;
        ctx._tagStartMark = event.getStartMark();
        return ctx;
    }

    public void applyFromEvent(LomponentReader.ReaderEvent event) {
        this._tagArgs = event.getArgs();
        this._tagName = event.getTagName();
        this._tagSourceText = event.getSourceText();;
        this._tagStartMark = event.getStartMark();
    }

    public String getTagName() {
        return _tagName;
    }

    public String[] getTagArgs() {
        return _tagArgs;
    }

    public String getTagSourceText() {
        return _tagSourceText;
    }

    public LomponentReader.Mark getTagStartMark() {
        return _tagStartMark;
    }
}
