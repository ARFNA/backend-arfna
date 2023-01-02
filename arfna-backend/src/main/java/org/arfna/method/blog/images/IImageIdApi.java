package org.arfna.method.blog.images;

import org.arfna.api.version.ArfnaVersion;
import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;

public interface IImageIdApi {

    ImageIdResponse getResponse(ImageIdPayload payload, EVersion version, Subscriber subscriber);

}
