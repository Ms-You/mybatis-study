package com.mybatis.board.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalException(final GlobalException e) {
        log.error("handleGlobalException: {}", e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse(e.getErrorCode()));
    }

    // 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());

        return ResponseEntity
                .status(e.getStatusCode().value())
                .body(new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED));
    }

    // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("handlerMethodArgumentNotValidException: {}", e.getMessage());

        return ResponseEntity
                .status(e.getStatusCode().value())
                .body(new ErrorResponse(ErrorCode.BAD_REQUEST));
    }

    // authentication failure
    @ExceptionHandler({InternalAuthenticationServiceException.class, BadCredentialsException.class})
    protected ResponseEntity<ErrorResponse> handleInternalAuthenticationServiceException() {
        return ResponseEntity
                .status(ErrorCode.ID_OR_PASSWORD_WRONG.getStatus())
                .body(new ErrorResponse(ErrorCode.ID_OR_PASSWORD_WRONG));
    }

    // 500
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error("handleException: {}", e.getMessage());

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
