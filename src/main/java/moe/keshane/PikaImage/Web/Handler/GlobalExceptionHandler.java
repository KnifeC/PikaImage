package moe.keshane.PikaImage.Web.Handler;


import moe.keshane.PikaImage.Exception.PageNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PageNotFoundException.class)
    @ResponseBody
    public String pageNotFound(PageNotFoundException e){
        return e.getMessage();
    }
}
