package com.lifeos.shared;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(OptimisticLockingFailureException.class)
    ResponseEntity<Object> conflict(OptimisticLockingFailureException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Task changed concurrently; reload and retry"),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
