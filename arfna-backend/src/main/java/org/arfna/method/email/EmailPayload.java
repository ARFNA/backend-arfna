package org.arfna.method.email;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class EmailPayload implements Serializable {
    private static final long serialVersionUID = -6175556810062077094L;

    @Expose private EEmailRequest requestType;
    @Expose private List<String> toFields;
    @Expose private String subject;
    @Expose private String body;

    public EmailPayload withToFields(List<String> toFields) {
        this.toFields = toFields;
        return this;
    }

    public EmailPayload withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailPayload withBody(String body) {
        this.body = body;
        return this;
    }

    public List<String> getToFields() {
        return toFields;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public EEmailRequest getRequestType() {
        return requestType;
    }
}
