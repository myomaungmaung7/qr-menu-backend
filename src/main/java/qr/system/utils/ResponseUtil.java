package qr.system.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import qr.system.response.ApiResponse;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success (String message, T data, HttpStatus status) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();

        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        return success(message, data, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        return success(message, data, HttpStatus.CREATED);
    }
}
