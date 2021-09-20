package org.arfna.util.files;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class ResourceHelper {

    private ResourceHelper() {
        // restrict instantiation
    }

    public static URL getResourceAsUrl(Class<?> clazz, String filename) {
        return clazz
                .getClassLoader()
                .getResource(clazz.getPackage().getName() + File.separator + filename);
    }

    public static InputStream getResourceAsStream(Class<?> clazz, String filename) {
        return clazz
                .getClassLoader()
                .getResourceAsStream(clazz.getPackage().getName() + File.separator + filename);
    }

}
