package org.arfna.method.password.login.api;

import org.arfna.api.version.EVersion;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.arfna.method.password.login.MutateSubscriberUtil;
import org.arfna.util.logger.ArfnaLogger;

public class MutateSubscriberApiV1 implements IMutateSubscriberApi {

    public MutateSubscribersResponse getResponse(MutateSubscribersPayload payload, EVersion version) {
        MutateSubscriberUtil util = new MutateSubscriberUtil();
        if (payload.getMutation() == ESubscriberMutation.REGISTER) {
            ArfnaLogger.debug(this.getClass(), "Registering subscriber");
            return util.addSubscriber(payload.getSubscriber(), version);
        } if (payload.getMutation() == ESubscriberMutation.ADD_PASSWORD) {
            ArfnaLogger.debug(this.getClass(), "Adding password to existing subscriber");
            return util.addPassword(payload.getSubscriber(), version);
        } if (payload.getMutation() == ESubscriberMutation.LOGIN) {
            ArfnaLogger.debug(this.getClass(), "Logging in user");
            return util.login(payload.getSubscriber(), version);
        } if (payload.getMutation() == ESubscriberMutation.ADD_SUBSCRIBER_WITH_PASSWORD) {
            ArfnaLogger.debug(this.getClass(), "Registering subscriber with password");
            return util.addSubscriberWithPassword(payload.getSubscriber(), version);
        }
        MutateSubscribersResponse response = new MutateSubscribersResponse();
        response.addValidationMessage(new ValidationMessage(EValidationMessage.INVALID_API));
        return response;
    }

}
