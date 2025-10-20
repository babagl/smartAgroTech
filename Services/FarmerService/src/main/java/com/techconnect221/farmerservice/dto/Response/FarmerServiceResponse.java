package com.techconnect221.farmerservice.dto.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FarmerServiceResponse <T>{
    private boolean success;
    private String message;
    private String debugMessage;
    private T data;
    private Date timestamp;
    private HttpStatus status;

    public FarmerServiceResponse(boolean success,String message, String debugMessage, T data, Date timestamp, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.debugMessage = debugMessage;
        this.data = data;
        this.timestamp = timestamp;
        this.status = status;
    }

    public void setTimestamp() {
        this.timestamp = new Date();
    }

    public static <T> FarmerServiceResponse<T> success(T data) {
        var response = new FarmerServiceResponse<T>();
        response.setSuccess(true);
        response.setMessage("OK");
        response.setData(data);
        response.setTimestamp(new Date());
        response.setStatus(HttpStatus.OK);
        return response;
    }

    public static <T> FarmerServiceResponse<T> error(String message) {
        var response = new FarmerServiceResponse<T>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setTimestamp(new Date());
        response.setStatus(HttpStatus.BAD_REQUEST);
        return response;
    }
}
