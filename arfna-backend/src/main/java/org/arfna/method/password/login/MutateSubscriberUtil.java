package org.arfna.method.password.login;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.arfna.method.password.login.api.MutateSubscribersResponse;
import org.arfna.util.logger.ArfnaLogger;

import java.util.List;

public class MutateSubscriberUtil {

    public MutateSubscribersResponse addSubscriber(Subscriber subscriber, EVersion version) {
        MutateSubscribersResponse response = new MutateSubscribersResponse();
        if (checkIfSubscriberExists(subscriber, version, response)) return response;
        ArfnaLogger.debug(this.getClass(), "Adding new subscriber to table");
        version.getDatabaseUtil().createSubscriber(subscriber);
        Subscriber subscriberInTable = version.getDatabaseUtil().getSubscriberFromEmail(subscriber.getEmailAddress());
        return response.setSubscriber(subscriberInTable);
    }

    public MutateSubscribersResponse addSubscriberWithPassword(Subscriber subscriber, EVersion version) {
        MutateSubscribersResponse response = new MutateSubscribersResponse();
        if (checkIfSubscriberExists(subscriber, version, response)) return response;
        if (validatePassword(subscriber, version, response)) return response;
        encryptPassword(subscriber, version);
        ArfnaLogger.debug(this.getClass(), "Adding new subscriber to table");
        version.getDatabaseUtil().createSubscriber(subscriber);
        Subscriber subscriberInTable = version.getDatabaseUtil().getSubscriberFromEmail(subscriber.getEmailAddress());
        return response.setSubscriber(subscriberInTable);
    }

    public MutateSubscribersResponse addPassword(Subscriber subscriber, EVersion version) {
        MutateSubscribersResponse response = new MutateSubscribersResponse();
        if (validatePassword(subscriber, version, response)) return response;
        encryptPassword(subscriber, version);
        ArfnaLogger.debug(this.getClass(), "Updating subscriber entry with password");
        version.getDatabaseUtil().updateSubscriber(subscriber);
        Subscriber subscriberInTable = version.getDatabaseUtil().getSubscriberFromEmail(subscriber.getEmailAddress());
        return response.setSubscriber(subscriberInTable);
    }

    public MutateSubscribersResponse login(Subscriber subscriber, EVersion version) {
        MutateSubscribersResponse response = new MutateSubscribersResponse();
        String plainTextPassword = subscriber.getPassword();
        ArfnaLogger.debug(this.getClass(), "Fetching password from table");
        Subscriber subscriberInTable = version.getDatabaseUtil().getSubscriberFromEmail(subscriber.getEmailAddress());
        ArfnaLogger.debug(this.getClass(), "Validating password");
        boolean isCorrectPassword = version.getPasswordHelper().isPasswordValid(plainTextPassword, subscriberInTable.getPassword());
        if (!isCorrectPassword) {
            response.addValidationMessage(new ValidationMessage(EValidationMessage.LOGIN_INCORRECT));
            return response;
        }
        return new MutateSubscribersResponse().setSubscriber(subscriberInTable);
    }

    private void addSubscriberExistsResponse(MutateSubscribersResponse response, Subscriber potentialSubscriberInTable) {
        if (potentialSubscriberInTable.getPassword() != null) {
            response.addValidationMessage(new ValidationMessage(EValidationMessage.SUBSCRIBER_ALREADY_EXISTS));
        } else {
            response.addValidationMessage(new ValidationMessage(EValidationMessage.SUBSCRIBER_NEEDS_TO_CREATE_PASSWORD));
        }
    }

    private void encryptPassword(Subscriber subscriber, EVersion version) {
        ArfnaLogger.debug(this.getClass(), "Encrypting password");
        String hashedPassword = version.getPasswordHelper().getEncryptedPassword(subscriber.getPassword());
        subscriber.setPassword(hashedPassword);
    }

    private boolean validatePassword(Subscriber subscriber, EVersion version, MutateSubscribersResponse response) {
        ArfnaLogger.debug(this.getClass(), "Validating password");
        List<ValidationMessage> messages = version.getPasswordHelper().validatePassword(subscriber.getPassword());
        if (!messages.isEmpty()) {
            messages.forEach(response::addValidationMessage);
            return true;
        }
        return false;
    }

    private boolean checkIfSubscriberExists(Subscriber subscriber, EVersion version, MutateSubscribersResponse response) {
        ArfnaLogger.debug(this.getClass(), "Checking if subscriber email exists in table");
        Subscriber potentialSubscriberInTable = version.getDatabaseUtil().getSubscriberFromEmail(subscriber.getEmailAddress());
        if (potentialSubscriberInTable != null) {
            addSubscriberExistsResponse(response, potentialSubscriberInTable);
            return true;
        }
        return false;
    }

}