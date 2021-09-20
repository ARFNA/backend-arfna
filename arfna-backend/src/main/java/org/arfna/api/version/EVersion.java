package org.arfna.api.version;

import org.arfna.database.DatabaseUtil;
import org.arfna.method.dummy.DummyApiV1;
import org.arfna.method.dummy.IDummyApi;
import org.arfna.method.login.IPasswordGenerator;
import org.arfna.method.login.PasswordGeneratorV1;

public enum EVersion {

    V1(new DummyApiV1(), new DatabaseUtil(), new PasswordGeneratorV1());

    private IDummyApi dummyAPI;
    private DatabaseUtil databaseUtil;
    private IPasswordGenerator passwordGenerator;

    EVersion(IDummyApi dummyAPI, DatabaseUtil databaseUtil, IPasswordGenerator passwordGenerator) {
        this.dummyAPI = dummyAPI;
        this.databaseUtil = databaseUtil;
        this.passwordGenerator = passwordGenerator;
    }

    public IDummyApi getDummyAPI() {
        return dummyAPI;
    }

    public DatabaseUtil getDatabaseUtil() {
        return databaseUtil;
    }
}
