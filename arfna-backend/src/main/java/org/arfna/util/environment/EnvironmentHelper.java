package org.arfna.util.environment;

import org.apache.commons.lang.SystemUtils;

public class EnvironmentHelper {

    private EnvironmentHelper() {
        // restrict instantiation
    }

    public static boolean isRuntimeProd() {
        return SystemUtils.IS_OS_LINUX;
    }

}
