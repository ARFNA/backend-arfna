package org.arfna.method.blog;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;

public interface IGetBlogApi {

    GetBlogResponse getResponse(GetBlogPayload payload, EVersion version);

}
