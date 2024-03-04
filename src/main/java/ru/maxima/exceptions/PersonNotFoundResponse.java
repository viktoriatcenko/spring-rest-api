package ru.maxima.exceptions;

import java.util.Date;

public class PersonNotFoundResponse extends Response {
    public PersonNotFoundResponse(String message, Date date) {
        super(message, date);
    }
}
