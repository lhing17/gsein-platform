package cn.gsein.platform.system.exception;

public class GseinException extends RuntimeException{

    public GseinException() {
    }

    public GseinException(String message) {
        super(message);
    }

    public GseinException(String message, Throwable cause) {
        super(message, cause);
    }

    public GseinException(Throwable cause) {
        super(cause);
    }

    public GseinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
