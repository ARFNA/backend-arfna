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

    @Expose
    @SerializedName("mailersend")
    private MailerKey mailerKey;

    private SecurityKey() {
        // restrict instantiation
    }

    static SecurityKey builder() {
        return new SecurityKey();
    }

    SecurityKey withAwsCredentials(String awsAccessKey, String awsSecretToken) {
        this.awsIamKey = new AwsIamKey(awsAccessKey, awsSecretToken);
        return this;
    }

    SecurityKey withMailerSendCredentials(String mailerSendToken) {
        this.mailerKey = new MailerKey(mailerSendToken);
        return this;
    }

    public AwsIamKey getAwsIamKey() {
        return awsIamKey;
    }

    public MailerKey getMailerKey() {
        return mailerKey;
    }

}
