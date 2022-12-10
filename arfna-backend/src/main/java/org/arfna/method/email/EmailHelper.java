package org.arfna.method.email;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import org.arfna.method.common.ValidationMessage;
import org.arfna.util.security.SecurityKey;


public class EmailHelper {

    private static final String CONTACT_US_RECIPIENT_NAME = "Roshnee";
    private static final String CONTACT_US_RECIPIENT_ADDRESS = "roshnee@arfna.org";

    public EmailResponse sendEmailForContactUs(EmailPayload payload) {
        Email email = new Email();
        payload.getFromField().forEach(email::setFrom);
        email.addRecipient(CONTACT_US_RECIPIENT_NAME, CONTACT_US_RECIPIENT_ADDRESS);
        email.setSubject("Contact Us! - Web Form Submission");
        email.setPlain(payload.getBody());
        MailerSend ms = new MailerSend();
        ms.setToken(SecurityKey.getSecurityKeys().getMailerKey().getApiToken());
        EmailResponse response = new EmailResponse();
        try {
            MailerSendResponse mailerSendResponse = ms.emails().send(email);
            return response.withMessageId(mailerSendResponse.messageId);
        } catch (MailerSendException e) {
            response
                .addValidationMessage(
                        ValidationMessage.createCustomValidation(-1, e.getMessage())
                );
            return response;
        }
    }

}
