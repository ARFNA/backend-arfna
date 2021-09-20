package org.arfna.method.password.login.api;

import com.google.gson.annotations.Expose;
import org.arfna.database.entity.Subscriber;

import java.io.Serializable;

public class MutateSubscribersPayload implements Serializable {
    private static final long serialVersionUID = 4436196785974537932L;

    @Expose private ESubscriberMutation mutation;
    @Expose private Subscriber subscriber;

    public ESubscriberMutation getMutation() {
        return mutation;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    /**
     * for creating a new subscriber - return false if subscriber already exists
     * {
     *     version: V1
     *     goal: "REGISTER"
     *     subscriber: {
     *         name:
     *         emailAddress:
     *     }
     * }
     *
     * for adding a password to existing user
     * {
     *     version: V1
     *     goal: "ADD_PASSWORD"
     *     subscriber: {
     *         emailAddress:
     *         password
     *     }
     * }
     *
     * for logging in
     * {
     *     version: V1
     *     goal: "LOGIN"
     *     subscriber: {
     *         emailAddress
     *         password
     *     }
     * }
     */

}
