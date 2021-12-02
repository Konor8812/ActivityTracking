package model.exception;

public class PropertyAlreadyExistsException extends Exception{
    public PropertyAlreadyExistsException() {
    }

    public PropertyAlreadyExistsException(String message) {
        super(message);
    }

    public PropertyAlreadyExistsException(Exception e) {
        super(e);
    }

    public PropertyAlreadyExistsException(String message, Exception e) {
        super(message, e);
    }
}
