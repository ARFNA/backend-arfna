package org.arfna.util.security;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MailerKey implements Serializable {
    private static final long serialVersionUID = -6515150040484858874L;

    @Expose
    @SerializedName("api_token")
    private String apiToken;

    MailerKey(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getApiToken() {
        return apiToken;
    }
}
