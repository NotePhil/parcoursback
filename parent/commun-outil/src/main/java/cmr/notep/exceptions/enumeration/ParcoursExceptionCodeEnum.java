package cmr.notep.exceptions.enumeration;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ParcoursExceptionCodeEnum {
    NOT_FOUND("Resource not found"),
    OPERATION_INTERDITE("Operation is not allowed"),
    INTERFACE_NON_RESPECTEE("Interface contract not respected"),
    INTERNAL_ERROR("Internal server error"),
    INVALID_INPUT("Invalid input data"),
    INVALID_TOKEN("Invalid activation token"),
    INVALID_OPERATION("Invalid operation"),
    EMAIL_NOT_SENT("The activation email could not be sent."),
    INVALID_STATE("The user is not in a valid state for this operation."),
    MAPPING_FAILED("User entity mapping failed"),
    TOKEN_EXPIRED("Token Expired");

    private final String message;

    ParcoursExceptionCodeEnum(String s) {
        this.message = s;
    }
}
