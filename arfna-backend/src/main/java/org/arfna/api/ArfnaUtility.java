package org.arfna.api;

import org.arfna.api.endpoints.*;
import org.arfna.api.version.ArfnaVersion;
import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.MethodResponse;
import org.arfna.util.gson.GsonHelper;
import org.arfna.util.logger.ArfnaLogger;

import java.util.Optional;

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

    public MethodResponse getMutatePostTableResponse(String jsonPayload, Optional<Subscriber> subscriber) {
        ArfnaLogger.info(this.getClass(), "Received mutate post table call");
        EVersion version = getVersion(jsonPayload);
        MutatePostUtility util = new MutatePostUtility();
        return util.getResponse(jsonPayload, version, subscriber);
    }

    public MethodResponse getPostsResponse(String jsonPayload) {
        ArfnaLogger.info(this.getClass(), "Received get posts call");
        EVersion version = getVersion(jsonPayload);
        GetBlogPostUtility util = new GetBlogPostUtility();
        return util.getResponse(jsonPayload, version);
    }

    public MethodResponse getSubscriberCookieResponse(String jsonPayload, Optional<Subscriber> subscriber) {
        ArfnaLogger.info(this.getClass(), "Received read subscriber cookie call");
        EVersion version = getVersion(jsonPayload);
        ReadSubscriberCookieUtility util = new ReadSubscriberCookieUtility();
        return util.getResponse(jsonPayload, version, subscriber);
    }

    public MethodResponse imageIdResponse(String jsonPayload, Optional<Subscriber> subscriber) {
        ArfnaLogger.info(this.getClass(), "Received request to image id api");
        EVersion version = getVersion(jsonPayload);
        ImageIdUtility util = new ImageIdUtility();
        return util.getResponse(jsonPayload, version, subscriber);
    }

    public MethodResponse emailResponse(String jsonPayload) {
        ArfnaLogger.info(this.getClass(), "Received request to email api");
        EVersion version = getVersion(jsonPayload);
        EmailUtility util = new EmailUtility();
        return util.getResponse(jsonPayload, version);
    }

    private EVersion getVersion(String jsonPayload) {
        ArfnaVersion version = GsonHelper.getGson().fromJson(jsonPayload, ArfnaVersion.class);
        return version.getVersion();
    }

}
