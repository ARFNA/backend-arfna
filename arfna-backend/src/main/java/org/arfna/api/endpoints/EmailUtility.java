package org.arfna.api.endpoints;

import org.arfna.api.version.EVersion;
import org.arfna.method.blog.GetBlogPayload;
import org.arfna.method.blog.GetBlogResponse;
import org.arfna.method.email.EmailPayload;
import org.arfna.method.email.EmailResponse;
import org.arfna.util.gson.GsonHelper;
import org.arfna.util.logger.ArfnaLogger;

public class EmailUtility {

    public EmailResponse getResponse(String inputPayload, EVersion version) {
        ArfnaLogger.debug(this.getClass(), "Version is: " + version.name());
        EmailPayload payload = GsonHelper.getGson().fromJson(inputPayload, EmailPayload.class);
        return version.getEmailApi().getResponse(payload, version);
    }

}
