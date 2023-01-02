package org.arfna.util.security;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.arfna.database.DatabaseUtil;
import org.arfna.util.environment.EnvironmentHelper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import org.arfna.util.files.ResourceHelper;
import org.arfna.util.gson.GsonHelper;
import org.arfna.util.logger.ArfnaLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class SecurityUtility {

    private static final String CREDENTIALS_SECRET_NAME = "credentials";
    private static final String LOCAL_CREDENTIALS_FILE_NAME = "credentials.json";
    private static final String HIBERNATE_PROPS_SECRET = "hibernate_credentials";
    private static final String LOCAL_HIBERNATE_CREDENTIALS = "hibernate_creds.json";

    private static SecurityKey SECURITY_KEY;
    private static Map HIBERNATE_PROPERTIES_MAP;
    private static SecretsManagerClient SECRET_CLIENT;


    public static SecurityKey getSecurityKeys() {
        if (SECURITY_KEY == null) {
            loadSecurityKey();
        }
        return SECURITY_KEY;
    }

    public static Map getHibernatePropertiesCredentials() {
        if (HIBERNATE_PROPERTIES_MAP == null) {
            loadHibernatePropertiesMap();
        }
        return HIBERNATE_PROPERTIES_MAP;
    }

    private static void loadHibernatePropertiesMap() {
        HIBERNATE_PROPERTIES_MAP = EnvironmentHelper.isRuntimeProd() ?
                loadHibernateProperties(getSecretString(HIBERNATE_PROPS_SECRET)) :
                loadHibernateProperties(getHibernateProps());
    }

    private static void loadSecurityKey() {
        SECURITY_KEY = EnvironmentHelper.isRuntimeProd() ?
                getSecurityKeyFromSecret() : getSecurityKeyFromDev();
    }

    private static SecurityKey getSecurityKeyFromDev() {
        return GsonHelper.getGsonWithPrettyPrint().fromJson(getSecurityKeyJsonDev(), SecurityKey.class);
    }

    private static Map loadHibernateProperties(String hibernateJson) {
        return GsonHelper.getGson().fromJson(hibernateJson, Map.class);
    }

    private static SecurityKey getSecurityKeyFromSecret() {
        String secret = getSecretString(CREDENTIALS_SECRET_NAME);
        assert secret != null;
        JsonObject root = JsonParser.parseString(secret).getAsJsonObject();
        return SecurityKey
            .builder()
            .withAwsCredentials(
                    root.getAsJsonPrimitive("aws_role.access_key").getAsString(),
                    root.getAsJsonPrimitive("aws_role.secret_key").getAsString())
            .withMailerSendCredentials(
                    root.getAsJsonPrimitive("mailersend.api_token").getAsString()
            );
    }

    private static String getSecretString(String key) {
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                    .secretId(key)
                    .build();
            GetSecretValueResponse valueResponse = getSecretClient().getSecretValue(valueRequest);
            return valueResponse.secretString();
        } catch (SecretsManagerException e) {
            ArfnaLogger.exception(SecurityUtility.class, "Unable to fetch secret " + key + ": " + e.awsErrorDetails().errorMessage(), e);
        }
        return null;
    }

    private static SecretsManagerClient getSecretClient() {
        if (SECRET_CLIENT == null) {
            Region region = Region.US_WEST_2;
            SECRET_CLIENT = SecretsManagerClient.builder()
                    .region(region)
                    .build();
        }
        return SECRET_CLIENT;
    }

    private static String getHibernateProps() {
        return readJsonFromFile(DatabaseUtil.class, LOCAL_HIBERNATE_CREDENTIALS);
    }

    private static String getSecurityKeyJsonDev() {
        return readJsonFromFile(SecurityKey.class, LOCAL_CREDENTIALS_FILE_NAME);
    }

    private static String readJsonFromFile(Class<?> clazz, String resourceName) {
        try (InputStream inputStream = ResourceHelper.getResourceAsStream(clazz, resourceName)) {
            return new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            ArfnaLogger.exception(SecurityUtility.class, resourceName + ": Unable to get security keys: " + e.getMessage(), e);
        }
        return null;
    }

}
