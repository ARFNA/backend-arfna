package org.arfna.api.endpoints;

import org.arfna.api.version.EVersion;
import org.arfna.method.password.login.api.MutateSubscribersPayload;
import org.arfna.method.password.login.api.MutateSubscribersResponse;
import org.arfna.util.gson.GsonHelper;
import org.arfna.util.logger.ArfnaLogger;

public class MutateSubscriberUtility {

    public MutateSubscribersResponse getResponse(String jsonPayload, EVersion version) {
        ArfnaLogger.debug(this.getClass(), "Version is: " + version.name());
        MutateSubscribersPayload payload = GsonHelper.getGson().fromJson(jsonPayload, MutateSubscribersPayload.class);
        return version.getMutateSubscriberApi().getResponse(payload, version);
    }

}
