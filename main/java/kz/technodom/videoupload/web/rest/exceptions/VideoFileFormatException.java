package kz.technodom.videoupload.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VideoFileFormatException extends RuntimeException {
    public VideoFileFormatException(String message) {
        super(message);
    }

    public VideoFileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
