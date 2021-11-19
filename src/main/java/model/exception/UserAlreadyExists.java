package model.exception;

public class UserAlreadyExists extends EntityAlreadyExist {
    public UserAlreadyExists() {
    }

    public UserAlreadyExists(String message) {
        super(message);
    }

    public UserAlreadyExists(Exception e) {
        super(e);
    }

    public UserAlreadyExists(String message, Exception e) {
        super(message, e);
    }
}
