package org.arfna.api.version;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ArfnaVersion implements Serializable {
    private static final long serialVersionUID = -7053292639459045702L;

    @Expose private EVersion version;

    public EVersion getVersion() {
        return version;
    }
}
