package com.github.edulook.look.endpoint.exceptions.handler;


import com.github.edulook.look.core.exceptions.ForbiddenException;
import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.endpoint.exceptions.data.ApiError;
import com.github.edulook.look.utils.LookUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> NotFound(RuntimeException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(),  LookUtils.toJSON(ex));
        return new ResponseEntity<>(
                apiError,
                new HttpHeaders(),
                apiError.getStatus());
    }


    @ExceptionHandler(value = { ForbiddenException.class })
    protected ResponseEntity<Object> forbidden(RuntimeException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(),  LookUtils.toJSON(ex));
        return new ResponseEntity<>(
                apiError,
                new HttpHeaders(),
                apiError.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(
                apiError,
                new HttpHeaders(),
                apiError.getStatus());
    }
}
