package model.exception;

public class EntityAlreadyExist extends ServiceException{
    public EntityAlreadyExist() {
    }

    public EntityAlreadyExist(String message) {
        super(message);
    }

    public EntityAlreadyExist(Exception e) {
        super(e);
    }

    public EntityAlreadyExist(String message, Exception e) {
        super(message, e);
    }
}
