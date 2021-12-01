package org.arfna.method.blog.mutation;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;

public interface IMutatePostApi {

    MutatePostResponse getResponse(MutatePostPayload payload, EVersion version, Subscriber subscriber);

}
