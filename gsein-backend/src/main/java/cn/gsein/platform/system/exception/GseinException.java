package cn.gsein.platform.system.exception;

import cn.gsein.platform.system.enums.ErrorCode;

public class GseinException extends RuntimeException {

    private int code = 500;

    public GseinException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

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

    public int getCode() {
        return code;
    }
}
