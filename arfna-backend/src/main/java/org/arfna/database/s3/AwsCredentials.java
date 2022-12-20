package org.arfna.database.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.arfna.util.security.SecurityKey;
import org.arfna.util.security.SecurityUtility;

public class AwsCredentials implements AWSCredentialsProvider {

    @Override
    public AWSCredentials getCredentials() {
        SecurityKey key = SecurityUtility.getSecurityKeys();
        return new BasicAWSCredentials(key.getAwsIamKey().getAccessKey(), key.getAwsIamKey().getSecretKey());
    }

    @Override
    public void refresh() {
        // do nothing
    }

}
