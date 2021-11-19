package model.exception;

public class ActivityAlreadyTaken extends ServiceException{
    public ActivityAlreadyTaken() {
    }

    public ActivityAlreadyTaken(String message) {
        super(message);
    }

    public ActivityAlreadyTaken(Exception e) {
        super(e);
    }

    public ActivityAlreadyTaken(String message, Exception e) {
        super(message, e);
    }
}
