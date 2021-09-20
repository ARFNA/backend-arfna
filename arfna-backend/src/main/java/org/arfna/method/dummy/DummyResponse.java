package org.arfna.method.dummy;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class DummyResponse implements Serializable {
    private static final long serialVersionUID = 6810458217983742216L;

    @Expose private String originalMessage;
    @Expose private String messageInPigLatin;

    public DummyResponse withOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
        return this;
    }

    public DummyResponse withMessageInPigLatin(String messageInPigLatin) {
        this.messageInPigLatin = messageInPigLatin;
        return this;
    }
}
