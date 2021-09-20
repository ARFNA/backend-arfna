package org.arfna.api.endpoints;

import org.arfna.api.version.EVersion;
import org.arfna.method.dummy.DummyResponse;
import org.arfna.util.logger.ArfnaLogger;

public class DummyUtility {

    public DummyResponse getResponse(String payload, EVersion version) {
        ArfnaLogger.debug(this.getClass(), "Version is: " + version.name());
        return version.getDummyAPI().getResponse(payload);
    }
}
