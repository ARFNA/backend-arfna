package org.arfna.method.cookie.subscriber;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.arfna.util.logger.ArfnaLogger;

import java.io.Serializable;

public class SubscriberCookieApiV1 implements ISubscriberCookieApi {

    @Override
    public SubscriberCookieResponse getResponse(SubscriberCookiePayload payload, Subscriber subscriber) {
        ArfnaLogger.debug(this.getClass(), "Reading subscriber cookie");
        EField fieldToRetrive = payload.getField();
        SubscriberCookieResponse response = new SubscriberCookieResponse();
        if (fieldToRetrive == EField.ALL) {
            response.setSubscriber(subscriber);
            return response;
        }
        response.addValidationMessage(new ValidationMessage(EValidationMessage.INVALID_API));
        return response;
    }
}
