package org.arfna.method.email;

import org.arfna.api.version.EVersion;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;

public class EmailApiV1 implements IEmailApi {

    @Override
    public EmailResponse getResponse(EmailPayload payload, EVersion version) {
        EEmailRequest requestType = payload.getRequestType();
        EmailResponse response = new EmailResponse();
        if (requestType == EEmailRequest.CONTACT_US) {
            return response;
        }
        response.addValidationMessage(new ValidationMessage(EValidationMessage.INVALID_API));
        return response;
    }
}
