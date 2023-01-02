package org.arfna.method.password.login.api;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;

import java.util.Optional;

public interface IMutateSubscriberApi {

    MutateSubscribersResponse getResponse(MutateSubscribersPayload payload, EVersion version, Optional<Subscriber> loggedInSubscriber);

}
