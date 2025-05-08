package cmr.notep.exceptions;


import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParcoursException.class)
    public ResponseEntity<Object> handleSchoolException(ParcoursException ex, WebRequest request) {
        System.out.println("GlobalExceptionHandler - Handling SchoolException");
        HttpStatus status = mapExceptionToHttpStatus(ex.getCode());

        return new ResponseEntity<>(
                new ErrorResponse(ex.getCode(), ex.getMessage()),
                status
        );
    }

    private HttpStatus mapExceptionToHttpStatus(ParcoursExceptionCodeEnum code) {
        return switch (code) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case OPERATION_INTERDITE -> HttpStatus.FORBIDDEN;
            case INTERFACE_NON_RESPECTEE -> HttpStatus.BAD_REQUEST;
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
