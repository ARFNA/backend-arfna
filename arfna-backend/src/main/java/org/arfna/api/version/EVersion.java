package org.arfna.api.version;

import org.arfna.database.DatabaseUtil;
import org.arfna.method.dummy.DummyApiV1;
import org.arfna.method.dummy.IDummyApi;

public enum EVersion {

    V1(new DummyApiV1(), new DatabaseUtil());

    private IDummyApi dummyAPI;
    private DatabaseUtil databaseUtil;

    EVersion(IDummyApi dummyAPI, DatabaseUtil databaseUtil) {
        this.dummyAPI = dummyAPI;
        this.databaseUtil = databaseUtil;
    }

    public IDummyApi getDummyAPI() {
        return dummyAPI;
    }

    public DatabaseUtil getDatabaseUtil() {
        return databaseUtil;
    }
}
