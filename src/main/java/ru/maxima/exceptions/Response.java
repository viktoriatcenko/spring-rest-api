package ru.maxima.exceptions;


import java.util.Date;

public class Response {
    private String message;
    private Date date;

    public Response(String message, Date date) {
        this.message = message;
        this.date = date;
    }
}
