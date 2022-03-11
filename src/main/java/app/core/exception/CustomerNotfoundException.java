package app.core.exception;

public class CustomerNotfoundException extends  Exception{

    public CustomerNotfoundException() {
    }

    public CustomerNotfoundException(String message) {
        super(message);
    }

    public CustomerNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
