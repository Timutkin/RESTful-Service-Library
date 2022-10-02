package ru.timutkin.restfulapplication.web.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SwaggerDescription {

    public static final String CREATE_AUTHOR  = "If AuthorBookRequest does not contain a List<BookDTO>, then an author will be created. If it contains a List<BookDTO>:\n" +
            "A BookDTO can only be passed with an id, then this object will be considered existing, there will be no recording or updating of the state.\n" +
            "If the message is transmitted without an id, it is assumed that the book does not exist, it will be created. In both cases, objects are automatically binded.";

}
