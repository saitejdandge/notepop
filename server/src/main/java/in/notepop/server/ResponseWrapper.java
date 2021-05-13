package in.notepop.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {
    private T data;
    private int result;
    private Status status;
    private String message;

    public static <T> ResponseWrapper<T> success(T data) {
        return new ResponseWrapper<>(data, 1, Status.SUCCESS, "success");
    }

    public static <T> ResponseWrapper<T> error(String errorMessage, int statusCode) {
        return new ResponseWrapper<>(null, statusCode, Status.FAILED, errorMessage);
    }

    public static <T> ResponseWrapper<T> error(String errorMessage) {
        return error(errorMessage, 0);
    }

}

