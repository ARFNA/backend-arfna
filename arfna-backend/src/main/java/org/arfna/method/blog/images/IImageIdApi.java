package org.arfna.method.blog.images;

import org.arfna.api.version.ArfnaVersion;
import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;

import java.util.Optional;

public interface IImageIdApi {

    ImageIdResponse getResponse(ImageIdPayload payload, EVersion version, Optional<Subscriber> subscriber);

}
