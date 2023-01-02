package org.arfna.method.cookie.subscriber;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;

public interface ISubscriberCookieApi {

    SubscriberCookieResponse getResponse(SubscriberCookiePayload payload, Subscriber subscriber);

}
