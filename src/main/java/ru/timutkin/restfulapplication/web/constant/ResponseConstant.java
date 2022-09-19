package ru.timutkin.restfulapplication.web.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseConstant {

    public static final String USER_WITH_EMAIL_ALREADY_EXISTS = "A user with email = %s already exists";

    public static final String USER_WITH_ID_NOT_FOUND = "User with id = %d not found";

    public static final String BOOK_WITH_ID_D_NOT_FOUND = "Book with id = %d not found";

    public static final String INCORRECT_COUNT_OF_PAGE = "The book count of page don't should be more 4000";


}
