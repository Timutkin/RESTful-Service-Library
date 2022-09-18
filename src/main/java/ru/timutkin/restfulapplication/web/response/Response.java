package ru.timutkin.restfulapplication.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private String errorMessage;
}
