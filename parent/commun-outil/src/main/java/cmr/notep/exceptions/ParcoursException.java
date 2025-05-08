package cmr.notep.exceptions;

import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import lombok.Getter;

@Getter
public class ParcoursException extends Exception{
    ParcoursExceptionCodeEnum code;
    public ParcoursException(ParcoursExceptionCodeEnum code, String message) {
        super(message);
        this.code = code;
    }
    public ParcoursException(ParcoursExceptionCodeEnum code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
