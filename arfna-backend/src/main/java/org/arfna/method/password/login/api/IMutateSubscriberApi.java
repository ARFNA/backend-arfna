package org.arfna.method.password.login.api;

import org.arfna.api.version.EVersion;

public interface IMutateSubscriberApi {

    MutateSubscribersResponse getResponse(MutateSubscribersPayload payload, EVersion version);

}
