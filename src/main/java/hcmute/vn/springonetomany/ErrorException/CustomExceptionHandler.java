package hcmute.vn.springonetomany.ErrorException;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handlerNotFoundException(Exception e) {
        return "error";
    }
}

