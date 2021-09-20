package org.arfna.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

    private static Gson gson;
    private static Gson gsonWithPrettyPrint;

    private GsonHelper() {
        // restrict instantiation
    }

    public static Gson getGson() {
        if (gson == null)
            gson = withDefaultFields(new GsonBuilder())
                    .create();
        return gson;
    }

    public static Gson getGsonWithPrettyPrint() {
        if (gsonWithPrettyPrint == null) {
            gsonWithPrettyPrint = withDefaultFields(new GsonBuilder())
                    .setPrettyPrinting()
                    .create();
        }
        return gsonWithPrettyPrint;
    }

    private static GsonBuilder withDefaultFields(GsonBuilder builder) {
        return builder
                .enableComplexMapKeySerialization()
                .excludeFieldsWithoutExposeAnnotation();
    }

}
