package moe.keshane.PikaImage.Exception;

public class DataInputException extends RuntimeException {
    public DataInputException() {
        super();
    }

    public DataInputException(String message) {
        super(message);
    }

    public DataInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInputException(Throwable cause) {
        super(cause);
    }

    protected DataInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
