package org.arfna.method.password.login.api;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.arfna.method.password.login.MutateSubscriberUtil;
import org.arfna.util.logger.ArfnaLogger;

import javax.swing.text.html.Option;
import java.util.Optional;

public class MutateSubscriberApiV1 implements IMutateSubscriberApi {

    public MutateSubscribersResponse getResponse(MutateSubscribersPayload payload, EVersion version, Optional<Subscriber> loggedInSubscriber) {
        MutateSubscriberUtil util = new MutateSubscriberUtil();
        if (payload.getMutation() == ESubscriberMutation.REGISTER) {
            if (loggedInSubscriber.isPresent())
                return generateInvalidCallDueToLoggedInSubscriber();
            ArfnaLogger.debug(this.getClass(), "Registering subscriber");
            return util.addSubscriber(payload.getSubscriber(), version);
        } if (payload.getMutation() == ESubscriberMutation.ADD_PASSWORD) {
            if (loggedInSubscriber.isPresent())
                return generateInvalidCallDueToLoggedInSubscriber();
            ArfnaLogger.debug(this.getClass(), "Adding password to existing subscriber");
            return util.addPassword(payload.getSubscriber(), version);
        } if (payload.getMutation() == ESubscriberMutation.LOGIN) {
            if (loggedInSubscriber.isPresent())
                return generateInvalidCallDueToLoggedInSubscriber();
            ArfnaLogger.debug(this.getClass(), "Logging in user");
            MutateSubscribersResponse response = util.login(payload.getSubscriber(), version);
            if (response.passedValidation())
                response.addDataToSend(response.getSubscriber());
            return response;
        } if (payload.getMutation() == ESubscriberMutation.ADD_SUBSCRIBER_WITH_PASSWORD) {
            if (loggedInSubscriber.isPresent())
                return generateInvalidCallDueToLoggedInSubscriber();
            ArfnaLogger.debug(this.getClass(), "Registering subscriber with password");
            return util.addSubscriberWithPassword(payload.getSubscriber(), version);
        } if (payload.getMutation() == ESubscriberMutation.CHECK_TYPE_FROM_EMAIL) {
            if (loggedInSubscriber.isPresent())
                return generateInvalidCallDueToLoggedInSubscriber();
            ArfnaLogger.debug(this.getClass(), "Checking type of email stored in database");
            return util.checkTypeSubscriberFromEmail(payload.getSubscriber(), version);
        } if (payload.getMutation() == ESubscriberMutation.LOGOUT) {
            ArfnaLogger.debug(this.getClass(), "Logging out current user, if present");
            return util.logout(loggedInSubscriber);
        } if (payload.getMutation() == ESubscriberMutation.ACCEPT_TERMS_OF_SERVICE) {
            ArfnaLogger.debug(this.getClass(), "Accepting terms of service of current user");
            if (loggedInSubscriber.isEmpty())
                return generateInvalidCallDueToNotLoggedInSubscriber();
            return util.acceptTermsOfService(loggedInSubscriber.get(), version);
        }
        MutateSubscribersResponse response = new MutateSubscribersResponse();
        response.addValidationMessage(new ValidationMessage(EValidationMessage.INVALID_API));
        return response;
    }

    private MutateSubscribersResponse generateInvalidCallDueToLoggedInSubscriber() {
        MutateSubscribersResponse response = new MutateSubscribersResponse();
        response.addValidationMessage(new ValidationMessage(EValidationMessage.SUBSCRIBER_ALREADY_LOGGED_IN));
        return response;
    }

    private MutateSubscribersResponse generateInvalidCallDueToNotLoggedInSubscriber() {
        MutateSubscribersResponse response = new MutateSubscribersResponse();
        response.addValidationMessage(new ValidationMessage(EValidationMessage.SUBSCRIBER_NOT_LOGGED_IN));
        return response;
    }

}
