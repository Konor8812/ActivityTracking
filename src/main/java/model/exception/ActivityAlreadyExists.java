package model.exception;

public class ActivityAlreadyExists extends EntityAlreadyExist {
    public ActivityAlreadyExists() {
    }

    public ActivityAlreadyExists(String message) {
        super(message);
    }

    public ActivityAlreadyExists(Exception e) {
        super(e);
    }

    public ActivityAlreadyExists(String message, Exception e) {
        super(message, e);
    }
}
