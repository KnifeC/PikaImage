package moe.keshane.PikaImage.Exception;

public class PermissionFailedException extends RuntimeException {
    public PermissionFailedException() {
        super();
    }

    public PermissionFailedException(String message) {
        super(message);
    }

    public PermissionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionFailedException(Throwable cause) {
        super(cause);
    }

    protected PermissionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
