package org.arfna.method.password.middleware;

import org.arfna.database.entity.Subscriber;

import java.util.Optional;

public interface IMiddlewareHelper {

    boolean isSubscriberAuthorized(Optional<Subscriber> subscriber, ESubscriberRole lowestAllowedRole);

}
