package org.arfna.util.logger;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.arfna.util.files.ResourceHelper;

import java.time.LocalDateTime;

/**
 * @author roshnee
 */
public class ArfnaLogger {

    private ArfnaLogger() {
        // restrict instantiation
    }

    private static final Logger LOGGER = Logger.getLogger("org.arfna.util.logger");
    private static final String PIPE = "\t|\t";
    private static final String LOCAL_PROPS = "local_log4j.properties";
    private static final String PROD_PROPS = "prod_log4j.properties";

    static {
        if (SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_MAC) {
            PropertyConfigurator.configure(ResourceHelper.getResourceAsUrl(ArfnaLogger.class, LOCAL_PROPS));
        } else {
            PropertyConfigurator.configure(ResourceHelper.getResourceAsUrl(ArfnaLogger.class, PROD_PROPS));
        }
    }

    public static void debug(Class<?> clazz, String message) {
        log(ELogType.DEBUG, clazz, message, null);
    }

    public static void info(Class<?> clazz, String message) {
        log(ELogType.INFO, clazz, message, null);
    }

    public static void warn(Class<?> clazz, String message) {
        log(ELogType.WARN, clazz, message, null);
    }

    public static void error(Class<?> clazz, String message) {
        log(ELogType.ERROR, clazz, message, null);
    }

    public static void exception(Class<?> clazz, String message, Exception e) {
        log(ELogType.EXCEPTION, clazz, message, e);
    }

    private static void log(ELogType logType, Class<?> clazz, String message, Exception exception) {
        if (logType == ELogType.DEBUG) {
            LOGGER.debug(formatResponse(clazz, message));
        } else if (logType == ELogType.INFO) {
            LOGGER.info(formatResponse(clazz, message));
        } else if (logType == ELogType.WARN) {
            LOGGER.warn(formatResponse(clazz, message));
        } else if (logType == ELogType.ERROR) {
            LOGGER.error(formatResponse(clazz, message));
        } else {
            LOGGER.error(formatResponse(clazz, message), exception);
        }
    }

    private static String formatResponse(Class<?> clazz, String message) {
        LocalDateTime date = LocalDateTime.now();
        return String.join(PIPE, date.toString(), clazz.getCanonicalName(), "message: " + message);
    }

    public static void main(String[] args) {
        ArfnaLogger.debug(ArfnaLogger.class, "I'm using the logger!");
    }

}
