package org.arfna.util.security;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.arfna.util.files.ResourceHelper;
import org.arfna.util.gson.GsonHelper;
import org.arfna.util.logger.ArfnaLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SecurityKey {

    private static SecurityKey SECURITY_KEY;

    @Expose
    @SerializedName("aws_role")
    private AwsIamKey awsIamKey;

    private SecurityKey() {
        // restrict instantiation
    }

    public AwsIamKey getAwsIamKey() {
        return awsIamKey;
    }

    public static SecurityKey getSecurityKeys() {
        if (SECURITY_KEY == null) {
            try (InputStream stream = ResourceHelper.getResourceAsStream(SecurityKey.class, "credentials.json")) {
                SECURITY_KEY = GsonHelper.getGson().fromJson(new InputStreamReader(stream), SecurityKey.class);
            } catch (IOException e) {
                ArfnaLogger.exception(SecurityKey.class, "Unable to get security keys: " + e.getMessage(), e);
            }
        }
        return SECURITY_KEY;
    }
}
