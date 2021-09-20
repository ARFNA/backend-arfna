package org.arfna.api.version;

import org.arfna.database.DatabaseUtil;
import org.arfna.method.dummy.DummyApiV1;
import org.arfna.method.dummy.IDummyApi;
import org.arfna.method.password.IPasswordHelper;
import org.arfna.method.password.PasswordHelperV1;
import org.arfna.method.password.login.api.IMutateSubscriberApi;
import org.arfna.method.password.login.api.MutateSubscriberApiV1;

public enum EVersion {

    V1(new DummyApiV1(), new MutateSubscriberApiV1(), new DatabaseUtil(), new PasswordHelperV1());

    private IDummyApi dummyAPI;
    private IMutateSubscriberApi mutateSubscriberApi;
    private DatabaseUtil databaseUtil;
    private IPasswordHelper passwordHelper;

    EVersion(IDummyApi dummyAPI, IMutateSubscriberApi mutateSubscriberApi, DatabaseUtil databaseUtil, IPasswordHelper passwordHelper) {
        this.dummyAPI = dummyAPI;
        this.mutateSubscriberApi = mutateSubscriberApi;
        this.databaseUtil = databaseUtil;
        this.passwordHelper = passwordHelper;
    }

    public IDummyApi getDummyAPI() {
        return dummyAPI;
    }

    public DatabaseUtil getDatabaseUtil() {
        return databaseUtil;
    }

    public IPasswordHelper getPasswordHelper() {
        return passwordHelper;
    }

    public IMutateSubscriberApi getMutateSubscriberApi() {
        return mutateSubscriberApi;
    }
}
