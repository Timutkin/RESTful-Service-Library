package ru.timutkin.restfulapplication.web.handler;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.exception.ExceptionUtils;

@UtilityClass
public class ExceptionHandlerUtils {
    public static String buildErrorMessage(Exception exception) {
        StringBuilder message =
                new StringBuilder(ExceptionUtils.getMessage(exception));

        Throwable cause;
        if ((cause = exception.getCause()) != null) {
            message.append(", cause: ").append(ExceptionUtils.getMessage(cause));
        }

        return message.toString();
    }
}
