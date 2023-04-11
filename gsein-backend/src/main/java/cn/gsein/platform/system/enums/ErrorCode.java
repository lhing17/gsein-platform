package cn.gsein.platform.system.enums;

public enum ErrorCode {
    /**
     * 40101: token不能为空
     */
    TOKEN_IS_EMPTY(40101, "token不能为空"),
    /**
     * 40102: token已过期
     */
    TOKEN_IS_EXPIRED(40102, "token已过期"),
    /**
     * 40103: token不合法
     */
    TOKEN_IS_ILLEGAL(40103, "token不合法"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
