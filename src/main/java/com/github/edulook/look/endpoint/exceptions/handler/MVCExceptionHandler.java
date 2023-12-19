package com.github.edulook.look.endpoint.exceptions.handler;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.endpoint.exceptions.data.ApiError;
import com.github.edulook.look.endpoint.exceptions.data.ForbiddenMVCException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MVCExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ForbiddenMVCException.class })
    protected ModelAndView forbidden(RuntimeException ex, WebRequest request) {
        return new ModelAndView("forbidden");
    }
}
