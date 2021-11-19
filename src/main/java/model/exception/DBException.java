package model.exception;

public class DBException extends Exception{
    public DBException() {
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(Exception e) {
        super(e);
    }

    public DBException(String message, Exception e) {
        super(message, e);
    }

}
