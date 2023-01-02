package org.arfna.method.cookie.subscriber;

import com.google.gson.annotations.Expose;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.MethodResponse;

import java.io.Serializable;

public class SubscriberCookieResponse extends MethodResponse implements Serializable {

    @Expose private Subscriber subscriber;

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public SubscriberCookieResponse setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        prepareForSerialization();
        return this;
    }

    private void prepareForSerialization() {
        this.subscriber.setPostsToNull();
        this.subscriber.setPassword(null);
    }
}
