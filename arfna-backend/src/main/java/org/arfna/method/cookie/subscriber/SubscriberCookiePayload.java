package org.arfna.method.cookie.subscriber;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class SubscriberCookiePayload implements Serializable {
    private static final long serialVersionUID = 345944004837284600L;

    @Expose private EField field;

    public EField getField() {
        return field;
    }
}
