package cmr.notep.exceptions;


import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.UndeclaredThrowableException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParcoursException.class)
    public ResponseEntity<Object> handleSchoolException(ParcoursException ex, WebRequest request) {
        HttpStatus status = mapExceptionToHttpStatus(ex.getCode());
        return new ResponseEntity<>(new ErrorResponse(ex.getCode(), ex.getMessage()), status);
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<Object> handleUndeclaredThrowable(UndeclaredThrowableException ex, WebRequest request) {
        Throwable cause = ex.getUndeclaredThrowable();
        if (cause instanceof ParcoursException parcoursException) {
            return handleSchoolException(parcoursException, request);
        }
        return new ResponseEntity<>(new ErrorResponse(ParcoursExceptionCodeEnum.INTERNAL_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpStatus mapExceptionToHttpStatus(ParcoursExceptionCodeEnum code) {
        return switch (code) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case OPERATION_INTERDITE -> HttpStatus.FORBIDDEN;
            case INTERFACE_NON_RESPECTEE -> HttpStatus.BAD_REQUEST;
            case DUPLICATE_KEY -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    // Inner class for structured error responses
    private static class ErrorResponse {
        private final String code;
        private final String message;

        public ErrorResponse(ParcoursExceptionCodeEnum code, String message) {
            this.code = code.name();
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
