package org.arfna.api.endpoints;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.blog.mutation.MutatePostPayload;
import org.arfna.method.blog.mutation.MutatePostResponse;
import org.arfna.method.password.middleware.ESubscriberRole;
import org.arfna.util.gson.GsonHelper;

import java.util.Optional;

public class MutatePostUtility {

    public MutatePostResponse getResponse(String inputPayload, EVersion version, Optional<Subscriber> subscriber) {
        boolean isSubscriberAuthorized = version.getMiddlewareHelper().isSubscriberAuthorized(subscriber, ESubscriberRole.WRITER_ROLE);
        if (!isSubscriberAuthorized) {
            MutatePostResponse unauthorizedResponse = new MutatePostResponse();
            unauthorizedResponse.setUnauthorized();
            return unauthorizedResponse;
        }
        MutatePostPayload payload = GsonHelper.getGson().fromJson(inputPayload, MutatePostPayload.class);
        return version.getMutatePostApi().getResponse(payload, version, subscriber.get());
    }

}
