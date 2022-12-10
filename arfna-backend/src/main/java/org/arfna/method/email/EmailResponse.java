package org.arfna.method.email;

import com.google.gson.annotations.Expose;
import org.arfna.method.common.MethodResponse;

import java.io.Serializable;

public class EmailResponse extends MethodResponse implements Serializable {
    private static final long serialVersionUID = -6175556810062077094L;

    @Expose private String messageId;

    public EmailResponse withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }
}
