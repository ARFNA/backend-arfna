package org.arfna.method.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * We add this class as a parent for all API responses to extend
 * This allows for serialization while still maintaining type safety
 */
public class MethodResponse implements Serializable {
    private static final long serialVersionUID = -6864423760419443911L;

    private transient boolean sendCookie;
    private transient List<Object> dataToPersist = new ArrayList<>();

    public MethodResponse addDataToSend(Object toSend) {
        this.setSendCookie();
        dataToPersist.add(toSend);
        return this;
    }

    public List<Object> getDataToPersist() {
        return dataToPersist;
    }

    private MethodResponse setSendCookie() {
        this.sendCookie = true;
        return this;
    }

    private MethodResponse noSendCookie() {
        this.sendCookie = false;
        return this;
    }
}
