package org.arfna.api.version;

import org.arfna.method.dummy.DummyApiV1;
import org.arfna.method.dummy.IDummyApi;

public enum EVersion {

    V1(new DummyApiV1());

    private IDummyApi dummyAPI;

    EVersion(IDummyApi dummyAPI) {
        this.dummyAPI = dummyAPI;
    }

    public IDummyApi getDummyAPI() {
        return dummyAPI;
    }
}
