package org.arfna.api;

import org.arfna.api.endpoints.DummyUtility;
import org.arfna.api.endpoints.MutateSubscriberUtility;
import org.arfna.api.version.ArfnaVersion;
import org.arfna.api.version.EVersion;
import org.arfna.method.common.MethodResponse;
import org.arfna.method.dummy.DummyResponse;
import org.arfna.method.password.login.api.MutateSubscribersResponse;
import org.arfna.util.gson.GsonHelper;
import org.arfna.util.logger.ArfnaLogger;

/**
 * @author roshnee
 * Endpoint class to be used by API Service. Contains methods for every API offered by arfna.
 */
public class ArfnaUtility {

    public MethodResponse getDummyResponse(String jsonPayload) {
        ArfnaLogger.info(this.getClass(), "Received Dummy Response call");
        EVersion version = getVersion(jsonPayload);
        DummyUtility util = new DummyUtility();
        return util.getResponse(jsonPayload, version);
    }

    public MethodResponse getMutateSubscriberResponse(String jsonPayload) {
        ArfnaLogger.info(this.getClass(), "Received mutate subscriber call");
        EVersion version = getVersion(jsonPayload);
        MutateSubscriberUtility util = new MutateSubscriberUtility();
        return util.getResponse(jsonPayload, version);
    }

    private EVersion getVersion(String jsonPayload) {
        ArfnaVersion version = GsonHelper.getGson().fromJson(jsonPayload, ArfnaVersion.class);
        return version.getVersion();
    }

}
