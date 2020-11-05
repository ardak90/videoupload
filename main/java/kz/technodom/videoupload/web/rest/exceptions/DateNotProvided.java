package kz.technodom.videoupload.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class DateNotProvided extends RuntimeException {
        public DateNotProvided(String message) {
            super(message);
        }

        public DateNotProvided(String message, Throwable cause) {
            super(message, cause);
        }
    }

