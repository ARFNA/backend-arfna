package org.arfna.method.dummy;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class DummyPayload implements Serializable {
    private static final long serialVersionUID = -6175556810062077094L;

    @Expose private String inputMessage;

    private DummyPayload() {
        // restrict instantiation
    }

    public String getInputMessage() {
        return inputMessage;
    }
}
