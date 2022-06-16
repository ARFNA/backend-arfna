package org.arfna.api.endpoints;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.blog.images.ImageIdPayload;
import org.arfna.method.blog.images.ImageIdResponse;
import org.arfna.method.password.middleware.ESubscriberRole;
import org.arfna.util.gson.GsonHelper;

import java.util.Optional;

public class ImageIdUtility {

    public ImageIdResponse getResponse(String inputPayload, EVersion version, Optional<Subscriber> subscriber) {
//        boolean isSubscriberAuthorized = version.getMiddlewareHelper().isSubscriberAuthorized(subscriber, ESubscriberRole.WRITER_ROLE);
//        if (!isSubscriberAuthorized) {
//            ImageIdResponse unauthorizedResponse = new ImageIdResponse();
//            unauthorizedResponse.setUnauthorized();
//            return unauthorizedResponse;
//        }
        ImageIdPayload payload = GsonHelper.getGson().fromJson(inputPayload, ImageIdPayload.class);
        Subscriber mockSubscriber = new Subscriber().setId(671).setRole(ESubscriberRole.ADMIN_ROLE.getRoleName());
        return version.getImageIdApi().getResponse(payload, version, mockSubscriber);
    }

}
