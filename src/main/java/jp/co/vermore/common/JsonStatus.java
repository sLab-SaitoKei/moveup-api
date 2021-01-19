package jp.co.vermore.common;

public enum JsonStatus {

    SUCCESS("S00000", "SUCCESS"),
    UNKNOWN_ERROR("S99999", "UNKONWN_ERROR"),

    ACCESS_DENY("S00001", "ACCESS_DENY"),
    PERMISSION_DENY("S00002", "PERMISSION_DENY"),
    USER_ID_INVALID("S00003", "USER_ID_INVALID"),
    TOKEN_INVALID("S00004", "TOKEN_INVALID"),

    PARAMETER_ERROR("S00101", "PARAMETER_ERROR"),
    DATA_NOT_FOUND("S00102", "DATA_NOT_FOUND"),
    DATA_SAVE_FAILED("S00103", "DATA_SAVE_FAILED"),
    PAGE_SIZE_TOO_LARGE("S00104", "PAGE_SIZE_TOO_LARGE"),
    DATA_EXISTED("S00105", "DATA_EXISTED"),

    COIN_OPERATION_FAILED("S00201","COIN_OPERATION_FAILED"),
    COIN_EXCESS("S00202", "COIN_EXCESS"),

    STOCK_OPERATION_FAILED("S00301","STOCK_OPERATION_FAILED"),

    API_NOT_FOUND("S00901", "API_NOT_FOUND"),

    // auth
    REGISTED("AT0001", "REGISTED"),
    WRONG_PASSWORD("AT0002", "WRONG_PASSWORD"),
    INIT_PROFILE("AT0003", "INIT_PROFILE"),

    // admin app
    COUPON_DATE_EXPIRED("AD0001", "COUPON_DATE_EXPIRED"),
    ;

    private final String statusCode;
    private final String message;

    JsonStatus(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String statusCode() {
        return statusCode;
    }
    public String getMessage() {
        return message;
    }
}

