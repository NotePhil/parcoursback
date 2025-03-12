package cmr.notep.exceptions;

public class ParcoursException extends Exception{
    public ParcoursException(String message) {
        super(message);
    }
    public ParcoursException(String message, Throwable cause) {
        super(message, cause);
    }
}
