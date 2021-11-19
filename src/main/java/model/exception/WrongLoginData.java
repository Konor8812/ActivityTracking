package model.exception;

public class WrongLoginData extends ServiceException{
    public WrongLoginData() {
    }

    public WrongLoginData(String message) {
        super(message);
    }

    public WrongLoginData(Exception e) {
        super(e);
    }

    public WrongLoginData(String message, Exception e) {
        super(message, e);
    }
}
