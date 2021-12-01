package org.arfna.method.password.middleware;

import org.arfna.database.entity.Subscriber;
import org.arfna.util.logger.ArfnaLogger;

import java.util.Optional;

public class MiddlewareHelperV1 implements IMiddlewareHelper {

    public boolean isSubscriberAuthorized(Optional<Subscriber> subscriber, ESubscriberRole lowestAllowedRole) {
        if (!subscriber.isPresent()) {
            ArfnaLogger.info(this.getClass(),"User with no cookie present attempted to access API");
            return false;
        }
        Subscriber presentSubscriber = subscriber.get();
        ESubscriberRole currentRole = ESubscriberRole.getRoleFromName(presentSubscriber.getRole());
        if (currentRole.getRoleNumber() <= lowestAllowedRole.getRoleNumber()) {
            return true;
        }
        ArfnaLogger.warn(this.getClass(),
                "Unauthenticated user with ID: " + presentSubscriber.getId() + " attempted access API with role: " + presentSubscriber.getRole());
        return false;
    }

}
