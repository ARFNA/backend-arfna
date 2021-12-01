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

    @Expose private Subscriber subscriber;

    public MutateSubscribersResponse setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        this.prepareAsHttpResponse();
        return this;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void prepareAsHttpResponse() {
        this.subscriber.setPostsToNull();
        this.subscriber.setPassword(null);
    }

}
