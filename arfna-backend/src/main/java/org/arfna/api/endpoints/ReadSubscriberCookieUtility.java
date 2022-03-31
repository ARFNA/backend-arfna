package org.arfna.api.endpoints;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.cookie.subscriber.SubscriberCookiePayload;
import org.arfna.method.cookie.subscriber.SubscriberCookieResponse;
import org.arfna.method.password.middleware.ESubscriberRole;
import org.arfna.util.gson.GsonHelper;

import java.util.Optional;

public class ReadSubscriberCookieUtility {

    public SubscriberCookieResponse getResponse(String inputPayload, EVersion version, Optional<Subscriber> subscriber) {
        boolean isSubscriberAuthorized = version.getMiddlewareHelper().isSubscriberAuthorized(subscriber, ESubscriberRole.NONE_ROLE);
        if (!isSubscriberAuthorized) {
            SubscriberCookieResponse unauthorizedResponse = new SubscriberCookieResponse();
            unauthorizedResponse.setUnauthorized();
            return unauthorizedResponse;
        }
        SubscriberCookiePayload payload = GsonHelper.getGson().fromJson(inputPayload, SubscriberCookiePayload.class);
        return version.getSubscriberCookieApi().getResponse(payload, subscriber.get());
    }

}
