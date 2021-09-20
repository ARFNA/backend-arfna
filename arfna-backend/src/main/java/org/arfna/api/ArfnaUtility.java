package org.arfna.api;

import org.arfna.api.endpoints.DummyUtility;
import org.arfna.api.version.ArfnaVersion;
import org.arfna.api.version.EVersion;
import org.arfna.method.dummy.DummyResponse;
import org.arfna.util.gson.GsonHelper;
import org.arfna.util.logger.ArfnaLogger;

/**
 * @author roshnee
 * Endpoint class to be used by API Service. Contains methods for every API offered by arfna.
 */
public class ArfnaUtility {

    public String getDummyResponse(String jsonPayload) {
        ArfnaLogger.info(this.getClass(), "Received Dummy Response call");
        EVersion version = getVersion(jsonPayload);
        DummyUtility util = new DummyUtility();
        DummyResponse response = util.getResponse(jsonPayload, version);
        return GsonHelper.getGson().toJson(response);
    }

    private EVersion getVersion(String jsonPayload) {
        ArfnaVersion version = GsonHelper.getGson().fromJson(jsonPayload, ArfnaVersion.class);
        return version.getVersion();
    }

}
