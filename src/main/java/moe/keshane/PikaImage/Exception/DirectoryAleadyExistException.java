package moe.keshane.PikaImage.Exception;

public class DirectoryAleadyExistException extends RuntimeException {
    public DirectoryAleadyExistException() {
        super();
    }

    public DirectoryAleadyExistException(String message) {
        super(message);
    }

    public DirectoryAleadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryAleadyExistException(Throwable cause) {
        super(cause);
    }

    protected DirectoryAleadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
