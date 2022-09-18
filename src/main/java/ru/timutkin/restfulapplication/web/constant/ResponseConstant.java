package ru.timutkin.restfulapplication.web.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseConstant {

    public static final String USER_WITH_EMAIL_ALREADY_EXISTS = "A user with this email address already exists";

    public static final String USER_WITH_ID_NOT_FOUND = "User with id = %s not found";

}
