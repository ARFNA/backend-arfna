package org.arfna.method.email;

import org.arfna.api.version.EVersion;

public interface IEmailApi {

    EmailResponse getResponse(EmailPayload payload, EVersion version);
}
