package jp.co.vermore.validation;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NotificationRegisterParams {

    @NotNull(message = "os can't be empty")
    @Min(value = 1, message = "os can't be less than 1")
    @Max(value = 2, message = "os can't be greater than 2")
    private byte os;

    @NotEmpty(message = "deviceId can't be empty")
    @Length(min = 1, max = 255, message = "deviceId length must be between 1 and 255")
    private String deviceId;

    @NotEmpty(message = "token can't be empty")
    @Length(min = 1, max = 255, message = "token length must be between 1 and 255")
    private String token;


    public byte getOs() {
        return os;
    }

    public void setOs(byte os) {
        this.os = os;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
