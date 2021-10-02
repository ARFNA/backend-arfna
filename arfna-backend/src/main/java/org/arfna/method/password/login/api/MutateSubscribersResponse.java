package org.arfna.method.password.login.api;

import com.google.gson.annotations.Expose;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.MethodResponse;
import org.arfna.method.common.ValidationMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MutateSubscribersResponse extends MethodResponse implements Serializable {
    private static final long serialVersionUID = -8394424520387746033L;

    @Expose private List<ValidationMessage> messages;
    @Expose private Subscriber subscriber;

    public void addValidationMessage(ValidationMessage message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
    }

    public MutateSubscribersResponse setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public boolean passedValidation() {
        return this.messages == null || this.messages.isEmpty();
    }
}
