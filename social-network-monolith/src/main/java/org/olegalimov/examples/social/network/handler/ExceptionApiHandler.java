package org.olegalimov.examples.social.network.handler;

import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.core.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionApiHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> notFoundException(EntityNotFoundException exception) {
        log.error("Не удалось найти запись", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
