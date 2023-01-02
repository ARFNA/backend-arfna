package org.arfna.util.security;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AwsIamKey implements Serializable {
    private static final long serialVersionUID = -6515150040484858874L;

    @Expose
    @SerializedName("access_key")
    private String accessKey;

    @Expose
    @SerializedName("secret_key")
    private String secretKey;

    AwsIamKey(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
